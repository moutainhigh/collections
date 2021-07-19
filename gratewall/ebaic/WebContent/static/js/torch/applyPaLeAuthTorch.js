define([ 'require', 'jquery', 'common' ], function(require, $, common) {
	var torch = {

		edit_primaryValues : function() {
			return "&gid=" + jazz.util.getParameter('gid');
		},
		edit_fromNames : [ 'applyPaLeAuth_Form' ],

		applyPaLeAuthQuery : function() {
			var url = "../../../torch/service.do?fid=applyPaLeAuth&wid=applyPaLeAuthQuery&gid="+jazz.util.getParameter('gid');
			$("div[name='applyPaLeAuthQuery_Form']").formpanel("option", "dataurl",url);
			$("div[name='applyPaLeAuthQuery_Form']").formpanel("reload");
			
		},

		applyPaLeAuthSave : function() {
			var params = {
				url : "../../../../../torch/service.do?fid=applyPaLeAuth"
						+ torch.edit_primaryValues(),
				components : torch.edit_fromNames,
				callback : function(jsonData, param, res) {
					jazz.info("保存成功");
				}
			};
			$.DataAdapter.submit(params);
		},
		
		_init : function() {

			require([ 'domReady' ], function(domReady) {
				domReady(function() {

					torch.applyPaLeAuthQuery();
					$("div[name='applyPaLeAuth_save_button']").off('click').on(
							'click', torch.applyPaLeAuthSave);
					$("div[name='applyPaLeAuth_reset_button']").off('click')
							.on('click', torch.applyPaLeAuthReset);
				});
			});
		}
	};
	torch._init();
	return torch;

});
