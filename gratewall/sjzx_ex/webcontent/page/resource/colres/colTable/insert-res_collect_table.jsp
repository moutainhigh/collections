<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@page import="cn.gwssi.common.dao.resource.PublicResource"%>
<%@page import="cn.gwssi.common.dao.resource.code.CodeMap"%>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ page import="cn.gwssi.common.context.Recordset"%>
<%@ page import="cn.gwssi.common.context.TxnContext"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script src="<%=request.getContextPath() %> /script/common/js/validator.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-plugin-data2table/js/jquery.tablet.js"></script>
<link href="<%=request.getContextPath()%>/script/jquery-plugin-Selector/css/jquery.dataSelector.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath() %> /script/common/js/validator.js"></script>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="1000" height="700">
<head>
<title>���Ӳɼ����ݱ���Ϣ</title>
<style type="text/css">
#dataitem_tb table{
	width: 95%;
}
</style>
</head>

<script language="javascript">
<%
TxnContext tbTypeContext = new TxnContext();
CodeMap codeMap = PublicResource.getCodeFactory();
Recordset rs = codeMap.lookup(tbTypeContext, "��Դ����_����������");
String tmp = "";
out.print("var dataitem_types = new Array; ");
for(int ii=0; ii<rs.size(); ii++){
	out.println("dataitem_types.push({\"key\": \""+rs.get(ii).get("codevalue")
		+"\", \"title\": \""+rs.get(ii).get("codename")+"\"});");
}
%>
<%
TxnContext codeTbContext = new TxnContext();
rs = codeMap.lookup(codeTbContext, "��Դ����_��Ӧ�����");
out.print("var code_table_list = new Array;");
for(int ii=0; ii<rs.size(); ii++){
	out.println("code_table_list.push({\"key\": \""+rs.get(ii).get("codevalue")
		+"\", \"title\": \""+rs.get(ii).get("codename")+"\"});");
}
%>
var now_adding = -1;
var is_name_used = 1;
var row_index = -1;
var now_line_no = -1;
var table_id="";
// �� ��
function func_record_saveRecord(){
	var en=getFormFieldValue('record:table_name_en');
	if(!checkEnName(en,'������')){
	return false;
	}
	var page = new pageDefine("/txn20201000.ajax", "������ݱ����Ƿ��Ѿ�ʹ��");	
	page.addParameter("record:table_name_en","primary-key:table_name_en");
	page.addParameter("record:collect_table_id","primary-key:collect_table_id");
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
  			alert("�������Ѵ��ڣ�����������");
  		}else{
  		  var item=getFormFieldValue('record:service_targets_id');
  		  if(!checkItem(item,"100","�����������")){
  		    return false;
  		  }
  		  item=getFormFieldValue('record:table_name_en');
  		  if(!checkItem(item,"100","������")){
  		    return false;
  		  }
  		  item=getFormFieldValue('record:table_name_cn');
  		  if(!checkItem(item,"100","����������")){
  		    return false;
  		  }
  		  item=getFormFieldValue('record:table_type');
  		  if(!checkItem(item,"100","������")){
  		    return false;
  		  }
  		  item=getFormFieldValue('record:table_desc');
  		  if(!checkItemLength(item,"1000","������")){
  		    return false;
  		  }
	      saveRecord( '', '����ɼ����ݱ���Ϣ��' );
  		}
}
function insertTable(errCode,errDesc,xmlResults){
		
		if(errCode != '000000'){
			alert('�������['+errCode+']==>'+errDesc);
			return;
		}else{
		    table_id=_getXmlNodeValues(xmlResults,'record:collect_table_id');
		    setFormFieldValue("record:collect_table_id",table_id);
		    setFormFieldValue("primary-key:collect_table_id",table_id);
		    alert("����ɹ�!");
		}
}


// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '����ɼ����ݱ���Ϣ��' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	var en=getFormFieldValue('record:table_name_en');
	if(!checkEnName(en,'������')){
	return false;
	}
	var page = new pageDefine("/txn20201000.ajax", "������ݱ����Ƿ��Ѿ�ʹ��");	
	page.addParameter("record:table_name_en","primary-key:table_name_en");
	page.callAjaxService('nameCheckCallback2');
}
function nameCheckCallback2(errCode,errDesc,xmlResults){
		is_name_used = 1;
		if(errCode != '000000'){
			alert('�������['+errCode+']==>'+errDesc);
			return;
		}
		is_name_used=_getXmlNodeValues(xmlResults,'record:name_nums');
		if(is_name_used>0){
  			alert("�������Ѵ��ڣ�����������");
  		}else{
  		   saveAndExit( '', '����ɼ����ݱ���Ϣ��' );
  		}
}

