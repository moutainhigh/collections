(function(){
	
	function createMobileChannel(event, args){
		var data = {
				SrcChnlId : event.getObj().getId(), 
				DestHostId : args.dstId,
				DestHostType : args.dstType,
				containChildren : args.containChildren,
					DocSDate : args.DocSDate
		};
		BasicDataHelper.call('wcm61_mobileportal', 'createMobileChannel', data, true, function(transport, json){
			alert("创建成功");
		});
	}
	var fnIsVisible = function(event){
		var srcChannelId = event.getObj().getId();
		//通过同步的ajax请求，获取当前栏目是否可创建移动栏目
		var transport = ajaxRequest({
			url : WCMConstants.WCM_ROOTPATH + 'center.do?serviceid=wcm61_mobileportal&methodname=isCanCreateMobile',
			method : 'GET',
			parameters : 'objectid='+srcChannelId +'&objecttype=101',
			asyn : false//执行同步请求
		});
		var json = parseXml(loadXml(transport.responseText));
		if(json.RESULT == 'false'){
			return false;
		}
		return true;
	}
	var createMobilechnl = function(event){
		var host = event.getHost();
		var hostId = host.getId();
		var sSrcId = (event.getIds().length == 0)?hostId : event.getIds();
		var sSiteType = event.getContext().params["SITETYPE"];
		var oParams = {srcId : sSrcId, siteType : sSiteType};
		FloatPanel.open(WCMConstants.WCM6_PATH + 'mobile/channel_select_4_create_channel.jsp?' + $toQueryStr(oParams) , '创建移动门户栏目',createMobileChannel.bind(this,event));
	}
	wcm.SysOpers.register({
		key : 'create4mobile',
		type : 'channel',
		desc : '生成移动门户栏目',
		title : '生成移动门户栏目',
		rightIndex : 13,
		order : 2,
		fn : createMobilechnl,
		isVisible : fnIsVisible
	});
	wcm.SysOpers.register({
		key : 'create4mobile',
		type : 'channelMaster',
		desc : '生成移动门户栏目',
		title : '生成移动门户栏目',
		rightIndex : 13,
		order : 3,
		fn : createMobilechnl,
		isVisible : fnIsVisible
	});
})();