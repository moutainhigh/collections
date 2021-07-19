<%@ page language="java" pageEncoding="GBK"%>
<jsp:directive.page import="com.gwssi.dw.runmgr.webservices.localtax.in.client.GSDJInfo"/>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Map.Entry" %>
<%@ page import="com.gwssi.dw.runmgr.services.common.Constants" %>
<freeze:html>
<head>
<script language="javascript">
</script>	
</head>

<freeze:body>
<freeze:title caption="测试结果"/>
<%!
	private String compare(String str1, String str2){
		if(str1 == null && str2 == null){
			return "&nbsp;";
		}
		if(str1.equals(str2)){
			return "&nbsp;"+str1;
		}else{
			return "&nbsp;<font color=red>"+str1+"</font>";
		}
	}
%>
<%
	try{
		com.gwssi.dw.runmgr.webservices.localtax.servlet.gs.ServiceSoap_PortType gsService = new com.gwssi.dw.runmgr.webservices.localtax.servlet.gs.ServiceLocator().getServiceSoap();
		com.gwssi.dw.runmgr.webservices.localtax.servlet.zr.ServiceSoap_PortType zrService = new com.gwssi.dw.runmgr.webservices.localtax.servlet.zr.ServiceLocator().getServiceSoap();
		%>
		<table border="0" width="95%" align="center" class="frame-body" cellpadding="0" cellspacing="1">
			<tr class="title-row">
				<td width="5%">方法名称</td>
				<td>返回代码</td>
			</tr>
			<tr class="framerow selectRow">
				<td rowspan="2">连接测试</td>
				<td>
				<% 
					com.gwssi.dw.runmgr.webservices.localtax.servlet.gs.ReturnMultiGSData gsData = gsService.getLJ_Query();
					com.gwssi.dw.runmgr.webservices.localtax.servlet.zr.ReturnMultiGSData zrData = zrService.getLJ_Query();
					out.print(compare(gsData.getFHDM(), zrData.getFHDM()));
				%>
				</td>
			</tr>
			<tr class="framerow selectRow">
				<td>&nbsp;<%= zrData.getFHDM()%></td>
			</tr>
		</table>
		<br>
		<table border="0" width="95%" align="center" class="frame-body" cellpadding="0" cellspacing="1">
			<tr class="title-row">
				<td>方法名称</td>
				<td>类型</td>
				<td>返回代码</td>
				<td>开始记录数</td>
				<td>结束记录数</td>
				<td>总条数</td>
				<td>注册号</td>
				<td>住所</td>
				<td>邮编</td>
				<td>电话</td>
				<td>经营范围</td>
				<td>注册资本</td>
				<td>法定代表人或负责人姓名</td>
				<td>法定代表人或负责人证件类型代码</td>
				<td>法定代表人或负责人身份证号</td>
				<td>管辖区县</td>
				<td>登记注册类型代码</td>
				<td>日期</td>
			</tr>
			<% 
			gsData = gsService.getGSDJ_QUERY("20080220","20080221", "1", "5");
			zrData = zrService.getGSDJ_QUERY("20080220","20080221", "1", "5");
			%>
			<tr class="framerow selectRow">
				<td rowspan="2">查询登记信息</td>
				<td>长软</td>
				<td><%= compare(gsData.getFHDM(), zrData.getFHDM())%></td>
				<td><%= compare(gsData.getKSJLS(), zrData.getKSJLS())%></td>
				<td><%= compare(gsData.getJSJLS(), zrData.getJSJLS())%></td>
				<td><%= compare(gsData.getZTS(), zrData.getZTS())%></td>
				<%com.gwssi.dw.runmgr.webservices.localtax.servlet.gs.GSDJInfo[] gsinfo = gsData.getGSDJ_INFO_ARRAY();
				  com.gwssi.dw.runmgr.webservices.localtax.servlet.zr.GSDJInfo[] zrinfo = zrData.getGSDJ_INFO_ARRAY();
				  if(gsinfo != null && zrinfo != null && gsinfo.length > 0 && zrinfo.length > 0){
				  %>
				    <td><%= compare(gsinfo[0].getYYZZH(), zrinfo[0].getYYZZH())%></td>
					<td><%= compare(gsinfo[0].getZSDZ(), zrinfo[0].getZSDZ())%></td>
					<td><%= compare(gsinfo[0].getYB(), zrinfo[0].getYB())%></td>
					<td><%= compare(gsinfo[0].getDH(), zrinfo[0].getDH()) %></td>
					<td><%= compare(gsinfo[0].getJYFW(), zrinfo[0].getJYFW()) %></td>
					<td><%= compare(gsinfo[0].getZCZB(), zrinfo[0].getZCZB()) %></td>
					<td><%= compare(gsinfo[0].getFRXM(), zrinfo[0].getFRXM()) %></td>
					<td><%= compare(gsinfo[0].getZJLXDM(), zrinfo[0].getZJLXDM()) %></td>
					<td><%= compare(gsinfo[0].getFRSFZH(), zrinfo[0].getFRSFZH()) %></td>
					<td><%= compare(gsinfo[0].getGXQX(), zrinfo[0].getGXQX()) %></td>
					<td><%= compare(gsinfo[0].getDJZCLXDM(), zrinfo[0].getDJZCLXDM()) %></td>
					<td><%= compare(gsinfo[0].getRQ(), zrinfo[0].getRQ()) %></td>
				  <%
				  }else{
				  	%>
				  	<td colspan="12">无符合条件的记录！</td>
				  	<%
				  }
				%>
			</tr>
			<tr class="framerow selectRow">
				<td>中软</td>
				<td>&nbsp;<%=zrData.getFHDM()%></td>
				<td>&nbsp;<%=zrData.getKSJLS()%></td>
				<td>&nbsp;<%=zrData.getJSJLS()%></td>
				<td>&nbsp;<%=zrData.getZTS()%></td>
				<%
				  if(gsinfo != null && zrinfo != null && gsinfo.length > 0 && zrinfo.length > 0){
				  %>
				    <td>&nbsp;<%=zrinfo[0].getYYZZH() %></td>
					<td>&nbsp;<%=zrinfo[0].getZSDZ() %></td>
					<td>&nbsp;<%=zrinfo[0].getYB() %></td>
					<td>&nbsp;<%=zrinfo[0].getDH() %></td>
					<td>&nbsp;<%=zrinfo[0].getJYFW() %></td>
					<td>&nbsp;<%=zrinfo[0].getZCZB() %></td>
					<td>&nbsp;<%=zrinfo[0].getFRXM() %></td>
					<td>&nbsp;<%=zrinfo[0].getZJLXDM() %></td>
					<td>&nbsp;<%=zrinfo[0].getFRSFZH() %></td>
					<td>&nbsp;<%=zrinfo[0].getGXQX() %></td>
					<td>&nbsp;<%=zrinfo[0].getDJZCLXDM() %></td>
					<td>&nbsp;<%=zrinfo[0].getRQ() %></td>
				  <%
				  }else{
				  	%>
				  	<td colspan="12">无符合条件的记录！</td>
				  	<%
				  }
				%>
			</tr>
		</table>
<%
	}catch(Exception e){
		%>
		<table border="0" width="95%" align="center" class="frame-body" cellpadding="0" cellspacing="1">
			<tr class="title-row">
				<td>发生异常：</td>
			</tr>
			<tr class="framerow selectRow">
				<td>异常信息：<%= e.getMessage()%></td>
			</tr>
		</table>
		<%
	}
%>
</freeze:body>
</freeze:html>