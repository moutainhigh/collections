//站点操作信息和Mgr定义
Ext.ns('wcm.domain.WebSiteMgr');
(function(){
	var m_nWebSiteObjType = 103;
	var m_sServiceId = 'wcm61_website';
	var m_oMgr = wcm.domain.WebSiteMgr;
	function getHelper(){
		return new com.trs.web2frame.BasicDataHelper();
	}

	function edit(event, oParams){
		FloatPanel.open({
			src : WCMConstants.WCM6_PATH + 'website/website_add_edit.jsp?' + $toQueryStr(oParams),
			title : wcm.LANG.WEBSITE_1||'新建/修改站点',
			callback : function(objId){
				CMSObj[oParams.objectid>0 ? 'afteredit' : 'afteradd'](event)({objType:WCMConstants.OBJ_TYPE_WEBSITE, objId:objId});
			}
		});
	}

	//add and edit operators.
	Ext.apply(wcm.domain.WebSiteMgr, {
		"new" : function(event){
			edit(event, {
				objectid : 0,
				sitetype : event.getContext().get("siteType") || 0
			});
		},
		edit : function(event){
			edit(event, {
				objectid : event.getObj().getId(),
				sitetype : event.getContext().get("siteType") || 0
			});
		},
		quicknew : function(event){
			FloatPanel.open(WCMConstants.WCM6_PATH + 'website/website_create.jsp', wcm.LANG.WEBSITE_2||"智能建站", function(objId){
				CMSObj.afteradd(event)({objType:WCMConstants.OBJ_TYPE_WEBSITE, objId:objId});
			});
		},
		likecopy : function(event){
			var id = event.getObj().getId();
			FloatPanel.open(WCMConstants.WCM6_PATH + 'website/website_likecopy.html?siteid=' + id, wcm.LANG.WEBSITE_3||"类似创建站点",function(objId){
				CMSObj.afteradd(event)({objType:WCMConstants.OBJ_TYPE_WEBSITE, objId:objId});
			});
		},
		setAppendixSize : function(event){
			var id = event.getObj().getId();
			wcm.CrashBoarder.get('set_appendix_size').show({
				title : wcm.LANG.website_2011 || '设置这个站点上传文档附件大小',
				src : WCMConstants.WCM6_PATH + 'website/website_set_doc_appendix_size.jsp',
				width:'550px',
				height:'200px',
				maskable:true,
				params :  {siteId : id}
			}); 
		}
	});

	//import and export operators
	function download(_sUrl){
		var frm = $('iframe4download');
		if(!frm) {
			frm = document.createElement('IFRAME');
			frm.id = "iframe4download";
			frm.style.display = 'none';
			document.body.appendChild(frm);
		}
		_sUrl = WCMConstants.WCM_ROOTPATH + "file/read_file.jsp?DownName=SITE&FileName=" + _sUrl;
		frm.src = _sUrl;
	}
	Ext.apply(wcm.domain.WebSiteMgr, {
		'import' : function(event){
			FloatPanel.open(WCMConstants.WCM6_PATH + 'website/website_import.html', wcm.LANG.WEBSITE_4||'站点导入', function(objId){
				CMSObj.afteradd(event)({objType:WCMConstants.OBJ_TYPE_WEBSITE, objId:objId});
			});
		},

		'export' : function(event){
			ProcessBar.start( wcm.LANG.WEBSITE_42||'执行站点导出..');
			var aIds = event.getObjsOrHost().getIds();
			getHelper().call(
					m_sServiceId,
					'export',
					{objectids: aIds.join(",")},
					true,
					function(_oXMLHttp, _json){
						ProcessBar.close();
						download(_oXMLHttp.responseText);
					}
			);
		},

		//wenyh@2009-06-23 添加从文本批量创建
		createFromFile : function(event){
			var nSiteType = event.getHost().getId();
			FloatPanel.open(WCMConstants.WCM6_PATH + 'website/website_create_fromfile.html?SiteType=' + nSiteType, wcm.LANG.WEBSITE_CREATE_FROMFILE||'批量创建', function(){
				CMSObj.afteradd(event)({objType:WCMConstants.OBJ_TYPE_WEBSITE,objId:0});
			});
		}
	});

	//delete operators.
	Ext.apply(wcm.domain.WebSiteMgr, {
		"delete" : function(event){
			this.recycle(event);
		},
		trash : function(event){
			this.recycle(event);
		},
		recycle : function(event){
			var aIds = event.getObjsOrHost().getIds();
			var nCount = aIds.length == 1 ? "" : aIds.length;
			Ext.Msg.confirm(
				String.format('确实要将这{0}个站点放入站点回收站吗?<br /><br /><span style="color:red;">这是危险操作！</span>',nCount),
				{ ok : function(){
								verifyPassword(function(){
										ProcessBar.start(wcm.LANG.WEBSITE_RECYCLE_PROCESSBAR||'将站点放入回收站');
										getHelper().call(
												m_sServiceId,
												'delete',
												{objectids:aIds.join(","), "drop":false},
												true,
												function(){
													ProcessBar.close();
													event.getObjsOrHost().afterdelete();
												}
										);
									} ,
									"删除站点-"
								);
				        }
				}
			);
			return false;
		}
	});

	function resetTemplates(_nFolderId, _nObjType, _fCallBack){
		getHelper().call('wcm6_template', 'impartTemplateConfig',
			{objectId: _nFolderId, ObjectType:_nObjType}, true, _fCallBack);
	}
	function synchTemplates(_nFolderId, _nObjType, childObjIds, _fCallBack){
		getHelper().call('wcm61_template', 'synTemplates',
			{objectId: _nFolderId, ObjectType:_nObjType, objectIds : childObjIds}, true, _fCallBack);
	}
	//密码校验
	/** 
     *  参数说明：
	 *          validCallback     匿名函数，密码验证通过后调用的函数
	 *          operateName    字符串，操作的名称，用来显示在弹出窗口的标题上，形如：删除站点
	**/
    function verifyPassword(validCallback ,operateName){
		wcm.CrashBoarder.get('validate-password').show({
			title : '<span style="color:red;" >'+operateName+'校验密码</span>',
			src : WCMConstants.WCM6_PATH+'include/validate_password.html',
			width :  '400px',
			height : '150px',
			callback : function(params){
				if(params=="true"){
					this.close();
					if(validCallback){
						validCallback();
					}
				}
			}  
		});								
	}
	Ext.apply(wcm.domain.WebSiteMgr, {
		synTemplates : function(event){
			var objId = event.getObj().getId();
			var params = {
				objType:m_nWebSiteObjType,
				objId:objId,
				close : 0,
				ExcludeTop : 1,
				ExcludeInfoview : 0,
				ExcludeLink : 1
			};
			FloatPanel.open({
				src : WCMConstants.WCM6_PATH + 'website/object_select.html',
				dialogArguments : params,
				title : wcm.LANG.WEBSITE_41||'选择栏目',
				callback : function(args){
					if(args.allChildren){
						var sHtml = wcm.LANG.WEBSITE_30||"确实要同步模板到子栏目吗?<br><span style=\'color:red;font:16px;\'>注意:<br>该操作会覆盖更改所有子栏目的模板设置!</span>";
						Ext.Msg.confirm(sHtml, {
							ok : function(){
								verifyPassword(function(){
										 FloatPanel.close();
										 resetTemplates(objId, m_nWebSiteObjType);
								},"同步模板到子栏目-");
							}
						});
					}else{
						FloatPanel.close();
						synchTemplates(objId, m_nWebSiteObjType, args.ids.join());
					}
				}
			});
		}
	});

//keywordmgr
	Ext.apply(wcm.domain.WebSiteMgr, {
		keyword : function(event){
			var aId = event.getObjsOrHost().getIds();
			var type = event.getObjsOrHost().objType;
			var nId = parseInt(aId.join(","))||event.getContext().get('siteType');
			var para = {};
			if(type.toUpperCase() =="WEBSITEROOT"||type.toUpperCase()=="WEBSITEINROOT"){
				para = {
					siteType : nId,
					siteId : 0
				}
			}else {
				para = {
					siteType : event.getContext().get('siteType'),
					siteId : nId
				}
			}
			wcm.CrashBoarder.get('DIALOG_KEYWORD_MGR').show({
				title : wcm.LANG.WEBSITE_40 || '管理关键词',
				src : WCMConstants.WCM6_PATH + 'keyword/keyword_list.html',
				width:'850px',
				height:'400px',
				maskable:true,
				params :  para
			});
		}
	});

	//preview and publish operators.
	function preview(_sId, _nObjectType){
		wcm.domain.PublishAndPreviewMgr.preview(_sId, _nObjectType);
	}

	function publish(_sIds, _nObjectType, _sPublishTypeMethod){
		wcm.domain.PublishAndPreviewMgr.publish(_sIds, _nObjectType, _sPublishTypeMethod);
	}

	Ext.apply(wcm.domain.WebSiteMgr, {
		preview : function(event){
			var aIds = event.getObjsOrHost().getIds();
			preview(aIds.join(","), m_nWebSiteObjType);
		},
		quickpublish : function(event){
			m_oMgr.homepublish(event);
		},
		homepublish : function(event){
			m_oMgr.publish(event, "soloPublish");
		},
		increasepublish : function(event){
			m_oMgr.publish(event, "increasingPublish");
		},
		completepublish : function(event){
			m_oMgr.publish(event, "fullyPublish");
		},
		updatepublish : function(event){
			m_oMgr.publish(event, "refreshPublish");
		},
		cancelpublish : function(event){
			m_oMgr.publish(event, "recallPublish");
		},
		publish : function(event, sPublishTypeMethod){
			var aIds = event.getObjsOrHost().getIds();
			publish(aIds.join(","), m_nWebSiteObjType, sPublishTypeMethod);
		}
	});

	function $openCentralWin(_sUrl, _sName){
		var _WIN_WIDTH = window.screen.availWidth;
		var _WIN_HEIGHT = window.screen.availHeight;
		var y = _WIN_HEIGHT * 0.12;
		var x = _WIN_HEIGHT * 0.17;
		var w = _WIN_WIDTH - 2 * x;
		var h = w * 0.618;

		var sFeature = 'resizable=yes,top=' + y + ',left='
				+ x + ',menubar =no,toolbar =no,width=' + w + ',height='
				+ h + ',scrollbars=yes,location =no,status=no,titlebar=no';

		var sName	= _sName || "";
		sName = sName.replace(/[^0-9a-zA-Z_]/g,'_');
		var oWin = window.open(_sUrl, sName, sFeature);
		if(oWin) oWin.focus();
	}
	Ext.apply(wcm.domain.WebSiteMgr, {
		setSiteUser : function(event){
			var id = event.getObj().getId();
			$openCentralWin(WCMConstants.WCM6_PATH + "../console/channel/siteuser_list.jsp?SiteId=" + id, "wcm61-siteuser");
		}
	});

	Ext.apply(wcm.domain.WebSiteMgr, {
		commentmgr : function(event){
			var hostId = event.getHost().getId();
			var sIds = (event.getIds().length == 0)?hostId : event.getIds();
			var oParams = {
				SiteId : sIds
			}
			var sUrl = WCMConstants.WCM_ROOTPATH + 'comment/comment_mgr.jsp?' + $toQueryStr(oParams);
			var sWinName = 'comment_name';
			$openMaxWin(sUrl, sWinName);
		}
	});
	Ext.apply(wcm.domain.WebSiteMgr, {
		pubstat : function(event){
			var hostId = event.getHost().getId();
			var sIds = (event.getIds().length == 0)?hostId : event.getIds();
			var oParams = {
				SiteId : sIds
			}
			var sUrl = WCMConstants.WCM6_PATH + '../console/stat/doccount_site_byuser.jsp?' + $toQueryStr(oParams);
			$openMaxWin(sUrl);
		}
	});
})();
(function(){
	var pageObjMgr = wcm.domain.WebSiteMgr;
	var reg = wcm.SysOpers.register;

	reg({
		key : 'edit',
		type : 'website',
		desc : wcm.LANG.WEBSITE_EDIT||'修改这个站点',
		title : wcm.LANG.WEBSITE_EDIT_TITLE||'修改站点的基本属性和发布设置等相关信息',
		rightIndex : 1,
		order : 1,
		fn : pageObjMgr['edit'],
		quickKey : 'M'
	});
	reg({
		key : 'preview',
		type : 'website',
		desc : wcm.LANG.WEBSITE_PREVIEW||'预览这个站点',
		title : wcm.LANG.WEBSITE_PREVIEW_TITLE||'重新生成并打开这个站点的预览页面',
		rightIndex : 3,
		order : 2,
		fn : pageObjMgr['preview'],
		quickKey : 'R'
	});
	reg({
		key : 'increasepublish',
		type : 'website',
		desc : wcm.LANG.WEBSITE_ADDPUB||'增量发布这个站点',
		title : wcm.LANG.WEBSITE_ADDPUB_TITLE||'发布站点和栏目的首页，并且发布所有处于可发布状态的文档',
		rightIndex : 5,
		order : 3,
		fn : pageObjMgr['increasepublish'],
		quickKey : 'P'
	});
	reg({
		key : 'homepublish',
		type : 'website',
		desc : wcm.LANG.WEBSITE_HOMEPUB||'仅发布这个站点首页',
		title : wcm.LANG.WEBSITE_HOMEPUB_TITLE|| '只更新和发布当前站点的首页',
		rightIndex : 5,
		order : 4,
		fn : pageObjMgr['homepublish']
	});
	reg({
		key : 'synTemplates',
		type : 'website',
		desc : wcm.LANG.WEBSITE_SYNTEMP||'同步模板到栏目',
		title : wcm.LANG.WEBSITE_SYNTEMP_TITLE||'将当前站点的模板应用到指定的栏目上',
		rightIndex : 1,
		order : 5,
		fn : pageObjMgr['synTemplates']
	});
	reg({
		key : 'seperate',
		type : 'website',
		desc : wcm.LANG.WEBSITE_SEP||'分隔线',
		title : "分隔线",
		rightIndex : -1,
		order : 6,
		fn : pageObjMgr['seperate']
	});
	reg({
		key : 'export',
		type : 'website',
		desc : wcm.LANG.WEBSITE_EXPORT||'导出这个站点到',
		title : wcm.LANG.WEBSITE_EXPORT_TITLE ||'将当前站点内容导出成XML格式文件',
		rightIndex : 1,
		order : 7,
		fn : pageObjMgr['export'],
		quickKey : 'X'
	});
	reg({
		key : 'seperate',
		type : 'website',
		desc : wcm.LANG.WEBSITE_SEP||'分隔线',
		rightIndex : -1,
		order : 8,
		fn : pageObjMgr['seperate']
	});
	reg({
		key : 'completepublish',
		type : 'website',
		desc : wcm.LANG.WEBSITE_COMPUB||'完全发布这个站点',
		title : wcm.LANG.WEBSITE_COMPUB_TITLE||'重新生成这个站点的所有文件',
		rightIndex : 4,
		order : 9,
		fn : pageObjMgr['completepublish']
	});
	reg({
		key : 'updatepublish',
		type : 'website',
		desc : wcm.LANG.WEBSITE_UPDATEPUB||'更新发布这个站点',
		title : wcm.LANG.WEBSITE_UPDATEPUB_TITLE||'更新当前站点和相关栏目的首页',
		rightIndex : 5,
		order : 10,
		fn : pageObjMgr['updatepublish']
	});
	/*reg({
		key : 'cancelpublish',
		type : 'website',
		desc : wcm.LANG.WEBSITE_CANCELPUB||'撤销发布这个站点',
		rightIndex : 4,
		order : 11,
		fn : pageObjMgr['cancelpublish']
	});*/
	reg({
		key : 'seperate',
		type : 'website',
		desc : wcm.LANG.WEBSITE_SEP||'分隔线',
		rightIndex : -1,
		order : 12,
		fn : pageObjMgr['seperate']
	});
	reg({
		key : 'likecopy',
		type : 'website',
		desc : wcm.LANG.WEBSITE_LIKECOPY||'类似创建',
		title : wcm.LANG.WEBSITE_LIKECOPY_TITLE||'创建相似的站点（唯一标识和存放位置不同）',
		rightIndex : -2,
		order : 13,
		fn : pageObjMgr['likecopy']
	});
	reg({
		key : 'seperate',
		type : 'website',
		desc : wcm.LANG.WEBSITE_SEP||'分隔线',
		rightIndex : -1,
		order : 14,
		fn : pageObjMgr['seperate']
	});
	/*reg({
		key : 'setSiteUser',
		type : 'website',
		desc : wcm.LANG.WEBSITE_39||'设置站点用户',
		rightIndex : 6,
		order : 15,
		fn : pageObjMgr['setSiteUser']
	});*/
	reg({
		key : 'keyword',
		type : 'website',
		desc : wcm.LANG.WEBSITE_40||'管理关键词',
		title : wcm.LANG.WEBSITE_40_TITLE||'管理当前站点下的所有关键词',
		rightIndex : 1,
		order : 16,
		fn : pageObjMgr['keyword']
	});
	reg({
		key : 'keyword',
		type : 'websiteInRoot',
		desc : wcm.LANG.WEBSITE_40||'管理关键词',
		title : wcm.LANG.WEBSITE_40_TITLE||'管理当前站点下的所有关键词',
		rightIndex : -2,
		order : 5,
		fn : pageObjMgr['keyword']
	});
	reg({
		key : 'recycle',
		type : 'website',
		desc : wcm.LANG.WEBSITE_RECYCLE||'删除站点',
		title : wcm.LANG.WEBSITE_RECYCLE_TITLE||'将站点放入回收站',
		rightIndex : 2,
		order : 15,
		fn : pageObjMgr['recycle'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'new',
		type : 'websiteInRoot',
		desc : wcm.LANG.WEBSITE_NEW||'创建一个新站点',
		title : wcm.LANG.WEBSITE_NEW_TITLE||'新建一个站点节点',
		rightIndex : -2,
		order : 1,
		fn : pageObjMgr['new'],
		quickKey : 'N'
	});
	reg({
		key : 'quicknew',
		type : 'websiteInRoot',
		desc : wcm.LANG.WEBSITE_QUICKNEW||'智能创建一个新站点',
		title : wcm.LANG.WEBSITE_QUICKNEW_TITLE||'按照已经完成的站点样例智能创建一个相似的新站点',
		rightIndex : -2,
		order : 2,
		fn : pageObjMgr['quicknew'],
		isVisible : function(event){
			var context = event.getContext();
			if(context.params["SITETYPE"] == 0){
				return true;
			}
			return false;
		}
	});
	reg({
		key : 'import',
		type : 'websiteInRoot',
		desc : wcm.LANG.WEBSITE_IMPORT||'从外部导入站点',
		title : wcm.LANG.WEBSITE_IMPORT_TITLE||'读取外部站点文件（只支持XML和ZIP文件），并生成相应的站点',
		rightIndex : -2,
		order : 3,
		fn : pageObjMgr['import']
	});
	reg({
		key : 'export',
		type : 'websites',
		desc : wcm.LANG.WEBSITE_EXPORTSOME||'导出这些站点到',
		title : wcm.LANG.WEBSITE_EXPORTSOME_TITLE||'导出这些站点，每个站点生成相应的XML文件',
		rightIndex : 1,
		order : 22,
		fn : pageObjMgr['export'],
		quickKey : 'X'
	});
	reg({
		key : 'recycle',
		type : 'websites',
		desc : wcm.LANG.WEBSITE_RECYCLE||'删除站点',
		title : wcm.LANG.WEBSITE_RECYCLE_TITLE|| '将站点放入回收站',
		rightIndex : 2,
		order : 23,
		fn : pageObjMgr['recycle'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'preview',
		type : 'websites',
		desc : wcm.LANG.WEBSITE_PREVIEWSOME||'预览这些站点',
		title : wcm.LANG.WEBSITE_PREVIEWSOME_TITLE || '重新生成并打开这些站点的预览页面',
		rightIndex : 3,
		order : 24,
		fn : pageObjMgr['preview'],
		quickKey : 'R'
	});
	reg({
		key : 'increasepublish',
		type : 'websites',
		desc : wcm.LANG.WEBSITE_ADDPUBSOME||'增量发布这些站点',
		title : wcm.LANG.WEBSITE_ADDPUBSOME_TITLE||'发布选中站点和他们的栏目的首页，并且发布所有可发布状态的文档',
		rightIndex : 5,
		order : 25,
		fn : pageObjMgr['increasepublish'],
		quickKey : 'P'
	});
	reg({
		key : 'homepublish',
		type : 'websites',
		desc : wcm.LANG.WEBSITE_HOMEPUBSOME||'仅发布这些站点首页',
		title : wcm.LANG.WEBSITE_HOMEPUBSOME_TITLE||'重新生成并发布选中站点的首页',
		rightIndex : 5,
		order : 26,
		fn : pageObjMgr['homepublish']
	});
	reg({
		key : 'completepublish',
		type : 'websites',
		desc : wcm.LANG.WEBSITE_COMPUBSOME||'完全发布这些站点',
		title : wcm.LANG.WEBSITE_COMPUBSOME_TITLE||'重新生成这些站点的所有文件',
		rightIndex : 4,
		order : 27,
		fn : pageObjMgr['completepublish']
	});
	reg({
		key : 'updatepublish',
		type : 'websites',
		desc : wcm.LANG.WEBSITE_UPDATEPUBSOME||'更新发布这些站点',
		title :  wcm.LANG.WEBSITE_UPDATEPUBSOME_TITLE||'更新这些站点和相关栏目的首页',
		rightIndex : 5,
		order : 28,
		fn : pageObjMgr['updatepublish']
	});
	/*reg({
		key : 'cancelpublish',
		type : 'websites',
		desc : wcm.LANG.WEBSITE_CANCELPUBSOME||'撤销发布这些站点',
		rightIndex : 4,
		order : 29,
		fn : pageObjMgr['cancelpublish']
	});*/
	reg({
		key : 'seperate',
		type : 'websites',
		desc : wcm.LANG.WEBSITE_SEP||'分隔线',
		title : "分隔线",
		rightIndex : -1,
		order : 30,
		fn : pageObjMgr['seperate']
	});
	reg({
		key : 'createFromFile',
		type : 'websiteInRoot',
		desc : wcm.LANG.WEBSITE_BATCHNEW||'批量创建站点',
		title : wcm.LANG.WEBSITE_BATCHNEW_TITLE||'从文件批量创建站点结构',
		rightIndex : -2,
		order : 4,
		fn : pageObjMgr['createFromFile']
	});
	reg({
		key : 'commentmgr',
		type : 'website',
		desc : wcm.LANG.WEBSITE_COMMENTMGR||'管理评论',
		title : wcm.LANG.WEBSITE_COMMENTMGR_TITLE||'管理该站点下的所有评论',
		rightIndex : 8,
		order : 17,
		fn : pageObjMgr['commentmgr'],
		isVisible : function(event){
			try{
				return $MsgCenter.getActualTop().g_IsRegister['comment'];
			}catch(err){
				return false;
			}
		}
	});
	reg({
		key : 'pubstat',
		type : 'website',
		desc : wcm.LANG.WEBSITE_PUBSTAT ||'发文统计',
		titile : '发文统计',
		rightIndex : 1,
		order : 18,
		fn : pageObjMgr['pubstat']
	});
	reg({
		key : 'setappendixsize',
		type : 'website',
		desc : wcm.LANG.website_2011 || '设置这个站点文档附件大小',
		title : wcm.LANG.website_2011 || '设置这个站点上传文档附件大小',
		rightIndex : 1,
		order : 10,
		fn : pageObjMgr['setAppendixSize'],
		isVisible : function(event){
			 return true;
		}
	});
})();