package com.analyticobjects.matchpoint.elementaryfunctions;

import com.analyticobjects.matchpoint.analytics.*;
import com.analyticobjects.matchpoint.numerics.*;
/**
 *
 * @author Joel
 * @version 1.0, 11/04/2006
 */
public class Sine implements ElementaryFunction
{
    private static final String name = "Sine";
    private static final String[] vars = {"x"};
    private static final FunctionString fstr = new FunctionString("sin(x)",vars,null);
    public static final String fstrHeader = "sin(";
    public static final String fstrTrailer = ")";
    private static final FunctionString dfstr = new FunctionString("cos(x)",vars,null);
    
    
    public String getFunctionName(){return name;}
    public FunctionString getFunctionString(){return fstr;}
    public String getFunctionStringHeader(){return fstrHeader;}
    public String getFunctionStringTrailer(){return fstrTrailer;}
    
    public double evaluate(double x)
    {
        return Math.sin(x);
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
