<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ���������б�</title>
</head>

<script language="javascript">

// ���ӹ�������
function func_record_addRecord()
{
	var page = new pageDefine( "insert-res_business_topics.jsp", "���ӹ�������", "modal" );
	page.addRecord();
}

// �޸Ĺ�������
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn203014.do", "�޸Ĺ�������", "modal" );
	page.addParameter( "record:business_topics_id", "primary-key:business_topics_id" );
	page.updateRecord();
}

// ɾ����������
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn203015.do", "ɾ����������" );
	page.addParameter( "record:business_topics_id", "primary-key:business_topics_id" );
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

<freeze:form action="/txn203011">
  <freeze:frame property="select-key" width="95%">
  </freeze:frame>

  <freeze:grid property="record" caption="��ѯ���������б�" keylist="business_topics_id" width="95%" navbar="bottom" fixrow="true">
      <freeze:button name="record_addRecord" caption="���ӹ�������" txncode="203013" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸Ĺ�������" txncode="203014" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ����������" txncode="203015" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="business_topics_id" caption="ҵ������ID" style="width:18%" />
      <freeze:cell property="service_targets_id" caption="�������ID" style="width:18%" />
      <freeze:cell property="topics_name" caption="��������" style="width:32%" />
      <freeze:cell property="topics_no" caption="������" style="width:16%" />
      <freeze:cell property="topics_desc" caption="��������" style="width:20%" visible="false" />
      <freeze:cell property="show_order" caption="��ʾ˳��" style="width:16%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
