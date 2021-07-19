Ext.ns('com.trs.ui');
/**
*编辑器
*/
(function(){
	//private 
	var editorFrm = "-frm";
	var template = [
		'<div class="XEditor">',
			'<textarea name="{0}" id="{0}">{1}</textarea>',
			'<iframe src="{2}" id="{0}-frm" frameborder="0" scrolling="auto" class="editor-frame"></iframe>',
		'</div>'
	].join("");
	
	var disabledEditorUrl = XConstants.BASE_PATH + 'com.trs.ui/editor/blank.html';

	com.trs.ui.XEditor = Ext.extend(com.trs.ui.BaseComponent, {
		disabledCls : 'XDisabledCls XDisabledEditor',
		editorUrl : XConstants.BASE_PATH + 'com.trs.ui/editor/core/editor.html',
		getHtml : function(){
			var config = this.initConfig;
			var sName = config['name'];
			var sUrl = config['disabled'] ? disabledEditorUrl : this.editorUrl;
			sUrl += (this.editorUrl.indexOf("?") > 0 ? "&" : "?");
			sUrl += "fieldname=" + encodeURIComponent(sName) + "&ObjectId=" + getParameter('ObjectId') + "&ChnlDocId=" + getParameter('ChnlDocId');
			if(config['params']) sUrl += "&" + $toQueryStr(config['params']);
			return String.format(template, sName, config['value'], sUrl);
		},
		setContent : function(sContent){
			var frmName = this.initConfig['name'] + editorFrm;
			try{
				var frm = $(frmName);
				if(frm.contentWindow.setHTML){
					frm.contentWindow.setHTML(sContent);
				}else{
					frm.contentWindow.document.body.innerHTML = sContent;
				}
			}catch(error){
			}
		},
		getContent : function(){
			var frmName = this.initConfig['name'] + editorFrm;
			try{
				var frm = $(frmName);
				if(frm.contentWindow.getHTML){
					return frm.contentWindow.getHTML();	
				}else{
					return frm.contentWindow.document.body.innerHTML;
				}
			}catch(error){
				return "";
			}
		},
		getValue : function(){
			var sContent = this.getContent();
			if(sContent.length == 17 && sContent.toLowerCase() == "<div>&nbsp;</div>"){
				sContent = "";
			}
			$(this.initConfig['name']).value = sContent;
			return sContent
		}
	});
})();