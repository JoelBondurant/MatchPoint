package com.analyticobjects.matchpoint.elementaryfunctions;

import com.analyticobjects.matchpoint.analytics.*;
import com.analyticobjects.matchpoint.numerics.*;
        
/**
 *
 * @author Joel
 * @version 1.0, 11/04/2006
 */
public interface ElementaryFunction
{
    public String getFunctionName();
    public FunctionString getFunctionString();
    public String getFunctionStringHeader();
    public String getFunctionStringTrailer();
    public Object evaluateByReflection(Object arg);
    public FunctionString derivative();
}
