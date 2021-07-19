<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ page import="cn.gwssi.common.context.Recordset"%>
<%@page import="cn.gwssi.common.dao.resource.PublicResource"%>
<%@page import="cn.gwssi.common.dao.resource.code.CodeMap"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="cn.gwssi.common.context.TxnContext"%>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="1000" height="800">
<head>
<title>�޸Ĳ���ά����Ϣ</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-plugin-data2table/js/jquery.tablet.js"></script>
<link href="<%=request.getContextPath()%>/script/jquery-plugin-Selector/css/jquery.dataSelector.css" rel="stylesheet" type="text/css" />
<base target="_self">
</head>

<script language="javascript">
//Ԥ��������ڸ�ʽ
<%
TxnContext dateContext = new TxnContext();
CodeMap codeMap = PublicResource.getCodeFactory();
Recordset rs = codeMap.lookup(dateContext, "�ɼ�����_���ڸ�ʽ");
String tmp = "";
out.println("var date_type_array = new Array; ");
for(int ii=0; ii<rs.size(); ii++){
	out.println("date_type_array.push({\"key\": \""+rs.get(ii).get("codevalue")
		+"\", \"title\": \""+rs.get(ii).get("codename")+"\"});");
}
%>
//Ԥ����Ĳ�����ʽ
<%
TxnContext codeTbContext = new TxnContext();
rs = codeMap.lookup(codeTbContext, "�ɼ�����_��������");
out.println("var param_type_array = new Array;");
for(int ii=0; ii<rs.size(); ii++){
	out.println("param_type_array.push({\"key\": \""+rs.get(ii).get("codevalue")
		+"\", \"title\": \""+rs.get(ii).get("codename")+"\"});");
}
%>
var isEditing = 0; //�Ƿ��������༭����.
function func_record_addRecord(){
	clickFlag=0; //���Է��̨�İ�ť����
	//�Զ��尴ť����״̬
	isEditing = 1;
	$("input[name='connector']").val("&");
	$('#add_table').show();
	$('#btn_add').removeClass('btn_add').addClass('btn_add_disabled');
	$('#add_table #saveprambtn').unbind("click");
	$('#add_table #saveprambtn').bind("click", function(){addParamValue(1)});
/* 	$('#btn_add')[0].onclick = function(){
		alert("����ɱ�����Ӻ��ٴβ���.");
	}; */
} 

