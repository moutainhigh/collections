<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.context.vo.VoUser"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="cn.gwssi.common.context.TxnContext"%>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<%
  HttpSession usersession = request.getSession(false);
  VoUser voUser = (VoUser) usersession.getAttribute(TxnContext.OPER_DATA_NODE); 
  String user=voUser.getOperName();
%>
<title>���ӵ���������Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveRecord()
{
	
	saveRecord( '', '���浯������' );
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '���浯������' );
}

// ���沢�ر�
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

function formatDateToString(date){
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	month = month.toString().length == 1 ? "0" + month : month; 
	var day = date.getDate();
	day = day.toString().length == 1 ? "0" + day : day;
	
	return year + "-" + month + "-" + day;
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	// ����publish_dateĬ��Ϊ���죬expire_dateĬ��Ϊ10���
	var today = new Date;
	var expire = Date.parse(today) + 10 * 24 * 3600 * 1000;
	var publish_date = formatDateToString(today);
	var expire_date = formatDateToString(new Date(expire));
	setFormFieldValue("record:publish_date", publish_date);
	setFormFieldValue("record:expire_date", expire_date);
	setFormFieldValue("record:AUTHOR", "<%=user%>");
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="���ӵ���������Ϣ"/>
<freeze:errors/>
<freeze:form action="/txn60800003">
  <freeze:block property="record" caption="���ӵ���������Ϣ" columns="1" width="95%" captionWidth="0.25">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="sys_popwindow_id" caption="ϵͳ����ID" datatype="string" maxlength="32" style="width:75%"/>
      <freeze:hidden property="AUTHOR" caption="������" datatype="string" maxlength="10" style="width:75%"/>
      <freeze:textarea property="content" caption="����" rows="4" minlength="1" maxlength="4000" style="width:75%"/>
      <freeze:text property="PUBLISH_DEPART" caption="��������"   maxlength="25" style="width:75%"/>
      <freeze:hidden property="roles" caption="�ɲ鿴������ɫ" style="width:75%"/>
      <freeze:datebox property="publish_date" caption="��������" datatype="string" maxlength="10" style="width:75%" notnull="true"/>
      <freeze:datebox property="expire_date" caption="��������" datatype="string" maxlength="10" style="width:75%" notnull="true"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
