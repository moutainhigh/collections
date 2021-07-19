$package('com.trs.web2frame');
com.trs.web2frame.AjaxRequest = Class.create('web2frame.AjaxRequest');
com.trs.web2frame.AjaxRequest.prototype = {
	initialize: function(url,options) {
		options.parameters = this.parameters(options.parameters);
		options.method = (options.method)?options.method.toLowerCase():'get';
		if(options.isLocal){
			var _onSuccess=options['onSuccess'] || Prototype.emptyFunction;
			Object.extend(options,{
				onSuccess:function(transport, json){
					try{
						if(_IE&&transport.status==0){
							var text	= transport.responseText;
							if(text.indexOf('<?xml')==0){
								text		= text.replace(/<\?xml[^>]*\?>(\n)?/m,'');
							}
							var xml		= transport.responseXML;
							xml.loadXML(text);
						}
					}catch(err){}
					(_onSuccess)(transport, json);
				}
			});
		}
		var r = new Ajax.Request(url,options);
		return r;
	},
	parameters: function(parameter){
		parameter = parameter||'';
		if (Array.isArray(parameter)){
			var params=[];
			for(var i=0;i<parameter.length;i+=2){
				params.push(parameter[i+1]+'='+encodeURIComponent(parameter[i+1]));
			}
			return params.join('&');
		}
		else if (String.isString(parameter)||Boolean.isBoolean(parameter)||Number.isNumber(parameter)){
			return parameter;
		}
		else{
			var params=[];
			for(param in parameter){
				params.push(param+'='+encodeURIComponent(parameter[param]));
			}
			return params.join('&');
		}
	}
};
/**
 * 扩展自prototype,增加onCreate方法和注册缺省的onException
 */
Object.extend(Ajax.Request.prototype,{
	initialize: function(url, options){
		try {
			(options['onCreate'] || Prototype.emptyFunction)();
		} catch (e) {}
		if(!options.onException){
			options.onException = window.DefaultAjaxException || function(a, err){
				alert('失败:\n------\n['+url+']\n------\n'+getErrorStack(err));
			}
		}
		this.transport = Ajax.getTransport();
		this.setOptions(options);
		this.createTime = new Date().getTime();
		this.url = url;
		if(this.url.length>255){
//			alert("URL已经超出255个字节,建议使用POST提交数据");
//			this.url = this.url.substring(0,255);
		}
		this.request(url);
	},
	evalJson : function(){
		var transport = this.transport;
		if(this.header('ReturnJson')=='true'){
			return com.trs.util.JSON.eval(transport.responseText);
		}
		if(this.header('Content-Type').indexOf('/html')!=-1){
			return null;
		}
		try{
			return com.trs.util.JSON.parseXml(transport.responseXML);
		}catch(err){
			if(this.header('Content-Type').indexOf('/xml')!=-1){
				return com.trs.util.JSON.parseXml(
					com.trs.util.XML.loadXML(transport.responseText));
			}
		}
		return null;
	},
	respondToReadyState: function(readyState) {
		var event = Ajax.Request.Events[readyState];
		var transport = this.transport, json = null;//this.evalJSON();

		if (event == 'Complete') {
			this.completeTime = new Date().getTime();
			var isNotLogin = this.header('TRSNotLogin');
			if(isNotLogin=='true'){
				var bBubble = (window.DefaultNotLogin||Prototype.emptyFunction)();
				(this.options['onFailure']||Prototype.emptyFunction)(transport, json, this);
				if(bBubble===false) return;
			}
			var isError = this.header('TRSException');
			json = this.evalJson();
			if(this.responseIsSuccess()&&isError!='true'){
				(this.options['onSuccess']||Prototype.emptyFunction)(transport, json, this);
			}
			else{
				if (isError=='true'||this.transport.status==500){
					(this.options['on500']||window.DefaultAjax500CallBack||Prototype.emptyFunction)(transport, json, this);
				}
				else{
					(this.options['onFailure']||window.DefaultAjaxFailureCallBack||Prototype.emptyFunction)(transport, json, this);
				}
			}

		}
		(this.options['on' + event] || Prototype.emptyFunction)(transport, json);
		Ajax.Responders.dispatch('on' + event, this, transport, json);
		/* Avoid memory leak in MSIE: clean up the oncomplete event handler */
		if (event == 'Complete'){
			this.transport.onreadystatechange = Prototype.emptyFunction;
			try{
				delete this.transport.responseXML;
				delete this.transport.responseText;
				delete this.transport;
				this.transport=null;
			}catch(err){
			}
			if(this.options['__call_back__']){
				this.options['__call_back__'](this);
			}
		}
	}
});