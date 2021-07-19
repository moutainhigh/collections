var TYPE_WCMOBJ_USER	= 204;
var TYPE_WCMOBJ_GROUP	= 201;
var TYPE_WCMOBJ_ROLE	= 203;

var WCMOBJ_PROPERTIES_NODE_NAME = "PROPERTIES";

function CNewWCMRightHelper(){
//定义属性
	this.arRightDef	= {};	
	this.arRight		= [];
	this.bUpdate		= false;
	this.Order			= 0;

//定义方法
	this.draw			= CNewWCMRightHelper_draw; //生成权限的交互页面
	this.addRightDef	= CNewWCMRightHelper_addRightDef; //添加权限的定义
	this.addRight		= CNewWCMRightHelper_addRight; //添加权限
	this.selectAll		= CNewWCMRightHelper_selectAll;
	this.removeAt		= CNewWCMRightHelper_removeAt;
	this.removeAll		= CNewWCMRightHelper_removeAll;
	this.putOperator	= CNewWCMRightHelper_putOperator;

	this.getIdArray		= CNewWCMRightHelper_getIdArray;
	this.getNameArray	= CNewWCMRightHelper_getNameArray;
	this.getIndexArray	= CNewWCMRightHelper_getIndexArray;
	this.toXMLInfo		= CNewWCMRightHelper_toXMLInfo;

	this.setRightStatus	= CNewWCMRightHelper_setRightStatus;
	this.compare		= CNewWCMRightHelper_compare;
	this.sort			= CNewWCMRightHelper_sortFunction;
	this.selectCol		= CNewWCMRightHelper_selectCol;
}

function getParentElementByTagName(_element, _sTagName){
	if(_element == null)
		return null;

	var parent = _element.parentElement;
	if(parent == null)
		return null;
	var sTagName = parent.tagName;
	if(sTagName == null || sTagName == "BODY")
		return null;

	if(sTagName == _sTagName)
		return parent;

	return getParentElementByTagName(parent, _sTagName);
}

function CNewWCMRightHelper_getColSelectAllStatus(_oParentTable, _nBeginRowIndex, _nCellIndex){
	var oRows			= _oParentTable.rows;
	for(var i=_nBeginRowIndex; i<oRows.length; i++){
		var oCell		= oRows[i].cells[_nCellIndex];
		var arEls		= oCell.getElementsByTagName("INPUT");
		if(arEls == null || arEls.length<=0){
			CTRSAction_alert("数据有误："+oCell.outerHTML);
			return;
		}

		if(!arEls[0].checked)
			return true;
	}
	return false;
}

function CNewWCMRightHelper_selectCol(_elEventTD, _nRightIndex){
	var nCellIndex		= _elEventTD.cellIndex;
	var oParentTR		= getParentElementByTagName(_elEventTD, "TR");
	var nTRIndex		= oParentTR.rowIndex;
	var oParentTable	= getParentElementByTagName(oParentTR, "TABLE");
	
	var bChecked		= CNewWCMRightHelper_getColSelectAllStatus(oParentTable, nTRIndex+1, nCellIndex);
	var oRows			= oParentTable.rows;
	var nRightNo		= 0;
	for(var i=nTRIndex+1; i<oRows.length; i++, nRightNo++){
		var oCell		= oRows[i].cells[nCellIndex];
		var arEls		= oCell.getElementsByTagName("INPUT");
		if(arEls == null || arEls.length<=0){
			CTRSAction_alert("数据有误："+oCell.outerHTML);
			return;
		}

		if(arEls[0].checked != bChecked){
			arEls[0].click();
		}
		//arEls[0].checked = bChecked;

		//NewWCMRightHelper.setRightStatus(nRightNo, _nRightIndex, arEls[0].checked);
	}
}


function CWCMRight(_nOprType, _nOprId, _sName, _sValue, _nRightId){
//定义对象的属性
	this.nRightId			= _nRightId || 0;
	this.nOprType			= _nOprType;
	this.nOprId				= _nOprId;
	this.sName				= _sName;

	var nLen = _sValue.length;
	this.arValue			= new Array();
	for(var i=nLen; i>0; i--){
		this.arValue[nLen-i] = _sValue.charAt(i-1);
	}
	//this.arValue			= (_sValue == null?[]:_sValue.split(""));	
	

	this.oWCMObj			= new CWCMObj();
	this.oWCMObj.setProperty("OPRTYPE", this.nOprType);
	this.oWCMObj.setProperty("OPRID", this.nOprId);
	this.oWCMObj.setProperty("RIGHTVALUE", _sValue);
	this.oWCMObj.setProperty("RIGHTID", this.nRightId);

//定义对象的方法
	this.isHasRight			= CWCMRight_isHasRight;
	this.getKey				= CWCMRight_getKey;
	this.toXML				= CWCMRight_toXML;
	this.getOperatorType	= function(){
		return this.nOprType;
	};
	this.getOperatorId	= function(){
		return this.nOprId;
	};

	this.setRightStatus	= CWCMRight_setRightStatus;
	
}

