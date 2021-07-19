<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit" />
<meta http-equiv="X-UA-Compatible" content="IE=9,chrome=1" />
<meta http-equiv="Cache-Control" content="no-transform" />
<title>数据记录展示</title>
<style>
/* css 重置 */
*{margin:0; padding:0; list-style:none; }
body{ font:normal 12px/22px 宋体;min-height:450px;overflow-x: hidden;}
img{ border:0;  }
a{ text-decoration:none; color:#333;  }
a:hover{ color:#1974A1;  }

/*当前对应的样式的内容*/
.slideTxtBox{ border:1px solid #ddd; text-align:left;width:1000px;margin: 0 auto; margin-left:0px; }
/* .slideTxtBox{ border:1px solid #ddd; text-align:left;width: 980px;margin: 0 auto;  } */
.slideTxtBox .hd{ height:30px; line-height:30px; background:#f4f4f4; padding:0 10px 0 20px;   border-bottom:1px solid #ddd;  position:relative; }
.slideTxtBox .hd ul{ float:left; position:absolute; left:-1px; top:-1px; height:32px;   }
.slideTxtBox .hd ul li{ float:left; padding:0 8px; cursor:pointer;  }
/* .slideTxtBox .hd ul li{ float:left; padding:0 15px; cursor:pointer;  } */
.slideTxtBox .hd ul li.on{ height:30px;  background:#fff; border:1px solid #ddd; border-bottom:2px solid #fff; }


.slideTxtBox .bd{background: #fff;}
.slideTxtBox .bd ul{ padding:15px;  zoom:1;  }
.slideTxtBox .bd li{ height:24px; line-height:24px;  padding-left: 150px; }
/* .slideTxtBox .bd li span{display:inline-block;width: 200px;} */
.slideTxtBox .bd li span{display:inline-block;/* width: 136px; */width: 110px;}


/* 下面是前/后按钮代码，如果不需要删除即可 */
.slideTxtBox .arrow{  position:absolute; right:-4px; top:0; }
.slideTxtBox .arrow a{ display:block;  width:5px; height:9px; float:right; margin-right:5px; margin-top:10px;  overflow:hidden;
	 cursor:pointer; background:url("../../page/integeration/arrow.png") 0 0 no-repeat; }
.slideTxtBox .arrow .next{ background-position:0 -50px;  }
.slideTxtBox .arrow .prevStop{ background-position:-60px 0; }
.slideTxtBox .arrow .nextStop{ background-position:-60px -50px; }


.title dl{margin-left:165px;}
/* .title dl dd{float:left;width:110px;} */
.title dl dd{float:left;width:117px;}
.slideTxtBox .bd li span.region{width:293px;white-space: nowrap;}
/* .slideTxtBox .bd li span.region{width:250px;white-space: nowrap;} */
.tips{margin:0 auto;margin-left:0;width:1100px;color:red;font-size:12px;}

/*针对数据导出*/
#l11 li span.region{width:250px;white-space: nowrap;}
#l11 li span.num{text-align: center;}
</style>
<script src="../../page/integeration/jquery-1.11.3.js"></script>
<script src="../../page/integeration/jquery.SuperSlide.2.1.1.js"></script>
</head>
<body>
<div class="">
 <div id="infos" style="font-size: 20px;;font-weight: bold;padding-bottom: 10px;width: 130px;">综合查询公示</div> 
 
 <style>
   
.sliderToggle{margin:0 auto;margin-bottom:4px;width:1002px;color: #fff;font-family: "微软雅黑";display: none;}
.sliderToggle .sliderToggleList{float:left;width:254px;}
.sliderToggle .sliderToggleList h2{font-weight:400; background: #444;color: #fff;font-family: "微软雅黑";padding: 4px;}
.sliderToggle .sliderToggleList.ml{margin-left: 4px}

.sliderToggle .sliderToggleList .cwrap .cmodel{padding: 12px 0;}
.sliderToggle .sliderToggleList .cwrap .sid{font-size: 38px;margin-left: 12px;}
.sliderToggle .sliderToggleList .cwrap .name{font-size: 38px;margin-left: 16px;margin-top: 2px}
.sliderToggle .sliderToggleList .cwrap .sysHit{font-size: 16px;float: right;margin-right: 7px;}
.lines{height: 1px;width: 242px;margin-left:5px;margin-right:5px;border-bottom:1px dashed #ccc;}
.deparmt {margin-left: 53px;}
.pd{margin-bottom: 6px;}
.deparmt span{font-family: "宋体";}
.mt{padding-top: 10px;}

#app01:hover,#app02:hover{
	background:#d89a28;
}

#app03:hover,#app04:hover{
	background:#44a6de;
}
.clear{clear:both;}
 </style>

		<div class="sliderToggle">
			<div >
				<div style="display: table;margin: 0 auto">
					<div class="sliderToggleList" style="background: #E3AA42">
						<h2 class="titles" style="background: #E3AA42; text-align: center;">证件号码查询</h2>
						<div class="cwrap" id="app01">
							<div class='cmodel'>
								<span class='sid' id="a0"></span> <span class='name' id="a1"></span> <span class='sysHit' id="a2"></span>
							</div>
							<div class="deparmt pd">
								<span id="a3"></span>
							</div>
							<div class="lines"></div>
						</div>
						<div class="cwrap mt" id="app02">
							<div class='cmodel'>
								<span class='sid' id="b0"></span> <span class='name' id="b1"></span> <span class='sysHit' id="b2"></span>
							</div>
							<div class="deparmt">
								<span id="b3"></span>
							</div>
						</div>
					</div>
					<div class="sliderToggleList ml" style="background: #6fb9e3;">
						<h2 style="background: #6fb9e3;text-align: center;">联系电话查询</h2>
						<!-- <table id="phoneStrong"></table> -->
						
						<div class="cwrap" id="app03">
							<div class='cmodel'>
								<span class='sid' id="c0"></span> <span class='name' id="c1"></span> <span class='sysHit' id="c2"></span>
							</div>
							<div class="deparmt pd">
								<span id="c3"></span>
							</div>
							<div class="lines"></div>
						</div>
						<div class="cwrap mt" id="app04">
							<div class='cmodel'>
								<span class='sid' id="d0"></span> <span class='name' id="d1"></span> <span class='sysHit' id="d2"></span>
							</div>
							<div class="deparmt">
								<span id="d3"></span>
							</div>
						</div>
					</div>
					<div class="clear"></div>
				</div>
			</div>
		</div>
		
		<h2 class="tips">说明：该处公示的是综合查询前30天的使用情况。</h2>
		<div class="slideTxtBox">
			<div class="hd">
				<!-- 下面是前/后按钮代码，如果不需要删除即可 -->
				<span class="arrow"><a class="next"></a><a class="prev"></a></span>
				<ul>
					<li class="l1">快速查询</li>
					<li class="l2">商事主体综合查询</li>
					<li class="l3">重复地址查询</li>
					<li class="l4">年报查询</li>
					<li class="l5">异常名录查询</li>
					<li class="l6">证件号码查询</li>
					<li class="l7">联系电话查询</li>
					<li class="l8">总局黑名单</li>
					<li class="l9">一人责任有限公司查询</li>
					<li class="l10">失信被执行人查询</li>
					<li class="l11" style='color:red'>数据导出</li>
				</ul>
			</div>
			<div class="title">
				<dl>
					<dd style="width: :60px">序号</dd>
					<dd>用户Id</dd>
					<dd>用户姓名</dd>
					<dd style="width:250px">所属部门</dd>
					<dd id="hitsExp" >查询次数</dd>
					<dd id="ms"  style="display: none;">描述</dd>
				</dl>
			</div>
			<div class="bd">
				<ul id="l1">
				</ul>
				<ul id="l2">
				</ul>
				<ul id="l3">
				</ul>
				<ul id="l4">
				</ul>
				<ul id="l5">
				</ul>
				<ul id="l6">
				</ul>
				<ul id="l7">
				</ul>
				<ul id="l8">
				</ul>
				<ul id="l9">
				</ul>
				 <ul id="l10">
				</ul>
				 <ul id="l11">
				</ul>
				 <ul id="l12">
				</ul>
			</div>
		</div>
</div>
		<script type="text/javascript">
				var root = "/query";
				//var arr = "快速查询,商事主体综合查询,重复地址查询,总局黑名单,年报查询,一人公司查询,失信被执行人查询,失信被执行人查询,异常名录查询,投资人,主要人员信息";
				$(function(){
					$.ajax({
						url:root + "/tag.do",
						type:"post",
						dataType:"json",
						//data:{"name":arr},
						success:function(data){
							var dataStr = data.data[0].data;
							var q1 = 0;
							var q2 = 0;
							var q3 = 0;
							var q4 = 0;
							var q5 = 0;
							var q6 = 0;
							var q7 = 0;
							var q8 = 0;
							var q9 = 0;
							var q10 = 0;
							var q11 = 0;
						
							for (var i = 0; i < dataStr.length; i++) {
								if(dataStr[i].operationDescribe == "快速查询"){
									q1++;
									var html = " ";
									var userId = " ";
									var userName = " ";
									var userDepartName = " ";
									var hits = " ";
									if(isNotEmpty(dataStr[i].userId)){
										userId = dataStr[i].userId;
									}
									if(isNotEmpty(dataStr[i].userName)){
										userName = dataStr[i].userName;
									}
									if(isNotEmpty(dataStr[i].name)){
										userDepartName = dataStr[i].name;
									}
									if(isNotEmpty(dataStr[i].hits)){
										hits = dataStr[i].hits;
									}
									html ="<li><span style='width:'60px'>"+(q1)+"</span><span class='num'>"+userId+"</span><span>"+userName+"</span><span class='region'>"+userDepartName+"</span><span class='num'>"+hits+"</span></li>";
									$("#l1").append(html);
								}
								if(dataStr[i].operationDescribe == "商事主体查询"){
									q2++;
									var html = " ";
									var userId = " ";
									var userName = " ";
									var userDepartName = " ";
									var hits = " ";
									if(isNotEmpty(dataStr[i].userId)){
										userId = dataStr[i].userId;
									}
									if(isNotEmpty(dataStr[i].userName)){
										userName = dataStr[i].userName;
									}
									if(isNotEmpty(dataStr[i].name)){
										userDepartName = dataStr[i].name;
									}
									if(isNotEmpty(dataStr[i].hits)){
										hits = dataStr[i].hits;
									}
									html ="<li><span style='width:'60px'>"+(q2)+"</span><span class='num'>"+userId+"</span><span>"+userName+"</span><span class='region'>"+userDepartName+"</span><span class='num'>"+hits+"</span></li>";
									$("#l2").append(html);
								}
								if(dataStr[i].operationDescribe == "重复地址查询"){
									q3++;
									var html = " ";
									var userId = " ";
									var userName = " ";
									var userDepartName = " ";
									var hits = " ";
									if(isNotEmpty(dataStr[i].userId)){
										userId = dataStr[i].userId;
									}
									if(isNotEmpty(dataStr[i].userName)){
										userName = dataStr[i].userName;
									}
									if(isNotEmpty(dataStr[i].name)){
										userDepartName = dataStr[i].name;
									}
									if(isNotEmpty(dataStr[i].hits)){
										hits = dataStr[i].hits;
									}
									html ="<li><span style='width:'60px'>"+(q3)+"</span><span class='num'>"+userId+"</span><span>"+userName+"</span><span class='region'>"+userDepartName+"</span><span class='num'>"+hits+"</span></li>";
									$("#l3").append(html);
								}
								if(dataStr[i].operationDescribe == "年报查询"){
									q4++;
									var html = " ";
									var userId = " ";
									var userName = " ";
									var userDepartName = " ";
									var hits = " ";
									if(isNotEmpty(dataStr[i].userId)){
										userId = dataStr[i].userId;
									}
									if(isNotEmpty(dataStr[i].userName)){
										userName = dataStr[i].userName;
									}
									if(isNotEmpty(dataStr[i].name)){
										userDepartName = dataStr[i].name;
									}
									if(isNotEmpty(dataStr[i].hits)){
										hits = dataStr[i].hits;
									}
									html ="<li><span style='width:'60px'>"+(q4)+"</span><span class='num'>"+userId+"</span><span>"+userName+"</span><span class='region'>"+userDepartName+"</span><span class='num'>"+hits+"</span></li>";
									$("#l4").append(html);
								}
								
								if(dataStr[i].operationDescribe == "异常名录查询"){
									q5++;
									var html = " ";
									var userId = " ";
									var userName = " ";
									var userDepartName = " ";
									var hits = " ";
									if(isNotEmpty(dataStr[i].userId)){
										userId = dataStr[i].userId;
									}
									if(isNotEmpty(dataStr[i].userName)){
										userName = dataStr[i].userName;
									}
									if(isNotEmpty(dataStr[i].name)){
										userDepartName = dataStr[i].name;
									}
									if(isNotEmpty(dataStr[i].hits)){
										hits = dataStr[i].hits;
									}
									html ="<li><span style='width:'60px'>"+(q5)+"</span><span class='num'>"+userId+"</span><span>"+userName+"</span><span class='region'>"+userDepartName+"</span><span class='num'>"+hits+"</span></li>";
									$("#l5").append(html);
								}
								if(dataStr[i].operationDescribe.indexOf("证照")>0){
									q6++;
									var html = " ";
									var userId = " ";
									var userName = " ";
									var userDepartName = " ";
									var hits = " ";
									if(isNotEmpty(dataStr[i].userId)){
										userId = dataStr[i].userId;
									}
									if(isNotEmpty(dataStr[i].userName)){
										userName = dataStr[i].userName;
									}
									if(isNotEmpty(dataStr[i].name)){
										userDepartName = dataStr[i].name;
									}
									if(isNotEmpty(dataStr[i].hits)){
										hits = dataStr[i].hits;
									}
									html ="<li><span style='width:'60px'>"+(q6)+"</span><span class='num uid'>"+userId+"</span><span>"+userName+"</span><span class='region'>"+userDepartName+"</span><span class='num'>"+hits+"</span></li>";
									$("#l6").append(html);
								}
								 if(dataStr[i].operationDescribe.indexOf("电话")>0){
										 q7++;
										var html = " ";
										var userId = " ";
										var userName = " ";
										var userDepartName = " ";
										var hits = " ";
										if(isNotEmpty(dataStr[i].userId)){
											userId = dataStr[i].userId;
										}
										if(isNotEmpty(dataStr[i].userName)){
											userName = dataStr[i].userName;
										}
										if(isNotEmpty(dataStr[i].name)){
											userDepartName = dataStr[i].name;
										}
										if(isNotEmpty(dataStr[i].hits)){
											hits = dataStr[i].hits;
										}
										html ="<li><span style='width:'60px'>"+(q7)+"</span><span class='num uid'>"+userId+"</span><span>"+userName+"</span><span class='region'>"+userDepartName+"</span><span class='num'>"+hits+"</span></li>";
										$("#l7").append(html);
								}
								
								if(dataStr[i].operationDescribe == "黑牌企业查询"){
									q8++;
									var html = " ";
									var userId = " ";
									var userName = " ";
									var userDepartName = " ";
									var hits = " ";
									if(isNotEmpty(dataStr[i].userId)){
										userId = dataStr[i].userId;
									}
									if(isNotEmpty(dataStr[i].userName)){
										userName = dataStr[i].userName;
									}
									if(isNotEmpty(dataStr[i].name)){
										userDepartName = dataStr[i].name;
									}
									if(isNotEmpty(dataStr[i].hits)){
										hits = dataStr[i].hits;
									}
									html ="<li><span style='width:'60px'>"+(q8)+"</span><span class='num'>"+userId+"</span><span>"+userName+"</span><span class='region'>"+userDepartName+"</span><span class='num'>"+hits+"</span></li>";
									$("#l8").append(html);
								}
								
								if(dataStr[i].operationDescribe == "一人责任有限公司查询"){
									q9++;
									var html = " ";
									var userId = " ";
									var userName = " ";
									var userDepartName = " ";
									var hits = " ";
									if(isNotEmpty(dataStr[i].userId)){
										userId = dataStr[i].userId;
									}
									if(isNotEmpty(dataStr[i].userName)){
										userName = dataStr[i].userName;
									}
									if(isNotEmpty(dataStr[i].name)){
										userDepartName = dataStr[i].name;
									}
									if(isNotEmpty(dataStr[i].hits)){
										hits = dataStr[i].hits;
									}
									html ="<li><span style='width:'60px'>"+(q9)+"</span><span class='num'>"+userId+"</span><span>"+userName+"</span><span class='region'>"+userDepartName+"</span><span class='num'>"+hits+"</span></li>";
									$("#l9").append(html);
								}
								if(dataStr[i].operationDescribe == "失信被执行人查询"){
									q10++;
									var html = " ";
									var userId = " ";
									var userName = " ";
									var userDepartName = " ";
									var hits = " ";
									if(isNotEmpty(dataStr[i].userId)){
										userId = dataStr[i].userId;
									}
									if(isNotEmpty(dataStr[i].userName)){
										userName = dataStr[i].userName;
									}
									if(isNotEmpty(dataStr[i].name)){
										userDepartName = dataStr[i].name;
									}
									if(isNotEmpty(dataStr[i].hits)){
										hits = dataStr[i].hits;
									}
									html ="<li><span style='width:'60px'>"+(q10)+"</span><span class='num'>"+userId+"</span><span>"+userName+"</span><span class='region'>"+userDepartName+"</span><span class='num'>"+hits+"</span></li>";
									$("#l10").append(html);
								}
								if(dataStr[i].operationDescribe.indexOf("导出")>0){
									q11++;
									var html = " ";
									var userId = " ";
									var userName = " ";
									var userDepartName = " ";
									var hits = " ";
									var description = " ";
									if(isNotEmpty(dataStr[i].userId)){
										userId = dataStr[i].userId;
									}
									if(isNotEmpty(dataStr[i].userName)){
										userName = dataStr[i].userName;
									}
									if(isNotEmpty(dataStr[i].name)){
										userDepartName = dataStr[i].name;
									}
									if(isNotEmpty(dataStr[i].hits)){
										hits = dataStr[i].hits;
									}
									if(isNotEmpty(dataStr[i].operationDescribe)){
										description = dataStr[i].operationDescribe;
									}
									html ="<li><span style='width:'60px'>"+(q11)+"</span><span class='num'>"+userId+"</span><span>"+userName+"</span><span class='region'>"+userDepartName+"</span><span class='num'>"+hits+"</span><span class='num'>"+description+"</span></li>";
									$("#l11").append(html);
								}
							}
							
							//var sid1 = $("#l6 li").eq(0).find("span").eq(0).html();
							var name1 = $("#l6 li").eq(0).find("span").eq(2).html();
							var deparm1 = $("#l6 li").eq(0).find("span").eq(3).html();
							if(deparm1.length>17){
								//deparm1 = deparm1.slice(0,10)+'...';
								deparm1 = deparm1.substring(0,10)+"...";
							}
							
							var hits1 = $("#l6 li").eq(0).find("span").eq(4).html();
							$("#a0").html("1");
							$("#a1").html(name1);
							$("#a2").text(hits1+"次");
							if(isEmpty(deparm1)){
								deparm1="&nbsp;";
							}
							$("#a3").html(deparm1);
							
							
							//var sid2 = $("#l6 li").eq(1).find("span").eq(0).html();
							var name2 = $("#l6 li").eq(1).find("span").eq(2).html();
							var deparm2 = $("#l6 li").eq(1).find("span").eq(3).html();
							if(deparm2.length>17){
								//deparm2 = deparm2.slice(0,10)+'...';
								deparm2 = deparm2.substring(0,10)+"...";
							}
							var hits2 = $("#l6 li").eq(1).find("span").eq(4).html();
							$("#b0").html("2");
							
							$("#b1").html(name2);
							$("#b2").text(hits2+"次");
							if(isEmpty(deparm2)){
								deparm2="&nbsp;";
							}
							$("#b3").html(deparm2);
							
							
							//var sid3 = $("#l7 li").eq(0).find("span").eq(0).html();
							var name3 = $("#l7 li").eq(0).find("span").eq(2).html();
							var deparm3 = $("#l7 li").eq(0).find("span").eq(3).html();
							if(deparm3.length>17){
								//deparm3 = deparm3.slice(0,10)+'...';
								deparm3 = deparm3.substring(0,10)+"...";
							}
							var hits3 = $("#l7 li").eq(0).find("span").eq(4).html();
							$("#c0").html("1");
							$("#c1").html(name3);
							$("#c2").text(hits3+"次");
							if(isEmpty(deparm3)){
								deparm3="&nbsp;";
							}
							$("#c3").html(deparm3);
							
							
							
							//var sid4 = $("#l7 li").eq(1).find("span").eq(0).html();
							var name4 = $("#l7 li").eq(1).find("span").eq(2).html();
							var deparm4 = $("#l7 li").eq(1).find("span").eq(3).html();
							if(deparm4.length>17){
								//deparm4 = deparm4.slice(0,10)+'...';
								deparm4 = deparm4.substring(0,10)+"...";
							}
							var hits4 = $("#l7 li").eq(1).find("span").eq(4).html();
							$("#d0").html("2");
							$("#d1").html(name4);
							$("#d2").text(hits4+"次");
							if(isEmpty(deparm4)){
								deparm4="&nbsp;";
							}
							$("#d3").html(deparm4);
							
							$(".sliderToggle").slideDown();
						}
					});
				});
				
		
			$(".slideTxtBox").slide({effect:"leftLoop",autoPlay:true,easing:"easeOutCirc",delayTime:500});
			
			setInterval(function(){
				var index = $(".on").index();
				if(index==10){
					$("#hitsExp").html("导出次数");
					$("#ms").css("display","block");
				}else{
					$("#hitsExp").html("查询次数");
					//$("#hitsExp").("查询次数");
					$("#ms").css("display","none");
				}
			}, 10);
			
			
			
			getMarginPost();
			
			$(window).resize(function(){
				getMarginPost();
			})
			
			
			function getMarginPost(){
				var sw = screen.width;
				var ww = $(window).width();
				var cal = 0;
				if(ww<sw){
					cal = ww
				}else{
					cal = sw;
				}
				var ml = cal-1000;
				var posl = ml/2;
				if(posl>200){
					$(".slideTxtBox").css("margin-left",posl-100);
					$(".tips").css("margin-left",posl-100);
				}
			}
			
			
			
			
			function isEmpty(val) {
				val = $.trim(val);
				if (val == null)
					return true;
				if (val == undefined || val == 'undefined')
					return true;
				if (val == "")
					return true;
				if (val.length == 0)
					return true;
				if (!/[^(^\s*)|(\s*$)]/.test(val))
					return true;
				return false;
			}

			function isNotEmpty(val) {
				return !isEmpty(val);
			}
			
			
			
			$(window).load(function(){
				 var sc = window.screen.width;
				
				 /*if(sc>1440){
					$(".sliderToggle").css("margin-left","253px");
				} */
				
				var windowWidth = $(window.top).width();
				var infos = $("#infos").width();
				var marginLeft = (windowWidth - infos)/2;
				 var sc = window.screen.width;
				 if(sc>1400){
					 $("#infos").css("margin-left",marginLeft-200);
				 }else{
					$("#infos").css("margin-left",marginLeft-180);
				 }
				
				
				
				var ml = $(".slideTxtBox").css("margin-left");
				$(".sliderToggle").css("margin-left",ml);
			});
			
			$(window).resize(function(){
				var ml = $(".slideTxtBox").css("margin-left");
				$(".sliderToggle").css("margin-left",ml);
			});
			
			
			
			$(function(){
			  $(".toggleTitle .chose").click(function(){
				  var _this = $(this);
				  _this.addClass("on").siblings().removeClass("on");
				  var _index = _this.index();
				  $(".toggleItems ul").eq(_index).removeClass("noneShow").siblings().addClass("noneShow");
			  });
			  
			  
			  $(".closeBtn").click(function(){
				  $(".sliderToggle").slideUp();
			  });
			  
			  
			});
			
		</script>
</body>
</html>