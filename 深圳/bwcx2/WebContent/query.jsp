<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="shortcut icon" href="statics/images/favicon.ico" type="image/x-icon"/> 
<title>不忘初心 牢记使命</title>
<link rel="stylesheet" type="text/css" href="statics/css/nav.css">
<!-- <link rel="stylesheet" type="text/css" href="http://www.sznews.com/2016gb/css/list.css"> -->
<link rel="stylesheet" type="text/css" href="statics/css/list.css">
<link rel="stylesheet" type="text/css" href="statics/iconfont/iconfont.css">

<script type="text/javascript" src="statics/js/jquery-V30.min.js"></script>
<!-- <script type="text/javascript" src="http://www.sznews.com/2016gb/js/myfocus-2.0.1.min.js"></script> -->
<!-- <script type="text/javascript" src="http://www.sznews.com/2016gb/js/login.js"></script> -->
<!-- <script type="text/javascript" src="http://www.sznews.com/test/test/js/zhugeIO.js"></script>
<script type="text/javascript" src="http://www.sznews.com/js/toMobile0506.js"></script> -->
<script type="text/javascript" src="statics/js/tzcommon.js"></script>
<script type="text/javascript" src="statics/js/jquery.pagination.js"></script>


<!--分页样式-->
<style type="text/css">
.content a{text-decoration: none;}


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
</style>


<style>
/**/
input {
    line-height: normal
}

input:focus {
    outline: 0
}

input[type=checkbox],input[type=radio] {
    box-sizing: border-box;
    padding: 0;
    vertical-align: middle
}

input[type=number]::-webkit-inner-spin-button,input[type=number]::-webkit-outer-spin-button {
    height: auto
}

input[type=search] {
    -webkit-appearance: textfield;
    -moz-box-sizing: border-box;
    -webkit-box-sizing: border-box;
    box-sizing: border-box
}

input[type=search]::-webkit-search-cancel-button,input[type=search]::-webkit-search-decoration {
    -webkit-appearance: none
}

input::-ms-clear,input::-ms-reveal {
    display: none
}

textarea,input {
    -webkit-tap-highlight-color: rgba(255,255,255,0);
    -webkit-user-modify: read-write-plaintext-only;
    outline: 0!important
}

input::-moz-placeholder,textarea::-moz-placeholder {
    color: #a6a6a6
}

input:-ms-input-placeholder,textarea:-ms-input-placeholder {
    color: #a6a6a6
}

input::-webkit-input-placeholder,textarea::-webkit-input-placeholder {
    color: #a6a6a6
}

.placeholder {
    color: #a6a6a6
}


/*搜索框*/
.mod-search {
    box-sizing: content-box;
    width: 348px;
    border: 1px solid #ccc;
    height: 48px;
    border-radius: 3px;
    position: relative
}

.mod-search:hover {
    border-color: #bbb
}

.mod-search__input {
    box-sizing: content-box;
    width: 225px;
    height: 30px;
    line-height: 30px;
    margin: 0 0 0 80px;
    padding: 10px 10px 10px 0;
    background: 0 0;
    text-indent: 12px;
    vertical-align: top;
    border: 0
}

.mod-search__btn-search {
    position: absolute;
    top: -1px;
    right: -1px;
    width: 50px;
    height: 50px;
    line-height: 50px;
    background-color: #b92a2a;
    border-radius: 0 3px 3px 0;
    text-align: center;
    color: #fff
}

.mod-search__btn-search i {
    font-size: 22px
}

.mod-search__btn-search:hover {
    background-color: #b92a2a;
	color:#fff;
}

.mod-search__btn-search:active {
    background-color: #b92a2a
}

.mod-search-dropdown {
    display: block;
    position: absolute;
    top: -1px;
    left: -1px;
    border: 1px solid #ccc;
    cursor: pointer;
    color: #333
}

.mod-search-dropdown-item {
    display: block;
    position: relative;
    width: 80px;
    height: 48px;
    line-height: 48px;
    text-indent: 10px
}

.mod-search-dropdown-item i {
    display: none;
    vertical-align: middle;
    font-size: 14px;
    text-indent: 0;
    transition: transform .3s;
    margin-left: 5px;
    *zoom:1}

.mod-search-dropdown-item-selected i {
    display: inline-block
}

.mod-search-dropdown-item:last-child {
    border-top: 1px solid #ddd;
    box-shadow: 0 2px 10px 0 rgba(0,0,0,.12)
}

.mod-search-dropdown-hover {
    background-color: #fff;
    border: 1px solid #ddd
}

