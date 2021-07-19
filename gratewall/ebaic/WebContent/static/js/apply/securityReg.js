define(['require','jquery', 'jazz', 'renderTemplate','util','common'], function(require,$, jazz, tpl,util,common){
	var reg = {
		_init : function(){
			var that = this;
			require(['domReady'], function (domReady) {
				domReady(function () {
					// 处理模板
			    /*	$("body").renderTemplate({templateName:'footer',insertType:'append'});
			    	$("body").renderTemplate({templateName:'header',insertType:'prepend'});*/
			    	//$(".wrap-header nav").css("display","none");
			    	$("[name='apply_security_reg_reset_button']").on('click', that.reset); 
			    	$("[name='apply_security_reg_query_button']").on('click', that.submit); 
			    	$("[name='apply_security_reg_back_button']").on('click', that.back); 
			    	

			    	util.exports('checkLoginName',that.checkLoginName);
			    	util.exports('checkMobile',that.checkMobile);

			    	$(document).keypress(function(e) {
						if (e.which == 13) {
							//回车查询
							$("#apply_security_reg_query_button").focus();
							$("#apply_security_reg_query_button").click();
						}
					});
				
					$("input[name='rePwd']").blur(that.checkRePwd);
					
					$('#mobilebtn').click(that.sendmobileCode);
				});//enf domReady
			});
		
		},
		
		
    	/**
    	 * 重置表单 
    	 */
    	reset : function (){  
    		var formId = 'userInfo_Form' ;
    		util.resetForm(formId);
    	} ,
    	/**
    	 * 提交表单 
    	 */
    	submit : function (){
    		var pass = $("#userInfo_Form").formpanel("validate");
    		if(pass){
	    		var params={		 
					 url:"../../../torch/service.do?fid=apply_security_reg",
					 components: ["userInfo_Form"],
					 callback: function(jsonData,param,res){
						 jazz.info("注册成功",function(){
							 window.location.href = "../../../page/apply/index.html";
						 });		
						
					 }
				};
				$.DataAdapter.submit(params);
    		}else{
    			jazz.warn('请填写正确的表单信息');
    		}
    	} ,
    	
    	back : function (){  
    		window.location.href = "../../../page/apply/index.html";
    	} ,
    	
    	
    	//检查用户名
    	checkLoginName : function (){
    		var result = {};
    		result["msg"]= "";
    		result["state"]=true;
    		var login_name = $("#login_name").textfield("getValue");
    		if(login_name==""){
    			result["msg"]= "登录名不能为空";
    			result["state"]=false;
//    			$("#check_result").removeClass("check-pass");
    		}else if(!/^[A-Za-z0-9]+$/.test(login_name)){
    			result["msg"]= "登录名只能是英文或数字";
    			result["state"]=false;
//    			$("#check_result").removeClass("check-pass");
    		}else if(login_name.length<4 || login_name.length>16){
    			result["msg"]= "登录名长度为4-16";
    			result["state"]=false;
//    			$("#check_result").removeClass("check-pass");
    		}else{
    			var login_name = $("#login_name").textfield("getValue");
    			$.ajax({
    				url : '../../../security/auth/regist/checkUser.do',
    				async: false,
    				data : {
    					loginName : login_name
    				},
    				success : function(isExist,state) {
    					var isHas=isExist;
    					if (isHas == "has") {
    						result["msg"]= "该用户名已存在，请重新输入！";
    						result["state"]=false;
//    						$("#check_result").removeClass("check-pass");
    					} else {
//    						$("#check_result").addClass("check-pass");
    					}
    				}
    			});
    		}
    		return result;	
    	},
    	
    	
    	/**
    	 * 检查用户移动电话是否被注册过
    	 * yzh
    	 */
    	checkMobile : function(){
    		var result = {};
    		result["msg"] = "";
    		result["state"] = true;
    		var mobile = $("#mobile").textfield("getValue");
    		if(!/^0?1\d{10}$/.test(mobile)){
    			result["msg"]= "请输入正确的移动号码！";
				result["state"]=false;
    		}

    		$.ajax({
    			url : '../../../security/auth/regist/checkMobile.do?mobile='+mobile,
    			async : false,
    			success : function(isExist,state){
    				var isHas = isExist;
					if (isHas == "has") {
						result["msg"]= "该移动号码已经被注册！";
						result["state"]=false;
						$("#mobilebtn").attr("disabled","disabled");
						$('#mobilebtn').css("cursor","default");
			    		$('#mobilebtn').css("color","#BBB");
					}else{
						$('#mobilebtn').removeAttr("disabled");
						$('#mobilebtn').css("cursor","pointer");
	    				$('#mobilebtn').css("color","#666;");
					}
    			}
    		});
    		return result;
    	},
    	
    	sendmobileCode : function (){

			var mobile = $('#mobile').textfield('getValue');
			var re = /^0?1\d{10}$/;
			if(mobile == ""){ 
				jazz.warn("请输入移动电话！");
				return;
			}else if(!(re.test(mobile))){
				jazz.warn("请输入合法的移动电话！");
				return;
			}
    		var params={		 
    				 url:'../../../common/sms/vercode/send.do?mobile='+mobile,
    				 callback: function(jsonData,param,res){
    					 jazz.info("发送成功");
    					 reg.setTimeButton();
    				 }
    			};
    		$.DataAdapter.submit(params);
	    		
    	},
    	
    	setTimeButton : function(){
    		var i=60;
    		$("#mobilebtn").attr("disabled","disabled");
    		$("#mobilebtn").val("重新发送("+i+")");
    		$('#mobilebtn').css("cursor","default");
    		$('#mobilebtn').css("color","#BBB");

    		var id = setInterval(function(){
    			i--;
    			$("#mobilebtn").val("重新发送("+i+")");
    			if(i==0){
    				clearInterval(id);
    				$('#mobilebtn').removeAttr("disabled");
    				$("#mobilebtn").val("获取验证码");
    				$('#mobilebtn').css("cursor","pointer");
    				$('#mobilebtn').css("color","#666;");
    				$('#mobilebtn').css("background","#F7F7F7;");
    			}
    		},1000);
    	}
    	
	};
	reg._init();
	return reg;
});


