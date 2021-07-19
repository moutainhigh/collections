<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="statics/iconfont/iconfont.css">
<script type="text/javascript" src="statics/js/jquery-V30.min.js"></script>

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
    position: relative;
    
}

.mod-search:hover {
    border-color: #bbb
}

.mod-search__input {
    box-sizing: content-box;
    width: 225px;
    height: 30px;
    line-height: 30px;
    margin: 0 0 0 89px;
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
    background-color: #b92a2a
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
    width: 96px;
    height: 48px;
    line-height: 48px;
    text-indent: 10px;
    z-index: 9999;
    background: #fff;
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
	
	<div style="width: 1000px;margin: 0 auto">
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
		
		
		function doQuery(){
			var keyword = $("#js_keyword").val();
		    var cidQuery = $("a.mod-search-dropdown span.myselect").data("type");
			
			if(cidQuery!=undefined){
				cid = cidQuery;
			}else{
				cid = 1;
			}
			
			
			window.open('query.jsp?cid='+cid+"&keyword="+encodeURIComponent(keyword));
		}
		
		
		$("#js_search").click(function(){
			doQuery();
		});
		
		
		
		
		$(document).keyup(function(event){
			  if(event.keyCode ==13){
				  doQuery();
			  }
		});
		
	</script>

