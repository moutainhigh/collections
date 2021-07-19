package com.gwssi.Contorller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.gwssi.AppConstants;
import com.gwssi.Service.PortalQueryService;
import com.gwssi.util.ErrorUtil;
import com.trs.dev4.jdk16.servlet24.ResponseUtil;

public class QueryWcmContorller extends BaseContorller {

	private static PortalQueryService portalQueryService = new PortalQueryService();

	private static Logger logger = Logger.getLogger(QueryWcmContorller.class);

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String method = request.getParameter(AppConstants.REQUEST_PARAM_NAME_METHOD);

		if ("queryDocumentMessage".equalsIgnoreCase(method)) {
			queryDocumentMessage(request, response);
			return;
		}

		String responseText = ErrorUtil.getErrorResponse("unsupported method.");
		ResponseUtil.response(response, responseText);

	}

	/****
	 * 根据条件分页查询文章
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void queryDocumentMessage(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String currentPage1 = request.getParameter(AppConstants.CURR_PAGE);
		String pageSize1 = request.getParameter(AppConstants.PAGE_SIZE);
		String docChannelId = request.getParameter(AppConstants.DOC_CHANNEL_ID);
		String title = request.getParameter(AppConstants.TITLE);
		String startCreateTime = request.getParameter(AppConstants.START_CREATE_TIME);
		String endCreateTime = request.getParameter(AppConstants.END_CREATE_TIME);
		String createName = request.getParameter(AppConstants.CREATE_NAME);
		int currentPage = 1;
		int pageSize = 20;
		if (StringUtils.isNotEmpty(currentPage1)) {
			currentPage = new Integer(currentPage1);
		}

		if (StringUtils.isNotEmpty(pageSize1)) {
			pageSize = new Integer(pageSize1);
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("docChannelId", docChannelId);
		map.put("title", title);
		map.put("startCreateTime", startCreateTime);
		map.put("endCreateTime", endCreateTime);
		map.put("createName", createName);

		BigDecimal count = (BigDecimal) portalQueryService.queryWcmDocumentByDocChannelId_page(map, currentPage,pageSize, true).get(0).get("COUN");
		map.put("coun", String.valueOf(count));

		List<Map<String, Object>> resultList = portalQueryService.queryWcmDocumentByDocChannelId_page(map, currentPage,pageSize, false);
		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
	   // String resultJSonString =	JSON.toJSONString(resultList,SerializerFeature.WriteDateUseDateFormat);

		// logger.info("queryDocumentMessage:>>>>>>>>>>>>>>>>>>>>>>\n"+
		// resultJSonString);
		DataGrid grid = new DataGrid();
		grid.setTotal(count);
		grid.setRows(resultList);
		logger.info("queryDocumentMessage:>>>>>>>>>>>>>>>>>>>>>>\n" + grid);
		ResponseUtil.response(response, JSON.toJSONString(grid));

	}

	class DataGrid {
		private BigDecimal total;
		private List rows = new ArrayList(0);

		public BigDecimal getTotal() {
			return total;
		}

		public void setTotal(BigDecimal total) {
			this.total = total;
		}

		public List getRows() {
			return rows;
		}

		public void setRows(List rows) {
			this.rows = rows;
		}

	}
}
