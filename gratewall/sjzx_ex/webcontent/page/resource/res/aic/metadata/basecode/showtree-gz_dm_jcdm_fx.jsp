<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<freeze:include href="/script/lib/jquery.js"></freeze:include>
<freeze:include href="/script/lib/interface.js"></freeze:include>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/plugins/jquery.select.js?p=9"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/component/JqTree.js?p=9"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/JqTree.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/JqForm.css"/>

<head>
<title>查询基础数据分项列表</title>
</head>

<script language="javascript">
function func_record_return(){

    goBack();

}

function func_record_addRecord()
{
          
     var sf_mx=1;
     var treeNode=window.jqTree.getSelectedNode();
     if(treeNode==null)return;
     if(treeNode=="|null"){    
     }else{
	     var dataObject = treeNode.getDataObject();
	     var sy_zt = dataObject["sy_zt"];
		     if(sy_zt=="0"){
		       alert("该数据分项已停用，不能增加节点");
		       return;
		 }
	 }
     var jcsjfx_fjd=treeNode.getId();
 	 var jc_dm_id = getFormFieldValue("select-key:jc_dm_id");
     if(jcsjfx_fjd=="")
     {
     szcc=1;

     var page=new pageDefine("/txn3010108.do?record:jcsjfx_fjd="+jcsjfx_fjd,"增加索引目录分项信息","window");   
     
     page.addValue(jc_dm_id,"record:jc_dm_id");     
	 page.addValue(sf_mx,"record:sfmx");
     page.addValue(szcc,"record:szcc");
     
      var win = window.frames("modifyFrame");
			 if( win != null ){
			 	page.goPage(null, win);
			}   
     }
     else
     {     
     var jcsjfx_id=dataObject["jcsjfx_id"];
     var szcc=treeNode.getLevel();
     var szcc_next=parseInt(szcc)+1;
	 var page = new pageDefine( "/txn3010108.do?record:jcsjfx_fjd="+jcsjfx_fjd, "增加索引目录分项信息", "window" );
 
     page.addValue(jc_dm_id,"record:jc_dm_id");     
	 page.addValue(sf_mx,"record:sfmx");
     page.addValue(szcc_next,"record:szcc");
     page.addValue(jcsjfx_id,"record:jcsjfx_id");
     
	 var win = window.frames("modifyFrame");
	
			 if( win != null ){
			 	page.goPage(null, win);
			}
     }
    
}

function func_record_updateRecord()
{
	var page = new pageDefine( "/txn3010104.do", "修改分组项", "modal" );
	page.addParameter( "record:jcsjfx_id", "primary-key:jcsjfx_id" );
	page.updateRecord();
}


function func_record_deleteRecord()
{        
         //var syml_zt=getFormFieldValue("select-key:syml_zt");
    
	     var selectMessage="请先选中要操作的节点";
	     var treeNode=window.jqTree.getSelectedNode();
	     if(treeNode==null)return;
	     var id=treeNode.getId();
	     if(id==""){
	        alert("根结点不能删除！");
	        return false;
	     }
	     var pid=treeNode.getPid();
	     
	     if(treeNode.getAttribute("hasChildren")=="true"){
	      alert('选中的分组项目录中包含子分组项目录，不能被删除');
	      return;
	     }
	     var dataObject = treeNode.getDataObject();     
         var jcsjfx_mc=dataObject["jcsjfx_mc"];      	     
		 var page = new pageDefine( "/txn3010105.ajax", "删除分组项" );	
		 		  
		 page.addValue(id, "primary-key:jcsjfx_id");
		 page.addValue(jcsjfx_mc,"record:jcsjfx_mc");
		 //page.addValue(syml_zt,"select-key:syml_zt");
		 		 
	    if(window.confirm("是否删除选中的节点！")){
		  	page.callAjaxService("delete_ajax");
		}		
 }
	
	// 删除节点后对页面的节点进行刷新操作
