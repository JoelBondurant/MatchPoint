package com.analyticobjects.matchpoint.chart;

import java.text.*;
import java.awt.font.*;
import java.awt.*;
/**
 *
 * @author Joel
 * @version 1.0, 10/03/06
 */
public class LogEAxisScale extends AxisScale
{

    private boolean skip = false;
    
    /** Creates a new instance of LinearAxisScale */
    public LogEAxisScale(AxisStyle style)
    {
        this.style = style;
        this.lowerBound = 1.0/Math.E;
        this.upperBound = Math.pow(Math.E,5.0);
        this.minorTicsPerInterval = 4;
        MINIMUM_LOWER_BOUND = 0.0 + 1.0/Double.MAX_VALUE;
        MAXIMUM_UPPER_BOUND = Double.MAX_VALUE;
    }
    
    public double transform(double x){return Math.log(x);}
    
    private void findFirstMajorTic()
    {
        this.firstMajorTic = Math.pow(Math.E,Math.floor(Math.log(this.lowerBound)));
    }
    
    private void findFirstMinorTic(){}
    
    public boolean hasNextMajorTicMark()
    {
        if(getNextMajorTicMark()<=this.upperBound)
        {
            this.majorTicIndex--;
            return true;
        }
        this.majorTicIndex = 0;
        return false;
    }
    
    public double getNextMajorTicMark()
    {
        if(this.majorTicIndex == 0){this.findFirstMajorTic();}
        double tic = this.firstMajorTic*Math.pow(Math.E,this.majorTicIndex);
        this.majorTicIndex++;
        return tic;
    }
    
    public boolean hasNextMinorTicMark()
    {
        return false;
    }
    
    private double previousMinorTicMark;
    public double getNextMinorTicMark()
    {
        return this.MINIMUM_LOWER_BOUND;
    }
    
    public void setMinorTicsPerInterval(int arg)
    {
        if(!(arg > 0.0))
        {
            style.setMinorTicsEnabled(false);
            this.minorTicsPerInterval = 0;
            return;
        }
        this.minorTicsPerInterval = arg;
    }
    
    public double setLowerBound(double arg)
    {
        if(!(arg > this.MINIMUM_LOWER_BOUND))
        {
            this.lowerBound = this.MINIMUM_LOWER_BOUND;
            return this.lowerBound;
        }
        if(!(arg < this.upperBound))
        {
            return this.lowerBound;
        }
        this.lowerBound = arg;
        return this.lowerBound;
    }
    
    public double setUpperBound(double arg)
    {
        if(!(arg < this.MAXIMUM_UPPER_BOUND))
        {
            this.upperBound = this.MAXIMUM_UPPER_BOUND;
            return this.upperBound;
        }
        if(!(arg > this.lowerBound))
        {
            return this.upperBound;
        }
        this.upperBound = arg;
        return this.upperBound;  
    }
    
    public boolean hasCustomMajorTicLabels(){return true;}
    public AttributedString getCurrentCustomMajorTicLabel()
    {
        if(this.majorTicIndex == 0){this.findFirstMajorTic();}
        double tic = this.firstMajorTic*Math.pow(Math.E,this.majorTicIndex);
        int n = (int)Math.floor(Math.log(tic));
        String str = "e" + (n-1);
        AttributedString astr = new AttributedString(str);
        astr.addAttribute(TextAttribute.SIZE,new Float(18f),0,1);
        astr.addAttribute(TextAttribute.SIZE,new Float(14f),1,str.length());
        astr.addAttribute(TextAttribute.WEIGHT,TextAttribute.WEIGHT_DEMIBOLD);
        astr.addAttribute(TextAttribute.SUPERSCRIPT,TextAttribute.SUPERSCRIPT_SUPER,1,str.length());
        return astr;
    }
    public boolean hasCustomMinorTicLabels(){return false;}
    public AttributedString getCurrentCustomMinorTicLabel(){return null;}
}
