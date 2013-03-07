package com.analyticobjects.matchpoint.gui;

import com.analyticobjects.matchpoint.Application;
import com.analyticobjects.matchpoint.scripting.*;
import com.analyticobjects.matchpoint.icons.*;
import java.lang.reflect.InvocationTargetException;
import javax.swing.plaf.basic.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import javax.swing.text.*;

/**
 *
 * @author Joel
 * @version 1.0, 12/05/2006
 */
public class ScriptToolBar extends JPanel
{
    private static ScriptToolBar uniqueInstance;
    private ScriptManager scriptMgr;
    private JComboBox comboBox;
    private DefaultComboBoxModel comboBoxModel;
    private int maxScriptsSize;
    private JSplitPane splitPane;
    private JPanel leftPanel, rightPanel;
    private BasicComboBoxEditor editor;
    private ScriptAction scriptAction;
    private EditorAction editorAction;
    private JLabel scriptMessage;
    private Dimension comboBoxMinSize;
    private Object scriptReturnObject;
    private JProgressBar progressBar;
    private ScriptWorker scriptWorker;
    private JButton executeButton;
    private JButton stopButton;
    private JToolBar rightToolBar;
    
    public JComboBox getComboBox(){return comboBox;}
    public DefaultComboBoxModel getModel(){return comboBoxModel;}
    public void setScriptMessage(String str)
    {
        scriptMessage.setText(str);
    }
    
    public JLabel getScriptMessage(){return scriptMessage;}
    
    public static ScriptToolBar getInstance()
    {
        if(uniqueInstance==null)
        {
            uniqueInstance = new ScriptToolBar();
        }
        return uniqueInstance;
    }

