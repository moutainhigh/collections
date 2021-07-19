package com.gwssi.doublePublic.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SQLLogger {

	/**
	 * logger
	 */
	private static final Logger LOG = Logger.getLogger(SQLLogger.class);

	/**
	 * 当Sql执行时间超过该值时，则进行log warn级别题型，否则记录INFO日志。
	 */
	private long warnWhenOverTime = 2 * 60 * 1000L;

	@Around("execution(* org.springframework.jdbc.core.JdbcTemplate.*(..))")
	public Object logSqlExecutionTime(ProceedingJoinPoint joinPoint)
			throws Throwable {
		long startTime = System.currentTimeMillis();
		Object result = joinPoint.proceed();
		long costTime = System.currentTimeMillis() - startTime;
		if (costTime > warnWhenOverTime) {
			StringBuilder sb = new StringBuilder();
			sb.append("执行的方法 :").append(joinPoint.getSignature());
			sb.append("得到的语句及查询参数: ").append(arrayToString(joinPoint.getArgs()));
			sb.append(" 总用时[").append(costTime).append("]毫秒");
			LOG.warn(sb);
		} else if (LOG.isInfoEnabled()) {
			StringBuilder sb = new StringBuilder();
			sb.append("执行的方法  :").append(joinPoint.getSignature());
			sb.append("得到的查询语句及参数: ").append(arrayToString(joinPoint.getArgs()));
			sb.append(" 总用时[").append(costTime).append("]毫秒");
			LOG.info(sb);
		}
		return result;
	}

	private static String arrayToString(Object[] a) {
		if (a == null)
			return "null";

		int iMax = a.length - 1;
		if (iMax == -1)
			return "[]";

		StringBuilder b = new StringBuilder();
		b.append('[');
		for (int i = 0;; i++) {
			if (a[i] instanceof Object[]) {
				b.append(arrayToString((Object[]) a[i]));
			} else {
				b.append(String.valueOf(a[i]));
			}
			if (i == iMax)
				return b.append(']').toString();
			b.append(", ");
		}
	}
}
