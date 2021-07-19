package com.gwssi.comselect.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.comselect.service.YCSelectService;
import com.gwssi.comselect.service.ZZQueryService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

@Controller
@RequestMapping("/zzselect")
public class ZZQueryController {
	@Resource
	private ZZQueryService zzQueryService;
	
	/**
	 * 证照查询
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/ZZQuery")
	public void ZZQuery(OptimusRequest req, OptimusResponse resp) throws OptimusException {
			Map<String, String> form = req.getForm("CFQueryListPanel");
			List<Map> lstParams= zzQueryService.ZZQuery(form,req.getHttpRequest());
			//System.out.println(lstParams.toString());
			resp.addGrid("CFQueryListGrid",lstParams);
		}
	/**
	 * 根据证照类别进行证照查询 ：建设工程竣工规划验收合格证
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/zzJgcert")
	public void zzJgcert(OptimusRequest req, OptimusResponse resp) throws OptimusException {
			String id = req.getParameter("id");
			resp.addForm("jsgcjgghyshgz",zzQueryService.zzJgcert(id));
		}
	
	/**
	 * 根据证照类别进行证照查询 ：建设工程验收合格证
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/zzYscert")
	public void zzYscert(OptimusRequest req, OptimusResponse resp) throws OptimusException {
			String id = req.getParameter("id");
			//System.out.println(lstParams.toString());
			resp.addForm("jsgcyshgz",zzQueryService.zzYscert(id));
		}
}
