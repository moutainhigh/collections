define(['jquery', 'jazz','util' ], function($, jazz,util){
 var torch ={};
	torch = {
		/**
		 * 模块初始化
		 */
			_init: function(){
				$_this = this;
				require(['domReady'], function (domReady) {
					    domReady(function () {
					    	var id = jazz.util.getParameter("id")||"";
				    		if(id){
				    			$_this.queryFunctionConfig(id);
				    		}
			    	   	 	$("tr:even").css({background:"#EFF6FA"});
			    		 	$("tr:odd").css({background:"#FBFDFD"});
			    		 	
			    		 	$(".jazz-field-comp-input").attr("disabled","disabled");
					    });
				});
	    	},
	    	
			 queryFunctionConfig : function(id){
				     var panelUrl="../../otherselect/getSPQueryById.do?id="+id;
	    			$("#querySPDetialPanel").formpanel("option","dataurl",panelUrl);
	    			$("#querySPDetialPanel").formpanel("reload");
			},
	    	
		};
	
		torch._init();
		return torch;
});
