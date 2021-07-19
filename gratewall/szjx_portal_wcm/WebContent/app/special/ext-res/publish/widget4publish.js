/**
*本文件处理发布页面中对一些资源块的扩展处理。
*如：分页导航需要对一些数据进行初始化
*/

/*=================================sltDetailPage Widget Start=============================*/
/*
*定义在发布页面中，需要进行的初始化处理
*/
function initSltNav(dom){
	var sltDom = dom;
	if(sltDom.nodeName.toLowerCase() != 'select'){
		return;
	}
	var index = getPageIndex();
	//如果文档没有分页，则返回
	if(!index)
		return;
	sltDom.options[index].selected = true;

	Event.observe(sltDom, 'change', function(){
		window.location = sltDom.options[sltDom.selectedIndex].value;
	});
}

function getPageIndex(){
	var src = window.location.href;
	src = src.split("/")[src.split("/").length -1];
	if(src.indexOf("?") >=0){
		src = src.substring(0,src.indexOf("?"));
	}
	var length = src .split("_").length;
	var index = 0;
	if(length > 1){
		src = src.split("_")[length-1];
		src = src.substring(0,src.indexOf("."));
		index = parseInt(src);
	}
	return index;
}
/*
*页面加载后，对下拉分页导航的初始化操作
*/
(function(){
	//Event.observe(window, 'load', function(event){
	DOM.ready(function(){
		var doms = document.getElementsByName("sltDetailPageData");
		for(var i = 0; i < doms.length; i++){
			var dom = Element.previous(doms[i]);
			dom.id = dom.id || genExtId();
			initSltNav(dom);
		}
	});
})();
/*=================================sltDetailPage Widget End=============================*/


/*=================================listDetailPage Widget Start=============================*/
/*
*定义在发布页面中，需要进行的初始化处理
*/
function initListNav(){
	var nPageIndex = getPageIndex() + 1;
	var currDetailPage = document.getElementById("detail_"+nPageIndex);
	if(!currDetailPage)
		return;
	currDetailPage.className = "currDetailPage";
}
/*
*页面加载后，对列表分页导航的初始化操作
*/
(function(){
	//Event.observe(window, 'load', function(event){
	DOM.ready(function(){
		initListNav();
	});
})();
/*=================================listDetailPage Widget End=============================*/
/*=================================trs-picdrag Widget Start=============================*/
(function(){
	//Event.observe(window, 'load', function(event){
	DOM.ready(function(){
		var doms = document.getElementsByName("trs-picdrag-data");
		for(var i=0;i<doms.length;i++){
			var data = eval("("+doms[i].value+")");
			var opel = $("c_rotPic_"+data.id);
			//在客户端需要重新生成flash，防止设计页面中设置links为null导致不能跳转链接
			//if(!opel || opel.innerHTML!="")continue;
			var infos = data.infos.split(";");
			var pics="",links="",texts="";
			for(var j=0;j<infos.length;j++){
				if(infos[j].trim().length<=0)continue;
				pics+=infos[j].split(",")[0];
				links+=infos[j].split(",")[1];
				texts+=infos[j].split(",")[2];
				if(j<infos.length-1 && infos[j+1] && infos[j+1].trim().length!=0){
					pics+="|";links+="|";texts+="|";
				}
			}
			opel.innerHTML=GetFlashHTML(data.WIDTH,data.HEIGHT,20,pics,links,texts,data.sRootPath);
		}
	});
})();
/*=================================trs-picdrag Widget End===============================*/