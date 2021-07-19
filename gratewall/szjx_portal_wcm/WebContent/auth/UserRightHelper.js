/*!
 * Class:UserRightHelper 
 * Util for set right by operator.
 *
 * History				Who				What
 * 20060428				WenYehui		created.
 * 20060726				wenyh			bug fix:设置权限时少了一列
 */

function RightDef(name,desc,index){	
	this.name = name;
	if(desc != null && desc.length > 0){
		this.desc = desc;
	}else{
		this.desc = name;
	}
	this.index = index;
}

function Right(objType,objId,objDesc,value,rightId){
	this.objType = objType;
	this.objId = objId;
	this.objDesc = objDesc;
	this.id = rightId || 0;

	this.values = new Array();	
	var nLen = value.length;
	for(var i=nLen; i>0; i--){
		this.values[nLen-i] = value.charAt(i-1);
	}	
	
	this.WCMObj = new CWCMObj();
	this.WCMObj.setProperty("OBJTYPE",this.objType);
	this.WCMObj.setProperty("OBJID",this.objId);
	this.WCMObj.setProperty("RIGHTVALUE",value);
	this.WCMObj.setProperty("RIGHTID",this.id);
}

Right.prototype.hasRight = function(index) {
	if(this.values.length < index+1) return false;
    return (this.values[index] == 1);
}

Right.prototype.getKey = function(){
	return this.objType + "_" + this.objId;
}

Right.prototype.getObjType = function(){
	return this.objType;
}

Right.prototype.getObjId = function(){
	return this.objId;
}

Right.prototype.asXml = function(){
	return WCMObjHelper.toXMLString(this.WCMObj, "WCMRIGHT");
}

Right.prototype.asHtml = function(){
	return '<font color="blue">' + this.objDesc + '</font>';
}

Right.prototype.setRight = function(index,hasRight){
	if(this.values.length < (index+1)){
		this.values.length = (index+1);
		for(var i=0; i<(index+1); i++){
			if(this.values[i] == null || this.values[i].length==0){
				this.values[i] = 0;
			}
		}
	}

	this.values[index] = (hasRight?1:0);
	
	var reversed = [];
	var len = this.values.length;
	for(var i=len-1;i>=0;i--){		
		reversed[len-i] = this.values[i];
	}		
	
	this.WCMObj.setProperty("RIGHTVALUE", reversed.join(""));	
}



function UserRightHelper(){
	this.RightDefs = [];
	this.Rights = [];
	this.bUpdate = false;
	this.Order = 0;
}

UserRightHelper.prototype.addRightDef = function(name,desc,index){	
	var rightdef = new RightDef(name,desc,index);

	if((index+1) > this.RightDefs.length){
		this.RightDefs.length = index+1;
	}

	this.RightDefs[index] = rightdef;	
}

UserRightHelper.prototype.addRight = function(objType,objId,objDesc,value,rightId){	
	var right = new Right(objType,objId,objDesc,value,rightId);
	this.Rights.push(right);	
}

UserRightHelper.prototype.selectAll = function(prefix,index,checked){	
	var eleName = prefix + "_" + this.Rights[index].getKey();	
	TRSHTMLElement.selectAllByName(eleName, checked);
}

UserRightHelper.prototype.removeAt = function(index){	
	TRSArray.splice(this.Rights,index,1);
	this.notifyRemove(null);
}

UserRightHelper.prototype.removeAll = function(){
	if(!CTRSAction_confirm("您确认删除全部的设置！"))	return;

	this.Rights.length = 0;
	this.notifyRemove(null);
}

UserRightHelper.prototype.putResource = function (objType,objId,objDesc){
	var right = null;
	for(var i=0; i<this.Rights.length; i++){
		right = this.Rights[i];
		if(right.objType == objType && right.objId == objId) return;
	}	
	
	this.addRight(objType,objId,objDesc,"0");
	this.bUpdate = true;
}

UserRightHelper.prototype.getIdArray = function(objType){
	var ids = [];
	var right = null;
	for(var i=0; i<this.Rights.length; i++){
		right = this.Rights[i];
		if(right == null) continue;
		if(right.objType == objType) {
			//ids[ids.length] = right.objId;
			ids.push(right.objId);
		}
	}

	return ids;
}

