<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<%@ page import = "cn.gwssi.common.context.Recordset"%>
<%@ page import = "com.gwssi.common.constant.FileConstant"%>
<%@ page import="com.gwssi.webservice.server.ParamAnalyzer"%>
<%@ page import="com.gwssi.common.constant.ShareConstants" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="1000" height="350">
<head>
<title>修改服务表信息</title>
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
			/*width:120px !important;*/
			height: auto;
		}
		.cssSelect{
			/*width:120px !important;*/
			height: auto;
		}
	</style>
</head>

<script type="text/javascript">
function limConTdWidth(){
$("#tab2_cond_table_div  tr").each(function(){
var s=$(this).children("td").eq(5).width(200);
});
}
var svrId = "";
// 保 存
function func_record_saveAndExit()
{
	var sava_data = getPostData();
	if(sava_data){
    	setFormFieldValue( "record:jsoncolumns" ,sava_data[0]);
    	setFormFieldValue( "record:sql" ,sava_data[1]);
		setFormFieldValue( "record:limit_data" ,sava_data[2]);
    	setFormFieldValue( "record:is_month_data", sava_data[3] );
    	//setFormFieldValue( "record:visit_period", sava_data[4] );
    	document.getElementById("record:visit_period").value=sava_data[4];
		setFormFieldValue( "inner-flag:modify-flag" ,"true");
		saveAndExit( '', '修改共享服务的服务表' );	// /txn40200001.do
	}
}
/* function deleteReturn(index){
	//alert(settings.data[(index+1)].table.id);
	//var columns = $(tab1_col_selected_div).tablet("getAllData");
    //columns = eval("("+columns+")");
	
	//if(tableid == $(tab1_table_all_div).find("option:selected").val())
	//{
		getColumnsByTable($(tab1_table_all_div).find("option:selected").val());
	//}
	
} */
function deleteReturn(){
	if(tab_index == 1){
		var tmpdata = $("#tab2_cond_table_div").tablet("getAllData");
		tmpdata = eval('(' + tmpdata +')');
		if(tmpdata.data.length == 0){
			$('#tab2_logic').attr('disabled', true);
		}else{
			$('#tab2_logic').attr('disabled', false);
		}//
	}else if(tab_index == 2){
		var tmpdata = $("#tab3_param_div").tablet("getAllData");
		tmpdata = eval('(' + tmpdata +')');
		if(tmpdata.data.length == 0){
			$('#tab3_cond').attr('disabled', true);
		}else{
			$('#tab3_cond').attr('disabled', false);
		}
	}
	getColumnsByTable($(tab1_table_all_div).find("option:selected").val());
}
// 返 回
function func_record_goBack()
{
	goBack();	// /txn40200001.do
}


