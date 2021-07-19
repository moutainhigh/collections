package com.gwssi.rodimus.doc.v2;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.gwssi.ebaic.common.util.UploadFilePathUtil;
import com.gwssi.ebaic.common.util.UploadUtil;
import com.gwssi.rodimus.config.ConfigUtil;
import com.gwssi.rodimus.dao.BaseDaoUtil;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.doc.v1.config.SysDocChapListManager;
import com.gwssi.rodimus.doc.v1.core.arch.ArchBuilder;
import com.gwssi.rodimus.doc.v1.core.data2html.DataSupport;
import com.gwssi.rodimus.doc.v2.core.data2html.HtmlBuilder;
import com.gwssi.rodimus.doc.v2.core.html2pdf.PdfBuilder;
import com.gwssi.rodimus.doc.v2.core.util.PdfUtil;
import com.gwssi.rodimus.expr.ExprUtil;
import com.gwssi.rodimus.util.FileUtil;
import com.gwssi.rodimus.util.ParamUtil;
import com.gwssi.rodimus.util.PathUtil;
import com.gwssi.rodimus.util.RequestUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.rodimus.util.UUIDUtil;

/**
 * <h2>文档生成工具类</h2>
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class DocUtil {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	/**
	 * PDF文件名。
	 */
	public static final String PDF_NAME = "ArchiveElecFile.pdf";
	/**
	 * 存档XML文件名，烂设计，把不相关的两件事情扯到一块了。
	 */
	public static final String XML_NAME = "ArchiveMetadata.xml";
	
	/**
	 * 
	 */
	public static final String WEB_URL = "http://160.100.0.92:7001";
	/**
	 * 
	 * @return 生成HTML的模板根路径。
	 */
	public static String getTemplateRootPath (){
		String webRootPath = PathUtil.getWebRootPath() ;
		String ret = webRootPath + ConfigUtil.get("doc.rootpath.template.v2");
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
		return com.gwssi.rodimus.doc.v1.DocUtil.getPath(params);
	}
	
	
	/**
	 * <h3>创建pdf记录<h3>
	 * 
	 * @param gid
	 * @param relativePath（用于保存be_wk_file表的file_path字段）
	 */
	protected static void createPdfRecord(String gid,String absolutePath){
		com.gwssi.rodimus.doc.v1.DocUtil.createPdfRecord(gid, absolutePath);
	}
	
	
	/**
	 * 
	 * @param docCode
	 * @param gid
	 * @return
	 */
	public static String buildDoc(String docCode, String gid,String chap,String type){
		if(StringUtils.isBlank(docCode)){
			throw new RuntimeException("“docCode”不能为空。 ");
		}
		if(StringUtils.isBlank(gid)){
			throw new RuntimeException("“gid”不能为空。 ");
		}
		/**
		 * 文书生成前：
		 * 根据cer_type和cer_no关联,把身份认证表(sysmgr_identity)中的人员记录的手机号复制到对应的股东、主要人员、法人表中，
		 */
		copyIdentityMobile(gid);
		 /**
		  * 文书生成前： 
		  * 1、如果be_wk_le_rep、be_wk_investor中有经过实名认证的人员或非自然人股东，
		  *   删除be_wk_upload_file 表的记录
		  * 2、从sysmgr_identity、sysmgr_identity_picture表中取出对应的图片，执行合成操作后
		  * 3、新增be_wk_upload_file表中对应的记录，保存到be_wk_upload_file
		  *   表 catagory_id='member'、'inv_cp'和'inv_person'的记录，
		  */
		copyIdentityFile(gid);
		
		String ret = "";
		//重新生成文件
		if("1".equals(type)){
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("gid", gid);
			ret = buildDoc(docCode,params,chap);
			return ret;
		}else if("2".equals(type)){//如果发生变更则生成，否则直接返回
			//如果文件发生变更
			if(haveChange(gid)){
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("gid", gid);
				ret = buildDoc(docCode,params,chap);
			}else{
				ret = getPdfFile(gid);
				if(StringUtils.isBlank(ret)){
					Map<String,Object> params = new HashMap<String,Object>();
					params.put("gid", gid);
					ret = buildDoc(docCode,params,chap);
				}
			}
			return ret;
		}else{//直接下载
			ret = getPdfFile(gid);
			if(StringUtils.isBlank(ret)){
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("gid", gid);
				ret = buildDoc(docCode,params,chap);
			}
			return ret;
		}
	}
	/**
	 * 复制已经进行身份认证的自然人股东、非自然人手机号码 从sysmgr_identity到be_wk_investor
	 * 复制已经进行身份认证的法定代表人手机号码 从sysmgr_identity到be_wk_le_rep
	 * and inde.type='0'--0--身份认证  2-- 身份核查
	 * and (inde.flag='0' or inde.flag='1') --0 待审核   1 正常
	 */
	public static void copyIdentityMobile(String gid){
		if(StringUtils.isBlank(gid)){
			throw new RuntimeException("“gid”不能为空。");
		}
		//1、查询已经认证过的自然人股东信息
		StringBuffer sql = new StringBuffer();
		sql.append(" select inv.investor_id,inv.cer_type,inv.cer_no,inde.mobile from be_wk_investor inv, sysmgr_identity inde where inv.cer_type = inde.cer_type and inv.cer_no = inde.cer_no  and inde.type='0' and (inde.flag='0' or inde.flag='1') and inv.gid = ? ");
		List<Map<String, Object>> list = DaoUtil.getInstance().queryForList(sql.toString(), gid);
		
		beginCopyMobile(list,gid);
		
		//2、查询已经认证过的非自然人股东信息
		sql.delete(0, sql.length());
		sql.append(" select inv.investor_id,inv.b_lic_type as cer_type,inv.b_lic_no as  cer_no,inde.mobile from be_wk_investor inv, sysmgr_identity inde where   inv.b_lic_type= inde.cer_type and  inv.b_lic_no= inde.cer_no and inde.type='0' and (inde.flag='0' or inde.flag='1') and inv.gid = ? ");
		list = DaoUtil.getInstance().queryForList(sql.toString(), gid);
		
		beginCopyMobile(list,gid);
		
		//3、查询已经认证过的法定代表人信息,并且复制
		sql.delete(0, sql.length());
		sql.append(" select inde.mobile,lp.le_rep_cer_type,lp.le_rep_cer_no,lp.le_rep_id from be_wk_le_rep lp, sysmgr_identity inde ")
		   .append(" where lp.le_rep_cer_type = inde.cer_type ")
		   .append(" and lp.le_rep_cer_no = inde.cer_no and inde.type = '0' and (inde.flag = '0' or inde.flag = '1') and lp.gid = ? ");
		Map<String, Object> row = DaoUtil.getInstance().queryForRow(sql.toString(), gid);
		if(!row.isEmpty()){
			String mobile = (String)row.get("mobile");
			String leRepId = (String)row.get("leRepId");
			sql.delete(0, sql.length());
			sql.append(" update be_wk_le_rep lp set lp.le_rep_mob = ? where lp.gid = ? and lp.le_rep_id= ? ");
			DaoUtil.getInstance().execute(sql.toString(), mobile,gid,leRepId);
		}
		
	}
	/**
	 * 将手机端经过身份认证的股东，法定代表人的手机号码，拷贝到对应的表当中
	 * @param list
	 * @param gid
	 */
	public static void beginCopyMobile(List<Map<String, Object>> list,String gid){
		StringBuffer sql = new StringBuffer();
		Iterator<Map<String, Object>> iterator = list.iterator();
		String mobile = "";
		String investorId = "";
		while(iterator.hasNext()){
			Map<String, Object> next = iterator.next();
			mobile =(String)next.get("mobile");
			investorId =(String)next.get("investorId");
			sql.delete(0, sql.length());
			sql.append(" update be_wk_investor inv set inv.mobile = ? where inv.gid=? and inv.investor_id=? ");
			if(StringUtil.isNotBlank(mobile)){//认证手机号不为空，才可以更新
				DaoUtil.getInstance().execute(sql.toString(), mobile,gid,investorId);
			}
		}
	}
	
	/**
	 * 检查be_wk_upload_file表 catagory_id='member'、'inv_cp'和'inv_person'的记录
	 * 如果be_wk_le_rep、be_wk_investor中有经过实名认证的法定代表人、自然人股东、非自然人股东，
	 * 但是在be_wk_upload_file表中没有对应的记录。
	 * 从sysmgr_identity、sysmgr_identity_picture表中取出对应的图片，
	 * 执行合成操作后，保存到be_wk_upload_file表中
	 */
	public static void copyIdentityFile(String gid){
		if(StringUtils.isBlank(gid)){
			throw new RuntimeException("“gid”不能为空。");
		}
		//1、复制自然人上传的文件
		StringBuffer sql = new StringBuffer();
		sql.append(" select inv.investor_id,inv.inv,inde.identity_id from be_wk_investor inv, sysmgr_identity inde where inv.cer_type = inde.cer_type and inv.cer_no = inde.cer_no  and inde.type='0' and (inde.flag='0' or inde.flag='1') and inv.gid = ? ");
		List<Map<String, Object>> list = DaoUtil.getInstance().queryForList(sql.toString(), gid);
		
		beginCopyFiles(list,"inv_person",gid);
		
		//2、复制非自然人上传的文件
		sql.delete(0, sql.length());
		sql.append(" select inv.investor_id,inde.identity_id ,inv.inv from be_wk_investor inv, sysmgr_identity inde where inv.b_lic_no= inde.cer_no and inde.type='0' and (inde.flag='0' or inde.flag='1') and inv.gid = ? ");
		list = DaoUtil.getInstance().queryForList(sql.toString(), gid);
		
		beginCopyFiles(list,"inv_cp",gid);
		
		
		//3、复制主要人员经过身份认证的人的文件
		sql.delete(0, sql.length());
		sql.append(" select mbr.entmember_id as investor_id,mbr.name as inv, inde.identity_id from be_wk_entmember mbr, sysmgr_identity inde where mbr.cer_type = inde.cer_type ")
		   .append(" and mbr.cer_no = inde.cer_no and inde.type = '0' and (inde.flag = '0' or inde.flag = '1') and mbr.gid = ? ");
		list = DaoUtil.getInstance().queryForList(sql.toString(), gid);
		
		beginCopyFiles(list,"member",gid);
		
		
	}
	/**
	 * 将手机端经过身份认证的文件信息，拷贝到be_wk_upload_file，其中合成文件的fileId是在27服务器上完成
	 * @param list
	 * @param categoryId
	 * @param gid
	 */
	public static void beginCopyFiles(List<Map<String, Object>> list,String categoryId,String gid){
		StringBuffer sql = new StringBuffer();
		Iterator<Map<String, Object>> iterator = list.iterator();
		String investorId = "";
		String identityId = "";
		String inv = "";
		int number = 0;
		while(iterator.hasNext()){
			number++;
			Map<String, Object> next = iterator.next();
			investorId =(String)next.get("investorId");
			identityId =(String)next.get("identityId");
			inv =(String)next.get("inv");
			//删除be_wk_upload_file 这个investorId股东的记录
			sql.delete(0, sql.length());
			sql.append(" delete from be_wk_upload_file f where f.ref_id = ? and f.gid=? ");
			DaoUtil.getInstance().execute(sql.toString(), investorId,gid);
			//从sysmgr_identity、sysmgr_identity_picture表中取出对应的图片
			sql.delete(0, sql.length());
			
			sql.append(" select p.*,i.type from sysmgr_identity i left join sysmgr_identity_picture p on i.identity_id = p.identity_id where p.identity_id = ? ");
			if(!"inv_cp".equals(categoryId)){//非自然人股东只有一张，自然人和法定代表人有三张
				sql.append(" and p.type in ('010','011') order by p.type ");
			}
			
			List<Map<String, Object>> picList = DaoUtil.getInstance().queryForList(sql.toString(), identityId);
			Map<String, Object> row = null;
			String fileId = "";
			String dataType= "";
			StringBuffer srcId = new StringBuffer();
			List<String> fileList = new ArrayList<String>();
			if(!picList.isEmpty()){
				for(int i=0;i<picList.size();i++){
					row = picList.get(i);
					fileId =(String)row.get("fileId");
					dataType =(String)row.get("type");
					fileList.add(fileId);//fileId
					if(i==picList.size()-1){
						srcId.append(fileId);
					}else{
						srcId.append(fileId).append(",");
					}
				}
			}
			if(fileList.size()==0){
				//没有认证图片
				return ;
			}
			//将取出对应的图片信息，建立be_wk_upload_file信息
			String fid=UUIDUtil.getUUID();
			sql.delete(0, sql.length());
			sql.append("insert into be_wk_upload_file(f_id, sn, ref_id, ref_text, ")
			   .append("file_id,thumb_file_id, state, gid, src_id,timestamp, category_id,data_type) ")
			   .append("values (?,?,?,?,?,?,'1',?,?,sysdate,?,?)");
			List<Object> param = new ArrayList<Object>();
			
			param.add(fid);//主键
			String sn = String.valueOf(number); 
			param.add(sn);//序号 签字盖章页需要
			param.add(investorId);//股东ID,法定代表人,主要人员
			param.add(inv);//股东名称
			String path =UploadFilePathUtil.getUploadPath(gid);
			//供远程调用，目前用于13服务器文书下载，从27服务器上执行合成，返回合成的fileId
			//String mergeFileId = romoteMergeImages(fileList,path);
			String mergeFileId = UploadUtil.mergeImages(fileList,path,"y");
			param.add(mergeFileId);
			param.add(mergeFileId);//压缩合并文件mergeFileId
			param.add(gid);
			param.add(srcId.toString());//合成前文件fileId的；拼接
			param.add(categoryId);//自然人上传策略
			param.add(dataType);//0--身份认证  2-- 身份核查
			DaoUtil.getInstance().execute(sql.toString(),param);
		}
	}
	
	/**
	 * 供远程调用，目前用于13服务器文书下载，从27服务器上执行合成，返回合成的fileId
	 * @param fileList
	 * @param path
	 * @return
	 */
	public static String romoteMergeImages(List<String> fileList,String path){
		
		Map<String,Object> reqJSonObject = new HashMap<String,Object>();
		reqJSonObject.put("fileList", fileList);
		reqJSonObject.put("path", path);
		List<Object> list = new ArrayList<Object>();
		list.add(reqJSonObject);
		String reqJSonString = JSON.toJSONString(list, false);
		String targetURL =WEB_URL+"/upload/clientMergePic.do?jsonStr="+reqJSonString;
//		String targetURL ="http://localhost:9999/upload/clientMergePic.do?jsonStr="+reqJSonString;
		PostMethod postMethod = null ;
		String responseText = "";
		try{
			postMethod = new PostMethod(targetURL);   
			postMethod.setRequestEntity(new StringRequestEntity(reqJSonString, "text/plain","utf-8"));
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
			
			int status = client.executeMethod(postMethod);
			
			if (status == HttpStatus.SC_OK) {
				responseText = postMethod.getResponseBodyAsString();
			}else{
				responseText = postMethod.getResponseBodyAsString();
				throw new RuntimeException("错误信息："+responseText);
			}
		}catch(IOException e){
			throw new RuntimeException(e.getMessage());
		}finally{
			if(postMethod!=null){
				postMethod.releaseConnection();
			}
		}
		return responseText;
	}
	
	/**
	 * 生成文书。
	 * 
	 * @param docCode
	 * @param params
	 * @param chap
	 * @return
	 */
	protected static String buildDoc(String docCode, Map<String,Object> params,String chap){
		String gid = StringUtil.safe2String(params.get("gid"));
		try{
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
			
			if(StringUtil.isBlank(gid)){
				throw new RuntimeException("gid不能为空。");
			}
			//检查文件是否正在被使用
			if(beUsed(gid)){
				throw new RuntimeException("文件正在被使用，请稍候重试。");
			}
			
			// 0. 准备参数
			Map<String,Object> defaultParams = ParamUtil.prepareParams(gid);
			if(defaultParams!=null){
				params.putAll(defaultParams);
			}
			String date = sdf.format(new Date());
			params.put("date", date);
			// 0.1. 准备上下文
			Map<String,Object> listContext = DataSupport.buildListContext(docCode, params);
			listContext.put("reqUrl", reqUrl);
			listContext.put("webUrl", WEB_URL);
			//pdf格式化后的html代码
			List<Map<String, String>> htmlList = new ArrayList<Map<String,String>>();
			// 0.2. 获取配置信息
			List<Map<String, Object>> docChapConfigList = SysDocChapListManager.instance.getConfig(docCode);
			
			// 1. 清除可能的原有数据
			String sql = "delete from be_wk_doc_chapter c where c.gid = ?";
			DaoUtil.getInstance().execute(sql, gid);
			// 2. 生成HTML
			// 2.1.  构造html头部，包含样式
			StringBuffer sbAllHtml = new StringBuffer();
			sbAllHtml.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">")      
				.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">")
				.append("<head>")
				.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />")
				.append("<style type=\"text/css\" mce_bogus=\"1\">");// TODO mce_bogus
			
			String cssFilePath = getTemplateRootPath() + "css/pdf-template.css";
			String cssString = FileUtil.readFile(cssFilePath);
			sbAllHtml.append( "\r\n"+cssString +"\r\n" );//样式
			
			sbAllHtml.append( "</style>\r\n</head>\r\n<body>");
			
			if(StringUtil.isBlank(chap)){
				// 2.2. 逐个生成章节
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
					String docHtml = HtmlBuilder.buildDocHtml(chapId, listContext); // 重用V1逻辑
					//1.1 创建章节记录
					generateHtmlList(chapConfig,htmlList,docHtml);
					sbAllHtml.append( docHtml );
				}
			}else{
				// 仅仅生成指定章节，测试用。
				chap = chap.replace(" ", "+");
				String docHtml = HtmlBuilder.buildDocHtml(chap, listContext); // 重用V1逻辑
				sbAllHtml.append( docHtml );
			}
			// 2.3 
			sbAllHtml.append( "</body></html>" );
			String allHtml = sbAllHtml.toString();
			params.put("allHtml",allHtml);
			
			FileUtil.createFile("c:\\tmp\\pdf.html", allHtml);
			
			String pdfPath = PdfBuilder.html2pdf(params);
			//3.保存pdf记录
			DocUtil.createPdfRecord(gid,pdfPath);
			//4.保存htmlJson
			saveHtmlJson(htmlList, gid);
			addPageNo(pdfPath,gid);
			return pdfPath;
		}finally{
			cleanLock(gid);
		}
		
	}
	
	/**
	 * 判断pdf文件是否存在，存在返回pdf路径，不存在返回空。
	 *  
	 * @param gid
	 * @return
	 */
	protected static String getPdfFile(String gid){
		if(StringUtils.isBlank(gid)){
			return "";
		}
		String sql = " select url from be_wk_doc d where d.gid=? ";
		BaseDaoUtil dao = DaoUtil.getInstance();
		String dir = dao.queryForOneString(sql, gid);
		if(StringUtils.isBlank(dir)){
			return "";
		}
		String fileRootPath = ConfigUtil.get("file.rootPath");//统一文件根目录
		String rootPath = ConfigUtil.get("doc.rootpath.pdf");//PDF根目录
		String fullPdfFilePath = fileRootPath+rootPath + dir + DocUtil.PDF_NAME;//PDF文件
		File pdfFile = new File(fullPdfFilePath);
		if(pdfFile.exists()){
			return fullPdfFilePath;
		}else{
			return "";
		}
	}
	
	
	
	/**
	 * 文件是否正在被使用。
	 * 
	 * be_wk_doc表file_lock有值则认为被锁。
	 * 
	 * 如果没有被锁，则加锁。
	 * 
	 * FIXME 有并发问题
	 * 
	 * @param gid
	 * @return
	 */
	private static boolean beUsed(String gid){
		if(StringUtils.isBlank(gid)){
			throw new RuntimeException("“gid”不能为空。");
		}
		String sql = "select count(1) from be_wk_doc doc where doc.gid=? and doc.file_lock is not null";
		long cnt = DaoUtil.getInstance().queryForOneLong(sql, gid);
		//文件未被使用，更新锁定标识
		if(cnt<1){
			sql = " update be_wk_doc doc set doc.file_lock='1' where doc.gid=? ";
			DaoUtil.getInstance().execute(sql, gid);
		}
		return cnt>0;
	}
	/**
	 * 清空锁定标识
	 * 
	 * @param gid
	 */
	private static void cleanLock(String gid){
		if(StringUtils.isBlank(gid)){
			throw new RuntimeException("“gid”不能为空。");
		}
		String sql = " update be_wk_doc doc set doc.file_lock = null where doc.gid=? ";
		DaoUtil.getInstance().execute(sql, gid);
	}

	
	/**
	 * 构造html章节列表
	 * 
	 * @param chapConfig
	 * @param htmlList
	 * @param docHtml
	 */
	protected static void generateHtmlList(Map<String, Object>chapConfig,List<Map<String, String>>htmlList,String docHtml){
		if(StringUtils.isBlank(docHtml)){
			return;
		}
		if(chapConfig==null||htmlList==null){
			return;
		}
		Map<String, String> html = new HashMap<String,String>();
		String title = PdfUtil.getTitleByContent(docHtml);
		if(StringUtils.isBlank(title)){
			return;
		}
		//String title = StringUtil.safe2String(chapConfig.get("title"));
		String href  = StringUtil.safe2String(chapConfig.get("href"));
		html.put("title", title);
		html.put("href", href);
		html.put("htmlCont", docHtml);
		htmlList.add(html);
	}
	
	protected static void saveHtmlJson(List<Map<String, String>> htmlList,String gid){
		if(StringUtils.isBlank(gid)){
			return;
		}
		if(null==htmlList){
			return;
		}
		String str = JSONArray.toJSONString(htmlList);
		String sql = " update be_wk_doc d set d.html_json=? where d.gid=? ";
		DaoUtil.getInstance().execute(sql, str,gid);
	}
	
	public static String getHtml(String gid){
		if(StringUtils.isBlank(gid)){
			return "";
		}
		String sql = "select html_json from be_wk_doc where gid=?";
		String ret = DaoUtil.getInstance().queryForOneString(sql, gid);
		return ret;
	}
	
	/**
	 * 解析章节信息，保存在be_wk_doc表directory字段中。
	 * 给PDF文件增加页码。
	 * 
	 * @param pdfPath
	 * @param gid
	 */
	protected static void addPageNo(String pdfPath,String gid){
		if(StringUtils.isBlank(gid)){
			throw new RuntimeException("“gid”不能为空。");
		}
		if(StringUtils.isBlank(pdfPath)){
			throw new RuntimeException("“pdfPath”不能为空。");
		}
		String sql = "select * from be_wk_requisition r where r.gid=?";
		Map<String, Object> ret = DaoUtil.getInstance().queryForRow(sql, gid);
		if(ret==null || ret.isEmpty()){
			throw new RuntimeException("未找到业务记录。");
		}
		String state = StringUtil.safe2String(ret.get("state"));
		if("1".equals(state)||"2".equals(state)){
			// 如果状态是未提交，则不生成页码。
			return;
		}
		//保存目录信息
		List<Map<String, Object>> chapInfo = ArchBuilder.parseChapterInfo(gid);
		sql = "update be_wk_doc d set d.directory=? where d.gid=?";
		if(chapInfo!=null && !chapInfo.isEmpty()){
			String dirString = JSON.toJSONString(chapInfo, false);
			DaoUtil.getInstance().execute(sql,dirString,gid);
			//添加页码
			PdfUtil.addPageNo(pdfPath, 2);
		}else{
			throw new RuntimeException("创建目录信息失败。");
		}
		
		
	}
	/**
	 * 文件内容是否发生变更。
	 * 
	 * @param gid
	 * @return
	 */
	protected static boolean haveChange(String gid){
		if(StringUtil.isBlank(gid)){
			return true;
		}
		String sql = " select count(m.modify_id) as cnt from be_wk_requisition r  left join be_wk_modify m on r.gid = m.gid and r.version=m.version  where r.gid =? ";
		long cnt = DaoUtil.getInstance().queryForOneLong(sql, gid);
		return cnt>0;
	}
}
