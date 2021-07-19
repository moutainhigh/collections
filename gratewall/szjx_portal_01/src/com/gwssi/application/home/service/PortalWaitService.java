package com.gwssi.application.home.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gwssi.application.common.AppConstants;
import com.gwssi.application.model.SmScheduleBO;
import com.gwssi.application.model.SmSysIntegrationBO;
import com.gwssi.application.model.SmTodoBO;
import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;


@Service
public class PortalWaitService extends BaseService {

	private static Logger logger = Logger.getLogger(PortalWaitService.class); // 日志

	/**
	 * 
	 * @param pages 当前页数
	 * @param pagesize 每页显示数量
	 * @param userid 查询条件
	 * @return
	 * @throws OptimusException
	 */
	public Map dopagesDate(int pages, int pagesize, String userid) throws OptimusException {
		int listbegin = 0;
		int listend = 0;
		int waitNo=0;//代办总数
		int pagescount = 0;
		PageResult pageResult = null;
		// 总记录数
		long recordCount = 0;
		long timeuseing=0;
		try {
			IPersistenceDAO dao = getPersistenceDAO();
			StringBuffer sql2 = new StringBuffer();
			//sql2.append("select count(*)  as countNum from SM_TODO t ");
			sql2.append("select count(*)  as countNum from SM_TODO t  where INSTR(upper(T.TODO_USER),?)>0 ");
			List allcountlist = new ArrayList();
			allcountlist.add(userid.toUpperCase());
			
			int count = dao.queryForInt(sql2.toString(), allcountlist);

			recordCount = count;
			//System.out.println("总记录数："+recordCount );
			if (pageResult == null) {
				// 总页数=总记录数/页大小
				pageResult = new PageResult(recordCount, pagesize);
				pageResult.setPageNo(pages);
			}
		
			
			
			
			//System.out.println("pagesize:"+pagesize);
			
			pagescount=pageResult.getTotalPageCount();
			if(pages>pageResult.getTotalPageCount()){
				pages=pageResult.getTotalPageCount();
			}
			
			
			if (pages < 1) {
				pages = 1;// 如果分页变量小于１,则将分页变量设为１
			}
			
			
			listend=pages*pagesize;
			listbegin=(pages-1)*pagesize;
			
			
			
			int i = 0;
			List items = new ArrayList();
			
			// 编写查询sql
			StringBuffer sql = new StringBuffer();
			sql.append(" select * from (  select t_.*, rownum as rownum_ from (");
			
			
			sql.append("select a.* , ");
			sql.append("(select sm.system_name from sm_sys_integration sm where sm.effective_marker='Y' and sm.system_type='SYS' AND SM.SYSTEM_CODE=  a.system_code) AS SYS_NAME");
			sql.append(" from SM_TODO a where  INSTR(upper(TODO_USER),?)>0 ");
			
			// 封装参数
			List paramList = new ArrayList();
			paramList.add(userid.toUpperCase());

			// 封装结果集
			
			sql.append("  ) t_ where rownum <="+listend+"  )  where rownum_ > "+listbegin);
			List<Map> smtolist = dao.queryForList(sql.toString(), paramList);
            
			
			StringBuffer sql3 = new StringBuffer();

			
			sql3.append("select sum(t.todo_num)  from sm_todo t ");
			sql3.append("  where INSTR(upper(TODO_USER),?)>0 ");
			waitNo = dao.queryForInt(sql3.toString(), paramList);
    
			
	
			
		
		pageResult.setQuerylist(smtolist);
		} catch (Exception e) {
			System.out.println("getMessage: " + e.getMessage());
			e.printStackTrace();
			throw new OptimusException("获取代办事项出错!");
		}
		// 显示数据部分
		int recordbegin = (pages - 1) * pagesize;// 起始记录
		int recordend = 0;
	
		// 最后一页记录显示处理
		if (pages == pagescount && pagescount % pagesize != 0) {
			recordend = (int) (recordbegin + (pagescount % pagesize));
		}

		Map map = new HashMap();
		map.put("listend", listend);
		map.put("listbegin", listbegin);
		map.put("data", pageResult);
		map.put("recordCount", recordCount);
		map.put("useingtime", timeuseing);
		map.put("waitNo",waitNo);
		//System.out.println("代办数量："+waitNo);
		return map;
	}
	
	/*
	 * 获取所有的代办事项
	 */
	public Map dogetAllWait(String userId) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql2 = new StringBuffer();
		sql2.append("select (select sysi.system_code from sm_sys_integration sysi  where sysi.pk_sys_integration =sm.parent_code) as parent_code ,sm.pk_sys_integration,t.* from SM_TODO t,sm_sys_integration sm where t.system_code =sm.system_code ");
		sql2.append("and INSTR(upper(TODO_USER),?)>0  AND SM.PARENT_CODE IS NOT NULL AND T.TODO_NUM>0");
		List allcountlist = new ArrayList();
		allcountlist.add(userId.toUpperCase());
		
		
		List<Map> smtolist = dao.queryForList(sql2.toString(), allcountlist);
		
	
		StringBuffer sql1 = new StringBuffer();
		sql1.append("select (select sysi.system_code" +
"          from sm_sys_integration sysi" + 
"         where sysi.pk_sys_integration = sm.parent_code) as parent_code, sm.pk_sys_integration," + 
"       t.*" + 
"  from SM_TODO t, SM_SYS_OTHER_INTEGRATION sm" + 
" where t.system_code = sm.system_code" + 
"   and INSTR(upper(TODO_USER), ?) > 0 AND SM.PARENT_CODE IS NOT NULL AND T.TODO_NUM>0");
		
		
		List<Map> smtolist1 = dao.queryForList(sql1.toString(), allcountlist);
		smtolist.addAll(smtolist1);
		Map map = new HashMap();
		map.put("waitlist", smtolist);
		return map;
	}
}
