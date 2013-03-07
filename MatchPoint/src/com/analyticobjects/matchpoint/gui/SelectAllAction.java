package com.analyticobjects.matchpoint.gui;

import com.analyticobjects.matchpoint.*;
import com.analyticobjects.matchpoint.icons.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import java.util.*;
import javax.swing.table.*;


/**
 * The action to select everything in focus.
 *
 * @author Joel
 * @version 1.0, 09/10/2006
 */
public class SelectAllAction extends AbstractAction
{
    
    private static SelectAllAction uniqueInstance;
    private static KeyStroke selectAllKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_A, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
    
    public static KeyStroke getSelectAllKeyStroke()
    {
        return selectAllKeyStroke;
    }
    /** Creates a new instance of SelectAllAction */
    private SelectAllAction()
    {
        super(Strings.selectAllActionTitle);
    }
    
    public static SelectAllAction getInstance()
    {
        if(uniqueInstance == null)
        {
            uniqueInstance = new SelectAllAction();
            uniqueInstance.putValue(ACCELERATOR_KEY, selectAllKeyStroke);
            uniqueInstance.putValue(SHORT_DESCRIPTION, Strings.selectAllActionShort);
            uniqueInstance.putValue(LONG_DESCRIPTION, Strings.selectAllActionLong);
            uniqueInstance.putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_A));
        }
        return uniqueInstance;
    }
    
    public void actionPerformed(ActionEvent ae)
    {
        JTable selectedTable;
        if(ae.getSource() instanceof JTable)
        {
            selectedTable = (JTable)ae.getSource();
        }
        else
        {
            selectedTable = Application.getMainFrame().getTableManager().getSelectedTable();
        }
        if(selectedTable == null){return;}
        selectedTable.clearSelection();
        selectedTable.getColumnModel().getSelectionModel().setSelectionInterval(0,selectedTable.getColumnModel().getColumnCount()-1);
        selectedTable.getSelectionModel().setSelectionInterval(0,selectedTable.getModel().getRowCount()-1);
    }
    
}
