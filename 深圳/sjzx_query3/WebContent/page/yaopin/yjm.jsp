<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.gwssi.optimus.plugin.auth.model.User"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<%
	String contextpath = request.getContextPath();
%>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Expires" content="0">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-control" content="no-cache">
<meta http-equiv="Cache" content="no-cache">
<meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no" />
<meta charset="utf-8" />
<title>药监主体信息查询</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/static/bt/css/bootstrap.min.css"></link>
<script src="<%=request.getContextPath()%>/static/js/yaopin/jquery-1-11-2.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/bt/js/bootstrap.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/sczt/base64.js" type="text/javascript"></script>

<style>
.header {
	background: url(../../static/images/other/banner1.jpg) center top
		repeat-x;
	height: 65px;
	overflow: hidden;
}

.header .banner {
	background: url(../../static/images/other/banner.jpg) center top
		no-repeat;
	margin: 0 auto;
	width: 1000px;
	height: 65px;
}

.userInfo {
	color: #fff;
	font-family: '微软雅黑';
}

.container  #descInfos, .container  #titleInfos {
	font-size: 12px;
	text-align: center;
	font-family: "宋体";
}

.container table td {
	font-size: 14px;
	/*  white-space: nowrap; */
	width: 80px;
	font-size: 12px;
	font-family: "宋体";
}

.container table tr:hover {
	background: #d9edf7;
}

.table>tbody>tr>td {
	border: 0px;
	color: #333333;
	font-size: 12px;
	font-family: "宋体";
	word-wrap: break-word;
	border-right: #f6f6f6 0px solid; /*  //显示右边框为1px，如果不想显示就为0px */
	border-top: #f9f9f9 1px solid; /* //显示上边框为1px，如果不想显示就为0px */
	border-left: #f9f9f9 1px solid; /* //显示左边框为1px，如果不想显示就为0px */
	border-bottom: #f5f5f5 0px solid; /* //显下右边框为1px，如果不想显示就为0px  */
}

.classQH {
	background: rgb(239, 246, 250);
}

.textLeft {
	text-align: right;
}

.panel-info .panel-heading {
	color: #31708f;
	background-color: #d9edf7;
	border-color: #bce8f1;
}

.displayNone {
	display: none;
}

#descInfos {
	padding: 15px 0
}


#descInfos a {
	text-decoration: none;
	width: 50%;
	display: block;
	float: left;
	text-align: center;
	padding:5px;
	color:#367696;
	font: 14px "Microsoft YaHei", helvetica,arial,verdana,sans-serif;
}
#descInfos a:hover{
	color:#ffa800;
	background-color: #D6E9F0;
}

