<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.context.TxnContext" %>
<%@ page import="cn.gwssi.common.context.DataBus" %>
<freeze:html width="650" height="350">
<head>
<title>查询系统使用情况报告列表</title>
<%
String path=java.util.ResourceBundle.getBundle("app").getString("docFilePath");
TxnContext context = (TxnContext)request.getAttribute("freeze-databus");
String userName=context.getRecord("oper-data").getValue("oper-name");
System.out.println(userName);
%>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script language="javascript">

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
function record_updateRecord()
{
    clickFlag=0;
	var state = getFormFieldValues('record:state');
	if(state=="发布"){
	   alert("已发布的报告不能修改!");
	} else {
		var page = new pageDefine( "/txn620200104.do", "修改系统使用情况报告");
		page.addParameter( "record:log_report_create_id", "primary-key:log_report_create_id" );
		page.updateRecord();
	}
	
}

//退回报告
function func_record_returnRecord(){
	clickFlag=0;
	if(confirm("是否确认退回该报告？")){
		var state = getFormFieldValues('record:state');
		if(state!="发布"){
			alert("只有发布的报告才可以退回");
			//return;
			//_formSubmit(null, '正在处理记录 ... ...');
		}else{
			var page = new pageDefine( "/txn620200109.do", "退回系统使用情况报告");
			page.addParameter("record:log_report_create_id", "select-key:log_report_create_id");
			page.updateRecord();
		}
	}
}

// 发布系统使用情况报告
function func_record_publishRecord()
{
	clickFlag=0;
	var state = getFormFieldValues('record:state');
	if(state=="发布"){
		alert("报告已发布");
		//return;
		//_formSubmit( null, '正在处理记录 ... ...');
	}else{
		var page = new pageDefine( "/txn620200107.do", "修改系统使用情况报告");
		page.addParameter( "record:log_report_create_id", "select-key:log_report_create_id" );
		page.updateRecord();
	}
	
}

// 删除系统使用情况报告
function func_record_deleteRecord()
{
	clickFlag=0;
	var state = getFormFieldValues('record:state');
	
	if(state=='发布'){//生成
		alert("已发布的报告不可以删除");
		//return;
	//	_formSubmit( null, '正在处理记录 ... ...');
		
	}else {
		var page = new pageDefine( "/txn620200105.do", "删除系统使用情况报告" );
		page.addParameter( "record:log_report_create_id", "primary-key:log_report_create_id" );
		page.addParameter( "record:path", "primary-key:path" );
		//page.addParameter( id, "primary-key:log_report_create_id" );
		//page.addParameter( path, "primary-key:path" );
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
		if(state=="发布" || userName==creator[i]){
		    var rname=encodeURI(reportName)+'.doc';
  	        var url = "/downloadFile?file=<%=path%>"+url+"&&fileName="+rname;
  	        var ihtml ="<a title='下载' href='"+url+"' onclick='toTurn(\""+id+"\",\""+state+"\")';><div class='download'></div></a>";
			document.getElementsByName("span_record:operate1")[i].innerHTML = ihtml;
		}
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

<freeze:form action="/txn620200101">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:hidden property="log_report_create_id" caption="日志报告id" datatype="string" maxlength="32" style="width:90%"/>
      <freeze:text property="report_name" caption="报告名称"  style="width:90%"/>
      <freeze:select property="state" caption="报告状态" valueset="报告状态" style="width:90%"/>
      <freeze:datebox property="create_date_start" caption="建立日期" prefix="<table width='90%' border='0' cellpadding='0' cellspacing='0'><tr><td width='45%'>" style="width:100%"/>
    </td><td width='5%'>至</td><td width='45%'>
    <freeze:datebox property="create_date_end" caption="建立日期" style="width:100%" colspan="0"/>
    </td></tr></table>
      <freeze:datebox property="publish_date_start" caption="发布日期" prefix="<table width='90%' border='0' cellpadding='0' cellspacing='0'><tr><td width='45%'>" style="width:100%"/>
    </td><td width='5%'>至</td><td width='45%'>
    <freeze:datebox property="publish_date_end" caption="发布日期" style="width:100%" colspan="0"/>
    </td></tr></table>
  </freeze:block>
<br />
  <freeze:grid property="record" caption="查询系统使用情况报告列表" keylist="log_report_create_id" width="95%" navbar="bottom" fixrow="false" multiselect="false">
      <freeze:button name="record_addRecord" caption="增加" txncode="620200103" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改" txncode="620200104" enablerule="1" hotkey="UPDATE" align="right" onclick="record_updateRecord();"/>
      <freeze:button name="record_publishRecord" caption="发布" txncode="620200107" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_publishRecord();"/>
      <freeze:button name="record_publishRecord" caption="退回" txncode="620200109" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_returnRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除" txncode="620200105" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:hidden property="log_report_create_id" caption="日志报告id" style="width:10%"/>
      <freeze:cell property="report_name" caption="报告名称" style="width:30%"  />
      <freeze:cell property="create_date" caption="建立日期" style="width:15%" />
      <freeze:cell property="last_mender" caption="最后修改者" style="width:10%" />
      <freeze:cell property="publish_date" caption="发布日期" style="width:10%" />
      <freeze:cell property="publish_person" caption="发布人" style="width:10%" />
      <freeze:cell property="state" caption="状态" valueset="报告状态" style="width:8%"/>
      <freeze:cell property="operate1" caption="操作" style="width:5%" />
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
