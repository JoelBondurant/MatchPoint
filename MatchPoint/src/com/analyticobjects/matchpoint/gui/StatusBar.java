package com.analyticobjects.matchpoint.gui;

import com.analyticobjects.matchpoint.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.text.*;
/**
 *
 * @author Joel
 * @version 1.0, 10/08/2006
 */
public class StatusBar extends JPanel implements ActionListener
{
    
    private JLabel timeLabel;
    private JLabel usedMemoryLabel;
    private JProgressBar progressBar;
    private DecimalFormat decimalFormatter = new DecimalFormat("#,###,##0.0");
    private Runtime runtime;
    private static int height = 21;
    
    /** Creates a new instance of StatusBar */
    public StatusBar()
    {
        runtime = Runtime.getRuntime();
        this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        this.setPreferredSize(new Dimension(5000,height));
        this.setBackground(SystemColor.control);
        Dimension dim1 = new Dimension(150,height);
        Dimension dim2 = new Dimension(90,height);
        Dimension dim3 = new Dimension(116,height);
        UIManager.put("ProgressBar.selectionBackground", new Color(150,30,30));  //text color
	UIManager.put("ProgressBar.selectionForeground", Color.WHITE);  //text color
        progressBar = new JProgressBar();
        progressBar.setBackground(SystemColor.control); //background
        progressBar.setForeground(Color.DARK_GRAY);  //foreground
        progressBar.setIndeterminate(false);
        progressBar.setString("Ready");
        progressBar.setStringPainted(true);
        progressBar.setMaximumSize(dim1);
        progressBar.setBorder(BorderFactory.createLoweredBevelBorder());
        progressBar.setMinimum(0);
        progressBar.setMaximum(10);
        progressBar.setValue(10);
        progressBar.setOpaque(false);
        //Font textFont = UIManager.getLookAndFeel().getDefaults().getFont(new JMenuItem("Text"));
        Font textFont = Font.getFont(Font.SANS_SERIF);
        timeLabel = new JLabel("");
        timeLabel.setMaximumSize(dim2);
        timeLabel.setPreferredSize(dim2);
        timeLabel.setMinimumSize(dim2);
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setFont(textFont);
        timeLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        usedMemoryLabel = new JLabel(usedMemory());
        usedMemoryLabel.setMaximumSize(dim3);
        usedMemoryLabel.setPreferredSize(dim3);
        usedMemoryLabel.setMinimumSize(dim3);
        usedMemoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        usedMemoryLabel.setFont(textFont);
        usedMemoryLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        this.setAlignmentY(JComponent.CENTER_ALIGNMENT);
        this.add(Box.createHorizontalStrut(3));
        this.add(progressBar);
        this.add(timeLabel);
        this.add(Box.createHorizontalGlue());
        this.add(usedMemoryLabel);
        this.add(Box.createHorizontalStrut(3));
        Timer memoryTimer = new Timer(2000,this);
        memoryTimer.setCoalesce(true);
        memoryTimer.setRepeats(true);
        memoryTimer.start();
        //this.add(Box.createHorizontalGlue());
    }
    
    public JProgressBar getProgressBar(){return progressBar;}
    
    public void setTimeLabel(String arg)
    {
        timeLabel.setText(arg);
    }
    
    public void actionPerformed(ActionEvent ae)
    {
        usedMemoryLabel.setText(usedMemory());
    }
    
    private String usedMemory()
    {
        double usedMemory = ((double)(runtime.totalMemory() - runtime.freeMemory())) / (1024.0*1024.0);
        double totalMemory = ((double)runtime.totalMemory()) / (1024.0*1024.0);
        return decimalFormatter.format(usedMemory) + " / " + decimalFormatter.format(totalMemory) + " MB";
    }
    
}
