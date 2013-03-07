package com.analyticobjects.matchpoint.chart;

import java.awt.*;
/**
 *
 * @author Joel
 */
public class Background
{
    private BackgroundStyle backgroundStyle;
    private Chart chart;
    
    /** Creates a new instance of Background */
    public Background(Chart aChart)
    {
        chart = aChart;
        backgroundStyle = new BackgroundStyle();
    }
    
    
    public void draw(Graphics2D g)
    {
        g.setPaint(backgroundStyle.getBackgroundPaint());
        g.fillRect(0,0,chart.getWidth(),chart.getHeight());
        g.setPaint(Color.BLACK);
        g.setFont(backgroundStyle.getTitleFont());
        String title = backgroundStyle.getTitle();
        g.drawString(title ,chart.getBackpane().getHorizontalMiddle()-g.getFontMetrics().stringWidth(title)/2,chart.getBackpane().getTopInset()/2);
    }
    
    public BackgroundStyle getStyle(){return backgroundStyle;}
}
