<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询服务用户列表</title>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<%
DataBus db = (DataBus)request.getAttribute("freeze-databus");
//System.out.println("in view-sys_user.jsp is \n"+db);
DataBus db2 = db.getRecord("info-config");
DataBus db3 = db.getRecord("select-key");
String sys_params = (db2.getValue("sys_params")==null||db2.getValue("sys_params").equals("") ) ?"无":db2.getValue("sys_params");
String user_params = ( db2.getValue("user_params")==null || db2.getValue("user_params").equals("") )?"无": db2.getValue("user_params") ;
String shared_columns =( db2.getValue("shared_columns")==null || db2.getValue("shared_columns").equals("") )?"无":db2.getValue("shared_columns");
%>
</head>

<script language="javascript">

_browse.execute(function(){
	
	$('#sys_params').html("<%=sys_params%>");
	$('#user_params').html("<%=user_params%>");
	$('#shared_columns').html("<%=shared_columns%>");
	$("#btn_config2").click(function(){
			var page = new pageDefine( "insert-sys_svr_config_steps2.jsp","用户服务信息配置","modal", 800, 600);
			page.addValue( "<freeze:out value='${select-key.sys_svr_service_id}'/>", "svrId");
			page.addValue(  "<freeze:out value='${select-key.sys_svr_user_id}'/>", "userId");
			page.addValue( "<freeze:out value='${select-key.svr_name}'/>",'svrName');
			page.addValue( "xx", "userName");
			page.addValue("edit","actiont");
			page.updateRecord( );
	})
	$("#btn_config3").click(function(){
			var page = new pageDefine( "config-user_limit.jsp","用户服务限定信息配置","modal", 800, 600);
			//tree-sys_svr.jsp
			page.addValue( "<freeze:out value='${select-key.sys_svr_service_id}'/>", "svrId");
			page.addValue(  "<freeze:out value='${select-key.sys_svr_user_id}'/>", "userId");
			page.updateRecord( );
	})
	
	$("#btn_test").click(function(){
			var page = new pageDefine( "test-user_svr.jsp","用户服务测试","modal", document.body.clientWidth, document.body.clientHeight);
			//tree-sys_svr.jsp
			page.addValue( "<freeze:out value='${select-key.sys_svr_service_id}'/>", "svrId");
			page.addValue(  "<freeze:out value='${select-key.sys_svr_user_id}'/>", "userId");
			page.updateRecord( );
	})
	$("#btn_print").click(function(){	
		   var page = new pageDefine( "/txn50200031.do", "打印用户服务信息");
			page.addValue( "<freeze:out value='${select-key.sys_svr_service_id}'/>", "select-key:sys_svr_service_id");
			page.addValue(  "<freeze:out value='${select-key.sys_svr_user_id}'/>",  "select-key:sys_svr_user_id");
			page.addValue( "<freeze:out value='${select-key.svr_name}'/>",'svrName');
			page.goPage( );
	})
});

function resetParentTree(svrName,svrId,userId){
	window.parent.resetParentTree(svrName,svrId,userId);
}

function doDelete(){
 if(confirm("您确定要删除此服务吗?")){
	 var url="/txn50201011.ajax?select-key:sys_svr_service_id=<freeze:out value='${select-key.sys_svr_service_id}'/>&select-key:sys_svr_user_id=<freeze:out value='${select-key.sys_svr_user_id}'/>";
	 var page = new pageDefine(url, "删除用户服务");
	 page.callAjaxService('doCallback_delete');
  }
}

function doPause(state){
  var url="/txn50201012.ajax?select-key:sys_svr_service_id=<freeze:out value='${select-key.sys_svr_service_id}'/>&select-key:sys_svr_user_id=<freeze:out value='${select-key.sys_svr_user_id}'/>&select-key:is_pause="+state;
  var page = new pageDefine(url, "停用用户服务");
  page.callAjaxService('doCallback_update');
}

function doCallback_delete(errCode, errDesc, xmlResults){
	if(errCode == '000000'){
		alert('删除成功');
		parent.window.location.reload();
	}	  
 }
 
function doCallback_update(errCode, errDesc, xmlResults){
	if(errCode == '000000'){
		alert('操作成功');
		if($('#btn_pause').val()=='停用'){
		   $('#btn_pause').val('启用');
		   parent.changeClass("<freeze:out value='${select-key.sys_svr_service_id}'/>","0");
		}else{
		   $('#btn_pause').val('停用');
		   parent.changeClass("<freeze:out value='${select-key.sys_svr_service_id}'/>","1");
		}
	}	  
 }

