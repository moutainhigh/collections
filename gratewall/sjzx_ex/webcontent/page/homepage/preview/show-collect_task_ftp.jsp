<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<%@ page import = "cn.gwssi.common.context.Recordset"%>
<%@ page import = "com.gwssi.common.constant.FileConstant"%>
<%@page import="com.gwssi.common.constant.CollectConstants"%>
<%-- template single/single-table-insert.jsp --%>
<freeze:html>
<head>
<title>�鿴�ɼ�������Ϣftp</title>
</head>
<script language='javascript' src='/script/uploadfile.js'></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>

<script language="javascript">

// �� ��
function func_record_saveRecord()
{
	saveRecord( '', '����ɼ�����' );
	/**var page = new pageDefine("/txn30101000.ajax", "�������Դ�����Ƿ�ʹ��");	 ///���������Դ�����Ƿ�ʹ��
	page.addParameter("record:data_source_id","primary-key:data_source_id");
	page.callAjaxService('nameCheckCallback');*/
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
  		  var page = new pageDefine( "/txn30101003.do", "����ɼ������");
  		  
	     /** page.addParameter("record:collect_task_id","primary-key:collect_task_id");
	      page.addParameter("record:collect_task_id","record:collect_task_id");
	      page.addParameter("record:service_targets_id","record:service_targets_id");
	      page.addParameter("record:task_name","record:task_name");
	      page.addParameter("record:data_source_id","record:data_source_id");
	      page.addParameter("record:task_description","record:task_description");
	      page.addParameter("record:record","record:record");
	      page.addParameter("record:task_status","record:task_status");
	      page.addParameter("record:is_markup","record:is_markup");
	      page.addParameter("record:creator_id","record:creator_id");
	      page.addParameter("record:created_time","record:created_time");
	      page.addParameter("record:last_modify_id","record:last_modify_id");
	      page.addParameter("record:last_modify_time","record:last_modify_time");
	      page.addParameter("record:fj_fk","record:fj_fk");
	      page.addParameter("record:fjmc","record:fjmc");
	      page.callAjaxService('insertTask');*/
  		
  		}
}
function insertTask(errCode,errDesc,xmlResults){
		
		if(errCode != '000000'){
			alert('�������['+errCode+']==>'+errDesc);
			return;
		}else{
		    var collect_task_id=_getXmlNodeValues(xmlResults,'record:collect_task_id');
		    setFormFieldValue("record:collect_task_id",collect_task_id);
		    setFormFieldValue("primary-key:collect_task_id",collect_task_id);
		    alert("����ɹ�!");
		}
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '����ɼ������' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '����ɼ������' );	// /txn30101001.do
}

// �� ��
function func_record_goBack()
{
	var page = new pageDefine( "/txn30101001.do", "��ѯ�ɼ�����");
	page.updateRecord();
}
function getParameter(){
	//�ӵ�ǰҳ��ȡֵ�����key=value��ʽ�Ĵ�
    var parameter = "input-data:service_targets_id="+ getFormFieldValue('record:service_targets_id')+"&input-data:collectType=<%=CollectConstants.TYPE_CJLX_FTP%>";
	return parameter;
}
// �޸ķ��ļ���Ϣ
function func_record_updateRecord(idx)
{
	var page = new pageDefine( "/txn30201004.do", "�޸��ļ���Ϣ");
	page.addValue(idx,"primary-key:ftp_task_id");
	var service_targets_id=getFormFieldValue("record:service_targets_id");
	page.addValue(service_targets_id,"primary-key:service_targets_id");
	page.updateRecord();
}
// ɾ��������Ϣ
function func_record_deleteRecord(idx)
{
if(confirm("�Ƿ�ɾ��ѡ�еļ�¼")){
	var page = new pageDefine( "/txn30201005.do", "ɾ���ļ���Ϣ" );
	page.addValue(idx,"primary-key:ftp_task_id");
	page.addParameter("record:service_targets_id","primary-key:service_targets_id");
	page.addParameter("record:collect_task_id","primary-key:collect_task_id");
	page.addParameter("record:fj_fk","record:fj_fk");
	page.addParameter("record:fjmc","record:fjmc");
	//page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" ); 
	page.updateRecord();
	}
	
}
//�鿴�ɼ����������Ϣ
function func_record_viewRecord(idx)
{
	var page = new pageDefine( "/txn30201006.do", "�鿴�ļ���Ϣ", "modal" );
	page.addValue(idx, "primary-key:ftp_task_id" );
	var collect_task_id=getFormFieldValue('record:collect_task_id');
	page.addValue(collect_task_id,"primary-key:collect_task_id");
	page.updateRecord();
}
// ��ȡ�ɼ������Ӧ����
function func_record_getFunction()
{
	var collect_task_id=getFormFieldValue('record:collect_task_id');
	if(collect_task_id==null||collect_task_id==""){
	    alert("������д�ɼ�������Ϣ!");
	    clickFlag=0;
	    return false;
    }
	var page = new pageDefine( "/txn30101009.do", "��ȡ�ɼ������Ӧ����");
	page.addParameter("record:collect_task_id","record:collect_task_id");
	page.addParameter("record:data_source_id","record:data_source_id");
	page.updateRecord();
}
function changeTarget()
{
	if (getFormFieldValue("record:service_targets_id") != null && getFormFieldValue("record:service_targets_id") != "")
	{
		var name=document.getElementById("record:_tmp_service_targets_id").value;
		setFormFieldValue("record:task_name",name+"_FTP");
		setFormFieldValue("record:data_source_id",0,"");
	}
}
//���Ʋɼ�����
function chooseCjzq(){
 var cjzq=getFormFieldValue("record:interval_time");
 if(cjzq==null||""==cjzq){
 	//var page = new pageDefine( "/page/collect/config/schedule/insert-collect_task_scheduling.jsp", "�����������", "modal");
	//page.addRecord();
	window.showModalDialog("/page/collect/config/schedule/insert-collect_task_scheduling.jsp", window, "dialogHeight:350px;dialogWidth:500px;dialogTop:250px;dialogLeft:300px;help:no;scrollbar:no;resizable:no;status:no");
	//window.open("/page/collect/config/schedule/insert-collect_task_scheduling.jsp", null, "dialogHeight:350px;dialogWidth:500px;dialogTop:250px;dialogLeft:300px;help:no;scrollbar:no;resizable:no;status:no");
 }else{
 	window.showModalDialog("/page/collect/config/schedule/insert-collect_task_scheduling.jsp", window, "dialogHeight:350px;dialogWidth:500px;dialogTop:250px;dialogLeft:300px;help:no;scrollbar:no;resizable:no;status:no");
 	//var page = new pageDefine( "/txn30801004.do", "�޸��������", "modal");
	//page.addParameter("primary-key:collect_task_id","primary-key:collect_task_id");
	//page.updateRecord();
 }
}
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	//document.getElementById('taskName').innerText = ("�������"+document.getElementById('record:task_name').value);
	//document.getElementById('targetName').innerText = ("�ɼ��������ƣ�"+document.getElementById('record:service_targets_name').value);
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:errors/>

