Object.extend(PageContext,{
	init: function(_params){
		Object.extend(this.params,_params || {});
		this.serviceId = "wcm6_extendfield";

		this.fOnSuceesses = {};		

		this.fOnSuceesses['Extendfields'] = function(_transport,_json){
			PageContext.PageCount = _json["CONTENTEXTFIELDS"]["PAGECOUNT"];
			PageContext.RecordNum = _json["CONTENTEXTFIELDS"]["NUM"];
			PageContext.PageSize = _json["CONTENTEXTFIELDS"]["PAGESIZE"];			
			PageContext.drawNavigator();
			
			var iStartIndex = parseInt(PageContext.params["CurrPage"]-1)*parseInt(PageContext.PageSize);
			var sValue = TempEvaler.evaluateTemplater('template_extendfields', _json,{"START_INDEX":iStartIndex});
			Element.update($('grid_data'), sValue);

			// ge gfc add @ 2007-4-3 17:09 如果有RotatingBar显示，先隐藏之
			if((top.actualTop || top).RotatingBar) {
				(top.actualTop || top).RotatingBar.stop();
			}
			
			Grid.init(!PageContext.loaded);
			Grid.bindEvents(!PageContext.loaded);
			Grid.selectRows([]);
			PageContext.loaded = true;
		}
	},	
	loadExtendfields : function(){			
		try{			
			var hostId = getParameter("channelid");
			var serviceParams;
			if(hostId > 0){
				serviceParams = {"HostId":hostId,"HostType":"101","channelid":hostId};
			}

			hostId = getParameter("siteid");
			if(!serviceParams && hostId > 0){
				serviceParams = {"HostId":hostId,"HostType":"103","siteid":hostId};
			}

			if(!serviceParams){
				hostId = getParameter("SiteType");
				serviceParams = {"HostId":hostId,"HostType":"1","sitetype":hostId};
			}
			if("true" == getParameter("ContainsChildren") || m_DelInRow == "true"){
				Object.extend(serviceParams,{"ContainsChildren":"true"});
				m_DelInRow = "false";
			}
			
			Object.extend(serviceParams, {FieldsToHTML:'LOGICFIELDDESC'});						
			Object.extend(this.params,serviceParams);						
			var oHelper = new com.trs.web2frame.BasicDataHelper();
			oHelper.Call(this.serviceId,'query',
				this.params,true,this.fOnSuceesses['Extendfields']);			
		}catch(err){
			//TODO logger
			//alert(err.message);
		}		
	},
	queryWithChildren : function(){		
		var zShowChildren = (this.params.ContainsChildren == "true");
		if(zShowChildren){
			Object.extend(this.params,{"ContainsChildren":"false"});
			$("show_children").title = "显示子对象的扩展字段";
			$("modeswitcher").className = "fold_mode";
		}else{
			Object.extend(this.params,{"ContainsChildren":"true"});	
			$("show_children").title = "隐藏子对象的扩展字段";
			$("modeswitcher").className = "unfold_mode";
		}

		this.params.CurrPage = 1;//go to first page.
		this.loadExtendfields();
	},
	updateCurrRows : function(){
		var aRowIds = Grid.getRowIds();
		try{			
			var oHelper = new com.trs.web2frame.BasicDataHelper();
			oHelper.Call(PageContext.serviceId,'query',
				PageContext.params,true,function(_transport,_json){				
					var sValue = TempEvaler.evaluateTemplater('template_extendfields', _json);
					Element.update($('grid_data'), sValue);
					Grid.init();
					Grid.bindEvents();
					Grid.selectRows(aRowIds,true);
			});
		}catch(err){
			//TODO logger
			//alert(err.message);
		}
	}	
});
PageContext.init();

Object.extend(PageContext.PageNav,{
	UnitName : '个',
	TypeName : '扩展字段',
	go : function(_iPage,_maxPage){
		if(_iPage>_maxPage){
			_iPage = _maxPage;
		}
		Object.extend(PageContext.params,{"CurrPage":_iPage});
		PageContext.loadExtendfields();
	}
});

