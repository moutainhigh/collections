if(!top.KeyWords){
	top.KeyWords = {};
	function keyWordsGetted(transport, json){
		Object.extend(top.KeyWords, {
			//some other key words, exclude metatable fields and wcmdocument fields.
			//....
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

	//get the url.
	var url = getWebURL() + "WCMV6/metadata/key_words_getter.jsp";

	//load the key words.
	new Ajax.Request(url, {
		method		: false,
		onSuccess	: keyWordsGetted
	});
}

function containKeyWordsInContainer(sContainer, isIncluded){
	if(sContainer == null){
		alert("没有指定要校验的容器");
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
		var msg = "[<font color='red'>" + element.value + "</font>]为系统保留字！";
		try{
			$alert(msg, function(){
				$dialog().hide();
				setTimeout(function(){
					element.focus();
				}, 10);
			});
		}catch(error){
			alert(msg);
			element.focus();
		}
		return true;
	}
}