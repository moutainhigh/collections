<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="300">
<head>
<title>�޸���ͼ</title>
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
   saveAndExit( '', '������ͼ' );
}



// ����
function func_record_goBack()
{
    goBack();
}


</script>
<freeze:body>
<freeze:title caption="�޸���ͼ"/>
<freeze:errors/>

<freeze:form action="/txn80003202">
    <freeze:block property="record" caption="�޸���ͼ" width="95%">
        <freeze:hidden property="sys_rd_claimed_view_id" caption="����" style="width:95%"/>
        <freeze:hidden property="sys_rd_data_source_id" caption="����" style="width:95%"/>
        <freeze:cell property="view_name" caption="��ͼ����:"   style="width:95%"/>
        <freeze:select property="sys_rd_system_id" caption="��������:" valueset="ҵ������" style="width:95%"/>
        
        <freeze:textarea property="view_use" caption="��ͼ��;" colspan="2" rows="3" maxlength="1000" minlength="1" style="width:98%" />
        <freeze:hidden property="view_script" caption="��ͼ�ű�" colspan="2"  minlength="1" style="width:95%" />
        
  	
        <freeze:button name="record_saveRecord" caption="�� ��" icon="/script/button-icon/icon_ok.gif" onclick="func_record_saveRecord();"/>
        <freeze:button name="record_goBack" caption="�� ��"icon="/script/button-icon/icon_cancel.gif" onclick="func_record_goBack();"/>  		        
    </freeze:block>
</freeze:form>
</freeze:body>
</freeze:html>