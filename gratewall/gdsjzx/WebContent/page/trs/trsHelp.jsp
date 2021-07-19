<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<title>检索帮助</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<style type="text/css">
td{
	color: #666;
    font-size: 12px;
    font-family: "瀹嬩綋";
    /* padding: 7px 0px 0px; */
    font-weight:bold;
    word-wrap: break-word;
}
tr{
border:1px solid #AFB0B1;
border-left: 0px solid #AFB0B1;
}
</style>
</head>
<body>
<div name="row_id" height="100%"   vtype="panel" layout="row" layoutconfig="{rowheight:['78px','*']}">
	<div class="headertop" style="background:url(<%=request.getContextPath()%>/static/images/system/index/B-05.png); height:78px; width:100%;">
		<img alt="广东省工商局" width="316px" height="52px"
			 src="<%=request.getContextPath()%>/static/images/system/index/B-06.png"
			 style="background: #ffffff; margin-left:50px;margin-top:10px; vertical-align: middle;"/>
	</div>
	<div style="height: 100%">
		<div>
			<div style="margin: 20px 10px 10px 20px;font-weight: bolder;line-height: 2;font-size: 19;">
				<font style="color: red;">温馨提示:</font></br>
				&nbsp;&nbsp;市场主体检索项:企业名称,注册号,统一信用代码,地址,经营范围,行业,法定代表人名称和证件号,高级人员名称和证件号,投资人名称和证件号,变更历史;</br>
				&nbsp;&nbsp;12315检索项:编号,申诉举报具体问题,受理登记人,事发地,关键字,姓名,地址,涉及主体经营地址,登记部门,涉及主体,商品名称;</br>
				&nbsp;&nbsp;年度报告检索项:企业名称,注册号,企业类型,通讯地址,企业状态,投资人,年报年度;</br>
				&nbsp;&nbsp;案件信息检索项:案件名称,案件编号,企业名称,案由,销案理由,案发地。</br>
			</div>
		</div>
		<div id="gwssiimg" style="position:fixed;bottom:0;">
			<img alt="广东省工商局" width="100%" height="67px"
				 src="<%=request.getContextPath()%>/static/images/system/index/B-04.png"
				 style="vertical-align: middle;"/>
		</div>
	</div>
</div>
</body>
</html>