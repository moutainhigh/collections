<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ page import="cn.gwssi.common.context.Recordset"%>
<%@ page import="cn.gwssi.common.context.vo.VoUser"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="cn.gwssi.common.context.TxnContext"%>
<!DOCTYPE html>
<freeze:html>
<%
	HttpSession usersession = request.getSession(false);
		VoUser voUser = (VoUser) usersession
				.getAttribute(TxnContext.OPER_DATA_NODE);
		String user = voUser.getOperName();
%>
<head>
<style type="text/css">
body {
	overflow-x: hidden;
}

.normal {
	background-color: green;
}

.jam {
	background-color: gray;
}

.busy {
	background-color: red;
	cursor: hand;
}

li.disabled {
	color: #888;
	background: none;
	padding-left: 15px;
}

.grid-row td.disabled {
	text-align: left;
	color: #888;
}

.tabs dt a,.tabs dt a:active,.tabs dt a:hover {
	text-decoration: none;
}

.tabs .dom_tabs_selected {
	background-color: transparent;
}

.tabs .dom_tabs_selected span{
	color: white;
	height: 24px;
	background-color: #369;
	display: inline-block;
	line-height: 24px;
	padding: 0 5px;
}

.inner_hidden,.outer_hidden,.dist_hidden {
	display: none;
}

.graph_guide{
	background: url(/css/homepage/images/legend.png) center 6px no-repeat;
	width: 100%;
	height: 71px;
}

