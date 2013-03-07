package com.analyticobjects.matchpoint.chart;

import com.analyticobjects.matchpoint.numerics.*;
import java.awt.*;
/**
 * The Axis class is concerned with ties together an AxysStyle and a Variable
 *
 * @author Joel
 * @version 1.0, 09/20/2006
 */
public class Axis
{
    
    private AxisStyle axisStyle;
    private Variable variable;
    private AxisScale axisScale;
    
    /** Creates a new instance of Axis */
    public Axis(Variable var)
    {
        variable = var;
        //I dont' pass the var to the style since the graph is drawn when 
        //var is empty.
        axisStyle = new AxisStyle();
        axisScale = new LinearAxisScale(axisStyle);
    }
    

    
    public Variable getVariable(){return variable;}
    public AxisStyle getStyle(){return axisStyle;}
    public AxisScale getScale(){return axisScale;}
    public void setScale(AxisScale scale){this.axisScale = scale;}
    
}
