package com.gwssi.rodimus.doc.v1;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.gwssi.ebaic.common.service.BjcaService;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.util.DateUtil;
import com.gwssi.rodimus.config.ConfigUtil;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.doc.v1.config.SysDocChapListManager;
import com.gwssi.rodimus.doc.v1.core.arch.ArchBuilder;
import com.gwssi.rodimus.doc.v1.core.data2html.DataSupport;
import com.gwssi.rodimus.doc.v1.core.data2html.HtmlBuilder;
import com.gwssi.rodimus.doc.v1.core.html2img.ImageBuilder;
import com.gwssi.rodimus.doc.v1.core.img2pdf.PdfBuilder;
import com.gwssi.rodimus.expr.ExprUtil;
import com.gwssi.rodimus.util.FileUtil;
import com.gwssi.rodimus.util.ParamUtil;
import com.gwssi.rodimus.util.PathUtil;
import com.gwssi.rodimus.util.RequestUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.torch.util.UUIDUtil;

/**
 * <h2>文档生成工具类</h2>
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class DocUtil {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	public static final String PDF_NAME = "ArchiveElecFile.pdf";
	public static final String XML_NAME = "ArchiveMetadata.xml";
	
	
	/**
	 * 
	 * @param docCode
	 * @param gid
	 * @return
	 */
	public static String buildDoc(String docCode, String gid){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("gid", gid);
		String ret = buildDoc(docCode,params);
		return ret;
	}
	/**
	 * <h3>生成文书</h3>
	 * 
	 * <p>
	 * 配置：
	 * <ol>
	 * 	<li>图片根路径：配置项为 doc.rootpath.image。</li>
	 * 	<li>模板根路径：配置项为 doc.rootpath.template。</li>
	 * 	<li>PDF根路径：配置项为 doc.rootpath.pdf。</li>
	 * </ol>
	 * </p>
	 * 
	 * <p>
	 * 输出：
	 * <ol>
	 * 	<li>文件内容图片。</li>
	 * 	<li>目录信息。</li>
	 * 	<li>PDF文件。</li>
	 * </ol>
	 * </p>
	 * 
	 * <p>
	 * 约定：
	 * <ol>
	 * 	<li>目录结构：[]/[]/[]/[gid]/</li>
	 * 	<li>PDF文件名：fdsf.pdf</li>
	 * 	<li>图片文件名：001_[章节号]_001.pdf</li>
	 * </ol>
	 * </p>
	 * 
	 * <p>
	 * 结果信息保存：
	 * <ol>
	 * 	<li>保存在be_wk_doc表中。</li>
	 * 	<li>每个结果保存一条记录。</li>
	 * </ol>
	 * </p>
	 * 
	 * @param listCode 对应sys_doc_list表list_code字段，用于读取配置信息
	 * @param params 用于读取业务数据
	 * @return pdfPath 
	 */
	public static String buildDoc(String docCode, Map<String,Object> params){
		if(StringUtil.isBlank(docCode)){
			throw new RuntimeException("docCode不能为空。");
		}
		HttpServletRequest request = RequestUtil.getHttpRequest();
		if(request==null){
			throw new RuntimeException("文档生成功能只能在Web环境下运行。");
		}
		String url = request.getRequestURL().toString();
		String uri = request.getRequestURI();
		String reqUrl = url.substring(0,url.indexOf(uri));
		
		// 0. 准备参数
		String gid = StringUtil.safe2String(params.get("gid"));
		if(StringUtil.isBlank(gid)){
			throw new RuntimeException("gid不能为空。");
		}
		Map<String,Object> defaultParams = ParamUtil.prepareParams(gid);
		if(defaultParams!=null){
			params.putAll(defaultParams);
		}
		String date = sdf.format(new Date());
		params.put("date", date);
		// 0.1. 准备上下文
		Map<String,Object> listContext = DataSupport.buildListContext(docCode, params);
//		reqUrl=reqUrl+"/ebaic";
		
		listContext.put("reqUrl", reqUrl);
		// 0.2. 获取配置信息
		// 		r.doc_id,r.sn,r.triger_expr,d.title,d.template_url
		List<Map<String, Object>> docChapConfigList = SysDocChapListManager.instance.getConfig(docCode);
		
		String sql = "delete from be_wk_doc_chapter c where c.gid = ?";
		DaoUtil.getInstance().execute(sql, gid);
		
		// 1. 逐个生成章节
		for(Map<String, Object> chapConfig : docChapConfigList){
//			if("AABXnDAAMAACjh+AAL".equals(chapConfig.get("chapConfigId"))){
			
			String trigerExpr = StringUtil.safe2String(chapConfig.get("trigerExpr"));
			if(!StringUtil.isBlank(trigerExpr)){
				Object exprResult = null;
				try{
					exprResult = ExprUtil.run(trigerExpr, params);
				}catch(Exception e){
					exprResult = null;
					e.printStackTrace();
				}
				if(exprResult==null){
					exprResult = Boolean.FALSE;
					//throw new RuntimeException("执行表达式异常："+trigerExpr);
				}
				if(!(exprResult instanceof Boolean)){
					throw new RuntimeException("执行表达式结果类型异常："+exprResult.getClass());
				}
				Boolean resultBoolean = (Boolean)exprResult;
				if(resultBoolean.equals(Boolean.FALSE)){
					continue ;
				}
			}
			
			String chapId = StringUtil.safe2String(chapConfig.get("chapConfigId"));
			//1. data2html
			String docHtml = HtmlBuilder.buildDocHtml(chapId, listContext);
			
			//2. html2img
			ImageBuilder.buildImage(chapConfig, docHtml,params);
			
			//}
		}
		//3. img2pdf
		String pdfPath = PdfBuilder.buildPdf(params);
		
		return pdfPath;
	}

	/**
	 * 对文件签名并且声称xml文档
	 * 
	 */
	public static void SignAndCreateXml(String gid,String pdfPath,OptimusRequest optimusRequest,BjcaService bjcaService){
		if(StringUtils.isBlank(gid)){
			throw new RuntimeException("gid 不能为空。");
		}
		if(StringUtils.isBlank(pdfPath)){
			throw new RuntimeException("pdfPath 不能为空。");
		}
		HttpServletRequest request = optimusRequest.getHttpRequest();
		request.setAttribute("filePath", pdfPath);
		//1.调用签名
//		Map<String,Object> ret = bjcaService.service("sign", request);
//		
//		if(!"success".equals((String)ret.get("result"))){
//			throw new RuntimeException("签名失败！");
//		}
		
		//2.生成xml文档
		String xmlPath = pdfPath.replace(PDF_NAME, XML_NAME);
		ArchBuilder.buildXml(gid,xmlPath);
	}
	

	/**
	 * 
	 * @return 生成HTML的模板根路径
	 */
	public static String getTemplateRootPath (){
		String webRootPath = PathUtil.getWebRootPath() ;
		String ret = webRootPath + ConfigUtil.get("doc.rootpath.template.v1");
		System.out.println(ret);
		return ret;
	}

	/**
	 *  /[OrgCode]/[DATE]/[GID]/
	 *  /110100/20160612/10020002121121/
	 * 
	 * @param params
	 * @return 目录
	 */
	public static String getPath(Map<String,Object> params){
		// 中间目录，形如： /[OrgCode]/[DATE]/[GID]/
		String expr = ConfigUtil.get("doc.dir");
		String ret = fillValue(expr,params);
		return ret;
	}
	
	protected static final Pattern EXPR_PATTERN = Pattern.compile("(\\$\\{.*?\\})"); //"(\\{.*?\\})"
	private static String fillValue(String msg, Map<String, Object> context) {
		if(StringUtil.isBlank(msg)){
			return "";
		}
		Matcher matcher = EXPR_PATTERN.matcher(msg);
		String paramPlaceHolder = null;
		String dataKey = null;
		Object dataValue = null;
		String dataStringValue = null;
		String ret = msg;
		
		while(matcher.find()){
			paramPlaceHolder = matcher.group(); // 如 ： {entName}
			paramPlaceHolder = paramPlaceHolder.trim();
			if(paramPlaceHolder!=null && paramPlaceHolder.length()>3){//至少包含${和}
				dataKey = paramPlaceHolder.substring(2, paramPlaceHolder.length()-1);
				if(StringUtil.isBlank(dataKey)){
					ret = ret.replace(paramPlaceHolder, "");
					continue ;
				}
				dataKey = dataKey.trim();
				dataValue = context.get(dataKey);
				dataStringValue = StringUtil.safe2String(dataValue);
				ret = ret.replace(paramPlaceHolder, dataStringValue);
			}
		}
		
		return ret;
	}
	
	/**
	 * <h3>创建pdf记录<h3>
	 * 
	 * @param gid
	 * @param relativePath（用于保存be_wk_file表的file_path字段）
	 */
	public static void createPdfRecord(String gid,String absolutePath){
		if(StringUtils.isBlank(gid)){
			throw new RuntimeException("gid不能为空。");
		}
		if(StringUtils.isBlank(absolutePath)){
			throw new RuntimeException("pdf相对路径不能为空。");
		}
		//删除之前产生的记录
		String sql = "delete from be_wk_file f where f.file_id in (select file_id from be_wk_upload_file where gid=? and category_id='P')";
		DaoUtil.getInstance().execute(sql, gid);
		sql = "delete from be_wk_upload_file f where f.gid=? and f.category_id='P'";
		DaoUtil.getInstance().execute(sql, gid);
		//插入记录
		String fid = UUIDUtil.getUUID();
		String fileId = UUIDUtil.getUUID();
		Calendar now = DateUtil.getCurrentDate();
		sql="insert into be_wk_upload_file f(f.f_id,f.file_id,f.gid,f.timestamp,f.category_id) values(?,?,?,?,?)";
		DaoUtil.getInstance().execute(sql, fid,fileId,gid,now,"P");
		sql="insert into be_wk_file f(f.file_id,f.suffix_name,f.content_type,f.timestamp,f.file_path,f.file_name) values(?,?,?, ?,?,?)";
		DaoUtil.getInstance().execute(sql,fileId,"pdf","application/pdf",now,absolutePath,"ArchiveElecFile.pdf");
	
		//存相对路径，供在线预览pdf使用，doc/为配置的文件虚拟路径
		sql="update be_wk_requisition r set r.doc_file_id = ? ,r.doc_relative_path=? where r.gid=? ";
		String relativePath = absolutePath.replace("/share/ebaic/doc/pdf/", "../../../doc/");
		DaoUtil.getInstance().execute(sql,fileId,relativePath,gid);
	
	}
	
	/**
	 * 生成文件前规则校验
	 * 
	 * @param gid
	 */
	public static void validateRules(String gid){
		if(StringUtils.isBlank(gid)){
			throw new RuntimeException("gid 不能为空。");
		}
		//法定代表人授权校验
		StringBuffer sbBuffer = new StringBuffer(" select r.auth_type, a.auth_id ");
		sbBuffer.append(" from be_wk_requisition r left join be_wk_auth a on r.gid = a.gid ");
		sbBuffer.append(" and a.is_le_rep_auth = '1' and a.flag = '1' and a.end_date >= sysdate ");
		sbBuffer.append(" and a.to_user_id = r.app_user_id and instr(a.opr_type, r.operation_type)>0 where r.gid = ? ");
		Map<String, Object> result = DaoUtil.getInstance().queryForRow(sbBuffer.toString(), gid);
		String authType = StringUtil.safe2String(result.get("authType"));
		String authId = StringUtil.safe2String(result.get("authId"));
		if(StringUtils.isBlank(authId)&&StringUtils.isBlank(authType)){
			sbBuffer.setLength(0);
			sbBuffer.append(" select count(1) from be_wk_le_rep le,be_wk_requisition r,t_pt_yh y ");
			sbBuffer.append(" where y.user_id = r.app_user_id and r.gid = le.gid ");
			sbBuffer.append(" and y.cer_no = le.le_rep_cer_no and r.gid=? ");
			long num = DaoUtil.getInstance().queryForOneLong(sbBuffer.toString(), gid);
			if(num<1){
				throw new RuntimeException("请完成法定代表人授权后再下载文件。");
			}
			return;
		}else if("1".equals(authType)&&StringUtils.isBlank(authId)){
			throw new RuntimeException("请完成法定代表人授权后再下载文件。");
		}else if("0".equals(authType)&&StringUtils.isBlank(authId)){
			return;
		}else if("1".equals(authType)&&!StringUtils.isBlank(authId)){
			return;
		}else{
			throw new RuntimeException("法定代表人授权数据异常，请联系管理员。");
		}
	}
	
	
	
	
	/**
	 * 
	 * chaiyoubing   html2pdf
	 * 
	 * 
	 */
	public static String html2pdf(String docCode, Map<String,Object> params){
		if(StringUtil.isBlank(docCode)){
			throw new RuntimeException("docCode不能为空。");
		}
		HttpServletRequest request = RequestUtil.getHttpRequest();
		if(request==null){
			throw new RuntimeException("文档生成功能只能在Web环境下运行。");
		}
		String url = request.getRequestURL().toString();
		String uri = request.getRequestURI();
		String reqUrl = url.substring(0,url.indexOf(uri));
		
		// 0. 准备参数
		String gid = StringUtil.safe2String(params.get("gid"));
		if(StringUtil.isBlank(gid)){
			throw new RuntimeException("gid不能为空。");
		}
		Map<String,Object> defaultParams = ParamUtil.prepareParams(gid);
		if(defaultParams!=null){
			params.putAll(defaultParams);
		}
		String date = sdf.format(new Date());
		params.put("date", date);
		// 0.1. 准备上下文
		Map<String,Object> listContext = DataSupport.buildListContext(docCode, params);
		listContext.put("reqUrl", reqUrl);
		
		// 0.2. 获取配置信息
		List<Map<String, Object>> docChapConfigList = SysDocChapListManager.instance.getConfig(docCode);
		
		String sql = "delete from be_wk_doc_chapter c where c.gid = ?";
		DaoUtil.getInstance().execute(sql, gid);

		//构造html头部，包含样式
		String allHtml="<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"      
				+"<html xmlns=\"http://www.w3.org/1999/xhtml\">"
				+"<head>"
				+"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />"
				+"<style type=\"text/css\" mce_bogus=\"1\">";
		
		String cssString = FileUtil.readFile("C:/xiaoqi/workspace_dj/ebaic/WebContent/static/css/pdf-template.css");
		allHtml+=cssString;//样式
		
		allHtml+="</style></head><body>";
		// 1. 逐个生成章节
		for(Map<String, Object> chapConfig : docChapConfigList){
			String trigerExpr = StringUtil.safe2String(chapConfig.get("trigerExpr"));
			if(!StringUtil.isBlank(trigerExpr)){
				Object exprResult = ExprUtil.run(trigerExpr, params);
				if(exprResult==null){
					throw new RuntimeException("执行表达式异常："+trigerExpr);
				}
				if(!(exprResult instanceof Boolean)){
					throw new RuntimeException("执行表达式结果类型异常："+exprResult.getClass());
				}
				Boolean resultBoolean = (Boolean)exprResult;
				if(resultBoolean.equals(Boolean.FALSE)){
					continue ;
				}
			}
			
			String chapId = StringUtil.safe2String(chapConfig.get("chapConfigId"));
			//1. data2html
			String docHtml = HtmlBuilder.buildDocHtml(chapId, listContext);
			allHtml+=docHtml;
		}
		allHtml+="</body></html>";
		//2. html2pdf
		params.put("allHtml", allHtml);
		String pdfPath = PdfBuilder.html2pdf(params);
		
		return pdfPath;
	}
}
