/**
 * file : loginStatus.js
 */
define(['jquery', 'jazz'], function($, jazz){
    var loginStatus = {
    	_user : null ,
    	getCurrentUser : function(){
    		if(loginStatus._user){
    			return loginStatus._user;
    		}
    		var ret = null;
    		$.ajax({
    			url : '../../../security/auth/user/loginStatus.do',
    			type : "post",
    			async : false,
    			dataType : "json",
    			success : function(data) {
    				ret = data;
    			}
    		});
			loginStatus._user = ret ;
			if(!loginStatus._user){
				alert('登录超时，请重新登录。');
				window.location.href = "/";
			}
			if(ret.result=='mobFail'||ret.result=='identityFail'||ret.result=='idCardFail'){
				//暂时没有具体的验证页面，统一跳转到安全中心，后台有可用的内部跳转的方法
				$("#hint-box").show();
				$("#bg").show();
			}
    		return ret;
    	}
    };
    return loginStatus;
});