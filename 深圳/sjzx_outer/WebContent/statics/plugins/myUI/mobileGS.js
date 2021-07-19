	function initData() {
			$("#list").removeClass("show").addClass("hide");
			//clearInfo();
			var unifsocicrediden = $('#unifsocicredidenInput').val();
			var entname = $('#entNameInput').val();
			
			var code = $('#index_code').val();
			var flag = checkCode();
			var isFull =  $("#isFull").prop("checked");
			unifsocicrediden = $.trim(unifsocicrediden)
			entname = $.trim(entname)
			code = $.trim(code);
			if(isEmpty(unifsocicrediden)&&isEmpty(entname)){
				alert("注册号\统一社会信用代码或商事主体名称至少一项不能为空");
				$("#submit2").text("查询");
				//$("#tagsSwitch").removeClass("show").addClass("hide");
			}else if(isEmpty(code)){
				alert("验证码不能为空");
				$("#submit2").text("查询");
				//$("#tagsSwitch").removeClass("show").addClass("hide");
			}else{
				if(isEmpty(unifsocicrediden)&&isEmpty(entname)){
					 $('#myModal').modal('show');
				}else{
					if(!isFull){//不勾选
						var tempFlag  = getKeyWorkd(entname);//存在关键字
						if(tempFlag){
							alert("查询过于宽泛");
							$("#submit2").text("查询");
						}else{
							if(flag!="0"){
								clearInfo();
								$.ajax({
									url : '../entEnt/entList.do',
									type : 'POST',
									dataType : 'json',
									beforeSend:function(){
										$("#list").addClass("hide");
										$("#submit2").text("查询中...");
										clearDataMap();
										//$("#submit2").off("click");
									},
									data:{
										"unifsocicrediden" : unifsocicrediden,
										"entname" : entname,
										"flag" : flag,
										"isFull":isFull
										},
									success:function(data){
										$("#entNameInput").val("");
										$("#unifsocicredidenInput").val("");
										$("#submit2").text("查询");
										$("#infos").removeClass("hide").addClass("show");
										$("#infosfunDesc").removeClass("hide").addClass("show");
										$("#infosfunDescLine").removeClass("hide").addClass("show");
										$("#validateCodeState").attr("data-status",flag);
										$("#submit2").text("查询");
										$("#index_code").parent().removeClass("has-warning has-success");
										loadingList(data);
									},
									error:function(){
										$("#tagsSwitch").removeClass("show").addClass("hide");
									}
								});
							}else{
								clearInfo();
								$("#tagsSwitch").removeClass("show").addClass("hide");
							} 
						}
					}else{
						$("#list").removeClass("show").addClass("hide");
						clearInfo(); //清空标签及内容
						$("#infos").empty(); //异常状态
						$.ajax({
							url : '../entEnt/detail.do',
							type : 'POST',
							dataType : 'json',
							async : false,//取消异步请求
							data : {
								"unifsocicrediden" : unifsocicrediden,
								"entname" : entname,
								"flag" : flag
							},
							beforeSend:function(){
								$("#submit2").text("查询中...");
								clearDataMap();
							},
							success : function(data) {
								if (data.data[0].data == "0") {
									alert("验证码不正确");
									clearInfo();
									$("#tagsSwitch").removeClass("show").addClass("hide");
									$("#infos").removeClass("show").addClass("hide");
									$("#infosfunDesc").removeClass("show").addClass("hide");
									$("#infosfunDescLine").removeClass("show").addClass("hide");
									$("#submit2").text("查询");
								} else {
									$("#entNameInput").val("");
									$("#unifsocicredidenInput").val("");
									$("#submit2").text("查询");
									$("#tagsSwitch").removeClass("hide").addClass("show");
									$("#infos").removeClass("hide").addClass("show");
									$("#infosfunDesc").removeClass("hide").addClass("show");
									$("#validateCodeState").attr("data-status",flag);
									$("#index_code").parent().removeClass("has-warning has-success");
									
									if (data.data[0].data.length != 0) {
										var id = data.data[0].data[0].id;
										var entJinYinYiChang = data.data[0].data[0].entflag;
										var error = data.data[0];
										var opetype = data.data[0].data[0].opetype;
										var entname = data.data[0].data[0].entname;
										var regno =  data.data[0].data[0].regno;
										var unifsocicrediden = data.data[0].data[0].unifsocicrediden;
										if (isNotEmpty(id)) {
											$("#entId").attr("ent", id);
										}
										if (isNotEmpty(entJinYinYiChang)) {
											$("#entJinYinYiChang").attr("entJinYinYiChang", entJinYinYiChang);
										}
										if (isNotEmpty(entname)) {
											 $("#entName").attr("entName",entname);
										}
										
										if (isNotEmpty(opetype)) {
											 $("#opetype").attr("opetype",opetype);
										}
										
										if (isNotEmpty(regno)) {
											 $("#regno").attr("regno",regno);
										}
										if (isNotEmpty(unifsocicrediden)) {
											 $("#unifsocicrediden").attr("unifsocicrediden",unifsocicrediden);
										}

										var  entId = $("#entJinYinYiChang").attr("entjinyinyichang");
										var opetype = "  " ;
										if (isNotEmpty(data.data[0].data[0].opetype)) {
											opetype = data.data[0].data[0].opetype;//企业类型
										}
										var enttype = " ";
										if (isNotEmpty(data.data[0].data[0].enttype)) {
										enttype = data.data[0].data[0].enttype;//企业类型
										}
										
										$.ajax({
											url:"../entEnt/entStatus.do",
											type:"post",
											data:{id:entId},
											dataType:"json",
											async : false,//取消异步请求
											success:function(data){
												
												// 企业撤销登记标志，entStatus>0为撤销登记，entStatus<=0为正常注册企业
												var entStatus = data.data[0].data[0].entStatus; 
												// 异常名录
												var ycml = data.data[0].data[0].ycml;
												// 严重违法失信
												var yzwf = data.data[0].data[0].yzwf;
												
												/*
												 * 逻辑说明：
												 * 	  1、首先判断企业的撤销登记状态，如果是撤销登记，则向页面展示“该企业已撤销登记”，此时其他信息不再展示；
												 * 	  2、如果不是撤销登记，如果有异常信息和严重违法失信，则同时展示两条信息，否则单个展示
												 */
												if (entStatus > 0) { // 企业撤销登记
													if (opetype=="GT") {
														$("#infos").html("<div class='alert alert-danger clearfix' role='alert'><p class='pull-left'><img src='../statics/images/Tip.gif'></p><div class='pull-left'><p class='fontRed'>提示:</p><p class='fontRed'><span >该个体工商户</span>已撤销登记！</p></div></div>  ");
													} else {
														$("#infos").html("<div class='alert alert-danger clearfix' role='alert'><p class='pull-left'><img src='../statics/images/Tip.gif'></p><div class='pull-left'><p class='fontRed'>提示:</p><p class='fontRed'>&nbsp;&nbsp;&nbsp;&nbsp;该企业已撤销登记！</p></div></div>  ");
													}
												} else { // 异常信息
													if (ycml <= 0 && yzwf <= 0) {  // 无异常信息
														return;
													}
													
													if (opetype == "GT" && ycml > 0) { // 个体异常名录
														$("#infos").html("<div class='alert alert-danger clearfix' role='alert'><p class='pull-left'><img src='../statics/images/Tip.gif'></p><div class='pull-left'><p class='fontRed'>提示:</p><p class='fontRed'><span >该个体工商户</span>已被标记为经营异常状态！</p></div></div>  ");
														return;
													}
													
													if (opetype != "GT" && ycml > 0 && yzwf > 0) { // 企业 有异常名录和严重违法失信
														$("#infos").html("<div class='alert alert-danger clearfix' role='alert'><p class='pull-left'><img src='../statics/images/Tip.gif'></p><div class='pull-left'><p class='fontRed'>提示:</p><p class='fontRed'><span >该"+enttype+"</span>已被载入经营异常名录！</p>" 
																+ "<p class='fontRed'><span >该"+enttype+"</span>已被列入严重违法失信企业名单！</p></div></div>  ");
														return;
													}
													
													if (opetype != "GT" && ycml > 0) { // 企业 有异常名录
														$("#infos").html("<div class='alert alert-danger clearfix' role='alert'><p class='pull-left'><img src='../statics/images/Tip.gif'></p><div class='pull-left'><p class='fontRed'>提示:</p><p class='fontRed'><span >该"+enttype+"</span>已被载入经营异常名录！</p></div></div>  ");
														return;
													}
													
													if (opetype != "GT" && yzwf > 0) { // 企业 有严重违法失信
														$("#infos").html("<div class='alert alert-danger clearfix' role='alert'><p class='pull-left'><img src='../statics/images/Tip.gif'></p><div class='pull-left'><p class='fontRed'>提示:</p><p class='fontRed'><span >该"+enttype+"</span>已被列入严重违法失信企业名单！</p></div></div>  ");
														return;
													}
												}
												
												/*// state= sx:为严重违法失信     yc:为异常名录  
												var state = data.data[0].data[0].state;
												// 异常名录或严重违法失信状态标志，count>0为有异常或违法失信，count<=0则没有
												var count = data.data[0].data[0].count;	
												// 企业撤销登记标志，entStatus>0为撤销登记，entStatus<=0为正常注册企业
												var entStatus = data.data[0].data[0].entStatus; 
												
												if (entStatus > 0 ) { // 企业撤销登记
													if (opetype=="GT") {
														$("#infos").html("<div class='alert alert-danger clearfix' role='alert'><p class='pull-left'><img src='../statics/images/Tip.gif'></p><div class='pull-left'><p class='fontRed'>提示:</p><p class='fontRed'><span >该个体工商户</span>已撤销登记！</p></div></div>  ");
													} else {
														$("#infos").html("<div class='alert alert-danger clearfix' role='alert'><p class='pull-left'><img src='../statics/images/Tip.gif'></p><div class='pull-left'><p class='fontRed'>提示:</p><p class='fontRed'>&nbsp;&nbsp;&nbsp;&nbsp;该企业已撤销登记！</p></div></div>  ");
													}
												} else {
													if (count > 0) {
														if (state == "sx") {
															if(opetype=="GT"){
																$("#infos").html("<div class='alert alert-danger clearfix' role='alert'><p class='pull-left'><img src='../statics/images/Tip.gif'></p><div class='pull-left'><p class='fontRed'>提示:</p><p class='fontRed'><span >该个体工商户</span>已被标记为严重违法失信状态！</p></div></div>  ");
															}else{
																$("#infos").html("<div class='alert alert-danger clearfix' role='alert'><p class='pull-left'><img src='../statics/images/Tip.gif'></p><div class='pull-left'><p class='fontRed'>提示:</p><p class='fontRed'><span >该"+enttype+"</span>已被列入严重违法失信企业名单！</p></div></div>  ");
															}
														} else {
															if (opetype == "GT") {
																$("#infos").html("<div class='alert alert-danger clearfix' role='alert'><p class='pull-left'><img src='../statics/images/Tip.gif'></p><div class='pull-left'><p class='fontRed'>提示:</p><p class='fontRed'><span >该个体工商户</span>已被标记为经营异常状态！</p></div></div>  ");
															}else{
																$("#infos").html("<div class='alert alert-danger clearfix' role='alert'><p class='pull-left'><img src='../statics/images/Tip.gif'></p><div class='pull-left'><p class='fontRed'>提示:</p><p class='fontRed'><span >该"+enttype+"</span>已被载入经营异常名录！</p></div></div>  ");
															}
															
														}
													}
												}*/
											}
										});
										getCode();//
									
										temp0(data.data[0].data);//默认显示基本信息页
										isDispayTagByOpetype(opetype); //过滤对应企业显示的标签
										changeTagger(); //切换标签
										$("#submit2").text("查询");
										
									}else{
										var entname = $('#entNameInput').val();
										$("#tagsSwitch").removeClass("show").addClass("hide");
										alert("暂无对应"+entname+"信息");
										$("#submit2").text("查询");
										getCode();//
									}
								}
							}
						});
				   }
				}
			}
		}

		function isDispayTagByOpetype(opetype) {
			
			$("#tagsSwitch").css({"display":"block"});
			
			$("#p1").html("<a href='#panel-1'data-toggle='tab'>基本信息</a>");
			$("#p5").html("<a href='#panel-5'data-toggle='tab'>变更信息</a>");
			$("#p6").html("<a href='#panel-6'data-toggle='tab'>股权质押信息</a>");
			$("#p7").html("<a href='#panel-7'data-toggle='tab'>动产抵押信息</a>");
			$("#p8").html("<a href='#panel-8'data-toggle='tab'>法院冻结信息</a>");
			$("#p9").html("<a href='#panel-9'data-toggle='tab'>经营异常信息</a>");
			$("#p11").html("<a href='#panel-11'data-toggle='tab'>严重违法失信信息</a>");
			
			if(opetype=="GS"||opetype=="FGS"||opetype=="NZFR"||opetype=="NZYY"||opetype=="HHQY"||opetype=="GRDZ"||opetype=="HHFZ"||opetype=="GRDZFZ"||opetype=="WZGS"||
					opetype=="WZHH"||opetype=="WZHHFZ"||opetype=="WZFZ"||opetype=="HZS"||opetype=="HZSFZ"||opetype=="GT"){
					$("#p2").html("<a href='#panel-2'data-toggle='tab'>许可经营信息</a>");
			}
			//这些有股东信息标签
			if(opetype=="GS"||opetype=="NZFR"||opetype=="WZGS"){
				$("#p3").html("<a href='#panel-3'data-toggle='tab'>股东信息</a>");
				
			}
			
			if(opetype=="HHQY"||opetype=="WZHH"){
				$("#p3").html("<a href='#panel-3'data-toggle='tab'>合伙人信息</a>");
			}
			
			if(opetype=="HZS"||opetype=="JT"){
				$("#p3").html("<a href='#panel-3'data-toggle='tab'>成员信息</a>");
			}
			
			if(opetype=="SLYB"){ //三来一补
				$("#p3").html("<a href='#panel-3'data-toggle='tab'>协议方</a>");
			}
			
			if(opetype=="GS"||opetype=="NZFR"||opetype=="WZGS"||opetype=="WGDB"){
				$("#p4").html("<a href='#panel-4'data-toggle='tab'>成员信息</a>");
			}else{
				//$("#p4").html("<a href='#panel-4'data-toggle='tab'>主要人员信息</a>");
			}
			
		    //$("#p4").html("<a href='#panel-4'data-toggle='tab'>成员信息</a>");
			
		}
		
		
		function changeTagger() {
			var tabId = 0;
			var flag = $("#validateCodeState").data("status");
			$('a[data-toggle="tab"]').on('shown.bs.tab', function(e) {
				tabId = $(e.target).parent().index();// 获取已激活的标签页的名称开始
				//getDataByTaggerId(flag, tabId);//切换对应的标签
				getDataByTaggerId(1, tabId);//切换对应的标签
				
			});
			$('#tagDispayEnt a[href="#panel-1"]').tab('show'); //默认将基本信息显示出来
		}

		function getDataByTaggerId(flag, tagId) {
			var id = $("#entId").attr("ent");
			var regno = $("#regno").attr("regno");
			var unifsocicrediden = $("#unifsocicrediden").attr("unifsocicrediden");
			var opetype = $("#opetype").attr("opetype");
			if (tagId == "5"||tagId == "6"||tagId == "7"||tagId == "8"||tagId == "9") {
				id = $("#entJinYinYiChang").attr("entJinYinYiChang");//企业异常名录信息
			}
			if(tagId==4){
				var entName = $("#entName").attr("entname");
			    $("#panel-5").find("h2").html(entName + "的变更信息");
			}
			//拼接标签下方的标题股权质押信息
			if(tagId==5){
				var entName = $("#entName").attr("entname");
			    $("#panel-6").find("h2").html(entName + "的股权质押信息");
			}
			if(tagId==6){
				var entName = $("#entName").attr("entname");
				$("#panel-7").find("h2").html(entName + "的动产抵押信息");
			}
			if(tagId==7){
				var entName = $("#entName").attr("entname");
				$("#panel-8").find("h2").html(entName + "的法院冻结信息");
			}
			if(tagId==8){
				var entName = $("#entName").attr("entname");
				$("#panel-9").find("h2").html(entName + "的经营异常信息");
			}
			if(tagId==9){
				var entName = $("#entName").attr("entname");
				$("#panel-11").find("h2").html(entName + "的严重违法失信信息");
			}
			
			var dataMapSet = $("#dataMap").attr("p"+tagId);
			if(tagId!=0){
				if(dataMapSet==undefined||$.isEmptyObject(dataMapSet)){
					$.ajax({
						url : '../entEnt/tag.do',
						type : 'POST',
						dataType : 'json',
						async : false,//取消异步请求
						data : {
							"flag" : flag,
							"tagId" : tagId,
							"id" : id,
							"regno" : regno,
							"unifsocicrediden" : unifsocicrediden,
							"opetype" : opetype
						},
						success : function(data) {
							$("#l1").html(" ");
							$("#l2").html(" ");
							$("#l3").html(" ");
							$("#l4").html(" ");
							$("#l5").html(" ");
							$("#l6").html(" ");
							$("#l7").html(" ");
							$("#l8").html(" ");
							$("#l9").html(" ");
							$("#l11").html(" ");
							var isnullObj = $.isEmptyObject(data.data);
							if (!isnullObj) {
								getDataSetById(data, tagId, id);
								$("#dataMap").attr("p"+tagId,JSON.stringify(data.data[0].data));
							}
						},
						error:function(data){
							//alert(data.responseText);
						}
					});
			  }else{
				  tagId++;
				  if($.isEmptyObject(dataMapSet)||dataMapSet.length==2){
					$("#l"+tagId).html("暂无数据！");
				  }
				  if(tagId==9){
						$("#l9").html("暂无数据！");
					}
				  if(tagId==10){   
					$("#l11").html("暂无数据！");
				  }
			  }
		  }
		}

		function getDataSetById(data, tagId, id) {
			var data = data.data[0].data;
			var opetype = $("#opetype").attr("opetype");
			if (!$.isEmptyObject(data)) {
				if (tagId == 0) { //基本信息
					temp0(data);
				} else if (tagId == 1) { //许可经营
					temp1(data);
				} else if (tagId == 2) {//股东信息
					if(opetype=="GS"||opetype=="NZFR"||opetype=="WZGS"){
						temp2(data);					
					}else if(opetype=="HHQY"||opetype=="WZHH"){
						temp21(data);	
					}else if(opetype=="HZS"){
						temp22(data);	
					}else if(opetype=="JT"){
						temp23(data);	
					}else if(opetype=="SLYB"){
						temp24(data);	
					}
				} else if (tagId == 3) {//
					temp3(data);
				} else if (tagId == 4) {
					temp4(data, id);
				} else if (tagId == 5) {
					temp5(data);
				} else if (tagId == 6) {
					temp6(data);
				} else if (tagId == 7) {
					temp7(data);
				} else if (tagId == 8) {
					temp8(data);
				} else if (tagId == 9) {
					temp10(data);
				} else {
					var entName = $("#entName").attr("entName");
					$("#panel-10").find("h2").html(entName + "的网站信息");
					temp9(data);
				}
			}else{
				tagId++;
				if(tagId==1){
					$("#l1").html("暂无数据！");
				}
				if(tagId==2){
					$("#l2").html("暂无数据！");
				}
				if(tagId==3){
					$("#l3").html("暂无数据！");
				}
				if(tagId==4){
					$("#l4").html("暂无数据！");
				}
				if(tagId==5){
					$("#l5").html("暂无数据！");
				}
				if(tagId==6){
					$("#l6").html("暂无数据！");
				}
				if(tagId==7){
					$("#l7").html("暂无数据！");
				}
				if(tagId==8){
					$("#l8").html("暂无数据！");
				}
				if(tagId==9){
					$("#l9").html("暂无数据！");
				}
				if(tagId==10){
					$("#l11").html("暂无数据！");
				}
			}
		}
		
		
		
		function clearInfo(){
			$("#p1").empty();
			$("#p2").empty();
			$("#p3").empty();
			$("#p4").empty();
			$("#p5").empty();
			$("#p6").empty();
			$("#p7").empty();
			$("#p8").empty();
			$("#p9").empty();
			$("#p11").empty();
			
			
			$("#panel-1 h2").empty();
			$("#panel-2 h2").empty();
			$("#panel-3 h2").empty();
			$("#panel-4 h2").empty();
			$("#panel-5 h2").empty();
			$("#panel-6 h2").empty();
			$("#panel-7 h2").empty();
			$("#panel-8 h2").empty();
			$("#panel-9 h2").empty();
			$("#panel-11 h2").empty();
			
			
			
			$("#panel-1 table").empty();
			$("#panel-2 table").empty();
			$("#panel-3 table").empty();
			$("#panel-4 table").empty();
			$("#panel-5 table").empty();
			$("#panel-6 table").empty();
			$("#panel-7 table").empty();
			$("#panel-8 table").empty();
			$("#panel-9 table").empty();
			$("#panel-11 table").empty();
		}
		
		
		function openConfirmPrint(obj){
			var title =  $("#tab-content div.active h2").html();
			var content =  $("#tab-content div.active .wrap").html();
			var l=(screen.availWidth-1000)/2;
			var t=(screen.availHeight-716)/2; 
			/*$.ajax({
				url:"../entEnt/print.do",
				data:{"title":title,"content":content},
				type:'post',
				dataType:"json",
				success:function(data){
					var data  = data.data[0].data;
					window.open('printPrew.html?type=1','newWin','resizable=yes,width=1000,height=716,top='+t+',left='+l+',toolbar=yes,menubar=yes,location=yes,status=yes');
				}
			})*/
			
			
			$("body").attr("title",title);;
			$("body").attr("content",content);
			window.open('printPrew.html?type=1','newprintWin','resizable=yes,width=1000,height=716,top='+t+',left='+l+',toolbar=yes,menubar=yes,location=yes,status=yes');
		}
		
		
		function clearUserInputs(){
			$("input[type='text']").val(" ");
		}
		
		
		
		function getKeyWorkd(str){
			var flag = false;
			/*if(str.indexOf("深圳")!=-1){
				flag = true;
			}*/
			return flag;
		}
		
		
		
		
		function loadingList(data){
			getCode();
			$("#tips").removeClass("show").addClass("hide");
			$("#myForm1").removeClass("formHeight");
			
			$("#tagsSwitch").removeClass("show").addClass("hide");
			$("#list").removeClass("hide").addClass("show");
			var entNameInput = $("#entNameInput").val();
			$("#keyWorkInfo").html($.trim(entNameInput));
			var html =" ";
			html+="<tr class='listTitle'><td>企业注册号</td><td>企业名称</td><td>市场主体类型</td><td>主体记录状态</td></tr>";
			var data = data.data[0].data;
			if(data.length==1){
				$("#tips").removeClass("show").addClass("hide");
				$("#list").removeClass("show").addClass("hide");
				$("#myForm1").removeClass("formHeight");
				$("#tagsSwitch").removeClass("show").addClass("hide");
				
				var id = data[0].id;
				var entJinYinYiChang = data[0].entflag;
				var opetype = data[0].opetype;
				var entname = data[0].entname;
				var regno =  data[0].regno;
				if (isNotEmpty(id)) {
					$("#entId").attr("ent", id);
				}
				if (isNotEmpty(entJinYinYiChang)) {
					$("#entJinYinYiChang").attr("entJinYinYiChang", entJinYinYiChang);
				}
				if (isNotEmpty(entname)) {
					 $("#entName").attr("entname",entname);
				}
				
				if (isNotEmpty(opetype)) {
					 $("#opetype").attr("opetype",opetype);
				}
				
				if (isNotEmpty(regno)) {
					 $("#regno").attr("regno",regno);
				}
				var  entId = $("#entJinYinYiChang").attr("entjinyinyichang");
				var opetype = "  " ;
				if (isNotEmpty(data[0].opetype)) {
					opetype = data[0].opetype;//企业类型
				}
				var enttype = " ";
				if (isNotEmpty(data[0].enttype)) {
					enttype = data[0].enttype;//企业类型
				}

				$.ajax({
					url:"../entEnt/entStatus.do",
					type:"post",
					//data:{id:id},
					data:{id:entId},
					dataType:"json",
					async : false,//取消异步请求
					success:function(data){
						
						// 企业撤销登记标志，entStatus>0为撤销登记，entStatus<=0为正常注册企业
						var entStatus = data.data[0].data[0].entStatus; 
						// 异常名录
						var ycml = data.data[0].data[0].ycml;
						// 严重违法失信
						var yzwf = data.data[0].data[0].yzwf;
						
						/*
						 * 逻辑说明：
						 * 	  1、首先判断企业的撤销登记状态，如果是撤销登记，则向页面展示“该企业已撤销登记”，此时其他信息不再展示；
						 * 	  2、如果不是撤销登记，如果有异常信息和严重违法失信，则同时展示两条信息，否则单个展示
						 */
						if (entStatus > 0) { // 企业撤销登记
							if (opetype=="GT") {
								$("#infos").html("<div class='alert alert-danger clearfix' role='alert'><p class='pull-left'><img src='../statics/images/Tip.gif'></p><div class='pull-left'><p class='fontRed'>提示:</p><p class='fontRed'><span >该个体工商户</span>已撤销登记！</p></div></div>  ");
							} else {
								$("#infos").html("<div class='alert alert-danger clearfix' role='alert'><p class='pull-left'><img src='../statics/images/Tip.gif'></p><div class='pull-left'><p class='fontRed'>提示:</p><p class='fontRed'>&nbsp;&nbsp;&nbsp;&nbsp;该企业已撤销登记！</p></div></div>  ");
							}
						} else { // 异常信息
							if (ycml <= 0 && yzwf <= 0) {  // 无异常信息
								return;
							}
							
							if (opetype == "GT" && ycml > 0) { // 个体异常名录
								$("#infos").html("<div class='alert alert-danger clearfix' role='alert'><p class='pull-left'><img src='../statics/images/Tip.gif'></p><div class='pull-left'><p class='fontRed'>提示:</p><p class='fontRed'><span >该个体工商户</span>已被标记为经营异常状态！</p></div></div>  ");
								return;
							}
							
							if (opetype != "GT" && ycml > 0 && yzwf > 0) { // 企业 有异常名录和严重违法失信
								$("#infos").html("<div class='alert alert-danger clearfix' role='alert'><p class='pull-left'><img src='../statics/images/Tip.gif'></p><div class='pull-left'><p class='fontRed'>提示:</p><p class='fontRed'><span >该"+enttype+"</span>已被载入经营异常名录！</p>" 
										+ "<p class='fontRed'><span >该"+enttype+"</span>已被列入严重违法失信企业名单！</p></div></div>  ");
								return;
							}
							
							if (opetype != "GT" && ycml > 0) { // 企业 有异常名录
								$("#infos").html("<div class='alert alert-danger clearfix' role='alert'><p class='pull-left'><img src='../statics/images/Tip.gif'></p><div class='pull-left'><p class='fontRed'>提示:</p><p class='fontRed'><span >该"+enttype+"</span>已被载入经营异常名录！</p></div></div>  ");
								return;
							}
							
							if (opetype != "GT" && yzwf > 0) { // 企业 有严重违法失信
								$("#infos").html("<div class='alert alert-danger clearfix' role='alert'><p class='pull-left'><img src='../statics/images/Tip.gif'></p><div class='pull-left'><p class='fontRed'>提示:</p><p class='fontRed'><span >该"+enttype+"</span>已被列入严重违法失信企业名单！</p></div></div>  ");
								return;
							}
						}
						
					}
				});
				
				
				temp0(data);//默认显示基本信息页
				$('#tagDispayEnt a[href="#panel-1"]').tab('show'); //默认将基本信息显示出来
				isDispayTagByOpetype(opetype); //过滤对应企业显示的标签
				changeTagger(); //切换标签
			}else{
				$.each(data,function(i,val){
					html+="<tr><td>"+val.regno+"</td><td><a href='javascript:;' data-xydm='"+val.unifsocicrediden+"' data-id='"+val.id+"' data-name='"+val.entname+"' data-regno='"+val.regno+"' onclick='seeDetail(this)'>"+val.entname+"</td><td>"+val.enttype+"</td><td class='tdcenter'>"+val.entstatus+"</td></tr>";
				});
				$("#list table").empty().append(html);
			}
		}
		
		
		function seeDetail(obj){
			var _this = $(obj);
			var l=(screen.availWidth-1000)/2;
			var t=(screen.availHeight-716)/2; 
			var id = _this.data("id");
			var name = _this.data("name");
			var xydm = _this.data("xydm");
			name = encode(name);
			var regno = _this.data("regno");
			window.open('listDetail.html?id='+id+"&name="+name+"&regno="+regno+"&xydm="+xydm,'newWin','resizable=yes,width=1000,height=716,top='+t+',left='+l+',toolbar=yes,menubar=yes,location=yes,status=yes');
		}
		

		
