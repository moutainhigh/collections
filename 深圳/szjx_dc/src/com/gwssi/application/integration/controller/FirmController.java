package com.gwssi.application.integration.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.application.integration.service.FirmService;
import com.gwssi.application.model.SmFirmBO;
import com.gwssi.application.model.SmLikemanBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;
import com.gwssi.optimus.util.DateUtil;



@Controller
@RequestMapping("firmList")
public class FirmController {
	
	@Autowired
	FirmService firmService;
	
	/**通过条件查询厂商信息
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("queryFirm")
	public void queryFirm(OptimusRequest req,OptimusResponse resp) throws OptimusException{
		
		//获取查询条件
		Map params = req.getForm("formpanel"); 
		
		//通过条件查询厂商的信息
		List list = firmService.getFirm(params);
		
		//封装数据并返回前台
		resp.addGrid("gridpanel", list);			
	}
	
	/**保存新增、修改的厂商信息
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("saveFirm")
	public void saveFirm(OptimusRequest req, OptimusResponse resp) throws OptimusException{
		
		String back="fail";
		
		//获取添加、修改页面中填写的数据
		Map<String,String> params = req.getForm("formpanel");
		SmFirmBO smFirmBO = req.getForm("formpanel",SmFirmBO.class);
		
		//获取当前登陆人id
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		String userId = user.getUserId();
		
	/*	//获取当前时间 ,将String 转换为Calendar
		String str= params.get("modifierTime");
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);*/
		
		
		
		
		//PkSmFirm为空，为添加页面；反之，为修改页面
		if(StringUtils.isBlank(smFirmBO.getPkSmFirm())){
			if(firmService.getFirmByName(smFirmBO) ==false){
			//将当前时间填入创建时间
			smFirmBO.setCreaterTime(Calendar.getInstance());
			smFirmBO.setCreaterId(userId);
			
			//将当前时间填入最后修改时间
			smFirmBO.setModifierTime(Calendar.getInstance());
			smFirmBO.setModifierId(userId);
			
			//保存添加的信息
			firmService.saveFirm(smFirmBO);
			
			back="success";
			}
		}
				
		else{
			
			//获取当前登录人
			User u = (User) session.getAttribute(OptimusAuthManager.USER);
			String uname = u.getUserName();
			String uid = u.getUserId();
			smFirmBO.setModifierId(uid);
			smFirmBO.setModifierName(uname);
			smFirmBO.setModifierTime(Calendar.getInstance());
			
			
			//保存修改的信息
			firmService.updateFirm(smFirmBO);
						
			back="success";
					
		}
				
