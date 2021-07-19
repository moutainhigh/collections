<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ���������б�</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">

// ���Ӽ�������
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_search_svr.jsp", "���Ӽ�������" );
	page.addRecord();
}

// �޸ļ�������
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn50010204.do", "�޸ļ�������", "modal" );
	page.addParameter( "record:sys_search_svr_id", "primary-key:sys_search_svr_id" );
	page.updateRecord();
}

// ɾ����������
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn50010205.do", "ɾ����������" );
	page.addParameter( "record:sys_search_svr_id", "primary-key:sys_search_svr_id" );
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

<freeze:form action="/txn50010201">
  <freeze:frame property="select-key" width="95%">
  </freeze:frame>

  <freeze:grid property="record" caption="��ѯ���������б�" keylist="sys_search_svr_id" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="���Ӽ�������" txncode="50010203" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸ļ�������" txncode="50010204" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ����������" txncode="50010205" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="@rowid" caption="���" align="middle" style="width:5%"/>
      <freeze:hidden property="sys_search_svr_id" caption="��������ID" style="width:35%" />
      <freeze:cell property="svr_name" caption="��������" style="width:25%" />
      <freeze:cell property="columns" caption="�����ֶ�" style="width:70%" visible="true" />
      <freeze:cell property="svr_query" caption="������ѯ��" style="width:20%" visible="false" />
      <freeze:cell property="svr_template" caption="��������ģ��" style="width:20%" visible="false" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
