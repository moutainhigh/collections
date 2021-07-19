$package('com.trs.dialog');

$import('com.trs.dialog.Button');
$import('com.trs.drag.Dragger');

com.trs.dialog.imgPath = com.trs.util.Common.BASE+'com.trs.dialog/img/';

//ge gfc add @ 2006-7-25
var APPENDIX_ID = '_trs_dialog_';

com.trs.dialog.Dialog = Class.create('dialog.Dialog');
com.trs.dialog.Dialog.prototype = {
	initialize: function(_sID){
		this.id = _sID;
		this.m_bCustomed = false;
	},
	init:function(){
		var id = this.id;
		var dialogInnerHTML = '<div id=dialog_page_shield_'+id+'></div>\
		<iframe id=dialog_cushion_'+id+' src="'+com.trs.dialog.imgPath+'blank.html"></iframe>\
		<div id=div_dialog_shadow_'+id+'></div>\
		<div id=dialog_'+id+'><table class=dialog cellpadding="0" cellspacing="0" width="100%" style="border:1px solid #000;">\
				<tr height="25" bgcolor="#6699cc">\
					<td id=dialogHead class=dialogHead unselectable="on">\
						<table style="-moz-user-select:none;" width="100%" border="0" cellpadding="0" cellspacing="0">\
							<tr>\
								<td width="6"></td>\
								<td id=dialogTitle class=dialogTitle></td>\
								<td id=dialogClose class=dialogClose width="27" align="right" valign="middle"></td>\
								<td width="6"></td>\
							</tr>\
						</table>\
					</td>\
				</tr>\
				<tr>\
					<td id=dialogBody class=dialogBody>\
						<table border="0" align="center" cellpadding="0" cellspacing="0">\
							<tr height="10"><td colspan="4"></td></tr>\
							<tr>\
								<td width="10"></td>\
								<td id=dialogIcon class=dialogIcon style="padding-right:5px;padding-left: 10px;" align="center" valign="absmiddle"></td>\
								<td id=dialogMsg class=dialogMsg></td>\
								<td width="10"></td>\
							</tr>\
							<tr height="10"><td colspan="4" align="center"></td></tr>\
							<tr><td colspan="4" align="center" id=dialogButtons class=dialogButtons></td></tr>\
							<tr height="10"><td colspan="4" align="center"></td></tr>\
						</table>\
					</td>\
				</tr>\
			</table></div>\
			';
		new Insertion.Bottom(document.body,dialogInnerHTML);
		var div = document.createElement("DIV");
		div.id = "dialog_title_"+id;
		div.style.fontSize = '12px';
		div.style.fontWeight = 'bold';
		div.style.cursor = 'move';
		div.style.width = '100%';
		this.titleElement = div;
		$("dialogTitle").appendChild(div);

		div = document.createElement("DIV");
		div.id = "dialog_msg_"+id;
		div.style.fontSize = '12px';
		this.msgElement = div;
		$("dialogMsg").appendChild(div);
	
		div = document.createElement("IMG");
		div.id = "dialog_icon_"+id;
		div.src = com.trs.dialog.imgPath+"3.gif";
		this.iconElement = div;
		$("dialogIcon").appendChild(div);
		
		var div = document.createElement("DIV");
		div.id = "dialog_buttons_"+id;
		this.buttonsElement = div;
		$("dialogButtons").appendChild(div);

		this.closeElement = com.trs.dialog.Button.CLOSE(id,this);	
		$("dialogClose").appendChild(this.closeElement);
		
		var dialogDragHandler = $("dialogTitle");
		this.heightElement = $("dialogBody");

		this.dialog = $("dialog_"+id);
		this.dialog.style.display = 'none';
		this.dialog.style.position = 'absolute';
		this.dialog.style.background = "#fff";
		this.dialog.style.zIndex = '999';

		this.dialogCushion = $("dialog_cushion_"+id);
		this.dialogCushion.style.display = 'none';
		this.dialogCushion.style.position = "absolute";
		this.dialogCushion.style.background = "#000000";
		this.dialogCushion.style.opacity = "0.4";
		this.dialogCushion.style.filter = "alpha(opacity = 40)";
		this.dialogCushion.style.zIndex = '998';

		this.divDialogShadow = $("div_dialog_shadow_"+id);
		this.divDialogShadow.style.display = 'none';
		this.divDialogShadow.style.position = "absolute";
		this.divDialogShadow.style.background = "#000000";
		this.divDialogShadow.style.opacity = "0.2";
		this.divDialogShadow.style.filter = "alpha(opacity = 20)";
		this.divDialogShadow.style.zIndex = '998';
		
		this.pageShieldElement = $("dialog_page_shield_"+id);
		this.pageShieldElement.style.display = 'none';
		this.pageShieldElement.style.position = 'absolute';
		this.pageShieldElement.style.left = '0';
		this.pageShieldElement.style.top = '0';
		this.pageShieldElement.style.background = "#fff";
		this.pageShieldElement.style.opacity = "0.2";
		this.pageShieldElement.style.filter = "alpha(opacity = 20)";
		this.pageShieldElement.style.zIndex = '997';
		this.pageShieldElement.style.width = Math.max(document.body.scrollWidth,document.body.clientWidth);
		this.pageShieldElement.style.height = Math.max(document.body.scrollHeight,document.body.clientHeight);
		
//		document.body.appendChild(this.pageShieldElement);
//		document.body.appendChild(this.dialogCushion);
//		document.body.appendChild(this.divDialogShadow);
//		document.body.appendChild(this.dialog);
//		document.body.removeChild(dialogContainer);
//		delete dialogContainer;
		var drag = this.drag = new com.trs.drag.Dragger(dialogDragHandler,this.dialog);
		drag.onDrag = drag.onDragEnd = this.shadow.bind(this);
		//alert("src:"+this.dialogCushion.src);
	},
	reset:function(){
		//this.destroy();
		document.body.removeChild(this.pageShieldElement);
		document.body.removeChild(this.divDialogShadow);
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
		this.pageShieldElement.style.display = '';
		this.pageShieldElement.style.width = Math.max(document.body.scrollWidth,document.body.clientWidth);
		this.pageShieldElement.style.height = Math.max(document.body.scrollHeight,document.body.clientHeight);
		this.dialogCushion.style.display = '';
		this.divDialogShadow.style.display = '';
		this.dialog.style.display = '';
		this.disableTabIndex();
		this.resize(w,h);
		this.move(l,t);
		this.shadow();
		//ge gfc add @ 2006-7-25
		try{
			var btnCancel = $('Cancel_' + APPENDIX_ID);
			var btnOk = $('OK_' + APPENDIX_ID);
			if (btnCancel != null){
				btnCancel.focus();
			}else if (btnOk != null){
				btnOk.focus();
				//btnOk.select();
			}
		}catch (ex){
			//alert(ex.description);
		}
	},
	hide:function(){
		this.dialog.style.display = 'none';
		this.dialogCushion.style.display = 'none';
		this.divDialogShadow.style.display = 'none';
		this.pageShieldElement.style.display = 'none';
		//ge gfc add @ 2007-3-15 11:03 清除注册的close方法
		//this.closeElement.onclick = null;

		this.enableTabIndex();
	},
	resize:function(w,h){
		w = (w)?w:300;
		h = (h)?h:160;
		var iInterval = this.dialog.offsetWidth-this.heightElement.offsetWidth;
		this.dialog.style.width = w;
		var oMsg = this.msgElement;
		if(oMsg){
			oMsg.style.wordWrap = 'normal';
			oMsg.style.wordBreak = 'normal';
			oMsg.style.overflow = 'visible';
		}
		if(this.heightElement.offsetWidth+iInterval>=700){
			this.dialog.style.width = 700;
		}
		else{
			this.dialog.style.width = this.heightElement.offsetWidth+iInterval;
		}
		if(oMsg){
			oMsg.style.wordWrap = 'break-word';
			oMsg.style.wordBreak = 'break-all';
		}
		if(this.heightElement.offsetHeight>=400){
			if(oMsg){
				oMsg.style.overflow = 'auto';
				oMsg.style.height = 400;
			}
			this.heightElement.height = 400;
			
		}
		else{
			this.heightElement.height = h;
		}
	},
	shadow:function(){
		Position.clone(this.dialog,this.dialogCushion);
		this.dialogCushion.style.height = parseInt(this.dialogCushion.style.height)+6;
		this.dialogCushion.style.width = parseInt(this.dialogCushion.style.width)+6;

		Position.clone(this.dialog, this.divDialogShadow);
		this.divDialogShadow.style.top = parseInt(this.divDialogShadow.style.top)+6;
		this.divDialogShadow.style.left = parseInt(this.divDialogShadow.style.left)+6;		
	},
	move:function(l,t){
		var screenOffset = [0,0];
		if(window.frameElement){
			screenOffset = Position.cumulativeOffset(window.frameElement);
		}
		var pageWidth = window.document.body.clientWidth-screenOffset[0];
		var pageHeight = window.document.body.clientHeight-screenOffset[1];
		l = l || ((parseInt(pageWidth)-parseInt(this.dialog.offsetWidth))/2+document.body.scrollLeft);
		this.dialog.style.left = l;
		t = t || ((parseInt(pageHeight)-parseInt(this.dialog.offsetHeight))/2+document.body.scrollTop);
		this.dialog.style.top = parseInt(t);
	},
	setTitle:function(title){
		this.titleElement.innerHTML = title;
	},
	setIcon:function(src){
		var div = document.createElement("IMG");
		div.id = this.iconElement.id;
		div.src = src;
		
		//$destroy(this.iconElement);
		$('dialogIcon').removeChild(this.iconElement);
		$('dialogIcon').appendChild(div);

		this.iconElement = div;
		div = null;
	},
	setMsg : function(msg){
		this.msgElement.innerHTML = msg;
	},
	event : function(_sMsg, _sOk, _sCancel, _sClose){
		if(_sMsg)this.setMsg(_sMsg);
		this.buttonsElement.innerHTML = '';
		var ok = null;
		if(_sOk){
			this.BTN_OK = ok = com.trs.dialog.Button.OK(this.id,this,_sOk);
			this.buttonsElement.appendChild(ok);
		}
		if(_sCancel){
			var cancel = this.BTN_CANCEL = com.trs.dialog.Button.CANCEL(this.id,this,_sCancel);
			this.buttonsElement.appendChild(cancel);
		}
		if(this.buttonsElement.innerHTML=='')
		{
			this.BTN_OK = ok = com.trs.dialog.Button.OK(this.id,this);
			this.buttonsElement.appendChild(ok);
		}
		if(_sClose){
			this.closeElement.onclick = function(){
				if(typeof(_sClose) == 'string') {
					eval(_sClose);
				}else if(typeof(_sClose) == 'function') {
					_sClose();
				}
				this.dialog.hide();
			}
		}
		this.show();
	},
	custom : function(_sBodyHtml, _close){
		this.heightElement.innerHTML = _sBodyHtml||'';
		if(_close){
			this.closeElement.onclick = function(){
				if(typeof(_close) == 'string') {
					eval(_close);
				}else if(typeof(_close) == 'function') {
					_close();
				}
				this.dialog.hide();
			}
		}
		this.m_bCustomed = true;
		this.show();
	},
	destroy : function(){
//		this.closeElement.onclick = null;
//		this.closeElement = null;
		this.closeElement.destroy();
		this.closeElement = null;
		this.BTN_OK.destroy();
		this.BTN_OK = null;
		this.BTN_CANCEL.destroy();
		this.BTN_CANCEL = null;
		document.body.removeChild(this.pageShieldElement);
		document.body.removeChild(this.divDialogShadow);
		document.body.removeChild(this.dialogCushion);
		document.body.removeChild(this.dialog);
		this.dialog = null;
		this.dialogCushion = null;
		this.divDialogShadow = null;
		this.pageShieldElement = null;
		this.buttonsElement = null;
		this.msgElement = null;
		this.iconElement = null;
		this.titleElement = null;
		this.heightElement = null;
		this.cachedTabIndex = null;
		this.drag.destroy();
		this.drag = null;
//		$destroy(this);
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
function $alert(msg, func,title){
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
function $fail(msg, func,title){
    return $errorMsg(msg, func, 4,title);
}	
