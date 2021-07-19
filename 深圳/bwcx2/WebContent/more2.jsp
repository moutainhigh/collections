<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>不忘初心 牢记使命</title>
<link rel="stylesheet" type="text/css" href="statics/css/nav.css">
<link rel="stylesheet" type="text/css" href="http://www.sznews.com/2016gb/css/list.css">
<script type="text/javascript" src="http://www.sznews.com/js/jquery/jquery-V30.min.js"></script>
<script type="text/javascript" src="http://www.sznews.com/2016gb/js/myfocus-2.0.1.min.js"></script>
<!-- <script type="text/javascript" src="http://www.sznews.com/2016gb/js/login.js"></script> -->
<script type="text/javascript" src="http://www.sznews.com/test/test/js/zhugeIO.js"></script>
<script type="text/javascript" src="http://www.sznews.com/js/toMobile0506.js"></script>
<script type="text/javascript" src="statics/js/tzcommon.js"></script>
<script type="text/javascript" src="statics/js/jquery.pagination.js"></script>
<!--分页样式-->
<style type="text/css">
.pages {
	float: right;
	font-size: 13px;
	padding: 20px 0 20px;
	text-align: center;
	margin: 20px 0;
}

.pages li {
	float: left;
	margin-right: 6px;
}

.page-previous a {
	background-color: #fff;
	border: 1px solid #e4e4e4;
	border-radius: 2px;
	padding: 7px 14px;
	width: 69px;
}

.page-next a {
	background-color: #fff;
	border: 1px solid #e4e4e4;
	border-radius: 2px;
	padding: 7px 14px;
	width: 69px;
}

.page a {
	background-color: #f5f5f5;
	border: 1px solid #d7d7d7;
	border-radius: 2px;
	color: #333;
	cursor: pointer;
	padding: 7px 14px;
	text-decoration: none;
}

.page a:hover, .page-active a {
	background-color: #ff0000;
	border: 1px solid #ccdbe4;
	color: #FFFFFF;
}
</style>


<style>
.szsclogo {
	width: 1024px;
	margin: 0 auto;
}

.szsclogo a{
 	height: 45px;
    display:block;
    line-height: 45px;
    color: #333;
    text-decoration: none;
}

.szsclogo  a:focus{outline:none;}
/* 

@media screen and (min-width:0\0) {
	.main .left{min-height: 600px;	}

}

@media only screen and (min-width: 1024px) and (max-width: 1366px) {
		.main .left{min-height: 600px;	}
}
	
	
@media only screen and (min-width: 1366px) and (max-width: 1920px)  {
		.main .left{min-height: 650px;	}
}		


@media only screen and (min-width: 1920px)   {
		.main .left{min-height: 650px;	}
}	 */
</style>
</head>


<body>
	<!-- Start:header -->
	<div class="header"  style="height: 65px; overflow: hidden; width: 100%; background: url('statics/images/banner1.jpg');margin-bottom: 7px">
		<div class="szsclogo"><a href="http://amr.sz.gov.cn/" target="_blank"><img src="statics/images/banner.jpg"></a></div>
	</div>
	<!-- End:header -->
	<!-- Start:content -->
	<div class="content">
		<!-- Start:crumbs -->
		<div class="crumbs yahei">
			<!-- test start -->
			<span class="crumbs-ico"></span>
			我的位置： <a href="http://mqs" target="_blank" class="cur">首页</a>><span id="navBrumb">滚动新闻</span>&nbsp;
			<!-- test end -->
		</div>

		<!-- End:crumbs -->

		<!-- Start:main -->
		<div class="main cf">
			<!-- Start:left -->
			<div class="left" style="width: 1024px;height:730px">
				<div class="c">
					 <div class="listw mt10" id="dataMore">
						<!-- <div class="list-pt">
							<ul class="cf">
								<li class="list-pt-li cf">
									<a href="http://www.sznews.com/news/content/2019-07/09/content_22245374.htm" target="_blank"><h3>
											2019年深圳市食品检验检测技能竞赛即将举办 <img src="">
										</h3></a> <a href="http://www.sznews.com/news/content/2019-07/09/content_22245374.htm" target="_blank"><p class="info">按照“不忘初心、牢记使命”主题教育工作部署及安排,深圳市市场监管局将于2019年7月12日-30日举办以“不忘初心、牢记使命——守护市民舌尖上的安全”为主题的2019年深圳市食品检验检测技能竞赛。</p></a>
									<div class="bot cf">
										<span class="l">
											深圳新闻网 <i class="date">2019-07-09</i>
										</span>
										<div class="r">
										</div>
									</div>
								</li>
							</ul>
						</div>
						<div class="list-pt">
							<ul class="cf">
								<li class="list-pt-li cf">
									<a href="http://www.sznews.com/news/content/2019-07/09/content_22243064.htm" target="_blank" class="list-pt-pic l"><img src="http://www.sznews.com/news/pic/2019-07/09/abfbb0d2-38fe-49b5-91d7-acaba5fd22c8.png" border="0" width="100%" height="100%"> </a> <a href="http://www.sznews.com/news/content/2019-07/09/content_22243064.htm" target="_blank"><h3>不忘初心 牢记使命|林劲民：带领班子推动社区发展向好</h3></a> <a href="http://www.sznews.com/news/content/2019-07/09/content_22243064.htm" target="_blank"><p class="info">去年8月，前海廉政监督局预防腐败处副处长林劲民被组织选派到吉厦社区挂点担任社区党委第一书记。“到了最基层，接上了地气，更懂得民情，为群众办事，虽然不易，但很值得。”</p></a>
									<div class="bot cf">
										<span class="l">
											深圳新闻网 <i class="date">2019-07-09</i>
										</span>
										<div class="r"></div>
									</div>
								</li>
							</ul>
						</div>  -->




							

						<!-- <ul pages='3' current='1' >
							<li class='page page1 page-active'>
								<a href='javascript:void(0)'>1</a>
							</li>
							<li class='page page2'>
								<a href=''>2</a>
							</li>
							<li class='page page3'>
								<a href=''>3</a>
							</li>
							<li class='page-next'>
								<a href=''>下一页</a>
							</li>
						</ul> -->

					</div>
					<!-- listw end -->

				</div>
				<!-- end c -->
				
			</div>
			<!-- End:left -->
			<div id="Pagination" class='page' style='clear:both'></div>
		</div>
		<!-- End:main -->
	</div>
	<!-- End:content -->

	<!-- Start:footer -->
	<div class="footer">
		深圳市市场监督管理局不忘初心牢记使命主题教育领导小组办公室
	</div>
	<!-- End:footer -->

