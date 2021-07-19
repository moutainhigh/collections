package com.gwssi.application.common.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.application.common.AppConstants;
import com.gwssi.application.common.service.DictionaryDetailsService;
import com.gwssi.application.log.annotation.LogBOAnnotation;
import com.gwssi.application.model.SmCodeTableManagerBO;
import com.gwssi.application.model.SmDmEffectiveBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;
import com.gwssi.optimus.util.DateUtil;




@Controller
@RequestMapping("/dictionaryDetails")
public class DictionaryDetailsController {
	
	@Autowired
    private DictionaryDetailsService dictionaryDetailsService;
	
	/**查询代码表的结构
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("queryStruct")
	@LogBOAnnotation(systemCode="SM",functionCode="SM0403",operationType="查询代码表结构",operationCode=AppConstants.LOG_OPERATE_QUERY)
	public void queryStruct(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//获取要查询的表名
		String codeTableEnName = req.getParameter("codeTableEnName");
		
		//通过表名查询该表的结构
		List list = dictionaryDetailsService.queryStructList(codeTableEnName);
		
		//封装数据并返回前台
		resp.addGrid("gridStruct", list);
	}
	
	/**查询代码表中的所有记录
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("queryValue")
	@LogBOAnnotation(systemCode="SM",functionCode="SM0403",operationType="查询代码表记录",operationCode=AppConstants.LOG_OPERATE_QUERY)
	public void queryValue(OptimusRequest req,OptimusResponse resp) throws OptimusException{
		
		//获取formpanel中的查询条件
		Map params = req.getForm("formpanel"); 
		
		//获取查询的表名
		String codeTableEnName = req.getParameter("codeTableEnName");
		
		//判断代码表是否存在
		Boolean lit = dictionaryDetailsService.queryTable(codeTableEnName);
		
		if(lit.equals(false)){
			
		}
		else{
		
			//通过表名、查询条件查询代码表信息
			List list = dictionaryDetailsService.queryValue(params,codeTableEnName);
		
			//封装数据并返回前台
			resp.addGrid("gridValue", list);	
			}
	}
	

	/**保存新添、修改的代码值信息
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("save")
	@LogBOAnnotation(systemCode="SM",functionCode="SM0403",operationType="保存代码表信息",operationCode=AppConstants.LOG_OPERATE_UPDATE)
	public void saveValue(OptimusRequest req,OptimusResponse resp) throws OptimusException{
		String back="fail";
		
		//获取表名
		String codeTableEnName = req.getParameter("codeTableEnName");
		
		//update为false是添加的保存，true为修改的保存
		String update = req.getParameter("update");
		
		//获取当前登陆人id
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		String userId = user.getUserId();
		String username = user.getUserName();
		
		//获取添加页面中所有填写的数据
		Map map =  req.getForm("formpanel_edit");
		String code = (String) map.get("code");
		String name = (String) map.get("name");
		String nameShort = (String) map.get("nameShort");
		String fCode = (String) map.get("fCode");
		String chooseMark = (String) map.get("chooseMark");
		String createrName = (String) map.get("createrName");
		
		
		
		
		//把获取的数据放入list中
		List list = new ArrayList();
		list.add(code);
		list.add(name);
		list.add(nameShort);
		list.add(fCode);
		list.add(chooseMark);
		list.add(createrName);
		list.add(userId);
		list.add(Calendar.getInstance());
		list.add(username);
		
		
		
		//通过代码值、表名查询是否已经添加该代码值
		Boolean oldValue = dictionaryDetailsService.queryCodeTableByCode(code,codeTableEnName);
		
		//判断update的值，false为添加，true为修改
		if(StringUtils.isNotEmpty(update)&&"false".equals(update)){
			
			//oldValue为false,无该代码值的记录，可以添加
			if(oldValue==false){
				
				//保存添加的信息
				dictionaryDetailsService.saveValue(list,codeTableEnName);
				back="success";
			}
		}
		else{
				//获取当前登陆人
				/*User u = (User) session.getAttribute(OptimusAuthManager.USER);
				String username = user.getUserName();
				list.add(username);*/
				
