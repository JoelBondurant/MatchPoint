package com.analyticobjects.matchpoint.scripting;

import com.analyticobjects.matchpoint.numerics.*;
import com.analyticobjects.matchpoint.gui.*;
import java.math.*;
import java.util.*;

/**
 *
 * @author Joel
 * @version 1.0, 12/11/2006
 */
public class ScriptableColumn 
{
    private Variable col;
    private DataTableModel dataTableModel;
    private int colIndex; //-1 indicates a virtual/derived column
    private double db;
    private ScriptableColumn a, b;
    private ScriptableColumn equalRef;
    private int method;
    public static int FROM_REFERENCE = 0;
    public static int FROM_TABLE = 1;
    public static int ADD = 2;
    public static int ADD_COLUMNS = 3;
    public static int SUBTRACT = 4;
    public static int SUBTRACT_COLUMNS = 5;
    public static int MULTIPLY = 6;
    public static int MULTIPLY_COLUMNS = 7;
    public static int DIVIDE = 8;
    public static int DIVIDE_COLUMNS = 9;
    
    /** Creates a new instance of ScriptableColumn */
    public ScriptableColumn(int index) 
    {
        colIndex = index;
        method = FROM_TABLE;
        col = DataStructure.getInstance().getVariable(index);
        dataTableModel = (DataTableModel)DataTable.getInstance().getModel();
        equalRef = null;
    }
    
    private ScriptableColumn(int method, ScriptableColumn a, ScriptableColumn b, double db) 
    {
        colIndex = -1;
        this.method = method;
        this.a = a;
        this.b = b;
        this.db = db;
        equalRef = null;
    }
    
    public double row(int row)
    {
        row-=ScriptableDataTable.getInstance().getIndexBase();
        return getValueAt(row);
    }
    
    public String deleteRow(int row)
    {
        int base = ScriptableDataTable.getInstance().getIndexBase();
        row -= base;
        if(method != FROM_TABLE)
        {
            return "Error: You can only delete rows saved in the table.";
        }
        col.remove(row);
        row += base;
        return "Row " + row + " deleted from table.";
    }
    
    public String deleteRows(int[] rows)
    {
        if(method != FROM_TABLE)
        {
            return "Error: You can only delete rows saved in the table.";
        }
        int base = ScriptableDataTable.getInstance().getIndexBase();
        if(base != 0)
        {
            for(int i = 0; i<rows.length; i++)
            {
                rows[i] -= base;
            }
        }
        col.remove(rows);
        return "Rows deleted from table.";
    }
    
    public ScriptableColumn setRow(int row, double val)
    {
        if(this.method != FROM_TABLE){return this;}
        row-=ScriptableDataTable.getInstance().getIndexBase();
        MutableDouble x = new MutableDouble(val);
        dataTableModel.silentSetValueAt(x, row, colIndex);
        return this;
    }
    
    private double getValueAt(int row)
    {
        if(method == FROM_REFERENCE){return equalRef.getValueAt(row);}
        if(method == FROM_TABLE){return ((MutableDouble)this.col.getValueAt(row)).doubleValue();}
        boolean v1b = (a==null)?(false):(row < a.length());
        boolean v2b = (b==null)?(false):(row < b.length());
        double v1, v2;
        v1 = (v1b) ? (a.getValueAt(row)) : (0.0);
        v2 = (v2b) ? (b.getValueAt(row)) : (0.0);
        if(method == ADD){return v1 + db;}
        if(method == ADD_COLUMNS){return v1 + v2;}
        if(method == SUBTRACT){return v1 - db;}
        if(method == SUBTRACT_COLUMNS){return v1 - v2;}
        v1 = (v1b) ? (v1) : (1.0);
        v2 = (v2b) ? (v2) : (1.0);
        if(method == MULTIPLY){return v1 * db;}
        if(method == MULTIPLY_COLUMNS){return v1 * v2;}
        if(method == DIVIDE){return v1 / db;}
        if(method == DIVIDE_COLUMNS){return v1 / v2;}
        return 9999.77;
    }
    
    public int length()
    {
        if(method == FROM_REFERENCE){return this.equalRef.length();}
        if(method == FROM_TABLE){return this.col.getSize();}
        if(method == ADD || method == SUBTRACT)
        {
            return a.length();
        }
        if(method == ADD_COLUMNS || method == SUBTRACT_COLUMNS)
        {
            return Math.max(a.length(),b.length());
        }
        if(method == MULTIPLY || method == DIVIDE)
        {
            return a.length();
        }
        if(method == MULTIPLY_COLUMNS || method == DIVIDE_COLUMNS)
        {
            return Math.min(a.length(),b.length());
        }
        return 0;
    }
    
    public ScriptableColumn add(double db)
    {
        return new ScriptableColumn(ADD,this,null,db);
    }
    
    public ScriptableColumn add(ScriptableColumn b)
    {
        return new ScriptableColumn(ADD_COLUMNS,this,b,0.0);
    }
    
     public ScriptableColumn subtract(double db)
    {
        return new ScriptableColumn(SUBTRACT,this,null,db);
    }
    
    public ScriptableColumn subtract(ScriptableColumn b)
    {
        return new ScriptableColumn(SUBTRACT_COLUMNS,this,b,0.0);
    }
    
