<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template master-detail-1/detail-table-insert.jsp --%>
<freeze:html width="650" height="200">
<head>
<title>增加主题信息</title>
</head>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script language="javascript">

// 保 存
function func_record_saveRecord()
{
	saveRecord( '', '保存主题' );
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存主题' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存主题' );	// /txn8000111.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn8000111.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	//getDataSourceInfo();
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
		$(this).next()[0].onclick=function(){
			$(this).prev()[0].click();
			if($(this).prev()[0].checked){
				$(this).css("background-position-y","top");
			}
		};
		if($(this).prev()[0].checked){
			$(this).css("background-position-y","top");
		}
	});
}
//根据db_name查询数据源
function getDataSourceInfo(){
	var page = new pageDefine( "/txn8000116.ajax", "根据db_name查询数据源" );
	page.addParameter("record:db_name","record:db_name");
	page.callback = dataSourceCallBack;
	page.callAjaxService( null, true );
}

function dataSourceCallBack(errorCode, errDesc, xmlResults){
	if(errorCode=="000000"){
		var db_type = _getXmlNodeValue( xmlResults, "/context/select-key/db_type" );
		var jndi_name = _getXmlNodeValue( xmlResults, "/context/select-key/jndi_name" );
		var db_url = _getXmlNodeValue( xmlResults, "/context/select-key/db_url" );
		var db_driver = _getXmlNodeValue( xmlResults, "/context/select-key/db_driver" );
		var db_username = _getXmlNodeValue( xmlResults, "/context/select-key/db_username" );
		var db_password = _getXmlNodeValue( xmlResults, "/context/select-key/db_password" );
		setFormFieldValue("select-key:db_type",db_type);
		setFormFieldValue("select-key:jndi_name",jndi_name);
		setFormFieldValue("select-key:db_url",db_url);
		setFormFieldValue("select-key:db_driver",db_driver);
		setFormFieldValue("select-key:db_username",db_username);
		setFormFieldValue("select-key:db_password",db_password);
	}
}

//组织获取数据库中Schema的参数
function getDbSchemaList() {
  	var	dbType = getFormFieldValue( 'select-key:db_type' );

  	var params = "";
  	if( dbType == 'datasource' ){
  		params += "database:jndi-name=" + getFormFieldValue("select-key:jndi_name");
	}
	else{
		params += "database:db-url=" + getFormFieldValue("select-key:db_url");
		params += "&database:db-driver=" + getFormFieldValue("select-key:db_driver");
	}
  	params += "&database:db-username=" + getFormFieldValue("select-key:db_username");
  	params += "&database:db-password=" + getFormFieldValue("select-key:db_password");
	
	return params;  	
}
_browse.execute( __userInitPage );
</script>
<freeze:body>
<freeze:title caption="增加主题信息"/>
<freeze:errors/>

<freeze:form action="/txn8000113">
  <freeze:block property="record" caption="增加主题信息" width="95%">
      <freeze:button name="record_saveRecord" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_saveAndContinue" caption="保存并继续" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_goBack" caption="关闭" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="sys_rd_system_id" caption="主键" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="sys_rd_data_source_id" caption="数据源ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="db_name" caption="数据源" datatype="string"  style="width:95%" readonly="true"/>
      <freeze:text property="sys_no" caption="主题编号" datatype="string" maxlength="20" style="width:95%" notnull="true"/>
      <freeze:text property="sys_name" caption="主题名称" datatype="string" maxlength="100" style="width:95%" notnull="true"/>
      <freeze:text property="sys_simple" caption="业务系统" datatype="string" maxlength="100" style="width:95%" notnull="true"/>
      
      <freeze:radio property="isshow" caption="是否可用" valueset="布尔型数" notnull="true" style="width:98%" value="1"></freeze:radio>
      <freeze:textarea property="memo" caption="备注" colspan="2" rows="4" maxlength="500" style="width:98%"/>
      <freeze:hidden property="db_username" caption="用户" style="width:95%"/>
      <freeze:hidden property="timestamp" caption="时间戳" datatype="string"  style="width:95%"/>
      <freeze:hidden property="sort" caption="排序" datatype="string" maxlength="16" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
