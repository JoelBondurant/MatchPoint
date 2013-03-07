/*
 * FitTableModel.java
 *
 * Created on March 18, 2006, 7:40 PM
 *
 * Copyright 2006 Analytic Objects. All rights reserved.
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.analyticobjects.matchpoint.gui;

import com.analyticobjects.matchpoint.*;
import com.analyticobjects.matchpoint.numerics.DataStructure;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Joel
 */
public class FitTableModel // extends AbstractTableModel
{
    private DataStructure dataStructure;
    
    /** Creates a new instance of FitTableModel */
    public FitTableModel()
    {
        dataStructure = DataStructure.getInstance();
    }
    
}
