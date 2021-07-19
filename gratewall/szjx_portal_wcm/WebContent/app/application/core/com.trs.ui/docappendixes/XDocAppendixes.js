Ext.ns('com.trs.ui');
/**
*附件管理
*/
(function(){
	//private 
	var template = '<div class="XDocAppendixes" id="{0}"></div>';
	var appendixesCache = {};//由Name和Appendixes对象组成的Hash对象

	//private 将附件的json对象转成xml形式
	var getAppendixesXML = function(type){
		var Appendixes = this.getAppendix(type);
		if(!Appendixes) return "";
		var arr = Ext.Json.array(Appendixes, "APPENDIXES.APPENDIX")||[];
		var sParams = ["APPENDIXID","APPFILE","SRCFILE","APPLINKALT","APPFLAG","APPDESC"];
		var myValue = Ext.Json.value;
		var sRetVal = '<OBJECTS>';
		for(var i=0;i<arr.length;i++){
			var oAppendix = arr[i];
			sRetVal += '<OBJECT';
			for(var j=0;j<sParams.length;j++){
				var sName = sParams[j];
				var sValue = myValue(oAppendix,sName)||'';
				if(sName=='APPENDIXID'){
					if(isNaN(sValue)) sValue = 0;
				}
				if(type==20&&sName=='APPFILE'){
					sRetVal += ' APPFILE="'+((myValue(oAppendix,'APPFILE.FILENAME')||'')+'').escape4Xml()+'"';
				}
				else if(sName=='APPENDIXID'){
					sRetVal += ' ID="'+sValue+'"';
				}
				else{
					sRetVal += ' '+sName+'="'+(sValue+'').escape4Xml()+'"';
				}
			}
			sRetVal += '/>';
		}
		sRetVal += '</OBJECTS>';
		return sRetVal;
	};

	var loadDocAppendixesByAjax = function(sName, sValue, fCallback){
		new Ajax.Request(XConstants.BASE_PATH + "com.trs.ui/docappendixes/XDocAppendixesHandler.jsp",
		{
			parameters : "ObjectId=" + sValue,
			method : 'post',
			onSuccess : function(transport){
				var oAppendixes = {};
				var json = eval("(" + transport.responseText + ")");
				for (var i = 0; i < json.length; i++){
					var sAppendixType = 'Type_'+json[i][0];
					var sAppendixXML = json[i][1];
					var oAppendix = {};
					try{
						oAppendix = Ext.Json.parseXml(loadXml(sAppendixXML)) || {};
					}catch(err){
						alert(err.message);
					}
					oAppendixes[sAppendixType] = oAppendix;
				}
				appendixesCache[sName] = oAppendixes;
				if(fCallback) fCallback();
			}
		});
	};

	com.trs.ui.XDocAppendixes = Ext.extend(com.trs.ui.BaseComponent, {
		getHtml : function(){
			return String.format(template, this.initConfig['name']);			
		},
		getAppendixes : function(){
			return appendixesCache[this.initConfig['name']];
		},
		setAppendixes : function(oAppendixes){
			appendixesCache[this.initConfig['name']] = Object.deepClone(oAppendixes);
		},
		getAppendix : function(type){
			var oAppendixes = this.getAppendixes();
			if(oAppendixes) return oAppendixes['Type_' + type];
			return null;
		},
		getAppendixXML : function(type){
			return getAppendixesXML.call(this, type);
		},
		/**
		*单击附件管理按钮时做的处理
		*/
		handleAppendixes : function(){
			var oAppendixes = this.getAppendixes();			
			if(oAppendixes == null){
				loadDocAppendixesByAjax(this.initConfig['name'], this.initConfig['objectId'], arguments.callee.bind(this));
				return;
			}
			var caller = this;
			FloatPanel.open({
				src : WCMConstants.WCM6_PATH + 'document/document_attachments.html',
				title : wcm.LANG.METAVIEWDATA_95 || '附件管理',		
				callback : function(info){
					caller.setAppendixes(info);
				},
				dialogArguments : oAppendixes
			});
		},
		initActions : function(){
			Event.observe(this.initConfig['name'], 'click', this.handleAppendixes.bind(this));
		},
		getValue : function(){
			return getAppendixesXML.call(this, 10);
		}
	});
})();