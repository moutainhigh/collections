package com.gwssi.dw.dq.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.genersoft.frame.base.database.DBException;
import com.genersoft.frame.base.database.DbUtils;
import com.gwssi.common.util.UuidGenerator;

/**
 * �������ĺ�̨���ݲ�ѯ
 * @author zhengziguo
 * @version 1.0
 */
public class DefinitionDao
{
	Connection					conn	= null;

	private PreparedStatement	ps;

	private String				connType;

	public DefinitionDao(String connType)
	{
		super();
		this.connType = connType;
		getConnection();
	}

	public void getConnection()
	{
		try {
			if (conn == null || conn.isClosed()) {
				conn = DbUtils.getConnection(connType);
			}
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ���ָ��
	 * @param entityId
	 * @param targetName
	 * @param targetFormula
	 * @return res 
	 * @throws DBException
	 * @throws SQLException
	 */
	public boolean insertTarget(String entityId, String targetName,
			String targetFormula) throws DBException, SQLException
	{
		String uuid = UuidGenerator.getUUID();
		boolean res = false;
		String sql = "insert into dq_target(TARGET_ID,ENTITY_ID,TARGET_DM,TARGET_NAME,TARGET_FORMULA) values(?,?,?,?,?)";
		ps = conn.prepareStatement(sql);
		ps.setString(1, uuid);
		ps.setString(2, entityId);
		ps.setString(3, uuid);
		ps.setString(4, targetName);
		ps.setString(5, targetFormula);
		int n = ps.executeUpdate();
		if (n > 0)       //�ж��Ƿ����� ����һ��booleanֵ��ǰ̨���д��� ��res=true��ʱ��ִ�����
			res = true;
		return res;

	}

	/**
	 * ����ָ��
	 * @param targetId
	 * @param targetName
	 * @param targetFormula
	 * @return res
	 * @throws DBException
	 * @throws SQLException
	 */
	public boolean updateTarget(String targetId, String targetName,
			String targetFormula) throws DBException, SQLException
	{
		boolean res = false;
		String sql = "update dq_target set TARGET_NAME=?,TARGET_FORMULA=? where TARGET_ID=?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, targetName);
		ps.setString(2, targetFormula);
		ps.setString(3, targetId);
		int n = ps.executeUpdate();
		if (n > 0)
			res = true;
		return res;
	}

	/**
	 * ɾ��ָ��
	 * @param targetId
	 * @return res
	 * @throws DBException
	 * @throws SQLException
	 */
	public boolean deleteTarget(String targetId) throws DBException,
			SQLException
	{
		boolean res = false;
		String sql = "delete from dq_target where target_id=?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, targetId);
		int n = ps.executeUpdate();
		if (n > 0)
			res = true;
		return res;
	}

	/**
	 * ��ѯָ��
	 * @param id
	 * @return
	 * @throws DBException
	 */
	public List queryTarget(String id) throws DBException
	{
		StringBuffer sql = new StringBuffer(); //���SQL�Ƚϸ��� ��StringBuffer�������ִ��Ч��
		sql.append("select tar.TARGET_NAME as targetName,tar.TARGET_FORMULA as targetFormula ");
		sql.append("from DQ_TARGET tar ");
		sql.append("where tar.TARGET_ID='");
		sql.append(id);
		sql.append("' ");
		return DbUtils.select(sql.toString(), conn);
	}

	/**
	 * �������
	 * @param tableName
	 * @param condition
	 * @return
	 */
	public String checkTargetCondition(String tableName, String condition)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("explain plan for (");
		sql.append("select ");
		sql.append(condition);
		sql.append(" from ");
		sql.append(tableName);
		sql.append(")");
		String msg = "true";
		try {
			DbUtils.execute(sql.toString(), conn);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			msg = e.getMessage();            //��ʽ���쳣
			msg = msg.replaceAll(" +", " ");
			msg = msg.replaceAll("\t+", "");
			msg = msg.replaceAll("\n+", "");
		}
		return msg;
	}

	/**
	 * ��ӷ���
	 * @param map
	 * @param res
	 * @param groupId
	 * @return true
	 * @throws DBException
	 * @throws SQLException
	 */
	public boolean insertGroup(Map map,boolean res,String groupId) throws DBException, SQLException
	{
		conn.setAutoCommit(false);    //�ر��Զ��ύ�Ĺ���
		String groupUuid = UuidGenerator.getUUID();
		String inputType = (String) map.get("inputType"); //map��ҵ���ݹ�����json2Map
		String entityTable = checkNull((String) map.get("entityTable"));
		String field = checkNull((String) map.get("field"));
		String dataType = checkNull((String) map.get("dataType"));
		String maxLength = checkNull((String) map.get("maxlength"));
		String name = (String) map.get("name");
        String entityId = checkNull((String) map.get("entityId"));
		String isCustom = checkNull((String) map.get("isCustom"));
		String sql = checkNull((String) map.get("sql"));
		String isActive = checkNull((String) map.get("isActive"));
		if(res){   //��booleanֵres �����ж�ִ��˳��
			StringBuffer sql3 = new StringBuffer();
			sql3.append("insert into DQ_GROUP");      //��ӷ���
			sql3.append("(GROUP_ID,ENTITY_ID,GROUP_NAME,INPUTTYPE,ISCUSTOM,MAXLENGTH,DATATYPE,FIELD,GROUP_SQL,ISACTIVE) ");
			sql3.append("values (?,?,?,?,?,?,?,?,?,?)");
			ps = conn.prepareStatement(sql3.toString());
			ps.setString(1, groupUuid);
			ps.setString(2, entityId);
			ps.setString(3, name);
			ps.setString(4, inputType);
			ps.setString(5, isCustom);
			ps.setString(6, maxLength);
			ps.setString(7, dataType);
			if (field.equals(""))
				ps.setString(8, "");
			else
				ps.setString(8, entityTable + "." + field);
			ps.setString(9, sql);
			ps.setString(10, isActive);
			ps.executeUpdate();
			ps.close();
		}
		try{          //res=falseΪUpdate��׼�� ��װdata����
			if (inputType.equals("select")) {   
				List paramsList = (List) map.get("params");
				List itemsList = (List) map.get("items");

				StringBuffer sql1 = new StringBuffer();
				sql1.append("insert into DQ_PARAMETER ");
				sql1.append("values (?,?,?,?,?,?)");
				PreparedStatement paramPS = conn.prepareStatement(sql1.toString());
				String sql2 = "insert into DQ_CUSTOM_PARAMETER(PARAMETER_ID,GROUP_ID) values (?,?)";
				PreparedStatement cParamPS = conn.prepareStatement(sql2);
				for (int i = 0; paramsList.size() > i; i++) {
					String uuid = UuidGenerator.getUUID();
					Map params = (Map) paramsList.get(i);
					String name1 = (String) params.get("name");
					String dataType1 = (String) params.get("dataType");
					String type = (String) params.get("type");
					if (type.equals("�������")) {
						type = "1";
					}
					else {
						type = "2";
					}
					String exp = (String) params.get("exp");
					String desp = (String) params.get("desp");
					paramPS.setString(1, uuid);
					paramPS.setString(2, name1);
					paramPS.setString(3, dataType1);
					paramPS.setString(4, type);
					paramPS.setString(5, exp);
					paramPS.setString(6, desp);
					paramPS.executeUpdate();
					cParamPS.setString(1, uuid);
					if (res)   //�ж�groupId
						cParamPS.setString(2, groupUuid);
					else
						cParamPS.setString(2, groupId);
					cParamPS.executeUpdate();
				}
				conn.commit();  //�ֶ��ύconn
				paramPS.close();
				cParamPS.close();//�ر����ӣ�������ر��ڵ����������ӵ�ʱ�����ݿ��ر�
				StringBuffer sql4 = new StringBuffer();
				sql4.append("insert into DQ_CUSTOM_ITEM");
				sql4.append("(CUSTOM_ITEM_ID,GROUP_ID,CUSTOM_ITEM_NAME,CUSTOM_ITEM_COND,CUSTOM_ITEM_OUTER) ");
				sql4.append("values (?,?,?,?,?)");
				PreparedStatement itemPS = conn.prepareStatement(sql4.toString());
				for (int i = 0; itemsList.size() > i; i++) {
					String uuid = UuidGenerator.getUUID();
					Map items = (Map) itemsList.get(i);
					String name1 = (String) items.get("name");
					String cond = (String) items.get("cond");
					String outer = (String) items.get("outer");
					itemPS.setString(1, uuid);
					if (res) 
						itemPS.setString(2, groupUuid);
					else
						itemPS.setString(2, groupId);
					itemPS.setString(3, name1);
					itemPS.setString(4, cond);
					itemPS.setString(5, outer);
					itemPS.executeUpdate();
				}
				conn.commit();
				itemPS.close();
			}
		}catch (Exception e) {
			
		}finally {
			conn.commit();
			conn.setAutoCommit(true);
		}
		return true;
	}
	/**
	 * ���ʵ��
	 * @param map
	 * @param res
	 * @param entityId
	 * @throws DBException
	 * @throws SQLException
	 */
	public void insertEntity(Map map,boolean res,String entityId) throws DBException, SQLException{
		String uuid = UuidGenerator.getUUID(); //���Uuid
		String name=(String)map.get("name");
		String table=(String)map.get("table");
		String subjectId = (String)map.get("subjectId");
		if(res){
			String sql1 ="insert into DQ_ENTITY(ENTITY_ID,SUBJECT_ID,ENTITY_NAME,ENTITY_TABLE) "
				+"values(?,?,?,?)";
			ps=conn.prepareStatement(sql1);
			ps.setString(1, uuid);
			ps.setString(2, subjectId);
			ps.setString(3, name);
			ps.setString(4, table);
			ps.executeUpdate();
			ps.close();
		}
		List foreignEntityList=(List)map.get("foreignEntity");
		List subjoinTableList=(List)map.get("subjoinTable");
		if(name != null && !name.equals("")){
			String sql ="insert into DQ_ENTITY_FOREIGN(ENTITY_ID,FOREIGN_ENTITY_ID,CONDITION) "
				+"values(?,?,?)";
			ps=conn.prepareStatement(sql);
			for(int i=0;foreignEntityList.size()>i;i++){
				Map foreignEntity = (Map)foreignEntityList.get(i);
				String id = (String)foreignEntity.get("id");
				String cond=(String)foreignEntity.get("cond");
				if (res)
					ps.setString(1, uuid);
				else
					ps.setString(1, entityId);
				ps.setString(2, id);
				ps.setString(3, cond);
				ps.executeUpdate();
				
//				ps.setString(1, id);
//				if (res)
//					ps.setString(2, uuid);
//				else
//					ps.setString(2, entityId);
//				ps.setString(3, cond);
//				ps.executeUpdate();
			}
			ps.close();
		}
		if (table !=null && !table.equals("")){
			String sql ="insert into DQ_SUBJOIN values(?,?,?)";
			ps=conn.prepareStatement(sql);
			for(int i=0;subjoinTableList.size()>i;i++){   //����subjoinTableList
				Map subjoinTable = (Map)subjoinTableList.get(i);
				String name1 = (String)subjoinTable.get("name");
				String cond=(String)subjoinTable.get("cond");
				if (res)
					ps.setString(1, uuid);
				else
					ps.setString(1, entityId);
				ps.setString(2, name1);
				ps.setString(3, cond);
				ps.executeUpdate();
			}
			ps.close();
		}
	}
	
	/**
	 * ����ʵ�� ��Ҫִ��delSubjoin()��delForeignTable()��insertEntity()Ȼ�����update
	 * @param map
	 * @throws DBException
	 * @throws SQLException
	 */
	public void updateEntity(Map map) throws DBException, SQLException {
		String entityId= (String)map.get("id");
		String subjectId= (String)map.get("subjectId");
		String name = (String)map.get("name");
		String table= (String)map.get("table");
		String entitySql = (String)map.get("sql");
		delSubjoin(entityId);
		delForeignTable(entityId);
		insertEntity(map,false,entityId);   //ִ������res=false�е�insertEntity��� 
		String sql = "update DQ_ENTITY set SUBJECT_ID=?,ENTITY_NAME=?,ENTITY_TABLE=?,ENTITY_SQL=? "
			+ " where ENTITY_ID=? ";
		ps = conn.prepareStatement(sql);
		ps.setString(1, subjectId);
		ps.setString(2, name);
		ps.setString(3, table);
		ps.setString(4, entitySql);
		ps.setString(5, entityId);
		ps.executeUpdate();
		ps.close();
	}
	
	/**
	 * ��װʵ������
	 * @param entityId
	 * @return json
	 * @throws DBException
	 */
	public Map queryEntity(String entityId) throws DBException {
		
		Map json=new HashMap();
		StringBuffer sql = new StringBuffer();
		sql.append("select ENTITY_NAME,ENTITY_TABLE ");
		sql.append("from DQ_ENTITY ");
		sql.append("where ENTITY_ID='");
		sql.append(entityId);
		sql.append("'");
		Map map = (Map)(DbUtils.select(sql.toString(), conn).get(0));
		json.put("name", (String)map.get("ENTITY_NAME"));
		json.put("table", (String)map.get("ENTITY_TABLE"));
		json.put("foreignEntity", foreignEntityList(entityId));
		json.put("subjoinTable", subjoinTableList(entityId));
		return json;
	}
	
	/**
	 * ���������ϵ��Ĳ�ѯ
	 * @param entityId
	 * @return List
	 * @throws DBException
	 */
	private List foreignEntityList(String entityId) throws DBException {
		String sql = "select FOREIGN_ENTITY_ID as id,CONDITION as cond "
			       + "from DQ_ENTITY_FOREIGN "
			       + "where ENTITY_ID='"
			       + entityId
			       + "'";
		return DbUtils.select(sql, conn);
	}
	/**
	 * ʵ��ϸ�ڱ�������װ
	 * @param entityId
	 * @return
	 * @throws DBException
	 */
	public Map detailsList(String entityId) throws DBException {
		Map jsonMap = new HashMap();
		String sql="select DETAILS_ID,DETAILS_FIELD,DETAILS_NAME from DQ_ENTITY_DETAILS where ENTITY_ID='"
			      +entityId
			      + "'";
		List list = DbUtils.select(sql, conn);
	    jsonMap.put("totalCount", list.size() + "");
	    jsonMap.put("records", list);
		return jsonMap;
	}
	
	/**
	 * ����ʵ����ϸ
	 * @param map
	 * @throws SQLException
	 */
	public void addDetail(Map map) throws SQLException {
		String sql = "insert into DQ_ENTITY_DETAILS values(?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, UuidGenerator.getUUID());
		ps.setString(2, checkNull((String)map.get("entityId")));
		ps.setString(3, checkNull((String)map.get("detailsField")));
		ps.setString(4, checkNull((String)map.get("detailsName")));
		try {
			ps.setInt(5, Integer.parseInt(checkNull((String)map.get("detailsWidth"))));
		} catch (NumberFormatException e) {
			ps.setInt(5, 0);
		}
		ps.setString(6, checkNull((String)map.get("flagSort")));
		ps.setString(7, checkNull((String)map.get("detailsType")));
		ps.setString(8, checkNull((String)map.get("isFilter")));
		ps.setString(9, checkNull((String)map.get("detailsHref")));
		ps.setString(10, checkNull((String)map.get("detailsParams")));
		try {
			ps.setInt(11, Integer.parseInt(checkNull((String)map.get("num"))));
		} catch (NumberFormatException e) {
			ps.setInt(11, 0);
		}
		ps.execute();
		ps.close();
	}
	
	/**
	 * ����ʵ����ϸ��
	 * @param map
	 * @throws SQLException
	 */
	public void updateDetail(Map map) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("update DQ_ENTITY_DETAILS set DETAILS_FIELD=?,");
		sql.append("DETAILS_NAME=?,DETAILS_WIDTH=?,FLAG_SORT=?,");
		sql.append("DETAILS_TYPE=?,ISFILTER=?,DETAILS_HREF=?,");
		sql.append("DETAILS_PARAMS=?,DETAILS_NUM=? ");
		sql.append("where DETAILS_ID=?");
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setString(1, checkNull((String)map.get("detailsField")));
		ps.setString(2, checkNull((String)map.get("detailsName")));
		try {
			ps.setInt(3, Integer.parseInt(checkNull((String)map.get("detailsWidth"))));
		} catch (NumberFormatException e) {
			ps.setInt(3, 0);
		}
		ps.setString(4, checkNull((String)map.get("flagSort")));
		ps.setString(5, checkNull((String)map.get("detailsType")));
		ps.setString(6, checkNull((String)map.get("isFilter")));
		ps.setString(7, checkNull((String)map.get("detailsHref")));
		ps.setString(8, checkNull((String)map.get("detailsParams")));
		try {
			ps.setInt(9, Integer.parseInt(checkNull((String)map.get("num"))));
		} catch (NumberFormatException e) {
			ps.setInt(9, 0);
		}
		ps.setString(10, checkNull((String)map.get("detailsId")));
		ps.execute();
		ps.close();
	}
	
