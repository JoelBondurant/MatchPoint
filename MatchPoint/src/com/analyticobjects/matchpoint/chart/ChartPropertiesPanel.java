package com.analyticobjects.matchpoint.chart;

import javax.swing.*;

/**
 *
 * @author Joel
 * @version 1.0, 10/15/2006
 */
public abstract class ChartPropertiesPanel extends JPanel
{
    
    protected String title;
    
    /** Creates a new instance of ChartPropertiesPanel */
    public ChartPropertiesPanel()
    {
    }
    
    public String getTitle(){return this.title;}
    
    public abstract void apply();
    
}
