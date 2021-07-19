<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�ɼ������б�</title>
<style type="text/css">
.activerow {
	cursor:auto;
}
</style>
</head>

<script language="javascript">

// �޸ķ������
function func_record_updateRecord(index)
{
	var page = new pageDefine( "/txn50211002.do", "��������", "modal", 600,400 );
	page.addValue( getFormFieldValue("record:sys_clt_user_id",index), "record:sys_clt_user_id" );
	page.updateRecord();
}


// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var stateTds = document.getElementsByName("span_record:state");
	var states = getFormAllFieldValues("record:state");
    var operationSpan = document.getElementsByName("span_record:oper");
	for (var i=0; i < operationSpan.length; i++){
		if(states[i] == '1'){
			stateTds[i].style.textAlign = 'right';
		}
	    operationSpan[i].innerHTML += "<a onclick='func_record_updateRecord(" + i + ");' href='#'>����</a>";
	}
}


_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�ɼ������б�"/>
<freeze:errors/>

<freeze:form action="/txn50211001">

  <freeze:grid property="record" caption="�ɼ������б�" keylist="sys_clt_user_id" width="95%" navbar="bottom" checkbox="false" multiselect="false" fixrow="false" rowselect="false">
      <freeze:button name="record_updateRecord" caption="����"  enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();" visible="false"/>
      <freeze:hidden property="sys_clt_user_id" caption="�ɼ����Ա��"/>
      <freeze:hidden property="state" caption="״̬"/>
      <freeze:cell property="@rowid" caption="���" style="width:6%" align="center"/>
      <freeze:cell property="name" caption="�ɼ���Դ" style="width:20%" align="left"/>
      <freeze:cell property="user_type" caption="�û�����" style="width:10%" align="left" valueset="user_type"/>
      <freeze:cell property="strategy" caption="�ɼ�����" style="width:8%" align="left" valueset="ʱ������" />
      <freeze:cell property="strategytime" caption="�ɼ�ʱ��" style="width:20%" align="left" />
      <freeze:cell property="state" caption="״̬" style="width:8%" align="left" valueset="syzt" />
      <freeze:cell property="startdate" caption="��ʼ����" style="width:12%" align="left" />
      <freeze:cell property="enddate" caption="��������" style="width:12%" align="left" />
      <freeze:cell property="oper" caption="����" style="width:4%" align="center" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
