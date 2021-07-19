define(['require','jquery', 'common','util','widget/Address'], function(require, $, common,util){
	var enter = {};
    enter = {
    	/**
    	 * 模块初始化。
    	 */
    	_init: function(){
    		var $_this = this;
    		require(['domReady'], function (domReady) {
			  domReady(function () {
				    common.getCurrentUser();
		 			
			    	//页面样式
			    	$_this.bindingCss();
			    	//绑定页面方法
			    	$_this.bindingClik();
			    	//设立本账户申请的名称列表
			    	$_this.personAccountApplyList();
			    	common.computeHeight();
			  });
			});
    	},
    	bindingClik:function(){
    		$("#my-apply").on('click',this.changTabUser);
	    	$("#no-my-apply").on('click',this.changTabNoUser);
	    	$("#cpCheckIn").on('click',this.cpCheckIn);
	    	$("#know").click(function(){
	 			$("#know-div").css("display","none");
	 			$("#bg").css("display","none");
	 		}); 
	    	
	    	$(".setupInit").live('click',this.setupInit);// 进入办理按钮
    	},
    	bindingCss:function(){
    		$("#bg").css("display","block");
 			$("#know-div").css("display","block");
    	},
    	personAccountApplyList:function(){
    		$("#nameListGridPanel").gridpanel("option",'datarender',this.datarender);
	    	$("#nameListGridPanel").gridpanel("option",'dataurl','../../../torch/service.do?m=data&fid=personAccountEnterNameList');
	    	//alert($("nameListGridPanel").getRowLength());
	    	
	    	$("#nameListGridPanel").gridpanel("reload",null,function(){
	    		if($("#nameListGridPanel").gridpanel("getRowLength") == '0'){
	    			$("#no-my-apply").click();
		    	}
	    	});
    	},
    	/**
    	 * 我的名称列表 datarender 。
    	 */
    	datarender:function(item,rowsdata){
    		var data = rowsdata.data;   
    		if(data.length==0){	
    			$("#paginator").css("display","none");
    		}
    		for (var i = 0; i < data.length; i++) {
    			entName = data[i]["entName"];
    			notNo = data[i]["notNo"];
    			nameId = data[i]["nameId"];
    			serialNo = data[i]["serialNo"];
    			// 稍后通过class:setupInit绑定事件
    			var htm = "<div class='setupInit'>进入办理<div>";	
    			data[i]["custom"] = htm;
    		}
    		
    		return data;
    	},
    	/** 
    	 * 待办名称列表中的进入办理处理事件。
    	 */
    	setupInit:function(e){
    		var nameId = $.trim($(e.target).parent().prev().text()||'');
    		if(!nameId){
    			jazz.info("未查询到您的名称信息，请联系运维人员。");
    			return ;
    		}
    		$(".coverForSubmit").css("display","block");
    		var params = {
    		        url : '../../../apply/setup/entrance/cpSetupNameValid.do',
    		        params:{
    		        	nameId:nameId,
    		        },
    		        async:false,
    		        callback : function(data, param, res) {
    		        	$(".coverForSubmit").css("display","none");
    		        	var result = res.getAttr("result");
    		        	if(result == "1"){
    		        		jazz.confirm('该设立名称还有有效期短于三天!请注意延期!',function(){
  	        		        			var params1 = {
	    		        				 url : '../../../apply/setup/entrance/cpNameListEnter.do',
	    		         		        params:{
	    		         		        	nameId:nameId,
	    		         		        },
	    		         		        async:false,
	    		         		        callback : function(data, param, res) {
	    		         		       	var gid = res.getAttr("gid") || '';
	    	        		        	if(gid){
	    	  
	    		         		        	window.location.href='../../../page/apply/setup/basic_info.html?gid='+gid;
	    	        		        	}else{
	    	        		        		jazz.info("通过该名称，设立初始化失败。");
	    	        		        	}
    		         		        }
    		        			}
    		        			$.DataAdapter.submit(params1);
    		        		});       		        		
       		        	}else if(result == "2"){
       		        		var params2 = {
   		        				 url : '../../../apply/setup/entrance/cpNameListEnter.do',
   		         		        params:{
   		         		        	nameId:nameId,
   		         		        },
   		         		        async:false,
   		         		        callback : function(data, param, res) {
   		         		       	var gid = res.getAttr("gid") || '';
   	        		        	if(gid){
   	        		        	
   		         		        	window.location.href='../../../page/apply/setup/basic_info.html?gid='+gid;
   		         		        	
   	        		        	}else{
   	        		        		jazz.info("通过该名称，设立初始化失败。");
   	        		        		}
		         		        }
		        			}
       		        		$.DataAdapter.submit(params2);
     		        	}else{
    		        		jazz.warn("该名称已经过期 请重新申请!");
    		        	}
    		        } 	
    		    };  	
    		$.DataAdapter.submit(params);
    	},
    	/**
    	 * 用户自己申请的tab页切换
    	 * 
    	 * */
    	changTabUser : function(){
    		$(this).removeClass("no-selected").addClass("selected");
 	    	$(this).siblings().removeClass("selected").addClass("no-selected");
 	    	
 	    	//提示框
 	    	$(".name-idx").css("display","block");
 	    	$(".no-my-apply").css("display","none"); 	    	
 	    	$("#nameListGridPanel").css("display","block");
 	    	$("#nameListGridPanel").gridpanel("reload");
 	
    	},
    	/**
    	 * 用户非自己申请的名称tab页切换
    	 * 
    	 * */
    	changTabNoUser : function(){
    		$(this).removeClass("no-selected").addClass("selected");
 			$(this).siblings().removeClass("selected").addClass("no-selected");
 			$(".no-my-apply").css("display","block");   
 			$(".name-idx").css("display","none");  
 			$("#nameListGridPanel").css("display","none");  
    	},
    	/**
    	 * 非本账户申请，点击确定
    	 */
    	cpCheckIn :function(){
    		var entName=$("#entNameNoMyApply").textfield("getValue");
    		var cerNo=$("#cerNo").textfield("getValue");
    		if(entName==''){
    			jazz.info("企业名称不能为空。");
    			return;
    		}
    		if(cerNo==''){
    			jazz.info("名称申请人证件号码不能为空。");
    			return;
    		}
    		entName = entName.replace(/(^\s*)|(\s*$)/g,'');
    		cerNo = cerNo.replace(/(^\s*)|(\s*$)/g,'');
    		$(".coverForSubmit").css("display","block");
    		var params = {
    		        url : '../../../apply/setup/entrance/cpSetupNameValid2.do',
    		        params:{
    		        	entName:entName,
    		        	cerNo:cerNo
    		        },
    		        async:false,
    		        callback : function(data, param, res) {
    		        	$(".coverForSubmit").css("display","none");
    		        	var result = res.getAttr("result");
    		        	if(result == "1"){
    		        		jazz.confirm('该设立名称还有有效期短于三天!请注意延期!',function(){
  	        		        			var params1 = {
	    		        				 url : '../../../apply/setup/entrance/cpCheckin.do',
	    		         		        params:{
//	    		         		        	entName:entName,
//	    		         		        	cerNo:cerNo
	    		         		        },
	    		         		        async:false,
	    		         		        callback : function(data, param, res) {
	    		         		        	
	    		         		        	$(".coverForSubmit").css("display","none");
	    		         		        	var gid = res.getAttr("gid") || '';
		    	        		        	if(gid){
		    		         		        	window.location.href='../../../page/apply/setup/basic_info.html?gid='+gid;
		    	        		        	}
    		         		        }
    		        			}
    		        			$.DataAdapter.submit(params1);
    		        		});       		        		
       		        	}else if(result == "2"){
       		        		$("body").loading();
       		        		var params2 = {
   		        				 url : '../../../apply/setup/entrance/cpCheckin.do',
   		         		        params:{
//   		         		        	entName:entName,
//   		         		        	cerNo:cerNo
   		         		        },
   		         		        async:false,
   		         		        callback : function(data, param, res) {
   		         		       
   		         		        	$(".coverForSubmit").css("display","none");
   		         		        	var gid = res.getAttr("gid") || '';
	    	        		        	if(gid){
	    		         		        	window.location.href='../../../page/apply/setup/basic_info.html?gid='+gid;
    	        		        	}
		         		        }
		        			}
       		        		$.DataAdapter.submit(params2);
     		        	}else{
    		        		jazz.warn("该名称已经过期 请重新申请!");
    		        	}
    		        } 	
    		    }; 
    		$.DataAdapter.submit(params);
    	},
    };
    enter._init();
    return enter;
});