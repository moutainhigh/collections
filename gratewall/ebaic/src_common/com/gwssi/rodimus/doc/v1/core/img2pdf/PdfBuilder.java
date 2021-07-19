package com.gwssi.rodimus.doc.v1.core.img2pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.gwssi.rodimus.config.ConfigUtil;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.doc.v1.DocUtil;
import com.gwssi.rodimus.exception.RodimusException;
import com.gwssi.rodimus.util.DateUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * 页码。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class PdfBuilder extends PdfPageEventHelper{
	/**这个PdfTemplate实例用于保存总页数 */  
    public PdfTemplate tpl;  
    /** 页码字体 */  
    public BaseFont bf;  
    /** 业务状态，判断是否头两页生成页码 */
    public String state;
    
    public PdfBuilder(){
    	
    }
    
    public PdfBuilder(String state){
    	this.state = state;
    }
	/**
	 * 生成PDF文件。
	 * 
	 * @param docConfig
	 * @param params
	 */
	public static String buildPdf(Map<String, Object> params) {
		String gid = StringUtil.safe2String(params.get("gid"));
		String state = StringUtil.safe2String(params.get("state"));
		if (StringUtil.isBlank(gid)) {
			throw new RodimusException("gid不能为空。");
		}
		String sql = "select * from be_wk_doc d where d.gid = ?";
		Map<String, Object> beWkDocBo = DaoUtil.getInstance().queryForRow(sql, gid);
		if (beWkDocBo == null || beWkDocBo.isEmpty()) {
			throw new RodimusException("生成PDF失败：没有找到图片信息。(wkdoc_empty)");
		}

		Calendar now = DateUtil.getCurrentTime();

		// 文件目录
		String dir = StringUtil.safe2String(beWkDocBo.get("url"));
		if (StringUtil.isBlank(dir)) {
			dir = DocUtil.getPath(params);
			sql = "update Be_Wk_Doc d set d.url = ? ,d.timestamp = ? where d.gid = ?";
			DaoUtil.getInstance().execute(sql, dir, now);
		}
		String fileRootPath = ConfigUtil.get("file.rootPath");//统一文件根目录
		String rootPath = ConfigUtil.get("doc.rootpath.pdf");//PDF根目录
		String imageRootPath = ConfigUtil.get("doc.rootpath.image");//图片根目录
		String fullPdfFilePath = fileRootPath+rootPath + dir + DocUtil.PDF_NAME;//PDF文件
		String imagePath = fileRootPath+imageRootPath + dir;//图片目录
		String pdfPath = fileRootPath+rootPath + dir;
		//pdf文件夹路径
		params.put("pdfRelativePath", rootPath+dir);
		sql = "select * from be_wk_doc_chapter c where c.gid = ? order by c.page_no";
		List<Map<String, Object>> list = DaoUtil.getInstance().queryForList(sql, gid);
		File dirDoc = new File(pdfPath);
		//创建文件夹
		if(!dirDoc.exists() || !dirDoc.isDirectory()){
			dirDoc.mkdirs();
		}
		Document document = new Document(PageSize.A4,0,0,0,0);
		try {
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fullPdfFilePath));
			writer.setStrictImageSequence(true);
			writer.setPageEvent(new PdfBuilder(state));
			document.open();
			for (Map<String, Object> row : list) {
				String imageUrls = StringUtil.safe2String(row.get("contentUrl"));
				if(StringUtil.isBlank(imageUrls)){
					continue;
				}
				String[] imageUrlArray = imageUrls.split(",");
				if(imageUrlArray==null || imageUrlArray.length==0){
					continue;
				}
				for(String imageUrl : imageUrlArray){
					String fullImagePath = imagePath + imageUrl;
					Image jpg = Image.getInstance(fullImagePath);
					Float h = jpg.getHeight();
					Float w = jpg.getWidth();
					int percent = getPercent(h, w);
					jpg.setAlignment(Image.MIDDLE);
					jpg.scalePercent(percent+8);
					document.add(jpg);
				}
			}
		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		} finally{
			document.close();
		}
		return fullPdfFilePath;
	}
	/**
	 * 按比例缩放图片
	 * 
	 * @param h
	 * @param w
	 * @return
	 */
	public static int getPercent(float h, float w) {  
        int p = 0;  
        float p2 = 0.0f;  
        p2 = 530 / w * 100;  
        p = Math.round(p2);  
        return p;  
    }
	public void onOpenDocument(PdfWriter writer, Document document) {
		try {
			tpl = writer.getDirectContent().createTemplate(100, 100);
			bf = BaseFont.createFont("STSong-Light","UniGB-UCS2-H", false);
		} catch (Exception e) {
			throw new ExceptionConverter(e);
		}
	}

	public void onEndPage(PdfWriter writer, Document document) {
		if("8".equals(this.state)){
			//头两页页不算在总页数中
			if(writer.getPageNumber()-2>0){
				// 在每页结束的时候把“第x页”信息写道模版指定位置
				PdfContentByte cb = writer.getDirectContent();
				cb.saveState();
				String text = "第 " + Integer.toString(writer.getPageNumber()-2) + " 页 , ";
				cb.beginText();
				cb.setFontAndSize(bf, 10);
				cb.setTextMatrix(270, 50);// 定位“第x页,共” 在具体的页面调试时候需要更改这xy的坐标
				cb.showText(text);
				cb.endText();
				cb.addTemplate(tpl, 310, 50);// 定位“y页” 在具体的页面调试时候需要更改这xy的坐标

				cb.saveState();
				cb.stroke();
				cb.restoreState();
				cb.restoreState();
				cb.closePath();// sanityCheck();
			}
		}else{
			//第一页不算在总页数中
			if(writer.getPageNumber()-1>0){
				// 在每页结束的时候把“第x页”信息写道模版指定位置
				PdfContentByte cb = writer.getDirectContent();
				cb.saveState();
				String text = "第 " + Integer.toString(writer.getPageNumber()-1) + " 页 , ";
				cb.beginText();
				cb.setFontAndSize(bf, 10);
				cb.setTextMatrix(270, 50);// 定位“第x页,共” 在具体的页面调试时候需要更改这xy的坐标
				cb.showText(text);
				cb.endText();
				cb.addTemplate(tpl, 310, 50);// 定位“y页” 在具体的页面调试时候需要更改这xy的坐标

				cb.saveState();
				cb.stroke();
				cb.restoreState();
				cb.restoreState();
				cb.closePath();// sanityCheck();
			}
		}
		
		
	}

	public void onCloseDocument(PdfWriter writer, Document document) {
		if("8".equals(this.state)){
			// 关闭document的时候获取总页数，并把总页数按模版写道之前预留的位置
			tpl.beginText();
			tpl.setFontAndSize(bf, 10);
			tpl.showText("共 "+Integer.toString(writer.getPageNumber()-3) + " 页");
			tpl.endText();
			tpl.closePath();// sanityCheck();
		}else{
			// 关闭document的时候获取总页数，并把总页数按模版写道之前预留的位置
			tpl.beginText();
			tpl.setFontAndSize(bf, 10);
			tpl.showText("共 "+Integer.toString(writer.getPageNumber()-2) + " 页");
			tpl.endText();
			tpl.closePath();// sanityCheck();
		}
		
	}
	/**
	 * 生成PDF文件。
	 * 
	 * @param docConfig
	 * @param params
	 */
	@SuppressWarnings("unused")
	public static String html2pdf(Map<String, Object> params) {
		String gid = StringUtil.safe2String(params.get("gid"));
		String state = StringUtil.safe2String(params.get("state"));
		if (StringUtil.isBlank(gid)) {
			throw new RodimusException("gid不能为空。");
		}
		String sql = "select * from be_wk_doc d where d.gid = ?";
		Map<String, Object> beWkDocBo = DaoUtil.getInstance().queryForRow(sql, gid);
		if (beWkDocBo == null || beWkDocBo.isEmpty()) {
			throw new RodimusException("生成PDF失败：没有找到图片信息。(wkdoc_empty)");
		}

		Calendar now = DateUtil.getCurrentTime();

		// 文件目录
		String dir = StringUtil.safe2String(beWkDocBo.get("url"));
		if (StringUtil.isBlank(dir)) {
			dir = DocUtil.getPath(params);
			sql = "update Be_Wk_Doc d set d.url = ? ,d.timestamp = ? where d.gid = ?";
			DaoUtil.getInstance().execute(sql, dir, now);
		}
		String fileRootPath = ConfigUtil.get("file.rootPath");//统一文件根目录
		String rootPath = ConfigUtil.get("doc.rootpath.pdf");//PDF根目录
		String fullPdfFilePath = fileRootPath+rootPath + dir + DocUtil.PDF_NAME;//PDF文件
		String pdfPath = fileRootPath+rootPath + dir;
		//pdf文件夹路径
		params.put("pdfRelativePath", rootPath+dir);
		sql = "select * from be_wk_doc_chapter c where c.gid = ? order by c.page_no";
		List<Map<String, Object>> list = DaoUtil.getInstance().queryForList(sql, gid);
		File dirDoc = new File(pdfPath);
		//创建文件夹
		if(!dirDoc.exists() || !dirDoc.isDirectory()){
			dirDoc.mkdirs();
		}
		
		String allHtml = (String)params.get("allHtml");
        FileOutputStream os = null;
		try {
			os = new FileOutputStream(fullPdfFilePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  
        
        ITextRenderer renderer = new ITextRenderer();       
        ITextFontResolver fontResolver = renderer.getFontResolver();       
        renderer.setDocumentFromString(allHtml.toString()); 
        //解决字体问题
        try {
			fontResolver.addFont("C:/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		} catch (com.lowagie.text.DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
        // 解决图片的相对路径问题       
        renderer.getSharedContext().setBaseURL("file:/C:/");
        renderer.layout();  
        try {
			renderer.createPDF(os);
		} catch (com.lowagie.text.DocumentException e) {
			e.printStackTrace();
		} finally{
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fullPdfFilePath;
	}
	
}
