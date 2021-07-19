define(['require','jquery','common','util','jazz'], function(require, $,common,util,jazz){
	var sendsms = {
		_init : function(){
			var $_this = this;
			require(['domReady'], function (domReady) {
				domReady(function () {
					// 根据模板处理页面样式
					//common.computeHeight();
					//获取用户移动电话
					$_this.getUserMobile();
					//绑定页面方法
					$_this.bindingClick();
					$_this.changeStyle();
					
				});
			});
		},
		//样式调整
		changeStyle:function(){
			$("div[name='smsform']").css("margin-top","-20px");
			/*$("div[name='smsform']").css("width","100%");*/
		},
		//上一页跳转
		backStep:function(){
			util.closeWindow('tipPage');
		},
		/**
		 * 绑定页面方法
		 */
		bindingClick:function(){
			/**
			 * 绑定提交方法 
			 */
			$("#save_button").on('click').on('click',sendsms.smsSubmit);
			
			//绑定发送验证码按钮
			$("#mobilebtn").on('click',sendsms.sendmobileCode);	
			$('#close_button').on('click',sendsms.closeDom);
			
		},
		closeDom:function(){
			util.closeWindow('confirmSMS');
		},
		/**
		 * 提交
		 */
		smsSubmit:function(){
			var gid = jazz.util.getParameter('gid');
			var mobileCode = $('#mobileCode').textfield('getValue');
			var isLe = jazz.util.getParameter('isLe');
			var url='../../../apply/setup/submit/cpSubmit.do';
			if(isLe){
				url='../../../apply/setup/submit/leRepSubmit.do';
			}
			if(!mobileCode){
				jazz.error("请输入验证码！");
			}else{
				var params = {
			        url : url,
			        params:{
			        	gid:gid,
			        	mobileCode:mobileCode
			        },
			        async:false,
			        callback : function(data, param, res) {
			        	var msg = res.getAttr('msg');
						jazz.info(msg,function(){
							 parent.location.href="../../../page/apply/person_account/home.html";
							 util.closeWindow('confirmSMS');
						});
			        }
			    };  	
				$.DataAdapter.submit(params);
			}
			
		},
		
		getUserMobile:function(){
			$.ajax({
				url:"../../../security/auth/info/getUserInfo.do",
				type:"post",
				success:function(data){
					if(data){
						data = $.parseJSON(data);
						var mobile = data.data[0].data.mobile;
						$('#mobile').textfield('setValue',mobile);
					}
					
				},
				error:function(data){
					jazz.error(data);
				}
			});
		},
		sendmobileCode : function (){
			
    		var params={		 
    				 url:'../../../common/sms/vercode/sendToUser.do',
    				 callback: function(jsonData,param,res){
    					 //jazz.info("发送成功");
    					 sendsms.setTimeButton();
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
	sendsms._init();
	return sendsms;
});


