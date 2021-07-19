/**
 * 查询条件组件
 */
 
 /**
  * 
  
 			   compCond_String     : [{text:'等于', value:'=', prefix:'\'', postfix:'\''},
	                                  {text:'不等于', value:'<>', prefix:'\'', postfix:'\''},
	                                  {text:'开始于', value:'like', prefix:'\'', postfix:'%\''},
	                                  {text:'结束于', value:'like', prefix:'\'%', postfix:'\''},
	                                  {text:'包含', value:'like', prefix:'\'%', postfix:'%\''},
	                                  {text:'IN', value:'IN', prefix:'(\'', postfix:'\')'},
	                                  {text:'为空', value:'is null', prefix:'(\'', postfix:'\')'},
	                                  {text:'不为空', value:'is not null', prefix:'(\'', postfix:'\')'}
	                                  ],
               compCond_Number     : [{text:'等于', value:'='},
                                      {text:'不等于', value:'='},
                                      {text:'大于', value:'='},
                                      {text:'大于等于', value:'='},
                                      {text:'小于', value:'='},
                                      {text:'小于等于', value:'='}],
               compCond_Date       : [{text:'等于', value:'='},
                                      {text:'不等于', value:'='},
                                      {text:'开始于', value:'='},
                                      {text:'结束于', value:'='}],
               compCond_Code       : [{text:'等于', value:'='},
                                      {text:'不等于', value:'='},
                                      {text:'大于', value:'='},
                                      {text:'大于等于', value:'='},
                                      {text:'小于', value:'='},
                                      {text:'小于等于', value:'='}]
   
   * 
   * */                                      
