<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<head>
<title>�鿴��������ͼ��Ϣ</title>
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
<freeze:errors/>
<br />
<freeze:form action="/txn80003205">
<freeze:block property="record" caption="�鿴��ͼ��Ϣ" width="95%" columns="1">
		<freeze:hidden property="object_schema" caption="�û�:" />
		<freeze:cell property="sys_name" caption="��������:"  />
        <freeze:cell property="view_name" caption="��ͼ����:" />
        <freeze:cell property="view_use" caption="��ͼ��;:" colspan="2" width="100%" />
        <freeze:cell property="view_script" caption="��ͼ�ű�:" colspan="2" width="100%"/> 
    </freeze:block>
    <p align="center" class="print-menu">
    <table cellspacing='0' cellpadding='0' class='button_table'><tr><td class='btn_left'></td><td><input type="button" name="record_goBackNoUpdate" value="�� ��" class="menu" onclick="func_record_goBack()" /></td><td class='btn_right'></td></tr></table>
    </p>
</freeze:form>
</freeze:body>
</freeze:html>