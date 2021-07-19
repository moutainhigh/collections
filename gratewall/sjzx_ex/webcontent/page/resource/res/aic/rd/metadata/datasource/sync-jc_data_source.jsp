<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="600" height="200">
<head>
<title>ͬ������Դ��Ϣ</title>
</head>
<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	var taskTime = getFormFieldValue("record:task_time");
	if(compareTime(taskTime)){
		saveAndExit( '', '��ʱͬ������Դ' );
	}
	
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
	page.callback = checkCallBack;
	page.callAjaxService( null, true );
}

function checkCallBack(errorCode, errDesc, xmlResults){
	if(errorCode!="000000"){
		alert(errDesc);
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

function func_sync(){
	if(!confirm('�Ƿ�����ͬ��������Դ��'))return;
	var page = new pageDefine( "/txn8000107.ajax", "ͬ������Դ","dialog" );
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
		alert("����Դͬ���ɹ���");
		goBack();
	}
}
// ����������ӣ�ҳ�������ɺ���û���ʼ������
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
 * ��ȡĬ��ͬ��ʱ��
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
<freeze:title caption="����Դͬ��"/>
<freeze:errors/>

<freeze:form action="/txn8000109">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_rd_data_source_id" caption="ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="����Դ" width="95%">
      <freeze:button name="record_testConn" caption="��������"  onclick="func_testConn();"/>
      <freeze:button name="record_SYNC" caption="����ͬ��"  onclick="func_sync();"/>
      <!--  
      <freeze:button name="record_saveAndExit" caption="��ʱͬ��" txncode="8000109" onclick="func_record_saveAndExit();"/>
      -->
      <freeze:button name="record_goBack" caption="�ر�"  onclick="test();"/>
      <freeze:hidden property="sys_rd_data_source_id" caption="ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="sync_flag" caption="ͬ����־" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:text property="db_name" caption="����Դ����" readonly="true" style="width:95%" notnull="true"/>
      <freeze:text property="db_server" caption="���ݿ�����" datatype="string" readonly="true" maxlength="20" style="width:95%"/>
      <freeze:datebox property="task_time" caption="ͬ��ʱ��" numberformat="5" colspan="2" style="width:98%"/>
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
