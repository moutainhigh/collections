define(['jquery', 'jazz','util' ,"cookie"], function($, jazz,util,cookie){
 var torch ={};
	torch = {
			defaut :"000",
		/**
		 * 模块初始化
		 */
			_init: function(){
				$_this = this;
				require(['domReady'], function (domReady) {
					    domReady(function () {
					    	$(".display").css("display","block");
					    	
					    	
					    	//获取当前用户的user_id 获取用户的角色，然后返回到前台判断是否有权限  是否公示？
					    	$_this.queryListUserRolesByUserId();
					    	//个体年报 头信息处理
					    	$_this.getDetail();
				    	    
				    	    //1初始化【个体年报 基本信息】 
						/*	$_this.initGTJBXXData();
							
							 //2初始化【个体年报  生产经营情况信息】 
							$_this.initGTZCZKData();
						
							//3初始化【个体年报  行政许可信息】  
							$_this.initGTCZXXKData();
							
							 //4初始化【个体年报 网站或网店信息】 
							$_this.initGTWDXXData();
							
						     //5初始化【个体年报 党建信息】 
					      	$_this.initGTDJXXData();*/
						
							 //6初始化【个体年报 修改信息】 
						//	$_this.initGTXGXXData();
							
							
					    	$("tr:even").css({background:"#EFF6FA"});
			    		 	$("tr:odd").css({background:"#FBFDFD"});
			    		 	$(".jazz-field-comp-input").attr("disabled","disabled");
			    		 	
			    		 	//函数导出
			    		 	 util.exports('GTczxxDetailList',$_this.GTczxxDetailList);//3初始化【个体年报  行政许可信息】  
					    
					    });
				});
	    	},
	    	getDetail:function(){
	    		var ancheyear =  jazz.util.getParameter("ancheyear")||'';
		    	var anchedate =  jazz.util.getParameter("anchedate")||'';
		    	$("#getYear").html(ancheyear);
		    	if(anchedate=="" || anchedate == 'undefined'){
		    		$("#anCheDate").html("空");
		    	}else{
		    		$("#anCheDate").html(anchedate);
		    	}
	    	},
	    	 //获取当前用户的user_id 获取用户的角色，然后返回到前台判断是否有权限  是否公示？ 
	    	queryListUserRolesByUserId : function() {
	    		var ifpub  = "";
	    		// $.cookie("username",username);
	    		// $.cookie("identifier",identifier);
	    		var userName = $.cookie("username");
	    		var identifier = $.cookie("identifier");
	    		
	    		var params = {
	    				url : '../../NBselect/queryUserRolesByUserId.do?userName=' + userName +"&identifier="+identifier,
	    				callback : function(data, param, res) {
	    				//	console.log(data.data);
	    					 
	    					 if(identifier=='hy'){
		    						ifpub = success;
		    					}else{
		    						ifpub = data.data;
		    					}
	    					 
	    					  //1初始化【个体年报 基本信息】 
							$_this.initGTJBXXData();
							
							 //2初始化【个体年报  生产经营情况信息】 
							$_this.initGTZCZKData(ifpub);
						
							//3初始化【个体年报  行政许可信息】  
							$_this.initGTCZXXKData();
							
							 //4初始化【个体年报 网站或网店信息】 
							$_this.initGTWDXXData();
							
						     //5初始化【个体年报 党建信息】 
					      	$_this.initGTDJXXData();
	    				}
	    		};
	    		$.DataAdapter.submit(params);
			},
	    	
	    	 //1.初始化【个体年报基本信息】 
	    	initGTJBXXData : function() {
	    		var ancheid =  jazz.util.getParameter("ancheid")||'';
				$.ajax({
					url : '../../NBselect/NBQYGTInfoQueryInfo.do',
					type : "post",
					data : {
						id : ancheid,
						type : parent.type,
						entityNo:1
					},
					async : false,
					dataType : "json",
					success : function(data) {
						var jsonData = data.data;
						if ($.isArray(jsonData)&&jsonData[0].data) {
							for (var i = 0, len = jsonData.length; i < len; i++) {
								$("div[name='" + jsonData[i].name + "']").formpanel("setValue", jsonData[i] || {});
				
							}
							
						}
					}
				});
			},
			
			//2.初始化【个体年报资产状况信息】 
			initGTZCZKData : function(ifpub) {
				var ancheid =  jazz.util.getParameter("ancheid")||'';
				$.ajax({
					url : '../../NBselect/NBQYGTInfoQueryInfo.do',
					type : "post",
					data : {
						id : ancheid,
						type : parent.type,
						entityNo:2
					},
					async : false,
					dataType : "json",
					success : function(data) {
						/*if(data && data.data && data.data[0]){
							$("div[name='GTzczkPanel']").formpanel('setValue',data.data[0]);
						}*/
						var jsonData = data.data;
						if ($.isArray(jsonData)&&jsonData[0].data) {
							for (var i = 0, len = jsonData.length; i < len; i++) {
								$("div[name='" + jsonData[i].name + "']").formpanel("setValue", jsonData[i] || {});
								//console.log(jsonData[i].data.maibusinc);
							}
							if (jsonData[0].data.ifvendinc=='1' && jsonData[0].data.vendinc !=null) {
								$("div[name = 'vendinc']").textfield('setValue',jsonData[0].data.vendinc+'万元');
							}
							if (jsonData[0].data.ifratgro=='1' && jsonData[0].data.ratgro !=null) {
								$("div[name = 'ratgro']").textfield('setValue',jsonData[0].data.ratgro+'万元');
							}
							if(ifpub=="fail"){
								if (jsonData[0].data.ifvendinc=='2') {
									$("div[name = 'vendinc']").textfield('setValue','不公示');
								}
								if (jsonData[0].data.ifratgro=='2') {
									$("div[name = 'ratgro']").textfield('setValue','不公示');
								}
							}else{
								$("div[name = 'vendinc']").textfield('setValue',jsonData[0].data.vendinc+'万元');
								$("div[name = 'ratgro']").textfield('setValue',jsonData[0].data.ratgro+'万元');
							}
						}
					}
				});
			},
			 /**
			   *3. 个体年报 股东及出资信息
			   */
			initGTCZXXKData : function(){
	    		/* var params = {
	    				 entityNo:3,
	    				 id:parent.id,
	    				 type:parent.type
			    		};
*/
				var ancheid =  jazz.util.getParameter("ancheid")||'';
				//var id =  parent.id;
				var type =  parent.type;
	    		var gridUrl="../../NBselect/NBQYGTInfoQueryInfo.do?id="+ancheid+"&type="+type+"&entityNo=3";
	    	//	$("#GTczxxGrid").gridpanel("option",'datarender',torch.backgtczxxFunction);
	    	//	$("#GTczxxGrid").gridpanel("option","dataurlparams",params);
	    		$("#gtxzxkGrid").gridpanel("option",'dataurl',gridUrl);
	    		$("#gtxzxkGrid").gridpanel("reload");
	    		
			},
			//4初始化【个体年报 网站或网店信息】 
			initGTWDXXData : function() {
				var ancheid =  jazz.util.getParameter("ancheid")||'';
				$.ajax({
					url : '../../NBselect/NBQYGTInfoQueryInfo.do',
					type : "post",
					data : {
						id : ancheid,
						type : parent.type,
						entityNo:4
					},
					async : false,
					dataType : "json",
					success : function(data) {
						/*if(data && data.data && data.data[0]){
							$("div[name='GTzczkPanel']").formpanel('setValue',data.data[0]);
						}*/
						var jsonData = data.data;
						if ($.isArray(jsonData)&&jsonData[0].data) {
							for (var i = 0, len = jsonData.length; i < len; i++) {
								$("div[name='" + jsonData[i].name + "']").formpanel("setValue", jsonData[i] || {});
								//console.log(jsonData[i].data.maibusinc);
							}
							
						}
					}
				});
			},
			//5初始化【个体年报 党建信息】 
			initGTDJXXData : function() {
				var ancheid =  jazz.util.getParameter("ancheid")||'';
				$.ajax({
					url : '../../NBselect/NBQYGTInfoQueryInfo.do',
					type : "post",
					data : {
						id : ancheid,
						type : parent.type,
						entityNo:5
					},
					async : false,
					dataType : "json",
					success : function(data) {
						/*if(data && data.data && data.data[0]){
							$("div[name='GTzczkPanel']").formpanel('setValue',data.data[0]);
						}*/
						var jsonData = data.data;
						if ($.isArray(jsonData)&&jsonData[0].data) {
							for (var i = 0, len = jsonData.length; i < len; i++) {
								$("div[name='" + jsonData[i].name + "']").formpanel("setValue", jsonData[i] || {});
								//console.log(jsonData[i].data.maibusinc);
							}
							
						}
					}
				});
			},
			//6初始化【个体年报 修改信息】 
			initGTXGXXData : function() {
				var ancheid =  jazz.util.getParameter("ancheid")||'';
				$.ajax({
					url : '../../NBselect/NBQYGTInfoQueryInfo.do',
					type : "post",
					data : {
						id : ancheid,
						type : parent.type,
						entityNo:6
					},
					async : false,
					dataType : "json",
					success : function(data) {
						/*if(data && data.data && data.data[0]){
							$("div[name='GTzczkPanel']").formpanel('setValue',data.data[0]);
						}*/
						var jsonData = data.data;
						if ($.isArray(jsonData)&&jsonData[0].data) {
							for (var i = 0, len = jsonData.length; i < len; i++) {
								$("div[name='" + jsonData[i].name + "']").formpanel("setValue", jsonData[i] || {});
								//console.log(jsonData[i].data.maibusinc);
							}
							
						}
					}
				});
			},
	    	//html中获取当前页的 上下文路径
	    	getContextPath: function (){
	    		var pathName = document.location.pathname;//  /page/mgr/torch/sysconfig/functionList.html
	    		var index = pathName.substr(1).indexOf("/");//  4
	    		var result = pathName.substr(0,index+1);//    /page
//	    		alert(pathName);
	    		return result;
	    	}
	    	
	}
	torch._init();
	return torch;
});
