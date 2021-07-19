/**
 * Author : wangyx
 * 下拉框相关的一些操作
 */
/**
 * 清空下拉框中所有数据
 * @param selectId：下拉框ID
 */
function clearOptions(selectId){
	var obj = document.getElementById(selectId);
	if(obj && obj.tagName && obj.tagName.toUpperCase() == "SELECT"){
		obj.innerHTML = "";
	}
}

/**
 * 创建下拉框的选项
 * @param optionValue：选项的值
 * @param optionText： 选项的显示值
 * @param addtionAttributes JSON对象，表示附加属性
 * @return Option
 */
function createOption(optionValue,optionText, addtionAttributes){
	var oOption = document.createElement("option");
	oOption.value = optionValue;
	oOption.text = optionText;
	oOption.title = optionText;
	
	if(addtionAttributes){
		for(var attName in addtionAttributes){
		    var paramValue = eval("addtionAttributes." + attName);
			eval("oOption." + attName + " = '" + paramValue + "'");
		}
	}
	return oOption;
}

/**
 * 添加元素到指定数组
 * @param arr 数组对象
 * @param v   要添加到数组的值
 * @param distinct 是否允许添加重复元素
 * @return Array
 */
function addToArray(arr,v, distinct){
  if(arr){
  	 if(distinct){//如果不添加重复元素，则执行循环查找
	  	 for(var i = 0; i < arr.length; i++){
			if(v.toString() == arr[i].toString())
				return;
	  	 }
  	 }
  	 arr.push(v);
  }
  
}

/**
 * 检查在数组是否存在某个值
 * @param arr 数组对象
 * @param v   要查找的值
 * @return index 在数组中出现的第一个位置
 */
function existInArray(arr, v){
	if(arr == null || arr.length == 0)
		return -1;
	for(var i=0;i<arr.length;i++){
		if(v.toString() == arr[i].toString()){
			return i;
		}
	}
	
	return -1;
}

/**
 * 将左侧下拉框的选项添加至右侧下拉框中
 * @param optionValue：左侧下拉框的ID
 * @param optionText： 右侧下拉框的ID
 * @param [optionValue]： 可选的值，追加至原值的后面
 * @return Option
 */
function clickToRight(left, right, delSrcOption){
	var optionArray = getSelectedOptions(left);
	
	if(optionArray){//如果未选择任何项，则进行提示
		var b = document.getElementById(right);
	
		if (b.beforecontentchange)
			b.beforecontentchange();
			
		var changed = false;
		for(var i=0; i < optionArray.length; i++){
			optionArray[i].selected = false;//将选中属性设为false，避免过去后就是选中状态
			if(!existInSelect(right, optionArray[i].value)){
				changed = true;
				if(delSrcOption){
					b.options.appendChild(optionArray[i]);
				}else{
					b.options.appendChild(optionArray[i].cloneNode(true));
				}
			}
		}
		if(changed)
			if(b.oncontentchange)
				b.oncontentchange();
	}else{
		alert("请选择要移动的项！");
	}
}

/**
 * 获得某个下拉框所有选中的option
 * @param 下拉框ID
 * @return Array
 */
function getSelectedOptions(objId){
	var a=document.getElementById(objId);
	if(a){
		var optionArray = new Array();
		for(var i=0; i < a.options.length; i++){
			if(a.options[i].selected){
				optionArray.push(a.options[i]);
			}
		}
		return optionArray;
	}
	
	return null;
}

/**
 * 删除选中的下拉框选项
 * @param selectId：下拉框ID
 */
function deleteSelectedOption(selectId, confirmed)
{
	var b = document.getElementById(selectId);
	//未选择要移动的项，给出提示
	if(b.selectedIndex == -1){
		alert("请选择要删除的项！");
		return;
	}
	if(!confirmed){
		if(!confirm("确认删除此项吗？")) return;
	}
	
	while(b.selectedIndex !=-1)
   	{
		//删除对应的项
   		b.options[b.selectedIndex] = null;
   	}
   	
   	if(b.oncontentchange)
		b.oncontentchange();
}

/**
 * 将某个下拉框的所有选项添加到另一个下拉框中
 * @param fromSelect：来源下拉框
 * @param toSelect：目标下拉框
 * @param optionValue：附加值（可选）
 */
