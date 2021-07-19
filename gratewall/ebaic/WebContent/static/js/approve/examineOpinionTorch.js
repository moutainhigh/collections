define(['require','jquery', 'approvecommon','util','xtxsutit'], function(require, $, approvecommon,util){
	var torch = {};
	torch = {
	edit_primaryValues : function(){return "&gid="+jazz.util.getParameter('gid');},
	edit_fromNames : [ 'examineOpinion_Form', 'entRuleCheck_Form'],
	_init : function(){
		require(['domReady'], function (domReady) {
		  domReady(function () {
			  	torch.examineOpinionQuery();
				torch.initOpinionOperation();
				
				
				//计算元素位置
				/*alert(11);
	    		var left = $(parent.frames['frameExampleApprovecontent']).offset().left;
	    		var top = $(parent.frames['frameExampleApprovecontent']).offset().top;
	    		alert(left   + "  === "  + top);*/
				
				$("div[name='examineOpinion_Form']").css("margin-top","-20px");
				$("div[name='entType']").css("height","35px");
				$("textarea[name='censorNotion']").css("height","63px");
				$("textarea[name='censorNotion']").css("padding-top","5px");
				$("div[name='examineOpinion_save_button']").off('click').on('click',torch.examineOpinionSave);
				$("div[name='examineOpinion_submit_button']").off('click').on('click',torch.examineOpinionSubmit);
				$("div[name='examineOpinion_submit_button_docSign']").off('click').on('click',torch.examineOpiniondocSignSubmit);
				$("#censor-help").off('click').on('click',torch.assigentExamine);
				$("div[name='censorResult']").on('click',function(){torch.setNotionValue('examine');});
				$("div[name='approveResult']").on('click',function(){torch.setNotionValue('approve');});
				$("#showMemo").off('click').on('click',torch.showMemo);
				
				//util.exports('setNotionRuletip',torch.setNotionRuletip);
				
				/**
				 * 2016-07-16 核准步骤隐藏层样式初始化 chaiyoubing  addshowMemo
				 */
//				torch.approveStepsCss();
				//企业类型后面添加辅助审查按钮，进行规则校验
				$("div[name='entType']").append('');
				//界面初始化默认隐藏掉备注区域框,当点击添加备注时显示备注区域框
				
		  });
		});
		
	},
	/**
	 * 核准步骤隐藏层样式初始化 chaiyoubing
	 */
	approveStepsCss:function(){
		$("#prompt-rule-pass").on('click',torch.promptRulePass);
		$("#special-rule-pass").on('click',torch.specialRulePass);
		$("#special-rule-apply").on('click',torch.specialRuleApply);
		$("#back_mytask").on('click',torch.backMytask);
		$("#approve-sign").on('click',torch.doSign);
		$("#back_mytask").hide();
		$("#approve-rules").hide();
		$("#approve-sign").hide();
		$("#approve-step1").hide();
		$("#approve-step2").hide();
		$("#approve-step3").hide();
		$("#approve-step4").hide();
		$("#approve-step5").hide();
	},
	
	/**
	 * 当点击不同的受理结果时 给受理意见赋值
	 */
	setNotionValue:function(type){
		if(type=='examine'){
			//审查
			var censorResult = $("div[name='censorResult']").radiofield("getValue")||'';
			if(censorResult=='10'){
				$("div[name='examineOpinion_submit_button_docSign']").text("提交核准");
				$("div[name='censorNotion']").textareafield("setValue","经审查，材料齐全、符合法定形式，建议准予设立登记。");
			}
			if(censorResult=='12'){
				$("div[name='examineOpinion_submit_button_docSign']").text("退回修改");
				$("div[name='censorNotion']").textareafield("setValue","");
			}
			if(censorResult=='13'){
				$("div[name='examineOpinion_submit_button_docSign']").text("驳回");
				$("div[name='censorNotion']").textareafield("setValue","");
			}
		}
		if(type=='approve'){
			//核准
			var approveResult = $("div[name='approveResult']").radiofield("getValue")||'';
			if(approveResult=='14'){
				$("div[name='examineOpinion_submit_button_docSign']").text("核准");
				$("div[name='approveNotion']").textareafield("setValue","准予设立登记。");
			}
			if(approveResult=='12'){
				$("div[name='examineOpinion_submit_button_docSign']").text("退回修改");
				$("div[name='approveNotion']").textareafield("setValue","");
			}
			if(approveResult=='13'){
				$("div[name='examineOpinion_submit_button_docSign']").text("驳回");
				$("div[name='approveNotion']").textareafield("setValue","");
			}
		}
	},
	/**
	 * 辅助审查
	 */
	assigentExamine : function(){
		var params={		 
				 url:"../../../torch/service.do?fid=34A8081EA54E1FC9E055000000000001"+torch.edit_primaryValues(),
				 components: [ 'examineOpinion_Form', 'entRuleCheck_Form'],
				 callback: function(jsonData,param,res){
					   var copyFinishFlag=false;
						//先搬库(申请到审批)
						$.ajax({
							url:'../../../approve/process/copyData.do',
							type:"post",
							async:false,
							dataType:"json",
							showloading: false,
							data:{
								"gid":jazz.util.getParameter('gid'),
							},
							success: function(resultData){
								if(resultData&&resultData=="success"){
									copyFinishFlag = true;
								}
							}
						});
						if(copyFinishFlag){//审批跑规则
							var result = torch.showRuleExecute();
							if(result&&result==true){
								jazz.info("辅助审查通过。");
							}
						}
				 }
			};
		$.DataAdapter.submit(params);
		
	},
	//显示规则信息页面
    showRuleExecute:function(){
    	var entType = $("div[name='entType']").comboxfield("getValue")||'';
    	if(!entType){
    		jazz.warn("企业类型不能为空。");
    		return false;
    	}
		var rsInfo = window.showModalDialog("interceptRuleList.html?gid="+jazz.util.getParameter('gid'),window,"resizable:yes;dialogWidth:1000px;dialogHeight:600px"); 
		if(rsInfo&&rsInfo!=true&&rsInfo!="undefined"){
			torch.setNotionRuletip(rsInfo);
		}
		return rsInfo;
	},
	initOpinionOperation: function(){
		$.ajax({
			url:'../../../approve/process/canApprove.do',
			type:"post",
			async:false,
			dataType:"json",
			data:{
				"gid":jazz.util.getParameter('gid'),
			},
			success: function(resultData){
				//返回结果为true：核准 ，false:审查
				var divNameArr=null;
				//初始化界面   
				if(resultData==true){
					//隐藏审查部分字段，并且给核准部分字段赋值
					divNameArr =['censorResult','approveName'];
					if(!$("div[name='approveResult']").radiofield("getValue")){
						$("div[name='approveResult']").radiofield("setValue",14);
					}
					if(!$("div[name='approveDate']").datefield("getValue")){
						$("div[name='approveDate']").datefield("setValue",torch.formatDate(new Date().getTime()));
					}
					if(!$("div[name='approveNotion']").textareafield("getValue")){
						if($("div[name='approveResult']").radiofield("getValue")=='14'){
							$("div[name='approveNotion']").textareafield("setValue","准予设立登记。");
						}else{
							$("div[name='approveNotion']").textareafield("setValue","");
						}
					}
					$("div[name='censorDate']").datefield("option","readonly",true);
					$("div[name='censorNotion']").textareafield("option","readonly",true);
					$("div[name='pageType']").hiddenfield("setValue","approve");//设置界面类型为核准界面
					var approveResult = $("div[name='approveResult']").radiofield("getValue");
					if(approveResult=='14'){
						$("div[name='examineOpinion_submit_button_docSign']").text("核准");
					}
					if(approveResult=='12'){
						$("div[name='examineOpinion_submit_button_docSign']").text("退回修改");
					}
					if(approveResult=='13'){
						$("div[name='examineOpinion_submit_button_docSign']").text("驳回");
					}
				}
				if(resultData==false){
					$("div[name='censorNotion']").textareafield("option","height",80);
					//隐藏核准部分字段，并且给审查部分字段赋值
					divNameArr =['approveResult','approveNotion','approveDate','approveName','censorName'];
					if(!$("div[name='censorResult']").radiofield("getValue")){
						$("div[name='censorResult']").radiofield("setValue",10);
					}
					if(!$("div[name='censorDate']").datefield("getValue")){
						$("div[name='censorDate']").datefield("setValue",torch.formatDate(new Date().getTime()));
					}
					if(!$("div[name='censorNotion']").textareafield("getValue")){
						if($("div[name='censorResult']").radiofield("getValue")=='10'){
							$("div[name='censorNotion']").textareafield("setValue","经审查，材料齐全、符合法定形式，建议准予设立登记。");
						}else{
							$("div[name='censorNotion']").textareafield("setValue","");
						}
					}
					$("div[name='pageType']").hiddenfield("setValue","examine");//设置界面类型为审查界面
					var censorResult = $("div[name='censorResult']").radiofield("getValue");
					if(censorResult=='10'){
						$("div[name='examineOpinion_submit_button_docSign']").text("提交核准");
					}
					if(censorResult=='12'){
						$("div[name='examineOpinion_submit_button_docSign']").text("退回修改");
					}
					if(censorResult=='13'){
						$("div[name='examineOpinion_submit_button_docSign']").text("驳回");
					}
				}
				for(var i=0;i<divNameArr.length;i++){
					$("div[name='"+divNameArr[i]+"']").hide();
				} 
			}
		});
	},
	examineOpinionQuery : function(){
		var updateKey= "&wid=34A8081EA54F1FC9E055000000000001,34A8081EA5511FC9E055000000000001";
//		var operationType =true;
		$.ajax({
				url:'../../../torch/service.do?fid=34A8081EA54E1FC9E055000000000001'+updateKey+torch.edit_primaryValues(),
				type:"post",
				async:false,
				dataType:"json",
				success: function(data){
					var jsonData = data.data;
					if($.isArray(jsonData)){
						 for(var i = 0, len = jsonData.length; i<len; i++){
							$("div[name='"+jsonData[i].name+"']").formpanel("setValue",jsonData[i] || {});
						 }
					 }
				}
			});
	},
    /**
     * 单纯的保存按钮，只保存当前环节的处理结果，审查/核准 意见，人，时间
     */
	examineOpinionSave : function(){
		var sumbitNum = 0;
		if(sumbitNum==0){
			if($("div[name='examineOpinion_Form']").formpanel('validate') && $("div[name='entRuleCheck_Form']").formpanel('validate')){
				var params={		 
					 url:"../../../torch/service.do?fid=34A8081EA54E1FC9E055000000000001"+torch.edit_primaryValues(),
					 components: torch.edit_fromNames,
					 callback: function(jsonData,param,res){
						jazz.info("保存成功");
					 }
				};
			    $.DataAdapter.submit(params);
			    sumbitNum=1;
			}
		}
	},
	/**
	 * 提交按钮，生成文书和哈希码
	 */
	examineOpiniondocSignSubmit:function(){
		var censorNotion = $("div[name='censorNotion']").textareafield("getValue");
		var approveNotion = $("div[name='approveNotion']").textareafield("getValue");
		var censorResult = $("div[name='censorResult']").radiofield("getValue");
		var approveResult = $("div[name='approveResult']").radiofield("getValue");
		var gid=jazz.util.getParameter('gid')||'';
		var pageType = $("div[name='pageType']").hiddenfield("getValue")||'';
		var entType = $("div[name='entType']").comboxfield("getValue");
		var entTypeCategory = "";
		
		//判断是通过(pass)，退回修改(back)，还是驳回(reject)
		var curState = "";
		if(pageType=='examine'){
			if(censorResult=='10'){
				curState = "pass";
			}
			if(censorResult=='12'){
				curState = "back";
			}
			if(censorResult=='13'){
				curState = "reject";
			}
			if(!censorNotion){
				jazz.warn("审查意见不能为空!");
				return;
			}
		}
		if(pageType=='approve'){
			if(approveResult=='14'){
				curState = "pass";
			}
			if(approveResult=='12'){
				curState = "back";
			}
			if(approveResult=='13'){
				curState = "reject";
			}
			if(!approveNotion){
				jazz.warn("核准意见不能为空!");
				return;
			}
		}
		
		if(curState == "pass"){
			//提交之前先进行判断申请人与法定代表人身份信息
			var identityState = "";
			var identityTip = "";
			var editFlag = "";
			var caCertId = "";
			var signPicUrl="";
			var submitFlag = true;
			$.ajax({
				url:'../../../approve/process/getSubmitInfo.do',
				type:"post",
				async:false,
				dataType:"json",
				data:{
					"entType":entType,
					"gid":gid
				},
				success: function(resultData){
					entTypeCategory = resultData.entTypeCategory;
					identityState = resultData.identityState;
					identityTip = resultData.identityTip;
					editFlag = resultData.editFlag;
					caCertId = resultData.caCertId;
					signPicUrl=resultData.signPicUrl;
				}	
			});
			if(entTypeCategory==""){
				jazz.warn("当前企业类型不能进行业务，请选择其他业务类型!");
				return;
			}
			if(pageType=='examine'){
				if(censorResult=='10'&&identityState=='2'){
					submitFlag = false;
				}
			}
			if(pageType=='approve'){
				if(approveResult=='14'&&(identityState=='2'||identityState=='3'||identityState=='4'||identityState=='5')){
					submitFlag = false;
				}
				if(approveResult=='14'&&!caCertId){
					jazz.warn("当前用户未绑定CA，不能执行核准操作，请联系分局系统管理员。");
					return;
				}
				if(approveResult=='14'&&!signPicUrl){
					jazz.warn("当前用户未上传签名图片，不能执行核准操作，请联系分局系统管理员。");
					return;
				}
			}
			if(submitFlag==false){
				jazz.warn(identityTip+"，现在不能执行通过操作！");
				return;
			}
			if(editFlag&&editFlag=='yes'){
				jazz.warn("修改过审核内容数据，不能执行通过操作！");
				return;
			}
			jazz.confirm("确认要提交吗?",function(){
				if($("div[name='examineOpinion_Form']").formpanel('validate')){
					var params={		 
							 url:"../../../torch/service.do?fid=34A8081EA54E1FC9E055000000000001"+torch.edit_primaryValues(),
							 components: torch.edit_fromNames,
							 callback: function(jsonData,param,res){
								 /***
									 * 1、确认后，显示隐藏层，开始执行核准步骤；
									 * 2、对核准的步骤执行结果进行美化
									 *		a.正在执行
									 * 		b.如果成功，对勾，并且执行下一步骤；
									 * 		c.如果失败，显示错误信息，停止执行下一步骤；
									 * 		d.规则页面怎么交互，之后再处理，目前先显示规则不通过内容；
									 */
									if(pageType=='examine'){//辅助审查，提交核准
										var copyFinishFlag=false;
										//先搬库(申请到审批)
										$.ajax({
											url:'../../../approve/process/copyData.do',
											type:"post",
											async:false,
											dataType:"json",
											data:{
												"gid":jazz.util.getParameter('gid'),
											},
											success: function(resultData){
												if(resultData&&resultData=="success"){
													copyFinishFlag = true;
												}
											}
										});
										if(copyFinishFlag==true){
											if(torch.showRuleExecute()){
												torch.execSubmit();
											}
										}
									}
									if(pageType=='approve'){//核准
										if(window.parent.openApproveWindow){
											window.parent.openApproveWindow(gid);
										}else{
											jazz.info('非法操作，请联系管理员。');
											return ;
										}
									}
							 }
					};
					$.DataAdapter.submit(params);
				}
			});
		}
		if(curState == "back"){
			jazz.confirm("确认要退回修改吗?",function(){
					var params={		 
							 url:"../../../torch/service.do?fid=34A8081EA54E1FC9E055000000000001"+torch.edit_primaryValues(),
							 components: torch.edit_fromNames,
							 callback: function(jsonData,param,res){
								 torch.execSubmit();
							 }
					};
					$.DataAdapter.submit(params);
			});
		}
		if(curState == "reject"){
			jazz.confirm("确认要驳回吗?",function(){
				var params={		 
						 url:"../../../torch/service.do?fid=34A8081EA54E1FC9E055000000000001"+torch.edit_primaryValues(),
						 components: torch.edit_fromNames,
						 callback: function(jsonData,param,res){
							 torch.execSubmit();
						 }
				};
				$.DataAdapter.submit(params);
			});
		}
	},
	execSubmit :function(){
		var sumbitNum;
		if($("div[name='examineOpinion_Form']").formpanel('validate') && $("div[name='entRuleCheck_Form']").formpanel('validate')){
			sumbitNum = 0;
		}
		if(sumbitNum==0){
			sumbitNum =1;
			var tipInfo = "提交成功。";
			var gid=jazz.util.getParameter('gid')||'';
			var pageType = $("div[name='pageType']").hiddenfield("getValue")||'';
			if(pageType=='approve'){
				var approveResult = $("div[name='approveResult']").radiofield("getValue");
				if(approveResult=='14'){
					tipInfo = "核准操作成功。";
				}
				if(approveResult=='12'){
					tipInfo = "退回修改操作成功。";
				}
				if(approveResult=='13'){
					tipInfo = "驳回操作成功。";
				}
			}
			if(pageType=='examine'){
				var censorResult = $("div[name='censorResult']").radiofield("getValue");
				if(censorResult=='10'){
					tipInfo = "提交核准操作成功。";
				}
				if(censorResult=='12'){
					tipInfo = "退回修改操作成功。";
				}
				if(censorResult=='13'){
					tipInfo = "驳回操作成功。";
				}
			}
			$.ajax({
					url:'../../../approve/process/execSubmit.do',
					type:"post",
					dataType:"json",
					data:{
						"gid":gid,
						"pageType":pageType
					},
					success: function(resultData){
						if(resultData=='success'){
							jazz.info(tipInfo,function(){
								parent.parent.window.location.href="../../../page/approve/mytask/mytask_list.html";
							});
						}
					},
					error : function(responseObj) {
     			    if(responseObj["responseText"]){
     			    	var err = $.parseJSON(responseObj["responseText"]);
     			    	if(err['exceptionMes']){
     			    		jazz.warn('<font color="blue" >错误信息</font> : ' + err['exceptionMes']);				    					    		
     			    	}else{
     			    		jazz.error('与服务器连接断开，请尝试重新登录或与管理员联系!');
     			    	}
     			    }else{
     			    	jazz.error('与服务器连接断开，请尝试重新登录或与管理员联系!');
     			    }
     			    return false;
     			}
			});
		}
	},
	
	formatDate:function(strTime){
		var date = new Date(strTime);
		return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
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
	beSureSubmit:function(){
		$("#approve-hide-cover").show();
		$("#approve-steps").show();
		torch.approveSubmit();
	},
	approveSubmit:function (){
		$("#approve-step1").show();
		var gid = jazz.util.getParameter('gid') || '';
		$.when($.post("../../../approve/doapprove/rulePrepare.do",{gid:gid}))
		 .then(torch.prepareSuccess,torch.prepareError);//核准环节-点击核准  规则准备、搬库
	},
	/**
		1.规则准备、搬库成功后，执行的方法
	*/
	prepareSuccess:function (data){
		$("#approve-step1").find(".step-result").text("成功");
		$("#approve-step2").show();
		var gid = jazz.util.getParameter('gid') || '';
		$.when($.post('../../../approve/doapprove/runRule.do',{gid:gid}))
		.then(torch.runRuleSuccess,torch.runRuleError);
	},
	
	/**
		规则准备、搬库失败后，执行的方法
	*/
	prepareError:function (){
		$("#approve-step1").find(".step-result").text("失败");
	},
	
	/**
		2、执行规则成功后，执行的方法
	*/
	runRuleSuccess:function (data){
		$("#approve-step2").find(".step-result").text("成功");
		$("#approve-step3").show();
		var gid = jazz.util.getParameter('gid') || '';
		$.when($.post('../../../approve/doapprove/submit.do',{gid:gid}))
		.then(torch.submitSuccess,torch.submitError);
	},
	/**
		执行规则失败后，执行的方法
	*/
	runRuleError:function (responseObj){
		$("#approve-step2").find(".step-result").text("失败");
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
		3、核准成功后，执行的方法
	*/
	submitSuccess:function (data){
		$("#approve-step4").show();
		$("#approve-step3").find(".step-result").text("成功");
		var gid = jazz.util.getParameter('gid') || '';
		$.when($.post('../../../approve/doapprove/doc.do',{gid:gid}))
		.then(torch.docSuccess,torch.docError);
	},
	/**
		核准失败后，执行的方法
	*/
	submitError:function (responseObj){
		$("#approve-step3").find(".step-result").text("失败");
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
		4、生成文书并且返回文书哈希码给客户端成功后，执行的方法
	*/
	docSuccess:function (data){
		$("#approve-step4").find(".step-result").text("成功");
		$("#approve-sign").show();
		SetUserCertList("SignForm.UserList");
		AddOnLoadEvent($onLoadFunc);
		$("input[name='hashcode']").val(data);
	},
	/**
		生成文书并且返回文书哈希码给客户端失败后，执行的方法
	*/
	docError:function (){
		$("#approve-step4").find(".step-result").text("失败");
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
		5、签名成功后，执行的方法
	*/
	signSuccess:function (){
		$("#approve-step5").show();
		$("#approve-step5").find(".step-result").text("成功");
		alert("恭喜您，核准成功！");
		$("#back_mytask").show();
		$("#approve-sign").hide();
	},
	/**
	 * 签名失败后，执行的方法
	 */
	signError:function (){
		$("#approve-step5").find(".step-result").text("失败");
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
	 * 签名
	 */
	doSign:function (){
		// 客户端签名
		var hashCode = $("input[name='hashcode']").val();
		var UserSignedData = torch.doClientSign(hashCode);
		if(UserSignedData){
			// 服务器端签名
			var gid = jazz.util.getParameter('gid') || '';
			$.when($.post('../../../approve/doapprove/sign.do',{gid:gid,UserSignedData:UserSignedData}))
			.then(torch.signSuccess,torch.signError);
		}
	},
	/**
	 * 执行客户端登录。
	 */
	doClientSign:function (hashCode){
		var strCertID = SignForm.UserList.value;
		if(!strCertID){
			$("#UserSignedData").val('');
			alert('请插入证书。');
			return ;
		}
		var UserSignedData = SignByP7(strCertID,hashCode);
		return UserSignedData;
	},
	
	/**
	 * 提示通过  prompt-rule-pass
	 */
	promptRulePass:function(){
		torch.ruleSuccessAfter();
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
		
		$.ajax({
			url:'../../../approve/process/executeTstdRules.do',
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
					torch.ruleSuccessAfter();
			    } 
			}
		});
	},
	/**
	 * 规则通过后,开始执行规则之后的步骤
	 */
	ruleSuccessAfter:function (){
		$("#approve-step2").find(".step-result").text("成功");
		$("#approve-step3").show();
		$("#approve-rules").hide();
		var gid = jazz.util.getParameter('gid') || '';
		var obj = $.post("../../../approve/doapprove/submit.do",{gid:gid});
		$.when(obj)
		.then(torch.submitSuccess,torch.submitError);//核准环节-点击核准 提交操作。
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
					url:'../../../approve/process/createTstd.do',
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
	},
	/**
	 * 返回我的任务列表
	 */
	backMytask:function(){
		window.location.href="../../../page/approve/mytask/mytask_list.html";
	},
	
	/**
	 * 把规则提示信息加入到核准意见中
	 */
	setNotionRuletip:function(ruleTip){
		var type = $("div[name='pageType']").hiddenfield("getValue");
		if(type=='examine'){
			var censorNotion = $("div[name='censorNotion']").textareafield("getValue");
			censorNotion = censorNotion +ruleTip;
			$("div[name='censorNotion']").textareafield("setValue",censorNotion);
		}
		if(type == 'approve'){
			var approveNotion = $("div[name='approveNotion']").textareafield("getValue");
			approveNotion = approveNotion +ruleTip;
			$("div[name='approveNotion']").textareafield("setValue",approveNotion);
		}
	}
};
torch._init();
return torch;

});
