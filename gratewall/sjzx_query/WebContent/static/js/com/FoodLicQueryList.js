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
					    	 $("#btnExport").css("display","block");
					    	$("div[name='supdepbysup']").comboxfield("option","disabled",true);
					    	torch.getRegorg();
					    	$("div[name='supunitbysup']").comboxfield("option","change",$_this._getAdminbrancode);
					    	
				    		$("#save_button").off('click').on('click',$_this.queryCFList);
						    $("#reset_button").off('click').on('click',torch.resetEditCondition);
						    $("#export_button").off('click').on('click',$_this.exportFunc);
						    
						    $("#export_button").css("display","block");
							$(".btns").css("display","block");
							
						    util.exports('regFood',$_this.regFood);
						    
						    
						    
					    });
				});
	    	},
	    	exportFunc : function(){
		    	
	    		var entname = $("#entnamehidden").hiddenfield('getValue');
	    	    var licno = $("#licnohidden").hiddenfield('getValue');
	    		var supunitbysup = $("#supunitbysuphidden").hiddenfield('getValue');
	    		var supdepbysup = $("#supdepbysuphidden").hiddenfield('getValue');
	    		var newflag = $("#newflaghidden").hiddenfield('getValue');
	    		var lictype = $("#lictypehidden").hiddenfield('getValue');
	    		var street = $("#streethidden").hiddenfield('getValue');
	    		var ztyt = $("#ztythidden").hiddenfield('getValue');
	    		var jyxm = $("#jyxmhidden").hiddenfield('getValue');
	    		
	    		var isnotquery = $("#isnotquery").hiddenfield('getValue');
	    		
	    		if(isnotquery!=""){
	    			$_this.post("../../FoodLicController/exportExcel.do", 
	    					{entname:entname,licno:licno,supunitbysup:supunitbysup,supdepbysup:supdepbysup,newflag:newflag,lictype:lictype,street:street,ztyt:ztyt,jyxm:jyxm});		    		
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
	    		$("div[name='supunitbysup']").comboxfield("option","dataurl","../../comselect/code_value.do?type=regorg");
	    		$("div[name='supunitbysup']").comboxfield("reload");
	    	},
	    	
	    	_getAdminbrancode:function(){
	    		$("div[name='supdepbysup']").comboxfield("option","disabled",true);
	    		var text = $('div[name="supunitbysup"]').comboxfield('getValue');
	    		if(text!=""){
	    			$("div[name='supdepbysup']").comboxfield("option","disabled",false);
	    			$("div[name='supdepbysup']").comboxfield("option","dataurl","../../comselect/code_value.do?type=adminbrancode&parm="+text);
		    		$("div[name='supdepbysup']").comboxfield("reload");
	    		}
	    	},
	    	
	    	getCount:function(){
	    		var params = {
	    				url : '../../TZRController/TZRQueryList.do?flag=1',
	    				components: ['TZRQueryListPanel'],
	    				callback : function(data, param, res) {
	    					$("#totals").html(data.data);
	    				}
	    		};
	    		$.DataAdapter.submit(params);
	    	},
			 /**
			   * 查询食品许可证信息
			   */
	    	queryCFList : function(){
	    		/*$("#tips").removeClass("none").css("display","block");
	    		$("#tips").css("display","block");*/
	    		/*var entname = $('#entname').textfield("getValue");
	    		var licno = $('#licno').textfield("getValue");
	    		var supunitbysup = $('#supunitbysup').comboxfield("getValue");
	    		var supdepbysup = $('#supdepbysup').comboxfield("getValue");
	    		if (!entname && !licno && !supunitbysup && !supdepbysup) {
					jazz.warn("请至少输入一个条件进行查询！");
					return false;
				}*/
	    		var entname = $("#entname").textfield('getValue');
	    		var licno = $("#licno").textfield('getValue');
	    		var supunitbysup = $("#supunitbysup").comboxfield('getValue');
	    		var supdepbysup = $("#supdepbysup").comboxfield('getValue');
	    		var newflag = $("#newflag").radiofield('getValue');
	    		var lictype = $("#lictype").comboxfield('getValue');
	    		var street = $("#street").textfield('getValue');
	    		var ztyt = $("#ztyt").textfield('getValue');
	    		var jyxm = $("#jyxm").textfield('getValue');
	    		
	    		
	    		$("#isnotquery").hiddenfield('setValue',1); // 获取是否查询
	    		
	    		$('#entnamehidden').hiddenfield('setValue',entname);
	    		$('#licnohidden').hiddenfield('setValue',licno);
	    		$('#supunitbysuphidden').hiddenfield('setValue',supunitbysup);
	    		$('#supdepbysuphidden').hiddenfield('setValue',supdepbysup);
	    		$('#newflaghidden').hiddenfield('setValue',newflag);
	    		$('#lictypehidden').hiddenfield('setValue',lictype);
	    		$('#streethidden').hiddenfield('setValue',street);
	    		$('#ztythidden').hiddenfield('setValue',ztyt);
	    		$('#jyxmhidden').hiddenfield('setValue',jyxm);
	    		$("#save_button .button-text").html("查询中...");
	    		$('#save_button').button('disable');
	    		$("#save_button").off('click');
	    		var timer = null;  
	    		clearTimeout(timer);  
	    			timer = setTimeout(function() { 
    				//$('#save_button').button('enable');
    				//$("#save_button .button-text").html("查询");
    				//$("#save_button").on('click',$_this.queryCFList);
    	    		//$("#save_button").on('click',$_this.queryCFList);
	    		}, 1800);
	    			//torch.getCount();
	    		//	$("#tips").css("display","block");
		    		//查询失信被执行人信息（DC_BL_LAOLAI），并且有回调函数
		    		var gridUrl="../../FoodLicController/FoodLicQueryList.do";
		    		$("#FoodLicQueryListGrid").gridpanel("option",'datarender',torch.backFunction);//torch.backFunction
		    		$("#FoodLicQueryListGrid").gridpanel("option",'dataurl',gridUrl);
		    		$("#FoodLicQueryListGrid").gridpanel('query', ['FoodLicQueryListPanel'],null);
			},
			
			/**
			 * SYS_FUNCTION_CONFIG列表回调函数
			 */
			backFunction:function (event, obj) {
				$('#save_button').button('enable');
				$("#save_button .button-text").html("查询");
				$("#save_button").on('click',$_this.queryCFList);
	    		var data = obj.data;
	    		if (data.length == 0) {
	    			$(".nodata").css("display", "block");
	    			jazz.warn("该查询没有精确匹配到单位名称");
	    		} else {
	    			$(".nodata").css("display", "none");
	    		}
	    		
	    		for (var i = 0; i < data.length; i++) {
	    			data[i]["detail"] = '<a href="javascript:void(0)" onclick="ebaic.regFood(\''
	    					+ data[i]["id"]
	    					+ '\',\''
							+ data[i]["lictype"]
			    			+ '\',\''
							+ data[i]["licid"]
	    					+ '\')">' + '查看' + ' </a>';
	    		}
	    		return data;
	    	},
	    	
	    	regFood:function(id, lictype, licid){
	    		var urlright = "page/food/foodDetail.jsp";
	    		var url = $_this.getContextPath() + "/" + urlright + "?flag=" + encode("0")
	    				  + "&id=" + encode(id) + "&lictype=" + encode(lictype)
	    				  + "&licid=" + encode(licid);
	    		window.open(url);

	    	},
	    	
	    	//html中获取当前页的 上下文路径
	    	getContextPath: function (){
	    		var pathName = document.location.pathname;//  /page/mgr/torch/sysconfig/functionList.html
	    		var index = pathName.substr(1).indexOf("/");//  4
	    		var result = pathName.substr(0,index+1);//    /page
	    		return result;
	    	},
	    	
	    	/**
	    	 * 重置方法
	    	 */
	    	resetEditCondition : function(){
				for( x in torch.edit_fromNames){
					$("div[name='"+torch.edit_fromNames[x]+"']").formpanel("reset");
				}
				$('#newflag').radiofield('setValue','0');
	    	},
	    	edit_fromNames : [ 'FoodLicQueryListPanel']
		};
	
		torch._init();
		return torch;
});
