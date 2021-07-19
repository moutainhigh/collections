<%@page import="com.gwssi.common.database.DBPoolConnection"%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ taglib uri="/WEB-INF/gwssi.tld" prefix="gwssi" %>
<%@ page import="cn.gwssi.common.context.TxnContext" %>
<%@ page import="cn.gwssi.common.context.DataBus" %>
<freeze:html width="650" height="350">
<head>
<title>查询系统使用情况报告列表</title>
<%
String path=java.util.ResourceBundle.getBundle("app").getString("docFilePath");
TxnContext context = (TxnContext)request.getAttribute("freeze-databus");
String userName=context.getRecord("oper-data").getValue("oper-name");
%>
<script type="text/javascript" src="/script/lib/jquery171.js"></script>
<jsp:include page="/page/share/common/top_datepicker.html"></jsp:include>
</head>

<script type="text/javascript">

// 增加系统使用情况报告
function func_record_addRecord()
{
	var page = new pageDefine( "insert-log_report_create.jsp", "增加系统使用情况报告", "modal" );
	page.addRecord();
}

// 增加系统使用情况报告
function func_record_addRecord1()
{
	var page = new pageDefine( "insert-log_report_create1.jsp", "增加系统使用情况报告", "modal" );
	page.addRecord();
}

// 修改系统使用情况报告
function record_updateRecord(id,state)
{
    clickFlag=0;
	var state = getFormFieldValues('record:state');
	if(state=="发布"){
	   alert("已发布的报告不能修改!");
	} else {
		var page = new pageDefine( "/txn620200104.do", "修改系统使用情况报告");
		page.addValue( id, "primary-key:log_report_create_id" );
		page.updateRecord();
	}
	
}

//退回报告
function func_record_returnRecord(id,state){
	//clickFlag=0;
	if(confirm("是否确认退回该报告？")){
		if(state!="发布"){
			alert("只有发布的报告才可以退回");
		}else{
			var page = new pageDefine( "/txn620200109.do", "退回系统使用情况报告");
			page.addValue(id, "select-key:log_report_create_id");
			page.updateRecord();
		}
	}
}

// 发布系统使用情况报告
function func_record_publishRecord(id,state)
{
	//clickFlag=0;
	if(state=="发布"){
		alert("报告已发布");
	}else{
		if(confirm("是否确认发布该报告？")){
			var page = new pageDefine( "/txn620200107.do", "修改系统使用情况报告");
			page.addValue( id, "select-key:log_report_create_id" );
			page.updateRecord();
		}
	}
	
}

// 删除系统使用情况报告
function func_record_deleteRecord(id,state)
{	
	if(state=='发布'){//生成
		alert("已发布的报告不可以删除");
	}else {
		var page = new pageDefine( "/txn620200105.do", "删除系统使用情况报告" );
		page.addValue( id, "primary-key:log_report_create_id" );
		//page.addParameter( "record:path", "primary-key:path" );
		page.deleteRecord( "是否删除选中的记录" );
	}
	
}

// 修改系统使用情况报告
function addOperRecord(id)
{
  var page = new pageDefine( "/txn620200108.ajax", "修改系统使用情况报告");
  page.addValue( id, "select-key:log_report_create_id" );
  page.callAjaxService('addRecordBack');
}

function addRecordBack(errCode, errDesc, xmlResults){
      if(errCode == '000000'){
        
	  }
}

