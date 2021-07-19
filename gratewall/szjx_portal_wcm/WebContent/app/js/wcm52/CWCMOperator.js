var TYPE_WCMOBJ_USER	= 204;
var TYPE_WCMOBJ_GROUP	= 201;
var TYPE_WCMOBJ_ROLE	= 203;

var PARENT_TABLE_NAME = "tbOperators";

function CWCMOperator_getObjType(){
	return this.ObjType;
}

function CWCMOperator_setObjType(_nType){
	if(_nType == null || _nType <= 0){
		CTRSAction_alert("传入无效的对象类型[CWCMOperator_setObjType]！");
		return;
	}
	this.ObjType = _nType;
}

function CWCMOperator_getObjId(){
	return this.ObjId;
}

function CWCMOperator_setObjId(_nId){
	if(_nId == null || _nId <= 0){
		CTRSAction_alert("传入无效的对象ID[CWCMOperator_setObjId]！");
		return;
	}
	this.ObjId = _nId;
}

function CWCMOperator_getObjName(){
	return this.ObjName;
}

function CWCMOperator_setObjName(_sName){
	if(_sName == null){
		CTRSAction_alert("传入无效的对象名称[CWCMOperator_setObjName]！");
		return;
	}
	this.ObjName = _sName;
}

function CWCMOperator_getObjKey(){
	return this.ObjKey;
}

function CWCMOperator_setObjKey(){
	this.ObjKey = this.ObjType.toString() + this.ObjId.toString();
}

function CWCMOperator(_nType, _nId, _sName){
	this.ObjType;
	this.ObjId;
	this.ObjName;
	this.ObjKey;

	this.getType = CWCMOperator_getObjType;
	this.setType = CWCMOperator_setObjType;
	this.getId = CWCMOperator_getObjId;
	this.setId = CWCMOperator_setObjId;
	this.getName = CWCMOperator_getObjName;
	this.setName = CWCMOperator_setObjName;
	this.getKey = CWCMOperator_getObjKey;
	this.setKey = CWCMOperator_setObjKey;

	/* Fields Initialize start */
	this.setType(_nType);
	this.setId(_nId);
	if(_sName != null)
		this.setName(_sName);
	this.setKey();
	/* Fields Initialize end */
} 

function CWCMOperatorHelper_getObjTitle(){
	switch(this.currOperator.ObjType){
		case TYPE_WCMOBJ_USER:
			return "<IMG src=\"../images/icon/icon_user.gif\" width=\"16\" height=\"16\">[<font color=blue>"+this.currOperator.ObjName+"</font>]";
		case TYPE_WCMOBJ_GROUP:
			return "<IMG src=\"../images/icon/icon_group.gif\" width=\"16\" height=\"16\">[<font color=blue>"+this.currOperator.ObjName+"</font>]";
		case TYPE_WCMOBJ_ROLE:
			return "<IMG src=\"../images/icon/icon_role.gif\" width=\"16\" height=\"16\">[<font color=blue>"+this.currOperator.ObjName+"</font>]";
		default:
			return "未知"+"[<font color=blue>"+this.currOperator.ObjName+"</font>]";
	}
}

function CWCMOperatorHelper_addNode(){
	var oTR = this.oParentTable.insertRow(-1);
	oTR.id = this.currOperator.ObjKey;
	oTR.name = this.currOperator.ObjKey;
	oTR.obj_type = this.currOperator.ObjType;
	oTR.obj_id = this.currOperator.ObjId;
	oTR.obj_name = this.currOperator.ObjName;
	oTR.className = "list_tr";

	oTD = oTR.insertCell(0);
	oTD.align = "left";
	oTD.innerHTML = this.getTitle();

	oTD = oTR.insertCell(1);
	oTD.align = "center";
	oTD.innerHTML = "<A onclick=\"WCMOperatorHelper.removeOperator("+this.currOperator.ObjType+", "+this.currOperator.ObjId+");WCMOperatorHelper.notifyTop("+this.currOperator.ObjType+", "+this.currOperator.ObjId+")\" href=\"#\"><IMG src=\"../images/wcm52/button_delete.gif\" border=\"0\" width=\"16\" height=\"16\"></A>";
}

function CWCMOperatorHelper_removeOperator(_nType, _nId, _sName){
	this.currOperator = new CWCMOperator(_nType, _nId, _sName);
	if(this.currOperator == null){
		CTRSAction_alert("无效的Operator对象！[CWCMOperatorHelper_removeOperator]");
		return;
	}

	var i = this.indexOf(this.currOperator.getKey());
	if(i < 0){
		return;
	}

	var oNode = document.getElementById(this.currOperator.getKey());
	this.oParentTable.deleteRow(oNode.rowIndex);

	TRSArray.splice(this.arKey, i, 1);
	TRSArray.splice(this.arObject, i, 1);

	if(!window.parent.removeId){
		CTRSAction_alert("顶级窗口必须定义函数[removeId]以维持数据同步！");
		return;
	}
	window.parent.removeId(_nType, _nId);

	this.arrayLength -= 1;
}

