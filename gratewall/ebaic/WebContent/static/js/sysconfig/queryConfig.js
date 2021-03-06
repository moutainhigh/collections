define(['jquery', 'jazz','util'], function($, jazz,util){
   
	 var torch ={};
	 torch = {
    	_init: function(){
    		$_this = this;
			require(['domReady'], function (domReady) {
				    domReady(function () {
			    		var configId = jazz.util.getParameter("configId");
			    		if(configId){
			    			$_this.reloadQueryConfig(configId);
			    		}
			    		var functionId = jazz.util.getParameter("functionId");
			    		if(functionId){
			    			$("#functionId").hiddenfield("setValue",functionId);
			    		}
			    		
			    		$("#query_button1").off('click').on('click',$_this.createQueryPageBySql);
			    		$("#back_button").off('click').on('click',torch.goBack);
				    	$("#save_button").off('click').on('click',$_this.addQueryConfig);
				    	$("#reset_button").off('click').on('click',torch.resetQueryColumn);
				    	$("#add_query_condition_button").off('click').on('click',torch.addQueryCondition);
				    	$("#add_query_column_button").off('click').on('click',$_this.addQueryColumn);
				    	
				    	util.exports('queryQueryColumnDetail',$_this.queryQueryColumnDetail);
						util.exports('queryQyeryConditionDetail',$_this.queryQyeryConditionDetail);
						util.exports('deleteQueryColumn',$_this.deleteQueryColumn);
						util.exports('deleteQueryConditionColumn',$_this.deleteQueryConditionColumn);
				    });
			});
    		
    	},
    	
    	reloadQueryConfig:function (configId){
    		
    		var params = {
    			configId:configId
    		};
    		
    		var formUrl='../../../../torch/config/queryQueryConfig.do';
    		$("#sysQueryConfigPanel").formpanel("option","dataurlparams",params);
    		$("#sysQueryConfigPanel").formpanel("option","dataurl",formUrl);
    		$("#sysQueryConfigPanel").formpanel("reload");
    		
    		var colUrl="../../../../torch/config/queryColumnList.do";
    		$("#queryColumnGrid").gridpanel("option",'datarender',torch.queryColumnGridRender);
    		$("#queryColumnGrid").gridpanel("option","dataurlparams",params);
    		$("#queryColumnGrid").gridpanel("option","dataurl",colUrl);
    		$("#queryColumnGrid").gridpanel("reload");
    		
    		var conUrl="../../../../torch/config/queryConditionColumnList.do";
    		$("#queryConditionGrid").gridpanel("option",'datarender',torch.queryConditionGridRender);
    		$("#queryConditionGrid").gridpanel("option","dataurl",conUrl);
    		$("#queryConditionGrid").gridpanel("option","dataurlparams",params);
    		$("#queryConditionGrid").gridpanel("reload");
    	},
    	
    	queryColumnGridRender:function (event, obj) {
    		var data = obj.data;
    		if (data.length == 0) {
    			$(".nodata").css("display", "block");
    		} else {
    			$(".nodata").css("display", "none");
    		}
    		for (var i = 0; i < data.length; i++) {
    			data[i]["opt"] = '<a href="javascript:void(0);" onclick="ebaic.queryQueryColumnDetail(\''+data[i].columnId+'\')">' + "??????" + '</a>'
    	 					+'&nbsp;&nbsp;<a href="javascript:void(0);" onclick="ebaic.deleteQueryColumn(\''+data[i].columnId+'\')">' + "??????" + '</a>';
    		}	
    		return data;
    	},
    	queryConditionGridRender :function (event, obj){
    		var data = obj.data;
    		if (data.length == 0) {
    			$(".nodata").css("display", "block");
    		} else {
    			$(".nodata").css("display", "none");
    		}
    		for (var i = 0; i < data.length; i++) {
    			data[i]["opt"] = '<a href="javascript:void(0);" onclick="ebaic.queryQyeryConditionDetail(\''+data[i].conditionId+'\')">' + "??????" + '</a>'
    	 					+'&nbsp;&nbsp;<a href="javascript:void(0);" onclick="ebaic.deleteQueryConditionColumn(\''+data[i].conditionId+'\')">' + "??????" + '</a>';
    		}	
    		return data;
    	},
    	/**
    	 * ????????????QueryColumn
    	 */
    	deleteQueryColumn:function(columnId){
    		/*jazz.info("??????????????????????????????????????????????????????");
    		return;*/
    		jazz.confirm("?????????????????????????????????",function(){
    			var params = {
	    				url : '../../../../torch/config/deleteQueryColumn.do',
	    				params:{
	    					columnId:columnId
	    				},
	    				callback : function(data, param, res) {
	    					var msg = res.getAttr("del");
	    					if(msg=='success'){
	    						jazz.info("???????????????",function(){
//	    							util.closeWindow("queryConfig.html",true);
	    							var configId = jazz.util.getParameter("configId");
	    				    		if(configId){
	    				    			$_this.reloadQueryConfig(configId);
	    				    		}
	    							/*var configId = jazz.util.getParameter("columnId");
	    							var colUrl="../../../../torch/config/queryColumnList.do";
	    							$("#queryColumnGrid").gridpanel("option","dataurlparams",params);
	    							$("#queryColumnGrid").gridpanel("option","dataurl",colUrl);
	    							$("#queryColumnGrid").gridpanel("reload");*/
	    						});
	    					}
	    				}
	    		};
	    		$.DataAdapter.submit(params);
    		},function(){
    			
    		});
    		
    	},
    	
    	/**
    	 * ????????????QueryConditionColumn
    	 */
    	deleteQueryConditionColumn:function(conditionId){
    		/*jazz.info("??????????????????????????????????????????????????????");
    		return;*/
    		jazz.confirm("?????????????????????????????????",function(){
    			var params = {
	    				url : '../../../../torch/config/deleteQueryConditionColumn.do',
	    				params:{
	    					conditionId:conditionId
	    				},
	    				callback : function(data, param, res) {
	    					var msg = res.getAttr("del");
	    					if(msg=='success'){
	    						jazz.info("???????????????",function(){
//	    							util.closeWindow("queryConfig.html",true);
	    							var configId = jazz.util.getParameter("configId");
	    				    		if(configId){
	    				    			$_this.reloadQueryConfig(configId);
	    				    		}
	    						/*	var configId = jazz.util.getParameter("configId");
	    							var conUrl="../../../../torch/config/queryConditionColumnList.do";
	    							$("#queryConditionGrid").gridpanel("option","dataurl",conUrl);
	    							$("#queryConditionGrid").gridpanel("option","dataurlparams",params);
	    							$("#queryConditionGrid").gridpanel("reload");*/
	    						});
	    					}
	    				}
	    		};
	    		$.DataAdapter.submit(params);
    		},function(){
    			
    		});
    		
    	},
    	
    	//??????????????????????????????
    	addQueryConfig:function (){
    		var model = $("#model1").comboxfield('getValue');
    		var params = {
    			url : '../../../../torch/config/torchConfig.do',
    			components : [ 'sysQueryConfigPanel' ],
    			params:{
    				model:model
    			},
    			callback : function(data, param, res) {
    				var msg = res.getAttr("save");
    				if(msg=='success'){
    					jazz.info("???????????????",function(){
    						util.closeWindow("queryEditConfig.html",true);
    					});
    				/*}else{
    					var configId = data.data.rows[0].configId;
    					url="../../../../torch/config/queryColumnList.do?configId="+configId;
    					$("#queryColumnGrid").gridpanel("option","dataurl",url);
    					$("#queryColumnGrid").gridpanel("reload");
    					
    					url="../../../../torch/config/queryConditionColumnList.do?configId="+configId;
    					$("#queryConditionGrid").gridpanel("option","dataurl",url);
    					$("#queryConditionGrid").gridpanel("reload");*/
    				}
    				
    			}
    		};
    		$.DataAdapter.submit(params);
    	},
    	queryQueryColumnDetail:function (columnId){
    		util.openWindow("queryQueryColumnDetail","?????????????????????","queryColumnDetail.html?columnId="+columnId,1000,350);
    		/*var url = torch.getContextPath()+"/mgr/torch/sysconfig/queryColumnDetail.html?columnId="+columnId;
    		window.location.href=url;*/
    	},

    	queryQyeryConditionDetail:function (conditionId){
    		util.openWindow("queryQyeryConditionDetail","????????????????????????","queryConditionColDetail.html?conditionId="+conditionId,1000,400);
    		/*var url = torch.getContextPath()+"/mgr/torch/sysconfig/queryConditionColDetail.html?conditionId="+conditionId;
    		window.location.href=url;*/
    	},
    	
    	addQueryColumn:function (){
    		var configId = jazz.util.getParameter("configId");
    		util.openWindow("addQueryCondition","?????????????????????","queryColumnDetail.html?configId="+configId,1050,450);
    	/*	var url = torch.getContextPath()+"/mgr/torch/sysconfig/queryColumnDetail.html?configId="+configId;
    		window.location.href=url;*/
    	},

    	addQueryCondition:function (){
    		var configId = jazz.util.getParameter("configId");
    		util.openWindow("addQueryCondition","??????????????????????????????","queryConditionColDetail.html?configId="+configId,1050,450);
    		/*var url = torch.getContextPath()+"/mgr/torch/sysconfig/queryConditionColDetail.html?configId="+configId;
    		window.location.href=url;*/
    	},
    	/**
    	 * ????????????
    	 */
    	resetQueryColumn : function(){
    				for( x in torch.edit_fromNames){
    					$("div[name='"+torch.edit_fromNames[x]+"']").formpanel("reset");
    				} 
    	},
    	edit_fromNames : [ 'sysQueryConfigPanel'],

    	
    	goBack:function (){
    		history.go(-1);
    	},
    	
    	//html????????????????????? ??????????????? rgm
    	getContextPath: function (){
    		var pathName = document.location.pathname;//  /page/mgr/torch/sysconfig/functionList.html
    		var index = pathName.substr(1).indexOf("/");//  4
    		var result = pathName.substr(0,index+1);//    /page
//    		alert(pathName);
    		return result;
    	}
    	
    };
	 torch._init();
    return torch;
});
