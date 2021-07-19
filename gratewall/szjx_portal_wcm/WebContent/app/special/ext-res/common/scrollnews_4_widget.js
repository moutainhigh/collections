(function(){
	DOM.ready(function(){
		var doms = document.getElementsByName("trs-scroll-news-data");
		for(var i = 0; i < doms.length; i++){
			var value = doms[i].value;
			var data = eval("("+value+")");
			new com.trs.ScrollNews(data).render();
		}
	});
})();