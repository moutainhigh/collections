window.onload = function(){
	setHeight(); //设置高度
};
/*上下拉动，并切换图片*/
function slideUpDown(that,upImgPath,downImgPath,id){
	if($(that).attr('src')!=downImgPath){
		$("#"+id).slideToggle();
		$(that).attr('src',downImgPath);
	}else{
		$(that).attr('src',upImgPath);
		$("#"+id).slideToggle();
	}	
	setTimeout(function(){
		setHeight();
	},400);
}
