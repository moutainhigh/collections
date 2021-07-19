/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	serviceId : 'wcm61_pagestyle',
	methodName : 'jQuery',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		"OrderBy"	   : "CrTime desc",
		"pagesize"     : 6
		
	}
});

//register for list query
wcm.ListQuery.register({
	callback : function(sValue){
		PageContext.loadList({
			StyleDesc : sValue
		});
	}
});

Ext.apply(PageContext.PageNav,{
	UnitName : '个',
	TypeName : '风格'
});

//register for toolbar
wcm.Toolbar.register({
	addStyle : function(){
		var param = {
			pageStyleId : 0
		}
		wcm.CrashBoarder.get('pagestyle_add').show({
			title : '新建页面风格',
			src : './style/pagestyle_addedit.jsp?pageStyleId=0',
			width:'420px',
			height:'150px',
			callback : function(params){
				this.close();
				PageContext.loadList(PageContext.params);
			}
		});		
	},
	'import' : function(){
		wcm.CrashBoarder.get('pagestyle_import').show({
			title : '导入风格',
			src : './style/pagestyle_import.jsp',
			width:'350px',
			height:'200px',
			callback : function(params){
				this.close();
				PageContext.loadList(PageContext.params);
			}
		});		
	},
	'export' : function(){
		var sPageStyleIds = wcm.ThumbList.getSelectedThumbItemIds().join(",");
		if(sPageStyleIds == null || sPageStyleIds.length == 0){
			Ext.Msg.alert("请选择需要导出的风格!");
			return;
		}
		if(top.ProcessBar)
			top.ProcessBar.start("导出页面风格！");
		BasicDataHelper.call('wcm61_pagestyle', 'exportPageStyleZip', {objectIds:sPageStyleIds}, true, function(_oXMLHttp, _json){
			var sFileUrl = _oXMLHttp.responseText;
			var frm = document.getElementById("ifrmDownload");
			if(frm == null) {
				frm = document.createElement('iframe');
				frm.style.height = 0;
				frm.style.width = 0;
				document.body.appendChild(frm);
			}
			if(top.ProcessBar)
				top.ProcessBar.close();
			sFileUrl = WCMConstants.WCM_ROOTPATH + "file/read_file.jsp?DownName=PAGESTYLE&FileName=" + sFileUrl;
			frm.src = sFileUrl;
		});
	},
	'delete' : function(){
		var selectedIds = wcm.ThumbList.getSelectedThumbItemIds().join(",");
		if(selectedIds == null || selectedIds.length == 0){
			Ext.Msg.alert("请选择要删除的风格!");
			return;
		}
		 Ext.Msg.confirm('确定要删除选中的风格吗？', {
			yes : function(){
				if(top.ProcessBar){
					top.ProcessBar.start("删除页面风格！");
				}
				BasicDataHelper.call("wcm61_pagestyle", 'delete', {ObjectIds:selectedIds}, true, function(transport, json){
					if(top.ProcessBar){
						top.ProcessBar.close();
					}
					PageContext.loadList(PageContext.params);
				});
			}
		});
	},
	'moreCmds' : function(list, event){
		var event = window.event || event;
		if(!window.simpleMenu){
			simpleMenu = new com.trs.menu.SimpleMenu();
		}
		var oOpers = [{
			oprKey : 'ImportResourceStyle',
			desc : "导入资源风格",
			cls : function(){
				return bCanAdd == 'true' ? 'ImportResourceStyle' : 'ImportResourceStyle disableCls';
			},
			cmd : function(){
				if(bCanAdd != 'true')return;
				 wcm.CrashBoarder.get('resourcestyle_import').show({
					title : '导入资源风格',
					src : './style/style_import.jsp?StyleType=2&PageStyleId=0',
					width:'350px',
					height:'200px',
					callback : function(params){
						this.close();
						//PageContext.loadList(PageContext.params);
					}
				});		
			}
		},
		{
			oprKey : 'ExportResourceStyle',
			desc : "导出资源风格",
			cls : function(){
				return 'ExportResourceStyle';
			},
			cmd : function(){
				wcm.CrashBoarder.get('ExportResourceStyle').show({
					title : '选择资源风格',
					src : './style/resource_style_select.jsp?pageStyleId=0&StyleType=2',//2标识是资源风格
					width:'700px',
					height:'370px',
					callback : function(params){
						var params = {
							ObjectIds : params.selectedStyleIds,
							PageStyleId : 0
						}
						this.close();
						if(top.ProcessBar)
							top.ProcessBar.start("导出资源风格！");
						 //发送请求，开始导出
						BasicDataHelper.call('wcm61_resourcestyle', 'exportResourceStyleZip', params, true, function(_oXMLHttp, _json){
							var sFileUrl = _oXMLHttp.responseText;
							var frm = document.getElementById("ifrmDownload");
							if(frm == null) {
								frm = document.createElement('iframe');
								frm.style.height = 0;
								frm.style.width = 0;
								document.body.appendChild(frm);
							}
							if(top.ProcessBar)
								top.ProcessBar.close();
							sFileUrl = WCMConstants.WCM_ROOTPATH + "file/read_file.jsp?DownName=RESOURCESTYLE&FileName=" + sFileUrl;
							frm.src = sFileUrl;
						});

					}
				});
			}
		},
		{
			oprKey : 'ImportContentStyle',
			desc : "导入内容风格",
			cls : function(){
				return bCanAdd == 'true' ? 'ImportResourceStyle' : 'ImportContentStyle disableCls';
			},
			cmd : function(){
				if(bCanAdd != 'true')return;
				 wcm.CrashBoarder.get('contentstyle_import').show({
					title : '导入内容风格',
					src : './style/style_import.jsp?StyleType=3&PageStyleId=0',
					width:'350px',
					height:'200px',
					callback : function(params){
						this.close();
					}
				});		
			}
		},
		{
			oprKey : 'ExportContentStyle',
			desc : "导出内容风格",
			cls : function(){
				return 'ExportContentStyle';
			},
			cmd : function(){
				wcm.CrashBoarder.get('ExportContentStyle').show({
					title : '选择内容风格',
					src : './style/content_style_select.jsp?pageStyleId=0',
					width:'700px',
					height:'370px',
					callback : function(params){
						var params = {
							ObjectIds : params.selectedStyleIds,
							PageStyleId : 0
						}
						this.close();
						if(top.ProcessBar)
							top.ProcessBar.start("导出内容风格！");
						 //发送请求，开始导出
						BasicDataHelper.call('wcm61_contentstyle', 'exportContentStyleZip', params, true, function(_oXMLHttp, _json){
							var sFileUrl = _oXMLHttp.responseText;
							var frm = document.getElementById("ifrmDownload");
							if(frm == null) {
								frm = document.createElement('iframe');
								frm.style.height = 0;
								frm.style.width = 0;
								document.body.appendChild(frm);
							}
							if(top.ProcessBar)
								top.ProcessBar.close();
							sFileUrl = WCMConstants.WCM_ROOTPATH + "file/read_file.jsp?DownName=CONTENTSTYLE&FileName=" + sFileUrl;
							frm.src = sFileUrl;
						});

					}
				});
			}
		}]
		simpleMenu.show(oOpers, {
			x : Event.pointerX(event),
			y : Event.pointerY(event)
		});
		
	}
});

