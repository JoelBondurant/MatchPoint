package com.analyticobjects.matchpoint.scripting;

import com.analyticobjects.matchpoint.numerics.*;
import com.analyticobjects.matchpoint.gui.*;

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
            return "Arrays can't be assigned to virtual columns.";
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
        return table.fillColumn(this.colIndex + table.getIndexBase(), num, startVal, increment);
    }
    
    public String fill(int startRow, int endRow, double startVal, double endVal)
    {
        if(this.method != FROM_TABLE){return "Error: You can only fill columns saved in the table.";}
        ScriptableDataTable table = ScriptableDataTable.getInstance();
        return table.fillColumn(this.colIndex + table.getIndexBase(), startRow, endRow, startVal, endVal);
    }
    
    public String fill(int num, String startVal, String increment)
    {
        if(this.method != FROM_TABLE){return "Error: You can only fill columns saved in the table.";}
        ScriptableDataTable table = ScriptableDataTable.getInstance();
        return table.fillColumn(this.colIndex + table.getIndexBase(), num, startVal, increment);
    }
    
    public String fill(int startRow, int endRow, String startVal, String endVal)
    {
        if(this.method != FROM_TABLE){return "Error: You can only fill columns saved in the table.";}
        ScriptableDataTable table = ScriptableDataTable.getInstance();
        return table.fillColumn(this.colIndex + table.getIndexBase(), startRow, endRow, startVal, endVal);
    }
    
    public String toString(){return "MatchPointColumn(" + colIndex + ")Object";}
    
}
