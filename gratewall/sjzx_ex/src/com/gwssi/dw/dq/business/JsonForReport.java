package com.gwssi.dw.dq.business;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import com.gwssi.dw.dq.DqContants;
import com.gwssi.dw.dq.dao.JsonDao;
import com.gwssi.dw.dq.reportModel.DqReport;
import com.gwssi.dw.dq.reportModel.DqReportReader;
import com.gwssi.dw.dq.reportModel.DqReportWriter;

/**
 * 用于Report展现前的JSON对象相关处理(格式转换)
 * @version 1.0
 * @author FlyFish
 */
public class JsonForReport
{
	/**
	 * 记录主栏中所有group对应的实体ID
	 */
	private List	zlGroupIdList	= new ArrayList();

	/**
	 * 记录宾栏中每个block中所有的group对应的ID，换block清空
	 */
	private List	blGroupIdList	= new ArrayList();

	/**
	 * 用于产生JSON对象的String
	 */
	private String	jsonStr			= "{zl:{blocks:[{id:'block_1',items:[{type:'group',id:'10002',text:'收入支出',groupItems:[{id:'1000201',text:'支出'},{id:'1000202',text:'收入'}]}]},{id:'block_3',items:[{type:'group',id:'10003',text:'营业成本',groupItems:[{id:'1000201',text:'支出'},{id:'1000202',text:'收入'}]}]}]},bl:{blocks:[{id:'block_3',items:[{type:'targets',id:'block_3_targets',text:'指标集合',targets:[{id:'1000201',text:'户数'},{id:'1000202',text:'注册资金总额'}]},{type:'group',id:'10003',text:'营业成本',groupItems:[{id:'1000201',text:'支出'},{id:'1000202',text:'收入'}]}]}]}}";
	
	private JsonDao jsonDao;
	
	private String title = "";
	
	private Map jsonMap;
	
	private ParamXMLAnalyze paramAnalyze;
	
	public JsonForReport()
	{
		super();
		this.jsonDao = new JsonDao();
	}

	public JsonForReport(String jsonStr, InputStream is)
	{
		super();
		this.jsonStr = jsonStr;
		this.jsonDao = new JsonDao();
		this.paramAnalyze = new ParamXMLAnalyze(is);
		
		this.parseJsonStr();
	}

	/**
	 * 将页面传来的JSON字符串转换成报表展现需要的JSON格式
	 * @return 转换后的JSON字符串
	 */
	public String queryJSON2String() {
		return JSONObject.fromObject(this.jsonMap).toString();
	}
	