function addParamValue(type){
	var paramName = $('input[name="param_name"]').val();
	var paramValue = $('input[name="param_value"]').val();
	var paramType = $('#param_type option:selected').val();
	var paramDateFormat = $('#date_format_area option:selected').val();
	var showOrder = $('input[name="show_order"]').val();
	var connector = $('input[name="connector"]').val();
	var ws_param_value_id = $('input[name="ws_param_value_id"]').val();
	//var ws_id = document.getElementById('primary-key:webservice_patameter_id').value;
	if(''==paramName || ''==paramValue || ''==showOrder){
		alert("��ɫΪ������,�������д����.");
		return;
	}
	if('02'==paramType && '00'==paramDateFormat){
		alert("��ѡ�����ڸ�ʽ.");
		return;
	}
	if(isNaN(parseInt(showOrder))){
		alert("����д[0,99]���ڵĺ��ʵ���������.");
		return;
	}else if(parseInt(showOrder)<0 || parseInt(showOrder)>99){
		alert("����д[0,99]���ڵĺ��ʵ���������.");
		return;
	}
	var page = new pageDefine("/txn30107003.ajax", "��Ӳ���ֵ");
	if(type==0){
		page = new pageDefine("/txn30107002.ajax", "�޸Ĳ���ֵ");	
		page.addValue(ws_param_value_id, "record:ws_param_value_id");
	}
	page.addParameter("primary-key:webservice_patameter_id", "record:webservice_patameter_id");
	page.addValue(paramName, "record:patameter_name");
	page.addValue(paramValue, "record:patameter_value");
	page.addValue(paramDateFormat, "record:style");
	page.addValue(paramType, "record:param_value_type");
	page.addValue(showOrder, "record:showorder");
	page.addValue(connector, "record:connector");
	//page.updateRecord();
	page.callAjaxService('addParamValueCallBack');
}

	function addParamValueCallBack(errCode, errDesc, xmlResults) {
		if (errCode != '000000') {
			alert('�������[' + errCode + ']==>' + errDesc);
			return;
		}/* 
		var href = window.location.href;
		$('#reload').attr("href", href); */
		$('#reload')[0].click();
	}

	function func_record_deleteRecord(id){
		if(confirm("�Ƿ�ɾ��ѡ�еļ�¼")){
		if(typeof(id) != 'undefined'){
			var page = new pageDefine("/txn30107005.ajax", "ɾ������ֵ");	
			page.addValue(id, "primary-key:ws_param_value_id");
			//page.addParameter("record-key:webservice_patameter_id", "primary-key:webservice_patameter_id");
			//page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
			page.callAjaxService('deleteParamValueCallBack');
		 }
		}
	}
	
	function fn_cancel(){
		goBackWithUpdate();
		//window.close();
	}
	
	function deleteParamValueCallBack(errCode, errDesc, xmlResults) {
		if (errCode != '000000') {
			alert('�������[' + errCode + ']==>' + errDesc);
			return;
		}
		$('#reload')[0].click();
	}
	var record_style='';
	function func_record_updateRecord(id){
		var lineNo = 0;
		if(window.event.srcElement){
			lineNo = window.event.srcElement.parentNode.parentNode.parentNode.parentNode.rowIndex;
			lineNo = lineNo - 1;
		}
		if(typeof(id) != 'undefined' && lineNo > -1){
			$('#add_table').show();
			var param_value_type = getFormFieldValue("record:param_value_type", lineNo);
			var param_name = getFormFieldValue("record:patameter_name", lineNo);
			var param_value = getFormFieldValue("record:patameter_value", lineNo);
			var show_order = getFormFieldValue("record:showorder", lineNo);
			var ws_param_value_id = getFormFieldValue("record:ws_param_value_id", lineNo);
			var connector = getFormFieldValue("record:connector",lineNo);
			record_style = getFormFieldValue("record:style", lineNo);
			
			//��ȡ������������Ӧ��codeֵ������ѡ��
			for(var i=0;i<param_type_array.length;i++){
				var tmp = param_type_array[i];
				if(tmp.title==param_value_type){
					$('#param_type').val(tmp.key);
					
					
					break;
				}				
			}
			
			setParamType();
			//$('#add_table #param_type option[value="'+param_value_type+'"]').attr("selected", true);
			$('#add_table input[name="param_name"]').val(param_name);
			$('#add_table input[name="param_value"]').val(param_value);
			$('#add_table input[name="show_order"]').val(show_order);
			$('#add_table input[name="ws_param_value_id"]').val(ws_param_value_id);
			$("#add_table input[name='connector']").val(connector);
			
			//addParamValue(0); //�����޸ķ���
			$('#add_table #saveprambtn').unbind("click");
			$('#add_table #saveprambtn').bind("click", function(){addParamValue(0)});
			
		}
	}
	
	function updateParamValueCallBack(errCode, errDesc, xmlResults) {
		if (errCode != '000000') {
			alert('�������[' + errCode + ']==>' + errDesc);
			return;
		}
		$('#reload')[0].click();
	}
	
	function hideTable() {
		$('#add_table input ').each(function() {
			var val =$(this).val();
			if(val!='����' && val!='ȡ��'){
				
				$(this).val('');
			}
		});
		isEditing = 0;
		$('#add_table').hide();
	}

	function setParamType() {
		var sel = $("#param_type option:selected").val();
		if("02" == sel){
		
			$('#date_format_title').show();
			$('#date_format_area').show();
			//��ȡ���ڸ�ʽ����Ӧ��codeֵ������ѡ��
			for(var i=0;i<date_type_array.length;i++){
				var tmp = date_type_array[i];
				if(tmp.title==record_style){
					$('#date_format_area select').val(tmp.key);				
					break;
				}				
			}
			
						
		}else{
			$('#date_format_title').hide();
			$('#date_format_area').hide();
		}
		var tdLength = $('#add_table table tr:first td').length;
		$('#add_table table tr:last td').attr('colspan', tdLength);
	}

	// �� ��
	function func_record_saveAndExit() {
		saveAndExit('', '���������'); // /txn30103001.do
	}

	// �� ��
	function func_record_goBack() {
		goBack(); // /txn30103001.do
	}

	// ����������ӣ�ҳ�������ɺ���û���ʼ������
	function __userInitPage() {
		for(ii=0; ii<param_type_array.length; ii++){
			$('#param_type').append('<option value="'+param_type_array[ii].key+'">'+param_type_array[ii].title+'</option>');
		}
		for(ii=0; ii<date_type_array.length; ii++){
			$('#date_format_area select').append('<option value="'+date_type_array[ii].key+'">'+date_type_array[ii].title+'</option>');
		}
		
		var ids = getFormAllFieldValues("record:ws_param_value_id");
		for(var i=0; i<ids.length; i++){
		   var htm='<a href="#" title="�޸�" onclick="func_record_updateRecord(\''+ids[i]+'\');"><div class="edit"></div></a>&nbsp;';
		   htm+='<a href="#" title="ɾ��" onclick="func_record_deleteRecord(\''+ids[i]+'\');"><div class="delete"></div></a>&nbsp;&nbsp;';
		   document.getElementsByName("span_record:oper")[i].innerHTML +=htm;
		}
		var href = window.location.href;
		
		$('#reload').attr("href", href);
		var paramType = getFormFieldValue("param:patameter_style");
		if('01'==paramType){	//�ַ���
			$('#connector_title').show();
			$('#connectorTD').show();
			$("input[name='connector']").val("&");
		}else if('02'==paramType){	//Xml����
			$('#connector_title').hide();
			$('#connectorTD').hide();
			$("input[name='connector']").val("");
			var index = document.getElementById('label:record:connector').cellIndex;
			$('#record tr').each(function(){
				$(this).find('td:eq('+index+')').hide();
			})
		}
	}

	_browse.execute('__userInitPage()');
