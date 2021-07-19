package com.laoda.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class ImageThumb {

	private String imageId;
	private String ImageAlt;
	private String imgSrc;
	private Integer isCover;

	public ImageThumb() {
		super();
	}

	public ImageThumb(String imageId) {
		super();
		this.imageId = imageId;
	}

	@Column
	public String getImageAlt() {
		return ImageAlt;
	}

	@Id
	@GeneratedValue(generator = "myId")
	@GenericGenerator(name = "myId", strategy = "uuid")
	public String getImageId() {
		return imageId;
	}

	@Column
	public String getImgSrc() {
		return imgSrc;
	}

	@Column
	public Integer getIsCover() {
		return isCover;
	}

	public void setImageAlt(String imageAlt) {
		ImageAlt = imageAlt;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

	public void setIsCover(Integer isCover) {
		this.isCover = isCover;
	}

}
