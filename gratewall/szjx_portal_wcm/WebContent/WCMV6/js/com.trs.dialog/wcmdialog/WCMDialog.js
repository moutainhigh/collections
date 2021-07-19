$package('com.trs.dialog.wcmdialog');
$importCSS('com.trs.dialog.wcmdialog.resource.WCMDialog');

$import('com.trs.drag.Dragger');

com.trs.dialog.imgPath = com.trs.util.Common.BASE+'com.trs.dialog/img/';

var APPENDIX_ID = '_trs_dialog_';

com.trs.dialog.Dialog = Class.create('dialog.Dialog');
com.trs.dialog.Dialog.prototype = {
	initialize: function(_sID){
		this.id = _sID;
		this.m_bCustomed = false;
	},
	init:function(){
		var id = this.id;
		var dialogInnerHTML = '<div id=dialog_page_shield_'+id+' class=dialogPageShield></div>\
		<iframe id=dialog_cushion_'+id+' src="'+com.trs.dialog.imgPath+'blank.html" class=dialogCushion></iframe>\
		<div id=dialog_'+id+' class=dialogOuter><table class=dialog cellpadding="0" cellspacing="0" border="0" width="100%">\
				<tr>\
					<td id=dialogHead class=dialogHead unselectable="on"></td>\
				</tr>\
				<tr>\
					<td id=dialogBody class=dialogBody></td>\
				</tr>\
			</table></div>';

		new Insertion.Bottom(document.body,dialogInnerHTML);
		this.pageShieldElement = $("dialog_page_shield_"+id);
		this.pageShieldElement.style.display = 'none';
		this.pageShieldElement.style.width = Math.max(document.body.scrollWidth,document.body.clientWidth);
		this.pageShieldElement.style.height = Math.max(document.body.scrollHeight,document.body.clientHeight);
		this.dialogCushion = $("dialog_cushion_"+id);
		this.dialogCushion.style.display = 'none';
		this.dialog = $("dialog_"+id);
		this.dialog.style.display = 'none';
		this.headElement = $("dialogHead");
		this.heightElement = $("dialogBody");
		this.oButton = new com.trs.dialog.wcmdialog.Button(this);
		this.setHead();
		this.setBody();
	},
	reset:function(){
		//this.destroy();
		document.body.removeChild(this.pageShieldElement);
		document.body.removeChild(this.dialogCushion);
		document.body.removeChild(this.dialog);
		this.drag.destroy();
		this.drag = null;
		this.init();
	},
	disableTabIndex : function(){
		if(this.notDealTabIndex)return;
		this.cachedTabIndex = [];
		var allSons = document.getElementsByTagName("*");
		for(var i = 0;i<allSons.length;i++){
			if(allSons[i].tabIndex!= -1){
				this.cachedTabIndex.push(allSons[i],allSons[i].tabIndex);
				allSons[i].tabIndex = -1;
			}
		}
	},
	enableTabIndex	: function(){
		if(this.notDealTabIndex)return;
		for(var i = 0;i<this.cachedTabIndex.length;i+=2){
			if(this.cachedTabIndex[i+1]==0){
				var oElement = this.cachedTabIndex[i];
				oElement.removeAttribute('tabIndex');
			}
			else{
				this.cachedTabIndex[i].tabIndex = this.cachedTabIndex[i+1];
			}
		}
		this.cachedTabIndex = [];
	},
	show:function(w,h,l,t){
		(top.actualTop || top).cancelKeyDown = true;
		this.pageShieldElement.style.width = Math.max(document.body.scrollWidth,document.body.clientWidth);
		this.pageShieldElement.style.height = Math.max(document.body.scrollHeight,document.body.clientHeight);
		this.pageShieldElement.style.display = '';

		this.dialogCushion.style.display = '';
		this.dialog.style.display = '';
		this.resize(w,h);
		this.move(l,t);
		this.shadow();
		//ge gfc add @ 2006-7-25
		try{
			var btnCancel = $('Cancel_' + APPENDIX_ID);
			var btnOk = $('OK_' + APPENDIX_ID);
			if (btnCancel != null){
				(getFirstHTMLChild(btnCancel)||btnCancel).focus();
			}else if (btnOk != null){
				(getFirstHTMLChild(btnOk)||btnOk).focus();
				//btnOk.select();
			}
		}catch (ex){
			//alert(ex.description);
		}
		this.disableTabIndex();
	},
	hide:function(){
		(top.actualTop || top).cancelKeyDown = false;
		this.dialog.style.display = 'none';
		this.dialogCushion.style.display = 'none';
		this.pageShieldElement.style.display = 'none';
		//ge gfc add @ 2007-3-15 11:03 清除注册的close方法
		//this.closeElement.onclick = null;

		this.enableTabIndex();
	},
	resize:function(w,h){
		var oMsg = this.msgElement;
		if(oMsg){
			oMsg.style.wordWrap = 'break-word';
			oMsg.style.wordBreak = 'break-all';
			oMsg.style.overflow = 'auto';
		}		
		w = (w)?w:328;
		h = (h)?h:169;
		this.dialog.style.width = Math.max(w, 324);
		this.dialog.style.height = Math.min(h, 169);
	},
	shadow:function(){
		Position.clone(this.dialog,this.dialogCushion);
	},
	move:function(l,t){
		var screenOffset = [0,0];
		//modified by hxj on 2008-04-19 将dialog居中
		/*
		if(window.frameElement){
			screenOffset = Position.cumulativeOffset(window.frameElement);
		}
		*/
		var pageWidth = window.document.body.clientWidth-screenOffset[0];
		var pageHeight = window.document.body.clientHeight-screenOffset[1];
		l = l || ((parseInt(pageWidth)-parseInt(this.dialog.offsetWidth))/2+document.body.scrollLeft);
		this.dialog.style.left = parseInt(l);
		t = t || ((parseInt(pageHeight)-parseInt(this.dialog.offsetHeight))/2+document.body.scrollTop);
		this.dialog.style.top = parseInt(t);
	},
	setTitle:function(title){
		try{
			this.titleElement.innerHTML = title;
		}catch(error){
			 throw new Error("没有实现titleElement元素,必须先指定titleElement元素");
		}
	},
	setIcon:function(src){
		try{
			this.iconElement.style.backgroundImage = 'url('+src+')';
		}catch(error){
			throw new Error("没有实现iconElement元素,必须先指定iconElement元素");
		}
	},
	setMsg:function(msg){
		try{
			this.msgElement.innerHTML = msg;
		}catch(error){
			throw new Error("没有实现msgElement元素,必须先指定msgElement元素");
		}
	},
	setHead : function(_sHead){			
		this.oButton.unloadButton($button.CLOSE);
		if(this.drag){
			this.drag.destroy();
		}
		if(_sHead == null){//set default head
			var _sHead = '\
					<div class="head_left"></div>\
					<div class="head_separate"></div>\
					<div class="head_right"></div>\
					<div class="head_close" id="dialogClose"></div>\
					<div class="head_title" id="dialogTitle" unselectable="on"></div>';		
		}
		this.headElement.innerHTML = _sHead;
		this.closeElement = $('dialogClose');
		this.titleElement = $('dialogTitle');
		this.oButton.loadButton($button.CLOSE, null);
		var dialogDragHandler = $("dialogTitle");
		if(dialogDragHandler){
			var drag = this.drag = new com.trs.drag.Dragger(dialogDragHandler, this.dialog);
			drag.onDrag = drag.onDragEnd = this.shadow.bind(this);
		}
	},
	setBody : function(_sBody){
		this.oButton.unloadAllButtons(true);
		if(_sBody == null){//set default body
			var _sBody = '\
					<table border=0 cellspacing=0 cellpadding=0 width="100%">\
					<tbody>\
						<tr>\
							<td class="body_left"></td>\
							<td class="body_middle">\
								<div class="body_icon" id="dialogIcon"></div>\
								<div class="dialogMsg">\
									<table border=0 cellspacing=0 cellpadding=0 class="dialogMsgTable">\
										<tr><td id="dialogMsg"></td></tr>\
									</table>\
								</div>\
								<div class="dialogButtons" id="dialogButtons"></div>\
							</td>\
							<td class="body_right"></td>\
						</tr>\
						<tr>\
							<td class="bottom_left"></td>\
							<td class="bottom_middle"></td>\
							<td class="bottom_right"></td>\
						</tr>\
					</tbody>\
					</table>';
		}
		this.heightElement.innerHTML = _sBody;
		this.buttonsElement = $('dialogButtons');
		this.msgElement = $('dialogMsg');
		this.iconElement = $('dialogIcon');
	},
	event:function(_sMsg, _sOk, _sCancel, _sClose){
		if(_sMsg) this.setMsg(_sMsg);
		//this.oButton.unloadAllButtons(true);
		this.oButton.unloadAllButtons();
		if(_sOk) this.oButton.loadButton($button.OK, _sOk);
		if(_sCancel) this.oButton.loadButton($button.CANCEL, _sCancel);
		if(this.buttonsElement.innerHTML == ''){
			this.oButton.loadButton($button.OK, _sOk);
		}
//		if(_sClose){
//			this.oButton.unloadButton($button.CLOSE);
			this.oButton.loadButton($button.CLOSE, _sClose);
//		}
		this.show();
	},
	custom:function(_sBodyHtml, _close){
		this.setBody(_sBodyHtml || '');
//		if(_close){
			this.oButton.unloadButton($button.CLOSE);
			this.oButton.loadButton($button.CLOSE, _close);
//		}
		this.m_bCustomed = true;
		this.show();
	},
	destroy:function(){
		this.oButton.unloadAllButtons();
		this.oButton = null;
		this.closeElement.onclick = null;
		this.closeElement = null;
		document.body.removeChild(this.pageShieldElement);
		document.body.removeChild(this.dialogCushion);
		document.body.removeChild(this.dialog);
		this.dialog = null;
		this.dialogCushion = null;
		this.pageShieldElement = null;
		this.buttonsElement = null;
		this.msgElement = null;
		this.iconElement = null;
		this.titleElement = null;
		this.heightElement = null;
		this.cachedTabIndex = null;
		this.drag.destroy();
		this.drag = null;
		$destroy(this);
	}
}