UserRightHelper.prototype.toXmlInfo = function(){
	var xmlInfo = "<WCMRIGHTS>";
	
	for(var i=0; i<this.Rights.length; i++){
		xmlInfo += this.Rights[i].asXml();
	}

	xmlInfo += "</WCMRIGHTS>";

	return xmlInfo;
}

UserRightHelper.prototype.setRight = function(rightNo,rightIndex,hasRight){
	if(this.Rights.length < (rightNo + 1)){
		CTRSAction_alert("RightNo[" + rightNo + "]不正确！溢出！");
		return;
	}
	
	var right = this.Rights[rightNo];
	if(right == null) return;
	
	right.setRight(rightIndex,hasRight);
}

UserRightHelper.prototype.compareRight = function (right1,right2){
	var r1 = right1 || null;
	var r2 = right2 || null;

	if((r1 == null) && (r2 == null)) return 0;
	if(r1 == null) return -1;
	if(r2 == null) return 1;

	var temp = r1.objType - r2.objType;
	if(temp != 0) return temp>0?1:-1;

	if(r1.objDesc > r2.objDesc) return 1;
	if(r1.objDesc < r2.objDesc) return -1;

	return 0;
}

UserRightHelper.prototype.sortRights = function (){
	var len = this.Rights.length;

	var temp,k;
	if(this.Order == 0){
		this.Order = 1;
	}else{
		this.Order = (0 - this.Order);
	}

	var nOrder = this.Order;
	for (var i=0; i<(len-1);i++ ){		
		k = i;
		for (var j = i+1;j < len;j++ ){
			if (this.compareRight(this.Rights[k],this.Rights[j]) == nOrder)
				k = j;
		}

		if (k!=i){
			temp = this.Rights[i];
			this.Rights[i] = this.Rights[k];
			this.Rights[k] = temp;
		}
	}
}

UserRightHelper.prototype.onRightChecked = function(elCheckbox,rightNo,rightIndex){
	this.setRight(rightNo,rightIndex,elCheckbox.checked);
}

UserRightHelper.prototype.selectCol = function(col,rightIndex){	
	var nCellIndex = col.cellIndex;	
	var parentRow = getParentByTagName(col,"TR");
	
	if(parentRow == null) return;//Maybe nerver.
	var nRowIndex = parentRow.rowIndex;

	var parentTable = getParentByTagName(parentRow,"TABLE");
	if(parentTable == null) return;
	
	var bNotAllChecked = !isAllChecked(parentTable,nRowIndex + 1,nCellIndex);
	if(bNotAllChecked == void(0)) return;

	var allRows = parentTable.rows;
	var nRightNo = 0;
	for(var i = nRowIndex + 1; i<allRows.length; i++,nRightNo++){
		var elCell = allRows[i].cells[nCellIndex];
		var inputEls = elCell.getElementsByTagName("INPUT");
		
		inputEls[0].checked = bNotAllChecked;
		
		this.setRight(nRightNo,rightIndex,bNotAllChecked);
		
	}
}

function isAllChecked(_table,_startRowIndex,_cellIndex){
	var allRows = _table.rows;
	for(var i=_startRowIndex; i<allRows.length; i++){
		var elCell = allRows[i].cells[_cellIndex];
		var inputEls = elCell.getElementsByTagName("INPUT");
		if(inputEls == null || inputEls.length == 0){
			CTRSAction_alert("数据有误："+oCell.outerHTML);
			return void(0);
		}

		if(!inputEls[0].checked) return false;
	}

	return true;
}

function getParentByTagName(ele,parentTagName){
	if(ele == null) return null;

	var elParent = ele.parentElement;
	if(elParent == null) return null;
	
	var elTagName = elParent.tagName;

	if(elTagName == null || elTagName.toLowerCase() == "body") return null;

	if(elTagName == parentTagName) return elParent;

	return getParentByTagName(elParent,parentTagName);
}

UserRightHelper.prototype.notifyRemove = function(eleOnEvent){
	//Need to override.
}