.frame_div{
	position: absolute;
	left: 50px;
	top: 5px;
	display: none;
	width: 400px;
	height: 360px;
	background: white;
}
.right .wrap .right_list li td.cbg {cursor:pointer; background:url(/css/homepage/images/bg.png) 50% 50% white no-repeat !important;}
.right .wrap .right_list li .state_info td{background:none !important;
	border:0px solid #f1f1f1 !important;}
.sel2turn{background-color:#8AB0D7 !important; cursor:default !important;}
/* .body-div{overflow-y:hidden !important;} */
</style>
<style type="text/css">
.show_svrTarget_close{/* position:absolute; */ width:10px;height:16px;
	text-indent:-10em; overflow:hidden;/* background:url(/page/homepage/home/images/tipbg.png) 100% -49px no-repeat; */
	cursor:pointer;}
.targetFrame{position:absolute; width:420px; min-height: 340px; display: none; 
	border:4px solid #85b6e2; 
}
</style>
<meta http-equiv="refresh" charset="gbk" />
<META http-equiv="X-UA-Compatible" content="IE=9" /> 
<script type="text/javascript"
	src="<%=request.getContextPath()%>/script/FusionCharts/jquery-1.4.2.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/script/FusionCharts/FusionCharts.js"></script>
<link href="<%=request.getContextPath()%>/css/homepage/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/script/page/calendar.js"></script>
<script type="text/javascript" src="/script/lib/jquery171.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/script/jquery-plugin-tab/jquery.tabs.js"></script>
<link
	href="<%=request.getContextPath()%>/script/jquery-plugin-tab/css/jquery.tabs.css"
	rel="stylesheet" type="text/css" />
<%
	DataBus context = (DataBus) request
				.getAttribute("freeze-databus");
		out.println("<script>var txnList='"
				+ context.getRecord("oper-data").getValue("txnList")
				+ "';</script>");
%>
<title>�������г��������ල����ίԱ��</title>
</head>
<freeze:body>
<div style="width:100%; height:100%;">
<!-- ������ͼ�����div -->
<div id="content" style="margin-left:0px;width:10000px;">
  <table width="100%" cellPadding="0" cellspaceing="0">
    <tr>
      <td id="tb1"  valign="top"  width="1024px" >
        <table class="index" cellpadding="0" style="width: 100%;table-layout: fixed; overflow: hidden;float: left;" 
           cellspacing="0" border="0">
		<tr>
			<td style="padding: 5px;" class="left">
				<!-- ��ҳ��࿪ʼ -->
				<div class="index_calendar" style="width: 100%; margin-bottom: 10px;">
					<h2 class="rcb">�����ճ̱�</h2>
					<div class="Calendar">
						<table cellspacing="0" cellpadding="0" border="0">
							<thead>
								<td valign="middle" colspan="7">
									<div class="head">
										<span style="padding-bottom:0px;line-height:32px;height:32px;" class="prey" onclick="javascript:PreY();"></span>
										<span style="padding-bottom:0px;line-height:32px;height:32px;" id="nowYear">2013</span>
										<span style="padding-bottom:0px;line-height:32px;height:32px;" class="nexty" onclick="javascript:NextY();"></span>
										<span style="padding-bottom:0px;line-height:32px;height:32px;" class="prey" onclick="javascript:PreM();"></span>
										<span style="padding-bottom:0px;line-height:32px;height:32px;" id="nowMonth">5</span>
										<span style="padding-bottom:0px;line-height:32px;height:32px;" class="nexty" onclick="javascript:NextM();"></span>
										<!-- <span class="query"></span> -->
									</div>
								</td>
							</thead>
							<tbody id="idCalendar"></tbody>
						</table>
					</div>
				</div>
				<div id="taskList" style="display:;" class="list"
					style="width:100%;">
					<h2 class="jhgg">��������</h2>
					<ul id="taskUl">
					</ul>
				</div>
				<div class="list" style="width: 100%; margin-top: 10px;">
					<h2 class="jhgg">��������</h2>
					<ul>
						<%
							Recordset infoList = null;
									String chartShareXML = "";
									String chartCollectXML = "";
									try {
										DataBus db = context.getRecord("chart");
										chartShareXML = db.getValue("chartShareXML");
										db = context.getRecord("chart_collect");
										chartCollectXML = db.getValue("chartCollectXML");
										infoList = context.getRecordset("tz-record");
										if (infoList != null && infoList.size() > 0) {
											for (int i = 0; i < infoList.size(); i++) {
												DataBus infoTmp = infoList.get(i);
												out.println("<li>"
														+ infoTmp.getValue("content") + "</li>");
											}
										} else {
											out.println("<li class='disabled'>���޹���</li>");
										}
									} catch (Exception e) {
										System.out.println("��ҳ��ȡ֪ͨ�������");
									}
						%>
					</ul>
				</div> <!-- ��ҳ������ -->
			</td>
			<td style="padding: 5px 10px;" valign="top" class="right">
				<!-- ��ҳ�Ҳ࿪ʼ -->
				<table class="wrap" cellpadding=0 cellspacing=0 border=0>
					<tr>
						<td align="left" valign="top">
							<div style="width: 98%; border:8px solid #4698e6; background:#f6f6f6;">
								<table style="background:#7fc1ff;margin:10px auto;" width="95%" cellpadding="0" cellspacing="0">
									<tr>
										<td align="center" style="background:url(/css/homepage/images/depart_p.jpg) center center no-repeat;" valign="middle" height="40"></td>
										<td style="background:#f1f1f1;" width="10px">&nbsp;</td>
										<td align="center" style="background:url(/css/homepage/images/inner_p.jpg) center center no-repeat;" valign="middle"></td>
										<td style="background:#f1f1f1;" width="10px">&nbsp;</td>
										<td align="center" style="background:url(/css/homepage/images/outer_p.jpg) center center no-repeat;" valign="middle"></td>
									</tr>
									<!-- <tr><td colspan="5" style="background:#f1f1f1;height:3px;">&nbsp;</td></tr> -->
									<tr style="background:#f1f1f1;">
								<%
											List<DataBus> alllist = new ArrayList<DataBus>();
											List<DataBus> inner = new ArrayList<DataBus>();
											List<DataBus> dist = new ArrayList<DataBus>();
											List<DataBus> outer = new ArrayList<DataBus>();
										//	List waming_info = new ArrayList();
											try {
												//��ȡ���з������
												infoList = context.getRecordset("mon-inner-record");
												if (null != infoList && infoList.size() > 0) {
													for (int i = 0; i < infoList.size(); i++) {
														alllist.add(infoList.get(i));
													}
												}
												//����ͬ���͵ķ���������
												for(int i = 0; i < alllist.size() ; i++){
													String tmpType = alllist.get(i).getValue("service_targets_type");
													if("000".equals(tmpType)){
														dist.add(alllist.get(i));
													} else if("001".equals(tmpType)){
														inner.add(alllist.get(i));
													} else if("002".equals(tmpType)){
														outer.add(alllist.get(i));
													}
												}
											} catch (Exception e) {
												System.out.println("��ҳ������ݳ���");
											}
								%>
								<!-- ���ط־� -->
								<td valign="top"><ul class="right_list">
												<%String name = "";
												//String state = "";
												String cls = "";
												String share = "";
												String collect = "";
												String svrId = "";
												String etl = "";
												String gray = "background:url("+request.getContextPath()
														+"/css/homepage/images/gray.png) center center no-repeat !important;";
													String green = "background:url("+request.getContextPath()
														+"/css/homepage/images/green.png) center center no-repeat !important;";
													for (int i = 0; i < dist.size(); i++) {
														DataBus db = dist.get(i);
														svrId = db.getValue("service_targets_id");
														name = db.getValue("service_targets_name");
														//state = db.getValue("waming_state"); 
														name = db.getValue("service_targets_name");
														share = db.getValue("share1");
														collect = db.getValue("collect1");
														etl = db.getValue("etl1");
																//if (i < 10) {
																	String tmpStr = "<table class='state_info' border='0' width='100%' cellpadding='0' cellspacing='0'>"
																	+"<tr><td style='height:10px; "+((collect.equals("0") && ("0").equals(etl)) ? gray : green )
																	+ "'></td></tr><tr><td style='height:10px; "+(share.equals("0") ? gray : green )+
																	"'></td></tr></table>";
																	out.println("<li><table width='100%'><tr><td style='cursor:pointer;' class='li_title' id='"+svrId+"' title='"
																			+ name
																			+ "' align='center' valign='middle'>"
																			+ name
																			+ "</td><td class='cbg' style='width:20px;'>"
																			+ tmpStr + " </td></tr></table></li>");
															}
												%>
												<li style="clear: both; display: none;"></li>
											</ul></td>
									<td width="10"></td>
										<td valign="top">
											<!-- �������ڲ�ϵͳ -->
											<ul class="right_list">
												<%
															for (int i = 0; i < inner.size(); i++) {
																DataBus db = inner.get(i);
																svrId = db.getValue("service_targets_id");
																name = db.getValue("service_targets_name");
																//state = db.getValue("waming_state"); 
																name = db.getValue("service_targets_name");
																share = db.getValue("share1");
																collect = db.getValue("collect1");
																etl = db.getValue("etl1");
															//	if (i < 10) {
																String tmpStr = "<table class='state_info' border='0' width='100%' cellpadding='0' cellspacing='0'>"
																	+"<tr><td style='height:10px; "+((collect.equals("0") && ("0").equals(etl)) ? gray: green )
																	+ "'></td></tr><tr><td style='height:10px; "+(share.equals("0") ? gray : green )+
																	"'></td></tr></table>";
																	out.println("<li><table width='100%'><tr><td style='cursor:pointer;' class='li_title' id='"+svrId+"' title='"
																			+ name
																			+ "' align='center' valign='middle'>"
																			+ name
																			+ "</td><td class='cbg' style='width:20px;'>"
																			+tmpStr+"</span></td></tr></table></li>");
															}
												%>
												<li style="clear: both; display: none;"></li>
											</ul>
										</td>
										<td width="10"></td>
										<td valign="top"><ul class="right_list">
												<!-- �������ⲿϵͳ -->
												<%
													for (int i = 0; i < outer.size(); i++) {
														DataBus db = outer.get(i);
														svrId = db.getValue("service_targets_id");
														name = db.getValue("service_targets_name");
														//state = db.getValue("waming_state"); 
														name = db.getValue("service_targets_name");
														share = db.getValue("share1");
														collect = db.getValue("collect1");
														etl = db.getValue("etl1");
																String tmpStr = "<table class='state_info' border='0' width='100%' cellpadding='0' cellspacing='0'>"
																		+"<tr><td style='height:10px; "+((collect.equals("0") && ("0").equals(etl)) ? gray : green )
																		+ "'></td></tr><tr><td style='height:10px; "+(share.equals("0") ? gray : green )+
																		"'></td></tr></table>";
																		out.println("<li><table width='100%'><tr><td style='cursor:pointer;' class='li_title' id='"+svrId+"' title='"
																			+ name
																			+ "' align='center' valign='middle'>"
																			+ name
																			+ "</td><td class='cbg' style='width:20px;'>"
																			+tmpStr+"</td></tr></table></li>");
															}
												%>
												<li style="clear: both; display: none;"></li>
											</ul></td>
									</tr>
									<tr style="background:#f1f1f1;"><td height="10" colspan="5">&nbsp;</td></tr>
									<tr style="background:#f7f7f7;">
										<td colspan="5">
											<div class="graph_guide" >
											  <div style="color: #000;text-align:center;padding-top: 30px;">
												  <img src="/css/homepage/images/gray.png"/>&nbsp;&nbsp;�޷���&nbsp;&nbsp;&nbsp;&nbsp;
												  <img src="/css/homepage/images/green.png"/>&nbsp;&nbsp;�з���&nbsp;&nbsp;&nbsp;&nbsp;
												  <img src="/css/homepage/images/green_2.gif"/>&nbsp;&nbsp;��������&nbsp;&nbsp;&nbsp;&nbsp;
												  <img src="/css/homepage/images/red_2.gif"/>&nbsp;&nbsp;����
											  </div>
											<!-- ������ͼ�� --></div>
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
					<!-- 
					<tr><td colspan="5" height="10">&nbsp;</td></tr>
					 -->
					<tr>
						<td style="width:100%;height:115px;background-color:#f8f9fb;"  colspan="5" align="center">
							<div id="div_foot" style="text-align:center;width:545px;height:115px; background-color:#f8f9fb;">
								<ul>
									<li style="float:left; height:115px; width:2px;
										background:url(<%=request.getContextPath()%>/css/homepage/images/line.png) 0 0 no-repeat;"></li>
									<li onclick="toTurn('zwt');" style="cursor:pointer;float:left; height:115px; width:130px; 
										background:url(<%=request.getContextPath()%>/css/homepage/images/home_icon.png) 0px -115px no-repeat;">
										<div style="font-size:12px;width:130px;text-align:center;color:#333;position:relative;top: 85px;">�ӿڹ�ϵͼ</div>
									</li>
									<li style="float:left; height:115px; width:2px;
										background:url(<%=request.getContextPath()%>/css/homepage/images/line.png) 0 0 no-repeat;"></li>
									<li onclick="toTurn('tjt');" style=" cursor:pointer;float:left; height:115px; width:130px; 
										background:url(<%=request.getContextPath()%>/css/homepage/images/home_icon.png) 0px -575px no-repeat;">
										<div style="font-size:12px;width:130px;text-align:center;color:#333;position:relative;top: 85px;">���ݱ��ϵͼ</div>
									</li>
									<!-- <li style="float:left; height:115px; width:2px;
										background:url(<%=request.getContextPath()%>/css/homepage/images/line.png) 0 0 no-repeat;"></li>
									<li onclick="toTurn('fenbu');" style=" cursor:pointer;float:left; height:115px; width:130px; 
										background:url(<%=request.getContextPath()%>/css/homepage/images/home_icon.png) 0px -230px no-repeat;">
										<div style="font-size:12px;width:130px;text-align:center;color:#333;position:relative;top: 85px;">�ӿڹ�ϵͼ</div>
									</li> --><%-- 
									<li  style="float:left; height:115px; width:2px;
										background:url(<%=request.getContextPath()%>/css/homepage/images/line.png) 0 0 no-repeat;"></li>
									<li onclick="toTurn('qst');" style=" cursor:pointer;float:left; height:115px; width:130px; 
										background:url(<%=request.getContextPath()%>/css/homepage/images/home_icon.png) 0px -345px no-repeat;">
										<div style="font-size:12px;width:130px;text-align:center;color:#333;position:relative;top: 85px;">�ɼ�����ֲ�</div>
									</li>
									<li style="float:left; height:115px; width:2px;
										background:url(<%=request.getContextPath()%>/css/homepage/images/line.png) 0 0 no-repeat;"></li>
									<li onclick="toTurn('rlt');" style="cursor:pointer; float:left; height:115px; width:130px; 
										background:url(<%=request.getContextPath()%>/css/homepage/images/home_icon.png) 0px -460px no-repeat;">
										<div style="font-size:12px;width:130px;text-align:center;color:#333;position:relative;top: 85px;">��������Ծ��</div>
									</li> --%>
									<li style="float:left; height:115px; width:2px;
										background:url(<%=request.getContextPath()%>/css/homepage/images/line.png) 0 0 no-repeat;"></li>
								</ul>
							</div>
						</td>
					</tr>
				</table> <!-- ��ҳ�Ҳ���� -->
			</td>
		</tr>
	</table>
	</td>
      <td id="tb2" style="" valign="top">
          <table style="width: 99%">
            <tr height="540px;">
              <Td align="center" style="width: 100%">
                <iframe src="/page/zwt/zwt.jsp" frameborder="0"  style="width:99%;height:525px;" ></iframe>
              </Td>
            </tr>
            <tr>
						<td style="width:100%;height:70px;background-color:#c0cfe4;"  colspan="5" align="center">
							<div id="div_foot_zwt" style="text-align:center;width:525px;height:70px; background-color:#c0cfe4;">
								<ul>
									<li style="float:left; height:70px; width:2px;
										background:url(<%=request.getContextPath()%>/css/homepage/images/line.png) 0 0 no-repeat;"></li>
									<li onclick="toTurn('home');" style="cursor:pointer;float:left; height:70px; width:100px; 
										background:url(<%=request.getContextPath()%>/css/homepage/images/home_icon2.png) 0px 0px no-repeat;">
										<div style="font-size:12px;width:100px;text-align:center;color:#333;position:relative;top: 50px;">��ҳ</div>
									</li>
									<li style="float:left; height:70px; width:2px;
										background:url(<%=request.getContextPath()%>/css/homepage/images/line.png) 0 0 no-repeat;"></li>
									<li id="zwt_sel" onclick="toTurn('');" style="float:left; height:70px; width:100px; 
										background:url(<%=request.getContextPath()%>/css/homepage/images/home_icon2.png) -100px -70px no-repeat;">
										<div style="font-size:12px;width:100px;text-align:center;color:#333;position:relative;top: 50px;">�ӿڹ�ϵͼ</div>
									</li>
									<li style="float:left; height:70px; width:2px;
										background:url(<%=request.getContextPath()%>/css/homepage/images/line.png) 0 0 no-repeat;"></li>
									<li onclick="toTurn('tjt');" style="cursor:pointer; float:left; height:70px; width:100px; 
										background:url(<%=request.getContextPath()%>/css/homepage/images/home_icon2.png) 0px -350px no-repeat;">
										<div style="font-size:12px;width:100px;text-align:center;color:#333;position:relative;top: 50px;">���ݱ��ϵͼ</div>
									</li>
									<!--  <li style="float:left; height:70px; width:2px;
										background:url(<%=request.getContextPath()%>/css/homepage/images/line.png) 0 0 no-repeat;"></li>
									<li onclick="toTurn('fenbu');" style=" cursor:pointer;float:left; height:70px; width:100px; 
										background:url(<%=request.getContextPath()%>/css/homepage/images/home_icon2.png) 0px -140px no-repeat;">
										<div style="font-size:12px;width:100px;text-align:center;color:#333;position:relative;top: 50px;">�ӿڹ�ϵͼ</div>
									</li>--><%-- 
									<li  style="float:left; height:70px; width:2px;
										background:url(<%=request.getContextPath()%>/css/homepage/images/line.png) 0 0 no-repeat;"></li>
									<li onclick="toTurn('qst');" style=" float:left; height:70px; width:100px; 
										background:url(<%=request.getContextPath()%>/css/homepage/images/home_icon2.png) 0px -210px no-repeat;">
										<div style="font-size:12px;width:100px;text-align:center;color:#333;position:relative;top: 50px;">�ɼ�����ֲ�</div>
									</li>
									<li style="float:left; height:70px; width:2px;
										background:url(<%=request.getContextPath()%>/css/homepage/images/line.png) 0 0 no-repeat;"></li>
									<li onclick="toTurn('rlt');" style="cursor:pointer;float:left; height:70px; width:100px; 
										background:url(<%=request.getContextPath()%>/css/homepage/images/home_icon2.png) 0px -280px no-repeat;">
										<div style="font-size:12px;width:100px;text-align:center;color:#333;position:relative;top: 50px;">��������Ծ��</div>
									</li> --%>
									<li style="float:left; height:70px; width:2px;
										background:url(<%=request.getContextPath()%>/css/homepage/images/line.png) 0 0 no-repeat;"></li>
								</ul>
							</div>
						</td>
					</tr>
          </table>	
      </td>
      <td id="tb3" style="height:610px;" valign="top">
         <table style="width: 99%">
            <tr height="540px;">
              <Td align="center">
            
                <!-- <iframe id="star_iframe" src="/page/zwt/Starpaths.jsp" frameborder="0"  style="width:99%;height:525px;" ></iframe>
             --> 
             <iframe id="star_iframe" src="/page/zwt/systablein.jsp" frameborder="0"  style="width:99%;height:525px;" ></iframe>
             
              </Td>
            </tr>
            <tr>
						<td style="width:100%;height:70px;background-color:#c0cfe4;"  colspan="5" align="center">
							<div id="div_foot_rlt" style="text-align:center;width:525px;height:70px; background-color:#c0cfe4;">
								<ul>
									 <li style="float:left; height:70px; width:2px;
										background:url(<%=request.getContextPath()%>/css/homepage/images/line.png) 0 0 no-repeat;"></li>
									<li onclick="toTurn('home');" style="cursor:pointer;float:left; height:70px; width:100px; 
										background:url(<%=request.getContextPath()%>/css/homepage/images/home_icon2.png) 0px 0px no-repeat;">
										<div style="font-size:12px;width:100px;text-align:center;color:#333;position:relative;top: 50px;">��ҳ</div>
									</li>
									<li style="float:left; height:70px; width:2px;
										background:url(<%=request.getContextPath()%>/css/homepage/images/line.png) 0 0 no-repeat;"></li>
									<li onclick="toTurn('zwt');" style="cursor:pointer;float:left; height:70px; width:100px; 
										background:url(<%=request.getContextPath()%>/css/homepage/images/home_icon2.png) 0px -70px no-repeat;">
										<div style="font-size:12px;width:100px;text-align:center;color:#333;position:relative;top: 50px;">�ӿڹ�ϵͼ</div>
									</li>
									<li style="float:left; height:70px; width:2px;
										background:url(<%=request.getContextPath()%>/css/homepage/images/line.png) 0 0 no-repeat;"></li>
									<li id="tjt_sel" onclick="toTurn('');" style="float:left; height:70px; width:100px; 
										background:url(<%=request.getContextPath()%>/css/homepage/images/home_icon2.png) -100px -350px no-repeat;">
										<div style="font-size:12px;width:100px;text-align:center;color:#333;position:relative;top: 50px;">���ݱ��ϵͼ</div>
									</li>
									<!--  <li style="float:left; height:70px; width:2px;
										background:url(<%=request.getContextPath()%>/css/homepage/images/line.png) 0 0 no-repeat;"></li>
									<li onclick="toTurn('fenbu');" style="cursor:pointer;float:left; height:70px; width:100px; 
										background:url(<%=request.getContextPath()%>/css/homepage/images/home_icon2.png) 0px -140px no-repeat;">
										<div style="font-size:12px;width:100px;text-align:center;color:#333;position:relative;top: 50px;">�ӿڹ�ϵͼ</div>
									</li>--><%--
									<li  style="float:left; height:70px; width:2px;
										background:url(<%=request.getContextPath()%>/css/homepage/images/line.png) 0 0 no-repeat;"></li>
									<li onclick="toTurn('qst');" style="cursor:pointer;float:left; height:70px; width:100px; 
										background:url(<%=request.getContextPath()%>/css/homepage/images/home_icon2.png) 0px -210px no-repeat;">
										<div style="font-size:12px;width:100px;text-align:center;color:#333;position:relative;top: 50px;">�ɼ�����ֲ�</div>
									</li> 
									<li style="float:left; height:70px; width:2px;
										background:url(<%=request.getContextPath()%>/css/homepage/images/line.png) 0 0 no-repeat;"></li>
									<li onclick="toTurn('rlt');" style="cursor:pointer;float:left; height:70px; width:100px; 
										background:url(<%=request.getContextPath()%>/css/homepage/images/home_icon2.png) 0px -280px no-repeat;">
										<div style="font-size:12px;width:100px;text-align:center;color:#333;position:relative;top: 50px;">��������Ծ��</div>
									</li> --%>
									<li style="float:left; height:70px; width:2px;
										background:url(<%=request.getContextPath()%>/css/homepage/images/line.png) 0 0 no-repeat;"></li>
								</ul>
							</div>
						</td>
					</tr>
          </table>
      </td>  
      <td id="tb4" style="height:610px;" valign="top">
        <table style="width: 99%">
            <tr height="540px;">
              <Td align="center">
        
              <!--   <iframe src="/page/zwt/MLPie_L1.jsp" frameborder="0"  style="width:99%;height:525px;" ></iframe>
   			 -->
   			 <iframe src="/page/zwt/tableinobject.jsp" frameborder="0"  style="width:99%;height:525px;" ></iframe>
   
              </Td>
            </tr>
            <tr>
						<td style="width:100%;height:70px;background-color:#c0cfe4;"  colspan="5" align="center">
							<div id="div_foot_fenbu" style="text-align:center;width:525px;height:70px; background-color:#c0cfe4;">
								<ul>
									<li style="float:left; height:70px; width:2px;
										background:url(<%=request.getContextPath()%>/css/homepage/images/line.png) 0 0 no-repeat;"></li>
									<li onclick="toTurn('home');" style="cursor:pointer;float:left; height:70px; width:100px; 
										background:url(<%=request.getContextPath()%>/css/homepage/images/home_icon2.png) 0px 0px no-repeat;">
										<div style="font-size:12px;width:100px;text-align:center;color:#333;position:relative;top: 50px;">��ҳ</div>
									</li> 
									<li style="float:left; height:70px; width:2px;
										background:url(<%=request.getContextPath()%>/css/homepage/images/line.png) 0 0 no-repeat;"></li>
									<li onclick="toTurn('zwt');" style="cursor:pointer;float:left; height:70px; width:100px; 
										background:url(<%=request.getContextPath()%>/css/homepage/images/home_icon2.png) 0px -70px no-repeat;">
										<div style="font-size:12px;width:100px;text-align:center;color:#333;position:relative;top: 50px;">�ӿڹ�ϵͼ</div>
									</li>
									<li style="float:left; height:70px; width:2px;
										background:url(<%=request.getContextPath()%>/css/homepage/images/line.png) 0 0 no-repeat;"></li>
									<li onclick="toTurn('tjt');" style="cursor:pointer;float:left; height:70px; width:100px; 
										background:url(<%=request.getContextPath()%>/css/homepage/images/home_icon2.png) 0px -350px no-repeat;">
										<div style="font-size:12px;width:100px;text-align:center;color:#333;position:relative;top: 50px;">���ݱ��ϵͼ</div>
									</li>
									<!--  <li style="float:left; height:70px; width:2px;
										background:url(<%=request.getContextPath()%>/css/homepage/images/line.png) 0 0 no-repeat;"></li>
									<li id="fenbu_sel" onclick="toTurn('fenbu');" style="float:left; height:70px; width:100px; 
										background:url(<%=request.getContextPath()%>/css/homepage/images/home_icon2.png) -100px -140px no-repeat;">
										<div style="font-size:12px;width:100px;text-align:center;color:#333;position:relative;top: 50px;">�ӿڹ�ϵͼ</div>
									</li>--><%--
									<li  style="float:left; height:70px; width:2px;
										background:url(<%=request.getContextPath()%>/css/homepage/images/line.png) 0 0 no-repeat;"></li>
									<li onclick="toTurn('qst');" style="cursor:pointer;float:left; height:70px; width:100px; 
										background:url(<%=request.getContextPath()%>/css/homepage/images/home_icon2.png) 0px -210px no-repeat;">
										<div style="font-size:12px;width:100px;text-align:center;color:#333;position:relative;top: 50px;">�ɼ�����ֲ�</div>
									</li>
									 <li style="float:left; height:70px; width:2px;
										background:url(<%=request.getContextPath()%>/css/homepage/images/line.png) 0 0 no-repeat;"></li>
									<li onclick="toTurn('rlt');" style="cursor:pointer;float:left; height:70px; width:100px; 
										background:url(<%=request.getContextPath()%>/css/homepage/images/home_icon2.png) 0px -280px no-repeat;">
										<div style="font-size:12px;width:100px;text-align:center;color:#333;position:relative;top: 50px;">��������Ծ��</div>
									</li> --%>
									<li style="float:left; height:70px; width:2px;
										background:url(<%=request.getContextPath()%>/css/homepage/images/line.png) 0 0 no-repeat;"></li>
								</ul>
							</div>
						</td>
					</tr>
          </table>
      </td>
      <td id="tb5" style="height:610px;" valign="top">
         <table style="width: 99%">
            <tr height="540px;">
              <Td align="center">
         	<!-- 
               <iframe src="/page/zwt/MLPie_L4.jsp" frameborder="0"  style="width:99%;height:525px;" ></iframe> 
    -->
              </Td>
            </tr>
            <tr>
						<td style="width:100%;height:70px;background-color:#c0cfe4;"  colspan="5" align="center">
							<div id="div_foot_qst" style="text-align:center;width:525px;height:70px; background-color:#c0cfe4;">
								<ul>
									<li style="float:left; height:70px; width:2px;
										background:url(<%=request.getContextPath()%>/css/homepage/images/line.png) 0 0 no-repeat;"></li>
									<li onclick="toTurn('home');" style="cursor:pointer;float:left; height:70px; width:100px; 
										background:url(<%=request.getContextPath()%>/css/homepage/images/home_icon2.png) 0px 0px no-repeat;">
										<div style="font-size:12px;width:100px;text-align:center;color:#333;position:relative;top: 50px;">��ҳ</div>
									</li>
									<li style="float:left; height:70px; width:2px;
										background:url(<%=request.getContextPath()%>/css/homepage/images/line.png) 0 0 no-repeat;"></li>
									<li onclick="toTurn('zwt');" style="cursor:pointer;float:left; height:70px; width:100px; 
										background:url(<%=request.getContextPath()%>/css/homepage/images/home_icon2.png) 0px -70px no-repeat;">
										<div style="font-size:12px;width:100px;text-align:center;color:#333;position:relative;top: 50px;">���ݽ�������</div>
									</li>
									<li style="float:left; height:70px; width:2px;
										background:url(<%=request.getContextPath()%>/css/homepage/images/line.png) 0 0 no-repeat;"></li>
									<li onclick="toTurn('tjt');" style="cursor:pointer;float:left; height:70px; width:100px; 
										background:url(<%=request.getContextPath()%>/css/homepage/images/home_icon2.png) 0px -350px no-repeat;">
										<div style="font-size:12px;width:100px;text-align:center;color:#333;position:relative;top: 50px;">���ݽ���ͳ��</div>
									</li>
									<li style="float:left; height:70px; width:2px;
										background:url(<%=request.getContextPath()%>/css/homepage/images/line.png) 0 0 no-repeat;"></li>
									<li onclick="toTurn('fenbu');" style="cursor:pointer;float:left; height:70px; width:100px; 
										background:url(<%=request.getContextPath()%>/css/homepage/images/home_icon2.png) 0px -140px no-repeat;">
										<div style="font-size:12px;width:100px;text-align:center;color:#333;position:relative;top: 50px;">�������ֲ�</div>
									</li>
									<li  style="float:left; height:70px; width:2px;
										background:url(<%=request.getContextPath()%>/css/homepage/images/line.png) 0 0 no-repeat;"></li>
									<li id="qst_sel" onclick="toTurn('');" style="float:left; height:70px; width:100px; 
										background:url(<%=request.getContextPath()%>/css/homepage/images/home_icon2.png) -100px -210px no-repeat;">
										<div style="font-size:12px;width:100px;text-align:center;color:#333;position:relative;top: 50px;">�ɼ�����ֲ�</div>
									</li>
									<li style="float:left; height:70px; width:2px;
										background:url(<%=request.getContextPath()%>/css/homepage/images/line.png) 0 0 no-repeat;"></li>
									<li onclick="toTurn('rlt');" style="cursor:pointer;float:left; height:70px; width:100px; 
										background:url(<%=request.getContextPath()%>/css/homepage/images/home_icon2.png) 0px -280px no-repeat;">
										<div style="font-size:12px;width:100px;text-align:center;color:#333;position:relative;top: 50px;">��������Ծ��</div>
									</li>
									<li style="float:left; height:70px; width:2px;
										background:url(<%=request.getContextPath()%>/css/homepage/images/line.png) 0 0 no-repeat;"></li>
								</ul>
							</div>
						</td>
					</tr>
          </table>
      </td> 
    </tr>
  
  </table>	 	
</div>

<iframe class="targetFrame" id="targetFrame" frameborder="0" style="width: 450px; height: 320px;" name="targetFrame" ></iframe>

<div id="show_svrTarget" style="font-size:12px;color:#555;position:
	absolute;display:none;width:auto; padding:2px; background:#ECF9FF; border:5px solid #E9F3FD;">
	<div style="border-bottom:1px solid #A6C9E1;overflow:hidden;background:url(images/tipbg.png) 0 0 repeat-x;;height:20px;font-size:12px;text-align:right;">
	    <div id="show_svrTarget_title" style="margin:0 20px 0 0; float:left"></div>
		<div onclick="javascript:closeIt();" class="show_svrTarget_close" title="�ر�" style="display:right;">X</div>
		<div style="clear:both;"></div>
	</div>
	<div id="show_svrTarget_info" class="windown-content"></div>
	<div style="text-align:right;"><!-- <a href="javascript:;">�鿴��ϸ</a>  --></div>
</div>
</div>
<script type="text/javascript">
function closeIt(){
	$('#show_svrTarget').hide();
}
function goPage(menuId) {
	//$('span.busy').css("cursor", "hand");
	menuId = ''+menuId;
	var strTxn = menuId.substr(0, menuId.length-1);
	if (txnList.indexOf(strTxn) > 0) {
		parent.window.frames['topFrame'].toolbar_onclick(menuId);
	}else{
		//alert("��û�в���Ȩ��.");
	}
}
var last_sel = 1;
var now_sel = 1;
var width1=0;
var height1=0;
var dist_in = new Array;
var inner_in = new Array;
var outer_in = new Array;
var wamings = new Array;
var share_times1 = new Array;
var targets_ids1 = new Array;
var targets_names1 = new Array;
var wamings = new Array;
	<%
	for (int i = 0; i < dist.size(); i++) {
		svrId = ((DataBus) dist.get(i))
				.getValue("service_targets_id");
		name = ((DataBus) dist.get(i))
				.getValue("service_targets_name");
		share = ((DataBus) dist.get(i))
				.getValue("share1");
		collect = ((DataBus) dist.get(i))
				.getValue("collect1");
		etl = ((DataBus) dist.get(i))
				.getValue("etl1");
		out.println("dist_in.push({'id': '"+svrId+"', 'name': '"+name+"', 'share': '"+share+"', 'collect': '"+collect+"', 'etl': '"+etl+"'})");
	}
	for (int i = 0; i < inner.size(); i++) {
		svrId = ((DataBus) inner.get(i))
				.getValue("service_targets_id");
		name = ((DataBus) inner.get(i))
				.getValue("service_targets_name");
		/* state = ((DataBus) dist.get(i))
				.getValue("waming_state"); */
		share = ((DataBus) inner.get(i))
				.getValue("share1");
		collect = ((DataBus) inner.get(i))
				.getValue("collect1");
		etl = ((DataBus) inner.get(i))
				.getValue("etl1");
		out.println("inner_in.push({'id': '"+svrId+"', 'name': '"+name+"', 'share': '"+share+"', 'collect': '"+collect+"', 'etl': '"+etl+"'})");
	}
	for (int i = 0; i < outer.size(); i++) {
		svrId = ((DataBus) outer.get(i))
				.getValue("service_targets_id");
		name = ((DataBus) outer.get(i))
				.getValue("service_targets_name");
		/* state = ((DataBus) dist.get(i))
				.getValue("waming_state"); */
		share = ((DataBus) outer.get(i))
				.getValue("share1");
		collect = ((DataBus) outer.get(i))
				.getValue("collect1");
		etl = ((DataBus) outer.get(i))
				.getValue("etl1");
		out.println("outer_in.push({'id': '"+svrId+"', 'name': '"+name+"', 'share': '"+share+"', 'collect': '"+collect+"', 'etl': '"+etl+"'})");
	}
	%>
window.onload = function(){
	init();
	if($(".today").length > 0){
		$('.today')[0].click();
	}
	width1 = window.screen.width;
	height1 = window.screen.height;
	if(width1){
		$('#content').width(width1*5);
		$('#tb1').width(width1);
		$('#tb2').width(width1);
		$('#tb3').width(width1);
		$('#tb4').width(width1);
		$('#tb5').width(width1); 
		//$('#tb6').width(width1);
	}else{
		$('#content').width(1360*5);
		$('#tb1').width(1360);
		$('#tb2').width(1360);
		$('#tb3').width(1360);
		$('#tb4').width(1360);
		$('#tb5').width(1360); 
		//$('#tb6').width(1360);
	}
	<!-- ����ÿ��������������ʱ�����ĺ��� -->
	var mover = function() {
		$('#targetFrame').hide();
			//console.log($(this).text());
			$el = $(this);
			var index = $el.parents('li').index();
			var ulindex = $el.parents('ul').parent().index();
			var who_show = dist_in;
			if(ulindex === 2){
				who_show = inner_in;
			}else if(ulindex === 4){
				who_show = outer_in;
			}else{
				who_show = dist_in;
			}
			//console.log('['+index+', '+index+']   ' +��who_show[index]['collect'] + ' === '+ who_show[index]['share']);
			$('#show_svrTarget_info')
					.html("<p>"+ $el.text()
									+ (who_show[index]['collect']==0 ? "�ɼ�����: ��" : "�ɼ�����: "+who_show[index]['collect']+"��")+"<br>"
									+ $el.text()
									+ (who_show[index]['share']==0 ? "�������: ��" : "�������: "+who_show[index]['share']+"��")+"<br>"
									+"</p>");
			$('#show_svrTarget').css({
				"top" : ($el.offset().top + $el.height()+3),
				"left" : $el.prev().offset().left
			}).show();
			$('#show_svrTarget_title').html($el.prev().text() + "�ſ�");
		};
		$('.cbg').bind('click', mover);
		$('.cbg').prev().click(function(){
			var offset = $(this).offset();
			var left = offset.left;
			var top = offset.top;
			//���ⵯ������ʾ����Ļ����֮��
			//���У��
			if(offset.left + $('#targetFrame').width() > $("#tb1").width()){
				left = $("#tb1").width() - 30 - $('#targetFrame').width();
			}			
			//�߶�У��
			var scrollHeight = Math.max(document.documentElement.scrollHeight, document.body.scrollHeight);
			if(offset.top+$('#targetFrame').height()>scrollHeight){
				top = 64;				
			} 
			
			$('#targetFrame').show();
			//bindClose();
			$('#targetFrame').css({"left":  left , "top":  top});
			document.getElementById('select-key:service_targets_id').value=$(this).attr('id');
			document.getElementById('select-key:service_targets_name').value=$(this).text();
			document.getElementById('form1').submit(); 
		});
		getRunInfoAjax();
		setInterval("getRunInfoAjax()", 300000);
	}
	var cale; // ��������
	function init() {
		//�õ���ǰ����
		var today = new Date;
		var year = today.getFullYear();
		var month = today.getMonth() + 1;
		$("#nowYear").html(year);
		$("#nowMonth").html(month);
		cale = new Calendar("idCalendar", {
			onSelectDayCallBack : function(o) {
				var year = this.Year;
				var month = this.Month;
				var day = o.innerText;
				var page = new pageDefine("/txn30801006.ajax", "�������ڻ�ȡ�����б�");
				page.addValue(year, "select-key:year");
				page.addValue(month, "select-key:month");
				page.addValue(day, "select-key:day");
				page.callAjaxService('setTaskList');

			},
			clean : true,
			allowPrev : false
		});

		// ��ʼ�������� 
		cale.PreDraw(new Date(year, month - 1, 1));
		$('#closeDiv').bind("click", function(){
			$('#targetFrame').attr("src", "");
			$('#targetDiv').hide();
		})
	}
var is_refresh = true;
	function refreshStar(){
		if(is_refresh){
			is_refresh = false;
		}
	}
	
	function getRunInfoAjax() {
		var page = new pageDefine("/txn6020010.ajax", "��ѯ��ǰ���������б�");
		page.callAjaxService('getRunInfo');
	}
	
	function callTargetBack(errCode, errDesc, xmlResults) {
		if (errCode != '000000') {
			alert('�������['+errCode+']==>'+errDesc);
			return;
		} 
		
		var share_times = _getXmlNodeValues(xmlResults, "record:service_end_time");
		var task_info = _getXmlNodeValues(xmlResults, "record:service_id");
		var targets_ids = _getXmlNodeValues(xmlResults, "record:service_targets_id");
		var targets_names = _getXmlNodeValues(xmlResults, "record:service_name");
		for(var ii=0; ii<targets_ids.length; ii++){
			$('#'+targets_ids[ii]).next().css('cursor', 'pointer').find('table tr:first td')
			.css('background', 'url(/css/homepage/images/green_2.gif) center top no-repeat !important');
		}
	}
	function getRunInfo(errCode, errDesc, xmlResults) {
		if (errCode != '000000') {
			alert('�������['+errCode+']==>'+errDesc);
			return;
		} 
		
		var share_times = _getXmlNodeValues(xmlResults, "record:service_end_time");
		var task_info = _getXmlNodeValues(xmlResults, "record:service_id");
		var targets_ids = _getXmlNodeValues(xmlResults, "record:service_targets_id");
		var targets_names = _getXmlNodeValues(xmlResults, "record:service_name");
		for(var ii=0; ii<targets_ids.length; ii++){
			$('#'+targets_ids[ii]).next().css('cursor', 'pointer').find('table tr:first td')
			.css('background', 'url(/css/homepage/images/green_2.gif) center top no-repeat !important');
		}
		for(var ii=0; ii<targets_ids.length; ii++){
			$('#'+targets_ids[ii]).next().unbind('click').bind('click', function(){
				var page = new pageDefine("/txn6020010.ajax?select-key:service_targets_id="+$(this).prev().attr('id'), "��ѯ��ǰ���������б�");
				page.callAjaxService('getRunInfo2');
			})
		}
		
		//������Ϣ
		share_times1 = _getXmlNodeValues(xmlResults, "waming-info:record_time");
		targets_ids1 = _getXmlNodeValues(xmlResults, "waming-info:object_target_id");
		targets_names1 = _getXmlNodeValues(xmlResults, "waming-info:tp");
		wamings = _getXmlNodeValues(xmlResults, "waming-info:waming_info");
		
		<!-- ���� �ĸ���������d�����ǶԵģ����� ȡ���������ֵ�� -->
		
		for(var ii=0; ii<targets_ids1.length; ii++){
			var wo = 'first';
			if(targets_names1[ii] == '1'){
				wo = 'eq(1)';
			}
			$('#'+targets_ids1[ii]).next().css('cursor', 'pointer').find('table tr:'+wo+' td')
			.css('background', 'url(/css/homepage/images/red_2.gif) center top no-repeat !important');
		}
		
		for(var ii=0; ii<targets_ids1.length; ii++){
			$('#'+targets_ids1[ii]).next().unbind('click').bind('click', function(){
				var page = new pageDefine("/txn6020012.ajax?select-key:service_targets_id="+
						$(this).prev().attr('id'), "��ѯ��ǰ��������Ϣ");
				page.callAjaxService('getTaskWamingInfo');
			})
		}
	}

	function getRunInfo2(errCode, errDesc, xmlResults) {
		if (errCode != '000000') {
			alert('�������['+errCode+']==>'+errDesc);
			return;
		} 
		var share_times = _getXmlNodeValues(xmlResults, "record:service_end_time");
		var task_info = _getXmlNodeValues(xmlResults, "record:service_id");
		var targets_ids = _getXmlNodeValues(xmlResults, "record:service_targets_id");
		var targets_names = _getXmlNodeValues(xmlResults, "record:service_name");
		var str = '';
		for(var ii=0; ii<task_info.length; ii++){
			str += ' ['+targets_names[ii] + ']������.<br>'
		}
		$('#'+targets_ids[0]).next().unbind('click').bind('click', function(){
			$('#targetFrame').hide();
			$el = $(this);
			$('#show_svrTarget_info').html("<p>" + str + "</p>").next().find('a').attr('href', '#');
			$('#show_svrTarget').css({
				"top" : ($el.offset().top + $el.height()-3),
				"left" : $el.prev().offset().left
			}).show();
		});
		$("#show_svrTarget_title").html("���1Сʱִ�е�����");
	}
	
	var share_times2 = new Array;
	var task_info2 = new Array;
	var targets_ids2= new Array;
	var targets_names2 = new Array;
	var waming_infos2 = new Array;
	
	function getTaskWamingInfo(errCode, errDesc, xmlResults) {
		if (errCode != '000000') {
			alert('�������['+errCode+']==>'+errDesc);
			return;
		} 
		share_times2 = _getXmlNodeValues(xmlResults, "record:record_time");
		task_info2 = _getXmlNodeValues(xmlResults, "record:patameter");
		targets_ids2 = _getXmlNodeValues(xmlResults, "record:service_targets_id");
		targets_names2 = _getXmlNodeValues(xmlResults, "record:service_name");
		var str = '';
		for(var ii=0; ii<task_info2.length; ii++){
			str += targets_names2[ii] + ' ' + task_info2[ii] + '<br>';
		}
		$el = $('#'+targets_ids2[0]);
		$('#show_svrTarget_info').html("<p>" + str + "</p>").next().find('a').attr('href', '#');
		$('#show_svrTarget').css({
			"top" : ($el.offset().top + $el.height()-3),
			"left" : $el.offset().left
		}).show();
	}
												
	function PreY() {
		var newY = cale.PreYear();
		$("#nowYear").html(newY);
	}
	function NextY() {
		var newY = cale.NextYear();
		$("#nowYear").html(newY);
	}
	function PreM() {
		var newM = cale.PreMonth();
		$("#nowMonth").html(newM);
	}
	function NextM() {
		var newM = cale.NextMonth();
		$("#nowMonth").html(newM);
	}

	function setTaskList(errCode, errDesc, xmlResults) {
		if (errCode != '000000') {
			return;
		}
		$("#taskUl").html("");
		var tasks = _getXmlNodeValues(xmlResults, "record:task_name");
		var task_ids = _getXmlNodeValues(xmlResults, "record:collect_task_id");
		var task_start = _getXmlNodeValues(xmlResults, "record:start_time");
		var task_end = _getXmlNodeValues(xmlResults, "record:end_time");

		for ( var ii = 0; ii < tasks.length; ii++) {
			$("#taskUl").append(
					"<li title="+tasks[ii]+"[" + task_start[ii]
					+ "-" + task_end[ii] + "] id='"+task_ids[ii]+"'>" + tasks[ii]
							+"</li>");
		}
	}

	$(".inner_more").click(function() {
		$('#targetFrame').hide();
		$(".inner_hidden").toggle("speed");
	})
	$(".outer_more").click(function() {
		$('#targetFrame').hide();
		$(".outer_hidden").toggle("speed");
	})
	$(".dist_more").click(function() {
		$('#targetFrame').hide();
		$(".dist_hidden").toggle("speed");
	})

	function doTurn(val) {
		$("#content").animate({
			marginLeft : val + "px"
		}, 800);
	}

	function toTurn(type) {
		$('#targetFrame').hide();
		$('#show_svrTarget').hide();
		$('.sel2trun').removeClass('sel2turn');
		$('#' + type + "_sel").addClass('sel2turn');
		if (type == 'zwt') {
			top.rowset.rows = "50,*";
			doTurn(-width1);
		}
 		if (type == 'tjt') {
			top.rowset.rows = "50,*";
			doTurn(-width1*2);
		}
 		if (type == 'fenbu') {
			top.rowset.rows = "50,*";
			doTurn(-width1*3);
		}
 		if (type == 'qst') {
			top.rowset.rows = "50,*";
			doTurn(-width1*4);
		}
		if (type == 'home') {
			top.rowset.rows = "115,*";
			doTurn(0);
		}
	}
	function closeIframe(){
		$('#targetFrame').attr("src", "");
		$('#targetFrame').hide();
		//unbindClose();
	}
	
	//����δ����,��ʱ���ε�
	/* function bindClose(){
		$('*').not('.targetFrame').bind('click', function(){
			console.log("halo");
			//$('#targetFrame').hide();
		})
	}
	
	function unbindClose(){
		$('*').not('.targetFrame').unbind('click');
	} */
	
</script>
<form action="/txn53000012.do" method="post" id="form1" name="form1" target="targetFrame">
  <input type="hidden" name="select-key:service_targets_id" id="select-key:service_targets_id"/>
  <input type="hidden" name="select-key:service_targets_name" id="select-key:service_targets_name"/>
</form>

</freeze:body>
</freeze:html>
