// define for cb
Object.extend(PageContext, {
	loadPage : function(){
		resetDisplay();
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		oHelper.Call('wcm6_user', 'getOnlineUsers', {Pagesize: -1}, false, function(_trans, _json){
			sValue = TempEvaler.evaluateTemplater('template_onliners', _json, {});
			Element.update($('onliners'), sValue)

			Element.hide('divWaiting');
			Element.show('onliners');
		}.bind(this));
	},
	onSendMsg : function(_nUserId, _sUserName){
		if(this.m_oShowDialog == null) {
			TRSCrashBoard.setMaskable(true);
			this.m_oShowDialog = TRSDialogContainer.register('Send_Msg', '发送在线短消息', 
				'./communications/message_sending.html', '580', '380');
		}
		var positions = Position.getAbsolutePositionInTop(Event.element(Event.findEvent()));
		closeframe();
		TRSDialogContainer.display('Send_Msg', {
			uid: _nUserId,
			uname: _sUserName
		}, positions[0] - 580, positions[1] + 15);
	}
});
function resetDisplay(){
	Element.hide('onliners');
	Element.show('divWaiting');
}
function init() {
	PageContext.loadPage();
}

function closeframe(){
	if (window.parent){
		window.parent.notifyParent2CloseMe(document.FRAME_NAME);
	}
	resetDisplay();
}

Event.observe(window, 'unload', function(){
	//$destroy(PageContext);
} , false);

//button init....
Event.observe(window, 'load', function(){
	new $WCMButton({
		ButtonType	: $ButtonType.REFRESH,
		Action		: 'PageContext.loadPage()',
		Container	: 'ButtonContainer'
	}).loadButton();
	new $WCMButton({
		ButtonType	: $ButtonType.CLOSE,
		Action		: 'closeframe()',
		Container	: 'ButtonContainer'
	}).loadButton();
});