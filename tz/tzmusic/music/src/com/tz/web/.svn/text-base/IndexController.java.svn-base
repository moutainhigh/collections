package com.tz.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tz.dao.MusicPeriodDao;
import com.tz.model.TzMusicPeriod;

@Controller
public class IndexController {

	@Autowired
	private MusicPeriodDao musicPeriodDao;//查询期刊
	
	/**
	 * 首页跳转
	 * 方法名：index
	 * 创建人：xuchengfei 时间：2015年3月18日-上午12:40:11 
	 * @return String
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	/**
	 * 播放页面
	 * 方法名：music
	 * 创建人：xuchengfei 时间：2015年3月18日-上午12:40:17 
	 * @param id
	 * @return ModelAndView
	 * @exception 
	 * @since  1.0.0
	 * 
	 */
//	@RequestMapping("/play/{id}")
//	public String music(@PathVariable("id")Integer id,ModelMap map) {
//		TzMusicPeriod period = musicPeriodDao.getMusicPeriod(id);
//		map.addAttribute("period", period);
//		return "index";
//	}
	
	@RequestMapping("/music/{id}")
	public ModelAndView music(@PathVariable("id")Integer id) {
		TzMusicPeriod period = musicPeriodDao.getMusicPeriod(id);
		ModelAndView modelAndView  = new ModelAndView();
		modelAndView.setViewName("index");
		modelAndView.addObject("period", period);
		return modelAndView;
	}
}
