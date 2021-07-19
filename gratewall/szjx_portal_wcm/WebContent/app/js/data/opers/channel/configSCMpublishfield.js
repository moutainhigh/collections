Ext.ns('wcm.domain.ChannelMgr');
(function (){
	var m_oMgr = wcm.domain.ChannelMgr;
	Ext.apply(wcm.domain.ChannelMgr, {
		'configSCMpublishfield' : function(event){
			
			var currObj = event.getObj();//获取当前选中的对象
			var nHostId = currObj.getId();//获取对象的id				
			var nHostType = currObj.getIntType();
			var currContext = event.getContext();
			var sitetype = currContext.params.SITETYPE;
			var nSiteType = 0;
			////获取站点类型
			if(sitetype != undefined && sitetype != null){
				if(sitetype == "4"){
					nSiteType = 4;
				}
			}
			var cbId = 'ConfigSCMCrashBoard';//定义弹出框的唯一标识			
			var crashboard = wcmXCom.get(cbId);//获取整个系统中的弹出框对象
			//如果这个对象不存在，则重新定义弹出框
			if(!crashboard){
				crashboard = new wcm.CrashBoard({
					id: cbId,
					title:'配置从WCM发布微博时使用的文档字段',
					maskable:true, //是否启用遮布
					draggable : true,//是否可以拖动
					width : '530px',//宽度
					height : '300px',//高度
					params : {HostId:nHostId,HostType:nHostType,SiteType:nSiteType},//传给弹出框页面的参数
					src: WCMConstants.WCM6_PATH + 'scm/config_documentfield_wcm.jsp',//内嵌页面地址					
					callback : function(params){//回调函数							
						//定义一个BasicDataHelper对象						
						var oHelper = new com.trs.web2frame.BasicDataHelper();
						oHelper.Call('wcm61_scmmicrocontenttemplate', 'save', 
							{ObjectId:params.ObjectId,HostId:params.HostId,HostType:params.HostType,MicroContentStyle:params.MicroContentStyle},
									true, function(){
										alert("设置成功！");
										this.close();
							}); 
					}
				});
				//弹出CrashBoard
				crashboard.show();
			}
		}
	});
})();
(function(){
	var fnIsVisible = function(event){
		if($MsgCenter.getActualTop().g_IsRegister['SCM']){
			//通过同步的ajax请求，判断用户是否是SCM管理员或者分组维护人员
			var currContext = event.getContext();
			var sitetype = currContext.params.SITETYPE;
			//仅文字库和资源库下显示操作入口
			if(sitetype != undefined && sitetype != null && ( sitetype == "4" || sitetype == "0" )){
				var transport = ajaxRequest({
					url : WCMConstants.WCM_ROOTPATH + 'center.do?serviceid=wcm61_scmgroup&methodname=isAdminsOfSCMGroups',
					method : 'GET',
					parameters : '',
					asyn : false//执行同步请求
				});	
				//将返回的xml字符串解析成json对象,此处可参考：http://wiki.trs.org.cn/pages/viewpage.action?pageId=6357513
				if(transport != undefined && transport != null){
					var json = parseXml(loadXml(transport.responseText));
					return $a(json, "result") == 'true';
				}
			}
			
		}
		return false;
	};
	//获取操作注册的对象
	var reg = wcm.SysOpers.register;
	reg({
		key : 'configscmpublishfield',
		type : 'channel',
		desc : '配置发微博文档字段',
		title : '配置从WCM发布微博时使用的文档字段',
		//rightIndex : 13, //13:修改栏目
		fn : function(event){ //点击操作后的响应函数
			//调用在上面定义的方法
			wcm.domain.ChannelMgr.configSCMpublishfield(event);
		},
		isVisible : fnIsVisible
	});
})();