<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<title>检索信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script
	src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js"
	type="text/javascript"></script>

<style type="text/css">
body { .p { PADDING-LEFT:18px;
	FONT-SIZE: 14px;
	WORD-SPACING: 4px
}

}
.pagination {
	background: #eee;
	margin: 0px;
	margin-left: 0px;
	padding-top: 0px;
	padding-bottom: 0px;
	text-align: center;
	border: 1;
	font-size: 12px;
}

.sous_list {
	width: 70%;
	border-bottom: 1px dashed #CCCCCC;
	margin-left: 185px;
	margin-top: 0px;
	padding-bottom: 0px;
	margin-right: 100px;
}

.sous_list h3 a {
	font-size: 16px;
	color: #21419E;
	line-height: 30px;
	text-decoration: underline;
}

.sous_list h3 a span {
	color: #F00;
}

.sous_list p {
	font-size: 12px;
	line-height: 24px;
	width: 100%;
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
}

.page {
	padding: 100px 0;
	zoom: 1
}

.page:after {
	content: "";
	display: block;
	height: 0;
	clear: both;
	visibility: hidden;
}

.page span {
	position: relative;
	margin: 0 2px;
	border: solid 1px #ccc;
	float: left;
}

.page span a {
	padding: 2px 5px;
	cursor: pointer;
}

.page span i {
	display: block;
	height: 10px;
	width: 10px;
	background: #ccc;
	border-radius: 50%;
	position: absolute;
	left: 50%;
	top: -20px;
	margin-left: -5px;
}

.page span i.active_i {
	background: red;
}
</style>

