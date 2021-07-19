define(['require', 'jquery', 'approvecommon','util','approve/approve'], function(require, $, approvecommon,util,approve){
var torch = {
		
		_init : function(){
			 require(['domReady'], function (domReady) {
				  domReady(function () {
					 $("button[name='btnRevocation_regCap']").hide();
					 $("button[name='btnRevocation_locRemark']").hide();
					 $("button[name='btnRevocation_domOwner']").hide();
					 $("button[name='btnRevocation_domAcreage']").hide();
					 var fieldNameArrs = ['regCap','domAcreage','domOwner','ptBusScope','opScope','locRemark','opSuffix','mainScope','opCustomScope'];
					 for(var i=0;i<fieldNameArrs.length;i++){
						 $("div[name='appUserEdit_"+fieldNameArrs[i]+"']").hide();
					 }
					 torch.exceptionApproveQuery();// 执行表单查询，加载表单数据
					 torch.renderFieldButton();//初始化 编辑、保存、取消按钮
					 torch.getListByCategory();//获取图片审查列表
					 torch.makeUpBusinessScope();//拼接经营范围预览
					 torch.changeStyle();
					 torch.initUI();//初始化界面，包括联系人链接 和 右侧审批框
					
		    		// 打开核准对话框，在审查意见frame中调用
		    		window.openApproveWindow = function(gid){
		    			var url = "approve_do_approve.html?gid="+jazz.util.getParameter('gid');
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
		    		$("div[name='domDetail']").css("margin-left","20px");
		    		$("div[name='formpanel']").css("margin","0 auto");
		    		window.refreshImageList = torch.getListByCategory;
		    		
				  });
				});
		 }, // end of _init
		 changeStyle:function(){
			$("div[name='exceptionApprove_Form']").css("width","800px"); 
		 },
		 initUI : function(){
			 // 动态拼接经营范围
			 torch.previewBusiness();
			 
			 //申请人  样式初始化
			 var linkman = $("#linkman").textfield("getValue"); // 后面添加超链接，进行查看详细信息
			 if(!linkman){
				 $("#findLinkManDetail").css("display","none");
			 }
			 $("#findLinkManDetail").off('click').click(torch.findLinkManDetail);// 绑定事件
			 
			 
		 	// 审批意见样式初始化
			//审查意见 始终在屏幕右边
			$(parent.window).scroll(function(){
				$("#censor-notice").css({top:$(parent.window).scrollTop()});
				});
 
				//审查意见的显示隐藏
			$("#censor").click(function(){
				var notice = $("#censor-notice");
				if(notice.width() == '600'){
					notice.animate({width:'0px'},'slow');
				}else{
					 notice.animate({width:'600px'},'slow');
				}
			});
			
			//给父级的iframe加上它的高度
			var height = $('body').height()+140;
			var fram = $('#frametabcontent',window.parent.document);
			
			if(fram){
				fram.css('height',height);
			}
		 },// end of initUI
		 
		 /**
		  * 拼接经营范围
		  */
		 makeUpBusinessScope : function(){
			 var mainScope = $("#mainScope").textareafield("getValue") || "";
			 var opScope = $("#opScope").textareafield("getValue") || "";
			 var ptBusScope = $("#ptBusScope").textareafield("getValue") || "";
			 var opCustomScope = $("#opCustomScope").textareafield("getValue") || "";
			 var opSuffix = $("#opSuffix").textareafield("getValue") || "";
			 
			 var businessScope = '';
			 mainScope = torch.wipeDot(mainScope);
			 if(mainScope){
				 mainScope += "；";
			 }
			 opScope = torch.wipeDot(opScope);
			 if(opScope){
				 opScope += "；";
			 }
			 ptBusScope = torch.wipeDot(ptBusScope);
			 if(ptBusScope){
				 ptBusScope += "；";
			 }
			 opCustomScope = torch.wipeDot(opCustomScope);
			 if(opCustomScope){
				 opCustomScope += "；";
			 }
			 opSuffix = torch.wipeDot(opSuffix);
			 
			 // 主营 + 许可 + 一般 + 手输
			 // businessScope = mainScope + opScope + ptBusScope + opCustomScope;

			 // 主营 + 一般 + 手输  + 许可
			 businessScope = mainScope + ptBusScope + opCustomScope + opScope ;

			 businessScope = torch.wipeDot(businessScope);
			 if(businessScope){
				 businessScope += "。";
			 }
			 if(opSuffix){ 
				 businessScope += "（"+opSuffix+"。）";
			 }
			 $("#businessScope").empty().html(businessScope);
		 },
		 wipeDot : function(s){
			 if(!s){
				 return '';
			 }
			 s = $.trim(s);
			 s = s.replace(/^[。|\.|;|；|,|，|、]*/,'');
			 s = s.replace(/[。|\.|;|；|,|，|、]*$/,'');
			 return s;
		 },
		 
		 //动态加载经营范围预览
		 previewBusiness:function(){
			$("#mainScope").keyup(function(){
				torch.makeUpBusinessScope();
			}); 
			$("#opScope").keyup(function(){
				torch.makeUpBusinessScope();
			}); 
			
			$("#ptBusScope").keyup(function(){
				torch.makeUpBusinessScope();
			});
			
			$("#opCustomScope").keyup(function(){
				torch.makeUpBusinessScope();
			});
			
			$("#opSuffix").keyup(function(){
				torch.makeUpBusinessScope();
			});
		 },
	
		edit_primaryValues : function(){
			return "&gid="+jazz.util.getParameter('gid');
		},
		edit_fromNames : [ 'exceptionApprove_Form', 'linkMan_Form'],
 
	 renderFieldButton:function(){
		 var fieldNameArr = ['regCap','domAcreage','domOwner','ptBusScope','opScope','locRemark','opSuffix','mainScope','opCustomScope'];
		 for(var i=0;i<fieldNameArr.length;i++){
			 //初始化隐藏保存和取消按钮，显示编辑按钮
			 $("button[name='btnSave_"+fieldNameArr[i]+"']").hide();
			 $("button[name='btnCancel_"+fieldNameArr[i]+"']").hide();
			 //判断如果改字段做过修改，显示撤销按钮，否则不显示（经营范围相关字段也不显示）
			 if(fieldNameArr[i]=="regCap"||fieldNameArr[i]=="locRemark"||fieldNameArr[i]=="domOwner"||fieldNameArr[i]=="domAcreage"){
				 if(torch.checkfieldEdit(fieldNameArr[i])==true){
					 $("button[name='btnRevocation_"+fieldNameArr[i]+"']").show();
				 }else{
					 $("button[name='btnRevocation_"+fieldNameArr[i]+"']").hide();
				 }
			 }
			 //判断如果申请用户修改字段做过修改，显示已修改标识，否则不显示
			 if(fieldNameArr[i]=="regCap"||fieldNameArr[i]=="locRemark"||fieldNameArr[i]=="domOwner"||fieldNameArr[i]=="domAcreage"
				 ||fieldNameArr[i]=="opScope"||fieldNameArr[i]=="opSuffix"||fieldNameArr[i]=="opCustomScope"||fieldNameArr[i]=="ptBusScope"||fieldNameArr[i]=="mainScope"){
				 if(torch.checkAppUserFieldEdit(fieldNameArr[i])==true){
					 $("div[name='appUserEdit_"+fieldNameArr[i]+"']").show();
				 }else{
					 $("div[name='appUserEdit_"+fieldNameArr[i]+"']").hide();
				 }
			 }
			 //绑定编辑按钮事件
			 $("button[name='btnEdit_"+fieldNameArr[i]+"']").on("click",function(){
				 var fieldName = this.name.substring(8);
				 torch.fieldEdit(fieldName);
			 });
			 //绑定保存按钮事件
			 $("button[name='btnSave_"+fieldNameArr[i]+"']").on("click",function(){
				 var fieldName = this.name.substring(8);
				 torch.fieldSave(fieldName,"ptSave");
			 });
			 //绑定取消按钮事件
			 $("button[name='btnCancel_"+fieldNameArr[i]+"']").on("click",function(){
				 //var fieldName = torch.getFieldName(this.name);
				 var fieldName = this.name.substring(10);
				 torch.fieldCancel(fieldName);
			 });
			 //绑定撤销按钮事件
			 $("button[name='btnRevocation_"+fieldNameArr[i]+"']").on("click",function(){
				 var fieldName = this.name.substring(14);
				 torch.fieldRevocation(fieldName);
			 });
		 }
	 },
	 fieldEdit : function(fieldName){
		// var fieldName = torch.getFieldName(this.name);
		 //点击编辑按钮时，显示保存和取消按钮，隐藏编辑按钮,撤销按钮
		 $("button[name='btnSave_"+fieldName+"']").show();
		 $("button[name='btnCancel_"+fieldName+"']").show();
		 $("button[name='btnEdit_"+fieldName+"']").hide();
		 $("button[name='btnRevocation_"+fieldName+"']").hide();
		 //获取vtype类型
		 var vtype = $("div[name='"+fieldName+"']").attr("vtype");
		 //当点击某一编辑按钮时，隐藏该编辑按钮，使其变成可编辑字段，并显示保存按钮
		 if(vtype){
			 if(vtype=='textfield'){
				 $("div[name='"+fieldName+"']").textfield("option","editable",true);
			 }
			 if(vtype=='textareafield'){
				 $("div[name='"+fieldName+"']").textareafield("option","editable",true);
			 }
			 if(vtype=='comboxfield'){
				 $("div[name='"+fieldName+"']").comboxfield("option","disabled",false);
			 }
		 }
		 if(fieldName=='locRemark'){
			 //住所(经营场所)的按钮控制着domStreet，domVillage，domNo，domBuilding，domOther的只读与编辑
			 var domNameArr = ['domDetail'];
			 for(var k=0;k<domNameArr.length;k++){
				 $("div[name='"+domNameArr[k]+"']").textfield("option","editable",true);
			 }
		 }
		 if(fieldName=='opScope'){
			 $("div[name='mainScope']").textareafield("option","editable",true);
			 $("div[name='ptBusScope']").textareafield("option","editable",true);
			 $("div[name='opCustomScope']").textareafield("option","editable",true);
			 $("div[name='opSuffix']").textareafield("option","editable",true);
			 //$("#scopeSign").removeAttr("disabled");
		 }
	 },
	
	 fieldRevocation:function(fieldName){
		 torch.fieldSave(fieldName,'cxSave');
	 },
	 
	 fieldSave : function(fieldName,type){
		 var fieldValue = "";
		 var fieldObject = [];
		 if(fieldName=='locRemark'){
			 var opLoc="北京市";
			 var domNameArr = ['domDetail'];
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
					 if(vtype=='comboxfield'){
						 domValue = $("div[name='"+domName+"']").comboxfield("getValue")||'';
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
		 }else if(fieldName=='opScope'){
			 var scopeArr = ['mainScope','opScope','ptBusScope','opCustomScope','opSuffix','businessScope'];
			 for(var i=0;i<scopeArr.length;i++){
				 var scopeName = scopeArr[i];
				 var scopeValue = "";
				 var textfield = $("div[name='"+scopeName+"']");
				 if(textfield.length > 0){
					 scopeValue = textfield.textareafield("getValue")|| '';
				 }else{
					 scopeValue = $("#"+scopeName).text() || '';
				 }
				 fieldObject.push({"fieldName":scopeName,"fieldValue":scopeValue});
			 }
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
			 if(!$("#exceptionApprove_Form").formpanel('validate')){
				 return false;
			 }
			var tip = "确认保存吗？";
			if(type=='cxSave'){
				tip = "确认要撤销吗？";
			}
			
			//在父级iframe里面弹出的问题,因为当前高度是
			//window.parent.jazz.confirm(tip, function(){
			window.parent.jazz.confirm(tip, function(){
			var params = {
	    					url:'../../../approve/process/saveEntField.do',
	    					params : {
	    						gid:jazz.util.getParameter('gid'),
	    						saveType:type,
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
	    								 var domNameArr = ['domDetail'];
	    								 for(var i=0;i<domNameArr.length;i++){
	    									 $("div[name='"+domNameArr[i]+"']").textfield("option","editable",false);
	    								 }
	    							 }
	    							 if(fieldName=='opScope'){
	    								 $("div[name='mainScope']").textareafield("option","editable",false);
	    								 $("div[name='ptBusScope']").textareafield("option","editable",false);
	    								 $("div[name='opCustomScope']").textareafield("option","editable",false);
	    								 $("div[name='opSuffix']").textareafield("option","editable",false);
	    							 }
	    							 //保存成功后显示编辑按钮，隐藏保存和取消按钮
	    							 $("button[name='btnSave_"+fieldName+"']").hide();
	    							 $("button[name='btnEdit_"+fieldName+"']").show();
	    							 $("button[name='btnCancel_"+fieldName+"']").hide();
	    							 //判断如果改字段做过修改，显示撤销按钮，否则不显示（经营范围相关字段也不显示）
	    							 if(fieldName=="regCap"||fieldName=="locRemark"||fieldName=="domOwner"||fieldName=="domAcreage"){
		    							 if(torch.checkfieldEdit(fieldName)==true){
		    								 $("button[name='btnRevocation_"+fieldName+"']").show();
		    							 }else{
		    								 $("button[name='btnRevocation_"+fieldName+"']").hide();
		    							 }
	    							 }
	    							 if(type=='cxSave'){
	    								 torch.exceptionApproveQuery();
	    							 }
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
	validateField:function(fieldArray){
			 if(fieldArray.length==0){
				 jazz.warn("请选择要保存的字段!");
				 return false;
			 }
			 for(var i=0;i<fieldArray.length;i++){
				 var fieldName = fieldArray[i].fieldName;
				 if(fieldName=='businessScope'){
					 continue;
				 }
				 
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
				 //住所(经营场所)的按钮控制着domDetail的只读与编辑
				 var domNameArr = ['domDetail'];
				 for(var i=0;i<domNameArr.length;i++){
					 $("div[name='"+domNameArr[i]+"']").textfield("option","editable",false);
				 }
			 }
			 if(fieldName=='opScope'){
				 $("div[name='mainScope']").textareafield("option","editable",false);
				 $("div[name='ptBusScope']").textareafield("option","editable",false);
				 $("div[name='opCustomScope']").textareafield("option","editable",false);
				 $("div[name='opSuffix']").textareafield("option","editable",false);
			 }
			 //隐藏保存和取消按钮，显示编辑按钮
			 $("button[name='btnSave_"+fieldName+"']").hide();
			 $("button[name='btnCancel_"+fieldName+"']").hide();
			 $("button[name='btnEdit_"+fieldName+"']").show();
			 //判断如果改字段做过修改，显示撤销按钮，否则不显示（经营范围相关字段也不显示）
			 if(fieldName=="regCap"||fieldName=="locRemark"||fieldName=="domOwner"||fieldName=="domAcreage"){
				 if(torch.checkfieldEdit(fieldName)==true){
					 $("button[name='btnRevocation_"+fieldName+"']").show();
				 }else{
					 $("button[name='btnRevocation_"+fieldName+"']").hide();
				 }
			 }
			 torch.exceptionApproveQuery();
		 },
		 //判断字段是否发生过修改
		 checkfieldEdit : function(fieldName){
			 var editFlag = false;
			 $.ajax({
					url:'../../../approve/process/checkfieldEdit.do',
					type:'post',
					async:false,
					dataType:'json',
					data:{
						"gid":jazz.util.getParameter("gid"),
						"fieldName":fieldName
					},
					success:function(data){
						editFlag = data;
					}
			 });
			 return editFlag;
		 },
		 //判断字段在申请平台申请用户是否发生过修改
		 checkAppUserFieldEdit : function(fieldName){
			 var editFlag = false;
			 $.ajax({
					url:'../../../approve/process/checkAppUserFieldEdit.do',
					type:'post',
					async:false,
					dataType:'json',
					data:{
						"gid":jazz.util.getParameter("gid"),
						"fieldName":fieldName
					},
					success:function(data){
						editFlag = data;
					}
			 });
			 return editFlag;
		 },
		 exceptionApproveQuery : function(){
			var updateKey= "&wid=34370D1AD90533CEE055000000000001,349386E3DA5F7262E055000000000001";
			$.ajax({
					url:'../../../torch/service.do?fid=34370D1AD90333CEE055000000000001'+updateKey+torch.edit_primaryValues(),
					type:"post",
					async:false,
					dataType:"json",
					success: function(data){
						var jsonData = data.data;
						if($.isArray(jsonData)){
							 for(var i = 0, len = jsonData.length; i<len; i++){
								 $("div[name='"+jsonData[i].name+"']").formpanel("setValue",jsonData[i] || {});
								 if(jsonData[i].name=='exceptionApprove_Form'){
									 var isMainScopeStandard =jsonData[i].data.isMainScopeStandard||'';
									 if(isMainScopeStandard=='1'){
										 $("div[name='mainScope']").removeClass("mainScope_red");
										 $("div[name='mainScope']").addClass("mainScope_green");
									 }else{
										 $("div[name='mainScope']").removeClass("mainScope_green");
										 $("div[name='mainScope']").addClass("mainScope_red");
									 }
								 }
							 }
						 }
						var entName = $("div[name='entName']").hiddenfield('getValue');
						$("#commonEntName").textfield('setValue',entName);
					}
				});
		},
	
		exceptionApproveReset : function(){
			for( x in torch.edit_fromNames){
				$("div[name='"+torch.edit_fromNames[x]+"']").formpanel("reset");
			} 
		},
		
		/*打开图像审查弹出框*/
		openImage:function(categoryId){
			if(!categoryId){
				jazz.info('未获取图片类别，请刷新后重试。');
			}
			var gidParams = "&gid="+jazz.util.getParameter('gid')||'';
			
		
			var win  = window.open('image_investigate.html?categoryId='+categoryId+gidParams,'imgviewer','width=850,height=540,top=50,left=100,toolbar=no,menubar=no,resizable=no,location=no');
			
			//解决图片审核通过之后，图片没当前窗口没有刷新的问题。	
			var open_q  = true;//关闭标识
			var configW;//监听器
			configW = setInterval(function(){
				if(open_q){
					//// 如果新窗口打开为真
					if(win&&win.closed){// 如果这个新窗口存在并且已经被关闭
						open_q = false;
						configW = null;  //释放空间
						clearInterval(configW);
						torch.getListByCategory();//重新调用一下一个.do
					}
				}
			},500);
		
			
			
			torch.getListByCategory();
		},
	
		/*获取上传图像列表*/
		getListByCategory:function(){
			$.ajax({
				url:'../../../approve/pic/getCategory.do?gid='+jazz.util.getParameter("gid"),
				type:'post',
				async:false,
				dataType:'json',
				success:function(data){
					var html = '';
					
					for(var i=0;i<data.length;i++){
						
						var row = data[i] || {};
						var id = row.categoryId || '';
						var title = row.title || '';
						var totalCnt = row.totalCnt || 0;
						var toCnt = row.toCnt || 0;
						var passCnt = row.passCnt || 0;
						var unPassCnt = row.unPassCnt || 0;
						if(!id){
							continue ;
						}
						if(totalCnt==0){
							continue;
						}
						var styleClass = "image-category-" ;
						var picLabel = 0;
						if(toCnt>0){
							styleClass += "todo"; //如果当前图片有通过审核的，则添加上对应的通过的样式
							label = 28;
						}
						if(toCnt==0 && unPassCnt>0){
							label = 29;
							styleClass += "unPass";//如果当前图片有通过审核的，则添加上对应的没有通过的样式
						}
						if(toCnt==0 && unPassCnt==0){
							label = 27;
							styleClass += "pass"; //通过的样式
						}
						html +=  '<div class="uploadfile '+styleClass+'" >'
							//+'<div class="fileTips caption"></div>'
						 +'<img  class="fileTips caption" src="../../../static/image/approve/'+label+'.png" />'
							+'<div class="fileWrap caption"></div>'
							//+'<img  class="picStates" src="../../../static/image/approve/'+label+'.png" />'
							 //+   '<img class="uploadimg '+styleClass+'" src="../../../static/image/approve/category'+id+'.png">'
							 +   '<img class="uploadimg"  src="../../../static/image/approve/category'+id+'.png">'
							 +   '<div title="'+title+'" class="uploadtitle" categoryId="'+id+'">'+title+'</div>'
							 +   '</div>';
					}
					$("#files").html('');
					$("#files").append(html);
					$('.uploadtitle').on('click',function(){
						torch.openImage($(this).attr("categoryId"));
					});
					$('.uploadimg').on('click',function(){
						torch.openImage($(this).next().attr("categoryId"));
					});
				}
				
			})
		},
		
		
		/**
		 * 得到当前浏览器的可视窗口宽高
		 */
		_getClientHeight:function(){
			var clientHeight = 0;
			if (document.body.clientHeight && document.documentElement.clientHeight) {
				clientHeight = (document.body.clientHeight < document.documentElement.clientHeight) ? document.body.clientHeight: document.documentElement.clientHeight;
			} else {
				clientHeight = (document.body.clientHeight > document.documentElement.clientHeight) ? document.body.clientHeight:document.documentElement.clientHeight;
			}
			return clientHeight;
		},
		//移动窗口时，始终让元素居中
		_resize:function($dom){
			var _this = this;
			$(window).resize(function(){
				_this._center_Dom($dom);
			});
		},
		
		//改变浏览器窗口时，始终让当前窗口居中
		_center_Dom:function($dom){
			var windowWidth = $(window).width();
			var windowHeight = this._getClientHeight();
			var domWidth = this._getDomWidthAndHeight($dom).width;
			var domHeight = this._getDomWidthAndHeight($dom).height;
			var left = (windowWidth-domWidth)/2;
			var top =  (windowHeight-domHeight)/2;
			
			$dom.css({left:left,top:top-200});
			
		},
		//得到当前Dom元素的宽和高
		_getDomWidthAndHeight:function($dom){
			return {
				width:$dom.width(),
				height:$dom.height()
			}
			
		},
		
		
		/**
		 * 申请人详情按钮事件。
		 */
		findLinkManDetail : function(){
			//获取业务gid
			var gid = $("div[name='linkMan_Form']").find("div[name='gid']").first().hiddenfield("getValue");
			if(gid){
				util.openWindow("linkManDetail","申请人详情","linkManDetail.html?gid="+gid,800,400);
			}
			//改变它的大小
			//让申请人永远居中
			var linkMan  = $("div[name='linkManDetail']");
			torch._center_Dom(linkMan);
			torch._resize(linkMan);
		},
		/**
	     * 关闭窗口 
	     */
	    closeWindow : function (refresh){
	    	if(refresh.trim()=='true'){
	    		jazz.info("保存成功");	
	    	}
	    	util.closeWindow("linkManDetail");
	    	if(refresh.trim()=='true'){
	    		torch.exceptionApproveQuery();
	    	}
	    },
	    
	    
	    //根据gid得到用户上传的自定义章程列表
	    //根据gid得到用户上传的自定义章程列表
	    getPdfViewByGid:function(){
	    	$.ajax({
				url:'../../../approve/pdf/pdf.do?gid='+jazz.util.getParameter("gid"),
				type:'post',
				async:false,
				dataType:'json',
				success:function(data){
					var html = '';
						var row = data[i] || {};
						var id = row.categoryId || '';
						var title = row.title || '';
						var totalCnt = row.totalCnt || 0;
						var toCnt = row.toCnt || 0;
						var passCnt = row.passCnt || 0;
						var unPassCnt = row.unPassCnt || 0;
						if(!id){
							continue ;
						}
						if(totalCnt==0){
							continue;
						}
						var styleClass = "image-category-" ;
						var picLabel = 0;
						if(toCnt>0){
							styleClass += "todo"; //如果当前图片有通过审核的，则添加上对应的通过的样式
							label = 28;
						}
						if(toCnt==0 && unPassCnt>0){
							label = 29;
							styleClass += "unPass";//如果当前图片有通过审核的，则添加上对应的没有通过的样式
						}
						if(toCnt==0 && unPassCnt==0){
							label = 27;
							styleClass += "pass"; //通过的样式
						}
						//将当前的默认内容追加到pdf预览窗口之中
					
				},
				error:function(data){
					jazz.info("连接中断，获取pdf失败");
				}
	    	
	    	});
	    },
	    
	    //打开pdf审查
	    openPdfViewer:function(){
	    	var gidParams = jazz.util.getParameter("gid")||'';
	    	var win  = window.open('pdf_investigate.html?gid='+gidParams,'pdfviewer','width=850,height=540,top=50,left=100,toolbar=no,menubar=no,resizable=no,location=no');
			
			//解决图片审核通过之后，图片没当前窗口没有刷新的问题。	
			var open_q  = true;//关闭标识
			var configW;//监听器
			configW = setInterval(function(){
				if(open_q){
					//// 如果新窗口打开为真
					if(win&&win.closed){// 如果这个新窗口存在并且已经被关闭
						open_q = false;
						configW = null;  //释放空间
						clearInterval(configW);
						torch.getPdfViewByGid();//重新调用一下一个.do
					}
				}
			},500);
		
	    	
	    }
	    
	};
	torch._init();
	return torch;

});


//点击每一张图片的时候，更新它的签字盖章页状态
function updateImageState(){
	
	
}

//重新绑定图片点击打开窗口事件
function openImage(categoryId){
	if(!categoryId){
		jazz.info('未获取图片类别，请刷新后重试。');
	}
	var gidParams = "&gid="+jazz.util.getParameter("gid")||'';
	var win  = window.open('image_investigate.html?categoryId='+categoryId+gidParams,'imgviewer','width=850,height=540,top=50,left=100,toolbar=no,menubar=no,resizable=no,location=no');
}



