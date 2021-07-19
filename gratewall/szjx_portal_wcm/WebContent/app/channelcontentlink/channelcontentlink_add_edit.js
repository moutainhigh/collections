//Validation的附加部分，主要用于处理多语种问题
var m_arrValidations = [{
		renderTo : 'linkname',
		desc : wcm.LANG.CHANNELCONTENTLINK_FN_11 || '名称',
		message :  wcm.LANG.CHANNELCONTENTLINK_FN_12 || '名称最大长度为30,不区分大小写'
	}, {
		renderTo : 'linkurl',
		desc :  wcm.LANG.CHANNELCONTENTLINK_FN_13 || '链接',
		message : String.format('链接格式：protocol://host:port/path'),
		warning : String.format('链接格式：protocol://host:port/path')
	}, {
		renderTo : 'linktitle',
		desc: wcm.LANG.CHANNELCONTENTLINK_FN_15 || '描述'
	}
];

var PageContext = {};
Object.extend(PageContext,{
	serviceId : 'wcm6_contentlink',	
	params    : {channelid:getParameter('channelid'),siteid:getParameter('siteid'),ObjectId:getParameter("ObjectId"),PageSize:-1},
	loadProps : function(){		
		var self = this;
		self.loadLinks();
		self.initValidation();
		try{
			$('linkname').select();
		}catch(ex){
			//Just skip it.
		}
	},
	loadLinks : function(){	
		var selLinkorder = $("sellinkorder");
		var nCurrOrder = $F("LinkOrder");	
		selLinkorder.value = nCurrOrder;
		selLinkorder.selectedIndex -= 1; 
		selLinkorder.options[selLinkorder.selectedIndex].setAttribute("value", nCurrOrder);
		if(selLinkorder.options.remove){//IE
			selLinkorder.options.remove(selLinkorder.selectedIndex + 1);
		}else{//Not IE
			selLinkorder.removeChild(selLinkorder.options[selLinkorder.selectedIndex + 1]);
		}
	},	
	initValidation : function(){
		ValidationHelper.registerValidations(m_arrValidations);
		ValidationHelper.addValidListener(function(){
				FloatPanel.disableCommand('SaveContentLink', false);
			}, "addEditForm");
			ValidationHelper.addInvalidListener(function(){
				FloatPanel.disableCommand('SaveContentLink', true);
			}, "addEditForm");
		ValidationHelper.initValidation();
	}
});

function exitsSimilar(){
	var channelId = PageContext.params.channelid;
	var siteId = PageContext.params.siteid;
	var linkName =$F("linkname"); 
	if(parseInt($F("linkid"))>0 && $('OldLinkName').value == linkName)return;
	var oData = {LinkName:encodeURIComponent(linkName),ChannelId:channelId,SiteId:siteId};
	var ajaxRequest = new Ajax.Request(
		"channelcontentlink_get_by_name.jsp",
		{method:'get', asynchronous:false, parameters:$toQueryStr(oData)}
	);
	
	var isNotLogin = ajaxRequest.header('TRSNotLogin');
	if(isNotLogin=='true'&&window.top.DoNotLogin){
		window.top.DoNotLogin();
		return;
	}
	var result = ajaxRequest.transport.responseText;
	if(result.trim() != "null"){				
		ValidationHelper.failureRPCCallBack( String.format(wcm.LANG.CHANNELCONTENTLINK_FN_1 || ("热词[{0}]已存在(不区分大小写)!"),linkName));		
	}else{
		 ValidationHelper.successRPCCallBack();
	}
}

function SaveContentLink(){	
	if(!ValidationHelper.doValid('addEditForm')){
		return false;
	}
	var pattern = /^[\w\u4e00-\u9fa5]*$/;
	if(!pattern.test($F("linkname"))){
		Ext.Msg.alert(wcm.LANG.CHANNELCONTENTLINK_FN_30 || "热词名称只能由汉字、字母、数字、下划线组成！");
		return false;
	}
	if(PageContext.params.ObjectId > 0){
		editContentLink();
		return false;
	}
	var oPostData = {
		LinkName:$F("linkname"),
		LinkTitle:$F("linktitle"),
		LinkUrl:$F("linkurl"),
		LinkOrder:$F("sellinkorder"),
		ChannelId:PageContext.params.channelid,
		SiteId:PageContext.params.siteid,
		IsUsedInChildren:getUsedChildrenValue()
	};
	BasicDataHelper.call("wcm6_contentlink","addLinkInChannel",oPostData,true,function(_transport,_json){
		//$MessageCenter.sendMessage("main",'PageContext.RefreshList','PageContext',[]);
		notifyFPCallback(parseInt(_transport.responseText, 10));	
		FloatPanel.close();
	});
	return false;
}
function editContentLink(){
	var oPostData = {
		LinkName:$F("linkname"),
		LinkTitle:$F("linktitle"),
		LinkUrl:$F("linkurl"),
		LinkID: parseInt($F("linkid")),
		LinkOrder:$F("sellinkorder"),
		ChannelId:PageContext.params.channelid,
		SiteId:PageContext.params.siteid,
		IsUsedInChildren:getUsedChildrenValue()
	};
	BasicDataHelper.call("wcm6_contentlink","editLinkInChannel",oPostData,true,function(_transport,_json){
		//$MessageCenter.sendMessage("main",'PageContext.RefreshList','PageContext',[]);
		notifyFPCallback(parseInt(_transport.responseText, 10));	
		FloatPanel.close();	
	});
	return false;
}
function getUsedChildrenValue(){
	if(!$('bUsedInChildren'))
		return 0;
	return $('bUsedInChildren').checked ? 1 : 0;
}

Event.observe(window,'load',function(){	
	var params = PageContext.params;
	if(params.ObjectId >0 ){	
		LockerUtil.register2(PageContext.params.ObjectId,106,true,"SaveContentLink");//106 is the wcmobjtype of contentlink
		PageContext.loadProps();
	}else{
		var dom = $("sellinkorder");
		var opts = dom.options;
		dom.selectedIndex = (opts.length - 1);
		PageContext.initValidation();
//		try{
//			$('linkname').focus();
//		}catch(ex){
//			//Just skip it.
//		}	
	};
	params = null;	
});