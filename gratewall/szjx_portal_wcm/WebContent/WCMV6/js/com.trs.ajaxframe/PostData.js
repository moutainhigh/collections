$package('com.trs.ajaxframe');

com.trs.ajaxframe.PostData = {
	_elements : function(elements){
		var vData = {};
		for(var i=0;i<elements.length;i++){
			var oElement = elements[i];
			if(oElement.name&&!oElement.getAttribute("ignore")){
				if(oElement.getAttribute("isAttr")){
					var attrs = vData["__ATTRIBUTE__"];
					if(!attrs){
						attrs = vData["__ATTRIBUTE__"] = [];
					}
					attrs.push(oElement.name.toUpperCase()+'='+Form.Element.getValue(oElement)||'');
				}
				else{
					var sValue = Form.Element.getValue(oElement);
					if(sValue!=null){
						var tmp = vData[oElement.name.toUpperCase()];
						if(!tmp){
							vData[oElement.name.toUpperCase()] = {NODEVALUE:sValue};
						}
						else if(!Array.isArray(tmp)){
							var arr = vData[oElement.name.toUpperCase()] = [];
							arr.push(tmp.NODEVALUE);
							arr.push(sValue);
						}
						else{
							vData[oElement.name.toUpperCase()].push(sValue);
						}
					}
				}
			}
		}
		if(vData["__ATTRIBUTE__"]){
			vData["ATTRIBUTE"] = {NODEVALUE:vData["__ATTRIBUTE__"].join("&")};
			delete vData["__ATTRIBUTE__"];
		}
		for(var name in vData){
			if(Array.isArray(vData[name])){
				vData[name] = {NODEVALUE:vData[name].join(",")};
			}
		}
		return vData;
	},
	form : function(_sFormId) {
		var elements = Form.getElements($(_sFormId));
		return this._elements(elements);
	},
	elements : function(){
		var elements = [];
		for(var i=0;i<arguments.length;i++){
			var tagElements = document.getElementsByName(arguments[i]);
			for (var j = 0; j < tagElements.length; j++){
				elements.push(tagElements[j]);
			}
		}
		return this._elements(elements);
	},
	param : function(_sName,_sValue,_oData){
		_oData=_oData||{};
		_oData[_sName.toUpperCase()]={};
		_oData[_sName.toUpperCase()]['NODEVALUE']=_sValue;
		return _oData;
	},
	params : function(_oParams,_oData){
		_oData=_oData||{};
		for(var sName in _oParams){
			_oData[sName.toUpperCase()]={NODEVALUE:_oParams[sName]};
		}
		return _oData;
	}
};
ClassName(com.trs.ajaxframe.PostData,'ajaxframe.PostData');