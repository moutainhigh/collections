package com.ly.vuecontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ly.dao.impl.SettingDao;
import com.ly.pojo.Setting;

@RestController
@RequestMapping("pro")
public class SettingController {

	@Autowired
	private SettingDao settingDao;
	
	
	
	@RequestMapping("getBuySettingList")
	public Map getSetting(){
		Map ret = new HashMap();
		List<Setting> list = settingDao.find("from Setting t where t.type=1");
		ret.put("list", list);
		return ret;
	}
	
	@RequestMapping("getSellSettingList")
	public Map getSellSetting(){
		Map ret = new HashMap();
		List<Setting> list = settingDao.find("from Setting t where t.type=2");
		ret.put("list", list);
		return ret;
	}
	
	
	
	@RequestMapping("updateSetting")
	public Map updateSellSetting(String percent){
		Map ret = new HashMap();
		int i = percent.lastIndexOf(",");
		int count =0;
		if(percent.contains(",")){
			for (int j = 0; j < percent.split(",").length; j++) {
				count++;
			}
		}
		
		if(i==-1||count==3){
			ret.put("msg","非法参数");
		}else{
			//Setting buyDefault = settingDao.find("from Setting t where t.isdefault  = 1 and t.type=1").get(0);
			//Setting sellDefault = settingDao.find("from Setting t where t.isdefault  = 1 and t.type=2").get(0);;
			try {
				
				settingDao.clearDefault();
				
				String code1 = percent.split(",")[0];
				String code2 = percent.split(",")[1];
				
				settingDao.updateBuyDefault(code1);
				settingDao.updateSellDefault(code2);
				
				ret.put("msg","保存成功");
			} catch (Exception e) {
				ret.put("msg","保存失败");
				e.printStackTrace();
			}
		}
		
		return ret;
	}
}
