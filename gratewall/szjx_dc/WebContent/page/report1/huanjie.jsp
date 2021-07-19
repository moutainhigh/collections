<%@ page contentType="text/html; charset=utf-8" language="java"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<script
	src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js"
	type="text/javascript"></script>

<script
	src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js"
	type="text/javascript"></script>
<style type="text/css">
</style>
<script type="text/javascript" charset="UTF-8">
	function reset() {
		$("#formpanel").formpanel('reset');
	}
	function queryUrl() {
		var aa = $("#formpanel").formpanel('getValue');
		var begintime = aa.data.begintime;
		var endtime = aa.data.endtime;
		var regcode = aa.data.regcode;
		if (begintime.length != 0 && endtime.length != 0) {
			if (begintime > endtime) {
				alert("初始日期大于截止日期");
				return;
			}
			if (begintime == endtime) {
				alert("初始日期等于截止日期");
				return;
			}
		}
		if (begintime.length == 0 || endtime.length == 0) {
			alert("日期为空");
			return;
		}
		var htmls = '<div>'
				+ '<a href="#" id=\'pluginurl\' onclick="downReport(' + '\''
				+ begintime
				+ '\''
				+ ','
				+ '\''
				+ endtime
				+ '\''
				+ ')" > 下     载</a>'
				+ '</div>'
				+ '<div style=\'background:white;color:white;text-align:right;\'>&nbsp;</div>'+
				"<div id=\"环节工作量统计表_15134\" align=center x:publishsource=\"Excel\">\n" +
				"<table border=0 cellpadding=0 cellspacing=0 width=4747 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:3561pt'>\n" + 
				" <col width=130 span=2 style='mso-width-source:userset;mso-width-alt:4160;\n" + 
				" width:98pt'>\n" + 
				" <col width=95 style='mso-width-source:userset;mso-width-alt:3040;width:71pt'>\n" + 
				" <col width=72 span=61 style='width:54pt'>\n" + 
				" <tr height=71 style='mso-height-source:userset;height:53.25pt'>\n" + 
				"  <td colspan=64 height=71 class=xl6915134 width=4747 style='height:53.25pt;\n" + 
				"  width:3561pt'><span\n" + 
				"  style='mso-spacerun:yes'>                                                                           \n" + 
				"  </span>环节工作量统计表</td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td rowspan=2 height=38 class=xl6515134 style='height:28.5pt;border-top:none'>单位名称</td>\n" + 
				"  <td rowspan=2 class=xl6515134 style='border-top:none'>处理人</td>\n" + 
				"  <td colspan=2 class=xl6515134 style='border-left:none'>预登记</td>\n" + 
				"  <td colspan=9 class=xl6415134 dir=LTR width=648 style='border-left:none;\n" + 
				"  width:486pt'>待分派</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>待决策</td>\n" + 
				"  <td colspan=4 class=xl6415134 dir=LTR width=288 style='border-left:none;\n" + 
				"  width:216pt'>待审批</td>\n" + 
				"  <td colspan=5 class=xl6415134 dir=LTR width=360 style='border-left:none;\n" + 
				"  width:270pt'>待受理</td>\n" + 
				"  <td colspan=6 class=xl6415134 dir=LTR width=432 style='border-left:none;\n" + 
				"  width:324pt'>办理中</td>\n" + 
				"  <td colspan=3 class=xl6415134 dir=LTR width=216 style='border-left:none;\n" + 
				"  width:162pt'>办结确认</td>\n" + 
				"  <td colspan=3 class=xl6415134 dir=LTR width=216 style='border-left:none;\n" + 
				"  width:162pt'>中止审批</td>\n" + 
				"  <td colspan=2 class=xl6415134 dir=LTR width=144 style='border-left:none;\n" + 
				"  width:108pt'>挂起审批</td>\n" + 
				"  <td colspan=2 class=xl6415134 dir=LTR width=144 style='border-left:none;\n" + 
				"  width:108pt'>回收审批</td>\n" + 
				"  <td colspan=3 class=xl6415134 dir=LTR width=216 style='border-left:none;\n" + 
				"  width:162pt'>延时审批</td>\n" + 
				"  <td colspan=3 class=xl6415134 dir=LTR width=216 style='border-left:none;\n" + 
				"  width:162pt'>办结审核</td>\n" + 
				"  <td colspan=3 class=xl6415134 dir=LTR width=216 style='border-left:none;\n" + 
				"  width:162pt'>不受理审核</td>\n" + 
				"  <td colspan=2 class=xl6415134 dir=LTR width=144 style='border-left:none;\n" + 
				"  width:108pt'>已挂起</td>\n" + 
				"  <td colspan=6 class=xl6415134 dir=LTR width=432 style='border-left:none;\n" + 
				"  width:324pt'>待归档</td>\n" + 
				"  <td colspan=3 class=xl6415134 dir=LTR width=216 style='border-left:none;\n" + 
				"  width:162pt'>驳回审批</td>\n" + 
				"  <td colspan=2 class=xl6415134 dir=LTR width=144 style='border-left:none;\n" + 
				"  width:108pt'>重办审批</td>\n" + 
				"  <td colspan=3 class=xl6415134 dir=LTR width=216 style='border-left:none;\n" + 
				"  width:162pt'>已归档</td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td height=19 class=xl6515134 style='height:14.25pt;border-top:none;\n" + 
				"  border-left:none'>提交到分派</td>\n" + 
				"  <td class=xl6515134 style='border-top:none;border-left:none'>直接回复</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>撤回</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>呈报上级</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>回退</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>局内转移</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>强制回收</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>请求审批</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>消费通回退</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>直接交办</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>逐级分派</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>批示</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>撤回</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>强制回收</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>审批</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>直接交办</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>不受理</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>撤回</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>回退</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>强制回收</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>受理</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>强制回收</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>申请办结</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>申请挂起</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>申请结案</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>申请延时</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>申请中止</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>不同意</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>撤回</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>同意</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>不同意</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>撤回</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>同意</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>不同意</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>同意</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>撤回</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>同意</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>不同意</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>撤回</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>同意</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>不同意</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>撤回</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>同意</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>不同意</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>撤回</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>同意</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>撤销</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>强制回收</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>驳回</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>撤回</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>归档</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>强制回收</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>申请重办</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>自动归档</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>不同意</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>撤回</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>同意</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>撤回</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>同意</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>强制回收</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>消费通回退</td>\n" + 
				"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>消费通结案</td>\n" + 
				" </tr>";

		$
				.ajax({
					type : "post",
					url : rootPath + "/queryXiaoBao/huanJie.do",
					data : "begintime=" + begintime + "&endtime=" + endtime 
							+ "&timess=" + new Date() + "&id=" + Math.random(),
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						var dataObj = eval("(" + data + ")");
						for (var i = 0; i < dataObj.length; i++) {
							htmls+=
								"<tr height=19 style='height:14.25pt'>\n" +
								"  <td height=19 class=xl6715134 width=130 style='height:14.25pt;border-top:\n" + 
								"  none;width:98pt'>"+dataObj[i].单位+"</td>\n" + 
								"  <td class=xl6715134 width=130 style='border-top:none;border-left:none;\n" + 
								"  width:98pt'>"+dataObj[i].处理人+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].预登记1+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].预登记2+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].待分派1+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].待分派2+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].待分派3+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].待分派4+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].待分派5+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].待分派6+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].待分派7+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].待分派8+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].待分派9+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].待决策+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].待审批1+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].待审批2+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].待审批3+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].待审批4+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].待受理1+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].待受理2+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].待受理3+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].待受理4+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].待受理5+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].办理中1+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].办理中2+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].办理中3+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].办理中4+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].办理中5+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].办理中6+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].办结确认1+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].办结确认2+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].办结确认3+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].中止审批1+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].中止审批2+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].中止审批3+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].挂起审批1+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].挂起审批2+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].回收审批1+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].回收审批2+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].延时审批1+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].延时审批2+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].延时审批3+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].办结审核1+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].办结审核2+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].办结审核3+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].不受理审核1+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].不受理审核2+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].不受理审核3+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].已挂起1+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].已挂起2+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].待归档1+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].待归档2+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].待归档3+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].待归档4+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].待归档5+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].待归档6+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].驳回审批1+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].驳回审批2+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].驳回审批3+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].重办审批1+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].重办审批2+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].已归档1+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].已归档2+"</td>\n" + 
								"  <td class=xl6615134 style='border-top:none;border-left:none'>"+dataObj[i].已归档3+"</td>\n" + 
								" </tr>";
						}
						htmls += '</table>' + '</div>';
					}
				});
		var newWim = open("huanjie_down.jsp", "_blank");
		window.setTimeout(function() {
			newWim.document.body.innerHTML = htmls;
		}, 500);
	}
</script>
</head>
<body>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
		titledisplay="true" width="100%" layout="table" showborder="false"
		layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100%"
		title="环节工作量统计表  统计条件">
		<div id="begintime" name='begintime' name='begintime' vtype="datefield"
			label="开始日期" labelAlign="right" labelwidth='100px' width="310"></div>

		<div id="endtime" name='endtime' vtype="datefield" label="截止日期"
			labelAlign="right" labelwidth='100px' width="310"></div>
		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom"
			align="center">
			<div name="query_button" vtype="button" text="查询"
				icon="../query/queryssuo.png" onclick="queryUrl();"></div>
			<div name="reset_button" vtype="button" text="重置"
				icon="../query/queryssuo.png" click="reset();"></div>
		</div>
	</div>
</body>
</html>
