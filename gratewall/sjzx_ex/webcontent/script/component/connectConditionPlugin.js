var connectCondition = function(config){
//	alert("connectConditionPlugin.js");
	if (!config.id){
        alert("没有传入组件的ID！请在json中用参数id传递过来！");
        return;
    }
    
	if (!config.parentContainer){
		alert("没有定义父容器的ID！请在json中用parentContainer参数传递过来！");
		return;
	}
	
	if (!config.getColumnSrcPrefix){
		alert("没有传入获取字段列表的URL前缀！请在json中用参数getColumnSrcPrefix传递过来！");
		return;
	}
	
	this.id = config.id;
	this.src = config.getColumnSrcPrefix;
	//this.connArray = new Array();
	this.connJSONArray = new Array();
	
	if (document.getElementById(this.id + "Condition")){
		alert("名称【" + this.id + "】已经存在，请更换");
		return;
	}
	
	this.tableConnectTableHtml =   '  <table width="95%" border="0" align="center" id="' + this.id + 'Container" style="display:none" class="frame-body" cellpadding="0" cellspacing="0">' + 
									'     <tr class="title-row">' + 
									'       <td colspan="9"><table cellspacing="0" cellpadding="0" width="100%"><tr><td class="leftTitle"></td><td class="secTitle">数据表查询条件</td><td class="rightTitle"></td></tr></table></td>' + 
									'     </tr>' + 
									'     <tr><td width="100%"><table border="0" cellspacing="0" id="' + this.id + '_topTopTable" cellpadding="0" width="100%">' +
									'     <tr class="grid-headrow" align="center">' + 
									'       <td width="7%"  style="border-right:1px solid #CCCCCC;">条件</td>' + 
									'       <td width="6%"  style="border-right:1px solid #CCCCCC;">括弧</td>' + 
									'       <td width="14%" style="border-right:1px solid #CCCCCC;">数据表</td>' + 
									'       <td width="18%" style="border-right:1px solid #CCCCCC;">表字段</td>' + 
									'       <td style="border-right:1px solid #CCCCCC;">连接关系</td>' + 
									'       <td width="14%" style="border-right:1px solid #CCCCCC;">数据表</td>' + 
									'       <td width="18%" style="border-right:1px solid #CCCCCC;">表字段</td>' + 
									'       <td width="6%" style="border-right:1px solid #CCCCCC;">括弧</td>' + 
									'       <td width="7%">操作</td>' + 
									'     </tr>' + 
									'     <tr class="framerow selectRow">' + 
									'       <td><select name="' + this.id + 'Condition" id="' + this.id + 'Condition" class="connectConditionSelect" disabled="true">' + 
									'         <option selected="selected"></option>' + 
									'       </select></td>' + 
									'       <td><select name="' + this.id + 'PreParen" id="' + this.id + 'PreParen" class="connectConditionSelect">' + 
									'         <option selected="selected"></option>' + 
									'         <option value="(">(</option>' + 
									'         <option value="((">((</option>' +
									'         <option value="(((">(((</option>' +
									'         <option value="((((">((((</option>' +
									'         <option value="(((((">(((((</option>' +
									'       </select></td>' + 
									'       <td><select name="' + this.id + 'TableGhost" id="' + this.id + 'TableGhost" class="connectConditionSelect" style="display:none"></select>' + 
									'          <select name="' + this.id + 'TableOne" id="' + this.id + 'TableOne" class="connectConditionSelect"></select></td>' + 
									'       <td><select name="' + this.id + 'ColumnOne" id="' + this.id + 'ColumnOne" class="connectConditionSelect"></select></td>' + 
									'       <td><select name="' + this.id + 'Relation" id="' + this.id + 'Relation" disabled class="connectConditionSelect"><option value="=" selected="selected">等于</option></select></td>' + 
									'       <td><select name="' + this.id + 'TableTwo" id="' + this.id + 'TableTwo" class="connectConditionSelect"></select></td>' + 
									'       <td><select name="' + this.id + 'ColumnTwo" id="' + this.id + 'ColumnTwo" class="connectConditionSelect"></select></td>' + 
									'       <td><select name="' + this.id + 'PostParen" id="' + this.id + 'PostParen" class="connectConditionSelect">' + 
									'          <option selected="selected"></option>' + 
									'          <option value=")">)</option>' + 
									'          <option value="))">))</option>' + 
									'          <option value=")))">)))</option>' + 
									'          <option value="))))">))))</option>' + 
									'          <option value=")))))">)))))</option>' + 
									'       </select></td>' + 
									'       <td><table cellspacing="0" cellpadding="0" class="button_table"><tr><td class="btn_left"></td><td><input type="button" id="'+ this.id + 'AddButton" name="'+ this.id + 'AddButton" value="添加" class="menu" /></td><td class="btn_right"></td></tr></table></td>' + 
									'     </tr></table></td></tr>' + 
									'     <tr id="'+ this.id +'hideRow" style="display:none" class="framerow">'+
                                    '       <td colspan="9">'+
                                    '           <table id="conditionTbl_'+ this.id +'" border="0" cellpadding="0" cellspacing="0" width="100%" bordercolor="#1ABCD1" style="border-collapse:collapse"></table>'+
                                    '       </td>'+
                                    '     </tr>'+
									'   </table>';
	document.getElementById(config.parentContainer).innerHTML = this.tableConnectTableHtml;
    
    document.getElementById(this.id + "_topTopTable").onresize = function(){
    	if (this.offsetWidth < 600 && this.offsetWidth != 0){
    		this.style.width = 600;
    	}
    }
    setShowTitleSelectList(this.id + "TableOne", this.id + "ColumnOne", this.id + "TableTwo", this.id + "ColumnTwo", false);
    
	// 绑定事件
	this.addEvent();
	// 初始化(没有数据，暂时不初始化)
	// this.init();
}

