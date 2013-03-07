package com.analyticobjects.matchpoint.gui;


import com.analyticobjects.matchpoint.*;
import com.analyticobjects.matchpoint.fileio.*;
import com.analyticobjects.matchpoint.icons.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author Joel
 * @version 1.0, 10/14/2006
 */
public class SaveAction extends AbstractAction
{
    private static SaveAction uniqueInstance;
    private static KeyStroke saveKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());

    
    public static KeyStroke getSaveKeyStroke()
    {
        return saveKeyStroke;
    }
    
    /** Creates a new instance of SaveAction */
    private SaveAction()
    {
        super(Strings.saveActionTitle);
    }
    
    public static SaveAction getInstance()
    {
        if(uniqueInstance == null)
        {
            uniqueInstance = new SaveAction();
            uniqueInstance.putValue(ACCELERATOR_KEY, saveKeyStroke);
            uniqueInstance.putValue(SHORT_DESCRIPTION, Strings.saveActionShort);
            uniqueInstance.putValue(LONG_DESCRIPTION, Strings.saveActionLong);
            uniqueInstance.putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_S));
        }
        return uniqueInstance;
    }
    
    public void actionPerformed(ActionEvent ae)
    {
        JTable savedTable = SaveAsAction.getInstance().getSavedTable();
        JTable selectedTable = Application.getMainFrame().getTableManager().getSelectedTable();
        if(selectedTable == null)
        {
            selectedTable = Application.getMainFrame().getDataTable();
        }
        File savedFile = SaveAsAction.getInstance().getSavedFile();
        File selectedFile = Application.getMainFrame().getTableManager().getSelectedTableFile();
        if(selectedFile == null)
        {
            SaveAsAction.getInstance().actionPerformed(ae);
            return;
        }
        CSVFileIO fio = new CSVFileIO();
        fio.writeFile(selectedFile, selectedTable);
    }
}
