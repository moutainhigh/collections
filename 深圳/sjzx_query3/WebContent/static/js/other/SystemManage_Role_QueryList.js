define(['jquery', 'jazz','util' ], function($, jazz,util){
 var torch ={};
	torch = {
		/**
		 * 模块初始化
		 */
			_init: function(){
				$_this = this;
				require(['domReady'], function (domReady) {
					    domReady(function () {
					    	
					    	$_this.querySXList();
					    	$("#add_button").css("display","block");
					    	$("#add_button").off('click').on('click',torch.addRole);
					    	
					    	$("#save_button").off('click').on('click',$_this.querySXList);
						    $("#reset_button").off('click').on('click',torch.resetEditCondition);
							
						    util.exports('editRole',$_this.editRole);
						    util.exports('deleteRole',$_this.deleteRole);
						    util.exports('managePerson',$_this.managePerson);
						    util.exports('managePower',$_this.managePower);
						    
					    });
				});
	    	},
	    	querySXList : function(){
	    		//torch.getCount();
	    		//$("#tips").css("display","block");
	    		var gridUrl="../../otherselect/RoleQueryList.do";
	    		$("#RoleQueryListGrid").gridpanel("option",'datarender',torch.backFunction);
	    		$("#RoleQueryListGrid").gridpanel("option",'dataurl',gridUrl);
	    		$("#RoleQueryListGrid").gridpanel('query', ['RoleQueryListPanel'],null);
	    		
			},
	    	/**
	    	 * 编辑角色信息 角色代码 角色名称 角色描述 
	    	 */
			editRole:function(rolecode){
				$(".jazz-ie6-position-fixed").css("display","block");
				$("#winContent").css({"display":"block","height":"410","width":"448"});
	    		$("#winContent").attr("src",$_this.getContextPath()+"/page/otherselect/editRole.html?roleCode="+rolecode);
	    	},
			
			/**
	    	 * 删除该角色
	    	 */
	    	deleteRole:function(rolecode){
	    		//var roleCode = jazz.util.getParameter("roleCode");
	    		jazz.confirm("删除角色后 该角色下所有人员以及该角色所具有的权限功能会一并删除 确认删除？",function(){
	    			$.ajax({
	    				url : "../../otherselect/deleteRole.do?roleCode="+rolecode,
	    				async : false,
	    				type : 'post',
	    				success : function(data) {
	    					if("1" == data){
	    						jazz.warn("删除成功！");
	    						location.reload();
	    					}else{
	    						jazz.warn("系统内部错误！删除失败！");
	    					}
	    				},
	    				error : function() {
	    					jazz.warn("系统内部错误！删除失败！");
	    				}
	    			});
	    		});
	    		
	    	},
			
			/**
			 * 角色下人员管理
			 */
			managePerson:function(rolecode){
	    		var url = $_this.getContextPath()+"/page/otherselect/SystemManage_Role_PersonList.html?roleCode="+rolecode;
	    		window.open(url);
	    		//util.openWindow("RoleQueryDetail","角色操作",$_this.getContextPath()+"/page/otherselect/RoleQueryDetail.html?rolecode="+rolecode,900,500);
	    	},
	    	
	    	
	    	/**
	    	 * 角色下权限管理
	    	 */
			managePower:function(rolecode){
				var url = $_this.getContextPath()+"/page/otherselect/SystemManage_Role_PowerList.html?roleCode="+rolecode;
	    		window.open(url);
	    		//util.openWindow("RoleQueryDetail","角色操作",$_this.getContextPath()+"/page/otherselect/RoleQueryDetail.html?rolecode="+rolecode,900,500);
	    	},
	    	
	    	/**
	    	 * 回调
	    	 */
	    	backFunction:function (event, obj) {
	    		var data = obj.data;
	    		if (data.length == 0) {
	    			$(".nodata").css("display", "block");
	    		} else {
	    			$(".nodata").css("display", "none");
	    		}
	    		for (var i = 0; i < data.length; i++) {
	    			data[i]["opt"] = '<a href="javascript:void(0);" onclick="ebaic.editRole(\''+data[i].rolecode+'\')">'+"编辑角色"+
	    			'</a>'+'&nbsp;&nbsp;'+'<a href="javascript:void(0);" onclick="ebaic.deleteRole(\''+data[i].rolecode+'\')">'+"删除角色"+
	    			'</a>'+'&nbsp;&nbsp;'+'<a href="javascript:void(0);" onclick="ebaic.managePerson(\''+data[i].rolecode+'\')">'+"用户管理"+
	    			'</a>'+'&nbsp;&nbsp;'+'<a href="javascript:void(0);" onclick="ebaic.managePower(\''+data[i].rolecode+'\')">'+"权限管理"+
	    			'</a>';
	    		}	
	    		return data;
	    	},
	    	/**
	    	 * 打开queryEditConfig.html页面
	    	 *//*
	    	configEditOrQuery:function(functionId){
	    		var url = $_this.getContextPath()+"/mgr/torch/sysconfig/queryEditConfig.html?functionId="+functionId;
	    		window.location.href=url;
	    	},
	    	*/
	    	/**
	    	 * 弹出funcConfig添加页面
	    	 */
	    	/*addFuncConfig:function(){
	    		var treeId = jazz.util.getParameter("treeId");
	    		var treePath = jazz.util.getParameter("treePath");
	    		util.openWindow("addFunctionConfig","新增功能信息","functionConfig.html?treeId="+treeId+"&treePath="+treePath,1000,500);
	    		var url = $_this.getContextPath()+"/mgr/torch/sysconfig/functionConfig.html";
	    		window.location.href=url;
	    	},*/
	    	//html中获取当前页的 上下文路径
	    	getContextPath: function (){
	    		var pathName = document.location.pathname;//  /page/mgr/torch/sysconfig/functionList.html
	    		var index = pathName.substr(1).indexOf("/");//  4
	    		var result = pathName.substr(0,index+1);//    /page
//	    		alert(pathName);
	    		return result;
	    	},
	    	
	    	/**
	    	 * 添加角色
	    	 */
	    	addRole : function(){
	    		$(".jazz-ie6-position-fixed").css("display","block");
	    		$("#winContent").css({"display":"block","height":"350","width":"448"});
	    		$("#winContent").attr("src",$_this.getContextPath()+"/page/otherselect/AddRole.html");
	    	},
	    	
	    	/**
	    	 * 重置方法
	    	 */
	    	resetEditCondition : function(){
	    				for( x in torch.edit_fromNames){
	    					$("div[name='"+torch.edit_fromNames[x]+"']").formpanel("reset");
	    				} 
	    	},
	    	edit_fromNames : [ 'RoleQueryListPanel']
	    	
		};
	
		torch._init();
		return torch;
});
