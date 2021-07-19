package com.tz.web.music;

import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tz.dao.MusicDao;
import com.tz.model.TzMusic;
import com.tz.web.upload.TmFileUtil;

/**
 * 
 * 音乐列表控制层
 * MusicController
 * 创建人:keke
 * 时间：2015年3月26日-下午11:43:16 
 * @version 1.0.0
 *
 */
@Controller
@RequestMapping("/tzmusic")
public class MusicController {
	
	@Autowired
	private MusicDao musicDao;
	
	/**
	 * 保存音乐列表
	 * 方法名：save
	 * 创建人：xuchengfei 时间：2015年3月26日-下午11:43:08 
	 * @param music
	 * @return TzMusic
	 * @throws JSONException 
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping(value="/save",method=RequestMethod.POST)
	@ResponseBody
	public String save(TzMusic music) throws JSONException{
		music.setPattern(TmFileUtil.getExtNoPoint(music.getPath()));
		music.setLabelPath(null);
		music.setSort(0);
		music.setIsDelete(0);//删除状态0为删除1删除
		music.setHit(0);//点击数
		music.setStatus(0);// 发布状态0未发布1发布
		music = musicDao.save(music);
		String result = JSONUtil.serialize(music,null,null,false,true);
		return result;
	}
	
	/**
	 * 修改音乐
	 * 方法名：save
	 * 创建人：xuchengfei 时间：2015年3月26日-下午11:43:08 
	 * @param music
	 * @return TzMusic
	 * @throws JSONException 
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	public String update(TzMusic music){
		try {
			musicDao.update(music);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}
	
	/**
	 * 修改音乐
	 * 方法名：save
	 * 创建人：xuchengfei 时间：2015年3月26日-下午11:43:08 
	 * @param music
	 * @return TzMusic
	 * @throws JSONException 
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping(value="/delete/{id}",method=RequestMethod.POST)
	@ResponseBody
	public String update(@PathVariable("id")Integer id){
		try {
			TzMusic music = new TzMusic(id);
			music.setIsDelete(1);
			musicDao.update(music);
			//musicDao.delete(id);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}
}
