package com.gwssi.advancedSearch.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.core.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;


@Service("advancedQueryService")
@SuppressWarnings("rawtypes")
public class AdvanceQueryService extends BaseService{
	
	@SuppressWarnings("unused")
	private static final String USER_PARAM_SIGN = "（参数值）";
	/**
	 * 数据中心资源目录库 数据源
	 */
	private static final String DATASOURCE_DC ="db_dc";
	/**
	 * 综合查询库 数据源
	 */
	private static final String DATASOUR_SJZX ="sjzx";
	/**
	 * 中心库  数据源
	 */
	private static final String DATASOURS_DC_DC ="dc_dc";
	
	private static final String DATASOURS_DC_QUERY ="db_query";
	/**
	 * 获取业务主题
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> getTopic() throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURCE_DC);
		StringBuffer sql = new StringBuffer();
		sql.append(" select topic_name as title,pk_dc_topic as key from (SELECT DISTINCT t.topic_name,t.pk_dc_topic,T.SORT FROM DC_TABLE d, DC_TOPIC t ");
		sql.append(" where T.effective_mark = 'Y'");
		//sql.append(" and d.is_query='1'");
		//sql.append(" and d.pk_dc_data_source='269563465ca04d98be61dc36d6712c4d'");
		sql.append(" ORDER BY T.SORT)");
		return dao.queryForList(sql.toString(),null);
	} 
	
	/**
	 * 根据业务主题获取业务表
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> getTableByTopic(String topicId) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURCE_DC);
		StringBuffer sql = new StringBuffer();
		sql.append("select d.table_name_cn as title,d.table_name_en as code,d.pk_dc_table as key");
		sql.append(" from dc_table d left join DC_TABLE_TOPIC t on (d.pk_dc_table = t.pk_dc_table and t.pk_dc_topic = ?)");
		sql.append(" where d.is_query='Y'");
		List<String> param=new ArrayList<String>();
		param.add(topicId);
		return dao.queryForList(sql.toString(),param);
	}
	
	/**
	 * 根据业务表获取字段
	 * @param tableName
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> getColumnByTable(String tableName) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURCE_DC);
		StringBuffer sql = new StringBuffer();
		sql.append(" select c.pk_dc_column  as key,c.column_name_cn as title,c.column_name_en as code , ");
		sql.append(" c.column_code as code_table,c.column_type as col_type,c.column_length  as col_lenght , ");
		sql.append(" t.table_name_en  as dc_table_name_en,t.table_name_cn  as dc_table_name_cn ");
		sql.append(" from dc_column c, dc_table t ");
		sql.append(" where c.column_name_cn is not null ");
		sql.append(" and c.pk_dc_table = t.pk_dc_table ");
		sql.append(" and c.pk_dc_table = ? ");
		sql.append(" order by t.order_no");
		List<String> param=new ArrayList<String>();
		param.add(tableName);
		return dao.queryForList(sql.toString(),param);
	}
	
	/**
	 * 根据拼接的sql查询数据
	 * @param tableName
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> getAdvQueryList(String sql) throws OptimusException
	{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
//		if(sql.indexOf("where")>0){
//		 testsql =  sql+"and rownum  <= 500";
//		}else{
//			testsql =  "select * from ("+sql+")  e where rownum  <= 500";
//		}
		String testsql =patternReg(sql, "500");
		//String testsql =  "select * from ("+sql+")  e where rownum <= 500";
		return dao.pageQueryForList(testsql, null);
		//return dao.queryForList(testsql.toString(),null);
	}
	
	/**
	 * 验证sql
	 * @param sql
	 * @return
	 */
	public boolean check(String sql) {
		boolean flag = false;
		if(StringUtils.isNotBlank(sql)){
			IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
			try {
//				String testsql = null;
//				if(sql.indexOf("where")>0){
//				 testsql =  sql+"and rownum <= 5";
//				}else{
//					testsql =  "select * from ("+sql+")  e where rownum <= 5";
//				}
				String testsql =patternReg(sql, "5");
				int i = dao.execute(testsql, null);
				if(i!=-1){
					flag = true;
				}
				else{
					flag = false;
				}
			} catch (OptimusException e) {
				e.printStackTrace();
			} finally{
				
			}
			return flag;
		}
		return flag;
	}
	/***
	 * SQL格式转换
	 * @param sql
	 * @param rownum
	 * @return
	 */
	public String patternReg(String sql,String rownum){
		StringBuffer sb = new StringBuffer();
//		String str = "SELECT DC_RA_MER_CLEAR.ADDR,DC_RA_MER_CLEAR.CLAIMTRANEE FROM DC_RA_MER_CLEAR,DC_RA_MER_CONTACTS WHERE  DC_RA_MER_CLEAR.ADDR=DC_RA_MER_CONTACTS.DEPARTMENT ";
		if(StringUtils.isEmpty(sql)){
			return null;
		}
		sql = sql.toUpperCase();
	String regParamStr = "SELECT([\\S\\s]*?)FROM";
		Pattern pattern =Pattern.compile(regParamStr.trim());
		Matcher matcher =pattern.matcher(sql.trim());
		//如果非空,这是一个正常的sql必须会有值
		System.out.println(matcher.find());
		String paramStr =matcher.group(1);
		String regParamName = "[\\s\\S]*?\\.([\\s\\S]*?),|[\\s\\S]*?\\.([\\s\\S]*?)$";
		pattern =Pattern.compile(regParamName);
		matcher =pattern.matcher(paramStr);
		String nowParamStr =null;
//		LinkedList<String> list = new LinkedList<String>();
		while(matcher.find()){
			//里面报错
			String paramGroupOne= matcher.group(1);
			String lastParamGroup= matcher.group(2);
			if(!StringUtils.isEmpty(paramGroupOne)){
//				list.add(paramGroupOne);
				if(nowParamStr!=null){
					nowParamStr = getNowString(nowParamStr, paramGroupOne);
				}else{
					nowParamStr = getNowString(paramStr, paramGroupOne);
				}
			}else if(!StringUtils.isEmpty(lastParamGroup)){
				if(nowParamStr!=null){
					nowParamStr = getNowString(nowParamStr, lastParamGroup);
				}else{
					nowParamStr = getNowString(paramStr, lastParamGroup);
				}
			}
			
		}
		System.out.println(nowParamStr);
		if(nowParamStr!=null){
			String [] sqlArr =sql.split(paramStr);
			sql =sqlArr[0]+" "+nowParamStr+" "+sqlArr[1];
		}
		
		int i =sql.indexOf("WHERE");
		if(i!=-1){
			sb.append(sql).append(" and rownum <=").append(rownum);
		}else{
			sb.append(sql).append(" WHERE  rownum <= ").append(rownum);
		}
		
		return sb.toString();
	}
/***
	 * 为不同表但字段相同的情况取别名
	 * @param paramStr
	 * @param param
	 * @return
	 */
	public String getNowString(String paramStr,String param){
		String regParamName = "[\\s\\S]*?\\.([\\s\\S]*?),|[\\s\\S]*?\\.([\\s\\S]*?)$";
		Pattern pattern =Pattern.compile(regParamName);
		Matcher matcher =pattern.matcher(paramStr);
//		if(matcher.find()){}
		while(matcher.find()){
			String paramGroupOne= matcher.group(1);
			if(!StringUtils.isEmpty(paramGroupOne)){
				if(paramGroupOne.equals(param)){
					paramStr = paramStr.trim();
					String [] nowStr =paramStr.split("\\."+param);
					if(nowStr.length>2){
						StringBuilder sb = new StringBuilder();
						for(int i =0;i<nowStr.length;i++){
							if(i==nowStr.length-1){
								if(paramStr.endsWith("."+param)){
									sb.append(nowStr[i]+".").append(param).append(" as "+param+i);
								}else{
									sb.append(nowStr[i]);
								}
							}else{
								sb.append(nowStr[i]+".").append(param).append(" as "+param+i);
							}
						}
						paramStr = sb.toString();
						return paramStr;
					}
				}
			}
		
		}
		
		return paramStr;
	}

