<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="java.util.*" %>
<%@ page import="com.gwssi.common.constant.ShareConstants" %>
<%@ page import="com.gwssi.common.constant.CollectConstants" %>
<%@ page import="com.gwssi.webservice.server.GSGeneralWebService" %>
<%@ page import="java.io.*"%>
<%@ page import="com.gwssi.common.constant.ExConstant"%>
<%@ page import="com.gwssi.common.util.AnalyCollectFile" %>
<%@ page import="jxl.write.WritableWorkbook" %>
<%@ page import="jxl.write.WritableSheet" %>
<%@ page import="jxl.write.Label" %>
<%@ page import="jxl.Workbook" %>
<%@ page import="java.io.File" %>
<%@ page import="com.gwssi.common.util.*" %>

<freeze:html>
<head>
<style type="text/css">
.odd1, .odd12{height:auto;}
</style>
</head>
<script language="javascript">
//生成下载结果文件
function func_record_createDownloadFile()
{
		
		var file_map = document.getElementById("FILE_MAP").value;
		var page = new pageDefine( "/txn50204004.ajax", "生成下载文件" );
		page.addValue(file_map,"select-key:file_map");
		_showProcessHintWindow ("正在生成数据文件...");
		page.callAjaxService("collectResCallBack");
	
}
function collectResCallBack(errorCode, errDesc, xmlResults){
	
	if(errorCode!="000000"){
		alert(errDesc);
		_hideProcessHintWindow ();
	}else{
		var txtUrl = _getXmlNodeValue(xmlResults,"record:txtUrl");
		var excelUrl = _getXmlNodeValue(xmlResults,"record:excelUrl");
		var result = _getXmlNodeValue(xmlResults,"record:result");
		
		if(result=="01"){
			var txtlink = document.getElementById("txtdownload");
			var par = txtlink.parentNode.parentNode;
			par.style.display="block";
			document.getElementById("txtdownload").href=txtUrl;
			document.getElementById("exceldownload").href=excelUrl;
		
		}else if(result=="00"){
			alert("获取文件失败！");
		}else{
			alert("系统错误！");
		}
		_hideProcessHintWindow ();
		
	}
}
</script>
	<freeze:body >
	<%
		//String service_no = request.getParameter("service_no");
		String service_id=request.getParameter("SHARE_SERVICE_ID");
		StringBuffer fileParam = new StringBuffer("");
		
		Enumeration enu = request.getParameterNames();
		HashMap map = new HashMap();
		while(enu.hasMoreElements()){
			String key = (String)enu.nextElement();
			String value = request.getParameter(key);
			if(value.trim().equals("")) continue;
			map.put(key.toUpperCase(), value);
			
			if(!key.equals("colNameEnArray")&&!key.equals("colNameCnArray")){
				if(fileParam.toString().equals("")){
					fileParam.append(key.toUpperCase()+"="+value);	
				}else{
					fileParam.append("#~#"+key.toUpperCase()+"="+value);
				}
				
			}
		}
		
		fileParam.append("#~#USER_TYPE=TEST");
		fileParam.append("#~#"+CollectConstants.WSDL_URL+"="+CollectConstants.WSDL_URL_TEST);
		fileParam.append("#~#"+CollectConstants.QNAME+"="+CollectConstants.QNAME_ERQI);
		
		map.put("USER_TYPE","TEST");
		map.remove("colNameEnArray");
		map.remove("colNameCnArray");
		map.put("SERVICE_ID",service_id);
		map.put(CollectConstants.WSDL_URL,CollectConstants.WSDL_URL_TEST);
		map.put(CollectConstants.QNAME,CollectConstants.QNAME_ERQI);
		
		//开始读文件
		String svrPath = ExConstant.SHARE_CONFIG;
		File file = new File(svrPath + File.separator + service_id+ ".dat");
		
		//Map<String, Map<String, String>> colMap = new HashMap<String, Map<String, String>>();
		Map<String, String> tmpMap = new HashMap<String, String>();
		//判断文件是否存在
		if (file.exists()) {
			InputStreamReader read = new InputStreamReader(
					new FileInputStream(file), "UTF-8");
			BufferedReader reader = new BufferedReader(read);
			String line = reader.readLine();
			while (line != null) {
				String[] cols = line.split("###");
				tmpMap.put("column_name_cn", cols[2]);
				tmpMap.put("column_name_en", cols[4]);
				line = reader.readLine();
			}
			reader.close();
		}
		//读文件结束

		fileParam.append("#~#column_name_en="+tmpMap.get("column_name_en"));
		fileParam.append("#~#column_name_cn="+tmpMap.get("column_name_cn"));
		String testStr =  fileParam.toString();	
		String[] colNameEnArray = tmpMap.get("column_name_en")
				.split(",");
		String[] colNameCnArray = tmpMap.get("column_name_cn")
				.split(",");
		
		System.out.println("这是访问二期的接口");
		GSGeneralWebService ws = new GSGeneralWebService();
		HashMap result = (HashMap)ws.query(map);
		HashMap[] mapArray = (HashMap[])result.get("GSDJ_INFO_ARRAY");
		
		
		
	%>
    <table width="100%" align="center"   cellspacing="0" cellpadding="0" class="frame-body" id="chaxun">
      <tr><td colspan="4">
			 <table width="100%" style="border-collapse:collapse;" cellspacing="0" cellpadding="0" border="0">
				<tr><td class="odd12">测试结果</td></tr>
			 </table>
		  </td>
	  </tr>
      
	  
	  <tr>
	  <td>
	  <table cellspacing="0" cellpadding="0" width="100%" style="border:1px solid rgb(207,207,254);background-color:#f7f7f7; border-collapse:collapse;" >
	  
	  <tr   align="center" class="framerow">
        <td class="odd1"   width="20%" align="right">返回代码：</td>
        <td class="odd1"   width="30%" align="left" >
        	<div id="code" class="entNameContentDiv" align="left">
        	<% 
        		String fhdm = result.get("FHDM").toString();
        	    String error_msg=ValueSetCodeUtil.getPropertiesByKey("share_error",fhdm);
        	    out.print(error_msg);
        	
        	 %></div>
        </td>	
        <td class="odd1"   width="20%" align="right">总条数：</td>
        <td class="odd1"  width="30%" align="left" ><div id="name" class="entRegNoContentDiv" align="left"><%= result.get("ZTS")==null?"0": result.get("ZTS") %></div></td>	        
      </tr>
	  <tr align="center" class="framerow">
        <td class="odd1"   width="20%" align="right">开始记录数：</td>
        <td class="odd1"   width="30%" align="left" ><div id="createDate" class="entNameContentDiv" align="left"><%= result.get("KSJLS")==null?"0": result.get("KSJLS")%></div></td>	
        <td class="odd1"   width="20%" align="right">结束记录数：</td>
        <td class="odd1"   width="30%" align="left" ><div id="createBy" class="entRegNoContentDiv" align="left"><%= result.get("JSJLS")==null?"0": result.get("JSJLS") %></div></td>	        
      </tr>
	  
	  </table>
	  
	  
	  </td>
	  </tr>
	  
	  
	  </table>
	  <br />
	  <div id="tbk" style="overflow:auto; width:100%; height: 200px;">
	  <table align="center" width="100%" cellspacing="0" cellpadding="0" class="frame-body" id="chaxun">
		  <%
		    if(mapArray == null || mapArray.length == 0){
		    %>
		    <tr><td colspan="4">
			 <table width="100%" style="border-collapse:collapse;" cellspacing="0" cellpadding="0" border="0">
				<tr><td class="odd12">结果列表</td></tr>
			 </table>
		  		<script type="text/javascript">
		    	document.getElementById("tbk").style.height='80px';
		    </script></td>
	  		</tr>
		    <tr  >
			  <td colspan="4" align="center" >
			  <table style="border-collapse:collapse;border:1px solid rgb(207,207,254);background-color:#f7f7f7;width:100%;"><tr>
			  <td align="center" class="odd1">
			  	<font color="red" size="4px">无符合条件的记录！</font>
			  </td></tr></table></td>
		    </tr>
		    <%
		    }else{
		    %>
		    
		    <tr><td colspan="<% int cols =  mapArray[0].entrySet().size(); out.println(++cols);%>">
			 <table width="100%" style="border-collapse:collapse;" cellspacing="0" cellpadding="0" border="0">
				<tr><td class="odd12">结果列表</td></tr>
			 </table>
		  		</td>
	  		</tr>
		   <tr><td >生成数据文件：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" title="生成数据文件" onclick="func_record_createDownloadFile()"><div class="download"></div></a><input type="hidden" name="FILE_MAP" id="FILE_MAP" value="<%= testStr%>" />
	  		</td>
			  </tr>
		    <tr style="display=none;"><td >下载数据文件：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a title='Excel下载' id="exceldownload" href='' onclick=''>Excel文件下载<div class='download'></div></a>
			   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a title='txt下载' id="txtdownload" href='' onclick=''>txt文件下载<div class='download'></div></a></td>
			  </tr> 
		    <tr>
		    	<td>
		    		<table style="border:1px solid rgb(207,207,254);background-color:#f7f7f7;border-collapse:collapse;" width="100%"  cellspacing="0" cellpadding="0">
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
		    
	
		  	for(int i = 0 ;mapArray != null && i < mapArray.length;i++){
		  		if(i%2==0){
				%>
				  <tr>
					<td class="odd12" align="center" ><%= i + 1%></td>
				<%
				for(int j = 0; j < colNameEnArray.length; j++){
					if(j%2==0){
					%>
					<td class="odd12" align="center" ><%= mapArray[i].get(colNameEnArray[j].toUpperCase()) == null ? "&nbsp;" : mapArray[i].get(colNameEnArray[j].toUpperCase())%></td>
					<%
				}else{
				%>
					<td class="odd12" align="center" ><%= mapArray[i].get(colNameEnArray[j].toUpperCase()) == null ? "&nbsp;" : mapArray[i].get(colNameEnArray[j].toUpperCase())%></td>
				<%
				}%>
				<%} %>
				  </tr>
				 <%}else{ %>
				 <tr class="framerow">
					<td class="odd12" align="center" ><%= i + 1%></td>
				<%
				for(int j = 0; j < colNameEnArray.length; j++){
					if(j%2==0){
					%>
					<td class="odd12" align="center" ><%= mapArray[i].get(colNameEnArray[j].toUpperCase()) == null ? "&nbsp;" : mapArray[i].get(colNameEnArray[j].toUpperCase())%></td>
					<%
				}else{
				%>
					<td class="odd12" align="center" ><%= mapArray[i].get(colNameEnArray[j].toUpperCase()) == null ? "&nbsp;" : mapArray[i].get(colNameEnArray[j].toUpperCase())%></td>
				<%
				}%>
				<%} %>
				  </tr>
				 <%}
			   %> 
			<%
			}
		  %>
		  
		  <%} %>  
	  </table>
	  </div>
	</freeze:body>
</freeze:html>