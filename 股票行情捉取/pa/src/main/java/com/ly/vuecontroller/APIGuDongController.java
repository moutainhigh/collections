package com.ly.vuecontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ly.dao.AccountDao;
import com.ly.placeholder.DESUtils;
import com.ly.pojo.Account;

@RestController
@RequestMapping("pro")
public class APIGuDongController {

	@Autowired
	private AccountDao accountDao;
	
	@RequestMapping("/gudongList")
	public Map<String,List<Account>> getGudongList() {
		List<Account> list = accountDao.getGudongList();
		Map<String,List<Account>> maps = new HashMap<String,List<Account>>();
		if (list != null && list.size() > 0) {
			maps.put("list", list);
		} else {
			maps.put("list", null);
		}
		return maps;
	}
	
	
	@RequestMapping(value = "/updateGudongInfo", method = RequestMethod.POST)
	public Map<String, String> updates(String account, String pwd) {
		Map<String, String> maps = new HashMap<String, String>();
		Map<String, String> param = new HashMap<String, String>();
		param.put("account", account);
		Account acc = accountDao.findGudong(param);
		if (pwd != null && !pwd.equals("")) {
			acc.setTokenId(DESUtils.getEncryptString(pwd));
			maps.put("msg", "股东信息更新成功");
			accountDao.save(acc);
		} else {
			maps.put("msg", "股东信息更新失败");
		}
		return maps;
	}
}
