package com.gwssi.rodimus.doc.v2.core.html2pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Map;

import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.gwssi.rodimus.config.ConfigUtil;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.doc.v2.DocUtil;
import com.gwssi.rodimus.exception.RodimusException;
import com.gwssi.rodimus.util.DateUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.rodimus.util.UUIDUtil;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPageEventHelper;

/**
 * 页码。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class PdfBuilder extends PdfPageEventHelper{
	/**
	 * 将HTML字符串生成PDF文件。
	 * 
	 * @param docConfig
	 * @param params
	 */
	@SuppressWarnings("resource")
	public static String html2pdf(Map<String, Object> params) {
		// 0. 参数判断
		if(params==null || params.isEmpty()){
			throw new RodimusException("生成DPF文件失败：参数为空。");
		}
		String gid = StringUtil.safe2String(params.get("gid"));
		if (StringUtil.isBlank(gid)) {
			throw new RodimusException("生成DPF文件失败：gid为空。");
		}
		String allHtml = (String)params.get("allHtml");
		if (StringUtil.isBlank(allHtml)) {
			throw new RodimusException("生成DPF文件失败：文件内容为空。");
		}
		String state = StringUtil.safe2String(params.get("state"));
		if (StringUtil.isBlank(state)) {
			throw new RodimusException("生成DPF文件失败：state为空。");
		}
		Calendar now = DateUtil.getCurrentTime();
		// 1. 处理 be_wk_doc
		
		String dir = null;
		String wkDocId = null;
		String sql = "select * from be_wk_doc d where d.gid = ?";
		Map<String, Object> beWkDocBo = DaoUtil.getInstance().queryForRow(sql, gid);
		if (beWkDocBo == null || beWkDocBo.isEmpty()){
			dir = DocUtil.getPath(params);
			wkDocId = UUIDUtil.getUUID();
			sql = "insert into be_wk_doc(doc_id,title,url,gid,timestamp) values (?,?,?,?,?)";
			DaoUtil.getInstance().execute(sql, wkDocId,params.get("docTitle"),dir,gid,now);
		}else{
			dir = StringUtil.safe2String(beWkDocBo.get("url"));
			if (StringUtil.isBlank(dir)) {
				dir = DocUtil.getPath(params);
				sql = "update Be_Wk_Doc d set d.url = ? ,d.timestamp = ? where d.gid = ?";
				DaoUtil.getInstance().execute(sql, dir, now,gid);
			}
			wkDocId = StringUtil.safe2String(beWkDocBo.get("docId"));
		}
		//更新章节表时期与doc表关联
		sql = "update be_wk_doc_chapter c set c.doc_id=? where c.gid=?";
		DaoUtil.getInstance().execute(sql, wkDocId, gid);
		
		// 2. 处理路径
		String fileRootPath = ConfigUtil.get("file.rootPath");//统一文件根目录 \data\ebaic\ 
		String rootPath = ConfigUtil.get("doc.rootpath.pdf");//PDF根目录 \doc\pdf
		String fullPdfFilePath = fileRootPath+rootPath + dir + DocUtil.PDF_NAME;//PDF文件
		String pdfPath = fileRootPath+rootPath + dir;
		//pdf文件夹路径
		params.put("pdfRelativePath", rootPath+dir);
		File dirDoc = new File(pdfPath);
		//创建文件夹
		if(!dirDoc.exists() || !dirDoc.isDirectory()){
			dirDoc.mkdirs();
		}
		
		//String state = StringUtil.safe2String(params.get("state"));

		// 3. 生成PDF
        FileOutputStream os = null;
		try {
			os = new FileOutputStream(fullPdfFilePath);
		} catch (FileNotFoundException e) {
			throw new RodimusException("生成PDF文件失败："+e.getMessage(),e);
		}
        ITextRenderer renderer = new ITextRenderer();       
        ITextFontResolver fontResolver = renderer.getFontResolver();  
//        System.out.println(allHtml.toString());
         
        renderer.setDocumentFromString(allHtml.toString()); 
        //解决字体问题
        try {
        	String fontPath = DocUtil.getTemplateRootPath() + "font/";
			fontResolver.addFont(fontPath+"simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		} catch (Exception e) {
			throw new RodimusException("生成PDF文件失败："+e.getMessage(),e);
		} 
        // 解决图片的相对路径问题     
        //renderer.getSharedContext().setBaseURL(fileRootPath);//  \data\ebaic\           背景图或通用路径  common\bg.png ， 上传图片 upload\
        renderer.getSharedContext().setBaseURL("file:/"+fileRootPath);
        renderer.layout();  
        
        try {
			renderer.createPDF(os);
		} catch (com.lowagie.text.DocumentException e) {
			e.printStackTrace();
		} finally{
			try {
				if(os!=null){
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fullPdfFilePath;
	}
	/*
	public static final String MESSAGE_STRING ="第%d页,sum%dpage";
	@SuppressWarnings("rawtypes")
	public static void creatPageNo(String pdfPath) throws IOException, COSVisitorException{
		PDDocument doc = null;
		try
		{
		 doc = PDDocument.load(pdfPath);
		 List allPages = doc.getDocumentCatalog().getAllPages();
		 PDFont font =PDType1Font.HELVETICA_BOLD;
		
		 float fontSize = 36.0f;
		 for( int i=0; i<allPages.size(); i++ )
		 {
		  PDPage page = (PDPage)allPages.get( i );
		  PDRectangle pageSize = page.findMediaBox();
		  float stringWidth = font.getStringWidth( String.format(MESSAGE_STRING, i+1,allPages.size()) )*fontSize/1000f;
		  // calculate to center of the page
		  int rotation = page.findRotation(); 
		  boolean rotate = rotation == 90 || rotation == 270;
		  float pageWidth = rotate ? pageSize.getHeight() : pageSize.getWidth();
		  float pageHeight = rotate ? pageSize.getWidth() : pageSize.getHeight();
		  double centeredXPosition = rotate ? pageHeight/2f : (pageWidth - stringWidth)/2f;
		  double centeredYPosition = rotate ? (pageWidth - stringWidth)/2f : pageHeight/2f;
		  // append the content to the existing stream
		  PDPageContentStream contentStream = new PDPageContentStream(doc, page, true, true,true);
		  contentStream.beginText();
		  // set font and font size
		  contentStream.setFont( font, fontSize );
		  // set text color to red
		  contentStream.setNonStrokingColor(255, 0, 0);
		  if (rotate)
		  {
		   // rotate the text according to the page rotation
		   contentStream.setTextRotation(Math.PI/2, centeredXPosition, centeredYPosition);
		  }
		  else
		  {
			  contentStream.setTextTranslation(centeredXPosition, centeredYPosition);
		  }
		  contentStream.drawString(String.format(MESSAGE_STRING, i+1,allPages.size()) );
		  contentStream.endText();
		  contentStream.close();
		 }
		 doc.save( pdfPath );
		}
		finally
		{
		 if( doc != null )
		 {
		  doc.close();
		 }
		}
	}
	
	public static void main(String[] args) throws COSVisitorException, IOException {
		creatPageNo("C:\\tmp\\1.pdf");
	}*/
//	/**这个PdfTemplate实例用于保存总页数 */  
//    public PdfTemplate tpl;  
//    /** 页码字体 */  
//    public BaseFont bf;  
//    /** 业务状态，判断是否头两页生成页码 */
//    public String state;
//    
//    public PdfBuilder(){
//    	
//    }
//    
//    public PdfBuilder(String state){
//    	this.state = state;
//    }
//	/**
//	 * 生成PDF文件。
//	 * 
//	 * @param docConfig
//	 * @param params
//	 */
//	public static String buildPdf(Map<String, Object> params) {
//		String gid = StringUtil.safe2String(params.get("gid"));
//		String state = StringUtil.safe2String(params.get("state"));
//		if (StringUtil.isBlank(gid)) {
//			throw new RodimusException("gid不能为空。");
//		}
//		String sql = "select * from be_wk_doc d where d.gid = ?";
//		Map<String, Object> beWkDocBo = DaoUtil.getInstance().queryForRow(sql, gid);
//		if (beWkDocBo == null || beWkDocBo.isEmpty()) {
//			throw new RodimusException("生成PDF失败：没有找到图片信息。(wkdoc_empty)");
//		}
//
//		Calendar now = DateUtil.getCurrentTime();
//
//		// 文件目录
//		String dir = StringUtil.safe2String(beWkDocBo.get("url"));
//		if (StringUtil.isBlank(dir)) {
//			dir = DocUtil.getPath(params);
//			sql = "update Be_Wk_Doc d set d.url = ? ,d.timestamp = ? where d.gid = ?";
//			DaoUtil.getInstance().execute(sql, dir, now);
//		}
//		String fileRootPath = ConfigUtil.get("file.rootPath");//统一文件根目录
//		String rootPath = ConfigUtil.get("doc.rootpath.pdf");//PDF根目录
//		String imageRootPath = ConfigUtil.get("doc.rootpath.image");//图片根目录
//		String fullPdfFilePath = fileRootPath+rootPath + dir + DocUtil.PDF_NAME;//PDF文件
//		String imagePath = fileRootPath+imageRootPath + dir;//图片目录
//		String pdfPath = fileRootPath+rootPath + dir;
//		//pdf文件夹路径
//		params.put("pdfRelativePath", rootPath+dir);
//		sql = "select * from be_wk_doc_chapter c where c.gid = ? order by c.page_no";
//		List<Map<String, Object>> list = DaoUtil.getInstance().queryForList(sql, gid);
//		File dirDoc = new File(pdfPath);
//		//创建文件夹
//		if(!dirDoc.exists() || !dirDoc.isDirectory()){
//			dirDoc.mkdirs();
//		}
//		Document document = new Document(PageSize.A4,0,0,0,0);
//		try {
//			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fullPdfFilePath));
//			writer.setStrictImageSequence(true);
//			writer.setPageEvent(new PdfBuilder(state));
//			document.open();
//			for (Map<String, Object> row : list) {
//				String imageUrls = StringUtil.safe2String(row.get("contentUrl"));
//				if(StringUtil.isBlank(imageUrls)){
//					continue;
//				}
//				String[] imageUrlArray = imageUrls.split(",");
//				if(imageUrlArray==null || imageUrlArray.length==0){
//					continue;
//				}
//				for(String imageUrl : imageUrlArray){
//					String fullImagePath = imagePath + imageUrl;
//					Image jpg = Image.getInstance(fullImagePath);
//					Float h = jpg.getHeight();
//					Float w = jpg.getWidth();
//					int percent = getPercent(h, w);
//					jpg.setAlignment(Image.MIDDLE);
//					jpg.scalePercent(percent+8);
//					document.add(jpg);
//				}
//			}
//		} catch (DocumentException de) {
//			System.err.println(de.getMessage());
//		} catch (IOException ioe) {
//			System.err.println(ioe.getMessage());
//		} finally{
//			document.close();
//		}
//		return fullPdfFilePath;
//	}
//	/**
//	 * 按比例缩放图片
//	 * 
//	 * @param h
//	 * @param w
//	 * @return
//	 */
//	public static int getPercent(float h, float w) {  
//        int p = 0;  
//        float p2 = 0.0f;  
//        p2 = 530 / w * 100;  
//        p = Math.round(p2);  
//        return p;  
//    }
//	public void onOpenDocument(PdfWriter writer, Document document) {
//		try {
//			tpl = writer.getDirectContent().createTemplate(100, 100);
//			bf = BaseFont.createFont("STSong-Light","UniGB-UCS2-H", false);
//		} catch (Exception e) {
//			throw new RodimusException(e.getMessage());
//		}
//	}

//	public void onEndPage(PdfWriter writer, Document document) {
//		if("8".equals(this.state)){
//			//头两页页不算在总页数中
//			if(writer.getPageNumber()-2>0){
//				// 在每页结束的时候把“第x页”信息写道模版指定位置
//				PdfContentByte cb = writer.getDirectContent();
//				cb.saveState();
//				String text = "第 " + Integer.toString(writer.getPageNumber()-2) + " 页 , ";
//				cb.beginText();
//				cb.setFontAndSize(bf, 10);
//				cb.setTextMatrix(270, 50);// 定位“第x页,共” 在具体的页面调试时候需要更改这xy的坐标
//				cb.showText(text);
//				cb.endText();
//				cb.addTemplate(tpl, 310, 50);// 定位“y页” 在具体的页面调试时候需要更改这xy的坐标
//
//				cb.saveState();
//				cb.stroke();
//				cb.restoreState();
//				cb.restoreState();
//				cb.closePath();// sanityCheck();
//			}
//		}
//			else{
//			//第一页不算在总页数中
//			if(writer.getPageNumber()-1>0){
//				// 在每页结束的时候把“第x页”信息写道模版指定位置
//				PdfContentByte cb = writer.getDirectContent();
//				cb.saveState();
//				String text = "第 " + Integer.toString(writer.getPageNumber()-1) + " 页 , ";
//				cb.beginText();
//				cb.setFontAndSize(bf, 10);
//				cb.setTextMatrix(270, 50);// 定位“第x页,共” 在具体的页面调试时候需要更改这xy的坐标
//				cb.showText(text);
//				cb.endText();
//				cb.addTemplate(tpl, 310, 50);// 定位“y页” 在具体的页面调试时候需要更改这xy的坐标
//
//				cb.saveState();
//				cb.stroke();
//				cb.restoreState();
//				cb.restoreState();
//				cb.closePath();// sanityCheck();
//			}
//		}
		
		
//	}
//
//	public void onCloseDocument(PdfWriter writer, Document document) {
//		if("8".equals(this.state)){
//			// 关闭document的时候获取总页数，并把总页数按模版写道之前预留的位置
//			tpl.beginText();
//			tpl.setFontAndSize(bf, 10);
//			tpl.showText("共 "+Integer.toString(writer.getPageNumber()-3) + " 页");
//			tpl.endText();
//			tpl.closePath();// sanityCheck();
//		}
//		else{
//			// 关闭document的时候获取总页数，并把总页数按模版写道之前预留的位置
//			tpl.beginText();
//			tpl.setFontAndSize(bf, 10);
//			tpl.showText("共 "+Integer.toString(writer.getPageNumber()-2) + " 页");
//			tpl.endText();
//			tpl.closePath();// sanityCheck();
//		}
		
//	}
	
	
}
