$(function(){
	var i=5;
	$("#closebtn").removeClass().addClass("mobilebtn");
	$("#closebtn").attr("disabled",true);
	var id = setInterval(function(){
		i--;
		$("#closebtn").val("请阅读注册协议("+i+")");
		if(i==0){
			clearInterval(id);
			$("#closebtn").val("进入注册");
			$("#check").css("display","block");
		}
	},1000);
	
	if($("#readed").attr("checked")=="checked"){
		$("#closebtn").removeClass().addClass("btn btn01");
		$("#closebtn").attr("disabled",false);
    }else{
    	$("#closebtn").removeClass().addClass("mobilebtn");
    	$("#closebtn").attr("disabled",true);
    }
	$("#readed").click(function(){
		if($("#readed").attr("checked")=="checked"){
			$("#closebtn").removeClass().addClass("btn btn01");
			$("#closebtn").attr("disabled",false);
        }else{
        	$("#closebtn").removeClass().addClass("mobilebtn");
        	$("#closebtn").attr("disabled",true);
        }
	});
});
//进入注册页面
function knowtips(){
	window.location.href = "reg.html";
} 
//返回按钮
function backIndex(){
	window.location.href = "../index.html";
}
