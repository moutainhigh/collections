package com.gwssi.expression.component.function.logic;

import java.math.BigDecimal;

import com.gwssi.expression.core.function.AbstractValueFunction;
import com.gwssi.expression.core.lang.UnsupportedArgumentException;
import com.gwssi.rodimus.util.StringUtil;

public class Comparison extends AbstractValueFunction {

    protected int type;

    public static final int LT = 0;
    public static final int GT = 1;
    public static final int LE = 2;
    public static final int GE = 3;
    public static final int NE = 4;
    public static final int EQ = 5;
    
    private String symbol = "";

    public Comparison(int type) {
        this.type = type;
        switch (type) {
        case 0:
            symbol = "<";
            break;
        case 1:
            symbol = ">";
            break;
        case 2:
            symbol = "<=";
            break;
        case 3:
            symbol = ">=";
            break;
        case 4:
            symbol = "!=";
            break;
        case 5:
            symbol = "==";
            break;

        default:
            break;
        }
    }

    public Object run(Object... args) {

        if (args==null || args.length != 2) {
            throw new UnsupportedArgumentException(symbol, args);
        }
        
        Object leftArg = args[0];
        Object rightArg = args[1];
        
        Object res = null;
        
        switch (type) {
        case LT: {
            res = lt(leftArg, rightArg);
            break;
        }
        case GT: {
            res = gt(leftArg, rightArg);
            break;
        }
        case LE: {
            res = le(leftArg, rightArg);
            break;
        }
        case GE: {
            res = ge(leftArg, rightArg);
            break;
        }
        case NE: {
            res = ne(leftArg, rightArg);
            break;
        }
        case EQ: {
            res = eq(leftArg, rightArg);
            break;
        }
        }
        
        return res;
    }
    
    public Object lt(Object a1, Object a2) {
    	if(a1==null && a2==null){
    		return false ;
    	}
    	if(a1==null){
    		return true;
    	}
    	
        if ((a1 instanceof Number) && (a2 instanceof Number)) {
            
            if ((a1 instanceof BigDecimal) && (a2 instanceof BigDecimal)) {
                BigDecimal p1 = (BigDecimal)a1;
                BigDecimal p2 = (BigDecimal)a2;
                
                if(p1.compareTo(p2)<0) {
                    return true;
                } else {
                    return false;
                }
                
            }
                
            double x = ((Number) a1).doubleValue();
            double y = ((Number) a2).doubleValue();
            return (x < y);
        }else{
        	// 当成double，做最后拯救
        	try{
        		double x = Double.parseDouble(a1.toString());
        		double y = Double.parseDouble((String)a2);
        		return (x < y);                    	
        	}catch(Throwable t){
        		//throw new UnsupportedArgumentException(symbol, a1,a2);
        	}
        }
        String p1 = StringUtil.safe2String(a1);
        String p2 = StringUtil.safe2String(a2);
        
        if(p1.compareTo(p2) < 0){
        	return true;
        }else{
        	return false;
        }
    }

    public Object gt(Object a1, Object a2) {
    	if(a1==null ){
    		return false ;
    	}
    	
        if ((a1 instanceof Number) && (a2 instanceof Number)) {
            if ((a1 instanceof BigDecimal) && (a2 instanceof BigDecimal)) {
                BigDecimal p1 = (BigDecimal)a1;
                BigDecimal p2 = (BigDecimal)a2;
                
                if(p1.compareTo(p2)>0) {
                    return true;
                } else {
                    return false;
                }
                
            }
            
            double x = ((Number) a1).doubleValue();
            double y = ((Number) a2).doubleValue();
            return (x > y);
        }else{
        	// 当成double，做最后拯救
        	try{
        		double x = Double.parseDouble(a1.toString());
        		double y = Double.parseDouble((String)a2);
        		return (x > y);                    	
        	}catch(Throwable t){
        		// throw new UnsupportedArgumentException(symbol, a1,a2);
        	}
        }
        
        String p1 = StringUtil.safe2String(a1);
        String p2 = StringUtil.safe2String(a2);
        
        if(p1.compareTo(p2) > 0){
        	return true;
        }else{
        	return false;
        }
        
    }

