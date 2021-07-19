$package('com.trs.ajaxframe');

/**
 * 扩展的自定义置标
 */
Object.extend(com.trs.ajaxframe.TagParser.prototype,{
	_parse_SELECT_ONE:function(_eElement,_oDataSource,_eDataSource,_oJson,_oParams,_sSelection){
		var select			= _eElement.getAttribute('select');
		var nooptionhide	= _eElement.getAttribute('nooptionhide');
		var valuebind		= _eElement.getAttribute('value');
		if(!valuebind){
			throw new Error('未指定取值范围,无法构建select置标的options.');
		}
		valuebind			= valuebind.toUpperCase();
		var textbind		= _eElement.getAttribute('text');
		if(!textbind){
			textbind		= valuebind;
		}
		textbind			= textbind.toUpperCase();
		var selected		= _eElement.getAttribute('selected')||'';
		selected			= this._fillValue(selected , _oJson , _oParams ,_sSelection);
		var defaultValue	= _eElement.getAttribute('default');
		defaultValue		= (defaultValue||'').trim();
		var sHtml			= '<select '
		var attrs			= [];
		var attributes		= _eElement.attributes;
		for(var j=0; j<attributes.length; j++){
			if(!_IE||attributes[j].specified){
				if(!['select','value','selected','text','default'].include(attributes[j].name.toLowerCase())){
					var value	= attributes[j].value;
					value		= this._fillValue(value , _oJson , _oParams ,_sSelection);
					attrs.push(attributes[j].name+'="'+value+'"');
				}
			}
		}
		sHtml				+= attrs.join(' ')+'>';
		try{
			if(select){
				select				= (_sSelection)?_sSelection+'.'+select.toUpperCase():select.toUpperCase();
				var selection		= com.trs.ajaxframe.TagHelper.array(select,_oJson)||[];
				var include			= false;
				if(nooptionhide&&selection.length==0){
					return '';
				}
				for(var i=0;i<selection.length;i++){
					var si			= selection[i];
					var thisValue	= si[valuebind];
					if(typeof thisValue=='object'){
						thisValue	= thisValue['NODEVALUE'];
					}
					if(selected==thisValue.trim()){
						include		= true;
					}
				}
				if(!include)selected	= defaultValue;
				for(var i=0;i<selection.length;i++){
					var si			= selection[i];
					var thisValue	= si[valuebind];
					if(typeof thisValue=='object'){
						thisValue	= thisValue['NODEVALUE'];
					}
					var thisText	= si[textbind];
					if(typeof thisText=='object'){
						thisText	= thisText['NODEVALUE'];
					}
					sHtml+='<option value="'+thisValue+'"';
					if(selected==thisValue.trim()){
						sHtml		+= ' selected';
					}
					sHtml			+= '>'+thisText+'</option>';
				}
			}
			else{
				var values			= valuebind.split('##');
				var include			= false;
				for(var i=0;i<values.length;i++){
					var thisValue	= values[i];
					if(selected==thisValue.trim()){
						include		= true;
					}
				}
				if(!include)selected	= defaultValue;
				var texts			= textbind.split('##');
				for(var i=0;i<values.length;i++){
					var thisValue	= values[i];
					var thisText	= texts[i];
					sHtml			+= '<option value="'+thisValue+'"';
					if(selected==thisValue.trim()){
						sHtml		+= ' selected';
					}
					sHtml			+= '>'+thisText+'</option>';
				}
			}
		}catch(err){
			throw new Error('select_one置标解析出错:'+err.message+':\n'+(err.stack||''));
		}
		sHtml						+= '</select>';
		return sHtml;
	},
	_parse_CHECKBOX:function(_eElement,_oDataSource,_eDataSource,_oJson,_oParams,_sSelection){
		var select=_eElement.getAttribute('select');
		var valuebind=_eElement.getAttribute('value');
		if(!valuebind){
			$log().error('checkbox null value');
			throw new com.trs.util.Exception('未指定取值范围,无法构建input(type=checkbox)置标的values.');
		}
		valuebind=valuebind.toUpperCase();
		var textbind=_eElement.getAttribute('text');
		if(!textbind){
			textbind=valuebind;
		}
		textbind=textbind.toUpperCase();
		var checked=_eElement.getAttribute('checked')||'';
		checked=this._fillValue(checked , _oJson , _oParams ,_sSelection);
		checked=checked.split(',');
		checked=checked.map(function(e){return e.trim();})
		var sHtml='';
		var attrs=[];
		var attributes=_eElement.attributes;
		for(var j=0; j<attributes.length; j++){
			if(!_IE||attributes[j].specified){
				if(!['select','value','checked','text'].include(attributes[j].name.toLowerCase())){
					var value=attributes[j].value;
					value=this._fillValue(value , _oJson , _oParams ,_sSelection);
					attrs.push(attributes[j].name+'="'+value+'"');
				}
			}
		}
		attrs=attrs.join(' ');
		try{
			if(select){
				select=select.toUpperCase();
				var selection=com.trs.ajaxframe.TagHelper.array(select,_oJson);
				for(var i=0;i<selection.length;i++){
					var si=selection[i];
					var thisValue=si[valuebind];
					if(typeof thisValue=='object'){
						thisValue=thisValue['NODEVALUE'];
					}
					var thisText=si[textbind];
					if(typeof thisText=='object'){
						thisText=thisText['NODEVALUE'];
					}
					sHtml+='<input type=checkbox '+attrs+' value="'+thisValue+'"';
					if(checked.include(thisValue.trim())){
						sHtml+=' checked';
					}
					sHtml+='>'+thisText;
				}
			}
			else{
				var values=valuebind.split('##');
				var texts=textbind.split('##');
				for(var i=0;i<values.length;i++){
					var thisValue=values[i];
					var thisText=texts[i];
					sHtml+='<input type=checkbox '+attrs+' value="'+thisValue+'"';
					if(checked.include(thisValue.trim())){
						sHtml+=' checked';
					}
					sHtml+='>'+thisText;
				}
			}
		}catch(err){
			throw new Error('checkbox置标解析出错:'+err.message);
		}
		return sHtml;
	},
	_parse_RADIOBOX:function(_eElement,_oDataSource,_eDataSource,_oJson,_oParams,_sSelection){
		var sValue = _eElement.getAttribute('value');
		var checked = _eElement.getAttribute('checked')||'';
		var sText	= _eElement.getAttribute('text')||'';
		checked = this._fillValue(checked , _oJson , _oParams ,_sSelection);
		var sHtml='';
		var attrs=[];
		var attributes=_eElement.attributes;
		for(var j=0; j<attributes.length; j++){
			if(!_IE||attributes[j].specified){
				if(!['value','checked'].include(attributes[j].name.toLowerCase())){
					var value=attributes[j].value;
					value=this._fillValue(value , _oJson , _oParams ,_sSelection);
					attrs.push(attributes[j].name+'="'+value+'"');
				}
			}
		}
		attrs=attrs.join(' ');
		sHtml+='<input type=checkbox '+attrs+' value="'+sValue+'"';
		if((checked||'false').trim().toLowerCase()=='true'){
			sHtml+=' checked';
		}
		sHtml+='>' + sText;
		return sHtml;
	},
	_parse_RADIO:function(_eElement,_oDataSource,_eDataSource,_oJson,_oParams,_sSelection){
		var select=_eElement.getAttribute('select');
		var valuebind=_eElement.getAttribute('value');
		if(!valuebind){
			throw new com.trs.util.Exception('未指定取值范围,无法构建input(type=checkbox)置标的values.');
		}
		valuebind=valuebind.toUpperCase();
		var textbind=_eElement.getAttribute('text');
		if(!textbind){
			textbind=valuebind;
		}
		textbind=textbind.toUpperCase();
		var checked=_eElement.getAttribute('checked')||'';
		checked=this._fillValue(checked , _oJson , _oParams ,_sSelection);
		var defaultValue=_eElement.getAttribute('default');
		var sHtml='';
		var attrs=[];
		var attributes=_eElement.attributes;
		for(var j=0; j<attributes.length; j++){
			if(!_IE||attributes[j].specified){
				if(!['select','value','checked','text','default'].include(attributes[j].name.toLowerCase())){
					var value=attributes[j].value;
					value=this._fillValue(value , _oJson , _oParams ,_sSelection);
					attrs.push(attributes[j].name+'="'+value+'"');
				}
			}
		}
		attrs=attrs.join(' ');
		try{
			if(select){
				select=select.toUpperCase();
				var selection=com.trs.ajaxframe.TagHelper.array(select,_oJson);
				var include=false;
				for(var i=0;i<selection.length;i++){
					var si=selection[i];
					var thisValue=si[valuebind];
					if(typeof thisValue=='object'){
						thisValue=thisValue['NODEVALUE'];
					}
					if(checked==thisValue.trim()){
						include=true;
					}
				}
				if(!include)checked=defaultValue;
				for(var i=0;i<selection.length;i++){
					var si=selection[i];
					var thisValue=si[valuebind];
					if(typeof thisValue=='object'){
						thisValue=thisValue['NODEVALUE'];
					}
					var thisText=si[textbind];
					if(typeof thisText=='object'){
						thisText=thisText['NODEVALUE'];
					}
					sHtml+='<input type=radio '+attrs+' value="'+thisValue+'"';
					if(checked==thisValue.trim()){
						sHtml+=' checked';
					}
					sHtml+='>'+thisText;
				}
			}
			else{
				var values=valuebind.split('##');
				var include=false;
				for(var i=0;i<values.length;i++){
					var thisValue=values[i];
					if(checked==thisValue.trim()){
						include=true;
					}
				}
				if(!include)checked=defaultValue;
				var texts=textbind.split('##');
				for(var i=0;i<values.length;i++){
					var thisValue=values[i];
					var thisText=texts[i];
					sHtml+='<input type=radio '+attrs+' value="'+thisValue+'"';
					if(checked==thisValue.trim()){
						sHtml+=' checked';
					}
					sHtml+='>'+thisText;
				}
			}
		}catch(err){
			throw new Error('radio置标解析出错:'+err.message);
		}
		return sHtml;
	},
	_parse_RADIO2:function(_eElement,_oDataSource,_eDataSource,_oJson,_oParams,_sSelection){
		var sValue		= _eElement.getAttribute('value')||'';
		sValue			= this._fillValue(sValue , _oJson , _oParams ,_sSelection);
		var checked		= _eElement.getAttribute('checked')||'';
		checked			= this._fillValue(checked , _oJson , _oParams ,_sSelection);
		var sHtml		= '';
		var attrs		= [];
		var attributes	= _eElement.attributes;
		for(var j=0; j<attributes.length; j++){
			if(!_IE||attributes[j].specified){
				if(!['value','checked'].include(attributes[j].name.toLowerCase())){
					var value	= attributes[j].value;
					value		= this._fillValue(value , _oJson , _oParams ,_sSelection);
					attrs.push(attributes[j].name+'="'+value+'"');
				}
			}
		}
		attrs			= attrs.join(' ');
		sHtml			+= '<input type=radio '+attrs+' value="'+sValue+'"';
		if(checked==sValue){
			sHtml		+= ' checked';
		}
		sHtml			+= '>'+_eElement.innerHTML;
		return sHtml;
	},
	_parse_COUNT : function(_eElement,_oDataSource,_eDataSource,_oJson,_oParams,_sSelection){
		return _oDataSource.recordsNum(_oJson)+'';
	},
	_parse_LINK : function(_eElement,_oDataSource,_eDataSource,_oJson,_oParams,_sSelection){
		var sLinkId	= _eElement.id;
		var eLink	= $(sLinkId);
		var sHref	= _eElement.getAttribute('href',2);
		sHref		= this._fillValue(sHref , _oJson , _oParams ,_sSelection);
		if(eLink){
			eLink.href	= sHref;
//			document.createStyleSheet(text);
		}
		else{
			if(_IE){
				var eLink	= document.createElement('LINK');
				eLink.rel	= 'stylesheet';
				eLink.type	= 'text/css';
				eLink.href	= sHref;
				try{
					document.getElementsByTagName("head")[0].appendChild(eLink);
				}catch(err){
					document.createStyleSheet(sHref);
				}
			}
			else{
				return '<link rel="stylesheet" type="text/css" id="'+sLinkId+'" href="'+sHref+'"/>';
			}
		}
		return '';
	},
	_parse_LITERATOR:function(_eElement,_oDataSource,_eDataSource,_oJson,_oParams,_sSelection){
		//TODO url的缺省值
		var url = _eElement.getAttribute("url") || '';
		var params = _eElement.getAttribute("params") || '';
		params = this._fillValue(params , _oJson , _oParams ,_sSelection);
		params = encodeParams(params);
		var sRefElmId = _eElement.getAttribute("ref");
		if(sRefElmId) {
			if($(sRefElmId) == null) {
				alert('指定的<trs:literator>节点的ref="' + sRefElmId + '"属性无效！');
				return '';
			}
			var iTime = new Date().getTime();
			var fId = '_flih_' + iTime;
			var sHtml = '<script>\n\n'
					+ 'var '+fId+'=function(_tran){\n'
					+ 'var _lih_ = $("' + sRefElmId + '");\n'
					+ '_lih_.innerHTML = _tran.responseText;\n'
					+ '};'
					+ 'new Ajax.Request("'+url+'",{method:"get",parameters:"'+params+'",onComplete:' + fId + '});\n'
					+ '</s'+'cript>';
			return sHtml;				
		}
		var iTime = new Date().getTime();
		var sId = '_lih_' + iTime;
		var fId = '_flih_' + iTime;
		var sHtml = '<SPAN id="'+sId+'" style="display:none"></SPAN>';
		sHtml += '<script>\n\n'
				+ 'var '+fId+'=function(_tran){\n'
				+ 'var _lih_ = $("' + sId + '");\n'
				+ 'new Insertion.Before(_lih_,_tran.responseText);\n'
				+ '};'
				+ 'new Ajax.Request("'+url+'",{method:"get",parameters:"'+params+'",onComplete:' + fId + '});\n'
				+ '</s'+'cript>';
		return sHtml;
	}
});
