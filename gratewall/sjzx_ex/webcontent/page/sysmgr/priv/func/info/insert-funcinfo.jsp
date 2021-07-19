<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<head>
<title>���ӹ�����Ϣ</title>
</head>

<freeze:body>
<freeze:title caption="���ӹ�����Ϣ"/>
<script language='javascript' src='<%=request.getContextPath()%>/script/lib/jquery.js'></script>
<script language='javascript' src='<%=request.getContextPath()%>/script/plugins/jquery.filterInput.js'></script>
<freeze:errors/>

<script language="javascript">

function func_record_saveRecord(){
  var gndm = document.getElementById("record:funccode");
  if(isNotChar(gndm.value,"���ܴ���")){
	  saveRecord( "", "���湦����Ϣ��Ϣ" );
	 }
  else {
  	return false;
  }
}

function func_record_saveAndContinue(){
  var gndm = document.getElementById("record:funccode");
  if(isNotChar(gndm.value,"���ܴ���")){
  	saveAndContinue( "", "���湦����Ϣ��Ϣ������" );
	 }
  else {
  	return false;
  }  	
}

function func_record_saveAndExit(){
  var gndm = document.getElementById("record:funccode");
  if(isNotChar(gndm.value,"���ܴ���")){
  	saveAndExit( "", "���湦����Ϣ��Ϣ������", "txn980301.do" );
	 }
  else {
  	return false;
  }  	
}

function func_record_goBack(){
  goBack( "txn980301.do" );
}
</script>

<freeze:form action="/txn980303">
  <freeze:block property="record" caption="���ӹ���Ȩ��ά��" width="95%">
    <freeze:button name="record_saveAndContinue" caption="���沢����" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();" visible="false"/>
    <freeze:button name="record_saveAndExit" caption="���沢�ر�" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
    <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
    <freeze:text property="parentcode" caption="�ϼ�����" style="width:100%" datatype="string" maxlength="36" readonly="true"/>
    <freeze:text property="funccode" caption="���ܴ���" newline="true" style="width:100%" datatype="string" maxlength="36" minlength="1"/>
    <freeze:text property="funcname" caption="��������" newline="true" style="width:100%" datatype="string" maxlength="256" minlength="1"/>
    <freeze:hidden property="groupname" caption="����������" style="width:100%"/>
    <freeze:hidden property="status" caption="״̬1" style="width:100%"/>
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
<%-- <freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE" onclick="func_record_saveRecord();" visible="false"/> --%>
</freeze:html>
