package com.bwcx.pojo;


public class DocumentEntity {

	private String docid;
	private String title;
	private String contentMeta;//内容概述
	private String editor;
	private String contents;
	private String savePath;
	private String filename;
	private String thumbUrl;
	private String attachFileSavePath;
	private String channelId;
	
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
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
	public String getContentMeta() {
		return contentMeta;
	}
	public void setContentMeta(String contentMeta) {
		this.contentMeta = contentMeta;
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
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getThumbUrl() {
		return thumbUrl;
	}
	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}
	public String getAttachFileSavePath() {
		return attachFileSavePath;
	}
	public void setAttachFileSavePath(String attachFileSavePath) {
		this.attachFileSavePath = attachFileSavePath;
	}
	
	
	
}