	private void parseJsonStr(){
		JSONObject a = JSONObject.fromObject(jsonStr);
		//从前台传过来的Map
		Map oldMap = (Map)a;
		//准备传送到报表的Map
		Map newMap = new HashMap();
		if (oldMap.get("title") != null)
			title = (String)oldMap.get("title");
		Map blMap = (Map)oldMap.get("bl");
		Map newBlMap = new HashMap();
		List blBlocksList = (List)blMap.get("blocks");
		List newBlBlocksList = new ArrayList();
		
		Map zlMap = (Map)oldMap.get("zl");
		Map newZlMap = new HashMap();
		List zlBlocksList = (List)zlMap.get("blocks");
		List newZlBlocksList = new ArrayList();
		
		Map oldBlockMap = null; 
		//新的block对象
		Map newBlockMap = null;
		//读取items数组
		List itemsList = null;
		//新的items数组
		List newItemsList = null;
		Map newItemMap = null;
		Map itemMap = null;
		
		//处理主栏
		for (int i=0;i < zlBlocksList.size();i++) {
			oldBlockMap = (Map)zlBlocksList.get(i); //只可能是group
			newBlockMap = new HashMap();
			itemsList = (List)oldBlockMap.get("items");
			newItemsList = new ArrayList();
			for (int j=0;itemsList != null && j < itemsList.size();j++) {
				newItemMap = new HashMap();
				itemMap = (Map)itemsList.get(j);
				newItemMap.put("type", "group");
				newItemMap.put("group", transformGroup(itemMap, "zl")); //对group进行查询、转换
				newItemsList.add(newItemMap); //将item添加到items数组中
			}
			newBlockMap.put("items", newItemsList);
			newZlBlocksList.add(newBlockMap);
		}
		newZlMap.put("blocks", newZlBlocksList); //zl对象至此产生
		newMap.put("zl", newZlMap);
		
		//处理宾栏
		for (int i=0;i < blBlocksList.size();i++) {
			oldBlockMap = (Map)blBlocksList.get(i); //可能是group,也可能是target
			newBlockMap = new HashMap();
			itemsList = checkNull((List)oldBlockMap.get("items"));
			newItemsList = new ArrayList();
			int orderIndex = 0;  //用于记录指标集在block中的位置
			List tempList = new ArrayList(); //用于暂时存放指标集的
			this.blGroupIdList.clear(); //每个block对group的ID列表重置
			for (int j=0;itemsList != null && j < itemsList.size();j++) {
				newItemMap = new HashMap();
				itemMap = (Map)itemsList.get(j);
				String type = checkNull((String)itemMap.get("type"));
				if (type.equals("group")) {
					newItemMap.put("type", "group");
					newItemMap.put("group", transformGroup(itemMap, "bl")); //对group进行查询、转换
					newItemsList.add(newItemMap); //将item添加到items数组中
				}
				else if (type.equals("targets")) {
					orderIndex = j;
					tempList.add(itemMap);
				}
			}
			//处理bl的指标集
			if (tempList.size() > 0) { //一个block里只允许出现一个指标集
				newItemMap = new HashMap();
				itemMap = checkNull((Map)tempList.get(0));
				List targetsList = checkNull((List)itemMap.get("targets"));
				newItemMap.put("type", "targets");
				newItemMap.put("text", checkNull((String)itemMap.get("text")));
				String conds = transformConds((Map)itemMap.get("conds"));
				newItemMap.put("targets", transformTargets(targetsList, conds)); //对targets数组进行查询、转换
				if (!conds.equals("")) {
					newItemMap.put("conds", "(" + conds + ")");
				}
				newItemsList.add(orderIndex, newItemMap);
			}
			newBlockMap.put("items", newItemsList);
			newBlBlocksList.add(newBlockMap);
		}
		newBlMap.put("blocks", newBlBlocksList); //bl对象至此产生
		newMap.put("bl", newBlMap);
		newMap.put("title", title);
		this.jsonDao.freeConnection();
		
		this.jsonMap = newMap;
	}
	
	public DqReport getDqReport(){
		//后台生成报表html
//		System.out.println("jsonOut:\t" + JSONObject.fromObject(jsonMap).toString());
		DqReport dqReport = DqReportReader.getInstance().readFromMap(this.jsonMap);
		String html = new DqReportWriter(dqReport).getHtml();
		this.jsonMap.put("html", html);
		String jsons = this.queryJSON2String();
		dqReport.setJsons(jsons);
		return dqReport;
	}
	
