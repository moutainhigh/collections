<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�޸�etl������Ϣ</title>
</head>

<script language="javascript">
//���Ʋɼ�����
function chooseCjzq(){

 	window.showModalDialog("/page/collect/config/schedule/insert-etl_task_scheduling.jsp", window, "dialogHeight:350px;dialogWidth:500px;dialogTop:250px;dialogLeft:300px;help:no;scrollbar:no;resizable:no;status:no");

}
// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '����etl�����ʧ��!' );	// /txn30300001.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn30300001.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	document.getElementById("label:record:inteval").innerText = '*'+document.getElementById("label:record:inteval").innerText;
	document.getElementById("label:record:inteval").style.color = 'red';
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸�etl������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn30300002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="etl_id" caption="����ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸�etl������Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="etl_id" caption="����ID" datatype="string"/>
      <freeze:browsebox property="res_target_id" caption="�������" valueset="��Դ����_�����������"  show="name"  notnull="true"   style="width:98%"/>
      <freeze:text property="subj_name" caption="��������" datatype="string" notnull="true" maxlength="240"  style="width:98%"/>
      <freeze:text property="inteval" caption="�ɼ�����" datatype="string" notnull="true" readonly="true" style="width:80%"/>&nbsp;<INPUT TYPE="button" Value="����" onclick="chooseCjzq()" class="FormButton">
      
      <freeze:select property="add_type" valueset="��Դ����_�ɼ���ʽ" caption="���ݴ�������" notnull="true" style="width:98%"/>
      <freeze:textarea cols="2" property="subj_desc" caption="��������"  colspan="2"  maxlength="1000"  style="width:98%"/>
      <freeze:hidden property="is_markup" caption="�Ƿ�ɾ��" datatype="string" value="Y" style="width:98%"/>
      <freeze:hidden property="show_order" caption="�����" style="width:98%"/>
      <freeze:hidden property="task_scheduling_id" caption="�ƻ�����ID"  />
      <freeze:hidden property="scheduling_type" caption="�ƻ���������"  />
      <freeze:hidden property="start_time" caption="�ƻ�����ʼʱ��"  />
      <freeze:hidden property="end_time" caption="�ƻ��������ʱ��"  />
      <freeze:hidden property="scheduling_week" caption="�ƻ���������"  />
      <freeze:hidden property="scheduling_day" caption="�ƻ���������"  />
      <freeze:hidden property="scheduling_count" caption="�ƻ�����ִ�д���"  />
      <freeze:hidden property="interval_time" caption="ÿ�μ��ʱ��"  />
      <freeze:hidden property="schedule_json" caption="��������ʵ������"  />
      
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
