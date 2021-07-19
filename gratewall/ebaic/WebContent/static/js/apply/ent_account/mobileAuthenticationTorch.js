define(['require','jquery', 'jazz', 'renderTemplate','util'], function(require,$, jazz, tpl,util){
	var reg = {
		_init : function(){
			var that = this;
			require(['domReady'], function (domReady) {
				domReady(function () {
					// 处理模板
			    	$("body").renderTemplate({templateName:'footer',insertType:'append'});
			    	//$("body").renderTemplate({templateName:'header',insertType:'prepend'});
			    	//$(".wrap-header nav").css("display","none");
			    	$("[name='apply_security_reg_query_button']").on('click', that.submit); 
			    	$("[name='apply_security_reg_back_button']").on('click', that.back); 
			    	util.exports('checkLoginName',that.checkLoginName);
			    	$(document).keypress(function(e) {
						if (e.which == 13) {
							//回车查询
							$("#apply_security_reg_query_button").focus();
							$("#apply_security_reg_query_button").click();
						}
					});
				
					$("input[name='rePwd']").blur(that.checkRePwd);
					
					
					//绑定按钮事件
					$('#oldMobile').click(that.sendmobileCode);
					
					$('#newMobile').click(that.sendNewMobileCode);
					//$('#newMobile').click(that.sendmobileCode);
				});//enf domReady
			});
		
		},
		
		
    	/**
    	 * 重置表单 
    	 */
    	reset : function (){  
    		var formId = 'mobileInfo_Form' ;
    		util.resetForm(formId);
    	} ,
    	/**
    	 * ajax提交表单 
    	 */
    	submit : function (){
    		var pass = $("#mobileInfo_Form").formpanel("validate");
    		if(pass){
	    		var params={		 
					 url:"../../../torch/service.do?fid=apply_security_reg",
					 components: ["mobileInfo_Form"],
					 callback: function(jsonData,param,res){
						 alert("更改移动电话码成功");		
						 window.location.href = "../../../page/apply/index.html";
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
    	
    	/*原移动电话码*/
    	sendmobileCode : function (){
			var mobile = $('#old_Mobile').textfield('getValue');
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
    					 reg.setTimeButton();//绑定验证码发送倒计时
    				 }
    			};
    			$.DataAdapter.submit(params);
	    		
    	},
    	
    	setTimeButton : function(){
    		var i=60;
    		$("#oldMobile").attr("disabled","disabled");
    		$("#oldMobile").val("重新发送("+i+")");
    		$('#oldMobile').css("cursor","default");
    		$('#oldMobile').css("color","#BBB");

    		var id = setInterval(function(){
    			i--;
    			$("#oldMobile").val("重新发送("+i+")");
    			if(i==0){
    				clearInterval(id);
    				$('#oldMobile').removeAttr("disabled");
    				$("#oldMobile").val("获取验证码");
    				$('#oldMobile').css("cursor","pointer");
    				$('#oldMobile').css("color","#666;");
    				$('#oldMobile').css("background","#F7F7F7;");
    			}
    		},1000);
    	},
    	
    	/*新移动电话码*/
    	sendNewMobileCode : function (){
			var mobile = $('#new_Mobile').textfield('getValue');
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
    					 reg.setNewMobileTimeButton();//绑定验证码发送倒计时
    				 }
    			};
    		$.DataAdapter.submit(params);
	    		
    	},
    	
    	setNewMobileTimeButton : function(){
    		var i=60;
    		$("#newMobileCode").attr("disabled","disabled");
    		$("#newMobile").val("重新发送("+i+")");
    		$('#newMobile').css("cursor","default");
    		$('#newMobile').css("color","#BBB");

    		var id = setInterval(function(){
    			i--;
    			$("#newMobile").val("重新发送("+i+")");
    			if(i==0){
    				clearInterval(id);
    				$('#newMobile').removeAttr("disabled");
    				$("#newMobile").val("获取验证码");
    				$('#newMobile').css("cursor","pointer");
    				$('#newMobile').css("color","#666;");
    				$('#newMobile').css("background","#F7F7F7;");
    			}
    		},1000);
    	}
    	
	};
	reg._init();
	return reg;
});


