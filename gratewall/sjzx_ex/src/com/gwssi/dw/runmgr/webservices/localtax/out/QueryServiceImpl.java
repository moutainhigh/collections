package com.gwssi.dw.runmgr.webservices.localtax.out;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.database.DBOperation;
import com.gwssi.common.database.DBOperationFactory;
import com.gwssi.dw.runmgr.services.common.Constants;

public class QueryServiceImpl implements QueryService
{
	private DBOperation operation = null;
	
	public QueryServiceImpl()
	{
		operation = DBOperationFactory.createOperation();
	}
	
	/**
	 * ��ѯ������¼
	 * @param qymc
	 * @param yyzzh
	 * @param state
	 * @return
	 */
	protected ReturnMultiGSData queryQY_InfoWithState(String qymc, String yyzzh, int state){
		ReturnMultiGSData data = new ReturnMultiGSData();
		if(isNull(qymc) && isNull(yyzzh)){
        	data.setFHDM(Constants.SERVICE_FHDM_INPUT_PARAM_ERROR);
    		data.setJSJLS("0");
    		data.setKSJLS("0");
    		data.setZTS("0");
    		return data;
        }
		
		String sql = createSingleRecordSQL(qymc, yyzzh, state);
//		System.out.println("sql ====== "+sql);
		return queryData(sql, 1, 1, state, "1");
	}
	
	protected ReturnMultiGSData queryQY_InfoListWithState(String cxrqq, String cxrqz, String ksjls, String jsjls, int state){
		//��������Ƿ�Ϸ�
		if((!isValidDate(cxrqq)) || (!isValidDate(cxrqz))){
			ReturnMultiGSData data = new ReturnMultiGSData();
        	data.setFHDM(Constants.SERVICE_FHDM_INPUT_PARAM_ERROR);
    		data.setJSJLS(jsjls);
    		data.setKSJLS(ksjls);
    		data.setZTS("0");
    		return data;
        }
		
		//�������˳���Ƿ���ȷ
		if(!isValidOrder(cxrqq, cxrqz)){
			ReturnMultiGSData data = new ReturnMultiGSData();
			data.setFHDM(Constants.SERVICE_FHDM_INPUT_PARAM_ERROR);
    		data.setJSJLS(jsjls);
    		data.setKSJLS(ksjls);
    		data.setZTS("0");
    		return data;
		}
		
		//��������Ƿ񳬹��޶��Ĳ�ѯ����
		if(!isValidRange(cxrqq, cxrqz)){
			ReturnMultiGSData data = new ReturnMultiGSData();
			data.setFHDM(Constants.SERVICE_FHDM_OVER_DATE_RANGE);
    		data.setJSJLS(jsjls);
    		data.setKSJLS(ksjls);
    		data.setZTS("0");
    		return data;
		}
		
		//���"��ʼ��¼��"��"������¼��"�Ƿ�Ϊ��Ч����
		int ksjlsInt = 0;
		int jsjlsInt = 2;
		try{
			ksjlsInt = Integer.parseInt(ksjls);
			jsjlsInt = Integer.parseInt(jsjls);
			//��ʼ�ͽ�����¼������Ϊ���������ҽ�����¼������ȿ�ʼ��¼����
			if(ksjlsInt <= 0 || jsjlsInt <= 0 || jsjlsInt < ksjlsInt){
				ReturnMultiGSData data = new ReturnMultiGSData();
				data.setFHDM(Constants.SERVICE_FHDM_INPUT_PARAM_ERROR);
	    		data.setJSJLS(jsjls);
	    		data.setKSJLS(ksjls);
	    		data.setZTS("0");
	    		return data;
			}
			//�ж�ȡֵ�����Ƿ�����޶�������
			if((jsjlsInt - ksjlsInt) > Constants.SERVICE_DEFAULT_MAX_RECORDS){
				ReturnMultiGSData data = new ReturnMultiGSData();
				data.setFHDM(Constants.SERVICE_FHDM_OVER_MAX);
	    		data.setJSJLS(jsjls);
	    		data.setKSJLS(ksjls);
	    		data.setZTS("0");
	    		return data;
			}
		}catch(NumberFormatException nfe){
			ReturnMultiGSData data = new ReturnMultiGSData();
			data.setFHDM(Constants.SERVICE_FHDM_INPUT_PARAM_ERROR);
    		data.setJSJLS(jsjls);
    		data.setKSJLS(ksjls);
    		data.setZTS("0");
    		return data;
		}
		
		String sql = createRangeSQL(cxrqq, cxrqz, ksjlsInt, jsjlsInt, state);
//		System.out.println("sql ====== "+sql);
		
		return queryData(sql, ksjlsInt, jsjlsInt, state, countRecord(cxrqq, cxrqz, state));
	}
	
