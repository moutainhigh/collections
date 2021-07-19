package com.gwssi.application.log.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.application.log.service.LogService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.controller.BaseController;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

@Controller
@RequestMapping("/log")
public class LogController extends BaseController{
	@Autowired
	private LogService logService;
	
	/**操作日志页面的跳转
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("operationLog")
	public void getOperationLog(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//进入操作日志页面
		resp.addPage("/page/log/operation_log.jsp");
	}
	
	
	/**接口日志页面的跳转
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("interfaceLog")
	public void getInterfaceLog(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//进入接口日志页面
		resp.addPage("/page/log/interface_log.jsp");
	}
	
	/**业务流程页面的跳转
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("flowLog")
	public void getFlowLog(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//进入业务流程日志页面
		resp.addPage("/page/log/operationflow_log.jsp");
	}
	
	/**操作日志查询
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("query")
	public void queryOperationLog(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//获取查询条件
		Map params = req.getForm("formpanel");
		
		//获取查询结果集
		List list = logService.queryOperationLog(params);
		
		//封装数据并返回前台
		resp.addGrid("gridpanel", list);
		
	}
	
	/**将全部系统名称显示在下拉列表中
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("requestSystem")
	public void queryRequestSystem(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		//获取当前全部系统名称
		List systemList = logService.querySystemList();
		
		//封装数据并返回前台
		resp.addTree("reqPkSys", systemList);
	}
	
	/**将全部系统名称显示在下拉列表中
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("targetSystem")
	public void queryTargetSystem(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		//获取当前全部系统名称
		List systemList = logService.querySystemList();
		
		//封装数据并返回前台
		resp.addTree("resPkSys", systemList);
	}
	
	/**业务流程日志查询
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("queryFlow")
	public void queryFlow(OptimusRequest req,OptimusResponse resp) throws OptimusException{
		
		//获取formpanel中的数据
		Map params = req.getForm("formpanel");
		
		//通过条件查询
		List list = logService.queryFlowLog(params);
		
		//封装数据并返回前台
		resp.addGrid("gridpanel", list);
	}
	
	/**接口日志查询
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("queryInterface")
	public void queryInterface(OptimusRequest req,OptimusResponse resp) throws OptimusException{
		
		//获取formpanel中的数据
		Map params = req.getForm("formpanel");
		
		//通过条件查询
		List list = logService.queryInterfaceLog(params);
		
		//封装数据并返回前台
		resp.addGrid("gridpanel", list);
	}
	
	@RequestMapping("logTest")
	public void logTest(OptimusRequest req, OptimusResponse resp){
		
	}
}
