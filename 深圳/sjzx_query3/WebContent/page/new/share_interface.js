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
	$.ajax({
				type : "get",
				url : "../../advQuery/getTable.do?topicId="+ key,
				async : false,
				dataType:"json",
				success : function(data) {
						//busiTopicData = eval('(' + datas + ')');
						busiTopicData = data.data[0].data;
						$(function() {
							var opt = {
								data : busiTopicData,
								onClick : function(event, key) {
									getTable(key, 'checkTbl_div');
								}
							}
							$('#' + container).dataSelector('destroy');
							$('#' + container).dataSelector(opt);
						})
				}
			});

}

// 根据业务主题获取数据表列表
function getTable(key, container) {
	if (typeof (currentTopic) == "undefined") {
		currentTopic = key;
	}
	$.ajax({
				type : "get",
				url : "../../advQuery/getTable.do?topicId="+ key,
				async : false,
				dataType:"json",
				success : function(data) {
						var datas = data.data[0].data;;
						//tblData = eval('(' + datas + ')');
						tblData = datas;
						console.log(tblData)
						$(function() {
							var opt = {
								data : tblData,
								onClick : function(event, key) {
									alert("1111 == " + key)
									//checkTable(key);
								}
							}
							$('#' + container).dataSelector('destroy');
							$('#' + container).dataSelector(opt);
						})
				}
			});
		deleteCheckTableFromTableList();

}

