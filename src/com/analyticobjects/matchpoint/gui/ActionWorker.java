package com.analyticobjects.matchpoint.gui;

import com.analyticobjects.matchpoint.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.beans.*;
import com.analyticobjects.matchpoint.gui.*;
import java.util.*;
import java.text.*;
/**
 *
 * @author Joel
 * @version 1.0, 12/17/2006
 */
public class ActionWorker extends SwingWorker<String, Object> implements PropertyChangeListener
{
    private static boolean enabled = true;
    private ActionListenerWorker actionObject;
    private ActionEvent ae;
    private String startMessage;
    private String finishMessage;
    private StatusBar statusBar;
    private JProgressBar progressBar;
    private MainFrame mainFrame;
    private long startTime, finishTime;
    private static FieldPosition fieldPosition = new FieldPosition(NumberFormat.INTEGER_FIELD);
    private static DecimalFormat decimalFormatter = new DecimalFormat("#,###,##0.000");
    private static StringBuffer elapsedTimeStringBuffer = new StringBuffer();
    private static Dimension minSize = new Dimension(50,20);
    
    synchronized public static boolean isEnabled(){return enabled;}
    
    /** Creates a new instance of ActionWorker */
    public ActionWorker(ActionListenerWorker actionObject, ActionEvent ae, String startMessage, String finishMessage)
    {
        enabled = false;
        this.actionObject = actionObject;
        this.ae = ae;
        this.startMessage = startMessage;
        this.finishMessage = finishMessage;
        mainFrame = Application.getMainFrame();
        statusBar = mainFrame.getStatusBar();
        progressBar = statusBar.getProgressBar();
        this.addPropertyChangeListener(this);
    }
    
    public String doInBackground()
    {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        startTime = System.nanoTime();
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                progressBar.setValue(0);
                progressBar.setString(startMessage);                
            }
        });
        actionObject.actionPerformedInWorkerThread(ae);
        return finishMessage;
    }
    
    @Override
    protected void done()
    {
        finishTime = System.nanoTime();
        double elapsedTime = (double)(finishTime - startTime)/1.0E9;
        elapsedTimeStringBuffer.setLength(0);
        decimalFormatter.format(elapsedTime, elapsedTimeStringBuffer, fieldPosition);
        statusBar.setTimeLabel(" " + elapsedTimeStringBuffer + " sec.");
        actionObject.actionPerformedWhenDone(ae);
        setActionWorkerProgress(10);
        progressBar.setString(finishMessage);
        progressBar.setIndeterminate(false);
        enabled = true;
    }
    
    synchronized public void setActionWorkerProgress(int progress)
    {
        setProgress(progress);
    }
    
    synchronized public void propertyChange(PropertyChangeEvent evt)
    {
        progressBar.setValue(this.getProgress());
    }
    
}
