Ext.ns('wcm.domain.MetaRecDataMgr');
Ext.ns('wcm.domain.MetaViewDataMgr');
(function(){
	var m_oMetaRecMgr = wcm.domain.MetaRecDataMgr;
	var aGovInfoViewId = [];
	function initGovInfoViewId(event,_callBack){
		BasicDataHelper.JspRequest(WCMConstants.WCM6_PATH+'metarecdata/get_govinfo_viewid.jsp',{},false,function(transport){
			aGovInfoViewId = transport.responseText.trim().split(",");
			if(_callBack)_callBack(event,aGovInfoViewId);
		});
	}

	//�½�
	function addGovInfoMeta(event,aGovInfoViewId){
		var nObjId = 0;		
		var sTitle = wcm.LANG.METAVIEWDATA_77 || "�½�";
		sTitle += (wcm.LANG.METAVIEWDATA_108 || "��¼");
		var contextParams = event.getContext().params;
		//�õ���ͼid��������ͼid���ж��Ƿ���������Ϣ��������ͼ
		var nViewId = event.getObj().getPropertyAsInt("viewid",0)||contextParams.VIEWID||0;
		if(aGovInfoViewId.include(nViewId)){
			var oParams = {
				ObjectId:nObjId,
				ClassInfoId : event.getHost().getId(),
				FlowDocId:contextParams.FlowDocId || 0,
				ViewId:nViewId
			};
			$openMaxWin(WCMConstants.WCM6_PATH + 'application/' + nViewId + '/metaviewdata_addedit.jsp?' + $toQueryStr2(oParams));
		}else{
			m_oMetaRecMgr.add(event);
		}
	}
	var addOper = wcm.SysOpers.getOperItem('metarecdataInClassinfo','add');
	addOper.fn = function(event){
		if(aGovInfoViewId.length==0){
			initGovInfoViewId(event,addGovInfoMeta);
		}else{
			addGovInfoMeta(event,aGovInfoViewId);
		}
		
	};

	//�޸�
	var editOper = wcm.SysOpers.getOperItem('metarecdata','edit');
	var oldEdit = wcm.domain.MetaRecDataMgr['edit'];
	function editGovInfoMeta(event,aGovInfoViewId){
		var obj = event.getObj();
		var nObjId = obj.getProperty("docid")||obj.getId();	
		var sTitle = wcm.LANG.METAVIEWDATA_77 || "�޸�";
		sTitle += (wcm.LANG.METAVIEWDATA_108 || "��¼");
		var contextParams = event.getContext().params;
		//�õ���ͼid��������ͼid���ж��Ƿ���������Ϣ��������ͼ
		var nViewId = event.getObj().getPropertyAsInt("viewid",0)||contextParams.VIEWID||0;
		if(aGovInfoViewId.include(nViewId)){
			var oParams = {
				ObjectId:nObjId,
				ClassInfoId : event.getHost().getId(),
				FlowDocId:contextParams.FlowDocId || 0,
				ViewId:nViewId
			};
			$openMaxWin(WCMConstants.WCM6_PATH + 'application/' + nViewId + '/metaviewdata_addedit.jsp?' + $toQueryStr2(oParams));
		}else{
			oldEdit(event);
		}
	}
	wcm.domain.MetaRecDataMgr['edit'] = editOper.fn = function(event){
		if(aGovInfoViewId.length==0){
			initGovInfoViewId(event,editGovInfoMeta);
		}else{
			editGovInfoMeta(event,aGovInfoViewId);
		}
	};

	//�鿴
	var oldView = wcm.domain.MetaViewDataMgr['view'];
	function viewGovInfoMeta(event,aGovInfoViewId){
		var _objId = event.getObj().getPropertyAsInt("docid",0);
		var nViewId = event.getContext().params["VIEWID"];
		if(aGovInfoViewId.include(nViewId)){
			var urlParams = "?objectId=" + _objId;
			$openMaxWin(WCMConstants.WCM6_PATH + "./application/" + nViewId + "/viewdata_detail.jsp" + urlParams);
		}else{
			oldView(event);
		}
	}
	Ext.apply(wcm.domain.MetaViewDataMgr, {
		view : function(event){
			if(aGovInfoViewId.length==0){
				initGovInfoViewId(event,viewGovInfoMeta);
			}else{
				viewGovInfoMeta(event,aGovInfoViewId);
			}
		}
	});
	
})();
