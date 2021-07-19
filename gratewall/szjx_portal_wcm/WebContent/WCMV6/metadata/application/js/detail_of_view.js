var ViewDataDealer = {
	objectId			: getParameter('objectId') || getParameter('documentId') || 0,//对象的ID属性
	objectContainer		: 'objectContainer',//页面 form Element 容器
	objectTemplate		: 'objectTemplate', //textarea id
	_alertMsg_			: false,

	/**---------------------------------------选择实现的方法　STAET-------------------------------------**/
	/**
	*init data here before request.	if need, override the method.
	*/
	_beforeInitData : function(){
		if(this._alertMsg_) this._fAlert('beforeInitData...');
		if(this.beforeInitData) this.beforeInitData();
	},
	/**
	*init data here after request.	if need, override the method.
	*/
	_afterInitData : function(transport, json){
		if(this._alertMsg_) this._fAlert('afterInitData...');
		if(this.afterInitData) this.afterInitData();
		HTMLElementParser.parse();
		adjustDimension();
	},
	/**
	*bind some events here.	if need, override the method.
	*/
	_bindEvents : function(){
		if(this._alertMsg_) this._fAlert('bindEvents...');
		if(this.bindEvents) this.bindEvents();
	},
	/**
	*show the operate process, if need, override the method.
	*/
	_fAlert : function(msg){
		if(this.fAlert){
			this.fAlert(msg);
		}else{//default deal with.
			alert(msg);
		}
	},
	/**---------------------------------------选择实现的方法　END-------------------------------------**/
	
	/**---------------------------------------内部实现方法　START-------------------------------------**/
	initData : function(){	
		document.body.style.display = 'none';
		this._beforeInitData();
		this.loadData();
	},
	loadData : function(){
		//ViewTemplateMgr.findById(this.objectId, {viewId : getPageParams()["viewId"]}, null, this.dataLoaded.bind(this));
		ViewTemplateMgr.findById(this.objectId, {
//			AllFieldsToHTML : true,
			FlowDocId : getParameter("FlowDocId") || 0,
			channelId : getParameter("channelid")
		}, null, this.dataLoaded.bind(this), function _f500(){
			//兼容视图被删除，但从站点访问的情况
			if(window.opener){
				var sRedirectURL = getWebURL() + "WCMV6/document/document_detail_show.html";
				sRedirectURL += window.location.search;//.substring(1);
				window.location.href =  sRedirectURL;
			}
		});
	},
	dataLoaded : function(transport, json){
		document.body.style.display = '';
		var sValue = TempEvaler.evaluateTemplater(this.objectTemplate, json);
		//Element.update(this.objectContainer, sValue);
		var oFlowDiv = $('FlowDiv');
		if(oFlowDiv){
			new Insertion.After(oFlowDiv, sValue);
		}else{
			new Insertion.Top(this.objectContainer, sValue);
		}
		this._afterInitData(transport, json);
		this.initEvent();
	},
	initEvent : function(){
		this._bindEvents();
	}
	/**---------------------------------------内部实现方法　END-------------------------------------**/
};

/*-----------------------------------------command button start-------------------------------*/
try{
	FloatPanel.addCloseCommand("关闭");   
}catch(error){//兼容window.open方式
	//just skip it.
}
/*-----------------------------------------command button end---------------------------------*/

Event.observe(window, 'load', function(){
	ViewDataDealer.initData();
});

function adjustDimension(){
	var minWidth = 520, minHeight = 200, maxWidth = 800, maxHeight = 500;
	var realWidth = document.body.scrollWidth;		
	var realHeight = document.body.scrollHeight;
	realWidth = realWidth > maxWidth ? maxWidth : (realWidth < minWidth ? minWidth : realWidth);
	realHeight = realHeight > maxHeight ? maxHeight : (realWidth < minHeight ? minHeight : realHeight);
	//if(realHeight == maxHeight){
		$(ViewDataDealer.objectContainer).style.overflow = "auto";
	//}
	try{
		FloatPanel.setSize(realWidth, realHeight);
	}catch(error){//兼容window.open方式
		//just skip it.
	}
	//FloatPanel.show();
}