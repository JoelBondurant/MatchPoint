package com.analyticobjects.matchpoint.numerics;

import com.analyticobjects.matchpoint.*;
import com.analyticobjects.matchpoint.gui.Strings;
import java.lang.reflect.InvocationTargetException;
import javax.swing.table.*;
import javax.swing.*;
import com.analyticobjects.matchpoint.gui.*;
import java.util.Vector;

/**
 * This class is the underlying data structure for the Matchpoint application.
 * @author Joel
 * @verstion 1.00, 03/18/2006
 */
public class DataStructure
{
    private static DataStructure uniqueInstance;
    private Vector<Variable> variables;
    private static final int INITIAL_CAPACITY = 3;
    private int dependantVariableIndex;
    private Vector<Integer> independantVariableIndex;
    
    /** Creates a new instance of the <tt>DataStructure</tt> */
    private DataStructure()
    {
        variables = new Vector<Variable>(INITIAL_CAPACITY);
    }
    
    public static DataStructure getInstance()
    {
        if(uniqueInstance == null)
        {
            uniqueInstance = new DataStructure();
            uniqueInstance.addVariable(new DoubleVariable());
            uniqueInstance.variables.get(0).setName(Strings.defaultIndependantVariableName);
            uniqueInstance.addVariable(new DoubleVariable());
            uniqueInstance.variables.get(1).setName(Strings.defaultDependantVariableName);
            uniqueInstance.addVariable(new DoubleVariable());
            uniqueInstance.variables.get(2).setName(Strings.defaultWeightName);
            uniqueInstance.dependantVariableIndex = 1;
            uniqueInstance.independantVariableIndex = new Vector<Integer>();
            uniqueInstance.independantVariableIndex.add(new Integer(0));
        }
        return uniqueInstance;
    }
      
    public void updateStats()
    {
        for(Variable var: variables){var.updateStats();}
    }
    
    public String getVariableName(int index)
    {
        return variables.get(index).getName();
    }
    
    public void setVariableName(int index, String aName)
    {
        variables.get(index).setName(aName);
    }
    
    public Variable getVariable(int index)
    {
        return variables.get(index);
    }
    
    public void addVariable(Variable var)
    {
        variables.add(var);
    }
    
    public int getVariableSize(int index)
    {
        return variables.get(index).getSize();
    }
    
    public int maximumVariableSize()
    {
        int max = 0, curSize = 0;
        for(int i = 0; i<getSize(); i++)
        {
            curSize = variables.get(i).getSize();
            if(curSize > max)
            {
                max = curSize;
            }
        }
        return max;
    }
    
    public int maximumVariableCapacity()
    {
        int max = 0, curCapacity = 0;
        for(int i = 0; i < getSize(); i++)
        {
            curCapacity = variables.get(i).getCapacity();
            if(curCapacity > max)
            {
                max = curCapacity;
            }
        }
        return max;
    }
    
    public int getSize()
    {
        return variables.size();
    }
    
    public Object getDataValueAt(int row, int column)
    {
        if(column < getSize())
        {
            return variables.get(column).getValueAt(row);
        }
        else
        {
            return null;
        }
    }
    
    public void setDataValueAt(Object aValue, int row, int column)
    {
        if(variables.get(column).getCapacity() - row < 11)
        {
            variables.get(column).ensureCapacity(Math.max(2*row + 2,row + 1024));
            /*System.gc();
            for(JTable jt: Application.getMainFrame().getTableManager().getManagedTables())
            {
                ((AbstractTableModel)jt.getModel()).fireTableStructureChanged();
            }*/
        }
        variables.get(column).setValueAt(aValue, row);
    }
    
    public boolean isValueEditable(int row, int column) 
    {
        return ( row < variables.get(column).getSize() + 1 );
    }
    
    public int getCapacity()
    {
        return variables.capacity();
    }

    public void deleteDataValueAt(int row, int column)
    {
        variables.get(column).remove(row);
    }
    
    public boolean deleteDataValuesAt(int[] rows, int[] cols)
    {
        boolean dataDeleted = false;
        for(int i=0; i<cols.length; i++)
        {
            dataDeleted |= variables.get(cols[i]).remove(rows);
        }
        return dataDeleted;
    }

    public Variable getDependantVariable()
    {
        return variables.get(dependantVariableIndex);
    }
    
    public Variable getIndependantVariable(int index)
    {
        return variables.get(independantVariableIndex.get(index).intValue());
    }
}