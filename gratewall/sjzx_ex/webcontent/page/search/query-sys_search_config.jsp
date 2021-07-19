<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯȫ�ļ����б�</title>
</head>

<script language="javascript">

// ����ȫ�ļ���
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_search_config.jsp", "����ȫ�ļ���", "modal" );
	page.addRecord();
}

// �޸�ȫ�ļ���
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn50030104.do", "�޸�ȫ�ļ���", "modal" );
	page.addParameter( "record:sys_search_config_id", "primary-key:sys_search_config_id" );
	page.updateRecord();
}

// ɾ��ȫ�ļ���
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn50030105.do", "ɾ��ȫ�ļ���" );
	page.addParameter( "record:sys_search_config_id", "primary-key:sys_search_config_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯȫ�ļ����б�"/>
<freeze:errors/>

<freeze:form action="/txn50030101">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:text property="sys_search_config_id" caption="��������ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="sys_svr_user_id" caption="�û�ID" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

  <freeze:grid property="record" caption="��ѯȫ�ļ����б�" keylist="sys_search_config_id" width="95%" navbar="bottom" fixrow="true">
      <freeze:button name="record_addRecord" caption="����ȫ�ļ���" txncode="50030103" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸�ȫ�ļ���" txncode="50030104" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ��ȫ�ļ���" txncode="50030105" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="sys_search_config_id" caption="��������ID" style="width:18%" />
      <freeze:cell property="sys_svr_user_id" caption="�û�ID" style="width:18%" />
      <freeze:cell property="permit_subject" caption="��Ȩ����" style="width:20%" visible="false" />
      <freeze:cell property="create_by" caption="������" style="width:16%" />
      <freeze:cell property="create_date" caption="��������" style="width:18%" />
      <freeze:cell property="config_order" caption="����˳��" style="width:15%" />
      <freeze:cell property="is_pause" caption="�Ƿ���ͣ" style="width:15%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
