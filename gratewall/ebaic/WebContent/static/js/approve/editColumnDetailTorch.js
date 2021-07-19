define([ 'require', 'jquery', 'approvecommon', 'util', 'approve/approve' ],
		function(require, $, approvecommon, util, approve) {
			var torch = {
					
					_init : function(){
						 require(['domReady'], function (domReady) {
							  domReady(function () {
									var columnId = jazz.util.getParameter("columnId");
									var params = {
										columnId:columnId
									}
									var formUrl='../../../../torch/config/queryEditColumnDetail.do';
									$("#editColumnPanel").formpanel("option","dataurlparams",params);
									$("#editColumnPanel").formpanel("option","dataurl",formUrl);
									$("#editColumnPanel").formpanel("reload"); 
								  
							  })
						})
				},
					
				
					//配置编辑页面保存方法
					saveEditColumn:function(){
						
						var params = {
								url : '../../../../torch/config/saveEditColumn.do',
								components : [ 'editColumnPanel' ],
								callback : function(data, param, res) {
									var msg = res.getAttr("save");
									if(msg=='success'){
										jazz.info("更新成功！");
									}
								}
							};
							$.DataAdapter.submit(params);
						
					},
					goBack:function(){
						
						history.go(-1);
						
					}
					
			}
			
	torch._init();
	return torch;		
});

