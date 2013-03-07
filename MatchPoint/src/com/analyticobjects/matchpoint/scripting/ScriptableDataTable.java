package com.analyticobjects.matchpoint.scripting;

import com.analyticobjects.matchpoint.gui.*;
import com.analyticobjects.matchpoint.numerics.*;
import java.math.*;
/**
 *
 * @author Joel
 * @version 1.0, 12/06/2006
 */
public class ScriptableDataTable
{
    private static ScriptableDataTable uniqueInstance;
    private DataTable dataTable;
    private DataTableModel dataTableModel;
    private DataStructure dataStructure;
    private int base = 1;//by default the table cells will be addressed wrt one.
    
    /**
     * Creates a new instance of ScriptableDataTable
     */
    private ScriptableDataTable()
    {
        dataTable = DataTable.getInstance();
        dataTableModel = (DataTableModel)dataTable.getModel();
        dataStructure = DataStructure.getInstance();
    }
    
    public static ScriptableDataTable getInstance()
    {
        if(uniqueInstance == null)
        {
            uniqueInstance = new ScriptableDataTable();
        }
        return uniqueInstance;
    }
    
    public String setIndexBase(int base)
    {
        if(base == 0){this.base = 0; return "Index base set to zero.";}
        if(base == 1){this.base = 1; return "Index base set to one.";}
        return "Error: Invalid base (" + base + ") , base must be zero or one.";
    }
    
    public int getIndexBase()
    {
        return base;
    }
    
    public ScriptableColumn column(int index)
    {
        return new ScriptableColumn(index - base);
    }
    
    public Object cell(int row, int col)
    {
        return dataTable.getValueAt(row - base, col - base);
    }
    
    public String setCell(int row, int col, Object x)
    {
        dataTableModel.silentSetValueAt(x, row - base, col - base);
        return "Cell  (" + row + ", " + col + ")  set.";
    }
    
    public int getColumnCount()
    {
        return dataTableModel.getColumnCount();
    }
    
    public String toString(){return "MatchPointDataTableObject";}
}
