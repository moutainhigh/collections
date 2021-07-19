<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�ɼ���־�б�</title>
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
	var page = new pageDefine( "/txn50212002.do", "��ϸ��־" );
	page.addValue( getFormFieldValue("record:sys_clt_log_id",index), "select-key:sys_clt_log_id" );
	page.goPage();
}


// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
    var state = getFormAllFieldValues("record:state"); 
    var operationSpan = document.getElementsByName("span_record:oper");
	for (var i=0; i < operationSpan.length; i++){
	    if(state[i]=="1"){
	    	operationSpan[i].innerHTML += "<a onclick='func_record_updateRecord(" + i + ");' href='#'>�鿴��־</a>";
	    }
	}
}


_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�ɼ���־�б�"/>
<freeze:errors/>

<freeze:form action="/txn50212001">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:select property="sys_clt_user_id" caption="�ɼ���Դ"  valueset="�ɼ���Դ����" show="name" data="value" style="width:60%"/> 
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="�ɼ���־�б�" keylist="sys_clt_log_id" width="95%" navbar="bottom" checkbox="false" multiselect="false" fixrow="false" rowselect="false">
      <freeze:button name="record_updateRecord" caption="�鿴��־"  enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();" visible="false"/>
      <freeze:hidden property="sys_clt_log_id" caption="�ɼ����Ա��"/>
      <freeze:hidden property="state" caption="״̬"/>
      <freeze:cell property="@rowid" caption="���" style="width:6%" align="center"/>
      <freeze:cell property="sys_clt_user_id" caption="�ɼ���Դ" valueset="�ɼ���Դ����" style="width:10%" align="left"/>
      <freeze:cell property="clt_startdate" caption="�ɼ���ʼ����" style="width:10%" align="left" />
      <freeze:cell property="clt_enddate" caption="�ɼ���������" style="width:10%" align="left" />
      <freeze:cell property="exc_starttime" caption="ִ�п�ʼʱ��" style="width:15%" align="left" />
      <freeze:cell property="exc_endtime" caption="ִ�н���ʱ��" style="width:15%" align="left" />      
      <freeze:cell property="state" caption="״̬" style="width:8%" align="left" valueset="�ɼ�ִ��״̬" />
      <freeze:cell property="logdesc" caption="������Ϣ" style="width:18%" align="left" />
      <freeze:cell property="oper" caption="����" style="width:8%" align="center" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
