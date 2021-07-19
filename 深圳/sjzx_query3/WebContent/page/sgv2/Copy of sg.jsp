<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<title>增加服务信息</title>
<link href="<%=request.getContextPath()%>/page/sgv2/script/lib/jquery.ui.timepicker.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/page/sgv2/script/css/cupertino/jquery.ui.all.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/page/sgv2/script/jquery-plugin-Selector/css/jquery.dataSelector.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/page/sgv2/script/jquery-plugin-tab/css/jquery.tabs.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/page/sgv2/jquery171.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/sgv2/script/jquery-plugin-tab/jquery.tabs.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/sgv2/script/jquery-plugin-data2table/js/jquery.tablet.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/sgv2/script/jquery-plugin-Selector/js/jquery.dataSelector.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/sgv2/script/lib/jquery-ui.custom.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/sgv2/script/lib/share_service.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/sgv2/script/lib/jquery.ui.timepicker.js"></script>
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

function deleteReturn(){
	/* alert(tab_index);
	if(tab_index == 0){
		var rowdata = $("#tab2_cond_table_div").tablet("getRowData", (index+1));
		rowdata = eval('('+ rowdata +')');
		alert(rowdata.table.id + " = = == " +$("#tab1_table_all_div_id option[id='"+rowdata.table.id+"']").text());
		$("#tab1_table_all_div_id option[id='"+rowdata.table.id+"']")[0].click();
	}else  */
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
	//getColumnsByTable($(tab1_table_all_div).find("option:selected").val());
}

	//获取服务编号
	function getService_no(){
		    var page = new pageDefine("/txn40200012.ajax", "获取服务编号");	
 			page.callAjaxService('setService_no');
	}
	function setService_no(errCode,errDesc,xmlResults){
	
			if(errCode != '000000'){
			setFormFieldValue( "record:service_no" ,"service1");
			return;
		}
		var service_no   = _getXmlNodeValues( xmlResults, "service_no");
		//alert(service_no);
		setFormFieldValue( "record:service_no" ,service_no);
	}


//从已选择字段表格删除一条数据到字段列表
function table2col(index){
	$(tab1_col_selected_div).tablet("removeRow", index);
}

// 保存并关闭
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
		saveAndExit( '', '保存共享服务的服务表' );	// /txn40200001.do
	}
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
	getService_no();
	
