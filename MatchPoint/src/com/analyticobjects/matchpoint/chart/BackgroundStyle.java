package com.analyticobjects.matchpoint.chart;

import java.awt.*;
/**
 *
 * @author Joel
 */
public class BackgroundStyle
{
    private String title;
    private Font titleFont;
    private Paint backgroundPaint;
    
    /** Creates a new instance of BackgroundStyle */
    public BackgroundStyle()
    {
        backgroundPaint = new GradientPaint(0f,0f,SystemColor.control,0f,200f,Color.WHITE);
        title = "Chart Title";
        titleFont = new Font("Dialog",Font.BOLD, 20);
    }
    
    public Paint getBackgroundPaint(){return backgroundPaint;}
    public void setBackgroundPaint(Paint aPaint){backgroundPaint = aPaint;}
    
    public Font getTitleFont(){return titleFont;}
    public void setTitleFont(Font aFont){this.titleFont = aFont;}
    
    public String getTitle(){return this.title;}
    public void setTitle(String aTitle){this.title = aTitle;}
    
}
