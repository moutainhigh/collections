<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.stat.StatAnalysisTool"%>
<%@ page import="com.trs.components.stat.IStatResult"%>
<%@ page import="com.trs.components.wcm.resource.Status"%>

<%@ page import="com.trs.infra.util.CMyString"%>
<%@ page import="com.trs.infra.util.CMyDateTime"%>
<%@include file="../../include/public_server.jsp"%>
<%@include file="../include/static_common.jsp"%>
<%
	// 构造统计的SQL语句
	String[] pStatSQL = new String[] {
		// 获取某一段时间创建的不同文档类型的总数
		"Select count(*) DataCount, DocStatus from WCMChnlDoc "
				// 统计指定时间段的数据
				+ " Where CrTime>= ${STARTTIME}"
				+ " And CrTime<= ${ENDTIME}"
				+ " and DocStatus>0 and ChnlId>0"
				+ " and DocStatus != " + Status.STATUS_ID_DRAFT
				//排除被删除文档的引用
				+ " and Modal>0"
				+ " Group by DocStatus"
		};
	//对应的参数列表
	String[] arrTime=getDateTimeFromParame(currRequestHelper);
	StatAnalysisTool tool = new StatAnalysisTool(pStatSQL);
	IStatResult result = tool.stat(arrTime[0], arrTime[1]);
	// 获取结果集主体对象，这里的是DocStatus
	List mainObjList = result.list();

	StringBuffer elements = new StringBuffer(500);
	StringBuffer color = new StringBuffer(500);
	for(int i=0;i<mainObjList.size();i++){
		Status  status = Status.findById((Integer.parseInt(mainObjList.get(i).toString())));
		if(status==null)continue;
		String sName = status.getDisp();
		// 获取统计结果
		int num = result.getResult(String.valueOf(mainObjList.get(i)));
		// 构造元素信息
		elements.append(",{\"value\":"+num+",\"label\":\""+sName+"("+num+")\"}");
		color.append(","+getRandomColor());
	}
	// 获取当前flash的描述信息
	String sFlashDesc = currRequestHelper.getString("desc");
	out.clear();
	String sColors=color.length()>0?color.substring(1):"";
	String sElements = elements.length()>0?elements.substring(1):"";
	out.println(getPieChartJson(sFlashDesc,sColors,sElements));
%>