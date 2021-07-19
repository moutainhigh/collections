/** Title:			CIntegerValidator.js
 *  Description:
 *		Methods of validator for Integer
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
 *		CBaseValidator.js
 *
 */
function CIntegerValidator(){	
}

CIntegerValidator.inherits(CBaseValidator);
CIntegerValidator.registerMethod("validate", CIntegerValidator_validate);
CIntegerValidator.registerMethod("validateNumberMaxLength", CIntegerValidator_validateNumberMaxLength);
CIntegerValidator.registerMethod("validateNumberMaxValue", CIntegerValidator_validateNumberMaxValue);
CIntegerValidator.registerMethod("validateNumberMinValue", CIntegerValidator_validateNumberMinValue);

function CIntegerValidator_validate(){
	if(this.oElement == null){
		this.oElement = arguments[0];
	}
	if(!this.superMethod("validate")){
		return false;
	}
	if(!this.isNumber(this.oElement.value)){
		this.sErrorInfo += (this.sErrorInfo.length>1?"\n\n":"")+String.format("您输入的[{0}]不是一个整数！",this.oElement.elname);
		return false;	
	}
	if(this.oElement.max_len){
		if(!this.validateNumberMaxLength(this.oElement.value, this.oElement.max_len)){
			this.sErrorInfo += (this.sErrorInfo.length>1?"\n\n":"")+String.format("您输入的[{0}]不能超过最大长度[{1}]！",this.oElement.elname,this.oElement.max_len);
			return false;
		}
	}
	if(this.oElement.max_value){
		if(!this.validateNumberMaxValue(this.oElement.value, this.oElement.max_value)){
			this.sErrorInfo += (this.sErrorInfo.length>1?"\n\n":"")+String.format("您输入的[{0}]不能超过最大值[{1}]！",this.oElement.elname,this.oElement.max_value);
			return false;
		}
	}
	if(this.oElement.min_value){
		if(!this.validateNumberMinValue(this.oElement.value, this.oElement.min_value)){
			this.sErrorInfo += (this.sErrorInfo.length>1?"\n\n":"")+String.format("您输入的[{0}]不能小于最小值[{1}]！",this.oElement.elname,this.oElement.min_value);
			return false;
		}
	}
	return true;
}

function CIntegerValidator_validateNumberMaxLength(_sCode, _nMaxLen){
	if(_nMaxLen == null || _nMaxLen <= 0){
		this.sErrorInfo += wcm.LANG.WCM52_ALERT_37 || "设定了错误的最大长度值！";
		return false;
	}
	_sCode = TRSString.trim(_sCode);
	var nLen = _sCode.length;
	if(nLen==0 || nLen<=_nMaxLen) return true;

	//this.sErrorInfo += "[" + _sCode + "] 的长度超过允许的最大长度 [" + _nMaxLen + "]!";
	return false; 
}

function CIntegerValidator_validateNumberMaxValue(_sCode, _nMaxValue){
	if(_nMaxValue == null){
		this.sErrorInfo += wcm.LANG.WCM52_ALERT_38 || "设定了错误的最大值！";
		return false;
	}
	var nValue = new Number(TRSString.trim(_sCode));
	var nMaxValue = new Number(_nMaxValue);
	if(nValue <= nMaxValue) return true;

	//this.sErrorInfo += "[" + nValue + "] 超过允许的最大值 [" + nMaxValue + "]!";
	return false; 
}

function CIntegerValidator_validateNumberMinValue(_sCode, _nMinValue){
	if(_nMinValue == null){
		this.sErrorInfo += wcm.LANG.WCM52_ALERT_39 || "设定了错误的最小值！";
		return false;
	}
	var nValue = new Number(TRSString.trim(_sCode));
	var nMinValue = new Number(_nMinValue);
	if(nValue >= nMinValue) return true;

	//this.sErrorInfo += "[" + _sCode + "] 小于允许的最小值 [" + nMinValue + "]!";
	return false; 
}