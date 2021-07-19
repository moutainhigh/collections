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
 * ����Reportչ��ǰ��JSON������ش���(��ʽת��)
 * @version 1.0
 * @author FlyFish
 */
public class JsonForReport
{
	/**
	 * ��¼����������group��Ӧ��ʵ��ID
	 */
	private List	zlGroupIdList	= new ArrayList();

	/**
	 * ��¼������ÿ��block�����е�group��Ӧ��ID����block���
	 */
	private List	blGroupIdList	= new ArrayList();

	/**
	 * ���ڲ���JSON�����String
	 */
	private String	jsonStr			= "{zl:{blocks:[{id:'block_1',items:[{type:'group',id:'10002',text:'����֧��',groupItems:[{id:'1000201',text:'֧��'},{id:'1000202',text:'����'}]}]},{id:'block_3',items:[{type:'group',id:'10003',text:'Ӫҵ�ɱ�',groupItems:[{id:'1000201',text:'֧��'},{id:'1000202',text:'����'}]}]}]},bl:{blocks:[{id:'block_3',items:[{type:'targets',id:'block_3_targets',text:'ָ�꼯��',targets:[{id:'1000201',text:'����'},{id:'1000202',text:'ע���ʽ��ܶ�'}]},{type:'group',id:'10003',text:'Ӫҵ�ɱ�',groupItems:[{id:'1000201',text:'֧��'},{id:'1000202',text:'����'}]}]}]}}";
	
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
	 * ��ҳ�洫����JSON�ַ���ת���ɱ���չ����Ҫ��JSON��ʽ
	 * @return ת�����JSON�ַ���
	 */
	public String queryJSON2String() {
		return JSONObject.fromObject(this.jsonMap).toString();
	}
	
	private void parseJsonStr(){
		JSONObject a = JSONObject.fromObject(jsonStr);
		//��ǰ̨��������Map
		Map oldMap = (Map)a;
		//׼�����͵������Map
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
		//�µ�block����
		Map newBlockMap = null;
		//��ȡitems����
		List itemsList = null;
		//�µ�items����
		List newItemsList = null;
		Map newItemMap = null;
		Map itemMap = null;
		
		//��������
		for (int i=0;i < zlBlocksList.size();i++) {
			oldBlockMap = (Map)zlBlocksList.get(i); //ֻ������group
			newBlockMap = new HashMap();
			itemsList = (List)oldBlockMap.get("items");
			newItemsList = new ArrayList();
			for (int j=0;itemsList != null && j < itemsList.size();j++) {
				newItemMap = new HashMap();
				itemMap = (Map)itemsList.get(j);
				newItemMap.put("type", "group");
				newItemMap.put("group", transformGroup(itemMap, "zl")); //��group���в�ѯ��ת��
				newItemsList.add(newItemMap); //��item��ӵ�items������
			}
			newBlockMap.put("items", newItemsList);
			newZlBlocksList.add(newBlockMap);
		}
		newZlMap.put("blocks", newZlBlocksList); //zl�������˲���
		newMap.put("zl", newZlMap);
		
		//�������
		for (int i=0;i < blBlocksList.size();i++) {
			oldBlockMap = (Map)blBlocksList.get(i); //������group,Ҳ������target
			newBlockMap = new HashMap();
			itemsList = checkNull((List)oldBlockMap.get("items"));
			newItemsList = new ArrayList();
			int orderIndex = 0;  //���ڼ�¼ָ�꼯��block�е�λ��
			List tempList = new ArrayList(); //������ʱ���ָ�꼯��
			this.blGroupIdList.clear(); //ÿ��block��group��ID�б�����
			for (int j=0;itemsList != null && j < itemsList.size();j++) {
				newItemMap = new HashMap();
				itemMap = (Map)itemsList.get(j);
				String type = checkNull((String)itemMap.get("type"));
				if (type.equals("group")) {
					newItemMap.put("type", "group");
					newItemMap.put("group", transformGroup(itemMap, "bl")); //��group���в�ѯ��ת��
					newItemsList.add(newItemMap); //��item��ӵ�items������
				}
				else if (type.equals("targets")) {
					orderIndex = j;
					tempList.add(itemMap);
				}
			}
			//����bl��ָ�꼯
			if (tempList.size() > 0) { //һ��block��ֻ�������һ��ָ�꼯
				newItemMap = new HashMap();
				itemMap = checkNull((Map)tempList.get(0));
				List targetsList = checkNull((List)itemMap.get("targets"));
				newItemMap.put("type", "targets");
				newItemMap.put("text", checkNull((String)itemMap.get("text")));
				String conds = transformConds((Map)itemMap.get("conds"));
				newItemMap.put("targets", transformTargets(targetsList, conds)); //��targets������в�ѯ��ת��
				if (!conds.equals("")) {
					newItemMap.put("conds", "(" + conds + ")");
				}
				newItemsList.add(orderIndex, newItemMap);
			}
			newBlockMap.put("items", newItemsList);
			newBlBlocksList.add(newBlockMap);
		}
		newBlMap.put("blocks", newBlBlocksList); //bl�������˲���
		newMap.put("bl", newBlMap);
		newMap.put("title", title);
		this.jsonDao.freeConnection();
		
		this.jsonMap = newMap;
	}
	
