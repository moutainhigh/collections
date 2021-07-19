package com.gwssi.ebaic.common.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.io.IOUtils;

import com.gwssi.optimus.util.gif.AnimatedGifEncoder;
import com.gwssi.optimus.util.gif.GifDecoder;
import com.gwssi.optimus.util.gif.Scalr;
import com.gwssi.optimus.util.gif.Scalr.Mode;
import com.gwssi.rodimus.config.ConfigUtil;
import com.gwssi.rodimus.exception.UploadException;

/**
 * 图片处理类。
 * 
 * @author lxb
 */
public class ImageUtil {
	
	/**
	 * 裁剪图片
	 * 
	 * @param src
	 *            源图片
	 * @param dest
	 *            裁剪后的图片
	 * @param x
	 *            裁剪范围的X坐标
	 * @param y
	 *            裁剪范围的Y坐标
	 * @param w
	 *            裁剪范围的宽度
	 * @param h
	 *            裁剪范围的高度
	 * @throws IOException
	 */
	public static void cutImage(String src, String dest, int x, int y, int w,
			int h)  {
		try {
			File file = new File(ConfigUtil.get("file.rootPath")+src);
			if (file.exists()) {
				//取文件扩展名
				String fname = file.getName();
				String suffix = fname.substring(fname.lastIndexOf(".")+1, fname.length());
				System.out.println("suffix="+suffix);
				Iterator<ImageReader> iterator = ImageIO
						.getImageReadersByFormatName(suffix);
				ImageReader reader = (ImageReader) iterator.next();
				InputStream in = new FileInputStream(ConfigUtil.get("file.rootPath")+src);
				ImageInputStream iis = ImageIO.createImageInputStream(in);
				reader.setInput(iis, true);
				ImageReadParam param = reader.getDefaultReadParam();
				Rectangle rect = new Rectangle(x, y, w, h);
				param.setSourceRegion(rect);
				BufferedImage bi = reader.read(0, param);
				File newFile =  new File(ConfigUtil.get("file.rootPath")+dest);
				if(!newFile.exists()){
					newFile.mkdirs();
				}
				ImageIO.write(bi, suffix, newFile);

				iis.close();
				in.close();
				// 最后删除原文件
				//file.delete();
			} else {
				throw new UploadException("要裁剪的文件不存在");
				//System.out.println("找不到要裁剪的文件：FileName=" + src);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new UploadException("剪切图片出错：" + ex.getMessage(), ex);
		}
	}

	/**
	 * 压缩图片。
	 * 
	 * @param src
	 * @param dest
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public static void compressImage(String src, String dest, int x, int y, int w,
			int h) {
		// FIXME  未实现
	}
	
	/***
	    * 功能 :调整图片大小 开发 ；如果原图的高、宽均比要压缩的高、宽小，则不压缩
	    * @param srcImgPath 原图片路径
	    * @param distImgPath  转换大小后图片路径
	    * @param width   转换后图片宽度
	    * @param height  转换后图片高度
	    * @return true:生成压缩图，false:原图足够小，不用生成新图片
	    */
	    public static boolean resizeImage(String srcImgPath, String destImgPath, 
	            int width, int height) throws IOException {
	        File srcFile = new File(ConfigUtil.get("file.rootPath")+srcImgPath);
	        Image srcImg = ImageIO.read(srcFile);
	        int srcWidth = srcImg.getWidth(null);
	        int srcHeight = srcImg.getHeight(null);
	        //如果原图的高、宽均比要压缩的高、宽小，则不压缩
	        if(srcWidth<=width && srcHeight<=height){
	            return false;
	        }
	        BufferedImage buffImg = null;
	        buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	        buffImg.getGraphics().drawImage(
	                srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
	        ImageIO.write(buffImg, "JPEG", new File(ConfigUtil.get("file.rootPath")+destImgPath));
	        return true;
	    }
	    
	    /**
	     * 调整gif图片大小
	     * @param srcImgPath
	     * @param distImgPath
	     * @param width
	     * @param height
	     * @return
	     */
	    public static boolean resizeGif(String srcImgPath, String distImgPath, int width,
	            int height){
	        FileOutputStream fos = null;
	        try {
	            GifDecoder gd = new GifDecoder();
	            int status = gd.read(new FileInputStream(new File(ConfigUtil.get("file.rootPath")+srcImgPath)));
	            if (status != GifDecoder.STATUS_OK) {
	                return false;
	            }
	            AnimatedGifEncoder ge = new AnimatedGifEncoder();
	            fos = new FileOutputStream(new File(ConfigUtil.get("file.rootPath")+distImgPath));
	            ge.start(fos);
	            ge.setRepeat(0);
	            for (int i = 0; i < gd.getFrameCount(); i++) {
	                BufferedImage frame = gd.getFrame(i);
	                BufferedImage rescaled = Scalr.resize(frame, Mode.FIT_EXACT, width, height);
	                int delay = gd.getDelay(i);
	                ge.setDelay(delay);
	                ge.addFrame(rescaled);
	            }
	            ge.finish();
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            IOUtils.closeQuietly(fos);
	        }
	        return true;
	    }

	    /**
		 * 将多个图片文件合并成一个图片文件
		 * @param srcPics 要合并的图片地址列表 地址+文件名
		 * @param destDir 生成图片的目录
		 * @param outName 生成的文件名
		 * @param flag    标识，x表示横向合并，y表示纵向合并
		 * @return 
		 */
		public static void mergePic(List<String> srcPics,String destDir,String outName,String flag) {
			
			flag="y";//测试 默认纵向合并
			if("x".equals(flag)){//横向合并
				xPic(srcPics, destDir,outName);
			}else if("y".equals(flag)){
				yPic(srcPics,destDir, outName);//纵向合并
			}
		}
		
		/**
		 * 将多张图片横向合并成一张
		 * @param srcPics
		 * @param destDir
		 * @param outName
		 */
		public static  void xPic(List<String> srcPics,String destDir,String outName) {//横向处理图片
			try {
				if(srcPics!=null && srcPics.size()>0){
					
					BufferedImage outImage = null;
					int curWidth=20;
					
					int len = srcPics.size();
					for(int i=0;i<len;i++){
						File tmpFile = new File(ConfigUtil.get("file.rootPath")+srcPics.get(i));
						BufferedImage tmpImage = ImageIO.read(tmpFile);
						
						int tmpWidth = tmpImage.getWidth();
						int tmpHeight = tmpImage.getHeight();// 图片高度
						int[] tmpImageArray = new int[tmpWidth * tmpHeight];// 从图片中读取RGB
						tmpImageArray = tmpImage.getRGB(0, 0, tmpWidth, tmpHeight, tmpImageArray, 0, tmpWidth);
						
						if(i==0){
							//背景图大小
							outImage = new BufferedImage(842, 595,BufferedImage.TYPE_INT_RGB);
							Graphics g= outImage.getGraphics();
							g.setColor(Color.white);
							g.fillRect(0, 0, 842, 595);
							
						}
						outImage.setRGB(curWidth, (842-tmpHeight)/2, tmpWidth, tmpHeight, tmpImageArray, 0, tmpWidth);// 设置该图片所在位置的RGB
						curWidth+=(tmpWidth+20);
					}
					File outFile = new File(ConfigUtil.get("file.rootPath")+destDir+outName);
					ImageIO.write(outImage, "jpg", outFile);// 写图片
				}			
			} catch (Exception e) {
				throw new RuntimeException("图片合成失败："+e.getMessage());
			}
		}
		/**
		 * 将多张图片纵向合并成一张
		 * @param srcPics 要合并的图片绝对路径列表 List中的所有图片合成一张
		 * @param destDir
		 * @param outName
		 */
		public static void yPic(List<String> srcPics,String destDir,String outName){//纵向处理图片
			long start=System.currentTimeMillis();
			
			try {
				if(srcPics!=null && srcPics.size()>0){
					
					BufferedImage outImage = null;
					int curHeight=0;
					int offsetHeight=0;
					BufferedImage tmp=null;
					int len = srcPics.size();
					for(int i=0;i<len;i++){
						File tmpFile = new File(ConfigUtil.get("file.rootPath")+srcPics.get(i));
						//File tmpFile = new File(srcPics.get(i));
						if(!tmpFile.exists()){
							System.out.println("要合并的图片不存在："+srcPics.get(i));
							continue;
						}
						BufferedImage tmpImage = ImageIO.read(tmpFile);
						
						int tmpWidth = tmpImage.getWidth();
						int tmpHeight = tmpImage.getHeight();// 图片高度
						if(tmpWidth>595 || tmpHeight>(842/len-10)){
							//图片太大进行缩放
							double toWidth=500;
							double toHeight=842/len-20;
							double hRate = toHeight/tmpHeight;
							double wRate = toWidth/tmpWidth;
							if(hRate < wRate){
								toWidth=tmpWidth*hRate;
								tmpWidth=(int)toWidth;
								tmpHeight=(int)toHeight;
							
							}else{
								toHeight=tmpHeight*wRate;
								tmpWidth=500;
								tmpHeight=(int)toHeight;							
							}
							
							tmp = new BufferedImage(tmpWidth, tmpHeight,  
				                    BufferedImage.TYPE_INT_RGB);  
				  
				            tmp.getGraphics().drawImage(  
				            		tmpImage.getScaledInstance(tmpWidth, tmpHeight,  
				                            java.awt.Image.SCALE_SMOOTH), 0, 0, null);
				            tmpImage=tmp;
						}
						offsetHeight = (842/len-tmpHeight)/2;
						int[] tmpImageArray = new int[tmpWidth * tmpHeight];// 从图片中读取RGB
						tmpImageArray = tmpImage.getRGB(0, 0, tmpWidth, tmpHeight, tmpImageArray, 0, tmpWidth);
						
						if(i==0 || outImage ==null){
							//背景图大小
							outImage = new BufferedImage(595, 842,BufferedImage.TYPE_INT_RGB);
							Graphics g= outImage.getGraphics();
							g.setColor(Color.white);
							g.fillRect(0, 0, 595, 842);
							
						}
						outImage.setRGB((595-tmpWidth)/2, curHeight+offsetHeight, tmpWidth, tmpHeight, tmpImageArray, 0, tmpWidth);// 设置该图片所在位置的RGB
						curHeight+=(842/len);
					}
					File outDir = new File(ConfigUtil.get("file.rootPath")+destDir);
					//File outDir = new File(destDir);
					if(!outDir.exists()){
						outDir.mkdirs();
					}
					
					if(outImage!=null){
						System.out.println("生成的新文件："+destDir+outName);
						//File outFile = new File(destDir+outName);
						FileOutputStream out = new FileOutputStream(ConfigUtil.get("file.rootPath")+destDir+outName);
						//FileOutputStream out = new FileOutputStream(destDir+outName);
						
						ImageIO.write(outImage, "jpg", out);// 写图片
						out.close();
					}
					
				}			
				
			} catch (Exception e) {
				throw new RuntimeException("图片合成失败："+e.getMessage());
			}
			long end=System.currentTimeMillis();
			System.out.println("图片合并完成，耗时："+(end-start)+"毫秒");
		}	    
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("C:\\Users\\LXB\\Desktop\\1.jpg");
		list.add("C:\\Users\\LXB\\Desktop\\2.jpg");
		try {
			//resizeImage("C:\\Users\\LXB\\Desktop\\Koala.jpg","C:\\Users\\LXB\\Desktop\\Koala1.jpg",800,600);
			//resizeGif("C:\\Users\\LXB\\Desktop\\123.gif","C:\\Users\\LXB\\Desktop\\321.gif",50,50);
			//cutImage("C:\\Users\\LXB\\Desktop\\Koala.jpg","C:\\Users\\LXB\\Desktop\\Koala_cut.jpg",400,100,400,400);
			mergePic(list, "C:\\Users\\LXB\\Desktop\\", "merge.jpg", "y");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
