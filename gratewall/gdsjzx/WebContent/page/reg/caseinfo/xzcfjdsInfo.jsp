<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>案件基本信息</title>
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
<div vtype="gridpanel" name="jbxxGrid" height="230" width="100%"  id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()" selecttype="1"	titledisplay="true" title="行政处罚决定书信息"  showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="98%">
		<div> 
				<div name='fijudgmentid' text="处罚决定书编号" visible="false" textalign="center"  width="10%"></div>
				<div name='timestamp' text="保留字段" visible="false" textalign="center"  width="10%"></div>
				<div name='sourceflag' text="保留字段" visible="false" textalign="center"  width="10%"></div>
			<div name='pendecno' text="处罚决定书文书号" textalign="center"  width="15%"></div>
			<div name='regno' text="企业注册号" textalign="center"  width="15%"></div>
			<div name='caseno' text="案件编号" visible="false" textalign="center"  width="15%"></div>
			<div name='fspartyname' text="当事人名称" textalign="center"  width="15%"></div>
			<div name='lerep' text="法定代表人（负责人）" textalign="center"  width="15%"></div>
			<div name='casereason' text="案由" textalign="center"  width="12%"></div>
			<div name='illegfact' text="违法事实" textalign="center"  width="12%"></div>
			<div name='postcode' text="邮政编码" visible="false" textalign="center"  width="10%"></div>
			<div name='tel' text="电话" visible="false" textalign="center"  width="10%"></div>
			<div name='oploc' text="住所（经营场所）" visible="false" textalign="center"  width="10%"></div>
			<div name='opscope' text="经营范围" visible="false" textalign="center"  width="10%"></div>
			<div name='sex' text="性别" visible="false" textalign="center"  width="10%"></div>
			<div name='age' text="年龄" visible="false" textalign="center"  width="10%"></div>
			<div name='occupation' text="职业" visible="false" textalign="center"  width="10%"></div>
			<div name='cerno' text="身份证号码" visible="false" textalign="center"  width="10%"></div>
			<div name='litedeg' text="文化程度" visible="false" textalign="center"  width="10%"></div>
			<div name='nation' text="民族" visible="false" textalign="center"  width="10%"></div>
			<div name='polstand' text="政治面目" visible="false" textalign="center"  width="10%"></div>
			<div name='fsnativeplace' text="籍贯" visible="false" textalign="center"  width="10%"></div>
			<div name='quabasis' text="定性依据" visible="false" textalign="center"  width="10%"></div>
			<div name='penbasis' text="处罚依据" visible="false" textalign="center"  width="10%"></div>
			<div name='fsalarm' text="警告" visible="false" textalign="center"  width="10%"></div>
			<div name='fschargestoplaw' text="责令停止违法行为"  textalign="center"  width="15%"></div>
			<div name='fschargestopintrusion' text="责令停止侵权行为" visible="false" textalign="center"  width="10%"></div>
			<div name='ffseizureillegal' text="没收违法所得" visible="false" textalign="center"  width="10%"></div>
			<div name='ffseizurenonlaw' text="没收非法所得" visible="false" textalign="center"  width="10%"></div>
			<div name='ffseizureprofit' text="没收非法获利" visible="false" textalign="center"  width="10%"></div>
			<div name='ffseizuresale' text="没收销货款" visible="false" textalign="center"  width="10%"></div>
			<div name='ffseizuredealbrow' text="没收非法经营额" visible="false" textalign="center"  width="10%"></div>
			<div name='penam' text="罚款" visible="false" textalign="center"  width="10%"></div>
			<div name='fskeeplicence' text="暂扣许可证" visible="false" textalign="center"  width="10%"></div>
			<div name='fsrevokelicence' text="吊销许可证" visible="false" textalign="center"  width="10%"></div>
			<div name='fskeepshopcard' text="暂扣营业执照" visible="false" textalign="center"  width="10%"></div>
			<div name='fsrevokeshopcard' text="吊销营业执照" visible="false" textalign="center"  width="10%"></div>
			<div name='fsstopproduction' text="责令停产停业" visible="false" textalign="center"  width="10%"></div>
			<div name='fsaviso' text="通报批评" visible="false" textalign="center"  width="10%"></div>
			<div name='fspricefixing' text="限价出售商品" visible="false" textalign="center"  width="10%"></div>
			<div name='fspreemption' text="强制收购商品" visible="false" textalign="center"  width="10%"></div>
			<div name='fsotherpunish' text="法律法规规定的其他行政处罚" visible="false" textalign="center"  width="10%"></div>
			<div name='fstellright' text="告知当事人权利" visible="false" textalign="center"  width="10%"></div>
			<div name='fdrecorddate' text="处罚决定书上标注的时间" visible="false" textalign="center"  width="10%"></div>
			<div name='fsrecorder' text="录入人" visible="false" textalign="center"  width="10%"></div>
			<div name='fdcreateupdatedate' text="录入修改的时间（取系统时间）" visible="false" textalign="center"  width="10%"></div>
			<div name='pigsign' text="归档标志" visible="false" textalign="center"  width="10%"></div>
			<div name='archno' text="电子档案编号" visible="false" textalign="center"  width="10%"></div>
			<div name='fsdeductproperty' text="没收非法财物及违法物品" visible="false" textalign="center"  width="10%"></div>
			<div name='fsopencorrect' text="责令公开更正" visible="false" textalign="center"  width="10%"></div>
			<div name='fsstopissue' text="责令停止发布" visible="false" textalign="center"  width="10%"></div>
			<div name='fsseizureillegaltype' text="没收违法所得类型" visible="false" textalign="center"  width="10%"></div>
			<div name='fsseizuresaletype' text="没收销货款类型" visible="false" textalign="center"  width="10%"></div>
				<div name='area_code' text="保留字段" visible="false" textalign="center"  width="10%"></div>
						
		</div>
	</div>
	<div vtype="gridtable" name="grid_table" rowselect="rowclick()"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