function delete_ajax(errCode,errDesc,xmlResults){
	  //必须在进行完相关的操作后才能调用次方法
	   if(errCode=='000000'){
	   window.jqTree.removeNode();
	   var win = getIframeByName('modifyFrame');
	   var selectedNode  = window.jqTree.getSelectedNode();
	   
	   if(selectedNode.node.attr("isRoot")!="true"){
	   		$("span.jTree_item_text:first",selectedNode.node).click();
	   }else{
	   		document.getElementById("modifyFrame").src="";
	   }
	   
	 }else{
	     alert(errDesc);
	 }	
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{

	$(document).ready(function() {
		var jc_dm_id = getFormFieldValue("select-key:jc_dm_id");
		var jc_dm_mc = getFormFieldValue("select-key:jc_dm_mc");
		var pageUrl ="<%=request.getContextPath()%>/txn3010106.ajax?select-key:jc_dm_id="+jc_dm_id;//获得第一层节点
		var nodePageUrl ="<%=request.getContextPath()%>/txn3010107.ajax";//根据父节点id获得节点，会根据父代码名称和代码值生成查询参数select-key:dm_fwxml=10009
		
		JqTree.treeConfig.loadingText="载入中...";
		JqTree.treeConfig.rootIcon="<%=request.getContextPath()%>/images/tree/folder.png";
		JqTree.treeConfig.folderIcon="<%=request.getContextPath()%>/images/tree/folder.png";
		JqTree.treeConfig.openFolderIcon="<%=request.getContextPath()%>/images/tree/openfolder.png";
		JqTree.treeConfig.fileIcon = "<%=request.getContextPath()%>/images/tree/file.png";
		JqTree.treeConfig.loadingIcon = "<%=request.getContextPath()%>/images/tree/loading.gif";
		JqTree.treeConfig.focusColor="#FFEE00";
		//menu_folder_closed.gif
		
		var attributes = new Array("sy_zt","xssx","jc_dm_id","jcsjfx_id", "jcsjfx_mc"); //需要加入的xml文件中节点的属性名称，会根据属性名称把数据加载到树节点属性上
		
		var treeObject = new JqTree.TreeObject.create("jcsjfx_id", "jcsjfx_mc", "jcsjfx_fjd");//id text pid
		var actionHandler = new JqTree.ActionHandler.create();
		actionHandler.click.handler = clickHandler; //添加点击的回调事件
		
		//sRootText,oTreeObject,sSrc,sChildSrc
		var jqTree = new JqTree.create(""+jc_dm_mc+"",treeObject,pageUrl,nodePageUrl,attributes,actionHandler,false,false,false);
		window.jqTree = jqTree;
		$("div#reportContainer").html("");	
		$("div#reportHelper").html("");	
		$("div#reportContainer").append(jqTree.tree);
		
		var paramAttribute = new Array();
		paramAttribute['valueAttribute'] = 'jcsjfx_lj';//
		paramAttribute['textAttribute'] = 'jcsjfx_mc';
		jqTree.showSearchPanel("基础代码：","<%=request.getContextPath()%>/txn3010109.ajax?select-key:jc_dm_id="+jc_dm_id,paramAttribute,"after");
		
		jqTree.showMoveNodePanel(new Array("xssx"),moveAction);
		
		function moveAction(nextJqTreeNode,swapAttributes,moveType){
			var jqTreeNode = this;
			//var id = jqTreeNode.getId();
			//var relativeId = nextJqTreeNode.getId();
	
			var thisDataObject = jqTreeNode.getDataObject();
			var first_jcsjfx_nm = jqTreeNode.getAttribute("jcsjfx_id");
			var first_xssx = jqTreeNode.getAttribute("xssx");

			var dataObject = nextJqTreeNode.getDataObject();
			var last_jcsjfx_nm = nextJqTreeNode.getAttribute("jcsjfx_id");
			var last_xssx = nextJqTreeNode.getAttribute("xssx");
			
			var src = "<%=request.getContextPath()%>/txn3010110.ajax";
			src+="?select-key:first_jcsjfx_nm=";
			src+=first_jcsjfx_nm;
			src+="&select-key:first_xssx=";
			src+=first_xssx;
			src+="&select-key:last_jcsjfx_nm=";
			src+=last_jcsjfx_nm;
			src+="&select-key:last_xssx=";
			src+=last_xssx;

			$.get(src,function(xml){
				
				var errorCode = $("error-code",xml).text();
				if(errorCode=="000000"){
					if(moveType=="down"){					 
						jqTree._moveDown(jqTreeNode,nextJqTreeNode,swapAttributes);//实现节点的下调动作
					}else if(moveType=="up"){
						jqTree._moveUp(jqTreeNode,nextJqTreeNode,swapAttributes);//实现节点的上调动作
					}
				}else{
					alert($("error-desc",xml).text());
				}
				
			});
		}
		jqTree.helperPanel.css("width","100%");
		jqTree.helperPanel.css("padding-left","-5px");
		$("div#reportHelper").append(jqTree.helperPanel);
				
		
		function clickHandler(event){		
			 var dataObject = this.jqTreeNode.getDataObject();			
			 var jcsjfx_id = dataObject["jcsjfx_id"];
			 var page = new pageDefine( "/txn3010104.do", "查询基础数据分项", "window" );
			 page.addValue(jcsjfx_id,"primary-key:jcsjfx_id");
			 var win = getIframeByName("modifyFrame");
			if( win != null ){
				page.goPage(null, win);
			}
			
		}		
	});
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询基础数据分项列表"/>
<freeze:errors/>

<freeze:frame property="select-key" width="95%">
   <freeze:hidden property="jc_dm_id" caption="基础代码ID" style="width:95%"/>
   <freeze:hidden property="jc_dm_dm" caption="基础代码" style="width:95%"/>
   <freeze:hidden property="jc_dm_mc" caption="基础代码名称" style="width:95%"/>
   <freeze:hidden property="jc_dm_bzly" caption="标准来源" style="width:95%"/>
   <freeze:hidden property="sy_zt" caption="使用状态" style="width:95%"/>
</freeze:frame>

<table border="0" cellpadding="0" cellspacing="0" width="95%" align="center" style='table-layout:fixed;'>
	<tr height="30"><td colspan="2" align="right">
      <freeze:button name="record_addRecord" caption="增加" txncode="3010103" styleClass="menu" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除" styleClass="menu" onclick="func_record_deleteRecord();"></freeze:button>
      <freeze:button name="record_returnRecord" caption="返回" styleClass="menu" onclick="func_record_return();"></freeze:button></td></tr>
   	  <tr><td height="1" bgcolor="3366FF" width="100%" colspan="2"></td></tr>
	  <tr><td height="5" width="100%" colspan="2"></td></tr>
	  <tr><td colspan="2">
	<div style="float:left;width:37%;">
		<div id="reportContainer" style="width:200px;height:310px; overflow:hidden; overflow-y:auto;"></div>
		<div id="reportHelper" style="height:50px; overflow:hidden;"></div>
	</div>
	<div id="operation" style="float:left; width:63%; height:430px;">
	<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
	  <tr><td><iframe name="modifyFrame" scrolling="no" frameborder="0" id="modifyFrame" src="" style="width:100%; height:100%;"></iframe></td></tr>
	</table>
  </div></td></tr>
</table>
</freeze:body>
</freeze:html>
