/*
 * PasteAction.java
 *
 * Created on April 1, 2006, 11:12 AM
 *
 * Copyright 2006 Analytic Objects. All rights reserved.
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.analyticobjects.matchpoint.gui;

import com.analyticobjects.matchpoint.*;
import com.analyticobjects.matchpoint.icons.*;
import com.analyticobjects.matchpoint.numerics.DataStructure;
import java.awt.datatransfer.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.util.*;
/**
 *
 * @author Joel
 */
public class PasteAction extends AbstractAction implements ActionListenerWorker
{
    private static PasteAction uniqueInstance;
    private Clipboard systemClipboard;
    private String rowstring, value;
    private static boolean enabled;
    private static ActionWorker workerObject;
    private JTable selectedTable;
    private static KeyStroke pasteKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_V, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
    
    public static KeyStroke getPasteKeyStroke()
    {
        return pasteKeyStroke;
    }
    
    /** Creates a new instance of PasteAction */
    private PasteAction()
    {
        super(Strings.pasteActionTitle, new ImageIcon(IconClassLoader.class.getResource("pasteIcon.png")));
        systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    }
    
    public static PasteAction getInstance()
    {
        if(uniqueInstance == null)
        {
            uniqueInstance = new PasteAction();
            uniqueInstance.putValue(ACCELERATOR_KEY, pasteKeyStroke);
            uniqueInstance.putValue(SHORT_DESCRIPTION, Strings.pasteActionShort);
            uniqueInstance.putValue(LONG_DESCRIPTION, Strings.pasteActionShort);
            uniqueInstance.putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_P));
            uniqueInstance.enabled = true;
        }
        return uniqueInstance;
    }
    
    public void actionPerformed(ActionEvent ae)
    {
        if(enabled && ActionWorker.isEnabled())
        {
            enabled = false;
            Application.getMainFrame().getStatusBar().getProgressBar().setIndeterminate(true);
            workerObject = new ActionWorker(this, ae, "Pasting data...", "Data pasted.");
            workerObject.execute();
        }
    }
    
    public void actionPerformedInWorkerThread(ActionEvent ae)
    {
       selectedTable = Application.getMainFrame().getTableManager().getSelectedTable();
       DataStructure ds = DataStructure.getInstance();
       if(selectedTable == null){return;}
       if(selectedTable.getSelectionModel().isSelectionEmpty()){return;}
       
       int startRow = (selectedTable.getSelectedRows())[0];
       int startCol = (selectedTable.getSelectedColumns())[0];
       String trstring = "";
       try
       {
          trstring = (String)(systemClipboard.getContents(this).getTransferData(DataFlavor.stringFlavor));
       }
       catch(Exception ex){}
       trstring = trstring.replaceAll("\\t\\t","\t \t");
       trstring = trstring.replaceAll("\\n\\t","\n \t");
       trstring = trstring.replaceAll("\\t\\n","\t \n");
       StringTokenizer stoken1 = new StringTokenizer(trstring,"\n");
       
      
       if(selectedTable instanceof DataTable)
       {
           for(int i=0; stoken1.hasMoreTokens(); i++)
           {
              rowstring = stoken1.nextToken();
              StringTokenizer stoken2 = new StringTokenizer(rowstring,"\t");
              for(int j=0; stoken2.hasMoreTokens(); j++)
              {
                 value = (String)stoken2.nextToken();
                 if((startRow + i < selectedTable.getRowCount()) && (startCol+j < selectedTable.getColumnCount()))
                 {
                    ds.setDataValueAt(value, startRow+i, startCol+j);
                 }
              }
           }  
       }
       else
       {
           for(int i=0; stoken1.hasMoreTokens(); i++)
           {
              rowstring = stoken1.nextToken();
              StringTokenizer stoken2 = new StringTokenizer(rowstring,"\t");
              for(int j=0; stoken2.hasMoreTokens(); j++)
              {
                 value = (String)stoken2.nextToken();
                 if((startRow + i < selectedTable.getRowCount()) && (startCol+j < selectedTable.getColumnCount()))
                 {
                    selectedTable.setValueAt(value, startRow+i, startCol+j);
                 }
              }
           }
       }
     }//end of actionPerformedInWorkerThread
    
    public void actionPerformedWhenDone(ActionEvent ae)
    {
        if(selectedTable == null){return;}
        AbstractTableModel stm = ((AbstractTableModel)selectedTable.getModel());
        if(stm == null){return;}
        stm.fireTableStructureChanged();
        workerObject = null;
        enabled = true;
    }
    
}//end of PasteAction
