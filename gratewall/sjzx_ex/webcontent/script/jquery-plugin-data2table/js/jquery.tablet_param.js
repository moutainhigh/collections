
;(function($) {
	var $this = $(this);
	var defaults = {
				data: [],
				evenRowClass: "tty_evenRow",
				oddRowClass: "tty_oddRow",
				activeRowClass: "tty_activeRow",
				selectedRowClass: "tty_selectedRow",
				editableClass: "tty_editableCol",
				addDelete: true,
			//	multiSelect: false,
				onclick: null,
				dblclick: null,
				editable: false,
				shownum:5,	//默认显示行数
				onDelete: null,
				lineNum: false,	//是否显示行号
				isAlias: true,	//是否支持数据字段别名检查
				patameter_type: null,
				patameter_style: null
			};
	var settings = defaults;
			
	var methods = {
		init: function(options) {
			settings = $.extend({}, defaults, options);
			return this.each(function() {
				_init();
				_tableStyle();
				_editable();
				var show = settings.shownum;
				if($this.find("tr:gt(0)").length > show){
					$this.height(show*25+30);
					$this.css("overflow-y", "scroll");
					$this.css("float", "left");
				}
				if(settings.lineNum){
					_setLineNumber();
				}
			});
		},
		insertRow: function(){
			var data = arguments[0];
			return this.each(function(){
				_insertRow(data);
				_tableStyle();
				_editable();
			});
		},
		initRow: function(){
			var data = arguments[0];
			return this.each(function(){
				_initRow(data);
				_tableStyle();
				_editable();
			});
		},
		addData: function() {
			var data = arguments[0];
			//console.log($this.find("table").html());
			return this.each(function() {
				_addData(data);
				_tableStyle();
				_editable();
				
				var show=settings.shownum;

				if($this.find("tr:gt(0)").length > show){
					$this.height(show*25+30);
					$this.css("overflow-y", "scroll");
					$this.css("float", "left");
				}
			});
		},
		removeRow: function() {
			var remove_index = arguments[0];
			return this.each(function() {
				if (remove_index > 0) {
					var delData = _removeRow(remove_index);
					if (settings.addDelete || $this.find(".oper").length>0) {
						_addDelete();
					}
				}
				var show=settings.shownum;
				if($this.find("tr:gt(0)").length <= show){
					$this.parent().height($this.height());
					$this.parent().css("overflow-y", "visible");
					$this.css("float", "left");
				}
				if(typeof(settings.onDelete)=='function'){
					settings.onDelete.apply(this, delData);  //执行删除后再执行用户自定义的操作
				}
				if(settings.lineNum){
					_setLineNumber();
				}
			});
		},
		removeAllData: function(){
	//		if(($this.find("tr:gt(0)").length > 0)){
//				if(confirm("确认要删除所有数据吗?")){
					$this.find("tr:gt(0)").remove();
	//			}
	//		}else{
	//			alert("没有数据");
	//		}
		},
		getRowData: function(which) {
			return _getRowData(which);
		},
		getAllData: function() {
		//	console.log(_getAllData());
			return _getAllData();
		},
		addDelete: function() {
			_addDelete();
		}
		,
		tableStyle: function() {
			_tableStyle(settings);
		},
		getDataLength:function(){
			return $this.find("tr:gt(0)").length;
		},
		checkData: function(){
			var dataAll = _getAllData();
			var data = arguments[0];
			dataAll = eval("(" + dataAll + ")");
			dataAll = dataAll.data;
			var flag = false;
			for(var ii=0; ii<dataAll.length; ii++){
				if (flag = _compObj(dataAll[ii], data)) {
					flag = true;
					break;
				}
			}
			return flag;
		},
		sort: function(){
			var dataAll = _getAllData();
			dataAll = eval("(" + dataAll + ")");
			var rowdata = new Array;
			rowdata.length = 8;
			for(var ii=0; ii<dataAll.data.length; ii++){
				rowdata[dataAll.data[ii].week.value] = dataAll.data[ii];
			}
			var data2add = new Array;
			for(var ii=0; ii<rowdata.length; ii++){
				//console.log(rowdata[ii]);
				if(typeof(rowdata[ii])!='undefined'){
					data2add.push(rowdata[ii]);
				}
			}
			rowdata.length = 0;
			$this.find("tr:gt(0)").remove();
			_addData(data2add);
			_tableStyle();
			_editable();
				
			var show=settings.shownum;

			if($this.find("tr:gt(0)").length > show){
				$this.height(show*25+30);
				$this.css("overflow-y", "scroll");
				$this.css("float", "left");
			}
		}
	};

	$.fn.tablet = function() {
		$this = $(this);

		var options = $this.data("options");
		if(options){
			settings = $.extend({}, defaults, options);
		}
		var method = arguments[0];
		if (methods[method]) {
			return methods[method].apply($this, Array.prototype.slice.call(arguments, 1));
		} else if (typeof method === 'object' || !method) {
			var tmp = $.extend({}, defaults, arguments[0]);
			$this.data("options", tmp);
			return methods.init.apply($this, arguments);
		} else {
			alert("method error");
			$.error('Method' + method + 'does not exist on jQuery.tablet');
		}
	}
	
	function _insertRow(data){
		var rowindex = $this.find("tr").length;
		var tmp = "<tr><td class='oper'>"+rowindex+"</td>";	//1 序号
		tmp += "<td class='oper'><select name='patameter_type' >";
			for(var ii=0; ii<settings.patameter_type.length; ii++){
			var rowTmp = settings.patameter_type[ii];
			tmp += "<option " 
			+" value='"+rowTmp.key+"'>"+rowTmp.title+"</option>";
			}
		tmp += "</select></td>";	//参数类型
		tmp += "<td name='patameter_name'></td>";	//3 参数名
		tmp += "<td name='patameter_value'></td>";	//5 参数值
		tmp += "<td class='oper'><select name='patameter_style' >";
			for(var ii=0; ii<settings.patameter_style.length; ii++){
			var rowTmp = settings.patameter_style[ii];
			tmp += "<option " 
			+" value='"+rowTmp.key+"'>"+rowTmp.title+"</option>";
			}
		tmp += "<td class='oper'><a title='保存修改' onclick='func_record_updateRecord(\""+rowindex+"\");' href='javascript:;'>"+
			"<div class='edit'></div></a>"+
			/*"<a href='javascript:;' title='删除' onclick='deleteDataItem(\""+rowindex+"\");' ><div class='delete'></div></a>"+*/
			"</td>";	//8
		tmp += "</tr>";	
		$this.find("tr:last").after(tmp);
		/*$this.find("tr:last td:eq(3) select").onchage(function(){
			$(this).find
		})*/
	}


	function _initRow(data){
		
		if(typeof data != 'undefined'){
		for(var ii=0; ii<data.length; ii++){
			var row = data[ii];
			var tmp = "";
			tmp += "<tr><td class='oper'>"+(ii+1)+"</td>";	//1 序号
			tmp += "<td class='oper'><select name='patameter_type' >";
			for(var jj=0; jj<settings.patameter_type.length; jj++){
				var rowTmp = settings.patameter_type[jj];
				tmp += "<option " + (row.patameter_type==rowTmp.key ? "selected" : "") 
				+" value='"+rowTmp.key+"'>"+rowTmp.title+"</option>";
			}
			tmp += "<td name='patameter_name'>"+row.patameter_name+"</td>";	// 参数名
			tmp += "<td name='patameter_value'>"+row.patameter_value+"</td>";	// 参数值
			tmp += "<td class='oper'><select name='patameter_style' >";
			for(var jj=0; jj<settings.patameter_style.length; jj++){
				var rowTmp = settings.patameter_style[jj];
				
				tmp += "<option " + (row.patameter_style==rowTmp.key ? "selected" : "") 
				+" value='"+rowTmp.key+"'>"+rowTmp.title+"</option>";
			}
			tmp += "<td class='oper'><a title='修改' onclick='func_record_updateRecord(\""+(ii+1)+"\");' href='javascript:;'>"+
				"<div class='edit'></div></a>"+
				/*"<a href='javascript:;' title='删除' onclick='deleteDataItem(\""+(ii+1)+"\");' ><div class='delete'></div></a>"+*/
				"<input name='webservice_patameter_id' type='hidden' value='"+
				row.webservice_patameter_id+"' /></td>";	//8
			tmp += "</tr>";	
			$this.find("tr:last").after(tmp);
		}
		}
	}
	
	function _tableStyle() {
		$this.find(settings.selectedRow).removeClass(settings.selectedRow);
		$this.find("tr:gt(0)").bind("mouseover", function() {
			$(this).addClass(settings.activeRowClass);
		});
		$this.find("tr:gt(0)").bind("mouseout", function() {
			$(this).removeClass(settings.activeRowClass);
		});

		if (typeof settings.onclick === "function") {
			$this.find("tr:gt(0)").unbind("click");
			$this.find("tr:gt(0)").bind("click", settings.onclick);
		}

		if (typeof settings.dblclick === "function") {
			$this.find("tr:gt(0)").unbind("dblclick");
			$this.find("tr:gt(0)").bind("dblclick", settings.dblclick);
		}
		//console.log($this.html());
		if (settings.addDelete) {
			_addDelete();
		}
	}

	function _editable() {
		var edit = settings.editable;	
		$this.find("."+settings.editableClass).removeClass(settings.editableClass);
		
		var s = "td:gt(999)";
		if (typeof edit === 'boolean' && edit) {
			s = "td";
			$this.find("tr:gt(0) "+s).not(".oper").not("lineNumber").addClass(settings.editableClass);
		} else if (typeof edit == 'number') {
			s = "td:eq(" + ((edit > 0) ? (edit - 1) : 999) + ")";
			$this.find("tr:eq(0) "+s).not(".oper").not("lineNumber").addClass(settings.editableClass);
		}
		$this.find("tr:gt(0)").each(function(index) {
			$(this).find(s).not(".oper").not(".lineNumber").unbind("click");
			$(this).find(s).not(".oper").not(".lineNumber").addClass(settings.editableClass);
			$(this).find(s).not(".oper").not(".lineNumber").click(function() {
				var td = $(this);
				var old_text = td.text();
				var old_html = td.html();
				td.html("<input class='new_value' type='text' value='" + old_text + "' />");
				var input = td.find("input.new_value");
				input.click(function(event) {
					return false;
				});
				input.width(td.width());
				input.trigger("focus").trigger("select");
				if(settings.isAlias){
					input.blur(function() {
						var blur = $(this);
						if(!_checkAlias(blur,td,old_text,old_html,index)){
							return;
						}
					});
					input.keyup(function(event) {
						var keyEvent = event || window.event;
						var key = keyEvent.keyCode;
						switch (key) {
							case 13:
								var blur = $(this);
								if(!_checkAlias(blur,td,old_text,old_html,index)){
									return;	
								}
								break;
							case 27:
								td.html(old_html);
								break;
						}
					});
				}else{
					//td.html(input.val());
					input.blur(function() {
						td.html(input.val());
					});
					input.keyup(function(event) {
						var keyEvent = event || window.event;
						var key = keyEvent.keyCode;
						switch (key) {
							case 13:
								td.html(input.val());
								break;
							case 27:
								td.html(old_html);
								break;
						}
					});
				}
			});
		});
		if (typeof edit == 'object' && edit.constructor == Array) {
			if(edit.length>0){
				$this.find("tr:eq(0) td:eq(" + ((edit[ii] > 0) ? (edit[ii] - 1) : 999) + ")").not(".oper").not("lineNumber").addClass(settings.editableClass);
			}
			for (var ii = 0; ii < edit.length; ii++) {
				$this.find("tr:gt(0)").each(function() {
					$(this).find("td:eq(" + ((edit[ii] > 0) ? (edit[ii] - 1) : 999) + ")").not(".oper").not("lineNumber").unbind("click");
					$(this).find("td:eq(" + ((edit[ii] > 0) ? (edit[ii] - 1) : 999) + ")").not(".oper").not("lineNumber").addClass(settings.editableClass);
					$(this).find("td:eq(" + ((edit[ii] > 0) ? (edit[ii] - 1) : 999) + ")").not(".oper").not("lineNumber").click(function() {
						var td = $(this);
						if(settings.isAlias){
						if(blur.val()!=''){
						var reg = new RegExp("^[a-zA-Z]\\w+$");
    					var re = reg.test(blur.val());
    					if(!re){
    						alert("请输入合法的别名,\n以字母开头,\n不能为中文,\n至少两位长度");
							td.html(old_html);
    						return;
    					}
    				}}
					var cellIndex = blur.parent()[0].cellIndex;
					var $tbl = blur.parent().parent().parent().find("tr:gt(0)");
					if(settings.isAlias){
					for(var ii=0; ii<$tbl.length; ii++){
						var tmp = $($tbl[ii]).find("td:eq("+cellIndex+")").text();
						if(blur.val()!= '' && tmp == blur.val().trim()){
							alert("第 "+(index+1)+" 行别名重复,请更换新的别名.");
							td.html(old_html);
    						return;
						}
					}}
						var old_text = td.text();
						var new_text = "";
						var input_hidden = td.find("input");
						td.html("<input class='new_value' type='text' value='" + old_text + "' />");
						var input = td.find("input.new_value");
						input.click(function(event) {
							return false;
						});
						input.width(td.width());
						input.trigger("focus").trigger("select");
						if(settings.isAlias){
						input.blur(function() {
							var blur = $(this);
							td.html(blur.val() + old_html.replace(old_text, ''));
							var old_data = _getAllData();
							var rowdata = _getRowData(index + 1);
							old_data = eval(old_data);
							rowdata = eval(rowdata);
							var check_result = false;
							var row_no = 0;
							for (var ii = 0; ii < old_data.length; ii++) {
								if (check_result = _compObj(old_data[ii], rowdata[0])) {
									row_no = ii;
									check_result = true;
									break;
								}
							}
							if (check_result && row_no != index) {
								alert("第 " + (row_no + 1) + " 行已存在相同数据");
								td.html(old_html);
							}
						});
						input.keyup(function(event) {
							var keyEvent = event || window.event;
							var key = keyEvent.keyCode;
							switch (key) {
								case 13:
									var blur = $(this);
									if(!_checkAlias(blur,td,old_text,old_html,index))
									return;	
									
									break;
								case 27:
									td.html(old_html);
									break;
							}
						});
						}else{
							td.html(input.val());
						}
					});
				});
				
			}
		}
	}

	function _addDelete() {
		if ($this.find(".oper").length == 0) {
			$this.find("tr:lt(1) td:last").after("<td class='oper'>操作</td>");
			$this.find("tr:gt(0)").each(function(index) {
				//var tableid = settings.data[(index+1)].table.id+"";
				//var columnid = settings.data[(index+1)].column.id+"";
				//$(this).find("td:last").after("<td class='oper'><a href='javascript:deleteReturn(\""+tableid+"\",\""+columnid+"\");' >删除</a></td>")
//			      $(this).find("td:last").after("<td class='oper'><a href='javascript:void(\"0\");' onclick='deleteReturn("+index+");' >删除</a></td>")
				$(this).find("td:last").after("<td class='oper'><a style='cursor:hand;'>删除</a></td>")
			});
			$this.find("tr:gt(0) td.oper a").each(function(index) {
				$(this).bind("click", function(event) {
					var ids = $(this).parent().parent().parent().parent().parent().attr('id');
					$("#"+ids).tablet("removeRow", (index+1));
					event.stopPropagation();
				});
			});
			$this.find("tr:gt(0) td.oper").each(function(index) {
				$(this).bind("click", function(event) {
					event.stopPropagation();
				});
			});
		} else {
			$this.find(".oper").remove();
			_addDelete();
		}
	}

	function _addData(tr_data) {
		var data = tr_data;
		var rowdata = "";
		if (data.constructor == Array ) {
			for (var ii = 0; ii < data.length; ii++) {
				var tmp = _addRow(data[ii]);
				if (tmp) {
					rowdata += tmp;
				}
			}
		}else if(data.constructor == Object){
			rowdata = _addRow(data);
		}
		if(rowdata.length > 0){
			$this.find("tr:last").after(rowdata);
		}
	};

	function _addRow(data) {
		var old_data = _getAllData();
		old_data = eval(old_data);
		var check_result = false;
		var row_no = 0;
		for(var ii=0; ii<old_data.length; ii++){
			if(check_result = _compObj(old_data[ii], data)){
				row_no = ii;
				check_result = true;
				break;
			}
		}
		var thead = _getThead();
		var tmp = "";
		if (!check_result) {
			tmp = "<tr>";
			for(var ii=0; ii<thead.length; ii++){
				var x = thead[ii];
				if (typeof data[x] === "object") {
					tmp += "<td>";
					var size = 0;
					var count = 0;
//					for (y in data[x]) {
//						size++;
//					}
					for (y in data[x]) {
			//			count++;
			//			if (count == size) {
			//				tmp += data[x][y];
			//			}
						tmp += '<input type="hidden" value="' + data[x][y] 
							+ '" name="' + y + '" />';
					}
					tmp += data[x].name_cn;
					tmp += "</td>"
				} else if(typeof data[x] == "string"){
					tmp += "<td>" + data[x] + "</td>";
				}else {
					tmp += "<td></td>";
				}
			}
			tmp += "</tr>";
		}else{
			alert("第 " + (row_no+1)+ " 行存在相同数据" );
		}
		return tmp;
	}

	//考虑删除
	function _removeRow(index) {
		var data_delete = _getRowData(index);
		$this.find("tr:eq(" + index + ")").remove();
		data_delete = eval('(' + data_delete + ')');
		return data_delete.data;
	};

	function _getThead(){
		var thead = [];
		$this.find("tr:eq(0) td input").not(".oper").not(".lineNumber").each(function(){
			thead.push($(this).val());
		});
		return thead;
	}
	
	function _setLineNumber(){
		$this.find("tr:eq(0) td:eq(0)").before("<td class='lineNumber'>序号</td>");
		$this.find("tr:gt(0)").each(function(index){
			$(this).find("td:eq(0)").before("<td>"+(index+1)+"</td>");
		});
	}
	
	function _init() {
		var tb = "<table align='center' cellpadding=0 cellspacing=0 class='tablet_table'>";
		if (settings == "undefined" || typeof settings != "object") return;
		var data = settings.data || [];
		var tmp = "";
		for (var col in data[0]) {
			tmp += "<td>" + data[0][col] + '<input type="hidden" name="' + col + '" value="' + col + '" />'
			+"</td>";
		}
		tb = tb+"<tr class='tablet_thead'>"+tmp+"</tr></table>";
		$this.html(tb);
		var body_data = [];
		for(var ii=1; ii<data.length; ii++){
			body_data.push(data[ii]);
		}
		_addData(body_data);
		_getThead();
	};

	function _getRowData(which) {
		var row_str = "";
		$this.find("tr:eq(" + which + ") td").not(".oper").each(function(index) {
			var inputsize = $(this).find("input").length;
			var input_str = "";
			if (inputsize != 0) {
				$(this).find("input").each(function(index) {
					input_str += "\"" + $(this).attr("name") + "\" : \"" + $(this).val() + "\",";
				});
				input_str = input_str.substring(0, input_str.lastIndexOf(","));
				input_str = "{" + input_str + "}";
			} else {
				input_str = "\"" + $(this).text() + "\"";
			}
			input_str = "\"" + $this.find("tr:eq(0) td:eq(" + index + ") input").val() + "\" : " + input_str;
			row_str += input_str + " ,";
		});
		if(row_str.length > 1){
			row_str = row_str.substring(0, row_str.lastIndexOf(",") - 1);
		}

		return "{data:[{"+row_str+"}]}";
	};

	function _getAllData() {
		var data_json_str = "";
		$this.find("tr:gt(0)").each(function() {
			data_json_str += "{";
			$(this).find("td").not(".oper").each(function(index) {
				var inputsize = $(this).find("input").length;
				var input_str = "";
				if (inputsize != 0) {
					$(this).find("input").each(function(index) {
						input_str += "\"" + $(this).attr("name") + "\" : \"" + $(this).val() + "\",";
					});
					input_str = input_str.substring(0, input_str.lastIndexOf(","));
					input_str = "{" + input_str + "}";
				} else {
					input_str = "\"" + $(this).text() + "\"";
				}

				input_str = "\"" + $this.find("tr:eq(0) td:eq(" + index + ") input").val() + "\" : " + input_str;
				data_json_str += input_str + " ,";
			});

			data_json_str = data_json_str.substring(0, data_json_str.lastIndexOf(",") - 1);
			data_json_str += "},";
		});
		if (data_json_str.length > 0) {
			data_json_str = data_json_str.substring(0, data_json_str.lastIndexOf(",") - 1);
			data_json_str += "}";
		}

		data_json_str = "[" + data_json_str + "]";
		//console.log("{data:" + data_json_str + "}");
		return "{data:" + data_json_str + "}";
	};

	function _propertyLength(obj) {
		var count = 0;
		if (typeof obj === "object" && (obj.constructor == Object)) {
			for (var ooo in obj) {
				if (obj.hasOwnProperty(ooo)) {
					count++;
				}
			}
			return count;
		}
	};

	//比较两个对象是否相等，忽略原形上的属性
	function _compObj(obj1, obj2) {
		if ((typeof obj1 === "object") && ((typeof obj2 === "object"))) {
			var count1 = _propertyLength(obj1);
			var count2 = _propertyLength(obj2);
			if (count1 == count2) {
				for (var ob in obj1) {
					if (obj1.hasOwnProperty(ob) && obj2.hasOwnProperty(ob)) {
						//如果属性是数组
						if (obj1[ob].constructor == Array && obj2[ob].constructor == Array)  {
							if (!_compArray(obj1[ob], obj2[ob])) {
								return false;
							};
						} else if (typeof obj1[ob] === "string" && typeof obj2[ob] === "string")  { //纯属性
							if (obj1[ob].toLowerCase() !== obj2[ob].toLowerCase()) {
								return false;
							}
						} else if (typeof obj1[ob] === "object" && typeof obj2[ob] === "object") {//属性是对象
							if (!_compObj(obj1[ob], obj2[ob])) {
								return false;
							};
						} else {
							return false;
						}
					} else {
						return false;
					}
				}
			} else {
				return false;
			}
		}

		return true;
	};

	function _compArray(array1, array2) {
		if ((typeof array1 === "object" && array1.constructor === Array) 
			&& (typeof array2 === "object" && array2.constructor === Array)) {
			if (array1.length == array2.length) {
				for (var i = 0; i < array1.length; i++) {
					var r = _compObj(array1[i].toLowerCase(), array2[i].toLowerCase());
					if (!r) {
						return false;
					}

				}

			} else {
				return false;
			}
		}
		return true;
	}
	
	function _checkAlias(blur,td,old_text,old_html,index){
		if(blur.val()!=''){
		var reg = new RegExp("^[a-zA-Z]\\w+$");
		var re = reg.test(blur.val());
	   if(!re){
			alert("请输入合法的别名,\n以字母开头,\n不能为中文,\n至少两位长度");
			td.html(old_html);
			return false;
			}
			}
		var cellIndex = blur.parent()[0].cellIndex;
		var $tbl = blur.parent().parent().parent().find("tr:gt(0)");
		for(var ii=0; ii<$tbl.length; ii++){
		var tmp = $($tbl[ii]).find("td:eq("+cellIndex+")").text();
		if(blur.val()!= '' && tmp == blur.val().trim()){
			alert("第 "+(index+1)+" 行别名重复,请更换新的别名.");
			td.html(old_html);
		return false;
		}
		}
		td.html(blur.val() + old_html.replace(old_text, ''));
		var old_data = _getAllData();
		var rowdata = _getRowData(index + 1);
		old_data = eval(old_data);
		rowdata = eval(rowdata);
		var check_result = false;
		var row_no = 0;
		for (var ii = 0; ii < old_data.length; ii++) {
			if (check_result = _compObj(old_data[ii], rowdata[0])) {
			row_no = ii;
			check_result = true;
			break;
		}
}
if (check_result && row_no != index) {
alert("第 " + (row_no + 1) + " 行已存在相同数据");
td.html(old_html);
}
	}



})(jQuery);
//function deleteReturn(index){
	//return false;
//}