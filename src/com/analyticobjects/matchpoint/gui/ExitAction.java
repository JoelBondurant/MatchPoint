/*
 * ExitAction.java
 *
 * Created on March 21, 2006, 11:04 PM
 *
 * Copyright 2006 Analytic Objects. All rights reserved.
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.analyticobjects.matchpoint.gui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;
import java.awt.Toolkit;


/**
 *
 * @author Joel
 */
public class ExitAction extends AbstractAction
{
    
    private static ExitAction uniqueInstance = null;
    
    /** Creates a new instance of ExitAction */
    private ExitAction()
    {
        super("Exit");
    }
    
    public static ExitAction getInstance()
    {
        if(uniqueInstance == null)
        {
            uniqueInstance = new ExitAction();
            //uniqueInstance.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_X, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            //the Cut action has ctrl-x as the accelerator key combo.
            uniqueInstance.putValue(SHORT_DESCRIPTION, "Exit the program.");
            uniqueInstance.putValue(LONG_DESCRIPTION, "Exit the program.");
            uniqueInstance.putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_X));
        }
        return uniqueInstance;
    }
    
    public void actionPerformed(ActionEvent ae)
    {
        System.exit(0);
    }
    
    
}