<script>
	//点击搜索加条件      到后台将值检索存到域里
	//C标签创建从域里取值。点击链接ajax传参数到后台。搜索条件保存起来。

	//$("#panel_1").append("<div style='border: 1px solid red;'><h2>面</h2><P> 这是一个简单的手风琴组件</P></div>");
	function returnOrgNo(orgno){
		var pages;
		var listbegin;
		var listend;
		var pagescount;
		var pagedata;
		$.ajax({
			url : '/gdsjzx/pageItems/pageCount.do?pages='+orgno,
			async: false,
			data : {
				orgno : orgno
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(data) {
				pages = data.data.pageNo;
				//alert(pages);
				temp=pages;
				listbegin = data.listbegin;
				listend = data.listend;
				pagescount = data.data.totalPageCount;
				pagedata=data.data.items;	
			//	alert(data.data.items);			
			},
			error : function() {
			}
			});
			
		if($(".page")!=undefined){		
			$(".page").remove(); 
			$("<div class='page'></div>").appendTo($(".p"));	
		}	
		
		if (pages > 1) {
				//alert('ppp')
					 	$("<span><a onclick='returnOrgNo("+ (pages - 1) +")'>上一页</a></span>").appendTo($('.page'));
					 }	
			 for (var i = listbegin; i <= listend; i++) {
                    if (i != pages) {
                        $("<span><a onclick='returnOrgNo(" + i + ")'>" + i + "</a><i></i></span>").appendTo($('.page'));
                    } else {
                        $("<span><a onclick='returnOrgNo(" + i + ")'>" + i + "</a><i class='active_i'></i></span>").appendTo($('.page'));
                    }
                }
                 if (pages != pagescount) {
                    $("<span><a onclick='returnOrgNo("+ (pages + 1) +")'>下一页</a></span> ").appendTo($('.page'));
                };
	};
	
	$(document).ready(function(){
		returnOrgNo("1");
	});
</script>
</head>
<body>
	<div class="p">
	</div>


	<div class="pagination">
		﻿ 总记录数:&nbsp;859155 &nbsp;&nbsp; 消耗时间:&nbsp;32 ms 当前为第1页 共85916页 <a
			class="next-page" href="/solr/collection1/browse?&q=&start=10">下一页</a>
	</div>




	<div class="results">

		<div class="sous_list">
			﻿
			<div>
				<h3>
					<a href="javascript:void(0);"
						onclick="trims('[440103001091998072000335]');"> 广州市荔湾区少年色彩服装店
					</a><label title="      注销            " class="      个体工商户            "><a
						href="javascript:void(0);"
						onclick="opencp('[440103001091998072000335]');"
						style="font-size: 14px; color: green;">企业族谱查看</a></label>
				</h3>
				<p>
					<b>成立日期:</b> 1998-07-20 &nbsp;&nbsp;<b>地址:</b> 德星路9号荔湾广场FC56档
				</p>
				<p>
					<b>企业类型:</b> 个体工商户 <label class="mysapn"
						title="      个体工商户            "></label>&nbsp;&nbsp;<b>行业:</b>
					批发和零售业 &nbsp;&nbsp;<b>登记机关:</b> 广州市工商行政管理局荔湾分局十甫所 &nbsp;&nbsp;<b><label
						class="mysapn1" title="      注销            "></label>企业状态:</b> 注销
				</p>
				<p>
					<b>法定代表人(负责人):</b> 林海峰 &nbsp;&nbsp;<b>曾用名:</b> &nbsp;&nbsp;<label
						class="      个体工商户            "><b>出资人:</b> </label>
				</p>
				<p>
					<b>经营范围:</b> 零售: 服装, 工艺品
				</p>
			</div>
		</div>




		<div class="sous_list">
			﻿
			<div>
				<h3>
					<a href="javascript:void(0);"
						onclick="trims('[44010300109199303020007X]');"> 广州市荔湾区阿德装璜店 </a><label
						title="      注销            " class="      个体工商户            "><a
						href="javascript:void(0);"
						onclick="opencp('[44010300109199303020007X]');"
						style="font-size: 14px; color: green;">企业族谱查看</a></label>
				</h3>
				<p>
					<b>成立日期:</b> 1993-03-02 &nbsp;&nbsp;<b>地址:</b> 长寿西路兴华大街30号内
				</p>
				<p>
					<b>企业类型:</b> 个体工商户 <label class="mysapn"
						title="      个体工商户            "></label>&nbsp;&nbsp;<b>行业:</b>
					批发和零售业 &nbsp;&nbsp;<b>登记机关:</b> 广州市工商行政管理局荔湾分局十甫所 &nbsp;&nbsp;<b><label
						class="mysapn1" title="      注销            "></label>企业状态:</b> 注销
				</p>
				<p>
					<b>法定代表人(负责人):</b> 区炎佳 &nbsp;&nbsp;<b>曾用名:</b> &nbsp;&nbsp;<label
						class="      个体工商户            "><b>出资人:</b> </label>
				</p>
				<p>
					<b>经营范围:</b> 零售: 玉器, 首饰, 工艺品, 装饰材料
				</p>
			</div>
		</div>

		<div class="sous_list">
			<div>
				<h3>
					<a href="javascript:void(0);"
						onclick="opencp('[440100000201208271030]');">
						440100000201208271030 </a>
				</h3>
				<p>
					<b>登记时间:</b> 2012-08-27 &nbsp;&nbsp;<b>信息类型:</b> 咨询 &nbsp;&nbsp;<b>受理登记人:</b>
					徐丽婷
				</p>
				<p>
					<b>接收方式:</b> 电话（语音） &nbsp;&nbsp;<b>处理单位:</b> 广州市工商行政管理局
					&nbsp;&nbsp;<b>消费类型:</b>
				</p>
				<p>
					<b>内容:</b> 来电咨询天河北路所在工商所电话，已告知天河工商所电话，对方中断电话。
				</p>
			</div>
		</div>




		<div class="sous_list">
			﻿
			<div>
				<h3>
					<a href="javascript:void(0);"
						onclick="opencp('[440100000201208270996]');"> 广州日报
						440100000201208270996 </a>
				</h3>
				<p>
					<b>登记时间:</b> 2012-08-27 &nbsp;&nbsp;<b>信息类型:</b> 咨询 &nbsp;&nbsp;<b>受理登记人:</b>
					梁颖娜
				</p>
				<p>
					<b>接收方式:</b> 电话（语音） &nbsp;&nbsp;<b>处理单位:</b> 广州市工商行政管理局
					&nbsp;&nbsp;<b>消费类型:</b> 其他中药
				</p>
				<p>
					<b>内容:</b>
					来电者称，8月23日其在网上看到广州日报的一条消息：可以去到指定各大药店换过期的药品。其去到龙津路的一家药店换取，但工作人员已经提前离开，认为不合理，已建议先致电广州日报相关工作人员咨询此事。
				</p>
			</div>
		</div>




		<div class="sous_list">
			﻿
			<div>
				<h3>
					<a href="javascript:void(0);"
						onclick="opencp('[440100000201208271038]');">
						440100000201208271038 </a>
				</h3>
				<p>
					<b>登记时间:</b> 2012-08-27 &nbsp;&nbsp;<b>信息类型:</b> 咨询 &nbsp;&nbsp;<b>受理登记人:</b>
					吴嘉良
				</p>
				<p>
					<b>接收方式:</b> 电话（语音） &nbsp;&nbsp;<b>处理单位:</b> 广州市工商行政管理局
					&nbsp;&nbsp;<b>消费类型:</b> 绿茶
				</p>
				<p>
					<b>内容:</b> 来电反映在流花地铁d4出口的报纸档卖假的绿茶，通话中断。
				</p>
			</div>
		</div>




		<div class="sous_list">
			﻿
			<div>
				<h3>
					<a href="javascript:void(0);"
						onclick="opencp('[440100000201208271033]');">
						440100000201208271033 </a>
				</h3>
				<p>
					<b>登记时间:</b> 2012-08-27 &nbsp;&nbsp;<b>信息类型:</b> 咨询 &nbsp;&nbsp;<b>受理登记人:</b>
					傅雪梅
				</p>
				<p>
					<b>接收方式:</b> 电话（语音） &nbsp;&nbsp;<b>处理单位:</b> 广州市工商行政管理局
					&nbsp;&nbsp;<b>消费类型:</b>
				</p>
				<p>
					<b>内容:</b>
					来电者咨询其曾经在制假售假处举报某商店的不锈钢制假售假，至今想查询案件进度。其称是交由花都工商部门跟进的，建议其拨打花都工商局咨询。
				</p>
			</div>
		</div>




		<div class="sous_list">
			﻿
			<div>
				<h3>
					<a href="javascript:void(0);"
						onclick="opencp('[440100000201208271054]');">
						440100000201208271054 </a>
				</h3>
				<p>
					<b>登记时间:</b> 2012-08-27 &nbsp;&nbsp;<b>信息类型:</b> 咨询 &nbsp;&nbsp;<b>受理登记人:</b>
					黎盛廷
				</p>
				<p>
					<b>接收方式:</b> 电话（语音） &nbsp;&nbsp;<b>处理单位:</b> 广州市工商行政管理局
					&nbsp;&nbsp;<b>消费类型:</b>
				</p>
				<p>
					<b>内容:</b> (录音）来电人称，我要投诉西村派出所，其家发生家庭暴力，报警后派出所人员不为其做笔录，（已于2012-08-27
					15:19:37回拨来电人18620202284， 告知来电人该种事情不属于12315的受理范围，建议其再次报警处理。）
				</p>
			</div>
		</div>




		<div class="sous_list">
			﻿
			<div>
				<h3>
					<a href="javascript:void(0);"
						onclick="opencp('[440100000201208271060]');"> 某药店
						440100000201208271060 </a>
				</h3>
				<p>
					<b>登记时间:</b> 2012-08-27 &nbsp;&nbsp;<b>信息类型:</b> 咨询 &nbsp;&nbsp;<b>受理登记人:</b>
					何毅
				</p>
				<p>
					<b>接收方式:</b> 电话（语音） &nbsp;&nbsp;<b>处理单位:</b> 广州市工商行政管理局
					&nbsp;&nbsp;<b>消费类型:</b>
				</p>
				<p>
					<b>内容:</b> 来电者反映该药店涉嫌销售假药。已建议其致电食药监。
				</p>
			</div>
		</div>




		<div class="sous_list">
			﻿
			<div>
				<h3>
					<a href="javascript:void(0);"
						onclick="opencp('[440100000201208270988]');"> 广州方为电子科技有限公司
						440100000201208270988 </a>
				</h3>
				<p>
					<b>登记时间:</b> 2012-08-27 &nbsp;&nbsp;<b>信息类型:</b> 消委会投诉 &nbsp;&nbsp;<b>受理登记人:</b>
					罗铭聪
				</p>
				<p>
					<b>接收方式:</b> 电话（语音） &nbsp;&nbsp;<b>处理单位:</b> 广州市工商行政管理局越秀分局大东工商所
					&nbsp;&nbsp;<b>消费类型:</b> 话费查询服务
				</p>
				<p>
					<b>内容:</b>
					投诉者称：我于8月26日晚上通过该公司参加充1999送手机以及电视的优惠套餐，该公司是电信公司的代理商，当时现场工作人员说每月返还33元并可用于抵购月租，其后我致电电信，客服答复说该费用不能用于抵购，现要求该公司给个说法并退订该套餐。请消委会协调处理。
				</p>
			</div>
		</div>




		<div class="sous_list">
			﻿
			<div>
				<h3>
					<a href="javascript:void(0);"
						onclick="opencp('[440100000201208271013]');"> 供销驾驶学校
						440100000201208271013 </a>
				</h3>
				<p>
					<b>登记时间:</b> 2012-08-27 &nbsp;&nbsp;<b>信息类型:</b> 咨询 &nbsp;&nbsp;<b>受理登记人:</b>
					孔芷韵
				</p>
				<p>
					<b>接收方式:</b> 电话（语音） &nbsp;&nbsp;<b>处理单位:</b> 广州市工商行政管理局
					&nbsp;&nbsp;<b>消费类型:</b> 驾驶培训服务
				</p>
				<p>
					<b>内容:</b> 来电咨询，其通过该驾校报名学车，付款至今未为其安排考试，现要求退款。地址待补充。
				</p>
			</div>
		</div>




		<div class="sous_list">
			﻿
			<div>
				<h3>
					<a href="javascript:void(0);"
						onclick="opencp('[440100000201208270980]');"> 大众点评团购
						440100000201208270980 </a>
				</h3>
				<p>
					<b>登记时间:</b> 2012-08-27 &nbsp;&nbsp;<b>信息类型:</b> 消委会投诉 &nbsp;&nbsp;<b>受理登记人:</b>
					原伟超
				</p>
				<p>
					<b>接收方式:</b> 电话（语音） &nbsp;&nbsp;<b>处理单位:</b> 广州市工商行政管理局越秀分局光塔工商所
					&nbsp;&nbsp;<b>消费类型:</b> 餐馆服务
				</p>
				<p>
					<b>内容:</b>
					投诉人称，我于8月23日通过该网站（p.dianping.com）团购了海记汕头牛肉分别价值98元和149元的两个套餐，还没有消费，现要求退，但对方拒绝。我认为不合理，要求退款，请消委会协调处理。
				</p>
			</div>
		</div>




		<div class="sous_list">
			﻿
			<div>
				<h3>
					<a href="javascript:void(0);"
						onclick="opencp('[440100000201208271040]');">
						440100000201208271040 </a>
				</h3>
				<p>
					<b>登记时间:</b> 2012-08-27 &nbsp;&nbsp;<b>信息类型:</b> 咨询 &nbsp;&nbsp;<b>受理登记人:</b>
					李俊塔
				</p>
				<p>
					<b>接收方式:</b> 电话（语音） &nbsp;&nbsp;<b>处理单位:</b> 广州市工商行政管理局
					&nbsp;&nbsp;<b>消费类型:</b>
				</p>
				<p>
					<b>内容:</b> 来电咨询外资企业年检的问题，建议其致电外资处。
				</p>
			</div>
		</div>
	</div>
	<div class="pagination">


		总记录数:&nbsp;859155 &nbsp;&nbsp;消耗时间:&nbsp;32 ms 当前为第1页 共85916页 <a
			class="next-page" href="/solr/collection1/browse?&q=&start=10">下一页</a>
		<br />
	</div>

</body>
</html>