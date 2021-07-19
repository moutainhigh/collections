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
					    	
					    	var roleCode = jazz.util.getParameter("roleCode");
					    	
					    	$_this.queryRoleInfo(roleCode);
					    	
					    	$("#btnSave").click(function(event){
					    		$_this.Save(roleCode);
					    	});
					    	
					    	$("#btnReSet").click(function(event){
					    		$_this.ReSet();
					    	});
					    	
					    	$("#btnClose").click(function(event){
					    		$('.jazz-ie6-position-fixed', window.parent.document).css("display","none");
					    		window.parent.document.getElementById("winContent").style.display="none";
					    	});
					    });
				});
	    	},
	    	
	    	queryRoleInfo : function(roleCode){
	    		
	    		if(roleCode){
	    			$.ajax({
	    				url : "../../otherselect/queryRoleInfo.do",
	    				data :{
	    					roleCode : roleCode
	    				},
	    				async : false,
	    				type : 'post',
	    				dataType : 'json',
	    				//contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
	    				success : function(data) {
	    					$("#roleName").val(data[0].rolename);
	    					$("#roleCode").val(data[0].rolecode);
	    					$("#roleDesc").val(data[0].roledesc);
	    				},
	    				error : function() {
	    					jazz.warn("查询角色信息失败");
	    				}
	    			});
	    		}
	    	},
	    	
	    	Save : function(roleCode){
	    		 
	    		var oldRoleCode = roleCode;
	    		
	    		var roleName = $("#roleName").val();
	    		var newRoleCode = $("#roleCode").val();
	    		var roleDesc = $("#roleDesc").val();
	    		
	    		if(!roleName || !roleCode || !roleDesc){
	    			jazz.warn("请完整填写角色信息！");
	    		}else{
	    			$.ajax({
	    				url : "../../otherselect/SaveRole.do",
	    				data :{ 
	    					oldRoleCode : oldRoleCode,
	    					roleName : roleName,
	    					newRoleCode : newRoleCode,
	    					roleDesc : roleDesc
	    				},
	    				async : false,
	    				type : 'post',
	    				dataType : 'text',
	    				contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
	    				success : function(data) {
	    					if("1"==data){
	    						jazz.warn("保存成功！");
								window.parent.document.getElementById("winContent").style.display="none";
								window.parent.location.reload();
	    					}else{
	    						jazz.warn("保存失败！");
	    					}
	    				},
	    				error : function() {
	    					jazz.warn("保存失败");
	    				}
	    			});
	    		}
	    	},
	    	
	    	
	    	ReSet : function(){
	    		$(".add").val("");
	    	},
	    	
	    	getContextPath: function (){
	    		var pathName = document.location.pathname;//  /page/mgr/torch/sysconfig/functionList.html
	    		var index = pathName.substr(1).indexOf("/");//  4
	    		var result = pathName.substr(0,index+1);//    /page
//	    		alert(pathName);
	    		return result;
	    	}
	};
torch._init();
return torch;
});
