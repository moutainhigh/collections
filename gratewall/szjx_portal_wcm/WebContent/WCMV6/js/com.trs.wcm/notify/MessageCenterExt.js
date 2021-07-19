$package('com.trs.wcm.notify');

var MC_ATTR_MOTIFY = 1, MC_ADDEDIT = 2, MC_DELETE = 3;
var MC_CHANNEL_MOVE = 4, MC_DOC_MOVE = 5

$import('com.trs.wcm.notify.AbstractListener');

Object.extend(com.trs.wcm.MessageCenter.prototype, {
	notify : function(_flag, _params){ // 默认提供的为int, 如果为string表示扩展的
		var oListener = ListenerFactory.make(_flag);
		if(oListener != null) {
			oListener.doNotify(_params);
		}
	}
});

var ListenerFactory = {};
ListenerFactory.make = function(_flag){
	if(_flag == null) {
		alert('[MessageCenterExt]需要传入刷新事件监听器构造的参数！');
		return null;
	}

	var sListenerName = null;
	if(typeof(_flag) == 'number') {
		sListenerName = ListenerFactory.__getDefinedListenerName(_flag);
	}else if(typeof(_flag) == 'string') {
		//TODO
		sListenerName = _flag.trim();
	}else{
		alert('[MessageCenterExt]无效的刷新事件监听器构造的参数类型！');
		return null;
	}
	var result = 'result = new ' + sListenerName + '();';
	eval(result);
	if(result == null) {
		alert('[MessageCenterExt]无法创建刷新事件监听器[' + sListenerName + ']！');
	}
	return result;
}
ListenerFactory.__getDefinedListenerName = function(_nFlag){
	if(_nFlag > 5 || _nFlag < 0) {
		alert('无法处理传入的参数[' + _nFlag + ']所对应的刷新事件监听器！');
		return -1;
	}
	if(this.m_hListeners == null) {
		this.m_hListeners = [];
		this.m_hListeners.push('AttrModify', 'AddEdit', 'Delete', 'ChannelMove', 'DocMove');
	}

	return this.m_hListeners[_nFlag - 1] + 'Listener';
}