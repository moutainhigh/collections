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

function func_record_saveRecord(){
  var jsmc = document.getElementById("record:rolename");
  if(isNotChar(jsmc.value,"��ɫ����")){
	  saveRecord( "", "����ҵ���ɫ��Ϣ" );
  }else{
  	return false;
  }
}

function func_record_saveAndContinue(){
  var jsmc = document.getElementById("record:rolename");
  if(isNotChar(jsmc.value,"��ɫ����")){
	  saveAndContinue( "", "����ҵ���ɫ��Ϣ������" );
  }else{
  	return false;
  }
}

function func_record_saveAndExit(){
  var jsmc = document.getElementById("record:rolename");
  if(isNotChar(jsmc.value,"��ɫ����")){
	  saveAndExit( "", "����ҵ���ɫ��Ϣ������" );
  }else{
  	return false;
  }
}

function func_record_goBack(){
  goBack( "/txn980321.do" );
}
</script>

<freeze:form action="/txn980323">
  <freeze:block property="record" caption="����ҵ���ɫά��" width="95%" columns="1">
    <freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE" onclick="func_record_saveAndExit();"/>
    <%--   
    <freeze:button name="record_saveAndContinue" caption="���沢����" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
    <freeze:button name="record_saveAndExit" caption="���沢�ر�" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
    --%>
    <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
    <freeze:text property="rolename" caption="��ɫ����" style="width:90%" datatype="string" maxlength="32" minlength="1"/>
    <freeze:text property="description" caption="��ɫ����" style="width:90%" datatype="string" maxlength="256"/>
    <freeze:text property="maxcount" caption="����ѯ��¼��" datatype="number" maxlength="10" minlength="1" value="0" style="width:90%"/>
       <freeze:textarea property="memo" caption="��ע" style="width:90%" colspan="2" rows="4"/>
    
    <freeze:hidden property="rolescope" caption="ʹ�÷�Χ" valueset="��ɫʹ�÷�Χ" style="width:90%" visible="false"/>
    <freeze:hidden property="groupname" caption="��ɫ�����Ĺ�����" style="width:90%" datatype="string" maxlength="32" visible="false"/>
    <freeze:hidden property="homepage" caption="��ҳ��" style="width:90%" datatype="string" maxlength="128" visible="false"/>
    <freeze:hidden property="layout" caption="ҳ�沼��" valueset="ҳ��Ĳ����б�" hint="WEBҳ��Ĳ�������" style="width:90%" visible="false"/>
    <freeze:hidden property="roletype" caption="��ɫ���ͣ�����" style="width:90%"/>
    <freeze:hidden property="roletype2" caption="��ɫ���ͣ�����" style="width:90%"/>
   
  </freeze:block>

</freeze:form>
</freeze:body>
<script language="javascript" type="text/javascript">
	//���������ַ�
	$(document).ready(
		function(){
			$(document.getElementById("record:rolename")).filterInput();
		}
	);
</script>
</freeze:html>
