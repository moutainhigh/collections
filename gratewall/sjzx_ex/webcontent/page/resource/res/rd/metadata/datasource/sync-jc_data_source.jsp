<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="600" height="200">
<head>
<title>同步数据源信息</title>
</head>
<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	var taskTime = getFormFieldValue("record:task_time");
	if(compareTime(taskTime)){
		saveAndExit( '', '定时同步数据源' );
	}
	
}

function checkDbNameExists(){
	if(getFormFieldValue("record:db_name")==''){
		alert("未选择数据源");
		return;
	}
	var page = new pageDefine( "/txn8000108.ajax", "检查数据源存在" );
	page.addParameter("record:db_name","record:db_name");
	page.addParameter("primary-key:sys_rd_data_source_id","record:sys_rd_data_source_id");
	page.addValue("update","record:oper_type");
	page.callback = checkCallBack;
	page.callAjaxService( null, true );
}

function checkCallBack(errorCode, errDesc, xmlResults){
	if(errorCode!="000000"){
		alert(errDesc);
	}else{
		var num = _getXmlNodeValue( xmlResults, "/context/out-temp/count" );
		if(num==0||num=="0"){
			saveAndExit( '', '保存数据源' );	
		}else{
			alert('此数据源已存在!');
			return;
		}
	}
}
// 返 回
function func_record_goBack()
{
	goBack();	// /txn8000101.do
}
//测试数据源连接
function func_testConn(){
	if(getFormFieldValue("record:db_name")==''){
		alert("未选择数据源");
		return;
	}
	var page = new pageDefine( "/txn8000106.ajax", "测试连接" );
	page.addParameter("record:db_name","record:db_name");
	//page.callback = testConnCallBack;
	//page.callAjaxService( null, true );
	page.callAjaxService("testConnCallBack");
}
function testConnCallBack(errorCode, errDesc, xmlResults){
	if(errorCode!="000000"){
		alert(errDesc);
	}else{
		var msg = getFormFieldValue("record:db_name");
		alert("数据源["+msg+"]连接成功！");
	}
}

function func_sync(){
	if(!confirm('是否现在同步此数据源？'))return;
	var page = new pageDefine( "/txn8000107.ajax", "同步数据源","dialog" );
	page.addParameter("record:db_name","record:db_name");
	page.addParameter("record:db_schema","record:db_schema");
	page.addParameter("record:db_username","record:db_username");
	page.addParameter("record:sys_rd_data_source_id","record:sys_rd_data_source_id");
	//page.callback = syncCallBack;
	//page.callAjaxService( null, true);
	page.callAjaxService("syncCallBack");
}

function syncCallBack(errorCode, errDesc, xmlResults){
	if(errorCode!="000000"){
		alert(errDesc);
	}else{
		alert("数据源同步成功！");
		goBack();
	}
}
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var t =getSyncDefaultTime();
	setFormFieldValue("record:task_time",0,t);
}
function test(){
	goBack();
}
/**
 * 
 */
function compareTime(t){
	var _d = new Date();
	var _str = _d.format('yyyyMMddhhmmss');
	return parseFloat(_str)<parseFloat(t+'00');
}
/**
 * 获取默认同步时间
 **/
 function getSyncDefaultTime(){
	var temp = new Date();
	var t = temp.getFullYear();
	var m = temp.getMonth()+1;
	t = m>9 ? t+''+m : t+'0'+m;
	t = temp.getDate()>9 ? t+''+temp.getDate() : t+'0'+temp.getDate();
	var h = temp.getHours();
	if(h<16){
		t = t+"1615"
	}else if(h>16&&h<21){
		h = h+1; 
		t = t+h+'00';
	}else{
		var mm = temp.getMinutes();
		mm = mm<10 ? '0'+mm : mm;
		t = ''+t+h+mm;
	}
	return t;
}
_browse.execute( __userInitPage );
</script>
<freeze:body>
<freeze:title caption="数据源同步"/>
<freeze:errors/>

<freeze:form action="/txn8000109">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_rd_data_source_id" caption="ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="数据源" width="95%">
      <freeze:button name="record_testConn" caption="测试连接"  onclick="func_testConn();"/>
      <freeze:button name="record_SYNC" caption="现在同步"  onclick="func_sync();"/>
      <!--  
      <freeze:button name="record_saveAndExit" caption="定时同步" txncode="8000109" onclick="func_record_saveAndExit();"/>
      -->
      <freeze:button name="record_goBack" caption="关闭"  onclick="test();"/>
      <freeze:hidden property="sys_rd_data_source_id" caption="ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="sync_flag" caption="同步标志" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:text property="db_name" caption="数据源名称" readonly="true" style="width:95%" notnull="true"/>
      <freeze:text property="db_server" caption="数据库类型" datatype="string" readonly="true" maxlength="20" style="width:95%"/>
      <freeze:datebox property="task_time" caption="同步时间" numberformat="5" colspan="2" style="width:98%"/>
      <freeze:hidden property="db_schema" caption="数据库模式" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="db_type" caption="数据库类型" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="db_username" caption="用户" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="db_password" caption="口令" maxlength="20" style="width:95%"/>
      <freeze:hidden property="db_url" caption="连接信息" datatype="string" maxlength="200"  style="width:98%"/>
      <freeze:hidden property="db_driver" caption="驱动程序" datatype="string" maxlength="64" style="width:95%"/>
      <freeze:hidden property="value_class" caption="数据转换类" datatype="string" maxlength="200" style="width:98%"/>
      <freeze:hidden property="merge_flag" caption="合并公共配置信息" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:hidden property="db_isolation" caption="事务隔离级" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="sync_table" caption="更新数据表" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="db_transaction" caption="事务类型" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="sync_date" caption="同步日期" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="creator" caption="创建人" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="db_svrname" caption="实例名" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="db_address" caption="主机IP" datatype="string" maxlength="64" style="width:95%"/>
      <freeze:hidden property="db_port" caption="端口" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:hidden property="timestamp" caption="时间戳" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
