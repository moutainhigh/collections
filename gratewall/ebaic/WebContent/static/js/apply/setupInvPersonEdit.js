define(['require','jquery', '../torch/invPersonEditTorch','common','util','validator'], function(require, $, invPersonEditTorch, common, util){
    var torch = {};
	torch = {
  
    	_init: function(){
    		require(['domReady'], function (domReady) {
				domReady(function () {
					
					/**用于展示出资明细列表**/
					torch.getInvPersonCon();
					$("body").parseComponent();
					/**自动计算出资总额**/
		    	    $('.ivt-money').textfield({vtype:'textfield',change:torch.changeMoney});
		    	    /**页面加载计算出资总额**/
		    	    torch.changeMoney();
		    		/**股东信息编辑页面展示**/
		    		torch.initPersonInfo();
//		    		$("#localSign").radiofield({itemselect: torch.localSignChange});
		    		/**根据身份证号码识别性别**/
		    		$("#cerNo").textfield('option','change',torch.cerNoChange);
		    		/**页面一加载若身份证号不为空则直接确定性别**/
		    		torch.cerNo2sex();
//		    		$("#cerType").comboxfield('option','change',torch.cerTypeChange);
		    		/**追加出资明细**/
		    	    $('#add_bt').on('click',torch.tr_add);
		    		$('#applySetupInvPersonEdit_query_button').on('click',torch.savePratnter);
		    		$("#applySetupInvPersonEdit_reset_button").on('click',torch.closePersonWindow);
		    		/**删除出资明细**/
		    		$(".removeTr").live('click',torch.cleanstagespayList);
		    	    
				});//enf domReady
			});
    		
    		
    	},
    	
    	edit_primaryValues : "&investorId="+jazz.util.getParameter('investorId')+"&gid="+jazz.util.getParameter('gid'),
    	
    	gid : jazz.util.getParameter('gid'),
    	
    	
    	/**用于展示出资明细列表**/
    	getInvPersonCon : function(){
    		$.ajax({
    			url:'../../../torch/service.do?fid=applySetupInvPersonEdit&wid=applySetupInvPersonCon'+torch.edit_primaryValues,
    			type:"post",
    			async:false,
    			dataType:"json",
    			success: function(data){
    				if(data==null){
    					$(".formpanel").renderTemplate({templateName:"list_table",insertType:"append"},
    							{tableTitle:"出资情况",col: [{title:"出资金额(万元)"},{title:"出资方式"},{title:"出资时间"},{title:"操作"}]});
    				}else{
    					
    					$(".formpanel").renderTemplate({templateName:"list_table",insertType:"append"},
    							{tableTitle:"出资情况",
    							col: [{title:"出资金额(万元)"},{title:"出资方式"},{title:"出资时间"},{title:"操作"}],
    							data:data.data[0].data}
    							);
    				}
    			}
    		});
    	},
    	

    	//出资金额变化总值显示
    	changeMoney : function moneychange(){
    		var allMoney=Number(0);
    		$(".ivt-money").each(function(){
    			if($(this).find(".jazz-field-comp-r").hasClass("jazz-helper-hidden")){
    				allMoney+=Number($(this).textfield("getValue"));
    			}
    			
    		});
    		$("#subConAm").hiddenfield("setValue",allMoney);
    		$("#subConAmSpan").html(allMoney);
    	},
    	// 股东信息弹出界面中Table添加带有组件的行
    	tr_add : function() {
    		var num = new Date().getTime();
    		var code="<tr class='ivt-row'>" 
    			+   "<td style='display:none;'>"
    			+	"</td>"
    		    +   "<td>"
    			+		"<div class='ivt-money' id='money"+num+"' ></div>"
    			+	"</td>"
    			+	"<td>"
    			+		"<div class='ivt-way' width='200' id='way"+num+"' rule='must'></div>"
    			+	"</td>"
    			+	"<td>"
    			+		"<div class='ivt-time' width='200'id='time"+num+"' ></div>"			
    			+	"</td>"
    			+	"<td class='removeTr'>"
    			+	"</td>"
    			+ "</tr>";
    		
    	    $("#list-table").append(code);
    	    
		    $('#money'+num).textfield({name:'money'+num, vtype:'textfield',rule:'must_number_contrast;>=0',change:torch.changeMoney});
		    $('#way'+num).comboxfield({name:'way'+num, vtype:'comboxfield', dataurl:'../../../dictionary/queryData.do?dicId=CA22',rule:'must'});
			$('#time'+num).datefield({name:'time'+num, editable:false,vtype:'datefield', rule:'must'});
			$("#applySetupInvCpSave_Form").parseComponent();
    	},
    	
    	//保存修改信息
    	savePratnter : function (){
    		var ii=$("#subConAm").hiddenfield("getValue");
//    		domOtherChange();
    		if(ii!="0"){
    			torch.setDom();
    			
    			var stagespay = torch.getInvJsonString();
    			var pass = $("#ivtFormPanel").formpanel("validate");
    			if(pass){
    				var that = util;
    				var params={
    						 url:"../../../torch/service.do?fid=applySetupInvPersonEdit"+torch.edit_primaryValues,
    						 components: [ 'applySetupInvPersonSave_Form'],
    						 params:{
    							 gid:torch.gid,
    							 stagespay : stagespay
    						 },
    						 callback:function(data,param,res){
    							
//    							 var ivtId =res.getAttr("ivtId");
//    							 if(ivtId){
//    								 if(ivtId=="false"){
//    									 jazz.error("投资人已存在，请检查身份证号。");
//    								 }else{
//    									 window.parent.bindDataOnPageLoad();
//    								 	 window.parent.closeInvestorEditWindow();			
//    								 }
//    							 }else{
//    								 jazz.info("保存失败，请联系系统维护人员。");
//    							 }
    							 jazz.info("保存成功!",function(){that.closeWindow('personW',true);});
    						 }
    					};
    				$.DataAdapter.submit(params);
    			}
    		}else{
    			jazz.warn("请输入出资情况！");
    		}
    		
    	},
    	
    	//获取投资人出资json字符串
    	getInvJsonString : function () {
    		var invArray = new Array();
    		$(".ivt-row").each(function() {
    			var inv_money = $($(this).find(".ivt-money")).textfield('getValue');
    			var inv_way = $($(this).find(".ivt-way")).comboxfield('getValue');
    			var inv_time = $($(this).find(".ivt-time")).datefield('getValue');
    			var inv = {
    				curActConAm : inv_money,
    				conForm : inv_way,
    				conDate : inv_time,
    			};
    			invArray.push(inv);
    		});
    		var invStr = JSON.stringify(invArray);
    		return invStr;
    	},
    	
    	
    	
    	cleanstagespayList : function(e){
    		
    		var obj = e.srcElement?e.srcElement:e.target;
    		
    		jazz.confirm("确认删除该出资？",function(){
    			$(obj).parent().remove();
    			torch.changeMoney();

    		},function(){
    			
    		});
    	},
    	
    	closePersonWindow : function(){
      		 util.closeWindow('personW',false);
      	},
      	
      	//初始化页面的一些判断
      	initPersonInfo : function(){
      		
      		var catId = "1100";
    		
//    		$.ajax({
//				url:"../../../../apply/setup/inv/getCatId.do?gid="+torch.gid,
//				type:"post",
//				async:false,
//				dataType:"json",
//				success: function(data){
//					catId = data;
//					if(catId == ""){
//					   jazz.error("获取catId异常");
//					   return;
//				   }
//				}
//			});
      		
      		//外资显示英文名称
      		if(catId=="5100"||catId=="5200"||catId=="5810"){
//      			$("#localSign").removeClass("hide");
//      			$("#nameEng").removeClass("hide");
      		}
      		
      		if(catId=="1200"||catId=="5200"){		
      			//以文本形式显示是否发起人选项-固定值
//      			$("#initiatorSign1").removeClass("hide");
      		}else{
      			$("#initiatorSign1").addClass("hide");
      		}
      		
      		torch.localSignChange();
      		
      		/**根据全流程电子化需求，证件类型只能是身份证,国籍是中国**/
      		$("#cerType").comboxfield('setValue','1');
      		$("#contry").comboxfield('setValue','156');

      	},
      	
       //身份证校验
//      	cerTypeChange : function(){
//      		if($("#cerType").comboxfield("getValue")=="1"){
//      			$("#cerNo").textfield("option","rule","must_idcard_length;0;20");
//      		}else{
//      			$("#cerNo").textfield("option","rule","must_length;0;20");
//      		}
//      	},
      	
      	
      	localSignChange : function(){
      		var localSign =$("#localSign").radiofield("getText");

      		 if(localSign=="外方"){
//      			$("#folk").comboxfield("setValue","00");
//      			$("#folk").comboxfield("option","rule","");
      			 
//      			$("#domProv").find("label").html("<font color='red'>*</font>在中国居住地址:");
//      			
//      			$("#domOther").textfield("option","rule","length;0;64");
//      			torch.foreignCerType("cerType");
//      			torch.foreignContry("contry");
      		}else{
      			$("#folk").comboxfield("setValue","");
//      			$("#folk").comboxfield("reset");
      			$("#folk").comboxfield("option","rule","must");

      			$("#domOther").textfield("option","rule","must_length;0;40");
      			
      			//证件类型不能出现外资和港澳台的证件
//      			torch.chinaCerType("cerType");
      			//国别和地区只能选中国
//      			torch.chinaContry("contry");
      		} 
      	},
      	
      	setDom : function(){
      		var domp = $("#prov").comboxfield("getText");
      		var domc = $("#city").comboxfield("getText");
      		var domo = $("#domOther").textfield("getValue");
      		var dom = domp +domc + domo;
      		$("#dom").hiddenfield("setValue",dom);
      	},
      	
      	//根据身份证号码识别性别
      	cerNoChange : function(){
      		if($("#cerNo").textfield("option","rule")=="must_idcard_length;0;20"){
      			var cerno=$("#cerNo").textfield("getValue");
      			if(cerno && cerno.length==18){
      				cerno = cerno.substr(16, 1);
      				if(Number(cerno)% 2==1){
//      				$("#sex").radiofield("setValue","1");
	      				$("#sex").comboxfield("setValue","1");
	      			}else{
	//      				$("#sex").radiofield("setValue","2");
	      				$("#sex").comboxfield("setValue","2");
	      			}
      			}else if(cerno && cerno.length==15){//15位身份证号
      				cerno = cerno.substr(14, 1);
      				if(Number(cerno)% 2==1){
	      				$("#sex").comboxfield("setValue","1");
	      			}else{
	      				$("#sex").comboxfield("setValue","2");
	      			}
      			}
      			
      		}
      	},
      	
      	//页面一加载如果身份证不为空判断性别
      	cerNo2sex : function(){
      		var cerNo = $("#cerNo").textfield('getValue');
      		if(cerNo){
      			torch.cerNoChange();
      		}
      	},
      	
        //内资类型的证件号码选项
      	chinaCerType : function(idName){
      		$("#"+idName).comboxfield("option","dataurl","../../../dmj/queryCerType.do?invType=1");
      		$("#"+idName).comboxfield("reload");
      	},
      	
      	foreignCerType : function(idName){
      		$("#"+idName).comboxfield("option","dataurl","../../../dmj/queryCerType.do?");
      		$("#"+idName).comboxfield("reload");
      	},
      	
        //国别和地区只包含中国
      	chinaContry : function(idName){
      		//queryContry
      		$("#"+idName).comboxfield("option","dataurl","../../../dmj/queryContry.do?invType=1");
      		$("#"+idName).comboxfield("reload");
      		$("#"+idName).comboxfield("setValue","156");
      	},
      	
      	//全部国别地区
      	foreignContry : function(idName){
      		$("#"+idName).comboxfield("option","dataurl","../../../dictionary/queryData.do?dicId=CA02");
      		$("#"+idName).comboxfield("reload");
      	}
      	

    };
    torch._init();
    return torch;
});

