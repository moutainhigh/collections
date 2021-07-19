//栏目操作面板信息
Ext.ns('channelhelper.PageContext');
(function(){
	var sName = wcm.LANG['CHANNEL'] || '栏目';
	wcm.PageOper.registerPanels({
		websiteHost : {
			isHost : true,
			title : wcm.LANG.CHANNEL_55 || '站点栏目操作任务',
			displayNum : 5
		},
		channelHost : {
			isHost : true,
			title : wcm.LANG.CHANNEL_56 || '子栏目操作任务',
			displayNum : 5
		},
		channelMaster : {
			title : wcm.LANG.CHANNEL_57 || '栏目操作任务',
			displayNum : 7,
			url : '?serviceid=wcm61_channel&methodname=jFindbyid',
			detailMore : function(wcmEvent, opers, curDef){
				wcm.domain.ChannelMgr.edit(wcmEvent);
			}
		},
		/*
		channelInRoot : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INROOT'] || '库{0}操作任务', sName),
			displayNum : 5
		},
		channelInSite : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INSITE'] || '站点{0}操作任务', sName),
			displayNum : 5
		},
		channelInChannel : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INCHANNEL'] || '栏目{0}操作任务', sName),
			displayNum : 5
		},*/
		channel : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 7,
			url : '?serviceid=wcm61_channel&methodname=jFindbyid',
			detailMore : function(wcmEvent, opers, curDef){
				wcm.domain.ChannelMgr.edit(wcmEvent);
			}
		},
		channels : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 7
		}
	});
})();

ValidationHelper.bindValidations([
	{
		renderTo : 'ChnlDesc',
		type :'string',
		required :'',
		max_len :'200',
		desc :wcm.LANG.CHANNEL_58 || '栏目显示名称'
	},
	{
		renderTo : 'ChnlName',
		type:'string',
		required:'',
		max_len:'50',
		desc:wcm.LANG.CHANNEL_59 || '栏目唯一标识',
		methods : function(){
			var element = this.field;
			var oHelper = new com.trs.web2frame.BasicDataHelper();
			var objId = (PageContext.event.getIds().length!=0)?PageContext.event.getIds():PageContext.event.getHost().getId();
			var oPostData = {
				objectId : objId,
				ChannelName : element.value
			};
			oHelper.call(
				'wcm6_channel',
				'existsSimilarName',
				oPostData,  true,
				function(transport, json){
					var result = $v(json,'result');
					var sDesc = result == 'true' ? ((wcm.LANG.CHANNEL_59 || '栏目唯一标识') + (wcm.LANG.SYSTEM_NOTUNIQUE||"不唯一")) : null;
					ValidatorHelper.execCallBack(element, sDesc);
				}
			)
		}			
	},
	{
		renderTo : 'LinkUrl',
		type :'url',
		required :'',
		max_len :'200',
		desc :wcm.LANG.CHANNEL_61 || '栏目链接'
	}
]);

	Ext.apply(channelhelper.PageContext, {
		selectTemplate : function(nTempId, nTempType){
			var event = PageContext.event;
			var channel = event.getObjs().getAt(0) || event.getHost();
			var sTempId = nTempId + '';
			var oParams = {
				objecttype: channel.getIntType(),
				objectid: channel.getId(),
				templatetype: nTempType,
				templateIds: sTempId
			};
			var win = $MsgCenter.getActualTop() || window;
			win.wcm.CrashBoarder.get('TEMPLATE_SELECT_SOLO').show({
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

		editTemplate : function(_nTempId, _nTempType, bVisualType){
			_nTempId = parseInt(_nTempId);
			var event = PageContext.event;
			var channel = event.getObjs().getAt(0) || event.getHost();
			if(_nTempId > 0){
				var newParams = {
					objectid : _nTempId,
					tempType : _nTempType,
					callback : 'channelhelper.PageContext.notifyAddEditTemplate',
					hostid :channel.getId(),
					hosttype : channel.getIntType(),
					typestub : 1
				}
				if(bVisualType){
					window.open('../special/design.jsp?templateId=' + _nTempId + 
					 '&HostType=' + channel.getIntType() + '&HostId=' + channel.getId());
				}else{
					$openMaxWin(WCMConstants.WCM6_PATH + 'template/template_add_edit.jsp?' + $toQueryStr(newParams));
				}
			}else{
				var editParams = {
					objectid : 0,
					tempType : _nTempType,
					callback : 'channelhelper.PageContext.notifyAddEditTemplate',
					hostid :channel.getId(),
					hosttype : channel.getIntType(),
					typestub : 1
				}
				$openMaxWin(WCMConstants.WCM6_PATH + 'template/template_add_edit.jsp?' + $toQueryStr(editParams));
			}
			
		},

		notifyAddEditTemplate : function(args){
			var event = PageContext.event;
			var chnlId = args.hostId;
			var chnlType = args.hostType;
			var tempId = args.objectId;
			var tempType = args.tempType;
			var savedTempId = args.saveTempId;
			if(tempId > 0) {
				CMSObj.afteredit(event)({objType:WCMConstants.OBJ_TYPE_CHANNEL, objId : chnlId});				
				return;
			}
			//else 
			var oPostData = {
				objectid: chnlId, 
				objecttype: chnlType,
				templateid: savedTempId,
				templatetype: tempType
			};
			var oHelper = new com.trs.web2frame.BasicDataHelper();
			oHelper.call('wcm6_template', 'setDefaultTemplate', oPostData, true, function(){
				CMSObj.afteredit(event)({objType:WCMConstants.OBJ_TYPE_CHANNEL, objId : chnlId});				
			});
		}
	});