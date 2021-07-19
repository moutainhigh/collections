$package('com.trs.ajaxframe');

com.trs.ajaxframe.NAVIGATE_DEFAULT={
	type:'list',//'list','normal'
	page:function(_sDataSourceId,_nPageIndex,_disabled){
		if(_disabled)return '<span style="color:red"><b>'+_nPageIndex+'</b></span>';
		return '<a href="#" style="color:black"'+
			' onclick="$parser.go(\''+
			_sDataSourceId+'\','+_nPageIndex+');">'+_nPageIndex+'</span></a>';
	},
	button:function(_sDataSourceId,_nPageIndex,_disabled,_nButtonType){
		switch(_nButtonType){
			case 1:
				if(_disabled)return '第一页';
				return '<a href="#" style="color:black"'+
					' onclick="$parser.go(\''+
					_sDataSourceId+'\','+_nPageIndex+');">第一页</span></a>';
			case 2:
				if(_disabled)return '上一页';
				return '<a href="#" style="color:black"'+
					' onclick="$parser.go(\''+
					_sDataSourceId+'\','+_nPageIndex+');">上一页</span></a>';
			case 3:
				if(_disabled)return '下一页';
				return '<a href="#" style="color:black"'+
					' onclick="$parser.go(\''+
					_sDataSourceId+'\','+_nPageIndex+');">下一页</span></a>';
			case 4:
				if(_disabled)return '最后一页';
				return '<a href="#" style="color:black"'+
					' onclick="$parser.go(\''+
					_sDataSourceId+'\','+_nPageIndex+');">最后一页</span></a>';
		}
	},
	more:function(){
		return '<a href="#">更多</a>';
	},
	space:function(){
		return '&nbsp;&nbsp;';
	}
};
ClassName(com.trs.ajaxframe.NAVIGATE_DEFAULT,'ajaxframe.Navigate');

com.trs.ajaxframe.Navigate = function(_sStyle,_sType){
	var oNavigate=null;
	try{
		oNavigate=eval(_sStyle);
	}catch(err){
		oNavigate=null;
	}
	if(!oNavigate){
		oNavigate=Object.extend({},com.trs.ajaxframe.NAVIGATE_DEFAULT);
	}
	else{
		oNavigate=Object.extend({},oNavigate);
	}
	if(_sType){
		oNavigate.type=_sType;
	}
	return oNavigate;
}