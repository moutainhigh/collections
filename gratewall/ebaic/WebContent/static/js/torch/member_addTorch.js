define([ 'require', 'jquery', 'jazz','util'], function(require, $, jazz,util) {
	var torch = {};
	torch = {

		edit_primaryValues : function() {
			var gid = jazz.util.getParameter('gid') || '';
			var mbrFlag = jazz.util.getParameter('mbrFlag') || '';
			var isBoard = jazz.util.getParameter('isBoard') || '';
			var isSuped = jazz.util.getParameter('isSuped') || '';
			return "&gid=" + gid + "&mbrFlag=" + mbrFlag + "&isBoard=" + isBoard + "&isSuped=" + isSuped;
		},
		edit_fromNames : [ 'entmemberAdd_Form' ],
		/**
		 * 添加保存
		 */
		addSave:function(){
			var mbrFlag = jazz.util.getParameter('mbrFlag') || '';
			var params = {
				url : "../../../torch/service.do?fid=applySetupMbrAdd"
						+ torch.edit_primaryValues(),
				components : torch.edit_fromNames,
				callback : function(jsonData, param, res) {
					jazz.info("保存成功", function() {
//						window.parent.location.reload();
						// 关闭当前窗口
						if(mbrFlag=='3'){
							window.parent.ebaic.getSupervisorsList();
						}else if(mbrFlag=='1'){
							window.parent.ebaic.getDirectorsList();
							//window.parent.ebaic.reDeployLegalDelegate();
							window.parent.ebaic.getManagersList();
						}else if(mbrFlag=='2'){
							window.parent.ebaic.getManagersList();
							//window.parent.ebaic.reDeployLegalDelegate();
						}
						window.parent.ebaic.getLegalDelegatedData();
						util.closeWindow('addMbrWin');
					});
				}
			};
			$.DataAdapter.submit(params);
		},
		applySetupMbrAddSave : function() {
			var mbrFlag = jazz.util.getParameter('mbrFlag') || '';
			var isManager = $("div[name='isManager']").comboxfield("getValue");
			//若保存董事信息时，兼任经理，则提示用户是否覆盖原经理信息
			if(mbrFlag=='1'&&isManager=='1'){
				jazz.confirm("若原来有经理，董事兼任经理会覆盖原来的经理信息，是否覆盖？",function(){
					torch.addSave();
				},function(){
					util.closeWindow('addMbrWin');
	    		});
			}else{
				torch.addSave();
			}
		},
		applySetupMbrAddReset : function() {
			/*for (x in torch.edit_fromNames) {
				$("div[name='" + torch.edit_fromNames[x] + "']").formpanel(
						"reset");
			}*/
			//window.parent.location.reload();
			util.closeWindow('addMbrWin');
		},
		_init : function() {

			require([ 'domReady' ], function(domReady) {
				domReady(function() {

					$("div[name='applySetupMbrAdd_save_button']").off('click')
							.on('click', torch.applySetupMbrAddSave);
					$("div[name='applySetupMbrAdd_reset_button']").off('click')
							.on('click', torch.applySetupMbrAddReset);
				});
			});
		}
	};
	torch._init();
	return torch;

});