function $dialog(_bForceRemain){
	var result = null;
	if(!window._trs_dialog_){
		//ge gfc modify @ 2006-7-25
		//window._trs_dialog_=new com.trs.dialog.Dialog("_trs_dialog_");
		window._trs_dialog_ = new com.trs.dialog.Dialog(APPENDIX_ID);
		window._trs_dialog_.init();
	}else{
		if(!(_bForceRemain != null && _bForceRemain) && window._trs_dialog_.m_bCustomed) {
			window._trs_dialog_.reset();
			window._trs_dialog_.m_bCustomed = false;
		}
	}
	result = window._trs_dialog_;
	result.openner = window;
	return result;
}

function $dialogElement(_sId){
	return $(_sId);
}
Event.observe(window,'load',$dialog);
Event.observe(window,'unload',function(){
	if(window._trs_dialog != null) {
		window._trs_dialog_.openner = null;
		window._trs_dialog_.destroy();
		window._trs_dialog_ = null;
	}
});
function $custom(_sBody,_sTitle,_fClose){
	_fClose = _fClose ? _fClose : '';
	var dg = $dialog();
    dg.setTitle(_sTitle);
    dg.custom(_sBody,_fClose);
    return dg;
}
function $errorMsg(msg, func, response, title){
    var dg = $dialog();
    func = func ? func : '';
    title = title ? title : '系统提示信息';
	dg.notDealTabIndex = true;
    dg.setIcon(__getDialogIcon(response));
    dg.setTitle(title);
    dg.event(msg, func, '', func);
    return dg;
}

