(function(){ 
	CKEDITOR.dialog.add("videoSet", function(editor)
	{ 
		return imageDialog( editor, "videoSet" );
	})

	var imageDialog = function( editor, dialogType )
	{
		return {
			title : "视频属性", 
			minWidth : 312,
			minHeight :52,
			onOk: function(){
				//alert("OK");
			} ,
			onLoad: function(){
				//alert("onLoad");
				//document.getElementById('cke_dialog_footer_58').style.display = "none";
			},
			onShow:function(){
				/*var IMAGE = 1;
				this.imageEditMode = false;
				this.imageElement = false;
				
				var editor = this.getParentEditor(),
					sel = this.getParentEditor().getSelection(),
					element = sel.getSelectedElement();

				debugger;
				if ( element && element.getName() == 'img' && !element.data( 'cke-realelement' ))
				{
					this.imageEditMode = element.getName();
					this.imageElement = element;
				}

				if ( this.imageEditMode )
				{
					// Use the original element as a buffer from  since we don't want
					// temporary changes to be committed, e.g. if the dialog is canceled.
					this.cleanImageElement = this.imageElement;
					this.imageElement = this.cleanImageElement.clone( true, true );

					// Fill out all fields.
					//this.setupContent( IMAGE, this.imageElement );
				}
				else
					this.imageElement =  editor.document.createElement( 'img' );
				*/
			},
			contents: [{
				id : 'videoSet',
				elements :
					[{
						type : 'html',
						html : '<iframe src="'+CKEDITOR.plugins.getPath( 'videoSet' ) + 'doubleclick.html' + '" style="width:100%;height:52px;" frameborder="0"></iframe>'
					}]
				}],
			buttons:[]
		} 
	}
})();