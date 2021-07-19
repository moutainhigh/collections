package com.gwssi.comselect.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.comselect.service.HouseCodeService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

@Controller
@RequestMapping("/houseCode")
public class HouseCodeControoler {
	@Resource
	private HouseCodeService houseCodeService;

	/**
	 * 区域查询
	 * 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/getRegCode")
	public void getRegCode(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		resp.addTree("region",
				houseCodeService.getRegCode(null, req.getHttpRequest()));
	}

	/**
	 * 街道查询
	 * 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/getStrCode")
	public void getStrCode(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		HttpServletRequest request = req.getHttpRequest();
		String param = request.getParameter("regioncode");
		resp.addTree("region", houseCodeService.getStrCode(param, request));
	}

	/**
	 * 查询楼栋信息
	 * 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/getBuildingInfo")
	public void getBuildingInfo(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		HttpServletRequest request = req.getHttpRequest();
		Map<String, String> form = req.getForm("queryHouseCodePanel");
		List<Map> list = houseCodeService.getBuildingInfo(form, request);
		resp.addGrid("queryHouseCodeGrid", list);
	}

	/**
	 * 根据楼栋编号查询房屋信息
	 * 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/getHouseInfo")
	public void getHouseInfo(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		HttpServletRequest request = req.getHttpRequest();
		String param = request.getParameter("buildingCode");
		List<Map> list = houseCodeService.getHouseInfo(param, request);
		resp.addGrid("queryHouseInfoGrid", list);
	}

	/**
	 * 二级菜单中根据form表单查询房屋信息
	 * 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping("/getHouseInfoByForm")
	public void getHouseInfoByForm(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		Map<String, String> form = req.getForm("queryHouseInfoPanel");
		List<Map> list = houseCodeService.getHouseInfoByForm(form,
				req.getHttpRequest());
		resp.addGrid("queryHouseInfoGrid", list);// ("region",houseCodeService.getStrCode(param,request));
	}
}
