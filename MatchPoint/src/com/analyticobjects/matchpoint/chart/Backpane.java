package com.analyticobjects.matchpoint.chart;

import java.awt.*;
/**
 *
 * @author Joel
 */
public class Backpane
{
    private Chart chart;
    private BackpaneStyle backpaneStyle;
    private Color tempColor;
    private int[] insets = {70,80,120,80};//right, top, left, bottom
    
    
    /** Creates a new instance of Backpane */
    public Backpane(Chart aChart)
    {
        chart = aChart;
        backpaneStyle = new BackpaneStyle();
    }
    
    public void draw(Graphics2D g)
    {
        g.setColor(backpaneStyle.getBackpaneColor());
        g.fillRect(getLeftInset(),getTopInset(),getBackpaneWidth()+1,getBackpaneHeight()+1);
    }
    
    public BackpaneStyle getStyle(){return backpaneStyle;}
    
    public int getRightInset(){return insets[0];}
    public int getTopInset(){return insets[1];}
    public int getLeftInset(){return insets[2];}
    public int getBottomInset(){return insets[3];}
    public void setRightInset(int x){insets[0] = Math.max(x,0);}
    public void setTopInset(int x){insets[1] = Math.max(x,0);}
    public void setLeftInset(int x){insets[2] = Math.max(x,0);}
    public void setBottomInset(int x){insets[3] = Math.max(x,0);}
    public int getBackpaneWidth(){return chart.getWidth() - getRightInset() - getLeftInset();}
    public int getBackpaneHeight(){return chart.getHeight() - getTopInset() - getBottomInset();}
    public int getHorizontalMiddle(){return getLeftInset() + getBackpaneWidth()/2;}
    public int getVerticalMiddle(){return getTopInset() + getBackpaneHeight()/2;}
    
}
