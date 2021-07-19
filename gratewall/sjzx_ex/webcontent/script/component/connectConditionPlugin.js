var connectCondition = function(config){
//	alert("connectConditionPlugin.js");
	if (!config.id){
        alert("û�д��������ID������json���ò���id���ݹ�����");
        return;
    }
    
	if (!config.parentContainer){
		alert("û�ж��常������ID������json����parentContainer�������ݹ�����");
		return;
	}
	
	if (!config.getColumnSrcPrefix){
		alert("û�д����ȡ�ֶ��б��URLǰ׺������json���ò���getColumnSrcPrefix���ݹ�����");
		return;
	}
	
	this.id = config.id;
	this.src = config.getColumnSrcPrefix;
	//this.connArray = new Array();
	this.connJSONArray = new Array();
	
	if (document.getElementById(this.id + "Condition")){
		alert("���ơ�" + this.id + "���Ѿ����ڣ������");
		return;
	}
	
	this.tableConnectTableHtml =   '  <table width="95%" border="0" align="center" id="' + this.id + 'Container" style="display:none" class="frame-body" cellpadding="0" cellspacing="0">' + 
									'     <tr class="title-row">' + 
									'       <td colspan="9"><table cellspacing="0" cellpadding="0" width="100%"><tr><td class="leftTitle"></td><td class="secTitle">���ݱ��ѯ����</td><td class="rightTitle"></td></tr></table></td>' + 
									'     </tr>' + 
									'     <tr><td width="100%"><table border="0" cellspacing="0" id="' + this.id + '_topTopTable" cellpadding="0" width="100%">' +
									'     <tr class="grid-headrow" align="center">' + 
									'       <td width="7%"  style="border-right:1px solid #CCCCCC;">����</td>' + 
									'       <td width="6%"  style="border-right:1px solid #CCCCCC;">����</td>' + 
									'       <td width="14%" style="border-right:1px solid #CCCCCC;">���ݱ�</td>' + 
									'       <td width="18%" style="border-right:1px solid #CCCCCC;">���ֶ�</td>' + 
									'       <td style="border-right:1px solid #CCCCCC;">���ӹ�ϵ</td>' + 
									'       <td width="14%" style="border-right:1px solid #CCCCCC;">���ݱ�</td>' + 
									'       <td width="18%" style="border-right:1px solid #CCCCCC;">���ֶ�</td>' + 
									'       <td width="6%" style="border-right:1px solid #CCCCCC;">����</td>' + 
									'       <td width="7%">����</td>' + 
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
									'       <td><select name="' + this.id + 'Relation" id="' + this.id + 'Relation" disabled class="connectConditionSelect"><option value="=" selected="selected">����</option></select></td>' + 
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
									'       <td><table cellspacing="0" cellpadding="0" class="button_table"><tr><td class="btn_left"></td><td><input type="button" id="'+ this.id + 'AddButton" name="'+ this.id + 'AddButton" value="���" class="menu" /></td><td class="btn_right"></td></tr></table></td>' + 
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
    
	// ���¼�
	this.addEvent();
	// ��ʼ��(û�����ݣ���ʱ����ʼ��)
	// this.init();
}

