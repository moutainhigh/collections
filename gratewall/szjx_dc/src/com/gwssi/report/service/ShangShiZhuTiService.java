package com.gwssi.report.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.report.balance.api.ReportModel;
import com.gwssi.report.balance.api.ReportSource;
import com.gwssi.report.model.TCognosReportBO;
import com.gwssi.report.util.LogOperation;

@Service
public class ShangShiZhuTiService extends BaseService implements ReportSource  {
	private String[] strs=new String[]{"宝安","南山","光明","福田","罗湖","龙岗","坪山","龙华","盐田","大鹏","前海","市民中心"};
	LogOperation logop = new LogOperation();
	@Override
	public ReportModel getReport(TCognosReportBO queryInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, Map> dengjiBanJieInfo(String beginTime,
			String endTime, int i, HttpServletRequest req) {
		IPersistenceDAO dao = this.getPersistenceDAO("dc_dc");
		List list=new ArrayList();
		List<Map> list1=new ArrayList<Map>();
		list.add(beginTime+" 00:00:00");
		list.add(endTime+" 23:59:59");
		Map<String,Map> map=new HashMap();
		String sql=
						"--全流程表1\n" +
						"select r.*,\n" + 
						"       round((case\n" + 
						"               when r.已办结 = 0 then\n" + 
						"                1\n" + 
						"               else\n" + 
						"                r.小计 / r.已办结\n" + 
						"             end),\n" + 
						"             4) * 100 || '%' as 办结率\n" + 
						"  from （select s.*, s.已办结 + s.未办 + s.撤回办结 as 总计, s.未办 as 小计\n" + 
						"  from (select t.单位,\n" + 
						"               sum(case\n" + 
						"                     when (t.app = '6' and t.state = 'WaitSignAccept' and\n" + 
						"                          t.wancheng <= t.shixian) or\n" + 
						"\n" + 
						"                          (t.app <> '6' and\n" + 
						"                          t.state in ('AICMER_InspectAgree',\n" + 
						"                                       'AICMER_AcceptedAndInspectAgree',\n" + 
						"                                       'AICMER_RejectTermination') and\n" + 
						"                          t.wancheng <= t.shixian) then\n" + 
						"                      t.sl\n" + 
						"                     else\n" + 
						"                      0\n" + 
						"                   end) as 已办结,\n" + 
						"               sum(case\n" + 
						"                     when (t.app = '6' and t.state = 'WaitSignAccept' and\n" + 
						"                          t.wancheng > t.shixian) or\n" + 
						"                          (t.app <> '6' and\n" + 
						"                          t.state in ('AICMER_InspectAgree',\n" + 
						"                                       'AICMER_AcceptedAndInspectAgree',\n" + 
						"                                       'AICMER_RejectTermination') and\n" + 
						"                          t.wancheng > t.shixian) or\n" + 
						"                          (t.state = 'AICMER_Accepted' and t.shixian < sysdate) then\n" + 
						"                      t.sl\n" + 
						"                     else\n" + 
						"                      0\n" + 
						"                   end) as 未办,\n" + 
						"               -- sum(case when  t.state='WaitSign' then t.sl else 0 end )as 待签收,\n" + 
						"               -- sum(case when  t.state='WaitSignAccept' then t.sl else 0 end )as 签收通过未核准,\n" + 
						"               sum(case\n" + 
						"                     when t.state = 'AICMER_BackTermination' then\n" + 
						"                      t.sl\n" + 
						"                     else\n" + 
						"                      0\n" + 
						"                   end) as 撤回办结\n" + 
						"          from (select b.biz_state state,\n" + 
						"                       b.app as app,\n" + 
						"                       b.UNIT_NAME 单位,\n" + 
						"                       b.time_limit as shixian,\n" + 
						"                       b.accept_time as wancheng,\n" + 
						"                       count(1) as sl\n" + 
						"                  from dc_dc.WHOLE_PROCESS b\n" + 
						"                 where 1 = 1\n" + 
						"                   and b.submit_time >=\n" + 
						"                       to_date(?, 'yyyy-mm-dd hh24:mi:ss')\n" + 
						"                   and b.submit_time <=\n" + 
						"                       to_date(?, 'yyyy-mm-dd hh24:mi:ss')\n" + 
						"\n" + 
						"                --and  (b.accept_dept_name  like '%科%' or b.accept_dept_name like '%局%')\n" + 
						"                 group by b.biz_state,\n" + 
						"                          b.UNIT_NAME,\n" + 
						"                          b.app,\n" + 
						"                          b.time_limit,\n" + 
						"                          b.accept_time) t\n" + 
						"         group by t.单位) s）r\n" + 
						"\n" + 
						"";
			try {
				list1=dao.queryForList(sql, list);
			} catch (OptimusException e) {
				e.printStackTrace();
			}
			if (list1==null ||list1.size()==0) {
				return null;
			}else {
				for (int j = 0; j < list1.size(); j++) {
					String str=(String) list1.get(j).get("单位");
					for (int j2 = 0; j2 < strs.length; j2++) {
						if (strs[j2].equals(str)) {
							Map map1=new HashMap();
							map1.put("已办结",list1.get(j).get("已办结"));
							map1.put("未办",list1.get(j).get("未办"));
							//map1.put("待签收",list1.get(j).get("待签收"));
							//map1.put("签收通过未核准",list1.get(j).get("签收通过未核准"));
							map1.put("撤回办结",list1.get(j).get("撤回办结"));
							map1.put("总计",list1.get(j).get("总计"));
							map1.put("小计",list1.get(j).get("小计"));
							map1.put("办结率",list1.get(j).get("办结率"));
							map.put(strs[j2],map1);
						}else{
							continue;
						}
					}
				}
				for (int j = 0; j < strs.length; j++) {
					if (map.get(strs[j])==null) {
						Map map1=new HashMap();
						map1.put("已办结","0");
						map1.put("未办","0");
						//map1.put("待签收","0");
						//map1.put("签收通过未核准","0");
						map1.put("撤回办结","0");
						map1.put("总计","0");
						map1.put("小计","0");
						map1.put("办结率","0%");
						map.put(strs[j],map1);
					}else{
						continue;
					}
				}
				
				for (int j = 0; j < strs.length; j++) {
					if (map.get(strs[j]).get("办结率").toString().startsWith(".")) {
						map.get(strs[j]).put("办结率","0"+map.get(strs[j]).get("办结率").toString());
					}else{
						continue;
					}
				}
				
				logop.logInfoYeWu("全系统全流程网上商事登记业务办理情况表", "WDY", i == 1 ? "查看报表"
						: "下载报表", sql, beginTime + "," + endTime + "," , req);
				return map;
			}
			
			
	}

	@SuppressWarnings({  "rawtypes", "unchecked" })
	public List<Map> dengjiExceedInfo(String beginTime, String endTime,
			int i, HttpServletRequest req) {
		IPersistenceDAO dao = this.getPersistenceDAO("dc_dc");
		List list=new ArrayList();
		List<Map> list1=new ArrayList<Map>();
		list.add(beginTime+" 00:00:00");
		list.add(endTime+" 23:59:59");
		String sql=
				"select apply_no,\n" +
						"       accept_dept_name window_name,\n" + 
						"      round( (case when( (b.app = '6' and b.biz_state = 'WaitSignAccept' and\n" + 
						"             b.accept_time > b.time_limit) or\n" + 
						"             b.biz_state in ('AICMER_InspectAgree',\n" + 
						"                        'AICMER_AcceptedAndInspectAgree',\n" + 
						"                        'AICMER_RejectTermination') and\n" + 
						"       b.accept_time > b.time_limit) then getworkday(submit_time, Accept_time)\n" + 
						"       when (b.biz_state = 'AICMER_Accepted' and b.time_limit < sysdate) then getworkday(b.submit_time,sysdate) end )) as time_exceed,\n" + 
						"      -- round(getworkday(time_limit, Accept_time)),\n" + 
						"       b.recent_permit_user_name  as user_name\n" + 
						"  from dc_dc.WHOLE_PROCESS b\n" + 
						" where ((b.app = '6' and b.biz_state = 'WaitSignAccept' and\n" + 
						"       b.accept_time > b.time_limit)\n" + 
						"    or (\n" + 
						"       b.biz_state in ('AICMER_InspectAgree',\n" + 
						"                        'AICMER_AcceptedAndInspectAgree',\n" + 
						"                        'AICMER_RejectTermination') and\n" + 
						"       b.accept_time > b.time_limit)\n" + 
						"    or (b.biz_state = 'AICMER_Accepted' and b.time_limit < sysdate))\n" + 
						"\n" + 
						"      --  select count(1)\n" + 
						"   and b.submit_time >=\n" + 
						"       to_date(?, 'yyyy-mm-dd hh24:mi:ss')\n" + 
						"   and b.submit_time <=\n" + 
						"       to_date(?, 'yyyy-mm-dd hh24:mi:ss')\n" + 
						"       order by b.accept_dept_name,to_char(b.submit_time,'dd') asc";
			try {
				list1 = dao.queryForList(sql, list);
			} catch (OptimusException e) {
				e.printStackTrace();
			}
			logop.logInfoYeWu("全流程网上商事登记业务超期办理情况表", "WDY", i == 1 ? "查看报表"
					: "下载报表", sql, beginTime + "," + endTime + "," , req);
		return list1;
	}

}