<div id="formpanel" class="formpanel_table1" style="height:250px;overflow-y: auto;">
	<div class="font_title">详细信息</div>
	<table id="datashow" class="font_table" >
		<tr><th class="class_td1">处罚决定书文书号:</th><th id="row4" class="class_td2"></th>
			<th class="class_td4">企业注册号:</th><th id="row5" class="class_td5"></th></tr>
		<tr><th class="class_td1">案件编号:</th><th id="row6" class="class_td2"></th>
			<th class="class_td4">当事人名称:</th><th id="row7" class="class_td5"></th></tr>
		<tr><th class="class_td1">法定代表人（负责人）:</th><th id="row8" class="class_td2"></th>
			<th class="class_td4">案由:</th><th id="row9" class="class_td5"></th></tr>
		<tr><th class="class_td1">违法事实:</th><th id="row10" class="class_td2"></th>
			<th class="class_td4">邮政编码:</th><th id="row11" class="class_td5"></th></tr>
		<tr><th class="class_td1">电话:</th><th id="row12" class="class_td2"></th>
			<th class="class_td4">住所（经营场所）:</th><th id="row13" class="class_td5"></th></tr>
		<tr><th class="class_td1">经营范围:</th><th id="row14" class="class_td2"></th>
			<th class="class_td4">性别:</th><th id="row15" class="class_td5"></th></tr>
		<tr><th class="class_td1">年龄:</th><th id="row16" class="class_td2"></th>
			<th class="class_td4">职业:</th><th id="row17" class="class_td5"></th></tr>
		<tr><th class="class_td1">身份证号码:</th><th id="row18" class="class_td2"></th>
			<th class="class_td4">文化程度:</th><th id="row19" class="class_td5"></th></tr>
		<tr><th class="class_td1">民族:</th><th id="row20" class="class_td2"></th>
			<th class="class_td4">政治面目:</th><th id="row21" class="class_td5"></th></tr>
		<tr><th class="class_td1">籍贯:</th><th id="row22" class="class_td2"></th>
			<th class="class_td4">定性依据:</th><th id="row23" class="class_td5"></th></tr>
		<tr><th class="class_td1">处罚依据:</th><th id="row24" class="class_td2"></th>
			<th class="class_td4">警告:</th><th id="row25" class="class_td5"></tr>	
		<tr><th class="class_td1">责令停止违法行为:</th><th id="row26" class="class_td2"></th>
			<th class="class_td4">责令停止侵权行为:</th><th id="row27" class="class_td5"></th></tr>
		<tr><th class="class_td1">没收违法所得:</th><th id="row28" class="class_td2"></th>
			<th class="class_td4">没收非法所得:</th><th id="row29" class="class_td5"></th></tr>
		<tr><th class="class_td1">没收非法获利:</th><th id="row30" class="class_td2"></th>
			<th class="class_td4">没收销货款:</th><th id="row31" class="class_td5"></th></tr>
		<tr><th class="class_td1">没收非法经营额:</th><th id="row32" class="class_td2"></th>
			<th class="class_td4">罚款:</th><th id="row33" class="class_td5"></th></tr>
		<tr><th class="class_td1">暂扣许可证:</th><th id="row34" class="class_td2"></th>
			<th class="class_td4">吊销许可证:</th><th id="row35" class="class_td5"></th></tr>
		<tr><th class="class_td1">暂扣营业执照:</th><th id="row36" class="class_td2"></th>
			<th class="class_td4">吊销营业执照:</th><th id="row37" class="class_td5"></tr>
		<tr><th class="class_td1">责令停产停业:</th><th id="row38" class="class_td2"></th>
			<th class="class_td4">通报批评:</th><th id="row39" class="class_td5"></th></tr>
		<tr><th class="class_td1">限价出售商品:</th><th id="row40" class="class_td2"></th>
			<th class="class_td4">强制收购商品:</th><th id="row41" class="class_td5"></th></tr>
		<tr><th class="class_td1">法律法规规定的其他行政处罚:</th><th id="row42" class="class_td2"></th>
			<th class="class_td4">告知当事人权利:</th><th id="row43" class="class_td5"></th></tr>
		<tr><th class="class_td1">处罚决定书上标注的时间:</th><th id="row44" class="class_td2"></th>
			<th class="class_td4">录入人:</th><th id="row45" class="class_td5"></th></tr>
		<tr><th class="class_td1">录入修改的时间:</th><th id="row46" class="class_td2"></th>
			<th class="class_td4">归档标志:</th><th id="row47" class="class_td5"></th></tr>
		<tr><th class="class_td1">电子档案编号:</th><th id="row48" class="class_td2"></th>
			<th class="class_td4">没收非法财物及违法物品:</th><th id="row49" class="class_td5"></tr>
		<tr><th class="class_td1">责令公开更正:</th><th id="row50" class="class_td2"></th>
			<th class="class_td4">责令停止发布:</th><th id="row51" class="class_td5"></th></tr>
		<tr><th class="class_td1">没收违法所得类型:</th><th id="row52" class="class_td2"></th>
			<th class="class_td4">没收销货款类型:</th><th id="row53" class="class_td5"></tr>
	</table>
</div>
</body>
</html>