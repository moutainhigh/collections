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
	 * �����ļ� ���ݾ���·����Ҫ��ʾ���ļ�����
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
		String filepath = path;// ���ؾ���·��
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
				// �������������ȶ���Ȼ���������ȥд��Ψһ��ͬ�������õ��ǻ������������
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
				throw new IOException("�ļ�������");
			}
		} else {
			throw new IOException("�ļ�������");
		}
	}
}
