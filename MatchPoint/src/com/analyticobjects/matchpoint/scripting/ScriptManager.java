package com.analyticobjects.matchpoint.scripting;

import java.util.*;
import java.text.*;
import javax.script.*;
import javax.swing.*;
import com.analyticobjects.matchpoint.*;
import com.analyticobjects.matchpoint.gui.*;
import com.analyticobjects.matchpoint.chart.*;
import com.analyticobjects.matchpoint.numerics.*;

/**
 *
 * @author Joel
 * @version 1.0, 12/05/2006
 */
public class ScriptManager
{
    private ScriptEngineManager scriptEngineMgr;
    private ScriptEngine rhinoEngine;
    private static ScriptManager uniqueInstance;
    private StatusBar statusBar;
    private RhinoFilter rhinoFilter;
    private static final String waitFunction = "function wait(x){Thread.sleep(1000*x);}";
    private static final String setConstantFunction = "function setConstant(x){return false;}";
    private static final String setPermanentFunction = "function setPermanent(x){return false;}";
    private static final String hpFactoryFunction = "function HPNumber(a){return com.analyticobjects.matchpoint.scripting.HighPrecisionNumber.createNew(a);}";
    private static final String absFunction = "function abs(x){return java.lang.Math.abs(x);}";
    private static final String acosFunction = "function acos(x){return java.lang.Math.acos(x);}";
    private static final String asinFunction = "function asin(x){return java.lang.Math.asin(x);}";
    private static final String atanFunction = "function atan(x){return java.lang.Math.atan(x);}";
    private static final String ceilFunction = "function ceil(x){return java.lang.Math.ceil(x);}";
    private static final String cosFunction = "function cos(x){return java.lang.Math.cos(x);}";
    private static final String expFunction = "function exp(x){return java.lang.Math.exp(x);}";
    private static final String floorFunction = "function floor(x){return java.lang.Math.floor(x);}";
    private static final String lnFunction = "function ln(x){return java.lang.Math.log(x);}";
    private static final String maxFunction = "function max(x,y){return java.lang.Math.max(x,y);}";
    private static final String minFunction = "function min(x,y){return java.lang.Math.min(x,y);}";
    private static final String powFunction = "function pow(x,y){return java.lang.Math.pow(x,y);}";
    private static final String randomFunction = "function random(a,b){return a+(b-a)*java.lang.Math.random();}";
    private static final String roundFunction = "function round(x){return java.lang.Math.rint(x);}";
    private static final String sinFunction = "function sin(x){return java.lang.Math.sin(x);}";
    private static final String sqrtFunction = "function sqrt(x){return java.lang.Math.sqrt(x);}";
    private static final String tanFunction = "function tan(x){return java.lang.Math.tan(x);}";
    private static final String toDegreesFunction = "function toDegrees(x){return java.lang.Math.toDegrees(x);}";
    private static final String toRadiansFunction = "function toRadians(x){return java.lang.Math.toRadians(x);}";
    private static final String cbrtFunction = "function cbrt(x){return java.lang.Math.cbrt(x);}";
    private static final String coshFunction = "function cosh(x){return java.lang.Math.cosh(x);}";
    private static final String expm1Function = "function expm1(x){return java.lang.Math.expm1(x);}";
    private static final String getExponentFunction = "function getExponent(x){return java.lang.Math.getExponent(x);}";
    private static final String hypotFunction = "function hypot(x,y){return java.lang.Math.hypot(x,y);}";
    private static final String IEEERemainderFunction = "function IEEERemainder(x,y){return java.lang.Math.IEEEremainder(x,y);}";
    private static final String logFunction = "function log(x){return java.lang.Math.log10(x);}";
    private static final String ln1pFunction = "function ln1p(x){return java.lang.Math.log1p(x);}";
    private static final String signumFunction = "function signum(x){return java.lang.Math.signum(x);}";
    private static final String sinhFunction = "function sinh(x){return java.lang.Math.sinh(x);}";
    private static final String tanhFunction = "function tanh(x){return java.lang.Math.tanh(x);}";
    private static final String logbaseFunction = "function logbase(b,x){return java.lang.Math.log(x)/java.lang.Math.log(b);}";
    private static final String log21pFunction = "function log1p(b,x){return java.lang.Math.log1p(x)/java.lang.Math.log1p(b);}";
    private static final String PrimativeArrayFunction = "function PrimativeArray(type,size)" +
            "{" +
            "   if(type == \"boolean\"){return java.lang.reflect.Array.newInstance(java.lang.Boolean.TYPE, size);} " +
            "   if(type == \"char\"){return java.lang.reflect.Array.newInstance(java.lang.Character.TYPE, size);} " +
            "   if(type == \"byte\"){return java.lang.reflect.Array.newInstance(java.lang.Byte.TYPE, size);} " +
            "   if(type == \"short\"){return java.lang.reflect.Array.newInstance(java.lang.Short.TYPE, size);} " +
            "   if(type == \"int\"){return java.lang.reflect.Array.newInstance(java.lang.Integer.TYPE, size);} " +
            "   if(type == \"long\"){return java.lang.reflect.Array.newInstance(java.lang.Long.TYPE, size);} " +
            "   if(type == \"float\"){return java.lang.reflect.Array.newInstance(java.lang.Float.TYPE, size);} " +
            "   if(type == \"double\"){return java.lang.reflect.Array.newInstance(java.lang.Double.TYPE, size);} " +
            "   if(type == \"String\"){return java.lang.reflect.Array.newInstance(java.lang.String, size);} " +
            "   return null;" +
            "}";
    
