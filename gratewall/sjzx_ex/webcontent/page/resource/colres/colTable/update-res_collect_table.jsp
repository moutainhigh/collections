<%@page import="cn.gwssi.common.dao.resource.PublicResource"%>
<%@page import="cn.gwssi.common.dao.resource.code.CodeMap"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ page import="cn.gwssi.common.context.Recordset"%>
<%@ page import="cn.gwssi.common.context.TxnContext"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-plugin-data2table/js/jquery.tablet.js"></script>
<link href="<%=request.getContextPath()%>/script/jquery-plugin-Selector/css/jquery.dataSelector.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath() %> /script/common/js/validator4m.js"></script>
<freeze:html width="1000" height="700">
<head>
<title>�޸Ĳɼ����ݱ���Ϣ</title>
<style type="text/css">
#dataitem_tb table{ 
	width: 98%;
}
</style>
</head>
<script type="text/javascript">
<%
TxnContext tbTypeContext = new TxnContext();
CodeMap codeMap = PublicResource.getCodeFactory();
Recordset rs = codeMap.lookup(tbTypeContext, "��Դ����_����������");
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
<%
DataBus context = (DataBus) request.getAttribute("freeze-databus");
if(StringUtils.isNotBlank(context.getValue("jsondata"))){
    out.println("var jsondata="+context.getValue("jsondata")+";");
}else if(StringUtils.isNotBlank(context.getRecord("record").getValue("jsondata"))){
	out.println("var jsondata="+context.getRecord("record").getValue("jsondata")+";");
	out.println("var jsondata=jsondata.data ;");
}
%>
var now_adding = -1;
var is_name_used = 1;
var row_index = -1;
var now_line_no = -1;
var name_ens = new Array;
var all_save = false;
//ȫҳ�汣��
 function func_record_saveRecord_all(){
	name_ens.length = 0; //�����������������
	//���¸�ֵ�������������飬��Ҫ����������֤
	
	 if($("#dataitem_tb").find("tr:eq(1)").find(
		"td:eq(1)").text()==""){
		 alert("�������������Ȼ���ٱ���!");
		 return false;
	 }
	
	
	
	 $('#dataitem_tb tr:gt(0)').each(function(index){
		 name_ens.push($(this).find("td:eq(1)").text());
	 });
	var flag = false;
	$('#dataitem_tb tr:gt(0)').each(function(index){
		//����У��
		flag = checkItem(++index);
		if(flag == false){
			return false;
		}
	});
	
	
	//ͨ��У��
	if(flag){
		//var dataStr = $('#dataitem_tb').tablet('getAllData');
		//dataStr = eval('('+dataStr+')');
		//dataStr = dataStr.data;
		var dataStr = "{'data': [";
		$('#dataitem_tb tr:gt(0)').each(function(index){
			var dataitem_name_en = $(this).find("td:eq(1)").text();
			var dataitem_name_cn = $(this).find("td:eq(2)").text();
			var dataitem_type = $(this).find("td:eq(3)").find(
					'select option:selected').val();
			var dataitem_long = $(this).find("td:eq(4)").text();
			var is_key = $(this).find("td:eq(5)").find('select option:selected').val();
			var code_table = $(this).find("td:eq(6)").find(
					'select option:selected').val();
			dataStr += "{'dataitem_name_en': '"+dataitem_name_en+"', 'dataitem_name_cn': '"+dataitem_name_cn+
				"', 'dataitem_type': '"+dataitem_type+"', 'dataitem_long': '"+dataitem_long+
				"', 'is_key': '"+is_key+"', 'code_table': '"+code_table+"' },";
		});
		dataStr = dataStr.replace(/,$/, "");
		dataStr += "]}";
		//alert(dataStr);
		var page = new pageDefine("/txn20201014.do", "����ɼ���Դ�����ֶ�");	
		//page.addValue(dataStr, "jsondata");
		//page.addParameter("record:collect_table_id","primary-key:collect_table_id");
		//page.updateRecord();
		setFormFieldValue('record:jsondata', dataStr);
	    saveRecord( '', '����ɼ����ݱ�������' );
	}
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
	    if(key[i]=='1'){
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
		   var page = new pageDefine( "/txn20201016.ajax", "���ɲɼ����ݱ�!");
  		   page.addParameter("record:collect_table_id","record:collect_table_id");
  		   page.addValue(is_name_used,"record:name_nums");
  		   page.callAjaxService('creatTable');
		}
		
	  		//alert("�ɼ����Ѵ��ڴ����ݱ����������ݱ�����������,���������ɸñ�!");
	  		//return false;
  			
  		}else if(is_name_used==-1){
	  		
	  		if(confirm("�ɼ����Ѵ��ڴ����ݱ�����,�����ݱ���û�����ݣ��Ƿ�������ɸ����ݱ�?")){
			   var page = new pageDefine( "/txn20201016.ajax", "���ɲɼ����ݱ�!");
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
		is_name_used = 1;
		if(errCode != '000000'){
			alert('�������['+errCode+']==>'+errDesc);
			return;
		}
		else{
		   alert("���ɱ�ɹ�!");
		   goBackWithUpdate();
		}
}
 
 
 function func_record_goBack()
{

	//var page = new pageDefine( "/txn20201001.do", "��ѯ�ɼ����ݱ�");
	//page.updateRecord();
	
	
	//goBackWithUpdate();
	window.close();
}
// ���Ӳɼ����������Ϣ
function func_record_addRecord()
{
	clickFlag=0; //���Է��̨�İ�ť����
	//�Զ��尴ť����״̬
	now_adding = 1;
	/* $('#btn_add').removeClass('btn_add').addClass('btn_add_disabled');
	$('#btn_add')[0].onclick = function(){
		alert("����ɱ�����Ӻ��ٴβ���.");
	}; */
	var data = getFormFieldValue('record:collect_table_id');
	$("#dataitem_tb").tablet("insertRow", data);
}

	//������������Ϣ
	function checkItem(rowIndex) {
		//var e = event.srcElement;
		//rowIndex = e.parentNode.parentNode.parentNode.rowIndex;
		var en = $("#dataitem_tb").find("tr:eq(" + rowIndex + ")").find(
				"td:eq(1)").text();
		if (en == "UPDATE_DATE") {
			alert("��"+rowIndex+"����, UPDATE_DATEΪϵͳ�����ֶν�ֹʹ�ã�");
			return false;
		}
		var en = $("#dataitem_tb").find("tr:eq(" + rowIndex + ")").find(
				"td:eq(1)").text();
		if (!checkEnName(en, '����������', rowIndex)) {
			return false;
		}
		var tmpAry = new Array;
		tmpAry = tmpAry.concat(name_ens);
		tmpAry[rowIndex-1]="@";
		/* if(tmpAry.join(',').indexOf(en) > -1){
			alert("��"+rowIndex+"������������'"+en+"'�ظ�!");
			return false;
		} */
		for(var ii=0; ii<tmpAry.length; ii++){
			if(en == tmpAry[ii]){
				alert("��"+rowIndex+"�к͵�"+(ii+1)+"������������'"+en+"'�ظ�!");
				return false;
			}
		}
		var dataitem_type = $("#dataitem_tb").find("tr:eq(" + rowIndex + ")")
				.find("td:eq(3)").find('select option:selected').val();
		var dataitem_long = $("#dataitem_tb").find("tr:eq(" + rowIndex + ")")
				.find("td:eq(4)").text();
		if (!checkItemTypeLength(dataitem_type, dataitem_long, rowIndex)) {//У���������
			return false;
		}
		if (!check_int(dataitem_long, rowIndex)) {//У���������
			return false;
		}
		return true;
	}

	function saveAndCon(errCode, errDesc, xmlResults) {
		is_name_used[0] = 1;
		if (errCode != '000000') {
			alert('�������[' + errCode + ']==>' + errDesc);
			return;
		} else {
			if (!all_save) {
				alert("�����ɹ�!");
			}
			var dataItem_id = _getXmlNodeValues(xmlResults,
					'record:collect_dataitem_id');
			dataItem_id = dataItem_id[0];
			$("#dataitem_tb")
					.find("tr:eq(" + now_line_no + ")")
					.find("td:last")
					.append(
							"<input name='collect_dataitem_id' type='hidden' value='"+dataItem_id+"' />");
			row_index = -1;
			now_adding = -1;
			$('#btn_add').removeClass('btn_add_disabled').addClass('btn_add');
			$('#btn_add')[0].onclick = function() {
				func_record_addRecord();
			}
			now_line_no = -1;
			//window.location.reload();
		}
		namechecked = true;
	}
	function deleteDataItem() {
		var e = event.srcElement;
		rowIndex = e.parentNode.parentNode.parentNode.rowIndex;
		$("#dataitem_tb").find("tr:eq(" + rowIndex + ")").remove();
	}

	// ����������ӣ�ҳ�������ɺ���û���ʼ������
	function __userInitPage() {
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
			shownum : 10,
			addDelete : false,
			dataitem_type : dataitem_types,
			code_table : code_table_list
		}
		$("#dataitem_tb").tablet(options);
		if(typeof jsondata != 'undefined'){
			$("#dataitem_tb").tablet("initRow", jsondata);
		}
		
		var ids =document.getElementsByName("span_dataItem:code_table");
		var value=getFormAllFieldValues("dataItem:code_table");
		
		for(var i=0; i<ids.length; i++){
			var val=value[i];
			if(val==null||val==""||val=="��"){
			}else{
			  ids[i].innerHTML='<a href="javascript:func_record_querycode(\''+value[i]+'\');" title="" >'+val+'</a>';
			}
	    }
		
		
	}
	function func_record_querycode(val){
	    var url="txn20301026.do?select-key:code_table="+val;
	    var page = new pageDefine( url, "�鿴���뼯��Ϣ", "���뼯��ѯ","modal","600","400" );
		page.addRecord();
	}

	_browse.execute('__userInitPage()');
