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
                               //初始化列表数据
				    		var gridUrl="../../hlselect/HLQueryList.do";
				    		$("#CFQueryListGrid").gridpanel("option",'datarender',torch.backFunction);
				    		$("#CFQueryListGrid").gridpanel("option",'dataurl',gridUrl);
				    		$("#CFQueryListGrid").gridpanel('query', ['CFQueryListPanel'],null);
				    		
				    		$("#save_button").off('click').on('click',$_this.queryCFList);
						    $("#reset_button").off('click').on('click',torch.resetEditCondition);
						    $("#add_button").off('click').on('click',torch.addFunc);
						    util.exports('regDetail',$_this.regDetail);
						    util.exports('editFunc',$_this.editFunc);
						    util.exports('delFunc',$_this.delFunc);
					    });
				});
	    	},
	    	getCount:function(){
	    		var params = {
	    				url : '../../quickselect/QuickQueryList.do',
	    				components: ['CFQueryListPanel'],
	    				callback : function(data, param, res) {
	    					$("#totals").html(data.data);
	    				}
	    		};
	    		$.DataAdapter.submit(params);
	    	},
			 /**
			   * 查询失信被执行人信息
			   */
	    	queryCFList : function(){
//	    		var entname = $('#entname').textfield("getValue");
//	    		var regno = $('#regno').textfield("getValue");
//				if (!entname && !regno) {
//					jazz.warn("请输入商事主体名称或统一社会信用代码！");
//					return false;
//				}
				$('#save_button').button('disable');
	    		$("#save_button").off('click');
	    		var timer = null;  
	    		clearTimeout(timer);  
	    			timer = setTimeout(function() { 
	    				$('#save_button').button('enable');
	    	    		$("#save_button").on('click',$_this.queryCFList);
	    		}, 1800);
				
//					torch.getCount();
			    	//	$("#tips").css("display","block");
				    		//查询失信被执行人信息（DC_BL_LAOLAI），并且有回调函数
			    		var gridUrl="../../hlselect/HLQueryList.do";
			    		$("#CFQueryListGrid").gridpanel("option",'datarender',torch.backFunction);
			    		$("#CFQueryListGrid").gridpanel("option",'dataurl',gridUrl);
			    		$("#CFQueryListGrid").gridpanel('query', ['CFQueryListPanel'],null);
			},
			
			addFunc:function(){ 
	    		var type="add";
	    		util.openWindow("portalApp_editOrAdd","新增前海汇率",$_this.getContextPath()+"/page/comselect/HLAdd.html?type="+type,600,400);
	    	},
	    	
			/**
			 * SYS_FUNCTION_CONFIG列表回调函数
			 */
			backFunction:function (event, obj) {
	    		var data = obj.data;
	    		if (data.length == 0) {
	    			jazz.warn("没有结果，请重新查询");
	    		} else {
	    			$(".nodata").css("display", "none");
	    		}
	    		for (var i = 0; i < data.length; i++) {
	    			data[i]["opt"] = '<a  href="javascript:void(0);" title="删除" onclick="ebaic.delFunc(\''+data[i].id+'\')">删除</a>';
	    		}
	    		return data;
	    	},
	    	
	    	/**
	    	 * 弹出editFunctionConfig.html页面
	    	 */
	    	editFunc:function(sExtSequence,entname,regno,abnormaltype,createtime,resoleunit,removetypecn,removetime,removedept,remark,btype,publictime,type,opetype,entstatus,entid){
	    		util.openWindow("SXQueryDetail","异常名录详情信息",$_this.getContextPath()+"/page/comselect/YCQueryDetail.html?sExtSequence="+sExtSequence
	    				+"&entname="+encode(entname)+"&regno="+encode(regno)+"&abnormaltype="+encode(abnormaltype)+"&createtime="+encode(createtime)+"&resoleunit="+encode(resoleunit)
	    				+"&removetypecn="+encode(removetypecn)+"&removetime="+encode(removetime)
	    				+"&removedept="+encode(removedept)+"&remark="+encode(remark)
	    				+"&btype="+encode(btype)+"&publictime="+encode(publictime)
	    				+"&type="+encode(type)+"&opetype="+encode(opetype)+"&entstatus="+encode(entstatus)+"&entid="+encode(entid),900,500);
	    		/*var url = $_this.getContextPath()+"/mgr/torch/sysconfig/editFunctionConfig.html?functionId="+functionId;
	    		window.location.href=url;*/
	    	},
	    	
	    	
	    	/*
	    	 * 清除指定funcConfig
	    	 */
	    	delFunc:function(id){
	    		jazz.confirm("确认要删除该地址吗？",function(){
	    			var params = {
		    				url : '../../hlselect/HLDelete.do',
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
	    	 * 弹出editFunctionConfig.html页面
	    	 */
	    	regDetail:function(id, enttype, opetype, entstatus,entid){
	    			var economicproperty ="";
	    	  		if(enttype.substring(0,1)=="1"||enttype.substring(0,1)=="2"||enttype.substring(0,1)=="3"||enttype.substring(0,1)=="4" || enttype.substring(0,1)=="A" || enttype.substring(0,1)=="C" ){//内资企业
	    	  			economicproperty="2";
	    	  		}else if(enttype.substring(0,1)=="5"||enttype.substring(0,1)=="6"||enttype.substring(0,1)=="7" || enttype.substring(0,1)=="W" || enttype.substring(0,1)=="Y"  ){//外资企业
	    	  			economicproperty= "3";
	    	  		}else if(enttype.substring(0,2)=="95"){//个体
	    	  			economicproperty= "1";
	    	  		}else if(enttype.substring(0,1) == "8"){//集团
	    	  			economicproperty= "4";
	    	  		}else{
	    	  			economicproperty= "2"; //暂时先写成2
	    	  		}
	    	  	//	var urlleft="<%=request.getContextPath()%>";
	    		var urlright = "page/reg/regDetail.jsp";
	    		var url = $_this.getContextPath() + "/" + urlright + "?flag=" + encode("0")
	    				+ "&economicproperty=" + encode(economicproperty) + "&priPid="
	    				+ encode(id) + "&opetype=" + encode(opetype) + "&entstatus="
	    				+ encode(entstatus) + "&entid=" + encode(entid);
	    		window.open(url);

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
	    	/**
	    	 * 重置方法
	    	 */
	    	resetEditCondition : function(){
	    		//$('#select').comboxfield('addOption', '等于', '1');
				//$("#treePath").comboxfield('addOption', treePath,treePath);
				
	    				for( x in torch.edit_fromNames){
	    					$("div[name='"+torch.edit_fromNames[x]+"']").formpanel("reset");
	    					
	    				} 
	    				//$('#select').comboxfield('hideOption','1');
	    				//$("#select").comboxfield("option","label","<font color='red'>*</font>中国居住地址");
	        			//$("#select']").comboxfield("option","rule","must");
	    				$('#select').comboxfield('setValue', '1', null);
	    				/*$("div[name='entname']").textfield("setValue");
	    				$("div[name='regno']").textfield("setValue");
	    				$('#CFQueryListPanel').formpanel('setValue','entname','');
	    				$('#CFQueryListPanel').formpanel('setValue','regno','');*/
	    	},
	    	edit_fromNames : [ 'CFQueryListPanel']
	    	
		};
	
		torch._init();
		return torch;
});
