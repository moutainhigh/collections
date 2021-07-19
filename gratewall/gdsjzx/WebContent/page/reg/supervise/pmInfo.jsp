<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>拍卖信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>

<script src="<%=request.getContextPath()%>/static/js/sczt/sczt.js" type="text/javascript"></script>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/static/css/reg/reg.css"></link>

<script type="text/javascript">
function rowclick(event,data){
}
</script>
				 
</head>
<body >
<div vtype="gridpanel" name="jbxxGrid" height="230" width="100%"  id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()" selecttype="1"	titledisplay="true" title="拍卖信息"  showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="98%">
		<div> 
				<div name='historyinfoid' text="历史资料编号" visible="false" textalign="center"  width="10%"></div>
				<div name='bizsequence' text="业务流水号" visible="false" textalign="center"  width="10%"></div>
				<div name='sourceflag' text="SOURCEFLAG" visible="false" textalign="center"  width="10%"></div>
			<div name='auc' text="拍卖人" textalign="center"  width="20%"></div>
			<div name='aucname' text="拍卖会名称" textalign="center"  width="20%"></div>
			<div name='aucregno' text="拍卖人注册号" textalign="center"  width="15%"></div>
			<div name='aucmasname' text="拍卖师姓名" textalign="center"  width="15%"></div>
			<div name='status' text="拍卖状态" textalign="center"  width="15%"></div>
			<div name='aucdate' text="拍卖会日期" textalign="center"  width="15%"></div>
			
			<div name='attn' text="经办人" visible="false" textalign="center"  width="15%"></div>
			<div name='aucspot' text="拍卖会地点" visible="false" textalign="center"  width="10%"></div>
			<div name='pubdate' text="公告日期" visible="false" textalign="center"  width="10%"></div>
			<div name='pubme' text="公告媒体" visible="false" textalign="center"  width="10%"></div>
			<div name='unfdate' text="标的展示日期" visible="false" textalign="center"  width="10%"></div>
			<div name='itemshowaddress' text="标的展示地点" visible="false" textalign="center"  width="10%"></div>
			<div name='isforeign' text="是否异地拍卖" visible="false" textalign="center"  width="10%"></div>
			<div name='barnum' text="成交份数" visible="false" textalign="center"  width="10%"></div>
			<div name='turnover' text="成交总金额" visible="false" textalign="center"  width="10%"></div>
			<div name='postbizsequence' text="拍卖后备案的业务流水号" visible="false" textalign="center"  width="10%"></div>
			<div name='contractsum' text="委托拍卖合同总金额" visible="false" textalign="center"  width="10%"></div>
			<div name='contractnum' text="委托拍卖合同总份数" visible="false" textalign="center"  width="10%"></div>
			<div name='supervisedeptid' text="监管机关编号" visible="false" textalign="center"  width="10%"></div>
			<div name='auctioneercertid' text="拍卖人证件ID" visible="false" textalign="center"  width="10%"></div>
			<div name='auctionperiodno' text="拍卖期号" visible="false" textalign="center"  width="10%"></div>
			<div name='postinfonum' text="岗位信息号码" visible="false" textalign="center"  width="10%"></div>
			<div name='approvedate' text="核准时间" visible="false" textalign="center"  width="10%"></div>
			<div name='itemshowenddate' text="展示结束时间" visible="false" textalign="center"  width="10%"></div>
			<div name='issuedeptid' text="登记发照机关编号" visible="false" textalign="center"  width="10%"></div>
			<div name='aicid' text="登记管辖机关编号" visible="false" textalign="center"  width="10%"></div>
			<div name='postapprovedate' text="拍卖后报送时间" visible="false" textalign="center"  width="10%"></div>
			<div name='hasmonitor' text="是否有现场监督" visible="false" textalign="center"  width="10%"></div>
			<div name='entitytypeid' text="拍卖备案的拍卖企业类型" visible="false" textalign="center"  width="10%"></div>
			<div name='lastapprovedate' text="代表拍卖后备案的核准时间" visible="false" textalign="center"  width="10%"></div>
			<div name='industrytype' text="拍卖企业性质" visible="false" textalign="center"  width="10%"></div>
			<div name='auctioncorpregno' text="拍卖企业注册号" visible="false" textalign="center"  width="10%"></div>
			<div name='item' text="拍卖标的条款" visible="false" textalign="center"  width="10%"></div>
			<div name='itemsource' text="拍卖标的来源" visible="false" textalign="center"  width="10%"></div>
			<div name='iswebsubmit' text="是否网上备案（y是）" visible="false" textalign="center"  width="10%"></div>
				<div name='requestid' text="拍卖前申请id" visible="false" textalign="center"  width="10%"></div>
				<div name='aucpripid' text="拍卖人主体身份代码" visible="false" textalign="center"  width="10%"></div>
				<div name='timestamp' text="TIMESTAMP" visible="false" textalign="center"  width="10%"></div>
		</div>
	</div>
	<div vtype="gridtable" name="grid_table" rowselect="rowclick()"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