var queryCondition = function(config){	
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
    this.getCodeSrc = config.getCodeSrcPrefix ? config.getCodeSrcPrefix : "txn60210012.ajax?select-key:jc_dm_id="
    
    if (document.getElementById(this.id + "Condition")){
        alert("名称【" + this.id + "】已经存在，请更换");
        return;
    }
    
    this.queryConditionHtml =   '  <table width="95%" border="0" align="center" id="' + this.id + 'Container" cellpadding="0" cellspacing="0" class="frame-body">' +
                                    '     <tr class="title-row">' + 
                                    '       <td><table cellspacing="0" cellpadding="0" width="100%"><tr><td class="leftTitle"></td><td class="secTitle">数据表查询条件</td><td class="rightTitle"></td></tr></table></td>' + 
                                    '     </tr>' + 
                                    '     <tr><td width="100%"><table id="' + this.id + '_topTopTable" style="border:2px solid #006699;border-collapse:collapse;" cellspacing="0" cellpadding="0" width="100%">' +
                                    '     <tr class="grid-headrow" align="center">' + 
                                    '       <td width="7%">条件</td>' + 
                                    '       <td width="6%">括弧</td>' + 
                                    '       <td width="20%">数据表</td>' + 
                                    '       <td width="20%">表字段</td>' + 
                                    '       <td width="10%">查询条件</td>' + 
                                    '       <td >参数值</td>' + 
                                    '       <td width="6%">括弧</td>' + 
                                    '       <td width="7%">操作</td>' + 
                                    '     </tr>' + 
                                    '     <tr class="framerow selectRow">' + 
                                    '       <td><select name="' + this.id + 'Condition" id="' + this.id + 'Condition" class="queryConditionSelect" disabled="true">' + 
                                    '         <option selected="selected"></option>' + 
                                    '         <option value="AND">并且</option>' + 
                                    '         <option value="OR">或者</option>' + 
                                    '       </select></td>' + 
                                    '       <td><select name="' + this.id + 'PreParen" id="' + this.id + 'PreParen" class="queryConditionSelect">' + 
                                    '         <option selected="selected"></option>' + 
                                    '         <option value="(">(</option>' + 
                                    '         <option value="((">((</option>' + 
                                    '         <option value="(((">(((</option>' + 
                                    '         <option value="((((">((((</option>' + 
                                    '         <option value="(((((">(((((</option>' + 
                                    '       </select></td>' + 
                                    '       <td><select name="' + this.id + 'TableGhost" id="' + this.id + 'TableGhost" class="connectConditionSelect" style="display:none"></select>' + 
                                    '<select name="' + this.id + 'TableOne" id="' + this.id + 'TableOne" class="queryConditionSelect"></select></td>' + 
                                    '       <td><select name="' + this.id + 'ColumnOne" id="' + this.id + 'ColumnOne" class="queryConditionSelect"></select></td>' + 
                                    '       <td><select name="' + this.id + 'RelationString" id="' + this.id + 'RelationString" class="queryConditionSelect" style="display:none"><OPTION value="=" selected postfix="\'" prefix="\'">等于</OPTION><OPTION value="<>" postfix="\'" prefix="\'">不等于</OPTION><OPTION value="like" postfix="%\'" prefix="\'">开始于</OPTION><OPTION value="like" postfix="\'" prefix="\'%">结束于</OPTION><OPTION value="like" postfix="%\'" prefix="\'%">包含</OPTION><OPTION value="not like" postfix="%\'" prefix="\'%">不包含</OPTION><OPTION value="IS" >为空</OPTION><OPTION value="IS NOT" >不为空</OPTION></select>' + 
                                    '<select name="' + this.id + 'RelationNumber" id="' + this.id + 'RelationNumber" class="queryConditionSelect" style="display:none"><OPTION value="=" selected>等于</OPTION><OPTION value="<>">不等于</OPTION><OPTION value=">">大于</OPTION><OPTION value=">=">大于等于</OPTION><OPTION value="<">小于</OPTION><OPTION value="<=">小于等于</OPTION></select>' +
                                    '<select name="' + this.id + 'RelationCode" id="' + this.id + 'RelationCode" class="queryConditionSelect" style="display:none"><OPTION value="=" selected postfix="\'" prefix="\'">等于</OPTION><OPTION value="<>" postfix="\'" prefix="\'">不等于</OPTION><OPTION value=">" postfix="\'" prefix="\'">大于</OPTION><OPTION value=">=" postfix="\'" prefix="\'">大于等于</OPTION><OPTION value="<" postfix="\'" prefix="\'">小于</OPTION><OPTION value="<=" postfix="\'" prefix="\'">小于等于</OPTION><OPTION value="IS" >为空</OPTION><OPTION value="IS NOT" >不为空</OPTION></select>' + 
                                    '<select name="' + this.id + 'RelationDate" id="' + this.id + 'RelationDate" class="queryConditionSelect" style="display:none"><OPTION value="=" selected postfix="\'" prefix="\'">等于</OPTION><OPTION value="<>" postfix="\'" prefix="\'">不等于</OPTION><OPTION value=">" postfix="\'" prefix="\'">大于</OPTION><OPTION value=">=" postfix="\'" prefix="\'">大于等于</OPTION><OPTION value="<" postfix="\'" prefix="\'">小于</OPTION><OPTION value="<=" postfix="\'" prefix="\'">小于等于</OPTION><OPTION value="IS" >为空</OPTION><OPTION value="IS NOT" >不为空</OPTION></select>' +  
                                    '</td>' + 
                                    '       <td><select name="' + this.id + 'ParamValueSelect" id="' + this.id + 'ParamValueSelect" class="queryConditionSelect" style="display:none"></select>' + 
                                    '<input type="text" id="' + this.id + 'ParamValueInput" style="display:none" class="queryConditionInput" />' + 
                                    '<input type="text" id="' + this.id + 'ParamValueDateSelect" style="display:none" class="queryConditionInput" onclick="calendar(this, 1)"/></td>' + 
                                    '       <td><select name="' + this.id + 'PostParen" id="' + this.id + 'PostParen" class="queryConditionSelect">' + 
                                    '          <option selected="selected"></option>' + 
                                    '          <option value=")">)</option>' + 
                                    '          <option value="))">))</option>' + 
                                    '          <option value=")))">)))</option>' + 
                                    '          <option value="))))">))))</option>' + 
                                    '          <option value=")))))">)))))</option>' + 
                                    '       </select></td>' + 
                                    '       <td><table cellspacing="0" cellpadding="0" class="button_table"><tr><td class="btn_left"></td><td><input type="button" id="'+ this.id + 'AddButton" name="'+ this.id + 'AddButton" value="添加" class="menu" /></td><td class="btn_right"></td></tr></table></td>' + 
                                    '     </tr>' + 
                                    '		</table></td></tr>' +  
                                    '     <tr id="'+ this.id +'hideRow" style="display:none;padding:0px;" class="framerow">'+
                                    '       <td>'+
                                    '           <table id="conditionTbl_'+ this.id +'" border="0" cellpadding="0" cellspacing="0" width="100%" style="border:2px solid #006699;border-top:none;"></table>'+
                                    '       </td>'+
                                    '     </tr>'+
                                    '   </table>';
    document.getElementById(config.parentContainer).innerHTML = this.queryConditionHtml;
    
    document.getElementById(this.id + "_topTopTable").onresize = function(){
    	if (this.offsetWidth < 600 && this.offsetWidth != 0){
    		this.style.width = 600;
    	}
    }
    
    setShowTitleSelectList(this.id + "TableOne", this.id + "ColumnOne", this.id + "ParamValueSelect", false);
    
    // 如果传入了条件，修改条件选择框（组件展现时一次性操作）
    // 字符串
    if (config.compCond_String){
    	var length = config.compCond_String.length;
    	if (length > 0){
    		var RelationString = document.getElementById(this.id + 'RelationString');
    		RelationString.innerHTML = "";
    		for (var i = 0; i < length; i++){
    			var tempOption = document.createElement("option");
                RelationString.appendChild(tempOption);
    			tempOption.text = config.compCond_String[i].text;
    			tempOption.value = config.compCond_String[i].value;
    			if(config.compCond_String[i].prefix){
    				tempOption.prefix = config.compCond_String[i].prefix;
    			}else{
    				tempOption.prefix = "";
    			}
    			if(config.compCond_String[i].postfix){
    				tempOption.postfix = config.compCond_String[i].postfix;
    			}else{
    				tempOption.postfix = "";
    			}
    			tempOption = null;
    		}
    		RelationString = null;
    	}
    }
    
    // 代码
    if (config.compCond_Code){
        var length = config.compCond_Code.length;
        if (length > 0){
            var RelationCode = document.getElementById(this.id + 'RelationCode');
            RelationCode.innerHTML = "";
            for (var i = 0; i < length; i++){
                var tempOption = document.createElement("option");
                RelationCode.appendChild(tempOption);
                tempOption.text = config.compCond_Code[i].text;
                tempOption.value = config.compCond_Code[i].value;
                if(config.compCond_Code[i].prefix){
                    tempOption.prefix = config.compCond_Code[i].prefix;
                }else{
                    tempOption.prefix = "";
                }
                if(config.compCond_Code[i].postfix){
                    tempOption.postfix = config.compCond_Code[i].postfix;
                }else{
                    tempOption.postfix = "";
                }
                tempOption = null;
            }
            RelationCode = null;
        }
    }
    // 数字
    if (config.compCond_Number){
        var length = config.compCond_Number.length;
        if (length > 0){
            var RelationNumber = document.getElementById(this.id + 'RelationNumber');
            RelationNumber.innerHTML = "";
            for (var i = 0; i < length; i++){
                var tempOption = document.createElement("option");
                RelationNumber.appendChild(tempOption);
                tempOption.text = config.compCond_Number[i].text;
                tempOption.value = config.compCond_Number[i].value;
                if(config.compCond_Number[i].prefix){
                    tempOption.prefix = config.compCond_Number[i].prefix;
                }else{
                    tempOption.prefix = "";
                }
                if(config.compCond_Number[i].postfix){
                    tempOption.postfix = config.compCond_Number[i].postfix;
                }else{
                    tempOption.postfix = "";
                }
                tempOption = null;
            }
            RelationNumber = null;
        }
    }
    // 日期
    if (config.compCond_Date){
        var length = config.compCond_Date.length;
        if (length > 0){
            var RelationDate = document.getElementById(this.id + 'RelationDate');
            RelationDate.innerHTML = "";
            for (var i = 0; i < length; i++){
                var tempOption = document.createElement("option");
                RelationDate.appendChild(tempOption);
                tempOption.text = config.compCond_Date[i].text;
                tempOption.value = config.compCond_Date[i].value;
                if(config.compCond_Date[i].prefix){
                    tempOption.prefix = config.compCond_Date[i].prefix;
                }else{
                    tempOption.prefix = "";
                }
                if(config.compCond_Date[i].postfix){
                    tempOption.postfix = config.compCond_Date[i].postfix;
                }else{
                    tempOption.postfix = "";
                }
                tempOption = null;
            }
            RelationDate = null;
        }
    }
    // 绑定事件
    this.addEvent();
    // 初始化(没有数据，暂时不初始化)
    // this.init();
    
	this.connArray = new Array();
	this.connJSONArray = new Array();
}

