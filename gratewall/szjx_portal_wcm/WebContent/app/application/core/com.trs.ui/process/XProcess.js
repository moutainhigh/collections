Ext.ns('com.trs.ui');
/**
*附件管理
*/
(function(){
	//private 
	var template = '<div class="XProcessBox" id="{1}"><iframe src="{0}" class="XProcess" id="XProcessIframe" scrolling="no" frameborder="no" border="0"></iframe></div>';
	com.trs.ui.XProcess = Ext.extend(com.trs.ui.BaseComponent, {
		getHtml : function(){
			var flowdocId = this.initConfig['FlowDocId'];
			var currName = this.initConfig['name'];
			var url = WCMConstants.WCM6_PATH + 'flowdoc/workflow_process_render_for_metadata.jsp?NoShowAllBtn=1&FlowDocId=' + flowdocId;
			return String.format(template, url, currName);			
		}
	});
})();