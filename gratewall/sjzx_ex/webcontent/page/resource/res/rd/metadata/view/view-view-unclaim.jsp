<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="350">
<head>
<title>�鿴δ������ͼ��Ϣ</title>
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
<freeze:title caption="��ͼ��Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn80003104">
<freeze:block property="record" caption="��ͼ��Ϣ" width="95%" columns="1">
		
        <freeze:cell property="unclaim_table_code" caption="��ͼ����:" />
        <freeze:cell property="remark" caption="��ͼ��;:"  />
        <freeze:cell property="object_script" caption="��ͼ�ű�:" />
    </freeze:block>
    <p align="center" class="print-menu">
    <table cellspacing='0' cellpadding='0' class='button_table'><tr><td class='btn_left'></td><td><input type="button" name="record_goBackNoUpdate" value="�� ��" class="menu" onclick="func_record_goBack()" /></td><td class='btn_right'></td></tr></table>
    </p>
</freeze:form>
</freeze:body>
</freeze:html>