// 获取数据表获取数据项
function getDataItemByKey(key, div_container) {
	alert("ye  " + key)
	var returnVal = "";
	$.ajax({
		type : "post",
		url : "query/advQuery/getColumn.do?tableId="+ key,
		async : false,
		dataType:"json",
		success : function(data) {
		 var datas = data.data[0].data;
		 returnVal = datas;
		}
	})
	return returnVal;
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
	var sql = "";
	if (step == '1') {
		tableCode = getFormFieldValue("record:table_code");
		sql = "SELECT * FROM " + tableCode;
	} else if (step == '2') {
		// sql = getFormFieldValue("record:sql");
		var relation = $("#selected_columns").tablet("getAllData");
		/** ******** */
		// 左连接相关变量
		var leftJoinTable = ''; // 比如 tb1 left join tb2 left join tb3
		// var leftJoinStr = '';
		// 正常查询条件变量
		var selectStr = ''; // 比如 tb1.id = tb2.id

		/** ******** */

		// console.log("old sql = "+sql);
		var tab_relation = eval('(' + relation + ')').data;
		for ( var i = 0; i < tab_relation.length; i++) {
			if (tab_relation[i].middleParen == '=') {
				var stn = tab_relation[i].leftTable.name_en + "."
						+ tab_relation[i].leftColumn.name_en
						+ tab_relation[i].middleParen
						+ tab_relation[i].rightTable.name_en + "."
						+ tab_relation[i].rightColumn.name_en;

				selectStr += (stn === '' ? '' : ' AND ') + stn;

			} else if (tab_relation[i].middleParen == 'left join') {
				var stn = tab_relation[i].leftTable.name_en + "."
						+ tab_relation[i].leftColumn.name_en + " = "
						+ tab_relation[i].rightTable.name_en + "."
						+ tab_relation[i].rightColumn.name_en + "(+) ";
				selectStr += (stn === '' ? '' : ' AND ') + stn;
				/** ************************************* */
				/*
				 * //找到左连接的所有表 leftJoinTable += ( leftJoinTable==='' ?
				 * (tab_relation[i].leftTable.name_en + " LEFT JOIN " +
				 * tab_relation[i].rightTable.name_en) : (" LEFT JOIN " +
				 * tab_relation[i].rightTable.name_en) ); //找到左连接的字段
				 * leftJoinTable += " ON " + tab_relation[i].leftTable.name_en +
				 * "." + tab_relation[i].leftColumn.name_en + " = " +
				 * tab_relation[i].rightTable.name_en + "." +
				 * tab_relation[i].rightColumn.name_en ;
				 */

				/** ************************************* */

			}
		}
		// console.log(tableCode);
		/*
		 * var tbs = tableCode.split(','); // 把不做左关联条件的表拼在查询表的最前面 // 比如， select *
		 * from tb5, tb1 left join tb2 on ... for(var ii=0; ii<tbs.length;
		 * ii++){ if(leftJoinTable.indexOf(tbs[ii]+".") == -1){ leftJoinTable =
		 * tbs[ii] + ', ' + leftJoinTable; } }
		 */
		// 重新拼接sql
		// sql = "SELECT * FROM " + leftJoinTable + ' WHERE ' + selectStr;
		sql = "SELECT * FROM " + tableCode + ' WHERE ' + selectStr + ' ';
		// alert(sql);
		setFormFieldValue("record:sql", sql);
	} else {
		sql = getFormFieldValue("record:sql");
		var t_param = $("#table_param").tablet("getAllData");
		var tab_param = eval('(' + t_param + ')').data;
		var tmp_sql_3 = '';
		for ( var i = 0; i < tab_param.length; i++) {
			var stn = tab_param[i].leftParen + tab_param[i].leftTable.name_en
					+ "." + tab_param[i].leftColumn.name_en + " ";
			// stn += tab_param[i].middleParen +
			// (tab_param[i].middleParen.indexOf("null")>-1 ? ' ' : (" ''"+
			// tab_param[i].paramValue+ "'' "));
			var middleTmp = tab_param[i].middleParen.value;
			
			if(tab_param[i].middleParen.type == 1){
            	if(middleTmp.indexOf("null") > -1){
            		stn += middleTmp + ' ';
            	}else if(middleTmp=='开始于'){
            		stn += " like ''"+ tab_param[i].paramValue+ "%'' ";
            	}else if(middleTmp=='结束于'){
            		stn += " like ''%"+ tab_param[i].paramValue+ "'' ";
            	}else if(middleTmp=='like'){
            		stn += " like ''%"+ tab_param[i].paramValue+ "%'' ";
            	}else{
                	stn += middleTmp + " ''"+ tab_param[i].paramValue+ "'' ";
            	}
            }else if(tab_param[i].middleParen.type == 3){
            	stn += middleTmp + " "+ tab_param[i].paramValue+ " ";
            }else if(tab_param[i].middleParen.type == 2){
            	tab_param[i].middleParen.value = tab_param[i].middleParen.value.replace(/date/ig,' ');
            	if(tab_param[i].middleParen.value.trim()=="=" || tab_param[i].middleParen.value.trim()=="<>"){
            		stn = tab_param[i].leftParen + ' to_date(substr(' + tab_param[i].leftTable.name_en + "." + tab_param[i].leftColumn.name_en + ",0,10), ''YYYY-MM-DD HH24:mi:ss'') " ; 
                }else{
                	stn = tab_param[i].leftParen + ' to_date(' + tab_param[i].leftTable.name_en + "." + tab_param[i].leftColumn.name_en + ", ''YYYY-MM-DD HH24:mi:ss'') " ; 
                }
//            	stn = tab_param[i].leftParen + ' to_date(' + tab_param[i].leftTable.name_en + "." + tab_param[i].leftColumn.name_en + ", ''YYYY-MM-DD HH24:mi:ss'') " ; 
            	stn += tab_param[i].middleParen.value + " to_date(''"+ tab_param[i].paramValue+ "'',''YYYY-MM-DD HH24:mi:ss'') ";
            }
            stn += tab_param[i].rightParen;
			
			/*if (tab_param[i].middleParen.type == 1) {
				if (middleTmp.indexOf("null") > -1) {
					stn += middleTmp + ' ';
				} else if (middleTmp == '开始于') {
					stn += " like '" + tab_param[i].paramValue + "%' ";
				} else if (middleTmp == '结束于') {
					stn += " like '%" + tab_param[i].paramValue + "' ";
				} else if (middleTmp == 'like') {
					stn += " like '%" + tab_param[i].paramValue + "%' ";
				} else {
					stn += middleTmp + " '" + tab_param[i].paramValue + "' ";
				}
			} else if (tab_param[i].middleParen.type == 3) {
				stn += middleTmp + " " + tab_param[i].paramValue + " ";
			} else if (tab_param[i].middleParen.type == 2) {
				tab_param[i].middleParen.value = tab_param[i].middleParen.value
						.replace(/date/ig, ' ');
				if (tab_param[i].middleParen.value.trim() == "="
						|| tab_param[i].middleParen.value.trim() == "<>") {
					stn = tab_param[i].leftParen + ' to_date(substr('
							+ tab_param[i].leftTable.name_en + "."
							+ tab_param[i].leftColumn.name_en
							+ ",0,10), 'YYYY-MM-DD HH24:mi:ss') ";
				} else {
					stn = tab_param[i].leftParen + ' to_date('
							+ tab_param[i].leftTable.name_en + "."
							+ tab_param[i].leftColumn.name_en
							+ ", 'YYYY-MM-DD HH24:mi:ss') ";
				}
				// stn = tab_param[i].leftParen + ' to_date(' +
				// tab_param[i].leftTable.name_en + "." +
				// tab_param[i].leftColumn.name_en + ", ''YYYY-MM-DD
				// HH24:mi:ss'') " ;
				stn += tab_param[i].middleParen.value + " to_date('"
						+ tab_param[i].paramValue
						+ "','YYYY-MM-DD HH24:mi:ss') ";
			}
			stn += tab_param[i].rightParen;*/
			/*
			 * if (sql.indexOf("WHERE") == -1) { sql += " WHERE " + stn; }else {
			 * if(tab_param[i].cond=='AND'){ sql += " AND " + stn; }else
			 * if(tab_param[i].cond=='OR'){ sql += " OR (" + stn +")"; }else{
			 * sql += " AND " + stn; } }
			 */
			if (tab_param[i].cond == 'AND') {
				tmp_sql_3 += " AND " + stn;
			} else if (tab_param[i].cond == 'OR') {
				tmp_sql_3 += " OR " + stn;
			} else {
				tmp_sql_3 += stn;
			}
		}
		if (sql.indexOf("WHERE") == -1) {
			sql += " WHERE " + tmp_sql_3;
		} else {
			sql += " AND (" + tmp_sql_3 + ")";
		}
	}
	// console.log("interface sql = "+sql);
	sql = sql.replace(/WHERE\s*(AND|OR)/ig, "WHERE ");
	sql = sql.replace(/,\s*WHERE/ig, " WHERE");
	sql = sql.replace(/WHERE\s*$/ig, "WHERE 1=1");
	sql = sql.replace(/\(\s*(AND|OR)\s*/ig, "(");
	//sql = sql.replace(/''/ig, "'");
	return sql;
}

function testSql(sql) {
	var flag = false;
	if (typeof (sql) == "undefined" || sql.length == 0) {
		return;
	}
	sql = sql.replace(/\'\'/ig, "\'").replace(/%/ig, "%25").replace(/\+/g,
			"%2B");
	var url = "/testsql?testsql=" + sql;
	url = encodeURI(url);
	$.ajax({
		type : "post",
		url : url,
		async : false,
		dataType : "xml",
		success : function(xmlResults) {
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
	/*
	 * var sql=getFormFieldValue("record:sql");
	 * 
	 * if(sql.trim()==''){
	 */
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
	setFormFieldValue("record:table_id", tableIds);
	setFormFieldValue("record:table_name_cn", tableNames);
	setFormFieldValue("record:table_code", tableCodes);
	// 拼装sql
	setFormFieldValue("record:sql", createSql('1'));
	// 获取表关联关系值
	var relation = $("#selected_columns").tablet("getAllData");
	if (relation != '{data:[]}') {
		setFormFieldValue("record:condition", relation);
		setFormFieldValue("record:sql", createSql('2'));
	}
	// 获取查询条件值
	var param = $("#table_param").tablet("getAllData");
	if (param != '{data:[]}') {
		setFormFieldValue("record:param", param);
		setFormFieldValue("record:sql", createSql('3'));
	}
	sql = getFormFieldValue("record:sql");
	var checkResult = testSql(sql);
	if (checkResult == false) {
		return;
	}
	/*
	 * } else{ setFormFieldValue("record:sql", " ");
	 * document.getElementById('table_sql').value = ""; //preview(); return; }
	 */
	document.getElementById('tableIds').value = tableIds;
	document.getElementById('table_sql').value = sql;
	sql = sql.replace(/%/ig, "%25").replace(/\'\'/ig, "\'");
	document.form1.submit();
	_showProcessHintWindow ("查询中...");
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
	setFormFieldValue("record:table_id", tableIds);
	setFormFieldValue("record:table_name_cn", tableNames);
	setFormFieldValue("record:table_code", tableCodes);
	// 拼装sql
	setFormFieldValue("record:sql", createSql('1'));

	// 获取表关联关系值
	var relation = $("#selected_columns").tablet("getAllData");
	if (relation != '{data:[]}') {
		setFormFieldValue("record:condition", relation);
		setFormFieldValue("record:sql", createSql('2'));
	}

	// 获取查询条件值
	var param = $("#table_param").tablet("getAllData");
	if (param != '{data:[]}') {
		setFormFieldValue("record:param", param);
		setFormFieldValue("record:sql", createSql('3'));
	}
	var tmpsql = getFormFieldValue("record:sql");
	tmpsql = tmpsql.replace(/''/ig, "'");
	document.getElementById("sql_last").innerHTML = tmpsql;
}
