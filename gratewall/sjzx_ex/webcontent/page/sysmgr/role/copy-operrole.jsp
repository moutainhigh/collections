<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="340">
<head>
<title>����ҵ���ɫ��Ϣ</title>
</head>

<freeze:body>
<freeze:title caption="����ҵ���ɫ��Ϣ"/>
<script language='javascript' src='<%=request.getContextPath()%>/script/lib/jquery.js'></script>
<script language='javascript' src='<%=request.getContextPath()%>/script/plugins/jquery.filterInput.js'></script>

<freeze:errors/>

<script language="javascript">
function func_record_saveAndExit(){

 saveAndExit( "", "����ҵ���ɫ��Ϣ", "/txn980328.do" );

}

function func_record_goBack(){
  goBack( "/txn980321.do" );
}
</script>

<freeze:form action="/txn980328">
  <freeze:frame property="primary-key" width="95%">
    <freeze:hidden property="roleid" caption="��ɫ���" style="width:90%"/>
    <freeze:hidden property="rolename" caption="��ɫ����" style="width:90%"/>
  </freeze:frame>

  <freeze:block property="record" caption="����ҵ���ɫ" width="95%" columns="1" captionWidth="0.25">
    <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE" onclick="func_record_saveAndExit();"/>
    <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
    <freeze:hidden property="roleid" caption="Դ��ɫ���" style="width:90%" readonly="true"/>
    <freeze:text property="rolename" caption="Դ��ɫ����" style="width:90%" datatype="string" maxlength="32" readonly="true"/>
    <freeze:text property="description" caption="Դ��ɫ����" style="width:90%" datatype="string" maxlength="256" readonly="true"/>

	<freeze:hidden property="roleid_target" caption="Ŀ���ɫ���" style="width:90%"/>
    <freeze:text property="rolename_target" caption="Ŀ���ɫ����" style="width:90%"  notnull="true" datatype="string" maxlength="32"/>
    <freeze:text property="description_target" caption="Ŀ���ɫ����" style="width:90%" datatype="string" maxlength="256"/>
  </freeze:block>

</freeze:form>
</freeze:body>
<script language="javascript" type="text/javascript">
	//���������ַ�
	$(document).ready(
		function(){
			$(document.getElementById("record:rolename_target")).filterInput();
		}
	);
</script>
</freeze:html>