    private ScriptToolBar()
    {
        progressBar = Application.getMainFrame().getStatusBar().getProgressBar();
        scriptReturnObject = null;
        maxScriptsSize = 50;
        comboBoxMinSize = new Dimension(150,20);
        this.setLayout(new BorderLayout());
        //this.setBorder(BorderFactory.createEtchedBorder());
        scriptMgr = ScriptManager.getInstance();
        comboBoxModel = new DefaultComboBoxModel();
        comboBox = new JComboBox();
        comboBox.setModel(comboBoxModel);
        comboBox.setMinimumSize(comboBoxMinSize);
        editor = (BasicComboBoxEditor)comboBox.getEditor();
        editorAction = new EditorAction();
        editor.addActionListener(editorAction);
        this.setMaximumSize(new Dimension(5000,31));
        this.setPreferredSize(new Dimension(5000,31));
        comboBox.setEditable(true);
        scriptAction = new ScriptAction();
        ComboBoxSelectionChangeListener cbscl = new ComboBoxSelectionChangeListener();
        comboBox.getEditor().getEditorComponent().addKeyListener(cbscl);
        comboBox.addMouseWheelListener(cbscl);
        comboBox.addActionListener(scriptAction);
        comboBox.setMaximumSize(new Dimension(5000,22));
        comboBox.setPreferredSize(new Dimension(5000,22));
        leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(300,31));
        leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.X_AXIS));
        //leftPanel.setBorder(BorderFactory.createRaisedBevelBorder());
        leftPanel.add(Box.createHorizontalStrut(3));
        leftPanel.add(comboBox);
        rightPanel = new JPanel();
        rightToolBar = new JToolBar();
        rightToolBar.setFloatable(false);
        rightToolBar.setRollover(true);
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(rightToolBar, BorderLayout.NORTH);
        //rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.X_AXIS));
        //rightPanel.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        
        //executeButton = new JButton(new ExecuteAction());
        executeButton = rightToolBar.add(new ExecuteAction());
        executeButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        //executeButton.setContentAreaFilled(false);
        executeButton.setText(null);
        executeButton.setFocusPainted(false);
        executeButton.setFocusable(false);
        Dimension dim = new Dimension(21,21);
        //executeButton.setMaximumSize(dim);
        //executeButton.setPreferredSize(dim);
        //executeButton.setMinimumSize(dim);
        //stopButton = new JButton(new StopAction());
        stopButton = rightToolBar.add(new StopAction());
        stopButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        //stopButton.setContentAreaFilled(false);
        stopButton.setText(null);
        stopButton.setFocusPainted(false);
        stopButton.setFocusable(false);
        //stopButton.setMaximumSize(dim);
        //stopButton.setPreferredSize(dim);
        //stopButton.setMinimumSize(dim);
        //rightPanel.add(executeButton);
        //rightPanel.add(stopButton);
        rightToolBar.add(executeButton);
        //rightToolBar.add(Box.createHorizontalStrut(3));
        rightToolBar.add(stopButton);
        rightToolBar.addSeparator();
        scriptMessage = new JLabel(" ");
        scriptMessage.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
        //rightPanel.add(Box.createHorizontalGlue());
        rightToolBar.add(Box.createHorizontalGlue());
        //rightPanel.add(scriptMessage);
        rightToolBar.add(scriptMessage);
        rightToolBar.addSeparator();
        rightToolBar.setBorderPainted(false);
        rightPanel.setBorder(BorderFactory.createRaisedBevelBorder());
        //rightPanel.add(Box.createHorizontalStrut(20));
        rightToolBar.add(Box.createHorizontalStrut(20));
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, leftPanel, rightPanel);
        splitPane.setUI(new javax.swing.plaf.metal.MetalSplitPaneUI());
        this.add(BorderLayout.CENTER,splitPane);
        this.setAlignmentX(Component.LEFT_ALIGNMENT);
        //comboBox.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(),BorderFactory.createEmptyBorder(1,3,1,3)));
    }
    
    public void setMaxScriptsSize(int num)
    {
        if(num>0){maxScriptsSize = num;}
    }
    
    private boolean finishedEditing = false;
    
    private class EditorAction implements ActionListener
    {
        synchronized public void actionPerformed(ActionEvent ae)
        {
            if(!ScriptWorker.isEnabled()){return;}
            finishedEditing = true;
        }
    }
    
    private boolean cbsc = false;
    private class ComboBoxSelectionChangeListener implements MouseWheelListener, KeyListener
    {
        private int x1 = 0;
        private int x2 = 0;
        public void mouseWheelMoved(MouseWheelEvent mwe)
        {
            if(!comboBox.isPopupVisible())
            {
                x1 = comboBox.getSelectedIndex() + mwe.getWheelRotation();
                x1 = Math.max(0,x1);
                x1 = Math.min(x1,comboBox.getItemCount()-1);
                comboBox.setSelectedIndex(x1);
                ScriptToolBar.this.cbsc = true;
            }
        }
        
        public void keyPressed(KeyEvent ke)
        {
            boolean change = false;
            if(ke.getKeyCode() == KeyEvent.VK_UP)
            {
                x2--;
                change = true;
            }
            if(ke.getKeyCode() == KeyEvent.VK_DOWN)
            {
                x2++;
                change = true;
            }
            if(change)
            {
                x2 = Math.max(0,x2);
                x2 = Math.min(x2,comboBox.getItemCount()-1);
                comboBox.setSelectedIndex(x2);
                ScriptToolBar.this.cbsc = true;
                ke.consume();
            }
            else
            {
                ScriptToolBar.this.cbsc = false;
            }
        }
        public void keyReleased(KeyEvent ke){}
        public void keyTyped(KeyEvent ke){}
    }
    
    private class ScriptAction implements ActionListener
    {
        private Dimension minSize = new Dimension(50,20);
        synchronized public void actionPerformed(ActionEvent ae)
        {
            if(ScriptToolBar.this.cbsc){ScriptToolBar.this.cbsc = false; return;}
            if(!ScriptWorker.isEnabled()){return;}
            long startTime = System.nanoTime();
            int selIndx = comboBox.getSelectedIndex();
            String script = (String)(comboBox.getSelectedItem());
            if(script == null){return;}
            if(script.isEmpty()){return;}
            if(!strAdded)
            {
                ScriptToolBar.this.addToTopOfComboBoxModel(script);
            }
            strAdded = false;
            //System.out.println("Selected Index = " + selIndx);
            //System.out.println(ae.paramString());
            if(selIndx > 0 || !finishedEditing) //if the user selected from the list, edit it
            {
                comboBox.setSelectedIndex(0);
                return;
            }
            if(selIndx == 0)
            {
                finishedEditing = false;
            }
            progressBar.setIndeterminate(true);
            progressBar.setString("Script running...");
            comboBox.setSelectedIndex(-1);
            if(comboBoxModel.getSize()==maxScriptsSize+1)
            {
                comboBoxModel.removeElementAt(maxScriptsSize);
            }
            scriptWorker = new ScriptWorker(script);
            scriptWorker.execute();
        }
    }
    
    private void addToTopOfComboBoxModel(String script)
    {
        int repIndx = findScriptIndex(script);
        if(repIndx == -1)
        {
            comboBoxModel.insertElementAt(script,0);
        }
        else
        {
            comboBoxModel.removeElementAt(repIndx);
            comboBoxModel.insertElementAt(script,0);
        }
    }

    private int findScriptIndex(String script)
    {
        int index = -1;
        for(int i=0; i<comboBoxModel.getSize(); i++)
        {
            if( ((String)comboBoxModel.getElementAt(i)).equals(script) )
            {
                index = i;
            }
        }
        return index;
    }
    
    private boolean strAdded = false;
    private class ExecuteAction extends AbstractAction
    {
        public ExecuteAction()
        {
            super(Strings.executeActionTitle, new ImageIcon(IconClassLoader.class.getResource("scriptStartIcon.png")));
            //putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            putValue(SHORT_DESCRIPTION, Strings.executeActionShort);
            putValue(LONG_DESCRIPTION, Strings.executeActionShort);
            //putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_O));
        }

        synchronized public void actionPerformed(ActionEvent ae)
        {
            if(!ScriptWorker.isEnabled()){return;}
            ScriptToolBar.this.finishedEditing = true;
            String script = (String)ScriptToolBar.this.editor.getItem();
            if(script==null){return;}
            if(script.isEmpty()){return;}
            //ScriptToolBar.this.addToTopOfComboBoxModel(script);
            comboBoxModel.insertElementAt(script,0);
            strAdded = true;
            comboBox.setSelectedIndex(0);
            ScriptToolBar.this.scriptAction.actionPerformed(ae);
            String str0 = (String)comboBoxModel.getElementAt(0);
            String str1 = (String)comboBoxModel.getElementAt(1);
            if(str0.equals(str1))
            {
                comboBoxModel.removeElementAt(0);
            }
        }
    }
    
    private class StopAction extends AbstractAction
    {
        public StopAction()
        {
            super(Strings.stopActionTitle, new ImageIcon(IconClassLoader.class.getResource("scriptStopIcon.png")));
            //putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            putValue(SHORT_DESCRIPTION, Strings.stopActionShort);
            putValue(LONG_DESCRIPTION, Strings.stopActionShort);
            //putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_O));
        }

        public void actionPerformed(ActionEvent ae)
        {
            if(ScriptToolBar.this.scriptWorker == null){return;}
            if(ScriptToolBar.this.scriptWorker.isDone()){return;}
            ScriptToolBar.this.scriptWorker.cancel(true);
            ScriptToolBar.this.setScriptMessage("Script execution stopped.");
        }
    }
}
