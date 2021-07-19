<%@ page contentType="text/html; charset=GBK" %>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="utils.system"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<script src="<%=request.getContextPath() %> /script/common/js/validator.js"></script>
<%@page import="cn.gwssi.common.dao.resource.PublicResource"%>
<%@page import="cn.gwssi.common.dao.resource.code.CodeMap"%>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ page import="cn.gwssi.common.context.Recordset"%>
<%@ page import="cn.gwssi.common.context.TxnContext"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script src="<%=request.getContextPath() %> /script/common/js/validator.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-plugin-data2table/js/jquery.tablet_param.js"></script>
<link href="<%=request.getContextPath()%>/script/jquery-plugin-Selector/css/jquery.dataSelector.css" rel="stylesheet" type="text/css" />
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�޸�socket������Ϣ</title>
<style type="text/css">
#patameter_tb table{
	width: 95%;
	table-layout: fixed;
}
#patameter_tb table td{
	text-overflow: ellipsis;
}
#patameter_tb table tr.activerow td{white-space: nowrap;}
</style>
</head>
<%
DataBus context = (DataBus) request.getAttribute("freeze-databus");
if(StringUtils.isNotBlank(context.getValue("jsondata"))){
    out.println("<script>var jsondata=eval('('+'"+context.getValue("jsondata")+"'+')');</script>");
}
%>
<script language="javascript">

<%
TxnContext txnContext = new TxnContext();
CodeMap codeMap2 = PublicResource.getCodeFactory();
Recordset rs2 = codeMap2.lookup(txnContext, "�ɼ�����_��������");
out.print("var patameter_types = new Array; ");
for(int ii=0; ii<rs2.size(); ii++){
	out.println("patameter_types.push({\"key\": \""+rs2.get(ii).get("codevalue")
		+"\", \"title\": \""+rs2.get(ii).get("codename")+"\"});");
}
%>

<%
TxnContext tbTypeContext = new TxnContext();
CodeMap codeMap = PublicResource.getCodeFactory();
Recordset rs = codeMap.lookup(tbTypeContext, "�ɼ�����_������ʽ");
out.print("var patameter_styles = new Array; ");
for(int ii=0; ii<rs.size(); ii++){
	out.println("patameter_styles.push({\"key\": \""+rs.get(ii).get("codevalue")
		+"\", \"title\": \""+rs.get(ii).get("codename")+"\"});");
}
%>

var now_adding = -1;
var is_name_used = 1;
var row_index = -1;
var now_line_no = -1;

// �� ��
function func_record_saveRecord()
{
	      var item=getFormFieldValue('record:method_name_en');
  		  if(!checkItem(item,"100","��������")){
  		    return false;
  		  }
	      item=getFormFieldValue('record:method_name_cn');
  		  if(!checkItemLength(item,"100","������������")){
  		    return false;
  		  }
	      item=getFormFieldValue('record:collect_table');
  		  if(!checkItem(item,"100","��Ӧ�ɼ���")){
  		    return false;
  		  }
  		  item=getFormFieldValue('record:collect_mode');
  		  if(!checkItem(item,"100","�ɼ���ʽ")){
  		    return false;
  		  }
  		  item=getFormFieldValue('record:encrypt_mode');
  		  if(!checkItem(item,"100","���ܷ���")){
  		    return false;
  		  }
  		  item=getFormFieldValue('record:method_description');
  		  if(!checkItemLength(item,"2000","��������")){
  		    return false;
  		  }
	      var page = new pageDefine( "/txn30102009.do", "���淽����Ϣ");
	    
	      page.addParameter("record:webservice_task_id","primary-key:webservice_task_id");
	      page.addParameter("record:webservice_task_id","record:webservice_task_id");
	      page.addParameter("record:collect_task_id","record:collect_task_id");
	      page.addParameter("record:service_targets_id","record:service_targets_id");
	      
	      page.addParameter("record:service_no","record:service_no");
	      page.addParameter("record:method_name_en","record:method_name_en");
	      page.addParameter("record:method_name_cn","record:method_name_cn");
	      page.addParameter("record:collect_table","record:collect_table");
	      page.addParameter("record:collect_mode","record:collect_mode");
	      page.addParameter("record:is_encryption","record:is_encryption");
	      page.addParameter("record:encrypt_mode","record:encrypt_mode");
	      page.addParameter("record:method_description","record:method_description");
	      page.addParameter("record:method_status","record:method_status");
	      //page.callAjaxService('updateTask');
	      page.updateRecord();
	       //saveRecord( '', '���淽����Ϣ' );
}
function updateTask(errCode,errDesc,xmlResults){
		
		if(errCode != '000000'){
			alert('�������['+errCode+']==>'+errDesc);
			return;
		}else{
		    alert("����ɹ�!");
		}
}
// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '����webservice�����' );	// /txn30102001.do
}

