define(['require','jquery', 'approvecommon','util'], function(require, $, approvecommon,util){
var getParameter = function(name){
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); 
	var r = window.location.search.substr(1).match(reg); 
	if (r!=null) { 
	   return unescape(r[2]); 
	} 
	return null; 
};
var torch = {
	
	_init : function(){
		require(['domReady'], function (domReady) {
			  domReady(function () {
				  $("div[name='passButton']").off('click').click(torch.func_pass);
				  $("div[name='tstdButton']").off('click').click(torch.func_tstd);
				  $("div[name='tdApplyButton']").off('click').click(torch.func_tstdApply);
				  $("div[name='closeButton']").off('click').click(torch.closeWindow);
				  $("div[name='notionButton']").off('click').click(torch.func_notion);
				  
				  $("div[name='passButton']").hide();
				  $("div[name='tstdButton']").hide();
				  $("div[name='tdApplyButton']").hide();
				  torch.initRuleList();
			  });
			});
		
	},
	initRuleList : function(){
		$("div[name='interceptRuleGrid']").gridpanel("option", "datarender",function(obj,resultData){
			var showFlag = true;
			var dataArrs=resultData.data;
			if(dataArrs.length>0){
				for(var i=0;i<dataArrs.length;i++){
					if(dataArrs[i].type=='锁定'){
						showFlag=false;
						break;
					}
				}
			}
			if(showFlag==true){
				 $("div[name='passButton']").show();
			}else{
				 $("div[name='tstdButton']").show();
				 $("div[name='tdApplyButton']").show();
			}
			if(dataArrs.length==0){
				window.returnValue=true;
		        window.close();
			}
		});
		$("div[name='interceptRuleGrid']").gridpanel("option", "dataurl","../../../approve/process/exeRule.do?gid="+getParameter('gid'));
		
		$("div[name='interceptRuleGrid']").gridpanel("reload");
	},
	
	/**
	 * 告知按钮事件
	 */
	func_notion:function(){
		var ruleInfo = $("div[name='interceptRuleGrid']").gridpanel("getSelectedRowData");
		if(ruleInfo.length==0){
			jazz.warn("请至少选择一项！");
			return false;
		}
		var ruleTip = '';
		for(var i=0;i<ruleInfo.length;i++){
			ruleTip = ruleTip + ruleInfo[i].msg +";";
		}
		window.returnValue = ruleTip;
		window.close();
		
		/*var gid = getParameter('gid');
		$.ajax({
			url:'../../../approve/process/exeRule.do',
			data:{
				gid:gid
			},
			type:'post',
			dataType:'json',
			success:function(data){
				var arr = data.data[0].data.rows;
				var ruletip = "";
				for(var i=0;i<arr.length;i++){
					ruletip = ruletip + arr[i].msg +";";
				}
				window.returnValue = ruletip;
				window.close();
			}
			
		});*/
	},
	
	
	/**
	 * 通过按钮事件。
	 */
	func_pass : function(){
		window.returnValue=true;
        window.close();
	},
	/**
	 * 特殊通道
	 */
	func_tstd:function(){
		var selectRules = $("div[name='interceptRuleGrid']").gridpanel('getAllData');//getSelectedRowData
		var ruleStepIds = "";
		for(var i=0;i<selectRules.length;i++){
			if(selectRules[i].type=='锁定'){
				ruleStepIds+=selectRules[i].ruleStepId;
				if(i!=selectRules.length-1){
					ruleStepIds+=",";
				}
			}
		}
		$.ajax({
			url:'../../../approve/process/executeTstdRules.do',
			type:"post",
			async:false,
			dataType:"json",
			data:{
				"gid":getParameter('gid'),
				"ruleStepIds":ruleStepIds
			},
			success: function(resultData){
				if(resultData=="noPass") {
			    	jazz.warn("特殊通道不能通过所有规则！");
			    }
				if(resultData=="error"){
			    	jazz.warn("没有可以使用的特殊通道！");
			    }
				if(resultData=="pass"){
					window.returnValue=true;
					window.close();
			    } 
			}	
		});
	},
	/**
	 * 特殊通道申请
	 */
	func_tstdApply:function(){
		var selectRules = $("div[name='interceptRuleGrid']").gridpanel('getSelectedRowData');
		if(selectRules.length==0){
			jazz.warn("请至少选择一条需要申请特殊通道的锁定类型的规则！");
			return;
		}
		var count=0;
		var ruleStepIds = "";
		for(var i=0;i<selectRules.length;i++){
			if(selectRules[i].type=='锁定'){
				ruleStepIds+=selectRules[i].ruleStepId;
				count++;
				if(i!=selectRules.length-1){
					ruleStepIds+=",";
				}
			}
		}
		if(count>0){
			jazz.confirm("确认要发起特殊通道申请？",function(){
				$.ajax({
					url:'../../../approve/process/createTstd.do',
					type:"post",
					async:false,
					dataType:"json",
					data:{
						"gid":getParameter('gid'),
						"ruleStepIds":ruleStepIds
					},
					success: function(resultData){
						jazz.info(resultData);
					}	
				});
			});
		}
	},
	/**
	 * 关闭窗口事件。
	 */
	closeWindow : function(){
		window.close();
	}
};
torch._init();
return torch;

});
