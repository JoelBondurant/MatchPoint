package com.analyticobjects.matchpoint.gui;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author Joel
 * @version 1.0, 12/22/2006
 */
public class StatisticsTable extends JTable
{
    private static StatisticsTable uniqueInstance;
    
    /** Creates a new instance of StatisticsTable */
    private StatisticsTable(StatisticsTableModel stm)
    {
        super(stm);
    }

    public static StatisticsTable getInstance()
    {
        if(uniqueInstance == null)
        {
            uniqueInstance = new StatisticsTable(new StatisticsTableModel());
        }
        return uniqueInstance;
    }
    
}
