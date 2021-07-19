var ori_tableParam;
var ori_tableCondition;
var tb_operators = {
	"number" : [ {
		"key" : "=",
		"title" : "等于"
	}, {
		"title" : '不等于',
		"key" : '<>'
	}, {
		"key" : "<",
		"title" : "小于"
	}, {
		"key" : "<=",
		"title" : "小于等于"
	}, {
		"key" : ">",
		"title" : "大于"
	}, {
		"key" : ">=",
		"title" : "大于等于"
	} ],
	"varchar" : [ {
		"key" : "=",
		"title" : "等于"
	}, {
		"key" : "<>",
		"title" : "不等于"
	}, {
		"key" : "<",
		"title" : "小于"
	}, {
		"key" : "<=",
		"title" : "小于等于"
	}, {
		"key" : ">",
		"title" : "大于"
	}, {
		"key" : ">=",
		"title" : "大于等于"
	}, {
		"key" : "开始于",
		"title" : "以...开始"
	}, {
		"key" : "结束于",
		"title" : "以...结束"
	}, {
		"key" : "like",
		"title" : "包含(LIKE)"
	}
	/*
	 * ,{ "key" : "IN", "title" : "包含(IN)", "operator" : " in (\'#@#\') " }
	 */
	, {
		"key" : "is null",
		"title" : "为空"
	}, {
		"key" : "is not null",
		"title" : "不为空"
	} ],
	"date" : [ {
		"title" : '等于',
		"key" : '='
	}, {
		"title" : '不等于',
		"key" : '<>'
	}, {
		"title" : '大于',
		"key" : '>'
	}, {
		"title" : '大于等于',
		"key" : '>='
	}, {
		"title" : '小于',
		"key" : '<'
	}, {
		"title" : '小于等于',
		"key" : '<='
	} ]
};

// 根据业务系统获取主题列表
function getTopic(key, container) {
		
	if (typeof (currentSystem) == "undefined") {
		currentSystem = key;
	}

}

// 根据业务主题获取数据表列表
function getTable(key, container) {
	if (typeof (currentTopic) == "undefined") {
		currentTopic = key;
	}
	var tblData = [];
	$.ajax({
		url:"../../advQuery/getTable.do",
		data:{topicId:key},
		type:"get",
		dataType:"json",
		success:function(data){
			tblData = data.data[0].data;
			//将点击的数据添加到右边选中的内容到选择的数据表中
			var opt = {
						data : tblData,
						onClick : function(event, key) {
						checkTable(key);
					  }
				}
			$('#' + container).dataSelector('destroy');
			$('#' + container).dataSelector(opt);
			deleteCheckTableFromTableList();
		}
		
	});

}


// 获取数据表获取数据项
function getDataItemByKey(key, div_container) {
	var datas = [];
	$.ajax({
		url:"../../advQuery/getColumn.do?tableId=" + key,
		type:"get",
		dataType:"json",
		success:function(data){
			 datas = data.data[0].data;//得到对应下方数据表的对应列
			// console.log("111111" + datas);
			 var opt = {
						multiple : false,
						data : datas,
						size : 1
					 };
			 $('#' + div_container).dataSelector("destroy");//销毁对应点击的数据行【即是移除选择数据表的结果集】
			 $('#' + div_container).dataSelector(opt);
			 
			
		}
		
	});
}


function setOperator(id, targetId) {
	if (typeof (id) == 'undefined' || typeof (targetId) == 'undefined') {
		return;
	}
	var type = $(targetId).find("option:selected").val();
	var col_type = $(id).find("option:selected").data("col_type");
	if (col_type && col_type.trim() != '') {
		type = col_type.trim();
	}
	if ('2' == type) {
		type = 'date';
	} else if ('3' == type) {
		type = 'number';
	} else {
		type = 'varchar';
	}
	var rightParen = new Array;
	for ( var ii = 0; ii < tb_operators[type].length; ii++) {
		var oper = tb_operators[type][ii];
		rightParen.push({
			"key" : oper.key,
			"title" : oper.title
		});
	}
	var opt_condition_right = {
		multiple : false,
		data : rightParen,
		size : 1,
		className : "sec_left"
	};
	if ($(targetId).dataSelector("getAllNodes").length > 0)
		$(targetId).dataSelector("destroy");
	$(targetId).dataSelector(opt_condition_right);
	// 设置选择为空不为空时后面的输入框不可输入
	$('#paramValue').attr("disabled", false);
	$('#pvs').attr("disabled", false);
	$('#condition_middle_id').change(function() {
		var p1 = $(this).children('option:selected').val();// 这就是selected的值
		if (p1 == "is null" || p1 == "is not null") {
			$('#paramValue').attr("disabled", true);
			$('#paramValue').attr("value", '');
			$('#pvs').attr("disabled", true);
		} else {
			$('#paramValue').attr("disabled", false);
			$('#pvs').attr("disabled", false);
		}
	});
}
var tableCode = '';
function createSql(step) {
	
}

