<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html title="查询功能信息列表">
<freeze:include href="/script/gwssi-xtree.js"/>
<freeze:include href="/script/struts-coolmenus3.js"/>

<freeze:body>
<freeze:title caption="查询功能信息列表"/>
<freeze:errors/>

<script language="javascript">
function funcClick( )
{
	if( funcinfo.selectedNode.id == '@temp:add-root-node' ){
		var page = new pageDefine( "insert-funcinfo.jsp", "增加功能信息", "modal", 650, 300);
		var ret = page.addRecord( );
		if( ret == 1 ){
			_browse.refresh();
		}
	}
	else{
		var page = new pageDefine( "/txn980311.do", "功能的交易信息" );
  		page.addValue(funcinfo.selectedNode.id, "select-key:funccode");
		var win = window.frames('txnlist');
		page.goPage( null, win );
	}
}

function func_record_addRecord()
{
	// 取上级节点
	var	pid = null;
	if( funcinfo != null && funcinfo.selectedNode != null ){
		pid = funcinfo.selectedNode.id;
	}
	
	if( pid != null && pid != "" && pid != '@temp:add-root-node' ){
		pid = "record:parentcode=" + pid;
	}
	
    var page = new pageDefine( "insert-funcinfo.jsp", "增加功能信息", "modal", 650, 300);
	var ret = page.addRecord( pid );
	if( ret == 1 ){
		refreshPage();
	}
}

function func_record_updateRecord()
{
	// 取上级节点
	if( funcinfo == null || funcinfo.selectedNode == null ){
		alert( '没有选中需要修改的记录' );
		return;
	}
	
	var pid = funcinfo.selectedNode.id;
	if( pid == null || pid == "" || pid.indexOf('@temp:') == 0 ){
		alert( '没有选中需要修改的记录' );
		return;
	}
	
	// 修改节点
    var page = new pageDefine( "/txn980304.do", "修改功能信息", "modal", 650, 300);
    page.addValue( pid, "primary-key:funccode" );
    page.updateRecord( );
}

function func_record_deleteRecord()
{
	// 取上级节点
	if( funcinfo == null || funcinfo.selectedNode == null ){
		alert( '没有选中需要删除的记录' );
		return;
	}
	
	var pid = funcinfo.selectedNode.id;
	if( pid == null || pid == "" || pid.indexOf('@temp:') == 0 ){
		alert( '没有选中需要删除的记录' );
		return;
	}
	
	if( funcinfo.selectedNode.submenu != null ){
		alert( '选中的功能中包含子功能，不能被删除' );
		return;
	}
	
	var page = new pageDefine( "/txn980305.do", "删除功能信息");
	
	// 输入执行成功时调用的函数或返回的页面地址
	page.nextPage = "@refreshPage";
	
	// 输入参数，调用服务
	page.addValue( pid, 'primary-key:funccode' );
	page.deleteRecord("是否删除选中的记录");
}

function moveNode( iType )
{
	var srcId = funcinfo.selectedNode.id;
	var destId = funcinfo.menuNode.id;
	
	if( iType != 'sub' ){
		var p = funcinfo.menuNode.parentNode;
		if( p == null ){
			destId = '';
		}
		else{
			destId = p.id;
		}
	}
	
	var	page = new pageDefine( "/txn980307.do", '移动功能节点' );
	
	// 输入执行成功时调用的函数或返回的页面地址
	page.nextPage = "@refreshPage";
	
	// 输入参数，调用服务
	page.addValue( srcId, 'record:funccode' );		// 移动的原始节点
	page.addValue( destId, 'record:parentcode' );	// 移动的目标节点
	// page.addValue( iType, 'record:move-type' );		// 移动方式：post,pre,sub
	page.callService( '移动功能节点' );
}

function prepareTree()
{
	// 增加最后一个节点
	funcinfo.addNode( '', '@temp:add-root-node', '增加新功能' );
}

// 刷新页面
function refreshPage()
{
	if( funcinfo != null && funcinfo.selectedNode != null ){
		var page = new pageDefine( menuPath + "query-funcinfo.jsp", "刷新列表", "window", 500, 250 );
	    
	    // 设置当前选中的节点
		var selectId = funcinfo.selectedNode.id;
		if( selectId != null && selectId != '' ){
			page.addValue( selectId, 'funcinfo:selected-node' );
		}
		
		page.goPage();
	}
	else{
		_browse.refresh();
	}
}

function exportData()
{
	// 取上级节点
	if( funcinfo == null || funcinfo.selectedNode == null ){
		alert( '没有选中需要修改的记录' );
		return;
	}
	
	var pid = funcinfo.selectedNode.id;
	if( pid == null || pid == "" || pid.indexOf('@temp:') == 0 ){
		alert( '没有选中需要修改的记录' );
		return;
	}
	
	// 修改节点
    var page = new pageDefine( "/txn980309.do", "导出数据", "modal", 750, 550);
    page.addValue( pid, "select-key:funccode" );
    page.goPage( );
}

function func_exportDocument()
{
	// 取上级节点
	if( funcinfo == null || funcinfo.selectedNode == null ){
		alert( '没有选中需要修改的记录' );
		return;
	}
	
	var pid = funcinfo.selectedNode.id;
	if( pid == null || pid == "" || pid.indexOf('@temp:') == 0 ){
		alert( '没有选中需要修改的记录' );
		return;
	}
	
	// 修改节点
    var page = new pageDefine( "/txn980308.do", "功能文档", "modal", 750, 550 );
    page.addValue( pid, "select-key:funccode" );
    page.goPage( );
}
</script>

  <br>
  <table border="0" cellpadding="0" cellspacing="0" width="95%" height="92%" align="center" style='table-layout:fixed;'>
	<tr height="1"><td width="30%"></td><td width="70%"></td></tr>
	<tr height="26" valign="top"><td width="100%" colspan="2">
    <freeze:button name="record_addRecord" value="增加子功能" onclick="func_record_addRecord();" style="color:blue" />
    <freeze:button name="record_updateRecord" value="修改功能" onclick="func_record_updateRecord();" style="color:blue"/>
    <freeze:button name="record_deleteRecord" value="删除功能" onclick="func_record_deleteRecord();" style="color:blue"/>
	<freeze:button name="moveNode" value="移动节点" onclick="funcinfo.openMoveNodeWindow('moveNode','sub')" style="color:blue"/>
	<!-- 
	<freeze:button name="exportData" txncode="980309" value="导出数据" onclick="exportData()" styleClass="menu"/>
	<freeze:button name="exportDocument" txncode="980308" value="生成文档" onclick="func_exportDocument()" styleClass="menu"/>
	-->
	</td></tr>
	<tr><td height="1" bgcolor="3366FF" width="100%" colspan="2"></td></tr>
	<tr height="99%"><td>
	<freeze:xtree name='funcinfo' txnCode='980301' width="100%" height="100%" 
		bgcolor="F7F7F7" aspect="list" nodeicon="scrollend.gif" nodename="record" 
		pidname="parentcode" idname="funccode" textname="{funcname}" 
		onclick="funcClick" precreate="prepareTree"/>
	</td>
	<td valign="top">
	<iframe name='txnlist' scrolling=no frameborder=0 width=100% height=100%></iframe>
	</td>
	</tr>
  </table>
</freeze:body>
</freeze:html>
