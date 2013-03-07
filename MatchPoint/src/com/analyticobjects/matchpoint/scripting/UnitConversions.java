package com.analyticobjects.matchpoint.scripting;

/**
 *
 * @author Joel
 * @version 1.0, 12/24/2006
 */
public class UnitConversions
{
    
    public static final double INCHES_PER_FOOT = 12.0;
    public static final double INCHES_PER_YARD = 36.0;
    public static final double FEET_PER_INCH = 1/12.0;
    public static final double FEET_PER_YARD = 3.0;
    public static final double YARDS_PER_INCH = 1/36.0;
    public static final double YARDS_PER_FOOT = 1/3.0;
    
    
    public static double inchesToFeet(double inches){return inches*FEET_PER_INCH;}
    public static double inchesToYards(double inches){return inches*YARDS_PER_INCH;}
    public static double feetToInches(double feet){return feet*INCHES_PER_FOOT;}
    public static double feetToYards(double feet){return feet*YARDS_PER_FOOT;}
    public static double yardsToInches(double yards){return yards*INCHES_PER_YARD;}
    public static double yardsToFeet(double yards){return yards*FEET_PER_YARD;}
    
    public String toString(){return "UnitConversions";}
    
}
