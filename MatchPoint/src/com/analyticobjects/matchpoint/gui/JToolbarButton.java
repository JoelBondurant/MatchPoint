package com.analyticobjects.matchpoint.gui;

import com.analyticobjects.matchpoint.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author Joel
 * @version 1.0, 10/23/2006
 */
public class JToolbarButton extends JButton
{
    
    /** Creates a new instance of JToolbarButton */
    public JToolbarButton(Action a)
    {
        super(a);
        this.setModel(new JToolbarButtonModel());
        this.setFocusPainted(false);
        this.setText("");
    }
    
    private class JToolbarButtonModel extends DefaultButtonModel
    {
        public boolean isSelected(){return false;}
        public void fireActionPerformed(ActionEvent e)
        {
            super.fireActionPerformed(e);
            Application.getMainFrame().requestFocus();
        }
    }
    
}