var m_DelInRow = "false";
var Grid = new com.trs.wcm.Grid('grid');
Object.extend(Grid,{
	_sort : function(_sSortField,_sSortOrder){
		Object.extend(PageContext.params,{"orderby":(_sSortField+' '+_sSortOrder)});
		PageContext.loadExtendfields();
	},
	_exec : function(_sFunctionType,_sRowId,_eRow,_eSrcElement){
		var funcName = _sFunctionType.toLowerCase();
		if(funcName == "delete"){
			$ExtendFieldMgr[funcName](_sRowId,_eRow,_eSrcElement);
			m_DelInRow = PageContext.params.ContainsChildren;
		}else{
			$ExtendFieldMgr[funcName](_sRowId,PageContext.params);
		}
	},
	_dealWithSelectedRows : function(){
		var checkedIds = this.getRowIds();	
		//TODO 权限处理
		$MessageCenter.sendMessage('oper_attr_panel','PageContext.response',"PageContext",
			Object.extend(PageContext.params,{"ObjectIds":checkedIds,"objecttype":"extendfield","ObjectRights":["11111111111111111111111111111111111111111111111111111111111111111"]}),true,true);
	},
	ctrlA : function(event){
		Grid.toggleAllRows();
		return false;
	},
	keyUp : function(event){
		Grid.selectPreRow(event.ctrlKey);
	},
	keyDown : function(event){
		Grid.selectNextRow(event.ctrlKey);
	},
	keyD : function(event){		
		var pSelectedIds = this.getRowIds();		
		if( pSelectedIds && pSelectedIds.length >= 1 ){
			m_DelInRow = PageContext.params.ContainsChildren;
			if(pSelectedIds.length == 1){
				$ExtendFieldMgr['delete'](pSelectedIds[0],this.getRows()[0]);
			}else{
				$ExtendFieldMgr['delete'](pSelectedIds);
			}
		}
		
	},
	keyDelete : function(event){
		this.keyD(event);
	},
	ctrlE : function(event){		
		var pSelectedIds = this.getRowIds();		
		if( pSelectedIds && pSelectedIds.length == 1 ){
			$ExtendFieldMgr.edit(pSelectedIds[0], PageContext.params);
		}		
	},
	ctrlN : function(event){
		$ExtendFieldMgr.edit(0, PageContext.params);
	}
});

//override the trace method for the channel link path.
function trace(_id,_isSite, rightValue, channelType){
	var url = "extendfield_index.html?"
	if(_isSite){
		url += "siteid=" + _id;
	}else{
		url += "channelid=" + _id;
		if(channelType != undefined){
			url += "&ChannelType=" + channelType;
		}
	}
	if(rightValue != undefined){
		url += "&RightValue=" + rightValue;
	}
	$changeSheet('extendfield/' + url);
	//window.location = url;

	// ge gfc add @ 2007-4-3 17:15 加入页面切换的过度页面
	if((top.actualTop || top).RotatingBar) {
		(top.actualTop || top).RotatingBar.start();
	}
}


function formatTime(_time){
	var times = _time.split(" ");
	return times[0];
}

//for search.
var FIELD_NAME = "..输入字段名称";
var FIELD_LOGIC_NAME = "..输入显示名称";
Event.observe(window,'load',function(){	
		PageContext.drawLiterator('literator_path');
		PageContext.loadExtendfields();
		var arQueryFields = [
			{name: 'LogicFieldDesc', desc: '显示名称', type: 'string', length: 25},
			{name: 'DBFieldName', desc: '字段名称', type: 'string', length: 50}
		];
		SimpleQuery.register('query_box', arQueryFields, function(_params){
			Object.extend(PageContext.params, _params);
			PageContext.loadExtendfields();
		}, true);
});

