
define(['require','jquery', '../torch/contactTorch','common','validator','apply/setupApproveMsg'], function(reuire, $, contactTorch, common,validator,approveMsg){
    var contact = {
    		
    	_init: function(){
    		require(['domReady'], function (domReady) {
				domReady(function () {
					contact.wrapInfo();
					contact.reLocate();
		    		common.computeHeight();
		    		contact.bindNameData();
		    		$("#fcerType").comboxfield("option","change",contact.fcerTypeChange);
		    		$("#cerType").comboxfield("option","change",contact.cerTypeChange);
		    		$("#name").autocompletecomboxfield("option","change",contact.onEntContactSelected);
		    		$("#fname").autocompletecomboxfield("option","change",contact.onFrContactSelected);
		    		$("img[name='back']").on('click',contact.hideAdvice);//隐藏退回修改意见
					$(".modifyBack").on('click',contact.showAdvice);//出现退回修改意见
				});
				
			});
    	},
    	//点击出现退回修改意见
    	showAdvice:function(){
    		$(".advice").css("display","block");
    		$(".modifyBack").css("display","none");
    	},
    	//点击隐藏退回修改意见
    	hideAdvice:function(){
    		$(".advice").css("display",'none');
    		$(".modifyBack").css("display","block");
    	},
    	edit_primaryValues : function(){return "&gid="+jazz.util.getParameter('gid')},
    	wrapInfo:function(){
    		var gid = jazz.util.getParameter("gid");
    		$(".main").renderTemplate({templateName:"content",insertType:"wrap",wrapSelector:".content"},
    				{gid:gid});
    	},
    	reLocate:function(){
    		var urlstr=location.href;
    		var urlstatus=false;
    		$(".banner a").each(function(){
    			if((urlstr+"/").indexOf($(this).attr("href"))>-1 && $(this).attr("href")!=''){
    				$(this).addClass("blueactive");
    				$(this).find("span:eq(0)").addClass("icon-connect-blue");
    				urlstatus=true;
    			}else{
    				$(this).removeClass("blueactive");
    				$(this).find("span:eq(0)").removeClass("icon-info-blue");
					$(this).find("span:eq(0)").addClass("icon-info");
    			}
    		});
    	},
    	
    	fcerTypeChange : function(){
    		var fcerTypeValue = $("#fcerType").comboxfield("getValue");
    		if(fcerTypeValue=="1"){
    			$("#fcerNo").textfield("option","rule","must_idcard_length;0;20");
    		}else{
    			$("#fcerNo").textfield("option","rule","must_length;0;20");
    		}
    	},
    	
    	cerTypeChange : function(){
    		var cerTypeValue = $("#cerType").comboxfield("getValue")
    		if(cerTypeValue=="1"){
    			$("#cerNo").textfield("option","rule","must_idcard_length;0;20");
    		}else{
    			$("#cerNo").textfield("option","rule","must_length;0;20");
    		}
    	},
    	
    	bindNameData : function (position){
    		var gid = jazz.util.getParameter("gid");
    		//alert(gid);
    		var nameDataUrl= '../../../dmj/queryInvestorsForEntId.do?gid=' +gid;
    		$("#name").autocompletecomboxfield('option', 'dataurl',nameDataUrl);
    		nameDataUrl= '../../../dmj/queryInvestorsForEntId.do?gid=' +gid+'&except=3';
    		$("#fname").autocompletecomboxfield('option', 'dataurl',nameDataUrl);
    		
//    		注释掉reload用来修正自动弹出下拉框选择项。
//    		$("#name").autocompletecomboxfield('reload');

    	},
    	
    	onEntContactSelected : function(event,ui){
    		var ivtId = ui.newValue;
    		//var ivtTypeText = ui.newText;
    		//alert(ivtTypeText);
		  	$.ajax({
				url: "../../../apply/setup/contact/getContactDetail.do?ivtId=" +ivtId,
				type:"post",
				async:false,
				success: function(data){
					if(data){
						if(data.name){
							$("#name").autocompletecomboxfield('setText',data.name);
						}else{
							$("#name").autocompletecomboxfield('setText',data.inv);
						}
						
						$("#cerType").comboxfield('setValue',data.cerType);
						$("#cerNo").textfield('setValue',data.cerNo);
						if(data.tel){
							$("#mobile").textfield('setValue',data.tel);
						}
						if(data.cerType=='1'){
				    		$("#cerNo").textfield("option","rule","must_idcard_length;0;20");
				    	}else{
				    		$("#cerNo").textfield("option","rule","must_length;0;20");
				    	}
					}
				}
			});
    	},
    	
    	onFrContactSelected : function(event,ui){
    		var ivtId = ui.newValue;
		  	$.ajax({
				url: "../../../apply/setup/contact/getContactDetail.do?ivtId=" +ivtId,
				type:"post",
				async:false,
				success: function(data){
					if(data){
						if(data.name){
							$("#fname").autocompletecomboxfield('setText',data.name);
						}else{
							$("#fname").autocompletecomboxfield('setText',data.inv);
						}
						$("#fcerType").comboxfield('setValue',data.cerType);
						$("#fcerNo").textfield('setValue',data.cerNo);
						if(data.tel){
							$("#fmobile").textfield('setValue',data.tel);
						}
						if(data.cerType=='1'){
				    		$("#fcerNo").textfield("option","rule","must_idcard_length;0;20");
				    	}else{
				    		$("#fcerNo").textfield("option","rule","must_length;0;20");
				    	}
					}
				}
			});
    	}
    	
    	
    	
    	
    };
    contact._init();
    return contact;
});