function toTurn(id,state){
  if(state=="发布"){
  	addOperRecord(id);
  }
  
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	//$('#t2').find('.pack-list li:eq(1)').hide();
	var rootPath = '<%=request.getContextPath()%>';
	var operate = getFormAllFieldValues("record:operate1");
	var path = getFormAllFieldValues("record:path");
	var filename = getFormAllFieldValues("record:filename");
	var states = getFormAllFieldValues("record:state");
	var ids = getFormAllFieldValues("record:log_report_create_id");
	var reportNames = getFormAllFieldValues("record:report_name");
	var creator = getFormAllFieldValues("record:creator");
	
	for(var i=0;i<operate.length;i++){
		var url = filename[i];
		var id = ids[i];
		var state = states[i]; 
		var path1 = path[i];
		var reportName = reportNames[i];
	    var userName='<%=userName%>';
	     var ihtml ="";
		if(state=="发布" || userName==creator[i]){
		    var rname=encodeURI(reportName)+'.doc';
  	        var url = "/downloadFile?file=<%=path%>"+url+"&&fileName="+rname;
  	        ihtml ="<a title='下载' href='"+url+"' onclick='toTurn(\""+id+"\",\""+state+"\")';><div class='download'></div></a>";
		}
		ihtml+="&nbsp;<a title='修改' href='javascript:void(\"0\");' onclick='record_updateRecord(\""+id+"\",\""+state+"\")';><div class='edit'></div></a>";
		if(state!="发布"){
	      ihtml+="&nbsp;<a title='发布' href='javascript:void(\"0\");' onclick='func_record_publishRecord(\""+id+"\",\""+state+"\")';><div class='run'></div></a>";
		}else{
		  ihtml+="&nbsp;<a title='退回' href='javascript:void(\"0\");' onclick='func_record_returnRecord(\""+id+"\",\""+state+"\")';><div class='stop'></div></a>";
		}
	    ihtml+="&nbsp;<a title='删除' href='javascript:void(\"0\");' onclick='func_record_deleteRecord(\""+id+"\",\""+state+"\")';><div class='delete'></div></a>";
		document.getElementsByName("span_record:operate1")[i].innerHTML = ihtml;
		
	}
	
	$(".radioNew").each(function(index){
		$($(this).prev()[0]).css("display","");
		$($(this).prev()[0]).css("margin-left","-1000");
		$(this).click(function(){
			$(".radioNew").css("background-position-y","bottom");
			$(this).prev()[0].click();
			if($(this).prev()[0].checked){
				$(this).css("background-position-y","top");
			}
		});
		if($(this).prev()[0].checked){
			$(this).css("background-position-y","top");
		}
	});
}


_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询系统使用情况报告列表"/>
<freeze:errors/>
<gwssi:panel action="txn620200101" target="" parts="t1,t2" styleClass="wrapper">
 	<gwssi:cell id="t1" name="报告状态 " key="state" data="state" />
	<gwssi:cell id="t2" name="建立日期 " key="created_time" data="created_time" date="true"/>
   
 </gwssi:panel>
<freeze:form action="/txn620200101">
 
<br />
  <freeze:grid property="record" caption="查询系统使用情况报告列表" keylist="log_report_create_id" width="95%" navbar="bottom" checkbox="false"  fixrow="false" multiselect="false">
      <freeze:button name="record_addRecord" caption="增加" txncode="620200103" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:hidden property="log_report_create_id" caption="日志报告id" />
      <freeze:cell property="@rowid" caption="序号" style="width:5%"  align="center"/>
      <freeze:cell property="report_name" caption="报告名称" style="width:30%"  align="center"/>
      <freeze:cell property="create_date" caption="建立日期" style="width:10%" align="center"/>
      <freeze:cell property="last_mender" caption="最后修改者" style="width:12%" align="center"/>
      <freeze:cell property="publish_date" caption="发布日期" style="width:10%" align="center"/>
      <freeze:cell property="publish_person" caption="发布人" style="width:12%" align="center"/>
      <freeze:cell property="state" caption="状态" valueset="报告状态" style="width:5%" align="center"/>
      <freeze:cell property="operate1" caption="操作" style="width:15%" align="center"/>
      <freeze:hidden property="creator" caption="上传人" style="width:22%" />
      <freeze:hidden property="report_type" caption="报告类型" style="width:20%" visible="false" />
      <freeze:hidden property="filename" caption="文件名" style="width:20%" visible="false" />
      <freeze:hidden property="path" caption="路径" style="width:20%"  />
      <freeze:hidden property="timestamp" caption="时间戳" style="width:12%" />
  </freeze:grid>
<a id="testa" style="display:none;">aaa</a>
</freeze:form>
</freeze:body>
</freeze:html>
