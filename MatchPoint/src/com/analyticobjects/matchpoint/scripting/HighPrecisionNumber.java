package com.analyticobjects.matchpoint.scripting;

import java.math.*;
/**
 *
 * @author Joel
 * @version 1.0, 12/16/2006
 */
public final class HighPrecisionNumber extends BigDecimal
{
    
    public static final String PI = "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825342117067982148086513282306647093844609550582231725359408128481117450284102701938521105559644622948954930381964428810975665933446128475648233786783165271201909145648566923460348610454326648213393607260249141273724587006606315588174881520920962829254091715364367892590360011330530548820466521384146951941511609433057270365759591953092186117381932611793105118548074462379962749673518857527248912279381830119491";
    public static final String E = "2.7182818284590452353602874713526624977572470936999595749669676277240766303535475945713821785251664274274663919320030599218174135966290435729003342952605956307381323286279434907632338298807531952510190115738341879307021540891499348841675092447614606680822648001684774118537423454424371075390777449920695517027618386062613313845830007520449338265602976067371132007093287091274437470472306969772093101416928368190255151086574637721112523897844250569536967707854499699679468644549059879316368892300987931";
    
    public static HighPrecisionNumber createNew(String arg)
    {
        if(arg.equals("PI")){return new HighPrecisionNumber(PI);}
        if(arg.equals("E")){return new HighPrecisionNumber(E);}
        return new HighPrecisionNumber(arg);
    }
    
    /** Creates a new instance of HighPrecisionNumber */
    public HighPrecisionNumber(String arg)
    {
        super(arg);
    }
    
    public BigDecimal divide(BigDecimal b)
    {
        return this.divide(b,256,BigDecimal.ROUND_HALF_EVEN);
    }
    
    public BigDecimal add(double b)
    {
        BigDecimal b2 = new BigDecimal(Double.toString(b));
        return this.add(b2);
    }
    
    public BigDecimal subtract(double b)
    {
        BigDecimal b2 = new BigDecimal(Double.toString(b));
        return this.subtract(b2);
    }
        
    public BigDecimal multiply(double b)
    {
        BigDecimal b2 = new BigDecimal(Double.toString(b));
        return this.multiply(b2);
    }
        
    public BigDecimal divide(double b)
    {
        HighPrecisionNumber b2 = HighPrecisionNumber.createNew(Double.toString(b));
        return this.divide(b2,256,BigDecimal.ROUND_HALF_EVEN);
    }
    
    public BigDecimal max(double b)
    {
        BigDecimal b2 = new BigDecimal(Double.toString(b));
        return this.max(b2);
    }
    
    public BigDecimal min(double b)
    {
        BigDecimal b2 = new BigDecimal(Double.toString(b));
        return this.min(b2);
    }
        
    public BigDecimal remainder(double b)
    {
        BigDecimal b2 = new BigDecimal(Double.toString(b));
        return this.remainder(b2);
    }
    
}