//register for thumb list cmds.
wcm.ThumbList.register({
	edit : function(properties){
		var nPageStyleId = properties['id'];
		if(nPageStyleId>0){
			var sStyle_edit_online = "./pagestyle_edit_index.html?PageStyleId=" + nPageStyleId+"&userName="+parent.parent.getParameter("userName");
			window.open(sStyle_edit_online);
			return;
		}
		
	},
	'export' : function(properties){
		if(top.ProcessBar)
			top.ProcessBar.start("导出页面风格！");
		BasicDataHelper.call('wcm61_pagestyle', 'exportPageStyleZip', {objectIds:properties['id']}, true, function(_oXMLHttp, _json){
			var sFileUrl = _oXMLHttp.responseText;
			var frm = document.getElementById("ifrmDownload");
			if(frm == null) {
				frm = document.createElement('iframe');
				frm.style.height = 0;
				frm.style.width = 0;
				document.body.appendChild(frm);
			}
			if(top.ProcessBar)
				top.ProcessBar.close();
			sFileUrl = WCMConstants.WCM_ROOTPATH + "file/read_file.jsp?DownName=PAGESTYLE&FileName=" + sFileUrl;
			frm.src = sFileUrl;
		});
	},
	'delete' : function(properties){
		Ext.Msg.confirm('确定要删除当前风格吗？', {
			yes : function(){
				if(top.ProcessBar){
					top.ProcessBar.start("删除页面风格！");
				}
				BasicDataHelper.call("wcm61_pagestyle", 'delete', {ObjectIds:properties['id']}, true, function(transport, json){
					if(top.ProcessBar){
						top.ProcessBar.close();
					}
					PageContext.loadList(PageContext.params);
				});
			}
		});
	},
	'moreCmds' : function(properties, event){
		if(!window.simpleMenu){
			simpleMenu = new com.trs.menu.SimpleMenu();
		}
		var oOpers = [{
				oprKey : 'likecopy',
				desc : "类似创建",
				cls : function(){
					var bCanDel = properties["BCANADD"];
					return bCanDel == "false" ? "disableCls" : "thumbItem";
				},
				cmd : function(){
					if(properties["BCANADD"] == 'false')
						return;
					 wcm.CrashBoarder.get('pagestyle_likecopy').show({
						title : String.format("从风格【{0}】类似创建",properties['STYLEDESC']),
						src : './style/copy_page_style.jsp?SourcePageStyleId=' + properties['id'],
						width:'420px',
						height:'150px',
						callback : doCopyAndCreate
					});
				}
			},{
				oprKey : 'editthumb',
				desc : "修改缩略图",
				cls : function(){
					var bCanDel = properties["BCANEDIT"];
					return bCanDel == "false" ? "disableCls" : "thumbItem";
				},
				cmd : function(){
					if(properties["BCANEDIT"] == 'false')
						return;
					uploadStyleThumb(properties['id']);
					 
				}
			
			}];
		simpleMenu.show(oOpers, {
			x : Event.pointerX(event),
			y : Event.pointerY(event)
		});
	}
});
function uploadStyleThumb(_nPageStyleId){
	var sURL = WCMConstants.WCM6_PATH + "special/style/thumb_set_for_style.jsp?PageStyleId="+_nPageStyleId;
	wcm.CrashBoarder.get('edit_pagestyle_thumb').show({
		title : "修改风格缩略图",
		src : sURL,
		width:'360px',
		height:'250px',
		callback : function (_sStyleThumb){
			var postData = {
				ObjectId : _nPageStyleId,
				StyleThumb : _sStyleThumb
			}
			BasicDataHelper.call("wcm61_pagestyle", "saveStyleThumb", postData, "true", function(){
				this.close();
				alert("缩略图修改成功！");
				PageContext.loadList(PageContext.params);
			});
		}
	});
}
function doCopyAndCreate(pageStyle){
	var postData = {
		SourcePageStyleId : pageStyle.Id,
		StyleName : pageStyle.Name,
		StyleDesc : pageStyle.Desc
	}
	BasicDataHelper.call("wcm61_pagestyle", "copy", postData, "true", function(){
		wcm.CrashBoarder.get('pagestyle_likecopy').close();
		PageContext.loadList(PageContext.params);
	});
}
var bCanAdd = 'false';
function setAddRight(bCanAddRight){
	bCanAdd = bCanAddRight;
}


Event.observe(window, 'load', function(){
	Event.observe('orderby-box', 'click', function(event){
		var dom = Event.element(window.event || event);
		if(!dom.name){
			return;
		}

		PageContext.loadList({
			"OrderBy" : dom.value
		});
	});
});