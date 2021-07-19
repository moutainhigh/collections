<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ������Դ�����б�</title>
</head>

<script language="javascript">

// ����������Դ����
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_etl_dbsource.jsp", "����������Դ����", "modal" );
	page.addRecord();
}

// �޸�������Դ����
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn501020004.do", "�޸�������Դ����", "modal" );
	page.addParameter( "record:sys_etl_dbsource_id", "primary-key:sys_etl_dbsource_id" );
	page.updateRecord();
}

// ɾ��������Դ����
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn501020005.do", "ɾ��������Դ����" );
	page.addParameter( "record:sys_etl_dbsource_id", "primary-key:sys_etl_dbsource_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ������Դ�����б�"/>
<freeze:errors/>

<freeze:form action="/txn501020001">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:select property="dbsource_lb" caption="������Դ���" valueset="etl_sjlylb" show="name" style="width:55%"/>
      <freeze:text property="db_name" caption="���ݿ�����" datatype="string" maxlength="20" style="width:55%"/>
  </freeze:block>

  <freeze:grid property="record" caption="������Դ�б�" keylist="sys_etl_dbsource_id" width="95%" navbar="bottom" fixrow="false" checkbox="false">
      <%-- 
      <freeze:button name="record_addRecord" caption="����������Դ����" txncode="501020003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸�������Դ����" txncode="501020004" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ��������Դ����" txncode="501020005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      --%>
      <freeze:cell property="sys_etl_dbsource_id" caption="ID" style="width:10%" visible="false"/>
      <freeze:cell property="dbsource_name" caption="������Դ����" align="center" style="width:14%" />
      <freeze:cell property="dbsource_lb" caption="������Դ���"  valueset="etl_sjlylb" align="center" style="width:12%" />
      <freeze:cell property="db_name" caption="���ݿ�����" align="center" style="width:13%" />
      <freeze:cell property="db_lb" caption="���ݿ����" align="center" style="width:13%" />
      <freeze:cell property="db_constr" caption="���ݿ�IP" align="center" style="width:13%" />
      <freeze:cell property="db_ms" caption="���ݿ�����" align="center" style="width:22%" />
      <freeze:cell property="db_user" caption="���ݿ��û�" align="center" style="width:13%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
