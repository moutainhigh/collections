/**点击检索字段。**/
$(document).ready(function(){
	$('#search_btn').bind("click",function(){
		$('#searchField').submit();
	});
});