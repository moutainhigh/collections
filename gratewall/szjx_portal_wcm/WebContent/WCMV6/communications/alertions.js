var Messager = {
	commandList: [],
	m_oMsgJson : null,
	m_oPontoon :  null,

	m_oInitingTimer : null,
	m_oDetectingTimer : null,
	m_oStepingTimer : null,
	m_oClosureTimer: null,

	m_nTimeout : 0, 
	m_bAllRead : false,
	m_bCeased : false,

	initPontoon : function(){
		Event.observe(this.pte('msg_logo'), 'mouseover', function(){
			this.pte('msg_logo').style.backgroundImage = 'url(./images/icon/msg_onhover.gif)';
		}.bind(this), false);
		Event.observe(this.pte('msg_logo'), 'mouseout', function(){
			this.pte('msg_logo').style.backgroundImage = 'url(./images/icon/msg_normal.gif)';
		}.bind(this));

		Event.observe(this.pte('msg_title'), 'click', function(){
			this.viewDetail();
			return false;
		}.bind(this));
		Event.observe(this.pte('msg_logo'), 'click', function(){
			this.viewDetail();
			return false;
		}.bind(this));
		Event.observe(this.pte('nav_pre'), 'click', function(){
			this.preMsg();
			return false;
		}.bind(this));
		Event.observe(this.pte('nav_next'), 'click', function(){
			this.nextMsg();
			return false;
		}.bind(this));
	},
	/**
	判断是否有新的消息
	*/
	isFlashed : function(_arrTempMsgs){
		var arrTempMsgs = _arrTempMsgs;
		if(arrTempMsgs.length != this.m_oMsgJson.length) {
			return true;
		}
		//else
		return (
			$v(arrTempMsgs[0], 'crtime').trim() != $v(this.m_oMsgJson[0], 'crtime').trim()
		);
	},//*/
	refresh : function(_nIndex, _bSetReadedFlag, _bIsAutoRead){
		if(_nIndex <= 0) {
			return;
		}
		if(_nIndex > this.m_oMsgJson.length) {
			return;
		}
		//else
		this.pte('items_index').innerHTML = _nIndex;
		this.pte('items_index').setAttribute('_index', _nIndex);
		if(_nIndex == 1) {
			this.pte('items_totle').innerHTML = this.m_oMsgJson.length;
		}
		var title = $trans2Html($v(this.m_oMsgJson[_nIndex - 1], 'title'));
		this.pte('msg_title').setAttribute('title', title);
		this.pte('msg_title').innerHTML = title;

		//this.pte('msg_content').innerHTML = $trans2Html($trunc($v(this.m_oMsgJson[_nIndex - 1], 'mbody'), 80));
		var sContent = $v(this.m_oMsgJson[_nIndex - 1], 'mbody') || '';
		this.pte('msg_content').innerHTML = $trunc($trans2Html(sContent), 80);
		this.pte('msg_posttime').innerHTML = $v(this.m_oMsgJson[_nIndex - 1], 'crtime');

		var nTotle = parseInt(this.pte('items_totle').innerHTML);
		if(_bIsAutoRead && _nIndex == nTotle) { // 如果是自动收取，置一下已经全部读的标志
			this.m_bAllRead = true;
			this.setNotifyStatus(false);
			this.setNotifyNum(0);
		}
		// 设置为已读
		if(_bSetReadedFlag) {
			// set flag TODO 
			//if((msg.FLAG.NODEVALUE + '').trim() == '0') {
				var oHelper = new com.trs.web2frame.BasicDataHelper();
				oHelper.Call('wcm6_message', 'setReadFlag',
					{Readed: true, objectids: $v(this.m_oMsgJson[_nIndex - 1], 'msgid')}, false); // do nothin' with this calling
			//}
		}
		//ge gfc add @ 2007-7-27 9:23 改变上一条、下一条的可用状态
		this.pte('nav_pre').className  = (_nIndex == 1) ? 'nav_go_pre_disabled' : 'nav_go_pre';
		this.pte('nav_next').className = (_nIndex == nTotle) ? 'nav_go_next_disabled' : 'nav_go_next';
	},
	reset : function(){
		if(PageContext.config == null || PageContext.config.MsgTypes.length == 0) {
			return;
		}
		//如果已经在显示了，则放弃这次操作直接返回
		if(this.m_oPontoon != null && this.m_oPontoon.isVisible()) {
			return;
		}
		if(Messager.m_oDetectingTimer) {
			window.clearTimeout(Messager.m_oDetectingTimer);
		}
		this.renderDetect();
	},
	detectMsg : function(_isFirstDisp){
		if(Messager.m_oInitingTimer) {//清理一下
			window.clearTimeout(Messager.m_oInitingTimer);
		}
		//1.初始化
		if(_isFirstDisp) {
			//TODO检测用户定制特性
			Messager.m_oInitingTimer = window.setTimeout(function(){
				this.detectMsg();
			}.bind(this), 2000);
			return;
		}
		//2.设定了读取哪些type的msg
		if(Messager.m_oDetectingTimer) {
			window.clearTimeout(Messager.m_oDetectingTimer);
		}
		this.renderDetect();

	},
	renderDetect : function(){
		var nDetectInt = PageContext.config.DetectInterval;
		if(!window.top._FOCUSED) {
			if(Messager.m_oDetectingTimer) {
				window.clearTimeout(Messager.m_oDetectingTimer);
			}
			Messager.m_oDetectingTimer = window.setTimeout(this.renderDetect.bind(this), nDetectInt*1000);
			return;
		}//*/
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		var sMsgTypes = PageContext.config.MsgTypes.join(',');
		oHelper.Call('wcm6_message', 'query', {ReadFlag: 0, MsgTypes: sMsgTypes, PageSize: -1}, false, function(_trans, _json){
			var arrMsgJson = $a(_json, 'messages.message'); 
			//没有未读消息
			if(arrMsgJson == null || arrMsgJson.length <= 0) {
				this.setNotifyStatus(false);
				this.setNotifyNum(0);
				Messager.m_oDetectingTimer = window.setTimeout(this.renderDetect.bind(this), nDetectInt*1000);
				return;
			}
			//通知有未读消息的"闪动"
			this.setNotifyStatus(true);
			this.setNotifyNum(arrMsgJson.length);
			this.m_oMsgJson = arrMsgJson;
			this.__promptMsg();
		}.bind(this));		
	},
	__promptMsg : function(){
		if(this.m_oPontoon == null) {//初次显示
			//获取信息并进行数据绑定
			this.m_oPontoon = new Pontoon('消息通知', $('aDiv'), 250, 150);
			this.m_oPontoon.setOnHide(function(){//隐藏后，重新开始检测
				//alert('after hide')
				if(!this.m_bAllRead){//如果自动接受尚未结束，彻底结束消息探测，直到下次被唤醒
					if(Messager.m_oStepingTimer != null) {
						window.clearTimeout(Messager.m_oStepingTimer);
					}
					return;
				}
				//else 继续消息探测
				var nDetectInt = PageContext.config.DetectInterval;
				Messager.m_oDetectingTimer = window.setTimeout(this.renderDetect.bind(this), nDetectInt*1000);
			}.bind(this));

			this.initPontoon();
		}
		
		//else 显示框没有完全消失，什么都不做
		if(this.m_oPontoon.getCurrentHeight() > 1) {
			return;
		}

		//消息框显示后，执行自动收取“下一条”
		this.m_bAllRead = false;
		this.m_bCeased = false;
		this.pte('items_index').setAttribute('_index', 0);
		if(Messager.m_oStepingTimer != null) {
			window.clearTimeout(Messager.m_oStepingTimer);
		}
		this.renderSteping();
		//显示消息框
		//this.__resetPontoonData();		
		this.m_oPontoon.show();

		
	},
	/**
	*自动收取下一条信息
	*/
	renderSteping : function(){
		// 如果被其他进程阻隔(例如点击"上一步"、"下一步")，停止自动接收和自动隐藏
		if(this.m_bCeased) {
			if(Messager.m_oStepingTimer) {
				window.clearTimeout(Messager.m_oStepingTimer);
			}
			if(Messager.m_oClosureTimer != null) {
				window.clearTimeout(Messager.m_oClosureTimer);
			}
			this.m_bAllRead = true; // 假装这一次已经读完了
			return;
		}
		// 如果全部已经读取 or 间隔时间到，停止自动接收，然后启动自动隐藏
		if(this.m_bAllRead){// || this.m_nTimeout > 2) {
			if(Messager.m_oStepingTimer != null) {
				window.clearTimeout(Messager.m_oStepingTimer);
			}
			if(Messager.m_oClosureTimer != null) {
				window.clearTimeout(Messager.m_oClosureTimer);
			}
			this.renderClosure();
			return;
		}

		//1.获取index
		var nIndex = this.getCurrIndex();
		if(nIndex >= this.m_oMsgJson.length) {
			return;
		}
		//alert(this.getCurrIndex())
		//2.刷新显示
		this.refresh(++nIndex, true, true);

		//3.善后工作
		this.m_nTimeout--;
		if(this.m_nTimeout <= 0) {
			this.m_nTimeout = 0;
		}				
		Messager.m_oStepingTimer = window.setTimeout(function(){
			this.renderSteping();
		}.bind(this), 4*1000)
	},
	//注意：这里定时关闭的interval肯定是小于detect-interval的
	renderClosure : function(){
		Messager.m_oClosureTimer = window.setTimeout(function(){
			this.m_oPontoon.hide();
		}.bind(this), 4*1000);
	},
	preMsg : function(){
		//收取上一条信息
		var nIndex = this.getCurrIndex();
		this.refresh(--nIndex, true)
		this.m_bCeased = true;
	},
	nextMsg : function(){
		//收取下一条信息
		var nIndex = this.getCurrIndex();
		this.refresh(++nIndex, true)
		this.m_bCeased = true;
	},
	viewDetail : function(){
		if(this.m_oMsgJson.length <= 0) {
			return;
		}
		var msg = this.m_oMsgJson[this.getCurrIndex() - 1];
		var sMsgId = 0;
		if(msg == null || (sMsgId = $v(msg, 'msgid')) == null) {
			return;
		}
		//TODO
		if(this.m_oShowDialog == null) {
			TRSCrashBoard.setMaskable(true);
			this.m_oShowDialog = TRSDialogContainer.register('Alertion_Show', '查看消息的详细信息', 
				'/wcm/WCMV6/communications/alertion_show.html', '540px', '360px', false, false, true);
		}
		
		TRSDialogContainer.display('Alertion_Show', {id: sMsgId, msgs: this.m_oMsgJson, index: this.getCurrIndex()});
		window.setTimeout(function(){
			this.m_oPontoon.hide();
		}.bind(this), 1000);


	},
	getCurrIndex : function(){
		return parseInt(this.pte('items_index').getAttribute('_index', 2));
	},
	getPontoon : function(){
		return this.m_oPontoon;
	},
	pte : function(_sId){
		return this.m_oPontoon.$(_sId);
	},
	setNotifyStatus : function(_bActive){
		var eIcon = window.top.document.getElementById('spAlertionIcon');
		if(eIcon == null) {
			return;
		}
		if(_bActive == true) {
			eIcon.className = 'alertion_active_icon';
			return;
		}
		//else
		eIcon.className = 'alertion_deactive_icon';
	},
	setNotifyNum : function(_nNum){
		var oNum = window.top.document.getElementById('spAlertionNum');
		if(oNum == null) {
			return;
		}
		if(parseInt(_nNum, 10)>0) {
			oNum.innerHTML = '('+_nNum+')';
			oNum.style.color = '#fdfbab';
			return;
		}
		//else
		oNum.style.color = '';
		oNum.innerHTML = '';
	}
}

