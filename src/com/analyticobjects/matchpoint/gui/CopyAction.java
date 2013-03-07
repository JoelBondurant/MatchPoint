package com.analyticobjects.matchpoint.gui;

import com.analyticobjects.matchpoint.*;
import com.analyticobjects.matchpoint.icons.*;
import com.analyticobjects.matchpoint.numerics.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import java.util.*;
import javax.swing.table.*;

/**
 *
 * @author Joel
 * @version 1.0, 04/07/2006
 */
public class CopyAction extends AbstractAction implements ActionListenerWorker
{
    private static CopyAction uniqueInstance;
    private static KeyStroke copyKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
    private Clipboard systemClipboard;
    private StringSelection selection;
    private ActionWorker workerObject;
    private StatusBar statusBar;
    private boolean enabled;
    private static final String tabString = "\t";
    private static final String newLineString = "\n";
    private static final String nullString = "null";
    private static final String emptyString = "";
    
    public static KeyStroke getCopyKeyStroke()
    {
        return copyKeyStroke;
    }
    /** Creates a new instance of CopyAction */
    private CopyAction()
    {
        super(Strings.copyActionTitle, new ImageIcon(IconClassLoader.class.getResource("Copy24.gif")));
        systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        statusBar = Application.getMainFrame().getStatusBar();
    }
    
    public static CopyAction getInstance()
    {
        if(uniqueInstance == null)
        {
            uniqueInstance = new CopyAction();
            uniqueInstance.putValue(ACCELERATOR_KEY, copyKeyStroke);
            uniqueInstance.putValue(SHORT_DESCRIPTION, Strings.copyActionShort);
            uniqueInstance.putValue(LONG_DESCRIPTION, Strings.copyActionShort);
            uniqueInstance.putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_C));
            uniqueInstance.enabled = true;
        }
        return uniqueInstance;
    }
    
    public void actionPerformed(ActionEvent ae)
    {
        if(enabled && ActionWorker.isEnabled())
        {
            enabled = false;
            workerObject = new ActionWorker(this, ae, "Copy in progress...", "Selection copied.");
            workerObject.execute();
        }
    }
    
    public void actionPerformedInWorkerThread(ActionEvent ae)
    {
        JTable selectedTable = Application.getMainFrame().getTableManager().getSelectedTable();
        if(selectedTable == null){return;}
        if(selectedTable.getSelectionModel().isSelectionEmpty()){return;}
        StringBuffer buffer = new StringBuffer();
        int numcols = selectedTable.getSelectedColumnCount();
        int numrows = selectedTable.getSelectedRowCount();
        int[] rowsselected = selectedTable.getSelectedRows();
        int[] colsselected = selectedTable.getSelectedColumns();
        if(selectedTable == Application.getMainFrame().getDataTable())
        {
            numrows = Math.min(numrows,DataStructure.getInstance().maximumVariableSize());
        }
        int progress = 0;
        int updateInterval = Math.max(1, (numrows) / 10);
        for (int i=0; i<numrows; i++)
        {        
            for (int j=0; j<numcols; j++)
            {
                Object val = selectedTable.getValueAt(rowsselected[i], colsselected[j]);
                if(val != null)
                {
                    buffer.append(val);
                }
                if(j<numcols-1)
                {
                    buffer.append(tabString);
                }
            }
            if(i<numrows - 1)
            {
                buffer.append(newLineString);
            }
            if( i % updateInterval == 0 && workerObject != null)
            {
                progress = i / updateInterval;
                workerObject.setActionWorkerProgress(progress+1);
            }
         }
         boolean isBufferNull = false;
         if(buffer.length()==4)
         {
             isBufferNull = (buffer.toString().compareTo(nullString)==0);
         }
         selection = new StringSelection((isBufferNull)?(emptyString):(buffer.toString()));//stupid implementation!
         systemClipboard.setContents(selection,selection);
    }
    
    public void actionPerformedWhenDone(ActionEvent ae)
    {
        workerObject = null;
        enabled = true;
    }
    
}