</script>
<freeze:body>
<freeze:title caption="�޸Ĳɼ����ݱ���Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn20201002.do">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="collect_table_id" caption="�ɼ����ݱ�ID" style="width:95%"/>
  </freeze:frame>

<freeze:block property="record" caption="�ɼ����ݱ���Ϣ" width="95%">
      <freeze:hidden property="collect_table_id" caption="�ɼ����ݱ�ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:browsebox property="service_targets_id" caption="�����������" show="name" notnull="true" valueset="��Դ����_�����������"   style="width:95%"/>
      <freeze:text property="table_name_en" caption="������" datatype="string" notnull="true" readonly="true" style="width:95%"/>
      <freeze:text property="table_name_cn" caption="����������" datatype="string" notnull="true"  style="width:95%"/>
      <freeze:select property="table_type" caption="������" show="name" valueset="��Դ����_������" notnull="true" style="width:95%"/>
      <freeze:textarea property="table_desc" caption="������" colspan="2" rows="2"  style="width:98%"/>
      <freeze:cell property="crename" caption="�����ˣ�" datatype="string"  style="width:95%"/>
      <freeze:cell property="cretime" caption="����ʱ�䣺" datatype="string"  style="width:95%"/>
      <freeze:cell property="modname" caption="����޸��ˣ�" datatype="string"  style="width:95%"/>
      <freeze:cell property="modtime" caption="����޸�ʱ�䣺" datatype="string"  style="width:95%"/>
      
      
      <freeze:hidden property="table_status" caption="��״̬" datatype="string"  style="width:95%"/>
      <freeze:hidden property="is_markup" caption="��Ч���" datatype="string"  style="width:95%"/>
      <freeze:hidden property="creator_id" caption="������ID" datatype="string"  style="width:95%"/>
      <freeze:hidden property="created_time" caption="����ʱ��" datatype="string"  style="width:95%"/>
      <freeze:hidden property="last_modify_id" caption="����޸���ID" datatype="string"  style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="����޸�ʱ��" datatype="string"  style="width:95%"/>
      <freeze:hidden property="cj_ly" caption="�ɼ���Դ" datatype="string"  style="width:95%"/>
      <freeze:hidden property="if_creat" caption="�ɼ����Ƿ����ɲɼ���" datatype="string"  style="width:95%"/>
      <freeze:hidden property="jsondata" caption="�ֶ�json" style="width:95%"/>
  </freeze:block>
   <br/>
 
  <freeze:grid property="dataItem" caption="�ɼ��������б�" keylist="collect_dataitem_id" width="95%"  multiselect="false" checkbox="false" fixrow="false">
      
      <freeze:hidden property="collect_dataitem_id" caption="�ɼ�������ID" style="width:12%" />
      <freeze:hidden property="collect_table_id" caption="�ɼ����ݱ�ID" style="width:12%" />
       <freeze:cell property="index" caption="���"  style="width:5%" align="center" />
      <freeze:cell property="dataitem_name_en" caption="����������" style="width:15%" />
      <freeze:cell property="dataitem_name_cn" caption="��������������" style="width:15%" />
      <freeze:cell property="dataitem_type" caption="����������" show="name" valueset="��Դ����_����������" style="width:10%" />
      <freeze:cell property="dataitem_long" caption="�������" style="width:10%" />
      <freeze:cell property="is_key" caption="�Ƿ�����"  valueset="�Ƿ�[1��0��]" style="width:10%" />
      <freeze:cell property="is_code_table" caption="�Ƿ�����" valueset="�Ƿ�[1��0��]" style="width:10%" />
      <freeze:cell property="code_table" caption="��Ӧ�����" valueset="��Դ����_��Ӧ�����" style="width:10%" />
      <freeze:cell property="dataitem_long_desc" caption="����������" style="width:15%"  />
      <freeze:hidden property="is_markup" caption="��Ч���" style="width:10%" />
      <freeze:hidden property="creator_id" caption="������ID" style="width:12%" />
      <freeze:hidden property="created_time" caption="����ʱ��" style="width:12%" />
      <freeze:hidden property="last_modify_id" caption="����޸���ID" style="width:12%" />
      <freeze:hidden property="last_modify_time" caption="����޸�ʱ��" style="width:12%" />
  </freeze:grid>
  
  
  
  <br>
  <table align='center' cellpadding=0 cellspacing=0 width="95%" >
  <tr><td><div title="����������" onclick="func_record_addRecord();" id="btn_add" class="btn_add"></div></td></tr>
  </table>
  <div id="dataitem_tb"></div>
    <table align='center' cellpadding=0 cellspacing=0 width="95%" >

  <tr><td>
  
  
  
  <td align="center" height="50" valign="bottom">
    <div class="btn_save"  onclick="func_record_saveRecord_all();"></div>
&nbsp;&nbsp;&nbsp;&nbsp;
  <div class="btn_gen"  onclick="func_record_createTable();"></div>
&nbsp;&nbsp;&nbsp;&nbsp;
<div class="btn_cancel"  onclick="func_record_goBack();"></div>
  </td></tr>	
	
  </table>
</freeze:form>
</freeze:body>
</freeze:html>
