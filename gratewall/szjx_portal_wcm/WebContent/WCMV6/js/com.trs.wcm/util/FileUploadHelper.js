var FileUploadHelper = {
	validFileExt :function(_strPath,_sAllowExt,_fAlert){
		var sAllowExt = _sAllowExt;
		_strPath = _strPath || '';
		_fAlert = _fAlert||window.$alert||alert;
		if(_strPath.length<=0){
			(_fAlert)("没有选择文件！");
			return false;
		}
		var validFileExtRe = new RegExp('.+\.('+_sAllowExt.split(',').join('|')+')$','ig');
		if(!validFileExtRe.test(_strPath)){
			(_fAlert)("只支持上传\""+sAllowExt+"\"格式的文件！");
			return false;
		}
		return true;
	},
	fileUploadedAlert : function(sResponseText,_fNoErr,_fErr,_fAlert){
		if(sResponseText.match(/<!--ERROR-->/img)){
			var texts = sResponseText.split('<!--##########-->');
			_fAlert = _fAlert||window.$alert||alert;
			if(texts[0]==0){
				(_fAlert)(texts[1]);
			}
			else{
				FaultDialog.show({
					code		: texts[0],
					message		: texts[1],
					detail		: texts[2],
					suggestion  : ''
				}, '与服务器交互时出错啦！');
			}
			if(_fErr)(_fErr)();
			else if(window.ProcessBar){
				ProcessBar.close();
			}
			return true;
		}
		else{
			(_fNoErr)();
		}
		return false;
	}
}