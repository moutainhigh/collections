package com.gwssi.rodimus.doc.v1.core.html2img;

/**
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class PageConfig {
	
	public static final PageConfig A4 = new PageConfig(0,0, 0,0,0,0);
	public static final PageConfig A4_landscape = new PageConfig(0,0, 0,0,0,0);
	
	private PageConfig(int width,int height,int marginTop,int marginBottom,
			int marginLeft,int marginRight){
		this.width = width;
		this.height = height;
		this.marginBottom = marginBottom;
		this.marginLeft = marginLeft;
		this.marginRight = marginRight ;
		this.marginTop = marginTop ;
	}
	
	private int width;
	private int height;
	
	private final int marginTop;
	private final int marginBottom;
	private final int marginLeft;
	private final int marginRight;
	
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public int getMarginTop() {
		return marginTop;
	}
	public int getMarginBottom() {
		return marginBottom;
	}
	public int getMarginLeft() {
		return marginLeft;
	}
	public int getMarginRight() {
		return marginRight;
	}
}
