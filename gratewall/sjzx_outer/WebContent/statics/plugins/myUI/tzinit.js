$(function(){
		$("#clearUserInputs").click(function(){
			clearUserInputs();
		});
		
		getCode();
		
		$("#index_code").blur(function(){
			var code = $("#index_code").val();
			if(isEmpty(code)){
				$("#index_code").tips({
	                side:3,
	                msg:'验证码不能为空',
	                bg:'#AE81FF',
	                time:1
            	});
			}else{
				$.ajax({
				　　　　type : "post",
				　　　　url :'../validate/checkCode.do',
				　　　　data : {
				　　　　		code:code,
				　　　　　　　　},
				　　　　dataType : 'json',
					 async:false, 
				　　　　success : function(data) {
				　　　　　　if(data.data[0].data=="success"){
				　　　　　　　	//　alert("验证码正确");
				　　　　　　　	$("#index_code").tips({
				                side:1,
				                msg:'验证码正确',
				                bg:'#AE81FF',
				                time:1
			            	});
				　　　　　　　	$("#index_code").parent().removeClass("has-error has-warning").addClass("has-success");
				　　　　　　　	$("#skyTips").removeClass("hideTips glyphicon-remove red").addClass("glyphicon-ok green");
				　　　　　　　　}
				　　　　　　else{
				　　　　　　　//　alert("验证码错误");
							$("#index_code").tips({
				                side:1,
				                msg:'验证码错误',
				                bg:'#AE81FF',
				                time:1
				            });
							$("#index_code").parent().removeClass("has-warning has-success").addClass("has-error");
							$("#skyTips").removeClass("hideTips glyphicon-ok green").addClass("glyphicon-remove red");
				　		}
					}
				});
			}
		});

		
		$("input").click(function(e){
			var obj = $(e.target);
			var id = obj.attr("id");
			if(id=="xydaCode"){
				var text = $("#"+id).val();
				if(isEmpty(text)){
					$(obj).tips({
		                side:2,
		                msg:'在此处输入注册号或者统一信用代码',
		                bg:'#AE81FF',
		                time:1
		            });
				}
			}
			
			if(id=="entNameInput"){
				var text = $("#"+id).val();
				if(isEmpty(text)){
					$(obj).tips({
		                side:2,
		                msg:'在此次输入企业名称',
		                bg:'#AE81FF',
		                time:1
		            });
				}
			}
			if(id=="index_code"){
				var text = $("#"+id).val();
				if(isEmpty(text)){
					$(obj).tips({
		                side:1,
		                msg:'输入验证码',
		                bg:'#AE81FF',
		                time:1
		            });
				}
			}
		})
	});
		
	
$("#submit2").click(function() {
	//$(this).addClass("disabled");
	initData();
});
		
function showMask(){     
   /* $("#mask").css("height",$(document).height());  
    $("#mask").css("width",$(document).width());   
    $("#myLoading").show();
    $("#mask").show(); */
}  
//隐藏遮罩层  
function hideMask(){     
   /* $("#mask").hide();  
    $("#myLoading").hide();*/
} 



//验证码
function getCode() {
  　		$("#index_code").val("");
  　　		var url = "../validate/getCode.do?timestamp="+(new Date()).valueOf();
  　　		$("#imgObj").attr("src", url);
}


function checkCode() {
	var flag = 0;
　　　if($("#codes").is(':hidden')){
　　　　	return;
	}
	var  code = $("#index_code").val();
	var isNum = checkNumber(code);
	if(!isNum){
		$("#index_code").parent().addClass("has-warning");
		$("#skyTips").removeClass("hideTips glyphicon-ok green").addClass("glyphicon-remove red");
	}else{
		$.ajax({
		　　　　type : "post",
		　　　　url :'../validate/checkCode.do',
		　　　　data : {
		　　　　		code:code,
		　　　　　　　　},
		　　　　dataType : 'json',
			 async:false, 
		　　　　success : function(data) {
		　　　　　　if(data.data[0].data=="success"){
		　　　　　　　	//　alert("验证码正确");
		　　　　　　　	$("#index_code").parent().removeClass("has-error has-warning").addClass("has-success");
		　　　　　　　	$("#skyTips").removeClass("hideTips glyphicon-remove red").addClass("glyphicon-ok green");
		　　　　　　　	flag = 1;
		　　　　　　　　}
		　　　　　　else{
		　　　　　　　//　alert("验证码错误");
					$("#index_code").parent().removeClass("has-warning has-success").addClass("has-error");
					$("#skyTips").removeClass("hideTips glyphicon-ok green").addClass("glyphicon-remove red");
		　　　　　　　　getCode();
		　		}
			}
		});
	}
	return flag;
}





