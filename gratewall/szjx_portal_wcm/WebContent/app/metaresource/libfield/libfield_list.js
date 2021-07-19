$(document).ready(function(){
	/**页面对象**/
	var PageContext = window.PageContext || {};
	$.extend(PageContext,{
		"delete" : function($dom){
			$.CrashBoard({
				id : 'delete-crashboard',
				maskable : true,
				draggable : true,
				url : 'libfield_delete.jsp',
				width : '500px',
				height : '200px',
				params : {ObjectId:$dom.attr("ObjectId"), pagesize:-1},
				callback : function(){
					alert($dom.attr("ObjectId"));
					//根据传进来的OBjectId的值，进行相应的删除操作
				}
			}).show();
		}
	});
	/**给document绑定click事件**/
	$(document).click(function(e){
		var $dom = $(e.target);
		$dom = $dom.closest("[cmd]");
		if(!$dom.attr("cmd"))return;
		PageContext[$dom.attr("cmd")]($dom.closest("tr"));
	});
});