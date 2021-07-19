define(['require','jquery','common','util','jazz'], function(require, $,common,util,jazz){
	var securityPreview = {
		_init : function(){
			var $_this = this;
			require(['domReady'], function (domReady) {
				domReady(function () {
					// 根据模板处理页面样式
					common.computeHeight();
					$_this.wrapInfo();
					$_this.reLocate();
					//获取名称信息
					$_this.getApplySetupPreviewName();
					//获取企业基本信息
					$_this.getApplySetupPreviewEnt();
					//获取股东信息
					$_this.getApplySetupPreviewinvestors();
					//获取主要人员信息
					$_this.getApplySetupPreviewMembers();
					//获取企业联系人信息
					$_this.getApplySetupPriviewContact();
					//财务负责人信息
					$_this.getApplySetupPriviewFinance();
					//获取上传文件
					$_this.getApplySetupPriviewFiles();
					//绑定页面方法
					$_this.bindingClick();
					//处理页面样式
					$_this.initPreviewUi();
					$_this.changeStyle();
					//$("#applyUpSetupButton").on('click',securityPreview.backStep);	
					$("#applyUpSetupButton").on('click',$_this.backStep);
					$("#close").on('click',$_this.close)
					$("#showPicDiv").on('click',$_this.close);
				});
			});
		},
		changeStyle:function(){
			$("div[vtype='textfield']").css("height",'auto');
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
		 * 公司设立提交
		 */
		applySubmitButton:function(){
			var gid = jazz.util.getParameter('gid');
			var params = {
    		        url : '../../../apply/setup/submit/beforeSubmit.do',
    		        params:{
    		        	gid:gid
    		        },
    		        async:false,
    		        callback : function(data, param, res) {
    		        	var status = res.getAttr('status');
    		        	if(status=='success'){
    		        		jazz.confirm("提交后不可修改，请确认您填写的信息完整准确，真实有效。点击【确定】提交。",function(){
    		    				
	    		        		var params = {
	    		        		        url : '../../../apply/setup/submit/cpSubmit.do',
	    		        		        params:{
	    		        		        	gid:gid
	    		        		        },
	    		        		        async:false,
	    		        		        callback : function(data, param, res) {
	    		        		        	var msg = res.getAttr('msg');
	    		        		        	jazz.info(msg,function(){
	    		   							 window.location.href="../../../page/apply/person_account/home.html";
	    		   							 //util.closeWindow('tipPage');
	    		   						});
	    		        		        }
	    		        		};  	
	    						$.DataAdapter.submit(params);
    		        		},function(){},'提交');
    		        	}
						
    		        }
    		    };  	
				$.DataAdapter.submit(params);
			
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
		getApplySetupPreviewName : function(){
			var url = "../../../torch/service.do?fid=applySetupPreview&wid=applySetupPreviewName&gid="+jazz.util.getParameter('gid');
			$("div[name='applySetupPreviewName_Form']").formpanel("option", "dataurl",url);
			$("div[name='applySetupPreviewName_Form']").formpanel("reload");
		},
		/**
		 * 预览 企业基本信息
		 */
		getApplySetupPreviewEnt : function(){
			var url = "../../../torch/service.do?fid=applySetupPreview&wid=applySetupPreviewEnt&gid="+jazz.util.getParameter('gid');
			$("div[name='applySetupPreviewEnt_Form']").formpanel("option", "dataurl",url);
			$("div[name='applySetupPreviewEnt_Form']").formpanel("reload");
		},
		/**
		 * 预览  企业股东信息
		 */
		getApplySetupPreviewinvestors:function(){
			var gid = jazz.util.getParameter('gid');
			var url = "../../../torch/service.do?fid=applySetupPreview&wid=applySetupPreviewInv&gid="+gid;
    		var params = {
    		        url : url,
    		        params:{
    		        	gid:gid
    		        },
    		        async:false,
    		        callback : function(data, param, res) {
    		        	var rows = data.data.rows;
    		        	var htm ="";
    		        	$.each(rows,function(i,v){
    		        		var content="<tr>";
    		        		if('自然人股东'==v.invType){
    		        			content += ("<td nowrap >"+(v.invType?v.invType:"")+"</td><td >"
    		        				+(v.inv?v.inv:"")+"</td><td>"
    		        				+(v.sex?v.sex:"")
    		        				+"</td><td nowrap>"
    		        				+(v.cerType?v.cerType:"")
    		        				+"</td><td nowrap>"
    		        				+(v.cerNo?v.cerNo:"")+"</td>");
    		        		}else if('非自然人股东'==v.invType){
    		        			content += ("<td nowrap >"+(v.invType?v.invType:"")+"</td><td>"
    		        				+(v.inv?v.inv:'')
    		        				+"</div></td><td>无</td><td nowrap>"
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
		getApplySetupPreviewMembers:function(){
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
    		        		
    		        		//var isLef = (v.leRepSign=='1'?"法定代表人<img src='../../../static/lib/JAZZ-UI/lib/themes/ebaic/images/message.png'/>":"");
    		        		//htm+=("<tr><td>"+isLef+"</td><td>"+(v.name?v.name:"")
    		        		
    		        		//修正法定代表人的显示的位置和美化
    		        		var isLef = (v.leRepSign=='1'?"【法定代表人】<img src='../../../static/lib/JAZZ-UI/lib/themes/ebaic/images/message.png'/>":"");
    		        		htm+=("<tr><td>"+(v.name?v.name:"")	+isLef+"<td>"
    		        			+(v.position?v.position:"")
    		        			+"</td><td>"+(v.sex?v.sex:"")
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
		getApplySetupPriviewContact:function(){
			var url = "../../../torch/service.do?fid=applySetupPreview&wid=applySetupPreviewContact&gid="+jazz.util.getParameter('gid');
			$("div[name='applySetupPreviewContact_Form']").formpanel("option", "dataurl",url);
			$("div[name='applySetupPreviewContact_Form']").formpanel("reload");
		},
		/**
		 * 预览   财务负责人信息
		 */
		getApplySetupPriviewFinance:function(){
			var url = "../../../torch/service.do?fid=applySetupPreview&wid=applySetupPreviewFinance&gid="+jazz.util.getParameter('gid');
			$("div[name='applySetupPreviewFinance_Form']").formpanel("option", "dataurl",url);
			$("div[name='applySetupPreviewFinance_Form']").formpanel("reload");
		},
		/**
		 * 预览   图片
		 */
		getApplySetupPriviewFiles:function(){
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
							+'" ><br /><span title="'+val.refText+'">'+(val.refText?val.refText:'')
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
		close:function(){
			$('#showPicDiv').hide();
		},
		/*closePic:function(){
			$('#showPicDiv').hide();
		},*/
		/**
		 * 以下几个方法均是采用模板渲染页面样式
		 */
		wrapInfo:function(){
			var gid = jazz.util.getParameter("gid");
			$(".main").renderTemplate({templateName:"content",insertType:"wrap",wrapSelector:".content"},
    				{gid:gid});
		},
		reLocate:function(){
			var urlstr=location.href;
			$(".banner a").each(function(){
				if((urlstr+"/").indexOf($(this).attr("href"))>-1 && $(this).attr("href")!=''){
					$(this).addClass("blueactive");
		    		$(this).find("span:eq(0)").addClass("icon-preview-blue");
				}else{
					$(this).removeClass("blueactive");
					$(this).find("span:eq(0)").removeClass("icon-info-blue");
					$(this).find("span:eq(0)").addClass("icon-info");
				}
			});
		},
		
		backStep:function(){
	    	window.location.href="../../../page/apply/setup/upload.html?gid="+jazz.util.getParameter('gid');
	    }
    	
	};
	securityPreview._init();
	return securityPreview;
});


