define(['require','jquery', 'jazz', 'renderTemplate','util'], function(require,$, jazz, tpl,util){
	var reg = {
		_init : function(){
			var that = this;
			require(['domReady'], function (domReady) {
				domReady(function () {
					// 处理模板
			    	$("body").renderTemplate({templateName:'footer',insertType:'append'});
			    	/*$("[name='apply_security_reg_reset_button']").on('click', that.reset); 
			    	$("[name='apply_security_reg_query_button']").on('click', that.submit); 
			    	$("[name='apply_security_reg_back_button']").on('click', that.back); */
			    	$("[name='apply_security_reg_submit_button']").on('click', that.submit);//提交认证
			    	
			    	$("[name='apply_security_reg_back_button']").on('click', that.back);//返回
			    	

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
    		var formId = 'mobileInfoAdd_Form' ;
    		util.resetForm(formId);
    	} ,
    	/**
    	 * 提交表单 
    	 */
    	submit : function (){
    		var pass = $("#mobileInfoAdd_Form").formpanel("validate");
    		if(pass){
	    		var params={		 
					 url:"../../../torch/service.do?fid=apply_security_reg",
					 components: ["mobileInfoAdd_Form"],
					 callback: function(jsonData,param,res){
						 alert("注册成功");		
						 window.location.href = "../../../page/apply/index.html";
					 }
				};
				$.DataAdapter.submit(params);
    		}else{
    			jazz.warn('请填写正确的表单信息');
    		}
    	} ,
    	
    	back : function (){  
    		window.location.href = "../../../page/apply/ent_account/home.html";
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


