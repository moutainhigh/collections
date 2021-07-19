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
					    	torch.getYear();
					    	torch.getRegorg();
					    	
					        $("div[name='regorg']").comboxfield("option","change",$_this._getAdminbrancode);
				    		$("#save_button").off('click').on('click',$_this.queryNBList);
						    $("#reset_button").off('click').on('click',torch.resetEditCondition);
						    $("#add_button").off('click').on('click',$_this.exportFunc);
							
						    
						    util.exports('NBDetail',$_this.NBDetail);
						    util.exports('contactDetail',$_this.contactDetail);
						    $("#toDates").css({"display":"inline-block"});
						    
						    
					    });
				});
	    	},
	    	
	   	 //获取当前用户的user_id 获取用户的角色，然后返回到前台判断是否有权限  
	    	queryListUserRolesByUserId : function() {
	    		var wnb  = "";
	    		var params = {
	    				url : '../../NBselect/queryUserRolesWNB.do',
	    				callback : function(data, param, res) {
	    				//	console.log(data.data);
	    					 wnb = data.data;
	    					 if(wnb=="success"){
	    						 $("#btnExport").removeClass("display");
	    						 torch.getRadio(wnb);
	    					 }
	    				}
	    		};
	    		$.DataAdapter.submit(params);
			},
	    	
	    	getRadio:function(wnb){
	    			
	    			$("#nbmark").find(".jazz-checkbox-label,.jazz-checkbox").click(function(){
		    			var _this = $(this);
		    			var index  = _this.parents(".jazz-checkbox-item").attr("index");
		    			if(index==1){
		    				$("#btnExport").addClass("display");
		    			}else{
		    				$("#btnExport").removeClass("display");
		    			}
		    		});
	    	},
	    	/**
	    	 *  导出数据
	    	 */
	    	exportFunc : function(){
	    	
	    		var adminbrancode123 = $("#adminbrancode").comboxfield('getValue');
	    		var entname = $("#entnamehidden").hiddenfield('getValue');
	    		var regorg = $("#regorghidden").hiddenfield('getValue');
	    		var ancheyear = $("#ancheyearhidden").hiddenfield('getValue');
	    		var adminbrancode = $("#adminbrancodehidden").hiddenfield('getValue');
	    		var entmark = $("#entmarkhidden").hiddenfield('getValue');
	    		var nbmark = $("#nbmarkhidden").hiddenfield('getValue');
	    		var yeWork = $("#yeWorkhidden").hiddenfield('getValue');
	    		
	    		
	    		var isnotquery = $("#isnotquery").hiddenfield('getValue');
	    		$('#add_button').button('disable'); 
	    		$("#add_button").off('click');
	    		if(isnotquery!=""){
		    		if(adminbrancode123==""){
		    			jazz.warn("请选择所属监管所！");
		    			$("#add_button").on('click',$_this.exportFunc);
		    			$('#add_button').button('enable'); 
		    		}else{
		    			$_this.post("../../NBselect/exportExcel.do", {entname:entname,regorg:regorg,ancheyear:ancheyear,adminbrancode:adminbrancode,entmark:entmark,nbmark:nbmark,yeWork:yeWork});
		    		}
		    		
	    		}else{
	    			$('#add_button').button('enable'); 
	    			$("#add_button").on('click',$_this.exportFunc);
	    			jazz.warn("请先查询后再导出！！");
	    		}
	    		
	    	},
	    	post : function(URL, PARAMS) {  
	    		/*console.log(PARAMS)
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
	    	    temp.submit();  */
	    		
	    		//使用异步提交表单,统计当前结果数，同时回调成功的时候，让文件实现下载
	    		var  entname   = PARAMS.entname;
	    		var regorg     = PARAMS.regorg;  
	    		var ancheyear  = PARAMS.ancheyear;  
	    		var adminbrancode= PARAMS.adminbrancode;
	    		var entmark  = PARAMS.entmark;    
	    		var nbmark    = PARAMS.nbmark;   
	    		var yeWork  = PARAMS.yeWork;     
	    		var params = {
	    				url : URL,
	    				params:PARAMS
	    				/*{
	    					entname:entname,
	    					regorg:regorg,
	    					ancheyear:ancheyear,
	    					adminbrancode:adminbrancode,
	    					entmark:entmark,
	    					nbmark:nbmark,
	    					yeWork:yeWork
	    				}*/,
	    				callback : function(data, param, res) {
	    					var counts = data.data;
	    					jazz.confirm('由于您导出的数据共'+counts+'条，导出时间可能较长。请耐心等待！',function(){
	    						$_this.getFilesToDowns("../../NBselect/exportExcelDowns.do", PARAMS);
			    			},function(){
			    				$("#add_button").on('click',$_this.exportFunc);
				    			$('#add_button').button('enable'); 
			    			});
	    				}
	    		};
	    		$.DataAdapter.submit(params);
	    	},
	    	getFilesToDowns:function(URL, PARAMS){
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
	    	   /* setTimeout(() => {
	    	    	$("#add_button").on('click',$_this.exportFunc);
	    			$('#add_button').button('enable'); 
				}, 5000);*/
	    	    setTimeout(function() {
	    	    	$("#add_button").on('click',$_this.exportFunc);
	    			$('#add_button').button('enable'); 
				}, 5000);
	    	},
	    	
	    	getYear:function(){
	    		var params = {
    				url : '../../NBselect/code_value.do?type=ancheyear',
    				callback : function(data, param, res) {
    					var defValue = data.data[0].value;
    					defaut = defValue;
    					for(var i in data.data){ 
    						$("div[name='ancheyear']").comboxfield('addOption', data.data[i].text, data.data[i].value);
    					}
    					$("div[name='ancheyear']").comboxfield('option', 'defaultvalue', defValue);
    				}
    		};
    		$.DataAdapter.submit(params);
	    	},
	    	
	    	getRegorg:function(){
	    		var params = {
    				url : '../../NBselect/code_value.do?type=regorg',
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
	    	_getAdminbrancode:function(){
	    		//$("div[name='adminbrancode']").comboxfield("reset");
	    		/*var json = [{}];
	    		$("div[name='adminbrancode']").comboxfield("option","dataurl",json);
	    		$("div[name='adminbrancode']").comboxfield("option","dataurl",json);
	    		$("div[name='adminbrancode']").comboxfield("reload");*/
	    		
	    		$("div[name='adminbrancode']").comboxfield("option","disabled",true);
	    		var text = $('div[name="regorg"]').comboxfield('getValue');
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
	    	
	    	
	    	//查询总条数
	    	getCount:function(){
	    		 $("#totals").text("") 
	    		var params = {
	    				url : '../../NBselect/NBQueryList.do?flag=1',
	    				components: ['NBQueryListPanel'],
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
	    		$('#isnotquery').hiddenfield('setValue',1);
	    	
	    		$("#tips").removeClass("none").css("display","block");
	    		$("#tips").css("display","block");
	    		
	    		var entname = $("#entname").textfield('getValue');
	    		var regorg = $("#regorg").comboxfield('getValue');
	    		var ancheyear = $("#ancheyear").comboxfield('getValue');
	    		var adminbrancode = $("#adminbrancode").comboxfield('getValue');
	    		var entmark = $("#entmark").radiofield('getValue');
	    		var nbmark = $("#nbmark").radiofield('getValue');
	    		var yeWork = $("#yeWork").radiofield('getValue');
	    		
	    		$('#entnamehidden').hiddenfield('setValue',entname);
	    		$('#regorghidden').hiddenfield('setValue',regorg);
	    		$('#ancheyearhidden').hiddenfield('setValue',ancheyear);
	    		$('#adminbrancodehidden').hiddenfield('setValue',adminbrancode);
	    		$('#entmarkhidden').hiddenfield('setValue',entmark);
	    		$('#nbmarkhidden').hiddenfield('setValue',nbmark);
	    		$('#yeWorkhidden').hiddenfield('setValue',yeWork);
					torch.getCount();
	    		
	    		$('#save_button').button('disable');
	    		$("#save_button").off('click');
	    		var timer = null;  
	    		clearTimeout(timer);  
	    			timer = setTimeout(function() { 
	    				$('#save_button').button('enable');
	    	    		$("#save_button").on('click',$_this.queryNBList);
	    		}, 1800);
	    		
	    			
	    			
	    		/*	var nbmark =$('#nbmark').radiofield('getValue');
		    		if (nbmark==0) {
		    			$('div[name="NBQueryListGrid"]').gridpanel('destroyComp');
		    			var _html  ="";
		    			_html+="<div>"+
		    			"<div name='regno' text='统一社会信用代码/注册号' textalign='left' width='15%'></div>"+
		    			"<div name='entname' text='商事主体名称' textalign='left' width='25%'></div>"+
		    			"<div name='enttype' text='商事主体类型' textalign='left' width='25%' dataurl='../../NBselect/code_value.do?type=enttype'></div>"+
		    			"<div name='estdate' text='成立日期' datatype='date' dataformat='YYYY-MM-DD' textalign='left'></div>"+
		    			"<div name='reccap' text='认缴资本总额（万元）' width='13%' textalign='right'></div>"+
		    			"<div name=contact text='联系方式' textalign='center' ></div></div>";
		    			
		    			$('div[name="NBQueryListGridColumn"]').children().remove();
		    			$('div[name="NBQueryListGridColumn"]').append(_html);
		    			//3.根据options重新创建gridpanel
		    	    	$('div[name="NBQueryListGrid"]').parseComponent();
	    			
		    		}else{
		    			$('div[name="NBQueryListGrid"]').gridpanel('destroyComp');
		    			var _html="";
		    			_html+="<div>"+
		    			"<div name='regno' text='统一社会信用代码/注册号' textalign='left' width='15%'></div>"+
		    			"<div name='entname' text='商事主体名称' textalign='left' width='25%'></div>"+
		    			"<div name='enttype' text='商事主体类型' textalign='left' width='25%' dataurl='../../NBselect/code_value.do?type=enttype'></div>"+
		    			"<div name=contact text='联系方式' textalign='center' width='7%'></div>"+
		    			"<div name='estdate' text='成立日期' datatype='date' dataformat='YYYY-MM-DD' textalign='left'></div>"+
		    			"<div name='reccap' text='认缴资本总额（万元）' width='13%' textalign='right'></div>"+
		    			"<div name='opt' text='操作' textalign='center'></div></div>";
		    			
		    			$('div[name="NBQueryListGridColumn"]').children().remove();
		    			$('div[name="NBQueryListGridColumn"]').append(_html);
		    			//3.根据options重新创建gridpanel
		    	    	$('div[name="NBQueryListGrid"]').parseComponent();
		    		}*/
	    			
	    			
		    	//查询失信被执行人信息（DC_BL_LAOLAI），并且有回调函数
	    		var gridUrl="../../NBselect/NBQueryList.do";
	    		$("#NBQueryListGrid").gridpanel("option",'datarender',torch.backFunction);
	    		$("#NBQueryListGrid").gridpanel("option",'dataurl',gridUrl);
	    		$("#NBQueryListGrid").gridpanel('query', ['NBQueryListPanel'],null);
				
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
	    		
	    		var nbmark =$('#nbmark').radiofield('getValue');
	    		if (nbmark==0) {
	    			for (var i = 0; i < data.length; i++) {
		    		/*	data[i]["opt"] = '<a href="javascript:void(0);" onclick="ebaic.NBDetail(\''
		    									+data[i].pripid
		    									+ '\',\''
		    									+ data[i].ancheid
		    									+'\')">' + "年报详情" + '</a>'
		    						+'&nbsp;&nbsp;<a href="javascript:void(0);" onclick="ebaic.configEditOrQuery(\''+data[i].functionId+'\')">' + "配置" + '</a>'
		    	 					+'&nbsp;&nbsp;<a href="javascript:void(0);" onclick="ebaic.delFunc(\''+data[i].functionId+'\')">' + "删除" + '</a>'
		    	 					;*/
		    			data[i]["contact"] = '<a href="javascript:void(0);" onclick="ebaic.contactDetail(\''+data[i].id+'\')">' + "查看" + '</a>';
	    			}
	    		}else{	
		    		for (var i = 0; i < data.length; i++) {
		    			data[i]["opt"] = '<a href="javascript:void(0);" onclick="ebaic.NBDetail(\''
		    									+data[i].pripid
		    									+ '\',\''
		    									+ data[i].ancheid
		    									+'\')">' + "年报详情" + '</a>'
		    						/*+'&nbsp;&nbsp;<a href="javascript:void(0);" onclick="ebaic.configEditOrQuery(\''+data[i].functionId+'\')">' + "配置" + '</a>'
		    	 					+'&nbsp;&nbsp;<a href="javascript:void(0);" onclick="ebaic.delFunc(\''+data[i].functionId+'\')">' + "删除" + '</a>'*/
		    	 					;
		    			data[i]["contact"] = '<a href="javascript:void(0);" onclick="ebaic.contactDetail(\''+data[i].id+'\')">' + "查看" + '</a>';
		    		}
	    		}
	    		return data;
	    	},
	    	/**
	    	 * 打开企业或者个体报表信息页面
	    	 */
	    	NBDetail:function(pripid,ancheid){ 
	    		var isGtOrQe =$('#entmarkhidden').hiddenfield('getValue');
	    		
					if(isGtOrQe==0){
						//var url = $_this.getContextPath()+"/page/comselect/nbEntDetail.jsp?id="+encode(ancheid)+"&pripid="+encode(pripid)+"&isGtOrQe="+isGtOrQe+"&type=gt&identifier=hy&username=WUCJ1@SZAIC";
						var url = $_this.getContextPath()+"/page/comselect/nbEntDetail.jsp?id="+encode(ancheid)+"&pripid="+encode(pripid)+"&isGtOrQe="+isGtOrQe+"&type=gt";
		    		}
		    		if(isGtOrQe==1){
		    			//var url = $_this.getContextPath()+"/page/comselect/nbEntDetail.jsp?id="+encode(ancheid)+"&pripid="+encode(pripid)+"&isGtOrQe="+isGtOrQe+"&type=qy&identifier=hy&username=WUCJ1@SZAIC";
		    			var url = $_this.getContextPath()+"/page/comselect/nbEntDetail.jsp?id="+encode(ancheid)+"&pripid="+encode(pripid)+"&isGtOrQe="+isGtOrQe+"&type=qy";
		    		}
				//	util.openWindow("NBQueryDetail","年报详情信息",$_this.getContextPath()+"/page/comselect/NBQueryDetail.html?pripid="+pripid+"&isGtOrQe="+isGtOrQe,680,392);
	    		window.open(url);
	    	},
	    	contactDetail:function(id){ 
	    		util.openWindow("contactQueryDetail","工商联络员信息",$_this.getContextPath()+"/page/comselect/contactQueryDetail.html?id="+id,700,350);
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
	    				$('#entmark').radiofield('setValue','1');
	    				$('#nbmark').radiofield('setValue','1');
	    				$('#yeWork').radiofield('setValue','1');
	    				$('#ancheyear').comboxfield('option', 'defaultvalue', defaut);
	    				$("div[name='adminbrancode']").comboxfield("option","disabled",true);
	    				$("#btnExport").removeClass("display");
	    	},
	    	edit_fromNames : [ 'NBQueryListPanel']
	    	
		};
	
		torch._init();
		return torch;
});
