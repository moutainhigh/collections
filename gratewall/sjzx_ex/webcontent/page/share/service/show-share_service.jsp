<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<%@ page import = "cn.gwssi.common.context.Recordset"%>
<%@ page import = "com.gwssi.common.constant.FileConstant"%>
<%-- template single/single-table-update.jsp --%>
<freeze:html>
<head>
<title>�鿴������Ϣ</title>
	<script type="text/javascript" src='<%=request.getContextPath()%>/script/uploadfile.js'></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-plugin-tab/jquery.tabs.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-plugin-data2table/js/jquery.tablet.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-plugin-Selector/js/jquery.dataSelector.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery-ui.custom.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/service/share_service.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery.ui.timepicker.js"></script>
	<link href="<%=request.getContextPath()%>/script/lib/jquery.ui.timepicker.css" rel="stylesheet" type="text/css">
	<link href="<%=request.getContextPath()%>/script/css/cupertino/jquery.ui.all.css" rel="stylesheet" type="text/css">
	<link href="<%=request.getContextPath()%>/script/jquery-plugin-Selector/css/jquery.dataSelector.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/script/jquery-plugin-tab/css/jquery.tabs.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		.cssSelect1{
			width:120px !important;
			height: auto;
		}
		.cssSelect{
			width:120px !important;
			height: auto;
		}
	</style>
</head>

<script type="text/javascript">
var svrId = "";

// �� ��
function func_record_saveAndExit()
{
	var sava_data = getPostData();
	if(sava_data){
    	setFormFieldValue( "record:jsoncolumns" ,sava_data[0]);
    	setFormFieldValue( "record:sql" ,sava_data[1]);
		setFormFieldValue( "record:limit_data" ,sava_data[2]);
	saveAndExit( '', '�޸Ĺ������ķ����' );	// /txn40200001.do
	}
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn40200001.do
}

