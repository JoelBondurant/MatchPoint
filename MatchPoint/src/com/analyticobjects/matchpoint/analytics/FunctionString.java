package com.analyticobjects.matchpoint.analytics;

import com.analyticobjects.matchpoint.elementaryfunctions.*;
import java.util.*;
import java.util.regex.*;
/**
 * This class contains a String representation of a function for user defined
 * functions and algebraic manipulation of functions.
 *
 * @author Joel
 * @version 1.0, 11/04/2006
 */
public class FunctionString
{
    private String functionString;
    private final String[] vars;
    private final String[] params;
    public static final FunctionString ZERO = new FunctionString("0",null,null);
    public static final FunctionString ONE = new FunctionString("1",null,null);
    private static ElementaryFunctionReflection eFunRef = ElementaryFunctionReflection.getInstance();

    public FunctionString(String str, String[] vars, String[] params)
    {
        functionString = new String(str);
        this.vars = vars;
        this.params = params;
    }
    
    public String toString(){return new String(functionString);}
    public int getVariableCount(){return vars.length;}
    public String[] getVariableList()
    {
        String[] varCopy = new String[vars.length];
        System.arraycopy(vars,0,varCopy,0,vars.length);
        return varCopy;
    }
    public int getParameterCount(){return params.length;}
    public String[] getParameterList()
    {
        String[] paramsCopy = new String[params.length];
        System.arraycopy(params,0,paramsCopy,0,params.length);
        return paramsCopy;
    }
    
    private static Pattern zeroPattern = Pattern.compile("(-?)0(\\.0*)?");
    
    public FunctionString simplify()  // a nonrecursive simplification algorithm
    {
        functionString = stripWhiteSpace(functionString);
        stripUnnecessaryParentheses();
        functionString = simplifyAdditionOnePass(functionString);
        functionString = simplify(functionString);
        return this;
    }
    
    private String simplifyAdditionOnePass(String str)
    {
        Matcher m = addedNumberPattern.matcher(str);
        while(m.find())
        {
            String a,b,c;
            a = str.substring(0,m.start());
            b = str.substring(m.start(),m.end());
            c = str.substring(m.end(),str.length());
            String[] components = getAdditionComponents(b);
            String part1 = components[0];
            String part2 = components[1];
            if(isNumber(part1) && isNumber(part2))
            {
                double x = Double.parseDouble(part1) + Double.parseDouble(part2);
                b = simplify(Double.toString(x));
            }
            str = a + b + c;
            m = addedNumberPattern.matcher(str);
        }
        return str;
    }
    
    private String simplify(String str) // a recursive simplification algorithm
    {
        if(str == ""){return str;}
        if(str == null){return null;}
        if(isNumericallyEqualToZero(str)){return "0";}
        if(isNumericallyEqualToOne(str)){return "1";}
        String simplifiedStr = str;
        if(isNumber(str))
        {
            simplifiedStr = str;
            return simplifiedStr;
        }
        if(isNumberWrappedInParens(str))
        {
            simplifiedStr = stripParentheses(str);
            return simplifiedStr;
        }
        if(isVariable(str))
        {
            simplifiedStr = str;
            return simplifiedStr;
        }
        if(isVariableWrappedInParens(str))
        {
            simplifiedStr = stripParentheses(str);
            return simplifiedStr;
        }
        if(isParameter(str))
        {
            simplifiedStr = str;
            return simplifiedStr;
        }
        if(isParameterWrappedInParens(str))
        {
            simplifiedStr = stripParentheses(str);
            return simplifiedStr;
        }
        if(eFunRef.isElementaryFunction(str))
        {
            //String[] strComps = eFunRef.getElementaryFucntionComponentsByFullString(str);
            simplifiedStr = str;//strComps[0] + simplify(strComps[1]) + strComps[2];
            return simplifiedStr;
        }
        if(eFunRef.isElementaryFunctionWrappedInParens(str))
        {
            //simplifiedStr = stripParentheses(str);
            //String[] strComps = eFunRef.getElementaryFucntionComponentsByFullString(simplifiedStr);
            simplifiedStr = str;//strComps[0] + simplify(strComps[1]) + strComps[2];
            return simplifiedStr;
        }
        if(isAdditionWrappedInParens(str) || isAddition(str))
        {
            simplifiedStr = str;
            int parensWrapping = countStrippedParentheses(str);
            String headerParens = "";
            String trailerParens = "";
            String str2 = stripParentheses(str);
            String[] components = getAdditionComponents(str2);
            if(components != null && components[0] != null && components[1] != null)
            {
                String part1 = components[0];
                String part2 = components[1];
                boolean isPart1Zero = isNumericallyEqualToZero(part1);
                boolean isPart2Zero = isNumericallyEqualToZero(part2);
                if(isPart1Zero)
                {
                    simplifiedStr = simplify(part2);
                }
                if(isPart2Zero)
                {
                    simplifiedStr = simplify(part1);
                }
                if(isPart1Zero && isPart2Zero)
                {
                    simplifiedStr = "0";
                }
                if(isNumber(part1) && isNumber(part2))//this usually doesn't do anything
                {
                    double x = Double.parseDouble(part1) + Double.parseDouble(part2);
                    simplifiedStr = simplify(Double.toString(x));
                }
                if(!isPart1Zero && !isPart2Zero)
                {
                    simplifiedStr = simplify(components[0]) + "+" + simplify(components[1]);
                }
                for(int i = 0; i<parensWrapping; i++)
                {
                    headerParens += "(";
                    trailerParens += ")";
                }
                simplifiedStr = headerParens + simplifiedStr + trailerParens;
            }
            return simplifiedStr;
        }
        return simplifiedStr;
    }
    
