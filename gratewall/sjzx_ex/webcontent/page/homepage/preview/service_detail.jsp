<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<%@ page import = "cn.gwssi.common.context.Recordset, java.net.URLDecoder"%>
<%@ page import = "com.gwssi.common.constant.FileConstant"%>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="1000" height="700">
<head>


<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<% 
	//String svrId = request.getParameter("select-key:service_targets_id");
	//String svrName = URLDecoder.decode(request.getParameter("select-key:service_targets_name"));
	//String svrName = request.getParameter("select-key:service_targets_name");
%>
<title>������Ϣ</title>
	<script type='text/javascript' src='<%=request.getContextPath()%>/script/uploadfile.js'></script>
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
		  .frame-body{margin-top: 3px !important;}
		.divHeightControll{/* line-height:120%; */}
		.table_name, .line{width:90px; text-align:right; display:inline;white-spcace:nowrap; height:20px; line-height: 1.5;}
		.line{margin: 0 10px; width:5px;}
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
   column_html += "<table class='haa'  width='100%'>";
   var cols = new Array;
   for(var ii=0; ii<tbTitleTmp.length; ii=ii+2){
	   var tmpAry = new Array;
	   for(var i=0;i<column_data.length;i++){
		     var column=column_data[i];
		     if(tbTitleTmp[ii] == column.table.name_en){
				tmpAry.push(column);
		     }
		}
	   cols.push(tmpAry);
   }
   
   for(var ii=0; ii<cols.length; ii++){
	   column_html += "<tr class='rows'><td width='15%' valign='middle' style='white-space:nowrap; text-align:right;'>"+
	   "<span style='color:red;'>[ "+tbTitleTmp[2*ii+1]+" ]:</span></td><td valign='middle'><div class='divHeightControll'>";
	   column_html += "<table style='table-layout:fixed;' width='100%' cellspacing='0' cellpadding='0'><tr>";
	   for(var i=0;i<cols[ii].length;i++){
		     var column=cols[ii];
		     column_html += '<td width="'+(100/6)+'%" align="right"><div style="display:inline; white-space:nowrap;"><div class="table_name">'
					+column[i].column.name_cn+"</div><div class='line'>|</div></div></td>";
		     
		     if( (i+1) % 6 === 0){
			 	 column_html += "</tr><tr>"
			 }
		}
	   if( cols[ii].length % 6 != 0){
		  // for(var i=0; i<6-cols[ii].length%6; i++){
			   column_html += "<td width='"+(100/6*(6 - cols[ii].length % 6))+"%' colspan='"+(6 - cols[ii].length % 6)+"'>&nbsp;</td>";
		   //}
		   column_html += "</tr>";
	   }
	  //column_html = column_html.substring(0, column_html.length - 25); 
	  column_html += "</table>";
	  column_html += "</div></td></tr>";
   }
   column_html += "</table>"
   document.getElementById('column_name_cn').innerHTML = column_html;

   //�����ѯ����
   
   var condition=jsondata.conditions;
   var cond_html="";
   for(var i=0;i<condition.length;i++){
     var cond=condition[i];
     cond_html+=cond.logic.name_cn+cond.leftParen+"["+cond.table.name_cn+"] �� "+cond.column.name_cn+" <span style='color:red'> "+cond.paren.name_cn+" </span>";
     cond_html+= cond.param_value.name_cn+cond.rightParen+"<br/>";
   }
   document.getElementById('service_condition').innerHTML=cond_html;
    if(!cond_html==""){
    	$('#cxtj_space').show()
    }
   //�û��������
   
   var param=jsondata.params;
   var param_html="";
   for(var i=0;i<param.length;i++){
     var parm=param[i];
     param_html+=parm.logic.name_cn+parm.leftParen+" ["+parm.table.name_cn+"] �� "+parm.column.name_cn+" <span style='color:red'> "+parm.paren.name_cn+" </span>";
     param_html+= parm.param_value.name_cn+parm.rightParen+"<br/>";
   }
   document.getElementById('service_param').innerHTML=param_html; 
   if(!param_html==""){
   		$('#srtj_space').show()
   }
    initWeek();
	initTime();
	initNumber();
	initTotal();
    
    var limit_data = getFormFieldValue("record:limit_data");
	limit_data = eval("(" + limit_data + ")");
	var data_array = new Array;
	data_array.push({
						"week": "����",
						"datesStr": "��ʼʱ��-����ʱ��",
			//			"end_time": "����ʱ��",
						"times_day": "���ʴ���",
						"count_dat": "ÿ�η�������",
						"total_count_day": "ÿ���������"
					});
	limit_data = limit_data.data;
	for(var ii=0; ii< limit_data.length; ii++){
		data_array.push(limit_data[ii]);
	}
	if(data_array.length > 1){
		$('#limit_table').show();
	}else{
		$('#limit_table').hide();
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

    var is_month_data = getFormFieldValue("record:is_month_data");
	var visit_period = getFormFieldValue("record:visit_period");
	if(is_month_data=='N'){
		document.getElementById("span_record:is_month_data").innerHTML="������";
	}else{
		document.getElementById("span_record:is_month_data").innerHTML="����";
	}
}
    if(!( getFormFieldValue('record:is_month_data') =='' && getFormFieldValue('record:visit_period') ==''  )){
    	 $('#pzdz_space').show()
    } 
    $('.tailrow:eq(0)').parent().find('tr').each(function(){
    	$(this).find("td:eq(0)").css("white-space", "nowrap")
    });
    $('.tailrow:eq(0)').parent().find('tr').each(function(){
    	$(this).find("td:eq(2)").css("white-space", "nowrap")
    });
    $('.tailrow:last').parent().find('tr').each(function(){
    	$(this).find("td:eq(0)").css("white-space", "nowrap")
    });
    $('.tailrow:last').parent().find('tr').each(function(){
    	$(this).find("td:eq(2)").css("white-space", "nowrap")
    });
    $('.divHeightControll').each(function(){
    	$(this).find(".line:last").html("&nbsp;");
    })
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:errors/>
<freeze:form action="/txn40200010" >
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="service_id" caption="����ID" style="width:95%"/>
  </freeze:frame>
  <br/>
 <table  border=0 cellpadding=0 cellspacing=0 width="95%" align="center" style="border-collapse:collapse;">
	<tr>
		<td >
			<div style="width:100%;">