function getTypeHTML(_nType){
	switch(_nType){
		case TYPE_WCMOBJ_USER:
			return "<img src='../images/wcm52/icon_user.gif' title='用户'>";
		case TYPE_WCMOBJ_GROUP:
			return "<img src='../images/wcm52/icon_group.gif' title='用户组'>";
		case TYPE_WCMOBJ_ROLE:
			return "<img src='../images/wcm52/icon_role.gif' title='角色'>";
		default:
			return "未知";
	}
}

function CWCMOperator_toHTML(){
	switch(this.nType){
		case TYPE_WCMOBJ_USER:
			return "用户["+this.sName+"]";
		case TYPE_WCMOBJ_GROUP:
			return "用户组["+this.sName+"]";
		case TYPE_WCMOBJ_ROLE:
			return "角色["+this.sName+"]";
		default:
			return "未知["+this.sName+"]";
	}
}

function CWCMOperator_getKey(){
	return this.nType+"_"+this.nId;
}


function CWCMOperator(_nType, _nId, _sName){
//定义属性
	this.nType	= _nType;
	this.nId		= _nId;
	this.sName	= _sName;

//定义方法
	this.getKey = function(){
		return this.nType+"_"+this.nId;
	};

	this.toHTML = CWCMOperator_toHTML;	
}

function CWCMRight_isHasRight(_nRightIndex){
	if((_nRightIndex+1) > this.arValue.length)
		return false;
	return (this.arValue[_nRightIndex] != 0);	
}

function CWCMRight_getKey(){
	return this.oWCMObj.getProperty("OPRTYPE") + "_" + this.oWCMObj.getProperty("OPRID");
}


function CWCMRight_toXML(){	
	return WCMObjHelper.toXMLString(this.oWCMObj, "WCMRIGHT");
}



function CWCMRight_setRightStatus(_nRightIndexs, _bHasRight){
	//parseInt一下结果,防止下一句"+"代码变成字符串连接,造成空间的浪费
	var _nRightIndex = parseInt(Math.max.apply(Math, _nRightIndexs));
	if(this.arValue.length<(_nRightIndex+1)){
		this.arValue.length = (_nRightIndex+1);
		for(var i=0; i<(_nRightIndex+1); i++){
			if(this.arValue[i] == null || this.arValue[i].length==0){
				this.arValue[i] = 0;
			}
		}
	}
	//this.arValue[_nRightIndex] = (_bHasRight?1:0);
	_bHasRight = _bHasRight ? 1 : 0;
	for (var i = 0; i < _nRightIndexs.length; i++){
		//判断当前索引是否存在
		if(NewWCMRightHelper.RightIndexMapping[_nRightIndexs[i]]){
			this.arValue[_nRightIndexs[i]] = _bHasRight;
		}
	}

	var str = "";
	for(var i=(this.arValue.length-1); i>=0; i--){
		str += this.arValue[i];
	}	
	this.oWCMObj.setProperty("RIGHTVALUE", str);
}


function CNewWCMRightHelper_notifyRemove(_elEvent){
	//$removeNode(_elEvent.parentNode.parentNode);
}

function CNewWCMRightHelper_onClickRightCheck(_elEvent, _nRightNo, _nRightIndex){
	//权限是否修改的标记
	window.GRightChanged = true;
	var arValues = [];
	arValues.push(_elEvent.value);
	//Depends只是在选中状态下才有效	
	if(_elEvent.checked){
		var sDepends = _elEvent.getAttribute("Depends");
		if(sDepends != null && sDepends.length>0)
			arValues = arValues.concat(sDepends.split(","));	
	}
	//Similar只是在非选中状态下才有效	
	else{
		var sSimilar = _elEvent.getAttribute("Similar");
		if(sSimilar != null && sSimilar.length>0)
			arValues = arValues.concat(sSimilar.split(","));		
	}

	TRSHTMLElement.selectByNameAndExeClick(_elEvent.name, _elEvent.checked, arValues, function(){
		setRightsInfoForUpdate.call(NewWCMRightHelper, _nRightNo, _elEvent.name);
	});
	//NewWCMRightHelper.setRightStatus(_nRightNo, _nRightIndex, _elEvent.checked);
	//modified by hxj 不止修改这个checkbox权限,把关联的权限也要进行修改
	//NewWCMRightHelper.setRightStatus(_nRightNo, arValues, _elEvent.checked);
}