				//保存修改的信息
				dictionaryDetailsService.updateValue(list,codeTableEnName);
				back="success";
			
		}
		
		//封装数据并返回前台
		resp.addAttr("back", back);
		
	}
	
	
	/**将代码值的信息回显在页面上
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("show")
	@LogBOAnnotation(systemCode="SM",functionCode="SM0403",operationType="显示代码表信息",operationCode=AppConstants.LOG_OPERATE_QUERY)
	public void showValue(OptimusRequest req, OptimusResponse resp) throws OptimusException{
		
		//获取前台的effectiveCode、codeTableEnName的值
		String eCode = req.getParameter("code");
		String codeTableEnName = req.getParameter("codeTableEnName");
		
		//通过代码值、表名查询代码表信息
		List<Map> list = dictionaryDetailsService.getDisplayValue(codeTableEnName,eCode);
		String code = (String) list.get(0).get("code");
		String name = (String) list.get(0).get("name");
		String nameShort = (String) list.get(0).get("nameShort");
		String fCode = (String) list.get(0).get("fCode");
		String chooseMark = (String) list.get(0).get("chooseMark");
		String createrName = (String) list.get(0).get("createrName");
		String modifierName = (String) list.get(0).get("modifierName");
		// list.get(0).get("createrTime");
		//System.out.println((String)list.get(0).get("createrTime"));
		//将查询的信息返回到页面中
		resp.addAttr("code", code);
		resp.addAttr("name", name);
		resp.addAttr("nameShort", nameShort);
		resp.addAttr("fCode", fCode);
		resp.addAttr("chooseMark", chooseMark);
		resp.addAttr("createrName", createrName);
		resp.addAttr("createrTime", list.get(0).get("createrTime"));
		resp.addAttr("modifierName", modifierName);
		resp.addAttr("modifierTime", list.get(0).get("modifierTime"));
		
	}
	
	/**删除选中的信息记录
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("deleteValue")
	@LogBOAnnotation(systemCode="SM",functionCode="SM0403",operationType="删除代码表信息",operationCode=AppConstants.LOG_OPERATE_DELETE)
	public void deleteValue(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//获取表名、代码值
		String codeTableEnName = req.getParameter("codeTableEnName");
		String code = req.getParameter("code");
		
		//通过主键删除代码表
		dictionaryDetailsService.deleteValue(code,codeTableEnName);
			
		//将success返回到页面
		resp.addAttr("back", "success");
	}
	
	/**进入修改的页面
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("update")
	@LogBOAnnotation(systemCode="SM",functionCode="SM0403",operationType="修改代码表信息",operationCode=AppConstants.LOG_OPERATE_UPDATE)
	public void updateDef(OptimusRequest req, OptimusResponse resp) throws OptimusException{
		
		//获取参数
		String codeTableEnName = req.getParameter("codeTableEnName");
		String code = req.getParameter("code");
		String update = req.getParameter("update");
		
		//封装数据
		resp.addAttr("code", code);
		resp.addAttr("codeTableEnName", codeTableEnName);
		resp.addAttr("update", update);
		
		//进入修改页面
		resp.addPage("/page/common/dictionary_detailsedit.jsp");
		
		
	}
	
	/**进入添加的页面
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("add")
	@LogBOAnnotation(systemCode="SM",functionCode="SM0403",operationType="添加代码表信息",operationCode=AppConstants.LOG_OPERATE_ADD)
	public void addDef(OptimusRequest req, OptimusResponse resp) throws OptimusException{
		
		//获取参数
		String codeTableEnName = req.getParameter("codeTableEnName");
		String update = req.getParameter("update");
		
		//封装数据
		resp.addAttr("update", update);
		resp.addAttr("codeTableEnName", codeTableEnName);
		
		
		
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		String username = user.getUserName();
		Calendar calendar = Calendar.getInstance();
		String currentDate = DateUtil.toDateStrWithTime(calendar);
		resp.addAttr("username", username);
		resp.addAttr("currentDate", currentDate);
		
		
		//进入添加页面
		resp.addPage("/page/common/dictionary_detailsedit.jsp");
	}
	
}
