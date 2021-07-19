<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html>
<head>
<title>�������ݿ⹲�������Ϣ</title>
</head>

<script language="javascript">

function validate(){
	var login_name = getFormFieldValue("record:login_name");
	var reg = new RegExp("^[a-zA-Z][a-zA-Z0-9#$_]{0,29}$");
    
    if (login_name==null||login_name==''){
		alert("���û����롿����Ϊ�գ�");
	    document.getElementById("record:login_name").select();
		return false;
	}
	    
    if (reg.test(login_name)==false){
		alert("����ȷ��д���û����롿!\r\nע�⣺ֻ������ĸ����ĸ��������ɣ�");
	    document.getElementById("record:login_name").select();
		return false;
	}
	
	var user_type = getFormFieldValue("record:user_type");
	var reg = new RegExp("^\s*$");
    if (reg.test(user_type)==true){
		alert("��ѡ���û����͡�!");
		return false;
	}
	
//	var pwd = getFormFieldValue("record:password");
//	if (pwd == ""){
//		alert("����д�����롿!");
//		return false;
//	}
//	var confPwd = getFormFieldValue("record:confirmPassword");
//	if(confPwd != pwd){
//		alert("�����������벻����");
//		return false;
//	}
	return true;
}

// �� ��
function func_record_saveRecord()
{
	if(!validate()){
		return;
	}
	saveRecord( '', '�������ݿ⹲��������' );
}

// ���沢����
function func_record_saveAndContinue()
{
	if(!validate()){
		return;
	}
	saveAndContinue( '', '�������ݿ⹲��������' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	if(!validate()){
		return;
	}
	saveAndExit( '', '�������ݿ⹲��������' );	// /txn50201001.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn50201001.do
}

function checkLoginName(){
    var name = getFormFieldValue("record:login_name");
    if(name!=null&&name!=""){
	  var page = new pageDefine( "txn52101009.ajax", "У���û����Ƿ����");
	  page.addParameter("record:login_name","select-key:login_name");
	  page.callAjaxService("initResponse");   
	} 
}

function initResponse(errCode, errDesc, xmlResults){
	if(errCode != '000000'){
      alert("�������"+errDesc);
      return;
    }else{
      var id = _getXmlNodeValues( xmlResults, "/context/record/sys_db_user_id" );
      if(id!=null&&id!=""){
          alert("�û������Ѿ����ڣ�");
          document.getElementById("record:login_name").value="";
          document.getElementById("record:login_name").focus();
      }
    }
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

//      <freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE" onclick="func_record_saveRecord();"/>
//      <freeze:button name="record_saveAndContinue" caption="���沢����" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�������ݿ⹲�������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn52101003">
  <freeze:block property="record" caption="�������ݿ⹲�������Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="����" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:text property="login_name" caption="�û�����" datatype="string" maxlength="20" minlength="1" style="width:95%" onblur="checkLoginName();"/>
      <freeze:text property="user_name" caption="�û�����" datatype="string" maxlength="20" minlength="1" style="width:95%"/>
      <freeze:select property="user_type" caption="�û�����" valueset="user_type" show="name" style="width:95%" notnull="true"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
