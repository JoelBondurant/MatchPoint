/*
 * DataTable.java
 *
 * Created on March 31, 2006, 11:12 PM
 *
 * Copyright 2006 Analytic Objects. All rights reserved.
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.analyticobjects.matchpoint.gui;

import com.analyticobjects.matchpoint.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author Joel
 */
public class DataTable extends JTable implements TableNavigation
{
    private static DataTable uniqueInstance;
    private DataTableModel model;
    private static int columnWidth = 120;
    
    /** Creates a new instance of DataTable */
    private DataTable()
    {
        super(new DataTableModel());
        model = (DataTableModel)this.getModel();
        setBackground(new Color(248,248,248));
        //setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setColumnSelectionAllowed(true);

        addMouseListener(new PopupMenu());

        
        getInputMap().put(DeleteAction.getDeleteKeyStroke(),Strings.deleteActionTitle);
        getActionMap().put(Strings.deleteActionTitle,DeleteAction.getInstance());
        
        getInputMap().put(CopyAction.getCopyKeyStroke(),Strings.copyActionTitle);
        getActionMap().put(Strings.copyActionTitle,CopyAction.getInstance());
        
        getInputMap().put(PasteAction.getPasteKeyStroke(),Strings.pasteActionTitle);
        getActionMap().put(Strings.pasteActionTitle,PasteAction.getInstance());
        
        getInputMap().put(CutAction.getCutKeyStroke(),Strings.cutActionTitle);
        getActionMap().put(Strings.cutActionTitle,CutAction.getInstance());
        
        for(int i = 0; i<TableNavigationKeyAction.getKeyStrokes().length; i++)
        {
            //getInputMap().put(TableNavigationKeyAction.getKeyStrokes()[i],TableNavigationKeyAction.getActionNames()[i]);
            //getActionMap().put(TableNavigationKeyAction.getActionNames()[i],TableNavigationKeyAction.getInstance());
            registerKeyboardAction(TableNavigationKeyAction.getInstance(),TableNavigationKeyAction.getActionNames()[i],TableNavigationKeyAction.getKeyStrokes()[i],JComponent.WHEN_FOCUSED);
        }

    }
    
    public static DataTable getInstance()
    {
        if(uniqueInstance == null)
        {
            uniqueInstance = new DataTable();
        }
        return uniqueInstance;
    }
     
    public void endOnceClicked()
    {
        int column = Math.min(getSelectedColumn(),((DataTableModel)getModel()).getColumnCount());
        getSelectionModel().clearSelection();
        addColumnSelectionInterval(column,column);
        addRowSelectionInterval(((DataTableModel)getModel()).getColumnSize(column)-1,((DataTableModel)getModel()).getColumnSize(column)-1);
    }
    
    public void endTwiceClicked()
    {
        getSelectionModel().clearSelection();
        addColumnSelectionInterval(((DataTableModel)getModel()).getUsedColumnCount()-1,((DataTableModel)getModel()).getUsedColumnCount()-1);
    }
    
    
    public void homeOnceClicked()
    {
        int column = Math.min(getSelectedColumn(),((DataTableModel)getModel()).getColumnCount());
        getSelectionModel().clearSelection();
        addColumnSelectionInterval(column,column);
        addRowSelectionInterval(0,0);
    }
    
    public void homeTwiceClicked()
    {
        getSelectionModel().clearSelection();
        addColumnSelectionInterval(0,0);
    }
    
    
    
   
    private class PopupMenu extends MouseAdapter
    {
        JPopupMenu popup;
        
        PopupMenu()
        {
            popup = new JPopupMenu();
            popup.add(CopyAction.getInstance());
            popup.add(PasteAction.getInstance());
            popup.add(CutAction.getInstance());
            popup.add(DeleteAction.getInstance());
        }
        
        public void mousePressed(MouseEvent me)
        {
            if(me.isPopupTrigger())
            {
                popupAction(me);
            }
        }
        
        public void mouseReleased(MouseEvent me)
        {
            if(me.isPopupTrigger())
            {
                popupAction(me);
            }
        }
        
        void popupAction(MouseEvent me)
        {
           popup.show(DataTable.this,me.getX(),me.getY());
        }
        
    }  //end PopupMenu class
    
      
}
