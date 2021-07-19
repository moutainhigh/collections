//图片操作信息和Mgr定义
Ext.ns('wcm.domain.photoMgr');
(function(){
	var m_oMgr = wcm.domain.photoMgr;
	var m_nDocumentObjType = 605;
	function getHelper(){
		return new com.trs.web2frame.BasicDataHelper();
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
	function uploadCallback1(fpInfo){
		setTimeout(function(){
			FloatPanel.open(fpInfo.src,
					fpInfo.title,uploadCallback);
		}, 200);
		return false;
	}
	function uploadCallback(fpInfo){
		FloatPanel.close();
		setTimeout(function(){
			FloatPanel.open(fpInfo.src,
					fpInfo.title);
		}, 200);
		return false;
	}
	function doOptionsAfterDisplayInfo(_params, _fDoAfterDisp){
		//alert(Ext.toSource(_params));
		var DIALOG_PHOTO_INFO = 'photo_info_dialog';
		var aTop = (top.actualTop||top);
		var link = WCMConstants.WCM6_PATH + 'photo/photo_info.jsp';
		aTop.m_eDocumentInfo = wcm.CrashBoarder.get(DIALOG_PHOTO_INFO).show({
			title : wcm.LANG.PHOTO_CONFIRM_63 || '系统提示信息',
			src : link,
			width: '500px',
			height: '220px',
			reloadable : true,
			params : _params,
			maskable : true,
			callback : _fDoAfterDisp
		});
	}
	function registerOnFinish(_title,_param,_sparam,event){
		var mytop = top || top.actualTop;
		mytop.m_eSelector = wcm.CrashBoarder.get(DIALOG_IMAGEKIND_SELECTOR).show({
			title : _title,
			src : WCMConstants.WCM6_PATH +'photo/channel_select.html',
			width: '250px',
			height: '300px',
			reloadable : false,
			params : _param,
			maskable : true,
			callback : function(_args){
				if(!_args.ids || _args.ids.length == 0){
					//Ext.Msg.alert(wcm.LANG.PHOTO_CONFIRM_54 || "没有选择分类");
					return false;
				}
				if(_args.mode == "radio"){						
					if(_args.ids && m_nCurrId != _args.ids){
						var oPostData = Object.extend(_sparam,{ToChannelId:_args.ids});					
						BasicDataHelper.call("wcm6_viewdocument","move",oPostData,true,function(_transport,_json){
							var r = $v(_json,"Reports.Is_Success");
							_json.REPORTS.TITLE = wcm.LANG.PHOTO_CONFIRM_129 || "移动图片";								
							Ext.Msg.report(_json,wcm.LANG.PHOTO_CONFIRM_131 || '图片移动结果',func);
							event.getObjs().afterdelete();
						});
					}				
				}else{									
					if(_args.ids && _args.ids.length > 0 && m_nCurrId != _args.ids){
						var oPostData = Object.extend(_sparam,{ToChannelIds:_args.ids});
						var func = function(){
							FloatPanel.close();
						};
						BasicDataHelper.call("wcm6_viewdocument","quote",oPostData,true,function(_transport,_json){
							_json.REPORTS.TITLE = wcm.LANG.PHOTO_CONFIRM_130 || "引用图片";
							Ext.Msg.report(_json,wcm.LANG.PHOTO_CONFIRM_132 || '图片引用结果',func);
							event.getObjs().afteredit();
						});
					}
				}
			}
		});
	}
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
	function $openMaxWin(_sUrl, _sName, _bResizable){
		var nWidth	= window.screen.width - 12;
		var nHeight = window.screen.height - 60;
		var nLeft	= 0;
		var nTop	= 0;
		var sName	= _sName || "";

		var oWin = window.open(_sUrl, sName, "resizable=" + (_bResizable == true ? "yes" : "no") + ",top=" + nTop + ",left=" + nLeft + ",menubar =no,toolbar =no,width=" + nWidth + ",height=" + nHeight + ",scrollbars=yes,location =no,titlebar=no");
		if(oWin)oWin.focus();
	}
	function __openPreviewPage(_sIds, _iObjectType,_extraParams){
		window.open('/wcm/app/preview/index.htm?objectType='+ _iObjectType + '&objectids=' + _sIds + '&'+$toQueryStr(_extraParams||{}), 'preview_page');
	}
	//
	var m_nCurrId = 0;

	var DIALOG_IMAGEKIND_SELECTOR = "Dialog_ImageKind_Selector";
	ChannelSelector = Class.create("ChannelSelector");

	ChannelSelector.prototype = {
		initialize : function(){		
		},
		selectMainKind : function(_param,event){		
			var pContext = {mode:"radio",SelectedIds:event.getHost().getId(),CurrId:event.getHost().getId(),Type:event.getHost().getType()};
			registerOnFinish(wcm.LANG.PHOTO_CONFIRM_52 || "选择主分类",pContext,_param,event);
		},
		selectOtherKinds : function(_param,event){
			var pContext = {mode:"multi",SelectedIds:event.getHost().getId(),CurrId:event.getHost().getId(),Type:event.getHost().getType()};
			registerOnFinish(wcm.LANG.PHOTO_CONFIRM_53 || "选择其它分类",pContext,_param,event);
		},
		setParams : function(_params){
			Object.extend(this.m_oParams,_params ||{});
		}
	};

	$channelSelector = new ChannelSelector();


	Ext.apply(wcm.domain.photoMgr, {
		/**
		 * @param : wcm.CMSObjEvent event
		 */
		//*
		upload : function(event){
			var oPageParams = event.getContext();
			var host = event.getHost();
			var hostid = host.getId();
			Object.extend(oPageParams,host.getType()=="website"?{"siteid" : hostid}:{"channelid" : hostid});
			FloatPanel.open(WCMConstants.WCM6_PATH +
					'photo/photo_upload.jsp?' + $toQueryStr(oPageParams),
					wcm.LANG.PHOTO_CONFIRM_61 || '上传图片', uploadCallback);
		},
		edit : function(event){
			var oPageParams = event.getContext();
			var host = event.getHost();
			var hostid = host.getId();
			var docid = event.getObjs().getAt(0).getPropertyAsInt("docId", 0);
			Object.extend(oPageParams,host.getType()=="website"?{"siteid" : hostid}:{"channelid" : hostid});
			Object.extend(oPageParams,{"DocId":docid});		
			FloatPanel.open(WCMConstants.WCM6_PATH +"photo/photodoc_edit.jsp?"+$toQueryStr(oPageParams),wcm.LANG.PHOTO_CONFIRM_67 || "编辑图片信息",CMSObj.afteredit(event));
		},
		"delete" : function(event){
			var oPageParams = event.getContext();
			//alert(Ext.toSource(oPageParams));
			var _sDocIds = event.getIds();
			_sDocIds = _sDocIds + '';
			var nCount = (_sDocIds.indexOf(',') == -1) ? 1 : _sDocIds.split(',').length;
			var sHint = (nCount==1)?'':' '+nCount+' ';
			var browserEvent = event.browserEvent;
			var bDrop = !!(browserEvent && 
				browserEvent.type=='keydown' && browserEvent.shiftKey);
			var params = {
				objectids: _sDocIds,
				operation: bDrop ? '_forcedelete' : '_trash'
			}
			doOptionsAfterDisplayInfo(params, function(){
				var aTop = (top.actualTop||top);
				BasicDataHelper.call("wcm6_viewdocument", 'delete', Object.extend(oPageParams,{"ObjectIds": _sDocIds, "drop": bDrop}), true, 
					function(){
						event.getObjs().afterdelete();
					}
				);
			}.bind(this));
		},
		importsysphoto : function(event){
			var oPageParams = {};
			var host = event.getHost();
			var hostid = host.getId();
			Object.extend(oPageParams,host.getType()=="website"?{"siteid" : hostid}:{"channelid" : hostid});
			FloatPanel.open(WCMConstants.WCM6_PATH +
					'photo/photo_importsyspics.jsp?' + $toQueryStr(oPageParams),
					wcm.LANG.PHOTO_CONFIRM_79 || '导入系统图片', uploadCallback);
		},
		importphotos : function(event){
			var oPageParams = {};
			var host = event.getHost();
			var hostid = host.getId();
			Object.extend(oPageParams,host.getType()=="website"?{"siteid" : hostid}:{"channelid" : hostid});
			if(!oPageParams.channelid){
				FloatPanel.open(WCMConstants.WCM6_PATH + 'photo/photos_siteimport_step1.html?' + $toQueryStr(oPageParams), wcm.LANG.PHOTO_CONFIRM_112 || '选择要批量上传图片的栏目', uploadCallback1);
				return;
			}
			FloatPanel.open(WCMConstants.WCM6_PATH +'photo/photos_import.jsp?' + $toQueryStr(oPageParams), wcm.LANG.PHOTO_CONFIRM_113 || '批量上传图片',
				function(){
						event.getObjs().afterdelete();
					});
		},
		preview : function(event){
			var host = event.getHost();
			var hostid = host.getId();
			var oParams = {
				FolderId : hostid,
				FolderType : host.getType()=="website"? 103:101,
				'isPhoto':true
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
				'isPhoto':true
			});
			BasicDataHelper.call("wcm6_viewdocument", "basicpublish", postData, true, function(_transport,_json){
				wcm.domain.PublishAndPreviewMgr.doAfterPublish(postData, "detailpublish",_transport,_json);
			}.bind(this));
		},
		detailpublish : function(event){
			var oPageParams = event.getContext();
			var _sIds = event.getIds();
			var postData = Object.extend({},{
				'ObjectIds' : _sIds,
				'isPhoto':true
			});
			BasicDataHelper.call("wcm6_viewdocument", "detailpublish", postData, true, function(_transport,_json){
				wcm.domain.PublishAndPreviewMgr.doAfterPublish(postData, "detailpublish",_transport,_json);
			}.bind(this));
		},
		directpublish : function(event){
			var oPageParams = event.getContext();
			var _sIds = event.getIds();
			var postData = Object.extend({},{
				'ObjectIds' : _sIds,
				'isPhoto':true
			});
			BasicDataHelper.call("wcm6_viewdocument", "directpublish", postData, true, function(_transport,_json){
				wcm.domain.PublishAndPreviewMgr.doAfterPublish(postData, "detailpublish",_transport,_json);
			}.bind(this));
		},
		quote : function(event){
			var oPageParams = event.getContext();
			var _sDocIds = event.getIds();
			var host = event.getHost();
			var currId = host.getType()=="website" ? 0:host.getId();
			var param ={ObjectIds:_sDocIds,channelids:currId,isPhoto:true};
			$channelSelector.setParams(param);
			$channelSelector.selectOtherKinds(param,event);		
		},
		move : function(event){		
			var oPageParams = event.getContext();
			var _sDocIds = event.getIds();
			var host = event.getHost();
			var param ={ObjectIds:_sDocIds,FromChannelId:currId,isPhoto:true};
			var currId = (host.getType()=="website"?0:host.getId());
			$channelSelector.setParams(param);
			$channelSelector.selectMainKind(param,event);
		},
		docpositionset : function(event){
			var oPageParams = event.getContext();
			var host = event.getHost();
			var hostid = host.getId();
			var objType = event.getObjs().getAt(0).objType;
			var docid = event.getObjs().getAt(0).getPropertyAsInt("docId", 0);
			Object.extend(oPageParams,host.getType()=="website"?{"siteid" : hostid}:{"channelid" : hostid});
			Object.extend(oPageParams,{"DocumentId":docid,"DocType":objType});
			FloatPanel.open(WCMConstants.WCM6_PATH + 'document/document_position_set.jsp?' + $toQueryStr(oPageParams), (objType.trim()=='photo'?(wcm.LANG.PHOTO_CONFIRM_8 || '图片'):(wcm.LANG.PHOTO_CONFIRM_68 || '文档')) +
			(wcm.LANG.PHOTO_CONFIRM_69 || '-调整顺序'), CMSObj.afteredit(event));
		},
		commentmgr : function(event){
			var docid = event.getObjs().getAt(0).getPropertyAsInt("docId", 0);
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
		},
		recallpublish : function(event){
			var host = event.getHost();
			var hostid = host.getId();
			var _sIds = event.getIds();
			var oParams = {
				FolderId : hostid,
				FolderType : host.getType()=="website"? 103:101
			};
			var sHtml = (wcm.LANG.PHOTO_CONFIRM_151 ||"确定要<font color=\'red\' style=\'font-size:14px;\'>撤销发布</font>所选图片么？将<font color=\'red\' style=\'font-size:14px;\'>不可逆转</font>");
			Ext.Msg.confirm(sHtml,{
				yes : function(){
					wcm.domain.PublishAndPreviewMgr.publish(_sIds, 600, 'recallPublish',oParams,"wcm6_viewdocument");			
				}
			});
		},
		setright : function(event){
			$openCenterWin(WCMConstants.WCM6_PATH + "auth/right_set.jsp?ObjType=" +
				m_nDocumentObjType + "&ObjId=" + event.getObjs().getPropertys("docId",0).join(),
				"document_right_set", 900, 600, "resizable=yes");
		},
		changestatus : function(event){
			var oPageParams = event.getContext();
			var host = event.getHost();
			var hostid = host.getId();
			var sId = event.getIds().join();
			var docid = event.getObjs().getAt(0).getPropertyAsInt("docId", 0);
			Object.extend(oPageParams,{"DocumentId":docid,"ObjectIds":sId,"IsPhoto":true,'ChannelIds':(oPageParams,host.getType()=="website"?0:hostid)});
			FloatPanel.open(WCMConstants.WCM6_PATH +'document/change_status.jsp?' + $toQueryStr(oPageParams), wcm.LANG.PHOTO_CONFIRM_70 || '图片-改变状态', CMSObj.afteredit(event));
		},
		downLoadOrigin : function(event){
			var oparam = {
				PhotoId : event.getObjs().getAt(0).getPropertyAsInt("docId", 0)
			}
			BasicDataHelper.JspRequest(WCMConstants.WCM6_PATH+'photo/photo_download.jsp',oparam,false,function(transport){
				var sFileUrl = transport.responseText;
				var frm = $MsgCenter.getActualTop().$('iframe4download');
				if(frm==null){
					frm = $MsgCenter.getActualTop().document.createElement('IFRAME');
					frm.id = "iframe4download";
					frm.style.display = 'none';
					$MsgCenter.getActualTop().document.body.appendChild(frm);
				}
				sFileUrl = WCMConstants.WCM_ROOTPATH +"file/read_file.jsp?DownName=DOCUMENT&FileName=" + sFileUrl;
				frm.src = sFileUrl;
			});
		}
		//*/
		//type here
	});
})();
(function(){
	var pageObjMgr = wcm.domain.photoMgr;
	var reg = wcm.SysOpers.register;

	reg({
		key : 'edit',
		type : 'photo',
		desc : wcm.LANG.PHOTO_CONFIRM_71 || '编辑这幅图片',
		title : wcm.LANG.PHOTO_CONFIRM_71 || '编辑这幅图片',
		rightIndex : 32,
		order : 1,
		fn : pageObjMgr['edit'],
		quickKey : 'E'
	});
	reg({
		key : 'delete',
		type : 'photo',
		desc : wcm.LANG.PHOTO_CONFIRM_72 || '删除这幅图片',
		title : wcm.LANG.PHOTO_CONFIRM_72 || '删除这幅图片',
		rightIndex : 33,
		order : 2,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'preview',
		type : 'photo',
		desc : wcm.LANG.PHOTO_CONFIRM_73 || '预览这幅图片',
		title : wcm.LANG.PHOTO_CONFIRM_73 || '预览这幅图片',
		rightIndex : 38,
		order : 3,
		fn : pageObjMgr['preview'],
		quickKey : 'Y'
	});
	reg({
		key : 'basicpublish',
		type : 'photo',
		desc : wcm.LANG.PHOTO_CONFIRM_74 || '发布这幅图片',
		title : wcm.LANG.PHOTO_CONFIRM_74 || '发布这幅图片',
		rightIndex : 39,
		order : 4,
		fn : pageObjMgr['basicpublish'],
		quickKey : 'P'
	});
	reg({
		key : 'move',
		type : 'photo',
		desc : wcm.LANG.PHOTO_CONFIRM_75 || '重新分类',
		title : wcm.LANG.PHOTO_CONFIRM_75 || '重新分类',
		rightIndex : 33,
		order : 5,
		fn : pageObjMgr['move']
	});
	reg({
		key : 'quote',
		type : 'photo',
		desc : wcm.LANG.PHOTO_CONFIRM_76 || '增加分类',
		title : wcm.LANG.PHOTO_CONFIRM_76 || '增加分类',
		rightIndex : 34,
		order : 6,
		fn : pageObjMgr['quote']
	});
	reg({
		key : 'changestatus',
		type : 'photo',
		desc : wcm.LANG.PHOTO_CONFIRM_77 || '改变这幅图片的状态',
		title : wcm.LANG.PHOTO_CONFIRM_77 || '改变这幅图片的状态',
		rightIndex : 35,
		order : 7,
		fn : pageObjMgr['changestatus']
	});
	reg({
		key : 'upload',
		type : 'photoInChannel',
		desc : wcm.LANG.PHOTO_CONFIRM_78 || '上传新图片',
		title : wcm.LANG.PHOTO_CONFIRM_78 || '上传新图片...',
		rightIndex : 31,
		order : 8,
		fn : pageObjMgr['upload'],
		quickKey : 'N'
	});
	reg({
		key : 'importsysphoto',
		type : 'photoInChannel',
		desc : wcm.LANG.PHOTO_CONFIRM_79 || '导入系统图片',
		title : wcm.LANG.PHOTO_CONFIRM_79 || '导入系统图片...',
		rightIndex : 31,
		order : 9,
		fn : pageObjMgr['importsysphoto']
	});
	reg({
		key : 'importphotos',
		type : 'photoInChannel',
		desc : wcm.LANG.PHOTO_CONFIRM_111 || '批量上传图片',
		title : wcm.LANG.PHOTO_CONFIRM_111 || '批量上传图片...',
		rightIndex : 31,
		order : 9,
		fn : pageObjMgr['importphotos']
	});
	reg({
		key : 'upload',
		type : 'photoInSite',
		desc : wcm.LANG.PHOTO_CONFIRM_78 || '上传新图片',
		title : wcm.LANG.PHOTO_CONFIRM_78 || '上传新图片...',
		rightIndex : 31,
		order : 10,
		fn : pageObjMgr['upload'],
		quickKey : 'N'
	});
	reg({
		key : 'importsysphoto',
		type : 'photoInSite',
		desc : wcm.LANG.PHOTO_CONFIRM_79 || '导入系统图片',
		title : wcm.LANG.PHOTO_CONFIRM_79 || '导入系统图片...',
		rightIndex : 31,
		order : 11,
		fn : pageObjMgr['importsysphoto']
	});
	reg({
		key : 'importphotos',
		type : 'photoInSite',
		desc : wcm.LANG.PHOTO_CONFIRM_111 || '批量上传图片',
		title : wcm.LANG.PHOTO_CONFIRM_111 || '批量上传图片...',
		rightIndex : 31,
		order : 11,
		fn : pageObjMgr['importphotos']
	});
	reg({
		key : 'delete',
		type : 'photos',
		desc : wcm.LANG.PHOTO_CONFIRM_80 || '删除这些图片',
		title : wcm.LANG.PHOTO_CONFIRM_80 || '删除这些图片',
		rightIndex : 33,
		order : 12,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'preview',
		type : 'photos',
		desc : wcm.LANG.PHOTO_CONFIRM_81 || '预览这些图片',
		title : wcm.LANG.PHOTO_CONFIRM_81 || '预览这些图片',
		rightIndex : 38,
		order : 13,
		fn : pageObjMgr['preview'],
		quickKey : 'Y'
	});
	reg({
		key : 'basicpublish',
		type : 'photos',
		desc : wcm.LANG.PHOTO_CONFIRM_82 || '发布这些图片',
		title : wcm.LANG.PHOTO_CONFIRM_82 || '发布这些图片',
		rightIndex : 39,
		order : 14,
		fn : pageObjMgr['basicpublish'],
		quickKey : 'P'
	});
	reg({
		key : 'move',
		type : 'photos',
		desc : wcm.LANG.PHOTO_CONFIRM_75 || '重新分类',
		title : wcm.LANG.PHOTO_CONFIRM_75 || '重新分类',
		rightIndex : 33,
		order : 15,
		fn : pageObjMgr['move']
	});
	reg({
		key : 'quote',
		type : 'photos',
		desc : wcm.LANG.PHOTO_CONFIRM_76 || '增加分类',
		title : wcm.LANG.PHOTO_CONFIRM_76 || '增加分类',
		rightIndex : 34,
		order : 16,
		fn : pageObjMgr['quote']
	});
	reg({
		key : 'changestatus',
		type : 'photos',
		desc : wcm.LANG.PHOTO_CONFIRM_83 || '改变这些图片的状态',
		title : wcm.LANG.PHOTO_CONFIRM_83 || '改变这些图片的状态',
		rightIndex : 35,
		order : 17,
		fn : pageObjMgr['changestatus']
	});
	reg({
		key : 'detailpublish',
		type : 'photo',
		desc : wcm.LANG.PHOTO_CONFIRM_135 ||'仅发布这幅图片细览',
		title : '仅发布这幅图片细览...',
		rightIndex : 39,
		order : 18,
		fn : pageObjMgr['detailpublish']
	});
	reg({
		key : 'directpublish',
		type : 'photo',
		desc : wcm.LANG.PHOTO_CONFIRM_160 ||'直接发布这幅图片',
		title : wcm.LANG.PHOTO_CONFIRM_161 ||'发布这幅图片,同时发布此图片的所有引用图片',
		rightIndex : 39,
		order : 18,
		fn : pageObjMgr['directpublish']
	});
	reg({
		key : 'detailpublish',
		type : 'photos',
		desc : wcm.LANG.PHOTO_CONFIRM_136 ||'仅发布这些图片细览',
		title : wcm.LANG.PHOTO_CONFIRM_136 ||'仅发布这些图片细览...',
		rightIndex : 39,
		order : 19,
		fn : pageObjMgr['detailpublish']
	});
	reg({
		key : 'directpublish',
		type : 'photos',
		desc : wcm.LANG.PHOTO_CONFIRM_162 ||'直接发布这些图片',
		title : wcm.LANG.PHOTO_CONFIRM_163 ||'发布这些图片,同时发布这些图片的所有引用图片',
		rightIndex : 39,
		order : 19,
		fn : pageObjMgr['directpublish']
	});
	reg({
		key : 'recallpublish',
		type : 'photo',
		desc : wcm.LANG.PHOTO_CONFIRM_84 || '撤销发布这幅图片',
		title : wcm.LANG.PHOTO_CONFIRM_85 || '撤销发布这幅图片,撤回已发布目录或页面',
		rightIndex : 39,
		order : 20,
		fn : pageObjMgr['recallpublish']
	});
	reg({
		key : 'recallpublish',
		type : 'photos',
		desc : wcm.LANG.PHOTO_CONFIRM_86 || '撤销发布这些图片',
		title : wcm.LANG.PHOTO_CONFIRM_87 || '撤销发布这些图片,撤回已发布目录或页面',
		rightIndex : 39,
		order : 21,
		fn : pageObjMgr['recallpublish']
	});
	reg({
		key : 'setright',
		type : 'photo',
		desc : wcm.LANG.PHOTO_CONFIRM_88 || '设置这幅图片的权限',
		title :'设置这幅图片的权限...',
		rightIndex : 61,
		order : 22,
		fn : pageObjMgr['setright']
	});
	reg({
		key : 'setright',
		type : 'photos',
		desc : wcm.LANG.PHOTO_CONFIRM_89 || '设置这些图片的权限',
		title : wcm.LANG.PHOTO_CONFIRM_89 || '设置这些图片的权限...',
		rightIndex : 61,
		order : 23,
		fn : pageObjMgr['setright']
	});
	reg({
		key : 'docpositionset',
		type : 'photo',
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
		type : 'photo',
		desc : wcm.LANG.PHOTO_CONFIRM_133 ||'管理评论',
		title : wcm.LANG.PHOTO_CONFIRM_134 ||'管理图片的评论',
		rightIndex : 8,
		order : 25,
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
		key : 'downLoadOrigin',
		type : 'photo',
		desc : wcm.LANG.PHOTO_CONFIRM_169 ||'下载原图',
		title : wcm.LANG.PHOTO_CONFIRM_169 ||'下载原图',
		rightIndex : 32,
		order : 26,
		fn : pageObjMgr['downLoadOrigin']
	});
})();