function func_record_export()
{

	var svrId = getFormFieldValue("record:service_id");
	var page = new pageDefine( "/txn40200016.do", "����������Ϣ");
	page.addValue( svrId, "primary-key:service_id" );
	
	//page.addParameter( "record:service_name", "record:service_name" );
	page.addParameter( "record:interface_id", "record:interface_id" );
	page.addParameter( "record:service_targets_id", "record:service_targets_id" );
	page.addParameter( "record:service_state", "record:service_state" );
	page.addParameter( "record:service_type", "record:service_type" );
	page.goPage();
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	_browse.enableCopy = true;
	var jsoncolumn = getFormFieldValue( "record:jsoncolumns" );
	svrId = getFormFieldValue("record:service_id");
    if(jsoncolumn==''){
    	alert("���ݻ�ȡʧ�ܣ�����÷�������ļ��Ƿ���ڣ�");
    }else{
   //��ʼ�������޶��������
    var jsondata = eval('(' + jsoncolumn + ')'); 
   
   //��ѡ�ֶ�
   var column_data=jsondata.columns;
   var column_html="";
   var tbTitleTmp = new Array;
   for(var i=0;i<column_data.length;i++){
     var column=column_data[i];
     if(tbTitleTmp.join(',').indexOf(column.table.name_en)==-1){
      	tbTitleTmp.push(column.table.name_en);
      	tbTitleTmp.push(column.table.name_cn);
     }
     //column_html+="["+column.table.name_cn+"("+column.table.name_en+")] �� "+column.column.name_cn+"("+column.column.name_en+")<br/>";
   }
   for(var ii=0; ii<tbTitleTmp.length; ii=ii+2){
	   column_html += "<span style='color:red;'>[ "+tbTitleTmp[ii+1]+" ( "+tbTitleTmp[ii]+" ) ]</span><br />&nbsp;&nbsp;";
	   for(var i=0;i<column_data.length;i++){
		     var column=column_data[i];
		     if(tbTitleTmp[ii] == column.table.name_en){
				column_html+= column.column.name_cn+"("+column.column.name_en+"), &nbsp;";
		     }
		}
	   column_html = column_html.replace(/,\s*&nbsp;$/ig, " ");
	   column_html += "<br />";
   }
   document.getElementById('span_record:column_name_cn').innerHTML = column_html;
    
   //�����ѯ����
   
   var condition=jsondata.conditions;
   var cond_html="";
   for(var i=0;i<condition.length;i++){
     var cond=condition[i];
     cond_html+=cond.logic.name_cn+cond.leftParen+"["+cond.table.name_cn+"] �� "+cond.column.name_cn+" <span style='color:red'> "+cond.paren.name_cn+" </span>";
     cond_html+= cond.param_value.name_cn+cond.rightParen+"<br/>";
   }
   document.getElementById('span_record:service_condition').innerHTML=cond_html;
    
   //�û��������
   
   var param=jsondata.params;
   var param_html="";
   for(var i=0;i<param.length;i++){
     var parm=param[i];
     param_html+=parm.logic.name_cn+parm.leftParen+" ["+parm.table.name_cn+"] �� "+parm.column.name_cn+" <span style='color:red'> "+parm.paren.name_cn+" </span>";
     param_html+= parm.param_value.name_cn+parm.rightParen+"<br/>";
   }
   document.getElementById('span_record:service_param').innerHTML=param_html; 
    
    initWeek();
	initTime();
	initNumber();
	initNumPer();
	initTotal();
    
    var limit_data = getFormFieldValue("record:limit_data");
	limit_data = eval("(" + limit_data + ")");
	var data_array = new Array;
	data_array.push({
						"week": "����",
						"datesStr": "��ʼʱ��-����ʱ��",
				//		"end_time": "����ʱ��",
						"times_day": "���ʴ���",
						"count_dat": "ÿ�η�������",
						"total_count_day": "ÿ���������"
					});
	limit_data = limit_data.data;
	for(var ii=0; ii< limit_data.length; ii++){
		data_array.push(limit_data[ii]);
	}
	
    $(function() {
	//׼��tabҳ
	    		var opts = {
	    		    addDelete : false,
					data: data_array,
					shownum : 8
				}
				$(tab4_limit_div).tablet(opts);
});

setTimeout(function(){
	getColumnsByTable($(tab1_table_all_div).find("option:first").val());
	$(tab1_table_all_div).find("option:first").attr("selected", true);
},1000);
document.getElementById("span_record:sql").title = "�������SQL���";
document.getElementById("span_record:sql").onclick = function(){
	var dcontent = document.getElementById("span_record:sql").innerHTML;
	dcontent = dcontent.replace("&lt;", "<").replace("&gt;", ">");
	window.clipboardData.setData("Text", dcontent);
	alert("SQL����Ѹ��Ƶ�������");
}
    }
    var is_month_data = getFormFieldValue("record:is_month_data");
	var visit_period = getFormFieldValue("record:visit_period");
	if(is_month_data=='N'){
		document.getElementById("span_record:is_month_data").innerHTML="������";
	}else{
		document.getElementById("span_record:is_month_data").innerHTML="����";
	}
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�鿴�������Ϣ"/>
<freeze:errors/>
<freeze:form action="/txn40200010" enctype="multipart/form-data">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="service_id" caption="����ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�鿴�������Ϣ" width="95%">
      
      <freeze:hidden property="service_id" caption="����ID" datatype="string"  style="width:95%"/>
      <freeze:hidden property="delIDs" caption="multi file id" style="width:90%" />
      <freeze:hidden property="delNAMEs" caption="multi file name" style="width:90%" />
      <freeze:hidden property="column_no" caption="������ID"  style="width:98%"/>
      <freeze:hidden property="column_name_cn" caption="��������������"  style="width:98%"/>
      <freeze:hidden property="column_alias" caption="���������"  style="width:98%"/>
      <freeze:hidden property="sort_column" caption="�����ֶ�"  style="width:95%"/>
      <freeze:hidden property="sql_one" caption="SQL1" style="width:98%"/>
      <freeze:hidden property="sql_two" caption="SQL2"  style="width:98%"/>
      <freeze:hidden property="is_markup" caption="��Ч���" value="Y" style="width:95%"/>
      <freeze:hidden property="creator_id" caption="������ID"  style="width:95%"/>
      <freeze:hidden property="created_time" caption="����ʱ��"  style="width:95%"/>
      <freeze:hidden property="last_modify_id" caption="����޸���ID"  style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="����޸�ʱ��"  style="width:95%"/>
      <freeze:hidden property="jsoncolumns"  datatype="string"  style="width:95%"/>
      <freeze:hidden property="limit_data" datatype="string" style="width:95%" />
      <freeze:hidden property="is_month_data" datatype="string" />
      <freeze:hidden property="visit_period" datatype="string" />
      
      <freeze:cell property="service_name" caption="��������" datatype="string"  style="width:95%"/>
      <freeze:cell property="interface_id" caption="�����ӿ�" valueset="�������_�ӿ�����"  show="name" style="width:95%"/>
      <freeze:cell property="service_targets_id" caption="�������" valueset="��Դ����_��������������"  show="name" style="width:95%"/>
      <freeze:cell property="service_state" caption="����״̬" valueset="��Դ����_�鵵����״̬" style="width:95%"/>
      <freeze:cell property="service_type" caption="��������" valueset="��Դ����_����Դ����" style="width:95%"/>
      <freeze:hidden property="service_no" caption="������" datatype="string" style="width:95%"/>
      <freeze:cell property="is_month_data" caption="���Ʒ��ʵ�������" datatype="string" style="width:95%" />
      <freeze:cell property="visit_period" caption="����ʱ����(��)" datatype="string" style="width:95%" />
      <freeze:cell property="old_service_no" caption="������" align="left" style="width:95%" />
      <freeze:cell hint="�������SQL���" property="sql" caption="SQL" colspan="2" style="width:98%"/>
      <freeze:cell disabled="true" property="service_description" caption="����˵��"  style="width:98%"/>
      <freeze:cell disabled="true" property="regist_description" caption="����˵��"  style="width:98%"/>
 <%
    DataBus context = (DataBus) request.getAttribute("freeze-databus");
 	Recordset fileList=null;
 	DataBus record = null;
    try{
    record	 = context.getRecord("record");
    String interfaceid= record.getValue("interface_id"); 
    	%>
    <input value="<%=interfaceid%>" type="hidden" name="record:interface_id2"/>

		<% 
    fileList = context.getRecordset("fjdb");
    if(fileList!=null && fileList.size()>0){
        for(int i=0;i<fileList.size();i++){
               DataBus file = fileList.get(i);
               String file_id = file.getValue(FileConstant.file_id);
               String file_name = file.getValue(FileConstant.file_name);
%>
<tr>
<td height="32" align="right">����������&nbsp;</td>
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
   <br/>
	<freeze:block property="record" caption="�鿴����������Ϣ" width="95%">
	  <freeze:cell property="column_name_cn" caption="������ѡ�ֶ�" datatype="string"  style="width:95%" colspan="2"/>
	  <freeze:cell property="service_condition" caption="�����ѯ����" datatype="string"  style="width:95%" colspan="2"/>
	  <freeze:cell property="service_param" caption="�û��������" datatype="string"  style="width:95%" colspan="2"/>
	  
	   <freeze:cell property="crename" caption="������" datatype="string"  style="width:95%"/>
  <freeze:cell property="cretime" caption="����ʱ��" datatype="string"  style="width:95%"/>
  <freeze:cell property="modname" caption="����޸���" datatype="string"  style="width:95%"/>
  <freeze:cell property="modtime" caption="����޸�ʱ��" datatype="string"  style="width:95%"/>
	</freeze:block>

<br />
<table border=0 cellpadding=0 cellspacing=0 width="95%" align="center" style="border-collapse:collapse;">
	<tr>
		<td >
			<div style="width:100%;">
				<dl class="tabs" id="tabs">
      	<dt>
      		<span>������������</span>
      	</dt>
      	
      	<!-- ���ĸ���ǩҳ��ʼ -->
      	<dd><div>
      		 <table class="dd_table" border="0" cellpadding="0" cellspacing="0" width="100%" align="center">
			<tr>
				<td><div id="tab4_limit_div"></div></td>
			</tr>
			</table>
			</div>
      	</dd>
      	<!-- ���ĸ���ǩҳ���� -->
    </dl>
			</div>
		</td>
	</tr>
	<tr>
		<td align="center" height="50" valign="bottom"><div class="btn_cancel"  onclick="func_record_goBack()"></div><input type="button" name="record_export_doc" value="     " class="btn_import" onclick="func_record_export();" /></td>
	
	</tr>
</table>
<!-- <p align="center" class="print-menu">
    	<table cellspacing='0' cellpadding='0' class='button_table'>
    	<tr><td class='btn_left'></td>
    	<td><input type="button" name="record_goBackNoUpdate" value="�� ��" class="menu" onclick="func_record_goBack()" />
    	</td><td class='btn_right'></td></tr>
    	</table>
    </p> -->
</freeze:form>
</freeze:body>
</freeze:html>
