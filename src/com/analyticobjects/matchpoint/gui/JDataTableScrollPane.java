package com.analyticobjects.matchpoint.gui;

import javax.swing.*;
import java.awt.*;


/**
 * This class customizes extends the JTableScrollPane providing custom features
 * such as a numbered row header.
 *
 * @author Joel
 * @version 1.0, 09/15/2006
 */
public class JDataTableScrollPane extends JTableScrollPane
{
    private Dimension prefDim = new Dimension(10,10);
    private int activeRow = 0;
    private StringBuilder rowHeaderText = new StringBuilder();
    private int strWidth = 6;
    private String sampleDigit = "8";
    

    /** Creates a new instance of JDataTableScrollPane */
    public JDataTableScrollPane(JTable jt)
    {
        super(jt);
        rowHeaderText.append(' ');
        rowHeaderText.append(' ');
        resizeRowHeader(super.rowHeader);
        this.getTableColumnHeader().setReorderingAllowed(false);
    }
    
    protected void resizeRowHeader(JTable jt)
    {
        try{this.strWidth = this.getGraphics().getFontMetrics().stringWidth(sampleDigit);}catch(Exception e){}
        prefDim.setSize(10 + 20 + this.strWidth*(int)(Math.log10(activeRow+100)), 10);
        jt.setPreferredScrollableViewportSize(prefDim);
        jt.revalidate();
    }
    

    protected Object getValueAt(int row, int column)
    {
        JDataTableScrollPane.this.activeRow = row;
        rowHeaderText.delete(2,rowHeaderText.length());
        rowHeaderText.append(row+1);
        rowHeaderText.append(' ');
        return rowHeaderText;
    }


}
