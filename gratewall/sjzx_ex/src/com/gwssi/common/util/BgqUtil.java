package com.gwssi.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * �����ڸ���������
 * ���б����ڴ����뱨������ʾ��ת��
 * @author zhyi
 * @version v1.0.0
 */
/**
 * @author zhyi
 *
 */
public class BgqUtil
{
	public static List bgqListFactory(String bgqType,String begYear,String endYear){
		List bgqList = new ArrayList();
		int begin;
		int end;
		String year;
		begin = new Integer(begYear).intValue();
		end =   new Integer(endYear).intValue();
		if(begin>end){
			begin = new Integer(endYear).intValue();
			end =   new Integer(begYear).intValue();
		}
		
		for(int i=begin;i<=end;i++){
			year = new Integer(i).toString();
			if(bgqType.equals("1")){//�걨
				bgqList.addAll(BgqUtil.makeYearBgqList(year));
			}else if(bgqType.equals("2")){//���걨
				bgqList.addAll(BgqUtil.makeHalfYearBgqList(year));
			}else if(bgqType.equals("3")){//����
				bgqList.addAll(BgqUtil.makeQuarterBgqList(year));
			}else if(bgqType.equals("4")){//�±�
				bgqList.addAll(BgqUtil.makeMonthBgqList(year));
			}else if(bgqType.equals("5")){//���±�
				bgqList.addAll(BgqUtil.makeHalfMonthBgqList(year));
			}else if(bgqType.equals("6")){//ʮ�걨,���Զ������
				bgqList.addAll(BgqUtil.makeYearBgqList(year));
			}else if(bgqType.equals("7")){//���걨
				bgqList.addAll(BgqUtil.makeYearBgqList(year));
			}else if(bgqType.equals("8")){//���걨
				bgqList.addAll(BgqUtil.makeYearBgqList(year));
			}
			year = null;
		}
		
		return bgqList;
	}
	
	public static HashMap bgqMapFactory(String bgqType,String begYear,String endYear){
		HashMap bgqHashMap = new HashMap();
		int begin;
		int end;
		String year;
		begin = new Integer(begYear).intValue();
		end =   new Integer(endYear).intValue();
		if(begin>end){
			begin = new Integer(endYear).intValue();
			end =   new Integer(begYear).intValue();
		}
		
		for(int i=begin;i<=end;i++){
			year = new Integer(i).toString();
			if(bgqType.equals("1")){//�걨
				bgqHashMap.putAll(BgqUtil.makeYearBgq(year));
			}else if(bgqType.equals("2")){//���걨
				bgqHashMap.putAll(BgqUtil.makeHalfYearBgq(year));
			}else if(bgqType.equals("3")){//����
				bgqHashMap.putAll(BgqUtil.makeQuarterBgq(year));
			}else if(bgqType.equals("4")){//�±�
				bgqHashMap.putAll(BgqUtil.makeMonthBgq(year));
			}else if(bgqType.equals("5")){//���±�
				bgqHashMap.putAll(BgqUtil.makeHalfMonthBgq(year));
			}else if(bgqType.equals("6")){//ʮ�걨,���Զ������
				bgqHashMap.putAll(BgqUtil.makeYearBgq(year));
			}else if(bgqType.equals("7")){//���걨
				bgqHashMap.putAll(BgqUtil.makeYearBgq(year));
			}else if(bgqType.equals("8")){//���걨
				bgqHashMap.putAll(BgqUtil.makeYearBgq(year));
			}
			year = null;
		}
		
		return bgqHashMap;
	}
	
	private static HashMap getBgqByYear(String year){
		HashMap bgqMaps = new HashMap();
		//�����걨����
		bgqMaps.putAll(makeYearBgq(year));
		//�����±�����
		bgqMaps.putAll(makeMonthBgq(year));
		//���ɼ�������
		bgqMaps.putAll(makeQuarterBgq(year));
		return bgqMaps;
	}
	
	private static HashMap makeYearBgq(String year){
		HashMap bgqMaps = new HashMap();
		String bgqDm;
		String bgqMc;
		//�����걨����
		bgqDm = year+"0000";
		bgqMc = year+"��";
		bgqMaps.put(bgqDm, bgqMc);
		bgqDm = null;
		bgqMc = null;
		return bgqMaps;
	}
	//�����±������±���Ѯ���ı����ڶ�
	private static HashMap makeMonthBgq(String year){
		HashMap bgqMaps = new HashMap();
		String month;
		for(int i=1;i<13;i++){
			month = new Integer(i).toString();
			if(i<10)month = "0"+month;
			bgqMaps.put(year+"0"+month+"0", year+"��"+i+"��");
		}
		return bgqMaps;
	}
	
//	���ɰ��±������ڶ�
	private static HashMap makeHalfMonthBgq(String year){
		HashMap bgqMaps = new HashMap();
		String month;
		for(int i=1;i<13;i++){
			month = new Integer(i).toString();
			if(i<10)month = "0"+month;
			bgqMaps.put(year+"0"+month+"4", year+"��"+i+"���ϰ���");
			bgqMaps.put(year+"0"+month+"5", year+"��"+i+"���°���");
		}
		return bgqMaps;
	}
	
//	�����±������±���Ѯ���ı����ڶ�
	private static HashMap makeXunBgq(String year){
		HashMap bgqMaps = new HashMap();
		String month;
		for(int i=1;i<13;i++){
			month = new Integer(i).toString();
			if(i<10)month = "0"+month;
			bgqMaps.put(year+"0"+month+"1", year+"��"+i+"����Ѯ");
			bgqMaps.put(year+"0"+month+"2", year+"��"+i+"����Ѯ");
			bgqMaps.put(year+"0"+month+"3", year+"��"+i+"����Ѯ");
		}
		return bgqMaps;
	}
	
