package com.analyticobjects.matchpoint.chart;

import com.analyticobjects.matchpoint.*;
import com.analyticobjects.matchpoint.gui.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.plaf.basic.BasicSplitPaneUI;

/**
 * An class to represent a chart.
 *
 * @author Joel
 * @version 1.0, 09/17/2006
 */
public class Chart extends JPanel implements TableModelListener
{

    private Background background;
    private Backpane backpane;
    private Axes2D axes;
    private DataPoints2D data;
    private static Chart uniqueInstance;
    
    /** Creates a new instance of Chart */
    private Chart(int dimensionality)
    {
        DataTable.getInstance().getModel().addTableModelListener(this);
        this.setLayout(null);
        this.setMinimumSize(new Dimension(400,300));
        this.setPreferredSize(new Dimension(500000,500000));
        this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(5,5,5,5,SystemColor.control),BorderFactory.createLineBorder(SystemColor.control.darker())));
        background = new Background(this);
        backpane = new Backpane(this);
        axes = new Axes2D(this);
        data = new DataPoints2D(this);
        this.addMouseListener(new ChartPropertiesTrigger());
    }
    
    public static Chart getInstance()
    {
        if(uniqueInstance == null)
        {
            uniqueInstance = new Chart(2);
        }
        return uniqueInstance;
    }
      
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D)g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        if(this.getSize().height + 50 < this.getMinimumSize().height){return;}//don't draw a tiny squished chart
        if(this.getSize().width + 100 < this.getMinimumSize().width){return;}
        background.draw(g2D);
        backpane.draw(g2D);
        axes.draw(g2D);
        data.draw(g2D);
    }
    
    public void tableChanged(TableModelEvent tme)
    {
        //axes.setDefaults();
        this.repaint();
    }
    
    public Background getChartBackground(){return background;}
    public Backpane getBackpane(){return backpane;}
    public Axes2D getAxes(){return axes;}
    public DataPoints2D getDataPoints(){return data;}
    
    private class ChartPropertiesTrigger extends MouseAdapter
    { 
        private static final String cmd = "double click";
        public void mouseClicked(MouseEvent me)
        {
            if(me.getClickCount()<2){return;}
            ChartPropertiesAction.getInstance().actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,cmd));
        }
    }
}
