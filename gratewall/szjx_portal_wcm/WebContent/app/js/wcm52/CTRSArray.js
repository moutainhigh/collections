//中文
function isAlien(a) {
   return isObject(a) && typeof a.constructor != 'function';
}
function isArray(a) {
    return isObject(a) && a.constructor == Array;
}
function isBoolean(a) {
    return typeof a == 'boolean';
}
function isEmpty(o) {
    var i, v;
    if (isObject(o)) {
        for (i in o) {
            v = o[i];
            if (isUndefined(v) && isFunction(v)) {
                return false;
            }
        }
    }
    return true;
}
function isFunction(a) {
    return typeof a == 'function';
}
function isNull(a) {
    return typeof a == 'object' && !a;
}
function isNumber(a) {
    return typeof a == 'number' && isFinite(a);
}
function isObject(a) {
    return (a && typeof a == 'object') || isFunction(a);
}
function isString(a) {
    return typeof a == 'string';
}
function isUndefined(a) {
    return typeof a == 'undefined';
} 

function CTRSArray_splice(_arSrc, _nStart, _nDelCount){
	var max = Math.max,
	min = Math.min,
	a = [], // The return value array
	e,  // element
	i = max(arguments.length - 3, 0),   // insert count
	k = 0,
	l = _arSrc.length,
	n,  // new length
	v,  // delta
	x;  // shift count

	_nStart = _nStart || 0;
	if (_nStart < 0) {
		_nStart += l;
	}	
	_nStart = max(min(_nStart, l), 0);  // start point
	_nDelCount = max(min(isNumber(_nDelCount) ? _nDelCount : l, l - _nStart), 0);    // delete count
	v = i - _nDelCount;
	n = l + v;
	while (k < _nDelCount) {
		e = _arSrc[_nStart + k];
		if (!isUndefined(e)) {
			 a[k] = e;
		}
		k += 1;
	}
	x = l - _nStart - _nDelCount;
	if (v < 0) {
		k = _nStart + i;
		while (x) {
			 _arSrc[k] = _arSrc[k - v];
			 k += 1;
			 x -= 1;
		}
		_arSrc.length = n;
	} else if (v > 0) {
		k = 1;
		while (x) {
			 _arSrc[n - k] = _arSrc[l - k];
			 k += 1;
			 x -= 1;
		}
	}
	for (k = 0; k < i; ++k) {
		_arSrc[_nStart + k] = arguments[k + 2];
	}
	return a;
}

function CTRSArray_remove(_arSrc, _sValue){
	for(var i=0; i<_arSrc.length; i++){
		if(_arSrc[i] == _sValue){
			return this.splice(_arSrc, i, 1);			
		}
	}
	return _arSrc;
}

function CTRSArray_clone(_arSrc){
	var newArray = new Array();
	newArray.length = _arSrc.length;
	for(var i=0; i<_arSrc.length; i++){
		newArray[i] = _arSrc[i];
	}
	return newArray;
}

function CTRSArray_toString(_arValue, _delim){
	if(_arValue == null || _arValue.length <= 0){
		return "";
	}
	var sDelim = _delim || ",";
	var sValue = "";
	for(var i=0; i<_arValue.length; i++){
		sValue += _arValue[i] + sDelim;
	}
	return sValue.substring(0, (sValue.length-sDelim.length));
}

function CTRSArray_isExist(_array, _element){
	if(!_array || !_element) return false;
	if(!_array.length){
		return (_array == _element);
	}
	for(var i=0; i<_array.length; i++){
		if(_element == _array[i]) return true;
	}
	return false;
}

function CTRSArray_distinct(_array){
	if(!_array || !_array.length) return _array;
	var newArray = new Array();
	for(var i=0; i<_array.length; i++){
		var oEl = _array[i];
		if(!oEl || this.isExist(newArray, oEl)) continue;
		newArray[newArray.length] = oEl;
	}
	return newArray;
}

function CTRSArray(){
//Define Methods
	this.splice = CTRSArray_splice;
	this.remove = CTRSArray_remove;
	this.clone	= CTRSArray_clone;
	this.toString = CTRSArray_toString;
	this.isExist = CTRSArray_isExist;
	this.distinct = CTRSArray_distinct;
}

var TRSArray = new CTRSArray();