function addAllOptionsWithoutClean(fromSelect, toSelect, delSrcOption){
	var a = document.getElementById(fromSelect);
	var b = document.getElementById(toSelect);
	
	if (b.beforecontentchange)
			b.beforecontentchange();
	
	var changed = false;
	var optionArray = new Array();
	for(var i=0; i<a.options.length; i++)
	{
		optionArray.push(a.options[i]);
	}
	for(var i=0; i<optionArray.length; i++)
	{
		optionArray[i].selected = false;//将选中属性设为false，避免过去后就是选中状态
		if(!existInSelect(toSelect, optionArray[i].value)){
			changed = true;
			if(delSrcOption){
				b.options.appendChild(optionArray[i]);
			}else{
				b.options.appendChild(optionArray[i].cloneNode(true));
			}
		}
	}
	
	if(changed)
		if(b.oncontentchange)
			b.oncontentchange();
			
	a = null;
	b = null;
	optionArray = null;
}

function existInSelect(target, value){
	var b = document.getElementById(target);
	var len = b.options.length;
	var exist = false;
	if(len > 0){//判断是否目标下拉框是否已经存在数据
		for(var j = 0; j < len; j++){//判断是否目标下拉框是否已经存在要添加的选项
			if(value == b.options[j].value){
				exist = true;
				break;
			}
		}
	}
	
	b = null;
	return exist;
}

function deleteOneRow(tblId, rowIndex, needAlert)
{
	if(needAlert){
		if(!confirm("确定删除此条件吗？")){
			return;
		}
	}
	
	var objTable = document.getElementById(tblId);
	if(objTable){
		objTable.deleteRow(rowIndex);
	}
	
	objTable = null;
}

Array.prototype.toJSONString = function(){
	var str = "";
	for (var key=0; key<this.length; key++){
		if(typeof this[key]=="number"){
			str += ","+this[key];
		}else if(typeof this[key] == "string"){
			str += ",\""+this[key]+"\"";
		}else if(!this[key] || this[key]==true){
			str += ","+this[key];
		}else if(typeof this[key]=="function"){
			str += ","+this[key].toString();
		}else if(typeof this[key] == "object"){  
			if(this[key].constructor == Array){
				str += ","+ this[key].toJSONString();
			}else if(this[key].constructor == Object){
				str += ","+this[key].toString();
			}
		}
	}
	return "["+str.substring(1)+"]";
}

Object.prototype.toString = function(){
	var str = "";
	for (var key in this){
		if(key=="toJSONString")continue;
		if(!this[key] || this[key]==true){
			str += ","+key+":\""+this[key]+"\"";
		}else if(typeof this[key]=="number"){
			str += ","+key+":"+this[key]+"";
		}else if(typeof this[key] == "string"){
			str += ","+key+":\""+this[key]+"\"";
		}else if(typeof this[key]=="function"){
			str += ","+key+":"+this[key].toString();
		}else if(typeof this[key] == "object"){  
			if(this[key].constructor == Array){
				str += ","+key+":"+this[key].toJSONString();
			}else if(this[key].constructor == Object){
				str += ","+key+":"+this[key].toString();
			}
		}
	
	}
	return "{"+str.substring(1)+"}";
}

 
/**
 * 初始化
 *
 */
