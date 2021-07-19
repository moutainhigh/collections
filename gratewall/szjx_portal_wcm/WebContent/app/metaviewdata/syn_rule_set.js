var SynRuleSetter = {
	serviceName		: 'wcm6_MetaDataDef',
	findMethodName	: 'findSynRule',
	saveMethodName	: 'saveSynRule',
	queryMethodName	: 'queryViewFieldInfos',
	channelId		: getParameter('channelId'),
	viewId : getParameter('viewId'),
	synRuleSetFrom : getParameter('synRuleSetFrom'),//channel  or  metaView

	getHelper : function(){
		return new com.trs.web2frame.BasicDataHelper();
	},
	doRequest : function(oParams){
		this.getHelper().Call(
			oParams["serviceName"] || this.serviceName,
			oParams["methodName"] || this.findMethodName,
			oParams["requestParams"] || {channelId : this.channelId, viewId : this.viewId, synRuleSetFrom : this.synRuleSetFrom}, 
			true,
			oParams["fCallBack"]
		);
	},
	
	loadData : function(){
		//第一个doRequest()请求的是已经设置了同步规则的参数
		this.doRequest({
			fCallBack : this.dataLoaded
		});
		//第二个doRequest()请求的是，获取到视图的所有字段
		this.doRequest({
			methodName		: this.queryMethodName,
			requestParams	: {SelectFields:'FIELDNAME,DBFIELDNAME,ANOTHERNAME', channelId : this.channelId, viewId : this.viewId, PageSize:-1},
			fCallBack		: function(transport, json){
				var viewFieldSel = $('viewFields');
				var aViewField = $a(json, "MetaViewFields.MetaViewField") || [];
				for (var i = 0; i < aViewField.length; i++){
					var option = document.createElement("option");
					option.value = $v(aViewField[i], "DBFIELDNAME");
					option.text = $v(aViewField[i], "ANOTHERNAME");
					viewFieldSel.options.add(option);
				}
			}
		});
		if(this.synRuleSetFrom == "metaView"){
			document.getElementById("synType").style.display="none";
		}
		
	},
	dataLoaded : function(transport, json){
		if(!json){
			return;
		}
		//初始赋值
		$('DocTitle').value = json["DOCTITLE"];
		$('DocContent').value = json["DOCCONTENT"];
		for (var name in json){
			if(name.toUpperCase() == "DOCTITLE" || name.toUpperCase() == "DOCCONTENT") 
				continue;
			Insertion.Bottom($('containerInner'), $('fieldTemplate').innerHTML);
            var newNode = Element.last($('containerInner'));
			var inputs = newNode.getElementsByTagName("input");
			inputs[0].value = name;
			inputs[1].value = json[name];
		}


		$('DocTitle').focus();
	},
	saveData : function(){
		debugger;
		var synType = 0;
		if(this.synRuleSetFrom == "channel"){
			var synTypes = document.getElementsByName("synType");
			for (var i = 0; i < synTypes.length; i++){
				if(synTypes[i].checked){
					synType = synTypes[i].value;
					break;
				}
			}
		} else {
			synType = 3;
		}
		var _dotTitle = document.getElementById('DocTitle').value;
		var _docContent = document.getElementById('DocContent').value;
		if((_dotTitle + _docContent).byteLength() > 500){
			alert(wcm.LANG.METAVIEWDATA_127 || "DocTitle和DocContent值加起来不要超过500字节.请尽量简短.");
			return false;
		}

		var _otherFieldContainer = $('otherFieldContainer');
		var arr = [];
		if(_otherFieldContainer){
			var inputs = _otherFieldContainer.getElementsByTagName("input");
			var i = 0;
			if(inputs && inputs.length > 1){
				//校验
				var bValid = true;
				while( i < inputs.length) {
					if(inputs.length == 2 && !inputs[0].value & !inputs[1].value){
						bValid = false;
						break;
					} 
					var k = inputs[i].value;
					if(!k){
						alert("字段名称不能为空.");
						return false;
					}					
					i = i + 2;
				}
			    i = 0;
				//赋值
				if(bValid){
					while(i < inputs.length){
						arr.push(inputs[i].value + "=" + inputs[i+1].value);
						i = i + 2;
					}	
				}
			}
		}
		var _otherSynField = "";
		if(arr.length > 0){
			_otherSynField = arr.join("&");
		}
		var _metaSynField = "docTitle=" + _dotTitle + "&docContent=" + _docContent
		if(_otherSynField.length > 0){
			_metaSynField = _metaSynField + "&" + _otherSynField;
		}

		var oParams = {
			channelId	: this.channelId,
			viewId : this.viewId,
			synType : synType,
			metaSynField    : _metaSynField
		};
		this.doRequest({
			methodName		: this.saveMethodName,
			requestParams	: oParams, 
			fCallBack		: this.dataSaved
		});
		return false;
	},
	dataSaved : function(){
		FloatPanel.close(true);
	}
};

function onOk(){
	return SynRuleSetter.saveData();
}

function mozWrap(txtarea, open)
{
   var selLength = txtarea.textLength;
   var selStart = txtarea.selectionStart;
   var selEnd = txtarea.selectionEnd;
   if (selEnd == 1 || selEnd == 2)
      selEnd = selLength;

   var s1 = (txtarea.value).substring(0,selStart);
   var s2 = (txtarea.value).substring(selStart, selEnd)
   var s3 = (txtarea.value).substring(selEnd, selLength);
   txtarea.value = s1 + open + s2 + s3;
   return;
}

Event.observe(window, 'load', function(){
	SynRuleSetter.loadData(); 

	Event.observe('viewFields', 'change', function(event){
		var viewFieldValue = $F('viewFields');
		window.lastFocus = window.lastFocus || $('DocTitle');
		window.lastFocus.focus();
		//适应多浏览器情况
		var selection = window.lastFocus;
		var content = "${" + viewFieldValue + "}";
		if(Ext.isIE){
			selection = document.selection.createRange().text = content;
		}
		else if (selection.selectionEnd - selection.selectionStart >= 0)
		{
			mozWrap(selection, content);
		}
	});
});

function addField(){
	if($('containerInner').childNodes.length == 0){
		var sHeaderRow = '<li class="head_li"><span class="head_label">文档字段</span><span class="head_value">视图字段</span></li>';
		Insertion.Bottom($('containerInner'), sHeaderRow);
	}
	Insertion.Bottom($('containerInner'), $('fieldTemplate').innerHTML);
}

Event.observe(document, 'click', function(event){
	//filter for condition.
	var srcElement = Event.element(window.event || event);
	if(!Element.hasClassName(srcElement, "deleteRow")){
		return;
	}

	//get row object.
	var tempNode = getRowObject(srcElement);
	if(!tempNode) return true;

	//delete the row or clear the value.
	if(Element.previous(tempNode) || Element.next(tempNode)){
		Element.remove(tempNode);
	}else{
		var inputs = tempNode.getElementsByTagName("input");
		for(var i = 0; i < inputs.length; i++){
			inputs[i].value = "";
		}
	}
});

function getRowObject(tempNode){
	while(tempNode){
		if(Element.hasClassName(tempNode, "row_li")){
			break;
		}
		tempNode = tempNode.parentNode;
	}
	return tempNode;
}