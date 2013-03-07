package com.analyticobjects.matchpoint.numerics;

/**
 *
 * @author Joel
 * @version 1.0, 01/31/2007
 */
public class IntegerInterval
{
    public int min;
    public int max;
    
    public IntegerInterval(int min, int max)
    {
        this.min = min;
        this.max = max;
    }
    
    public int getContainedIntegerCount(){return max - min + 1;}
    public int getDiameter(){return max - min;}
}
