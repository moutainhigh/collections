//package com.gwssi.rodimus.log;
//
//import org.apache.log4j.Logger;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.Signature;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//
//import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
//import com.gwssi.rodimus.util.JSonUtil;
//import com.gwssi.rodimus.util.HttpSessionUtil;
//
///**
// * SQL日志。
// * 
// * @author liuhailong
// */
//@Aspect
//public class SqlLogAspect {
//
//	protected static Logger logger = Logger.getLogger(SqlLogAspect.class);
//	
//	/**
//	 * 
//	 * @param dao
//	 * @param sqlCommand
//	 * @param params
//	 */
//	public static void log(IPersistenceDAO dao,String sqlCommand,Object result,Throwable e,Object...params){
//		HttpSessionUtil.getCurrentUserId();
//		HttpSessionUtil.getId();
//	}
//	
//	/**
//	 * 切所有service包下的类的方法。
//	 * 
//	 * @param jp
//	 * @return 
//	 * @throws java.lang.Throwable
//	 */
//	@Around("execution(* com.gwssi..*.DaoUtil.*(..))")
//	public Object log(ProceedingJoinPoint jp) throws Throwable{
//		
//		// 记录基本信息
//		Object target = jp.getTarget();// 被调用的Bean
//		Signature signature = jp.getSignature();// 方法签名
//		String methodName = signature.getName();// 方法名
//		Object[] args = jp.getArgs();// 方法参数
//		if(logger.isDebugEnabled()){
//			logger.debug(String.format("类：【%s】，方法：【%s】\r\n参数：【%s】", 
//					target.getClass(),methodName,
//					JSonUtil.toJSONString(args, false)));
//		}
//		
//		// 执行方法调用
//		Object ret = null;
//		try{
//			ret = jp.proceed(args);
//		}catch(Throwable e){
//			// 记录异常
//			if(logger.isDebugEnabled()){
//				logger.debug(String.format("\r\n异常：%s", JSonUtil.toJSONString(e, false)));
//			}
//			throw e;
//		}
//		
//		// 记录返回值
//		if(logger.isDebugEnabled()){
//			logger.debug(String.format("\r\n返回值：%s", JSonUtil.toJSONString(ret, false)));
//		}
//		return ret;
//	}
//}
