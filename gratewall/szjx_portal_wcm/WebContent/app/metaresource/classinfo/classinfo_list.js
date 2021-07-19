/**分类树显示和隐藏事件**/
$(document).ready(function(){
	$('#sepBar').bind("click",function(){
		var dom = $('#container');
		dom.toggleClass('hideNavTree');
	});
	$('#search_btn').bind("click",function(){
		$('#search_input').attr("name",$('#search_select').val());
		$('#search_form').submit();
	});
});