//鼠标放上时展示当前选中的代码值
function showSelectedValue(thistd)
{
   var selectOp = window.document.getElementById('param_value_select');
  if( selectOp.selectedIndex>-1){
	thistd.title=document.getElementById('param_value_select').options[selectOp.selectedIndex].text;
	}
	else{
	thistd.title="";
	}
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var jsoncolumn = getFormFieldValue( "record:jsoncolumns" );
	svrId = getFormFieldValue("record:service_id");
	if(jsoncolumn==''){
		$(".btn_save").hide();
		$(".owasp").hide();
    	alert("数据获取失败，请检查该服务相关文件是否存在！");
  //  	window.close();
    }else{
   //初始化服务限定条件表格
    var jsondata = eval('(' + jsoncolumn + ')'); 
    initWeek();
	initTime();
	initNumber();
	initNumPer();
	initTotal();
    
    var limit_data = getFormFieldValue("record:limit_data");
	limit_data = eval("(" + limit_data + ")");
	var data_array = new Array;
	data_array.push({
						"week": "星期",
						"datesStr": "开始时间-结束时间",
						//"end_time": "",
						"times_day": "访问次数",
						"count_dat": "每次访问总数",
						"total_count_day": "每天访问总数"
					});
	limit_data = limit_data.data;
	for(var ii=0; ii< limit_data.length; ii++){
		data_array.push(limit_data[ii]);
	}
	var opts_lim = {
					data: data_array,
					shownum : 8
				}
				$(tab4_limit_div).tablet(opts_lim);
//    $(function() {
	//准备tab页
	var tabs = $.tabs({
		selector:"#tabs",
    	selected:0,
    	click: function(index){
    		if(index == 1){
    			var flag = checkItem();
    			if(flag){
    				$("#all_data_div").hide();
        			tab_index = 1;
        			tab2_table = tab1_table_all || [];
    	    		//初始化第二个标签页中的列表 
    				var tab2_table_opt = {
    					data: tab2_table,
    					size: 1,
    					multiple : false,
    					onChange: dblfunction
    				};
    				$(tab2_table_div).dataSelector(tab2_table_opt);
       				getColumnsByTable($(tab2_table_div).find("option:first").val());
    				$(tab2_table_div).find("option:first").attr("selected", true);
        			setOperator(tab2_col_select, '#paren');
    			}
	    	}else if(index == 2){
	    		var flag = checkItem();
    			if(flag){
    				tab_index = 2;
    	    		tab3_table = tab1_table_all || [];
    	    		//初始化第三个标签页中的列表 
    				var tab3_table_opt = {
    					data: tab3_table,
    					size: 1,
    					multiple : false,
    					onChange: dblfunction
    				};
    				$(tab3_table_div).dataSelector(tab3_table_opt);
    				getColumnsByTable($(tab3_table_div).find("option:first").val());
    				$(tab3_table_div).find("option:first").attr("selected", true);
    			}
	    	}else if(index == 3){
	    		var flag = checkItem();
    			if(flag){
    	    		tab_index = 3;
    			}
	    	}else if(index == 4){
	    		var flag = checkItem();
    			if(flag){
    	    		tab_index = 4;
    	    		showSql();
    			}
	    	}else{
	    		tab_index = 0;
	    	}
    	}
    });
    //初始化数据表数据
    var options = {
		multiple : false,
		data : tab1_table_all,
		size : 1,
		//onDblclick: dblfunction
		onClick: dblfunction
	}
	$(tab1_table_all_div).dataSelector(options);
	//初始化字段数据，自动加载第一张表的字段
	//setCloumnNames($("#data_all_table option:first").val());
	//$("#data_all_table option:first").addClass("selected");
	
	//初始化第一个标签页的表格
	var data_tmp = [{
			"table" : "表名",
			"column" : "数据项名",
			"alias": "别名",
			"column_type" : "类型",
			"column_length" : "长度"
		}];

	var tab1_col_selected_opt = {
		addDelete : true,
		data : data_tmp.concat(jsondata.columns),
		editable: 3,
		onDelete: function(){
			var delData = arguments[0];
			if(delData){
				getColumnsByTable(delData.table.id);
				//alert("#tab1_table_all_div_id option#"+delData.table.id);
				$("#tab1_table_all_div_id option#"+delData.table.id).attr("selected", true);
			}
			deleteReturn();
		}
	};
	$(tab1_col_selected_div).tablet(tab1_col_selected_opt);
	
	//初始化第二个标签页中的表格
	data_tmp = [{
			"logic":"逻辑条件",
          	"leftParen":"括号",
          	"table":"表名",
       		"column":"字段",
          	"paren":"条件",
			"param_value":"参数值",
            "rightParen":"括号"
        }];
	var tab2_cond_opt = {
		addDelete:true,
		data: data_tmp.concat(jsondata.conditions)
		//,
/*		dblclick : function(event){
			var which = this.rowIndex;
			if(which > 0){
				table2col(this.rowIndex);
			}
		}*/
	};
	$(tab2_cond_div).tablet(tab2_cond_opt); 
	
	//初始化第三个标签页中的表格
	data_tmp = [{
			"logic":"逻辑条件",
			"leftParen":"括号",
          	"table":"数据表",
       		"column":"数据项",
            "col_type": "类型",
          	"paren":"条件",
			"param_value":"参数值",
			"rightParen":"括号"
        }];
	var tab3_param_opt = {
		addDelete:true,
		data: data_tmp.concat(jsondata.params)
	};
	$(tab3_param_div).tablet(tab3_param_opt); 

	getTableListByInterface1('init');
	setOperator('#tab3_col_type', '#tab3_paren');
//});

setTimeout(function(){
	getColumnsByTable($(tab1_table_all_div).find("option:first").val());
	$(tab1_table_all_div).find("option:first").attr("selected", true);
},1000);

limConTdWidth();

//var first = true;

//设置选择为空不为空时后面的输入框不可输入

$('#paren').change(function(){
    var p1=$(this).children('option:selected').val();//这就是selected的值
    if(p1=="is null" || p1=="is not null")
    {
    	$('#param_value').attr("disabled",true);
    	$('#param_value').attr("value",'');
    	$('#param_value_select').attr("disabled", true);
    }
    else
    {
    	$('#param_value').attr("disabled",false);
    	$('#param_value_select').attr("disabled", false);
    }
    });
}
	var is_month_data = getFormFieldValue("record:is_month_data");
	if(is_month_data && 'Y' == is_month_data){
		//$('#month_data1').attr("checked", true);
		//$('#month_data2').attr("checked", false);
		$('#month_data1')[0].checked = true;
		$('#month_data2')[0].checked = false;
	}else{
		//$('#month_data1').attr("checked", false);
		//$('#month_data2').attr("checked", true);
		$('#month_data1')[0].checked = false;
		$('#month_data2')[0].checked = true;
	}
	
	var visit_period = getFormFieldValue("record:visit_period");
	if( visit_period != '' && parseInt(visit_period)>0){
		$("#limitVisitPeriod").attr("checked", true);
		$('#visit_period_day').val(visit_period);
	}else{
		$("#limitVisitPeriod").attr("checked", false);
		$('#visit_period_day').val(0);
	}
	var seviceType = getFormFieldValue("record:service_type");
	if(seviceType!=null&&seviceType=="02"){
		$("#limitOp").hide();
	}else{
		$("#limitOp").show();
	}
	
}
function changeServiceType(){
	var seviceType = getFormFieldValue("record:service_type");
	document.getElementById("dta").click();
	if(seviceType!=null&&seviceType=="02"){
		$("#limitOp").hide();
	}else{
		$("#limitOp").show();
		 initWeek();
		 initTime();
		 initNumber();
		 initNumPer();
		 initTotal();
		 var data_array = new Array;
		 data_array.push({
								"week": "星期",
								"datesStr": "开始时间-结束时间",
								//"end_time": "",
								"times_day": "访问次数",
								"count_dat": "每次访问总数",
								"total_count_day": "每天访问总数"
							});
			
		var opts_lim = {
							data: data_array,
							shownum : 8
						}
		$(tab4_limit_div).tablet(opts_lim);
	}
}

