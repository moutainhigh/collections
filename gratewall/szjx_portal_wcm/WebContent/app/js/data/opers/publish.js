//站点等需要的预览、发布信息和Mgr定义
Ext.ns('wcm.domain.PublishAndPreviewMgr');
(function(){
	var m_oMgr = wcm.domain.PublishAndPreviewMgr={
		serviceId : 'wcm6_publish',
		helpers : {},
		getHelper : function(_sServceFlag){
			return new com.trs.web2frame.BasicDataHelper();
		}
	};
	Ext.apply(wcm.domain.PublishAndPreviewMgr, {
		publish : function(_sIds, _iObjectType, _sMethodName ,_oExtraParams, _sServiceId){
			//TODO 提示发布
			_sMethodName = _sMethodName || 'publish';
			var postData = Object.extend(_oExtraParams || {},{
				'ObjectIds' : _sIds, 
				'ObjectType' : _iObjectType
			});
			m_oMgr.getHelper().call((_sServiceId || m_oMgr.serviceId), _sMethodName, postData, true, function(_transport,_json){
				m_oMgr.doAfterPublish(postData, _sMethodName,_transport,_json);
			}.bind(this));
		},
		doAfterPublish : function(_postData, _sMethodName,_transport,_json){
			if(_json!=null&&_json["REPORTS"]){
				var oReports = _json["REPORTS"];
				var stJson = com.trs.util.JSON;
				var bIsSuccess = stJson.value(oReports, "IS_SUCCESS");
				var title = stJson.value(oReports, "TITLE");
				if(bIsSuccess=='false'){
					if(title.indexOf(wcm.LANG.PUBLISH_7 || "图片") != -1){
						if(oReports.REPORT.length){
							for(var i =0;i< oReports.REPORT.length;i++){
								var currItem = stJson.value(oReports.REPORT[i], "TITLE");
								if( currItem != null)
									oReports.REPORT[i].TITLE = currItem.replace(new RegExp(wcm.LANG.PUBLISH_6 || "文档","g"),wcm.LANG.PUBLISH_7 || "图片");
							}
						}else{
							oReports.REPORT.TITLE = oReports.REPORT.TITLE.replace(new RegExp(wcm.LANG.PUBLISH_6 || "文档","g"),wcm.LANG.PUBLISH_7 || "图片");
						}
					}
					if(title.indexOf(wcm.LANG.PUBLISH_8 || "视频") != -1){
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
					Ext.Msg.report(_json,wcm.LANG.PUBLISH_1||'发布校验结果');
					return;
				}
			}
			var bIsPublished = (_sMethodName.toLowerCase() != 'recallpublish');
			Ext.Msg.$timeAlert(wcm.LANG.PUBLISH_2||'已经将您的发布操作提交到后台了...', 3);
			//TODO 刷新main和oap中需要刷新的字段
			var params = Object.extend({}, _postData);
			params['StatusName'] = bIsPublished ? wcm.LANG.PUBLISH_3||'已发' : wcm.LANG.PUBLISH_4||'已否';
			params['StatusValue'] = bIsPublished ? '10' : '15';
			var objectids = params['ObjectIds'];
			if(!Array.isArray(objectids)) {
				params['ObjectIds'] = [objectids];
			}
			
		},
		_checkPreview_ : function(_sIds, _iObjectType, _extraParams, _sServiceId){
			m_oMgr.getHelper().call((_sServiceId || m_oMgr.serviceId),'preview', Object.extend(Object.extend({},_extraParams||{}),{
					objectIds: _sIds, 
					objectType: _iObjectType
			}), false, function(transport, json){
				var urlCount = com.trs.util.JSON.value(json, "URLCOUNT");
				if((''+_sIds).indexOf(",") >= 0){
					if(urlCount == 0){
						var message = "";
						var dataArray = com.trs.util.JSON.array(json,"DATA");
						for (var i = 0; i < dataArray.length; i++){
							if(com.trs.util.JSON.value(json, "Title")!=null){
								if(com.trs.util.JSON.value(json, "Title").indexOf(wcm.LANG.PUBLISH_7 || "图片") != -1)
									dataArray[i].EXCEPTION = dataArray[i].EXCEPTION.replace(new RegExp(wcm.LANG.PUBLISH_6 || "文档","g"),wcm.LANG.PUBLISH_7 || "图片");
								if(com.trs.util.JSON.value(json, "Title").indexOf(wcm.LANG.PUBLISH_8 || "视频") != -1)
									dataArray[i].EXCEPTION = dataArray[i].EXCEPTION.replace(new RegExp(wcm.LANG.PUBLISH_6 || "文档","g"),wcm.LANG.PUBLISH_8 || "视频");
							}
							message += $transHtml(dataArray[i].EXCEPTION) + "<br>";
						}
						Ext.Msg.$alert(message);
					}else{
						m_oMgr.__openPreviewPage(_sIds, _iObjectType, _extraParams);
					}
				}else{
					if(urlCount == 0){
						if(com.trs.util.JSON.value(json, "Title")!=null){
							if(com.trs.util.JSON.value(json, "Title").indexOf(wcm.LANG.PUBLISH_7 || "图片") != -1)
								json.DATA[0].EXCEPTION = json.DATA[0].EXCEPTION.replace(new RegExp(wcm.LANG.PUBLISH_6 || "文档","g"),wcm.LANG.PUBLISH_7 || "图片");
							if(com.trs.util.JSON.value(json, "Title").indexOf(wcm.LANG.PUBLISH_8 || "视频") != -1)
								json.DATA[0].EXCEPTION = json.DATA[0].EXCEPTION.replace(new RegExp(wcm.LANG.PUBLISH_6 || "文档","g"),wcm.LANG.PUBLISH_8 || "视频");
						}
						 Ext.Msg.fault({
							message : (json.DATA.length>0)?json.DATA[0].EXCEPTION:"",
							detail : (json.DATA.length>0)?json.DATA[0].EXCEPTIONDETAIL:""
						},wcm.LANG.PUBLISH_5||'预览出错');
							/*$alert(com.trs.util.JSON.value(json, "DATA[0].EXCEPTION"), function(){
							$dialog().hide();
						});//*/
					}else{
						var urls = com.trs.util.JSON.value(json, "DATA.0.URLS");
						if(urls.length == 1){
							var pObjId = _sIds;
							var pObjectType = _iObjectType;
							if(pObjectType == 600 || pObjectType == "600") {
								pObjectType = "605";
							}
							if(m_oMgr.isMobileObj(pObjId,pObjectType)) {
								window.open(WCMConstants.WCM6_PATH+"preview/mobilePreview/mobilePreviewPage.html?URL="+urls);
							}else {
								window.open(urls);
							}
							return;
						}
						m_oMgr.__openPreviewPage(_sIds, _iObjectType, _extraParams);
					}
				}			
			}.bind(this));
		},
		preview : function(_sIds, _iObjectType,_extraParams, _sServiceId){
			m_oMgr._checkPreview_(_sIds,_iObjectType,_extraParams, _sServiceId);
		},
		__openPreviewPage : function(_sIds, _iObjectType,_extraParams){
			window.open(WCMConstants.WCM6_PATH + 'preview/index.htm?objectType='+ _iObjectType + '&objectids=' + _sIds + '&'+$toQueryStr(_extraParams||{}), 'preview_page');
		},
		isMobileObj : function(objectId,objectType){
			if(!objectType || !objectId) {
				return false;
			}
			//通过同步的ajax请求，获取当前栏目是否可创建移动栏目
			var transport = ajaxRequest({
				url : WCMConstants.WCM_ROOTPATH + 'center.do?serviceid=wcm61_mobileportal&methodname=isMobileObject',
				method : 'GET',
				parameters : 'objectid='+objectId +'&objecttype='+objectType,
				asyn : false//执行同步请求
			});
			if(!transport) {
				transport = new ajaxRequest({
					url : WCMConstants.WCM_ROOTPATH + 'center.do?serviceid=wcm61_mobileportal&methodname=isMobileObject',
					method : 'GET',
					parameters : 'objectid='+objectId +'&objecttype='+objectType,
					asyn : false//执行同步请求
				}).transport;
			}
			var json = parseXml(loadXml(transport.responseText));
			if(json.RESULT == 'true'){
				return true;
			}
			return false;
		}
	});
})();

