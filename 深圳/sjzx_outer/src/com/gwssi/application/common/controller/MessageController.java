package com.gwssi.application.common.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.application.common.service.MessageService;
import com.gwssi.application.model.SmNoticeBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.controller.BaseController;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;
import com.gwssi.optimus.util.DateUtil;


@Controller
@RequestMapping("/message")
public class MessageController extends BaseController{
	
	@Autowired
	private MessageService messageService;
	
	/**添加页面的跳转
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("add")
	public void addDef(OptimusRequest req, OptimusResponse resp) throws OptimusException{
		
		//获取当前登录人
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		String username = user.getUserName();
		
		//获取当前日期
		Calendar calendar = Calendar.getInstance();
		String currentDate = DateUtil.toDateStrWithTime(calendar);
		
		//封装数据
		resp.addAttr("username", username);
		resp.addAttr("currentDate", currentDate);
		
		//进入添加页面
		resp.addPage("/page/common/message_edit.jsp");
	}
	
	/**查询消息
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("query")
	public void queryMessage(OptimusRequest req, OptimusResponse resp) throws OptimusException{
		
		//获取数据
		Map params = req.getForm("formpanel");
		
		//获取当前登录人
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		String userid = user.getUserId();
		
		//查询消息
		List list = messageService.queryMessage(params,userid);
		
		//封装数据并返回前台
		resp.addGrid("gridpanel", list);
		
	}
	
	/**岗位资源树查询
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("findByListPost")
	@ResponseBody
	public void findByListPost(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		List listPost = messageService.findPostTree();
        Map rootNode = new HashMap();
        rootNode.put("name", "深圳市市场监督管理委");
        rootNode.put("id", "1");
        rootNode.put("open", true);
		listPost.add(rootNode);
		resp.addTree("sendTo", listPost);

	}
	
	/**维护、新增页面的保存
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("save")
	public void savaMessage(OptimusRequest req, OptimusResponse resp) throws OptimusException{
		
		String back="fail";
		
		String sendTos =  req.getAttr("sendTos").toString();
		
		//获取添加、修改页面中填写的数据
		Map<String,String> params = req.getForm("formpanel_edit");
		SmNoticeBO smNoticeBO = req.getForm("formpanel_edit",SmNoticeBO.class);
		
		smNoticeBO.setSendTo(sendTos);
		/*//获取当前时间 ,将String 转换为Calendar
		String str= params.get("modifierTime");
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);*/
		
		//获取当前登陆人id
		HttpSession session = WebContext.getHttpSession();
		User u = (User) session.getAttribute(OptimusAuthManager.USER);
		String userid = u.getUserId();
		
		//将当前时间、登陆人id填入最后修改时间、最后修改人id
		smNoticeBO.setModifierTime(Calendar.getInstance());
		smNoticeBO.setModifierId(userid);
		
		//pkNotice为空，为添加页面；反之，为修改页面
		if(StringUtils.isBlank(smNoticeBO.getPkNotice())){
			
			//将当前时间、登陆人id填入创建时间、创建人id
			smNoticeBO.setCreaterTime(Calendar.getInstance());
			smNoticeBO.setCreaterId(userid);
			
			//将当前时间、登陆人id填入最后修改时间、最后修改人id
			smNoticeBO.setModifierTime(Calendar.getInstance());
			smNoticeBO.setModifierId(userid);
			
			//保存添加的信息
			messageService.saveMessage(smNoticeBO);
			
			back="success";
		}
				
		else{
			
			//获取当前登录人
			User user = (User) session.getAttribute(OptimusAuthManager.USER);
			String username = user.getUserName();
			smNoticeBO.setModifierName(username);
			smNoticeBO.setModifierId(userid);
			smNoticeBO.setModifierTime(Calendar.getInstance());
			
			//保存修改的信息
			messageService.updateMessage(smNoticeBO);
						
			back="success";
					
		}
				
		//封装数据并返回前台
		resp.addAttr("back", back);
		resp.addAttr("PkNotice",smNoticeBO.getPkNotice());
				
	}
	
	/**维护页面的跳转
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("update")
	public void updateDef(OptimusRequest req, OptimusResponse resp) throws OptimusException{
		
		//获取参数
		String pkNotice = req.getParameter("pkNotice");
		
		//封装数据
		resp.addAttr("pkNotice", pkNotice);
		
		//进入维护页面
		resp.addPage("/page/common/message_edit.jsp");
	}
	
	/**维护页面数据的回显
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("show")
	public void showDef(OptimusRequest req, OptimusResponse resp) throws OptimusException{
		
		//获取参数
		String pkNotice = req.getParameter("pkNotice");
		
		//通过主键获取消息
		SmNoticeBO smNoticeBO = messageService.getDisplayDef(pkNotice);
		
		/*//岗位资源树回显的设置
		String s1=smNoticeBO.getSendTo().substring(1).toString();
		smNoticeBO.setSendTo(s1);*/
		
		//封装数据并返回前台
		resp.addForm("formpanel_edit",smNoticeBO);
	}
	
	
	
	
	

}