	/**
	 * ���ʵ����ϸ��list
	 * @param detailsId
	 * @return
	 * @throws DBException
	 */
	public Map getDetails(String detailsId) throws DBException {
		String sql = "select * from DQ_ENTITY_DETAILS where DETAILS_ID='" +
				detailsId +
				"' ";
		List list = DbUtils.select(sql, conn);
		return (Map)list.get(0);
	}
	
	/**
	 * ɾ��ʵ����ϸ��
	 * @param detailId
	 * @throws DBException
	 */
	public void delDetails(String detailId) throws DBException {
		String sql = "delete from DQ_ENTITY_DETAILS where DETAILS_ID = '" +
				detailId +
				"' ";
		DbUtils.execute(sql, conn);
	}
	
	/**
	 * ʵ�����������ѯ
	 * @param entityId
	 * @return List
	 * @throws DBException
	 */
	private List subjoinTableList(String entityId) throws DBException{
	
		String sql = "select SUBJOIN_TABLE as name,CONDITION as cond "
			       + "from DQ_SUBJOIN "
			       + "where ENTITY_ID='"
			       + entityId
			       + "'";
		return DbUtils.select(sql, conn);
	}
	
	/**
	 * ����������װ
	 * @param id
	 * @return jsonMap
	 * @throws DBException
	 */
	public Map queryGroup(String id) throws DBException {
		Map jsonMap = new HashMap();
		StringBuffer sql = new StringBuffer();
		sql.append("select * ");
		sql.append("from DQ_GROUP ");
		sql.append("where GROUP_ID='");
		sql.append(id);
		sql.append("' ");
		Map map = (Map)(DbUtils.select(sql.toString(), conn).get(0));
		jsonMap.put("id", (String)map.get("GROUP_ID"));
		jsonMap.put("name", (String)map.get("GROUP_NAME"));
		String inputType = (String)map.get("INPUTTYPE");
		String isActive = (String)map.get("ISACTIVE");
		jsonMap.put("inputType", inputType);
		jsonMap.put("isActive", isActive);
		if (inputType.equals("input")) {
			String field = (String)map.get("FIELD");
			jsonMap.put("field", field.split("[.]")[1]);
			jsonMap.put("dataType", (String)map.get("DATATYPE"));
			jsonMap.put("maxlength", (String)map.get("MAXLENGTH"));
		}
		else if (inputType.equals("select")) {
			String isCustom = (String)map.get("ISCUSTOM");
			jsonMap.put("isCustom", isCustom);
			if (isCustom.equals("0")) {
				jsonMap.put("sql", checkNull((String)map.get("GROUP_SQL")));
			}
			else if (isCustom.equals("1")) {
				jsonMap.put("params", queryParams(id));
				jsonMap.put("items", queryItems(id));
			}
		}
		return jsonMap;
	}
	
