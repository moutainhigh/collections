package com.room.bean;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class Fav {

	private String favId;
	private Room room;
	private User user;

	@Id
	@GeneratedValue(generator = "myId")
	@GenericGenerator(name = "myId", strategy = "uuid")
	public String getFavId() {
		return favId;
	}

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name="roomId")
	public Room getRoom() {
		return room;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="userId")
	public User getUser() {
		return user;
	}

	public void setFavId(String favId) {
		this.favId = favId;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
