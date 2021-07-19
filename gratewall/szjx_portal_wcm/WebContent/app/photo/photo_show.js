var PageContext = {};
Object.extend(PageContext,{
	init : function(){
	},
	loadImage : function(){
		//show.
		var spanEls = document.getElementsByClassName("navdesc");
		var spanEl = spanEls[spanEls.length-1];
		spanEl.nextSibling.className = "actived_size";
		$("currImage").src = mapWebFile(spanEl.getAttribute("_file"));
		Element.hide($("div_loading"));
		Element.show($("main"));
//		setTimeout(function(){
//			PageContext.showProcessInfo(json);
//		},500);
	}
});
PageContext.init();

function highlightNav(_nav){
	_nav.className = "navdesc_mouseover";
}

function normalizeNav(_nav){
	_nav.className = "navdesc";
}

function showScaledImage(_nav){
	var fn = _nav.getAttribute("_file");
	$("currImage").src = mapWebFile(fn);
	document.getElementsByClassName("actived_size")[0].className = "";
	_nav.nextSibling.className="actived_size";
}

function showPhoto(_photoId,_chnlId,_SiteId,_recId,_currPage){	
	var sUrl = window.location.href;
	var sSearch = window.location.search;
	sUrl = sUrl.substr(0,sUrl.indexOf(sSearch));
	sUrl += "?DocumentId="+_photoId;	
	sUrl += "&CurrPage="+_currPage;
	if(_SiteId > 0){
		sUrl += "&SiteId="+_SiteId;
	}else{
		sUrl += "&ChannelId="+_chnlId;
	}
	sUrl += "&RecId="+_recId;
	
	window.location.assign(sUrl);
}

function onNext(_photoId,_chnlId,_SiteId,_recId,CurrPage ,PageCount){	
	if(CurrPage >= PageCount){
		Ext.Msg.alert(wcm.LANG.PHOTO_CONFIRM_38 || "后面已经没有图片了！");
		return false;
	}
	showPhoto(_photoId,_chnlId,_SiteId,_recId,CurrPage+1);
}

function onPrevious(_photoId,_chnlId,_SiteId,_recId,CurrPage,PageCount){	
	if(CurrPage == 1){
		Ext.Msg.alert(wcm.LANG.PHOTO_CONFIRM_39 || "前面已经没有图片了！");
		return false;
	}
	showPhoto(_photoId,_chnlId,_SiteId,_recId,CurrPage-1);
}

function mapWebFile(_fn){
	if(_fn.indexOf("W0") == 0){
		return "/webpic/"+_fn.substr(0,8)+"/"+_fn.substr(0,10)+"/"+_fn;
	}else{
		return "../../file/read_image.jsp?FileName=" + _fn;
	}
}

Event.observe(window,"load",function(){
	try{	
		PageContext.loadImage();
	}catch(e){
		//TODO logger
		//alert(e.message)
	}
});

function breakLine(_value,_lineLength){
	var lineLength = _lineLength || 30;
	if(!_value || _value.length <= lineLength) return _value;	
	var result = "";
	var source = _value;	
	var len=0;
	var schar;
	for(var i=0;schar=source.charAt(i);i++){
		result+=schar;
		len+=(schar.match(/[^\x00-\xff]/)!=null?2:1);
		if(len>= lineLength){
			result+="<br />";
			len=0;
		}
	}

	return result;
}