// �� ��
function func_record_goBack()
{
	//goBack();	// /txn30102001.do
	
	var page = new pageDefine( "/txn30101052.do", "��ѯ�ɼ�����");
	page.addParameter("record:webservice_task_id","primary-key:webservice_task_id");
	page.addParameter("record:collect_task_id","primary-key:collect_task_id");
	page.updateRecord();
}
function getParameter(){
	//�ӵ�ǰҳ��ȡֵ�����key=value��ʽ�Ĵ�
    var parameter = 'input-data:service_targets_id=<freeze:out  value="${record.service_targets_id}"/>';
	return parameter;
}

// �޸ķ�����Ϣ
function func_record_updateRecord(idx)
{
	var e = event.srcElement;
	var rowIndex = e.parentNode.parentNode.parentNode.rowIndex;
	
	
	 
	var page = new pageDefine( "/txn30103008.do", "�޸Ĳ�����Ϣ", "modal" ,1000, 800 );
	
	 var webservice_patameter_id = $("#patameter_tb").find("tr:eq(" + rowIndex + ")")
				.find("td:last").find("input[name='webservice_patameter_id']")
				.val();
	page.addValue(webservice_patameter_id,"primary-key:webservice_patameter_id");
	
	
	var patameter_type=$("#patameter_tb").find("tr:eq(" + rowIndex + ")").find(
				"td:eq(1)").find(
					'select option:selected').val();
	page.addValue(patameter_type,"record:patameter_type");	
				
	var patameter_name=$("#patameter_tb").find("tr:eq(" + rowIndex + ")").find(
				"td:eq(2)").text();			
	page.addValue(patameter_name,"record:patameter_name");	
	
	var patameter_value=$("#patameter_tb").find("tr:eq(" + rowIndex + ")").find(
				"td:eq(3)").text();			
	page.addValue(patameter_value,"record:patameter_value");
	
	var patameter_style=$("#patameter_tb").find("tr:eq(" + rowIndex + ")").find(
				"td:eq(4)").find(
					'select option:selected').val();	
	page.addValue(patameter_style,"record:patameter_style");
	
	page.updateRecord();
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
	var ids = getFormAllFieldValues("dataItem:webservice_patameter_id");
	for(var i=0; i<ids.length; i++){
	  
	   var htm='<a href="#" title="�޸�" onclick="func_record_updateRecord(\''+ids[i]+'\');"><div class="edit"></div></a>&nbsp;';
	   htm+='<a href="#" title="ɾ��" onclick="func_record_deleteRecord(\''+ids[i]+'\');"><div class="delete"></div></a>';
	   document.getElementsByName("span_dataItem:oper")[i].innerHTML +=htm;
	 }
	 
	 var data = new Array;

	data.push({
		"rowIndex" : "���",
		"patameter_type" : "��������",
		"patameter_name" : "������",
		"patameter_value" : "����ֵ",
		"patameter_style" : "������ʽ",
		"oper" : "����"
	});
	var options = {
		data : data,
		editable : 3,
		shownum : 10,
		lineNum : false,
		isAlias : false,
		addDelete : false,
		patameter_style: patameter_styles,
		patameter_type: patameter_types
	}
	$("#patameter_tb").tablet(options);
	if(typeof jsondata != 'undefined'){
			$("#patameter_tb").tablet("initRow", jsondata);
		}
	$('#patameter_tb table tr.tablet_thead td:eq(3)').attr("white-space","nowrap");
	$('#patameter_tb table tr.tablet_thead td:eq(1)').css("width", "10%");
	$('#patameter_tb table tr.tablet_thead td:eq(5)').css("width", "12%");
	$('#patameter_tb table tr.tablet_thead td:eq(4)').css("width", "12%");
	$('#patameter_tb table tr.tablet_thead td:eq(2)').css("width", "12%");
	$('#patameter_tb table tr.tablet_thead td:eq(0)').css("width", "6%");

}
// ���Ӳ�����Ϣ
function func_record_addRecord()
{
    var collect_task_id=getFormFieldValue('record:collect_task_id');
    var webservice_task_id=getFormFieldValue('record:webservice_task_id');
    if(webservice_task_id==null||webservice_task_id==""){
	    alert("������д�ɼ����񷽷���Ϣ!");
	    clickFlag=0;
    }else{
	    clickFlag=0; //���Է��̨�İ�ť����
	    //�Զ��尴ť����״̬
	    now_adding = 1;
	    $('#btn_add').removeClass('btn_add').addClass('btn_add_disabled');
	    $('#btn_add')[0].onclick = function(){
	    	alert("����ɱ�����Ӻ��ٴβ���.");
	    };
	    var data = getFormFieldValue('record:collect_table_id');
	    $("#patameter_tb").tablet("insertRow", data);
	    
	    /**
		 var page = new pageDefine( "/txn30103006.do", "���Ӳ���",'modal');
		 page.addValue(collect_task_id,"primary-key:collect_task_id");
		 page.addValue(webservice_task_id,"primary-key:webservice_task_id");
		 var service_targets_id=getFormFieldValue("record:service_targets_id");
		 page.addValue(service_targets_id,"primary-key:service_targets_id");
		 page.addRecord();
		 */
	}
	 
}
//���������������Ϣ
function saveDataItem() {
	var e = event.srcElement;
	var rowIndex = e.parentNode.parentNode.parentNode.rowIndex;
	now_line_no = rowIndex;
	var webservice_patameter_id = $("#patameter_tb").find("tr:eq(" + rowIndex + ")")
			.find("td:last").find("input[name='webservice_patameter_id']")
			.val();
	
		var en = $("#patameter_tb").find("tr:eq(" + rowIndex + ")").find(
				"td:eq(2)").text();
		if (!checkEnName(en, '��������')) {
			return false;
		}
				
		var patameter_value = $("#patameter_tb").find("tr:eq(" + rowIndex + ")").find(
				"td:eq(3)").text();
				
		 if(!checkItem(patameter_value,"1000","����ֵ")){
  		    return false;
  		}
		
		var page = new pageDefine("/txn30103000.ajax", "���������Ƿ��Ѿ�ʹ��");
		page.addValue(en, "primary-key:patameter_name");
		page.addValue(webservice_patameter_id, "primary-key:webservice_patameter_id");
		page.addParameter("record:webservice_task_id", "primary-key:webservice_task_id");
		row_index = rowIndex;
		page.callAjaxService('nameCheckCallback3');
	
}


