/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	serviceId : 'wcm61_special',
	methodName : 'jQuery',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : ""
	}
});

//register for list query
wcm.ListQuery.register({
	callback : function(sValue){
		PageContext.loadList({
			SpecialName : sValue
		});
	}
});

Ext.apply(PageContext.PageNav,{
	UnitName : '个',
	TypeName : '专题'
});

//register for toolbar
wcm.Toolbar.register({
	create : function(){
		wcm.CrashBoarder.get('special_new').show({
			title : '新建专题',
			src : 'introduction.jsp',//'special_1.jsp?ObjectId=0',
			width:'729px',
			height:'437px',
			maskable : true,
			callback : function(nSpecialId){
				this.close();
				//刷新列表页面
				PageContext.loadList(PageContext.params);
				if(nSpecialId != 0){
					setTimeout(function(){
						window.open('design.jsp?specialid='+nSpecialId);
					}, 10);
				}
			}
		});		
	},
	likecopy : function(){
		wcm.CrashBoarder.get('special_new').show({
			title : '类似创建专题',
			src : 'special_select_list.html',
			width:'630px',
			height:'450px',
			maskable : true,
			callback : function(){
				this.close();
				//刷新列表页面
				PageContext.loadList(PageContext.params);
			}
		});	
	},
	mgrRecycle : function(){
		wcm.CrashBoarder.get('recycle_special').show({
			title : '专题回收站',
			src : 'special_recycle_list.html',
			width:'630px',
			height:'450px',
			maskable : true,
			callback : function(){
				//刷新列表页面
				PageContext.loadList(PageContext.params);
			}
		});	
	}
});

//register for thumb list cmds.
wcm.ThumbList.register({
	preview : function(properties){
		var postData = {
			'ObjectIds' : properties['HOSTID'], 
			'ObjectType' : 101
		};
		BasicDataHelper.call("wcm6_publish",'preview',postData,true,function(transport, json){
			var urlCount = com.trs.util.JSON.value(json, "URLCOUNT");
			if(urlCount == 0){
				 Ext.Msg.fault({
					message : (json.DATA.length>0)?json.DATA[0].EXCEPTION:"",
					detail : (json.DATA.length>0)?json.DATA[0].EXCEPTIONDETAIL:""
				});
			}else{
				var urls = com.trs.util.JSON.value(json, "DATA.0.URLS");
				if(urls.length == 1){
					window.open(urls);
					return;
				}
			}
		});
	},
	design : function(properties){
		window.open('design.jsp?specialid='+properties['id']);
	},
	'delete' : function(properties){
		 Ext.Msg.confirm('<span style="line-height:150%">确定要将此专题放入回收站吗？<br/><font color="red"">(此操作会导致已发布的页面撤销！)</font></span>', {
			yes : function(){
				var postData = {
					'ObjectIds' : properties['id'],
					'Drop' : false
				};
				BasicDataHelper.call("wcm61_special",'delete',postData,true,function(transport, json){
					PageContext.loadList(PageContext.params);
				});
			},
			no : function(){
			}
		});
	},
	moreCmds : function(properties, event){
		if(!window.simpleMenu){
			simpleMenu = new com.trs.menu.SimpleMenu();
		}
		simpleMenu.show([
			{
				oprKey : 'publish',
				desc : "发布",
				cls:function(){					
					if(properties['CANPUB'] != "true"){
						return "disableCls";
					}
				},
				cmd : function(){
					if(properties['CANPUB'] != "true"){
						return;
					}
					var postData = {
						'ObjectIds' : properties['HOSTID'], 
						'ObjectType' : 101
					};
					BasicDataHelper.call("wcm6_publish", "fullyPublish", postData, true, function(_transport,_json){
						if(_json!=null&&_json["REPORTS"]){
							var oReports = _json["REPORTS"];
							var stJson = com.trs.util.JSON;
							var bIsSuccess = stJson.value(oReports, "IS_SUCCESS");
							var title = stJson.value(oReports, "TITLE");
							if(bIsSuccess=='false'){
								Ext.Msg.report(_json,wcm.LANG.PUBLISH_1||'发布校验结果');
								return;
							}
						}
						Ext.Msg.$timeAlert('已经将您的发布操作提交到后台了...', 3);
					});
				}
			},
			{
				oprKey : 'edit',
				desc : "修改",
				cls:function(){					
					if(properties['CANEDIT'] != "true"){
						return "disableCls";
					}
				},
				cmd : function(){
					if(properties['CANEDIT'] != "true"){
						return;
					}
					edit(properties['id']);
				}
			},
			{
				oprKey : 'setStyle',
				desc : "设置风格",
				cls:function(){					
					if(properties['CANEDIT'] != "true"){
						return "disableCls";
					}
				},
				cmd : function(){
					if(properties['CANEDIT'] != "true"){
						return;
					}
					var oHelper = new com.trs.web2frame.BasicDataHelper();
					var oPostData = {objectId :properties['OBJECTID']};
					oHelper.JspRequest(
						"getStyle.jsp",
						oPostData,  true,
						function(transport, json){
							if(transport.responseText.trim() != ""){
								var nStyleId = transport.responseText.trim();
								wcm.CrashBoarder.get('pagesytle_select').show({
									title : '页面风格列表',
									src : 'style/pagestyle_select_list.html?selectedId=' + nStyleId,
									width:'630px',
									height:'450px',
									callback : function(_json){
										var oPostData = {
											ObjectId: properties['id'],
											StyleId:_json.StyleId,
											StyleName:_json.StyleName
										};
										BasicDataHelper.call("wcm61_special",'setStyle',oPostData,true,function(_trans,_json){
											wcm.CrashBoarder.get('pagesytle_select').close();
											//刷新列表页面
											PageContext.loadList(PageContext.params);
										});	
									}
								});	
							}
					});
				}
			},
			{
				oprKey : 'mgrTemplate',
				desc : "管理模板",
				cls:function(){					
					if(properties['CANEDIT'] != "true" || properties['HASTEMPLATERIGHT'] != "true"){
						return "disableCls";
					}
				},
				cmd : function(){
					if(properties['CANEDIT'] != "true" || properties['HASTEMPLATERIGHT'] != "true"){
						return;
					}

					var nChannelId = properties['HOSTID'];

					wcm.CrashBoarder.get('template_list_4special').show({
						title : '模板列表',
						src : 'template_list.html?channelId=' + nChannelId,
						width:'680px',
						height:'450px',
						maskable : true,
						callback : function(){
							this.close();
						}
					});
				}
			}
		], {
			x : Event.pointerX(event),
			y : Event.pointerY(event)
		});
	}
});

function edit(nSpecialId){
	wcm.CrashBoarder.get('special_edit').show({
		title : '修改专题',
		src : 'special_1.jsp?ObjectId=' + nSpecialId + "&isNew=0",
		width:'729px',
		height:'437px',
		maskable : true,
		callback : function(params){
			this.close();
			//刷新列表页面
			PageContext.loadList(PageContext.params);
		}
	});		
}
