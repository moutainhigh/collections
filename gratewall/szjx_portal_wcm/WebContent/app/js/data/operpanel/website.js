//站点操作面板信息
Ext.ns('websitehelper.PageContext');
(function(){
	var sName = wcm.LANG['WEBSITE'] || '站点';
	wcm.PageOper.registerPanels({
		WebSiteRoot : {
			isHost : true,
			title : wcm.LANG.WEBSITE_31 || '库站点操作任务',
			displayNum : 5,
			detail : function(cmsobjs, info){
				var siteType = cmsobjs.getIds()[0] || 0;
				var siteTypeNames = [wcm.LANG.WEBSITE_32 || '文字库', wcm.LANG.WEBSITE_33 || '图片库', wcm.LANG.WEBSITE_34 || '视频库', '', wcm.LANG.WEBSITE_35 || '资源库'];
				return siteTypeNames[siteType];
			}
		},
		websiteInRoot : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INROOT'] || '库{0}操作任务', sName),
			displayNum : 5
		},
		websiteInSite : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INSITE'] || '站点{0}操作任务', sName),
			displayNum : 5
		},
		websiteInChannel : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INCHANNEL'] || '栏目{0}操作任务', sName),
			displayNum : 5
		},
		website : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 7,
			url : '?serviceid=wcm61_website&methodname=jFindbyid',
			detailMore : function(wcmEvent, opers, curDef){
				wcm.domain.WebSiteMgr.edit(wcmEvent);
			}
		},
		websites : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 7
		}
	});
})();

ValidationHelper.bindValidations([
	{
		renderTo : 'SiteDesc',
		type :'string',
		required :'',
		max_len :'200',
		desc :wcm.LANG.WEBSITE_36 || '站点显示名称'
	},
	{
		renderTo : 'SiteName',
		type:'string',
		required:'',
		max_len:'50',
		desc:wcm.LANG.WEBSITE_37 || '站点唯一标识',
		methods : PageContext.validExistProperty()		
	},
	{
		renderTo : 'RootDomain',
		type :'url',
		required :'',
		max_len :'200',
		desc :wcm.LANG.WEBSITE_38 || '站点HTTP'
	}
]);

//findbyid页面的选择模板
Ext.apply(websitehelper.PageContext, {
	selectTemplate : function(nTempId, nTempType){
		var event = PageContext.event;
		var website = event.getObj();
		var sTempId = nTempId + '';
		var oParams = {
			objecttype: website.getIntType(),
			objectid: website.getId(),
			templatetype: nTempType,
			templateIds: sTempId
		};
		wcm.CrashBoarder.get('TEMPLATE_SELECT_SOLO').show({
			title : wcm.LANG.TEMPLATE_SELECT||'选择模板',
			src : WCMConstants.WCM6_PATH + 'template/template_select_list.html',
			width:'510px',
			height:'240px',
			maskable:true,
			params : oParams,
			callback : function(_args){
				var oHelper = new com.trs.web2frame.BasicDataHelper();
				var oPostData = {
					objectid: oParams.objectid, 
					objecttype: oParams.objecttype,
					templateid: _args.selectedIds[0] || 0,
					templatetype: oParams.templatetype
				};
				if(oParams.templateids == 0 && oPostData.templateid == 0)
					return;
				oHelper.call('wcm6_template', 'setDefaultTemplate', oPostData, true, function(){
					CMSObj.afteredit(event)();
					this.close();
				}.bind(this));
			}
		
		});
	},
	
	editTemplate : function(_nTempId, _nTempType, bVisual){
		_nTempId = parseInt(_nTempId);
		var event = PageContext.event;
		var website = event.getObjs().getAt(0) || event.getHost();
		if(_nTempId > 0){
			var newParams = {
				objectid : _nTempId,
				tempType : _nTempType,
				callback : 'websitehelper.PageContext.notifyAddEditTemplate',
				hostid :website.getId(),
				hosttype : website.getIntType(),
				typestub : 1
			}
			if(bVisual){
				window.open('../special/design.jsp?templateId=' + _nTempId + 
					 '&HostType=' + website.getIntType() + '&HostId=' + website.getId());
			}else{
				$openMaxWin(WCMConstants.WCM6_PATH + 'template/template_add_edit.jsp?' + $toQueryStr(newParams));	
			}
		}else{
			var editParams = {
				objectid : 0,
				tempType : _nTempType,
				callback : 'websitehelper.PageContext.notifyAddEditTemplate',
				hostid :website.getId(),
				hosttype : website.getIntType(),
				typestub : 1
			}
			$openMaxWin(WCMConstants.WCM6_PATH + 'template/template_add_edit.jsp?' + $toQueryStr(editParams));
		}
		
	},

	notifyAddEditTemplate : function(args){
		var event = PageContext.event;
		var webSiteId = args.hostId;
		var siteType = args.hostType;
		var tempId = args.objectId;
		var tempType = args.tempType;
		var savedTempId = args.saveTempId;
		if(tempId > 0) {
			CMSObj.afteredit(event)({objType:WCMConstants.OBJ_TYPE_WEBSITE, objId : webSiteId});
			return;
		}
		//else 
		var oPostData = {
			objectid: webSiteId, 
			objecttype: siteType,
			templateid: savedTempId,
			templatetype: tempType
		};
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		oHelper.call('wcm6_template', 'setDefaultTemplate', oPostData, true, function(){
			CMSObj.afteredit(event)({objType:WCMConstants.OBJ_TYPE_WEBSITE, objId : webSiteId});				
		});
	}

});
