var PageContext = {
	init : function(){
		var docId = getParameter("PhotoId") || 0;
		var channelId = getParameter("ChannelId") || 0;
		var siteId = getParameter("SiteId") || 0;
		this.params = {ObjectId:docId,ChannelId:channelId,SiteId:siteId};		
	},
	loadPage : function(){
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		oHelper.Call('wcm6_photo', 'getQuoteDocs', this.params, true, this.QuoteDocsLoaded);
		oHelper.Call('wcm6_document', 'findById', this.params, true, this.ImageLoaded);
	},
	ImageLoaded : function(_transport,_json){
		var sValue = TempEvaler.evaluateTemplater('template_imageprops', _json["DOCUMENT"]);			
		Element.update($("imageprops"),sValue);		
	},
	QuoteDocsLoaded : function(_transport,_json){
		if(_json["DOCUMENTS"]&&_json["DOCUMENTS"].length!=0){
			var sValue = TempEvaler.evaluateTemplater('template_quotes', _json);			
			Element.update($("quotes"),sValue);		
		}
	}
};
PageContext.init();
window._dealWith500 = function(_sMessage){
	alert(_sMessage);
}
function getFileType(_fn){
	return _fn.replace(/^.*\.([^.]+)$/,function(a,b){if(b){return b.toUpperCase();}return "未知";});
}

function mapFileName(_fns){
	if(_fns == null || _fns.trim().length == 0){
		return "../images/photo/pic_notfound.jpg";
	}
	var fns = _fns.split(",");
	var r = fns[fns.length-1];
	return "/webpic/" + r.substr(0,8) + "/" + r.substr(0,10) + "/" + r;
}
function ImgScale(_elImg){
	if(_elImg.width>300){
		_elImg.width = 300;
	}
	_elImg.style.visibility = 'visible';
}