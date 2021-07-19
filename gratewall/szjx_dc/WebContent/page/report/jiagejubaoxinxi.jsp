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
<script type="text/javascript">
	function reset() {
		$("#formpanel").formpanel('reset');
	}
	function queryUrl() {
		var aa = $("#formpanel").formpanel('getValue');
		var begintime = aa.data.begintime;
		var tbegintime = aa.data.tbegintime;
		var endtime = aa.data.endtime;
		var tendtime = aa.data.tendtime;
		if (tbegintime.length!=0&&tendtime.length!=0) {
			if (tbegintime>tendtime) {
				alert("同比开始日期大于截止日期!");
				return;
			}
		}
		if (begintime.length != 0 && endtime.length != 0) {
			if (begintime > endtime) {
				alert("初始日期大于截止日期!");
				return;
			}
			if (begintime == endtime) {
				alert("初始日期等于截止日期!");
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
				+ ','
				+ '\''
				+ tbegintime
				+ '\''
				+ ','
				+ '\''
				+ tendtime
				+ '\''
				+ ')" > 下     载</a>'
				+ '</div>'+
				"<div id=\"xxx_17061\" align=center x:publishsource=\"Excel\">\n" +
				"\n" + 
				"<table border=0 cellpadding=0 cellspacing=0 width=598 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:449pt'>\n" + 
				" <col width=72 style='width:54pt'>\n" + 
				" <col width=86 style='mso-width-source:userset;mso-width-alt:2752;width:65pt'>\n" + 
				" <col width=107 style='mso-width-source:userset;mso-width-alt:3424;width:80pt'>\n" + 
				" <col width=72 style='width:54pt'>\n" + 
				" <col class=xl6717061 width=117 style='mso-width-source:userset;mso-width-alt:\n" + 
				" 3744;width:88pt'>\n" + 
				" <col class=xl6717061 width=144 style='mso-width-source:userset;mso-width-alt:\n" + 
				" 4608;width:108pt'>\n" + 
				" <tr height=31 style='mso-height-source:userset;height:23.25pt'>\n" + 
				"  <td colspan=6 height=31 class=xl7717061 width=598 style='border-right:.5pt solid black;\n" + 
				"  height:23.25pt;width:449pt'>价格投诉举报信息采集表1</td>\n" + 
				" </tr>\n" + 
				" <tr height=18 style='height:13.5pt'>\n" + 
				"  <td colspan=4 height=18 class=xl8017061 width=337 style='height:13.5pt;\n" + 
				"  width:253pt'>项目</td>\n" + 
				"  <td class=xl6417061 width=117 style='border-top:none;border-left:none;\n" + 
				"  width:88pt'>数量</td>\n" + 
				"  <td class=xl6517061 width=144 style='border-top:none;border-left:none;\n" + 
				"  width:108pt'>同比</td>\n" + 
				" </tr>";

				$
				.ajax({
					type : "post",
					url : rootPath + "/quert12315Controller/jiaGeJuBao.do",
					data : "begintime=" + begintime + "&endtime=" + endtime+"&tbegintime="+tbegintime+"&tendtime="+tendtime
							+ "&id=" + Math.random(),
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						var dataObj = eval("(" + data + ")");
						var banjielv=((dataObj.办结件数.数量/(dataObj.合计.数量==0?1:dataObj.合计.数量))*100).toFixed(2);
						banjielv+='%';
						htmls+=
							"<tr height=18 style='height:13.5pt'>\n" +
							"  <td rowspan=9 height=164 class=xl7417061 width=72 style='border-bottom:.5pt solid black;\n" + 
							"  height:123.0pt;border-top:none;width:54pt'>受理情况</td>\n" + 
							"  <td rowspan=3 class=xl7417061 width=86 style='border-bottom:.5pt solid black;\n" + 
							"  border-top:none;width:65pt'>类别</td>\n" + 
							"  <td colspan=2 class=xl6317061 width=179 style='border-left:none;width:134pt'>价格政策咨询</td>\n" + 
							"  <td class=xl6517061 width=117 style='border-top:none;border-left:none;\n" + 
							"  width:88pt'>"+dataObj.价格政策咨询.数量+"</td>\n" + 
							"  <td class=xl6517061 width=144 style='border-top:none;border-left:none;\n" + 
							"  width:108pt'>"+dataObj.价格政策咨询.同比+"</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td colspan=2 height=18 class=xl6317061 width=179 style='height:13.5pt;\n" + 
							"  border-left:none;width:134pt'>违法行为举报</td>\n" + 
							"  <td class=xl6517061 width=117 style='border-top:none;border-left:none;\n" + 
							"  width:88pt'>"+dataObj.违法行为举报.数量+"</td>\n" + 
							"  <td class=xl6517061 width=144 style='border-top:none;border-left:none;\n" + 
							"  width:108pt'>"+dataObj.违法行为举报.同比+"</td>\n" + 
							" </tr>\n" + 
							" <tr height=20 style='mso-height-source:userset;height:15.0pt'>\n" + 
							"  <td colspan=2 height=20 class=xl6317061 width=179 style='height:15.0pt;\n" + 
							"  border-left:none;width:134pt'>合计</td>\n" + 
							"  <td class=xl6517061 width=117 style='border-top:none;border-left:none;\n" + 
							"  width:88pt'>"+(dataObj.合计.数量)+"</td>\n" + 
							"  <td class=xl6517061 width=144 style='border-top:none;border-left:none;\n" + 
							"  width:108pt'>"+dataObj.合计.同比+"</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td rowspan=6 height=108 class=xl7417061 width=86 style='border-bottom:.5pt solid black;\n" + 
							"  height:81.0pt;border-top:none;width:65pt'>来源</td>\n" + 
							"  <td colspan=2 class=xl6317061 width=179 style='border-left:none;width:134pt'>来信</td>\n" + 
							"  <td class=xl6617061 width=117 style='border-top:none;border-left:none;\n" + 
							"  width:88pt'>"+dataObj.来信.数量+"</td>\n" + 
							"  <td class=xl6517061 width=144 style='border-top:none;border-left:none;\n" + 
							"  width:108pt'>"+dataObj.来信.同比+"</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td colspan=2 height=18 class=xl6317061 width=179 style='height:13.5pt;\n" + 
							"  border-left:none;width:134pt'>来电</td>\n" + 
							"  <td class=xl6617061 width=117 style='border-top:none;border-left:none;\n" + 
							"  width:88pt'>"+dataObj.来电.数量+"</td>\n" + 
							"  <td class=xl6617061 width=144 style='border-top:none;border-left:none;\n" + 
							"  width:108pt'>"+dataObj.来电.同比+"</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td colspan=2 height=18 class=xl6317061 width=179 style='height:13.5pt;\n" + 
							"  border-left:none;width:134pt'>电子邮件</td>\n" + 
							"  <td class=xl6617061 width=117 style='border-top:none;border-left:none;\n" + 
							"  width:88pt'>"+dataObj.电子邮件.数量+"</td>\n" + 
							"  <td class=xl6517061 width=144 style='border-top:none;border-left:none;\n" + 
							"  width:108pt'>"+dataObj.电子邮件.同比+"</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td colspan=2 height=18 class=xl8117061 width=179 style='border-right:.5pt solid black;\n" + 
							"  height:13.5pt;border-left:none;width:134pt'>来访</td>\n" + 
							"  <td class=xl6617061 width=117 style='border-top:none;border-left:none;\n" + 
							"  width:88pt'>"+dataObj.来访.数量+"</td>\n" + 
							"  <td class=xl6517061 width=144 style='border-top:none;border-left:none;\n" + 
							"  width:108pt'>"+dataObj.来访.同比+"</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td rowspan=2 height=36 class=xl7417061 width=107 style='border-bottom:.5pt solid black;\n" + 
							"  height:27.0pt;border-top:none;width:80pt'>其中</td>\n" + 
							"  <td class=xl6317061 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>上级交办</td>\n" + 
							"  <td class=xl6617061 width=117 style='border-top:none;border-left:none;\n" + 
							"  width:88pt'>"+dataObj.上级交办.数量+"</td>\n" + 
							"  <td class=xl6517061 width=144 style='border-top:none;border-left:none;\n" + 
							"  width:108pt'>"+dataObj.上级交办.同比+"</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td height=18 class=xl6317061 width=72 style='height:13.5pt;border-top:none;\n" + 
							"  border-left:none;width:54pt'>部门转办</td>\n" + 
							"  <td class=xl6617061 width=117 style='border-top:none;border-left:none;\n" + 
							"  width:88pt'>"+dataObj.部门转办.数量+"</td>\n" + 
							"  <td class=xl6517061 width=144 style='border-top:none;border-left:none;\n" + 
							"  width:108pt'>"+dataObj.部门转办.同比+"</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td colspan=2 rowspan=4 height=72 class=xl6817061 width=158 style='border-right:\n" + 
							"  .5pt solid black;border-bottom:.5pt solid black;height:54.0pt;width:119pt'>办理情况</td>\n" + 
							"  <td colspan=2 class=xl6317061 width=179 style='border-left:none;width:134pt'>办结件数</td>\n" + 
							"  <td class=xl6517061 width=117 style='border-top:none;border-left:none;\n" + 
							"  width:88pt'>"+dataObj.办结件数.数量+"</td>\n" + 
							"  <td class=xl6517061 width=144 style='border-top:none;border-left:none;\n" + 
							"  width:108pt'>"+dataObj.办结件数.同比+"</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td colspan=2 height=18 class=xl6317061 width=179 style='height:13.5pt;\n" + 
							"  border-left:none;width:134pt'>办结率</td>\n" + 
							"  <td class=xl6517061 width=117 style='border-top:none;border-left:none;\n" + 
							"  width:88pt'>"+banjielv+"</td>\n" + 
							"  <td class=xl6517061 width=144 style='border-top:none;border-left:none;\n" + 
							"  width:108pt'>0%</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td rowspan=2 height=36 class=xl7417061 width=107 style='border-bottom:.5pt solid black;\n" + 
							"  height:27.0pt;border-top:none;width:80pt'>其中</td>\n" + 
							"  <td class=xl6317061 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>咨询办结件</td>\n" + 
							"  <td class=xl6517061 width=117 style='border-top:none;border-left:none;\n" + 
							"  width:88pt'>"+dataObj.咨询办结件数.数量+"</td>\n" + 
							"  <td class=xl6517061 width=144 style='border-top:none;border-left:none;\n" + 
							"  width:108pt'>"+dataObj.咨询办结件数.同比+"</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td height=18 class=xl6317061 width=72 style='height:13.5pt;border-top:none;\n" + 
							"  border-left:none;width:54pt'>举报办结件</td>\n" + 
							"  <td class=xl6517061 width=117 style='border-top:none;border-left:none;\n" + 
							"  width:88pt'>"+dataObj.举报办结件数.数量+"</td>\n" + 
							"  <td class=xl6517061 width=144 style='border-top:none;border-left:none;\n" + 
							"  width:108pt'>"+dataObj.举报办结件数.同比+"</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td colspan=2 rowspan=6 height=108 class=xl6817061 width=158\n" + 
							"  style='border-right:.5pt solid black;border-bottom:.5pt solid black;\n" + 
							"  height:81.0pt;width:119pt'>查处情况</td>\n" + 
							"  <td colspan=2 class=xl6317061 width=179 style='border-left:none;width:134pt'>协调办结案件</td>\n" + 
							"  <td class=xl6517061 width=117 style='border-top:none;border-left:none;\n" + 
							"  width:88pt'>"+dataObj.协调办结案件.数量+"</td>\n" + 
							"  <td class=xl6517061 width=144 style='border-top:none;border-left:none;\n" + 
							"  width:108pt'>"+dataObj.协调办结案件.同比+"</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td colspan=2 height=18 class=xl6317061 width=179 style='height:13.5pt;\n" + 
							"  border-left:none;width:134pt'>查处办结案件</td>\n" + 
							"  <td class=xl6517061 width=117 style='border-top:none;border-left:none;\n" + 
							"  width:88pt'>"+dataObj.查处办结结案.数量+"</td>\n" + 
							"  <td class=xl6617061 width=144 style='border-top:none;border-left:none;\n" + 
							"  width:108pt'>"+dataObj.查处办结结案.同比+"</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td colspan=2 height=18 class=xl6317061 width=179 style='height:13.5pt;\n" + 
							"  border-left:none;width:134pt'>退还金额</td>\n" + 
							"  <td class=xl6517061 width=117 style='border-top:none;border-left:none;\n" + 
							"  width:88pt'>　</td>\n" + 
							"  <td class=xl6517061 width=144 style='border-top:none;border-left:none;\n" + 
							"  width:108pt'>　</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td colspan=2 height=18 class=xl6317061 width=179 style='height:13.5pt;\n" + 
							"  border-left:none;width:134pt'>没收违法所得</td>\n" + 
							"  <td class=xl6517061 width=117 style='border-top:none;border-left:none;\n" + 
							"  width:88pt'>　</td>\n" + 
							"  <td class=xl6517061 width=144 style='border-top:none;border-left:none;\n" + 
							"  width:108pt'>　</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td colspan=2 height=18 class=xl6317061 width=179 style='height:13.5pt;\n" + 
							"  border-left:none;width:134pt'>罚款</td>\n" + 
							"  <td class=xl6517061 width=117 style='border-top:none;border-left:none;\n" + 
							"  width:88pt'>　</td>\n" + 
							"  <td class=xl6517061 width=144 style='border-top:none;border-left:none;\n" + 
							"  width:108pt'>　</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td colspan=2 height=18 class=xl6317061 width=179 style='height:13.5pt;\n" + 
							"  border-left:none;width:134pt'>经济制裁总额</td>\n" + 
							"  <td class=xl6517061 width=117 style='border-top:none;border-left:none;\n" + 
							"  width:88pt'>　</td>\n" + 
							"  <td class=xl6517061 width=144 style='border-top:none;border-left:none;\n" + 
							"  width:108pt'>　</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td colspan=2 rowspan=10 height=180 class=xl6817061 width=158\n" + 
							"  style='border-right:.5pt solid black;border-bottom:.5pt solid black;\n" + 
							"  height:135.0pt;width:119pt'>价格举报工作机构情况</td>\n" + 
							"  <td rowspan=4 class=xl7417061 width=107 style='border-bottom:.5pt solid black;\n" + 
							"  border-top:none;width:80pt'>设立举报中心</td>\n" + 
							"  <td class=xl6317061 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>省级</td>\n" + 
							"  <td class=xl6617061 width=117 style='border-top:none;border-left:none;\n" + 
							"  width:88pt'>　</td>\n" + 
							"  <td class=xl6517061 width=144 style='border-top:none;border-left:none;\n" + 
							"  width:108pt'>　</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td height=18 class=xl6317061 width=72 style='height:13.5pt;border-top:none;\n" + 
							"  border-left:none;width:54pt'>地级</td>\n" + 
							"  <td class=xl6617061 width=117 style='border-top:none;border-left:none;\n" + 
							"  width:88pt'>　</td>\n" + 
							"  <td class=xl6517061 width=144 style='border-top:none;border-left:none;\n" + 
							"  width:108pt'>　</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td height=18 class=xl6317061 width=72 style='height:13.5pt;border-top:none;\n" + 
							"  border-left:none;width:54pt'>县级</td>\n" + 
							"  <td class=xl6617061 width=117 style='border-top:none;border-left:none;\n" + 
							"  width:88pt'>　</td>\n" + 
							"  <td class=xl6517061 width=144 style='border-top:none;border-left:none;\n" + 
							"  width:108pt'>　</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td height=18 class=xl6317061 width=72 style='height:13.5pt;border-top:none;\n" + 
							"  border-left:none;width:54pt'>合计</td>\n" + 
							"  <td class=xl6617061 width=117 style='border-top:none;border-left:none;\n" + 
							"  width:88pt'>　</td>\n" + 
							"  <td class=xl6517061 width=144 style='border-top:none;border-left:none;\n" + 
							"  width:108pt'>　</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='mso-height-source:userset;height:13.5pt'>\n" + 
							"  <td rowspan=4 height=72 class=xl7417061 width=107 style='border-bottom:.5pt solid black;\n" + 
							"  height:54.0pt;border-top:none;width:80pt'>举报途径</td>\n" + 
							"  <td class=xl6317061 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>电话</td>\n" + 
							"  <td class=xl6617061 width=117 style='border-top:none;border-left:none;\n" + 
							"  width:88pt'>　</td>\n" + 
							"  <td class=xl6517061 width=144 style='border-top:none;border-left:none;\n" + 
							"  width:108pt'>　</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td height=18 class=xl6317061 width=72 style='height:13.5pt;border-top:none;\n" + 
							"  border-left:none;width:54pt'>电子邮件</td>\n" + 
							"  <td class=xl6617061 width=117 style='border-top:none;border-left:none;\n" + 
							"  width:88pt'>　</td>\n" + 
							"  <td class=xl6517061 width=144 style='border-top:none;border-left:none;\n" + 
							"  width:108pt'>　</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td height=18 class=xl6317061 width=72 style='height:13.5pt;border-top:none;\n" + 
							"  border-left:none;width:54pt'>举报箱</td>\n" + 
							"  <td class=xl6617061 width=117 style='border-top:none;border-left:none;\n" + 
							"  width:88pt'>　</td>\n" + 
							"  <td class=xl6517061 width=144 style='border-top:none;border-left:none;\n" + 
							"  width:108pt'>　</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td height=18 class=xl6317061 width=72 style='height:13.5pt;border-top:none;\n" + 
							"  border-left:none;width:54pt'>合计</td>\n" + 
							"  <td class=xl6617061 width=117 style='border-top:none;border-left:none;\n" + 
							"  width:88pt'>　</td>\n" + 
							"  <td class=xl6517061 width=144 style='border-top:none;border-left:none;\n" + 
							"  width:108pt'>　</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td colspan=2 height=18 class=xl6317061 width=179 style='height:13.5pt;\n" + 
							"  border-left:none;width:134pt'>举报用车（辆）</td>\n" + 
							"  <td class=xl6617061 width=117 style='border-top:none;border-left:none;\n" + 
							"  width:88pt'>　</td>\n" + 
							"  <td class=xl6517061 width=144 style='border-top:none;border-left:none;\n" + 
							"  width:108pt'>　</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td colspan=2 height=18 class=xl6317061 width=179 style='height:13.5pt;\n" + 
							"  border-left:none;width:134pt'>专职工作人员</td>\n" + 
							"  <td class=xl6617061 width=117 style='border-top:none;border-left:none;\n" + 
							"  width:88pt'>　</td>\n" + 
							"  <td class=xl6517061 width=144 style='border-top:none;border-left:none;\n" + 
							"  width:108pt'>　</td>\n" + 
							" </tr>";

						htmls += '</table>' + '</div>';
					}
				});
				var newWim = open("jiagejubaoxinxi_down.jsp", "_blank");
				window.setTimeout(function() {
					newWim.document.body.innerHTML = htmls;
				}, 1000);		
	}
</script>
</head>
<body>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
		titledisplay="true" width="100%" layout="table" showborder="false"
		layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100%"
		title="价格投诉举报信息采集表1  统计条件">
		<div id="begintime" name='begintime' name='endtime' vtype="datefield"
			label="开始日期" labelAlign="right" labelwidth='100px' width="310"></div>

		<div id="endtime" name='endtime' vtype="datefield" label="截止日期"
			labelAlign="right" labelwidth='100px' width="310"></div>
			
		<div id="tbegintime" name='tbegintime' valuetip='默认为上年同期...' name='tbegintime' vtype="datefield"
			label="同比开始日期" labelAlign="right" labelwidth='100px' width="310"></div>
		<div id="tendtime" name='tendtime' valuetip='默认为上年同期...' vtype="datefield" label="同比截止日期"
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