_browse.execute( '__userInitPage()' );


</script>
<freeze:body>
<freeze:title caption="修改服务信息"/>
<freeze:errors/>
<freeze:form action="/txn40200010" enctype="multipart/form-data">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="service_id" caption="服务ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改服务表信息" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回"hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="service_id" caption="服务ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="old_service_no" caption="服务编号" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="delIDs" caption="multi file id" style="width:90%" />
      <freeze:hidden property="delNAMEs" caption="multi file name" style="width:90%" />
      <freeze:hidden property="column_no" caption="数据项ID"  style="width:98%"/>
      <freeze:hidden property="column_name_cn" caption="数据项中文名称"  style="width:98%"/>
      <freeze:hidden property="column_alias" caption="数据项别名"  style="width:98%"/>
      <freeze:hidden property="sort_column" caption="排序字段"  style="width:95%"/>
      <freeze:hidden property="sql" caption="SQL"  style="width:98%"/>
      <freeze:hidden property="sql_one" caption="SQL1" style="width:98%"/>
      <freeze:hidden property="sql_two" caption="SQL2"  style="width:98%"/>
      <freeze:hidden property="is_markup" caption="有效标记" value="Y" style="width:95%"/>
      <freeze:hidden property="creator_id" caption="创建人ID"  style="width:95%"/>
      <freeze:hidden property="created_time" caption="创建时间"  style="width:95%"/>
      <freeze:hidden property="last_modify_id" caption="最后修改人ID"  style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="最后修改时间"  style="width:95%"/>
      <freeze:hidden property="jsoncolumns"  datatype="string"  style="width:95%"/>
      <freeze:hidden property="limit_data" datatype="string" style="width:95%" />
      <freeze:hidden property="temp" datatype="string" style="width:95%" />
      <freeze:hidden property="is_month_data" datatype="string" />
      <freeze:hidden property="visit_period" datatype="string" />
      <freeze:text property="service_name" caption="服务名称" notnull="true" datatype="string" maxlength="100" colspan="2" style="width:97.5%"/>
      
      <freeze:select  property="interface_id" caption="基础接口" onchange="getTableListByInterface1()" valueset="共享服务_接口名称"  show="name" style="width:95%"/>
      
      <freeze:select property="service_state" caption="服务状态" notnull="true" valueset="资源管理_归档服务状态"  style="width:95%"/>
	  <freeze:browsebox property="service_targets_id" caption="服务对象" notnull="true" valueset="资源管理_共享服务对象名称"  show="name" style="width:95%"/>
	  <freeze:select property="service_type" caption="服务类型" notnull="true" valueset="资源管理_数据源类型" onchange="changeServiceType()" style="width:95%"/>
      <freeze:select property="is_single" caption="是否单户" valueset="是否单户"  style="width:95%"/>
      <freeze:hidden property="fjmc" caption="文件上传" />
      <freeze:hidden property="fj_fk" caption="文件上传id" style="width:90%" />
      <freeze:text readonly="true" property="service_no" caption="服务编号" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:textarea property="service_description" caption="服务说明" colspan="2" rows="1" maxlength="2000" style="width:98%"/>
      <freeze:textarea property="regist_description" caption="备案说明" colspan="2" rows="1" maxlength="2000" style="width:98%"/>
   <%
    DataBus context = (DataBus) request.getAttribute("freeze-databus");
    Recordset fileList=null;
    try{
     fileList = context.getRecordset("fjdb");

    if(fileList!=null && fileList.size()>0){
    	out.println("<tr><td height=\"32\" align=\"right\">文件名称：</td><td colspan=\"3\"><table >");
        for(int i=0;i<fileList.size();i++){
               DataBus file = fileList.get(i);
               String file_id = file.getValue(FileConstant.file_id);
               String file_name = file.getValue(FileConstant.file_name);
%>
<tr id='<%=file_id%>'>
 <td><a href="#" onclick="downFile('<%=file_id%>')" title="附件" ><%=file_name %></a></td>
 <td><a href="#" onclick="delChooseFile('<%=file_id%>','record:delIDs','<%=file_name%>','record:delNAMEs')"  title="删除" ><span class="delete">&nbsp;&nbsp;</span></a>
</tr>
<%  }
    out.println("</table></td></tr>"); 
   }
   }catch(Exception e){
	   System.out.println(e);
   }