// 添加事件
queryCondition.prototype.addEvent = function(){
    var p = this;
    // 选择表时
    var tableOne = document.getElementById(this.id + "TableOne");
    if (tableOne){
        tableOne.onchange = function(){
            p.getColumns(this.options[this.selectedIndex].value, p.id + "ColumnOne");
        };
    }
    tableOne = null;
    
    // 选择字段时
    var ColumnOne = document.getElementById(p.id + "ColumnOne");
    
    if (ColumnOne){
        ColumnOne.onchange = function(){
            var columnType=1;
            //alert(this.options.length+'---'+this.selectedIndex);
            if(this.options.length>0){
              columnType = this.options[this.selectedIndex].type;
              var codeValue = this.options[this.selectedIndex].codeValue;
            }
            // alert(columnType + ", " + codeValue);
            var paramValueInput = document.getElementById(p.id + "ParamValueInput");
            var paramValueSelect = "";
            if(document.getElementById(p.id + "ParamValueSelect")){
              paramValueSelect=document.getElementById(p.id + "ParamValueSelect");
            }
            //var paramValueSelect = document.getElementById(p.id + "ParamValueSelect");
            var paramValueDateSelect = document.getElementById(p.id + "ParamValueDateSelect");
            
            paramValueInput.value = "";
            paramValueSelect.innerHTML = "";
            paramValueDateSelect.value = "";
            
            var RelationString = document.getElementById(p.id + "RelationString");
            var RelationNumber = document.getElementById(p.id + "RelationNumber");
            var RelationCode = document.getElementById(p.id + "RelationCode");
            var RelationDate = document.getElementById(p.id + "RelationDate");
            /**
             * columnType:
             * 1. 字符串
             * 2. 日期
             * 3. 数字
             * codeValue:
             * 当columnType为1且codeValue有值时，为码表
             */
            if (columnType == 1){ // 字符串
            	if (codeValue){//码表
            		paramValueInput.style.display = "none";
            		paramValueSelect.style.display = "block";
            		paramValueDateSelect.style.display = "none";
            		RelationString.style.display = "none";
            		RelationCode.style.display = "block";
            		RelationDate.style.display = "none";
            		RelationNumber.style.display = "none";
            		
            		paramValueSelect.innerHTML = "";
            		var option = document.createElement("option");
            		paramValueSelect.appendChild(option);
            		option.text = "数据读取中...";
            		option = null;
            		$.get(p.getCodeSrc + codeValue, function(xml){
            			fillXmlToSelect(xml, "record", p.id + "ParamValueSelect", "jcsjfx_dm", "jcsjfx_mc");
            		});
            	}else{// 普通字符串
            		paramValueInput.style.display = "block";
                    paramValueSelect.style.display = "none";
                    paramValueDateSelect.style.display = "none";
                    RelationString.style.display = "block";
                    RelationCode.style.display = "none";
                    RelationDate.style.display = "none";
                    RelationNumber.style.display = "none";
            	}
            }else if (columnType == 2){ // 日期
            	paramValueInput.style.display = "none";
                paramValueSelect.style.display = "none";
                paramValueDateSelect.style.display = "block";
                RelationString.style.display = "none";
                RelationCode.style.display = "none";
                RelationDate.style.display = "block";
                RelationNumber.style.display = "none";
            }else if (columnType == 3){ // 数字
                paramValueInput.style.display = "block";
                paramValueSelect.style.display = "none";
                paramValueDateSelect.style.display = "none";
                RelationString.style.display = "none";
                RelationCode.style.display = "none";
                RelationDate.style.display = "none";
                RelationNumber.style.display = "block";
            }else{// 不让输入
            	paramValueInput.style.display = "none";
                paramValueSelect.style.display = "none";
                paramValueDateSelect.style.display = "none";
                RelationString.style.display = "none";
                RelationCode.style.display = "none";
                RelationDate.style.display = "none";
                RelationNumber.style.display = "none";
            }
            paramValueInput = null;
            paramValueSelect = null;
            paramValueDateSelect = null;
            RelationString = null;
            RelationCode = null;
            RelationNumber = null;
            RelationDate = null;
        };
    }
    ColumnOne = null;
    
    // 添加事件
    var addButton = document.getElementById(this.id + "AddButton");
    if (addButton){
        addButton.onclick = function(){
        	p.addQueryCondition();
        }
    }
};

