define(['require','jquery', '../torch/invCpEditTorch','common','util','../widget/Address'], function(require, $, invCpEditTorch, common,util){
    var torch = {
    	_init: function(){
    		require(['domReady'], function (domReady) {
				domReady(function () {
		    		torch.getInvCpStagespayList();
		    		$('.ivt-money').textfield({vtype:'textfield',change:torch.changeMoney});
		    		$('#domOther').textfield({vtype:'comboxfield',change:torch.domOtherChange});
		    		$("body").parseComponent();
		    		torch.byInfoTab();
		    		torch.initCpInfo();
		    		
		    		$("#localSign").radiofield({itemselect: torch.folkChange});
		    		$("#add_bt").on('click',torch.tr_add);
		    		$("#applySetupInvPersonEdit_query_button").on('click',torch.savePratnter);
		    		$("#applySetupInvPersonEdit_reset_button").on('click',torch.closeCpWindow);
		    		torch.changeMoney();
		    		$(".removeTr").live('click',torch.cleanstagespayList);
		    		$("#address").Address({provCtrlId:'prov',cityCtrlId:'city'});
		    		torch.initInfo();
				});//enf domReady
				
				
				
			});
 
    	},
    	
    	edit_primaryValues : function(){
    		return "&investorId="+jazz.util.getParameter('investorId')
    	},
    	
    	gid : jazz.util.getParameter('gid'),
    	infoTab1 : jazz.util.getParameter('infoTab'),
    	byInfoTab :function(){
    		var infoTab=torch.infoTab1;
    		$("#infoTab").hiddenfield('setValue',infoTab);
    	},
    	getInvCpStagespayList : function(){
    	
    		$.ajax({
    			url:'../../../torch/service.do?fid=applySetupInvCpEdit&wid=applySetupInvCpStagespayList'+torch.edit_primaryValues(),
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
    	
    	savePratnter : function (){
    		var ii=$("#subConAm").hiddenfield("getValue");
    		torch.domOtherChange();
    		if(ii!="0"){
    			torch.setDom();
    			var stagespay = torch.getInvJsonString();
    			var pass = $("#applySetupInvCpSave_Form").formpanel("validate");
    			if(pass){
    				var that = util;
    				var params={
    						 url:"../../../torch/service.do?fid=applySetupInvCpEdit"+torch.edit_primaryValues(),
    						 components: [ 'applySetupInvCpSave_Form'],
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
    							 jazz.info("保存成功!",function(){that.closeWindow('cpW',true);});
    						 }
    					};
    				$.DataAdapter.submit(params);
    			}
    			
    		}else{
    			jazz.warn("请输入出资情况！");
    		}
    		
    	},
		
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
    	
    	tr_add : function() {
    		var $_this = this;
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
    			+		"<div class='ivt-time' width='200' id='time"+num+"' ></div>"			
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
    	
    	cleanstagespayList : function(e){
    		
    		var obj = e.srcElement?e.srcElement:e.target;
    		
    		jazz.confirm("确认删除该出资？",function(){
    			$(obj).parent().remove();
    			torch.changeMoney();

    		},function(){
    			
    		});
    	},    	
    	initInfo : function(){
    		
    		/*var params={
					 url:"../../../apply/setup/inv/getCwpInfo.do",
					 components: ['applySetupInvCpSave_Form'],
					 callback:function(data,param,res){
						// jazz.info("保存成功!",function(){that.closeWindow('cpW',true);});
					 }
				}
			$.DataAdapter.submit(params);*/
    		var infoTab= $("#infoTab").hiddenfield("getValue");
    		var inv=$("#inv").textfield("getValue");
    		$.ajax({
    			url:"../../../apply/setup/inv/getCwpInfo.do",
    			dataType:'json',
    			data:{infoTab:infoTab,inv:inv},
    			type:'post',
    			async:false,
    			success: function(data){
    				if(data.infoTab=='0'){
    					$("#bLicType").comboxfield("setValue",data.bLicType);
        				$("#bLicNo").textfield("setValue",data.bLicNo); 
        				$("#address").Address({provCtrlId:'prov',cityCtrlId:'city'},'',{proValue:data.prov,cityValue:data.city});
        				$("#domOther").textfield("setValue",data.domOther);
    				}else{
    					torch.initCpInfo();
    				}
				}
    		});
    	},
    	initCpInfo : function(){
    		
    		var catId;
    		
    		$.ajax({
				url:"../../../apply/setup/inv/getCatId.do?gid="+torch.gid,
				type:"post",
				async:false,
				dataType:"json",
				success: function(data){
					catId = data;
					if(catId == ""){
//					   jazz.error("获取catId异常");
//					   return;
				   }
				}
			});
    		
    		if(catId=="5100"||catId=="5200"||catId=="5810"){
    			
    			$("#localSign").css("display","block");
    			
    			//TODO  页面加载后判断localSign
//    			if('0'!=data['localSign']){
//    				$("#invType").comboxfield('option', 'dataurl','../../../dmj/getForeignTypes.do');
//    				$("#invType").comboxfield('reload');
//    			}
    		}
    		
    		if(catId=="1200"||catId=="5200"){
    			//以文本形式显示是否发起人选项-固定值
    			$("#initiatorSign1").css("display","block");
    			$("#initiatorSign1").css("width","281px");
    			$("#initiatorSign1").css("padding-left","37px");
    			$("#initiatorSign1").html("<span>是否发起人:&nbsp;&nbsp;是</span>");
    		}	
    		
    		//内资国别和地区只能有中国
    		 var localSignText =$("#localSign").radiofield("getText");
    		 torch.companyTypeChangeForContry(catId,localSignText,"contry");
    		 if(localSignText=="外方"){
    			 $("#domOther").textfield("option","rule","");
    		 }else{
    			 $("#domOther").textfield("option","rule","must");
    		 }
    		 
    		 //TODO  判断是否为一人有限公司
    		 
    		 
    		 
    	},
    	
    	setDom : function(){
      		var domp = $("#prov").comboxfield("getText");
      		var domc = $("#city").comboxfield("getText");
      		var domo = $("#domOther").textfield("getValue");
      		var dom = domp +domc + domo;
      		$("#dom").hiddenfield("setValue",dom);
      	},
    	
    	//内资国别和地区只能有中国
    	companyTypeChangeForContry : function(catId,localSignText,countryId){
    		if(catId>5000){//外资
    			torch.localSignChangeForContry(localSignText,countryId)
    		}else{//内资
    			torch.chinaContry(countryId);
    		}
    	},
    	
    	localSignChangeForContry : function(localSign,countryId){
    		if(localSign=="中方"){
    			//国别和地区只能选中国
    			torch.chinaContry(countryId);
    		}else{//外方
    			torch.foreignContry(countryId);
    		}
    	},
    	
    	folkChange : function(){
    		var localSign =$("#localSign").radiofield("getText");
    		if(localSign=="外方"){
//    			$("#domOther").textfield("option","rule","");
    			torch.foreignContry("contry");
    		}else{
//    			$("#domOther").textfield("option","rule","must");
    			torch.chinaContry("contry");
    		}
    		
    		torch.switchLocalSign(localSign);
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
    	},
    	
    	switchLocalSign : function(localSign){
    		
    		if(localSign=="外方"){
    			$("#invType").comboxfield('option', 'dataurl','../../../dmj/getForeignTypes.do');
    			$("#invType").comboxfield('reload');
    		}else{
    			$("#invType").comboxfield('option', 'dataurl','../../../dmj/getIvtTypes.do');
    			$("#invType").comboxfield('reload');
    		}
    	},
    	
    	domOtherChange : function(){
    		var address_detail=$("#domOther").textfield("getValue");
    		address_detail=torch.CtoH(address_detail);
    		$("#domOther").textfield("setValue",address_detail);
    	},
    	
    	CtoH : function(str) //全角半角转换。
    	{
    	    var result="";
    	    for (var i = 0; i < str.length; i++)
    	    {
    	        if (str.charCodeAt(i)==12288)
    	        {
    	            result+= String.fromCharCode(str.charCodeAt(i)-12256);
    	            continue;
    	         }
    	        if (str.charCodeAt(i)>65280 && str.charCodeAt(i)<65375){
    	        	result+= String.fromCharCode(str.charCodeAt(i)-65248);
    	        }else{
    	        	result+= String.fromCharCode(str.charCodeAt(i));
    	        }
    	     }
    	     return result;
    	}, 
    	
    	closeCpWindow : function(){
    		 util.closeWindow('cpW',false);
    	},
    	
    	beiJingCompanyDataReturn : function(){
    		var investorId = jazz.util.getParameter('investorId');
    		$.ajax({
    			url : "../../../dmj/beiJingCompanyDataReturn.do?investorId="+investorId,
    			type : "post",
    			async : false,
    			success : function(data){
    				
    			}
    		});
    	}
    };
    torch._init();
    return torch;
});

