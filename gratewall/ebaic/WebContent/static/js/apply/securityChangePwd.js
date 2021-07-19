define(['require','jquery','util','common','validator'], function(require,$,util,common,validator){
	var torch = {
		_init : function(){
			var that = this;
			require(['domReady'], function (domReady) {
				domReady(function () {
					/*// 处理模板
					$("body").renderTemplate({templateName:'header',insertType:'prepend'});*/
			    /*	$("body").renderTemplate({templateName:'footer',insertType:'append'});*/

			    	torch.getUserInfo();
			    	$("#updateBtn").on('click',torch.updatePassword);
			    	$("#mobilebtn").on('click',torch.sendmobileCode);
			    	$("#returnBtn").on('click',torch.returnOption);

				});//enf domReady
			});
		
		},
		
		getUserInfo : function(){
			
			var param ={
					url:'../../../security/auth/info/getUserInfo.do',
					params:{},
					async:true,
					callback:function(data,param,res){
						
						torch.initInfo(data);
					}
			};
			$.DataAdapter.submit(param,this);
			
		},
		
		initInfo : function(data){
			if(data && data.data){
				var mobile = data.data.mobile;
				var loginName = data.data.loginName;
				var mobCheckState = data.data.mobCheckState;
			}else{
				jazz.error("获取信息异常！");
				return;
			}
			
			if (mobCheckState == "1") {//手机通过验证
				$("#mobileCode").textfield('option','rule','must');
				$("#mobileCode").css("display","block");
				$("#mobilebtn").css("display","block");
				$("#mobile").css("display","block");
			} else {
			    $("#mobileCode").textfield('option','rule','');
			    $("#mobileCode").css("display","none");
				$("#mobilebtn").css("display","none");
			}
			
			if(mobile == null || mobile == "" || loginName == null || loginName == ""){
				jazz.error("获取信息异常，请联系管理员！");
			}
			$("#mobile").textfield('setValue',mobile);
			$("#loginName").textfield('setValue',loginName);
		},
		
		updatePassword : function(){
			var userPwdOld = $('#user_pwd_old').passwordfield("getValue");
			var userPwd = $('#user_pwd').passwordfield("getValue");
			var re_pwd = $('#re_pwd').passwordfield("getValue");
			var cerNo = $('#cerNo').textfield("getValue");
			
			if(userPwdOld==""){
				jazz.error("原密码不能为空!");
				return;
			}
			if(userPwd==""){
				jazz.error("新密码不能为空!");
				return;
			}
			
			if(userPwd!=re_pwd){
				jazz.error("新密码与确认密码不一致!");
				return;
			}
			
			if(cerNo == ""){
				jazz.error("证件号码不能为空!");
				return;
			}
			var dataUrl ='../../../security/auth/info/updatePassword.do';
			var param ={
					url:dataUrl,
					components:['user_info'],
					params:{},
					async:true,
					callback:function(data,param,res){
						var result = res.getAttr("result");
						
						if(result=="0"){
							jazz.info("原密码填写不正确！");
						}else if (result=="1"){
							jazz.info("密码修改成功!",function(){
								window.parent.location='../../../page/apply/security/index.html';
							});
						}else{
							jazz.info("密码修改失败!");
						}
					}
			};
			$.DataAdapter.submit(param,this);
		},
		
		sendmobileCode : function (){

			/*var mobile = $('#mobile').hiddenfield('getValue');
			if(!mobile){
				jazz.error("获取信息有误，请联系管理员！");
				return;
			}
			*/
			var result = torch.checkCerNo();
			if(result == "2"){
				jazz.warn("请输入身份证号码！");
				return;
			}
			if(result == "0"){
				jazz.warn("您输入的身份证号码与库中的不匹配，请重新输入本人身份证号码！");
				return;
			}
			
    		var params={		 
    				 url:'../../../common/sms/vercode/sendToUser.do',
    				 callback: function(jsonData,param,res){
    					 jazz.info("发送成功");
    					 torch.setTimeButton();
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
    	},
    	
    	returnOption : function(){
    		window.location.href = "index.html";
    	},
    	
    	checkCerNo : function(){
			var res;
			var cerNo = $("#cerNo").textfield('getValue');
			if(!cerNo){
				return "2";//cerNo为空
			}
			$.ajax({
				url : '../../../security/auth/info/valideCerNo.do',
				async: false,
				data : {
					cerNo : cerNo
				},
				success : function(data) {
					res = data;//1代表匹配正确，0则相反
				}
			});
			return res;
		}
    	
	};
	torch._init();
	return torch;
});