//手机端需要知道是否从电脑端通过URL跳转过来的
function isContains(str, substr) {
    return str.indexOf(substr) >= 0;
}	
		
$(window).load(function(){
	/*var docFrom =document.referrer;
	
	
	if(isNotEmpty(docFrom)){
		var isFromComputer =  isContains(docFrom,'entSelect');
		if(!isFromComputer){//不是从电脑端URL过来的
			var entname =  GetQueryString("eName");
			entname = decode(entname)||'';
			var unifsocicrediden =  GetQueryString("euid");
			var isFull =  GetQueryString("ec");
			entname =$.trim(entname);
			unifsocicrediden =$.trim(unifsocicrediden);
			isFull =$.trim(isFull);
			getQueryStr(unifsocicrediden,entname,isFull);
		}
	}*/
	
	
	
	var url = window.location.search;
	   var entName = " ";
	   var str="";
	   if (url.indexOf("?") != -1) {  
		   str=  decodeURIComponent(url.split("&")[0].split("=")[1]);
	   }
	   entName = str;
	   
	   var item = GetQueryString("item");
	   var unifsocicrediden =  GetQueryString("euid");
	   var isFull =  GetQueryString("ec");
	   entName =$.trim(entName);
	   unifsocicrediden =$.trim(unifsocicrediden);
	   isFull =$.trim(isFull);
	   
	   if(item==1){
		   getQueryStr(unifsocicrediden,entName,isFull);
	   }
});