	/**
	 * ���·���  ��ִ��ɾ��������ٸ���
	 * @param map
	 * @throws SQLException
	 * @throws DBException
	 */
	public void updateGroup(Map map) throws SQLException, DBException {
		
		String groupId = (String)map.get("id");
		String inputType = (String) map.get("inputType");
		String entityTable = checkNull((String) map.get("entityTable"));
		String field = checkNull((String) map.get("field"));
		String dataType = checkNull((String) map.get("dataType"));
		String maxLength = checkNull((String) map.get("maxlength"));
		String groupName = (String) map.get("name");
        String entityId = checkNull((String) map.get("entityId"));
		String isCustom = checkNull((String) map.get("isCustom"));
		String groupSql = checkNull((String) map.get("sql"));
		String isActive = checkNull((String) map.get("isActive"));
		delItems(groupId);
		delParams(groupId);
		insertGroup(map,false,groupId);
		String sql = "update DQ_GROUP set ENTITY_ID=?,GROUP_NAME=?,INPUTTYPE=?,ISCUSTOM=?,MAXLENGTH=?,DATATYPE=?,FIELD=?,GROUP_SQL=?,ISACTIVE=?"
			       + "where GROUP_ID=? ";
		ps = conn.prepareStatement(sql);
		ps.setString(1, entityId);
		ps.setString(2, groupName);
		ps.setString(3, inputType);
		ps.setString(4, isCustom);
		ps.setString(5, maxLength);
		ps.setString(6, dataType);
		if (field.equals(""))
			ps.setString(7, "");
		else
			ps.setString(7, entityTable + "." + field);
		ps.setString(8, groupSql);
		ps.setString(9, isActive);
		ps.setString(10, groupId);
		ps.executeUpdate();
		ps.close();
	}
	/**
	 * ɾ������ѡ��ģ��
	 * @param groupId
	 * @throws DBException
	 * @throws SQLException
	 */
	private void delGroupItems(String groupId) throws DBException, SQLException{
		String sql1="select GROUP_TEMP_ID from DQ_GROUP_TEMP "   //�Ȳ����GROUP_TEMP_ID
			       +"where GROUP_ID='"+groupId+"'";
		List dataList = DbUtils.select(sql1, conn);
		String sql3="delete from DQ_GROUP_TEMP_ITEMS where GROUP_TEMP_ID=?";
		ps=conn.prepareStatement(sql3);
		for(int i=0;dataList.size()>i;i++)
		{
			Map map = (Map)dataList.get(i);  //����������List�л�ȡGROUP_TEMP_ID ����ɾ����
			String tempId = (String)map.get("GROUP_TEMP_ID");
			ps.setString(1, tempId);
			ps.executeUpdate();
		}
		ps.close();
		String sql2="delete from DQ_GROUP_TEMP where GROUP_ID='"+groupId+"'";
		Statement st = conn.createStatement();
		st.executeUpdate(sql2);
		st.close();
	}
	/**
	 * ɾ��������������ģ��
	 * @param groupId
	 * @throws SQLException
	 */
	private void delInput(String groupId) throws SQLException{
		String sql = "delete from DQ_INPUT_TEMP "+"where GROUP_ID='"
		           +groupId+"'";
		Statement st = conn.createStatement();
		st.executeUpdate(sql);
	}
	/**
	 * ɾ������
	 * @param groupId
	 * @throws SQLException
	 * @throws DBException
	 */
	public void delGroup(String groupId) throws SQLException, DBException {
		delItems(groupId);      
		delParams(groupId);
		delInput(groupId);
		delGroupItems(groupId);  
		String sql = "delete from DQ_GROUP where GROUP_ID='" +   //�Ȱ�һ��������йص������һ��ɾ�� ��ɾ��������е�����
			groupId +
			"' ";
		Statement st = conn.createStatement();
		st.executeUpdate(sql);
		st.close();
	}
	
