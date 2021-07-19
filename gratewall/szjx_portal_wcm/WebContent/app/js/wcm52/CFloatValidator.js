function CFloatValidator(){	
}

CFloatValidator.inherits(CBaseValidator);
CFloatValidator.registerMethod("validate", CFloatValidator_validate);
CFloatValidator.registerMethod("isFloat",  CFloatValidator_isMultiFloat);

function CFloatValidator_validate(){
	if(this.oElement == null){
		this.oElement = arguments[0];
	}
	if(!this.superMethod("validate")){
		return false;
	}
	if(!this.isFloat(this.oElement.value)){
		this.sErrorInfo += (this.sErrorInfo.length>1?"\n\n":"")+String.format("您输入的[{0}] 不是一个小数！",this.oElement.elname);
		return false;
	}
	return true;
}

function CFloatValidator_isSimpleFloat(_sCode){
	_sCode = TRSString.trim(_sCode);
	var nLen = _sCode.length;
	if(nLen==0)return true;

	//get int part length of a float
	var nPose = _sCode.indexOf(".");
	var myReg ;
	if(nPose<1){
		myReg = new RegExp("^[1-9][0-9]{0,}$");
	}else{
		if(nPose==1){
			myReg = new RegExp("^[0-9]\.[0-9]{"+(nLen-nPose-1)+"}$"); 	
		}else{
			myReg = new RegExp("^[1-9][0-9]{"+(nPose-1)+"}\.[0-9]{"+(nLen-nPose-1)+"}$"); 
		}
	}
	if(myReg.test(_sCode)) return true;
	
	return false; 
}

//校验科学计数法的前部
function CFloatValidator_isMultiFloatPrefix(_sPrefix) {
	var nLen = _sPrefix.length;
	if(nLen==0) return false;

	//get int part length of a float
	var nPose = _sPrefix.indexOf(".");
	var myReg ;
	if(nPose<1){
		myReg = new RegExp("^[1-9][0-9]{0,}$");
	}else{
		if(nPose==1){
			myReg = new RegExp("^[0-9]\.[0-9]{"+(nLen-nPose-1)+"}$"); 	
		}else{
			myReg = new RegExp("^[1-9][0-9]{"+(nPose-1)+"}\.[0-9]{"+(nLen-nPose-1)+"}$"); 
		}
	}
	if(myReg.test(_sPrefix)) {
		return true;
	}
	return false; 
}
//校验科学计数法的后部
function CFloatValidator_isMultiFloatSuffix(_sSuffix) {
	var regexp = new RegExp("^[\-\+]{0,1}[1-9]+[0-9]*$");
	return (regexp.test(_sSuffix));
}

//校验Float，兼容科学计数法的格式
function CFloatValidator_isMultiFloat(_sCode) {
	_sCode = _sCode.replace( /^\s*/, "" ).replace( /\s*$/, "" );
	_sCode = _sCode.toUpperCase();
	var nLen = _sCode.length;
	if(nLen==0) return true;

	var array = _sCode.split("E");
	if(array.length > 2) {
		return false; 
	}

	if(array.length == 1) {
		return CFloatValidator_isSimpleFloat(array[0]);
	}

	return CFloatValidator_isMultiFloatPrefix(array[0]) && CFloatValidator_isMultiFloatSuffix(array[1]);
}