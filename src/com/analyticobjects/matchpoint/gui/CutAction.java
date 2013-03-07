package com.analyticobjects.matchpoint.gui;

import com.analyticobjects.matchpoint.*;
import com.analyticobjects.matchpoint.icons.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import java.util.*;


/**
 *
 * @author Joel
 * @version 1.0, 04/08/2006
 */
public class CutAction extends AbstractAction implements ActionListenerWorker
{
    private ActionWorker workerObject;
    private boolean enabled;
    private static CutAction uniqueInstance;
    private static KeyStroke cutKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_X, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
    private Clipboard systemClipboard;
    private StringSelection selection;
    
    public static KeyStroke getCutKeyStroke()
    {
        return cutKeyStroke;
    }
    /** Creates a new instance of CopyAction */
    private CutAction()
    {
        super(Strings.cutActionTitle, new ImageIcon(IconClassLoader.class.getResource("Cut24.gif")));
        systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    }
    
    public static CutAction getInstance()
    {
        if(uniqueInstance == null)
        {
            uniqueInstance = new CutAction();
            uniqueInstance.putValue(ACCELERATOR_KEY, cutKeyStroke);
            uniqueInstance.putValue(SHORT_DESCRIPTION, Strings.cutActionShort);
            uniqueInstance.putValue(LONG_DESCRIPTION, Strings.cutActionShort);
            uniqueInstance.putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_T));
            uniqueInstance.enabled = true;
        }
        return uniqueInstance;
    }
    
    public void actionPerformed(ActionEvent ae)
    {
        if(enabled && ActionWorker.isEnabled())
        {
            enabled = false;
            Application.getMainFrame().getStatusBar().getProgressBar().setIndeterminate(true);
            workerObject = new ActionWorker(this, ae, "Cutting Data...", "Data cut.");
            workerObject.execute();
        }
    }
    
    public void actionPerformedInWorkerThread(ActionEvent ae)
    {
         CopyAction.getInstance().actionPerformedInWorkerThread(ae);
         DeleteAction.getInstance().actionPerformedInWorkerThread(ae);
    }
    
    public void actionPerformedWhenDone(ActionEvent ae)
    {
        CopyAction.getInstance().actionPerformedWhenDone(ae);
        DeleteAction.getInstance().actionPerformedWhenDone(ae);
        workerObject = null;
        enabled = true;
    }
    
}
