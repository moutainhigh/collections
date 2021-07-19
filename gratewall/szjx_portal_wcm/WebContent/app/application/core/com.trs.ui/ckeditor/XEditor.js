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
			'<iframe src="{2}" id="{0}-frm" frameborder="0" scrolling="no" class="editor-frame"></iframe>',
		'</div>'
	].join("");
	
	var disabledEditorUrl = XConstants.BASE_PATH + 'com.trs.ui/ckeditor/blank.html';

	com.trs.ui.XEditor = Ext.extend(com.trs.ui.BaseComponent, {
		disabledCls : 'XDisabledCls XDisabledEditor',
		editorUrl : WCMConstants.WCM6_PATH + 'editor/editor.html',
		getHtml : function(){
			var config = this.initConfig;
			var sName = config['name'];
			var sUrl = config['disabled'] ? disabledEditorUrl : this.editorUrl;
			sUrl += (this.editorUrl.indexOf("?") > 0 ? "&" : "?");
			sUrl += "EditorValueElId=" + sName + "&height=300px&editorConfig=metadataConfig&fieldname=" + encodeURIComponent(sName) + "&ObjectId=" + getParameter('ObjectId') + "&ChnlDocId=" + getParameter('ChnlDocId');
			if(config['params']) sUrl += "&" + $toQueryStr(config['params']);
			return String.format(template, sName, config['value'], sUrl);
		},
		setContent : function(sContent){
			 
		},
		getContent : function(){
			/*var frmName = this.initConfig['name'] + editorFrm;
			try{
				var frm = $(frmName);
				if(!frm) return null;
				if(frm.contentWindow.GetHTML){
					return frm.contentWindow.GetHTML();	
				}else{
					return frm.contentWindow.document.body.innerHTML;
				}
			}catch(error){
				return "";
			}*/
			var frmName = this.initConfig['name'] + editorFrm;
			try{
				var frm = $(frmName);
				if(!frm) return null;
				
				if(frm.contentWindow.GetEditor){
					var FCK = frm.contentWindow.GetEditor();//new!new!
					//return FCK.QuickGetText();
					return FCK.GetHTML();
				}else{
					return frm.contentWindow.document.body.innerHTML;
				}
			}catch(error){
				return "";
			}
		},
		getValue : function(){
			var sContent = this.getContent();
			if(sContent == null) return $(this.initConfig['name']).value;
			if(sContent.length == 17 && sContent.toLowerCase() == "<div>&nbsp;</div>"){
				sContent = "";
			}
			$(this.initConfig['name']).value = sContent;
			return sContent;
		}
	});
})();

/**
*编辑器全屏打开
*/
function FullOpenEditor(bFull,win){
	var pfs=frames;
	for(var i=0;i<pfs.length;i++)
	{
		if(pfs[i]==win.parent)
		{
			var el = pfs[i].frameElement;
			if(bFull) {
				el.className += " Xeditor_full_edit";
				//window.top.document.body.style.overflowY = 'hidden';
			}else {
				if((' '+el.className+' ').indexOf(' Xeditor_full_edit ')!=-1) {
					var re = new RegExp('(?:^|\\s+)Xeditor_full_edit(?:\\s+|$)', "g");
					el.className = el.className.replace(re, " ");
				}
				//window.top.document.body.style.overflowY = 'auto';
			}
				/*
				el.style.position = "absolute";
				el.style.top = 0;
				el.style.left = 0;
				el.style.width = "100%";
				el.style.height = "100%";
				*/
		}
	}
}