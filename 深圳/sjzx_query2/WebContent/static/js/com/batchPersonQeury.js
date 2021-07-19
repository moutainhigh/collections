define([ 'jquery', 'jazz', 'util','ajaxfileupload','form' ], function($, jazz, util,ajaxfileupload,form) {
var torch ={};
	torch = {
			_init: function(){
				$_this = this;
				require(['domReady'], function (domReady) {
					    domReady(function () {
                             //初始化列表数据
						    $("#btnClose").off('click').on('click',torch._leave);
					    });
				});
	    	},
	    	
	    	_leave:function(){
	    		var temp = window.parent.$("#frame_win").get(0).contentWindow.html();
	    		console.lgo(temp)
	    	},
	    	edit_fromNames : [ 'formpanel_edit']
		};
	
		torch._init();
		return torch;
});
