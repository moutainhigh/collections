/*发布一条微博*/
Ext.ns('wcm.domain.DocumentMgr');
(function(){
	Ext.apply(wcm.domain.DocumentMgr, {
		'showCrashBoard' : function(event){
			var currObj = event.getObj();//获取当前选中的文档对象
			var nRecId = currObj.getPropertyAsInt('objId');//获取文档的ChnlDocId
			//获取文档的发布状态
			var nStatusId = currObj.getPropertyAsInt('docstatusId');
			if(nStatusId != 10){
				var sDocTitle = currObj.getPropertyAsString('doctitle');
				alert('抱歉，文档还未发布！请确认文档已审核通过并且发布！\n文档标题：' + sDocTitle);
				return;
			}
			//定义弹出框的唯一标识
			var cbId = 'SCMCrashBoard';
			//获取整个系统中的弹出框对象
			var crashboard = wcmXCom.get(cbId);
			//如果这个对象不存在，则重新定义弹出框
			if(!crashboard){				
				crashboard = new wcm.CrashBoard({
					id: cbId,
					title:'从WCM创建微博',
					maskable:true, //是否启用遮布
					draggable : true,//是否可以拖动
					width : '574px',//宽度
					height : '415px',//高度
					params : {RecId:nRecId,SCMGroupId:0,SiteType:0},//传给弹出框页面的参数
					src: WCMConstants.WCM6_PATH + 'scm/create_microblog_wcm.jsp',//内嵌页面地址
					callback : function(params){//回调函数
							var patt = new RegExp('非常抱歉');//要查找的字符串
							if(patt.test(params)){
								return;
							}
							//定义一个BasicDataHelper对象
							var oHelper = new com.trs.web2frame.BasicDataHelper();
							oHelper.Call(
								'wcm61_scmmicrocontent', 
								'save', 
								{AccountIds:params._AccountIds, SCMGroupId:params._SCMGroupId, Content:params._MicroContent, Picture:params._Picture,Source:params._Source},
								true,
								function(){
									if(params._hasWorkFlow == '1'){
										alert("微博已提交审核人员，请您耐心等待审核结果。");
									}else{
										alert("已提交发布队列，请您稍后，即刻发布微博！");
									}
									this.close();
								},
								function(_transport,_json){$render500Err(_transport,_json);this.close();},
								function(_transport,_json){}
							); 
					}
				});
				//弹出CrashBoard
				crashboard.show();
			}
		} // END OF function: showCrashBoard
	} )
})();

(function(){
	var fnIsVisible = function(event){
		var currObj = event.getObj();//获取当前选中的文档对象
		//获取文档的发布状态
		var nStatusId = currObj.getPropertyAsInt('docstatusId');
		if(nStatusId == 10){
			if($MsgCenter.getActualTop().g_IsRegister['SCM']){
				//通过同步的ajax请求，判断用户是否是SCM管理员或者分组维护人员
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
	//注册一个操作到XXX面板上
	reg({
		key : 'createmicrocontentinfo', //唯一标识该操作
		type : 'chnldoc',  //操作面板的类型参考《TRSWCM6.5二次开发-操作面板》
		desc : '发布一条微博', //操作的显示名称
		title : '发布一条微博', //操作的提示信息
		//rightIndex : 39, //权限定义值，39为发布文档的权限值
		order : 3.1,//显示顺序
		fn : function(event){ //点击操作后的响应函数
			//调用在上面定义的方法
			wcm.domain.DocumentMgr.showCrashBoard(event);
		},
		isVisible : fnIsVisible
	});
})();