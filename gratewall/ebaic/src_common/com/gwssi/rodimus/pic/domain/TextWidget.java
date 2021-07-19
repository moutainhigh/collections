package com.gwssi.rodimus.pic.domain;

import java.awt.Font;
/**
 * 在图片上写文字。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class TextWidget {
	
	public TextWidget(int x,int y,String s){
		this.x = x;
		this.y = y;
		this.text = s;
	}
	
	public TextWidget(int x,int y,String s,Font font){
		this.x = x;
		this.y = y;
		this.text = s;
		this.font = font;
	}
	
	private int x = 0;
	private int y = 0;
	private Font font = null;
	private String text = "";
	
	
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
	public Font getFont() {
		return font;
	}
	public void setFont(Font font) {
		this.font = font;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
