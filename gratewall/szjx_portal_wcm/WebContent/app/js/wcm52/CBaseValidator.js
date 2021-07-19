/** Title:			CBaseValidator.js
 *  Description:
 *		Base function of validators
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WSW
 *  Created:		2004-11-23
 *  Vesion:			1.0
 *  Last EditTime:	2004-11-23
 *	Update Logs:
 *		WSW@2004-11-23 Created File
 *	Note:
 *		
 *
 *	Depends:
 *		CTRSString.js
 *
 */

function CBaseValidator_validate(){	
	this.sErrorInfo	= "";
	if(this.oElement == null){
		this.sErrorInfo	+= (this.sErrorInfo.length>1?"\n\n":"")+(wcm.LANG.WCM52_ALERT_16 || "客户端校验器组装失败！ 无法找到目标元素！\n");
		return false;
	}
	if(this.oElement.not_null){
		if(!this.validateNotNull(this.oElement.value)){
			this.sErrorInfo += (this.sErrorInfo.length>1?"\n\n":"")+ (wcm.LANG.WCM52_ALERT_17 || "请输入") + " ["+this.oElement.elname+"] ！\n";
			return false;
		}
	}
	return true;
}

function CBaseValidator_validateNotNull(_sCode){
	_sCode = TRSString.trim(_sCode);
	var nLen = TRSString.getStringLength(_sCode);
	if(nLen==0){
		//this.sErrorInfo += " 您输入的信息为空，不符合要求！";
		return false;
	}
	return true;
}

function CBaseValidator_isNumber(_sCode){
	_sCode = _sCode.toString();
	var validChars = "0123456789";
	var startFrom = 0;
	if (_sCode.substring(0, 2) == "0x") {
	   validChars = "0123456789abcdefABCDEF";
	   startFrom = 2;
	} else if (_sCode.charAt(0) == "0") {
	   validChars = "01234567";
	   startFrom = 1;
	} else if (_sCode.charAt(0) == "-") {
		startFrom = 1;
	}

	for (var n = startFrom; n < _sCode.length; n++) {
		if (validChars.indexOf(_sCode.substring(n, n+1)) == -1){
			//this.sErrorInfo += "您输入的 [" + _sCode + "] 不是一个数字！";
			return false;
		}
	}
	return true;
}

function CBaseValidator_setElement(_oElement){
	this.oElement = _oElement;
}

function CBaseValidator(){
	this.sErrorInfo	= "";
	this.oElement	= null;
}

CBaseValidator.registerMethod("validate", CBaseValidator_validate);
CBaseValidator.registerMethod("validateNotNull", CBaseValidator_validateNotNull);
CBaseValidator.registerMethod("isNumber", CBaseValidator_isNumber);
CBaseValidator.registerMethod("setElement", CBaseValidator_setElement);


var BaseValidator = new CBaseValidator();