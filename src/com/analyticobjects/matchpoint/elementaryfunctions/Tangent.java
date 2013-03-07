package com.analyticobjects.matchpoint.elementaryfunctions;

import com.analyticobjects.matchpoint.analytics.*;
import com.analyticobjects.matchpoint.numerics.*;
/**
 *
 * @author Joel
 * @version 1.0, 11/05/2006
 */
public class Tangent implements ElementaryFunction
{
    private static final String name = "Tangent";
    private static final String[] vars = {"x"};
    private static final FunctionString fstr = new FunctionString("tan(x)",vars,null);
    public static final String fstrHeader = "tan(";
    public static final String fstrTrailer = ")";
    private static final FunctionString dfstr = new FunctionString("sec(x)^2",vars,null);
    
    
    public String getFunctionName(){return name;}
    public FunctionString getFunctionString(){return fstr;}
    public String getFunctionStringHeader(){return fstrHeader;}
    public String getFunctionStringTrailer(){return fstrTrailer;}
    
    public double evaluate(double x)
    {
        return Math.tan(x);
    }
    
    public Object evaluateByReflection(Object arg)
    {
        MutableDouble y = new MutableDouble();
        double y2 = evaluate(((MutableDouble)arg).doubleValue());
        y.setValue(y2);
        return y;
    }
    
    public FunctionString derivative()
    {
        return dfstr;
    }
}
