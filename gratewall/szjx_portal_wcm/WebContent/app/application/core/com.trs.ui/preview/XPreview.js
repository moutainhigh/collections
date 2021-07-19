Ext.ns('com.trs.ui');
/**
*预览
*/
(function(){
	//private 
	var template = '<div class="XPreviewBox" id="{1}"><iframe src="{0}" class="XPreview" id="XPreviewIframe" scrolling="auto" frameborder="no" border="0"></iframe></div>';
	com.trs.ui.XPreview = Ext.extend(com.trs.ui.BaseComponent, {
		getHtml : function(){
			var objectId = this.initConfig['objectId'];
			var currName = this.initConfig['name'];
			var nFlowDocId = getParameter('FlowDocId', window.location.search);
			var params = {
				folderId : this.initConfig['folderId'],
				folderType : this.initConfig['folderType'],
				objectIds : objectId,
				objectType : 600
			}
			//add by SJ -- 资源库工作流流转时，添加查看权限
			if(nFlowDocId > 0) {
				params.FlowDocId = nFlowDocId;
			}
			this.preview(objectId,params);
			return String.format(template, "#", currName);
		},
		preview : function(_sIds, params){
			BasicDataHelper.call('wcm6_viewdocument', 'preview', params, false, function(transport, json){
				var urlCount = com.trs.util.JSON.value(json, "URLCOUNT");
				if((''+_sIds).indexOf(",") >= 0){
					if(urlCount == 0){
						var message = "";
						var dataArray = com.trs.util.JSON.array(json,"DATA");
						for (var i = 0; i < dataArray.length; i++){
							message += $transHtml(dataArray[i].EXCEPTION) + "<br>";
						}
						Ext.Msg.$alert(message);
						return;
					}
				}else{
					if(urlCount == 0){
						 Ext.Msg.fault({
							message : (json.DATA.length>0)?json.DATA[0].EXCEPTION:"",
							detail : (json.DATA.length>0)?json.DATA[0].EXCEPTIONDETAIL:""
						}, '预览出错');
						return;
					}else{
						var url = "";
						var urls = com.trs.util.JSON.value(json, "DATA.0.URLS");
						if(urls.length == 1){
							url = urls;
						}else{
							url = WCMConstants.WCM6_PATH + 'preview/index.htm?'+$toQueryStr(params);
						}
						$('XPreviewIframe').src = url;
					}
				}

			}.bind(this));
		}
	});
})();