Event.onDOMReady(function(){
	PageContext.loadConfig();
	if(PageContext.config == null || PageContext.config.MsgTypes.length == 0) {
		return;
	}
	//else
	Messager.detectMsg(true);
});

Event.observe(window.top, 'resize', function(){
	var pontoon = Messager.getPontoon();
	if(pontoon != null) {
		pontoon.posPontoon();
	}
});

// 读取配置信息
var PageContext = {};
Object.extend(PageContext, {
	loadConfig : function(){
		var config = {
			MsgTypes: [],
			DetectInterval:  5,// unit: second
			ClosureInterval: 5
		};
		//get the system configuration
		var sysConfig = Object.extend({
			msgNotifyPublish: {},
			msgNotifyCommunication: {},
			msgNotifyWorkFlow: {},
			msgNotifyTimeSpan: {}
		}, (top.actualTop || top).PageContext.individuationConfig);
		//1.types to detect
		if(sysConfig.msgNotifyPublish['paramValue'] == '1') config.MsgTypes.push('2');
		if(sysConfig.msgNotifyCommunication['paramValue'] == '1') config.MsgTypes.push('1');
		if(sysConfig.msgNotifyWorkFlow['paramValue'] == '1') config.MsgTypes.push('3');
		if(config.MsgTypes.length == 0) {
			return;
		}
		//else
		//2.interval to detect
		var interval = parseInt(sysConfig.msgNotifyTimeSpan['paramValue']);
		if(!isNaN(interval) && interval > 5) {
			config.DetectInterval = interval;
		}

		PageContext.config = config;
	}
});