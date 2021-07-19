define(['require','jquery','common','util'], function(require, $,common,util){
	var confirm = {
		_init : function(){
			var $_this = this;
			require(['domReady'], function (domReady) {
				domReady(function () {
					$("body").loading();
					var state;
					// 根据模板处理页面样式
					common.computeHeight();
					$_this.wrapInfo();
					$_this.reLocate();
					$_this.getLinkName();
					$_this.getRepresetativeList();		
					
					
					/*预览审批意见*/
					//$_this._showApproveAdvice();
					$("#check").on("click",confirm.getChecked);
					
				});
			});
		},
		/*获取为提交和未通过的自然人股东*/
		getChecked:function(){
			if(this.checked==true){
				$("#name").css("color","#8cc0e7");
				$("#cp").children(".tabbox").children(".tabcardgreen").css("display","none");
				$("#ncp").children(".tabbox").children(".tabcardgreen").css("display","none");
				$("#representative").children(".tabbox").children(".tabcardgreen").css("display","none");
			}else{
				$("#name").css("color","#666");
				$("#cp").children(".tabbox").children(".tabcardgreen").css("display","inline-block");
				$("#ncp").children(".tabbox").children(".tabcardgreen").css("display","inline-block");
				$("#representative").children(".tabbox").children(".tabcardgreen").css("display","inline-block");
			}
		},
		/*获取申请人信息*/
		getLinkName:function(){
			var gid=jazz.util.getParameter('gid')||'';
			var url="../../../torch/service.do?fid=applySetupPreview&wid=applySetupPreviewReqInfo&ui=data&gid="+gid;
			$("#applySetupPreviewReqInfo_Form").gridpanel("option","datarender",confirm.applySetupPreviewReqInfo_Form);
			$("#applySetupPreviewReqInfo_Form").gridpanel("option","dataurl",url);
			$("#applySetupPreviewReqInfo_Form").gridpanel("reload");
		},
		
		applySetupPreviewReqInfo_Form:function(item,data){
			var gid=jazz.util.getParameter('gid')||'';
			var data=data.data;
			var linkman,submitDate;
			if(data[0].state==1){
			
				$("#apply").css("display","none");
				$(".tabtitle").css("display","none");
				$(".dotted-line").css("display","none");
				$("#cp").css("display","none");
				$("#ncp").css("display","none");
				$("#representative").css("display","none");
				$(".tip").css("display","block");
				$("body").loading('hide');
			}
			if(data[0].linkman==null){
				 linkman='';
			}else{
				linkman=data[0].linkman;
			}
			if(data[0].submitDate==null){
				 submitDate='';
			}else{
				submitDate=data[0].submitDate;
			}
			var html='<div class="apply">'+
			'<p>申请人：'+linkman+'</p>'+
			'<p>提交日期：'+submitDate+'</p>'+
			'</div>';
			$("#apply").append(html);
		},
    	/*得到账户列表详情内容*/
     	getRepresetativeList:function(){
    		var gid=jazz.util.getParameter('gid')||'';
    		var url="../../../torch/service.do?fid=applySetupPreview&wid=applySetupPreviewConfirm&ui=data&gid="+gid;
			$("#representativeGridPanel").gridpanel("option","dataurl",url);
			
			$("#invtabGridPanel").gridpanel("option","datarender",confirm.representativeRender);
			$("#invtabGridPanel").gridpanel("option","dataurl",url);
			
			$("#invPersonGridPanel").gridpanel("option","datarender",confirm.representativeRender);
			$("#invPersonGridPanel").gridpanel("option","dataurl",url);
			
			$("#representativeGridPanel").gridpanel("option","datarender",confirm.representativeRender);
			$("#representativeGridPanel").gridpanel("reload");
    	},
    	representativeRender:function(item,data){
    	
    		var gid=jazz.util.getParameter('gid')||'';
    		var data=data.data;
    		var len=data.length;
    		var person=[];
    		var nonperson=[];
    		var representative=[];
    		
    		
    		for(var i=0;i<len;i++){
    			if(data[i].c=="自然人股东"){
    				person.push(data[i]);
    			}else if(data[i].c=="非自然人股东"){
    				nonperson.push(data[i]);
    			}else{
    				representative.push(data[i]);//法定代表人
    			}
    		}
    		if(person.length==0||person==null){
    			$(".person").css("display","none");
    		}
    		if(nonperson.length==0||nonperson==null){
    			$(".nonperson").css("display","none");
    		}
    		
    		if(representative.length==0||representative==null||representative==undefined){
    			var html='<div class="tabcarduncertain">'+
			     				'<span class="uncertain">未设定</span>'+
			     			'</div>';
    			$("#representative").append(html);
    		}else{//否则拼接企业法定代表人信息
    			for(var j = 0;j<representative.length;j++){
    				if(representative[j].state=="退回申请人"){
    					var html="";
    					html+="<div class='tabCompany'><span  class='compName'>"+representative[j].name+"</span><span  class='state'>退回申请人</span><span class='showReason' name='showReason' >查看原因</span></div>";
    					$("#representative").append(html);
    					$("span[name='showReason']").on("click",confirm.showReason);//绑定事件
    				}else{
    					var html="";
    					html+="<div class='tabCompany'><span  class='compName'>"+representative[j].name+"</span><span  class='state'>"+representative[j].state+"</span>";
    					$("#representative").append(html);
    				}
    				
    			}
    		}
    		
    		
    		
    		$("#cp").renderTemplate({templateName:"tab_card",insertType:"prepend"},{"rows":person,"gid":gid});
    		$("#ncp").renderTemplate({templateName:"tab_card",insertType:"prepend"},{"rows":nonperson,"gid":gid});
    		//$("#representative").renderTemplate({templateName:"tab_card",insertType:"prepend"},{"rows":representative,"gid":gid});
    		$("body").loading('hide');
    		
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
		    		$(this).find("span:eq(0)").addClass("tab-confirm-blue");
					urlstatus=true;
				}else{
					$(this).removeClass("blueactive");
					$(this).find("span:eq(0)").removeClass("tab-confirm");
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
			
		},
		
		showReason:function(){
			confirm._showApproveAdvice();
		},
		
		/*显示审批意见*/
		_showApproveAdvice:function(){
			var html = "";
			html="<div class='hint-boxtop'><span class='approveCofirmTitle'>提交业务确认未通过</span>"+
					"<div name='details' colspan='2' rowspan='1' vtype='textareafield' label=''  width='520' height='180' defaultvalue='申请人将业务提交给拟设企业的法定代表人做业务确认,法定代表人确认业务申请内容正确无误后,可直接提交业务申请。如果审核不通过需要修改,业务申请会被退给申请人修改,完成修改后还需要提交给法定代表人进行业务确认。' readonly='true'  rule='chinese'></div></p>"+
					"<div class='full-line confirmLine'></div>"+
					"<div class='okBtns'>"+
					"<span id='enter-font' class='commonBt' name='ok' style='cursor: pointer'>关闭</span>"+
					"</div>"+
					"</div>";
			
			
			var win = jazz.widget({
				vtype : 'window',
				name : 'approveAdvice',
				title : '审批意见',
				titlealign : 'left',
				titledisplay : true,
				width : 550,
				height :345,
				visible : true,
				modal:true,
				closable:false,
				content : html,
			});
			// 打开窗口

			win.window('open');
			
			 $("span[name='ok'").click(function(){
				var clswindow = $("div[name='approveAdvice']");
				if(clswindow!=null){
					clswindow.window('close');
				}
			});
			
		}
		
	
		
	};
	confirm._init();
	return confirm;
});


