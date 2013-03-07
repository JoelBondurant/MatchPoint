/*
 * OpenAction.java
 *
 * Created on March 17, 2006, 3:11 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.analyticobjects.matchpoint.gui;

import com.analyticobjects.matchpoint.*;
import com.analyticobjects.matchpoint.icons.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 *
 * @author Joel
 */


public class OpenAction extends AbstractAction
{
      
    private static OpenAction uniqueInstance;
    private JFileChooser fileChooser;
    //private static ImageIcon icn = new ImageIcon(getClass().getResource("icons/Open24.gif"));
    /** Creates a new instance of OpenAction */
    private OpenAction()
    {
        super(Strings.openActionTitle, new ImageIcon(IconClassLoader.class.getResource("Open24.gif")));
        fileChooser = new JFileChooser();
    }

    
    public static OpenAction getInstance()
    {
        if(uniqueInstance == null)
        {
            uniqueInstance = new OpenAction();
            uniqueInstance.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            uniqueInstance.putValue(SHORT_DESCRIPTION, Strings.openActionShort);
            uniqueInstance.putValue(LONG_DESCRIPTION, Strings.openActionShort);
            uniqueInstance.putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_O));
        }
        return uniqueInstance;
    }
    
    public void actionPerformed(ActionEvent ae)
    {
        fileChooser.showOpenDialog(Application.getMainFrame());
        File selectedFile = fileChooser.getSelectedFile();
        System.out.println(selectedFile);
        
    }

    
}
