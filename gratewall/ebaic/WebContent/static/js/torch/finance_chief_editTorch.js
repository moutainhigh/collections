define(['require', 'jquery', 'approvecommon' ], function(require, $, approvecommon) {
	var getParameter = function(name){
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); 
		var r = window.location.search.substr(1).match(reg); 
		if (r!=null) { 
		   return unescape(r[2]); 
		} 
		return null; 
	};
	var torch = {

		edit_primaryValues : "&contactId=" + getParameter('contactId'),
		edit_fromNames : [ 'updateFinanceC_Form' ],

		financeChiefEditQuery : function() {
			var updateKey = "&wid=financeCECfgId";
			$.ajax({
				url : '../../../torch/service.do?fid=financeCEFuncId' + updateKey + torch.edit_primaryValues,
				type : "post",
				async : false,
				dataType : "json",
				success : function(data) {
					var jsonData = data.data;
					if ($.isArray(jsonData)) {
						for ( var i = 0, len = jsonData.length; i < len; i++) {
							$("div[name='" + jsonData[i].name + "']").formpanel("setValue", jsonData[i] || {});
						}
					}
				}
			});
		},
		financeChiefEditSave : function() {
			var params = {
				url : "../../../torch/service.do?fid=financeCEFuncId" + torch.edit_primaryValues,
				components : torch.edit_fromNames,
				callback : function(jsonData, param, res) {
					jazz.info("保存成功");
				}
			};
			$.DataAdapter.submit(params);
		},
		financeChiefEditReset : function() {
			for (x in torch.edit_fromNames) {
				$("div[name='" + torch.edit_fromNames[x] + "']").formpanel("reset");
			}
		},
		_init : function() {
			require(['domReady'], function (domReady) {
				  domReady(function () {
					  torch.financeChiefEditQuery();
					  $("div[name='financeChiefEdit_save_button']").off('click').click(torch.financeChiefEditSave);
					  $("div[name='financeChiefEdit_reset_button']").off('click').click(torch.financeChiefEditReset);
				  });
				});
			
		},
		changeMust:function(){
			
		}
	};
	torch._init();
	return torch;

});
