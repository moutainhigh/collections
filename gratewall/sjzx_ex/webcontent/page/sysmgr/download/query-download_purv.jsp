<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ���������б�</title>
</head>

<script language="javascript">

// ������������
function func_record_addRecord()
{
	var page = new pageDefine( "insert-download_purv.jsp", "������������", "modal" );
	page.addRecord();
}

// �޸���������
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn105004.do", "�޸���������", "modal" );
	page.addParameter( "record:download_purv_id", "primary-key:download_purv_id" );
	page.updateRecord();
}

// ɾ����������
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn105005.do", "ɾ����������" );
	page.addParameter( "record:download_purv_id", "primary-key:download_purv_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ���������б�"/>
<freeze:errors/>

<freeze:form action="/txn105001">
  <freeze:block property="select-key" width="95%" caption="��ѯ����" theme="query">
  	<freeze:text property="jgmc" caption="��������" style="width:95%" />
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="��ѯ���������б�" keylist="download_purv_id" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="������������" txncode="105003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸���������" txncode="105004" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ����������" txncode="105005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="download_purv_id" caption="����" style="width:10%" visible="false"/>
      <freeze:cell property="agency_id" caption="�������" style="width:22%" />
      <freeze:cell property="has_purv" caption="�Ƿ���������" style="width:19%" />
      <freeze:cell property="max_result" caption="������������" style="width:19%" />
      <freeze:cell property="last_modi_user" caption="����޸���" style="width:21%" />
      <freeze:cell property="last_modi_date" caption="����޸�����" style="width:19%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