	public DqReport getDqReport(){
		//��̨���ɱ���html
//		System.out.println("jsonOut:\t" + JSONObject.fromObject(jsonMap).toString());
		DqReport dqReport = DqReportReader.getInstance().readFromMap(this.jsonMap);
		String html = new DqReportWriter(dqReport).getHtml();
		this.jsonMap.put("html", html);
		String jsons = this.queryJSON2String();
		dqReport.setJsons(jsons);
		return dqReport;
	}
	
	/**
	 * ��ԭʼ��group������и���,����group��Ӧ��ID��ӵ�List��
	 * @param oldGroupMap group��
	 * @param type "bl"��"zl",������group��λ��
	 * @return
	 */
	private Map transformGroup(Map oldGroupMap, String type) {
		Map newGroupMap = new HashMap(); //�µ�groupMap
		boolean isInput = false;//��ʶgroup�Ƿ���Input����
		String id = (String)oldGroupMap.get("id");
		String dataId = checkNull((String)oldGroupMap.get("dataId"));
		if (type.equals("bl")) { //������Id��ӵ���Ӧlist��
			this.blGroupIdList.add(id);
		}
		else {
			this.zlGroupIdList.add(id);
		}
		String text = checkNull((String)oldGroupMap.get("text"));
		String show = checkNull((String)oldGroupMap.get("show"));
		List groupParamsList = checkNull((List)oldGroupMap.get("groupParams"));
//		String dataType = checkNull(getGroupDataType(id)); //�������������
		String field = checkNull(getColumnName(id)); //������ֶ�����
		List groupItemsList = checkNull((List)oldGroupMap.get("groupItems"));
		List newGroupItemsList = new ArrayList();
		newGroupMap.put("id", id);
		newGroupMap.put("text", text);
		//�Ƿ���ʾ����
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
			newGroupItem.put("text", itemText); //item�е�text
			String itemCond = (String)groupItem.get("cond");
			String itemValue1 = (String)groupItem.get("value1");
			String itemValue2 = (String)groupItem.get("value2");
			String[] values = null;
			if (itemCond != null)
				isInput = true;
			if (isInput) { 
				//input����cond(����)������
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
				//select����
				List itemParamsList = checkNull((List)groupItem.get("params"));
				Map cond = getCondition(id, itemId, dataId, itemParamsList, groupParamsList);
				if (cond.get("outer") != null) {  //�Զ���������outer�ֶ�
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
	 * ��targetָ��������и���
	 * @param targetsList ָ������
	 * @param conds ��������
	 * @return
	 */
	private List transformTargets(List targetsList, String conds) {
		String fromStr = ""; //targets�����ж���ͬһ��from
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
			if (i == 0)  //���ڵ�һ������
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
	 * ͨ���жϣ������ݿ��ж���Ԥ����SQL���������Զ���SQL����
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
		if (isCustomFlag.equals("0")) { //��ʹ���Զ���SQL����
			map = getPrevCondition(dataId, groupItemId, (String)tempMap.get("FIELD"));
		}
		else { //ʹ���Զ���SQL����
			map = getCustomCondition(groupId, groupItemId, itemParamsList, groupParamsList);
		}
		return map;
	}

	/**
	 * ��DQ_PREV_ITME_COND���У�ͨ������ID�ͷ�����ID�������Ԥ����SQL����
	 * @param dataId ������Դ�ķ���ID,���Ǳ����ķ���ID
	 * @param groupItemId ������ID
	 * @param field ʵ�����ֶ���
	 * @return PREV_CONDITION Ԥ�����������
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
		String pattern = "\\{GROUPITEM\\}"; //�滻{GROUPITEM}
		String cond = checkNull(jsonDao.queryPrevCondition(sql, DqContants.CON_TYPE));
		Matcher matcher = Pattern.compile(pattern).matcher(cond);
		cond = matcher.replaceAll(field);
		pattern = "(\\[)[^(\\])]*(\\])"; //�滻��[]�ĳ���
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
	 * ��DQ_CUSTOM_ITEM���У�ͨ������ID���Զ��������ID��������Զ���SQL����
	 * @param groupId ����ID
	 * @param groupItemId �Զ��������ID
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
		String pattern = "(\\{|\\[)[^(\\}|\\])]*(\\}|\\])"; //�滻��['']����{''}�͵�ֵ
		Matcher matcher =Pattern.compile(pattern).matcher(cond);
		while (matcher.find()) {
			String temp = matcher.group(); //['xxxx'] or {xxxx}
			if (temp.indexOf("[") < 0) { //Ϊ{}��,�ǲ���
				temp = temp.substring(1,temp.length()-1); //'xxxx' or xxxx
				values.add(transParam(temp,itemParamsList, groupParamsList));
			}
			else { //Ϊ[]��,�ǳ���
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
	 * ��ҳ�洫�����Ĳ����б���,������ֵ���滻condition�д������Ĳ�������
	 * @param paramName
	 * @param paramsList
	 * @return
	 */
	private String transParam(String paramName, List itemParamsList, List groupParamsList) {
		for (int i=0;i < itemParamsList.size();i++) {
			Map paramMap = (Map)itemParamsList.get(i);
			if (((String)paramMap.get("PARAMETER_NAME")).equals(paramName)) {
				String type = (String)paramMap.get("PARAMETER_TYPE");
				if (type.equals("1")) { //�������
					String dataType = (String)paramMap.get("DATATYPE");
					String value = getInputParamValue(paramName, groupParamsList);
					if (checkDataType(dataType)) { //Ϊstring
						return value;
					}
					else { //Ϊinteger,date��
						return value;
					}
				}
				else { //�̶�����
					String dataType = (String)paramMap.get("DATATYPE");
					if (checkDataType(dataType)) { //Ϊstring
						return paramAnalyze.analyzeParam((String)paramMap.get("PARAMETER_EXP"));
					}
					else { //Ϊinteger,date��
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
	 * �����л�ö�Ӧ�ֶ�,����
	 * @param groupId ����ID
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
	 * �����л����������
	 * @param groupId ����ID
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
	 * ��DQ_TARGET���У�ͨ��ָ��ID�������Ӧ�� ���㹫ʽ
	 * @param targetId ָ��ID
	 * @return TARGET_FORMULA,ENTITY_ID ���㹫ʽ��ʵ��ID��Map
	 */
	private Map getTargetMap(String targetId) {
		String sql = "select tar.TARGET_FORMULA,tar.ENTITY_ID from DQ_TARGET tar " +
				"where tar.TARGET_ID='" +
				targetId +
				"'";
		return jsonDao.queryOne(sql, DqContants.CON_TYPE);
	}
	
	/**
	 * ����targets�е�from�ַ���
	 * @param singleEntityId ָ���Ӧ��ʵ��ID
	 * @return
	 */
	private String generateFromStr(String singleEntityId, String conds) { //ָ��Ϊ��,����Ϊ��,condsΪ�����sql����������Ҫ��ȡ��ر������
		String fromStr = "";
		StringBuffer allGroupId = new StringBuffer(); //���ڲ���SQL���������('','','')�Ĳ���
		StringBuffer allTableName = new StringBuffer();
		
		List tableNameList = new ArrayList();  //�����SQL�����е�ʵ������б�
		String pattern = "([(\\s)]*)([^\\s]*)(\\.)"; //����table������
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
		//����������block�����ID�ͱ�����ǰblock�ķ���ID�ϲ�
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
		List groupTableNameList = jsonDao.queryAll(sql, DqContants.CON_TYPE); //���з�Χ�ڵ�group��Ӧ��ʵ�������
		sql = "select a.ENTITY_TABLE,a.ENTITY_ID from DQ_ENTITY a " +
				"where a.ENTITY_ID='" +
				singleEntityId +
				"'";
		Map targetTableNameMap = jsonDao.queryOne(sql, DqContants.CON_TYPE); //��target��Ӧ��ʵ�����
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
				//��ѯ����ʵ���Ĺ�����ϵ
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
			//��ѯ���ӱ�
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
	 * ����group��dataType�Ƿ���string,��string����true,���򷵻�false
	 * @param dataType
	 * @return
	 */
	private boolean checkDataType(String dataType) {
		if (dataType.equals("�ַ�")) {
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