//
//	/*用户注册*/
////	function regist() {
////		// 检查登录名中是否有空格
////		var loginName = $('#login_name').textfield('getValue');
////		if($.trim(loginName) != loginName){
////			jazz.info('您输入的登录名中存在空格，请修正后再提交。');
////			return ;
////		}
////		
////		
////		$('#cardType').hiddenfield('setValue','1');
////		$('#country_city').hiddenfield('setValue','156');
////		var params = {
////			url : '../../system/regist.do',
////			components : [ 'user_info' ],
////			callback : function(data,obj,res) {
////				
////				var result = res.getAttr('result');
////				/*if(result=='5'){
////					jazz.info("请输入真实有效的姓名和身份证号码！");
////				}else */
////				if (result=='1'){
////					jazz.info("注册成功,请重新登录！",function(){
////						window.location='../../index.html';
////						});
////				}else{
////					jazz.info("保存失败，请联系工作人员！");
////				}
////				/*window.open("registSuccess.html","_blank","'left=300px,top=150px,width=800, height=480,toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, copyhistory=no'");
////				setTimeout(function(){
////					window.location.href='../../index.html';
////				}, 3000);*/
////				
////			}
////		};
////		jazz.confirm("请确认您的姓名和身份证号一致且真实有效，如果不一致会导致无法登录系统，并且无法找回账户，只能持本人身份证到工商局现场解锁！",function(){
////			$.DataAdapter.submit(params);
////			focusError();
////		},function(){
////				
////		});
////	};
//
//	//根据证件类型添加规则
////	function checkCardType(){
////		var cardType = $("#cardType").comboxfield("getValue");
////		if(cardType=="1"){
////			$("#id_card").textfield("option","rule","must_idcard_length;0;40");
////		}else{
////			$("#id_card").textfield("option","rule","must_length;0;40");
////		}
////	}
//	

	function checkRePwd(){
		var result = {};
		result["msg"]= "";
		result["state"]=true;
		var user_pwd = $("#user_pwd").passwordfield("getValue");
		var rePwd = $("#rePwd").passwordfield("getValue");
		if(user_pwd != rePwd){
			result["msg"]= "您输入的密码不一致，请重新输入！";
			result["state"]=false;
		}

		return result;
	}
//	
////	function reset() {
////		$("#user_info").formpanel('reset');
////		$("#check_result").text("");
////	}
//	
//	
//	
////	function backIndex(){
////		window.location.href="../../page/system/main.html";
////	} 
//	//提交验证出错,光标移到第一个出错位置
////	function focusError(){
////		$(".jazz_validator_border").each(function(){
////			$(this).find(".jazz-field-comp-input").focus();
////			return false;
////		});
////	}