	/**
	 * ��ѯ�Զ��������
	 * @param groupId
	 * @return List
	 * @throws DBException
	 */
	private List queryItems(String groupId) throws DBException {
		String sql = "select CUSTOM_ITEM_ID as id,CUSTOM_ITEM_NAME as name,CUSTOM_ITEM_COND as cond,CUSTOM_ITEM_OUTER as outer " +
				"from DQ_CUSTOM_ITEM " +
				"where GROUP_ID='" +
				groupId +
				"' ";
		return DbUtils.select(sql, conn);
	}
	
	/**
	 * ��ѯ�Զ����������Զ�����������������
	 * @param groupId
	 * @return
	 * @throws DBException
	 */
	private List queryParams(String groupId) throws DBException {
		String sql = "select para.PARAMETER_ID as id,para.PARAMETER_NAME as name," +
				"para.DATATYPE as dataType,para.PARAMETER_TYPE as type,para.PARAMETER_EXP as exp,para.PARAMETER_DESP as desp " +
				"from DQ_PARAMETER para,DQ_CUSTOM_PARAMETER cpara " +
				"where para.PARAMETER_ID=cpara.PARAMETER_ID " +
				"and cpara.GROUP_ID='" +
				groupId +
				"' ";
		return DbUtils.select(sql, conn);
	}
	/**
	 * ɾ��ʵ����������
	 * @param entityId
	 * @throws SQLException
	 */
	private void delSubjoin(String entityId) throws SQLException {
		String sql ="delete from DQ_SUBJOIN where ENTITY_ID='"+entityId+"'";
		Statement st = conn.createStatement();
		st.executeUpdate(sql);
		st.close();
	}
	