%>   
  <!-- <span class="btn_add" onclick="addNewRowEx('record:fjmc1','&nbsp;上传文件：','80%')" title="增加">&nbsp;&nbsp;</span> -->
<freeze:file property="fjmc1" caption="备案附件" style="width:80%" maxlength="100" colspan="2" />&nbsp;<span class="btn_add" onclick="addNewRow()" title="增加"></span><table id="moreFile" width="100%"  cellspacing="0" cellpadding="0"></table>
 <freeze:cell property="crename" caption="创建人：" datatype="string"  style="width:95%"/>
  <freeze:cell property="cretime" caption="创建时间：" datatype="string"  style="width:95%"/>
  <freeze:cell property="modname" caption="最后修改人：" datatype="string"  style="width:95%"/>
  <freeze:cell property="modtime" caption="最后修改时间：" datatype="string"  style="width:95%"/>
  </freeze:block>

</freeze:form>
<br />
<table class="owasp" border=0 cellpadding=0 cellspacing=0 width="95%" align="center" style="border-collapse:collapse;">
	<tr>
		<td >
			<div style="width:100%;">
				<dl class="tabs" id="tabs">
      	<dt>
      		<a id="dta">配置结果集</a>
      		<a>查询条件</a>
      		<a>配置输入参数</a>
      		<a id="limitOp">配置限制条件</a>
      		<a>预览SQL</a>
      	</dt>
      	<!-- 第一个标签页开始 -->
      	<dd><div>
      	<table class="dd_table" border="1" cellpadding="3" cellspacing="0" width="100%" align="center">
		<tr style="padding-top: 5px;">
      			<td style="" valign="top" width="120px;">
	      			<div style=" width:100%; text-align:left; font-size: 14px; color: 333#;" >请选择数据表：</div>
      				<div style=" width:100%;" id="tab1_table_all_div"></div>
      			</td>
      			<td style="" valign="top" width="120px">
      				<div style=" width:100%; text-align:left; font-size: 14px; color: 333#;" >请选择数据项：</div>
      				<div style="background-color:white; width:100%;" id="tab1_col_all_div"></div>
      			</td>
      			<td style="border:1px solid gray; " width="20px">
      				<div style="width: 100%; height: 160px; line-height:40px; text-align: center;">
      				<a style="display:none;" href="javascript:void(0)" onclick="table2col()" >&lt;</a>
      				<a style="display:none;" href="javascript:void(0)" onclick="" >&lt;&lt;</a>
      				<a href="javascript:void(0)" onclick="addMultiData()" >&gt;</a>
      				<a href="javascript:void(0)" onclick="addMultiData()" >&gt;&gt;</a>
      			</div>
      			</td>
      			<td valign="top" style="">
      				<div style=" width:100%; text-align:left; font-size: 14px; color: 333#; padding-left: 10px;" >结果集：</div>
      				<div style="width:100%;" id="tab1_col_selected_div"></div>
      			</td>
      		</tr>
      	</table></div>
      	</dd>
      	<!-- 第一个标签页结束 -->
      	<!-- 第二个标签页开始 -->
      	<dd><div>
      		<table class="dd_table" border="1" cellpadding="3" cellspacing="0" width="100%" align="center">
				<tr>
      				<td style="border: 1px solid gray;">
      				<table style="width: 100%;background-color: #dee6e9; border-collapse:collapse;" border=1 cellpadding=2 cellspacing=0 align="center">
      				<tr><td nowrap>条件</td><td nowrap> 括弧 </td><td nowrap>数据表</td><td nowrap>字段</td><td nowrap>条件</td>
   								<td nowrap>值</td><td nowrap>括弧</td>
     					</tr>
      					<tr>
      						<td><select id="tab2_logic"><option selected value="AND" >并且(AND)</option><option value="OR">或者(OR)</option></select></td>
      						<td>
      							<select id="leftParen">
           							<option selected value=""></option>
           							<option value="(">(</option>
           							<option value="((">((</option>
           							<option value="(((">(((</option>
           							<option value="((((">((((</option>
         						</select>
         					</td>
         					<td style="width:80px; background-color:white; overflow:hidden;"><div id="tab2_table_all_div"></div></td>
         					<td style="width:80px; background-color:white; overflow:hidden;"><div id="tab2_col_all_div"></div></td>
         					<td>
         						<select id="paren"></select>
         					</td>
         					<td onmouseover="showSelectedValue(this);">
         					<input id="param_value" type="text"/>
         					<select id="param_value_select" style="display:none;width:150px;" ></select></td>
         					<td>
         						<select id="rightParen">
         							<option value=""></option>
         							<option value=")">)</option>
         							<option value="))">))</option>
         							<option value=")))">)))</option>
         							<option value="))))">))))</option>
       							</select>
       						</td>
      					</tr>
     <tr><td colspan=7 align="right" nowrap><span style="color:red;" id="tab2_error_msg">&nbsp;&nbsp;</span><button onclick="addCondData()">添加条件</button>&nbsp;&nbsp;</td></tr>
  	</table>
      				</td>
      			</tr>
      			 <tr>
      				<td style="padding:10px;" valign="top">
      					<div id="tab2_cond_table_div"></div>
      				</td>
      			</tr>
      			<tr id="old_condition">
      				<td style="padding: 0 10px 0; text-align:left;" valign="top">
      					接口配置条件
      				</td>
      			</tr>
      			<tr  id="old_condition1">
      				<td style="padding:10px;" valign="top">
      					<div id="tab2_interface_div"></div>
      				</td>
      			</tr>
	      	</table></div>
      	</dd>
      	<!-- 第二个标签页结束 -->
      	<!-- 第三个标签页开始 -->
      	<dd><div>
      		<table class="dd_table" border="1" cellpadding="3" cellspacing="0" width="100%" align="center">
				<tr><td style="border: 1px solid gray;">
				<table style="width: 100%;background-color: #dee6e9;border-collapse:collapse;" border=1 cellpadding=2 cellspacing=0>
				<tr><td>逻辑条件</td><td>括弧</td><td>数据表</td><td>数据项</td><td>类型</td><td>条件</td><td>值</td><td>括弧</td></tr>
              <tr><td>
              <select id="tab3_cond">
              	<option value=""></option>
              	<option value='AND'>并且(AND)</option>
              	<option value='OR'>或者(OR)</option>	 
              </select>
             </td>
              <td nowrap>
          <select id="tab3_leftParen">
           <option selected value=""></option>
           <option value="(">(</option>
           <option value="((">((</option>
           <option value="(((">(((</option>
           <option value="((((">((((</option>
         </select> 
        </td>
     <td style="width:80px; background-color:white; overflow:hidden;"><div id="tab3_table_all_div"></div></td>
     <td style="width:80px; background-color:white; overflow:hidden;"><div id="tab3_col_all_div"></div></td>
     <td nowrap><select onchange="setOperator('#tab3_col_type', '#tab3_paren');" id="tab3_col_type">
     	<option value='1' selected>变长字符型</option>
     	<option value='3'>数值型</option>
     	<option value='2' >日期型</option></select>
     </td>
     <td nowrap><select id="tab3_paren"></select>
   </td>
   <td nowrap><input type="text" value="<%=ParamAnalyzer.USER_PARAM_SIGN %>" id="tab3_param_value" disabled /></td>
   <td nowrap>
       <select id="tab3_rightParen">
         <option value=""></option>
         <option value=")">)</option>
         <option value="))">))</option>
         <option value=")))">)))</option>
         <option value="))))">))))</option>
       </select>
     </td></tr>
     <tr><td colspan=7 align="right"><span style="color:red;" id="tab3_error_msg">&nbsp;&nbsp;</span>&nbsp;&nbsp;
     	<button onclick="addTab3Data();">添加条件</button>&nbsp;&nbsp;
     </td></tr>
  </table>
	</td>
      			</tr>
      			<tr><td valign="top" style="padding-top:10px;">
      				<div style="width: 100%;" id="tab3_param_div"></div>
      			</td></tr>
      		</table>
      	<br /></div>
      	</dd>
      	<!-- 第三个标签页结束 -->
      	<!-- 第四个标签页开始 -->
      	<dd><div>
      		<table class="dd_table" border="1" cellpadding="3" cellspacing="0" width="100%" align="center">
				<tr style="padding-top: 5px;">
      			<td>
      				<table width="100%" border="0" align="center" class="frame-body" cellpadding="0" cellspacing="0"> 
