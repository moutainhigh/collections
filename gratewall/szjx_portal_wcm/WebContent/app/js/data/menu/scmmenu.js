(function(){
	var reg = wcm.MenuView.register.bind(wcm.MenuView);
	reg({
		key : 'scm',
		desc : '社会化内容管理',
		parent : 'XJ',
		cls : function(event, descNode){
			if(g_IsRegister['SCM']){
				//通过同步的ajax请求，判断用户是否是SCM管理员或者分组维护人员
				var transport = ajaxRequest({
					url : WCMConstants.WCM_ROOTPATH + 'center.do?serviceid=wcm61_scmgroup&methodname=isAdminsOfSCMGroups',
					method : 'GET',
					parameters : '',
					asyn : false//执行同步请求
				});	
				//将返回的xml字符串解析成json对象,此处可参考：http://wiki.trs.org.cn/pages/viewpage.action?pageId=6357513
				if(transport != undefined && transport != null){
				if (transport.responseText != undefined && transport.responseText != null){
						var json = parseXml(loadXml(transport.responseText));
						var isPass = $a(json, "result") == 'true';
						if(isPass){
							//如果是管理员或分组维护人员，显示操作入口
							Ext.fly(descNode)[ isPass ? 'removeClass' : 'addClass']('disabled');
						}else{
							//判断用户是否是SCM审核人员
							transport = ajaxRequest({
								url : WCMConstants.WCM_ROOTPATH + 'center.do?serviceid=wcm61_scmworkflow&methodname=isAuditorOfSCM',
								method : 'GET',
								parameters : '',
								asyn : false//执行同步请求
							});
							if (transport.responseText != undefined && transport.responseText != null){
								json = parseXml(loadXml(transport.responseText));
								isPass = $a(json, "result") == 'true';
								Ext.fly(descNode)[ isPass ? 'removeClass' : 'addClass']('disabled');
							}else{
								Ext.fly(descNode)[ false ? 'removeClass' : 'addClass']('disabled');
							}
						}
					}else{
						Ext.fly(descNode)[ false ? 'removeClass' : 'addClass']('disabled');
					}
				}
			}else{
				Ext.fly(descNode)[ false ? 'removeClass' : 'addClass']('disabled');
			}
		},
		cmd : function(event){
			window.open(WCMConstants.WCM6_PATH + 'scm/index.jsp');
		}
	});
})();	
