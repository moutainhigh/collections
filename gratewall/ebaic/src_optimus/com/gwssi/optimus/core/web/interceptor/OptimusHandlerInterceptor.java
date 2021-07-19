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
import java.util.HashMap;
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
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.util.JSON;
import com.gwssi.rodimus.log.ExceptionLogUtil;
import com.gwssi.rodimus.util.RequestMethodValidateUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;


public class OptimusHandlerInterceptor implements HandlerInterceptor{
    
    private final static Logger logger = LoggerFactory.getLogger(OptimusHandlerInterceptor.class);
    
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object paramObject)
            throws Exception {
    	RequestMethodValidateUtil.validate(request); 
        ThreadLocalManager.add(ThreadLocalManager.RAW_HTTP_REQUEST, request);
        ThreadLocalManager.add(ThreadLocalManager.RAW_HTTP_RESPONSE, response);
        return true;
    }

    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object paramObject,
            ModelAndView modelAndView){
//        DAOManager.clearThreadDAOCache();
        OptimusResponse optimusResp = (OptimusResponse)ThreadLocalManager
            .get(ThreadLocalManager.OPTIMUS_RESPONSE);
        if(optimusResp==null)
            return;
        if(modelAndView!=null)
            modelAndView.clear();
        String encoding = request.getCharacterEncoding();
        if(StringUtils.isEmpty(encoding))
            encoding = "UTF-8";
        if(StringUtils.isNotEmpty(optimusResp.getResponseBody())){
            responseString(response, optimusResp.getResponseBody(), encoding);
        }else if(optimusResp.getDownloadFile()!=null){
            processDownload(response, optimusResp, encoding);
        }else if(StringUtils.isNotEmpty(optimusResp.getPage())){
            String page = optimusResp.getPage();
            if(page.startsWith("redirect:") || page.startsWith("forward:")){
                modelAndView.setViewName(page);
                optimusResp.addPage(null);
            }else if(page.endsWith(".oftl")){
                processFtl(request, response, page);
            }else{
                processPage(request, response, optimusResp, encoding);
            }
        }else{
            processJson(response, optimusResp, encoding);
        }
    }
    
    private void processPage(HttpServletRequest request, HttpServletResponse response, 
            OptimusResponse optimusResp, String encoding){
        if(response.isCommitted())
            return;
        response.addHeader("Content-Type", "text/html;charset="+encoding);
        try {
            String page = optimusResp.getPage();
            String jsonDataStr = optimusResp.allData2JSON();
            if(page.contains(".jsp")){
                request.getRequestDispatcher(page).include(request, response);
//                if(StringUtils.isNotEmpty(jsonDataStr)){
//                    request.setAttribute("OptimusData", jsonDataStr.replaceAll("'", "&apos;"));
//                    request.getRequestDispatcher("/page/common/pageData.jsp").include(request, response);
//                }
            }else{
                String pageData = "";
                PrintWriter localPrintWriter = null;
                if(StringUtils.isNotEmpty(jsonDataStr)){
                    pageData = "<div id=\"OptimusData\"  style='display:none;' data='"
                        + jsonDataStr.replaceAll("'", "&apos;") + "'></div>";
                    localPrintWriter = new PrintWriter(new OutputStreamWriter(
                        response.getOutputStream(), encoding));
                }
                request.getRequestDispatcher(page).include(request, response);
                if(localPrintWriter!=null){
                    localPrintWriter.println(pageData);
                    localPrintWriter.flush();
                    localPrintWriter.close();
                }
            }
        } catch (Exception e) {
            logger.error("返回http信息异常", e);
        } 
    }
    
    private void processJson(HttpServletResponse response, 
            OptimusResponse optimusResp, String encoding){
        if(response.isCommitted())
            return;
        responseString(response, optimusResp.allData2JSON(), encoding);
    }
    
    /**
     * 返回json信息
     * @param response
     * @param jsonStr
     * @param encoding
     */
    private void responseString(HttpServletResponse response, 
            String content, String encoding){
        if(response.isCommitted())
            return;
        response.addHeader("Content-Type", "text/html;charset="+encoding);
        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
            printWriter.write(content);
        } catch (Exception e) {
            logger.error("返回http信息异常", e);
        } finally {
            if(printWriter!=null){
                printWriter.flush();
                printWriter.close();
            }
        }
    } 
    
    private void processDownload(HttpServletResponse response, 
            OptimusResponse optimusResp, String encoding){
        if(response.isCommitted())
            return;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        OutputStream fos = null;
        InputStream fis = null;
        File file = null;
        boolean deleteAfterDone = false;
        try {
            @SuppressWarnings("unchecked")
			Map<String,Object> downloadFile = optimusResp.getDownloadFile();        
            file = (File)downloadFile.get("file");
            String fileName = (String)downloadFile.get("fileName");
            deleteAfterDone = (Boolean)downloadFile.get("deleteAfterDone");
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            fos = response.getOutputStream();
            bos = new BufferedOutputStream(fos);
            //x-download
            response.setContentType("application/octet-stream;charset="+encoding);
//            response.setHeader("Content-disposition",
//                  "attachment;filename="+URLEncoder.encode(fileName, encoding));
            String downloadName = new String(fileName.getBytes("utf-8"), "iso8859-1");
            response.setHeader("Content-disposition", "attachment;filename="+downloadName);
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
                if(fis!=null)
                    fis.close();
                if(bis!=null)
                    bis.close();
                if(fos!=null)
                    fos.close();
                if(bos!=null)
                    bos.close();
                if(deleteAfterDone && file!=null && file.exists())
                    file.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void processFtl(HttpServletRequest req, HttpServletResponse res, String path){
        if(res.isCommitted())
            return;
        res.setContentType("text/html;charset=utf-8");
        res.setCharacterEncoding("UTF-8");
//        Configuration ftlConfig = (Configuration)req.getServletContext().getAttribute(Constants.FTL_CONFIGURATION_LABEL);
        Configuration ftlConfig = Constants.FTL_CONFIG;
        try {
            path = Constants.FTL_PATH_PREFIX + path; 
            Template template = ftlConfig.getTemplate(path);
            template.process(null, res.getWriter());
        } catch (Exception e) {
            logger.error("处理ftl异常", e);
        }
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
            Object paramObject, Exception exception) throws Exception {
        //clear
        ThreadLocalManager.clear();
        if (exception != null) {
        	ExceptionLogUtil.log(request, paramObject,exception);
            //logger.error("请求过程发生异常", exception);
            if(response.isCommitted())
                return;
            Map<String,String> exceptionMap = new HashMap<String,String>();
            if (exception instanceof OptimusException) {
                OptimusException optimusException = (OptimusException) exception;
                Throwable rootCause = optimusException.getRootCause();
                String exceptionName = rootCause == null ? "OptimusException" : rootCause
                        .getClass().getName();
                exceptionMap.put("exception", exceptionName);
                exceptionMap.put("exceptionName", exceptionName);
                exceptionMap.put("exceptionMes", optimusException.getMessage());
            } else {
                String exceptionName = exception.getClass().getName();
                exceptionMap.put("exception", exceptionName);
                exceptionMap.put("exceptionName", exceptionName);
                exceptionMap.put("exceptionMes", exception.getMessage());
                response.setStatus(500);
            }
            
            OptimusResponse optimusResp = (OptimusResponse) ThreadLocalManager
                    .get(ThreadLocalManager.OPTIMUS_RESPONSE);
            
            if (optimusResp != null && StringUtils.isNotEmpty(optimusResp.getPage())) {
                    //跳转到错误页面
            } else {
                String encoding = request.getCharacterEncoding();
                if (StringUtils.isEmpty(encoding))
                    encoding = "UTF-8";
                responseString(response, JSON.toJSON(exceptionMap), encoding);
            }
        }
    }

}
