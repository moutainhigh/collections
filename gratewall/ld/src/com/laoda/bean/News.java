package com.laoda.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class News {

	private String newsId;
	private String title;
	private String contents;
	private String shortTitle;
	private Date createTime;
	private int hits;
	private String newImgSrc;

	@Id
	@GeneratedValue(generator = "myId")
	@GenericGenerator(name = "myId", strategy = "uuid")
	public String getNewsId() {
		return newsId;
	}

	@Column
	public String getTitle() {
		return title;
	}

	@Column
	public String getContents() {
		return contents;
	}

	@Column
	public String getShortTitle() {
		return shortTitle;
	}

	@Column(columnDefinition = "TimeStamp")
	public Date getCreateTime() {
		return createTime;
	}

	@Column
	public int getHits() {
		return hits;
	}

	@Column
	public String getNewImgSrc() {
		return newImgSrc;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public void setNewImgSrc(String newImgSrc) {
		this.newImgSrc = newImgSrc;
	}

	public News() {
		super();
	}

	public News(String newsId) {
		super();
		this.newsId = newsId;
	}

}