    /** Creates a new instance of ScriptEngine */
    private ScriptManager()
    {
        statusBar = Application.getMainFrame().getStatusBar();
        scriptEngineMgr = new ScriptEngineManager();
        rhinoEngine = scriptEngineMgr.getEngineByName("rhino");
        rhinoFilter = new RhinoFilter();
        rhinoEngine.put("table",ScriptableDataTable.getInstance());
        execute("setConstant(table);setPermanent(table);");
        rhinoEngine.put("runtime",Runtime.getRuntime());
        execute("setConstant(runtime);setPermanent(runtime);");
        rhinoEngine.put("chart",Chart.getInstance());
        executeUnfiltered("importPackage(java.lang);");//you may not want to do this!
        executeUnfiltered("importPackage(java.util);");
        executeUnfiltered("importPackage(java.awt);");
        executeUnfiltered("importPackage(java.io);");
        //execute("importPackage(java.math)");
        executeUnfiltered("importPackage(java.awt.event);");
        executeUnfiltered("importPackage(javax.swing);");
        executeUnfiltered("importPackage(javax.swing.event);");
        executeUnfiltered("importClass(com.analyticobjects.matchpoint.scripting.UnitConversions);");
        executeUnfiltered("PI = Math.PI;");execute("setConstant(PI);setPermanent(PI);");
        executeUnfiltered("E = Math.E;");execute("setConstant(E);setPermanent(E);");
        executeUnfiltered(waitFunction);execute("setConstant(wait);setPermanent(wait);");
        executeUnfiltered(PrimativeArrayFunction);execute("setConstant(PrimativeArray);setPermanent(PrimativeArray);");
        executeUnfiltered(setConstantFunction);execute("setConstant(setConstant);setPermanent(setConstant);");
        executeUnfiltered(setPermanentFunction);execute("setConstant(setPermanent);setPermanent(setPermanent);");
        executeUnfiltered(hpFactoryFunction);execute("setConstant(hpFactory);setPermanent(hpFactory);");
        executeUnfiltered(absFunction);execute("setConstant(abs);setPermanent(abs);");
        executeUnfiltered(acosFunction);execute("setConstant(acos);setPermanent(acos);");
        executeUnfiltered(asinFunction);execute("setConstant(asin);setPermanent(asin);");
        executeUnfiltered(atanFunction);execute("setConstant(atan);setPermanent(atan);");
        executeUnfiltered(ceilFunction);execute("setConstant(ceil);setPermanent(ceil);");
        executeUnfiltered(cosFunction);execute("setConstant(cos);setPermanent(cos);");
        executeUnfiltered(expFunction);execute("setConstant(exp);setPermanent(exp);");
        executeUnfiltered(floorFunction);execute("setConstant(floor);setPermanent(floor);");
        executeUnfiltered(lnFunction);execute("setConstant(ln);setPermanent(ln);");
        executeUnfiltered(maxFunction);execute("setConstant(max);setPermanent(max);");
        executeUnfiltered(minFunction);execute("setConstant(min);setPermanent(min);");
        executeUnfiltered(powFunction);execute("setConstant(pow);setPermanent(pow);");
        executeUnfiltered(roundFunction);execute("setConstant(round);setPermanent(round);");
        executeUnfiltered(randomFunction);execute("setConstant(random);setPermanent(random);");
        executeUnfiltered(sinFunction);execute("setConstant(sin);setPermanent(sin);");
        executeUnfiltered(sqrtFunction);execute("setConstant(sqrt);setPermanent(sqrt);");
        executeUnfiltered(tanFunction);execute("setConstant(tan);setPermanent(tan);");
        executeUnfiltered(toDegreesFunction);execute("setConstant(toDegrees);setPermanent(toDegrees);");
        executeUnfiltered(toRadiansFunction);execute("setConstant(toRadians);setPermanent(toRadians);");
        executeUnfiltered(cbrtFunction);execute("setConstant(cbrt);setPermanent(cbrt);");
        executeUnfiltered(coshFunction);execute("setConstant(cosh);setPermanent(cosh);");
        executeUnfiltered(expm1Function);execute("setConstant(expm1);setPermanent(expm1);");
        executeUnfiltered(getExponentFunction);execute("setConstant(getExponent);setPermanent(getExponent);");
        executeUnfiltered(hypotFunction);execute("setConstant(hypot);setPermanent(hypot);");
        executeUnfiltered(IEEERemainderFunction);execute("setConstant(IEEERemainder);setPermanent(IEEERemainder);");
        executeUnfiltered(logFunction);execute("setConstant(log);setPermanent(log);");
        executeUnfiltered(ln1pFunction);execute("setConstant(ln1p);setPermanent(ln1p);");
        executeUnfiltered(signumFunction);execute("setConstant(signum);setPermanent(signum);");
        executeUnfiltered(sinhFunction);execute("setConstant(sinh);setPermanent(sinh);");
        executeUnfiltered(tanhFunction);execute("setConstant(tanh);setPermanent(tanh);");
        executeUnfiltered(logbaseFunction);execute("setConstant(logbase);setPermanent(logbase);");
        executeUnfiltered(log21pFunction);execute("setConstant(log21p);setPermanent(log21p);");
    }
    
    public static ScriptManager getInstance()
    {
        if(uniqueInstance == null)
        {
            uniqueInstance = new ScriptManager();
        }
        return uniqueInstance;
    }
    
    private Object executeUnfiltered(String scriptString)
    {
        try 
        {
            return rhinoEngine.eval(scriptString);
        } 
        catch(ScriptException se)
        {
            String msg = se.getMessage().substring(16).replaceAll("\\(<Unknown source>#1\\) in <Unknown source> at line number 1","");
            msg = msg.replaceAll("javascript.internal.","");
            msg = msg.replaceAll("EcmaError:","");
            return msg;
        }
        catch(OutOfMemoryError oome)
        {
            return "Out of memory error: " + oome.getMessage();
        }
        catch(Throwable t)
        {
            return "Unspecified error: " + t.getMessage();
        }
    }
    
    public Object execute(String scriptString)
    {
        String translatedScriptString = rhinoFilter.translate(scriptString);
        Object returnVal = executeUnfiltered(translatedScriptString);
        return returnVal;
    }

    
}
