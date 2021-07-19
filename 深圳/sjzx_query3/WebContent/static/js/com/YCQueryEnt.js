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
					    	var entname = decode(jazz.util.getParameter("entname"));
					    	$('#entname').html(entname);
					    	$("#infos").css("display","block");
					    	document.title = entname +"的异常名录信息";
					    	var entid = jazz.util.getParameter("entid");
				    		var gridUrl="../../ycselect/YCQueryList.do";
				    		$("#CFQueryListGrid").gridpanel("option",'datarender',torch.backFunction);
				    		$("#CFQueryListGrid").gridpanel("option",'dataurl',gridUrl);
				    		$("#CFQueryListGrid").gridpanel("option", "dataurlparams", {
				    			 "entid":entid
				    		});
				    		$("#CFQueryListGrid").gridpanel('reload'); 
						    
						    util.exports('regDetail',$_this.regDetail);
						    util.exports('editFunc',$_this.editFunc);
					    });
				});
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
//	    			data[i]["entname"] = '<a href="javascript:void(0)" title="'+data[i]["entname"] +'" onclick="ebaic.regDetail(\''
//	    					+ data[i]["pripid"]
//	    					+ '\',\''
//	    					+ data[i]["type"]
//	    					+ '\',\''
//	    					+ data[i]["opetype"]
//	    					+ '\',\''
//	    					+ data[i]["entstatus"]
//			    			+ '\',\''
//							+ data[i]["entid"]
//	    					+ '\')">' + data[i]["entname"] + ' </a>';
	    			if(data[i]["createtime"]){
	    				data[i]["createtime"] = data[i]["createtime"].substring(0,10);
	    			}
	    			/*data[i]["opt"] = '<a href="javascript:void(0)" onclick="ebaic.editFunc(\''
	    					+ data[i]["pripid"]
	    					+ '\',\''
	    					+ data[i]["entname"]
	    					+ '\',\''
			    			+ data[i]["regno"]
	    					+ '\',\''
	    					+ data[i]["abnormaltype"]
	    					+ '\',\''
	    					+ data[i]["createtime"]
	    					+ '\',\''
	    					+ data[i]["resoleunit"]
	    					+ '\',\''
	    					+ data[i]["removetypecn"]
	    					+ '\',\''
	    					+ data[i]["removetime"]
	    					+ '\',\''
	    					+ data[i]["removedept"]
	    					+ '\',\''
	    					+ data[i]["remark"]
	    					+ '\',\''
	    					+ data[i]["btype"]
	    					+ '\',\''
	    					+ data[i]["publictime"]
			    			+ '\',\''
							+ data[i]["type"]
			    			+ '\',\''
							+ data[i]["opetype"]
			    			+ '\',\''
							+ data[i]["entstatus"]
			    			+ '\',\''
							+ data[i]["entid"]
	    					+'\')">' + "详情" + '</a>'
	    		}*/
	    		data[i]["opt"] = '<a href="javascript:void(0)" onclick="ebaic.editFunc(\''
	    			+ data[i]["id"]
    				+ '\',\''
					+ data[i]["entid"]
					+'\')">' + "详情" + '</a>'
		}
	    		return data;
	    	},
	    	
	    	/**
	    	 * 弹出editFunctionConfig.html页面
	    	 */
	    	/*editFunc:function(sExtSequence,entname,regno,abnormaltype,createtime,resoleunit,removetypecn,removetime,removedept,remark,btype,publictime,type,opetype,entstatus,entid){
	    		util.openWindow("SXQueryDetail","异常名录详情信息",$_this.getContextPath()+"/page/comselect/YCQueryDetail.html?sExtSequence="+sExtSequence
	    				+"&entname="+encode(entname)+"&regno="+encode(regno)+"&abnormaltype="+encode(abnormaltype)+"&createtime="+encode(createtime)+"&resoleunit="+encode(resoleunit)
	    				+"&removetypecn="+encode(removetypecn)+"&removetime="+encode(removetime)
	    				+"&removedept="+encode(removedept)+"&remark="+encode(remark)
	    				+"&btype="+encode(btype)+"&publictime="+encode(publictime)
	    				+"&type="+encode(type)+"&opetype="+encode(opetype)+"&entstatus="+encode(entstatus)+"&entid="+encode(entid),900,500);
	
	    	}, */
	    	
	    	editFunc:function(id,entid){
	    		util.openWindow("SXQueryDetail","异常名录详情信息",$_this.getContextPath()+"/page/comselect/YCQueryDetail.html?id="+id+"&entid="+entid,900,500);
	    		/*var url = $_this.getContextPath()+"/mgr/torch/sysconfig/editFunctionConfig.html?functionId="+functionId;
	    		window.location.href=url;*/
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
