package com.analyticobjects.matchpoint.chart;

import com.analyticobjects.matchpoint.*;
import com.analyticobjects.matchpoint.gui.*;
import com.analyticobjects.matchpoint.icons.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author Joel
 * @version 1.0, 10/10/2006
 */
public class ChartPropertiesDialog extends JDialog
{
    private Vector<ChartPropertiesPanel> panels;
    private Chart chart;
    
    /** Creates a new instance of ChartPropertiesDialog */
    public ChartPropertiesDialog()
    {
        this.setTitle(Strings.chartPropertiesDialogTitle);
        ((Frame)this.getOwner()).setIconImage(new ImageIcon(IconClassLoader.class.getResource("logo16.png")).getImage());
        this.setSize(250,400);
        this.setModal(false);
        this.chart = Application.getMainFrame().getChart();
        this.panels = new Vector<ChartPropertiesPanel>(10);
        panels.add(new BackgroundPanel());
        panels.add(new DataPoints2DPanel());
        panels.add(new Axes2DPanel());
        this.initGUI();
    }

    public void setVisible(boolean b)
    {
        this.setLocationRelativeTo(Application.getMainFrame());
        super.setVisible(b);
    }
    
    private void initGUI()
    {
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();
        for(ChartPropertiesPanel panel: panels)
        {
            tabbedPane.add(panel.getTitle(), panel);
        }
        container.add(tabbedPane, BorderLayout.CENTER);
        ButtonPanel buttonPanel = new ButtonPanel();
        container.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private class ButtonPanel extends JPanel
    {
        private JButton cancelButton = new JButton(Strings.cancel);
        private JButton applyButton = new JButton(Strings.apply);
        private JButton okButton = new JButton(Strings.ok);
        private ChartPropertiesDialog dialog;
        
        public ButtonPanel()
        {
            dialog = ChartPropertiesDialog.this;
            this.initGUI();
        }
        
        private void initGUI()
        {
            this.add(cancelButton);
            this.add(applyButton);
            this.add(okButton);
            this.cancelButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                    dialog.setVisible(false);
                }
            });
            this.applyButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                    for(ChartPropertiesPanel panel: panels)
                    {
                        panel.apply();
                    }
                    Application.getMainFrame().repaint(); //chart.repaint(); etc... doesn't work.
                }
            });
            this.okButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                    dialog.setVisible(false);
                    for(ChartPropertiesPanel panel: panels)
                    {
                        panel.apply();
                    }
                    Application.getMainFrame().repaint();
                }
            });
        }

    }
}
