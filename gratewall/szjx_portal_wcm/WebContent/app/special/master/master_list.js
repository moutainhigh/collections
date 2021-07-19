/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	serviceId : 'wcm61_master',//'wcm61_widget',
	methodName : 'JQuery',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		"MasterType" : "0",
		"pageSize" : window.screen.width <= 1024 ? "8" :"10"
	}
});

//register for list query
wcm.ListQuery.register({
	callback : function(sValue){
		PageContext.loadList({
			MName : sValue
		});
	}
});

Ext.apply(PageContext.PageNav,{
	UnitName : '个',
	TypeName : '母板'
});

//register for toolbar
wcm.Toolbar.register({
	add : function(){
		addEditMaster(0);
	},
	'import' : function(){
		wcm.CrashBoarder.get('master_import').show({
			title : '导入母板',
			src : 'master/master_import.jsp',
			maskable : true,
			width : '400px',
			height: '170px',
			callback : function(params){
				if(params == 'true'){
					this.close();
					//刷新列表页面
					PageContext.loadList();
				}
			}
		});
	},
	'export' : function(){
		exportMasters(wcm.ThumbList.getSelectedThumbItemIds().join(","));
	},
	'delete' : function(){
		deleteMasters(wcm.ThumbList.getSelectedThumbItemIds().join(","));		
	}
});

//register for thumb list cmds.
wcm.ThumbList.register({
	edit : function(properties){
		addEditMaster(properties['id']);
	},
	'export' : function(properties){
		exportMasters(properties['id']);
	},
	'delete' : function(properties){
		deleteMasters(properties['id']);
	}
});

function addEditMaster(_nMasterId){
	var nMasterId = _nMasterId;
	wcm.CrashBoarder.get('master_addedit').show({
		title : '新建/修改母板',
		src : 'master/master_addedit.jsp?ObjectId='+nMasterId,
		maskable : true,
		width:'580px',
		height:'370px',
		callback : function(params){
			if(params == 'true'){
				PageContext.loadList();
				this.close();
			}
		}
	});	
}

function exportMasters(_sMasterIds){
	var sMasterIds = _sMasterIds;
	if(sMasterIds == null || sMasterIds == ''){
		Ext.Msg.alert("请选择要导出的母板！");
		return;
	}

	BasicDataHelper.Call('wcm61_master','export',{ObjectIds : sMasterIds},true, function(_trans, _json){
		downloadFile(_trans.responseText);
	});
}


function downloadFile(_sFileUrl){
	var sFileUrl = _sFileUrl;
	var frm = document.getElementById('iframe4download');
	if(frm==null){
		frm = document.createElement('IFRAME');
		frm.id = "iframe4download";
		frm.style.display = 'none';
		document.body.appendChild(frm);
	}
	sFileUrl = WCMConstants.WCM_ROOTPATH +"file/read_file.jsp?DownName=MASTER&FileName=" + sFileUrl;
	frm.src = sFileUrl;
}


function deleteMasters(_sMasterIds){
	var sInfo;
	if(_sMasterIds.indexOf(",") == -1){
		sInfo = "确定要删除这个母板？";
	}else{
		sInfo = "确定要删除这些母板？";
	}
	var sMasterIds = _sMasterIds;
	if(sMasterIds == null || sMasterIds == ''){
		Ext.Msg.alert("请选择要删除的母板");
		return;
	}
	Ext.Msg.confirm(sInfo,function(){
		BasicDataHelper.Call('wcm61_master','delete',{ObjectIds : sMasterIds},true,function(_trans, _json){
			PageContext.loadList();
		});
	});
}
//弹出图片和缩略图以外的元素上发生点击事件时，触发弹出图片的click事件，让其隐藏
Event.observe(document, 'click', function(event){
	event = window.event || event;
	var dom = Event.element(event);
	if(!(dom.tagName =='IMG') && $('loader')){
		jQuery('#loader img').click();
	}
});
