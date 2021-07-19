define(['require','jquery', 'common'], function(require, $, common){
	
    var login = {
    	/**
    	 * 模块初始化。
    	 */
    	_init: function(){
    		require(['domReady'], function (domReady) {
    			  domReady(function () {
    				  //验证移动电话,发送验证码
    				  $("#sendSmsVercodeBtn").click(function(){
    					  login.checkMob();
    				  });
    				  //登陆
    				  $("[name='btnLogin']").click(function(){
    					  var accountType = $("input[name='type']").val();
    					  if(accountType==1){//'1'是个人账户登录
    						   login.doLogin();
    					  }else if(accountType==2){//'2'是企业账户登录
    						  login.entDoLogin();
    					  }
    				  });
    			    	//<!-- 关闭遮罩层-->
    			    	$("#loginFirst").click(function(){ 
    			    	   if($("#show-tip").css("display")=="none"){
    			    	    $("show-notice").css("display","none");
    			    	    $("#bg").css("display","none");
    			    	    $("#loginFirst").css("display","none");
    			    	    $("#close-bg").css("display","none");
    			    	   }else {
    			    	    $("#loginFirst").css("display","none");
    			    	   }
    			    	   
    			    	});
    			    	$("#show-tip").click(function(){
    			    	 if($("#loginFirst").css("display")=="none"){
    			    	    $("#show-tip").css("display","none");
    			    	    $("show-notice").css("display","none");
    			    	    $("#bg").css("display","none");
    			    	    $("#close-bg").css("display","none");
    			    	 }else{
    			    	 	$("#show-tip").css("display","none");
    			    	 }
    			    	   
    			    	});
    			    	//<!-- 关闭遮罩层-->
    			    	
    			    	
    			    	$("#close-bg").click(function(){
    			    	   $("#show-notice").css("display","none");
    			    	   $("#bg").css("display","none");
    			    	});
    			    	
    			    	//回车登录
    			    	document.onkeydown=function(e){
    			    		 var theEvent = e || window.event;    
    			    	     var code = theEvent.keyCode || theEvent.which || theEvent.charCode;    
    			    	     if (code == 13) { 
    			    	    	 var accountType = $("input[name='type']").val();
    	    					  if(accountType==1){//'1'是个人账户登录
    	    						   login.doLogin();
    	    					  }else{//'2'是企业账户登录
    	    						  login.entDoLogin();
    	    					  }
    			    	         return false;    
    			    	     }    
    			    	     return true;    
    			    	};
    			    	
    			    	//点击企业用户按钮时用户按钮样式替换
    			    	$("#ent-user").click(function(){
    			    		$("#person").hide();
    			    		/*$("#ent").show();*/
    			    	  $("#ent-left").removeClass("ent-left");
    			    	  $("#ent-left").addClass("ent-left-click");
    			    	  $("#ent-center").removeClass("ent-center");
    			    	  $("#ent-center").addClass("ent-center-click");
    			    	  $("#ent-right").removeClass("ent-right");
    			    	  $("#ent-right").addClass("ent-right-click");
    			    	  
    			    	  $("#person-left").removeClass("person-left");
    			    	  $("#person-left").addClass("person-leftNoClick");
    			    	  $("#personCenter").removeClass("personCenter");
    			    	  $("#personCenter").addClass("person-center-noclick");
    			    	  $("#person-right").removeClass("person-right");
    			    	  $("#person-right").addClass("person-right-noclick");
    			    	  $(".mobile").css("display","block").addClass("login-margin");
    			    	  /*$(".user-name").attr("margin-top","15px");*/
    			    	  $(".user-button").removeClass("user-button").addClass("user-btn");
    			    	  $(".pwd").css("margin-top","15px");
    			    	  $(".verify-code").css("margin-top","15px");
    			    	  $(".user-name").css("margin-top","15px");
    			    	  $(".mobVerify-code").css("display","block").css("margin-top","15px");
    			    	  $(".codeLine").css("display","block");    			    	  
    			    	  $("#name").val('');
    			    	  $("#pwd").val('');
    			    	  $("#verifycode").val('');
    			    	  
    			    	  // 切换为企业用户
    			    	  $("input[name='type']").val('2');
    			    	  //显示企业用户的忘记密码
    			    	  $("#entAccount").show();
    			    	  $("#personAccount").hide();
    			    	});
    			    	
    			    	//点击个人用户按钮时用户按钮样式替换
    			    	$("#person-user").click(function(){
    			    		$("#person").show();
    			    		$("#ent").hide();
    			    	  $("#person-left").addClass("person-left");
    			    	  $("#person-left").removeClass("person-leftNoClick");
    			    	  $("#personCenter").addClass("personCenter");
    			    	  $("#personCenter").removeClass("person-center-noclick");
    			    	  $("#person-right").addClass("person-right");
    			    	  $("#person-right").removeClass("person-right-noclick");
    			    	
    			    	
    			    	  $("#ent-left").addClass("ent-left");
    			    	  $("#ent-left").removeClass("ent-left-click");
    			    	  $("#ent-center").addClass("ent-center");
    			    	  $("#ent-center").removeClass("ent-center-click");
    			    	  $("#ent-right").addClass("ent-right");
    			    	  $("#ent-right").removeClass("ent-right-click");    
    			    	  $(".mobile").css("display","none");
    			    	  $(".mobVerify-code").css("display","none");
    			    	  $(".codeLine").css("display","none");
    			    	  $(".pwd").css("margin-top","30px");
    			    	  $(".verify-code").css("margin-top","30px");
    			    	  $(".user-name").css("margin-top","30px");
    			    	  $("#name").val('');
    			    	  $("#pwd").val('');
    			    	  $("#mobCode").val('');
    			    	  $("#mob").val('');
    			    	  
    			    	  // 切换为个人用户
    			    	  $("input[name='type']").val('1');
    			    	  //显示个人用户的忘记密码
    			    	  $("#entAccount").hide();
    			    	  $("#personAccount").show();
    			    	});
    			    	
    			    	// <!--div标签1px自适应屏幕高度-->
    			    	total=document.documentElement.clientHeight;
    			    	rowHeight=(total-$('header').height()-$('footer').height()-$(".main").height())/2;
    			    	//topline和bottomline的自适应高度
    			    	document.getElementById("top-line").style.height=rowHeight+"px";
    			    	document.getElementById("bottom-line").style.height=rowHeight+"px";
    			    	
    			    	//util.exports('doLogin',login.doLogin);
    			  });
    			});
	    		    	
	    	
    	},
    	changeVercode:function(){
    		$("#verify-code").attr("src", "../../system/getVerifyCode.do?" + new Date());
    	},
    	/*
    	 * 个人账户登录
    	 */
    	doLogin : function (){
    		var type = $.trim($("input[name='type']").val());
    		var user = $.trim($("input[name='loginName']").val());
    		var passwrod = $.trim($("input[name='password']").val());
    		if(passwrod == "密码"){
    			passwrod = "";
    		}
    		var vercode = $("#verifycode").val();
    		
    		
    		if(!user){
    			jazz.error("用户名不能为空。");
    			login.changeVercode();
    			return ;
    		}
    		if(!passwrod){
    			jazz.error("密码不能为空。");
    			login.changeVercode();
    			return ;
    		}
    		if(!vercode){
    			jazz.error("验证码不能为空。");
    			login.changeVercode();
    			return ;
    		}
    		if(!type){
    			type = '1';
    		}
    		
    		var params = {
    			url : "../../security/auth/user/login.do",
    			params : {
    	            user: user,
    	            password: passwrod,
    	            vercode: vercode,
    	            type: type
    	        },
    			callback : function(data, r, res) {
    				var result = res.getAttr('result');
    				if(result=='success'){
    					// 登录成功
    					window.location.href = '../../../page/apply/person_account/home.html';
    				}else if(result == 'needCheck'){
    					jazz.confirm("您的身份证号码未通过校验，为了保证您后续能够顺利办理工商业务，请输入您本人的真实身份证号码！",function(){
    						var win = jazz.widget({
    							name : "checkCerNo",
    							vtype : 'window',
    							title : '身份证号验证',
    							titlealign : 'left',
    							titledisplay : true,
    							modal : true,
    							frameurl : 'check_cer_no.html',
    							height : 230,
    							width : 600
    						});
    						// 打开窗口
    						win.window('open');
    					},function(){});
    				}else{
    					login.changeVercode();
    					jazz.error("登录失败:"+result);
    				}
    			} 
    		};
    		
    		$.DataAdapter.submit(params);
    	},
    	/*
    	 * 企业账户登录
    	 */
    	entDoLogin : function (){
    		var user = $.trim($("#name").val());  //添加并去除前后空格
    		var passwrod = $.trim($("#pwd").val());
    		var mobile = $.trim($("#mob").val());
    		var vercode = $.trim($("#mobCode").val());
    		if(passwrod == "密码"){
    			passwrod = "";
    		}
    		if(!user){
    			jazz.error("用户名不能为空。");
    			return ;
    		}
    		if(!passwrod){
    			jazz.error("密码不能为空。");
    			return ;
    		}
    		if(!mobile){
    			jazz.error("移动电话不能为空。");
    			return ;
    		}
    		
    		if(!vercode){
    			jazz.error("验证码不能为空。");
    			return ;
    		}
    		var params = {
    			url : "../../../apply/entAccount/loginCheck.do",
    			params : {
    	            user: user,
    	            password: passwrod,
    	            vercode: vercode,
    	            mobile:mobile
    	        },
    			callback : function(data, r, res) {
    				var result = res.getAttr('result');
    				if(result=='success'){
    					// 登录成功
    					window.location.href = '../ent_account/home.html';//企业登陆后页面
    				}else{
    					jazz.error("登录失败");
    				}
    			} 
    		};
    		$.DataAdapter.submit(params);
    	},
    	/**
		 * 企业账户登录移动电话验证。验证通过发送短信
		 */
		checkMob:function(){
    		var passwrod = $.trim($("#pwd").val());
			var user = $.trim($("#name").val());
    		var mobile = $.trim($("#mob").val());
			if(!user){
				jazz.error("登录名不能为空。");
				return;
			}
			if(!mobile){
				jazz.error("移动电话不能为空。");
				return;
			}
			var param ={
					url: '../../../apply/entAccount/checkMob.do',
					params:{user:user,mobile:mobile,passwrod:passwrod},
					async:true,
					callback:function(data,param,res){
						var result = res.getAttr("result");
						if(result=="success"){
							$('#sendSmsVercodeBtn').removeAttr("disabled");
							login.sendVercode();//发送短信
							
						}else{
							$('#sendSmsVercodeBtn').attr("disabled","true");
							return;
						}
					}
			};
			$.DataAdapter.submit(param,this);
		},
		/**
		 * 发送手机验证码。
		 */
		sendVercode:function(){
			var mobile = $.trim($("#mob").val());
			if(!mobile){
				jazz.error("移动电话不能为空。");
				return;
			}
			var param ={
					url: '../../../common/sms/vercode/send.do',
					params:{mobile:mobile},
					async:true,
					callback:function(data,param,res){
						var result = res.getAttr("result");
						if(result=="success"){
							var i=60;
							$('#sendSmsVercodeBtn').attr("disabled","disabled");
							$('.sendSmsVercodeBtn').css("color","#333");
							$('#sendSmsVercodeBtn').text("重新发送("+i+")");
							jazz.info("短信发送成功。");
							var id=setInterval(function(){
								i--;
								$('#sendSmsVercodeBtn').text("重新发送("+i+")");
								if(i==0){
									clearInterval(id);
									$('#sendSmsVercodeBtn').removeAttr("disabled");
									$('#sendSmsVercodeBtn').text("发送验证码");
									$('.sendSmsVercodeBtn').css("color","#148AC0");
								}
							},1000
						/*	setTimeout(function(){
								
									
							   },*/
							  /* common.getSmsVercodeValidateTimeInSeconds()*1000*/
							
							);
						}else{
							jazz.error("获取短信验证码失败!");
							return;
						}
					}
			};
			$.DataAdapter.submit(param,this);
		}
    };
    login._init();
    return login;
});




