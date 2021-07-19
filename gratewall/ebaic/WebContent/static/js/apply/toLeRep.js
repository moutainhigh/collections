define(['require','jquery', 'common','util'], function(require, $, common,util){
	
    var tolerep = {
    	/**
    	 * 模块初始化。
    	 */
    	_init: function(){
    		require(['domReady'], function (domReady) {
    			  domReady(function () {
    				    $("#submitToLeRep").on('click', tolerep.submitToLeRep);
    				    $("#applyAuth").on('click', tolerep.setAuthType);
    				    $("#showApplyDetail").on('click',tolerep.showApplyDetail);
    				    $('#cancel').on('click',tolerep.cancel);
    				    $('#backToAppUser').on('click',tolerep.backToAppUser);
    			  });
    			});
	    		    	
	    	
    	},
    	backToAppUser:function(){
    		var gid = jazz.util.getParameter('gid');
			jazz.confirm("确定要将业务退回申请人吗？点击【确定】提交。",function(){
				
	    		var params = {
	    		        url : '../../../apply/setup/submit/backToApp.do',
	    		        params:{
	    		        	gid:gid,
	    		        },
	    		        async:false,
	    		        callback : function(data, param, res) {
	    		        	jazz.info("已成功退回申请人，请联系申请人尽快修改有误信息后重新提交业务，避免影响您的业务办理时间。",function(){
								 window.location.href="../../../page/apply/person_account/home.html";
								 
							 });
	    		        }
	    		    };  	
	    		$.DataAdapter.submit(params);
			},function(){});
    	},
    	showApplyDetail:function(){
    		var gid = jazz.util.getParameter('gid');
    		window.location.href="app_detail.html?gid="+gid;
    	},
    	setAuthType:function(){
    		var gid = jazz.util.getParameter('gid');
    		window.location.href="le_auth.html?gid="+gid;
    		/*var authType='1';
    		
    		var gid = jazz.util.getParameter('gid');
    		//var isNext = jazz.util.getParameter('isNext');
    		$.ajax({
    			url:'../../../req/setAuthType.do',
    			type:"post",
    			data:{gid:gid,authType:authType},
    			success:function(data){
    				if(data){
    					var err = $.parseJSON(data);
    					if(err['exceptionMes']){
    			    		jazz.error('<font color="blue" >错误信息</font> : ' + err['exceptionMes']);
    			    		return ;
    					}
    				}
    				jazz.info("保存成功",function(){
    					//if(isNext){
    						parent.location.href="../../../page/apply/person_account/home.html?gid="+jazz.util.getParameter('gid');
    					//}
						util.closeWindow('tipPage');
					});	
    			},
    			error : function(responseObj) {
    			    if(responseObj["responseText"]){
    			    	var err = $.parseJSON(responseObj["responseText"]);
    			    	if(err['exceptionMes']){
    			    		jazz.info('<font color="blue" >错误信息</font> : ' + err['exceptionMes']);				    					    		
    			    	}else{
    			    		jazz.error('与服务器连接断开，请尝试重新登录或与管理员联系!');
    			    	}
    			    }else{
    			    	jazz.error('与服务器连接断开，请尝试重新登录或与管理员联系!');
    			    }
    			    
    			    return false;
    			}
    		});*/
    	},
    	
    	cancel:function(){
    		window.location.href="../../../page/apply/person_account/home.html";
    	},
    	submitToLeRep:function(){
	    	var gid = jazz.util.getParameter('gid');
			var params = {
		        url : '../../../apply/setup/submit/beforeSubmit.do',
		        params:{
		        	gid:gid
		        },
		        async:false,
		        callback : function(data, param, res) {
		        	var url = res.getAttr('nextUrl');
		        	if(url){
		        		url+=("?isLe=1&gid="+gid);
		        		util.openWindow("confirmSMS", "短信确认", url ,600 , 250);
		        	}else{
		        		jazz.error('提交失败');
		        		/*jazz.confirm("提交后不可修改，请确认您填写的信息完整准确，真实有效。点击【确定】提交。",function(){
		    				
    		        		var params = {
    		        		        url : '../../../apply/setup/submit/submitToLeRep.do',
    		        		        params:{
    		        		        	gid:gid
    		        		        },
    		        		        async:false,
    		        		        callback : function(data, param, res) {
    		        		        	var msg = res.getAttr('msg');
    		        		        	jazz.info(msg,function(){
    		   							 window.location.href="../../../page/apply/person_account/home.html";
    		   							 //util.closeWindow('tipPage');
    		   						});
    		        		        }
    		        		};  	
    						$.DataAdapter.submit(params);
		        		},function(){});*/
		        	}
					
		        }
		    };  	
			$.DataAdapter.submit(params);
		
	    },
    	submitToLeRep_bak:function(){
    		var gid = jazz.util.getParameter('gid');
			jazz.confirm("提交后不可修改，请确认申请人填写的信息完整准确，真实有效。点击【确定】提交。",function(){
				
			var params = {
    		        url : '../../../apply/setup/submit/leRepSubmit.do',
    		        params:{
    		        	gid:gid,
    		        },
    		        async:false,
    		        callback : function(data, param, res) {
    		        	jazz.info("提交成功，您将在两个工作日内收到反馈结果，请留意系统信息与短信提醒。",function(){
							 window.location.href="../../../page/apply/person_account/home.html";
							 
						 });
    		        }
    		    };  	
    		$.DataAdapter.submit(params);
        	},function(){});
    	}
    	
    	
    };
    tolerep._init();
    return tolerep;
});