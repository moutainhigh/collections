<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ�û����������б�</title>
</head>

<script language="javascript">

// �����û���������
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_svr_limit.jsp", "�����û���������", "modal" );
	page.addRecord();
}

// �޸��û���������
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn50011704.do", "�޸��û���������", "modal" );
	page.addParameter( "record:sys_svr_limit_id", "primary-key:sys_svr_limit_id" );
	page.updateRecord();
}

// ɾ���û���������
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn50011705.do", "ɾ���û���������" );
	page.addParameter( "record:sys_svr_limit_id", "primary-key:sys_svr_limit_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ�û����������б�"/>
<freeze:errors/>

<freeze:form action="/txn50011701">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:text property="sys_svr_user_id" caption="�û�ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="sys_svr_service_id" caption="����ID" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

  <freeze:grid property="record" caption="��ѯ�û����������б�" keylist="sys_svr_limit_id" width="95%" navbar="bottom" fixrow="true">
      <freeze:button name="record_addRecord" caption="�����û���������" txncode="50011703" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸��û���������" txncode="50011704" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ���û���������" txncode="50011705" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="sys_svr_limit_id" caption="�û�������������" style="width:12%" />
      <freeze:cell property="sys_svr_user_id" caption="�û�ID" style="width:12%" />
      <freeze:cell property="sys_svr_service_id" caption="����ID" style="width:12%" />
      <freeze:cell property="is_limit_week" caption="�Ƿ����Ʊ�����" style="width:10%" />
      <freeze:cell property="is_limit_time" caption="�Ƿ�����ʱ��" style="width:10%" />
      <freeze:cell property="is_limit_number" caption="�Ƿ����ƴ���" style="width:10%" />
      <freeze:cell property="is_limit_total" caption="�Ƿ�����������" style="width:10%" />
      <freeze:cell property="limit_week" caption="��������" style="width:10%" />
      <freeze:cell property="limit_time" caption="����ʱ��" style="width:12%" />
      <freeze:cell property="limit_start_time" caption="��ֹ����ʱ��" style="width:12%" />
      <freeze:cell property="limit_end_time" caption="��������ʱ��" style="width:12%" />
      <freeze:cell property="limit_number" caption="���ƴ���" style="width:12%" />
      <freeze:cell property="limit_total" caption="����������" style="width:12%" />
      <freeze:cell property="limit_desp" caption="��������" style="width:20%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
