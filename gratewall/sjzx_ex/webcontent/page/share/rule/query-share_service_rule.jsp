<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ���ʹ����б�</title>
</head>

<script language="javascript">

// ���ӷ��ʹ���
function func_record_addRecord()
{
	var page = new pageDefine( "insert-share_service_rule.jsp", "���ӷ��ʹ���", "modal" );
	page.addRecord();
}

// �޸ķ��ʹ���
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn403004.do", "�޸ķ��ʹ���", "modal" );
	page.addParameter( "record:rule_id", "primary-key:rule_id" );
	page.updateRecord();
}

// ɾ�����ʹ���
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn403005.do", "ɾ�����ʹ���" );
	page.addParameter( "record:rule_id", "primary-key:rule_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ���ʹ����б�"/>
<freeze:errors/>

<freeze:form action="/txn403001">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:text property="service_id" caption="����ID" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

  <freeze:grid property="record" caption="��ѯ���ʹ����б�" keylist="rule_id" width="95%" navbar="bottom" fixrow="true">
      <freeze:button name="record_addRecord" caption="���ӷ��ʹ���" txncode="403003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸ķ��ʹ���" txncode="403004" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ�����ʹ���" txncode="403005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="rule_id" caption="������ʹ���ID" style="width:10%" visible="false"/>
      <freeze:cell property="service_id" caption="����ID" style="width:16%" />
      <freeze:cell property="week" caption="�����ڼ�" style="width:14%" />
      <freeze:cell property="start_time" caption="�����쿪ʼʱ��" style="width:14%" />
      <freeze:cell property="end_time" caption="���������ʱ��" style="width:14%" />
      <freeze:cell property="times_day" caption="������ɷ��ʴ���" style="width:14%" />
      <freeze:cell property="count_dat" caption="������һ�οɷ��ʼ�¼��Ŀ" style="width:14%" />
      <freeze:cell property="total_count_day" caption="������ɷ����ܼ�¼��Ŀ" style="width:14%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
