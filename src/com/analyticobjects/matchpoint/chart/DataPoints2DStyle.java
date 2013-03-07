package com.analyticobjects.matchpoint.chart;

import com.analyticobjects.matchpoint.numerics.*;
import java.awt.*;
import java.awt.geom.*;
/**
 *
 * @author Joel
 * @version 1.0, 09/24/2006
 */
public class DataPoints2DStyle
{
    
    private Paint paint;
    private Color borderColor;
    private RectangularShape symbol;
    private RectangularShape[] symbols;
    /**
     * Creates a new instance of DataPoints2DStyle
     */
    public DataPoints2DStyle()
    {
        paint = new Color(50,160,240);
        borderColor = new Color(0,0,0);
        symbols = new RectangularShape[2];
        symbols[0] = new Ellipse2D.Double();
        symbols[0].setFrame(0.0,0.0,8.0,8.0);
        symbols[1] = new Rectangle.Double();
        symbols[1].setFrame(0.0,0.0,8.0,8.0);
        symbol = symbols[0];
    }
    
    public Paint getSymbolFillPaint(){return paint;}
    public void setSymbolFillPaint(Paint paint){this.paint = paint;}
    public Color getSymbolBorderColor(){return borderColor;}
    public void setSymbolBorderColor(Color borderColor){this.borderColor = borderColor;}
    public RectangularShape getSymbol(){return symbol;}
    public void setSymbol(RectangularShape symbol){this.symbol = symbol;}
    
    
}
