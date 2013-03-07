package com.analyticobjects.matchpoint.numerics;

/**
 *
 * @author Joel
 * @version 1.0, 12/08/2006
 */
public class CurveFittingEngine
{
    private int maxIterations;
    private double minDeltaObjectiveFunction;
    
    public CurveFittingEngine(double minDeltaObjectiveFuction, int maxIterations)
    {
        this.maxIterations = maxIterations;
        this.minDeltaObjectiveFunction = minDeltaObjectiveFuction;
    }

    public double[] fit(LMFunction f, double[] dependantVariable, double[][] independantVariables, double[] weights, double[] initialParameters)
    {
        int paramDimension = initialParameters.length;
        int numDataPoints = Math.min(dependantVariable.length, minSize(independantVariables));
        double[] fittedParameters = new double[initialParameters.length];
        System.arraycopy(initialParameters, 0, fittedParameters, 0, initialParameters.length);
        int iterations = 0;
        double currentObjectiveFunction = 0.0;
        double previousObjectiveFunction = 0.0;
        double deltaObjectiveFunction = 0.0;
        double[] currentParameters = initialParameters;
        do
        {
            currentObjectiveFunction = calculateObjectiveFunction(f, dependantVariable, independantVariables, weights, currentParameters, numDataPoints);
            deltaObjectiveFunction = currentObjectiveFunction - previousObjectiveFunction;
            
            previousObjectiveFunction = currentObjectiveFunction;
            iterations++;
        }while(!stoppingCondition(deltaObjectiveFunction, iterations));
        return fittedParameters; //return a new FitResults object with fittedParameters, lastObjectFunction, sumOfSquares, condition
    }
    
    private double sumOfSquares(LMFunction f, double[] dependantVariable, double[][] independantVariables, double[] weights, double[] parameters, int numDataPoints)
    {
        double sos = 0.0;
        double[] independantVariableValues = new double[independantVariables.length];
        for(int i = 0; i<numDataPoints; i++)
        {
            for(int j = 0; j<independantVariables.length; j++)
            {
                independantVariableValues[j] = independantVariables[j][i];
            }
            sos += weights[i] * square( dependantVariable[i] - f.evaluate(independantVariableValues, parameters) );
        }
        return sos;
    }
    
    private double calculateObjectiveFunction(LMFunction f, double[] dependantVariable, double[][] independantVariables, double[] weights, double[] parameters, int numDataPoints)
    {
        return sumOfSquares(f, dependantVariable, independantVariables, weights, parameters, numDataPoints);
    }
    
    private boolean stoppingCondition(double deltaObjectiveFuntion, int iterations)
    {
        return (Math.abs(deltaObjectiveFuntion) < minDeltaObjectiveFunction || iterations > maxIterations) && iterations>2;
    }

    private int minSize(double[][] vars)
    {
        int minLength = 0;
        for(int i = 0; i<vars.length; i++)
        {
            int sizei = vars[i].length;
            if(sizei<minLength){minLength=sizei;}
        }
        return minLength;
    }
    
    private double square(double x){return x*x;}
    
}
