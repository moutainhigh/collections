var FloatPanel = {
	CLOSE_COMMAND: 'close_command_btn',
	show : function(){
		$('floatpanel').contentWindow.document.body.style.visibility = 'visible';
	},
	hide : function(){
		$('floatpanel').contentWindow.document.body.style.visibility = 'hidden';
	},
	open : function(_src,_sTitle,_iWidth,_iHeight,_fWhenClose){
		(top.actualTop || top).cancelKeyDown = true;
		if(window.showCoverAll)window.showCoverAll(898);
		var ePanel = $('floatpanel');
		ePanel.style.zIndex = 899;
		ePanel.style.display = '';
		$('floatpanel').contentWindow.openMe(_src,_sTitle,_iWidth,_iHeight);
		FloatPanel.doWhenClose = _fWhenClose;

		FloatPanel.m_hCommands = {};
	},
	openIn : function(_src,_sTitle,_iWidth,_iHeight,_fWhenClose){
		var ePanel = $('floatpanel');
		ePanel.style.zIndex = 899;
		ePanel.style.display = '';
		$('floatpanel').contentWindow.openMe(_src,_sTitle,_iWidth,_iHeight);
		FloatPanel.doWhenClose = _fWhenClose;

		FloatPanel.m_hCommands = {};
	},
	center : function(){
	},
	close : function(_bActual){
		(top.actualTop || top).cancelKeyDown = false;
		//alert('before close')
		if(window.hideCoverAll)window.hideCoverAll();
		$('floatpanel').contentWindow.closeMe(_bActual);

		try{
			FloatPanel.removeAllCommands();
		}catch(err){
			//TODO logger
//			alert(err.message);
		}
		window.focus();
	},
	setTitle : function(_sTitle, _sDesc){
		$('floatpanel').contentWindow.setTitle.apply(null,arguments);
	},
	setSize : function(_iWidth,_iHeight){
		$('floatpanel').contentWindow.setSize.apply(null,arguments);
	},
	sizeBy : function(_dW,_dH){
		$('floatpanel').contentWindow.sizeBy.apply(null,arguments);
	},
	addCommand : function(_sCmdId,_sCmdHtml,_fnCmd,_oScope,_arrArgs){
		if(FloatPanel.m_hCommands[_sCmdId]) {
			//alert('had cmd-' + _sCmdId + ', ' + FloatPanel.m_hCommands[_sCmdId])
			return;
		}
		//else
		$('floatpanel').contentWindow.addCommand.apply(null,arguments);
		FloatPanel.m_hCommands[_sCmdId] = true;
	},
	addCloseCommand : function(_sCmdHtml){
		if(FloatPanel.m_hCommands[FloatPanel.CLOSE_COMMAND]) {
			return;
		}
		//else
		$('floatpanel').contentWindow.addCloseCommand.apply(null,arguments);
		FloatPanel.m_hCommands[FloatPanel.CLOSE_COMMAND] = true;
	},
	removeCommand : function(_sCmdId){
		$('floatpanel').contentWindow.removeCommand.apply(null,arguments);
		delete FloatPanel.m_hCommands[_sCmdId];
	},
	removeAllCommands : function(){
		//alert('close all cmds')
		$('floatpanel').contentWindow.removeAllCommands();
		if(FloatPanel.m_hCommands[FloatPanel.CLOSE_COMMAND]) {
			FloatPanel.removeCloseCommand();
		}
		FloatPanel.m_hCommands = {};
	},
	removeCloseCommand : function(){
		$('floatpanel').contentWindow.removeCloseCommand();
		delete FloatPanel.m_hCommands[FloatPanel.CLOSE_COMMAND];
	},
	setAfterClose : function(){
		var flag = arguments[0];
		if(flag == null) return;
		if(typeof(flag) == 'string') {
			$('floatpanel').contentWindow.setSendingMsg(arguments);
		}else if(typeof(flag) == 'function') {
			$('floatpanel').contentWindow.addOnBeforeCloseListener(flag);
		}
	},
	disableCommand : function(_sCmdId, _disabled, _bHide){
		$('floatpanel').contentWindow.setCommandDisable.apply(null,arguments);
	},
	disableCloseCommand : function(){
		this.disableCommand("_close_",true);
	},
	enableCloseCommand : function(){
		this.disableCommand("_close_",false);
	}
};
Event.observe(window,'load',function(){
	if(!$('floatpanel')){
		var pbIframe = document.createElement('IFRAME');
		pbIframe.id = 'floatpanel';
		pbIframe.frameBorder = 0;
		pbIframe.src = com.trs.util.Common.BASE + '../include/window.html';
		pbIframe.style.cssText = "position:absolute;left:0;top:0;width:100%;height:100%;display:none";
		pbIframe.allowTransparency = true;
		document.body.appendChild(pbIframe);
	}
});