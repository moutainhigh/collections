(function(){
	DOM.ready(function(){
		var doms = document.getElementsByName("trs-scroll-image-data");
		for(var i = 0; i < doms.length; i++){
			var value = doms[i].value;
			var data = eval("("+value+")");
			var imgUrls = data.imgUrls || "";
			var linkUrls = data.linkUrls || "";
			if(data.selectMode == "1"){
				//如果是垂直滚动图片的话，就去掉url串的最后一个的$
				imgUrls = imgUrls.substring(0,imgUrls.lastIndexOf('$'));
				linkUrls = linkUrls.substring(0,linkUrls.lastIndexOf('$'));
			}
			if(imgUrls){
				data.imgUrls = imgUrls.split("$");
			}
			if(linkUrls){
				data.linkUrls = linkUrls.split("$");
			}
			var descs = data.descs || "";
			data.descs = descs.split("$");
			new com.trs.ScrollImage(data).render();
		}
	});
})();