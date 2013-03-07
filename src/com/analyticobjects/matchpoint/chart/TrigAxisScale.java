package com.analyticobjects.matchpoint.chart;

import java.text.*;
import java.awt.font.*;
import java.awt.*;
/**
 *
 * @author Joel
 * @version 1.0, 10/04/2006
 */
public class TrigAxisScale extends AxisScale
{
    protected double majorTicInterval;
    protected int minorTicsBeforeFirstMajor;
    protected boolean skip = false;
    protected double majorTicStart;
   
    public TrigAxisScale(AxisStyle style)
    {
        this.style = style;
        this.lowerBound = 0.0;
        this.upperBound = 10.0*Math.PI;
        this.majorTicInterval = Math.PI;
        this.majorTicStart = 0.0;
        this.minorTicsPerInterval = 3;
        LinearAxisScale.MINIMUM_LOWER_BOUND = Double.MIN_VALUE;
        LinearAxisScale.MAXIMUM_UPPER_BOUND = Double.MAX_VALUE;
    }
    
    public double transform(double x){return x;}
    
    protected void findFirstMajorTic()
    {
        double tic = this.majorTicStart;
        if(tic == this.lowerBound)
        {
            this.firstMajorTic = tic;
            return;
        }
        while(tic > this.lowerBound)
        {
            tic -= this.majorTicInterval;
        }
        tic += this.majorTicInterval;
        this.firstMajorTic = tic;
    }
    
    protected void findFirstMinorTic()
    {
        findFirstMajorTic();
        minorTicsBeforeFirstMajor = 0;
        double tic = this.firstMajorTic;
        double interval = this.majorTicInterval/((double)(this.minorTicsPerInterval + 1));
        while(tic > this.lowerBound)
        {
            tic -= interval;
            minorTicsBeforeFirstMajor++;
        }
        tic += interval;
        minorTicsBeforeFirstMajor--;
        this.firstMinorTic = tic;
    }
    
    
    public boolean hasNextMajorTicMark()
    {
        if(getNextMajorTicMark() < this.upperBound + this.majorTicInterval/1000.0)
        {
            this.majorTicIndex--;
            return true;
        }
        this.majorTicIndex = 0;
        return false;
    }
    
    public double getNextMajorTicMark()
    {
        if(this.majorTicIndex == 0){findFirstMajorTic();}
        double tic = this.firstMajorTic + ((double)this.majorTicIndex)*this.majorTicInterval;
        this.majorTicIndex++;
        return tic;
    }
    
    public boolean hasNextMinorTicMark()
    {
        if(getNextMinorTicMark() < (this.upperBound + this.majorTicInterval/1000.0))
        {
            this.minorTicIndex--;
            if(skip){this.minorTicIndex--;}
            return true;
        }
        this.minorTicIndex = 0;
        return false;
    }
    
    public double getNextMinorTicMark()
    {
        skip = false;
        if(this.minorTicIndex == 0){findFirstMinorTic();}
        double dx = (this.majorTicInterval/((double)(this.minorTicsPerInterval + 1)));
        double tic = this.firstMinorTic + ((double)this.minorTicIndex)*dx;
        if( (this.minorTicIndex + (this.minorTicsPerInterval + 1 - this.minorTicsBeforeFirstMajor)) % (this.minorTicsPerInterval + 1) == 0 )
        {
            tic += dx;
            this.minorTicIndex++;
            skip = true;
        }
        this.minorTicIndex++;
        return tic;
    }
    
    public double getMajorTicInterval(){return this.majorTicInterval;}
    public boolean setMajorTicInterval(double arg)
    {
        if(!(arg > 0.0)){return false;}
        this.majorTicInterval = arg;
        return true;
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
    
    public double getMajorTicStart(){return this.majorTicStart;}
    public boolean setMajorTicStart(double arg)
    {
        if((arg < this.lowerBound) || (arg >= this.upperBound)){return false;}
        this.majorTicStart = arg;
        return true;
    }
    
    public double setLowerBound(double arg)
    {
        if(arg < this.upperBound)
        {
            this.lowerBound = arg;
        }
        return this.lowerBound;
    }
    
    public double setUpperBound(double arg)
    {
        if(arg > this.lowerBound)
        {
            this.upperBound = arg;
        }
        return this.upperBound;
    }
    
    public boolean hasCustomMajorTicLabels(){return true;}
    private Font symbolFont = new Font("Serif",Font.PLAIN,16);
    public AttributedString getCurrentCustomMajorTicLabel()
    {
        if(this.majorTicIndex == 0){this.findFirstMajorTic();}
        double tic = this.firstMajorTic + ((double)this.majorTicIndex)*this.majorTicInterval;
        double m = tic / Math.PI;
        String str = ((int) m) + "\u03C0";
        AttributedString astr = new AttributedString(str);
        astr.addAttribute(TextAttribute.FONT,symbolFont,0,str.length());
        return astr;
    }
    public boolean hasCustomMinorTicLabels(){return false;}
    public java.text.AttributedString getCurrentCustomMinorTicLabel(){return null;}
    
}

