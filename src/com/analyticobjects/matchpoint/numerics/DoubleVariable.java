package com.analyticobjects.matchpoint.numerics;

import java.math.BigDecimal;

/**
 * Resizable-array to store doubles.  All of this because Java (1.5) does not allow ArrayList&lt;double&gt;.
 *
 * @author  Joel Bondurant
 * @version 1.00, 03/18/2006
 */
public class DoubleVariable extends Variable
{
    private double[] elementData;  //The array into which the elements of the DoubleVariable are stored.
    private MutableDouble objectValue;  //A way to get values as Double when needed.
    private int size;  //The size of the DoubleVariable (the number of elements it contains).
    private MutableDouble minimum, maximum;
    private BigDecimal sizeDecimal, total, sumOfSquares, mean, variance, diameter;
    private Integer sizeObject;


    /**
     * Constructs an empty array with the specified initial capacity.
     *
     * @param   initialCapacity   The initial capacity of the array.
     * @exception IllegalArgumentException if the specified initial capacity
     *            is negative
     */
    public DoubleVariable(int initialCapacity)
    {
        size = 0;
        objectValue = new MutableDouble();
        if (initialCapacity < 0)
        {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
        elementData = new double[initialCapacity];
        sizeObject = new Integer(0);
        minimum = new MutableDouble();
        maximum = new MutableDouble();
    }

    /**
     * Constructs an empty list with an initial capacity of INITIAL_CAPACITY (1000).
     */
    public DoubleVariable() 
    {
	this(INITIAL_CAPACITY);
    }
    
    public Integer calculateSize()
    {
        sizeDecimal = BigDecimal.valueOf((long)size);
        return sizeObject.valueOf(size);
    }
    
    public Object calculateTotal()
    {
        if(size==0){return null;}
        total = BigDecimal.ZERO;
        for(int i = 0; i<size; i++)
        {
            total = total.add(BigDecimal.valueOf(elementData[i]));
        }
        return new Double(total.doubleValue());
    }
    
    public Object calculateSumOfSquares()
    {
        if(size==0){return null;}
        BigDecimal bigX, bigXSquared;
        double x = 0;
        sumOfSquares = BigDecimal.ZERO;
        for(int i = 0; i<size; i++)
        {
            x = elementData[i];
            bigX = BigDecimal.valueOf(x);
            bigXSquared = bigX.multiply(bigX);
            sumOfSquares = sumOfSquares.add(bigXSquared);
        }
        return new Double(sumOfSquares.doubleValue());
    }
    
    public Object calculateMean()
    {
        if(size==0){return null;}
        if(sizeDecimal.compareTo(BigDecimal.ZERO)==0){return null;}
        mean = total.divide(sizeDecimal,500,BigDecimal.ROUND_HALF_EVEN);
        return new Double(mean.doubleValue());
    }
    
    public Object calculateVariance()
    {
        if(size==0){return null;}
        if(size==1)
        {
            variance = BigDecimal.ZERO;
            return variance;
        }

        BigDecimal bigMean = mean;
        BigDecimal bigSumOfSquares = sumOfSquares;
        BigDecimal bigSize = sizeDecimal;
        BigDecimal bigMeanSquared = bigMean.multiply(bigMean);
        BigDecimal bigVarianceNumerator = bigSumOfSquares.subtract(bigSize.multiply(bigMeanSquared));
        BigDecimal bigVarianceDenominator = bigSize.subtract(BigDecimal.ONE);
        if(bigVarianceDenominator.compareTo(BigDecimal.ZERO)==0)
        {
            return null;
        }
        BigDecimal bigVariance = bigVarianceNumerator.divide(bigVarianceDenominator,500,BigDecimal.ROUND_HALF_EVEN);
        variance = bigVariance;
        return new Double(variance.doubleValue());
    }
    
    
    public Object calculateMinimum()
    {
        if(size==0){return null;}
        double x = elementData[0];
        for(int i = 1; i<size; i++)
        {
            if(elementData[i]<x){x=elementData[i];}
        }
        minimum.setValue(x);
        return minimum;
    }
        
    public Object calculateMaximum()
    {
        if(size==0){return null;}
        double x = elementData[0];
        for(int i = 1; i<size; i++)
        {
            if(elementData[i]>x){x=elementData[i];}
        }
        maximum.setValue(x);
        return maximum;
    }

    public Object calculateDiameter()
    {
        if(size==0){return null;}
        BigDecimal max = BigDecimal.valueOf(maximum.doubleValue());
        BigDecimal min = BigDecimal.valueOf(minimum.doubleValue());
        diameter = max.subtract(min);
        return new Double(diameter.doubleValue());
    }
    /**
     * Trims the capacity of this <tt>DoubleVariable</tt> instance to be the
     * array's current size.  A programmer can use this operation to minimize
     * the storage of a <tt>DoubleVariable</tt> instance.
     */
    public void trimToSize() 
    {
	int oldCapacity = elementData.length;
	if(getSize() < oldCapacity) 
        {
	    double oldData[] = elementData;
	    elementData = new double[getSize()];
	    System.arraycopy(oldData, 0, elementData, 0, getSize());
	}
    }

    /**
     * Increases the capacity of this <tt>DoubleVariable</tt> instance, if
     * necessary, to ensure that it can hold at least the number of elements
     * specified by the minimum capacity argument. 
     * 
     * @param minCapacity   the desired minimum capacity.
     */
    public void ensureCapacity(int minCapacity)
    {
	int oldCapacity = elementData.length;
	if (minCapacity > oldCapacity) 
        {
	    double oldData[] = elementData;
	    int newCapacity = (oldCapacity * 3)/2;
    	    if (newCapacity < minCapacity)
            {
                newCapacity = minCapacity;
            }
	    elementData = new double[newCapacity];
	    System.arraycopy(oldData, 0, elementData, 0, getSize());
	}
    }

    /**
     * Returns the number of elements in the underlying data container.
     *
     * @return  the number of elements in the underlying data container.
     */
    public int getSize() 
    {
	return size;
    }
    
    /**
     * Method to set the size data member to set a standard internal API.
     * Additional rules and event handling for size property changes can be
     * consolidated here. 
     */
    private void setSize(int size)
    {
        dirty = true;
        this.size = size;
    }

    /**
     * Tests whether the <tt>DoubleVariable</tt> has any elements.
     *
     * @return <tt>true</tt> if the underlying data container has zero elements;
     *         <tt>false</tt> otherwise.
     */
    public boolean isEmpty() 
    {
	return (getSize() == 0);
    }

    /**
     * Returns a copy of the <tt>DoubleVariable</tt> instance.  (The
     * elements themselves are copied.)
     * 
     * @return a clone of this <tt>DoubleVariable</tt> instance.
     */
    public Object clone() 
    {
        DoubleVariable cpy = new DoubleVariable(elementData.length);
        cpy.setSize(this.getSize());
	System.arraycopy(elementData, 0, cpy.elementData, 0, getSize());
	return cpy;
    }

    /**
     * Returns a copy of the array contained in this Resizable array.
     *
     * @return a copy of the array contained in this Resizable array.
     */
    public double[] toArray() 
    {
	double[] result = new double[size];
	System.arraycopy(elementData, 0, result, 0, size);
	return result;
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param  Index index of element to return.
     * @return The element at the specified position in this list.
     * @throws IndexOutOfBoundsException if index is out of range <tt>(index
     * 	&lt; 0 || index &gt;= size())</tt>.
     */
    public double get(int index)
    {
	rangeCheck(index);
	return elementData[index];
    }
 
    /**
     * Returns the element at the specified position in this list as an Object 
     * for convenient use with classes that accept Doubles but not doubles.
     * Initially created for to help implement the 
     * javax.swing.table.AbstractTableModel.
     *
     * @param  Index index of element to return.
     * @return The element at the specified position in this list.
     * @throws    IndexOutOfBoundsException if index is out of range <tt>(index
     * 		  &lt; 0 || index &gt;= size())</tt>.
     */
    public Object getValueAt(int row)
    {
        if( row < getSize() )
        {
            objectValue.setValue(this.get(row));
            return objectValue;
        }
        return null;
    }
    

    /**
     * Replaces the element at the specified position in this list with
     * the specified element.
     *
     * @param index index of element to replace.
     * @param element element to be stored at the specified position.
     * @return the element previously at the specified position.
     * @throws    IndexOutOfBoundsException if index out of range
     *		  <tt>(index &lt; 0 || index &gt;= size())</tt>.
     */
    public double set(int index, double element)
    {
        dirty = true;
	rangeCheck(index);
	double oldValue = elementData[index];
	elementData[index] = element;
	return oldValue;
    }
    
    /**
     * Not sure what the purpose of this method is?
     *
     * @param index index of element to replace.
     * @param element element to be stored at the specified position.
     * @return the element previously at the specified position.
     * @throws    IndexOutOfBoundsException if index out of range
     *		  <tt>(index &lt; 0 || index &gt;= size())</tt>.
     */
    public void safeSet(int index, double element)
    {
        if(index == size)
        {
            add(element);
        }
        else
        {
            set(index, element);
        }
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param element element to be appended to this list.
     * @return <tt>true</tt>
     */
    public boolean add(double element) 
    {
        dirty = true;
	ensureCapacity(size + 1);
	elementData[size++] = element;
	return true;
    }

    /**
     * Inserts the specified element at the specified position in the
     * array. Shifts the element currently at that position (if any) and
     * any subsequent elements to the right (adds one to their indices).
     *
     * @param index Index at which the specified element is to be inserted.
     * @param element Element to be inserted.
     * @throws    IndexOutOfBoundsException if index is out of range
     *		  <tt>(index &lt; 0 || index &gt; size())</tt>.
     */
    public void add(int index, double element)
    {
        dirty = true;
	if (index > size || index < 0)
        {
	    throw new IndexOutOfBoundsException("Index: "+index+", Size: "+size);
        }
	ensureCapacity(size+1);
	System.arraycopy(elementData, index, elementData, index + 1, size - index);
	elementData[index] = element;
	setSize(size+1);
    }
    
    /**
     * Returns the capacity of the <tt>DoubleVariable</tt>.
     */
    public int getCapacity()
    {
        return elementData.length;
    }

    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their
     * indices).
     *
     * @param index the index of the element to removed.
     * @return the element that was removed from the list.
     * @throws    IndexOutOfBoundsException if index out of range <tt>(index
     * 		  &lt; 0 || index &gt;= size())</tt>.
     */
    public Object remove(int index)
    {
        dirty = true;
	rangeCheck(index);
	Object oldValue = new MutableDouble(elementData[index]);
	int numMoved = size - index - 1;
	if(numMoved > 0)
        {
	    System.arraycopy(elementData, index+1, elementData, index, numMoved);
        }
	setSize(size-1);
	return oldValue;
    }
    
    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their
     * indices).
     *
     * @param rows an array of the indexes of the elements to removed.
     * @return the element that was removed from the list.
     * @throws    IndexOutOfBoundsException if index out of range <tt>(index
     * 		  &lt; 0 || index &gt;= size())</tt>.
     */
    public void remove(int[] deletedRows)
    {
        dirty = true;
        int sizeToGCAt = 100000;
        int maxOverhead = 10000;
        int lastDeletedRowIndex = 0;
        for(int i=0; i<deletedRows.length; i++)
        {
            if(!(deletedRows[i] < size))
            {
                break;
            }
            lastDeletedRowIndex = i;
        }
        int newSize = size - lastDeletedRowIndex - 1;
        //System.out.println("lastDeletedRowIndex = " + lastDeletedRowIndex + "    newSize = " + newSize);
        if(newSize == 0)
        {
            elementData = new double[Variable.INITIAL_CAPACITY];
            if(Math.abs(size) > sizeToGCAt)
            {
                Runtime.getRuntime().gc();
            }
            size = 0;
            return;
        }
        int newCapacity = Math.max( Math.min((int)((double)newSize * 1.1), newSize + maxOverhead), Variable.INITIAL_CAPACITY);
        double[] newElementData;
        if(newCapacity < elementData.length)
        {
            newElementData = new double[newCapacity];
        }
        else
        {
            newElementData = elementData;
        }
        //copy/transfer array data here!
        int chunkStart = 0;
        int chunkEnd = 0;
        int chunkProgress = 0;
        int deletedRowProcessed = 0;
        int srcProgress = 0;
        do
        {
            srcProgress = chunkProgress + deletedRowProcessed;
            int delRow = deletedRows[deletedRowProcessed];
            if(delRow != srcProgress)//there is a bug here, joel 02/05/2007!!!
            {
                chunkStart = srcProgress;
                chunkEnd = delRow - 1;
            }
            else
            {
                while(deletedRowProcessed + 1 < deletedRows.length && delRow + 1 == deletedRows[deletedRowProcessed+1])
                {
                    ++deletedRowProcessed;
                    ++delRow;
                }
                chunkStart = ++delRow;
                if(deletedRowProcessed == deletedRows.length - 1)
                {
                    chunkEnd = size - 1;
                }
                else
                {
                    chunkEnd = deletedRows[deletedRowProcessed+1] - 1;
                }
            }
            int chunkSize = chunkEnd - chunkStart + 1;
            System.arraycopy(elementData, chunkStart, newElementData, chunkProgress, chunkSize);
            System.out.print("(chunkStart, chunkEnd, chunkProgress, chunkSize) = ");
            System.out.println("("+chunkStart+", "+chunkEnd+", "+chunkProgress+", "+chunkSize+")");
            chunkProgress += chunkSize;
        }while(chunkProgress < newSize);
        //end copy array data here.
        elementData = newElementData;
        if(Math.abs(size) > sizeToGCAt)
        {
            Runtime.getRuntime().gc();
        }
	size = newSize;
    }
    
  
    /**
     * Removes all of the elements from the array.  The array will
     * be empty after this call returns.
     */
    public void clear()
    {
        dirty = true;
	setSize(0);
    }

    /**
     * Removes from the array all of the elements whose index is between
     * fromIndex, inclusive and toIndex, exclusive.  Shifts any succeeding
     * elements to the left (reduces their index).
     * This call shortens the list by <tt>(toIndex - fromIndex)</tt> elements.
     * (If <tt>toIndex==fromIndex</tt>, this operation has no effect.)
     *
     * @param fromIndex index of first element to be removed.
     * @param toIndex index after last element to be removed.
     */
    protected void removeRange(int fromIndex, int toIndex)
    {
        dirty = true;
	int numMoved = size - toIndex;
        System.arraycopy(elementData, toIndex, elementData, fromIndex, numMoved);
	setSize(size-(toIndex-fromIndex));
    }

    /**
     * Check if the given index is in range.  If not, throw an appropriate
     * runtime exception.  This method does *not* check if the index is
     * negative: It is always used immediately prior to an array access,
     * which throws an ArrayIndexOutOfBoundsException if index is negative.
     *
     * @param index Index to check for validity.
     */
    private void rangeCheck(int index)
    {
	if(index >= size)
        {
	    throw new IndexOutOfBoundsException("Index: "+index+", Size: "+size);
        }
    }
    
    /**
     * Returns the element at the specified position in this list as an Object 
     * for convenient use with classes that accept Doubles but not doubles.
     * Initially created for to help implement the 
     * javax.swing.table.AbstractTableModel.
     *
     * @param  aValue Value to put into the <tt>DoubleVariable</tt>.
     * @param  row Index of the <tt>DoubleVariable</tt> to set.
     * @throws    IndexOutOfBoundsException if index is out of range <tt>(index 
     * &gt;= size())</tt>.
     * @throws    ArrayIndexOutOfBoundsException if index is out of range <tt>(index
     * 		  &lt; 0)</tt>.
     */
    public void setValueAt(Object aValue, int row)
    {
        dirty = true;
        if((aValue instanceof String))
        {
            try
            {
                double x = Double.parseDouble((String)aValue);
                if(Double.isNaN(x) || Double.isInfinite(x))
                {
                    dirty = false;
                    return;
                }
                safeSet(row, x);
            }
            catch(NumberFormatException e){}
        }
        else if(aValue instanceof Number)
        {
            double x = ((Number)aValue).doubleValue();
            if(Double.isNaN(x) || Double.isInfinite(x))
            {
                dirty = false;
                return;
            }
            safeSet(row, x);
        }
    }
    
    
    
}//end of DoubleVariable class