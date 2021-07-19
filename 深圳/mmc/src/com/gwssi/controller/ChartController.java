package com.gwssi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.dao.DCUploadRecordDao;

@Controller
public class ChartController {

	@Autowired
	private DCUploadRecordDao dcdao;

	@RequestMapping("/getData01")
	@ResponseBody
	public Map getData01() {
		Map map = new HashMap();
		map.put("remark", "1");
		// 企业管辖区域为空数量
		List list = dcdao.getRecord(map);
		//System.out.println(list);
		map.put("remark", list);
		return map;
	}

	@RequestMapping("/getData02")
	@ResponseBody
	public Map getData02() {
		Map map = new HashMap();
		map.put("remark", "2");
		// 企业管辖区域为空数量
		List list = dcdao.getRecord(map);
		//System.out.println(list);
		map.put("remark", list);
		return map;
	}

	@RequestMapping("/getData04")
	@ResponseBody
	public Map getData04() {
		Map map = new HashMap();
		map.put("remark", "4");
		// 企业管辖区域为空数量
		List list = dcdao.getRecord(map);
		//System.out.println(list);
		map.put("remark", list);
		return map;
	}

	@RequestMapping("/getData05")
	@ResponseBody
	public Map getData05() {
		Map map = new HashMap();
		map.put("remark", "5");
		// 企业管辖区域为空数量
		List list = dcdao.getRecord(map);
		//System.out.println(list);
		map.put("remark", list);
		return map;
	}

	@RequestMapping("/getData07")
	@ResponseBody
	public Map getData07() {
		Map map = new HashMap();
		map.put("remark", "7");
		// 企业管辖区域为空数量
		List list = dcdao.getRecord(map);
		//System.out.println(list);
		map.put("remark", list);
		return map;
	}

	@RequestMapping("/getData08")
	@ResponseBody
	public Map getData08() {
		Map map = new HashMap();
		map.put("remark", "8");
		// 企业管辖区域为空数量
		List list = dcdao.getRecord(map);
		//System.out.println(list);
		map.put("remark", list);
		return map;
	}

	@RequestMapping("/getData09")
	@ResponseBody
	public Map getData09() {
		Map map = new HashMap();
		map.put("remark", "9");
		// 企业管辖区域为空数量
		List list = dcdao.getRecord(map);
	//	System.out.println(list);
		map.put("remark", list);
		return map;
	}

	@RequestMapping("/getData10")
	@ResponseBody
	public Map getData10() {
		Map map = new HashMap();
		map.put("remark", "10");
		// 企业管辖区域为空数量
		List list = dcdao.getRecord(map);
		//System.out.println(list);
		map.put("remark", list);
		return map;
	}

	// 11 12 同一个
	@RequestMapping("/getData11")
	@ResponseBody
	public Map getData11() {
		Map map = new HashMap();
		map.put("remark", "11");
		// 企业管辖区域为空数量
		List list = dcdao.getRecord(map);
		//System.out.println(list);
		map.put("remark", list);
		return map;
	}

	@RequestMapping("/getData12")
	@ResponseBody
	public Map getData12() {
		Map map = new HashMap();
		map.put("remark", "12");
		// 企业管辖区域为空数量
		List list = dcdao.getRecord(map);
		//System.out.println(list);
		map.put("remark", list);
		return map;
	}

	@RequestMapping("/getData13")
	@ResponseBody
	public Map getData13() {
		Map map = new HashMap();
		map.put("remark", "13");
		// 企业管辖区域为空数量
		List list = dcdao.getRecord(map);
		//System.out.println(list);
		map.put("remark", list);
		return map;
	}

	@RequestMapping("/getData14")
	@ResponseBody
	public Map getData14() {
		Map map = new HashMap();
		map.put("remark", "14");
		// 企业管辖区域为空数量
		List list = dcdao.getRecord(map);
		//System.out.println(list);
		map.put("remark", list);
		return map;
	}

	@RequestMapping("/getData15")
	@ResponseBody
	public Map getData15() {
		Map map = new HashMap();
		map.put("remark", "15");
		// 企业管辖区域为空数量
		List list = dcdao.getRecord(map);
		//System.out.println(list);
		map.put("remark", list);
		return map;
	}

	@RequestMapping("/getData16")
	@ResponseBody
	public Map getData16() {
		Map map = new HashMap();
		map.put("remark", "16");
		// 企业管辖区域为空数量
		List list = dcdao.getRecord(map);
		System.out.println(list);
		map.put("remark", list);
		return map;
	}

	@RequestMapping("/getData17")
	@ResponseBody
	public Map getData17() {
		Map map = new HashMap();
		map.put("remark", "17");
		// 企业管辖区域为空数量
		List list = dcdao.getRecord(map);
		//System.out.println(list);
		map.put("remark", list);
		return map;
	}

	@RequestMapping("/getData18")
	@ResponseBody
	public Map getData18() {
		Map map = new HashMap();
		map.put("remark", "18");
		// 企业管辖区域为空数量
		List list = dcdao.getRecord(map);
		//System.out.println(list);
		map.put("remark", list);
		return map;
	}

	@RequestMapping("/getData19")
	@ResponseBody
	public Map getData19() {
		Map map = new HashMap();
		map.put("remark", "19");
		// 企业管辖区域为空数量
		List list = dcdao.getRecord(map);
		//System.out.println(list);
		map.put("remark", list);
		return map;
	}

}
