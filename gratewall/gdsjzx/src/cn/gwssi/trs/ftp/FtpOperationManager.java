package cn.gwssi.trs.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.optimus.core.exception.OptimusException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import cn.gwssi.quartz.inter.JobServer;
import cn.gwssi.resource.CodeToValue;
import cn.gwssi.resource.FileUtil;
import cn.gwssi.resource.FtpUtil;
import cn.gwssi.trs.service.TrsService;

/**
 * FTP上传实体
 * @author zhixiong
 * @version 1.0
 * @since 2016-12-02
 */
@Controller
@RequestMapping("/ftp")
public class FtpOperationManager implements JobServer{
	
	private static Logger log = Logger.getLogger(FtpOperationManager.class);
	
	@Autowired
	private TrsService trsService;
	
	@ResponseBody
	@RequestMapping("ftpJob")
	public String job(String paramers){
		try {
			/*Date date=new Date();
			String nowTime = DateUtil.DateToStr(date);
			String timestamp =trsService.findTime();*/
			///log.info("【开始时间】"+timestamp+"【结束时间】"+nowTime);
			log.info("【开始产生数据】");
			List<Map> resultList = trsService.findPunish();
			log.info("【结束产生数据】【数据量："+(resultList==null?"0":resultList.size())+"】");
			if(resultList!=null && resultList.size()>0){
				log.info("【开始产生pdf】");
				for(Map<String,Object> m : resultList){//遍历生成pdf文件到指定目录
					GeneratePDF(m);//生成pdf到指定目录
				}
				log.info("【结束产生pdf】");
			}
			log.info("【开始上传FTP】");
			upload();//从指定目录上传到FTP
			log.info("【结束上传FTP】");
			//更新时间点
			//trsService.update(timestamp,nowTime);
		} catch (OptimusException e) {
			log.info("PDF-job异常："+e.getMessage());
		}
		return null;
	}
	
