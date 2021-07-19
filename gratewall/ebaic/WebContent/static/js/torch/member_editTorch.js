define([ 'require', 'jquery', 'jazz','util' ], function(require, $, jazz,util) {
	var torch = {};
	torch = {
		
		edit_primaryValues : function() {
			var mbrFlag = jazz.util.getParameter('mbrFlag') || '';
			var gid = jazz.util.getParameter('gid') || '';
			return "&entmemberId=" + jazz.util.getParameter('entmemberId')+ "&mbrFlag=" + mbrFlag+"&gid=" + gid;
		},
		edit_fromNames : [ 'entmember_edit_Form' ],
		/**
		 * 编辑保存
		 */
		editSave:function(){
			var mbrFlag = jazz.util.getParameter('mbrFlag') || '';
			var isManager = $("div[name='isManager']").comboxfield("getValue");
			var entMemberId = jazz.util.getParameter('entmemberId') || '';
			var params = {
					url : "../../../torch/service.do?fid=applySetupMbrEdit"
							+ torch.edit_primaryValues(),
					components : torch.edit_fromNames,
					callback : function(jsonData, param, res) {
						jazz.info("保存成功",function(){
//									window.parent.location.reload();
							if(mbrFlag=='3'){
								window.parent.ebaic.getSupervisorsList();
							}else if(mbrFlag=='1'){
								//如果编辑董事页面 中取消兼任经理选项 则从库中删除该主要人员担任经理的记录
								if(isManager == '0'){
									$.ajax({
										url : '../../../apply/setup/member/deleteDirector.do',
										data : {
											entMemberId : entMemberId
										},
										type : 'post',
										async : false,
										success : function(data){
										}
									});
								}
								window.parent.ebaic.getDirectorsList();
								window.parent.ebaic.getManagersList();
								window.parent.ebaic.getLegalDelegatedData();
							}else if(mbrFlag=='2'){
								window.parent.ebaic.getDirectorsList();
								window.parent.ebaic.getManagersList();
								window.parent.ebaic.getLegalDelegatedData();
							}
//									window.parent.ebaic.getSupervisorsList();
//									window.parent.ebaic.getLegalDelegatedData();
							util.closeWindow('cover');
						});
					}
				};
				$.DataAdapter.submit(params);
		},

		applySetupMbrEditSave : function() {
			var mbrFlag = jazz.util.getParameter('mbrFlag') || '';
			var isManager = $("div[name='isManager']").comboxfield("getValue");
			var gid = jazz.util.getParameter('gid') || '';
			//若保存董事信息时，兼任经理，则提示用户是否覆盖原经理信息
			if(mbrFlag=='1'&&isManager=='1'){
				$.ajax({
					url : '../../../apply/setup/member/queryDirector.do',
					data : {
						gid : gid
					},
					type : 'post',
					async : false,
					success : function(data){
						if(data != '0'){
							jazz.confirm("若原来有经理，董事兼任经理会覆盖原来的经理信息，是否覆盖？",function(){
								torch.editSave();
							},function(){
								util.closeWindow('cover');
				    		});
						}else{
							torch.editSave(function(){
								util.closeWindow('cover');
							});
						}
					}
				});
			}else{
				torch.editSave();
			}
			
		},
		cancel : function() {
			/*for (x in torch.edit_fromNames) {
				$("div[name='" + torch.edit_fromNames[x] + "']").formpanel(
						"reset");
			}*/
			//window.parent.location.reload();
			util.closeWindow('cover');
		},
		_init : function() {
			
			require([ 'domReady' ], function(domReady) {
				domReady(function() {

					$("div[name='applySetupMbrEdit_save_button']").off('click')
							.on('click', torch.applySetupMbrEditSave);
					$("div[name='applySetupMbrEdit_cancel_button']")
							.off('click').on('click',torch.cancel);
				});
			});
		}
	};
	torch._init();
	return torch;

});
