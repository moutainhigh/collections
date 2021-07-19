package com.gwssi.application.log.aspect;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Calendar;

import org.aspectj.lang.JoinPoint;

import com.gwssi.application.log.annotation.LogBOAnnotation;
import com.gwssi.optimus.core.web.event.OptimusResponse;

import cn.gov.customs.casp.sdk.gslog.GSLogger;
import cn.gov.customs.casp.sdk.gslog.GSLoggerFactory;

/**
 * @author chehaoran
 * 日志通知类（切面是通知和切点的结合，切面的工作称为通知）
 * 
 *
 */
public class LogAspect {
	
//	Logger logger = Logger.getLogger("logSocket");
	GSLogger logger = GSLoggerFactory.getLogger(this.getClass());
	private String message;
	
	/**
	 * 当连接点所在的方法执行前，执行此方法
	 * 
	 * @param joinPoint
	 * @throws Throwable
	 */
	public void doBefore(JoinPoint joinPoint) throws Throwable{
		LogBOAnnotation bo = getLogBO(joinPoint);
		if(bo!=null){
			Calendar operationTime = Calendar.getInstance();	
			Timestamp t = new Timestamp(operationTime.getTimeInMillis());
//    		String systemCode = bo.systemCode();	
//    		String functionCode = bo.functionCode();	
//    		String operationType = bo.operationType();	
//    		String operationState ;
			System.out.println(t + bo.functionCode());
		}
    		System.out.println("doBefore!");
          
	}
	
	/**
	 * 当连接点所在的方法执行后，执行此方法
	 * 
	 */
	public void doAfter(){
		System.out.println("doAfter!");
	}
	
	/**
	 * 当连接点所在的方法成功返回后，执行此方法
	 * 
	 * @param joinPoint
	 * @throws Throwable
	 */
	public void doAfterReturn(JoinPoint joinPoint) throws Throwable{
		LogBOAnnotation bo = getLogBO(joinPoint);
		Object[] parms = getParms(joinPoint);
		for(Object parm : parms){
			if (parm instanceof OptimusResponse) {
				OptimusResponse resp = (OptimusResponse) parm;
				
			}
		}
		if(bo!=null){
			Calendar operationTime = Calendar.getInstance();	
			Timestamp t = new Timestamp(operationTime.getTimeInMillis());
			String systemCode = bo.systemCode();	
			String functionCode = bo.functionCode();	
			String operationType = bo.operationType();	
			String operationState = "1";//1表示成功
			message = t + "|" + systemCode +"|"+ functionCode +"|"+ operationType +"|"+ operationState;
			System.out.println(message);
			logger.info(message);
		}
		System.out.println("doAfterReturn!");
	}
	
	/**
	 * 当连接点所在的方法抛出异常后，执行此方法
	 * 
	 * @param joinPoint 连接点
	 * @throws Throwable
	 */
	public void doAfterThrow(JoinPoint joinPoint) throws Throwable{
		LogBOAnnotation bo = getLogBO(joinPoint);
		Object[] parms = getParms(joinPoint);
		if(bo!=null){
			Calendar operationTime = Calendar.getInstance();	
			Timestamp t = new Timestamp(operationTime.getTimeInMillis());
			String systemCode = bo.systemCode();	
			String functionCode = bo.functionCode();	
			String operationType = bo.operationType();	
			String operationState = "0";//0表示有异常
			message = t + "|" + systemCode +"|"+ functionCode +"|"+ operationType +"|"+ operationState;
			System.out.println(message);
			logger.info(message);
		}
		System.out.println("doAfterThrow!");
	}
	
	/**
	 * 获取连接点方法的日志注解BO
	 * 
	 * @param joinPoint 连接点
	 * @return 日志BO的注解类
	 * @throws Throwable
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private LogBOAnnotation getLogBO(JoinPoint joinPoint) throws Throwable{
		//取得连接点方法的参数
		Object[] parms = joinPoint.getArgs();
		int length = parms.length;
		if(length!=0){
			//取得方法参数的类
			Class[] cls = new Class[length];
			for(int i = 0;i<length;i++){
				cls[i] = parms[i].getClass();
			}
			//取得连接点所在的类
			Class cl = joinPoint.getTarget().getClass();
			String methodName = joinPoint.getSignature().getName();
			//根据方法名和参数类型，取得连接点所在的方法
			Method method = cl.getMethod(methodName, cls);
//			cl.getMethod(methodName,(Class[])null);
			if (method != null) {  
                boolean hasAnnotation = method.isAnnotationPresent(LogBOAnnotation.class);   
                if (hasAnnotation) {
                		//取得方法的日志BO类型注解
                		LogBOAnnotation bo = method.getAnnotation(LogBOAnnotation.class);
                		return bo;
//                		Calendar operationTime = Calendar.getInstance();	
//                		Timestamp t = new Timestamp(operationTime.getTimeInMillis());
                		
                }
			}
		}
		return null;
	}
	
	private Object[] getParms(JoinPoint joinPoint) throws Throwable{
		//取得连接点方法的参数
		Object[] parms = joinPoint.getArgs();
		return parms;
	}
}
