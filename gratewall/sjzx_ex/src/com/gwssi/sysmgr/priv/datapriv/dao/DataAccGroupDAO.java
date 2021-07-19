/**
 * 
 */
package com.gwssi.sysmgr.priv.datapriv.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.gwssi.sysmgr.priv.datapriv.exception.DataAccGroupNotFoundException;


/**
 * @author ����
 * ����Ȩ����־���
 */
public class DataAccGroupDAO {
	private static final Logger log = Logger.getLogger(DBase.class);
	private static DataAccGroupDAO inst = new DataAccGroupDAO(); 		// Ψһʵ��
	private Map groupMap = new HashMap();
	
	protected DataAccGroupDAO(){
	}
	
	static public DataAccGroupDAO getInst(){
		return inst;
	}
	
	/**
	 * ��ջ���
	 **/
	public void init(){
		groupMap.clear();
	}
	/**
	 * ��ȡ����Ȩ�������������Ȩ��
	 * @param groupId ����Ȩ����id
	 * @return ����Ȩ���б�
	 * @throws SQLException
	 */
	public List getGroupItem(String groupId) throws SQLException{
		String sql = "select DATAACCGRPID,OBJECTID,DATAACCID from " +
				"DATAACCGROUPITEM WHERE DATAACCGRPID = " + groupId ;
		return DBase.getInst().query(sql);
	}
	
	/**
	 * ��ȡ����Ȩ���������ĳ������Ȩ��
	 * @param groupId ����Ȩ����id
	 * @param objectId ����Ȩ�����
	 * @return ����Ȩ���б�
	 * @throws SQLException
	 */
	public List getGroupItem(String groupId,String objectId) throws SQLException{
		String sql = "select DATAACCGRPID,OBJECTID,DATAACCID from " +
				"DATAACCGROUPITEM WHERE DATAACCGRPID = " 
			+ groupId + " and OBJECTID = " + objectId;
		return DBase.getInst().query(sql);
	}
	
	/**
	 * ��ȡ�������Ȩ������ĳ������Ȩ������²�ͬ���ʹ����°���������Ȩ�޸���
	 * @param dataAccGroupIds ����Ȩ����ID����
	 * @param dataObjId ����Ȩ�����
	 * @param rule ���ʹ���
	 * @return ����Ȩ�޸���
	 * @throws SQLException
	 */
	public int getGroupsItemCount(Collection dataAccGroupIds,String dataObjId,String rule) throws SQLException{
		StringBuffer groupIds = new StringBuffer();
		Iterator iter = dataAccGroupIds.iterator();
		while(iter.hasNext()){
			groupIds.append(iter.next().toString());
			groupIds.append(',');
		}
		if(groupIds.length() > 0) groupIds.deleteCharAt(groupIds.length() - 1);
		else return 0;
		String sql = "select count(*) as cnt from DATAACCGROUP A INNER JOIN " +
				"DATAACCGROUPITEM B ON A.DATAACCGRPID = B.DATAACCGRPID where " +
				"B.OBJECTID = " + dataObjId + " and A.DATAACCGRPID in (" + groupIds +	")" +
				"AND A.DATAACCRULE = '" + rule + "'";
		return DBase.getInst().getCount(sql);
	}
	
	/**
	 * ��ȡ�������Ȩ������ĳ������Ȩ������²�ͬ���ʹ����°���������Ȩ��
	 * @param dataAccGroupIds ����Ȩ����ID����
	 * @param dataObjId ����Ȩ�����
	 * @param rule ���ʹ���
	 * @return ����Ȩ��
	 * @throws SQLException
	 */
	public List getGroupsItem(Collection dataAccGroupIds,String dataObjId,String rule ) throws SQLException{
		StringBuffer groupIds = new StringBuffer();
		Iterator iter = dataAccGroupIds.iterator();
		while(iter.hasNext()){
			groupIds.append(iter.next().toString());
			groupIds.append(',');
		}
		if(groupIds.length() > 0) groupIds.deleteCharAt(groupIds.length() - 1);
		else return new ArrayList();
		String sql;
		if(rule.equals("2")){
			sql = "select distinct B.DATAACCID from DATAACCGROUP A INNER JOIN " +
					"DATAACCGROUPITEM B ON A.DATAACCGRPID = B.DATAACCGRPID where " +
					"B.OBJECTID = " + dataObjId + " and A.DATAACCGRPID in (" + groupIds +	") " +
					"AND A.DATAACCRULE = '2' and B.DATAACCID not in(select B.DATAACCID from " +
					"DATAACCGROUP A INNER JOIN DATAACCGROUPITEM B ON A.DATAACCGRPID = " +
					"B.DATAACCGRPID where B.OBJECTID = " + dataObjId + " and A.DATAACCGRPID " +
					"in (" + groupIds +	") AND A.DATAACCRULE = '1')";
		}
		else{
			sql = "select distinct B.DATAACCID from DATAACCGROUP A INNER JOIN " +
			"DATAACCGROUPITEM B ON A.DATAACCGRPID = B.DATAACCGRPID where " +
			"B.OBJECTID = " + dataObjId + " and A.DATAACCGRPID in (" + groupIds +	")" +
			" AND A.DATAACCRULE = '1'";
		}
		return DBase.getInst().getFieldList(sql);
	}
	
	/**
	 * ��ȡ����Ȩ������Ϣ
	 * @param groupId ����Ȩ����id
	 * @return ����Ȩ������Ϣ
	 * @throws SQLException
	 * @throws DataAccGroupNotFoundException
	 */
	public Map getGroupInfo(String groupId) throws SQLException, DataAccGroupNotFoundException{
		if(groupMap.containsKey(groupId)){
			Map group = (Map) groupMap.get(groupId);
			log.debug("�Ѵӻ�������������Ȩ�޷�����Ϣ��");
			return group;
		}

		// �����ݿ��ȡ����Ȩ�޷�����Ϣ
		String sql = "select DATAACCGRPID,DATAACCGRPNAME,DATAACCRULE," +
				"DATAACCTYPE from DATAACCGROUP WHERE DATAACCGRPID = " + groupId ;
		List groups = DBase.getInst().query(sql);
		if(groups.size() > 0){
			groupMap.put(groupId, groups.get(0));
			return (Map) groups.get(0);
		}
		
		log.debug("IDΪ[" + groupId + "]������Ȩ�޷��鲻����");
		throw new DataAccGroupNotFoundException();
	}
	/**
	 * @param args
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws DataAccGroupNotFoundException 
	 */
	public static void main(String[] args){
		try {
			DBase.getInst().open();
			DataAccGroupDAO.getInst().getGroupInfo("1");
			DataAccGroupDAO.getInst().getGroupItem("1");
			DBase.getInst().close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataAccGroupNotFoundException e) {
			e.printStackTrace();
		}
	}

}
