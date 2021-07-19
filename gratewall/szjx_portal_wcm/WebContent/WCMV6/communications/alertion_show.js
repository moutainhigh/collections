// define for cb
Object.extend(PageContext, {
	loadDetail : function(){
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		oHelper.Call('wcm6_message', 'findbyid',
			{objectid: PageContext.id}, false, function(_trans, _json){
			var msg = $v(_json, 'message');
			$('msg_title').innerHTML = $trans2Html(msg.TITLE.NODEVALUE);
			$('msg_posttime').innerHTML = msg.CRTIME.NODEVALUE;

			var msgType = msg.MSGTYPE.NODEVALUE;
			$('hdMsgType').value = msgType;
			var typedInfo = this.__getMsgTypedInfo(msgType);
			$('msg_type').innerHTML = typedInfo['type_name'];
			$('msg_list_lnk').innerHTML = typedInfo['link_tip'];
			//$('msg_status').innerHTML = this.__getMsgStatusName(msg.FLAG.NODEVALUE);
			$('msg_operator').innerHTML = msg.CRUSER.NODEVALUE;
			$('msg_content').innerHTML = msg.MBODY.NODEVALUE;//$trans2Html(msg.MBODY.NODEVALUE);
			PageContext.m_sCurrMsgBody = msg.MBODY.NODEVALUE;

			$('spUserDesc').innerHTML = typedInfo['user_desc'];

			// set flag TODO 
			//if((msg.FLAG.NODEVALUE + '').trim() == '0') {
				var oHelper = new com.trs.web2frame.BasicDataHelper();
				oHelper.Call('wcm6_message', 'setReadFlag',
					{Readed: true, objectids: msg.MSGID.NODEVALUE}, false); // do nothin' with this calling
			//}

			$('msg_reply_lnk').innerHTML = (msgType == '2') ? '以短消息转发' : '以短消息回复';
			Element.show('msg_reply_lnk');
		}.bind(this));
	},
	refresh : function(_nIndex){
		if(_nIndex <= 0) {
			return;
		}
		if(_nIndex > PageContext.msgs.length) {
			return;
		}
		//else
		$('items_index').innerHTML = _nIndex;
		PageContext.index = _nIndex;
		this.id = PageContext.msgs[_nIndex - 1].MSGID.NODEVALUE;
		this.index = _nIndex;
		this.loadDetail();
		
		//ge gfc add @ 2007-7-27 9:23 改变上一条、下一条的可用状态
		this.__initNav();
	},
	initNav : function(){
		$('items_index').innerHTML = this.index;
		$('items_totle').innerHTML = this.msgs.length;

		this.__initNav();
	},
	__initNav : function(){
		$('nav_pre').className  = (this.index == 1) ? 'nav_go_pre_disabled' : 'nav_go_pre';
		$('nav_next').className = (this.index == this.msgs.length) ? 'nav_go_next_disabled' : 'nav_go_next';
	},
	preMsg : function(){
		//收取上一条信息
		var nIndex = this.getCurrIndex();
		this.refresh(--nIndex)
	},
	nextMsg : function(){
		//收取下一条信息
		var nIndex = this.getCurrIndex();
		this.refresh(++nIndex)
	},
	getCurrIndex : function(){
		return parseInt(PageContext.index);
	},
	__getMsgStatusName : function(_type){
		if(this.m_arrMsgStatusName == null) {
			this.m_arrMsgStatusName = ['未读', '已读'];
		}
		_type = parseInt(_type);
		if(isNaN(_type) || _type < 0 ||  _type > 1) {
			return '';
		}
		return this.m_arrMsgStatusName[_type];
	},
	__getMsgTypedInfo : function(_type){
		if(this.m_arrMsgTypedInfo == null) {
			this.m_arrMsgTypedInfo = [
			{type_name: '沟通',		user_desc: '发送人',	link_tip: '转到短消息列表'},
			{type_name: '发布',		user_desc: '发送人',	link_tip: '转到发布监控页面'},
			{type_name: '工作流',	user_desc: '来自于',	link_tip: '转到短消息列表'}];
		}
		_type = parseInt(_type);
		if(isNaN(_type) || _type < 1 ||  _type > 3) {
			return '';
		}
		return this.m_arrMsgTypedInfo[_type - 1];
	},
	__getMsgUserDesc : function(_type){
		if(this.m_arrMsgUserDesc == null) {
			this.m_arrMsgUserDesc = ['发送人', '发送人', '来自于'];
		}
		_type = parseInt(_type);
		if(isNaN(_type) || _type < 1 ||  _type > 3) {
			return '';
		}
		return this.m_arrMsgUserDesc[_type - 1];
	},
	showMsgList : function(){
		var sType = $('hdMsgType').value;
		if(sType == '1' || sType == '3') {
			top.MenuOperates.showSMmList({
				
			});
			//$openCentralWin('../communications/short_msg_list.html', '在线短消息列表', false, true);
			return;
		}else if(sType == '2') {
			if((top.actualTop || top).MenuOperates) {
				(top.actualTop || top).MenuOperates.skipTo({
					Path: 'publicMonitor,0'
				});
				return;
			}
		}
		//TODO
		alert('尚未实现该列表！');
	},
	//以短消息的方式回复
	replyBySM : function(){
		var params = {
			raw_msg: ShortMsgMgr.getQuoteMsg(PageContext.m_sCurrMsgBody || '')
		};
		var sType = $('hdMsgType').value;
		if(sType == '2') { // 发布信息，只能转发
			Object.extend(params, {
				raw_title: ShortMsgMgr.getForwardTitle($('msg_title').innerHTML)
			});
		}else{ // 可以回复
			Object.extend(params, {
				uname: $('msg_operator').innerHTML,
				raw_title: ShortMsgMgr.getReplyTitle($('msg_title').innerHTML)
			});	
		}
		if(this.m_oShowDialog == null) {
			TRSCrashBoard.setMaskable(true);
			this.m_oShowDialog = TRSDialogContainer.register('Send_Msg', '发送在线短消息', 
				'./communications/message_sending.html', '580', '380');
		}
		this.m_oShowDialog.display(params);		
	}
});
function init(_args) {
	PageContext.id = _args.id;
	PageContext.msgs = _args.msgs;
	PageContext.index = _args.index;
	PageContext.loadDetail();
	PageContext.initNav();
}

function closeframe(){
	if (window.parent){
		window.parent.notifyParent2CloseMe(document.FRAME_NAME);
	}
}

Event.observe(window, 'unload', function(){
	//$destroy(PageContext);
} , false);