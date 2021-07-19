<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<script
	src="<%=request.getContextPath()%> /script/common/js/validator.js"></script>
<script language="javascript"
	src="<%=request.getContextPath()%>/script/lib/jquery.min.js"></script>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
	<title>�޸�webservice������Ϣ</title>
</head>

<script language="javascript">


function func_record_addRecord(){
	var wsTaskId = getFormFieldValue("select-key:database_task_id");
	var page = new pageDefine("/txn30103033.do","����Id��ѯ��������","modal","650","350");
   	//var page = new pageDefine("insert-collect_parameter.jsp","����Id��ѯ��������","modal","650","350");
	page.addValue(wsTaskId, "select-key:database_task_id");
	page.updateRecord();
}

// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '����database�����' );	// /txn30102001.do
}

// �� ��
function func_record_goBack()
{
	var page = new pageDefine( "/txn30101004.do", "��ѯ�ɼ�����");
	page.addParameter("record:database_task_id","primary-key:database_task_id");
	page.addParameter("record:collect_task_id","primary-key:collect_task_id");
	page.updateRecord();
}

function getParameter(){
	//�ӵ�ǰҳ��ȡֵ�����key=value��ʽ�Ĵ�
    var parameter='input-data:service_targets_id='+getFormFieldValue('record:service_targets_id');
	return parameter;
}

function getParameterForTable(){
	//�ӵ�ǰҳ��ȡֵ�����key=value��ʽ�Ĵ�  CollectConstants.TYPE_CJLX_FILEUPLOADֵ01������Դ�����ݿ�����
	var data_source_id = getFormFieldValue("record:data_source_id");
	
	if(data_source_id==null || data_source_id == ""){
		alert("����ѡ������Դ��");
	}else{
		var parameter = "input-data:data_source_id="+ getFormFieldValue('record:_tmp_data_source_id');
		return parameter;
	}
}

function getParameterForTableColumn(){
	//�ӵ�ǰҳ��ȡֵ�����key=value��ʽ�Ĵ�  CollectConstants.TYPE_CJLX_FILEUPLOADֵ01������Դ�����ݿ�����
	var data_source_id = getFormFieldValue("record:data_source_id");
	var source_collect_table = getFormFieldValue("record:source_collect_table");
	
	if(source_collect_table==null || source_collect_table == ""){
		alert("����ѡ��Դ�ɼ�����");
	}else{
		var parameter = "input-data:data_source_id="+ getFormFieldValue('record:data_source_id')+"&input-data:source_collect_table="+ getFormFieldValue('record:source_collect_table');
		return parameter;
	}
}

function func_record_updateDelete(ids){
	alert(1);
	var page = new pageDefine( "/txn30103055.do", "ɾ������" );
	page.addValue(ids,"primary-key:webservice_patameter_id" );
	page.updateRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
	page.callAjaxService('doCallback_delete');
}

function func_record_updateRecord(ids){
	var page = new pageDefine("/txn30103033.do","����Id��ѯ��������","modal","650","350");
	page.addValue(ids, "primary-key:webservice_patameter_id");
	page.updateRecord();
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var ids = getFormAllFieldValues("dataItem:webservice_patameter_id");
	for(var i=0; i<ids.length; i++){
	   var htm='<a href="#" title="�޸�" onclick="func_record_updateRecord(\''+ids[i]+'\');"><div class="edit"></div></a>&nbsp;';
	   	   htm+='<a href="#" title="ɾ��" onclick="func_record_updateDelete(\''+ids[i]+'\');"><div class="delete"></div></a>&nbsp;';
	   document.getElementsByName("span_dataItem:oper")[i].innerHTML +=htm;
	 }
	 var paramStyle = document.getElementById("label:dataItem:patameter_style");
	 if(paramStyle){
		 paramStyle.innerText += '(*)';
	 }
	 
	$('#btn_config3').click(function(){
		//var wsTaskId = getFormFieldValue("select-key:database_task_id");
   		//var page = new pageDefine("/txn30102114.do","����Id��ѯ��������","modal");
		//page.addValue(wsTaskId, "select-key:database_task_id");
		//page.updateRecord();
		var page = new pageDefine( "/txn30501009.do", "�޸ķ�����Ϣ","modal");
		var idx = getFormFieldValue('record:database_task_id');
		page.addValue(idx,"primary-key:database_task_id");
		var collect_task_id=getFormFieldValue('record:collect_task_id');
		page.addValue(collect_task_id,"primary-key:collect_task_id");
	
		page.updateRecord();
   })
   
   $('#btn_star').click(function(){
   })
   
   $('#btn_pause').click(function(){
   })
         
}

