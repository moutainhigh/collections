package com.tz.web.comment;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tz.dao.CommentDao;
import com.tz.model.TzMusicComment;
import com.tz.model.TzMusicPeriod;
import com.tz.model.TzMusicUser;
import com.tz.util.ip.TmIpUtil;

@Controller
public class CommentController {
	@Autowired
	private CommentDao commentDao;
	
	/**
	 * 需要登录的!!!!
	 * 保存评论信息
	 * 方法名：save
	 * 创建人：xuchengfei 时间：2015年3月24日-下午10:45:36 
	 * @param peroidId
	 * @param comment
	 * @param request
	 * @return String
	 * @exception 
	 * @since  1.0.0
	 */
	@ResponseBody
	@RequestMapping(value="/comment/save/{pid}",method=RequestMethod.POST)
	public String saveComment(@PathVariable("pid")Integer peroidId,TzMusicComment comment,HttpServletRequest request){
		TzMusicUser musicUser = (TzMusicUser) request.getSession().getAttribute("user");
		if(musicUser!=null && comment!=null){
			comment.setPeriod(new TzMusicPeriod(peroidId));
			comment.setUser(musicUser);
			comment.setStatus(1);//发布
			comment.setIp(TmIpUtil.getIpAddr(request));
			comment.setIsDelete(0);//未删除
			commentDao.save(comment);
			return "success";
		}else{
			return "fail";
		}
	}
	
	
	/**
	 * 评论模板数据和总页数
	 * 方法名：template
	 * 创建人：xuchengfei 时间：2015年3月25日-下午9:39:12 
	 * @param peroidId
	 * @param pageNo
	 * @param pageSize
	 * @return ModelAndView
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("/ncomment/template/{pid}/{pno}/{psize}")
	public ModelAndView template(@PathVariable("pid")Integer peroidId,@PathVariable("pno")Integer pageNo,@PathVariable("psize")Integer pageSize){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("template/comment/template");
		Integer totalCount = commentDao.countComments(peroidId);//总数
		List<TzMusicComment> comments = commentDao.findComments(peroidId, pageNo, pageSize);//查询期刊对应的评论
		modelAndView.addObject("comments", comments);//存放到作用域中
		modelAndView.addObject("totalCount", totalCount);
		return modelAndView;
	}
}