// 初始化
queryCondition.prototype.init = function(){
	var TableGhost = document.getElementById(this.id + "TableGhost");
	var options = TableGhost.options;
    if (options.length > 0){
    	document.getElementById(this.id + "Container").style.display = "block";
    	var TableOne = document.getElementById(this.id + "TableOne");
    	TableOne.innerHTML = "";
    	for (var i = 0; i < options.length; i++){
    		TableOne.appendChild(options[i].cloneNode(true));
    	}
    	document.getElementById(this.id + "TableOne").onchange();
    	TableOne = null;
    }else{
    	document.getElementById(this.id + "Container").style.display = "none";
    	document.getElementById(this.id + "TableOne").innerHTML = "";
    	document.getElementById(this.id + "ColumnOne").innerHTML = "";
    	document.getElementById(this.id + "ParamValueSelect").innerHTML = "";
    	document.getElementById(this.id + "ParamValueInput").value = "";
    	document.getElementById(this.id + "ParamValueDateSelect").value = "";
    }
    TableGhost = null;
}

// 设置选择数据表
queryCondition.prototype.setDataFromSelect = function(selectId){
    var TableGhost = document.getElementById(this.id + "TableGhost");
    TableGhost.innerHTML = "";
    var options = document.getElementById(selectId).options;

    for (var i = 0; i < options.length; i++){
        TableGhost.appendChild(options[i].cloneNode(true));
    }
    
    // 执行完毕后，再次执行初始化
    this.init();
}

