(function(){ 
	CKEDITOR.dialog.add("testuidialog", function(editor)
	{ 
		return {
			title : "testUiDialog", 
			minWidth : 200,
			minHeight :200,
			contents: [{
				id : 'page1',
				label : 'page1',
				elements :
				[{
					type : 'hbox',
					widths : [ '100px', '100px', '100px' ],
					children :
					[{
						type:'html',
						html:'<div>Cell1</div>'
					},{
						type:'html',
						html:'<div>Cell2</div>'
					},{
						type: 'vbox',
						children:[{
							type:'html',
							html:'<div>Cell3</div>'
						},{
							type:'html',
							html:'<div>Cell4</div>'
						}]
					}]
				}]
			},{
				id : 'InfoTab',
				label : 'Info Tab',
				elements :
				[{
					type: 'text',
					id: 'widthInput',
					label: 'Width',
					labelLayout: 'horizontal'
				}]
			}]
		} 
	})
})();