UserRightHelper.prototype.refreshRights = function (_nObjType,_newRights){
	var right = null;
	for(var i=this.Rights.length -1 ; i>=0; i--){
		right = this.Rights[i];
		if(right == null || right.objType != _nObjType) continue;
		if(!_newRights.contains(right.objId)){
			TRSArray.splice(this.Rights,i,1);
			this.bUpdate = true;
		}
	}
}

Array.prototype.contains = function (el){
	for(var i=0; i<this.length; i++){
		if(this[i] == el) return true;
	}

	return false;
}

UserRightHelper.prototype.draw = function(startRihtDefIndex,endRihtDefIndex,divId){	
	var sHtml = '<TABLE width="98%" border="0" cellpadding="0" cellspacing="1" bgcolor="A6A6A6">'
				+	'<TR bgcolor="#BEE2FF" class="list_th">'			
				+		'<TD bgcolor="#BEE2FF">全选</TD>'
				+		'<TD bgcolor="#BEE2FF"><A href="#" onClick="RightSetHelper.sortRights();init();">被设置者';

	if (this.Order == 1) sHtml += "▲";
	if (this.Order == -1) sHtml += "▼";	

	sHtml += "</A></TD>";	

	var rightDefs= [];	
	
	//Table Head
	var rightDef = null;
	for(var i=startRihtDefIndex; i<=endRihtDefIndex && i<this.RightDefs.length; i++){
		rightDef = this.RightDefs[i];
		if(rightDef == null) continue;

		sHtml += '<TD bgcolor="#BEE2FF" title="' + rightDef.desc + '[点击后可以选择一列]" '
				+ 'onclick="RightSetHelper.selectCol(this,' + rightDef.index + ')" '
				+ 'style="cursor:hand">' + rightDef.name + '</TD>';
		
		rightDefs.push(rightDef);
	}
	
	sHtml += '<TD bgcolor="#BEE2FF">删除</TD>';
	sHtml += "</TR>";

	//Table Body
	var right = null;	
	var sbgcolor = "";
	var count = 0;//行号的显示
	for(var i=0; i<this.Rights.length; i++){
		right = this.Rights[i];
		if(right == null) continue;
		if(("id_TRSSimpleTab0" == divId) && (right.objType != 103)) continue;

		//sbgcolor  = ((i+1)%2)==1?"white":"F5F5F5";
		
		sbgcolor  = ((count+1)%2)==1?"white":"F5F5F5";
		
		sHtml += '<TR style="background-color:' + sbgcolor + ';font-size:9pt;">'
				+ '<TD align="center">'
				+ '<INPUT type="checkbox" onclick="RightSetHelper.selectAll('+endRihtDefIndex+','+i+',this.checked)" title="选中一行">'
				+  (count+1) + '</TD>'
				+ '<TD align="center">'+right.asHtml()+'</TD>';

		count++;

		//all 
		for(var j=0;j<rightDefs.length;j++){
			var tempRightDef = rightDefs[j];
			sHtml += '<TD align="center" title="'+tempRightDef.desc+'">'
					+ '<INPUT type="checkbox" onclick="RightSetHelper.onRightChecked(this,'+i+','+tempRightDef.index+')" '
					+	'value="'+tempRightDef.index+'" name="'+endRihtDefIndex+'_'+right.getKey()+'" '
					+	(right.hasRight(tempRightDef.index)?"checked":"")
					+'></TD>';
		}

		//
		sHtml += '<TD align=center><img alt="删除当前设置信息" src="../images/button_delete_small.gif" onclick="RightSetHelper.removeAt('+i+');" style="cursor:hand;"></TD>';
		sHtml += '</TR>';
		
	}
	
	sHtml += '</TABLE>';
	
	var elDiv = document.getElementById(divId);
	if(elDiv == null){
		elDiv = document.createElement("div");
		elDiv.id = divId;
		elDiv.innerHTML = sHtml;
		document.body.appendChild(elDiv);		
	}else{		
		elDiv.innerHTML = sHtml;
	}	
}

var RightSetHelper = new UserRightHelper();