/*
*hxj update on 2007-7-31
*由当初的只更新修改之后的权限位改成权限重新生成，
*目的是为了将设置栏目权限时清空栏目的站点类权限，
*否则栏目可能有这种情况：有站点类权限，没有其他类权限，
*但用户是看不到站点类权限的，所以就认为是没有栏目权限，
*但却看到了栏目[由于导航树用Access权限]
*/
function setRightsInfoForUpdate(_nRightNo, chkName){
	var totalInputs = document.getElementsByName(chkName);
	var newRightValue = new Array(64);
	for (var i = 0; i < newRightValue.length; i++){//初始化数组
		newRightValue[i] = 0;
	}
	for (var i = 0; i < totalInputs.length; i++){
		if(totalInputs[i].checked){
			var rightIndex = totalInputs[i].value;
			newRightValue[63-rightIndex] = 1;			
		}
	}
	oRight = this.arRight[_nRightNo];
	oRight.oWCMObj.setProperty("RIGHTVALUE", newRightValue.join(""));
	oRight.arValue = []; 
	for (var i = newRightValue.length-1; i >=0; i--){
		oRight.arValue.push(newRightValue[i]);
	}
}

function CNewWCMRightHelper_draw(_sRightType, _elDiv){
	var sHTML = ""
			+"<TABLE width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" bgcolor=\"A6A6A6\">" 
			+"							<TR class=\"grid_head\" align='center'>"			
			+((currMode == 'edit') ? "<TD bgcolor=\"#BEE2FF\" onclick='selectAllCheck(this)' style='cursor:pointer;'>全选</TD>" : "<TD bgcolor=\"#BEE2FF\">序号</TD>")
			+"<TD bgcolor=\"#BEE2FF\">类型</TD>"
			+"<TD bgcolor=\"#BEE2FF\"><A href=\"#\" onClick=\"NewWCMRightHelper.sort();init();return false;\">名称";

	if (this.Order == 1) sHTML +="▲";
	if (this.Order == -1) sHTML +="▼";
		
	sHTML += "</A></TD>";

	var sRightType = _sRightType.toLowerCase();

	
	//按原顺序显示设置者，并且排序方式不变
	var nOrder = this.Order;
		
	this.Order = 0- this.Order;
	this.sort();
	this.Order = nOrder;

	//Table Head
	var currRightDef = this.arRightDef[sRightType];
	for (var i = 0; i < currRightDef.length; i++){
		var tempRightDef = currRightDef[i];
		/*
		*保存已添加权限索引的Hash对象，以便联动选择时判断是否需要重置相应的
		*权限位
		*/
		if(!this.RightIndexMapping){
			this.RightIndexMapping = {};
		}
		this.RightIndexMapping[tempRightDef["Index"]] = true;

		if(window.currMode == 'edit'){
			sHTML += "<TD bgcolor=\"#BEE2FF\" title=\""+tempRightDef["Desc"]+"[点击后可以选择一列]\" "
					+"onclick=\"NewWCMRightHelper.selectCol(this, "+tempRightDef["Index"]+");\" style=\"cursor:hand\">"
					+ tempRightDef["Name"] + "</TD>";
		}else{
			sHTML += "<TD bgcolor=\"#BEE2FF\">" + tempRightDef["Name"] + "</TD>";		
		}
	}
	if(window.currMode == 'edit')
		sHTML += "<TD bgcolor=\"#BEE2FF\">删除</TD>\n"			
	sHTML += "</TR>";

	//Table Body
	var oRight = null, nRightIndex, rowClass;
	for(var i=0; i<this.arRight.length; i++){
		oRight = this.arRight[i];
		rowClass = "grid_row " + (((i+1)%2)==1 ? "grid_row_odd" : "grid_row_even");
		if(window.currMode == 'edit'){
			sHTML	+= "<TR style=\"font-size:9pt;\" class=\"" + rowClass + "\">\n"
					//+"<TD align=center>" + (i+1) + "<input type=checkbox onclick=\"NewWCMRightHelper.selectAll("+i+", '"+sRightType+"', this.checked)\">"
					+"<TD align=center>" + (i+1) + "<input type=checkbox onclick=\"NewWCMRightHelper.selectAll("+i+", '', this.checked, this)\">"
					+"</TD>"
					+"<TD align=center>"+getTypeHTML(oRight.nOprType)+"</TD>"
					+"<TD align=left style='padding-left:5px;'>"+oRight.sName+"</TD>";			
		}else{
			sHTML	+= "<TR style=\"font-size:9pt;\" class=\"" + rowClass + "\">\n"
					+"<TD align=center>" + (i+1) +"</TD>"
					+"<TD align=center>"+getTypeHTML(oRight.nOprType)+"</TD>"
					+"<TD align=left style='padding-left:5px;'>"+oRight.sName+"</TD>";					
		}
		//所有选项
		for (var j = 0; j < currRightDef.length; j++){	
			var oRightDefTemp = currRightDef[j];
			nRightIndex = oRightDefTemp["Index"];
			if(window.currMode == 'edit'){
				var sAttrs = "";
				if(oRightDefTemp["Depends"] != null){
					sAttrs = " Depends=\""+oRightDefTemp["Depends"]+"\" ";
				}
				if(oRightDefTemp["Similar"] != null){
					sAttrs += " Similar=\""+oRightDefTemp["Similar"]+"\" ";
				}				
				sHTML += "<TD  align=center title=\""+oRightDefTemp["Desc"]+"\">"
						+ "<input type=checkbox needDeal='1' onClick=\"CNewWCMRightHelper_onClickRightCheck(this, "+i+", "+nRightIndex+")\" value=\""+nRightIndex+"\""
						//+" name=\""+sRightType+"_"+oRight.getKey()+"\""
						+" name=\"_"+oRight.getKey()+"\""
						+(oRight.isHasRight(nRightIndex)?" checked ":"")
						+sAttrs
						+">" + "</TD>";
			}else{
				sHTML += "<TD  align=center title=\""+oRightDefTemp["Desc"]+"\">"
						+ "<img src='../images/auth/" + (oRight.isHasRight(nRightIndex) ? "hasright.gif" : "noright.gif") + "'>" + "</TD>";			
			}
		}
		if(currMode == 'edit'){
			sHTML += "<TD align=center><img alt='删除当前设置信息' src=\"../images/wcm52/button_delete_small.gif\" onclick=\"NewWCMRightHelper.removeAt("+i+");CNewWCMRightHelper_notifyRemove(this);\" style=\"cursor:hand;\"></TD>";
		}
		sHTML	+= "</TR>\n";		
	}
	
	//Table End
	sHTML += "</TABLE>";
			
	if(_elDiv == null)
		document.write(sHTML);
	else
		_elDiv.innerHTML = sHTML;
}

