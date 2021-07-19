package com.gwssi.report.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
public class Query12315ForMonthService   extends BaseService implements ReportSource {
	
	
	IPersistenceDAO dao = this.getPersistenceDAO("dc_dc");
	LogOperation logop = new LogOperation();
	
	public Date getYearFirst(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}
	
	
	
	
	
	
	public List<Map> getData(String beginTime, String endTime,
			int opreationtype, HttpServletRequest req) {
		List params = new ArrayList();
		List<Map> list = new ArrayList<Map>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(beginTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.add(Calendar.YEAR, -1);//
		String tBeginTime = sdf.format(cal.getTime());
		cal.clear();
		try {
			cal.setTime(sdf.parse(endTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.add(Calendar.YEAR, -1);
		String tEndTime = sdf.format(cal.getTime());
		String firstDay = sdf.format(getYearFirst(Integer.valueOf(beginTime
				.substring(0, 4))));
		String tfirstDay = sdf.format(getYearFirst(Integer.valueOf(tBeginTime
				.substring(0, 4))));
		params.add(beginTime + " 00:00:00");
		params.add(endTime + " 23:59:59");
		params.add(firstDay + " 00:00:00");
		params.add(endTime + " 23:59:59");
		params.add(tBeginTime + " 00:00:00");
		params.add(tEndTime + " 23:59:59");
		params.add(tfirstDay + " 00:00:00");
		params.add(tEndTime + " 23:59:59");
		String sql = "with x as (\n"
				+ "select q.inftype,(case when q.regtime1>=to_date(?, 'yyyy-mm-dd hh24:mi:ss') and q.regtime1<=to_date(?, 'yyyy-mm-dd hh24:mi:ss') then 1 else null end) now,\n"
				+ "(case when q.regtime2>=to_date(?, 'yyyy-mm-dd hh24:mi:ss') and q.regtime2<=to_date(?, 'yyyy-mm-dd hh24:mi:ss') then 1 else null end) now_sum,\n"
				+ "(case when q.regtime3>=to_date(?, 'yyyy-mm-dd hh24:mi:ss') and q.regtime3<=to_date(?, 'yyyy-mm-dd hh24:mi:ss') then 1 else null end) upyear,\n"
				+ "(case when q.regtime4>=to_date(?, 'yyyy-mm-dd hh24:mi:ss') and q.regtime4<=to_date(?, 'yyyy-mm-dd hh24:mi:ss') then 1 else null end) upyear_sum,\n"
				+ "q.statename,q.acctype,q.acctype acctype1,q.acctype acctype2,q.acctype acctype3,q.acctype acctype4,q.acctype acctype5,q.acctype acctype6,q.acctype acctype7,q.acctype acctype8,q.acctype acctype9,\n"
				+ "q.acctype acctype10,q.acctype acctype11,q.acctype acctype12,q.acctype acctype13,q.putoncase,q.MEDIATE_RESULT,q.PENAM,q.infowareid ,\n"
				+ "q.REDECOLOS from\n"
				+ "(select i.inftype,i.regtime regtime1,i.regtime regtime2,i.regtime regtime3,i.regtime regtime4,n.statename,f.acctype,f.putoncase,m.MEDIATE_RESULT,p.PENAM,t.infowareid,m.REDECOLOS from dc_cpr_infoware i\n"
				+ "left join dc_CPR_PROCESS_INSTANCE  n\n"
				+ "on i.proinsid=n.proinsid\n"
				+ "left join dc_cpr_feedback f\n"
				+ "on f.feedbackid=i.feedbackid\n"
				+ "left join dc_CPR_MEDIATION m\n"
				+ "on m.mediationid=f.mediationid\n"
				+ "left join dc_CPR_CASE_INFO c\n"
				+ "on f.caseinfoid=c.caseinfoid\n"
				+ "left join dc_CPR_LEGAL_PUNISHMENT p\n"
				+ "on c.legpunid=p.legpunid\n"
				+ "left join dc_CPR_INFOWARE_TRANSFER t\n"
				+ "on i.infowareid=t.infowareid where i.inftype='1') q)\n"
				+ "   select '承办数' name,'件' unit, to_char(nvl(sum(now),0)) now ,to_char(nvl(sum(now_sum),0)) nowsum,to_char(nvl(sum(upyear),0))upyear,to_char(nvl(sum(upyear_sum),0))upyear_sum,\n"
				+ "          to_char(nvl(sum(now_sum),0)-nvl(sum(upyear_sum),0))zjshu,\n"
				+ "          to_char(round((nvl(sum(now_sum),0)-nvl(sum(upyear_sum),0))/nvl(sum(upyear_sum),1),4)*100,'fm99999999990.0999') zjlv\n"
				+ "       from x --承办数\n"
				+ "union all\n"
				+ "   select '受理数' name,'件' unit,to_char(nvl(sum(now),0)) now ,to_char(nvl(sum(now_sum),0)) nowsum,to_char(nvl(sum(upyear),0))upyear,to_char(nvl(sum(upyear_sum),0))upyear_sum,\n"
				+ "          to_char(nvl(sum(now_sum),0)-nvl(sum(upyear_sum),0))zjshu,\n"
				+ "          to_char(round((nvl(sum(now_sum),0)-nvl(sum(upyear_sum),0))/nvl(sum(upyear_sum),1),4)*100,'fm99999999990.0999') zjlv\n"
				+ "       from x where  x.statename not in ('待分派','预登记','网上登记','待受理') and x.acctype <>'20'--受理数\n"
				+ "union all\n"
				+ "   select '调解成功数' name,'件' unit, to_char(nvl(sum(now),0)) now ,to_char(nvl(sum(now_sum),0)) nowsum,to_char(nvl(sum(upyear),0))upyear,to_char(nvl(sum(upyear_sum),0))upyear_sum,\n"
				+ "          to_char(nvl(sum(now_sum),0)-nvl(sum(upyear_sum),0))zjshu,\n"
				+ "          to_char(round((nvl(sum(now_sum),0)-nvl(sum(upyear_sum),0))/nvl(sum(upyear_sum),1),4)*100,'fm99999999990.0999') zjlv\n"
				+ "       from x where  x.statename not in ('待分派','预登记','网上登记','待受理')\n"
				+ "        and x.acctype = '11' and x.MEDIATE_RESULT = '1'    --调解成功数\n"
				+ "union all\n"
				+ "\n"
				+ "   select '调解成功率' name,'%' unit,\n"
				+ "   to_char(round(nvl(sum(case when x.acctype = '11' and x.MEDIATE_RESULT = '1'then now else null end),0)/\n"
				+ "          nvl(sum(case when x.acctype1 <>'20' then now else null end),1),4)*100,'fm9999999990.099') now,\n"
				+ "          to_char(round(nvl(sum(case when x.acctype = '11' and x.MEDIATE_RESULT = '1'then now_sum else null end),0)/\n"
				+ "          nvl(sum(case when x.acctype1 <>'20' then now_sum else null end),1),4)*100,'fm9999999990.099') nowsum,\n"
				+ "          to_char(round(nvl(sum(case when x.acctype = '11' and x.MEDIATE_RESULT = '1'then upyear else null end),0)/\n"
				+ "          nvl(sum(case when x.acctype1 <>'20' then upyear else null end),1),4)*100,'fm9999999990.099')upyear,\n"
				+ "          to_char(round(nvl(sum(case when x.acctype = '11' and x.MEDIATE_RESULT = '1'then upyear_sum else null end),0)/\n"
				+ "          nvl(sum(case when x.acctype1 <>'20' then upyear_sum else null end),1),4)*100,'fm9999999990.099') upyear_sum,\n"
				+ "          to_char((round(nvl(sum(case when x.acctype3 = '11' and x.MEDIATE_RESULT = '1'then now_sum else null end),0)/\n"
				+ "          nvl(sum(case when x.acctype4 <>'20' then now_sum else null end),1),4)-\n"
				+ "          (round(nvl(sum(case when x.acctype5 = '11' and x.MEDIATE_RESULT = '1'then upyear_sum else null end),0)/\n"
				+ "          nvl(sum(case when x.acctype6 <>'20' then upyear_sum else null end),1),4)))*100,'fm9999999990.099')  zjshu,\n"
				+ "          to_char(round(((round(nvl(sum(case when x.acctype7 = '11' and x.MEDIATE_RESULT = '1'then now_sum else null end),0)/\n"
				+ "          nvl(sum(case when x.acctype8 <>'20' then now_sum else null end),1),4))-\n"
				+ "          (round(nvl(sum(case when x.acctype9 = '11' and x.MEDIATE_RESULT = '1'then upyear_sum else null end),0)/\n"
				+ "          nvl(sum(case when x.acctype10 <>'20' then upyear_sum else null end),1),4)))/\n"
				+ "          nvl((round(nvl(sum(case when x.acctype11 = '11' and x.MEDIATE_RESULT = '1'then upyear_sum else null end),null)/\n"
				+ "          nvl(sum(case when x.acctype12 <>'20' then upyear_sum else null end),1),4)),1),4)*100,'fm9999999990.09999') zjlv\n"
				+ "       from x where x.statename not in ('待分派','预登记','网上登记','待受理')\n"
				+ "           --调解成功数\n"
				+ "union all\n"
				+ "      select '挽回损失' name,'万元' unit,\n"
				+ "      to_char(nvl(sum(case when now='1' then REDECOLOS else null end),0)/10000,'fm9999999990.0999999999999') now ,\n"
				+ "             to_char(nvl(sum(case when now_sum='1' then REDECOLOS else null end),0)/10000,'fm9999999990.0999999999999') nowsum,\n"
				+ "             to_char(nvl(sum(case when upyear='1' then REDECOLOS else null end),0)/10000,'fm9999999990.0999999999999') upyear,\n"
				+ "             to_char(nvl(sum(case when upyear_sum=1 then REDECOLOS else null end ),0)/10000,'fm9999999990.0999999999999') upyear_sum,\n"
				+ "                to_char((nvl(sum(case when now_sum='1' then REDECOLOS else null end),0)-nvl(sum(case when upyear_sum='1' then REDECOLOS else null end),0))/10000,'fm9999999990.0999999999999')zjshu,\n"
				+ "                to_char(round(((nvl(sum(case when now_sum='1' then REDECOLOS else null end),0)-nvl(sum(case when upyear_sum='1' then REDECOLOS else null end),0))/10000)\n"
				+ "                /nvl((sum(case when upyear_sum=1 then REDECOLOS else null end )/10000),1),4)*100,'fm9999999990.099')zjlv\n"
				+ "             from x where x.statename not in ('待分派','预登记','网上登记','待受理')\n"
				+ "              and x.acctype = '11'   --挽回经济损失\n"
				+ "union all\n"
				+ "      select '诉转案立案数' name,'件' unit,\n"
				+ "       to_char(nvl(sum(now),0)) now ,to_char(nvl(sum(now_sum),0)) nowsum,to_char(nvl(sum(upyear),0))upyear,to_char(nvl(sum(upyear_sum),0))upyear_sum,\n"
				+ "          to_char(nvl(sum(now_sum),0)-nvl(sum(upyear_sum),0))zjshu,\n"
				+ "          to_char(round((nvl(sum(now_sum),0)-nvl(sum(upyear_sum),0))/nvl(sum(upyear_sum),1),4)*100,'fm99999999990.0999') zjlv\n"
				+ "       from x where x.statename not in ('待分派','预登记','网上登记','待受理')\n"
				+ "        and x.acctype = '12' and x.putoncase is not null --诉转案件数\n"
				+ "union all\n"
				+ "        select '诉转案罚款金额' name,'万元' unit,\n"
				+ "        to_char(nvl(sum(case when now='1' then PENAM else null end),0)/10000,'fm9999999990.0999999999999') now ,\n"
				+ "             to_char(nvl(sum(case when now_sum='1' then PENAM else null end),0)/10000,'fm9999999990.0999999999999') nowsum,\n"
				+ "             to_char(nvl(sum(case when upyear='1' then PENAM else null end),0)/10000,'fm9999999990.0999999999999') upyear,\n"
				+ "             to_char(nvl(sum(case when upyear_sum=1 then PENAM else null end ),0)/10000,'fm9999999990.0999999999999') upyear_sum,\n"
				+ "                to_char((nvl(sum(case when now_sum='1' then PENAM else null end),0)-nvl(sum(case when upyear_sum='1' then PENAM else null end),0))/10000,'fm9999999990.0999999999999')zjshu,\n"
				+ "                to_char(round(((nvl(sum(case when now_sum='1' then PENAM else null end),0)-nvl(sum(case when upyear_sum='1' then PENAM else null end),0))/10000)\n"
				+ "                /nvl((sum(case when upyear_sum=1 then PENAM else null end ))/10000,1),4)*100,'fm9999999990.099')zjlv\n"
				+ "             from x where x.statename not in ('待分派','预登记','网上登记','待受理')\n"
				+ "              and x.acctype = '12'  and x.putoncase is not null --诉转案罚款金额\n"
				+ "union all\n"
				+ "       select '转服务站处理数' name,'件' unit,\n"
				+ "       to_char(nvl(sum(now),0)) now ,to_char(nvl(sum(now_sum),0)) nowsum,to_char(nvl(sum(upyear),0))upyear,to_char(nvl(sum(upyear_sum),0))upyear_sum,\n"
				+ "          to_char(nvl(sum(now_sum),0)-nvl(sum(upyear_sum),0))zjshu,\n"
				+ "          to_char(round((nvl(sum(now_sum),0)-nvl(sum(upyear_sum),0))/nvl(sum(upyear_sum),1),4)*100,'fm99999999990.0999') zjlv\n"
				+ "       from x where  x.infowareid is not null  --转服务站处理数";

		try {
			list = dao.queryForList(sql, params);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("消费者投诉处理情况表（非月报版）", "WDY", opreationtype == 1 ? "查看报表"
				: "下载报表", sql, beginTime + "," + endTime, req);
		return list;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public ReportModel getReport(TCognosReportBO queryInfo) {
		// TODO Auto-generated method stub
		return null;
	}

}
