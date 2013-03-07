/*
 * StatisticalReflection.java
 *
 * Created on October 16, 2006, 8:04 PM
 *
 * Copyright 2006 Analytic Objects. All rights reserved.
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.analyticobjects.matchpoint.numerics;

import java.lang.reflect.*;
/**
 *
 * @author Joel
 * @version 1.0, 10/16/2006
 */
public class StatisticalReflection
{

    public static final Method[] methods = Statistics.class.getMethods();
    private static final String[][] statNames =
           {{"getName","Name"},
            {"getElementCount","Length"},
            {"getMinimum","Minimum"},
            {"getMaximum","Maximum"},
            {"getDiameter","Diameter"},
            {"getTotal","Sum"},
            {"getSumOfSquares","Sum of Squares"},
            {"getMean","Mean"},
            {"getVariance","Variance"}};
  
    public static String getStatName(int i){return statNames[i][1];}
    public static int getStatsCount(){return statNames.length;}

    public static Object getStatsValueAt(int row, int column)
    {
        Variable x = DataStructure.getInstance().getVariable(row);
        x.updateStats();
        String funName = statNames[column][0];//the name of the method we want to invoke.
        for(int i = 0; i<methods.length; i++)
        {
            if(methods[i].getName()==funName){column=i;}//don't do this, just sort the list once!
        }
        try
        {
            return methods[column].invoke(x);
        }
        catch(Exception e){e.printStackTrace();}
        return null;
    }
    
}
