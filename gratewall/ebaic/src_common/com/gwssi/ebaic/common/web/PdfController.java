
package com.gwssi.ebaic.common.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.util.JSON;
import com.gwssi.rodimus.rule.RuleUtil;
import com.gwssi.rodimus.util.FileUtil;
import com.gwssi.rodimus.util.ParamUtil;
import com.gwssi.rodimus.validate.msg.ValidateMsg;
import com.gwssi.rodimus.validate.msg.ValidateMsgEntity;

/**
 * <h3>Pdf操作</h3>
 * 
 * 
 * @author liuhailong2008#foxmail.com
 * @author liuxiangqian
 */
@Controller
@RequestMapping("/pdf")
public class PdfController {
	/**
	 * 生成并下载PDF文件。
	 * 
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/v2/getFile")
	public void getFile(OptimusRequest request,OptimusResponse response) 
			throws OptimusException{
		
		String listCode = ParamUtil.get("listCode");
		String gid = ParamUtil.get("gid");
		String chap = ParamUtil.get("chap",false);
		//type为1表示从新生成文件并下载，否在如果已经生成过则直接下载。
		String type = ParamUtil.get("type",false);
		String pdfPath = com.gwssi.rodimus.doc.v2.DocUtil.buildDoc(listCode, gid, chap,type);
		
		
		String contentType = "application/octet-stream;charset=UTF-8";
		String fileName = listCode;
		String phyFilePath = pdfPath;
		FileUtil.download(contentType, fileName,"", phyFilePath , response.getHttpResponse());
		
	}
	
	/**
	 * 生成文件
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/v2/generateFile")
	public void generateFile(OptimusRequest request,OptimusResponse response) 
			throws OptimusException{
		
		String listCode = ParamUtil.get("listCode");
		String gid = ParamUtil.get("gid");
		String chap = ParamUtil.get("chap",false);
		//type为1表示从新生成文件并下载，否在如果已经生成过则直接下载。
		String type = ParamUtil.get("type",false);
		com.gwssi.rodimus.doc.v2.DocUtil.buildDoc(listCode, gid, chap,type);
	}
	
	@RequestMapping("/v2/getHtml")
	public void getHtml(OptimusRequest request,OptimusResponse response) 
			throws OptimusException{
		
		String gid = ParamUtil.get("gid");
		String htmlString = com.gwssi.rodimus.doc.v2.DocUtil.getHtml(gid);
		response.addResponseBody(htmlString);
	}
	
	
	@RequestMapping("/check/Rule")
	public void checkRule(OptimusRequest request,OptimusResponse response){
		//获取gid
		String gid = request.getParameter("gid");
		//准备返回值的变量
		Map<String,String> map = new HashMap<String,String>();
		//调用规则   设立：数据填写检查
		ValidateMsg msg = RuleUtil.getInstance().runRule("ebaic_setup_data", gid);
		List<ValidateMsgEntity> errors = msg.getAllMsg();
		if(errors!=null && !errors.isEmpty()){
			//2表示校验失败
			map.put("check", "2");
			response.addResponseBody(JSON.toJSON(map));
			return;
		}
		//1表示校验成功
		map.put("check", "1");
		response.addResponseBody(JSON.toJSON(map));
	}

//	@RequestMapping("/test")
//	public void test(OptimusRequest request,OptimusResponse response) 
//			throws OptimusException{
//		//执行生成文件前规则校验
//		//ArchBuilder.buildXml("5782bc83ffb64ae0b85ece3708b7a067","c:\\tmp\\aa.xml");
//		com.gwssi.rodimus.doc.v2.DocUtil.addPageNo("C:\\share\\ebaic\\doc\\pdf\\110108000\\20160809\\5782bc83ffb64ae0b85ece3708b7a067\\ArchiveElecFile.pdf", "5782bc83ffb64ae0b85ece3708b7a067");
//	}
	
//	/**
//	 * 检查是否可以下载文件。
//	 * 
//	 * @param request
//	 * @param response
//	 * @throws OptimusException
//	 */
//	@RequestMapping("/validate")
//	public void validate(OptimusRequest request,OptimusResponse response) 
//			throws OptimusException{
//		String gid = ParamUtil.get("gid");
//		//执行生成文件前规则校验
//		DocUtil.validateRules(gid);
//	}
//	/**
//	 * <h2>生成pdf，并以字节流方式返回</h2>
//	 * 
//	 * @param request
//	 * @param response
//	 * @throws OptimusException
//	 */
//	@RequestMapping("/getFile")
//	public void getFile(OptimusRequest request,OptimusResponse response) 
//			throws OptimusException{
//		String gid = ParamUtil.get("gid");
//		Map<String,Object> params = new HashMap<String,Object>();
//		String pdfPath = "";
//		String listCode = "";
//		listCode = ParamUtil.get("listCode");
//		params.put("gid", gid);
//		pdfPath = DocUtil.buildDoc(listCode, params);
//		if(StringUtils.isBlank(pdfPath)){
//			throw new FileException("未找到pdf文件。");
//		}
//		//保存pdf记录
//		String pdfRelativePath = StringUtil.safe2String(params.get("pdfRelativePath"));
//		DocUtil.createPdfRecord(gid,pdfRelativePath);
//		FileInputStream fis = null;
//		OutputStream os = null;
//		
//		try {
//			File file = new File(pdfPath);
//			fis = new FileInputStream(file);
//			os = response.getHttpResponse().getOutputStream();
//			//设置头信息，采用文件形式下载
//			response.getHttpResponse().setContentType("application/octet-stream;charset=UTF-8");
//		    response.getHttpResponse().addHeader("Content-Disposition", "attachment;filename="+listCode+".pdf");
//		    response.getHttpResponse().addHeader("Content-Length", "" + file.length());
//		    
//			byte[] data = new byte[10240];
//			int len = 0;
//			while ((len = fis.read(data)) > 0) {
//				os.write(data, 0, len);
//			}
//			os.flush();
//		} catch (IOException e) {
//			throw new FileException("获取文件失败："+e.getMessage());
//		}finally{
//			IOUtils.closeQuietly(fis);
//			IOUtils.closeQuietly(os);
//		}
//	}
//	
}
