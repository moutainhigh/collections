package com.laoda.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.laoda.bean.News;
import com.laoda.dao.NewsDao;

@Controller
@RequestMapping("/news")
public class NewsController {

	@Autowired
	private NewsDao newsDao;

	
	@ResponseBody
	@RequestMapping("/list/{pageIndex}")
	public List getNewsList(@PathVariable int pageIndex) {
		List list  = newsDao.getNewsListByPage(pageIndex, 10);

		return list;
	}
	
	
	@ResponseBody
	@RequestMapping("/save")
	public int getNewsList(News news) {
		//
		News newsBo = new News();
		news.setContents("我是测试新闻");
		news.setHits(0);
		news.setCreateTime(new Date());
		news.setTitle("前海诞生多项法治创新成果，环境获近九成企业认可");
		news.setShortTitle("\r\n" + 
				"大鹏\"减船转产\"启动 今年申请渔民可获得23.2万元补贴");
		news.setNewImgSrc("http://n.sinaimg.cn/mil/8_ori/upload/f8bc40b5/20171011/9uKX-fymrcpw5440035.gif");
		newsDao.save(news);
		System.out.println(111);
		return 1;
	}
}
