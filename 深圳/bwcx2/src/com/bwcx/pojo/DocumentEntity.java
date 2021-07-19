package com.bwcx.pojo;

public class DocumentEntity {
	// https://blog.csdn.net/m0_38084243/article/details/82380873 本文见
//https://tangdudream.iteye.com/blog/1412358
	// https://blog.csdn.net/zengdeqing2012/article/details/78864922
	private String docid;
	private String title;
	private String contentmeta;// 内容概述
	private String editor;
	private String contents;// https://blog.csdn.net/jingtianyiyi/article/details/80421930
	private String tranformeContents;
	private String contentsPlainText;
	private String contentToEd;
	private String savepath;
	private String filename;
	private String thumburl;
	private String attachfilesavepath;
	private String edittime;
	private String channelid;
	private String pubdate;
	private String isHaveThumb;
	private String isPubOrSave;
	private String isFile;
	private Integer count;

	public String getIsFile() {
		return isFile;
	}

	public void setIsFile(String isFile) {
		this.isFile = isFile;
	}

	public String getContentsPlainText() {
		return contentsPlainText;
	}

	public void setContentsPlainText(String contentsPlainText) {
		this.contentsPlainText = contentsPlainText;
	}

	public String getEdittime() {
		return edittime;
	}

	public void setEdittime(String edittime) {
		this.edittime = edittime;
	}

	public String getIsPubOrSave() {
		return isPubOrSave;
	}

	public void setIsPubOrSave(String isPubOrSave) {
		this.isPubOrSave = isPubOrSave;
	}

	public String getContentToEd() {
		return contentToEd;
	}

	public void setContentToEd(String contentToEd) {
		this.contentToEd = contentToEd;
	}

	public String getTranformeContents() {
		return tranformeContents;
	}

	public void setTranformeContents(String tranformeContents) {
		this.tranformeContents = tranformeContents;
	}

	public String getIsHaveThumb() {
		return isHaveThumb;
	}

	public void setIsHaveThumb(String isHaveThumb) {
		this.isHaveThumb = isHaveThumb;
	}

	public String getPubdate() {
		return pubdate;
	}

	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}

	public String getDocid() {
		return docid;
	}

	public void setDocid(String docid) {
		this.docid = docid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContentmeta() {
		return contentmeta;
	}

	public void setContentmeta(String contentmeta) {
		this.contentmeta = contentmeta;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getSavepath() {
		return savepath;
	}

	public void setSavepath(String savepath) {
		this.savepath = savepath;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getThumburl() {
		return thumburl;
	}

	public void setThumburl(String thumburl) {
		this.thumburl = thumburl;
	}

	public String getAttachfilesavepath() {
		return attachfilesavepath;
	}

	public void setAttachfilesavepath(String attachfilesavepath) {
		this.attachfilesavepath = attachfilesavepath;
	}

	public String getChannelid() {
		return channelid;
	}

	public void setChannelid(String channelid) {
		this.channelid = channelid;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
