var PageContext = {
	init : function(){
		var photoId = getParameter("PhotoId") || 0;
		var picFile = getParameter("PicName");
		this.params = {PhotoId:photoId,FileName:picFile};		
	},
	loadPage : function(){
		$("picshower").src = this.params.FileName;		
		this.loadPicProps();
	},
	loadPicProps : function(){
		BasicDataHelper.call("wcm6_photo","loadSysPicProps",this.params,false,function(_transport,_json){
			var sValue = TempEvaler.evaluateTemplater('template_imageprops', _json);			
			Element.update($("imageprops"),sValue);
			$("pictype").innerText = getFileType(PageContext.params.FileName);
		});
	}
};
PageContext.init();

function getFileType(_fn){
	return _fn.replace(/^.*\.([^.]+)$/,function(a,b){if(b){return b.toUpperCase();}return "未知";});
}

function resizeIfNeed(_imgloaded){	
	if(_imgloaded){		
		var w = _imgloaded.width;
		var h= _imgloaded.height;
		if(w > 300 || h > 300){
			var r = w/h;
			if(r > 1){
				w = 300;
				h = 300/r;
			}else{
				h = 300;
				w = 300*r;
			}

			_imgloaded.width = w;
			_imgloaded.height = h;
		}
		
		_imgloaded.onload = null;
	}
}
Event.observe(window,"load",function(){
	PageContext.loadPage();
});