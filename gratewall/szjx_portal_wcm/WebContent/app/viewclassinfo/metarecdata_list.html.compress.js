//资源操作信息和Mgr定义
Ext.ns('wcm.domain.MetaViewDataMgr');
(function(){
	var m_oMgr = wcm.domain.MetaViewDataMgr;
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
	function _addEdit(_id,event){				
		var nObjId = _id || 0;		
		var sTitle = (nObjId == 0)?(wcm.LANG.METAVIEWDATA_77 || "新建"):(wcm.LANG.METAVIEWDATA_106 || "修改");
		sTitle += (wcm.LANG.METAVIEWDATA_108 || "记录");
		var contextParams = event.getContext().params;
		var nViewId = event.getObj().getPropertyAsInt("viewid",0)||contextParams.VIEWID||0;
		var oParams = {
			ObjectId:nObjId,
			ChannelId:event.getHost().getId(),
			ChnlDocId:event.getObj().getPropertyAsInt("recid",0),
			FlowDocId:contextParams.FlowDocId || 0,
			ViewId:nViewId
		};
		$openMaxWin(WCMConstants.WCM6_PATH + './application/' + nViewId + '/metaviewdata_addedit.jsp?' + $toQueryStr2(oParams));
	}
	Ext.apply(wcm.domain.MetaViewDataMgr, {
		/**
		 * @param : wcm.CMSObjEvent event
		 */
		view : function(event){
			var _objId = event.getObj().getPropertyAsInt("docid",0);
			var nViewId = event.getContext().params["VIEWID"];
			var urlParams = "?objectId=" + _objId;
			var url = WCMConstants.WCM6_PATH + "application/" + nViewId + "/viewdata_detail.jsp" + urlParams;
			$openMaxWin(url);
		},
		docpositionset : function(event){
			//alert(Ext.toSource(event));
			var oPageParams = {};
			var host = event.getHost();
			var hostid = host.getId();
			var docid = event.getObj().getPropertyAsInt("docid",0);
			Object.extend(oPageParams,host.getType()=="website"?{"siteid" : hostid}:{"channelid" : hostid});
			Object.extend(oPageParams,{"DocumentId":docid});
			FloatPanel.open(WCMConstants.WCM6_PATH + 'metaviewdata/record_position_set.jsp?' + $toQueryStr(oPageParams), (wcm.LANG.METAVIEWDATA_25 || '记录—改变位置到'), CMSObj.afteredit(event));
		},
		preview : function(event){
			var host = event.getHost();
			var hostid = host.getId();
			var oParams = {
				FolderId : hostid,
				FolderType : host.getType()=="website"? 103:101
			};
			var sIds = event.getIds().join(',');
			wcm.domain.PublishAndPreviewMgr.preview(sIds,600,oParams,"wcm6_viewdocument");
		},
		basicpublish : function(event){
			//TODO 提示发布
			publish(event,"basicPublish");
		},
		recallpublish : function(event){
			var sHtml = (wcm.LANG.METAVIEWDATA_130 ||"确定要<font color=\'red\' style=\'font-size:14px;\'>撤销发布</font>所选记录么？将<font color=\'red\ style=\'font-size:14px;\'>不可逆转</font>");
			Ext.Msg.confirm(sHtml,{
				yes : function(){
					publish(event,'recallPublish');
				}
			});
		},
		detailpublish : function(event){
			publish(event,'detailPublish');
		},
		directpublish : function(event){
			var oPageParams = event.getContext();
			var _sIds = event.getIds();
			var postData = Object.extend({},{
				'ObjectIds' : _sIds
			});
			BasicDataHelper.call("wcm6_viewdocument", "directpublish", postData, true, function(_transport,_json){
				wcm.domain.PublishAndPreviewMgr.doAfterPublish(postData, "detailpublish",_transport,_json);
			}.bind(this));
		},
		directRecallpublish : function(event){
			var sHtml = (wcm.LANG.METAVIEWDATA_139 ||"确定要<font color=\'red\' style=\'font-size:14px;\'>撤销发布</font>所选记录及其所有引用记录么？将<font color=\'red\' style=\'font-size:14px;\'>不可逆转</font>");
			Ext.Msg.confirm(sHtml,{
				yes : function(){
					publish(event, 'recallpublishall');	
				}
			})	
		},
		"delete" : function(event){
			var oHost = parseHost(event.getHost());
			var browserEvent = event.browserEvent;
			var bDrop = !!(browserEvent && 
				browserEvent.type=='keydown' && browserEvent.shiftKey);
			var params = {
				objectids: event.getIds(),
				operation: bDrop ? '_forcedelete' : '_trash'
							}
			doOptionsAfterDisplayInfo(params, function(){
				ProcessBar.start(wcm.LANG.DOCUMENT_PROCESS_123||'删除记录');
				getHelper().call('wcm6_viewdocument', 'delete',
					Ext.apply(oHost,{"ObjectIds": event.getIds(), "drop": bDrop}), true, 
					function(){
						ProcessBar.close();
							event.getObjs().afterdelete();
                }
				);
            });
		},
		changestatus : function(event){
			var oPageParams = event.getContext();
			var host = event.getHost();
			var hostid = host.getId();
			var _sObjectIds = event.getIds().join(',');
			Object.extend(oPageParams,{"ObjectIds":_sObjectIds,"IsPhoto":false,'ChannelIds':(oPageParams,host.getType()=="website"?0:hostid)});
			FloatPanel.open(WCMConstants.WCM6_PATH +'document/change_status.jsp?' + $toQueryStr(oPageParams), (wcm.LANG.METAVIEWDATA_31 || '记录-改变状态'), CMSObj.afteredit(event));
		},
		changelevel : function(event){
			var oPageParams = event.getContext();
			var host = event.getHost();
			var hostid = host.getId();
			var _sObjectIds = event.getObjs().getPropertys("docId").join(',');
			Object.extend(oPageParams,{
				"ObjectIds":_sObjectIds,
				"IsPhoto":false});
			FloatPanel.open(WCMConstants.WCM6_PATH +'document/change_doclevel.jsp?' + $toQueryStr(oPageParams), '记录-改变密级', CMSObj.afteredit(event));
		},
		"export" : function(event){
			//alert(Ext.toSource(event));
			var _nViewId = event.getObj().getProperty('dockind');
			_nViewId = _nViewId || event.getContext().params["VIEWID"];
			var _chnlId = event.getContext().params["CHANNELID"];
			var _sObjectIds = event.getObjs().getPropertys("docId").join(',');
			try{
				if(!_nViewId){
					Ext.Msg.alert(wcm.LANG.METAVIEWDATA_32 || "没有指定视图ID[VIEWID]");
					return;
				}
			}catch(error){
				Ext.Msg.alert("metaviewdata.export:" + error.message);
			}
			FloatPanel.open( WCMConstants.WCM6_PATH +'metaviewdata/record_export.jsp?' + $toQueryStr({
				viewId		: _nViewId,
				channelId   : _chnlId,
				ObjectIds	: _sObjectIds					
			}), (wcm.LANG.METAVIEWDATA_33 || '导出记录'), CMSObj.afteredit(event));
			return;
		},
		setsynrule : function(event){
			var channelId = event.getHost().getId();
			FloatPanel.open( WCMConstants.WCM6_PATH + 'metaviewdata/syn_rule_set.html?synRuleSetFrom=channel&channelId=' + channelId, (wcm.LANG.METAVIEWDATA_34 || '设置数据同步到WCMDocument的规则'), CMSObj.afteredit(event));
		},
		createfromexcel : function(event){
			var _nViewId = event.getContext().params["VIEWID"];
			try{
				if(!_nViewId){
					Ext.Msg.alert(wcm.LANG.METAVIEWDATA_32 || "没有指定视图ID[VIEWID]");
					return;
				}
			}catch(error){
				Ext.Msg.alert("metaviewdata.createfromexcel:" + error.message);
			}
			wcm.domain.MetaViewDataMgr.download("/wcm/app/metaviewdata/read_excel.jsp?ViewId=" + _nViewId);
		},
		download : function(sURL){
			var frm = $MsgCenter.getActualTop().$('iframe4download');
			if(frm == null) {
				frm = $MsgCenter.getActualTop().document.createElement('IFRAME');
				frm.id = "iframe4download";
				frm.style.display = 'none';
				$MsgCenter.getActualTop().document.body.appendChild(frm);
			}
			frm.src = sURL;		
		},
		'import' : function(event){
			var host = event.getHost();
			var hostid = host.getId();
			var _nViewId = event.getContext().params["VIEWID"];

			try{
				if(!_nViewId){
					Ext.Msg.alert(wcm.LANG.METAVIEWDATA_32 || "没有指定视图ID[VIEWID]");
					return;
				}
			}catch(error){
				Ext.Msg.alert("metaviewdata.import:" + error.message);
			}
			var oParams = {
				ViewId	 : _nViewId,
				ChannelId : host.getType()=="website"?0:hostid
			}
			FloatPanel.open( WCMConstants.WCM6_PATH + 'metaviewdata/view_data_import.jsp?' + $toQueryStr(oParams), (wcm.LANG.METAVIEWDATA_35 || '导入记录'), CMSObj.afteradd(event));
		},
		quote : function(event){
			var _nchannelId = event.getObjs().getAt(0).getPropertyAsInt("currchnlid");
			var _sDocIds = event.getIds().join(',');
			var oPostData = {
				'channelids' : _nchannelId,
				'objectids' : _sDocIds
			}
			FloatPanel.open(  WCMConstants.WCM6_PATH + 'metaviewdata/record_quoteto.html?' + $toQueryStr(oPostData), (wcm.LANG.METAVIEWDATA_36 || '引用记录'), CMSObj.afteredit(event));
		},
		copy : function(event){
			var _nchannelId = event.getObjs().getAt(0).getPropertyAsInt("currchnlid"); 
			var _sDocIds = event.getIds().join(',');
			var oPostData = {
				'channelids' : _nchannelId,
				'objectids' : _sDocIds
			}
			FloatPanel.open(  WCMConstants.WCM6_PATH + 'metaviewdata/record_copyto.html?' + $toQueryStr(oPostData), (wcm.LANG.METAVIEWDATA_38 || '复制记录'), CMSObj.afteredit(event));
		},
		move : function(event){
			var _nchannelId = event.getObjs().getAt(0).getPropertyAsInt("currchnlid");
			var _sDocIds = event.getIds().join(',');

			var oPostData = {
				'channelids' : _nchannelId,
				'objectids' : _sDocIds
			}
			FloatPanel.open(  WCMConstants.WCM6_PATH + 'metaviewdata/record_moveto.html?' + $toQueryStr(oPostData), (wcm.LANG.METAVIEWDATA_39 || '移动记录'), CMSObj.afteredit(event));
		},
		add : function(event){
			_addEdit(0,event);
		},
		edit : function(event){
			var obj = event.getObj();
			_addEdit(obj.getProperty("docid")||obj.getId(),event);
		},
		batchupdate : function(event){
			//逗号分隔的ids
			var objIds = event.getContext().params["IDS"];
			//
			alert(objIds.split(",").length);
			if(objIds.split(",").length > 5){
				alert(wcm.LANG.metaviewdata_1001 || "要批量修改的数据超过了5个，为减少出错机会和减少对数据库的影响，请分批进行批量修改");
			}
			var _nViewId = event.getContext().params["VIEWID"];
			var oPostData = {
				'updateids' : objIds,
				'viewid' : _nViewId
			};
			FloatPanel.open(  WCMConstants.WCM6_PATH + 'metaviewdata/batchUpdate.jsp?' + $toQueryStr(oPostData), ('批量修改'), CMSObj.afteredit(event));

		},
		deleteEntity : function(event){			
			var sIds = event.getIds();
			var nChnlId = event.getObj().getProperty("chnlId");
			var oPageParams = {};			
			Ext.Msg.confirm(String.format("确实要将这{0}条记录放入废稿箱吗?",sIds.length), {
                yes : function(){
						ProcessBar.init(wcm.LANG.METAVIEWDATA_28 || '删除进度');
						ProcessBar.addState((wcm.LANG.METAVIEWDATA_29 || '删除'), 2);
						ProcessBar.start();
						Object.extend(oPageParams,{"ObjectIds":sIds.join(","),"ChannelId":nChnlId});
						BasicDataHelper.call('wcm6_document',"delete",oPageParams,true,function(transport, json){
							ProcessBar.close();
							var bSucess = $v(json, 'REPORTS.IS_SUCCESS') || true;
							if(bSucess == false || bSucess == 'false'){
								Ext.Msg.fault({
									message : wcm.LANG.METAVIEWDATA_30 || '删除数据结果',
									detail : json
								});
							}
							event.getObjs().afterdelete();
						});
                }
            });
		},
		exportall : function(event){
			Ext.Msg.confirm(wcm.LANG.METAVIEWDATA_111 || '此操作可能需要较长时间。确实要导出所有记录吗？', {
				yes : function(){
					var _nViewId = event.getContext().params["VIEWID"];
					var _nChnlId = event.getContext().params["CHANNELID"]
					try{
						if(!_nViewId){
							Ext.Msg.alert(wcm.LANG.METAVIEWDATA_32 || "没有指定视图ID[VIEWID]");
							return;
						}
					}catch(error){
						Ext.Msg.alert("metaviewdata.export:" + error.message);
					}
					FloatPanel.open( WCMConstants.WCM6_PATH +'metaviewdata/record_export.jsp?' + $toQueryStr({
						channelid   : _nChnlId,
						exportall   : 1
					}), (wcm.LANG.METAVIEWDATA_110 || '导出所有记录'), CMSObj.afteredit(event));
					return;
				}
			});	
		},
		setright : function(event){
			var docId = event.getObj().getPropertyAsInt('docid', 0);
			$openCenterWin(WCMConstants.WCM6_PATH + "auth/right_set.jsp?ObjType=605&ObjId=" + docId,
					"document_right_set", 900, 600, "resizable=yes");
		},
		relationProMaintain : function(event){
			var docId = event.getObj().getPropertyAsInt('docid', 0);
			var currViewId = event.getObj().getPropertyAsInt("viewid",0)||contextParams.VIEWID||0;
			wcm.MetaViewSelector.selectView({methodname : 'queryRelatingViews', MetaViewId:currViewId, ContainsChildrenBox : false},function(args){
				var viewId = args.ViewId || 0;
				var viewName = args.selectedNames;
				if(viewId == 0)
					return;
				var url = WCMConstants.WCM6_PATH + 'metaviewdata/metaviewdata_relations_back_select.jsp?ViewName=' + encodeURIComponent(viewName);
				FloatPanel.open({
					src : url,
					title : String.format("{0}数据管理",viewName),
					callback : function(){
						
					},
					dialogArguments : {
						relations : 0,
						CurrDocId : 0,
						RelatedDocId : docId,
						FromBackSelect : true,
						RelatingViewId : viewId,
						RelatedViewId : currViewId
					}
				});
				
			});
		},
		allRelDocMaintain : function(event){
			var docId = event.getObj().getPropertyAsInt('docid', 0);
			var currViewId = event.getObj().getPropertyAsInt("viewid",0)||contextParams.VIEWID||0;
			//by CC 20120413 需要指定一个公共页面，然后打开时需要将所有的视图名称和视图ID都传递过去
			/**步骤
				1、根据获取到的docId和currViewId获取到所有关联视图

			*/
			var oPageParams = {};
			Object.extend(oPageParams,{"MetaViewId":currViewId,"ContainsChildrenBox":false});
			BasicDataHelper.call('wcm61_metaview',"queryViewsRelatingToCurrView",oPageParams,true,function(transport, json){
				//获取到xml格式的所有的METAVIEW
				if(json.METAVIEWS){
					
					var arrayMETAVIEW = com.trs.util.JSON.array(json.METAVIEWS,"METAVIEW");
				} else {
					Ext.Msg.alert("没有关联视图ID[VIEWID]！！！");
					return;
				}
				// 进行链接，METAVIEWS到新打开的页面
				
				var relateViewNames = new Array(arrayMETAVIEW.length);
				var relateViewIds = new Array(arrayMETAVIEW.length);
				for(var i=0; i<arrayMETAVIEW.length; i++ ){
					relateViewNames[i] = arrayMETAVIEW[i]["VIEWNAME"];
					relateViewIds[i] = arrayMETAVIEW[i]["VIEWINFOID"];
				}
				Object.extend(oPageParams,{"docId":docId, "relateViewNames":relateViewNames.join(","), "relateViewIds":relateViewIds.join(",")});
				$openMaxWin(WCMConstants.WCM6_PATH + './metaviewdata/metaviewdata_forallrelationview_back_select.jsp?' + $toQueryStr2(oPageParams));
				//alert($toQueryStr2(oPageParams));

				//跳转到新页面进行了操作
				//debugger;
				//return false;
				
			});
			/*
			wcm.MetaViewSelector.selectView({methodname : 'queryRelatingViews', MetaViewId:currViewId, ContainsChildrenBox : false},function(args){
				debugger;
				return false;
				var viewId = args.ViewId || 0;
				var viewName = args.selectedNames;
				if(viewId == 0)
					return;
				
				var url = WCMConstants.WCM6_PATH + 'metaviewdata/metaviewdata_relations_back_select.jsp?ViewName=' + encodeURIComponent(viewName);
				alert(url);
				FloatPanel.open({
					src : url,
					title : String.format("{0}数据管理",viewName),
					callback : function(){
						
					},
					dialogArguments : {
						relations : 0,
						CurrDocId : 0,
						RelatedDocId : docId,
						FromBackSelect : true,
						RelatingViewId : viewId,
						RelatedViewId : currViewId
					}
				});
				
			});
			*/
		}
	});
})();
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
function preparePostData(event){
	return {
		//'objectids' : event.getObjs().getDocIds().reverse(),
		'objectids' : event.getIds(),
		'channelids' : event.getObj().getPropertyAsInt('channelid')
	};
}
function publish(event, _sMethodName){
	    var objectids = event.getIds().join(',');
		var oPostData = {'ObjectIds' : objectids};
		BasicDataHelper.call("wcm6_viewdocument", _sMethodName, oPostData, true,
			function(_transport,_json){
				wcm.domain.PublishAndPreviewMgr.doAfterPublish(oPostData, _sMethodName, _transport, _json);
			}
		);
	}
