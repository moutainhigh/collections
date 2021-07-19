Object.extend(PageContext,{
	//是否将数据提供者切换到本地的开关
	isLocal : false,
	//远程服务的相关属性
	ObjectServiceId : 'wcm6_message',
	ObjectMethodName : 'query',
	AbstractParams : {
			//记录一下全局的权限
			'RightValue'	: getParameter('RightValue') || '0000000000000000',
			fieldsToHtml: 'title'
	},//服务所需的参数
	ViewType :  getParameter('ViewType') || 2,
	PageFilters : [
		{ Name:'最新消息',	Type: '0'},
		{ Name:'已收消息',	Type: '2', IsDefault: 'true'   }, 
		{ Name:'已发消息',	Type: '1' }
	],
	PageFilterName : 'ReadFlag',
	//为了使页面具有行为,定义Mgr对象
	ObjectMgr : ShortMsgMgr,

	_doBeforeRefresh : function(){
		if(PageContext.params['ReadFlag'] != null) {
			PageContext.ViewType = PageContext.params['ReadFlag'];
		}
		
		Object.extend(PageContext.params, {
			ReadFlag: PageContext.ViewType, // 默认为“已收”
			MsgTypes: '1,3', //表示"沟通"和"工作流通知"
			DateTimeFormat: 'MM-dd HH:mm'
		});
		this.initDisplay();

		PageContext.setOptionsDisp();
	},

	initDisplay : function(){ // 显示合适的表头信息
		if(PageContext.params['ReadFlag'] == 1) { //已发送
			$('spTimeHeader').innerHTML = '发送时间';
			Element.hide('spCrUserHeader');
			Element.show('spRecieverHeader');
		}else{// 收件箱
			$('spTimeHeader').innerHTML = '到达时间';
			Element.hide('spRecieverHeader');
			Element.show('spCrUserHeader');
		}
	},
	_doBeforeBinding : function(_trans, _json){
		if(PageContext.params['ReadFlag'] != 0) {
			return;
		}
		
		//else
		var unreadMsgs = $a(_json, 'MESSAGES.MESSAGE');
		if(unreadMsgs == null || unreadMsgs.length == 0) {
			return;
		}
		//alert(allMsgs.length + '\n' + unreadMsgs.length)

		for (var i = 0; i < unreadMsgs.length; i++){
			unreadMsgs[i]['UNREADED'] = true;
		}
		//alert(Object.parseSource(_json))//*/
	}
});
Object.extend(PageContext, {
	initOptions : function(){
		//FloatPanel.addCloseCommand('关闭');
		// 绑定按钮点击事件
		var btns = $('divOptions').getElementsByTagName('button');
		for (var i = 0; i < btns.length; i++){
			var eBtn = btns[i];
			var fAction = PageContext.ObjectMgr[eBtn.getAttribute('_action', 2)];
			if(fAction == null) {
				continue;
			}
			Event.observe(eBtn, 'click', function(){
				var caller = this;
				caller.apply(PageContext.ObjectMgr, [Grid.getRowIds(), PageContext.params]);
			}.bind(fAction));

			delete eBtn;
		}
	},
	setOptionsDisp : function(){
		var flag = parseInt(PageContext.params.ReadFlag);
		switch(flag){
			case 0:
				Element.show('spSignAsReaded');
				Element.hide('spClearInbox');
				Element.hide('spClearOutbox');
				Element.show('spReply');
				break;
			case 2:
				Element.hide('spSignAsReaded');
				Element.show('spClearInbox');
				Element.hide('spClearOutbox');
				Element.show('spReply');
				break;
			case 1:
				Element.hide('spSignAsReaded');
				Element.hide('spClearInbox');
				Element.show('spClearOutbox');
				Element.hide('spReply');
				break;
			default:
				break;
		}
	},
	showDetail : function(event, lnk){
		var json = PageContext.Data;
		json = $a(json, 'messages.message');
		
		if(!json) {
			$fail('不合理的数据！');
			return;
		}
		//else
		var rowData = null;
		var rowIndex = lnk.getAttribute('_index', 2);
		if(json.length < rowIndex || (rowData = json[rowIndex - 1]) == null) {
			return;
		}
		var sTitle = $v(rowData, 'title');
		var sDetail = $v(rowData, 'mbody');
		var sTime = $v(rowData, 'crtime');
		var sUser = (PageContext.params['ReadFlag'] == 1) ?
			  '发给[' + $v(rowData, 'Receivers') + ']'
			: '来自[' + $v(rowData, 'cruser') + ']';
		//如果点击的是未读消息，则将其置为已读状态
		var spTitle = lnk.children[0];
		if(spTitle && spTitle.className == 'title_unreaded') {
			window.setTimeout(function(){
				lnk.children[0].className = 'title_normal';
			}, 10);
			$shortMsgMgr.signAsReaded([lnk.getAttribute('_id', 2)], PageContext.params, true);
		}

		var sContent = '<div class="dv_title">' + sTitle
				+ ' (<span class="sp_time">' + sTime + sUser + '</span>)</div>';
		sContent += '<div class="dv_detail">' + sDetail + '</div>';
		showHelpTip(event, sContent, false);	
	}
});
Object.extend(PageContext.PageNav,{
	UnitName : '条',
	TypeName : '短消息'
});

Object.extend(Grid,{
	draggable : false,
	keyD : function(event){//delete
		this._delete(event);
	},
	keyDelete : function(event){//delete
		this._delete(event);
	},
	//==================内部方法========================//
	_hasRight : function(_sOperation){
		return true;
	},

	_delete : function(event){//Trash
		//1.根据页面传入的权限判断是否有权限操作
		if(!this._hasRight("delete")){
			return false;
		}

		//2.获取选中的对象ID,返回一个数组
		var pSelectedIds = this.getRowIds();		
		if( pSelectedIds && pSelectedIds.length>0 ){
			PageContext.ObjectMgr["delete"](pSelectedIds.join(','), PageContext.params);
		}
	}
});

function trace(){
	this.trace.apply($channelMgr, arguments);
}

function escapeHTML(_sRawStr){
	return _sRawStr.stripTags();
}

function getAdaptedUsers(_sCrUser, _sReceivers){
	return PageContext.params['ReadFlag'] == 1 ? _sReceivers : _sCrUser;
}