//�������������
function nameCheckCallback3(errCode, errDesc, xmlResults) {
	is_name_used = 1;
	if (errCode != '000000') {
		alert('�������[' + errCode + ']==>' + errDesc);
		return;
	}
	is_name_used = _getXmlNodeValues(xmlResults, 'record:name_nums');
	
	if (is_name_used[0] > 0) {
		alert("�������Ѵ��ڣ�����������");
	} else {
		if (row_index != -1) {
			var rowIndex = row_index;
			var webservice_task_id = getFormFieldValue('record:webservice_task_id');
			
			var patameter_type = $("#patameter_tb").find(
					"tr:eq(" + rowIndex + ")").find("td:eq(1)").find(
					'select option:selected').val();
			
			var patameter_name = $("#patameter_tb").find(
					"tr:eq(" + rowIndex + ")").find("td:eq(2)").text();
			var patameter_value = $("#patameter_tb").find(
					"tr:eq(" + rowIndex + ")").find("td:eq(3)").text();
					
			var patameter_style = $("#patameter_tb").find(
					"tr:eq(" + rowIndex + ")").find("td:eq(4)").find(
					'select option:selected').val();
			

			var page = new pageDefine("/txn30103003.ajax", "���������Ϣ");
			var webservice_patameter_id = $("#patameter_tb").find("tr:eq(" + rowIndex + ")")
			.find("td:last").find("input[name='webservice_patameter_id']")
			.val();
			page.addValue(webservice_patameter_id, "record:webservice_patameter_id");
			page.addValue(webservice_task_id, "record:webservice_task_id");
			page.addValue(patameter_type, "record:patameter_type");
			page.addValue(patameter_name, "record:patameter_name");
			page.addValue(patameter_value, "record:patameter_value");
			page.addValue(patameter_style, "record:patameter_style");
			page.callAjaxService('saveAndCon');
		}
	}
}

