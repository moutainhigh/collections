$package('com.trs.ajaxframe');

com.trs.ajaxframe.Action = {
	_base : function(_sUrl,_sServiceName,_sMethodName,_oPostData,_oOptions){
//		var xml= this._buildXML(_sServiceName,_sMethodName,com.trs.util.JSON.parseJsonToXML('',_oPostData,'\t\t'));
		var xml= this._buildXml(_sServiceName,_sMethodName,_oPostData);
//		var postBody=com.trs.util.XML.loadXML(xml);
//		return new com.trs.ajax.RequestWithData(_sUrl,_oOptions,postBody);
		return new com.trs.ajax.RequestWithData(_sUrl,_oOptions,xml);
	},
	post : function(_sUrl,_sServiceName,_oPostData,_oOptions){
		return this._base(_sUrl,_sServiceName,'post',_oPostData,_oOptions);
	},
	save : function(_sUrl,_sServiceName,_oPostData,_oOptions){
		return this._base(_sUrl,_sServiceName,'save',_oPostData,_oOptions);
	},
	_delete : function(_sUrl,_sServiceName,_oPostData,_oOptions){
		return this._base(_sUrl,_sServiceName,'delete',_oPostData,_oOptions);
	},
	call : function(_sUrl,_sServiceName,_sMethodName,_oPostData,_oOptions){
		return this._base(_sUrl,_sServiceName,_sMethodName,_oPostData,_oOptions);
	},
	multiCall : function(_sUrl , _arrCombined , _oOptions){
		var xml = '<post-data>\n';
		for(var i=0;i<_arrCombined.length;i++){
			xml += '\n\t<method type="'+_arrCombined[i]["methodName"]+'">'+
					 _arrCombined[i]["serviceName"]+
					'</method>'+
					'\n\t<parameters>\n'+
						com.trs.util.JSON.parseJsonToXML('',_arrCombined[i]["params"],'\t\t')+
					'\n\t</parameters>';
		}
		xml += '\n</post-data>';
		return new com.trs.ajax.RequestWithData(_sUrl,_oOptions,xml);
	},
	multiCall2 : function(_sUrl , _arrCombined , _oOptions){
		var myDoc = com.trs.util.XML.loadXML('<post-data></post-data>');
		var element = myDoc.documentElement;
		for(var i=0;i<_arrCombined.length;i++){
			var eleTmp = myDoc.createElement("method");
			eleTmp.setAttribute("type",_arrCombined[i]["methodName"]);
			eleTmp.appendChild(myDoc.createTextNode(_arrCombined[i]["serviceName"]));
			element.appendChild(eleTmp);
			eleTmp = myDoc.createElement("parameters");
			element.appendChild(eleTmp);
			com.trs.util.JSON.parseJson2Element(myDoc,eleTmp,_arrCombined[i]["params"]);
		}
		return new com.trs.ajax.RequestWithData(_sUrl,_oOptions,myDoc);
	},
	/**
	 * 需要进行重载
	 */
	_buildXML : function(_sServiceName,_sMethodName,_sXmlParams){
		var xml='<post-data>'+
					'\n\t<method type="'+_sMethodName+'">'+
					 _sServiceName+
					'</method>'+
					'\n\t<parameters>\n'+
						_sXmlParams+
					'\n\t</parameters>'+
				'</post-data>';
		return xml;
	},
	_buildXml : function(_sServiceName,_sMethodName,_oPostData){
		var myDoc = com.trs.util.XML.loadXML('<post-data>'+
					'<method type="'+_sMethodName+'">'+
					 _sServiceName+
					'</method>'+
					'<parameters>'+
					'</parameters>'+
				'</post-data>');
		var parameters = myDoc.getElementsByTagName("parameters")[0];
		com.trs.util.JSON.parseJson2Element(myDoc,parameters,_oPostData);
		return myDoc;
	}
};
ClassName(com.trs.ajaxframe.Action , 'ajaxframe.Action');
com.trs.ajaxframe.URLAction = {
	_base : function(_sUrl,_sServiceName,_oPostData,_oOptions,_sMethodName){
		_sUrl=(_sUrl.indexOf('?')!=-1)?(_sUrl+'&'):(_sUrl+'?');
		if(_oPostData==null){
			_sUrl+=this._buildUrl(_sServiceName,_sMethodName,null);
		}
		else if(typeof _oPostData=='string'){
			_sUrl+=this._buildUrl(_sServiceName,_sMethodName,_oPostData);
		}
		else{
			_sUrl+=this._buildUrl(_sServiceName,_sMethodName,com.trs.util.JSON.parseJsonToParams(_oPostData));
		}
		return new com.trs.ajax.Request(_sUrl,_oOptions);
	},
	get : function(_sUrl,_sServiceName,_oPostData,_oOptions){
		return this._base(_sUrl,_sServiceName,_oPostData,_oOptions,'get');
	},
	findByIds : function(_sUrl,_sServiceName,_sParams,_oOptions){
		return this._base(_sUrl,_sServiceName,_sParams,_oOptions,'findbyids');
	},
	post : function(_sUrl,_sServiceName,_oPostData,_oOptions){
		return this._base(_sUrl,_sServiceName,_oPostData,_oOptions,'post');
	},
	save : function(_sUrl,_sServiceName,_oPostData,_oOptions){
		return this._base(_sUrl,_sServiceName,_oPostData,_oOptions,'save');
	},
	_delete : function(_sUrl,_sServiceName,_oPostData,_oOptions){
		return this._base(_sUrl,_sServiceName,_oPostData,_oOptions,'delete');
	},
	call : function(_sUrl,_sServiceName,_sMethodName,_oPostData,_oOptions){
		return this._base(_sUrl,_sServiceName,_oPostData,_oOptions,_sMethodName);
	},
	/**
	 * 需要进行重载
	 */
	_buildUrl : function(_sServiceName,_sMethodName,_sParams){
		var sUrl='servicename='+_sServiceName+'&methodname='+_sMethodName;
		if(_sParams) sUrl+='&'+_sParams;
		return sUrl;
	}
};
ClassName(com.trs.ajaxframe.URLAction , 'ajaxframe.URLAction');
com.trs.ajaxframe.LocalAction = {
	_base : function(_sUrl,_sServiceName,_oPostData,_oOptions,method){
		var xml=null;
		try
		{
			xml=com.trs.util.JSON.parseJsonToXML('',_oPostData,'');
			com.trs.util.XML.writeXML(_sUrl,xml);
		}catch(err)
		{
			(_oOptions.onFailure||Prototype.emptyFunction)({responseText:xml,responseXml:null},_oOptions['returnValue']);
		}
		(_oOptions.onSuccess||Prototype.emptyFunction)({responseText:xml,responseXml:null},_oOptions['returnValue']);
	},
	insert : function(_sUrl,_sServiceName,_oPostData,_oOptions){
		return this._base(_sUrl,_sServiceName,_oPostData,_oOptions,'Input');
	},
	save : function(_sUrl,_sServiceName,_oPostData,_oOptions){
		return this._base(_sUrl,_sServiceName,_oPostData,_oOptions,'Save');
	},
	_delete : function(_sUrl,_sServiceName,_oPostData,_oOptions){
		return this._base(_sUrl,_sServiceName,_oPostData,_oOptions,'Delete');
	}
};
ClassName(com.trs.ajaxframe.LocalAction , 'ajaxframe.LocalAction');