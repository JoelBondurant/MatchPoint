package com.analyticobjects.matchpoint.chart;

import java.text.AttributedString;
/**
 * The AxisScale class is the base class upon which all other axis scales are based.
 * AxisScale objects do not deal with graphics context coordinates since they 
 * know nothing about their drawn/spatial orientation.
 *
 * @author Joel
 * @version 1.0, 09/30/2006
 */
public abstract class AxisScale
{
    
    public static double MINIMUM_LOWER_BOUND;
    public static double MAXIMUM_UPPER_BOUND;
    
    protected double lowerBound;
    protected double upperBound;
    protected AxisStyle style;
    protected int majorTicIndex = 0;
    protected int minorTicIndex = 0;
    protected double firstMajorTic;
    protected double firstMinorTic;
    protected int minorTicsPerInterval;
    
    public double getUpperBound(){return upperBound;}
    public abstract double setUpperBound(double ub); //will return the upper bound
    
    public double getLowerBound(){return lowerBound;}
    public abstract double setLowerBound(double lb); //will return the lower bound
    
    public double getRange(){return upperBound - lowerBound;}
    public boolean isInRange(double val){return  !( (val>upperBound) || (val<lowerBound) );}
  
    public int getMinorTicsPerInterval(){return minorTicsPerInterval;}
    public abstract void setMinorTicsPerInterval(int arg);

    public static final double getMinimumLowerBound()
    {
        return MINIMUM_LOWER_BOUND;
    }
    public static final double getMaximumUpperBound()
    {
        return MAXIMUM_UPPER_BOUND;
    }
    public abstract double getNextMajorTicMark();
    public abstract boolean hasNextMajorTicMark();
    public abstract double getNextMinorTicMark();
    public abstract boolean hasNextMinorTicMark();
    public abstract double transform(double x);
    public abstract boolean hasCustomMajorTicLabels();
    public abstract AttributedString getCurrentCustomMajorTicLabel();
    public abstract boolean hasCustomMinorTicLabels();
    public abstract AttributedString getCurrentCustomMinorTicLabel();
}
