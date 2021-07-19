Object.extend(PageContext,{	
	isLocal : false,		
	ObjectServiceId : 'wcm6_contentlink',
	ObjectMethodName : 'query',
	AbstractParams : {
			"SelectFields" : "",
			"channelid" : getParameter("channelid"),			
			"RightValue"	: getParameter("RightValue") || "0000000000000000"
	},	

	//为了右侧显示操作栏和属性栏,定义右侧面板的类型
	ObjectType	: 'contentlink',	
	loadTypeDesc : function(){		
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		oHelper.Call(this.ObjectServiceId,"queryLinkType",this.params,true,function(_transport,_json){						
			var typedesc = $v(_json['CONTENTLINKTYPE'],"TypeName",false) || "";				
			if(typedesc == null || typedesc.length <= 0){
				typedesc = "没有设置热词";
			}else{
				typedesc += "(" + $v(_json['CONTENTLINKTYPE'],"TypeDesc",false) + ")";
			}
			
			Element.update($("typedesc"),typedesc);			
		});
	},
	_doBeforeRefresh : function(_params){
		var p = _params.toQueryParams();
		Object.extend(this.params,{channelid:p.channelid});
		PageContext.drawLiterator('literator_path',"channelid="+p.channelid+"&NotTraceSite=true");		
	}
});

Object.extend(PageContext.PageNav,{
	UnitName : '个',
	TypeName : '热词'
});

Object.extend(Grid,{
	draggable : true,
	
	_hasRight : function(_sOperation){
		return true;
	},	

	//使用传入的权限
	_getRight : function(){
		return getParameter("RightValue");
	},
	_dealWithSelectedRows : function(){
		var checkedIds = this.getRowIds();	
		var rightValue = getRightFromHost();
		var oParams = Object.extend(PageContext.params,{"ObjectIds":checkedIds,"objecttype":"contentlink","ObjectRights":rightValue});

		$MessageCenter.sendMessage('oper_attr_panel','PageContext.response',"PageContext",oParams,true,true);
	}
});

Object.extend(com.trs.wcm.ListDragger.prototype,{
	_getHint : function(_eRoot){
		//
		if(!top.DragAcross){
			top.DragAcross = {};
		}
		//TODO channelid处理
		var sLinkId = _eRoot.getAttribute("grid_rowid",2);
		var sLinkName = _eRoot.getAttribute("grid_rowname",2);
		var aCheckboxs = document.getElementsByName("LinkId");
		var bCurrSelected = false;
		var aSelectDocIds = [];
		for(var i=0;i<aCheckboxs.length;i++){
			if(!aCheckboxs[i].checked)continue;
			if(aCheckboxs[i].value==sLinkId){
				bCurrSelected = true;
			}
			aSelectDocIds.push(aCheckboxs[i].value);
		}
		if(!bCurrSelected){
			aSelectDocIds.push(sLinkId);
		}	
		
		if(PageContext.params["OrderBy"]){
			return "自动排序列表不支持手动排序";
		}

		return sLinkName+"[热词-"+sLinkId+"]";
	},
	_isSortable : function(_eRoot){		
		if(!PageContext.params["OrderBy"]){
			var sRight = _eRoot.getAttribute("right",2);
			var nRightIndex = _eRoot.getAttribute("sortableRightIndex",2);
			if(sRight!=null&&!isAccessable4WcmObject(sRight,nRightIndex)){
				return false;
			}
			return true;
		}
		return false;
	},
	_move : function(_eCurr,_iPosition,_eTarget){
		//_iPosition表示当前元素相对于目标元素的位置,1表示之前,0表示之后
		if(!PageContext.params["OrderBy"]){
			var linkId = _eCurr.getAttribute('grid_rowid', 2);
			var targetLinkId = _eTarget.getAttribute('grid_rowid', 2);				
			var newOrder = _eTarget.getAttribute("LinkOrder",2);
			if(_iPosition == 1){
				newOrder++;
			}

			if(confirm('您确定要调整热词的顺序？')){									
				var LastNextSibling = this.lastNextSibling;				
				var oPostData = {LinkId:linkId,NewOrder:newOrder,ChannelId:PageContext.params.channelid};
				BasicDataHelper.call("wcm6_contentlink","changeOrder",oPostData,false,function(){
					PageContext.RefreshList();
				});

//				Grid.adjustColors();
				delete _eCurr;
				delete _eNext;
				return true;
			}				
		}
		return false;		
	}
});
function getRightFromHost(){
	return $nav().getCurrElementInfo()["RightValue"];
}

//override the trace method for the channel link path.
function trace(_id,_isSite, rightValue, channelType){	
	if(_isSite){		
		return;
	}

	var url = "contentlink_index.html?channelid=" + _id;
	if(channelType != undefined){
		url += "&ChannelType=" + channelType;
	}

	if(rightValue != undefined){
		url += "&RightValue=" + rightValue;
	}	
	$changeSheet('contentlink/' + url);
//	window.location.assign(url);
	//PageContext.drawLiterator('literator_path');
	// ge gfc add @ 2007-4-3 17:15 加入页面切换的过度页面
	if((top.actualTop || top).RotatingBar) {
		(top.actualTop || top).RotatingBar.start();
	}
}

Event.observe(window,'load',function(){	
	PageContext.LoadPage();
	PageContext.drawLiterator('literator_path');
	//PageContext.loadTypeDesc();
	/*
	var r = PageContext.refresh;
	var rr = function(_params){		
		r(_params);
		PageContext.loadTypeDesc();
	};

	PageContext.refresh = rr;*/
});