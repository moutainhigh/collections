//~！！需要有一个返回值
CKEDITOR.dialog.add("imageUploadDialog", function(editor){
	 return {
		title: "imageUpload",
	　	resizable: CKEDITOR.DIALOG_RESIZE_NONE,
	　	minWidth: 360,
	　	minHeight: 100,
		onOk:function(){
			document.getElementById("form").submit();
		},
	　　contents: [{
		　　id: 'IUD',
		　	name: 'IUD',
		　　label: 'IUD',
		　　title: 'IUD',
		　　elements: [{
			　　type: 'html',
				html:
					'<style type="text/css">'+
						'.box span{'+
							'font-size:15px;'+
							'font-family:Arial;'+
							'color:white;'+
						'}'+
						'#cke_dialog_footer_83{'+
							'text-align:center'+
						'}'+
					'</style>'+
					'<div class="box">'+
						'<form class="form" id="form" method="post" action="imageUpload.jsp">'+
							'<span>选择图片：</span>'+
							'<input type="file" name="imagePath" id="imagePath"/>'+
						'</form>'+
					'</div>'
		　　	}]
	　　 }]
	}
})