	protected ReturnMultiGSData queryData(String sql, int ksjlsInt, int jsjlsInt, int state, String total){
		
		ReturnMultiGSData data = new ReturnMultiGSData();
    	try {
			List list = operation.select(sql);
			if(list.size() < 1){
				data.setFHDM(Constants.SERVICE_FHDM_NO_RESULT);
	    		data.setJSJLS("" + jsjlsInt);
	    		data.setKSJLS("" + ksjlsInt);
	    		data.setZTS("0");
	    		return data;
			}
			GSDJInfo[] infos = new GSDJInfo[list.size()];
			for(int i = 0; i < list.size(); i++){
				Map map = (Map) list.get(i);
				GSDJInfo info = new GSDJInfo();
				info.setYYZZH(map.get("YYZZH") == null ? "" : trimSpace(""+map.get("YYZZH")));//Ӫҵִ��ע���
				info.setQYMC(map.get("QYMC") == null ? "" :   trimSpace(""+map.get("QYMC"))); //��ҵ����
				info.setYB(map.get("YB") == null ? "" :       trimSpace(""+map.get("YB")));	//�ʱ�
				info.setDH(map.get("DH") == null ? "" :       trimSpace(""+map.get("DH")));	//�绰
				info.setJYFW(map.get("JYFW") == null ? "" :   trimSpace(""+map.get("JYFW"))); //��Ӫ��Χ
				info.setZCZB(map.get("ZCZB") == null ? "" :   trimSpace(""+map.get("ZCZB"))); //ע���ʱ�
				info.setFRXM(map.get("FRXM") == null ? "" :   trimSpace(""+map.get("FRXM"))); //���������˻���������
				info.setZJLXDM(map.get("ZJLXDM") == null ? "" : trimSpace(""+map.get("ZJLXDM")));//���������˻�����֤�����ʹ���
				info.setFRSFZH(map.get("FRSFZH") == null ? "" : trimSpace(""+map.get("FRSFZH")));//���������˻��������֤��
				info.setGXQX(map.get("GXQX") == null ? "" :   trimSpace(""+map.get("GXQX")));   //��Ͻ����				
				info.setDJZCLXDM(map.get("DJZCLXDM") == null ? "" : trimSpace(""+map.get("DJZCLXDM")));//��ҵ����
				info.setZSDZ(map.get("ZSDZ") == null ? "" :   trimSpace(""+map.get("ZSDZ")));   //ס��
				info.setRQ(map.get("CLRQ") == null ? "" : format(trimSpace(""+map.get("CLRQ"))));//��׼����
				infos[i] = info;
			}
			data.setFHDM(Constants.SERVICE_FHDM_SUCCESS);
			data.setJSJLS("" + jsjlsInt);
    		data.setKSJLS("" + ksjlsInt);
			data.setGSDJ_INFO_ARRAY(infos);
			data.setZTS(total);
		} catch (Exception e) {
			e.printStackTrace();
			data.setFHDM(Constants.SERVICE_FHDM_SYSTEM_ERROR);
			data.setJSJLS("" + jsjlsInt);
    		data.setKSJLS("" + ksjlsInt);
    		data.setZTS("0");
		} 
		
		return data;
	}
	
	private String trimSpace(String str){
		str = str.trim();
		while(str.startsWith("��")){
			int idx = str.indexOf("��");
			str = str.substring(idx + 1);
		}
		while(str.endsWith("��")){
			int idx = str.lastIndexOf("��");
			str = str.substring(0, idx);
		}
		return str;
	}
	
	private static String format(String from) throws ParseException{
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-M-d");
    	Date d = df.parse(from);
		df.applyPattern("yyyy-M-d H:mm:ss");
		return df.format(d);    	
    }
	
	public ReturnMultiGSData queryBGQY_Info(String qymc, String yyzzh)
	{
		return queryQY_InfoWithState(qymc, yyzzh, Constants.SERVICE_QY_STATE_BG);
	}
	