	/**
	 * 对原始的group对象进行更新,并将group对应的ID添加到List中
	 * @param oldGroupMap group项
	 * @param type "bl"、"zl",仅代表group的位置
	 * @return
	 */
	private Map transformGroup(Map oldGroupMap, String type) {
		Map newGroupMap = new HashMap(); //新的groupMap
		boolean isInput = false;//标识group是否是Input类型
		String id = (String)oldGroupMap.get("id");
		String dataId = checkNull((String)oldGroupMap.get("dataId"));
		if (type.equals("bl")) { //将分组Id添加到对应list中
			this.blGroupIdList.add(id);
		}
		else {
			this.zlGroupIdList.add(id);
		}
		String text = checkNull((String)oldGroupMap.get("text"));
		String show = checkNull((String)oldGroupMap.get("show"));
		List groupParamsList = checkNull((List)oldGroupMap.get("groupParams"));
//		String dataType = checkNull(getGroupDataType(id)); //分组的数据类型
		String field = checkNull(getColumnName(id)); //分组的字段名称
		List groupItemsList = checkNull((List)oldGroupMap.get("groupItems"));
		List newGroupItemsList = new ArrayList();
		newGroupMap.put("id", id);
		newGroupMap.put("text", text);
		//是否显示代码
		if (show.equals("1")) {
			newGroupMap.put("show", "mixed");
		}
		else if (show.equals("0")) {
			newGroupMap.put("show", "text");
		}
		
		for (int i=0;groupItemsList != null && i < groupItemsList.size();i++) {
			Map groupItem = (Map)groupItemsList.get(i);
			Map newGroupItem = new HashMap();
			String itemId = checkNull((String)groupItem.get("id"));
			String itemText = checkNull((String)groupItem.get("text"));
			if (show.equals("1")) {
				itemText = "(" + itemId + ")" + itemText;
			}
			newGroupItem.put("text", itemText); //item中的text
			String itemCond = (String)groupItem.get("cond");
			String itemValue1 = (String)groupItem.get("value1");
			String itemValue2 = (String)groupItem.get("value2");
			String[] values = null;
			if (itemCond != null)
				isInput = true;
			if (isInput) { 
				//input类型cond(条件)的生成
				StringBuffer cond = new StringBuffer("");
				cond.append(field);
				if (itemCond.equals("<")) {
					cond.append(" < ? ");
					values = new String[1];
					values[0] = itemValue1;
				}
				else if (itemCond.equals(">")) {
					cond.append(" > ? ");
					values = new String[1];
					values[0] = itemValue1;
				}
				else if (itemCond.equals("like")) {
					cond.append(" like ? ");
					values = new String[1];
					values[0] = "%" + itemValue1 + "%";
				}
				else if (itemCond.equals("between")) {
					cond.append(" between ? and ? ");
					values = new String[2];
					values[0] = itemValue1;
					values[1] = itemValue2;
				}
				newGroupItem.put("cond", cond.toString());
				newGroupItem.put("values", values);
			}
			else {
				//select类型
				List itemParamsList = checkNull((List)groupItem.get("params"));
				Map cond = getCondition(id, itemId, dataId, itemParamsList, groupParamsList);
				if (cond.get("outer") != null) {  //自定义分组项的outer字段
					newGroupItem.put("outer", cond.get("outer"));
				}
				newGroupItem.put("cond", cond.get("cond"));
				newGroupItem.put("values", cond.get("values"));
			}
			newGroupItemsList.add(newGroupItem);
		}
		if (isInput) 
			newGroupMap.put("type", "input");
		newGroupMap.put("groupItems", newGroupItemsList);
		
		return newGroupMap;
	}
	
	/**
	 * 对target指标数组进行更新
	 * @param targetsList 指标数组
	 * @param conds 附加条件
	 * @return
	 */
	private List transformTargets(List targetsList, String conds) {
		String fromStr = ""; //targets数组中都是同一个from
		List newTargetsList = new ArrayList();
		for (int i=0;targetsList != null && i < targetsList.size();i++) {
			Map newTargetMap = new HashMap();
			Map targetMap = (Map)targetsList.get(i);
			String id = (String)targetMap.get("id");
			String text = (String)targetMap.get("text");
			newTargetMap.put("id", id);
			newTargetMap.put("text", text);
			Map queryMap = getTargetMap(id);
			newTargetMap.put("query", queryMap.get("TARGET_FORMULA"));
			if (i == 0)  //仅在第一次运行
				fromStr = generateFromStr(checkNull((String)queryMap.get("ENTITY_ID")), conds);
			newTargetMap.put("from", fromStr);
			newTargetsList.add(newTargetMap);
		}
		return newTargetsList;
	}
	
	private String transformConds(Map item) {
		if (item == null) {
			return "";
		}
		List condList = checkNull((List)item.get("cond"));
		StringBuffer condStr = new StringBuffer();
		this.title = checkNull((String)item.get("name")) + this.title;
		for (int i=0;i < condList.size();i++) {
			Map map = (Map)condList.get(i);
			if (i != 0) {
				condStr.append((String)map.get("logicOpr"));
				condStr.append(" ");
			}
			condStr.append((String)map.get("cond"));
			condStr.append(" ");
		}
		System.out.println("condStr:" + condStr.toString());
		return condStr.toString();
	}
	
