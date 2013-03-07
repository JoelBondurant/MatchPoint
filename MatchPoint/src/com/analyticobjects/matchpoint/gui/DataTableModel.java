/*
 * DataTableModel.java
 *
 * Created on March 18, 2006, 7:40 PM
 *
 * Copyright 2006 Analytic Objects. All rights reserved.
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.analyticobjects.matchpoint.gui;

import com.analyticobjects.matchpoint.numerics.*;
import javax.swing.table.*;
import com.analyticobjects.matchpoint.*;

/**
 *
 * @author Joel
 */
public class DataTableModel extends AbstractTableModel
{
    private DataStructure dataStructure;
    
    /** Creates a new instance of DataTableModel */
    public DataTableModel()
    {
        dataStructure = DataStructure.getInstance();
        addTableModelListener(Application.getMainFrame().getChart());
    }
    
    public int getRowCount()
    {
      return dataStructure.maximumVariableCapacity();
    }

    public int maximumVariableSize()
    {
      return dataStructure.maximumVariableSize();
    }

    public int getColumnCount()
    {
      return dataStructure.getCapacity();
    }

    public int getUsedColumnCount()
    {
      return dataStructure.getSize();
    }

    public Object getValueAt(int row, int column)
    {
      return dataStructure.getDataValueAt(row, column);
    }

    public boolean isCellEditable(int row, int column) 
    {
      return dataStructure.isValueEditable(row, column);
    }

    public String getColumnName(int column) 
    {
      return dataStructure.getVariableName(column);
    }

    public void setValueAt(Object aValue, int row, int column)
    {
      dataStructure.setDataValueAt(aValue, row, column);
      this.fireTableCellUpdated(row, column);
    }
    
    public void silentSetValueAt(Object aValue, int row, int column)
    {
      dataStructure.setDataValueAt(aValue, row, column);
      //this.fireTableCellUpdated(row, column);
    }

    public void deleteValueAt(int row, int column)
    {
      dataStructure.deleteDataValueAt(row, column);
      this.fireTableStructureChanged();
    }
    
    public void silentDeleteValueAt(int row, int column)
    {
      dataStructure.deleteDataValueAt(row, column);
      //this.fireTableStructureChanged();
    }
    
    public boolean silentDeleteValuesAt(int[] rows, int[] cols)
    {
      return dataStructure.deleteDataValuesAt(rows, cols);
      //this.fireTableStructureChanged();
    }

    public int getColumnSize(int index)
    {
      return dataStructure.getVariableSize(index);
    }
    
    @Override
    public void fireTableStructureChanged()
    {
        int cols = this.getColumnCount();
        int[] colwidth = new int[cols];
        Object[] colid = new Object[cols];
        DataTable dt = DataTable.getInstance();
        for(int i=0; i<cols; i++)
        {
            TableColumn col = dt.getColumnModel().getColumn(i);
            colwidth[i] = col.getWidth();
            colid[i] = col.getIdentifier();
        }
        super.fireTableStructureChanged();
        cols = this.getColumnCount();
        for(int i=0; i<cols; i++)
        {
            TableColumn col = dt.getColumnModel().getColumn(i);
            if(colid[i] == col.getIdentifier())
            {
                col.setPreferredWidth(colwidth[i]);
                col.setWidth(colwidth[i]);
            }
        }
    }
    

}
