//模板操作信息和Mgr定义
Ext.ns('wcm.domain.TemplateMgr');
(function(){
	var m_oMgr = wcm.domain.TemplateMgr={
		serviceId : 'wcm6_template',
		helpers : {},
		getHelper : function(_sServceFlag){
			return new com.trs.web2frame.BasicDataHelper();
		}
	};
	
	Ext.apply(wcm.domain.TemplateMgr, {
		//type here
		'new' : function(event){
			var result = '';
			var context = event.getContext();
			var host = event.getHost();
			if(context) {
				if(host.getType() == null) {
					result += $toQueryStr(context);
				}else{
					result = 'hosttype=' + host.getIntType();
					result += '&hostid=' + host.getId();
				}
			}
			result += '&objectid=0';
			//return result;
			var params = event.getContext();
			if(params && params.isTypeStub) {
				result += '&typestub=1';
			}
			$openMaxWin(WCMConstants.WCM6_PATH + 'template/template_add_edit.jsp?' + result);
		},
//		__getCurrentParams : function(_sIds, _oRawParams, bIsSingle){
		__getCurrentParams : function(event){
			var result = '';
			var context = event.getContext();
			var host = event.getHost();
			var bIsSingle = event.length()<=1;
			if(context) {
				if(host.getType() == null) {
					result += $toQueryStr(context);
				}else{
					result = 'hosttype=' + host.getIntType();
					result += '&hostid=' + host.getId();
				}
			}
			result += '&' + (bIsSingle ? 'objectid' : 'objectids') + '=' + event.getIds();
			return result;

		},

		edit : function(event){
			var sParams = m_oMgr.__getCurrentParams(event);
			/*
			if(_fun && PageContext) {
				PageContext.notifyAfterEdit = _fun;
			}else{//否则,进行重置
				delete PageContext.notifyAfterEdit;
			}*/
			var params = event.getContext();
			if(params && params.isTypeStub) {
				sParams += '&typestub=1';
			}
			$openMaxWin(WCMConstants.WCM6_PATH + 'template/template_add_edit.jsp?' + sParams);
		},

		'import' : function(event){
			var sParams = m_oMgr.__getCurrentParams(event);
			FloatPanel.open(WCMConstants.WCM6_PATH + 'template/template_import.html?' + sParams, wcm.LANG.TEMPLATE_24 || '模板导入', function(){
				CMSObj.afteradd(event)({objType:WCMConstants.OBJ_TYPE_TEMPLATE});
			});
		},
		
		__checkNoRecords : function(oHostInfos){
			if(oHostInfos.RecordNum <= 0) {
				Ext.Msg.$fail(wcm.LANG.TEMPLATE_ALERT || '没有任何要操作的模板!');
				return false;
			}
			return true;
		},

		//__getCurrentPostData : function(_sTempIds, _oHostInfos, _sRenderAllName){
		__getCurrentPostData : function(event, _sRenderAllName){
			//alert(Object.parseSource(event));
			var sTempIds = event.getIds().join(',');
			var host = event.getHost();
			var context = event.getContext();
			var obj = event.getObjs().getAt(0);
			var bIsHost = _sRenderAllName!=null;
			var oPostData = Ext.apply({
				objectids : bIsHost?'':sTempIds,
				hostid : host.getId(),
				hostType : host.getIntType()
			});
			if(bIsHost) {
				oPostData = {};
				oPostData[_sRenderAllName] = true;
				Ext.apply(oPostData,context.get("pagecontext").params);
				Ext.apply(oPostData,{PAGESIZE:-1});
				//oPostData.containsChildren = context.ContainsChildren;
			}
			return oPostData;
		},
		exportAll : function(event, operItem){
			return m_oMgr['export'](event, operItem, 'exportAll');
		},
		'export' : function(event, operItem, _sRenderAllName){
			var oHostInfos = event.getContext();
			if(!m_oMgr.__checkNoRecords(oHostInfos)) {
				return;
			}
			//$beginSimplePB('正在导出模板..',  oHostInfos.isSolo ? 2 : 4);
			//var oPostData = this.__getCurrentPostData(_sTempIds, _oHostInfos, 'exportAll');
			ProcessBar.start(wcm.LANG.TEMPLATE_143 || '执行模板导出..');
			var oPostData = m_oMgr.__getCurrentPostData(event, _sRenderAllName);
			m_oMgr.getHelper().call(m_oMgr.serviceId, 'export', oPostData, true, function(_oXMLHttp, _json){
				//$endSimplePB();
				ProcessBar.close();
				var sFileUrl = _oXMLHttp.responseText;
				var frm = document.getElementById("ifrmDownload");
				if(frm == null) {
					frm = document.createElement('iframe');
					frm.style.height = 0;
					frm.style.width = 0;
					document.body.appendChild(frm);
				}
				
				sFileUrl = WCMConstants.WCM_ROOTPATH + "file/read_file.jsp?DownName=TEMPLATE&FileName=" + sFileUrl;
				frm.src = sFileUrl;
				
			}.bind(this));//*/
		},

		checkAll : function(event, operItem){
			return m_oMgr.check(event, operItem, 'CheckAll');
		},
		//check : function(_sTempIds, _oHostInfos, _bRecheck){
		check : function(event, operItem, _sRenderAllName){
			
			var oHostInfos = event.getContext();
			var sTempIds = event.getIds();
			var bIsSingle = event.length()<=1;
			if(!m_oMgr.__checkNoRecords(oHostInfos)) {
				return;
			}
			if(!confirm(wcm.LANG.TEMPLATE_CONFIRM_0 || '此操作将校验模板的语法和文法细则,可能需要较长时间.确实要进行模板校验吗?')) {
				return;
			}
			ProcessBar.start(wcm.LANG.TEMPLATE_49||'校验模板..');
			//var oPostData = m_oMgr.__getCurrentPostData(sTempIds, oHostInfos, 'CheckAll');
			var oPostData = m_oMgr.__getCurrentPostData(event, _sRenderAllName);
			m_oMgr.getHelper().call(m_oMgr.serviceId, 'check', oPostData, true, function(_trans, _json){
				ProcessBar.close();
				var func = bIsSingle != true ? null : function(){
					CMSObj.afteredit(event)();
				}
				
				Ext.Msg.report(_json, wcm.LANG.TEMPLATE_25 || '模板校验',func, {
					option: function(report, index){
						var nIndex = (event.length() ==1&&!_sRenderAllName) ? _json.REPORTS.REPORT.TITLE.indexOf('~Edit-'):(_json.REPORTS.REPORT[index]?_json.REPORTS.REPORT[index].TITLE.indexOf('~Edit-'):-1);
						if(nIndex>-1){
							return 'edit';
						}
						return 'no_edit';
					},
					renderOption:
						function(sTempId){
							$openMaxWin(WCMConstants.WCM6_PATH + 'template/template_add_edit.jsp?objectid=' + sTempId);
							//var params = event.getContext();
							//m_oMgr.edit(sTempId, params, function(_nSavedTempId){
							//	m_oMgr.check(event, operItem, true);
							//}.bind(this));
						}.bind(this)
				});
			}.bind(this));
		},

		wcag2check : function(event){
			var sTempIds = event.getIds();
			var oHostInfos = event.getContext();
			if(!m_oMgr.__checkNoRecords(oHostInfos)) {
				return;
			}
			//if(_bRecheck != true) {
				//$beginSimplePB('正在校验模板..', _oHostInfos.isSolo ? 2 : 5);
			//}
			var oPostData = {HostType:103,HostId:0,TemplateId:sTempIds};
			var oHelper = new com.trs.web2frame.BasicDataHelper();
			oHelper.call(m_oMgr.serviceId, 'checkWCAG2TemplateText', oPostData, true, function(_trans, _json){
				wcm.CrashBoarder.get('WCAG2-TEMPLATE').show({
					title : wcm.LANG.TEMPLATE_55 || '模板WCAG2校验结果',
					src : WCMConstants.WCM6_PATH + 'template/template_wcag2_check_result2.html',
					width:'650px',
					height:'350px',
					maskable:true,
					params : _json
				});
			}.bind(this));
		},

		preview : function(event){
			window.open(WCMConstants.WCM6_PATH + 'template/template_preview.jsp?TempId=' + event.getIds());
		},
		
		
		//previewFolder : function(_sEmploryId, _nObjectType, _nTempId){
		previewFolder : function(event){
			var _nTempId = event.getIds();
			var host = event.getHost();
			var _sEmploryId = event.objId;
			var _nObjectType = event.objType;
			var nTempType = event.tempType;
			var oParams = {
				objecttype: _nObjectType,
				objectids: _sEmploryId,
				templateid: _nTempId
			}
			var sChnlorSite = _nObjectType == 103 ? (wcm.LANG.TEMPLATE_CHNL||'栏目') : (wcm.LANG.TEMPLATE_SITE||'站点');
			if(nTempType != 1) { //如果不是概览,则进行提示
				Ext.Msg.confirm(String.format(wcm.LANG.TEMPLATE_28||'当前模板并不是概览模板.<br><br>点击[确定]将按<b>默认首页模板</b>预览该{0}.',sChnlorSite), {
					ok : function(){
						var host = event.getHost();
						var nObjectType = host.getIntType();
						var sEmploryId = host.getId();
						var oParams1 = {
							objecttype: _nObjectType,
							objectids: sEmploryId,
							templateid: 0
						}
						m_oMgr.getHelper().call('wcm6_publish', 'preview', oParams1, true, function(_trans, _json){
							if($v(_json, 'urlcount') == 0) {
								try{
									var errInfo = {
										message: _json.DATA[0].EXCEPTION, 
										detail: _json.DATA[0].EXCEPTIONDETAIL
									};
									$render500Err(_trans, errInfo, true);
								}catch(err){
									//TODO logger
									//alert(err.message);
								}
								return;
							}
							//else
							try{
								var sUrl = $v(_json, 'data')[0]['URLS'];
								window.open(sUrl, 'preview_page');		
							}catch(err){
								//TODO logger
								//alert(err.message);
							}
						})
					}
				});
				return;
			}
			m_oMgr.getHelper().call('wcm6_publish', 'preview', oParams, true, function(_trans, _json){
				if($v(_json, 'urlcount') != '1') {
					try{
						var errInfo = {
							message: (_json.DATA.length>0)?_json.DATA[0].EXCEPTION:"", 
							detail: (_json.DATA.length>0)?_json.DATA[0].EXCEPTIONDETAIL:""
						};
						$render500Err(_trans, errInfo, true);
					}catch(err){
						//TODO logger
						//alert(err.message);
					}
					return;
				}
				//else
				try{
					var sUrl = $v(_json, 'data')[0]['URLS'];
					window.open(sUrl, 'preview_page');		
				}catch(err){
					//TODO logger
					//alert(err.message);
				}
			})

		}, 
		'delete' : function(event){
			var sTempIds = event.getIds() + '';
			var oPageParams = event.getContext();
			var nCount = (sTempIds.indexOf(',') == -1) ? 1 : sTempIds.split(',').length;
			var sHint = (nCount==1)?'':' '+nCount+' ';
			var oPostData = {
				"ObjectIds": sTempIds
			};
			if (confirm(String.format(wcm.LANG.TEMPLATE_30||('确实要将这{0}个模板删除吗?'),sHint))){
				var fun = function(){
					m_oMgr.getHelper().call(m_oMgr.serviceId,'delete', oPostData, true, 
						function(){
							event.getObjs().afterdelete();
						}
					);
				}
				m_oMgr.getHelper().call('wcm61_template','existNestedTemplate', oPostData, true, 
					function(_transport,_json){
						if(_json.REPORTS.REPORT){
							Ext.Msg.report(_json,"含有嵌套模板",fun);
						}else{
							fun();
						}
					}
				);
			}
		},
		synchAll : function(event, operItem){
			return m_oMgr.synch(event, operItem, 'RedistributeAll');
		},
		//synch : function(_sTempIds, _oHostInfos){
		synch : function(event, operItem, _sRenderAllName){
			var oHostInfos = event.getContext();
			if(!m_oMgr.__checkNoRecords(oHostInfos)) {
				return;
			}
			if(_sRenderAllName && !confirm(wcm.LANG.TEMPLATE_CONFIRM_1 || '此操作将重新分发模板附件,可能需要较长时间.确实要同步附件吗?')) {
				return;
			}
			ProcessBar.start(wcm.LANG.TEMPLATE_50||'同步模板附件');
			//var oPostData = this.__getCurrentPostData(_sTempIds, oHostInfos, 'RedistributeAll');
			var oPostData = m_oMgr.__getCurrentPostData(event, _sRenderAllName);
			var dStart = new Date().getTime();
			m_oMgr.getHelper().call(m_oMgr.serviceId, 'redistributeAppendixes', oPostData, true, function(){
				var sInterval = (new Date().getTime() - dStart)/1000;
				ProcessBar.close();
				Ext.Msg.$success(String.format("成功同步了模板附件!实际耗时<br><b> {0}</b>秒.",sInterval)); 
			}.bind(m_oMgr));
		},
		
		showArrangeEmployment : function(event){
			var host = event.getHost();
			var sRight = host.right;
			var nHostType = event.getObj().getPropertyAsInt('folderType');
			var nHostId = event.getObj().getPropertyAsInt('folderId');
			var nTempId = event.getIds();
			var nTempType = event.tempType;
			//拥有模板查看权限,即可进行分配操作
			if(!wcm.AuthServer.hasRight(sRight, 24)) {
				return;
			}
			if(!(nHostType > 0) || !(nHostId > 0)) {
				return;
			}
			var url = WCMConstants.WCM6_PATH + 'template/channel_select.html?TemplateId=' + nTempId + '&TemplateType=' + nTempType + '&HostType=' + nHostType + '&HostId=' + nHostId;
			//需要同时传入是否对当前站点是否可以修改(设置模板)的权限
			if(nHostType == '103' && wcm.AuthServer.hasRight(sRight, 9)) {
				url += '&SiteModify=1';
			}
			FloatPanel.open(url, wcm.LANG.EMPOLOYTO||'将模板分配给栏目或站点使用',CMSObj.afteradd(event));	
		}
		

	});
})();
(function(){
	var pageObjMgr = wcm.domain.TemplateMgr;
	var reg = wcm.SysOpers.register;
	var fnIsVisible = function(event){
		var host = event.getHost();
		var context = event.getContext();
		if(Ext.isTrue(host.isVirtual) && host.getPropertyAsInt("chnlType", 0) != 0){
			return false;
		}
		return true;
	};
	reg({
		key : 'edit',
		type : 'template',
		desc : wcm.LANG.TEMPLATE_0 || '修改这个模板',
		title:'修改这个模板...',
		rightIndex : 23,
		order : 1,
		fn : pageObjMgr['edit'],
		quickKey : 'M'
	});
	reg({
		key : 'preview',
		type : 'template',
		desc : wcm.LANG.TEMPLATE_2 || '预览这个模板',
		title:'预览这个模板...',
		rightIndex : 24,
		order : 2,
		fn : pageObjMgr['preview'],
		quickKey : 'R'
	});
	reg({
		key : 'export',
		type : 'template',
		desc : wcm.LANG.TEMPLATE_4 || '导出这个模板',
		title:'导出这个模板...',
		rightIndex : 24,
		order : 3,
		fn : pageObjMgr['export'],
		quickKey : 'X'
	});
	reg({
		key : 'check',
		type : 'template',
		desc : wcm.LANG.TEMPLATE_6 || '校验这个模板',
		title:'校验这个模板...',
		rightIndex : 25,
		order : 4,
		fn : pageObjMgr['check']
	});
	reg({
		key : 'wcag2check',
		type : 'template',
		desc : wcm.LANG.TEMPLATE_53 || 'WCAG2校验',
		title:'WCAG2校验...',
		rightIndex : 25,
		order : 6.5,
		fn : pageObjMgr['wcag2check']
	});
	reg({
		key : 'delete',
		type : 'template',
		desc : wcm.LANG.TEMPLATE_8 || '删除这个模板',
		title:'删除这个模板...',
		rightIndex : 22,
		order : 5,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'synch',
		type : 'template',
		desc : wcm.LANG.TEMPLATE_10 || '同步模板附件',
		title:'同步模板附件...',
		rightIndex : 28,
		order : 6,
		fn : pageObjMgr['synch']
	});
	reg({
		key : 'new',
		type : 'templateInChannel',
		desc : wcm.LANG.TEMPLATE_12 || '新建一个模板',
		title:'新建一个模板...',
		rightIndex : 21,
		order : 7,
		fn : pageObjMgr['new'],
		isVisible : fnIsVisible,
		quickKey : 'N'
	});
	reg({
		key : 'import',
		type : 'templateInChannel',
		desc : wcm.LANG.TEMPLATE_13 || '导入模板',
		title:'导入模板',
		rightIndex : 21,
		order : 8,
		fn : pageObjMgr['import'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'export',
		type : 'templateInChannel',
		desc : wcm.LANG.TEMPLATE_15 || '导出所有模板',
		title:'导出所有模板...',
		rightIndex : 24,
		order : 9,
		fn : pageObjMgr['exportAll']
	});
	reg({
		key : 'check',
		type : 'templateInChannel',
		desc : wcm.LANG.TEMPLATE_17 || '校验所有模板',
		title:'校验所有模板...',
		rightIndex : 25,
		order : 10,
		fn : pageObjMgr['checkAll']
	});
	reg({
		key : 'synch',
		type : 'templateInChannel',
		desc : wcm.LANG.TEMPLATE_19 || '同步所有模板附件',
		title : wcm.LANG.TEMPLATE_20 || '同步当前所列模板的附件',
		rightIndex : 28,
		order : 11,
		fn : pageObjMgr['synchAll']
	});
	reg({
		key : 'new',
		type : 'templateInSite',
		desc : wcm.LANG.TEMPLATE_12 || '新建一个模板',
		title:'新建一个模板...',
		rightIndex : 21,
		order : 12,
		fn : pageObjMgr['new'],
		quickKey : 'N'
	});
	reg({
		key : 'import',
		type : 'templateInSite',
		desc : wcm.LANG.TEMPLATE_13 || '导入模板',
		title:'导入模板...',
		rightIndex : 21,
		order : 13,
		fn : pageObjMgr['import']
	});
	reg({
		key : 'export',
		type : 'templateInSite',
		desc : wcm.LANG.TEMPLATE_15 || '导出所有模板',
		title:'导出所有模板...',
		rightIndex : 24,
		order : 14,
		fn : pageObjMgr['exportAll']
	});
	reg({
		key : 'check',
		type : 'templateInSite',
		desc : wcm.LANG.TEMPLATE_17 || '校验所有模板',
		title:'校验所有模板...',
		rightIndex : 25,
		order : 15,
		fn : pageObjMgr['checkAll']
	});
	reg({
		key : 'synch',
		type : 'templateInSite',
		desc : wcm.LANG.TEMPLATE_19 || '同步所有模板附件',
		title : wcm.LANG.TEMPLATE_20 || '同步当前所列模板的附件',
		rightIndex : 28,
		order : 16,
		fn : pageObjMgr['synchAll']
	});
	reg({
		key : 'export',
		type : 'templates',
		desc : wcm.LANG.TEMPLATE_22 || '导出这些模板',
		title:'导出这些模板...',
		rightIndex : 24,
		order : 17,
		fn : pageObjMgr['export'],
		quickKey : 'X'
	});
	reg({
		key : 'check',
		type : 'templates',
		desc : wcm.LANG.TEMPLATE_23 || '校验这些模板',
		title:'校验这些模板...',
		rightIndex : 25,
		order : 18,
		fn : pageObjMgr['check']
	});
	reg({
		key : 'delete',
		type : 'templates',
		desc : wcm.LANG.TEMPLATE_21 || '删除这些模板',
		title:'删除这些模板...',
		rightIndex : 22,
		order : 19,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'synch',
		type : 'templates',
		desc : wcm.LANG.TEMPLATE_10 || '同步模板附件',
		title:'同步模板附件...',
		rightIndex : 28,
		order : 20,
		fn : pageObjMgr['synch']
	});
	reg({
		key : 'designvisualtemplate',
		type : 'template',
		desc : '设计可视化模板',
		title:'设计可视化模板...',
		rightIndex : 23,
		order : 1.5,
		fn : function(){
			pageObjMgr['designVisualTemplate'].apply(this, arguments);
		},
		isVisible : function(event){
			var obj = event.getObjs().getAt(0);
			if(obj.getPropertyAsString('VisualAble') != 'true'){
				return false;
			}
			return true;
		}
	});
})();