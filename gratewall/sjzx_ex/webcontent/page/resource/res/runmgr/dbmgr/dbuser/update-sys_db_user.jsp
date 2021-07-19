<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html>
<head>
<title>修改服务对象信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	saveAndExit( '', '保存数据库共享对象管理' );	// /txn50201001.do
}

// 返 回
function func_record_goBack()
{
	//_closeModalWindow(true)	// /txn50201001.do
	goBack();
}

// 删除数据库共享对象
function func_record_deleteRecord()
{
    _showProcessHintWindow( "正在删除，请稍候....." );
	var page = new pageDefine( "/txn52101005.ajax", "删除数据库共享对象" );
	page.addParameter( "record:sys_db_user_id", "record:sys_db_user_id" );
	page.addParameter( "record:login_name", "record:login_name" );
	page.addParameter( "record:user_type","record:user_type");
	page.callAjaxService( "deleteResponse" );
}

function deleteResponse(errCode, errDesc, xmlResults){
	if(errCode != '000000'){
      alert("处理错误："+errDesc);
      return;
    }
    alert("操作成功！");
    _closeModalWindow(true);
    
}
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var hasConfig = getFormFieldValue("record:hasConfig");
	if(hasConfig=='1'){
	   setFormFieldVisible('record_record_deleteRecord',0,false);
	}
}


_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="修改数据库共享对象信息"/>
<freeze:errors/>

<freeze:form action="/txn52101002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_db_user_id" caption="数据库共享对象编号" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改数据库共享对象信息" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_deleteRecord" caption="删 除"  enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="sys_db_user_id" caption="数据库共享对象编号" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="login_name" caption="用户名" datatype="string" />
      <freeze:hidden property="user_type" caption="用户类型" datatype="string" />
      <freeze:hidden property="hasConfig" caption="是否配置：" datatype="string" />
      <freeze:cell property="login_name" caption="用户代码：" datatype="string" style="width:95%" />
      <freeze:text property="user_name" caption="用户名称：" datatype="string" maxlength="20" minlength="1" style="width:95%"/>
      <freeze:cell property="password" caption="用户密码：" datatype="string" style="width:95%" />
      <freeze:cell property="user_type" caption="用户类型：" valueset="user_type" style="width:95%"/>
      <freeze:radio property="state" caption="状态：" value="0" valueset="user_state" show="name" data="code" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