(function(){
	var pageObjMgr = wcm.domain.MetaViewDataMgr;
	var reg = wcm.SysOpers.register;
	function fnIsNotDraft(event){
		var obj = event.getObj();
		var bDraft= obj.getPropertyAsString("bDraft");
		if("true" == bDraft){
			return false;
		}
		return true;
	}
	reg({
		key : 'docpositionset',
		type : 'MetaViewData',
		desc : wcm.LANG.METAVIEWDATA_40 || '改变记录顺序到',
		title : wcm.LANG.METAVIEWDATA_40 || '改变记录顺序到...',
		rightIndex : 62,
		order : 1,
		fn : pageObjMgr['docpositionset']
	});
	reg({
		key : 'edit',
		type : 'MetaViewData',
		desc : wcm.LANG.METAVIEWDATA_41 || '修改这条记录',
		title : wcm.LANG.METAVIEWDATA_41 || '修改这条记录...',
		rightIndex : 32,
		order : 2,
		fn : pageObjMgr['edit'],
		quickKey : 'E'
	});
	/*
	reg({
		key : 'maintainreldoc',
		type : 'MetaViewData',
		desc : '维护关联产品',
		title : '维护关联产品...',
		rightIndex : 32,
		order : 2.1,
		fn : pageObjMgr['relationProMaintain'],
		isVisible : function(event){
			var contextParam = event.getContext().params;
			var bShowRelDocsMaintain = contextParam['ShowRelDocsMaintain'];
			if(!bShowRelDocsMaintain){
				return false;
			}else{
				return true;
			}
		}
	});
	*/
	reg({
		key : 'maintainallreldoc',
		type : 'MetaViewData',
		desc : '维护所有关联产品',
		title : '维护所有关联产品...',
		rightIndex : 32,
		order : 2.2,
		fn : pageObjMgr['allRelDocMaintain'],
		isVisible : function(event){
			var contextParam = event.getContext().params;
			var bShowRelDocsMaintain = contextParam['ShowRelDocsMaintain'];
			if(!bShowRelDocsMaintain){
				return false;
			}else{
				return true;
			}
		}
	});
	reg({
		key : 'preview',
		type : 'MetaViewData',
		desc : wcm.LANG.METAVIEWDATA_42 || '预览这条记录',
		title : wcm.LANG.METAVIEWDATA_43 || '预览这条记录发布效果',
		rightIndex : 38,
		order : 3,
		fn : pageObjMgr['preview'],
		quickKey : 'R'
	});
	reg({
		key : 'basicpublish',
		type : 'MetaViewData',
		desc : wcm.LANG.METAVIEWDATA_44 || '发布这条记录',
		title : wcm.LANG.METAVIEWDATA_45 || '发布这条记录，生成这条记录的细览页面以及更新相关概览页面',
		rightIndex : 39,
		order : 4,
		isVisible : fnIsNotDraft,
		fn : pageObjMgr['basicpublish'],
		quickKey : 'P'
	});
	reg({
		key : 'detailpublish',
		type : 'MetaViewData',
		desc : wcm.LANG.METAVIEWDATA_46 || '仅发布这条记录细览',
		title : wcm.LANG.METAVIEWDATA_47 || '仅发布这条记录细览，仅重新生成这条记录的细览页面',
		rightIndex : 39,
		order : 5,
		isVisible : fnIsNotDraft,
		fn : pageObjMgr['detailpublish']
	});
	reg({
		key : 'directpublish',
		type : 'MetaViewData',
		desc : wcm.LANG.METAVIEWDATA_131 || '直接发布这条记录',
		title : wcm.LANG.METAVIEWDATA_132 || '发布这条记录细览，同时发布此记录的所有引用记录',
		rightIndex : 39,
		order : 5,
		isVisible : fnIsNotDraft,
		fn : pageObjMgr['directpublish']
	});
	reg({
		key : 'recallpublish',
		type : 'MetaViewData',
		desc : wcm.LANG.METAVIEWDATA_48 || '撤销发布这条记录',
		title : wcm.LANG.METAVIEWDATA_49 || '撤销发布这条记录，撤回已发布目录或页面',
		rightIndex : 39,
		order : 6,
		isVisible : fnIsNotDraft,
		fn : pageObjMgr['recallpublish']
	});
	reg({
		key : 'directRecallpublish',
		type : 'MetaViewData',
		desc : wcm.LANG.METAVIEWDATA_133 || '直接撤销发布这条记录',
		title : wcm.LANG.METAVIEWDATA_134 || '撤销发布这条记录，同步撤销这条记录所有的引用记录',
		rightIndex : 39,
		order : 6,
		isVisible : fnIsNotDraft,
		fn : pageObjMgr['directRecallpublish']
	});
	reg({
		key : 'export',
		type : 'MetaViewData',
		desc : wcm.LANG.METAVIEWDATA_50 || '导出这条记录',
		title : wcm.LANG.METAVIEWDATA_51 || '将这条记录导出成zip文件',
		rightIndex : 34,
		order : 7,
		fn : pageObjMgr['export'],
		quickKey : 'X'
	});
	reg({
		key : 'seperate',
		type : 'MetaViewData',
		desc : wcm.LANG.METAVIEWDATA_52 || '分隔线',
		title : wcm.LANG.METAVIEWDATA_52 || '分隔线',
		rightIndex : -1,
		order : 8,
		fn : pageObjMgr['seperate']
	});
	reg({
		key : 'changestatus',
		type : 'MetaViewData',
		desc : wcm.LANG.METAVIEWDATA_53 || '改变这条记录状态',
		title : wcm.LANG.METAVIEWDATA_53 || '改变这条记录状态',
		rightIndex : 35,
		order : 9,
		isVisible : fnIsNotDraft,
		fn : pageObjMgr['changestatus']
	});
	reg({
		key : 'changelevel',
		type : 'MetaViewData',
		desc : '改变这条记录的密级',
		rightIndex : 61,
		order : 9,
		fn : pageObjMgr['changelevel']
	});
	reg({
		key : 'move',
		type : 'MetaViewData',
		desc : wcm.LANG.METAVIEWDATA_54 || '移动这条记录到',
		title : wcm.LANG.METAVIEWDATA_54 || '移动这条记录到',
		rightIndex : 33,
		order : 10,
		isVisible : fnIsNotDraft,
		fn : pageObjMgr['move']
	});
	reg({
		key : 'copy',
		type : 'MetaViewData',
		desc : wcm.LANG.METAVIEWDATA_55 || '复制这条记录到',
		title : wcm.LANG.METAVIEWDATA_55 || '复制这条记录到',
		rightIndex : 34,
		order : 11,
		isVisible : fnIsNotDraft,
		fn : pageObjMgr['copy']
	});
	reg({
		key : 'quote',
		type : 'MetaViewData',
		desc : wcm.LANG.METAVIEWDATA_56 || '引用这条记录到',
		title : wcm.LANG.METAVIEWDATA_56 || '引用这条记录到',
		rightIndex : 34,
		order : 12,
		isVisible : fnIsNotDraft,
		fn : pageObjMgr['quote']
	});
	reg({
		key : 'delete',
		type : 'MetaViewData',
		desc : wcm.LANG.METAVIEWDATA_129 || '删除记录',
		title : wcm.LANG.METAVIEWDATA_57 || '将记录放入废稿箱',
		rightIndex : 33,
		order : 13,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'add',
		type : 'MetaViewDataInChannel',
		desc : '新建一条记录',
		title : '新建一条记录...',
		rightIndex : 31,
		order : 14,
		fn : pageObjMgr['add'],
		quickKey : 'N'
	});
	reg({
		key : 'import',
		type : 'MetaViewDataInChannel',
		desc : wcm.LANG.METAVIEWDATA_59 || '从外部导入记录',
		title : wcm.LANG.METAVIEWDATA_59 || '从外部导入记录...',
		rightIndex : 31,
		order : 15,
		fn : pageObjMgr['import']
	});
	reg({
		key : 'createfromexcel',
		type : 'MetaViewDataInChannel',
		desc : wcm.LANG.METAVIEWDATA_60 || '从Excel创建记录',
		title : wcm.LANG.METAVIEWDATA_60 || '从Excel创建记录...',
		rightIndex : 31,
		order : 16,
		fn : pageObjMgr['createfromexcel']
	});
	reg({
		key : 'setsynrule',
		type : 'MetaViewDataInChannel',
		desc : wcm.LANG.METAVIEWDATA_61 || '设置同步规则',
		title : wcm.LANG.METAVIEWDATA_62 || '设置同步到文档的规则',
		rightIndex : 13,
		order : 17,
		fn : pageObjMgr['setsynrule']
	});
	reg({
		key : 'preview',
		type : 'MetaViewDatas',
		desc : wcm.LANG.METAVIEWDATA_63 || '预览这些记录',
		title : wcm.LANG.METAVIEWDATA_64 || '预览这些记录发布效果',
		rightIndex : 38,
		order : 18,
		fn : pageObjMgr['preview'],
		quickKey : 'R'
	});
	reg({
		key : 'basicpublish',
		type : 'MetaViewDatas',
		desc : wcm.LANG.METAVIEWDATA_65 || '发布这些记录',
		title : wcm.LANG.METAVIEWDATA_66 || '发布这些记录，生成这些记录的细览页面以及更新相关概览页面',
		rightIndex : 39,
		order : 19,
		fn : pageObjMgr['basicpublish'],
		quickKey : 'P'
	});
	reg({
		key : 'detailpublish',
		type : 'MetaViewDatas',
		desc : wcm.LANG.METAVIEWDATA_67 || '仅发布这些记录细览',
		title : wcm.LANG.METAVIEWDATA_68 || '仅发布这些记录细览，仅重新生成这些记录的细览页面',
		rightIndex : 39,
		order : 20,
		fn : pageObjMgr['detailpublish']
	});
	reg({
		key : 'directpublish',
		type : 'MetaViewDatas',
		desc : wcm.LANG.METAVIEWDATA_135 || '直接发布这些记录',
		title : wcm.LANG.METAVIEWDATA_136 || '发布这些记录细览，同时发布这些记录的所有引用记录',
		rightIndex : 39,
		order : 20,
		fn : pageObjMgr['directpublish']
	});
	reg({
		key : 'recallpublish',
		type : 'MetaViewDatas',
		desc : wcm.LANG.METAVIEWDATA_69 || '撤销发布这些记录',
		title : wcm.LANG.METAVIEWDATA_70 || '撤销发布这些记录，撤回已发布目录或页面',
		rightIndex : 39,
		order : 21,
		fn : pageObjMgr['recallpublish']
	});
	reg({
		key : 'directRecallpublish',
		type : 'MetaViewDatas',
		desc : wcm.LANG.METAVIEWDATA_137 || '直接撤销发布这些记录',
		title : wcm.LANG.METAVIEWDATA_138 || '撤销发布这些记录，同步撤销这些记录所有的引用记录',
		rightIndex : 39,
		order : 21,
		fn : pageObjMgr['directRecallpublish']
	});
	reg({
		key : 'export',
		type : 'MetaViewDatas',
		desc : wcm.LANG.METAVIEWDATA_71 || '导出这些记录',
		title : wcm.LANG.METAVIEWDATA_72 || '将这些记录导出成zip文件',
		rightIndex : 34,
		order : 22,
		fn : pageObjMgr['export']
	});
	reg({
		key : 'seperate',
		type : 'MetaViewDatas',
		desc : wcm.LANG.METAVIEWDATA_52 || '分隔线',
		title : wcm.LANG.METAVIEWDATA_52 || '分隔线',
		rightIndex : -1,
		order : 23,
		fn : pageObjMgr['seperate']
	});
	reg({
		key : 'changestatus',
		type : 'MetaViewDatas',
		desc : wcm.LANG.METAVIEWDATA_73 || '改变这些记录状态',
		title : wcm.LANG.METAVIEWDATA_73 || '改变这些记录状态',
		rightIndex : 35,
		order : 24,
		fn : pageObjMgr['changestatus']
	});
	reg({
		key : 'changelevel',
		type : 'MetaViewDatas',
		desc : '改变这些记录的密级',
		rightIndex : 61,
		order : 25,
		fn : pageObjMgr['changelevel']
	});
	reg({
		key : 'move',
		type : 'MetaViewDatas',
		desc : wcm.LANG.METAVIEWDATA_74 || '移动这些记录到',
		title : wcm.LANG.METAVIEWDATA_74 || '移动这些记录到',
		rightIndex : 33,
		order : 25,
		fn : pageObjMgr['move']
	});
	reg({
		key : 'copy',
		type : 'MetaViewDatas',
		desc : wcm.LANG.METAVIEWDATA_75 || '复制这些记录到',
		title : wcm.LANG.METAVIEWDATA_75 || '复制这些记录到',
		rightIndex : 34,
		order : 26,
		fn : pageObjMgr['copy']
	});
	reg({
		key : 'quote',
		type : 'MetaViewDatas',
		desc : wcm.LANG.METAVIEWDATA_76 || '引用这些记录到',
		title : wcm.LANG.METAVIEWDATA_76 || '引用这些记录到',
		rightIndex : 34,
		order : 27,
		fn : pageObjMgr['quote']
	});
	reg({
		key : 'delete',
		type : 'MetaViewDatas',
		desc : wcm.LANG.METAVIEWDATA_129 || '删除记录',
		title : wcm.LANG.METAVIEWDATA_57 || '将记录放入废稿箱',
		rightIndex : 33,
		order : 28,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'setright',
		type : 'MetaViewData',
		desc :  wcm.LANG.METAVIEWDATA_117 || '设置这条记录权限',
		title : wcm.LANG.METAVIEWDATA_117 || '设置这条记录权限',
		rightIndex : 61,
		order : 14,
		fn : pageObjMgr['setright']
	});
	reg({
		key : 'exportall',
		type : 'MetaViewDataInChannel',
		desc : wcm.LANG.METAVIEWDATA_110 || '导出所有记录',
		title : wcm.LANG.METAVIEWDATA_110 || '导出所有记录...',
		rightIndex : 34,
		order : 29,
		fn : pageObjMgr['exportall']
	});
	//reg({
		//key : 'batchupdate',
		//type : 'MetaViewDataInChannel',
		//desc : wcm.LANG.METAVIEWDATA_113 || '批量修改',
		//title : wcm.LANG.METAVIEWDATA_113 || '批量修改...',
		//rightIndex : 34,
		//order : 29,
		//isVisible : function(){
			//return false;
		//},
		//fn : pageObjMgr['batchupdate']
	//});

})();
//分类法操作信息和Mgr定义
WCMConstants.OBJ_TYPE_CLASSINFO = 'classinfo';;
Ext.ns('wcm.domain.classinfoMgr');
(function(){
	var m_oMgr = wcm.domain.classinfoMgr;
	function getHelper(){
		return new com.trs.web2frame.BasicDataHelper();
	}
	function config(_id,_name){
		var params = {objectId : _id, objectName : _name};
		var url = 'classinfo/classinfo_config.html?' + $toQueryStr(params);		
		FloatPanel.open({
			src : WCMConstants.WCM6_PATH + url,
			title : wcm.LANG.CLASSINFO_25 || '分类法维护'
		});				
	}
	Ext.apply(wcm.domain.classinfoMgr, {		
		"delete" : function(event){
			var objsOrHost = event.getObjsOrHost();
			var aIds = objsOrHost.getIds();
			var nCount = aIds.length;
			var sHint = (nCount == 1)? '' : nCount;
			Ext.Msg.confirm( String.format("确实要将这{0}个分类法删除吗?", sHint),{				
				yes : function(){
					getHelper().call("wcm61_classinfo", 
					"deleteClassInfo", //远端方法名				
					{"ObjectIds": aIds.join(",")}, //传入的参数
					true, 
					function(){//响应函数	
						event.getObjsOrHost()["afterdelete"]();
					}
				)}
			});
		},					
		add : function(event){
			var url = 'classinfo/classinfo_add_edit.html';
			FloatPanel.open({
				src : WCMConstants.WCM6_PATH + url,
				title : wcm.LANG.CLASSINFO_9 || '新建一个分类法',
				callback : function(objId, objName){	
					var info = {objId : objId, objType : WCMConstants.OBJ_TYPE_CLASSINFO};
					CMSObj.createFrom(info, null)['afteradd']();
					FloatPanel.close();					
					config.apply(window, arguments);
				}
			});				
		},
		config : function(event){
			var nObjectId = event.getObj().getId();
			var sObjName = event.getObj().getPropertyAsString("objectName")
			config(nObjectId,sObjName);
		},
		'import' : function(event){
			var context = event.getContext();
			var params = {
				OwnerId: context.OwnerId,
				OwnerType: context.OwnerType
			}
			FloatPanel.open(WCMConstants.WCM6_PATH + 'classinfo/classinfo_import.jsp?' + $toQueryStr(params), 
				wcm.LANG.CLASSINFO_13 || '导入分类法', CMSObj.afteradd(event));
		},
		'export' : function(event){
			if(event.length()==0 && event.getObj().getId()==""){
				Ext.Msg.$alert(wcm.LANG.classinfo_1001 || '必须选中至少一个分类法!');
				return;
			}
			var oPostData = {
				ClassInfoIds: event.getIds().join()||event.getObj().getId()
			};
			BasicDataHelper.call('wcm61_classinfo', 'exportClassInfos', oPostData, true, function(_trans, _json){
				var sFileUrl = _trans.responseText;
				var frm = document.getElementById("ifrmDownload");
				if(frm == null) {
					frm = document.createElement('iframe');
					frm.style.height = 0;
					frm.style.width = 0;
					document.body.appendChild(frm);
				}
				sFileUrl = WCMConstants.WCM6_PATH + "file/read_file.jsp?DownName=" + sFileUrl + "&FileName=" + sFileUrl;
				frm.src = sFileUrl;
			
			}.bind(this));	
		}
	});
})();
(function(){
	var pageObjMgr = wcm.domain.classinfoMgr;
	var reg = wcm.SysOpers.register;

	reg({
		key : 'config',
		type : 'ClassInfo',
		desc : wcm.LANG.CLASSINFO_11 || '维护这个分类法',
		title : wcm.LANG.CLASSINFO_11 || '维护这个分类法',
		rightIndex : -2,
		order : 1,
		fn : pageObjMgr['config'],
		quickKey : ['E']
	});
	reg({
		key : 'delete',
		type : 'ClassInfo',
		desc : wcm.LANG.CLASSINFO_31 || '删除这个分类法',
		title : wcm.LANG.CLASSINFO_31 || '删除这个分类法',
		rightIndex : -2,
		order : 2,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'export',
		type : 'ClassInfo',
		desc : wcm.LANG.CLASSINFO_36 || '导出这个分类法',
		title : wcm.LANG.CLASSINFO_36 || '导出这个分类法',
		rightIndex : -2,
		order : 3,
		fn : pageObjMgr['export']
	});
	reg({
		key : 'config',
		type : 'ClassInfoCls',
		desc : wcm.LANG.CLASSINFO_11 || '维护这个分类法',
		title : wcm.LANG.CLASSINFO_11 || '维护这个分类法',
		rightIndex : -2,
		order : 3,
		fn : pageObjMgr['config'],
		quickKey : ['E']
	});
	reg({
		key : 'delete',
		type : 'ClassInfoCls',
		desc : wcm.LANG.CLASSINFO_31 || '删除这个分类法',
		title : wcm.LANG.CLASSINFO_31 || '删除这个分类法',
		rightIndex : -2,
		order : 4,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'add',
		type : 'ClassInfoInRoot',
		desc : '新建一个分类法',
		title : '新建一个分类法...',
		rightIndex : -1,
		order : 5,
		fn : pageObjMgr['add'],
		quickKey : ['N']
	});
	reg({
		key : 'import',
		type : 'ClassInfoInRoot',
		desc : '导入分类法',
		title : '导入分类法...',
		rightIndex : -1,
		order : 6,
		fn : pageObjMgr['import']
	});
	reg({
		key : 'export',
		type : 'ClassInfos',
		desc : wcm.LANG.CLASSINFO_37 || '导出这些分类法',
		title : wcm.LANG.CLASSINFO_37 || '导出这些分类法',
		rightIndex : -2,
		order : 3,
		fn : pageObjMgr['export']
	});
	reg({
		key : 'delete',
		type : 'ClassInfos',
		desc : wcm.LANG.CLASSINFO_32 || '删除这些分类法',
		title : wcm.LANG.CLASSINFO_32 || '删除这些分类法',
		rightIndex : -2,
		order : 7,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});

})();
Ext.ns("wcm.ListQuery", "wcm.ListQuery.Checker");

