Object.extend(PageContext,{
	serviceId : 'wcm6_contentlink',	
	selectedTypes : [],
	params    : {channelid:getParameter('channelid')},
	loadTypes : function(){		
		BasicDataHelper.Call(this.serviceId,"queryTypes",this.params,true,function(_transport,_json){
			PageContext.PageCount = _json["CONTENTLINKTYPES"]["PAGECOUNT"];
			PageContext.RecordNum = _json["CONTENTLINKTYPES"]["NUM"];
			PageContext.PageSize = _json["CONTENTLINKTYPES"]["PAGESIZE"];			
			
			var sValue = TempEvaler.evaluateTemplater('type_select_template', _json);
			Element.update($("type_select"),sValue);			
			PageContext.drawNavigator();
		});
	},
	loadCurrentType : function(){
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		oHelper.Call(this.serviceId,"queryLinkType",this.params,true,function(_transport,_json){			
			PageContext.currentLinkType = $v(_json['CONTENTLINKTYPE'],"ContentlinkTypeId",false) || 0;

			var sValue = $v(_json['CONTENTLINKTYPE'],"TypeName",false);						
			if(!sValue){
				sValue = "无";
			}

			//Element.update($("current_linktype"),sValue);			
		});
	},
	setCurrentLinkType : function(_typeid){
		var params = {"ContentLinkTypeId":_typeid,"ChannelId":this.params.channelid};			
		BasicDataHelper.Call(PageContext.serviceId,"setLinkType",params,true,function(_transport,_json){		
			$MessageCenter.sendMessage("main",'PageContext.RefreshList','PageContext',$toQueryStr(PageContext.params));
			FloatPanel.close(true);		
		});
		FloatPanel.close();
		return false;
	},
	importSysLinks : function(){
		if(this.selectedTypes.length ==0){
			return true;
		}else{
			var params = {"ContentLinkTypeIds":this.selectedTypes,"ChannelId":this.params.channelid};
			BasicDataHelper.Call(PageContext.serviceId,"importSysLinks",params,true,function(_transport,_json){		
				$MessageCenter.sendMessage("main",'PageContext.RefreshList','PageContext',$toQueryStr(PageContext.params));
				FloatPanel.close(true);		
			});
		}
		FloatPanel.close();
		return false;
	}
});

Object.extend(PageContext.PageNav,{
	UnitName : '个',
	TypeName : '热词分类',
	go : function(_iPage,_maxPage){
		if(_iPage>_maxPage){
			_iPage = _maxPage;
		}
		Object.extend(PageContext.params,{"CurrPage":_iPage});
		PageContext.loadTypes();
	}
});

function recordValue(_checkbox){
	if(!_checkbox) return;
	var v = _checkbox.value;
	if(_checkbox.checked){
		PageContext.selectedTypes.push(v);
	}else{
		PageContext.selectedTypes._remove(v);
	}
}

function setLinkType(){
	/*
	var types = document.getElementsByName("LinkType");
	var type = null;
	var typeid = 0;	
	for(var i=0,len=types.length; i<len; i++){
		type = types[i];
		if(type.checked){
			typeid = type.value;
			break;
		}
	}
	
	if(typeid == PageContext.currentLinkType){
		return true;
	}

	if(typeid == 0){
		var sHtml = "<div style='padding-left:10px;text-indent:20px;font-weight:bold'>";
		sHtml += "没有选择一个分类!<div style='padding-top:5px'>取消栏目的热词设置?</div>";
		sHtml += "</div>";
		$confirm(sHtml,function(){
				$dialog().hide()
				PageContext.setCurrentLinkType(typeid);
			},function(){$dialog().hide()},"设置确认");
			
		return false;
	}*/	

	//PageContext.setCurrentLinkType(typeid);
	PageContext.importSysLinks();
	
	return false;
}

Event.observe(window,'load',function(){
	PageContext.loadTypes();	
});