.mod-search-dropdown-hover i {
    transform: rotate(180deg)
}

.icon-search {
    background-position: -90px -120px;
    width: 19px;
    height: 19px;
    display: block;
    position: absolute;
    top: 50%;
    left: 50%;
    margin-left: -10px;
    margin-top: -10px
}



.mod-search-dropdown-item:hover{background: #b92a2a;color:#fff}

.myselect{
	background: #fff;
}
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
	
	
	<div style="margin-top: 35px;">
		<div class="mod-search">
                <a class="mod-search-dropdown">
                    <span class="mod-search-dropdown-item mod-search-dropdown-item-selected" data-type="1">滚动新闻<i class="iconfont icon-xiangxiaanniu"></i></span>
                    <span class="mod-search-dropdown-item" style="display: none;" data-type="2">媒体报道<i class="iconfont icon-xiangxiaanniu"></i></span>
                    <span class="mod-search-dropdown-item" style="display: none;" data-type="3">理论学习<i class="iconfont icon-xiangxiaanniu"></i></span>
                    <span class="mod-search-dropdown-item" style="display: none;" data-type="4">方案计划<i class="iconfont icon-xiangxiaanniu"></i></span>
                    <span class="mod-search-dropdown-item" style="display: none;" data-type="5">影像资料<i class="iconfont icon-xiangxiaanniu"></i></span>
                </a>
                <input type="text" id="js_keyword" maxlength="38" class="mod-search__input" autocomplete="off" placeholder="搜索新闻标题" data-sync="1">
                <a id="js_search" href="javascript:void(0)" class="mod-search__btn-search"><i class="iconfont  icon-weibiaoti30"></i></a>
            </div>
	</div>
	
	
	
	
	
		<!-- Start:crumbs -->
		<div class="crumbs yahei">
			<!-- test start -->
			<span class="crumbs-ico"></span>
			我的位置： <a href="http://mqs" target="_blank" class="cur">首页</a>><span id="navBrumb"></span>&nbsp;
			<!-- test end -->
		</div>

		<!-- End:crumbs -->

		<!-- Start:main -->
		<div class="main cf">
			<!-- Start:left -->
			<div class="left" style="width: 1024px;height: 650px">
				<div class="c">
					 <div class="listw mt10" id="dataMore">
						

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
	    
	    var keyword = $("#js_keyword").val()||keyword_;
	    var cidQuery = $("a.mod-search-dropdown span.myselect").data("type");
		var cid = getQueryString("cid");
		
		if(cidQuery!=undefined){
			cid = cidQuery;
		}
		
		var nav = "滚动新闻";
		if(cid==1||cid==null){
			 nav = "滚动新闻查找";
		}
		else if(cid==2){
			 nav = "媒体报道查找";
		}else if(cid==3){
			nav = "理论学习查找";
		}else if(cid==4){
			nav = "方案计划查找";
		}else if(cid==5){
			nav = "影像资料查找";
		}
		$("#navBrumb").html(nav);
		
		$.ajax({
			url:"getDocsByChannelForMore.do",
			type:"get",
			//async:false,//取消异步请求
			data:{pageNo:pageIndex,pageSize:4,cid:cid,keyword:keyword},
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

$(function(){
	var cid = getQueryString("cid");
	var url = window.location.search;
	var str = "";
	if (url.indexOf("?") != -1) {
		str = decodeURIComponent(url.split("&")[1].split("=")[1]);
	}
	var	keyWord = str;
	getXZXKGSList(keyWord, "", 1, "init");
})



	
</script>
<script>
		
		
		
		
		
		
		$(".mod-search a.mod-search-dropdown").hover(function(){
			var _this = $(this);
			_this.css("background","#fbfbfb")
			_this.find("span").css("display","block");
		},function(){
			var _this = $(this);
			_this.css("background","#fff");
			_this.find("span").css("display","none");
			_this.find("span").eq(0).css("display","block");
			if(_this.find("span").hasClass("myselect")){
				_this.find("span").css("display","none");
				_this.find("span.myselect").css("display","block");
			}
		})
		
		
		
		$("a.mod-search-dropdown span").click(function(){
			var _this = $(this);
			_this.parent().find("span").css("display","none");
			_this.addClass("myselect").siblings().removeClass("myselect")
			
		})
		
		
		
		
		$("#js_search").click(function(){
			getXZXKGSList("", "", 1, "init");
		})
	
		$(document).keyup(function(event){
			  if(event.keyCode ==13){
				  getXZXKGSList("", "", 1, "init");
			  }
		});
	
	</script>

</html>