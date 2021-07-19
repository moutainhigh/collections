<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询用户视图信息</title>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<%
DataBus db = (DataBus)request.getAttribute("freeze-databus");
System.out.println("in view-sys_view.jsp is \n"+db);
DataBus db2 = db.getRecord("info-config");
DataBus info_base = db.getRecord("info-base");

String conn_params = (db2.getValue("conn_params")==null||db2.getValue("conn_params").equals("") ) ?"无":db2.getValue("conn_params");
String shared_columns =( db2.getValue("shared_columns")==null || db2.getValue("shared_columns").equals("") )?"无":db2.getValue("shared_columns");
%>
</head>

<script language="javascript">

_browse.execute(function(){
	$('#conn_params').html("<%=conn_params%>");
	$('#shared_columns').html("<%=shared_columns%>");

});
</script>
<freeze:body>
<freeze:errors/>

 	<table width="98%" border="0" align="center" class="frame-body" cellpadding="0" cellspacing="0"> 			    
			    <tr><td >
						<table width="100%" cellspacing="0" cellpadding="0" border="0">
							<tr><td class="leftTitle"></td><td  class="secTitle">&nbsp;基本信息</td><td class="rightTitle"></td></tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<table style="border:2px solid #006699;border-collapse:collapse;" width="100%"  cellspacing="0" cellpadding="0" >
							<tr class="framerow">
		         				<td class="even1" align="center" width="15%">服务类型：</td><td class="odd1" width="35%"><freeze:out value="${info-base.stype}"/></td>
		         				<td class="even1" align="center" width="15%">服务代码：</td><td class="odd1" width="35%"><freeze:out value="${info-base.svr_code}"/></td>
		        			</tr>
		      
		        			<tr class="framerow">
		         				<td class="even2" align="center" width="15%">创建人员：</td><td class="odd2" width="35%"><freeze:out value="${info-base.create_by}"/></td>
		         				<td class="even2" align="center" width="15%">创建时间：</td><td class="odd2" width="35%"><freeze:out value="${info-base.create_date}"/></td>
		        			</tr>
						</table>
					</td>
				</tr>
			    
			   
	</table>	
    <br>

     <table width="98%" border="0" align="center" class="frame-body" cellpadding="0" cellspacing="0"> 
			    <tr><td >
						<table width="100%" cellspacing="0" cellpadding="0" border="0">
							<tr><td class="leftTitle"></td><td class="secTitle">&nbsp;配置信息</td><td class="rightTitle"></td></tr>
						</table>
					</td>
				</tr>
				
				<tr>
					<td>
						<table style="border:2px solid #006699;border-collapse:collapse;" width="100%"  cellspacing="0" cellpadding="0">
							<tr class="framerow">
		         				<td class="even1" align="center" width="15%">共享字段：</td><td class="odd1" id="shared_columns" width="85%"></td>
		        			</tr>
			    			<tr class="framerow">
		         				<td  class="even2" align="center">连接条件：</td><td class="odd2" id="conn_params"></td>
		       	 			</tr>
						</table>
					</td>
				</tr>
			    
			    
		        
	</table>	
<br>
</freeze:body>
</freeze:html>
