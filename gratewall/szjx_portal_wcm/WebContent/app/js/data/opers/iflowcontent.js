//流转文档操作信息和Mgr定义
Ext.ns('wcm.domain.IFlowContentMgr');
(function(){
	var m_oMgr = wcm.domain.IFlowContentMgr;
	function getHelper(){
		return new com.trs.web2frame.BasicDataHelper();
	}
	function promptOption(_sTitle, _params, event){
		var sTitle = _sTitle || (wcm.LANG['IFLOWCONTENT_55'] ||'处理我的工作');
		var sUrl = WCMConstants.WCM6_PATH + 'flowdoc/workflow_process_option.html';
		wcm.CrashBoarder.get('Process_Option').show({
			title :  sTitle,
			src : sUrl,
			width: '480px',
			height: '350px',
			params : _params,
			maskable : true,
			callback : function(){ 
				PageContext.refreshList(null, []);
			}
		});	
	}
	function render(event,reSubmited){
		var obj = event.getObjs().getAt(0);
		var nFlowDocId = obj.getPropertyAsInt('flowDocId',0);
		if(nFlowDocId==0 || nFlowDocId==null) return;
		var nFlagId = obj.getPropertyAsInt('flagid',0); 
		if(reSubmited &&
			(obj.getPropertyAsInt('worked',0) == 1 || nFlagId == 2 || nFlagId == 8)) { // 对于已经处理过的文档，不允许再重新指派
			return;
		}
		//ge gfc add @ 2007-10-18 11:43 如果是在处理表单的话，默认打开表单编辑页
		var sEditPage = obj.getPropertyAsString('editPage');
		if(!reSubmited) {
			if(sEditPage && sEditPage.indexOf('infoview/infoview_document_addedit.jsp') != -1) {
				$openMaxWin(WCMConstants.WCM6_PATH + "infoview/" + sEditPage + "&FlowDocId=" + nFlowDocId+"&WorklistViewType=1");
				return;
			}
		}
		var bAccepted = (obj.getPropertyAsInt('accepted', 0) != 0);
		var o_params = {
			FlowDocId: nFlowDocId,
			title: obj.getPropertyAsString('contentTitle'),
			ctype:  obj.getPropertyAsInt('contentType', 0),
			cid:   obj.getId(),
			resubmited: reSubmited || false
		};
		if(!reSubmited && sEditPage && sEditPage.indexOf("applyform_add_manager.jsp") > 0){//依申请公开的地址
			$openMaxWin(sEditPage + "&FlowDocId=" + nFlowDocId);
			return;
		}
		if(!reSubmited && sEditPage && sEditPage.indexOf("metaviewdata_addedit.jsp") > 0){
			$openMaxWin(sEditPage + "&FlowDocId=" + nFlowDocId + "&WorklistViewType=1#tabs-process");
			return;
		}
		//不是重新指派，并且在当前节点可编辑，有自己的编辑页面，则进入编辑页面，在此页面进行流转的处理
		if(!reSubmited && sEditPage && obj.getPropertyAsString("CanEditInFlow", 'false') == 'true' && sEditPage.indexOf("document_addedit.jsp") > 0){
			$openMaxWin(WCMConstants.WCM6_PATH + "document/" + sEditPage + "&FlowDocId=" + nFlowDocId+"&WorklistViewType=1");
		//否则，进入公共的处理页面
		}else{
			var sTitle = reSubmited ? (wcm.LANG['IFLOWCONTENT_56'] ||'重新指派') : (wcm.LANG['IFLOWCONTENT_57'] ||'处理文档');
			var sUrl = WCMConstants.WCM6_PATH + 'flowdoc/workflow_process_render.jsp';
			wcm.CrashBoarder.get('Process_Render').show({
				title :  sTitle,
				src : sUrl,
				width: '600px',
				height: '555px',
				params : o_params,
				maskable : true,
				callback : function(){
					//$MsgCenter.$main().afteredit();
					CMSObj.afteredit(event)();
				}
			}); 
		}
	}

	function reflow(_params){
		var sTitle = wcm.LANG['IFLOWCONTENT_96'] || "将文档重新投入流转";
		var sUrl = WCMConstants.WCM6_PATH + 'flowdoc/workflow_process_reflow.jsp';
		wcm.CrashBoarder.get('Process_Reflow').show({
			title :  sTitle,
			src : sUrl,
			width: '480px',
			height: '450px',
			params : _params,
			maskable : true,
			callback : function(_args){
				_startDocInFlow(_args);
			}
		});
	}

	function _startDocInFlow(_params){
		var postData = {};
		if(_params.reflow){
			postData = _params;
		}
		else{
			postData = {objectid: _params['ContentId']}
		} 
		BasicDataHelper.call('wcm6_document', 'startDocumentInFlow', postData, true, function(){
			 PageContext.refreshList(null, []);
		});
	}
	Ext.apply(wcm.domain.IFlowContentMgr, {
		/**
		 * @param : wcm.CMSObjEvent event
		 */
		sign : function(event){
			if(event.length()==0){
				Ext.Msg.$alert(wcm.LANG['IFLOWCONTENT_73'] || '必须选中至少一篇文档!');
				return;
			}
			var ids = event.getObjs().getPropertyAsInt("flowdocid",0);
			var accepteds = event.getObjs().getPropertyAsInt("accepted",0);
			for(var index=0; index<accepteds.length; index++){
				var accepted = accepteds[index];
				if(accepted!=0){
					Ext.Msg.$alert(wcm.LANG['IFLOWCONTENT_54'] || '只有尚未签收的文档才能进行签收操作！');
					return;
				}
			}
			var oPostData = {
				ObjectIds: ids
			};
			BasicDataHelper.call('wcm6_process', 'doAccept', oPostData, true, function(_trans, _json){
				//PageContext.refreshList(null, []);
				CMSObj.afteredit(event)();
				//PageContext.updateCurrRows(event.getIds());
			});
		},
		refuse : function(event){
			if(event.length()==0){
				Ext.Msg.$alert(wcm.LANG['IFLOWCONTENT_73'] || '必须选中至少一篇文档!');
				return;
			}
			var ids = event.getObjs().getPropertyAsInt("flowdocid",0).join();
			var titles = event.getObjs().getPropertyAsString("contenttitle").join();
			var _params = {
				ObjectIds: ids,
				titles : titles,
				option : 'refuse',
				Action : 'refuse'
			}
			promptOption(wcm.LANG['IFLOWCONTENT_102'] ||'拒绝', _params,event);
		},
		rework : function(event){
			if(event.length()==0){
				Ext.Msg.$alert(wcm.LANG['IFLOWCONTENT_73'] || '必须选中至少一篇文档！');
				return;	
			}
			var ids = event.getObjs().getProperty("flowdocid").join();
			var titles = event.getObjs().getProperty("contenttitle").join();
			var _params = {
				ObjectIds: ids,
				titles : titles,
				option : 'rework',
				Action : 'backTo'
			}
			promptOption(wcm.LANG['IFLOWCONTENT_103'] ||'要求返工', _params,event);
		},
		dealing : function(event){
			render(event,false);
		},
		reasign : function(event){
			render(event,true);
		},
		cease : function(event){
			if(event.length()==0)return;
			var obj = event.getObjs();
			var nFlowDocId = obj.getPropertyAsInt("flowdocid",0).join();
			var titles = obj.getPropertyAsString("contenttitle").join();
			var nIsEnd = obj.getPropertyAsInt("isend",0).join();
			if(nIsEnd == 1){
				reflow({
					ContentId: obj.getIds(),
					ContentType : obj.getPropertyAsInt("contenttype",0),
					doctitle : obj.getAt(0).getPropertyAsString("contenttitle")
				});
				return;
			}
			var params = {
				ObjectIds: nFlowDocId,
				flowid: nFlowDocId,
				titles:titles,
				docs: [
					{id: nFlowDocId, title:titles}
				],
				ctype:  obj.getPropertyAsInt("contenttype",0),
				cid:  obj.getIds(),
				option: 'cease'
			};
			promptOption(wcm.LANG['IFLOWCONTENT_3'] || '结束文档流转', params,event);
		},
		edit : function(event){
			if(event.length()==0)return;
			var editpage = event.getObjs().getProperty("editPage")[0];
			var nFlowDocId = event.getObjs().getProperty("flowdocid");
			//wenyh@2007-08-01 图片使用FloatPanel打开(在目标页面进行是否从工流打开困难).
			if(editpage.indexOf("../photo/photodoc_edit.html?") == 0){					
				FloatPanel.open(WCMConstants.WCM6_PATH + "photo/photodoc_edit.jsp"+editpage.substr(editpage.indexOf("?")).replace("DocumentId","DocId") + "&FlowDocId=" + nFlowDocId, wcm.LANG['IFLOWCONTENT_59'] || "编辑图片信息",680,350);
			}else if(editpage.indexOf("photo/photo_upload.jsp") >= 0){					
				FloatPanel.open(WCMConstants.WCM6_PATH + "photo/photodoc_edit.jsp"+editpage.substr(editpage.indexOf("?")).replace("DocumentId","DocId") + "&FlowDocId=" + nFlowDocId, wcm.LANG['IFLOWCONTENT_59'] || "编辑图片信息",680,350);
			}
			//fjh@2007-11-5 视频文件编辑窗口不是最大化
			else if(editpage.indexOf("../video/video_addedit.jsp?") == 0){
				window.open(editpage + "&FlowDocId=" + nFlowDocId,'_blank', 'width=800,height=560,location=no');
			}else{
				$openMaxWin(WCMConstants.WCM6_PATH + "document/" + editpage + "&FlowDocId=" + nFlowDocId);
			}
		},
		deletedoc : function(event){
			if(event.length()==0) return;
			var nFlowDocId = event.getObjs().getPropertyAsInt("flowdocid",0);
			Ext.Msg.confirm(wcm.LANG['IFLOWCONTENT_60'] || '确认是要删除当前流转的文档吗？', {
				yes : function(){
					var objs = event.getObjs();
					var sErrorMsg = "";
					for(var i=0 ;i<objs.size();i++){
						var obj = event.getObjs().getAt(i);
						var nDocId = obj.getPropertyAsInt("docid",0);
						var flowdocid = obj.getPropertyAsInt("flowdocid",0);
						var delPage = obj.getProperty("deletepage");
						if(!flowdocid || !delPage) return;
						if(delPage && delPage.indexOf('center.do') == -1 && delPage.indexOf('.jsp') == -1) {
							delPage = BasicDataHelper['WebService'] + '?' + delPage;
						}
						var sActionUrl = delPage  + "&FlowDocId=" + flowdocid;
						var ajaxRequest = new Ajax.Request(
							sActionUrl,
							{
								onSuccess: function(){
									//PageContext.refreshList(null, []);
									if(i == event.length()){
										CMSObj.afterdelete(event)();
									}
								},
								onFailure :function(){
									if(!sErrorMsg){
										sErrorMsg +="删除id为：[";
									}
									sErrorMsg += nDocId ;
								},
								asyn:true
							}
						);
						if(sErrorMsg && (i ==event.length())){
							if(sErrorMsg.charAt(length -1) == ","){
								sErrorMsg = sErrorMsg.subString(sErrorMsg,sErrorMsg.length-1);
							}
							sErrorMsg +="]的文档失败！！！";
						}
					}
				},
				no : function(){
					return;
				}
			});
		},
		show : function(event){
			if(event.length()==0)return;
			var obj = event.getObjs().getAt(0);
			var showPage = obj.getPropertyAsString("showpage");
			if(showPage.indexOf("../video/player.jsp?") == 0){
				window.open('../video/player.jsp?docId=' + obj.getId() , '_blank', 'width=800,height=560,location=yes');
			}else{	// 默认为查看
				var ix = showPage.indexOf('../document/document_detail_show.html');
				if(ix == 0){
					var len = '../document/document_detail_show.html'.length;
					showPage = '../document/document_detail.jsp' + showPage.substr(len);
				}
				var nFlowDocId = obj.getPropertyAsInt("trueFlowDocId",0) > 0 ? obj.getPropertyAsInt("trueFlowDocId",0) : obj.getPropertyAsInt("flowdocid",0);
				if(showPage.indexOf('?') != -1){
					showPage += "&FlowDocId=" + nFlowDocId;
				}else{
					showPage += "?FlowDocId=" + nFlowDocId;
				}
				$openMaxWin(showPage);
			}
		},
		detailPublish : function(event){
			var objs = event.getObjs();
			BasicDataHelper.call('wcm6_publish', 'detailPublish', {
				objectids	: objs.getIds(),
				objecttype	: objs.getPropertyAsInt("contentType",0),
				FlowDocId	: objs.getPropertyAsInt("flowDocId",0)
			}, true, function(_trans, _json){
				//PageContext.RefreshList();
				Ext.Msg.$timeAlert(wcm.LANG['IFLOWCONTENT_61'] || '已将您的发布任务提交到后台！', 3);
			})
		}
		//type here
	});
})();

