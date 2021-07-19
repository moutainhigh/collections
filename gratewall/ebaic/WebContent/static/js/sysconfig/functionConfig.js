define(['jquery', 'jazz','util' ], function($, jazz,util){
	 var torch ={};
		torch = {
	    	_init: function(){
	    		$_this = this;
	    		require(['domReady'], function (domReady) {
				    domReady(function () {
			    		var functionId = jazz.util.getParameter("functionId")||"";
			    		if(functionId){
			    			this.reloadFuncConfig(functionId);
			    		}
			    		var treeId = jazz.util.getParameter("treeId")||"";
			    		if(treeId){
			    			$("#treeId").hiddenfield("setValue",treeId);
			    		}
			    		var treePath = jazz.util.getParameter("treePath")||"";
			    		if(treePath){
			    			$("#treePath").comboxfield('addOption', treePath,treePath);
			    		}
			    		$("#save_button").off('click').on('click',$_this.insertFunConfig);
			    		$("#newAddQuery_button").off('click').on('click',$_this.addQueryConfig);
			    		$("#back_button").off('click').on('click',torch.goBack);
				    	$("#newAddEdit_button").off('click').on('click',$_this.addEditConfig);
				    	$("#function_reset_button").off('click').on('click',torch.resetFuncConfig);
				    });
				});
				
	    	},
	    	reloadFuncConfig:function(functionId){
	    		var params = {
	    			functionId:functionId
	    		};
	    		url="../../../../torch/config/queryFuncConfig.do";
	    		$("#functionConfigPanel").formpanel("option","dataurlparams",params);
	    		$("#functionConfigPanel").formpanel("option","dataurl",url);
	    		$("#functionConfigPanel").formpanel("reload");
	    		
	    		var gridUrl="../../../../torch/config/editOrQueryGrid.do";
	    		$("#editOrQueryGrid").gridpanel("option","dataurlparams",params);
	    		$("#editOrQueryGrid").gridpanel("option","dataurl",gridUrl);
	    		$("#editOrQueryGrid").gridpanel("reload"); 
	    	},
	    	//配置function页面保存方法
	    	insertFunConfig:function (){
	    		var headfilePath = $("#headfilePath").comboxfield("getValue");
	    		var treePath = $("#treePath").comboxfield("getValue");
	    		var pagefileName =$.trim($("#pagefileName").textfield("getValue"));
	    		var fileSuffix = $("#fileSuffix").comboxfield("getValue");
	    		var pagefilePath =headfilePath+treePath+"/"+pagefileName+fileSuffix;
	    		if(pagefilePath){
	    			$("#pagefilePath").hiddenfield("setValue",pagefilePath);
	    		}
	    		var params = {
	    			url : '../../../../torch/config/insertFunConfig.do',
	    			components : [ 'functionConfigPanel' ],
	    			callback : function(data, param, res) {
	    				var msg = res.getAttr("save");
	    				if(msg=='success'){
	    					jazz.info("保存成功！",function(){
	    						util.closeWindow("functionList.html",true);
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
	    	resetFuncConfig : function(){
	    				for( x in torch.edit_fromNames){
	    					$("div[name='"+torch.edit_fromNames[x]+"']").formpanel("reset");
	    				} 
	    	},
	    	edit_fromNames : [ 'functionConfigPanel'],
	    	
	    	goBack:function (){
	    		history.go(-1);
	    	}
	    };
	    torch._init();
	    return torch;
	    
});














