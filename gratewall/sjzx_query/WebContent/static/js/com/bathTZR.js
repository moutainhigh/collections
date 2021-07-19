define([ 'jquery', 'jazz', 'util','ajaxfileupload','print','form' ], function($, jazz, util,ajaxfileupload,PrintArea,form) {
	var torch = {};
	torch = {
		_init : function() {
			$_this = this;
			require([ 'domReady' ], function(domReady) {
				domReady(function() {
					
					$(".uploadForm").css("display","block");
					$("#batchTZRListGrid").gridpanel("reload");
					$("#back_button").off('click').on('click', torch.goBack);
					$("#upload_button").off('click').on('click', torch.uploadFile);
					$("#exportFile").off('click').on('click', torch.exportFile);
				});
			});
		},
		
		_show:function(){
			alert(1);
		},
		goBack : function(e) {
			javascript:history.go(-1);
		},
		uploadFile:function(){
			var flag = $("div[name='select'").checkboxfield('getValue');
			if(flag==""){
				jazz.info("请选择法定代表人或者证件号");
			}else if(flag=="1"){
				flag = 1;
			}else if(flag=="2"){
				flag = 2;
			}else{
				flag = 3;
			}
			
			$.ajaxFileUpload({
                url: "../../../batchtzr/bathTZRUpload.do?flag="+flag, //用于文件上传的服务器端请求地址
                secureuri: false, //是否需要安全协议，一般设置为false
                fileElementId: 'file', //文件上传域的ID
                dataType: 'json', //返回值类型 一般设置为json
                success: function (data, status){ //服务器成功响应处理函数
                	if(status=="success"){
                		$(".btns").css("display","block");
                		$("#batchTZRListGrid").gridpanel("option",'dataurl',"../../../batchtzr/TZRList.do");
        	    		$("#batchTZRListGrid").gridpanel("reload");
                	}
                },
                error: function (data, status, e)//服务器响应失败处理函数
                {
                   alert(data.responseText);
                }
            });
	},
	exportFile:function(){
		 window.open("../../../batch/fdddown.do");
	}
	
	};

	torch._init();
	return torch;
});


/*<script type="text/javascript">
function checkForms() {
	var flag = $("input[type='checkbox'][class='checkbox']:checked").length;
	$("#myForm").action = "../../../batch/bathFDDBRUpload.do";
}
</script>*/