(function(){
	var pageObjMgr = wcm.domain.IFlowContentMgr;
	var reg = wcm.SysOpers.register;
	reg({
		key : 'show',
		type : 'iflowcontent',
		desc : wcm.LANG['IFLOWCONTENT_68'] || '查看这篇文档',
		rightIndex : -1,
		order : 5,
		fn : pageObjMgr['show']
	});
	reg({
		key : 'edit',
		type : 'iflowcontent',
		desc : wcm.LANG['IFLOWCONTENT_69'] || '编辑这篇文档',
		rightIndex : 0,
		order : 6,
		fn : pageObjMgr['edit'],
		isVisible : function(event){
			var context = event.getContext();
			if(context.ViewType==1)return true;
			return false;	 
		},
		quickKey : 'M'
	});
	reg({
		key : 'deletedoc',
		type : 'iflowcontent',
		desc : wcm.LANG['IFLOWCONTENT_70'] || '删除这篇文档',
		title : wcm.LANG['IFLOWCONTENT_70'] || '删除这篇文档',
		rightIndex : 1,
		order : 7,
		fn : pageObjMgr['deletedoc'],
		isVisible : function(event){
			var context = event.getContext();
			if(context.ViewType==1)return true;
			return false;	 
		},
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'detailPublish',
		type : 'iflowcontent',
		desc : wcm.LANG['IFLOWCONTENT_71'] || '发布这篇文档',
		rightIndex : 2,
		order : 8,
		fn : pageObjMgr['detailPublish'],
		isVisible : function(event){
			var context = event.getContext();
			if(context.ViewType==1)return true;
			return false;	 
		},
		quickKey : 'P'
	});
	reg({
		key : 'sign',
		type : 'iflowcontent',
		desc : wcm.LANG['IFLOWCONTENT_75'] || '签收工作流文档',
		title : wcm.LANG['IFLOWCONTENT_75'] || '签收工作流文档',
		rightIndex : -1,
		order : 1,
		fn : pageObjMgr['sign'],
		isVisible : function(event){
			var context = event.getContext();
			if(context.ViewType==1)return true;
			return false;
		}
	});
	reg({
		key : 'sign',
		type : 'iflowcontents',
		desc : wcm.LANG['IFLOWCONTENT_75'] || '签收工作流文档',
		title : wcm.LANG['IFLOWCONTENT_75'] || '签收工作流文档',
		rightIndex : -1,
		order : 1,
		fn : pageObjMgr['sign'],
		isVisible : function(event){
			var context = event.getContext();
			if(context.ViewType==1)return true;
			return false;
		}
	});
	reg({
		key : 'refuse',
		type : 'iflowcontent',
		desc : wcm.LANG['IFLOWCONTENT_76'] || '拒绝工作流文档',
		title : wcm.LANG['IFLOWCONTENT_76'] || '拒绝工作流文档',
		rightIndex : -1,
		order : 2,
		fn : pageObjMgr['refuse'],
		isVisible : function(event){
			var context = event.getContext();
			if(context.ViewType==1)return true;
			return false;
		}
	});
	reg({
		key : 'refuse',
		type : 'iflowcontents',
		desc : wcm.LANG['IFLOWCONTENT_76'] || '拒绝工作流文档',
		title : wcm.LANG['IFLOWCONTENT_76'] || '拒绝工作流文档',
		rightIndex : -1,
		order : 2,
		fn : pageObjMgr['refuse'],
		isVisible : function(event){
			var context = event.getContext();
			if(context.ViewType==1)return true;
			return false;
		}
	});
	reg({
		key : 'rework',
		type : 'iflowcontent',
		desc : wcm.LANG['IFLOWCONTENT_64'] || '返工工作流文档',
		title : wcm.LANG['IFLOWCONTENT_64'] || '返工工作流文档',
		rightIndex : -1,
		order : 3,
		fn : pageObjMgr['rework'],
		isVisible : function(event){
			var context = event.getContext();
			if(context.ViewType==1)return true;
			return false;
		}
	});
	reg({
		key : 'rework',
		type : 'iflowcontents',
		desc : wcm.LANG['IFLOWCONTENT_64'] || '返工工作流文档',
		title : wcm.LANG['IFLOWCONTENT_64'] || '返工工作流文档',
		rightIndex : -1,
		order : 3,
		fn : pageObjMgr['rework'],
		isVisible : function(event){
			var context = event.getContext();
			if(context.ViewType==1)return true;
			return false;
		}
	});
	 
})();