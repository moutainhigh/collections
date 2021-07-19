$import('com.trs.dialog.Dialog');
var ShortMsgMgr = {
	serviceId : 'wcm6_message',
	getHelper : function(_sServceFlag){
		return new com.trs.web2frame.BasicDataHelper();
	},
	refresh : function(){
		PageContext.RefreshList();
	},
	'delete' : function(_arIds, _params){
		if(!this.checkSubmit(_arIds, '删除')) {
			return;
		}
		var nCount = _arIds.length;
		if(typeof(_arIds) == 'string') {
			nCount = _arIds.split(',').length;
		}
		if (confirm('确实要删除' + (nCount == 1 ? '此' : '这 ' + nCount +' 条') + '短消息吗？')){
			$beginSimplePB('正在删除短消息..', 2);
			this.getHelper().call(this.serviceId, 'delete', {ObjectIds: _arIds, drop: true}, false, function(){
				$endSimplePB();
				PageContext.RefreshList();
			});
		}
	},
	setUnreadFlag : function(_arIds, _params){
		if(!this.checkSubmit(_arIds, '设置为未读')) {
			return;
		}
		$beginSimplePB('正在将短消息设置为未读..', 2);
		this.getHelper().call(this.serviceId, 'setReadFlag', {Readed: false, ObjectIds: _arIds}, false, function(){
			$endSimplePB();
			PageContext.RefreshList();
		});		
	},
	signAsReaded : function(_arIds, _params, _bHidePb){
		if(!this.checkSubmit(_arIds, '设置为已读')) {
			return;
		}
		if(_bHidePb != true) {
			$beginSimplePB('正在将短消息设置为已读..', 2);
		}
		this.getHelper().call(this.serviceId, 'setReadFlag', {Readed: true, ObjectIds: _arIds}, false, function(){
			if(_bHidePb != true) {
				$endSimplePB();
				PageContext.RefreshList();
			}
		});		
	},
	clearInbox : function(_arIds, _params){
		var json = PageContext.Data;
		json = $a(json, 'messages.message');
		if(!json || !(json.length > 0)) {
			$fail('收件箱中没有任何纪录！');
			return;
		}
		if (confirm('确实要清空收件箱吗？')){
			$beginSimplePB('正在清空收件箱..', 2);
			this.getHelper().call(this.serviceId, 'clearInbox', null, false, function(){
				$endSimplePB();
				PageContext.RefreshList();
			});	
		}	
	},
	clearOutbox : function(_arIds, _params){
		var json = PageContext.Data;
		json = $a(json, 'messages.message');
		if(!json || !(json.length > 0)) {
			$fail('发件箱中没有任何纪录！');
			return;
		}
		if (confirm('确实要清空发件箱吗？')){
			$beginSimplePB('正在清空发件箱..', 2);
			this.getHelper().call(this.serviceId, 'clearOutbox', null, false, function(){
				$endSimplePB();
				PageContext.RefreshList();
			});	
		}	
	},
	checkSubmit : function(_arIds, _sOptionName){
		if(_arIds.length == 0) {
			$fail('请选择要' + _sOptionName + '的项！');
			return false;
		}
		return true;
	},
	sendMsg : function(_params){
		if(this.m_oShowDialog == null) {
			this.m_oShowDialog = TRSDialogContainer.register('Send_Msg', '发送在线短消息', 
				'../communications/message_sending.html', '580', '380');
		}
		this.m_oShowDialog.display(_params);
	},
	forward : function(_arIds, _params){
		this.reply(_arIds, _params, true);
	},
	reply : function(_arIds, _params, _bForward){
		var sOptionDesc = _bForward ? '转发' : '回复';
		if(!this.checkSubmit(_arIds, sOptionDesc)) {
			return;
		}
		if(window.Grid == null) {
			return;
		}
		
		var json = PageContext.Data;
		json = $a(json, 'messages.message');
		
		if(!json) {
			$fail('不合理的数据！');
			return;
		}
		//else
		if(_arIds.length != 1) {
			$fail('只能对一条信息进行' + sOptionDesc + '！');
			return;
		}
		var nCurrId = _arIds[0];
		var params = null;
		for (var i = 0; i < json.length; i++){
			var elm = json[i];
			if($v(elm, 'MsgId') == nCurrId) {
				params = {
					uname: _bForward ? '' :	$v(elm, 'CRUSER'),
					raw_title: _bForward ? this.getForwardTitle($v(elm, 'TITLE')) 
									: this.getReplyTitle($v(elm, 'TITLE')),
					raw_msg: this.getQuoteMsg($v(elm, 'MBODY'))
				};
				break;
			}
		}
		if(params == null) {
			return;
		}
		//else
		this.sendMsg(params);
	},
	getReplyTitle : function(_sRawTitle){
		return 'Re:' + (_sRawTitle || '');
	},
	getForwardTitle : function(_sRawTitle){
		return 'Fw:' + (_sRawTitle || '');
	},
	getQuoteMsg : function(_sRawMsg){
		return '<br>' + '----- Original Message -----<br>' + (_sRawMsg || '');
	}	
};

var $shortMsgMgr = ShortMsgMgr;
