package com.ye.monitor.from;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ye.monitor.YeStockHttpUtil;

//https://wenku.baidu.com/view/0fb4c331a58da0116d174983.html
//http://blog.csdn.net/djun100/article/details/25748419
//http://tieba.baidu.com/p/4907062727
//http://blog.csdn.net/u010248330/article/details/69258531?locationNum=14&fps=1
//https://www.cnblogs.com/bcsflilong/p/4245336.html ½âÎö
@Component
public class WangYiWeb {
public static Logger log = LogManager.getLogger(WangYiWeb.class);
	
	//http://api.money.126.net/data/feed/1000983
	//http://api.money.126.net/data/feed/2600391
	@Autowired
	private YeStockHttpUtil yeStockHttpUtil;
}
