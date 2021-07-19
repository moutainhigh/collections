$package('com.trs.web2frame');

com.trs.web2frame.PostData = {
	_elements : function(elements,_bCaseSensitive){
		var vData = {};
		for(var i=0;i<elements.length;i++){
			var oElement = elements[i];
			if(oElement.name&&!oElement.getAttribute("ignore")){
				if(oElement.getAttribute("isAttr")){
					var attrs = vData["__ATTRIBUTE__"];
					if(!attrs){
						attrs = vData["__ATTRIBUTE__"] = [];
					}
					if(_bCaseSensitive){
						attrs.push(oElement.name+'='+Form.Element.getValue(oElement)||'');
					}
					else{
						attrs.push(oElement.name.toUpperCase()+'='+Form.Element.getValue(oElement)||'');
					}
				}
				else{
					var sValue = Form.Element.getValue(oElement);
					if(sValue!=null){
						var tmp = null;
						if(_bCaseSensitive){
							tmp = vData[oElement.name];
						}
						else{
							tmp = vData[oElement.name.toUpperCase()];
						}
						if(!tmp){
							if(_bCaseSensitive){
								vData[oElement.name] = {NODEVALUE:sValue};
							}
							else{
								vData[oElement.name.toUpperCase()] = {NODEVALUE:sValue};
							}
						}
						else if(!Array.isArray(tmp)){
							var arr = null;
							if(_bCaseSensitive){
								arr = vData[oElement.name] = [];
							}
							else{
								arr = vData[oElement.name.toUpperCase()] = [];
							}
							arr.push(tmp.NODEVALUE);
							arr.push(sValue);
						}
						else{
							if(_bCaseSensitive){
								vData[oElement.name].push(sValue);
							}
							else{
								vData[oElement.name.toUpperCase()].push(sValue);
							}
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
		var eForm = $(_sFormId);
		var elements = Form.getElements(eForm);
		var bCaseSensitive = (eForm.CaseSensitive)?
				(eForm.CaseSensitive.value=='true'||eForm.CaseSensitive.value=='1'):false;
		return this._elements(elements,bCaseSensitive);
	},
	elements : function(_bCaseSensitive){
		var elements = [];
		for(var i=0;i<arguments.length;i++){
			var tagElements = document.getElementsByName(arguments[i]);
			for (var j = 0; j < tagElements.length; j++){
				elements.push(tagElements[j]);
			}
		}
		return this._elements(elements,_bCaseSensitive);
	},
	param : function(_sName,_sValue,_oData,_bCaseSensitive){
		_oData = _oData||{};
		var oTmp = null;
		if(_bCaseSensitive){
			oTmp = _oData[_sName] = {};
		}
		else{
			oTmp = _oData[_sName.toUpperCase()] = {};
		}
		oTmp['NODEVALUE'] = _sValue;
		return _oData;
	},
	params : function(_oParams,_oData){
		_oData = _oData||{};
		var bCaseSensitive = (_oParams)?_oParams['CaseSensitive']:false;
		for(var sName in _oParams){
			if(typeof _oParams[sName]=='function')continue;
			if(_oParams[sName]==null){
				if(bCaseSensitive){
					_oData[sName] = {NODEVALUE:""};
				}
				else{
					_oData[sName.toUpperCase()] = {NODEVALUE:""};
				}
			}
			else{
				if(bCaseSensitive){
					_oData[sName] = {NODEVALUE:_oParams[sName]+""};
				}
				else{
					_oData[sName.toUpperCase()] = {NODEVALUE:_oParams[sName]+""};
				}
			}
		}
		return _oData;
	}
};
ClassName(com.trs.web2frame.PostData,'web2frame.PostData');