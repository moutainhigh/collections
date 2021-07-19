var tab1_table_all = new Array; //第一个标签页中，存放数据表的列表数据
var tab1_col_all = new Array; // 存放数据项的列表数据，在三个标签页中都有使用
var tab2_table = new Array; //第二个标签页中，存放数据表的列表数据
var tab1_param = new Array;	//第一个标签页中继承自接口的参数

var interface_sql = '';	//继承自接口的sql

var tab1_table_all_div = "#tab1_table_all_div"; //第一个标签页中，存放数据表列表的容器id
var tab1_col_all_div = "#tab1_col_all_div"; //第一个标签页中，存放数据项列表的容器id
var tab1_col_selected_div = "#tab1_col_selected_div"; //第一个标签页中，存放选择数据项的表格的容器id
var tab2_cond_div = "#tab2_cond_table_div"; //第二个标签页中，存放条件的表格的容器id
var tab2_interface_div = "#tab2_interface_div";	//第二个标签页中，存放继承接口条件的表格的容器id
var tab2_table_div = "#tab2_table_all_div"; //第二个标签页中，存放数据表列表的容器id
var tab2_col_div = "#tab2_col_all_div"; //第二个标签页中，存放数据项列表的容器id
var tab2_col_select = "#tab2_col_all_div_id"; //第二个标签页中，存放数据项的select id
var tab2_error_msg = "#tab2_error_msg"; //第二个标签页中，显示错误信息的容器id
var tab2_param = "#param_value_select"; //第二个标签页中，存放用户输入值的容器id
var tab3_table_div = "#tab3_table_all_div"; //第三个标签页中，存放数据表列表的容器id
var tab3_col_div = "#tab3_col_all_div"; //第三个标签页中，存放数据项列表的容器id
var tab3_col_select = "#tab3_col_all_div_id"; //第三个标签页中，存放数据项的select id
var tab3_param_div = "#tab3_param_div"; //第三个标签页中，存放输入参数的表格的容器id
var tab3_error_msg = "#tab3_error_msg"; //第三个标签页中，显示错误信息的容器id
var tab4_limit_div = "#tab4_limit_div"; //第四个标签页中，存放服务限制条件的表格的容器id
var tab4_error_msg = "#tab4_error_msg"; //第四个标签页中，显示错误信息的容器id

