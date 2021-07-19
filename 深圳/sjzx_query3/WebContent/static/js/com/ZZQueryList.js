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
					    	
					    /*	$("div[name='adminbrancode']").comboxfield("option","disabled",true);
					    	torch.getRegorg();*/
					    	
					    	
				    		$("#save_button").off('click').on('click',$_this.queryCFList);
						    $("#reset_button").off('click').on('click',torch.resetEditCondition);
							
						   // $("div[name='regorg']").comboxfield("option","change",$_this._getAdminbrancode);
						    
						//    util.exports('regDetail',$_this.regDetail);
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
    					//$("div[name='regorg']").comboxfield('option', 'defaultvalue', defValue);
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
	    		var projectname = $('#projectname').textfield("getValue");
	    		var certno = $('#certno').textfield("getValue");
	    		var ficationdate = $('#ficationdate').datefield("getValue");
	    		var certtype = $('#certtype').comboxfield("getValue");
				if (!projectname && !certno && !ficationdate && !certtype ) {
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
				
//					torch.getCount();
			    	//	$("#tips").css("display","block");
				    		//查询失信被执行人信息（DC_BL_LAOLAI），并且有回调函数
			    		//var gridUrl="../../ycselect/YCQueryList.do";
			    		var gridUrl="../../zzselect/ZZQuery.do";
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
	    			jazz.warn("该查询没有精确匹配到证照信息");
	    		} else {
	    			$(".nodata").css("display", "none");
	    		}
	    		for (var i = 0; i < data.length; i++) {
	    			data[i]["opt"] = '<a href="javascript:void(0)" onclick="ebaic.editFunc(\''
		    			+ data[i]["id"]
	    				+ '\',\''
						+ data[i]["certtype"]
						+'\')">' + "详情" + '</a>';
					data[i]["certtype"]=$_this.getPersontype(data[i]["certtype"]);
		}
	    		return data;
	    	},
	    	
	    	editFunc:function(id,certtype){
	    		//alert(id);
	    		//alert(certtype);
	    		if(certtype=='1'){
		    	util.openWindow("jsgcjgghyshgz","建设工程竣工规划验收合格证",$_this.getContextPath()+"/page/comselect/zz_jgcert.html?id="+id+"&certtype="+certtype,500,360);
	    		}else if(certtype=='2'){
			    	util.openWindow("jsgcyshgz","建设工程验收合格证",$_this.getContextPath()+"/page/comselect/zz_yscert.html?id="+id+"&certtype="+certtype,820,600);
	    		}
	    		//util.openWindow("SXQueryDetail","异常名录详情信息",$_this.getContextPath()+"/page/comselect/YCQueryEnt.html?entid="+entid,900,500);
	    		//window.open($_this.getContextPath()+"/page/comselect/YCQueryEnt.html?entid="+entid+"&entname="+encode(entname));
	    		/*var url = $_this.getContextPath()+"/mgr/torch/sysconfig/editFunctionConfig.html?functionId="+functionId;
	    		window.location.href=url;*/
	    	},
	    	 getPersontype:function(code){
	    		switch(code){
	    		case '1' :		return '建设工程竣工规划验收合格证';
	    		case '2' :		return '建设工程验收合格证';
	    		}
	    		return "";
	    	}
	    	,
	    	
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
