package com.analyticobjects.matchpoint.gui;

import com.analyticobjects.matchpoint.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.plaf.basic.*;
import javax.swing.plaf.metal.*;


/**
 * This class customizes extends the JScrollPane providing custom features such
 * as a row header and scrollbar thumbs that don't get sized to useless dimensions.
 *
 * @author Joel
 * @version 1.0, 09/10/2006
 */
abstract class JTableScrollPane extends JScrollPane
{
    protected JTable rowHeader;
    protected JTable jTable;
    protected RowHeaderModel rowHeaderModel;
    protected JTableHeader columnHeader;

    /** Creates a new instance of JTableScrollPane */
    public JTableScrollPane(JTable jt)
    {
        super(jt);
        this.jTable = jt;
        rowHeaderModel = new RowHeaderModel();
        rowHeader = new JTable(rowHeaderModel);
        rowHeader.setBackground(SystemColor.control);
        //rowHeader.setAutoResizeMode(jt.AUTO_RESIZE_ALL_COLUMNS); //breaks row header sizing
        rowHeader.setAlignmentX(JTable.RIGHT_ALIGNMENT);
        rowHeader.setSelectionModel(jTable.getSelectionModel());
        rowHeader.setAutoscrolls(false);//bug fix 10/11/2006
        this.setRowHeaderView(rowHeader);
        //this.getVerticalScrollBar().setUI(new VerticalScrollBarUI());  //looks ugly
        this.add(new ScrollPaneULButton(), JScrollPane.UPPER_LEFT_CORNER);
        this.getVerticalScrollBar().addAdjustmentListener(new ScrollBarListener());
        JPanel llc = new JPanel();
        JPanel urc = new JPanel();
        llc.setBorder(BorderFactory.createEtchedBorder());
        urc.setBorder(BorderFactory.createEtchedBorder());
        this.setCorner(JTableScrollPane.LOWER_LEFT_CORNER, llc);
        this.setCorner(JTableScrollPane.UPPER_RIGHT_CORNER, urc);
        columnHeader = jt.getTableHeader();
        columnHeader.addMouseListener(new ColumnHeaderListener());
        rowHeader.addMouseListener(new RowHeaderListener());
    }
    
        
    public JTableHeader getTableColumnHeader()
    {
        return columnHeader;
    }
    
    //protected abstract void resizeRowHeader(RowHeaderModel rhm);
    protected abstract void resizeRowHeader(JTable jt);

    private class ScrollBarListener implements AdjustmentListener
    {
        public void adjustmentValueChanged(AdjustmentEvent ae)
        {
            resizeRowHeader(JTableScrollPane.this.rowHeader);
        }
    }
    
    private class ColumnHeaderListener extends MouseAdapter
    {
        public void mouseClicked(MouseEvent me)
        {
            if(me.getClickCount()<2){return;}
            JTableHeader src = ((JTableHeader)me.getSource());
            JTable srcTable = src.getTable();
            if(!srcTable.getColumnModel().getColumnSelectionAllowed()){return;}
            int srcNum = src.columnAtPoint(me.getPoint());
            if(srcTable.isColumnSelected(srcNum))
            {
                srcTable.getColumnModel().getSelectionModel().removeSelectionInterval(srcNum,srcNum);
                if(srcTable.getColumnModel().getSelectionModel().isSelectionEmpty())
                {
                    srcTable.getSelectionModel().clearSelection();
                    JTableScrollPane.this.rowHeader.getColumnModel().getSelectionModel().clearSelection();
                }
            }
            else
            {
                srcTable.getColumnModel().getSelectionModel().addSelectionInterval(srcNum,srcNum);
                srcTable.getSelectionModel().addSelectionInterval(0,srcTable.getModel().getRowCount());
            }
        }
    }
    
    
    private class RowHeaderListener extends MouseAdapter
    {
        public void mouseClicked(MouseEvent me)
        {
            JTable src = ((JTable)me.getSource());
            if(me.getClickCount()<2){return;}
            if(!src.getRowSelectionAllowed()){return;}
            int srcNum = src.rowAtPoint(me.getPoint());
            if(!jTable.getColumnModel().getSelectionModel().isSelectionEmpty())
            {
                jTable.getColumnModel().getSelectionModel().removeSelectionInterval(0,jTable.getColumnCount()-1);
                jTable.getSelectionModel().removeSelectionInterval(srcNum,srcNum);
            }
            else
            {
                jTable.getColumnModel().getSelectionModel().addSelectionInterval(0,jTable.getColumnCount()-1);
                jTable.getSelectionModel().addSelectionInterval(srcNum,srcNum);
            }
        }
        
        public void mouseReleased(MouseEvent me)
        {
            JTable src = ((JTable)me.getSource());
            if(jTable.getColumnModel().getSelectionModel().isSelectionEmpty())
            {
                src.getSelectionModel().clearSelection();
            }
        }
    }

    
    private class ScrollPaneULButton extends JPanel
    {
        private String actionCmd = "ScrollPaneULButton";
        public ScrollPaneULButton()
        {
            super();
            this.setBorder(BorderFactory.createEtchedBorder());
            this.addMouseListener( new MouseAdapter()
            {
                public void mouseClicked(MouseEvent me)
                {
                    if(me.getClickCount()<2)
                    {
                        JTableScrollPane.this.jTable.getSelectionModel().clearSelection();
                        JTableScrollPane.this.jTable.getColumnModel().getSelectionModel().clearSelection();
                    }
                    else
                    {
                        SelectAllAction.getInstance().actionPerformed(new ActionEvent(JTableScrollPane.this.jTable,0,actionCmd));
                    }
                }
            });
        }
    }
    
    private class VerticalScrollBarUI extends BasicScrollBarUI
    {
        protected Dimension minimumThumbSize = new Dimension(10,50);
        
        public Dimension getMinimumThumbSize()
        {
            return minimumThumbSize;
        }
    }
  
    //provides a hook into the RowHeaderModel
    protected abstract Object getValueAt(int row, int column);
    
    protected class RowHeaderModel extends AbstractTableModel
    {    
        public RowHeaderModel()
        {
            super();
        }
        
        public Object getValueAt(int row, int column)
        {
            return JTableScrollPane.this.getValueAt(row, column);
        }

        public int getColumnCount()
        {
            return 1;
        }
        
        public int getRowCount()
        {
            return JTableScrollPane.this.jTable.getRowCount();
        }
    }
}
