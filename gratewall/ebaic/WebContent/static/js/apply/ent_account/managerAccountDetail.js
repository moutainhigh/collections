define(['require','jquery','entCommon','util'], function(require, $,entCommon,util){
	var applyDetail = {
		_init : function(){
			var $_this = this;
			require(['domReady'], function (domReady) {
				domReady(function () {
					//获取企业基本信息
					$_this.getApplyDetailEnt();
					//绑定页面方法
					$_this.bindingClick();
					//处理页面样式
					$_this.initPreviewUi();
					//$("#applyUpSetupButton").on('click',securityPreview.backStep);	
					$("#backButton").on('click',$_this.backStep);
				});
			});
		},
		//上一页跳转
		backStep:function(){
			window.location.href="../../../page/apply/ent_account/queryEntAccountManagerList.html";
		},
		/**
		 * 绑定页面方法
		 */
		bindingClick:function(){
			/**
			 * 绑定提交方法 
			 */
			$("#applySubmitButton").on('click',this.applySubmitButton);
		},
		/**
		 * 根据查询到的所有数据初始化页面样式
		 */
		initPreviewUi:function(){
			
		},
		/**
		 * 预览 企业基本信息
		 */
		getApplyDetailEnt : function(){
			var url = "../../../torch/service.do?fid=managerAccDetail&wid=managerAccDetail01&managerId="+jazz.util.getParameter('managerId');
			$("div[name='managerAccountDetail_Form']").formpanel("option", "dataurl",url);
			$("div[name='managerAccountDetail_Form']").formpanel("reload");
		},
		/**
		 * 以下几个方法均是采用模板渲染页面样式
		 */
		wrapInfo:function(){
			var gid = jazz.util.getParameter("gid");
			$(".main").renderTemplate({templateName:"content",insertType:"wrap",wrapSelector:".content"},
    				{gid:gid});
		},
		
	};
	applyDetail._init();
	return applyDetail;
});


