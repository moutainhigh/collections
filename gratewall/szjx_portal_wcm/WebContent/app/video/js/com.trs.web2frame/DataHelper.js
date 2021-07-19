$package('com.trs.web2frame');

$import('com.trs.util.JSON');
$import('com.trs.util.XML');
$import('com.trs.web2frame.PostData');
$import('com.trs.web2frame.AjaxRequest');

com.trs.web2frame.BasicDataHelper = Class.create('web2frame.BasicDataHelper');
com.trs.web2frame.BasicDataHelper.prototype = {
	WebService : '/wcm/center.do',
	initialize : function(){
		this.call = this.Call;
		this.combine = this.Combine;
		this.multiCall = this.MultiCall;
	},
	_BuildXml : function(_sServiceName,_sMethodName,_oPostData){
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
	},
	_BuildUrl : function(_sServiceId,_sMethod,_sParams){
		if(_sMethod == 'get'){
			_sMethod = 'query';
		}
		else if(_sMethod=='findbyids'){
			if(!_sParams.match(/objectids=/ig)){
				_sParams = 'objectids='+_sParams;
			}
		}
		var retUrlQuery = 'serviceid='+_sServiceId+'&methodname='+_sMethod;
		if(_sParams) retUrlQuery += '&' + _sParams;
		return retUrlQuery;
	},
	_DoPost : function(_sUrl,_sServiceId,_sMethod,_oOptions){
		var myDoc = this._BuildXml(_sServiceId,_sMethod,_oOptions["postBody"]);
		_oOptions["postBody"] = myDoc;
		return new com.trs.web2frame.AjaxRequest(_sUrl,_oOptions);
	},
	_DoGet : function(_sUrl,_sServiceId,_sMethod,_oOptions){
		_sUrl = (_sUrl.indexOf('?')!=-1)?(_sUrl+'&'):(_sUrl+'?');
		var sParams = _oOptions["parameters"];
		_sUrl += this._BuildUrl(_sServiceId,_sMethod,sParams);
		_oOptions["parameters"] = '';
		return new com.trs.web2frame.AjaxRequest(_sUrl,_oOptions);
	},
	_DoMultiRequest : function(_sUrl , _arrCombined , _oOptions){
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
		_oOptions["postBody"] = myDoc;
		return new com.trs.web2frame.AjaxRequest(_sUrl,_oOptions);
	},
	_BindEvents : function(_fSuccess,_f500,_fFailure){
		var _oOptions = {};
		if(_fSuccess)_oOptions['onSuccess'] = _fSuccess;
		if(_f500)_oOptions['on500'] = _f500;
		if(_fFailure)_oOptions['onFailure'] = _fFailure;
		return _oOptions;
	},
	_MakePostData : function(_oPost){
		var postData = null;
		if(typeof _oPost=='function'){
			throw '参数传入错误:_oPost不能为Function类型.';
		}
		if(String.isString(_oPost)){
			if($(_oPost)==null){
				throw '参数传入错误:不存在id为'+_oPost+'的form对象.';
			}
			postData = com.trs.web2frame.PostData.form(_oPost);
		}
		else if(Object.isObject(_oPost)){
			postData = com.trs.web2frame.PostData.params(_oPost);
		}
		else if(_oPost!=null){
			postData = com.trs.web2frame.PostData.form(_oPost);
		}
		return postData;
	},
	_Request : function(_oDataSource,_oPost,_bSend){
		var sMethod		= _oDataSource["method"];
		if(_bSend!=null&&!Boolean.isBoolean(_bSend)){
			throw '参数传入错误:_bSend只能为true,false或null.';
		}
		sMethod = (sMethod)?sMethod.toLowerCase():'call';
		var sUrl = _oDataSource["url"];
		var sServiceId = _oDataSource["serviceId"];
		var oOptions = _oDataSource["options"];
		if(sMethod == '__multicall__'){
			return this._DoMultiRequest(sUrl,_oPost,Object.extend(oOptions,{method:'post'}));
		}
		_oPost = this._MakePostData(_oPost);
		if(sMethod == '__jsp__'){
			Object.extend(oOptions,{
				parameters : com.trs.util.JSON.parseJsonToParams(_oPost),
				method : (_bSend)?'POST':'GET',
				contentType : 'application/x-www-form-urlencoded'
			});
			return new com.trs.web2frame.AjaxRequest(sUrl,oOptions);
		}
		if(_bSend){
			return this._DoPost(sUrl,sServiceId,sMethod,Object.extend(oOptions,{ method : 'Post',postBody : _oPost }));
		}
		Object.extend(oOptions,{
			parameters : com.trs.util.JSON.parseJsonToParams(_oPost),
			method : 'GET'
		});
		return this._DoGet(sUrl,sServiceId,sMethod,oOptions);
	},
	LocalCall : function(_sServiceId,_sMethodName,_oPost,_bSend,_fSuccess,_f500,_fFailure){
		var sUrl = com.trs.util.Common.BASE + '../localxml/'+_sServiceId+'/'+_sMethodName+'.xml';
		if(!_fFailure){
			_fFailure = function(transport){
				var abc = document.createElement("IMG");
				abc.src = oDataSource.url;
				abc.style.display = 'none';
				document.body.appendChild(abc);
				$alert("Error "+transport.status+":<br>"+abc.src);
				document.body.removeChild(abc);
			}
		}
		var oOptions = this._BindEvents(_fSuccess,_f500,_fFailure);
		var oDataSource = {serviceId:_sServiceId,url:sUrl,method:_sMethodName,options:oOptions};
		this._Request(oDataSource,_oPost,_bSend);
	},
	Call : function(_sServiceId,_sMethodName,_oPost,_bSend,_fSuccess,_f500,_fFailure){
		var oOptions = this._BindEvents(_fSuccess,_f500,_fFailure);
		var oDataSource = {serviceId:_sServiceId,url:this.WebService,method:_sMethodName,options:oOptions};
		this._Request(oDataSource,_oPost,_bSend);
	},
	Combine : function(_sServiceId,_sMethodName,_oPost){
		_oPost = this._MakePostData(_oPost);
		return {"serviceName":_sServiceId,"methodName":_sMethodName,"params":_oPost};
	},
	MultiCall : function(_arrCombined,_fSuccess,_f500,_fFailure){
		var oOptions = this._BindEvents(_fSuccess,_f500,_fFailure);
		var oDataSource = {serviceId:'',url:this.WebService,method:'__multicall__',options:oOptions};
		this._Request(oDataSource,_arrCombined);
	},
	JspRequest : function(_sUrl,_oPost,_bSend,_fSuccess,_f500,_fFailure){
		var oOptions = this._BindEvents(_fSuccess,_f500,_fFailure);
		var oDataSource = {serviceId:'',url:_sUrl,method:'__jsp__',options:oOptions};
		this._Request(oDataSource,_oPost,_bSend);
	}
};
var DataHelper = com.trs.web2frame.DataHelper = Class.create('web2frame.DataHelper');
Object.extend(com.trs.web2frame.DataHelper.prototype,com.trs.web2frame.BasicDataHelper.prototype);
Object.extend(com.trs.web2frame.DataHelper.prototype,{
	initialize : function(_sServiceId){
		if(!_sServiceId){
			this.call = this.Call;
		}
		else{
			var caller = this;
			this.call = function(_sMethodName,_oPost,_bSend,_fSuccess,_f500,_fFailure){
				caller.Call(_sServiceId,_sMethodName,_oPost,_bSend,_fSuccess,_f500,_fFailure);
			}
			this.save = function(_oPost,_bSend,_fSuccess,_f500,_fFailure){
				caller.call('save',_oPost,_bSend,_fSuccess,_f500,_fFailure);
			}
			this.findByIds = function(_oPost,_bSend,_fSuccess,_f500,_fFailure){
				caller.call('findbyids',_oPost,_bSend,_fSuccess,_f500,_fFailure);
			}
			this.get = function(_oPost,_bSend,_fSuccess,_f500,_fFailure){
				caller.call('get',_oPost,_bSend,_fSuccess,_f500,_fFailure);
			}
			this.remove = this._delete = this["delete"] = function(_oPost,_bSend,_fSuccess,_f500,_fFailure){
				caller.call('delete',_oPost,_bSend,_fSuccess,_f500,_fFailure);
			}
		}
		this.combine = this.Combine;
		this.multiCall = this.MultiCall;
	}
});

var BasicDataHelper = new com.trs.web2frame.BasicDataHelper();