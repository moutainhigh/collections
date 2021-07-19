define(['require','jquery', 'approvecommon','util'], function(require, $, approvecommon, util){
	var g ={};
    g = {
    	/**
    	 * 模块初始化。
    	 */
    	_init: function(){
    		require(['domReady'], function (domReady) {
			  domReady(function () {
				 $('#approve-step0').show();
				 var oUserSelect = $("#UserList");
				 if(oUserSelect){
					 var user = oUserSelect.val();
					 if(!user){
						 $("#userMsg").html("请插入UKey后重试。");
						 $("#userRetryBtn").show();
					 }else{
						 $("#userMsg").html("核准期间不得拔下UKey，否则会导致核准失败。");
						 $("#userRetryBtn").hide();
						 g.approveSubmit();// 执行第一步
					 }
				 }
					 
				$('#userRetryBtn').live("click",function(){
					window.location.reload();
				});
				$('#cancelBtn').live("click",function(){
					util.closeWindow("approveWindow");
				});
				$('#backToLisBtn').live("click",function(){
					  //window.location.href="../../../page/approve/mytask/mytask_list.html";
					  window.parent.location.href = '../mytask/mytask_list.html';
					  window.close();
				});
				
				$("#prompt-rule-pass").on('click',g.promptRulePass);
				$("#special-rule-pass").on('click',g.specialRulePass);
				$("#special-rule-apply").on('click',g.specialRuleApply);
				$("#back_mytask").on('click',g.backMytask);
				
			  });
			});
    	},
    	/**
    	 * 自413以下 内容  chaiyoubing   2016-07-14    
    	 *	按步骤执行：
    	 *	
    	 *	1、执行规则准备（搬库）；rulePrepare
    	 *	
    	 *	2、执行规则；runRule
    	 *	
    	 *	3、执行核准逻辑；submit
    	 *	
    	 *	4、生成电子文书(准备签名)；doc
    	 *	
    	 *	5、签名  sign
    	*/
    	approveSubmit:function (){
    		$("#approve-step1").show();
    		var gid = jazz.util.getParameter('gid') || '';
    		if(!gid){
    			alert('gid不能为空。');
    		}
    		$.when($.post("../../../approve/doapprove/rulePrepare.do",{gid:gid}))
    		 .then(g.prepareSuccess,g.prepareError);//核准环节-点击核准  规则准备、搬库
    	},
    	/**
    		1.规则准备、搬库成功后，执行的方法
    	*/
    	prepareSuccess:function (data){
    		$("#approve-step1").find(".step-result").find("#img").attr("src","../../../static/image/img/icon/check.png");
    		$("#approve-step2").show();
    		var gid = jazz.util.getParameter('gid') || '';
    		$.when($.post('../../../approve/doapprove/runRule.do',{gid:gid}))// 执行第2步
    		.then(g.runRuleSuccess,g.runRuleError);
    	},
    	
    	/**
    		规则准备、搬库失败后，执行的方法
    	*/
    	prepareError:function (){
    		$("#approve-step1").find(".step-result").find("#img").attr("src","../../../static/image/img/icon/error.png");
    	},
    	
    	/**
    		2、执行规则成功后，执行的方法
    	*/
    	runRuleSuccess:function (data){
    		$("#approve-step2").find(".step-result").find("#img").attr("src","../../../static/image/img/icon/check.png");
    		$("#approve-step3").show();
    		var gid = jazz.util.getParameter('gid') || '';
    		$.when($.post('../../../approve/doapprove/doc.do',{gid:gid}))// 执行第3步
    		.then(g.docSuccess,g.docError);
    	},
    	/**
    		执行规则失败后，执行的方法
    	*/
    	runRuleError:function (responseObj){
    		$("#approve-step2").find(".step-result").find("#img").attr("src","../../../static/image/img/icon/error.png");
    		$("#approve-rules").show();
    		
    		if(responseObj["responseText"]){
    	    	var err = jazz.stringToJson(responseObj["responseText"]);
    	    	if(err['exceptionMes']){
    	    		var errmsgs = jazz.stringToJson(err['exceptionMes']).allMsg;
    	    		var msg = "";
    	    		var type = "";
    	    		var name = "";
    	    		var ruleStepId = "";
    	    		if(errmsgs.length>0){
    					for(var i=0;i<errmsgs.length;i++){
    						var count = i+1;
    						msg = errmsgs[i].msg;
    						ruleStepId = errmsgs[i].ruleStepId;
    						type = errmsgs[i].type;
    						name = errmsgs[i].name;
    						var html ="<tr class='approve-rule' id='approve-rule'>" +
    										"<td class='approve-td-check'>"+
    											"<input name='ruleStepId' type='hidden' value='"+ruleStepId+ "' />"+"<input name='rule-check' type='checkbox'/>" +
    										"</td>"+
    										"<td class='approve-td-num' align='center'>" +count+"</td>"+
    										"<td class='apporve-td-type' align='center'>" +type+"</td>" +
    										"<td class='approve-td-name' align='left'>" +name+"</td>" +
    										"<td class='approve-td-msg' align='left'>" +msg+"</td>" +
    									"</tr>";
    						$("#approve-table").append(html);
    						
    						if(errmsgs[i].type=='锁定'){
    							 $("#prompt-rule-pass").hide();
    						}
    					}
    				}
    	    	}else{
    	    		jazz.error('与服务器连接断开，请尝试重新登录或与管理员联系!');
    	    	}
    	    }else{
    	    	jazz.error('与服务器连接断开，请尝试重新登录或与管理员联系!');
    	    }
    	},
    	
    	/**
    		4、核准成功
    	*/
    	submitSuccess:function (data){
    		$("#approve-step5").find(".step-result").find("#img").attr("src","../../../static/image/img/icon/check.png");
    		$("#backToLisBtn").show();
    		$("#successMsg").find("#success").text("核准成功");
    		$("#successMsg").find("img").show();
    		$("#successMsg").show();
    		$("#approve-sign").hide();
    	},
    	/**
    		核准失败后，执行的方法
    	*/
    	submitError:function (responseObj){
    		$("#approve-step5").find(".step-result").find("#img").attr("src","../../../static/image/img/icon/error.png");
    		if(responseObj["responseText"]){
    			var err = jazz.stringToJson(responseObj["responseText"]);
    	    	if(err['exceptionMes']){
    	    		$("#approve-step5").find(".step-errorinfo").text(err['exceptionMes']);
    	    	}else{
    	    		jazz.error('与服务器连接断开，请尝试重新登录或与管理员联系!');
    	    	}
    	    }else{
    	    	jazz.error('与服务器连接断开，请尝试重新登录或与管理员联系!');
    	    }
    	},
    	
    	/**
    		3.生成电子档案
    	*/
    	docSuccess:function (hashCode){
    		$("#approve-step3").find(".step-result").find("#img").attr("src","../../../static/image/img/icon/check.png");
    		$("#approve-step4").show();
    		$("#approve-sign").show();
    		$("input[name='hashcode']").val(hashCode);
    		var UserSignedData = g.doClientSign(hashCode);
    		if(UserSignedData){
    			var gid = jazz.util.getParameter('gid') || '';
        		$.when($.post('../../../approve/doapprove/sign.do',{UserSignedData : UserSignedData,gid:gid}))// 执行第4步
        		.then(g.signSuccess,g.signError);
    		}else{
    			$("#approve-step4").find(".step-result").find("#img").attr("src","../../../static/image/img/icon/error.png");
    		}
    	},
    	
    	/**
		生成文书并且返回文书哈希码给客户端失败后，执行的方法
		*/
		docError:function (){
			$("#approve-step3").find(".step-result").find("#img").attr("src","../../../static/image/img/icon/error.png");
			if(responseObj["responseText"]){
				var err = jazz.stringToJson(responseObj["responseText"]);
		    	if(err['exceptionMes']){
		    		$("#approve-step3").find(".step-errorinfo").text(err['exceptionMes']);
		    	}else{
		    		jazz.error('与服务器连接断开，请尝试重新登录或与管理员联系!');
		    	}
		    }else{
		    	jazz.error('与服务器连接断开，请尝试重新登录或与管理员联系!');
		    }
		},
    	/**
    	 * 执行客户端登录。
    	 */
    	doClientSign : function (hashCode){
    		var strCertID = SignForm.UserList.value;
    		if(!strCertID){
    			$("#UserSignedData").val('');
    			alert('请插入证书');
    			return ;
    		}
    		var UserSignedData = SignByP7(strCertID,hashCode);
    		return UserSignedData;
    	},
    	
    	/**
    		5、签名成功后，执行的方法
    	*/
    	signSuccess:function (){
    		$("#approve-step4").find(".step-result").find("#img").attr("src","../../../static/image/img/icon/check.png");
    		$("#approve-step5").show();
    		var gid = jazz.util.getParameter('gid') || '';
    		$.when($.post('../../../approve/doapprove/submit.do',{gid:gid}))//执行第4步
    		.then(g.submitSuccess,g.submitError);
    	},
    	/**
    	 * 签名失败后，执行的方法
    	 */
    	signError:function(responseObj){
    		$("#approve-step4").find(".step-result").find("#img").attr("src","../../../static/image/img/icon/error.png");
    		if(responseObj["responseText"]){
    			var err = jazz.stringToJson(responseObj["responseText"]);
    	    	if(err['exceptionMes']){
    	    		$("#approve-step4").find(".step-errorinfo").text(err['exceptionMes']);
    	    	}else{
    	    		jazz.error('与服务器连接断开，请尝试重新登录或与管理员联系!');
    	    	}
    	    }else{
    	    	jazz.error('与服务器连接断开，请尝试重新登录或与管理员联系!');
    	    }
    	},
    	/**
    	 * 提示通过  prompt-rule-pass
    	 */
    	promptRulePass:function(){
    		g.ruleSuccessAfter();
    	},
    	
    	/**
    	 * 特殊通道  special-rule-pass
    	 */
    	specialRulePass:function(){
    		var ruleStepIds = "";
    		$("input[name='rule-check']").each(function(){
    				if($(this).is(':checked')){
    					 if($(this).parent().siblings(".apporve-td-type").text()=="锁定"){
    						 var ruleStepid = $(this).prev().val()+",";
    						  ruleStepIds+=ruleStepid;
    					 }
    				}
    		});
    		if(ruleStepIds==""){
    			jazz.warn("请至少选择一条需要通过特殊通道的锁定类型的规则！");
    			return;
    		}else{
    			$.ajax({
        			url:'../../../approve/doapprove/specialGalleryPass.do',
        			type:"post",
        			async:false,
        			dataType:"json",
        			data:{
        				"gid":jazz.util.getParameter('gid') || '',
        				"ruleStepIds":ruleStepIds,
        			},
        			success: function(resultData){
        				if(resultData=="noPass") {
        			    	jazz.warn("特殊通道不能通过所有规则！");
        			    }
        				if(resultData=="error"){
        			    	jazz.warn("没有可以使用的特殊通道！");
        			    }
        				if(resultData=="pass"){
        					//特殊通道，通过后，执行下一步的核准操作；
        					g.ruleSuccessAfter();
        			    } 
        			}
        		});
    			
    		}
    	},
    	/**
    	 * 规则通过后,开始执行规则之后的步骤
    	 */
    	ruleSuccessAfter:function (){
    		$("#approve-step2").find(".step-result").find("#img").attr("src","../../../static/image/img/icon/check.png");
    		$("#approve-step3").show();
    		$("#approve-rules").hide();
    		var gid = jazz.util.getParameter('gid') || '';
    		$.when($.post('../../../approve/doapprove/doc.do',{gid:gid}))// 执行第3步
    		.then(g.docSuccess,g.docError);
    	},
    	/**
    	 * 特殊通道申请 special-rule-apply
    	 */
    	specialRuleApply:function(){
    		var ruleStepIds = "";
    		$("input[name='rule-check']").each(function(){
    				if($(this).is(':checked')){
    					 if($(this).parent().siblings(".apporve-td-type").text()=="锁定"){
    						 var ruleStepid = $(this).prev().val()+",";
    						  ruleStepIds+=ruleStepid;
    					 }
    				}
    		});
    		
    		if(ruleStepIds==""){
    			jazz.warn("请至少选择一条需要申请特殊通道的锁定类型的规则！");
    			return;
    		}else{
    			jazz.confirm("确认要发起特殊通道申请？",function(){
    				$.ajax({
    					url:'../../../approve/doapprove/applySpecial.do',
    					type:"post",
    					async:false,
    					dataType:"json",
    					data:{
    						"gid":jazz.util.getParameter('gid') || '',
    						"ruleStepIds":ruleStepIds
    					},
    					success: function(resultData){
    						jazz.info(resultData);
    					}	
    				});
    			});
    		}
    	}

    };
    g._init();
    return g;
});