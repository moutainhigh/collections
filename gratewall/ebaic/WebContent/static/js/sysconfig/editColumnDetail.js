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
				    		var columnId = jazz.util.getParameter("columnId");
				    		if(columnId){
				    			$_this.reloadEditColumnDetail(columnId);
				    		}
				    		var configId = jazz.util.getParameter("configId");
				    		if(configId){
				    			$("#configId").hiddenfield("setValue",configId);
				    		}
				    		
				    		$("#save_button").off('click').on('click',$_this.saveEditColumn);
				    		$("#back_button").off('click').on('click',torch.goBack);
				    		$("#reset_button").off('click').on('click',torch.resetEditConfig);
				    		
				    		util.exports('queryEditColumnDetail',$_this.queryEditColumnDetail);
				    		util.exports('deleteEditColumn',$_this.deleteEditColumn);
				    });
			});
    	},
    	//配置编辑页面保存方法
    	//配置function页面保存方法
    	saveEditColumn:function (){
    		var params = {
					url : '../../../../torch/config/saveEditColumn.do',
					components : [ 'editColumnPanel' ],
					callback : function(data, param, res) {
						var msg = res.getAttr("save");
						if(msg=='success'){
							jazz.info("保存成功！",function(){
								util.closeWindow("editConfig.html",true);
							});
						}s
					}
				};
				$.DataAdapter.submit(params);
    	},
    	
    	reloadEditColumnDetail:function (columnId){
    		var params = {
    			columnId:columnId
    		};
    		var formUrl='../../../../torch/config/queryEditColumnDetail.do';
    		$("#editColumnPanel").formpanel("option","dataurlparams",params);
    		$("#editColumnPanel").formpanel("option","dataurl",formUrl);
    		$("#editColumnPanel").formpanel("reload");
    		
    		/*var gridUrl="../../../../torch/config/editColumnList.do";
    		$("#editColumnGrid").gridpanel("option",'datarender',torch.editColumnGridRender);
    		$("#editColumnGrid").gridpanel("option","dataurlparams",params);
    		$("#editColumnGrid").gridpanel("option","dataurl",gridUrl);
    		$("#editColumnGrid").gridpanel("reload"); */
    	},

    	editColumnGridRender:function (event, obj) {
    		var data = obj.data;
    		if (data.length == 0) {
    			$(".nodata").css("display", "block");
    		} else {
    			$(".nodata").css("display", "none");
    		}
    		for (var i = 0; i < data.length; i++) {
    			data[i]["opt"] = '<a href="javascript:void(0);" onclick="ebaic.queryEditColumnDetail(\''+data[i].columnId+'\')">' + "编辑" + '</a>'
    						+'&nbsp;&nbsp;<a href="javascript:void(0);" onclick="ebaic.deleteEditColumn(\''+data[i].columnId+'\')">' + "删除" + '</a>';
    		}	
    		return data;
    	},
    	deleteEditColumn:function (columnId){
    		/*jazz.info("对不起，您的权限不足，请联系管理员！");
    		return;*/
    		var params = {
    				url : '../../../../torch/config/deleteEditColumn.do',
    				params:{
    					columnId:columnId
    				},
    				callback : function(data, param, res) {
    					var msg = res.getAttr("del");
    					if(msg=='success'){
    						jazz.info("删除成功！",function(){
    							var configId = jazz.util.getParameter("configId");
    							url="../../../../torch/config/editColumnList.do?configId="+configId;
    							$("#editColumnGrid").gridpanel("option","dataurl",url);
    							$("#editColumnGrid").gridpanel("reload");
    						});
    					}
    				}
    			};
    		$.DataAdapter.submit(params);
    	},
    	queryEditColumnDetail:function (columnId){
    		util.openWindow("queryEditColumnDetail","编辑列组件信息","editColumnDetail.html?functionId="+columnId,1050,450);
    		/*var url = $_this.getContextPath()+"/mgr/torch/sysconfig/editColumnDetail.html?columnId="+columnId;
    		window.location.href=url;*/
    	},
    	/**
    	 * 重置方法
    	 */
    	resetEditConfig : function(){
    				for( x in torch.edit_fromNames){
    					$("div[name='"+torch.edit_fromNames[x]+"']").formpanel("reset");
    				} 
    	},
    	edit_fromNames : [ 'editColumnPanel'],

    	goBack:function (){
    		history.go(-1);
    	},
    	
    	//html中获取当前页的 上下文路径
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
