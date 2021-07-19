package com.gwssi.report.service;

import java.util.ArrayList;
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
public class HouseReportService extends BaseService implements ReportSource{
	//IPersistenceDAO dao = this.getPersistenceDAO("dc_dc");
	LogOperation logop = new LogOperation();
	@Override
	public ReportModel getReport(TCognosReportBO queryInfo) {
		return null;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> fenJuBiaoShi(String regorg, int i, HttpServletRequest req) {
		IPersistenceDAO dao = this.getPersistenceDAO("dc_dc");
		StringBuffer sb=new StringBuffer();
		List list=new ArrayList();
		List<Map> list1=new ArrayList<Map>();
		sb.append(
				"with x as(\n" +
						"select b.oldenttype,b.industryphy ,(case when b.regorg='440303' then '罗湖'\n" + 
						"                                         when b.regorg='440304' then '福田'\n" + 
						"                                         when b.regorg='440305' then '南山'\n" + 
						"                                         when b.regorg='440306' then '宝安'\n" + 
						"                                         when b.regorg='440307' then '龙岗'\n" + 
						"                                         when b.regorg='440308' then '盐田'\n" + 
						"                                         when b.regorg='440309' then '光明'\n" + 
						"                                         when b.regorg='440310' then '坪山'\n" + 
						"                                         when b.regorg='440342' then '龙华'\n" + 
						"                                         when b.regorg='440343' then '大鹏'\n" + 
						"                                         else '其他' end)regorg ,h.flag，b.specflag,1 as sumflag from dc_dc.dc_house_ent_housenum  h,tm_updata.e_baseinfo b  where h.pripid(+)=b.pripid ");
		if (regorg!=null&&regorg.length()!=0&&!"001".equals(regorg)) {
			sb.append("and b.regorg=? \n");
			list.add(regorg);
		}
		sb.append(")\n" +"select nvl(t.regorg,'合计') 分区,nvl(t.第一标记,0)第一标记,nvl(t.第一总数,'0')第一总数,to_char(round(nvl(t.第一标记,0)/nvl(t.第一总数,1),4)*100,'fm9990.09')||'%' 第一占比,\n" + 
						"nvl(t.第二标记,0)第二标记,nvl(t.第二总数,0)第二总数,to_char(round(nvl(t.第二标记,0)/nvl(t.第二总数,1),4)*100,'fm9990.09')||'%' 第二占比,\n" + 
						"nvl(t.第三标记,0)第三标记,nvl(t.第三总数,0)第三总数,to_char(round(nvl(t.第三标记,0)/nvl(t.第三总数,1),4)*100,'fm9990.09')||'%' 第三占比,\n" + 
						"nvl(t.外资标记,0)外资标记,nvl(t.外资总数,0)外资总数,to_char(round(nvl(t.外资标记,0)/nvl(t.外资总数,1),4)*100,'fm9990.09')||'%' 外资占比,\n" + 
						"nvl(t.内资标记,0)内资标记,nvl(t.内资总数,0)内资总数,to_char(round(nvl(t.内资标记,0)/nvl(t.内资总数,1),4)*100,'fm9990.09')||'%' 内资占比\n" + 
						"from (\n" + 
						"select x.regorg,sum(case when x.industryphy='A'and x.flag ='1' then 1 else null end) 第一标记,\n" + 
						"sum(case when x.industryphy='A' then 1 else null end) 第一总数,\n" + 
						"sum(case when x.industryphy in ('B','C','D','E') and x.flag='1' then 1 else null end)第二标记,\n" + 
						"sum(case when x.industryphy in ('B','C','D','E')  then 1 else null end)第二总数,\n" + 
						"sum(case when x.industryphy not in ('A','B','C','D','E ') and x.flag='1' then 1 else null end) 第三标记,\n" + 
						"sum(case when x.industryphy not in ('A','B','C','D','E ') then 1 else null end) 第三总数 ，\n" + 
						"sum(case when substr(trim(x.oldenttype),1,1) in ('5','6','Y','W') and x.flag ='1'then 1 else null end)外资标记,\n" + 
						"sum(case when substr(trim(x.oldenttype),1,1) in ('5','6','Y','W')then 1 else null end)外资总数,\n" + 
						"sum(case when ((x.oldenttype in ('4000','4100','4110','4200','4210','4300','4310','4311','4312','4313','4400','4410',\n" + 
						"                           '3000','3100','3101','3102','3103','3600','3700',\n" + 
						"                           '4120','4220','4320','4321','4322','4323','4420',\n" + 
						"                           '3200','3201','3202','3203',\n" + 
						"                           '2140','2213','2223',\n" + 
						"                           '3301','4600','4601','4602','4603','4604','4605','4606','4607','4608','4609','4611','4612','4613','4700','4701','4800','4330','4340',\n" + 
						"                           '3500','3501','3502','3503','3504','3505','3506','3507','3508','3509','3511','3512','3513','3300','3400',\n" + 
						"               '1140','1213','1223'))\n" + 
						"       or (x.oldenttype in ('1110','2110') and (substr(trim(x.specflag),8,1)='0' or trim(x.specflag) is null))\n" + 
						"       or (x.oldenttype in ('2000','2100','2120','2200','2210','2220','A000',\n" + 
						"                            '1000','1100','1120','1130','1152','1200','1210','1220','1230',\n" + 
						"                            '1300','2300') and substr(x.specflag,8,1)='0')) and x.flag =1 then 1 else null end)  内资标记,\n" + 
						"sum(case when ((x.oldenttype in ('4000','4100','4110','4200','4210','4300','4310','4311','4312','4313','4400','4410',\n" + 
						"                           '3000','3100','3101','3102','3103','3600','3700',\n" + 
						"                           '4120','4220','4320','4321','4322','4323','4420',\n" + 
						"                           '3200','3201','3202','3203',\n" + 
						"                           '2140','2213','2223',\n" + 
						"                           '3301','4600','4601','4602','4603','4604','4605','4606','4607','4608','4609','4611','4612','4613','4700','4701','4800','4330','4340',\n" + 
						"                           '3500','3501','3502','3503','3504','3505','3506','3507','3508','3509','3511','3512','3513','3300','3400',\n" + 
						"               '1140','1213','1223'))\n" + 
						"       or (x.oldenttype in ('1110','2110') and (substr(trim(x.specflag),8,1)='0' or trim(x.specflag) is null))\n" + 
						"       or (x.oldenttype in ('2000','2100','2120','2200','2210','2220','A000',\n" + 
						"                            '1000','1100','1120','1130','1152','1200','1210','1220','1230',\n" + 
						"                            '1300','2300') and substr(x.specflag,8,1)='0'))  then 1 else null end)  内资总数\n" + 
						" from x\n" + 
						"group by x.sumflag,rollup(x.regorg)\n" + 
						")t  order by t.regorg");
		try {
			list1=dao.queryForList(sb.toString(), list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("各分区企业地址标识情况", "WDY", i == 1 ? "查看报表"
				: "下载报表", sb.toString(), regorg, req);
		return list1;
	}
}
