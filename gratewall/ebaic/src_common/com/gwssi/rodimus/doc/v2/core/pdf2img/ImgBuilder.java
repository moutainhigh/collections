package com.gwssi.rodimus.doc.v2.core.pdf2img;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.util.ImageIOUtil;

import com.gwssi.rodimus.util.FileUtil;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class ImgBuilder {
	/**
	 * pdf文件转换成jpg文件
	 * 
	 * @param pdfUrl 	 pdf文件绝对路径
	 * @param imgTempUrl 图片保存的路径
	 */
	public static void pdfToImage(String pdfUrl, String imgTempUrl){
        try{
        	//清空文件夹内所有jpg图片
	    	File file = new File(imgTempUrl);
	    	FileUtil.cleanDir(file, "jpg");
	        // 读入PDF
	        PdfReader pdfReader = new PdfReader(pdfUrl);
	        // 计算PDF页码数
	        int pageCount = pdfReader.getNumberOfPages();
	        // 循环每个页码
	        for (int i = pageCount; i >= pdfReader.getNumberOfPages(); i--)
	        {
	            ByteArrayOutputStream out = new ByteArrayOutputStream();
	            PdfStamper pdfStamper = null;
	            PDDocument pdDocument = null;
	
	            pdfReader = new PdfReader(pdfUrl);
	            pdfReader.selectPages(String.valueOf(i));
	            pdfStamper = new PdfStamper(pdfReader, out);
	            pdfStamper.close();
	            // 利用PdfBox生成图像
	            pdDocument = PDDocument.load(new ByteArrayInputStream(out
	                    .toByteArray()));
	            OutputStream outputStream = new FileOutputStream(imgTempUrl
	                    + "ImgName" + "-" + i + ".jpg");
	
	            ImageOutputStream output = ImageIO
	                    .createImageOutputStream(outputStream);
	            PDPage page = (PDPage) pdDocument.getDocumentCatalog()
	                    .getAllPages().get(0);
	            BufferedImage image = page.convertToImage(
	                    BufferedImage.TYPE_INT_RGB, 150);
	            ImageIOUtil.writeImage(image, "jpg", outputStream, 150);
	            if (output != null)
	            {
	                output.flush();
	                output.close();
	            }
	            pdDocument.close();
	        }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
	
//	public static void main(String[] args) {
//		String path = "C:\\share\\ebaic\\doc\\pdf\\110108000\\20160802\\87e027537b2b4da4a81f5439b0169556";
//		File file = new File(path);
//		cleanDir(file, "jpg");
//	}
//	
}