var dataSelectHTMLObj = function(config){

	//if (!config.rootPath){
    //    alert("没有传入组件的路径！请在json中用参数rootPath传递过来！");
    //    return;
    //}
    
	if (!config.selectPrefix){
        alert("没有传入组件的ID！请在json中用参数id传递过来！");
        return;
    }
    
	if (!config.text_srcTitle){
		alert("没有定义父容器的ID！请在json中用parentContainer参数传递过来！");
		return;
	}
	
	if (!config.text_selectSrcTitle){
		alert("没有传入获取字段列表的URL前缀！请在json中用参数getColumnSrcPrefix传递过来！");
		return;
	}
	
	this.selectPrefix = config.selectPrefix;
	this.text_srcTitle = config.text_srcTitle;
	this.text_selectSrcTitle = config.text_selectSrcTitle;
	this.tableContainer = config.tableContainer;
	this.rootPath = config.rootPath;
	this.txnCode = config.txnCode;
	this.paramStr = config.paramStr;
	this.optionValue = config.optionValue;
	this.optionText = config.optionText;
	this.fillObjId = config.fillObjId;
	this.addtionalParam = config.addtionalParam;
	
	//主题及数据表选择部分
	this.selectTableHTMLObj = "<table cellpadding='0' style='table-layout:fixed;width:75%' cellspacing='0' border='0' align='center'>"+
							    "<tr height='30' >"+
							        "<td align='right' width='100' nowrap='nowrap'>"+this.text_srcTitle+"：</td>"+
							        "<td align='left' width='200'>"+
							            "<select name='source_"+this.selectPrefix+"' id='source_"+this.selectPrefix+"' style='width:100%'></select></td>"+
							        "<td></td>" + 
							        "<td width='200'></td>" + 
							    "</tr>"+
							    "<tr>"+
							        "<td align='right' height='200'>"+this.text_selectSrcTitle+"：</td>"+
							        "<td style='border:#6371b5 1px solid;overflow:hidden;'>"+
							            "<select name='from_"+this.selectPrefix+"' id='from_"+this.selectPrefix+"' multiple='true' class='dataSelect'></select></td>"+
							        "<td align='center'>"+
							            "<table width='100%' height='100' border='0' cellpading='0' cellspacing='0' align='center'>"+
							                "<tr><td align='center' valign='middle'><img id='addOne_"+this.selectPrefix+"' src='"+this.rootPath+"/images/table/button_nexts.gif' border='0' title='选取一项' onClick=''/></td></tr>"+
							                "<tr><td align='center' valign='middle'><img id='addAll_"+this.selectPrefix+"' src='"+this.rootPath+"/images/table/button_ends.gif' border='0' title='选取全部' onClick=''/></td></tr>"+
							                "<tr><td align='center' valign='middle'><img id='deleteOne_"+this.selectPrefix+"' src='"+this.rootPath+"/images/table/button_prvs.gif' border='0' title='取消一项' onClick=''/></td></tr>"+
							                "<tr><td align='center' valign='middle'><img id='deleteAll_"+this.selectPrefix+"' src='"+this.rootPath+"/images/table/button_begins.gif' border='0' title='取消全部' onClick=''/></td></tr>"+
							            "</table>"+
							        "</td>"+
							        "<td style='border:#6371b5 1px solid;overflow:hidden;'>"+
							            "<select name='selected_"+this.selectPrefix+"' id='selected_"+this.selectPrefix+"' multiple='true' class='dataSelect'></select>"+
							        "</td>"+
							    "</tr>"+
							"</table>";
	document.getElementById(config.tableContainer).innerHTML = this.selectTableHTMLObj;
	if (config.oncontentchange){
		document.getElementById("selected_"+this.selectPrefix).oncontentchange = config.oncontentchange;
	}
	
	if (config.beforecontentchange)
		document.getElementById("selected_"+this.selectPrefix).beforecontentchange = config.beforecontentchange;
	
	this.addEvent();
	
}

/**
 * 执行查询
 * @param txnCode: 交易代码
 * @param param: 参数
 * @param dataBusprefix：数据总线的前缀
 * @param selectObjId：显示结果的下拉框ID
 * @param valueColumn：下拉框的value值
 * @param textColumn：下拉框的显示值
 * @param [addtionalParam]：JSON对象，可选，附加属性和值
 * @param [checkDataSelect]：JSON对象，可选，删除时参照的对象ID
 * @param [callBackMethod]：function对象，可选，查询完数据后执行的方法
 */
dataSelectHTMLObj.prototype.doSelectQuery = function(txnCode, param, dataBusprefix, selectObjId, valueColumn, textColumn, addtionalParam, checkDataSelect, callBackMethod){
	clearOptions(selectObjId, true);
	if(!param)
		param = '';
		
	if(!dataBusprefix)
		dataBusprefix = "record";
		
	$.get(this.rootPath + txnCode + param, function(xml){
		fillXmlToSelect(xml, dataBusprefix, selectObjId, valueColumn, textColumn, addtionalParam, checkDataSelect);
		if(callBackMethod){
			callBackMethod();
		}
		document.getElementById(selectObjId).onchange();
	});
	
}

