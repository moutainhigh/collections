//待添加信息模板信息
define(['require','jquery', 'jazz','util','entCommon'], function(require,$, jazz,util,entCommon){
	var entAuth = {
		/**
		 * 初始化
		 */
		_init : function(){
			var $_this = this;
			
			require(['domReady'], function (domReady) {
				domReady(function () {
//					entAuth.countDownTimer(30);
					$("#countDownTimer").text("00:00:00");
//					$("#authEntName").text($("#entName").attr("title"));
					//获取认证服务码
					$("#getServerCode").on("click",entAuth.getServerCode);
					//获取认证结果
					$("#getEntAuthResult").on("click",entAuth.getEntAuthResult);
					//$("#testImage").on("click",entAuth.testImage);
				});
			});
		},
		/**
		 * 分秒倒计时
		 *  mm 分钟
		 */
		countDownTimer : function(mm){
//			var mm = 1;
			var ss = 0;
			setInterval(function(){
				var time = "00:00:00";
				if(ss>=0&&ss<10){
					if(mm>=0&&mm<10){
						time = "00:0"+mm+":0"+ss;
					}else{
						time = "00:"+mm+":0"+ss;
					}
				}else{
					if(mm>=0&&mm<10){
						time = "00:0"+mm+":"+ss;
					}else{
						time = "00:"+mm+":"+ss;
					}
				}
//				var time = "00:"+mm+":"+ss;
				if(ss<=0){
					mm=mm-1;
					ss=60;
				}
				if(mm<0){
					$("#countDownTimer").text("00:00:00");
					return;
				}
				$("#countDownTimer").text(time);
				ss=ss-1;
			},1000);
		},
		/**
		 * 获取认证结果
		 */
		getEntAuthResult : function(){
			$("#showPic").hide();
			var serverCode = $("#serverCode").val();
			var authCode = $("#authCode").val();
			if(!serverCode){
				jazz.error("认证服务码不能为空。");
				return;
			}
			if(!authCode){
				jazz.error("认证码不能为空。");
				return;
			}
			var params = {
		        url : "../../../apply/entAuth/checkEntAuth.do",
		        params:{ serverCode : serverCode  , authCode : authCode },
		        async:false,
		        callback : function(data, param, res) {
		        	var result = res.getAttr('result');
		        	var flag = result.authFlag;
		        	$("#authEntName").text(result.authEntName);
		        	if(flag=="0"){
		        		jazz.info("认证成功");
		        		$("#showPic").show();
		        	}else if(flag=="2"){
		        		jazz.info("认证超时，请重新认证");
		        	}else{
		        		jazz.info("认证失败");
		        	}
		        } 	
		    };  	
    		$.DataAdapter.submit(params);
		},
		/**
		 * 获取认证结果
		 */
		getServerCode : function(){
			var entName = $("#authEntName").val();
			var regNo = $("#regNo").val();
			if(!entName){
				jazz.error("企业名称不能为空。");
				return;
			}
			if(!regNo){
				jazz.error("企业注册号或统一社会信用代码不能为空。");
				return;
			}
			var params = {
		        url : "../../../apply/entAuth/getServerCode.do",
		        params:{ entName : entName  , regNo : regNo },
		        async:false,
		        callback : function(data, param, res) {
		        	var authMap = res.getAttr('authMap');
		        	//$("#result").show();
		        	$("#serverCode").val(authMap.serverCode);
		        	$("#authTime").val(authMap.startTime);
		        	//30分钟倒计时
		        	entAuth.countDownTimer(30);
		        } 	
		    };  	
    		$.DataAdapter.submit(params);
		}
	}
	entAuth._init();
	return entAuth;
});