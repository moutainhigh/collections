<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>拍卖后备案信息</title>
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
<div vtype="gridpanel" name="jbxxGrid" height="230" width="100%"  id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()" selecttype="1"	titledisplay="true" title="拍卖后备案信息"  showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="98%">
		<div> 
				<div name='afterauctionid' text="表的主键" visible="false" textalign="center"  width="10%"></div>
				<div name='bizsequence' text="业务流水号" visible="false" textalign="center"  width="10%"></div>
				<div name='auctionid' text="对应Auction表的主键" visible="false" textalign="center"  width="10%"></div>
			<div name='attn' text="经办人"  textalign="center"  width="20%"></div>
			<div name='passnum' text="流拍数" textalign="center"  width="15%"></div>
			<div name='postapprovedate' text="报送时间" textalign="center"  width="15%"></div>
			<div name='status' text="状态" textalign="center"  width="10%"></div>
			<div name='hasmonitor' text="是否现场监督" textalign="center"  width="10%"></div>
			<div name='barnum' text="成交份数" textalign="center"  width="15%"></div>
			<div name='bargro' text="成交总金额" textalign="center"  width="15%"></div>
			
			<div name='apprdate' text="核准日期" visible="false" textalign="center"  width="10%"></div>
			<div name='aicid' text="登记管辖机关ID" visible="false" textalign="center"  width="10%"></div>
			<div name='issuedeptid' text="登记机关ID" visible="false" textalign="center"  width="10%"></div>
			<div name='list' text="本场监拍人员名单" visible="false" textalign="center"  width="10%"></div>
			<div name='untrademsg' text="未成交情况说明" visible="false" textalign="center"  width="10%"></div>
			<div name='strifenum' text="竞买人数" visible="false" textalign="center"  width="10%"></div>
			<div name='acceptnum' text="受买人数" visible="false" textalign="center"  width="10%"></div>
			<div name='iswebsubmit' text="是否网上备案（y是）" visible="false" textalign="center"  width="10%"></div>
			<div name='aucdate' text="拍卖会日期" visible="false" textalign="center"  width="10%"></div>
			<div name='createddate' text="拍卖后创建时间" visible="false" textalign="center"  width="10%"></div>
				<div name='requestid' text="拍卖后申请id" visible="false" textalign="center"  width="10%"></div>
				<div name='aucpripid' text="拍卖人主体身份代码" visible="false" textalign="center"  width="10%"></div>
				<div name='sourceflag' text="SOURCEFLAG" visible="false" textalign="center"  width="10%"></div>
				<div name='timestamp' text="TIMESTAMP" visible="false" textalign="center"  width="10%"></div>
		</div>
	</div>
	<div vtype="gridtable" name="grid_table" rowselect="rowclick()"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
<div id="formpanel" class="formpanel_table1" style="height:250px;overflow-y: auto;">
	<div class="font_title">详细信息</div>
	<table id="datashow" class="font_table" >
		<tr><th class="class_td1">经办人:</th><th id="row4" class="class_td2"></th>
			<th class="class_td4">流拍数:</th><th id="row5" class="class_td5"></th></tr>
		<tr><th class="class_td1">报送时间:</th><th id="row6" class="class_td2"></th>
			<th class="class_td4">状态:</th><th id="row7" class="class_td5"></th></tr>
		<tr><th class="class_td1">是否现场监督:</th><th id="row8" class="class_td2"></th>
			<th class="class_td4">成交份数:</th><th id="row9" class="class_td5"></th></tr>
		<tr><th class="class_td1">成交总金额:</th><th id="row10" class="class_td2"></th>
			<th class="class_td4">核准日期:</th><th id="row11" class="class_td5"></th></tr>
		<tr><th class="class_td1">登记管辖机关ID:</th><th id="row12" class="class_td2"></th>
			<th class="class_td4">登记机关ID:</th><th id="row13" class="class_td5"></th></tr>
		<tr><th class="class_td1">本场监拍人员名单:</th><th id="row14" class="class_td2"></th>
			<th class="class_td4">未成交情况说明:</th><th id="row15" class="class_td5"></th></tr>
		<tr><th class="class_td1">竞买人数:</th><th id="row16" class="class_td2"></th>
			<th class="class_td4">受买人数:</th><th id="row17" class="class_td5"></th></tr>
		<tr><th class="class_td1">是否网上备案（y是）:</th><th id="row18" class="class_td2"></th>
			<th class="class_td4">拍卖会日期:</th><th id="row19" class="class_td5"></th></tr>
		<tr><th class="class_td1">拍卖后创建时间:</th><th id="row20" class="class_td2" colspan="3"></th>
			</tr>
	</table>
</div>
</body>
</html>