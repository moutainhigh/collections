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
	var wsTaskId = getFormFieldValue("select-key:webservice_task_id");
	var page = new pageDefine("/txn30103033.do","����Id��ѯ��������","modal","650","350");
   	//var page = new pageDefine("insert-collect_parameter.jsp","����Id��ѯ��������","modal","650","350");
	page.addValue(wsTaskId, "select-key:webservice_task_id");
	page.updateRecord();
}

// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '����webservice�����' );	// /txn30102001.do
}

// �� ��
function func_record_goBack()
{
	var page = new pageDefine( "/txn30101004.do", "��ѯ�ɼ�����");
	page.addParameter("record:webservice_task_id","primary-key:webservice_task_id");
	page.addParameter("record:collect_task_id","primary-key:collect_task_id");
	page.updateRecord();
}

function getParameter()
{
	//�ӵ�ǰҳ��ȡֵ�����key=value��ʽ�Ĵ�
    var parameter = 'input-data:service_targets_id=<freeze:out  value="${record.service_targets_id}"/>';
	return parameter;
}

//ɾ����������
function func_record_updateDelete(ids){
	//alert("func_record_updateDelete ids="+ids);
	var page = new pageDefine( "/txn30103055.ajax", "ɾ������" );
	page.addValue(ids,"primary-key:webservice_patameter_id" );
	//page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
	page.callAjaxService('doCallback_delparam');
}
function doCallback_delparam(errCode, errDesc, xmlResults){
	if(errCode == '000000'){
		alert('ɾ���ɹ�');	
	}else{
		alert("ɾ��ʧ�� errCode="+errCode);
	}
	
	
	var wsTaskId   = getFormFieldValue("select-key:webservice_task_id");
	var service_no = getFormFieldValue("record:service_no");
	var href="/txn30102111.do?select-key:service_no="+service_no
			+"&select-key:webservice_task_id="+wsTaskId;
	window.location.href=href; 
	
}

function func_record_updateRecord(ids){
	//alert("func_record_updateRecord ids="+ids);
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
		var wsTaskId = getFormFieldValue("select-key:webservice_task_id");
   		var page = new pageDefine("/txn30102114.do","����Id��ѯ��������","modal");
		page.addValue(wsTaskId, "select-key:webservice_task_id");
		page.updateRecord();
   })
   
   $('#btn_star').click(function(){
   })
   
   $('#btn_pause').click(function(){
   })
         
}

//ɾ��������
function doDelete(){
	if(confirm("��ȷ��Ҫɾ���˷�����?")){
		var wsTaskId = getFormFieldValue("select-key:webservice_task_id");
	 	var url="/txn30102025.ajax?primary-key:webservice_task_id="+wsTaskId;
		var page = new pageDefine(url, "ɾ������");
	 	page.callAjaxService('doCallback_delete');
  	}
}

function doCallback_delete(errCode, errDesc, xmlResults){
	if(errCode == '000000'){
		alert('ɾ���ɹ�');
		
		//���ø����ڷ���ɾ����Ӧ�����ڵ�
		var wsTaskId = getFormFieldValue("select-key:webservice_task_id");
		parent.window.delNode(wsTaskId);
		
	}	  
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
	<freeze:errors />

	<freeze:form action="/txn30102111">
		<freeze:frame property="select-key" width="95%">
			<freeze:hidden property="webservice_task_id" caption="WEBSERVICE����ID"
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
											<!-- <input name="btn_del" id='btn_del' class="grid-menu"
												style="" type="button" onclick="doDelete()" value="ɾ��" />  -->
										</td>
										<td class="btn_right"></td>
									</tr>
								</table>
								<!-- 
								<freeze:if test="${record.method_status=='0'}">
									<table cellspacing="0" cellpadding="0" class="button_table">
										<tr>
											<td class="btn_left"></td>
											<td>
											<div class="run" id='btn_star'></div>
											</td>
											<td class="btn_right"></td>
										</tr>
									</table>
								</freeze:if>
								<freeze:if test="${record.method_status=='1'}">
									<table cellspacing="0" cellpadding="0" class="button_table">
										<tr>
											<td class="btn_left"></td>
											<td>
											<div class="stop" id='btn_stop'></div>
											</td>
											<td class="btn_right"></td>
										</tr>
									</table>
								</freeze:if>
								 -->
							</td>
							<td class="rightTitle"></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<freeze:block property="record" caption="�޸ķ�����Ϣ" width="95%">
			<freeze:hidden property="webservice_task_id" caption="WEBSERVICE����ID"
				datatype="string" maxlength="32" style="width:95%" />
			<freeze:hidden property="collect_task_id" caption="�ɼ�����ID"
				datatype="string" maxlength="32" style="width:95%" />
			<freeze:hidden property="service_targets_id" caption="����ID"
				datatype="string" maxlength="32" style="width:95%" />
			<freeze:cell property="service_no" caption="����ID��" datatype="string"
				style="width:95%" />
			<freeze:cell property="method_name_en" caption="�������ƣ�"
				datatype="string" style="width:95%" />
			<freeze:cell property="method_name_cn" caption="������������"
				datatype="string" style="width:95%" />
			<freeze:cell property="collect_table" caption="��Ӧ�ɼ���" show="name"
				notnull="true" valueset="��Դ����_�ɼ���" style="width:95%" />
			<freeze:cell property="collect_mode" caption="�ɼ���ʽ" show="name"
				notnull="true" valueset="��Դ����_�ɼ���ʽ" style="width:95%" />
			<freeze:hidden property="is_encryption" caption="�Ƿ����"
				datatype="string" style="width:95%" />
			<freeze:cell property="encrypt_mode" caption="���ܷ���" notnull="true"
				show="name" valueset="��Դ����_���ܷ���" value="01" style="width:95%" />
			<freeze:cell property="method_description" caption="��������" colspan="2"
				style="width:98%" />
			<freeze:cell property="web_name_space" caption="�����ռ䣺" colspan="2"
				style="width:95%" />
			<freeze:hidden property="method_status" caption="����״̬"
				datatype="string" style="width:95%" />
		</freeze:block>
		<br>
		<freeze:grid property="dataItem" caption="�����б�"
			keylist="webservice_patameter_id" multiselect="false"
			checkbox="false" width="95%" fixrow="false">
			<freeze:button name="record_addRecord" caption="���Ӳ���"
				enablerule="0" hotkey="ADD" align="right"
				onclick="func_record_addRecord();" />
			<freeze:hidden property="webservice_patameter_id" caption="����ID" />
			<freeze:hidden property="webservice_task_id" caption="����ID" />
			<freeze:cell property="patameter_type" caption="��������"
				style="width:25%"  align="center"/>
			<freeze:cell property="patameter_name" caption="������"
				style="width:40%"  align="center"/>
			<freeze:cell property="patameter_style" caption="������ʽ"
				valueset="�ɼ�����_������ʽ" style="width:20%"  align="center" />
			<freeze:cell property="oper" caption="����" align="center"
				style="width:15%" />
		</freeze:grid>
		
	</freeze:form>
</freeze:body>
</freeze:html>
