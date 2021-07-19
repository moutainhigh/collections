define(['require', 'jquery', 'approvecommon'], function(require, $, common){
	
	var getParameter = function(name){
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); 
		var r = window.location.search.substr(1).match(reg); 
		if (r!=null) { 
		   return unescape(r[2]); 
		} 
		return null; 
	};
	
var torch = {
	
	  gid : getParameter('gid'),
	 _init : function(){

	 require(['domReady'], function (domReady) {
	    domReady(function () {
	    	
	    	torch.initTab();
	    	
	    	
	    	$("#ifm1").attr('src','detailReqAllInfo.html?gid='+torch.gid);
	    	$('#tab_id').find('a').on('click',function(){
	    		var frame = $(this).attr('tab');
	    		if(!($('#'+frame).attr('src'))){
	    			if(frame == 'ifm1'){
	    				$("#ifm1").attr('src','detailReqAllInfo.html?gid='+torch.gid);
	    			}
	    			if(frame == 'ifm2'){
	    				$("#ifm2").attr('src','showReqHistory.html?gid='+torch.gid);
	    			}
	    			if(frame == 'ifm3'){
	    				$("#ifm3").attr('src','docHistory.html?gid='+torch.gid);
	    			}
	    			
	    		}
	    	})
	    });
		});
	 },
	 
	 /**
	  * 如果状态为退回修改，则文书页签不能点击
	  */
	 initTab:function(){
		$.ajax({
			url:'../../../approve/reqhistory/getReqState.do',
			data:{
				gid:torch.gid
			},
			type:'post',
			dataType:'json',
			success:function(data){
				if(data!=null && data=='4'){
					$('#tab_id').tabpanel('disable','2');
				}
			}
		}); 
	 },
	 
	 showAllInfo:function(){
		 $("#ifm1").attr('src','detailReqAllInfo.html?gid='+torch.gid);
		 $("#ifm2").attr('src','showReqHistory.html?gid='+torch.gid);
		 $("#ifm3").attr('src','docHistory.html?gid='+torch.gid);
	 }
			
};
torch._init();
return torch;

});
