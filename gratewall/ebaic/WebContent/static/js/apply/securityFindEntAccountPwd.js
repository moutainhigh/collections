define(['require','jquery', 'jazz', 'renderTemplate','util'], function(require,$, jazz, tpl,util){
	
	var torch = {
		_init : function(){
			var that = this;
			require(['domReady'], function (domReady) {
				domReady(function () {
					if($("header").length == 0){
						  $("body").renderTemplate({templateName:'footer',insertType:'append'});
			        	  $("body").renderTemplate({templateName:'header',insertType:'prepend'});
			        	  //common.getCurrentUser();
					}
					// 绑定事件
					torch.bindEvents();
					//common.computeHeight();
					
				});//enf domReady
			});
		},
		bindEvents : function(){
			//1.发送手机验证码
			$('#sendSmsVercodeBtn').live('click',torch.sendVercode);
			//2.提交保存
			$('#updateBtn').live('click',torch.save);
			//3.返回按钮
			$('#backBtn').live('click',torch.backLogin);
			//4.校验俩次输入的密码
			$("div[name='re_pwd']").blur(torch.checkRePwd);
			//5.校验企业信息
			$("#checkEntAccount").live('click',torch.checkEntAccount);
		},
		/**
		 * 验证企业信息
		 */
		checkEntAccount:function(){
			var entName = $('#entAccName').textfield('getValue');
			var regNo = $('#regNo').textfield('getValue');
			var legName = $('#legName').textfield('getValue');
			var regCerNo = $('#regCerNo').textfield('getValue');
			if(!entName){
				jazz.error("企业名称不能为空。");
				return;
			}
			if(!regNo){
				jazz.error("注册号或统一社会信用代码不能为空。");
				return;
			}
			if(!legName){
				jazz.error("法定代表人姓名不能为空。");
				return;
			}
			if(!regCerNo){
				jazz.error("法定代表人身份证号不能为空。");
				return;
			}
			var param ={
					url: '../../../security/auth/findEntAccountPwd/checkEntAccount.do',
					params:{entName:entName,legName:legName,regCerNo:regCerNo,regNo:regNo},
					async:true,
					callback:function(data,param,res){
						var mobile = res.getAttr("mobile");
						$("#hideMobile").val(mobile);
						var m = mobile.split("");
						if(m.length>=11){
							m[3]="*";
							m[4]="*";
							m[5]="*";
							m[6]="*";
						}
						mobile = m.join("");
						$("#mobile").textfield('setValue',mobile);
						jazz.info("企业身份校验成功。");
					}
			};
			$.DataAdapter.submit(param,this);
		},
		/**
		 * 返回
		 */
		backLogin:function(){
			window.location.href='/';
		},
		/**
		 * 发送手机验证码。
		 */
		sendVercode : function (){
			var mobile = $('#hideMobile').val();
//			var mobile = $('#mobile').textfield('getValue');
			var re = /^0?1\d{10}$/;
			if(mobile == ""){ 
				jazz.warn("移动电话不存在！");
				return;
			}else if(!(re.test(mobile))){
				jazz.warn("移动电话格式错误！");
				return;
			}
    		var params={		 
    				 url:'../../../common/sms/vercode/send.do?mobile='+mobile,
    				 callback: function(jsonData,param,res){
    					 jazz.info("发送成功");
    					 torch.setTimeButton();
    				 }
    			};
    		$.DataAdapter.submit(params);
	    		
    	},
    	
    	setTimeButton : function(){
    		var i=60;
    		$("#sendSmsVercodeBtn").attr("disabled","disabled");
    		$("#sendSmsVercodeBtn").val("重新发送("+i+")");
    		$('#sendSmsVercodeBtn').css("cursor","default");
    		$('#sendSmsVercodeBtn').css("color","#BBB");

    		var id = setInterval(function(){
    			i--;
    			$("#sendSmsVercodeBtn").val("重新发送("+i+")");
    			if(i==0){
    				clearInterval(id);
    				$('#sendSmsVercodeBtn').removeAttr("disabled");
    				$("#sendSmsVercodeBtn").val("获取验证码");
    				$('#sendSmsVercodeBtn').css("cursor","pointer");
    				$('#sendSmsVercodeBtn').css("color","#666;");
    				$('#sendSmsVercodeBtn').css("background","#F7F7F7;");
    			}
    		},1000);
    	},
		/**
		 * 提交保存新密码。
		 */
		save:function(){
			var entAccName = $('#entAccName').textfield('getValue');
			var mobile = $('#hideMobile').val();
//			var mobile = $("#mobile").textfield('getValue');
			var pwd = $('#user_pwd').passwordfield('getValue');
			var repwd = $('#re_pwd').passwordfield('getValue');
			var verCode = $('#mobileCode').textfield('getValue');
			if(!entAccName){
				jazz.error("企业名称不能为空。");
				return;
			}
			if(!pwd){
				jazz.error("新密码不能为空。");
				return;
			}
			if(!repwd){
				jazz.error("确认密码不能为空。");
				return;
			}
			if(!mobile){
				jazz.error("移动电话不能为空。");
				return;
			}
			if(!verCode){
				jazz.error("验证码不能为空。");
				return;
			}
			
			if(repwd!=pwd){
				//jazz.error("两次密码不一致。");
				return;
			}
			var param ={
					url: '../../../security/auth/findEntAccountPwd/save.do',
					params:{entAccName:entAccName,mobile:mobile,pwd:pwd,verCode:verCode},
					async:true,
					callback:function(data,param,res){
						var result = res.getAttr("result");
						if(result=="success"){
							jazz.info("保存成功，请返回首页重新登录。",function(){
								window.parent.location='../../../page/apply/index.html';
							});
						}else{
							jazz.error("保存失败，请刷新页面后重试。");
							return;
						}
					}
			};
			$.DataAdapter.submit(param,this);
			
		}// end of function save
	};
	torch._init();
	return torch;
});
function checkRePwd(){
	var result = {};
	result["msg"]= "";
	result["state"]=true;
	var user_pwd = $("#user_pwd").passwordfield("getValue");
	var rePwd = $("#re_pwd").passwordfield("getValue");
	if(user_pwd != rePwd){
		result["msg"]= "您输入的密码不一致，请重新输入！";
		result["state"]=false;
	}

	return result;
}

