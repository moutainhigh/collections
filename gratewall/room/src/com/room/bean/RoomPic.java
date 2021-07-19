package com.room.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class RoomPic {

	private String roomPicId;
	private String picURL;
	private Integer isCover;

	@Column
	public Integer getIsCover() {
		return isCover;
	}

	@Column
	public String getPicURL() {
		return picURL;
	}

	@Id
	@GeneratedValue(generator = "myId")
	@GenericGenerator(name = "myId", strategy = "uuid")
	public String getRoomPicId() {
		return roomPicId;
	}

	
	public void setIsCover(Integer isCover) {
		this.isCover = isCover;
	}

	public void setPicURL(String picURL) {
		this.picURL = picURL;
	}

	public void setRoomPicId(String roomPicId) {
		this.roomPicId = roomPicId;
	}
	

}
