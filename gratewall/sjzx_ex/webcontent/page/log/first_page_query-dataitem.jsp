<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="850" height="550">
<head>
	<title>ϵͳ��־</title>
</head>

<script language="javascript">





// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	

}
function func_record_goBack()
{
	window.close();	// /txn201001.do
}




_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
	<freeze:title caption="��ѯϵͳ��־�б�" />
	<freeze:errors />

	<freeze:form action="/txn601016">
	<freeze:frame property="select-key" width="95%">
      <freeze:hidden property="collect_joumal_id" caption="����" style="width:95%"/>
  </freeze:frame>
		<freeze:block theme="view" property="record" caption="�鿴ϵͳ��־"
			width="95%">
			      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
			
			 <freeze:hidden property="first_page_query_id" caption="����" style="width:10%" visible="false"/>
      <freeze:hidden property="orgid" caption="ID" style="width:95%" />
      <freeze:cell property="opername" caption="��������" style="width:95%" />
      <freeze:cell property="username" caption="������" style="width:95%" />
      <freeze:cell property="orgname" caption="��������" style="width:95%" />
      <freeze:cell property="query_time" caption="����ʱ��"   style="width:95%" />
      <freeze:cell property="state" caption="�û�״̬" style="width:95%" />
      <freeze:cell property="count" caption="����" style="width:95%" />
      <freeze:cell property="num" caption="����" style="width:95% " />
      <freeze:cell property="orgname" caption="��������" style="width:95%" />
      <freeze:cell property="ipaddress" caption="ip��ַ" style="width:95%" />
     
		</freeze:block>



	</freeze:form>
</freeze:body>
</freeze:html>
