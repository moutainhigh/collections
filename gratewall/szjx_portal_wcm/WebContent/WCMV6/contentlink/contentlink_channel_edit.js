Object.extend(PageContext,{
	serviceId : 'wcm6_contentlink',	
	params    : {channelid:getParameter('channelid'),ObjectId:getParameter("ObjectId")},
	loadProps : function(){		
		var self = this;
		BasicDataHelper.Call(this.serviceId,"findById",this.params,false,function(_transport,_json){
			var sValue = TempEvaler.evaluateTemplater('template_link', _json);			
			Element.update($("link"),sValue);	
			self.loadLinks();
			self.initValidation();
			try{
				$('linkname').select();
			}catch(ex){
				//Just skip it.
			}
		});
	},
	loadLinks : function(){	
		var oPostData = {PageSize:-1};
		Object.extend(oPostData,this.params);
		BasicDataHelper.Call(this.serviceId,"query",oPostData,false,function(_transport,_json){								
			var sValue = TempEvaler.evaluateTemplater('template_linkorder', _json);				
			Element.update($("linkorderholder"),sValue);					
			//$("sellinkorder").value = $F("LinkOrder");
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
		});		
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
	var channelId = $F("ChannelId");
	if(channelId <= 0) return;
	var linkName =$F("linkname"); 
	var oData = {LinkName:encodeURIComponent(linkName),LinkId:$F("LinkId"),ChannelId:channelId};
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


function editContentLink(){	
	BasicDataHelper.call("wcm6_contentlink","editLinkInChannel","addEditForm",true,function(_transport,_json){
		$MessageCenter.sendMessage("main",'PageContext.RefreshList','PageContext',[]);
		FloatPanel.close(true);	
	});
	FloatPanel.close();	
	return false;
}

//becaus the template must be a textarea,the element can't nested.
//so draw it.
function drawArea(_value){
	var sHtml = '<textarea type="text" id="linktitle" name="linktitle" class="input_textarea" scroll="auto" cols="20" rows="4"'
	sHtml += "validation=\"type:'string',desc:'描述',max_len:'100',showid:'validation_tip'\">";
	sHtml += _value;
	sHtml += "</textarea>";
	return sHtml;
}

function resetLinkOrder(_sel){
	$("LinkOrder").value = _sel.value;
}

Event.observe(window,'load',function(){	
	LockerUtil.register2(PageContext.params.ObjectId,106,true,"savebtn");//106 is the wcmobjtype of contentlink
	PageContext.loadProps();
});