<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�޸Ĵ��������Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
   var jcsjfx_dm=getFormFieldValue("record:jcsjfx_dm");
   var jcsjfx_mc=getFormFieldValue("record:jcsjfx_mc");  
   if(jcsjfx_dm==""||jcsjfx_mc==""){
       window.alert("����ȷ��д������...");
       return;
   }
   if(/[\'@$!`~/?#,;^&\*\.\\]+/.test(jcsjfx_mc))  {
    ��alert("�������ݷ��������в�Ӧ���������ַ�"); 
    ��return;
  ��}
	if(/[\'@$!`~/?#,;^&\*\.\\]+/.test(jcsjfx_dm))  {
    ��alert("�������ݷ�������в�Ӧ���������ַ�"); 
    ��return;
  ��}  
    saveAndExit( '', '���������λ��' );   
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn3010101.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸Ĵ��������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn3010102">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="jcsjfx_id" caption="���ݷ���ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸Ĵ��������Ϣ" captionWidth="0.5" width="95%" nowrap="true">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:hidden property="jcsjfx_id" caption="���ݷ���ID" datatype="string" maxlength="36" style="width:95%"/>
      <freeze:hidden property="jc_dm_id" caption="����ID" datatype="string" maxlength="36" minlength="1" style="width:95%"/>
      <freeze:text property="jcsjfx_dm" caption="���ݷ������" datatype="string" maxlength="36" minlength="1" colspan="2" style="width:95%"/>
      <freeze:text property="jcsjfx_mc" caption="���ݷ�������" datatype="string" maxlength="150" minlength="1" colspan="2" style="width:95%"/>
      <freeze:hidden property="jcsjfx_cjm" caption="���ݷ���㼶��" datatype="string" maxlength="36" style="width:95%"/>
      <freeze:hidden property="jcsjfx_fjd" caption="���ݷ���ڵ����" datatype="string" maxlength="36" style="width:95%"/>
      <freeze:hidden property="szcc" caption="���ڲ��" datatype="string" maxlength="2" style="width:95%"/>
      <freeze:hidden property="xssx" caption="��ʾ˳��" datatype="string" maxlength="" style="width:95%"/>
      <freeze:hidden property="sfmx" caption="�Ƿ���ϸ" datatype="string" maxlength="1" style="width:95%"/>
      <!--<freeze:select property="sy_zt" caption="ʹ��״̬" valueset="�������ݷ���ʹ��״̬" notnull="true" style="width:95%"/>-->
      <freeze:textarea property="fx_ms" caption="��������" colspan="2" rows="2" maxlength="32700" style="width:98%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