// 添加事件
dataSelectHTMLObj.prototype.addEvent = function(){
	var p = this;
	// 设定宽度
	var tableContainer = document.getElementById(p.tableContainer);
	if (tableContainer){
		tableContainer.onresize = function(){
			if (this.offsetWidth < 800){
				this.childNodes[0].style.width = "600";
			}else{
				this.childNodes[0].style.width = "75%";
			}
		};
	}
	
	//主题变更时查询对应的表
	var obj = document.getElementById("source_" + p.selectPrefix);
	if (obj){
		obj.onchange = function(){
			p.queryDatas();
		};
	}
	
	//双击添加到右侧下拉框
	obj = document.getElementById("from_" + p.selectPrefix);
	if (obj){
		obj.ondblclick = function(){
			clickToRight("from_" + p.selectPrefix, "selected_" + p.selectPrefix, true);
		};
	}
	
	//双击删除选中的项
	obj = document.getElementById("selected_" + p.selectPrefix);
	if (obj){
		obj.ondblclick = function(){
			var n = "";
			if(p.addtionalParam){
				for(var attName in p.addtionalParam){
					n = attName;
					break;
				}
			}
			p.clickToLeft(n);
		};
	}
	
	//添加一个或多个项到右侧下拉框
	obj = document.getElementById("addOne_" + p.selectPrefix);
	if (obj){
		obj.onclick = function(){
			clickToRight("from_" + p.selectPrefix, "selected_" + p.selectPrefix, true);
		};
	}
	
	//添加所有项到右侧下拉框
	obj = document.getElementById("addAll_" + p.selectPrefix);
	if (obj){
		obj.onclick = function(){
			addAllOptionsWithoutClean("from_" + p.selectPrefix, "selected_" + p.selectPrefix, true);
		};
	}
	
	//删除一个或多个项
	obj = document.getElementById("deleteOne_" + p.selectPrefix);
	if (obj){
		obj.onclick = function(){
			var n = "";
			if(p.addtionalParam){
				for(var attName in p.addtionalParam){
					n = attName;
					break;
				}
			}
			p.clickToLeft(n);
		};
	}
	
	//删除所有项
	obj = document.getElementById("deleteAll_" + p.selectPrefix);
	if (obj){
		obj.onclick = function(){
			var n = "";
			if(p.addtionalParam){
				for(var attName in p.addtionalParam){
					n = attName;
					break;	
				}
			}
			p.deleteAllOptions(n);
		};
	}
	
};

dataSelectHTMLObj.prototype.queryDatas = function(){
	var p = this;
	var obj = document.getElementById("source_" + p.selectPrefix);
	
	if (obj){
		var value = obj.options[obj.selectedIndex].value;
		p.doSelectQuery(p.txnCode, p.paramStr + value, null, p.fillObjId, p.optionValue, p.optionText, p.addtionalParam, "selected_"+p.selectPrefix);
	}
	obj = null;
};

dataSelectHTMLObj.prototype.updateColumnTables = function(fromSelect, toSelect, confirmed){
	var p = this;
	if(!confirmed && document.getElementById(fromSelect).options.length == 0){
		alert("未选择数据表！");
		return;
	}
	document.getElementById(toSelect).options.length = 0
	addAllOptionsWithoutClean(fromSelect, toSelect);
	var obj = document.getElementById(toSelect);
	if(obj.options.length == 0){
		return;
	}
	if(obj)
		this.queryColumns(obj.options[obj.selectedIndex].value);
}

dataSelectHTMLObj.prototype.queryColumns = function(tblId){
	this.queryDatas();
};

dataSelectHTMLObj.prototype.clickToLeft = function(attributeName, confirmed){
	var p = this;
	var b = document.getElementById("selected_" + p.selectPrefix);
	if(b.selectedIndex == -1){
		alert("请选择要删除的项！");
		return;
	}
	if(!confirmed){
		if(!confirm("确认删除此项吗？")) return;
	}
	var optionArray = getSelectedOptions("selected_" + p.selectPrefix);
	
			
	if(b.beforecontentchange && !b.beforecontentchange("delOne", true))
		return; 
		
	var parendObj = document.getElementById("source_" + p.selectPrefix);
	var a = document.getElementById("from_" + p.selectPrefix);
	// a, b 都是下拉框，如果a, b 都不显示的时候，可能会冒出来，这时候需要先手动将其隐藏，动作执行完成后再将其显示出来
	var _display_a = a.style.display;
	if (a.offsetWidth == 0){
		a.style.display = "none";
	}
	var _display_b = b.style.display;
	if (b.offsetWidth == 0){
		b.style.display = "none";
	}
	
	if(parendObj && optionArray){
		var parendId = parendObj.options[parendObj.selectedIndex].value;
		for(var i=0; i < optionArray.length; i++){
			var childParentValue = eval("optionArray[i]."+attributeName);
			if((!childParentValue) || (parendId && childParentValue && parendId.toLowerCase() == childParentValue.toLowerCase())){
				a.options.appendChild(optionArray[i]);
			}else{
				b.removeChild(optionArray[i]);
			}
		}
	}
	// 重新把a, b 的显示状态重置为原先的
	a.style.display = _display_a;
	b.style.display = _display_b;
	if(b.oncontentchange)
		b.oncontentchange();
	
	//强制调用onchange，用来重新对数据排序
	parendObj.fireEvent("onchange");
	b = null;
	a = null;
};

/**
 * 删除下拉框的所有项
 * @param attributeName 用于判断父下拉框的值
 * @param confirmed 是否需要提示确认信息
 */
