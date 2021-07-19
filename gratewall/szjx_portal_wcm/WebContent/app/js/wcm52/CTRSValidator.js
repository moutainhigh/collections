/** Title:			CTRSValidator.js
 *  Description:
 *		Controller of TRSVlidators
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WSW
 *  Created:		2004-11-23
 *  Vesion:			1.0
 *  Last EditTime:	2006-02-23
 *	Update Logs:
 *		WSW@2004-11-23 Created File
 *		wenyh@2006-02-23,修正可能存在的el.focus()错误.
 *	Note:
 *		
 *
 *	Depends:
 *		TRSBase.js
 *		CTRSString.js
 *
 */


function CTRSValidator_validate(_oForm){
	
	this.sErrorInfo = "";
	var thisForm = _oForm;
	if(thisForm == null){
		thisForm = window.event.srcElement;
	}
	if(thisForm==null)return false;

	var arEls = thisForm.elements;//get All data from the form
	var oValidator = null;//predefined validator object
	var bValid = true;//is Valid

	for(var i = 0;i<arEls.length;i++){			
		if(!arEls[i].pattern)
			continue;//Next Element

		
		oValidator = this.getValidator(arEls[i]);
		if(oValidator == null)
			continue;

		if(!oValidator.validate()){
			if(oValidator.notDisplayErrorInfo){//不显示错误提示页面
				return false;
			}

			var oInfo = CTRSValidator_convertErrorInfo(oValidator.sErrorInfo);
			CTRSAction_displyReports(oInfo.Width, oInfo.Height, oInfo.InfoHTML);
			if(arEls[i].select)arEls[i].select();
			//wenyh@20060323,the focus() method may cause an error if the el is disabled.
			try{
				if(!arEls[i].disabled && arEls[i].focus)arEls[i].focus();
			}catch(e){
			}
			return false;
			//bValid = false;
			//var bHasCarrigeReturn = (oValidator.sErrorInfo.substring(0, 1) == "\n");
			//this.sErrorInfo += ((this.sErrorInfo.length>1 && !bHasCarrigeReturn)?"\n\n":"") + oValidator.sErrorInfo;
		}
	}
//	if(!bValid){
//		CTRSAction_alert(this.sErrorInfo);//display error info
//		return bValid;
//}
	return bValid;
}

function CTRSValidator_convertErrorInfo(_sErrorInfo){
	var MAX_LENGTH = 17;

	var nCarrigeCount	= 2;
	var nMaxLength		= MAX_LENGTH;
	var nNextPos		= _sErrorInfo.indexOf("\n");
	var nPrePos			= 0;
	while(nNextPos>=0){
		if((nNextPos-nPrePos)>nMaxLength){
			nMaxLength = (nNextPos-nPrePos);
		}
		nPrePos		= nNextPos;
		nNextPos	= _sErrorInfo.indexOf("\n", nNextPos+1);
		nCarrigeCount++;
	}	
	if((_sErrorInfo.length-nPrePos) > nMaxLength){
		nMaxLength = _sErrorInfo.length;
	}
	

	var nWidth = 300;
	if(nMaxLength>MAX_LENGTH){
		nWidth += (nMaxLength-MAX_LENGTH)*3;
	}
	var nHeight = 200;
	nHeight += 20*nCarrigeCount;

	var oInfo = new Object();
	oInfo.CarrigeCount	= nCarrigeCount;
	oInfo.MaxLength		= nMaxLength;
	oInfo.Width			= nWidth;
	oInfo.Height		= nHeight;
	oInfo.InfoHTML		= _sErrorInfo.replace(/\n/g, "<BR>");

	return oInfo;
}


function CTRSValidator_addValidators(_sPattern, _sObjectName, _sSrcFile){
	if(_sObjectName == null || _sObjectName.length == 0)return;
	if(_sPattern == null	|| _sPattern.length == 0)return;
	if(_sSrcFile == null	|| _sSrcFile.length == 0)return;
	
	_sPattern = _sPattern.toLowerCase();

	this.TRSValidators[_sPattern] = _sObjectName;
	this.TRSValidatorFiles[_sPattern] = _sSrcFile;	
	document.write("<script src=\""+_sSrcFile+"\"></script>");
}

function CTRSValidator_getValidator(_oElement){	
	var sPattern = _oElement.pattern.toLowerCase();
	

	var oValidator = this.TRSValidators[sPattern];
	if(oValidator == null)
		return null;

	if(typeof(oValidator) != "string"){
		oValidator.setElement(_oElement);
		return oValidator;
	}

	try{
		oValidator = eval("new "+oValidator+"();");	
		oValidator.setElement(_oElement);
	}catch(e){
		CTRSAction_alert(e);
		CTRSAction_alert(String.format("没有找到必须的源文件[{0}]",+this.TRSValidatorFiles[sPattern]));
		//return null;
	}
	this.TRSValidators[sPattern] = oValidator;
	return oValidator;	
	
}

//Define TRSValidator Controller Object
function CTRSValidator(){
	this.sErrorInfo = "";
	this.TRSValidators = {};
	this.TRSValidatorFiles = {};
}

CTRSValidator.registerMethod('validate', CTRSValidator_validate);
CTRSValidator.registerMethod('addValidators', CTRSValidator_addValidators);
CTRSValidator.registerMethod('getValidator', CTRSValidator_getValidator);

//define an TRSValidator Controller Object
var TRSValidator  = new CTRSValidator();