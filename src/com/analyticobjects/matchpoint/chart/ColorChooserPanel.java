package com.analyticobjects.matchpoint.chart;

import com.analyticobjects.matchpoint.Application;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author Joel
 * @version 1.0, 10/15/2006
 */
public class ColorChooserPanel extends JPanel
{
    
    private Color color;
    private JColorChooser chooser;
    private Dimension size;
    private boolean changed;

    /** Creates a new instance of ColorChooserPanel */
    public ColorChooserPanel(Color c)
    {
        this.color = c;
        this.setBackground(c);
        this.chooser = new JColorChooser(c);
        this.changed = false;
        initGUI();
    }
    
    private void initGUI()
    {
        size = new Dimension(30,20);
        this.setMinimumSize(size);
        this.setMaximumSize(size);
        this.setPreferredSize(size);
        this.setBorder(BorderFactory.createLoweredBevelBorder());
        this.setToolTipText("Double click here to edit the color.");
        this.addMouseListener(new PaintChooserAction());
    }
    
    public boolean isChanged(){return this.changed;}
    public void setChanged(boolean b){this.changed = b;}
    
    public void setColor(Color c)
    {
        this.color = c;
        this.setBackground(c);
        this.changed = true;
    }
    
    public Color getColor(){return this.color;}
    
    
    private class PaintChooserAction extends MouseAdapter
    { 
        private static final String title = "Color Chooser";
        public void mouseClicked(MouseEvent me)
        {
            if(me.getClickCount()<2){return;}
            Color c = chooser.showDialog(ColorChooserPanel.this,title,ColorChooserPanel.this.color);
            if(c == null){return;}
            ColorChooserPanel.this.setColor(c);
        }
    }
}
