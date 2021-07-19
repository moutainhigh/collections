//焦点图
DOM.ready(function(){
	var hotPicDoms = document.getElementsByName("HotPicTextarea");
	for(var index = 0; index < hotPicDoms.length; index++){
		FocusPicRender(hotPicDoms[index]);
	}
});

(function(){
	var cache = {};

	window.FocusPicRender = function(textarea){
		var value = textarea.value;
		var data = eval("("+value+")");
		var sBigPic = data.BigPic;
		var sSmallPics = data.SmallPics;
		var sTitleBox = data.TitleBox;
		var sPicHrefs = data.picHrefs;
		var sDocumentHrefs = data.documentHrefs;
		var sDocumentTitles = data.documentTitles;
		var nWidth = data.width;
		var nHeight = data.height;
		var nSmallHeight = data.smallHeight;
		var nFontSize = data.smallFontSize;
		var nTitlePos = data.titlePos;
		var nSmallPos = data.smallPos;
		var nSmallPosRight = data.smallPosRight;
		//不显示底部条
		var bIsShowBar = data.IsShowBar;//
		var sTitleBgId = data.TitleBg;//

		if(sTitleBgId){
			if(document.getElementById(sTitleBgId)){
				document.getElementById(sTitleBgId).style.display=bIsShowBar;
				document.getElementById(sTitleBgId).style.height = nSmallHeight+"px";
			}
		}
		if(sSmallPics){
			if(document.getElementById(sSmallPics)){
				document.getElementById(sSmallPics).style.display=bIsShowBar;
				document.getElementById(sSmallPics).style.bottom=nSmallPos+"px";
				document.getElementById(sSmallPics).style.right=nSmallPosRight+"px";
			}
		}
		if(sTitleBox){
			if(document.getElementById(sTitleBox)){
				document.getElementById(sTitleBox).style.display=bIsShowBar;
				document.getElementById(sTitleBox).style.height = nSmallHeight+"px";
				document.getElementById(sTitleBox).style.lineHeight = nSmallHeight+"px";
				document.getElementById(sTitleBox).style.fontSize = nFontSize+"px";
				document.getElementById(sTitleBox).style.left = nTitlePos+"px";
			}
		}

		if(cache[sBigPic]){
			cache[sBigPic].TimeOutEnd();
			cache[sBigPic] = null;
		}
		

		//构造数据调用函数初始化
		var sPicHrefArrs = sPicHrefs.split("###");
		var sDocumentHrefArrs = sDocumentHrefs.split("###");
		var sDocumentTitleArrs = sDocumentTitles.split("###");
		var oFocusPic = new com.trs.FocusPic(sBigPic,sSmallPics,sTitleBox,"",nWidth,nHeight);
		for(var i=0;i<sDocumentHrefArrs.length-1;i++){
			oFocusPic.Add(sPicHrefArrs[i],sPicHrefArrs[i],sDocumentHrefArrs[i],sDocumentTitleArrs[i]);
		}
		oFocusPic.begin();

		cache[sBigPic] = oFocusPic;
		return oFocusPic;
	}

	Event.observe(window, 'unload', function(){
		for(var sKey in cache){
			if(cache[sKey].TimeOutEnd){
				cache[sKey].TimeOutEnd();
				cache[sKey] = null;
			}
		}
		cache = null;
	});
})();

