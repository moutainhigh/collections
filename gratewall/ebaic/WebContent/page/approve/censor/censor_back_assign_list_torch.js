var tf = {};
tf.queryBackAssign = {};

tf.queryBackAssign.queryGrid_datarender = function (event,obj){
};

tf.queryBackAssign.query_fromNames = [ 'queryGrid_Form'];




tf.queryBackAssign.queryBackAssignSure = function(){
				$("div[name='queryGrid_Grid']").gridpanel("option", "dataurl","../../../torch/service.do?fid=query_back_assign&wid=queryBackAssign");
				$("div[name='queryGrid_Grid']").gridpanel("query", [ "queryGrid_Form"]);
		};
tf.queryBackAssign.queryBackAssignReset = function(){
			for( x in tf.queryBackAssign.query_fromNames){
				$("div[name='"+tf.queryBackAssign.query_fromNames[x]+"']").formpanel("reset");
			} 
		};