.bgr{border-right:2px solid #000;}

.selects{background:#bce8f1;}
</style>
</head>
<body>
	<%
		User user = (User) request.getSession().getAttribute("user");
		String username = "";
		if (user == null) {
			username = "未登录用户";
		} else {
			username = user.getUserName();
		}
	%>
	<div id="header" class="header">
		<div class="banner" style="position: relative;">
			<div class="userInfo" style="position: absolute; right: 10px; top: 47px;">
				当前用户:<%=username%>&nbsp;&nbsp;&nbsp;&nbsp;<span id="content" class="userInfo"></span>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var date, txt = "";
		var week = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
		date = new Date();
		txt += date.getFullYear() + "年" + (date.getMonth() + 1) + "月"
				+ date.getDate() + "日  ";
		txt += week[date.getDay()];
		document.getElementById("content").innerText = txt;
	</script>

	<div class="container" style="margin-top: 15px">
		<div class="row clearfix">
			<div class="col-md-3 column">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h3 class="panel-title" id="titleInfos"></h3>
					</div>
					<div class="panel-body" style="text-align:center" id="descInfos" ></div>
				</div>
			</div>
			<div class="col-md-9 column">
				<div class="panel panel-info">

					<div class='wrapDetail'>
						<div class="panel-heading">
							<h3 class="panel-title" id="deitailInfoTitle"></h3>
						</div>
						<div class="panel-body">
							<table class="table table-bordered">

								<tbody id=detailInfo>
								</tbody>
							</table>
						</div>
					</div>
					
					<div class='wrapDetail displayNone'>
						<div class="panel-heading">
							<h3 class="panel-title" id="deitail2InfoTitle"></h3>
						</div>
						<div class="panel-body">
							<table class="table table-bordered">

								<tbody id="detail2Info">
								</tbody>
							</table>
						</div>
					</div>

				</div>
			</div>

		</div>
	</div>

<script src="<%=request.getContextPath()%>/static/js/yaopin/myLoading.js" type="text/javascript"></script>
	<script type="text/javascript">
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

		function getUrlParam(name) {
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
			var r = window.location.search.substr(1).match(reg); //匹配目标参数
			if (r != null)
				return unescape(r[2]);
			return null; //返回参数值
		}

		$(window).load(function() {
			var pripid = getUrlParam("pripid");
			
			var entid = getUrlParam("entid");
			var id = getUrlParam("id");; ///许可证书ID
			
			var url = window.location.search;
			var str = decodeURIComponent(url.split("&")[3].split("=")[1]);
			var entname = str; ///许可证书ID
			
			$("#titleInfos").html("");
			$("#childInfos").html("");
			$("#descInfos").empty();
			$("#detailInfo").empty();
			$("#detail2Info").empty();
			
			if (isNotEmpty(pripid)||isNotEmpty(entid)) {
				ajaxLoading(id);
				ajaxYaojianZhuTiInfo(entid);
			} else {
				alert("查询参数有误");
			}
			
			
			$("#descInfos").html("<a href='javascript:;' class='queryEnt bgr selects' data-entname='"+entname+"' data-eid='"+entid+"'>主体信息</a><a id='mentions' class='queryEnt' href='javascript:;' data-beianId='"+id+"'>备案信息</a>");
			
			$('.queryEnt').off("click").on("click",function(){
				var _this = $(this);
				_this.addClass("selects").siblings().removeClass("selects");
				$(".wrapDetail").eq(_this.index()).removeClass("displayNone").siblings().addClass("displayNone").removeClass("selects");
			});

			
		});

		
		
		
		
		
		
		function ajaxYaojianZhuTiInfo(id){
			var html ="";
			
			
			$.ajax({
				url:"../../beiAn/queryYaoJianSysMainDetail.do",
				data:{
					id : id
				},
				dataType : 'json',
				beforeSend:function(data){
					$("#detail2Info").empty();
					var html ="<tr><td>系统加载中，请稍后.......</td></tr>";
					$("#detail2Info").append(html);
				},
				success:function(dataStr){
					var data = dataStr.data[0].data[0];
					var entName = "";
					if (isNotEmpty(data.entname)) {
						entName = data.entname;
					}

					var certNo = ""
					if (isNotEmpty(data.certno)) {
						certNo = data.certno;
					}
					var legalPerson = "";
					if (isNotEmpty(data.legalPerson)) {
						legalPerson = data.legalPerson;
					}

					var businessScope = "";
					if (isNotEmpty(data.businessScope)) {
						businessScope = data.businessScope;
					}
					var zhusuo = "";
					if (isNotEmpty(data.registerAddress)) {
						zhusuo = data.registerAddress;
					}
					

					var entId = "";

					if (isNotEmpty(data.entid)) {
						entId = data.entid;
					}

					var chenlidate = "";
					if (isNotEmpty(data.chenlidate)) {
						chenlidate = data.chenlidate;
					}
					var legalPerson = "";
					if (isNotEmpty(data.legperson)) {
						legalPerson = data.legperson;
					}
					
					var resperson ="";
					if (isNotEmpty(data.resperson)) {
						resperson = data.resperson;
					}
					var inArea ="";
					if (isNotEmpty(data.inArea)) {
						inArea = data.inArea;
					}
					
					var mailingAddress = "";
					if (isNotEmpty(data.mailingAddress)) {
						mailingAddress = data.mailingAddress;
					}
					var orgCode = "";
					if (isNotEmpty(data.orgcode)) {
						orgCode = data.orgcode;
					}
					var pripid = "";
					if (isNotEmpty(data.pripid)) {
						pripid = data.pripid;
					}
					var registerAddress = "";
					if (isNotEmpty(data.registeraddress)) {
						registerAddress = data.registeraddress;
					}
					var registerDate = "";
					if (isNotEmpty(data.registerdate)) {
						registerDate = data.registerdate;
					}
					var regorg = "";
					if (isNotEmpty(data.regorg)) {
						regorg = data.regorg;
					}
					var regorgCn = "";
					if (isNotEmpty(data.regorgCn)) {
						regorgCn = data.regorgCn;
					}
					var responsiblePerson = "";
					if (isNotEmpty(data.resperson)) {
						responsiblePerson = data.resperson;
					}
					var contactPhone = "";
					if (isNotEmpty(data.contactPhone)) {
						contactPhone = data.contactPhone;
					}
					var validPeriodStart = "";
					if (isNotEmpty(data.yystart)) {
						validPeriodStart = data.yystart;
					}
					var validPeriodEnd = ""
					if (isNotEmpty(data.yyend)) {
						validPeriodEnd = data.yyend;
					}
					var validstate = "";
					if (isNotEmpty(data.validstate)) {
						validstate = data.validstate;
					}
					
					//注册资本
					var registerCapital ="";
					if (isNotEmpty(data.registerCapital)) {
						registerCapital = data.registerCapital+'万元';
					}
					//单位人数
					var employeeNum="";
					if (isNotEmpty(data.employeeNum)) {
						employeeNum = data.employeeNum;
					}
					//实收资本
					var factCapital ="";
					if (isNotEmpty(data.factCapital)) {
						factCapital = data.factCapital +'万元';
					}
					
					var entState ='';
					if (isNotEmpty(data.entState)) {
						entState = data.entState ;
					}
					
					
					$("#deitailInfoTitle").html("主体名称： " +entName );
					
					html += '<tr >';
					html += '<td class="textLeft">企业名称:</td>';
					html += '<td>' + entName + '</td>';
					html += '<td class="textLeft">统一信用代码:</td>';
					html += '<td>' + certNo + '</td>';
					html += '</tr>';
					
					html += '<tr class="classQH">';
					html += '<td class="textLeft">住所:</td>';
					html += '<td>' + zhusuo+ '</td>';
					html += '<td class="textLeft">注册号:</td>';
					html += '<td>' + validstate + '</td>';
					html += '</tr>';
					
					html += '<tr>';
					html += '<td class="textLeft">法定代表人:</td>';
					html += '<td>' + legalPerson  + '</td>';
					html += '<td class="textLeft">所属区域:</td>';
					html += '<td>' + inArea + '</td>';
					html += '</tr>';

					html += '<tr class="classQH">';
					html += '<td class="textLeft">企业负责人:</td>';
					html += '<td>' + resperson + '</td>';
					html += '<td class="textLeft">成立日期: </td>';
					html += '<td>' + chenlidate + '</td>';
					html += '</tr>';
					
					
					
					html += '<tr >';
					html += '<td class="textLeft">经营范围:</td>';
					html += '<td>' + businessScope + '</td>';
					html += '<td class="textLeft">营业期限自: </td>';
					html += '<td>' + validPeriodStart + '</td>';
					html += '</tr>';
					
					
					html += '<tr class="classQH">';
					html += '<td class="textLeft">经济性质:</td>';
					html += '<td></td>';
					html += '<td class="textLeft">营业期限至: </td>';
					html += '<td>' + validPeriodEnd + '</td>';
					html += '</tr>';
					
					
					
					html += '<tr >';
					html += '<td class="textLeft">公司类型:</td>';
					html += '<td></td>';
					html += '<td class="textLeft">联系电话: </td>';
					html += '<td>' + contactPhone + '</td>';
					html += '</tr>';
					
					
					html += '<tr class="classQH">';
					html += '<td class="textLeft">企业状态:</td>';
					html += '<td>'+entState+'</td>';
					html += '<td class="textLeft">单位人数: </td>';
					html += '<td>' + employeeNum + '</td>';
					html += '</tr>';
					
					
					html += '<tr class="classQH">';
					html += '<td class="textLeft">注册资本:</td>';
					html += '<td>'+registerCapital+'</td>';
					html += '<td class="textLeft">单位通讯地址: </td>';
					html += '<td>' + mailingAddress + '</td>';
					html += '</tr>';
					
					
					
					html += '<tr class="classQH">';
					html += '<td class="textLeft">实收资本:</td>';
					html += '<td  colspan="3">'+factCapital+'</td>';
					html += '</tr>';
					
					
					$("#detailInfo").html(html);
				},
				error:function(data){
					
				}
				
			});
			
			
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		function ajaxLoading(id) {
			$("#detail2Info").empty();
			var html = "";
			$.ajax({
				url : "../../beiAn/getDetail.do",
				data : {
					/* pripid : pripid,
					regno : '',
					entid : '', */
					id : id
				},
				type : 'post',
				dataType : 'json',
				beforeSend : function(data) {
						var html ="<tr><td>系统加载中，请稍后.......</td></tr>";
						$("#detail2Info").append(html);
						
				},
				success : function(dataStr) {
					var data = dataStr.data[0].data[0];
					if (isEmpty(data)) {
						alert("暂无企业信息");
						window.close();
						return;
					}

					

					var entName = "";
					if (isNotEmpty(data.entName)) {
						entName = data.entName;
					}

					var certNo = ""
					if (isNotEmpty(data.certNo)) {
						certNo = data.certNo;
					}
					var legalPerson = "";
					if (isNotEmpty(data.legalPerson)) {
						legalPerson = data.legalPerson;
					}

					var businessScope = "";
					if (isNotEmpty(data.businessScope)) {
						businessScope = data.businessScope;
					}
					var certificateType = ""; //===决定经营方式
					var operatingMode = ""; //经营方式
					var beiAnType = "";
					if (isNotEmpty(data.certificateType)) {
						certificateType = data.certificateType;
						beiAnType = data.certificateType;
					}
					
					
					if (isNotEmpty(data.operatingMode)) {
						operatingMode = data.operatingMode;
					}
					

					var entId = "";

					if (isNotEmpty(data.entId)) {
						entId = data.entId;
					}

					var issueDate = "";
					if (isNotEmpty(data.issueDate)) {
						issueDate = data.issueDate;
					}
					var issueDept = "";
					if (isNotEmpty(data.issueDept)) {
						issueDept = data.issueDept;
					}
					var legalPerson = "";
					if (isNotEmpty(data.legalPerson)) {
						legalPerson = data.legalPerson;
					}
					var mailingAddress = "";
					if (isNotEmpty(data.mailingAddress)) {
						mailingAddress = data.mailingAddress;
					}
					var orgCode = "";
					if (isNotEmpty(data.orgCode)) {
						orgCode = data.orgCode;
					}
					var pripid = "";
					if (isNotEmpty(data.pripid)) {
						pripid = data.pripid;
					}
					var qualityResponsible = "";
					if (isNotEmpty(data.qualityResponsible)) {
						qualityResponsible = data.qualityResponsible;
					}
					var registerAddress = "";
					if (isNotEmpty(data.registerAddress)) {
						registerAddress = data.registerAddress;
					}
					var registerDate = "";
					if (isNotEmpty(data.registerDate)) {
						registerDate = data.registerDate;
					}
					var regorg = "";
					if (isNotEmpty(data.regorg)) {
						regorg = data.regorg;
					}
					var regorgCn = "";
					if (isNotEmpty(data.regorgCn)) {
						regorgCn = data.regorgCn;
					}
					var responsiblePerson = "";
					if (isNotEmpty(data.responsiblePerson)) {
						responsiblePerson = data.responsiblePerson;
					}
					var typeName = "";
					if (isNotEmpty(data.typeName)) {
						typeName = data.typeName;
					}
					var validPeriodStart = "";
					if (isNotEmpty(data.validPeriodStart)) {
						validPeriodStart = data.validPeriodStart;
					}
					var validPeriodEnd = ""
					if (isNotEmpty(data.validPeriodEnd)) {
						validPeriodEnd = data.validPeriodEnd;
					}
					var validstate = "";
					if (isNotEmpty(data.validstate)) {
						validstate = data.validstate;
					}

					
					
					if (beiAnType == "01") {

					} else if (beiAnType == "02") {

					} else if (beiAnType == "04") {

					} else if (beiAnType == "05") {
						html += '<tr >';
						html += '<td class="textLeft">企业名称:</td>';
						html += '<td>' + entName + '</td>';
						html += '<td class="textLeft">许可证号:</td>';
						html += '<td>' + certNo + '</td>';
						html += '</tr>';
						
						html += '<tr class="classQH">';
						html += '<td class="textLeft">法定代表人:</td>';
						html += '<td>' + legalPerson + '</td>';
						html += '<td class="textLeft">证照状态:</td>';
						html += '<td>' + validstate + '</td>';
						html += '</tr>';
						
						html += '<tr>';
						html += '<td class="textLeft">企业负责人:</td>';
						html += '<td>' + responsiblePerson + '</td>';
						html += '<td class="textLeft">发证日期:</td>';
						html += '<td>' + issueDate + '</td>';
						html += '</tr>';

						html += '<tr class="classQH">';
						html += '<td class="textLeft">质量负责人:</td>';
						html += '<td>' + qualityResponsible + '</td>';
						html += '<td class="textLeft">发证机关: </td>';
						html += '<td>' + regorgCn + '</td>';
						html += '</tr>';

						html += '<tr>';
						html += '<td class="textLeft">仓库地址:</td>';
						html += '<td  colspan="3">' + mailingAddress + '</td>';
						html += '</tr>';
						
						
						html += '<tr class="classQH">';
						html += '<td class="textLeft">经营范围:</td>';
						html += '<td  colspan="3">' + businessScope + '</td>';
						html += '</tr>';
					} else if (beiAnType == "06") {
						html += '<tr >';
						html += '<td class="textLeft">企业名称:</td>';
						html += '<td>' + entName + '</td>';
						html += '<td class="textLeft">许可证号:</td>';
						html += '<td>' + certNo + '</td>';
						html += '</tr>';
						
						
						html += '<tr class="classQH">';
						html += '<td class="textLeft">法定代表人:</td>';
						html += '<td>' + legalPerson + '</td>';
						html += '<td class="textLeft">证照状态:</td>';
						html += '<td>' + validstate + '</td>';
						html += '</tr>';
						
						
						html += '<tr >';
						html += '<td class="textLeft">企业负责人:</td>';
						html += '<td>' + responsiblePerson + '</td>';
						html += '<td class="textLeft">发证日期:</td>';
						html += '<td>' + issueDate + '</td>';
						html += '</tr>';

						html += '<tr class="classQH">';
						html += '<td class="textLeft">住所:</td>';
						html += '<td>' + registerAddress + '</td>';
						html += '<td class="textLeft">发证部门 :</td>';
						html += '<td>' + regorgCn + '</td>';
						html += '</tr>';

						html += '<tr >';
						html += '<td class="textLeft">有效期限自:</td>';
						html += '<td>' + validPeriodStart + '</td>';
						html += '<td class="textLeft">有效期限至: </td>';
						html += '<td>' + validPeriodEnd + '</td>';
						html += '</tr>';

						html += '<tr class="classQH">';
						html += '<td class="textLeft">经营场所:</td>';
						html += '<td>' + mailingAddress + '</td>';
						html += '<td class="textLeft">经营方式 :</td>';
						html += '<td>' + operatingMode + '</td>';
						html += '</tr>';

						html += '<tr>';
						html += '<td class="textLeft">库房地址:</td>';
						html += '<td  colspan="3">' + mailingAddress + '</td>';
						html += '</tr>';
						html += '<tr>';
						html += '<td class="textLeft">经营范围:</td>';
						html += '<td  colspan="3">' + businessScope + '</td>';
						html += '</tr>';

					} else if (beiAnType == "07") {

						html += '<tr >';
						html += '<td class="textLeft">企业名称:</td>';
						html += '<td>' + entName + '</td>';
						html += '<td class="textLeft">许可证号:</td>';
						html += '<td>' + certNo + '</td>';
						html += '</tr>';
						
						html += '<tr class="classQH">';
						html += '<td class="textLeft">法定代表人:</td>';
						html += '<td>' + legalPerson + '</td>';
						html += '<td class="textLeft">证照状态:</td>';
						html += '<td>' + certNo + '</td>';
						html += '</tr>';
						
						html += '<tr >';
						html += '<td class="textLeft">企业负责人:</td>';
						html += '<td>' + responsiblePerson + '</td>';
						html += '<td class="textLeft">发证日期:</td>';
						html += '<td>' + issueDate + '</td>';
						html += '</tr>';

						html += '<tr class="classQH">';
						html += '<td class="textLeft">经营方式:</td>';
						html += '<td>' + validPeriodEnd + '</td>';
						html += '<td class="textLeft">发证机关: </td>';
						html += '<td>' + regorgCn + '</td>';
						html += '</tr>';

						html += '<tr >';
						html += '<td class="textLeft">有效期限自:</td>';
						html += '<td>' + validPeriodStart + '</td>';
						html += '<td class="textLeft">有效期限至: </td>';
						html += '<td>' + validPeriodEnd + '</td>';
						html += '</tr>';

						html += '<tr class="classQH">';
						html += '<td class="textLeft">经营地址:</td>';
						html += '<td  colspan="3">' + mailingAddress + '</td>';
						html += '</tr>';

						html += '<tr>';
						html += '<td class="textLeft">经营范围:</td>';
						html += '<td  colspan="3">' + businessScope + '</td>';
						html += '</tr>';

					} else if (beiAnType == "08") {

					} else if (beiAnType == "10") {

						html += '<tr >';
						html += '<td class="textLeft">企业名称:</td>';
						html += '<td>' + entName + '</td>';
						html += '<td class="textLeft">证书编号:</td>';
						html += '<td>' + certNo + '</td>';
						html += '</tr>';
						
						html += '<tr class="classQH">';
						html += '<td class="textLeft">有效期限自:</td>';
						html += '<td>' + validPeriodStart + '</td>';
						html += '<td class="textLeft">证照状态:</td>';
						html += '<td>' + validstate + '</td>';
						html += '</tr>';
						
						html += '<tr >';
						html += '<td class="textLeft">有效期至:</td>';
						html += '<td>' + validPeriodEnd + '</td>';
						html += '<td class="textLeft">发证日期:</td>';
						html += '<td>' + issueDate + '</td>';
						html += '</tr>';

						html += '<tr class="classQH">';
						html += '<td class="textLeft">地址:</td>';
						html += '<td>' + mailingAddress + '</td>';
						html += '<td class="textLeft">发证机关: </td>';
						html += '<td>' + regorgCn + '</td>';
						html += '</tr>';

						html += '<tr>';
						html += '<td class="textLeft">认证范围:</td>';
						html += '<td  colspan="3">' + businessScope + '</td>';
						html += '</tr>';
					} else if (beiAnType == "11") {

					} else if (beiAnType == "13") {

					} else if (beiAnType == "15") {

					} else if (beiAnType == "16") {

					} else if (beiAnType == "21") {

						html += '<tr >';
						html += '<td class="textLeft">企业名称:</td>';
						html += '<td>' + entName + '</td>';
						html += '<td class="textLeft">备案编号:</td>';
						html += '<td>' + certNo + '</td>';
						html += '</tr>';
						
						html += '<tr class="classQH">';
						html += '<td class="textLeft">法定代表人:</td>';
						html += '<td>' + legalPerson + '</td>';
						html += '<td class="textLeft">备案状态:</td>';
						html += '<td>' + validstate + '</td>';
						html += '</tr>';
						
						html += '<tr >';
						html += '<td class="textLeft">企业负责人:</td>';
						html += '<td>' + responsiblePerson + '</td>';
						html += '<td class="textLeft">备案部门:</td>';
						html += '<td>' + regorgCn + '</td>';
						html += '</tr>';

						html += '<tr class="classQH">';
						html += '<td class="textLeft">经营方式:</td>';
						html += '<td>' + operatingMode + '</td>';
						html += '<td class="textLeft">备案日期: </td>';
						html += '<td>' + issueDate + '</td>';
						html += '</tr>';

						html += '<tr >';
						html += '<td class="textLeft">有效期限自:</td>';
						html += '<td>' + validPeriodStart + '</td>';
						html += '<td class="textLeft">有效期限至: </td>';
						html += '<td>' + validPeriodEnd + '</td>';
						html += '</tr>';

						html += '<tr class="classQH">';
						html += '<td class="textLeft">经营范围:</td>';
						html += '<td>' + businessScope + '</td>';
						html += '<td class="textLeft">住所 :</td>';
						html += '<td>' + registerAddress + '</td>';
						html += '</tr>';

						html += '<tr >';
						html += '<td class="textLeft">经营场所:</td>';
						html += '<td>' + registerAddress + '</td>';
						html += '<td class="textLeft">库房地址: </td>';
						html += '<td>' + mailingAddress + '</td>';
						html += '</tr>';

					}
					if(beiAnType=="05"||beiAnType=="06"||beiAnType=="07"){
						$("#mentions").html("许可证信息");
					}else if(beiAnType=="10"){
						$("#mentions").html("GSP认证");
					}else if(beiAnType=="21"){
						$("#mentions").html("备案信息");
					}
					
					
					
					$("#titleInfos").html(typeName);
					$("#deitail2InfoTitle").html(typeName);
					$("#detail2Info").html(html);

				},
				error : function(data) {

				}

			});
		}
	</script>
</body>
</html>