<freeze:form action="/txn30101015" enctype="multipart/form-data">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="collect_task_id" caption="�ɼ�����ID" style="width:95%"/>
  </freeze:frame>
<!--   <br>
	<table border=0 cellpadding=0 cellspacing=0 width="95%" align="center"
		style="border-collapse: collapse;">
		<tr>
			<td id='targetName' width="45%" style="font-weight:bold;text-align:right;padding-right:10px;"></td>
			<td width="15%"></td>
			<td id='taskName' style="text-align:left;font-weight:bold;padding-left:10px;"></td>
		</tr>
	</table> -->
  <freeze:block property="record" caption="�鿴�ɼ�������Ϣ" width="95%">
      <freeze:hidden property="collect_task_id" caption="�ɼ�����ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:cell property="data_source_id" caption="����Դ" show="name" valueset="��Դ����_����Դ"  style="width:95%"/>
      <freeze:cell property="scheduling_day1" caption="�ɼ�����" datatype="string"  style="width:80%"/>
      <freeze:hidden property="collect_type" caption="�ɼ�����" datatype="string" maxlength="20" />
      <freeze:cell property="task_description" caption="����˵��"  style="width:98%"/>
      <freeze:cell property="record" caption="����˵��"  style="width:98%"/>
      <freeze:hidden property="fjmc" caption="�ļ��ϴ�" />
      <freeze:hidden property="service_targets_id" caption="�����������" style="width:95%"/>
      <freeze:hidden property="task_name" caption="��������" datatype="string"  style="width:95%"/>
      <freeze:hidden property="fj_fk" caption="�ļ��ϴ�id" style="width:90%" />
      <freeze:hidden property="delIDs" caption="multi file id" style="width:90%"  />
      <freeze:hidden property="delNAMEs" caption="multi file name" style="width:90%" />
      <freeze:hidden property="task_status" caption="����״̬" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="is_markup" caption="��Ч���" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="creator_id" caption="������ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="created_time" caption="����ʱ��" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:hidden property="last_modify_id" caption="����޸���ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="����޸�ʱ��" datatype="string" maxlength="19" style="width:95%"/>
  </freeze:block>
   <freeze:grid property="dataItem" caption="�ļ��б�" keylist="ftp_task_id" multiselect="false" checkbox="false" width="95%" fixrow="false" >
      <freeze:hidden property="ftp_task_id" caption="ftp����ID"  />
      <freeze:hidden property="collect_task_id" caption="�ɼ�����ID"  />
      <freeze:cell property="@rowid" caption="���"  style="width:5%" align="center" />
      <freeze:cell property="file_name_cn" caption="�ļ���������" style="width:20%" />
      <freeze:cell property="file_name_en" caption="�ļ�����" style="width:15%" />
      <freeze:cell property="service_no" caption="������"   style="width:15%" />
      <freeze:cell property="file_description" caption="�ļ�����" style="width:30%" />
      <freeze:hidden property="oper" caption="����" style="width:15%" />
  </freeze:grid>
</freeze:form>
</freeze:body>
</freeze:html>
