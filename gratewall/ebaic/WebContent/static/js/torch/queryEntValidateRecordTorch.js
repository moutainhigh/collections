define(['require', 'jquery', 'entCommon'], function(require, $, entCommon){
var torch = {
		
	queryEntValidate_datarender : function (event,obj){
		var data=obj.data;
		var code="";
		var label="";
		var url="";
		for(var i=0,len=data.length;i<len;i++){
			if(!data[i]){
				continue;
			}
			var htm="<div class='table-panel-tr'>"
				+"<span class='index-span-index'></span>"
				+"<div class='ent-name omit'><p style='width:150px;text-align:center'><span class='pr6'>认证码:</span>"+data[i]['authCode']+"</p></div>"
				+"<div class='ent-name omit'><p style='width:150px;text-align:center;'><span class='pr6'>服务码:</span>"+data[i]['operCode']+"</p></div>"			
				+"<div class='ent-name omit'><p style='width:280px;text-align:center'><span class='pr6'>触发认证时间:</span>"+data[i]['validateCodeStartTime']+"</p></div>";
				
				//显示是否已经通过，未通过，关于使用验证码，可以在再送一次ajax请求
				if(data[i].state==1){
					htm +="<div class='ent-name omit'><p style='width:280px;text-align:center;color:#21C02B'>已通过</p></div>";
				}else{
					htm +="<div class='ent-name omit'><p style='width:280px;text-align:center;color:#E70101'>未通过</p></div>";
				}
			
			    
			    //关闭
			    htm += "</div></div>";
				data[i]["custom"] = htm;
		}
		
		return data;
	},

	query_fromNames : [ 'queryEntValidate_Form'],


	queryEntValidateRecordSure : function(){
		$("div[name='queryEntValidate_Grid']").gridpanel("option","datarender",torch.queryEntValidate_datarender);
		$("div[name='queryEntValidate_Grid']").gridpanel("option", "dataurl","../../../torch/service.do?fid=query_ent_validate_record&wid=query_ent_validate_record");
		$("div[name='queryEntValidate_Grid']").gridpanel("query", [ "queryEntValidate_Form"]);
	},
	queryEntValidateRecordReset : function(){
				for( x in torch.query_fromNames){
					$("div[name='"+torch.query_fromNames[x]+"']").formpanel("reset");
				} 
			},
	/**
	 * 返回企业登录主页
	 */
	back_button:function(){
		window.location.href = "../../../page/apply/ent_account/home.html";
	},
	/*列表数据格式化*/
	myOperation_datarender:function(event,obj){
		
	},
	
	 _init : function(){
		 
	 require(['domReady'], function (domReady) {
	    domReady(function () {
	    	torch.queryEntValidateRecordSure();
			//回车查询
			$(document).keypress(function(e) {
				if (e.which == 13) {
					//回车查询
					torch.queryEntValidateRecordSure();
				}
			});
			$("div[name='queryEntValidateRecord_query_button']").off('click').on('click',torch.queryEntValidateRecordSure);
			$("div[name='queryEntValidateRecord_reset_button']").off('click').on('click',torch.queryEntValidateRecordReset);
			$("div[name='queryEntValidateRecord_back_button']").off('click').on('click',torch.back_button);
			
		 });
		});
	 }
};
torch._init();
return torch;

});
