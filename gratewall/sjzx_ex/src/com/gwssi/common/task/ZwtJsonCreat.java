package com.gwssi.common.task;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import cn.gwssi.common.component.logger.TxnLogger;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.database.DBOperation;
import com.gwssi.common.database.DBOperationFactory;
import com.gwssi.common.util.JSONUtils;
import com.gwssi.common.util.JsonDataUtil;

public class ZwtJsonCreat
{
	protected static Logger	logger	= TxnLogger.getLogger(ZwtJsonCreat.class
			.getName());
	/**
	 * 根据内容返回右侧数据的map
	 * 
	 * @param content
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private HashMap getRightSideMapStr(String content)
	{
		HashMap right_map = new HashMap();
		right_map.put("description", "<a href='javscript:;' ></a>");
		right_map.put("name", content);
		right_map.put("slug", "anthropocentric-value-source");
		right_map.put("type", "perspective");
		return right_map;
	}

	/**
	 * 根据内容返回左侧数据的map
	 * @param content
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private HashMap getLeftSideMapStr(String content)
	{
		HashMap right_map = new HashMap();
		right_map.put("description", "<a href='javscript:;' ></a>");
		right_map.put("name", content);
		right_map.put("slug", "economy-2");
		right_map.put("type", "theme");
		return right_map;
	}
	
	/**
	 * 根据内容返回中间和左侧对应关系的map
	 * @param content
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private HashMap getCenterRelationStr(HashMap valueMap,List lst_interface)
	{
		String table_name = valueMap.get("TABLE_NAME_CN").toString();
		String table_no = valueMap.get("TABLE_NO").toString();
		String sys_name = valueMap.get("SERVICE_TARGETS_NAME").toString();
		List lst_relation=new ArrayList();
		//先放入该表对应的业务系统
		lst_relation.add(sys_name);
		//判断是否存在对应关系
		for (int j = 0; j < lst_interface.size(); j++) {
			HashMap map = (HashMap) lst_interface.get(j);
			String table_id = map.get("TABLE_ID").toString();
			if (table_id.indexOf(table_no)!=-1) {
				lst_relation.add(map.get("INTERFACE_NAME").toString());
			}
		}
		HashMap center_map = new HashMap();
		center_map.put("description", "<a href='javscript:;' ></a>");
		center_map.put("name", table_name);
		center_map.put("links", lst_relation.toArray());
		center_map.put("slug", "anthropocentric-value-source");
		center_map.put("type", "episode");
		return center_map;
	}
	
	/**
	 * 根据内容返回中间和左右侧对应关系的map
	 * @param content
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private HashMap getTableInterfaceServiceRelationStr(HashMap valueMap,List lst_interface)
	{
		String INTERFACE_ID = valueMap.get("INTERFACE_ID").toString();
		String SERVICE_TARGETS_NAME = valueMap.get("SERVICE_TARGETS_NAME").toString();
		String interface_name = "";
		
		List lst_relation=new ArrayList();
		//先放入该表对应的业务系统
		//lst_relation.add(sys_name);
		//判断是否存在对应关系
		for (int j = 0; j < lst_interface.size(); j++) {
			HashMap map = (HashMap) lst_interface.get(j);
			String id = map.get("INTERFACE_ID").toString();
			if (id.equals(INTERFACE_ID)) {
				//添加表名关系
				interface_name = map.get("INTERFACE_NAME").toString();
				String[] table_names = map.get("TABLE_NAME").toString().split(",");
				for(int i = 0;i<table_names.length;i++){
					lst_relation.add(table_names[i]);
				}
				
			}
		}
		
		String[] target_names = SERVICE_TARGETS_NAME.split(",");
		for(int i=0;i<target_names.length;i++){
			if (!lst_relation.contains(target_names[i])) {
				lst_relation.add(target_names[i]);
			}
		}
		
		HashMap center_map = new HashMap();
		center_map.put("description", "<a href='javscript:;' ></a>");
		center_map.put("name", interface_name);
		center_map.put("links", lst_relation.toArray());
		center_map.put("slug", "anthropocentric-value-source");
		center_map.put("type", "episode");
		return center_map;
	}

	/**
	 * 生成 蛛网图 系统-接口-表关系图 数据结构
	 * @return
	 * @throws DBException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String bulidZwtTableDetail() throws DBException
	{
		long begin=System.currentTimeMillis();
		logger.info("[系统-接口-表关系图]开始");
		// 获取被调用前20的基础接口
		String sql = JobSqlUtil.getTop20InterfaceSql();
		DBOperation operation = DBOperationFactory.createOperation();
		List lst_interface = operation.select(sql);
		String tableIds = "";
		// 定义右侧的数据集合
		List rightList = new ArrayList();
		// 拼接所有的前20的接口所对应的表的ID串
		for (int i = 0; i < lst_interface.size(); i++) {
			HashMap map = (HashMap) lst_interface.get(i);
			String table_id = map.get("TABLE_ID").toString();
			rightList.add(getRightSideMapStr(map.get("INTERFACE_NAME")
					.toString()));
			for (int j = 0; j < table_id.trim().split(",").length; j++) {
				tableIds += tableIds == "" ? "'"
						+ table_id.trim().split(",")[j] + "'" : ",'"
						+ table_id.trim().split(",")[j] + "'";
			}
		}
		// 定义左侧的数据集合
		List leftList = new ArrayList();
		// 放入左侧的业务系统数据
		String[] leftArray = new String[] { "案件系统", "12315系统", "登记系统", "信用系统",
				"网格监管", "市场监管系统", "广告系统", "电子商务", "地税局", "国家工商总局" };
		for (int i = 0; i < leftArray.length; i++) {
			leftList.add(getLeftSideMapStr(leftArray[i]));
		}
		Map all_map = new HashMap();
		all_map.put("perspectives", rightList);
		all_map.put("themes", leftList);

		// 根据拼接的表串 查询所有对应的表
		sql = JobSqlUtil.getShareTableByTableId(tableIds);
		List lst_share_table = operation.select(sql);
		//定义中间数据集合
		List centerList = new ArrayList();
		for (int i = 0; i < lst_share_table.size(); i++) {
			HashMap map = (HashMap) lst_share_table.get(i);
			centerList.add(getCenterRelationStr(map, lst_interface));
		}
		all_map.put("episodes", centerList);
		logger.info("[系统-接口-表关系图]结束,耗时:"+(System.currentTimeMillis()-begin));
		return JSONUtils.toJsonStringByObj(all_map);
	}
	
	/**
	 * 生成 蛛网图 表-接口-服务对象关系图 数据结构
	 * @return
	 * @throws DBException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String bulidZwtInterfaceDetail() throws DBException
	{
		long begin=System.currentTimeMillis();
		logger.info("[表-接口-服务对象]开始");
		// 获取被调用前20的基础接口
		String sql = JobSqlUtil.getTop20InterfaceSql();
		DBOperation operation = DBOperationFactory.createOperation();
		List lst_interface = operation.select(sql);
		String tableIds = "";
		
		// 拼接所有的前20的接口所对应的表的ID串
		for (int i = 0; i < lst_interface.size(); i++) {
			HashMap map = (HashMap) lst_interface.get(i);
			String table_id = map.get("TABLE_ID").toString();
			//rightList.add(getRightSideMapStr(map.get("INTERFACE_NAME").toString()));
			for (int j = 0; j < table_id.trim().split(",").length; j++) {
				tableIds += tableIds == "" ? "'"
						+ table_id.trim().split(",")[j] + "'" : ",'"
						+ table_id.trim().split(",")[j] + "'";
			}
		}
		
		// 定义右侧的数据集合
		List rightList = new ArrayList();
		sql = JobSqlUtil.getRightServiceObject();
		List sObj = operation.select(sql);
		for(int i=0;i<sObj.size();i++){
			HashMap  map = (HashMap)sObj.get(i);
			rightList.add(getRightSideMapStr(map.get("SERVICE_TARGETS_NAME").toString()));
		}
		
		// 定义左侧的数据集合
		
		List leftList = new ArrayList();
		sql = JobSqlUtil.getLeftTableByTableId(tableIds);
		List  tables = operation.select(sql);
		for(int i=0;i<tables.size();i++){
			HashMap  map = (HashMap)tables.get(i);
			leftList.add(getLeftSideMapStr(map.get("TITLE").toString()));
			
		}
		
		Map all_map = new HashMap();
		all_map.put("perspectives", rightList);
		all_map.put("themes", leftList);

		// 查询被调用前20的接口对应的服务对象信息
		sql = JobSqlUtil.getShareInterfaceTable();
		List lst_share_interface = operation.select(sql);
		//定义中间数据集合
		List centerList = new ArrayList();
		for (int i = 0; i < lst_share_interface.size(); i++) {
			HashMap map = (HashMap) lst_share_interface.get(i);
			centerList.add(getTableInterfaceServiceRelationStr(map, lst_interface));
		}
		all_map.put("episodes", centerList);
		logger.info("[表-接口-服务对象]结束,耗时:"+(System.currentTimeMillis()-begin));
		return JSONUtils.toJsonStringByObj(all_map);
	}

	/**
	 * 
	 * getZwtContent(获取蛛网图的内容[主题-接口-服务对象])    
	 * TODO(这里描述这个方法适用条件 – 可选)    
	 * TODO(这里描述这个方法的执行流程 – 可选)    
	 * TODO(这里描述这个方法的使用方法 – 可选)    
	 * TODO(这里描述这个方法的注意事项 – 可选)    
	 * @param type
	 * @return
	 * @throws DBException
	 * @throws UnsupportedEncodingException        
	 * String       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getZwtContent(String type) throws DBException,
			UnsupportedEncodingException
	{
		long begin=System.currentTimeMillis();
		logger.info("[生成首页蛛网图初始图]开始");
		List zwtInterface = getZwtRight();
		String zwtP3 = zwtRight(zwtInterface);

		/**
		 * 数据主题，蛛网图左侧数据
		 */
		zwtInterface = getZwtLeft();
		String zwtP2 = zwtLeft(zwtInterface);
		/**
		 * 蛛网图中间部分数据
		 */
		Map dataMap = getZwtCenter(type);