function CNewWCMRightHelper_addRightDef(_nIndex, _sName, _sDesc, _sType, _sDespends, _sSimilar){
	var type = _sType.toLowerCase();
	if(!type) return;
	var oItem = {
		Index	: _nIndex,
		Name	: _sName,
		Desc	: _sDesc,
		Type	: type,
		Depends	: _sDespends,
		Similar	: _sSimilar
	};	
	if(!this.arRightDef[type]){
		this.arRightDef[type] = [];
	}
	this.arRightDef[type].push(oItem);
}

function CNewWCMRightHelper_addRight(_nOperatorType, _nOperatorId, _sOperatorName, _sValue, _nRightId){
	var oRight = new CWCMRight(_nOperatorType, _nOperatorId, _sOperatorName, _sValue, _nRightId);
	this.arRight[this.arRight.length] = oRight;
}

function CNewWCMRightHelper_selectAll(_nRightIndex, _sPrefix , _bChecked, currObj){
	//TRSHTMLElement.selectAllByName(_sPrefix+"_"+this.arRight[_nRightIndex].getKey(), _bChecked);
	while(currObj!=null && currObj.tagName.toLowerCase() != "tr"){
		currObj = currObj.parentNode;
	}
	if(currObj == null) return;
	var trArray = currObj.getElementsByTagName("input");
	for (var i = 1; i < trArray.length; i++){
		if(trArray[i].type.toLowerCase() == "checkbox"){
			if(trArray[i].checked != _bChecked){
				trArray[i].checked = _bChecked;
				trArray[i].onclick();
			}
		}
	}
}

function CNewWCMRightHelper_removeAt(_nRightIndex){
	TRSArray.splice(this.arRight, _nRightIndex, 1);
}

