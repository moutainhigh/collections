var SynRuleSetter = {
	serviceName		: 'wcm6_MetaDataDef',
	findMethodName	: 'findSynRule',
	saveMethodName	: 'saveSynRule',
	queryMethodName	: 'queryViewFieldInfos',
	channelId		: getParameter('channelId'),

	getHelper : function(){
		return new com.trs.web2frame.BasicDataHelper();
	},
	doRequest : function(oParams){
		this.getHelper().call(
			oParams["serviceName"] || this.serviceName,
			oParams["methodName"] || this.findMethodName,
			oParams["requestParams"] || {channelId : this.channelId}, 
			true,
			oParams["fCallBack"]
		);
	},
	loadData : function(){
		this.doRequest({
			fCallBack : this.dataLoaded
		});
		this.doRequest({
			methodName		: this.queryMethodName,
			requestParams	: {SelectFields:'FIELDNAME,DBFIELDNAME,ANOTHERNAME', channelId : this.channelId, PageSize:-1},
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
	},
	dataLoaded : function(transport, json){
		if(!json){
			return;
		}
		$('DocTitle').value = json["DOCTITLE"];
		$('DocContent').value = json["DOCCONTENT"];

		$('DocTitle').focus();
	},
	saveData : function(){
		var synType = 0;
		var synTypes = document.getElementsByName("synType");
		for (var i = 0; i < synTypes.length; i++){
			if(synTypes[i].checked){
				synType = synTypes[i].value;
				break;
			}
		}
		var oParams = {
			channelId	: this.channelId,
			synType		: synType,
			docTitle	: $F('DocTitle'),
			docContent	: $F('DocContent')
		};
		this.doRequest({
			methodName		: this.saveMethodName,
			requestParams	: oParams, 
			fCallBack		: this.dataSaved
		});
	},
	dataSaved : function(){
		FloatPanel.close(true);
	}
};

FloatPanel.addCloseCommand();   
FloatPanel.addCommand('savebtn', '确定', SynRuleSetter.saveData, SynRuleSetter);

Event.observe(window, 'load', function(){
	SynRuleSetter.loadData(); 

	Event.observe('viewFields', 'change', function(event){
		var viewFieldValue = $F('viewFields');
		if(!viewFieldValue) return;
		window.lastFocus = window.lastFocus || $('DocTitle');
		window.lastFocus.focus();
		document.selection.createRange().text = "${" + viewFieldValue + "}";
	});
});
