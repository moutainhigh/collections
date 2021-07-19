<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="include/error_scm.jsp"%>
<%@ include file="include/index_check.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>微博管理-首页</title>
		<link rel="stylesheet" href="index.css">
		<!-- 引入JQuery的js文件 -->
		<script src="js/jquery-1.7.2.min.js"></script>
		<script src="js/scm_common.js"></script>
		<!-- 引入crashboard的组件文件 -->
		<script src="js/jquery.ui.crashboard.js"></script>
		<!-- 引入crashboard的样式文件 -->
		<link href="css/jquery.ui.crashboard.css" rel="stylesheet" type="text/css" />
		<script src="index.js"></script>
		<script>
			<!--
			//弹出创建微博页面
			 function show(){
				var CrashBoarderInfo = {
					id : 'createMicroblog', //id
					title : '创建微博',//弹出窗口名称
					maskable : true, //是否绘制遮布
					draggable : true,//是否可以拖动
					url : 'create_microblog.jsp',//弹出框显示的内容的页面地址
					width : '574px',//宽度
					height : '290px',//高度
					params : {},//传给弹出框页面的参数(需要获取组id)
					callback : function(params){//回调函数
						$.ajax({
							type:"post", 
							data:{AccountIds:params._AccountIds, SCMGroupId:params._SCMGroupId, Content:params._MicroContent, Picture:params._Picture,Source:params._Source},
							dataType:"text",
							url:"add_microcontent_dowith.jsp",
							success:function(data){
								if($.trim(data)==1){
									if(params._hasWorkFlow == '1'){
										alert("微博已提交至审核人员，请您耐心等待审核结果。");
									}else{
										alert("发布成功！");
									}
								}else{
									alert("发布失败！失败原因是："+$.trim(data));
								}
								//兼容浏览器返回值（IE8 返回#fff，其他返回rgb(255, 255, 255))
								var isTran1 = "#fff";
								var isTran2 = "rgb(255, 255, 255)";
								var li1Back = $("#li1").css("background-color");
								var li2Back = $("#li2").css("background-color");

								if(isTran1.toLowerCase() == li1Back.toLowerCase() || isTran2.toLowerCase() == li1Back.toLowerCase()){
									$("#frame_content").attr("src","microcontent/single_microblog_list.jsp");
								}else if(isTran1.toLowerCase() == li2Back.toLowerCase() || isTran2.toLowerCase() == li2Back.toLowerCase()){
									$("#frame_content").attr("src","microcontent/attention_microblog_list.jsp");
								}
							},
							error:function(){"与服务器通信失败！"}
						});
					}
				};
				CrashBoard.show(CrashBoarderInfo);//弹出crashBoard框
			}
			//-->
		</script>
	</head>
	<body class="bodyBackground">
	<!--加载图片-->
	<img src="images/small.cur" style="display:none" />
		<script>
		<%if(!CMyString.isEmpty(outSysetmMsg)){%>
			alert("<%=outSysetmMsg%>");
			closeBrowser();
		<%}%>
		</script>
		<div id="header"><!--头部开始-->
			<div id="headerContent">
				<div id="headerRight">
					<div class="logout">
						<a href="../logout.jsp" class="linkFont" tabindex="-1">退出</a>
					</div>
					<div class="line">|</div>
					<div id="shortMessageBox" class="showShortMessage" onmouseover="showOnlyShortMessage()" onmouseout="hiddenShortMessage()">
						<div class="linkFont" onmouseover="showShortMessage()">消息<img src="images/down_icon.png" border="0" class="paddingLeft3Px" /></div>
						<div id="shortMessage" class="showNewMessage">
						</div>
					</div>
					<div class="welcomeInfo">
						<span class="linkFont" onclick="nameClick()"><%=loginUser.getName()%></span>&nbsp;您好！今天是<%=CMyString.transDisplay(showToday)%> &nbsp;
					</div>
				</div>
			</div>
			<div class="clearFloat"></div>
		</div><!--头部结束-->
		<div id="content"><!--内容部分开始-->
			<div id="contentLeft">
				<div id="createMessage">
					<a href="javascript:void(0)" onclick="show()" class="thickbox" title="创建微博">
						<div id="createMessagePic"></div>
					</a>
				</div>
				<div id="navContent">
					<!--官微管理-->
					<div class="slide">
						<span><img id="prefix1" src="images/icon.png" /></span>
						<span><img id="icon1" src="images/icon1.png" /></span>
						<a href="javascript:void(0)" class="btn-slide1" >
							官微管理
						</a>
					</div>
					<div id="panel1">
						<ul>
							<li id="li1">
								<a href="microcontent/single_microblog_list.jsp" target="frame_content" onclick="changeState('#li1');" >
									官微内容
					 			</a>
							</li>
							<li id="li2">
								<a href="microcontent/attention_microblog_list.jsp" target="frame_content" onclick="changeState('#li2')" >
									关注的微博
								</a>
							</li>
							<li id="li3">
								<a href="microcontent/show_comment_list.jsp" onclick="changeState('#li3')" target="frame_content" >
									评论
								</a>
							</li>
							<li id="li4">
								<a href="microcontent/show_favorites_list.jsp" onclick="changeState('#li4')" target="frame_content" >
									收藏
								</a>
							</li>
							<li id="li5">
								<a href="microcontent/show_at_list.jsp" onclick="changeState('#li5')" target="frame_content" >
									@我
								</a>
							</li>
						</ul>
					</div>
					<!--运营状况-->
					<div class="slide">
						<span><img id="prefix2" src="images/icon.png" /></span>
						<span><img id="icon2" src="images/icon2.png" /></span>
						<a href="javascript:void(0)" class="btn-slide2" >运营状况</a>
					</div>
					<div id="panel2">
						<ul>
							<li id="li6">
							<%
							// 获取的时间
							CMyDateTime now = CMyDateTime.now();
							int nStatYear = now.getYear();
							%>
								<a href="stat/mc_count_stat.jsp?StatYear=<%=nStatYear%>" onclick="changeState('#li6')" target="frame_content" >
									微博总数
								</a>
							</li>
							<li id="li7">
								<a href="stat/mc_original_per_stat.jsp?StatYear=<%=nStatYear%>" onclick="changeState('#li7')" target="frame_content" >
									原创率
								</a>
							</li>
							<li id="li8">
								<a href="stat/mc_transmit_count_stat.jsp?StatYear=<%=nStatYear%>" onclick="changeState('#li8')" target="frame_content" >
									被转发数
								</a>
							</li>
							<li id="li9">
								<a href="stat/mc_comment_count_stat.jsp?StatYear=<%=nStatYear%>" onclick="changeState('#li9')" target="frame_content" >
									被评论数
								</a>
							</li>
							<li id="li10">
								<a href="stat/mc_transmit_avg_stat.jsp?StatYear=<%=nStatYear%>" onclick="changeState('#li10')" target="frame_content" >
									被转发率
								</a>
							</li>
							<li id="li11">
								<a href="stat/mc_comment_avg_stat.jsp?StatYear=<%=nStatYear%>" onclick="changeState('#li11')" target="frame_content" >
									被评论率
								</a>
							</li>
							<li id="li12">
								<a href="stat/mc_follow_count_stat.jsp?StatYear=<%=nStatYear%>" onclick="changeState('#li12')" target="frame_content" >
									粉丝数
								</a>
							</li>
						</ul>
					</div>
					<!--关系管理-->
					<div class="slide" style="display:none">
						<span><img id="prefix3" src="images/icon.png" /></span>
						<span><img id="icon3" src="images/icon3.png" /></span>
						<a href="javascript:void(0)" class="btn-slide3" >关系管理</a>
					</div>
					<div id="panel3">
						<ul>
							<li id="li13">
								<a href="javascript:void(0)" onclick="changeState('#li13');return false;" target="frame_content" >
									关注关系
								</a>
							</li>
							<li id="li14">
								<a href="javascript:void(0)" onclick="changeState('#li14');return false;" >
									用户搜索
								</a>
							</li>
						</ul>
					</div>	
					<!--帐号管理-->
					<div class="slide">
						<span><img id="prefix4" src="images/icon.png" /></span>
						<span><img id="icon4" src="images/icon4.png" /></span>
						<a href="javascript:void(0)" class="btn-slide4" >帐号管理</a>
					</div>
					<div id="panel4" <%if(!SCMAuthServer.isAdminOfSCM(loginUser)){%>style="height:32px;"<%}%>>
						<ul>
							<li id="li15">
								<a href="account/all_accounts_list.jsp" onclick="changeState('#li15')" target="frame_content" >
									微博帐号管理
								</a>
							</li>
							<li id="li16" <%if(!SCMAuthServer.isAdminOfSCM(loginUser)){%>style="display:none;"<%}%>>
								<a href="account/rights_management.jsp" onclick="changeState('#li16')" target="frame_content" >
									权限管理
								</a>
							</li>
						</ul>
					</div>
					<!--舆情管理-->
					<div class="slide" style="display:none">
						<span><img  id="prefix5" src="images/icon.png" /></span>
						<span><img id="icon5" src="images/icon5.png" /></span>
						<a href="javascript:void(0)" class="btn-slide5" >舆情管理</a>
					</div>
					<div id="panel5">
						<ul>
							<li id="li17">
								<a href="javascript:void(0)" onclick="changeState('#li17');return false;" >
									待添加
								</a>
							</li>
							<li id="li18">
								<a href="javascript:void(0)" onclick="changeState('#li18');return false;" >
									待添加
								</a>
							</li>
						</ul>
					</div>

					<!--审核管理-->
					<div class="slide">
						<span><img  id="prefix6" src="images/icon.png" /></span>
						<span><img id="icon6" src="images/icon6.png" /></span>
						<a href="javascript:void(0)" class="btn-slide6" >审核管理</a>
					</div>
					<div id="panel6">
						<ul>
							<li id="li19" <%if(!SCMAuthServer.isAdminOfSCM(loginUser)){%>style="display:none;"<%}%>>
								<a href="audit/configure_workflow.jsp" onclick="changeState('#li19');" target="frame_content" >
									工作流配置
								</a>
							</li>
							
							<li id="li20">
								<a href="audit/microblog_checking_list.jsp" onclick="changeState('#li20');" target="frame_content" >
									微博审核
								</a>
							</li>
						</ul>
					</div>

				</div>
			</div>
			<div id="contentRight">
				<div id="autoMessage" class="autoMessage">
				</div>
				<!--需要获取groupid的值-->
				<iframe id="frame_content" name="frame_content" scrolling="no" frameborder="0" marginwidth="0" marginheight="0"></iframe>
			</div> 
		</div><!--内容部分结束-->
		<div id="contentBottom"></div>
		<div id="footer"><!--尾部开始-->
			2012©北京拓尔思信息技术股份有限公司 版权所有
		</div><!--尾部结束-->
		<script>
			$(function(){
				var urlAddress = "<%=CMyString.filterForJs(sIFrameSrc)%>";
				var flagToPage = <%=nFlagToPage%>;
				if(flagToPage == 1){
					$(".btn-slide1").click();
					$("#li1 a").click();
				}else if(flagToPage == 2){
					$(".btn-slide4").click();
					$("#li15 a").click();
				}else if(flagToPage == 5){
					$(".btn-slide4").click();
					$("#li15 a").click();
				}else if(flagToPage == 3){
					$(".btn-slide4").click();
					$("#li16 a").click();
				}else{
					$(".btn-slide6").click();
					$("#li20 a").click();
				}
				$("#frame_content").attr("src",urlAddress);
			});
			showAutoMessage();
		</script>

	</body>
</html>