    public boolean isNumericallyEqualToZero(String str)
    {
        boolean result = false;
        try
        {
            double num = Double.parseDouble(str);
            if(Math.abs(num)==0.0){result = true;}
        }catch(Exception e){}
        return result;
    }
    
    public boolean isNumericallyEqualToOne(String str)
    {
        boolean result = false;
        try
        {
            double num = Double.parseDouble(str);
            if(num==1.0){result = true;}
        }catch(Exception e){}
        return result;
    }
    
    public boolean isVariable(String str)
    {
        if(vars == null){return false;}
        for(String s: vars)
        {
            if(s.equals(str)){return true;}
        }
        return false;
    }
    
    public boolean isVariableWrappedInParens(String str)
    {
        if(vars == null){return false;}
        String str2 = stripParentheses(str);
        return isVariable(str2);
    }
    
    public boolean isParameter(String str)
    {
        if(params == null){return false;}
        for(String s: params)
        {
            if(s.equals(str)){return true;}
        }
        return false;
    }
    
    public boolean isParameterWrappedInParens(String str)
    {
        if(params == null){return false;}
        String str2 = stripParentheses(str);
        return isParameter(str2);
    }
    
    private static Pattern numberPattern = Pattern.compile("[+|-]?\\d+(\\.\\d*)?|[+|-]?\\.\\d+");
    private static Pattern addedNumberPattern = Pattern.compile("([\\(\\+|\\(\\-]?\\d+(\\.\\d*)?|[+|-]?\\.\\d+)\\+([\\(\\+|\\(\\-]?\\d+(\\.\\d*)?|[+|-]?\\.\\d+)");
    
    public boolean isNumber(String str)
    {
        boolean result = false;
        try
        {
            double x = Double.parseDouble(str);
            result = true;
        }catch(Exception e){}
        return result;
    }
    
    public boolean isNumberWrappedInParens(String str)
    {
        String str2 = stripParentheses(str);
        return isNumber(str2);
    }
    
    public static int countStrippedParentheses(String str)
    {
        int x1 = str.length();
        str = stripParentheses(str);
        int x2 = str.length();
        return (x1 - x2)/2;
    }
    
    public static String stripParentheses(String str)
    {
        if(str.startsWith("(") && str.endsWith(")"))
        {
            int parenMatch = 0;
            int parenIndex = 1;
            boolean parenMatched = false;
            for(int i=1; !parenMatched && i<str.length(); i++)
            {
                if(str.charAt(i) == '(')
                {
                    parenIndex++;
                }
                if(str.charAt(i) == ')')
                {
                    parenIndex--;
                }
                if(parenIndex == 0)
                {
                    parenMatch = i;
                    parenMatched = true;
                }
            }
            if(parenMatch == str.length()-1)
            {
                str = str.substring(1,str.length()-1);
                return stripParentheses(str);
            }
        }
        return str;
    }
    