<DIV >
<SPAN style="FONT-STYLE: normal;COLOR: #000000; FONT-SIZE: 13px; FONT-WEIGHT: bold; TEXT-DECORATION: none">������Ϣ</SPAN>
</DIV>
		</div>
		</td>
	</tr>
</table>
<freeze:block property="record" caption="�鿴�������Ϣ" width="95%">
      <freeze:cell property="service_type" caption="��������" valueset="��Դ����_����Դ����" style="width:95%"/>
      <freeze:cell property="interface_id" caption="�����ӿ�" valueset="�������_�ӿ�����"  show="name" style="width:95%"/>
      <freeze:cell property="mintime" caption="���������ṩʱ��" style="width:95%"/>
      <freeze:cell property="maxtime" caption="��������ṩʱ��" style="width:95%"/>

      
      <freeze:hidden property="service_description" caption="����˵��"  style="width:98%"/>
      <freeze:hidden property="regist_description" caption="����˵��"  style="width:98%"/>
      
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
      <freeze:hidden property="service_name" caption="��������" datatype="string"  style="width:95%"/>
      <freeze:hidden property="service_targets_id" caption="�������" style="width:95%"/>
      <freeze:hidden property="service_state" caption="����״̬" style="width:95%"/>
      <freeze:hidden property="service_no" caption="������" datatype="string" style="width:95%"/>
      <freeze:hidden property="old_service_no" caption="������" align="left" style="width:95%" />
      <freeze:hidden hint="�������SQL���" property="sql" caption="SQL" style="width:98%"/>
  </freeze:block>
   <br/>
  
  
    
<table  border=0 cellpadding=0 cellspacing=0 width="95%" align="center" style="border-collapse:collapse;">
	<tr>
		<td >
			<div style="width:100%;">
<DIV >
<SPAN style="FONT-STYLE: normal;COLOR: #000000; FONT-SIZE: 13px; FONT-WEIGHT: bold; TEXT-DECORATION: none">�����ֶ���Ϣ</SPAN>
</DIV>
		</div>
		</td>
	</tr>