<tr><td style="padding:0;">	
<table width="100%" border="1" style="background-color: #dee6e9;;border-collapse:collapse;" align="center" cellpadding="2" cellspacing="0"> 
	  <tr>
		         	<td nowrap align="left" width="15%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;限制访问当月数据</td>
		         	<td nowrap align='left'  width="40%">
		         		<input type="radio" value="Y" name="is_month_data" id="month_data1" /><label id="month_label_1" for="month_data1">限制</label>
		         		<input type="radio" value="N" name="is_month_data" id="month_data2" /><label id="month_label_2" for="month_data2">不限制</label>
		         	</td>
		         	<td nowrap align="left" width="15%">&nbsp;&nbsp;&nbsp;&nbsp;访问时间间隔<input id='limitVisitPeriod' checked onFocus='this.blur();' type='checkbox' value='0' onclick="setVisitP();" /></td>
		         	<td nowrap align='left' width="30%"><input id='visit_period_day' size="5" type='text' value='7' />&nbsp;天</td>
	 </tr>
	 <tr><td height="12" bgColor="white" colspan="4"></td></tr>
	  <tr >
		         	<td nowrap align="left" width="150px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;限定星期</td>
		         	<td colspan=3  id="weekTd" ></td>
	  </tr>
	  <tr>
		 <td nowrap align="left" width="15%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;访问时间<input id='limitTimeBtn' onFocus='this.blur();' type='checkbox' value='0' onclick="setlimitTime();"  /></td>
		 <td nowrap align='left'  width="40%" >
		 <input id="datesStr" readonly type="hidden"  value="点击右侧按钮选择时间" style="width:80%;" /> 
		 <span id="dates" style="width:75%">(注：重叠的时间段将被自动合并)</span>
		 <div  id="Date_zone" style="background:#fcfcfc;z-index:9934;display:none;position:absolute; left:400px; top: 400px;border:1px solid #069;"  >
		      	 开始日期：<input id="stime" readonly type="text" value='00:00' size='6' />至
		      	结束日期：<input id="etime" readonly type="text" value="24:00" size='6' />
		      	 <br />
		    	<input type="button" value="确定" onclick="submitDate()" /><input type="button" value="关闭" onclick="cancelDate()" />
		    	<br />
		    </div>
		 
		<!--  从 <input id="stime" readonly="true" type="text" value='00:00' size='6' />&nbsp;到 &nbsp; <input id="etime" readonly="true" type="text" value="24:00" size='6' />
		  --><input id="addDateBtn" type="button"  onclick="showDate()" value="添加"/></td>
	 	 <td nowrap align="left" width="15%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;每天访问次数<input id='limitNumberBtn' onFocus='this.blur();' type='checkbox' value='0' onclick="setnumber();"  /></td>
		 <td nowrap align='left'   width="30%">每天&nbsp;<input id="limitNumber"  type="text"  size='6' />&nbsp;次</td>
	 </tr>
	 <tr >
		         	<td nowrap align="left" width="15%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;每次访问总数<input id='limitNumPerBtn' onFocus='this.blur();' type='checkbox' value='0' onclick="setnumPer();"  /></td>
		         	<td nowrap align='left'  width="40%">每次&nbsp;<input id="limitNumPer" disabled="disabled" name="limitNumPer"  type="text"  size='6' />&nbsp;条</td>
		         	<td nowrap align="left" width="15%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;每天总数<input id='limitTotalBtn' onFocus='this.blur();' type='checkbox' value='0' onclick="setTotal();" /></td>
		         	<td nowrap align='left' width="30%">每天&nbsp;<input id='limitTotal' type="text"  size='6' />&nbsp;条</td>
	 </tr>
				</table>
</td></tr>
				<tr><td style="text-align:right; padding-top:5px; padding-right: 10px; background-color: white;"><span style="color:red;" id="tab4_error_msg">&nbsp;&nbsp;</span>&nbsp;&nbsp;<button onclick="addLimitData();">添加限制条件</button>&nbsp;&nbsp;</td></tr>
				<tr>
					<td><div id="tab4_limit_div"></div></td>
				</tr>
</table>
      			</td>
      		</tr>
      		</table></div>
      	</dd>
      	<!-- 第四个标签页结束 -->
      	<dd>
    <div>
      		<table class="dd_table" border="1" style="table-layout:fixed;" cellpadding="3" cellspacing="0" width="100%" align="center">
		<tr style="padding-top: 5px;">
      			<td> 
    <textarea readonly style="width:100%; height: 200px;" id="sql_last"></textarea></td></tr></table></div>
    </dd>
    </dl>
			</div>
		</td>
	</tr>
</table>
<br />
</freeze:body>
</freeze:html>
