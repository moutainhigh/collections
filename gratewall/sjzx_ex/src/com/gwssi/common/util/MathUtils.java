package com.gwssi.common.util;

import java.math.BigDecimal;

public class MathUtils
{

	public final static String	MATH_STRING	= "string";

	public final static String	MATH_Double	= "double";

	public final static String	MATH_FLOAT	= "float";

	public final static String	MATH_INT	= "int";

	/**
	 * 四舍五入的方法,并设置保留几位小数
	 * 
	 * @param obj
	 * @return
	 */
	public static String toCountUp(Object obj, int count)
	{
		return new BigDecimal(String.valueOf(obj)).setScale(count,
				BigDecimal.ROUND_HALF_UP).toString();
	}

	/**
	 * 除法
	 * 
	 * @param obj1
	 * @param obj2
	 * @param count
	 *            保留小数位置
	 * @return
	 */
	private static BigDecimal divideNum(Object obj1, Object obj2, int count)
	{
		return new BigDecimal(String.valueOf(obj1)).divide(new BigDecimal(
				String.valueOf(obj2)), count, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 除法
	 * 
	 * @param obj1
	 * @param obj2
	 * @param count
	 *            保留小数位置
	 * @return
	 */
	public static String divide(Object obj1, Object obj2, int count)
	{
		return divideNum(obj1, obj2, count).stripTrailingZeros()
				.toPlainString();
	}

	/**
	 * 加法
	 * 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static String add(Object obj1, Object obj2)
	{
		return new BigDecimal(String.valueOf(obj1)).add(
				new BigDecimal(String.valueOf(obj2))).toString();
	}

	/**
	 * 减法
	 * 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static String sub(Object obj1, Object obj2)
	{
		return new BigDecimal(String.valueOf(obj1)).subtract(
				new BigDecimal(String.valueOf(obj2))).toString();
	}

	/**
	 * 乘法
	 * 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static String mult(Object obj1, Object obj2)
	{
		return new BigDecimal(String.valueOf(obj1)).multiply(
				new BigDecimal(String.valueOf(obj2))).toString();
	}
	
	public static String percentage(Object obj1, Object obj2, int count)
	{
		String result = "";
		BigDecimal per_num = new BigDecimal(obj1.toString());
		BigDecimal divideNum = new BigDecimal(obj2.equals("0") ? "1"
				: obj2.toString());
		if (obj1.equals("0") || obj2.equals("0")) {
			return "0";
		}
		result = per_num.divide(divideNum, count, BigDecimal.ROUND_HALF_UP)
				.multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString();
		return result;
	}

	public static void main(String[] args)
	{
		System.out.println(MathUtils.percentage("4","39757", 6));
	}
}