// �� ��
function func_record_goBack()
{
	//var page = new pageDefine( "/txn20201001.do", "��ѯ�ɼ����ݱ�");
	//page.updateRecord();
	goBack("/txn20201001.do");	// /txn20201001.do
}
// ���Ӳɼ����������Ϣ 
function func_record_addRecord()
{
    var collect_table_id=getFormFieldValue('record:collect_table_id');
    if(collect_table_id==null||collect_table_id==""){
	    alert("������д�ɼ�����Ϣ!");
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
    	$("#dataitem_tb").tablet("insertRow", data);
	}
	 
}

//������������Ϣ
function saveDataItem() {
	var e = event.srcElement;
	var rowIndex = e.parentNode.parentNode.parentNode.rowIndex;
	now_line_no = rowIndex;
	var dataitem_id = $("#dataitem_tb").find("tr:eq(" + rowIndex + ")")
			.find("td:last").find("input[name='collect_dataitem_id']")
			.val();
	if (typeof dataitem_id == 'undefined') {
		var en = $("#dataitem_tb").find("tr:eq(" + rowIndex + ")").find(
				"td:eq(1)").text();
		if (!checkEnName(en, '����������')) {
			return false;
		}
		var dataitem_type = $("#dataitem_tb").find(
				"tr:eq(" + rowIndex + ")").find("td:eq(3)").find(
				'select option:selected').val();
		var dataitem_long = $("#dataitem_tb").find(
				"tr:eq(" + rowIndex + ")").find("td:eq(4)").text();
		if (!checkItemTypeLength(dataitem_type, dataitem_long)) {//У���������
			return false;
		}
		if (!check_int(dataitem_long)) {//У���������
			return false;
		}
		var page = new pageDefine("/txn20202000.ajax", "������������Ƿ��Ѿ�ʹ��");
		page.addValue(en, "select-key:dataitem_name_en");
		page.addValue(dataitem_id, "select-key:collect_dataitem_id");
		page.addParameter("record:collect_table_id", "select-key:collect_table_id");
		row_index = rowIndex;
		page.callAjaxService('nameCheckCallback3');
	} else {
		var page = new pageDefine("/txn20202002.ajax", "�޸Ĳɼ���������Ϣ");
		page.addValue(dataitem_id, "primary-key:collect_dataitem_id");
		page.addValue(dataitem_id, "record:collect_dataitem_id");
		var collect_table_id = getFormFieldValue('record:collect_table_id');
		//page.addValue(collect_table_id, "record:collect_table_id");
		//page.updateRecord();
		var dataitem_name_en = $("#dataitem_tb").find( "tr:eq(" + rowIndex + ")").find("td:eq(1)").text();
		var dataitem_name_cn = $("#dataitem_tb").find( "tr:eq(" + rowIndex + ")").find("td:eq(2)").text();
		var dataitem_type = $("#dataitem_tb").find( "tr:eq(" + rowIndex + ")").find("td:eq(3)").find( 'select option:selected').val();
		var dataitem_long = $("#dataitem_tb").find( "tr:eq(" + rowIndex + ")").find("td:eq(4)").text();
		var is_key = $("#dataitem_tb").find("tr:eq(" + rowIndex + ")") .find("td:eq(5)").find('select option:selected').val();
		var code_table = $("#dataitem_tb").find("tr:eq(" + rowIndex + ")") .find("td:eq(6)").find('select option:selected').val();
		var last_modify_id = getFormFieldValue('record:last_modify_id');
		var last_modify_time = getFormFieldValue('record:last_modify_time');
		page.addValue(collect_table_id, "record:collect_table_id");
		page.addValue(dataitem_name_en, "record:dataitem_name_en");
		page.addValue(dataitem_name_cn, "record:dataitem_name_cn");
		page.addValue(dataitem_type, "record:dataitem_type");
		page.addValue(dataitem_long, "record:dataitem_long");
		page.addValue('Y', "record:is_markup");
		page.addValue(is_key, "record:is_key");
		page.addValue(code_table, "record:code_table");
		page.addValue("", "record:dataitem_long_desc");
		//page.addValue(last_modify_id, "record:last_modify_id");
		//page.addValue(last_modify_time, "record:last_modify_time");
		page.callAjaxService('saveAndCon');
	}
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
			var collect_table_id = getFormFieldValue('record:collect_table_id');
			var dataitem_name_en = $("#dataitem_tb").find(
					"tr:eq(" + rowIndex + ")").find("td:eq(1)").text();
			var dataitem_name_cn = $("#dataitem_tb").find(
					"tr:eq(" + rowIndex + ")").find("td:eq(2)").text();
			var dataitem_type = $("#dataitem_tb").find(
					"tr:eq(" + rowIndex + ")").find("td:eq(3)").find(
					'select option:selected').val();
			var dataitem_long = $("#dataitem_tb").find(
					"tr:eq(" + rowIndex + ")").find("td:eq(4)").text();
			var is_key = $("#dataitem_tb").find("tr:eq(" + rowIndex + ")")
					.find("td:eq(5)").find('select option:selected').val();
			var code_table = $("#dataitem_tb").find(
					"tr:eq(" + rowIndex + ")").find("td:eq(6)").find(
					'select option:selected').val();
			var last_modify_id = getFormFieldValue('record:last_modify_id');
			var last_modify_time = getFormFieldValue('record:last_modify_time');

			var page = new pageDefine("/txn20202008.ajax", "����ɼ���������Ϣ");
			page.addValue(collect_table_id, "record:collect_table_id");
			page.addValue(dataitem_name_en, "record:dataitem_name_en");
			page.addValue(dataitem_name_cn, "record:dataitem_name_cn");
			page.addValue(dataitem_type, "record:dataitem_type");
			page.addValue(dataitem_long, "record:dataitem_long");
			page.addValue(is_key, "record:is_key");
			page.addValue(code_table, "record:code_table");
			page.addValue("", "record:dataitem_long_desc");
			page.addValue(last_modify_id, "record:last_modify_id");
			page.addValue(last_modify_time, "record:last_modify_time");
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
		var dataitem_id = $("#dataitem_tb").find("tr:eq(" + now_line_no + ")")
				.find("td:last").append("<input name='collect_dataitem_id' type='hidden' value='"+dataItem_id+"' />");
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

function deleteDataItem() {
	var e = event.srcElement;
	var rowIndex = e.parentNode.parentNode.parentNode.rowIndex;
	if (now_adding == 1) {
		$("#dataitem_tb").find("tr:eq(" + rowIndex + ")").remove();
		now_adding = -1;
		$('#btn_add').removeClass('btn_add_disabled').addClass('btn_add');
		$('#btn_add')[0].onclick = function() {
			func_record_addRecord();
		}
	} else if (now_adding == -1) {
		var dataitem_id = $("#dataitem_tb").find("tr:eq(" + rowIndex + ")")
				.find("td:last").find("input[name='collect_dataitem_id']")
				.val();
		if (typeof dataitem_id != 'undefined') {
			var page = new pageDefine("/txn20202005.do", "ɾ���ɼ���������Ϣ");
			page.addValue(dataitem_id, "primary-key:collect_dataitem_id");
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
		$("#dataitem_tb").find("tr:eq(" + rowIndex + ")").remove();
		now_adding = -1;
		$('#btn_add').removeClass('btn_add_disabled').addClass(
				'btn_add');
		$('#btn_add')[0].onclick = function() {
			func_record_addRecord();
		}
	}
}

// �޸Ĳɼ����������Ϣ
function func_record_updateRecord(idx)
{
	var page = new pageDefine( "/txn20202004.do", "�޸Ĳɼ���������Ϣ" );
	page.addValue(idx,"primary-key:collect_dataitem_id");
	var collect_table_id=getFormFieldValue('record:collect_table_id');
	page.addValue(collect_table_id,"primary-key:collect_table_id");
	
	page.updateRecord();
}

// ɾ���ɼ����������Ϣ
function func_record_deleteRecord(idx)
{

	var page = new pageDefine( "/txn20202005.do", "ɾ���ɼ���������Ϣ" );
	if(confirm("�Ƿ�ɾ��ѡ�еļ�¼")){
		page.addValue(idx,"primary-key:collect_dataitem_id");
		page.updateRecord();
	}
}
//�鿴�ɼ����������Ϣ
function func_record_viewRecord(idx)
{
	var page = new pageDefine( "/txn20202006.do", "�鿴�ɼ���������Ϣ", "modal" );
	page.addValue(idx, "primary-key:collect_dataitem_id" );
	var collect_table_id=getFormFieldValue('record:collect_table_id');
	page.addValue(collect_table_id,"primary-key:collect_table_id");
	page.updateRecord();
}

// ���ɱ�
function func_record_createTable()
{
    var collect_table_id=getFormFieldValue('record:collect_table_id');
    //var ids = getFormAllFieldValues("dataItem:dataitem_name_en");
    var ids = new Array;
    $('#dataitem_tb').find("tr:gt(0)").each(function(){
    	ids.push($(this).find("td:eq(1)").text());
    })
    
    if(collect_table_id==null||collect_table_id==""){
	    alert("������д�ɼ�����Ϣ!");
	    clickFlag=0;
    }else if(now_adding==1){
        alert("���ȱ���������!");
    }else if(ids==null||ids.length==0){
        alert("������д��������Ϣ!");
	    clickFlag=0;
    }
    else{
        //var key=getFormAllFieldValues("dataItem:is_key");
        var key = new Array;
        $('#dataitem_tb').find("tr:gt(0)").each(function(){
        	key.push($(this).find("td:eq(5) select option:selected").val());
        })
        var num=0;
	    if(ids!=null){
	    for(i=0;i<ids.length;i++){
	    if(key[i]=='��'){
	       num=num+1;
	      }
	     }
	    }
        if(num>1){
	        alert("ֻ����һ��������������!");
		    clickFlag=0;
		    return false;
        }
    
        var page = new pageDefine( "/txn20201009.ajax", "���ɲɼ����ݱ�!");
        page.addParameter("record:table_name_en","primary-key:table_name_en");
	    page.callAjaxService('creatTableCheck');
	}
}
function creatTableCheck(errCode,errDesc,xmlResults){
		is_name_used = 1;
		if(errCode != '000000'){
			alert('�������['+errCode+']==>'+errDesc);
			return;
		}
		is_name_used=_getXmlNodeValues(xmlResults,'record:name_nums');
		if(is_name_used>0){
		
		if(confirm("�ɼ����Ѵ��ڴ����ݱ����������ݱ����������ݣ��Ƿ�������ɸ����ݱ�?")){
		   var page = new pageDefine( "/txn20201008.ajax", "���ɲɼ����ݱ�!");
  		   page.addParameter("record:collect_table_id","record:collect_table_id");
  		   page.addValue(is_name_used,"record:name_nums");
  		   page.callAjaxService('creatTable');
		}
		
	  		//alert("�ɼ����Ѵ��ڴ����ݱ����������ݱ�����������,���������ɸñ�!");
	  		//return false;
  		}else if(is_name_used==-1){
  		
	  		if(confirm("�ɼ����Ѵ��ڴ����ݱ�����,�����ݱ���û�����ݣ��Ƿ�������ɸ����ݱ�?")){
			   var page = new pageDefine( "/txn20201008.ajax", "���ɲɼ����ݱ�!");
	  		   page.addParameter("record:collect_table_id","record:collect_table_id");
	  		   page.addValue(is_name_used,"record:name_nums");
	  		   page.callAjaxService('creatTable');
			}
			
			//alert("�ɼ����Ѵ��ڴ����ݱ�,���������ɸñ�!");
  			//return false;
  		}else{
  		 if(confirm("�Ƿ��������ݱ�?")){
	  		   var page = new pageDefine( "/txn20201008.ajax", "���ɲɼ����ݱ�!");
	  		   page.addParameter("record:collect_table_id","record:collect_table_id");
	  		   page.addValue(is_name_used,"record:name_nums");
	  		   page.callAjaxService('creatTable');
  		   }
  		}
}
function creatTable(errCode,errDesc,xmlResults){
		is_name_used[0] = 1;
		if(errCode != '000000'){
			alert('�������['+errCode+']==>'+errDesc);
			return;
		}
		else{
		   alert("���ɱ�ɹ�!");
		}
}


// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var collect_table_id = getFormAllFieldValues("record:collect_table_id");
	
	if(collect_table_id!=""){
	  alert("����ɹ�!");
	}
	
	var ids = getFormAllFieldValues("dataItem:collect_dataitem_id");
	for(var i=0; i<ids.length; i++){
	   var htm='<a href="#" title="�鿴" onclick="func_record_viewRecord(\''+ids[i]+'\');"><div class="detail"></div></a>&nbsp;';
	  
	   htm+='<a href="#" title="�޸�" onclick="func_record_updateRecord(\''+ids[i]+'\');"><div class="edit"></div></a>&nbsp;';
	   htm+='<a href="#" title="ɾ��" onclick="func_record_deleteRecord(\''+ids[i]+'\');"><div class="delete"></div></a>';
	   document.getElementsByName("span_dataItem:oper")[i].innerHTML +=htm;
	 }
	var data = new Array;

	data.push({
		"rowIndex" : "���",
		"dataitem_name_en" : "����������",
		"dataitem_name_cn" : "��������",
		"dataitem_type" : "����������",
		"dataitem_long" : "�������",
		"is_key" : "�Ƿ�����",
		"code_table" : "��Ӧ�����",
		"oper" : "����"
	});
	var options = {
		data : data,
		editable : true,
		shownum : 10,
		lineNum : false,
		isAlias : false,
		addDelete : false,
		dataitem_type: dataitem_types,
		code_table: code_table_list
	}
	$("#dataitem_tb").tablet(options);
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="���Ӳɼ����ݱ���Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn20201003" enctype="multipart/form-data">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="collect_table_id" caption="�ɼ����ݱ�ID" style="width:95%"/>
  </freeze:frame>
  <freeze:block property="record" caption="�ɼ����ݱ���Ϣ" width="95%">
  	  <freeze:button name="record_saveRecord" caption="����" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_createTable" caption="���ɱ�" hotkey="SAVE" onclick="func_record_createTable();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="collect_table_id" caption="�ɼ����ݱ�ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:browsebox property="service_targets_id" caption="�����������" show="name" notnull="true" valueset="��Դ����_�����������"   style="width:95%"/>
      <freeze:text property="table_name_en" caption="������" datatype="string" notnull="true"  style="width:95%"/>
      <freeze:text property="table_name_cn" caption="����������" datatype="string"  notnull="true"  style="width:95%"/>
      <freeze:select property="table_type" caption="������" show="name" valueset="��Դ����_������" notnull="true" style="width:95%"/>
      <freeze:textarea property="table_desc" caption="������" colspan="2" rows="2"  style="width:98%"/>
      <freeze:hidden property="table_status" caption="��״̬" datatype="string"  style="width:95%"/>
      <freeze:hidden property="is_markup" caption="��Ч���" datatype="string"  style="width:95%"/>
      <freeze:hidden property="creator_id" caption="������ID" datatype="string"  style="width:95%"/>
      <freeze:hidden property="created_time" caption="����ʱ��" datatype="string"  style="width:95%"/>
      <freeze:hidden property="last_modify_id" caption="����޸���ID" datatype="string"  style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="����޸�ʱ��" datatype="string"  style="width:95%"/>
      <freeze:hidden property="cj_ly" caption="�ɼ���Դ" datatype="string"  style="width:95%"/>
      <freeze:hidden property="if_creat" caption="�ɼ����Ƿ����ɲɼ���" datatype="string"  style="width:95%"/>
  </freeze:block>
  <br>
<%--    <freeze:grid property="dataItem" caption="�ɼ��������б�" keylist="collect_dataitem_id" multiselect="false" checkbox="false" width="95%" navbar="bottom" fixrow="false" >
      <freeze:button name="record_addRecord" caption="���������" txncode="20202003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:hidden property="collect_dataitem_id" caption="�ɼ�������ID"  />
      <freeze:hidden property="collect_table_id" caption="�ɼ����ݱ�ID"  />
      <freeze:cell property="@rowid" caption="���"  style="width:6%" align="center" />
      <freeze:cell property="dataitem_name_en" caption="����������" style="width:12%" />
      <freeze:cell property="dataitem_name_cn" caption="��������" style="width:12%" />
      <freeze:cell property="dataitem_type" caption="����������"  show="name" valueset="��Դ����_����������" style="width:12%" />
      <freeze:cell property="dataitem_long" caption="�������" style="width:10%" />
      <freeze:cell property="is_key" caption="�Ƿ�����" valueset="��Դ����_�Ƿ�����" style="width:10%" />
      <freeze:hidden property="is_code_table" caption="�Ƿ�����" style="width:10%" />
      <freeze:cell property="code_table" caption="��Ӧ�����" valueset="��Դ����_��Ӧ�����" style="width:12%" />
      <freeze:cell property="oper" caption="����" align="center" style="width:12%" />
      <freeze:hidden property="dataitem_long_desc" caption="����������" style="width:20%"  />
      <freeze:hidden property="is_markup" caption="��Ч���" style="width:10%" />
      <freeze:hidden property="creator_id" caption="������ID" style="width:12%" />
      <freeze:hidden property="created_time" caption="����ʱ��" style="width:12%" />
      <freeze:hidden property="last_modify_id" caption="����޸���ID" style="width:12%" />
      <freeze:hidden property="last_modify_time" caption="����޸�ʱ��" style="width:12%" />
  </freeze:grid> --%>
  
  <table align='center' cellpadding=0 cellspacing=0 width="95%" >
  <tr><td><div title="����������" onclick="func_record_addRecord();" id="btn_add" class="btn_add"></div></td></tr>
  </table>
  <div id="dataitem_tb"></div>
</freeze:form>

</freeze:body>
</freeze:html>
