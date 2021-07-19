var	ViewClsTemplateMgr = {};

Object.extend(ViewClsTemplateMgr, window.ViewTemplateMgr);
Object.extend(ViewClsTemplateMgr, {
	move : function(_sDocIds, _oPageParams, otherParams){
		var hostParams = this.preProcessParams(_sDocIds);
		var oPostData = {
			'classinfoid' : otherParams['classinfoid'],
			'objectids' : hostParams["docIds"]
		}
		FloatPanel.open('./metadata/record_moveto_cls.html?' + $toQueryStr(oPostData), '移动记录', 400, 350);
	},
	"import" : function(_sId,_oPageParams, _oOtherParams){
		try{
			if(!_oOtherParams || !_oOtherParams["viewId"]){
				_oOtherParams1 = $main().getPageParams() || {};
				if(!_oOtherParams1["viewId"]){
					$alert("没有指定视图ID[viewId]");
					return;
				}
				_oOtherParams["viewId"] = _oOtherParams1["viewId"];
			}
		}catch(error){
			alert("ViewTemplateMgr.import:" + error.message);
		}
		var oParams = {
			DocumentId : _sId,
			ViewId	   : _oOtherParams["viewId"],
			ChannelId : _oOtherParams["channelid"] || 0,
			SiteId : _oOtherParams["siteid"] || 0
		}
		if(oParams.ChannelId==0){
			//站点智能创建文档,分成两步,1,选择栏目;2,在相应栏目上新建
			FloatPanel.open('./metadata/view_data_import_cls.jsp?' + $toQueryStr(oParams), '导入记录', 500, 350);
			return;
		}
		FloatPanel.open('./metadata/view_data_import.jsp?' + $toQueryStr(oParams), '导入记录', 500, 300);
	},
	copy : function(_sDocIds, _oPageParams, otherParams){
		var hostParams = this.preProcessParams(_sDocIds);
		var oPostData = {
			'classinfoid' : otherParams['classinfoid'],
			'objectids' : hostParams["docIds"]
		}
		FloatPanel.open('./metadata/record_copyto_cls.html?' + $toQueryStr(oPostData), '复制记录', 400, 350);
	}
});