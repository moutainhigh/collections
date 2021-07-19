<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="700" height="200">
<head>
<title>�޸�����Դ��Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	checkDbNameExists();
}
function checkDbNameExists(){
	if(getFormFieldValue("record:db_name")==''){
		alert("δѡ������Դ");
		return;
	}
	var page = new pageDefine( "/txn8000108.ajax", "�������Դ����" );
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
			saveAndExit( '', '��������Դ' );	
		}else{
			alert('������Դ�Ѵ���!');
			return;
		}
	}
}
// �� ��
function func_record_goBack()
{
	goBack();	// /txn8000101.do
}
//��������Դ����
function func_testConn(){
	if(getFormFieldValue("record:db_name")==''){
		alert("δѡ������Դ");
		return;
	}
	var page = new pageDefine( "/txn8000106.ajax", "��������" );
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
		alert("����Դ["+msg+"]���ӳɹ���");
	}
}
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( __userInitPage );
</script>
<freeze:body>
<freeze:title caption="�޸�����Դ��Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn8000102">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_rd_data_source_id" caption="ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸�����Դ��Ϣ" width="95%">
      <freeze:button name="record_saveRecord" caption="��������"  onclick="func_testConn();"/>
      <freeze:button name="record_saveAndExit" caption="����" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�ر�" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="sys_rd_data_source_id" caption="ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="sync_flag" caption="ͬ����־" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:select property="db_name" caption="����Դ����" valueset="�������ļ�ȡ���ݿ��б�" style="width:95%" notnull="true"/>
      <freeze:datebox property="create_date" caption="��������" value="@DATE" numberformat="3" style="width:95%"/>
      <freeze:textarea property="memo" caption="��ע" maxlength="1000" colspan="2" rows="4" style="width:98%"/>
      
      <freeze:hidden property="db_schema" caption="���ݿ�ģʽ" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="db_type" caption="���ݿ�����" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="db_username" caption="�û�" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="db_password" caption="����" maxlength="20" style="width:95%"/>
      <freeze:hidden property="db_url" caption="������Ϣ" datatype="string" maxlength="200"  style="width:98%"/>
      <freeze:hidden property="db_driver" caption="��������" datatype="string" maxlength="64" style="width:95%"/>
      <freeze:hidden property="value_class" caption="����ת����" datatype="string" maxlength="200" style="width:98%"/>
      <freeze:hidden property="merge_flag" caption="�ϲ�����������Ϣ" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:hidden property="db_isolation" caption="������뼶" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="sync_table" caption="�������ݱ�" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="db_transaction" caption="��������" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="sync_date" caption="ͬ������" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="creator" caption="������" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="db_svrname" caption="ʵ����" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="db_address" caption="����IP" datatype="string" maxlength="64" style="width:95%"/>
      <freeze:hidden property="db_port" caption="�˿�" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:hidden property="timestamp" caption="ʱ���" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