	public void saveAdvQueryInfo(String params) throws OptimusException {
		Map<String,Object> baseInfoMap = new HashMap<String,Object>();
		 JSONObject json = JSON.parseObject(params);
         for(java.util.Map.Entry<String,Object> entry:json.entrySet()){  
             baseInfoMap.put(entry.getKey(), entry.getValue());
         }  
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_QUERY);
	/*	Map<String,Object> conditionMap = new HashMap<String,Object>();
		Map<String,Object> paramMap = new HashMap<String,Object>();*/
		//获取当前登陆人id
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		String userId = user.getUserId();
		String username = user.getUserName();
		//获取当前时间
		Date date=new Date();
	    DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=format.format(date);
		
		/*String query_info_id = null;
		String condition_id = null;
		String param_id = null;*/
		baseInfoMap.put("created_time", time);
		baseInfoMap.put("creator_id", username);
		//编辑或者修改ADV_QUERY_BASE_INFO表    
		insertAdvQueryBaseInfo(baseInfoMap);
		
		/*if(StringUtils.isBlank(query_info_id)){
			insertAdvQueryBaseInfo(baseInfoMap);
		}else{
			updateAdvQueryBaseInfo(baseInfoMap);
		}*/
		//编辑或者修改ADV_QUERY_CONDITION表 
		/*if(StringUtils.isBlank(condition_id)){
			insertAdvQueryCondition(conditionMap);
		}else{
			updateAdvQueryCondition(conditionMap);
		}
		//编辑或者修改ADV_QUERY_PARAM表 
		if(StringUtils.isBlank(param_id)){
			insertAdvQueryParam(paramMap);
		}else{
			updateAdvQueryParam(paramMap);
		}*/
	}
	
	@SuppressWarnings("unused")
	private void insertAdvQueryParam(Map<String, Object> map) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_QUERY);
		String editSqlParam = "insert into ADV_QUERY_PARAM values(?,?,?, ?,?,?, ?,?,?, ?,?,?, ?,?,?, ?)";
		List<String> paramList=new ArrayList<String>();
		paramList.add("");
		paramList.add("");
		dao.execute(editSqlParam, paramList);
	}

	@SuppressWarnings("unused")
	private void insertAdvQueryCondition(Map<String, Object> map) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_QUERY);
		String editSqlCondition = "insert into ADV_QUERY_CONDITION values(?,?,?, ?,?,?, ?,?,?, ?,?,?, ?,?,?, ?,?,?, ?)";
		List<String> conditionList=new ArrayList<String>();
		conditionList.add("");
		conditionList.add("");
		dao.execute(editSqlCondition, conditionList);
	}

	private void insertAdvQueryBaseInfo(Map<String, Object> map) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_QUERY);
		String editSqlBaseInfo = "insert into  ADV_QUERY_BASE_INFO values(?,?,?, ?,?,?, ?,?,?, ?,?,?, ?,?,?)";
		List<String> baseInfoList=new ArrayList<String>();
		String query_info_id = UUID.randomUUID().toString();
		baseInfoList.add(query_info_id);
		baseInfoList.add((String) map.get("queryNameTransfer"));
		baseInfoList.add((String) map.get("name_en"));
		baseInfoList.add((String) map.get("name_cn"));
		baseInfoList.add((String) map.get("sql"));