		//封装数据并返回前台
		resp.addAttr("back", back);
		resp.addAttr("pkSmFirm",smFirmBO.getPkSmFirm());
				
	}
	
	/**将选中的厂商信息回显
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("showFirm")
	public void showFirm(OptimusRequest req, OptimusResponse resp) throws OptimusException{
		
		//获取主键
		String pkSmFirm = req.getParameter("pkSmFirm");
		
		//通过主键获取消息
		SmFirmBO smFirmBO = firmService.getDisplayFirm(pkSmFirm);
		
		//封装数据并返回前台
		resp.addForm("formpanel",smFirmBO);
	}
	 
	
	/**删除选中的信息记录
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("deleteFirm")
	public void deleteFirm(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//获取主键
		String pkSmFirm = req.getAttr("pkSmFirm").toString();
		
		//通过主键pkSmFirm删除代码表
		firmService.deleteFirm(pkSmFirm);
		
		//封装数据并返回前台
		resp.addAttr("back", "success");
	}
	
	/**进入添加的页面
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("addFirm")
	public void addFirm(OptimusRequest req, OptimusResponse resp) throws OptimusException{
		
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
		
		//进入添加的页面
		resp.addPage("/page/integeration/firm_edit.jsp");
	}
	
	/**进入修改页面
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("updateFirm")
	public void updateFirm(OptimusRequest req, OptimusResponse resp) throws OptimusException{
		
		//获取参数
		String pkSmFirm = req.getParameter("pkSmFirm");
		
		//传递参数
		resp.addAttr("pkSmFirm", pkSmFirm);
		
		//页面跳转
		resp.addPage("/page/integeration/firm_edit.jsp");
	}
	
	/**进入维护页面
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("checkFirm")
	public void checkFirm(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//获取参数
		String pkSmFirm = req.getParameter("pkSmFirm");
				
		//传递参数
		resp.addAttr("pkSmFirm", pkSmFirm);
		
		//页面跳转
		resp.addPage("/page/integeration/linkman.jsp");
	}
	
	/**通过条件查询联系人信息
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("queryLinkman")
	public void queryLinkman(OptimusRequest req,OptimusResponse resp) throws OptimusException{
		
		//获取参数
		Map params = req.getForm("formpanel"); 
		String pkSmFirm = req.getParameter("pkSmFirm");
		
		//通过条件查询联系人信息
		List list = firmService.getLinkman(params,pkSmFirm);
		
		//封装数据并返回前台
		resp.addGrid("gridpanel", list);			
	}
	
	/**进入添加页面
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("addLinkman")
	public void addLinkman(OptimusRequest req, OptimusResponse resp) throws OptimusException{
		
		//获取当前登录人
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		String username = user.getUserName();
				
		//获取当前日期
		Calendar calendar = Calendar.getInstance();
		String currentDate = DateUtil.toDateStrWithTime(calendar);
		
		//获取pkSmFirm
		String pkSmFirm = req.getParameter("pkSmFirm");
				
		//封装数据
		resp.addAttr("username", username);
		resp.addAttr("currentDate", currentDate);
		resp.addAttr("pkSmFirm", pkSmFirm);
		
		//进入添加的页面
		resp.addPage("/page/integeration/linkman_edit.jsp");
	}
	
	/**保存新增、修改的联系人信息
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("saveLinkman")
	public void saveLinkman(OptimusRequest req, OptimusResponse resp) throws OptimusException{
		
		String back="fail";
		
		//获取添加、修改页面中填写的数据
		Map<String,String> params = req.getForm("formpanel");
		SmLikemanBO smLikemanBO = req.getForm("formpanel",SmLikemanBO.class);
		
		//获取当前登陆人id
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		String userId = user.getUserId();
		
		/*//获取当前时间 ,将String 转换为Calendar
		String str= params.get("modifierTime");
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);*/
		
		
		
		
		
		//PkSmLikeman为空，为添加页面；反之，为修改页面
		if(StringUtils.isBlank(smLikemanBO.getPkSmLikeman())){
			if(firmService.getLinkmanByNameAndPhone(smLikemanBO) == false){
				//将当前时间、登陆人id，填入创建时间、创建人id
				smLikemanBO.setCreaterTime(Calendar.getInstance());
				smLikemanBO.setCreaterId(userId);
			
				//将当前时间、登陆人id 填入最后修改时间、最后修改人id
				smLikemanBO.setModifierTime(Calendar.getInstance());
				smLikemanBO.setModifierId(userId);
				
				//保存添加的信息
				firmService.saveLinkman(smLikemanBO);
			
				back="success";
			}
		}
				
		else{
			
			//获取当前登录人
			User u = (User) session.getAttribute(OptimusAuthManager.USER);
			String uname = u.getUserName();
			String uid = u.getUserId();
			smLikemanBO.setModifierName(uname);
			smLikemanBO.setModifierId(uid);
			smLikemanBO.setModifierTime(Calendar.getInstance());
			
			//保存修改的信息
			firmService.updateLinkman(smLikemanBO);
						
			back="success";
					
		}
				
		//封装数据并返回前台
		resp.addAttr("back", back);
		resp.addAttr("pkSmLikeman",smLikemanBO.getPkSmLikeman());
				
	}
	
	/**删除选中的联系人信息
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("deleteLinkman")
	public void deleteLinkman(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//获取主键
		String pkSmLikeman = req.getAttr("pkSmLikeman").toString();
		
		//通过主键pkSmFirm删除代码表
		firmService.deleteLinkman(pkSmLikeman);
		
		//封装数据并返回前台
		resp.addAttr("back", "success");
	}
	
	/**进入修改页面
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("updateLinkman")
	public void updateLinkman(OptimusRequest req, OptimusResponse resp) throws OptimusException{
		
		//获取参数
		String pkSmLikeman = req.getParameter("pkSmLikeman");
		
		//传递参数
		resp.addAttr("pkSmLikeman", pkSmLikeman);
		
		//页面跳转
		resp.addPage("/page/integeration/linkman_edit.jsp");
	}
	
	/**将选中的联系人信息回显
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("showLinkman")
	public void showLinkman(OptimusRequest req, OptimusResponse resp) throws OptimusException{
		
		//获取参数
		String pkSmLikeman = req.getParameter("pkSmLikeman");
		
		//通过主键获取消息
		SmLikemanBO smLikemanBO = firmService.getDisplayLinkman(pkSmLikeman);
		
		//封装数据并返回前台
		resp.addForm("formpanel",smLikemanBO);
	}
	
	/**获取所有厂商信息
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("getFrim")
	@ResponseBody
	public void getFrim(OptimusRequest req, OptimusResponse resp) throws OptimusException{
		List list = firmService.getAllFirm();
		resp.addTree("firmlist", list);
	}
	
	/**根据厂商获取联系人
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("getLinkman")
	@ResponseBody
	public void getLinkman(OptimusRequest req,OptimusResponse resp) throws OptimusException{
		String pkSmFirm = req.getParameter("pkSmFirm");
		List list = firmService.getAllLinkman(pkSmFirm);
		resp.addTree("linklist", list);			
	}
}