    public ScriptableColumn multiply(double db)
    {
        return new ScriptableColumn(MULTIPLY,this,null,db);
    }
    
    public ScriptableColumn multiply(ScriptableColumn b)
    {
        return new ScriptableColumn(MULTIPLY_COLUMNS,this,b,0.0);
    }
    
    public ScriptableColumn divide(double db)
    {
        return new ScriptableColumn(DIVIDE,this,null,db);
    }
    
    public ScriptableColumn divide(ScriptableColumn b)
    {
        return new ScriptableColumn(DIVIDE_COLUMNS,this,b,0.0);
    }
    
    public String setEqual(ScriptableColumn arg)
    {
        if(this.method != FROM_TABLE)
        {
            this.equalRef = arg;
            this.method = FROM_REFERENCE;
            this.a = arg.a;
            this.b = arg.b;
            this.db = arg.db;
            this.col = arg.col;
            this.colIndex = arg.colIndex;
            return "Column equality set.";
        }
        equalRef = null;
        int len = arg.length();
        this.col.clear();
        for(int row = 0; row<len; row++)
        {
            dataTableModel.silentSetValueAt(arg.getValueAt(row), row, colIndex);
        }
        return "Column equality set.";
    }
    
    public String setEqual(double[] arg)
    {
        if(this.method != FROM_TABLE)
        {
            return "Error: Arrays can't be assigned to virtual columns.";
        }
        equalRef = null;
        this.col.clear();
        for(int row = 0; row<arg.length; row++)
        {
            dataTableModel.silentSetValueAt(arg[row], row, colIndex);
        }
        return "Column equality set.";
    }
    
    public String fill(int num, double startVal, double increment)
    {
        if(this.method != FROM_TABLE){return "Error: You can only fill columns saved in the table.";}
        ScriptableDataTable table = ScriptableDataTable.getInstance();
        return fillColumn(this.colIndex + table.getIndexBase(), num, startVal, increment);
    }
    
    public String fill(int startRow, int endRow, double startVal, double endVal)
    {
        if(this.method != FROM_TABLE){return "Error: You can only fill columns saved in the table.";}
        ScriptableDataTable table = ScriptableDataTable.getInstance();
        return fillColumn(this.colIndex + table.getIndexBase(), startRow, endRow, startVal, endVal);
    }
    
    public String fill(int num, String startVal, String increment)
    {
        if(this.method != FROM_TABLE){return "Error: You can only fill columns saved in the table.";}
        ScriptableDataTable table = ScriptableDataTable.getInstance();
        return fillColumn(this.colIndex + table.getIndexBase(), num, startVal, increment);
    }
    
    public String fill(int startRow, int endRow, String startVal, String endVal)
    {
        if(this.method != FROM_TABLE){return "Error: You can only fill columns saved in the table.";}
        ScriptableDataTable table = ScriptableDataTable.getInstance();
        return fillColumn(this.colIndex + table.getIndexBase(), startRow, endRow, startVal, endVal);
    }
    
    private String fillColumn(int col, int num, int startVal, int increment)
    {
        int base = ScriptableDataTable.getInstance().getIndexBase();
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
    
    private String fillColumn(int col, int num, double startVal, double increment)
    {
        if( Math.rint(increment)==increment && Math.rint(startVal)==startVal )
        {
            fillColumn(col, num, (int)startVal, (int)increment);
        }
        else
        {
            double endVal = startVal + (num - 1)* increment;
            int base = ScriptableDataTable.getInstance().getIndexBase();
            fillColumn(col, 0 + base, num, startVal, endVal);
        }
        return "Column  " + col + "  filled.";
    }
    
    private String fillColumn(int col, int startRow, int endRow, double startVal, double endVal)
    {
        int numRows = endRow - startRow;
        if(numRows<20000)  //if it won't take too long, use the more precise method
        {
            String startValStr = Double.toString(startVal);
            String endValStr = Double.toString(endVal);
            return fillColumn(col, startRow, endRow, startValStr, endValStr);
        }
        if(numRows == 0)
        {
            return ScriptableDataTable.getInstance().setCell(startRow, col, startVal);
        }
        int base = ScriptableDataTable.getInstance().getIndexBase();
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
    
    private String fillColumn(int col, int num, String startVal, String increment)
    {
        int base = ScriptableDataTable.getInstance().getIndexBase();
        BigDecimal bigStartVal = new BigDecimal(startVal);
        BigDecimal bigIncrement = new BigDecimal(increment);
        BigDecimal bigNumMinusOne = new BigDecimal(num-1);
        BigDecimal bigEndVal = bigStartVal.add(bigNumMinusOne.multiply(bigIncrement));
        String endVal = bigEndVal.toString();
        return fillColumn(col, 0 + base, num, startVal, endVal);
    }
    
    private String fillColumn(int col, int startRow, int endRow, String startVal, String endVal)
    {
        int base = ScriptableDataTable.getInstance().getIndexBase();
        int numRows = endRow - startRow;
        if(numRows == 0)
        {
            return ScriptableDataTable.getInstance().setCell(startRow, col, startVal);
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
    
    public String toString(){return "MatchPointColumn(" + colIndex + ")Object";}
    
}
