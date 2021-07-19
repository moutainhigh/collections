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
					    /*	var gridUrl="../../TZRController/TZRQueryList.do";
				    		$("#TZRQueryListGrid").gridpanel("option",'datarender',torch.backFunction);
				    		$("#TZRQueryListGrid").gridpanel("option",'dataurl',gridUrl);
				    		$("#TZRQueryListGrid").gridpanel('query', ['TZRQueryListPanel'],null);*/
				    		
				    		$("#save_button").off('click').on('click',$_this.queryCFList);
						    $("#reset_button").off('click').on('click',torch.resetEditCondition);
							$(".btns").css("display","block");
						    util.exports('regDetail',$_this.regDetail);
						    util.exports('regfile',$_this.regfile);
					    });
				});
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
			   * 查询失信被执行人信息
			   */
	    	queryCFList : function(){
	    		var name = $('#name').textfield("getValue");
	    		var cerno = $('#cerno').textfield("getValue");
	    		if (!name && !cerno) {
					jazz.warn("请输入投资人或证照号码！");
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
	    		
	    		
	    		
			/*	if (!name) {
					jazz.warn("请输入投资人！");
					return false;
				}
				if (!cerno) {
					jazz.warn("请输入证照号码！");
					return false;
				}*/
//					//torch.getCount();
			    	//	$("#tips").css("display","block");
		    		//查询失信被执行人信息（DC_BL_LAOLAI），并且有回调函数
		    		var gridUrl="../../TZRController/TZRQueryList.do";
		    		$("#TZRQueryListGrid").gridpanel("option",'datarender',torch.backFunction);//torch.backFunction
		    		$("#TZRQueryListGrid").gridpanel("option",'dataurl',gridUrl);
		    		$("#TZRQueryListGrid").gridpanel('query', ['TZRQueryListPanel'],null);
			},
			
			/**
			 * SYS_FUNCTION_CONFIG列表回调函数
			 */
			backFunction:function (event, obj) {
	    		var data = obj.data;
	    		if (data.length == 0) {
	    			$(".nodata").css("display", "block");
	    			jazz.warn("该查询没有精确匹配到商事主体");
	    		} else {
	    			$(".nodata").css("display", "none");
	    		}
	    		
	    		for (var i = 0; i < data.length; i++) {
	    			data[i]["entname"] = '<a href="javascript:void(0)" title="'+data[i]["entname"] +'" onclick="ebaic.regDetail(\''
	    					+ data[i]["pripid"]
	    					+ '\',\''
	    					+ data[i]["type"]
	    					+ '\',\''
	    					+ data[i]["opetype"]
	    					+ '\',\''
	    					+ data[i]["entstatus"]
			    			+ '\',\''
			    			+ data[i]["entname"]
			    			+ '\',\''
			    			+ data[i]["regno"]
			    			+ '\',\''
							+ data[i]["entid"]
	    					+ '\')">' + data[i]["entname"] + ' </a>';
	    			data[i]["file"] = '<a href="javascript:void(0)" onclick="ebaic.regfile(\''
						+ data[i]["entid"]
						+ '\')">' + '影像' + ' </a>';
	    			data[i]["estdate"] = data[i]["estdate"].substring(0,10);
	    		}
	    		return data;
	    	},
	    	
	    	regfile:function(entid){
	    		var url = "http://aaicweb03/EIMIS/frmShowEntImage.aspx?entid=" + entid;
	    		window.open(url);
	    	},
	    	
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
    				+ encode(entstatus) + "&entname=" + encode(entname)+ "&regno=" + encode(regno)+ "&entid=" + encode(entid);
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
	    	},
	    	edit_fromNames : [ 'TZRQueryListPanel']
		};
	
		torch._init();
		return torch;
});
