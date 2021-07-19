<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery.min.js"></script>
<%
long totalCount=0l;
DataBus db = (DataBus)request.getAttribute("freeze-databus");
if(db!=null){
    DataBus db2 = db.getRecord("select-key");
	if(db2!=null){
		if(null ==db2.getValue("totalCount")){
			totalCount=5000l;
		}else{
			totalCount = new Long(db2.getValue("totalCount")).longValue();
		}
	}
}

%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询日志查询列表</title>
</head>

<script language="javascript">

//更多日志信息
function moreRecord(){
	var page = new pageDefine( "/txn620100101.do", "增加日志查询" );
	page.addRecord();
}

// 增加日志查询
function func_record_addRecord()
{
	var page = new pageDefine( "insert-view_sys_app_log_query.jsp", "增加日志查询", "modal" );
	page.addRecord();
}

// 修改日志查询
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn620100104.do", "修改日志查询", "modal" );
	page.addParameter( "record:username", "primary-key:username" );
	page.updateRecord();
}

// 删除日志查询
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn620100105.do", "删除日志查询" );
	page.addParameter( "record:username", "primary-key:username" );
	page.deleteRecord( "是否删除选中的记录" );
}
function funTBChanged(){
	var type = getFormFieldValue('select-key:type');
	alert("type:"+type);
	
	if(type=='4'){
	setAllFieldVisible("record:ipaddress",false);
	}else{
	setAllFieldVisible("record:ipaddress",true);
	}
}
//查看日志交易
function func_record_addRecord(){
  var page = new pageDefine( "/txn981214.do", "查看业务日志信息", "modal", 650, 400);
  page.addParameter("record:flowno","primary-key:flowno");
  page.goPage();
}

function exportExcel(){
  var totalCount = "<%=totalCount%>";
  var numPerPage = 5000;
 if(totalCount>numPerPage){
  var page = new pageDefine( "/txn620100120.do", "设置导出页码数", "modal", 300, 100);
  page.addValue(totalCount,"select-key:totalCount");
  page.addValue(numPerPage,"select-key:numPerPage");
  page.goPage();
	 
 }else{
	
 /*
 	$("input[type='button']").each(function(){
 		var s = $(this).val();
 		if(s&&s=="导出"){
 			
 			//$(this).attr("disabled","");
 		//	alert( $(this).attr("disabled") );
 		}
 		//alert( $(this).attr("disabled"))
 	})
 */
 	clickFlag=0;
  	doExport(1,totalCount);	
 }
}

function doExport(fromCount, toCount){
	document.forms[0].action ="/txn620100119.do?select-key:fromCount="+fromCount+"&select-key:toCount="+toCount;
	document.forms[0].submit();
	document.forms[0].action ="/txn620100106.do";
	
}

function func_record_viewRecord(type){
    var page = new pageDefine( "/txn981219.do", "查看查询日志信息", "modal", 650, 400);
    page.addParameter("record:first_page_query_id","primary-key:first_page_query_id");
  	page.goPage();
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
    document.getElementById("label:select-key:orgname").style.color = "black";
	var operationSpan = document.getElementsByName("span_record:operation");
	var types = getFormAllFieldValues("record:type");
	var oper_type = getFormAllFieldValues("record:oper_type");
	
	for (var i=0; i < operationSpan.length; i++){
	    if("一般查询"==types[i]){
	       if(oper_type[i]=='企业主体查询'){
			operationSpan[i].innerHTML = "<a title='查看详细' onclick='setCurrentRowChecked(\"record\");func_record_viewRecord();' href='#'><div class='query'></div></a>";
		   }
		}	
		if("系统管理"==types[i]){
			operationSpan[i].innerHTML = "<a title='查看详细' onclick='setCurrentRowChecked(\"record\");func_record_addRecord();' href='#'><div class='query'></div></a>";
		}
		if("高级查询及下载"==types[i]){
			if(oper_type[i].indexOf('下载')!=-1){
			 operationSpan[i].innerHTML = "<a onclick='setCurrentRowChecked(\"record\");func_record_updateRecord();' title='查看明细' href='#'><div class='detail'></div></a>";
			}else{
			  operationSpan[i].innerHTML = "<a onclick='setCurrentRowChecked(\"record\");func_record_queryRecord();' title='查看明细' href='#'><div class='detail'></div></a>";
			}
		}
		
	}	
}