    private void stripUnnecessaryParentheses()
    {
        String str = functionString;
        str = stripParentheses(str);
        int parenCount = 0;
        for(int i = 0; i<str.length(); i++)
        {
            if(str.charAt(i) == '('){parenCount++;}
        }
        if(parenCount == 0){return;}
        int[] openParens = new int[parenCount];
        int[] closeParens = new int[parenCount];
        int oparendex = 0;
        int cparendex = 0;
        for(int i = 0; i<str.length(); i++)
        {
            if(str.charAt(i) == '(')
            {
                openParens[oparendex] = i;
                oparendex++;
            }
            if(str.charAt(i) == ')')
            {
                closeParens[cparendex] = i;
                cparendex++;
            }
        }
        int[][] parens = new int[parenCount][2];
        Vector<Integer> openParenVector = new Vector<Integer>(parenCount);
        for(int i = 0; i<parenCount; i++)
        {
            openParenVector.add(new Integer(openParens[i]));
        }
        for(int i = 0; i<parenCount; i++)
        {
            parens[i][1] = closeParens[i];
            int maxOpenParen = 0;
            int maxIndex = 0;
            for(int j = 0; j<openParenVector.size(); j++)
            {
                if((int)openParenVector.get(j)<closeParens[i])
                {
                    maxOpenParen = (int)openParenVector.get(j);
                    maxIndex = j;
                }
                else
                {
                    break;
                }
            }
            openParenVector.remove(maxIndex);
            parens[i][0] = maxOpenParen;
        }
        for(int i = 0; i<parenCount-1; i++)
        {
            if( (parens[i][0] - 1 == parens[i+1][0]) && (parens[i][1] + 1 == parens[i+1][1]) )
            {
                StringBuilder strb = new StringBuilder(str);
                strb.setCharAt(parens[i][1],' ');
                strb.setCharAt(parens[i][0],' ');
                str = strb.toString();
            }
        }
        str = stripWhiteSpace(str);
        this.functionString = str;
    }
    
    public static String stripWhiteSpace(String str)
    {
        return str.replaceAll("(\\s)*","");
    }
    
    public boolean isElementary(String str)
    {
        if(isNumber(str)){return true;}
        if(isNumberWrappedInParens(str)){return true;}
        if(isParameter(str)){return true;}
        if(isParameterWrappedInParens(str)){return true;}
        if(isVariable(str)){return true;}
        if(isVariableWrappedInParens(str)){return true;}
        if(eFunRef.isElementaryFunction(str)){return true;}
        if(eFunRef.isElementaryFunctionWrappedInParens(str)){return true;}
        return false;
    }
    
    public boolean areParensBalanced(String str)
    {
        int numOpen = 0;
        int numClosed = 0;
        for(int i = 0; i<str.length(); i++)
        {
            if(str.charAt(i) == '('){numOpen++;}
            if(str.charAt(i) == ')'){numClosed++;}
            if(numClosed > numOpen){return false;}
        }
        return (numOpen == numClosed);
    }
    
    private static Pattern addPattern = Pattern.compile("(\\+)");
    public boolean isAddition(String str)
    {
        boolean result = false;
        Matcher m = addPattern.matcher(str);
        if(!m.find()){return false;}
        String a;
        String b;
        int addIndex;
        do
        {
            addIndex = m.start();
            a = str.substring(0, addIndex);
            b = str.substring(addIndex + 1, str.length());
            if(areParensBalanced(a) && areParensBalanced(b))
            {
                result = true;
            }
        }while(!result && m.find());
        return result;
    }
    
    public String[] getAdditionComponents(String str)
    {
        if(!isAddition(str)){return null;}
        String[] components = new String[2];
        Matcher m = addPattern.matcher(str);
        if(!m.find()){return null;}
        String a;
        String b;
        int addIndex;
        do
        {
            addIndex = m.start();
            a = str.substring(0, addIndex);
            b = str.substring(addIndex + 1, str.length());
            if(areParensBalanced(a) && areParensBalanced(b))
            {
                components[0] = a;
                components[1] = b;
            }
        }while(m.find());
        return components;
    }
    
    public boolean isAdditionWrappedInParens(String str)
    {
        String str2 = stripParentheses(str);
        return isAddition(str2);
    }
    
    public static void main(String[] args)
    {
        String str = "a + sin( b + tan(x+0.0+6+7) + 0 +3+5+3*5+6)";
        String[] vars = {"a,b,x"};
        //str = "(1.0) + (((((5.5668))*7.56))*(-8.5 (+9) *(6 - 3.8965)))";
        FunctionString fstr = new FunctionString(str,vars,null);
        System.out.println(fstr);
        fstr.simplify();
        System.out.println(fstr);
    }
}
