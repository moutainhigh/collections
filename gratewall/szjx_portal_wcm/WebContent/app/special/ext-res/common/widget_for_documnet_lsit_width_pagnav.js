(function(){
	var ajax_CheckTail = InitAjax();
	function getTextResponse(theurl){
		ajax_CheckTail.open("GET", theurl, false); 
		ajax_CheckTail.send(null); 
		if (ajax_CheckTail.readyState == 4 && ajax_CheckTail.status == 200) {
			var value =ajax_CheckTail.responseText;
		}else{
			value = -1;
		}
		return value;
	}
	function InitAjax(){
		var ajax_CheckTail=false; 
		try{
			ajax_CheckTail = new ActiveXObject("Msxml2.XMLHTTP"); 
		}catch(e){
			try{
				ajax_CheckTail = new ActiveXObject("Microsoft.XMLHTTP"); 
			}catch(E){
				ajax_CheckTail = false; 
			}
		}
		if(!ajax_CheckTail && typeof XMLHttpRequest!='undefined'){
			ajax_CheckTail = new XMLHttpRequest(); 
		}
		return ajax_CheckTail;
	}
	function generatePageUrls(pageCount){
		var src = window.location.href.replace("\\","/"),
			pagename = src.split("/")[src.split("/").length -1].split("?")[0],
			pageInfo = pagename.split("."),
			preName = pageInfo[0].split("_")[0]||'index',
			pageTail = "html",
			urls = [];
		if(!pageInfo[1]){
			var ajax_CheckTail = InitAjax();
			var theurl=src+"index.html"; 
			var value = getTextResponse(theurl);
			if(value==-1){
				pageTail="htm";
			}
		}
		urls.push(preName+"."+(pageInfo[1]||pageTail));
		for (var i = 1; i < pageCount; i++){
			urls.push(preName+"_"+i+"."+(pageInfo[1]||pageTail));
		}
		return urls;
	}
	Event.observe(window, 'load', function(event){
		var doms = document.getElementsByName("documentListPageNav");
		for(var i = 0; i < doms.length; i++){
			var dom = Element.previous(doms[i]),
				count = parseInt(dom.getAttribute("count"), 10) || 5;
			dom.innerHTML = createPageHTML(count, getPageIndex(),generatePageUrls(count));
		}
	});
})();