var PageContext = {
	init : function(){
		var photoId = getParameter("PhotoId") || 0;
		var picFile = getParameter("PicName");
		this.params = {PhotoId:photoId,FileName:picFile};		
	},
	loadPage : function(){
		$("pictype").innerText = getFileType(PageContext.params.FileName);
	}
};
PageContext.init();

function getFileType(_fn){
	return _fn.replace(/^.*\.([^.]+)$/,function(a,b){if(b){return b.toUpperCase();}return wcm.LANG.PHOTO_CONFIRM_94 ||"未知";});
}

function resizeIfNeed(_imgloaded){	
	if(_imgloaded){		
		var w = _imgloaded.width;
		var h= _imgloaded.height;
		if(w > 300 || h > 250){
			var r = w/h;
			if(r > 1){
				w = 300;
				h = 300/r;
			}else{
				h = 250;
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