<div id="formpanel" class="formpanel_table1" style="height:250px;overflow-y: auto;">
	<div class="font_title">详细信息</div>
	<table id="datashow" class="font_table" >
		<tr><th class="class_td1">拍卖人:</th><th id="row4" class="class_td2"></th>
			<th class="class_td4">拍卖会名称:</th><th id="row5" class="class_td5"></th></tr>
		<tr><th class="class_td1">拍卖人注册号:</th><th id="row6" class="class_td2"></th>
			<th class="class_td4">拍卖师姓名:</th><th id="row7" class="class_td5"></th></tr>
		<tr><th class="class_td1">经办人:</th><th id="row8" class="class_td2"></th>
			<th class="class_td4">拍卖状态:</th><th id="row9" class="class_td5"></th></tr>
		<tr><th class="class_td1">拍卖会日期:</th><th id="row10" class="class_td2"></th>
			<th class="class_td4">拍卖会地点:</th><th id="row11" class="class_td5"></th></tr>
		<tr><th class="class_td1">公告日期:</th><th id="row12" class="class_td2"></th>
			<th class="class_td4">公告媒体:</th><th id="row13" class="class_td5"></th></tr>
		<tr><th class="class_td1">标的展示日期:</th><th id="row14" class="class_td2"></th>
			<th class="class_td4">标的展示地点:</th><th id="row15" class="class_td5"></th></tr>
		<tr><th class="class_td1">是否异地拍卖:</th><th id="row16" class="class_td2"></th>
			<th class="class_td4">成交份数:</th><th id="row17" class="class_td5"></th></tr>
		<tr><th class="class_td1">成交总金额:</th><th id="row18" class="class_td2"></th>
			<th class="class_td4">拍卖后备案的业务流水号:</th><th id="row19" class="class_td5"></th></tr>
		<tr><th class="class_td1">委托拍卖合同总金额:</th><th id="row20" class="class_td2"></th>
			<th class="class_td4">委托拍卖合同总份数:</th><th id="row21" class="class_td5"></th></tr>
		<tr><th class="class_td1">监管机关编号:</th><th id="row22" class="class_td2"></th>
			<th class="class_td4">拍卖人证件ID:</th><th id="row23" class="class_td5"></th></tr>
		<tr><th class="class_td1">拍卖期号:</th><th id="row24" class="class_td2"></th>
			<th class="class_td4">岗位信息号码:</th><th id="row25" class="class_td5"></th></tr>	
		<tr><th class="class_td1">核准时间:</th><th id="row26" class="class_td2"></th>
			<th class="class_td4">展示结束时间:</th><th id="row27" class="class_td5"></th></tr>
		<tr><th class="class_td1">登记发照机关编号:</th><th id="row28" class="class_td2"></th>
			<th class="class_td4">登记管辖机关编号:</th><th id="row29" class="class_td5"></th></tr>
			
		<tr><th class="class_td1">拍卖后报送时间:</th><th id="row30" class="class_td2"></th>
			<th class="class_td4">是否有现场监督:</th><th id="row31" class="class_td5"></th></tr>
		<tr><th class="class_td1">拍卖备案的拍卖企业类型:</th><th id="row32" class="class_td2"></th>
			<th class="class_td4">代表拍卖后备案的核准时间:</th><th id="row33" class="class_td5"></th></tr>
			
		<tr><th class="class_td1">拍卖企业性质:</th><th id="row34" class="class_td2"></th>
			<th class="class_td4">拍卖企业注册号:</th><th id="row35" class="class_td5"></th></tr>
		<tr><th class="class_td1">拍卖标的条款:</th><th id="row36" class="class_td2"></th>
			<th class="class_td4">拍卖标的来源:</th><th id="row37" class="class_td5"></th></tr>
		<tr><th class="class_td1">是否网上备案（y是）:</th><th id="row38" class="class_td2" colspan="3"></th></tr>
	</table>
</div>
</body>
</html>