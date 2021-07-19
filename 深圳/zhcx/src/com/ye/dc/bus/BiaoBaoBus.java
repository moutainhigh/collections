package com.ye.dc.bus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ye.dc.dao.BaoBiaoDao;
import com.ye.dc.dao.BiaoBao_Asy_LogDao;
import com.ye.dc.dao.DeptDao;
import com.ye.dc.pojo.BaoBiaoUser;
import com.ye.dc.pojo.BaoBiao_Asy_Log;
import com.ye.dc.pojo.Dept;
import com.ye.dc.pojo.Role;

@Component
public class BiaoBaoBus {
	@Autowired
	private DeptDao deptDao;
	
	
	@Autowired
	private BaoBiaoDao baoBiaoDao;

	@Autowired
	private BiaoBao_Asy_LogDao baoBiao_Asy_LogDao;

	public void savetTOBaoBiaoFormLog() {

		List<BaoBiao_Asy_Log> list = baoBiao_Asy_LogDao.getRecordList();

		Map deptMap = null;
		Map userMap = null;

		BaoBiaoUser baoBiaoUser = null;


		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				BaoBiao_Asy_Log log = list.get(i);

				String deptNameFrom = log.getDeptname().trim();
				String userIdFrom = log.getUserid().trim();
				String userNameFrom = log.getName().trim();

				deptMap = new HashMap();
				deptMap.put("deptname", deptNameFrom);

				Dept dept = deptDao.getDeptCodeByName(deptMap);

				if (dept != null) {
					
					userMap  = new HashMap(); 
					userMap.put("yhid_pk", userIdFrom);
					
					baoBiaoUser = baoBiaoDao.getUserIsExit(userMap);
					
					if(baoBiaoUser!=null) {
					
						baoBiaoUser.setDepartment_code(dept.getCode());
						
						baoBiaoUser.setDepartment_parent_code(dept.getParentCode());
						
						baoBiaoDao.save(baoBiaoUser);
						
						
					}else {
						
						baoBiaoUser = new BaoBiaoUser();
						
						baoBiaoUser.setYhid_pk(userIdFrom);
						baoBiaoUser.setYhzh(userIdFrom);
						
						baoBiaoUser.setYhxm(userNameFrom);
						baoBiaoUser.setDepartment_code(dept.getCode());
						baoBiaoUser.setDepartment_parent_code(dept.getParentCode());
						
						baoBiaoDao.save(baoBiaoUser);
					}
					log.setIsAdd("1");
					baoBiao_Asy_LogDao.save(log);
				}
			}
		}
	}
}
