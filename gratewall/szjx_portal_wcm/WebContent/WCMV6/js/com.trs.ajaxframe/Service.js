$package('com.trs.ajaxframe');

com.trs.ajaxframe.Service = Class.create('ajaxframe.Service');
com.trs.ajaxframe.Service.prototype = {
	initialize : function() {
		this.xml	= '';
		this.url	= '';
		this['action']={};
	},
	attachEvent:function(_sAction,_sEvent,_fObserver){
		_sEvent		= _sEvent.toLowerCase().trim();
		_sAction	= _sAction.toLowerCase().trim();
		switch(_sEvent){
			case 'success':
				_sEvent='onSuccess';
				break;
			case 'failure':
				_sEvent='onFailure';
				break;
			case '500':
				_sEvent='on500';
				break;
		}
		this[_sAction][_sEvent]	= _fObserver;
	},
	detachEvent : function(_sAction,_sEvent){
		_sEvent=_sEvent.toLowerCase().trim();
		_sAction=_sAction.toLowerCase().trim();
		switch(_sEvent){
			case 'success':
				_sEvent='onSuccess';
				break;
			case 'failure':
				_sEvent='onFailure';
				break;
			case '500':
				_sEvent='on500';
				break;
		}
		this[_sAction][_sEvent]	= undefined;
	},
	setUrl : function(_sUrl){
		this['url']		= _sUrl;
	},
	setServiceName : function(_sServiceName){
		this['serviceName'] = _sServiceName;
	}
};