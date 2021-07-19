<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Map.Entry" %>
<%@ page import="com.gwssi.dw.runmgr.webservices.localtax.out.client.*" %>
<%@ page import="java.net.URL" %>
<%@ page import="org.apache.axis.client.*" %>
<%@ page import="javax.xml.namespace.QName" %>
<%@ page import="com.gwssi.dw.runmgr.services.common.Constants" %>
<freeze:html>
	<head>
	</head>

	<freeze:body>
	<freeze:title caption="测试结果"/>
	<%
		String[] colNameEnArray = request.getParameter("colNameEnArray").split(",");
		String[] colNameCnArray = request.getParameter("colNameCnArray").split(",");
		Enumeration enu = request.getParameterNames();
		HashMap map = new HashMap();
		while(enu.hasMoreElements()){
			String key = (String)enu.nextElement();
			String value = request.getParameter(key);
			//System.out.println("key--->"+key+", value--->"+value);
			if(value.trim().equals("")) continue;
			map.put(key.toUpperCase(), value);
		}
		GSGeneralWebServiceService s = new GSGeneralWebServiceServiceLocator();
		StringBuffer url = new StringBuffer();
		url.append("http://")
		   .append(request.getServerName())
		   .append(":")
		   .append(request.getServerPort())
		   .append(request.getContextPath())
		   .append("/services/GSWebService");
		System.out.println("Address: "+url);
		HashMap result = s.getGSWebService(new URL(url.toString())).query(map);
		//System.out.println("return-------------->"+result);
		HashMap[] mapArray = (HashMap[])result.get("GSDJ_INFO_ARRAY");
	%>
	
    <table width="100%" border="1" align="center" cellspacing="0" class="frame-body" id="chaxun">
      <tr>
        <td colspan="4" class="title-row" align="left" >测试结果</td>
      </tr>
	  <tr align="center" class="framerow">
        <td width="20%" align="right">返回代码：</td>
        <td width="30%" align="left" >
        	<div id="code" class="entNameContentDiv" align="left">
        	<% 
        		String fhdm = (String)result.get("FHDM");
        		if(fhdm.equalsIgnoreCase(Constants.SERVICE_FHDM_LOGIN_FAIL)){
        			out.print("登录失败");
        		}else if(fhdm.equalsIgnoreCase(Constants.SERVICE_FHDM_INPUT_PARAM_ERROR)){
        			out.print("参数错误");
        		}else if(fhdm.equalsIgnoreCase(Constants.SERVICE_FHDM_NO_RESULT)){
        			out.print("无符合条件的记录");
        		}else if(fhdm.equalsIgnoreCase(Constants.SERVICE_FHDM_OVER_DATE_RANGE)){
        			out.print("日期范围过大,共享服务测试最大查询天数为7天");
        		}else if(fhdm.equalsIgnoreCase(Constants.SERVICE_FHDM_OVER_MAX)){
        			out.print("超过最大返回数量");
        		}else if(fhdm.equalsIgnoreCase(Constants.SERVICE_FHDM_SUCCESS)){
        			out.print("查询成功");
        		}else if(fhdm.equalsIgnoreCase(Constants.SERVICE_FHDM_SYSTEM_ERROR)){
        			out.print("系统错误");
        		}else{
        			out.print("未知错误");
        		}
        	 %></div>
        </td>	
        <td width="20%" align="right">总条数：</td>
        <td width="30%" align="left" ><div id="name" class="entRegNoContentDiv" align="left"><%= result.get("ZTS")==null?"0": result.get("ZTS") %></div></td>	        
      </tr>
	  <tr align="center" class="framerow">
        <td width="20%" align="right">开始记录数：</td>
        <td width="30%" align="left" ><div id="createDate" class="entNameContentDiv" align="left"><%= result.get("KSJLS")==null?"0": result.get("KSJLS")%></div></td>	
        <td width="20%" align="right">结束记录数：</td>
        <td width="30%" align="left" ><div id="createBy" class="entRegNoContentDiv" align="left"><%= result.get("JSJLS")==null?"0": result.get("JSJLS") %></div></td>	        
      </tr>
	    
	  <tr align="center" class="framerow">
        <td width="20%" align="center" colspan="4"><input type="button" class="menu" value=" 返 回 " onclick="window.open('<%=request.getContextPath()%>/dw/runmgr/services/svrtest/detail-sys_svr_service.jsp?<%=request.getQueryString()%>','_self');"/></td>
      </tr>
	  </table>
	  <br>
	  <table align="center" cellspacing="0" class="frame-body" id="chaxun">
		  
		  <%
		    if(mapArray == null || mapArray.length == 0){
		    %>
		    <tr>
			  <td colspan="4" class="title-row" align="left" >结果列表</td>
			
		    </tr>
		    <tr>
			  <td colspan="4" width="90%" class="framerow" align="center" ><font color="red" size="5px">无符合条件的记录！</font></td>
		    </tr>
		    <%
		    }else{
		    %>
		    <tr>
			  <td colspan="<%=mapArray[0].entrySet().size()%>" class="title-row" align="left" >结果列表</td>
		    </tr>
		    <tr class="grid-headrow">
		    	<td nowrap height="26" align="center" >序号</td>
		    <%
		    	if(colNameCnArray != null){
					%>
						<%
						for(int i = 0; i < colNameCnArray.length; i++){
							%>
							<td nowrap height="26" align="center" ><%= colNameCnArray[i]%></td>
							<%
			    	    }
			    	    %>
					<%
		        }
		    %>
		    </tr>
		    <%
		    
		    }
		  	for(int i = 0 ;mapArray != null && i < mapArray.length;i++){
				%>
				  <tr class="framerow">
					<td height="26" align="center" ><%= i + 1%></td>
				<%
				for(int j = 0; j < colNameEnArray.length; j++){
					%>
					<td height="26" align="center" ><%= mapArray[i].get(colNameEnArray[j].toUpperCase()) == null ? "&nbsp;" : mapArray[i].get(colNameEnArray[j].toUpperCase())%></td>
					<%
				}
				%>
				  </tr>
				<%
			}
		  %>
	  </table>
	  
	</freeze:body>
</freeze:html>