function __getDialogIcon(_response){
    var response = _response ? _response : '';
    var icon = '';
    switch (response){
	    case 1:
	        icon = com.trs.dialog.imgPath+"1.gif";
	        break;
	    case 2:
	        icon = com.trs.dialog.imgPath+"2.gif";
	        break;
	    case 3:
	        icon = com.trs.dialog.imgPath+"3.gif";
	        break;
	    case 4:
	        icon = com.trs.dialog.imgPath+"4.gif";
	        break;
	    case 5:
	        icon = com.trs.dialog.imgPath+"5.gif";
	        break;
	    case 6:
	        icon = com.trs.dialog.imgPath+"6.gif";
	        break;
		//ge gfc add @ 2006-7-25
	    case 7:
	        icon = com.trs.dialog.imgPath+"7.gif";
	        break;
	    default:
	        icon = com.trs.dialog.imgPath+"5.gif";
	        break;
    }
    
    return icon;	
}

function $formatSymbolForMsg(msg){
	var dealedSymbols = [",", "，", ";", "；", "!", "！"];
	var endChar = msg.substr(msg.length-1);
	if(dealedSymbols.include(endChar)){
		msg = msg.substr(0, msg.length-1)+"。";
	}
	return msg;
}
function $alert(msg, func,title){
	msg = $formatSymbolForMsg(msg);
    return $errorMsg(msg, func, 5,title);
}
function $timeAlert(msg,iSeconds,func,title,nIcon){
	iSeconds = iSeconds || 5;
	var d = $errorMsg(msg+'<br><br><div style="font-size:12px;color:gray;padding-left:20px;">本窗口在<span id="_dialog_timecounter_" style="font-size:11px;padding:0 2px;font-weigth:bold;color:blue">'+iSeconds+'</span>秒内将自动消失.</div>', func, 5,title);
	d.cnt = 0;
	d.setIcon(__getDialogIcon(nIcon || 5));
	setTimeout(
		function(){
			if((++d.cnt)>=iSeconds){
				d.hide();
			}
			else{
				var tc = $dialogElement('_dialog_timecounter_');
				if(!tc) return;
				tc.innerHTML = iSeconds-d.cnt;
				setTimeout(arguments.callee,1000);
			}
		}
	,1000);
}

