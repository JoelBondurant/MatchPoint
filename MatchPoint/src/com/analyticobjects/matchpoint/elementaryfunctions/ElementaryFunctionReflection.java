package com.analyticobjects.matchpoint.elementaryfunctions;

import java.util.*;
import java.util.regex.*;
/**
 *
 * @author Joel
 * @version 1.0, 11/05/2006
 */
public class ElementaryFunctionReflection
{
    private static ElementaryFunctionReflection uniqueInstance;
    private Vector<ElementaryFunction> efunctions;
    
    /** Creates a new instance of ElementaryFunctionReflection */
    private ElementaryFunctionReflection()
    {
        efunctions = new Vector<ElementaryFunction>(50);
        efunctions.add(new Sine());
        efunctions.add(new Cosine());
        efunctions.add(new Tangent());
        efunctions.add(new Secant());
    }
    
    public static ElementaryFunctionReflection getInstance()
    {
        if(uniqueInstance == null)
        {
            uniqueInstance = new ElementaryFunctionReflection();
        }
        return uniqueInstance;
    }
    
    public String[] getLexemes()
    {
        return null;
    }
    
    public ElementaryFunction getElementaryFunctionByStringHeader(String header)
    {
        for(ElementaryFunction efun: efunctions)
        {
            if(efun.getFunctionStringHeader().equals(header))
            {
                return efun;
            }
        }
        return null;
    }
    
    public String[] getElementaryFunctionComponentsByFullString(String full)
    {
        String[] components = new String[3];
        String regex = "";
        for(ElementaryFunction efun: efunctions)
        {
            regex += efun.getFunctionStringHeader();
            regex = regex.substring(0,regex.length()-1);
            regex += "\\(|";
        }
        regex = regex.substring(0,regex.length()-1);
        Pattern efunctionHeader = Pattern.compile(regex);
        Matcher m = efunctionHeader.matcher(full);
        if(!m.find()){return null;}
        int start = m.start();
        int end = m.end();
        int endEnd = full.length() - 1;
        components[0] = full.substring(0, end);
        components[1] = full.substring(end, endEnd - (start));
        components[2] = full.substring(endEnd - (start), endEnd + 1);
        return components;
    }
    
    public boolean isElementaryFunction(String str)
    {
        for(int i = 0; i<efunctions.size(); i++)
        {
            if(str.startsWith(efunctions.get(i).getFunctionStringHeader()) && str.endsWith(efunctions.get(i).getFunctionStringTrailer()))
            {
                return true;
            }
        }
        return false;
    }
    
    public boolean isElementaryFunctionName(String str)
    {
        for(int i = 0; i<efunctions.size(); i++)
        {
            String name = efunctions.get(i).getFunctionStringHeader();
            if(name.endsWith("("))
            {
                return true;
            }
        }
        return false;
    }
    
    public boolean isElementaryFunctionWrappedInParens(String str)
    {
        StringBuffer strb = new StringBuffer(str);
        while(str.startsWith("(") && str.endsWith(")"))
        {
            strb.deleteCharAt(strb.length()-1);
            strb.deleteCharAt(0);
            str = new String(strb);
        }
        String str2 = new String(strb);
        return isElementaryFunction(str2);
    }
    
}