function doCallback_update(errCode, errDesc, xmlResults){
	if(errCode == '000000'){
		alert('�޸ĳɹ�');
		parent.window.location.reload();
	}	  
}


function doDelete(){
	//if(confirm("��ȷ��Ҫɾ���˷�����?")){
		//var wsTaskId = getFormFieldValue("select-key:database_task_id");
	 	//var url="/txn30102025.ajax?primary-key:database_task_id="+wsTaskId;
		//var page = new pageDefine(url, "ɾ������");
	 	//page.callAjaxService('doCallback_delete');
  	//}
  	
  	if(confirm("�Ƿ�ɾ��ѡ�еļ�¼")){
	//var page = new pageDefine( "/txn30501005.do", "ɾ��������Ϣ" );
	var page = new pageDefine( "/txn30501005.ajax", "ɾ��������Ϣ" );
	var idx = getFormFieldValue('record:database_task_id');
	page.addValue(idx,"primary-key:database_task_id");
	page.addParameter("record:collect_task_id","primary-key:collect_task_id");
	//page.updateRecord();
	page.callAjaxService('doCallback_delete');
	}
}

function doCallback_delete(errCode, errDesc, xmlResults){
	if(errCode == '000000'){
		alert('ɾ���ɹ�');
		parent.window.location.reload();
	}	  
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
	<freeze:errors />

	<freeze:form action="/txn30501003">
		<freeze:frame property="select-key" width="95%">
			<freeze:hidden property="database_task_id" caption="DATABASE����ID"
				style="width:95%" />
		</freeze:frame>
		<table width="95%" border="0" align="center" class="frame-body"
			cellpadding="0" cellspacing="0">
			<tr>
				<td colspan="5">
					<table width="100%" cellspacing="0" cellpadding="0" border="0">
						<tr>
							<td class="leftTitle"></td>
							<td class="secTitle">
								&nbsp;&nbsp;
							</td>
							<td class="centerTitle">
								<table cellspacing="0" cellpadding="0" class="button_table">
									<tr>
										<td class="btn_left"></td>
										<td>
											<!-- <input name="btn_config3" id='btn_config3' class="grid-menu"
												style="" type="button" value="�޸�" /> -->
											<div class="edit" id='btn_config3'></div>
										</td>
										<td class="btn_right"></td>
									</tr>
								</table>
								<table cellspacing="0" cellpadding="0" class="button_table">
									<tr>
										<td class="btn_left"></td>
										<td>
											<div class="delete" id='btn_del' onclick="doDelete()"></div>
										</td>
										<td class="btn_right"></td>
									</tr>
								</table>
							</td>
							<td class="rightTitle"></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<freeze:block property="record" caption="�޸ķ�����Ϣ" width="95%">
			<freeze:hidden property="service_targets_id" caption="�����������"   style="width:95%"/>
      		<freeze:hidden property="data_source_id" caption="����Դ"    style="width:95%"/> 
      		<freeze:cell property="task_name" caption="��������" datatype="string"  style="width:95%"/>
      		
      		<freeze:cell property="collect_table" caption="Ŀ��ɼ���" valueset="��Դ����_�ɼ���" style="width:95%" />
      		<freeze:cell property="collect_mode" caption="�ɼ���ʽ" valueset="��Դ����_�ɼ���ʽ" style="width:95%" />
      		<freeze:cell property="source_collect_table" caption="Դ�ɼ���"   style="width:95%" />
      		<freeze:cell property="source_collect_column" caption="�����ֶ�" style="width:95%" />
      		
      		<freeze:cell property="description" caption="˵��"  style="width:95%"/>
      		<freeze:hidden property="created_time" caption="����ʱ��" datatype="string" maxlength="19"  style="width:95%"/>
      		<freeze:hidden property="database_task_id" caption="�ɼ����ݿ�����ID" datatype="string" maxlength="32"  style="width:95%"/>
      		<freeze:hidden property="collect_task_id" caption="�ɼ�����ID" datatype="string" maxlength="32" style="width:95%"/>	
				
		</freeze:block>
		<br>
		
	</freeze:form>
</freeze:body>
</freeze:html>