function CNewWCMRightHelper_removeAll(){
	if(this.arRight.length == 0)
		return;
	if($confirm("您确认删除全部的设置！", function(){
		$dialog().hide();
		this.arRight.length = 0;
		CNewWCMRightHelper_notifyRemove(null);
	}.bind(this), function(){$dialog().hide();}));
}


function CNewWCMRightHelper_putOperator(_nType, _nId, _sName, _sRightValue){
	for(var i=0; i<this.arRight.length; i++){
		if(this.arRight[i].nOprType == _nType
			&& this.arRight[i].nOprId == _nId){
			return;
		}
	}
	
	this.bUpdate		= true;
	this.addRight(_nType, _nId, _sName, _sRightValue || "0");
}

function CNewWCMRightHelper_getIdArray(_nType){
	var arId = [];
	for(var i=0; i<this.arRight.length; i++){
		if(this.arRight[i].getOperatorType() == _nType)
			arId[arId.length] = this.arRight[i].getOperatorId();
	}
	return arId;
}

function CNewWCMRightHelper_getNameArray(_nType){
	var arName = [];
	for(var i=0; i<this.arRight.length; i++){
		if(this.arRight[i].getOperatorType() == _nType)
			arName[arName.length] = this.arRight[i].sName;
	}
	return arName;
}

function CNewWCMRightHelper_getIndexArray(_nType){
	var arIndex = [];
	for(var i=0; i<this.arRight.length; i++){
		if(this.arRight[i].getOperatorType() == _nType)
			arIndex[arIndex.length] = i;
	}
	return arIndex;
}


function CNewWCMRightHelper_toXMLInfo(){
	var sResult = "<WCMRIGHTS>\n";
	
	for(var i=0; i<this.arRight.length; i++){
		sResult += this.arRight[i].toXML() + "\n";
	}

	sResult += "</WCMRIGHTS>";

	return sResult;
}

function CNewWCMRightHelper_setRightStatus(_nRightNo, _nRightIndexs, _bHasRight){
	if(!isArray(_nRightIndexs)){
		_nRightIndexs = [_nRightIndexs];
	}
	if(this.arRight.length<(_nRightNo+1)){
		CTRSAction_alert("RightNo["+_nRightNo+"]不正确！溢出！");
		return;
	}

	this.arRight[_nRightNo].setRightStatus(_nRightIndexs, _bHasRight);
}

function CNewWCMRightHelper_compare(_oRight1,_oRight2){
	var oRight1 = _oRight1 || null;
	var oRight2 = _oRight2 || null;

	if (oRight1 == null && oRight2 == null) return 0;
	if (oRight1 == null) return -1;
	if (oRight2 == null) return 1;
	
	var result= oRight2.nOprType - oRight1.nOprType;
	if (result != 0) return result>0?1:-1;
	
	if (oRight1.sName > oRight2.sName) return 1;
	if (oRight1.sName < oRight2.sName) return -1;
	
	return 0;
	
	
}

function CNewWCMRightHelper_sortFunction(){
	var temp;
	var len = this.arRight.length;
	var k;

	if (this.Order == 0) this.Order =1;
	else this.Order = 0 - this.Order;
	var nOrder = this.Order;


	for (var i=0; i<(len-1);i++ ){
		
		k=i;
		for (var j = i+1;j < len;j++ ){
			if (this.compare(this.arRight[k],this.arRight[j]) == nOrder)
				k = j;
		}
		if (k!=i){
			temp = this.arRight[i];
			this.arRight[i] = this.arRight[k];
			this.arRight[k] = temp;
		}

	}

}


var NewWCMRightHelper = new CNewWCMRightHelper();

function selectAllCheck(currElement){
	var tableObj = currElement.parentNode.parentNode;
	var checkBoxArray = tableObj.getElementsByTagName("input");
	var needSelectAll = false;
	for (var i = 0; i < checkBoxArray.length; i++){
		var tempCheckBox = checkBoxArray[i];		
		if(tempCheckBox.getAttribute('needDeal') == 1){
			if(!tempCheckBox.checked){
				needSelectAll = true;
				break;
			}
		}
	}
	for (var i = 0; i < checkBoxArray.length; i++){
		var tempCheckBox = checkBoxArray[i];		
		if(tempCheckBox.getAttribute('needDeal') == 1){
			if((needSelectAll && !tempCheckBox.checked) ||
					(!needSelectAll && tempCheckBox.checked)){
				tempCheckBox.checked = needSelectAll;
				tempCheckBox.fireEvent('onclick');
			}
		}else{
			tempCheckBox.checked = needSelectAll;
		}
	}
}