Object.extend(PageContext,{
	//是否将数据提供者切换到本地的开关
//	isLocal : true,
	isLocal : false,
	
	//远程服务的相关属性
	ObjectServiceId : 'wcm6_document',
	ObjectMethodName : 'query',
	ObjectType : 'video',
	AbstractParams : {
			"SelectFields" : "DocTitle,DocId,CrTime,CrUser,DocChannel,DOCSTATUS,DocType,ATTRIBUTE"
	},//服务所需的参数

	//页面过滤器的相关配置
	PageFilterDisplayNum : 6,
	PageFilters : [
		{Name:'全部视频',Type:0,IsDefault:true},
		{Name:'新稿',Type:1},
		{Name:'可发',Type:2},
		{Name:'已发',Type:3},
		{Name:'我创建的',Type:4},
		{Name:'最近三天',Type:5},
		{Name:'最近一周',Type:6},
		{Name:'最近一月',Type:7}
	]
});

Object.extend(PageContext.PageNav,{
	UnitName : '个',
	TypeName : '视频'
});

Object.extend(Grid,{
	draggable : false,

	_dealWithSelectedRows : function(){
		var rows = this.getRows();
		var arrVideoIds = [];
		var arrVideoTokens = [];
		var arrImgs = [];
		for(var i=0;i<rows.length;i++){
			var nRowId = this.getRowId(rows[i]);
			var sPhotoSrc = rows[i].getAttribute("video_img", 2);
			var sToken = rows[i].getAttribute("video_token", 2);
			arrVideoIds.push(nRowId);
			arrVideoTokens.push(sToken);
			arrImgs.push(sPhotoSrc);
		}
		parent.JustSelectVideos(arrVideoIds, arrImgs, arrVideoTokens);
	}
});
