<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html>
<head>
<title>�޸ķ��������Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '�������ݿ⹲��������' );	// /txn50201001.do
}

// �� ��
function func_record_goBack()
{
	//_closeModalWindow(true)	// /txn50201001.do
	goBack();
}

// ɾ�����ݿ⹲�����
function func_record_deleteRecord()
{
    _showProcessHintWindow( "����ɾ�������Ժ�....." );
	var page = new pageDefine( "/txn52101005.ajax", "ɾ�����ݿ⹲�����" );
	page.addParameter( "record:sys_db_user_id", "record:sys_db_user_id" );
	page.addParameter( "record:login_name", "record:login_name" );
	page.addParameter( "record:user_type","record:user_type");
	page.callAjaxService( "deleteResponse" );
}

function deleteResponse(errCode, errDesc, xmlResults){
	if(errCode != '000000'){
      alert("�������"+errDesc);
      return;
    }
    alert("�����ɹ���");
    _closeModalWindow(true);
    
}
// ����������ӣ�ҳ�������ɺ���û���ʼ������
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
<freeze:title caption="�޸����ݿ⹲�������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn52101002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_db_user_id" caption="���ݿ⹲�������" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸����ݿ⹲�������Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ ��"  enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="sys_db_user_id" caption="���ݿ⹲�������" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="login_name" caption="�û���" datatype="string" />
      <freeze:hidden property="user_type" caption="�û�����" datatype="string" />
      <freeze:hidden property="hasConfig" caption="�Ƿ����ã�" datatype="string" />
      <freeze:cell property="login_name" caption="�û����룺" datatype="string" style="width:95%" />
      <freeze:text property="user_name" caption="�û����ƣ�" datatype="string" maxlength="20" minlength="1" style="width:95%"/>
      <freeze:cell property="password" caption="�û����룺" datatype="string" style="width:95%" />
      <freeze:cell property="user_type" caption="�û����ͣ�" valueset="user_type" style="width:95%"/>
      <freeze:radio property="state" caption="״̬��" value="0" valueset="user_state" show="name" data="code" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
