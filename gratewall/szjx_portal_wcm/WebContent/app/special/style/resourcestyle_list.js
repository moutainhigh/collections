/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	serviceId : 'wcm61_resourcestyle',
	methodName : 'jQuery',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		"OrderBy"	   : "CrTime desc",
		"PageStyleId"  : getParameter("PageStyleId") || 0,
		"pageSize"     : 6
	}
});

//register for list query
wcm.ListQuery.register({
	callback : function(sValue){
		PageContext.loadList({
			StyleName : sValue
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
		var nPageStyleId = getParameter("PageStyleId") || 0;
		var srcUrl = WCMConstants.WCM6_PATH + 'special/style/resource_style_addedit.jsp?pageStyleId=' + nPageStyleId + '&ResourceStyleId=0';
		wcm.CrashBoarder.get('resourcestyle_addedit').show({
			title : '新建资源风格',
			src : srcUrl,
			width:'900px',
			height:'400px',
			callback : function(params){
				this.close();
				PageContext.loadList(PageContext.params);
				Ext.Msg.alert("如果需要在其他页面风格下创建类似的资源风格，请执行此资源风格更多操作中的【复制给】");
			}
		});		
	},
	'import' : function(){
		var nPageStyleId = getParameter("PageStyleId") || 0;
		wcm.CrashBoarder.get('pagestyle_import').show({
			title : '导入风格',
			src : WCMConstants.WCM6_PATH + 'special/style/style_import.jsp?StyleType=2&PageStyleId=' + nPageStyleId,
			width:'350px',
			height:'160px',
			callback : function(params){
				this.close();
				PageContext.loadList(PageContext.params);
			}
		});		
	},
	'export' : function(){
		var nPageStyleId = getParameter("PageStyleId") || 0;
		var styleIds = wcm.ThumbList.getSelectedThumbItemIds().join(",");
		if(styleIds == null || styleIds.length == 0){
			Ext.Msg.alert("请选择需要导出的风格!");
			return;
		}
		BasicDataHelper.call('wcm61_resourcestyle', 'exportResourceStyleZip', {objectIds:styleIds,PageStyleId:nPageStyleId}, true, function(_oXMLHttp, _json){
			var sFileUrl = _oXMLHttp.responseText;
			var frm = document.getElementById("ifrmDownload");
			if(frm == null) {
				frm = document.createElement('iframe');
				frm.style.height = 0;
				frm.style.width = 0;
				document.body.appendChild(frm);
			}
			sFileUrl = WCMConstants.WCM_ROOTPATH + "file/read_file.jsp?DownName=RESOURCESTYLE&FileName=" + sFileUrl;
			frm.src = sFileUrl;
		});
	},
	'delete' : function(){
		var selectedIds = wcm.ThumbList.getSelectedThumbItemIds().join(",");
		if(selectedIds == null || selectedIds.length == 0){
			Ext.Msg.alert("请选择要删除的风格");
			return;
		}
		 Ext.Msg.confirm('确定要删除选中的风格吗？', {
			yes : function(){
				if(top.ProcessBar){
					top.ProcessBar.start("删除资源风格！");
				}
				BasicDataHelper.call("wcm61_resourcestyle", 'delete', {ObjectIds:selectedIds}, true, function(transport, json){
					if(top.ProcessBar){
						top.ProcessBar.close();
					}
					PageContext.loadList(PageContext.params);
				});
			}
		});
	}
});

//register for thumb list cmds.
wcm.ThumbList.register({
	edit : function(properties){
		var nPageStyleId = getParameter("PageStyleId") || 0;
		var srcUrl = WCMConstants.WCM6_PATH + 'special/style/resource_style_addedit.jsp?pageStyleId='+ nPageStyleId + '&ResourceStyleId=' + properties['id'];
		wcm.CrashBoarder.get('resourcestyle_addedit').show({
			title : '修改资源风格',
			src : srcUrl,
			width:'900px',
			height:'400px',
			callback : function(params){
				this.close();
				PageContext.loadList(PageContext.params);
			}
		});	
	},
	'export' : function(properties){
		var styleIds = properties['id'];
		if(styleIds == null || styleIds.length == 0){
			Ext.Msg.alert("请选择需要导出的风格!");
			return;
		}
		BasicDataHelper.call('wcm61_resourcestyle', 'exportResourceStyleZip', {objectIds:styleIds}, true, function(_oXMLHttp, _json){
			var sFileUrl = _oXMLHttp.responseText;
			var frm = document.getElementById("ifrmDownload");
			if(frm == null) {
				frm = document.createElement('iframe');
				frm.style.height = 0;
				frm.style.width = 0;
				document.body.appendChild(frm);
			}
			sFileUrl = WCMConstants.WCM_ROOTPATH + "file/read_file.jsp?DownName=RESOURCESTYLE&FileName=" + sFileUrl;
			frm.src = sFileUrl;
		});
	},
	'preview' : function(properties){
		wcm.CrashBoarder.get('resource-style-preview').show({
			title : '预览资源风格',
			src : WCMConstants.WCM6_PATH + 'special/style/stylepreview/resource_preview_page_for_list.jsp?ResourceStyleId='+ properties['id'],
			width:'500px',
			height:'300px'
		});
	},
	'moreCmds' : function(properties, event){
		if(!window.simpleMenu){
			simpleMenu = new com.trs.menu.SimpleMenu();
		}
		var oOpers = [{
				oprKey : 'delete',
				desc : "删除",
				cls : function(){
					var bCanDel = properties["BCANDEL"];
					return bCanDel == "false" ? "disableCls" : "";
				},
				cmd : function(){
					if(properties["BCANDEL"] == 'false')
						return;
					 Ext.Msg.confirm('确定要删除选中的风格吗？', {
						yes : function(){
							if(top.ProcessBar){
								top.ProcessBar.start("删除资源风格！");
							}
							BasicDataHelper.call("wcm61_resourcestyle", 'delete', {ObjectIds:properties['id']}, true, function(transport, json){
								if(top.ProcessBar){
									top.ProcessBar.close();
								}
								PageContext.loadList(PageContext.params);
							});
						}
					});
				}
			},
			{
				oprKey : 'copyto',
				desc : "复制给",
				cls : function(){
					var bCanAdd = properties["BCANADD"];
					return bCanAdd == "false" ? "disableCls" : "";
				},
				cmd : function(){
					 if(properties["BCANADD"] == 'false')
						 return;

					var sResourceStyleDesc = properties['RESOURCESTYLENAME'];
					var sTitle = "复制资源风格";
					if(sResourceStyleDesc!=""){
						sTitle += "[" + sResourceStyleDesc + "]";
					}
					var cssFlag = properties['CSSFLAG'] || "";
					var pageStyleId = properties['PAGESTYLEID'] || 0;
					wcm.CrashBoarder.get('resource-style-copyto').show({
						title : sTitle,
						src : WCMConstants.WCM6_PATH + 'special/style/copy_resource_style_to.jsp?SourceResourceStyleId='+ properties['id'] +'&PageStyleId=' + pageStyleId,
						width:'500px',
						height:'400px',
						callback : function(_copyInfo){
							if(top.ProcessBar){
								top.ProcessBar.start("复制资源风格！");
							}
							var postData = {
								SourceResourceStyleId : _copyInfo.SourceResourceStyleId || 0,
								TargetPageStyleIds : _copyInfo.TargetPageStyleIds,
								CopyMode : _copyInfo.copyMode
							}
							BasicDataHelper.call("wcm61_resourcestyle", "copy", postData, 'true', function(_transport,_json){
								if(top.ProcessBar){
									top.ProcessBar.close();
								}
								Ext.Msg.report(_json, '资源风格复制结果', function(){
									wcm.CrashBoarder.get('resource-style-copyto').close();
								});
								wcm.CrashBoarder.get('resource-style-copyto').hide();
							});
							return false;
						}
					});
				}
			},{
				oprKey : 'editthumb',
				desc : "修改缩略图",
				cls : function(){
					var bCanDel = properties["BCANEDIT"];
					return bCanDel == "false" ? "disableCls" : "";
				},
				cmd : function(){
					if(properties["BCANEDIT"] == 'false')
						return;
					uploadStyleThumb(properties['id']);
					 
				}
			
			}];
		var nCurrPageStyleId = properties['PAGESTYLEID'] || 0;
		if(nCurrPageStyleId != 0){
			oOpers.push({
				oprKey : 'restore',
				desc : "恢复默认",
				title: "恢复为系统级别",
				cls : function(){
					var bCanReset = properties["BCANRESET"]; 
					var bCanEdit = properties["BCANEDIT"];
					return (bCanReset == "false" || bCanEdit == "false") ? "disableCls" : "";
				},
				cmd : function(){
					if(properties["BCANRESET"] == 'false' || properties["BCANEDIT"] == 'false')
						return;
					 BasicDataHelper.call("wcm61_resourcestyle", "resetResourceStyle", {ResourceStyleId : properties['id']}, 'true', function(){
						PageContext.loadList(PageContext.params);
					});
				}
			});
		}
		simpleMenu.show(oOpers, {
			x : Event.pointerX(event),
			y : Event.pointerY(event)
		});
	}
});
function uploadStyleThumb(_nResourceStyleId){
	var sURL = WCMConstants.WCM6_PATH + "special/style/thumb_set_for_style.jsp?ResourceStyleId="+_nResourceStyleId;
	wcm.CrashBoarder.get('edit_style_thumb').show({
		title : "修改风格缩略图",
		src : sURL,
		width:'400px',
		height:'260px',
		callback : function (_sStyleThumb){
			var postData = {
				ObjectId : _nResourceStyleId,
				StyleThumb : _sStyleThumb
			}
			BasicDataHelper.call("wcm61_resourcestyle", "saveStyleThumb", postData, "true", function(){
				this.close();
				alert("缩略图修改成功！");
				PageContext.loadList(PageContext.params);
			});
		}
	});
}
PageContext.addListener('afterrender', function(transport, json){
	 var nPageStyleId = getParameter("PageStyleId") || 0;
	 if(nPageStyleId == 0){
		 Element.show('importel');
		 Element.show('exportel');
	 }
});

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