//ge gfc add @ 2006-7-25
function $wait(msg, func,title){
	if (msg == null){
		msg = '<span style="font-size:13px;font-weight:bold;font-family:Courier New;padding-left:20px">操作正在进行，请稍候..</span>';
	}
    return $errorMsg(msg, func,7,title);
} 

function $success(msg,okFunc,title){
	msg = $formatSymbolForMsg(msg);
    okFunc = okFunc ? okFunc : '';
    title = title ? title : '系统提示信息';
    var dg = $dialog();
    dg.setIcon(com.trs.dialog.imgPath+"2.gif");
    dg.setTitle(title);
    dg.event(msg, okFunc, '', okFunc);
    return dg;
}
function $confirm(msg, okFunc, cancelFunc, title, response){
    okFunc = okFunc ? okFunc : '';
    cancelFunc = cancelFunc ? cancelFunc : '';
    title = title ? title : '系统提示信息';
    var dg = $dialog();
   
    response = response ? response : 3;
    dg.setIcon(__getDialogIcon(response));
    dg.setTitle(title);
    dg.event(msg, okFunc, cancelFunc, cancelFunc);
    return dg;
}
function msgbox(oConfig){
	oConfig = oConfig || {};
    var dg = $dialog();
	dg.setTitle(oConfig["title"] || '系统提示信息');
	dg.setMsg(oConfig["msg"] || "");
	dg.oButton.unloadAllButtons();
	var aBtn = oConfig["buttons"] || [{type : $button.OK}];
	var bContainCloseBtn = false;
	for (var i = 0; i < aBtn.length; i++){
		if(aBtn[i]['type'] == $button.CLOSE){
			bContainCloseBtn = true;
		}
		dg.oButton.loadButton(aBtn[i]['type'], aBtn[i]['handler']);
	}
	if(!bContainCloseBtn){
		dg.oButton.loadButton($button.CLOSE);
	}
	dg.show();
}
function $fail(msg, func,title){
	msg = $formatSymbolForMsg(msg);
    return $errorMsg(msg, func, 4,title);
}	


var $button = com.trs.dialog.wcmdialog.Button = Class.create('wcmdialog.Button');
$button.OK = 1;
$button.CANCEL = 2;
$button.CLOSE = 3;
$button.YES = 4;
$button.NO = 5;
$button.BCLOSE = 6;

