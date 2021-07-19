//底部标签页的设置
var FooterTabDealer = {
	container : null,
	topFooterWin : null,//主窗口中footer窗口对象
	footerTabSystem : null,
	footerTabSite : null,
	footerTabChannel : null,
	serviceName : 'wcm6_individuation',
	saveMethod : 'save',

	/*
	*返回保存的变量名值对序列
	*/
	setSaveParams : function(aCombine, oHelper){
		//TODO checkValue
	},

	getSystemFooterSelect : function(){
		var systemTab = wcm.TabManager.getTabs(WCMConstants.TAB_HOST_TYPE_WEBSITEROOT, true).items;
		var str = '<select name="defaultSystemSheet" id="defaultSystemSheet" style="width:100px;height:18px;">';
		for(var index=0; index<systemTab.length;index++){
			str +='<option value="' + systemTab[index].type + '">' + 
				Ext.kaku(systemTab[index].desc, null, null) + '</option>';
		}
		str += '</select>';		
		return str;
	},

	getSiteFooterSelect : function(){
		var siteTab = wcm.TabManager.getTabs(WCMConstants.TAB_HOST_TYPE_WEBSITE, true).items;
		var str = '<select name="defaultSiteSheet" id="defaultSiteSheet" style="width:100px;height:18px;">';
		for(var index=0; index < siteTab.length; index++){
			str += '<option value="' + siteTab[index].type + '">' + 
				Ext.kaku(siteTab[index].desc, null, null) + '</option>';
		}
		str += '</select>';		
		return str;		
	},

	getChannelFooterSelect : function(){
		var channelTab = wcm.TabManager.getTabs(WCMConstants.TAB_HOST_TYPE_CHANNEL,true).items;
		var str = '<select name="defaultChannelSheet" id="defaultChannelSheet" style="width:100px;height:18px;">';
		for(var index = 0; index < channelTab.length; index++){
			str += '<option value="' + channelTab[index].type + '">' + 
				Ext.kaku(channelTab[index].desc, null, null) + '</option>';
		}
		str += '</select>';		
		return str;				
	},

	initFooterTabEvent : function(){
		//TODO初始化默认控件的事件
	},

	initFooterTabValue : function(){
		this.footerTabSystem.innerHTML = this.getSystemFooterSelect();
		this.footerTabSite.innerHTML = this.getSiteFooterSelect();
		this.footerTabChannel.innerHTML = this.getChannelFooterSelect();
		IndividualDealer.initConfigs(this.container);
	},

	initFooterTab : function(){//初始化Footer页
		this.container = $('footerDiv');
		//this.topFooterWin = topHandler.$MessageCenter.getFooter();
		this.footerTabSystem = $('footerTabSystem');
		this.footerTabSite = $('footerTabSite');
		this.footerTabChannel = $('footerTabChannel');
		this.initFooterTabEvent();
		this.initFooterTabValue();
	}
};

//点击个性化设置页面的“底部标签页”节点时，触发的动作
function footerTabLoad(){
	if(!FooterTabDealer.isLoaded){
		FooterTabDealer.initFooterTab();
		FooterTabDealer.isLoaded = true;
	}
}

//Event.observe(window, 'load', FooterTabDealer.initFooterTab.bind(FooterTabDealer));