	/**
	 * 通过判断，从数据库中读出预定义SQL条件或者自定义SQL条件
	 * @param groupId
	 * @param groupItemId
	 * @return
	 */
	private Map getCondition(String groupId, String groupItemId, String dataId, List itemParamsList, List groupParamsList) {
		String sql = "select a.ISCUSTOM,a.FIELD from DQ_GROUP a " +
				"where a.GROUP_ID='" +
				groupId +
				"' ";
		Map tempMap = jsonDao.queryOne(sql, DqContants.CON_TYPE);
		String isCustomFlag = (String)tempMap.get("ISCUSTOM");
		Map map;
		if (isCustomFlag.equals("0")) { //不使用自定义SQL条件
			map = getPrevCondition(dataId, groupItemId, (String)tempMap.get("FIELD"));
		}
		else { //使用自定义SQL条件
			map = getCustomCondition(groupId, groupItemId, itemParamsList, groupParamsList);
		}
		return map;
	}

	/**
	 * 在DQ_PREV_ITME_COND表中，通过分组ID和分组项ID，来获得预定义SQL条件
	 * @param dataId 条件来源的分组ID,非是本来的分组ID
	 * @param groupItemId 分组项ID
	 * @param field 实体表的字段名
	 * @return PREV_CONDITION 预定义分组条件
	 */
	private Map getPrevCondition(String dataId, String groupItemId, String field) {
		String[] idArray = dataId.split(",");
		StringBuffer id = new StringBuffer();
		id.append("(");
		for(int i=0,len=idArray.length;i < len;i++) {
			id.append("'" + idArray[i] + "'");
			if (i != len-1) 
				id.append(",");
		}
		id.append(")");
		String sql = "select cond.PREV_CONDITION from DQ_PREV_ITME_COND cond " +
				"where cond.MAP_GROUP_ID in " +
				id.toString() +
				" and cond.MAP_GROUPITEM_ID='" +
				groupItemId +
				"'";
		List values = new ArrayList();
		String pattern = "\\{GROUPITEM\\}"; //替换{GROUPITEM}
		String cond = checkNull(jsonDao.queryPrevCondition(sql, DqContants.CON_TYPE));
		Matcher matcher = Pattern.compile(pattern).matcher(cond);
		cond = matcher.replaceAll(field);
		pattern = "(\\[)[^(\\])]*(\\])"; //替换如[]的常量
		matcher = Pattern.compile(pattern).matcher(cond);
		while (matcher.find()) {
			String temp = matcher.group(); //['xxxx']
			temp = temp.substring(1,temp.length()-1); //'xxxx' or xxxx
			values.add(temp);
		}
		Map map = new HashMap();
		map.put("cond", "(" + matcher.replaceAll("?") + ")");
		map.put("values", values);
		return map;
	}
	
	/**
	 * 在DQ_CUSTOM_ITEM表中，通过分组ID和自定义分组项ID，来获得自定义SQL条件
	 * @param groupId 分组ID
	 * @param groupItemId 自定义分组项ID
	 * @return
	 */
	private Map getCustomCondition(String groupId, String groupItemId, List itemParamsList, List groupParamsList) {
		String sql = "select cond.CUSTOM_ITEM_COND,cond.CUSTOM_ITEM_OUTER from DQ_CUSTOM_ITEM cond " +
				"where cond.GROUP_ID='" +
				groupId +
				"' and cond.CUSTOM_ITEM_ID='" +
				groupItemId +
				"' ";
		Map dataMap = jsonDao.queryOne(sql, DqContants.CON_TYPE);
		String cond = checkNull((String)dataMap.get("CUSTOM_ITEM_COND"));
		String outer = checkNull((String)dataMap.get("CUSTOM_ITEM_OUTER"));
		List values = new ArrayList();
		String pattern = "(\\{|\\[)[^(\\}|\\])]*(\\}|\\])"; //替换如['']或者{''}型的值
		Matcher matcher =Pattern.compile(pattern).matcher(cond);
		while (matcher.find()) {
			String temp = matcher.group(); //['xxxx'] or {xxxx}
			if (temp.indexOf("[") < 0) { //为{}型,是参数
				temp = temp.substring(1,temp.length()-1); //'xxxx' or xxxx
				values.add(transParam(temp,itemParamsList, groupParamsList));
			}
			else { //为[]型,是常量
				values.add(temp.substring(1,temp.length()-1));
			}
		}
		Map map = new HashMap();
		if (!outer.equals("")) {
			map.put("outer", outer);
			map.put("cond", "dm.jcsjfx_dm is null" + " and (" + matcher.replaceAll("?") + ")");
		}
		else {
			map.put("cond", "(" + matcher.replaceAll("?") + ")");
		}
		map.put("values", values);
		return map;
	}
	
