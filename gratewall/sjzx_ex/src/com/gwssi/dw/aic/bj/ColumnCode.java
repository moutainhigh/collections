package com.gwssi.dw.aic.bj;

import java.util.List;
import java.util.Map;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.database.DBOperation;
import com.gwssi.common.database.DBOperationFactory;

public class ColumnCode
{
	public static final String CODE = "CODE";
	public static final String NAME = "NAME";
	public static final String MIX = "MIX";
	
	private ColumnCode(){}
	
	public static List parseColumnCode(String columns, List list, String displayType){
		if(displayType.equalsIgnoreCase(CODE)){
			return list;
		}
		String col = parseMixColumns(columns);
		String sql = "SELECT DEMO,COLUMN_NAME,COLUMN_BYNAME FROM V_COLUMN_CODE_NEW WHERE COLUMN_NAME_ALL IN ("+col+")";
		DBOperation operation = DBOperationFactory.createTimeOutOperation();
		List l = null;
		try {
			//��ѯ�ֶδ������Ϣ
			l = operation.select(sql);
			if(l == null || l.size() == 0){
				return list;
			}
			//ѭ��ÿ���������ѯ������ֵ������list��Ӧ�ֶε�ֵ
			for(int i = 0; i < l.size(); i++){
				Map map = (Map)l.get(i);
				String code = (String)map.get("DEMO");
				String colName = (String)map.get("COLUMN_NAME");
				String colByName = (String)map.get("COLUMN_BYNAME");
				String queryCodeSql = "select f.jcsjfx_dm, f.jcsjfx_mc from gz_dm_jcdm_fx f, gz_dm_jcdm t where t.jc_dm_dm='"+code+"' and f.jc_dm_id=t.jc_dm_id";
				List codeList = operation.select(queryCodeSql);
				//ѭ�������
				for(int j = 0; j < list.size(); j++){
					Map m = (Map)list.get(j);
					//�����ֶ�Ӣ���������ƥ��
					if(m.keySet().contains(colName)){
						m.put(colName, getCode(codeList, (String)m.get(colName), displayType));
					}else if(m.keySet().contains(colByName)){
						m.put(colByName, getCode(codeList, (String)m.get(colByName), displayType));
					}
					
				}
			}
		} catch (DBException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * �����ֶ�Ӣ����
	 * @param mixedColmnsStr
	 * @return
	 */
	public static String[] getColumnNames(String mixedColmnsStr){
		String[] mixedColmnsStrs = mixedColmnsStr.split(",");
		String[] columnEnName = null;
		if(mixedColmnsStrs != null && mixedColmnsStrs.length > 0){
			columnEnName = new String[mixedColmnsStrs.length];
			for(int i = 0; i < mixedColmnsStrs.length; i++){
				String str = mixedColmnsStrs[i];
				String name = str.substring(str.lastIndexOf(".") + 1);
				if(name.trim().equals("")){
					name = str.substring(str.indexOf(".") + 1, str.lastIndexOf("."));
				}
				columnEnName[i] = name;
			}
		}
		return columnEnName;
	}
	
	public static void main(String[] args)
	{
		String str = "aa.aa.";
		System.out.println(str.substring(str.indexOf(".") + 1, str.lastIndexOf(".")));
	}
	
	/**
	 * ���ر���.�ֶ�������ʽ��
	 * @param mixedColmnsStr
	 * @return
	 */
	public static String getTblColStr(String mixedColmnsStr){
		String[] mixedColmnsStrs = mixedColmnsStr.split(",");
		StringBuffer str = new StringBuffer();
		if(mixedColmnsStrs != null && mixedColmnsStrs.length > 0){
			for(int i = 0; i < mixedColmnsStrs.length; i++){
				str.append(mixedColmnsStrs[i].substring(0, mixedColmnsStrs[i].lastIndexOf(".")));
				if(i == mixedColmnsStrs.length - 1){
					continue;
				}
				str.append(",");
			}
		}
		return str.toString();
	}
	
	/**
	 * �Ӵ��������Ѱ��ƥ���ֵ
	 * @param codeList
	 * @param value
	 * @return
	 */
	private static String getCode(List codeList, String value, String displayType){
		try{
			for(int i = 0; i < codeList.size(); i++){
				Map map = (Map)codeList.get(i); 
				String code = (String)map.get("JCSJFX_DM");
				if(value != null && code.trim().equals(value.trim())){
					if(displayType.equalsIgnoreCase(NAME)){
						return (String)map.get("JCSJFX_MC");
					}else if(displayType.equalsIgnoreCase(MIX)){
						return code + " " + map.get("JCSJFX_MC");
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return value;
	}
	
	/**
	 * תΪ����IN��׼���ַ���
	 * @param mixCol
	 * @return
	 */
	private static String parseMixColumns(String mixCol){
		if(mixCol == null || mixCol.trim().length() == 0){
			return null;
		}
		String columns = getTblColStr(mixCol);
		columns = columns.replaceAll(",", "','");
		
		return "'"+columns+"'";
	}
}