	/**
	 * ɾ��ʵ�������ϵ��
	 * @param entityId
	 * @throws SQLException
	 */
	private void delForeignTable(String entityId) throws SQLException {
		String sql ="delete from DQ_ENTITY_FOREIGN where ENTITY_ID='"+entityId+"' or FOREIGN_ENTITY_ID='"+entityId+"'";
		Statement st = conn.createStatement();
		st.executeUpdate(sql);
		st.close();
	}
	
	/**
	 * ɾ��ʵ��
	 * @param entityId
	 * @throws SQLException
	 * @throws DBException
	 */
	public void delEntity(String entityId) throws SQLException, DBException {
		delSubjoin(entityId);
		delForeignTable(entityId);
	    String sql1="select GROUP_ID from DQ_GROUP "+"where ENTITY_ID='"
	                +entityId
	                +"'";
	    List dataList = DbUtils.select(sql1, conn);//����DQ_GROUP�в��ENTITY_ID
	    for(int i=0;i<dataList.size();i++)
	    {
	    	Map map = (Map)dataList.get(i);
	    	String groupId=(String)map.get("GROUP_ID");
	    	delGroup(groupId);   //��ִ�з����ɾ��
	    }
	    Statement st = conn.createStatement();
	    sql1 = "delete from DQ_TARGET where ENTITY_ID='"+entityId+"'";//ɾ��ָ��
	    st.executeUpdate(sql1);
	    st.close();
	    st = conn.createStatement();
		String sql ="delete from DQ_ENTITY where ENTITY_ID='"+entityId+"'";
		st.executeUpdate(sql);
		st.close();
	}
	
