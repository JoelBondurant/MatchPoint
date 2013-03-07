package com.analyticobjects.matchpoint.scripting;

import com.analyticobjects.matchpoint.*;
import java.awt.*;
import javax.swing.*;
import com.analyticobjects.matchpoint.gui.*;
import java.util.*;
import java.text.*;
/**
 *
 * @author Joel
 * @version 1.0, 12/17/2006
 */
public class ScriptWorker extends SwingWorker<Object, Object>
{
    private static boolean enabled = true;
    private String script;
    private ScriptManager scriptManager;
    private JLabel scriptMessage;
    private Object returnObject;
    private StatusBar statusBar;
    private JProgressBar progressBar;
    private MainFrame mainFrame;
    private long startTime, finishTime;
    private static FieldPosition fieldPosition = new FieldPosition(NumberFormat.INTEGER_FIELD);
    private static DecimalFormat decimalFormatter = new DecimalFormat("#,###,##0.000");
    private static StringBuffer elapsedTimeStringBuffer = new StringBuffer();
    private static Dimension minSize = new Dimension(50,20);
    
    synchronized public static boolean isEnabled(){return enabled;}
    
    /** Creates a new instance of ScriptWorker */
    public ScriptWorker(String script)
    {
        enabled = false;
        this.script = script;
        returnObject = "null";
        scriptManager = ScriptManager.getInstance();
        scriptMessage = ScriptToolBar.getInstance().getScriptMessage();
        mainFrame = Application.getMainFrame();
        statusBar = mainFrame.getStatusBar();
        progressBar = statusBar.getProgressBar();
    }
    
    public Object doInBackground()
    {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        startTime = System.nanoTime();
        scriptMessage.setText("");
        returnObject = scriptManager.execute(script);
        if(returnObject==null){returnObject = "null";}
        return returnObject;
    }
    
    @Override
    protected void done()
    {
        scriptMessage.setText(returnObject.toString());
        scriptMessage.setMinimumSize(minSize);
        ((DataTableModel)DataTable.getInstance().getModel()).fireTableStructureChanged();
        finishTime = System.nanoTime();
        double elapsedTime = (double)(finishTime - startTime)/1.0E9;
        elapsedTimeStringBuffer.setLength(0);
        decimalFormatter.format(elapsedTime, elapsedTimeStringBuffer, fieldPosition);
        statusBar.setTimeLabel(" " + elapsedTimeStringBuffer + " sec.");
        progressBar.setIndeterminate(false);
        progressBar.setString("Script finished.");
        progressBar.setValue(10);
        enabled = true;
    }
    
    
    
}
