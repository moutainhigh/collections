define(['require','jquery','entCommon','qrcode'], function(require,entCommon){
var torch = {
		_init : function(){
			 require(['domReady'], function (domReady) {
			    domReady(function () {
			    	torch.getUniScid();
			    	$("div[name = 'cp_returnButton']").off('click').on('click',torch.back_button);
				 });
			 });
		},
		
		getUniScid:function(){
			$.ajax({
				url:'../../../apply/entManagerAccount/getUniScid.do',
				type:"post",
				async:false,
				success: function(data){
					var UniScid = $.trim(data);
					$("#lincensePic").attr('src','../../../../common/cert/show.do?regNo='+UniScid);
				
				}
			});
		},
	
		back_button:function(){
			window.location.href="home.html";
		}
	};
	torch._init();
	return torch;
});