function func_record_updateRecord()
{
	var page = new pageDefine( "/txn60340004.do", "修改下载日志", "modal" );
	page.addParameter( "record:first_page_query_id", "primary-key:download_log_id" );
	page.updateRecord();
}

function func_record_queryRecord()
{
	var page = new pageDefine( "/txn60340006.do", "查看下载日志", "modal" );
	page.addParameter( "record:first_page_query_id", "primary-key:download_log_id" );
	page.updateRecord();
}

//机构选择
function sjjg_select(){
  selectJG("tree","select-key:jgid_fk","select-key:orgname");
}

function selectYh(){
    var parameter = getFormFieldValue('select-key:jgid_fk');
    if(parameter==""){
      alert("请先选择机构");
      return;
    }
	return 'jgid_fk=' + parameter;
}
// 判断是否中文函数 
function ischinese(s) { 
	var ret=true; 
	for(var i=0;i<s.length;i++) ret=ret && (s.charCodeAt(i)>=10000); return ret; 
 }

function doSub(){
  var username=document.getElementById('select-key:username');
  if(username){
    //alert(username.value+"---"+!ischinese(username.value));
    if(username.value!='' && !ischinese(username.value)){
       alert('用户姓名必须为中文!');
       return false;
    }else{
       return true;
    }
  
  }else{
     return true;
  }
}

function clean(){
 document.getElementById("select-key:orgname").value = "";
 document.getElementById("select-key:jgid_fk").value = "";
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption=""/>
<freeze:errors/>

<freeze:form action="/txn620100106" onsubmit="return doSub();">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%" >
      <freeze:text property="username" caption="用户姓名" datatype="string" maxlength="100" style="width:90%"/>
      <freeze:hidden property="query_time" caption="操作时间" datatype="string" maxlength="32" style="width:90%"/>
      <freeze:datebox property="query_date_start" caption="操作时间" prefix="<table width='90%' border='0' cellpadding='0' cellspacing='0'><tr><td width='45%'>" style="width:100%"/>
    </td><td width='5%'>至</td><td width='45%'>
    <freeze:datebox property="query_date_end" caption="操作时间" style="width:100%" colspan="0"/>
    </td></tr></table> 
      <freeze:text property="orgname" caption="所属机构" style="width:70%"  readonly="true" postfix="（<a id='orgchoice' href='javascript:void(0);' onclick='sjjg_select();'>选择</a>）&nbsp;<a id='clean' href='javascript:void(0);' onclick='clean();'>清空</a>"></freeze:text>
      <freeze:hidden property="jgid_fk"></freeze:hidden>
      <freeze:text property="ipaddress" caption="IP" datatype="string" maxlength="100" style="width:90%"/>
      <freeze:select property="type" caption="日志类型" valueset="操作类型"  style="width:90%" />
  </freeze:block>
<br />
  <freeze:grid property="record" caption="最近半年日志查询列表" keylist="username" width="95%" rowselect="false" navbar="bottom" fixrow="false" checkbox="false">
      <freeze:button name="export"  caption="导出" enablerule="0"  align="right" onclick='exportExcel();'/>
      <freeze:button name="record_addRecord" caption="更多" txncode="620100101" enablerule="0" hotkey="MORE" align="right" onclick="moreRecord();"/>
      <freeze:hidden property="flowno" caption="流水号"   />
      <freeze:hidden property="first_page_query_id" caption="id"/>
      <freeze:cell property="@rowid" caption="序号"  style="width:6%" align="center" />
      <freeze:cell property="username" caption="用户姓名" style="width:8%" align="center"/>
      <freeze:cell property="orgid" caption="所属机构" valueset="机构" style="width:23%" align="center"/>
      <freeze:cell property="type" caption="日志类型" valueset="操作类型" style="width:10%" align="center" />
      <freeze:cell property="oper_type" caption="操作类型"  style="width:12%" align="center" />
      <freeze:hidden property="oper_type"  caption="操作类型"  style="width:8%" />
      <freeze:cell property="query_time" caption="操作时间" style="width:20%" align="center"/>
      <freeze:cell property="ipaddress" caption="IP" style="8%" align="center"/>
  	  <freeze:cell property="operation" caption="操作" align="center" style="width:5%" align="center"/>
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