	public ReturnMultiGSData queryBGQY_InfoList(String cxrqq, String cxrqz, String ksjls, String jsjls)
	{
		return queryQY_InfoListWithState(cxrqq, cxrqz, ksjls, jsjls, Constants.SERVICE_QY_STATE_BG);
	}

	public ReturnMultiGSData queryDXQY_Info(String qymc, String yyzzh)
	{
		return queryQY_InfoWithState(qymc, yyzzh, Constants.SERVICE_QY_STATE_DX);
	}

	public ReturnMultiGSData queryDXQY_Info_List(String cxrqq, String cxrqz, String ksjls, String jsjls)
	{
		return queryQY_InfoListWithState(cxrqq, cxrqz, ksjls, jsjls, Constants.SERVICE_QY_STATE_DX);
	}

	public ReturnMultiGSData queryQY_Info(String qymc, String yyzzh)
	{
		return queryQY_InfoWithState(qymc, yyzzh, Constants.SERVICE_QY_STATE_OPEN);
	}

	public ReturnMultiGSData queryQY_Info_List(String cxrqq, String cxrqz, String ksjls, String jsjls)
	{
		return queryQY_InfoListWithState(cxrqq, cxrqz, ksjls, jsjls, Constants.SERVICE_QY_STATE_OPEN);
	}

	public ReturnMultiGSData queryZXQY_Info(String qymc, String yyzzh)
	{
		return queryQY_InfoWithState(qymc, yyzzh, Constants.SERVICE_QY_STATE_ZX);
	}

	public ReturnMultiGSData queryZXQY_Info_List(String cxrqq, String cxrqz, String ksjls, String jsjls)
	{
		return queryQY_InfoListWithState(cxrqq, cxrqz, ksjls, jsjls, Constants.SERVICE_QY_STATE_ZX);
	}
	
	/**
	 * ��֤�ַ����Ƿ�Ϊ��
	 * @param str
	 * @return
	 */
    private boolean isNull(String str){
    	if(str == null || str.trim().equals("")){
    		return true;
    	}
    	return false;
    }
    
    /**
     * ��֤���ںϷ��ԣ�
     * @param date -- ��ʽ"yyyyMMdd"
     * @return boolean
     */
    private boolean isValidDate(String date){
    	try{
    		Integer.parseInt(date);
    		DateFormat format = new SimpleDateFormat(Constants.SERVICE_IN_PARAM_DATE_FORMAT);
    		format.parse(date);
    	}catch(Exception e){
    		return false;
    	}
    	return true;
    }
    
    /**
     * ������ѯ������¼��SQL���
     * @param qymc
     * @param yyzzh
     * @param state
     * @return
     */
    private String createSingleRecordSQL(String qymc, String yyzzh, int state){
    	StringBuffer sql = new StringBuffer();
		sql.append("SELECT YYZZH, QYMC, ZSDZ, YB, DH, JYFW, ZCZB, FRXM, ZJLXDM, FRSFZH, GXQX, DJZCLXDM, CLRQ, HZRQ, QYZT, LRRQ, QYFL FROM BJGS_JBXXB WHERE (QYMC='")
		   .append(qymc)
		   .append("' OR YYZZH='")
		   .append(yyzzh)
		   .append("') AND QYZT='")
		   .append(state)
		   .append("'");
		
		return sql.toString();
    }
    
