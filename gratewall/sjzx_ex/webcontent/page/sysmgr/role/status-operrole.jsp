<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<head>
<title>���ý�ɫ��״̬</title>
</head>

<freeze:body>
<freeze:title caption="���ý�ɫ��״̬"/>
<freeze:errors/>

<script language="javascript">
function func_record_saveAndExit(){
  saveAndExit( "", "�޸�ҵ���ɫ��Ϣ", "/txn980322.do" );
}

function func_record_goBack(){
  goBack( "/txn980321.do" );
}
</script>

<freeze:form action="/txn980326">
  <freeze:frame property="primary-key" width="95%">
    <freeze:hidden property="roleid" caption="��ɫ���" style="width:90%"/>
    <freeze:hidden property="rolename" caption="��ɫ����" style="width:90%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸�ҵ���ɫά��" width="95%" columns="1" captionWidth="0.2">
    <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE" onclick="func_record_saveAndExit();"/>
    <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
    <freeze:hidden property="roleid" caption="��ɫ���" style="width:90%"/>
    <freeze:text property="rolename" caption="��ɫ����" style="width:90%" datatype="string" maxlength="32" readonly="true"/>
    <freeze:text property="description" caption="��ɫ����" style="width:90%" datatype="string" maxlength="256" readonly="false"/>
    <freeze:select property="rolescope" caption="��ɫʹ�÷�Χ" valueset="��ɫʹ�÷�Χ"  visible="false"/>
    <freeze:radio property="status" caption="��Ч��־" valueset="��Ч��־" style="width:30%"/>
    <freeze:text property="maxcount" caption="����ѯ��¼��" datatype="number" maxlength="5" minlength="1" value="0" style="width:90%"/>
    <freeze:textarea property="memo" caption="��ע" style="width:90%" maxlength="256" cols="60" rows="5"/>
  </freeze:block>

</freeze:form>
<script language="javascript">
var flag = getFormFieldValue('record:status');
if( flag == '0' ){
	flag = '1';
}
else{
	flag = '0';
}

setFormFieldValue('record:status', 0, flag);
</script>
</freeze:body>
</freeze:html>