// 添加事件
connectCondition.prototype.addEvent = function(){
	var p = this;
	// 选择表1时
	var tableOne = document.getElementById(this.id + "TableOne");
	if (tableOne){
		tableOne.onchange = function(){
			p.removeOptionFromSelect(p.id + "TableTwo", this.options[this.selectedIndex].value);
			p.getColumns(this.options[this.selectedIndex].value, p.id + "ColumnOne");
		};
	}
	tableOne = null;
	
	// 选择表2时
	var tableTwo = document.getElementById(p.id + "TableTwo");
    if (tableTwo){
        tableTwo.onchange = function(){
        	p.removeOptionFromSelect(p.id + "TableOne", this.options[this.selectedIndex].value)
        	p.getColumns(this.options[this.selectedIndex].value, p.id + "ColumnTwo");
        };
    }
    tableTwo = null;
    
    // 选择字段1时
    var ColumnOne = document.getElementById(p.id + "ColumnOne");
    if (ColumnOne){
    	ColumnOne.onchange = function(){
    		p.setDefaultColumn(p.id + "ColumnTwo", this.options[this.selectedIndex].text);
    	};
    }
    ColumnOne = null;
    
    // 添加事件
    var addButton = document.getElementById(this.id + "AddButton");
    if (addButton){
    	addButton.onclick = function(){
    		p.addConnCondition();
    	};
    }
    
    var delButton = document.getElementById("deleteConnCondition_"+p.id);
    if (delButton){
    	delButton.onclick = function(){
    		p.delConnCondition(delButton.rowIndex);
    	};
    }
};

/**
 * 从指定下拉框中删除指定选项
 * selectId: 下拉框ID
 * optionValue: 待删除选项的value
 */
connectCondition.prototype.removeOptionFromSelect = function(selectId, optionValue){
	var defineSelect = document.getElementById(selectId);
	
	var selectedValue = null;
	if (defineSelect.selectedIndex >= 0){
		selectedValue = defineSelect.options[defineSelect.selectedIndex].value;
	}
	
	defineSelect.innerHTML = "";
	var ghostSelect = document.getElementById(this.id + "TableGhost");
	var options = ghostSelect.options;
	for (var i = 0; i < options.length; i++){		
		if (options[i].value != optionValue){
			var tempOption = options[i].cloneNode(true);
			if (selectedValue && tempOption.value == selectedValue){
				tempOption.selected = "selected";
			}
			defineSelect.appendChild(tempOption);
			tempOption = null;
		}
	}
	defineSelect = null;
	ghostSelect = null;
}

