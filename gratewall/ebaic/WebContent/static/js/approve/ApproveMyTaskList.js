define(['require','jquery', 'approvecommon','util'], function(require, $, approvecommon, util){

    var g = {
    	/**
    	 * 模块初始化。
    	 */
    	_init: function(){	
    		require(['domReady'], function (domReady) {
    			  domReady(function () {
    				  // 绑定事件
    				  $("[name='mytask_button']").on('click',g.myTaskApprove);
    				  g.noData();
    			  });
    		});
	    	
    	},
    	myTaskApprove:function(){
    		var rowData = $("div[name='queryGrid_Grid']").gridpanel('getSelectedRowData');
    		if(rowData.length==0){
    			jazz.warn("请选择需要查看的数据!");
    			return false;
    		}
    		var gid = rowData[0].gid;
    		
    		$.ajax({
    			url:'../../../approve/process/checkIsOnePerson.do',
    			data:{
    				"gid":gid
    			},
    			type:'post',
    			dataType:'json',
    			success:function(data){
    				if(data==false){
    					jazz.warn("核准人和审查人不能是同一个人！");
    					return false;
    				}else{
    					window.location.href="../../../page/approve/process/approve.html?gid="+gid;
    				}
    			},
    			error:function(data){
    				jazz.info("数据异常，请联系管理员！");
    			}
    		});
    		
    		
    		//util.openWindow("approve","业务审核","../../../page/approve/process/approve.html?gid="+gid,1000,600);
    	},
    	/*无数据显示图片*/
    	noData:function(){
    		$.ajax({
    			url:"../../../torch/service.do?fid=mytask_list&wid=queryMytaskList",
    			type:"post",
    			dataType:"json",
    			async:false,
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