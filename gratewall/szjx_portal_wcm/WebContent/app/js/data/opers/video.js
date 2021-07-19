//$xxxName$操作信息和Mgr定义
Ext.ns('wcm.domain.videoMgr');
(function(){
	var m_oMgr = wcm.domain.videoMgr;

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

	function publish(objectids, _sMethodName,_oExtraParams){
		_sMethodName = _sMethodName || 'publish';
		var oPostData = {'ObjectIds' : objectids,'IsVideo':true};
		getHelper().call("wcm6_viewdocument", _sMethodName, oPostData, true,
			function(_transport,_json){
				wcm.domain.PublishAndPreviewMgr.doAfterPublish(oPostData, _sMethodName, _transport, _json);
			}
		);
	}
	function getHelper(){
		return new com.trs.web2frame.BasicDataHelper();
	}
	function openEditor(params){
		if(params.DocumentId=='0'&&params.SiteId!=0){
			FloatPanel.open(WCMConstants.WCM6_PATH + 'video/document_siteadd_step1.html?' 
				+ $toQueryStr(params), wcm.LANG.VIDEO_PROCESS_81 || '选择要新建视频的栏目', 400, 350);
			return;
		}
		var iWidth = window.screen.availWidth - 12;
		var iHeight = window.screen.availHeight - 30;
		var sFeature = 'location=no,resizable=yes,menubar=no,scrollbars=no,status=no,titlebar=no,toolbar=no,top=0,left=0,border=0,width='+iWidth+',height='+iHeight;
		window.open(WCMConstants.WCM6_PATH + "video/video_addedit.jsp?" + $toQueryStr(params), "_blank" , sFeature);
	}
	function preparePostData(event){
		var obj = event.getObj();
		return {
			'objectids' : event.getIds(),
			'channelids' : event.getObjs().getPropertyAsInt('currchnlid')
		};
	}
	function parseHost(host){
		if(host.getType()==WCMConstants.OBJ_TYPE_CHANNEL){
			return {ChannelId:host.getId(),SiteId:0};
		}
		if(host.getType()==WCMConstants.OBJ_TYPE_WEBSITE){
			return {SiteId:host.getId(),ChannelId:0};
		}
		return {};
	}
	function doOptionsAfterDisplayInfo(_params, _fDoAfterDisp){
		var DIALOG_DOCUMENT_INFO = 'document_info_dialog';
		wcm.CrashBoarder.get('DIALOG_DOCUMENT_INFO').show({
			title : wcm.LANG.DOCUMENT_PROCESS_208 || '系统提示信息',
			src : WCMConstants.WCM6_PATH + 'docrecycle/video_info.html',
			width:'500px',
			height:'205px',
			maskable:true,
			params :  _params,
			callback : _fDoAfterDisp
		});
	}
	Ext.apply(wcm.domain.videoMgr, {

		upload: function(event) {
		var oParams = Ext.apply({
				DocumentId : 0,
				FromEditor : 1
			}, parseHost(event.getHost()));
			openEditor(oParams);
		},
	snap: function(event) {
			var docId = event.getObj().docId;
			var sFeature = 'location=no,menubar=no,status=no,titlebar=no,toolbar=no,top=0,left=0,border=0,width=800,height=440';
			window.open(WCMConstants.WCM6_PATH + "video/updateThumb.jsp?docId=" + docId, "_blank", sFeature);
		},
		
		preview : function(event){
			var host = event.getHost();
			var hostid = host.getId();
			var oParams = {
				FolderId : hostid,
				FolderType : host.getType()=="website"? 103:101,
				'IsVideo':true
			};
			var _sIds = event.getIds();
			wcm.domain.PublishAndPreviewMgr.preview(_sIds,600,oParams,"wcm6_viewdocument");
		},
		basicpublish : function(event){
			//TODO 提示发布
			var oPageParams = event.getContext();
			var _sIds = event.getIds();
			var postData = Object.extend({},{
				'ObjectIds' : _sIds,
				'IsVideo':true
			});
			BasicDataHelper.call("wcm6_viewdocument", "basicpublish", postData, true, function(_transport,_json){
				wcm.domain.PublishAndPreviewMgr.doAfterPublish(postData, "detailpublish",_transport,_json);
			}.bind(this));
		},
		//publish : function(event){
			//TODO 提示发布
		//	var oPageParams = event.getContext();
		//	var _sIds = event.getIds();
		//	var postData = Object.extend({},{
			//	'ObjectIds' : _sIds,
			//	'IsVideo':true
			//});
			//BasicDataHelper.call("wcm6_viewdocument", "detailpublish", postData, true, function(_transport,_json){
			//	wcm.domain.PublishAndPreviewMgr.doAfterPublish(postData, "detailpublish",_transport,_json);
			//}.bind(this));
		//},
		detailpublish : function(event){
			var oPageParams = event.getContext();
			var _sIds = event.getIds();
			var postData = Object.extend({},{
				'ObjectIds' : _sIds,
				'IsVideo':true
			});
			BasicDataHelper.call("wcm6_viewdocument", "detailpublish", postData, true, function(_transport,_json){
				wcm.domain.PublishAndPreviewMgr.doAfterPublish(postData, "detailpublish",_transport,_json);
			}.bind(this));
		},
		directpublish : function(event){
			var oPostData = {'ObjectIds' : event.getObjs().getPropertyAsString("docId").join(","), 'ObjectType' : 605 };
			getHelper().call("wcm6_publish", "directPublish", oPostData, true,
				function(_transport,_json){
					wcm.domain.PublishAndPreviewMgr.doAfterPublish(oPostData, "directPublish", _transport, _json);
				}
			);
		},
		edit : function(event){
			var obj = event.getObjs().getAt(0);
			var host = event.getHost();
			var oPageParams = event.getContext();
			var oVideo = event.getObj();
			var oParams = {
				ChnlDocId : oVideo.recId,
				DocumentId : oVideo.docId,
				ChannelId :obj.getPropertyAsInt('channelid',0),
				SiteId :host.getType()==WCMConstants.OBJ_TYPE_WEBSITE?host.getId():0,
				FromEditor : 1
			};
			openEditor(oParams);
		},
		changestatus : function(event){
			var oPageParams = event.getContext();
			var host = event.getHost();
			var hostid = host.getId();
			var sId = event.getIds().join();
			Object.extend(oPageParams,{
				"ObjectIds":sId,
				"IsPhoto":false,
				'ChannelIds':(oPageParams,host.getType()=="website"?0:hostid)});
			FloatPanel.open(WCMConstants.WCM6_PATH +'document/change_status.jsp?' + $toQueryStr(oPageParams), '视频-改变状态', CMSObj.afteredit(event));
		},
		copy : function(event,operItem){
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
			for(index=0; index<=channelType.length; index++){
				if(channelType[index]==13)nExcludeInfoView=0;
			}
			var args = {
				IsRadio : 0,
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
					ExcludeSelf : 0
				});
			}
			FloatPanel.open(
				WCMConstants.WCM6_PATH + 'include/channel_select.html?close=1',
				wcm.LANG.VIDEO_PROCESS_93 || '视频-视频复制到...',
				function(selectIds, selectChnlDescs){
					if(!selectIds||selectIds.length==0) {
						Ext.Msg.$alert(wcm.LANG.VIDEO_PROCESS_32 ||'请选择当前视频要复制到的目标栏目!');
						return false;
					}
					var nFromChnlId = event.getObj().getPropertyAsInt('currchnlid');
					var oPostData = {
						"ObjectIds" : sObjectids,
						"FromChannelId" : nFromChnlId,
						"ToChannelIds" : selectIds.join(',')
					};
					var oHelper = new com.trs.web2frame.BasicDataHelper();
					var func = function(){
						FloatPanel.close();
						CMSObj.afteredit(event)();
					}
					oHelper.Call('wcm61_video','copy',oPostData,true,
						function(_transport,_json){
							if(_json!=null&&_json["REPORTS"]){
								var oReports = _json["REPORTS"];
								var stJson = com.trs.util.JSON;
								var bIsSuccess = stJson.value(oReports, "IS_SUCCESS");
								var title = stJson.value(oReports, "TITLE");
								if(bIsSuccess=='true'){
									if(title.indexOf("文档") != -1){
										oReports.TITLE = oReports.TITLE.replace(new RegExp(wcm.LANG.PUBLISH_6 || "文档","g"),wcm.LANG.PUBLISH_8 || "视频");
										if(oReports.REPORT.length){
											for(var i =0;i< oReports.REPORT.length;i++){
												var currItem = stJson.value(oReports.REPORT[i], "TITLE");
												if( currItem != null)
													oReports.REPORT[i].TITLE = currItem.replace(new RegExp(wcm.LANG.PUBLISH_6 || "文档","g"),wcm.LANG.PUBLISH_8 || "视频");
											}
										}else{
											oReports.REPORT.TITLE = oReports.REPORT.TITLE.replace(new RegExp(wcm.LANG.PUBLISH_6 || "文档","g"),wcm.LANG.PUBLISH_8 || "视频");
										}
									}
									Ext.Msg.report(_json,wcm.LANG.VIDEO_PROCESS_36 ||'视频复制结果');
									FloatPanel.hide();
								}else{
									if(title.indexOf("文档") != -1){
										oReports.TITLE = oReports.TITLE.replace(new RegExp(wcm.LANG.PUBLISH_6 || "文档","g"),wcm.LANG.PUBLISH_8 || "视频");
										if(oReports.REPORT.length){
											for(var i =0;i< oReports.REPORT.length;i++){
												var currItem = stJson.value(oReports.REPORT[i], "TITLE");
												if( currItem != null)
													oReports.REPORT[i].TITLE = currItem.replace(new RegExp(wcm.LANG.PUBLISH_6 || "文档","g"),wcm.LANG.PUBLISH_8 || "视频");
											}
										}else{
											oReports.REPORT.TITLE = oReports.REPORT.TITLE.replace(new RegExp(wcm.LANG.PUBLISH_6 || "文档","g"),wcm.LANG.PUBLISH_8 || "视频");
										}
									}
									Ext.Msg.report(_json,wcm.LANG.VIDEO_PROCESS_36 ||'视频复制结果');
									FloatPanel.hide();
								}
							}
						},
						function(_transport,_json){

							$render500Err(_transport,_json);
							FloatPanel.close();
						}
					);
				},
				dialogArguments = args
			);
		},
		quote : function(event){
			var oPostData = preparePostData(event);
			FloatPanel.open(WCMConstants.WCM6_PATH +
					'video/video_quoteto.html?' + $toQueryStr(oPostData),
					wcm.LANG.VIDEO_PROCESS_95 || '视频-视频引用到...');
		},
		move : function(event,operItem){
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
			for(index=0; index<=channelType.length; index++){
				if(channelType[index]==13)nExcludeInfoView=0;
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
			FloatPanel.open(
				WCMConstants.WCM6_PATH + 'include/channel_select.html?close=1',
				wcm.LANG.VIDEO_PROCESS_94 || '视频-视频移动到...',
				function(selectIds, selectChnlDescs){
					if(!selectIds||selectIds.length==0) {
						Ext.Msg.$alert(wcm.LANG.VIDEO_PROCESS_52 ||'请选择当前视频要移动到的目标栏目!');
						return false;
					}
					var nFromChnlId = event.getObj().getPropertyAsInt('currchnlid');
					var oPostData = {
						"ObjectIds" : sObjectids,
						"FromChannelId" : nFromChnlId,
						"ToChannelId" : selectIds.join(',')
					}
					var func = function(){
						FloatPanel.close();
						CMSObj.afteredit(event)();
					}
					var oHelper = new com.trs.web2frame.BasicDataHelper();
					oHelper.Call('wcm6_viewdocument', 'move',oPostData,true,
						function(_transport,_json){
							if(_json!=null&&_json["REPORTS"]){
								var oReports = _json["REPORTS"];
								var stJson = com.trs.util.JSON;
								var bIsSuccess = stJson.value(oReports, "IS_SUCCESS");
								var title = stJson.value(oReports, "TITLE");
								if(bIsSuccess=='true'){
									if(title.indexOf("文档") != -1){
										oReports.TITLE = oReports.TITLE.replace(new RegExp(wcm.LANG.PUBLISH_6 || "文档","g"),wcm.LANG.PUBLISH_8 || "视频");
										if(oReports.REPORT.length){
											for(var i =0;i< oReports.REPORT.length;i++){
												var currItem = stJson.value(oReports.REPORT[i], "TITLE");
												if( currItem != null)
													oReports.REPORT[i].TITLE = currItem.replace(new RegExp(wcm.LANG.PUBLISH_6 || "文档","g"),wcm.LANG.PUBLISH_8 || "视频");
											}
										}else{
											oReports.REPORT.TITLE = oReports.REPORT.TITLE.replace(new RegExp(wcm.LANG.PUBLISH_6 || "文档","g"),wcm.LANG.PUBLISH_8 || "视频");
										}
									}
									Ext.Msg.report(_json,(wcm.LANG.VIDEO_PROCESS_54 ||'视频移动结果'),func);
									FloatPanel.hide();
								}else{
									if(title.indexOf("文档") != -1){
										oReports.TITLE = oReports.TITLE.replace(new RegExp(wcm.LANG.PUBLISH_6 || "文档","g"),wcm.LANG.PUBLISH_8 || "视频");
										if(oReports.REPORT.length){
											for(var i =0;i< oReports.REPORT.length;i++){
												var currItem = stJson.value(oReports.REPORT[i], "TITLE");
												if( currItem != null)
													oReports.REPORT[i].TITLE = currItem.replace(new RegExp(wcm.LANG.PUBLISH_6 || "文档","g"),wcm.LANG.PUBLISH_8 || "视频");
											}
										}else{
											oReports.REPORT.TITLE = oReports.REPORT.TITLE.replace(new RegExp(wcm.LANG.PUBLISH_6 || "文档","g"),wcm.LANG.PUBLISH_8 || "视频");
										}
									}
									Ext.Msg.report(_json,(wcm.LANG.VIDEO_PROCESS_54 ||'视频移动结果'),func);
									FloatPanel.hide();
								}
							}
						},
						function(_transport,_json){
							$render500Err(_transport,_json);
							FloatPanel.close();
						}
					);
				},
				dialogArguments = args
			);
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
				getHelper().call('wcm61_viewdocument', 'delete',
					Ext.apply(oHost,{"ObjectIds": event.getIds(), "drop": bDrop}), true, 
					function(){
						event.getObjs().afterdelete();
					}
				);
			});
		},
		segment : function(event){
			var docId = event.getObj().docId;
			var sFeature = 'location=no,menubar=no,status=no,titlebar=no,toolbar=no,top=0,left=0,border=0,width=800,height=580';
			window.open(WCMConstants.WCM6_PATH + "video/cutVideo.jsp?docId=" + docId, "_blank", sFeature);
		},
		docpositionset : function(event){
			var oPageParams = event.getContext();
			var host = event.getHost();
			var hostid = host.getId();
			var objType = event.getObjs().getAt(0).objType;
			var docid = event.getObjs().getAt(0).getPropertyAsInt("docId", 0);
			Object.extend(oPageParams,host.getType()=="website"?{"siteid" : hostid}:{"channelid" : hostid});
			Object.extend(oPageParams,{"DocumentId":docid,"DocType":objType,"DocTypeDesc":'视频'});
			FloatPanel.open(WCMConstants.WCM6_PATH + 'document/document_position_set.jsp?' + $toQueryStr(oPageParams), (objType.trim()=='video'?('视频'):(wcm.LANG.PHOTO_CONFIRM_68 || '文档')) +
			(wcm.LANG.PHOTO_CONFIRM_69 || '-调整顺序'), CMSObj.afteredit(event));
		},
		detailpublish : function(event){
			publish(event.getIds(), 'detailPublish');
		},
		recallpublish : function(event){
			var host = event.getHost();
			var hostid = host.getId();
			var _sIds = event.getIds();
			var oParams = {
				FolderId : hostid,
				FolderType : host.getType()=="website"? 103:101
			};
			var sHtml = String.format("确定要{0}撤销发布{1}所选视频么？将{2}不可逆转{3}！",'<font color=\'red\' style=\'font-size:14px;\'>','</font>','<font color=\'red\' style=\'font-size:14px;\'>','</font>');
			Ext.Msg.confirm(sHtml,{
				yes : function(){
					wcm.domain.PublishAndPreviewMgr.publish(_sIds, 600, 'recallPublish',oParams,"wcm6_viewdocument");
				}
			});
		},
		directRecallpublish : function(event){
			var sHtml = String.format("确定要{0}撤销发布{1}所选文档及其所有引用文档么？将{2}不可逆转！{3}",'<font color=\'red\' style=\'font-size:14px;\'>','</font>','<font color=\'red\' style=\'font-size:14px;\'>','</font>');
			Ext.Msg.confirm(sHtml,{
				yes : function(){
					publish(event.getIds(), 'recallpublishall');	
				}
			})	
		},
		setright : function(event){
			var m_nDocumentObjType = 605;
			$openCenterWin(WCMConstants.WCM6_PATH + "auth/right_set.jsp?ObjType=" +
				m_nDocumentObjType + "&ObjId=" + event.getObjs().getPropertys("docId",0).join(),
				"document_right_set", 900, 600, "resizable=yes");
		},
		/*record: function(event) {
			var oPageParams = event.getContext();
			var host = event.getHost();
			var chnId = host.getType()==WCMConstants.OBJ_TYPE_CHANNEL?host.getId():0;
			var sFeature = 'location=no,resizable=no,menubar=no,scrollbars=yes,status=yes,titlebar=no,toolbar=no,top=0,left=0,border=0,width=850,height=640';
			window.open(WCMConstants.WCM6_PATH + "video/video_record.jsp?ChannelId=" + chnId, "_blank", sFeature);
	    },*/
		live: function(event) {
			var host = event.getHost();
			var hostId = host.getId();
			var sFeature = 'location=no,resizable=no,menubar=no,scrollbars=yes,status=yes,titlebar=no,toolbar=no,top=0,left=0,border=0,width=1000,height=900';
			window.open(WCMConstants.WCM6_PATH + "video/live_add.jsp?SiteId=" + hostId, "_blank", sFeature);
	    },
		multiupload: function(event) {
			var oPageParams = event.getContext();
			var host = event.getHost();
		//var chnId = oPageParams.host.objId;
			var chnId = host.getType()==WCMConstants.OBJ_TYPE_CHANNEL?host.getId():0;
			var sFeature = 'location=no,resizable=no,menubar=no,scrollbars=yes,status=yes,titlebar=no,toolbar=no,top=0,left=0,border=0,width=800,height=560';
			window.open(WCMConstants.WCM6_PATH + "video/video_batch_add.jsp?ChannelId=" + chnId, "_blank", sFeature);
	    },
		commentmgr : function(event){
			var docid = event.getObj().docId;
			var host = event.getHost();
			var hostid = host.getId();

			var oParams = Ext.apply({
				DocumentId : docid,
				ChannelId :hostid,
				SiteId : 0
			}, parseHost(event.getHost()));
			var sUrl = WCMConstants.WCM_ROOTPATH +'comment/comment_mgr.jsp?'
					+ $toQueryStr(oParams);
			$openMaxWin(sUrl);
		}

		});
})();
(function(){
	var pageObjMgr = wcm.domain.videoMgr;
	var reg = wcm.SysOpers.register;
	reg({
		key : 'upload',
		type : 'videoInChannel',
		desc : wcm.LANG.VIDEO_PROCESS_159 || '新建视频',
		title : wcm.LANG.VIDEO_PROCESS_108 || '上传新视频',
		rightIndex : 31,
		order : 8,
		fn : pageObjMgr['upload'],
		quickKey : 'N'
	});
	reg({
		key : 'upload',
		type : 'videoInSite',
		desc : wcm.LANG.VIDEO_PROCESS_159 || '新建视频',
		title : wcm.LANG.VIDEO_PROCESS_108 || '上传新视频',
		rightIndex : 31,
		order : 8,
		fn : pageObjMgr['upload'],
		quickKey : 'N'
	});
	reg({
		key : 'snap',
		type : 'video',
		desc : wcm.LANG.VIDEO_PROCESS_214 || '重新抓取缩略图',
		title : wcm.LANG.VIDEO_PROCESS_215 || '抓取更合适的缩略图',
		rightIndex : 32,
		order : 8,
		fn : pageObjMgr['snap']
	});
	
	reg({
		key : 'preview',
		type : 'video',
		desc : wcm.LANG.VIDEO_PROCESS_116 || '预览这个视频',
		title : wcm.LANG.VIDEO_PROCESS_116 || '预览这个视频',
		rightIndex : 38,
		order : 3,
		fn : pageObjMgr['preview'],
		quickKey : 'Y'
	});
	reg({
		key : 'basicpublish',
		type : 'video',
		desc : wcm.LANG.VIDEO_PROCESS_118 || '发布这个视频',
		title : wcm.LANG.VIDEO_PROCESS_118 || '发布这个视频',
		rightIndex : 39,
		order : 4,
		fn : pageObjMgr['basicpublish'],
		isVisible : function(event){
			var obj = event.getObj();
			var converting = obj.getPropertyAsInt('converting', 1);
			return converting == 1 || converting == 3;
		},
		quickKey : 'P'
	});
	reg({
		key : 'edit',
		type : 'video',
		desc : wcm.LANG.VIDEO_PROCESS_115 ||'修改这个视频',
		title : wcm.LANG.VIDEO_PROCESS_115 ||'修改这个视频',
		rightIndex : 32,
		order : 1,
		isVisible : function(event){
			var obj = event.getObj();
			var converting = obj.getPropertyAsInt('converting', 1);
			return converting == 1 || converting == 3;
		},
		fn : pageObjMgr['edit'],
		quickKey : 'M'
	});
	reg({
		key : 'changestatus',
		type : 'video',
		desc : wcm.LANG.VIDEO_PROCESS_125 ||'改变这个视频状态',
		title : wcm.LANG.VIDEO_PROCESS_125 ||'改变这个视频状态',
		rightIndex : 35,
		order : 8,
		fn : pageObjMgr['changestatus']
	});
/*	reg({
		key : 'copy',
		type : 'video',
		desc : wcm.LANG.VIDEO_PROCESS_120 ||'复制这个视频到',
		title : wcm.LANG.VIDEO_PROCESS_120 ||'复制这个视频到',
		rightIndex : 34,
		order : 4,
		fn : pageObjMgr['copy']
	});
	reg({
		key : 'quote',
		type : 'video',
		desc : wcm.LANG.VIDEO_PROCESS_121 ||'引用这个视频到',
		title : wcm.LANG.VIDEO_PROCESS_121 ||'引用这个视频到',
		rightIndex : 34,
		order : 5,
		fn : pageObjMgr['quote']
	});*/
	reg({
		key : 'move',
		type : 'video',
		desc : wcm.LANG.VIDEO_PROCESS_122 ||'移动这个视频到',
		title : wcm.LANG.VIDEO_PROCESS_122 ||'移动这个视频到',
		rightIndex : 33,
		order : 6,
		fn : pageObjMgr['move']
	});
/*	reg({
		key : 'copy',
		type : 'videos',
		desc : wcm.LANG.VIDEO_PROCESS_144 ||'复制这些视频到',
		title : wcm.LANG.VIDEO_PROCESS_144 ||'复制这些视频到',
		rightIndex : 34,
		order : 4,
		fn : pageObjMgr['copy']
	});
	reg({
		key : 'quote',
		type : 'videos',
		desc : wcm.LANG.VIDEO_PROCESS_146 ||'引用这些视频到',
		title : wcm.LANG.VIDEO_PROCESS_146 ||'引用这些视频到',
		rightIndex : 34,
		order : 5,
		fn : pageObjMgr['quote']
	});*/
	reg({
		key : 'move',
		type : 'videos',
		desc : wcm.LANG.VIDEO_PROCESS_145 ||'移动这些视频到',
		title : wcm.LANG.VIDEO_PROCESS_145 ||'移动这些视频到',
		rightIndex : 33,
		order : 6,
		fn : pageObjMgr['move']
	});
	reg({
		key : 'trash',
		type : 'video',
		desc : wcm.LANG.VIDEO_PROCESS_123 ||'将视频放入废稿箱',
		title : wcm.LANG.VIDEO_PROCESS_124 ||'将这个视频放入废稿箱',
		rightIndex : 33,
		order : 7,
		fn : pageObjMgr['trash'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'trash',
		type : 'videos',
		desc : wcm.LANG.VIDEO_PROCESS_123 ||'将视频放入废稿箱',
		title : wcm.LANG.VIDEO_PROCESS_123 ||'将视频放入废稿箱',
		rightIndex : 33,
		order : 3,
		fn : pageObjMgr['trash'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'segment',
		type : 'video',
		desc : wcm.LANG.VIDEO_PROCESS_216 || '标引与切割',
		title : wcm.LANG.VIDEO_PROCESS_217 || '对这个视频进行标引和切割',
		rightIndex : 31,
		order : 8,
		isVisible : function(event) {		
			var actualTop = $MsgCenter.getActualTop();
	        if(actualTop.autoclip==true){
	            return true;
	        }
	        return false;
        },
		fn : pageObjMgr['segment']
	});
	reg({
		key : 'detailpublish',
		type : 'video',
		desc : wcm.LANG.VIDEO_PROCESS_127 ||'仅发布这个视频细览',
		title : wcm.LANG.VIDEO_PROCESS_128 ||'仅发布这个视频细览，仅重新生成这个视频的细览页面',
		rightIndex : 39,
		order : 9,
		isVisible : function(event){
			var obj = event.getObj();
			var converting = obj.getPropertyAsInt('converting', 1);
			return converting == 1 || converting == 3;
		},
		fn : pageObjMgr['detailpublish']
	});
	reg({
		key : 'directpublish',
		type : 'video',
		desc : wcm.LANG.VIDEO_PROCESS_229 ||'直接发布这个视频',
		title : wcm.LANG.VIDEO_PROCESS_226 ||'发布这个视频,同时发布此视频的所有引用视频',
		rightIndex : 39,
		order : 11,
		isVisible : function(event){
			var obj = event.getObj();
			var converting = obj.getPropertyAsInt('converting', 1);
			return converting == 1 || converting == 3;
		},

		fn : pageObjMgr['directpublish']
	});
	reg({
	    key : 'recallpublish',
		type : 'video',
		desc : wcm.LANG.VIDEO_PROCESS_129 || '撤销发布这个视频',
		title : wcm.LANG.VIDEO_PROCESS_130 || '撤销发布这个视频,撤回已发布目录或页面',
		rightIndex : 39,
		order : 18,
		fn : pageObjMgr['recallpublish']
	});
	reg({
		key : 'directRecallpublish',
		type : 'video',
		desc : wcm.LANG.VIDEO_PROCESS_230 ||'直接撤销发布这个视频',
		title : wcm.LANG.VIDEO_PROCESS_231 ||'撤销发布这个视频，同步撤销这个视频所有的引用视频',
		rightIndex : 39,
		order : 13,
		fn : pageObjMgr['directRecallpublish']
	});
	reg({
		key : 'setright',
		type : 'video',
		desc : wcm.LANG.VIDEO_PROCESS_126 || '设置这个视频的权限',
		title : wcm.LANG.VIDEO_PROCESS_126 || '设置这个视频的权限...',
		rightIndex : 61,
		order : 20,
		fn : pageObjMgr['setright']
	});
	reg({
		key : 'preview',
		type : 'videos',
		desc : wcm.LANG.VIDEO_PROCESS_140 || '预览这些视频',
		title : wcm.LANG.VIDEO_PROCESS_140 || '预览这些视频',
		rightIndex : 38,
		order : 13,
		fn : pageObjMgr['preview'],
		quickKey : 'Y'
	});
	reg({
		key : 'basicpublish',
		type : 'videos',
		desc : wcm.LANG.VIDEO_PROCESS_142 || '发布这些视频',
		title : wcm.LANG.VIDEO_PROCESS_142 || '发布这些视频',
		rightIndex : 39,
		order : 14,
		fn : pageObjMgr['basicpublish'],
		isVisible : function(event){
			var objs = event.getObjs();
			for(var i=1;i<objs.size();i++){
				var obj = objs.getAt(i);
				var converting = obj.getPropertyAsInt('converting', 1);
				if(converting == 0){
					return false;
				}
			}
			return converting == 1 || converting == 3;
		},
		quickKey : 'P'

	});
	reg({
		key : 'directpublish',
		type : 'videos',
		desc : wcm.LANG.VIDEO_PROCESS_227 ||'直接发布这些视频',
		title : wcm.LANG.VIDEO_PROCESS_228 ||'发布这些视频，同步发布这些视频所有的引用视频',
		rightIndex : 39,
		order : 10,
		isVisible : function(event){
			var objs = event.getObjs();
			for(var i=1;i<objs.size();i++){
				var obj = objs.getAt(i);
				var converting = obj.getPropertyAsInt('converting', 1);
				if(converting == 0){
					return false;
				}
			}
			return converting == 1 || converting == 3;
		},

		fn : pageObjMgr['directpublish']
	});
	reg({
		key : 'recallpublish',
		type : 'videos',
		desc : wcm.LANG.VIDEO_PROCESS_151 || '撤销发布这些视频',
		title : wcm.LANG.VIDEO_PROCESS_152 || '撤销发布这些视频,撤回已发布目录或页面',
		rightIndex : 39,
		order : 19,
		fn : pageObjMgr['recallpublish']
	});
	reg({
		key : 'directRecallpublish',
		type : 'videos',
		desc : wcm.LANG.VIDEO_PROCESS_232 ||'直接撤销发布这些视频',
		title : wcm.LANG.VIDEO_PROCESS_233 ||'撤销发布这些视频，同步撤销这些视频所有的引用视频',
		rightIndex : 39,
		order : 12,
		fn : pageObjMgr['directRecallpublish']
	});
	reg({
		key : 'setright',
		type : 'videos',
		desc : wcm.LANG.VIDEO_PROCESS_148 || '设置这些视频的权限',
		title : wcm.LANG.VIDEO_PROCESS_148 || '设置这些视频的权限...',
		rightIndex : 61,
		order : 21,
		fn : pageObjMgr['setright']
	});
	reg({
		key : 'changestatus',
		type : 'videos',
		desc : wcm.LANG.VIDEO_PROCESS_147 || '改变这些视频的状态',
		title : wcm.LANG.VIDEO_PROCESS_147 || '改变这些视频的状态',
		rightIndex : 35,
		order : 17,
		fn : pageObjMgr['changestatus']
	});
	reg({
		key : 'detailpublish',
		type : 'videos',
		desc : wcm.LANG.VIDEO_PROCESS_149 ||'仅发布这些视频细览',
		title : wcm.LANG.VIDEO_PROCESS_150 ||'仅发布这些视频细览，仅重新生成这些文档的细览页面',
		rightIndex : 39,
		order : 9,
		isVisible : function(event){
			var objs = event.getObjs();
			for(var i=1;i<objs.size();i++){
				var obj = objs.getAt(i);
				var converting = obj.getPropertyAsInt('converting', 1);
				if(converting == 0){
					return false;
				}
			}
		},
		fn : pageObjMgr['detailpublish']
	});
	/*reg({
		key : 'record',
		type : 'videoInChannel',
		desc : wcm.LANG.VIDEO_PROCESS_218 ||'新建录制视频',
		title : wcm.LANG.VIDEO_PROCESS_218 ||'新建录制视频',
		rightIndex : 39,
		order : 9,
		fn : pageObjMgr['record']
	});*/
/*	reg({
		key : 'live',
		type : 'videoInSite',
		desc : wcm.LANG.VIDEO_PROCESS_220 ||'新建视频直播',
		title : wcm.LANG.VIDEO_PROCESS_220 ||'新建视频直播',
		rightIndex : 39,
		order : 9,
		fn : pageObjMgr['live']
	});*/
	reg({
		key : 'multiupload',
		type : 'videoInChannel',
		desc : wcm.LANG.VIDEO_PROCESS_110 ||'批量新建视频',
		title : wcm.LANG.VIDEO_PROCESS_110 ||'批量新建视频...',
		rightIndex : 31,
		order : 9,
		fn : pageObjMgr['multiupload']
	});
	reg({
		key : 'docpositionset',
		type : 'video',
		desc : wcm.LANG.PHOTO_CONFIRM_90 || '调整顺序',
		title : wcm.LANG.PHOTO_CONFIRM_90 || '调整顺序',
		rightIndex : 62,
		order : 24,
		fn : pageObjMgr['docpositionset'],
		isVisible : function(event){
			if(event.getHost().getType() == WCMConstants.OBJ_TYPE_CHANNEL)
				return true;
			return false;
		}
	});
	reg({
		key : 'commentmgr',
		type : 'video',
		desc : wcm.LANG.VIDEO_PROCESS_221 ||'管理评论',
		title : wcm.LANG.VIDEO_PROCESS_222 ||'管理视频的评论',
		rightIndex : 8,
		order : 40,
		fn : pageObjMgr['commentmgr'],
		isVisible : function(event){
			try{
				return $MsgCenter.getActualTop().g_IsRegister['comment'];
			}catch(err){
				return false;
			}
		}
	});
})();