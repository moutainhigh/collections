package com.gwssi.rodimus.doc.v1.core.html2img;


import java.io.File;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.gwssi.rodimus.config.ConfigUtil;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.doc.v1.DocUtil;
import com.gwssi.rodimus.doc.v1.core.data2html.HtmlBuilder;
import com.gwssi.rodimus.exception.RodimusException;
import com.gwssi.rodimus.util.DateUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.rodimus.util.UUIDUtil;
import com.gwssi.torch.core.TemplateSupport;

import gui.ava.html.image.generator.HtmlImageGenerator;

public class ImageBuilder {
	
	private static final Logger logger = Logger.getLogger(ImageBuilder.class);
	
	private static final String FILE_SUFFIX = ".png";
	/**
	 * 
	 * 水印
	 * @param docConfig r.doc_id,r.sn,r.triger_expr,d.title,d.template_url
	 * @param docHtml
	 * @param params
	 * 
	 */
	public static void buildImage(Map<String, Object> chapConfig, String chapHtml, Map<String, Object> params) {
		// 准备参数
		if (StringUtil.isBlank(chapHtml)) {
			return;
		}
		if (chapConfig==null || chapConfig.isEmpty()) {
			throw new RodimusException("生成图片出错：配置信息为空。");
		}
		if (params==null || params.isEmpty()) {
			throw new RodimusException("生成图片出错：参数为空。");
		}
		String gid = StringUtil.safe2String(params.get("gid"));
		if (StringUtil.isBlank(gid)) {
			throw new RodimusException("生成图片出错：gid为空。");
		}
		String title = StringUtil.safe2String(chapConfig.get("title"));
		if(StringUtils.isBlank(title)){
			throw new RodimusException("生成图片出错：title为空。");
		}
		// 文件目录
		Calendar now = DateUtil.getCurrentTime();
		String fileRootPath = ConfigUtil.get("file.rootPath");
		String imageRootPath = ConfigUtil.get("doc.rootpath.image");//图片根目录
		String sql = "select * from be_wk_doc d where d.gid = ?";
		Map<String, Object> beWkDocBo = DaoUtil.getInstance().queryForRow(sql, gid);
		String dir = null;
		String wkDocId = null;
		if (beWkDocBo == null || beWkDocBo.isEmpty()){
			dir = DocUtil.getPath(params);
			wkDocId = UUIDUtil.getUUID();
			sql = "insert into be_wk_doc(doc_id,title,url,gid,timestamp) values (?,?,?,?,?)";
			DaoUtil.getInstance().execute(sql, wkDocId,params.get("docTitle"),dir,gid,now);
		}else{
			dir = StringUtil.safe2String(beWkDocBo.get("url"));
			wkDocId = StringUtil.safe2String(beWkDocBo.get("docId"));
		}

		String imageDir =fileRootPath+imageRootPath + dir;
		imageDir = TemplateSupport.cleanFileSeparator(imageDir);
		File dirDoc = new File(imageDir);
		//创建文件夹
		if(!dirDoc.exists() || !dirDoc.isDirectory()){
			dirDoc.mkdirs();
		}
		String[] htmlArray = chapHtml.split(HtmlBuilder.PAGING_SEPARATOR);
		if(htmlArray==null || htmlArray.length==0){
			throw new RodimusException("生成图片出错：Html内容为空。");
		}
		
		sql = "select nvl(sum(nvl(c.page_cnt,0)),0)+1 as cnt from be_wk_doc_chapter c where c.gid = ?";
		BigDecimal pageNo = DaoUtil.getInstance().queryForOneBigDecimal(sql, gid);
		
		HtmlImageGenerator hg = new HtmlImageGenerator();
		StringBuffer imageNames = new StringBuffer();
		StringBuffer fileIds = new StringBuffer();
		
		boolean first = true ;
		for(int i = 0 , length = htmlArray.length;  i<length;  ++i){
			String html = htmlArray[i];
			// 图片文件名
			String imageName = StringUtil.padLeft(pageNo.intValue(),3) + "_" + StringUtil.padLeft(i,3) + FILE_SUFFIX;
			String fileId = UUIDUtil.getUUID();
			
			if(first){
				first = false;
			}else{
				imageNames.append(",");
				fileIds.append(",");
			}
			imageNames.append(fileId).append(FILE_SUFFIX);
			fileIds.append(fileId);
			String imageUrl = imageDir+fileId+FILE_SUFFIX;
			hg.loadHtml(html);
			hg.saveAsImage(imageUrl);
//			Html2Image html2Image = Html2Image.fromHtml(html);
//			html2Image.getImageRenderer().saveImage(imageUrl);
			logger.info("生成图片完整路径--------->"+imageUrl);
			String filePath =  imageRootPath + dir;
			sql = " insert into be_wk_file f(f.file_id,f.suffix_name,f.timestamp,f.file_path,f.file_name) values(?,?,?,?,?) ";
			DaoUtil.getInstance().execute(sql,fileId,"png",now,filePath,imageName);
		}
		sql = "insert into be_wk_doc_chapter(chap_id,doc_id,page_no,page_cnt,content_url,security_level,gid,timestamp,chap_config_id,file_ids) values (?,?,?,?,?,?,?,?,?,?)";
		String chapId = UUIDUtil.getUUID();
		DaoUtil.getInstance().execute(sql, chapId,wkDocId,pageNo,htmlArray.length,imageNames.toString(),null,gid,now,chapConfig.get("chapConfigId"),fileIds.toString());
	}
	
//	public static void main(String[] args){
//		String url = "http://localhost:8080/file/doc_template/setup/basic.html";
//		HtmlImageGenerator hg = new HtmlImageGenerator();
//		Dimension dimension = new Dimension();
//		dimension.setSize(2970, 3000);// width , height 
//		hg.setSize(dimension);
//		hg.loadUrl(url);
//		hg.saveAsImage("D:\\t.jpg");
//	}
}
