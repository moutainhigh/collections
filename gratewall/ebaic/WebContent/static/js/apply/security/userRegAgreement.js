define(['require','jquery', 'jazz', 'renderTemplate','util'], function(require,$, jazz, tpl,util){
	var reg = {
		_init : function(){
			var that = this;
			require(['domReady'], function (domReady) {
				domReady(function () {
					that.waitReg();
					$("#backbtn").on('click', that.backIndex); 
					$("#closebtn").on('click', that.knowtips); 
			    	$("[name='apply_security_reg_query_button']").on('click', that.submit); 
				});//enf domReady
			});
			
		},
		waitReg : function(){
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
		},
		//进入注册页面
		knowtips : function(){
			window.location.href = "reg.html";
		}, 
		//返回按钮
		backIndex :function(){
			window.location.href = "../index.html";
		}

	};
	reg._init();
	return reg;
});