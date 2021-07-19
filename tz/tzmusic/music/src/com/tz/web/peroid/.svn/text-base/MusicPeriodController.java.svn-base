package com.tz.web.peroid;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tz.dao.MusicPeriodDao;
import com.tz.model.TzMusicPeriod;
import com.tz.model.TzMusicUser;
import com.tz.util.TmStringUtils;

@Controller
@RequestMapping("/period")
public class MusicPeriodController {

	@Autowired
	private MusicPeriodDao periodDao;
	
	
	/**
	 * 查询期刊列表
	 * 方法名：list
	 * 创建人：xuchengfei 时间：2015年3月29日-上午12:39:33 
	 * @param request
	 * @return ModelAndView
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping(value="/list")
	public ModelAndView  list(HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("music/list");
		return modelAndView;
	}
	
	@RequestMapping(value="/template")
	public ModelAndView  template(HttpServletRequest request){
		String keyword = request.getParameter("keyword");
		String typeId = request.getParameter("typeId");
		ModelAndView modelAndView = new ModelAndView();
		List<TzMusicPeriod> periods=  periodDao.findPeriods(keyword, TmStringUtils.isNotEmpty(typeId)?new Integer(typeId):null);
		modelAndView.addObject("periods", periods);
		modelAndView.setViewName("template/period/template");
		return modelAndView;
	}
	
	
	/**
	 * 跳转到添加音乐的页面
	 * 方法名：addmusic
	 * 创建人：xuchengfei 时间：2015年3月26日-下午9:27:20 
	 * @return ModelAndView
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("/addmusic")
	public ModelAndView addmusic(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("music/add");
		return modelAndView;
	}
	
	/**
	 * 编辑页面
	 * 方法名：addmusic
	 * 创建人：xuchengfei 时间：2015年3月26日-下午9:28:45 
	 * @param id
	 * @return ModelAndView
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("/editmusic/{id}")
	public ModelAndView addmusic(@PathVariable("id")Integer id){
		ModelAndView modelAndView = new ModelAndView();
		if(id!=null){//修改
			TzMusicPeriod period = periodDao.getMusicPeriod(id);
			modelAndView.addObject("period", period);
		}
		modelAndView.setViewName("music/add");
		return modelAndView;
	}
	
	/**
	 * 保存期刊
	 * 方法名：save
	 * 创建人：xuchengfei 时间：2015年3月26日-上午1:01:08 
	 * @param period
	 * @param request
	 * @return String
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping(value="/save",method=RequestMethod.POST)
	@ResponseBody
	public TzMusicPeriod save(TzMusicPeriod period,HttpServletRequest request){
		TzMusicUser musicUser = (TzMusicUser)request.getSession().getAttribute("user");
		period.setMusicUser(musicUser);//保存用户
		period.setStatus(1);//发布状态1发布0未发布
		period.setIsDelete(0);//删除状态0为删除1删除
		period.setHit(0);//点击数
		TzMusicPeriod musicPeriod = periodDao.save(period);
		if(musicPeriod!=null){
			return musicPeriod;
		}else{
			return null;
		}
	}
	
	/**
	 * 新增，修改，删除的时候一定引起重视
	 * update xxx where id = 1 and user=id
	 * 方法名：update
	 * 创建人：xuchengfei 时间：2015年3月26日-下午9:41:54 
	 * @param period
	 * @param request
	 * @return TzMusicPeriod
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	public TzMusicPeriod update(TzMusicPeriod period){
		if(period!=null && period.getId()!=null){
			TzMusicPeriod musicPeriod = periodDao.update(period);
			if(musicPeriod!=null){
				return musicPeriod;
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
}
