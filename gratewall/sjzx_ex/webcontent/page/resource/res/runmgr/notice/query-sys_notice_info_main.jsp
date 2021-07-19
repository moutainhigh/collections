<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="800" height="700">
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery1.1.js"></script>
<title>查询资料列表</title>
<style type="text/css">
	td#td_sys_notice_title span{
		width:80% !important;
	}
</style>
</head>

<script language="javascript">

// 增加通知通告表
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_notice_info.jsp", "增加资料", "modal" );
	page.addRecord();
}

// 修改通知通告表
function func_record_updateRecord()
{
	var index = getSelectedRowid("record");
	var sys_notice_state = getFormFieldText("record:sys_notice_state",index);    
	if(sys_notice_state=="1"){
	    alert("已发布的资料不能修改！");
	    var grid = getGridDefine("record", 0); 
        clickFlag = 0; 
        grid.checkMenuItem(); 	
	    return;
	}
	var page = new pageDefine( "/txn53000004.do", "修改资料", "modal" );
	page.addParameter( "record:sys_notice_id", "primary-key:sys_notice_id" );
	page.updateRecord();
}

// 删除通知通告表
function func_record_deleteRecord()
{
  	var checkboxs= document.getElementsByName('record:_flag');
	var flag = false;
	var sys_notice_state = "";
	for(var i=0;i<checkboxs.length;i++){
		if(checkboxs[i].checked==true){
		    sys_notice_state = getFormFieldValue("record:sys_notice_state",i);
		    if(sys_notice_state=="1"){
			   flag = true;
			   break;
			}
		}
	}
	if(flag){
	   alert("已发布的资料不能删除！");
       var grid = getGridDefine("record", 0); 
       clickFlag = 0; 
       grid.checkMenuItem(); 	   
	   return;
	}
	var page = new pageDefine( "/txn53000005.do", "删除资料" );
	page.addParameter( "record:sys_notice_id", "primary-key:sys_notice_id" );
	page.addParameter( "record:sys_notice_date", "primary-key:sys_notice_date" );
	page.addParameter( "record:sys_notice_filepath", "primary-key:sys_notice_filepath" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	$(document).ready(function(){
		var leftTopCell = $("table#record tr[@class!='grid-headrow']").get();		
		for (var index = 0; index < leftTopCell.length; index ++){
			var hasfj = document.getElementsByName("record:hasfj")[index].value;
			if(hasfj=="Y"){
			   document.getElementsByName("td_sys_notice_title")[index].innerHTML += '<img border="0" src="<%=request.getContextPath()%>/images/fujian.png" onclick="chakanfj('+index+')"></img>';
			   // alert(document.getElementsByName("td_sys_notice_title")[index].outerHTML);
			}
			//var isnew = document.getElementsByName("record:isnew")[index].value;
			//if(isnew=="Y"){
			  // document.getElementsByName("span_record:sys_notice_title")[index].innerHTML += '<img border="0" src="<%=request.getContextPath()%>/images/new.gif"></img>';
			//}			
		}
	});	
}
//查看附件
function chakanfj(index){

    var sys_notice_id = getFormFieldValue('record:sys_notice_id',index);
    $.ajax({
	  type: "post",
	  url: "<%=request.getContextPath()%>/txn53000011.ajax?primary-key:sys_notice_id=" + sys_notice_id,
	  async: false,
	  success: function(xmlResults){
	  	if (xmlResults.selectSingleNode("//context/error-code").text != "000000"){
	  		alert(xmlResults.selectSingleNode("//context/error-desc").text);
	  		return false;
	  	}else{
	  		var sys_notice_id = _getXmlNodeValues( xmlResults, "/context/record/sys_notice_id" );
			var sys_notice_filepath = _getXmlNodeValues( xmlResults, "/context/record/sys_notice_filepath" );
	        var page = new pageDefine("/txn53000006.do", "查看附件", "_self");  
	        page.addValue(sys_notice_id,"record:sys_notice_id");	
	        page.addValue(sys_notice_filepath,"record:sys_notice_filepath");
	        page.goPage();
	  	}
	  }
	}); 
}


function funcQueryInfo(){

	if (event.srcElement.tagName.toUpperCase() == 'IMG'){
		return;
	}
	var index = getSelectedRowid("record");
	var sys_notice_id = getFormFieldText("record:sys_notice_id",index);  
	var page = new pageDefine("/txn53000007.do", "详细信息","_black",1024,768);	
	page.addValue( sys_notice_id, "primary-key:sys_notice_id" );
	page.goPage();
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询资料列表"/>
<freeze:errors/>

<freeze:form action="/txn53000008">
      <freeze:hidden property="select-key:sys_notice_state" caption="发布状态" style="width:60%"/>
  <freeze:grid property="record" caption="资料列表" keylist="sys_notice_id" width="95%" checkbox="false" navbar="bottom" fixrow="false" onclick="funcQueryInfo()" >
      <freeze:hidden property="sys_notice_id" caption="资料ID" style="width:15%" />
      <freeze:hidden property="sys_notice_filepath" caption="附件" style="width:15%" />
      <freeze:hidden property="hasfj" caption="是否包含附件" style="width:15%" />
      <freeze:hidden property="isnew" caption="是否新发布" style="width:15%" />
      <freeze:hidden property="sys_notice_state" caption="发布状态" style="width:20%" />
      <freeze:cell property="@rowid" caption="序号" align="middle" style="width:6%"/>
      <freeze:cell property="sys_notice_title" caption="标题" style="width:25%" />       
      <freeze:hidden property="sys_notice_promulgator" caption="发布人" style="width:20%" />
      <freeze:hidden property="sys_notice_org" caption="发布单位" style="width:20%" />
      <freeze:cell property="sys_notice_date" caption="发布时间" style="width:25%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
