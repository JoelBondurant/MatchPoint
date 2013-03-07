package com.analyticobjects.matchpoint.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author Joel
 * @version 1.0, 10/16/2006
 */
public class JFontComboBox extends JComboBox
{
    private static Font[] systemFonts;
    private static String[] systemFontNames;
    private JPopupMenu pop;
    
    static {
        Font[] allFonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
        boolean[] allFontsIndexPrintable = new boolean[allFonts.length];
        int printableFonts = 0;
        char theLetterA = 'a';
        for(int i = 0; i<allFonts.length; i++)
        {
            if(allFonts[i].canDisplay(theLetterA))
            {
                printableFonts++;
                allFontsIndexPrintable[i] = true;
            }
            else
            {
                allFontsIndexPrintable[i] = false;
            }
        }
        systemFonts = new Font[printableFonts];
        systemFontNames = new String[printableFonts];
        int counter = 0;
        for(int i=0; i<allFonts.length; i++)
        {
            if(allFontsIndexPrintable[i])
            {
                systemFonts[counter] = allFonts[i];
                counter++;
            }
        }
        for(int i=0; i<printableFonts; i++)
        {
            systemFonts[i] = systemFonts[i].deriveFont(18.0f);
            systemFontNames[i] = systemFonts[i].getName();
        }
    }
    
    /** Creates a new instance of JFontComboBox */
    public JFontComboBox()
    {
        super(systemFontNames);
        this.setRenderer(new FontCellRenderer());
        this.setPreferredSize(new Dimension(220,this.getPreferredSize().height));
        this.addMouseWheelListener(new ScrollWheelActivity());
    }
    
    public Font getSelectedFont()
    {
        int selectedIndex = this.getSelectedIndex();
        return systemFonts[selectedIndex];
    }
    
    public static Font getFontFromName(String name)
    {
        Font aFont = null;
        for(Font sysFont: systemFonts)
        {
            if(sysFont.getName()==name){aFont = sysFont;}
        }
        return aFont;
    }
    
     private class FontCellRenderer extends JLabel implements ListCellRenderer 
     {

         public FontCellRenderer() 
         {
             setOpaque(true);
         }
         
         public Component getListCellRendererComponent(JList list,Object value,int index,boolean isSelected,boolean cellHasFocus)
         {
             setText(value.toString());
             Font aFont = JFontComboBox.getFontFromName(value.toString());
             if(aFont != null){this.setFont(aFont);}
             setBackground(isSelected ? SystemColor.textHighlight : Color.WHITE);
             //setForeground(isSelected ? Color.BLACK : Color.BLACK);
             return this;
         }
     }//end of FontCelRenderer class
     
     private class ScrollWheelActivity implements MouseWheelListener
     {
         private JFontComboBox c = JFontComboBox.this;
         private int x = 0;
         public void mouseWheelMoved(MouseWheelEvent mwe)
         {
             if(!c.isPopupVisible())
             {
                 x = c.getSelectedIndex() + mwe.getWheelRotation();
                 x = Math.max(0,x);
                 x = Math.min(x,c.getItemCount());
                 c.setSelectedIndex(x);
             }
         }
     }//end of ScrollWheelActivity class

}//end of JFontComboBox class  <------------------------>
