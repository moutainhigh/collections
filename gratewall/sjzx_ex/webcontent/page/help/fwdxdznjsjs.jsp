<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<%-- template master-detail-3/frame-detail-update.jsp --%>

<freeze:html>
<head>

<style type="text/css">
#one {
	height: 180px;
	border-bottom: 1px solid #c2c2c2;
	border-right: 1px solid #c2c2c2;
	border-left: 1px solid #c2c2c2;
	width: 85%;
	padding: 5px;
	font-family: 宋体;
	line-height: 150%;
	font-size: 11.0pt;
	text-align: left;
	color: #555555;
}

#two {
	height: 180px;
	border-bottom: 1px solid #c2c2c2;
	border-right: 1px solid #c2c2c2;
	border-left: 1px solid #c2c2c2;
	width: 85%;
	padding: 5px;
	font-family: 宋体;
	line-height: 150%;
	font-size: 11.0pt;
	text-align: left;
	color: #555555;
}

#three {
	height: 180px;
	border-bottom: 1px solid #c2c2c2;
	border-right: 1px solid #c2c2c2;
	border-left: 1px solid #c2c2c2;
	width: 85%;
	padding: 5px;
	font-family: 宋体;
	line-height: 150%;
	font-size: 11.0pt;
	text-align: left;
	color: #555555;
}

.top {
	width: 95%;
	padding: 5px;
	color: #555555;
	background-color: #f2f2f2;
	font-family: 宋体;
	line-height: 150%;
	font-size: 11.0pt;
	height: 25%;
	text-align: left;
}

.item {
	color: #555555;
	border: 1px solid #c2c2c2;
	background-color: #93b9da;
	width: 85%;
	height: 40px;
	line-height: 135px;
}

</style>
<script type="text/javascript">
	
</script>
</head>
<freeze:body>
	<freeze:title caption="" />
	<freeze:errors />
	<table align="center" width="96%" height="90%">
		<tr style="height: 150px;">
			<td colspan=3 style="padding-top: 20px;" valign="top" align="center">
				<div class="top">
					<p style="font: 120%, 宋体; text-align: center; display: none;">服务对象介绍</p>
					&nbsp&nbsp&nbsp&nbsp服务对象是指通过采集服务、共享服务可接入数据交换平台的单位或系统。
					主要包括三类对象：内部系统、区县分局、外部系统。数据交换平台对服务对象是委办局的名称进行了规范，
					28个委办局的名称固定为：工商局、地税局、国土局、人保局、安监局、质监局、卫生局、国税局、检验检疫局、
					财政局、公安局、邮政局、交通委、知识产权局、审计局、新闻出版局、水务局、城管执法局、环保局、高法、交通委、
					住房城乡建设委、规划委、旅游委、发展改革委、文化执法总队、市政市容委、深圳海关。
				</div>
			</td>
		</tr>

		<tr>
			<td align="center" style="width: 32%; padding-top: 20px;"
				valign="top">
				<div style="padding-bottom: 40px">
					<div class="item">
						<Table style="color: #fff;">
						 <tr>
						  <td><img src="/page/help/helpRes/nbxt.png" style="margin-top: 2px;"/></td>
						  <td><b style="margin-top: 10px;">内部系统</b></td>
						 </tr>
						</Table>
					</div>


					<div id="one">&nbsp&nbsp&nbsp&nbsp内部系统指深圳市市场和质量监督管理委员会内部为各业务部门开发的业务系统。
						目前，内部系统共有13个，主要包括：登记系统、网上登记系统、年检系统、案件系统、12315系统、
						案件系统、信用系统、网监系统、广告监测系统,农资监管系统、私营个体经济网、企业E网通等 。</div>
				</div>
			</td>
			<td align="center" style="width: 32%; padding-top: 20px;"
				valign="top">
				<div style="padding-bottom: 40px">
					<div class="item">
						<Table style="color: #fff;">
						 <tr>
						  <td><img src="/page/help/helpRes/wbxt.png" style="margin-top: 2px;"/></td>
						  <td><b style="margin-top: 10px;">外部系统</b></td>
						 </tr>
						</Table>
					</div>
					<div id="two">
						&nbsp&nbsp&nbsp&nbsp外部系统指通过数据交换平台和深圳市市场和质量监督管理委员会进行数据交换的外部单位或外部业务系统，
						目前约13个，主要包括：深圳市经信委资源中心、深圳市地税系统、深圳市国税系统、深圳市高法、深圳市人力社保局、
						深圳市质监局、深圳市住建委、深圳市海淀信息办、深圳市西城信息办等。</div>
				</div>
			</td>


			<td align="center" style="width: 32%;padding-top: 20px;" valign="top">
				<div style="padding-bottom: 40px">
					<div class="item">
						<Table style="color: #fff;">
						 <tr>
						  <td><img src="/page/help/helpRes/qxfj.png" style="margin-top: 2px;"/></td>
						  <td><b style="margin-top: 10px;">区县分局</b></td>
						 </tr>
						</Table>
					</div>
					<div id="three">
						&nbsp&nbsp&nbsp&nbsp区县分局指隶属于深圳市工商局的各分局，共18个，包括：海淀分局、朝阳分局、
						西城分局、东城分局、丰台分局、房山分局、密云分局、顺义分局、门头沟分局、通州分局、延庆分局、昌平分局、怀柔分局等。</div>
				</div>
			</td>
		</tr>
	</table>

</freeze:body>
</freeze:html>


