package com.analyticobjects.matchpoint;

public class TestCode
{
    private static int num = 10000000;
    public static void main(String[] args)
    {
        boolean s = true;
        long[] m1 = new long[100];
        long[] m2 = new long[100];
        for(int i = 0; i<100; i++)
        {
            if(s)
            {
                m1[i] = method1();
                m2[i] = method2();
            }
            else
            {
                m2[i] = method2();
                m1[i] = method1();
            }
            s=!s;
        }
        System.out.println("Method 1 average time in nanoseconds = " + avgTime(m1));
        System.out.println(x2);
        System.out.println("Method 2 average time in nanoseconds = " + avgTime(m2));
        System.out.println(y2);
        System.out.println("Difference = " + (x2 - y2));
    }
    
    private static double x = 0.0;
    private static double x2 = 0.0;
    private static double y2 = 0.0;
    private static long method1()
    {
        x=0.0;
        long startTime = System.nanoTime();
        for(int i = 0; i<num; i++)
        {
            double prex = (double)i/(double)(i+1);
            x+=prex;
        }
        x2 = x;
        return System.nanoTime() - startTime;
    }
    
    private static long method2()
    {
        long startTime = System.nanoTime();
        double y = 0.0;
        for(int i = 0; i<num; i++)
        {
            y+=(double)i/(double)(i+1);
        }
        y2 = y;
        return System.nanoTime() - startTime;
    }
        
    private static double avgTime(long[] m)
    {
        double sum = 0;
        for(int i = 0; i<m.length; i++){sum+=m[i];}
        return sum / (double)m.length;
    }
    
}