	/**
	 * 从页面传过来的参数列表中,用输入值来替换condition中传过来的参数名称
	 * @param paramName
	 * @param paramsList
	 * @return
	 */
	private String transParam(String paramName, List itemParamsList, List groupParamsList) {
		for (int i=0;i < itemParamsList.size();i++) {
			Map paramMap = (Map)itemParamsList.get(i);
			if (((String)paramMap.get("PARAMETER_NAME")).equals(paramName)) {
				String type = (String)paramMap.get("PARAMETER_TYPE");
				if (type.equals("1")) { //输入参数
					String dataType = (String)paramMap.get("DATATYPE");
					String value = getInputParamValue(paramName, groupParamsList);
					if (checkDataType(dataType)) { //为string
						return value;
					}
					else { //为integer,date等
						return value;
					}
				}
				else { //固定参数
					String dataType = (String)paramMap.get("DATATYPE");
					if (checkDataType(dataType)) { //为string
						return paramAnalyze.analyzeParam((String)paramMap.get("PARAMETER_EXP"));
					}
					else { //为integer,date等
						return paramAnalyze.analyzeParam((String)paramMap.get("PARAMETER_EXP"));
					}
				}
			}
		}
		return null;
	}
	
	private String getInputParamValue(String paramName, List groupParamsList) {
		for (int i=0;i < groupParamsList.size();i++) {
			Map map = (Map)groupParamsList.get(i);
			if (paramName.equals((String)map.get("PARAMETER_NAME"))) {
				return (String)map.get("PARAMETER_EXP");
			}
		}
		return null;
	}
	
	/**
	 * 分组中获得对应字段,列名
	 * @param groupId 分组ID
	 * @return
	 */
	private String getColumnName(String groupId) {
		String sql = "select a.FIELD from DQ_GROUP a " +
				"where a.GROUP_ID='" +
				groupId +
				"' ";
		Map tempMap = jsonDao.queryOne(sql, DqContants.CON_TYPE);
		return (String)tempMap.get("FIELD");
	}
	
	/**
	 * 分组中获得数据类型
	 * @param groupId 分组ID
	 * @return
	 */
	private String getGroupDataType(String groupId) {
		String sql = "select a.DATATYPE from DQ_GROUP a " +
				"where a.GROUP_ID='" +
				groupId +
				"' ";
		Map tempMap = jsonDao.queryOne(sql, DqContants.CON_TYPE);
		return (String)tempMap.get("DATATYPE");
	}
	
	/**
	 * 在DQ_TARGET表中，通过指标ID来获得相应的 计算公式
	 * @param targetId 指标ID
	 * @return TARGET_FORMULA,ENTITY_ID 计算公式和实体ID的Map
	 */
	private Map getTargetMap(String targetId) {
		String sql = "select tar.TARGET_FORMULA,tar.ENTITY_ID from DQ_TARGET tar " +
				"where tar.TARGET_ID='" +
				targetId +
				"'";
		return jsonDao.queryOne(sql, DqContants.CON_TYPE);
	}
	
