<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html>
<head>
<title>增加数据库共享对象信息</title>
</head>

<script language="javascript">

function validate(){
	var login_name = getFormFieldValue("record:login_name");
	var reg = new RegExp("^[a-zA-Z][a-zA-Z0-9#$_]{0,29}$");
    
    if (login_name==null||login_name==''){
		alert("【用户代码】不能为空！");
	    document.getElementById("record:login_name").select();
		return false;
	}
	    
    if (reg.test(login_name)==false){
		alert("请正确填写【用户代码】!\r\n注意：只能由字母或字母和数字组成！");
	    document.getElementById("record:login_name").select();
		return false;
	}
	
	var user_type = getFormFieldValue("record:user_type");
	var reg = new RegExp("^\s*$");
    if (reg.test(user_type)==true){
		alert("请选择【用户类型】!");
		return false;
	}
	
//	var pwd = getFormFieldValue("record:password");
//	if (pwd == ""){
//		alert("请填写【密码】!");
//		return false;
//	}
//	var confPwd = getFormFieldValue("record:confirmPassword");
//	if(confPwd != pwd){
//		alert("两次密码输入不符！");
//		return false;
//	}
	return true;
}

// 保 存
function func_record_saveRecord()
{
	if(!validate()){
		return;
	}
	saveRecord( '', '保存数据库共享对象管理' );
}

// 保存并继续
function func_record_saveAndContinue()
{
	if(!validate()){
		return;
	}
	saveAndContinue( '', '保存数据库共享对象管理' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	if(!validate()){
		return;
	}
	saveAndExit( '', '保存数据库共享对象管理' );	// /txn50201001.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn50201001.do
}

function checkLoginName(){
    var name = getFormFieldValue("record:login_name");
    if(name!=null&&name!=""){
	  var page = new pageDefine( "txn52101009.ajax", "校验用户名是否存在");
	  page.addParameter("record:login_name","select-key:login_name");
	  page.callAjaxService("initResponse");   
	} 
}

function initResponse(errCode, errDesc, xmlResults){
	if(errCode != '000000'){
      alert("处理错误："+errDesc);
      return;
    }else{
      var id = _getXmlNodeValues( xmlResults, "/context/record/sys_db_user_id" );
      if(id!=null&&id!=""){
          alert("用户代码已经存在！");
          document.getElementById("record:login_name").value="";
          document.getElementById("record:login_name").focus();
      }
    }
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

//      <freeze:button name="record_saveRecord" caption="保 存" hotkey="SAVE" onclick="func_record_saveRecord();"/>
//      <freeze:button name="record_saveAndContinue" caption="保存并继续" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="增加数据库共享对象信息"/>
<freeze:errors/>

<freeze:form action="/txn52101003">
  <freeze:block property="record" caption="增加数据库共享对象信息" width="95%">
      <freeze:button name="record_saveAndExit" caption="保存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:text property="login_name" caption="用户代码" datatype="string" maxlength="20" minlength="1" style="width:95%" onblur="checkLoginName();"/>
      <freeze:text property="user_name" caption="用户名称" datatype="string" maxlength="20" minlength="1" style="width:95%"/>
      <freeze:select property="user_type" caption="用户类型" valueset="user_type" show="name" style="width:95%" notnull="true"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
