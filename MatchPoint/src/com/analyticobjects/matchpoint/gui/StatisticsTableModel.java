/*
 * StatisticsTableModel.java
 *
 * Created on March 18, 2006, 7:38 PM
 *
 * Copyright 2006 Analytic Objects. All rights reserved.
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.analyticobjects.matchpoint.gui;

import com.analyticobjects.matchpoint.*;
import com.analyticobjects.matchpoint.numerics.*;
import javax.swing.table.*;
import javax.swing.event.*;

/**
 *
 * @author Joel
 */
public class StatisticsTableModel extends AbstractTableModel implements TableModelListener
{
    
    private DataStructure dataStructure;
    
    /** Creates a new instance of StatisticsTableModel */
    public StatisticsTableModel()
    {
        dataStructure = DataStructure.getInstance();
        Application.getMainFrame().getDataTable().getModel().addTableModelListener(this);
    }
    
    public int getRowCount()
    {
      return dataStructure.getCapacity();
    }

    public int getColumnCount()
    {
        return StatisticalReflection.getStatsCount();
    }

    public Object getValueAt(int row, int column)
    {
      return StatisticalReflection.getStatsValueAt(row, column);
    }

    public boolean isCellEditable(int row, int column) 
    {
      return false;
    }

    public String getColumnName(int column) 
    {
      return StatisticalReflection.getStatName(column);
    }
    
    public void tableChanged(TableModelEvent tme)
    {
        this.fireTableStructureChanged();
    }
    
    @Override
    public void fireTableStructureChanged()
    {
        int cols = this.getColumnCount();
        int[] colwidth = new int[cols];
        Object[] colid = new Object[cols];
        StatisticsTable st = StatisticsTable.getInstance();
        for(int i=0; i<cols; i++)
        {
            TableColumn col = st.getColumnModel().getColumn(i);
            colwidth[i] = col.getWidth();
            colid[i] = col.getIdentifier();
        }
        super.fireTableStructureChanged();
        cols = this.getColumnCount();
        for(int i=0; i<cols; i++)
        {
            TableColumn col = st.getColumnModel().getColumn(i);
            if(colid[i] == col.getIdentifier())
            {
                col.setPreferredWidth(colwidth[i]);
                col.setWidth(colwidth[i]);
            }
        }
    }

    
}
