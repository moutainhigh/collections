<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="java.util.*" %>
<%@ page import="com.gwssi.common.constant.ShareConstants" %>
<%@ page import="com.gwssi.common.constant.CollectConstants" %>
<%@ page import="com.gwssi.webservice.server.GSGeneralWebService" %>
<%@ page import="com.gwssi.common.util.XmlToMapUtil" %>
<freeze:html>
<head>
</head>
	<freeze:body >
	<%
		String[] colNameEnArray = request.getParameter("colNameEnArray").split(",");
		String[] colNameCnArray = request.getParameter("colNameCnArray").split(",");
		Enumeration enu = request.getParameterNames();
		HashMap map = new HashMap();
		while(enu.hasMoreElements()){
			String key = (String)enu.nextElement();
			String value = request.getParameter(key);
			
			if(value.trim().equals("")) continue;
			map.put(key.toUpperCase(), value);
		}
		
		map.put(CollectConstants.WSDL_URL,CollectConstants.WSDL_URL_TEST);
		map.put(CollectConstants.QNAME,CollectConstants.QNAME_NEW);
		map.remove("colNameEnArray");
		map.remove("colNameCnArray");
		
		GSGeneralWebService gs = new GSGeneralWebService();
		String dom = gs.queryData(XmlToMapUtil.map2Dom(map));
		
		Map mm  = (Map) XmlToMapUtil.dom2Map(dom).get("data");
		List list = (List) mm.get("row");
		
	%>
    <table width="100%" align="center"   cellspacing="0" cellpadding="0" class="frame-body" id="chaxun">
      <tr><td colspan="4">
			 <table width="100%" cellspacing="0" cellpadding="0" border="0">
				<tr><td class="leftTitle"></td><td class="secTitle">测试结果</td><td class="rightTitle"></td></tr>
			 </table>
		  </td>
	  </tr>
      
	  
	  <tr>
	  <td>
	  <table cellspacing="0" cellpadding="0" width="100%" style="border:2px solid #006699;border-collapse:collapse;" >
	  
	  <tr   align="center" class="framerow">
        <td class="odd2"   width="20%" align="right">返回代码：</td>
        <td class="odd2"   width="30%" align="left" >
        	<div id="code" class="entNameContentDiv" align="left">
        	<% 
        		String fhdm = (String)XmlToMapUtil.dom2Map(dom).get("FHDM").toString();
        	System.out.println("fhdm:"+fhdm);
        		if(fhdm.equalsIgnoreCase(ShareConstants.SERVICE_FHDM_LOGIN_FAIL)){
        			out.print("登录失败");
        		}else if(fhdm.equalsIgnoreCase(ShareConstants.SERVICE_FHDM_INPUT_PARAM_ERROR)){
        			out.print("参数错误");
        		}else if(fhdm.equalsIgnoreCase(ShareConstants.SERVICE_FHDM_NO_RESULT)){
        			out.print("无符合条件的记录");
        		}else if(fhdm.equalsIgnoreCase(ShareConstants.SERVICE_FHDM_OVER_DATE_RANGE)){
        			out.print("超过最大日期查询限制");
        		}else if(fhdm.equalsIgnoreCase(ShareConstants.SERVICE_FHDM_OVER_MAX)){
        			out.print("超过最大返回数量");
        		}else if(fhdm.equalsIgnoreCase(ShareConstants.SERVICE_FHDM_SUCCESS)){
        			out.print("查询成功");
        		}else if(fhdm.equalsIgnoreCase(ShareConstants.SERVICE_FHDM_SYSTEM_ERROR)){
        			out.print("系统错误");
        		}else{
        			out.print("未知错误");
        		}
        	 %></div>
        </td>	
        <td class="odd2"   width="20%" align="right">总条数：</td>
        <td class="odd2"  width="30%" align="left" ><div id="name" class="entRegNoContentDiv" align="left"><%= XmlToMapUtil.dom2Map(dom).get("ZTS")==null?"0": XmlToMapUtil.dom2Map(dom).get("ZTS") %></div></td>	        
      </tr>
	  <tr align="center" class="framerow">
        <td class="odd1"   width="20%" align="right">开始记录数：</td>
        <td class="odd1"   width="30%" align="left" ><div id="createDate" class="entNameContentDiv" align="left"><%= XmlToMapUtil.dom2Map(dom).get("KSJLS")==null?"0": XmlToMapUtil.dom2Map(dom).get("KSJLS")%></div></td>	
        <td class="odd1"   width="20%" align="right">结束记录数：</td>
        <td class="odd1"   width="30%" align="left" ><div id="createBy" class="entRegNoContentDiv" align="left"><%= XmlToMapUtil.dom2Map(dom).get("JSJLS")==null?"0": XmlToMapUtil.dom2Map(dom).get("JSJLS") %></div></td>	        
      </tr>
	  
	  </table>
	  
	  
	  </td>
	  </tr>
	  
	  
	  </table>
	  <br />
	  <div id="tbk" style="overflow:auto; width:100%; height: 200px;">
	  <table align="center" width="100%" cellspacing="0" cellpadding="0" class="frame-body" id="chaxun">
		  <%
		    if(list == null || list.size() == 0){
		    %>
		    <tr><td colspan="4">
			 <table width="100%" cellspacing="0" cellpadding="0" border="0">
				<tr><td class="leftTitle"></td><td class="secTitle">结果列表</td><td class="rightTitle"></td></tr>
			 </table>
		  		<script type="text/javascript">
		    	document.getElementById("tbk").style.height='80px';
		    </script></td>
	  		</tr>
		    <tr  >
			  <td colspan="4" align="center" >
			  <table style="border-collapse:collapse;border:2px solid #006699;width:100%;"><tr>
			  <td align="center" class="odd1">
			  	<font color="red" size="4px">无符合条件的记录！</font>
			  </td></tr></table></td>
		    </tr>
		    <%
		    }else{
		    %>
		    
		    <tr><td colspan="<% int cols =  ((Map)list.get(0)).entrySet().size(); out.println(++cols);%>">
			 <table width="100%" cellspacing="0" cellpadding="0" border="0">
				<tr><td class="leftTitle"></td><td class="secTitle">结果列表</td><td class="rightTitle"></td></tr>
			 </table>
		  		</td>
	  		</tr>
		    
		    <tr>
		    	<td>
		    		<table style="border:2px solid #006699;border-collapse:collapse;" width="100%"  cellspacing="0" cellpadding="0">
		    			<tr class="grid-headrow">
		    	<td nowrap  align="center" >序号</td>
		    <%
		    	if(colNameCnArray != null){
					%>
						<%
						for(int i = 0; i < colNameCnArray.length; i++){
							%>
							<td nowrap align="center" ><%= colNameCnArray[i]%></td>
							<%
			    	    }
			    	    %>
					<%
		        }
		    %>
		    </tr>
		    <%
		    
		    }
		  	for(int i = 0 ;list != null && i < list.size();i++){
		  		if(i%2==0){
				%>
				  <tr class="framerow">
					<td class="odd2" align="center" ><%= i + 1%></td>
				<%
				for(int j = 0; j < colNameEnArray.length; j++){
					if(j%2==0){
					%>
					<td class="odd2" align="center" ><%= ((Map)list.get(i)).get(colNameEnArray[j].toUpperCase()) == null ? "&nbsp;" : ((Map)list.get(i)).get(colNameEnArray[j].toUpperCase())%></td>
					<%
				}else{
				%>
					<td class="odd2" align="center" ><%= ((Map)list.get(i)).get(colNameEnArray[j].toUpperCase()) == null ? "&nbsp;" : ((Map)list.get(i)).get(colNameEnArray[j].toUpperCase())%></td>
				<%
				}%>
				<%} %>
				  </tr>
				 <%}else{ %>
				 <tr class="framerow">
					<td class="odd1" align="center" ><%= i + 1%></td>
				<%
				for(int j = 0; j < colNameEnArray.length; j++){
					if(j%2==0){
					%>
					<td class="odd1" align="center" ><%= ((Map)list.get(i)).get(colNameEnArray[j].toUpperCase()) == null ? "&nbsp;" : ((Map)list.get(i)).get(colNameEnArray[j].toUpperCase())%></td>
					<%
				}else{
				%>
					<td class="odd1" align="center" ><%= ((Map)list.get(i)).get(colNameEnArray[j].toUpperCase()) == null ? "&nbsp;" : ((Map)list.get(i)).get(colNameEnArray[j].toUpperCase())%></td>
				<%
				}%>
				<%} %>
				  </tr>
				 <%}
			   %> 
			<%
			}
		  %>
		    		</table>
		    	</td>
		    </tr>
		    
		    
	  </table>
	  </div>
	</freeze:body>
</freeze:html>