	/**
	 * 上传文件到FTP
	 */
	private void upload() {
		FTPClient ftp = null;
		FileInputStream in = null;
		try {
			File srcDir = FileUtil.createFileOrDir(FtpUtil.LocalPath,false);
			if(srcDir!=null){
				File[] allFile = srcDir.listFiles();
				log.info("【上传PDF文件总数："+(allFile==null?"0":allFile.length)+"】");
				if(allFile!=null && allFile.length>0){
					ftp = FtpUtil.GetFtpConnection();
					String fileName = "";
					for(int currentFile = 0; currentFile < allFile.length; currentFile++){
						fileName = allFile[currentFile].getName();//获取文件名
						in = new FileInputStream(allFile[currentFile]);
						boolean result = ftp.storeFile(new String(fileName.getBytes("GBK"),"iso-8859-1"), in);
						if(result){//如果传完就删除这个文件
							log.info("【上传第"+currentFile+"个PDF文件"+fileName+"成功】");
							in.close();
							in = null;
							allFile[currentFile].delete();
							//FileUtil.delFile(srcName);
						}else{
							log.info("【上传第"+currentFile+"个PDF文件"+fileName+"失败】");
						}
					}
					ftp.logout();
				}
			}
		} catch (Exception e) {
			log.info("上传PDF异常："+e.getMessage());
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					log.info("关闭文件流异常："+e.getMessage());
				} finally {
					in = null;
				}
			}
			FtpUtil.release(ftp);
		}
	}
	
	/**
	 * 生成pdf到指定目录
	 * @param params
	 */
	private void GeneratePDF(Map<String,Object> params) {
		Document document = null;
		String fileName="";
		FileOutputStream fots = null;
		try {
			String caseid =(String) params.get("caseid");
			String penorg=params.get("penorg")==null?"":(String)params.get("penorg");
			String pendecno=params.get("pendecno")==null?"":(String)params.get("pendecno");
			String illegpt=params.get("illegpt")==null?"":(String)params.get("illegpt");
			String regno=params.get("regno")==null?"":(String)params.get("regno");
			String enttype=params.get("enttype")==null?"":(String)params.get("enttype");
			String str="";
			String dom=params.get("dom")==null?"":(String)params.get("dom");
			String lerep=params.get("lerep")==null?"":(String)params.get("lerep");
			Object regcap=params.get("regcap")==null?"":params.get("regcap");
			if(!"".equals(regcap)){
				if("9999".equals(enttype) || "9920".equals(enttype) || "9910".equals(enttype)){
					str = "元";
				}else{
					str = "万元";
				}
			}
			String estdate=params.get("estdate")==null?"":(String)params.get("estdate");
			String opfrom=params.get("opfrom")==null?"":(String)params.get("opfrom");
			String opto=params.get("opto")==null?"":(String)params.get("opto");
			String opscope=params.get("opscope")==null?"":(String)params.get("opscope");
			String illegact=params.get("illegact")==null?"":(String)params.get("illegact");
			String quabasis=params.get("quabasis")==null?"":(String)params.get("quabasis");
			String illegacttype=params.get("illegacttype")==null?"":(String)params.get("illegacttype");
			String penbasis=params.get("penbasis")==null?"":(String)params.get("penbasis");
			String penresult=params.get("penresult")==null?"":(String)params.get("penresult");
			
			fileName=FtpUtil.LocalPath+caseid+".pdf";
			fots = new FileOutputStream(fileName); 
			
			document = new Document(PageSize.A4);
			PdfWriter.getInstance(document, fots);
			BaseFont bfChinese = BaseFont.createFont(FtpOperationManager.class.getResource("/ftl") + "/MSYHL.ttc,1",
					BaseFont.IDENTITY_H, BaseFont.EMBEDDED); 
			Font FontChineseTitle = new Font(bfChinese, 14, Font.BOLD);
			Font FontChineseContent = new Font(bfChinese, 12, Font.NORMAL);
			document.open();
			/*document.addTitle("ID.NET");
			document.addAuthor("dotuian"); 
			document.addSubject("This is the subject of the PDF file."); 
			document.addKeywords("This is the keyword of the PDF file.");*/
			Paragraph paragraph = new Paragraph(penorg+"行政处罚决定书",FontChineseTitle);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			document.add(paragraph);
			
			paragraph = new Paragraph(pendecno,FontChineseTitle);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			document.add(paragraph);
			
			StringBuffer sbf = new StringBuffer("当事人企业名称：").append(illegpt);
			sbf.append("；注册号：").append(regno);
			sbf.append("；公司类型：").append(CodeToValue.codeToValue("T_DM_QYLXDM",enttype));
			sbf.append("；住所：").append(dom);
			sbf.append("；法定代表人：").append(lerep);
			sbf.append("；注册资本：").append(regcap).append(str);
			sbf.append("；成立日期：").append(estdate);
			if(StringUtils.isNotBlank(opto)){
				sbf.append("； 营业期限：").append(opfrom).append("-").append(opto);
			}else{
				sbf.append("； 营业期限：长期");
			}
			sbf.append("； 经营范围：").append(opscope);
			paragraph = new Paragraph(sbf.toString(),FontChineseContent);
			paragraph.setFirstLineIndent(24);
			document.add(paragraph);
			
			if(StringUtils.isNotBlank(illegact)){
				sbf = new StringBuffer("").append(illegact);
				paragraph = new Paragraph(sbf.toString(),FontChineseContent);
				paragraph.setFirstLineIndent(24);
				document.add(paragraph);
			}
			
			sbf = new StringBuffer("当事人的上述行为，违反了").append(quabasis).append("，构成了").append(illegacttype).append(";根据").append(penbasis).append("，并鉴于当事人违法行为的社会影响不大，主动承认错误，根据《中华人民共和国行政处罚法》第五条“实施行政处罚，纠正违法行为，应当坚持处罚与教育相结合，教育公民、法人或者其他组织自觉守法”的规定，我局决定处罚如下：");
			paragraph = new Paragraph(sbf.toString(),FontChineseContent);
			paragraph.setFirstLineIndent(24);
			document.add(paragraph);
			
			if(StringUtils.isNotBlank(penresult)){
				sbf = new StringBuffer(penresult);
				paragraph = new Paragraph(sbf.toString(),FontChineseContent);
				paragraph.setFirstLineIndent(24);
				document.add(paragraph);
			}

			sbf = new StringBuffer("当事人应当自收到本行政处罚决定之日起15日内，到本局POS机缴费窗口刷卡缴纳罚（没）款。逾期不缴纳罚款的，根据《中华人民共和国行政处罚法》第五十一条第一款第（一）项的规定，本局将每日按罚款数额的3%加处罚款。");
			paragraph = new Paragraph(sbf.toString(),FontChineseContent);
			paragraph.setFirstLineIndent(24);
			document.add(paragraph);
			
			sbf = new StringBuffer("当事人如不服本处罚决定，可在收到处罚决定书之日起60日内，向广东省工商行政管理局或者东莞市人民政府申请复议，也可以在6个月内直接向人民法院提起诉讼。");
			paragraph = new Paragraph(sbf.toString(),FontChineseContent);
			paragraph.setFirstLineIndent(24);
			document.add(paragraph);
		} catch (FileNotFoundException e) {
			log.info("FileNotFoundException文件流异常："+e.getMessage());
		} catch (DocumentException e) {
			log.info("DocumentException异常："+e.getMessage());
		} catch (IOException e) {//如果异常将未完全生成好的文件删除
			log.info("IOException流异常："+e.getMessage());
			if(StringUtils.isNotBlank(fileName)&&fileName.contains(".pdf")){
				FileUtil.delFile(fileName);
			}
		} catch (OptimusException e) {
			log.info("OptimusException异常："+e.getMessage());
		} finally {
			if(document!=null){
				document.close();
				document = null;
			}
			if(fots!=null){
				try {
					fots.close();
				} catch (IOException e) {
					log.info("IOException异常："+e.getMessage());
				}finally {
					fots = null;
				}
			}
		}
	}
	/*private void GeneratePDF(Map<String,Object> params) {
		PdfWriter writer = null;
		PdfDocument pdf = null;
		Document document = null;
		PdfFont font = null;
		String fileName="";
		try {
				String caseid =(String) params.get("caseid");
				String penorg=params.get("penorg")==null?"":(String)params.get("penorg");
				String pendecno=params.get("pendecno")==null?"":(String)params.get("pendecno");
				String illegpt=params.get("illegpt")==null?"":(String)params.get("illegpt");
				String regno=params.get("regno")==null?"":(String)params.get("regno");
				String enttype=params.get("enttype")==null?"":(String)params.get("enttype");
				String str="";
				String dom=params.get("dom")==null?"":(String)params.get("dom");
				String lerep=params.get("lerep")==null?"":(String)params.get("lerep");
				Object regcap=params.get("regcap")==null?"":params.get("regcap");
				if(!"".equals(regcap)){
					if("9999".equals(enttype) || "9920".equals(enttype) || "9910".equals(enttype)){
						str = "元";
					}else{
						str = "万元";
					}
				}
				String estdate=params.get("estdate")==null?"":(String)params.get("estdate");
				String opfrom=params.get("opfrom")==null?"":(String)params.get("opfrom");
				String opto=params.get("opto")==null?"":(String)params.get("opto");
				String opscope=params.get("opscope")==null?"":(String)params.get("opscope");
				String illegact=params.get("illegact")==null?"":(String)params.get("illegact");
				String quabasis=params.get("quabasis")==null?"":(String)params.get("quabasis");
				String illegacttype=params.get("illegacttype")==null?"":(String)params.get("illegacttype");
				String penbasis=params.get("penbasis")==null?"":(String)params.get("penbasis");
				String penresult=params.get("penresult")==null?"":(String)params.get("penresult");
				
				fileName=FtpUtil.LocalPath+caseid+".pdf";
				writer = new PdfWriter(fileName);
				pdf = new PdfDocument(writer);
				document = new Document(pdf);
				font = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", false);
				System.out.println("开始");
				System.out.println("===="+(penorg+"行政处罚决定书").replaceAll("…", "...").replaceAll("·","*"));
				Paragraph paragraph = new Paragraph((penorg+"行政处罚决定书").replaceAll("…", "...").replaceAll("·","*").replaceAll("凉","")).setTextAlignment(TextAlignment.CENTER).setFont(font).setFontSize(13);
				document.add(paragraph);
				System.out.println("===="+pendecno.replaceAll("…", "...").replaceAll("·","*"));
				paragraph = new Paragraph(pendecno.replaceAll("…", "...").replaceAll("·","*").replaceAll("凉","")).setTextAlignment(TextAlignment.CENTER).setFont(font).setFontSize(13);
				document.add(paragraph);
				StringBuffer sbf = new StringBuffer("当事人企业名称：").append(illegpt);
				sbf.append("；注册号：").append(regno);
				sbf.append("；公司类型：").append(CodeToValue.codeToValue("T_DM_QYLXDM",enttype));
				sbf.append("；住所：").append(dom);
				sbf.append("；法定代表人：").append(lerep);
				sbf.append("；注册资本：").append(regcap).append(str);
				sbf.append("；成立日期：").append(estdate);
				if(StringUtils.isNotBlank(opto)){
					sbf.append("； 营业期限：").append(opfrom).append("-").append(opto);
				}else{
					sbf.append("； 营业期限：长期");
				}
				sbf.append("； 经营范围：").append(opscope);
				System.out.println("===="+sbf.toString().replaceAll("…", "...").replaceAll("·","*"));
				paragraph = new Paragraph(sbf.toString().replaceAll("…", "...").replaceAll("·","*").replaceAll("凉","")).setFirstLineIndent(24).setFont(font).setFontSize(11);
				document.add(paragraph);
				
				if(StringUtils.isNotBlank(illegact)){
					sbf = new StringBuffer("").append(illegact);
					System.out.println("===="+sbf.toString().replaceAll("…", "...").replaceAll("·","*"));
					paragraph = new Paragraph(sbf.toString().replaceAll("…", "...").replaceAll("·","*").replaceAll("凉","")).setFirstLineIndent(24).setFont(font).setFontSize(11);
					document.add(paragraph);
				}
				
				sbf = new StringBuffer("当事人的上述行为，违反了").append(quabasis).append("，构成了").append(illegacttype).append(";根据").append(penbasis).append("，并鉴于当事人违法行为的社会影响不大，主动承认错误，根据《中华人民共和国行政处罚法》第五条“实施行政处罚，纠正违法行为，应当坚持处罚与教育相结合，教育公民、法人或者其他组织自觉守法”的规定，我局决定处罚如下：");
				System.out.println("===="+sbf.toString().replaceAll("…", "...").replaceAll("·","*"));
				paragraph = new Paragraph(sbf.toString().replaceAll("…", "...").replaceAll("·","*").replaceAll("凉","")).setFirstLineIndent(24).setFont(font).setFontSize(11);
				document.add(paragraph);
				
				if(StringUtils.isNotBlank(penresult)){
					sbf = new StringBuffer(penresult);
					System.out.println("===="+sbf.toString().replaceAll("…", "...").replaceAll("·","*"));
					paragraph = new Paragraph(sbf.toString().replaceAll("…", "...").replaceAll("·","*").replaceAll("凉","")).setFirstLineIndent(0).setFont(font).setFontSize(11);
					document.add(paragraph);
				}

				sbf = new StringBuffer("当事人应当自收到本行政处罚决定之日起15日内，到本局POS机缴费窗口刷卡缴纳罚（没）款。逾期不缴纳罚款的，根据《中华人民共和国行政处罚法》第五十一条第一款第（一）项的规定，本局将每日按罚款数额的3%加处罚款。");
				System.out.println("===="+sbf.toString().replaceAll("…", "...").replaceAll("·","*"));
				paragraph = new Paragraph(sbf.toString().replaceAll("…", "...").replaceAll("·","*").replaceAll("凉","")).setFirstLineIndent(24).setFont(font).setFontSize(11);
				document.add(paragraph);
				
				sbf = new StringBuffer("当事人如不服本处罚决定，可在收到处罚决定书之日起60日内，向广东省工商行政管理局或者东莞市人民政府申请复议，也可以在6个月内直接向人民法院提起诉讼。");
				System.out.println("===="+sbf.toString().replaceAll("…", "...").replaceAll("·","*"));
				paragraph = new Paragraph(sbf.toString().replaceAll("…", "...").replaceAll("·","*").replaceAll("凉","")).setFirstLineIndent(24).setFont(font).setFontSize(11);
				document.add(paragraph);
		} catch (FileNotFoundException e) {
			log.info("FileNotFoundException文件流异常："+e.getMessage());
		} catch (IOException e) {//如果异常将未完全生成好的文件删除
			log.info("IOException流异常："+e.getMessage());
			if(StringUtils.isNotBlank(fileName)&&fileName.contains(".pdf")){
				FileUtil.delFile(fileName);
			}
		} catch (OptimusException e) {
			log.info("OptimusException异常："+e.getMessage());
		} finally{
			if(document!=null){
				document.close();
				document = null;
			}
			if(pdf!=null){
				pdf.close();
				pdf = null;
			}
			if(writer!=null){
				try {
					writer.close();
				} catch (IOException e) {
					log.info("PdfWriter流关闭异常："+e.getMessage());
				}finally{
					writer = null;
				}
			}
		}
	}*/
	
	public static void main(String[] args) throws IOException {
		FtpOperationManager t = new FtpOperationManager();
		//t.upload();
		String[] locations = {"applicationContext.xml","springmvc-servlet.xml"};
        ApplicationContext ctx = new ClassPathXmlApplicationContext(locations);
        TrsService o = (TrsService) ctx.getBean("trsService");
        t.setTrsService(o);
		t.job("");
		
		/*String[] locations = {"applicationContext.xml","springmvc-servlet.xml"};
        ApplicationContext ctx = 
		    new ClassPathXmlApplicationContext(locations);
        FwdyServiceManager o = (FwdyServiceManager) ctx.getBean("fwdyServiceManager");
        MsgSenderService msg = (MsgSenderService) ctx.getBean("msgSenderService");
        */
		/*FTPClient ftp = new FTPClient();
		int reply;
		ftp.connect("10.1.2.124", 21);
		ftp.login("anonymous", "Hello");
		ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
		ftp.setControlEncoding("utf8");
		reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
		}
		creatDirectory(ftp, "/pub/b/c/d");*/
	}

	public TrsService getTrsService() {
		return trsService;
	}

	public void setTrsService(TrsService trsService) {
		this.trsService = trsService;
	}
	
}
