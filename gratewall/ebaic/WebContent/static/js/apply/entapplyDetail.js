define(['require','jquery','entcommon','util'], function(require, $,entcommon,util){
	var applyDetail = {
		_init : function(){
			var $_this = this;
			require(['domReady'], function (domReady) {
				domReady(function () {
					$("body").loading();
					// 根据模板处理页面样式
					entcommon.computeHeight();
					$_this.wrapInfo();
					$_this.reLocate();
					$_this.loadInformation();
					//获取业务办理和审批信息记录
					$_this.getApplyDetailRecord();
					
					//绑定页面方法
					$_this.bindingClick();
					//处理页面样式
					$_this.initPreviewUi();
					$_this.changeStyle();	
					$("#backButton").on('click',$_this.backStep);
					$("#close").on('click',$_this.close)
					$("#showPicDiv").on('click',$_this.close);
					
				});
			});
		},
		/*业务详情标签*/
		wrapInfo : function(){
    		var gid = jazz.util.getParameter("gid");
    		$(".wrapper").renderTemplate({templateName:"tab_content",insertType:"prepend"},{gid:gid});
    	},
	    reLocate:function(){
	    	var urlstr=location.href;
			$(".tabclass a").each(function(){				
				if((urlstr+"/").indexOf($(this).attr("href"))>-1 && $(this).attr("href")!=''){
					$(this).addClass("blueactive");
		    		$(this).find("span:eq(0)").addClass("tab-apply-blue");
					urlstatus=true;
				}else{
					$(this).removeClass("blueactive");
					$(this).find("span:eq(0)").removeClass("tab-apply-blue");
					$(this).find("span:eq(0)").addClass("tab-apply");
				}
			});
	    },
		changeStyle:function(){
			$("div[vtype='textfield']").css("height",'auto');
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
		
		},
		
		
		/**
		 * 根据查询到的所有数据初始化页面样式
		 */
		initPreviewUi:function(){
			
		},
		
		/**
		 * 加载申请信息
		 */
		loadInformation:function(){
			//获取名称信息
			applyDetail.getApplyDetailName();
			//获取企业基本信息
			applyDetail.getApplyDetailEnt();
			//获取股东信息
			applyDetail.getApplyDetailinvestors();
			//获取主要人员信息
			applyDetail.getApplyDetailMembers();
			//获取企业联系人信息
			applyDetail.getApplyDetailContact();
			//财务负责人信息
			applyDetail.getApplyDetailFinance();
			//获取上传文件
			applyDetail.getApplyDetailFiles();
			
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
    		        	if(rows.length == 0){
    		        		$('.previes-investors').parent().addClass('hide');
    		        	}else{
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
        		        			content += ("<td nowrap >"+(v.invType?v.invType:"")+":</td><td>"
        		        				+(v.inv?v.inv:'')
        		        				+"</td><td>无</td><td nowrap>"
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
    		        	$("body").loading("hide");
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
    		        	if(rows.length == 0){
    		        		$('.previes-members').parent().addClass('hide');
    		        	}else{
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
		 * 预览   业务办理和审批信息记录
		 */
		getApplyDetailRecord:function(){
			var gid = jazz.util.getParameter('gid');
			var url = "../../../torch/service.do?fid=applySetupPreview&wid=applySetupPreviewRecord&gid="+gid;
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
    		        		if(rows.length!=0||rows!=null){
    		        			//修正法定代表人的显示的位置和美化
        		        		htm+=("<tr><td>"+(v.processDate?v.processDate:"")+"<td>"
        		        			+(v.processStep?v.processStep:"")
        		        			+"</td><td>"+(v.userName?v.userName:"")
        		        			+"</td><td>"
        		        			+(v.regOrg?v.regOrg:"")
        		        			+"</td><td>"
        		        			+(v.processNotion?v.processNotion:"")+"</td></tr>");
    		        		}else{
    		        			$(".previes-record").css("display","none");
    		        		}
    		        	});
    		        	$('.previes-record').append(htm);
    		        	
    		        }
    		};  	
    		$.DataAdapter.submit(params);
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
		close:function(){
			$('#showPicDiv').hide();
		}
		/*closePic:function(){
			$('#showPicDiv').hide();
		}*/
		
		
	};
	applyDetail._init();
	return applyDetail;
});


