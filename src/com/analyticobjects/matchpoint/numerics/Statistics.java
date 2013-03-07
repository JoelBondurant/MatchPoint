package com.analyticobjects.matchpoint.numerics;

/**
 *
 * @author Joel
 * @version 1.0, 10/16/2006
 */
interface Statistics
{
    public String getName();
    public Object getElementCount();
    public Object getMinimum();
    public Object getMaximum();
    public Object getDiameter();
    public Object getTotal();
    public Object getSumOfSquares();
    public Object getMean();
    public Object getVariance();
}