// ����¼�
connectCondition.prototype.addEvent = function(){
	var p = this;
	// ѡ���1ʱ
	var tableOne = document.getElementById(this.id + "TableOne");
	if (tableOne){
		tableOne.onchange = function(){
			p.removeOptionFromSelect(p.id + "TableTwo", this.options[this.selectedIndex].value);
			p.getColumns(this.options[this.selectedIndex].value, p.id + "ColumnOne");
		};
	}
	tableOne = null;
	
	// ѡ���2ʱ
	var tableTwo = document.getElementById(p.id + "TableTwo");
    if (tableTwo){
        tableTwo.onchange = function(){
        	p.removeOptionFromSelect(p.id + "TableOne", this.options[this.selectedIndex].value)
        	p.getColumns(this.options[this.selectedIndex].value, p.id + "ColumnTwo");
        };
    }
    tableTwo = null;
    
    // ѡ���ֶ�1ʱ
    var ColumnOne = document.getElementById(p.id + "ColumnOne");
    if (ColumnOne){
    	ColumnOne.onchange = function(){
    		p.setDefaultColumn(p.id + "ColumnTwo", this.options[this.selectedIndex].text);
    	};
    }
    ColumnOne = null;
    
    // ����¼�
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
 * ��ָ����������ɾ��ָ��ѡ��
 * selectId: ������ID
 * optionValue: ��ɾ��ѡ���value
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

// Ĭ��ѡ��columnTwo�е�ѡ��
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

// ��ʼ��
connectCondition.prototype.init = function(){	
	var tableGhost = document.getElementById(this.id + "TableGhost");	
	var options = tableGhost.options;
	if (options.length >= 2){
		// ��ʾ�����������
		document.getElementById(this.id + "Container").style.display = "block";
		this.removeOptionFromSelect(this.id + "TableOne" , options[1].value);
		this.removeOptionFromSelect(this.id + "TableTwo" , options[0].value);
		document.getElementById(this.id + "TableOne").onchange();
        document.getElementById(this.id + "TableTwo").onchange();
	}else{
		// ���������������
		document.getElementById(this.id + "Container").style.display = "none";
		document.getElementById(this.id + "TableOne").innerHTML = "";
        document.getElementById(this.id + "TableTwo").innerHTML = "";
        document.getElementById(this.id + "ColumnOne").innerHTML = "";
        document.getElementById(this.id + "ColumnTwo").innerHTML = "";
//        document.getElementById(this.id + "AddButton").disabled = "true";
	}
}

// ����ѡ�����ݱ�
connectCondition.prototype.setDataFromSelect = function(selectId){
	var tableGhost = document.getElementById(this.id + "TableGhost");
	tableGhost.innerHTML = "";
	var options = document.getElementById(selectId).options;
	for (var i = 0; i < options.length; i++){
		tableGhost.appendChild(options[i].cloneNode(true));
	}
	
	// ִ����Ϻ��ٴ�ִ�г�ʼ��
	this.init();
}

// ��ȡ���µ��ֶ��б�
connectCondition.prototype.getColumns = function(tableNo, columnSelectId){
	var p = this;
	var columnSelect = document.getElementById(columnSelectId);
	columnSelect.innerHTML = "";
	var tempOption = document.createElement("option");
	columnSelect.appendChild(tempOption);
	tempOption.text = "���ݶ�ȡ��...";
	tempOption = null;
	columnSelect = null;
	
	$.get(p.src + tableNo, function(xml){
		fillXmlToSelect(xml, "record", columnSelectId, "column_no", "column_name_cn", {type: "edit_type", codeValue: "demo", colName:"column_name"});
		document.getElementById(p.id + "ColumnOne").onchange();
	});
}

/** 
 * ���һ������������
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
		alert("�޿�ѡ�ֶΣ�������ӣ�");
		return;
	}
	//��������������
	var content = "<font color=red>"+con1.options[con1.selectedIndex].text + " </font>" 
			    + "<font color=red>" + leftKH.options[leftKH.selectedIndex].text + " </font>" 
			    + leftTbl.options[leftTbl.selectedIndex].text + "<font color=red> �� </font>"
			    + leftCol.options[leftCol.selectedIndex].text + " "
			    + "<font color=red>" + con2.options[con2.selectedIndex].text + " </font>"
				+ rightTbl.options[rightTbl.selectedIndex].text + "<font color=red> �� </font>"
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
		addToArray(p.connJSONArray, connJSONObj);//��ӵ�"����"
	}
	var rowIndex = 0;
	var objTable = document.getElementById("conditionTbl_" + p.id);
	var newTr = objTable.insertRow();
	newTr.height = 30;

	//�����
	var newTd0 = newTr.insertCell();
	newTd0.style.borderBottom = "1px solid #CCCCCC";
	var newTd1 = newTr.insertCell();
	newTd1.style.borderBottom = "1px solid #CCCCCC";

	//���������ݺ�����
	newTd0.innerHTML = content; 
	newTd0.width = "90%";
	newTd0.align = "left";
	newTd1.innerHTML= "<table cellspacing='0' cellpadding='0' class='button_table'><tr><td class='btn_left'></td><td><input type='button' id='deleteConnCondition_" + p.id + "' value=' ɾ�� ' class='menu' /></td><td class='btn_right'></td></tr></table>";
	newTd1.width = "10%";
	newTd1.align = "center";
	
	document.getElementById(p.id + "hideRow").style.display = "block";
	
	var delButton = document.getElementsByName("deleteConnCondition_"+p.id);
    var obj = delButton[delButton.length - 1];
    if (obj){
    	obj.onclick = function(){
    		if(confirm("ȷ��ɾ����������")){
				p.deleteCondition(this.parentNode.parentNode.rowIndex);
    		}
    	};
    }
    
    if(p.connJSONArray.length != 0){
    	con1.options.length = 0;
    	con1.options.add(createOption('AND','����'));
    	con1.options.add(createOption('OR','����'));
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
		if(objTable.rows.length == 0){//�����ʾ�����ı��û����ʱ�����������
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

    //ȥ����һ��������AND����OR
    var regExp = /^<FONT color=[\"\']?red[\"\']?>(����||����) <\/FONT>/;
    if(p.connJSONArray[0] && regExp.test(objTable.rows[0].cells[0].innerHTML)){
		p.connJSONArray[0].condition = "";
		var newContent = p.connJSONArray[0].paramText.replace(/^<FONT color=[\"\']?red[\"\']?>(����||����) <\/FONT>/i, "");
		p.connJSONArray[0].paramText = newContent;
		objTable.rows[0].cells[0].innerHTML = newContent;
    }
	objTable = null;
}

/** ������������Ƿ�Ϸ�
 *  ��֤���ݣ�	1.��ѡ����һ�����ϵ����ݱ�ʱ�������б�����������
 *  			2.��ѡ�����������ϵ����ݱ�ʱ���������ݱ�������һ������������
 *				3.����ѡ������ݱ�������������������
 */
connectCondition.prototype.validateConnCondition = function(selectedTblId){
	var p = this;
	//��á���ѡ���ݱ�����
	var selectedTblObj = document.getElementById(selectedTblId);
	//���ѡ��ĸ���
	var tables = selectedTblObj.options.length;
	
	if(tables > 1){//���ѡ����һ�����ϵ����ݱ�ʱ�������б���������
		if(p.connJSONArray.length >= (tables - 1)){
			var tblIdArray = p.getTableId();
			var isInclude = true;//�Ƿ��õ���ѡ��ı�
			for(var i = 0; i < tables; i++){//ѭ������ѡ���ݱ�������
				var tblId = selectedTblObj.options[i].value;
				if(existInArray(tblIdArray, tblId) == -1){//�ڡ����ݱ�ID���顱�в���
					isInclude = false;
					break;
				}
			}
			if(!isInclude){
				alert("����δ���ǵ�ѡ������ݱ�");
				return false;
			}
		}else{
			alert("δѡ���㹻������������");
			return false;
		}
	}
	
	return true;
}

/** 
 *  ������������б��id
 *  @param regex  : �����Ӳ����ַ����ķָ��ַ����硰~��
 *  @return tblIdArray: �����漰���ı��ID
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
 * ��ñ����������ַ���������Ԫ��Ϊ��AND (TABLE_NAME_LEFT.COLUMN_NAME_LEFT=TABLE_NAME_RIGHT.COLUMN_NAME_RIGHT) 
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
 * �ж��Ƿ��Ѿ�����ͬ������������
 * @param �����Ƚϵ�JSON����
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
 * ���������������
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
