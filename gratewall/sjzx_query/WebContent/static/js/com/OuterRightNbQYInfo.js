define(['jquery', 'jazz','util','print'], function($, jazz,util,print){
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
					    	$(".display").css("display","block");
					    	
					    	//alert(torch.defaut);
					    	//获取当前用户的user_id 获取用户的角色，然后返回到前台判断是否有权限  是否公示？
					        $_this.queryListUserRolesByUserId();
					    
					 
					    	//企业年报 头信息处理
					    	$_this.getDetail();
				    	    
							
					    	$("tr:even").css({background:"#EFF6FA"});
			    		 	$("tr:odd").css({background:"#FBFDFD"});
			    		 	$(".jazz-field-comp-input").attr("disabled","disabled");
			    		 	
			    		 	$("#printPage").off('click').on('click',$_this._pringPrevie);
			    		 	
			    		 	//函数导出
			    		 	 util.exports('qyczxxDetailList',$_this.qyczxxDetailList);//3【企业年报 股东及出资信息】详情
			    		 	 util.exports('qydwdbDetailList',$_this.qydwdbDetailList);//6初始化【企业年报 对外提供保证担保信息】  
					    
					    });
				});
	    	},
	    	
	    	_pringPrevie:function(){
	    		$("#printArea").printArea();
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
				var params = {
	    				url : '../../NBselect/queryUserRolesByUserId.do',
	    				callback : function(data, param, res) {
	    				//	console.log(data.data);
	    					ifpub = data.data;
	    					 //1初始化【企业年报 基本信息】 
							$_this.initQYJBXXData(ifpub);
							
							 //2初始化【企业年报 资产状况信息】 
							$_this.initQYZCZKData(ifpub);
						
							//3初始化【企业年报 股东及出资信息】  
							$_this.initQYCZXXKData();
							
							 //4初始化【企业年报 股权变更信息】 
							$_this.initQYGQBGData();
							
							 //5初始化【企业年报 对外投资信息】 
							$_this.initQYDWTZData();
							
							 //6初始化【企业年报 对外提供保证担保信息】 
							$_this.initQYDWDBData();
							
							 //7初始化【企业年报 网站或网店信息】 
							$_this.initQYWDXXData();
							
						     //8初始化【企业年报 党建信息】 
					      	$_this.initQYDJXXData();
	    					
	    				}
	    		};
	    		$.DataAdapter.submit(params);
			},
	    	
	    	 //1.初始化【企业年报基本信息】 
	    	initQYJBXXData : function(ifpub) {
	    		//var ifpub = $("div[name = 'ifpubhidden']").hiddenfield('getValue');
	    		//alert(ifpub);
	    		var ancheid =  jazz.util.getParameter("ancheid")||'';
	    		var type =  jazz.util.getParameter("type")||'';
	    		alert(type)
	    		return;
				$.ajax({
					url : '../../NBselect/NBQYGTInfoQueryInfo.do',
					type : "post",
					data : {
						id : ancheid,
						type : type,
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
						//alert(ifpub);
							if(ifpub=="fail"){
								if (jsonData[0].data.ifpubempnum=="不公示") {
									$("div[name = 'empnum']").textfield('setValue','不公示');
								}
							}
							
						}
					}
				});
			},
			
			//2.初始化【企业年报资产状况信息】 
			initQYZCZKData : function(ifpub) {
				var ifpubhidden = $("div[name = 'ifpubhidden']").hiddenfield('getValue');
				var ancheid =  jazz.util.getParameter("ancheid")||'';
				var type =  jazz.util.getParameter("type")||'';
				$.ajax({
					url : '../../NBselect/NBQYGTInfoQueryInfo.do',
					type : "post",
					data : {
						id : ancheid,
						type : type,
						entityNo:2
					},
					async : false,
					dataType : "json",
					success : function(data) {
						/*if(data && data.data && data.data[0]){
							$("div[name='qyzczkPanel']").formpanel('setValue',data.data[0]);
						}*/
						var jsonData = data.data;
						if ($.isArray(jsonData)&&jsonData[0].data) {
							for (var i = 0, len = jsonData.length; i < len; i++) {
								$("div[name='" + jsonData[i].name + "']").formpanel("setValue", jsonData[i] || {});
								//console.log(jsonData[i].data.maibusinc);
							}
							if (jsonData[0].data.ifassgro=="公示" && jsonData[0].data.vendinc !=null&& jsonData[0].data.vendinc != 'undefined') {
								$("div[name = 'assgro']").textfield('setValue',jsonData[0].data.assgro+'万元');
							}
							if (jsonData[0].data.iftotequ=="公示" && jsonData[0].data.totequ !=null&& jsonData[0].data.totequ != 'undefined') {
								$("div[name = 'totequ']").textfield('setValue',jsonData[0].data.totequ+'万元');
							}
							if (jsonData[0].data.ifvendinc=="公示" && jsonData[0].data.vendinc !=null&& jsonData[0].data.vendinc != 'undefined') {
								$("div[name = 'vendinc']").textfield('setValue',jsonData[0].data.vendinc+'万元');
							}
							if (jsonData[0].data.ifprogro=="公示" && jsonData[0].data.progro !=null&& jsonData[0].data.progro != 'undefined') {
								$("div[name = 'progro']").textfield('setValue',jsonData[0].data.progro+'万元');
							}
							if (jsonData[0].data.ifmaibusinc=="公示" && jsonData[0].data.maibusinc !=null&& jsonData[0].data.maibusinc != 'undefined') {
								$("div[name = 'maibusinc']").textfield('setValue',jsonData[0].data.maibusinc+'万元');
							}
							if (jsonData[0].data.ifnetinc=="公示" && jsonData[0].data.netinc !=null&& jsonData[0].data.netinc != 'undefined') {
								$("div[name = 'netinc']").textfield('setValue',jsonData[0].data.netinc+'万元');
							}
							if (jsonData[0].data.ifratgro=="公示" && jsonData[0].data.ratgro !=null&& jsonData[0].data.ratgro != 'undefined') {
								$("div[name = 'ratgro']").textfield('setValue',jsonData[0].data.ratgro+'万元');
							}
							if (jsonData[0].data.ifliagro=="公示" && jsonData[0].data.liagro !=null&& jsonData[0].data.liagro != 'undefined') {
								$("div[name = 'liagro']").textfield('setValue',jsonData[0].data.liagro+'万元');
							}
							
							if(ifpub=="fail"){
								
								if (jsonData[0].data.ifassgro=="不公示") {
									$("div[name = 'assgro']").textfield('setValue','企业选择不公示');
								}
								if (jsonData[0].data.iftotequ=="不公示") {
									$("div[name = 'totequ']").textfield('setValue','企业选择不公示');
								}
								if (jsonData[0].data.ifvendinc=="不公示") {
									$("div[name = 'vendinc']").textfield('setValue','企业选择不公示');
								}
								if (jsonData[0].data.ifprogro=="不公示") {
									$("div[name = 'progro']").textfield('setValue','企业选择不公示');
								}
								if (jsonData[0].data.ifmaibusinc=="不公示") {
									$("div[name = 'maibusinc']").textfield('setValue','企业选择不公示');
								}
								if (jsonData[0].data.ifnetinc=="不公示") {
									$("div[name = 'netinc']").textfield('setValue','企业选择不公示');
								}
								if (jsonData[0].data.ifratgro=="不公示") {
									$("div[name = 'ratgro']").textfield('setValue','企业选择不公示');
								}
								if (jsonData[0].data.ifliagro=="不公示") {
									$("div[name = 'liagro']").textfield('setValue','企业选择不公示');
								}
							}else{
								$("div[name = 'assgro']").textfield('setValue',jsonData[0].data.assgro+'万元');
								$("div[name = 'totequ']").textfield('setValue',jsonData[0].data.totequ+'万元');
								$("div[name = 'vendinc']").textfield('setValue',jsonData[0].data.vendinc+'万元');
								$("div[name = 'progro']").textfield('setValue',jsonData[0].data.progro+'万元');
								$("div[name = 'maibusinc']").textfield('setValue',jsonData[0].data.maibusinc+'万元');
								$("div[name = 'netinc']").textfield('setValue',jsonData[0].data.netinc+'万元');
								$("div[name = 'ratgro']").textfield('setValue',jsonData[0].data.ratgro+'万元');
								$("div[name = 'liagro']").textfield('setValue',jsonData[0].data.liagro+'万元');
							}
						}
					}
				});
			},
		
			 /**
			   *3. 企业年报 股东及出资信息
			   */
			initQYCZXXKData : function(){
	    		/* var params = {
	    				 entityNo:3,
	    				 id:parent.id,
	    				 type:parent.type
			    		};
*/         
				var ancheid =  jazz.util.getParameter("ancheid")||'';
			//	var id =  parent.id;
				//var type =  parent.type;
				var type =   jazz.util.getParameter("type")||'';
	    		var gridUrl="../../NBselect/NBQYGTInfoQueryInfo.do?id="+ancheid+"&type="+type+"&entityNo=3";
	    		$("#qyczxxGrid").gridpanel("option",'datarender',torch.backqyczxxFunction);
	    	//	$("#qyczxxGrid").gridpanel("option","dataurlparams",params);
	    		$("#qyczxxGrid").gridpanel("option",'dataurl',gridUrl);
	    		$("#qyczxxGrid").gridpanel("reload");
	    		
			},
			/**
			 *  4初始化【企业年报 股权变更信息】 
			 */
			initQYGQBGData : function(){
	    		/* var params = {
	    				 entityNo:4,
	    				 id:parent.id,
	    				 type:parent.type
			    		};
*/
				var ancheid =  jazz.util.getParameter("ancheid")||'';
			//	var id =  parent.id;
				var type =  parent.type;
	    		var gridUrl="../../NBselect/NBQYGTInfoQueryInfo.do?id="+ancheid+"&type="+type+"&entityNo=4";
	    	//	$("#qygqbgGrid").gridpanel("option",'datarender',torch.backqygqbgFunction);
	    	//	$("#qygqbgGrid").gridpanel("option","dataurlparams",params);
	    		$("#qygqbgGrid").gridpanel("option",'dataurl',gridUrl);
	    		$("#qygqbgGrid").gridpanel("reload");
	    		
			},
			/**
			 *  5初始化【企业年报 对外投资信息】 
			 */
			initQYDWTZData : function(){
	    		/* var params = {
	    				 entityNo:5,
	    				 id:parent.id,
	    				 type:parent.type
			    		};
*/
				var ancheid =  jazz.util.getParameter("ancheid")||'';
				//var id =  parent.id;
				var type =  parent.type;
	    		var gridUrl="../../NBselect/NBQYGTInfoQueryInfo.do?id="+ancheid+"&type="+type+"&entityNo=5";
	    		$("#qydwtzGrid").gridpanel("option",'datarender',torch.backqydwtzFunction);
	    	//	$("#qydwtzGrid").gridpanel("option","dataurlparams",params);
	    		$("#qydwtzGrid").gridpanel("option",'dataurl',gridUrl);
	    		$("#qydwtzGrid").gridpanel("reload");
	    		
			},
			/**
			 *   6初始化【企业年报 对外提供保证担保信息】 
			 */
			initQYDWDBData : function(){
	    		/* var params = {
	    				 entityNo:5,
	    				 id:parent.id,
	    				 type:parent.type
			    		};
*/
				var ancheid =  jazz.util.getParameter("ancheid")||'';
				//var id =  parent.id;
				var type =  parent.type;
	    		var gridUrl="../../NBselect/NBQYGTInfoQueryInfo.do?id="+ancheid+"&type="+type+"&entityNo=6";
	    		$("#qydwdbGrid").gridpanel("option",'datarender',torch.backqydwdbFunction);
	    	//	$("#qydwtzGrid").gridpanel("option","dataurlparams",params);
	    		$("#qydwdbGrid").gridpanel("option",'dataurl',gridUrl);
	    		$("#qydwdbGrid").gridpanel("reload");
	    		
			},
			//7初始化【企业年报 网站或网店信息】 
			initQYWDXXData : function() {
				var ancheid =  jazz.util.getParameter("ancheid")||'';
				$.ajax({
					url : '../../NBselect/NBQYGTInfoQueryInfo.do',
					type : "post",
					data : {
						id : ancheid,
						type : parent.type,
						entityNo:7
					},
					async : false,
					dataType : "json",
					success : function(data) {
						/*if(data && data.data && data.data[0]){
							$("div[name='qyzczkPanel']").formpanel('setValue',data.data[0]);
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
			//8初始化【企业年报 党建信息】 
			initQYDJXXData : function() {
				var ancheid =  jazz.util.getParameter("ancheid")||'';
				$.ajax({
					url : '../../NBselect/NBQYGTInfoQueryInfo.do',
					type : "post",
					data : {
						id : ancheid,
						type : parent.type,
						entityNo:8
					},
					async : false,
					dataType : "json",
					success : function(data) {
						/*if(data && data.data && data.data[0]){
							$("div[name='qyzczkPanel']").formpanel('setValue',data.data[0]);
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
			//9初始化【企业年报 修改信息】 
			initQYXGXXData : function() {
				var ancheid =  jazz.util.getParameter("ancheid")||'';
				$.ajax({
					url : '../../NBselect/NBQYGTInfoQueryInfo.do',
					type : "post",
					data : {
						id : ancheid,
						type : parent.type,
						entityNo:9
					},
					async : false,
					dataType : "json",
					success : function(data) {
						/*if(data && data.data && data.data[0]){
							$("div[name='qyzczkPanel']").formpanel('setValue',data.data[0]);
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
			/**
			 * 企业年报 股东及出资信息
			 */
			backqyczxxFunction:function (event, obj) {
	    		var data = obj.data;
	    		if (data.length == 0) {
	    			$(".nodata").css("display", "block");
	    		//	$(".grid_paginator_qyczxx").css("display", "none");
	    		} else {
	    			$(".nodata").css("display", "none");
	    		}
	    		for (var i = 0; i < data.length; i++) {
	    			/*data[i]["lisubconam"]= data[i].lisubconam+ "万元";
	    			data[i]["liacconam"]= data[i].liacconam+ "万元";*/
	    			//data[i]["updatetime"] = data[i].updatetime.split("-")[0]+"年" +data[i].updatetime.split("-")[1]+"月"+data[i].updatetime.split("-")[2]+"日";
	    			data[i]["opt"] = '<a href="javascript:void(0);" onclick="ebaic.qyczxxDetailList(\''+data[i].ancheid+'\')">' + "详情" + '</a>'
	    						/*+'&nbsp;&nbsp;<a href="javascript:void(0);" onclick="ebaic.configEditOrQuery(\''+data[i].functionId+'\')">' + "配置" + '</a>'
	    	 					+'&nbsp;&nbsp;<a href="javascript:void(0);" onclick="ebaic.delFunc(\''+data[i].functionId+'\')">' + "删除" + '</a>'*/
	    	 					;
	    		}
	    		
	    		return data;
	    	},
	    	/**
			 *  6初始化【企业年报 对外提供保证担保信息】 
			 */
	    	backqydwdbFunction:function (event, obj) {
	    		var data = obj.data;
	    		if (data.length == 0) {
	    			$(".nodata").css("display", "block");
	    		} else {
	    			$(".nodata").css("display", "none");
	    		}
	    		for (var i = 0; i < data.length; i++) {
	    			/*data[i]["lisubconam"]= data[i].lisubconam+ "万元";
	    			data[i]["liacconam"]= data[i].liacconam+ "万元";*/
	    			//data[i]["updatetime"] = data[i].updatetime.split("-")[0]+"年" +data[i].updatetime.split("-")[1]+"月"+data[i].updatetime.split("-")[2]+"日";
	    			data[i]["opt"] = '<a href="javascript:void(0);" onclick="ebaic.qydwdbDetailList(\''+data[i].ancheid+'\')">' + "详情" + '</a>'
	    						/*+'&nbsp;&nbsp;<a href="javascript:void(0);" onclick="ebaic.configEditOrQuery(\''+data[i].functionId+'\')">' + "配置" + '</a>'
	    	 					+'&nbsp;&nbsp;<a href="javascript:void(0);" onclick="ebaic.delFunc(\''+data[i].functionId+'\')">' + "删除" + '</a>'*/
	    	 					;
	    		}
	    		
	    		return data;
	    	},
	    	/**
	    	 * 企业年报 股东及出资信息
	    	 */
	    	qyczxxDetailList:function(ancheid){
	    		util.openWindow("NBQYCZXXQueryDetail","出资信息详情",$_this.getContextPath()+"/page/comselect/NBQYCZXXQueryDetail.html?ancheid="+ancheid,680,392);
	    	},
	    	/**
	    	 *  6初始化【企业年报 对外提供保证担保信息】 
	    	 */
	    	qydwdbDetailList:function(ancheid){
	    		util.openWindow("NBQYCDWDBQueryDetail","对外提供保证担保信息",$_this.getContextPath()+"/page/comselect/NBQYCDWDBQueryDetail.html?ancheid="+ancheid,680,392);
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