    /**
     * ����ĳ���ڷ�Χ�ڵĲ�ѯ���������Ʒ��ؽ��������
     * @param dateFrom
     * @param dateEnd
     * @param ksjls
     * @param jsjls
     * @return
     */
    private String createRangeSQL(String dateFrom, String dateEnd, int ksjls, int jsjls, int state){
    	StringBuffer sql = new StringBuffer();
//    	if(state == Constants.SERVICE_QY_STATE_OPEN){
//        	sql.append("SELECT * FROM (SELECT ROWNUM AS rn, YYZZH, QYMC, ZSDZ, YB, DH, JYFW, ZCZB, FRXM, ZJLXDM, FRSFZH, GXQX, DJZCLXDM, CLRQ, HZRQ, QYZT, LRRQ, QYFL FROM BJGS_JBXXB WHERE QYZT='12' AND HZRQ between '");
//    	}else if(state == Constants.SERVICE_QY_STATE_DX){
//        	sql.append("SELECT * FROM (SELECT ROWNUM AS rn, YYZZH, QYMC, ZSDZ, YB, DH, JYFW, ZCZB, FRXM, ZJLXDM, FRSFZH, GXQX, DJZCLXDM, CLRQ, HZRQ, QYZT, LRRQ, QYFL FROM BJGS_JBXXB WHERE QYZT='14' AND HZRQ between '");
//		}else if(state == Constants.SERVICE_QY_STATE_ZX){
//	    	sql.append("SELECT * FROM (SELECT ROWNUM AS rn, YYZZH, QYMC, ZSDZ, YB, DH, JYFW, ZCZB, FRXM, ZJLXDM, FRSFZH, GXQX, DJZCLXDM, CLRQ, HZRQ, QYZT, LRRQ, QYFL FROM BJGS_JBXXB WHERE QYZT='13' AND HZRQ between '");
//		}else{
//	    	sql.append("SELECT * FROM (SELECT ROWNUM AS rn, YYZZH, QYMC, ZSDZ, YB, DH, JYFW, ZCZB, FRXM, ZJLXDM, FRSFZH, GXQX, DJZCLXDM, CLRQ, HZRQ, QYZT, LRRQ, QYFL FROM BJGS_JBXXB WHERE QYZT='15' AND HZRQ between '");
//		}
    	sql.append("SELECT * FROM (SELECT ROWNUM AS rn, YYZZH, QYMC, ZSDZ, YB, DH, JYFW, ZCZB, FRXM, ZJLXDM, FRSFZH, GXQX, DJZCLXDM, CLRQ FROM BJGS_JBXXB WHERE QYZT='")
    	   .append(state)   
    	   .append("' AND TO_CHAR(LRRQ,'yyyyMMdd') between '")
    	   .append(dateFrom)
    	   .append("' AND '")
    	   .append(dateEnd)
    	   .append("' AND ROWNUM <= ")
    	   .append(jsjls)
    	   .append(") where rn >= ")
    	   .append(ksjls);
    	
		return sql.toString();
    }
    
    /**
     * ��ü�¼����
     * @param dateFrom
     * @param dateEnd
     * @param state
     * @return
     */
    private String countRecord(String dateFrom, String dateEnd, int state){
    	StringBuffer sql = new StringBuffer();
    	sql.append("SELECT COUNT(1) as total FROM (SELECT YYZZH FROM BJGS_JBXXB WHERE QYZT='")
  	   		.append(state)
  	   		.append("' AND TO_CHAR(LRRQ,'yyyyMMdd') between '")
  	   		.append(dateFrom)
  	   		.append("' AND '")
  	   		.append(dateEnd)
  	   		.append("')");
//    	System.out.println("count sql---->"+sql);
    	try {
			Map m = operation.selectOne(sql.toString());
//			System.out.println("total map====>"+m);
			return m.get("TOTAL").toString();
		} catch (DBException e) {
			e.printStackTrace();
		}
		
		return "0";
    }
    
    /**
     * ��鿪ʼ�����Ƿ��ڽ�������֮ǰ�����
     * @param from
     * @param to
     * @return boolean
     */
    private boolean isValidOrder(String from, String to){
    	try{
    		DateFormat format = new SimpleDateFormat(Constants.SERVICE_IN_PARAM_DATE_FORMAT);
    		Date d1 = format.parse(from);
    		Date d2 = format.parse(to);

    		if(!d1.before(d2)){//�Ƚ��������Ƿ����
    			return d1.equals(d2);
    		}
    		
    		return d1.before(d2);
    	}catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    }
    
    /**
     * �������䲻�ó����޶�����
     * @param from
     * @param to
     * @return
     */
    private boolean isValidRange(String from, String to){
    	try{
    		DateFormat format = new SimpleDateFormat(Constants.SERVICE_IN_PARAM_DATE_FORMAT);
    		Date d1 = format.parse(from);
    		Date d2 = format.parse(to);
    		long l1 = d1.getTime();
    		long l2 = d2.getTime();

    		if(d1.before(d2) || d1.equals(d2)){//�����ʼ�����ڽ�������ǰ�����ж��Ƿ�����޶�����
    			return Constants.SERVICE_IN_PARAM_MAX_QUERY_DATE >= (l2 - l1);
    		}else{
    			return false;
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    }    
}