// 设置选择数据表 jufeng 2012-06-28
queryCondition.prototype.setDataFromTemp = function(temp){
	if(temp!=null&&temp.length>0){
	    var TableGhost = document.getElementById(this.id + "TableGhost");
        TableGhost.innerHTML = "";
		for(var j=0;j<temp.length; j++){
			  var aOption = document.createElement('OPTION');
			  aOption.value=temp[j].tblNo;
			  aOption.innerText=temp[j].tblNameCn;
			  aOption.title=temp[j].tblNameCn;
			  aOption.tblName=temp[j].tblName;
			  TableGhost.appendChild(aOption);
		}
	}
    // 执行完毕后，再次执行初始化
    this.init();
}

// 重置选择数据表 jufeng 2012-06-28
queryCondition.prototype.updateDataFromTemp = function(temp,tempOld){

    var TableGhost = document.getElementById(this.id + "TableGhost");
    var addTemp = new Array;
    var delTemp = new Array;
    var temp2 = new Array;
    var tempOld2 = new Array;
    if(temp!=null&&temp.length>0&&tempOld!=null&&tempOld.length>0){
    	for(var j=0; j<temp.length; j++){
    		temp2.push( temp[j].tblNo );
    	}
    	for(var j=0; j<tempOld.length; j++){
    		tempOld2.push( tempOld[j].tblNo );
    	}
    //现在在以前里找不到的 先不做处理
       /*
        var tempOldStr = tempOld2.join('-');
	    for(var j=0;j<temp.length; j++){
	    	if(tempOldStr.indexOf(temp[j].tblNo)<0){
	    		addTemp.push(temp[j].tblNo);
	    	}	
	    }
	    */
	//以前的在现在里找不到的 需要删除相关系统参数
	    var tempStr = temp2.join('-');
	    for(var j=0;j<tempOld.length; j++){
	    	if(tempStr.indexOf(tempOld[j].tblNo)<0){
	    		delTemp.push(tempOld[j].tblNo);
	    	}	
	    }
	//根据现在的seletedTable填充查询表
	    TableGhost.innerHTML = "";
		for(var j=0;j<temp.length; j++){
			  var aOption = document.createElement('OPTION');
			  aOption.value=temp[j].tblNo;
			  aOption.innerText=temp[j].tblNameCn;
			  aOption.title=temp[j].tblNameCn;
			  aOption.tblName=temp[j].tblName;
			  TableGhost.appendChild(aOption);
		}
	
	if(delTemp.length>0){
		this.delConnConditionOfRemovedTables(delTemp);
	}
	}
	this.init();
}

