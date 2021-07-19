package com.gwssi.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadUtil
{
	/**
	 * 下载文件 根据绝对路径和要显示的文件名称
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void downloadFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		String path = request.getParameter("file");
		String fileName = request.getParameter("fileName");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		OutputStream fos = null;
		InputStream fis = null;
		String filepath = path;// 本地绝对路径
		File file = new File(filepath);
		if (file.exists()) {
			try {
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				fos = response.getOutputStream();
				bos = new BufferedOutputStream(fos);
				response.setHeader("Content-disposition",
						"attachment;filename="
								+ URLEncoder.encode(fileName, "utf-8"));
				int bytesRead = 0;
				// 用输入流进行先读，然后用输出流去写，唯一不同的是我用的是缓冲输入输出流
				byte[] buffer = new byte[8192];
				while ((bytesRead = bis.read(buffer, 0, 8192)) != -1) {
					bos.write(buffer, 0, bytesRead);
				}
				bos.flush();
				fis.close();
				bis.close();
				fos.close();
				bos.close();
			} catch (Exception e) {
				throw new IOException("文件不存在");
			}
		} else {
			throw new IOException("文件不存在");
		}
	}
}
