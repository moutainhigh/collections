<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.context.vo.VoUser"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="cn.gwssi.common.context.TxnContext"%>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<%
  HttpSession usersession = request.getSession(false);
  VoUser voUser = (VoUser) usersession.getAttribute(TxnContext.OPER_DATA_NODE); 
  String user=voUser.getOperName();
%>
<head>
<title>�޸ĵ���������Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	var startDate=getFormFieldValue("record:publish_date");
	var endDate=getFormFieldValue("record:expire_date");
	startDate=startDate.replaceAll("-","");
	endDate=endDate.replaceAll("-","");
	if(parseInt(endDate)<=parseInt(startDate)){
		alert('�������ڲ������ڻ���ڷ�������');
		return;
	}
	saveAndExit( '', '���浯������' );	// /txn60800001.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn60800001.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
	setFormFieldValue("record:AUTHOR", "<%=user%>");
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸ĵ���������Ϣ"/>
<freeze:errors/>
<!-- <freeze:browsebox property="roles" caption="�ɲ鿴������ɫ" multiple="true" valueset="��֯������ɫ" show="name" notnull="true" style="width:75%"/>
       -->
<freeze:form action="/txn60800002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_popwindow_id" caption="ϵͳ����ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸ĵ���������Ϣ" columns="1" width="95%" captionWidth="0.25">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="sys_popwindow_id" caption="ϵͳ����ID" datatype="string" maxlength="32" minlength="1" style="width:75%"/>
      <freeze:hidden property="AUTHOR" caption="������" datatype="string" maxlength="10" style="width:75%"/>
      <freeze:textarea property="content" caption="����" rows="4" minlength="1" maxlength="4000" style="width:75%"/>
      <freeze:text property="PUBLISH_DEPART" caption="��������"   maxlength="25" style="width:75%"/>
      <freeze:hidden property="roles" caption="�ɲ鿴������ɫ"  style="width:75%"/>
      
      <freeze:datebox property="publish_date" caption="��������" datatype="string" maxlength="10" style="width:75%" notnull="true"/>
      <freeze:datebox property="expire_date" caption="��������" datatype="string" maxlength="10" style="width:75%" notnull="true"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
