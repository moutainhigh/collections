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
					    	$_this.queryListUserRolesByUserId();
					    	/*var defaults = $("div[name='ancheyear']").comboxfield('getValue');
					    	alert(defaults);*/
					    	$("div[name='adminbrancode']").comboxfield("option","disabled",true);
					    	
					    	
					    	
					    	
					    	//('#regorg').comboxfield('showOption','22222');
					    	
					    	torch.getSelectBox();
					    	torch.getRegorg();
				    		$("#save_button").off('click').on('click',$_this.queryNBList);
						    $("#reset_button").off('click').on('click',torch.resetEditCondition);
						    $("#add_button").off('click').on('click',$_this.exportFunc);
						   
						    $("div[name='regorg']").comboxfield("option","change",$_this._getAdminbrancode);
						    util.exports('contactDetail',$_this.contactDetail);
						    util.exports('regDetail',$_this.regDetail);
						    $("#toDates").css({"display":"inline-block"});
						    
						   // $("div[name='regorg']").comboxfield('removeOption', '', '');
							//$("div[name='regorg']").comboxfield.removeOption("");
						    
						    
					    });
				});
	    	},
	    	getSelectBox:function(){
	    		$("#select").comboxfield('addOption', '等于', '1');
	    		$("#select").comboxfield('addOption', '包含', '2');
	    		$("div[name='select']").comboxfield('option', 'defaultvalue', 1);
	    	},
	    	
	    	
	    	 //获取当前用户的user_id 获取用户的角色，然后返回到前台判断是否有权限  
	    	queryListUserRolesByUserId : function() {
	    		var wnb  = "";
	    		var params = {
	    				url : '../../yaoxie/queryUserRolesByUserId.do',
	    				callback : function(data, param, res) {
	    				//	console.log(data.data);
	    					 wnb = data.data;
	    					 if(wnb=="success"){
	    						 $("#btnExport").css("display","block");
	    					 }
	    				}
	    		};
	    		$.DataAdapter.submit(params);
			},
	    	/**
	    	 *  导出数据
	    	 */
	    	exportFunc : function(){
	    	
	    		var entname = $("#entnamehidden").hiddenfield('getValue');
	    	    var regno = $("#regnohidden").hiddenfield('getValue');
	    		var regorg = $("#regorghidden").hiddenfield('getValue');
	    		var SpQymark = $("#SpQymarkhidden").hiddenfield('getValue');
	    		var adminbrancode = $("#adminbrancodehidden").hiddenfield('getValue');
	    		var estdate_begin = $("#estdate_beginhidden").hiddenfield('getValue');
	    		var estdate_end = $("#estdate_endhidden").hiddenfield('getValue');
	    		
	    		
	    		var isnotquery = $("#isnotquery").hiddenfield('getValue');
	    		
	    		if(isnotquery!=""){
		    		/*if(adminbrancode123==""){
		    			jazz.warn("请选择所属工商所！");
		    		}else{*/
		    			$_this.post("../../yaoxie/exportExcel.do", {entname:entname,regno:regno,regorg:regorg,SpQymark:SpQymark,adminbrancode:adminbrancode,estdate_begin:estdate_begin,estdate_end:estdate_end});
		    		//}
		    		
	    		}else{
	    			jazz.warn("请先查询后再导出！！");
	    		}
	    		
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
	    	
	    	getRegorg:function(){
	    		var params = {
    				url : '../../yaoxie/code_value.do?type=regorg',
    				callback : function(data, param, res) {
    					var defValue = data.data[0].value;
    					defaut = defValue;
    					for(var i in data.data){ 
    						$("div[name='regorg']").comboxfield('addOption', data.data[i].text, data.data[i].value);
    					}
    					//$("div[name='regorg']").comboxfield('option', 'defaultvalue', defValue);
    				}
    		};
    		$.DataAdapter.submit(params);
	    	},
	    	
	    	
	    	//查询总条数
	    	getCount:function(){
	    	//	 $("#totals").text("");
	    		var params = {
	    				url : '../../yaoxie/yaoxieQyQueryList.do?flag=1',
	    				components: ['SpQyQueryListPanel'],
	    				callback : function(data, param, res) {
	    					$("#totals").html(data.data);
	    				}
	    		};
	    		$.DataAdapter.submit(params);
	    	},
			 /**
			   * 查询失信被执行人信息
			   */
	    	queryNBList : function(){
	    		$("#isnotquery").hiddenfield('setValue',1);
	    		
	    		$("#tips").removeClass("none").css("display","block");
	    		$("#tips").css("display","block");
	    		
	    		
	    		var SpQymark =$('#SpQymark').radiofield('getValue');
	    		

	    		var entname = $("#entname").textfield('getValue');
	    		var regorg = $("#regorg").comboxfield('getValue');
	    		var regno = $("#regno").textfield('getValue');
	    		var adminbrancode = $("#adminbrancode").comboxfield('getValue');
	    		var estdate_begin = $("#estdate_begin").datefield('getValue');
	    		var estdate_end = $("#estdate_end").datefield('getValue');
	    		var SpQymark = $("#SpQymark").radiofield('getValue');
	    		
	    		
	    		$('#entnamehidden').hiddenfield('setValue',entname);
	    		$('#regorghidden').hiddenfield('setValue',regorg);
	    		$('#regnohidden').hiddenfield('setValue',regno);
	    		$('#adminbrancodehidden').hiddenfield('setValue',adminbrancode);
	    		$('#estdate_beginhidden').hiddenfield('setValue',estdate_begin);
	    		$('#estdate_endhidden').hiddenfield('setValue',estdate_end);
	    		$('#SpQymarkhidden').hiddenfield('setValue',SpQymark);
	    		
	    		torch.getCount();
	    		$('#save_button').button('disable');
	    		$("#save_button").off('click');
	    		var timer = null;  
	    		clearTimeout(timer);  
	    			timer = setTimeout(function() { 
	    				$('#save_button').button('enable');
	    	    		$("#save_button").on('click',$_this.queryNBList);
	    		}, 1800);
	    			
		    	//查询失信被执行人信息（DC_BL_LAOLAI），并且有回调函数
	    	   var gridUrl="../../yaoxie/yaoxieQyQueryList.do";
	    		$("#SpQyQueryListGrid").gridpanel("option",'datarender',torch.backFunction);
	    		$("#SpQyQueryListGrid").gridpanel("option",'dataurl',gridUrl);
	    		$("#SpQyQueryListGrid").gridpanel('query', ['SpQyQueryListPanel'],null);
	    		
				
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
	    			if(data[i].entid == null || data[i].entid == undefined){
	    				data[i]["opt"] = "无证";
	    				data[i]["entname"] = '<a href="javascript:void(0)" title="'+data[i]["entname"] +'" onclick="ebaic.regDetail(\''
    					+ data[i]["pripid"]
    					+ '\',\''
    					+ data[i]["type"]
    					+ '\',\''
    					+ data[i]["opetype"]
    					+ '\',\''
    					+ data[i]["regstate"]
		    			+ '\',\''
		    			+ data[i]["entname"]
		    			+ '\',\''
		    			+ data[i]["regno"]
		    			+ '\',\''
						+ data[i]["entid"]
    					+ '\')">' + data[i]["entname"] + ' </a>';
	    				/*data[i]["file"] = '<a href="javascript:void(0)" onclick="ebaic.regfile(\''
							+ data[i]["entid"]
							+ '\')">' + '影像' + ' </a>';*/
	    			}else{
	    			
	    				data[i]["opt"] = '<a href="javascript:void(0);" onclick="ebaic.contactDetail(\''+data[i]["entid"]+'\')">' + "有证" + '</a>';
	    				data[i]["entname"] = '<a href="javascript:void(0)" title="'+data[i]["entname"] +'" onclick="ebaic.regDetail(\''
    					+ data[i]["pripid"]
    					+ '\',\''
    					+ data[i]["enttype"]
    					+ '\',\''
    					+ data[i]["opetype"]
    					+ '\',\''
    					+ data[i]["regstate"]
		    			+ '\',\''
		    			+ data[i]["entname"]
		    			+ '\',\''
		    			+ data[i]["regno"]
		    			+ '\',\''
						+ data[i]["entid"]
    					+ '\')">' + data[i]["entname"] + ' </a>';
    				   /*data[i]["file"] = '<a href="javascript:void(0)" onclick="ebaic.regfile(\''
						+ data[i]["entid"]
						+ '\')">' + '影像' + ' </a>';*/
	    			}
	    		}
	    		return data;
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
    				+ encode(entstatus)+ "&entname=" + encode(entname)+ "&regno=" + encode(regno) + "&entid=" + encode(entid)+"&bregno=" + encode(regno);
    		window.open(url);

	    	},
	    	regfile:function(entid){
	    		var url = "http://aaicweb03/EIMIS/frmShowEntImage.aspx?entid=" + entid;
	    		window.open(url);
	    	},
	    	/**
	    	 * 打开企业或者个体报表信息页面
	    	 */
	    	NBDetail:function(pripid,ancheid){ 
	    		var isGtOrQe =$('#entmarkhidden').hiddenfield('getValue');
	    		
					if(isGtOrQe==0){
						var url = $_this.getContextPath()+"/page/comselect/nbEntDetail.jsp?id="+encode(ancheid)+"&pripid="+encode(pripid)+"&isGtOrQe="+isGtOrQe+"&type=gt";
		    		}
		    		if(isGtOrQe==1){
		    			var url = $_this.getContextPath()+"/page/comselect/nbEntDetail.jsp?id="+encode(ancheid)+"&pripid="+encode(pripid)+"&isGtOrQe="+isGtOrQe+"&type=qy";
		    		}
				//	util.openWindow("NBQueryDetail","年报详情信息",$_this.getContextPath()+"/page/comselect/NBQueryDetail.html?pripid="+pripid+"&isGtOrQe="+isGtOrQe,680,392);
	    		window.open(url);
	    	},
	    	contactDetail:function(entid){ 
	    		util.openWindow("yaoxieyzQueryList","药品企业许可证信息",$_this.getContextPath()+"/page/yaopin/yaoxieyzQueryList.html?entid="+entid,850,412);
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
	    				$('#SpQymark').radiofield('setValue','0');
	    				$("div[name='adminbrancode']").comboxfield("option","disabled",true);
	    				/*$('#nbmark').radiofield('setValue','1');
	    				$('#ancheyear').comboxfield('option', 'defaultvalue', defaut);*/
	    			
	    	},
	    	_getAdminbrancode:function(){
	    		//$("div[name='adminbrancode']").comboxfield("reset");
	    		/*var json = [{}];
	    		$("div[name='adminbrancode']").comboxfield("option","dataurl",json);
	    		$("div[name='adminbrancode']").comboxfield("option","dataurl",json);
	    		$("div[name='adminbrancode']").comboxfield("reload");*/
	    		
	    		
	    		
	    		
	    		$("div[name='adminbrancode']").comboxfield("option","disabled",true);
	    		var text = $('div[name="regorg"]').comboxfield('getValue');
	    		//$("div[name='adminbrancode']").comboxfield("option","dataurl","../../SpQySelect/code_value.do?type=adminbrancode&param="+text)
	    		//$("div[name='adminbrancode']").comboxfield("reload");
	    		
	    		if(text!=""){
	    			$("div[name='adminbrancode']").comboxfield("option","disabled",false);
	    			$("div[name='adminbrancode']").comboxfield("option","dataurl","../../SpQySelect/code_value.do?type=adminbrancode&param="+text)
		    		$("div[name='adminbrancode']").comboxfield("reload");
		  /*  		var params = {
		    				url : '../../SpQySelect/code_value.do',
		    				params:{
		    					type:"adminbrancode",
		    					param:text
		    				},
		    				callback : function(data, param, res) {
		    					var defValue = data.data[0].value;
		    					defaut = defValue;
		    					//$("div[name='adminbrancode']").comboxfield('addOption', "","");
		    					for(var i in data.data){
		    						$("div[name='adminbrancode']").comboxfield('addOption', data.data[i].text, data.data[i].value);
		    					}
		    					//$("div[name='adminbrancode']").comboxfield('option', 'defaultvalue', defValue);
		    				}
		    		};
		    		$.DataAdapter.submit(params);*/
	    		}
	    	},
	    	
	    	
	    	edit_fromNames : [ 'SpQyQueryListPanel']
	    	
		};
	
		torch._init();
		return torch;
});
