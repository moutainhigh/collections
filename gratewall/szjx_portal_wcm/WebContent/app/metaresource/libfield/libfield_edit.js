function show(){
		$.CrashBoard({
			id : 'edit-crashboard',
			maskable : true,
			draggable : true,
			url : 'libfield_edit.jsp',
			width : '500px',
			height : '200px',
			params : {ObjectId: 108, pagesize: -1},
			callback : function(objs){
				//这里接受参数为选择的资源结构Ids，然后执行同步修改字段
				alert("这里是回调函数的链接url");
			}
		}).show();
}
