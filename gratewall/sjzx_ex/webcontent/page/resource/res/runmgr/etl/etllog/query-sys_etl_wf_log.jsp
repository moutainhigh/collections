<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ��ȡ��������б�</title>
</head>

<script language="javascript">
// ��ѯSession��Ϣ
function func_viewDetail(){
	var page = new pageDefine( "/txn501040002.do", "�鿴ת�������б�", "window" );
	page.addParameter( "record:workflow_id","select-key:workflow_id");
	page.addParameter( "record:rep_foldername", "select-key:rep_foldername" );
	page.addParameter( "record:wf_name", "select-key:wf_name" );
	page.addParameter( "record:dbuser", "select-key:dbuser" );
	page.goPage();
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ��ȡ��������б�"/>
<freeze:errors/>

<freeze:form action="/txn501040001">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:select property="rep_id" caption="��Ŀ����" valueset="ETL��Ŀ" show="name" style="width:55%"/>
  </freeze:block>

  <freeze:grid property="record" caption="��ȡ�����б�" keylist="workflow_id" width="95%" navbar="bottom" fixrow="false" multiselect="false">
      <freeze:button name="record_dispatch" caption="��ϸ" txncode="501040002" enablerule="1" align="right" onclick="func_viewDetail();"/>
      <freeze:hidden property="sys_etl_wf_id" caption="ID" style="width:10%" />
      <freeze:hidden property="rep_folderid" caption="�ļ���ID" style="width:15%" />
      <freeze:cell property="rep_foldername" caption="�ļ�������" align="center" style="width:15%" />
      <freeze:hidden property="workflow_id" caption="workflow_ID" style="width:15%" />
      <freeze:cell property="wf_name" caption="��ȡ��������" align="center" style="width:15%" />
      <freeze:cell property="wf_ms" caption="��ȡ��������" align="center" style="width:20%" />
      <freeze:cell property="wf_db_source" caption="Դ���ݿ�" align="center" style="width:20%" />
      <freeze:cell property="wf_db_target" caption="Ŀ�����ݿ�" align="center" style="width:20%" />
      <freeze:hidden property="wf_state" caption="״̬" style="width:10%" />
      <freeze:hidden property="dbuser" caption="���ݿ��û�" style="width:10%" />
      <freeze:hidden property="domain_name" caption="����" style="width:10%" />
      <freeze:hidden property="server_name" caption="������" style="width:10%" />
  </freeze:grid>
</freeze:form>
</freeze:body>
</freeze:html>
