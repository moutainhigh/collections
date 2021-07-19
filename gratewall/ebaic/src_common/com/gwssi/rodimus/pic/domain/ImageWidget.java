package com.gwssi.rodimus.pic.domain;

/**
 * 在图片上写文字。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class ImageWidget {
	
	public ImageWidget(int x, int y, float alpha, String src){
		this.x = x;
		this.y = y;
		this.alpha = alpha ;
		this.src = src ;
	}
	
	
	private int x = 0;
	private int y = 0;
	private float alpha = 0.5f;
	private String src = "";
	
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public float getAlpha() {
		return alpha;
	}
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
	
	
}
