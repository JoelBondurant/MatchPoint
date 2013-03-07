/*
 * Application.java
 *
 * Created on February 18, 2006, 2:44 AM
 * 
 */
package com.analyticobjects.matchpoint;

import com.analyticobjects.matchpoint.gui.MainFrame;
import java.util.prefs.*;
import java.awt.*;
import javax.swing.*;
/**
 * This class represents the MatchPoint application, and holds the main point of
 * entry for the application execution.
 * @author Joel Bondurant
 * @version 0.01
 */
public class Application 
{
    private static MainFrame matchPointMainFrame;
    public static final String name = "MatchPoint";
    public static final String version = "0.01";
    private static Preferences systemPrefs;
    private static Preferences userPrefs;

    private Application(){}

    /**
     * The start of the whole Matchpoint application.
     *
     * @param args Command line arguments passed to the MatchPoint application
     */
    public static void main(String[] args)
    {
       matchPointMainFrame = new MainFrame();
       matchPointMainFrame.initGUI();
       matchPointMainFrame.setVisible(true);
       matchPointMainFrame.requestFocus();//what does this do?
       SwingUtilities.invokeLater(new Runnable()
       {
           public void run(){Thread.currentThread().setPriority(Thread.MAX_PRIORITY);}
       });
    }
    
    public static MainFrame getMainFrame()
    {
        return matchPointMainFrame;
    }
    
}
