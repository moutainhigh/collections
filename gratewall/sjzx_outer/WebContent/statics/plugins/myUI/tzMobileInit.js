$(function(){
		$("#clearUserInputs").click(function(){
			clearUserInputs();
		});
	
		getCode();
		$("#index_code").blur(function(){
			var code = $("#index_code").val();
			if(isEmpty(code)){
				//alert("验证码不能为空");
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
				　　　　　　　　}
				　　　　　　else{
				　　　　　　　//　alert("验证码错误");
							$("#index_code").parent().removeClass("has-warning has-success").addClass("has-error");
							$("#skyTips").removeClass("hideTips glyphicon-ok green").addClass("glyphicon-remove red");
				　		}
					}
				});
			}
		});
	});
		
var timer = null; 	
$("#submit2").on("click",function() {
	$(this).html("查询中...");
	clearTimeout(timer); 
	timer = setTimeout(function() { 
			initData();
	}, 500); 
});

/*var btn = document.getElementById("submit2");
btn.onclick = function add(){
	 // $("#submit2").addClass("btn-default").removeClass("btn-primary");
	  btn.innerHTML = "查询中";
	  btn.onclick = null;
	  initData();
	  clearTimeout(timer);
	  var timer = setTimeout(function(){
	    btn.onclick = add;
	    //$("#submit2").addClass("btn-primary").removeClass("btn-default");
	    },2000);  
	}
*/



		


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
	var  code = $("#index_code").val()
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



$(window).load(function() { 
	
}); 