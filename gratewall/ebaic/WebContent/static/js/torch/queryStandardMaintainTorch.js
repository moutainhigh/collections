define(['require', 'jquery', 'common','util'], function(require, $, common,util){
var torch = {
		manageScopeStandardMaintain_datarender : function (event,obj){
	},

	query_fromNames : [ 'manageScopeStandardMaintain_Form'],


	biaoZhunYongYuWeiHuSure : function(){
					$("div[name='manageScopeStandardMaintain_Grid']").gridpanel("option", "dataurl","../../../torch/service.do?fid=37A4813A37143B90E055000000000001&wid=37A4813A37153B90E055000000000001");
					$("div[name='manageScopeStandardMaintain_Grid']").gridpanel("query", [ "manageScopeStandardMaintain_Form"]);
			},
	biaoZhunYongYuWeiHuReset : function(){
				for( x in torch.query_fromNames){
					$("div[name='"+torch.query_fromNames[x]+"']").formpanel("reset");
				} 
			},
	biaoZhunYongYuWeiHuEdit : function(){
		var selectedRules = $("div[name = 'manageScopeStandardMaintain_Grid']").gridpanel('getSelectedRowData');
		
		if(selectedRules.length == 0){
			jazz.warn("请选择需要编辑的数据!");
			return ;
		}
		if(selectedRules.length > 1){
			jazz.warn("只能编辑一条数据!");
			return;
		}
		var editId = selectedRules[0].sjId;
		util.openWindow("edit","修改经营范围标准用语信息","editStandardMaintain.html?sj_id="+editId,1200,500);
	},	
	
	biaoZhunYongYuWeiHuInsert : function(){
		util.openWindow("insert","新增经营范围标准用语信息","insertStandardMaintain.html",1200,500);
	},
	
	biaoZhunYongYuWeiHuDelete : function(){
		var selectedId = $("div[name = 'manageScopeStandardMaintain_Grid']").gridpanel('getSelectedRowData');
		if(selectedId.length > 1){
			jazz.warn("只能删除一条数据!");
			return;
		}
		var delId = selectedId[0].sjId;
		
		$.ajax({
			url : '../../../torch/service.do?fid=394C1DD8C52E28ADE055000000000001&sjId='+delId,
			type:"post",
			dataType : "json",
			async : false,
			success: function(data){
				jazz.info('删除成功!',function(){
					location.reload();
				});
			},
			error : function(data){
				jazz.warn('删除失败 请重试!');
			}
		});
		
		
		//alert(delId);
//		$.ajax({
//			url : '../../../my/test/deleteManagerSope.do?delId='+delId,
//			type:"post",
//			dataType : "json",
//			async : false,
//			success: function(data){
//				jazz.info('删除成功!',function(){
//					location.reload();
//				});
//			},
//			error : function(data){
//				jazz.warn('删除失败 请重试!');
//			}
//		});
	},
			
	 _init : function(){

	 require(['domReady'], function (domReady) {
	    domReady(function () {
	    	document.onkeydown = torch.manageScopeQuery;
	    	//torch.manageScopeQuery();
			$("div[name='_Form']").gridpanel("option","datarender",torch.manageScopeStandardMaintain_datarender);
			$("div[name='_Form']").gridpanel("reload");
			
			$("div[name='biaoZhunYongYuWeiHu_query_button']").off('click').on('click',torch.biaoZhunYongYuWeiHuSure);
			$("div[name='biaoZhunYongYuWeiHu_reset_button']").off('click').on('click',torch.biaoZhunYongYuWeiHuReset);
			$("div[name='biaoZhunYongYuWeiHu_edit_button']").off('click').on('click',torch.biaoZhunYongYuWeiHuEdit);
			$("div[name='biaoZhunYongYuWeiHu_insert_button']").off('click').on('click',torch.biaoZhunYongYuWeiHuInsert);
			$("div[name='biaoZhunYongYuWeiHu_delete_button']").off('click').on('click',torch.biaoZhunYongYuWeiHuDelete);
			
			$('div[name="sortQuery"]').comboxfield('option','dataurl','../../../dmj/querySortStandardMaintain.do?');
			$('div[name="sortQuery"]').comboxfield('option','change',function(){
				torch.biaoZhunYongYuWeiHuSure();
				//alert($('div[name = "sortQuery"]').comboxfield("getText"));
			});
			
			/*
				$("div[name='_Form']").gridpanel("option",'dataparams',{fdm:value});
				$('div[name="sortQuery"]')
				
				$("div[name='_Form']").gridpanel("reload");
			});
			*/
		 });
		});
	 	
	 },
	 
	 
	 manageScopeQuery : function(event){
		 var code = event.keyCode ;
		 if(code == "13"){
			 torch.biaoZhunYongYuWeiHuSure();
		 }
		 
		 
//		 $("body").bind('onkeydown',function(e){
//			if(event.keyCode=='13'){
//				alert("ffff");
//			} 
//		 });
//		 $('div[name  = "manageScopeQuery"]').bind('onkeydown',function(event){
//			 var theEvent = event || window.event;
//			 var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
//			 if(code == "13"){
//				 torch.biaoZhunYongYuWeiHuSure();
//			 }
//		 })
	 }
	 
};
torch._init();
return torch;

});
