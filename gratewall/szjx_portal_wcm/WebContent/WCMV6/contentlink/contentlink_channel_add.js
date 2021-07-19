Object.extend(PageContext,{
	serviceId : 'wcm6_contentlink',	
	params    : {channelid:getParameter('channelid'),PageSize:-1},
	loadLinks : function(){				
		BasicDataHelper.Call(this.serviceId,"query",this.params,false,function(_transport,_json){			
			var sValue = TempEvaler.evaluateTemplater('template_linkorder', _json);
			Element.update($("linkorder"),sValue);					
		});
		this.initValidation();	
	},	
	initValidation : function(){
		ValidationHelper.addValidListener(function(){
				FloatPanel.disableCommand('savebtn', false);
			}, "addEditForm");
			ValidationHelper.addInvalidListener(function(){
				FloatPanel.disableCommand('savebtn', true);
			}, "addEditForm");
		ValidationHelper.initValidation();
	}
});

function exitsSimilar(){
	var channelId = PageContext.params.channelid;
	if(channelId <= 0) return;
	var linkName =$F("linkname"); 
	var oData = {LinkName:encodeURIComponent(linkName),ChannelId:channelId};
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
		ValidationHelper.failureRPCCallBack("热词["+linkName+"]已存在(不区分大小写)!");		
	}else{
		 ValidationHelper.successRPCCallBack();
	}
}

function addContentLink(){	
	var oPostData = {
		LinkName:$F("linkname"),
		LinkTitle:$F("linktitle"),
		LinkUrl:$F("linkurl"),
		LinkOrder:$F("sellinkorder"),
		ChannelId:PageContext.params.channelid
	};
		
	BasicDataHelper.call("wcm6_contentlink","addLinkInChannel",oPostData,true,function(_transport,_json){
		$MessageCenter.sendMessage("main",'PageContext.RefreshList','PageContext',[]);
		FloatPanel.close(true);	
	});
	FloatPanel.close();	
	return false;
}

Event.observe(window,'load',function(){	
	PageContext.loadLinks();
	try{
		$('linkname').focus();
	}catch(ex){
		//Just skip it.
	}
});