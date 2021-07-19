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
					    	var id = jazz.util.getParameter("id")||"";
					    	/*var sExtSequence = jazz.util.getParameter("sExtSequence")||"";
					    	var entname = decode(jazz.util.getParameter("entname"));
					    	var type = decode(jazz.util.getParameter("type"));
					    	var opetype = decode(jazz.util.getParameter("opetype"));					    	
					    	var entstatus = decode(jazz.util.getParameter("entstatus"));
					    	var regno = decode(jazz.util.getParameter("regno"));
					    	var entid = decode(jazz.util.getParameter("entid"));
					    	if(entname == "undefined"){
					    		entname = "";
					    	}else{
					    		entname = '<a href="javascript:void(0)" title="'+entname +'" onclick="ebaic.regDetail(\''
		    					+ sExtSequence
		    					+ '\',\''
		    					+ type
		    					+ '\',\''
		    					+ opetype
		    					+ '\',\''
		    					+ entstatus
		    					+ '\',\''
		    					+ entname
		    					+ '\',\''
		    					+ regno
		    					+ '\',\''
		    					+ entid
		    					+ '\')">' + entname + ' </a>';
					    	}
					    	var regno = decode(jazz.util.getParameter("regno"));
					    	if(regno == "undefined"){
					    		regno = "";
					    	}
					    	var abnormaltype = decode(jazz.util.getParameter("abnormaltype"));
					    	if(abnormaltype == "undefined"){
					    		abnormaltype = "";
					    	}
					    	var createtime = decode(jazz.util.getParameter("createtime"));
					    	if(createtime == "undefined"){
					    		createtime = "";
					    	}else{
					    		createtime = createtime.substring(0,10);
					    	}
					    	var resoleunit = decode(jazz.util.getParameter("resoleunit"));
					    	if(resoleunit == "undefined"){
					    		resoleunit = "";
					    	}
					    	var removetypecn = decode(jazz.util.getParameter("removetypecn"));
					    	if(removetypecn == "undefined"){
					    		removetypecn = "";
					    	}
					    	var removetime = decode(jazz.util.getParameter("removetime"));
					    	if(removetime == "undefined"){
					    		removetime = "";
					    	}else{
					    		removetime = removetime.substring(0,10);
					    	}
					    	var removedept = decode(jazz.util.getParameter("removedept"));
					    	if(removedept == "undefined"){
					    		removedept = "";
					    	}
					    	var remark = decode(jazz.util.getParameter("remark"));
					    	if(remark == "undefined"){
					    		remark = "";
					    	}
					    	var btype = decode(jazz.util.getParameter("btype"));
					    	if(btype == "undefined"){
					    		btype = "";
					    	}
					    	var publictime = decode(jazz.util.getParameter("publictime"));
					    	if(publictime == "undefined"){
					    		publictime = "";
					    	}else{
					    		publictime = publictime.substring(0,10);
					    	}
					    	$('#entname').textfield('setValue',entname);
					    	$('#regno').textfield('setValue',regno);
					    	$('#abnormaltype').textfield('setValue',abnormaltype);
					    	$('#createtime').datefield('setValue',createtime);
					    	$('#resoleunit').textfield('setValue',resoleunit);
					    	$('#removetypecn').textfield('setValue',removetypecn);
					    	$('#removetime').datefield('setValue',removetime);
					    	$('#removedept').textfield('setValue',removedept);
					    	$('#remark').textfield('setValue',remark);
					    	$('#btype').textfield('setValue',btype);
					    	$('#publictime').datefield('setValue',publictime);*/
				    		if(id){
				    			$_this.queryFunctionConfig(id);
				    		}
					    	$("#back_button").off('click').on('click',$_this.goBack);
			    	   	 	$("tr:even").css({background:"#EFF6FA"});
			    		 	$("tr:odd").css({background:"#FBFDFD"});
			    		 	
			    		 	$(".jazz-field-comp-input").attr("disabled","disabled");
			    		 	
			    		 	util.exports('regDetail',$_this.regDetail);
					    });
				});
	    	},
	    	
	    	/**
	    	 * 弹出editFunctionConfig.html页面
	    	 */
	    	regDetail:function(id, enttype, opetype, entstatus, entname, regno, entid){
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
	    				+ encode(entstatus)+ "&entname="
	    				+ encode(entname)+ "&regno="
	    				+ encode(regno)+ "&entid="
	    				+ encode(entid);
	    		window.open(url);

	    	},
			 /**
				 * 根据sExtSequence查询查询失信被执行人信息（DC_BL_LAOLAI）
				 */
			 queryFunctionConfig : function(id){
			
				    var panelUrl="../../ycselect/getQueryById.do?sExtSequence="+id;
	    			$("#querySXDetialPanel").formpanel("option","dataurl",panelUrl);
	    			$("#querySXDetialPanel").formpanel("reload");
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
	    			data[i]["opt"] = '<a href="javascript:void(0);" onclick="ebaic.editFunc(\''+data[i].functionId+'\')">' + "编辑" + '</a>'
	    						+'&nbsp;&nbsp;<a href="javascript:void(0);" onclick="ebaic.configEditOrQuery(\''+data[i].functionId+'\')">' + "配置" + '</a>'
	    	 					+'&nbsp;&nbsp;<a href="javascript:void(0);" onclick="ebaic.delFunc(\''+data[i].functionId+'\')">' + "删除" + '</a>';
	    		}	
	    		return data;
	    	},
	    	
	    	/**
	    	 * 清除指定funcConfig
	    	 */
	    	delFunc:function(functionId){
	    		/*jazz.info("对不起，您的权限不足，请联系管理员！");
	    		return;*/
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
	    	editFunc:function(functionId){
	    		util.openWindow("editFunctionConfig","编辑功能信息","editFunctionConfig.html?functionId="+functionId,1000,350);
	    		/*var url = $_this.getContextPath()+"/mgr/torch/sysconfig/editFunctionConfig.html?functionId="+functionId;
	    		window.location.href=url;*/
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
	    		/*var url = $_this.getContextPath()+"/mgr/torch/sysconfig/functionConfig.html";
	    		window.location.href=url;*/
	    	},
	    	//html中获取当前页的 上下文路径
	    	getContextPath: function (){
	    		var pathName = document.location.pathname;//  /page/mgr/torch/sysconfig/functionList.html
	    		var index = pathName.substr(1).indexOf("/");//  4
	    		var result = pathName.substr(0,index+1);//    /page
//	    		alert(pathName);
	    		return result;
	    	},
	    	goBack:function (){
	    		history.go(1);
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
	    	
		};
	
		torch._init();
		return torch;
});
