var tab_index = 0;


function limConTdWidth(){
$("#tab2_cond_table_div  tr").each(function(){
var s=$(this).children("td").eq(5).width(200);
});
}

function deleteReturn(){
	alert(tab_index)
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
/* setTimeout(function(){
	getColumnsByTable($(tab1_table_all_div).find("option:first").val());
	$(tab1_table_all_div).find("option:first").attr("selected", true);
},1000); */

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





function getColumnsByTable(share_table_id) {
	alert(share_table_id)
	$.ajax({
		type : "post",
		url : "../../advQuery/getColumn.do?tableId=" + share_table_id,
		async : false,
		dataType : "json",
		success : function(data) {
			var datas = data.data[0].data;
			tab1_col_all = datas;//配置表中的数据列
			var table_Col_All = {
				data : datas,
				onClick : function(event, key) {
					selectCols(key, 'tab1_col_all_div',datas.length,share_table_id);
				}
			}
			//$('#tab1_col_all_div').dataSelector('destroy')
			$('#tab1_col_all_div').dataSelector(table_Col_All);
		}
	});
	
}
