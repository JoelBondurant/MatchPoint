package com.analyticobjects.matchpoint.analytics;

import java.util.*;
import java.util.regex.*;
import com.analyticobjects.matchpoint.elementaryfunctions.*;
/**
 * This class can calculate the derivate of a function represented by a
 * FunctionString object.
 *
 * @author Joel
 * @version 1.0, 11/04/2006
 */
public class DerivativeOperator
{
    private ElementaryFunctionReflection eFunRef;
    
    public DerivativeOperator()
    {
        eFunRef = ElementaryFunctionReflection.getInstance();
    }
    
    public static FunctionString differentiate(FunctionString fstr)
    {
       // if(isElementary(fstr)){}
        return fstr;
    }
    
}
