<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="300">
<head>
<title>���캯��</title>
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
function func_record_saveRecord11()
{   saveAndExit( "", "", "/txn80004101.do" );
  ///saveRecord( '', '����ɹ�' );
  //window.close();
}

// ����
function func_record_goBack()
{
    goBack();
}
/**
 * 
 */
function funTBChanged(){

}
// �����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{	
}
function setDisplay(id,b){
}

_browse.execute( __userInitPage );

</script>
<freeze:body>
<freeze:title caption="���캯��"/>
<freeze:errors/>

<freeze:form action="/txn80004103">
    <freeze:block property="record" caption="���캯��" width="95%">
        <freeze:hidden property="sys_rd_claimed_view_id" caption="�����캯������" style="width:75%"/>
        <freeze:hidden property="sys_rd_unclaim_table_id" caption="������" style="width:75%"/>
        <freeze:hidden property="sys_rd_data_source_id" caption="����Դid" style="width:75%"/>
        <freeze:hidden property="object_schema" caption="ģʽ" style="width:75%"/>
        <freeze:hidden property="unclaim_table_code" caption="��������"  style="width:75%"/>
        <freeze:hidden property="data_object_type" caption="��������" value="F" style="width:75%"/>
        <freeze:cell property="unclaim_table_code" caption="��������"  style="width:75%"/>
        <freeze:select property="sys_rd_system_id" caption="��������" valueset="ҵ������"  style="width:95%" notnull="true"/>
        <freeze:textarea property="remark" caption="������;" colspan="3" rows="3" maxlength="1000" minlength="1" style="width:98%"/>
        <freeze:hidden property="object_script" caption="�����ű�" colspan="3"  minlength="1" style="width:98%"/>
        <freeze:button name="record_saveRecord11" caption="�� ��" icon="/script/button-icon/icon_ok.gif" onclick="func_record_saveRecord11();"/>
        <freeze:button name="record_goBack" caption="�� ��"icon="/script/button-icon/icon_cancel.gif" onclick="func_record_goBack();"/>  		        
    </freeze:block>
</freeze:form>
</freeze:body>
</freeze:html>