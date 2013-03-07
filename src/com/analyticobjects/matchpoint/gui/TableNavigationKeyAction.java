/*
 * TableNavigationKeyAction.java
 *
 * Created on April 15, 2006, 9:36 AM
 *
 * Copyright 2006 Analytic Objects. All rights reserved.
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.analyticobjects.matchpoint.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 *
 * @author Joel
 */
public class TableNavigationKeyAction extends AbstractAction
{
    
    private static TableNavigationKeyAction uniqueInstance;
    private static KeyStroke[] tableNavigationKeyStrokes = 
    {KeyStroke.getKeyStroke(KeyEvent.VK_HOME, 0),KeyStroke.getKeyStroke(KeyEvent.VK_END, 0)};
    private static String[] tableNavigationActionNames =
    {"Home","End"};
    private long[] lastTime;
    private final double doubleClickThreshold = 600.0;
        
    public static KeyStroke[] getKeyStrokes()
    {
        return tableNavigationKeyStrokes;
    }
    
    public static String[] getActionNames()
    {
        return tableNavigationActionNames;
    } 
    
    /** Creates a new instance of CopyAction */
    private TableNavigationKeyAction()
    {
        super("Navigation Action");
        lastTime = new long[getActionNames().length];
        for(int i = 0; i<getActionNames().length; i++)
        {
            lastTime[i] = -5000L;
        }
    }
    
    public static TableNavigationKeyAction getInstance()
    {
        if(uniqueInstance == null)
        {
            uniqueInstance = new TableNavigationKeyAction();
        }
        return uniqueInstance;
    }
    
    public void actionPerformed(ActionEvent ae)
    {
         long thisTime = System.currentTimeMillis();
         TableNavigation sourceTable = (TableNavigation)ae.getSource();
         if(ae.getActionCommand().compareTo(getActionNames()[0])==0)
         {
             if(Math.abs(thisTime - lastTime[0])>doubleClickThreshold)
             {
                 sourceTable.homeOnceClicked();
             }
             else
             {
                 sourceTable.homeTwiceClicked();
             }
             lastTime[0] = thisTime;
         }
         else if(ae.getActionCommand().compareTo(getActionNames()[1])==0)
         {
             if(Math.abs(thisTime - lastTime[1])>doubleClickThreshold)
             {
                 sourceTable.endOnceClicked();
             }
             else
             {
                 sourceTable.endTwiceClicked();
             }
             lastTime[1] = thisTime; 
         }
    }
    
}
