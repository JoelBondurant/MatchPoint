/*
 * MainFrame.java
 *
 * Created on February 18, 2006, 2:57 AM
 *
 */
package com.analyticobjects.matchpoint.gui;

import com.analyticobjects.matchpoint.*;
import com.analyticobjects.matchpoint.chart.*;
import com.analyticobjects.matchpoint.chart.ChartPropertiesAction;
import com.analyticobjects.matchpoint.icons.*;
import java.awt.*;
import javax.swing.*;
import static javax.swing.JSplitPane.*;
import javax.swing.border.*;
import java.awt.event.*;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * This class represents the primary MatchPoint MainFrame.
 * 
 * @author Joel Bondurant
 */
public class MainFrame extends JFrame
{
    
    /**
     * Creates a new instance of MainFrame
     */
    public MainFrame()
    {
        super(Application.name + " " + Application.version);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public void initGUI()
    {
        Container content = this.getContentPane();
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            try  //try to use the system look and feel
            {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex2){
            
            } // oh well, default look will do
        }
        
        Toolkit kit = Toolkit.getDefaultToolkit();
        int screenDPI = kit.getScreenResolution();
        Dimension screenSize = kit.getScreenSize();
        
        Point screenCenter = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        setBounds(30,30,Math.min(screenCenter.x*2-60,1280),Math.min(screenCenter.y*2-60,1024));
        setIconImage(new ImageIcon(IconClassLoader.class.getResource("logo16.png")).getImage());
        
        //Construct the menu bar
        JMenuBar mainMenu = new JMenuBar();
        //mainMenu.setBorder(BorderFactory.createEtchedBorder());
        
        //Create the File Menu from a set of Action objects
        JMenu fileMenu = new JMenu(Strings.fileMenuName);
        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.add(OpenAction.getInstance()).setIcon(null);
        fileMenu.add(SaveAction.getInstance()).setIcon(null);
        fileMenu.add(SaveAsAction.getInstance()).setIcon(null);
        fileMenu.addSeparator();
        fileMenu.add(ExitAction.getInstance()).setIcon(null);
        mainMenu.add(fileMenu);
        
        editMenu = new JMenu(Strings.editMenuName);
        editMenu.setMnemonic(KeyEvent.VK_E);
        editMenu.add(CopyAction.getInstance()).setIcon(null);
        editMenu.add(PasteAction.getInstance()).setIcon(null);
        editMenu.add(CutAction.getInstance()).setIcon(null);
        editMenu.add(SelectAllAction.getInstance()).setIcon(null);
        editMenu.add(DeleteAction.getInstance()).setIcon(null);
        mainMenu.add(editMenu);
        
        JMenu chartMenu = new JMenu(Strings.chartMenuName);
        chartMenu.setMnemonic(KeyEvent.VK_C);
        //chartMenu.add(ChartPropertiesAction.getInstance());
        mainMenu.add(chartMenu);
        
        setJMenuBar(mainMenu);
        
        mainToolbar = new JToolBar();
        mainToolbar.setRollover(true);
        mainToolbar.addSeparator();
        JButton tbref;
        tbref = mainToolbar.add(OpenAction.getInstance());
        tbref.setFocusPainted(false);
        tbref.setFocusable(false);
        tbref = mainToolbar.add(CopyAction.getInstance());
        tbref.setFocusPainted(false);
        tbref.setFocusable(false);
        tbref = mainToolbar.add(PasteAction.getInstance());
        tbref.setFocusPainted(false);
        tbref.setFocusable(false);
        tbref = mainToolbar.add(CutAction.getInstance());
        tbref.setFocusPainted(false);
        tbref.setFocusable(false);
        tbref = mainToolbar.add(DeleteAction.getInstance());
        tbref.setFocusPainted(false);
        tbref.setFocusable(false);
        tbref = null;
        
        mainToolbar.setPreferredSize(new Dimension(200,30));
        mainToolbar.setFloatable(false);
        mainToolbar.setOrientation(Scrollbar.HORIZONTAL);
        mainToolbar.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        scriptToolBar = ScriptToolBar.getInstance();
                       
        JPanel topToolbarPanel = new JPanel();
        topToolbarPanel.setLayout(new BoxLayout(topToolbarPanel,BoxLayout.Y_AXIS));
        topToolbarPanel.add(mainToolbar);
        topToolbarPanel.add(scriptToolBar);
        content.add(topToolbarPanel, BorderLayout.NORTH);
        
        content.add(statusBar, BorderLayout.SOUTH);
        graphToolbar = new JToolBar();
        graphToolbar.setPreferredSize(new Dimension(30,200));
        graphToolbar.setFloatable(false);
        graphToolbar.setOrientation(Scrollbar.VERTICAL);
        graphToolbar.addSeparator();
        graphToolbar.setBorder(BorderFactory.createEtchedBorder());

        Icon variableGreen16;
        variableGreen16 = new ImageIcon(IconClassLoader.class.getResource("variableGreen16.gif"));
               
        dataTable = DataTable.getInstance();
        dataTable.setName("Data Table");
        
        
        jdtsp = new JDataTableScrollPane(dataTable);
        Border jdtspb = BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(5,5,5,5,SystemColor.control),BorderFactory.createLineBorder(SystemColor.control.darker()));
        jdtspb = BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(jdtspb,"Data Table",TitledBorder.CENTER,TitledBorder.BELOW_TOP,Font.getFont("Serif"),Color.BLACK),jdtspb);
        jdtsp.setBorder(jdtspb);
        jdtsp.setMinimumSize(new Dimension(100,100));
        
        graphPanel = new JPanel();
        graphPanel.setLayout(new BorderLayout());
        chart = Chart.getInstance();
        graphPanel.add(chart, BorderLayout.CENTER);
        graphPanel.add(graphToolbar, BorderLayout.EAST);
        graphPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        topPanes = new JSplitPane(HORIZONTAL_SPLIT, true, jdtsp, graphPanel);
        //topPanes.setContinuousLayout(false);
        topPanes.setOneTouchExpandable(true);
        topPanes.setDividerSize(9);
        topPanes.setResizeWeight(0.0);
        topPanes.setUI(new javax.swing.plaf.metal.MetalSplitPaneUI());
        statsPane = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
        
        fitTable = new JTable(500,20);
        fitTable.setName("Fit Models Table");
        fitTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        statsTable = StatisticsTable.getInstance();
        statsTable.setName("Statistics Table");
        statsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableManager = new JTableManager();
        tableManager.add(dataTable).add(fitTable).add(statsTable);
        
        //dataTabbedPane.add(Strings.dataTableTitle, new JDataTableScrollPane(dataTable));
        statsPane.add(Strings.dataStatisticsTableTitle,new JDataTableScrollPane(statsTable));
        statsPane.add(Strings.fitModelsTableTitle,new JDataTableScrollPane(fitTable));
        allPanes = new JSplitPane(VERTICAL_SPLIT, true, topPanes, statsPane);
        //allPanes.setContinuousLayout(false);
        allPanes.setOneTouchExpandable(true);
        allPanes.setDividerSize(9);
        allPanes.setResizeWeight(0.9);
        allPanes.setUI(new javax.swing.plaf.metal.MetalSplitPaneUI());
        content.add(allPanes, BorderLayout.CENTER);

        topPanes.setDividerLocation(305);
        allPanes.setDividerLocation(600);
        chartMenu.add(ChartPropertiesAction.getInstance()); // must be added after the chart is created!!!
    }
    
    
    private JTableManager tableManager;
    public JTableManager getTableManager(){return this.tableManager;}
    private Chart chart;
    public Chart getChart(){return this.chart;}
    private JTable dataTable, fitTable, statsTable;
    public JTable getDataTable(){return this.dataTable;}
    private JTabbedPane statsPane;
    private JSplitPane topPanes;
    public JSplitPane getTopSplitPane(){return topPanes;}
    private JSplitPane allPanes;
    public JSplitPane getBottomSplitPane(){return allPanes;}
    private StatusBar statusBar = new StatusBar();
    public StatusBar getStatusBar(){return statusBar;}  
    private JMenuItem openItem;
    private JToolBar mainToolbar;
    private JToolBar graphToolbar;
    private JPanel graphPanel;
    private JDataTableScrollPane jdtsp;
    private JMenu editMenu;
    private ScriptToolBar scriptToolBar;
}
