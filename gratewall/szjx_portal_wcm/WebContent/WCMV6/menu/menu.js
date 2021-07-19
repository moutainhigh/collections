Object.extend(PageContext, {
	showOnliners : function(){
		TRSCrashBoard.setMaskable(false);
		if(this.m_oShowDialog == null) {
			this.m_oShowDialog = TRSDialogContainer.register('Onliners_Show', '在线用户列表', 
				'./communications/onliners.html', '200', '350');
		}
		var positions = Position.getAbsolutePositionInTop(Event.element(Event.findEvent()));		
		TRSDialogContainer.display('Onliners_Show', null, positions[0] - 200, positions[1] + 15);
		TRSCrashBoard.setMaskable(true);
	},
	notifyAlertions : function(){
		$MessageCenter.sendMessage('alertions', 'Messager.reset', 'Messager', null, true, true);
		MenuOperates.showSMmList();
	},
	logout : function(){
		
	},
	showOrderTypes : function(_evt){
		if(this.m_eBubbleList == null) {
			var div = document.createElement('<div class="listMenuContainer"></div>');
			/*div.innerHTML = '<div class="listMenu"><ul class="listItems" id="list_items">\
				<li><a href="#" onclick="PageContext.renderItemSelect(1); return false;">在线短消息</a></li>\
				<li><a href="#" onclick="PageContext.renderItemSelect(2); return false;">发布信息</a></li>\
				<li><a href="#" onclick="PageContext.renderItemSelect(3); return false;">工作流提示</a></li>\
				<li class="item_sp"><a href="#" onclick="PageContext.renderItemSelect(0); return false;">设置</a></li></ul></div>';
			//*/
			div.innerHTML = '<div class="listMenu"><ul class="listItems" id="list_items">\
				<li><a href="#" onclick="PageContext.renderItemSelect(1); return false;">在线短消息</a></li>\
				<li class="item_sp"><a href="#" onclick="PageContext.renderItemSelect(0); return false;">设置</a></li></ul></div>';

			div = document.body.appendChild(div);
			this.m_eBubbleList = new com.trs.wcm.BubblePanel(div);
		}
		var event = window.event || _evt;
		var x = Event.pointerX(event)+4;
		var y = Event.pointerY(event)+4;
		this.m_eBubbleList.bubble([x,y],function(_Point){
			return [_Point[0]- this.offsetWidth, _Point[1]];
		});
		//showHelpTip(_evt, sOrderTypes, false);
	},
	renderItemSelect : function(_type){
		this.m_eBubbleList.hide();
		switch(_type){
			case 0:
				MenuOperates.individuate("msg");
				break;
			case 1:
				$openCentralWin('./communications/short_msg_list.html', '在线短消息列表', false, true);
				break;
			default:
				// TODO
				alert('尚未实现！');
				break;
		}
		//hideHelpTip($('lnkFirer'));		
	},
	skipToUserInfo : function(){
		MenuOperates.skipTo({Path: 'myInformation,0'});
	}
});
