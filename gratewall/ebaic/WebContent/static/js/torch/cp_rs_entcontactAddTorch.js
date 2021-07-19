define(['require', 'jquery', 'entCommon'], function(require, $, entCommon){
	var torch = {
		/**
		 * 保存时参数
		 * @returns {String}
		 */
		edit_primaryValues : function(){
		    return "&entId=" + jazz.util.getParameter('entId');
		},
		edit_fromNames : [ 'cpRsEntContact_Form'],
		/**
		 * 保存
		 */
		cp_rs_entcontactAddSave : function(){
			var params={		 
				 url:"../../../torch/service.do?fid=cp_rs_entcontactAdd"+torch.edit_primaryValues(),
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
		cp_rs_entcontactAddReset : function(){
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
				 
					$("div[name='cp_rs_entcontactAdd_save_button']").off('click').on('click',torch.cp_rs_entcontactAddSave);
					$("div[name='cp_rs_entcontactAdd_reset_button']").off('click').on('click',torch.cp_rs_entcontactAddReset);
					$("div[name='cp_rs_entcontactAdd_goback_button']").off('click').on('click',torch.back_button);//返回
				 });
			});
		 }
	};
	torch._init();
	return torch;
});
