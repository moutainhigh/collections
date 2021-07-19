define(['require','jquery', 'common','util'], function(require, $, common,util){

    var terminate = {
    	/**
    	 * 模块初始化。
    	 */
    	_init: function(){
    		var $_this = this;
    		require(['domReady'], function (domReady) {
			  domReady(function () {
				    var user = common.getCurrentUser();
				    $("#user").textfield("setValue",user.name);
					var date=new Date();
					var year=date.getFullYear();
					var month=date.getMonth()+1;
					var day=date.getDate();
					$("#date").datefield("setValue",year+"-"+month+"-"+day);
			    	//绑定页面方法
			    	$_this.bindingClik();
			  });
			});
    	},
    	bindingClik:function(){
    		$("#selectReason").on("comboxfieldchange",terminate.onReasonChange);
    		$('#save_button').on('click',terminate.saveReason);
    		$('#back_button').on('click',terminate.goBack);
    	},
    	onReasonChange:function (enent,ui){
    		var oVal=ui.newText;
			if(oVal == "其他"){
					$("#reason").show();
			}else{
					$("#reason").hide();
					$("#reason").textareafield("setValue",'');
			}
    	
    	},
    	saveReason:function(){
    		var gid = jazz.util.getParameter('gid');
    		var reason = $("#selectReason").comboxfield("getValue");
    		if('其他'==reason){
    			 reason = $("#reason").textareafield("getValue");
    		}
    		if(!reason){
    			jazz.error("业务终止原因不能为空。");
    			return ;
    		}
    		jazz.confirm("是否确认终止该业务？",function(){
    			$.ajax({
        			url:'../../../req/stopReq.do',
        			type:'post',
        			data:{'gid':gid,'reason':reason},
        			success:function(data){
        				jazz.info("业务已终止",function(){
        					util.closeWindow('stopReq',true);
        				});
        				
        			},
        			error : function(responseObj) {
        			    if(responseObj["responseText"]){
        			    	var err = $.parseJSON(responseObj["responseText"]);
        			    	if(err['exceptionMes']){
        			    		jazz.info('<font color="blue" >错误信息</font> : ' + err['exceptionMes']);				    					    		
        			    	}else{
        			    		jazz.error('与服务器连接断开，请尝试重新登录或与管理员联系!');
        			    	}
        			    }else{
        			    	jazz.error('与服务器连接断开，请尝试重新登录或与管理员联系!');
        			    }
        			    return false;
        			}
        		});
    		},function(){
    			
    		});
    	},
    	goBack:function(){
    		util.closeWindow('stopReq');
    	}
    	
    };
    terminate._init();
    return terminate;
});