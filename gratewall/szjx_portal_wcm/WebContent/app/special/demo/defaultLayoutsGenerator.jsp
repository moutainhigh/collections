<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" errorPage="error.jsp"%>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor"%>
<%@ page import="com.trs.components.common.publish.widget.Layout"%>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="java.util.HashMap"%>
<%@include file="../../include/public_server.jsp"%>
<%
	out.println(LocaleServer.getString("defaultLayoutsGenerator.jsp.label.creating_default_layout", "创建专题默认布局......<br/>"));
	JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
	defaultLayoutsGenerator(processor);
	out.println(LocaleServer.getString("defaultLayoutsGenerator.jsp.label.successs2create_default_layout", "创建专题默认布局成功！"));
%>

<%!
	public Logger logger = Logger.getLogger("sadsad");
	/*
	*	默认数据为二维数组，第一个为比例值，第二个为比例类型
	*/
	String[][] defaultData = new String[][] {{"*","1"},
	{"50:50","2"},{"30:70","2"},{"40:60","2"},{"38:*","2"},{"70:30","2"},{"60:40","2"},{"300:700","1"},{"400:600","1"},
		{"30:30:40","2"},{"33:*:33","2"},{"30:40:30","2"},{"350:400:250","1"},{"500:250:250","1"},
		{"25:25:25:25","2"},{"250:250:250:250","1"}};

	public String sServiceId = "wcm61_layout",sMethodName="save";
	public void layoutGenerator(JSPRequestProcessor processor,String[] data){
		HashMap parameters = new HashMap();
		parameters.put("ObjectId","0");
		parameters.put("LayoutName","");
		parameters.put("Columns",new Integer(data[0].split(Layout.RATIO_SEPERATE).length));
		parameters.put("RatioType",new Integer(data[1]));
		parameters.put("Ratio",data[0]);
		try{
			processor.excute(sServiceId, sMethodName,parameters);
		}catch(Exception e){
			logger.error(CMyString.format(LocaleServer.getString("test.jsp.found", "生成布局默认参数时出现异常！布局信息为：ratio=[{0}]ratiotype={1}!"), new String[]{data[0],data[1]}));
		}
	}
	public void defaultLayoutsGenerator(JSPRequestProcessor processor){
		logger.info(LocaleServer.getString("defaultLayoutsGenerator.jsp.label.creating_default_layout", "创建专题默认布局......<br/>"));
		for(int i =0;i<defaultData.length;i++){
			layoutGenerator(processor,defaultData[i]);
		}
		logger.info(LocaleServer.getString("defaultLayoutsGenerator.jsp.label.successs2create_default_layout", "创建专题默认布局成功！"));
	}
%>