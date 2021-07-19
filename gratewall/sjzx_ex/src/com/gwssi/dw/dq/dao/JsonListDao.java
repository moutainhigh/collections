package com.gwssi.dw.dq.dao;

import java.util.List;

import com.genersoft.frame.base.database.DBException;
import com.genersoft.frame.base.database.DbUtils;

/**
 * 后台数据查询
 *@author zhengziguo
 */

public class JsonListDao
{
	private String		connType;

	public JsonListDao(String connType)
	{
		this.connType = connType;
	}

	/**
	 * 将SUBJECT_ID，SUBJECT_PID，SUBJECT_NAME的查询结果装入List中
	 * @param connType 数据库连接方式
	 * @throws DBException
	 * @return dataList
	 */
	public List subjectList() throws DBException
	{
		List dataList;
		String sql = " select sub.SUBJECT_ID, sub.SUBJECT_PID,sub.SUBJECT_NAME "
				+ " from DQ_SUBJECT sub order by sub.SUBJECT_NAME asc";
		dataList = DbUtils.select(sql, this.connType);
		return dataList;
	}

	/**
	 * 将TABLE_NO,TABLE_NAME,TABLE_NAME_CN的查询结果装入List中
	 * @param connType 数据库连接方式
	 * @throws DBException
	 * @return dataList
	 */
	public List tableList(String subjectId) throws DBException
	{
		List dataList;
		String sql = " select TABLE_NO,TABLE_NAME,TABLE_NAME_CN "
				+ " from SYS_SYSTEM_SEMANTIC A,SYS_TABLE_SEMANTIC B,DQ_SUBJECT_SYS c "
				+ " where b.SYS_ID=a.SYS_ID and c.SYS_ID=b.SYS_ID and c.SUBJECT_ID='"
				+ subjectId + "' order by b.TABLE_NAME asc";
		dataList = DbUtils.select(sql, this.connType);
		return dataList;
	}
    
	/**
	 * @param tableNo
	 * @return dataList
	 * @throws DBException
	 */
	public List columnList(String tableNo) throws DBException
	{
		List dataList;
		String sql = " select COLUMN_NO,COLUMN_NAME,COLUMN_NAME_CN "
				+ " from SYS_COLUMN_SEMANTIC " + " where TABLE_NO='" + tableNo
				+ "' order by COLUMN_NAME_CN asc";
		dataList = DbUtils.select(sql, this.connType);
		return dataList;
	}
	
	/**
	 * @param tableName
	 * @param subjectId
	 * @return dataList
	 * @throws DBException
	 */
	public List queryColumn(String tableName,String subjectId)throws DBException{
		List dataList;
		StringBuffer sql = new StringBuffer();
		sql.append("select COLUMN_NO,COLUMN_NAME,COLUMN_NAME_CN ");
		sql.append(" from sys_column_semantic");
		sql.append(" where table_no in ");
		sql.append("(select a.table_no from sys_table_semantic a ");
		sql.append("where a.table_name='");
		sql.append(tableName);
		sql.append("') order by COLUMN_NAME_CN asc");
		dataList = DbUtils.select(sql.toString(), this.connType);
		return dataList;
	}
    /**
     * @param entityId
     * @return
     * @throws DBException
     */
    public List getTable(String entityId) throws DBException{
    	List dataList;
    	StringBuffer sql = new StringBuffer();
    	sql.append("select TABLE_NAME_CN,TABLE_NAME from SYS_TABLE_SEMANTIC where TABLE_NAME in(");
    	sql.append("select ENTITY_TABLE as TABLE_NAME from DQ_ENTITY where ENTITY_ID in");
    	sql.append("(select FOREIGN_ENTITY_ID from DQ_ENTITY_FOREIGN where ENTITY_ID='");
    	sql.append(entityId);
    	sql.append("')");
    	sql.append("union");
    	sql.append("(select ENTITY_TABLE from DQ_ENTITY where ENTITY_ID ='");
    	sql.append(entityId);
    	sql.append("')");
    	sql.append("union");
    	sql.append("(select  SUBJOIN_TABLE from DQ_SUBJOIN where ENTITY_ID='");
    	sql.append(entityId);
    	sql.append("')");
    	sql.append(")");
    	sql.append("order by TABLE_NAME_CN asc");
    	dataList = DbUtils.select(sql.toString(), this.connType);
		return dataList;
    }
	/**
	 * @param subjectId
	 * @return dataList
	 * @throws DBException
	 */
	public List entityList(String subjectId) throws DBException
	{
		List dataList;
		String sql = " select entity.ENTITY_ID, entity.ENTITY_TABLE, entity.ENTITY_NAME "
				+ " from DQ_ENTITY entity "
				+ " where entity.SUBJECT_ID='"
				+ subjectId + "' order by entity.ENTITY_NAME asc";
		dataList = DbUtils.select(sql, this.connType);
		return dataList;
	}

