define(['jquery', 'jazz','util' ], function($, jazz,util){
 var torch ={};
	torch = {
			defaut :"",
		/**
		 * 模块初始化
		 */
			_init: function(){
				$_this = this;
				require(['domReady'], function (domReady) {
					    domReady(function () {
					    	$_this.queryPortalAppList();
					    	$(document).keydown(function(event) {
								if (event.keyCode == 13) {
									$_this.queryPortalAppList();
								}
							}); 
				    		$("#save_button").off('click').on('click',$_this.queryPortalAppList);
						    $("#reset_button").off('click').on('click',torch.resetEditCondition);
						    $("#add_button").off('click').on('click',torch.addFunc);
						    
						    $("#export_button").off('click').on('click',$_this.exportFunc);
						    
						    $("#toDates").css({"display":"inline-block"});
						    
						    util.exports('detailFunc',$_this.detailFunc);
						    util.exports('editFunc',$_this.editFunc);
						    util.exports('delFunc',$_this.delFunc);
						    util.exports('runOrStopFunc',$_this.runOrStopFunc);
					    });
				});
	    	},
	    	/**
	    	 *  导出数据
	    	 */
	    	exportFunc : function(){
	    		var address = $("#address").textfield('getValue');
	    		var houseCode = $("#houseCode").textfield('getValue');
	    		var addrFlag = $("#addrFlag").radiofield('getValue');
	    		$_this.post("../../WhiteEntSelect/exportExcel.do", {address:address,houseCode:houseCode,addrFlag:addrFlag});
	    	},
	    	post : function(URL, PARAMS) {        
	    	    var temp = document.createElement("form");        
	    	    temp.action = URL;        
	    	    temp.method = "post";        
	    	    temp.style.display = "none";        
	    	    for (var x in PARAMS) {        
	    	        var opt = document.createElement("textarea");        
	    	        opt.name = x;        
	    	        opt.value = PARAMS[x];                
	    	        temp.appendChild(opt);        
	    	    }        
	    	    document.body.appendChild(temp);        
	    	    temp.submit();               
	    	},
			 /**
			   * 查询列表
			   */
	    	queryPortalAppList : function(){
	    		$('#save_button').button('disable');
	    		$("#save_button").off('click');
	    		var timer = null;  
	    		clearTimeout(timer);  
	    			timer = setTimeout(function() { 
	    				$('#save_button').button('enable');
	    	    		$("#save_button").on('click',$_this.queryPortalAppList);
	    		}, 1000);
	    	
		    	//查询失信被执行人信息（DC_BL_LAOLAI），并且有回调函数
	    		var gridUrl="../../WhiteEntSelect/WhiteEntQueryList.do";
	    		$("#whiteEntQueryListGrid").gridpanel("option",'datarender',torch.backFunction);
	    		$("#whiteEntQueryListGrid").gridpanel("option",'dataurl',gridUrl);
	    		$("#whiteEntQueryListGrid").gridpanel('query', ['whiteEntQueryListPanel'],null);
				
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
	    				data[i]["opt"] = '<a  href="javascript:void(0);" title="详情" onclick="ebaic.detailFunc(\''+data[i].id+'\')">详情</a>'
						+'&nbsp;|&nbsp;<a href="javascript:void(0);" title="编辑" onclick="ebaic.editFunc(\''+data[i].id+'\')">编辑</a>'
						+'&nbsp;|&nbsp;<a href="javascript:void(0);" title="删除" onclick="ebaic.delFunc(\''+data[i].id+'\')">删除</a>';
		            }	
	    		return data;
	    	},
	    	/**
	    	 * 详情
	    	 */
	    	detailFunc:function(id){ 
	    		var type="detail";
	    		util.openWindow("portalApp_editOrAdd","详情",$_this.getContextPath()+"/page/comselect/whiteEnt_editOrAdd.html?id="+id+"&type="+type,800,400);
	    	},
	    	editFunc:function(id){ 
	    		var type="edit";
	    		util.openWindow("portalApp_editOrAdd","编辑",$_this.getContextPath()+"/page/comselect/whiteEnt_editOrAdd.html?id="+id+"&type="+type,800,400);
	    	},
	    	addFunc:function(){ 
	    		var type="add";
	    		util.openWindow("portalApp_editOrAdd","新增",$_this.getContextPath()+"/page/comselect/whiteEnt_editOrAdd.html?type="+type,800,400);
	    	},
	    	/*
	    	 * 清除指定funcConfig
	    	 */
	    	delFunc:function(id){
	    		jazz.confirm("确认要删除该地址吗？",function(){
	    			var params = {
		    				url : '../../WhiteEntSelect/WhiteEntDelete.do',
		    				params:{
		    					"id":id
		    				},
		    				callback : function(data, param, res) {
		    					var msg = res.getAttr("del");
		    					if(msg=='success'){
		    						jazz.info("删除成功！",function(){
		    							//util.closeWindow("whiteEntQueryList.html",true);
		    							//$_this.queryPortalAppList();
		    							var tDef =	$(window.top.$("#frame_maincontent").get(0)).attr("src");
			    						var reloads =	$(window.top.$("#frame_maincontent").get(0));
			    						var closeW = $(window.top.$("div[name='portalApp_editOrAdd'"));
			    						var colseOverLay = $(window.top.$("div[class='jazz-window-modal'"));
			    						closeW.remove();
			    						colseOverLay.remove();
			    						reloads.attr("src",tDef);
		    						});
		    					}
		    				}
		    		};
		    		$.DataAdapter.submit(params);
	    		},function(){
	    			
	    		});
	    		
	    	},
	    	/**
	    	 * 打开queryEditConfig.html页面
	    	 */
	    	configEditOrQuery:function(functionId){
	    		var url = $_this.getContextPath()+"/mgr/torch/sysconfig/queryEditConfig.html?functionId="+functionId;
	    		window.location.href=url;
	    	},
	    	
	    	/**
	    	 * 弹出funcConfig添加页面
	    	 */
	    	addFuncConfig:function(){
	    		var treeId = jazz.util.getParameter("treeId");
	    		var treePath = jazz.util.getParameter("treePath");
	    		util.openWindow("addFunctionConfig","新增功能信息","functionConfig.html?treeId="+treeId+"&treePath="+treePath,1000,350);
	    		var url = $_this.getContextPath()+"/mgr/torch/sysconfig/functionConfig.html";
	    		window.location.href=url;
	    	},
	    	//html中获取当前页的 上下文路径
	    	getContextPath: function (){
	    		var pathName = document.location.pathname;//  /page/mgr/torch/sysconfig/functionList.html
	    		var index = pathName.substr(1).indexOf("/");//  4
	    		var result = pathName.substr(0,index+1);//    /page
//	    		alert(pathName);
	    		return result;
	    	},
	    	
	    	/**
	    	 * 批量清理func
	    	 */
	    	funcBatchDelete:function(){
	    		var selected = $('div[name="funcGrid"]').gridpanel('getSelection');
	    		if (selected == null || selected.length<1 ){
	    			jazz.info("请选中至少一个目标");
	    		}else if(selected.length>=1){
		    		jazz.confirm("确定清除Func？",function(){
		    			var params = {
			    				url : '../../../../admin/torch/delBatchFunc.do',
			    				components: ['funcGrid'],
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
	    	leave:function (){
	    	//	history.go(-1);
	    		win.window('close'); 
	    	},
	    	/**
	    	 * 重置方法
	    	 */
	    	resetEditCondition : function(){
	    				for( x in torch.edit_fromNames){
	    					$("div[name='"+torch.edit_fromNames[x]+"']").formpanel("reset");
	    				} 
	    				$('#addrFlag').radiofield('setValue','');
	    			
	    	},
	    	edit_fromNames : [ 'whiteEntQueryListPanel']
	    	
		};
	
		torch._init();
		return torch;
});
