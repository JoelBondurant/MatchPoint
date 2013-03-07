package com.analyticobjects.matchpoint.chart;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author Joel
 * @version 1.0, 10/10/2006
 */
public class ChartPropertiesAction extends AbstractAction
{
    private static ChartPropertiesAction uniqueInstance = null;
    private ChartPropertiesDialog dialog;
    
    /** Creates a new instance of ExitAction */
    private ChartPropertiesAction()
    {
        super("Chart Properties");
        dialog = new ChartPropertiesDialog();
    }
    
    public static ChartPropertiesAction getInstance()
    {
        if(uniqueInstance == null)
        {
            uniqueInstance = new ChartPropertiesAction();
            //uniqueInstance.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_?, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            uniqueInstance.putValue(SHORT_DESCRIPTION, "Modify the chart properties.");
            uniqueInstance.putValue(LONG_DESCRIPTION, "Modify the chart properties.");
            uniqueInstance.putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_H));
        }
        return uniqueInstance;
    }
    
    public void actionPerformed(ActionEvent ae)
    {
        dialog.setVisible(true);
    }
    
    
}