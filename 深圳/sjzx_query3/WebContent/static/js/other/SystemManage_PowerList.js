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
					    	
					    	$("#save_button").off('click').on('click',$_this.querySXList);
						    $("#reset_button").off('click').on('click',torch.resetEditCondition);
						    /*$("#add_button").off('click').on('click',torch.addPersonRole);*/
							
						    util.exports('openFunc',$_this.openFunc);
						    util.exports('closeFunc',$_this.closeFunc);
						    
					    });
				});
	    	},
	    	querySXList : function(){
	    		//torch.getCount();
	    		//$("#tips").css("display","block");
	    		//var roleCode = jazz.util.getParameter("roleCode");
	    		var gridUrl="../../otherselect/PowerList.do?";
	    		$("#PowerListGrid").gridpanel("option",'datarender',torch.backFunction);
	    		$("#PowerListGrid").gridpanel("option",'dataurl',gridUrl);
	    		$("#PowerListGrid").gridpanel('query', ['PowerListPanel'],null);
	    		
			},
	    	/**
	    	 * 弹出editFunctionConfig.html页面
	    	 */
	    	openFunc:function(functionCode){
	    		//var url = $_this.getContextPath()+"/page/otherselect/SystemManage_Role_PersonList.jsp?userId="+userId;
	    		//window.open(url);
	    		//util.openWindow("RoleQueryDetail","角色操作",$_this.getContextPath()+"/page/otherselect/RoleQueryDetail.html?rolecode="+rolecode,900,500);
	    		//var roleCode = jazz.util.getParameter("roleCode");
	    		jazz.confirm("确认启用？",function(){
	    			$.ajax({
	    				url : "../../otherselect/banPower.do?functionCode="+functionCode+"&flag=Y",
	    				async : false,
	    				type : 'post',
	    				success : function(data) {
	    					if("1" == data){
	    						jazz.warn("启用成功！");
	    						location.reload();
	    					}else{
	    						jazz.warn("系统内部错误！启用失败！");
	    					}
	    				},
	    				error : function() {
	    					jazz.warn("系统内部错误！启用失败！");
	    				}
	    			});
	    		});
	    	},
	    	
	    	closeFunc:function(functionCode){
	    		//var url = $_this.getContextPath()+"/page/otherselect/SystemManage_Role_PersonList.jsp?userId="+userId;
	    		//window.open(url);
	    		//util.openWindow("RoleQueryDetail","角色操作",$_this.getContextPath()+"/page/otherselect/RoleQueryDetail.html?rolecode="+rolecode,900,500);
	    		//var roleCode = jazz.util.getParameter("roleCode");
	    		jazz.confirm("确认禁用？",function(){
	    			$.ajax({
	    				url : "../../otherselect/banPower.do?functionCode="+functionCode+"&flag=N",
	    				async : false,
	    				type : 'post',
	    				success : function(data) {
	    					if("1" == data){
	    						jazz.warn("禁用成功！");
	    						location.reload();
	    					}else{
	    						jazz.warn("系统内部错误！禁用失败！");
	    					}
	    				},
	    				error : function() {
	    					jazz.warn("系统内部错误！禁用失败！");
	    				}
	    			});
	    		});
	    	},
	    	
	    	/**
	    	 * 添加人员角色信息
	    	 *//*
	    	addPersonRole:function(){
	    		//var roleCode = jazz.util.getParameter("roleCode");
	    		$("#winContent").css({"display":"block","height":"410","width":"320"});
	    		$("#winContent").attr("src",$_this.getContextPath()+"/page/otherselect/AddPerson.html");
	    	},*/
	    	
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
	    		var effectiveMarker;
	    		for (var i = 0; i < data.length; i++) {
	    			effectiveMarker = data[i].effectiveMarker;
	    			if(effectiveMarker == "Y"){
	    				data[i]["opt"] = '<a href="javascript:void(0);" onclick="ebaic.closeFunc(\''+data[i].functionCode+'\')">'+"禁用"+'</a>';
	    			}else if(effectiveMarker == "N"){
	    				data[i]["opt"] = '<a href="javascript:void(0);" onclick="ebaic.openFunc(\''+data[i].functionCode+'\')">'+"启用"+'</a>';
	    			}else{
	    				data[i]["opt"] = '<span>该功能是否启用标识有误 请核实！</span>'
	    			}
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
	    	edit_fromNames : [ 'PowerListPanel']
	    	
		};
	
		torch._init();
		return torch;
});
