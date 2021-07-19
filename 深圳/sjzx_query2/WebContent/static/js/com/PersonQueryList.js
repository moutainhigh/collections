define(['jquery', 'jazz','util' ], function($, jazz,util){
 var torch ={};
	torch = {
		win:"",
		/**
		 * 模块初始化
		 */
			_init: function(){
				$_this = this;
				require(['domReady'], function (domReady) {
					    domReady(function () {
					    	$_this.getSelectBox();//加载下拉框
					    	$_this.queryListUserRolesByUserId();
					    	$("#exportData").css("display","block");
                               //初始化列表数据
					    	//;
					    /*	var gridUrl="../../Personselect/PersonQueryList.do";
				    		$("#PersonQueryListGrid").gridpanel("option",'datarender',torch.backFunction);
				    		$("#PersonQueryListGrid").gridpanel("option",'dataurl',gridUrl);
				    		$("#PersonQueryListGrid").gridpanel('query', ['PersonQueryListPanel'],null);*/
				    		
				    		$("#save_button").off('click').on('click',$_this.queryPersonList);
						    $("#reset_button").off('click').on('click',torch.resetEditCondition);
						   /* $("#battchUpload").off('click').on('click',torch.battchUpload);
						    $(".jazz-toolbar-element").eq(2).addClass("disapper");*/
						    
						    /*$("#batchQuery").off('click').on('click',torch.batchQuery);
						    $("#batchExport").off('click').on('click',torch.batchExport);*/
							$(".btns").css("display","block");
						    util.exports('regDetail',$_this.regDetail);
						    util.exports('regfile',$_this.regfile);
						    util.exports('BGDetail',$_this.BGDetail);
						    util.exports('viewDec',$_this.viewDec);
					    });
				});
	    	},
	    	
	    	//获取当前用户的user_id 获取用户的角色，然后返回到前台判断是否有权限  
	    	queryListUserRolesByUserId : function() {
	    		var pl  = "";
	    		var params = {
	    				url : '../../readTxt/QueryBatchPerson.do',
	    				callback : function(data, param, res) {
	    				//	console.log(data.data);
	    					pl = data.data;
	    					 if(pl=="success"){
	    						/* $(".jazz-toolbar-element").eq(2).removeClass("disapper");*/
	    						 $("#form1").removeClass("hide");
	    					 }
	    				}
	    		};
	    		$.DataAdapter.submit(params);
			},
	    	getCount:function(){
	    		var params = {
	    				url : '../../readTxt/PersonQueryList.do?flag=1',
	    				components: ['PersonQueryListPanel'],
	    				callback : function(data, param, res) {
	    					$("#totals").html(data.data);
	    				}
	    		};
	    		$.DataAdapter.submit(params);
	    	},
			 /**
			   * 查询失信被执行人信息
			   */
	    	queryPersonList : function(){
	    		//var lerep = $('#lerep').textfield("getValue");
	    		var cerno = $('#cerno').textfield("getValue");
	    		var persname = $('#persname').textfield("getValue");
				if (!persname && !cerno) {
					jazz.warn("请输入姓名或身份证号码！");
					return false;
				}
				if(cerno && $.trim(cerno).length !=18 && $.trim(cerno).length !=15){
					jazz.warn("请输入合法的身份证号码！");
					return false;
				}
				
				$('#save_button').button('disable');
	    		$("#save_button").off('click');
	    		var timer = null;  
	    		clearTimeout(timer);  
	    			timer = setTimeout(function() { 
	    				$('#save_button').button('enable');
	    	    		$("#save_button").on('click',$_this.queryPersonList);
	    		}, 1800);
				
				
				/*if (cerno && !cerno) {
					jazz.warn("请输入证照号码！");
					return false;
				}*/
	    	
             //    torch.getCount();
	    	//	$("#tips").css("display","block");
		    		//查询失信被执行人信息（DC_BL_LAOLAI），并且有回调函数
	    		var gridUrl="../../readTxt/PersonQueryList.do";
	    		$("#PersonQueryListGrid").gridpanel("option",'datarender',torch.backFunction);
	    		$("#PersonQueryListGrid").gridpanel("option",'dataurl',gridUrl);
	    		$("#PersonQueryListGrid").gridpanel('query', ['PersonQueryListPanel'],null);
				
			},
			/**
			 * SYS_FUNCTION_CONFIG列表回调函数
			 */
			backFunction:function (event, obj) {
	    		var data = obj.data;
	    		if (data.length == 0) {
	    			$(".nodata").css("display", "block");
	    			//$("div[name='grid_paginator']").css("display", "none");
	    			jazz.warn("该查询没有精确匹配到商事主体");
	    		} else {
	    			$(".nodata").css("display", "none");
	    		}
	    		/*for (var i = 0; i < data.length; i++) {
	    			data[i]["opt"] = '<a href="javascript:void(0);" onclick="ebaic.editFunc(\''+data[i].sExtSequence+'\')">' + "详情" + '</a>'
	    						+'&nbsp;&nbsp;<a href="javascript:void(0);" onclick="ebaic.configEditOrQuery(\''+data[i].functionId+'\')">' + "配置" + '</a>'
	    	 					+'&nbsp;&nbsp;<a href="javascript:void(0);" onclick="ebaic.delFunc(\''+data[i].functionId+'\')">' + "删除" + '</a>'
	    	 					;
	    		}	*/
	    		
	    		for (var i = 0; i < data.length; i++) {
	    			data[i]["entname"] = '<a href="javascript:void(0)" title="'+data[i]["entname"] +'" onclick="ebaic.regDetail(\''
	    					+ data[i]["pripid"]
	    					+ '\',\''
	    					+ data[i]["type"]
	    					+ '\',\''
	    					+ data[i]["opetype"]
	    					+ '\',\''
	    					+ data[i]["entname"]
	    					+ '\',\''
	    					+ data[i]["regno"]
	    					+ '\',\''
	    					+ data[i]["status"]
			    			+ '\',\''
							+ data[i]["entid"]
	    					+ '\')">' + data[i]["entname"] + ' </a>';
	    			data[i]["estdate"] = data[i]["estdate"].substring(0,10);
	    			if(data[i]["typeFlag"] == '是'){
		    				data[i]["typeFlag"] = '<a href="javascript:void(0)" title="'+data[i]["typeFlag"] +'" onclick="ebaic.BGDetail(\''
	    					+ data[i]["pripid"]
		    				+ '\',\''
	    					+ data[i]["cerno"]
	    					+ '\',\''
	    					+ data[i]["perflag"]
	    					+ '\')">' + '历史' + ' </a>';
	    			}
	    			var cerNo = "";
	    			if($_this.isNotEmpty(data[i]["cerno"])){
						cerNo = data[i]["cerno"];
						data[i]["cerno"] = '<div class="cerno"><a href="javascript:void(0);" data-cerno="'+cerNo+'" onclick="ebaic.viewDec(this'+','+ "'"+ data[i]["cerno"]+"'"+');">' + "查看(该操作会记录日志)" + '</a></div>';
					}else{
						
					}
	    			
	    		}
	    		$("#exportData").css("display","block")
	    		return data;
	    	},
	    	isEmpty:function (val) {
	    		val = $.trim(val);
	    		if (val == null)
	    			return true;
	    		if (val == undefined || val == 'undefined')
	    			return true;
	    		if (val == "")
	    			return true;
	    		if (val.length == 0)
	    			return true;
	    		if (!/[^(^\s*)|(\s*$)]/.test(val))
	    			return true;
	    		return false;
	    	},
	    	isNotEmpty:function (val) {
	    		return !$_this.isEmpty(val);
	    	},
	    	
	    	viewDec:function(obj,name){
	    		var _this = $(obj);
	    		var cerno = _this.data("cerno");
	    		var rootPath = $_this.getContextPath();
	    		$.ajax({
		    			url:rootPath+'/reg/showCerno.do',
		    			data:{
		    				flag : "查询法定代表人证照号码",
		    			},
		    			type:"post",
		    			dataType : 'json',
		    			success:function(data){
		    				if(data.code!=0){
		    					alert(data.msg);
		    				}else{
		    					_this.parent().html(cerno);
		    				}
		    			}
		    		});
	    	},
	    	
	    	BGDetail:function(id,cerno,perflag){
	    		var flag = '';
	    		if(perflag == "高管人员"){	    			
	    			flag = 1;
	    			var url = $_this.getContextPath()+"/page/reg/nzwz/nzwz_biangengxx.jsp?priPid=" + id +"&entityNo=" + '2305' +"&sign=" + flag;
	    		}else if(perflag == "股东"){
	    			flag = 2;
	    			var url = $_this.getContextPath()+"/page/reg/nzwz/nzwz_biangengxx.jsp?priPid=" + id +"&entityNo=" + '2305' +"&sign=" + flag;
	    		}else if(perflag == "法定代表人"){
	    			flag = 3;
	    			var url = $_this.getContextPath()+"/page/reg/nzwz/nzwz_biangengxx.jsp?priPid=" + id +"&entityNo=" + '2305' +"&sign=" + flag;
	    		}
	    		window.open(url);
	    	},
	    	regfile:function(entid){
	    		var url = "http://aaicweb03/EIMIS/frmShowEntImage.aspx?entid=" + entid;
	    		window.open(url);
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
	    	regDetail:function(id, enttype, opetype,entname,regno, entstatus, entid){
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
	    				+ encode(entstatus)+ "&entname=" + encode(entname) + "&regno="
	    				+ encode(regno) + "&entid=" + encode(entid)+"&bregno="+encode(regno);
	    		
	    		
	    		
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
	    	getSelectBox:function(){
	    		$("#select").comboxfield('addOption', '当前', '1');
	    		$("#select").comboxfield('addOption', '包含历史', '0');
	    		$("div[name='select']").comboxfield('option', 'defaultvalue', 1);
	    	},
	    	//批量上传文件
	    	battchUpload:function(){
	    		$("#myForms").slideDown();
	    		$("#exportData").animate({"top":"230"},200);
	    	},
	    	/**
	    	 * 重置方法
	    	 */
	    	resetEditCondition : function(){
	    				for( x in torch.edit_fromNames){
	    					$("div[name='"+torch.edit_fromNames[x]+"']").formpanel("reset");
	    				} 
	    				$("div[name='select']").comboxfield('option', 'defaultvalue', 1);
	    	},
/*	    	batchQuery:function(){
	    		torch.createWindow("uploadWin", "请选择上传的文件")
	    		console.log(win)
	    	},
	    	batchExport:function(){
	    		alert(2)
	    	},
	    	createWindow:function(name,title){
	    		 win = top.jazz.widget({ 
	    		        vtype: 'window', 
	    		        name: 'win', 
	    		        title: title, 
	    		        width: 550, 
	    		        height: 350, 
	    		        modal:true, 
	    		        visible: true ,
	    				showborder : true, //true显示窗体边框    false不显示窗体边
	    				closestate: false,
	    				minimizable : true, //是否显示最小化按钮
	    				frameurl: "../../page/comselect/bathPerson.html"
	    		    }); 
	    	},
	    	colseWin:function(){
	    		win.window('close'); 
	    	},
*/	    	
	    	edit_fromNames : [ 'PersonQueryListPanel']
		};
	
		torch._init();
		return torch;
});