function saveAndCon(errCode, errDesc, xmlResults) {
	is_name_used[0] = 1;
	if (errCode != '000000') {
		alert('�������[' + errCode + ']==>' + errDesc);
		return;
	} else {
		alert("�����ɹ�!");
		var dataItem_id = _getXmlNodeValues(xmlResults, 'record:collect_dataitem_id');
		dataItem_id = dataItem_id[0];
		var webservice_patameter_id = $("#patameter_tb").find("tr:eq(" + now_line_no + ")")
				.find("td:last").append("<input name='webservice_patameter_id' type='hidden' value='"+webservice_patameter_id+"' />");
		row_index = -1;
		now_adding = -1;
		$('#btn_add').removeClass('btn_add_disabled').addClass('btn_add');
		$('#btn_add')[0].onclick = function() {
			func_record_addRecord();
		}
		now_line_no = -1;
	}
	namechecked = true;
}

function deleteDataItem(rowIndex) {
		var e = event.srcElement;
		rowIndex = e.parentNode.parentNode.parentNode.rowIndex;
		if (now_adding == 1) {
			$("#patameter_tb").find("tr:eq(" + rowIndex + ")").remove();
			now_adding = -1;
			$('#btn_add').removeClass('btn_add_disabled').addClass('btn_add');
			$('#btn_add')[0].onclick = function() {
				func_record_addRecord();
			}
		} else if (now_adding == -1) {
			var webservice_patameter_id = $("#patameter_tb").find("tr:eq(" + rowIndex + ")")
					.find("td:last").find("input[name='webservice_patameter_id']")
					.val();
			if (typeof webservice_patameter_id != 'undefined') {
				var page = new pageDefine("/txn30103005.ajax", "ɾ��������Ϣ");
				var webservice_task_id = getFormFieldValue('record:webservice_task_id');
				page.addValue(webservice_task_id, "primary-key:webservice_task_id");
				page.addValue(webservice_patameter_id, "primary-key:webservice_patameter_id");
				page.callAjaxService("deleteBack");
				row_index = rowIndex;
			}
		}
	}

function deleteBack(errCode, errDesc, xmlResults) {
		if (errCode != '000000') {
			alert('�������[' + errCode + ']==>' + errDesc);
			return;
		} else {
			alert("ɾ���ɹ�!");
			rowIndex = row_index;
			row_index = -1;
			$("#patameter_tb").find("tr:eq(" + rowIndex + ")").remove();
			now_adding = -1;
			$('#btn_add').removeClass('btn_add_disabled').addClass(
					'btn_add');
			$('#btn_add')[0].onclick = function() {
				func_record_addRecord();
			}
		}
	}

