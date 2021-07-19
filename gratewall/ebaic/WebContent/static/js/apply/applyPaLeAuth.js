define([ 'require', 'jquery', 'common' ,'util'], function(require, $, common,util) {
	var applyPaLeAuth = {
		_init : function() {

			require([ 'domReady' ], function(domReady) {
				domReady(function() {

					applyPaLeAuth.applyPaLeAuthQuery();
					$("#applyPaLeAuth_save_button").on('click', applyPaLeAuth.applyPaLeAuthSave);
					$("#applyPaLeAuth_cancel_button").on('click', applyPaLeAuth.applyPaLeAuthCancel);
					$("div[name='applyPaLeAuthQuery_Form']").css("border","none");
				});
			});
		},
		
		applyPaLeAuthQuery : function() {
			var url = "../../../torch/service.do?fid=applyPaLeAuth&wid=applyPaLeAuthQuery&gid="+jazz.util.getParameter('gid');
			$("div[name='applyPaLeAuthQuery_Form']").formpanel("option", "dataurl",url);
			$("div[name='applyPaLeAuthQuery_Form']").formpanel("reload");
			
		},

		applyPaLeAuthSave : function() {
			jazz.confirm("授权后，被授权人将可以代替法定代表人进行业务确认和提交的操作，业务后续如果出现退回再提交的情况，也无需法定代表人参与。您可以通过终止业务来取消授权。点击【确定】完成授权。",function(){
				var params = {
					url : "../../../torch/service.do?fid=applyPaLeAuth&wid=applyPaLeAuthSave&gid="+jazz.util.getParameter('gid'),
					callback : function(jsonData, param, res) {
						
						jazz.info("授权成功",function(){
							window.location.href="../../../page/apply/person_account/home.html";
						});
					}
				};
				$.DataAdapter.submit(params);
			},function(){
				
			});
			
		},
		applyPaLeAuthCancel:function(){
			history.go(-1);
			//window.location.href="../../../page/apply/person_account/home.html";
			//util.closeWindow('authOpr');
		}
		
		
	};
	applyPaLeAuth._init();
	return applyPaLeAuth;

});
