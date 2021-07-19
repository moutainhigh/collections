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
					    	//页面元素处理
							$_this.loadData();
							
							
					    	$("#back_button").off('click').on('click',$_this.goBack);
			    	   	 	$("tr:even").css({background:"#EFF6FA"});
			    		 	$("tr:odd").css({background:"#FBFDFD"});
			    		 	
			    		 	$(".jazz-field-comp-input").attr("disabled","disabled");
			    		////若董事有兼任经理，初始化经理信息
//							$_this.initManagerData();
							//函数导出
			    		 //	util.exports("birthdayDateVerify",$_this.birthdayDateVerify());
					    });
				});
	    	},
	    	
	    	loadData : function() {
	    		var id = jazz.util.getParameter("id")||"";
				$.ajax({
					url : '../../NBselect/getContactDetailById.do?id='+id,
					type : "post",
					async : false,
					dataType : "json",
					success : function(data) {
						var jsonData = data.data;
						if ($.isArray(jsonData)) {
							for (var i = 0, len = jsonData.length; i < len; i++) {
								$("div[name='" + jsonData[i].name + "']").formpanel("setValue", jsonData[i] || {});
				
								if(jsonData[i].data.mobel==""){
									$("#mobel").find(".jazz-form-text-label").eq(1).html("");
								}else{
									$("#mobel").find(".jazz-form-text-label").eq(1).html("<a class='getMobels' href='#####'  data-mobel="+jsonData[i].data.mobel+">查看(该操作会记录日志)</a>");
								}
								
								if(jsonData[i].data.cerno==""){
									$("#cerno").find(".jazz-form-text-label").eq(1).html("");
								}else{
									$("#cerno").find(".jazz-form-text-label").eq(1).html("<a class='cernoOper' href='#####'  data-mobel="+jsonData[i].data.cerno+">查看(该操作会记录日志)</a>");
								}
								
								//var formData = jsonData[i].data ;
							}
							
							$_this.getMobels();
							$_this.getOperation();
							/*$("#Prov-City-Other").Address({provCtrlId:'houseAddProv',cityCtrlId:'houseAddCity'},'',{proValue:jsonData[0].data.houseAddProv,cityValue:jsonData[0].data.houseAddCity});
							//如果是外籍 则民族默认显示为'无'
							if(!(jsonData[0].data.country == '446') && !(jsonData[0].data.country == '158') && !(jsonData[0].data.country == '344')
									&& !(jsonData[0].data.country == '156')){
								$("div[name = 'nation']").comboxfield('setValue','00');
							}*/
						}
					}
				});
			},
			
			
			getMobels:function(){
				$(".getMobels").on("click",function(){
					var _this = $(this);
					_this.each(function(){
						var $this = $(this).data("mobel");
						$(this).html($this);
						$(this).css("text-decoration","none");
						$.ajax({
							url:'../../../query/reg/showphone.do',
							data:{
								flag : "查询工商联络员手机号码"
							},
							type:"post",
							dataType : 'json',
							success:function(data){
							}
						});
					});
				});
			},
			getOperation:function(){
				$(".cernoOper").on("click",function(){
					var _this = $(this);
					_this.each(function(){
						var $this = $(this).data("mobel");
						$(this).html($this);
						$(this).css("text-decoration","none");
						$.ajax({
							url:'../../../query/reg/showphone.do',
							data:{
								flag : "查询工商联络员身份证号码"
							},
							type:"post",
							dataType : 'json',
							success:function(data){
							}
						});
					});
				});
			},
			
			/**
	    	 * 查询董事里是否有兼任经理的，若有,回显该董事的信息
	    	 */
	    	initManagerData:function(){
	    		var mbrFlag = jazz.util.getParameter('mbrFlag')||'';
	    		if(mbrFlag==2){//编辑经理时
	    			var gid = jazz.util.getParameter('gid')||'';
	    			var params = {
	    					url : '../../../apply/setup/member/isManager.do',
	    					params:{
	    						gid : gid ,
	    					},
	    					async:false,
	    					callback : function(data, param, res) {
	    						var data = res.getAttr("result")|| {};
	    						if(data){
	    							$("div[name='psnjobId']").hiddenfield("setValue", data.psnjobId || '');
	    							$("div[name='supsType']").comboxfield("setValue", data.supsType || '');
	    							$("div[name='posBrForm']").comboxfield("setValue", data.posBrForm || '');
	    							$("div[name='offYears']").comboxfield("setValue", data.offYears || '');
	    							
	    							$("div[name='name']").autocompletecomboxfield("setValue",data.name || '');
	    							if(data.cerType=="1"){//身份证
	    								$("div[name='cerNo']").textfield("option",'rule',"must_idcard_length;0;40");
	    							}else{
	    								$("div[name='cerNo']").textfield("option",'rule',"must_length;0;40");
	    							}
	    							$("div[name='cerType']").comboxfield("setValue",data.cerType || '');
	    							$("div[name='cerNo']").textfield("setValue",data.cerNo || '');
	    							$("div[name='country']").comboxfield("setValue",data.country || '');
	    							$("div[name='sex']").radiofield("setValue",data.sex || '');
	    							$("div[name='liteDeg']").comboxfield("setValue",data.liteDeg || '');
	    							
	    							$("div[name='nation']").comboxfield("setValue",data.nation || '');//polStand
	    							$("div[name='polStand']").comboxfield("setValue",data.polStand || '');
	    							var date = data.natDate || '';
	    							var d = date.split(" ");
	    							$("div[name='natDate']").datefield("setValue",d[0] || '');
	    							
	    							$("div[name='houseAddProv']").comboxfield("setValue",data.houseAddProv || '');
	    							$("div[name='houseAddCity']").comboxfield("setValue",data.houseAddCity || '');
	    							$("div[name='houseAddOther']").textfield("setValue",data.houseAddOther || '');
	    						}
	    					} 	
	    			};  	
	    			$.DataAdapter.submit(params);
	    		}
	    	},
	    	
			 /**
				 * 根据sExtSequence查询查询失信被执行人信息（DC_BL_LAOLAI）
				 */
			 queryFunctionConfig : function(id){
				/* var params = {
						 id:id
			    		};*/
				   //查询失信被执行人信息（DC_BL_LAOLAI）
				  //   var panelUrl="../../otherselect/getQueryById.do";
				     var panelUrl="../../NBselect/getContactDetailById.do?id="+id;
				 //	$("#querySXDetialPanel").formpanel("option","dataurlparams",params);
	    			$("#queryContactDetialPanel").formpanel("option","dataurl",panelUrl);
	    			$("#queryContactDetialPanel").formpanel("reload");
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