// �޸Ĳ�����Ϣ
function func_record_updateRecord2(idx)
{
	var page = new pageDefine( "/txn30103007.do", "�޸Ĳ�����Ϣ", "modal"  );
	page.addValue(idx,"primary-key:webservice_patameter_id");
	var service_targets_id=getFormFieldValue("record:service_targets_id");
	page.addValue(service_targets_id,"primary-key:service_targets_id");
	page.updateRecord();
}
// ɾ��������Ϣ
function func_record_deleteRecord(idx)
{
if(confirm("�Ƿ�ɾ��ѡ�еļ�¼")){
	var page = new pageDefine( "/txn30103005.do", "ɾ��������Ϣ" );
	page.addValue(idx,"primary-key:webservice_patameter_id");
	//page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" ); 
	page.updateRecord();
	}
	
}
//�鿴�ɼ����������Ϣ
function func_record_viewRecord(idx)
{
	var page = new pageDefine( "/txn30103007.do", "�鿴������Ϣ", "modal" );
	page.addValue(idx,"primary-key:webservice_patameter_id");
	
	page.updateRecord();
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸ķ�����Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn30102008">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="webservice_task_id" caption="WEBSERVICE����ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸ķ�����Ϣ" width="95%">
      <freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="webservice_task_id" caption="WEBSERVICE����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="collect_task_id" caption="�ɼ�����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="service_targets_id" caption="����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:cell property="service_no" caption="����ID��" datatype="string"  style="width:95%"/>
      <freeze:text property="method_name_en" caption="�������ƣ�" datatype="string"  notnull="true" style="width:95%"/>
      <freeze:text property="method_name_cn" caption="������������" datatype="string"  style="width:95%"/>
      <freeze:browsebox property="collect_table" caption="��Ӧ�ɼ���" show="name" notnull="true" valueset="��Դ����_��������Ӧ�ɼ���" parameter="getParameter();"  style="width:95%"/>
      <freeze:select property="collect_mode" caption="�ɼ���ʽ" show="name" notnull="true" valueset="��Դ����_�ɼ���ʽ" style="width:95%"/>
      <freeze:hidden property="is_encryption" caption="�Ƿ����"   datatype="string"  style="width:95%"/>
      <freeze:select property="encrypt_mode" caption="���ܷ���"  notnull="true" show="name" valueset="��Դ����_���ܷ���" value="01" style="width:95%"/>
      <freeze:textarea property="method_description" caption="��������" colspan="2" rows="2"  style="width:98%"/>
      <freeze:hidden property="method_status" caption="����״̬" datatype="string"  style="width:95%"/>
  </freeze:block>
<br>
  <%--   <freeze:grid property="dataItem" caption="�����б�" keylist="webservice_patameter_id" multiselect="false" checkbox="false" width="95%" navbar="bottom" fixrow="false" >
     <freeze:button name="record_addRecord" caption="��Ӳ���" txncode="20202003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:hidden property="webservice_patameter_id" caption="����ID"  />
      <freeze:hidden property="webservice_task_id" caption="����ID"  />
      <freeze:cell property="@rowid" caption="���"  style="width:5%" align="center" />
      <freeze:cell property="patameter_type" caption="��������" style="width:20%" />
      <freeze:cell property="patameter_name" caption="������" style="width:25%" />
      <freeze:cell property="patameter_value" caption="����ֵ"   style="width:25%" />
      <freeze:cell property="oper" caption="����" align="center" style="width:25%" />
  </freeze:grid>  --%>
    <table align='center' cellpadding=0 cellspacing=0 width="95%" >
  <tr><td></td></tr>
  </table>
  <div id="patameter_tb"></div>
</freeze:form>
</freeze:body>
</freeze:html>
