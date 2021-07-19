//package com.gwssi.rodimus.log;
//
//import org.apache.log4j.Logger;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.Signature;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//
//import com.gwssi.rodimus.util.JSonUtil;
//import com.gwssi.ebaic.domain.TPtYhBO;
//import com.gwssi.rodimus.util.HttpSessionUtil;
//
///**
// * <h2>通过AOP方式记录业务异常。</h2>
// * <p>
// * TODO lixibo 1、创建业务异常表；2、设计方法名和业务模块、业务功能描述映射关系的解决办法（可配置，不应该在代码中），业务日志应该是可统计的，人类可读的。
// * 	需要考虑：	即时插入数据库会影响性能，如果放入队列就几乎不用时间了；
// * 			需要处理好和异常日志的关系，最好是两边不重复存，但两边能关联上；
// * 			记录当前登录用户，需要处理好和登录日志的关系（登录本身执行业务方法吗？）；
// * 			转JSON会较话时间，需要得出一个性能、功能平衡的方案。
// * </p>
// * <p>
// * TODO chaiyongbing 调通代码：1、切面；2、记录功能（无参数的情况，无返回值的情况、异常的情况，等等）；3、性能测试。
// * </p>
// * 
// * @author liuhailong
// */
//@Aspect
//public class BizLogAspect {
//	
//	protected static Logger logger = Logger.getLogger(BizLogAspect.class);
//	
//	/**
//	 * 切所有service包下的类的方法。
//	 * 
//	 * @param jp
//	 * @return 
//	 * @throws java.lang.Throwable
//	 */
//	@Around("execution(* com.gwssi..*.service.*.*(..))")
//	public Object log(ProceedingJoinPoint jp) throws Throwable{
//		//TODO 跟踪一下每一行代码的执行时间，看一下是否有性能瓶颈
//		
//		// 记录当前登录用户
//		TPtYhBO user = HttpSessionUtil.getCurrentUser();
//		if(logger.isDebugEnabled()){
//			logger.debug(String.format("用户：%s",
//					JSonUtil.toJSONString(user, false)));
//		}
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
