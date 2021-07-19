if(!top.KeyWords){	
	//top.KeyWords = {};
	function keyWordsGetted(transport, json){
		//Object.extend(top.KeyWords,json);	
		try{
			eval(transport.responseText.trim());
		}catch(e){
			top.KeyWords = {};
		}
		Object.extend(top.KeyWords, {
			//some other key words, exclude metatable fields and wcmdocument fields.
			//....
			'sysdate'	: true,
			'date'		: true,
			'time'		: true,
			'right'		: true,			
			'style'		: true,
			'select'	: true,
			'from'		: true,
			'where'		: true,
			'group'		: true,
			'order'		: true,
			'by'		: true,
			'desc'		: true,
			'asc'		: true,
			'delete'	: true,
			'update'	: true
		});
	}
    
	/*
	*把文档表的字段不再做为关键字了*/
	//get the url.
	var url = WCMConstants.WCM6_PATH + "metadata/key_words_getter.jsp?ignoredoc=true";

	//load the key words.
	new Ajax.Request(url, {
		method		: "get",
		onSuccess	: keyWordsGetted
	});
	//*/
	//keyWordsGetted();
}

function containKeyWordsInContainer(sContainer, isIncluded){
	if(sContainer == null){
		alert(wcm.LANG.METADBTABLE_4 || "没有指定要校验的容器");
		return false;
	}
	var elements = Form.getElements(sContainer);
	for (var i = 0, length = elements.length; i < length; i++){
		var element = elements[i];
		if(_containeKeyWords_(element, isIncluded)){
			return true;
		}
	}
	return false;
}

function containKeyWordsInFields(aFields, isIncluded){
	if(!Array.isArray(aFields)){
		aFields = [aFields];
	}
	for (var i = 0, length = aFields.length; i < length; i++){
		var element = $(aFields[i]);
		if(_containeKeyWords_(element, isIncluded)){
			return true;
		}
	}
	return false;
}

function containKeyWords(sValue){
	if(!sValue) return false;
	if(top.KeyWords[sValue.toLowerCase()]){
		return true;
	}
	return false;
}

function _containeKeyWords_(element, isIncluded){
	if(!element){
		return false;
	}
	if(isIncluded && element.getAttribute("includeKeyWord") == null) {
		return false;
	}
	if(!isIncluded && element.getAttribute("excludeKeyWord") != null){
		return false;
	}
	if(top.KeyWords[element.value.toLowerCase()]){
		var msg = String.format("[<font color=\'red\'>{0}</font>]为系统保留字!",element.value);
		try{
			Ext.Msg.$alert(msg);
		}catch(error){
			alert(msg);
			element.focus();
		}
		return true;
	}
}