//根据不同条件掉用不同的方法
function getQueryStr(unifsocicrediden,entname,isCheck){
	if(isCheck=="1"){
		exactQuery(unifsocicrediden,entname,1)
	}else{
		uncertainQuery(unifsocicrediden,entname,1,isFull)
	}
}





//精确查询
function exactQuery(unifsocicrediden,entname,flag){
	$("#list").removeClass("show").addClass("hide");
	clearInfo(); //清空标签及内容
	$("#infos").empty(); //异常状态
	$.ajax({
		url : '../entEnt/detail.do',
		type : 'POST',
		dataType : 'json',
		async : false,//取消异步请求
		data : {
			"unifsocicrediden" : unifsocicrediden,
			"entname" : entname,
			"flag" : flag
		},
		beforeSend:function(data){
			//showMask();
			$("#submit2").text("查询中...");
			//$("#submit2").off("click");
			clearDataMap();
		},
		success : function(data) {
			if (data.data[0].data == "0") {
				alert("验证码不正确");
				clearInfo();
				$("#infos").removeClass("show").addClass("hide");
				$("#infosfunDesc").removeClass("show").addClass("hide");
				$("#infosfunDescLine").removeClass("show").addClass("hide");
				$("#printerBtn").removeClass("show").addClass("hide");
				$("#submit2").text("查询");
			} else {
				$("#infos").removeClass("hide").addClass("show");
				$("#infosfunDesc").removeClass("hide").addClass("show");
				$("#infosfunDescLine").removeClass("hide").addClass("show");
				$("#printerBtn").removeClass("hide").addClass("show");
				$("#validateCodeState").attr("data-status",flag);
				if (data.data[0].data.length != 0) {
					var id = data.data[0].data[0].id;
					var entJinYinYiChang = data.data[0].data[0].entflag;
					var error = data.data[0];
					var opetype = data.data[0].data[0].opetype;
					var entname = data.data[0].data[0].entname;
					var regno =  data.data[0].data[0].regno;
					var unifsocicrediden = data.data[0].data[0].unifsocicrediden;
					if (isNotEmpty(id)) {
						$("#entId").attr("ent", id);
					}
					if (isNotEmpty(entJinYinYiChang)) {
						$("#entJinYinYiChang").attr("entJinYinYiChang", entJinYinYiChang);
					}
					if (isNotEmpty(entname)) {
						 $("#entName").attr("entName",entname);
					}
					
					if (isNotEmpty(opetype)) {
						 $("#opetype").attr("opetype",opetype);
					}
					
					if (isNotEmpty(regno)) {
						 $("#regno").attr("regno",regno);
					}
					if (isNotEmpty(unifsocicrediden)) {
						 $("#unifsocicrediden").attr("unifsocicrediden",unifsocicrediden);
					}

					var  entId = $("#entJinYinYiChang").attr("entjinyinyichang");
					var opetype = "  " ;
					if (isNotEmpty(data[0].opetype)) {
						opetype = data[0].opetype;//企业类型
					}
					var enttype = " ";
					if (isNotEmpty(data[0].enttype)) {
						enttype = data[0].enttype;//企业类型
					}

					
					$.ajax({
						url:"../entEnt/entStatus.do",
						type:"post",
						data:{id:entId},
						dataType:"json",
						async : false,//取消异步请求
						success:function(data){
							
							// 企业撤销登记标志，entStatus>0为撤销登记，entStatus<=0为正常注册企业
							var entStatus = data.data[0].data[0].entStatus; 
							// 异常名录
							var ycml = data.data[0].data[0].ycml;
							// 严重违法失信
							var yzwf = data.data[0].data[0].yzwf;
							
							/*
							 * 逻辑说明：
							 * 	  1、首先判断企业的撤销登记状态，如果是撤销登记，则向页面展示“该企业已撤销登记”，此时其他信息不再展示；
							 * 	  2、如果不是撤销登记，如果有异常信息和严重违法失信，则同时展示两条信息，否则单个展示
							 */
							if (entStatus > 0) { // 企业撤销登记
								if (opetype=="GT") {
									$("#infos").html("<div class='alert alert-danger clearfix' role='alert'><p class='pull-left'><img src='../statics/images/Tip.gif'></p><div class='pull-left'><p class='fontRed'>提示:</p><p class='fontRed'><span >该个体工商户</span>已撤销登记！</p></div></div>  ");
								} else {
									$("#infos").html("<div class='alert alert-danger clearfix' role='alert'><p class='pull-left'><img src='../statics/images/Tip.gif'></p><div class='pull-left'><p class='fontRed'>提示:</p><p class='fontRed'>&nbsp;&nbsp;&nbsp;&nbsp;该企业已撤销登记！</p></div></div>  ");
								}
							} else { // 异常信息
								if (ycml <= 0 && yzwf <= 0) {  // 无异常信息
									return;
								}
								
								if (opetype == "GT" && ycml > 0) { // 个体异常名录
									$("#infos").html("<div class='alert alert-danger clearfix' role='alert'><p class='pull-left'><img src='../statics/images/Tip.gif'></p><div class='pull-left'><p class='fontRed'>提示:</p><p class='fontRed'><span >该个体工商户</span>已被标记为经营异常状态！</p></div></div>  ");
									return;
								}
								
								if (opetype != "GT" && ycml > 0 && yzwf > 0) { // 企业 有异常名录和严重违法失信
									$("#infos").html("<div class='alert alert-danger clearfix' role='alert'><p class='pull-left'><img src='../statics/images/Tip.gif'></p><div class='pull-left'><p class='fontRed'>提示:</p><p class='fontRed'><span >该"+enttype+"</span>已被载入经营异常名录！</p>" 
											+ "<p class='fontRed'><span >该"+enttype+"</span>已被列入严重违法失信企业名单！</p></div></div>  ");
									return;
								}
								
								if (opetype != "GT" && ycml > 0) { // 企业 有异常名录
									$("#infos").html("<div class='alert alert-danger clearfix' role='alert'><p class='pull-left'><img src='../statics/images/Tip.gif'></p><div class='pull-left'><p class='fontRed'>提示:</p><p class='fontRed'><span >该"+enttype+"</span>已被载入经营异常名录！</p></div></div>  ");
									return;
								}
								
								if (opetype != "GT" && yzwf > 0) { // 企业 有严重违法失信
									$("#infos").html("<div class='alert alert-danger clearfix' role='alert'><p class='pull-left'><img src='../statics/images/Tip.gif'></p><div class='pull-left'><p class='fontRed'>提示:</p><p class='fontRed'><span >该"+enttype+"</span>已被列入严重违法失信企业名单！</p></div></div>  ");
									return;
								}
							}
						}
					});
					getCode();//
				
					temp0(data.data[0].data);//默认显示基本信息页
					isDispayTagByOpetype(opetype); //过滤对应企业显示的标签
					changeTagger(); //切换标签
					$("#submit2").text("查询");
					
				}else{
					var entname = $('#entNameInput').val();
					alert("暂无对应"+entname+"信息");
					$("#tagsSwitch").removeClass("show").addClass("hide");
					$("#submit2").text("查询");
					getCode();//
				}
			}
		},
		error:function(data){
			$("#submit2").text("查询");
			alert(data.responseText);
		}
	});
}

//模糊查询
function uncertainQuery(unifsocicrediden,entname,flag,isFull){
	$.ajax({
		url : '../entEnt/entList.do',
		type : 'POST',
		dataType : 'json',
		beforeSend:function(){
			$("#list").addClass("hide");
			$("#submit2").text("查询中...");
			//$("#submit2").off("click");
			clearDataMap();
		},
		data:{
			"unifsocicrediden" : unifsocicrediden,
			"entname" : entname,
			"flag" : flag,
			"isFull":isFull
			},
		success:function(data){
			$("#infos").removeClass("hide").addClass("show");
			$("#infosfunDesc").removeClass("hide").addClass("show");
			$("#infosfunDescLine").removeClass("hide").addClass("show");
			$("#validateCodeState").attr("data-status",flag);
			$("#submit2").text("查询");
			/*$("#submit2").on("click",function(){
				initData();
			});*/
			loadingList(data);
		},error:function(data){
			$("#submit2").text("查询");
			alert(data.responseText);
		}
	});
}

