package com.analyticobjects.matchpoint.chart;

/**
 *
 * @author Joel
 * @version 1.0, 09/30/06
 */
public class Log10AxisScale extends AxisScale
{

    private boolean skip = false;
    
    /** Creates a new instance of LinearAxisScale */
    public Log10AxisScale(AxisStyle style)
    {
        this.style = style;
        this.lowerBound = 0.1;
        this.upperBound = 1000.0;
        this.minorTicsPerInterval = 4;
        MINIMUM_LOWER_BOUND = 0.0 + 1.0/Double.MAX_VALUE;
        MAXIMUM_UPPER_BOUND = Double.MAX_VALUE;
    }
    
    public double transform(double x){return Math.log10(x);}
    
    private void findFirstMajorTic()
    {
        this.firstMajorTic = Math.pow(10.0,Math.floor(Math.log10(this.lowerBound)));
    }
    
    private void findFirstMinorTic()
    {
        findFirstMajorTic();
        double tic = this.firstMajorTic;
        if( (this.firstMajorTic == this.lowerBound) || (0.8*this.firstMajorTic < this.lowerBound) )
        {
            this.firstMinorTic = 2.0*this.firstMajorTic;
            return;
        }
        while(tic > this.lowerBound)
        {
            tic -= 2.0*this.firstMajorTic;
        }
        this.firstMinorTic = (tic + 2.0*this.firstMajorTic);
    }
    
    
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
        double tic = this.firstMajorTic*Math.pow(10.0,this.majorTicIndex);
        this.majorTicIndex++;
        return tic;
    }
    
    public boolean hasNextMinorTicMark()
    {
        double temp = this.previousMinorTicMark;
        if(getNextMinorTicMark()<=this.upperBound)
        {
            this.minorTicIndex--;
            if(skip){this.minorTicIndex--;}
            this.previousMinorTicMark = temp;
            return true;
        }
        this.minorTicIndex = 0;
        this.previousMinorTicMark = temp;
        return false;
    }
    
    private double previousMinorTicMark;
    public double getNextMinorTicMark()
    {
        skip = false;
        if(this.minorTicIndex == 0)
        {
            this.findFirstMinorTic();
            previousMinorTicMark = this.firstMinorTic;
            this.minorTicIndex++;
            return this.firstMinorTic;
        }
        double n = Math.floor(Math.log10(this.previousMinorTicMark));
        double tic = this.previousMinorTicMark + 1.0*Math.pow(10.0,n);
        if(  Math.abs(tic - Math.pow(10.0, n + 1.0)) < Math.pow(10.0, n - 3.0)  )
        {
            tic = 2.0*Math.pow(10.0, n + 1.0);
            skip = true;
            this.minorTicIndex++;
        }
        this.minorTicIndex++;
        this.previousMinorTicMark = tic;
        return tic;
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
    
    public boolean hasCustomMajorTicLabels(){return false;}
    public java.text.AttributedString getCurrentCustomMajorTicLabel(){return null;}
    public boolean hasCustomMinorTicLabels(){return false;}
    public java.text.AttributedString getCurrentCustomMinorTicLabel(){return null;}
}
