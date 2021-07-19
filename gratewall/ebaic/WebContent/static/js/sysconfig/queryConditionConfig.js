define(['jquery', 'jazz','util'], function($, jazz,util){
	 var torch ={};
		torch = {
	    	_init: function(){
	    		$_this = this;
	    		require(['domReady'], function (domReady) {
				    domReady(function () {
			    		var conditionId = jazz.util.getParameter("conditionId")||"";
			    		if(conditionId){
			    			$_this.reloadFuncConfig(conditionId);
			    		}
			    		var configId = jazz.util.getParameter("configId");
			    		if(configId){
			    			$("#configId").hiddenfield("setValue",configId);
			    		}
			    		$("#save_button").off('click').on('click',$_this.saveQueryCondition);
				    	$("#back_button").off('click').on('click',$_this.goBack);
				    	$("#reset_button").off('click').on('click',torch.resetEditCondition);
				    });
				});
				
	    	},
	    	reloadFuncConfig:function(conditionId){
	    		var params = {
	    				conditionId:conditionId
	    			}
	    			url="../../../../torch/config/queryQueryConditionDetail.do";
	    			$("#queryConditionPanel").formpanel("option","dataurlparams",params);
	    			$("#queryConditionPanel").formpanel("option","dataurl",url);
	    			$("#queryConditionPanel").formpanel("reload");
	    	},
	    	//配置function页面保存方法
	    	saveQueryCondition:function (){
	    		var params = {
	    				url : '../../../../torch/config/saveQueryCondition.do',
	    				components : [ 'queryConditionPanel' ],
	    				callback : function(data, param, res) {
	    					var msg = res.getAttr("save");
	    					if(msg=='success'){
	    						jazz.info("保存成功！",function(){
	    							util.closeWindow("queryConfig.html",true);
	    							var configId = jazz.util.getParameter("configId");
	    				    		if(configId){
	    				    			$_this.reloadQueryConfig(configId);
	    				    		}
	    						});
	    					}
	    				}
	    			};
	    		$.DataAdapter.submit(params);
	    	},
	    	editOrQueryGridRender :function(event, obj){
	    		var data = obj.data;
	    		if (data.length == 0) {
	    			$(".nodata").css("display", "block");
	    		} else {
	    			$(".nodata").css("display", "none");
	    		}
	    		for (var i = 0; i < data.length; i++) {
	    			var tempVal = data[i].configSql;
	    			if(tempVal.indexOf("select")!=-1){
	    				data[i]["opt"] = '<a href="javascript:void(0);" onclick="queryConfigDetail(\''+data[i].configId+'\')">' + "编辑" + '</a>';
	    			}else{
	    				data[i]["opt"] = '<a href="javascript:void(0);" onclick="editConfigDetail(\''+data[i].configId+'\')">' + "编辑" + '</a>';
	    			}
	    		}	
	    		return data;
	    	},
	    	editConfigDetail:function (configId){
	    		var url = torch.getContextPath()+"/mbr/torch/sysconfig/editConfig.html?configId="+configId;
	    		window.location.href=url;
	    	},
	    	queryConfigDetail:function (configId){
	    		var url = torch.getContextPath()+"/mbr/torch/sysconfig/queryConfig.html?configId="+configId;
	    		window.location.href=url;
	    	},
	    	
	    	addQueryConfig:function (){
	    		var functionId = jazz.util.getParameter("functionId")||"";
	    		var url = torch.getContextPath()+"/mgr/torch/sysconfig/queryConfig.html?functionId="+functionId;
	    		window.location.href=url;
	    	},
	    	addEditConfig:function (){
	    		var functionId = jazz.util.getParameter("functionId")||"";
	    		var url = torch.getContextPath()+"/mgr/torch/sysconfig/editConfig.html?functionId="+functionId;
	    		window.location.href=url;
	    	},
	    	
	    	//html中获取当前页的 上下文路径 rgm
	    	getContextPath: function (){
	    		var pathName = document.location.pathname;//  /page/mgr/torch/sysconfig/functionList.html
	    		var index = pathName.substr(1).indexOf("/");//  4
	    		var result = pathName.substr(0,index+1);//    /page
	//    		alert(pathName);
	    		return result;
	    	},
	    	
	    	/**
	    	 * 重置方法
	    	 */
	    	resetEditCondition : function(){
	    				for( x in torch.edit_fromNames){
	    					$("div[name='"+torch.edit_fromNames[x]+"']").formpanel("reset");
	    				} 
	    	},
	    	edit_fromNames : [ 'queryConditionPanel'],
	    	
	    	goBack:function (){
	    		history.go(-1);
	    	}
	    };
	    torch._init();
	    return torch;
	    
});














