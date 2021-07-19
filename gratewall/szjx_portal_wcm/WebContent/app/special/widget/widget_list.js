/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	serviceId : 'wcm61_widget',
	methodName : 'jQuery',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		"pageSize" : window.screen.width <= 1024 ? "8" :"10"
	}
});


wcm.ListQuery.register({
	callback : function(sValue){
		PageContext.loadList({
			WName : sValue
		});
	}
});

Ext.apply(PageContext.PageNav,{
	UnitName : '个',
	TypeName : '资源'
});

function getHelper(){
	return new com.trs.web2frame.BasicDataHelper();
}

//register for toolbar
wcm.Toolbar.register({
	addWidget : function(){
		var oparams = {
			objectId : 0
		}
		wcm.CrashBoarder.get('add_widget').show({
			title : '新建资源',
			src : 'widget/widget_addedit.jsp',
			width:'850px',
			height:'450px',
			params : oparams,
			callback : function(params){
				PageContext.refreshList();
			}
		});		
	},
	importWigets : function(){
		wcm.CrashBoarder.get('import_widget').show({
			title : '导入资源',
			src : 'widget/widget_import.html',
			width:'400px',
			height:'170px',
			callback : function(params){
				this.close();
				//页面刷新
				PageContext.refreshList();
			}
		});		
	},
	'export' : function(){
		var aWidgetIds = wcm.ThumbList.getSelectedThumbItemIds();
		var nCount = aWidgetIds.length;
		if(nCount<=0){
			Ext.Msg.alert("没有选择要导出的对象.", function(){});
			return;
		}
		var sWidgetIds = aWidgetIds.join(",");
		var oPostData = {
			objectIds : sWidgetIds
		};
		getHelper().call('wcm61_widget', 'export', oPostData, true, function(_oXMLHttp, _json){
			var sFileUrl = _oXMLHttp.responseText;
			var frm = document.getElementById("ifrmDownload");
			if(frm == null) {
				frm = document.createElement('iframe');
				frm.style.height = 0;
				frm.style.width = 0;
				document.body.appendChild(frm);
			}
			sFileUrl = WCMConstants.WCM_ROOTPATH + "file/read_file.jsp?DownName=WIDGET&FileName=" + sFileUrl;
			frm.src = sFileUrl;
			
		});
	},
	'delete' : function(){
		var aWidgetIds = wcm.ThumbList.getSelectedThumbItemIds();
		var nCount = aWidgetIds.length;
		//需要先判断一下这些资源是否有被使用，被使用了需要给出提示
		if(nCount<=0){
			Ext.Msg.alert("没有选择要删除的对象.");
			return;
		}
		var sWidgetIds = aWidgetIds.join(",");
		var oPostData = {
			objectIds : sWidgetIds
		};
		Ext.Msg.confirm('确实要删除资源吗? ', {
			yes : function(){
				getHelper().call('wcm61_widget','delete', oPostData, true, 
					function(){
						//页面刷新
						PageContext.loadList(PageContext.params);
				});
			}
		});
	}
});

//register for thumb list cmds.
wcm.ThumbList.register({
	edit : function(properties){
		var oparams = {
			objectId : properties['id']
		};
		wcm.CrashBoarder.get('edit_widget').show({
			title : '修改资源',
			src : 'widget/widget_addedit.jsp',
			width:'850px',
			height:'450px',
			params : oparams,
			callback : function(params){
				//this.close();
				//页面刷新,参数？
				PageContext.refreshList();
			}
		});
	},
	'export' : function(properties){
		var oPostData = {
			objectIds : properties['id']
		};
		getHelper().call('wcm61_widget', 'export', oPostData, true, function(_oXMLHttp, _json){
			var sFileUrl = _oXMLHttp.responseText;
			var frm = document.getElementById("ifrmDownload");
			if(frm == null) {
				frm = document.createElement('iframe');
				frm.style.height = 0;
				frm.style.width = 0;
				document.body.appendChild(frm);
			}
			sFileUrl = WCMConstants.WCM_ROOTPATH + "file/read_file.jsp?DownName=WIDGET&FileName=" + sFileUrl;
			frm.src = sFileUrl;
			
		});
	},
	'delete' : function(properties){
		//需要先判断一下该资源是否有被使用，被使用了需要给出提示
		var oPostData = {
			objectIds : properties['id']
		};
		Ext.Msg.confirm('确实要删除资源吗? ', {
			yes : function(){
				getHelper().call('wcm61_widget','delete', oPostData, true, 
					function(){
						//页面刷新
						PageContext.loadList(PageContext.params);
				});
			}
		});
	}
});


function resizeIfNeed(_loadImg){
	if(!_loadImg) return;
	var maxHeight = 112;
	var maxWidth = 172;
	var loadImg = new Image();
	loadImg.src = _loadImg.src;
	var height = loadImg.height, width = loadImg.width;
	if(height > maxHeight || width > maxWidth){	
		if(height > width){
			width = maxHeight * width/height;
			height = maxHeight
		}else{
			height = maxWidth * height/width;
			width = maxWidth;
		}
		_loadImg.width = width;
		_loadImg.height = height;
	}
}