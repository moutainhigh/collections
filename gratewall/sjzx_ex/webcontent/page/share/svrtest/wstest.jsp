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
	<freeze:title caption="���Խ��"/>
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
        <td colspan="4" class="title-row" align="left" >���Խ��</td>
      </tr>
	  <tr align="center" class="framerow">
        <td width="20%" align="right">���ش��룺</td>
        <td width="30%" align="left" >
        	<div id="code" class="entNameContentDiv" align="left">
        	<% 
        		String fhdm = (String)result.get("FHDM");
        		if(fhdm.equalsIgnoreCase(Constants.SERVICE_FHDM_LOGIN_FAIL)){
        			out.print("��¼ʧ��");
        		}else if(fhdm.equalsIgnoreCase(Constants.SERVICE_FHDM_INPUT_PARAM_ERROR)){
        			out.print("��������");
        		}else if(fhdm.equalsIgnoreCase(Constants.SERVICE_FHDM_NO_RESULT)){
        			out.print("�޷��������ļ�¼");
        		}else if(fhdm.equalsIgnoreCase(Constants.SERVICE_FHDM_OVER_DATE_RANGE)){
        			out.print("���ڷ�Χ����,��������������ѯ����Ϊ7��");
        		}else if(fhdm.equalsIgnoreCase(Constants.SERVICE_FHDM_OVER_MAX)){
        			out.print("������󷵻�����");
        		}else if(fhdm.equalsIgnoreCase(Constants.SERVICE_FHDM_SUCCESS)){
        			out.print("��ѯ�ɹ�");
        		}else if(fhdm.equalsIgnoreCase(Constants.SERVICE_FHDM_SYSTEM_ERROR)){
        			out.print("ϵͳ����");
        		}else{
        			out.print("δ֪����");
        		}
        	 %></div>
        </td>	
        <td width="20%" align="right">��������</td>
        <td width="30%" align="left" ><div id="name" class="entRegNoContentDiv" align="left"><%= result.get("ZTS")==null?"0": result.get("ZTS") %></div></td>	        
      </tr>
	  <tr align="center" class="framerow">
        <td width="20%" align="right">��ʼ��¼����</td>
        <td width="30%" align="left" ><div id="createDate" class="entNameContentDiv" align="left"><%= result.get("KSJLS")==null?"0": result.get("KSJLS")%></div></td>	
        <td width="20%" align="right">������¼����</td>
        <td width="30%" align="left" ><div id="createBy" class="entRegNoContentDiv" align="left"><%= result.get("JSJLS")==null?"0": result.get("JSJLS") %></div></td>	        
      </tr>
	    
	  <tr align="center" class="framerow">
        <td width="20%" align="center" colspan="4"><input type="button" class="menu" value=" �� �� " onclick="window.open('<%=request.getContextPath()%>/dw/runmgr/services/svrtest/detail-sys_svr_service.jsp?<%=request.getQueryString()%>','_self');"/></td>
      </tr>
	  </table>
	  <br>
	  <table align="center" cellspacing="0" class="frame-body" id="chaxun">
		  
		  <%
		    if(mapArray == null || mapArray.length == 0){
		    %>
		    <tr>
			  <td colspan="4" class="title-row" align="left" >����б�</td>
			
		    </tr>
		    <tr>
			  <td colspan="4" width="90%" class="framerow" align="center" ><font color="red" size="5px">�޷��������ļ�¼��</font></td>
		    </tr>
		    <%
		    }else{
		    %>
		    <tr>
			  <td colspan="<%=mapArray[0].entrySet().size()%>" class="title-row" align="left" >����б�</td>
		    </tr>
		    <tr class="grid-headrow">
		    	<td nowrap height="26" align="center" >���</td>
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