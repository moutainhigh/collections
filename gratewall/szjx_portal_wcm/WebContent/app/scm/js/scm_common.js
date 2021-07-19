//scm公共js文件
(function(){
	var ajax0 = $.ajax;
	// 重载ajax方法，设置contentType属性
	$.ajax = function(opt){
		var firstArg = arguments[0] || {};
		if(!firstArg.contentType){
			firstArg.contentType = "application/x-www-form-urlencoded";
		}
		ajax0.apply($, arguments);
	}
})();