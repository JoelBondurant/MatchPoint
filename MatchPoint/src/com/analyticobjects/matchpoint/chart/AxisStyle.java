package com.analyticobjects.matchpoint.chart;

import java.awt.*;

/**
 *
 * @author Joel
 */
public class AxisStyle
{

    private Color lineColor;
    private Stroke lineStroke;
    private boolean majorTicsEnabled, minorTicsEnabled;
    private Color minorTicsColor, majorTicsColor;
    private int majorTicLength, minorTicLength;
    private Font axisFont;
    
    /**
     * Creates a new instance of AxisStyle
     */
    public AxisStyle()
    {
        lineColor = Color.BLACK;
        lineStroke = new BasicStroke();
        majorTicsEnabled = true;
        minorTicsEnabled = true;
        minorTicsColor = Color.DARK_GRAY;
        majorTicsColor = Color.BLACK;
        majorTicLength = 8;
        minorTicLength = 4;
        axisFont = new Font("Serif", Font.PLAIN, 16);
    }
    
   
    public Color getLineColor(){return lineColor;}
    public void setLineColor(Color aColor){this.lineColor = aColor;}
    
    public Stroke getLineStroke(){return lineStroke;}
    public void setLineStroke(Stroke aStroke){this.lineStroke = aStroke;}
    
    public boolean isMajorTicsEnabled(){return majorTicsEnabled;}
    public void setMajorTicsEnabled(boolean arg){this.majorTicsEnabled = arg;}
    
    public boolean isMinorTicsEnabled(){return minorTicsEnabled;}
    public void setMinorTicsEnabled(boolean arg){this.minorTicsEnabled = arg;}
    
    public int getMajorTicLength(){return majorTicLength;}
    public void setMajorTicLength(int arg){this.majorTicLength = arg;}
    
    public int getMinorTicLength(){return minorTicLength;}
    public void setMinorTicLength(int arg){this.minorTicLength = arg;}
    
    public Color getMinorTicsColor(){return minorTicsColor;}
    public void setMinorTicsColor(Color arg){this.minorTicsColor = arg;}
    
    public Color getMajorTicsColor(){return majorTicsColor;}
    public void setMajorTicsColor(Color arg){this.majorTicsColor = arg;}
    
    public Font getAxisFont(){return axisFont;}
    public void setAxisFont(Font arg){this.axisFont = arg;}
}
