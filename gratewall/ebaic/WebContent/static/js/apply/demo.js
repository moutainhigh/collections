define(['require', 'jquery', 'common', 'ajaxfileupload'], function(require, $, common){
    var demo = {
    	_init: function(){
    		require(['domReady'], function (domReady) {
    			  domReady(function () {
    				  $('#uploadButton').on('click',function(){
    					  demo.upload();
    				  })
    				  
			    	
    			  });
    			});
	    	
	    	
    	},
    	upload: function(){
    		$.ajaxFileUpload
            (
                {
                    url: '/upload/upload.do', //用于文件上传的服务器端请求地址up参数标记此次是上传操作还是裁剪操作
                    secureuri: false, //一般设置为false，是否安全上传
                    fileElementId: 'uploadfile', //文件上传控件的id属性 
                    dataType: 'json', //返回值类型 一般设置为json 期望服务器传回的数据类型
                    success: function(data, status){
                    	alert(data);
                    	},
                    error: function (data, status, e)//服务器响应失败处理函数
                    {
                        alert(e);
                    }
                }
            );
    	},
    	ii: "abcaaaaaaaaaaaaaa",
    	
    	loadDom : function(data){
   		 $('#scope').empty();
   		 for(var i in data){
   			$('#scope').append('<div>'+i+':'+data[i]+'</div>');
   		 }
   		 
   	 }
    	
    	
    };
    demo._init();
    return demo;
});