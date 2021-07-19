define(['require','jquery', 'common'], function(require,$, common){
	var getParameter = function(name){
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); 
		var r = window.location.search.substr(1).match(reg); 
		if (r!=null) { 
		   return unescape(r[2]); 
		} 
		return null; 
	};
	var torch = {
			_init : function(){
				require(['domReady'], function (domReady) {
					  domReady(function () {
						  
					  });
				});		
			},
			
		
	}
	torch._init();
    return torch;

});

