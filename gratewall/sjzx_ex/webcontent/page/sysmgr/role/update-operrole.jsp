<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="340">
<head>
<title>�޸�ҵ���ɫ��Ϣ</title>
</head>

<freeze:body>
<freeze:title caption="�޸�ҵ���ɫ��Ϣ"/>
<script language='javascript' src='<%=request.getContextPath()%>/script/lib/jquery.js'></script>
<script language='javascript' src='<%=request.getContextPath()%>/script/plugins/jquery.filterInput.js'></script>

<freeze:errors/>

<script language="javascript">
function func_record_saveAndExit(){
  saveAndExit( "", "�޸�ҵ���ɫ��Ϣ", "/txn980322.do" );
}

function func_record_goBack(){
  goBack( "/txn980321.do" );
}
</script>

<freeze:form action="/txn980322">
  <freeze:frame property="primary-key" width="95%">
    <freeze:hidden property="roleid" caption="��ɫ���" style="width:90%"/>
    <freeze:hidden property="rolename" caption="��ɫ����" style="width:90%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸�ҵ���ɫά��" width="95%" columns="1" captionWidth="0.25">
    <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE" onclick="func_record_saveAndExit();"/>
    <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
    <freeze:hidden property="roleid" caption="��ɫ���" style="width:90%"/>
    <freeze:text property="rolename" caption="��ɫ����" style="width:90%" datatype="string" maxlength="32" readonly="true"/>
    <freeze:text property="description" caption="��ɫ����" style="width:90%" datatype="string" maxlength="256"/>
    <freeze:text property="maxcount" caption="����ѯ��¼��" datatype="number" maxlength="10" minlength="1" value="0" style="width:90%"/>
   <freeze:textarea property="memo" caption="��ע" style="width:90%" colspan="2" rows="4"/>
    <freeze:hidden property="rolescope" caption="��ɫʹ�÷�Χ" valueset="��ɫʹ�÷�Χ" style="width:90%" visible="false"/>
    <freeze:hidden property="groupname" caption="��ɫ�����Ĺ�����" style="width:90%" datatype="string" maxlength="32" visible="false"/>
    <freeze:hidden property="homepage" caption="��ҳ��" style="width:90%" datatype="string" maxlength="128" visible="false"/>
    <freeze:hidden property="layout" caption="ҳ�沼��" valueset="ҳ��Ĳ����б�" hint="WEBҳ��Ĳ�������" style="width:90%" visible="false"/>
    <freeze:hidden property="roletype" caption="��ɫ���ͣ�����" style="width:90%"/>
    <freeze:hidden property="roletype2" caption="��ɫ���ͣ�����" style="width:90%"/>
    <freeze:hidden property="status" caption="��Ч��־" style="width:90%"/>
    
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
