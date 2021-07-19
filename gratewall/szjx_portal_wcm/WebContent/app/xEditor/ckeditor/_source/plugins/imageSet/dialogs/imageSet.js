(function(){ 
	CKEDITOR.dialog.add("imageSet", function(editor)
	{ 
		return imageDialog( editor, "imageSet" );
	})

	var imageDialog = function( editor, dialogType )
	{
		return {
			title : "图片属性", 
			minWidth : 282,
			minHeight :52,
			contents: [{
				id : 'imageSet',
				elements :
					[{
						type : 'html',
						html : '<iframe src="'+CKEDITOR.plugins.getPath( 'imageSet' ) + 'doubleclick.html' + '" style="width:100%;height:52px;" frameborder="0"></iframe>'
					}]
				}],
			buttons:[]
		} 
	}
})();