define(['require', 'jquery', 'common','util'], function(require, $, common,util){
	var torch ={};
	torch = {
		/**
		 * 模块初始化
		 */
		 _init : function(){
			 $_this = this;
			 require(['domReady'], function (domReady) {
				    domReady(function () {
				    	$("#redisInfo").comboxfield('option','change',torch.redisInfo);
//				    	$("div[name='redisInfo']").off('click').on('click',torch.redisInfo);
						$("div[name='redis_save_button']").off('click').on('click',torch.redisSave);
						$("div[name='redis_reset_button']").off('click').on('click',torch.redisReset);
						$("div[name='redis_batch_delete_button']").off('click').on('click',torch.redisBatchDelete);
						
						util.exports('delRedis',$_this.delRedis);
				    });
				});
			 },
			 
			 /**
				 * 查询
				 */
			redisSave : function(){
				
				var selectRedis  = $("#selectRedis").comboxfield('getValue');
				if (selectRedis == null || selectRedis == "") {
					jazz.info("请选择redis配置类型");
				}else{
					var url = '../../../../admin/redis/listByPrefix.do?prefix='+selectRedis;
					$("#redisGrid").gridpanel("option",'datarender',torch.redisRender);
			    	$("#redisGrid").gridpanel("option",'dataurl',url);
			    	$("#redisGrid").gridpanel("reload");
				}
			},
			/**
			 * 列表回调函数
			 */
	    	redisRender:function (item, rowsdata) {
	    		var data = rowsdata.data;
	    		if (data.length == 0) {
	    			$(".nodata").css("display", "block");
	    		} else {
	    			$(".nodata").css("display", "none");
	    		}
	    		for (var i = 0; i < data.length; i++) {
	    			data[i]["opt"] = '<a href="javascript:void(0);" onclick="ebaic.delRedis(\''+data[i].key+'\')">' + "清除" + '</a>';
	    		}	
	    		return data;
	    	},
	    	
	    	/**
	    	 * 清除指定key缓存
	    	 */
	    	delRedis:function(key){
	    		jazz.confirm("确定清除缓存？",function(){
	    			var params = {
		    				url : '../../../../admin/redis/delRedis.do',
		    				params:{
		    					key:key
		    				},
		    				callback : function(data, param, res) {
		    					var msg = res.getAttr("del");
		    					if(msg=='success'){
		    						jazz.info("删除成功！",function(){
		    							torch.redisSave();
		    						});
		    					}
		    				}
		    		};
		    		$.DataAdapter.submit(params);
	    		},function(){
	    			
	    		});
	    		
	    	},
	    	
	    	
	    	/**
	    	 * 批量清理redis缓存
	    	 */
	    	redisBatchDelete:function(){
	    		var selected = $('div[name="redisGrid"]').gridpanel('getSelection');
	    		if (selected == null || selected.length<1 ){
	    			jazz.info("请选中至少一个目标");
	    		}else if(selected.length>=1){
		    		jazz.confirm("确定清除缓存？",function(){
		    			var params = {
			    				url : '../../../../admin/redis/delBatchRedis.do',
			    				components: ['redisGrid'],
			    				callback : function(data, param, res) {
			    					var msg = res.getAttr("del");
			    					if(msg=='success'){
			    						jazz.info("删除成功！",function(){
			    							torch.redisSave();
			    						});
			    					}
			    				}
			    		};
			    		$.DataAdapter.submit(params);
		    		},function(){
		    			
		    		});
	    		}
	    	},
	    	
	    	
	    	
    	 /**
		 * 查询redis信息
		 */
    	redisInfo : function(){
			var redisInfo  = $("#redisInfo").comboxfield('getValue');
			var url = '../../../../admin/redis/selectRedisInfo.do?redisInfo='+redisInfo;
			$("#redisInfoGrid").gridpanel("option",'datarender',torch.redisback);
	    	$("#redisInfoGrid").gridpanel("option",'dataurl',url);
	    	$("#redisInfoGrid").gridpanel("reload");
		},
		/**
		 * 查询redis信息回调函数
		 */		
		 redisback:function (item, rowsdata) {
    		var data = rowsdata.data;
    		if (data.length == 0) {
    			$(".nodata").css("display", "block");
    		} else {
    			$(".nodata").css("display", "none");
    		}
    	/*	for (var i = 0; i < data.length; i++) {
    			data[i]["opt"] = '<a href="javascript:void(0);" onclick="ebaic.delRedis(\''+data[i].key+'\')">' + "清除" + '</a>';
    			 * 
    			 
    		}	*/
    		return data;
   	},
    	
    	/**
    	 * 重置方法
    	 */
    	redisReset : function(){
    				for( x in torch.edit_fromNames){
    					$("div[name='"+torch.edit_fromNames[x]+"']").formpanel("reset");
    				} 
    	},
    	
		edit_primaryValues : function(){
		    return "&sjId=" + jazz.util.getParameter('sjId')
		},
		
		edit_fromNames : [ 'insertManageScope_Form'],

		};
	
		torch._init();
		
		return torch;

});
