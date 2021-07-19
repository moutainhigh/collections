define(['require','jquery', 'util','loginStatus','common'], function(require, $, util,loginStatus,common){
    var index = {
    	/**
    	 * 模块初始化。
    	 */
    	_init: function(){
    		require(['domReady'], function (domReady) {
				domReady(function () {
					//var user = common.getCurrentUser();
		    		// 处理模板
			    	/*$("body").renderTemplate({templateName:'footer',insertType:'append'});
			    	$("body").renderTemplate({templateName:'header',insertType:'prepend'},user);*/
			    	
			    	/* 点击进入的隐藏显示效果 */
			    	$(function(){
			    		$(".auth").mouseover(function(){
			    		    $("#auth-enter-click").css("display","block");
			    		}).mouseout(function(){
			    			$("#auth-enter-click").removeClass(".auth .enter-click");
			    			$("#auth-enter-click").addClass(".auth .enter-click-out");
			    		});
			    		$(".phone").mouseover(function(){
			    		    $("#phone-enter-click").css("display","block");
			    		}).mouseout(function(){
			    			$("#phone-enter-click").removeClass(".phone .enter-click");
			    			$("#phone-enter-click").addClass(".phone .enter-click-out");
			    		});
			    		$(".user-info").mouseover(function(){
			    		    $("#info-enter-click").css("display","block");
			    		}).mouseout(function(){
			    			$("#info-enter-click").removeClass(".user-info .enter-click");
			    			$("#info-enter-click").addClass(".user-info .enter-click-out");
			    		});
			    		$(".user-pwd").mouseover(function(){
			    		    $("#pwd-enter-click").css("display","block");
			    		}).mouseout(function(){
			    			$("#pwd-enter-click").removeClass(".user-pwd .enter-click");
			    			$("#pwd-enter-click").addClass(".user-pwd .enter-click-out");
			    		});
			    		
//			    		$("#pwd-enter-click").on('click',index.goChangePwd);
//			    		$("#user-realname-identify").on('click',index.goIndentity);
//			    		$("#info-enter-click").on('click',index.goChangeRegInfo);
//			    		$("#phone-enter-click").on('click',index.goMobileAuthentication);
			    		
			    		
			    		$(".user-pwd").on('click',index.goChangePwd);
			    		$("#user-realname-identify").on('click',index.goIndentity);
			    		$(".user-info").on('click',index.goChangeRegInfo);
			    		$(".phone").on('click',index.goMobileAuthentication);
			    	})
			 		
				});//enf domReady
			});
    		
    	},
    	
    	goChangePwd : function(){
    		//window.location.href = "../../../page/apply/security/changePwd.html";
    		window.location.href = "changePwd.html";
    	},
    	
    	goIndentity : function(){
    		//window.location.href = "../../../page/apply/security/indentity.html";
    		window.location.href = "indentity.html";
    	},
    	
    	goChangeRegInfo : function(){
    		//window.location.href = "../../../page/apply/security/changeRegInfoValidate.html";
    		window.location.href = "changeRegInfoValidate.html";
    	},
    	
    	goMobileAuthentication : function(){
    		//window.location.href = "../../../page/apply/security/mobileAuthentication.html";
    		window.location.href = "mobileAuthentication.html";
    	}
    	
    	
    };
    index._init();
    return index;
});