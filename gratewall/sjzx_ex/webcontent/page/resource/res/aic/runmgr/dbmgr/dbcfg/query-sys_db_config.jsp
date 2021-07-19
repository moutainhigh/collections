<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ͼ�����б�</title>
<style type="text/css">
.activerow {
	cursor:auto;
}
</style>

</head>

<script language="javascript">

// ���ӷ�������
function func_record_addRecord()
{
	var page = new pageDefine( "main-config.jsp", "������ͼ����");
	page.addRecord();
}
// ɾ�����ݿ⹲�����
function func_record_deleteRecord(index)
{
    var config_type = getFormFieldValue("record:config_type",index);
	var page = new pageDefine( "txn52103009.do", "ѡ��ɾ�����ݿ���ͼ" );
	page.addValue( getFormFieldValue("record:sys_db_config_id",index), "record:sys_db_config_id" );
	page.addValue( getFormFieldValue("record:sys_db_user_id",index), "record:sys_db_user_id" );
	page.addValue( getFormFieldValue("record:config_name",index), "record:config_name" );
	page.addValue( getFormFieldValue("record:login_name",index), "record:login_name" );
	page.addValue( getFormFieldValue("record:grant_table",index), "record:grant_table" );
	if(config_type=='01'){
		page.addValue( getFormFieldValue("record:table_no",index), "record:old_table" );
	}else{
		page.addValue( getFormFieldValue("record:permit_column",index), "record:old_table" );
	}
	page.addValue( getFormFieldValue("record:user_type",index), "record:user_type" );
	page.addValue( config_type,"record:config_type");
	page.deleteRecord("ȷ��ɾ��������");
}
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
    var operationSpan = document.getElementsByName("span_record:oper");
	for (var i=0; i < operationSpan.length; i++){
	    operationSpan[i].innerHTML += "<a onclick='func_record_deleteRecord(" + i + ");' href='#'>ɾ��</a>&nbsp;&nbsp;<a onclick='func_viewLog(" + i + ");' href='#'>�鿴��־</a>";
	}
}

function func_viewLog(index){
    var login_name = getFormFieldValue("record:login_name",index);
    var config_name = getFormFieldValue("record:config_name",index);
    var user_type = getFormFieldValue("record:user_type",index);
    
	var page = new pageDefine( "/txn52103008.do", "��ͼ��־");
	page.addValue(login_name,"select-key:login_name");
	page.addValue(config_name,"select-key:config_name");
	page.addValue(user_type,"select-key:user_type");
	page.goPage();  
}
_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ͼ�����б�"/>
<freeze:errors/>

<freeze:form action="/txn52103001">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:text property="config_name" caption="�������ƣ�"  style="width:60%"/>
      <freeze:select property="config_type" caption="�������ͣ�"  valueset="���ݿ���ͼ��������" style="width:60%"/>
      <freeze:select property="sys_db_user_id" caption="�û����ƣ�" valueset="���ݿ⹲��������" style="width:60%"/>
      <freeze:select property="state" caption="�û�״̬��" valueset="user_state" style="width:60%"/>
      <freeze:select property="sys_db_view_id" caption="��ͼ���ƣ�" valueset="���ݿ⹲����ͼ����" style="width:60%"/>
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="��ͼ�����б�" keylist="sys_db_config_id" checkbox="false" multiselect="false" width="95%" navbar="bottom" fixrow="false" rowselect="false">
      <freeze:button name="record_addRecord" caption="����" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();" visible="false"/>
      <freeze:button name="record_deleteRecord" caption="ɾ��"  enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();" visible="false"/>

      <freeze:hidden property="sys_db_config_id" caption="��ͼ���ñ���" />
      <freeze:hidden property="config_name" caption="��������" />
      <freeze:hidden property="sys_db_view_id" caption="��ͼ����" />
      <freeze:hidden property="sys_db_user_id" caption="�û�����" />
      <freeze:hidden property="user_type" caption="�û�����" />
      <freeze:hidden property="grant_table" caption="grant_table" />
      <freeze:hidden property="table_no" caption="table_no" />
      <freeze:hidden property="config_type" caption="config_type" />
      <freeze:hidden property="permit_column" caption="permit_column" />
      <freeze:cell property="@rowid" caption="���" style="width:5%" align="center"/>
      <freeze:cell property="config_name" caption="��������" style="width:14%" align="left" />
      <freeze:cell property="login_name" caption="�û�����" style="width:8%" align="left" />
      <freeze:cell property="user_name" caption="�û�����" style="width:15%" align="left" />
      <freeze:cell property="state" caption="״̬" valueset="user_state" style="width:8%" align="left" />
      <freeze:cell property="view_code" caption="��ͼ����" style="width:10%" align="left" />
      <freeze:cell property="view_name" caption="��ͼ����" style="width:15%" align="left" />
      <freeze:cell property="config_type" caption="��������" style="width:10%" valueset="���ݿ���ͼ��������" align="left" />
      <freeze:cell property="oper" caption="����" style="width:15%" align="center" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
