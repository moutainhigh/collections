//水印操作信息和Mgr定义
Ext.ns('wcm.domain.WatermarkMgr');
(function(){
	var m_oMgr = wcm.domain.WatermarkMgr;
	function getHelper(){
		return new com.trs.web2frame.BasicDataHelper();
	}
	Ext.apply(wcm.domain.WatermarkMgr, {
		/**
		 * @param : wcm.CMSObjEvent event
		 */
		//*
		add : function(event){
			var oPageParams = {};
			var hostid = event.getHost().getId();
			Object.extend(oPageParams,{ObjectId:0,siteid:hostid});
			FloatPanel.open(WCMConstants.WCM6_PATH +"watermark/watermark_addedit.jsp?" + $toQueryStr(oPageParams), wcm.LANG.WATERMARK_PROCESS_11 ||'上传水印', CMSObj.afteradd(event));
		},
		edit : function(event){			
			var docid = event.getObjs().getAt(0).getId();
			var oPageParams = {};
			var hostid = event.getHost().getId();
			Object.extend(oPageParams,{ObjectId:docid,siteid:hostid});
			FloatPanel.open(WCMConstants.WCM6_PATH +"watermark/watermark_addedit.jsp?" + $toQueryStr(oPageParams), wcm.LANG.WATERMARK_PROCESS_12 ||'编辑水印', CMSObj.afteredit(event));
		},
		"delete" : function(event){
			var sId = event.getIds();
			var hostid = event.getHost().getId();
			var nCount = sId.toString().split(',').length;
			var sHint = (nCount==1)?'':' '+nCount+' ';
			var sResult = (wcm.LANG.WATERMARK_PROCESS_13 ||'确实要将这') + sHint + (wcm.LANG.WATERMARK_PROCESS_14 ||'个水印删除吗? ');
			Ext.Msg.confirm(sResult,{
                yes : function(){
                    BasicDataHelper.call("wcm6_watermark", 
						'delete', //远端方法名				
						Object.extend({}, {"ObjectIds": sId,"LibId":hostid}), //传入的参数
						true, //异步
						function(){//响应函数
							//$MsgCenter.$main().afteredit();
							event.getObjs().afterdelete();
						}
					);
                }
            });
		} 
		//*/
		//type here
	});
})();
(function(){
	var pageObjMgr = wcm.domain.WatermarkMgr;
	var reg = wcm.SysOpers.register;

	reg({
		key : 'edit',
		type : 'watermark',
		desc : wcm.LANG.WATERMARK_PROCESS_15 ||'编辑这个水印',
		title : wcm.LANG.WATERMARK_PROCESS_15 ||'编辑这个水印...',
		rightIndex : 32,
		order : 1,
		fn : pageObjMgr['edit'],
		quickKey : 'E'
	});
	reg({
		key : 'delete',
		type : 'watermark',
		desc : wcm.LANG.WATERMARK_PROCESS_16 ||'删除这个水印',
		title : wcm.LANG.WATERMARK_PROCESS_16 ||'删除这个水印...',
		rightIndex : 32,
		order : 2,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'add',
		type : 'watermarkInSite',
		desc : wcm.LANG.WATERMARK_PROCESS_17 ||'上传新水印',
		title : wcm.LANG.WATERMARK_PROCESS_17 ||'上传新水印...',
		rightIndex : 32,
		order : 3,
		fn : pageObjMgr['add'],
		quickKey : 'N'
	});
	reg({
		key : 'delete',
		type : 'watermarks',
		desc : wcm.LANG.WATERMARK_PROCESS_18 ||'删除这些水印',
		title : wcm.LANG.WATERMARK_PROCESS_18 ||'删除这些水印...',
		rightIndex : 32,
		order : 4,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});

})();