<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ����ʹ��ͳ���б�</title>
<style type="text/css">
#navbar{
	display:none;
}
#frame_record{
	border-collapse:collapse;
}
</style>
</head>

<script language="javascript">
// ��ֹ�����Զ���ã��Է�ֹ�û�ѡ���������ʱ���Զ����ء�
function _setFocus(){
	return;
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption=""/>
<freeze:errors/>

<freeze:form action="/txn60900001">
  <freeze:frame property="select-key" caption=""  width="100%" >
	<freeze:hidden property="func_name" caption="��ҵ(����)ID" style="width:95%"/>
	<freeze:hidden property="query_date_from" caption="��ҵ���" style="width:95%" />
	<freeze:hidden property="query_date_to" caption="��ҵ���ƣ�" style="width:95%"/>
  </freeze:frame>
  <freeze:grid property="record" caption="�����ط־�ʹ�����ͳ��" width="95%" navbar="bottom" checkbox="false" fixrow="false" >
  	<freeze:cell property="@rowid" caption="���" align="middle" style="width:6%"/>
    <freeze:cell property="sjjgname" caption="���ؾ�" style="width:15%"/>
  	<freeze:cell property="querytimes" caption="ʹ�ô���" value="" style="width:15%"/>
  </freeze:grid>
</freeze:form>
</freeze:body>
</freeze:html>
