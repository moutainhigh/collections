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
<title>��ѯ�������ݷ����б�</title>
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
		       alert("�����ݷ�����ͣ�ã��������ӽڵ�");
		       return;
		 }
	 }
     var jcsjfx_fjd=treeNode.getId();
 	 var jc_dm_id = getFormFieldValue("select-key:jc_dm_id");
     if(jcsjfx_fjd=="")
     {
     szcc=1;

     var page=new pageDefine("/txn3010108.do?record:jcsjfx_fjd="+jcsjfx_fjd,"��������Ŀ¼������Ϣ","window");   
     
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
	 var page = new pageDefine( "/txn3010108.do?record:jcsjfx_fjd="+jcsjfx_fjd, "��������Ŀ¼������Ϣ", "window" );
 
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
	var page = new pageDefine( "/txn3010104.do", "�޸ķ�����", "modal" );
	page.addParameter( "record:jcsjfx_id", "primary-key:jcsjfx_id" );
	page.updateRecord();
}


function func_record_deleteRecord()
{        
         //var syml_zt=getFormFieldValue("select-key:syml_zt");
    
	     var selectMessage="����ѡ��Ҫ�����Ľڵ�";
	     var treeNode=window.jqTree.getSelectedNode();
	     if(treeNode==null)return;
	     var id=treeNode.getId();
	     if(id==""){
	        alert("����㲻��ɾ����");
	        return false;
	     }
	     var pid=treeNode.getPid();
	     
	     if(treeNode.getAttribute("hasChildren")=="true"){
	      alert('ѡ�еķ�����Ŀ¼�а����ӷ�����Ŀ¼�����ܱ�ɾ��');
	      return;
	     }
	     var dataObject = treeNode.getDataObject();     
         var jcsjfx_mc=dataObject["jcsjfx_mc"];      	     
		 var page = new pageDefine( "/txn3010105.ajax", "ɾ��������" );	
		 		  
		 page.addValue(id, "primary-key:jcsjfx_id");
		 page.addValue(jcsjfx_mc,"record:jcsjfx_mc");
		 //page.addValue(syml_zt,"select-key:syml_zt");
		 		 
	    if(window.confirm("�Ƿ�ɾ��ѡ�еĽڵ㣡")){
		  	page.callAjaxService("delete_ajax");
		}		
 }
	
	// ɾ���ڵ���ҳ��Ľڵ����ˢ�²���
function delete_ajax(errCode,errDesc,xmlResults){
	  //�����ڽ�������صĲ�������ܵ��ôη���
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

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{

	$(document).ready(function() {
		var jc_dm_id = getFormFieldValue("select-key:jc_dm_id");
		var jc_dm_mc = getFormFieldValue("select-key:jc_dm_mc");
		var pageUrl ="<%=request.getContextPath()%>/txn3010106.ajax?select-key:jc_dm_id="+jc_dm_id;//��õ�һ��ڵ�
		var nodePageUrl ="<%=request.getContextPath()%>/txn3010107.ajax";//���ݸ��ڵ�id��ýڵ㣬����ݸ��������ƺʹ���ֵ���ɲ�ѯ����select-key:dm_fwxml=10009
		
		JqTree.treeConfig.loadingText="������...";
		JqTree.treeConfig.rootIcon="<%=request.getContextPath()%>/images/tree/folder.png";
		JqTree.treeConfig.folderIcon="<%=request.getContextPath()%>/images/tree/folder.png";
		JqTree.treeConfig.openFolderIcon="<%=request.getContextPath()%>/images/tree/openfolder.png";
		JqTree.treeConfig.fileIcon = "<%=request.getContextPath()%>/images/tree/file.png";
		JqTree.treeConfig.loadingIcon = "<%=request.getContextPath()%>/images/tree/loading.gif";
		JqTree.treeConfig.focusColor="#FFEE00";
		//menu_folder_closed.gif
		
		var attributes = new Array("sy_zt","xssx","jc_dm_id","jcsjfx_id", "jcsjfx_mc"); //��Ҫ�����xml�ļ��нڵ���������ƣ�������������ư����ݼ��ص����ڵ�������
		
		var treeObject = new JqTree.TreeObject.create("jcsjfx_id", "jcsjfx_mc", "jcsjfx_fjd");//id text pid
		var actionHandler = new JqTree.ActionHandler.create();
		actionHandler.click.handler = clickHandler; //��ӵ���Ļص��¼�
		
		//sRootText,oTreeObject,sSrc,sChildSrc
		var jqTree = new JqTree.create(""+jc_dm_mc+"",treeObject,pageUrl,nodePageUrl,attributes,actionHandler,false,false,false);
		window.jqTree = jqTree;
		$("div#reportContainer").html("");	
		$("div#reportHelper").html("");	
		$("div#reportContainer").append(jqTree.tree);
		
		var paramAttribute = new Array();
		paramAttribute['valueAttribute'] = 'jcsjfx_lj';//
		paramAttribute['textAttribute'] = 'jcsjfx_mc';
		jqTree.showSearchPanel("�������룺","<%=request.getContextPath()%>/txn3010109.ajax?select-key:jc_dm_id="+jc_dm_id,paramAttribute,"after");
		
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
						jqTree._moveDown(jqTreeNode,nextJqTreeNode,swapAttributes);//ʵ�ֽڵ���µ�����
					}else if(moveType=="up"){
						jqTree._moveUp(jqTreeNode,nextJqTreeNode,swapAttributes);//ʵ�ֽڵ���ϵ�����
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
			 var page = new pageDefine( "/txn3010104.do", "��ѯ�������ݷ���", "window" );
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
<freeze:title caption="��ѯ�������ݷ����б�"/>
<freeze:errors/>

<freeze:frame property="select-key" width="95%">
   <freeze:hidden property="jc_dm_id" caption="��������ID" style="width:95%"/>
   <freeze:hidden property="jc_dm_dm" caption="��������" style="width:95%"/>
   <freeze:hidden property="jc_dm_mc" caption="������������" style="width:95%"/>
   <freeze:hidden property="jc_dm_bzly" caption="��׼��Դ" style="width:95%"/>
   <freeze:hidden property="sy_zt" caption="ʹ��״̬" style="width:95%"/>
</freeze:frame>

<table border="0" cellpadding="0" cellspacing="0" width="95%" align="center" style='table-layout:fixed;'>
	<tr height="30"><td colspan="2" align="right">
      <freeze:button name="record_addRecord" caption="����" txncode="3010103" styleClass="menu" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ��" styleClass="menu" onclick="func_record_deleteRecord();"></freeze:button>
      <freeze:button name="record_returnRecord" caption="����" styleClass="menu" onclick="func_record_return();"></freeze:button></td></tr>
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
