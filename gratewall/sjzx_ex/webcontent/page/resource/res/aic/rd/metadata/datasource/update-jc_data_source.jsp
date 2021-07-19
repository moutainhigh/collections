<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="700" height="200">
<head>
<title>修改数据源信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	checkDbNameExists();
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
	page.callAjaxService("checkCallBack");
}

function checkCallBack(errorCode, errDesc, xmlResults){
	if(errorCode!="000000"){
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
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( __userInitPage );
</script>
<freeze:body>
<freeze:title caption="修改数据源信息"/>
<freeze:errors/>

<freeze:form action="/txn8000102">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_rd_data_source_id" caption="ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改数据源信息" width="95%">
      <freeze:button name="record_saveRecord" caption="测试连接"  onclick="func_testConn();"/>
      <freeze:button name="record_saveAndExit" caption="保存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="关闭" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="sys_rd_data_source_id" caption="ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="sync_flag" caption="同步标志" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:select property="db_name" caption="数据源名称" valueset="从配置文件取数据库列表" style="width:95%" notnull="true"/>
      <freeze:datebox property="create_date" caption="创建日期" value="@DATE" numberformat="3" style="width:95%"/>
      <freeze:textarea property="memo" caption="备注" maxlength="1000" colspan="2" rows="4" style="width:98%"/>
      
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
