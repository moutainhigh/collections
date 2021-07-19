define(['require','jquery', 'approvecommon','util'], function(require, $, approvecommon, util){

    var g = {
    	/**
    	 * 模块初始化。
    	 */
    	_init: function(){
    		require(['domReady'], function (domReady) {
			  domReady(function () {
				// 绑定事件
			    $("div[name='doReceive']").on('click', g.doReceive);
			    g.noData();
			  });
			});
	    	
    	} ,
    
    	doReceive: function (){
    		var obj = $("div[name='queryForm_Grid']").gridpanel('getSelectedRowData');
    		if(obj.length==0){
    			jazz.info("请至少选取一项!");
    			return false;
    		}
    		jazz.confirm("确认要领取吗?",function(){
    			var arr = g.getRequisitionIdArr();
    			var params = {
    					url:'../../../approve/approval/doReceive.do',
    					params : {
    						requisitionArr:arr
    					},
    					callback : function(jsonData,param,res) {
    						if(res.getAttr('result')=='success'){
    							jazz.info("领取成功。",function(){
    								tf.approvalReceive_list.approvalReceive_listSure();
    							});
    						}else{
    							jazz.info("领取失败");
    						}
    					}
    			}
    			$.DataAdapter.submit(params);
    		},function(){});
	    } ,
	    getRequisitionIdArr : function (){
	    	var ii = $("div[name='queryForm_Grid']").gridpanel('getSelectedRowData');
	    	var requisitionarr = [];
	    	for(var i=0;i<ii.length;i++){
	    		requisitionarr[i] = ii[i].requisitionId;
	    	}
	    	return requisitionarr;
	    },
	    /*无数据的时候显示图片*/
	    noData:function(){
	    	$.ajax({
	    		url:"../../../torch/service.do?fid=5187ecb80fae47e0ac739f8edbe6c643&wid=33CB2653196B66F1E055000000000001",
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