// 默认选中columnTwo中的选项
connectCondition.prototype.setDefaultColumn = function(selectId, optionText){	
	var p = this;
	var defineSelectT = document.getElementById(p.id + "ColumnTwo");
	
	for(var i=0; i < defineSelectT.options.length; i++){		
		if (defineSelectT.options[i].text == optionText){
			defineSelectT.options[i].setAttribute("selected", "selected");
			defineSelectT.selectedIndex = i;
			defineSelectT = null;
			return;
		}
	}
	defineSelectT = null;
	p = null;
}

// 初始化
connectCondition.prototype.init = function(){	
	var tableGhost = document.getElementById(this.id + "TableGhost");	
	var options = tableGhost.options;
	if (options.length >= 2){
		// 显示连接条件组件
		document.getElementById(this.id + "Container").style.display = "block";
		this.removeOptionFromSelect(this.id + "TableOne" , options[1].value);
		this.removeOptionFromSelect(this.id + "TableTwo" , options[0].value);
		document.getElementById(this.id + "TableOne").onchange();
        document.getElementById(this.id + "TableTwo").onchange();
	}else{
		// 隐藏连接条件组件
		document.getElementById(this.id + "Container").style.display = "none";
		document.getElementById(this.id + "TableOne").innerHTML = "";
        document.getElementById(this.id + "TableTwo").innerHTML = "";
        document.getElementById(this.id + "ColumnOne").innerHTML = "";
        document.getElementById(this.id + "ColumnTwo").innerHTML = "";
//        document.getElementById(this.id + "AddButton").disabled = "true";
	}
}

// 设置选择数据表
connectCondition.prototype.setDataFromSelect = function(selectId){
	var tableGhost = document.getElementById(this.id + "TableGhost");
	tableGhost.innerHTML = "";
	var options = document.getElementById(selectId).options;
	for (var i = 0; i < options.length; i++){
		tableGhost.appendChild(options[i].cloneNode(true));
	}
	
	// 执行完毕后，再次执行初始化
	this.init();
}

// 获取表下的字段列表
connectCondition.prototype.getColumns = function(tableNo, columnSelectId){
	var p = this;
	var columnSelect = document.getElementById(columnSelectId);
	columnSelect.innerHTML = "";
	var tempOption = document.createElement("option");
	columnSelect.appendChild(tempOption);
	tempOption.text = "数据读取中...";
	tempOption = null;
	columnSelect = null;
	
	$.get(p.src + tableNo, function(xml){
		fillXmlToSelect(xml, "record", columnSelectId, "column_no", "column_name_cn", {type: "edit_type", codeValue: "demo", colName:"column_name"});
		document.getElementById(p.id + "ColumnOne").onchange();
	});
}

/** 
 * 添加一条表连接条件
 */
