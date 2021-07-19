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
	
	edit_primaryValues : "&gid="+ getParameter('gid'),
	edit_fromNames : [ 'linkManDetail_Form'],
	_init : function(){
		require(['domReady'], function (domReady) {
			  domReady(function () {
				  torch.linkManDetailQuery();
				  $("div[name='linkManDetail_save_button']").off('click').click(torch.linkManDetailSave);
				  $("div[name='linkManDetail_reset_button']").off('click').click(torch.linkManDetailReset);
			  });
			});
		
	},
	linkManDetailQuery : function(){
		var updateKey= "&wid=34A8081EA5471FC9E055000000000001";
		$.ajax({
				url:'../../../../torch/service.do?fid=34A8081EA5461FC9E055000000000001'+updateKey+torch.edit_primaryValues,
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
	 * 保存按钮事件。
	 */
	linkManDetailSave : function(){
		var params={		 
				 url:"../../../../torch/service.do?fid=34A8081EA5461FC9E055000000000001"+torch.edit_primaryValues,
				 components: torch.edit_fromNames,
				 callback: function(jsonData,param,res){
					 jazz.info("保存成功");
					 //util.closeWindow("linkManDetail",false);
					// window.parent.ebaic.closeWindow("true");
				 }
			};
			$.DataAdapter.submit(params);
	},
	/**
	 * 返回按钮事件。
	 */
	linkManDetailReset : function(){
		util.closeWindow("linkManDetail",false);
		//window.parent.ebaic.closeWindow("false");
	}
	
};
torch._init();
return torch;

});