</body>

<script type="text/javascript">


///根据总记录数获取总页数
var getPageCount = function (RecordCount) {
    return Math.ceil(RecordCount / 4);
}
	
var keyword ='';
var xzxkCreatePageBar = function (PageCount, type) {
    if (type == "init") {//是否初始化
        $("#Pagination").pagination(PageCount, {
            num_edge_entries: 1, //边缘页数
            callback: function (page_index, jq) {
            	getXZXKGSList($.trim($("#txtKeyWord").val()), "", page_index + 1,"");
            }
        });
    }
}

function getXZXKGSList(keyword_, type, pageIndex, getDataType) {
	    var url = location.href;
		var cid = getQueryString("cid");
		if(cid==""||cid==null){
			cid = 1; 
		}
		var nav = "滚动新闻";
		if(cid==1){
			 nav = "滚动新闻";
		}
		else if(cid==2){
			 nav = "媒体报道";
		}else if(cid==3){
			nav = "理论学习";
		}else if(cid==4){
			nav = "方案计划";
		}else if(cid==5){
			nav = "影像资料";
		}
		$("#navBrumb").html(nav);
		
		$.ajax({
			url:"getDocsByChannelForMore.do",
			type:"post",
			//async:false,//取消异步请求
			data:{pageNo:pageIndex,pageSize:4,cid:cid},
			dataType:"json",
			beforeSend:function(data){
				$("#dataMore").empty();
			},
			success:function(data){
				var _tm = "";
				var list = data.list;
				 var PageCount = getPageCount(data.count);
				 xzxkCreatePageBar(PageCount, getDataType);
				for (var i = 0; i < list.length; i++) {
					//_tm +="<li><a	href='detail.jsp?docid="+list[i].docid+"'	target='_blank'>"+list[i].title+" </a></li>";
					
					var  thumbURL = list[i].thumburl;
					if(thumbURL!=null){
						_tm +="<div class='list-pt'>";
						_tm +="<ul class='cf'>";
						_tm +="<li class='list-pt-li cf'>";
					    _tm +="<a href='detail.jsp?docid="+list[i].docid+"' target='_blank' class='list-pt-pic l'><img src='"+list[i].thumburl+"' border='0' width='100%' height='100%'> </a> <a href='detail.jsp?docid="+list[i].docid+"' target='_blank'><h3>"+list[i].title.trim()+"</h3></a> <a href='detail.jsp?docid="+list[i].docid+"' target='_blank'><p class='info'>"+list[i].contentmeta+"</p></a>";
					    _tm +="<div class='bot cf'>";
					    _tm +="<span class='l'><i class='date'>"+list[i].pubdate+"</i>";
						_tm +="</span>";
						_tm +="<div class='r'></div>";
						_tm +="</div>";
						_tm +="</li>";
						_tm +="</ul>";
						_tm +="</div>";
					}else{
						_tm +="<div class='list-pt'>";
						_tm +="<ul class='cf'>";
						_tm +="	<li class='list-pt-li cf'>";
						
						var isFile = list[i].isFile;
						if(isFile==null||isFile<1){
							_tm +="	<a href='detail.jsp?docid="+list[i].docid+"' target='_blank'><h3>";
							_tm += list[i].title +" <img src=''></h3></a> <a href='detail.jsp?docid="+list[i].docid+"' target='_blank'><p class='info'>"+list[i].contentmeta.trim()+"</p></a>";
							_tm +="	<div class='bot cf'>";
							_tm +="		<span class='l'>";
							 _tm +="<span class='l'><i class='date'>"+list[i].pubdate+"</i>";
							_tm +="		</span>";
							_tm +="	<div class='r'>";
							_tm +="	</div>";
							_tm +="	</div>";
							_tm +="	</li>";
							_tm +="</ul>";
							_tm +="</div>";
						}else{
							_tm +="	<a href='attach/"+list[i].docid+".do' target='_blank'><h3>";
							_tm += list[i].title +" <img src=''></h3></a> <a href='attach/"+list[i].docid+".do' target='_blank'><p class='info'>"+list[i].contentmeta.trim()+"</p></a>";
							_tm +="	<div class='bot cf'>";
							_tm +="		<span class='l'>";
							 _tm +="<span class='l'><i class='date'>"+list[i].pubdate+"</i>";
							_tm +="		</span>";
							_tm +="	<div class='r'>";
							_tm +="	</div>";
							_tm +="	</div>";
							_tm +="	</li>";
							_tm +="</ul>";
							_tm +="</div>";
						}
						
						
						
					}
					
					
					
					
				}
				$("#dataMore").append(_tm);
			}
			
		});
}


getXZXKGSList("", "", 1, "init");
	
</script>


</html>