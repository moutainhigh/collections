<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="300">
<head>
<title>������ͼ</title>
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
{
   saveAndExit( "", "", "/txn80003101.do" );
   //saveAndExit( "����ɹ�", "����ʧ��", '' );
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
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{	
}
function setDisplay(id,b){
}

_browse.execute( __userInitPage );

</script>
<freeze:body>
<freeze:title caption="������ͼ"/>
<freeze:errors/>

<freeze:form action="/txn80003103">
    <freeze:block property="record" caption="������ͼ" width="95%">
        <freeze:hidden property="sys_rd_claimed_view_id" caption="��������ͼ����" style="width:75%"/>
        <freeze:hidden property="sys_rd_unclaim_table_id" caption="�����" style="width:75%"/>
        <freeze:hidden property="sys_rd_data_source_id" caption="����Դid" style="width:75%"/>
        <freeze:hidden property="object_schema" caption="ģʽ" style="width:75%"/>
        <freeze:hidden property="unclaim_table_code" caption="��ͼ����"  style="width:75%"/>
        <freeze:hidden property="data_object_type" caption="��������" value="V" style="width:75%"/>
        <freeze:cell property="unclaim_table_code" caption="��ͼ����"  style="width:75%"/>
        <freeze:select property="sys_rd_system_id" caption="��������" valueset="ҵ������"  style="width:95%"/>
        <freeze:textarea property="remark" caption="��ͼ��;" colspan="3" rows="3" maxlength="1000" minlength="1" style="width:98%"/>
        <freeze:textarea property="object_script" caption="��ͼ�ű�" colspan="3" rows="10" minlength="1" style="width:98%"/>
        <freeze:button name="record_saveRecord11" caption="�� ��" icon="/script/button-icon/icon_ok.gif" onclick="func_record_saveRecord11();"/>
        <freeze:button name="record_goBack" caption="�� ��"icon="/script/button-icon/icon_cancel.gif" onclick="func_record_goBack();"/>  		        
    </freeze:block>
</freeze:form>
</freeze:body>
</freeze:html>