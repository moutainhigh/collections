<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="350">
<head>
<title>服务配置</title>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery1.1.js"></script>
<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/component/stepHelp.js"></SCRIPT>
<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/component/dataSelectPlugin.js"></SCRIPT>
<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/component/connectConditionPlugin.js"></SCRIPT>
<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/component/queryConditionPlugin.js"></SCRIPT>
<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/component/generateTotalTable.js"></SCRIPT>
<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/page/insert-sys_svr_config.js"></SCRIPT>
</head>
<freeze:body>
	<freeze:title caption="服务配置"/>
	<form action="preview.jsp" method="post" target="pageList_frameX" style="margin:0;padding:0">
		<input type="hidden" id="record:query_sql" name="record:query_sql" />
		<input type="hidden" id="record:columnsEnArray" name="record:columnsEnArray" />
		<input type="hidden" id="record:columnsCnArray" name="record:columnsCnArray" />
		<input type="hidden" id="preSelectedIdx" name="preSelectedIdx" />
		<div id="step1DIV"> 
			<table width="95%" border="0" align="center" class="frame-body" cellpadding="0" cellspacing="1"> 
			    <tr class="title-row">
			      <td>服务配置</td>
			    </tr>
			    <tr class="framerow">
		          <td><div id="div1" style="height:100%;width:100%;"></div></td>
		        </tr>
		    </table>
		    <br>	
		    <div id="div2" style="margin-top:10px;" align="center">
	        	<input type='button' id='goBack0' class="menu" value=' 返 回 ' onclick="goBack();"/>
	        </div>
        </div>
        
        <div id="step2DIV" style="display:none" align="center">
        	<IFRAME id=pageList_frameX frameBorder=0 width="95%" ></IFRAME>
        		
		</div>
        
	</form>
</freeze:body>
<script type="text/javascript">
	window.rootPath = "<%=request.getContextPath()%>";
	window.targetUserId = "<%=request.getParameter("record:sys_svr_user_id")%>";
	window.targetUserName = "<%=request.getParameter("record:user_name")%>";
	var doUpdate = null;
	var ajaxXml = null;
</script>
</freeze:html>
