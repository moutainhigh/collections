var bInFloatPanel = getParameter("_fromfp_")=='1';
if(!bInFloatPanel){
	var m_oCommands = {};
	var m_tmpBtn = '<input id="ipt_{1}" type="button" value="{0}"/>';
	function addCommand(_sCmdId,_sCmdHtml,_fnCmd,_oScope,_arrArgs){
		var sBtnId = 'cmd_'+_sCmdId;
		var btnCmd = document.createElement("SPAN");
		btnCmd.innerHTML = String.format(m_tmpBtn, _sCmdHtml, _sCmdId);
		btnCmd.className = 'command_btn';
		btnCmd.id = sBtnId;
		m_oCommands[sBtnId] = {
			fn : _fnCmd,
			scope : _oScope,
			args : _arrArgs
		};
		$('buttons_container').appendChild(btnCmd);
	}
	function addCloseCommand(_sCmdHtml){
		addCommand("_close_", _sCmdHtml||"取消", '_close_');
	}
	Event.observe(window, 'load', function(){
		var divBtns = document.createElement('DIV');
		divBtns.id = 'buttons_container';
		divBtns.align = 'center';
		document.body.appendChild(divBtns);
		if(window.m_fpCfg && window.m_fpCfg.m_arrCommands){
			var bHasClose = (window.m_fpCfg.withclose===false) || false;
			for(var i=0,n=window.m_fpCfg.m_arrCommands.length;i<n;i++){
				var o = window.m_fpCfg.m_arrCommands[i];
				if(o.cmd=='close'){
					bHasClose = true;
					addCloseCommand(o.name);
					continue;
				}
				addCommand(o.cmd, o.name, o.cmd, o.scope, o.args);
			}
			if(!bHasClose){
				addCloseCommand();
			}
		}else{
			addCommand('onOk', '确定', 'onOk', null);
			addCloseCommand();
		}
		var bodyStyle = document.body.style;
		bodyStyle.paddingLeft = bodyStyle.paddingRight
			= (document.body.offsetWidth - m_fpCfg.size[0])/2;
		bodyStyle.paddingTop = bodyStyle.paddingBottom
			= (document.body.offsetHeight - m_fpCfg.size[1])/2;
	});
	Event.observe(window, 'unload', function(){
		document.onclick = null;
		document.onkeydown = null;
	});
	function findCommand(target){
		while(target!=null && target.tagName!='BODY'){
			if(target.className=='command_btn')return target;
			target = target.parentNode;
		}
		return null;
	}
	document.onclick = function(event){
		event = event || window.event;
		var target = event.target || event.srcElement;
		target = findCommand(target);
		if(target==null)return;
		if(target.disabled)return false;
		var cmdId = target.id;
		var cmdInfo = m_oCommands[cmdId];
		if(cmdInfo.fn=='_close_'){
			window.opener = null;
			window.close();
			return;
		}
		var retVal = window[cmdInfo.fn].apply(cmdInfo.scope, []);
		if(retVal!==false){
			window.opener = null;
			window.close();
		}
		return false;
	};
}
function setFPCmdDisable(_cmdName, disable, hide){
	if(parent.setCmdDisable){
		return parent.setCmdDisable(_cmdName, disable, hide);
	}
	var sBtnId = 'ipt_'+_cmdName;
	var oButton = document.getElementById(sBtnId);
	if(!oButton)return;
	oButton.disabled = disable;
	oButton.parentNode.style.display = hide===true ? 'none' : '';
}
function notifyFPCallback(){
	if(Ext.isFunction(parent.m_winFromCallback)){
		window.__notifyFPResult = parent.m_winFromCallback.apply(null, arguments);
		return;
	}
	window.__notifyFPResult = true;
}
var FloatPanel = {
	close : function(){
		closeWindow();
	},
	hide : function(){
		Element.hide(parent.window.frameElement);
	},
	addCommand : function(){
	},
	setTitle : function(_title){
		if(parent.replaceTitleWith){
			return parent.replaceTitleWith(_title);
		}
		window.title = _title;
	},
	disableCommand : setFPCmdDisable,
	dialogArguments : (function(){
		return parent.m_winDialogArguments || {};
	})()
}
function closeWindow(){
	if(window.__notifyFPResult===false)return;
	if(Ext.isFunction(parent.closeMe)){
		parent.closeMe();
	}
	else if(window.frameElement){
		window.frameElement.src = Ext.isSecure?SSL_SECURE_URL:'';
	}else{
		window.opener = null;
		window.close();
	}
}
Event.observe(document, 'keydown', function(event){
	event = event || window.event;
	if(event.keyCode==27)FloatPanel.close();
});
