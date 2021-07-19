Ext.ns('wcm.domain.InfoviewDocMgr');
(function(){
	Ext.apply(wcm.domain.InfoviewDocMgr, {
		//type here
	});
})();

(function(){
	var pageObjMgr = wcm.domain.InfoviewDocMgr;
	var reg = wcm.SysOpers.register;
	var m_sServiceId = 'wcm61_viewdocument';
	var m_nObjType = 600;
	//改用简版后，做了单独的支持。
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
	function preview(_sDocIds, _nObjectType, _oExtraParams,_serviceid){
			wcm.domain.PublishAndPreviewMgr.preview(_sDocIds, _nObjectType, _oExtraParams,_serviceid);
		}
	function getHelper(_sServceFlag){
			return new com.trs.web2frame.BasicDataHelper();
		}
	function doOptionsAfterDisplayInfo(_params, _fDoAfterDisp){
			var cb = wcm.CrashBoard.get({
					id : 'document_info_dialog',
					title : wcm.LANG['INFOVIEW_DOC_112'] || '系统提示信息',
					url : WCMConstants.WCM6_PATH + 'infoview/infoviewdoc_info.html',
					width:'500px',
					height:'300px',
					maskable:true,
					params :  _params,
					callback : _fDoAfterDisp
				});
			cb.show();
			
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
	function doAfterPublish(_postData, _sMethodName,_transport,_json){
		if(_json!=null&&_json["REPORTS"]){
			var oReports = _json["REPORTS"];
			var stJson = com.trs.util.JSON;
			var bIsSuccess = stJson.value(oReports, "IS_SUCCESS");
			if(bIsSuccess=='false'){
				Ext.Msg.report(_json, wcm.LANG['INFOVIEW_DOC_180'] || '发布校验结果');
				return;
			}
		}
		var bIsPublished = (_sMethodName.toLowerCase() != 'recallpublish');
		Ext.Msg.$timeAlert((wcm.LANG['INFOVIEW_DOC_181'] || '已经将您的发布操作提交到后台了...'), 3);
		//TODO 刷新main和oap中需要刷新的字段
		var params = Object.extend({}, _postData);
		params['StatusName'] = bIsPublished ? (wcm.LANG['INFOVIEW_DOC_182'] || '已发') : (wcm.LANG['INFOVIEW_DOC_183'] || '已否');
		params['StatusValue'] = bIsPublished ? '10' : '15';
		var objectids = params['ObjectIds'];
		if(!Array.isArray(objectids)) {
			params['ObjectIds'] = [objectids];
		}
	}
	function publish(objectids, _sMethodName,_oExtraParams){
			_sMethodName = _sMethodName || 'publish';
			var oPostData = {'ObjectIds' : objectids};
			getHelper().call(m_sServiceId, _sMethodName, oPostData, true,
				function(_transport,_json){
					doAfterPublish(oPostData, _sMethodName, _transport, _json);
				}
			);
		}
	function exportCurrent(event, _bExportSelected) {
			var docIds = event.getIds().join();
			var m_nCurrChannelId = event.getHost().getId() || 0;
			var context = event.getContext();
			var sSelectedIVIds = null;
			if(_bExportSelected){
				sSelectedIVIds = docIds;
				if(!sSelectedIVIds)sSelectedIVIds = '0';
			}
			doExcelExport("", sSelectedIVIds, context);
		}
	function doExcelExport(_exportFields,_exportIVIds, context){
			var sRequestUrl = WCMConstants.WCM6_PATH + "infoview/infoview_document_export_excel.jsp";
			 
			var params = 'ChannelId=' + context.pageDataParams.m_nCurrChannelId + '&SearchXML=' + context.pageDataParams.m_sSearchXML;

			if(_exportFields && _exportFields.length > 0) {
				params += '&ExportFields=' + _exportFields;
			}
			if(_exportIVIds && _exportIVIds.length > 0) {
				params += '&SelectedDocIds=' + _exportIVIds;
			}
			if(parseInt(context.pageDataParams.m_sDocStatus) > 0){
				params += '&DocStatus=' + context.pageDataParams.m_sDocStatus;
			}
			new Ajax.Request(sRequestUrl,{
				contentType : 'application/x-www-form-urlencoded',
				method : 'post',
				parameters : params,
				onSuccess : function(_transport){
					result = _transport.responseText;
					if(result && result.indexOf("<excelfile>") != -1){
						var ix = result.indexOf("<excelfile>") + 11;
						var ixx = result.indexOf("</excelfile>");
						result = result.substring(ix,ixx);
						var frm = document.getElementById("ifrmDownload");
						if(frm == null) {
							frm = document.createElement('iframe');
							frm.style.height = 0;
							frm.style.width = 0;
							document.body.appendChild(frm);
						}
						var sUrl = "../file/read_file.jsp?FileName="+result; 	
						frm.src = sUrl;
					}else{
						Ext.Msg.alert(wcm.LANG['INFOVIEW_DOC_114'] || "导出统计结果到Excel失败！");
					} 
				},
				onFailure : function(_transport){
					 Ext.Msg.alert(wcm.LANG['INFOVIEW_DOC_114'] || "导出统计结果到Excel失败！");
				}
			});
		}
	function openEditor(params){
			if(params.DocumentId=='0'&&params.SiteId!=0){
				FloatPanel.open(WCMConstants.WCM6_PATH + 'document/document_siteadd_step1.html?' 
					+ $toQueryStr(params), wcm.LANG['INFOVIEW_DOC_115'] || '选择要新建文档的栏目', 400, 350);
				return;
			}
			var iWidth = window.screen.availWidth - 12;
			var iHeight = window.screen.availHeight - 30;
			var sFeature = 'location=no,resizable=yes,menubar=no,scrollbars=no,status=no,titlebar=no,toolbar=no,top=0,left=0,border=0,width='+iWidth+',height='+iHeight;
			window.open(WCMConstants.WCM6_PATH + "document/document_addedit.jsp?" + $toQueryStr(params), "_blank" , sFeature);
		}
	function exportExcel(event, _bExportSelected) {
		var docIds = event.getObjs().getPropertyAsInt('docid', 0);
		if(_bExportSelected && docIds.length==0){
			Ext.Msg.$alert(wcm.LANG['INFOVIEW_DOC_184'] || '必须至少选择一篇文档！');
			return;
		}
		var m_nCurrChannelId = event.getHost().getId() || 0;	
		var context = event.getContext();
		var pFields = context.pageDataParams.m_sCurrOutlineFields.split(",");
		for(var nIndex = pFields.length-1; nIndex>=0; nIndex--){
			if(pFields[nIndex] == "_EDIT" || pFields[nIndex] == "_PREVIEW" )
				pFields.splice(nIndex, 1);
		}

		var oArgs = {
			ChannelId : m_nCurrChannelId,
			SelectedFields : pFields.join(","),
			IsExport : 1
		};
		var sUrl = WCMConstants.WCM6_PATH + 'infoview/infoview_fields_select_order.jsp';
		var cb = wcm.CrashBoard.get({
			id : 'exportExcel',
			title : wcm.LANG['INFOVIEW_DOC_113'] || '导出为Excel',
			url : sUrl,
			width: '680px',
			height: '370px',
			params : oArgs,
			callback : function(sResult){
				if(!sResult) {
					return false;
				}
				var sSelectedIVIds = null;
				if(_bExportSelected== true){
					sSelectedIVIds = docIds;
					if(!sSelectedIVIds)sSelectedIVIds = '0';
				}
				cb.close();
				doExcelExport(sResult,sSelectedIVIds,context);
				return false;
			}
		});
		cb.show();
	}
	function openImport(event,result){
		var oParams = {
			DocumentId : result.DocumentId,
			ChannelId : result.ChannelId,
			SiteId : result.SiteId
		}
		var sChannelNames = result.ChannelName;
		setTimeout(function(){
			FloatPanel.open(WCMConstants.WCM6_PATH +'document/document_import.jsp?' + $toQueryStr(oParams), String.format(wcm.LANG.INFOVIEW_DOC_157 || ('文档-导入文档到栏目[{0}]'),sChannelNames[0]),CMSObj.afteradd(event));
		}, 10);
		return false;
	}
	function startDocInFlow(event){ 
		var postData = {
			objectid: event.getObj().getPropertyAsInt('docid')
		}
		var sServiceId = 'wcm6_document';
		BasicDataHelper.call(sServiceId, 'startDocumentInFlow', postData, true, function(){
			CMSObj.afteredit(event)();
		});
		return false;
	}
	Ext.apply(pageObjMgr, {
	'new' : function(event){
		var oParams = Ext.apply({
			DocumentId : 0,
			FromEditor : 1
		}, parseHost(event.getHost()));
		openEditor(oParams);
	},
	basicpublish : function(event){
		publish(event.getIds(), 'basicPublish');
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
			ProcessBar.start(wcm.LANG['INFOVIEW_DOC_174'] || "删除表单文档");
			getHelper().call(m_sServiceId, 'delete',
				Ext.apply(oHost,{"ObjectIds": event.getIds(), "drop": bDrop}), true, 
				function(){
					ProcessBar.close();
					event.getObjs().afterdelete();
				}
			);
		});
	},
	moveall : function(event,operItem){
		var sHtml = String.format("确定要{0}移动所有{1}文档么？将{2}不可逆转！{3}",'<font color=\'red\' style=\'font-size:14px;\'>','</font>','<font color=\'red\' style=\'font-size:14px;\'>','</font>');
		Ext.Msg.confirm(sHtml,{
			yes : function(){
				pageObjMgr.move(event, operItem, true);
			}
		})		
	},
	move : function(event,operItem,bMoveall){
		var context = event.getContext();
		var sObjectids = event.getIds();
		var host = event.getHost();
		var hostId = host.getId();
		var hostType = host.getIntType();
		var hostChnlId = hostType == 101 ? hostId : 0;
		sObjectids = (sObjectids.length!=0)?sObjectids:'0';
		if(bMoveall)sObjectids = '0';
		var channelids = event.getObjs().getPropertyAsString("channelid", 0);
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
		var nExcludeInfoView = 0;
		var args = {
			IsRadio : 1,
			ExcludeTop : 1,
			ExcludeLink : 1,
			ExcludeInfoView : nExcludeInfoView,
			ExcludeOnlySearch : 1,
			ShowOneType : 1,
			SelectedChannelIds : channelids.join() || hostChnlId,
			NotSelect : 1,
			RightIndex : 31,
			canEmpty : true,
			ValidInfoViewChannel : 1
		};
		if(bIsOneChannel){
			Ext.apply(args, {
				CurrChannelId : hostChnlId || tmpChannelid || 0,
				ExcludeSelf : 1
			});
		}
		FloatPanel.open({
			src :  WCMConstants.WCM6_PATH + 'include/channel_select.html',
			title : wcm.LANG['INFOVIEW_DOC_116'] || '文档-文档移动到...',
			callback : function(selectIds, selectChnlDescs){
				if(!selectIds||selectIds.length==0) {
					Ext.Msg.$alert('请选择当前文档要移动到的目标栏目!');
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
				var func = function(){
					FloatPanel.close();
					CMSObj.afteredit(event)();
				}
				var oHelper = new com.trs.web2frame.BasicDataHelper();
				ProcessBar.start(wcm.LANG['INFOVIEW_DOC_176'] || "移动文档");
				oHelper.Call('wcm6_viewdocument',(sObjectids=='0')?'moveAll':'move',oPostData,true,
					function(_transport,_json){
						ProcessBar.close();
						Ext.Msg.report(_json, wcm.LANG['INFOVIEW_DOC_118'] || '文档移动结果',func);
						FloatPanel.hide();
					},
					function(_transport,_json){
						ProcessBar.close();
						$render500Err(_transport,_json);
						FloatPanel.close();
					}
				);
			},
			dialogArguments : args
		});
	},
	copyall : function(event,operItem){
		pageObjMgr.copy(event, operItem, true);
	},
	copy : function(event,operItem,bCopyall){
		var pageContext = event.getContext();
		var sObjectids = event.getIds();
		var host = event.getHost();
		var hostId = host.getId();
		var hostType = host.getIntType();
		var hostChnlId = hostType == 101 ? hostId : 0;
		sObjectids = (sObjectids.length!=0)?sObjectids:'0';
		if(bCopyall)sObjectids = '0';
		var channelids = event.getObjs().getPropertyAsString("channelid", 0);
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
		var nExcludeInfoView = 0;
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
				canEmpty : true,
				ValidInfoViewChannel : 1
		};
		if(bIsOneChannel){
			Ext.apply(args, {
				CurrChannelId : hostChnlId || tmpChannelid || 0,
				ExcludeSelf : 0
			});
		}
		FloatPanel.open({
			src : WCMConstants.WCM6_PATH + 'include/channel_select.html',
			title : wcm.LANG['INFOVIEW_DOC_119'] || '文档-文档复制到...',
			callback : function(selectIds, selectChnlDescs){
				if(!selectIds||selectIds.length==0) {
					Ext.Msg.$alert(wcm.LANG['INFOVIEW_DOC_120'] || '请选择当前文档要复制到的目标栏目!');
					return false;
				}
				var nFromChnlId = bCopyall==true ? hostChnlId:event.getObj().getPropertyAsInt('currchnlid');
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
				ProcessBar.start(wcm.LANG['INFOVIEW_DOC_177'] || "复制文档");
				oHelper.Call('wcm6_viewdocument',(sObjectids=='0')?'copyAll':'copy',oPostData,true,
					function(_transport,_json){
						ProcessBar.close();
						Ext.Msg.report(_json, wcm.LANG['INFOVIEW_DOC_121'] || '文档复制结果', func);
						FloatPanel.hide();
					},
					function(_transport,_json){
						ProcessBar.close();
						$render500Err(_transport,_json);
						FloatPanel.close();
					}
				);
			},
			dialogArguments : args
		});
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
	setUserDesignFields : function(event) {
		var docIds = event.getIds().join();
		var m_nCurrChannelId = event.getHost().getId() || 0;
		var pageParams = event.getContext().pageDataParams;
		var oArgs = {
			ChannelId : m_nCurrChannelId,
			SelectedFields : pageParams.m_sCurrOutlineFields
		};
		var sUrl = WCMConstants.WCM6_PATH + 'infoview/infoview_fields_select_order.jsp';
		var cb = wcm.CrashBoard.get({
			id : 'cb_setUserDesignFields',
			title : wcm.LANG['INFOVIEW_DOC_13'] || '自定义视图',
			url : sUrl,
			width: '680px',
			height: '370px',
			params : oArgs,
			callback : function(sResult){
				if(sResult === false || sResult===null || sResult===window.undefined){
					return;
				}
				if(!sResult) {
					sResult = '';
				}
				if(sResult.byteLength()>1000){
					Ext.Msg.$alert(wcm.LANG['INFOVIEW_DOC_122'] || '设置的缺省视图字段超长，长度最大限制为1000.');
					return;
				}
				cb.close();
				var frmOutlineFields = document.getElementById("frmOutlineFields");
				frmOutlineFields.SelectedFields.value = sResult;
				frmOutlineFields.url.value = location.href;
				frmOutlineFields.submit();
				return false;
			}
		});
		cb.show();
	},
	'export' : function(event, elToolbar){
		var exportExcels = [];
		exportExcels.push({
			desc : wcm.LANG['INFOVIEW_DOC_15'] || '导出Excel',
			oprKey : 'item0',
			cmd : function(){
				exportExcel(event);
			}
		});
		exportExcels.push({
			desc : wcm.LANG['INFOVIEW_DOC_123'] || '导出Excel...(选中行)',
			oprKey : 'item1',
			cmd : function(){
				 exportExcel(event, true);
			}
		});
		/*
		exportExcels.push({
			desc : wcm.LANG['INFOVIEW_DOC_124'] || '导出当前视图',
			oprKey : 'item2',
			cmd : function(){
				 exportCurrent(event);
			}
		});
		exportExcels.push({
			desc : wcm.LANG['INFOVIEW_DOC_125'] || '导出当前视图...(选中行)',
			oprKey : 'item3',
			cmd : function(){
				 exportCurrent(event, true);
			}
		}); 
		*/
		simpleMenu = new com.trs.menu.SimpleMenu({sBaseCls : 'exportExcelsbox'});
		simpleMenu.show(exportExcels || [], {x:elToolbar.offsetLeft,y:elToolbar.offsetTop+50});

	},
	exportForSelect : function(event){
		exportExcel(event, true);
	},
	exportExcelForAll : function(event){
		exportExcel(event, false);
	},
	quoteDoc : function(event){
		var oPostData = {
				'objectids' : event.getIds(),
				'channelids' : event.getObj().getPropertyAsInt('channelid'),
				'channelType' : 13
			}
			FloatPanel.open(WCMConstants.WCM6_PATH +
					'document/document_quoteto.html?' + $toQueryStr(oPostData),
					wcm.LANG['INFOVIEW_DOC_126'] || '文档-文档引用到...');
	},
	edit : function(event){
		var host = event.getHost();
		var oParams = {
			DocumentId : event.getObj().getPropertyAsInt('docid'),
			ChannelId :event.getObj().getPropertyAsInt('channelid'),
			SiteId :host.getType()==WCMConstants.OBJ_TYPE_WEBSITE?host.getId():0,
			FromEditor : 1
		};
		openEditor(oParams);
	},
	docpositionset : function(event){
		var oPageParams = event.getContext();
		var host = event.getHost();
		var hostid = host.getId();
		var objType = event.getObjs().getAt(0).objType;
		var docid = event.getObjs().getAt(0).getPropertyAsInt("docId", 0);
		Object.extend(oPageParams,host.getType()=="website"?{"siteid" : hostid}:{"channelid" : hostid});
		Object.extend(oPageParams,{"DocumentId":docid,"DocType":objType});
		FloatPanel.open(WCMConstants.WCM6_PATH + 'document/document_position_set.jsp?' + $toQueryStr(oPageParams),
		wcm.LANG.INFOVIEW_DOC_161 || '表单-调整顺序', CMSObj.afteredit(event));
	},
	view : function(event){
		var pageContext = event.getContext();
		var host = event.getHost();
		var hostType = host.getIntType();
		var hostId = host.getId();
		var oParams = {
			DocumentId : event.getObj().getPropertyAsInt('docid'),
			ChannelId :event.getObj().getPropertyAsInt('channelid'),
			ChnlDocId : event.getIds(),
			FromRecycle : pageContext.fromRecycle || 0
		};
		$openMaxWin(WCMConstants.WCM6_PATH +
				'document/document_show.jsp?' + $toQueryStr(oParams));
	},
	logo : function(event){
		var oParams = {
			HostId : event.getObj().getPropertyAsInt('docid'),
			HostType :605
		};
		$openCenterWin(WCMConstants.WCM6_PATH + 
			'logo/logo_list.jsp?' + $toQueryStr(oParams),"infoviewdoc_logo", 900, 600, "resizable=yes");
	},
	"import" : function(event){
		var oParams = Ext.apply({
			DocumentId : event.getObj().getPropertyAsInt('docid')
		}, parseHost(event.getHost()));
		if(oParams.SiteId!=0){
			FloatPanel.open(WCMConstants.WCM6_PATH +
				'document/document_siteimport_step1.html?' + $toQueryStr(oParams),
				wcm.LANG.INFOVIEW_DOC_155 || '选择要文档导入的目标栏目',openImport.bind(this,event));
			return;
		}
		FloatPanel.open(WCMConstants.WCM6_PATH +
				'document/document_import.jsp?' + $toQueryStr(oParams),
				wcm.LANG.INFOVIEW_DOC_156 || '文档-导入文档',CMSObj.afteradd(event));
	},
	startFlow : function(event){
		Ext.Msg.confirm(wcm.LANG['INFOVIEW_DOC_185'] || '您确实要将当前文档投入流转吗？', {
			yes : function(){
				startDocInFlow(event);
			},
			no : function(){
				 return;
			}
		});
	},
	setright : function(event){
		var docId = event.getObj().getPropertyAsInt('docid', 0);
		$openCenterWin(WCMConstants.WCM6_PATH + "auth/right_set.jsp?ObjType=605&ObjId=" + docId,
				"document_right_set", 900, 600, "resizable=yes");
	},
	changestatus : function(event){
		var oPageParams = event.getContext(); 
		var sId = event.getIds().join();
		var nChannelId = event.getObj().getPropertyAsInt("channelid", 0);
		Object.extend(oPageParams,{
			"ObjectIds":sId,
			"IsPhoto":false,
			'ChannelIds': nChannelId});
		FloatPanel.open(WCMConstants.WCM6_PATH +'document/change_status.jsp?' + $toQueryStr(oPageParams), wcm.LANG['INFOVIEW_DOC_186'] || '文档-改变状态', CMSObj.afteredit(event));
	},
	detailPublish : function(event){
		publish(event.getIds(), 'detailPublish');
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
	recallPublish : function(event){
		var sHtml = String.format("确实要{0}撤销发布{1}这篇文档么？将{2}不可逆转！{3}",'<font color=\'red\' style=\'font-size:14px;\'>','</font>','<font color=\'red\' style=\'font-size:14px;\'>','</font>');
		Ext.Msg.confirm(sHtml,{
			yes : function(){
			   publish(event.getIds(), 'recallPublish');
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
	PublishInfo : function(event){
		var nId = event.getObj().getPropertyAsInt('docid', 0);
		var oArgs = {
			FolderType : 605,
			FolderId : nId
		}
		var sUrl = WCMConstants.WCM6_PATH + 'infoview/publishtask_get_by_type_id.jsp';
		var cb = wcm.CrashBoard.get({
			id : 'INFOVIEWDOC_PUBLISHIINFO',
			title : wcm.LANG['INFOVIEW_DOC_187'] || '发布历史信息显示',
			url : sUrl,
			width: '600px',
			height: '400px',
			params : oArgs,
			callback : function(_args){
			}
		});
		cb.show();
	},
	'exportDoc' : function(event){
		var oPostData = {
				'objectids' : event.getIds(),
				'channelids' : event.getObj().getPropertyAsInt("channelid", 0)
			};
		FloatPanel.open(WCMConstants.WCM6_PATH +
				'document/document_export.jsp?' + $toQueryStr(oPostData), wcm.LANG['INFOVIEW_DOC_188'] || '文档-导出文档');
	},
	backup : function(event){
		var oPostData = {
			docids: event.getObj().getPropertyAsInt('docid', 0),
			ExcludeTrashed: true
		};
		getHelper().call('wcm6_documentBak','backup', oPostData, true,
			function(_transport,_json){
				Ext.Msg.report(_json,wcm.LANG['INFOVIEW_DOC_189'] || '文档版本保存结果');
			}
		);
	},
	exportall : function(event){
		if(confirm(wcm.LANG['INFOVIEW_DOC_190'] || '此操作可能需要较长时间.确实要导出所有文档吗?')) {
			var oPostData = Ext.apply({
				ExportAll: 1
			}, parseHost(event.getHost()));
			var context = event.getContext();
			var dialogArguments = Ext.apply({}, context.get("pagecontext").params);
			Ext.apply(dialogArguments,{PAGESIZE:-1});
			FloatPanel.open(
				WCMConstants.WCM6_PATH + 'document/document_export.jsp?' + $toQueryStr(oPostData),
				wcm.LANG['INFOVIEW_DOC_191'] || '文档-导出所有文档',
				null,
				dialogArguments
			);
		}	
	},
	changelevel : function(event){
		var oPageParams = event.getContext();
		Object.extend(oPageParams,{
			"ObjectIds": event.getObjs().getPropertyAsInt('docid', 0),
			"IsPhoto":false
		});
		FloatPanel.open(WCMConstants.WCM6_PATH +'document/change_doclevel.jsp?' + $toQueryStr(oPageParams), '文档-改变密级', CMSObj.afteredit(event));
	},
	setTop : function(event){
		var docId = event.getObj().getPropertyAsInt('docid',0);
		var channelId = event.getObj().getPropertyAsInt('channelid', 0);
		var params = {
			DocumentId : docId,
			ChannelId : channelId
		}
		FloatPanel.open(WCMConstants.WCM6_PATH +'document/document_topset.jsp?' + $toQueryStr(params), '设置置顶', CMSObj.afteredit(event));
	}
})

})();

(function(){
	var pageObjMgr = wcm.domain.InfoviewDocMgr;
	var reg = wcm.SysOpers.register;
	var fnIsVisible = function(event){
			var host = event.getHost();
			if(Ext.isTrue(host.isVirtual)){
				return false;
			}
			return true;
	};
	
	reg({
		key : 'new',
		type : 'infoviewdocInChannel',
		isHost : true,
		desc :  wcm.LANG['INFOVIEW_DOC_127'] || '新建表单文档',
		title : wcm.LANG['INFOVIEW_DOC_127'] || '新建表单文档',
		rightIndex : 31,
		order : 1,
		fn : pageObjMgr['new'],
		quickKey : 'N'
	});
	reg({
		key : 'copy',
		type : 'infoviewdocInChannel',
		isHost : true,
		desc :  wcm.LANG['INFOVIEW_DOC_153'] || '复制所有文档到',
		title : wcm.LANG['INFOVIEW_DOC_153'] || '复制所有文档到',
		rightIndex : 57,
		order : 2,
		fn : pageObjMgr['copyall']
	});
	reg({
		key : 'move',
		type : 'infoviewdocInChannel',
		isHost : true,
		desc : wcm.LANG.INFOVIEW_DOC_162 ||'移动所有文档到',
		title : wcm.LANG.INFOVIEW_DOC_162 ||'移动所有文档到',
		rightIndex : 56,
		order : 3,
		fn : pageObjMgr['moveall']
	});
	reg({
		key : 'exportall',
		type : 'infoviewdocInChannel',
		desc : wcm.LANG.INFOVIEW_DOC_192 || '导出所有文档',
		title:'导出所有文档...',
		rightIndex : 34,
		order : 4,
		fn : pageObjMgr['exportall'],
		quickKey : 'X'
	});
	reg({
		key : 'import',
		type : 'infoviewdocInChannel',
		isHost : true,
		desc : wcm.LANG.INFOVIEW_DOC_154 ||'从外部导入文档',
		title : wcm.LANG.INFOVIEW_DOC_154 ||'从外部导入文档',
		rightIndex : 31,
		order : 4,
		fn : pageObjMgr['import']
	});
	reg({
		key : 'setuserdesignfields',
		type : 'infoviewdocInChannel',
		isHost : true,
		desc : wcm.LANG['INFOVIEW_DOC_13'] || '自定义视图',
		title : wcm.LANG['INFOVIEW_DOC_13'] || '自定义视图',
		rightIndex : 13,
		order : 5,
		fn : pageObjMgr['setUserDesignFields']
	});
	reg({
		key : 'exportexcelall',
		type : 'infoviewdocInChannel',
		isHost : true,
		desc : wcm.LANG['INFOVIEW_DOC_15'] || '导出Excel',
		title : wcm.LANG['INFOVIEW_DOC_149'] || '导出所有',
		rightIndex : 30,
		order : 6,
		fn : pageObjMgr['exportExcelForAll']
	});
	reg({
		key : 'edit',
		type : 'infoviewdoc',
		desc : wcm.LANG['INFOVIEW_DOC_128'] || '修改这篇文档',
		title : wcm.LANG['INFOVIEW_DOC_128'] || '修改这篇文档',
		rightIndex : 32,
		order : 1,
		fn : pageObjMgr['edit'],
		quickKey : 'M'
	});
	reg({
		key : 'preview',
		type : 'infoviewdoc',
		desc :  wcm.LANG['INFOVIEW_DOC_129'] || '预览这篇文档',
		title : wcm.LANG['INFOVIEW_DOC_130'] || '预览这篇文档发布效果',
		rightIndex : 38,
		order : 2,
		fn : pageObjMgr['preview'],
		quickKey : 'R'
	});
	reg({
		key : 'copy',
		type : 'infoviewdoc',
		desc : wcm.LANG['INFOVIEW_DOC_133'] || '复制这篇文档到',
		title : wcm.LANG['INFOVIEW_DOC_133'] || '复制这篇文档到',
		rightIndex : 34,
		order : 3,
		fn : pageObjMgr['copy']
	});
	reg({
		key : 'quote',
		type : 'infoviewdoc',
		desc : wcm.LANG['INFOVIEW_DOC_134'] ||'引用这篇文档到',
		title : wcm.LANG['INFOVIEW_DOC_134'] ||'引用这篇文档到',
		rightIndex : 34,
		order : 4,
		fn : pageObjMgr['quoteDoc']
	});
	reg({
		key : 'move',
		type : 'infoviewdoc',
		desc : wcm.LANG['INFOVIEW_DOC_135'] || '移动这篇文档到',
		title : wcm.LANG['INFOVIEW_DOC_135'] || '移动这篇文档到',
		rightIndex : 33,
		order : 5,
		fn : pageObjMgr['move']
	});
	reg({
		key : 'basicpublish',
		type : 'infoviewdoc',
		desc : wcm.LANG['INFOVIEW_DOC_131'] ||'发布这篇文档',
		title :  wcm.LANG['INFOVIEW_DOC_132'] ||'发布这篇文档，生成这篇文档的细览页面以及更新相关概览页面',
		rightIndex : 39,
		order : 6,
		fn : pageObjMgr['basicpublish'],
		quickKey : 'P'
	});
	reg({
		key : 'detailpublish',
		type : 'infoviewdoc',
		desc : wcm.LANG.INFOVIEW_DOC_163 ||'仅发布这篇文档细览',
		title : wcm.LANG.INFOVIEW_DOC_164 ||'仅发布这篇文档细览,仅重新生成这篇文档的细览页面',
		rightIndex : 39,
		order : 7,
		fn : pageObjMgr['detailPublish']
	});
	reg({
		key : 'directpublish',
		type : 'infoviewdoc',
		desc : wcm.LANG.INFOVIEW_DOC_204 ||'直接发布这篇文档',
		title : wcm.LANG.INFOVIEW_DOC_205 ||'发布这篇文档,同时发布此文档的所有引用文档',
		rightIndex : 39,
		order : 7,
		fn : pageObjMgr['directpublish']
	});
	reg({
		key : 'recallpublish',
		type : 'infoviewdoc',
		desc : wcm.LANG['INFOVIEW_DOC_169'] ||'撤销发布这篇文档',
		title :  wcm.LANG['INFOVIEW_DOC_170'] ||'撤销发布这篇文档，撤回已发布目录或页面',
		rightIndex : 39,
		order : 8,
		fn : pageObjMgr['recallPublish']
	});
	reg({
		key : 'directRecallpublish',
		type : 'infoviewdoc',
		desc : wcm.LANG.INFOVIEW_DOC_206 ||'直接撤销发布这篇文档',
		title : wcm.LANG.INFOVIEW_DOC_207 ||'撤回当前文档的发布页面，并同步撤销文档的所有引用以及原文档发布页面',
		rightIndex : 39,
		order : 8,
		fn : pageObjMgr['directRecallpublish']
	});
	reg({
		key : 'publishinfo',
		type : 'infoviewdoc',
		desc : wcm.LANG['INFOVIEW_DOC_171'] || '发布历史',
		title : wcm.LANG['INFOVIEW_DOC_171'] || '发布历史',
		rightIndex : 39,
		order : 9,
		fn : pageObjMgr['PublishInfo']
	});
	reg({
		key : 'trash',
		type : 'infoviewdoc',
		desc : wcm.LANG['INFOVIEW_DOC_136'] || '删除这篇文档',
		title : wcm.LANG['INFOVIEW_DOC_137'] || '删除这篇文档',
		rightIndex : 33,
		order : 10,
		fn : pageObjMgr['trash'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'exportexcelselectone',
		type : 'infoviewdoc',
		desc : wcm.LANG['INFOVIEW_DOC_15'] || '导出Excel',
		title : wcm.LANG['INFOVIEW_DOC_15'] || '导出Excel',
		rightIndex : 30,
		order : 11,
		fn : pageObjMgr['exportForSelect']
	});
	reg({
		key : 'export',
		type : 'infoviewdoc',
		desc : wcm.LANG['INFOVIEW_DOC_172'] || '导出这篇文档',
		title : wcm.LANG['INFOVIEW_DOC_172'] || '导出这篇文档',
		rightIndex : 30,
		order : 12,
		fn : pageObjMgr['exportDoc']
	});		
	reg({
		key : 'startflow',
		type : 'infoviewdoc',
		desc : wcm.LANG['INFOVIEW_DOC_193'] || '开始流转',
		title : wcm.LANG['INFOVIEW_DOC_193'] || '开始流转',
		rightIndex : 31,
		order : 13,
		fn : pageObjMgr['startFlow'],
		isVisible : function(event){
			if(event.getObj().getPropertyAsString("CanInFlow")=='true')
				return true;
			return false;
		}
	});
	reg({
		key : 'changelevel',
		type : 'infoviewdoc',
		desc :  wcm.LANG.DOCUMENT_PROCESS_273 || '改变这篇文档的密级',
		title : '改变这篇文档的密级...',
		rightIndex : 61,
		order : 14,
		fn : pageObjMgr['changelevel']
	});
	reg({
		key : 'settop',
		type : 'infoviewdoc',
		desc :  '设置置顶',
		title : '设置置顶',
		rightIndex : 62,
		order : 15,
		fn : pageObjMgr['setTop']
	});
	reg({
		key : 'preview',
		type : 'infoviewdocs',
		desc : wcm.LANG['INFOVIEW_DOC_194'] || '预览这些文档',
		title : wcm.LANG['INFOVIEW_DOC_195'] || '预览这些文档发布效果',
		rightIndex : 38,
		order : 1,
		fn : pageObjMgr['preview'],
		quickKey : 'R'
	});
	reg({
		key : 'basicpublish',
		type : 'infoviewdocs',
		desc : wcm.LANG['INFOVIEW_DOC_138'] || '发布这些文档',
		title : wcm.LANG['INFOVIEW_DOC_139'] || '发布这些文档，生成这些文档的细览页面以及更新相关概览页面',
		rightIndex : 39,
		order : 2,
		fn : pageObjMgr['basicpublish'],
		quickKey : 'P'
	});
	reg({
		key : 'detailpublish',
		type : 'infoviewdocs',
		desc : wcm.LANG['INFOVIEW_DOC_202'] ||'仅发布这些文档细览',
		title :  wcm.LANG['INFOVIEW_DOC_203'] ||'仅发布这些文档细览，仅重新生成这些文档的细览页面',
		rightIndex : 39,
		order : 3,
		fn : pageObjMgr['detailPublish']
	});
	reg({
		key : 'directpublish',
		type : 'infoviewdocs',
		desc : wcm.LANG.INFOVIEW_DOC_208 ||'直接发布这些文档',
		title : wcm.LANG.INFOVIEW_DOC_209 ||'发布这些文档，同步发布这些文档所有的引用文档',
		rightIndex : 39,
		order : 3,
		fn : pageObjMgr['directpublish']
	});
	reg({
		key : 'recallpublish',
		type : 'infoviewdocs',
		desc : wcm.LANG['INFOVIEW_DOC_167'] ||'撤销发布这些文档',
		title :  wcm.LANG['INFOVIEW_DOC_168'] ||'撤销发布这些文档，撤回已发布目录或页面',
		rightIndex : 39,
		order : 4,
		fn : pageObjMgr['recallPublish']
	});
	reg({
		key : 'directRecallpublish',
		type : 'infoviewdocs',
		desc : wcm.LANG.DOCUMENT_PROCESS_210 ||'直接撤销发布这些文档',
		title : wcm.LANG.DOCUMENT_PROCESS_211 ||'撤回这些文档的发布页面，并同步撤销文档的所有引用以及原文档发布页面',
		rightIndex : 39,
		order : 4,
		fn : pageObjMgr['directRecallpublish']
	});
	reg({
		key : 'copy',
		type : 'infoviewdocs',
		desc : wcm.LANG['INFOVIEW_DOC_140'] || '复制这些文档到',
		title : wcm.LANG['INFOVIEW_DOC_140'] || '复制这些文档到',
		rightIndex : 34,
		order : 5,
		fn : pageObjMgr['copy']
	});
	reg({
		key : 'move',
		type : 'infoviewdocs',
		desc : wcm.LANG['INFOVIEW_DOC_141'] || '移动这些文档到',
		title : wcm.LANG['INFOVIEW_DOC_141'] || '移动这些文档到',
		rightIndex : 33,
		order : 6,
		fn : pageObjMgr['move']
	});
	reg({
		key : 'quote',
		type : 'infoviewdocs',
		desc : wcm.LANG['INFOVIEW_DOC_142'] || '引用这些文档到',
		title : wcm.LANG['INFOVIEW_DOC_142'] || '引用这些文档到',
		rightIndex : 34,
		order : 7,
		fn : pageObjMgr['quoteDoc']
	});
	reg({
		key : 'changestatus',
		type : 'infoviewdocs',
		desc : wcm.LANG['INFOVIEW_DOC_196'] || '改变这些文档的状态',
		title : wcm.LANG['INFOVIEW_DOC_196'] || '改变这些文档的状态',
		rightIndex : 35,
		order : 7,
		fn : pageObjMgr['changestatus']
	});
	reg({
		key : 'setright',
		type : 'infoviewdocs',
		desc : wcm.LANG['INFOVIEW_DOC_197'] || '设置这些文档的权限',
		title : wcm.LANG['INFOVIEW_DOC_197'] || '设置这些文档的权限',
		rightIndex : 61,
		order : 8,
		fn : pageObjMgr['setright']
	});
	reg({
		key : 'exportexcelselectmore',
		type : 'infoviewdocs',
		desc : wcm.LANG['INFOVIEW_DOC_15'] || '导出Excel(选中行)',
		title : wcm.LANG['INFOVIEW_DOC_15'] || '导出Excel(选中行)',
		rightIndex : 30,
		order : 8,
		fn : pageObjMgr['exportForSelect']
	});
	reg({
		key : 'trash',
		type : 'infoviewdocs',
		desc : wcm.LANG['INFOVIEW_DOC_143'] ||'删除这些文档',
		title : wcm.LANG['INFOVIEW_DOC_143'] ||'删除这些文档',
		rightIndex : 33,
		order : 9,
		fn : pageObjMgr['trash'],
		quickKey : ['Delete', 'ShiftDelete']
	});
		reg({
		key : 'changelevel',
		type : 'infoviewdocs',
		desc : '改变这些文档的密级',
		title : '改变这些文档的密级...',
		rightIndex : 61,
		order : 10,
		fn : pageObjMgr['changelevel']
	});
	reg({
		key : 'export',
		type : 'infoviewdocs',
		desc :  wcm.LANG['INFOVIEW_DOC_198'] || '导出这些文档',
		title : wcm.LANG['INFOVIEW_DOC_199'] || '将当前文档导出成zip文件',
		rightIndex : 34,
		order : 10,
		fn : pageObjMgr['exportDoc'],
		quickKey : 'X'
	});
	reg({
		key : 'docpositionset',
		type : 'infoviewdoc',
		desc : wcm.LANG.INFOVIEW_DOC_160 ||'调整顺序',
		title : wcm.LANG.INFOVIEW_DOC_160 ||'调整顺序',
		rightIndex : 62,
		order : 9,
		fn : pageObjMgr['docpositionset'],
		isVisible : function(event){
			if(event.getHost().getType() == WCMConstants.OBJ_TYPE_CHANNEL)
				return true;
			return false;
		}
	});
	reg({
		key : 'logo',
		type : 'infoviewdoc',
		desc : wcm.LANG.INFOVIEW_DOC_152 ||'表单文档LOGO',
		title : wcm.LANG.INFOVIEW_DOC_152 ||'表单文档LOGO',
		rightIndex : 32,
		order : 10,
		fn : pageObjMgr['logo']
	});
	reg({
		key : 'setright',
		type : 'infoviewdoc',
		desc :  wcm.LANG.INFOVIEW_DOC_200 || '设置这篇文档的权限',
		title : wcm.LANG.INFOVIEW_DOC_200 || '设置这篇文档的权限',
		rightIndex : 61,
		order : 11,
		fn : pageObjMgr['setright']
	});
	reg({
		key : 'changestatus',
		type : 'infoviewdoc',
		desc :  wcm.LANG.INFOVIEW_DOC_201 || '改变状态',
		title : wcm.LANG.INFOVIEW_DOC_201 || '改变状态',
		rightIndex : 32,
		order : 12,
		fn : pageObjMgr['changestatus']
	});
})();