package com.analyticobjects.matchpoint.chart;

import com.analyticobjects.matchpoint.Application;
import com.analyticobjects.matchpoint.gui.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author Joel
 * @version 1.0, 10/14/2006
 */
public class BackgroundPanel extends ChartPropertiesPanel
{
    private JTextField titleField;
    private JFontComboBox titleFontList;
    private ColorChooserPanel backgroundColorChooser, backpaneColorChooser;
    private BackgroundStyle backgroundStyle;
    private BackpaneStyle backpaneStyle;
    private Backpane backpane;
    private JSpinner topInsetSpinner, leftInsetSpinner, bottomInsetSpinner, rightInsetSpinner;
    private float titleFontSize;
    /**
     * Creates a new instance of BackgroundPanel
     */
    public BackgroundPanel()
    {
        backgroundStyle = Application.getMainFrame().getChart().getChartBackground().getStyle();
        backpaneStyle = Application.getMainFrame().getChart().getBackpane().getStyle();
        backpane = Application.getMainFrame().getChart().getBackpane();
        this.title = "Background";
        
        this.titleField = new JTextField(20);
        this.titleField.setText(backgroundStyle.getTitle());
        this.add(new JLabel("Chart Title: "));
        this.add(titleField);
        
        titleFontList = new JFontComboBox();
        titleFontList.setSelectedItem(backgroundStyle.getTitleFont().getFontName());
        titleFontSize = (float)backgroundStyle.getTitleFont().getSize();
        this.add(new JLabel("Title Font:"));
        this.add(titleFontList);
        
        this.add(new JLabel("Background Color: "));
        Paint p = backgroundStyle.getBackgroundPaint();
        if(!(p instanceof Color)){p = Color.WHITE;}
        this.backgroundColorChooser = new ColorChooserPanel((Color) p);
        this.add(backgroundColorChooser);
        
        this.add(new JLabel("Backpane Color: "));
        Color c = backpaneStyle.getBackpaneColor();
        this.backpaneColorChooser = new ColorChooserPanel(c);
        this.add(backpaneColorChooser);
        
        //this.add(new JLabel("Insets: "));
        JPanel insetsPanel = new JPanel();
        insetsPanel.setLayout(new BorderLayout());
        TitledBorder insetsGroupBorder = BorderFactory.createTitledBorder("Insets");
        insetsGroupBorder.setTitleColor(Color.BLACK);
        insetsPanel.setBorder(insetsGroupBorder);
        Dimension spinnerSize = new Dimension(30,50);
        topInsetSpinner = new JSpinner(new SpinnerNumberModel(backpane.getTopInset(),50,5000,1));
        topInsetSpinner.setMaximumSize(spinnerSize);
        insetsPanel.add(topInsetSpinner,BorderLayout.NORTH);
        leftInsetSpinner = new JSpinner(new SpinnerNumberModel(backpane.getLeftInset(),50,5000,1));
        insetsPanel.add(leftInsetSpinner,BorderLayout.WEST);
        bottomInsetSpinner = new JSpinner(new SpinnerNumberModel(backpane.getBottomInset(),50,5000,1));
        insetsPanel.add(bottomInsetSpinner,BorderLayout.SOUTH);
        rightInsetSpinner = new JSpinner(new SpinnerNumberModel(backpane.getRightInset(),50,5000,1));
        insetsPanel.add(rightInsetSpinner,BorderLayout.EAST);
        this.add(insetsPanel);
        
    }
    
  
    public void apply()
    {
        backgroundStyle = Application.getMainFrame().getChart().getChartBackground().getStyle();
        backgroundStyle.setTitle(titleField.getText());
        backgroundStyle.setTitleFont(titleFontList.getSelectedFont().deriveFont(titleFontSize));
        if(this.backgroundColorChooser.isChanged())
        {
            backgroundStyle.setBackgroundPaint(backgroundColorChooser.getColor());
        }
        backpane.getStyle().setBackpaneColor(backpaneColorChooser.getColor());
        backpane.setTopInset(((Integer)topInsetSpinner.getValue()).intValue());
        backpane.setLeftInset(((Integer)leftInsetSpinner.getValue()).intValue());
        backpane.setBottomInset(((Integer)bottomInsetSpinner.getValue()).intValue());
        backpane.setRightInset(((Integer)rightInsetSpinner.getValue()).intValue());
    }
    
}
