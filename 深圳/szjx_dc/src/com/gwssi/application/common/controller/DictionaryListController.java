package com.gwssi.application.common.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.application.common.service.DictionaryService;
import com.gwssi.application.model.SmCodeTableManagerBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.controller.BaseController;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;
import com.gwssi.optimus.util.DateUtil;


@Controller
@RequestMapping("/dictionaryList")
public class DictionaryListController extends BaseController {

	@Autowired
    private DictionaryService dictionaryService;
	
	/**进入代码集管理
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("dictionarypage")
	public void getDictionary(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//进入代码集管理页面
		resp.addPage("/page/common/dictionary_list.jsp");
	}
	
	/**将全部系统名称显示在下拉列表中
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("systemList")
	public void querySystemList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		//获取当前全部系统名称
		List systemList = dictionaryService.querySystemList();
		
		//封装数据并返回前台
		resp.addTree("pkSysIntegration", systemList);
	}
	
	/**查询代码表信息
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("query")
	public void queryDictionaryDef(OptimusRequest req,OptimusResponse resp) throws OptimusException{
		//获取formpanel中填写的数据
		Map params = req.getForm("formpanel"); 
		
		//通过代码表中、英文名，查询代码表的信息
		List list = dictionaryService.getDef(params);
		
		//封装数据并返回前台
		resp.addGrid("gridpanel", list);			
	}
	
	/**保存代码表信息
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("save")
	public void saveDictionryDef(OptimusRequest req,OptimusResponse resp) throws OptimusException{
		String back="fail";
		
		//获取添加、修改页面中填写的数据
		SmCodeTableManagerBO smCodeTableManagerBO=req.getForm("formpanel_edit",SmCodeTableManagerBO.class);
		Map<String,String> params = req.getForm("formpanel_edit");
		
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
				
		
		
		//通过代码表名查询是否已有该记录
		SmCodeTableManagerBO oldCodeTableBO=dictionaryService.queryCodeTableByName(smCodeTableManagerBO.getCodeTableEnName());
		
		//pkCodeTableManager为空，为添加页面；反之，为修改页面
		if(StringUtils.isBlank(smCodeTableManagerBO.getPkCodeTableManager())){
			
			//oldCodeTableBO为null,表示代码表中无该记录，可进行添加
			if(oldCodeTableBO==null){
				
				//将当前时间、登陆人id填入创建时间、创建人id
				smCodeTableManagerBO.setCreaterTime(Calendar.getInstance());
				smCodeTableManagerBO.setCreaterId(userid);
				
				//将当前时间、登陆人id填入最后修改时间、最后修改人id
				smCodeTableManagerBO.setModifierTime(Calendar.getInstance());
				smCodeTableManagerBO.setModifierId(userid);
				
				//保存添加的信息
				dictionaryService.saveDef(smCodeTableManagerBO);
				dictionaryService.createDef(smCodeTableManagerBO);
				back="success";
			}
		}
		else{
				//获取当前登录人
				User user = (User) session.getAttribute(OptimusAuthManager.USER);
				String username = user.getUserName();
				smCodeTableManagerBO.setModifierName(username);
				smCodeTableManagerBO.setModifierId(userid);
				smCodeTableManagerBO.setModifierTime(Calendar.getInstance());
			
				
				//保存修改的信息
				dictionaryService.updateDef(smCodeTableManagerBO);
				back="success";
			
		}
		
		//封装数据并返回前台
		resp.addAttr("back", back);
		resp.addAttr("PkCodeTableManager",smCodeTableManagerBO.getPkCodeTableManager());
		
	}
	
	/**删除选中的信息记录
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("delete")
	public void deleteDictionaryDef(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//获取主键
		String pkCodeTableManager = req.getAttr("pkId").toString();
		
		dictionaryService.dropDef(pkCodeTableManager);
		//通过主键pkCodeTableManager删除代码表
		dictionaryService.deleteDef(pkCodeTableManager);
		
		
		//封装数据并返回前台
		resp.addAttr("back", "success");
	}
	
	/**将选中需修改的代码表信息回显到页面上
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("show")
	public void showDictionaryDef(OptimusRequest req, OptimusResponse resp) throws OptimusException{
		
		//获取前台pkCodeTableManager的值
		String pkCodeTableManager =req.getParameter("pkCodeTableManager");
		
		//通过主键pkCodeTableManager查询该记录的所有值
		SmCodeTableManagerBO smCodeTableManagerBO = dictionaryService.getDisplayDef(pkCodeTableManager);
		
		//封装数据并返回前台
		resp.addForm("formpanel_edit", smCodeTableManagerBO);
		
	}
	
	/**进入修改的页面
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("update")
	public void updateDef(OptimusRequest req, OptimusResponse resp) throws OptimusException{
		
		//获取参数
		String pkCodeTableManager = req.getParameter("pkCodeTableManager");
		
		//封装数据
		resp.addAttr("pkCodeTableManager", pkCodeTableManager);
		
		//进入修改的页面
		resp.addPage("/page/common/dictionary_edit.jsp");
		
		
	}
	
	/**进入详细查看的页面
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("details")
	public void detailsDef(OptimusRequest req, OptimusResponse resp) throws OptimusException{
		
		//获取参数
		String codeTableEnName = req.getParameter("codeTableEnName");
		/*String codeTableChName = req.getParameter("codeTableChName");
		try {
			codeTableChName = new String(codeTableChName.getBytes(),"utf-8");
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		String codeTableChName = dictionaryService.getCodeTableChName(codeTableEnName);
		
		
		
		//封装数据
		resp.addAttr("codeTableEnName", codeTableEnName);
		resp.addAttr("codeTableChName", codeTableChName);
		
		//进入详细查看页面
		resp.addPage("/page/common/dictionary_details.jsp");
	}
	
	/**进入添加的页面
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("add")
	public void addDef(OptimusRequest req, OptimusResponse resp) throws OptimusException{
		
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		String username = user.getUserName();
		Calendar calendar = Calendar.getInstance();
		String currentDate = DateUtil.toDateStrWithTime(calendar);
		resp.addAttr("username", username);
		resp.addAttr("currentDate", currentDate);
		
		//进入添加的页面
		resp.addPage("/page/common/dictionary_edit.jsp");
	}
	
	
}
