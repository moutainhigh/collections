package com.ly.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ly.dao.AccountDao;
import com.ly.placeholder.DESUtils;
import com.ly.pojo.Account;

//https://www.cnblogs.com/fanshuyao/p/7168471.html
//https://www.cnblogs.com/shuaifing/p/8119664.html
@Controller
@RequestMapping("services")
public class GuDongController {
	@Autowired
	private AccountDao accountDao;

	@RequestMapping("/gudongList")
	@ResponseBody
	public Map getGudongList() {
		List list = accountDao.getGudongList();
		Map maps = new HashMap();
		if (list != null && list.size() > 0) {
			maps.put("list", list);
		} else {
			maps.put("list", "");
		}
		return maps;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/updateGudongInfo")
	@ResponseBody
	public Map updates(String account,String pwd) {
		Map maps = new HashMap();
		Map param = new HashMap();
		param.put("account", account);
		Account acc = accountDao.findGudong(param);
		if(pwd!=null&&!pwd.equals("")){
			acc.setTokenId(DESUtils.getEncryptString(pwd));
			maps.put("msg", "6");
			accountDao.save(acc);
		}else{
			maps.put("msg", "7");
		}
		return maps;
	}
	
}
