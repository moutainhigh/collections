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
<script language='javascript' src='<%=request.getContextPath()%>/script/uploadfile.js'></script>
<script language="javascript">
// �� ��
function func_record_saveRecord(){
	
	var page = new pageDefine("/txn30101000.ajax", "�������Դ�����Ƿ�ʹ��");	
	page.addParameter("record:data_source_id","primary-key:data_source_id");
	page.addParameter("record:collect_task_id","primary-key:collect_task_id");
	page.callAjaxService('nameCheckCallback');
}
function nameCheckCallback(errCode,errDesc,xmlResults){
		is_name_used = 1;
		if(errCode != '000000'){
			alert('�������['+errCode+']==>'+errDesc);
			return;
		}
		is_name_used=_getXmlNodeValues(xmlResults,'record:name_nums');
		if(is_name_used>0){
  			alert("����Դ�����Ѿ�ʹ��");
  		}else{
  		  var page = new pageDefine( "/txn30101002.ajax", "����ɼ�������Ϣ");
	      page.addParameter("record:collect_task_id","primary-key:collect_task_id");
	      page.addParameter("record:collect_task_id","record:collect_task_id");
	      page.addParameter("record:service_targets_id","record:service_targets_id");
	      page.addParameter("record:task_name","record:task_name");
	      page.addParameter("record:data_source_id","record:data_source_id");
	      page.addParameter("record:collect_type","record:collect_type");
	      page.addParameter("record:task_description","record:task_description");
	      page.addParameter("record:record","record:record");
	      page.addParameter("record:task_status","record:task_status");
	      page.addParameter("record:is_markup","record:is_markup");
	      page.addParameter("record:creator_id","record:creator_id");
	      page.addParameter("record:created_time","record:created_time");
	      page.addParameter("record:last_modify_id","record:last_modify_id");
	      page.addParameter("record:last_modify_time","record:last_modify_time");
	      page.callAjaxService('updateTable');
  		}
}
function updateTable(errCode,errDesc,xmlResults){
		
		if(errCode != '000000'){
			alert('�������['+errCode+']==>'+errDesc);
			return;
		}else{
		    alert("����ɹ�!");
		}
}
//�鿴�ɼ����������Ϣ
function func_record_viewRecord(idx)
{
	var page = new pageDefine( "/txn30102016.do", "�鿴������Ϣ", "modal" );
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

	var ids = getFormAllFieldValues("dataItem:webservice_task_id");
	for(var i=0; i<ids.length; i++){
	   var htm='<a href="#" title="�鿴" onclick="func_record_viewRecord(\''+ids[i]+'\');"><div class="detail"></div></a>&nbsp;';
	   document.getElementsByName("span_dataItem:oper")[i].innerHTML +=htm;
	 }
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�鿴�ɼ�������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn30101006">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="collect_task_id" caption="�ɼ�����ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�鿴�ɼ�������Ϣ" width="95%">
      <freeze:button name="record_goBack" caption="�� ��"hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="collect_task_id" caption="�ɼ�����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:cell property="service_targets_id" caption="�����������" show="name"  valueset="��Դ����_�����������"   style="width:95%"/>
      <freeze:cell property="task_name" caption="��������" datatype="string"  style="width:95%"/>
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
       <%
    DataBus context = (DataBus) request.getAttribute("freeze-databus");
    Recordset fileList=null;
    try{
    fileList = context.getRecordset("fjdb");
    if(fileList!=null && fileList.size()>0){
        for(int i=0;i<fileList.size();i++){
               DataBus file = fileList.get(i);
               String file_id = file.getValue(FileConstant.file_id);
               String file_name = file.getValue(FileConstant.file_name);
	%>
	<tr>
	<td height="32" align="right">��������&nbsp;</td>
	<td colspan="3">
		
	<a href="#" onclick="downFile('<%=file_id%>')" title="����" ><%=file_name %></a>
	</td>
	</tr>
	
	<% }
	     }
	   }catch(Exception e){
		   System.out.println(e);
	   }
	%>   
  </freeze:block>
<br>
   <freeze:grid property="dataItem" caption="�����б�" keylist="webservice_task_id" multiselect="false" checkbox="false" width="95%"  fixrow="false" >
      <freeze:hidden property="webservice_task_id" caption="����ID"  />
      <freeze:hidden property="collect_task_id" caption="�ɼ�����ID"  />
      <freeze:cell property="index" caption="���"  style="width:5%" align="center" />
      <freeze:cell property="method_name_cn" caption="������������" style="width:20%" />
      <freeze:cell property="method_name_en" caption="��������" style="width:15%" />
      <freeze:cell property="service_no" caption="����ID"   style="width:15%" />
      <freeze:cell property="method_description" caption="��������" style="width:30%" />
      <freeze:cell property="oper" caption="����" align="center" style="width:15%" />
  </freeze:grid>
</freeze:form>
</freeze:body>
</freeze:html>
