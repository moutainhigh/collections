var PageContext = {
	init : function(){
		var photoId = getParameter("PhotoId") || 0;
		var picFile = getParameter("PicName");
		this.params = {PhotoId:photoId,FileName:picFile};		
	},
	loadPage : function(){
		$("picshower").src = mapWebFile(this.params.FileName);	
		$("pictype").innerText = getFileType(PageContext.params.FileName);
	}
};
PageContext.init();

function getFileType(_fn){
	return _fn.replace(/^.*\.([^.]+)$/,function(a,b){if(b){return b.toUpperCase();}return wcm.LANG.PHOTO_CONFIRM_94 ||"未知";});
}
function mapWebFile(_fn){
	if(_fn == null || _fn.trim().length == 0){
		return "../images/photo/pic_notfound.gif";
	}
	if(_fn.indexOf("W0") == 0){
		return "/webpic/"+_fn.substr(0,8)+"/"+_fn.substr(0,10)+"/"+_fn;
	}else{
		return "../../file/read_image.jsp?FileName=" + _fn;
	}
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