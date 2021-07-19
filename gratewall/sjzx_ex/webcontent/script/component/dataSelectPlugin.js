/**
 * Author : wangyx
 * ��������ص�һЩ����
 */
/**
 * �������������������
 * @param selectId��������ID
 */
function clearOptions(selectId){
	var obj = document.getElementById(selectId);
	if(obj && obj.tagName && obj.tagName.toUpperCase() == "SELECT"){
		obj.innerHTML = "";
	}
}

/**
 * �����������ѡ��
 * @param optionValue��ѡ���ֵ
 * @param optionText�� ѡ�����ʾֵ
 * @param addtionAttributes JSON���󣬱�ʾ��������
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
 * ���Ԫ�ص�ָ������
 * @param arr �������
 * @param v   Ҫ��ӵ������ֵ
 * @param distinct �Ƿ���������ظ�Ԫ��
 * @return Array
 */
function addToArray(arr,v, distinct){
  if(arr){
  	 if(distinct){//���������ظ�Ԫ�أ���ִ��ѭ������
	  	 for(var i = 0; i < arr.length; i++){
			if(v.toString() == arr[i].toString())
				return;
	  	 }
  	 }
  	 arr.push(v);
  }
  
}

/**
 * ����������Ƿ����ĳ��ֵ
 * @param arr �������
 * @param v   Ҫ���ҵ�ֵ
 * @return index �������г��ֵĵ�һ��λ��
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
 * ������������ѡ��������Ҳ���������
 * @param optionValue������������ID
 * @param optionText�� �Ҳ��������ID
 * @param [optionValue]�� ��ѡ��ֵ��׷����ԭֵ�ĺ���
 * @return Option
 */