Object.extend(com.trs.dialog.wcmdialog.Button.prototype, {
	oContainer	: null,//dialog容器
	
	initialize : function(_oDialog){
		this.oContainer = _oDialog;
	},
	buttonMouseEvent : function(_sButton, _sOldClass, _sNewClass){
		Element.removeClassName(_sButton, _sOldClass);
		Element.addClassName(_sButton, _sNewClass);
	},
	buttonKeyEvent : function(_sButton, event){
		event = window.event || event;
		if(event.keyCode == Event.KEY_RETURN || event.keyCode == 32){
			$(_sButton).click();
		}
		Event.stop(event);
		return false;
	},
	unloadAllButtons : function(_bExceptClose){
		for (var buttonType in this.commonButton){
			if(_bExceptClose && buttonType == $button.CLOSE)
				continue;
			this.unloadButton(buttonType);
		}	
	},
	unloadButtons : function(){
		for (var i = 0; i < arguments.length; i++){
			this.unloadButton(arguments[i]);
		}
	},
	unloadButton : function(_buttonType){
		if(this.commonButton && this.commonButton[""+_buttonType]){
			var temp = this.commonButton[""+_buttonType];
			Event.stopObserving(temp[0], 'click', temp[1]);
			Event.stopObserving(temp[0], 'mouseover', temp[2]);
			Event.stopObserving(temp[0], 'mouseout', temp[3]);
			Event.stopObserving(temp[0], 'keydown', temp[4]);
			if(_buttonType != $button.CLOSE){
				$removeNode(getPreviousHTMLSibling($(temp[0])));
				$removeNode(getNextHTMLSibling($(temp[0])));
				$removeNode($(temp[0]));
			}
			delete temp[1];
			delete temp[2];
			delete temp[3];
			delete temp[4];
			delete this.commonButton[""+_buttonType];
		}			
	},
	loadButton : function(_buttonType, _fAction){
		var sButtonHtml = '';
		var sButtonId = '';
		var sClassName = '';
		switch(_buttonType){
			case $button.YES:
				sButtonId = "Yes_" + this.oContainer.id;
				sClassName = 'yes_btn_middle';
				sButtonHtml = '<div class="btn_left"></div>\
						<div class="' + sClassName + '" id="'+sButtonId+'">\
							<div class="btn_middle_inner" tabindex="1" unselectable="on"></div>\
						</div>\
						<div class="btn_right"></div>';
				new Insertion.Bottom(this.oContainer.buttonsElement, sButtonHtml);
				break;
			case $button.OK :
				sButtonId = "Ok_" + this.oContainer.id;
				sClassName = 'ok_btn_middle';
				sButtonHtml = '<div class="btn_left"></div>\
						<div class="' + sClassName + '" id="'+sButtonId+'">\
							<div class="btn_middle_inner" tabindex="1" unselectable="on"></div>\
						</div>\
						<div class="btn_right"></div>';
				new Insertion.Bottom(this.oContainer.buttonsElement, sButtonHtml);
				break;
			case $button.NO:
				sButtonId = "No_" + this.oContainer.id;
				sClassName = 'no_btn_middle';
				sButtonHtml = '<div class="btn_left"></div>\
						<div class="' + sClassName + '" id="'+sButtonId+'">\
							<div class="btn_middle_inner" tabindex="2" unselectable="on"></div>\
						</div>\
						<div class="btn_right"></div>';
				new Insertion.Bottom(this.oContainer.buttonsElement, sButtonHtml);
				break;
			case $button.CANCEL:
				sButtonId = "Cancel_" + this.oContainer.id;
				sClassName = 'cancel_btn_middle';
				sButtonHtml = '<div class="btn_left"></div>\
						<div class="' + sClassName + '" id="'+sButtonId+'">\
							<div class="btn_middle_inner" tabindex="2" unselectable="on"></div>\
						</div>\
						<div class="btn_right"></div>';
				new Insertion.Bottom(this.oContainer.buttonsElement, sButtonHtml);
				break;
			case $button.BCLOSE:
				sButtonId = "Close_" + this.oContainer.id;
				sClassName = 'close_btn_middle';
				sButtonHtml = '<div class="btn_left"></div>\
						<div class="' + sClassName + '" id="'+sButtonId+'">\
							<div class="btn_middle_inner" tabindex="2" unselectable="on"></div>\
						</div>\
						<div class="btn_right"></div>';
				new Insertion.Bottom(this.oContainer.buttonsElement, sButtonHtml);
				break;
			case $button.CLOSE:
				sButtonId = "dialogClose";
				sClassName = 'head_close';
				if($(sButtonId) == null)return;
				break;
			default:
				throw new Error("未知的button类型:"+_buttonType);
		}		
		if(!_fAction){
			var fAction = function(){
				$dialog().hide();
			};
		}else if(typeof _fAction == 'string'){
			var fAction = function(){
				if($button.CLOSE == _buttonType){
					$dialog().hide();
				}
				try{
					eval(_fAction);
				}catch(err){
					//just skip it
				}
			};
		}else{
			var fAction = function(){
				if($button.CLOSE == _buttonType){
					$dialog().hide();
				}
				try{
					_fAction();
				}catch(err){
					//just skip it
				}
			};		
		}
		if(this.commonButton == null){
			this.commonButton = new Object();
		}
		var temp = this.commonButton[""+_buttonType] = [
				sButtonId, 
				fAction, 
				this.buttonMouseEvent.bind(this, sButtonId, sClassName, sClassName+"_active"),
				this.buttonMouseEvent.bind(this, sButtonId, sClassName+"_active", sClassName),
				this.buttonKeyEvent.bind(this, sButtonId)
		];
		Event.observe(temp[0], 'click', temp[1]);
		Event.observe(temp[0], 'mouseover', temp[2]);
		Event.observe(temp[0], 'mouseout', temp[3]);
		Event.observe(temp[0], 'keydown', temp[4]);
	}
});


