define(['require','jquery','util','common'], function(require,$,util,common){
	var torch = {
		_init : function(){
			var that = this;
			require(['domReady'], function (domReady) {
				domReady(function () {
					
					// 处理模板
			    	torch.getUserInfo();
			    	$("#mobileOldBtn").on('click',torch.sendCurrentUserMobile);
			    	$("#mobileNewBtn").on('click',function(){torch.sendmobileCode('mobileNew')});
			    	$("#updateBtn").on('click',torch.saveInfo);
			    	$("#returnBtn").on('click',torch.returnOption);
			    	$(".jazz-formpanel-content").css("background-color","#fff");
			    	
			    	//$("#mobileCodeOld").off('click').on('click',torch.checkMobileOld);

				});//enf domReady
			});
		
		},
		
		getUserInfo : function(){
			
			var params ={
					url:'../../../security/auth/info/getUserInfo.do',
					params:{},
					async:true,
					callback:function(data,param,res){
						torch.initInfo(data);
					}
			};
			$.DataAdapter.submit(params);
			
		},
		
		initInfo : function(data){
			if(data && data.data){
				var mobCheckState = data.data.mobCheckState;
				var mobile = data.data.mobile;
			}else{
				jazz.error("获取信息异常！");
				return;
			}
			
			$("#mobCheckState").hiddenfield('setValue',mobCheckState);
			$("#displayMobile").hiddenfield('setValue',mobile);
			var subMobile = mobile.substring(0,3).concat("****").concat(mobile.substring(7));
			$("#displayMobileOn").textfield('setValue',subMobile);
			if(mobCheckState == "1"){//手机通过校验
				//$("#mobileOld").show();
				$("#mobileCodeOld").show();
				$("#mobileOldBtn").show();
				//$("#mobileOld").textfield('option','rule','must_cellphone');
				$("#mobileCodeOld").textfield('option','rule','must');
				$("#mobileTip").show();
				$("#checkTip").append("(已认证)");
				$("#displayMobileOn").show();
				
			}else{
				//$("#mobileOld").hide();
				$("#mobileCodeOld").hide();
				$("#mobileOldBtn").hide();
				$("#mobileOld").textfield('option','rule','');
				$("#mobileCodeOld").textfield('option','rule','');
				$("#mobileTip").hide();
				$("#checkTip").append("(未认证)");
				$("#displayMobileOn").hide();
				$("#mobileNew").textfield('option','label','移动电话码');
				$("#mobileNew").textfield('setValue',mobile);
			}
			
			
		},
		
		saveInfo : function(){
			torch.checkInfo();
			
			var params={		 
					 url:'../../../security/auth/info/validateMobile.do',
					 components: [ 'mobile_info'],
					 callback: function(jsonData,param,res){
						 var result = jsonData.data;
						 if(result == "1"){
							 jazz.util.info("操作成功！", function(){  
								 window.location.href = "./index.html";
							 });
							 
						 }else{
							 jazz.error("操作失败，请重新操作！");
							 return;
						 }
					 }
				};
			$.DataAdapter.submit(params);
		},
		
		//表单信息校验
		checkInfo : function(){
			
			
			var mobileNew = $("#mobileNew").textfield("getValue");
			var mobileCodeNew = $("#mobileCodeNew").textfield("getValue");
			//var mobileOld = $("#mobileOld").textfield("getValue");
			var mobileCodeOld = $("#mobileCodeOld").textfield("getValue");
			var cerNo = $("#cerNo").textfield("getValue");
			
			var mobCheckState = $("#mobCheckState").hiddenfield('getValue');
			if(mobCheckState == "1"){
				if(mobileCodeOld ==""){
					jazz.error("请您填写更换认证移动电话信息！");
					return;
				}
			}
			if(mobileNew == "" || mobileCodeNew ==""){
				jazz.error("请您填写更换认证移动电话信息！");
				return;
			}
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
		},
		
		sendmobileCode : function (id){
			
			var mobile;
				mobile = $("#"+id).textfield('getValue');
			if(mobile == ""){
				jazz.error("移动电话码为空，请填写移动电话码！");
				return;
			}
			var re = /^0?1\d{10}$/;
			if(!(re.test(mobile))){
				jazz.warn("请输入合法的移动电话！");
				return;
			}
			
			//var mobileOld = $("#mobileOld").textfield('getValue');
			//var mobileApproved = $("#displayMobileOn").textfield('getValue');
//			alert(mobileApproved);
//			alert(mobileOld);
//			if(mobileOld != mobileApproved){
//				jazz.warn("已验证的移动号码和输入的原移动号码不匹配！");
//				return;
//			}
			
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
					 url:'../../../common/sms/vercode/send.do?mobile='+mobile,
					 callback: function(jsonData,param,res){
						 jazz.info("发送成功");
						 torch.setTimeButton(id);
					 }
				};
			$.DataAdapter.submit(params);
	    		
		},
		
		
		//给当前用户的验证移动电话发送电话号
		sendCurrentUserMobile : function(){
			var params={		 
					 url:'../../../security/auth/info/sendVerCode.do',
					 callback: function(jsonData,param,res){
						 jazz.info("发送成功");
						 torch.setTimeButton("mobileOldBtn");
					 }
				};
			$.DataAdapter.submit(params);
		},
		
		
		setTimeButton : function(id){

//			if(id == "mobileOld"){
//				id = "mobileOldBtn";
//			}
			if(id == "mobileNew"){
				id = "mobileNewBtn";
			}
			
			var i=60;
			$("#"+id).attr("disabled","disabled");
			$("#"+id).val("重新发送("+i+")");
			$("#"+id).css("cursor","default");
			$("#"+id).css("color","#BBB");

			var fid = setInterval(function(){
				i--;
				$("#"+id).val("重新发送("+i+")");
				if(i==0){
					clearInterval(fid);
					$("#"+id).removeAttr("disabled");
					$("#"+id).val("获取验证码");
					$("#"+id).css("cursor","pointer");
					$("#"+id).css("color","#666;");
					$("#"+id).css("background","#F7F7F7;");
				}
			},1000);
			
		},
		
		returnOption : function(){
			window.location.href = "index.html";
		}
		
		
//		//检验原移动号码是否与认证的电话号码是否一致
//		checkMobileOld : function(){
//			var mobileOld = $("#mobileOld").textfield('getValue');
//			//var mobileApproved = $("#displayMobileOn").textfield('getValue');
//			alert(mobileApproved);
//			alert(mobileOld);
//		}
	};
	torch._init();
	return torch;
});


