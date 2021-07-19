<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.stat.StatAnalysisTool"%>
<%@ page import="com.trs.components.stat.IStatResult"%>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@ page import="com.trs.infra.util.CMyDateTime"%>
<%@include file="../../include/public_server.jsp"%>
<%@include file="../include/static_common.jsp"%>
<%
	// 构造统计的SQL语句
	String[] pStatSQL = new String[] {
		// 获取某一段时间创建的不同文档类型的总数
		"Select count(*) DataCount, DocForm from WCMChnlDoc "
				// 统计指定时间段的数据
				+ " Where CrTime>= ${STARTTIME}"
				+ " And CrTime<= ${ENDTIME}"
				+ " and DocStatus>0 and CHNLID>0"
				+ " and DocStatus != " + Status.STATUS_ID_DRAFT
				//排除被删除文档的引用
				+ " and Modal>0"
				+ " Group by DocForm"
		};
	//对应的参数列表
	String[] arrTime=getDateTimeFromParame(currRequestHelper);
	StatAnalysisTool tool = new StatAnalysisTool(pStatSQL);
	IStatResult result = tool.stat(arrTime[0], arrTime[1]);
	// 获取结果集主体对象，这里的是DOCFORM
	List mainObjList = result.list();
	// 获取文本类型图片的柱状图	
	StringBuffer elements = new StringBuffer(500);
	StringBuffer xlaybel = new StringBuffer(500);
	int y_max = 0,y_min = Integer.MAX_VALUE,y_step=10;
	for(int i=0;i<mainObjList.size();i++){
		if(mainObjList.get(i) == null) continue;
		String sName = getDOCFormName(Integer.parseInt(mainObjList.get(i).toString()));
		// 统计文档类型，取第1条SQL
		int num = result.getResult(String.valueOf(mainObjList.get(i)));
		//取最大值和最小值
		if(y_max<num) y_max = num;
		if(y_min>num) y_min = num;
		// 构造元素信息
		elements.append(",{\"top\":"+num+",\"tip\":\"文档类型:"+sName+"<br>稿量:#top#\"}");
		xlaybel.append(",\""+sName+"\"");
	}
	String sXlaybel=xlaybel.length()>0?xlaybel.substring(1):"";
	String sElements = elements.length()>0?elements.substring(1):"";
	// 获取当前flash的描述信息
	String sFlashDesc = currRequestHelper.getString("desc");

	// 设置最大值与最小值
	if(y_max > 0) y_step = y_max/10>0?y_max/10:1;
	y_max += y_step;
	response.setHeader("XlabelsNum",String.valueOf((CMyString.split(sXlaybel,",").length)));
	response.setHeader("ChartType","barchart");
	out.clear();
	out.print(getBarChartJson(sFlashDesc,sElements,sXlaybel,y_max,y_step));
%>