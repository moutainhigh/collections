<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<freeze:include href="/script/lib/jquery.js"></freeze:include>
<freeze:include href="/script/lib/interface.js"></freeze:include>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/component/JqTree.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/JqTree.css"/>
<head>
<title>查询下载设置列表</title>
</head>

<script language="javascript">

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	$(document).ready(function() {
		var pageUrl ="<%=request.getContextPath()%>/txn1050011.ajax";
		var nodePageUrl ="<%=request.getContextPath()%>/txn1050011.ajax";
		JqTree.treeConfig.loadingText="载入中...";
		JqTree.treeConfig.focusColor="#FFEE00";
		var attributes = new Array("download_purv_id");
		var treeObject = new JqTree.TreeObject.create("download_purv_id", "jgmc", "jgmc");//id text pid
		var actionHandler = new JqTree.ActionHandler.create();
		actionHandler.click.handler = clickHandler; //添加点击的回调事件
		
		//sRootText,oTreeObject,sSrc,sChildSrc
		var jqTree = new JqTree.create("深圳市市场和质量监督管理委员会",treeObject,pageUrl,nodePageUrl,attributes,actionHandler,false,false,false);
		window.jqTree = jqTree;
		$("div#reportContainer").html("");
		$("div#reportContainer").append(jqTree.tree);	
		
		function clickHandler(event){
			var dataObject = this.jqTreeNode.getDataObject();
			var download_purv_id = dataObject["download_purv_id"];
			var page = new pageDefine( "/txn105004.do?primary-key:download_purv_id=" + download_purv_id, "修改下载设置", "window" );
			var win = window.frames("modifyFrame");
			if( win != null ){
				if (document.getElementById("modifyFrame").style.display == "none"){
					document.getElementById("modifyFrame").style.display = "block";
				}
				page.goPage(null, win);
			}
		}		
	});
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询下载设置列表"/>
<freeze:errors/>

<table border="0" cellpadding="0" cellspacing="0" width="95%" align="center">
	<tr><td height="1" bgcolor="3366FF" width="100%" colspan="2"></td></tr>
	<tr><td height="5" width="100%" colspan="2"></td></tr>
	<tr><td width="35%" valign="top"><div id="reportContainer" style="width:300px;overflow-x:hidden;overflow-y:auto;"></div></td>
	<td width="65%" valign="top"><div id="operation"><iframe name="modifyFrame" scrolling="no" frameborder="0" id="modifyFrame" src="" style="width:100%; height:100%;display:none;"></iframe></div></td></tr>
	</table>
</freeze:body>
</freeze:html>