	/**
	 * @param entityId
	 * @return dataList
	 * @throws DBException
	 */
	public List targetList(String entityId) throws DBException
	{
		List dataList;
		String sql = "select tg.TARGET_ID,tg.TARGET_NAME "
				+ "from DQ_TARGET tg " + "where tg.ENTITY_ID='" + entityId
				+ "' order by tg.TARGET_NAME asc";
		dataList = DbUtils.select(sql, this.connType);
		return dataList;
	}
	
	/**
	 * target名称的模糊查询,用于快速定位指标树
	 * @param name
	 * @return
	 * @throws DBException
	 */
	public List queryTargetList(String name) throws DBException {
		List dataList;
		StringBuffer sql = new StringBuffer();
		sql.append("select s.subject_id||'/'||e.entity_id||'/'||t.target_id as path");
		sql.append(",t.target_name||'-'||e.entity_name as name ");
		sql.append("from dq_subject s,dq_entity e,dq_target t ");
		sql.append("where s.subject_id=e.subject_id and e.entity_id=t.entity_id ");
		sql.append("and t.target_name like '%");
		sql.append(name);
		sql.append("%' order by instr(t.target_name,'");
		sql.append(name);
		sql.append("'),t.target_name ");
		dataList = DbUtils.select(sql.toString(), this.connType);
		return dataList;
	}

	/**
	 * @param entityId
	 * @return dataList
	 * @throws DBException
	 */
	public List groupList(String entityId, String groupType) throws DBException
	{
		List dataList;
		String sql = "";
		if (groupType.equals("0") || groupType.equals("1")) {
			sql = "select gr.GROUP_ID,gr.GROUP_NAME " + "from DQ_GROUP gr "
				+ "where gr.ENTITY_ID='" + entityId + "' and gr.ISACTIVE='"+ groupType +"' order by gr.GROUP_NAME asc";
		}
		else {
			sql = "select gr.GROUP_ID,gr.GROUP_NAME " + "from DQ_GROUP gr "
				+ "where gr.ENTITY_ID='" + entityId + "' order by gr.ISACTIVE,gr.GROUP_NAME asc";
		}
		dataList = DbUtils.select(sql, this.connType);
		return dataList;
	}
	
	/**
	 * group名称的模糊查询,用于快速定位分组树
	 * @param name
	 * @return
	 * @throws DBException
	 */
	public List queryGroupList(String name) throws DBException {
		List dataList;
		StringBuffer sql = new StringBuffer();
		sql.append("select s.subject_id||'/'||e.entity_id||'/'||t.group_id as path");
		sql.append(",t.group_name||'-'||e.entity_name as name ");
		sql.append("from dq_subject s,dq_entity e,dq_group t ");
		sql.append("where s.subject_id=e.subject_id and e.entity_id=t.entity_id ");
		sql.append("and t.isactive='1' and t.group_name like '%");
		sql.append(name);
		sql.append("%' order by instr(t.group_name,'");
		sql.append(name);
		sql.append("'),t.group_name ");
		dataList = DbUtils.select(sql.toString(), this.connType);
		return dataList;
	}
	
	/**
	 * @param entityId
	 * @return
	 * @throws DBException
	 */
	public List foreignEntityList(String entityId) throws DBException
	{
		
		List dataList;
		String sql= "select FOREIGN_ENTITY_ID,CONDITION "
			      +"from DQ_ENTITY_FOREIGN "
			      +"where ENTITY_ID='"+entityId+"'";
		dataList = DbUtils.select(sql, this.connType);
		return dataList;
	}
	
