//package cn.gwssi.report.util;
//
///* 
// * PURPOSE : 设置报表参数 
// */  
//
//import java.util.Calendar;
//import java.util.GregorianCalendar;
//import java.util.List;
//
//import com.cognos.developer.schemas.bibus._3.ParameterValue;
//import com.cognos.developer.schemas.bibus._3.ParmValueItem;
//import com.cognos.developer.schemas.bibus._3.SimpleParmValueItem;
//
///** 
// * 报表参数设置 
// *  
// * @author 
// */  
//public class SetReportParameterAL {  
//
//	/** 
//	 * 设置报表所需的所有参数值 
//	 *  
//	 * @param p 
//	 * @return 
//	 */  
//	public ParameterValue[] setReportParameters(RepParameterHelper   
//
//			p) {  
//		// 获取频率  
//		int repFrequency = p.getReportPutInfoDomain().getReportFreq  
//
//				();  
//		//获取结束日期  
//		Calendar endDate = p.getReportDate();  
//		//获取参数、参数值  
//		List<ReportPropertyDomain> list = p.getProperties();  
//		int propNum = 0;  
//		if (endDate != null) {  
//
//			/* 根据期末日期和报表频率获得报告期间的开始日期 */  
//			Calendar startDate = getStartDateByRepFrequency  
//
//					(repFrequency,  
//							endDate);  
//
//			if (list != null) {  
//				propNum = list.size();  
//			}  
//			//报表运行参数对象  
//			ParameterValue[] parameters=null;  
//			parameters = new ParameterValue[propNum + 1]; // 固定参数数  组+开始结束日期  
//			ParameterValue parameter = null;  
//			ReportPropertyDomain propDomain = null;  
//			for (int i = 0; i < propNum; i++) {  
//				propDomain = list.get(i);  
//				//设置参数名，参数值至参数对象  
//				parameter = setParameter(p, propDomain.getParameterName  
//
//						(),propDomain.getParameterValue());  
//				parameters = parameter;  
//			}  
//			//设置结束日期  
//			parameters[propNum] = setParameter(p,   
//
//					ParameterUtil.END_DATE, calendarToString(endDate));  
//			//设置开始日期  
//			parameters[propNum + 1] = setParameter(p,   
//
//					ParameterUtil.START_DATE,  
//					calendarToString(startDate));  
//			return parameters;  
//		} else {  
//			return null;  
//		}  
//	}  
//
//	/** 
//	 * 根据频率和结束日期，获得开始日期 
//	 *  
//	 * @param repFrequency 
//	 *            报表频率，（日，周，月，季，半年，年 分别对应 
//
//1,2,3,4,5,6） 
//	 * @param endDate 
//	 *            结束日期 
//	 * @return 
//	 */  
//	private Calendar getStartDateByRepFrequency(int repFrequency,  
//			Calendar endDate) {  
//		Calendar startDate = new GregorianCalendar();  
//		startDate.set(Calendar.YEAR, endDate.get(Calendar.YEAR));//设  置年  
//		startDate.set(Calendar.MONTH, endDate.get(Calendar.MONTH));//  设置月  
//		startDate.set(Calendar.DAY_OF_MONTH, endDate.get  
//
//				(Calendar.DAY_OF_MONTH));//设置日  
//
//		switch (repFrequency) {  
//
//		// 日报的开始日期，开始日期和结束日期同一日  
//			case ParameterUtil.DAY:  
//			return startDate;  
//
//			// 周报的开始日期，开始日期为星期一  
//		case ParameterUtil.WEEK:  
//			startDate.set(Calendar.DAY_OF_WEEK, 1);  
//			break;  
//
//			// 月报的开始日期，开始日期为月初  
//		case ParameterUtil.MONTH:  
//			startDate.set(Calendar.DAY_OF_MONTH, 1);  
//			break;  
//
//			// 季报的开始日期  
//		case ParameterUtil.SEASON:  
//			switch (endDate.get(Calendar.MONTH)) {  
//			case Calendar.MARCH://第一季度  
//				startDate.set(Calendar.MONTH, 0);//开始日期，1月1日  
//				startDate.set(Calendar.DAY_OF_MONTH, 1);  
//				break;  
//			case Calendar.JUNE://第二季度  
//				startDate.set(Calendar.MONTH, 3);//开始日期，4月1日  
//				startDate.set(Calendar.DAY_OF_MONTH, 1);  
//				break;  
//			case Calendar.SEPTEMBER://第三季度  
//				startDate.set(Calendar.MONTH, 6);//开始日期，7月1日  
//				startDate.set(Calendar.DAY_OF_MONTH, 1);  
//				break;  
//			case Calendar.DECEMBER://第四季度  
//				startDate.set(Calendar.MONTH, 9);//开始日期，10月1日  
//				startDate.set(Calendar.DAY_OF_MONTH, 1);  
//				break;  
//			default:  
//			}  
//			;  
//			break;  
//
//			// 半年报的开始日期  
//		case ParameterUtil.HALF_YEAR:  
//			if (endDate.get(Calendar.MONTH) == Calendar.JUNE) {//如果结  
////				束日期为6月份  
//				startDate.set(Calendar.MONTH, 0);//开始日期，1月1日  
//				startDate.set(Calendar.DAY_OF_MONTH, 1);  
//			} else {  
//				startDate.set(Calendar.MONTH, 6);//开始日期，7月1日  
//				startDate.set(Calendar.DAY_OF_MONTH, 1);  
//			}  
//			;  
//			break;  
//
//			// 年报的开始日期  
//		case ParameterUtil.YEAR:  
//			startDate.set(Calendar.MONTH, 0);//开始日期1月1日  
//			startDate.set(Calendar.DAY_OF_MONTH, 1);  
//			break;  
//		default:  
//		}  
//
//		return startDate;  
//	}  
//
//	/** 
//	 * Calendar转换为日期格式的字符串 
//	 *  
//	 * @param calendar 
//	 * @return 
//	 */  
//	private String calendarToString(Calendar calendar) {  
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
//				String date = dateFormat.format(calendar.getTime());  
//
//				return date;  
//	}  
//
//	/** 
//	 * 设置参数 
//	 *  
//	 * @param paramName 
//	 *            参数名 
//	 * @param paramValue 
//	 *            参数值 
//	 * @return 
//	 */  
//	private ParameterValue setParameter(RepParameterHelper p,String paramName,  
//			String paramValue) {  
//		//参数对象  
//		ParameterValue parameter = new ParameterValue();  
//		/* 
//  同个参数名但有一组值（in(a,b,c,d)） 
//		 */  
//		paramValues = paramValue.split(",");//分割一组值  
//		int paramNum = paramValues.length;  
//		ParmValueItem[] pvi = new ParmValueItem[paramNum];  
//		//分割后循环依次设置到参数对象中  
//		for (int i = 0; i < paramNum; i++) {  
//			SimpleParmValueItem item = new SimpleParmValueItem();  
//			item.setUse(paramValues);//使用值  
//			pvi = item;  
//		}  
//		parameter.setName(paramName);  
//		parameter.setValue(pvi);  
//
//		return parameter;  
//	}  
//}  