var TransformableDialog = {
	show : function(_sTitle, _sMsg, _sOk, _sCancel, _sClose){
		(top.actualTop || top).cancelKeyDown = true;
		this.dialog = $dialog();
		this.dialog.setBody(this.getDialogHtml());
		this.dialog.setTitle(_sTitle);
		this.dialog.setIcon(com.trs.dialog.imgPath + '6.gif');
		this.dialog.event(_sMsg, _sOk, _sCancel, _sClose);
		this.dialog.m_bCustomed = true;
	},
	hide : function(){
		(top.actualTop || top).cancelKeyDown = false;
		this.dialog.hide();
	},
	getDialogHtml : function(){
		return	'\
			<table border=0 cellspacing=0 cellpadding=0 style="height:200px;width:400px;" id="outerTable">\
				<tr>\
					<td class="bodytd">\
						<table border=0 cellspacing=0 cellpadding=0 style="width:100%;height:100%;">\
							<tr>\
								<td class="body_left2"><div style="width:8px;overflow:hidden;"></div></td>\
								<td style="width:100px;">\
									<div id="dialogIcon" style="background:no-repeat center center;height:100%;width:100px;"></div>\
								</td>\
								<td id="dialogMsg" class="TransformableDialog_msg"></td>\
								<td class="body_right2"><div style="width:8px;overflow:hidden;"></div></td>\
							</tr>\
							<tr height="32">\
								<td class="body_left2"><div style="width:8px;overflow:hidden;"></div></td>\
								<td colspan="2"><div class="dialogButtons" id="dialogButtons"></div></td>\
								<td class="body_right2"><div style="width:8px;overflow:hidden;"></div></td>\
							</tr>\
						</table>\
					</td>\
				</tr>\
				<tr height="9">\
					<td height="9">\
						<table border=0 cellspacing=0 cellpadding=0 style="width:100%;height:100%;">\
							<tr>\
								<td class="bottom_left3"><div style="width:14px;height:9px;overflow:hidden;"></div></td>\
								<td class="bottom_middle3" colspan="2"><div style="height:9px;overflow:hidden;"></div></td>\
								<td class="bottom_right3"><div style="width:14px;height:9px;overflow:hidden;"></div></td>\
							</tr>\
						</table>\
					</td>\
				</tr>\
			</table>';    
	},	
	destroy : function(){
		this.dialog = null;
	}	
};
Event.observe(window, 'unload', function(){
	TransformableDialog.destroy();
});
