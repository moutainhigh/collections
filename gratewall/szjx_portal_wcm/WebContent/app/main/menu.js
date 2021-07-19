Ext.ns("PageContext");
Object.extend(PageContext, {
	showOnliners : function(){
		var sUrl = WCMConstants.WCM6_PATH + 'message/onliners.jsp';
			wcm.CrashBoarder.get('Onliners_Show').show({
				title :  wcm.LANG['ONLINE_USER_LIST'] || '在线用户列表',
				src : sUrl,
				width: '270px',
				height: '300px',
				params : {},
				maskable : true,
				callback : function(){
				}
		}); 
	},
	notifyAlertions : function(){
		var sParams = [
			'HostType=', WCMConstants.OBJ_TYPE_MYMSGLIST,
			'&SiteType=' + 200,
			'&TabHostType=' + WCMConstants.TAB_HOST_TYPE_MYMSGLIST
		].join('');
		var tabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_MYMSGLIST, PageContext.hasNewMsgs?'message0':'message2');
		if(tabItem==null){
			return false;
		}
		wcm.TabManager.exec(tabItem, sParams);
	},
	logout : function(){
		
	},
	renderItemSelect : function(_type){
		this.m_eBubbleList.hide();
		switch(_type){
			case 0:
				MenuOperates.individuate("msg");
				break;
			case 1:
				$openCentralWin('./communications/short_msg_list.html',wcm.LANG.menu_1000 || '在线短消息列表', false, true);
				break;
			default:
				// TODO
				alert(wcm.LANG.menu_2000 || '尚未实现！');
				break;
		}
		//hideHelpTip($('lnkFirer'));		
	},
	skipToUserInfo : function(){
		gSkipTo({Path:'myInformation,0'});
	}
});
var m_MsgDetecting = function(){
	var timeoutMsgDetect = 0;
	var lastMsgs = '';
	function detect(){
		var msgTypes = [1,2,3];
		var sMsgTypes = msgTypes.join();
		BasicDataHelper.JspRequest('message/message_query_service.jsp', {ReadFlag: 0, MsgTypes: sMsgTypes}, false, function(_trans){
			var result = Ext.result(_trans) || '';
			var results = result.split(";");
			var num = results[0];
			var bHasNewMsgs = num > 0;
			PageContext.hasNewMsgs = bHasNewMsgs;
			$('spAlertionIcon').className = bHasNewMsgs ? 'alertion_active_icon' : 'alertion_deactive_icon';
			$('spAlertionNum').innerHTML = bHasNewMsgs ? '(' + num + ')' : '(0)';
			if(bHasNewMsgs && lastMsgs.indexOf(results[1])==-1 && wcm.MsgSound){
				try{
					wcm.MsgSound.play();
				}catch(err){}
			}
			lastMsgs = results[1];
			timeoutMsgDetect = setTimeout(detect, nLoginTimeoutLimitValue);
		}, null, function(_trans, _json, scope){
			if(_trans.status==12029 || _trans.status==12007){
				(window.NotifySystemError || Ext.emptyFn)(_trans, _json, scope);
			}else if(scope.header('TRSNotLogin')){
				if(confirm(wcm.LANG.menu_3000 || '您登录超时或没有登录本系统，是否关闭本窗口？')){
					if(Ext.isIE7){
						window.open("","_self");//fix ie7
						window.close();
					}else{
						window.opener = null;
						window.close();
					}
				}
			}
			else{
				(window.DefaultAjaxFailureCallBack || Ext.emptyFn)(_trans, _json, scope);
			}
		});
	}
	Event.observe(window, 'load', detect);
};
m_MsgDetecting();