</script>
<freeze:body>
<freeze:errors/>
     <table width="98%" border="0" align="center" class="frame-body" cellpadding="0" cellspacing="0"> 
			    <tr><td colspan="5">
				<table width="100%" cellspacing="0" cellpadding="0" border="0">
					<tr><td class="leftTitle"></td><td class="secTitle">&nbsp;&nbsp;<freeze:out value="${info-base.svr_name}"/></td>
						<td class="centerTitle">
						<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
						<input name="btn_config3" id='btn_config3' class="grid-menu"
								style="" type="button" value="限制条件" />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input name="btn_test" id='btn_test' class="grid-menu"
								style="" type="button" value="测试" />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input name="btn_delete" id='btn_delete' class="grid-menu"
								style="" onclick="doDelete()" type="button"
								value="删除" />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
				<freeze:if test="${info-base.is_pause=='0'}">
					<table cellspacing="0" cellpadding="0" class="button_table">
						<tr>
							<td class="btn_left"></td>
							<td>
								<input name="btn_pause" id='btn_pause' class="grid-menu"
									style="" onclick="doPause('1')" type="button"
									value="停用" />
							</td>
							<td class="btn_right"></td>
						</tr>
					</table>
				</freeze:if>
				      <freeze:if test="${info-base.is_pause=='1'}">
					<table cellspacing="0" cellpadding="0" class="button_table">
						<tr>
							<td class="btn_left"></td>
							<td>
								<input name="btn_pause" id='btn_pause' class="grid-menu"
									style="" onclick="doPause('0')" type="button"
									value="启用" />
							</td>
							<td class="btn_right"></td>
						</tr>
					</table>
				</freeze:if>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input name="btn_print" id='btn_print' class="grid-menu"
								style="" type="button" value="导出" />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
						</td>
						<td class="rightTitle"></td></tr>
				</table> 
 			</td>
 		</tr><tr><td style="padding:0px;" colspan="5">
 			<table cellspacing="0" cellpadding="0" style="width:100%;border:2px solid #006699;border-collapse:collapse;">
 				<tr>
		         	<td class="odd2" align="center" width="20%">服务类型：</td><td class="odd2" width="30%"><freeze:out value="${info-base.stype}"/></td>
		         	<td class="odd2" align="center" width="20%">视图代码：</td><td class="odd2" width="30%"><freeze:out value="${info-base.svr_code}"/></td>
		        </tr>
		        <tr>
		         	<td class="odd1" align="center" width="20%">开放时间：</td><td class="odd1" width="30%"><freeze:out value="${info-base.open_time}"/></td>
		         	<td class="odd1" align="center" width="20%">限定条件：</td><td class="odd1" width="30%"><freeze:out value="${info-base.limit_conn}"/></td>
		        </tr>
		        <tr>
		         	<td class="odd2" align="center" width="20%">创建人员：</td><td class="odd2" width="30%"><freeze:out value="${info-base.create_by}"/></td>
		         	<td class="odd2" align="center" width="20%">创建时间：</td><td class="odd2" width="30%"><freeze:out value="${info-base.create_date}"/></td>
		        </tr></table>
 		</td></tr>
	</table>	
    <br>

    <table width="98%" border="0" align="center" class="frame-body" cellpadding="0" cellspacing="0"> 
		<tr><td colspan="5">
				<table width="100%" cellspacing="0" cellpadding="0" border="0">
					<tr><td class="leftTitle"></td><td class="secTitle">&nbsp;&nbsp;配置信息</td>
						<td class="centerTitle">
						<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
						<input name="btn_config2" id='btn_config2' class="grid-menu" style="width: 50px;" type="button" value="配置" />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
						</td>
						<td class="rightTitle"></td></tr>
				</table> 
 			</td>
 		</tr><tr><td style="padding:0px;" colspan="5">
 			<table cellspacing="0" cellpadding="0" style="width:100%;border:2px solid #006699;border-collapse:collapse;">
			    <tr class="framerow">
		         	<td class="odd2" align="center" width="20%">共享字段：</td><td style="line-height:150%;" class="odd2" id="shared_columns" width="80%"></td>
		        </tr>
			    <tr class="framerow">
		         	<td  class="odd1" align="center">系统参数：</td><td class="odd1" id="sys_params"></td>
		        </tr>
		        <tr class="framerow">
		         <td  class="odd2" align="center">用户参数：</td><td class="odd2" id="user_params"></td>
		        </tr></table></td></tr>
	</table>	
</freeze:body>
</freeze:html>
