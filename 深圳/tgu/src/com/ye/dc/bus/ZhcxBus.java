package com.ye.dc.bus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.relation.Role;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ye.dc.dao.DeptDao;
import com.ye.dc.dao.UserDao;
import com.ye.dc.dao.WCM_Asy_LogDao;
import com.ye.dc.pojo.Dept;
import com.ye.dc.pojo.User;
import com.ye.dc.pojo.WCM_Asy_Log;

@Component
public class ZhcxBus {
	private Logger debug = Logger.getLogger(ZhcxBus.class);
	
	@Autowired
	private DeptDao deptDao;

	@Autowired
	private WCM_Asy_LogDao zhcx_Asy_LogDao;

	public void saveToDbFromLog() {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		
		List<WCM_Asy_Log> list = zhcx_Asy_LogDao.getRecordList();
		
		Map deptMap  = null;
		Map userMap = null;
		
		User user = null; 
		
		
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				WCM_Asy_Log log = list.get(i);
				
				String deptNameFrom = log.getDeptname().trim();
				String userIdFrom = log.getUserid().trim();
				String userNameFrom = log.getName().trim();
				
				
				deptMap = new HashMap();
				deptMap.put("deptname", deptNameFrom);

				Dept dept = deptDao.getDeptCodeByName(deptMap);


				
				debug.debug("当前跑到"+(i+1)+"条，还有" +  (list.size()-1-i) + "条");
				zhcx_Asy_LogDao.save(log);
			}

		}
		long endTime = System.currentTimeMillis(); // 获取结束时间
		long time = endTime - startTime;
		double hour = time / 1000 / 60.0 / 60.0;
		debug.debug("系统运行完成,程序运行时间：" + (time) + "毫秒 , 共 " + (time) / 1000 / 60.0 + "分,共" + hour + "小时"); // 输出程序运行时间
		debug.debug("同步完成");
	}

}
