<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="350">
<head>
<title>�鿴������洢������Ϣ</title>
</head>

<script language="javascript">
// ����
function func_record_goBack()
{
    goBack();
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
    
}

_browse.execute( __userInitPage );

</script>
<freeze:body>
<freeze:title caption="�鿴�洢������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn80005205">
<freeze:block property="record" caption="�鿴�洢������Ϣ" width="95%" columns="1">
		<freeze:hidden property="object_schema" caption="�û�:" />
		<freeze:cell property="sys_name" caption="��������:"  />
        <freeze:cell property="view_name" caption="�洢��������:" />
        <freeze:cell property="view_use" caption="�洢������;:" colspan="2" width="20%" />
        <freeze:hidden property="view_script" caption="�洢���̽ű�:" colspan="2" width="30%" /> 
    </freeze:block>
    <p align="center" class="print-menu">
    	<table cellspacing='0' cellpadding='0' class='button_table'><tr><td class='btn_left'></td><td><input type="button" name="record_goBackNoUpdate" value="�� ��" class="menu" onclick="func_record_goBack()" /></td><td class='btn_right'></td></tr></table>
    </p>
</freeze:form>
</freeze:body>
</freeze:html>