<%
/** Title:			template_parser.jsp
 *  Description:
 *		WCM6 操作产生器
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			CH
 *  Created:		2006-11-21 18:16 上午
 *  Vesion:			1.0
 *  Last EditTime:	
 *	Update Logs:
 *
 *  Parameters:
 *		
 *
 */
%>

<%@ page contentType="text/javascript;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp"%>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="java.util.Hashtable" %>
<%@ page import="java.util.Iterator,java.util.Comparator,java.util.Collections" %>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.individuation.*" %>
<%@ page import="com.trs.wcm.config.PageOperators" %>
<%@ page import="com.trs.wcm.config.PageOperator" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.components.common.conjection.service.IComponentEntryConfigService"%>
<%@ page import="com.trs.components.common.conjection.ComponentEntryConfigConstants"%>
<%@ page import="com.trs.components.common.conjection.persistent.EntryConfig"%>
<!------- WCM IMPORTS END ------------>


<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%
//ge gfc add @ 2007-03-15 选件入口的配置
IComponentEntryConfigService configSrv = (IComponentEntryConfigService) DreamFactory
		.createObjectById("IComponentEntryConfigService");
EntryConfig currConfig = configSrv.getTypedEntryConfig(ComponentEntryConfigConstants.TYPE_COMMENT);

out.clear();
out.println("$package('com.trs.wcm');");
out.println("com.trs.wcm.AllOperators = {");

String oprType = request.getParameter("oprType");
String forDefault = request.getParameter("forDefault");//得到默认排序的标志
String where = "";

if(oprType != null){
	where = "oprType=?"	;
}
WCMFilter filter = new WCMFilter("", where, "OprType, OprOrder");

if(oprType != null){//得到某一特定类型的操作命令
	filter.addSearchValues(oprType);
}

String sCurrOprType = null;
int nCurrOprTypeCount = 0;
PageOperators operators = PageOperators.openWCMObjs(null, filter);

ArrayList list = new ArrayList();

//需要进行排序处理
if(forDefault == null){
	Hashtable operatorHT = new Hashtable();//存放操作命令，此操作命令在自定义命令中出现过
	Hashtable ht = new Hashtable();//存放查询出来的自定义命令信息
	{//init block start
		where = "userId=? and paramName=?";
		filter = new WCMFilter("", where, "paramValue", "paramValue,objectIdsValue");
		filter.addSearchValues(loginUser.getId());
		filter.addSearchValues("operator");
		Individuations individuations = Individuations.openWCMObjs(null, filter);	

		for (int i = 0, nSize = individuations.size(); i < nSize; i++) {
			Individuation individuation = (Individuation) individuations.getAt(i);
			if (individuation == null)
				continue;	
			ht.put(individuation.getParamValue(), individuation.getObjectIdsValue());
		}
	}//init block end

	for (int i = 0, nSize = operators.size(); i < nSize; i++) {
		PageOperator operator = (PageOperator) operators.getAt(i);
		if (operator == null)
			continue;	
		if(ht.get(operator.getType()) != null){//进行了个性化定制
			List  operatorsOfType = (List)operatorHT.get(operator.getType());
			if(operatorsOfType == null){
				operatorsOfType = new ArrayList();
				operatorHT.put(operator.getType(), operatorsOfType);
			}
			operatorsOfType.add(operator);
			operatorHT.put(operator.getType() + "__" + operator.getOprKey(), operator);
		}else{
			list.add(operator);
		}
	}

	for (Enumeration e = ht.keys(); e.hasMoreElements();) {
		oprType = (String)e.nextElement();
		List  operatorsOfType = (List)operatorHT.get(oprType);
		if(operatorsOfType == null){
			continue;
		}

		String objectIdsValue = (String)ht.get(oprType);
		final String[] oprKeys = objectIdsValue.split(";")[1].split(":")[1].split(",");
		Collections.sort(operatorsOfType, new Comparator(){
			public int compare(Object obj1, Object obj2){
				PageOperator operator1 = (PageOperator) obj1;
				PageOperator operator2 = (PageOperator) obj2;
				return getIndex(oprKeys,operator1.getOprKey()) - getIndex(oprKeys,operator2.getOprKey());
			}
		});
		list.addAll(operatorsOfType);
/*
		String objectIdsValue = (String)ht.get(oprType);
		String[] oprKeys = objectIdsValue.split(";")[1].split(":")[1].split(",");
		for(int i = 0, nSize = oprKeys.length; i < nSize; i++){
			PageOperator operator = (PageOperator) operatorHT.get(oprType + "__" + oprKeys[i]);
			if(operator == null){
				continue;
			}
			list.add(operator);		
			operatorsOfType.remove(operator);
			operatorHT.remove(oprType + "__" + oprKeys[i]);
		}
		list.addAll(operatorsOfType);
*/
	}
}else{//不需要默认排序
	for (int i = 0, nSize = operators.size(); i < nSize; i++) {
		PageOperator operator = (PageOperator) operators.getAt(i);
		if (operator == null)
			continue;	
		list.add(operator);
	}
}

for (int i = 0, nSize = list.size(); i < nSize; i++) {
	PageOperator operator = (PageOperator) list.get(i);
	if (operator == null)
		continue;

	// Type First
	if (sCurrOprType == null
			|| !sCurrOprType.equalsIgnoreCase(operator.getType())) {
		if (sCurrOprType != null) {
			out.println("    ],");
		}

		sCurrOprType = operator.getType();
		out.println("    \"" + sCurrOprType + "\":");
		out.println("    [");
		nCurrOprTypeCount = 0;
	}
	//	ge gfc add @ 2007-03-15
	String sKey = operator.getOprKey();
	//System.out.println(sKey + ", " + currConfig.isEnable());
	if (sKey.equalsIgnoreCase("commentmgr") && !currConfig.isEnable())
		continue;
	
	if(nCurrOprTypeCount>0){
		out.println("        ,");
	}
	nCurrOprTypeCount++;

	
	out.println("        {\"operKey\":\"" + sKey
			+ "\",\"operName\":\"" + operator.getName()
			+ "\",\"operDesc\":\"" + operator.getDesc()
			+ "\",\"rightIndex\":" + operator.getRightIndex() + "}");
	
	
}
out.println("    ]");

out.println("}");
%>

<%!
	private int getIndex(String[] array, String target){
		for(int i = 0; i < array.length; i++){
			if(array[i].equalsIgnoreCase(target)){
				return i;
			}
		}
		return -1;
	}
%>