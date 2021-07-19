$package('com.trs.wcm.domain');

$import('com.trs.dialog.Dialog');
$import('com.trs.wcm.domain.PublishMgr');
$import('com.trs.wcm.domain.TemplateMgr');

com.trs.wcm.domain.WebSiteMgr=Class.create('wcm.domain.website.WebSiteMgr');
com.trs.wcm.domain.WebSiteMgr.prototype = {
	initialize: function(){
		this.objectType = 103;
		this.serviceId = 'wcm6_website';
		this.helpers = {};
	},
	getInstance : function(){
		if($webSiteMgr == null) {
			$webSiteMgr = new com.trs.wcm.domain.WebSiteMgr();
		}
		return $webSiteMgr;
	},
	getHelper : function(_sServceFlag){
		return new com.trs.web2frame.BasicDataHelper();
		/*
		_sServceFlag = (!_sServceFlag)?'basic':_sServceFlag.toLowerCase();
		if(!this.helpers[_sServceFlag]) {
			var oHelper = null;
			switch(_sServceFlag){
				case 'basic' :
					oHelper = BasicDataHelper;
					break;
				case 'WebSites' :
					oHelper = $dataHelper(com.trs.wcm.datasource.website.WebSites);
					break;
				default :
					alert('WebSiteMgr不支持"'+_sServceFlag+'"数据源');
					break;
			}
			this.helpers[_sServceFlag] = oHelper;
		}
		return this.helpers[_sServceFlag];
		*/
	},
	/********************************************************
	* 定义业务逻辑
	*********************************************************
	*/
	'new' : function(_sId, _oPageParams){
		this.edit(0, _oPageParams);
	},
	edit : function(_sId, params){
		params = params || {};
		var sheetIndex = params["sheetIndex"] || 0;
		var view = params["view"] || false;
		var url = './website/website_add_edit.html?siteid=' + _sId + "&sheetIndex=" + sheetIndex + "&view=" + view;
		if(params.sitetype != undefined){
			url += "&siteType=" + params.sitetype;
		}
		FloatPanel.open(url, '新建/修改站点', 550, 500);
	},
	preview : function(_sId){
		$publishMgr.preview(_sId, this.objectType);
	},
	quickpublish : function(_arrIds){//仅发布站点首页
		this.homepublish(_arrIds);
	},
	homepublish : function(_arrIds){//仅发布站点首页
		this.publish(_arrIds, "soloPublish");
	},
	increasepublish : function(_arrIds){
		this.publish(_arrIds, "increasingPublish");
	},
	completepublish : function(_arrIds){
		this.publish(_arrIds, "fullyPublish");
	},
	updatepublish : function(_arrIds){
		this.publish(_arrIds, "refreshPublish");
	},
	cancelpublish : function(_arrIds){
		this.publish(_arrIds, "recallPublish");
	},
	publish : function(_sIds, publishTypeMethod){
		$publishMgr.publish(_sIds, this.objectType, publishTypeMethod);
	},
	trash : function(_arrIds){
		this.recycle(_arrIds.join(","));
	},
	recycle : function(_arrIds,_bNoNeedRefresh,_oCallBacks){
		_oCallBacks = _oCallBacks || {};
		_arrIds = _arrIds + "";
		var siteType = $nav().findSiteType($nav().$('s_' + (_arrIds.split(",")[0])));
		if(confirm('确实要将这' + _arrIds.split(",").length + '个站点放入站点回收站吗? ')){
			ProcessBar.init('进度执行中，请稍候...');
			ProcessBar.addState('正在放入回收站');			
			ProcessBar.addState('删除完成');
			ProcessBar.addState('正在刷新导航树');
			ProcessBar.start();
			this.getHelper().call(this.serviceId,'delete',{objectids:_arrIds,"drop":false},true, function(){
				ProcessBar.next();
				setTimeout(function(){
					ProcessBar.next();
					setTimeout(function(){
						ProcessBar.exit();
						var mainWin = $MessageCenter.iframes["main"].contentWindow;
						if(mainWin.WebSites){//主列表页面为站点页面
							$nav().refreshSiteType(siteType);// || PageContext.params['SiteType']);
							if(!_bNoNeedRefresh){
								$MessageCenter.sendMessage('main', 'WebSites.loadWebSites', "WebSites", null);
							}
						}else{
							$nav().doAfterDelSite(_arrIds);
							$nav().refreshMain();
						}
					},100);
				},100);
			},_oCallBacks["onFailure"],_oCallBacks["onFailure"]);
			return true;
		}
		return false;
	},
	trace : function(siteId, rights){
		var nSiteType = 0;
		try{
			nSiteType = $nav().findFocusNodeSiteType() || 0;
		}catch(error){
			nSiteType = 0;
		}
		(top.actualTop||top).location_search = '?siteid=' + siteId + "&RightValue=" + rights + "&SiteType=" + nSiteType;
		document.location.href = '../channel/channel_thumbs_index.html?siteid=' + siteId + "&RightValue=" + rights;		
		// ge gfc add @ 2007-4-3 17:15 加入页面切换的过度页面
		if((top.actualTop || top).RotatingBar) {
			(top.actualTop || top).RotatingBar.start();
		}
	},
	'export' : function(siteIds){
		siteIds = siteIds + "";
		ProcessBar.init('进度执行中，请稍候...');
		ProcessBar.addState('正在执行导出处理');			
		ProcessBar.addState('处理完成');
		ProcessBar.start();
		this.getHelper().call(this.serviceId,'export', {objectids: siteIds}, true, function(_oXMLHttp, _json){
			ProcessBar.next();
			setTimeout(function(){
				ProcessBar.exit();
				var sFileUrl = _oXMLHttp.responseText;

				var frm = (top.actualTop||top).$('iframe4download');
				if(frm == null) {
					frm = (top.actualTop||top).document.createElement('IFRAME');
					frm.id = "iframe4download";
					frm.style.display = 'none';
					(top.actualTop||top).document.body.appendChild(frm);
				}
				sFileUrl = "/wcm/file/read_file.jsp?DownName=WEBSITE&FileName=" + sFileUrl;
				frm.src = sFileUrl;
			}, 100);
		});
	},
	'import' : function(){
		FloatPanel.open('./website/website_import.html', '站点导入', 400, 100);
	},
	afterImport : function(idObject){
		try{
			$nav().refreshSiteRoot(PageContext.params['SiteType']);
			$MessageCenter.sendMessage('main', 'WebSites.update', 'WebSites', idObject);			
		}catch (ex){
			//TODO logger
			//alert(ex.description);
		}		
	},
	quicknew : function(){
		FloatPanel.open('./website/website_create.jsp', "智能建站", 730, 420);
	},
	synchtemplate : function(_arrIds){
		//if(!confirm("确定要同步模板到栏目吗？\n可能会覆盖栏目模板...")) {
		//	return;
		//}		
		//else		
		//wenyh@2007-12-04 改为用$confirm提示,突出提示
		var sHtml = "确实要同步模板到栏目吗？<br><span style='color:red;font:16px;'>注意：<br>该操作会覆盖更改所有栏目的模板设置！</span>";
		var _self = this;
		var okFunc = function(){
			$dialog().hide();
			$beginSimplePB('正在同步模板到栏目..', 2);			
			$templateMgr.synchTemplates(_arrIds, _self.objectType, function(){
					setTimeout(function(){
						$endSimplePB();
						$timeAlert('成功将当前站点模板同步到了其栏目下！', 3, null, null, 2);
					}, 100);
				}
			);
		}

		$confirm(sHtml,okFunc,function (){$dialog().hide()},"操作确认");
	},
	likecopy : function(siteId){
		FloatPanel.open('./website/website_likecopy.html?siteid=' + siteId, "类似创建站点", 400, 100);		
	},
	queryWebSites : function(_frmQueryData){
		WebSites.loadWebSites();		
	},
	commentmgr : function(_sId, _oPageParams){
		var oParams = {
			SiteId : _sId
		}
		var sUrl = '../../comment/comment_mgr.jsp?' + $toQueryStr(oParams);
		$openMaxWin(sUrl);
	},
	confphotolib : function(){
		FloatPanel.open("./photo/photolib_config.html","图片库设置",680,280);
	},
	//fjh @ 2007-9-14 18:38 增加“视频库配置”的操作定义 
	confvideolib : function(){
		FloatPanel.open("./video/config.jsp","视频库设置",480,200);
	},
	//ge gfc add @ 2007-6-28 9:48 追加“管理模板变量”的操作定义
	settempargs : function(_sId){
		var sParams = 'siteid=' + _sId;
		FloatPanel.open('./template/template_args_index.html?' + sParams, '站点模板变量', 500, 450);
	}
};

var $webSiteMgr = new com.trs.wcm.domain.WebSiteMgr();