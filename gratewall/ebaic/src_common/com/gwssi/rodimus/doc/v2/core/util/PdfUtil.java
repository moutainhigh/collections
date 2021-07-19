package com.gwssi.rodimus.doc.v2.core.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.gwssi.rodimus.exception.RodimusException;
import com.gwssi.rodimus.util.FileUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

/**
 * PDF 工具类。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class PdfUtil {
	
	public static void main(String[] args) throws Exception {
//		addPageNo("C:\\tmp\\1.pdf", 1);
		
		long start = System.currentTimeMillis();
		List<Map<String,Object>> chapInfo = parseChapterPageNo("C:\\share\\ebaic\\doc\\pdf\\110108000\\20160809\\5782bc83ffb64ae0b85ece3708b7a067\\ArchiveElecFile.pdf",2);
		System.out.println(JSON.toJSONString(chapInfo, true)); 
		long end = System.currentTimeMillis();
		System.out.println("耗时：" + (end - start) + "毫秒");
//		[
//			{
//				"chapName":"封面",
//				"chapPageCnt":1,
//				"logicPageNo":0,
//				"phyPageNo":1
//			},
//			{
//				"chapName":"基本信息表",
//				"chapPageCnt":1,
//				"logicPageNo":1,
//				"phyPageNo":2
//			},
//			{
//				"chapName":"自然人股东信息表",
//				"chapPageCnt":1,
//				"logicPageNo":2,
//				"phyPageNo":3
//			},
//			{
//				"chapName":"非自然人股东信息表",
//				"chapPageCnt":2,
//				"logicPageNo":3,
//				"phyPageNo":4
//			},
//			{
//				"chapName":"董事、经理、监事信息表",
//				"chapPageCnt":2,
//				"logicPageNo":5,
//				"phyPageNo":6
//			},
//			{
//				"chapName":"财务负责人信息表",
//				"chapPageCnt":1,
//				"logicPageNo":7,
//				"phyPageNo":8
//			},
//			{
//				"chapName":"住所证明",
//				"chapPageCnt":1,
//				"logicPageNo":8,
//				"phyPageNo":9
//			},
//			{
//				"chapName":"公司章程",
//				"chapPageCnt":6,
//				"logicPageNo":9,
//				"phyPageNo":10
//			},
//			{
//				"chapName":"企业名称预先核准通知书",
//				"chapPageCnt":1,
//				"logicPageNo":15,
//				"phyPageNo":16
//			},
//			{
//				"chapName":"股东确认书",
//				"chapPageCnt":1,
//				"logicPageNo":16,
//				"phyPageNo":17
//			},
//			{
//				"chapName":"法定代表人承诺书",
//				"logicPageNo":17,
//				"phyPageNo":18
//			}
//		]
//		耗时：491毫秒

		
//		start = System.currentTimeMillis();
//		addPageNo("H:\\gwssi\\setup_1100.pdf",1);
//		end = System.currentTimeMillis();
//		System.out.println("耗时：" + (end - start) + "毫秒");
		
		
		
	}

	
	
	
	
	
	
	
	
	/** 
	 * 给PDF文件加页码。
	 * 
	 * 	292毫秒。
	 * 
	 * @param sourcePhyPath 源PDF文件物理路径
	 * @param skipCoverCnt 需略过的封面页数
	 */
	public static void addPageNo(String sourcePhyPath,int skipCoverCnt) {
		String destPhyPath = sourcePhyPath + ".temp.pdf";
		addPageNo(sourcePhyPath,destPhyPath,skipCoverCnt);
		File destFile = new File(destPhyPath);
		if(destFile.exists() && destFile.length()>0){
			FileUtil.cleanDir(new File(sourcePhyPath));
			destFile.renameTo(new File(sourcePhyPath));
		}
	}
	/**
	 * 给PDF文件加页码。
	 * 
	 * @param sourcePhyPath 源PDF文件物理路径
	 * @param destPhyPath 目标PDF文件路径
	 * @param skipCoverCnt 需略过的封面页数
	 */
	public static void addPageNo(String sourcePhyPath,String destPhyPath,int skipCoverCnt) {
		if(StringUtil.isBlank(sourcePhyPath)){
			throw new RodimusException("源PDF文件物理路径不能为空。");
		}
		if(StringUtil.isBlank(destPhyPath)){
			throw new RodimusException("目标PDF文件路径不能为空。");
		}
		sourcePhyPath = sourcePhyPath.trim();
		destPhyPath = destPhyPath.trim();
		if(sourcePhyPath.equals(destPhyPath)){
			throw new RodimusException("“源PDF文件物理路径”和“目标PDF文件路径”不能相同。");
		}
		if(skipCoverCnt<0){
			skipCoverCnt = 0;
		}
		try	{
			BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			PdfReader reader = new PdfReader(sourcePhyPath);
			Document document = new Document(PageSize.A4);
			PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(destPhyPath));
			document.open();
			PdfContentByte canvas = writer.getDirectContent();
			PdfImportedPage page;
			int pageCnt = reader.getNumberOfPages() - skipCoverCnt;
			if(pageCnt<=0){
				return ;
			}
			for (int i = 1; i <= reader.getNumberOfPages(); i++) {
				String text = "";
				if(i>skipCoverCnt){
					text = "第 "+ (i-skipCoverCnt) + " 页     共 " + pageCnt + " 页" ;
				}
				page = writer.getImportedPage(reader, i);
				canvas.addTemplate(page, 1f, 0, 0, 1, 0, 0);
				canvas.beginText();
				canvas.setFontAndSize(bf, 10);
				canvas.showTextAligned(Element.ALIGN_CENTER, text, 300, 30, 0);
				canvas.endText();
				document.newPage();
			}
			document.close();
		}catch(Exception e){
			throw new RodimusException(e.getMessage(),e);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	static Set<String> ChapterNames = new HashSet<String>();
	
	static {
		ChapterNames.add("设立登记审核表");
		ChapterNames.add("内资公司设立登记申请书");
		ChapterNames.add("基本信息表");
		ChapterNames.add("自然人股东信息表");
		ChapterNames.add("非自然人股东信息表");
		ChapterNames.add("董事、经理、监事信息表");
		ChapterNames.add("财务负责人信息表"); // 企业联系人信息表
		ChapterNames.add("住所证明");
		ChapterNames.add("中华人民共和国公司法");// 章程
		ChapterNames.add("企业名称预先核准通知书");
		ChapterNames.add("股东确认书");
		ChapterNames.add("法定代表人承诺书");
	}
	/**
	 * 解析PDF得到章节页码。
	 * 
	 * 注意： 只有HTML转PDF刚刚生成的PDF文件可以解析成功，加上页码后就不成了。
	 * 
	 * @param pdfPhyPath 源PDF文件物理路径
	 * @param skipCoverCnt  需略过的封面页数
	 * @return
	 */
	public static List<Map<String,Object>> parseChapterPageNo(String pdfPhyPath,int skipCoverCnt) {
		if(StringUtil.isBlank(pdfPhyPath)){
			throw new RodimusException("PDF文件物理路径不能为空。");
		}
		if(skipCoverCnt<0){
			skipCoverCnt = 0;
		}
		List<Map<String,Object>> ret = new ArrayList<Map<String,Object>>();
		Set<String> chapNameSet = new HashSet<String>();//已经有的章节
		try{
			PdfReader reader = new PdfReader(pdfPhyPath);
			PdfReaderContentParser parser = new PdfReaderContentParser(reader);
			TextExtractionStrategy strategy;
			int pageCnt = reader.getNumberOfPages();
			for (int i = 1; i <= pageCnt; i++) {
				try{
					strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
					String content = strategy.getResultantText();
					parsePageNo(i, content, skipCoverCnt,chapNameSet, ret);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			Map<String,Object> lastOne = ret.get(ret.size()-1);
			int pageNo=0;
			if(lastOne.get("phyPageNo")!=null){
				pageNo = (Integer)lastOne.get("phyPageNo");
			}
			int chapPageCnt = pageCnt - pageNo+1;
			lastOne.put("chapPageCnt", chapPageCnt);
			ret.remove(ret.size()-1);
			ret.add(lastOne);
		}catch(Exception e){
			e.printStackTrace();
			throw new RodimusException(e.getMessage(),e);
		}
		return ret;
	}

	private static void parsePageNo(int i, String content,int skipCoverCnt, Set<String> chapNameSet, List<Map<String,Object>> ret ) {
		for (String title : ChapterNames) {
			if (content.contains(title)) {
				if ("中华人民共和国公司法".equals(title)) {
					title = "公司章程";
				}
				if("内资公司设立登记申请书".equals(title)){
					title = "封面";
				}
				// 当前章节已经包含过了
				if(chapNameSet.contains(title)){
					return ;
				}
				// 保存章节信息
				Map<String,Object> row = new HashMap<String,Object>();
				row.put("chapName", title);
				row.put("phyPageNo", i);
				row.put("logicPageNo", i-skipCoverCnt);
				
				if(ret!=null && !ret.isEmpty()){
					Map<String,Object> preRow = ret.get(ret.size()-1);
					if(preRow!=null){
						int prePhyPageNo = (Integer)preRow.get("phyPageNo");
						int chapPageCnt = i - prePhyPageNo ;
						preRow.put("chapPageCnt", chapPageCnt);
					}
				}
				
				ret.add(row);
				
				chapNameSet.add(title);
				break;
			}
		}
	}
	
	/**
	 * 从内容中获取章节标题
	 * 
	 * @param content
	 * @return
	 */
	public static String getTitleByContent(String content){
		String title = "";
		if(StringUtil.isBlank(content)){
			return title;
		}
		for (String name : ChapterNames) {
			if (content.contains(name)) {
				if ("中华人民共和国公司法".equals(name)) {
					title = "公司章程";
				}else if("内资公司设立登记申请书".equals(name)){
					title = "封面";
				}else{
					title = name;
				}
				break;
			}
		}
		return title;
	}
}
