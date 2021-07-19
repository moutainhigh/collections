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
						   checkTable(key);//添加到已选择表
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
	var sql = "";
	var templateSql = '';
	if (step == '1') {
		//tableCode = getFormFieldValue("record:table_code");
		var colum = getSearchSelectColumn();
		tableCode = getSelectTable().tableCodes;
		
		
		//console.log("==> getSearchSelectColumn()" + colum);
		sql = "SELECT "+colum+" FROM " + tableCode;
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
		
		var colum = getSearchSelectColumn();
		tableCode = getSelectTable().tableCodes;
		//暂时注释的内容
		//sql = "SELECT * FROM " + tableCode + ' WHERE ' + selectStr + ' ';
		
		//改由此处定义
		sql = "SELECT "+colum+" FROM " + tableCode + ' WHERE ' + selectStr + ' ';
		templateSql = sql;
		// alert(sql);
		//setFormFieldValue("record:sql", sql);
		//将sql存入cookies中
		$.cookie('templateSql', templateSql); 
	} else {
		
		var beforeSql = $.cookie('templateSql');
		
		sql = beforeSql; //取出sql之后再拼接一次
		//sql = getFormFieldValue("record:sql");
		var t_param = $("#table_param").tablet("getAllData");
		var tab_param = eval('(' + t_param + ')').data;
		var tmp_sql_3 = '';
		for ( var i = 0; i < tab_param.length; i++) {
			var stn = tab_param[i].leftParen + tab_param[i].leftTable.name_en
					+ "." + tab_param[i].leftColumn.name_en + " ";
			var middleTmp = tab_param[i].middleParen.value;
			
			if(tab_param[i].middleParen.type == 1){
            	if(middleTmp.indexOf("null") > -1){
            		stn += middleTmp + ' ';
            	}else if(middleTmp=='开始于'){
            		//stn += " like ''"+ tab_param[i].paramValue+ "%'' ";
            		stn += " like '"+ tab_param[i].paramValue+ "%' ";
            	}else if(middleTmp=='结束于'){
            		//stn += " like ''%"+ tab_param[i].paramValue+ "'' ";
            		stn += " like '%"+ tab_param[i].paramValue+ "' ";
            	}else if(middleTmp=='like'){
            		//stn += " like ''%"+ tab_param[i].paramValue+ "%'' ";
            		stn += " like '%"+ tab_param[i].paramValue+ "%' ";
            	}else{
                	//stn += middleTmp + " ''"+ tab_param[i].paramValue+ "'' ";
                	stn += middleTmp + " '"+ tab_param[i].paramValue+ "' ";
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
			//sql += " AND (" + tmp_sql_3 + ")";
			sql += " AND (" + tmp_sql_3 + ")";
		}
	}
	sql = sql.replace(/WHERE\s*(AND|OR)/ig, "WHERE ");
	sql = sql.replace(/,\s*WHERE/ig, " WHERE");
	sql = sql.replace(/WHERE\s*$/ig, "WHERE 1=1");
	sql = sql.replace(/\(\s*(AND|OR)\s*/ig, "("); 
	
	
	
	$("#sql_last").html(sql);//得到生成的的sql
	
	//生产的Sql语句存入cookies里面
	$.cookie('createSql', sql); 
	
	return sql;
}



function testSql(sql) {
	var flag = false;
	if (typeof (sql) == "undefined" || sql.length == 0) {
		return;
	}
	//sql = sql.replace(/\'\'/ig, "\'").replace(/%/ig, "%25").replace(/\+/g,"%2B");
	var url = "../../advQuery/testsql.do";
	//sql = encodeURI(sql);//中文转义,对''和?的仍然有点问题
	$.ajax({
		type : "post",
		url : url,
		async : false,
		data:{testsql:sql},
		dataType : "json", 
		success : function(data) {
			var result = data.data[0].data;
			if(result!=null){
				flag = true;
				//并在页面上显示
				$.cookie("sqlHTML",sql);
				$("#listResult").on("load",function(){
					
				})
			}
		},
		error:function(data){
			alert(data.responseText);
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
	//var colunm = getSearchSelectColumn();
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
	//createSql("1",tableCodesList);
	createSql('1');
	
	var relationList = [];
	// 获取表关联关系值
	var relation = $("#selected_columns").tablet("getAllData");//得到表与表的关联关系
	if (relation != '{data:[]}'){
		//setFormFieldValue("record:condition", relation);
		//setFormFieldValue("record:sql", createSql('2'));
		relationList.push(relation);
		createSql("2");
		//createSql("2",relationList);
	}
	// 获取查询条件值
	var param = $("#table_param").tablet("getAllData");//得到查询条件表格下拉选中的内容
	var paramList = [];
	if (param != '{data:[]}') {
		//setFormFieldValue("record:param", param);
		paramList.push(param);
		//setFormFieldValue("record:sql", createSql('3'));
		createSql('3')
		//createSql("3",paramList);
		//此处只需要拼接对应的sql之后的查询条件即可
		var beforeSql = $.cookie('templateSql');//取出存在cookies里面的值
	}
	
	var  sql = $.cookie('createSql'); //从cookie中取出存的值
	//sql = sql.replace(/%/ig, "%25").replace(/\'\'/ig, "\'");//正则替换掉一些非法的引号，包含生成的Sql语句之中的内容,当前是给空格替换
	
	var checkResult = testSql(sql); 
	if (checkResult == false) {
		return;
	}else{
		$.cookie("sqlHTML",sql);
		var obj = getJsonObj(sql);  //得到对应的json对象
		var jsonStr =  JSON.stringify(obj);  //将拼接出来的json对像转成字符串之后将对应的json字符串传到后台
		saveTableInfoTable(jsonStr);//保存对应的sql语句和对应的表到后台之中
	}
	
}

function deleteCheckTableFromTableList() {
	var sel_tbl = $("#checkTbl_div").dataSelector("getAllNodes");
	for ( var ii = 0; ii < sel_tbl.length; ii++) {
		$("#tbl_div").dataSelector("removeNodeById", sel_tbl[ii].key);
	}
}


//暂时没有用到的
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
	var relation = $("#selected_columns").tablet("getAllData");//表关系得到
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
	
	///tmpsql = tmpsql.replace(/''/ig, "'");
	//$("#sql_last").html(tmpsql);
	/*return {
		tabIdList:tabIdList,
		tableNamesList:tableNamesList,
		tableCodesList:tableCodesList,
		relationList:relationList,
		params:params
	};*/
	
}



/*得到选择的表的中英文名称**/
function getSelectTable(){
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
	
	return {
			tableIds:tableIds,
			tableNames:tableNames,
			tableCodes:tableCodes
		};
}

/*得到表的列*/
function getSearchSelectColumn(){
	var columns = $("#getColumDetail tr");//只取每一行的data属性
	if(columns==""||columns=="undefined"||columns.length==0){
		return " * ";
		
	}else{
		var name_en = "";
		var name_cn= "";
		var column_cn= "";
		var column_en= "";
		var sql = "";
		columns.each(function(){
			 var _this = $(this);
			 name_en = _this.data("name_en");
			 name_cn = _this.data("name_cn");
			 column_cn = _this.data("column_cn");
			 column_en = _this.data("column_en");
			 sql += "," +name_en+"."+column_en + "" ;
			
		});
		var length = sql.length;
	    sql = sql.substring(1,length);
		}
		$("#sql_last").html(sql);//当前sql得到查询条件的内容[注意，此处没有查询条件的情况]
		
		return sql;
}



//*生产json对象将所有选择的表拼接形成sql语句*/
function  getJsonObj(sql){
	var queryTableJson = {};
	queryTableJson.sql="";
	var colsNum = 0;//计算有多少列
	var cols = ""; //存入当前的所有的用户选择的列的中文名
	/*queryTableJson.name_en="";
	queryTableJson.name_cn="";
	queryTableJson.column_en="";
	queryTableJson.column_cn="";*/
	var queryNameTransfer = $.cookie("queryName");//  查询的主题名称
	//alert(queryNameTransfer);
	queryNameTransfer= queryNameTransfer.substring(0,queryNameTransfer.length-2)+"查询";
	
	queryTableJson.queryNameTransfer =queryNameTransfer;
	
	var name_en  = "";
	var name_cn = "";
	var column_en  = "";
	var column_cn   = "";
	//var column_ais   = "";
	
	//得到它的表名
	var table=[];
	//秩只需要循环遍历结果集的tr去取对应得表信息即可 
	$("#getColumDetail tr").each(function(){
		
		 var _this = $(this);
		// var obj = {};
		 name_en += _this.data("name_en")+",";
		 name_cn += _this.data("name_cn")+",";
		 column_en += _this.data("name_en")+"."+_this.data("column_en")+",";
		 column_cn +=  _this.data("name_cn")+"."+_this.data("column_cn")+",";
		 
		 //obj.name_en = name_en;
		// obj.name_cn = name_cn;
		// obj.column_en=column_en
		// obj.column_cn=column_cn;
		// table.push(obj);
		 cols+= "mo_" +column_cn;
		 ++colsNum;
		 
	});
	
	//字符串截取
	 name_en= name_en.substring(0,name_en.length-1);
	 name_cn = name_cn.substring(0,name_cn.length-1);
	 column_en = column_en.substring(0,column_en.length-1);
	 column_cn = column_cn.substring(0,column_cn.length-1);
	 
	$.cookie("cols",cols);//存入对应的列的中文名
	$.cookie("colsNum",colsNum);//存入对应的列的中文名
	 
	 //将截取之后的放入数组中
	queryTableJson.name_en = name_en;
	queryTableJson.name_cn = name_cn;
	queryTableJson.column_en = column_en;
	queryTableJson.column_cn = column_cn;
	
	
	queryTableJson.sql = sql; //放入生成的obj对象之中
	
	var relation = $("#selected_columns").tablet("getAllData");//表关系得到
	var relationList = []; 
	if (relation != '{data:[]}') {
		relationList.push(relation);
	}
	//queryTableJson.baseInfo.relationList.push(relationList);
	
	// 获取查询条件值
	var param = $("#table_param").tablet("getAllData");
	var params =[];
	if (param != '{data:[]}') {
		condition.push(param); //得到对应的表之间的查询条件
	}
	//queryTableJson.baseInfo.condition.push(condition);//存入对应的JSON声明为condition的对象属性之中
	
	return queryTableJson;//返回当前的生产的json对象数据
}


//清除已经选择的列
function clearSelect(obj){
	var _this = $(obj).parent().parent();
	var name_en = _this.data("name_en");
	var name_cn = _this.data("name_cn");
	var column_cn = _this.data("column_cn");
	var column_en = _this.data("column_en");
	var columntype = _this.data("columntype");
	var collength =_this.data("collength");
	_this.remove();
	$("#selectColumnList").append("<option data-name_en="+name_en+" data-name_cn="+name_cn +" data-column_en ="+column_en +" data-column_cn="+column_cn+" data-collength="+collength+" data-columntype = "+columntype+" >"+column_cn+"</option>");
	$("#selectColumnList option").off("click").on("click",function(){
		var _this = $(this);
		var name_en = _this.data("name_en");
		var name_cn = _this.data("name_cn");
		var column_cn = _this.data("column_cn");
		var column_en = _this.data("column_en");
		var columntype = _this.data("columntype");
		var collength =_this.data("collength");
		_this.remove();
		$("#getColumDetail").append("<tr  data-name_en="+name_en+" data-name_cn="+name_cn +" data-column_en ="+column_en +" data-column_cn="+column_cn+" data-collength="+collength+" data-columntype = "+columntype+" datass><td>"+name_cn+"</td><td>"+column_cn+"</td><td>"+columntype+"</td><td>"+collength+"</td><td><a href='javascipt:;' onclick='clearSelect(this)'>删除</td></tr>");
	});
}


/*得到展示的数据表格列，用于执行sql之后的语句，加入到预览SQL语句查询出来的内容*/
function saveTableInfoTable(jsonStr){
	//此处拼接对应的json字符串
	$.ajax({
		url:"../../advQuery/saveAdvInfo.do",
		type:"post",
		dataType:"json",
		data:{datas:jsonStr},
		success:function(data){
			var dataStr = data.data[0].data;
			if(dataStr=="1"){
				$("#listResult").attr("src","queryForPage.jsp");
					jazz.info("高级查询配置保存成功");
			}else{
				jazz.info("高级查询配置保存失败");
			}
		}
	});

}