//		baseInfoList.add((String) map.get((String) map.get("queryNameTransfer")));
		baseInfoList.add((String) map.get("queryNameTransfer"));
		baseInfoList.add((String) map.get(""));
		baseInfoList.add((String) map.get(""));
		baseInfoList.add((String) map.get("creator_id"));
		baseInfoList.add((String) map.get("created_time"));
		baseInfoList.add((String) map.get(""));
		baseInfoList.add((String) map.get(""));
		baseInfoList.add((String) map.get("column_en"));
		baseInfoList.add((String) map.get("column_cn"));
		baseInfoList.add((String) map.get(""));
		
		dao.execute(editSqlBaseInfo, baseInfoList);
	}
	
	@SuppressWarnings("unused")
	private void updateAdvQueryBaseInfo(Map<String, Object> map) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_QUERY);
		String editSqlBaseInfo = "update  ADV_QUERY_BASE_INFO f set f.query_info_name,f.table_id=?,f.table_name_cn=?,f.sql=?,f.query_info_description=?,f.query_info_state=?,f.is_markup=?,f.creator_id=?,f.created_time=?,f.last_modify_id=?,f.last_modify_time=?,f.column_name_en=?,f.column_name_cn=?,f.column_alias where f.query_info_id=? ";
		List<String> baseInfoList=new ArrayList<String>();
		baseInfoList.add("");
		baseInfoList.add("");
		dao.execute(editSqlBaseInfo, baseInfoList);
	}
	
	@SuppressWarnings("unused")
	private void updateAdvQueryCondition(Map<String, Object> map) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_QUERY);
		String editSqlCondition = "update  ADV_QUERY_CONDITION f set f.query_info_id,f.frist_connector=?,f.left_paren=?,f.left_table_no=?,f.left_table_name_en=?,f.left_table_name_cn=?,f.left_column_no=?,f.left_column_name_en=?,f.left_column_name_cn=?,f.second_connector=?,f.right_table_no=?,f.right_table_name_en=?,f.right_table_name_cn=?,f.right_column_no=?,f.right_column_name_en=?,f.right_column_name_cn=?,f.right_paren=?,show_order=? where f.condition_id=? ";
		List<String> conditionList=new ArrayList<String>();
		conditionList.add("");
		conditionList.add("");
		dao.execute(editSqlCondition, conditionList);
	}
	
	@SuppressWarnings("unused")
	private void updateAdvQueryParam(Map<String, Object> map) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_QUERY);
		String editSqlParam = "update  ADV_QUERY_PARAM f set f.query_info_id=?,f.frist_connector=?,f.left_paren=?,f.table_name_en=?,f.table_name_cn=?,f.column_name_en=?,f.column_name_cn=?,f.second_connector=?,f.param_type=?,f.right_paren=?,f.param_value=?,f.show_order=?,f.table_no=?,f.column_no=?,f.need_input=? where f.param_id=? ";
		List<String> paramList=new ArrayList<String>();
		paramList.add("");
		paramList.add("");
		dao.execute(editSqlParam, paramList);
	}
	
	/**
	 * 根据查询名称和查询创建人查找查询。
	 * @param queryName,createUser
	 * @return
	 * @throws OptimusException
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getList(Map map) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_QUERY);
		StringBuffer sql=new StringBuffer();
		sql.append("select * from ADV_QUERY_BASE_INFO where 1=1 ");
		List list=new ArrayList();
		if (map!=null) {
			Object object = map.get("queryName");
			if (object!=null) {
				if (object.toString().trim().length()>0) {
					sql.append(" and query_info_name like ?");
					sql.append(" ORDER BY CREATED_TIME desc)");
					list.add("%"+object.toString().trim()+"%");
				}
			}
			if (map.get("createUser")!=null) {
				Object object2 = map.get("createUser");
				if (object2.toString().trim().length()>0) {
					sql.append(" and creator_id like ?");
					sql.append(" ORDER BY CREATED_TIME desc)");
					list.add("%"+object2.toString().trim()+"%");
				}
			}
		}
		return dao.pageQueryForList(sql.toString(),list);
	}
	
	/**
	 * 根据查询ID删除查询
	 * @param id
	 * @return
	 * @throws OptimusException
	 */
	@SuppressWarnings("unchecked")
	public void delQuery(String id) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_QUERY);
		String sql="delete from adv_query_param where query_info_id = ?";
		String sql2="delete from adv_query_condition where query_info_id = ?";
		String sql3="delete from adv_query_base_info where query_info_id = ?";
		List list=new ArrayList();
		list.add(id);
		dao.execute(sql, list);
		dao.execute(sql2, list);
		dao.execute(sql3, list);
	}

	/**
	 * 根据ID获取查询信息详情
	 * @param id
	 * @return
	 * @throws OptimusException
	 */
	@SuppressWarnings({ "unchecked" })
	public Map getQueryById(String id) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_QUERY);
		String sql="SELECT QUERY_INFO_NAME,TABLE_NAME_CN,SQL,QUERY_INFO_DESCRIPTION,CREATOR_ID,CREATED_TIME,COLUMN_NAME_CN"
					+" FROM ADV_QUERY_BASE_INFO WHERE QUERY_INFO_ID = ?";
		List list=new ArrayList();
		list.add(id);
		return dao.queryForList(sql, list).get(0);
	}

	@SuppressWarnings({ "unchecked"})
	public String getHtmlById(String id) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_QUERY);
		String sql="select Sql ,column_name_cn,column_name_en from ADV_QUERY_BASE_INFO where QUERY_INFO_ID = ?";
		List list =new ArrayList();
		list.add(id);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		String params="{\"baseInfo\":{\"sql\":\"SELECT DC_RA_PERSON.RECID,DC_RA_PERSON.PBEARING,DC_RA_LICE_INFO.RECID,DC_RA_LICE_INFO.PRIPID FROM DC_RA_ENT_LOGOFF,DC_RA_PERSON,DC_RA_LICE_INFO WHERE  DC_RA_ENT_LOGOFF.RECID=DC_RA_PERSON.DECLECON  AND ( DC_RA_ENT_LOGOFF.RECID = '5'  )\",\"tableNames\":[{\"column_cn\":\"记录ID\",\"column_en\":\"RECID\",\"name_cn\":\"企业自然人信息\",\"name_en\":\"DC_RA_PERSON\"},{\"column_cn\":\"有无计划生育证明\",\"column_en\":\"PBEARING\",\"name_cn\":\"企业自然人信息\",\"name_en\":\"DC_RA_PERSON\"},{\"column_cn\":\"记录ID\",\"column_en\":\"RECID\",\"name_cn\":\"企业许可信息\",\"name_en\":\"DC_RA_LICE_INFO\"},{\"column_cn\":\"主体身份代码\",\"column_en\":\"PRIPID\",\"name_cn\":\"企业许可信息\",\"name_en\":\"DC_RA_LICE_INFO\"}]}}";
		 JSONObject json = JSON.parseObject(params);
		 List listParam = new ArrayList();  
         for(java.util.Map.Entry<String,Object> entry:json.entrySet()){ 
        	 System.out.println(entry.getValue());
        	 JSONObject json2 = (JSONObject) JSONObject.toJSON(entry.getValue());
        	 for(java.util.Map.Entry<String,Object> entry2:json2.entrySet()){  
        		 System.out.println("==========================================");
                 System.out.println(entry2.getKey()+"-"+entry2.getValue()+"\t"); 
                 listParam.add(entry2.getValue());
             }  
         }  
         for(int i = 0 ; i< listParam.size();i++){   
             System.out.println(listParam.get(i));   
         }   
         Map<String, String> map = new HashMap<String, String>();
         @SuppressWarnings("unused")
		List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
         @SuppressWarnings("unused")
		List listParam2 = (List) listParam.get(1); 
         /*for(int i = 0 ; i< listParam2.size();i++){   
             System.out.println(listParam2.get(i).toString()); 
             ((Object) listMap).put(listParam2.get(i).toString());
         } */
        /* for (Map<String, String> _map : listParam2) {
 			map.put(String.valueOf(_map.get("column_cn")), String.valueOf(_map.get("column_en")));
 		}*/
       
	}
}
