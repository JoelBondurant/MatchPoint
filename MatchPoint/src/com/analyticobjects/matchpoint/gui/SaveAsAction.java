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
public class SaveAsAction extends AbstractAction
{
    private static SaveAsAction uniqueInstance;
    private JFileChooser fileChooser;
    private JTable savedTable;
    private File savedFile;
    
    /** Creates a new instance of SaveAction */
    private SaveAsAction()
    {
        super(Strings.saveAsActionTitle);
        fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(CSVFileIO.getFileFilter());
    }
    
    public static SaveAsAction getInstance()
    {
        if(uniqueInstance == null)
        {
            uniqueInstance = new SaveAsAction();
            uniqueInstance.putValue(SHORT_DESCRIPTION, Strings.saveAsActionShort);
            uniqueInstance.putValue(LONG_DESCRIPTION, Strings.saveAsActionLong);
            uniqueInstance.putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_A));
        }
        return uniqueInstance;
    }
    
    public void actionPerformed(ActionEvent ae)
    {
        savedTable = Application.getMainFrame().getTableManager().getSelectedTable();
        if(savedTable == null)
        {
            savedTable = Application.getMainFrame().getDataTable();
        }
        File aFile = Application.getMainFrame().getTableManager().getSelectedTableFile();
        if(aFile != null && aFile.exists())
        {
            fileChooser.setCurrentDirectory(aFile);
        }
        fileChooser.showSaveDialog(Application.getMainFrame());
        savedFile = fileChooser.getSelectedFile();
        if(savedFile == null){return;}
        String savedFileName = savedFile.getName();
        String newFileName = "";
        DataFileIO fileWriter;
        try
        {
            fileWriter = (DataFileIO)fileChooser.getFileFilter().getClass().getEnclosingClass().getConstructors()[0].newInstance(new Object[0]);
        }
        catch(Exception ie)
        {
            System.out.println("SaveAsAction exception caught when creating a new DataFileIO object.");
            fileWriter = new CSVFileIO();
        }
        
        if(savedFileName.startsWith("\"") && savedFileName.endsWith("\""))
        {
            newFileName = savedFileName.substring(1,savedFileName.length()-1);
        }
        else if(savedFileName.toLowerCase().endsWith(fileWriter.getFileExtension().toLowerCase()))
        {
            newFileName = savedFileName;
        }
        else
        {
            newFileName = savedFileName + fileWriter.getFileExtension();
        }
        File newFile = new File(fileChooser.getSelectedFile().getParentFile().getAbsolutePath() + File.separator + newFileName);
        Application.getMainFrame().getTableManager().setSelectedTableFile(newFile);
        fileChooser.setSelectedFile(newFile);
        fileWriter.writeFile(newFile, savedTable);
    }
    
    public JTable getSavedTable(){return savedTable;}
    public File getSavedFile(){return savedFile;}
}