</table>
	<freeze:block property="record" caption="�����ֶ���Ϣ" width="95%">
	  <freeze:hidden property="column_name_cn" caption="������ѡ�ֶ�" datatype="string"  style="width:95%" colspan="2"/>
	  	  <tr valign="center" id="row_0" height="32">
		
		<td colspan='4'><div id="column_name_cn" style="width:95%"></div>
	</td>
</tr>
	</freeze:block>

	   <br/>
	   
	   <DIV id="cxtj_space"  style="display:none;"> 
<table  border=0 cellpadding=0 cellspacing=0 width="95%" align="center" style="border-collapse:collapse;">
	<tr>
		<td >
			<div style="width:100%;">
<DIV >
<SPAN style="FONT-STYLE: normal;COLOR: #000000; FONT-SIZE: 13px; FONT-WEIGHT: bold; TEXT-DECORATION: none">�����ѯ����</SPAN>
</DIV>
		</div>
		</td>
	</tr>
</table>
	<freeze:block property="record" caption="�����ѯ����" width="95%">
	  <freeze:hidden property="service_condition" caption="�����ѯ����" datatype="string"  style="width:95%" colspan="2"/>
		  <tr valign="center" id="row_0" height="32">
		
		<td colspan='4'><span id="service_condition" style="width:95%"></span>
	</td>
</tr>
	</freeze:block>
	</DIV>
		   <br/>
	<DIV id="srtj_space"  style="display:none;"> 	  
<table  border=0 cellpadding=0 cellspacing=0 width="95%" align="center" style="border-collapse:collapse;">
	<tr>
		<td >
			<div style="width:100%;">
<DIV >
<SPAN style="FONT-STYLE: normal;COLOR: #000000; FONT-SIZE: 13px; FONT-WEIGHT: bold; TEXT-DECORATION: none">�û���������</SPAN>
</DIV>
		</div>
		</td>
	</tr>
</table>
	<freeze:block property="record" caption="�û���������" width="95%">
	  <freeze:hidden property="service_param" caption="�û��������" datatype="string"  style="width:95%" colspan="2"/>
			  <tr valign="center" id="row_0" height="32">
		
		<td colspan='4'><span id="service_param" style="width: 95%; display: inline-block; line-height: 1.6;"></span>
	</td>
</tr>
	</freeze:block>
	</DIV>
	
	<br/>
	
	<DIV id="pzdz_space"  style="display:none;"> 
<table  border=0 cellpadding=0 cellspacing=0 width="95%" align="center" style="border-collapse:collapse;">
	<tr>
		<td >
			<div style="width:100%;">
<DIV >
<SPAN style="FONT-STYLE: normal;COLOR: #000000; FONT-SIZE: 13px; FONT-WEIGHT: bold; TEXT-DECORATION: none">�������ù���</SPAN>
</DIV>
		</div>
		</td>
	</tr>
</table>
	 <freeze:block property="record" caption="�������ù���" width="95%">
      <freeze:cell property="is_month_data" caption="���Ʒ��ʵ�������" datatype="string" style="width:95%" />
      <freeze:cell property="visit_period" caption="����ʱ����(��)" datatype="string" style="width:95%" />
	</freeze:block>
	</DIV>
	
<br />
<table id="limit_table" border=0 cellpadding=0 cellspacing=0 width="95%" align="center" style="display:none;border-collapse:collapse;">
	<tr>
		<td >
			<div style="width:100%;">
  <DIV>
<SPAN style="FONT-STYLE: normal; COLOR: #000000; FONT-SIZE: 13px; FONT-WEIGHT: bold; TEXT-DECORATION: none">���Ʒ�������</SPAN>

</DIV>
      	
      	<!-- ���ĸ���ǩҳ��ʼ -->
      	<div >
      	<br/>
      		 <table class="dd_table" border="0" cellpadding="0" cellspacing="0" width="100%" align="center">
			<tr>
				<td><div id="tab4_limit_div"></div></td>
			</tr>
			</table>
			</div>
      	<!-- ���ĸ���ǩҳ���� -->
			</div>
		</td>
	</tr>
	
</table>
<table border=0 cellpadding=0 cellspacing=0 width="95%" align="center" style="border-collapse:collapse;">
<tr>
	<td height="30" align="center">
	<div onclick="func_record_goBack()" class="btn_cancel"></div>
	</td>
</tr>
</table>
</freeze:form>
</freeze:body>
</freeze:html>