function clickToRight(left, right, delSrcOption){
	var optionArray = getSelectedOptions(left);
	
	if(optionArray){//���δѡ���κ���������ʾ
		var b = document.getElementById(right);
	
		if (b.beforecontentchange)
			b.beforecontentchange();
			
		var changed = false;
		for(var i=0; i < optionArray.length; i++){
			optionArray[i].selected = false;//��ѡ��������Ϊfalse�������ȥ�����ѡ��״̬
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
		alert("��ѡ��Ҫ�ƶ����");
	}
}

/**
 * ���ĳ������������ѡ�е�option
 * @param ������ID
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
 * ɾ��ѡ�е�������ѡ��
 * @param selectId��������ID
 */
function deleteSelectedOption(selectId, confirmed)
{
	var b = document.getElementById(selectId);
	//δѡ��Ҫ�ƶ����������ʾ
	if(b.selectedIndex == -1){
		alert("��ѡ��Ҫɾ�����");
		return;
	}
	if(!confirmed){
		if(!confirm("ȷ��ɾ��������")) return;
	}
	
	while(b.selectedIndex !=-1)
   	{
		//ɾ����Ӧ����
   		b.options[b.selectedIndex] = null;
   	}
   	
   	if(b.oncontentchange)
		b.oncontentchange();
}

/**
 * ��ĳ�������������ѡ����ӵ���һ����������
 * @param fromSelect����Դ������
 * @param toSelect��Ŀ��������
 * @param optionValue������ֵ����ѡ��
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
		optionArray[i].selected = false;//��ѡ��������Ϊfalse�������ȥ�����ѡ��״̬
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
	if(len > 0){//�ж��Ƿ�Ŀ���������Ƿ��Ѿ���������
		for(var j = 0; j < len; j++){//�ж��Ƿ�Ŀ���������Ƿ��Ѿ�����Ҫ��ӵ�ѡ��
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
		if(!confirm("ȷ��ɾ����������")){
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
 * ��ʼ��
 *
 */
var dataSelectHTMLObj = function(config){

	//if (!config.rootPath){
    //    alert("û�д��������·��������json���ò���rootPath���ݹ�����");
    //    return;
    //}
    
	if (!config.selectPrefix){
        alert("û�д��������ID������json���ò���id���ݹ�����");
        return;
    }
    
	if (!config.text_srcTitle){
		alert("û�ж��常������ID������json����parentContainer�������ݹ�����");
		return;
	}
	
	if (!config.text_selectSrcTitle){
		alert("û�д����ȡ�ֶ��б��URLǰ׺������json���ò���getColumnSrcPrefix���ݹ�����");
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
	
	//���⼰���ݱ�ѡ�񲿷�
	this.selectTableHTMLObj = "<table cellpadding='0' style='table-layout:fixed;width:75%' cellspacing='0' border='0' align='center'>"+
							    "<tr height='30' >"+
							        "<td align='right' width='100' nowrap='nowrap'>"+this.text_srcTitle+"��</td>"+
							        "<td align='left' width='200'>"+
							            "<select name='source_"+this.selectPrefix+"' id='source_"+this.selectPrefix+"' style='width:100%'></select></td>"+
							        "<td></td>" + 
							        "<td width='200'></td>" + 
							    "</tr>"+
							    "<tr>"+
							        "<td align='right' height='200'>"+this.text_selectSrcTitle+"��</td>"+
							        "<td style='border:#6371b5 1px solid;overflow:hidden;'>"+
							            "<select name='from_"+this.selectPrefix+"' id='from_"+this.selectPrefix+"' multiple='true' class='dataSelect'></select></td>"+
							        "<td align='center'>"+
							            "<table width='100%' height='100' border='0' cellpading='0' cellspacing='0' align='center'>"+
							                "<tr><td align='center' valign='middle'><img id='addOne_"+this.selectPrefix+"' src='"+this.rootPath+"/images/table/button_nexts.gif' border='0' title='ѡȡһ��' onClick=''/></td></tr>"+
							                "<tr><td align='center' valign='middle'><img id='addAll_"+this.selectPrefix+"' src='"+this.rootPath+"/images/table/button_ends.gif' border='0' title='ѡȡȫ��' onClick=''/></td></tr>"+
							                "<tr><td align='center' valign='middle'><img id='deleteOne_"+this.selectPrefix+"' src='"+this.rootPath+"/images/table/button_prvs.gif' border='0' title='ȡ��һ��' onClick=''/></td></tr>"+
							                "<tr><td align='center' valign='middle'><img id='deleteAll_"+this.selectPrefix+"' src='"+this.rootPath+"/images/table/button_begins.gif' border='0' title='ȡ��ȫ��' onClick=''/></td></tr>"+
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
 * ִ�в�ѯ
 * @param txnCode: ���״���
 * @param param: ����
 * @param dataBusprefix���������ߵ�ǰ׺
 * @param selectObjId����ʾ�����������ID
 * @param valueColumn���������valueֵ
 * @param textColumn�����������ʾֵ
 * @param [addtionalParam]��JSON���󣬿�ѡ���������Ժ�ֵ
 * @param [checkDataSelect]��JSON���󣬿�ѡ��ɾ��ʱ���յĶ���ID
 * @param [callBackMethod]��function���󣬿�ѡ����ѯ�����ݺ�ִ�еķ���
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

// ����¼�
dataSelectHTMLObj.prototype.addEvent = function(){
	var p = this;
	// �趨���
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
	
	//������ʱ��ѯ��Ӧ�ı�
	var obj = document.getElementById("source_" + p.selectPrefix);
	if (obj){
		obj.onchange = function(){
			p.queryDatas();
		};
	}
	
	//˫����ӵ��Ҳ�������
	obj = document.getElementById("from_" + p.selectPrefix);
	if (obj){
		obj.ondblclick = function(){
			clickToRight("from_" + p.selectPrefix, "selected_" + p.selectPrefix, true);
		};
	}
	
	//˫��ɾ��ѡ�е���
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
	
	//���һ��������Ҳ�������
	obj = document.getElementById("addOne_" + p.selectPrefix);
	if (obj){
		obj.onclick = function(){
			clickToRight("from_" + p.selectPrefix, "selected_" + p.selectPrefix, true);
		};
	}
	
	//���������Ҳ�������
	obj = document.getElementById("addAll_" + p.selectPrefix);
	if (obj){
		obj.onclick = function(){
			addAllOptionsWithoutClean("from_" + p.selectPrefix, "selected_" + p.selectPrefix, true);
		};
	}
	
	//ɾ��һ��������
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
	
	//ɾ��������
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
		alert("δѡ�����ݱ�");
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
		alert("��ѡ��Ҫɾ�����");
		return;
	}
	if(!confirmed){
		if(!confirm("ȷ��ɾ��������")) return;
	}
	var optionArray = getSelectedOptions("selected_" + p.selectPrefix);
	
			
	if(b.beforecontentchange && !b.beforecontentchange("delOne", true))
		return; 
		
	var parendObj = document.getElementById("source_" + p.selectPrefix);
	var a = document.getElementById("from_" + p.selectPrefix);
	// a, b �������������a, b ������ʾ��ʱ�򣬿��ܻ�ð��������ʱ����Ҫ���ֶ��������أ�����ִ����ɺ��ٽ�����ʾ����
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
	// ���°�a, b ����ʾ״̬����Ϊԭ�ȵ�
	a.style.display = _display_a;
	b.style.display = _display_b;
	if(b.oncontentchange)
		b.oncontentchange();
	
	//ǿ�Ƶ���onchange���������¶���������
	parendObj.fireEvent("onchange");
	b = null;
	a = null;
};

/**
 * ɾ���������������
 * @param attributeName �����жϸ��������ֵ
 * @param confirmed �Ƿ���Ҫ��ʾȷ����Ϣ
 */
dataSelectHTMLObj.prototype.deleteAllOptions = function(attributeName, confirmed){
	var p = this;
	var b = document.getElementById("selected_" + p.selectPrefix);
	if(b.options.length == 0){
		alert("�޿�ɾ�����");
		return;
	}
	if(!confirmed){
		if(!confirm("ȷ��ɾ����������")) return;
	}
	
	if(b.beforecontentchange && !b.beforecontentchange("delAll", true))
		return; 
			
	for(var i=0; i<b.options.length; i++){
		b.options[i].selected = true;
	}
	
	p.clickToLeft(attributeName, true);
	
}

/**
 * ������������ָ�����Ե�ֵ
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
 * ��xml����ת��Ϊselect������ 
 * �����б�
 * xml              : ��ȡ������xml����
 * nodeName         : ��ȡ�Ľڵ�����
 * selectObjectId   : ��Ҫ��ֵ������������ID
 * valueColumn      : ��Ӧ������value��xml�ڵ�����
 * textColumn       : ��Ӧ������text��xml�ڵ�����
 * additionParams   : ���Ӳ���( jason���� )
 * checkDataSelect	: ��Ҫ�������ݵ����������ID���������Ҫ���˶��󣬲����˲�������
 * ���÷���ʾ����fillXmlToSelect(xmlObject, "record", "selectTest", "sys_id", "sys_name");
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
    		alert("û���ҵ�IDΪ��" + "����������");
    	}
    }
    
    var selectObject;
    if (typeof selectObjectId == "object"){
    	selectObject = selectObjectId;
    }else{
    	selectObject = document.getElementById(selectObjectId);
    }
    
    // �����selectObject
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
            	// У���Ƿ����
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
                    // ���option�ĸ�������
                    for (var paramName in additionParams){
                        var paramValue = eval("additionParams." + paramName);
                        var tempNode = nodeElement.selectSingleNode(paramValue);
                        var addtionValue = tempNode ? tempNode.text : "";
                        tempNode = null;
                        eval("option." + paramName + " = '" + addtionValue + "'");
                    }
                }
            	// ֻ����text��Ϊ�յ�ʱ���Ҳ����ڵ�ʱ�򣬲�ִ����ӣ���Ȼ�����һ���հ׵�������û������
            	selectObject.appendChild(option);
            }
        }
        selectObject = null;
        exsitValueArray = null;
    }else{
        alert("û���ҵ�IDΪ��" + selectObjectId + "����������"); 
    }
}