	/**
	 * ɾ���Զ��������
	 * @param groupId
	 * @throws SQLException
	 */
	private void delItems(String groupId) throws SQLException {
		String sql = "delete from DQ_CUSTOM_ITEM where GROUP_ID='" +
				groupId +
				"' ";
		Statement st = conn.createStatement();
		st.executeUpdate(sql);
		st.close();
	}
	
	/**
	 * ɾ���Զ���������
	 * @param groupId
	 * @throws SQLException
	 * @throws DBException
	 */
	private void delParams(String groupId) throws SQLException, DBException {
		String sql = "select PARAMETER_ID from DQ_CUSTOM_PARAMETER "
				+ "where GROUP_ID='" + groupId + "'";
		List list = DbUtils.select(sql, conn);
		sql = "delete from DQ_CUSTOM_PARAMETER where GROUP_ID='" + groupId + "'";
		Statement st = conn.createStatement();
		st.executeUpdate(sql);
		st.close();
		sql = "delete from DQ_PARAMETER where PARAMETER_ID=?";
		ps = conn.prepareStatement(sql);
		for (int i=0;i < list.size();i++) {
			Map map = (Map)list.get(i);
			String paramId = (String)map.get("PARAMETER_ID");
			ps.setString(1, paramId);
			ps.executeUpdate();
		}
		ps.close();
	}
	
	/**
	 * ���ַ����
	 * @param temp
	 * @return
	 */
	private String checkNull(String temp) {
		if (temp == null)
			return "";
		else
			return temp;
	}
	
	/**
	 * �ر�����
	 * @throws DBException
	 */
	public void close() throws DBException
	{
		DbUtils.freeConnection(conn);
	}
}
