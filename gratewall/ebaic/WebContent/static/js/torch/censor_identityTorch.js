define(['jquery', 'jazz','js/renderTemplate'], function($, jazz,renderTemplate){
var torch = {
	
	query_fromNames : [ 'queryForm_Form', 'queryForm_Form'],


	identitySure : function(){
					$("div[name='queryForm_Form']").formpanel("option","dataurl","../../../../torch/service.do?fid=censor_identity&wid=censor_identity_form01&gid={gid}&")
					$("div[name='queryForm_Form']").formpanel("reload");
					$("div[name='queryForm_Form']").formpanel("option","dataurl","../../../../torch/service.do?fid=censor_identity&wid=censor_identity_form02&gid={gid}")
					$("div[name='queryForm_Form']").formpanel("reload");
			},
	identityReset : function(){
				for( x in torch.query_fromNames){
					$("div[name='"+torch.query_fromNames[x]+"']").formpanel("reset");
				} 
			},
	 _init : function(){
//		$("body").renderTemplate({templateName:'footer',insertType:'append'});
//		$("body").renderTemplate({templateName:'header',insertType:'prepend'}); 
		
		$("div[name='identity_query_button']").off('click').click(torch.identitySure);
		$("div[name='identity_reset_button']").off('click').click(torch.identityReset);
			
		this.hidePic();
		$("div[name='linkmanUpfile']").on('click',this.showUpfile);
		$("div[name='linkmanCerPic']").on('click',this.showLinkmanIdentityPic);
		$("div[name='leReqCerPic']").on('click',this.showLeReqIdentityPic);
	 },
	 
	 hidePic:function(){
		 $("#checkPic").hide();
	 },
	 
	 /**
	  * 调用公安部接口显示证照图片信息
	  */
	 showLinkmanIdentityPic:function(){
	    var linkmanName = $("div[name='linkmanName']").textfield('getValue');
	    var linkmanCerNo = $("div[name='linkmanCerNo']").textfield('getValue');
	    $.ajax({
	    	url:'../../../approve/censor/showIdentityPic.do',
	    	data:{
	    		name:linkmanName,
	    		cerNo:linkmanCerNo
	    	},
	    	type:'post',
	    	dataType:'json',
	    	success:function(data){
	    		if(data!=null){
	    			var picUrl = data.data[0].data.picUrl;
	    			if(picUrl!=null){
	    				$("#linkmanPic").attr("src",picUrl);
	    			}else{
	    				jazz.info("获取证照失败！");
	    			}
	    		}
	    	}
	    });
	 },
	 
	 
	 showLeReqIdentityPic:function(){
		    var lereqName = $("div[name='leReqName']").textfield('getValue');
		    var lereqCerNo = $("div[name='leReqCerNo']").textfield('getValue');
		    $.ajax({
		    	url:'../../../approve/censor/showIdentityPic.do',
		    	data:{
		    		name:lereqName,
		    		cerNo:lereqCerNo
		    	},
		    	type:'post',
		    	dataType:'json',
		    	success:function(data){
		    		if(data!=null){
		    			var picUrl = data.data[0].data.picUrl;
		    			if(picUrl!=null){
		    				$("#leReqPic").attr("src",picUrl);
		    			}else{
		    				jazz.info("获取证照失败！");
		    			}
		    		}
		    	}
		    });
		 },
	 
	 /**
	  * 查看本人上传附件
	  */
	 showUpfile:function(){
		 var identity_link_id = $("div[name='linkmanId']").hiddenfield('getValue');
		 $.ajax({
			 url:'../../../approve/censor/identity.do',
			 data:{
				 identityId:identity_link_id
			 },
			 type:"post",
			 dataType:"json",
			 success:function(data){
				 var arr = data.data[0];
				 if(data!=null && data.data[0]!=null){
					 $("#checkPic").show();
					 var arr = data.data[0].data;
					 var picurl = [];
					 for(var i=0;i<arr.length;i++){
						 picurl[i] = arr[i];
					 }
					/* $("#img0").textfield('setValue',picurl[0]);
					 $("#img1").textfield('setValue',picurl[1]);*/
					 $("#img0").attr('src',picurl[0]);
					 $("#img1").attr('src',picurl[1]);
					 
				 }else{
					 jazz.info("获取附件信息失败！");
				 }
				
			 }
			 
		 });
	 }
};
torch._init();
return torch;

});
