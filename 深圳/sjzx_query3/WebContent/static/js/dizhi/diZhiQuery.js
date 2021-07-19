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
					    	$('#export_button').button('disable');
				    		$("#export_button").off('click');
					    	
					    	$_this.queryListUserRolesByUserId();
					    	$_this.getSelect();
					    	$_this.getSelectOld();
					    	torch.getZhengZhaoType();
				    		$("#save_button").off('click').on('click',$_this.queryNBList);
						    $("#reset_button").off('click').on('click',torch.resetEditCondition);
						    $("#export_button").off('click').on('click',$_this.exportFunc);
						   
						    util.exports('contactDetail',$_this.contactDetail);
						    util.exports('regDetail',$_this.regDetail);
						    $("#toDates").css({"display":"inline-block"});
						    
						    
						    $("#toDates").css("display","inline-block");
						    $("#toBgDates").css("display","inline-block");
						  
						    //$_this.getTree();
						    $('#isZaiRuYiChang').radiofield('option','itemselect',$_this.changeradio);

						    
					    });
				});
	    	},
	    	changeradio:function(event,value){
	    		var isZaiRuYiChang =$('#isZaiRuYiChang').radiofield('getValue');
	    		if(isZaiRuYiChang=="否"){
	    			$("div[name='yiChangType']").comboxfield("option","disabled",true);
	    		}else{
	    			$("div[name='yiChangType']").comboxfield("option","disabled",false);
	    		}
	    		
	    	},
	    	
	    	
	    	getTree:function(){
	    		var setting = {
	    				data: {
	    					simpleData: {
	    						enable: true
	    					}
	    				},
	    				view: {
	    					fontCss: getFont,
	    					nameIsHTML: true
	    				},
	    				callback: {
	    					onClick: onClick,
	    					/*onCheck:onCheck*/
	    				},
	    				
	    				check: {
	    					enable: true,
	    					chkboxType:{ "Y":'ps', "N":'ps'},
	    					chkDisabledInherit: true
	    				},

	    			};

	    		function getFont(treeId, node) {
	    			return node.font ? node.font : {};
	    		}
	    		
	    		
	            
	            function onCheck(e,treeId,treeNode){
		            var treeObj=   $.fn.zTree.getZTreeObj('treeDemo');
		            nodes=treeObj.getCheckedNodes(true),
		            v="";
		            for(var i=0;i<nodes.length;i++){
		            	v+=nodes[i].name + ",";
		            	console.log("节点id:"+nodes[i].id+"节点名称"+v); //获取选中节点的值
		            }
	            
	            }

	    		
	    		function onClick(){
	    			//alert('点击事件');
	    		}

	    		
	    		var zNodes =[
	    			/*{ id:11, pId:0, name:"请选择异常类型", open:true},
	    			{ id:11, pId:1, name:"父节点11 - 折叠"},
	    			{ id:111, pId:11, name:"叶子节点111",noIcon:true},
	    			{ id:112, pId:11, name:"叶子节点112",noIcon:true},
	    			{ id:113, pId:11, name:"叶子节点113",noIcon:true},
	    			{ id:114, pId:11, name:"叶子节点114",noIcon:true},
	    			{ id:12, pId:1, name:"父节点12 - 折叠"},
	    			{ id:121, pId:12, name:"叶子节点121",noIcon:true},
	    			{ id:122, pId:12, name:"叶子节点122",noIcon:true},
	    			{ id:123, pId:12, name:"叶子节点123",noIcon:true},
	    			{ id:124, pId:12, name:"叶子节点124",noIcon:true},*/
	    			{ id:0, pId:0, name:"请选择异常类型", open:false},
	    			{ id:1, pId:0, name:"通过登记的住所或经营场所无法联系", noIcon:true},
	    			{ id:2, pId:0, name:"未在责令的期限内公示即时信息", noIcon:true},
	    			{ id:3, pId:0, name:"公示企业信息隐瞒真实情况，弄虚作假", noIcon:true},
	    			{ id:4, pId:0, name:"个体工商户年度报告隐瞒真实情况，弄虚作假", noIcon:true},
	    			{ id:5, pId:0, name:"未依照《企业信息公示暂行条例》第八条规定的期限公示年度报告的", noIcon:true},
	    		];
	    		$("#treeDemo").tree({setting: setting, data: zNodes});

	    	},
	    	getZhengZhaoType:function(){
	    		var params = {
	    				url : '../../diZhiQuery/code_value.do',
	    				callback : function(data, param, res) {
	    					var defValue = data.data[0].value;
	    					defaut = defValue;
	    					//$("div[name='yiChangType']").comboxfield('addOption', '', '');
	    					for(var i in data.data){ 
	    						$("div[name='yiChangType']").comboxfield('addOption', data.data[i].text, data.data[i].value);
	    					}
	    					//$("div[name='regorg']").comboxfield('option', 'defaultvalue', defValue);
	    					
	    				}
	    		};
	    		$.DataAdapter.submit(params);
	    		
	    		
	    	},
	    	getSelect:function(){
	    		$("#bgQianSelect").comboxfield('addOption', '不包含', '0');
	    		$("#bgQianSelect").comboxfield('addOption', '包含', '1');
	    		$("div[name='bgQianSelect']").comboxfield('option', 'defaultvalue', 1);
	    	},
	    	
	    	getSelectOld:function(){
	    		$("#bgHouSelect").comboxfield('addOption', '不包含', '0');
	    		$("#bgHouSelect").comboxfield('addOption', '包含', '1');
	    		$("div[name='bgHouSelect']").comboxfield('option', 'defaultvalue',1);
	    	},
	    	
	    	 //获取当前用户的user_id 获取用户的角色，然后返回到前台判断是否有权限  
	    	queryListUserRolesByUserId : function() {
	    		var wnb  = "";
	    		var params = {
	    				url : '../../diZhiQuery/queryUserRolesByUserId.do',
	    				callback : function(data, param, res) {
	    				//	console.log(data.data);
	    					 wnb = data.data;
	    					 if(wnb=="success"){
	    						 $('#export_button').button('enable');
	    				    	 $("#export_button").on('click',$_this.exportFunc);
	    					 }
	    				}
	    		};
	    		$.DataAdapter.submit(params);
			},
	    	/**
	    	 *  导出数据
	    	 */
	    	exportFunc : function(){
	    	
	    		var unif = $("#unifhidden").hiddenfield('getValue');
	    		var entname = $("#entnamehidden").hiddenfield('getValue');
	    	    
	    		var bgQianSelect = $("#bgQianSelecthidden").hiddenfield('getValue');
	    		var altBefore = $("#altBeforehidden").hiddenfield('getValue');
	    		
	    		
	    		var bgHouSelect = $("#bgHouSelecthidden").hiddenfield('getValue');
	    		var altAfter = $("#altAfterhidden").hiddenfield('getValue');
	    		
	    		
	    		var estdate_begin = $("#estdate_beginhidden").hiddenfield('getValue');
	    		var estdate_end = $("#estdate_endhidden").hiddenfield('getValue');
	    		
	    		var bgdate_begin = $("#estdate_beginhidden").hiddenfield('getValue');
	    		var bgdate_end = $("#estdate_endhidden").hiddenfield('getValue');
	    		
	    		var isZaiRuYiChang = $("#SpQymarkhidden").hiddenfield('getValue');
	    		var yiChangType = $("#yiChangTypehidden").hiddenfield('getValue');
	    		
	    		var isnotquery = $("#isnotquery").hiddenfield('getValue');
	    		
	    		if(isnotquery!=""){
		    		/*if(adminbrancode123==""){
		    			jazz.warn("请选择所属工商所！");
		    		}else{*/
		    			$_this.post("../../diZhiQuery/exportExcelDowns.do", {
		    				entname:entname,
		    				unif:unif,
		    				bgQianSelect:bgQianSelect,
		    				altBefore:altBefore,
		    				bgHouSelect:bgHouSelect,
		    				altAfter:altAfter,
		    				estdate_begin:estdate_begin,
		    				estdate_end:estdate_end,
		    				bgdate_begin:bgdate_begin,
		    				bgdate_end:bgdate_end,
		    				isZaiRuYiChang:isZaiRuYiChang,
		    				yiChangType:yiChangType
		    				});
		    		//}
		    		
	    		}else{
	    			jazz.warn("请先查询后再导出！！");
	    		}
	    		
	    	},
	    	post : function(URL, PARAMS) {  
	    		$('#export_button').button('disable');
	    		$("#export_button").off('click');
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
	    	    
	    	    setTimeout(function(){
	    	    	 $('#export_button').button('enable');
			    	 $("#export_button").on('click',$_this.exportFunc);
				}, 30000);
	    	    
	    	    
	    	    
	    	},
	    	
	    	
	    	//查询总条数
	    	getCount:function(){
	    	//	 $("#totals").text("");
	    		var params = {
	    				url : '../../diZhiQuery/queryDiZhiList.do?flag=1',
	    				components: ['SpQyQueryListPanel'],
	    				callback : function(data, param, res) {
	    					$("#totals").html(data.data);
	    				}
	    		};
	    		$.DataAdapter.submit(params);
	    	},
			 /**
			   * 查询信息
			   */
	    	queryNBList : function(){
	    		$("#isnotquery").hiddenfield('setValue',1);
	    		
	    		$("#tips").removeClass("none").css("display","block");
	    		$("#tips").css("display","block");
	    		
	    		
	    		
	    		//$("div[name='yiChangType']")
	    		
	    		var unif = $("#unif").textfield('getValue');//统一社会信用代码
	    		var entname = $("#entname").textfield('getValue');
	    		
	    		
	    		var bgQianSelect = $("#bgQianSelect").comboxfield('getValue');//变更前区域下拉框 
	    		var altBefore = $("#altBefore").textfield('getValue');
	    		
	    		
	    		var bgHouSelect = $("#bgHouSelect").comboxfield('getValue');//变更后区域下拉框 
	    		var altAfter = $("#altAfter").textfield('getValue');
	    		
	    		//成立日期
	    		var estdate_begin = $("#estdate_begin").datefield('getValue');
	    		var estdate_end = $("#estdate_end").datefield('getValue');
	    		
	    		//变更日期
	    		var bgdate_begin = $("#bgdate_begin").datefield('getValue');
	    		var bgdate_end = $("#bgdate_end").datefield('getValue');
	    		
	    		
	    		var SpQymark =$('#isZaiRuYiChang').radiofield('getValue');
	    		
	    		var yiChangType = $("#yiChangType").comboxfield('getValue');
	    		
	    		
	    		
	    		$('#unifhidden').hiddenfield('setValue',unif);
	    		$('#entnamehidden').hiddenfield('setValue',entname);
	    		
	    		$('#bgQianSelecthidden').hiddenfield('setValue',bgQianSelect);
	    		$('#altBeforehidden').hiddenfield('setValue',altBefore);
	    		
	    		
	    		$('#bgHouSelecthidden').hiddenfield('setValue',bgHouSelect);
	    		$('#altAfterhidden').hiddenfield('setValue',altAfter);
	    		
	    		$('#estdate_beginhidden').hiddenfield('setValue',estdate_begin);
	    		$('#estdate_endhidden').hiddenfield('setValue',estdate_end);
	    		
	    		
	    		$('#bgdate_beginhidden').hiddenfield('setValue',bgdate_begin);
	    		$('#bgdate_endhidden').hiddenfield('setValue',bgdate_end);
	    		
	    		$('#SpQymarkhidden').hiddenfield('setValue',SpQymark);
	    		$('#yiChangTypehidden').hiddenfield('setValue',yiChangType);
	    		
	    		
	    		
	    		
	    		
	    	
	    		
	    		torch.getCount();
	    		$('#save_button').button('disable');
	    		$("#save_button").off('click');
	    		/*var timer = null;  
	    		clearTimeout(timer);  
	    			timer = setTimeout(function() { 
	    				$('#save_button').button('enable');
	    	    		$("#save_button").on('click',$_this.queryNBList);
	    		}, 1800);*/
	    			
		    	//查询失信被执行人信息（DC_BL_LAOLAI），并且有回调函数
	    	   var gridUrl="../../diZhiQuery/queryDiZhiList.do";
	    		$("#SpQyQueryListGrid").gridpanel("option",'datarender',torch.backFunction);
	    		$("#SpQyQueryListGrid").gridpanel("option",'dataurl',gridUrl);
	    		$("#SpQyQueryListGrid").gridpanel('query', ['SpQyQueryListPanel'],null);
	    		
				
			},
			/**
			 * SYS_FUNCTION_CONFIG列表回调函数
			 */
			backFunction:function (event, obj) {
				
	    		
	    		$('#save_button').button('enable');
	    		$("#save_button").on('click',$_this.queryNBList);
				var data = obj.data;
	    		if (data.length == 0) {
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
	    					+ data[i]["entstatus"]
			    			+ '\',\''
			    			+ data[i]["entname"]
			    			+ '\',\''
			    			+ data[i]["regno"]
			    			+ '\',\''
			    			+ data[i]["entid"]
			    			+ '\',\''
							+ data[i]["bregno"]
	    					+ '\')">' + data[i]["entname"] + ' </a>';
	    			data[i]["file"] = '<a href="javascript:void(0)" onclick="ebaic.regfile(\''
						+ data[i]["entid"]
						+ '\')">' + '影像' + ' </a>';
	    			//data[i]["estdate"] = data[i]["estdate"].substring(0,10);
	    		}
	    		return data;

	    	},
	    	/**
	    	 * 弹出editFunctionConfig.html页面
	    	 */
	    	regDetail:function(id, enttype, opetype, entstatus, entname, regno, entid,bregno){
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
	    				+ encode(entstatus)+ "&entname=" + encode(entname)+ "&regno=" + encode(regno) + "&entid=" + encode(entid) + "&bregno=" + encode(bregno);
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
	    	contactDetail:function(pripid){ 
	    		util.openWindow("yaoxieyzQueryList","药械企业许可证信息",$_this.getContextPath()+"/page/yaopin/yaoxieyzQueryList.html?pripid="+pripid,850,412);
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
	    				//$('#isZaiRuYiChang').radiofield('setValue','-1');
	    				
	    				
	    				/*var treeObj = $.fn.zTree.getZTreeObj('treeDemo');
	    				treeObj.checkAllNodes(false);
*/
	    				
	    				
	    				//$("div[name='adminbrancode']").comboxfield("option","disabled",true);
	    				/*$('#nbmark').radiofield('setValue','1');
	    				$('#ancheyear').comboxfield('option', 'defaultvalue', defaut);*/
	    			
	    	},
	    	edit_fromNames : [ 'SpQyQueryListPanel']
	    	
		};
	
		torch._init();
		return torch;
});
