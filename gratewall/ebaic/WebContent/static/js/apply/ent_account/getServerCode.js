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
					
					$("#getServerCode").on("click",entAuth.getServerCode);
				});
			});
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
				jazz.error("企业注册号不能为空。");
				return;
			}
			var params = {
		        url : "../../../apply/entAuth/getServerCode.do",
		        params:{ entName : entName  , regNo : regNo },
		        async:false,
		        callback : function(data, param, res) {
		        	var authMap = res.getAttr('authMap');
		        	$("#result").show();
		        	$("#serverCode").text(authMap.serverCode);
		        	$("#authTime").text(authMap.startTime);
		        } 	
		    };  	
    		$.DataAdapter.submit(params);
		}
	}
	entAuth._init();
	return entAuth;
});