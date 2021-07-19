(function(){ 
	CKEDITOR.dialog.add("testDialog", function(editor)
	{ 
		return {
			title : "testDialog", 
			minWidth : 200,
			minHeight :200,
			onOk: function(){
				alert("OK");
			} ,
			onLoad: function(){
				alert("onLoad");
			},
			onShow:function(){
				alert("onShow");
			},
			contents: [{
				id : 'tab1',
				elements :
					[{
						type : 'html',
						html : '<iframe src="'+CKEDITOR.plugins.getPath( 'testdialog' ) + 'page.html' + '" style="width:100%;height:100%;" frameborder="0"></iframe>'
					}]
				}]
		} 
	})
})();