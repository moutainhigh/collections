

function resizeFlash(){
	var width = $("container").offsetWidth;
	var height = $("container").offsetHeight;
	//$("month_doc_type_barflash_data").style.height=(height-20)/2-75;
	var flashs = document.getElementsByTagName("OBJECT");
	for (var i = 0; i < flashs.length; i++){
		var realHeight = (height-20)/2-50;
		var realWidth = (width-20)/3-20;
		if(realHeight<160)realHeight = 160;
		if(realWidth<180)realWidth = 180;
		flashs[i].style.height=realHeight+"px";
		flashs[i].style.width=realWidth+"px";
	}
	//alert(height-53<380?380:height-53+"px")
	$("month_flash_chart_data").style.width=(width-20)*2/3-20+"px";
	$("hitscount").style.height=height-53<380?380:height-53+"px";
}
Event.observe(window,"resize",function(){
	resizeFlash();
});
Event.observe(window,"load",function(){
	setTimeout(resizeFlash,0);
});
/*
* flash的另存为图片实现
*
*/
OFC = {};
OFC.jquery = {
	name: "jQuery",
	version: function(src) { return $(src).get_version() },
	rasterize: function (src, dst) { $(dst).replaceWith(OFC.jquery.image(src)) },
	image: function(src) { return "<img src='data:image/gif;base64," + $(src).get_img_binary() + "' />"},
	popup: function(src) {
		var img_win = window.open('', 'Charts')
		with(img_win.document) {
			write('<html><head><title>保存统计数据为图片<\/title><\/head><body>' + OFC.jquery.image(src) + '<\/body><\/html>') }
		// stop the 'loading...' message
		img_win.document.close();
	}
}

/*
*  右键保存flash为图片所调用的方法
*/
//function save_image() {OFC.jquery.popup()}