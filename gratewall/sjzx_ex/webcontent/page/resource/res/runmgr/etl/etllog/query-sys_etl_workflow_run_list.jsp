<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯת�������б�</title>
</head>

<script language="javascript">
// ��ѯSession��Ϣ
function func_viewDetail(){
	var page = new pageDefine( "/txn501040003.do", "�鿴��־��ϸ��Ϣ", "modal" );
	page.addParameter( "record:workflow_run_id", "select-key:workflow_run_id" );
	page.addParameter( "record:mapping_name", "select-key:mapping_name" );
	page.addParameter( "select-key:rep_foldername", "select-key:rep_foldername" );
	page.addParameter( "select-key:wf_name", "select-key:wf_name" );
	page.addParameter( "select-key:dbuser", "select-key:dbuser" );	
	page.goPage();
}

// ����
function func_record_goBack()
{
	goBack();
}
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯת�������б�"/>
<freeze:errors/>

<freeze:form action="/txn501040002">
  <freeze:frame property="select-key" caption="" width="95%">
      <freeze:hidden property="workflow_id" caption="workflow_id" style="width:55%"/> 
      <freeze:hidden property="dbuser" caption="���ݿ��û�" style="width:55%"/> 
  </freeze:frame>

  <freeze:grid property="record" caption="ת�������б�" keylist="workflow_id" width="95%" navbar="bottom" fixrow="false" multiselect="false">
      <freeze:button name="record_dispatch" caption="��ϸ" txncode="501040003" enablerule="1" hotkey="UPDATE" align="right" onclick="func_viewDetail();"/>
      <freeze:hidden property="workflow_id" caption="ID" style="width:10%"/>    
      <freeze:cell property="mapping_name" caption="ת������" align="center" style="width:20%" />
      <freeze:hidden property="src_success_rows" caption="Դ�ɹ���¼��" style="width:15%" />
      <freeze:hidden property="src_failed_rows" caption="Դʧ�ܼ�¼��" style="width:15%" />
      <freeze:hidden property="targ_success_rows" caption="Ŀ��ɹ���¼��" style="width:15%" />
      <freeze:hidden property="targ_failed_rows" caption="Ŀ��ʧ�ܼ�¼��" style="width:20%" />
      <freeze:hidden property="total_trans_errs" caption="ת������" style="width:20%" />
      <freeze:hidden property="first_error_msg" caption="������Ϣ" style="width:50%" />
      <freeze:cell property="start_time" caption="��ʼʱ��" align="center" style="width:30%" />
      <freeze:cell property="end_time" caption="����ʱ��" align="center" style="width:30%" />
      <freeze:cell property="status" caption="״̬" align="center" />
      <freeze:hidden property="workflow_run_id" caption="workflow_run_id" style="width:20%" />
  </freeze:grid>
  <div style="text-align:center">
  	<input type="button" value="����" onclick="func_record_goBack();" class="menu" style="width:60px;" />
  </div>
</freeze:form>
</freeze:body>
</freeze:html>