var limitWeek = new Array;
var limitCache = {
  'limitTime': '0',
  'limitNumber': '0',
  'limitWeek': '0',
  'limitTotal': '0',
  'datesStr':'',
  'stime': '00:00',
  'etime': '24:00',
  'number': 0,
  'limitNumPer': 0,
  'total': 0,
  'limitWeekArray': []
}
var weeks = ['x', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六', '星期日'];

var tb_operators = {
  "number" : [{
    "key" : "=",
    "title" : "等于",
    "operator" : " = #@# "
  },{
    "title": '不等于',
    "key": '<>',
    "operator" : " <> #@# "
  },{
    "key" : "<",
    "title" : "小于",
    "operator" : " < #@# "
  },{
    "key" : "<=",
    "title" : "小于等于",
    "operator" : " <= #@# "
  },{
    "key" : ">",
    "title" : "大于",
    "operator" : " > #@# "
  },{
    "key" : ">=",
    "title" : "大于等于",
    "operator" : " >= #@# "
  }
  ],
  "varchar" : [
  {
    "key" : "=",
    "title" : "等于",
    "operator" : " = \'#@#\' "
  },{
    "key" : "<>",
    "title" : "不等于",
    "operator" : " <> \'#@#\' "
  },{
    "key" : "<",
    "title" : "小于",
    "operator" : " < \'#@#\' "
  },{
    "key" : "<=",
    "title" : "小于等于",
    "operator" : " <= \'#@#\' "
  },{
    "key" : ">",
    "title" : "大于",
    "operator" : " > \'#@#\' "
  },{
    "key" : ">=",
    "title" : "大于等于",
    "operator" : " >= \'#@#\' "
  },{
    "key" : "like",
    "title" : "以...开始",
    "operator" : " like \'#@#%\' "
  },{
    "key" : "like",
    "title" : "以...结束",
    "operator" : " like \'%#@#\' "
  },{
    "key" : "like",
    "title" : "包含(LIKE)",
    "operator" : " like \'%#@#%\' "
  }
  ,{
    "key" : "IN",
    "title" : "包括(IN)",
    "operator" : " in (\'#@#\')"
  }
  ,{
    "key" : "is null",
    "title" : "为空",
    "operator" : " is null "
  },{
    "key" : "is not null",
    "title" : "不为空",
    "operator" : " is not null "
  }
  ],
  "date": [{
    "title": '等于',
    "key": '=',
    "operator" : "date = \'#@#\' "
  }, {
    "title": '不等于',
    "key": '<>',
    "operator" : "date \'#@#\' "
  }, {
    "title": '大于',
    "key": '>',
    "operator" : "date \'#@#\' "
  }, {
    "title": '大于等于',
    "key": '>=',
    "operator" : "date \'#@#\' "
  }, {
    "title": '小于',
    "key": '<',
    "operator" : "date \'#@#\' "
  }, {
    "title": '小于等于',
    "key": '<=',
    "operator" : "date \'#@#\' "
  }]
};

var tab_index = 0; //当前显示的tab序号
//定义的数据项列表的双击函数
var dblfunction = function(event, key) {
  if (key == "undefined") {
    return false;
  }
  $(this).find("option.selected").removeClass("selected");
  $(this).find("option").each(function() {
    if ($(this).attr("id") == key) {
      $(this).addClass("selected");
    }
  });
  getColumnsByTable(key);

};
//根据接口ID查询数据表列表
var init_flag;
function getTableListByInterface1(flag) {
	init_flag = flag;
  var page = new pageDefine("/txn40200007.ajax", "根据接口ID获取数据表列表");
  var interface_id = getFormFieldValue('record:interface_id');
  if (interface_id && interface_id != '') {
    page.addValue(interface_id, "select-key:interface_id");
    page.callAjaxService('setTableNames');
  }
}

function getTableListByInterface2() {
  var page = new pageDefine("/txn40200007.ajax", "根据接口ID获取数据表列表");
  var interface_id = getFormFieldValue('record:interface_id2');
  if (interface_id && interface_id != '') {
    page.addValue(interface_id, "select-key:interface_id");
    page.callAjaxService('setTableNames');
  }
}
//根据接口ID查询数据表列表

function getTableListByInterface() {
		//清空原有数据
	$(tab1_col_selected_div).tablet("removeAllData");
//	$(tab1_col_all_div).find("option").remove();
	$(tab2_cond_div).tablet("removeAllData");
	$(tab3_param_div).tablet("removeAllData");
	$(tab4_limit_div).tablet("removeAllData");
  var page = new pageDefine("/txn40200007.ajax", "根据接口ID获取数据表列表");
  var interface_id = getFormFieldValue('record:interface_id');
  if (interface_id && interface_id != '') {
    page.addValue(interface_id, "select-key:interface_id");
    page.callAjaxService('setTableNames');
  }
}
//设置数据表数据

function setTableNames(errCode, errDesc, xmlResults) {
  if (errCode != '000000') {
    //alert('处理错误['+errCode+']==>'+errDesc);
    return;
  }
  var datas = _getXmlNodeValues(xmlResults, "record:dataString");
  interface_sql = _getXmlNodeValues(xmlResults, "record:interfacesql");
  tab1_param =  _getXmlNodeValues(xmlResults, "record:tableParam");
  tab1_param = tab1_param[0];
//  tab1_param = tab1_param.replaceAll("null", "''");	//替换null为空‘’ 
  tab1_param = eval("(" + ("{data:" + tab1_param+"}") + ")");
  tab1_table_all = eval('(' + datas + ')');
  
  //复制服务相关
  var services = _getXmlNodeValues(xmlResults, "record:sql_one");
  services = eval('('+ services +')');
  var sel_service = document.getElementById("record:service");
  if(sel_service){
	  $el = $(sel_service);
	  $el.empty();
	  $el.append("<option>请选择...</option>");
	  for(var ii=0; ii<services.length; ii++){
		  $el.append("<option value='"+services[ii].service_no+"' id='"+services[ii].service_id+"'>"
				  +services[ii].service_name+"</option>");
	  }
	 // $el.width($el.parent().width());
	  //$el.find("option:first").text("请选择复制对象");
  }
  
  //初始化数据表数据
  var options = {
    multiple: false,
    data: tab1_table_all,
    size: 10,
    onClick: dblfunction
  }
  $(tab1_table_all_div).dataSelector("destroy");
  $(tab1_table_all_div).dataSelector(options);
  var share_table_id = $(tab1_table_all_div).find("option:first").val();
  $(tab1_table_all_div).find("option:first").attr("selected", true);
  getColumnsByTable(share_table_id);
	var data_tmp = [{
			"logic":"逻辑条件",
          	"leftParen":"括号",
          	"table":"表名",
       		"column":"字段",
          	"paren":"条件",
			"param_value":"值",
            "rightParen":"括号"
        }];
	var opts = {
		data: data_tmp,
		addDelete: false
	};
	$(tab2_interface_div).tablet(opts);
	if(tab1_param.data.length > 0){
		$("#old_condition").show();
		$("#old_condition1").show();
		$(tab2_interface_div).tablet("removeAllData");
		$(tab2_interface_div).tablet("addData", tab1_param.data);
	}else{
		$("#old_condition").hide();
		$("#old_condition1").hide();
	}
	if(!init_flag){
		var data = $(tab1_col_selected_div).tablet('getAllData');
		
		if(data != '{data:[]}'){
			if(confirm('是否要清空原来选择的数据项？(点击【确定】清空，【取消】保留。若选择【保留】请务必确认已选数据项在新接口中存在，否则将造成服务调用失败。本操作不可恢复！！！)')){
				$(tab1_col_selected_div).tablet("removeAllData");
			}
		}
		
	}
}

function setListByService(){
	var sel_service = document.getElementById("record:service");
	if(sel_service){
	  $el = $(sel_service);
	  $el = $el.find("option:selected");
	  var page = new pageDefine("/txn40200014.ajax", "根据数据表获取字段列表");
	  page.addValue($el.attr("id"), "primary-key:service_id");
	  page.callAjaxService('copyService');
  }
}

function copyService(errCode, errDesc, xmlResults) {
  if (errCode != '000000') {
    //alert('处理错误['+errCode+']==>'+errDesc);
    return;
  }
  var jsondata = _getXmlNodeValues(xmlResults, "record:jsoncolumns");
  if(jsondata[0] == ''){
	  alert("获取复制服务数据失败.\n请检查是否有该服务相关文件.");
	  return;
  }
//  alert(jsondata[0]);
  var jsondata = eval('('+ jsondata[0] +')');
  var typedata = jsondata.columns;
  $("#tab1_col_selected_div").tablet("removeAllData");
  $("#tab1_col_selected_div").tablet("addData", typedata);
  
  typedata = jsondata.conditions;
  $("#tab2_cond_table_div").tablet("removeAllData");
  $("#tab2_cond_table_div").tablet("addData", typedata);

  typedata = jsondata.params;
  $("#tab3_param_div").tablet("removeAllData");
  $("#tab3_param_div").tablet("addData", typedata);
  
  typedata = _getXmlNodeValues(xmlResults, "record:limit_data");
  typedata = eval('(' + typedata + ')');
  $("#tab4_limit_div").tablet("removeAllData");
  $("#tab4_limit_div").tablet("addData", typedata.data);
}

//根据数据表获取字段列表
function getColumnsByTable(share_table_id) {
  var page = new pageDefine("/txn40200008.ajax", "根据数据表获取字段列表");
  page.addValue(share_table_id, "select-key:share_table_id");
  page.callAjaxService('setCloumnNames');
}
//设置数据项列表

function setCloumnNames(errCode, errDesc, xmlResults) {
  if (errCode != '000000') {
    //alert('处理错误['+errCode+']==>'+errDesc);
    return;
  }
  var datas = _getXmlNodeValues(xmlResults, "dataString");
  
  tab1_col_all = eval('(' + datas + ')');
  
  //初始化数据表数据
  if (tab_index == 0) { //如果是第一个标签页
    var options = {
      multiple: true,
      data: tab1_col_all,
      size: 10,
      onClick: function(event, key) {
        if (typeof key == "undefined") return false;
        col2table(key);
        var col_selected = $(tab1_col_all_div).dataSelector("getSelectedNodes");
        for (var x in col_selected) {
          $(tab1_col_all_div).dataSelector("removeSelectedNodes", x);
        }
      }
    };
    //检查一下是不是第一次初始化，确保不报错
    options.onClick = null;
    options.onDblclick = function(event, key) {
        if (typeof key == "undefined") return false;
        col2table(key);
        var col_selected = $(tab1_col_all_div).dataSelector("getSelectedNodes");
        for (var x in col_selected) {
          $(tab1_col_all_div).dataSelector("removeSelectedNodes", x);
        }
      };
    if ($(tab1_col_all_div).find("select").length > 0) {
      $(tab1_col_all_div).dataSelector('destroy');
    }
    $(tab1_col_all_div).dataSelector(options);
  } else if (tab_index == 1) { //如果是第二个标签页
    var options = {
      multiple: false,
      data: tab1_col_all, //第二个标签页的数据项列表
      size: 1
    };
    if ($(tab2_col_div).find("option").length > 0) {
      $(tab2_col_div).dataSelector('destroy');
    }
      $(tab2_col_div).dataSelector(options);
    $(tab2_col_select+" option").each(function(){
    	var $this = $(this);
    	var key = $(this).val();
    	for(var ii=0; ii<tab1_col_all.length; ii++){
    		var x = tab1_col_all[ii];
    		if(x.key == key){
    			$this.data("col_type", x.dataitem_type);
    			$this.data("code_table", x.code_table);
    			break;
    		}
    	}
    });
    
    $(tab2_col_select).change(function(){
    	setOperator(tab2_col_select, '#paren');
    	var code_table = $(this).find("option:selected").data("code_table");
    	$("#param_value").unbind("click");
    	if(code_table && code_table != ''){
    		$("#param_value").hide();
    		$("#param_value_select").show();
  			setParamSelect(code_table, tab2_param);
    	}else{
    		$("#param_value_select").empty();
    		$("#param_value_select").hide();
    		var col_type = $(this).find("option:selected").data("col_type");
    		if(col_type == '1'){
    			$("#param_value").val('');
    			$("#param_value").show();
    		}else if(col_type == '2'){
    			$("#param_value").val('');
    			$("#param_value").show();
    			$("#param_value").bind("click", function(){
    				calendar($("#param_value")[0], 1)
    			});
    		}else if(col_type == '3'){
    			$("#param_value").val('');
 		   		$("#param_value").show();
    		}		
    	}
    });
  } else if (tab_index == 2) { //如果是第三个标签页
    var options = {
      multiple: false,
      data: tab1_col_all, //第三个标签页的数据项列表
      size: 1
    };
    if ($(tab3_col_div).find("option").length > 0) {
      $(tab3_col_div).dataSelector('destroy');
    }
      $(tab3_col_div).dataSelector(options);
    
    $(tab3_col_select+" option").each(function(){
    	var $this = $(this);
    	var key = $(this).val();
    	for(var ii=0; ii<tab1_col_all.length; ii++){
    		var x = tab1_col_all[ii];
    		if(x.key == key){
    			$this.data("col_type", x.dataitem_type);
    			$this.data("code_table", x.code_table);
    			break;
    		}
    	}
    });
  	$(tab3_col_select).change(function(){
    	var col_type = $(this).find("option:selected").data("col_type");		
    	var targetId = '#tab3_paren';
    	var type = 'varchar';
    	if(col_type == '3'){
    		$("#tab3_col_type").attr("value", col_type);
    		type = 'number';
    	}else if(col_type == '2'){
    		$("#tab3_col_type").attr("value", col_type);
    		type = 'date';
    	}else{
    		$("#tab3_col_type").attr("value", "1");
    	}	
    	$(targetId).empty();
	for(var ii=0; ii<tb_operators[type].length; ii++){
		var oper = tb_operators[type][ii];
		$(targetId).append("<option value='"+oper.key+"'>"+oper.title+"</option>");
		$(targetId).find("option:last").data("operator",oper.operator);
	 }
	$(targetId).find("option")[0].selected = true;
//	$(targetId).find("option").each(function(){
	//	console.log($(this).data("operator"));
//	});
    });
  }
  
  var columns = $(tab1_col_selected_div).tablet("getAllData");
  columns = eval("("+columns+")");
  for(var cnum=0;cnum<columns.data.length;cnum++)
  {
  	  for (var tnum = 0; tnum < tab1_col_all.length; tnum++) {
	  	if(tab1_col_all[tnum].key == columns.data[cnum].column.id)
		{
			//delete tab1_col_all[tnum];
			//tab1_col_all.splice(tnum,1);
			$(tab1_col_all_div).find("#"+tab1_col_all[tnum].key).remove();
			break;
		}
	  }
  }
  
}

function setParamSelect(code_table, targetId) {
  $.ajax({
    			type: "post",
    			url: "/txn20301026.ajax?attribute-node:record_page-row=10000&select-key:code_table="+code_table,
    			cache: true,
           		async: false,
    			dataType:'xml',
    			success: function(xmlResults) {
    				if (xmlResults.selectSingleNode("//context/error-code").text != "000000"){
	  					alert(xmlResults.selectSingleNode("//context/error-desc").text);
	  					return false;
	  				}else{
	  					$(targetId).empty();
	  					$(xmlResults).find("record").each(function(){
	  						$(targetId).append('<option value="'+$(this)[0].selectSingleNode("jcsjfx_dm").text+'">' +$(this)[0].selectSingleNode("jcsjfx_dm").text + '---' +$(this)[0].selectSingleNode("jcsjfx_mc").text+'</option>');
	  					});
						$(targetId).find("option:first").selected = true;
	  				}
			    }
  });
}

//从字段列表选择字段到表格

function col2table(key) {
//	alert(key);
  var selected_col = null;
  var rowData = [];
  //  var sort = $(tab1_col_selected_div).tablet("getDataLength");
  for (var ii = 0; ii < tab1_col_all.length; ii++) {
    if (tab1_col_all[ii].key == key) {
      selected_col = tab1_col_all[ii];
      rowData = [{
        //        "sort":sort,
        "table": {
          "id": selected_col.share_table_id,
          "name_en": selected_col.table_name_en,
          "name_cn": selected_col.table_name_cn
        },
        "column": {
          "id": selected_col.key,
          "name_en": selected_col.dataitem_name_en,
          "name_cn": selected_col.title
        },
        "alias": "",
        "column_type": selected_col.codename,
        "column_length": selected_col.dataitem_long
      }];
      break;
    }
  };

  $(tab1_col_selected_div).tablet("addData", rowData);
}

function addMultiData() {
  var selected_col = null;
  var rowData = [];
  var selected_cols = $(tab1_col_all_div).dataSelector("getSelectedNodes");
  for (var ii = 0; ii < selected_cols.length; ii++) {
    for (var jj = 0; jj < tab1_col_all.length; jj++) {
      if (selected_cols[ii].key == tab1_col_all[jj].key) {
        selected_col = selected_cols[ii];
        rowData.push({
          //          "sort":sort,
          "table": {
            "id": selected_col.share_table_id,
            "name_en": selected_col.table_name_en,
            "name_cn": selected_col.table_name_cn
          },
          "column": {
            "id": selected_col.key,
            "name_en": selected_col.dataitem_name_en,
            "name_cn": selected_col.title
          },
          "alias": "",
          "column_type": selected_col.codename,
          "column_length": selected_col.dataitem_long
        });
        break;
      } else {
        continue;
      }
    }
  }

  $(tab1_col_selected_div).tablet("addData", rowData);
  for (var ii = 0; ii < selected_cols.length; ii++) {
    $(tab1_col_all_div).dataSelector("removeNodes", selected_cols[ii].key);
  }
}

//插入一条查询条件

function addCondData() {
  var c0 = $("#tab2_logic").find(":selected");
  var c1 = $("#leftParen").find(":selected");
  var c2 = $(tab2_table_div).dataSelector("getSelectedNodes");
  var c3 = $(tab2_col_div).dataSelector("getSelectedNodes");
  var c4 = $("#paren").find(":selected");
  
  var c5_1 = $("#param_value_select option:selected");
  var c5 = $("#param_value"); 
   if(c5.css("display")!= "none" && (c4.val().indexOf('null')==-1) &&  c5.val().trim()==''){
		$(tab2_error_msg).html("参数值不能为空,请填写参数值");
		$("#param_value").focus();
		return false;
  	}
  var col_type = $(tab2_col_select).find("option:selected").data("col_type");
  if(col_type && '3'==col_type){
  	if(isNaN(c5.val())){
  		$(tab2_error_msg).html("当前选择数据项为数字类型，值必须为数字 ");
  		$("#param_value").val(""); 
  		return;
  	}
  }
  if($("#param_value_select").css("display")!='none'){
  	if($('#param_value_select').attr('disabled')){
  		c5_1.val('').text('');
  	}
  	c5 = c5_1;
  }
  var c6 = $("#rightParen").find(":selected");
  var tmp_cols = $(tab2_col_div).dataSelector("getAllNodes");
  var data2table = [];
  var sort = $(tab2_cond_div).tablet("getDataLength");
  for (var ii = 0; ii < tmp_cols.length; ii++) {
    if (tmp_cols[ii].key == c3[0].key) {
      data2table = {
        //       "sort": sort,
        "logic":  {"value":c0.val(), "name_cn":c0.text()},
        "leftParen": c1.val(),
        "table": {
          "id": tmp_cols[ii].share_table_id,
          "name_en": tmp_cols[ii].table_name_en,
          "name_cn": tmp_cols[ii].table_name_cn
        },
        "column": {
          "id": tmp_cols[ii].key,
          "name_en": tmp_cols[ii].dataitem_name_en,
          "col_type": tmp_cols[ii].dataitem_type,
          "name_cn": tmp_cols[ii].title
        },
        "paren": {"value":c4.val(), "name_cn":c4.text(), "operator": c4.data("operator")},
        "param_value":  {"value":c5.val(), "name_cn":c5.text()=='' ? c5.val() : c5.text()},
        "rightParen": c6.val()
      };
      break;
    }
  }
 $(tab2_error_msg).html('');
 var datatmp = $("#tab2_cond_table_div").tablet("getAllData");
 datatmp = eval('(' + datatmp + ')');
 if(datatmp.data.length == 0){
	 data2table.logic.name_cn = ' ';
	 data2table.logic.value = ' ';
 }
  var flag = $(tab2_interface_div).tablet("checkData", data2table);
  if(!flag){
  	$(tab2_cond_div).tablet("addData", data2table);
  }else{
  	var re = confirm("基础接口已经配置相同的条件,确认添加吗?");
  	if(re){
  		$(tab2_cond_div).tablet("addData", data2table);
  	}
  }
   if($(tab2_cond_div).find("table tr").length==2){
 	$("#tab2_logic").attr("value", "AND");
 }
 limConTdWidth();
 datatmp = $("#tab2_cond_table_div").tablet("getAllData");
 datatmp = eval('(' + datatmp + ')');
 if(datatmp.data.length > 0){
	  $("#tab2_logic").attr("disabled", false);
	  $("#tab2_logic option[value='no1']").remove();
 }
}

//插入一条配置输入参数记录

function addTab3Data(type) {
  var c1 = $('#tab3_cond option:selected').val();
  var c2 = $(tab3_col_div).dataSelector("getSelectedNodes");
  var c3 = $("#tab3_paren").find(":selected");
  var c4 = $("#tab3_param_value").val();
  var c5 = type;
  var c6 = $("#tab3_leftParen").find(":selected").val();
  var c7 = $("#tab3_rightParen").find(":selected").val();
  var c8 = $("#tab3_col_type").find("option:selected").val();
  var c8_1 = $("#tab3_col_type").find("option:selected").text();
  var tab3_table_data = [];
  var tmp_cols = $(tab3_col_div).dataSelector("getAllNodes");
  //  var sort = $(tab3_param_div).tablet("getDataLength");
	if(c1 == 'AND'){
		c5={"value": "AND", name_cn:"并且"};
	}else if(c1 == 'OR'){
		c5={"value": "OR", name_cn:"或者"};
	}else{
		c5={"value": " ", name_cn:" "};
	}
  for (var ii = 0; ii < tmp_cols.length; ii++) {
    if (tmp_cols[ii].key == c2[0].key) {
      tab3_table_data = [{
        "table": {
          "id": tmp_cols[ii].share_table_id,
          "name_en": tmp_cols[ii].table_name_en,
          "name_cn": tmp_cols[ii].table_name_cn
        },
        "column": {
          "id": tmp_cols[ii].key,
          "name_en": tmp_cols[ii].dataitem_name_en,
          "col_type": c8,
          "name_cn": tmp_cols[ii].title
        },
        "col_type": {
        	"type": c8,
        	"name_cn": c8_1
        },
        "paren": {"value": c3.val(), "name_cn": c3.text(), "operator": c3.data("operator")},
        "param_value": {"value": c4, "name_cn":c4},
        "logic": c5,
        "leftParen": c6,
        "rightParen": c7
      }];
    }
  }
  var tmpdata = $("#tab3_param_div").tablet("getAllData");
	tmpdata = eval('(' + tmpdata +')');
	if(tmpdata.data.length == 0){
		tab3_table_data[0].logic.value = ' ';
		tab3_table_data[0].logic.name_cn = ' ';
	}
  $(tab3_param_div).tablet("addData", tab3_table_data);
  var datatmp = $(tab3_param_div).tablet("getAllData");
  datatmp = eval('(' + datatmp + ')');
  if(datatmp.data.length > 0){
	  $("#tab3_cond option[value='no']").remove();
	  $("#tab3_cond").attr("disabled", false);
  }
}

//获取配置好的服务的所有信息，包括 查询数据项、 查询条件 和 配置输入参数

function getPostData() {
	if(!checkItem()){
		return;
	}
	var is_month_data = $("input[name='is_month_data']:checked").val();
	if(typeof is_month_data === 'undefined'){
		is_month_data = 'N';
	}
  	var visit_period = 0;
  	if($("#limitVisitPeriod").attr('checked')){
  		visit_period = $('#visit_period_day').val();
  		 var reg = new RegExp("^\\d{1,3}$");
      if(!reg.test(visit_period)){
    	  $('#visit_period_day').val('');
          alert("访问时间间隔请填写3位以内数字");
          return;
      }
	}
  var columns = $(tab1_col_selected_div).tablet("getAllData");
  columns = eval("("+columns+")");
  if (columns.data.length == 0) {
    alert("没有配置结果集");
    return false;
  }
  var conditions = $(tab2_cond_div).tablet("getAllData");
  conditions = eval("("+conditions+")");
  if (conditions.data.length == 0) {
    var re = confirm("没有配置查询条件,确认继续吗?");
    if (!re) return false;
  }
  
  var limit_data1 = $(tab4_limit_div).tablet("getAllData");
  limit_data1 = eval("("+limit_data1+")");
    if (limit_data1.data.length == 0) {
    var re = confirm("没有配置访问限制条件,确认继续吗?");
    if (!re) return false;
  }
  var params = $(tab3_param_div).tablet("getAllData");
  params = eval("("+params+")");
  var all_data = {
    "columns": columns.data,
    "conditions": conditions.data,
    "params": params.data
  };
  //console.log(obj2str(all_data));
  //获取最终的sql语句
  var sql_str = getSql(all_data);
  if(typeof(sql_str)=='undefined' || sql_str.trim() == ''){
  	return;
  }
  sql_str_test = sql_str;
  //console.log("sql = " + sql_str_test);
  sql_str_test = sql_str_test.replace(/\+/g, "%2B").replace(/%/g, "%25");
  if(sql_str_test.indexOf('to_date') == -1){
	sql_str_test = sql_str.replace("（参数值）", "1");
  }else{
	sql_str_test = sql_str.replace(/（参数值）/ig, "2013-04-01");
  }
  //console.log("after replace: " + sql_str_test);
  var url = "/testsql?testsql=" + sql_str_test;
  url = encodeURI(url);
  var flag = false;
  //  sql_str = getSql(data_sample);
  //console.log("url: "+url);
  //验证sql语句是否合法
  $.ajax({
    type: "post",
    url: url,
    async: false,
    success: function(xmlResults) {
      var result = xmlResults.selectSingleNode("/results/sql").text;
      if (result == "false") {
        alert("sql语句验证失败");
        flag = false;
      } else {
        flag = true;
      }
    }
  });
  if(flag) {
  	var str1 = obj2str(all_data);
//	var str1 = "{ \"columns\": "+ columns.data + ","+ " \"conditions \": " +conditions.data+"," +" \"params\"" +": "+ params.data+"}";
  	//alert(str1);
  	str1 = str1.replace(/%25/g, "%");
  	//.replace(/\\'/g,"'")
  	//alert(str1);
    var dataarry = [str1, sql_str, obj2str(limit_data1), is_month_data, visit_period];
    return dataarry;
  }
}

function obj2str(o) {
  var r = [];
  if (typeof o == "string") {
    return "\"" + o.replace(/([\'\"\\])/g, "\\$1").replace(/(\n)/g, "\\n").replace(/(\r)/g, "\\r").replace(/(\t)/g, "\\t") + "\"";
  }
  if (typeof o == "object") {
    if (!o.sort) {
      for (var i in o) {
        r.push(i + ":" + obj2str(o[i]));
      }
      if ( !! document.all && !/^\n?function\s*toString\(\)\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/.test(o.toString)) {
        r.push("toString:" + o.toString.toString());
      }
      r = "{" + r.join() + "}";
    } else {
      for (var i = 0; i < o.length; i++) {
        r.push(obj2str(o[i]))
      }
      r = "[" + r.join() + "]";
    }
    return r;
  }
  return o.toString();
}

function getSql(data) {
  //尝试拼出sql语句
  if (typeof data == 'undefined') return false;
  var all_cols = data.columns;
  var cols_sql_str = '';
  var table_tmp = '';
  var sqltmp = '';
  if (interface_sql && interface_sql.length > 0) {
    sqltmp = interface_sql[0].toString();
  }
  var from_table = new Array;
  //拼字段
  for (var ii = 0; ii < all_cols.length; ii++) {
    var tmp = all_cols[ii];
    var tmp_str = '';
    tmp_str += tmp.table.name_en + '.' + tmp.column.name_en;
    if(tmp.alias.trim() != ""){
    	tmp_str += ' as ' + tmp.alias;
    }
    tmp_str +=  ' ,';
    cols_sql_str += tmp_str;
  }
  if (cols_sql_str.lastIndexOf(",") == cols_sql_str.length - 1) {
    cols_sql_str = cols_sql_str.substr(0, cols_sql_str.length - 1);
  }
  sqltmp = sqltmp.replace(/SELECT(\s)+\*/i, "SELECT " + cols_sql_str);
	//拼查询条件
  table_tmp = '';
  all_cols = data.conditions;
  for (var ii = 0; ii < all_cols.length; ii++) {
  /*var tmp_1 = '';
  	if(ii==0){
  		if(typeof(tmp.logic) == 'undefined'){
  			tmp_1 = 'AND';
  		}else{
  			alert(tmp.logic.value);
  		}
  	}*/
    var tmp = all_cols[ii];
    var opera = tmp.paren;
	var tb_col = tmp.table.name_en + "." + tmp.column.name_en;
    //console.log(opera.operator);
    var value = tmp.param_value.value.trim().replace(/'/g, '');
    //alert(value + " = = " + opera.operator);
    if(opera.operator.indexOf('#@#')>-1){
    	opera.operator = opera.operator.replace(/#@#/i, value);
		if(opera.operator.indexOf('date') > -1){
			opera.operator = opera.operator.replace(/date/i, '');
			opera.operator = ' ' + opera.value + " to_date('" + value + "','YYYY-MM-DD HH24:mi:ss') ";
//			tb_col = "to_date("+tmp.table.name_en + "." + tmp.column.name_en+",'YYYY-MM-DD HH24:mi:ss')";
			if(opera.value.trim() != '=' && opera.value.trim() != '<>'){
				tb_col = "to_date("+tmp.table.name_en + "." + tmp.column.name_en+",'YYYY-MM-DD HH24:mi:ss')";
			}else{
				tb_col = "to_date(substr("+tmp.table.name_en + "." + tmp.column.name_en+",0,10),'YYYY-MM-DD HH24:mi:ss')";
			}
		}
    }else{
		if (opera.operator.indexOf('to_date') > -1) {
			opera.operator = opera.operator.replace(/date/i, '');
			opera.operator = ' ' + opera.value + " to_date('" + value + "','YYYY-MM-DD HH24:mi:ss') ";
//			tb_col = "to_date(" + tmp.table.name_en + "." + tmp.column.name_en + ",'YYYY-MM-DD HH24:mi:ss')";
			if(opera.value.trim() != '=' && opera.value.trim() != '<>'){
				tb_col = "to_date("+tmp.table.name_en + "." + tmp.column.name_en+",'YYYY-MM-DD HH24:mi:ss')";
			}else{
				tb_col = "to_date(substr("+tmp.table.name_en + "." + tmp.column.name_en+",0,10),'YYYY-MM-DD HH24:mi:ss')";
			}
		}
	}
    var tmp_str = tmp.logic.value + " " + tmp.leftParen + " " + tb_col + " " + opera.operator  + " " + tmp.rightParen;
    table_tmp += tmp_str;
    tmp_1 = '';
  }
  sqltmp = table_tmp.length > 0 ? ( (sqltmp.indexOf('WHERE')>-1 ? (sqltmp+' ') : (sqltmp + ' WHERE ')) + (' AND ('+table_tmp+')')) : (sqltmp+' ');
	//拼用户输入参数
  table_tmp = '';
  tb_col = '';
  all_cols = data.params;
  for (var ii = 0; ii < all_cols.length; ii++) {
    var tmp = all_cols[ii];
    var opera = tmp.paren;
    var value = tmp.param_value.value.trim();
	tb_col = tmp.table.name_en + "." + tmp.column.name_en;
    if(opera.operator.indexOf('#@#')>-1){
    	opera.operator = opera.operator.replace(/#@#/i, value);
		if(opera.operator.indexOf('date') > -1){
			opera.operator = opera.operator.replace(/date/i, '');
			opera.operator = ' ' + opera.value + " to_date('" + value + "','YYYY-MM-DD HH24:mi:ss') ";
			if(opera.value.trim() != '=' && opera.value.trim() != '<>'){
				tb_col = "to_date("+tmp.table.name_en + "." + tmp.column.name_en+",'YYYY-MM-DD HH24:mi:ss')";
			}else{
				tb_col = "to_date(substr("+tmp.table.name_en + "." + tmp.column.name_en+",0,10),'YYYY-MM-DD HH24:mi:ss')";
			}
		}
    }
	else{
		if (opera.operator.indexOf('to_date') > -1) {
			opera.operator = opera.operator.replace(/date/i, '');
			opera.operator = ' ' + opera.value + " to_date('" + value + "','YYYY-MM-DD HH24:mi:ss') ";
			//tb_col = "to_date(" + tmp.table.name_en + "." + tmp.column.name_en + ",'YYYY-MM-DD HH24:mi:ss')";
			if(opera.value.trim() != '=' && opera.value.trim() != '<>'){
				tb_col = "to_date("+tmp.table.name_en + "." + tmp.column.name_en+",'YYYY-MM-DD HH24:mi:ss')";
			}else{
				tb_col = "to_date(substr("+tmp.table.name_en + "." + tmp.column.name_en+",0,10),'YYYY-MM-DD HH24:mi:ss')";
			}
		}
	}
    var tmp_str = tmp.logic.value + " " + tmp.leftParen + " " + tb_col + " " + opera.operator +  " " + tmp.rightParen;
    table_tmp += tmp_str;
  }
  sqltmp = table_tmp.length > 0 ? ( (sqltmp.indexOf('WHERE')>-1 ? (sqltmp+' ') : (sqltmp + ' WHERE ')) + (' AND ('+table_tmp+')')) : (sqltmp+' ');
  sqltmp = sqltmp.replace(/WHERE\s+(AND|OR)/ig, "WHERE ");	//where之后不能直接连接 and 或者 or
  sqltmp = sqltmp.replace(/\(\s*(AND|OR)\s*/ig, "(");
  //console.log("sql = "+sqltmp);
  if(sqltmp.indexOf('WHERE') == -1){
	  sqltmp += ' WHERE 1=1 ';
  }/*else{
	  sqltmp = sqltmp.replace(/WHERE/ig, "WHERE 1=1 ");  
  }*/
  sqltmp = sqltmp.replace(/'+/ig, "'");
  //alert(sqltmp);
  return sqltmp;
}


/**
  限制条件的相关函数
 **/
/*
 function setLimitWeek(){
  if( $('#limitWeekBtn').val()==0 ){
     $('#limitWeekBtn').val(1);
     $("input[name='weekDay']").attr('disabled',false);
  }else{
    $('#limitWeekBtn').val(0);
    $("input[name='weekDay']").attr('disabled',true);
  }
}
*/

function setlimitTime() {
  if ($('#limitTimeBtn').val() == 0) {
    $('#limitTimeBtn').val(1);
    //$("#stime").attr('disabled', false);
    //$("#etime").attr('disabled', false);
    $("#addDateBtn").attr('disabled',false);
  } else {
    $('#limitTimeBtn').val(0);
    $("#stime").val("00:00");
    $("#etime").val("24:00");
    //$("#stime").attr('disabled', true);
    //$("#etime").attr('disabled', true);
    $("#addDateBtn").attr('disabled',true);
  }
}

function setnumPer() {
  if ($('#limitNumPerBtn').val() == 0) {
    $('#limitNumPerBtn').val(1);
    $("input[name='limitNumPer']").attr('disabled', false);
    $("input[name='limitNumPer']").val('500');
  } else {
    $('#limitNumPerBtn').val(0);
    $("input[name='limitNumPer']").attr('disabled', true);
  }
}

function setnumber() {
  if ($('#limitNumberBtn').val() == 0) {
    $('#limitNumberBtn').val(1);
    $("#limitNumber").attr('disabled', false);
    $("#limitNumber").val('10');
  } else {
    $('#limitNumberBtn').val(0);
    $("#limitNumber").attr('disabled', true);
  }
}

function setTotal() {
  if ($('#limitTotalBtn').val() == 0) {
    $('#limitTotalBtn').val(1);
    $("#limitTotal").attr('disabled', false);
    $("#limitTotal").val('5000');
  } else {
    $('#limitTotalBtn').val(0);
    $("#limitTotal").attr('disabled', true);
  }
}

function setVisitP() {
	if($("#limitVisitPeriod").attr('checked') == 'checked'){
		$('#visit_period_day').val('7');
		$('#visit_period_day').attr('disabled', false);
	}else{
		$('#visit_period_day').val('0');
		$('#visit_period_day').attr('disabled', true);
	}
}

//验证是否正整数

function isPositiveInteger(str) {
  var regu = /^[1-9]\d*$/;
  return regu.test(str);
}

function limit_validate() {
  var flag = true;
  //在这里加验证
  var limitNumber = $('#limitNumber').val();
  var limitNumPer = $('#limitNumPer').val();
  var limitTotal = $('#limitTotal').val();
  //if ($("input[name='weekDay']:checked").length == 0) {
 //   alert("访问时间至少选择一天!");
  //  return false;
  //}
  //校验限定次数
  if ($('#limitNumberBtn').val() == "1") {
    if (limitNumber == "") {
      alert("【每天访问次数】不应为空值！");
      flag = false;
      return flag;
    } else {
      if (!isPositiveInteger(limitNumber)) {
        alert("【每天访问次数】应为正整数！");
        flag = false;
        return flag;
      }
    }
  }

  //校验限定没次总数
  if ($('#limitNumPerBtn').val() == "1") {
    if (limitNumPer == "") {
      alert("【每次访问总数】不应为空值！");
      flag = false;
      return flag;
    } else {
      if (!isPositiveInteger(limitNumPer)) {
        alert("【每次访问总数】应为正整数！");
        flag = false;
        return flag;
      }
    }
  }

  //校验限定条数
  if ($('#limitTotalBtn').val() == "1") {
    if (limitTotal == "") {
      alert("【每天访问总数】不应为空值！");
      flag = false;
      return flag;
    } else {
      if (!isPositiveInteger(limitTotal)) {
        alert("【每天访问总数】应为正整数！");
        flag = false;
        return flag;
      }
    }
  }
  var stime = $('#stime').val();
  var etime = $('#etime').val();
  if (stime != '' && etime != '') {
    if (parseInt(stime.replace(":", "")) > parseInt(etime.replace(":", ""))) {
      alert("【访问时间】结束时间不能早于开始时间");
      flag = false;
      return flag;
    }
  }
  return flag;
}

function initWeek() {
  var e = limitCache.limitWeekArray;
  var estr = "," + e.join(",") + ","
  var ds = "";
  if (limitCache.limitWeekArray && limitCache.limitWeekArray.length == 0) {
    //  ds="disabled"
  }

  var str = ""
  for (var j = 1; j < 8; j++) {

    if (estr.indexOf("," + j + ",") >= 0) {
      str += " <input type=checkbox onFocus='this.blur();' name='weekDay' value='" + j + "' " //+ds
      +
      "/> " + weeks[j] + "";
    } else {
      str += " <input type=checkbox onFocus='this.blur();' name='weekDay' value='" + j + "' " //+ds
      +
      " checked /> " + weeks[j] + "";
    }
  }
  $('#weekTd').html(str);
}

function initTime() {
  var e = limitCache.limitTime;
  var st = limitCache.stime;
  var et = limitCache.etime;
  if (e && e == '1') {
    $('#limitTimeBtn').attr('checked', true);
    $('#limitTimeBtn').val('1');
  } else if (e && e == "0") {
    $('#limitTimeBtn').attr('disabled', false);
    $('#addDateBtn').attr('disabled', true);
    //$('#stime').attr('disabled', true);
    //$('#etime').attr('disabled', true);
  }

  $('#stime').val(st);
  $('#etime').val(et);

  $('#stime').timepicker({
    showLeadingZero: true,
    showCloseButton: true,
    showNowButton: true
  });
  $('#etime').timepicker({
    showLeadingZero: true
  });

}

function initNumber() {
  var e = limitCache.limitNumber;
  if (e && e == "1") {
    $('#limitNumberBtn').attr('checked', true);
    $('#limitNumberBtn').val('1');
    $('#limitNumber').val(limitCache.number)
  } else if (e && e == "0") {
    $('#limitNumber').attr('disabled', true);
  }
}

function initNumPer() {
  var e = limitCache.limitNumPer;
  if (e && e == "1") {
    $('#limitNumPerBtn').attr('checked', true);
    $('#limitNumPerBtn').val('1');
    $('#limitNumPer').val(limitCache.number)
  } else if (e && e == "0") {
    $('#limitNumPer').attr('disabled', true);
  }
}

function initTotal() {
  var e = limitCache.limitTotal;
  if (e && e == "1") {
    $('#limitTotalBtn').attr('checked', true);
    $('#limitTotalBtn').val('1');
    $('#limitTotal').val(limitCache.total)
  } else if (e && e == "0") {
    $('#limitTotal').attr('disabled', true);
  }
}

// 获取限定条件数据

function get_service_limit_data() {
  if (!limit_validate()) {
    return false;
  }

  var weeks_new = new Array;
  $("input[name='weekDay']:checked").each(function() {
    weeks_new.push($(this).val())
  })
  
  var limitTime = $('#limitTimeBtn').val();
  var limitNumber = $('#limitNumberBtn').val();
  var limitNumPer = $('#limitNumPerBtn').val();
  var limitTotal = $('#limitTotalBtn').val();
  var datesStr = $('#datesStr').val();
  /*var stime = $('#stime').val();
  if (stime && stime.length == 4) {
    stime = "0" + stime;
  }
  var etime = $('#etime').val();
  if (etime && etime.length == 4) {
    etime = "0" + etime;
  }*/

  var number = $('#limitNumber').val();
  var numPer = $('#limitNumPer').val();
  var total = $('#limitTotal').val();
  /*if (stime == "") {
    stime = "00:00"
  };
  if (etime == "") {
    etime = "24:00"
  };*/

  if ($('#limitNumberBtn').val() == "0") {
    number = "";
  }
  if ($('#limitNumPerBtn').val() == "0") {
    numPer = "";
  }

  if ($('#limitTotalBtn').val() == "0") {
    total = "";
  }
  limitCache.limitTime = limitTime;
  limitCache.limitNumber = limitNumber;
  limitCache.limitWeek = limitWeek;
  limitCache.limitTotal = limitTotal;
 /* limitCache.stime = stime;
  limitCache.etime = etime;*/
  limitCache.datesStr = datesStr;
  limitCache.number = number;
  limitCache.numPer = numPer;
  limitCache.total = total;
  limitCache.limitWeekArray = weeks_new;
  //console.log(limitCache);
  return limitCache;
}
//添加限制条件数据
function addLimitData(){
  
  var limit_data = get_service_limit_data();
  var svr_limit = new Array;
  var added_limit = $(tab4_limit_div).tablet("getAllData");
  added_limit = eval("("+added_limit+")");
  added_limit = added_limit.data;
  var added_week = new Array;
  if(added_limit.length==0){
  	$(tab4_error_msg).html("请选择限定星期");
  }
  for(var ii=0; ii<added_limit.length; ii++){
    added_week.push(added_limit[ii].week.value);
  }
  if(limit_data && typeof limit_data == "object"){
	  if(first){
		  limit_data.datesStr='00:00-24:00';
	  }
    var weekdays = limit_data.limitWeekArray;
    for(var ii=0; ii<weekdays.length; ii++){
      if(added_week.join(',').indexOf(weekdays[ii]) > -1){
        $(tab4_error_msg).html(weeks[weekdays[ii]]+" 已配置访问限制; 请删除后再配置.");
        break;
      }else{
	  	$(tab4_error_msg).html(" ");
        var tmp_data = {
          'week' : {'value': weekdays[ii], 'name_cn':weeks[weekdays[ii]]},
          'datesStr' : limit_data.datesStr,
          //'end_time' : limit_data.etime,
          'times_day' : limit_data.number,
          'count_dat' : limit_data.numPer,
          'total_count_day' : limit_data.total
        };
        svr_limit.push(tmp_data);
      }
    }
    $(tab4_limit_div).tablet("addData", svr_limit);
    $(tab4_limit_div).tablet("sort");
    $("input[name='weekDay']:checked").each(function() {
        $(this).attr("checked", false);
      })
  }
}

function setOperator(id, targetId){
	if(typeof(id) == 'undefined' || typeof(targetId) == 'undefined'){
		return;
	}
	var type = $(id).find("option:selected").val();
	var col_type = $(id).find("option:selected").data("col_type");
	if(col_type && col_type.trim()!=''){
		type = col_type.trim();
	}
	if('2'==type){
		type = 'date';
	}else if('3'==type){
		type = 'number';
	}else{
		type = 'varchar';
	}
	$(targetId).empty();
	for(var ii=0; ii<tb_operators[type].length; ii++){
		var oper = tb_operators[type][ii];
		$(targetId).append("<option value='"+oper.key+"'> "+oper.title+" </option>");
		$(targetId).find("option:last").data("operator",oper.operator);
	}
	
	$(targetId).find("option:first").selected = true;
	$('#param_value').attr("disabled",false);
	$('#param_value_select').attr("disabled", false);
}

function showSql(){
	var columns = $(tab1_col_selected_div).tablet("getAllData");
	  columns = eval("("+columns+")");
	  if (columns.data.length == 0) {
	    //alert("没有配置结果集");
	    return false;
	  }
	  var conditions = $(tab2_cond_div).tablet("getAllData");
	  conditions = eval("("+conditions+")");
	  //if (conditions.data.length == 0) {
	   // var re = confirm("没有配置查询条件,确认继续吗?");
	    //if (!re) return false;
	  //}
	  
	  var limit_data1 = $(tab4_limit_div).tablet("getAllData");
	  limit_data1 = eval("("+limit_data1+")");
	   // if (limit_data1.data.length == 0) {
	  //  var re = confirm("没有配置访问限制条件,确认继续吗?");
	 //  if (!re) return false;
	 // }
	  var params = $(tab3_param_div).tablet("getAllData");
	  params = eval("("+params+")");
	  var all_data = {
	    "columns": columns.data,
	    "conditions": conditions.data,
	    "params": params.data
	  };
	  //console.log(obj2str(all_data));
	  //获取最终的sql语句
	  var sql_str = getSql(all_data);
	$("#sql_last").html(sql_str);
}

var repeatItem = ''; //重复的字段名

//检查数据字段名重复
function checkItem(){
	var flag = true;
	var dataAll = $("#tab1_col_selected_div").tablet("getAllData");
	dataAll = eval('('+ dataAll +')');
	dataAll = dataAll.data;
	var itemAry = new Array;
	for(var ii=0; ii<dataAll.length; ii++){
		itemAry.push(dataAll[ii].column.name_en+dataAll[ii].alias);
	}
	var count = 0;
	itemAry.sort();
//	var st = new Array;
	for(var ii=1; ii<itemAry.length; ii++){
		if(itemAry[ii]==itemAry[ii-1]){
//			repeatItem += itemAry[ii-1];
			for(var jj=0; jj<dataAll.length; jj++){
				if(dataAll[jj].column.name_en==itemAry[ii]){
					alert(dataAll[jj].table.name_cn+" 的 "+dataAll[jj].column.name_cn+"\n字段名重复，请设置别名.");
//					st.push(ii);
					document.getElementById("dta").click();
//					if(count==1){
//						alert(dataAll[st[0]].table.name_cn+" 的 "+dataAll[st[0]].column.name_cn+"\n"+dataAll[st[1]].table.name_cn+" 的 "+dataAll[st[1]].column.name_cn+"\n字段名一样，请设置别名.");
						flag = false;
						break;
//					}
//					count++;
				}
			}
			break;
		}
	}
	return flag;
}

function showDate(){
	 $('#Date_zone').show();
}
function cancelDate(){
	 $('#Date_zone').hide();
}

//添加按钮触发事件
var first = true;
function submitDate(){
	/*
	var limit_len = $(tab4_limit_div).tablet("length");
	if(limit_len > 0){
		first = false;
	}*/
	var stime=$('#stime').val();
	var etime=$('#etime').val();
	if(timeCompare(etime,stime)!=1){
		alert('输入有误：开始时间必须小于结束时间!');
		return;
	}
	
	var datesStr='';
	if(first){
		datesStr=stime+'-'+etime;
		//$('#datesStr').val(datesStr);
		$('#stime').val('00:00');
		$('#etime').val('24:00');
		$('#addDateBtn').val('继续添加');
		$('#dates').html('<span><span>'+datesStr+'</span><span class="delete" title="删除" onclick="delDates(this)">&nbsp;&nbsp;</span></span>');
		
		first=false; 
	}else{
		datesStr = $('#datesStr').val();
		tmpStr=$('#stime').val()+'-'+$('#etime').val();
		datesStr+=','+tmpStr;
		//$('#datesStr').val(datesStr+','+tmpStr);
		$('#stime').val('00:00');
		$('#etime').val('24:00');
		var tmphtml = $('#dates').html();
		$('#dates').html(tmphtml+'<span><span>'+tmpStr+'</span><span class="delete" title="删除" onclick="delDates(this)">&nbsp;&nbsp;</span></span>');
	}
	
	

	$('#datesStr').val(checkTime(datesStr));
	//console.log($('#datesStr').val());
	cancelDate();
}
function delDates(obj){	
	/*
	var datesArry = $('#datesStr').val().split(',');
	var objs=obj.parentElement.parentElement.childNodes;
	var i=0;
	var datesStr='';
	for(i=0;i<objs.length;i++){
		//alert('i='+i+'--data='+objs[i].childNodes[0].data);
		
		if(objs[i] == obj.parentElement){
			continue;
		}else{
			datesStr+=(datesArry[i]+',');
		}
		
	}
	datesStr = datesStr.replace(/,$/, '');*/
	
	//datesStr=datesStr.replace(obj.parentElement.childNodes[0].data,'');
	
	obj.parentElement.parentElement.removeChild(obj.parentElement);
	//删除时间串中对应的值
	var datesStr = "";
	$('#dates>span').each(function(){
		datesStr += $(this).children(":first").text()+",";
	})
	datesStr = datesStr.replace(/,$/, '');
	$('#datesStr').val(datesStr);
	if(datesStr==''){
		$('#dates').html('(注：重叠的时间段将被自动合并)');
		$('#addDateBtn').val('添加');
		first=true;
	}
}

//校验合并重叠的时间段
function checkTime(dates){
	//dates="00:00-03:00,12:00-14:00,09:00-12:00";
	var dateArry=dates.split(',');
	
	dateArry=dateArry.sort();
	var startArry=[];
	var endArry=['00:00'];
	var flag=false; //标记是否提示过合并
	for(var i=0;i<dateArry.length;i++){
		var tmp=dateArry[i].split('-');	
		
		if(i==0){
			startArry[0]=tmp[0];
			endArry[0]=tmp[1];
		}else if(timeCompare(tmp[0], endArry[endArry.length-1])==1){ //没有交集
			startArry[startArry.length]=tmp[0];
			endArry[endArry.length]=tmp[1];
		}else if(timeCompare(tmp[1], endArry[endArry.length-1])==1){ //部分交集 取合集
			if(!flag && confirm("发现有时间重叠，确认重叠时间段将会被合并，取消则重新输入。")){
				endArry[endArry.length-1]=tmp[1];
				flag=true;
			}else{
				var tmp = dates.substring(0,dates.lastIndexOf(','));
				backdates(tmp);
				return tmp
			}
			
		}
	}
	var dateStr = '';
	for(var i=0;i<startArry.length;i++){
		dateStr += startArry[i]+"-"+endArry[i]+",";
	} 
	dateStr = dateStr.replace(/,$/, '');
	
	refreshdates(startArry,endArry);
	return dateStr;
}

//用户取消输入，恢复输入前的内容
function backdates(dates){
	var delStr='<span class="delete" title="删除" onclick="delDates(this)">&nbsp;&nbsp;</span>';
	var tmphtml ='';
	var datesArry = dates.split(',');
	for(var i=0;i<datesArry.length;i++){
		tmphtml += ('<span><span>'+datesArry[i]+'</span>'+delStr+'</span>');
		if((i+1)%3==0)
			tmphtml+='<br />';
	}
	$('#dates').html(tmphtml);
}

//刷新时间段输入框内容
function refreshdates(sArry,eArry){
	if(sArry.length!=eArry.length)
		return ;
	var delStr='<span class="delete" title="删除" onclick="delDates(this)">&nbsp;&nbsp;</span>';
	var tmphtml ='';
	for(var i=0;i<sArry.length;i++){
		tmphtml += ('<span><span>'+sArry[i]+'-'+eArry[i]+'</span>'+delStr+'</span>');
		if((i+1)%3==0)
			tmphtml+='<br />';
	}
	$('#dates').html(tmphtml);
}
//相等返回0,1>2返回1,1<2返回-1
function timeCompare(time1,time2){
	var tmp1 = time1.split(':');
	var tmp2 = time2.split(':');
	if(parseInt(tmp1[0],10)==parseInt(tmp2[0],10) && parseInt(tmp1[1],10)==parseInt(tmp2[1],10)){
		return 0;
	}
	if(parseInt(tmp1[0],10)>parseInt(tmp2[0],10) ||(parseInt(tmp1[0],10)==parseInt(tmp2[0],10) && parseInt(tmp1[1],10)>parseInt(tmp2[1],10))){
		return 1;
	}
	return -1;
}

