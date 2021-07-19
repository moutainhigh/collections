//文档操作信息和Mgr定义
Ext.ns('wcm.domain.DocumentMgr', 'wcm.domain.ChnlDocMgr');
(function(){
	var m_nDocumentObjType = 605;
	function parseHost(host){
		if(host.getType()==WCMConstants.OBJ_TYPE_CHANNEL){
			return {ChannelId:host.getId(),SiteId:0};
		}
		if(host.getType()==WCMConstants.OBJ_TYPE_WEBSITE){
			return {SiteId:host.getId(),ChannelId:0};
		}
		return {};
	}
	function openEditor(params){
		if(params.DocumentId=='0'&&params.SiteId!=0){
			FloatPanel.open(WCMConstants.WCM6_PATH + 'document/document_siteadd_step1.html?' 
				+ $toQueryStr(params), wcm.LANG.DOCUMENT_PROCESS_81 || '选择要新建文档的栏目', 400, 350);
			return ;
		}
		var iWidth = window.screen.availWidth - 12;
		var iHeight = window.screen.availHeight - 30;
		var sFeature = 'location=no,resizable=yes,menubar=no,scrollbars=yes,status=no,titlebar=no,toolbar=no,top=0,left=0,border=0,width='+iWidth+',height='+iHeight;
		window.open(WCMConstants.WCM6_PATH + "document/document_addedit.jsp?" + $toQueryStr(params), "_blank" , sFeature);
	}
	function getHelper(_sServceFlag){
		return new com.trs.web2frame.BasicDataHelper();
	}
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
	//改用简版后,做了单独的支持.
	function $openCenterWin(_sUrl, _sName, _width, _height, _sFeature){
		if(!_width || !_height){
			$openCentralWin(_sUrl, _sName);
			return;
		}
		var _WIN_WIDTH = window.screen.availWidth;
		var _WIN_HEIGHT = window.screen.availHeight;
		var l = (_WIN_WIDTH - _width) / 2;
		var t = (_WIN_HEIGHT - _height) / 2;
		sFeature = "left="+l + ",top=" + t +",width=" 
			+ _width + ",height=" + _height + "," + _sFeature;
		var sName	= _sName || "";
		sName = sName.replace(/[^0-9a-zA-Z_]/g,'_');
		var oWin = window.open(_sUrl, sName, sFeature);
		if(oWin) oWin.focus();
	}
	function openQuickNew(result){
		var oParams = {
			DocumentId : result.DocumentId,
			ChannelId : result.ChannelId,
			SiteId : result.SiteId
		}
		var sChannelNames = result.ChannelName;
		setTimeout(function(){
			FloatPanel.open(WCMConstants.WCM6_PATH +
				'document/document_quicknew.html?' + $toQueryStr(oParams),
				String.format(wcm.LANG.DOCUMENT_PROCESS_190 || ('文档-智能创建Office文档到栏目[{0}]'),sChannelNames[0]));
		}, 10);
		return false;
	}
	function openImport(event,result){
		var oParams = {
			DocumentId : result.DocumentId,
			ChannelId : result.ChannelId,
			SiteId : result.SiteId
		}
		var sChannelNames = result.ChannelName;
		setTimeout(function(){
			FloatPanel.open(WCMConstants.WCM6_PATH +'document/document_import.jsp?' + $toQueryStr(oParams), wcm.LANG.DOCUMENT_PROCESS_89 || '文档导入',function(objId){
				CMSObj['afteradd'](event)({objType:WCMConstants.OBJ_TYPE_CHNLDOC, objId:objId});
			});
		}, 10);
		return false;
	}
	function openImportOffice(result){
		var oParams = {
			DocumentId : result.DocumentId,
			ChannelId : result.ChannelId,
			SiteId : result.SiteId
		}
		var sChannelNames = result.ChannelName;
		setTimeout(function(){
			FloatPanel.open(WCMConstants.WCM6_PATH +
					'document/document_importoffice.jsp?' + $toQueryStr(oParams), 
					String.format(wcm.LANG.DOCUMENT_PROCESS_84 || ('文档-批量导入Office文档到栏目[{0}]'),sChannelNames[0]));
		}, 10);
		return false;
	}
	Ext.apply(wcm.domain.DocumentMgr, {
		'new' : function(event){
			var oParams = Ext.apply({
				DocumentId : 0,
				FromEditor : 1
			}, parseHost(event.getHost()));
			openEditor(oParams);
		},
		edit : function(event){
			var host = event.getHost();
			var oParams = {
				ChnlDocId : event.getObj().getId(),
				DocumentId : event.getObjs().getDocIds(),
				ChannelId :event.getObj().getPropertyAsInt('currchnlid'),
				SiteId :host.getType()==WCMConstants.OBJ_TYPE_WEBSITE?host.getId():0,
				FromEditor : 1
			};
			openEditor(oParams);
		},
		logo : function(event){
			var oParams = {
				HostId : event.getObjs().getDocIds(),
				HostType :605
			};
			$openCenterWin(WCMConstants.WCM6_PATH + 
				'logo/logo_list.jsp?' + $toQueryStr(oParams),"document_logo", 900, 600, "resizable=yes");
		},
		quicknew : function(event){
			var oParams = parseHost(event.getHost());
			if(oParams.SiteId!=0){
				FloatPanel.open(WCMConstants.WCM6_PATH +
					'document/document_siteimportoffice_step1.html?' + $toQueryStr(oParams),
					wcm.LANG.DOCUMENT_PROCESS_85 || '选择要智能创建文档的栏目',openImportOffice );
				return;
			}
			FloatPanel.open(WCMConstants.WCM6_PATH +
				'document/document_importoffice.jsp?' + $toQueryStr(oParams),
				wcm.LANG.DOCUMENT_PROCESS_107 || '智能创建文档');
		},
		/** 智能创建文档已经包含了批量导入Office文档
		importoffice : function(event){
			var oParams = parseHost(event.getHost());
			if(oParams.SiteId!=0){
				FloatPanel.open(WCMConstants.WCM6_PATH +
					'document/document_siteimportoffice_step1.html?' + $toQueryStr(oParams), 
					wcm.LANG.DOCUMENT_PROCESS_86 || '选择要批量导入Office文档的栏目',openImportOffice );
				return;
			}
			FloatPanel.open(WCMConstants.WCM6_PATH +
					'document/document_importoffice.jsp?' + $toQueryStr(oParams), 
					wcm.LANG.DOCUMENT_PROCESS_87 || '文档-批量导入Office文档');
		},**/
		"import" : function(event){
			var oParams = parseHost(event.getHost());
			if(oParams.SiteId!=0){
				FloatPanel.open(WCMConstants.WCM6_PATH +
					'document/document_siteimport_step1.html?' + $toQueryStr(oParams),
					wcm.LANG.DOCUMENT_PROCESS_88 || '选择文档导入的目标栏目',openImport.bind(this,event));
				return;
			}
			FloatPanel.open(WCMConstants.WCM6_PATH +
					'document/document_import.jsp?' + $toQueryStr(oParams),
					wcm.LANG.DOCUMENT_PROCESS_89 || '文档导入',function(objId){
				CMSObj['afteradd'](event)({objType:WCMConstants.OBJ_TYPE_CHNLDOC, objId:objId});
			});
		},
		exportall : function(event){
			Ext.Msg.confirm(wcm.LANG.DOCUMENT_PROCESS_90 || '此操作可能需要较长时间.确实要导出所有文档吗?',{
				yes : function(){
					var oPostData = Ext.apply({
						ExportAll: 1
					}, parseHost(event.getHost()));
					var context = event.getContext();
					var dialogArguments = Ext.apply({}, context.get("pagecontext").params);
					Ext.apply(dialogArguments,{PAGESIZE:500});
					getHelper().call(m_sServiceId, "query", context.get("pagecontext").params, true,
						function(_transport,_json){
							Ext.apply(oPostData,{Count:_json.VIEWDOCUMENTS.NUM});
							FloatPanel.open(
								WCMConstants.WCM6_PATH + 'document/document_export.jsp?' + $toQueryStr(oPostData),
								wcm.LANG.DOCUMENT_PROCESS_91 || '文档-导出所有文档',
								null,
								dialogArguments
							);
						}
					);
				}
			});	
		},
		setright : function(event){
			$openCenterWin(WCMConstants.WCM6_PATH + "auth/right_set.jsp?ObjType=" +
				m_nDocumentObjType + "&ObjId=" + event.getObjs().getDocIds(),
				"document_right_set", 900, 600, "resizable=yes");
		}
	});
	function doOptionsAfterDisplayInfo(_params, _fDoAfterDisp){
		var DIALOG_DOCUMENT_INFO = 'document_info_dialog';
		wcm.CrashBoarder.get('DIALOG_DOCUMENT_INFO').show({
			title : wcm.LANG.DOCUMENT_PROCESS_208 || '系统提示信息',
			src : WCMConstants.WCM6_PATH + 'docrecycle/document_info.html',
			width:'500px',
			height:'205px',
			maskable:true,
			params :  _params,
			callback : _fDoAfterDisp
		});
	}
	function preparePostData(event){
		return {
			'documentids' : event.getObjs().getDocIds(),
			'objectids' : event.getIds(),
			'channelids' : event.getObjs().getPropertyAsInt('currchnlid')
		};
	}
	var m_sServiceId = 'wcm61_viewdocument';
	var m_nObjType = 600;
	function preview(_sDocIds, _nObjectType, _oExtraParams,_serviceid){
		wcm.domain.PublishAndPreviewMgr.preview(_sDocIds, _nObjectType, _oExtraParams,_serviceid);
	}
	function publish(objectids, _sMethodName, event){
		_sMethodName = _sMethodName || 'publish';
		var oPostData = {'ObjectIds' : objectids};
		getHelper().call(m_sServiceId, _sMethodName, oPostData, true,
			function(_transport,_json){
				wcm.domain.PublishAndPreviewMgr.doAfterPublish(oPostData, _sMethodName, _transport, _json);
				setTimeout(function(){
					CMSObj.afteredit(event)();
				},3000);
			}
		);
	}
	wcm.domain.ChnlDocMgr = Ext.applyIf({
		changestatus : function(event){
			var oPageParams = event.getContext();
			var host = event.getHost();
			var hostid = host.getId();
			var sId = event.getIds().join();
			var docid = event.getObjs().getAt(0).getPropertyAsInt("docId", 0);
			Object.extend(oPageParams,{
				"DocumentId":docid,
				"ObjectIds":sId,
				"IsPhoto":false,
				'ChannelIds':(oPageParams,host.getType()=="website"?0:hostid)});
			FloatPanel.open(WCMConstants.WCM6_PATH +'document/change_status.jsp?' + $toQueryStr(oPageParams), wcm.LANG.DOCUMENT_PROCESS_92 || '文档-改变状态', CMSObj.afteredit(event));
		},
		changelevel : function(event){
			var oPageParams = event.getContext();
			var host = event.getHost();
			var hostid = host.getId();
			Object.extend(oPageParams,{
				"ObjectIds":event.getObjs().getDocIds(),
				"IsPhoto":false});
			FloatPanel.open(WCMConstants.WCM6_PATH +'document/change_doclevel.jsp?' + $toQueryStr(oPageParams), '文档-改变密级', CMSObj.afteredit(event));
		},
		preview : function(event){
			//$publishMgr.serviceId = this.serviceId;
			var sIds = event.getIds().join();
			var host = event.getHost();
			var oParams = {
				FolderId : host.getId() || 0,
				FolderType : host.getIntType()
			};
			preview(sIds,m_nObjType,oParams,m_sServiceId);
		},
		basicpublish : function(event){
			publish(event.getIds(), 'basicPublish', event);
		},
		detailpublish : function(event){
			publish(event.getIds(), 'detailPublish', event);
		},
		directpublish : function(event){
			var oPostData = {'ObjectIds' : event.getObjs().getDocIds(), 'ObjectType' : 605 };
			getHelper().call("wcm6_publish", "directPublish", oPostData, true,
				function(_transport,_json){
					wcm.domain.PublishAndPreviewMgr.doAfterPublish(oPostData, "directPublish", _transport, _json);
					setTimeout(function(){
						CMSObj.afteredit(event)();
					},3000);
				}
			);
		},
		recallpublish : function(event){
			var sHtml = String.format("确定要{0}撤销发布{1}所选文档么？将{2}不可逆转！{3}",'<font color=\'red\' style=\'font-size:14px;\'>','</font>','<font color=\'red\' style=\'font-size:14px;\'>','</font>');
			Ext.Msg.confirm(sHtml,{
				yes : function(){
					publish(event.getIds(), 'recallPublish', event);	
				}
			})	
		},
		directRecallpublish : function(event){
			var sHtml = String.format("确定要{0}撤销发布{1}所选文档及其所有引用文档么？将{2}不可逆转！{3}",'<font color=\'red\' style=\'font-size:14px;\'>','</font>','<font color=\'red\' style=\'font-size:14px;\'>','</font>！');
			Ext.Msg.confirm(sHtml,{
				yes : function(){
					publish(event.getIds(), 'recallpublishall', event);	
				}
			})	
		},

		copyall : function(event,operItem){
			var sHtml = String.format("确定要{0}复制所有{1}文档么？将{2}不可逆转！{3}",'<font color=\'red\' style=\'font-size:14px;\'>','</font>','<font color=\'red\' style=\'font-size:14px;\'>','</font>');
			Ext.Msg.confirm(sHtml,{
				yes : function(){
					wcm.domain.ChnlDocMgr.copy(event, operItem, true);	
				}
			})			
		},
		copyEntity : function(event,bCopyall,oPostData){
			ProcessBar.start(wcm.LANG.DOCUMENT_PROCESS_269 ||'执行复制文档..');
			var oHelper = new com.trs.web2frame.BasicDataHelper();
			var func = function(){
				FloatPanel.close();
				CMSObj.afteredit(event)();
			}
			oHelper.Call('wcm6_viewdocument',(bCopyall==true)?'copyAll':'copy',oPostData,true,
				function(_transport,_json){
					ProcessBar.close();
					Ext.Msg.report(_json,(wcm.LANG.DOCUMENT_PROCESS_36 ||'文档复制结果'), func);
					FloatPanel.hide();
				},
				function(_transport,_json){
					$render500Err(_transport,_json);
					FloatPanel.close();
				}
			);
		},
		copy : function(event,operItem,bCopyall){
			var pageContext = event.getContext();
			var sObjectids = event.getIds();
			var host = event.getHost();
			var hostId = host.getId();
			var hostType = host.getIntType();
			var hostChnlId = hostType == 101 ? hostId : 0;
			sObjectids = (sObjectids.length!=0)?sObjectids:'0';
			var channelids = event.getObjs().getPropertyAsString("currchnlid", 0);
			if(!Ext.isArray(channelids)) channelids = [channelids];
			var channelType = event.getObjs().getPropertyAsString("channelType", 0);
			if(!Ext.isArray(channelType)) channelType = [channelType];
			var bIsOneChannel = true;
			var tmpChannelid = channelids[0];
			for(var i=1,n=channelids.length; i<n; i++){
				if(tmpChannelid!=channelids[i]){
					bIsOneChannel = false;
					break;
				}
			}
			var nExcludeInfoView = 1;
			for(index=0; index<channelType.length; index++){
				if(channelType[index]==13){
					nExcludeInfoView = 0;
					break;
				}
			}
			var args = {
				IsRadio : 0,
				ExcludeTop : 1,
				ExcludeLink : 1,
				ExcludeVirtual : 1,
				ExcludeInfoView : nExcludeInfoView,
				ExcludeOnlySearch : 1,
				ShowOneType : 0,
				MultiSites : 1,
				SiteTypes : '0,4',
				MultiSiteType : 0,
				SelectedChannelIds : channelids.join() || hostChnlId,
				NotSelect : 1,
				RightIndex : 31,
				canEmpty : true
			};
			if(bIsOneChannel){
				Ext.apply(args, {
					CurrChannelId : hostChnlId || tmpChannelid || 0,
					ExcludeSelf : 0
				});
			}
			getHelper().call(m_sServiceId, "query", pageContext.get("pagecontext").params, true,
				function(_transport,_json){
				var itemCount = 0;
				if(bCopyall){
					itemCount = _json.VIEWDOCUMENTS.NUM;
				}
				FloatPanel.open(
					WCMConstants.WCM6_PATH + 'include/channel_select.html?ItemCount=' + itemCount + '&close=1',
					wcm.LANG.DOCUMENT_PROCESS_93 || '文档-文档复制到...',
					function(selectIds, selectChnlDescs){
						if(!selectIds||selectIds.length==0) {
							Ext.Msg.$alert('请选择当前文档要复制到的目标栏目!');
							return false;
						}
						var nFromChnlId = bCopyall==true ? hostChnlId:event.getObj().getPropertyAsInt('currchnlid');
						var oPostData = {
							"ObjectIds" : sObjectids,
							"FromChannelId" : nFromChnlId,
							"ToChannelIds" : selectIds.join(',')
						};			
						//增加检索信息相关参数
						Ext.apply(oPostData, PageContext.params);
						if(bCopyall){
							Ext.apply(oPostData,{ITEMCOUNT:500});
						}
						wcm.domain.ChnlDocMgr.copyEntity(event,bCopyall,oPostData);
					},
					dialogArguments = args
				);
			});
		},

		moveall : function(event,operItem){
			var sHtml = String.format("确定要{0}移动所有{1}文档么？将{2}不可逆转！{3}",'<font color=\'red\' style=\'font-size:14px;\'>','</font>','<font color=\'red\' style=\'font-size:14px;\'>','</font>');
			Ext.Msg.confirm(sHtml,{
				yes : function(){
					wcm.domain.ChnlDocMgr.move(event, operItem, true);
				}
			})			
		},
		moveEntity : function(event,bMoveall,oPostData){
			var func = function(){
				FloatPanel.close();
				CMSObj.afteredit(event)();
			}
			ProcessBar.start(wcm.LANG.DOCUMENT_PROCESS_270 ||'执行移动文档..');
			var oHelper = new com.trs.web2frame.BasicDataHelper();
			oHelper.Call('wcm6_viewdocument',(bMoveall==true)?'moveAll':'move',oPostData,true,
				function(_transport,_json){
					ProcessBar.close();
					Ext.Msg.report(_json,(wcm.LANG.DOCUMENT_PROCESS_54 ||'文档移动结果'),func);
					FloatPanel.hide();
				},
				function(_transport,_json){
					$render500Err(_transport,_json);
					FloatPanel.close();
				}
			);
		},
		move : function(event,operItem,bMoveall){
			var context = event.getContext();
			var sObjectids = event.getIds();
			var host = event.getHost();
			var hostId = host.getId();
			var hostType = host.getIntType();
			var hostChnlId = hostType == 101 ? hostId : 0;
			sObjectids = (sObjectids.length!=0)?sObjectids:'0';
			var channelids = event.getObjs().getPropertyAsString("currchnlid", 0);
			if(!Ext.isArray(channelids)) channelids = [channelids];
			var channelType = event.getObjs().getPropertyAsString("channelType", 0);
			if(!Ext.isArray(channelType)) channelType = [channelType];
			var bIsOneChannel = true;
			var tmpChannelid = channelids[0];
			for(var i=1,n=channelids.length; i<n; i++){
				if(tmpChannelid!=channelids[i]){
					bIsOneChannel = false;
					break;
				}
			}
			var nExcludeInfoView = 1;
			for(index=0; index<channelType.length; index++){
				if(channelType[index]==13){
					nExcludeInfoView = 0;
					break;
				}
			}
			var args = {
				IsRadio : 1,
				ExcludeTop : 1,
				ExcludeLink : 1,
				ExcludeVirtual : 1,
				ExcludeInfoView : nExcludeInfoView,
				ExcludeOnlySearch : 1,
				ShowOneType : 1,
				SelectedChannelIds : channelids.join() || hostChnlId,
				NotSelect : 1,
				RightIndex : 31,
				canEmpty : true
			};
			if(bIsOneChannel){
				Ext.apply(args, {
					CurrChannelId : hostChnlId || tmpChannelid || 0,
					ExcludeSelf : 1
				});
			}
			getHelper().call(m_sServiceId, "query", context.get("pagecontext").params, true,
				function(_transport,_json){
				var itemCount = 0;
				if(bMoveall){
					itemCount = _json.VIEWDOCUMENTS.NUM;
				}
				FloatPanel.open(
					WCMConstants.WCM6_PATH + 'include/channel_select.html?ItemCount=' + itemCount + '&close=1',
					wcm.LANG.DOCUMENT_PROCESS_94 || '文档-文档移动到...',
					function(selectIds, selectChnlDescs){
						if(!selectIds||selectIds.length==0) {
							Ext.Msg.$alert(wcm.LANG.DOCUMENT_PROCESS_52 ||'请选择当前文档要移动到的目标栏目!');
							return false;
						}
						var nFromChnlId = bMoveall==true ? hostChnlId:event.getObj().getPropertyAsInt('currchnlid');
						var oPostData = {
							"ObjectIds" : sObjectids,
							"FromChannelId" : nFromChnlId,
							"ToChannelId" : selectIds.join(',')
						}
						//增加检索信息相关参数
						Ext.apply(oPostData, PageContext.params);
						if(bMoveall){
							Ext.apply(oPostData,{PAGESIZE:500});
						}
						wcm.domain.ChnlDocMgr.moveEntity(event,bMoveall,oPostData);
					},
					dialogArguments = args
				);
			});
		},
		quote : function(event){
			var oPostData = preparePostData(event);
			var channelType = event.getObjs().getPropertyAsString("channelType", 0);
			Ext.apply(oPostData,{channelType : channelType});
			FloatPanel.open(WCMConstants.WCM6_PATH +
					'document/document_quoteto.html?' + $toQueryStr(oPostData),
					wcm.LANG.DOCUMENT_PROCESS_95 || '文档-文档引用到...');
		},
		trash : function(event){
			var oHost = parseHost(event.getHost());
			var nCount = event.length();
			var sHint = (nCount==1)?'':' '+nCount+' ';
			var browserEvent = event.browserEvent;
			var bDrop = !!(browserEvent && 
				browserEvent.type=='keydown' && browserEvent.shiftKey);
			var params = {
				objectids: event.getIds(),
				operation: bDrop ? '_forcedelete' : '_trash'
			}
			doOptionsAfterDisplayInfo(params, function(){
				ProcessBar.start(wcm.LANG.DOCUMENT_PROCESS_123||'删除文档');
				getHelper().call(m_sServiceId, 'delete',
					Ext.apply(oHost,{"ObjectIds": event.getIds(), "drop": bDrop}), true, 
					function(){
						ProcessBar.close();
						event.getObjs().afterdelete();
					}
				);
			});
		},
		'export' : function(event){
			var oPostData = preparePostData(event);
			FloatPanel.open(WCMConstants.WCM6_PATH +
					'document/document_export.jsp?' + $toQueryStr(oPostData),
					wcm.LANG.DOCUMENT_PROCESS_96 || '文档-导出文档');
		},
		backup : function(event){
			var oPostData = {
				docids: event.getObjs().getDocIds(),
				ExcludeTrashed: true
			};
			getHelper().call('wcm6_documentBak','backup', oPostData, true,
				function(_transport,_json){
					Ext.Msg.report(_json,wcm.LANG.DOCUMENT_PROCESS_97 || '文档版本保存结果');
				}
			);
		},
		backupmgr : function(event){
			var sDocId = event.getObjs().getDocIds();
			FloatPanel.open(WCMConstants.WCM6_PATH +
					'document/docbak_list.html?DocumentId=' + sDocId,
					wcm.LANG.DOCUMENT_PROCESS_98 || '文档-版本管理', CMSObj.afteredit(event));
		},
		docpositionset : function(event){
			var oPageParams = event.getContext();
			var host = event.getHost();
			var hostid = host.getId();
			var objType = event.getObjs().getAt(0).objType;
			var docid = event.getObjs().getAt(0).getPropertyAsInt("docId", 0);
			Object.extend(oPageParams,host.getType()=="website"?{"siteid" : hostid}:{"channelid" : hostid});
			Object.extend(oPageParams,{"DocumentId":docid,"DocType":objType});
			var sObjType = (objType.trim()=='photo'? (wcm.LANG.PHOTO || '图片') :(wcm.LANG.DOCUMENT || '文档'));
			var sTitle = String.format(wcm.LANG.DOCUMENT_PROCESS_222 || "{0}-调整顺序", sObjType);
			FloatPanel.open(WCMConstants.WCM6_PATH + 'document/document_position_set.jsp?' + $toQueryStr(oPageParams), sTitle, CMSObj.afteredit(event));
		},
		view : function(event){
			var pageContext = event.getContext();
			var host = event.getHost();
			var hostType = host.getIntType();
			var hostId = host.getId();
			var oParams = {
				DocumentId : event.getObjs().getDocIds(),
				ChannelId :event.getObj().getPropertyAsInt('currchnlid'),
				//SiteId :host.getType()==WCMConstants.OBJ_TYPE_WEBSITE?host.getId():0,
				ChnlDocId : event.getIds(),
				FromRecycle : pageContext.fromRecycle || 0
			};
			$openMaxWin(WCMConstants.WCM6_PATH +
					'document/document_show.jsp?' + $toQueryStr(oParams));
		},
		commentmgr : function(event){
			var oParams = Ext.apply({
				DocumentId : event.getObjs().getDocIds(),
				ChannelId :event.getObj().getPropertyAsInt('currchnlid'),
				SiteId : 0
			}, parseHost(event.getHost()));
			var sUrl = WCMConstants.WCM_ROOTPATH +'comment/comment_mgr.jsp?'
					+ $toQueryStr(oParams);
			$openMaxWin(sUrl);
		},
		pasteFromTop : function(event){
			wcm.domain.ChnlDocMgr._PasteInTo('copy', event);
		},
		quoteFromTop : function(event){
			var oActualTop = $MsgCenter.getActualTop();
			if(oActualTop._QuickDataCenter_!=null){
				if(oActualTop._QuickDataCenter_.ChannelId == event.getHost().getId())
					return false;
				wcm.domain.ChnlDocMgr._PasteInTo('quote', _oPageParams);
			}
		}
	}, wcm.domain.DocumentMgr);
	Ext.apply(wcm.domain.ChnlDocMgr, {
		quoteTo : function(objectids, tochannelid){
			var oPostData = {
				ObjectIds : objectids,
				ToChannelIds : tochannelid
			}
			getHelper().call(m_sServiceId, 'quote', oPostData, true, 
					function(transport,_json){
						ReportsDialog.show(_json,wcm.LANG.DOCUMENT_PROCESS_47 || '文档引用结果',function(){
							return;
						});
					});
		},
		saveorder : function(oPostData, _oCallBacks){
			/*
			var oPostData = {
				FromDocId:_iDocId,
				ToDocId:_iTargetDocId,
				position:_iPosition,
				channelid:_iChannelId
			};
			*/
			_oCallBacks = _oCallBacks || {};
			getHelper().call(m_sServiceId, 'changeOrder', oPostData, false, 
				_oCallBacks["onSuccess"], _oCallBacks["onFailure"], _oCallBacks["onFailure"]
			);
		},
		copyToTop : function(_sDocIds, _sDocTitles, _oPageParams){
			var aTop = $MsgCenter.getActualTop();
			aTop._QuickDataCenter_ = {
				ChnlDocIds : _sDocIds,
				ChnlDocTitles : _sDocTitles,
				ChannelId : _oPageParams['channelid']
			};
		},
		cutToTop : function(_sDocIds, _sDocTitles, _oPageParams){
			var aTop = $MsgCenter.getActualTop();
			aTop._QuickDataCenter_ = {
				IsCutting : true,
				ChnlDocIds : _sDocIds,
				ChnlDocTitles : _sDocTitles,
				ChannelId : _oPageParams['channelid']
			};
		},
		_PasteInTo : function(_sMethod, event){
			var oActualTop = $MsgCenter.getActualTop();
			if(oActualTop._QuickDataCenter_==null)return;
			var caller = wcm.domain.ChnlDocMgr;
			if(!caller._checkAvaliable(event))return;
			var _sDocIds = oActualTop._QuickDataCenter_.ChnlDocIds;
			var _sDocTitles = oActualTop._QuickDataCenter_.ChnlDocTitles;
			var bIsCutting = oActualTop._QuickDataCenter_.IsCutting;
			if(bIsCutting && _sMethod=='quote'){
				return;
			}
			_sMethod = (bIsCutting && _sMethod=='copy')?'move':'copy';
			var sDisplay = (_sMethod=='quote') ? (wcm.LANG.DOCUMENT_PROCESS_101 || '引用') : ((bIsCutting)?(wcm.LANG.DOCUMENT_PROCESS_102 || '移动'): (wcm.LANG.DOCUMENT_PROCESS_103 || '复制'));
			var sConfirmTip = String.format(wcm.LANG.DOCUMENT_PROCESS_100 || ('您确定要{0}以下文档?\n'),sDisplay);
			if(_sDocTitles){
				var arrIds = _sDocIds.split(',');
				var arrTitles = _sDocTitles.split(',');
				for (var i = 0; i < arrIds.length; i++){
					sConfirmTip += '  '+(i+1)+',[' + (wcm.LANG.DOCUMENT || '文档') + '-'+arrIds[i]+']:'+(arrTitles[i]||'')+'\n';
				}
			}
			else{
				sConfirmTip += _sDocIds;
			}
			if(!confirm(sConfirmTip)){
				return;
			}
			var oPostData = null;
			var nChannelId = event.getHost().getId();
			if(_sMethod=='move'){
				oPostData = {
					"ObjectIds" : _sDocIds,
					"ToChannelId" : nChannelId
				}
			}
			else{
				oPostData = {
					"ObjectIds" : _sDocIds,
					"ToChannelIds" : nChannelId
				}
			}
			_sMethod = _sMethod || 'quote';
			getHelper().Call(m_sServiceId, _sMethod, oPostData, true,
				function(_transport,_json){
					oActualTop.ReportsDialog.show(_json, String.format("文档{0}结果",sDisplay), function(){
						var chnldocs = new wcm.ChnlDocs();
						chnldocs.addElement({objId:0});
						chnldocs.afteradd();
					});
				}
			);
			oActualTop._QuickDataCenter_ = null;
		},
		_checkAvaliable : function(event){
			var context = event.getContext();
			if(context.getHost().getType()!=WCMConstants.OBJ_TYPE_CHANNEL)return false;
			if(Ext.isTrue(context.isVirtual))return false;
			if(context.right && !wcm.AuthServer.hasRight(context.right, 31)){
				return false;
			}
			return true;
		},
		recoverBak : function(_nVersion, _oPageParams, _fCallBack){
			var nDocumentId = _oPageParams.DocumentId;
			_nVersion = parseInt(_nVersion, 10);
			var oPostData = {
				DocumentId : nDocumentId,
				Version : _nVersion
			};
			getHelper().call('wcm6_documentBak', 'recover', oPostData, true,
				function(_transport,_json){
					$timeAlert(String.format(wcm.LANG.DOCUMENT_PROCESS_105 || ("成功恢复文档[ID={0}]为版本[版本号={1}]!"),nDocumentId, _nVersion+1), 5, null, null, 2);
					var documents = new wcm.Documents();
					documents.addElement({objId:nDocumentId});
					documents.afteredit();
					if(_fCallBack)_fCallBack();
				}
			);
		},
		deleteBak : function(_nVersion, _oPageParams, _fCallBack){
			var nDocumentId = _oPageParams.DocumentId;
			_nVersion = parseInt(_nVersion, 10);
			var oPostData = {
				DocumentId : nDocumentId,
				ObjectIds : _nVersion
			};
			getHelper().call('wcm6_documentBak','delete', oPostData,true,
				function(_transport,_json){
					var documentBaks = new wcm.CMSObjs({
						objType : WCMConstants.OBJ_TYPE_DOCUMENTBAK
					});
					//documentBaks.addElement({objId:0});
					documentBaks.afterdelete();
					if(_fCallBack)_fCallBack();
				}
			);
		},
		viewBak : function(_nVersion, _oPageParams){
			var nDocumentId = _oPageParams.DocumentId;
			var oPostData = {
				DocumentId : nDocumentId,
				Version : _nVersion
			};
			$openMaxWin(WCMConstants.WCM6_PATH + 
				'document/document_backup_show.html?' + $toQueryStr(oPostData));
		},
		settopdocument : function(event){
			var sDocId = event.getObjs().getDocIds();
			var nChnlId = event.getObj().getPropertyAsInt('currchnlid');
			var oPostData = {
				channelid : nChnlId,
				documentid : sDocId,
				targetdocumentid : 0,
				topflag : 0
			};
			getHelper().Call("wcm6_document", "settopdocument", oPostData, true,
				function(_transport,_json){
					event.getObjs().afteredit();;
			});
		},

		editorToolBar : function(event){
			var oPageParams = event.getContext();
			var channelId = 0;
			channelId = event.getHost().getId();
			var sDialogId = "editToolbar";
			wcm.CrashBoarder.get(sDialogId).show({
				id	: sDialogId,
				title : wcm.LANG.CHANNEL_70 ||"编辑器工具栏定制",
				src : "channel/eidtor_toolBar_set.jsp",
				width : "800px",
				height : "500px",
				params : {"ChannelId": channelId},
				callback : function(params){
					CMSObj.afteredit(event);
				}
			});
		},
		importEditorCss : function(event){
			var oPageParams = event.getContext();
			var channelId = 0;
			channelId = event.getHost().getId();
			FloatPanel.open(WCMConstants.WCM6_PATH + 'channel/channel_importEditorCss.jsp?ChannelId=' + channelId, wcm.LANG.CHANNEL_71 ||'样式文件导入', function(){
			});
		},
		importEditorSiteCSS: function(event){
			var oPageParams = event.getContext();
			var siteId = 0;
			siteId = event.getHost().getId();
			FloatPanel.open(WCMConstants.WCM6_PATH + 'channel/channel_importEditorCss.jsp?SiteId=' + siteId, wcm.LANG.CHANNEL_71 ||'样式文件导入', function(){
			});
		},
		docFieldsSet : function(event){
			var hostId = event.getHost().getId();
			var params = {
				ChannelId : hostId
			};
			FloatPanel.open(WCMConstants.WCM6_PATH + 'channel/document_field_showset.jsp?' + $toQueryStr(params), wcm.LANG.CHANNEL_74 ||'设置文档列表显示字段', function(){
				CMSObj.afteredit(event)({objType:WCMConstants.OBJ_TYPE_CHANNEL,objId:hostId});
			});
		},
		docPropSet : function(event){
			var hostId = event.getHost().getId();
			var params = {
				ChannelId : hostId
			};
			FloatPanel.open(WCMConstants.WCM6_PATH + 'channel/document_props_showset.jsp?' + $toQueryStr(params), wcm.LANG.CHANNEL_75 ||'文档编辑页面属性定制', function(){
				CMSObj.afteredit(event)({objType:WCMConstants.OBJ_TYPE_CHANNEL,objId:hostId});
			});
		},
		
		trace_document : function(event){
			var objId = event.getObjs().getDocIds();
			$openMaxWin(WCMConstants.WCM6_PATH + 'document/trace_document.jsp?DocumentId=' + objId);
		},
		// 批量修改所有文档,需要先通过当前的页面参数获取到需要修改的chnldocids,再发送修改服务
		editAllDocuments : function(event){
			var hostId = event.getHost().getId();
			var params = {};
			Ext.apply(params,PageContext.params);
			Ext.apply(params,{
				"CurrPageIndex":1,
				"PageSize":-1
			});
			// 先查询出chnldocids是为了解决，页面中检索参数与我修改页面中修改的参数出现混淆的问题
			// 例如 我页面中选择发布的文档，修改时把它修改成“新稿”，则传入的参数中只会有一个新稿参数，导致检索结果不正确
			getHelper().Call("wcm61_viewdocument","query",params,true,
				function(_transport,_json){
				var num = _json["VIEWDOCUMENTS"]["NUM"];
				var viewDocument = _json["VIEWDOCUMENTS"]["VIEWDOCUMENT"];
				var chnlDocIds = [];
				if(num == 0){
					Ext.Msg.alert("没有需要修改的文档！");
					return;
				}
				if(num == 1){
					chnlDocIds.push(viewDocument["RECID"]);
				}else if(num > 1){
					for (var i = 0; i < viewDocument.length; i++){
						chnlDocIds.push(viewDocument[i]["RECID"]);
					}
				}
				wcm.domain.ChnlDocMgr.modify(event,{ChnlDocIds:chnlDocIds.join(",")});
			});
		},
		// 批量修改这些文档
		editDocuments : function(event){
			var params = {
				ChnlDocIds : event.getIds().join(",")
			};
			wcm.domain.ChnlDocMgr.modify(event,params);
		},
		modify : function(event,params){
			var sTitle = wcm.LANG.DOCUMENT_PROCESS_267 || '修改这篇文档属性';
			if(event.getIds().length > 1){
				sTitle = wcm.LANG.CHANNEL_120 || '批量修改文档属性'
			}
			FloatPanel.open(
				WCMConstants.WCM6_PATH + 'document/documents_modify.jsp?ChannelId='+event.getHost().getId(), 
				sTitle, 
				function(param){
					Ext.apply(param,params);
					wcm.domain.ChnlDocMgr.editDocumentsEntity(event,param);
				},
				params
			);
		},
		editDocumentsEntity : function(event,oPostDate){
			// 发送服务
			getHelper().Call("wcm61_viewdocument", "editDocuments", oPostDate, true,
			function(_transport,_json){
				//ProcessBar.exit();
				FloatPanel.close();
				CMSObj.afteredit(event)();
			},
			function(_transport,_json){
				$render500Err(_transport,_json);
				FloatPanel.close();
			});
		}
	});
})();
(function(){
	var m_nWebSiteObjType = 103;
	var pageObjMgr = wcm.domain.ChnlDocMgr;
	var reg = wcm.SysOpers.register;
	var fnIsVisible = function(event){
			var host = event.getHost();
			if(Ext.isTrue(host.isVirtual)){
				return false;
			}
			return true;
	};
	var fnIsNotDraft = function(event){
		var currObj = event.getObj();
		var bDraft= currObj.getPropertyAsString("bDraft");
		if("true" == bDraft){
			return false;
		}
		return true;
	}
	reg({
		key : 'new',
		type : 'documentInSite',
		desc : wcm.LANG.DOCUMENT_PROCESS_106 ||'创建一篇新文档',
		title : '创建一篇新文档...',
		rightIndex : 31,
		order : 1,
		fn : pageObjMgr['new'],
		isVisible : fnIsVisible,
		quickKey : 'N'
	});
	reg({
		key : 'quicknew',
		type : 'documentInSite',
		desc : wcm.LANG.DOCUMENT_PROCESS_107 ||'智能创建文档',
		title : '智能创建文档...',
		rightIndex : 31,
		order : 2,
		fn : pageObjMgr['quicknew'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'import',
		type : 'documentInSite',
		desc : wcm.LANG.DOCUMENT_PROCESS_108 ||'从外部导入文档',
		title : '从外部导入文档...',
		rightIndex : 31,
		order : 3,
		fn : pageObjMgr['import'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'exportall',
		type : 'documentInSite',
		desc : wcm.LANG.DOCUMENT_PROCESS_109 ||'导出所有文档',
		title : '导出所有文档...',
		rightIndex : 34,
		order : 4,
		fn : pageObjMgr['exportall'],
		quickKey : 'X'
	});
	/** 批量导入与智能创建文档在同一个入口“智能创建文档”
	reg({
		key : 'importoffice',
		type : 'documentInSite',
		desc : wcm.LANG.DOCUMENT_PROCESS_110 ||'批量导入Office文档',

		rightIndex : 31,
		order : 5,
		fn : pageObjMgr['importoffice'],
		isVisible : fnIsVisible
	});**/
	/*
	reg({
		key : 'importEditorSiteCSS',
		type : 'documentInSite',
		desc : wcm.LANG.CHANNEL_73 ||'导入编辑器样式文件',
		title : '导入编辑器样式文件...',
		rightIndex : 31,
		order : 6,
		fn : pageObjMgr['importEditorSiteCSS'],
		isVisible : fnIsVisible
	});
	*/
	reg({
		key : 'new',
		type : 'documentInChannel',
		desc : wcm.LANG.DOCUMENT_PROCESS_106 ||'创建一篇新文档',
		title:'创建一篇新文档...',
		rightIndex : 31,
		order : 1,
		fn : pageObjMgr['new'],
		isVisible : fnIsVisible,
		quickKey : 'N'
	});
	reg({
		key : 'quicknew',
		type : 'documentInChannel',
		desc : wcm.LANG.DOCUMENT_PROCESS_107 ||'智能创建一篇新文档',
		title:'智能创建一篇新文档...',
		rightIndex : 31,
		order : 2,
		fn : pageObjMgr['quicknew'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'import',
		type : 'documentInChannel',
		desc : wcm.LANG.DOCUMENT_PROCESS_108 ||'从外部导入文档',
		title:'从外部导入文档...',
		rightIndex : 31,
		order : 3,
		fn : pageObjMgr['import'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'move',
		type : 'documentInChannel',
		desc : wcm.LANG.DOCUMENT_PROCESS_112 ||'移动所有文档到',
		title:'移动所有文档到...',
		rightIndex : 56,
		order : 4,
		fn : pageObjMgr['moveall'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'copy',
		type : 'documentInChannel',
		desc : wcm.LANG.DOCUMENT_PROCESS_113 ||'复制所有文档到',
		title:'复制所有文档到...',
		rightIndex : 57,
		order : 5,
		fn : pageObjMgr['copyall'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'exportall',
		type : 'documentInChannel',
		desc : wcm.LANG.DOCUMENT_PROCESS_109 ||'导出所有文档',
		title:'导出所有文档...',
		rightIndex : 34,
		order : 6,
		fn : pageObjMgr['exportall'],
		isVisible : function(event){
			var context = event.getContext();
			if(context.params['CHANNELTYPE'] == "11"){
				return false;
			}
			return true;
		}
	});
	//
	reg({
		key : 'editorToolBar',
		type : 'documentInChannel',
		desc : wcm.LANG.CHANNEL_72 ||'定制编辑器工具栏',
		title:'定制编辑器工具栏...',
		rightIndex : 13,
		order : 8,
		fn : pageObjMgr['editorToolBar'],
		isVisible : fnIsVisible
	});
	/*reg({
		key : 'importEditorCss',
		type : 'documentInChannel',
		desc : wcm.LANG.CHANNEL_73 ||'导入编辑器样式文件',
		title:'导入编辑器样式文件...',
		rightIndex : 13,
		order : 9,
		fn : pageObjMgr['importEditorCss'],
		isVisible : fnIsVisible
	});*/
	reg({
		key : 'docFieldsSet',
		type : 'documentInChannel',
		desc : wcm.LANG.CHANNEL_74 ||'设置文档列表显示字段',
		title:'设置文档列表显示字段...',
		rightIndex : 13,
		order : 10,
		fn : pageObjMgr['docFieldsSet'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'docPropSet',
		type : 'documentInChannel',
		desc : wcm.LANG.CHANNEL_75 ||'文档编辑页面属性定制',
		title:'文档编辑页面属性定制...',
		rightIndex : 13,
		order : 11,
		fn : pageObjMgr['docPropSet'],
		isVisible : fnIsVisible
	});
	/** 取消批量导入Office文档操作，该操作与智能创建文档合并
	reg({
		key : 'importoffice',
		type : 'documentInChannel',
		desc : wcm.LANG.DOCUMENT_PROCESS_110 ||'批量导入Office文档',

		rightIndex : 31,
		order : 7,
		fn : pageObjMgr['importoffice'],
		isVisible : fnIsVisible
	});
	**/
	reg({
		key : 'editAllDocuments',
		type : 'documentInChannel',
		desc : wcm.LANG.CHANNEL_121 || '修改所有文档属性',
		title:'修改所有文档属性...',
		rightIndex : 32,
		order : 12,
		fn : pageObjMgr['editAllDocuments'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'edit',
		type : 'chnldoc',
		desc : wcm.LANG.DOCUMENT_PROCESS_115 ||'修改这篇文档',
		title : '修改这篇文档...',
		rightIndex : 32,
		order : 1,
		fn : pageObjMgr['edit'],
		quickKey : 'M'
	});
	reg({
		key : 'preview',
		type : 'chnldoc',
		desc : wcm.LANG.DOCUMENT_PROCESS_116 ||'预览这篇文档',
		title : '预览这篇文档...',
		rightIndex : 38,
		order : 2,
		fn : pageObjMgr['preview'],
		quickKey : 'R'
	});
	reg({
		key : 'basicpublish',
		type : 'chnldoc',
		desc : wcm.LANG.DOCUMENT_PROCESS_118 ||'发布这篇文档',
		title : wcm.LANG.DOCUMENT_PROCESS_119 ||'发布这篇文档,生成这篇文档的细览页面以及更新相关概览页面',
		rightIndex : 39,
		order : 3,
		isVisible : fnIsNotDraft,
		fn : pageObjMgr['basicpublish'],
		quickKey : 'P'
	});
	reg({
		key : 'copy',
		type : 'chnldoc',
		desc : wcm.LANG.DOCUMENT_PROCESS_120 ||'复制这篇文档到',
		title: '复制这篇文档到',
		rightIndex : 34,
		order : 4,
		fn : pageObjMgr['copy'],
		isVisible : function(event){
			var host = event.getHost();
			if(host.getIntType() != m_nWebSiteObjType){
				return fnIsNotDraft(event);
			}
			var objs = event.getObjs();
			for (var i = 0, length = objs.size(); i < length; i++){
				var obj = objs.getAt(i);
				if(obj.getPropertyAsInt('dockind') != 0) return false;
			}
			return fnIsNotDraft(event);
		}
	});
	reg({
		key : 'quote',
		type : 'chnldoc',
		desc : wcm.LANG.DOCUMENT_PROCESS_121 ||'引用这篇文档到',
		title: '引用这篇文档到',
		rightIndex : 34,
		order : 5,
		isVisible : fnIsNotDraft,
		fn : pageObjMgr['quote']
	});
	reg({
		key : 'move',
		type : 'chnldoc',
		desc : wcm.LANG.DOCUMENT_PROCESS_122 ||'移动这篇文档到',
		title: '移动这篇文档到',
		rightIndex : 33,
		order : 6,
		isVisible : fnIsNotDraft,
		fn : pageObjMgr['move']
	});
	reg({
		key : 'trash',
		type : 'chnldoc',
		desc : wcm.LANG.DOCUMENT_PROCESS_123 ||'删除文档',
		title : wcm.LANG.DOCUMENT_PROCESS_124 ||'将这篇文档放入废稿箱',
		rightIndex : 33,
		order : 7,
		fn : pageObjMgr['trash'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'changestatus',
		type : 'chnldoc',
		desc : wcm.LANG.DOCUMENT_PROCESS_125 ||'改变这篇文档状态',
		title: '改变这篇文档状态...',
		rightIndex : 35,
		order : 8,
		isVisible : fnIsNotDraft,
		fn : pageObjMgr['changestatus']
	});
	reg({
		key : 'changelevel',
		type : 'chnldoc',
		desc :  wcm.LANG.DOCUMENT_PROCESS_273 || '改变这篇文档的密级',
		title : '改变这篇文档的密级...',
		rightIndex : 61,
		order : 8,
		fn : pageObjMgr['changelevel']
	});
	reg({
		key : 'setright',
		type : 'chnldoc',
		desc : wcm.LANG.DOCUMENT_PROCESS_126 ||'设置这篇文档权限',
		title :'设置这篇文档权限...',
		rightIndex : 61,
		order : 9,
		fn : pageObjMgr['setright']
	});
	reg({
		key : 'detailpublish',
		type : 'chnldoc',
		desc : wcm.LANG.DOCUMENT_PROCESS_127 ||'仅发布这篇文档细览',
		title : wcm.LANG.DOCUMENT_PROCESS_128 ||'仅发布这篇文档细览,仅重新生成这篇文档的细览页面',
		rightIndex : 39,
		order : 10,
		isVisible : fnIsNotDraft,
		fn : pageObjMgr['detailpublish']
	});
	reg({
		key : 'directpublish',
		type : 'chnldoc',
		desc : wcm.LANG.DOCUMENT_PROCESS_239 ||'直接发布这篇文档',
		title : wcm.LANG.DOCUMENT_PROCESS_240 ||'发布这篇文档,同时发布此文档的所有引用文档',
		rightIndex : 39,
		order : 11,
		isVisible : fnIsNotDraft,
		fn : pageObjMgr['directpublish']
	});
	reg({
		key : 'recallpublish',
		type : 'chnldoc',
		desc : wcm.LANG.DOCUMENT_PROCESS_129 ||'撤销发布这篇文档',
		title : '撤销发布这篇文档...',
		rightIndex : 39,
		order : 12,
		isVisible : fnIsNotDraft,
		fn : pageObjMgr['recallpublish']
	});
	reg({
		key : 'directRecallpublish',
		type : 'chnldoc',
		desc : wcm.LANG.DOCUMENT_PROCESS_254 ||'直接撤销发布这篇文档',
		title : wcm.LANG.DOCUMENT_PROCESS_256 ||'撤销发布这篇文档，同步撤销这篇文档所有的引用文档',
		rightIndex : 39,
		order : 13,
		isVisible : fnIsNotDraft,
		fn : pageObjMgr['directRecallpublish']
	});
	reg({
		key : 'backup',
		type : 'chnldoc',
		desc : wcm.LANG.DOCUMENT_PROCESS_131 ||'为这篇文档产生版本',
		title :'为这篇文档产生版本...',
		rightIndex : 32,
		order : 14,
		fn : pageObjMgr['backup']
	});
	reg({
		key : 'backupmgr',
		type : 'chnldoc',
		desc : wcm.LANG.DOCUMENT_PROCESS_132 ||'管理这篇文档版本',
		titile : '管理这篇文档版本...',
		rightIndex : 32,
		order : 15,
		fn : pageObjMgr['backupmgr']
	});
	reg({
		key : 'seperate',
		type : 'chnldoc',
		desc : wcm.LANG.DOCUMENT_PROCESS_134 ||'分隔线',
		title : '分隔线',
		rightIndex : -1,
		order : 16,
		fn : pageObjMgr['seperate']
	});
	reg({
		key : 'export',
		type : 'chnldoc',
		desc : wcm.LANG.DOCUMENT_PROCESS_135 ||'导出这篇文档',
		title : '导出这篇文档...',
		rightIndex : 34,
		order : 17,
		fn : pageObjMgr['export'],
		quickKey : 'X'
	});
	reg({
		key : 'commentmgr',
		type : 'chnldoc',
		desc : wcm.LANG.DOCUMENT_PROCESS_137 ||'管理评论',
		title : wcm.LANG.DOCUMENT_PROCESS_138 ||'管理文档的评论',
		rightIndex : 8,
		order : 18,
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
		key : 'docpositionset',
		type : 'chnldoc',
		desc : wcm.LANG.DOCUMENT_PROCESS_139 ||'调整顺序',
		title:'调整顺序',
		rightIndex : 62,
		order : 19,
		fn : pageObjMgr['docpositionset'],
		isVisible : function(event){
			if(!event.getContext().get('CanSort')){
				return false;
			}
			if(event.getHost().getType() == WCMConstants.OBJ_TYPE_CHANNEL)
				return true;
			return false;
		}
	});
	reg({
		key : 'logo',
		type : 'chnldoc',
		desc : wcm.LANG.DOCUMENT_PROCESS_206 ||'文档LOGO',
		title : "文档LOGO...",
		rightIndex : 32,
		order : 20,
		fn : pageObjMgr['logo']
	});
	reg({
		key : 'preview',
		type : 'chnldocs',
		desc : wcm.LANG.DOCUMENT_PROCESS_140 ||'预览这些文档',
		title : '预览这些文档...',
		rightIndex : 38,
		order : 1,
		fn : pageObjMgr['preview'],
		quickKey : 'R'
	});
	reg({
		key : 'basicpublish',
		type : 'chnldocs',
		desc : wcm.LANG.DOCUMENT_PROCESS_142 ||'发布这些文档',
		title : wcm.LANG.DOCUMENT_PROCESS_143 ||'发布这些文档,生成这些文档的细览页面以及更新相关概览页面',
		rightIndex : 39,
		order : 2,
		fn : pageObjMgr['basicpublish'],
		quickKey : 'P'
	});
	reg({
		key : 'trash',
		type : 'chnldocs',
		desc : wcm.LANG.DOCUMENT_PROCESS_123 ||'删除文档',
		title : wcm.LANG.DOCUMENT_PROCESS_124 ||'将这篇文档放入废稿箱',
		rightIndex : 33,
		order : 3,
		fn : pageObjMgr['trash'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'copy',
		type : 'chnldocs',
		desc : wcm.LANG.DOCUMENT_PROCESS_144 ||'复制这些文档到',
		title : "复制这些文档到...",
		rightIndex : 34,
		order : 4,
		fn : pageObjMgr['copy'],
		isVisible : function(event){
			var host = event.getHost();
			if(host.getIntType() != m_nWebSiteObjType) return true;
			var objs = event.getObjs();
			for (var i = 0, length = objs.size(); i < length; i++){
				var obj = objs.getAt(i);
				if(obj.getPropertyAsInt('dockind') != 0) return false;
			}
			return true;
		}
	});
	reg({
		key : 'move',
		type : 'chnldocs',
		desc : wcm.LANG.DOCUMENT_PROCESS_145 ||'移动这些文档到',
		title : "移动这些文档到",
		rightIndex : 33,
		order : 5,
		fn : pageObjMgr['move']
	});
	reg({
		key : 'quote',
		type : 'chnldocs',
		desc : wcm.LANG.DOCUMENT_PROCESS_146 ||'引用这些文档到',
		title : "引用这些文档到...",
		rightIndex : 34,
		order : 6,
		fn : pageObjMgr['quote']
	});
	reg({
		key : 'changestatus',
		type : 'chnldocs',
		desc : wcm.LANG.DOCUMENT_PROCESS_147 ||'改变这些文档的状态',
		title : '改变这些文档的状态...',
		rightIndex : 35,
		order : 7,
		fn : pageObjMgr['changestatus']
	});
	reg({
		key : 'changelevel',
		type : 'chnldocs',
		desc : '改变这些文档的密级',
		title : '改变这些文档的密级...',
		rightIndex : 61,
		order : 7,
		fn : pageObjMgr['changelevel']
	});
	reg({
		key : 'setright',
		type : 'chnldocs',
		desc : wcm.LANG.DOCUMENT_PROCESS_148 ||'设置这些文档的权限',
		title : '设置这些文档的权限...',
		rightIndex : 61,
		order : 8,
		fn : pageObjMgr['setright']
	});
	reg({
		key : 'detailpublish',
		type : 'chnldocs',
		desc : wcm.LANG.DOCUMENT_PROCESS_149 ||'仅发布这些文档细览',
		title : wcm.LANG.DOCUMENT_PROCESS_150 ||'仅发布这些文档细览,仅重新生成这些文档的细览页面',
		rightIndex : 39,
		order : 9,
		fn : pageObjMgr['detailpublish']
	});
	reg({
		key : 'directpublish',
		type : 'chnldocs',
		desc : wcm.LANG.DOCUMENT_PROCESS_241 ||'直接发布这些文档',
		title : wcm.LANG.DOCUMENT_PROCESS_242 ||'发布这些文档，同步发布这些文档所有的引用文档',
		rightIndex : 39,
		order : 10,
		fn : pageObjMgr['directpublish']
	});
	reg({
		key : 'recallpublish',
		type : 'chnldocs',
		desc : wcm.LANG.DOCUMENT_PROCESS_151 ||'撤销发布这些文档',
		title : '撤销发布这些文档...',
		rightIndex : 39,
		order : 11,
		fn : pageObjMgr['recallpublish']
	});
	reg({
		key : 'directRecallpublish',
		type : 'chnldocs',
		desc : wcm.LANG.DOCUMENT_PROCESS_255 ||'直接撤销发布这些文档',
		title : wcm.LANG.DOCUMENT_PROCESS_257 ||'撤销发布这些文档，同步撤销这些文档所有的引用文档',
		rightIndex : 39,
		order : 12,
		fn : pageObjMgr['directRecallpublish']
	});
	reg({
		key : 'backup',
		type : 'chnldocs',
		desc : wcm.LANG.DOCUMENT_PROCESS_153 ||'为这些文档产生版本',
		title : '为这些文档产生版本...',
		rightIndex : 32,
		order : 13,
		fn : pageObjMgr['backup']
	});
	reg({
		key : 'seperate',
		type : 'chnldocs',
		desc : wcm.LANG.DOCUMENT_PROCESS_134 ||'分隔线',
		title : '分隔线',
		title : '分隔线',
		rightIndex : -1,
		order : 14,
		fn : pageObjMgr['seperate']
	});
	reg({
		key : 'export',
		type : 'chnldocs',
		desc : wcm.LANG.DOCUMENT_PROCESS_154 ||'导出这些文档',
		title : '导出这些文档...',
		rightIndex : 34,
		order : 15,
		fn : pageObjMgr['export'],
		quickKey : 'X'
	});
	reg({
		key : 'settopdocument',
		type : 'chnldoc',
		desc : wcm.LANG.DOCUMENT_PROCESS_232 ||'取消置顶',
		title : '取消置顶...',
		rightIndex : 62,
		order : 16,
		fn : pageObjMgr['settopdocument'],
		isVisible : function(event){
			var docContext = event.getObj();
			if(docContext.getPropertyAsString("isTopped") == "true") {
				return true;
			} else {
				return false;
			}
			
		}
	});
	reg({
		key : 'trace_document',
		type : 'chnldoc',
		desc : wcm.LANG.DOCUMENT_PROCESS_236 ||'跟踪文档',
		title : '跟踪文档...',
		rightIndex : 32,
		order : 17,
		fn : pageObjMgr['trace_document']
	});
	reg({
		key : 'editDocuments',
		type : 'chnldocs',
		desc : wcm.LANG.DOCUMENT_PROCESS_268 || '修改这些文档属性',
		title : '修改这些文档属性...',
		rightIndex : 32,
		order : 18,
		fn : pageObjMgr['editDocuments']
	});
	reg({
		key : 'editDocuments',
		type : 'chnldoc',
		desc : wcm.LANG.DOCUMENT_PROCESS_267 || '修改这篇文档属性',
		title : '修改这篇文档属性...',
		rightIndex : 32,
		order : 18,
		fn : pageObjMgr['editDocuments']
	});
})();