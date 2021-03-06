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
							var zcontent = '<span>'+dataArrs[i].subConAm+'&nbsp;&nbsp;<a class="subConCl" style="cursor: pointer">??????</a><input type="hidden" value="'+dataArrs[i].investorId+'"/></span>';
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
							var zcontent = '<span>'+dataArrs[i].subConAm+'&nbsp;&nbsp;<a class="subConCl" style="cursor: pointer">??????</a><input type="hidden" value="'+dataArrs[i].investorId+'"/></span>';
							dataArrs[i].subConAm = zcontent;
						}
					}
					
				}
				if(dataSize<10){
					$("div[id='noShareholder_Grid_paginator']").hide();//???????????????????????????????????????????????????
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
		 //????????????????????????
		 renderFieldButton:function(){
			 //??????????????????????????????????????????????????????
			 var fieldNameArr = ['processAIEntBasicInfo_Form','financeInfo_Form','entcontactInfo_Form'];
			 for(var i=0;i<fieldNameArr.length;i++){
				 //?????????????????????????????????????????????????????????
				 $("button[name='btnSave_"+fieldNameArr[i]+"']").hide();
				 $("button[name='btnCancel_"+fieldNameArr[i]+"']").hide();
				 //????????????????????????
				 $("button[name='btnEdit_"+fieldNameArr[i]+"']").on("click",function(){
					 var fieldName = this.name.substring(8);
					 torch.fieldEdit(fieldName);
				 });
				 //????????????????????????
				 $("button[name='btnSave_"+fieldNameArr[i]+"']").on("click",function(){
					 var fieldName = this.name.substring(8);
					 torch.fieldSave(fieldName);
				 });
				 //????????????????????????
				 $("button[name='btnCancel_"+fieldNameArr[i]+"']").on("click",function(){
					 //var fieldName = torch.getFieldName(this.name);
					 var fieldName = this.name.substring(10);
					 torch.fieldCancel(fieldName);
				 });
			 }
		 },
		 fieldEdit : function(fieldName){
			// var fieldName = torch.getFieldName(this.name);
			 //????????????????????????????????????????????????????????????????????????
			
			 $("button[name='btnSave_"+fieldName+"']").show();
			 $("button[name='btnCancel_"+fieldName+"']").show();
			 $("button[name='btnEdit_"+fieldName+"']").hide();
			 
			 var fieldName  = $("div[name='"+fieldName+"']");
			 alert(fieldName);
			 //???????????????????????????????????????
			 //var vtype = $("div[name='"+fieldName+"']").attr("vtype");
			 $("div[name='"+fieldName+"']").textfield("option","editable",true);//???????????????????????????????????????		 
			 return;
			 
			 //????????????????????????????????????????????????????????????????????????????????????????????????????????????
			 if(vtype){
				 if(vtype=='textfield'){
					 $("div[name='"+fieldName+"']").textfield("option","editable",true);
				 }
				 if(vtype=='datefield'){
					 $("div[name='"+fieldName+"']").datefield("option","editable",true);
				 }
			 }
			 if(fieldName=='locRemark'){
				 //??????(????????????)??????????????????domStreet???domVillage???domNo???domBuilding???domOther??????????????????
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
		 //?????????????????????????????????????????????
		 fieldSave : function(fieldName){
			 var fieldValue = "";
			 var fieldObject = [];
			 if(fieldName=='locRemark'){
				 var opLoc="?????????";
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
				 //??????vtype??????
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
				jazz.confirm("????????????????", function(){
					 var params = {
		    					url:'../../../approve/process/saveEntField.do',
		    					params : {
		    						gid:jazz.util.getParameter('gid'),
		    						fieldObject:fieldObject
		    					},
		    					callback : function(jsonData,param,res) {
		    						if(res.getAttr('result')=='success'){
		    							 //?????????????????????????????????????????????????????????????????????????????????????????????????????????
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
		    								 //??????(????????????)??????????????????domStreet???domVillage???domNo???domBuilding???domOther??????????????????
		    								 var domNameArr = ['domStreet','domVillage','domNo','domBuilding','domOther'];
		    								 for(var i=0;i<domNameArr.length;i++){
		    									 $("div[name='"+domNameArr[i]+"']").textfield("option","editable",false);
		    								 }
		    							 }
		    							 if(fieldName=='opScope'){
		    								 $("div[name='ptBusScope']").textareafield("option","editable",false);
		    								 $("div[name='opCustomScope']").textareafield("option","editable",false);
		    							 }
		    							 //???????????????????????????????????????????????????????????????
		    							 $("button[name='btnSave_"+fieldName+"']").hide();
		    							 $("button[name='btnEdit_"+fieldName+"']").show();
		    							 $("button[name='btnCancel_"+fieldName+"']").hide();
		    						}else{
		    							jazz.info("????????????!");
		    						}
		    					}
					 }
				 	$.DataAdapter.submit(params);
				 },function(){
					  torch.fieldCancel(fieldName);
				 });
			 }
		 },
		 //???????????????????????????
		 validateField:function(fieldArray){
			 if(fieldArray.length==0){
				 jazz.warn("???????????????????????????!");
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
			 //????????????????????????????????????????????????????????????????????????
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
				 //??????(????????????)??????????????????domStreet???domVillage???domNo???domBuilding???domOther??????????????????
				 var domNameArr = ['domStreet','domVillage','domNo','domBuilding','domOther'];
				 for(var i=0;i<domNameArr.length;i++){
					 $("div[name='"+domNameArr[i]+"']").textfield("option","editable",false);
				 }
			 }
			 if(fieldName=='opScope'){
				 $("div[name='ptBusScope']").textareafield("option","editable",false);
				 $("div[name='opCustomScope']").textareafield("option","editable",false);
			 }
			 //????????????????????????????????????????????????
			 $("button[name='btnSave_"+fieldName+"']").hide();
			 $("button[name='btnCancel_"+fieldName+"']").hide();
			 $("button[name='btnEdit_"+fieldName+"']").show();
			 torch.exceptionApproveQuery();
		 },
		 //????????????????????????????????????????????????
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
			//????????????????????????
			*/
			
		 
		 /*?????????????????????????????????????????????*/
		 _appendShowPicDialog:function(){			 
			 var obj = e.srcElement?e.srcElement:e.target;
				var id=$(obj).attr('id');
				$('#showPic').attr('src','../../../upload/showPic.do?fileId='+id);
		 },
			/**
			 * ????????????
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
								//????????????
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
	    			title : '????????????',
	    			titlealign : 'left',
	    			titledisplay : true,
	    			modal : true,
	    			height : '540',
	    			width : '800',
	    			closable : true,
	    			content:content
	    		});
	    		// ????????????
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
					    
					  	// ???????????????????????????????????????frame?????????
			    		window.openApproveWindow = function(gid){
			    			var url = "approve_do_approve.html?gid="+jazz.util.getParameter('gid') ;
			    			var win = window.parent.jazz.widget({
			        			vtype : 'window',
			        			name : 'approveWindow',
			        			title : '????????????',
			        			titlealign : 'left',
			        			titledisplay : true,
			        			modal : true,
			        			frameurl : url,
			        			height : '540',
			        			width : '600',
			        			closable : 'true'
			        		});
			        		// ????????????
			        		win.window('open');
			    			//util.openWindow("approveWindow", "????????????", url , "600" , "500",false);
			    		};
					  	
					  	/*???????????????????????????????????????*/
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
						torch.getApplySetupPriviewFiles();  //????????????
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
				  		//????????????iframe??????????????????????????????????????????????????????????????????????????????????????????
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
