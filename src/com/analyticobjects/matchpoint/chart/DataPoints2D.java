package com.analyticobjects.matchpoint.chart;


import com.analyticobjects.matchpoint.numerics.*;
import java.awt.*;
import java.awt.geom.*;
/**
 *
 * @author Joel
 * @version 1.0, 09/24/2006
 */
public class DataPoints2D
{
    private Chart chart;
    private DataPoints2DStyle style;
    private DoubleVariable x, y;
    private Backpane backpane;
    private AxisScale xAxisScale, yAxisScale;
    private Axes2D axes;
    
    /**
     * Creates a new instance of DataPoints2D
     */
    public DataPoints2D(Chart chart)
    {
        this.chart = chart;
        style = new DataPoints2DStyle();
        backpane = chart.getBackpane();
        xAxisScale = chart.getAxes().getXAxis().getScale();
        yAxisScale = chart.getAxes().getYAxis().getScale();
        x = (DoubleVariable)DataStructure.getInstance().getIndependantVariable(0);
        y = (DoubleVariable)DataStructure.getInstance().getDependantVariable();
        axes = chart.getAxes();
    }
    
    public void draw(Graphics2D g)
    {
        g.setClip(backpane.getLeftInset()+1,backpane.getTopInset()+1,backpane.getBackpaneWidth()-1,backpane.getBackpaneHeight()-1);
        int size = Math.min(x.getSize(),y.getSize());
        double xi, yi;
        double xg, yg;
        for(int i = 0; i<size; i++)
        {
            xi = x.get(i);
            yi = y.get(i);
            if(!(xAxisScale.isInRange(xi)) || !(yAxisScale.isInRange(yi))){continue;}
            xg = axes.transformXToDraw(xi);
            yg = axes.transformYToDraw(yi);
            xg -= style.getSymbol().getWidth()/2.0;
            yg -= style.getSymbol().getHeight()/2.0;
            g.translate((int)xg,(int)yg);
            g.setPaint(style.getSymbolFillPaint());
            g.fill(style.getSymbol());
            g.setColor(style.getSymbolBorderColor());
            g.draw(style.getSymbol());
            g.translate(-(int)xg,-(int)yg);
        }
        g.setClip(0,0,Integer.MAX_VALUE,Integer.MAX_VALUE);
    }
    
    public DataPoints2DStyle getStyle()
    {
        return style;
    }
    
}
