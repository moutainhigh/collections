(function(){ 
	CKEDITOR.dialog.add('pageAttrs', function(editor)
	{ 
		return {
			title : editor.lang.pageAttrs.title,
			minWidth : 550,
			minHeight : 280,
			onOk : function(editor){
				var win = window.frames[1];
				var a =win.document.getElementById('previousSpaceSet').value;
				//debugger
				CKEDITOR.instances.editor.addCss('p{margin-top:'+a+'em!important;}');
			},
			contents: [{
				id :  'page1',
				elements : [{
					type : 'html',
					html : '<iframe src="'+CKEDITOR.plugins.getPath( 'pageAttrs' ) + 'page.html' + '" style="width:100%;height:300px;" frameborder="0"></iframe>'
				}]
			}]
		} 
	})
})();
