package com.analyticobjects.matchpoint.scripting;

import java.util.regex.*;
import java.util.*;
/**
 *
 * @author Joel
 * @version 1.0, 12/05/2006
 */
public class RhinoFilter
{
    private Vector<Constant> constants = new Vector<Constant>(100);
    private Vector<Permanent> permanents = new Vector<Permanent>(100);
    private Pattern reflectPackagePattern = Pattern.compile(".*(java\\.lang\\.reflect).*");
    private Pattern getClassPattern = Pattern.compile(".*\\.getClass\\(.*");
    private Pattern classObjectPattern = Pattern.compile(".*\\.class.*");
    private Pattern matchpointPattern = Pattern.compile(".*com\\.analyticobjects\\.matchpoint.*");
    private final String nothing = "";
    private final String reflectionError = "\"Error: Use of Java reflection is not allowed.\"";
    private final String privateClassError = "\"Error: This class/package is not available to the scripting engine.\"";
    private Pattern setConstantPattern = Pattern.compile("setConstant *\\( *([a-zA-Z_][a-zA-Z_0-9]*) *\\) *;?");
    private Pattern setPermanentPattern = Pattern.compile("setPermanent *\\( *([a-zA-Z_][a-zA-Z_0-9]*) *\\) *;?");
     
    public String translate(String scriptString)
    {
        scriptString = processPermanents(scriptString);
        scriptString = processConstants(scriptString);
        Matcher reflectPackageMatcher = reflectPackagePattern.matcher(scriptString);
        Matcher getClassMatcher = getClassPattern.matcher(scriptString);
        Matcher classObjectMatcher = classObjectPattern.matcher(scriptString);
        Matcher matchpointMatcher = matchpointPattern.matcher(scriptString);
        if(reflectPackageMatcher.matches()){return reflectionError;}
        if(getClassMatcher.matches()){return reflectionError;}
        if(classObjectMatcher.matches()){return reflectionError;}
        if(matchpointMatcher.matches()){return privateClassError;}
        return scriptString;
    }
    
    private String processConstants(String arg)
    {
        Matcher setConstantMatcher = setConstantPattern.matcher(arg);
        boolean foundAny = false;
        while(setConstantMatcher.find())
        {
            foundAny = true;
            constants.add(new Constant(setConstantMatcher.group(1),true,setConstantMatcher.start()));
        }
        for(Constant c : constants)
        {
            Matcher cm = c.resetPattern.matcher(arg);
            if(!cm.find())
            {
                c.justCreated = false;
                continue;
            }
            if(c.justCreated && cm.end() > c.location)
            {
                c.justCreated = false;
                return "\"Error:  " + c.name + " was declared constant and cannot be reassigned.\"";
            }
            c.justCreated = false;
            return "\"Error:  " + c.name + " was declared constant and cannot be reassigned.\"";
        }
        if(foundAny)
        {
            arg = arg.replaceAll(setConstantPattern.pattern(),"true;");
        }
        return arg;
    }
    
    private class Constant
    {
        final Pattern resetPattern;
        final String name;
        boolean justCreated;
        final int location;
        Constant(String name, boolean justCreated, int location)
        {
            this.name = name;
            this.justCreated = justCreated;
            this.location = location;
            resetPattern = Pattern.compile("(^| |;)" + name + " *=[^=]");
        }
    }
    
     private String processPermanents(String arg)
    {
        Matcher setPermanentMatcher = setPermanentPattern.matcher(arg);
        boolean foundAny = false;
        while(setPermanentMatcher.find())
        {
            foundAny = true;
            permanents.add(new Permanent(setPermanentMatcher.group(1),true,setPermanentMatcher.start()));
        }
        for(Permanent p : permanents)
        {
            Matcher pm = p.deletePattern.matcher(arg);
            if(!pm.find())
            {
                p.justCreated = false;
                continue;
            }
            if(p.justCreated && pm.end() > p.location)
            {
                p.justCreated = false;
                return "\"Error:  " + p.name + " was declared permanent and cannot be deleted.\"";
            }
            p.justCreated = false;
            return "\"Error:  " + p.name + " was declared permanent and cannot be deleted.\"";
        }
        if(foundAny)
        {
            arg = arg.replaceAll(setPermanentPattern.pattern(),"true;");
        }
        return arg;
    }
    
    private class Permanent
    {
        final Pattern deletePattern;
        final String name;
        boolean justCreated;
        final int location;
        Permanent(String name, boolean justCreated, int location)
        {
            this.name = name;
            this.justCreated = justCreated;
            this.location = location;
            deletePattern = Pattern.compile("(^| |;)delete +" + name + "($|;| )");
        }
    }
        
}
