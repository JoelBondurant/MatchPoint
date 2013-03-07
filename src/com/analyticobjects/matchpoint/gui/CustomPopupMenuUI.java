/*
 * CustomPopupMenuIUI.java
 *
 * Created on March 22, 2006, 12:41 AM
 *
 * Copyright 2006 Analytic Objects. All rights reserved.
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.analyticobjects.matchpoint.gui;

/**
 *
 * @author Joel
 */
import java.awt.*;
//import java.awt.image.*;
import javax.swing.*;
import javax.swing.plaf.*;
//import javax.swing.plaf.metal.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.*;


class CustomPopupMenuUI extends BasicPopupMenuUI {
    public static ComponentUI createUI(JComponent c) {
        return new CustomPopupMenuUI();
    }
    
    public Popup getPopup(JPopupMenu popup, int x, int y) {
        Popup pp = super.getPopup(popup,x,y);
        JPanel panel = (JPanel)popup.getParent();
        panel.setBorder(new ShadowBorder(2,2));
        panel.setOpaque(false);
        return pp;
    }
}

class ShadowBorder extends AbstractBorder {
    int xoff, yoff;
    Insets insets;
    public ShadowBorder(int x, int y) {
        this.xoff = x;
        this.yoff = y;
        insets = new Insets(0,0,xoff,yoff);
    }
    
    public Insets getBorderInsets( Component c ) {
        return insets;
    }
    
    public void paintBorder(Component comp, Graphics g, int x, int y, int width, int height) {
        Graphics2D gx = (Graphics2D) g;
        gx.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.5f));
        gx.setColor(Color.black);
        gx.translate(x,y);
        gx.fillRect(width-xoff, yoff, xoff, height-yoff);
        gx.fillRect(xoff, height-yoff, width-xoff, yoff);
        gx.translate(-x,-y);
        gx.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1.0f));
    }
       
   
}
