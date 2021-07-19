define(['require','jquery','common','util'], function(require, $,common,util){
	var confirm = {
		_init : function(){
			var $_this = this;
			require(['domReady'], function (domReady) {
				domReady(function () {
					// 根据模板处理页面样式
					common.computeHeight();
					$_this.wrapInfo();
					$_this.reLocate();
					$_this.getApprovalList();
				});
			});
		},
		/*获取审批意见*/
		getApprovalList:function(){
			var gid=jazz.util.getParameter('gid')||'';
			var url="../../../torch/service.do?fid=applySetupPreview&wid=applySetupPreviewApprove&ui=data&gid="+gid;
			$("#applySetupPreviewApprove_Grid").gridpanel("option","datarender",confirm.invApprovalRender);
			$("#applySetupPreviewApprove_Grid").gridpanel("option","dataurl",url);
			$("#applySetupPreviewApprove_Grid").gridpanel("reload");
		},
		invApprovalRender: function(item,data){
    		var gid=jazz.util.getParameter('gid')||'';
    		$("#list").renderTemplate({templateName:"tab_list",insertType:"after"},{"rows":data.data,"gid":gid}); 
    	},
    	
    	/*业务详情切换标签*/
		wrapInfo : function(){
    		var gid = jazz.util.getParameter("gid");
    		$(".wrapper").renderTemplate({templateName:"tab_content",insertType:"prepend"},{gid:gid});
    	},
    	
	    reLocate:function(){
	    	var urlstr=location.href;
			$(".tabclass a").each(function(){
				if((urlstr+"/").indexOf($(this).attr("href"))>-1 && $(this).attr("href")!=''){
					$(this).addClass("blueactive");
		    		$(this).find("span:eq(0)").addClass("tab-advice-blue");
					urlstatus=true;
				}else{
					$(this).removeClass("blueactive");
					$(this).find("span:eq(0)").removeClass("tab-id-blue");
				}
			});
	    },
		
		/**
		 * 绑定页面方法
		 */
		bindingClick:function(){
		
		},
		
		
		/**
		 * 根据查询到的所有数据初始化页面样式
		 */
		initPreviewUi:function(){
			
		}
		
	
		
	};
	confirm._init();
	return confirm;
});