(function(){
	var sContent = [
		'<div class="querybox">',
			'<div class="qbr">',
				'<table border=0 cellspacing=0 cellpadding=0 class="qbc">',
					'<tr>',
						'<td class="elebox">',
							'<input type="text" name="queryValue" id="queryValue" onfocus="wcm.ListQuery.focusQueryValue();" onkeydown="wcm.ListQuery.keydownQueryValue(event);">',
							'<select name="queryType" id="queryType" onchange="wcm.ListQuery.changeQueryType();">{0}</select>',
						'</td>',
						'<td class="search" onclick="wcm.ListQuery.doQuery();"><div>&nbsp;</div></td>',
					'</tr>',
				'</table>',
			'</div>',
		'</div>'
	].join("");

	var allFlag = "-1";

	Ext.apply(wcm.ListQuery, {
		/**
		 * @cfg {String} container
		 * the container of query box to render to.
		 */
		/**
		 * @cfg {Boolean} appendQueryAll
		 * whether append the query all item or not, default to false.
		 */
		/**
		 * @cfg {Boolean} autoLoad
		 * whether the query box auto loads itself or not, default to true.
		 */
		/**
		*@cfg {String} maxStrLen
		*the max length of string value. default to 100
		*/
		/**
		 * @cfg {Object} items
		 * the query items of query box.
		 *eg. {name : 'id', desc : '站点', type : 'string'}
		 */
		/**
		 * @cfg {Function} callback
		 * the callback when user clicks the search button.
		 */
		config : null,
		register : function(_config){
			var config = {maxStrLen : 100, appendQueryAll : false, autoLoad : true};
			Ext.apply(config, _config);
			if(config["appendQueryAll"]){
				config["items"].unshift({name: allFlag, desc: WCMLANG["LIST_QUERY_ALL_DESC"] || "全部", type: 'string'});
			}
			this.config = config;
			if(config["autoLoad"]){
				if(document.body){
					this.render();
				}else{
					Event.observe(window, 'load', this.render.bind(this), false);
				}
			}
			return this;
		},
		render : function(){
			var sOptHTML = "";
			var items = this.config.items;
			for (var i = 0; i < items.length; i++){
				sOptHTML += "<option value='" + items[i].name + "' title='"+ items[i].desc + "'>" +  items[i].desc + "</option>";
			}
			Element.update(this.config.container, String.format(sContent, sOptHTML));
			$('queryValue').value = this.getDefaultValue();
		},
		changeQueryType : function(){
			var eQVal = $('queryValue');
			if(eQVal.value.indexOf(WCMLANG["LIST_QUERY_INPUT_DESC"]||"..输入") >= 0) {
				eQVal.value = this.getDefaultValue();
				eQVal.style.color = 'gray';
			}
			eQVal.select();
			eQVal.focus();
		},
		keydownQueryValue : function(event){
			event = window.event || event;
			if(event.keyCode == 13){
				Event.stop(event);
				this.doQuery();
			}
		},
		focusQueryValue : function(){
			var eQVal = $('queryValue');
			eQVal.style.color = '#414141';
			eQVal.select();
		},
		getDefaultValue : function(){
			var nIndex = $('queryType').selectedIndex;
			if(nIndex < 0) return "";
			var oItem =  this.getItem(nIndex);
			return (WCMLANG["LIST_QUERY_INPUT_DESC"]||"..输入") + (oItem["name"] == allFlag ? (WCMLANG["LIST_QUERY_JSC_DESC"]||"检索词") : oItem["desc"]);
		},
		getItem : function(_index){
			return this.config["items"][_index];
		},
		getParams : function(){
			var params = {};
			var sQType = $F("queryType");
			var sQValue= $F("queryValue");
			if(this.getDefaultValue() == sQValue){
				sQValue = "";
			}
			if(sQType == allFlag){
				params["isor"] = true;
				var items = this.config["items"];
				for (var i = 0; i < items.length; i++){
					var item = items[i];
					if(item["name"] == allFlag) continue;
					if(this.valid(item).isFault) continue;
					params[item["name"]] = sQValue;
				}
			}else{
				params["isor"] = false;
				params[sQType] = sQValue;
			}
			return params;
		},
		valid : function(item){
			var sQValue = $F("queryValue").trim();
			var sType = item["type"] || '';
			sType = sType.toLowerCase();
			var checker = wcm.ListQuery.Checker;
			var result = (checker[sType]||checker['default'])(sQValue, item);
			return {isFault : !!result, msg : result}
		},
		clearLastParams : function(){
			if(!window.PageContext || !PageContext.params) return;
			var params = PageContext.params;
			var items = this.config["items"];
			for (var i = 0; i < items.length; i++){
				var item = items[i];
				delete params[item["name"]];
				delete params[item["name"].toUpperCase()];
			}
			delete params["SelectIds"];
		},
		doQuery : function(){
			//check the valid.
			var validInfo = this.valid(this.getItem($('queryType').selectedIndex));
			if(validInfo.isFault) {
				Ext.Msg.$alert(validInfo["msg"]);
				return;
			}
			//exec the callback.
			if(this.config.callback){
				this.clearLastParams();
				this.config.callback(this.getParams());
			}
		}
	});

	//wcm.ListQuery.Checker
	Ext.apply(wcm.ListQuery.Checker, {
		'default' : function(){
			return false;
		},
		"int" : function(sValue){
			if(sValue.trim().length == 0) return false;
			if(wcm.ListQuery.getDefaultValue() == sValue) return false;
			var nIntVal = parseInt(sValue, 10);
			if(!(/^-?[0-9]+\d*$/).test(sValue)) {
				return WCMLANG["LIST_QUERY_INT_MIN"] || "要求为整数！";
			}else if(nIntVal > 2147483647){
				return WCMLANG["LIST_QUERY_INT_MAX"] || '要求在-2147483648~2147483647(-2^31~2^31-1)之间的数字！';
			}
			return false;
		},
		"float" : function(sValue){
			if(sValue.trim().length == 0) return false;
			if(sValue.match(/^-?[0-9]+(.[0-9]*)?$/) == null){
				return WCMLANG["LIST_QUERY_FLOAT"] || "要求为小数！";
			}
			return false;
		},
		"double" : function(sValue){
			if(sValue.trim().length == 0) return false;
			if(sValue.match(/^-?[0-9]+(.[0-9]*)?$/) == null){
				return WCMLANG["LIST_QUERY_FLOAT"] || "要求为小数！";
			}
			return false;
		},
		"string" : function(sValue, item){
			var nDefMaxLen = wcm.ListQuery.config["maxStrLen"];
			var nItemMaxLen = parseInt(item["maxLength"], 10) || nDefMaxLen;
			var nMaxLen = Math.min(nDefMaxLen, nItemMaxLen);
			if(sValue.length > nMaxLen){
				return '<span style="width:180px;overflow-y:auto;">'+String.format("当前检索字段限制为[<b>{0}</b>]个字符长度，当前为[<b>{1}</b>]！<br><br><b>提示：</b>每个汉字长度为2。", nMaxLen, sValue.length)+'</span>';
			}
			return false;
		},
		"date" : function(sValue, item){
			var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/;
			if(sValue && !reg.test(sValue)){
				return '<span style="width:180px;overflow-y:auto;">当前检索字段限制为日期类型！<br><br><b>提示：</b>如yyyy-MM-dd。</span>';
			}
			return false;
		}
	});
})();
var FileUploader = Class.create();
Object.extend(FileUploader, {
	_cache_ : [],
	onUploadAll : null,
	getCache : function(){
		return this._cache_;
	},
	setUploadAll : function(fUploadAll){
		this.onUploadAll = fUploadAll;
	},
	isEmptyValue : function(){
		return this.isUploadAll();
	},
	isUploadAll : function(){
		for (var i = 0, length = this._cache_.length; i < length; i++){
			var instance = this._cache_[i];
			if(instance.isBindData) return false;
		}
		return true;
	},
	destory : function(){
		for (var i = 0, length = this._cache_.length; i < length; i++){
			var instance = this._cache_[i];
			delete instance["oRelateElement"];
			delete instance["oIframeElement"];
			delete instance["oFileControl"];
			delete instance["fChangeEvent"];
			delete instance["fUploadedCallBack"];
			delete instance["fUploadedCallBack2"];
			this._cache_[i] = null;
		}
		delete FileUploader["onUploadAll"];
		this._cache_ = [];
	}
});
Object.extend(FileUploader.prototype, {
	uploadSrc					: 'file_upload.html',
	uploadDoWithSrc				: 'file_upload_dowith.jsp',
	appendixIframeIdSuffix		: '_iframe',
	appendixFileControlId		: 'fileNameControl',
	oRelateElement				: null,
	fChangeEvent				: null,
	fUploadedCallBack			: null,
	fUploadedCallBack2			: null,
	oIframeElement				: null,
	oFileControl				: null,
	isBindData					: false,

	initialize : function(sRelateElement, fChangeEvent, fUploadedCallBack){
		//1.cache the instance, for destroy.
		FileUploader._cache_.push(this);

		//2.init apperance.
		this.oRelateElement = $(sRelateElement);
		this.fChangeEvent = fChangeEvent;
		this.fUploadedCallBack = fUploadedCallBack;
		this.oIframeElement = document.createElement('iframe');
		this.oIframeElement.id = sRelateElement + this.appendixIframeIdSuffix;
		this.oIframeElement.style.display = 'none';
		this.oIframeElement.src = this.uploadSrc;
		document.body.appendChild(this.oIframeElement);

		//3.init actions--events.
		this.initEvent();
	},
	initEvent : function(){
		//1.bind this.oRelateElement events.
		Event.observe(this.oRelateElement, 'click', this.onBrowse.bind(this));

		//2.bind this.oIframeElement events.
		Event.observe(this.oIframeElement, 'readystatechange', this.onIframeStateChanged.bind(this));
	},
	onBrowse : function(){
		try{			
			if(!this.oFileControl){
				this.oFileControl = this.oIframeElement.contentWindow.document.getElementById(this.appendixFileControlId);
				this.oFileControl.onchange = function(event){
					this.isBindData = true;
					if(this.fChangeEvent){
						this.fChangeEvent(this.oFileControl.value);
					}
				}.bind(this);
			}	
			this.oFileControl.click();
		}catch(error){
			alert(error.message);
		}
	},
	onIframeStateChanged : function(){
		if(!this.isBindData) return;
		if(this.oIframeElement.readyState.toLowerCase() != 'complete') return;
		this.onUpload();
	},
	onUpload : function(){
		var oInfoDiv = this.oIframeElement.contentWindow.document.getElementById("infoId");
		if(oInfoDiv){
			if(oInfoDiv.getAttribute("isError")){
				alert(wcm.LANG.METAVIEWDATA_109 || "上传文件失败！\n" + oInfoDiv.innerText);
			}else{
				this.isBindData = false;
				var fUploadCallBack = this.fUploadedCallBack2 || this.fUploadedCallBack;
				if(fUploadCallBack){
					fUploadCallBack(decodeURI(oInfoDiv.innerHTML));
				}
			}
		}
		if(FileUploader.isUploadAll() && FileUploader.onUploadAll){
			FileUploader.onUploadAll();
		}
		this.oIframeElement.setAttribute("src", this.uploadSrc);
	},
	reset : function(){
		//reset the file control value by reload the page.
		if(!this.oFileControl) return;
		this.isBindData = false;
		this.oFileControl.form.reset();
	},
	doUpload : function(fUploadedCallBack){
		if(!this.oFileControl || !this.isBindData){
			//not trigger the browser action, so return.
			if(FileUploader.isUploadAll() && FileUploader.onUploadAll){
				FileUploader.onUploadAll();
			}
			return;
		}
		this.fUploadedCallBack2 = fUploadedCallBack;
		var sValue = this.oFileControl.value;
		var fileNameValue = sValue.substring(sValue.lastIndexOf("\\")+1);
		var sParams = "fileNameParam=" + this.appendixFileControlId + "&fileNameValue="+encodeURI(fileNameValue);
		var fileForm = this.oFileControl.form;
		fileForm.action = this.uploadDoWithSrc + "?" + sParams;
		fileForm.submit();
	}
});

// destroy the FileUploader cache.
Event.observe(window, 'unload', function(){
	FileUploader.destory();
});
/**-------------------------------------------------------------------------**/

var FileDownloader = Class.create();
Object.extend(FileDownloader, {
	download : function(sUrl){
		var frm = (top.actualTop||top).$('iframe4download');
		if(!frm) {
			frm = (top.actualTop||top).document.createElement('IFRAME');
			frm.id = "iframe4download";
			frm.style.display = 'none';
			(top.actualTop||top).document.body.appendChild(frm);
		}
		frm.src = sUrl;		
	}
});
