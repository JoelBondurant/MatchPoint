package com.analyticobjects.matchpoint.gui;

import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 *
 * @author Joel
 * @version 1.0, 10/08/2006
 */
public class JTableManager implements ListSelectionListener
{
    private Vector<ManagedJTable> tables;
    private int selectedIndex;
    /**
     * Creates a new instance of JTableManager
     */
    public JTableManager()
    {
        tables = new Vector<ManagedJTable>(10);
        selectedIndex = -1;
    }
    
    public void valueChanged(ListSelectionEvent lse)
    {
        int index = -1;
        ListSelectionModel lsm = ((ListSelectionModel)(lse.getSource()));
        if(lsm.getValueIsAdjusting()){return;}
        if(lsm.isSelectionEmpty())
        {
            this.selectedIndex = index;
            return;
        }
        JTable jt;
        for(int i = 0; i<tables.size(); i++)
        {
            jt = tables.get(i).getJTable();
            if(lsm == jt.getSelectionModel())
            {
                index = i;
            }
            else
            {
                jt.getSelectionModel().clearSelection();
            }
        }
        if(!(lsm.isSelectionEmpty()))
        {
            this.selectedIndex = index;
        }
        //if(this.selectedIndex != -1){System.out.println(this.getSelectedTable().getName());}
    }
    
    public JTable getSelectedTable()
    {
        if(this.selectedIndex == -1){return null;}
        return this.tables.get(this.selectedIndex).getJTable();
    }
    
    public JTable[] getManagedTables()
    {
        JTable[] jts = new JTable[tables.size()];
        for(int i = 0; i<tables.size(); i++)
        {
            jts[i] = tables.get(i).getJTable();
        }
        return jts;
    }
    
    public File getSelectedTableFile()
    {
        if(this.selectedIndex == -1){return null;}
        return this.tables.get(this.selectedIndex).getFile();
    }
    
    public void setSelectedTableFile(File aFile)
    {
        if(this.selectedIndex == -1){return;}
        this.tables.get(this.selectedIndex).setFile(aFile);
    }
    
    public JTableManager add(JTable jt)
    {
        ManagedJTable mjt = new ManagedJTable(jt);
        tables.add(mjt);
        jt.getSelectionModel().addListSelectionListener(this);
        return this;
    }
    
    private class ManagedJTable
    {
        private File aFile;
        private JTable aTable;
        
        ManagedJTable(JTable aTable)
        {
            this.aFile = null;
            this.aTable = aTable;
        }
        
        public JTable getJTable(){return this.aTable;}
        public File getFile(){return this.aFile;}
        public void setFile(File aFile){this.aFile = aFile;}
    }
    
}