	private static HashMap makeQuarterBgq(String year){
		HashMap bgqMaps = new HashMap();
		String bgqDm;
		String bgqMc;
		String quarter;
		for(int i=1;i<5;i++){
			quarter = new Integer(i).toString();
			bgqDm = year+quarter+"000";
			bgqMc = year+"��"+i+"����";
			bgqMaps.put(bgqDm, bgqMc);
		}
		return bgqMaps;
	}
	
	private static HashMap makeHalfYearBgq(String year){
		HashMap bgqMaps = new HashMap();
		bgqMaps.put(year+"5000", year+"���ϰ���");
		bgqMaps.put(year+"6000", year+"���°���");
		return bgqMaps;
	}
	
	
	/**
	 * �����걨���ڴ��뼯
	 * @param year
	 * @return
	 */
	private static List makeYearBgqList(String year){
		List bgqList = new ArrayList();
		String bgqDm;
		//�����걨����
		bgqDm = year+"0000";
		bgqList.add(bgqDm);
		bgqDm = null;
		return bgqList;
	}
	/**
	 * �����±����ڴ��뼯
	 * @param year
	 * @return
	 */
	private static List makeMonthBgqList(String year){
		List bgqList = new ArrayList();
		String bgqDm;
		String month;
		for(int i=1;i<13;i++){
			month = new Integer(i).toString();
			if(i<10)month = "0"+month;
			bgqList.add(year+"0"+month+"0");
		}
		return bgqList;
	}
	
//	���ɰ��±������ڶ�
	private static List makeHalfMonthBgqList(String year){
		List bgqList = new ArrayList();
		String month;
		for(int i=1;i<13;i++){
			month = new Integer(i).toString();
			if(i<10)month = "0"+month;
			bgqList.add(year+"0"+month+"4");
			bgqList.add(year+"0"+month+"5");
		}
		return bgqList;
	}
	
//	�����±������±���Ѯ���ı����ڶ�
	private static List makeXunBgqList(String year){
		List bgqList = new ArrayList();
		String month;
		for(int i=1;i<13;i++){
			month = new Integer(i).toString();
			if(i<10)month = "0"+month;
			bgqList.add(year+"0"+month+"1");
			bgqList.add(year+"0"+month+"2");
			bgqList.add(year+"0"+month+"3");
		}
		return bgqList;
	}
	
	private static List makeQuarterBgqList(String year){
		List bgqList = new ArrayList();
		String bgqDm;
		String quarter;
		for(int i=1;i<5;i++){
			quarter = new Integer(i).toString();
			bgqDm = year+quarter+"000";
			bgqList.add(bgqDm);
		}
		return bgqList;
	}
	
	private static List makeHalfYearBgqList(String year){
		List bgqList = new ArrayList();
		bgqList.add(year+"5000");
		bgqList.add(year+"6000");
		return bgqList;
	}
	
	public static String dmToMc(String bgqDm){
		String bagMc = null;
		String year,quarter,month,xun;
		
		if(bgqDm.length()==8){
			year = bgqDm.substring(0,4);
			quarter = bgqDm.substring(4,5);
			month = bgqDm.substring(5,7);
			xun = bgqDm.substring(7,8);
			
			if(quarter.equals("0")&&month.equals("00")&&xun.equals("0")){
				bagMc = year+"��";
			}else if(!quarter.equals("0")){
				bagMc = year+"��"+quarter+"����";
			}else if(!month.equals("0")){
				if(xun.equals("0")){
					bagMc = year+"��"+month+"��";
				}else if(xun.equals("1")){
					bagMc = year+"��"+month+"����Ѯ";
				}else if(xun.equals("2")){
					bagMc = year+"��"+month+"����Ѯ";
				}else if(xun.equals("3")){
					bagMc = year+"��"+month+"����Ѯ";
				}
			}
		}
		
		return bagMc;
	}
	
	public static String mcToDm(String bgqMc){
		String bagDm = null;
		
		return bagDm;
	}
	
	public static void main(String[] args){
		System.out.println(BgqUtil.dmToMc("20060010"));
	}
}
