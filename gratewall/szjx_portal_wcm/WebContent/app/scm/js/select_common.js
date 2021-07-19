$(function(){
	//更多的下拉框绑定事件
	$(".userOperater-title").toggle(function(){
		$(this).next('div').toggle().end().parent().siblings().children('div').hide();
	},function(){
		$(this).next('div').toggle().end().parent().siblings().children('div').hide();
	});
	$(window.parent.document).find("html").click(function(){$(".userOperater-list").hide();});
	$("html").click(function(){
		$(".userOperater-list").hide();
	});
	$(".groupSelect").sSelect();
	setHeight();
});
//字数的显示
function countDefaultText(that){
	$(that).parent().next().next().html($(that).html());
}
