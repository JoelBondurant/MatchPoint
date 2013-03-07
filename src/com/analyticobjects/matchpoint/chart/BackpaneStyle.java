package com.analyticobjects.matchpoint.chart;

import java.awt.*;
/**
 *
 * @author Joel
 * @version 1.0, 09/17/2006
 */
public class BackpaneStyle
{
    
    private Color backpaneColor;
    
    /**
     * Creates a new instance of BackpaneStyle
     */
    public BackpaneStyle()
    {
        backpaneColor = SystemColor.control.darker();//new Color(255,245,200);
    }
    
    public Color getBackpaneColor()
    {
        return backpaneColor;
    }
    
    public void setBackpaneColor(Color aColor)
    {
        this.backpaneColor = aColor;
    }
    
}
