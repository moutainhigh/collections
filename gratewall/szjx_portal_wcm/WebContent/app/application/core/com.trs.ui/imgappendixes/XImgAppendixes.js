Ext.ns('com.trs.ui');
/**
*附件管理
*/
(function(){
	//private 
	var template = '<iframe src="{0}" width="" height="" class="XImgAppendix" id="XImgAppendix" frameborder="no" border="0"></iframe>';
	var appendixesPicCache = {};//由Name和Appendixes对象组成的Hash对象

	//private 将附件的json对象转成xml形式
	var getAppendixesXML = function(){
		var Appendixes = this.getAppendixes();
		if(!Appendixes) return "";
		var arr = Ext.Json.array(Appendixes, "APPENDIXES.APPENDIX")||[];
		var sParams = ["APPENDIXID","APPFILE","SRCFILE","APPLINKALT","APPFLAG","APPDESC","APPDESC2"];
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
				if(sName=='APPFILE'){
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
	com.trs.ui.XImgAppendixes = Ext.extend(com.trs.ui.BaseComponent, {
		getHtml : function(){
			var documentId = this.initConfig['objectId'];
			var url = WCMConstants.WCM6_PATH + 'document/document_pic_attachments.html?documentId=' + documentId;
			return String.format(template,url);			
		},
		setAppendixes : function(oAppendixes){
			appendixesPicCache[this.initConfig['name']] = Object.deepClone(oAppendixes);
		},
		getAppendixes : function(type){
			return appendixesPicCache[this.initConfig['name']];
		},
		getAppendixXML : function(type){
			return getAppendixesXML.call(this, type);
		},
		getValue : function(){
			//从小页面获取数据
			var iframeEl = $('XImgAppendix');
			var iframeWindow = iframeEl.contentWindow;
			iframeWindow.PageContext.collectDatas();
			var appendixes = iframeWindow.m_Appendixes;
			this.setAppendixes(appendixes['Type_20']);
			return getAppendixesXML.call(this);
		}
	});
})();