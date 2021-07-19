//短消息操作信息和Mgr定义
Ext.ns('wcm.domain.MessageMgr');
(function(){
	var m_oMgr = wcm.domain.MessageMgr;
	serviceId = 'wcm6_message';
	function getHelper(){
		return new com.trs.web2frame.BasicDataHelper();
	}
	function sendMsg(_params,event,addOrEdit){
		var sUrl = WCMConstants.WCM6_PATH + 'message/message_sending.html';
		wcm.CrashBoarder.get('Send_Msg').show({
			title :  wcm.LANG['MESSAGE_8'] || '发送在线短消息',
			src : sUrl,
			width: '700px',
			height: '500px',
			params : _params,
			maskable : true,
			callback : function(_objId){
				CMSObj[addOrEdit==1 ? 'afteradd' : 'afteredit'](event)(_objId);
			}
		}); 
	}
	function reSend(event, _bForward){
		var sOptionDesc = _bForward ? (wcm.LANG['MESSAGE_9'] || '转发') : (wcm.LANG['MESSAGE_10'] ||'回复');
		var objs = event.getObjs();
		var _arIds = event.getIds();
		if(!m_oMgr.checkSubmit(_arIds, sOptionDesc)) {
			return;
		}
		if(_arIds.length != 1) {
			Ext.Msg.$fail(String.format("只能对一条信息进行{0}",sOptionDesc));
			return;
		}
		var nCurrId = _arIds[0];
		var obj = objs.getAt(0);
		var params = null; 
		params = {
			uname: _bForward ? '' :	obj.getPropertyAsString('crUser'),
			raw_title: _bForward ? getForwardTitle(obj.getPropertyAsString('title')) 
							: getReplyTitle(obj.getPropertyAsString('title')),
			raw_msg: getQuoteMsg(obj.getPropertyAsString('mbody'))
		};
		if(params == null) {
			return;
		}
		if(params.uname.toLowerCase() == "system"){
			Ext.Msg.warn(wcm.LANG['MESSAGE_55'] || '不允许给system用户发送短消息');
			return;
		}
		//else
		sendMsg(params,event,0);
	}
	function getReplyTitle(_sRawTitle){
		return 'Re: ' + (_sRawTitle || '');
	}
	function getForwardTitle(_sRawTitle){
		return 'Fw: ' + (_sRawTitle || '');
	}
	function getQuoteMsg(_sRawMsg){
		return '<br>' + '----- Original Message -----<br>' + (_sRawMsg || '');
	}
	Ext.apply(wcm.domain.MessageMgr, {
		/**
		 * @param : wcm.CMSObjEvent event
		 */
		newMsg : function(event){
			var params = {};
			sendMsg(params,event,1);
		},
		forward : function(event){
			reSend(event, true,0);
		},
		reply : function(event){
			reSend(event, false,0);
		},
		'delete' : function(event){
			_arIds = event.getIds();
			if(!m_oMgr.checkSubmit(_arIds, wcm.LANG['MESSAGE_12'] || '删除')) {
				return;
			}
			var nCount = _arIds.length;
			if(typeof(_arIds) == 'string') {
				nCount = _arIds.split(',').length;
			}
			var params = {
				ObjectIds: _arIds, 
				drop: true
			};
			if (confirm(String.format(wcm.LANG.MESSAGE_13||('确实要将这{0}个短消息删除吗?'),nCount))){
				//$beginSimplePB('正在删除短消息..', 2);
				BasicDataHelper.call(serviceId, 'delete', params, false, function(){
					//$endSimplePB();
					//$MsgCenter.$main().afteredit();
					CMSObj.afterdelete(event)();
				});
			}
		},
		signAsReaded : function(event){
			var _arIds = event.getIds();
			if(!m_oMgr.checkSubmit(_arIds, wcm.LANG['MESSAGE_18'] || '设置为已读')) {
				return;
			}
			BasicDataHelper.call(serviceId, 'setReadFlag', {Readed: true, ObjectIds: _arIds}, false, function(){
				CMSObj.afteredit(event)();
			});		
		},
		clearInbox : function(event){
			var context = event.getContext();
			if(context.RecordNum <= 0){
				Ext.Msg.$fail(wcm.LANG['MESSAGE_19'] || '收件箱中没有任何记录！');
				return;
			}
			Ext.Msg.confirm((wcm.LANG['MESSAGE_20'] || '确实要清空收件箱吗？'),{
				yes : function(){
						//$beginSimplePB('正在清空发件箱..', 2);
						BasicDataHelper.call(serviceId, 'clearInbox', null, false, function(){
						//$endSimplePB();
						$MsgCenter.$main().afteredit();
						//CMSObj.afterdelete(event)();
					});
				}
			});
		},
		clearOutbox : function(event){
			var context = event.getContext();
			if(context.RecordNum <= 0){
				Ext.Msg.$fail(wcm.LANG['MESSAGE_21'] || '发件箱中没有任何记录！');
				return;
			}
			Ext.Msg.confirm((wcm.LANG['MESSAGE_22'] || '确实要清空发件箱吗？'),{
				yes : function(){
						//$beginSimplePB('正在清空发件箱..', 2);
						BasicDataHelper.call(serviceId, 'clearOutbox', null, false, function(){
						//$endSimplePB();
						$MsgCenter.$main().afteredit();
						//CMSObj.afterdelete(event)();
					});
				}
			});
		},
		checkSubmit : function(_arIds, _sOptionName){
			if(_arIds.length == 0) {
				Ext.Msg.$fail(String.format("请选择要{0}的项！",_sOptionName));
				return false;
			}
			return true;
		}
		 
		//type here
	});
})();
(function(){
	var pageObjMgr = wcm.domain.MessageMgr;
	var reg = wcm.SysOpers.register;
	reg({
		key : 'newMsg',
		type : 'myMsgListInRoot',
		isHost : true,
		desc : wcm.LANG['MESSAGE_27'] || '写新短消息',
		rightIndex : -1,
		order : 1,
		fn : pageObjMgr['newMsg'],
		quickKey : 'N'
	});
	reg({
		key : 'signAsReaded',
		type : 'message',
		desc : wcm.LANG['MESSAGE_28'] || '标记为已读',
		rightIndex : -1,
		order : 2,
		fn : pageObjMgr['signAsReaded'],
		isVisible : function(event){
			var context = event.getContext();
			if(context.ReadFlag==0)return true;
			return false;
		}
	});
	reg({
		key : 'reply',
		type : 'message',
		desc : wcm.LANG['MESSAGE_29'] || '回复短消息',
		rightIndex : -1,
		order : 3,
		fn : pageObjMgr['reply'],
		isVisible : function(event){
			var context = event.getContext();
			if(context.ReadFlag==1)return false;
			return true;
		}
	});
	reg({
		key : 'forward',
		type : 'message',
		desc : wcm.LANG['MESSAGE_30'] || '转发短消息',
		rightIndex : -1,
		order : 4,
		fn : pageObjMgr['forward']
	});
	reg({
		key : 'deleteMsg',
		type : 'message',
		desc : wcm.LANG['MESSAGE_31'] || '删除短消息',
		rightIndex : -1,
		order : 5,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'clearInbox',
		type : 'myMsgListInRoot',
		isHost : true,
		desc : wcm.LANG['MESSAGE_32'] || '清空收件箱',
		rightIndex : -1,
		order : 2,
		fn : pageObjMgr['clearInbox'],
		isVisible : function(event){
			var context = event.getContext();
			if(context.ReadFlag==2)return true;
			return false;
		}
	});
	reg({
		key : 'clearOutbox',
		type : 'myMsgListInRoot',
		isHost : true,
		desc : wcm.LANG['MESSAGE_33'] || '清空已发短消息',
		rightIndex : -1,
		order : 2,
		fn : pageObjMgr['clearOutbox'],
		isVisible : function(event){
			var context = event.getContext();
			if(context.ReadFlag==1)return true;
			return false;
		}
	});
	reg({
		key : 'deleteMsg',
		type : 'messages',
		desc : wcm.LANG['MESSAGE_31'] || '删除短消息',
		rightIndex : -1,
		order : 5,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']

	});
	reg({
		key : 'signAsReaded',
		type : 'messages',
		desc : wcm.LANG['MESSAGE_28'] || '标记为已读',
		rightIndex : -1,
		order : 2,
		fn : pageObjMgr['signAsReaded'],
		isVisible : function(event){
			var context = event.getContext();
			if(context.ReadFlag==0)return true;
			return false;
		}
	});

})();