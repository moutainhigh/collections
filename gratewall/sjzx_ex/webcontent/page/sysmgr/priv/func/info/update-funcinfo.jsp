<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<head>
<title>�޸Ĺ�����Ϣ</title>
</head>

<freeze:body>
<freeze:title caption="�޸Ĺ�����Ϣ"/>
<script language='javascript' src='<%=request.getContextPath()%>/script/lib/jquery.js'></script>
<script language='javascript' src='<%=request.getContextPath()%>/script/plugins/jquery.filterInput.js'></script>

<freeze:errors/>

<script language="javascript">
function func_record_saveAndExit(){
//�����"/txn980301.do" �����404����
  saveAndExit( "", "�޸Ĺ�����Ϣ��Ϣ","txn980301.do");
}

function func_record_goBack(){
  goBack( "txn980301.do" );
}
</script>

<freeze:form action="/txn980302">
  <freeze:frame property="primary-key" width="95%">
    <freeze:hidden property="funccode" caption="���ܴ���" style="width:90%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸Ĺ���Ȩ��ά��" width="95%">
    <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE" onclick="func_record_saveAndExit();"/>
    <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
    <freeze:text property="parentcode" caption="�ϼ�����" style="width:100%" datatype="string" maxlength="36" readonly="true"/>
    <freeze:text property="funccode" caption="���ܴ���" newline="true" style="width:100%" datatype="string" maxlength="36" readonly="true"/>
    <freeze:text property="funcname" caption="��������" newline="true" style="width:100%" datatype="string" maxlength="256" minlength="1"/>
    <freeze:hidden property="groupname" caption="����������" style="width:100%"/>
    <freeze:hidden property="status" caption="״̬" style="width:100%"/>
    <freeze:textarea property="memo" caption="��ע��Ϣ" style="width:95%" maxlength="256" colspan="2" rows="4"/>
  </freeze:block>

</freeze:form>
</freeze:body>
<script language="javascript" type="text/javascript">
	//���������ַ�
	$(document).ready(
		function(){
			$(document.getElementById("record:funccode")).filterInput();
			$(document.getElementById("record:funcname")).filterInput();
		}
	);
</script>
</freeze:html>
