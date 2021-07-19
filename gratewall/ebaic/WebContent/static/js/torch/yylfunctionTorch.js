define(['require', 'jquery', 'common','util'], function(require, $, common,util){
var torch = {
		name_datarender : function (event,obj){
	},

	query_fromNames : [ 'name_Form'],


	yylfunctionSure : function(){
					$("div[name='name_Grid']").gridpanel("option", "dataurl","../../../torch/service.do?fid=3781B9225C621C82E055000000000001&wid=3781B9225C791C82E055000000000001");
					$("div[name='name_Grid']").gridpanel("query", [ "name_Form"]);
			},
	yylfunctionReset : function(){
				for( x in torch.query_fromNames){
					$("div[name='"+torch.query_fromNames[x]+"']").formpanel("reset");
				} 
			},
			
			yyladduser : function(){
				//alert("123");
		util.openWindow("add","添加人员信息","../../page/test/adduser.html",1000,500);

			},
			yylupdate: function(){
//				alert("1111");
				var selectRules = $("div[name='name_Grid']").gridpanel('getSelectedRowData');
				if(selectRules.length>1){
					jazz.warn("只能编辑一条数据");
					return;
				}
				if(selectRules.length==0){
					jazz.warn("请选择需要操作的数据");
					return;
				}
				var editId = selectRules[0].id;
//				alert(editId);
				util.openWindow("edit","修改人员信息","../../page/test/leigeEdit.html?id="+editId,1000,500);
			},
			yyldelete : function(){
				var selectRules = $("div[name='name_Grid']").gridpanel('getSelectedRowData');
				if(selectRules.length==0){
					jazz.warn("请选择数据!");
					return;
				}
				var deleteIds = "";
				for(var i=0;i<selectRules.length;i++){
					if(selectRules[i].id){
						deleteIds+=selectRules[i].id;
						if(i!=selectRules.length-1){
							deleteIds+=",";
						}
					}
				}
				jazz.confirm("确定要删除数据吗",function(){
					$.ajax({
					url:'../../../my/test/deleteId.do',
					type:"post",
					async:false,
					dataType:"json",
					data:{
						"deleteIds":deleteIds
					},
					success: function(resultData){
						//alert(resultData);
					//jazz.confirm("确定要删除所选数据吗");
				//    return false;
						jazz.info("删除成功");
						torch.yylfunctionSure();
					}	
				})
				});
			},

			

	 _init : function(){

	 require(['domReady'], function (domReady) {
	    domReady(function () {
			$("div[name='_Form']").gridpanel("option","datarender",torch.name_datarender);
			$("div[name='_Form']").gridpanel("reload");
		 
			$("div[name='yylfunction_query_button']").off('click').on('click',torch.yylfunctionSure);
			$("div[name='yylfunction_reset_button']").off('click').on('click',torch.yylfunctionReset);
			$("div[name='leigeEdit_add_button']").off('click').on('click',torch.yyladduser);
			$("div[name='leigeEdit_update_button']").off('click').on('click',torch.yylupdate);
			$("div[name='leigeEdit_delete_button']").off('click').on('click',torch.yyldelete);
		 });
		});
	 }
};
torch._init();
return torch;

});
