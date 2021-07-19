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
					    	$("#tips").css("display","block");
					    	//初始化列表数据
					   /* 	
					    	var gridUrl="../../otherselect/SXQueryList.do";
				    		$("#SXQueryListGrid").gridpanel("option",'datarender',torch.backFunction);
				    		$("#SXQueryListGrid").gridpanel("option",'dataurl',gridUrl);
				    		$("#SXQueryListGrid").gridpanel('query', ['SXQueryListPanel'],null);*/
				    		
				    		$("#save_button").off('click').on('click',$_this.queryCYList);
						    $("#reset_button").off('click').on('click',torch.resetEditCondition);
							
						    util.exports('editFunc',$_this.editFunc);
						    
						    
					    });
				});
	    	},
	    	
	    	
	    	/*getCount:function(){
	    		var params = {
	    				url : '../../otherselect/SXQueryList.do?flag=1',
	    				components: ['SXQueryListPanel'],
	    				callback : function(data, param, res) {
	    					$("#totals").html(data.data);
	    				}
	    		};
	    		$.DataAdapter.submit(params);
	    	},*/
	    	
			 /**
			   * 查询餐饮服务许可证信息
			   */
	    	queryCYList : function(){
	    		//torch.getCount();
	    		
	    		
	    		var queryValue1 = $("div[name = 'unitName']").textfield("getValue");
	    		var queryValue2 = $("div[name = 'certificateNo']").textfield("getValue");
	    		
	    		var queryValue3 = $.trim(queryValue1);
	    		var queryValue4 = $.trim(queryValue2);
	    		
	    		//if(queryValue3 || queryValue4){
	    			var gridUrl="otherselect/CYQueryList.do";
		    		$("#SXQueryListGrid").gridpanel("option",'datarender',torch.backFunction);
		    		$("#SXQueryListGrid").gridpanel("option",'dataurl',gridUrl);
		    		$("#SXQueryListGrid").gridpanel('query', ['CYQueryListPanel'],null);
	    	/*	}else{
	    			jazz.warn("请输入查询条件");
	    		}*/
			},
			/**
			 * SYS_FUNCTION_CONFIG列表回调函数
			 */
			backFunction:function (event, obj) {
	    		var data = obj.data;
	    		if (data.length == 0) {
	    			$(".nodata").css("display", "block");
	    		} else {
	    			$(".nodata").css("display", "none");
	    		}
	    		for (var i = 0; i < data.length; i++) {
	    			data[i]["opt"] = '<a href="javascript:void(0);" onclick="ebaic.editFunc(\''+data[i].id+'\')">' + "详情" + '</a>';
	    		}	
	    		return data;
	    	},
	    	
	    	editFunc:function(id){
	    		//util.openWindow("CYQueryDetail","餐饮服务许可证信息详情信息",$_this.getContextPath()+"/page/otherselect/CYQueryDetail.html?id="+id,700,350);
	    		//window.open ($_this.getContextPath()+"/page/otherselect/CYQueryDetail.html?id="+id,'详细信息','height=290,width=600,top=180,left=370,toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
	    		$("#winContent").css("display","block");
	    		$("#winContent").attr("src",$_this.getContextPath()+"/CYQueryDetail.html?id="+id);
	    	},
	    	
	    	getContextPath: function (){
	    		var pathName = document.location.pathname;
	    		var index = pathName.substr(1).indexOf("/");
	    		var result = pathName.substr(0,index+1);
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
	    	edit_fromNames : [ 'CYQueryListPanel']
	    	
		};
	
		torch._init();
		return torch;
});
