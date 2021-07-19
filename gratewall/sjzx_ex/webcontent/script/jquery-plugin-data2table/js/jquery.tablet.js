
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
				shownum:5,	//Ĭ����ʾ����
				onDelete: null,
				lineNum: false,	//�Ƿ���ʾ�к�
				isAlias: true,	//�Ƿ�֧�������ֶα������
				dataitem_type: null,
				code_table: null,
				isPage: true,
				pageRow: 10
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
				var show = settings.shownum;
				if($this.find("tr:gt(0)").length > show){
					$this.height(show*25+30);
					$this.css("overflow-y", "scroll");
					$this.css("float", "left");
				}
			});
		},
		initRow: function(){
			var data = arguments[0];
			return this.each(function(){
				_initRow(data);
				_tableStyle();
				_editable();
				var show = settings.shownum;
				if($this.find("tr:gt(0)").length > show){
					$this.height(show*25+30);
					$this.css("overflow-y", "scroll");
					$this.css("float", "left");
				}
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
					settings.onDelete.apply(this, delData);  //ִ��ɾ������ִ���û��Զ���Ĳ���
				}
				if(settings.lineNum){
					_setLineNumber();
				}
			});
		},
		removeAllData: function(){
	//		if(($this.find("tr:gt(0)").length > 0)){
//				if(confirm("ȷ��Ҫɾ������������?")){
					$this.find("tr:gt(0)").remove();
	//			}
	//		}else{
	//			alert("û������");
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
		},
		length: function(){
			return $this.find("tr:gt(0)").length;
		},
		insertParam: function(){
			var data = arguments[0];
			return this.each(function(){
				_insertRowParam(data);
				_tableStyle();
				_editable();
				var show = settings.shownum;
				if($this.find("tr:gt(0)").length > show){
					$this.height(show*25+30);
					$this.css("overflow-y", "scroll");
					$this.css("float", "left");
				}
			});
		},
		initData: function(){
			var data = arguments[0];
			return this.each(function(){
				_initData(data);
				_tableStyle();
				_editable();
				var show = settings.shownum;
				if($this.find("tr:gt(0)").length > show){
					$this.height(show*25+30);
					$this.css("overflow-y", "scroll");
					$this.css("float", "left");
				}
			});
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
	
	function _insertRowParam(data){
		var rowindex = $this.find("tr").length;
		var tmp = "<tr><td class='oper'>"+rowindex+"</td>";	//1 ���
		//tmp += "<td name='param_value_type'></td>";	//2��������
		tmp += "<td class='oper'><select name='param_value_type' >";
		for(var ii=0; ii<settings.param_value_type.length; ii++){
			var rowTmp = settings.param_value_type[ii];
			tmp += "<option " 
			+" value='"+rowTmp.key+"'>"+rowTmp.title+"</option>";
		}
		tmp += "</select></td>";	//2��������
		
		tmp += "<td name='patameter_name'></td>";	//3 ������
		tmp += "<td name='patameter_value'></td>";	//4 ����ֵ
		//tmp += "<td name='style'></td>";	//5������ʽ
		tmp += "<td class='oper'><select name='param_value_type' >";
		for(var ii=0; ii<settings.style.length; ii++){
			var rowTmp = settings.style[ii];
			tmp += "<option " 
			+" value='"+rowTmp.key+"'>"+rowTmp.title+"</option>";
		}
		tmp += "</select></td>";	//5������ʽ
		
		tmp += "<td name='showorder'></td>";//6����˳��
		tmp += "<td class='oper'>"+
			"<a href='javascript:;' title='ɾ��' onclick='deleteDataItem(\""+rowindex+"\");' ><div class='delete'></div></a>"+
			"</td>";	//7����
		tmp +="<td name='ws_param_value_id' style='display:none;' ></td>"
		tmp += "</tr>";	
		$this.find("tr:last").after(tmp);
	}
	function _initData(data){
		var rowindex = $this.find("tr").length;
		var tmp = "<tr><td class='oper'>"+rowindex+"</td>";	//1 ���
		//tmp += "<td name='param_value_type'></td>";	//2��������
		tmp += "<td class='oper'><select name='param_value_type' >";
		for(var ii=0; ii<settings.param_value_type.length; ii++){
			var rowTmp = settings.param_value_type[ii];
			if(rowTmp.key==data.param_value_type){
				//alert("key="+rowTmp.key+"---title="+rowTmp.title+"---type="+data.param_value_type);
				tmp += "<option " 
					+" value='"+rowTmp.key+"' selected='selected' >"+rowTmp.title+"</option>";
			}else{
				tmp += "<option " 
					+" value='"+rowTmp.key+"'>"+rowTmp.title+"</option>";
			}
			
		}
		tmp += "</select></td>";	//2��������
		
		tmp += "<td name='patameter_name'>"+data.patameter_name+"</td>";	//3 ������
		tmp += "<td name='patameter_value'>"+data.patameter_value+"</td>";	//4 ����ֵ
		//tmp += "<td name='style'></td>";	//5������ʽ
		tmp += "<td class='oper'><select name='param_value_type' >";
		for(var ii=0; ii<settings.style.length; ii++){
			var rowTmp = settings.style[ii];
			if(rowTmp.key==data.style){
				tmp += "<option " 
					+" value='"+rowTmp.key+"'selected='selected' >"+rowTmp.title+"</option>";
			}else{
				tmp += "<option " 
					+" value='"+rowTmp.key+"'>"+rowTmp.title+"</option>";
			}
			
		}
		tmp += "</select></td>";	//5������ʽ
		
		tmp += "<td name='showorder'>"+data.showorder+"</td>";//6����˳��
		tmp += "<td class='oper'>"+
			"<a href='javascript:;' title='ɾ��' onclick='deleteDataItem(\""+rowindex+"\");' ><div class='delete'></div></a>"+
			"</td>";	//7����
		tmp += "<td name='ws_param_value_id' style='display:none;' >"+data.ws_param_value_id+"</td>"
		
		tmp += "</tr>";	
		$this.find("tr:last").after(tmp);
	}
	
	function _insertRow(data){
		var rowindex = $this.find("tr").length;
		var tmp = "<tr><td class='oper'>"+rowindex+"</td>";	//1 ���
		tmp += "<td name='dataitem_name_en'></td>"; //2 ����������
		tmp += "<td name='dataitem_name_cn'></td>";	//3 ������
		tmp += "<td class='oper'><select name='dataitem_type' >";
		for(var ii=0; ii<settings.dataitem_type.length; ii++){
			var rowTmp = settings.dataitem_type[ii];
			tmp += "<option " 
			+" value='"+rowTmp.key+"'>"+rowTmp.title+"</option>";
		}
		tmp += "</select></td>";	//4 ����������
		tmp += "<td name='dataitem_long'></td>";	//5 �������
		tmp += "<td class='oper'><select name='is_key'><option value='0' selected>��</option><option value='1'>��</option></select></td>"	;	//6 �Ƿ�����
		tmp += "<td class='oper'><select id='code_table' >";
		for(var ii=0; ii<settings.code_table.length; ii++){
			var rowTmp = settings.code_table[ii];
			tmp += "<option " 
			+" value='"+rowTmp.key+"'>"+rowTmp.title+"</option>";
		}
		/*"<option selected value='00'>��</option>"+
			"<option value='01'>��ҵ����</option><option value='02'>��ҵ����</option><option value='03'>����</option>"+
		*/
		tmp += "</select></td>";	//7 ��Ӧ�����
		tmp += "<td class='oper'><a href='javascript:;' title='ɾ��' onclick='deleteDataItem();' >"+
			"<div class='delete'></div></a></td>";	//8
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
			tmp += "<tr><td class='oper'>"+(ii+1)+"</td>";	//1 ���
			tmp += "<td name='dataitem_name_en' >"+row.dataitem_name_en+"</td>";
			tmp += "<td name='dataitem_name_cn' >"+row.dataitem_name_cn+"</td>";
			tmp += "<td class='oper'><select name='dataitem_type' >";
			for(var jj=0; jj<settings.dataitem_type.length; jj++){
				var rowTmp = settings.dataitem_type[jj];
				tmp += "<option " + (row.dataitem_type==rowTmp.key ? "selected" : "") 
				+" value='"+rowTmp.key+"'>"+rowTmp.title+"</option>";
			}
				/*"<option " + (row.dataitem_type=="01" ? "selected" : "") +" value='01'>CHAR</option><option " +
						(row.dataitem_type=='02' ? "selected" : "") +" value='02'>VACHAR2</option><option " +
						(row.dataitem_type=='03' ? "selected" : "") +" value='03'>INT</option><option " +
						(row.dataitem_type=='04' ? "selected" : "") +" value='04'>DATE</option><option " +
						(row.dataitem_type=='05' ? "selected" : "") +" value='05'>TIMESTAMP</option>"*/
				+"</select></td>";	//4 ����������
			tmp += "<td name='dataitem_long'>"+row.dataitem_long+"</td>";	//5 �������
			tmp += "<td class='oper'><select name='is_key'><option " +
				(row.is_key=="0" ? "selected" : "") +" value='0' selected>��</option><option " +
				(row.is_key=="1" ? "selected" : "") +" value='1'>��</option></select></td>"	;	//6 �Ƿ�����
			tmp += "<td class='oper'><select id='code_table' >";
			for(var jj=0; jj<settings.code_table.length; jj++){
				rowTmp = settings.code_table[jj];
				tmp += "<option " + (row.code_table==rowTmp.key ? "selected" : "") 
				+" value='"+rowTmp.key+"'>"+rowTmp.title+"</option>";
			}
			/*"<option " +
				(row.code_table=="00" ? "selected" : "") +" value='00'>��</option>"+
				"<option " + (row.code_table=="01" ? "selected" : "") +" value='01'>��ҵ����</option><option " +
				(row.code_table=="02" ? "selected" : "") +" value='02'>��ҵ����</option><option " + 
				(row.code_table=="03" ? "selected" : "") +" value='03'>����</option>"+*/
			tmp += "</select></td>";	//7 ��Ӧ�����
			tmp += "<td class='oper'><a href='javascript:;' title='ɾ��' onclick='deleteDataItem(\""+(ii+1)+"\");' >"+
				"<div class='delete'></div></a><input name='collect_dataitem_id' type='hidden' value='"+
				row.collect_dataitem_id+"' /><input name='is_code_table' type='hidden' value='"+row.is_code_table+"' /></td>";	//8
			tmp += "</tr>";	
			$this.find("tr:last").after(tmp);
		}
		}
	}
	
	function _setPage(){
		if(settings.isPage){
			$this.find('tbody:first')
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
    						alert("������Ϸ��ı���,\n����ĸ��ͷ,\n����Ϊ����,\n������λ����");
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
							alert("�� "+(index+1)+" �б����ظ�,������µı���.");
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
								alert("�� " + (row_no + 1) + " ���Ѵ�����ͬ����");
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
			$this.find("tr:lt(1) td:last").after("<td class='oper'>����</td>");
			$this.find("tr:gt(0)").each(function(index) {
				//var tableid = settings.data[(index+1)].table.id+"";
				//var columnid = settings.data[(index+1)].column.id+"";
				//$(this).find("td:last").after("<td class='oper'><a href='javascript:deleteReturn(\""+tableid+"\",\""+columnid+"\");' >ɾ��</a></td>")
//			      $(this).find("td:last").after("<td class='oper'><a href='javascript:void(\"0\");' onclick='deleteReturn("+index+");' >ɾ��</a></td>")
				$(this).find("td:last").after("<td class='oper'><a style='cursor:hand;'>ɾ��</a></td>")
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
			alert("�� " + (row_no+1)+ " �д�����ͬ����" );
		}
		return tmp;
	}

	//����ɾ��
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
		$this.find("tr:eq(0) td:eq(0)").before("<td class='lineNumber'>���</td>");
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

	//�Ƚ����������Ƿ���ȣ�����ԭ���ϵ�����
	function _compObj(obj1, obj2) {
		if ((typeof obj1 === "object") && ((typeof obj2 === "object"))) {
			var count1 = _propertyLength(obj1);
			var count2 = _propertyLength(obj2);
			if (count1 == count2) {
				for (var ob in obj1) {
					if (obj1.hasOwnProperty(ob) && obj2.hasOwnProperty(ob)) {
						//�������������
						if (obj1[ob].constructor == Array && obj2[ob].constructor == Array)  {
							if (!_compArray(obj1[ob], obj2[ob])) {
								return false;
							};
						} else if (typeof obj1[ob] === "string" && typeof obj2[ob] === "string")  { //������
							if (obj1[ob].toLowerCase() !== obj2[ob].toLowerCase()) {
								return false;
							}
						} else if (typeof obj1[ob] === "object" && typeof obj2[ob] === "object") {//�����Ƕ���
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
			alert("������Ϸ��ı���,\n����ĸ��ͷ,\n����Ϊ����,\n������λ����");
			td.html(old_html);
			return false;
			}
			}
		var cellIndex = blur.parent()[0].cellIndex;
		var $tbl = blur.parent().parent().parent().find("tr:gt(0)");
		for(var ii=0; ii<$tbl.length; ii++){
		var tmp = $($tbl[ii]).find("td:eq("+cellIndex+")").text();
		if(blur.val()!= '' && tmp == blur.val().trim()){
			alert("�� "+(index+1)+" �б����ظ�,������µı���.");
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
alert("�� " + (row_no + 1) + " ���Ѵ�����ͬ����");
td.html(old_html);
}
	}



})(jQuery);
//function deleteReturn(index){
	//return false;
//}