$(function() {
	//准备tab页
	var tabs = $.tabs({
		selector:"#tabs",
    	selected:0,
    	click: function(index){
    		if(index == 1){
    			var flag = checkItem();
        		if(flag){
        			var datatmp = $("#tab2_cond_table_div").tablet("getAllData");
      			  datatmp = eval('(' + datatmp + ')');
      			  if(datatmp.data.length > 0){
      				  $("#tab2_logic").attr("disabled", false);
      			  }else{
      				  $("#tab2_logic").attr("disabled", true);
      			  }
      			$("#all_data_div").hide();
      			tab_index = 1;
      			tab2_table = tab1_table_all || [];
  	    		//初始化第二个标签页中的列表 
  				var tab2_table_opt = {
  					data: tab1_table_all,
  					size: 1,
  					multiple : false,
  					onChange: dblfunction
  				};
  	    		if($(tab2_table_div).find("select").length>0){
  	    			$(tab2_table_div).dataSelector("destroy");
  	    		}
  				$(tab2_table_div).dataSelector(tab2_table_opt);
  				getColumnsByTable($(tab2_table_div).find("option:first").val());
  				$(tab2_table_div).find("option:first").attr("selected", true);
      			setOperator(tab2_col_select, '#paren');
        		}
	    	}else if(index == 2){
	    		var flag = checkItem();
        		if(flag){
        			var datatmp = $("#tab3_cond_table_div").tablet("getAllData");
        			  datatmp = eval('(' + datatmp + ')');
        			  if(datatmp.data.length > 0){
        				  $("#tab3_cond").attr("disabled", false);
        			  }else{
        				  $("#tab3_cond").attr("disabled", true);
        			  }
      	    		tab_index = 2;
      	    		tab3_table = tab1_table_all || [];
      	    		//初始化第三个标签页中的列表 
      				var tab3_table_opt = {
      					data: tab1_table_all,
      					size: 1,
      					multiple : false,
      					onChange: dblfunction
      				};
      	    		if($(tab3_table_div).find("select").length > 0){
      	    			$(tab3_table_div).dataSelector("destroy");
      	    		}
      	    		$(tab3_table_div).dataSelector(tab3_table_opt);
      				
      				getColumnsByTable($(tab3_table_div).find("option:first").val());
      				$(tab3_table_div).find("option:first").attr("selected", true);
          			//setOperator(tab3_col_select, '#tab3_paren');
          			$('#param_value_select').empty().hide();
          			$('#param_value').val('').show();
        		}
	    	}else if(index == 3){
	    		var flag = checkItem();
        		if(flag){
        			tab_index = 3;
        			initWeek();
    				initTime();
    				initNumPer();
    				initNumber();
    				initTotal();
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
		size : 10,
		onClick: dblfunction
	}
	$(tab1_table_all_div).dataSelector(options);
	
	//初始化自动加载第一个接口下的数据表数据
	var interface_opts = document.getElementById("record:interface_id").getElementsByTagName("option");
	for(var ii=0; ii<interface_opts.length; ii++){
		if(interface_opts[ii].value == ''){
			document.getElementById("record:interface_id").remove(ii);
		}
		if(interface_opts[ii].value != ''){
			interface_opts[ii].selected = "selected";
			getTableListByInterface();
			break;
		}
	}
	
	//初始化第一个标签页的表格
	var tab1_col_selected_opt = {
		addDelete : true,
		data : [{
			"table" : "表名",
			"column" : "数据项名",
			"alias" : "别名",
			"column_type" : "类型",
			"column_length" : "长度"
		}],
		editable: 3,
		onClick : function(event){
//			var which = this.rowIndex;
//			if(which > 0){
//				table2col(this.rowIndex);
//			}
			//console.log($(tab1_col_selected_div).tablet("getRowData", which));
		},
		shownum:5,
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
	var tab2_cond_opt = {
		addDelete:true,
		data: [{
			"logic":"逻辑条件",
          	"leftParen":"括号",
          	"table":"表名",
       		"column":"字段",
          	"paren":"条件",
			"param_value":"值",
            "rightParen":"括号"
        }]
	};
	$(tab2_cond_div).tablet(tab2_cond_opt); 
	
	//初始化第三个标签页中的表格
	var tab3_param_opt = {
		addDelete:true,
		data: [{
			"logic":"逻辑条件",
          	"leftParen":"括号",
          	"table":"数据表",
       		"column":"数据项",
            "col_type": "类型",
          	"paren":"条件",
			"param_value":"值",
            "rightParen":"括号"
        }]
	};
	$(tab3_param_div).tablet(tab3_param_opt); tab3_param_div
	
	var opts = {
					data: [{
						"week": "星期",
						"datesStr": "开始时间-结束时间",
			//			"end_time": "结束时间",
						"times_day": "访问次数",
						"count_dat": "每次访问总数",
						"total_count_day": "每天访问总数"
					}],
					shownum : 8
				}
	$(tab4_limit_div).tablet(opts);
	
});
	setOperator("#tab3_col_type", '#tab3_paren');
	
	 /* $('#paren').change(function(){
    var p1=$(this).children('option:selected').val();//这就是selected的值
    if(p1=="is null" || p1=="is not null")
    {
    	$('#param_value').attr("disabled",true);
    	$('#param_value').attr("value",'');
    }
    else
    {
    	$('#param_value').attr("disabled",false);
    }
    }); */
	//设置选择为空不为空时后面的输入框不可输入
	$('#param_value').attr("disabled",false);
	$('#param_value_select').attr("disabled", false);
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
	
	var seviceType = getFormFieldValue("record:service_type");
	if(seviceType!=null&&seviceType=="02"){
		$("#limitOp").hide();
	}else{
		$("#limitOp").show();
	}
}
setTimeout(function(){
	getColumnsByTable($(tab1_table_all_div).find("option:first").val());
	$(tab1_table_all_div).find("option:first").attr("selected", true);
},1000);

var first=true;

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
//_browse.execute( '__userInitPage()' );
</script>

<!-- 
      <freeze:button name="record_saveRecord" caption="保 存" visible="false" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" visible="false" caption="保存并继续" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
 -->
<br />
<table border=0 cellpadding=0 cellspacing=0 width="95%" align="center" style="border-collapse:collapse;">
	<tr>
		<td >
			<div style="width:100%;">
				<dl class="tabs" id="tabs">
      	<dt>
      		<a id="dta">配置结果集</a>&nbsp;&nbsp;
      		<a>查询条件</a>&nbsp;&nbsp;
      		<a>配置输入参数</a>&nbsp;&nbsp;
      		<a id="limitOp">配置限制条件</a>
      		<a>预览SQL语句</a>
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
      				<!-- <a href="javascript:void(0)" onclick="table2col()" >&lt;</a>
      				<a href="javascript:void(0)" onclick="" >&lt;&lt;</a> -->
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
      						<td><select id="tab2_logic">
      						<option value='no1' selected></option>
      						<option value="AND" >并且(AND)</option><option value="OR">或者(OR)</option></select></td>
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
         					<td onmouseover="showSelectedValue(this)">
         					<input id="param_value" name="param_value" type="text"/>
         					<select id="param_value_select" style="width: 150px;display:none;"></select></td>
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
      				<td  style="padding:10px;" valign="top">
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
              	<option selected value='no'></option>
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
   <td nowrap></td>
   <td nowrap>
       <select id="tab3_rightParen">
         <option value=""></option>
         <option value=")">)</option>
         <option value="))">))</option>
         <option value=")))">)))</option>
         <option value="))))">))))</option>
       </select>
     </td></tr>
     <tr><td colspan="8" align="right">
		<button onclick="addTab3Data();">添加条件</button>&nbsp;&nbsp;
     </td></tr>
  </table>
      				</td>
      			</tr>
      			<tr><td valign="top" style="padding:10px;">
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
<table width="100%" border="1" style="background-color: #dee6e9;;border-collapse:collapse;" align="center" cellpadding="2" cellspacing="0"> 
	  <tr>
		         	<td nowrap align="left" width="15%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;限制访问当月数据</td>
		         	<td nowrap align='left'  width="40%">
		         		<input type="radio" value="Y" name="is_month_data" id="month_data1" /><label for="month_data1">限制</label>
		         		<input type="radio" value="N" name="is_month_data" checked id="month_data2" /><label for="month_data2">不限制</label>
		         	</td>
		         	<td nowrap align="left" width="15%">&nbsp;&nbsp;&nbsp;&nbsp;访问时间间隔<input id='limitVisitPeriod' checked onFocus='this.blur();' type='checkbox' value='0' onclick="setVisitP();" /></td>
		         	<td nowrap align='left' width="30%"><input id='visit_period_day' size="5" type='text' value='7' />&nbsp;天</td>
	 </tr>
	 <tr><td height="12" bgColor="white" colspan="4"></td></tr>
	 <tr>
		         	<td nowrap  align="left" width="150px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;限定星期</td>
		         	<td colspan=3 id="weekTd" ></td>
	  </tr>
	  <tr>
		 <td nowrap  align="left" width="15%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;访问时间<input id='limitTimeBtn' onFocus='this.blur();' type='checkbox' value='0' onclick="setlimitTime();"  /></td>
		 <td nowrap align='left'  width="40%" >
		 <input id="datesStr" readonly type="hidden"  value="点击右侧按钮选择时间" style="width:80%;" /> 
		 <span id="dates" style="width:75%;">(注：重叠的时间段将被自动合并)</span>
		 <div id="Date_zone" style="background:#fcfcfc;z-index:9934;display:none;position:absolute; left:400px; top: 400px;border:1px solid #069;"  >
		      	 开始日期：<input id="stime" readonly type="text" value='00:00' size='6' />至
		      	结束日期：<input id="etime" readonly type="text" value="24:00" size='6' />
		      	 <br />
		    	<input type="button" value="确定" onclick="submitDate()" /><input type="button" value="关闭" onclick="cancelDate()" />
		    	<br />
		    </div>
		 
		<!--  从 <input id="stime" readonly="true" type="text" value='00:00' size='6' />&nbsp;到 &nbsp; <input id="etime" readonly="true" type="text" value="24:00" size='6' />
		  --><input id="addDateBtn" type="button"  onclick="showDate()" value="添加"/></td>
	 	<td nowrap align="left" width="15%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;每天访问次数<input id='limitNumberBtn' onFocus='this.blur();' type='checkbox' value='0' onclick="setnumber();"  /></td>
		 <td nowrap  align='left'   width="30%">每天&nbsp;<input id="limitNumber"  type="text"  size='6' />&nbsp;次</td>
	 </tr>
	 <tr>
		         	<td nowrap align="left" width="15%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;每次访问总数<input id='limitNumPerBtn' onFocus='this.blur();' type='checkbox' value='0' onclick="setnumPer();"  /></td>
		         	<td nowrap align='left'  width="40%">每次&nbsp;<input id="limitNumPer" disabled="disabled" name="limitNumPer"  type="text"  size='6' />&nbsp;条</td>
		         	<td nowrap align="left" width="15%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;每天总数<input id='limitTotalBtn' onFocus='this.blur();' type='checkbox' value='0' onclick="setTotal();" /></td>
		         	<td nowrap align='left' width="30%">每天&nbsp;<input id='limitTotal' type="text"  size='6' />&nbsp;条</td>
	 </tr>
				</table>
</td></tr>
				<tr><td style="text-align:right; padding-top:5px; padding-right: 10px; background-color: white;"><span style="color:red;" id="tab4_error_msg">&nbsp;</span>&nbsp;&nbsp;<button onclick="addLimitData();">添加限制条件</button></td></tr>
				<tr>
					<td><div id="tab4_limit_div"></div></td>
				</tr>

</table></div>				
      	</dd>
      	<!-- 第四个标签页结束 -->
    <dd>
    <div>
      		<table class="dd_table" border="1" style="table-layout:fixed;" cellpadding="3" cellspacing="0" width="100%" align="center">
		<tr style="padding-top: 5px;">
      			<td> 
    <textarea readonly style="width:100%;height:200px;" id="sql_last"></textarea></td></tr></table></div>
    </dd>
    </dl>
			</div>
		</td>
	</tr>
</table>
