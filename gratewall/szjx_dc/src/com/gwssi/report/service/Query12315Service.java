package com.gwssi.report.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

/**
 * 
 * 项目名称：szjx_dc 类名称：Query12315Service 类描述： 创建人：batman 创建时间：2017年11月24日 下午5:56:05
 * 
 * @version
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Service
public class Query12315Service extends BaseService implements ReportSource {
	IPersistenceDAO dao = this.getPersistenceDAO("dc_dc");
	LogOperation logop = new LogOperation();
	@SuppressWarnings("serial")
	private Map<String, String> regcodes = new HashMap<String, String>() {
		{
			put("罗湖局", "440303");
			put("福田局", "440304");
			put("南山局", "440305");
			put("宝安局", "440306");
			put("龙岗局", "440307");
			put("盐田局", "440302");// 之前有对应使用报表中的对应，现在使用消保系统中DC_CPR_INVOLVED_MAIN表的
									// DIVISION字段结合addr来进行对应
			put("光明局", "440308");// 之前有对应
			put("坪山局", "440309");// 之前有对应
			put("龙华局", "440310");// 之前有对应
			put("大鹏局", "440311");// 之前有对应
		}
	};
	private String[] regs = new String[] { "罗湖局", "福田局", "南山局", "宝安局", "龙岗局",
			"盐田局", "光明局", "坪山局", "龙华局", "大鹏局" };

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
		logop.logInfoYeWu("消费者投诉处理情况表", "WDY", opreationtype == 1 ? "查看报表"
				: "下载报表", sql, beginTime + "," + endTime, req);
		return list;
	}

	@Override
	public ReportModel getReport(TCognosReportBO queryInfo) {
		return null;
	}

	public List<Map> getYeWuNum(String beginTime, String endTime,
			int opreationtype, HttpServletRequest req, String regcode) {
		List list = new ArrayList();
		List<Map> list1 = new ArrayList<Map>();
		list.add(beginTime);
		list.add(endTime);
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT  REPLACE(NVL(T.REGPERSON, '合计'), 'NULL', '  ') AS 姓名,\n"
				+ "        NVL(SUM(T.CNT), 0) AS 总数 ,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='1' THEN T.CNT END),0) AS  电话,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='2' THEN T.CNT END),0) AS  短信,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='3' THEN T.CNT END),0) AS  来人,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='4' THEN T.CNT END),0) AS  来函,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='5' THEN T.CNT END),0) AS  传真,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='6' THEN T.CNT END),0) AS  互联网络,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='7' THEN T.CNT END),0) AS  留言,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='8' THEN T.CNT END),0) AS  市府12345转件,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='9' THEN T.CNT END),0) AS  其他,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='a' THEN T.CNT END),0) AS  政府在线,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='b' THEN T.CNT END),0) AS  电子邮件,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='c' THEN T.CNT END),0) AS  直通车,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='d' THEN T.CNT END),0) AS  来信,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='e' THEN T.CNT END),0) AS  上级部门交办,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='f' THEN T.CNT END),0) AS  民心桥,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='g' THEN T.CNT END),0) AS  其它部门转办,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='k' THEN T.CNT END),0) AS  QQ服务,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='m' THEN T.CNT END),0) AS  微信平台,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='h' THEN T.CNT END),0) AS  三打两建办,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='i' THEN T.CNT END),0) AS  局长邮箱,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='j' THEN T.CNT END),0) AS  异地消费申诉转办件,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='l' THEN T.CNT END),0) AS  全国价格举报平台来件,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='n' THEN T.CNT END),0) AS  消费通,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='o' THEN T.CNT END),0) AS  消委会转件\n"
				+ "  FROM (SELECT NVL(A.REGPERSON, 'NULL') AS REGPERSON,\n"
				+ "               A.INCFORM,\n"
				+ "               COUNT(1) AS CNT\n"
				+ "          FROM DC_CPR_INFOWARE A\n"
				+ "          where a.regtime>=to_date(?,'yyyy-mm-dd')\n"
				+ "          and a.regtime<=to_date(?,'yyyy-mm-dd')\n");
		if (regcode != null && regcode.length() > 0) {
			String[] split = regcode.split(",");
			if (split.length > 1) {
				for (int i = 0, b = split.length; i < b; i++) {
					if (i == 0) {
						sb.append(" and (a.regdepcode = ? ");
						list.add(split[i]);
					} else if (i == b - 1) {
						sb.append(" or a.regdepcode= ? )\n");
						list.add(split[i]);
					} else {
						sb.append(" or a.regdepcode=? ");
						list.add(split[i]);
					}
				}
			} else {
				sb.append("and a.regdepcode= ? \n");
				list.add(regcode);
			}
		}
		sb.append("         GROUP BY A.REGPERSON, A.INCFORM) T\n"
				+ " GROUP BY ROLLUP(T.REGPERSON)");
		try {
			list1 = dao.queryForList(sb.toString(), list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("话务员登记量报表", "WDY", opreationtype == 1 ? "查看报表"
				: "下载报表", sb.toString(), beginTime + "," + endTime + ","
				+ regcode, req);
		return list1;
	}

	public List<Map> getYeWuTj(String beginTime, String endTime,
			int opreationtype, HttpServletRequest req, String regcode) {
		List list = new ArrayList();
		List<Map> list1 = new ArrayList<Map>();
		StringBuffer sb = new StringBuffer();
		list.add(beginTime);
		list.add(endTime);
		sb.append("SELECT  (case when T.REGPERSON is null then ' 'else t.REGPERSON end) as 姓名,\n"
				+ "NVL(SUM(CASE WHEN T.Inftype='1' THEN T.CNT END),0) AS  市场监管投诉,\n"
				+ "NVL(SUM(CASE WHEN T.Inftype='2' THEN T.CNT END),0) AS  举报,\n"
				+ "NVL(SUM(CASE WHEN T.Inftype='3' THEN T.CNT END),0) AS  咨询,\n"
				+ "NVL(SUM(CASE WHEN T.Inftype='4' THEN T.CNT END),0) AS  建议,\n"
				+ "NVL(SUM(CASE WHEN T.Inftype='6' THEN T.CNT END),0) AS  消委会投诉,\n"
				+ "NVL(SUM(CASE WHEN T.Inftype='9' THEN T.CNT END),0) AS  其他,\n"
				+ "NVL(SUM(CASE WHEN T.Inftype='8' THEN T.CNT END),0) AS  行政监察件,\n"
				+ "NVL(SUM(T.CNT),0) AS 总数\n"
				+ "  FROM (\n"
				+ "SELECT A.REGPERSON,A.Inftype, COUNT(1) AS CNT\n"
				+ "  FROM DC_CPR_INFOWARE A,DC_cpr_process_instance B\n"
				+ "  where A.Proinsid = B.Proinsid\n"
				+ "    and A.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "    and A.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "    and B.action <> '直接回复'\n");
		if (regcode != null && regcode.length() > 0) {
			String[] split = regcode.split(",");
			if (split.length > 1) {
				for (int i = 0, b = split.length; i < b; i++) {
					if (i == 0) {
						sb.append(" and (a.regdepcode = ? ");
						list.add(split[i]);
					} else if (i == b - 1) {
						sb.append(" or a.regdepcode= ? )\n");
						list.add(split[i]);
					} else {
						sb.append(" or a.regdepcode=? ");
						list.add(split[i]);
					}
				}
			} else {
				sb.append("and a.regdepcode= ? \n");
				list.add(regcode);
			}
		}
		sb.append(" GROUP BY A.REGPERSON,A.Inftype ) T\n"
				+ " GROUP BY T.REGPERSON");
		try {
			list1 = dao.queryForList(sb.toString(), list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("话务员登记提交量统计报表", "WDY", opreationtype == 1 ? "查看报表"
				: "下载报表", sb.toString(), beginTime + "," + endTime + ","
				+ regcode, req);
		return list1;
	}

	public List<Map> getYeWuTongjiByType(String beginTime, String endTime,
			int opreationtype, HttpServletRequest req, String regcode) {
		List list = new ArrayList();
		List<Map> list1 = new ArrayList<Map>();
		StringBuffer sb = new StringBuffer();
		list.add(beginTime);
		list.add(endTime);

		sb.append("  SELECT REPLACE(NVL(T.NAME, '合计'), 'NULL', '  ') AS 姓名,\n"
				+ "NVL(SUM(CASE WHEN T.Inftype='1' THEN T.CNT END),0) AS  市场监管投诉,\n"
				+ "NVL(SUM(CASE WHEN T.Inftype='2' THEN T.CNT END),0) AS  举报,\n"
				+ "NVL(SUM(CASE WHEN T.Inftype='3' THEN T.CNT END),0) AS  咨询,\n"
				+ "NVL(SUM(CASE WHEN T.Inftype='4' THEN T.CNT END),0) AS  建议,\n"
				+ "NVL(SUM(CASE WHEN T.Inftype='6' THEN T.CNT END),0) AS  消委会投诉,\n"
				+ "NVL(SUM(CASE WHEN T.Inftype='9' THEN T.CNT END),0) AS  其他,\n"
				+ "NVL(SUM(CASE WHEN T.Inftype='8' THEN T.CNT END),0) AS  行政监察件,\n"
				+ "NVL(SUM(CASE WHEN T.Inftype is not null THEN T.CNT END),0) AS 总数\n"
				+ "  FROM (\n" + "SELECT B.NAME,A.Inftype, COUNT(1) AS CNT\n"
				+ "  FROM DC_CPR_INFOWARE A,dc_code.dc_12315_codedata B\n"
				+ "  where A.INCFORM(+) = B.Code\n"
				+ "    and B.codetable='CH03'\n"
				+ "    and A.REGTIME(+) >= to_date(?,'yyyy-mm-dd')\n"
				+ "    and A.REGTIME(+) <= to_date(?,'yyyy-mm-dd')\n");
		if (regcode != null && regcode.length() > 0) {
			String[] split = regcode.split(",");
			if (split.length > 1) {
				for (int i = 0, b = split.length; i < b; i++) {
					if (i == 0) {
						sb.append(" and (a.regdepcode = ? ");
						list.add(split[i]);
					} else if (i == b - 1) {
						sb.append(" or a.regdepcode= ? )\n");
						list.add(split[i]);
					} else {
						sb.append(" or a.regdepcode=? ");
						list.add(split[i]);
					}
				}
			} else {
				sb.append("and a.regdepcode= ? \n");
				list.add(regcode);
			}
		}
		sb.append(" GROUP BY B.NAME,A.Inftype ) T\n"
				+ " GROUP BY ROLLUP(T.NAME)");
		try {
			list1 = dao.queryForList(sb.toString(), list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("登记量统计报表（按接收类型分）", "WDY", opreationtype == 1 ? "查看报表"
				: "下载报表", sb.toString(), beginTime + "," + endTime + ","
				+ regcode, req);
		return list1;
	}

	public List<Map> getYeWuTongjiByDept(String beginTime, String endTime,
			int opreationtype, HttpServletRequest req) {
		List list = new ArrayList();
		List<Map> list1 = new ArrayList<Map>();
		list.add(beginTime);
		list.add(endTime+" 23:59:59");
		String sql = 
						" with x as (\n" + 
						" select nvl(t.HANPARDEPNAME ,'暂无') HANPARDEPNAME,nvl(t.Handepname,'空')Handepname,t.Inftype  from\n" + 
						"        dc_dc.dc_cpr_infoware t\n" + 
						"        where  t.REGTIME >= to_date(?,'yyyy-mm-dd')\n" + 
						"    and t.REGTIME <= to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"\n" + 
						" )\n" + 
						" select nvl(x.HANPARDEPNAME,'总计') 上级部门,nvl(x.Handepname,'合计')承办部门 ,\n" + 
						"NVL(SUM(CASE WHEN x.Inftype='1' THEN 1 END),0) AS  市场监管投诉,\n" + 
						"NVL(SUM(CASE WHEN x.Inftype='2' THEN 1 END),0) AS  举报,\n" + 
						"NVL(SUM(CASE WHEN x.Inftype='3' THEN 1 END),0) AS  咨询,\n" + 
						"NVL(SUM(CASE WHEN x.Inftype='4' THEN 1 END),0) AS  建议,\n" + 
						"NVL(SUM(CASE WHEN x.Inftype='6' THEN 1 END),0) AS  消委会投诉,\n" + 
						"NVL(SUM(CASE WHEN x.Inftype='9' THEN 1 END),0) AS  其他,\n" + 
						"NVL(SUM(CASE WHEN x.Inftype='8' THEN 1 END),0) AS  行政监察件,\n" + 
						"count(1) AS 总数\n" + 
						"from x\n" + 
						"group by rollup(x.HANPARDEPNAME,x.Handepname)\n" + 
						"order by x.HANPARDEPNAME";
		try {
			list1 = dao.queryForList(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("登记量统计报表（按承办部门分）", "WDY", opreationtype == 1 ? "查看报表"
				: "下载报表", sql, beginTime + "," + endTime, req);
		return list1;
	}

	public List<Map> getYeWuHot(String beginTime, String endTime,
			int opreationtype, HttpServletRequest req, String regcode) {
		List list = new ArrayList();
		List<Map> list1 = new ArrayList<Map>();
		StringBuffer sb = new StringBuffer();
		list.add(beginTime);
		list.add(endTime);
		sb.append("SELECT  REPLACE(NVL(T.REGPERSON, '合计'), 'NULL', '  ') AS REGPERSON,\n"
				+ "        NVL(SUM(T.CNT), 0) AS 总数 ,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='1' THEN T.CNT END),0) AS  电话,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='2' THEN T.CNT END),0) AS  短信,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='3' THEN T.CNT END),0) AS  来人,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='4' THEN T.CNT END),0) AS  来函,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='5' THEN T.CNT END),0) AS  传真,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='6' THEN T.CNT END),0) AS  互联网络,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='7' THEN T.CNT END),0) AS  留言,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='8' THEN T.CNT END),0) AS  市府12345转件,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='9' THEN T.CNT END),0) AS  其他,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='a' THEN T.CNT END),0) AS  政府在线,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='b' THEN T.CNT END),0) AS  电子邮件,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='c' THEN T.CNT END),0) AS  直通车,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='d' THEN T.CNT END),0) AS  来信,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='e' THEN T.CNT END),0) AS  上级部门交办,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='f' THEN T.CNT END),0) AS  民心桥,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='g' THEN T.CNT END),0) AS  其它部门转办,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='k' THEN T.CNT END),0) AS  QQ服务,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='m' THEN T.CNT END),0) AS  微信平台,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='h' THEN T.CNT END),0) AS  三打两建办,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='i' THEN T.CNT END),0) AS  局长邮箱,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='j' THEN T.CNT END),0) AS  异地消费申诉转办件,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='l' THEN T.CNT END),0) AS  全国价格举报平台来件,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='n' THEN T.CNT END),0) AS  消费通,\n"
				+ "        NVL(SUM(CASE WHEN T.INCFORM='o' THEN T.CNT END),0) AS  消委会转件\n"
				+ "  FROM (SELECT NVL(A.regperson, 'NULL') AS REGPERSON,\n"
				+ "               A.INCFORM,\n"
				+ "               COUNT(1) AS CNT\n"
				+ "          FROM DC_CPR_INFOWARE A\n"
				+ "          where A.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "            and A.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "            and A.ISHOTINFORM = '1'\n");
		if (regcode != null && regcode.length() > 0) {
			String[] split = regcode.split(",");
			if (split.length > 1) {
				for (int i = 0, b = split.length; i < b; i++) {
					if (i == 0) {
						sb.append(" and (a.regdepcode = ? ");
						list.add(split[i]);
					} else if (i == b - 1) {
						sb.append(" or a.regdepcode= ? )\n");
						list.add(split[i]);
					} else {
						sb.append(" or a.regdepcode=? ");
						list.add(split[i]);
					}
				}
			} else {
				sb.append("and a.regdepcode= ? \n");
				list.add(regcode);
			}
		}
		sb.append("         GROUP BY A.REGPERSON, A.INCFORM) T\n"
				+ " GROUP BY ROLLUP(T.REGPERSON)");
		try {
			list1 = dao.queryForList(sb.toString(), list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("话务员登记热点投诉人件统计报表", "WDY", opreationtype == 1 ? "查看报表"
				: "下载报表", sb.toString(), beginTime + "," + endTime + ","
				+ regcode, req);
		return list1;
	}

	public List<Map> getHotMan(String beginTime, String endTime,
			String checkType, int opreationtype, HttpServletRequest req) {
		List list = new ArrayList();
		List<Map> list1 = new ArrayList<Map>();
		list.add(beginTime);
		list.add(endTime);
		String[] split = checkType.split(",");
		String sql = "SELECT  REPLACE(NVL(T.persname, '合计'), 'NULL', '  ') AS persname,\n"
				+ "NVL(SUM(CASE WHEN T.Handepname='市市场稽查局' or T.Hanpardepname = '市市场稽查局' THEN T.CNT END),0) AS  市市场稽查局,\n"
				+ "NVL(SUM(CASE WHEN T.Handepname='福田局' or T.Hanpardepname = '福田局' THEN T.CNT END),0) AS  福田局,\n"
				+ "NVL(SUM(CASE WHEN T.Handepname='罗湖局' or T.Hanpardepname = '罗湖局' THEN T.CNT END),0) AS  罗湖局,\n"
				+ "NVL(SUM(CASE WHEN T.Handepname='南山局' or T.Hanpardepname = '南山局' THEN T.CNT END),0) AS  南山局,\n"
				+ "NVL(SUM(CASE WHEN T.Handepname='盐田局' or T.Hanpardepname = '盐田局' THEN T.CNT END),0) AS  盐田局,\n"
				+ "NVL(SUM(CASE WHEN T.Handepname='龙岗局' or T.Hanpardepname = '龙岗局' THEN T.CNT END),0) AS  龙岗局,\n"
				+ "NVL(SUM(CASE WHEN T.Handepname='宝安局' or T.Hanpardepname = '宝安局' THEN T.CNT END),0) AS  宝安局,\n"
				+ "NVL(SUM(CASE WHEN T.Handepname='光明局' or T.Hanpardepname = '光明局' THEN T.CNT END),0) AS  光明局,\n"
				+ "NVL(SUM(CASE WHEN T.Handepname='坪山局' or T.Hanpardepname = '坪山局' THEN T.CNT END),0) AS  坪山局,\n"
				+ "NVL(SUM(CASE WHEN T.Handepname='龙华局' or T.Hanpardepname = '龙华局' THEN T.CNT END),0) AS  龙华局,\n"
				+ "NVL(SUM(CASE WHEN T.Handepname='大鹏局' or T.Hanpardepname = '大鹏局' THEN T.CNT END),0) AS  大鹏局,\n"
				+ "NVL(SUM(CASE WHEN T.Hanpardepname is not null  THEN T.CNT END),0) AS 总数\n"
				+ "  FROM (\n"
				+ "SELECT NVL(B.Persname,'NULL') AS persname ,A.Handepname,A.Hanpardepname, COUNT(1) AS CNT\n"
				+ "  FROM DC_CPR_INFOWARE A,DC_CPR_INFO_PROVIDER B\n"
				+ "  where A.Infproid = B.Infproid\n"
				+ "    and A.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "    and A.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "    and A.Ishotinform = '1'\n" + "    and A.Inftype in (";
		for (int i = 0; i < split.length; i++) {
			sql += "'" + split[i] + "'";
			if (i < split.length - 1) {
				sql += ",";
			}
		}
		sql += ")\n"
				+ " GROUP BY B.Persname,A.Handepname,A.Hanpardepname ) T\n"
				+ " GROUP BY rollup(T.persname)";
		try {
			list1 = dao.queryForList(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("热点投诉人咨询举报投诉件统计报表", "WDY",
				opreationtype == 1 ? "查看报表" : "下载报表", sql, beginTime + ","
						+ endTime + "," + checkType, req);
		return list1;
	}

	public List<Map> getHotManChe(String beginTime, String endTime,
			int opreationtype, HttpServletRequest req, String regcode) {
		List list = new ArrayList();
		List<Map> list1 = new ArrayList<Map>();
		StringBuffer sb = new StringBuffer();
		list.add(beginTime);
		list.add(endTime);
		sb.append(" SELECT  REPLACE(NVL(T.persname, '合计'), 'NULL', '  ') AS persname,\n"
				+ "NVL(SUM(CASE WHEN T.Handepname='市市场稽查局' or T.Hanpardepname = '市市场稽查局' THEN T.CNT END),0) AS  市市场稽查局,\n"
				+ "NVL(SUM(CASE WHEN T.Handepname='福田局' or T.Hanpardepname = '福田局' THEN T.CNT END),0) AS  福田局,\n"
				+ "NVL(SUM(CASE WHEN T.Handepname='罗湖局' or T.Hanpardepname = '罗湖局' THEN T.CNT END),0) AS  罗湖局,\n"
				+ "NVL(SUM(CASE WHEN T.Handepname='南山局' or T.Hanpardepname = '南山局' THEN T.CNT END),0) AS  南山局,\n"
				+ "NVL(SUM(CASE WHEN T.Handepname='盐田局' or T.Hanpardepname = '盐田局' THEN T.CNT END),0) AS  盐田局,\n"
				+ "NVL(SUM(CASE WHEN T.Handepname='龙岗局' or T.Hanpardepname = '龙岗局' THEN T.CNT END),0) AS  龙岗局,\n"
				+ "NVL(SUM(CASE WHEN T.Handepname='宝安局' or T.Hanpardepname = '宝安局' THEN T.CNT END),0) AS  宝安局,\n"
				+ "NVL(SUM(CASE WHEN T.Handepname='光明局' or T.Hanpardepname = '光明局' THEN T.CNT END),0) AS  光明局,\n"
				+ "NVL(SUM(CASE WHEN T.Handepname='坪山局' or T.Hanpardepname = '坪山局' THEN T.CNT END),0) AS  坪山局,\n"
				+ "NVL(SUM(CASE WHEN T.Handepname='龙华局' or T.Hanpardepname = '龙华局' THEN T.CNT END),0) AS  龙华局,\n"
				+ "NVL(SUM(CASE WHEN T.Handepname='大鹏局' or T.Hanpardepname = '大鹏局' THEN T.CNT END),0) AS  大鹏局,\n"
				+ "NVL(SUM(CASE WHEN (T.Hanpardepname is not null and T.Handepname is not null)  THEN T.CNT END),0) AS 总数\n"
				+ "  FROM (\n"
				+ "SELECT NVL(B.Persname,'NULL') AS persname ,A.Handepname,A.Hanpardepname, COUNT(1) AS CNT\n"
				+ "  FROM DC_CPR_INFOWARE A,DC_CPR_INFO_PROVIDER B,DC_CPR_INQUIRY C\n"
				+ "  where A.Infproid = B.Infproid\n"
				+ "    and A.Inquiryid = C.Inquiryid\n"
				+ "    and A.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "    and A.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "    and A.Inftype = '3'\n"
				+ "    and A.Keyword = 'cab8409e73d74a2d97739f93de1fbf0a'\n"
				+ "    and C.Businessrange = '29020000'\n");
		if (regcode != null && regcode.length() > 0) {
			String[] split = regcode.split(",");
			if (split.length > 1) {
				for (int i = 0, b = split.length; i < b; i++) {
					if (i == 0) {
						sb.append(" and (a.regdepcode = ? ");
						list.add(split[i]);
					} else if (i == b - 1) {
						sb.append(" or a.regdepcode= ? )\n");
						list.add(split[i]);
					} else {
						sb.append(" or a.regdepcode=? ");
						list.add(split[i]);
					}
				}
			} else {
				sb.append("and a.regdepcode= ? \n");
				list.add(regcode);
			}
		}
		sb.append(" GROUP BY B.Persname,A.Handepname,A.Hanpardepname ) T\n"
				+ " GROUP BY rollup(T.persname)");
		try {
			list1 = dao.queryForList(sb.toString(), list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("热点投诉人举报投诉撤诉统计报表", "WDY", opreationtype == 1 ? "查看报表"
				: "下载报表", sb.toString(), beginTime + "," + endTime + ","
				+ regcode, req);
		return list1;
	}

	public List<Map> getDjMoney(String beginTime, String endTime,
			int opreationtype, HttpServletRequest req) {
		List list = new ArrayList();
		List<Map> list1 = new ArrayList<Map>();
		list.add(beginTime);
		list.add(endTime);
		String sql = " SELECT  REPLACE(NVL(T.XFNAME, '合计'), 'NULL', '  ') AS 消费类型,\n"
				+ "NVL(SUM(CASE WHEN T.INVOAM is not null THEN T.INVOAM END),0) AS  涉及金额,\n"
				+ "NVL(SUM(CASE WHEN T.CASEVAL is not null THEN T.CASEVAL END),0) AS  案值,\n"
				+ "NVL(SUM(CASE WHEN T.ECOLOVAL is not null THEN T.ECOLOVAL END),0) AS  经济损失\n"
				+ "  FROM (\n"
				+ "SELECT NVL(D.NAME,'NULL') AS XFNAME ,B.INVOAM,C.CASEVAL,C.ECOLOVAL, COUNT(1) AS CNT\n"
				+ "  FROM DC_CPR_INFOWARE A,DC_CPR_involved_object B,DC_CPR_COMPLAINT C,dc_code.dc_12315_codedata D\n"
				+ "  where A.INVOBJID=B.INVOBJID\n"
				+ "    and A.COMPLAINTID=C.COMPLAINTID\n"
				+ "    and B.INVOBJTYPE = D.code\n"
				+ "    and D.codetable='CH20'\n"
				+ "    and A.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "    and A.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ " GROUP BY D.NAME,B.INVOAM,C.CASEVAL,C.ECOLOVAL ) T\n"
				+ " GROUP BY rollup(T.XFNAME)";
		try {
			list1 = dao.queryForList(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("登记信息涉及金额统计表", "WDY", opreationtype == 1 ? "查看报表"
				: "下载报表", sql, beginTime + "," + endTime, req);
		return list1;
	}

	public List<Map> getXwh(String beginTime, String endTime,
			int opreationtype, HttpServletRequest req) {
		List list = new ArrayList();
		List<Map> list1 = new ArrayList<Map>();
		list.add(beginTime);
		list.add(endTime);
		String sql = "SELECT  REPLACE(NVL(T.Regdepname, '合计'), 'NULL', ' ') AS 登记部门,\n"
				+ "        NVL(SUM(CASE WHEN T.Infoori='01' THEN T.CNT END),0) AS  直接到咨询举报申诉中心,\n"
				+ "        NVL(SUM(CASE WHEN T.Infoori='02' THEN T.CNT END),0) AS  维权联络点,\n"
				+ "        NVL(SUM(CASE WHEN T.Infoori='03' THEN T.CNT END),0) AS  消协,\n"
				+ "        NVL(SUM(CASE WHEN T.Infoori='04' THEN T.CNT END),0) AS  协作单位,\n"
				+ "        NVL(SUM(CASE WHEN T.Infoori='05' THEN T.CNT END),0) AS  销售企业,\n"
				+ "        NVL(SUM(CASE WHEN T.Infoori='06' THEN T.CNT END),0) AS  服务企业,\n"
				+ "        NVL(SUM(CASE WHEN T.Infoori='07' THEN T.CNT END),0) AS  生产企业,\n"
				+ "        NVL(SUM(CASE WHEN T.Infoori='08' THEN T.CNT END),0) AS  工商部门,\n"
				+ "        NVL(SUM(CASE WHEN T.Infoori='09' THEN T.CNT END),0) AS  人大,\n"
				+ "        NVL(SUM(CASE WHEN T.Infoori='10' THEN T.CNT END),0) AS  政协,\n"
				+ "        NVL(SUM(CASE WHEN T.Infoori='11' THEN T.CNT END),0) AS  其他行政部门,\n"
				+ "        NVL(SUM(CASE WHEN T.Infoori='99' THEN T.CNT END),0) AS  其他\n"
				+ "  FROM (SELECT NVL(A.Regdepname, 'NULL') AS Regdepname,A.Infoori, COUNT(1) AS CNT\n"
				+ "          FROM DC_CPR_INFOWARE A ,dc_code.dc_12315_codedata B\n"
				+ "          where A.Infoori = B.Code\n"
				+ "          and B.Codetable = 'CH02'\n"
				+ "          and A.Inftype ='6'\n"
				+ "          and A.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "          and A.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "         GROUP BY A.Regdepname,A.Infoori) T\n"
				+ " GROUP BY ROLLUP(T.Regdepname)";
		try {
			list1 = dao.queryForList(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("消委会登记情况表", "WDY", opreationtype == 1 ? "查看报表"
				: "下载报表", sql, beginTime + "," + endTime, req);
		return list1;
	}

	public List<Map> getXwhMoney(String beginTime, String endTime,
			int opreationtype, HttpServletRequest req) {
		List list = new ArrayList();
		List<Map> list1 = new ArrayList<Map>();
		list.add(beginTime);
		list.add(endTime);
		String sql = "SELECT  REPLACE(NVL(T.XFNAME, '合计'), 'NULL', ' ') AS 消费类型,\n"
				+ "NVL(SUM(CASE WHEN T.INVOAM is not null THEN T.INVOAM END),0) AS  涉及金额,\n"
				+ "NVL(SUM(CASE WHEN T.CASEVAL is not null THEN T.CASEVAL END),0) AS  案值,\n"
				+ "NVL(SUM(CASE WHEN T.ECOLOVAL is not null THEN T.ECOLOVAL END),0) AS  经济损失\n"
				+ "  FROM (\n"
				+ "SELECT NVL(D.NAME,'NULL') AS XFNAME ,B.INVOAM,C.CASEVAL,C.ECOLOVAL, COUNT(1) AS CNT\n"
				+ "  FROM DC_CPR_INFOWARE A,DC_CPR_involved_object B,DC_CPR_COMPLAINT C,dc_code.dc_12315_codedata D\n"
				+ "  where A.INVOBJID=B.INVOBJID\n"
				+ "    and A.COMPLAINTID=C.COMPLAINTID\n"
				+ "    and B.INVOBJTYPE = D.code\n"
				+ "    and D.codetable='ZH18'\n"
				+ "    and A.Inftype = '6'\n"
				+ "    and A.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "    and A.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ " GROUP BY D.NAME,B.INVOAM,C.CASEVAL,C.ECOLOVAL ) T\n"
				+ " GROUP BY rollup(T.XFNAME)";
		try {
			list1 = dao.queryForList(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("消委会登记信息涉及金额统计表", "WDY", opreationtype == 1 ? "查看报表"
				: "下载报表", sql, beginTime + "," + endTime, req);
		return list1;
	}

	public List<Map> touSuBanJieTj(String beginTime, String endTime,
			String regionCheck, int opreationtype, HttpServletRequest req) {
		List list = new ArrayList();
		List<Map> list1 = new ArrayList<Map>();
		String sql = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String tBeginTime = null;// 同比开始时间
		String tEndTime = null;// 同比结束时间
		try {
			cal.setTime(sdf.parse(beginTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.add(Calendar.YEAR, -1);//
		tBeginTime = sdf.format(cal.getTime());
		try {
			cal.setTime(sdf.parse(endTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.add(Calendar.YEAR, -1);//
		tEndTime = sdf.format(cal.getTime());
		list.add(beginTime);
		list.add(endTime);
		list.add(beginTime);
		list.add(endTime);
		list.add(tBeginTime);
		list.add(tEndTime);

		if (regionCheck == null || regionCheck.length() == 0
				|| "1".equals(regionCheck)) {
			// 默认区局
			sql = "--消费者投诉举报办结情况一览表(区局)\n"
					+ "SELECT\n"
					+ "REPLACE(NVL(T.hanpardepname, '合计'), 'NULL', '') AS 承办单位,\n"
					+ "NVL(SUM(case when t.flag='1' then T.CNT end),0)  AS 承办本期,\n"
					+ "NVL(SUM(CASE WHEN  t.flag='0' THEN T.CNT END),0) AS 同比,\n"
					+ "NVL(SUM(CASE WHEN T.Inftype ='1'  and t.flag='1' THEN T.CNT END),0) AS  投诉承办,\n"
					+ "NVL(SUM(CASE WHEN T.Inftype  ='1' AND T.FINISHTIME is not null and t.flag='1' THEN T.CNT END),0) AS  投诉已办结,--\n"
					+ "(case when NVL(SUM(CASE WHEN T.Inftype ='1'  and t.flag='1'  THEN T.CNT END),0)=0\n"
					+ " then 0 else\n"
					+ " round((NVL(SUM(CASE WHEN T.Inftype  ='1'  and t.flag='1' AND T.FINISHTIME is not null THEN T.CNT END),0)/\n"
					+ " NVL(SUM(CASE WHEN T.Inftype ='1' and t.flag='1'   THEN T.CNT END),0)),4)end)*100 ||'%' AS 投诉办结率,\n"
					+ "\n"
					+ "NVL(SUM(CASE WHEN T.Inftype  ='1' and t.flag='1'  AND  T.MEDIATE_RESULT = '1' THEN T.CNT END),0) AS  投诉调解成功数,\n"
					+ "(case when NVL(SUM(CASE WHEN T.Inftype ='1' and t.flag='1'   THEN T.CNT END),0)=0\n"
					+ " then 0 else\n"
					+ " round(NVL(SUM(CASE WHEN T.Inftype  ='1' and t.flag='1'  AND T.MEDIATE_RESULT = '1' THEN T.CNT END),0)/\n"
					+ "NVL(SUM(CASE WHEN T.Inftype ='1'  and t.flag='1'  THEN T.CNT END),0),4)end)*100||'%' AS 投诉调解成功率,\n"
					+ "\n"
					+ "NVL(SUM(CASE WHEN T.Inftype  ='1'  and t.flag='1' AND T.PUTONCASE = '1' THEN T.CNT END),0) AS  投诉诉转案件,\n"
					+ "(case when NVL(SUM(CASE WHEN T.Inftype ='1' and t.flag='1'   THEN T.CNT END),0)=0\n"
					+ " then 0 else\n"
					+ " round((NVL(SUM(CASE WHEN T.Inftype  ='1' and t.flag='1'  AND T.PUTONCASE = '1' THEN T.CNT END),0)/\n"
					+ " NVL(SUM(CASE WHEN T.Inftype ='1'  and t.flag='1'  THEN T.CNT END),0)),4)end)*100||'%' AS 投诉立案率,\n"
					+ "NVL(SUM(CASE WHEN T.Inftype='2'  and t.flag='1' then T.CNT END),0) AS  举报承办,\n"
					+ "NVL(SUM(CASE WHEN T.Inftype='2' and t.flag='1'  AND T.FINISHTIME is not null THEN T.CNT END),0) AS  举报已办结,\n"
					+ "(case when NVL(SUM(CASE WHEN T.Inftype='2' and t.flag='1'  then T.CNT END),0)=0\n"
					+ " then 0 else\n"
					+ " round((NVL(SUM(CASE WHEN T.Inftype = '2' and t.flag='1'  AND T.FINISHTIME is not null THEN T.CNT END),0)/\n"
					+ " NVL(SUM(CASE WHEN T.Inftype='2' and t.flag='1'  then T.CNT END),0)),4)end)*100||'%' AS 举报办结率,\n"
					+ "NVL(SUM(CASE WHEN T.Inftype='2' and t.flag='1'  AND T.PUTONCASE = '1' THEN T.CNT END),0) AS 举报立案数,\n"
					+ "(case when NVL(SUM(CASE WHEN T.Inftype='2' and t.flag='1'  then T.CNT END),0)=0\n"
					+ " then 0 else\n"
					+ " round((NVL(SUM(CASE WHEN T.Inftype='2' and t.flag='1'  AND T.PUTONCASE = '1' THEN T.CNT END),0)/\n"
					+ " NVL(SUM(CASE WHEN T.Inftype='2' and t.flag='1'  then T.CNT END),0)),4)end)*100||'%' AS 举报立案数率\n"
					+ "  FROM (select s.*,\n"
					+ "       (case when s.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
					+ "             and  s.REGTIME <= to_date(?,'yyyy-mm-dd') then '1'\n"
					+ "             else '0' end) as flag from  (\n"
					+ "SELECT NVL(A.hanpardepname,'NULL') AS hanpardepname,A.Inftype,D.PRESTATENAME,A.FINISHTIME,A.REGTIME,C.MEDIATE_RESULT,B.PUTONCASE,COUNT(1) AS CNT\n"
					+ "  FROM DC_CPR_INFOWARE A,DC_CPR_FEEDBACK B,DC_CPR_MEDIATION C,DC_CPR_PROCESS_STEP D\n"
					+ "  where A.FEEDBACKID=B. FEEDBACKID\n"
					+ "    and B.MEDIATIONID=C.MEDIATIONID\n"
					+ "    and A．PROINSID = D.PROINSID\n"
					+ "    and a.inftype in ('1','2')\n"
					+ "    and ((A.REGTIME >= to_date(?,'yyyy-mm-dd') and  A.REGTIME <= to_date(?,'yyyy-mm-dd'))\n"
					+ "    or (A.REGTIME >= to_date(?,'yyyy-mm-dd') and  A.REGTIME < to_date(?,'yyyy-mm-dd')))\n"
					+ "    and a.hanpardepname is not null\n"
					+ " GROUP BY A.hanpardepname,A.Inftype,D.PRESTATENAME,A.FINISHTIME,A.REGTIME,C.MEDIATE_RESULT,B.PUTONCASE) s) T\n"
					+ " GROUP BY rollup(T.hanpardepname)";

		} else {
			sql =

			"--消费者投诉举报办结情况一览表（科所）\n"
					+ "SELECT\n"
					+ "REPLACE(NVL(T.handepname, '合计'), 'NULL', '') AS 承办单位,\n"
					+ "NVL(SUM(case when t.flag='1'then T.CNT end),0)  AS 承办本期,\n"
					+ "NVL(SUM(CASE WHEN  t.flag='0'THEN T.CNT END),0) AS 同比,\n"
					+ "NVL(SUM(CASE WHEN T.Inftype ='1' and t.flag='1'  THEN T.CNT END),0) AS  投诉承办,\n"
					+ "NVL(SUM(CASE WHEN T.Inftype  ='1' and t.flag='1' AND T.FINISHTIME is not null THEN T.CNT END),0) AS  投诉已办结,--\n"
					+ "(case when NVL(SUM(CASE WHEN T.Inftype ='1' and t.flag='1'  THEN T.CNT END),0)=0\n"
					+ " then 0 else\n"
					+ " round((NVL(SUM(CASE WHEN T.Inftype  ='1' and t.flag='1' AND T.FINISHTIME is not null THEN T.CNT END),0)/\n"
					+ " NVL(SUM(CASE WHEN T.Inftype ='1'  and t.flag='1' THEN T.CNT END),0)),4)end)*100 ||'%' AS 投诉办结率,\n"
					+ "\n"
					+ "NVL(SUM(CASE WHEN T.Inftype  ='1' and t.flag='1' AND T.MEDIATE_RESULT = '1' THEN T.CNT END),0) AS  投诉调解成功数,\n"
					+ "(case when NVL(SUM(CASE WHEN T.Inftype ='1' and t.flag='1'  THEN T.CNT END),0)=0\n"
					+ " then 0 else\n"
					+ " round(NVL(SUM(CASE WHEN T.Inftype  ='1' and t.flag='1' AND T.MEDIATE_RESULT = '1' THEN T.CNT END),0)/\n"
					+ "NVL(SUM(CASE WHEN T.Inftype ='1'  and t.flag='1' THEN T.CNT END),0),4)end)*100||'%' AS 投诉调解成功率,\n"
					+ "\n"
					+ "NVL(SUM(CASE WHEN T.Inftype  ='1' and t.flag='1' AND T.PUTONCASE = '1' THEN T.CNT END),0) AS  投诉诉转案件,\n"
					+ "(case when NVL(SUM(CASE WHEN T.Inftype ='1' and t.flag='1'  THEN T.CNT END),0)=0\n"
					+ " then 0 else\n"
					+ " round((NVL(SUM(CASE WHEN T.Inftype  ='1' and t.flag='1' AND T.PUTONCASE = '1' THEN T.CNT END),0)/\n"
					+ " NVL(SUM(CASE WHEN T.Inftype ='1'  and t.flag='1' THEN T.CNT END),0)),4)end)*100||'%' AS 投诉立案率,\n"
					+ "NVL(SUM(CASE WHEN T.Inftype='2' and t.flag='1' then T.CNT END),0) AS  举报承办,\n"
					+ "NVL(SUM(CASE WHEN T.Inftype='2'  and t.flag='1' AND T.FINISHTIME is not null THEN T.CNT END),0) AS  举报已办结,\n"
					+ "(case when NVL(SUM(CASE WHEN T.Inftype='2'  and t.flag='1' then T.CNT END),0)=0\n"
					+ " then 0 else\n"
					+ " round((NVL(SUM(CASE WHEN T.Inftype = '2'  and t.flag='1' AND T.FINISHTIME is not null THEN T.CNT END),0)/\n"
					+ " NVL(SUM(CASE WHEN T.Inftype='2'and t.flag='1'  then T.CNT END),0)),4)end)*100||'%' AS 举报办结率,\n"
					+ "NVL(SUM(CASE WHEN T.Inftype='2' and t.flag='1' AND T.PUTONCASE = '1' THEN T.CNT END),0) AS 举报立案数,\n"
					+ "(case when NVL(SUM(CASE WHEN T.Inftype='2' and t.flag='1' then T.CNT END),0)=0\n"
					+ " then 0 else\n"
					+ " round((NVL(SUM(CASE WHEN T.Inftype='2'and t.flag='1'  AND T.PUTONCASE = '1' THEN T.CNT END),0)/\n"
					+ " NVL(SUM(CASE WHEN T.Inftype='2'and t.flag='1'  then T.CNT END),0)),4)end)*100||'%' AS 举报立案数率\n"
					+ "  FROM (select s.*,\n"
					+ "       (case when s.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
					+ "             and  s.REGTIME <= to_date(?,'yyyy-mm-dd') then '1'\n"
					+ "             else '0' end) as flag from  (\n"
					+ "SELECT NVL(A.handepname,'NULL') AS handepname,A.Inftype,D.PRESTATENAME,A.FINISHTIME,A.REGTIME,C.MEDIATE_RESULT,B.PUTONCASE,COUNT(1) AS CNT\n"
					+ "  FROM DC_CPR_INFOWARE A,DC_CPR_FEEDBACK B,DC_CPR_MEDIATION C,DC_CPR_PROCESS_STEP D\n"
					+ "  where A.FEEDBACKID=B. FEEDBACKID\n"
					+ "    and B.MEDIATIONID=C.MEDIATIONID\n"
					+ "    and A．PROINSID = D.PROINSID\n"
					+ "    and a.inftype in ('1','2')\n"
					+ "    and ((A.REGTIME >= to_date(?,'yyyy-mm-dd') and  A.REGTIME <= to_date(?,'yyyy-mm-dd'))\n"
					+ "    or (A.REGTIME >= to_date(?,'yyyy-mm-dd') and  A.REGTIME < to_date(?,'yyyy-mm-dd')))\n"
					+ "    and a.hanpardepname is not null\n"
					+ " GROUP BY A.handepname,A.Inftype,D.PRESTATENAME,A.FINISHTIME,A.REGTIME,C.MEDIATE_RESULT,B.PUTONCASE) s) T\n"
					+ " GROUP BY rollup(T.handepname)";
		}
		try {
			list1 = dao.queryForList(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("20xx年 xx月消费者投诉举报办结情况一览表", "WDY",
				opreationtype == 1 ? "查看报表" : "下载报表", sql, tBeginTime + ","
						+ tEndTime + "," + beginTime + "," + endTime, req);
		return list1;
	}

	public List<Map> getRegCode() {
		List<Map> list = new ArrayList<Map>();
		String sql = "select b.regdepcode,c.name from  (select a.regdepcode regdepcode from dc_dc.dc_cpr_infoware a group by a.regdepcode) b left join db_yyjc.jc_public_department c on b.regdepcode=c.code  where b.regdepcode<>'6100'  order by c.parent_code";
		try {
			list = dao.queryForList(sql, null);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<List<Map>> touSuReDianTj(String beginTime, String endTime,
			String regionCheck, int i, HttpServletRequest req) {
		List list = new ArrayList();
		List<Map> list1 = new ArrayList<Map>();
		List<List<Map>> list2 = new ArrayList<List<Map>>();
		String sql = null;
		list.add(beginTime);
		list.add(endTime);
		list.add(beginTime);
		list.add(endTime);
		if (regionCheck != null && regionCheck.length() != 0
				&& "2".equals(regionCheck)) {
			sql = "SELECT A.bumen as 承办区局,\n"
					+ "       A.NAME as 投诉类别热点,\n"
					+ "       A.CNT as 数量,\n"
					+ "       B.NAME as 投诉行业热点,\n"
					+ "       B.CNT as 数量1\n"
					+ "  FROM (SELECT P.bumen, P.NAME, P.CNT, P.ROW_CNT\n"
					+ "          FROM (SELECT S.bumen,\n"
					+ "                       S.NAME,\n"
					+ "                       S.CNT,\n"
					+ "                       ROW_NUMBER() OVER(PARTITION BY S.bumen ORDER BY CNT DESC) ROW_CNT\n"
					+ "                  FROM (select bb.bumen,bb.name,bb.cnt from (SELECT\n"
					+ "                               (case when substrb(D.code,1,2)='01' then '质量'\n"
					+ "                                     when substrb(D.code,1,2)='02' then '广告'\n"
					+ "                                     when substrb(D.code,1,2)='03' then '商标'\n"
					+ "                                     when substrb(D.code,1,2)='04' then '不正当竞争及限制竞争'\n"
					+ "                                     when substrb(D.code,1,2)='05' then '无照经营'\n"
					+ "                                     when substrb(D.code,1,2)='06' then '传销'\n"
					+ "                                     when substrb(D.code,1,2)='07' then '合同'\n"
					+ "                                     when substrb(D.code,1,2)='08' then '包装标识'\n"
					+ "                                     when substrb(D.code,1,2)='09' then '计量'\n"
					+ "                                     when substrb(D.code,1,2)='10' then '价格'\n"
					+ "                                     when substrb(D.code,1,2)='11' then '人身权利'\n"
					+ "                                     when substrb(D.code,1,2)='12' then '售后服务'\n"
					+ "                                     when substrb(D.code,1,2)='13' then '假冒'\n"
					+ "                                     when substrb(D.code,1,2)='14' then '其它违反登记管理行为'\n"
					+ "                                     when substrb(D.code,1,2)='49' then '违反控烟条例'\n"
					+ "                                     when substrb(D.code,1,2)='51' then '电梯类'\n"
					+ "                                     when substrb(D.code,1,2)='52' then '压力容器类'\n"
					+ "                                     when substrb(D.code,1,2)='53' then '食药监投诉举报环节'\n"
					+ "                                      end) AS NAME,\n"
					+ "                                    T.HANDEPNAME AS bumen,\n"
					+ "                               COUNT(1) AS CNT\n"
					+ "                          FROM DC_CPR_INFOWARE T, DC_CODE.DC_12315_CODEDATA D\n"
					+ "                         WHERE T.APPLBASQUE = D.CODE\n"
					+ "                           AND D.CODETABLE = 'CH27'\n"
					+ "                           and t.APPLBASQUE is not null\n"
					+ "                           and T.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
					+ "                           and T.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
					+ "                         GROUP BY substrb(D.code,1,2), T.HANDEPNAME) bb\n"
					+ "                                       where bb.name is not null) S) P\n"
					+ "         WHERE P.ROW_CNT <= 3\n"
					+ "           and P.bumen is not null) A,\n"
					+ "       (SELECT P.bumen, P.NAME, P.CNT, P.ROW_CNT\n"
					+ "          FROM (SELECT S.bumen,\n"
					+ "                       S.NAME,\n"
					+ "                       S.CNT,\n"
					+ "                       ROW_NUMBER() OVER(PARTITION BY S.bumen ORDER BY CNT DESC) ROW_CNT\n"
					+ "                  FROM (select aa.bumen,aa.name,aa.cnt from (SELECT  T.HANDEPNAME AS bumen,\n"
					+ "                               (case when substrb(D.code,1,3)='101' then '食品'\n"
					+ "                                     when substrb(D.code,1,3)='103' then '烟、酒和饮料'\n"
					+ "                                     when substrb(D.code,1,3)='105' then '保健品'\n"
					+ "                                     when substrb(D.code,1,3)='108' then '药品'\n"
					+ "                                     when substrb(D.code,1,3)='110' then '医疗器械'\n"
					+ "                                     when substrb(D.code,1,3)='114' then '化妆品'\n"
					+ "                                     when substrb(D.code,1,3)='117' then '服装、鞋帽'\n"
					+ "                                     when substrb(D.code,1,3)='119' then '布料、毛线'\n"
					+ "                                     when substrb(D.code,1,3)='121' then '家居用品'\n"
					+ "                                     when substrb(D.code,1,3)='124' then '儿童用品'\n"
					+ "                                     when substrb(D.code,1,3)='127' then '家用电器'\n"
					+ "                                     when substrb(D.code,1,3)='131' then '计算机产品'\n"
					+ "                                     when substrb(D.code,1,3)='134' then '通讯产品'\n"
					+ "                                     when substrb(D.code,1,3)='138' then '房屋'\n"
					+ "                                     when substrb(D.code,1,3)='141' then '装修建材'\n"
					+ "                                     when substrb(D.code,1,3)='145' then '照摄像产品'\n"
					+ "                                     when substrb(D.code,1,3)='148' then '卫生用品'\n"
					+ "                                     when substrb(D.code,1,3)='150' then '出版物'\n"
					+ "                                     when substrb(D.code,1,3)='153' then '文化、运动用品'\n"
					+ "                                     when substrb(D.code,1,3)='156' then '宠物及宠物用品'\n"
					+ "                                     when substrb(D.code,1,3)='159' then '首饰'\n"
					+ "                                     when substrb(D.code,1,3)='162' then '五金交电'\n"
					+ "                                     when substrb(D.code,1,3)='165' then '交通工具'\n"
					+ "                                     when substrb(D.code,1,3)='168' then '燃料'\n"
					+ "                                     when substrb(D.code,1,3)='170' then '殡葬用品'\n"
					+ "                                     when substrb(D.code,1,3)='173' then '农资用品'\n"
					+ "                                     when substrb(D.code,1,3)='180' then '保健食品'\n"
					+ "                                 end) AS NAME,\n"
					+ "                               COUNT(1) AS CNT\n"
					+ "                          FROM DC_CPR_INFOWARE           T,\n"
					+ "                               DC_CODE.DC_12315_CODEDATA D,\n"
					+ "                               DC_CPR_INVOLVED_OBJECT    B\n"
					+ "                         WHERE D.CODETABLE = 'CH20'\n"
					+ "                           AND T.INVOBJID = B.INVOBJID\n"
					+ "                           AND B.INVOBJTYPE = D.CODE\n"
					+ "                           AND T.INFTYPE IN ('1', '2', '6')\n"
					+ "                           and b.INVOBJTYPE is not null\n"
					+ "                           and T.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
					+ "                           and T.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
					+ "                         GROUP BY  T.HANDEPNAME , substrb(D.code,1,3)) aa\n"
					+ "                                       where aa.name is not null) S) P\n"
					+ "         WHERE P.ROW_CNT <= 3\n"
					+ "           and P.bumen is not null) B\n"
					+ " WHERE A.bumen = B.bumen\n"
					+ "   AND A.ROW_CNT = B.ROW_CNT\n" + "";
			String sql1 = "select HANDEPNAME as 承办区局 from dc_cpr_INFOWARE\n"
					+ "   where REGTIME >= to_date(?,'yyyy-mm-dd')\n"
					+ "   and REGTIME <= to_date(?,'yyyy-mm-dd')\n"
					+ "   and REGTIME >= to_date(?,'yyyy-mm-dd')\n"
					+ "   and REGTIME <= to_date(?,'yyyy-mm-dd')\n"
					+ "   and HANDEPNAME is not null\n"
					+ "   group by HANDEPNAME";
			List<Map> list4 = new ArrayList<Map>();
			try {
				list1 = dao.queryForList(sql, list);
				list4 = dao.queryForList(sql1, list);
			} catch (OptimusException e) {
				e.printStackTrace();
			}
			for (int j = 0; j < list4.size(); j++) {
				List<Map> list3 = new ArrayList<Map>();
				for (int j2 = 0; j2 < list1.size(); j2++) {
					if (list1.get(j2).get("承办区局")
							.equals(list4.get(j).get("承办区局"))) {
						list3.add(list1.get(j2));
					}
				}
				list2.add(list3);
			}
			logop.logInfoYeWu("各辖区投诉举报热点排名一览表", "WDY",
					i == 1 ? "查看报表" : "下载报表", sql, beginTime + "," + endTime
							+ ",（区局=1，科所=2）" + regionCheck, req);
			return list2;
		} else {
			sql = "SELECT A.bumen as 承办区局,\n"
					+ "       A.NAME as 投诉类别热点,\n"
					+ "       A.CNT as 数量,\n"
					+ "       B.NAME as 投诉行业热点,\n"
					+ "       B.CNT as 数量1\n"
					+ "  FROM (SELECT P.bumen, P.NAME, P.CNT, P.ROW_CNT\n"
					+ "          FROM (SELECT S.bumen,\n"
					+ "                       S.NAME,\n"
					+ "                       S.CNT,\n"
					+ "                       ROW_NUMBER() OVER(PARTITION BY S.bumen ORDER BY CNT DESC) ROW_CNT\n"
					+ "                  FROM (select bb.bumen,bb.name,bb.cnt from (SELECT\n"
					+ "                               (case when substrb(D.code,1,2)='01' then '质量'\n"
					+ "                                     when substrb(D.code,1,2)='02' then '广告'\n"
					+ "                                     when substrb(D.code,1,2)='03' then '商标'\n"
					+ "                                     when substrb(D.code,1,2)='04' then '不正当竞争及限制竞争'\n"
					+ "                                     when substrb(D.code,1,2)='05' then '无照经营'\n"
					+ "                                     when substrb(D.code,1,2)='06' then '传销'\n"
					+ "                                     when substrb(D.code,1,2)='07' then '合同'\n"
					+ "                                     when substrb(D.code,1,2)='08' then '包装标识'\n"
					+ "                                     when substrb(D.code,1,2)='09' then '计量'\n"
					+ "                                     when substrb(D.code,1,2)='10' then '价格'\n"
					+ "                                     when substrb(D.code,1,2)='11' then '人身权利'\n"
					+ "                                     when substrb(D.code,1,2)='12' then '售后服务'\n"
					+ "                                     when substrb(D.code,1,2)='13' then '假冒'\n"
					+ "                                     when substrb(D.code,1,2)='14' then '其它违反登记管理行为'\n"
					+ "                                     when substrb(D.code,1,2)='49' then '违反控烟条例'\n"
					+ "                                     when substrb(D.code,1,2)='51' then '电梯类'\n"
					+ "                                     when substrb(D.code,1,2)='52' then '压力容器类'\n"
					+ "                                     when substrb(D.code,1,2)='53' then '食药监投诉举报环节'\n"
					+ "                                      end) AS NAME,\n"
					+ "                                     (case when T.HANDEPNAME = '福田局' or T.Hanpardepname = '福田局' then '福田局'\n"
					+ "                                     when T.HANDEPNAME = '罗湖局' or T.Hanpardepname = '罗湖局' then '罗湖局'\n"
					+ "                                     when T.HANDEPNAME = '南山局' or T.Hanpardepname = '南山局' then '南山局'\n"
					+ "                                     when T.HANDEPNAME = '盐田局' or T.Hanpardepname = '盐田局' then '盐田局'\n"
					+ "                                     when T.HANDEPNAME = '宝安局' or T.Hanpardepname = '宝安局' then '宝安局'\n"
					+ "                                     when T.HANDEPNAME = '龙岗局' or T.Hanpardepname = '龙岗局' then '龙岗局'\n"
					+ "                                     when T.HANDEPNAME = '光明局' or T.Hanpardepname = '光明局' then '光明局'\n"
					+ "                                     when T.HANDEPNAME = '坪山局' or T.Hanpardepname = '坪山局' then '坪山局'\n"
					+ "                                     when T.HANDEPNAME = '龙华局' or T.Hanpardepname = '龙华局' then '龙华局'\n"
					+ "                                     when T.HANDEPNAME = '大鹏局' or T.Hanpardepname = '大鹏局' then '大鹏局'end) AS bumen,\n"
					+ "                               COUNT(1) AS CNT\n"
					+ "                          FROM DC_CPR_INFOWARE T, DC_CODE.DC_12315_CODEDATA D\n"
					+ "                         WHERE T.APPLBASQUE = D.CODE\n"
					+ "                           AND D.CODETABLE = 'CH27'\n"
					+ "                           and t.APPLBASQUE is not null\n"
					+ "                           and T.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
					+ "                           and T.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
					+ "                         GROUP BY substrb(D.code,1,2),(case when T.HANDEPNAME = '福田局' or T.Hanpardepname = '福田局' then '福田局'\n"
					+ "                                     when T.HANDEPNAME = '罗湖局' or T.Hanpardepname = '罗湖局' then '罗湖局'\n"
					+ "                                     when T.HANDEPNAME = '南山局' or T.Hanpardepname = '南山局' then '南山局'\n"
					+ "                                     when T.HANDEPNAME = '盐田局' or T.Hanpardepname = '盐田局' then '盐田局'\n"
					+ "                                     when T.HANDEPNAME = '宝安局' or T.Hanpardepname = '宝安局' then '宝安局'\n"
					+ "                                     when T.HANDEPNAME = '龙岗局' or T.Hanpardepname = '龙岗局' then '龙岗局'\n"
					+ "                                     when T.HANDEPNAME = '光明局' or T.Hanpardepname = '光明局' then '光明局'\n"
					+ "                                     when T.HANDEPNAME = '坪山局' or T.Hanpardepname = '坪山局' then '坪山局'\n"
					+ "                                     when T.HANDEPNAME = '龙华局' or T.Hanpardepname = '龙华局' then '龙华局'\n"
					+ "                                     when T.HANDEPNAME = '大鹏局' or T.Hanpardepname = '大鹏局' then '大鹏局'end)) bb\n"
					+ "                                       where bb.name is not null) S) P\n"
					+ "         WHERE P.ROW_CNT <= 3\n"
					+ "           and P.bumen is not null) A,\n"
					+ "       (SELECT P.bumen, P.NAME, P.CNT, P.ROW_CNT\n"
					+ "          FROM (SELECT S.bumen,\n"
					+ "                       S.NAME,\n"
					+ "                       S.CNT,\n"
					+ "                       ROW_NUMBER() OVER(PARTITION BY S.bumen ORDER BY CNT DESC) ROW_CNT\n"
					+ "                  FROM (select aa.bumen,aa.name,aa.cnt from (SELECT (case when T.HANDEPNAME = '福田局' or T.Hanpardepname = '福田局' then '福田局'\n"
					+ "                                     when T.HANDEPNAME = '罗湖局' or T.Hanpardepname = '罗湖局' then '罗湖局'\n"
					+ "                                     when T.HANDEPNAME = '南山局' or T.Hanpardepname = '南山局' then '南山局'\n"
					+ "                                     when T.HANDEPNAME = '盐田局' or T.Hanpardepname = '盐田局' then '盐田局'\n"
					+ "                                     when T.HANDEPNAME = '宝安局' or T.Hanpardepname = '宝安局' then '宝安局'\n"
					+ "                                     when T.HANDEPNAME = '龙岗局' or T.Hanpardepname = '龙岗局' then '龙岗局'\n"
					+ "                                     when T.HANDEPNAME = '光明局' or T.Hanpardepname = '光明局' then '光明局'\n"
					+ "                                     when T.HANDEPNAME = '坪山局' or T.Hanpardepname = '坪山局' then '坪山局'\n"
					+ "                                     when T.HANDEPNAME = '龙华局' or T.Hanpardepname = '龙华局' then '龙华局'\n"
					+ "                                     when T.HANDEPNAME = '大鹏局' or T.Hanpardepname = '大鹏局' then '大鹏局'end) AS bumen,\n"
					+ "                               (case when substrb(D.code,1,3)='101' then '食品'\n"
					+ "                                     when substrb(D.code,1,3)='103' then '烟、酒和饮料'\n"
					+ "                                     when substrb(D.code,1,3)='105' then '保健品'\n"
					+ "                                     when substrb(D.code,1,3)='108' then '药品'\n"
					+ "                                     when substrb(D.code,1,3)='110' then '医疗器械'\n"
					+ "                                     when substrb(D.code,1,3)='114' then '化妆品'\n"
					+ "                                     when substrb(D.code,1,3)='117' then '服装、鞋帽'\n"
					+ "                                     when substrb(D.code,1,3)='119' then '布料、毛线'\n"
					+ "                                     when substrb(D.code,1,3)='121' then '家居用品'\n"
					+ "                                     when substrb(D.code,1,3)='124' then '儿童用品'\n"
					+ "                                     when substrb(D.code,1,3)='127' then '家用电器'\n"
					+ "                                     when substrb(D.code,1,3)='131' then '计算机产品'\n"
					+ "                                     when substrb(D.code,1,3)='134' then '通讯产品'\n"
					+ "                                     when substrb(D.code,1,3)='138' then '房屋'\n"
					+ "                                     when substrb(D.code,1,3)='141' then '装修建材'\n"
					+ "                                     when substrb(D.code,1,3)='145' then '照摄像产品'\n"
					+ "                                     when substrb(D.code,1,3)='148' then '卫生用品'\n"
					+ "                                     when substrb(D.code,1,3)='150' then '出版物'\n"
					+ "                                     when substrb(D.code,1,3)='153' then '文化、运动用品'\n"
					+ "                                     when substrb(D.code,1,3)='156' then '宠物及宠物用品'\n"
					+ "                                     when substrb(D.code,1,3)='159' then '首饰'\n"
					+ "                                     when substrb(D.code,1,3)='162' then '五金交电'\n"
					+ "                                     when substrb(D.code,1,3)='165' then '交通工具'\n"
					+ "                                     when substrb(D.code,1,3)='168' then '燃料'\n"
					+ "                                     when substrb(D.code,1,3)='170' then '殡葬用品'\n"
					+ "                                     when substrb(D.code,1,3)='173' then '农资用品'\n"
					+ "                                     when substrb(D.code,1,3)='180' then '保健食品'\n"
					+ "                                 end) AS NAME,\n"
					+ "                               COUNT(1) AS CNT\n"
					+ "                          FROM DC_CPR_INFOWARE           T,\n"
					+ "                               DC_CODE.DC_12315_CODEDATA D,\n"
					+ "                               DC_CPR_INVOLVED_OBJECT    B\n"
					+ "                         WHERE D.CODETABLE = 'CH20'\n"
					+ "                           AND T.INVOBJID = B.INVOBJID\n"
					+ "                           AND B.INVOBJTYPE = D.CODE\n"
					+ "                           AND T.INFTYPE IN ('1', '2', '6')\n"
					+ "                           and b.INVOBJTYPE is not null\n"
					+ "                           and T.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
					+ "                           and T.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
					+ "                         GROUP BY (case when T.HANDEPNAME = '福田局' or T.Hanpardepname = '福田局' then '福田局'\n"
					+ "                                     when T.HANDEPNAME = '罗湖局' or T.Hanpardepname = '罗湖局' then '罗湖局'\n"
					+ "                                     when T.HANDEPNAME = '南山局' or T.Hanpardepname = '南山局' then '南山局'\n"
					+ "                                     when T.HANDEPNAME = '盐田局' or T.Hanpardepname = '盐田局' then '盐田局'\n"
					+ "                                     when T.HANDEPNAME = '宝安局' or T.Hanpardepname = '宝安局' then '宝安局'\n"
					+ "                                     when T.HANDEPNAME = '龙岗局' or T.Hanpardepname = '龙岗局' then '龙岗局'\n"
					+ "                                     when T.HANDEPNAME = '光明局' or T.Hanpardepname = '光明局' then '光明局'\n"
					+ "                                     when T.HANDEPNAME = '坪山局' or T.Hanpardepname = '坪山局' then '坪山局'\n"
					+ "                                     when T.HANDEPNAME = '龙华局' or T.Hanpardepname = '龙华局' then '龙华局'\n"
					+ "                                     when T.HANDEPNAME = '大鹏局' or T.Hanpardepname = '大鹏局' then '大鹏局'end), substrb(D.code,1,3)) aa\n"
					+ "                                       where aa.name is not null) S) P\n"
					+ "         WHERE P.ROW_CNT <= 3\n"
					+ "           and P.bumen is not null) B\n"
					+ " WHERE A.bumen = B.bumen\n"
					+ "   AND A.ROW_CNT = B.ROW_CNT\n" + "";
			try {
				list1 = dao.queryForList(sql, list);
			} catch (OptimusException e) {
				e.printStackTrace();
			}
			for (int j = 0; j < regs.length; j++) {
				List<Map> list3 = new ArrayList<Map>();
				for (int j2 = 0; j2 < list1.size(); j2++) {
					if (list1.get(j2).get("承办区局").equals(regs[j])) {
						list3.add(list1.get(j2));
					}
				}
				list2.add(list3);
			}
			logop.logInfoYeWu("各辖区投诉举报热点排名一览表", "WDY",
					i == 1 ? "查看报表" : "下载报表", sql, beginTime + "," + endTime
							+ ",（区局=1，科所=2）" + regionCheck, req);
			return list2;

		}

	}

	public List<Map> touSuYiDong(String beginTime, String endTime,
			String hBeginTime, String hEndTime, String checkType,
			String regionCheck, int i, HttpServletRequest req) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String hBeginTimeBackup = null;// 环比开始时间
		String hEndTimeBackup = null;// 环比结束时间
		String tBeginTime = null;// 同比开始时间
		String tEndTime = null;// 同比结束时间
		String types = null;
		// 前台没有同比时间的值得时候，默认环比时间为上个月同一时间段
		if (hBeginTime == null || hBeginTime.length() == 0) {
			try {
				cal.setTime(sdf.parse(beginTime));
				cal.add(Calendar.MONTH, -1);//
			} catch (ParseException e) {
				e.printStackTrace();
			}
			hBeginTimeBackup = sdf.format(cal.getTime());
		} else {
			hBeginTimeBackup = hBeginTime;
		}
		if (hEndTime == null || hEndTime.length() == 0) {
			try {
				cal.setTime(sdf.parse(endTime));
				cal.add(Calendar.MONTH, -1);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			hEndTimeBackup = sdf.format(cal.getTime());
		} else {
			hEndTimeBackup = hEndTime;
		}

		try {
			cal.setTime(sdf.parse(beginTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.add(Calendar.YEAR, -1);//
		tBeginTime = sdf.format(cal.getTime());

		try {
			cal.setTime(sdf.parse(endTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.add(Calendar.YEAR, -1);
		tEndTime = sdf.format(cal.getTime());
		List list = new ArrayList();
		List<Map> list1 = new ArrayList<Map>();
		list.add(beginTime);
		list.add(endTime);
		list.add(beginTime);
		list.add(endTime);
		list.add(tBeginTime);
		list.add(tEndTime);
		list.add(tBeginTime);
		list.add(tEndTime);
		list.add(beginTime);
		list.add(endTime);
		list.add(tBeginTime);
		list.add(tEndTime);
		list.add(tBeginTime);
		list.add(tEndTime);
		list.add(beginTime);
		list.add(endTime);
		list.add(hBeginTimeBackup);
		list.add(hEndTimeBackup);
		list.add(hBeginTimeBackup);
		list.add(hEndTimeBackup);
		list.add(beginTime);
		list.add(endTime);
		list.add(hBeginTimeBackup);
		list.add(hEndTimeBackup);
		list.add(hBeginTimeBackup);
		list.add(hEndTimeBackup);
		// list.add(tBeginTime);
		// list.add(tEndTime);
		// list.add(tBeginTime);
		// list.add(tEndTime);
		// list.add(tBeginTime);
		// list.add(tEndTime);
		// list.add(tBeginTime);
		// list.add(tEndTime);
		// list.add(hBeginTimeBackup);
		// list.add(hEndTimeBackup);
		// list.add(hBeginTimeBackup);
		// list.add(hEndTimeBackup);
		// list.add(hBeginTimeBackup);
		// list.add(hEndTimeBackup);
		// list.add(hBeginTimeBackup);
		// list.add(hEndTimeBackup);
		// list.add(beginTime);
		// list.add(endTime);
		StringBuffer sb = new StringBuffer();
		if (regionCheck != null && regionCheck.length() != 0
				&& "2".equals(regionCheck)) {
			sb.append("--投诉/举报各辖区异动提示表\n"
					+ "select * from (\n"
					+ "SELECT\n"
					+ "(case when T.Hanpardepname is null then ' ' else T.Hanpardepname end )AS 上级单位,\n"
					+ "T.Handepname AS 承办单位,\n"
					+ "NVL(SUM(CASE WHEN T.PRESTATENAME = '待受理'\n"
					+ "             and T.REGTIME >= to_date(?,'yyyy-mm-dd')--当前开始时间\n"
					+ "             and T.REGTIME < to_date(?,'yyyy-mm-dd')--当前及结束时间\n"
					+ "             THEN T.CNT END),0)  AS 承办量,\n"
					+ "\n"
					+ "((NVL(SUM(CASE WHEN T.PRESTATENAME = '待受理'\n"
					+ "               and T.REGTIME >= to_date(?,'yyyy-mm-dd')--当前开始时间\n"
					+ "               and T.REGTIME < to_date(?,'yyyy-mm-dd')--当前结束时间\n"
					+ "               THEN T.CNT END),0))-\n"
					+ " (NVL(SUM(CASE WHEN T.PRESTATENAME = '待受理'\n"
					+ "               AND T.REGTIME >= to_date(?,'yyyy-mm-dd')--同比开始时间\n"
					+ "               AND T.REGTIME < to_date(?,'yyyy-mm-dd')--同比结束时间\n"
					+ "               THEN T.CNT END),0)))  AS 同比数量增减,\n"
					+ "\n"
					+ "round((case when (NVL(SUM(CASE WHEN T.PRESTATENAME = '待受理'\n"
					+ "                         AND T.REGTIME >= to_date(?,'yyyy-mm-dd')--同比开始时间\n"
					+ "                         AND T.REGTIME < to_date(?,'yyyy-mm-dd')--同比结束时间\n"
					+ "                         THEN T.CNT END),0))=0 then 0 else\n"
					+ " ((NVL(SUM(CASE WHEN T.PRESTATENAME = '待受理'\n"
					+ "                and T.REGTIME >= to_date(?,'yyyy-mm-dd')--当前开始时间\n"
					+ "                and T.REGTIME < to_date(?,'yyyy-mm-dd')--当前结束时间\n"
					+ "                THEN T.CNT END),0))-\n"
					+ " (NVL(SUM(CASE WHEN T.PRESTATENAME = '待受理'\n"
					+ "               AND T.REGTIME >= to_date(?,'yyyy-mm-dd')--同比开始时间\n"
					+ "               AND T.REGTIME < to_date(?,'yyyy-mm-dd')--同比结束时间\n"
					+ "               THEN T.CNT END),0)))/(NVL(SUM(CASE WHEN T.PRESTATENAME = '待受理'\n"
					+ "                                             AND T.REGTIME >= to_date(?,'yyyy-mm-dd')--同比开始时间\n"
					+ "                                             AND T.REGTIME < to_date(?,'yyyy-mm-dd')--同比结束时间\n"
					+ "                                             THEN T.CNT END),0))end),4)*100  AS 同比比例增减,\n"
					+ "\n"
					+ "((NVL(SUM(CASE WHEN T.PRESTATENAME = '待受理'\n"
					+ "               and T.REGTIME >= to_date(?,'yyyy-mm-dd')--当前开始时间\n"
					+ "               and T.REGTIME < to_date(?,'yyyy-mm-dd')--当前结束时间\n"
					+ "               THEN T.CNT END),0))-\n"
					+ " (NVL(SUM(CASE WHEN T.PRESTATENAME = '待受理'\n"
					+ "               AND T.REGTIME >= to_date(?,'yyyy-mm-dd')--环比开始时间\n"
					+ "               AND T.REGTIME < to_date(?,'yyyy-mm-dd')--环比结束时间\n"
					+ "               THEN T.CNT END),0)))  AS 环比数量增减,\n"
					+ "\n"
					+ "round((case when (NVL(SUM(CASE WHEN T.PRESTATENAME = '待受理'\n"
					+ "                         AND T.REGTIME >= to_date(?,'yyyy-mm-dd')--环比开始时间\n"
					+ "                         AND T.REGTIME < to_date(?,'yyyy-mm-dd')--环比结束时间\n"
					+ "                         THEN T.CNT END),0))=0 then 0 else\n"
					+ " ((NVL(SUM(CASE WHEN T.PRESTATENAME = '待受理'\n"
					+ "                and T.REGTIME >= to_date(?,'yyyy-mm-dd')--当前开始时间\n"
					+ "                and T.REGTIME < to_date(?,'yyyy-mm-dd')--当前结束时间\n"
					+ "                THEN T.CNT END),0))-\n"
					+ " (NVL(SUM(CASE WHEN T.PRESTATENAME = '待受理'\n"
					+ "               AND T.REGTIME >= to_date(?,'yyyy-mm-dd')--环比开始时间\n"
					+ "               AND T.REGTIME < to_date(?,'yyyy-mm-dd')--环比结束时间\n"
					+ "               THEN T.CNT END),0)))/(NVL(SUM(CASE WHEN T.PRESTATENAME = '待受理'\n"
					+ "                                             AND T.REGTIME >= to_date(?,'yyyy-mm-dd')--环比开始时间\n"
					+ "                                             AND T.REGTIME < to_date(?,'yyyy-mm-dd')--环比结束时间\n"
					+ "                                             THEN T.CNT END),0))end),4)*100  AS 环比比例增减\n"
					+ "  FROM (\n"
					+ "SELECT A.Hanpardepname AS Hanpardepname,\n"
					+ "       A.Handepname AS Handepname,\n"
					+ "       A.Inftype,B.PRESTATENAME,\n"
					+ "       A.REGTIME,COUNT(1) AS CNT\n"
					+ "  FROM DC_CPR_INFOWARE A,DC_CPR_PROCESS_STEP B\n"
					+ "  where A.PROINSID = B.PROINSID\n");
			if (checkType != null && checkType.length() != 0
					&& "1".equals(checkType)) {
				sb.append("    and A.INFTYPE in('1','6')\n");
				types = "投诉";
			} else if (checkType != null && checkType.length() != 0
					&& "2".equals(checkType)) {
				sb.append("    and A.INFTYPE in('2')\n");
				types = "举报";
			} else {
				sb.append("    and A.INFTYPE in('1','2','6')\n");
				types = "投诉举报";
			}
			sb.append(" GROUP BY A.Hanpardepname,A.Handepname,A.Inftype,B.PRESTATENAME,A.REGTIME) T\n"
					+ " where T.Handepname is not null\n"
					+ " GROUP BY T.Hanpardepname,T.Handepname\n"
					+ " )\n"
					+ " order by 上级单位,环比比例增减 desc");
		} else {
			sb.append("--投诉/举报各辖区异动提示表\n"
					+ "select * from (\n"
					+ "SELECT\n"
					+ "T.Handepname AS 承办单位,\n"
					+ "NVL(SUM(CASE WHEN T.PRESTATENAME = '待受理'\n"
					+ "             and T.REGTIME >= to_date(?,'yyyy-mm-dd')--当前开始时间\n"
					+ "             and T.REGTIME < to_date(?,'yyyy-mm-dd')--当前结束时间\n"
					+ "             THEN T.CNT END),0)  AS 承办量,\n"
					+ "\n"
					+ "((NVL(SUM(CASE WHEN T.PRESTATENAME = '待受理'\n"
					+ "               and T.REGTIME >= to_date(?,'yyyy-mm-dd')--当前开始时间\n"
					+ "               and T.REGTIME < to_date(?,'yyyy-mm-dd')--当前结束时间\n"
					+ "               THEN T.CNT END),0))-\n"
					+ " (NVL(SUM(CASE WHEN T.PRESTATENAME = '待受理'\n"
					+ "               AND T.REGTIME >= to_date(?,'yyyy-mm-dd')--同比开始时间\n"
					+ "               AND T.REGTIME < to_date(?,'yyyy-mm-dd')--同比结束时间\n"
					+ "               THEN T.CNT END),0)))  AS 同比数量增减,\n"
					+ "\n"
					+ "round((case when (NVL(SUM(CASE WHEN T.PRESTATENAME = '待受理'\n"
					+ "                         AND T.REGTIME >= to_date(?,'yyyy-mm-dd')--同比开始时间\n"
					+ "                         AND T.REGTIME < to_date(?,'yyyy-mm-dd')--同比结束时间\n"
					+ "                         THEN T.CNT END),0))=0 then 0 else\n"
					+ " ((NVL(SUM(CASE WHEN T.PRESTATENAME = '待受理'\n"
					+ "                and T.REGTIME >= to_date(?,'yyyy-mm-dd')--当前开始时间\n"
					+ "                and T.REGTIME < to_date(?,'yyyy-mm-dd')--当前结束时间\n"
					+ "                THEN T.CNT END),0))-\n"
					+ " (NVL(SUM(CASE WHEN T.PRESTATENAME = '待受理'\n"
					+ "               AND T.REGTIME >= to_date(?,'yyyy-mm-dd')--同比开始时间\n"
					+ "               AND T.REGTIME < to_date(?,'yyyy-mm-dd')--同比结束时间\n"
					+ "               THEN T.CNT END),0)))/(NVL(SUM(CASE WHEN T.PRESTATENAME = '待受理'\n"
					+ "                                             AND T.REGTIME >= to_date(?,'yyyy-mm-dd')--同比开始时间\n"
					+ "                                             AND T.REGTIME < to_date(?,'yyyy-mm-dd')--同比结束时间\n"
					+ "                                             THEN T.CNT END),0))end),4)*100  AS 同比比例增减,\n"
					+ "\n"
					+ "((NVL(SUM(CASE WHEN T.PRESTATENAME = '待受理'\n"
					+ "               and T.REGTIME >= to_date(?,'yyyy-mm-dd')--当前开始时间\n"
					+ "               and T.REGTIME < to_date(?,'yyyy-mm-dd')--当前结束时间\n"
					+ "               THEN T.CNT END),0))-\n"
					+ " (NVL(SUM(CASE WHEN T.PRESTATENAME = '待受理'\n"
					+ "               AND T.REGTIME >= to_date(?,'yyyy-mm-dd')--环比开始时间\n"
					+ "               AND T.REGTIME < to_date(?,'yyyy-mm-dd')--环比结束时间\n"
					+ "               THEN T.CNT END),0)))  AS 环比数量增减,\n"
					+ "\n"
					+ "round((case when (NVL(SUM(CASE WHEN T.PRESTATENAME = '待受理'\n"
					+ "                         AND T.REGTIME >= to_date(?,'yyyy-mm-dd')--环比开始时间\n"
					+ "                         AND T.REGTIME < to_date(?,'yyyy-mm-dd')--环比结束时间\n"
					+ "                         THEN T.CNT END),0))=0 then 0 else\n"
					+ " ((NVL(SUM(CASE WHEN T.PRESTATENAME = '待受理'\n"
					+ "                and T.REGTIME >= to_date(?,'yyyy-mm-dd')--当前开始时间\n"
					+ "                and T.REGTIME < to_date(?,'yyyy-mm-dd')--当前结束时间\n"
					+ "                THEN T.CNT END),0))-\n"
					+ " (NVL(SUM(CASE WHEN T.PRESTATENAME = '待受理'\n"
					+ "               AND T.REGTIME >= to_date(?,'yyyy-mm-dd')--环比开始时间\n"
					+ "               AND T.REGTIME < to_date(?,'yyyy-mm-dd')--环比结束时间\n"
					+ "               THEN T.CNT END),0)))/(NVL(SUM(CASE WHEN T.PRESTATENAME = '待受理'\n"
					+ "                                             AND T.REGTIME >= to_date(?,'yyyy-mm-dd')--环比开始时间\n"
					+ "                                             AND T.REGTIME < to_date(?,'yyyy-mm-dd')--环比结束时间\n"
					+ "                                             THEN T.CNT END),0))end),4)*100 AS 环比比例增减\n"
					+ "  FROM (\n"
					+ "SELECT (CASE WHEN A.Handepname='福田局' or A.Hanpardepname = '福田局' THEN '福田局'\n"
					+ "             WHEN A.Handepname='罗湖局' or A.Hanpardepname = '罗湖局' THEN '罗湖局'\n"
					+ "             WHEN A.Handepname='南山局' or A.Hanpardepname = '南山局' THEN '南山局'\n"
					+ "             WHEN A.Handepname='盐田局' or A.Hanpardepname = '盐田局' THEN '盐田局'\n"
					+ "             WHEN A.Handepname='龙岗局' or A.Hanpardepname = '龙岗局' THEN '龙岗局'\n"
					+ "             WHEN A.Handepname='宝安局' or A.Hanpardepname = '宝安局' THEN '宝安局'\n"
					+ "             WHEN A.Handepname='光明局' or A.Hanpardepname = '光明局' THEN '光明局'\n"
					+ "             WHEN A.Handepname='坪山局' or A.Hanpardepname = '坪山局' THEN '坪山局'\n"
					+ "             WHEN A.Handepname='龙华局' or A.Hanpardepname = '龙华局' THEN '龙华局'\n"
					+ "             WHEN A.Handepname='大鹏局' or A.Hanpardepname = '大鹏局' THEN '大鹏局'\n"
					+ "             WHEN A.Handepname='市价格监督检查局' or A.Hanpardepname = '市价格监督检查局' THEN '市价格监督检查局'\n"
					+ "             WHEN A.Handepname='市企业注册局' or A.Hanpardepname = '市企业注册局' THEN '市企业注册局' end)AS Handepname,\n"
					+ "       A.Inftype,B.PRESTATENAME,A.REGTIME,COUNT(1) AS CNT\n"
					+ "  FROM DC_CPR_INFOWARE A,DC_CPR_PROCESS_STEP B ,dc_code.dc_12315_codedata c\n"
					+ "  where A.PROINSID = B.PROINSID\n"
					+ "  and A.APPLBASQUE = c.code \n"
					+ "  and  c.codetable = 'CH27' \n");
			if (checkType != null && checkType.length() != 0
					&& "1".equals(checkType)) {
				sb.append("    and A.INFTYPE in('1','6')\n");
				types = "投诉";
			} else if (checkType != null && checkType.length() != 0
					&& "2".equals(checkType)) {
				sb.append("    and A.INFTYPE in('2')\n");
				types = "举报";
			} else {
				sb.append("    and A.INFTYPE in('1','2','6')\n");
				types = "投诉举报";
			}
			sb.append("    and A.INFTYPE in('1','2','6')\n"
					+ " GROUP BY A.Handepname,A.Hanpardepname,A.Inftype,B.PRESTATENAME,A.REGTIME) T\n"
					+ " where T.Handepname is not null\n"
					+ " GROUP BY T.Handepname\n" + " )\n"
					+ " order by 环比比例增减 desc");
		}
		try {
			list1 = this.dao.queryForList(sb.toString(), list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("投诉举报各辖区异动提示表", "WDY", i == 1 ? "查看报表" : "下载报表",
				sb.toString(),
				beginTime + "," + endTime + "," + hBeginTime + "," + hEndTime
						+ "," + types + ","
						+ ("2".equals(regionCheck) ? "科/所" : "区/局"), req);
		return list1;
	}

	public List<Map> touSuHangYe(String beginTime, String endTime,
			String hBeginTime, String hEndTime, String checkType, int i,
			HttpServletRequest req) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String hBeginTimeBackup = null;// 环比开始时间
		String hEndTimeBackup = null;// 环比结束时间
		String tBeginTime = null;// 同比开始时间
		String tEndTime = null;// 同比结束时间
		String types = null;
		int timeDifference = this.getTimeDifferenceDay(beginTime, endTime);
		// 前台没有环比时间的值得时候，默认环比时间为上个时间段同一长度的时间
		if (hBeginTime == null || hBeginTime.length() == 0) {
			try {
				cal.setTime(sdf.parse(beginTime));
				cal.add(Calendar.DAY_OF_MONTH, timeDifference);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			hBeginTimeBackup = sdf.format(cal.getTime());
		} else {
			hBeginTimeBackup = hBeginTime;
		}
		if (hEndTime == null || hEndTime.length() == 0) {

			hEndTimeBackup = beginTime;
		} else {
			hEndTimeBackup = hEndTime;
		}

		try {
			cal.setTime(sdf.parse(beginTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.add(Calendar.YEAR, -1);//
		tBeginTime = sdf.format(cal.getTime());

		try {
			cal.setTime(sdf.parse(endTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.add(Calendar.YEAR, -1);
		tEndTime = sdf.format(cal.getTime());
		List list = new ArrayList();
		List<Map> list1 = new ArrayList<Map>();
		beginTime += " 00:00:00";
		endTime += " 23:59:59";
		hBeginTimeBackup += " 00:00:00";
		hEndTimeBackup += " 00:00:00";
		tBeginTime += " 00:00:00";
		tEndTime += " 23:59:59";
		list.add(tBeginTime);
		list.add(tEndTime);
		list.add(hBeginTimeBackup);
		list.add(hEndTimeBackup);
		list.add(beginTime);
		list.add(endTime);
		StringBuffer sb = new StringBuffer();
		sb.append("select * from (\n"
				+ "SELECT\n"
				+ "T.CODE AS 涉及客体,\n"
				+ "NVL(SUM( t.dangqian),0)  AS 承办量,\n"
				+ "((NVL(SUM(t.dangqian),0))-\n"
				+ " (NVL(SUM(t.tongbi),0)))  AS 同比数量增减,\n"
				+ "round((case when (NVL(SUM(t.tongbi),0))=0 then 1 else\n"
				+ " ((NVL(SUM(t.dangqian),0))-\n"
				+ " (NVL(SUM(t.tongbi),0)))/\n"
				+ " (NVL(SUM(t.tongbi),0))end),4)*100  AS 同比比例增减,\n"
				+ "((NVL(SUM(t.dangqian),0))-\n"
				+ " (NVL(SUM(t.huanbi),0)))  AS 环比数量增减,\n"
				+ "round((case when (NVL(SUM(t.huanbi),0))=0 then 1 else\n"
				+ " (((NVL(SUM(t.dangqian),0))-\n"
				+ " (NVL(SUM(t.huanbi),0)))/\n"
				+ " (NVL(SUM(t.huanbi),0)))end),4)*100  AS 环比比例增减\n"
				+ "  FROM (\n"
				+ "        SELECT (case when substrb(t.code,1,3)='101' then '食品'\n"
				+ "              when substrb(t.code,1,3)='103' then '烟、酒和饮料'\n"
				+ "              when substrb(t.code,1,3)='105' then '保健品'\n"
				+ "              when substrb(t.code,1,3)='108' then '药品'\n"
				+ "              when substrb(t.code,1,3)='110' then '医疗器械'\n"
				+ "              when substrb(t.code,1,3)='114' then '化妆品'\n"
				+ "              when substrb(t.code,1,3)='117' then '服装、鞋帽'\n"
				+ "              when substrb(t.code,1,3)='119' then '布料、毛线'\n"
				+ "              when substrb(t.code,1,3)='121' then '家居用品'\n"
				+ "              when substrb(t.code,1,3)='124' then '儿童用品'\n"
				+ "              when substrb(t.code,1,3)='127' then '家用电器'\n"
				+ "              when substrb(t.code,1,3)='131' then '计算机产品'\n"
				+ "              when substrb(t.code,1,3)='134' then '通讯产品'\n"
				+ "              when substrb(t.code,1,3)='138' then '房屋'\n"
				+ "              when substrb(t.code,1,3)='141' then '装修建材'\n"
				+ "              when substrb(t.code,1,3)='145' then '照摄像产品'\n"
				+ "              when substrb(t.code,1,3)='148' then '卫生用品'\n"
				+ "              when substrb(t.code,1,3)='150' then '出版物'\n"
				+ "              when substrb(t.code,1,3)='153' then '文化、运动用品'\n"
				+ "              when substrb(t.code,1,3)='156' then '宠物及宠物用品'\n"
				+ "              when substrb(t.code,1,3)='159' then '首饰'\n"
				+ "              when substrb(t.code,1,3)='162' then '五金交电'\n"
				+ "              when substrb(t.code,1,3)='165' then '交通工具'\n"
				+ "              when substrb(t.code,1,3)='168' then '燃料'\n"
				+ "              when substrb(t.code,1,3)='170' then '殡葬用品'\n"
				+ "              when substrb(t.code,1,3)='173' then '农资用品'\n"
				+ "              when substrb(t.code,1,3)='180' then '保健食品'\n"
				+ "              when substrb(t.code,1,3)='199' then '其他商品'\n"
				+ "              else '其他' end) AS code,\n"
				+ "              A.Inftype,\n"
				+ "              A.REGTIME,\n"
				+ "              (case when ( a.regtime >=to_date(?,'yyyy-mm-dd hh24:mi:ss')  --同比开始时间\n"
				+ "                    and a.regtime <=to_date(?,'yyyy-mm-dd hh24:mi:ss')) --同比结束时间\n"
				+ "                    then 1 else 0 end ) tongbi,--同比flag)\n"
				+ "              (case when (a.regtime >=to_date(?,'yyyy-mm-dd hh24:mi:ss') --环比开始时间\n"
				+ "                    and a.regtime <to_date(?,'yyyy-mm-dd hh24:mi:ss')) -- 环比结束时间\n"
				+ "                    then 1 else 0 end ) huanbi,--环比flag\n"
				+ "              (case when (a.regtime >=to_date(?,'yyyy-mm-dd hh24:mi:ss') --当前开始时间\n"
				+ "                    and a.regtime <=to_date(?,'yyyy-mm-dd hh24:mi:ss')) --当前结束时间\n"
				+ "                    then 1 else 0 end) dangqian --当前flag\n"
				+ "                  FROM DC_CPR_INFOWARE A,DC_CPR_INVOLVED_OBJECT C,dc_code.dc_12315_codedata t\n"
				+ "                  where  A.INVOBJID = C.INVOBJID \n"
				+ "                    and C.INVOBJTYPE = t.code \n"
				+ "                    and t.codetable = 'CH20' \n");
		if (checkType != null && checkType.length() != 0
				&& "1".equals(checkType)) {
			sb.append("    and A.INFTYPE in('1') \n");
			types = "投诉";
		} else if (checkType != null && checkType.length() != 0
				&& "2".equals(checkType)) {
			sb.append("    and A.INFTYPE in('2') \n");
			types = "举报";
		} else {
			sb.append("    and A.INFTYPE in('1','2','6') \n");
			types = "投诉举报";
		}
		sb.append(") T \n");
		sb.append(" GROUP BY T.code\n" + " )order by 环比比例增减  desc");
		try {
			list1 = this.dao.queryForList(sb.toString(), list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("投诉举报行业异动提示表(" + types + ")", "WDY", i == 1 ? "查看报表"
				: "下载报表", sb.toString(), beginTime + "," + endTime + ","
				+ hBeginTime + "," + hEndTime + "," + types, req);
		return list1;
	}

	public List<Map> touSuLeiBie(String beginTime, String endTime,
			String hBeginTime, String hEndTime, String checkType, int i,
			HttpServletRequest req) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String hBeginTimeBackup = null;// 环比开始时间
		String hEndTimeBackup = null;// 环比结束时间
		String tBeginTime = null;// 同比开始时间
		String tEndTime = null;// 同比结束时间
		String types = null;
		// 前台没有同比时间的值得时候，默认环比时间为上个月同一时间段
		if (hBeginTime == null || hBeginTime.length() == 0) {
			try {
				cal.setTime(sdf.parse(beginTime));
				cal.add(Calendar.MONTH, -1);//
			} catch (ParseException e) {
				e.printStackTrace();
			}
			hBeginTimeBackup = sdf.format(cal.getTime());
		} else {
			hBeginTimeBackup = hBeginTime;
		}
		if (hEndTime == null || hEndTime.length() == 0) {
			try {
				cal.setTime(sdf.parse(endTime));
				cal.add(Calendar.MONTH, -1);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			hEndTimeBackup = sdf.format(cal.getTime());
		} else {
			hEndTimeBackup = hEndTime;
		}

		try {
			cal.setTime(sdf.parse(beginTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.add(Calendar.YEAR, -1);//
		tBeginTime = sdf.format(cal.getTime());

		try {
			cal.setTime(sdf.parse(endTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.add(Calendar.YEAR, -1);
		tEndTime = sdf.format(cal.getTime());
		List list = new ArrayList();
		List<Map> list1 = new ArrayList<Map>();
		list.add(beginTime+" 00:00:00");
		list.add(endTime+" 23:59:59");
		list.add(beginTime+" 00:00:00");
		list.add(endTime+" 23:59:59");
		list.add(tBeginTime+" 00:00:00");
		list.add(tEndTime+" 23:59:59");
		list.add(tBeginTime+" 00:00:00");
		list.add(tEndTime+" 23:59:59");
		list.add(beginTime+" 00:00:00");
		list.add(endTime+" 23:59:59");
		list.add(tBeginTime+" 00:00:00");
		list.add(tEndTime+" 23:59:59");
		list.add(tBeginTime+" 00:00:00");
		list.add(tEndTime+" 23:59:59");
		list.add(beginTime+" 00:00:00");
		list.add(endTime+" 23:59:59");
		list.add(hBeginTimeBackup+" 00:00:00");
		list.add(hEndTimeBackup+" 23:59:59");
		list.add(hBeginTimeBackup+" 00:00:00");
		list.add(hEndTimeBackup+" 23:59:59");
		list.add(beginTime+" 00:00:00");
		list.add(endTime+" 23:59:59");
		list.add(hBeginTimeBackup+" 00:00:00");
		list.add(hEndTimeBackup+" 23:59:59");
		list.add(hBeginTimeBackup+" 00:00:00");
		list.add(hEndTimeBackup+" 23:59:59");
		StringBuffer sb = new StringBuffer();
		sb.append(
				"--投诉/举报问题类别异动提示表\n" +
						"select * from(\n" + 
						"SELECT\n" + 
						"T.CODE  AS 涉及客体,\n" + 
						"NVL(SUM(CASE WHEN --T.PRESTATENAME = '待受理'\n" + 
						"            -- and\n" + 
						"             T.REGTIME >= to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"             and T.REGTIME <=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"             THEN T.CNT END),0)  AS 承办量,\n" + 
						"\n" + 
						"((NVL(SUM(CASE WHEN --T.PRESTATENAME = '待受理'\n" + 
						"               --and\n" + 
						"               T.REGTIME >= to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"               and T.REGTIME <=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"               THEN T.CNT END),0))-\n" + 
						" (NVL(SUM(CASE WHEN --T.PRESTATENAME = '待受理'\n" + 
						"      --AND\n" + 
						"       T.REGTIME >= to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"      AND T.REGTIME <=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"      THEN T.CNT END),0)))  AS 同比数量增减,\n" + 
						"\n" + 
						"round((case when (NVL(SUM(CASE WHEN --T.PRESTATENAME = '待受理'\n" + 
						"      --AND\n" + 
						"      T.REGTIME >= to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"      AND T.REGTIME <=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"      THEN T.CNT END),0))=0 then 0 else\n" + 
						" ((NVL(SUM(CASE WHEN --T.PRESTATENAME = '待受理'\n" + 
						"                --and\n" + 
						"                 T.REGTIME >= to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"                and T.REGTIME <=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"                THEN T.CNT END),0))-\n" + 
						" (NVL(SUM(CASE WHEN --T.PRESTATENAME = '待受理'\n" + 
						"               --AND\n" + 
						"               T.REGTIME >= to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"               AND T.REGTIME <=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"               THEN T.CNT END),0)))/(NVL(SUM(CASE WHEN -- T.PRESTATENAME = '待受理'\n" + 
						"                                            -- AND\n" + 
						"                                             T.REGTIME >= to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"                                             AND T.REGTIME <=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"                                             THEN T.CNT END),0))end),4)*100  AS 同比比例增减,\n" + 
						"\n" + 
						"((NVL(SUM(CASE WHEN --T.PRESTATENAME = '待受理'\n" + 
						"               --and\n" + 
						"               T.REGTIME >= to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"               and T.REGTIME <=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"               THEN T.CNT END),0))-\n" + 
						" (NVL(SUM(CASE WHEN --T.PRESTATENAME = '待受理'\n" + 
						"              -- AND\n" + 
						"              T.REGTIME >= to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"               AND T.REGTIME <=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"               THEN T.CNT END),0)))  AS 环比数量增减,\n" + 
						"\n" + 
						"round((case when (NVL(SUM(CASE WHEN --T.PRESTATENAME = '待受理'\n" + 
						"                    --AND\n" + 
						"                     T.REGTIME >= to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"                    AND T.REGTIME <=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"                    THEN T.CNT END),0))=0 then 0 else\n" + 
						" ((NVL(SUM(CASE WHEN --T.PRESTATENAME = '待受理'\n" + 
						"               -- and\n" + 
						"                T.REGTIME >= to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"                and T.REGTIME <=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"                THEN T.CNT END),0))-\n" + 
						" (NVL(SUM(CASE WHEN --T.PRESTATENAME = '待受理'\n" + 
						"              -- AND\n" + 
						"               T.REGTIME >= to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"               AND T.REGTIME <=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"               THEN T.CNT END),0)))/(NVL(SUM(CASE WHEN --T.PRESTATENAME = '待受理'\n" + 
						"                                            -- AND\n" + 
						"                                             T.REGTIME >= to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"                                             AND T.REGTIME <=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"                                             THEN T.CNT END),0))end),4)*100  AS 环比比例增减\n" + 
						"  FROM (\n" + 
						"SELECT (case when substrb(T.code,0,2)='01' then '质量'\n" + 
						"      when substrb(T.code,0,2)='02' then '广告'\n" + 
						"      when substrb(T.code,0,2)='03' then '商标'\n" + 
						"      when substrb(T.code,0,2)='04' then '不正当竞争及限制竞争'\n" + 
						"      when substrb(T.code,0,2)='05' then '无照经营'\n" + 
						"      when substrb(T.code,0,2)='06' then '传销'\n" + 
						"      when substrb(T.code,0,2)='07' then '合同'\n" + 
						"      when substrb(T.code,0,2)='08' then '包装标识'\n" + 
						"      when substrb(T.code,0,2)='09' then '计量'\n" + 
						"      when substrb(T.code,0,2)='10' then '价格'\n" + 
						"      when substrb(T.code,0,2)='11' then '人身权利'\n" + 
						"      when substrb(T.code,0,2)='12' then '售后服务'\n" + 
						"      when substrb(T.code,0,2)='13' then '假冒'\n" + 
						"      when substrb(T.code,0,2)='14' then '其它违反登记管理行为'\n" + 
						"      when substrb(T.code,0,2)='49' then '违反控烟条例'\n" + 
						"      when substrb(T.code,0,2)='51' then '电梯类'\n" + 
						"      when substrb(T.code,0,2)='52' then '压力容器类'\n" + 
						"      when substrb(T.code,0,2)='53' then '食药监投诉举报环节'\n" + 
						"      else '其他' end) AS CODE,\n" + 
						"      A.Inftype,A.REGTIME,COUNT(1) AS CNT\n" + 
						"  FROM DC_CPR_INFOWARE A,dc_code.dc_12315_codedata t\n" + 
						"  where t.codetable = 'CH27'\n" + 
						"     and A.APPLBASQUE = t.code(+) \n");
		if (checkType != null && checkType.length() != 0
				&& "1".equals(checkType)) {
			sb.append("    and A.INFTYPE in('1','6')\n");
			types = "投诉";
		} else if (checkType != null && checkType.length() != 0
				&& "2".equals(checkType)) {
			sb.append("    and A.INFTYPE in('2')\n");
			types = "举报";
		} else {
			sb.append("    and A.INFTYPE in('1','2','6')\n");
			types = "投诉举报";
		}
		sb.append("GROUP BY t.code,A.Inftype,A.REGTIME) T\n" +
						" GROUP BY T.CODE)\n" + 
						" order by 环比比例增减  desc");
		try {
			list1 = this.dao.queryForList(sb.toString(), list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("投诉举报问题类别异动提示表", "WDY", i == 1 ? "查看报表" : "下载报表",
				sb.toString(), beginTime + "," + endTime + "," + hBeginTime
						+ "," + hEndTime + "," + types, req);
		return list1;
	}

	public List<Map> lingYuZiXun(String beginTime, String endTime, int i,
			HttpServletRequest req) {
		List list = new ArrayList();
		List<Map> list1 = new ArrayList<Map>();
		list.add(beginTime);
		list.add(endTime);
		String sql = "--各领域咨询业务数量统计表（表1）\n"
				+ "SELECT\n"
				+ "'深圳市市场监督管理局' AS 单位,\n"
				+ "nvl(SUM(CASE WHEN T.BUSINESSRANGE like '500%' THEN T.CNT END),0) AS 有效咨询业务量,\n"
				+ "nvl(SUM(CASE WHEN T.BUSINESSRANGE = '50010000' THEN T.CNT END),0) AS 质量数量,\n"
				+ "round((case when (NVL(SUM(CASE WHEN T.BUSINESSRANGE like '500%'\n"
				+ "                         THEN T.CNT END),0))=0 then 0 else\n"
				+ " nvl(SUM(CASE WHEN T.BUSINESSRANGE = '50010000' THEN T.CNT END),0)/\n"
				+ " nvl(SUM(CASE WHEN T.BUSINESSRANGE like '500%' THEN T.CNT END),0)end),4)*100  AS 质量占有效咨询业务量的比例,\n"
				+ "\n"
				+ "nvl(SUM(CASE WHEN T.BUSINESSRANGE = '50020000' THEN T.CNT END),0) AS 计量数量,\n"
				+ "round((case when (NVL(SUM(CASE WHEN T.BUSINESSRANGE like '500%'\n"
				+ "                         THEN T.CNT END),0))=0 then 0 else\n"
				+ " nvl(SUM(CASE WHEN T.BUSINESSRANGE = '50020000' THEN T.CNT END),0)/\n"
				+ " nvl(SUM(CASE WHEN T.BUSINESSRANGE like '500%' THEN T.CNT END),0)end),4)*100 AS 计量占有效咨询业务量的比例,\n"
				+ "\n"
				+ "nvl(SUM(CASE WHEN T.BUSINESSRANGE = '50030000' THEN T.CNT END),0) AS 标准数量,\n"
				+ "round((case when (NVL(SUM(CASE WHEN T.BUSINESSRANGE like '500%'\n"
				+ "                         THEN T.CNT END),0))=0 then 0 else\n"
				+ " nvl(SUM(CASE WHEN T.BUSINESSRANGE = '50030000' THEN T.CNT END),0)/\n"
				+ " nvl(SUM(CASE WHEN T.BUSINESSRANGE like '500%' THEN T.CNT END),0)end),4)*100  AS 标准占有效咨询业务量的比例,\n"
				+ "\n"
				+ "nvl(SUM(CASE WHEN T.BUSINESSRANGE = '50040000' THEN T.CNT END),0) AS 特种设备数量,\n"
				+ "round((case when (NVL(SUM(CASE WHEN T.BUSINESSRANGE like '500%'\n"
				+ "                         THEN T.CNT END),0))=0 then 0 else\n"
				+ " nvl(SUM(CASE WHEN T.BUSINESSRANGE = '50040000' THEN T.CNT END),0)/\n"
				+ " nvl(SUM(CASE WHEN T.BUSINESSRANGE like '500%' THEN T.CNT END),0)end),4)*100  AS 特种设备占有效咨询业务量的比例,\n"
				+ "\n"
				+ "nvl(SUM(CASE WHEN T.BUSINESSRANGE = '50050000' THEN T.CNT END),0) AS 认证认可数量,\n"
				+ "round((case when (NVL(SUM(CASE WHEN T.BUSINESSRANGE like '500%'\n"
				+ "                         THEN T.CNT END),0))=0 then 0 else\n"
				+ " nvl(SUM(CASE WHEN T.BUSINESSRANGE = '50050000' THEN T.CNT END),0)/\n"
				+ " nvl(SUM(CASE WHEN T.BUSINESSRANGE like '500%' THEN T.CNT END),0)end),4)*100  AS 认证认可占有效咨询业务量的比例,\n"
				+ "\n"
				+ "nvl(SUM(CASE WHEN T.BUSINESSRANGE = '50060000' THEN T.CNT END),0) AS 生产许可证数量,\n"
				+ "round((case when (NVL(SUM(CASE WHEN T.BUSINESSRANGE like '500%'\n"
				+ "                         THEN T.CNT END),0))=0 then 0 else\n"
				+ " nvl(SUM(CASE WHEN T.BUSINESSRANGE = '50060000' THEN T.CNT END),0)/\n"
				+ " nvl(SUM(CASE WHEN T.BUSINESSRANGE like '500%' THEN T.CNT END),0)end),4)*100  AS 生产许可证占有效咨询业务量比例,\n"
				+ "\n"
				+ "nvl(SUM(CASE WHEN T.BUSINESSRANGE = '50090000' THEN T.CNT END),0) AS 其他数量,\n"
				+ "round((case when (NVL(SUM(CASE WHEN T.BUSINESSRANGE like '500%'\n"
				+ "                         THEN T.CNT END),0))=0 then 0 else\n"
				+ " nvl(SUM(CASE WHEN T.BUSINESSRANGE = '50090000' THEN T.CNT END),0)/\n"
				+ " nvl(SUM(CASE WHEN T.BUSINESSRANGE like '500%' THEN T.CNT END),0)end),4)*100  AS 其他占有效咨询业务量的比例\n"
				+ "\n" + "  FROM (\n" + "SELECT B.BUSINESSRANGE,\n"
				+ "       A.Inftype,A.REGTIME,COUNT(1) AS CNT\n"
				+ "  FROM DC_CPR_INFOWARE A,DC_CPR_INQUIRY  B\n"
				+ "  where A.INQUIRYID=B.INQUIRYID\n"
				+ "    and A.INFTYPE = '3'\n"
				+ " GROUP BY B.BUSINESSRANGE,A.Inftype,A.REGTIME) T\n"
				+ " where T.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "    and T.REGTIME <= to_date(?,'yyyy-mm-dd')";
		try {
			list1 = dao.queryForList(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("各领域咨询业务数量统计表", "WDY", i == 1 ? "查看报表" : "下载报表", sql,
				beginTime + "," + endTime, req);
		return list1;
	}

	public List<Map> lingYuTouSu(String beginTime, String endTime, int i,
			HttpServletRequest req) {
		List list = new ArrayList();
		List<Map> list1 = new ArrayList<Map>();
		list.add(beginTime);
		list.add(endTime);
		String sql = "--各领域投诉业务数量统计表（表2）\n"
				+ "SELECT\n"
				+ "'深圳市市场监督管理局' AS 单位,\n"
				+ "nvl(SUM(CASE WHEN T.Businesstype = 'ZH21' THEN T.CNT END),0) AS 投诉总数,\n"
				+ "nvl(SUM(CASE WHEN substrb(T.Applbasque,1,2) = '01' THEN T.CNT END),0) AS 质量数量,\n"
				+ "round((case when (NVL(SUM(CASE WHEN T.Businesstype = 'ZH21'\n"
				+ "                         THEN T.CNT END),0))=0 then 0 else\n"
				+ " nvl(SUM(CASE WHEN substrb(T.Applbasque,1,2) = '01' THEN T.CNT END),0)/\n"
				+ " nvl(SUM(CASE WHEN T.Businesstype = 'ZH21' THEN T.CNT END),0)end),4)*100  AS 质量占投诉的比例,\n"
				+ "\n"
				+ "nvl(SUM(CASE WHEN substrb(T.Applbasque,1,2) = '09' THEN T.CNT END),0) AS 计量数量,\n"
				+ "round((case when (NVL(SUM(CASE WHEN T.Businesstype = 'ZH21'\n"
				+ "                         THEN T.CNT END),0))=0 then 0 else\n"
				+ " nvl(SUM(CASE WHEN substrb(T.Applbasque,1,2) = '09' THEN T.CNT END),0)/\n"
				+ " nvl(SUM(CASE WHEN T.Businesstype = 'ZH21' THEN T.CNT END),0)end),4)*100  AS 计量占投诉的比例,\n"
				+ "\n"
				+ "nvl(SUM(CASE WHEN substrb(T.Applbasque,1,2) = '54' THEN T.CNT END),0) AS 标准数量,\n"
				+ "round((case when (NVL(SUM(CASE WHEN T.Businesstype = 'ZH21'\n"
				+ "                         THEN T.CNT END),0))=0 then 0 else\n"
				+ " nvl(SUM(CASE WHEN substrb(T.Applbasque,1,2) = '54' THEN T.CNT END),0)/\n"
				+ " nvl(SUM(CASE WHEN T.Businesstype = 'ZH21' THEN T.CNT END),0)end),4)*100  AS 标准占投诉的比例,\n"
				+ "\n"
				+ "nvl(SUM(CASE WHEN substrb(T.Applbasque,1,2) = '55' THEN T.CNT END),0) AS 特种设备数量,\n"
				+ "round((case when (NVL(SUM(CASE WHEN T.Businesstype = 'ZH21'\n"
				+ "                         THEN T.CNT END),0))=0 then 0 else\n"
				+ " nvl(SUM(CASE WHEN substrb(T.Applbasque,1,2) = '55' THEN T.CNT END),0)/\n"
				+ " nvl(SUM(CASE WHEN T.Businesstype = 'ZH21' THEN T.CNT END),0)end),4)*100  AS 特种设备占投诉的比例,\n"
				+ "\n"
				+ "nvl(SUM(CASE WHEN substrb(T.Applbasque,1,2) = '56' THEN T.CNT END),0) AS 认证认可数量,\n"
				+ "round((case when (NVL(SUM(CASE WHEN T.Businesstype = 'ZH21'\n"
				+ "                         THEN T.CNT END),0))=0 then 0 else\n"
				+ " nvl(SUM(CASE WHEN substrb(T.Applbasque,1,2) = '56' THEN T.CNT END),0)/\n"
				+ " nvl(SUM(CASE WHEN T.Businesstype = 'ZH21' THEN T.CNT END),0)end),4)*100  AS 认证认可占投诉的比例,\n"
				+ "\n"
				+ "nvl(SUM(CASE WHEN substrb(T.Applbasque,1,2) = '57' THEN T.CNT END),0) AS 生产许可证数量,\n"
				+ "round((case when (NVL(SUM(CASE WHEN T.Businesstype = 'ZH21'\n"
				+ "                         THEN T.CNT END),0))=0 then 0 else\n"
				+ " nvl(SUM(CASE WHEN substrb(T.Applbasque,1,2) = '57' THEN T.CNT END),0)/\n"
				+ " nvl(SUM(CASE WHEN T.Businesstype = 'ZH21' THEN T.CNT END),0)end),4)*100  AS 生产许可证占投诉的比例,\n"
				+ "\n"
				+ "nvl(SUM(CASE WHEN substrb(T.Applbasque,1,2) = '99' THEN T.CNT END),0) AS 其他数量,\n"
				+ "round((case when (NVL(SUM(CASE WHEN T.Businesstype = 'ZH21'\n"
				+ "                         THEN T.CNT END),0))=0 then 0 else\n"
				+ " nvl(SUM(CASE WHEN substrb(T.Applbasque,1,2) = '99' THEN T.CNT END),0)/\n"
				+ " nvl(SUM(CASE WHEN T.Businesstype = 'ZH21' THEN T.CNT END),0)end),4)*100  AS 其他占投诉的比例\n"
				+ "\n"
				+ "  FROM (\n"
				+ "SELECT A.Applbasque,B.Businesstype,\n"
				+ "       A.Inftype,A.REGTIME,COUNT(1) AS CNT\n"
				+ "  FROM DC_CPR_INFOWARE A,DC_CPR_INVOLVED_OBJECT B\n"
				+ "  where A.INVOBJID=B.INVOBJID\n"
				+ "    and A.INFTYPE in ('1','6')\n"
				+ "    and B.Businesstype = 'ZH21'\n"
				+ " GROUP BY A.Applbasque,B.Businesstype,A.Inftype,A.REGTIME) T\n"
				+ " where t.regtime>= to_date(?,'yyyy-mm-dd')\n"
				+ " and   t.regtime<= to_date(?,'yyyy-mm-dd')";
		try {
			list1 = dao.queryForList(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("各领域投诉业务数量统计表", "WDY", i == 1 ? "查看报表" : "下载报表", sql,
				beginTime + "," + endTime, req);

		return list1;
	}

	public List<Map> lingYuJuBao(String beginTime, String endTime, int i,
			HttpServletRequest req) {
		List list = new ArrayList();
		List<Map> list1 = new ArrayList<Map>();
		list.add(beginTime);
		list.add(endTime);
		String sql = "--各领域举报业务数量统计表（表3）\n"
				+ "SELECT\n"
				+ "'深圳市市场监督管理局' AS 单位,\n"
				+ "nvl(SUM(CASE WHEN T.Businesstype = 'ZH21' THEN T.CNT END),0) AS 举报总数,\n"
				+ "nvl(SUM(CASE WHEN substrb(T.Applbasque,1,2) = '01' THEN T.CNT END),0) AS 质量数量,\n"
				+ "round((case when (NVL(SUM(CASE WHEN T.Businesstype = 'ZH21'\n"
				+ "                         THEN T.CNT END),0))=0 then 0 else\n"
				+ " nvl(SUM(CASE WHEN substrb(T.Applbasque,1,2) = '01' THEN T.CNT END),0)/\n"
				+ " nvl(SUM(CASE WHEN T.Businesstype = 'ZH21' THEN T.CNT END),0)end),4)*100  AS 质量占举报的比例,\n"
				+ "\n"
				+ "nvl(SUM(CASE WHEN substrb(T.Applbasque,1,2) = '09' THEN T.CNT END),0) AS 计量数量,\n"
				+ "round((case when (NVL(SUM(CASE WHEN T.Businesstype = 'ZH21'\n"
				+ "                         THEN T.CNT END),0))=0 then 0 else\n"
				+ " nvl(SUM(CASE WHEN substrb(T.Applbasque,1,2) = '09' THEN T.CNT END),0)/\n"
				+ " nvl(SUM(CASE WHEN T.Businesstype = 'ZH21' THEN T.CNT END),0)end),4)*100  AS 计量占举报的比例,\n"
				+ "\n"
				+ "nvl(SUM(CASE WHEN substrb(T.Applbasque,1,2) = '54' THEN T.CNT END),0) AS 标准数量,\n"
				+ "round((case when (NVL(SUM(CASE WHEN T.Businesstype = 'ZH21'\n"
				+ "                         THEN T.CNT END),0))=0 then 0 else\n"
				+ " nvl(SUM(CASE WHEN substrb(T.Applbasque,1,2) = '54' THEN T.CNT END),0)/\n"
				+ " nvl(SUM(CASE WHEN T.Businesstype = 'ZH21' THEN T.CNT END),0)end),4)*100  AS 标准占举报的比例,\n"
				+ "\n"
				+ "nvl(SUM(CASE WHEN substrb(T.Applbasque,1,2) = '55' THEN T.CNT END),0) AS 特种设备数量,\n"
				+ "round((case when (NVL(SUM(CASE WHEN T.Businesstype = 'ZH21'\n"
				+ "                         THEN T.CNT END),0))=0 then 0 else\n"
				+ " nvl(SUM(CASE WHEN substrb(T.Applbasque,1,2) = '55' THEN T.CNT END),0)/\n"
				+ " nvl(SUM(CASE WHEN T.Businesstype = 'ZH21' THEN T.CNT END),0)end),4)*100  AS 特种设备占举报的比例,\n"
				+ "\n"
				+ "nvl(SUM(CASE WHEN substrb(T.Applbasque,1,2) = '56' THEN T.CNT END),0) AS 认证认可数量,\n"
				+ "round((case when (NVL(SUM(CASE WHEN T.Businesstype = 'ZH21'\n"
				+ "                         THEN T.CNT END),0))=0 then 0 else\n"
				+ " nvl(SUM(CASE WHEN substrb(T.Applbasque,1,2) = '56' THEN T.CNT END),0)/\n"
				+ " nvl(SUM(CASE WHEN T.Businesstype = 'ZH21' THEN T.CNT END),0)end),4)*100  AS 认证认可占举报的比例,\n"
				+ "\n"
				+ "nvl(SUM(CASE WHEN substrb(T.Applbasque,1,2) = '57' THEN T.CNT END),0) AS 生产许可证数量,\n"
				+ "round((case when (NVL(SUM(CASE WHEN T.Businesstype = 'ZH21'\n"
				+ "                         THEN T.CNT END),0))=0 then 0 else\n"
				+ " nvl(SUM(CASE WHEN substrb(T.Applbasque,1,2) = '57' THEN T.CNT END),0)/\n"
				+ " nvl(SUM(CASE WHEN T.Businesstype = 'ZH21' THEN T.CNT END),0)end),4)*100  AS 生产许可证占举报的比例,\n"
				+ "\n"
				+ "nvl(SUM(CASE WHEN substrb(T.Applbasque,1,2) = '99' THEN T.CNT END),0) AS 其他数量,\n"
				+ "round((case when (NVL(SUM(CASE WHEN T.Businesstype = 'ZH21'\n"
				+ "                         THEN T.CNT END),0))=0 then 0 else\n"
				+ " nvl(SUM(CASE WHEN substrb(T.Applbasque,1,2) = '99' THEN T.CNT END),0)/\n"
				+ " nvl(SUM(CASE WHEN T.Businesstype = 'ZH21' THEN T.CNT END),0)end),4)*100  AS 其他占举报的比例\n"
				+ "\n"
				+ "  FROM (\n"
				+ "SELECT A.Applbasque,B.Businesstype,\n"
				+ "       A.Inftype,A.REGTIME,COUNT(1) AS CNT\n"
				+ "  FROM DC_CPR_INFOWARE A,DC_CPR_INVOLVED_OBJECT B\n"
				+ "  where A.INVOBJID=B.INVOBJID\n"
				+ "    and A.INFTYPE = '2'\n"
				+ "    and B.Businesstype = 'ZH21'\n"
				+ " GROUP BY A.Applbasque,B.Businesstype,A.Inftype,A.REGTIME) T\n"
				+ " where t.regtime>= to_date(?,'yyyy-mm-dd')\n"
				+ " and t.regtime <= to_date(?,'yyyy-mm-dd')";
		try {
			list1 = dao.queryForList(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("各领域举报业务数量统计表", "WDY", i == 1 ? "查看报表" : "下载报表", sql,
				beginTime + "," + endTime, req);
		return list1;
	}

	public List<Map> juBaoTouSuPinPai(String beginTime, String endTime, int i,
			HttpServletRequest req) {
		List list = new ArrayList();
		List<Map> list1 = new ArrayList<Map>();
		list.add(beginTime);
		list.add(endTime);
		String sql = "--举报、投诉热点产品品牌或企业统计表（表5）\n"
				+ "select P.产品名称,P.品牌或生产企业,P.举报数,P.举报查实数,P.投诉数,P.投诉受理数\n"
				+ "from(\n"
				+ "select X.产品名称,X.品牌或生产企业,X.举报数,X.举报查实数,X.投诉数,X.投诉受理数,\n"
				+ "       ROW_NUMBER() OVER(ORDER BY X.举报数 DESC) ROW_CNT\n"
				+ "from\n"
				+ "(SELECT\n"
				+ "(case when T.Mdsename is null then ' ' else T.Mdsename end)AS 产品名称,\n"
				+ "(case when T.BRANDNAME is null then ' ' else T.BRANDNAME end) AS 品牌或生产企业,\n"
				+ "NVL(SUM(CASE WHEN T.Inftype = '2' THEN T.CNT END),0)  AS 举报数,\n"
				+ "NVL(SUM(CASE WHEN T.Inftype = '2' AND T.Acctype = '12' THEN T.CNT END),0) AS 举报查实数,\n"
				+ "NVL(SUM(CASE WHEN T.Inftype in ('1','6') THEN T.CNT END),0)  AS 投诉数,\n"
				+ "NVL(SUM(CASE WHEN T.Inftype in ('1','6') AND T.Accepttime is not null THEN T.CNT END),0)  AS 投诉受理数\n"
				+ "  FROM (\n"
				+ "SELECT B.Mdsename,B.BRANDNAME,C.Acctype,\n"
				+ "      A.Inftype,A.REGTIME,A.Accepttime, COUNT(1) AS CNT\n"
				+ "  FROM DC_CPR_INFOWARE A,DC_CPR_INVOLVED_OBJECT B,DC_CPR_FEEDBACK C\n"
				+ "  where A.INVOBJID=B.INVOBJID\n"
				+ "    and C.FEEDBACKID=A.FEEDBACKID\n"
				+ "    and B.Businesstype = 'ZH21'\n"
				+ " GROUP BY B.Mdsename,B.BRANDNAME,C.Acctype,A.Inftype,A.REGTIME,A.Accepttime) T\n"
				+ " where  t.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ " and t.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ " GROUP BY T.Mdsename,T.BRANDNAME)X)P\n"
				+ "where P.ROW_CNT <= '10'";
		try {
			list1 = dao.queryForList(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("举报、投诉热点产品品牌或企业统计表", "WDY", i == 1 ? "查看报表" : "下载报表",
				sql, beginTime + "," + endTime, req);
		return list1;
	}

	public List<Map> BanLiXiaoLv(String beginTime, String endTime, int i,
			HttpServletRequest req) {
		List list = new ArrayList();
		List<Map> list1 = new ArrayList<Map>();
		list.add(beginTime);
		list.add(endTime);
		String sql = "--12365投诉处置工作效率与工作效果分析表（表7）\n"
				+ "SELECT\n"
				+ "'深圳市市场监督管理局' AS 单位名称,\n"
				+ "NVL(SUM(CASE WHEN T.Inftype in ('1','6') THEN T.CNT END),0)  AS 投诉数,\n"
				+ "NVL(SUM(CASE WHEN T.Inftype in ('1','6') AND T.Accepttime is not null THEN T.CNT END),0)  AS 投诉受理数,\n"
				+ "'2' AS 登记到提出拟办意见的平均时长,\n"
				+ "round((case when NVL(SUM(CASE WHEN T.Inftype in ('1','6') AND T.Accepttime is not null THEN T.CNT END),0)=0 then 0 else\n"
				+ " nvl(SUM(CASE WHEN T.Inftype in ('1','6') AND T.Accepttime is not null THEN T.Finishtime-T.REGTIME END),0)/\n"
				+ " NVL(SUM(CASE WHEN T.Inftype in ('1','6') AND T.Accepttime is not null THEN T.CNT END),0)end),2) AS 办理每起投诉的平均时长,\n"
				+ "'5' AS 办理每起投诉的平均通话次数,\n"
				+ "'3000' AS 办理每起投诉的平均通话时长,\n"
				+ "NVL(SUM(CASE WHEN T.Inftype in ('1','6') AND T.Accepttime is not null THEN T.Disam END),0) AS 提出索赔金额,\n"
				+ "NVL(SUM(CASE WHEN T.Inftype in ('1','6') AND T.Accepttime is not null THEN T.Redecolos END),0) AS 实际赔付金额,\n"
				+ "round((case when NVL(SUM(CASE WHEN T.Inftype in ('1','6') AND T.Accepttime is not null THEN T.Disam END),0)=0 then 0 else\n"
				+ " NVL(SUM(CASE WHEN T.Inftype in ('1','6') AND T.Accepttime is not null THEN T.Redecolos END),0)/\n"
				+ " NVL(SUM(CASE WHEN T.Inftype in ('1','6') AND T.Accepttime is not null THEN T.Disam END),0)end),4)*100 AS 赔付金额占索赔金额比例,\n"
				+ "' ' AS 通话满意率,\n"
				+ "NVL(SUM(CASE WHEN T.Inftype in ('1','6') AND T.Accepttime is not null AND T.MEDIATE_RESULT = '1' THEN T.CNT END),0) AS 调解成功数,\n"
				+ "round((case when NVL(SUM(CASE WHEN T.Inftype in ('1','6') AND T.Accepttime is not null THEN T.CNT END),0)=0 then 0 else\n"
				+ " NVL(SUM(CASE WHEN T.Inftype in ('1','6') AND T.Accepttime is not null AND T.MEDIATE_RESULT = '1' THEN T.CNT END),0)/\n"
				+ " NVL(SUM(CASE WHEN T.Inftype in ('1','6') AND T.Accepttime is not null THEN T.CNT END),0)end),4)*100 AS 调解成功率,\n"
				+ "NVL(SUM(CASE WHEN T.Inftype in ('1','6') AND T.Accepttime is not null AND T.PUTONCASE = '1' THEN T.CNT END),0) AS 投诉立案数,\n"
				+ "round((case when NVL(SUM(CASE WHEN T.Inftype in ('1','6') AND T.Accepttime is not null THEN T.CNT END),0)=0 then 0 else\n"
				+ "NVL(SUM(CASE WHEN T.Inftype in ('1','6') AND T.Accepttime is not null AND T.PUTONCASE = '1' THEN T.CNT END),0)/\n"
				+ "NVL(SUM(CASE WHEN T.Inftype in ('1','6') AND T.Accepttime is not null THEN T.CNT END),0)end),4)*100 AS 投诉立案率\n"
				+ "  FROM (\n"
				+ "SELECT C.Acctype,A.Finishtime,D.Disam,D.Redecolos,D.MEDIATE_RESULT,C.PUTONCASE,\n"
				+ "       A.Inftype,A.REGTIME,A.Accepttime, COUNT(1) AS CNT\n"
				+ "  FROM DC_CPR_INFOWARE A,DC_CPR_INVOLVED_OBJECT B,DC_CPR_FEEDBACK C,DC_CPR_MEDIATION D\n"
				+ "  where A.INVOBJID=B.INVOBJID\n"
				+ "    and C.FEEDBACKID=A.FEEDBACKID\n"
				+ "    and C.MEDIATIONID=D.MEDIATIONID\n"
				+ "    and B.Businesstype = 'ZH21'\n"
				+ " GROUP BY C.Acctype,A.Inftype,A.REGTIME,A.Accepttime,A.Finishtime,D.Disam,D.Redecolos,D.MEDIATE_RESULT,C.PUTONCASE) T\n"
				+ " where t.regtime>= to_date(?,'yyyy-mm-dd')\n"
				+ " and t.regtime <= to_date(?,'yyyy-mm-dd')";
		try {
			list1 = dao.queryForList(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("12365投诉处置工作效率与工作效果分析表", "WDY", i == 1 ? "查看报表"
				: "下载报表", sql, beginTime + "," + endTime, req);
		return list1;
	}

	public List<Map> DaJiaRen(String beginTime, String endTime, int i,
			HttpServletRequest req) {
		List list = new ArrayList();
		List<Map> list1 = new ArrayList<Map>();
		list.add(beginTime);
		list.add(endTime);
		String sql = "--职业打假人（表8）\n"
				+ "SELECT\n"
				+ "'深圳市市场监督管理局' AS 单位名称,\n"
				+ "NVL(SUM(CASE WHEN T.PERSNAME is not null THEN T.CNT END),0)  AS 本地登记的职业打假人数,\n"
				+ "NVL(SUM(CASE WHEN T.Inftype in ('1','6')  THEN T.CNT END),0)  AS 投诉数,\n"
				+ "(case when NVL(SUM(CASE WHEN T.PERSNAME is not null THEN T.CNT END),0)=0 then 0 else\n"
				+ "NVL(SUM(CASE WHEN T.Inftype in ('1','6')  THEN T.CNT END),0)/\n"
				+ "NVL(SUM(CASE WHEN T.PERSNAME is not null THEN T.CNT END),0)end) AS 人均投诉数,\n"
				+ "NVL(SUM(CASE WHEN T.Inftype in ('1','6') AND T.Accepttime is not null THEN T.CNT END),0)  AS 投诉受理数,\n"
				+ "NVL(SUM(CASE WHEN T.Inftype in ('1','6') AND T.Accepttime is not null AND T.MEDIATE_RESULT = '1' THEN T.CNT END),0) AS 调解成功数,\n"
				+ "(case when NVL(SUM(CASE WHEN T.Inftype in ('1','6') AND T.Accepttime is not null THEN T.CNT END),0)=0 then 0 else\n"
				+ " NVL(SUM(CASE WHEN T.Inftype in ('1','6') AND T.Accepttime is not null AND T.MEDIATE_RESULT = '1' THEN T.CNT END),0)/\n"
				+ " NVL(SUM(CASE WHEN T.Inftype in ('1','6') AND T.Accepttime is not null THEN T.CNT END),0)end) AS 调解成功率,\n"
				+ "NVL(SUM(CASE WHEN T.Inftype in ('1','6') AND T.Accepttime is not null THEN T.Disam END),0) AS 提出索赔金额,\n"
				+ "(case when NVL(SUM(CASE WHEN T.PERSNAME is not null THEN T.CNT END),0)=0 then 0 else\n"
				+ "NVL(SUM(CASE WHEN T.Inftype in ('1','6') AND T.Accepttime is not null THEN T.Disam END),0)/\n"
				+ "NVL(SUM(CASE WHEN T.PERSNAME is not null THEN T.CNT END),0)end) AS 人均提出索赔金额,\n"
				+ "NVL(SUM(CASE WHEN T.Inftype in ('1','6') AND T.Accepttime is not null THEN T.Redecolos END),0) AS 实际赔付金额,\n"
				+ "(case when NVL(SUM(CASE WHEN T.PERSNAME is not null THEN T.CNT END),0)=0 then 0 else\n"
				+ "NVL(SUM(CASE WHEN T.Inftype in ('1','6') AND T.Accepttime is not null THEN T.Redecolos END),0)/\n"
				+ "NVL(SUM(CASE WHEN T.PERSNAME is not null THEN T.CNT END),0)end) AS 人均实际赔付金额,\n"
				+ "(case when NVL(SUM(CASE WHEN T.Inftype in ('1','6') AND T.Accepttime is not null THEN T.Disam END),0)=0 then 0 else\n"
				+ " NVL(SUM(CASE WHEN T.Inftype in ('1','6') AND T.Accepttime is not null THEN T.Redecolos END),0)/\n"
				+ " NVL(SUM(CASE WHEN T.Inftype in ('1','6') AND T.Accepttime is not null THEN T.Disam END),0)end) AS 赔付金额占索赔金额比例,\n"
				+ "NVL(SUM(CASE WHEN T.Inftype = '2'  THEN T.CNT END),0)  AS 举报数,\n"
				+ "(case when NVL(SUM(CASE WHEN T.PERSNAME is not null THEN T.CNT END),0)=0 then 0 else\n"
				+ "NVL(SUM(CASE WHEN T.Inftype = '2'  THEN T.CNT END),0)/\n"
				+ "NVL(SUM(CASE WHEN T.PERSNAME is not null THEN T.CNT END),0)end) AS 人均举报数,\n"
				+ "NVL(SUM(CASE WHEN T.Inftype = '2' AND T.ACCTYPE = '12' THEN T.CNT END),0) AS 查实数,\n"
				+ "(case when NVL(SUM(CASE WHEN T.Inftype = '2'  THEN T.CNT END),0)=0 then 0 else\n"
				+ "NVL(SUM(CASE WHEN T.Inftype = '2' AND T.ACCTYPE = '12' THEN T.CNT END),0)/\n"
				+ "NVL(SUM(CASE WHEN T.Inftype = '2'  THEN T.CNT END),0)end) AS 举报查实率\n"
				+ "  FROM (\n"
				+ "SELECT C.Acctype,A.Finishtime,D.Disam,D.Redecolos,D.MEDIATE_RESULT,C.PUTONCASE,E.PERSNAME,\n"
				+ "       A.Inftype,A.REGTIME,A.Accepttime, COUNT(1) AS CNT\n"
				+ "  FROM DC_CPR_INFOWARE A,DC_CPR_INVOLVED_OBJECT B,DC_CPR_FEEDBACK C,DC_CPR_MEDIATION D,DC_CPR_INFO_PROVIDER E\n"
				+ "  where A.INVOBJID=B.INVOBJID\n"
				+ "    and C.FEEDBACKID=A.FEEDBACKID\n"
				+ "    and C.MEDIATIONID=D.MEDIATIONID\n"
				+ "    and E.INFPROID=A.INFPROID\n"
				+ "    and B.Businesstype = 'ZH21'\n"
				+ "    and A.Ishotinform = '1'\n"
				+ " GROUP BY C.Acctype,A.Inftype,A.REGTIME,A.Accepttime,A.Finishtime,D.Disam,D.Redecolos,D.MEDIATE_RESULT,C.PUTONCASE,E.PERSNAME) T\n"
				+ "  where t.regtime>= to_date(?,'yyyy-mm-dd')\n"
				+ " and t.regtime <= to_date(?,'yyyy-mm-dd')";
		try {
			list1 = dao.queryForList(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("职业打假人", "WDY", i == 1 ? "查看报表" : "下载报表", sql,
				beginTime + "," + endTime, req);
		return list1;
	}

	public Map<String, Map> JiaGeJuBao(String beginTime, String endTime,
			String tBeginTime, String tEndTime, int i, HttpServletRequest req) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		// tBeginTime;// 同比开始时间
		// tEndTime ;// 同比结束时间
		if (tBeginTime == null || tBeginTime.length() == 0) {
			try {
				cal.setTime(sdf.parse(beginTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			cal.add(Calendar.YEAR, -1);//
			tBeginTime = sdf.format(cal.getTime());
		}

		if (tEndTime == null || tEndTime.length() == 0) {
			try {
				cal.setTime(sdf.parse(endTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			cal.add(Calendar.YEAR, -1);
			tEndTime = sdf.format(cal.getTime());
		}
		List list = new ArrayList();
		for (int j = 0; j < 5; j++) {
			list.add(beginTime);
			list.add(endTime);
			list.add(tBeginTime);
			list.add(tEndTime);
			list.add(beginTime);
			list.add(endTime);
			list.add(tBeginTime);
			list.add(tEndTime);
			list.add(tBeginTime);
			list.add(tEndTime);
		}
		String sql =

		"--001价格举报信息采集表１（第1段）\n"
				+ "select REPLACE(NVL(p.xiangmu1, '合计'), 'NULL', '') AS 项目,\n"
				+ "       NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                    AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                    THEN P.CNT END),0)  AS 数量,\n"
				+ "      round((case when NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                              AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                    THEN P.CNT END),0)=0 then 0\n"
				+ "            else ((NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                                AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                                THEN P.CNT END),0))-(NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                                                             AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                                                             THEN P.CNT END),0)))/\n"
				+ "                  (NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                                AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                                THEN P.CNT END),0)) end),4)*100 || '%' AS 同比\n"
				+ "from (\n"
				+ "select T.xiangmu1,T.REGTIME,count(1) AS CNT\n"
				+ "from\n"
				+ "(select A.REGTIME,\n"
				+ "     (case\n"
				+ "           when A.INFTYPE IN ('2','1') and B.Businesstype = 'ZH20' then '违法行为举报' end) xiangmu1\n"
				+ "from DC_CPR_INFOWARE A,DC_CPR_INQUIRY H,DC_CPR_INVOLVED_OBJECT B,dc_code.dc_12315_codedata c\n"
				+ "where A.INQUIRYID= H.INQUIRYID(+)\n"
				+ "  and A.INVOBJID=B.INVOBJID\n"
				+ "  and b.INVOBJTYPE=c.code\n"
				+ " and c.codetable='ZH20'\n"
				+ "  --AND C.CODE IS NOT NULL\n"
				+ "   --and a.inftype in ('1','2')\n"
				+ "   and a.ACCEPTTIME is not null\n"
				+ "   union all\n"
				+ "   select A.REGTIME,\n"
				+ "     (case when A.INFTYPE=3 and H.BUSINESSRANGE in ('31000000','30000000') then '价格政策咨询'end) xiangmu1\n"
				+ "from DC_CPR_INFOWARE A,DC_CPR_INQUIRY H,DC_CPR_INVOLVED_OBJECT B\n"
				+ "where A.INQUIRYID= H.INQUIRYID(+)\n"
				+ "  and A.INVOBJID=B.INVOBJID\n"
				+ "   and a.inftype ='3'\n"
				+ "  )T\n"
				+ "group by T.xiangmu1,T.REGTIME)P\n"
				+ "where p.xiangmu1 is not null\n"
				+ "group by ROLLUP(p.xiangmu1) \n"

				+ "union all\n"
				+

				" --002\n"
				+ " select REPLACE(NVL(p.xiangmu2, '合计'), 'NULL', '') AS 项目,\n"
				+ "       NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                    AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                    THEN P.CNT END),0)  AS 数量,\n"
				+ "      round((case when NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                              AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                    THEN P.CNT END),0)=0 then 0\n"
				+ "            else ((NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                                AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                                THEN P.CNT END),0))-(NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                                                             AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                                                             THEN P.CNT END),0)))/\n"
				+ "                  (NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                                AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                                THEN P.CNT END),0)) end),4)*100 ||'%' AS 同比\n"
				+ "from (select t.xiangmu2,T.REGTIME,count(1) AS CNT\n"
				+ "from\n"
				+ "(select A.REGTIME,\n"
				+ "     (case when A.Incform='d' then '来信'\n"
				+ "           when A.Incform='1' then '来电'\n"
				+ "           when A.Incform='b' then '电子邮件'\n"
				+ "           when A.Incform='3' then '来访'\n"
				+ "           when A.Incform='e' then '上级交办'\n"
				+ "           when A.Incform='g' then '部门转办' end) xiangmu2\n"
				+ "from DC_CPR_INFOWARE A,DC_CPR_INVOLVED_OBJECT B\n"
				+ "where A.INVOBJID=B.INVOBJId\n"
				+ "  and A.ACCEPTTIME is not null\n"
				+ "   and a.inftype in ('1','2')\n"
				+ "  and b.businesstype='ZH20'\n"
				+ "  union all\n"
				+ "  select A.REGTIME,\n"
				+ "     (case when A.Incform='d' then '来信'\n"
				+ "           when A.Incform='1' then '来电'\n"
				+ "           when A.Incform='b' then '电子邮件'\n"
				+ "           when A.Incform='3' then '来访'\n"
				+ "           when A.Incform='e' then '上级交办'\n"
				+ "           when A.Incform='g' then '部门转办' end) xiangmu2\n"
				+ "from DC_CPR_INFOWARE A,DC_CPR_INQUIRY H\n"
				+ "where H.INQUIRYID(+)=A.INQUIRYID\n"
				+ "   and a.inftype  ='3'\n"
				+ "   and H.BUSINESSRANGE in ('31000000','30000000'))T\n"
				+ "group by T.REGTIME,t.xiangmu2)P\n"
				+ "where p.xiangmu2 is not null\n"
				+ "group by p.xiangmu2 \n"

				+ "union all\n"
				+

				"--003\n"
				+

				"--003\n"
				+ "--价格举报信息采集表１（第三段）\n"
				+ "select REPLACE(NVL(p.xiangmu3, '合计'), 'NULL', '') AS 项目,\n"
				+ "       NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                    AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                    THEN P.CNT END),0)  AS 数量,\n"
				+ "      round((case when NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                              AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                    THEN P.CNT END),0)=0 then 0\n"
				+ "            else ((NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                                AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                                THEN P.CNT END),0))-(NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                                                             AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                                                             THEN P.CNT END),0)))/\n"
				+ "                  (NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                                AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                                THEN P.CNT END),0)) end),4)*100 || '%' AS 同比\n"
				+ "\n"
				+ "from (select xiangmu3,T.REGTIME,count(1) AS CNT\n"
				+ "from\n"
				+ "(select A.REGTIME,\n"
				+ "      (case when A.FINISHTIME is not null then '办结件数' end) xiangmu3\n"
				+ "from DC_CPR_INFOWARE A,DC_CPR_INVOLVED_OBJECT B ,DC_CPR_INQUIRY H\n"
				+ "where  A.INVOBJID=B.INVOBJId\n"
				+ "  and H.INQUIRYID=A.INQUIRYID\n"
				+ "  and A.ACCEPTTIME is not null\n"
				+ "   and a.inftype ='2'\n"
				+ "  and b.businesstype='ZH20'\n"
				+ "  union all\n"
				+ "  select A.REGTIME,\n"
				+ "      (case-- when A.FINISHTIME is not null and A.Inftype = '2'then '举报办结件数'\n"
				+ "       when A.FINISHTIME is not null  and H.BUSINESSRANGE in ('31000000','30000000') then '办结件数' end) xiangmu3\n"
				+ "from DC_CPR_INFOWARE A ,DC_CPR_INQUIRY H\n"
				+ "where  H.INQUIRYID(+)=A.INQUIRYID\n"
				+ "   and a.inftype ='3')T\n"
				+ "group by T.REGTIME,xiangmu3)P\n"
				+ "where p.xiangmu3 is not null\n"
				+ "group by p.xiangmu3 \n"

				+ "union all\n"
				+

				"--004\n"
				+ "select REPLACE(NVL(p.xiangmu4, '合计'), 'NULL', '') AS 项目,\n"
				+ "       NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                    AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                    THEN P.CNT END),0)  AS 数量,\n"
				+ "      round((case when NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                              AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                    THEN P.CNT END),0)=0 then 0\n"
				+ "            else ((NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                                AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                                THEN P.CNT END),0))-(NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                                                             AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                                                             THEN P.CNT END),0)))/\n"
				+ "                  (NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                                AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                                THEN P.CNT END),0)) end),4)*100 ||'%' AS 同比\n"
				+ "from (select xiangmu4,T.REGTIME,count(1) AS CNT\n"
				+ "from\n"
				+ "(select A.REGTIME,\n"
				+ "      (case when A.FINISHTIME is not null then '举报办结件数'\n"
				+ "       --when A.FINISHTIME is not null and A.Inftype = '3' and H.BUSINESSRANGE in ('31000000','30000000') then '咨询办结件数'\n"
				+ "       end) xiangmu4\n"
				+ "from DC_CPR_INFOWARE A,DC_CPR_INVOLVED_OBJECT B ,DC_CPR_INQUIRY H\n"
				+ "where  A.INVOBJID=B.INVOBJId\n"
				+ "  and H.INQUIRYID=A.INQUIRYID\n"
				+ "  and A.ACCEPTTIME is not null\n"
				+ "   and a.inftype ='2'\n"
				+ "  and b.businesstype='ZH20'\n"
				+ "  union all\n"
				+ "  select A.REGTIME,\n"
				+ "      (case-- when A.FINISHTIME is not null and A.Inftype = '2'then '举报办结件数'\n"
				+ "       when A.FINISHTIME is not null  and H.BUSINESSRANGE in ('31000000','30000000') then '咨询办结件数' end) xiangmu4\n"
				+ "from DC_CPR_INFOWARE A ,DC_CPR_INQUIRY H\n"
				+ "where  H.INQUIRYID(+)=A.INQUIRYID\n"
				+ "   and a.inftype ='3'\n"
				+ "  )T\n"
				+ "group by T.REGTIME,xiangmu4)P\n"
				+ "where p.xiangmu4 is not null\n"
				+ "group by p.xiangmu4 \n"

				+ "union all\n"
				+

				"--005\n"
				+ "--价格举报信息采集表１（第五段）\n"
				+ "select REPLACE(NVL(p.xiangmu5, '合计'), 'NULL', '') AS 项目,\n"
				+ "       NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                    AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                    THEN P.CNT END),0)  AS 数量,\n"
				+ "      round((case when NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                              AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                    THEN P.CNT END),0)=0 then 0\n"
				+ "            else ((NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                                AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                                THEN P.CNT END),0))-(NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                                                             AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                                                             THEN P.CNT END),0)))/\n"
				+ "                  (NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                                AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                                THEN P.CNT END),0)) end),4)*100 || '%' AS 同比\n"
				+ "\n"
				+ "from (select xiangmu5,T.REGTIME,count(1) AS CNT\n"
				+ "from\n"
				+ "(select A.REGTIME,\n"
				+ "      (case when A.FINISHTIME is not null and D.Acctype = '11' then '协调办结案件'\n"
				+ "      when A.FINISHTIME is not null and D.Acctype = '12' then '查处办结结案'end) xiangmu5\n"
				+ "from DC_CPR_INFOWARE A,DC_CPR_INVOLVED_OBJECT B,DC_CPR_FEEDBACK D\n"
				+ "where A.INVOBJID=B.INVOBJID\n"
				+ " and D.FEEDBACKID(+) = A.FEEDBACKID\n"
				+ "  and A.ACCEPTTIME is not null\n"
				+ "   and a.inftype in ('1','2')\n"
				+ "  and b.businesstype='ZH20')T\n"
				+ "group by T.REGTIME,xiangmu5)P\n"
				+ "where p.xiangmu5 is not null\n" + "group by p.xiangmu5";

		List<Map> list1 = new ArrayList<Map>();
		try {
			list1 = dao.queryForList(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		Map map = new HashMap();
		for (int j = 0; j < list1.size(); j++) {
			Map value = new HashMap();
			value.put("数量", list1.get(j).get("数量"));
			value.put("同比", list1.get(j).get("同比"));
			map.put(list1.get(j).get("项目"), value);
		}
		Map values = new HashMap();
		values.put("数量", "0");
		values.put("同比", "0%");
		String[] str = new String[] { "价格政策咨询", "违法行为举报", "来信", "来电", "电子邮件",
				"来访", "上级交办", "部门转办", "办结件数", "举报办结件数", "咨询办结件数", "协调办结案件",
				"查处办结结案" };
		for (int j = 0, jj = str.length; j < jj; j++) {

			if (map.get(str[j]) == null) {
				map.put(str[j], values);
			}
		}
		logop.logInfoYeWu("价格投诉举报信息采集表1", "WDY", i == 1 ? "查看报表" : "下载报表", sql,
				beginTime + "," + endTime + "," + tBeginTime + "," + tEndTime,
				req);
		return map;
	}

	public Map<String, List<Map>> JiaGeJuBao2(String beginTime, String endTime,
			String tBeginTime, String tEndTime, int i, HttpServletRequest req) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		if (tBeginTime == null || tBeginTime.length() == 0) {
			try {
				cal.setTime(sdf.parse(beginTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			cal.add(Calendar.YEAR, -1);//
			tBeginTime = sdf.format(cal.getTime());
		}

		if (tEndTime == null || tEndTime.length() == 0) {
			try {
				cal.setTime(sdf.parse(endTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			cal.add(Calendar.YEAR, -1);
			tEndTime = sdf.format(cal.getTime());
		}

		List list = new ArrayList();
		for (int j = 0; j < 4; j++) {
			list.add(beginTime);
			list.add(endTime);
			list.add(tBeginTime);
			list.add(tEndTime);
			list.add(beginTime);
			list.add(endTime);
			list.add(tBeginTime);
			list.add(tEndTime);
			list.add(tBeginTime);
			list.add(tEndTime);
		}
		String sql1 =

		"----价格举报信息采集表２（第一段）\n"
				+ "select (case when q.总数='0000合计' then o.name when  substr(q.总数,3,2)<>'00' then '　　　　'||o.name else '　　'||o.name end) as 项目,\n"
				+ "q.* from (\n"
				+ "select\n"
				+ "REPLACE(NVL(p.xiangmu, '0000合计'), 'NULL', '') AS 总数,\n"
				+ "NVL(SUM(CASE WHEN P.FINISHTIME is not null\n"
				+ "             AND P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "             AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "             THEN P.CNT END),0)  AS 办结数,\n"
				+ "round((case when NVL(SUM(CASE WHEN P.FINISHTIME is not null\n"
				+ "                   AND P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                   AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                   THEN P.CNT END),0)=0 then 0\n"
				+ "      else ((NVL(SUM(CASE WHEN P.FINISHTIME is not null\n"
				+ "                     AND P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                     AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                     THEN P.CNT END),0))-(NVL(SUM(CASE WHEN P.FINISHTIME is not null\n"
				+ "                                                  AND P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                                                  AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                                                  THEN P.CNT END),0)))/\n"
				+ "             (NVL(SUM(CASE WHEN P.FINISHTIME is not null\n"
				+ "                      AND P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                      AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                      THEN P.CNT END),0)) end),4)*100 ||'%'AS 办结同比,\n"
				+ "NVL(SUM(CASE WHEN P.ACCTYPE = '12'\n"
				+ "             AND P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "             AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "             THEN P.CNT END),0)  AS 查处数,\n"
				+ "round((case when NVL(SUM(CASE WHEN P.ACCTYPE = '12'\n"
				+ "                   AND P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                   AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                   THEN P.CNT END),0)=0 then 0\n"
				+ "      else ((NVL(SUM(CASE WHEN P.ACCTYPE = '12'\n"
				+ "                     AND P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                     AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                     THEN P.CNT END),0))-(NVL(SUM(CASE WHEN P.ACCTYPE = '12'\n"
				+ "                                                  AND P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                                                  AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                                                  THEN P.CNT END),0)))/\n"
				+ "             (NVL(SUM(CASE WHEN P.ACCTYPE = '12'\n"
				+ "                      AND P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                      AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                      THEN P.CNT END),0)) end),4)*100  ||'%'AS 查处同比,\n"
				+ "NVL(SUM(CASE  when P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "             AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "             THEN P.zhicai END),0)  AS 经济制裁,\n"
				+ "round((case when NVL(SUM(CASE when\n"
				+ "                    P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                   AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                   THEN P.zhicai END),0)=0 then 0\n"
				+ "      else ((NVL(SUM(CASE WHEN\n"
				+ "                      P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                     AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                     THEN P.zhicai END),0))-(NVL(SUM(CASE WHEN  P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                                                  AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                                                  THEN P.zhicai END),0)))/\n"
				+ "             (NVL(SUM(CASE WHEN  P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                      AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                      THEN P.zhicai END),0)) end),4)*100  ||'%'AS 制裁同比,\n"
				+ "\n"
				+ "NVL(SUM(CASE  when P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "             AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "             THEN P.tuihuan END),0)  AS 退还金额,\n"
				+ "round((case when NVL(SUM(CASE when\n"
				+ "                    P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                   AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                   THEN P.tuihuan END),0)=0 then 0\n"
				+ "      else ((NVL(SUM(CASE WHEN\n"
				+ "                      P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                     AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                     THEN P.tuihuan END),0))-(NVL(SUM(CASE WHEN  P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                                                  AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                                                  THEN P.tuihuan END),0)))/\n"
				+ "             (NVL(SUM(CASE WHEN  P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                      AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                      THEN P.tuihuan END),0)) end),4)*100  ||'%'AS 退还同比\n"
				+ "from (\n"
				+ "select T.xiangmu,T.REGTIME,T.FINISHTIME,T.ACCTYPE,t.zhicai zhicai,t.tuihuan tuihuan ,count(1) AS CNT\n"
				+ "from\n"
				+ "(select A.REGTIME,\n"
				+ "        A.FINISHTIME,\n"
				+ "        C.ACCTYPE,\n"
				+ "        f.SANCTIONAMOUNT zhicai,\n"
				+ "        f.RETURNAMOUNT tuihuan,\n"
				+ "      (case when substr(B.INVOBJTYPE,0,2)='01' then '0100'\n"
				+ "        when substr(B.INVOBJTYPE,0,2)='02' then '0200'\n"
				+ "        when substr(B.INVOBJTYPE,0,2)='03' then '0300'\n"
				+ "        when substr(B.INVOBJTYPE,0,2)='04' then '0400'\n"
				+ "        when substr(B.INVOBJTYPE,0,2)='05' then '0500'\n"
				+ "        when substr(B.INVOBJTYPE,0,2)='06' then '0600'\n"
				+ "        when substr(B.INVOBJTYPE,0,2)='07' then '0700'\n"
				+ "        when substr(B.INVOBJTYPE,0,2)='08' then '0800'\n"
				+ "        when substr(B.INVOBJTYPE,0,2)='09' then '0900'\n"
				+ "        when substr(B.INVOBJTYPE,0,2)='10' then '1000'\n"
				+ "        when substr(B.INVOBJTYPE,0,2)='11' then '1100'\n"
				+ "        when substr(B.INVOBJTYPE,0,2)='12' then '1200'\n"
				+ "        when substr(B.INVOBJTYPE,0,2)='13' then '1300'\n"
				+ "        when substr(B.INVOBJTYPE,0,2)='14' then '1400'\n"
				+ "        when  substr(B.INVOBJTYPE,0,2)='99'then '9900' end) xiangmu\n"
				+ "from DC_CPR_INFOWARE A,DC_CPR_INVOLVED_OBJECT B,DC_CPR_FEEDBACK C,dc_code.dc_12315_codedata t,dc_CPR_CASE_INFO E,dc_CPR_LEGAL_PUNISHMENT  F\n"
				+ "where A.INVOBJID=B.INVOBJID\n"
				+ "  and C.FEEDBACKID=A.FEEDBACKID\n"
				+ "  and E.CASEINFOID(+)=C.CASEINFOID\n"
				+ "  and F.LEGPUNID(+)=E.LEGPUNID\n"
				+ "  and A.INFTYPE ='2'\n"
				+ " and t.codetable='CH27'\n"
				+ " and A.Applbasque = t.code\n"
				+ " and B.BUSINESSTYPE='ZH20'\n"
				+ " and substr(t.code,0,2) in ('01','02','03','04','05','06','07','08','19','10','11','12','13','14','99')\n"
				+ "union all\n"
				+ "select A.REGTIME,\n"
				+ "        A.FINISHTIME,\n"
				+ "        C.ACCTYPE,\n"
				+ "        f.SANCTIONAMOUNT zhicai,\n"
				+ "        f.RETURNAMOUNT tuihuan,\n"
				+ "      B.INVOBJTYPE xiangmu\n"
				+ "from DC_CPR_INFOWARE A,DC_CPR_INVOLVED_OBJECT B,DC_CPR_FEEDBACK C,dc_code.dc_12315_codedata t,dc_CPR_CASE_INFO E,dc_CPR_LEGAL_PUNISHMENT  F\n"
				+ "where A.INVOBJID=B.INVOBJID\n"
				+ "  and C.FEEDBACKID=A.FEEDBACKID\n"
				+ "  and E. CASEINFOID(+)=C. CASEINFOID\n"
				+ "   and F.LEGPUNID(+)=E.LEGPUNID\n"
				+ "  and A.INFTYPE ='2'\n"
				+ " and t.codetable='CH27'\n"
				+ " and A.Applbasque = t.code\n"
				+ " and B.BUSINESSTYPE='ZH20'\n"
				+ " and substr(t.code,0,2) in ('01','02','03','04','05','06','07','08','19','10','11','12','13','14','99'))T\n"
				+ "where T.xiangmu is not null\n"
				+ "group by T.xiangmu,T.REGTIME,T.FINISHTIME,T.ACCTYPE,t.zhicai,t.tuihuan)P\n"
				+ "group by rollup(p.xiangmu) )q,\n"
				+ "(SELECT code，name FROM dc_code.dc_12315_codedata  WHERE CODETABLE='ZH20'union all\n"
				+ "select '0000合计' as code,'价格检查' as name from dual) o\n"
				+ "where q.总数=o.code\n" + "order by o.code";

		String sql2 = "----价格举报信息采集表２（第二段）\n"
				+ "select (case when q.总数='0000合计' then o.name when  substr(q.总数,3,2)<>'00' then '　　'||o.name else '　'||o.name end) as 项目,\n"
				+ "q.* from (\n"
				+ "select\n"
				+ "REPLACE(NVL(p.xiangmu, '0000合计'), 'NULL', '') AS 总数,\n"
				+ "NVL(SUM(CASE WHEN P.FINISHTIME is not null\n"
				+ "             AND P.REGTIME >= to_date(?,'yyyy-mm-dd') --当前开始时间\n"
				+ "             AND P.REGTIME <= to_date(?,'yyyy-mm-dd') --当前结束时间\n"
				+ "             THEN P.CNT END),0)  AS 办结数,\n"
				+ "round((case when NVL(SUM(CASE WHEN P.FINISHTIME is not null\n"
				+ "                   AND P.REGTIME >= to_date(?,'yyyy-mm-dd') --同比开始时间\n"
				+ "                   AND P.REGTIME <= to_date(?,'yyyy-mm-dd') --同比结束时间\n"
				+ "                   THEN P.CNT END),0)=0 then 0\n"
				+ "      else ((NVL(SUM(CASE WHEN P.FINISHTIME is not null\n"
				+ "                     AND P.REGTIME >= to_date(?,'yyyy-mm-dd') --当前开始时间\n"
				+ "                     AND P.REGTIME <= to_date(?,'yyyy-mm-dd') --当前结束时间\n"
				+ "                     THEN P.CNT END),0))-(NVL(SUM(CASE WHEN P.FINISHTIME is not null\n"
				+ "                                                  AND P.REGTIME >= to_date(?,'yyyy-mm-dd') --同比开始时间\n"
				+ "                                                  AND P.REGTIME <= to_date(?,'yyyy-mm-dd') --同比结束时间\n"
				+ "                                                  THEN P.CNT END),0)))/\n"
				+ "             (NVL(SUM(CASE WHEN P.FINISHTIME is not null\n"
				+ "                      AND P.REGTIME >= to_date(?,'yyyy-mm-dd') --同比开始时间\n"
				+ "                      AND P.REGTIME <= to_date(?,'yyyy-mm-dd') --同比结束时间\n"
				+ "                      THEN P.CNT END),0)) end),4)*100 ||'%'AS 办结同比,\n"
				+ "NVL(SUM(CASE WHEN P.ACCTYPE = '12'\n"
				+ "             AND P.REGTIME >= to_date(?,'yyyy-mm-dd') --当前开始时间\n"
				+ "             AND P.REGTIME <= to_date(?,'yyyy-mm-dd') --当前结束时间\n"
				+ "             THEN P.CNT END),0)  AS 查处数,\n"
				+ "\n"
				+ "round((case when NVL(SUM(CASE WHEN P.ACCTYPE = '12'\n"
				+ "                   AND P.REGTIME >= to_date(?,'yyyy-mm-dd') --同比开始时间\n"
				+ "                   AND P.REGTIME <= to_date(?,'yyyy-mm-dd') --同比结束时间\n"
				+ "                   THEN P.CNT END),0)=0 then 0\n"
				+ "      else ((NVL(SUM(CASE WHEN P.ACCTYPE = '12'\n"
				+ "                     AND P.REGTIME >= to_date(?,'yyyy-mm-dd') --当前开始时间\n"
				+ "                     AND P.REGTIME <= to_date(?,'yyyy-mm-dd') --当前结束时间\n"
				+ "                     THEN P.CNT END),0))-(NVL(SUM(CASE WHEN P.ACCTYPE = '12'\n"
				+ "                                                  AND P.REGTIME >= to_date(?,'yyyy-mm-dd') --同比开始时间\n"
				+ "                                                  AND P.REGTIME <= to_date(?,'yyyy-mm-dd') --同比结束时间\n"
				+ "                                                  THEN P.CNT END),0)))/\n"
				+ "             (NVL(SUM(CASE WHEN P.ACCTYPE = '12'\n"
				+ "                      AND P.REGTIME >= to_date(?,'yyyy-mm-dd') --同比开始时间\n"
				+ "                      AND P.REGTIME <= to_date(?,'yyyy-mm-dd') --同比结束时间\n"
				+ "                      THEN P.CNT END),0)) end),4)*100 ||'%'AS 查处同比,\n"
				+ "NVL(SUM(CASE  when P.REGTIME >= to_date(?,'yyyy-mm-dd') --当前开始时间\n"
				+ "             AND P.REGTIME <= to_date(?,'yyyy-mm-dd') --当前结束时间\n"
				+ "             THEN P.zhicai END),0)  AS 经济制裁,\n"
				+ "round((case when NVL(SUM(CASE when\n"
				+ "                    P.REGTIME >= to_date(?,'yyyy-mm-dd') --同比开始时间\n"
				+ "                   AND P.REGTIME <= to_date(?,'yyyy-mm-dd') --同比结束时间\n"
				+ "                   THEN P.zhicai END),0)=0 then 0\n"
				+ "      else ((NVL(SUM(CASE WHEN\n"
				+ "                      P.REGTIME >= to_date(?,'yyyy-mm-dd') --当前开始时间\n"
				+ "                     AND P.REGTIME <= to_date(?,'yyyy-mm-dd') --当前结束时间\n"
				+ "                     THEN P.zhicai END),0))-(NVL(SUM(CASE WHEN  P.REGTIME >= to_date(?,'yyyy-mm-dd') --同比开始时间\n"
				+ "                                                  AND P.REGTIME <= to_date(?,'yyyy-mm-dd') --同比结束时间\n"
				+ "                                                  THEN P.zhicai END),0)))/\n"
				+ "             (NVL(SUM(CASE WHEN  P.REGTIME >= to_date(?,'yyyy-mm-dd') --同比开始时间\n"
				+ "                      AND P.REGTIME <= to_date(?,'yyyy-mm-dd') --同比结束时间\n"
				+ "                      THEN P.zhicai END),0)) end),4)*100  ||'%'AS 制裁同比,\n"
				+ "\n"
				+ "NVL(SUM(CASE  when P.REGTIME >= to_date(?,'yyyy-mm-dd') --当前开始时间\n"
				+ "             AND P.REGTIME <= to_date(?,'yyyy-mm-dd') --当前结束时间\n"
				+ "             THEN P.tuihuan END),0)  AS 退还金额,\n"
				+ "round((case when NVL(SUM(CASE when\n"
				+ "                    P.REGTIME >= to_date(?,'yyyy-mm-dd') --同比开始时间\n"
				+ "                   AND P.REGTIME <= to_date(?,'yyyy-mm-dd') --同比结束时间\n"
				+ "                   THEN P.tuihuan END),0)=0 then 0\n"
				+ "      else ((NVL(SUM(CASE WHEN\n"
				+ "                      P.REGTIME >= to_date(?,'yyyy-mm-dd') --当前开始时间\n"
				+ "                     AND P.REGTIME <= to_date(?,'yyyy-mm-dd') --当前结束时间\n"
				+ "                     THEN P.tuihuan END),0))-(NVL(SUM(CASE WHEN  P.REGTIME >= to_date(?,'yyyy-mm-dd') --同比开始时间\n"
				+ "                                                  AND P.REGTIME <= to_date(?,'yyyy-mm-dd') --同比结束时间\n"
				+ "                                                  THEN P.tuihuan END),0)))/\n"
				+ "             (NVL(SUM(CASE WHEN  P.REGTIME >= to_date(?,'yyyy-mm-dd') --同比开始时间\n"
				+ "                      AND P.REGTIME <= to_date(?,'yyyy-mm-dd') --同比结束时间\n"
				+ "                      THEN P.tuihuan END),0)) end),4)*100  ||'%'AS 退还同比\n"
				+ "from (\n"
				+ "select T.xiangmu,T.REGTIME,T.FINISHTIME,T.ACCTYPE,t.zhicai zhicai,t.tuihuan tuihuan,count(1) AS CNT\n"
				+ "from\n"
				+ "(select A.REGTIME,\n"
				+ "        A.FINISHTIME,\n"
				+ "        C.ACCTYPE,\n"
				+ "        f.SANCTIONAMOUNT zhicai,\n"
				+ "        f.RETURNAMOUNT tuihuan,\n"
				+ "        t.code AS xiangmu\n"
				+ "from DC_CPR_INFOWARE A,DC_CPR_INVOLVED_OBJECT B,DC_CPR_FEEDBACK C,dc_code.dc_12315_codedata t,dc_CPR_CASE_INFO E,dc_CPR_LEGAL_PUNISHMENT  F\n"
				+ "where A.INVOBJID=B.INVOBJID\n"
				+ "  and C.FEEDBACKID=A.FEEDBACKID\n"
				+ "  and E.CASEINFOID(+)=C.CASEINFOID\n"
				+ "  and F.LEGPUNID(+)=E.LEGPUNID\n"
				+ "  and A.Applbasque = t.code\n"
				+ "  and A.INFTYPE ='2'\n"
				+ "  and B.BUSINESSTYPE='ZH20'\n"
				+ "  and t.codetable='CH27'\n"
				+ "  and substr(t.code,0,2) in ('01','02','03','04','05','06','07','08','19','10','11','12','13','14','99')\n"
				+ "  union all\n"
				+ "  select A.REGTIME,\n"
				+ "        A.FINISHTIME,\n"
				+ "        C.ACCTYPE,\n"
				+ "        f.SANCTIONAMOUNT zhicai,\n"
				+ "        f.RETURNAMOUNT tuihuan,\n"
				+ "       (case when substr(t.code,0,2)='01' then '0100'\n"
				+ "        when substr(t.code,0,2)='02' then '0200'\n"
				+ "        when substr(t.code,0,2)='03' then '0300'\n"
				+ "        when substr(t.code,0,2)='04' then '0400'\n"
				+ "        when substr(t.code,0,2)='05' then '0500'\n"
				+ "        when substr(t.code,0,2)='06' then '0600'\n"
				+ "        when substr(t.code,0,2)='07' then '0700'\n"
				+ "        when substr(t.code,0,2)='08' then '0800'\n"
				+ "        when substr(t.code,0,2)='09' then '0900'\n"
				+ "        when substr(t.code,0,2)='10' then '1000'\n"
				+ "        when substr(t.code,0,2)='11' then '1100'\n"
				+ "        when substr(t.code,0,2)='12' then '1200'\n"
				+ "        when substr(t.code,0,2)='13' then '1300'\n"
				+ "        when substr(t.code,0,2)='14' then '1400'\n"
				+ "        when substr(t.code,0,2)='31' then '3100'\n"
				+ "        when substr(t.code,0,2)='32' then '3200'\n"
				+ "        when substr(t.code,0,2)='33' then '3300'\n"
				+ "        when substr(t.code,0,2)='34' then '3400'\n"
				+ "        when substr(t.code,0,2)='35' then '3500'\n"
				+ "        when substr(t.code,0,2)='36' then '3600'\n"
				+ "        when substr(t.code,0,2)='37' then '3700'\n"
				+ "        when substr(t.code,0,2)='38' then '3800'\n"
				+ "        when substr(t.code,0,2)='39' then '3900'\n"
				+ "        when substr(t.code,0,2)='49' then '4900'\n"
				+ "        when substr(t.code,0,2)='14' then '1400'\n"
				+ "        when  substr(t.code,0,2)='99'then '9900' end) AS xiangmu\n"
				+ "from DC_CPR_INFOWARE A,DC_CPR_INVOLVED_OBJECT B,DC_CPR_FEEDBACK C,dc_code.dc_12315_codedata t,dc_CPR_CASE_INFO E,dc_CPR_LEGAL_PUNISHMENT  F\n"
				+ "where A.INVOBJID=B.INVOBJID\n"
				+ "  and C.FEEDBACKID=A.FEEDBACKID\n"
				+ "  and A.Applbasque = t.code\n"
				+ "  and E.CASEINFOID(+)=C.CASEINFOID\n"
				+ "  and F.LEGPUNID(+)=E.LEGPUNID\n"
				+ "  and A.INFTYPE ='2'\n"
				+ "  and B.BUSINESSTYPE='ZH20'\n"
				+ "  and t.codetable='CH27'\n"
				+ "  and substr(t.code,0,2) in ('01','02','03','04','05','06','07','08','19','10','11','12','13','14','99')\n"
				+ ")T\n"
				+ "where T.xiangmu is not null\n"
				+ "group by T.xiangmu,T.REGTIME,T.FINISHTIME,T.ACCTYPE,t.zhicai,t.tuihuan)P\n"
				+ "group by ROLLUP(p.xiangmu) )q,\n"
				+ "(SELECT code，name FROM dc_code.dc_12315_codedata  WHERE CODETABLE='CH27'union all\n"
				+ "select '0000合计' as code,'全部' as name from dual) o\n"
				+ "where q.总数=o.code\n" + "order by o.code";

		Map<String, List<Map>> map = new HashMap<String, List<Map>>();
		try {
			map.put("第一部分", dao.queryForList(sql1, list));
			map.put("第二部分", dao.queryForList(sql2, list));
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("价格投诉举报信息采集表2", "WDY", i == 1 ? "查看报表" : "下载报表", sql1
				+ "\n" + "union all \n" + sql2, beginTime + "," + endTime + ","
				+ tBeginTime + "," + tEndTime, req);
		return map;
	}

	public Map<String, List<Map>> jiaGeTouSuRiBao(String beginTime, int i,
			HttpServletRequest req) {
		List list = new ArrayList();
		list.add(beginTime);
		Map<String, List<Map>> map1 = new HashMap<String, List<Map>>();
		String sql1 = "select (case when A.INFTYPE in ('1','6') then '申诉'\n"
				+ "             when A.INFTYPE = '2' then '举报' end) AS 类别,\n"
				+ "        (case when D.PERSNAME is null then ' ' else D.PERSNAME end ) AS 申诉举报人,\n"
				+ "        (case when C.INVNAME is null then ' ' else C.INVNAME end) AS 涉及主体,\n"
				+ "        (case when t.name is null then ' ' else t.name end) AS 主要内容,\n"
				+ "        (case when A.REGINO is null then ' ' else A.REGINO end) AS 登记编号,\n"
				+ "        (case when A.HANDEPNAME is null then ' ' else  A.HANDEPNAME end) AS 处理部门\n"
				+ "from DC_CPR_INFOWARE A,DC_CPR_INVOLVED_OBJECT B,DC_CPR_INVOLVED_MAIN C,DC_CPR_INFO_PROVIDER D,dc_code.dc_12315_codedata t\n"
				+ "where A.INVOBJID=B.INVOBJID\n"
				+ "  and C.INVMAIID=A.INVMAIID\n"
				+ "  and D.INFPROID=A.INFPROID\n"
				+ "  and A.Applbasque = t.code\n"
				+ "  and A.INFTYPE in ('1','2','6')\n"
				+ "  and B.BUSINESSTYPE='ZH20'\n"
				+ "  and t.codetable = 'CH27'\n" + "  and C.DIVISION =  '"
				+ " ?" + "'\n" + "  and to_char(A.Regtime,'yyyy-mm-dd') =? ";
		try {
			for (int j = 0; j < regs.length; j++) {

				String sql = "select (case when A.INFTYPE in ('1','6') then '申诉'\n"
						+ "             when A.INFTYPE = '2' then '举报' end) AS 类别,\n"
						+ "        (case when D.PERSNAME is null then ' ' else D.PERSNAME end ) AS 申诉举报人,\n"
						+ "        (case when C.INVNAME is null then ' ' else C.INVNAME end) AS 涉及主体,\n"
						+ "        (case when t.name is null then ' ' else t.name end) AS 主要内容,\n"
						+ "        (case when A.REGINO is null then ' ' else A.REGINO end) AS 登记编号,\n"
						+ "        (case when A.HANDEPNAME is null then ' ' else  A.HANDEPNAME end) AS 处理部门\n"
						+ "from DC_CPR_INFOWARE A,DC_CPR_INVOLVED_OBJECT B,DC_CPR_INVOLVED_MAIN C,DC_CPR_INFO_PROVIDER D,dc_code.dc_12315_codedata t\n"
						+ "where A.INVOBJID=B.INVOBJID\n"
						+ "  and C.INVMAIID=A.INVMAIID\n"
						+ "  and D.INFPROID=A.INFPROID\n"
						+ "  and A.Applbasque = t.code\n"
						+ "  and A.INFTYPE in ('1','2','6')\n"
						+ "  and B.BUSINESSTYPE='ZH20'\n"
						+ "  and t.codetable = 'CH27'\n"
						+ "  and C.DIVISION =  '"
						+ this.regcodes.get(regs[j])
						+ "'\n" + "  and to_char(A.Regtime,'yyyy-mm-dd') =? ";
				map1.put(this.regs[j], dao.queryForList(sql, list));
			}
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("价格投诉举报行业统计表", "WDY", i == 1 ? "查看报表" : "下载报表", sql1,
				beginTime + this.regs, req);
		return map1;
	}

	public List<Map> hangYeLeiXingTjX(String beginTime, String endTime,
			String tBeginTime, String tEndTime, String hBeginTime,
			String hEndTime, int i, HttpServletRequest req) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Map> list1 = new ArrayList<Map>();
		List list = new ArrayList();
		Calendar cal = Calendar.getInstance();
		if (tBeginTime == null || tBeginTime.length() == 0) {
			try {
				cal.setTime(sdf.parse(beginTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			cal.add(Calendar.YEAR, -1);//
			tBeginTime = sdf.format(cal.getTime());
		}
		if (tEndTime == null || tEndTime.length() == 0) {
			try {
				cal.setTime(sdf.parse(endTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			cal.add(Calendar.YEAR, -1);
			tEndTime = sdf.format(cal.getTime());
		}
		if (hBeginTime == null || hBeginTime.length() == 0) {
			try {
				cal.setTime(sdf.parse(beginTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			cal.add(Calendar.MONTH, -1);
			hBeginTime = sdf.format(cal.getTime());
		}
		if (hEndTime == null || hEndTime.length() == 0) {
			try {
				cal.setTime(sdf.parse(endTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			cal.add(Calendar.MONTH, -1);
			hEndTime = sdf.format(cal.getTime());
		}

		for (int j = 0; j < 6; j++) {
			list.add(beginTime);
			list.add(endTime);
		}
		for (int j = 0; j < 2; j++) {
			list.add(beginTime);
			list.add(endTime);
			list.add(tBeginTime);
			list.add(tEndTime);
			list.add(tBeginTime);
			list.add(tEndTime);
		}
		list.add(hBeginTime);
		list.add(hEndTime);
		list.add(hBeginTime);
		list.add(hEndTime);
		list.add(beginTime);
		list.add(endTime);
		list.add(hBeginTime);
		list.add(hEndTime);
		list.add(hBeginTime);
		list.add(hEndTime);
		String sql =

		"--行业类型统计报表-详情\n"
				+ "select (case when substr(q.code,3,2)<>'00' then '　　　　'||o.name else '　　'||o.name end) as 涉及客体类型,q.* from (select\n"
				+ "P.code,\n"
				+ "NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')                --当前开始时间\n"
				+ "              AND P.REGTIME <= to_date(?,'yyyy-mm-dd')                --当前结束时间\n"
				+ "              THEN P.shuliang END),0)  AS 数量,\n"
				+ "round((case when (select count(1) from DC_CPR_INFOWARE A,DC_CPR_INVOLVED_OBJECT B\n"
				+ "                 where  A.INVOBJID=B.INVOBJID\n"
				+ "                 and a.inftype in ('2','1')\n"
				+ "and a.ACCEPTTIME is not null \n"
				+ "                 and B.BUSINESSTYPE='ZH20'\n"
				+ "                 and a.regtime>= to_date(?,'yyyy-mm-dd')                    --当前开始时间\n"
				+ "                 and a.regtime <= to_date(?,'yyyy-mm-dd'))=0 then 1\n"
				+ "                else(NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')  --当前开始时间\n"
				+ "                AND P.REGTIME <= to_date(?,'yyyy-mm-dd')                     --当前结束时间\n"
				+ "                THEN P.shuliang END),0))/(select count(1) from DC_CPR_INFOWARE A,DC_CPR_INVOLVED_OBJECT B\n"
				+ "                 where  A.INVOBJID=B.INVOBJID\n"
				+ "                 and a.inftype in ('2','1')\n"
				+ "                 and a.ACCEPTTIME is not null \n"
				+ "                 and B.BUSINESSTYPE='ZH20'\n"
				+ "                 and a.regtime>= to_date(?,'yyyy-mm-dd')                    --当前开始时间\n"
				+ "                 and a.regtime <= to_date(?,'yyyy-mm-dd')) end),4)*100 AS 占上级总量的百分比,\n"
				+ "round((case when (select count(1) from DC_CPR_INFOWARE A,DC_CPR_INVOLVED_OBJECT B\n"
				+ "                 where  A.INVOBJID=B.INVOBJID\n"
				+ "                 and a.inftype in ('2','1')\n"
				+ "                 and B.BUSINESSTYPE='ZH20'\n"
				+ "                 and a.ACCEPTTIME is not null \n"
				+ "                 and a.regtime>= to_date(?,'yyyy-mm-dd')                    --当前开始时间\n"
				+ "                 and a.regtime <= to_date(?,'yyyy-mm-dd')) =0 then 1       --当前结束时间\n"
				+ "               else NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')    --当前开始时间\n"
				+ "               AND P.REGTIME <= to_date(?,'yyyy-mm-dd')                       --当前结束时间\n"
				+ "               THEN P.shuliang END),0)/(((select count(1) from DC_CPR_INFOWARE A,DC_CPR_INVOLVED_OBJECT B\n"
				+ "                 where  A.INVOBJID=B.INVOBJID\n"
				+ "                 and a.inftype in ('2','1')\n"
				+ "                 and a.ACCEPTTIME is not null \n"
				+ "                 and B.BUSINESSTYPE='ZH20'\n"
				+ "                 and a.regtime>= to_date(?,'yyyy-mm-dd')                    --当前开始时间\n"
				+ "                 and a.regtime <= to_date(?,'yyyy-mm-dd')))) end),4)*100    --当前结束时间\n"
				+ "                 AS 占物价举报总量百分比,\n"
				+ "NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')                                              --同比开始时间\n"
				+ "              AND P.REGTIME <= to_date(?,'yyyy-mm-dd')                                              --同比结束时间\n"
				+ "              THEN P.shuliang END),0)  AS 去年同期登记量,\n"
				+ "round((case when NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')                             --同比开始时间\n"
				+ "              AND P.REGTIME <= to_date(?,'yyyy-mm-dd')                                               --同比结束时间\n"
				+ "              THEN P.shuliang END),0)=0 then 0 else\n"
				+ "(NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')                                               --当前开始时间\n"
				+ "              AND P.REGTIME <= to_date(?,'yyyy-mm-dd')                                               --当前结束时间\n"
				+ "              THEN P.shuliang END),0)-NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')         --同比开始时间\n"
				+ "              AND P.REGTIME <= to_date(?,'yyyy-mm-dd')                                               --同比结束时间\n"
				+ "              THEN P.shuliang END),0))/\n"
				+ "              NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')                                 --同比开始时间\n"
				+ "              AND P.REGTIME <= to_date(?,'yyyy-mm-dd')                                               --同比结束时间\n"
				+ "              THEN P.shuliang END),0)end),4)*100 AS 比去年同期增减,\n"
				+ "NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')                                               --环比开始时间\n"
				+ "              AND P.REGTIME <= to_date(?,'yyyy-mm-dd')                                               --环比结束时间\n"
				+ "              THEN P.shuliang END),0)  AS 上期登记量,\n"
				+ "round((case when NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')                              --环比开始时间\n"
				+ "              AND P.REGTIME <= to_date(?,'yyyy-mm-dd')                                               --环比结束时间\n"
				+ "              THEN P.shuliang END),0)=0 then 0 else\n"
				+ "(NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')                                               --当前开始时间\n"
				+ "              AND P.REGTIME <= to_date(?,'yyyy-mm-dd')                                               --当前结束时间\n"
				+ "              THEN P.shuliang END),0)-NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')         --环比开始时间\n"
				+ "              AND P.REGTIME <= to_date(?,'yyyy-mm-dd')                                               --环比结束时间\n"
				+ "              THEN P.shuliang END),0))/\n"
				+ "              NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')                                 --环比开始时间\n"
				+ "              AND P.REGTIME <= to_date(?,'yyyy-mm-dd')                                               --环比结束时间\n"
				+ "              THEN P.shuliang END),0) end),4)*100 AS 比上一时间段增减\n"
				+ "--0 AS 比上一时间段增减\n"
				+ "from\n"
				+ "( select\n"
				+ " A.REGTIME,\n"
				+ "t.code as code,\n"
				+ " count(*) AS shuliang\n"
				+ "from DC_CPR_INFOWARE A,DC_CPR_INVOLVED_OBJECT B,dc_code.dc_12315_codedata t\n"
				+ "where A.INVOBJID=B.INVOBJID\n"
				+ "  and B.INVOBJTYPE = t.code\n"
				+ "  and A.INFTYPE in( '2','1')\n"
				+ "  and B.BUSINESSTYPE='ZH20'\n"
				+ "  and t.codetable = 'ZH20'\n"
				+ "and a.ACCEPTTIME is not null \n"
				+ "group by A.REGTIME,t.code\n"
				+ "union all\n"
				+ "select  A.REGTIME,\n"
				+ " (case when substr(t.code,0,2)='01' then '0100'\n"
				+ "        when substr(t.code,0,2)='02' then '0200'\n"
				+ "        when substr(t.code,0,2)='03' then '0300'\n"
				+ "        when substr(t.code,0,2)='04' then '0400'\n"
				+ "        when substr(t.code,0,2)='05' then '0500'\n"
				+ "        when substr(t.code,0,2)='06' then '0600'\n"
				+ "        when substr(t.code,0,2)='07' then '0700'\n"
				+ "        when substr(t.code,0,2)='08' then '0800'\n"
				+ "        when substr(t.code,0,2)='09' then '0900'\n"
				+ "        when substr(t.code,0,2)='10' then '1000'\n"
				+ "        when substr(t.code,0,2)='11' then '1100'\n"
				+ "        when substr(t.code,0,2)='12' then '1200'\n"
				+ "        when substr(t.code,0,2)='13' then '1300'\n"
				+ "        when substr(t.code,0,2)='14' then '1400'\n"
				+ "        when  substr(t.code,0,2)='99'then '9900' end) AS code,\n"
				+ "  count(*) AS shuliang\n"
				+ "from DC_CPR_INFOWARE A,DC_CPR_INVOLVED_OBJECT B,dc_code.dc_12315_codedata t\n"
				+ "where A.INVOBJID=B.INVOBJID\n"
				+ "  and B.INVOBJTYPE = t.code\n"
				+ "  and A.INFTYPE in ('1','2')\n"
				+ "and a.ACCEPTTIME is not null \n"
				+ "  and B.BUSINESSTYPE='ZH20'\n"
				+ "  and t.codetable = 'ZH20'\n"
				+ "group by t.code,A.REGTIME  )P\n"
				+ "group by P.code\n"
				+ "order by p.code) q,\n"
				+ "(SELECT * FROM dc_code.dc_12315_codedata  WHERE CODETABLE='ZH20') o\n"
				+ "where q.code=o.code\n" + "order by o.code";

		try {
			list1 = dao.queryForList(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("价格投诉举报行业统计表详情", "WDY", i == 1 ? "查看报表" : "下载报表",
				sql, beginTime + "," + endTime + "," + tBeginTime + ","
						+ tEndTime + "," + hBeginTime + "," + hEndTime, req);
		return list1;
	}

	public List<Map> hangYeLeiXingTj(String beginTime, String endTime,
			String tBeginTime, String tEndTime, String hBeginTime,
			String hEndTime, int i, HttpServletRequest req) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Map> list1 = new ArrayList<Map>();
		List list = new ArrayList();
		Calendar cal = Calendar.getInstance();
		if (tBeginTime == null || tBeginTime.length() == 0) {
			try {
				cal.setTime(sdf.parse(beginTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			cal.add(Calendar.YEAR, -1);//
			tBeginTime = sdf.format(cal.getTime());
		}
		if (tEndTime == null || tEndTime.length() == 0) {
			try {
				cal.setTime(sdf.parse(endTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			cal.add(Calendar.YEAR, -1);
			tEndTime = sdf.format(cal.getTime());
		}
		if (hBeginTime == null || hBeginTime.length() == 0) {
			try {
				cal.setTime(sdf.parse(beginTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			cal.add(Calendar.MONTH, -1);
			hBeginTime = sdf.format(cal.getTime());
		}
		if (hEndTime == null || hEndTime.length() == 0) {
			try {
				cal.setTime(sdf.parse(endTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			cal.add(Calendar.MONTH, -1);
			hEndTime = sdf.format(cal.getTime());
		}
		for (int j = 0; j < 6; j++) {
			list.add(beginTime);
			list.add(endTime);
		}
		for (int j = 0; j < 2; j++) {
			list.add(beginTime);
			list.add(endTime);
			list.add(tBeginTime);
			list.add(tEndTime);
			list.add(tBeginTime);
			list.add(tEndTime);
		}
		list.add(hBeginTime);
		list.add(hEndTime);
		list.add(hBeginTime);
		list.add(hEndTime);
		list.add(beginTime);
		list.add(endTime);
		list.add(hBeginTime);
		list.add(hEndTime);
		list.add(hBeginTime);
		list.add(hEndTime);
		String sql =

		"--行业类型统计报表-大类\n"
				+ "select (case when substr(q.code,3,2)<>'00' then '　　'||o.name else '　　'||o.name end)  as 涉及客体类型,q.* from (select\n"
				+ "P.code,\n"
				+ "NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "              AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "              THEN P.shuliang END),0)  AS 数量,\n"
				+ "round((case when (select count(1) from DC_CPR_INFOWARE A,DC_CPR_INVOLVED_OBJECT B\n"
				+ "                 where  A.INVOBJID=B.INVOBJID\n"
				+ "                 and a.inftype in ('2','1')\n"
				+ "                 and B.BUSINESSTYPE='ZH20'\n"
				+ "                 and a.regtime>= to_date(?,'yyyy-mm-dd')                    --当前开始时间\n"
				+ "                 and a.regtime <= to_date(?,'yyyy-mm-dd'))=0 then 1 else\n"
				+ "                     NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                    AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                    THEN P.shuliang END),0)/(select count(1) from DC_CPR_INFOWARE A,DC_CPR_INVOLVED_OBJECT B\n"
				+ "                 where  A.INVOBJID=B.INVOBJID\n"
				+ "                 and a.inftype in ('2','1')\n"
				+ "and a.ACCEPTTIME is not null \n"
				+ "                 and B.BUSINESSTYPE='ZH20'\n"
				+ "                 and a.regtime>= to_date(?,'yyyy-mm-dd')                    --当前开始时间\n"
				+ "                 and a.regtime <= to_date(?,'yyyy-mm-dd')) end),4)*100 AS 占上级总量的百分比,\n"
				+ "round((case when (select count(1) from DC_CPR_INFOWARE A,DC_CPR_INVOLVED_OBJECT B\n"
				+ "                 where  A.INVOBJID=B.INVOBJID\n"
				+ "                 and a.inftype in ('2','1')\n"
				+ "                 and B.BUSINESSTYPE='ZH20'\n"
				+ "                 and a.ACCEPTTIME is not null \n"
				+ "                 and a.regtime>= to_date(?,'yyyy-mm-dd')                    --当前开始时间\n"
				+ "                 and a.regtime <= to_date(?,'yyyy-mm-dd'))=0 then 1\n"
				+ "                    else NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                    AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                    THEN P.shuliang END),0)/(select count(1) from DC_CPR_INFOWARE A,DC_CPR_INVOLVED_OBJECT B\n"
				+ "                 where  A.INVOBJID=B.INVOBJID\n"
				+ "                 and a.inftype in ('1','2')\n"
				+ "                 and a.ACCEPTTIME is not null \n"
				+ "                 and B.BUSINESSTYPE='ZH20'\n"
				+ "                 and a.regtime>= to_date(?,'yyyy-mm-dd')                    --当前开始时间\n"
				+ "                 and a.regtime <= to_date(?,'yyyy-mm-dd')) end),4)*100 AS 占物价举报总量百分比,\n"
				+ "NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "              AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "              THEN P.shuliang END),0)  AS 去年同期登记量,\n"
				+ "round((case when NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "              AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "              THEN P.shuliang END),0)=0 then 0 else\n"
				+ "NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "              AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "              THEN P.shuliang END),0)-NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "                                                    AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "                                                    THEN P.shuliang END),0)/\n"
				+ "                                      NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "              AND P.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "              THEN P.shuliang END),0)end),4)*100 AS 比去年同期增减,\n"
				+ "NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')                                               --环比开始时间\n"
				+ "              AND P.REGTIME <= to_date(?,'yyyy-mm-dd')                                               --环比结束时间\n"
				+ "              THEN P.shuliang END),0)  AS 上期登记量,\n"
				+ "--0 AS 上期登记量,\n"
				+ "round((case when NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')                              --环比开始时间\n"
				+ "              AND P.REGTIME <= to_date(?,'yyyy-mm-dd')                                               --环比结束时间\n"
				+ "              THEN P.shuliang END),0)=0 then 0 else\n"
				+ "(NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')                                               --当前开始时间\n"
				+ "              AND P.REGTIME <= to_date(?,'yyyy-mm-dd')                                               --当前结束时间\n"
				+ "              THEN P.shuliang END),0)-NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')         --环比开始时间\n"
				+ "              AND P.REGTIME <= to_date(?,'yyyy-mm-dd')                                               --环比结束时间\n"
				+ "              THEN P.shuliang END),0))/\n"
				+ "              (NVL(SUM(CASE WHEN P.REGTIME >= to_date(?,'yyyy-mm-dd')                                 --环比开始时间\n"
				+ "              AND P.REGTIME <= to_date(?,'yyyy-mm-dd')                                               --环比结束时间\n"
				+ "              THEN P.shuliang END),0))end),4)*100 AS 比上一时间段增减\n"
				+ "--0 AS 比上一时间段增减\n"
				+ "from\n"
				+ "(\n"
				+ "select  A.REGTIME,\n"
				+ " (case when substr(t.code,0,2)='01' then '0100'\n"
				+ "        when substr(t.code,0,2)='02' then '0200'\n"
				+ "        when substr(t.code,0,2)='03' then '0300'\n"
				+ "        when substr(t.code,0,2)='04' then '0400'\n"
				+ "        when substr(t.code,0,2)='05' then '0500'\n"
				+ "        when substr(t.code,0,2)='06' then '0600'\n"
				+ "        when substr(t.code,0,2)='07' then '0700'\n"
				+ "        when substr(t.code,0,2)='08' then '0800'\n"
				+ "        when substr(t.code,0,2)='09' then '0900'\n"
				+ "        when substr(t.code,0,2)='10' then '1000'\n"
				+ "        when substr(t.code,0,2)='11' then '1100'\n"
				+ "        when substr(t.code,0,2)='12' then '1200'\n"
				+ "        when substr(t.code,0,2)='13' then '1300'\n"
				+ "        when substr(t.code,0,2)='14' then '1400'\n"
				+ "        when  substr(t.code,0,2)='99'then '9900' end) AS code,\n"
				+ "        count(*) AS shuliang\n"
				+ "from DC_CPR_INFOWARE A,DC_CPR_INVOLVED_OBJECT B,dc_code.dc_12315_codedata t\n"
				+ "where A.INVOBJID=B.INVOBJID\n"
				+ "  and B.INVOBJTYPE = t.code\n"
				+ "  and A.INFTYPE in ('2','1')\n"
				+ "  and a.ACCEPTTIME is not null \n"
				+ "  and B.BUSINESSTYPE='ZH20'\n"
				+ "  and t.codetable = 'ZH20'\n"
				+ "group by t.code,A.REGTIME  )P\n"
				+ "group by P.code\n"
				+ "order by p.code) q,\n"
				+ "(SELECT * FROM dc_code.dc_12315_codedata  WHERE CODETABLE='ZH20') o\n"
				+ "where q.code=o.code\n" + "--and q.数量<>'0'\n"
				+ "order by o.code";

		try {
			list1 = dao.queryForList(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("价格投诉举报行业统计表", "WDY", i == 1 ? "查看报表" : "下载报表", sql,
				beginTime + "," + endTime + "," + tBeginTime + "," + tEndTime
						+ "," + hBeginTime + "," + hEndTime, req);
		return list1;
	}

	public List<Map> shenSuJuBao_12358(String beginTime, String endTime, int i,
			HttpServletRequest req) {
		List list = new ArrayList();
		List<Map> list1 = new ArrayList<Map>();
		list.add(beginTime);
		list.add(endTime);
		list.add(beginTime);
		list.add(endTime);
		String sql = "--12358申诉举报统计报表\n"
				+ "select (case when q.总数='0000合计' then o.name when  substr(q.总数,3,2)<>'00' then '　　　　'||o.name else '　　'||o.name end) as 项目,\n"
				+ "q.* from (\n"
				+ "select\n"
				+ "REPLACE(NVL(p.code, '0000合计'), 'NULL', '') AS 总数,\n"
				+ "NVL(SUM(CASE WHEN p.HANDEPNAME = '价检局' AND P.INFTYPE  ='1' THEN P.shuliang END),0)  AS 价检申诉,\n"
				+ "NVL(SUM(CASE WHEN p.HANDEPNAME = '价检局'  AND P.INFTYPE = '2' THEN P.shuliang END),0)  AS 价检举报,\n"
				+ "NVL(SUM(CASE WHEN p.HANDEPNAME = '罗湖局'  AND P.INFTYPE ='1' THEN P.shuliang END),0)  AS 罗湖申诉,\n"
				+ "NVL(SUM(CASE WHEN p.HANDEPNAME = '罗湖局'  AND P.INFTYPE = '2' THEN P.shuliang END),0)  AS 罗湖举报,\n"
				+ "NVL(SUM(CASE WHEN p.HANDEPNAME = '福田局'  AND P.INFTYPE ='1' THEN P.shuliang END),0)  AS 福田申诉,\n"
				+ "NVL(SUM(CASE WHEN p.HANDEPNAME = '福田局'  AND P.INFTYPE = '2' THEN P.shuliang END),0)  AS 福田举报,\n"
				+ "NVL(SUM(CASE WHEN p.HANDEPNAME = '南山局'  AND P.INFTYPE ='1' THEN P.shuliang END),0)  AS 南山申诉,\n"
				+ "NVL(SUM(CASE WHEN p.HANDEPNAME = '南山局'  AND P.INFTYPE = '2' THEN P.shuliang END),0)  AS 南山举报,\n"
				+ "NVL(SUM(CASE WHEN p.HANDEPNAME = '宝安局'  AND P.INFTYPE = '1' THEN P.shuliang END),0)  AS 宝安申诉,\n"
				+ "NVL(SUM(CASE WHEN p.HANDEPNAME = '宝安局'  AND P.INFTYPE = '2' THEN P.shuliang END),0)  AS 宝安举报,\n"
				+ "NVL(SUM(CASE WHEN p.HANDEPNAME = '龙岗局'  AND P.INFTYPE ='1' THEN P.shuliang END),0)  AS 龙岗申诉,\n"
				+ "NVL(SUM(CASE WHEN p.HANDEPNAME = '龙岗局'  AND P.INFTYPE = '2' THEN P.shuliang END),0)  AS 龙岗举报,\n"
				+ "NVL(SUM(CASE WHEN p.HANDEPNAME = '盐田局'  AND P.INFTYPE = '1' THEN P.shuliang END),0)  AS 盐田申诉,\n"
				+ "NVL(SUM(CASE WHEN p.HANDEPNAME = '盐田局'  AND P.INFTYPE = '2' THEN P.shuliang END),0)  AS 盐田举报,\n"
				+ "NVL(SUM(CASE WHEN p.HANDEPNAME = '光明局'  AND P.INFTYPE ='1' THEN P.shuliang END),0)  AS 光明申诉,\n"
				+ "NVL(SUM(CASE WHEN p.HANDEPNAME = '光明局'  AND P.INFTYPE = '2' THEN P.shuliang END),0)  AS 光明举报,\n"
				+ "NVL(SUM(CASE WHEN p.HANDEPNAME = '坪山局'  AND P.INFTYPE ='1' THEN P.shuliang END),0)  AS 坪山申诉,\n"
				+ "NVL(SUM(CASE WHEN p.HANDEPNAME = '坪山局'  AND P.INFTYPE = '2' THEN P.shuliang END),0)  AS 坪山举报,\n"
				+ "NVL(SUM(CASE WHEN p.HANDEPNAME = '大鹏局'  AND P.INFTYPE = '1' THEN P.shuliang END),0)  AS 大鹏申诉,\n"
				+ "NVL(SUM(CASE WHEN p.HANDEPNAME = '大鹏局'  AND P.INFTYPE = '2' THEN P.shuliang END),0)  AS 大鹏举报,\n"
				+ "NVL(SUM(CASE WHEN p.HANDEPNAME = '龙华局' AND P.INFTYPE ='1' THEN P.shuliang END),0)  AS 龙华申诉,\n"
				+ "NVL(SUM(CASE WHEN p.HANDEPNAME = '龙华局'  AND P.INFTYPE = '2' THEN P.shuliang END),0)  AS 龙华举报,\n"
				+ "NVL(SUM(CASE WHEN p.HANDEPNAME = '注册局'  AND P.INFTYPE = '1' THEN P.shuliang END),0)  AS 注册局申诉,\n"
				+ "NVL(SUM(CASE WHEN p.HANDEPNAME = '注册局' AND P.INFTYPE = '2' THEN P.shuliang END),0)  AS 注册局举报,\n"
				+ "NVL(SUM(CASE WHEN p.HANDEPNAME = '稽查局'  AND P.INFTYPE ='1' THEN P.shuliang END),0)  AS 稽查局申诉,\n"
				+ "NVL(SUM(CASE WHEN p.HANDEPNAME = '稽查局'  AND P.INFTYPE = '2' THEN P.shuliang END),0)  AS 稽查局举报,\n"
				+ "NVL(SUM(P.shuliang ),0)  AS 合计行\n"
				+ "from\n"
				+ "(select t.code AS code,\n"
				+ "       (case when a.HANDEPNAME = '福田局' or a.Hanpardepname = '福田局' then '福田局'\n"
				+ "          when a.HANDEPNAME = '罗湖局' or a.Hanpardepname = '罗湖局' then '罗湖局'\n"
				+ "          when a.HANDEPNAME = '南山局' or a.Hanpardepname = '南山局' then '南山局'\n"
				+ "          when a.HANDEPNAME = '盐田局' or a.Hanpardepname = '盐田局' then '盐田局'\n"
				+ "          when a.HANDEPNAME = '宝安局' or a.Hanpardepname = '宝安局' then '宝安局'\n"
				+ "          when a.HANDEPNAME = '龙岗局' or a.Hanpardepname = '龙岗局' then '龙岗局'\n"
				+ "          when a.HANDEPNAME = '光明局' or a.Hanpardepname = '光明局' then '光明局'\n"
				+ "          when a.HANDEPNAME = '坪山局' or a.Hanpardepname = '坪山局' then '坪山局'\n"
				+ "          when a.HANDEPNAME = '龙华局' or a.Hanpardepname = '龙华局' then '龙华局'\n"
				+ "          when a.HANDEPNAME = '大鹏局' or a.Hanpardepname = '大鹏局' then '大鹏局'\n"
				+ "          when a.HANDEPNAME = '市价格监督检查局' or a.Hanpardepname = '市价格监督检查局' then '价监局'\n"
				+ "          when a.HANDEPNAME = '市市场稽查局' or a.Hanpardepname = '市市场稽查局' then '稽查局'\n"
				+ "          when a.HANDEPNAME = '市企业注册局' or a.Hanpardepname = '市企业注册局' then '注册局' end)as HANDEPNAME,\n"
				+ "        A.INFTYPE,\n"
				+ "        count(*) AS shuliang\n"
				+ "from DC_CPR_INFOWARE A,DC_CPR_INVOLVED_OBJECT B,DC_CPR_INVOLVED_MAIN C,dc_code.dc_12315_codedata t\n"
				+ "where A.INVOBJID=B.INVOBJID\n"
				+ "  and C.INVMAIID=A.INVMAIID\n"
				+ "  and B.INVOBJTYPE = t.code\n"
				+ "  and A.INFTYPE in ('1','2')\n"
				+ "  and B.BUSINESSTYPE='ZH20'\n"
				+ "  and t.codetable = 'ZH20'\n"
				+ "  and A.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "  and A.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "   and a.accepttime is not null\n"
				+ "group by t.code,A.INFTYPE,A.HANDEPNAME, a.Hanpardepname\n"
				+ "union all\n"
				+ "select (case when substr(t.code,0,2)='01' then '0100'\n"
				+ "        when substr(t.code,0,2)='02' then '0200'\n"
				+ "        when substr(t.code,0,2)='03' then '0300'\n"
				+ "        when substr(t.code,0,2)='04' then '0400'\n"
				+ "        when substr(t.code,0,2)='05' then '0500'\n"
				+ "        when substr(t.code,0,2)='06' then '0600'\n"
				+ "        when substr(t.code,0,2)='07' then '0700'\n"
				+ "        when substr(t.code,0,2)='08' then '0800'\n"
				+ "        when substr(t.code,0,2)='09' then '0900'\n"
				+ "        when substr(t.code,0,2)='10' then '1000'\n"
				+ "        when substr(t.code,0,2)='11' then '1100'\n"
				+ "        when substr(t.code,0,2)='12' then '1200'\n"
				+ "        when substr(t.code,0,2)='13' then '1300'\n"
				+ "        when substr(t.code,0,2)='14' then '1400'\n"
				+ "        when  substr(t.code,0,2)='99'then '9900'\n"
				+ "          else '11' end) AS code,\n"
				+ "        (case when a.HANDEPNAME = '福田局' or a.Hanpardepname = '福田局' then '福田局'\n"
				+ "          when a.HANDEPNAME = '罗湖局' or a.Hanpardepname = '罗湖局' then '罗湖局'\n"
				+ "          when a.HANDEPNAME = '南山局' or a.Hanpardepname = '南山局' then '南山局'\n"
				+ "          when a.HANDEPNAME = '盐田局' or a.Hanpardepname = '盐田局' then '盐田局'\n"
				+ "          when a.HANDEPNAME = '宝安局' or a.Hanpardepname = '宝安局' then '宝安局'\n"
				+ "          when a.HANDEPNAME = '龙岗局' or a.Hanpardepname = '龙岗局' then '龙岗局'\n"
				+ "          when a.HANDEPNAME = '光明局' or a.Hanpardepname = '光明局' then '光明局'\n"
				+ "          when a.HANDEPNAME = '坪山局' or a.Hanpardepname = '坪山局' then '坪山局'\n"
				+ "          when a.HANDEPNAME = '龙华局' or a.Hanpardepname = '龙华局' then '龙华局'\n"
				+ "          when a.HANDEPNAME = '大鹏局' or a.Hanpardepname = '大鹏局' then '大鹏局'\n"
				+ "          when a.HANDEPNAME = '市价格监督检查局' or a.Hanpardepname = '市价格监督检查局' then '价监局'\n"
				+ "          when a.HANDEPNAME = '市市场稽查局' or a.Hanpardepname = '市市场稽查局' then '稽查局'\n"
				+ "          when a.HANDEPNAME = '市企业注册局' or a.Hanpardepname = '市企业注册局' then '注册局' end)as HANDEPNAME,\n"
				+ "        A.INFTYPE,\n"
				+ "        count(*) AS shuliang\n"
				+ "from DC_CPR_INFOWARE A,DC_CPR_INVOLVED_OBJECT B,DC_CPR_INVOLVED_MAIN C,dc_code.dc_12315_codedata t\n"
				+ "where A.INVOBJID=B.INVOBJID\n"
				+ "  and C.INVMAIID=A.INVMAIID\n"
				+ "  and B.INVOBJTYPE = t.code\n"
				+ "  and A.INFTYPE in ('1','2')\n"
				+ "  and B.BUSINESSTYPE='ZH20'\n"
				+ "  and t.codetable = 'ZH20'\n"
				+ "  and A.REGTIME >= to_date(?,'yyyy-mm-dd')\n"
				+ "  and A.REGTIME <= to_date(?,'yyyy-mm-dd')\n"
				+ "  and a.accepttime is not null\n"
				+ "group by t.code,A.INFTYPE,A.HANDEPNAME, a.Hanpardepname\n"
				+ ")P\n"
				+ "group by ROLLUP(P.code)) q,\n"
				+ "(SELECT code，name FROM dc_code.dc_12315_codedata  WHERE CODETABLE='ZH20'union all\n"
				+ "select '0000合计' as code,'价格检查' as name from dual) o\n"
				+ "where q.总数=o.code(+)\n" + "order by o.code";

		try {
			list1 = dao.queryForList(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("12358投诉举报统计表", "WDY", i == 1 ? "查看报表" : "下载报表", sql,
				beginTime + "," + endTime, req);
		return list1;
	}

	public List<Map> qianHaiLeiJi(String endTime, int i, HttpServletRequest req) {
		IPersistenceDAO dao = this.getPersistenceDAO("tm_updata");
		List list = new ArrayList();
		List<Map> list1 = new ArrayList<Map>();
		list.add(endTime);
		String sql = "select\n"
				+ "      '企业数量' \"区划\",\n"
				+ "      sum(case when b.dom like '%南山%' and b.regstate='1' then 1\n"
				+ "               else 0\n"
				+ "          end) \"南山区\",\n"
				+ "      sum(case when b.dom like '%福田%' and b.regstate='1'  then 1\n"
				+ "               else 0\n"
				+ "          end) \"福田区\",\n"
				+ "      sum(case when b.dom like '%宝安%' and b.regstate='1'  then 1\n"
				+ "               else 0\n"
				+ "          end) \"宝安区\",\n"
				+ "      sum(case when b.dom like '%罗湖%' and b.regstate='1'  then 1\n"
				+ "               else 0\n"
				+ "          end) \"罗湖区\",\n"
				+ "      sum(case when b.dom like '%龙岗%' and b.regstate='1'  then 1\n"
				+ "               else 0\n"
				+ "          end) \"龙岗区\",\n"
				+ "      sum(case when b.dom like '%龙华%' and b.regstate='1'  then 1\n"
				+ "               else 0\n"
				+ "          end) \"龙华区\",\n"
				+ "      sum(case when b.dom like '%盐田%' and b.regstate='1'  then 1\n"
				+ "               else 0\n"
				+ "          end) \"盐田区\",\n"
				+ "      sum(case when b.dom like '%光明新区%' and b.regstate='1'  then 1\n"
				+ "               else 0\n"
				+ "          end) \"光明新区\",\n"
				+ "      sum(case when b.dom like '%坪山新区%' and b.regstate='1'  then 1\n"
				+ "               else 0\n"
				+ "          end) \"坪山新区\",\n"
				+ "      sum(case when b.dom like '%大鹏新区%' and b.regstate='1'  then 1\n"
				+ "               else 0\n"
				+ "          end) \"大鹏新区\"\n"
				+ "from tm_updata.e_baseinfo b,\n"
				+ "     dc_dc.dc_ns_enterprise_list l\n"
				+ "where b.pripid=l.pripid\n"
				+ "      and substr(b.oldenttype,1,1) in ('1','2','3','4','5','6','Y','W','A')\n"
				+ "      and b.dom like '%入驻%前海商务秘书%'\n"
				+ "      and b.estdate<=to_date(?,'yyyy-mm-dd')    --截止时间\n"
				+ "and l.addr_flag='1' and (b.dom like '%地址%' or b.dom like '%场所%')";
		try {
			list1 = dao.queryForList(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("累计入驻前海商务秘书公司且在营业执照上标注实际经营场所的企业区域分布", "WDY",
				i == 1 ? "查看报表" : "下载报表", sql, endTime, req);
		return list1;
	}

	public List<Map> qianHaiGangZi(String beginTime, String endTime, int i,
			HttpServletRequest req) {
		IPersistenceDAO dao = this.getPersistenceDAO("tm_updata");
		List list = new ArrayList();
		List<Map> list1 = new ArrayList<Map>();
		list.add(endTime);
		list.add(beginTime);
		list.add(endTime);
		String sql = "with x as(\n"
				+ "    select\n"
				+ "        (case when substr(b.industryco,1,2) between '71' and '72' then '租赁和商业服务业'\n"
				+ "              when substr(b.industryco,1,2) between '66' and '69' then '金融业'\n"
				+ "              when substr(b.industryco,1,2) between '51' and '52' then '批发和零售业'\n"
				+ "              when substr(b.industryco,1,2) between '63' and '65' then '信息传输、软件和信息技术服务业'\n"
				+ "              when substr(b.industryco,1,2) between '73' and '75' then '科学研究和技术服务业'\n"
				+ "              else '其他行业'\n"
				+ "         end) hylx,\n"
				+ "        (case when b.estdate<=to_date(?,'yyyy-mm-dd') and b.regstate='1' then 1\n"
				+ "              else 0\n"
				+ "         end) ljqy,   --截止时间\n"
				+ "        (case when b.estdate>=to_date(?,'yyyy-mm-dd') and b.estdate<=to_date(?,'yyyy-mm-dd') then 1\n"
				+ "              else 0\n"
				+ "         end) xzqy    --开始时间、截止时间\n"
				+ "\n"
				+ "\n"
				+ "    from e_baseinfo b,\n"
				+ "         dc_dc.dc_ns_enterprise_list l\n"
				+ "\n"
				+ "    where b.pripid=l.pripid\n"
				+ "         and l.addr_flag='1'\n"
				+ "         and substr(b.oldenttype,1,1) in ('5','6','Y','W')\n"
				+ "         and (b.pripid in (select distinct m.pripid from dc_dc.dc_ra_mer_base m,dc_dc.dc_ra_mer_invest i where m.id=i.main_tb_id and i.countrycode='344')\n"
				+ "              or b.country='344')\n"
				+ " )\n"
				+ "   select x.hylx \"行业类型\",\n"
				+ "         round(sum(x.ljqy)/(select sum(x.ljqy) from x),4)*100||'%' \"累计企业\",\n"
				+ "         round(sum(x.xzqy)/(select sum(x.xzqy) from x),4)*100||'%' \"新增企业\"\n"
				+ "  from x\n" + "  group by x.hylx\n" + "";
		try {
			list1 = dao.queryForList(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("前海片区港资背景企业行业分布", "WDY", i == 1 ? "查看报表" : "下载报表",
				sql, beginTime + "," + endTime, req);
		return list1;
	}

	public List<Map> qianHaiGangZiSl(String beginTime, String endTime, int i,
			HttpServletRequest req) {
		IPersistenceDAO dao = this.getPersistenceDAO("tm_updata");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List list = new ArrayList();
		List<Map> list1 = new ArrayList<Map>();
		String tBeginTime = null;
		String tEndTime = null;
		Calendar cal = Calendar.getInstance();
		if (tBeginTime == null || tBeginTime.length() == 0) {
			try {
				cal.setTime(sdf.parse(beginTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			cal.add(Calendar.YEAR, -1);//
			tBeginTime = sdf.format(cal.getTime());
		}
		if (tEndTime == null || tEndTime.length() == 0) {
			try {
				cal.setTime(sdf.parse(endTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			cal.add(Calendar.YEAR, -1);//
			tEndTime = sdf.format(cal.getTime());
		}
		list.add(beginTime);
		list.add(endTime);
		list.add(tBeginTime);
		list.add(tEndTime);
		list.add(endTime);
		list.add(beginTime);
		list.add(endTime);
		list.add(tBeginTime);
		list.add(tEndTime);
		list.add(endTime);
		String sql = "select xm \"项目\",\n"
				+ "       byxz \"本月新增\",\n"
				+ "       round((byxz-snbyxz)/snbyxz*100,2)||'%' \"同比增长\",\n"
				+ "       bylj \"本月累计\"\n"
				+ "from\n"
				+ "   (select\n"
				+ "       '企业总数' xm,\n"
				+ "       sum((case when b.estdate>=to_date(?,'yyyy-mm-dd') and b.estdate<=to_date(?,'yyyy-mm-dd') then 1\n"
				+ "             else 0\n"
				+ "        end)) byxz,  --开始时间、截止时间\n"
				+ "        sum((case when b.estdate>=to_date(?,'yyyy-mm-dd') and b.estdate<=to_date(?,'yyyy-mm-dd') then 1\n"
				+ "             else 0\n"
				+ "        end)) snbyxz,  --上年开始时间、上年截止时间\n"
				+ "       sum((case when b.estdate<=to_date(?,'yyyy-mm-dd') and b.regstate='1' then 1\n"
				+ "             else 0\n"
				+ "        end)) bylj  --截止时间\n"
				+ "   from e_baseinfo b,\n"
				+ "        dc_dc.dc_ns_enterprise_list l\n"
				+ "   where b.pripid=l.pripid\n"
				+ "        and l.addr_flag='1'\n"
				+ "        and substr(b.oldenttype,1,1) in ('5','6','Y','W')\n"
				+ "        and (b.pripid in (select distinct m.pripid from dc_dc.dc_ra_mer_base m,dc_dc.dc_ra_mer_invest i where m.id=i.main_tb_id and i.countrycode='344')\n"
				+ "             or b.country='344')\n"
				+ "  )\n"
				+ "union all\n"
				+ "select xm \"项目\",\n"
				+ "       byxz \"本月新增\",\n"
				+ "       round((byxz-snbyxz)/snbyxz*100,2)||'%' \"同比增长\",\n"
				+ "       bylj \"本月累计\"\n"
				+ "from\n"
				+ "  (select\n"
				+ "       '注册资本' xm,\n"
				+ "       sum((case when b.estdate>=to_date(?,'yyyy-mm-dd') and b.estdate<=to_date(?,'yyyy-mm-dd') then round(b.regcapusd*h.rat/100,2)\n"
				+ "             else 0\n"
				+ "        end))  byxz,  --开始时间、截止时间\n"
				+ "       sum((case when b.estdate>=to_date(?,'yyyy-mm-dd') and b.estdate<=to_date(?,'yyyy-mm-dd') then round(b.regcapusd*h.rat/100,2)\n"
				+ "             else 0\n"
				+ "        end)) snbyxz,  --上年开始时间、上年截止时间\n"
				+ "       sum((case when b.estdate<=to_date(?,'yyyy-mm-dd') and b.regstate='1' then round(b.regcapusd*h.rat/100,2)\n"
				+ "             else 0\n"
				+ "        end))  bylj  --截止时间\n"
				+ "   from e_baseinfo b,\n"
				+ "        dc_dc.dc_ns_enterprise_list l,\n"
				+ "        (select exchange_rate rat from (select * from dc_dc.dc_ns_exchange_rate order by sta_time desc) where rownum=1) h\n"
				+ "   where b.pripid=l.pripid\n"
				+ "        and l.addr_flag='1'\n"
				+ "        and substr(b.oldenttype,1,1) in ('5','6','Y','W')\n"
				+ "        and (b.pripid in (select distinct m.pripid from dc_dc.dc_ra_mer_base m,dc_dc.dc_ra_mer_invest i where m.id=i.main_tb_id and i.countrycode='344')\n"
				+ "             or b.country='344')\n" + "   )";
		try {
			list1 = dao.queryForList(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("前海片区港资背景企业数量", "WDY", i == 1 ? "查看报表" : "下载报表", sql,
				beginTime + "," + endTime, req);
		return list1;
	}

	public List<Map> qianHaiZongLiang(String beginTime, String endTime, int i,
			HttpServletRequest req) {
		IPersistenceDAO dao = this.getPersistenceDAO("tm_updata");
		List list = new ArrayList();
		List<Map> list1 = new ArrayList<Map>();
		list.add(endTime);
		list.add(beginTime);
		list.add(endTime);
		String sql = "with x as(\n"
				+ "  select\n"
				+ "        (case when substr(b.industryco,1,2) between '71' and '72' then '租赁和商业服务业'\n"
				+ "              when substr(b.industryco,1,2) between '66' and '69' then '金融业'\n"
				+ "              when substr(b.industryco,1,2) between '51' and '52' then '批发和零售业'\n"
				+ "              when substr(b.industryco,1,2) between '63' and '65' then '信息传输、软件和信息技术服务业'\n"
				+ "              when substr(b.industryco,1,2) between '73' and '75' then '科学研究和技术服务业'\n"
				+ "              else '其他行业'\n"
				+ "         end) hylx,\n"
				+ "        (case when b.estdate<=to_date(?,'yyyy-mm-dd') and b.regstate='1' then 1\n"
				+ "              else 0\n"
				+ "         end) ljqy,   --截止时间\n"
				+ "        (case when b.estdate>=to_date(?,'yyyy-mm-dd') and b.estdate<=to_date(?,'yyyy-mm-dd') then 1\n"
				+ "              else 0\n"
				+ "         end) xzqy  --开始时间、截止时间\n"
				+ "  from dc_dc.dc_ns_enterprise_list l,\n"
				+ "       e_baseinfo b\n"
				+ "  where b.pripid=l.pripid\n"
				+ "        and l.addr_flag='1'\n"
				+ "        and substr(b.oldenttype,1,1) in ('1','2','3','4','5','6','Y','W','A')\n"
				+ ")\n"
				+ "  select x.hylx \"行业类型\",\n"
				+ "         round(sum(x.ljqy)/(select sum(x.ljqy) from x),4)*100||'%' \"累计企业\",\n"
				+ "         round(sum(x.xzqy)/(select sum(x.xzqy) from x),4)*100||'%' \"新增企业\"\n"
				+ "  from x\n" + "  group by x.hylx";
		try {
			list1 = dao.queryForList(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("前海片区总量企业行业分布", "WDY", i == 1 ? "查看报表" : "下载报表", sql,
				beginTime + "," + endTime, req);
		return list1;
	}

	public List<Map> qianHaiXinZeng(String beginTime, String endTime, int i,
			HttpServletRequest req) {
		IPersistenceDAO dao = this.getPersistenceDAO("tm_updata");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List list = new ArrayList();
		List<Map> list1 = new ArrayList<Map>();
		String tBeginTime = null;
		String tEndTime = null;
		Calendar cal = Calendar.getInstance();
		if (tBeginTime == null || tBeginTime.length() == 0) {
			try {
				cal.setTime(sdf.parse(beginTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			cal.add(Calendar.YEAR, -1);//
			tBeginTime = sdf.format(cal.getTime());
		}
		if (tEndTime == null || tEndTime.length() == 0) {
			try {
				cal.setTime(sdf.parse(endTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			cal.add(Calendar.YEAR, -1);//
			tEndTime = sdf.format(cal.getTime());
		}
		list.add(endTime);
		list.add(beginTime);
		list.add(endTime);
		list.add(beginTime);
		list.add(endTime);
		list.add(beginTime);
		list.add(endTime);
		list.add(tBeginTime);
		list.add(tEndTime);
		list.add(tBeginTime);
		list.add(tEndTime);
		String sql = "with x1 as(\n"
				+ " select\n"
				+ " sum(case when substr(b.oldenttype,1,1) in ('1','2','3','4','A') then 1\n"
				+ "            else 0\n"
				+ "       end ) nzhs,\n"
				+ " sum(case when b.oldenttype in ('1000','1100','1110','1120','1121','1122','1123','1130','1140','1151','1152','1153','1190','1200','1210','1211','1212','1213','1219','1220','1221','1222','1223','1229','1230','1300','1150',\n"
				+ "                                     '3000','3100','3101','3102','3103','3200','3201','3202','3203','3300','3400','3500','3501','3502','3503','3504','3505','3506','3507','3508','3509','3511','3512','3513','3600','3900','4530','4531','4532','4533','4540') then round(regcap,2)\n"
				+ "      else 0\n"
				+ "  end) nzzczb,\n"
				+ "  sum(case when substr(b.oldenttype,1,1) in ('5','6','Y','W') then 1\n"
				+ "            else 0\n"
				+ "       end ) wzhs,\n"
				+ " sum(case when b.oldenttype not in ('5800','5810','5820','5830','5840','5890','6800','6810','6820','6840','6830','6890','Y800','Y810','Y811','Y812','Y813','Y814','Y815','Y816','Y817','Y830','Y831','Y832','Y833','Y834','Y835','Y836','Y837','Y900','Y910','Y911','Y912','Y913','Y914','Y915','Y916','Y917','Y930','Y931','Y932','Y933','Y934','Y935','Y936','Y937','W800','W810','W811','W812','W830','W900','W910','W911','W912','W930','W940','5310','6310','Y820','Y821','Y822','Y823','Y824','Y825','Y826','Y827','Y920','Y921','Y922','Y923','Y924','Y925','Y926','Y927','W820','W920','5841','5842','5843') then round(b.regcapusd*q.rat/100,2)\n"
				+ "      else 0\n"
				+ "  end) wzzczb\n"
				+ " from e_baseinfo b,\n"
				+ "      dc_dc.dc_ns_enterprise_list l，\n"
				+ "      (select r.exchange_rate rat from dc_dc.dc_ns_exchange_rate r where substr(to_char(r.sta_time,'yyyy-mm-dd'),1,7)=substr(?,1,7)) q    --结束日期月份的汇率\n"
				+ " where b.pripid=l.pripid\n"
				+ "       and b.estdate>=to_date(?,'yyyy-mm-dd')   --开始日期\n"
				+ "       and b.estdate<=to_date(?,'yyyy-mm-dd')   --结束日期\n"
				+ "),    --当期企业\n"
				+ "\n"
				+ " x2 as (\n"
				+ "  select\n"
				+ "       count(*) gths,\n"
				+ "       sum(case when p.fundam < '1000'  then round(p.fundam,2)\n"
				+ "                else 0\n"
				+ "           end) gtzczb\n"
				+ "  from e_pb_baseinfo p,\n"
				+ "       dc_dc.dc_ns_enterprise_list h\n"
				+ "  where p.pripid=h.pripid\n"
				+ "        and p.estdate>=to_date(?,'yyyy-mm-dd')  --开始日期\n"
				+ "        and p.estdate<=to_date(?,'yyyy-mm-dd')  --结束日期\n"
				+ "),  --当期个体\n"
				+ "\n"
				+ " x3 as (\n"
				+ "   select\n"
				+ "        count(*) gaths\n"
				+ "    from e_baseinfo c,\n"
				+ "         dc_dc.dc_ns_enterprise_list k\n"
				+ "\n"
				+ "    where c.pripid=k.pripid\n"
				+ "         and substr(c.oldenttype,1,1) in ('5','6','Y','W')\n"
				+ "         and (c.pripid in (select distinct m.pripid from dc_dc.dc_ra_mer_base m,dc_dc.dc_ra_mer_invest i where m.id=i.main_tb_id and i.countrycode in ('344','446','158'))\n"
				+ "              or c.country in ('344','446','158'))\n"
				+ "         and c.estdate>=to_date(?,'yyyy-mm-dd')  --开始日期\n"
				+ "         and c.estdate<=to_date(?,'yyyy-mm-dd')  --结束日期\n"
				+ "),  --当期港澳台\n"
				+ "\n"
				+ " x4 as(\n"
				+ " select\n"
				+ "  sum(case when substr(b.oldenttype,1,1) in ('1','2','3','4','A') then 1\n"
				+ "            else 0\n"
				+ "       end ) nzhs,\n"
				+ "  sum(case when substr(b.oldenttype,1,1) in ('5','6','Y','W') then 1\n"
				+ "            else 0\n"
				+ "       end ) wzhs\n"
				+ " from e_baseinfo b,\n"
				+ "      dc_dc.dc_ns_enterprise_list l\n"
				+ " where b.pripid=l.pripid\n"
				+ "       and b.estdate>=to_date(?,'yyyy-mm-dd')   --对应上年的开始日期\n"
				+ "       and b.estdate<=to_date(?,'yyyy-mm-dd')   --对应上年的结束日期\n"
				+ "),   --上期企业\n"
				+ "\n"
				+ " x5 as (\n"
				+ "  select\n"
				+ "       count(*) gths\n"
				+ "  from e_pb_baseinfo p,\n"
				+ "       dc_dc.dc_ns_enterprise_list h\n"
				+ "  where p.pripid=h.pripid\n"
				+ "        and p.estdate>=to_date(?,'yyyy-mm-dd')  --对应上年的开始日期\n"
				+ "        and p.estdate<=to_date(?,'yyyy-mm-dd')  --对应上年的结束日期\n"
				+ ")  --上期个体\n"
				+ " select x1.nzhs+x1.wzhs+x2.gths \"商事主体户数\",\n"
				+ "        round(((x1.nzhs+x1.wzhs+x2.gths)-(x4.nzhs+x4.wzhs+x5.gths))/(x4.nzhs+x4.wzhs+x5.gths)*100,2)||'%' \"商事主体同比\",\n"
				+ "        x1.nzzczb+x1.wzzczb+x2.gtzczb \"商事主体注册资本\",\n"
				+ "        x1.nzhs+x1.wzhs \"企业户数\",\n"
				+ "        round(((x1.nzhs+x1.wzhs)-(x4.nzhs+x4.wzhs))/(x4.nzhs+x4.wzhs)*100,2)||'%' \"企业同比\",\n"
				+ "        x1.nzzczb+x1.wzzczb \"企业注册资本\",\n"
				+ "        x2.gths \"个体户数\",\n"
				+ "        round((x2.gths-x5.gths)/x5.gths*100,2)||'%' \"个体同比\",\n"
				+ "        x2.gtzczb \"个体注册资本\",\n"
				+ "        x3.gaths \"港澳台户数\"\n" + " from x1,x2,x3,x4,x5\n";
		try {
			list1 = dao.queryForList(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		for (int j = 0; j < list1.size(); j++) {
			if (list1.get(j).get("商事主体户数") == null
					|| list1.get(j).get("商事主体户数").toString().length() == 0) {
				list1.get(j).put("商事主体户数", 0);
			}
			if (list1.get(j).get("商事主体同比") == null
					|| list1.get(j).get("商事主体同比").toString().length() == 0
					|| "%".equals(list1.get(j).get("商事主体同比").toString())) {
				list1.get(j).put("商事主体同比", "0%");
			}
			if (list1.get(j).get("商事主体注册资本") == null
					|| list1.get(j).get("商事主体注册资本").toString().length() == 0) {
				list1.get(j).put("商事主体注册资本", 0);
			}
			if (list1.get(j).get("企业户数") == null
					|| list1.get(j).get("企业户数").toString().length() == 0) {
				list1.get(j).put("企业户数", 0);
			}
			if (list1.get(j).get("企业同比") == null
					|| list1.get(j).get("企业同比").toString().length() == 0
					|| "%".equals(list1.get(j).get("企业同比").toString())) {
				list1.get(j).put("企业同比", "0%");
			}
			if (list1.get(j).get("企业注册资本") == null
					|| list1.get(j).get("企业注册资本").toString().length() == 0) {
				list1.get(j).put("企业注册资本", 0);
			}
			if (list1.get(j).get("个体户数") == null
					|| list1.get(j).get("个体户数").toString().length() == 0) {
				list1.get(j).put("个体户数", 0);
			}
			if (list1.get(j).get("个体同比") == null
					|| list1.get(j).get("个体同比").toString().length() == 0
					|| "%".equals(list1.get(j).get("个体同比").toString())) {
				list1.get(j).put("个体同比", "0%");
			}
			if (list1.get(j).get("个体注册资本") == null
					|| list1.get(j).get("个体注册资本").toString().length() == 0) {
				list1.get(j).put("个体注册资本", 0);
			}
			if (list1.get(j).get("港澳台户数") == null
					|| list1.get(j).get("港澳台户数").toString().length() == 0) {
				list1.get(j).put("港澳台户数", 0);
			}
		}
		logop.logInfoYeWu("前海蛇口新增数量统计", "WDY", i == 1 ? "查看报表" : "下载报表", sql,
				beginTime + "," + endTime, req);
		return list1;
	}

	public Map<String, Map<String, Map<String, String>>> jiChaBiao01(
			String beginTime, String endTime, int i, HttpServletRequest req) {
		List list = new ArrayList();
		String[] str1 = new String[] { "电话", "网络", "信件", "走访", "其他", "合计" };
		String[] str2 = new String[] { "投诉举报受理", "投诉举报不受理", "咨询", "建议", "合计" };
		String[] str3 = new String[] { "研制", "生产", "流通", "消费", "其他", "合计" };
		Map map = new HashMap();
		list.add(beginTime + " 00:00:00");
		list.add(endTime + " 23:59:59");
		String sql1 = "select REPLACE(NVL(t.xiangmu, '合计'), 'NULL', '') AS 分类,\n"
				+ "       sum((case\n"
				+ "             when substr(t.leixing, 0, 4) in\n"
				+ "                  ('1010', '1011', '1012', '1013', '1014', '1019') then t.shuliang  else  0 end)) 食品,\n"
				+ "       sum((case  when substr(t.leixing, 0, 3) = '105' then  t.shuliang  else  0 end)) 保健食品,\n"
				+ "       sum((case when substr(t.leixing, 0, 3) = '108' then t.shuliang else 0 end)) 药品,\n"
				+ "       sum((case when substr(t.leixing, 0, 3) = '114' then t.shuliang else 0 end)) 化妆品,\n"
				+ "       sum((case  when substr(t.leixing, 0, 3) = '110' then t.shuliang else 0 end)) 医疗器械,\n"
				+ "       NVL(SUM(t.shuliang),0) AS 合计行\n"
				+ "  from (select p.invobjtype leixing, p.xiangmu xiangmu, count(1) shuliang\n"
				+ "          from (select b.invobjtype,\n"
				+ "                       (case\n"
				+ "                         when A.incform in ('1','7') then '电话'\n"
				+ "                         when A.incform in ('6','b','k','m') then '网络'\n"
				+ "                         when A.incform in ('d','4') then '信件'\n"
				+ "                         when A.incform in ('3') then '走访'\n"
				+ "                         else '其他'     end) xiangmu\n"
				+ "                  from DC_CPR_INFOWARE        A,\n"
				+ "                       dc_CPR_FEEDBACK        c,\n"
				+ "                       dC_CPR_INVOLVED_OBJECT b\n"
				+ "                 where a.feedbackid = c.feedbackid\n"
				+ "                   and b.invobjid(+) = a.invobjid\n"
				+ "                   and a.inftype in ('1', '2', '3', '4')\n"
				+ "                   and a.regtime > to_date(?, 'yyyy-mm-dd hh24:mi:ss')\n"
				+ "                   and a.regtime < to_date(?, 'yyyy-mm-dd hh24:mi:ss')\n"
				+ "                   and b.invobjtype is not null\n"
				+ "                   and (substr(b.invobjtype,0,4) in('1010', '1011', '1012', '1013', '1014', '1019') or\n"
				+ "                   substr(b.invobjtype,0,3) in('105', '108', '114', '110')) ) p\n"
				+ "         group by rollup( p.invobjtype, p.xiangmu)) t\n"
				+ " group by t.xiangmu";
		String sql2 = "select REPLACE(NVL(t.xiangmu, '合计'), 'NULL', '') AS 分类,\n"
				+ "       sum((case\n"
				+ "             when substr(t.leixing, 0, 4) in\n"
				+ "                  ('1010', '1011', '1012', '1013', '1014', '1019') then t.shuliang  else  0 end)) 食品,\n"
				+ "       sum((case  when substr(t.leixing, 0, 3) = '105' then  t.shuliang  else  0 end)) 保健食品,\n"
				+ "       sum((case when substr(t.leixing, 0, 3) = '108' then t.shuliang else 0 end)) 药品,\n"
				+ "       sum((case when substr(t.leixing, 0, 3) = '114' then t.shuliang else 0 end)) 化妆品,\n"
				+ "       sum((case  when substr(t.leixing, 0, 3) = '110' then t.shuliang else 0 end)) 医疗器械,\n"
				+ "       NVL(SUM(t.shuliang), 0) AS 合计行\n"
				+ "  from (select p.invobjtype leixing, p.xiangmu xiangmu, count(1) shuliang\n"
				+ "          from (select b.invobjtype,\n"
				+ "                       (case\n"
				+ "                         when A.INFTYPE in ('1', '2') and\n"
				+ "                              c.acctype in ('10', '11', '12', '13') then\n"
				+ "                          '投诉举报受理'\n"
				+ "                         when A.INFTYPE in ('1', '2') and\n"
				+ "                              (c.acctype in ('20', '21', '22', '23', '24', '25', '26', '27', '28', '29') or c.acctype is null) then\n"
				+ "                          '投诉举报不受理'\n"
				+ "                         when A.INFTYPE = '3' then  '咨询'\n"
				+ "                           when a.inftype = '4' then '建议' end) xiangmu\n"
				+ "                  from DC_CPR_INFOWARE        A,\n"
				+ "                       dc_CPR_FEEDBACK        c,\n"
				+ "                       dC_CPR_INVOLVED_OBJECT b\n"
				+ "                 where a.feedbackid = c.feedbackid\n"
				+ "                   and b.invobjid(+) = a.invobjid\n"
				+ "                   and a.inftype in ('1', '2', '3', '4')\n"
				+ "                   and a.regtime > to_date(?, 'yyyy-mm-dd hh24:mi:ss')\n"
				+ "                   and a.regtime < to_date(?, 'yyyy-mm-dd hh24:mi:ss')\n"
				+ "                   and b.invobjtype is not null\n"
				+ "                   and (substr(b.invobjtype,0,4) in('1010', '1011', '1012', '1013', '1014', '1019') or\n"
				+ "                   substr(b.invobjtype,0,3) in('105', '108', '114', '110')) ) p\n"
				+ "         group by rollup( p.invobjtype, p.xiangmu)) t\n"
				+ " group by t.xiangmu";
		String sql3 = "select REPLACE(NVL(t.xiangmu, '合计'), 'NULL', '') AS 分类,\n"
				+ "      sum((case\n"
				+ "            when substr(t.leixing, 0, 4) in\n"
				+ "                 ('1010', '1011', '1012', '1013', '1014', '1019') then t.shuliang  else  0 end)) 食品,\n"
				+ "      sum((case  when substr(t.leixing, 0, 3) = '105' then  t.shuliang  else  0 end)) 保健食品,\n"
				+ "      sum((case when substr(t.leixing, 0, 3) = '108' then t.shuliang else 0 end)) 药品,\n"
				+ "      sum((case when substr(t.leixing, 0, 3) = '114' then t.shuliang else 0 end)) 化妆品,\n"
				+ "      sum((case  when substr(t.leixing, 0, 3) = '110' then t.shuliang else 0 end)) 医疗器械,\n"
				+ "      NVL(SUM(t.shuliang), 0) AS 合计行\n"
				+ " from (select p.invobjtype leixing, p.xiangmu xiangmu, count(1) shuliang\n"
				+ "         from (select b.invobjtype,\n"
				+ "                      (case\n"
				+ "                        when A.applbasque ='5301' then '研制'\n"
				+ "                        when A.applbasque ='5302' then '生产'\n"
				+ "                        when A.applbasque ='5303' then '流通'\n"
				+ "                        when A.applbasque ='5304' then '消费'\n"
				+ "                        else '其他' end) xiangmu\n"
				+ "                 from DC_CPR_INFOWARE        A,\n"
				+ "                      dc_CPR_FEEDBACK        c,\n"
				+ "                      dC_CPR_INVOLVED_OBJECT b\n"
				+ "                where a.feedbackid = c.feedbackid\n"
				+ "                  and b.invobjid(+) = a.invobjid\n"
				+ "                  and a.inftype in ('1', '2')\n"
				+ "                  and a.regtime > to_date(?, 'yyyy-mm-dd hh24:mi:ss')\n"
				+ "                  and a.regtime < to_date(?, 'yyyy-mm-dd hh24:mi:ss')\n"
				+ "                  and b.invobjtype is not null\n"
				+ "                  and c.acctype in ('10', '11', '12', '13')\n"
				+ "                  and (substr(b.invobjtype,0,4) in('1010', '1011', '1012', '1013', '1014', '1019') or\n"
				+ "                  substr(b.invobjtype,0,3) in('105', '108', '114', '110')) ) p\n"
				+ "        group by rollup( p.invobjtype, p.xiangmu)) t\n"
				+ "group by t.xiangmu";
		try {
			List<Map> list01 = dao.queryForList(sql1, list);

			List<Map> list02 = dao.queryForList(sql2, list);

			List<Map> list03 = dao.queryForList(sql3, list);

			Map map1 = new HashMap();
			Map map2 = new HashMap();
			Map map3 = new HashMap();
			// Map map13=new HashMap();
			for (int j = 0; j < list01.size(); j++) {
				Map map12 = list01.get(j);
				for (int j2 = 0; j2 < str1.length; j2++) {
					if (map12.get("分类").equals(str1[j2])) {
						Map map11 = new HashMap();
						map11.put("食品", map12.get("食品"));
						map11.put("保健食品", map12.get("保健食品"));
						map11.put("药品", map12.get("药品"));
						map11.put("化妆品", map12.get("化妆品"));
						map11.put("医疗器械", map12.get("医疗器械"));
						map11.put("合计行", map12.get("合计行"));
						map1.put(str1[j2], map11);
					} else {
						continue;
					}
				}
			}
			for (int j = 0; j < str1.length; j++) {
				if (map1.get(str1[j]) == null) {
					Map map11 = new HashMap();
					map11.put("食品", "0");
					map11.put("保健食品", "0");
					map11.put("药品", "0");
					map11.put("化妆品", "0");
					map11.put("医疗器械", "0");
					map11.put("合计行", "0");
					map1.put(str1[j], map11);
				}
			}

			for (int j = 0; j < list02.size(); j++) {
				Map map22 = list02.get(j);
				for (int j2 = 0; j2 < str2.length; j2++) {
					if (map22.get("分类").equals(str2[j2])) {
						Map map21 = new HashMap();
						map21.put("食品", map22.get("食品"));
						map21.put("保健食品", map22.get("保健食品"));
						map21.put("药品", map22.get("药品"));
						map21.put("化妆品", map22.get("化妆品"));
						map21.put("医疗器械", map22.get("医疗器械"));
						map21.put("合计行", map22.get("合计行"));
						map2.put(str2[j2], map21);
					} else {
						continue;
					}
				}
			}
			for (int j = 0; j < str2.length; j++) {
				if (map2.get(str2[j]) == null) {
					Map map21 = new HashMap();
					map21.put("食品", "0");
					map21.put("保健食品", "0");
					map21.put("药品", "0");
					map21.put("化妆品", "0");
					map21.put("医疗器械", "0");
					map21.put("合计行", "0");
					map2.put(str2[j], map21);
				}
			}

			for (int j = 0; j < list03.size(); j++) {
				Map map32 = list03.get(j);
				for (int j2 = 0; j2 < str3.length; j2++) {
					if (map32.get("分类").equals(str3[j2])) {
						Map map31 = new HashMap();
						map31.put("食品", map32.get("食品"));
						map31.put("保健食品", map32.get("保健食品"));
						map31.put("药品", map32.get("药品"));
						map31.put("化妆品", map32.get("化妆品"));
						map31.put("医疗器械", map32.get("医疗器械"));
						map31.put("合计行", map32.get("合计行"));
						map3.put(str3[j2], map31);
					} else {
						continue;
					}
				}
			}
			for (int j = 0; j < str3.length; j++) {
				if (map3.get(str3[j]) == null) {
					Map map31 = new HashMap();
					map31.put("食品", "0");
					map31.put("保健食品", "0");
					map31.put("药品", "0");
					map31.put("化妆品", "0");
					map31.put("医疗器械", "0");
					map31.put("合计行", "0");
					map3.put(str3[j], map31);
				}
			}
			map.put("第一部分", map1);
			map.put("第二部分", map2);
			map.put("第三部分", map3);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("稽查1表", "WDY", i == 1 ? "查看报表" : "下载报表", sql1 + "\n"
				+ sql2 + "\n" + sql3, beginTime + "," + endTime, req);
		return map;
	}

	// 前海企业入驻及注明场所统计
	public List<Map> qianHaiRuZhu(String beginTime, String endTime, int i,
			HttpServletRequest req) {
		IPersistenceDAO dao = this.getPersistenceDAO("dc_dc");
		List list = new ArrayList();
		List<Map> list1 = new ArrayList<Map>();
		list.add(beginTime);
		list.add(endTime+" 23:59:59");
		list.add(endTime+" 23:59:59");
		String sql =/* "select e.xinzengruzhu 新增入驻,\n"
				+ "round((case when e.xinzeng=0 then 1 else e.xinzengruzhu/e.xinzeng end ),4)*100||'%' 占比,\n"
				+ "e.leijiruzhu 累计入驻，\n"
				+ "round((case when e.leiji=0 then 1 else e.leijiruzhu/e.leiji end),4)*100||'%'  累计占比,\n"
				+ "e.zhumingchangsuo 注明场所\n"
				+ "from\n"
				+ "(select count(1) leiji，\n"
				+ "count(case when t.dom1 like '%入驻%前海商务秘书%' then 1 else null end) leijiruzhu,\n"
				+ "count(case when t.dom3 like '%入驻%前海商务秘书%' and  (t.dom3 like '%地址%' or t.dom3 like '%场所%')then 1 else null end ) zhumingchangsuo,\n"
				+ "count(case when t.time1>=to_date(?,'yyyy-mm-dd')\n"
				+ "           and t.time1<to_date(?,'yyyy-mm-dd')\n"
				+ "           then  1 else null end) xinzeng,\n"
				+ "count(case when t.time2>=to_date(?,'yyyy-mm-dd')\n"
				+ "           and t.time2<to_date(?,'yyyy-mm-dd')\n"
				+ "           and t.dom2 like '%入驻%前海商务秘书%'\n"
				+ "           then  1 else null end) xinzengruzhu\n"
				+ "\n"
				+ "from\n"
				+ "(select\n"
				+ "      b.dom dom1,\n"
				+ "      b.dom dom2,\n"
				+ "      b.dom dom3,\n"
				+ "      b.estdate time1,\n"
				+ "      b.estdate time2\n"
				+ "from tm_updata.e_baseinfo b,\n"
				+ "     dc_dc.dc_ns_enterprise_list l\n"
				+ "where b.pripid=l.pripid\n"
				+ "      and substr(b.oldenttype,1,1) in ('1','2','3','4','5','6','Y','W','A')\n"
				+ "      and b.estdate<to_date(?,'yyyy-mm-dd')    --截止时间\n"
				+ "and l.addr_flag='1' )t ) e\n";*/
				"select e.xinzengruzhu 新增入驻,\n" +
				"round((case when e.xinzeng=0 then 1 else e.xinzengruzhu/e.xinzeng end ),4)*100||'%' 占比,\n" + 
				"e.leijiruzhu 累计入驻，\n" + 
				"round((case when e.leiji=0 then 1 else e.leijiruzhu/e.leiji end),4)*100||'%'  累计占比,\n" + 
				"e.zhumingchangsuo 注明场所\n" + 
				"from\n" + 
				"(select sum(t.leijiflag) leiji，\n" + 
				"count(case when t.dom1 like '%入驻%前海商务秘书%' then 1 else null end) leijiruzhu,\n" + 
				"count(case when t.dom3 like '%入驻%前海商务秘书%' and  (t.dom3 like '%地址%' or t.dom3 like '%场所%')then 1 else null end ) zhumingchangsuo,\n" + 
				"sum(t.flag) xinzeng,\n" + 
				"sum(case  when t.dom2 like '%入驻%前海商务秘书%'   then  t.flag else 0 end) xinzengruzhu\n" + 
				"from\n" + 
				"(select\n" + 
				"      b.dom dom1,\n" + 
				"      b.dom dom2,\n" + 
				"\n" + 
				"      b.dom dom3,\n" + 
				"      b.estdate time1,\n" + 
				"      b.estdate time2,\n" + 
				"      (case when b.estdate >=to_date(?,'yyyy-mm-dd')  and b.regstate='1' then 1 else 0 end) flag, --开始时间\n" + 
				"      (case when  (( b.regstate='1') or (  b.regstate<>'1' and b.apprdate>to_date(?,'yyyy-mm-dd hh24:mi:ss') )) then 1 else 0 end )leijiflag\n" + 
				"from tm_updata.e_baseinfo b,--上一行，详细结束时间\n" + 
				"     dc_dc.dc_ns_enterprise_list l,\n" + 
				"      tm_updata.e_revoke r\n" + 
				"where b.pripid=l.pripid\n" + 
				"and   b.pripid=r.pripid(+)\n" + 
				"     and b.estdate<=to_date(?,'yyyy-mm-dd hh24:mi:ss')    --详细结束时间\n" + 
				"    and ((substr(b.oldenttype,1,2)  in ('45','39')\n" + 
				"              or (b.oldenttype in ('1151','1212','1222','2130','2151','2152','2212','2222'))\n" + 
				"              or (b.oldenttype in ('1121','1122','1123','1153','1190','1211','1219','1221','1229','2121','2122','2123','2153','2190','2211','2219','2221','2229','1150','2160'))  --总局为内资，其中1150 2160为新增归为其他私营有限责任公司\n" + 
				"              or (b.oldenttype in ('1110','2110') and substr(trim(b.specflag),8,1) <>'0')\n" + 
				"              or (b.oldenttype in ('1000','1100','1120','1130','1152','1200','1210','1220','1230','1300','2000','2100','2120','2200','2210','2220','2300','A000') and (substr(b.specflag,8,1)<>'0' or trim(b.specflag) is null))\n" + 
				"          )or\n" + 
				"( (substr(b.oldenttype,1,2) in ('30','31','32','33','34','35','36','40','41','42','43','44','46','47','48'))\n" + 
				"           or (b.oldenttype in ('1140','1213','1223','2140','2213','2223'))\n" + 
				"           or (b.oldenttype in ('1000','1100','1120','1130','1152','1200','1210','1220','1230','1300','2000','2100','2120','2200','2210','2220','2300','A000') and substr(b.specflag,8,1)='0')\n" + 
				"           or (b.oldenttype in ('1110','2110') and (substr(trim(b.specflag),8,1)='0' or trim(b.specflag) is null))  )\n" + 
				"           or ( substr(b.oldenttype,1,1) in ('5','6','Y','W')))\n" + 
				"           and l.addr_flag='1'\n" + 
				"        )t ) e";

		try {
			list1 = dao.queryForList(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("前海企业入驻及注明场所统计", "WDY", i == 1 ? "查看报表" : "下载报表",
				sql, beginTime + "," + endTime, req);
		return list1;
	}

	// 求取两个时间的差(天数)
	public int getTimeDifferenceDay(String beginTime, String endTime) {
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");
		long from = 0;
		long to = 0;
		try {
			from = simpleFormat.parse(beginTime).getTime();
			to = simpleFormat.parse(endTime).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int days = (int) ((from - to) / (1000 * 60 * 60 * 24));
		return days;
	}

	public Date getYearFirst(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}

}
