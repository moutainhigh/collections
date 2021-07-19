package com.gwssi.doublePublic.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogLogger {
	
	
	@Pointcut("execution (* com.gwssi.doublePublic..*.*(..))")
	private void anyMethod() {
		//System.out.println("============进入anyMethod方法==============");
	}
	
	
	@Before("anyMethod()")
    public void before(JoinPoint call) {
        //System.out.println("切点工作-----仅记录当前的SQL和对应的参数");
        String className = call.getTarget().getClass().getName();  
        String methodName = call.getSignature().getName(); 
        Object[] args = call.getArgs();
       /* if (args != null && args.length > 0 && args[0].getClass() == String.class) {
            args[0] = "改变后的参数1";
            System.out.println(args[0]);
        }*/
       // System.out.println("【!!通知】: 【 " + className + " 】类的   【"   + methodName + "】   方法开始了"); 
		
        
        for (Object object : args) {
			//System.out.println("传递的参数列表为：  "  + object);
		}
		
    } 
	
	
	
	
}
