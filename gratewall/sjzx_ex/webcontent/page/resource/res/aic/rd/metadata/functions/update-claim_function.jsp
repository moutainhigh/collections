<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="300">
<head>
<title>�޸ĺ���</title>
<style type="text/css">
.cls-no{
	display:none;
}
.cls-yes{
	display:block;
}
</style>
</head>

<script language="javascript">

// ����
function func_record_saveRecord()
{
   saveAndExit( '', '���溯��' );
}



// ����
function func_record_goBack()
{
    goBack();
}


</script>
<freeze:body>
<freeze:title caption="�޸ĺ���"/>
<freeze:errors/>

<freeze:form action="/txn80004202">
    <freeze:block property="record" caption="�޸ĺ���" width="95%">
        <freeze:hidden property="sys_rd_claimed_view_id" caption="����" style="width:95%"/>
        <freeze:hidden property="sys_rd_data_source_id" caption="����" style="width:95%"/>
        <freeze:cell property="view_name" caption="��������:"   style="width:95%"/>
        <freeze:select property="sys_rd_system_id" caption="��������:" valueset="ҵ������" style="width:95%"/>
        
        <freeze:textarea property="view_use" caption="������;" colspan="2" rows="3" maxlength="1000" minlength="1" style="width:98%" />
        <freeze:hidden property="view_script" caption="�����ű�" colspan="2"  minlength="1" style="width:95%" />
        
  	
        <freeze:button name="record_saveRecord" caption="�� ��" icon="/script/button-icon/icon_ok.gif" onclick="func_record_saveRecord();"/>
        <freeze:button name="record_goBack" caption="�� ��"icon="/script/button-icon/icon_cancel.gif" onclick="func_record_goBack();"/>  		        
    </freeze:block>
</freeze:form>
</freeze:body>
</freeze:html>