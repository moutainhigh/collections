/**
 * 
 */
package com.gwssi.sysmgr.priv.datapriv.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.gwssi.sysmgr.priv.datapriv.exception.DataObjNotFoundException;


/**
 * @author ����
 *
 */
public class DataObjDAO {
	private static final Logger log = Logger.getLogger(DBase.class);
	private static DataObjDAO inst = new DataObjDAO(); 		// Ψһʵ��
	private List dataObjList = null;
	
	protected DataObjDAO(){
	}

	/**
	 * ��ջ���
	 **/
	public void init(){
		if(dataObjList != null){
			dataObjList.clear();
			dataObjList = null;
		}
	}
	
	static public DataObjDAO getInst(){
		return inst;
	}
	
	public String getDataObjId(String dataObj) throws SQLException, DataObjNotFoundException{
		if(dataObjList == null) 
			dataObjList = DBase.getInst().query("select OBJECTID,OBJECTSOURCE from DATAOBJECT");
		for(int i = 0 ; i < dataObjList.size(); i++){
			Map obj = (Map) dataObjList.get(i);
			if(obj.get("OBJECTSOURCE").toString().equals(dataObj)){
				return obj.get("OBJECTID").toString();
			}
		}
		log.error("���ݶ���[" + dataObj + "]������");
		throw new DataObjNotFoundException();
	}
	
	public List getDataObjItem(String table,String nameField,String idField,String codeField,String parentField, String parentCode, String sort) throws SQLException{
		StringBuffer sql = new StringBuffer("select ")
			.append("a.").append(nameField).append(" as name,a.").append(idField)
			.append(" as id,a.").append(codeField).append(" as code,");
		
		if(parentField != null && parentField.length() > 0){
			sql.append("(select count(*) from ").append(table)
				.append(" b where a.").append(idField).append(" = b.")
				.append(parentField).append(") as branchNum");
		}
		else{
			sql.append("0 as branchNum");
		}
		sql.append(" from ").append(table).append(" a where ");
		
		if(table.equals("XT_ZZJG_JG")){
			sql.append(" a.sfyx='0' ");
		}else{
			sql.append(" 1=1 ");
		}
		if(parentField != null && parentField.length() > 0){
			sql.append(" and (").append(parentField).append(" = '")
				.append(parentCode).append("'");
			if(parentCode.length() == 0){
				sql.append(" or ").append(parentField).append(" is null");
			}
			sql.append(")");
		}
		sql.append(" order by ").append(sort);
		return DBase.getInst().query(sql.toString());
		
	}

	public List getDataObjItemById(String table,String nameField,String idField,String codeField,String parentField, String id) throws SQLException{
		StringBuffer sql = new StringBuffer("select ")
			.append("a.").append(nameField).append(" as name,a.").append(idField)
			.append(" as id,a.").append(codeField).append(" as code,");
		
		if(parentField != null && parentField.length() > 0){
			sql.append("(select count(*) from ").append(table)
				.append(" b where a.").append(idField).append(" = b.")
				.append(parentField).append(") as branchNum");
		}
		else{
			sql.append("0 as branchNum");
		}
		sql.append(" from ").append(table).append(" a where 1=1 ");
		sql.append(" and ").append(idField).append(" = '")
				.append(id).append("'");
		
		return DBase.getInst().query(sql.toString());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
