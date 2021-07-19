define(['require', 'jquery', 'approvecommon','util'], function(require, $, approvecommon, util){

    var g = {
    	/**
    	 * 模块初始化。
    	 */
    	_init: function(){
    		require(['domReady'], function (domReady) {
			  domReady(function () {
				// 绑定事件
    	        $("[name='doAdopt']").on('click', g.doAdopt);
    	        g.noData();
			  });
			});
	    	 
    	} ,
    	
    	
    	/**
    	 * 领取
    	 * @returns {Boolean}
    	 */
    	doAdopt:function (){
    		var reqarr = g.getRequisitionIdarr();
    		if(reqarr.length==0){
    			jazz.info("请勾选待领取的申请记录");
    			return false;
    		}
    		
    		jazz.confirm("确认领取吗？",function (){
    			var params = {
    					url:'../../../approve/censor/saveAdopt.do',
    					params:{
    						reqArr:reqarr
    					},
    					callback:function(data,r,res){
    						if(res.getAttr('result')=='success'){
    							jazz.info("领取成功",function(){
    								tf.queryLq.queryLqSure();
    							});
    						}else{
    							jazz.info("领取失败");
    						}
    					}
    			}
    			$.DataAdapter.submit(params);
    		});
    	},
    	
    	/**
    	 * 获取选中的数据
    	 * @returns {Array}
    	 */
    	getRequisitionIdarr:function(){
    		var ii = $("div[name='queryGrid_Grid']").gridpanel('getSelectedRowData');
    		var requisitionarr = [];
    		for(var i=0;i<ii.length;i++){
    			requisitionarr[i] = ii[i].requisitionId;
    		}
    		return requisitionarr;
    	},
    	/*无数据的时候显示图片*/
    	noData:function(){
    		$.ajax({
    			url:"../../../torch/service.do?fid=query_lq&wid=queryLq",
    			type:"post",
    			async:false,
				dataType:"json",
				success:function(data){
					var len=data.data[0].data.totalrows;
					if(len==0){
						$("#img").css("display","block");
					}
				}
    		});
    	}
    	
	    

    };
    g._init();
    return g;
});