function testSql(sql) {
	var flag = false;
	if (typeof (sql) == "undefined" || sql.length == 0) {
		return;
	}
	sql = sql.replace(/\'\'/ig, "\'").replace(/%/ig, "%25").replace(/\+/g,"%2B");
	var url = "/testsql?testsql=" + sql;
	url = encodeURI(url);
	$.ajax({
		type : "post",
		url : url,
		async : false,
		dataType : "json", 
		success : function(data) {
			var result = xmlResults.selectSingleNode("/results/sql").text;
			if (result == "false") {
				alert("SQL校验失败！请检查后重试");
				flag = false;
			} else {
				flag = true;
			}
		}
	});
	return flag;
}

// 新增关联条件
function addRelation() {
	var a = $('#leftTbl_div').dataSelector("getSelectedNodes");
	var b = $('#rightTbl_div').dataSelector("getSelectedNodes");
	var c = $('#leftItem_div').dataSelector("getSelectedNodes");
	var d = $('#rightItem_div').dataSelector("getSelectedNodes");
	var e = $('#condition_paren').dataSelector("getSelectedNodes");
	if (a.length == 0) {
		alert("请选择关联表表1");
		return;
	}
	if (b.length == 0) {
		alert("请选择关联表表2");
		return;
	}
	if (c.length == 0) {
		alert("请选择关联表表1数据项");
		return;
	}
	if (d.length == 0) {
		alert("请选择关联表表2数据项");
		return;
	}
	if (a[0].code == b[0].code) {
		alert("数据关联表不能相同!");
		return;
	}
	var tableRelation = [ {
		"leftTable" : {
			"id" : a[0].key,
			"name_en" : a[0].code,
			"name_cn" : a[0].title
		},
		"leftColumn" : {
			"id" : c[0].key,
			"name_en" : c[0].code,
			"name_cn" : c[0].title
		},
		"middleParen" : e[0].key,
		"rightTable" : {
			"id" : b[0].key,
			"name_en" : b[0].code,
			"name_cn" : b[0].title
		},
		"rightColumn" : {
			"id" : d[0].key,
			"name_en" : d[0].code,
			"name_cn" : d[0].title
		}
	} ];
	$("#selected_columns").tablet("addData", tableRelation);
	// 设置预览标签显示
	tableRelation = $("#selected_columns").tablet("getAllData");
	bulidTableCondition(tableRelation);
	if(ori_tableCondition){
		removeOriDel(ori_tableCondition);
	}
	
	
}
//如果接口正在使用，原有的配置项不能被修改或删除，清空组件添加时产生的多余删除操作
function removeOriDel(ori_data){
	if(being_used=='true' && ori_data){
		var trs  = $("#selected_columns").find('tr:not(.tablet_thead)');
		$.each(trs,function(index,val){
			var tds = $(val).find('td');
			var left_col_id = $(tds[1]).find('input[name="id"]').val();
			var middleParen = $(tds[2]).html();
			var right_col_id = $(tds[4]).find('input[name="id"]').val();
			var len = ori_data.length;
			for (var i=0;i<len;i++){
				if(left_col_id==ori_data[i].leftColumn.id
						&& middleParen==ori_data[i].middleParen
						&& right_col_id==ori_data[i].rightColumn.id){
					//是原有的关联条件,屏蔽删除操作
					$(tds[5]).html('');
					return true;
				}
					
			}
		});
	}
}
// 添加查询条件
function addParam() {
	var a = $('#condition_div').dataSelector("getSelectedNodes");
	var b = $('#condition_item_div').dataSelector("getSelectedNodes");
	var cond = $('#condition_cond').dataSelector("getSelectedNodes");
	var left = $('#condition_left').dataSelector("getSelectedNodes");
	var right = $('#condition_right').dataSelector("getSelectedNodes");
	var middle = $('#condition_middle').dataSelector("getSelectedNodes");
	var paramValue = $('#paramValue').val();
	var pvs = $('#pvs').find("option:selected");
	if (a.length == 0) {
		alert("请选择查询条件的数据表");
		return false;
	}
	if (b.length == 0) {
		alert("请选择查询条件的数据项");
		return false;
	}
	if ($("#pvs").css("display") != "none") {
		paramValue = pvs.val();
	}
	if (middle[0].key.indexOf("null") == -1
			&& (typeof (paramValue) == 'undefined' || paramValue.trim().length == 0)) {
		alert("请填写参数值");
		return false;
	}
	var parenType = 1;
	if ($('#condition_item_div_id option:selected').data("col_type") == '3') {
		parenType = 3;
		if (isNaN(paramValue)) {
			alert("请填写合适的数字类型参数值");
			return false;
		}
	} else if ($('#condition_item_div_id option:selected').data("col_type") == '2') {
		parenType = 2;
	}
	var tableParam = [ {
		"cond" : cond[0].key,
		"leftParen" : left[0].key,
		"leftTable" : {
			"id" : a[0].key,
			"name_en" : a[0].code,
			"name_cn" : a[0].title
		},
		"leftColumn" : {
			"id" : b[0].key,
			"name_en" : b[0].code,
			"name_cn" : b[0].title
		},
		"middleParen" : {
			"value" : middle[0].key,
			"type" : parenType,
			"name_cn" : middle[0].key
		},
		"paramValue" : paramValue,
		"rightParen" : right[0].key
	} ];
	if ($("#table_param tr").length == 1) {
		tableParam[0].cond = ' ';
	}
	$("#table_param").tablet("addData", tableParam);
	if ($("#table_param tr").length == 2) {
		$("#condition_cond").attr("disabled", false);
		$("#condition_cond_id option[value=' ']").remove();
	}
	// 设置预览标签显示
	tableParam = $("#table_param").tablet("getAllData");
	bulidTableParam(tableParam);
	limConTdWidth();
	if(ori_tableParam){
		removeParamOriDel(ori_tableParam);
	}
	
}
function re_cond_init(obj){
	if(being_used=='true' && ori_tableCondition){
		removeOriDel(ori_tableCondition);
	}	
}
function re_param_init(obj){
	if(being_used=='true' && ori_tableParam){
		removeParamOriDel(ori_tableParam);
	}	
}
//如果接口正在使用，原有的配置项不能被修改或删除，清空组件添加时产生的多余删除操作
function removeParamOriDel(ori_data){
	if(being_used=='true' && ori_data){
		var trs  = $("#table_param").find('tr:not(.tablet_thead)');
		$.each(trs,function(index,val){
			var tds = $(val).find('td');
			var cond = $(tds[0]).html();
			var leftParen = $(tds[1]).html();
			var left_col_id = $(tds[3]).find('input[name="id"]').val();
			var middleParen = $(tds[4]).find('input[name="value"]').val();
			var paramValue = $(tds[5]).html();
			var rightParen = $(tds[6]).html();
			var len = ori_data.length;
			for (var i=0;i<len;i++){
				if(cond==ori_data[i].cond
						&&leftParen ==ori_data[i].leftParen
						&&left_col_id==ori_data[i].leftColumn.id
						&& middleParen==ori_data[i].middleParen.value
						&& paramValue==ori_data[i].paramValue
						&& rightParen==ori_data[i].rightParen){
					//是原有的关联条件,屏蔽删除操作
					$(tds[7]).html('');
					return true;
				}
					
			}
		});
	}
}
function bulidTableCondition(conds) {
	var cond_html = "";
	if (typeof (conds) != 'object') {
		conds = eval('(' + conds + ')');
		conds = conds.data;
	}

	for ( var i = 0; i < conds.length; i++) {
		var cond = conds[i];
		cond_html += "[" + cond.leftTable.name_cn + "] 的 "
				+ cond.leftColumn.name_cn + " <span style='color:red'> "
				+ cond.middleParen + " </span>";
		cond_html += "[" + cond.rightTable.name_cn + "] 的 "
				+ cond.rightColumn.name_cn + "<br/>";
	}

	$("#cond_condition").html(cond_html == '' ? '无' : cond_html);
}

function bulidTableParam(parms) {
	var param_html = "";

	if (typeof (parms) != 'object') {
		parms = eval('(' + parms + ')');
		parms = parms.data;
	}
	for ( var i = 0; i < parms.length; i++) {
		var parm = parms[i];
		param_html += parm.cond + " [" + parm.leftTable.name_cn + "] 的 "
				+ parm.leftColumn.name_cn + " <span style='color:red'> "
				+ parm.middleParen.value + " </span>";
		param_html += parm.paramValue + "<br/>";
	}
	// alert(param_html);
	$("#cond_param").html(param_html == '' ? '无' : param_html);
	// $("#cond_param").html(param_html);
}

function preview() {
	var objPrams = showSql();
	
	//注意开始部分,直到583结束
	var sql = '';
	// 获取选择的数据表
	var e = $('#checkTbl_div').dataSelector("getAllNodes");
	var tableIds = "";
	var tableNames = "";
	var tableCodes = "";
	if (e.length == 0) {
		alert("请选择数据表!");
		return;
	} else {
		for ( var ii = 0; ii < e.length; ii++) {
			tableIds += (tableIds == '' ? e[ii].key : ',' + e[ii].key);
			tableNames += (tableNames == '' ? e[ii].title : ',' + e[ii].title);
			tableCodes += (tableCodes == '' ? e[ii].code : ',' + e[ii].code);
		}
	}
	
	var tabIdList = [];
	var tableNamesList = [];
	var tableCodesList = [];
	
	tabIdList.push(tableIds);
	tableNamesList.push(tableNames);
	tableCodesList.push(tableCodes);
	
	//setFormFieldValue("record:table_id", tableIds);
	//setFormFieldValue("record:table_name_cn", tableNames);
	//setFormFieldValue("record:table_code", tableCodes);
	// 拼装sql
	//setFormFieldValue("record:sql", createSql('1'));
	
	
	
	var relationList = [];
	// 获取表关联关系值
	var relation = $("#selected_columns").tablet("getAllData");//得到表与表的关联关系
	if (relation != '{data:[]}'){
		//setFormFieldValue("record:condition", relation);
		//setFormFieldValue("record:sql", createSql('2'));
		relationList.push(relation);
		createSql("2");
	}
	// 获取查询条件值
	var param = $("#table_param").tablet("getAllData");//得到查询条件表格下拉选中的内容
	var paramList = [];
	if (param != '{data:[]}') {
		//setFormFieldValue("record:param", param);
		paramList.push(param);
		//setFormFieldValue("record:sql", createSql('3'));
		createSql('3')
	}
	
	var checkResult = testSql(sql);
	if (checkResult == false) {
		return;
	}else{
		/*$.ajax({
			url:"../../advQuery/saveAdvInfo.do",
			type:"get",
			data:{queryParams:relation},
			beforeSend:function(data){
			},
			success:function(data){
				var datas = data.data[0].data;
				console.log(data);
			}
		});*/
		//document.getElementById('tableIds').value = tableIds;
		//document.getElementById('table_sql').value = sql;
		//sql = sql.replace(/%/ig, "%25").replace(/\'\'/ig, "\'");
		
	}

	
	var sql = $("#sql_last").html();
	
	
}
function deleteCheckTableFromTableList() {
	var sel_tbl = $("#checkTbl_div").dataSelector("getAllNodes");
	for ( var ii = 0; ii < sel_tbl.length; ii++) {
		$("#tbl_div").dataSelector("removeNodeById", sel_tbl[ii].key);
	}
}



function showSql() {
	// 获取选择的数据表
	var e = $('#checkTbl_div').dataSelector("getAllNodes");
	var tableIds = "";
	var tableNames = "";
	var tableCodes = "";
	if (e.length == 0) {
		// alert("请选择数据表!");
		return;
	} else {
		for ( var ii = 0; ii < e.length; ii++) {
			tableIds += (tableIds == '' ? e[ii].key : ',' + e[ii].key);
			tableNames += (tableNames == '' ? e[ii].title : ',' + e[ii].title);
			tableCodes += (tableCodes == '' ? e[ii].code : ',' + e[ii].code);
		}
	}
	
	var tabIdList = [];
	var tableNamesList = [];
	var tableCodesList = [];
	
	tabIdList.push(tableIds);
	tableNamesList.push(tableNames);
	tableCodesList.push(tableCodes);
	
	
	// 获取表关联关系值
	var relation = $("#selected_columns").tablet("getAllData");
	var relationList = []; 
	if (relation != '{data:[]}') {
		//setFormFieldValue("record:condition", relation);
		//setFormFieldValue("record:sql", createSql('2'));
		relationList.push(relation);
	}
	
	// 获取查询条件值
	var param = $("#table_param").tablet("getAllData");
	var params =[];
	if (param != '{data:[]}') {
		//setFormFieldValue("record:param", param);
		//setFormFieldValue("record:sql", createSql('3'));
		params.push(param);
	}
	return {
		tabIdList:tabIdList,
		tableNamesList:tableNamesList,
		tableCodesList:tableCodesList,
		relationList:relationList,
		params:params
	};
	
}


//自定义表单选择内容
//获取域的名称 和 内容
function setFormFieldValue(disStr,param){
	//$("#sql_last").html(param);
	//console.log(">>>>>>>>>>>>" +param);
	
}


function getFormFieldValue(disStr,param){
	
}