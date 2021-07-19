package com.room.controller;

import java.util.List;

import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.room.bean.Room;
import com.room.dao.RoomDao;
import com.room.service.RoomService;

//http://www.cnblogs.com/nosqlcoco/p/5562107.html
//http://blog.csdn.net/shan9liang/article/details/42181345
//http://blog.csdn.net/boli1020/article/details/39925783
//http://blog.csdn.net/lqg164310221/article/details/53390621
///http://www.cnblogs.com/ssslinppp/p/4530002.html
//http://www.cnblogs.com/qlong8807/p/5534417.html
//http://blog.csdn.net/yfisaboy/article/details/31755631

//http://blog.csdn.net/u010357182/article/details/51931876
//http://blog.csdn.net/shan9liang/article/details/42181345
//https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=org.springframework.web.servlet.view.json.MappingJacksonJsonView&oq=spring4%2520jackson%2520%25E9%2585%258D%25E7%25BD%25AE&rsv_pq=f6cc89e000034d15&rsv_t=850eXHFE4eSgk9kUrqZ5lFUu2QEhDQgrQuy9AmWT7uExCxIAejZQijOdP6U&rqlang=cn&rsv_enter=1&rsv_n=2&rsv_sug3=1&bs=spring4%20jackson%20%E9%85%8D%E7%BD%AE
@Controller
public class GetRoomDataController {

	@Autowired
	private RoomService roomService;

	// @RequestMapping(value="/room",produces = "application/json; charset=utf-8")
	@RequestMapping(value = "/room")
	@ResponseBody
	public Room getRoom() throws JSONException {
		Room room = new Room();
		room.setRoomName("我的测试地址");
		room.setRoomType("222");
		room.setRoomdId("0000");
		room.setRoomLocat("我的家");
		System.out.println(JSONUtil.serialize(room));

		return room;

		// return JSONUtil.serialize(room);
	}

	@RequestMapping("/getRoom/{roomId}")
	@ResponseBody
	public List index(@PathVariable("roomId") String roomId) {
		String id = roomId;
		System.out.println(id);
		Room room = new Room();
		List list = roomService.getRoomListById(id);
		return list;
	}
}