</script>
<freeze:body>
<freeze:title caption="ά��������Ϣ"/>
<freeze:errors/>
<freeze:form action="/txn30103002"> 
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="webservice_patameter_id" caption="����ID" style="width:95%"/>
  </freeze:frame>
  <freeze:frame property="param" width="95%">
  	<freeze:hidden property="patameter_style" caption="��������" style="width:10%"/>
  </freeze:frame>
   <freeze:grid property="record" rowselect="true" checkbox="false" caption="ά��������Ϣ" width="95%">
      <freeze:button name="record_addRecord" caption="�� ���²���" enablerule="0" align="right" onclick="func_record_addRecord();"/>
      <freeze:cell property="param_value_type" valueset="�ɼ�����_��������" caption="��������" datatype="string"  style="width:10%"/>
      <freeze:cell property="patameter_name" caption="������" datatype="string"  style="width:20%"/>
      <freeze:cell property="patameter_value" caption="����ֵ" datatype="string"  style="width:10%"/>
      <freeze:cell property="style" valueset="�ɼ�����_���ڸ�ʽ" caption="������ʽ" datatype="string"  style="width:10%"/>
      <freeze:cell property="connector" caption="���ӷ�" style="width:10%"/>
      <freeze:cell property="showorder" caption="����˳��" style="width:10%"/>
      <freeze:hidden property="ws_param_value_id" caption="����ֵid" datatype="string"  style=""/>
      <freeze:hidden property="webservice_patameter_id" caption="����ID"  style=""/>
      <freeze:cell property="oper" caption="����" align="center" style="width:10%" />
  </freeze:grid>
  <br />
  <div id='add_table' style='display:none;' >
		<table class='frame-body' align='center' cellpadding='0' cellspacing='0' style='width: 95%; border: 1px solid #ccc;'>
			<!-- <tr height='30' class='grid-headrow'>
				<td>��������</td>
				<td><font color='red'> ������* </font></td>
				<td><font color='red'> ����ֵ* </font></td>
				<td id='connector_title'>���ӷ�</td>
				<td id='date_format_title' style='display:none;'> <font color='red'>���ڸ�ʽ*</td>
				<td><font color='red'>����˳��*</font></td>
			</tr> -->
			<tr height='30' class='oddrow'>
				<td><select id='param_type' onchange='setParamType()' name='param_type' style='width:90%;'>
						<!-- <option value='00'>�ַ���</option>
						<option value='01'>����</option>
						<option value='02'>����</option> -->
				</select></td>
				<td><input type='text' size='50' name='param_name' value='' style='width:90%;' /></td>
				<td><input type='text' size='50' name='param_value' value='' style='width:90%;' /></td>
				<td id='connectorTD'><input type='text' name='connector' value='&' style='width:90%' /></td>
				<td id='date_format_area' style='display:none;'><select style='width:90%;' name='date_type'>
						<!-- <option value='00'>yyyy-mm-dd</option>
						<option value='01'>yyyy/mm/dd</option>
						<option value='02'>yyyymmdd</option> -->
				</select></td>
				<td>
				<input type='text' name='show_order' value='' style='width:90%;' />
				<input type='hidden' name='ws_param_value_id' />
				</td>
			</tr>
			 <tr height='30' class='oddrow'>
				<td align='center' colspan='5'>
					<!--  <div class="btn_save"></div>&nbsp;&nbsp;&nbsp;<a href='javascript:;' onclick='hideTable()'>ȡ��</a>
					-->
					<input type="button" id="saveprambtn" value="����" />&nbsp;&nbsp;&nbsp;<input type="button" id="cancelbtn" value="ȡ��" onclick='hideTable()' /> 
				</td>
			</tr> 
		</table></div>
		<br />
</freeze:form>
	<table align='center' cellpadding='0' cellspacing='0' style='width: 95%;'>
		<tr>
			<td height='30' align='center'><div class="btn_cancel" onclick='fn_cancel()'></div></td>
		</tr>
	</table>
<a id="reload" href="" style="display: none"></a>
</freeze:body>
</freeze:html>
