define(['require', 'jquery', 'entCommon'], function(require, $, entCommon){
var torch = {
	
	edit_primaryValues : function(){
	    return "&entId=" + jazz.util.getParameter('entId')
	},
	edit_fromNames : [ 'cpRsEntContact_Form'],
	/**
	 * 回显
	 */
	cp_rs_entcontactQuery : function(){
		var updateKey= "&wid=cp_rs_entcontact";
		$.ajax({
				url:'../../../torch/service.do?fid=cp_rs_entcontact'+updateKey+torch.edit_primaryValues(),
				type:"post",
				async:false,
				dataType:"json",
				success: function(data){
					var jsonData = data.data;
					if($.isArray(jsonData)){
						 for(var i = 0, len = jsonData.length; i<len; i++){
							 $("div[name='"+jsonData[i].name+"']").formpanel("setValue",jsonData[i] || {});
						 }
					 }
				}
			});
	},
	/**
	 * 编辑保存
	 */
	cp_rs_entcontactSave : function(){
		var params={		 
			 url:"../../../torch/service.do?fid=cp_rs_entcontact"+torch.edit_primaryValues(),
			 components: torch.edit_fromNames,
			 callback: function(jsonData,param,res){
				 jazz.info("保存成功");		
			 }
		};
		$.DataAdapter.submit(params);
	},
	/**
	 * 重置
	 */
	cp_rs_entcontactReset : function(){
		for( x in torch.edit_fromNames){
			$("div[name='"+torch.edit_fromNames[x]+"']").formpanel("reset");
		} 
	},
	/**
	 * 返回企业登录主页
	 */
	back_button:function(){
		window.location.href = "../../../page/apply/ent_account/home.html";
	},
	/**
	 * 初始化
	 */
	 _init : function(){
		 require(['domReady'], function (domReady) {
		    domReady(function () {
				torch.cp_rs_entcontactQuery();
				
				$(document).keypress(function(e) {
					if (e.which == 13) {
						//回车保存修改
						torch.cp_rs_entcontactSave();
					}
				});
				$("div[name='cp_rs_entcontact_save_button']").off('click').on('click',torch.cp_rs_entcontactSave);
	//			$("div[name='cp_rs_entcontact_reset_button']").off('click').on('click',torch.cp_rs_entcontactReset);
				$("div[name='cp_rs_entcontact_back_button']").off('click').on('click',torch.back_button);
			 });
		});
		 $("div[name='cpRsEntContact_Form']").css("margin","-20px auto 0px");
	 }
};
torch._init();
return torch;

});
