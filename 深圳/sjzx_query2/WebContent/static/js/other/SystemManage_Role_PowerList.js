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
					    	
					    	//$("#save_button").off('click').on('click',$_this.saveFuncRole);
						    $("#add_button").off('click').on('click',torch.addFuncRole);
						    util.exports('editFunc',$_this.editFunc);
					    });
				});
	    	},
	    	querySXList : function(){
	    		var roleCode = jazz.util.getParameter("roleCode");
	    		//alert(roleCode);
	    		var gridUrl="../../otherselect/RolePowerList.do?roleCode="+roleCode;
	    		$("#RolePowerListGrid").gridpanel("option",'datarender',torch.backFunction);
	    		$("#RolePowerListGrid").gridpanel("option",'dataurl',gridUrl);
	    		$("#RolePowerListGrid").gridpanel('query',['RolePersonListPanel'],null);
	    		
			},
	    	/**
	    	 * 弹出editFunctionConfig.html页面
	    	 */
	    	editFunc:function(functionCode){
	    		var roleCode = jazz.util.getParameter("roleCode");
	    		jazz.confirm("确认删除？",function(){
	    			$.ajax({
	    				url : "../../otherselect/deleteRolePower.do?functionCode="+functionCode+'&roleCode='+roleCode,
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
	    	 * 添加人员角色信息
	    	 */
	    	addFuncRole:function(){
	    		var roleCode = jazz.util.getParameter("roleCode");
	    		$('.jazz-ie6-position-fixed', window.parent.document).css("display","block");
	    		$("#winContent").css({"display":"block","height":"380","width":"450"});
	    		$("#winContent").attr("src",$_this.getContextPath()+"/page/otherselect/AddFuncRole.html?roleCode="+roleCode);
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
	    			data[i]["opt"] = '<a href="javascript:void(0);" onclick="ebaic.editFunc(\''+data[i].functionCode+'\')">'+"删除"+'</a>';
	    		}	
	    		return data;
	    	},
	    	//html中获取当前页的 上下文路径
	    	getContextPath: function (){
	    		var pathName = document.location.pathname;//  /page/mgr/torch/sysconfig/functionList.html
	    		var index = pathName.substr(1).indexOf("/");//  4
	    		var result = pathName.substr(0,index+1);//    /page
//	    		alert(pathName);
	    		return result;
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
