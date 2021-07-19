/*package com.gwssi.rodimus.doc.core;

import com.gwssi.rodimus.util.FileUtil;

import gui.ava.html.Html2Image;

public class Test2 {

	public static void main(String[] args) {
		String path = "C:/DOCETST/";
		String id = "AABXnDAAMAACjh+AAA";
		long begin = System.currentTimeMillis();
		//long end = System.currentTimeMillis();
		String file = path + id + ".html";
		String html = FileUtil.readFile(file);
		System.out.println("耗时1："+(System.currentTimeMillis()-begin));
		begin = System.currentTimeMillis();
		
		Html2Image html2Image = Html2Image.fromHtml(html);
		System.out.println("耗时2："+(System.currentTimeMillis()-begin));
		begin = System.currentTimeMillis();
		String imageUrl = path + id + ".png";
		html2Image.getImageRenderer().saveImage(imageUrl);
		System.out.println("耗时3："+(System.currentTimeMillis()-begin));
		
		//System.out.println(end-begin);
		System.out.println("success...");
	}

}
*/