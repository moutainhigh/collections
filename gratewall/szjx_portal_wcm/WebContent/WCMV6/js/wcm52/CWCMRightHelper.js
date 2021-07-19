var TYPE_WCMOBJ_USER	= 204;
var TYPE_WCMOBJ_GROUP	= 201;
var TYPE_WCMOBJ_ROLE	= 203;

var WCMOBJ_PROPERTIES_NODE_NAME = "PROPERTIES";

function CWCMRightHelper(){
//定义属性
	this.arRightDef	= [];	
	this.arRight		= [];
	this.bUpdate		= false;
	this.Order			= 0;

//定义方法
	this.draw			= CWCMRightHelper_draw; //生成权限的交互页面
	this.addRightDef	= CWCMRightHelper_addRightDef; //添加权限的定义
	this.addRight		= CWCMRightHelper_addRight; //添加权限
	this.selectAll		= CWCMRightHelper_selectAll;
	this.removeAt		= CWCMRightHelper_removeAt;
	this.removeAll		= CWCMRightHelper_removeAll;
	this.putOperator	= CWCMRightHelper_putOperator;

	this.getIdArray		= CWCMRightHelper_getIdArray;
	this.getNameArray	= CWCMRightHelper_getNameArray;
	this.getIndexArray	= CWCMRightHelper_getIndexArray;
	this.toXMLInfo		= CWCMRightHelper_toXMLInfo;

	this.setRightStatus	= CWCMRightHelper_setRightStatus;
	this.compare		= CWCMRightHelper_compare;
	this.sort			= CWCMRightHelper_sortFunction;
	this.selectCol		= CWCMRightHelper_selectCol;
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

function CWCMRightHelper_getColSelectAllStatus(_oParentTable, _nBeginRowIndex, _nCellIndex){
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

function CWCMRightHelper_selectCol(_elEventTD, _nRightIndex){
	var nCellIndex		= _elEventTD.cellIndex;
	var oParentTR		= getParentElementByTagName(_elEventTD, "TR");
	var nTRIndex		= oParentTR.rowIndex;
	var oParentTable	= getParentElementByTagName(oParentTR, "TABLE");
	
	var bChecked		= CWCMRightHelper_getColSelectAllStatus(oParentTable, nTRIndex+1, nCellIndex);
	var oRows			= oParentTable.rows;
	var nRightNo		= 0;
	for(var i=nTRIndex+1; i<oRows.length; i++, nRightNo++){
		var oCell		= oRows[i].cells[nCellIndex];
		var arEls		= oCell.getElementsByTagName("INPUT");
		if(arEls == null || arEls.length<=0){
			CTRSAction_alert("数据有误："+oCell.outerHTML);
			return;
		}

		arEls[0].checked = bChecked;

		WCMRightHelper.setRightStatus(nRightNo, _nRightIndex, arEls[0].checked);
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



function CWCMRight_setRightStatus(_nRightIndex, _bHasRight){
	if(this.arValue.length<(_nRightIndex+1)){
		this.arValue.length = (_nRightIndex+1);
		for(var i=0; i<(_nRightIndex+1); i++){
			if(this.arValue[i] == null || this.arValue[i].length==0){
				this.arValue[i] = 0;
			}
		}
	}
	this.arValue[_nRightIndex] = (_bHasRight?1:0);

	var str = "";
	for(var i=(this.arValue.length-1); i>=0; i--){
		str += this.arValue[i];
	}

	this.oWCMObj.setProperty("RIGHTVALUE", str);
}


function CWCMRightHelper_notifyRemove(_elEvent){
}

function CWCMRightHelper_onClickRightCheck(_elEvent, _nRightNo, _nRightIndex){
	WCMRightHelper.setRightStatus(_nRightNo, _nRightIndex, _elEvent.checked);
}

function CWCMRightHelper_draw(_nStartRightDefIndex, _nEndRightDefIndex, _elDiv,_sExtraIndexes,_bReadMode){
	var sHTML = ""
			+"<TABLE width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" bgcolor=\"A6A6A6\">" 
			+"							<TR class=\"grid_head\" align='center'>"			
			+((currMode == 'edit') ? "<TD bgcolor=\"#BEE2FF\" onclick='selectAllCheck(this)' style='cursor:pointer;'>全选</TD>" : "<TD bgcolor=\"#BEE2FF\">序号</TD>")
			+"<TD bgcolor=\"#BEE2FF\">类型</TD>"
			+"<TD bgcolor=\"#BEE2FF\"><A href=\"#\" onClick=\"WCMRightHelper.sort();init();return false;\">名称";

		if (this.Order == 1) sHTML +="▲";
		if (this.Order == -1) sHTML +="▼";
			
		sHTML += "</A></TD>";

	
	//按原顺序显示设置者，并且排序方式不变
	var nOrder = this.Order;
		
	this.Order = 0- this.Order;
	this.sort();
	this.Order = nOrder;

	var extraIndexes = new Array();
	var arrayofIndexes = new Array();
	var sExtraIndexes= _sExtraIndexes||"";

	if(sExtraIndexes !=""){	
		extraIndexes = sExtraIndexes.split(",")
	}
	

	//Table Head
	for(var i=_nStartRightDefIndex; i<this.arRightDef.length && i<=_nEndRightDefIndex; i++){
		if(this.arRightDef[i] == null)continue;

		if(currMode == 'edit'){
			sHTML += "<TD bgcolor=\"#BEE2FF\" title=\""+this.arRightDef[i]["Desc"]+"[点击后可以选择一列]\" "
					+"onclick=\"WCMRightHelper.selectCol(this, "+this.arRightDef[i]["Index"]+");\" style=\"cursor:hand\">"
					+ this.arRightDef[i]["Name"] + "</TD>";
		}else{
			sHTML += "<TD bgcolor=\"#BEE2FF\">" + this.arRightDef[i]["Name"] + "</TD>";		
		}
		arrayofIndexes.push(i);
	}

	//extraright
	var currIndex;
	for(var i=0; i < extraIndexes.length; i++){
		currIndex=parseInt(extraIndexes[i]);
		if(isNaN(currIndex)||(currIndex >= _nStartRightDefIndex && currIndex <= _nEndRightDefIndex)||currIndex >= this.arRightDef.length) continue;
		if(this.arRightDef[currIndex] == null)continue;
		//alert(currIndex);

		if(currMode == 'edit'){
			sHTML += "<TD bgcolor=\"#BEE2FF\" title=\""+this.arRightDef[currIndex]["Desc"]+"[点击后可以选择一列]\" "
					+"onclick=\"WCMRightHelper.selectCol(this);\" style=\"cursor:hand\">"
					+ this.arRightDef[currIndex]["Name"] + "</TD>";
		}else{
			sHTML += "<TD bgcolor=\"#BEE2FF\>" + this.arRightDef[currIndex]["Name"] + "</TD>";		
		}
		arrayofIndexes.push(currIndex);
	
	}
	if(currMode == 'edit')
		sHTML += "<TD bgcolor=\"#BEE2FF\">删除</TD>\n"
			
	sHTML += "</TR>";


	//Table Body
	var oRight = null, oRightDef, sBgColor;
	for(var i=0; i<this.arRight.length; i++){
		oRight = this.arRight[i];
		rowClass = "grid_row " + (((i+1)%2)==1 ? "grid_row_odd" : "grid_row_even");
		if(currMode == 'edit'){
			sHTML	+= "<TR style=\"font-size:9pt;\" class=\"" + rowClass + "\">\n"
					+"<TD align=center>" + (i+1) + "<input type=checkbox onclick=\"WCMRightHelper.selectAll("+i+","+_nEndRightDefIndex+", this.checked)\">"
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
		for(var j=0; j<arrayofIndexes.length; j++){
			oRightDef = this.arRightDef[arrayofIndexes[j]];
			if(oRightDef == null)continue;

			nRightIndex = oRightDef["Index"];
			if(currMode == 'edit'){
				sHTML += "<TD  align=center title=\""+oRightDef["Desc"]+"\">"
						+ "<input type=checkbox needDeal='1' onClick=\"CWCMRightHelper_onClickRightCheck(this, "+i+", "+nRightIndex+")\" value=\""+nRightIndex+"\" name=\""+_nEndRightDefIndex+"_"+oRight.getKey()+"\" "+(oRight.isHasRight(nRightIndex)?"checked":"")+">" + "</TD>";
			}else{
				sHTML += "<TD  align=center title=\""+oRightDef["Desc"]+"\">"
						+ "<img src='../images/auth/" + (oRight.isHasRight(nRightIndex) ? "hasright.gif" : "noright.gif") + "'>" + "</TD>";
			
			}
		}
		if(currMode == 'edit'){
			sHTML += "<TD align=center><img alt='删除当前设置信息' src=\"../images/wcm52/button_delete_small.gif\" onclick=\"WCMRightHelper.removeAt("+i+");CWCMRightHelper_notifyRemove(this);\" style=\"cursor:hand;\"></TD>";
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

function CWCMRightHelper_addRightDef(_nIndex, _sName, _sDesc){
	var oItem = {};
	oItem["Index"] = _nIndex;
	oItem["Name"]	= _sName;
	oItem["Desc"]	= _sDesc;

	if((_nIndex+1) > this.arRightDef.length)
		this.arRightDef.length = (_nIndex+1);

	this.arRightDef[_nIndex] = oItem;
}

function CWCMRightHelper_addRight(_nOperatorType, _nOperatorId, _sOperatorName, _sValue, _nRightId){
	var oRight = new CWCMRight(_nOperatorType, _nOperatorId, _sOperatorName, _sValue, _nRightId);
	this.arRight[this.arRight.length] = oRight;
}

function CWCMRightHelper_selectAll(_nRightIndex, _sPrefix , _bChecked){
	TRSHTMLElement.selectAllByName(_sPrefix+"_"+this.arRight[_nRightIndex].getKey(), _bChecked);
}

function CWCMRightHelper_removeAt(_nRightIndex){
	TRSArray.splice(this.arRight, _nRightIndex, 1);
}

function CWCMRightHelper_removeAll(){
	if(this.arRight.length == 0)
		return;
	if($confirm("您确认删除全部的设置！", function(){
		$dialog().hide();
		this.arRight.length = 0;
		CWCMRightHelper_notifyRemove(null);
	}.bind(this)));
}


function CWCMRightHelper_putOperator(_nType, _nId, _sName){
	for(var i=0; i<this.arRight.length; i++){
		if(this.arRight[i].nOprType == _nType
			&& this.arRight[i].nOprId == _nId){
			return;
		}
	}
	
	this.bUpdate		= true;
	this.addRight(_nType, _nId, _sName, "0");
}

function CWCMRightHelper_getIdArray(_nType){
	var arId = [];
	for(var i=0; i<this.arRight.length; i++){
		if(this.arRight[i].getOperatorType() == _nType)
			arId[arId.length] = this.arRight[i].getOperatorId();
	}
	return arId;
}

function CWCMRightHelper_getNameArray(_nType){
	var arName = [];
	for(var i=0; i<this.arRight.length; i++){
		if(this.arRight[i].getOperatorType() == _nType)
			arName[arName.length] = this.arRight[i].sName;
	}
	return arName;
}

function CWCMRightHelper_getIndexArray(_nType){
	var arIndex = [];
	for(var i=0; i<this.arRight.length; i++){
		if(this.arRight[i].getOperatorType() == _nType)
			arIndex[arIndex.length] = i;
	}
	return arIndex;
}


function CWCMRightHelper_toXMLInfo(){
	var sResult = "<WCMRIGHTS>\n";
	
	for(var i=0; i<this.arRight.length; i++){
		sResult += this.arRight[i].toXML() + "\n";
	}

	sResult += "</WCMRIGHTS>";

	return sResult;
}

function CWCMRightHelper_setRightStatus(_nRightNo, _nRightIndex, _bHasRight){
	if(this.arRight.length<(_nRightNo+1)){
		CTRSAction_alert("RightNo["+_nRightNo+"]不正确！溢出！");
		return;
	}

	this.arRight[_nRightNo].setRightStatus(_nRightIndex, _bHasRight);
}

function CWCMRightHelper_compare(_oRight1,_oRight2){
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

function CWCMRightHelper_sortFunction(){
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


var WCMRightHelper = new CWCMRightHelper();

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