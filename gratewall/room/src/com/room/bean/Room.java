package com.room.bean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

//http://www.cnblogs.com/czj-zhm/p/5884715.html
@Entity
@Table
public class Room {

	private String roomdId;
	private String roomName;
	private String roomLocat;
	private String roomType;
	private Set<RoomPic> roomPics = new HashSet<RoomPic>(0);

	public Room() {
		super();
	}

	public Room(String roomdId) {
		super();
		this.roomdId = roomdId;
	}

	@Id
	@GeneratedValue(generator = "myId")
	@GenericGenerator(name = "myId", strategy = "uuid")
	public String getRoomdId() {
		return roomdId;
	}

	@Column
	public String getRoomLocat() {
		return roomLocat;
	}

	@Column
	public String getRoomName() {
		return roomName;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="pics")
	public Set<RoomPic> getRoomPics() {
		return roomPics;
	}

	@Column
	public String getRoomType() {
		return roomType;
	}

	public void setRoomdId(String roomdId) {
		this.roomdId = roomdId;
	}

	public void setRoomLocat(String roomLocat) {
		this.roomLocat = roomLocat;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public void setRoomPics(Set<RoomPic> roomPics) {
		this.roomPics = roomPics;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

}