    public Object le(Object a1, Object a2) {
    	if(a1==null && a2==null){
    		return true ;
    	}
    	if(a1==null){
    		return true;
    	}
    	
        if ((a1 instanceof Number) && (a2 instanceof Number)) {
            
            if ((a1 instanceof BigDecimal) && (a2 instanceof BigDecimal)) {
                BigDecimal p1 = (BigDecimal)a1;
                BigDecimal p2 = (BigDecimal)a2;
                
                if(p1.compareTo(p2)<=0) {
                    return true;
                } else {
                    return false;
                }
                
            }
            
            double x = ((Number) a1).doubleValue();
            double y = ((Number) a2).doubleValue();
            return (x <= y);
        }
        else{
        	// 当成double，做最后拯救
        	try{
        		double x = Double.parseDouble(a1.toString());
        		double y = Double.parseDouble((String)a2);
        		return (x <= y);                    	
        	}catch(Throwable t){
        		//throw new UnsupportedArgumentException(symbol, a1,a2);
        	}
        }
        
        String p1 = StringUtil.safe2String(a1);
        String p2 = StringUtil.safe2String(a2);
        
        if(p1.compareTo(p2)<= 0){
        	return true;
        }else{
        	return false;
        }
    }

    public Object ge(Object a1, Object a2) {
    	
    	if(a1==null && a2==null){
    		return true ;
    	}
    	if(a1==null){
    		return false;
    	}
    	
        if ((a1 instanceof Number) && (a2 instanceof Number)) {
            
            if ((a1 instanceof BigDecimal) && (a2 instanceof BigDecimal)) {
                BigDecimal p1 = (BigDecimal)a1;
                BigDecimal p2 = (BigDecimal)a2;
                
                if(p1.compareTo(p2)>=0) {
                    return true;
                } else {
                    return false;
                }
                
            }
            
            double x = ((Number) a1).doubleValue();
            double y = ((Number) a2).doubleValue();
            return (x >= y);
        }else{
        	// 当成double，做最后拯救
        	try{
        		double x = Double.parseDouble(a1.toString());
        		double y = Double.parseDouble((String)a2);
        		return (x >= y);                    	
        	}catch(Throwable t){
        		//throw new UnsupportedArgumentException(symbol, a1,a2);
        	}
        }
        
        String p1 = StringUtil.safe2String(a1);
        String p2 = StringUtil.safe2String(a2);
        
        if(p1.compareTo(p2)>=0){
        	return true;
        }else{
        	return false;
        }
    }

    public Object eq(Object a1, Object a2) {

        if (a1 == null) {
            if (a2 == null) {
                return true;
            } else {                
                return false;
            }
            
        } else if (a2 == null) {
            return false;
            
        } else if ((a1 instanceof Number) && (a2 instanceof Number)) {
            
            if ((a1 instanceof BigDecimal) && (a2 instanceof BigDecimal)) {
                BigDecimal p1 = (BigDecimal)a1;
                BigDecimal p2 = (BigDecimal)a2;
                
                return p1.equals(p2);
            }
            
            double x = ((Number) a1).doubleValue();
            double y = ((Number) a2).doubleValue();
            return (x == y);
            
        } else if ((a1 instanceof Boolean) && (a2 instanceof Boolean)) {
            Boolean x = ((Boolean) a1).booleanValue();
            Boolean y = ((Boolean) a2).booleanValue();
            return (x == y);
            
        }  else if ((a1 instanceof String) && (a2 instanceof String)) {
            String x = (String) a1;
            String y = (String) a2;
            return x.equals(y);
        } else if(a1!=null && a2!=null){
        	return a1.toString().equalsIgnoreCase(a2.toString());
        }

        String p1 = StringUtil.safe2String(a1);
        String p2 = StringUtil.safe2String(a2);
        
        if(p1.equals(p2)){
        	return true;
        }else{
        	return false;
        }
        
        //throw new UnsupportedArgumentException(symbol, a1, a2);
    }

    public Object ne(Object a1, Object a2) {

        if (a1 == null) {
            if (a2 == null) {
                return false;
            } else {                
                return true;
            }
            
        } else if (a2 == null) {
            return true;
            
        } else if ((a1 instanceof Number) && (a2 instanceof Number)) {
            
            if ((a1 instanceof BigDecimal) && (a2 instanceof BigDecimal)) {
                BigDecimal p1 = (BigDecimal)a1;
                BigDecimal p2 = (BigDecimal)a2;
                
                
                return !(p1.equals(p2));
            }
            
            double x = ((Number) a1).doubleValue();
            double y = ((Number) a2).doubleValue();
            return (x != y);
            
        } else if ((a1 instanceof Boolean) && (a2 instanceof Boolean)) {
            Boolean x = ((Boolean) a1).booleanValue();
            Boolean y = ((Boolean) a2).booleanValue();
            return (x != y);
            
        }  else if ((a1 instanceof String) && (a2 instanceof String)) {
            String x = (String) a1;
            String y = (String) a2;
            return !(x.equals(y));
        }
        
        String p1 = StringUtil.safe2String(a1);
        String p2 = StringUtil.safe2String(a2);
        
        if(p1.equals(p2)){
        	return false;
        }else{
        	return true;
        }
        
        //throw new UnsupportedArgumentException(symbol, a1, a2);
    }

}