connectCondition.prototype.addConnCondition = function(){
	var p 	     = this;
	var con1 	 = document.getElementById(p.id + "Condition");
	var leftKH   = document.getElementById(p.id + "PreParen");
	var leftTbl  = document.getElementById(p.id + "TableOne");
	var leftCol  = document.getElementById(p.id + "ColumnOne");
	var con2 	 = document.getElementById(p.id + "Relation");
	var rightTbl = document.getElementById(p.id + "TableTwo");
	var rightCol = document.getElementById(p.id + "ColumnTwo");
	var rightKH  = document.getElementById(p.id + "PostParen");
	
	if(leftCol.options.length == 0 || rightCol.options.length == 0){
		alert("无可选字段，不能添加！");
		return;
	}
	//构造表的连接中文
	var content = "<font color=red>"+con1.options[con1.selectedIndex].text + " </font>" 
			    + "<font color=red>" + leftKH.options[leftKH.selectedIndex].text + " </font>" 
			    + leftTbl.options[leftTbl.selectedIndex].text + "<font color=red> 的 </font>"
			    + leftCol.options[leftCol.selectedIndex].text + " "
			    + "<font color=red>" + con2.options[con2.selectedIndex].text + " </font>"
				+ rightTbl.options[rightTbl.selectedIndex].text + "<font color=red> 的 </font>"
				+ rightCol.options[rightCol.selectedIndex].text + " "
				+ "<font color=red>" + rightKH.options[rightKH.selectedIndex].text + " </font>";
	
	var connJSONObj = {condition:con1.options[con1.selectedIndex].value, 
					   preParen :leftKH.options[leftKH.selectedIndex].value, 
					   tableOneId :leftTbl.options[leftTbl.selectedIndex].value, 
					   tableOneName :leftTbl.options[leftTbl.selectedIndex].tblName, 
					   tableOneNameCn :leftTbl.options[leftTbl.selectedIndex].text, 
					   columnOneId:leftCol.options[leftCol.selectedIndex].value, 
					   columnOneName:leftCol.options[leftCol.selectedIndex].colName, 
					   columnOneNameCn:leftCol.options[leftCol.selectedIndex].text, 
					   relation :con2.options[con2.selectedIndex].value, 
					   tableTwoId :rightTbl.options[rightTbl.selectedIndex].value, 
					   tableTwoName :rightTbl.options[rightTbl.selectedIndex].tblName,
					   tableTwoNameCn :rightTbl.options[rightTbl.selectedIndex].text, 
					   columnTwoId:rightCol.options[rightCol.selectedIndex].value, 
					   columnTwoName:rightCol.options[rightCol.selectedIndex].colName, 
					   columnTwoNameCn:rightCol.options[rightCol.selectedIndex].text,
					   postParen:rightKH.options[rightKH.selectedIndex].value,
					   paramText:content};
					
	if(p.exist(connJSONObj)){
		return;
	}else{
		addToArray(p.connJSONArray, connJSONObj);//添加到"数组"
	}
	var rowIndex = 0;
	var objTable = document.getElementById("conditionTbl_" + p.id);
	var newTr = objTable.insertRow();
	newTr.height = 30;

	//添加列
	var newTd0 = newTr.insertCell();
	newTd0.style.borderBottom = "1px solid #CCCCCC";
	var newTd1 = newTr.insertCell();
	newTd1.style.borderBottom = "1px solid #CCCCCC";

	//设置列内容和属性
	newTd0.innerHTML = content; 
	newTd0.width = "90%";
	newTd0.align = "left";
	newTd1.innerHTML= "<table cellspacing='0' cellpadding='0' class='button_table'><tr><td class='btn_left'></td><td><input type='button' id='deleteConnCondition_" + p.id + "' value=' 删除 ' class='menu' /></td><td class='btn_right'></td></tr></table>";
	newTd1.width = "10%";
	newTd1.align = "center";
	
	document.getElementById(p.id + "hideRow").style.display = "block";
	
	var delButton = document.getElementsByName("deleteConnCondition_"+p.id);
    var obj = delButton[delButton.length - 1];
    if (obj){
    	obj.onclick = function(){
    		if(confirm("确认删除此条件吗？")){
				p.deleteCondition(this.parentNode.parentNode.rowIndex);
    		}
    	};
    }
    
    if(p.connJSONArray.length != 0){
    	con1.options.length = 0;
    	con1.options.add(createOption('AND','并且'));
    	con1.options.add(createOption('OR','或者'));
    	con1.disabled = false;
    }
    
    con1 	 = null;
	leftKH   = null;
	leftTbl  = null;
	leftCol  = null;
	con2 	 = null;
	rightTbl = null;
	rightCol = null;
	rightKH  = null;
    obj      = null;
    delButton = null;
}

