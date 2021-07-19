define(['require','jquery','common','util'], function(require, $,common,util){
	var applyConfirm = {
		_init : function(){
			var $_this = this;
			require(['domReady'], function (domReady) {
				domReady(function () {
					// 根据模板处理页面样式
					common.computeHeight();
					//$_this.wrapInfo();
					//$_this.reLocate();
					//获取名称信息
					$_this.getApplyDetailName();
					//获取企业基本信息
					$_this.getApplyDetailEnt();
					//获取股东信息
					$_this.getApplyDetailinvestors();
					//获取主要人员信息
					$_this.getApplyDetailMembers();
					//获取企业联系人信息
					$_this.getApplyDetailContact();
					//财务负责人信息
					$_this.getApplyDetailFinance();
					//获取上传文件
					$_this.getApplyDetailFiles();
					//绑定页面方法
					$_this.bindingClick();
					//处理页面样式
					$_this.initPreviewUi();
					
				});
			});
		},
		//上一页跳转
		backStep:function(){
			history.go(-1);
			//window.location.href="../../../page/apply/person_account/home.html";
		},
		/**
		 * 绑定页面方法
		 */
		bindingClick:function(){
			//提交按钮
			$("#submitButton").on('click',this.applySubmitButton);
			//返回首页按钮
			$("#cancelButton").on('click',this.backStep);
			//退回按钮
			$("#backButton").on('click',this.backToApp);
			$("#showPicDiv").on('click',this.closePic);
		},
		/**
		 * 法人提交确认
		 */
		applySubmitButton:function(){
			var gid = jazz.util.getParameter('gid');
			jazz.confirm("提交后不可修改，请确认您填写的信息完整准确，真实有效。点击【确定】提交。",function(){
				
			var params = {
    		        url : '../../../apply/setup/submit/leRepSubmit.do',
    		        params:{
    		        	gid:gid,
    		        },
    		        async:false,
    		        callback : function(data, param, res) {
    		        	jazz.info("提交成功，您将在两个工作日内收到反馈结果，请留意系统信息与短信提醒。",function(){
							 window.location.href="../../../page/apply/person_account/home.html";
							 
						 });
    		        }
    		    };  	
    		$.DataAdapter.submit(params);
        	},function(){});
		},
		backToApp:function(){
			var gid = jazz.util.getParameter('gid');
			jazz.confirm("确定要将业务退回申请人吗？点击【确定】提交。",function(){
				
	    		var params = {
	    		        url : '../../../apply/setup/submit/backToApp.do',
	    		        params:{
	    		        	gid:gid,
	    		        },
	    		        async:false,
	    		        callback : function(data, param, res) {
	    		        	jazz.info("已成功退回申请人，请联系申请人尽快修改有误信息后重新提交业务，避免影响您的业务办理时间。",function(){
								 window.location.href="../../../page/apply/person_account/home.html";
								 
							 });
	    		        }
	    		    };  	
	    		$.DataAdapter.submit(params);
			},function(){});
		},
		/**
		 * 展示法人授权页面
		 */
		showLeRepAuth:function(){
			util.openWindow("leRepAuth", "法定代表人授权", "../../../page/torch/apply/person_account/le_auth.html?gid="+jazz.util.getParameter('gid') ,800 , 600);
		},
		/**
		 * 根据查询到的所有数据初始化页面样式
		 */
		initPreviewUi:function(){
			
		},
		/**
		 * 预览 名称信息
		 */
		getApplyDetailName : function(){
			var url = "../../../torch/service.do?fid=applySetupPreview&wid=applySetupPreviewName&gid="+jazz.util.getParameter('gid');
			$("div[name='applySetupPreviewName_Form']").formpanel("option", "dataurl",url);
			$("div[name='applySetupPreviewName_Form']").formpanel("reload");
		},
		/**
		 * 预览 企业基本信息
		 */
		getApplyDetailEnt : function(){
			var url = "../../../torch/service.do?fid=applySetupPreview&wid=applySetupPreviewEnt&gid="+jazz.util.getParameter('gid');
			$("div[name='applySetupPreviewEnt_Form']").formpanel("option", "dataurl",url);
			$("div[name='applySetupPreviewEnt_Form']").formpanel("reload");
		},
		/**
		 * 预览  企业股东信息
		 */
		getApplyDetailinvestors:function(){
			var gid = jazz.util.getParameter('gid');
			var url = "../../../torch/service.do?fid=applySetupPreview&wid=applySetupPreviewInv&gid="+gid;
    		var params = {
    		        url : url,
    		        params:{
    		        	gid:gid,
    		        },
    		        async:false,
    		        callback : function(data, param, res) {
    		        	var rows = data.data.rows;
    		        	var htm ="";
    		        	$.each(rows,function(i,v){
    		        		var content="<tr>";
    		        		if('自然人股东'==v.invType){
    		        			content += ("<td nowrap >"+(v.invType?v.invType:"")+":</td><td >"
    		        				+(v.inv?v.inv:"")+"</td><td>"
    		        				+(v.sex?v.sex:"")
    		        				+"</td><td nowrap>"
    		        				+(v.cerType?v.cerType:"")
    		        				+"</td><td nowrap>"
    		        				+(v.cerNo?v.cerNo:"")+"</td>");
    		        		}else if('非自然人股东'==v.invType){
    		        			content += ("<td nowrap >"+(v.invType?v.invType:"")+":</td><td colspan='2'>"
    		        				+(v.inv?v.inv:"")
    		        				+"</td><td nowrap>"
    		        				+(v.cerType?v.cerType:"")
    		        				+"</td><td nowrap>"
    		        				+(v.cerNo?v.cerNo:"")+"</td>");
    		        		}
    		        		content+=("<td nowrap>"+(v.conAm?v.conAm:"0")
    		        			+"万元</td><td>"+(v.times?v.times:"0")
    		        			+"次</td></tr>");
    		        		htm+=(content);
    		        	});
    		        	$('.previes-investors').append(htm);
    		        }
    		};  	
    		$.DataAdapter.submit(params);
		},
		/**
		 * 预览   主要人员信息
		 */
		getApplyDetailMembers:function(){
			var gid = jazz.util.getParameter('gid');
			var url = "../../../torch/service.do?fid=applySetupPreview&wid=applySetupPreviewMbr&gid="+gid;
    		var params = {
    		        url : url,
    		        params:{
    		        	gid:gid,
    		        },
    		        async:false,
    		        callback : function(data, param, res) {
    		        	var rows = data.data.rows;
    		        	var htm ="";
    		        	$.each(rows,function(i,v){
    		        		
    		        		var isLef = (v.leRepSign=='1'?"法定代表人<img src='../../../static/lib/JAZZ-UI/lib/themes/ebaic/images/message.png'/>":"");
    		        		htm+=("<tr><td>"+isLef+"</td><td>"+(v.name?v.name:"")
    		        				+"</td><td>"
    		        			+(v.position?v.position:"")
    		        			+"</td><td>"+v.sex
    		        			+"</td><td>"
    		        			+(v.cerType?v.cerType:"")
    		        			+"</td><td>"
    		        			+(v.cerNo?v.cerNo:"")+"</td></tr>");
    		        		
    		        	});
    		        	$('.previes-members').append(htm);
    		        	
    		        }
    		};  	
    		$.DataAdapter.submit(params);
		},
		/**
		 * 预览   企业联系人信息
		 */
		getApplyDetailContact:function(){
			var url = "../../../torch/service.do?fid=applySetupPreview&wid=applySetupPreviewContact&gid="+jazz.util.getParameter('gid');
			$("div[name='applySetupPreviewContact_Form']").formpanel("option", "dataurl",url);
			$("div[name='applySetupPreviewContact_Form']").formpanel("reload");
		},
		/**
		 * 预览   财务负责人信息
		 */
		getApplyDetailFinance:function(){
			var url = "../../../torch/service.do?fid=applySetupPreview&wid=applySetupPreviewFinance&gid="+jazz.util.getParameter('gid');
			$("div[name='applySetupPreviewFinance_Form']").formpanel("option", "dataurl",url);
			$("div[name='applySetupPreviewFinance_Form']").formpanel("reload");
		},
		/**
		 * 预览   图片
		 */
		getApplyDetailFiles:function(){
			var $_this = this;
			$.ajax({
				url:"../../../torch/service.do?fid=applySetupPreview&wid=applySetupPreviewFiles&gid="+jazz.util.getParameter('gid'),
				type:'post',
				dataType:'json',
				success:function(data){
					var rows = data.data[0].data.rows;
					var categoryId='';
					var obj=$('#uploadFiles');
					$.each(rows,function(index,val){
						if(val.categoryId != categoryId){
							//新的大类
							var tmpH='<div class="content" style="clear:both;"><div class="content-top">'+
							'<img src="../../../static/image/img/icon/ZYRRXX.png">'+
							'<span>'+val.title+'</span></div>'+
						    '<div id="categoryId'+val.categoryId+'" class="previes-prove"></div></div>';
							obj.append(tmpH);
							categoryId=val.categoryId;
						}
						if(val.fileId){
							var tmpH='<div class="categoryPic" ><img id="'+val.fileId+'" width="100px" height="100px" src="../../../upload/showPic.do?fileId='
							+(val.thumbFileId?val.thumbFileId:val.fileId)
							+'" ><br /><span>'+(val.refText?val.refText:'')
							+'</span></div>';
							$('#categoryId'+categoryId).append(tmpH);
							$("#"+val.fileId).on('click',$_this.showPic);
						}
						
					});
					
				},
				error:function(data){
					
				}
			});
		},
		showPic:function(e){
			
			var obj = e.srcElement?e.srcElement:e.target;
			var id=$(obj).attr('id');
			$('#showPic').attr('src','../../../upload/showPic.do?fileId='+id);
			$('#showPicDiv').show();
			
			
		},
		closePic:function(){
			$('#showPicDiv').hide();
		}
		
		
		
	};
	applyConfirm._init();
	return applyConfirm;
});