		List targets = (List) dataMap.get("targets");
		List topics = (List) dataMap.get("topics");
		String zwtP1 = zwtCenter(targets, topics, type);
		logger.info("[生成首页蛛网图初始图]结束,耗时:"+(System.currentTimeMillis()-begin));
		return "{\"episodes\": " + zwtP1 + ", \r\n\"themes\": " + zwtP2
				+ ", \r\n\"perspectives\": " + zwtP3 + "}";
		
	}
	
	/**
	 * 
	 * getForceContent(获取力导向布局图的内容[接口-服务对象])    
	 * TODO(这里描述这个方法适用条件 – 可选)    
	 * TODO(这里描述这个方法的执行流程 – 可选)    
	 * TODO(这里描述这个方法的使用方法 – 可选)    
	 * TODO(这里描述这个方法的注意事项 – 可选)    
	 * @param type
	 * @return
	 * @throws DBException
	 * @throws UnsupportedEncodingException        
	 * String       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getForceContent(String type,String strCon,String iCon) throws DBException,
			UnsupportedEncodingException
	{
		long begin=System.currentTimeMillis();
		logger.info("[生成力图初始图]开始");
		
		//全部服务对象信息：service_targets_id,service_targets_name,svr_count
		List fObject = getNodes(type,strCon,iCon);//nodes
		List fRel = getNodesRel(type,strCon,iCon);//link
		String fNodes = forceCenter(fObject,fRel,"part");

		return fNodes;
		/*return "{\"episodes\": " + zwtP1 + ", \r\n\"themes\": " + zwtP2
				+ ", \r\n\"perspectives\": " + zwtP3 + "}";*/
		
	}
	
	/**
	 * 
	 * @param type
	 * @param strCon：服务对象查询条件
	 * @param iCon:接口查询条件
	 * @return
	 * @throws DBException
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public HashMap getForceMapdata(String type,String strCon,String iCon) throws DBException,
			UnsupportedEncodingException
	{
		long begin=System.currentTimeMillis();
		logger.info("[生成力图初始图]开始");
		
		//全部服务对象信息：service_targets_id,service_targets_name,svr_count
		List fObject = getNodes(type,strCon,iCon);//nodes
		List fRel = getNodesRel(type,strCon,iCon);//link
		HashMap fNodes = forceStrMap(fObject,fRel,"part");

		return fNodes;
		/*return "{\"episodes\": " + zwtP1 + ", \r\n\"themes\": " + zwtP2
				+ ", \r\n\"perspectives\": " + zwtP3 + "}";*/
		
	}
	
	/**
	 * 服务对象，蛛网图右侧数据
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getZwtRight() throws DBException
	{
		DBOperation operation = DBOperationFactory.createOperation();
		
		List zwtInterface = new ArrayList();
		String sql = " select r.service_targets_id, r.service_targets_name, (select count(1) "
				+ "from collect_task task where task.service_targets_id = r.service_targets_id and task.is_markup='Y') + "
				+ "(select count(1) from share_service svr "
				+ "where svr.service_targets_id = r.service_targets_id and svr.is_markup='Y') svr_count "
				+ " from res_service_targets r where r.is_markup = 'Y' and r.service_targets_id"
				+ " is not null  order by r.show_order";

		zwtInterface = operation.select(sql);

		return zwtInterface;
	}
	
	/**
	 * 服务对象、接口信息，力图基本元素
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getNodes(String type,String strCon,String iCon) throws DBException
	{
		DBOperation operation = DBOperationFactory.createOperation();
		String sql = "";
		List zwtInterface = new ArrayList();
		if(type.equals("all")){
			StringBuffer sqlBuf = new StringBuffer("select 'o' AS type,t.service_targets_name as name ,t.service_targets_id as id  from res_service_targets t where t.is_markup='Y' ");
			
			if(strCon!=null &&!strCon.equals("")){
				sqlBuf.append(" and t.service_targets_type in "+strCon);
			}
			
			sqlBuf.append(" union all ");
			sqlBuf.append(" select 'i' as type ,i.interface_name as name ,i.interface_id as id  from share_interface i  where i.is_markup='Y' ");
			
			if(iCon!=null &&!iCon.equals("")){
				sqlBuf.append(" and i.interface_name like '%"+iCon+"%'");
			}
			sql = sqlBuf.toString();
			System.out.println("Nodes:all-sql="+sql);
			
			
			/*sql = " select 'o' AS type,t.service_targets_name as name ,t.service_targets_id as id  from res_service_targets t where t.is_markup='Y' "
					+ " union all "
					+ " select 'i' as type ,i.interface_name as name ,i.interface_id as id  from share_interface i  where i.is_markup='Y'";*/
		} else{
			StringBuffer sqlBuf = new StringBuffer();
			sqlBuf.append("select 'i' as type, interface_id as id,interface_desc as name "
					+" from (SELECT s.interface_id, t.interface_name interface_desc, count(1)"
					+" FROM SHARE_SERVICE s, share_interface t where s.interface_id = t.interface_id "
					+" and s.is_markup = 'Y' and t.interface_state = 'Y' and t.is_markup = 'Y' ");
			if(iCon!=null &&!iCon.equals("")){
				sqlBuf.append(" and t.interface_name like '%"+iCon+"%'");
			}
			sqlBuf.append(" group by s.interface_id, t.interface_name order by 3 desc) where rownum <= 20 ");
			sqlBuf.append(" union all ");
			sqlBuf.append(" select 'o' as type,id,name from ( "
					+" select distinct ss.service_targets_id as id ,tt.service_targets_name as name  from share_service ss,res_service_targets tt where ss.interface_id in (select interface_id from (select * "
					+"  from (SELECT s.interface_id, t.interface_name interface_desc, count(1)"
					+" FROM SHARE_SERVICE s, share_interface t where s.interface_id = t.interface_id"
					+" and s.is_markup = 'Y' and t.interface_state = 'Y' and t.is_markup = 'Y'  group by s.interface_id, t.interface_name order by 3 desc) "
					+" where rownum <= 20)) and ss.service_targets_id = tt.service_targets_id ");
			
			if(strCon!=null&&!strCon.equals("")){
				sqlBuf.append(" and tt.service_targets_type in "+strCon);
			}
			
			sqlBuf.append(")");
			sql = sqlBuf.toString();
			
			
			/*if(strCon!=null&&!strCon.equals("")){
				sql = "select 'i' as type, interface_id as id,interface_desc as name "
						+" from (SELECT s.interface_id, t.interface_name interface_desc, count(1)"
						+" FROM SHARE_SERVICE s, share_interface t where s.interface_id = t.interface_id "
						+" and s.is_markup = 'Y' and t.interface_state = 'Y' and t.is_markup = 'Y'"
						+" group by s.interface_id, t.interface_name order by 3 desc) where rownum <= 20 "
						+" union all  "
						+" select 'o' as type,id,name from ( "
						+" select distinct ss.service_targets_id as id ,tt.service_targets_name as name  from share_service ss,res_service_targets tt where ss.interface_id in (select interface_id from (select * "
						+"  from (SELECT s.interface_id, t.interface_name interface_desc, count(1)"
						+" FROM SHARE_SERVICE s, share_interface t where s.interface_id = t.interface_id"
						+" and s.is_markup = 'Y' and t.interface_state = 'Y' and t.is_markup = 'Y'  group by s.interface_id, t.interface_name order by 3 desc) "
						+" where rownum <= 20)) and ss.service_targets_id = tt.service_targets_id and tt.service_targets_type in "+strCon+")"
						;
			}else{
				sql = "select 'i' as type, interface_id as id,interface_desc as name "
						+" from (SELECT s.interface_id, t.interface_name interface_desc, count(1)"
						+" FROM SHARE_SERVICE s, share_interface t where s.interface_id = t.interface_id "
						+" and s.is_markup = 'Y' and t.interface_state = 'Y' and t.is_markup = 'Y'"
						+" group by s.interface_id, t.interface_name order by 3 desc) where rownum <= 20 "
						+" union all  "
						+" select 'o' as type,id,name from ( "
						+" select distinct ss.service_targets_id as id ,tt.service_targets_name as name  from share_service ss,res_service_targets tt where ss.interface_id in (select interface_id from (select * "
						+"  from (SELECT s.interface_id, t.interface_name interface_desc, count(1)"
						+" FROM SHARE_SERVICE s, share_interface t where s.interface_id = t.interface_id"
						+" and s.is_markup = 'Y' and t.interface_state = 'Y' and t.is_markup = 'Y'  group by s.interface_id, t.interface_name order by 3 desc) "
						+" where rownum <= 20)) and ss.service_targets_id = tt.service_targets_id)"
						;
			}*/
			
			
		}
		
       System.out.println("@@@@@@@sql="+sql);
		zwtInterface = operation.select(sql);

		return zwtInterface;
	}
	
	/**
	 * 服务对象、接口信息，力图基本元素
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getNodesRel(String type,String strCon,String iCon) throws DBException
	{
		DBOperation operation = DBOperationFactory.createOperation();
		String sql = "";
		List zwtInterface = new ArrayList();
		if(("all").equals(type)){
			StringBuffer sqlBuf = new StringBuffer(" select t.*,r.service_targets_name from ( "
					+ " select i.interface_name, i.interface_id, s.service_targets_id "
					+" from share_interface i left join share_service s" 
					+ " on i.interface_id = s.interface_id ) t,res_service_targets r where t.service_targets_id = r.service_targets_id");
			if(strCon!=null && !strCon.equals("")){
				sqlBuf.append(" and r.service_targets_type in "+strCon);
			}
			if(iCon!=null && !iCon.equals("")){
				sqlBuf.append(" and t.interface_name like '%"+iCon+"%' ");
			}
			sql = sqlBuf.toString();
			
			/*sql = " select t.*,r.service_targets_name from ( "
					+ " select i.interface_name, i.interface_id, s.service_targets_id "
					+" from share_interface i left join share_service s" 
					+ " on i.interface_id = s.interface_id ) t,res_service_targets r where t.service_targets_id = r.service_targets_id";*/
		}else {
			StringBuffer sqlBuf = new StringBuffer(" select t.*,r.service_targets_name from ( "
					+ " select i.interface_name, i.interface_id, s.service_targets_id "
					+" from share_interface i left join share_service s" 
					+ " on i.interface_id = s.interface_id  where i.interface_id in (select interface_id from "
					+" (SELECT s.interface_id, t.interface_name interface_desc, count(1) "
 +" FROM SHARE_SERVICE s, share_interface t where s.interface_id = t.interface_id "
 +" and s.is_markup = 'Y' and t.interface_state = 'Y' and t.is_markup = 'Y'");
			if(iCon!=null && !iCon.equals("")){
				sqlBuf.append(" and t.interface_name like '%"+iCon+"%' ");
			}
			
			sqlBuf.append(" group by s.interface_id, t.interface_name order by 3 desc) where rownum<=20 "
					+")) t,res_service_targets r where t.service_targets_id = r.service_targets_id ");
			
			if(strCon!=null&&!strCon.equals("")){
				sqlBuf.append("and r.service_targets_type in "+strCon);
			}
			
			sql = sqlBuf.toString();
			
			/*if(strCon!=null&&!strCon.equals("")){
				sql = " select t.*,r.service_targets_name from ( "
						+ " select i.interface_name, i.interface_id, s.service_targets_id "
						+" from share_interface i left join share_service s" 
						+ " on i.interface_id = s.interface_id  where i.interface_id in (select interface_id from "
						+" (SELECT s.interface_id, t.interface_name interface_desc, count(1) "
	 +" FROM SHARE_SERVICE s, share_interface t where s.interface_id = t.interface_id "
	 +" and s.is_markup = 'Y' and t.interface_state = 'Y' and t.is_markup = 'Y'"
	 +" group by s.interface_id, t.interface_name order by 3 desc)"
						+")) t,res_service_targets r where t.service_targets_id = r.service_targets_id and r.service_targets_type in "+strCon;
			}else{
			sql = " select t.*,r.service_targets_name from ( "
					+ " select i.interface_name, i.interface_id, s.service_targets_id "
					+" from share_interface i left join share_service s" 
					+ " on i.interface_id = s.interface_id  where i.interface_id in (select interface_id from "
					+" (SELECT s.interface_id, t.interface_name interface_desc, count(1) "
 +" FROM SHARE_SERVICE s, share_interface t where s.interface_id = t.interface_id "
 +" and s.is_markup = 'Y' and t.interface_state = 'Y' and t.is_markup = 'Y'"
 +" group by s.interface_id, t.interface_name order by 3 desc) where rownum<=20 "
					+")) t,res_service_targets r where t.service_targets_id = r.service_targets_id";
			}*/
		}
		System.out.println("rel-sql:"+sql);
		
		zwtInterface = operation.select(sql);

		return zwtInterface;
	}
	
	/**
	 * 蛛网图的左侧数据，数据主题部分
	 * 
	 * @param topics
	 * @return
	 * @throws DBException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String zwtRight(List targets) throws DBException
	{
		String result = "";
		List rst = new ArrayList();
		for (int ii = 0; ii < targets.size(); ii++) {
			Map tmpMap = (Map) targets.get(ii);
			Map tmp = new HashMap();
			tmp.put("type", "perspective");
			tmp.put("description",
					"<a href='javscript:;' ></a>");
			tmp.put("slug", "anthropocentric-value-source");
			tmp.put("name", tmpMap.get("SERVICE_TARGETS_NAME"));
			tmp.put("count", tmpMap.get("SVR_COUNT"));
			rst.add(tmp);
		}

		result = JsonDataUtil.toJSONString(rst);

		return result;
	}
	
	/**
	 * 服务对象，蛛网图右侧数据
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getZwtLeft() throws DBException
	{
		DBOperation operation = DBOperationFactory.createOperation();
		String sql = "select t.service_targets_id as key , t.service_targets_name as title "
				+ "from res_service_targets t where t.is_markup = '"
				+ ExConstant.IS_MARKUP_Y
				+ "' and (select count(1) from res_business_topics s where s.service_targets_id = t.service_targets_id) > 0"
				+ " order by t.service_targets_type, t.show_order";
		List zwtInterface = operation.select(sql);

		return zwtInterface;
	}
	
	/**
	 * 蛛网图的左侧数据，数据主题部分
	 * 
	 * @param topics
	 * @return
	 * @throws DBException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String zwtLeft(List topics) throws DBException
	{
		String result = "";
		List rst = new ArrayList();
		for (int ii = 0; ii < topics.size(); ii++) {
			Map tmpMap = (Map) topics.get(ii);
			Map tmp = new HashMap();
			tmp.put("type", "theme");
			tmp.put("description",
					"<a href='javscript:;' ></a>");
			tmp.put("slug", "economy-2");
			tmp.put("key", tmpMap.get("KEY"));
			tmp.put("name", " " + tmpMap.get("TITLE").toString() + " ");
			rst.add(tmp);
		}

		result = JsonDataUtil.toJSONString(rst);

		return result;
	}
	
	/**
	 * 蛛网图中间部分数据， 包括两部分，接口关联的服务对应的服务对象 接口查询的数据表的数据主题
	 * 
	 * @return
	 * @throws DBException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getZwtCenter(String type) throws DBException
	{
		DBOperation operation = DBOperationFactory.createOperation();
		
		/**
		 * 接口服务对象
		 */
		Map dataMap = new HashMap();

		String sql = "with t1 as ("
				+ ("part".equals(type) ? "select * from( " : "")
				+ "SELECT s.interface_id,t.interface_name interface_desc ,count(1) "
				+ " FROM SHARE_SERVICE s,share_interface t where s.interface_id=t.interface_id "
				+ " and s.is_markup='Y' and t.interface_state = 'Y' and t.is_markup='Y' group by s.interface_id,t.interface_name order by 3 desc "
				+ " "
				+ ("part".equals(type) ? " ) where rownum<=20 " : "")
				+ " ) select rst.service_targets_name, b.* "
				+ "from (select svr.service_targets_id, t1.* from share_service svr, t1 "
				+ "where svr.interface_id = t1.interface_id and svr.is_markup='Y') b, res_service_targets rst "
				+ "where rst.service_targets_id = b.service_targets_id and rst.is_markup='Y'";
		List targets = operation.select(sql);
		System.out.println("@@@@@@dwn--type="+type+"sql:"+sql);
		dataMap.put("targets", targets);
		/**
		 * 接口关联主题
		 */
		sql = " select interface_id, interface_name interface_desc, "
				+ "get_restarget_bytableid(table_id) service_targets_name from(   "
				+ "select interface_id,t.interface_name, wm_concat(t.table_id) table_id   "
				+ "from share_interface t where t.is_markup = 'Y' and t.interface_state = 'Y' "
				+ "group by t.interface_name,t.interface_id)";
		List topics = operation.select(sql);
		dataMap.put("topics", topics);

		return dataMap;
	}
	
	private String zwtCenter(List targets, List topics, String type)
			throws DBException
	{
		String result = "";
		List showNodes = new ArrayList();
		if ("part".equals(type)) {
			showNodes = getShowNode();
		} else {
			showNodes = getShowAllNode();
		}
		List rst = new ArrayList();
		//System.out.println("size="+showNodes.size());
		for (int ii = 0; ii < showNodes.size(); ii++) {
			Map tmp1 = new HashMap();
			Map tmpMap = (Map) showNodes.get(ii);
			tmp1.put("name", tmpMap.get("INTERFACE_DESC"));
			tmp1.put("type", "episode");
			tmp1.put("episode", 1);
			List list = new ArrayList();
			// System.out.println(tmpMap);
			for (int jj = 0; jj < topics.size(); jj++) {
				Map tmp = (Map) topics.get(jj);
				// System.out.println(tmp);
				if (tmpMap.get("INTERFACE_DESC") != null
						&& tmp.get("INTERFACE_DESC") != null
						&& tmpMap.get("INTERFACE_DESC").toString().equals(
								tmp.get("INTERFACE_DESC").toString())) {
					if (tmp.get("SERVICE_TARGETS_NAME") != null
							&& StringUtils.isNotBlank(tmp.get(
									"SERVICE_TARGETS_NAME").toString())) {
						String[] tars = tmp.get("SERVICE_TARGETS_NAME")
								.toString().split(",");
						for (int kk = 0; kk < tars.length; kk++) {
							if (!list.contains(" " + tars[kk] + " ")) {
								list.add(" " + tars[kk] + " ");
							}
						}
					}

				}
			}
			for (int jj = 0; jj < targets.size(); jj++) {
				Map tmp = (Map) targets.get(jj);
				// System.out.println(tmp);
				if (tmpMap.get("INTERFACE_DESC") != null
						&& tmp.get("INTERFACE_DESC") != null
						&& tmpMap.get("INTERFACE_DESC").toString().equals(
								tmp.get("INTERFACE_DESC").toString())) {
					if (tmp.get("SERVICE_TARGETS_NAME") != null
							&& StringUtils.isNotBlank(tmp.get(
									"SERVICE_TARGETS_NAME").toString())
							&& !list.contains(tmp.get("SERVICE_TARGETS_NAME")
									.toString())) {
						list.add(tmp.get("SERVICE_TARGETS_NAME").toString()
								.trim());
					}
				}
			}
			tmp1.put("links", list);
			rst.add(tmp1);
		}
		result = JsonDataUtil.toJSONString(rst);

		// System.out.println("---------------------\n"+result);
		return result;
	}
	
	/**
	 * 返回力状图的datafile内容
	 * @param nodes
	 * @param nodesRel
	 * @param type
	 * @return
	 * @throws DBException
	 */
	private String forceCenter(List nodes, List nodesRel, String type)
			throws DBException
	{
		String strNodes = "";
		String strLinks = "";
		
		//Map rst = new HashMap();
		
		List NodesMap = new ArrayList();
		System.out.println("size="+nodes.size());
		for (int ii = 0; ii < nodes.size(); ii++) {
			Map tmp1 = new HashMap();
			Map tmpMap = (Map) nodes.get(ii);
			if(tmpMap!=null &&tmpMap.get("TYPE").toString().equals("o")){
				//tmp1.put("symbol", "star");
				//tmp1.put("symbolSize", 50);
				tmp1.put("category", 1);//服务对象
			}else{
				tmp1.put("category", 2);//接口
			}
			tmp1.put("name", tmpMap.get("NAME").toString());
			tmp1.put("pid", tmpMap.get("ID").toString());
			NodesMap.add(tmp1);
		}
		
		List LinksMap = new ArrayList();
		for(int jj=0;jj<nodesRel.size();jj++){
			Map tmp2 = new HashMap();
			Map tmp3 = new HashMap();
			Map tmpMap =(Map)nodesRel.get(jj);
			tmp2.put("source", tmpMap.get("INTERFACE_NAME"));
			tmp2.put("target", tmpMap.get("SERVICE_TARGETS_NAME"));
			tmp2.put("weight", "100");
			tmp2.put("name", "调用");
			//服务对象调用接口情况
			tmp3.put("source", tmpMap.get("SERVICE_TARGETS_NAME"));
			tmp3.put("target", tmpMap.get("INTERFACE_NAME"));
			tmp3.put("weight", "1");
			tmp3.put("name", "调用");
			
			LinksMap.add(tmp2);
			LinksMap.add(tmp3);
		}
		
		//rst.put("nodes", NodesMap);
		//rst.put("links", LinksMap);
		
		strNodes = JsonDataUtil.toJSONString(NodesMap);
		strLinks = JsonDataUtil.toJSONString(LinksMap);
		String result = "{\"nodes\": "+strNodes+", \r\n\"links\": "+strLinks+"}";
		
		return result;
	}
	
	private HashMap<String,String> forceStrMap(List nodes, List nodesRel, String type)
			throws DBException
	{
		HashMap<String,String> map = new HashMap<String,String>();
		String strNodes = "";
		String strLinks = "";
		
		//Map rst = new HashMap();
		
		List NodesMap = new ArrayList();
		System.out.println("size="+nodes.size());
		for (int ii = 0; ii < nodes.size(); ii++) {
			Map tmp1 = new HashMap();
			Map tmpMap = (Map) nodes.get(ii);
			if(tmpMap!=null &&tmpMap.get("TYPE").toString().equals("o")){
				//tmp1.put("symbol", "star");
				//tmp1.put("symbolSize", 50);
				tmp1.put("category", 1);//服务对象
			}else{
				tmp1.put("category", 2);//接口
			}
			tmp1.put("name", tmpMap.get("NAME").toString());
			tmp1.put("pid", tmpMap.get("ID").toString());
			NodesMap.add(tmp1);
		}
		
		List LinksMap = new ArrayList();
		for(int jj=0;jj<nodesRel.size();jj++){
			Map tmp2 = new HashMap();
			Map tmp3 = new HashMap();
			Map tmpMap =(Map)nodesRel.get(jj);
			tmp2.put("source", tmpMap.get("INTERFACE_NAME"));
			tmp2.put("target", tmpMap.get("SERVICE_TARGETS_NAME"));
			tmp2.put("weight", "1");
			tmp2.put("name", "调用");
			//服务对象调用接口情况
			tmp3.put("source", tmpMap.get("SERVICE_TARGETS_NAME"));
			tmp3.put("target", tmpMap.get("INTERFACE_NAME"));
			tmp3.put("weight", "1");
			tmp3.put("name", "调用");
			
			LinksMap.add(tmp2);
			LinksMap.add(tmp3);
		}
		
		//rst.put("nodes", NodesMap);
		//rst.put("links", LinksMap);
		
		strNodes = JsonDataUtil.toJSONString(NodesMap);
		strLinks = JsonDataUtil.toJSONString(LinksMap);
		//String result = "{\"nodes\": "+strNodes+", \r\n\"links\": "+strLinks+"}";
		map.put("nodes", strNodes);
		map.put("links", strLinks);
		return map;
	}
	
	/**
	 * 查询接口使用次数最多的20条记录， 用于首页蛛网图默认显示
	 * 
	 * @return
	 * @throws DBException
	 */
	private List getShowNode() throws DBException
	{
		DBOperation operation = DBOperationFactory.createOperation();
		List zwtInterface = new ArrayList();
		String sql = "select * from( SELECT s.interface_id,t.interface_name interface_desc ,count(1) "
				+ " FROM SHARE_SERVICE s,share_interface t where s.interface_id=t.interface_id "
				+ " and s.is_markup='Y' and t.is_markup='Y' and t.interface_state = 'Y' group by s.interface_id,t.interface_name order by 3 desc)"
				+ " where rownum<=20";
		zwtInterface = operation.select(sql);

		return zwtInterface;
	}
	
	/**
	 * 接口所有数据，用于组织蛛网图全部数据
	 * 
	 * @return
	 * @throws DBException
	 */
	private List getShowAllNode() throws DBException
	{
		DBOperation operation = DBOperationFactory.createOperation();
		
		List zwtInterface = new ArrayList();
		String sql= "SELECT t.interface_id, t.interface_name interface_desc, count(1)" +
				" FROM (select interface_id from SHARE_SERVICE where is_markup = 'Y') s," +
				" (select * from share_interface where is_markup = 'Y' and interface_state = 'Y' ) t" +
				" where s.interface_id(+) = t.interface_id" +
				" group by t.interface_id, t.interface_name order by 3 desc";
		/*
		String sql = " SELECT s.interface_id,t.interface_name interface_desc ,count(1) "
				+ " FROM SHARE_SERVICE s,share_interface t where s.interface_id=t.interface_id "
				+ " and s.is_markup='Y' group by s.interface_id,t.interface_name order by 3 desc ";
		*/
		
		zwtInterface = operation.select(sql);

		return zwtInterface;
	}
	
	/**
	 * 
	 * getShare_Interface(获取共享接口信息)    
	 * TODO(这里描述这个方法适用条件 – 可选)    
	 * TODO(这里描述这个方法的执行流程 – 可选)    
	 * TODO(这里描述这个方法的使用方法 – 可选)    
	 * TODO(这里描述这个方法的注意事项 – 可选)    
	 * @return
	 * @throws DBException        
	 * List       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public List getShare_Interface() throws DBException{
		DBOperation operation = DBOperationFactory.createOperation();
		
		List share_Interface = new ArrayList();
		String sql = " select t.INTERFACE_NAME,t.TABLE_ID,t.TABLE_NAME_CN "
			+ " from share_interface t where t.is_markup='Y' and t.interface_state = 'Y'   order by t.last_modify_time desc ";
		share_Interface = operation.select(sql);

		return share_Interface;
		
	}
	
	public List getShare_Table(String ids) throws DBException{
		DBOperation operation = DBOperationFactory.createOperation();
		
		List share_Interface = new ArrayList();
		String sql = " select t1.TABLE_NAME_CN,t1.TABLE_NAME_EN,t2.TOPICS_NAME from res_share_table t1,res_business_topics t2  "
			+ " where t1.business_topics_id = t2.business_topics_id(+) and t1.table_no in ( "+ids+")";
		share_Interface = operation.select(sql);

		return share_Interface;
		
	}
	
	public String getInterface_table(){
		StringBuffer content = new StringBuffer("");
		List rootNode = new ArrayList();
		try {
			rootNode = getShare_Interface();
			for(int i=0;i<rootNode.size();i++){
				Map tmpMap = (Map) rootNode.get(i);
				String INTERFACE_NAME = (String)tmpMap.get("INTERFACE_NAME");//接口名称
				String id  = (String)tmpMap.get("TABLE_ID");//表ID
				System.out.println("id=="+id);
				String[] ids = id.split(",");
				System.out.println("ids.size="+ids.length);
				StringBuffer table_no = new StringBuffer("");
				for(int j=0;j<ids.length;j++){
					if(j==(ids.length-1)){
						table_no.append("'"+ids[j]+"'");
					}else{
						table_no.append("'"+ids[j]+"',");
					}
				}
				//拼根节点字符串
				INTERFACE_NAME=(i+1)+"."+INTERFACE_NAME;
				content.append("<div title='"+INTERFACE_NAME+"' style='padding:10px' data-options=' selected:true,tools:[{iconCls:\"icon-reload\",handler:function(){$(\"#dg\").datagrid(\"reload\");}}]'>");
				//拼叶子节点
				content.append("<table id=\'dg\' class=\'easyui-datagrid\' data-options=\'url:\"datagrid_data1.json\",method:\"get\",fit:true,fitColumns:true,singleSelect:true\'>");
				content.append("<thead><tr><th data-options=\'field:\"序号\",width:50\'>序号</th>");
				content.append("<th data-options=\'field:\"表中文名\",width:150\'>表中文名</th>");
				content.append("<th data-options=\'field:\"表英文名\",width:150\'>表英文名</th>");
				content.append("<th data-options=\'field:\"所属主题\",width:150\'>所属主题</th></tr></thead>");
				
				List leafNode = new ArrayList();
				leafNode = getShare_Table(table_no.toString());
				for(int k=0;k<leafNode.size();k++){
					Map leafMap = (Map) leafNode.get(k);
					String name_cn = "-";
					if(leafMap.get("TABLE_NAME_CN")!=null){
						name_cn = leafMap.get("TABLE_NAME_CN").toString();
					}
						
					
					String name_en = leafMap.get("TABLE_NAME_EN").toString();
					String topic_name = "-";
					if(leafMap.get("TOPICS_NAME")!=null){
						topic_name = leafMap.get("TOPICS_NAME").toString();
					}
					
					
					content.append("<tr>");
					content.append("<td style=\'width:50\'>"+(k+1)+"</td>");
					content.append("<td style=\'width:150\'>"+name_cn+"</td>");
					content.append("<td style=\'width:150\'>"+name_en+"</td>");
					content.append("<td style=\'width:150\'>"+topic_name+"</td>");
					content.append("</tr>");
				}
				
				content.append("</table></div>");
				
				
			}
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("-------start----");
		System.out.println("content=="+content.toString());
		System.out.println("-------end----");
		return content.toString();
	}

}