	/**
	 * 生成targets中的from字符串
	 * @param singleEntityId 指标对应的实体ID
	 * @return
	 */
	private String generateFromStr(String singleEntityId, String conds) { //指标为主,分组为辅,conds为额外的sql条件，现需要提取相关表和条件
		String fromStr = "";
		StringBuffer allGroupId = new StringBuffer(); //用于产生SQL语句中类似('','','')的部分
		StringBuffer allTableName = new StringBuffer();
		
		List tableNameList = new ArrayList();  //额外的SQL条件中的实体表名列表
		String pattern = "([(\\s)]*)([^\\s]*)(\\.)"; //查找table的名称
		Matcher matcher =Pattern.compile(pattern).matcher(conds);
		while (matcher.find()) {
			String temp = matcher.group(2);
			tableNameList.add(temp);
		}
		allTableName.append("(");
		for (int i=0;i < tableNameList.size();i++) {
			allTableName.append("'" + (String)tableNameList.get(i) + "'");
			if (i != tableNameList.size() - 1)
				allTableName.append(",");
		}
		allTableName.append(")");
		
		allGroupId.append("(");
		//将主栏所有block分组的ID和宾栏当前block的分组ID合并
		List tempList = new ArrayList();
		tempList.addAll(this.zlGroupIdList);
		tempList.addAll(this.blGroupIdList);
		for (int i=0;i < tempList.size();i++) {
			allGroupId.append("'" + (String)tempList.get(i) + "'");
			if (i != tempList.size() - 1)
				allGroupId.append(",");
		}
		allGroupId.append(")");
		String sql = "select distinct(b.ENTITY_TABLE),b.ENTITY_ID from DQ_ENTITY b " +
				"where b.ENTITY_ID in ";
		sql += "(select distinct(a.ENTITY_ID) from DQ_GROUP a " +
				"where a.GROUP_ID in " +
				allGroupId +
				")";
		if (!allTableName.toString().equals("()")) {
			sql += " or b.ENTITY_TABLE in " + allTableName;
		}
		List groupTableNameList = jsonDao.queryAll(sql, DqContants.CON_TYPE); //所有范围内的group对应的实体表名集
		sql = "select a.ENTITY_TABLE,a.ENTITY_ID from DQ_ENTITY a " +
				"where a.ENTITY_ID='" +
				singleEntityId +
				"'";
		Map targetTableNameMap = jsonDao.queryOne(sql, DqContants.CON_TYPE); //此target对应的实体表名
		String targetTableName = checkNull((String)targetTableNameMap.get("ENTITY_TABLE"));
		String targetEntityId = (String)targetTableNameMap.get("ENTITY_ID");
		fromStr = targetTableName;
		for (int i=0;groupTableNameList != null && i < groupTableNameList.size();i++) {
			Map groupTableNameMap = (Map)groupTableNameList.get(i);
			String groupTableName = (String)groupTableNameMap.get("ENTITY_TABLE");
			String groupEntityId = (String)groupTableNameMap.get("ENTITY_ID");
			if (targetTableName.equals(groupTableName))
				continue;
			else {
				fromStr += " left join " + groupTableName;
				//查询两个实体间的关联关系
				sql = "select a.CONDITION from DQ_ENTITY_FOREIGN a " +
						"where a.FOREIGN_ENTITY_ID='" +
						groupEntityId +
						"' and a.ENTITY_ID='" +
						targetEntityId +
						"'";
				Map foreignEntityMap = jsonDao.queryOne(sql, DqContants.CON_TYPE);
				String condition = checkNull((String)foreignEntityMap.get("CONDITION"));
				fromStr += " on " + condition;
			}
		}
		List subjoinList = new ArrayList();
		if (!allTableName.toString().equals("()")) {
			//查询附加表
			sql = "select distinct(a.SUBJOIN_TABLE),a.CONDITION from DQ_SUBJOIN a where a.ENTITY_ID='" +
					singleEntityId +
					"' and a.SUBJOIN_TABLE in " +
					allTableName;
			subjoinList = checkNull(jsonDao.queryAll(sql, DqContants.CON_TYPE));
		}
		for (int i=0;i < subjoinList.size();i++) {
			Map subjoinMap = (Map)subjoinList.get(i);
			fromStr += " left join " + (String)subjoinMap.get("SUBJOIN_TABLE");
			fromStr += " on " + (String)subjoinMap.get("CONDITION");
		}
		return fromStr;
	}
	
	private Map checkNull(Map temp) {
		if (temp == null)
			return new HashMap();
		else
			return temp;
	}
	
	private String checkNull(String temp) {
		if (temp == null)
			return "";
		else
			return temp;
	}
	
	private List checkNull(List temp) {
		if (temp == null)
			return new ArrayList();
		else
			return temp;
	}
	
	/**
	 * 检验group中dataType是否是string,是string返回true,否则返回false
	 * @param dataType
	 * @return
	 */
	private boolean checkDataType(String dataType) {
		if (dataType.equals("字符")) {
			return true;
		}
		else {
			return false;
		}
	}

	public List getZlGroupIdList()
	{
		return zlGroupIdList;
	}

	public void setZlGroupIdList(List zlGroupIdList)
	{
		this.zlGroupIdList = zlGroupIdList;
	}

	public List getBlGroupIdList()
	{
		return blGroupIdList;
	}

	public void setBlGroupIdList(List blGroupIdList)
	{
		this.blGroupIdList = blGroupIdList;
	}

}