// 获取表下的字段列表
queryCondition.prototype.getColumns = function(tableNo, columnSelectId){
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

// 获得查询关系
queryCondition.prototype.getRelationSelect = function(){
	var RelationString		= document.getElementById(this.id + "RelationString");
	var RelationNumber		= document.getElementById(this.id + "RelationNumber");
	var RelationCode		= document.getElementById(this.id + "RelationCode");
	var RelationDate		= document.getElementById(this.id + "RelationDate");
	var relationDom = null;
	if (RelationString.style.display == 'block'){
		relationDom = RelationString;
	}else if (RelationNumber.style.display == 'block'){
		relationDom = RelationNumber;
	}else if(RelationCode.style.display == 'block'){
		relationDom = RelationCode;
	}else if(RelationDate.style.display == 'block'){
		relationDom = RelationDate;
	}
	
	RelationString = null;
	RelationNumber = null;
	RelationCode = null;
	RelationDate = null;
	return relationDom;
}

// 获得参数值
queryCondition.prototype.getParam = function(){
	var ParamValueSelect		= document.getElementById(this.id + "ParamValueSelect");
	var ParamValueInput			= document.getElementById(this.id + "ParamValueInput");
	var ParamValueDateSelect	= document.getElementById(this.id + "ParamValueDateSelect");
	
	var relationSelect = this.getRelationSelect();
	if (!relationSelect){
		return null;
	}
	
	var selectedOption = relationSelect.options[relationSelect.selectedIndex];
	
	var paramString		= selectedOption.prefix ? selectedOption.prefix : "";
	var displayString	= "";
	var userInputString = selectedOption.prefix ? selectedOption.prefix : "";
	
	if (ParamValueSelect.selectedIndex >= 0){
		paramString		+= ParamValueSelect.options[ParamValueSelect.selectedIndex].value;
		displayString	+= ParamValueSelect.options[ParamValueSelect.selectedIndex].text;
	}else{
		paramString		+= ParamValueInput.value + ParamValueDateSelect.value;
		displayString	+= ParamValueInput.value + ParamValueDateSelect.value; 
	}
	
	userInputString += "?";
	
	paramString			+= selectedOption.postfix ? selectedOption.postfix : "";
	displayString		+= "";
	userInputString		+= selectedOption.postfix ? selectedOption.postfix : "";
	
	relationSelect = null;
	ParamValueSelect = null;
	ParamValueInput = null;
	ParamValueDateSelect = null;
	var regExp = /[~\\\?'"\^\*]/;
	if(regExp.test(displayString)){
		alert("【参数值】中含有不合法的字符【" + displayString.match(regExp) + "】，请重新输入！");
		return false;
	}

	var jsonObject = '({value:"' + paramString + '", displayValue:"' + displayString + 
		'", userInputString :"' + userInputString + '"})';
	return eval(jsonObject);
}

// 添加一条连接条件
queryCondition.prototype.addQueryCondition = function(){ 
	document.getElementById("qct_topTopTable").style.borderBottomWidth = '0px';
	var p = this;
    var conditionSelect		= document.getElementById(this.id + "Condition");	// 连接条件下拉框对象 AND
    var preParenSelect		= document.getElementById(this.id + "PreParen");	// 左括号下拉框对象
    var tableOneSelect		= document.getElementById(this.id + "TableOne");	// 数据表下拉框对象
    var columnOneSelect		= document.getElementById(this.id + "ColumnOne");	// 字段下拉框对象
    var relationSelect		= this.getRelationSelect();							// 比较条件下拉框对象
    var postParenSelect		= document.getElementById(this.id + "PostParen");	// 右括号下拉框对象
    // 比较参数可以如下得到, 显示文本paramJson.displayValue, 拼好的字符串paramJson.value
    
    var paramJson = this.getParam();
    if (!paramJson){
    	return;
    }
    if(relationSelect.value=='IS' || relationSelect.value=='IS NOT'){
       paramJson.displayValue="";
       paramJson.value=" NULL";
    }else{
	    if(paramJson.displayValue.trim() == ""){
			alert("请输入【参数值】！");
			return;
		}
	}
	
	// 辨别特殊字符
	/*var expStrArray = new Array("北京","北京市","科技","有限","有限公","有限公司","限公司","公司",
			"东城","西城","崇文","宣武","朝阳","海淀","丰台","石景山","门头沟",
			"房山","通州","顺义","大兴","昌平","平谷","怀柔","密云","延庆","东城区","西城区",
			"崇文区","宣武区","朝阳区","海淀区","丰台区","石景山区","门头沟区","房山区","通州区",
			"顺义区","大兴区","昌平区","平谷区","怀柔区","密云县","延庆县","城区","文区","武区",
			"阳区","淀区","台区","景山区","山区","头沟区","沟区","山区","州区","义区","兴区",
			"平区","谷区","柔区","云县","庆县","河北省","山西省","辽宁省","吉林省","黑龙江省",
			"江苏省","浙江省","安徽省","福建省","江西省","山东省","河南省","湖北省","湖南省",
			"广东省","海南省","四川省","贵州省","云南省","陕西省","甘肃省","青海省","台湾省",
			"内蒙古","广西","西藏","宁夏","新疆","天津市","上海市","重庆市","河北","山西","辽宁",
			"吉林","黑龙江","江苏","浙江","安徽","福建","江西","山东","河南","湖北","湖南","广东",
			"海南","四川","贵州","云南","陕西","甘肃","青海","台湾","天津","上海","重庆","%","中心");
	*/
	var expStrArray = new Array("%");
	var temp;
	for(temp in expStrArray){
		if(paramJson.displayValue.trim() == expStrArray[temp]){
			alert("提示：请输入正确的信息后查询");
			return false;
		}
	}
	
	var content = "<font color=red>"+conditionSelect.options[conditionSelect.selectedIndex].text + " </font>" 
			    + "<font color=red>" + preParenSelect.options[preParenSelect.selectedIndex].text + " </font>" 
			    + tableOneSelect.options[tableOneSelect.selectedIndex].text + " 的 "
			    + columnOneSelect.options[columnOneSelect.selectedIndex].text + " "
			    + "<font color=red>" + relationSelect.options[relationSelect.selectedIndex].text + " </font>"
				+ paramJson.displayValue + " "
				+ "<font color=red>" + postParenSelect.options[postParenSelect.selectedIndex].text + " </font>";
	
	
	//构造表的连接	      
	var connJSONObj = {condition    :conditionSelect.options[conditionSelect.selectedIndex].value,
					   preParen     :preParenSelect.options[preParenSelect.selectedIndex].value,
					   tableOneId   :tableOneSelect.options[tableOneSelect.selectedIndex].value,
					   tableOneName :tableOneSelect.options[tableOneSelect.selectedIndex].tblName,
					   tableOneNameCn :tableOneSelect.options[tableOneSelect.selectedIndex].text,
					   columnOneId  :columnOneSelect.options[columnOneSelect.selectedIndex].value,
					   columnOneName:columnOneSelect.options[columnOneSelect.selectedIndex].colName,
					   columnOneNameCn:columnOneSelect.options[columnOneSelect.selectedIndex].text,
					   relation     :relationSelect.options[relationSelect.selectedIndex].value,
					   paramValue   :paramJson.value,
					   userInputString :paramJson.userInputString,
					   postParen    :postParenSelect.options[postParenSelect.selectedIndex].value,
					   paramText    :content};
	//return;     
	//判断是否为重复条件
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
	var newTd1 = newTr.insertCell();
	
	//计算添加条件的行数，用来设置隔行换色
	var rows = document.getElementById("conditionTbl_qct").rows.length;
	if(rows%2 == 0){
		newTd0.className = "odd1";
		newTd1.className = "odd1";
	}else{
		newTd0.className = "odd2";
		newTd1.className = "odd2";
	}

	//设置列内容和属性
	newTd0.innerHTML = content; 
	newTd0.width = "90%";
	newTd0.align = "left";
	newTd1.innerHTML= "<table cellspacing='0' cellpadding='0' class='button_table'><tr><td class='btn_left'></td><td><input type='button' id='deleteConnCondition_" + p.id + "' value=' 删除 ' class='menu' tblNo='"+connJSONObj.tableOneId+"' /></td><td class='btn_right'></td></tr></table>";
	newTd1.align = "center";
	
	document.getElementById(p.id + "hideRow").style.display = "block";
	
	rowIndex ++;
	
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
    	conditionSelect.options.length = 0;
    	conditionSelect.options.add(createOption('AND','并且'));
    	conditionSelect.options.add(createOption('OR','或者'));
    	conditionSelect.disabled = false;
    }
    
    obj             = null;
    delButton       = null;
    conditionSelect = null;
    preParenSelect  = null;
    tableOneSelec   = null;
    columnOneSelect = null;
    relationSelect  = null;
    postParenSelect = null;
}

queryCondition.prototype.deleteCondition = function(idx){
	var p = this;
	deleteOneRow("conditionTbl_" + p.id, idx);
	var objTable = document.getElementById("conditionTbl_" + p.id);
	if(objTable){
		if(objTable.rows.length == 0){//如果显示条件的表格没有行时，则进行隐藏
			objTable.parentNode.parentNode.style.display = "none";
			document.getElementById("qct_topTopTable").style.borderBottomWidth = '2px';
		}
	}
	this.connJSONArray.splice(idx, 1);
	
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
		var newContent = p.connJSONArray[0].paramText.replace(/^<FONT color=[\"\']?red[\"\']?>\s*(并且||或者) <\/FONT>/i, "");
		p.connJSONArray[0].paramText = newContent;
		objTable.rows[0].cells[0].innerHTML = newContent;
    }
    
	objTable = null;
}

// 删除一条连接条件
queryCondition.prototype.delConnCondition = function(idx){
	var p = this;
	this.connJSONArray.splice(idx, 1);
}


queryCondition.prototype.getQueryConditionStr = function(idx){
	var p = this;
	var paramStr = "";
	
	if(p.connJSONArray && p.connJSONArray.length > 0){
		var connArray = p.connJSONArray;
		
		for(var i = 0; i < connArray.length; i++){
			var jsonObj = connArray[i];
			//alert("jsonObj.condition - "+ jsonObj.condition + " jsonObj.preParen "+ jsonObj.preParen);
			paramStr += jsonObj.condition +" "
					 +  jsonObj.preParen
					 +  jsonObj.tableOneName +"."
					 +  jsonObj.columnOneName + " "
					 +  jsonObj.relation + " "
					 +  jsonObj.paramValue
					 +  jsonObj.postParen +" ";
		}
	}
	return paramStr;
}

queryCondition.prototype.getQueryConditionStrWithoutValue = function(idx){
	var p = this;
	var paramStr = "";
	
	if(p.connJSONArray && p.connJSONArray.length > 0){
		var connArray = p.connJSONArray;
		
		for(var i = 0; i < connArray.length; i++){
			var jsonObj = connArray[i];
			paramStr += jsonObj.condition +" "
					 +  jsonObj.preParen
					 +  jsonObj.tableOneName +"."
					 +  jsonObj.columnOneName + " "
					 +  jsonObj.relation + " "
					 +  jsonObj.userInputString
					 +  jsonObj.postParen +" ";
		}
	}
	return paramStr;
}

/** 
 *  获得连接条件中表的id
 *  @param regex  : 表连接参数字符串的分隔字符，如“~”
 *  @return tblIdArray: 所有涉及到的表的ID
 */
queryCondition.prototype.getTableId = function(){
	var p = this;
	var tblIdArray = new Array();
	if(p.connJSONArray){
		for(var i = 0; i < p.connJSONArray.length; i++){
			var connObj = p.connJSONArray[i];
			addToArray(tblIdArray, connObj.tableOneId, true);
		}
	}
	
	return tblIdArray;
}

/**
 * 判断是否已经存在同样的连接条件
 * @param 用来比较的JSON对象
 */
queryCondition.prototype.exist = function(obj){
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
		    && (p.connJSONArray[i].paramValue.trim() == obj.paramValue.trim()) 
		    && (p.connJSONArray[i].postParen == obj.postParen) ){
			return true;
		}
	}
	
	return false;
}