function CWCMOperatorHelper_insert(_nType, _nId, _sName){
	this.currOperator = new CWCMOperator(_nType, _nId, _sName);
	if(this.currOperator == null){
		CTRSAction_alert("无效的Operator对象！[CWCMOperatorHelper_insert]");
		return;
	}
	var i = this.indexOf(this.currOperator.getKey());
	if(i >= 0){
		CTRSAction_alert("对象已存在！[CWCMOperatorHelper_insert]");
		return;
	}
	this.arKey[this.arrayLength] = this.currOperator.getKey();
	this.arObject[this.arrayLength] = this.currOperator;
	this.arrayLength += 1;
	this.addNode();
	if(!window.parent.addId){
		CTRSAction_alert("顶级窗口必须定义函数[addId]以维持数据同步！");
		return;
	}
	window.parent.addId(_nType, _nId);
}

function CWCMOperator_indexOf(_ObjKey){
	for(var i=0; i<this.arrayLength; i++){
		if(this.arKey[i] == _ObjKey){
			return i;
		}
	}

	return -1;
}

function CWCMOperator_getOperatorArray(_nType){
	var arOperators = new Array();
	var n = 0;

	for(var i=0; i<this.arrayLength; i++){
		if(this.arObject[i].getType() == _nType){
			arOperators[n] = this.arObject[i];
			n++;
		}
	}

	return arOperators;
}

function CWCMOperator_getUsers(){
	return this.getOperatorArray(TYPE_WCMOBJ_USER);
}

function CWCMOperator_getGroups(){
	return this.getOperatorArray(TYPE_WCMOBJ_GROUP);
}

function CWCMOperator_getRoles(){
	return this.getOperatorArray(TYPE_WCMOBJ_ROLE);
}

function CWCMOperator_getOperatorIds(_ObjKey){
	var arOperators = this.getOperatorArray(_ObjKey);
	if(arOperators == null || arOperators.length <= 0){
		return "";
	}

	var sIds = "";
	for(var i=0; i<arOperators.length; i++){
		sIds += arOperators[i].getId() + ",";
	}

	return sIds.substring(0, (sIds.length-1));
}

function CWCMOperator_getOperatorNames(_ObjKey){
	var arOperators = this.getOperatorArray(_ObjKey);
	if(arOperators == null || arOperators.length <= 0){
		return "";
	}

	var sNames = "";
	for(var i=0; i<arOperators.length; i++){
		sNames += arOperators[i].getName() + ",";
	}

	return sNames.substring(0, (sNames.length-1));
}

function CWCMOperator_getUserIds(){
	return this.getOperatorIds(TYPE_WCMOBJ_USER);
}

function CWCMOperator_getUserNames(){
	return this.getOperatorNames(TYPE_WCMOBJ_USER);
}

function CWCMOperator_getGroupIds(){
	return this.getOperatorIds(TYPE_WCMOBJ_GROUP);
}

function CWCMOperator_getGroupNames(){
	return this.getOperatorNames(TYPE_WCMOBJ_GROUP);
}

function CWCMOperator_getRoleIds(){
	return this.getOperatorIds(TYPE_WCMOBJ_ROLE);
}

function CWCMOperator_getRoleNames(){
	return this.getOperatorNames(TYPE_WCMOBJ_ROLE);
}

function CWCMOperator_notifyTop(_nType, _nId){
	window.parent.notify(_nType, _nId);
}

function CWCMOperatorHelper(){
	this.currOperator;
	this.arrayLength = 0;
	this.arKey = new Array();
	this.arObject = new Array();
	this.arOperators = new Array();
	this.arOperators[0] = this.arKey;
	this.arOperators[1] = this.arObject;
	this.oParentTable = document.getElementById(PARENT_TABLE_NAME);
	if(this.oParentTable == null){
		this.oParentTable = document.createElement("TABLE");
		this.oParentTable.id = PARENT_TABLE_NAME;
		this.oParentTable.name = PARENT_TABLE_NAME;
		document.body.appendChild(this.oParentTable);
	}

	this.getTitle = CWCMOperatorHelper_getObjTitle;
	this.insert = CWCMOperatorHelper_insert;
	this.addNode = CWCMOperatorHelper_addNode;
	this.removeOperator = CWCMOperatorHelper_removeOperator;
	this.indexOf = CWCMOperator_indexOf;
	this.getOperatorArray = CWCMOperator_getOperatorArray;
	this.getUsers = CWCMOperator_getUsers;
	this.getGroups = CWCMOperator_getGroups;
	this.getRoles = CWCMOperator_getRoles;
	this.getOperatorIds = CWCMOperator_getOperatorIds;
	this.getOperatorNames = CWCMOperator_getOperatorNames;
	this.getUserIds = CWCMOperator_getUserIds;
	this.getUserNames = CWCMOperator_getUserNames;
	this.getGroupIds = CWCMOperator_getGroupIds;
	this.getGroupNames = CWCMOperator_getGroupNames;
	this.getRoleIds = CWCMOperator_getRoleIds;
	this.getRoleNames = CWCMOperator_getRoleNames;
	this.notifyTop = CWCMOperator_notifyTop;
}

var WCMOperatorHelper = new CWCMOperatorHelper();