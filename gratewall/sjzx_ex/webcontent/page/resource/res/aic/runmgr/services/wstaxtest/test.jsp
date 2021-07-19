<%@ page language="java" import="java.lang.reflect.Method" contentType="text/html;charset=GBK"  pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<jsp:directive.page import="java.util.Enumeration"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<freeze:html>
  <head>
    <title>对比结果</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  <freeze:body>
  <freeze:title caption="查询结果" />
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
	  	String methodName = request.getParameter("methodName");
	  	Class[] paramType = null;//参数类型
	  	String[] args = null;//查询参数
	  	if(methodName.endsWith("INFO")){
	  		paramType = new Class[2];
	  		paramType[0] = String.class;
	  		paramType[1] = String.class;
	  		
	  		String qymc = request.getParameter("qymc");
	  		String yyzzh = request.getParameter("yyzzh");
	  		
	  		args = new String[2];
	  		args[0] = qymc;
	  		args[1] = yyzzh;
	  		
	  	}else if(methodName.endsWith("QUERY")){
	  		paramType = new Class[4];
	  		paramType[0] = String.class;
	  		paramType[1] = String.class;
	  		paramType[2] = String.class;
	  		paramType[3] = String.class;
	  		
	  		String cxrqq = request.getParameter("cxrqq");
	  		String cxrqz = request.getParameter("cxrqq");
	  		String ksjls = request.getParameter("ksjls");
	  		String jsjls = request.getParameter("jsjls");
	  		
	  		args = new String[4];
	  		args[0] = cxrqq;
	  		args[1] = cxrqz;
	  		args[2] = ksjls;
	  		args[3] = jsjls;
	  	}else{
	  		
	  	}
	  	
	  	//查询工商webservice接口
	  	Method gsmethod = gsService.getClass().getMethod(methodName, paramType);
	  	Object gsobj = gsmethod.invoke(gsService, args);
	  	com.gwssi.dw.runmgr.webservices.localtax.servlet.gs.ReturnMultiGSData gsData = (com.gwssi.dw.runmgr.webservices.localtax.servlet.gs.ReturnMultiGSData)gsobj;
		
	  	//查询中科软webservice接口	  	
	  	Method zrmethod = zrService.getClass().getMethod(methodName, paramType);
	  	Object zrobj = zrmethod.invoke(zrService, args);
		com.gwssi.dw.runmgr.webservices.localtax.servlet.zr.ReturnMultiGSData zrData = (com.gwssi.dw.runmgr.webservices.localtax.servlet.zr.ReturnMultiGSData)zrobj;
  %>
  		<table border="0" width="95%" align="center" class="frame-body" cellpadding="0" cellspacing="1">
			<tr class="title-row">
				<td nowrap>序号</td>
				<td nowrap>方法名称</td>
				<td nowrap>类型</td>
				<td nowrap>返回代码</td>
				<td nowrap>开始记录数</td>
				<td nowrap>结束记录数</td>
				<td nowrap>总条数</td>
				<td nowrap>注册号</td>
				<td nowrap>企业名称</td>
				<td nowrap>住所</td>
				<td nowrap>邮编</td>
				<td nowrap>电话</td>
				<td nowrap>经营范围</td>
				<td nowrap>注册资本</td>
				<td nowrap>法定代表人或负责人姓名</td>
				<td nowrap>法定代表人或负责人证件类型代码</td>
				<td nowrap>法定代表人或负责人身份证号</td>
				<td nowrap>管辖区县</td>
				<td nowrap>登记注册类型代码</td>
				<td nowrap>日期</td>
			</tr>
			<%com.gwssi.dw.runmgr.webservices.localtax.servlet.gs.GSDJInfo[] gsinfo = gsData.getGSDJ_INFO_ARRAY();
			  com.gwssi.dw.runmgr.webservices.localtax.servlet.zr.GSDJInfo[] zrinfo = zrData.getGSDJ_INFO_ARRAY();
			  if(gsinfo != null && zrinfo != null && gsinfo.length > 0 && zrinfo.length > 0){
				  for(int i = 0; i < gsinfo.length; i++){
				  %>
					<tr class="<%= i%2 == 0 ? "evenrow" : "oddrow" %>">
						<td rowspan="2" nowrap><%= i + 1%></td>
						<td rowspan="2" nowrap><%= gsmethod.getName()%></td>
						<td  nowrap>长软</td>
						<td nowrap><%= compare(gsData.getFHDM(), zrData.getFHDM())%></td>
						<td nowrap><%= compare(gsData.getKSJLS(), zrData.getKSJLS())%></td>
						<td nowrap><%= compare(gsData.getJSJLS(), zrData.getJSJLS())%></td>
						<td nowrap><%= compare(gsData.getZTS(), zrData.getZTS())%></td>
					    <td nowrap><%= compare(gsinfo[i].getYYZZH(), zrinfo[i].getYYZZH())%></td>
						<td nowrap><%= compare(gsinfo[i].getQYMC(), zrinfo[i].getQYMC())%></td>
						<td nowrap><%= compare(gsinfo[i].getZSDZ(), zrinfo[i].getZSDZ())%></td>
						<td nowrap><%= compare(gsinfo[i].getYB(), zrinfo[i].getYB())%></td>
						<td nowrap><%= compare(gsinfo[i].getDH(), zrinfo[i].getDH()) %></td>
						<td nowrap><%= compare(gsinfo[i].getJYFW(), zrinfo[i].getJYFW()) %></td>
						<td nowrap><%= compare(gsinfo[i].getZCZB(), zrinfo[i].getZCZB()) %></td>
						<td nowrap><%= compare(gsinfo[i].getFRXM(), zrinfo[i].getFRXM()) %></td>
						<td nowrap><%= compare(gsinfo[i].getZJLXDM(), zrinfo[i].getZJLXDM()) %></td>
						<td nowrap><%= compare(gsinfo[i].getFRSFZH(), zrinfo[i].getFRSFZH()) %></td>
						<td nowrap><%= compare(gsinfo[i].getGXQX(), zrinfo[i].getGXQX()) %></td>
						<td nowrap><%= compare(gsinfo[i].getDJZCLXDM(), zrinfo[i].getDJZCLXDM()) %></td>
						<td nowrap><%= compare(gsinfo[i].getRQ(), zrinfo[i].getRQ()) %></td>
					</tr>
					<tr class="<%= i%2 == 0 ? "evenrow" : "oddrow" %>">
						<td>中软</td>
						<td nowrap>&nbsp;<%=zrData.getFHDM()%></td>
						<td nowrap>&nbsp;<%=zrData.getKSJLS()%></td>
						<td nowrap>&nbsp;<%=zrData.getJSJLS()%></td>
						<td nowrap>&nbsp;<%=zrData.getZTS()%></td>
					    <td nowrap>&nbsp;<%=zrinfo[i].getYYZZH() %></td>
					    <td nowrap>&nbsp;<%=zrinfo[i].getQYMC() %></td>
						<td nowrap>&nbsp;<%=zrinfo[i].getZSDZ() %></td>
						<td nowrap>&nbsp;<%=zrinfo[i].getYB() %></td>
						<td nowrap>&nbsp;<%=zrinfo[i].getDH() %></td>
						<td nowrap>&nbsp;<%=zrinfo[i].getJYFW() %></td>
						<td nowrap>&nbsp;<%=zrinfo[i].getZCZB() %></td>
						<td nowrap>&nbsp;<%=zrinfo[i].getFRXM() %></td>
						<td nowrap>&nbsp;<%=zrinfo[i].getZJLXDM() %></td>
						<td nowrap>&nbsp;<%=zrinfo[i].getFRSFZH() %></td>
						<td nowrap>&nbsp;<%=zrinfo[i].getGXQX() %></td>
						<td nowrap>&nbsp;<%=zrinfo[i].getDJZCLXDM() %></td>
						<td nowrap>&nbsp;<%=zrinfo[i].getRQ() %></td>
					</tr>
				  <%
				  }
				  %>
				  <tr class="framerow" align="center">
						<td colspan="20"><input type="button" class="menu" value=" 返 回 " onclick="window.open('<%= request.getContextPath()%>/dw/runmgr/services/wstaxtest/index.jsp', '_self')" /></td>
					</tr>
				  <%
			  }else{
			  	%>
			  	<TR class="framerow">
			  		<td rowspan="2" nowrap>1</td>
			  		<td rowspan="2" nowrap><%= gsmethod.getName()%></td>
			  		<td nowrap>长软</td>
			  		<td nowrap><%= compare(gsData.getFHDM(), zrData.getFHDM())%></td>
					<td nowrap><%= compare(gsData.getKSJLS(), zrData.getKSJLS())%></td>
					<td nowrap><%= compare(gsData.getJSJLS(), zrData.getJSJLS())%></td>
					<td nowrap><%= compare(gsData.getZTS(), zrData.getZTS())%></td>
					<td nowrap>&nbsp;</td>
					<td nowrap>&nbsp;</td>
					<td nowrap>&nbsp;</td>
					<td nowrap>&nbsp;</td>
					<td nowrap>&nbsp;</td>
					<td nowrap>&nbsp;</td>
					<td nowrap>&nbsp;</td>
					<td nowrap>&nbsp;</td>
					<td nowrap>&nbsp;</td>
					<td nowrap>&nbsp;</td>
					<td nowrap>&nbsp;</td>
					<td nowrap>&nbsp;</td>
					<td nowrap>&nbsp;</td>
			  	</TR>
			  	<TR class="framerow">
			  		<td nowrap>中软</td>
			  		<td nowrap>&nbsp;<%=zrData.getFHDM()%></td>
					<td nowrap>&nbsp;<%=zrData.getKSJLS()%></td>
					<td nowrap>&nbsp;<%=zrData.getJSJLS()%></td>
					<td nowrap>&nbsp;<%=zrData.getZTS()%></td>
					<td nowrap>&nbsp;</td>
					<td nowrap>&nbsp;</td>
					<td nowrap>&nbsp;</td>
					<td nowrap>&nbsp;</td>
					<td nowrap>&nbsp;</td>
					<td nowrap>&nbsp;</td>
					<td nowrap>&nbsp;</td>
					<td nowrap>&nbsp;</td>
					<td nowrap>&nbsp;</td>
					<td nowrap>&nbsp;</td>
					<td nowrap>&nbsp;</td>
					<td nowrap>&nbsp;</td>
					<td nowrap>&nbsp;</td>
			  	</TR>
			  	<tr class="framerow" align="center">
					<td colspan="20"><input type="button" class="menu" value=" 返 回 " onclick="window.open('<%= request.getContextPath()%>/dw/runmgr/services/wstaxtest/index.jsp', '_self')" /></td>
				</tr>
			  	<%
			  }
			%>
		</table>
		
 <%
	}catch(Exception e){
		%>
		<table border="0" width="95%" align="center" class="frame-body" cellpadding="0" cellspacing="1">
			<tr class="title-row">
				<td nowrap>发生异常：</td>
			</tr>
			<tr class="oddrow">
				<td nowrap>异常信息：<%= e.getMessage()%></td>
			</tr>
			<tr class="oddrow" align="center">
				<td nowrap><input type="button" class="menu" value=" 返 回 " onclick="window.open('<%= request.getContextPath()%>/dw/runmgr/services/wstaxtest/index.jsp', '_self')" /></td>
			</tr>
		</table>
		<%
	}
 %>
  </freeze:body>
</freeze:html>