/**
 * 清除所有连接条件
 */
queryCondition.prototype.Clear = function(){
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

/**
 * 清除所有已去除表的系统参数表
 */
queryCondition.prototype.delConnConditionOfRemovedTables = function(delTemp){
	var delA = new Array;
	var delTempStr = delTemp.join('-');
	var p = this;
	var objTable = document.getElementById("conditionTbl_" + p.id);
	var tbodyNodes = objTable.childNodes;
	
	if(delTemp!=null&&delTemp.length>0){
		if(tbodyNodes!=null&&tbodyNodes.length>0){
			var trNodes = tbodyNodes[0].childNodes
			if(trNodes!=null&&trNodes.length>0){
				for(var j=0; j<trNodes.length; j++){
					var aInput = trNodes[j].childNodes[1].childNodes[0];
					var tblNo = aInput.getAttribute("tblNo");
					if(delTempStr.indexOf(tblNo)>=0){
						delA.push(j);
					}
				}
			}
			
		}
	}
	//删除页面显示信息
	if(delA!=null&&delA.length>0){
		var a = new Array;
		for(var j=0; j<delA.length; j++){
			a.push( tbodyNodes[0].childNodes[delA[j]]);
			//alert( tbodyNodes[0].childNodes[delA[j]] )
		}
		if(a!=null&&a.length>0){
			for(var j=0; j<a.length; j++){
				tbodyNodes[0].removeChild(a[j]);
			}
		}
	}
	//alert( delA.join('-') +" \n "+ delTempStr )
	
	//删除连接条件数组中信息
	this.removeQueryConditionFromconnJSONArray(delA);
	
	/*
	if(delA!=null&&delA.length>0){
		for(var j=0; j<delA.length; j++){
			this.delConnCondition(delA[j]);
		}
	}	
	*/
	//alert(p.connJSONArray.length)
}

//清除连接条件数组中信息 jufeng 2012-06-27
queryCondition.prototype.removeQueryConditionFromconnJSONArray = function(temp){
	var p = this;
	if(temp!=null&&temp.length>0){
		var tempStr = '-'+temp.join('-')+'-';
		if(p.connJSONArray && p.connJSONArray.length > 0){
			var connArray = p.connJSONArray;
			var connArrayTemp = [];
			
			for(var i = 0; i < connArray.length; i++){
				if(tempStr.indexOf('-'+i+'-')<0){
					connArrayTemp.push( connArray[i] );
				}
			}
			p.connJSONArray = [];
			p.connJSONArray = connArrayTemp;
		}
	}
	//清除生成连接条件时可能出现的最前的AND
	if(p.connJSONArray!=null&&p.connJSONArray.length>0){
	   p.connJSONArray[0].condition = "";
	}
}
