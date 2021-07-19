define(['require', 'jquery', 'approvecommon','approve/approve','util' ], function(require, $, approvecommon,approve,util) {
	var getParameter = function(name){
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); 
		var r = window.location.search.substr(1).match(reg); 
		if (r!=null) { 
		   return unescape(r[2]); 
		} 
		return null; 
	};
	var torch = {
	
		shareholder_datarender : function(event, obj) {
		},
		noShareholder_datarender : function(event, obj) {
		},
		mainPerson_datarender : function(event, obj) {
		},
		gid : getParameter('gid'),
		query_fromNames : [ 'entLinkInfo_Form', 'processAIEntInfo_Form', 'processAIEntBasicInfo_Form', 'financeInfo_Form', 'entcontactInfo_Form', 'shareholder_Form', 'noShareholder_Form', 'mainPerson_Form' ],

		processAllInfoSure : function() {
			$("div[name='entLinkInfo_Form']").formpanel("option", "dataurl", "../../../torch/service.do?fid=processAIFuncId&wid=entLinkInfo&gid=" + torch.gid);
			$("div[name='entLinkInfo_Form']").formpanel("reload");
			$("div[name='processAIEntInfo_Form']").formpanel("option", "dataurl", "../../../torch/service.do?fid=processAIFuncId&wid=processAIEntInfo&gid=" + torch.gid);
			$("div[name='processAIEntInfo_Form']").formpanel("reload");
			$("div[name='processAIEntBasicInfo_Form']").formpanel("option", "dataurl", "../../../torch/service.do?fid=processAIFuncId&wid=processAIEntBasicInfo&gid=" + torch.gid);
			$("div[name='processAIEntBasicInfo_Form']").formpanel("reload");
			$("div[name='financeInfo_Form']").formpanel("option", "dataurl", "../../../torch/service.do?fid=processAIFuncId&wid=financeInfo&gid=" + torch.gid);
			$("div[name='financeInfo_Form']").formpanel("reload");
			$("div[name='entcontactInfo_Form']").formpanel("option", "dataurl", "../../../torch/service.do?fid=processAIFuncId&wid=entcontactInfo&gid=" + torch.gid);
			$("div[name='entcontactInfo_Form']").formpanel("reload");
			$("div[name='shareholder_Grid']").gridpanel("option", "dataurl", "../../../torch/service.do?fid=processAIFuncId&wid=shareholder&gid=" + torch.gid);
			$("div[name='shareholder_Grid']").gridpanel("query", [ "shareholder_Form" ]);
			$("div[name='shareholder_Grid']").gridpanel("option", "datarender",function(obj,resultData){
				var dataArrs = resultData.data;
				var dataSize = dataArrs.length;
				if($.isArray(dataArrs)){
					for(var i=0;i<dataArrs.length;i++){
						if(dataArrs[i].subConAm){
							var zcontent = '<span>'+dataArrs[i].subConAm+'&nbsp;&nbsp;<a class="subConCl" style="cursor: pointer">详情</a><input type="hidden" value="'+dataArrs[i].investorId+'"/></span>';
							dataArrs[i].subConAm = zcontent;
						}
					}
					
				}
				if(dataSize<10){
					$("div[id='shareholder_Grid_paginator']").hide();
				}
			});
			$("div[name='noShareholder_Grid']").gridpanel("option", "dataurl", "../../../torch/service.do?fid=processAIFuncId&wid=noShareholder&gid=" + torch.gid);
			$("div[name='noShareholder_Grid']").gridpanel("query", [ "noShareholder_Form" ]);
			$("div[name='noShareholder_Grid']").gridpanel("option", "lineno",false );
			$("div[name='noShareholder_Grid']").gridpanel("option", "datarender",function(obj,resultData){
				var dataArrs = resultData.data;
				var dataSize = dataArrs.length;
				if($.isArray(dataArrs)){
					for(var i=0;i<dataArrs.length;i++){
						if(dataArrs[i].subConAm){
							var zcontent = '<span>'+dataArrs[i].subConAm+'&nbsp;&nbsp;<a class="subConCl" style="cursor: pointer">详情</a><input type="hidden" value="'+dataArrs[i].investorId+'"/></span>';
							dataArrs[i].subConAm = zcontent;
						}
					}
					
				}
				if(dataSize<10){
					$("div[id='noShareholder_Grid_paginator']").hide();//少于指定的数据条数时，不显示分页条
				}
			});
			
			$("div[name='mainPerson_Grid']").gridpanel("option", "dataurl", "../../../torch/service.do?fid=processAIFuncId&wid=mainPerson&gid=" + torch.gid);
			$("div[name='mainPerson_Grid']").gridpanel("query", [ "mainPerson_Form" ]);
			$("div[name='mainPerson_Grid']").gridpanel("option", "datarender",function(obj,resultData){
				var dataArrs = resultData.data;
				var dataSize = dataArrs.length;
				if(dataSize<10){
					$("div[id='mainPerson_Grid_paginator']").hide();
				}
				if(dataSize>0){
					for(var i=0;i<dataArrs.length;i++){
						if(dataArrs[i].natDate){
							dataArrs[i].natDate = dataArrs[i].natDate.substring(0,10);
							var houseAddCity ="";
							if(dataArrs[i].houseAddProv){
								houseAddCity+=dataArrs[i].houseAddProv;
							}
							if(dataArrs[i].houseAddCity){
								houseAddCity+=dataArrs[i].houseAddCity;
							}
							if(dataArrs[i].houseAddOther){
								houseAddCity+=dataArrs[i].houseAddOther;
							}
							dataArrs[i].houseAddCity =houseAddCity;
						}
					}
				}
			});
			
		},
		processAllInfoReset : function() {
			for (x in torch.query_fromNames) {
				$("div[name='" + torch.query_fromNames[x] + "']").formpanel("reset");
			}
		},
		 showorhide:function(){
			 if($("#censor-notice").is(":hidden")){
				 $("#censor").removeClass("censor-new");
			 }else if($("#censor-notice").is(":visible")){
				 $("#censor").addClass("censor-new");
			 }
		 },
		 
		 _openInfo:function(obj){
			 window.parent.openbConAmDetailWindow(obj);
		 },
		 
		 /*
		 //绑定编辑按钮开始
		 renderFieldButton:function(){
			 //设置对应可以将表单位可输入的许可范围
			 var fieldNameArr = ['processAIEntBasicInfo_Form','financeInfo_Form','entcontactInfo_Form'];
			 for(var i=0;i<fieldNameArr.length;i++){
				 //初始化隐藏保存和取消按钮，显示编辑按钮
				 $("button[name='btnSave_"+fieldNameArr[i]+"']").hide();
				 $("button[name='btnCancel_"+fieldNameArr[i]+"']").hide();
				 //绑定编辑按钮事件
				 $("button[name='btnEdit_"+fieldNameArr[i]+"']").on("click",function(){
					 var fieldName = this.name.substring(8);
					 torch.fieldEdit(fieldName);
				 });
				 //绑定保存按钮事件
				 $("button[name='btnSave_"+fieldNameArr[i]+"']").on("click",function(){
					 var fieldName = this.name.substring(8);
					 torch.fieldSave(fieldName);
				 });
				 //绑定取消按钮事件
				 $("button[name='btnCancel_"+fieldNameArr[i]+"']").on("click",function(){
					 //var fieldName = torch.getFieldName(this.name);
					 var fieldName = this.name.substring(10);
					 torch.fieldCancel(fieldName);
				 });
			 }
		 },
		 fieldEdit : function(fieldName){
			// var fieldName = torch.getFieldName(this.name);
			 //点击编辑按钮时，显示保存和取消按钮，隐藏编辑按钮
			
			 $("button[name='btnSave_"+fieldName+"']").show();
			 $("button[name='btnCancel_"+fieldName+"']").show();
			 $("button[name='btnEdit_"+fieldName+"']").hide();
			 
			 var fieldName  = $("div[name='"+fieldName+"']");
			 alert(fieldName);
			 //定位到当前的可编辑的的表单
			 //var vtype = $("div[name='"+fieldName+"']").attr("vtype");
			 $("div[name='"+fieldName+"']").textfield("option","editable",true);//将当前的文本框设置为可编辑		 
			 return;
			 
			 //当点击某一编辑按钮时，隐藏该编辑按钮，使其变成可编辑字段，并显示保存按钮
			 if(vtype){
				 if(vtype=='textfield'){
					 $("div[name='"+fieldName+"']").textfield("option","editable",true);
				 }
				 if(vtype=='datefield'){
					 $("div[name='"+fieldName+"']").datefield("option","editable",true);
				 }
			 }
			 if(fieldName=='locRemark'){
				 //住所(经营场所)的按钮控制着domStreet，domVillage，domNo，domBuilding，domOther的只读与编辑
				 var domNameArr = ['domStreet','domVillage','domNo','domBuilding','domOther'];
				 for(var k=0;k<domNameArr.length;k++){
					 $("div[name='"+domNameArr[k]+"']").textfield("option","editable",true);
				 }
			 }
			 if(fieldName=='opScope'){
				 $("div[name='ptBusScope']").textareafield("option","editable",true);
				 $("div[name='opCustomScope']").textareafield("option","editable",true);
			 }
		 },
		 //将用户输入的保存到对应的数据中
		 fieldSave : function(fieldName){
			 var fieldValue = "";
			 var fieldObject = [];
			 if(fieldName=='locRemark'){
				 var opLoc="北京市";
				 var domNameArr = ['domStreet','domVillage','domNo','domBuilding','domOther'];
				 for(var i=0;i<domNameArr.length;i++){
					 var domName = domNameArr[i];
					 var domValue = "";
					 var vtype = $("div[name='"+domName+"']").attr("vtype");
					 if(vtype){
						 if(vtype=='textfield'){
							 domValue = $("div[name='"+domName+"']").textfield("getValue")||'';
						 }
						 if(vtype=='textareafield'){
							 domValue = $("div[name='"+domName+"']").textareafield("getValue")||'';
						 }
					 }
					 if(domName=='locRemark'){
						 opLoc+=$("div[name='"+domName+"']").comboxfield("getText")||''
					 }else{
						 opLoc+=domValue;
					 }
					 fieldObject.push({"fieldName":domName,"fieldValue":domValue});
				 }
				 fieldObject.push({"fieldName":"opLoc","fieldValue":opLoc});
				 fieldObject.push({"fieldName":"domDistrict","fieldValue":'110108'});
			 }else{
				 //获取vtype类型
				 var vtype = $("div[name='"+fieldName+"']").attr("vtype");
				 if(vtype){
					 if(vtype=='textfield'){
						 fieldValue = $("div[name='"+fieldName+"']").textfield("getValue")||'';
					 }
					 if(vtype=='textareafield'){
						 fieldValue = $("div[name='"+fieldName+"']").textareafield("getValue")||'';
					 }
					 if(vtype=='comboxfield'){
						 fieldValue = $("div[name='"+fieldName+"']").comboxfield("getValue")||'';
					 }
				 }
				 fieldObject.push({"fieldName":fieldName,"fieldValue":fieldValue});
			 }
			 if(torch.validateField(fieldObject)){
				jazz.confirm("确认保存吗?", function(){
					 var params = {
		    					url:'../../../approve/process/saveEntField.do',
		    					params : {
		    						gid:jazz.util.getParameter('gid'),
		    						fieldObject:fieldObject
		    					},
		    					callback : function(jsonData,param,res) {
		    						if(res.getAttr('result')=='success'){
		    							 //当点击某一保存按钮时，隐藏该保存按钮，使其变成只读字段，并显示编辑按钮
		    							 var vtype = $("div[name='"+fieldName+"']").attr("vtype");
		    							 if(vtype){
		    								 if(vtype=='textfield'){
		    									 $("div[name='"+fieldName+"']").textfield("option","editable",false);
		    								 }
		    								 if(vtype=='textareafield'){
		    									 $("div[name='"+fieldName+"']").textareafield("option","editable",false);
		    								 }
		    								 if(vtype=='comboxfield'){
		    									 $("div[name='"+fieldName+"']").comboxfield("option","diabled",true);
		    								 }
		    							 }
		    							 if(fieldName=='locRemark'){
		    								 //住所(经营场所)的按钮控制着domStreet，domVillage，domNo，domBuilding，domOther的只读与编辑
		    								 var domNameArr = ['domStreet','domVillage','domNo','domBuilding','domOther'];
		    								 for(var i=0;i<domNameArr.length;i++){
		    									 $("div[name='"+domNameArr[i]+"']").textfield("option","editable",false);
		    								 }
		    							 }
		    							 if(fieldName=='opScope'){
		    								 $("div[name='ptBusScope']").textareafield("option","editable",false);
		    								 $("div[name='opCustomScope']").textareafield("option","editable",false);
		    							 }
		    							 //保存成功后显示编辑按钮，隐藏保存和取消按钮
		    							 $("button[name='btnSave_"+fieldName+"']").hide();
		    							 $("button[name='btnEdit_"+fieldName+"']").show();
		    							 $("button[name='btnCancel_"+fieldName+"']").hide();
		    						}else{
		    							jazz.info("保存失败!");
		    						}
		    					}
					 }
				 	$.DataAdapter.submit(params);
				 },function(){
					  torch.fieldCancel(fieldName);
				 });
			 }
		 },
		 //用户输入的信息校验
		 validateField:function(fieldArray){
			 if(fieldArray.length==0){
				 jazz.warn("请选择要保存的字段!");
				 return false;
			 }
			 for(var i=0;i<fieldArray.length;i++){
				 var fieldName = fieldArray[i].fieldName;
				 var vtype = $("div[name='"+fieldName+"']").attr("vtype");
				 if(vtype){
					 if(vtype=='textfield'){
						 if(!$("div[name='"+fieldName+"']").textfield("verify")){
							 return false;
						 }else{
							 continue;
						 }
					 }
					 if(vtype=='textareafield'){
						 if(!$("div[name='"+fieldName+"']").textareafield("verify")){
							 return false;
						 }else{
							 continue;
						 }
					 }
					 if(vtype=='comboxfield'){
						 if(!$("div[name='"+fieldName+"']").comboxfield("verify")){
							 return false;
						 }else{
							continue;
						 }
					 }
				 }else{
					 return false;
				 }
			 }
			 return true;
		 },
		 fieldCancel : function(tempFieldName){
			 var fieldName = tempFieldName;
			 //隐藏该保存按钮并显示编辑按钮，使文本变成只读状态
			 var vtype = $("div[name='"+fieldName+"']").attr("vtype");
			 if(vtype){
				 if(vtype=='textfield'){
					 $("div[name='"+fieldName+"']").textfield("option","editable",false);
				 }
				 if(vtype=='textareafield'){
					 $("div[name='"+fieldName+"']").textareafield("option","editable",false);
				 }
				 if(vtype=='comboxfield'){
					 $("div[name='"+fieldName+"']").comboxfield("option","disabled",true);
				 }
			 }
			 if(fieldName=='locRemark'){
				 //住所(经营场所)的按钮控制着domStreet，domVillage，domNo，domBuilding，domOther的只读与编辑
				 var domNameArr = ['domStreet','domVillage','domNo','domBuilding','domOther'];
				 for(var i=0;i<domNameArr.length;i++){
					 $("div[name='"+domNameArr[i]+"']").textfield("option","editable",false);
				 }
			 }
			 if(fieldName=='opScope'){
				 $("div[name='ptBusScope']").textareafield("option","editable",false);
				 $("div[name='opCustomScope']").textareafield("option","editable",false);
			 }
			 //隐藏保存和取消按钮，显示编辑按钮
			 $("button[name='btnSave_"+fieldName+"']").hide();
			 $("button[name='btnCancel_"+fieldName+"']").hide();
			 $("button[name='btnEdit_"+fieldName+"']").show();
			 torch.exceptionApproveQuery();
		 },
		 //再从数据库里面查询对应的企业信息
		 exceptionApproveQuery : function(){
				var updateKey= "&wid=34370D1AD90533CEE055000000000001,349386E3DA5F7262E055000000000001";
				$.ajax({
						url:'../../../../torch/service.do?fid=34370D1AD90333CEE055000000000001'+updateKey+torch.edit_primaryValues(),
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
							var entName = $("div[name='entName']").hiddenfield('getValue');
							$("#commonEntName").textfield('setValue',entName);
						}
					});
			},
			//绑定按钮编辑结束
			*/
			
		 
		 /*弹出层显示客户信息预览图片内容*/
		 _appendShowPicDialog:function(){			 
			 var obj = e.srcElement?e.srcElement:e.target;
				var id=$(obj).attr('id');
				$('#showPic').attr('src','../../../upload/showPic.do?fileId='+id);
		 },
			/**
			 * 预览图片
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
							}
							
						});
						$(".previes-prove").find('img').on('click',$_this.showPic);
					},
					error:function(data){
						
					}
				});
			},
			showPic:function(e){
				
				var content = '<div style="text-align:center;width:100%;overflow-x:auto;">'
					        +     '<img src="" style="height:480px;margin:8px 0" id="showPic" title="" />'
				            + '</div>';
				var win = window.parent.jazz.widget({
	    			vtype : 'window',
	    			name : 'showPicDiv',
	    			title : '图片预览',
	    			titlealign : 'left',
	    			titledisplay : true,
	    			modal : true,
	    			height : '540',
	    			width : '800',
	    			closable : true,
	    			content:content
	    		});
	    		// 打开窗口
	    		win.window('open');
	    		var obj = e.srcElement?e.srcElement:e.target;
				var id=$(obj).attr('id');
				$('#showPic',window.parent.document).attr('src','../../../upload/showPic.do?fileId='+id);
			},
			changeStyle:function(){
				$("div[vtype='textfield']").css("height",'35px');
				/*$("label").css("font-weight","bold");*/
			},
		_init : function() {
			require(['domReady'], function (domReady) {
				  domReady(function () {
					    
					  	// 打开核准对话框，在审查意见frame中调用
			    		window.openApproveWindow = function(gid){
			    			var url = "approve_do_approve.html?gid="+jazz.util.getParameter('gid') ;
			    			var win = window.parent.jazz.widget({
			        			vtype : 'window',
			        			name : 'approveWindow',
			        			title : '提交核准',
			        			titlealign : 'left',
			        			titledisplay : true,
			        			modal : true,
			        			frameurl : url,
			        			height : '540',
			        			width : '600',
			        			closable : 'true'
			        		});
			        		// 打开窗口
			        		win.window('open');
			    			//util.openWindow("approveWindow", "提交核准", url , "600" , "500",false);
			    		};
					  	
					  	/*让审核元素永远跟着窗口运动*/
					  	 $(parent.window).scroll(function(){
					  		 	$("#censor-notice").css({top:$(parent.window).scrollTop() + 10})
					  		});
					  
					    $("div[name='_Form']").gridpanel("option", "datarender", torch.shareholder_datarender);
						$("div[name='_Form']").gridpanel("reload");
						$("div[name='_Form']").gridpanel("option", "datarender", torch.noShareholder_datarender);
						$("div[name='_Form']").gridpanel("reload");
						$("div[name='_Form']").gridpanel("option", "datarender", torch.mainPerson_datarender);
						$("div[name='_Form']").gridpanel("reload");

						$("div[name='processAllInfo_query_button']").off('click').click(torch.processAllInfoSure);
						$("div[name='processAllInfo_reset_button']").off('click').click(torch.processAllInfoReset);
						torch.processAllInfoSure();
						torch.showorhide();
						torch.changeStyle();
						torch.getApplySetupPriviewFiles();  //预览图片
						$("#censor").click(function(){
			    			 	 var notice = $("#censor-notice");
				    			 if(notice.width() == '700'){
				    				 notice.animate({width:'0px'},'slow');
				    			 }else{
				    				 notice.animate({width:'700px'},'slow');
				    			 }
							});
						$('.subConCl').live('click',function(){
							var investorId = $(this).parent().children('input').val();
							torch._openInfo(investorId);
						});
						
						 });
				  		//计算当前iframe的高度，让其自父窗口自适应高度，低于指定的高度不是显示滚动条
						var height = $('body').height()+240;
	    	    		var fram = $('#frametabcontent',window.parent.document);
	    	    		if(fram){
	    	    			fram.css('height',height);
	    	    		}
	    	    		
				  });
		}
	};
	torch._init();
	return torch;

});
