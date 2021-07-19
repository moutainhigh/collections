//栏目操作信息和Mgr定义
Ext.ns('wcm.domain.ChannelMgr');

(function(){
	var m_nChannelObjType = 101;
	var m_sServiceId = 'wcm6_channel';
	var m_oMgr = wcm.domain.ChannelMgr;
	function getHelper(){
		return new com.trs.web2frame.BasicDataHelper();
	}

	function edit(event, oParams){
		FloatPanel.open({
			src : WCMConstants.WCM6_PATH + 'channel/channel_add_edit.jsp?' + $toQueryStr(oParams),
			title : wcm.LANG.CHANNEL_9||'新建/修改栏目',
			callback : function(objId){
				CMSObj[oParams["objectid"] > 0 ? 'afteredit' : 'afteradd'](event)({objType:WCMConstants.OBJ_TYPE_CHANNEL, objId:objId});
			}
		});
	}

	function renderMove(event, _args){
		moveAsChild(_args.srcId, _args.dstId, (_args.dstType == 103), event);
	}
	function moveAsChild(_nSrcChnlIds, _nTargetId, _bIsSite, event){
		var oPostData = {srcChannelIds : _nSrcChnlIds};
		if(_bIsSite === true) {
			oPostData.DstSiteId = _nTargetId;
		}else{
			oPostData.DstChannelId = _nTargetId;
		}
		getHelper().call(m_sServiceId, 'moveAsChild', oPostData, true, function(){
			//通知其他页面刷新
			var objsOrHost = event.getObjsOrHost();
			var items = [];
			for (var i = 0; i < objsOrHost.length(); i++){
				items.push({objId : objsOrHost.getAt(i).getId()});
			}
			var context = {dstObjectId : _nTargetId, isSite : _bIsSite};
			var oCmsObjs = CMSObj.createEnumsFrom({
				objType : objsOrHost.getType()
			}, context);
			oCmsObjs.addElement(items);
			oCmsObjs.aftermove();
		});
	}
	function renderLikecopy(event,_args){
		var oPostData = {srcChannelId: _args.srcId};
		if(_args.dstType == 103) {
			oPostData.DstSiteId = _args.dstId;
		}else{
			oPostData.DstChannelId = _args.dstId;
		}
		var nType = _args.dstType, nDstSiteId = oPostData.DstSiteId, nDstChnlId = oPostData.DstChannelId;
		ProcessBar.start(wcm.LANG.CHANNEL_8||'栏目类似创建');
		getHelper().call(m_sServiceId, 'createFrom', oPostData, true, function(_trans, _json){
			var bSucess = $v(_json, 'REPORTS.IS_SUCCESS');
			var objectIds = $v(_json, 'REPORTS.ObjectIds.ObjectId');
			ProcessBar.close();
			Ext.Msg.report(_json, wcm.LANG.CHANNEL_7||'栏目类似创建结果', function(){
				if(bSucess != 'true') return;
				var wcmEvent = CMSObj.createEvent(event.getHost(), {
					objId : _args.dstId,
					objType : _args.dstType==103 ?
						WCMConstants.OBJ_TYPE_WEBSITE : WCMConstants.OBJ_TYPE_CHANNELMASTER
				});
				CMSObj.afteradd(wcmEvent)({objType:WCMConstants.OBJ_TYPE_CHANNEL, objId:objectIds});
			});
		});
	}
	function resetTemplates(_nFolderId, _nObjType, _fCallBack){
		getHelper().call('wcm6_template', 'impartTemplateConfig',
			{objectId: _nFolderId, ObjectType:_nObjType}, true, _fCallBack);
	}
	function synchTemplates(_nFolderId, _nObjType, childObjIds, _fCallBack){
		getHelper().call('wcm61_template', 'synTemplates',
			{objectId: _nFolderId, ObjectType:_nObjType, objectIds : childObjIds}, true, _fCallBack);
	}
	Ext.apply(wcm.domain.ChannelMgr, {
		'new' : function(event){
			var hostId = event.getHost().getId();
			var hostType = event.getHost().getIntType();
			edit(event, {
				objectid:	0,
				channelid:	0,
				parentid:	hostType == 101 ? hostId : 0,
				siteid:		hostType == 101 ? 0 : hostId
			});
		},
		edit : function(event){
			var hostId = event.getHost().getId();
			var hostType = event.getHost().getIntType();
			var obj = event.getObj().getId();
			edit(event, {
				objectid:	event.getObj().getId(),
				channelid:	event.getObj().getId(),
				parentid:	hostType == 101 ? hostId : 0,
				siteid:		hostType == 101 ? 0 : hostId
			});
		},
		'import' : function(event){
			var hostId = event.getHost().getId();
			var hostType = event.getHost().getIntType();
			var params = {
				parentid : hostId,
				objecttype : hostType
			}
			FloatPanel.open(WCMConstants.WCM6_PATH + 'channel/channel_import.html?' + $toQueryStr(params), wcm.LANG.CHANNEL_6||'栏目导入', function(){
				CMSObj.afteradd(event)({objType:WCMConstants.OBJ_TYPE_CHANNEL});
			});
		},

		export0 : function (oPostData){
			if(oPostData.ObjectIds){
				FloatPanel.open(WCMConstants.WCM6_PATH + 'channel/channel_export.html?' + $toQueryStr(oPostData)
					, wcm.LANG.CHANNEL_10||'导出栏目');
			}else
				FloatPanel.open(WCMConstants.WCM6_PATH + 'channel/channel_export.html?' + $toQueryStr(oPostData)
					, wcm.LANG.CHANNEL_66||'导出所有栏目');
		},

		exportAll : function(event){
			var hostId = event.getHost().getId();
			var hostType = event.getHost().getIntType();
			if(event.getContext().RecordNum <= 0) {
				Ext.Msg.$fail(wcm.LANG.CHANNEL_67||'没有任何要导出的栏目。');
				return false;
			}
			var objId = hostId;
			var oPostData = {};
			oPostData[hostType == 101 ? 'parentChannelId' : 'parentSiteId'] = objId;
			wcm.domain.ChannelMgr.export0(oPostData);
		},
		'export' : function(event){
			var hostId = event.getHost().getId();
			var hostType = event.getHost().getIntType();
			var objType = event.getObjs().getIntType();
			var objIds = objType == 101 ? event.getIds().join(",") || hostId : hostId;
			wcm.domain.ChannelMgr.export0({ObjectIds : objIds});
		},
		move : function(event){
			var host = event.getHost();
			var hostId = host.getId();
			var hosttype = host.getIntType();
			var sIds = (event.getIds().length == 0)?hostId : event.getIds();
			var sUrl = WCMConstants.WCM6_PATH + 'channel/channel_select_move.html?srcId=' + sIds;
			var sFolderInfo = 'folderType=' + hosttype;
			sFolderInfo += '&folderId=' + hostId;
			sUrl += '&' + sFolderInfo;
			FloatPanel.open(sUrl, wcm.LANG.CHANNEL_5||'栏目移动',renderMove.bind(this, event));
		},

		likecopy : function(event){
			var host = event.getHost();
			var hostId = host.getId();
			var sSrcId = (event.getIds().length == 0)?hostId : event.getIds();
			var sSiteType = event.getContext().params["SITETYPE"];
			var oParams = {srcId : sSrcId, siteType : sSiteType};
			FloatPanel.open(WCMConstants.WCM6_PATH + 'channel/channel_select_likecopy.jsp?' + $toQueryStr(oParams) , wcm.LANG.CHANNEL_LIKECOPY_2 || '类似创建',renderLikecopy.bind(this,event));
		},

		trash : function(event){
			var hostId = event.getHost().getId();
			var sIds = (event.getIds().length == 0)?hostId : event.getIds();
			sIds = sIds + '';
			var nCount = (sIds.indexOf(',') == -1) ? 1 : sIds.split(',').length;
			var sCon = "";
			if(nCount == 1){
				sCon = wcm.LANG.CHANNEL_12||"确实要将此栏目放入回收站吗?";
			}else{
				sCon = String.format(wcm.LANG.CHANNEL_13||("确实要将这 {0} 个栏目放入回收站吗?"),nCount);
			}
			// 添加对栏目是否是专题对应栏目的判断
			getHelper().call('wcm61_special', 'findByChnlIds', {ChannelIds : sIds}, true, function(transport, _json){
				try{
					var oSpecials = $a(_json, "Specials.Special");
					if(!oSpecials[0]){
						if (confirm(sCon)){
							ProcessBar.start(wcm.LANG.CHANNEL_4||'删除栏目');
							getHelper().call(m_sServiceId, 'delete', {ObjectIds: sIds, drop: false}, true, function(){
								ProcessBar.close();
								window.setTimeout(function(){
									event.getObjsOrHost().afterdelete();
								}, 500);
							});
						}
					}else {
						alert("包含与专题对应的栏目，这样的栏目不能直接被删除，请到专题管理页面删除对应专题！");
					}
					
				}catch(e){
					alert(e);
				}
			});
			
		},
		commentmgr : function(event){
			var hostId = event.getHost().getId();
			var sIds = (event.getIds().length == 0)?hostId : event.getIds();
			var oParams = {
				ChannelId : sIds
			}
			var sUrl = WCMConstants.WCM_ROOTPATH + 'comment/comment_mgr.jsp?' + $toQueryStr(oParams);
			var sWinName = 'comment_name';
			$openMaxWin(sUrl, sWinName);
		},
		chnlpositionset : function(event){
			var oPageParams = event.getContext();
			//var host = event.getHost();
			//var hostid = host.getId();
			//var objType = event.getObjs().getAt(0).objType;
			var channelId = 0;
			if(event.getObjs().getAt(0)==null){
				channelId = event.getHost().getId();
			}
			else {
				channelId = event.getObjs().getAt(0).getId();
			}
			//Object.extend(oPageParams,host.getType()=="website"?{"siteid" : hostid}:{"channelid" : hostid});
			Object.extend(oPageParams,{"ChannelId":channelId});
			FloatPanel.open(WCMConstants.WCM6_PATH + 'channel/channel_position_set.jsp?' + $toQueryStr(oPageParams),
			wcm.LANG.CHANNEL_48||'栏目-调整顺序', CMSObj.afteredit(event));
		},
		synTemplates : function(event){
			var objId = event.getObj().getId();
			var params = {
				objType:m_nChannelObjType,
				objId:objId,
				close : 0,
				ExcludeTop : 1,
				ExcludeInfoview : 0,
				ExcludeLink : 1
			};
			FloatPanel.open({
				src : WCMConstants.WCM6_PATH + 'channel/object_select.html',
				dialogArguments : params,
				title : wcm.LANG.CHANNEL_64||'选择栏目',
				callback : function(args){
					if(args.allChildren){
						var sHtml = wcm.LANG.CHANNEL_11||"确实要同步模板到子栏目吗？<br><span style=\'color:red;font:16px;\'>注意：<br>该操作会覆盖更改所有子栏目的模板设置！</span>";
						Ext.Msg.confirm(sHtml, {
							ok : function(){
								FloatPanel.close();
								resetTemplates(objId, m_nChannelObjType);
							}
						});
					}else{
						FloatPanel.close();
						synchTemplates(objId, m_nChannelObjType, args.ids.join());
					}
				}
			});
		},
		preview : function(event){
			var hostId = event.getHost().getId();
			var aObjIds = (event.getIds().length == 0)?hostId : event.getIds().join(",");
			wcm.domain.PublishAndPreviewMgr.preview(aObjIds, m_nChannelObjType);
		},
		publish : function(event, _sServiceId){
			var hostId = event.getHost().getId();
			var aObjIds = (event.getIds().length == 0)?hostId : event.getIds().join(",");
			wcm.domain.PublishAndPreviewMgr.publish(aObjIds, m_nChannelObjType, _sServiceId);
		},
		increasingpublish : function(event){
			m_oMgr.publish(event, 'increasingPublish');
		},
		increasepublish : function(event){
			m_oMgr.increasingpublish(event);
		},
		fullypublish : function(event){
			m_oMgr.publish(event, 'fullyPublish');
		},
		refreshpublish : function(event){
			m_oMgr.publish(event, 'refreshPublish');
		},
		solopublish : function(event){
			m_oMgr.publish(event, 'soloPublish');
		},
		recallpublish : function(event){
			var sHtml = String.format("确实要{0}撤销发布{1}这个栏目么？将{2}不可逆转！{3}",'<font color=\'red\' style=\'font-size:14px;\'>','</font>','<font color=\'red\' style=\'font-size:14px;\'>','</font>');
			Ext.Msg.confirm(sHtml,{
				yes : function(){
                   m_oMgr.publish(event, 'recallPublish');
                }
			})
		},
		createFromFile : function(event){
			var hostId = event.getHost().getId();
			var hostType = event.getHost().getIntType();
			var params = {
				parentid : hostId,
				objecttype : hostType
			}

			FloatPanel.open(WCMConstants.WCM6_PATH + 'channel/channel_create_fromfile.html?' + $toQueryStr(params), '批量创建栏目', function(){
				CMSObj.afteradd(event)({objType:WCMConstants.OBJ_TYPE_CHANNEL});
			});
		}
	});

})();
(function(){
	var pageObjMgr = wcm.domain.ChannelMgr;
	var reg = wcm.SysOpers.register;
	var fnIsVisible = function(event){
		var objs = event.getObjs();
		var context = event.getContext();
		if(objs.length() > 0){
			var chnlTypes = objs.getPropertyAsInt("chnlType", 0);
		}else{
			var chnlTypes = context.get('ChannelType') || 0;
		}
		if(!Array.isArray(chnlTypes)){
			chnlTypes = [chnlTypes];
		}
		var hideChnlTypes = [1,2,11];
		for (var i = 0; i < chnlTypes.length; i++){
			if(hideChnlTypes.include(chnlTypes[i])) return false;
		}
		return true;
	}
	var fnIsVisible2 = function(event){
		var objs = event.getObjs();
		var context = event.getContext();
		if(objs.length() > 0){
			var chnlTypes = objs.getPropertyAsInt("chnlType", 0);
		}else{
			var chnlTypes = context.get('ChannelType') || 0;
		}
		if(!Array.isArray(chnlTypes)){
			chnlTypes = [chnlTypes];
		}
		var hideChnlTypes = [1,2,11,13];
		for (var i = 0; i < chnlTypes.length; i++){
			if(hideChnlTypes.include(chnlTypes[i])) return false;
		}
		return true;
	}
	var fnIsVisible3 = function(event){
		var objs = event.getObjs();
		var context = event.getContext();
		if(context.params.SITETYPE && context.params.SITETYPE.indexOf("4") != -1)
			return false;
		if(objs.length() > 0){
			var chnlTypes = objs.getPropertyAsInt("chnlType", 0);
		}else{
			var chnlTypes = context.get('ChannelType') || 0;
		}
		if(!Array.isArray(chnlTypes)){
			chnlTypes = [chnlTypes];
		}
		var hideChnlTypes = [1,2,11,13];
		for (var i = 0; i < chnlTypes.length; i++){
			if(hideChnlTypes.include(chnlTypes[i])) return false;
		}
		return true;
	}
	reg({
		key : 'edit',
		type : 'channel',
		desc : wcm.LANG.CHANNEL_EDIT || '修改这个栏目',
		title : wcm.LANG.CHANNEL_EDIT_TITLE || '修改栏目的基本属性和发布设置等相关信息',
		rightIndex : 13,
		order : 1,
		fn : pageObjMgr['edit'],
		quickKey : 'M'
	});
	reg({
		key : 'move',
		type : 'channel',
		desc : wcm.LANG.CHANNEL_MOVE || '移动这个栏目',
		title : wcm.LANG.CHANNEL_MOVE_TITLE || '移动这个栏目到指定的栏目',
		rightIndex : 12,
		order : 2,
		fn : pageObjMgr['move']
	});
	reg({
		key : 'preview',
		type : 'channel',
		desc : wcm.LANG.CHANNEL_PREVIEW || '预览这个栏目',
		title : wcm.LANG.CHANNEL_PREVIEW_TITLE || '重新生成并打开这个栏目的预览页面',
		rightIndex : 15,
		order : 4,
		fn : pageObjMgr['preview'],
		isVisible : fnIsVisible,
		quickKey : 'R'
	});
	reg({
		key : 'increasingpublish',
		type : 'channel',
		desc : wcm.LANG.CHANNEL_ADDPUB || '增量发布这个栏目',
		title : wcm.LANG.CHANNEL_ADDPUB_TITLE || '发布栏目的首页，并且发布所有处于可发布状态的文档',
		rightIndex : 17,
		order : 5,
		fn : pageObjMgr['increasingpublish'],
		isVisible : fnIsVisible,
		quickKey : 'P'
	});
	reg({
		key : 'solopublish',
		type : 'channel',
		desc : wcm.LANG.CHANNEL_SOLOPUB || '仅发布此栏目的首页',
		title : wcm.LANG.CHANNEL_SOLOPUB_TITLE || '重新生成并发布当前栏目的首页',
		rightIndex : 17,
		order : 6,
		fn : pageObjMgr['solopublish'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'export',
		type : 'channel',
		desc : wcm.LANG.CHANNEL_EXPORT || '导出这个栏目',
		title : wcm.LANG.CHANNEL_EXPORT_SEL || '导出当前选中的栏目（提供下载的zip或者xml文件）',
		rightIndex : 13,
		order : 7,
		fn : pageObjMgr['export']
	});
	reg({
		key : 'synTemplates',
		type : 'channel',
		desc : wcm.LANG.CHANNEL_SYNCH || '同步模板到子栏目',
		title : wcm.LANG.CHANNEL_SYNCH_TITLE || '将当前栏目的模板应用到其子栏目上',
		rightIndex : 13,
		order : 8,
		fn : pageObjMgr['synTemplates'],
		isVisible : fnIsVisible2
	});
	reg({
		key : 'seperate',
		type : 'channel',
		desc : wcm.LANG.CHANNEL_SEPERATE || '分隔线',
		rightIndex : -1,
		order : 9,
		fn : pageObjMgr['seperate']
	});
	reg({
		key : 'fullypublish',
		type : 'channel',
		desc : wcm.LANG.CHANNEL_FULLPUB || '完全发布这个栏目',
		title : wcm.LANG.CHANNEL_FULLPUB_TITLE || '重新生成并发布当前栏目下的的所有文件',
		rightIndex : 16,
		order : 10,
		fn : pageObjMgr['fullypublish'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'refreshpublish',
		type : 'channel',
		desc : wcm.LANG.CHANNEL_REFRESHPUB || '更新发布这个栏目',
		title : wcm.LANG.CHANNEL_REFRESHPUB_TITLE || '更新当前栏目和其相关栏目的首页',
		rightIndex : 17,
		order : 11,
		fn : pageObjMgr['refreshpublish'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'recallpublish',
		type : 'channel',
		desc : wcm.LANG.CHANNEL_CANCELPUB || '撤销发布这个栏目',
		title : wcm.LANG.CHANNEL_CANCELPUB_PUBED || '撤回已发布目录或页面',
		rightIndex : 16,
		order : 12,
		fn : pageObjMgr['recallpublish'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'seperate',
		type : 'channel',
		desc : wcm.LANG.CHANNEL_SEPERATE || '分隔线',
		rightIndex : -1,
		order : 13,
		fn : pageObjMgr['seperate']
	});
	reg({
		key : 'likecopy',
		type : 'channel',
		desc : wcm.LANG.CHANNEL_LIKECOPY || '类似创建',
		title : wcm.LANG.CHANNEL_LIKECOPY_TITLE || '创建相似的栏目（唯一标识和存放位置不同）',
		rightIndex : 13,
		order : 14,
		fn : pageObjMgr['likecopy']
	});
	reg({
		key : 'docpositionset',
		type : 'channel',
		desc :  wcm.LANG.CHANNEL_DOCPOSITIONSET ||  '调整顺序',
		title : wcm.LANG.CHANNEL_DOCPOSITIONSET_TITLE ||  '调整当前栏目在站点中的顺序',
		isVisible : fnIsVisible,
		rightIndex : 13,
		order : 35,
		fn : pageObjMgr['chnlpositionset']
	});
	reg({
		key : 'commentmgr',
		type : 'channel',
		desc : wcm.LANG.CHANNEL_COMMENT || '管理评论',
		title : wcm.LANG.CHANNEL_COMMENT_TITLE || '管理当前栏目下的所有评论',
		rightIndex : 8,
		order : 16,
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
		key : 'new',
		type : 'websiteHost',
		desc : wcm.LANG.CHANNEL_NEW || '新建一个栏目',
		title : wcm.LANG.CHANNEL_NEW_INSITE || '在当前站点下创建一个新栏目',
		rightIndex : 11,
		order : 258,
		fn : pageObjMgr['new'],
		quickKey : 'N'
	});
	reg({
		key : 'import',
		type : 'websiteHost',
		desc : wcm.LANG.CHANNEL_IMPORT || '导入栏目',
		title : wcm.LANG.CHANNEL_IMPORT_INSITE || '在当前站点中导入一个子栏目（从zip文件中）',
		rightIndex : 11,
		order : 259,
		fn : pageObjMgr['import']
	});
	reg({
		key : 'export',
		type : 'websiteHost',
		desc : wcm.LANG.CHANNEL_EXPORTALL || '导出所有子栏目',
		title : wcm.LANG.CHANNEL_EXPORTALL_INSITE || '导出当前站点下的所有子栏目（提供下载的zip或者xml文件）',
		rightIndex : 13,
		order : 260,
		fn : pageObjMgr['exportAll']
	});
	reg({
		key : 'new',
		type : 'channelHost',
		desc : wcm.LANG.CHANNEL_NEW || '新建一个栏目',
		title : wcm.LANG.CHANNEL_NEW_INCHANNEL ||'在当前栏目中新建一个子栏目',
		rightIndex : 11,
		order : 18,
		fn : pageObjMgr['new'],
		quickKey : 'N'
	});
	reg({
		key : 'import',
		type : 'channelHost',
		desc : wcm.LANG.CHANNEL_IMPORT || '导入栏目',
		title : wcm.LANG.CHANNEL_IMPORT_INCHANNEL || '在当前栏目中导入一个子栏目（从zip文件中）',
		rightIndex : 11,
		order : 19,
		fn : pageObjMgr['import']
	});
	reg({
		key : 'export',
		type : 'channelHost',
		desc : wcm.LANG.CHANNEL_EXPORTALL || '导出所有子栏目',
		title : wcm.LANG.CHANNEL_EXPORTALL_INCHANNEL || '导出当前栏目下的所有子栏目（提供下载的zip或者xml文件）',
		rightIndex : 13,
		order : 20,
		fn : pageObjMgr['exportAll'],
		isVisible : function(event){
			var hostchnl = event.getHost();
			if(hostchnl.channelType && hostchnl.channelType==13)return false;
			return true;
		}
	});
	reg({
		key : 'edit',
		type : 'channelMaster',
		desc : wcm.LANG.CHANNEL_EDIT_NOW || '修改当前栏目',
		title : wcm.LANG.CHANNEL_EDIT_NOW_TITLE || '修改当前栏目的基本属性和发布设置等相关信息',
		rightIndex : 13,
		order : 21,
		fn : pageObjMgr['edit'],
		quickKey : 'M'
	});
	reg({
		key : 'move',
		type : 'channelMaster',
		desc : wcm.LANG.CHANNEL_MOVE_NOW || '移动当前栏目',
		title : wcm.LANG.CHANNEL_MOVE_NOW_TITLE || '移动当前栏目到指定的位置',
		rightIndex : 12,
		order : 22,
		fn : pageObjMgr['move']
	});
	reg({
		key : 'trash',
		type : 'channelMaster',
		desc : wcm.LANG.CHANNEL_TRASH || '删除栏目',
		title : wcm.LANG.CHANNEL_TRASH_TITLE || '将栏目放入回收站',
		rightIndex : 12,
		order : 23,
		fn : pageObjMgr['trash'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'preview',
		type : 'channelMaster',
		desc : wcm.LANG.CHANNEL_PREVIEW_NOW || '预览当前栏目',
		title : wcm.LANG.CHANNEL_PREVIEW_NOWPUB || '预览当前栏目的发布效果',
		rightIndex : 15,
		order : 24,
		fn : pageObjMgr['preview'],
		isVisible : fnIsVisible,
		quickKey : 'R'
	});
	reg({
		key : 'increasingpublish',
		type : 'channelMaster',
		desc : wcm.LANG.CHANNEL_ADDPUB_NOW || '增量发布当前栏目',
		title : wcm.LANG.CHANNEL_ADDPUB_NOW_TITLE || '发布当前栏目和其子栏目的首页，并且发布该栏目下所有可发布状态的文档',
		rightIndex : 17,
		order : 25,
		fn : pageObjMgr['increasingpublish'],
		isVisible : fnIsVisible,
		quickKey : 'P'
	});
	reg({
		key : 'solopublish',
		type : 'channelMaster',
		desc : wcm.LANG.CHANNEL_SOLOPUB_NOW || '仅发布当前栏目首页',
		title : wcm.LANG.CHANNEL_SOLOPUB_NOW_TITLE || '重新生成并发布当前栏目的首页',
		rightIndex : 17,
		order : 26,
		fn : pageObjMgr['solopublish'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'export',
		type : 'channelMaster',
		desc : wcm.LANG.CHANNEL_EXPORT_NOW || '导出当前栏目',
		title : wcm.LANG.CHANNEL_EXPORT_NOW_INFO || '导出当前栏目（提供下载的zip或者xml文件）',
		rightIndex : 13,
		order : 27,
		fn : pageObjMgr['export']
	});
	reg({
		key : 'synTemplates',
		type : 'channelMaster',
		desc : wcm.LANG.CHANNEL_SYNCH || '同步模板到子栏目',
		title : wcm.LANG.CHANNEL_SYNCH_TITLE || '将当前栏目的模板应用到其子栏目上',
		rightIndex : 13,
		order : 28,
		fn : pageObjMgr['synTemplates'],
		isVisible : fnIsVisible2
	});
	reg({
		key : 'seperate',
		type : 'channelMaster',
		desc : wcm.LANG.CHANNEL_SEPERATE || '分隔线',
		rightIndex : -1,
		order : 29,
		fn : pageObjMgr['seperate']
	});
	reg({
		key : 'fullypublish',
		type : 'channelMaster',
		desc : wcm.LANG.CHANNEL_FULLPUB_NOW || '完全发布当前栏目',
		title : wcm.LANG.CHANNEL_FULLPUB_NOW_TITLE || '重新生成和发布当前栏目下的所有文档',
		rightIndex : 16,
		order : 30,
		fn : pageObjMgr['fullypublish'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'refreshpublish',
		type : 'channelMaster',
		desc : wcm.LANG.CHANNEL_REFRESHPUB_NOW || '更新发布当前栏目',
		title : wcm.LANG.CHANNEL_REFRESHPUB_SEL || '仅重新生成当前栏目的首页以及相关的概览页面',
		rightIndex : 17,
		order : 31,
		fn : pageObjMgr['refreshpublish'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'recallpublish',
		type : 'channelMaster',
		desc : wcm.LANG.CHANNEL_CANCELPUB_NOW || '撤销发布当前栏目',
		title : wcm.LANG.CHANNEL_CANCELPUB_PUBED || '撤回已发布目录或页面',
		rightIndex : 16,
		rightIndex : 16,
		order : 32,
		fn : pageObjMgr['recallpublish'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'seperate',
		type : 'channelMaster',
		desc : wcm.LANG.CHANNEL_SEPERATE || '分隔线',
		rightIndex : -1,
		order : 33,
		fn : pageObjMgr['seperate']
	});
	reg({
		key : 'likecopy',
		type : 'channelMaster',
		desc : wcm.LANG.CHANNEL_LIKECOPY || '类似创建',
		title : wcm.LANG.CHANNEL_LIKECOPY_TITLE || '创建相似的栏目（唯一标识和存放位置不同）',
		rightIndex : 13,
		order : 34,
		fn : pageObjMgr['likecopy']
	});
	reg({
		key : 'docpositionset',
		type : 'channelMaster',
		desc :  wcm.LANG.CHANNEL_DOCPOSITIONSET ||  '调整顺序',
		title : wcm.LANG.CHANNEL_DOCPOSITIONSET_TITLE || '调整当前栏目在站点中的顺序',
		isVisible : fnIsVisible,
		rightIndex : 13,
		order : 35,
		fn : pageObjMgr['chnlpositionset']
	});
	reg({
		key : 'seperate',
		type : 'channelMaster',
		desc : wcm.LANG.CHANNEL_SEPERATE || '分隔线',
		rightIndex : -1,
		order : 36,
		fn : pageObjMgr['seperate']
	});
	reg({
		key : 'commentmgr',
		type : 'channelMaster',
		desc : wcm.LANG.CHANNEL_COMMENT || '管理评论',
		title : wcm.LANG.CHANNEL_COMMENT_TITLE || '管理当前栏目下的所有评论',
		rightIndex : 8,
		order : 37,
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
		key : 'move',
		type : 'channels',
		desc : wcm.LANG.CHANNEL_MOVESOME || '移动这些栏目',
		title : wcm.LANG.CHANNEL_MOVESOME_TITLE || '移动这些栏目到指定的栏目',
		rightIndex : 12,
		order : 39,
		fn : pageObjMgr['move']
	});
	reg({
		key : 'increasingpublish',
		type : 'channels',
		desc : wcm.LANG.CHANNEL_ADDPUBSOME || '增量发布这些栏目',
		title : wcm.LANG.CHANNEL_ADDPUBSOME_TITLE ||'发布选中的栏目的首页，并且发布所有可发布状态的文档',
		rightIndex : 17,
		order : 40,
		fn : pageObjMgr['increasingpublish'],
		isVisible : fnIsVisible,
		quickKey : 'P'
	});
	reg({
		key : 'preview',
		type : 'channels',
		desc : wcm.LANG.CHANNEL_PREVIEWSOME || '预览这些栏目',
		title : wcm.LANG.CHANNEL_PREVIEWSOME_SEL || '预览当前选中栏目的发布效果',
		rightIndex : 15,
		order : 41,
		fn : pageObjMgr['preview'],
		isVisible : fnIsVisible,
		quickKey : 'R'
	});
	reg({
		key : 'solopublish',
		type : 'channels',
		desc : wcm.LANG.CHANNEL_SOLOPUBSOME || '仅发布这些栏目的首页',
		title : wcm.LANG.CHANNEL_SOLOPUBSOME_TITLE || '重新生成并发布选中栏目的首页',
		rightIndex : 17,
		order : 42,
		fn : pageObjMgr['solopublish'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'export',
		type : 'channels',
		desc : wcm.LANG.CHANNEL_EXPORTPUBSOME || '导出这些栏目',
		title : wcm.LANG.CHANNEL_EXPORT_SEL || '导出当前选中的栏目（提供下载的zip或者xml文件）',
		rightIndex : 13,
		order : 43,
		fn : pageObjMgr['export']
	});
	reg({
		key : 'seperate',
		type : 'channels',
		desc : wcm.LANG.CHANNEL_SEPERATE || '分隔线',
		rightIndex : -1,
		order : 44,
		fn : pageObjMgr['seperate']
	});
	reg({
		key : 'fullypublish',
		type : 'channels',
		desc : wcm.LANG.CHANNEL_FULLPUBSOME || '完全发布这些栏目',
		title : wcm.LANG.CHANNEL_FULLPUBSOME_TITLE || '重新生成并发布选中栏目下的所有文件',
		rightIndex : 16,
		order : 45,
		fn : pageObjMgr['fullypublish'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'refreshpublish',
		type : 'channels',
		desc : wcm.LANG.CHANNEL_REFRESHPUBSOME || '更新发布这些栏目',
		title : wcm.LANG.CHANNEL_REFRESHPUBSOME_TITLE||'重新生成并发布选中栏目和与其相关栏目的首页',
		rightIndex : 17,
		order : 46,
		fn : pageObjMgr['refreshpublish'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'recallpublish',
		type : 'channels',
		desc : wcm.LANG.CHANNEL_CANCELPUBSOME || '撤销发布这些栏目',
		title : wcm.LANG.CHANNEL_CANCELPUBSOME_TITLE || '撤销选中栏目的发布操作',
		rightIndex : 16,
		order : 47,
		fn : pageObjMgr['recallpublish'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'trash',
		type : 'channels',
		desc : wcm.LANG.CHANNEL_TRASH || '删除栏目',
		title : wcm.LANG.CHANNEL_TRASH_TITLE || '将这个栏目放入回收站',
		rightIndex : 12,
		order : 48,
		fn : pageObjMgr['trash'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'createFromFile',
		type : 'channelHost',
		desc : wcm.LANG.CHANNEL_CREATE_FROMFILE|| '批量创建栏目',
		title : wcm.LANG.CHANNEL_CREATE_FROMFILE_TITLE || '从文件批量创建栏目',
		rightIndex : 11,
		order : 22,
		fn : pageObjMgr['createFromFile']
	});
	reg({
		key : 'createFromFile',
		type : 'websiteHost',
		desc : wcm.LANG.CHANNEL_CREATE_FROMFILE|| '批量创建栏目',
		title : wcm.LANG.CHANNEL_CREATE_FROMFILE_TITLE || '从文件批量创建栏目',
		rightIndex : 11,
		order : 260,
		fn : pageObjMgr['createFromFile']
	});
	
	reg({
		key : 'trash',
		type : 'channel',
		desc : wcm.LANG.CHANNEL_TRASH || '删除栏目',
		title : wcm.LANG.CHANNEL_TRASH_TITLE || '将这个栏目放入回收站',
		rightIndex : 12,
		order : 213,
		fn : pageObjMgr['trash'],
		quickKey : ['Delete', 'ShiftDelete']
	});
})();