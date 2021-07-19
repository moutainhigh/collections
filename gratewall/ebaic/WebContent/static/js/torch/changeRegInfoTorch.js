define(['require', 'jquery', 'common'], function(require, $, common){
var torch = {
	
	_init : function(){

		 require(['domReady'], function (domReady) {
		    domReady(function () {
			 
		    	torch.getUserInfo();
				$("#changeRegInfo_save_button").on('click',torch.changeRegInfoSave);
				$("#returnSbtn").on('click',torch.returnOption);
			 });
			});
	},
		
	edit_primaryValues : function(){
//	    return "&userId=" + jazz.util.getParameter('userId')
	    //return "&userId=" + $("#userId").hiddenfield('getValue');
	},
	edit_fromNames : [ 'changeRegInfoSave_Form'],



	changeRegInfoQuery : function(){
//		var updateKey= "&wid=changeRegInfoSave";
//		$.ajax({
//				url:'../../../../torch/service.do?fid=changeRegInfo'+updateKey+torch.edit_primaryValues(),
//				type:"post",
//				async:false,
//				dataType:"json",
//				success: function(data){
//					var jsonData = data.data;
//					if($.isArray(jsonData)){
//						 for(var i = 0, len = jsonData.length; i<len; i++){
//							 $("div[name='"+jsonData[i].name+"']").formpanel("setValue",jsonData[i] || {});
//						 }
//					 }
//				}
//			});
		
		$.ajax({
			url:'../../../../security/auth/info/getUserRegInfo.do',
			type:"post",
			async:false,
			dataType:"json",
			success: function(data){
				if(data && data.data && data.data[0]){
					$("div[name='changeRegInfoSave_Form']").formpanel('setValue',data.data[0]);
				}
			}
		});
			
	},


	changeRegInfoSave : function(){
				var params={		 
						 url:"../../../torch/service.do?fid=changeRegInfo",
						 components: torch.edit_fromNames,
						 callback: function(jsonData,param,res){
							 jazz.info("保存成功");		
						 }
					};
					$.DataAdapter.submit(params);
	},
	
	getUserInfo : function(){
		
		var params ={
				url:'../../../security/auth/info/getUserInfo.do',
				params:{},
				async:true,
				callback:function(data,param,res){
					torch.initInfo(data);
				}
		};
		$.DataAdapter.submit(params);
		
	},
	
	initInfo : function(data){
		if(data && data.data){
			//var userId = data.data.userId;
			var mobile = data.data.mobile;
		}else{
			jazz.error("获取信息异常！");
			return;
		}
		
		$("#mobile").hiddenfield('setValue',mobile);
		//$("#userId").hiddenfield('setValue',userId);
		
		//看是否通过手机验证，通过手机验证为1
		var flag = 1;//jazz.util.getParameter("isPassed");
		if(flag == "1"){
			$("#info").css('display','block');
		}else{
			window.location.href = "changeRegInfoValidate.html";//返回手机校验页面
		}
		
		//执行初始化查询
		torch.changeRegInfoQuery();
		
	},
	
	returnOption : function(){
		window.location.href = "index.html";
	}
	 
};
torch._init();
return torch;

});
