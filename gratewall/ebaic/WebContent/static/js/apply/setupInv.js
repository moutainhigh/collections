define(['jquery','common','util','apply/setupApproveMsg'], function($, common,util,approveMsg){
    var torch ={};
	torch = {
    	_init: function(){   		
    		require(['domReady'],function(domReady){
    			domReady(function(){
    				$("div[name='invPersonGridPanel']").css("height","50px");
					$("div[name='invCpGridPanel']").css("height","50px");
    				torch.wrapInfo();
    				torch.reLocate();
    				torch.getInvCpList();
    				torch.getInvPersonList();
    			
					common.computeHeight();					
					/**
					 * 绑定方法
					 */
					torch.bindingClick();
					$("img[name='back']").on('click',torch.hideAdvice);//隐藏退回修改意见
					$(".modifyBack").on('click',torch.showAdvice);//出现退回修改意见
					//torch.TitlePage();
    			});
    		});
    	},
    	//点击出现退回修改意见
    	showAdvice:function(){
    		$(".advice").css("display","block");
    		$(".modifyBack").css("display","none");
    	},
    	//点击隐藏退回修改意见
    	hideAdvice:function(){
    		$(".advice").css("display",'none');
    		$(".modifyBack").css("display","block");
    	},
    	/**
    	 * 绑定事件
    	 */
    	bindingClick:function(){
    		$("#applySetupBasic_forward_button").on('click',torch.nextStep);
			$("#applySetupBasic_back_button").on('click',torch.backStep);
			$("#applySetupBasic_save_button").on('click',torch.saveStep); 
			
			$(".btnEntDemoAdd").live("click",function(){
				var infoTab="";
				$(".info-tab").each(function(){
		    		 infoTab1 = $(this).children('span').eq(1).text();
		    		 if(infoTab1 == "信息未填写"){
		    			 infoTab="0";
			    	 }else{
			    		 infoTab="1";	
			    		}
		    	});
				var gid = jazz.util.getParameter('gid')||'';
				var investorType = $(this).children("input").eq(0).val();
				var investorId = $(this).children("input").eq(1).val();
				if(investorType=='20'||investorType=='35'||investorType=='36'||investorType=='91'){
					
					torch._openInfo(investorId,gid);
				}else{
					torch._openInfoT(investorId,gid,infoTab);
				}
			});	
    	},
    	/**
    	 * 得到非自然人列表
    	 */
    	getInvCpList : function(){
			var gid = jazz.util.getParameter('gid')||'';
    		var url = '../../../torch/service.do?fid=applySetupInv&wid=applySetupInvCpList&m=data&gid='+gid;
    		$("#invCpGridPanel").gridpanel("option",'datarender',torch.invCpRender);
	    	$("#invCpGridPanel").gridpanel("option",'dataurl',url);
	    	$("#invCpGridPanel").gridpanel("reload");
    	},
    	invCpRender:function(item,data){
    		var gid = jazz.util.getParameter('gid')||'';
    		$("#cpList").siblings('.cardBox').empty();
    		if(data.data.length==0){
    			$(".titleCp").css("display","none");
    		}
    		$("#cpList").renderTemplate({templateName:"card",insertType:"after"},{"rows":data.data,"gid":gid}); 
    	},
    	/**
    	 * 得到自然人列表
    	 */
    	getInvPersonList : function(){
    		var gid = jazz.util.getParameter('gid')||'';
    		var url = '../../../torch/service.do?fid=applySetupInv&wid=applySetupInvPersonList&m=data&gid='+gid;
    		$("#invPersonGridPanel").gridpanel("option",'datarender',torch.invPersonRender);
	    	$("#invPersonGridPanel").gridpanel("option",'dataurl',url);
	    	$("#invPersonGridPanel").gridpanel("reload");
    	},
    	invPersonRender:function(item,data){
    		var gid = jazz.util.getParameter('gid')||'';
    		$("#personList").siblings('.cardBox').empty();
    		if(data.data.length==0){
    			$(".titlePerson").css("display","none");
    		}
    		$("#personList").renderTemplate({templateName:"card",insertType:"after"},{"rows":data.data,"gid":gid}); 
    	},
	   	_openInfoT:function(obj,gid,infoTab){
	    	util.openWindow('cpW','非自然人股东信息',"inv_cp_edit.html?investorId="+obj+"&gid="+gid+"&infoTab="+infoTab,930,570);

	   	},
	    _openInfo:function(obj,gid){
	    	util.openWindow('personW','自然人股东信息',"inv_person_edit.html?investorId="+obj+"&gid="+gid,915,530);
	    },
	  
	    backStep:function(){
	    	window.location.href="../../../page/apply/setup/basic_info.html?gid="+jazz.util.getParameter('gid');
	    },
	    
	    /**
	     * 股东下一步
	     */
	    nextStep:function(){
	    	//检验股东信息是否全部填写
	    	var flag = false;
	    	$(".info-tab").each(function(){
	    		var infoTab = $(this).children('span').eq(1).text();
	    		if(infoTab == "信息未填写"){
	    			flag = true;
	    		}
	    	});
	    	if(flag == true){
	    		jazz.warn("请先填写完未保存的股东信息！");
	    	}else{
	    		$.ajax({
					url:'../../../apply/setup/inv/runRule.do',
					type:"post",
					data: {
						gid:jazz.util.getParameter('gid')
					},
					dataType:"json",
					success:function(data){
						if(data.result){
							window.location.href="../../../page/apply/setup/member.html?gid="+jazz.util.getParameter('gid');
						}else{
							jazz.info(data.msg);
						}
					}
				});
	    	}
	    },
	    /**
	     * 股东保存
	     */
	    saveStep:function(){
	    	$.ajax({
				url:'../../../apply/setup/inv/runRule.do',
				type:"post",
				data: {
					gid:jazz.util.getParameter('gid')
				},
				dataType:"json",
				success:function(data){
					if(data.result){
						jazz.info("保存成功。");
					}else{
						jazz.info(data.msg);
					}
				}
			});
	    },
	    /**
         *@desc 获取页面数量
         *@return 页面数
         */
	    getPageCount: function() {
            return Math.ceil(parseInt(this.options.totalrecords) / parseInt(this.options.pagerows)) || 1;
        },
	    //没有数据的时候隐藏显示title和分页按钮
	   TitlePage:function(){
		   //自然人股东个数
		   var num=$(".main").children("input").eq(1).val();
		   num++;
		   //非自然人股东的个数
		   var titleCp=$(".main").children("input").eq(1).val();
		   titleCp++;
		  /* alert(titleCp);*/
		   
		   if(document.getElementsByClassName("card").length>=0){
			   if($(".card").children("input").val()==20||$(".card").children("input").val()==36||
					   $(".card").children("input").val()==35||$(".card").children("input").val()==91){
				   $(".titlePerson").css("display","block");
			   	}
			   if($(".main").children("input").eq(1).val()!="" && titleCp>=1){
			   	 $(".titleCp").css("display","block");
			   	}
			   
			   if($(".main").children("input").eq(0).val()>7){
				   $(".pagePerson").css("display","block");
			   }
			   if($(".main").children("input").eq(1).val()>7){
				   $(".pageCp").css("display","block");
				   
			   }
		   } 
	    },

    	wrapInfo : function(){
    		var gid = jazz.util.getParameter("gid");
    		$(".main").renderTemplate({templateName:"content",insertType:"wrap",wrapSelector:".content"},{gid:gid});
    	},
	    reLocate:function(){
	    	var urlstr=location.href;
			$(".banner a").each(function(){
				if((urlstr+"/").indexOf($(this).attr("href"))>-1 && $(this).attr("href")!=''){
					$(this).addClass("blueactive");
		    		$(this).find("span:eq(0)").addClass("icon-inv-blue");
					urlstatus=true;
				}else{
					$(this).removeClass("blueactive");
					$(this).find("span:eq(0)").removeClass("icon-info-blue");
					$(this).find("span:eq(0)").addClass("icon-info");
				}
			});
	    },
    };
    
    torch._init();
    return torch;
});

