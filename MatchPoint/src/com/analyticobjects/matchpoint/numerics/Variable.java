package com.analyticobjects.matchpoint.numerics;

import java.util.*;

/**
 * The Variable class defines the API for which one dimensional vectors are accessed.
 * 
 * @author Joel
 */
public abstract class Variable implements Statistics
{
    public static final int INITIAL_CAPACITY = 256;  //The initial size of the data container.
    
    public abstract int getSize();
    public abstract int getCapacity();
    public abstract void trimToSize();
    public abstract void ensureCapacity(int minCapacity);
    public abstract boolean isEmpty();
    public abstract Object remove(int index);
    public abstract boolean remove(int[] rows);
    public abstract void clear();
    public abstract void setValueAt(Object aValue, int row);
    public abstract Object getValueAt(int row);
    
    protected boolean dirty = true;
    private String name;  //The name of the variable.  e.g.  x, y, length, viscosity, heart_rate...
    private Integer size;
    private Object total;
    private Object sumOfSquares;
    private Object mean;
    private Object variance;
    private Object minimum;
    private Object maximum;
    private Object diameter;
   
    protected abstract Integer calculateSize();
    protected abstract Object calculateTotal();
    protected abstract Object calculateSumOfSquares();
    protected abstract Object calculateMean();
    protected abstract Object calculateVariance();
    protected abstract Object calculateMinimum();
    protected abstract Object calculateMaximum();
    protected abstract Object calculateDiameter();
    
    public void updateStats()
    {
        if(dirty)
        {
            size = calculateSize();
            total = calculateTotal();
            mean = calculateMean();
            sumOfSquares = calculateSumOfSquares();
            variance = calculateVariance();
            minimum = calculateMinimum();
            maximum = calculateMaximum();
            diameter = calculateDiameter();
        }
        dirty = false;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return name;
    }
    
    public Integer getElementCount()
    {
        if(dirty){updateStats();}
        return size;
    }
    
    public Object getTotal()
    {
        if(dirty){updateStats();}
        return total;
    }
    
    public Object getSumOfSquares()
    {
        if(dirty){updateStats();}
        return sumOfSquares;
    }
    
    
    public Object getMean()
    {
        if(dirty){updateStats();}
        return mean;
    }
    
    public Object getVariance()
    {
        if(dirty){updateStats();}
        return variance;
    }
    
    public Object getMinimum()
    {
        if(dirty){updateStats();}
        return minimum;
    }
        
    public Object getMaximum()
    {
        if(dirty){updateStats();}
        return maximum;
    }
    
    public Object getDiameter()
    {
        if(dirty){updateStats();}
        return diameter;
    }
    
}
