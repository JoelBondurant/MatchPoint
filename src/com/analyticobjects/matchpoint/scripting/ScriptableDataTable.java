package com.analyticobjects.matchpoint.scripting;

import com.analyticobjects.matchpoint.gui.*;
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
    private int base = 1;
    
    /**
     * Creates a new instance of ScriptableDataTable
     */
    private ScriptableDataTable()
    {
        dataTable = DataTable.getInstance();
        dataTableModel = (DataTableModel)dataTable.getModel();
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
    
    private String fillColumn(int col, int num, int startVal, int increment)
    {
        col-=base;
        int val = startVal;
        for(int i = 0; i < num; i++)
        {
            dataTableModel.silentSetValueAt(val, i, col);
            val+=increment;
        }
        col+=base;
        return "Column  " + col + "  filled.";
    }
    
    public String fillColumn(int col, int num, double startVal, double increment)
    {
        if( Math.rint(increment)==increment && Math.rint(startVal)==startVal )
        {
            fillColumn(col, num, (int)startVal, (int)increment);
        }
        else
        {
            double endVal = startVal + (num - 1)* increment;
            fillColumn(col, 0 + base, num, startVal, endVal);
        }
        return "Column  " + col + "  filled.";
    }
    
    public String fillColumn(int col, int startRow, int endRow, double startVal, double endVal)
    {
        int numRows = endRow - startRow;
        if(numRows<1000)  //if it won't take too long, use the more precise method
        {
            String startValStr = Double.toString(startVal);
            String endValStr = Double.toString(endVal);
            return fillColumn(col, startRow, endRow, startValStr, endValStr);
        }
        if(numRows == 0)
        {
            return setCell(startRow, col, startVal);
        }
        col -= base;
        startRow -= base;
        endRow -= base;
        double deltaVal = (endVal - startVal);
        double increment = deltaVal / numRows;
        if(Math.rint(increment) == increment && Math.rint(startVal) == startVal)
        {
            int istartVal = (int)startVal;
            int val = istartVal;
            int row;
            int iincrement = (int)(deltaVal / numRows);
            for(int i = 0; i <= numRows; i++)
            {
                row = startRow + i;
                val = istartVal + iincrement * i;
                dataTableModel.silentSetValueAt(val, row, col);
            }
        }
        else
        {
            double val = startVal;
            int row;
            double drow;
            double dnumRows;
            for(int i = 0; i <= numRows; i++)
            {
                row = startRow + i;
                drow = (double)row;
                dnumRows = (double)numRows;
                val = deltaVal * (drow/dnumRows) + startVal;
                dataTableModel.silentSetValueAt(val, row, col);
            }
        }
        col+=base;
        return "Column  " + col + "  filled.";
    }
    
    public String fillColumn(int col, int num, String startVal, String increment)
    {
        BigDecimal bigStartVal = new BigDecimal(startVal);
        BigDecimal bigIncrement = new BigDecimal(increment);
        BigDecimal bigNumMinusOne = new BigDecimal(num-1);
        BigDecimal bigEndVal = bigStartVal.add(bigNumMinusOne.multiply(bigIncrement));
        String endVal = bigEndVal.toString();
        return fillColumn(col, 0 + base, num, startVal, endVal);
    }
    
    public String fillColumn(int col, int startRow, int endRow, String startVal, String endVal)
    {
        int numRows = endRow - startRow;
        if(numRows == 0)
        {
            return setCell(startRow, col, startVal);
        }
        col -= base;
        startRow -= base;
        endRow -= base;
        BigDecimal bigEndVal = new BigDecimal(endVal);
        BigDecimal bigStartVal = new BigDecimal(startVal);
        BigDecimal bigNumerator = bigEndVal.subtract(bigStartVal);
        BigDecimal bigDenominator = new BigDecimal(numRows);
        BigDecimal bigDVal = bigNumerator.divide(bigDenominator,128,BigDecimal.ROUND_HALF_EVEN);
        BigDecimal bigValue = bigStartVal;
        for(int i = 0; i <= numRows; i++)
        {
            dataTableModel.silentSetValueAt(bigValue.doubleValue(), startRow + i, col);
            bigValue = bigValue.add(bigDVal);
        }
        col+=base;
        return "Column  " + col + "  filled.";
    }
    
    
    public String toString(){return "MatchPointDataTableObject";}
}
