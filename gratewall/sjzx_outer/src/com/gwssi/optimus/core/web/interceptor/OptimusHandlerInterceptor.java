package com.gwssi.optimus.core.web.interceptor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.gwssi.optimus.core.common.Constants;
import com.gwssi.optimus.core.common.ThreadLocalManager;
import com.gwssi.optimus.core.web.event.OptimusResponse;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class OptimusHandlerInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory
			.getLogger(OptimusHandlerInterceptor.class);

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object paramObject) throws Exception {
		ThreadLocalManager.add("http_request", request);
		ThreadLocalManager.add("http_response", response);
		return true;
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object paramObject,
			ModelAndView modelAndView) {
		OptimusResponse optimusResp = (OptimusResponse) ThreadLocalManager
				.get("optimus_response");
		if (optimusResp == null)
			return;
		if (modelAndView != null)
			modelAndView.clear();
		String encoding = request.getCharacterEncoding();
		if (StringUtils.isEmpty(encoding))
			encoding = "UTF-8";
		if (StringUtils.isNotEmpty(optimusResp.getResponseBody())) {
			responseString(response, optimusResp.getResponseBody(), encoding);
		} else if (optimusResp.getDownloadFile() != null) {
			processDownload(response, optimusResp, encoding);
		} else if (StringUtils.isNotEmpty(optimusResp.getPage())) {
			String page = optimusResp.getPage();
			if ((page.startsWith("redirect:")) || (page.startsWith("forward:"))) {
				modelAndView.setViewName(page);
				optimusResp.addPage(null);
			} else if (page.endsWith(".oftl")) {
				processFtl(request, response, page);
			} else {
				processPage(request, response, optimusResp, encoding);
			}
		} else {
			processJson(response, optimusResp, encoding);
		}
	}

	private void processPage(HttpServletRequest request,
			HttpServletResponse response, OptimusResponse optimusResp,
			String encoding) {
		if (response.isCommitted())
			return;
		response.addHeader("Content-Type", "text/html;charset=" + encoding);
		try {
			String page = optimusResp.getPage();
			String jsonDataStr = optimusResp.allData2JSON();
			String pageData = "";
			PrintWriter localPrintWriter = null;
			
			if (page.contains(".jsp")) {
                request.getRequestDispatcher(page).include(request, response);
                if(StringUtils.isNotEmpty(jsonDataStr)){
	                request.setAttribute("OptimusData", jsonDataStr.replaceAll("'", "&apos;"));
	                request.getRequestDispatcher("/page/common/pageData.jsp").include(request, response);
                }
				return;
			}
			
			if (StringUtils.isNotEmpty(jsonDataStr)) {
				pageData = "<div id=\"OptimusData\"  style='display:none;' data='"
						+ jsonDataStr.replaceAll("'", "&apos;") + "'></div>";
				localPrintWriter = new PrintWriter(new OutputStreamWriter(
						response.getOutputStream(), encoding));
			}
			request.getRequestDispatcher(page).include(request, response);
			if (localPrintWriter != null) {
				localPrintWriter.println(pageData);
				localPrintWriter.flush();
				localPrintWriter.close();
			}
		} catch (Exception e) {
			logger.error("返回http信息异常", e);
		}
	}

	private void processJson(HttpServletResponse response,
			OptimusResponse optimusResp, String encoding) {
		if (response.isCommitted())
			return;
		responseString(response, optimusResp.allData2JSON(), encoding);
	}

	private void responseString(HttpServletResponse response, String content,
			String encoding) {
		if (response.isCommitted())
			return;
		response.addHeader("Content-Type", "text/html;charset=" + encoding);
		PrintWriter printWriter = null;
		try {
			printWriter = response.getWriter();
			printWriter.write(content);
		} catch (Exception e) {
			logger.error("返回http信息异常", e);
		} finally {
			if (printWriter != null) {
				printWriter.flush();
				printWriter.close();
			}
		}
	}

	private void processDownload(HttpServletResponse response,
			OptimusResponse optimusResp, String encoding) {
		if (response.isCommitted())
			return;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		OutputStream fos = null;
		InputStream fis = null;
		File file = null;
		boolean deleteAfterDone = false;
		try {
			Map downloadFile = optimusResp.getDownloadFile();
			file = (File) downloadFile.get("file");
			String fileName = (String) downloadFile.get("fileName");
			deleteAfterDone = ((Boolean) downloadFile.get("deleteAfterDone"))
					.booleanValue();
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			fos = response.getOutputStream();
			bos = new BufferedOutputStream(fos);

			response.setContentType("application/octet-stream;charset="
					+ encoding);

			String downloadName = new String(fileName.getBytes("utf-8"),
					"iso8859-1");
			response.setHeader("Content-disposition", "attachment;filename="
					+ downloadName);
			int bytesRead = 0;
			byte[] buffer = new byte[4096];
			while ((bytesRead = bis.read(buffer, 0, 4096)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
			bos.flush();
		} catch (Exception e) {
			logger.error("处理文件下载异常", e);
		} finally {
			try {
				if (fis != null)
					fis.close();
				if (bis != null)
					bis.close();
				if (fos != null)
					fos.close();
				if (bos != null)
					bos.close();
				if ((deleteAfterDone) && (file != null) && (file.exists()))
					file.delete();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void processFtl(HttpServletRequest req, HttpServletResponse res,
			String path) {
		if (res.isCommitted())
			return;
		res.setContentType("text/html;charset=utf-8");
		res.setCharacterEncoding("UTF-8");

		Configuration ftlConfig = Constants.FTL_CONFIG;
		try {
			path = "/ftl" + path;
			Template template = ftlConfig.getTemplate(path);
			template.process(null, res.getWriter());
		} catch (Exception e) {
			logger.error("处理ftl异常", e);
		}
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object paramObject,
			Exception exception) throws Exception {
		ThreadLocalManager.clear();
	}
}