	public List globalForeignEntityList(String subjectId) throws DBException {
		List dataList;
		String sql = " select entity.ENTITY_ID, entity.ENTITY_TABLE, entity.ENTITY_NAME "
				+ " from DQ_ENTITY entity "
				+ " where entity.ISCOMMONFOREIGN='1' and entity.SUBJECT_ID<>'"
				+ subjectId + "' order by entity.ENTITY_NAME asc";
		dataList = DbUtils.select(sql, this.connType);
		return dataList;
	}
	
	/**
	 * @param entityId
	 * @return
	 * @throws DBException
	 */
	public List subjoinEntityList(String entityId) throws DBException
	{
		List dataList;
		String sql= "select SUBJOIN_TABLE,CONDITION "
			      +"from DQ_SUBJOIN "
			      +"where ENTITY_ID='"+entityId+"' order by SUBJOIN_TABLE asc";
		dataList = DbUtils.select(sql, this.connType);
		return dataList;
	}
	
	/**
	 * @return
	 * @throws DBException
	 */
	public List allParamList() throws DBException {
		String sql = "select * from DQ_PARAMETER order by PARAMETER_NAME asc";
		return DbUtils.select(sql, this.connType);
	}
	
	/**
	 * @param groupId
	 * @return
	 * @throws DBException
	 */
	public List getGroupTempList(String groupId) throws DBException {
		String sql = "select GROUP_TEMP_ID,GROUP_TEMP_NAME from DQ_GROUP_TEMP where GROUP_ID='"
		    +groupId
		    +"' ";;
		return DbUtils.select(sql, this.connType);
	}
	
//	/**
//	 * @param EntityId
//	 * @param subjectId
//	 * @return
//	 * @throws DBException
//	 */
//	public List getColumn(String EntityId,String subjectId) throws DBException{
//		StringBuffer sql = new StringBuffer();
//		sql.append("select a.ENTITY_NAME as TABLE_NAME_CN,a.ENTITY_TABLE as TABLE_NAME,b.COLUMN_NAME,b.COLUMN_NAME_CN");
//		sql.append(" from DQ_ENTITY a,sys_column_semantic b where TABLE_NO in");
//		sql.append("(select a.table_no "+" from sys_table_semantic a,dq_subject_sys b ");
//		sql.append("where a.table_name in");
//		sql.append("(select ENTITY_TABLE from DQ_ENTITY where ENTITY_ID='");
//		sql.append(EntityId);
//		sql.append("')");
//		sql.append("and  a.sys_id=b.SYS_ID and b.subject_id='");
//		sql.append(subjectId);
//		sql.append("')");
//		System.out.println(sql);
//		List dataList=DbUtils.select(sql.toString(), conn);
//		return dataList;
//		
//	}
//	/**
//	 * @param EntityId
//	 * @return
//	 * @throws DBException
//	 */
//	public List getTable(String EntityId) throws DBException{
//		List list;
//		String sql="select ENTITY_TABLE as TABLE_NAME from DQ_ENTITY where ENTITY_ID='"
//			       +EntityId
//			       +"'";
//		list = DbUtils.select(sql, conn);
//		return list;
//		
//		
//	}
//	/**
//	 * 重构
//	 * @param EntityId
//	 * @param subjectId
//	 * @return
//	 * @throws DBException
//	 */
//	public List column(String EntityId,String subjectId) throws DBException{
//		List list1 = getTable(EntityId);
//	    List dataList = getColumn(EntityId,subjectId);
//	    List list = new ArrayList();
//	   
//	    for(int i=0;i<dataList.size();i++)
//	    {   Map json =new HashMap();
//			Map map = (Map)(dataList.get(i));
//			for(int k=0;k<list1.size();k++){
//				 Map json1 =new HashMap();
//				 Map map1 = (Map)(list1.get(k));
//				 json1.put("Table_Name", (String)map1.get("TABLE_NAME"));
//				 json1.put("Column_Name", (String)map.get("COLUMN_NAME"));
//				 json1.put("Colum_Name_CN",(String)map.get("COLUMN_NAME"));
//				 list.add(json1);
//			}
//		    json.put("Table_Name", (String)map.get("TABLE_NAME"));
//			json.put("Column_Name", (String)map.get("COLUMN_NAME"));
//			json.put("Colum_Name_CN","&nbsp;"+"&nbsp;"+(String)map.get("COLUMN_NAME_CN"));
//		    list.add(json);
//		}
//	   return list;
//	}
}
