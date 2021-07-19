
/**页面加载后执行的事件**/
$(document).ready(function(){
	/**分类树显示和隐藏事件**/
	$('#sepBar').bind("click",function(){
		var dom = $('#container');
		dom.toggleClass('hideNavTree');
	});
	/**绑定检索按钮的事件**/
	$('#search_btn').bind("click",function(){
		$('#search_input').attr("name",$('#search_select').val());
		$('#search_form').submit();
	});
});

