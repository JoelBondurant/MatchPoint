package com.analyticobjects.matchpoint.numerics;


public class MutableDouble extends Number
{

    private double value;  //The value of the MutableDouble.

    /**
     * Returns the value stored in the <code>MutableDouble</code> object
     * that represents the primitive <code>double</code> stored.
     */
    public double getValue()
    {
        return value;
    }

    /**
     * Sets the value stored in the <code>MutableDouble</code> object
     * that represents the primitive <code>double</code> stored.
     */
    public void setValue(double value)
    {
        this.value = value;
    }

    /**
     * Constructs a newly allocated <code>MutableDouble</code> object that
     * represents the primitive <code>double</code> argument.
     *
     * @param   value   the value to be represented by the 
     * <code>MutableDouble</code>.
     */
    public MutableDouble(double value)
    {
        this.value = value;
    }

    /**
     * Constructs a newly allocated <code>MutableDouble</code> object that
     * represents the primitive <code>double</code> zero.
     */
    public MutableDouble()
    {
        value = 0.0;
    }

    /**
     * Returns a string representation of this <code>MutableDouble</code>
     * object.  The primitive <code>double</code> value represented by this
     * object is converted to a string exactly as if by the method
     * <code>toString</code> of one argument.
     *
     * @return  A <code>String</code> representation of this object.
     */
    public String toString() 
    {
        return String.valueOf(getValue());
    }

    /**
     * Returns the value of this <code>MutableDouble</code> as a 
     * <code>byte</code> (by casting to a <code>byte</code>).
     *
     * @return  the <code>double</code> value represented by this object
     *          converted to type <code>byte</code>
     */
    public byte byteValue() 
    {
        return (byte)getValue();
    }

    /**
     * Returns the value of this <code>MutableDouble</code> as a
     * <code>short</code> (by casting to a <code>short</code>).
     *
     * @return  the <code>double</code> value represented by this object
     *          converted to type <code>short</code>
     */
    public short shortValue() 
    {
        return (short)getValue();
    }

    /**
     * Returns the value of this <code>MutableDouble</code> as an
     * <code>int</code> (by casting to type <code>int</code>).
     *
     * @return  the <code>double</code> value represented by this object
     *          converted to type <code>int</code>
     */
    public int intValue() 
    {
        return (int)getValue();
    }

    /**
     * Returns the value of this <code>MutableDouble</code> as a
     * <code>long</code> (by casting to type <code>long</code>).
     *
     * @return  the <code>double</code> value represented by this object
     *          converted to type <code>long</code>
     */
    public long longValue() 
    {
        return (long)getValue();
    }

    /**
     * Returns the <code>float</code> value of this
     * <code>MutableDouble</code> object.
     *
     * @return  the <code>double</code> value represented by this object
     *          converted to type <code>float</code>
     */
    public float floatValue() 
    {
        return (float)getValue();
    }

    /**
     * Returns the <code>double</code> value of this
     * <code>MutableDouble</code> object.
     *
     * @return the <code>double</code> value represented by this object
     */
    public double doubleValue() 
    {
        return (double)getValue();
    }

    /**
     * Returns a hash code for this <code>MutableDouble</code> object.
     *
     * @return  a <code>hash code</code> value for this object.
     */
    public int hashCode() 
    {
        long bits = Double.doubleToLongBits(value);
        return (int)(bits ^ (bits >>> 32));
    }

    /**
     * Compares this object against the specified object.  The result
     * is <code>true</code> if and only if the argument is not
     * <code>null</code> and is a <code>MutableDouble</code> object that
     * represents a <code>double</code> that has the same value as the
     * <code>double</code> represented by this object.
     */
    public boolean equals(Object obj) 
    {
        return (obj instanceof MutableDouble)
               && (Double.doubleToLongBits(((MutableDouble)obj).value) == Double.doubleToLongBits(value));
    }


    /**
     * Compares two <code>MutableDouble</code> objects numerically.
     */
    public int compareTo(MutableDouble anotherMutableDouble) 
    {
        return Double.compare(this.getValue(), anotherMutableDouble.getValue());
    }

}//end of MutableDouble inner class
