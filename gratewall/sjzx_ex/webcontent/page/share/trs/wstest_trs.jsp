<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<%@ page import="java.util.*"%>
<%@ page import="com.gwssi.common.constant.ShareConstants"%>
<%@ page import="com.gwssi.common.constant.CollectConstants"%>
<%@ page import="com.gwssi.socket.share.server.ServerService"%>
<%@ page import="com.gwssi.webservice.server.GSGeneralWebService"%>
<%@ page import="java.io.*"%>
<%@ page import="java.net.*"%>
<%@ page import="com.gwssi.common.constant.ExConstant"%>
<%@ page import="com.gwssi.common.util.AnalyCollectFile"%>
<%@ page import="jxl.write.WritableWorkbook"%>
<%@ page import="jxl.write.WritableSheet"%>
<%@ page import="jxl.write.Label"%>
<%@ page import="jxl.Workbook"%>
<%@ page import="java.io.File"%>
<%@ page import="com.gwssi.common.util.*"%>
<%@ page import="cn.gwssi.common.component.logger.TxnLogger"%>
<%@ page import="org.apache.log4j.Logger"%>
<freeze:html>
<head>
<style type="text/css">
.odd1,.odd12 {
	height: auto;
}
</style>
</head>
<script language="javascript">
	
</script>
<freeze:body>
	<%
		Logger logger = TxnLogger.getLogger("wstest.jsp");
				String trs_data_base = request
						.getParameter("trs_data_base");
				Map columnMap = new HashMap();
				if (trs_data_base.split(",").length == 1) {
					java.util.ResourceBundle bundle = ResourceBundle
							.getBundle("trs");

					String base = trs_data_base.split(",")[0];
					String keys = ValueSetCodeUtil.getPropertiesByKey(
							"trs", base + "_key");
					String values = ValueSetCodeUtil.getPropertiesByKey(
							"trs", base + "_value");
					if (!keys.equals("")) {
						String[] ks = keys.split(",");
						String[] vs = values.split(",");
						if (ks.length == vs.length) {
							for (int i = 0; i < ks.length; i++) {
								columnMap.put(ks[i], vs[i]);
							}
						}
					}
				}

				try {
					String SVR_CODE = request.getParameter("SVR_CODE");
					String queryStr = request.getParameter("queryStr");

					Enumeration enu = request.getParameterNames();
					HashMap map = new HashMap();
					map.put("USER_TYPE", "TEST");
					map.put("SVR_CODE", SVR_CODE);
					map.put("queryStr", queryStr);
					map.put("rows", "1");
					map.put("nPage", "1");
					String param = XmlToMapUtil.map2Dom(map);
					GSGeneralWebService ws = new GSGeneralWebService();
					String dom = (String) ws.queryTrsDataByHttp(param, "");
					Map result = XmlToMapUtil.dom2Map(dom);

					Map mm = new HashMap();
					List list = new ArrayList();
					if ("BAIC0000".equals(result.get("FHDM").toString())) {
						if(null!=result.get("data")&& !result.get("data").equals("")){
							mm = (HashMap) result.get("data");
							if (null != mm.get("row")) {
								if (mm.get("row") instanceof Map) {
									list = new ArrayList();
									list.add(mm.get("row"));
								} else {
									list = (List) mm.get("row");
								}
							}
						}
					}
					//HashMap[] mapArray = new HashMap[list.size()];
					List dataList = new ArrayList();
					for (int i = 0; i < list.size(); i++) {
						HashMap m = (HashMap) list.get(i);
						dataList.add(m);
					}
	%>

	<table width="100%" align="center" cellspacing="0" cellpadding="0"
		class="frame-body" id="chaxun">
		<tr>
			<td colspan="4">
				<table width="100%" style="border-collapse: collapse;"
					cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="odd12">测试结果</td>
					</tr>
				</table>
			</td>
		</tr>


		<tr>
			<td>
				<table cellspacing="0" cellpadding="0" width="100%"
					style="border: 1px solid rgb(207, 207, 254); background-color: #f7f7f7; border-collapse: collapse;">

					<tr align="center" class="framerow">
						<td class="odd1" width="20%" align="right">返回代码：</td>
						<td class="odd1" width="30%" align="left">
							<div id="code" class="entNameContentDiv" align="left">
								<%
									String fhdm = result.get("FHDM").toString();
												String error_msg = ValueSetCodeUtil.getPropertiesByKey(
														"share_error", fhdm);
												out.print(error_msg);
								%>
							</div>
						</td>
						<td class="odd1" width="20%" align="right">展示条数：</td>
						<td class="odd1" width="30%" align="left"><div id="name"
								class="entRegNoContentDiv" align="left">1</div></td>
					</tr>

				</table>


			</td>
		</tr>


	</table>
	<br />
	<div id="tbk" style="overflow: auto; width: 100%; height: 250px;">
		<table align="center" width="100%" cellspacing="0" cellpadding="0"
			class="frame-body" id="chaxun">
			<%
				if (dataList == null || dataList.size() == 0) {
			%>
			<tr>
				<td colspan="4">
					<table width="100%" style="border-collapse: collapse;"
						cellspacing="0" cellpadding="0" border="0">
						<tr>
							<td class="odd12">结果列表</td>
						</tr>
					</table> <script type="text/javascript">
						document.getElementById("tbk").style.height = '80px';
					</script>
				</td>
			</tr>
			<tr>
				<td colspan="4" align="center">
					<table
						style="border-collapse: collapse; border: 1px solid rgb(207, 207, 254); background-color: #f7f7f7; width: 100%;">
						<tr>
							<td align="center" class="odd1"><font color="red" size="4px">无符合条件的记录！</font>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<%
				} else {
			%>

			<tr>
				<td>
					<table width="100%" style="border-collapse: collapse;"
						cellspacing="0" cellpadding="0" border="0">
						<tr>
							<td class="odd12">结果列表</td>
						</tr>
					</table>
				</td>
			</tr>
			<%
				HashMap m = (HashMap) dataList.get(0);
								Iterator it = m.keySet().iterator();
								Object key;
								Object value;
			%>
			<tr>
				<td class="odd12" align="left">
					<%
						while (it.hasNext()) {
											key = it.next();
											value = m.get(key);
											if (columnMap.size() > 0) {
												out.print("["+columnMap.get(key) + "]：" + value
														+ "<br>");
											} else {
												out.print("["+key + "]:" + "：" + value + "<br>");
											}
										}
					%>
				</td>
			</tr>

			<%
				}
						} catch (Exception e) {
							e.printStackTrace();
							logger.debug(e.getMessage());
						}
			%>
		</table>
	</div>
</freeze:body>
</freeze:html>