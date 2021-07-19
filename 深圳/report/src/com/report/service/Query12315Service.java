package com.report.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.report.dao.BaseDao;

@Service
@Transactional
public class Query12315Service extends BaseDao{
	
	public List getRecode(String bTime,String eTime) throws ParseException {

		String startTime = null, endTime = null, lastYearStartTime = null, lastYearEndTime = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		
	//开始时间
		String str = bTime;
		startTime = bTime;
		Date dt = sdf.parse(str);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.add(Calendar.YEAR, -1);// 日期减1年
		Date dt1 = rightNow.getTime();
		String reStr = sdf.format(dt1);
		System.out.println(reStr);
		lastYearStartTime = reStr; // 去年这个日期
		

		
		//结束时间
		str = eTime;
		endTime = str;
		dt = sdf.parse(str);
		rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.add(Calendar.YEAR, -1);// 日期减1年
		dt1 = rightNow.getTime();
		reStr = sdf.format(dt1);
		lastYearEndTime = reStr;
		System.out.println(reStr);
		
		
		
		
		
		
		
		
		String sql = "with x as (\n"
				+ "select q.inftype,(case when q.regtime1>=to_date('"+startTime+" 00:00:00', 'yyyy-mm-dd hh24:mi:ss') and q.regtime1<=to_date('"+endTime+" 23:59:59', 'yyyy-mm-dd hh24:mi:ss') then 1 else null end) now,\n"
				+ "(case when q.regtime2>=to_date('"+startTime+" 00:00:00', 'yyyy-mm-dd hh24:mi:ss') and q.regtime2<=to_date('"+endTime+" 23:59:59', 'yyyy-mm-dd hh24:mi:ss') then 1 else null end) now_sum,\n"
				+ "(case when q.regtime3>=to_date('"+lastYearStartTime+" 00:00:00', 'yyyy-mm-dd hh24:mi:ss') and q.regtime3<=to_date('"+lastYearEndTime+" 23:59:59', 'yyyy-mm-dd hh24:mi:ss') then 1 else null end) upyear,\n"
				+ "(case when q.regtime4>=to_date('"+lastYearStartTime+" 00:00:00', 'yyyy-mm-dd hh24:mi:ss') and q.regtime4<=to_date('"+lastYearEndTime+" 23:59:59', 'yyyy-mm-dd hh24:mi:ss') then 1 else null end) upyear_sum,\n"
				+ "q.statename,q.acctype,q.acctype acctype1,q.acctype acctype2,q.acctype acctype3,q.acctype acctype4,q.acctype acctype5,q.acctype acctype6,q.acctype acctype7,q.acctype acctype8,q.acctype acctype9,\n"
				+ "q.acctype acctype10,q.acctype acctype11,q.acctype acctype12,q.acctype acctype13,q.putoncase,q.MEDIATE_RESULT,q.PENAM,q.infowareid ,\n"
				+ "q.REDECOLOS from\n"
				+ "(select i.inftype,i.regtime regtime1,i.regtime regtime2,i.regtime regtime3,i.regtime regtime4,n.statename,f.acctype,f.putoncase,m.MEDIATE_RESULT,p.PENAM,t.infowareid,m.REDECOLOS from dc_cpr_infoware i\n"
				+ "left join dc_CPR_PROCESS_INSTANCE  n\n" + "on i.proinsid=n.proinsid\n"
				+ "left join dc_cpr_feedback f\n" + "on f.feedbackid=i.feedbackid\n" + "left join dc_CPR_MEDIATION m\n"
				+ "on m.mediationid=f.mediationid\n" + "left join dc_CPR_CASE_INFO c\n"
				+ "on f.caseinfoid=c.caseinfoid\n" + "left join dc_CPR_LEGAL_PUNISHMENT p\n"
				+ "on c.legpunid=p.legpunid\n" + "left join dc_CPR_INFOWARE_TRANSFER t\n"
				+ "on i.infowareid=t.infowareid where i.inftype='1') q)\n"
				+ "   select '承办数' name,'件' unit, to_char(nvl(sum(now),0)) now ,to_char(nvl(sum(now_sum),0)) nowsum,to_char(nvl(sum(upyear),0))upyear,to_char(nvl(sum(upyear_sum),0))upyear_sum,\n"
				+ "          to_char(nvl(sum(now_sum),0)-nvl(sum(upyear_sum),0))zjshu,\n"
				+ "          to_char(round((nvl(sum(now_sum),0)-nvl(sum(upyear_sum),0))/nvl(sum(upyear_sum),1),4)*100,'fm99999999990.0999') zjlv\n"
				+ "       from x --承办数\n" + "union all\n"
				+ "   select '受理数' name,'件' unit,to_char(nvl(sum(now),0)) now ,to_char(nvl(sum(now_sum),0)) nowsum,to_char(nvl(sum(upyear),0))upyear,to_char(nvl(sum(upyear_sum),0))upyear_sum,\n"
				+ "          to_char(nvl(sum(now_sum),0)-nvl(sum(upyear_sum),0))zjshu,\n"
				+ "          to_char(round((nvl(sum(now_sum),0)-nvl(sum(upyear_sum),0))/nvl(sum(upyear_sum),1),4)*100,'fm99999999990.0999') zjlv\n"
				+ "       from x where  x.statename not in ('待分派','预登记','网上登记','待受理') and x.acctype <>'20'--受理数\n"
				+ "union all\n"
				+ "   select '调解成功数' name,'件' unit, to_char(nvl(sum(now),0)) now ,to_char(nvl(sum(now_sum),0)) nowsum,to_char(nvl(sum(upyear),0))upyear,to_char(nvl(sum(upyear_sum),0))upyear_sum,\n"
				+ "          to_char(nvl(sum(now_sum),0)-nvl(sum(upyear_sum),0))zjshu,\n"
				+ "          to_char(round((nvl(sum(now_sum),0)-nvl(sum(upyear_sum),0))/nvl(sum(upyear_sum),1),4)*100,'fm99999999990.0999') zjlv\n"
				+ "       from x where  x.statename not in ('待分派','预登记','网上登记','待受理')\n"
				+ "        and x.acctype = '11' and x.MEDIATE_RESULT = '1'    --调解成功数\n" + "union all\n"
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
				+ "       from x where x.statename not in ('待分派','预登记','网上登记','待受理')\n" + "           --调解成功数\n"
				+ "union all\n" + "      select '挽回损失' name,'万元' unit,\n"
				+ "      to_char(nvl(sum(case when now='1' then REDECOLOS else null end),0)/10000,'fm9999999990.0999999999999') now ,\n"
				+ "             to_char(nvl(sum(case when now_sum='1' then REDECOLOS else null end),0)/10000,'fm9999999990.0999999999999') nowsum,\n"
				+ "             to_char(nvl(sum(case when upyear='1' then REDECOLOS else null end),0)/10000,'fm9999999990.0999999999999') upyear,\n"
				+ "             to_char(nvl(sum(case when upyear_sum=1 then REDECOLOS else null end ),0)/10000,'fm9999999990.0999999999999') upyear_sum,\n"
				+ "                to_char((nvl(sum(case when now_sum='1' then REDECOLOS else null end),0)-nvl(sum(case when upyear_sum='1' then REDECOLOS else null end),0))/10000,'fm9999999990.0999999999999')zjshu,\n"
				+ "                to_char(round(((nvl(sum(case when now_sum='1' then REDECOLOS else null end),0)-nvl(sum(case when upyear_sum='1' then REDECOLOS else null end),0))/10000)\n"
				+ "                /nvl((sum(case when upyear_sum=1 then REDECOLOS else null end )/10000),1),4)*100,'fm9999999990.099')zjlv\n"
				+ "             from x where x.statename not in ('待分派','预登记','网上登记','待受理')\n"
				+ "              and x.acctype = '11'   --挽回经济损失\n" + "union all\n"
				+ "      select '诉转案立案数' name,'件' unit,\n"
				+ "       to_char(nvl(sum(now),0)) now ,to_char(nvl(sum(now_sum),0)) nowsum,to_char(nvl(sum(upyear),0))upyear,to_char(nvl(sum(upyear_sum),0))upyear_sum,\n"
				+ "          to_char(nvl(sum(now_sum),0)-nvl(sum(upyear_sum),0))zjshu,\n"
				+ "          to_char(round((nvl(sum(now_sum),0)-nvl(sum(upyear_sum),0))/nvl(sum(upyear_sum),1),4)*100,'fm99999999990.0999') zjlv\n"
				+ "       from x where x.statename not in ('待分派','预登记','网上登记','待受理')\n"
				+ "        and x.acctype = '12' and x.putoncase is not null --诉转案件数\n" + "union all\n"
				+ "        select '诉转案罚款金额' name,'万元' unit,\n"
				+ "        to_char(nvl(sum(case when now='1' then PENAM else null end),0)/10000,'fm9999999990.0999999999999') now ,\n"
				+ "             to_char(nvl(sum(case when now_sum='1' then PENAM else null end),0)/10000,'fm9999999990.0999999999999') nowsum,\n"
				+ "             to_char(nvl(sum(case when upyear='1' then PENAM else null end),0)/10000,'fm9999999990.0999999999999') upyear,\n"
				+ "             to_char(nvl(sum(case when upyear_sum=1 then PENAM else null end ),0)/10000,'fm9999999990.0999999999999') upyear_sum,\n"
				+ "                to_char((nvl(sum(case when now_sum='1' then PENAM else null end),0)-nvl(sum(case when upyear_sum='1' then PENAM else null end),0))/10000,'fm9999999990.0999999999999')zjshu,\n"
				+ "                to_char(round(((nvl(sum(case when now_sum='1' then PENAM else null end),0)-nvl(sum(case when upyear_sum='1' then PENAM else null end),0))/10000)\n"
				+ "                /nvl((sum(case when upyear_sum=1 then PENAM else null end ))/10000,1),4)*100,'fm9999999990.099')zjlv\n"
				+ "             from x where x.statename not in ('待分派','预登记','网上登记','待受理')\n"
				+ "              and x.acctype = '12'  and x.putoncase is not null --诉转案罚款金额\n" + "union all\n"
				+ "       select '转服务站处理数' name,'件' unit,\n"
				+ "       to_char(nvl(sum(now),0)) now ,to_char(nvl(sum(now_sum),0)) nowsum,to_char(nvl(sum(upyear),0))upyear,to_char(nvl(sum(upyear_sum),0))upyear_sum,\n"
				+ "          to_char(nvl(sum(now_sum),0)-nvl(sum(upyear_sum),0))zjshu,\n"
				+ "          to_char(round((nvl(sum(now_sum),0)-nvl(sum(upyear_sum),0))/nvl(sum(upyear_sum),1),4)*100,'fm99999999990.0999') zjlv\n"
				+ "       from x where  x.infowareid is not null  --转服务站处理数\n";

		// System.out.println(" \n "+sql);

		Query query = this.getSession().createSQLQuery(sql);
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

		List list = query.list();

		return list;
		

	}

}