dataSelectHTMLObj.prototype.deleteAllOptions = function(attributeName, confirmed){
	var p = this;
	var b = document.getElementById("selected_" + p.selectPrefix);
	if(b.options.length == 0){
		alert("无可删除的项！");
		return;
	}
	if(!confirmed){
		if(!confirm("确认删除所有项吗？")) return;
	}
	
	if(b.beforecontentchange && !b.beforecontentchange("delAll", true))
		return; 
			
	for(var i=0; i<b.options.length; i++){
		b.options[i].selected = true;
	}
	
	p.clickToLeft(attributeName, true);
	
}

/**
 * 获得下拉框项的指定属性的值
 * @param attributeName
 * @return Array
 */
dataSelectHTMLObj.prototype.getSelectedDataValues = function(attributeName){
	var p = this;
	var obj = document.getElementById("selected_" + p.selectPrefix).options;
	var vArray = new Array();
	if(obj){
		for(var i=0; i<obj.length; i++){
			addToArray(vArray, eval("obj[i]."+attributeName));
		}
	}
	
	return vArray;
}

dataSelectHTMLObj.prototype.getMixedDataValues = function(attributeNames, pattern){
	var p = this;
	var obj = document.getElementById("selected_" + p.selectPrefix).options;
	var vArray = new Array();
	if(obj){
		for(var i=0; i<obj.length; i++){
			var val = '';
			if(attributeNames){
				for(var j = 0 ; j < attributeNames.length; j++){
					val += eval("obj[i]."+attributeNames[j]);
					if(j == attributeNames.length - 1){
						continue;
					}
					val += pattern;
				}
			}
			addToArray(vArray, val);
		}
	}
	
	return vArray;
}


/**
 * 将xml对象转化为select的内容 
 * 参数列表
 * xml              : 读取过来的xml对象
 * nodeName         : 读取的节点名称
 * selectObjectId   : 需要设值的下拉框对象的ID
 * valueColumn      : 对应下拉框value的xml节点名称
 * textColumn       : 对应下拉框text的xml节点名称
 * additionParams   : 附加参数( jason对象 )
 * checkDataSelect	: 需要过滤数据的下拉框对象ID，如果不需要过滤对象，不传此参数即可
 * 调用方法示例：fillXmlToSelect(xmlObject, "record", "selectTest", "sys_id", "sys_name");
 */
function fillXmlToSelect(xml, nodeName, selectObjectId, valueColumn, textColumn, additionParams, checkDataSelect){
    var exsitValueArray = new Array;
    if (checkDataSelect){
    	var checkDataSelectDom = document.getElementById(checkDataSelect);
    	try{
	        var options = checkDataSelectDom.options;
	        for(var tt = 0; tt < options.length; tt++){
	        	exsitValueArray.push(options[tt].value);
	        }
    	}catch(e){
    		alert("没有找到ID为【" + "】的下拉框");
    	}
    }
    
    var selectObject;
    if (typeof selectObjectId == "object"){
    	selectObject = selectObjectId;
    }else{
    	selectObject = document.getElementById(selectObjectId);
    }
    
    // 先清空selectObject
    selectObject.innerHTML = "";
    if (selectObject){
        var nodeArray = xml.selectNodes("//" + nodeName);
        for (var i = 0; i < nodeArray.length; i++){
            var nodeElement = nodeArray.item(i);
            var optionValue = nodeElement.selectSingleNode(valueColumn);
            var optionText = nodeElement.selectSingleNode(textColumn);
            
            var isExist = false;
            
            var option = document.createElement("option");
            if (optionValue){
            	// 校验是否存在
            	if ( exsitValueArray.length > 0 ){
            		for (var arrayIter = 0; arrayIter < exsitValueArray.length; arrayIter ++){
            			if (exsitValueArray[arrayIter] == optionValue.text){
            				isExist = true;
            				break;
            			}
            		}
                }
            }
            if(!isExist && optionText){
                var text = document.createTextNode(optionText.text);
                option.value = optionValue.text;
                option.appendChild(text);
                if (additionParams){
                    // 添加option的附加属性
                    for (var paramName in additionParams){
                        var paramValue = eval("additionParams." + paramName);
                        var tempNode = nodeElement.selectSingleNode(paramValue);
                        var addtionValue = tempNode ? tempNode.text : "";
                        tempNode = null;
                        eval("option." + paramName + " = '" + addtionValue + "'");
                    }
                }
            	// 只有在text不为空的时候，且不存在的时候，才执行添加，不然添加了一个空白到界面上没有意义
            	selectObject.appendChild(option);
            }
        }
        selectObject = null;
        exsitValueArray = null;
    }else{
        alert("没有找到ID为【" + selectObjectId + "】的下拉框！"); 
    }
}


