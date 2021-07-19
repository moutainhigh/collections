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
					    	
					    	$("div[name='adminbrancode']").comboxfield("option","disabled",true);
					    	torch.getRegorg();
					    	
				    		$("#save_button").off('click').on('click',$_this.queryCFList);
						    $("#reset_button").off('click').on('click',torch.resetEditCondition);
							
						    $("div[name='regorg']").comboxfield("option","change",$_this._getAdminbrancode);
						    
						    util.exports('regDetail',$_this.regDetail);
						    util.exports('editFunc',$_this.editFunc);
					    });
				});
	    	},
	    	getRegorg:function(){
	    		var params = {
    				url : '../../SpQySelect/code_value.do?type=regorg',
    				callback : function(data, param, res) {
    					var defValue = data.data[0].value;
    					defaut = defValue;
    					for(var i in data.data){ 
    						$("div[name='regorg']").comboxfield('addOption', data.data[i].text, data.data[i].value);
    					}
    				}
	    		};
	    		$.DataAdapter.submit(params);
	    	},
	    	_getAdminbrancode:function(){
	    		$("div[name='adminbrancode']").comboxfield("option","disabled",true);
	    		var text = $('div[name="regorg"]').comboxfield('getValue');
	    		if(text!=""){
	    			$("div[name='adminbrancode']").comboxfield("option","disabled",false);
	    			$("div[name='adminbrancode']").comboxfield("option","dataurl","../../SpQySelect/code_value.do?type=adminbrancode&param="+text)
		    		$("div[name='adminbrancode']").comboxfield("reload");
	    		}
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
	    		var entname = $('#entname').textfield("getValue");
	    		var regno = $('#regno').textfield("getValue");
	    		var regorg = $('#regorg').comboxfield("getValue");
	    		var adminbrancode = $('#adminbrancode').comboxfield("getValue");
	    		var entstatus = $('#entstatus').comboxfield("getValue");
	    		var area = $('#area').comboxfield("getValue");
				if (!entname && !regno && !regorg && !adminbrancode && !entstatus && !area) {
					jazz.warn("请输入至少一项查询条件");
					return false;
				}
				
				$('#save_button').button('disable');
	    		$("#save_button").off('click');
	    		var timer = null;  
	    		clearTimeout(timer);  
	    			timer = setTimeout(function() { 
	    				$('#save_button').button('enable');
	    	    		$("#save_button").on('click',$_this.queryCFList);
	    		}, 1800);
				
	    		//TODO
	    		var gridUrl="../../yzwfsx_select/YZWFSXEntList.do";
	    		$("#CFQueryListGrid").gridpanel("option",'datarender',torch.backFunction);
	    		$("#CFQueryListGrid").gridpanel("option",'dataurl',gridUrl);
	    		$("#CFQueryListGrid").gridpanel('query', ['CFQueryListPanel'],null);
			},
			/**
			 * SYS_FUNCTION_CONFIG列表回调函数
			 */
			backFunction:function (event, obj) {
	    		var data = obj.data;
	    		if (data.length == 0) {
	    			jazz.warn("该查询没有精确匹配到商事主体");
	    		} else {
	    			$(".nodata").css("display", "none");
	    		}
	    		for (var i = 0; i < data.length; i++) {
	    			data[i]["opt"] = '<a href="javascript:void(0)" onclick="ebaic.editFunc(\''
		    			+ data[i]["entid"]
	    				+ '\',\''
						+ data[i]["entname"]
						+'\')">' + "详情" + '</a>';
					
	    			data[i]["entname"] = '<a href="javascript:void(0)" title="'+data[i]["entname"] +'" onclick="ebaic.regDetail(\''
	    					+ data[i]["id"]
	    					+ '\',\''
	    					+ data[i]["type"]
	    					+ '\',\''
	    					+ data[i]["opetype"]
	    					+ '\',\''
	    					+ data[i]["entstatus"]
			    			+ '\',\''
							+ data[i]["entid"]
			    			+ '\',\''
							+ data[i]["entname"]
			    			+ '\',\''
							+ data[i]["regno"]
	    					+ '\')">' + data[i]["entname"] + ' </a>';
	    			if(data[i]["createtime"]){
	    				data[i]["createtime"] = data[i]["createtime"].substring(0,10);
	    			}
		}
	    		return data;
	    	},
	    	
	    	/**
	    	 * 弹出editFunctionConfig.html页面
	    	 */
	    	editFunc:function(entid,entname){
	    		window.open($_this.getContextPath()+"/page/comselect/YZWFSXQueryEnt.html?entid="+entid+"&entname="+encode(entname));
	    	},
	    	
	    	
	    	/*
	    	 * 清除指定funcConfig
	    	 */
	    	delFunc:function(functionId){
	    		jazz.info("对不起，您的权限不足，请联系管理员！");
	    		return;
	    		jazz.confirm("确认要删除该功能项吗？",function(){
	    			var params = {
		    				url : '../../../../admin/torch/delFunc.do',
		    				params:{
		    					functionId:functionId
		    				},
		    				callback : function(data, param, res) {
		    					var msg = res.getAttr("del");
		    					if(msg=='success'){
		    						jazz.info("删除成功！",function(){
		    							var treeId = jazz.util.getParameter("treeId");
		    				    		if(treeId){
		    				    			$_this.queryFunctionConfig(treeId);
		    				    		}
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
	    	regDetail:function(id, enttype, opetype, entstatus,entid,entname,regno){
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
	    				+ encode(entstatus) + "&entid=" + encode(entid)+ "&entname=" + encode(entname)+ "&regno=" + encode(regno);
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
	    		for( x in torch.edit_fromNames){
	    				$("div[name='"+torch.edit_fromNames[x]+"']").formpanel("reset");
	    					
	    			} 
    				$('#select').comboxfield('setValue', '1', null);
    				$("div[name='adminbrancode']").comboxfield("option","disabled",true);

	    	},
	    	edit_fromNames : [ 'CFQueryListPanel']
	    	
		};
	
		torch._init();
		return torch;
});
