Ext.ns('com.trs.util');
com.trs.util.CAjaxCaller = function(config){
	this.addEvents('beforesave', 'save', 'aftersave');
}
Ext.extend(com.trs.util.CAjaxCaller, com.trs.util.Observable, {
	push : function(sServiceId, sMethodName, sFormName){
		this.aCombine = this.aCombine||[];
		this.aCombine.push(this.oHelper.Combine(sServiceId, sMethodName, sFormName));		
	},
	valid : function(){
	},
	makePostData : function(){
	},
	onSuccess : function(){
	},
	beforeSave : function(){
		if(this.valid() === false) return false;
		return this.fireEvent('beforesave');
	},
	saveByService : function(sServiceId, sMethodName, sFormName){
		if(this.beforeSave() === false) return;
		this.aCombine = [];
		this.oHelper = new com.trs.web2frame.BasicDataHelper();
		this.makePostData();
		this.fireEvent('save');
		this.push.apply(this, arguments);
		var caller = this;
		this.oHelper.MultiCall(this.aCombine, function(transport, json){
			caller.afterSave(transport, json);
		}, this.onFailure);
	},
	saveByJspService : function(sUrl, sServiceId, sMethodName, sFormName){
		if(this.beforeSave() === false) return;
		this.aCombine = [];
		this.oHelper = new com.trs.web2frame.BasicDataHelper();
		this.makePostData();
		this.fireEvent('save');
		this.push.call(this, sServiceId, sMethodName, sFormName);
		var caller = this;
		this.oHelper.JspMultiCall(sUrl, this.aCombine, function(transport, json){
			caller.afterSave(transport, json);
		}, this.onFailure);				
	},
	saveByJsp : function(sUrl, sFormName, sPostType){
		if(this.beforeSave() === false) return;
		this.makePostData();
		this.fireEvent('save');
		var caller = this;
		var jsonData = com.trs.web2frame.PostData.form(sFormName, function(k){return k;});
		new Ajax.Request(sUrl, {
			method : sPostType,
			parameters : $toQueryStr(jsonData), 
			onSuccess : function(transport, json){
				caller.afterSave(transport, json);
			},
			onFailure : this.onFailure
		});				
	},
	save : function(sFormName){
		var dom = $(sFormName);
		var sUrl = dom.getAttribute('action');
		var sServiceId = dom.getAttribute('serviceId');
		var sMethodName = dom.getAttribute('methodName');
		if(sUrl && sServiceId){
			this.saveByJspService(sUrl, sServiceId, sMethodName, sFormName);
			return;
		}
		if(sUrl){
			this.saveByJsp(sUrl, sFormName, dom.getAttribute('method') || 'get');
			return;
		}
		this.saveByService(sServiceId, sMethodName, sFormName);
	},
	afterSave : function(transport, json){
		this.onSuccess(transport, json);
		this.fireEvent('aftersave', transport, json);
	}
});

var AjaxCaller = new com.trs.util.CAjaxCaller();