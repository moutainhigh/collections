package com.gwssi.optimus.core.web.fileupload;

import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.util.DateUtil;
import com.gwssi.optimus.util.UuidGenerator;
import com.gwssi.optimus.util.gif.AnimatedGifEncoder;
import com.gwssi.optimus.util.gif.GifDecoder;
import com.gwssi.optimus.util.gif.Scalr;
import com.gwssi.optimus.util.gif.Scalr.Mode;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UploadFileTool {
	private static final Logger logger = LoggerFactory
			.getLogger(UploadFileTool.class);
	public static final int DEFAULT_BUFFER_SIZE = 4096;
	public static final String PIC_COMPRESS_FLAG = "_compress";
	public static final int PIC_COMPRESS_WIDTH = 200;
	public static final int PIC_COMPRESS_HEIGHT = 200;
	private static String uploadFileTempPath;
	private static int bufferSize;
	private static DiskFileItemFactory localDiskFileItemFactory;
	
	//by chaihaowei
	private static String uploadIconTempDir =null;
	
	public static void init() {
		String rootDir = ConfigManager.getProperty("rootDir");
		String uploadTempDir = ConfigManager.getProperty("upload.tempDir");
		
		/*by chaihaowei*/
		uploadIconTempDir=	ConfigManager.getProperty("upload.IconTempDir");
		if (StringUtils.isEmpty(uploadIconTempDir)){
			uploadIconTempDir = "icontemp";
		}	
		/*bychaihaowei*/		
				
		if (StringUtils.isEmpty(rootDir)) {
			throw new RuntimeException("附件上传临时路径未配置");
		}
		if (StringUtils.isEmpty(uploadTempDir))
			uploadFileTempPath = rootDir + File.separator + "temp";
		else {
			uploadFileTempPath = rootDir + File.separator + uploadTempDir;
		}

		String cfgBufferSize = ConfigManager.getProperty("upload.bufferSize");
		if (StringUtils.isNotEmpty(cfgBufferSize))
			bufferSize = Integer.parseInt(cfgBufferSize);
		else {
			bufferSize = 4096;
		}
		String cfgFileMaxSize = ConfigManager.getProperty("upload.fileMaxSize");

		initUploadTempDir();

		localDiskFileItemFactory = new DiskFileItemFactory();
		localDiskFileItemFactory.setSizeThreshold(bufferSize);
		localDiskFileItemFactory.setRepository(new File(uploadFileTempPath));
	}

	public static void initUploadTempDir() {
		File uploadFileTempDir = new File(uploadFileTempPath);
		if (!(uploadFileTempDir.exists()))
			uploadFileTempDir.mkdirs();
	}

	private static File getTodayTempDir() {
		Calendar calendar = DateUtil.getCurrentDate();
		/*String todayDirPath = uploadFileTempPath + File.separator
				+ calendar.get(1) + File.separator + (calendar.get(2) + 1)
				+ File.separator + calendar.get(5);*/
		String todayDirPath = uploadFileTempPath + File.separator
				+ uploadIconTempDir;

		File todayDir = new File(todayDirPath);
		if (!(todayDir.exists())) {
			todayDir.mkdirs();
		}
		return todayDir;
	}

	public static List<Map> upload(HttpServletRequest request)
			throws OptimusException {
		String swordFileMaxSize = request.getParameter("swordFileMaxSize");
		String swordUploadMaxSize = request.getParameter("swordUploadMaxSize");
		String encoding = request.getCharacterEncoding();
		ServletFileUpload localServletFileUpload = new ServletFileUpload(
				localDiskFileItemFactory);

		localServletFileUpload.setHeaderEncoding(encoding);

		List localList = null;
		try {
			localList = localServletFileUpload.parseRequest(request);
		} catch (FileUploadException e) {
			throw new OptimusException("", e);
		}
		Iterator localIterator = localList.iterator();
		List attachMapList = new ArrayList();
		while (localIterator.hasNext()) {
			FileItem fileItem = (FileItem) localIterator.next();
			if (fileItem.isFormField())
				continue;
			String fileName = fileItem.getName();
			String fileNameTail = fileName.substring(fileName.lastIndexOf("."))
					.toLowerCase();

			String fileId = UuidGenerator.getUUID();
			File todayTempDir = getTodayTempDir();
			File tempFile = new File(todayTempDir, fileId);
			Map attachMap = new HashMap();
			try {
				fileItem.write(tempFile);

				if ((fileNameTail.equals(".jpg"))
						|| (fileNameTail.equals(".jpeg"))
						|| (fileNameTail.equals(".bmp"))
						|| (fileNameTail.equals(".png"))) {
					String compressPicPath = todayTempDir.getAbsolutePath()
							+ "/" + fileId + "_compress";

					resizeImage(tempFile.getAbsolutePath(), compressPicPath,
							200, 200);
				} else if (fileNameTail.equals(".gif")) {
					String compressPicPath = todayTempDir.getAbsolutePath()
							+ "/" + fileId + "_compress";

					resizeGif(tempFile.getAbsolutePath(), compressPicPath, 200,
							200);
				}

				attachMap.put("success", Boolean.valueOf(true));
				attachMap.put("msg", "上传成功");
				attachMap.put("id", fileId);
			} catch (Exception e) {
				attachMap.put("success", Boolean.valueOf(false));
				attachMap.put("msg", e.getMessage());
				logger.error("将上传附件保存到临时目录异常", e);
			}
			attachMapList.add(attachMap);
		}
		return attachMapList;
	}

	public static InputStream load(String fileId) throws FileNotFoundException {
		File localFile = getFile(fileId);
		if (localFile.exists()) {
			FileInputStream localInputStream = new FileInputStream(localFile);
			return localInputStream;
		}
		return null;
	}

	public static File getCompressPic(String fileId) {
		File file = getFile(fileId + "_compress");
		if (file == null)
			file = getFile(fileId);
		return file;
	}

	public static File getFile(String fileId) {
		File file = new File(getTodayTempDir(), fileId);
		if (!(file.exists())) {
			Calendar calendar = DateUtil.getCurrentDate();
			calendar.add(5, -1);
		/*	String yesterdayDirPath = uploadFileTempPath + File.separator
					+ calendar.get(1) + File.separator + (calendar.get(2) + 1)
					+ File.separator + calendar.get(5);*/
			String yesterdayDirPath = uploadFileTempPath + File.separator
					+ uploadIconTempDir;
			
			file = new File(yesterdayDirPath, fileId);
		}
		return file;
	}

	public static void copyFile2TempDir(String fileId, File srcFile) {
		File todayTempDir = getTodayTempDir();
		File destFile = new File(todayTempDir, fileId);
		try {
			if (!(destFile.exists()))
				FileUtils.copyFile(srcFile, destFile);
		} catch (IOException e) {
			logger.error("将回填form的附件拷贝到临时目录：" + destFile.getAbsolutePath()
					+ "异常", e);
		}
	}

	public static String getFileFormatSize(double size, int scale) {
		double kiloByte = size / 1024.0D;
		if (kiloByte < 1.0D) {
			return size + "Bytes";
		}
		double megaByte = kiloByte / 1024.0D;
		if (megaByte < 1.0D) {
			BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
			return result1.setScale(scale, 4).toPlainString() + "K";
		}

		double gigaByte = megaByte / 1024.0D;
		if (gigaByte < 1.0D) {
			BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
			return result2.setScale(scale, 4).toPlainString() + "M";
		}

		double teraBytes = gigaByte / 1024.0D;
		if (teraBytes < 1.0D) {
			BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
			return result3.setScale(scale, 4).toPlainString() + "G";
		}
		BigDecimal result4 = new BigDecimal(teraBytes);
		return result4.setScale(scale, 4).toPlainString() + "T";
	}

	public static void resizeImage(String srcImgPath, String distImgPath,
			int width, int height) throws IOException {
		File srcFile = new File(srcImgPath);
		Image srcImg = ImageIO.read(srcFile);
		int srcWidth = srcImg.getWidth(null);
		int srcHeight = srcImg.getHeight(null);

		if ((srcWidth <= width) && (srcHeight <= height)) {
			return;
		}
		BufferedImage buffImg = null;
		buffImg = new BufferedImage(width, height, 1);
		buffImg.getGraphics().drawImage(
				srcImg.getScaledInstance(width, height, 4), 0, 0, null);

		ImageIO.write(buffImg, "JPEG", new File(distImgPath));
	}

	public static void resizeGif(String srcImgPath, String distImgPath,
			int width, int height) {
		FileOutputStream fos = null;
		try {
			GifDecoder gd = new GifDecoder();
			int status = gd.read(new FileInputStream(new File(srcImgPath)));
			if (status != 0) {
				return;
			}
			AnimatedGifEncoder ge = new AnimatedGifEncoder();
			fos = new FileOutputStream(new File(distImgPath));
			ge.start(fos);
			ge.setRepeat(0);
			for (int i = 0; i < gd.getFrameCount(); ++i) {
				BufferedImage frame = gd.getFrame(i);
				BufferedImage rescaled = Scalr.resize(frame,
						Scalr.Mode.FIT_EXACT, width, height,
						new BufferedImageOp[0]);
				int delay = gd.getDelay(i);
				ge.setDelay(delay);
				ge.addFrame(rescaled);
			}
			ge.finish();
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			IOUtils.closeQuietly(fos);
		}
	}
}