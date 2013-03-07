package com.analyticobjects.matchpoint.chart;

import java.awt.*;
import java.util.*;
import java.text.*;
import java.awt.font.*;
import java.awt.geom.*;
import com.analyticobjects.matchpoint.numerics.*;
/**
 *
 * @author Joel
 */
public class Axes2D
{
    private Chart chart;
    private Backpane backpane;
    private Axis xAxis, yAxis;
    private AxisStyle xAxisStyle, yAxisStyle;
    private AxisScale xAxisScale, yAxisScale;
    private DataStructure dataStructure;
    private Color axesColor;
    private DecimalFormat formatter;
    private String tempStr;
    
    /**
     * Creates a new instance of Axes2D
     */
    public Axes2D(Chart aChart)
    {
        chart = aChart;
        backpane = chart.getBackpane();
        dataStructure = DataStructure.getInstance();
        yAxis = new Axis(dataStructure.getDependantVariable());
        xAxis = new Axis(dataStructure.getIndependantVariable(0));
        yAxisStyle = yAxis.getStyle();
        xAxisStyle = xAxis.getStyle();
        yAxisScale = yAxis.getScale();
        xAxisScale = xAxis.getScale();
        axesColor = Color.BLACK;
        formatter = new DecimalFormat();
        formatter.setMaximumFractionDigits(2);
        formatter.setMaximumIntegerDigits(4);
    }
    
    public Axis getXAxis(){return xAxis;}
    public Axis getYAxis(){return yAxis;}
    
    
    public void draw(Graphics2D g)
    {
        drawTics(g);
        g.setColor(axesColor);
        g.drawRect(backpane.getLeftInset(),backpane.getTopInset(),backpane.getBackpaneWidth(),backpane.getBackpaneHeight());
        g.setColor(Color.BLACK);
        g.setFont(yAxisStyle.getAxisFont());
        int z = g.getFontMetrics().stringWidth(dataStructure.getDependantVariable().getName());
        g.drawString(dataStructure.getDependantVariable().getName(),4*backpane.getLeftInset()/10-z,backpane.getTopInset() + backpane.getBackpaneHeight()/2);
        g.setFont(xAxisStyle.getAxisFont());
        g.drawString(dataStructure.getIndependantVariable(0).getName(),backpane.getHorizontalMiddle(),backpane.getTopInset() + backpane.getBackpaneHeight() + 7*backpane.getBottomInset()/10);
    }
    
    public void drawTics(Graphics2D g)
    {
        drawXTics(g);
        drawYTics(g);
    }
    
    public void drawXTics(Graphics2D g)
    {
        g.setFont(xAxisStyle.getAxisFont());
        if(!xAxisStyle.isMajorTicsEnabled()){return;}
        int y = backpane.getTopInset()+backpane.getBackpaneHeight();
        double x;
        AttributedString aTempStr;
        while(xAxisScale.hasNextMajorTicMark())
        {
            x = xAxisScale.getNextMajorTicMark();
            g.setColor(xAxisStyle.getMajorTicsColor());
            g.drawLine((int)transformXToDraw(x),y,(int)transformXToDraw(x),y+xAxisStyle.getMajorTicLength());
            g.setColor(Color.BLACK);
            if(!xAxisScale.hasCustomMajorTicLabels())
            {
                tempStr = formatter.format(x);
                int width = g.getFontMetrics().stringWidth(tempStr);
                int height = g.getFontMetrics().getHeight();
                g.drawString(tempStr,(int)(transformXToDraw(x)-(int)((double)width/2.0)),y+xAxisStyle.getMajorTicLength()+(int)(height*0.8));
            }
            else
            {
                aTempStr = xAxisScale.getCurrentCustomMajorTicLabel();
                AttributedCharacterIterator iter = aTempStr.getIterator();
                Rectangle2D r = g.getFontMetrics().getStringBounds(iter,0,iter.getEndIndex(),g);
                g.drawString(iter,(int)(transformXToDraw(x)-r.getWidth()/4),y+xAxisStyle.getMajorTicLength()+(int)(r.getHeight()*1.2));
            }
        }
        if(!xAxisStyle.isMinorTicsEnabled()){return;}
        while(xAxisScale.hasNextMinorTicMark())
        {
            x = xAxisScale.getNextMinorTicMark();
            g.setColor(xAxisStyle.getMinorTicsColor());
            g.drawLine((int)transformXToDraw(x),y,(int)transformXToDraw(x),y+xAxisStyle.getMinorTicLength());
        }
    }
    
    public void drawYTics(Graphics2D g)
    {
        g.setFont(yAxisStyle.getAxisFont());
        if(!yAxisStyle.isMajorTicsEnabled()){return;}
        int x = backpane.getLeftInset();
        double y;
        AttributedString aTempStr;
        while(yAxisScale.hasNextMajorTicMark())
        {
            y = yAxisScale.getNextMajorTicMark();
            g.setColor(yAxisStyle.getMajorTicsColor());
            g.drawLine(x,(int)transformYToDraw(y),x-yAxisStyle.getMajorTicLength(),(int)transformYToDraw(y));
            g.setColor(Color.BLACK);
            if(!yAxisScale.hasCustomMajorTicLabels())
            {
                tempStr = formatter.format(y);
                int width = g.getFontMetrics().stringWidth(tempStr);
                int height = g.getFontMetrics().getHeight();
                g.drawString(tempStr,x-yAxisStyle.getMajorTicLength()-(int)(width*1.2),(int)transformYToDraw(y)+(int)((double)height/4.0));
            }
            else
            {
                aTempStr = yAxisScale.getCurrentCustomMajorTicLabel();
                AttributedCharacterIterator iter = aTempStr.getIterator();
                Rectangle2D r = g.getFontMetrics().getStringBounds(iter,0,iter.getEndIndex(),g);
                g.drawString(iter,x-yAxisStyle.getMajorTicLength()-(int)r.getWidth(),(int)transformYToDraw(y)+(int)(r.getHeight()/4.0));
            }
        }
        if(!yAxisStyle.isMinorTicsEnabled()){return;}
        while(yAxisScale.hasNextMinorTicMark())
        {
            y = yAxisScale.getNextMinorTicMark();
            g.setColor(yAxisStyle.getMinorTicsColor());
            g.drawLine(x,(int)transformYToDraw(y),x-yAxisStyle.getMinorTicLength(),(int)transformYToDraw(y));
        }
    }
    
    
    public double transformXToDraw(double x)
    {
        x = xAxisScale.transform(x);
        double tlb = xAxisScale.transform(xAxisScale.getLowerBound());
        double tub = xAxisScale.transform(xAxisScale.getUpperBound());
        double xg = backpane.getLeftInset() + (x - tlb)*(double)backpane.getBackpaneWidth()/(tub - tlb);
        return xg;
    }
    
    public double transformYToDraw(double y)
    {
        y = yAxisScale.transform(y);
        double tlb = yAxisScale.transform(yAxisScale.getLowerBound());
        double tub = yAxisScale.transform(yAxisScale.getUpperBound());
        double yg = backpane.getTopInset() + backpane.getBackpaneHeight() - (y - tlb)*(double)backpane.getBackpaneHeight()/(tub - tlb);
        return yg;
    }
    
}
