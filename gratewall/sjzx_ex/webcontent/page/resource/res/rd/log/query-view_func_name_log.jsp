<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ���ܴ�С���Ӧ�б�</title>
</head>

<script language="javascript">

// ���ӹ��ܴ�С���Ӧ
function func_record_addRecord()
{
	var page = new pageDefine( "insert-view_func_name_log.jsp", "���ӹ��ܴ�С���Ӧ", "modal" );
	page.addRecord();
}

// �޸Ĺ��ܴ�С���Ӧ
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn620100404.do", "�޸Ĺ��ܴ�С���Ӧ", "modal" );
	page.updateRecord();
}

// ɾ�����ܴ�С���Ӧ
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn620100405.do", "ɾ�����ܴ�С���Ӧ" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ���ܴ�С���Ӧ�б�"/>
<freeze:errors/>

<freeze:form action="/txn620100401">
  <freeze:frame property="select-key" width="95%">
  </freeze:frame>

  <freeze:grid property="record" caption="��ѯ���ܴ�С���Ӧ�б�" keylist="second_func_name_fk" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="���ӹ��ܴ�С���Ӧ" txncode="620100403" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸Ĺ��ܴ�С���Ӧ" txncode="620100404" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ�����ܴ�С���Ӧ" txncode="620100405" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="first_func_name" caption="���ܴ���" style="width:34%" />
      <freeze:cell property="second_func_name" caption="����С��" style="width:66%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
