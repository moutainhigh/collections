/*验证企业是否合法的JS*/
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
			    	$("[name='apply_security_reg_reset_button']").on('click', that.reset); 
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
					 //url:"../../../torch/service.do?fid=apply_security_reg",//跳转到公商接口
					 components: ["userInfo_Form"],
					 callback: function(jsonData,param,res){
						 //如果公商中返回对应企业的json信息，则跳转到网登的企业认证service服务接口
						 //window.location.href = "../../../page/apply/index.html";
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
    				//申请发送短信
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
