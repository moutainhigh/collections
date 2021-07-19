define(['require', 'jquery', 'common'], function(require, $, common){
var torch = {
	
	_init : function(){

		 require(['domReady'], function (domReady) {
		    domReady(function () {
			 	torch.getUserInfo();
		    	$("#mobilebtn").on('click',torch.sendmobileCode);
		    	$("#codebtn").on('click',torch.checkMobileCode);
				$("#returnBtn").on('click',torch.returnOption);
			 });
		});
	},
		
	edit_primaryValues : function(){
//	    return "&userId=" + jazz.util.getParameter('userId')
	    //return "&userId=" + $("#userId").hiddenfield('getValue');
	},
	edit_fromNames : [ 'changeRegInfoSave_Form'],

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
			//var userId = data.data.userId;
			var mobile = data.data.mobile;
		}else{
			jazz.error("获取信息异常！");
			return;
		}
		$("#mobile").textfield('setValue',mobile);
		//$("#userId").hiddenfield('setValue',userId);
//		$("#mobileCode").textfield('option','rule','must');
		
	},
	
	checkMobileCode : function(){
		var mobileCode = $("#mobileCode").textfield('getValue');
		if(mobileCode == ""){
			jazz.error("手机验证码不能为空！");
			return;
		}
		var params={		 
				 url:'../../../security/auth/info/checkMobile.do?mobileCode='+mobileCode,
				 callback: function(jsonData,param,res){
					 var url = res.getAttr("url");
					 if(url.startsWith("../")){
						window.location.href = url;
					 }else{
						jazz.error("校验不通过，请重新校验。");
					 }
				 }
			};
		$.DataAdapter.submit(params);
	},
	
	sendmobileCode : function (){
		var params = {		 
			 url:'../../../security/auth/info/sendVerCode.do',
			 callback: function(jsonData,param,res){
				 jazz.info("发送成功。");
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
	}
	 
};
torch._init();
return torch;

});
