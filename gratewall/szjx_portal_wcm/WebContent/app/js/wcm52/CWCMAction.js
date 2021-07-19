/** 
 * @fileoverview 
 * 此文件定义了CWCMAction对象，完成WCM新增和修改页面中表单的提交<BR>
 *  Copyright: 		www.trs.com.cn<BR>
 *  Company: 		TRS Info. Ltd.<BR>
 *  Author:			CH<BR>
 *  Created:		2004-11-24 08:38<BR>
 *  Vesion:			1.0<BR>
 *  Last EditTime:	2004-11-24/2004-11-24<BR>
 *	Update Logs:<BR>
 *		CH@2004-11-24 Created File<BR>
 *	Note:<BR>
 *		<BR>
 *	Depends:<BR>
 *		CTRSAction.js<BR>
 *		CWCMObj.js<BR>
 *		CWCMObjHelper.js
 *<BR>
 *	Examples:<BR>
 *		../test/CWCMAction_test.html<BR>
 * @author CH cao.hui@trs.com.cn
 * @version 1.o
 */

/**
 * CWCMAction构造函数
 * @class CWCMAction类
 * @constructor
 * @return CWCMAction的一个实例
 */
function CWCMAction(){
//Define Methods
	this.isValid			= CWCMAction_isValid;
	this.doPost				= CWCMAction_doPost;
	this.createActionForm	= CWCMAction_createActionForm;
	this.getDowithURL		= CWCMAction_getDowithURL;
	this.bIsDebug			= false;
}

/**
 * 校验并提交指定的表单中的数据，提交的目标为相应的dowith页面
 * @param {Form} _elDataForm 指定的数据表单对象（例如文档采集页面的Name为frmData的Form对象）
 * @param {Form} _elActionForm 指定的Action表单对象
 */
function CWCMAction_doPost(_elDataForm, _elActionForm, _bNotDoValidate){
	//Validate Form
	if(!_bNotDoValidate && !TRSValidator.validate(_elDataForm)){
		return false;
	}

	//Constructor Form Data
	var arEls = _elDataForm.elements;//get All data from the form
	var WCMObj = new CWCMObj(), sElName, sValue, elTemp;	
	var oAttribute = new Object();
	for(var i = 0;i<arEls.length;i++){			
		if(!this.isValid(arEls[i]))continue;		

		sElName	= arEls[i].name;	
		if(isUndefined(sElName) || sElName==null || sElName.length==0)continue;

		if(!isUndefined(arEls[i].BitIndex)){
			var bValue;
			if(arEls[i].type == "checkbox" || arEls[i].type == "radio"){
				bValue = arEls[i].checked;
			}else{
				bValue = arElsp[i].value;
			}

			WCMObj.setBitValue(sElName, arEls[i].BitIndex, bValue);
		}
		else if(!isUndefined(arEls[i].IsAttr) && !isUndefined(arEls[i].FieldName)){//Attribute类型的属性
			WCMObj.setAttribute(arEls[i].FieldName, sElName, CWCMAction_getValue(_elDataForm, sElName));
		}
		else if(arEls[i].type == "checkbox" && arEls[i].IsBoolean){
			WCMObj.setProperty(sElName, (arEls[i].checked?"1":"0"));		
		}
		else{//一般属性
			WCMObj.setProperty(sElName, CWCMAction_getValue(_elDataForm, sElName));		
		}
	}

	//Save Form Data To ActionForm And Submit Action
	if(_elActionForm == null){//Create Action Form
		_elActionForm = this.createActionForm();		
	}
	var sXML = WCMObjHelper.toXMLString(WCMObj,"WCMOBJ");
	_elActionForm.ObjectXML.value = sXML;	

	//Debug
	if(this.bIsDebug){
		CTRSAction_alert("ActionForm Data Infor:");
		arEls = _elActionForm.elements;
		for(var i = 0; i<arEls.length; i++){			
			sElName	= arEls[i].name;	
			if(isUndefined(sElName) || sElName==null || sElName.length==0)continue;

			CTRSAction_alert(sElName + ":" + CWCMAction_getValue(_elActionForm, sElName));
		}
	}

	//Display Process Bar
	if(!_elActionForm.NotDisplayRunningBar)
		RunningProcessBar.start();

	//Submit
	_elActionForm.submit();
	

	//Return，Set Data Form Submit InValid
	return false;
}


/**
 * @private
 */
function CWCMAction_getDowithURL(){
	var sURL		= window.location.href;
	if(sURL.indexOf("?")>0){
		sURL = sURL.replace(/.jsp?/, "_dowith.jsp?");
	}else{
		sURL = sURL.replace(/.jsp/, "_dowith.jsp");
	}
	return sURL;	
}

/**
 * @private
 */
function CWCMAction_createActionForm(){
	
	var sHTML = ""
			+"<form name=\"frmAction\" method=\"post\" action=\""+this.getDowithURL()+"\">" 
			+"	<input type=hidden name=\"ObjectId\">" 
			+"	<input type=hidden name=\"ObjectXML\">" 
			+"</form>";

	document.body.insertAdjacentHTML("afterBegin", sHTML);
	return document.frmAction;
}

/**
 * @private
 */
function CWCMAction_getValue(_elDataForm, _sElName){
	var elTemp	= eval("_elDataForm."+_sElName);
	if(!isUndefined(elTemp.tagName)){
		if((elTemp.type == "radio" || elTemp.type == "checkbox") && !elTemp.checked)
			return "";

		return elTemp.value;
	}
	
	var sValue = "", elItem;	
	for(var i=0; i<elTemp.length; i++){
		elItem = elTemp[i];
		
		if((elItem.type == "checkbox" && elItem.checked)
			|| elItem.type == "text"){
			
			if(sValue == ""){
				sValue = elItem.value;
			}else{
				sValue += ","+elItem.value;
			}
		}
		else if(elItem.type == "radio" && elItem.checked){
			return elItem.value;
		}		
	}	
	return sValue;
}


/**
 * @private
 */
function CWCMAction_isValid(_elElement){
	if(_elElement.type 
		&& (_elElement.type == "submit" 
		|| _elElement.type == "reset"
		|| _elElement.type == "button"
		|| _elElement.ignore) ){
			return false;
	}

	return true;
}

/**
 * @private
 */
var m_oActionForm = null;

var WCMAction = new CWCMAction();

document.write("<" + "script src=\"../js/wcm52/CRunningProcessBar.js\"><" + "/script>");