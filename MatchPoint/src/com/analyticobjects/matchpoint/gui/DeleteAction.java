package com.analyticobjects.matchpoint.gui;

import com.analyticobjects.matchpoint.*;
import com.analyticobjects.matchpoint.icons.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
/**
 *
 * @author Joel
 * @version 1.0, 12/18/2006
 */
public class DeleteAction extends AbstractAction implements ActionListenerWorker
{
    private static DeleteAction uniqueInstance;
    private static KeyStroke deleteKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0);
    private static boolean enabled;
    private static ActionWorker workerObject;
    
    public static KeyStroke getDeleteKeyStroke()
    {
        return deleteKeyStroke;
    }
    
    /** Creates a new instance of DeleteAction */
    private DeleteAction()
    {
        super(Strings.deleteActionTitle, new ImageIcon(IconClassLoader.class.getResource("Delete24.gif")));
    }
    
    public static DeleteAction getInstance()
    {
        if(uniqueInstance == null)
        {
            uniqueInstance = new DeleteAction();
            uniqueInstance.putValue(ACCELERATOR_KEY, deleteKeyStroke);
            uniqueInstance.putValue(SHORT_DESCRIPTION, Strings.deleteActionShort);
            uniqueInstance.putValue(LONG_DESCRIPTION, Strings.deleteActionShort);
            uniqueInstance.putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_D));
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
            workerObject = new ActionWorker(this, ae, "Deleting data...", "Data deleted.");
            workerObject.execute();
        }
    }
    
    public void actionPerformedInWorkerThread(ActionEvent ae)
    {
        JTable selectedTable = Application.getMainFrame().getTableManager().getSelectedTable();
        if(selectedTable == null){return;}
        ListSelectionModel sel = selectedTable.getSelectionModel();
        if(sel.isSelectionEmpty()){return;}   
        if(selectedTable.getClass() == DataTable.class)
        {
            DataTable dtable = (DataTable)selectedTable;
            int[] rows = dtable.getSelectedRows();
            int[] cols = dtable.getSelectedColumns();
            //System.out.println(Arrays.toString(rows));
            //System.out.println(Arrays.toString(cols));
            DataTableModel dtableModel = (DataTableModel)(dtable.getModel());
            boolean tableWasChanged;
            tableWasChanged = dtableModel.silentDeleteValuesAt(rows, cols);
            //dtableModel.fireTableStructureChanged();
            if(tableWasChanged)
            {
                dtableModel.fireTableDataChanged();
            }
        }
    }//end of actionPerformedInWorkerThread method
    
    public void actionPerformedWhenDone(ActionEvent ae)
    {
        workerObject = null;
        enabled = true;
    }
    
}//end of DeleteAction class