connectCondition.prototype.deleteCondition = function(idx){
	var p = this;
	deleteOneRow("conditionTbl_" + p.id, idx);
	var objTable = document.getElementById("conditionTbl_" + p.id);
	if(objTable){
		if(objTable.rows.length == 0){//如果显示条件的表格没有行时，则进行隐藏
			objTable.parentNode.parentNode.style.display = "none";
		}
	}
	p.connJSONArray.splice(idx, 1);
	
	var con1 = document.getElementById(p.id + "Condition");
	if(p.connJSONArray.length == 0){
    	con1.options.length = 0;
    	con1.appendChild(createOption('',''));
    	con1.className = "connectConditionSelect";
    	//con1.options[0].selected = true;
    	con1.disabled = true;
    }

    //去掉第一个条件的AND或者OR
    var regExp = /^<FONT color=[\"\']?red[\"\']?>(并且||或者) <\/FONT>/;
    if(p.connJSONArray[0] && regExp.test(objTable.rows[0].cells[0].innerHTML)){
		p.connJSONArray[0].condition = "";
		var newContent = p.connJSONArray[0].paramText.replace(/^<FONT color=[\"\']?red[\"\']?>(并且||或者) <\/FONT>/i, "");
		p.connJSONArray[0].paramText = newContent;
		objTable.rows[0].cells[0].innerHTML = newContent;
    }
	objTable = null;
}

/** 检查连接条件是否合法
 *  验证内容：	1.当选择了一个以上的数据表时，必须有表连接条件；
 *  			2.当选择了两个以上的数据表时，必须数据表数量减一个连接条件；
 *				3.所有选择的数据表必须出现在连接条件中
 */
connectCondition.prototype.validateConnCondition = function(selectedTblId){
	var p = this;
	//获得“已选数据表”对象
	var selectedTblObj = document.getElementById(selectedTblId);
	//获得选择的个数
	var tables = selectedTblObj.options.length;
	
	if(tables > 1){//如果选择了一个以上的数据表时，必须有表连接条件
		if(p.connJSONArray.length >= (tables - 1)){
			var tblIdArray = p.getTableId();
			var isInclude = true;//是否用到了选择的表
			for(var i = 0; i < tables; i++){//循环“已选数据表”下拉框
				var tblId = selectedTblObj.options[i].value;
				if(existInArray(tblIdArray, tblId) == -1){//在“数据表ID数组”中查找
					isInclude = false;
					break;
				}
			}
			if(!isInclude){
				alert("条件未覆盖到选择的数据表！");
				return false;
			}
		}else{
			alert("未选择足够的连接条件！");
			return false;
		}
	}
	
	return true;
}

/** 
 *  获得连接条件中表的id
 *  @param regex  : 表连接参数字符串的分隔字符，如“~”
 *  @return tblIdArray: 所有涉及到的表的ID
 */
connectCondition.prototype.getTableId = function(){
	var p = this;
	var tblIdArray = new Array();
	if(p.connJSONArray){
		for(var i = 0; i < p.connJSONArray.length; i++){
			var connObj = p.connJSONArray[i];
			addToArray(tblIdArray, connObj.tableOneId, true);
			addToArray(tblIdArray, connObj.tableTwoId, true);
		}
	}
	
	return tblIdArray;
}

/**
 * 获得表连接条件字符串，返回元素为：AND (TABLE_NAME_LEFT.COLUMN_NAME_LEFT=TABLE_NAME_RIGHT.COLUMN_NAME_RIGHT) 
 * @return String
 */
connectCondition.prototype.getconnConditionStr = function(){
	var p = this;
	var paramStr = "";
	if(p.connJSONArray && p.connJSONArray.length > 0){
		var connArray = p.connJSONArray;
		for(var i = 0; i < connArray.length; i++){
			var jsonObj = connArray[i];
			paramStr += jsonObj.condition +" "
					 +  jsonObj.preParen
					 +  jsonObj.tableOneName +"."
					 +  jsonObj.columnOneName
					 +  jsonObj.relation
					 +  jsonObj.tableTwoName +"."
					 +  jsonObj.columnTwoName
					 +  jsonObj.postParen +" ";
		}
	}
	return paramStr;
}

/**
 * 判断是否已经存在同样的连接条件
 * @param 用来比较的JSON对象
 */
connectCondition.prototype.exist = function(obj){
	if(!obj){
		return false;
	}
	var p = this;
	for(var i = 0; i < p.connJSONArray.length; i++ ){
		if((p.connJSONArray[i].condition == obj.condition) 
		    && (p.connJSONArray[i].preParen == obj.preParen)
		    && (p.connJSONArray[i].tableOneName == obj.tableOneName) 
		    && (p.connJSONArray[i].columnOneName == obj.columnOneName) 
		    && (p.connJSONArray[i].relation == obj.relation) 
		    && (p.connJSONArray[i].tableTwoName == obj.tableTwoName) 
		    && (p.connJSONArray[i].columnTwoName == obj.columnTwoName) 
		    && (p.connJSONArray[i].postParen == obj.postParen) ){
			return true;
		}
	}
	
	return false;
}

/**
 * 清除所有连接条件
 */
connectCondition.prototype.Clear = function(){
	var p = this;
	var condition = document.getElementById(p.id + "Condition");
	condition.innerHTML = "";
	condition.appendChild(createOption("", ""));
	condition = null;
	var rows = document.getElementById("conditionTbl_" + p.id).rows;
	for (var i = rows.length - 1; i >= 0; i--){
		document.getElementById("conditionTbl_" + p.id).deleteRow();
	}
	document.getElementById(p.id + "hideRow").style.display = "none";
	
	p.connJSONArray = new Array;
}
