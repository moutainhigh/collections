<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<%@ page import = "cn.gwssi.common.context.Recordset"%>
<%@ page import = "com.gwssi.common.constant.FileConstant"%>
<%-- template single/single-table-update.jsp --%>
<freeze:html>
<head>
<title>�鿴�ɼ�������Ϣ</title>
</head>
<script language="javascript">
//�鿴�ɼ����������Ϣ
function func_record_viewRecord(idx)
{
	var page = new pageDefine( "/txn30102006.do", "�鿴������Ϣ", "modal" );
	page.addValue(idx,"primary-key:webservice_task_id");
	
	page.updateRecord();
}

// �� �沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '����ɼ������' );	// /txn30101001.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn30101001.do
}
function getParameter(){
	//�ӵ�ǰҳ��ȡֵ�����key=value��ʽ�Ĵ�
    var parameter = 'input-data:service_targets_id=' + getFormFieldValue('record:service_targets_id');
	return parameter;
}


// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	//var targetName = parent.targetName;
	//alert(targetName);
	/* var ids = getFormAllFieldValues("dataItem:webservice_task_id");
	for(var i=0; i<ids.length; i++){
	   var htm='<a href="#" title="�鿴" onclick="func_record_viewRecord(\''+ids[i]+'\');"><div class="detail"></div></a>&nbsp;';
	   document.getElementsByName("span_dataItem:oper")[i].innerHTML +=htm;
	 } */
	//document.getElementById('taskName').innerText = ("�������"+document.getElementById('record:task_name').value);
	//document.getElementById('targetName').innerText = ("�ɼ��������ƣ�"+document.getElementById('record:service_targets_name').value);
	//alert(document.getElementById('body-div').clientHeight);
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:errors/>

<freeze:form action="/txn30101006">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="collect_task_id" caption="�ɼ�����ID" style="width:95%"/>
  </freeze:frame>
<!-- <br>
	<table border=0 cellpadding=0 cellspacing=0 width="95%" align="center"
		style="border-collapse: collapse;">
		<tr>
			<td id='targetName' width="45%" style="font-weight:bold;text-align:right;padding-right:10px;"></td>
			<td width="15%"></td>
			<td id='taskName' style="text-align:left;font-weight:bold;padding-left:10px;"></td>
		</tr>
	</table> -->
  <freeze:block property="record" caption="�鿴�ɼ�������Ϣ" width="95%">
      <freeze:hidden property="collect_task_id" caption="�ɼ�����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="service_targets_id" caption="�����������" style="width:95%"/>
      <freeze:hidden property="task_name" caption="��������" datatype="string"  style="width:95%"/>
      <freeze:cell property="data_source_id" caption="����Դ" show="name" valueset="��Դ����_����Դ"  style="width:95%"/>
      <freeze:cell property="scheduling_day1" caption="�ɼ�����" datatype="string"  style="width:95%"/>
      <freeze:hidden property="collect_type" caption="�ɼ�����" datatype="string" maxlength="20" />
      <freeze:cell property="task_description" caption="����˵��" colspan="2"  style="width:98%"/>
      <freeze:cell property="record" caption="����˵��" colspan="2"  style="width:98%"/>
      <freeze:hidden property="task_status" caption="����״̬" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="is_markup" caption="��Ч���" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="creator_id" caption="������ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="created_time" caption="����ʱ��" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:hidden property="last_modify_id" caption="����޸���ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="����޸�ʱ��" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:hidden property="service_targets_name" caption="�����������" datatype="string" maxlength="32" style="width:95%"/>
   </freeze:block>
   <freeze:grid property="dataItem" caption="�����б�" keylist="webservice_task_id" multiselect="false" checkbox="false" width="95%" fixrow="false" >
      <freeze:hidden property="webservice_task_id" caption="����ID"  />
      <freeze:hidden property="collect_task_id" caption="�ɼ�����ID"  />
      <freeze:cell property="@rowid" caption="���"  style="width:5%" align="center" />
      <freeze:cell property="method_name_cn" caption="������������" style="width:20%" />
      <freeze:cell property="method_name_en" caption="��������" style="width:15%" />
      <freeze:hidden property="service_no" caption="����ID"   style="width:15%" />
      <freeze:cell property="method_description" caption="��������" style="width:30%" />
      <freeze:hidden property="oper" caption="����" style="width:15%" />
  </freeze:grid>
</freeze:form>
</freeze:body>
</freeze:html>
