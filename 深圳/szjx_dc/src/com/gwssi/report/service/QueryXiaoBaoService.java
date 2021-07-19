package com.gwssi.report.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
public class QueryXiaoBaoService  extends BaseService implements ReportSource{
	IPersistenceDAO dao = this.getPersistenceDAO("dc_dc");
	LogOperation logop = new LogOperation();
	static final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	Calendar cal = Calendar.getInstance();
	@Override
	public ReportModel getReport(TCognosReportBO queryInfo) {
		return null;
	}

	/**
	 * @param depCode
	 * @return
	 * @根据部门代码获取部门名称
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getDepName(String depCode){
		List<Map> list1=new ArrayList<Map>();
		StringBuffer sql=new StringBuffer();
		List list=new ArrayList();
		boolean rc=depCode!=null&&depCode.length()!=0;
		String[] split=depCode.split(",");
		String depName="";
		if (rc) {
			for (int j = 0; j < split.length ; j++) {
				list.add(split[j]);
			}
		}
		 sql.append(
				"select d.name 单位  from db_yyjc.jc_public_department d where 1=1 ");
		if (rc) {
			sql.append(" and (  \n" );
			for (int j = 0,t=split.length; j < t; j++) {
				if (j==0&&t==1) {
					sql.append(" d.code = ? \n ");
					break;
				}else if (j==0&&t>1) {
					sql.append(" d.code = ? \n ");
				}else{
					sql.append("or  d.code= ? \n ");
				}
			}
			sql.append(" )  \n" );
		}
		try {
			list1=dao.queryForList(sql.toString(), list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		for (int i = 0,j=list1.size(); i < j; i++) {
			if (i==0&&j==1) {
				depName+=list1.get(i).get("单位");
			}else
			if (i==0&&j>1) {
				depName+=list1.get(i).get("单位")+",";
			}else if(j>1&&i==j-1){
				depName+=list1.get(i).get("单位");
			}else{
				depName+=list1.get(i).get("单位")+",";
			}
		}
		return depName;
	}
	
	
	/**
	 * @param nowDate
	 * @return  
	 */
	public String  getOneYearAgo(String nowDate){
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(nowDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
			cal.add(Calendar.YEAR, -1);
		return sdf.format(cal.getTime());
	}

	/**
	 * @param nowDate
	 * @return  
	 */
	public String  getOneMonthAgo(String nowDate){
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(nowDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
			cal.add(Calendar.MONTH, -1);
		return sdf.format(cal.getTime());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> duBan(String beginTime, String endTime,
			int i, HttpServletRequest req) {
		List list =new ArrayList();
		List list1 =new ArrayList();
		list.add(beginTime+" 00:00:00");
		list.add(endTime+" 23:59:59");
		String sql="select nvl(ddd.处室,'合计')处室,nvl(ddd.单位,'汇总')单位,ddd.总数,ddd.一次,ddd.两次,ddd.三次,\n" +
						"round(ddd.环节超时分子/ddd.环节超时分母,0) 环节超时,\n" + 
						"round(ddd.受理超时分子/ddd.受理超时分母,0) 受理超时,\n" + 
						"round(ddd.调查超时分子/ddd.调查超时分母,0) 调查超时,\n" + 
						"round(ddd.办结超时分子/ddd.办结超时分母,0) 办结超时,\n" + 
						"ddd.自动,ddd.人工 from\n" + 
						"(select dd.处室,dd.单位,sum(dd.总数) 总数,sum(dd.一次)一次，sum(dd.两次) 两次,sum(dd.三次) 三次,sum(dd.环节超时分子)环节超时分子,\n" + 
						"sum(dd.环节超时分母) 环节超时分母,sum(dd.受理超时分子) 受理超时分子, sum(dd.受理超时分母) 受理超时分母,\n" + 
						"sum(dd.调查超时分子) 调查超时分子,sum(dd.调查超时分母) 调查超时分母,sum(dd.办结超时分子)办结超时分子 ,sum(dd.办结超时分母)办结超时分母,\n" + 
						"sum(dd.自动) 自动,sum(dd.人工) 人工  from\n" + 
						"(select d.dep_name_par 处室, d.dep_name 单位,count(1)总数 ,\n" + 
						"                 count(case when d.count=1 then 1 else null end) 一次,\n" + 
						"                 count(case when d.count=2 then 1 else null end) 两次,\n" + 
						"                 count(case when d.count>2 then 1 else null end) 三次,\n" + 
						"                 nvl(sum(case when d.ss7 in ('1','2','3') then d.count else 1 end),0) 环节超时分子,\n" + 
						"                 nvl(sum(case when d.ss8 in ('1','2','3') then 1 else null end),1) 环节超时分母 ,\n" + 
						"                 nvl(sum(case when d.ss1=1 then d.count else null end),0) 受理超时分子，\n" + 
						"                 nvl(sum(case when d.ss2=1 then 1 else null end),1)  受理超时分母,\n" + 
						"                 nvl(sum(case when d.ss3=2 then  d.count else null end),0)调查超时分子，\n" + 
						"                 nvl(sum(case when d.ss4=2 then 1 else null end),1)  调查超时分母，\n" + 
						"                 nvl(sum(case when d.ss5=3 then d.count else null end),0) 办结超时分子,\n" + 
						"                 nvl(sum(case when d.ss6=3 then 1 else null end),1)  办结超时分母,\n" + 
						"                 nvl(count(case when d.type=2 then 1 else null end),0) 自动，\n" + 
						"                 nvl(count(case when d.type=1 then 1 else null end),0) 人工,\n" + 
						"                 1 flag\n" + 
						"                 from\n" + 
						"(select  f.*, d2.dep_name  dep_name_par  from\n" + 
						"(select s.type,\n" + 
						"        s.supmode ss1,\n" + 
						"        s.supmode ss2,\n" + 
						"        s.supmode ss3,\n" + 
						"        s.supmode ss4,\n" + 
						"        s.supmode ss5,\n" + 
						"        s.supmode ss6,\n" + 
						"        s.supmode ss7,\n" + 
						"        s.supmode ss8,\n" + 
						"        s.suptime,\n" + 
						"        d1.dep_name,\n" + 
						"        s.supeddepcode,\n" + 
						"        s.supedpardepcode,\n" + 
						"        s.count\n" + 
						"        from dc_dc.dc_CPR_SUPERVISION s\n" + 
						"        left join dc_dc.DC_JG_SYS_RIGHT_DEPARTMENT d1\n" + 
						"        on s.supeddepcode=d1.sys_right_department_id\n" + 
						"        where s.suptime>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"        and   s.suptime<=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						" ) f left join  dc_dc.DC_JG_SYS_RIGHT_DEPARTMENT d2\n" + 
						"  on f.supedpardepcode=d2.sys_right_department_id\n" + 
						" )d\n" + 
						"group by d.dep_name_par,d.dep_name\n" + 
						"order by d.dep_name_par)dd\n" + 
						"group by dd.flag,rollup(dd.处室,dd.单位)) ddd";

				
		try {
			list1=dao.queryForList(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("督办统计报表", "WDY", i == 1 ? "查看报表" : "下载报表", sql,
				beginTime + "," + endTime, req);
		return list1;
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> fenPai(String beginTime, String endTime, int i,
			HttpServletRequest req) {
		List list=new ArrayList();
		List<Map> list1=new ArrayList<Map>();
		list.add(beginTime+" 00:00:00");
		list.add(endTime+" 23:59:59");
		String sql=
				"select z1.dep_name 辖区局,z1.handepname 监管所,z1.咨询汇总,z1.咨询办结量,z1.举报汇总,z1.举报办结量,\n" +
						"z1.市场监管投诉汇总,z1.市场监管投诉办结量,z1.建议汇总,z1.建议办结量,z1.其他汇总,z1.其他办结量,\n" + 
						"z1.监管所总计,sum(z1.监管所总计) over( partition by z1.dep_name ) 分局总计 from\n" + 
						"(\n" + 
						"select y.*,y.咨询汇总+y.举报汇总+y.市场监管投诉汇总+y.建议汇总+y.其他汇总 as 监管所总计  from\n" + 
						"(select z.dep_name,z.handepname,\n" + 
						"sum(case when z.inftype=3  then 1 else 0 end)咨询汇总,\n" + 
						"sum(case when z.inftype=3 and z.finishtime is not null then 1 else 0 end)咨询办结量,\n" + 
						"sum(case when z.inftype=2  then 1 else 0 end)举报汇总,\n" + 
						"sum(case when z.inftype=2 and z.finishtime is not null then 1 else 0 end)举报办结量,\n" + 
						"sum(case when z.inftype=1 then 1 else 0 end)市场监管投诉汇总，\n" + 
						"sum(case when z.inftype=1 and z.finishtime is not null then 1 else 0 end)市场监管投诉办结量,\n" + 
						"sum(case when z.inftype=4  then 1 else 0 end)建议汇总,\n" + 
						"sum(case when z.inftype=4 and z.finishtime is not null then 1 else 0 end)建议办结量,\n" + 
						"sum(case when z.inftype not in('1','2','3','4') then 1 else 0 end)其他汇总，\n" + 
						"sum(case when z.inftype not in('1','2','3','4') and z.finishtime is not null then 1 else 0 end)其他办结量\n" + 
						"from\n" + 
						"(select t.handepname,d1.dep_name,t.inftype,t.regtime ,t.finishtime from\n" + 
						"(select i.HANDEPNAME,d.parent_dep_code ,i.inftype,i.regtime,i.finishtime from dc_dc.dc_cpr_infoware i left join dc_dc.DC_JG_SYS_RIGHT_DEPARTMENT d\n" + 
						"on  i.HANDEPCODE=d.sys_right_department_id\n" + 
						"and i.regtime>=to_date(?,'yyyy-mm-dd hh24:mi:ss') and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss')) t\n" + 
						"left join dc_dc.DC_JG_SYS_RIGHT_DEPARTMENT d1 on t.parent_dep_code=d1.sys_right_department_id and length(d1.dep_name)=3 ) z\n" + 
						"where z.dep_name is not null\n" + 
						"and （z.handepname like '%所%'or z.handepname like '综合监管%'or z.handepname like '%综合业务科%' or z.handepname like '%监督管理科%'）\n" + 
						"and (z.handepname <> '龙华局食品安全监督管理科')\n" + 
						"group by z.dep_name,z.handepname\n" + 
						"order by z.dep_name) y) z1";
		try {
			list1=dao.queryForList(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("分派至监管所信息件数据统计", "WDY", i == 1 ? "查看报表" : "下载报表", sql,
				beginTime + "," + endTime, req);
		return list1;
	}


	@SuppressWarnings({  "rawtypes", "unchecked" })
	public List<Map> fenJuBanLi(String beginTime, String endTime, int i,
			HttpServletRequest req) {
		List list=new ArrayList();
		List<Map> list1=new ArrayList<Map>();
		list.add(beginTime+" 00:00:00");
		list.add(endTime+" 23:59:59");
		list.add(beginTime+" 00:00:00");
		list.add(endTime+" 23:59:59");
		list.add(beginTime+" 00:00:00");
		list.add(endTime+" 23:59:59");
		String sql=
				"\n" +
						"select nvl(dddd.处室,'合计') 处室,nvl(dddd.dep_name1,'汇总') 单位,dddd.投诉登记,dddd.举报登记,dddd.总登记量,\n" + 
						"to_char(round(dddd.总登记量/(case when dddd.登记全部=0 or dddd.登记全部 is null then 1 else dddd.登记全部 end),4)*100,'fm990.099')||'%' 登记比例,\n" + 
						"nvl(dddd.投诉分派,0) 投诉分派,nvl(dddd.投诉完成,0) 投诉完成,\n" + 
						"to_char(round(nvl(dddd.投诉完成,0)/nvl(dddd.投诉分派,1),4)*100,'fm990.099')||'%' 投诉办结率,\n" + 
						"nvl(dddd.举报分派,0) 举报分派,nvl(dddd.举报完成,0) 举报完成,\n" + 
						"to_char(round(nvl(dddd.举报完成,0)/nvl(dddd.举报分派,1),4)*100,'fm990.099')||'%' 举报办结率,\n" + 
						"nvl(dddd.分派总计,0) 分派总计,nvl(dddd.完成总计,0) 完成总计,\n" + 
						"to_char(round(nvl(dddd.完成总计,0)/nvl(dddd.分派总计,1),4)*100,'fm990.099')||'%' 总完成率\n" + 
						"from\n" + 
						"(\n" + 
						"select ddd.dep_name 处室,ddd.dep_name1,sum(ddd.投诉登记) 投诉登记,sum(ddd.举报登记) 举报登记,sum(总登记量) 总登记量,ddd.登记全部,\n" + 
						"sum(ddd.投诉分派) 投诉分派,sum(ddd.投诉完成) 投诉完成,sum(ddd.举报分派) 举报分派,sum(ddd.举报完成) 举报完成,sum(ddd.分派总计) 分派总计,\n" + 
						"sum(ddd.完成总计) 完成总计   from\n" + 
						"(select distinct( d1.dep_name),t2.dep_name dep_name1,t2.投诉登记,t2.举报登记, t2.总登记量,t2.登记全部,t2.投诉分派,t2.投诉完成,\n" + 
						"              t2.举报分派,t2.举报完成,t2.分派总计,t2.完成总计 from\n" + 
						"      ( select distinct(d.parent_dep_code),d.dep_name,t1.投诉登记,t1.举报登记, t1.总登记量,t1.登记全部,t1.投诉分派,t1.投诉完成,\n" + 
						"              t1.举报分派,t1.举报完成,t1.分派总计,t1.完成总计 from\n" + 
						"       (select (case when t.handepcode is null then y.regdepcode else t.handepcode  end ) 单位代码,nvl(y.投诉登记,0)投诉登记,nvl(y.举报登记,0)举报登记,nvl(y.总登记量,0)总登记量,\n" + 
						"       (select sum(case when i3.inftype in ('1','2') then 1 else null end ) from dc_dc.dc_cpr_infoware i3\n" + 
						"                               where i3.regtime>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"                               and i3.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss'))登记全部,\n" + 
						"       t.投诉分派,t.投诉完成, t.举报分派,t.举报完成,t.分派总计,t.完成总计 from\n" + 
						"\n" + 
						"      ( select e.handepcode, e.tousufenpai 投诉分派,e.tousuwancheng 投诉完成,\n" + 
						"              e.jubaofenpai 举报分派,e.jubaowancheng 举报完成,\n" + 
						"              e.fenpaizongji 分派总计,e.wanchengzongji 完成总计\n" + 
						"              from\n" + 
						"       (select q.handepcode,sum(case when q.w1='1' and q.a1 is not null then 1 else null end) tousufenpai,\n" + 
						"                           sum(case when q.w1='1' and q.f1 is not null then 1 else null end) tousuwancheng,\n" + 
						"                           sum(case when q.w1='2' and q.a1 is not null then 1 else null end) jubaofenpai,\n" + 
						"                           sum(case when q.w1='2' and q.f1 is not null then 1 else null end) jubaowancheng,\n" + 
						"                           sum(case when q.a2 is not null then 1 else null end)fenpaizongji,\n" + 
						"                           sum(case when q.f2 is not null then 1 else null end) wanchengzongji\n" + 
						"                           from\n" + 
						"       (select p.ACCEPTTIME a1,p.ACCEPTTIME a2,p.inftype w1,p.inftype w2,p.finishtime f1,p.finishtime f2,p.handepcode  from dc_dc.dc_cpr_infoware p\n" + 
						"       where p.inftype in ('1','2')\n" + 
						"       and p.ACCEPTTIME>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"       and p.ACCEPTTIME<=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"       and p.handepcode is not null\n" + 
						"       ) q\n" + 
						"       group by q.handepcode) e) t\n" + 
						"       full outer join\n" + 
						"      (  select o.regdepcode,\n" + 
						"              sum(case when o.i1='1' then 1 else 0 end) 投诉登记,\n" + 
						"              sum(case when o.i1='2' then 1 else 0 end) 举报登记,\n" + 
						"              count(1) 总登记量\n" + 
						"              from\n" + 
						"      ( select i.regdepcode,i.inftype i1,i.inftype i2,i.regtime from dc_dc.dc_cpr_infoware i\n" + 
						"       where i.inftype in ('1','2')\n" + 
						"       and i.regtime>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"       and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss')) o\n" + 
						"       group by o.regdepcode\n" + 
						"       ) y\n" + 
						"       on t.handepcode=y.regdepcode\n" + 
						"       )t1\n" + 
						"       left join dc_dc.DC_JG_SYS_RIGHT_DEPARTMENT d\n" + 
						"\n" + 
						"       on t1.单位代码=d.sys_right_department_id) t2\n" + 
						"       left join dc_dc.DC_JG_SYS_RIGHT_DEPARTMENT d1\n" + 
						"       on t2.parent_dep_code=d1.sys_right_department_id\n" + 
						"       order by d1.dep_name\n" + 
						"       ) ddd\n" + 
						"      group by ddd.登记全部,rollup(ddd.dep_name,ddd.dep_name1)\n" + 
						"      )dddd";
		try {
			list1=dao.queryForList(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("各分局科所投诉举报登记办理情况", "WDY", i == 1 ? "查看报表" : "下载报表", sql,
				beginTime + "," + endTime, req);
		return list1;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> huiFang(String beginTime, String endTime, int i,
			HttpServletRequest req) {
		List list=new ArrayList();
		List<Map> list1=new ArrayList<Map>();
		list.add(beginTime+" 00:00:00");
		list.add(endTime+" 23:59:59");
		String sql=
				"select nvl(ddd.处局,'合计') 处局,nvl(ddd.处理单位,'汇总') 处理单位,ddd.huifang 回访数,\n" +
						"to_char(round(ddd.huifang/nvl(ddd.xinxijian,1),4)*100,'9990.09')||'%' 占比,\n" + 
						"ddd.属实,ddd.不属实，ddd.无法联系,ddd.其他 from (\n" + 
						"select dd.处局,dd.处理单位,sum(dd.回访数) huifang ,sum(dd.xinxijian) xinxijian,sum(dd.属实) 属实,sum(dd.不属实) 不属实,sum(dd.无法联系) 无法联系,sum(dd.其他)其他 from\n" + 
						"(select distinct(s2.dep_name) 处局,r.dep_name 处理单位,r.huifangshu 回访数,r.xinxijian,\n" + 
						"r.shushi 属实,r.bushushi 不属实,r.wufalianxi 无法联系,r.qita 其他 ,1 flag from\n" + 
						"(select distinct(s1.parent_dep_code),s1.dep_name,nvl(e.huifangshu,0) huifangshu, e.xinxijian,e.shushi,e.bushushi,e.wufalianxi,e.qita from\n" + 
						"(select d.handepcode,\n" + 
						"       sum(case when d.id1 is not null then 1 else null end) huifangshu,\n" + 
						"       sum(case when d.infowareid is not null then 1 else null end) xinxijian,\n" + 
						"       nvl(sum(case when d.id2 is not null and d.visitconfirm='1' then 1 else null end ),0)shushi,\n" + 
						"       nvl(sum(case when d.id2 is not null and d.visitconfirm='2' then 1 else null end ),0)bushushi,\n" + 
						"      nvl( sum(case when d.id2 is not null and d.visitconfirm='3' then 1 else null end ),0)wufalianxi ,\n" + 
						"       nvl(sum(case when d.id2 is not null and d.visitconfirm='4' then 1 else null end ),0)qita\n" + 
						"from (\n" + 
						"      select  v.visitconfirm,v.returnvisitid id1 ,v.returnvisitid id2 ,i.infowareid ,i.handepcode from dc_dc.dc_cpr_infoware i  left join dc_dc.dc_CPR_RETURN_VISIT v on i.infowareid=v.infowareid\n" + 
						"      where i.regtime>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"        and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"        and i.handepcode is not null\n" + 
						") d\n" + 
						"group by handepcode) e\n" + 
						"left join dc_dc.DC_JG_SYS_RIGHT_DEPARTMENT s1\n" + 
						"on e.handepcode=s1.sys_right_department_id) r\n" + 
						"left join dc_dc.DC_JG_SYS_RIGHT_DEPARTMENT s2\n" + 
						"on r.parent_dep_code=s2.sys_right_department_id\n" + 
						"order by s2.dep_name\n" + 
						")dd\n" + 
						"group by dd.flag,rollup(dd.处局,dd.处理单位)\n" + 
						")ddd";
		try {
			list1=dao.queryForList(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("回访统计表", "WDY", i == 1 ? "查看报表" : "下载报表", sql,
				beginTime + "," + endTime, req);
		return list1;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> juBao(String beginTime, String endTime, int i,
			HttpServletRequest req) {
		List list=new ArrayList();
		List<Map> list1=new ArrayList<Map>();
		list.add(beginTime+" 00:00:00");
		list.add(endTime+" 23:59:59");
		list.add(this.getOneYearAgo(beginTime)+" 00:00:00");
		list.add(this.getOneYearAgo(endTime)+" 23:59:59");
		list.add(beginTime+" 00:00:00");
		list.add(endTime+" 23:59:59");
		list.add(beginTime+" 00:00:00");
		list.add(endTime+" 23:59:59");
		String sql=
				"select nvl(ddddd.dep_name ,'合计') 处室,nvl(ddddd.处理部门,'汇总') 处理部门,nvl(ddddd.信息件,0) 信息件,\n" +
						"to_char(round((nvl(ddddd.信息件,0)-nvl(ddddd.去年,0))/nvl(ddddd.去年,1),4)*100,'fm999999990.0999')||'%'同比,\n" + 
						"ddddd.已办结,to_char(round(ddddd.已办结/nvl(ddddd.信息件,1),4)*100,'fm99990.0999')||'%' 办结率,\n" + 
						"ddddd.立案数,to_char(round(ddddd.立案数/nvl(ddddd.信息件,1),4)*100,'fm99990.0999')||'%' 立案率\n" + 
						"from\n" + 
						"(select dddd.dep_name,dddd.处理部门,dddd.信息件,dddd.已办结,dddd.立案数,dddd.去年 from\n" + 
						"(select ddd.dep_name,ddd.处理部门,sum(ddd.信息件) 信息件,sum(ddd.已办结) 已办结 ,sum(ddd.立案数) 立案数,sum(qunian) 去年,ddd.flag\n" + 
						" from (\n" + 
						"select distinct(j.dep_name),t.dep_name 处理部门,t.xinxijiana 信息件,\n" + 
						"t.banjie 已办结,t.lian 立案数,t.qunian,1 as flag\n" + 
						"from\n" + 
						"(select distinct(h.parent_dep_code),h.dep_name,g.xinxijian xinxijiana,g.qunian, g.banjie, g.lian from\n" + 
						"(select d.handepcode,\n" + 
						"       sum(case when d.time1 is not null and d.time1>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"                    and d.time1<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else null end) xinxijian,\n" + 
						"       sum(case when d.time2 is not null and d.time2>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"                    and d.time2<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else null end) qunian,\n" + 
						"       nvl(sum(case when d.time3 is not null and d.finishtime is not null and d.time3>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"                    and d.time3<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else null end ),0) banjie,\n" + 
						"       nvl(sum(case when d.lasmodtime is not null  and d.time4 is not null and d.time4>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"                    and d.time4<=to_date(?,'yyyy-mm-dd hh24:mi:ss')then 1 else null end),0) lian\n" + 
						"       from\n" + 
						"(select\n" + 
						"       i.handepcode,\n" + 
						"       i.accepttime time1,\n" + 
						"       i.accepttime time2,\n" + 
						"       i.accepttime time3,\n" + 
						"       i.accepttime time4,\n" + 
						"       i.finishtime,\n" + 
						"       c.lasmodtime\n" + 
						"  from dc_dc.dc_cpr_infoware i\n" + 
						"  left join dc_dc.dc_cpr_feedback f\n" + 
						"    on i.feedbackid = f.feedbackid\n" + 
						"  left join dc_dc.dc_cpr_case_info c\n" + 
						"    on f.caseinfoid = c.caseinfoid\n" + 
						"where i.inftype='2' and i.handepcode is not null\n" + 
						") d\n" + 
						"group by d.handepcode) g\n" + 
						"left join  dc_dc.DC_JG_SYS_RIGHT_DEPARTMENT h\n" + 
						"on g.handepcode =h.sys_right_department_id) t\n" + 
						"left join  dc_dc.DC_JG_SYS_RIGHT_DEPARTMENT j\n" + 
						"on t.parent_dep_code =j.sys_right_department_id\n" + 
						"order by j.dep_name\n" + 
						")ddd\n" + 
						"group by ddd.flag, rollup(ddd.dep_name,ddd.处理部门)\n" + 
						")dddd\n" + 
						")ddddd";
		try {
			list1=dao.queryForList(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("举报件处理情况统计表", "WDY", i == 1 ? "查看报表" : "下载报表", sql,
				beginTime + "," + endTime, req);
		return list1;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> touSu(String beginTime, String endTime, int i,
			HttpServletRequest req) {
		List list=new ArrayList();
		List<Map> list1=new ArrayList<Map>();
		list.add(beginTime+" 00:00:00");
		list.add(endTime+" 23:59:59");
		list.add(this.getOneYearAgo(beginTime)+" 00:00:00");
		list.add(this.getOneYearAgo(endTime)+" 23:59:59");
		list.add(beginTime+" 00:00:00");
		list.add(endTime+" 23:59:59");
		list.add(beginTime+" 00:00:00");
		list.add(endTime+" 23:59:59");
		list.add(beginTime+" 00:00:00");
		list.add(endTime+" 23:59:59");
		String sql=
				"select nvl(dddd.处室,'合计') 处室,nvl(dddd.处理部门,'汇总') 处理部门,nvl(dddd.信息件,'0')信息件,\n" +
						" to_char(round((nvl(dddd.信息件,0)-nvl(dddd.去年数量,0))/nvl(dddd.去年数量,1),4)*100,'fm9999999990.0999')||'%' 同比,\n" + 
						" dddd.已办结,to_char(round(dddd.已办结/nvl(dddd.信息件,1),4)*100,'fm99999990.0999')||'%' 办结率,\n" + 
						" nvl(dddd.调解数,0) 调解数,dddd.调解成功数,to_char(round(dddd.调解成功数/nvl(dddd.调解数,1),4)*100,'fm99990.0999')||'%' 调解成功率\n" + 
						" from\n" + 
						"(select ddd.处室,ddd.处理部门,ddd.信息件,ddd.已办结,ddd.调解数,ddd.调解成功数,ddd.去年数量 from\n" + 
						"(select dd.处室,dd.处理部门,sum(dd.信息件) 信息件,sum(dd.已办结)已办结, sum(dd.调解数) 调解数,sum(dd.调解成功数) 调解成功数,\n" + 
						"sum(qunian) 去年数量 from\n" + 
						"(select distinct(j.dep_name) 处室,t.dep_name 处理部门,t.xinxijiana 信息件,t.banjie 已办结,\n" + 
						"t.tiaojie 调解数,t.tiaojiechenggong 调解成功数,t.qunian,t.flag from\n" + 
						"(select distinct(h.parent_dep_code),h.dep_name,g.xinxijian xinxijiana, g.banjie, g.tiaojie tiaojie,g.tiaojiechenggong, g.qunian,1 flag from\n" + 
						"(select d.handepcode,\n" + 
						"       sum(case when d.time1 is not null and d.time1>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"                    and d.time1<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else null end) xinxijian,\n" + 
						"       sum(case when d.time2 is not null and d.time2>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"                    and d.time2<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else null end) qunian,\n" + 
						"       nvl(sum(case when d.time3 is not null and d.finishtime is not null and d.time3>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"                    and d.time3<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else null end ),0) banjie,\n" + 
						"       sum(case when d.mediationid is not null  and d.time4 is not null and d.time4>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"                    and d.time4<=to_date(?,'yyyy-mm-dd hh24:mi:ss')then 1 else null end) tiaojie,\n" + 
						"       nvl(sum(case when d.mediationid1 is not null and d.mediate_result='1' and d.time5 is not null and d.time5>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"                    and d.time5<=to_date(?,'yyyy-mm-dd hh24:mi:ss')then 1 else null end),0) tiaojiechenggong\n" + 
						"       from\n" + 
						"(select\n" + 
						"       i.handepcode,\n" + 
						"       i.accepttime time1,\n" + 
						"       i.accepttime time2,\n" + 
						"       i.accepttime time3,\n" + 
						"       i.accepttime time4,\n" + 
						"       i.accepttime time5,\n" + 
						"       i.finishtime,\n" + 
						"       c.mediationid mediationid,\n" + 
						"       c.mediationid mediationid1,\n" + 
						"       c.mediate_result\n" + 
						"  from dc_dc.dc_cpr_infoware i\n" + 
						"  left join dc_dc.dc_cpr_feedback f\n" + 
						"    on i.feedbackid = f.feedbackid\n" + 
						"  left join dc_dc.dc_cpr_mediation c\n" + 
						"    on f.mediationid = c.mediationid\n" + 
						"where i.inftype='1' and i.handepcode is not null\n" + 
						") d\n" + 
						"group by d.handepcode) g\n" + 
						"left join  dc_dc.DC_JG_SYS_RIGHT_DEPARTMENT h\n" + 
						"on g.handepcode =h.sys_right_department_id) t\n" + 
						"left join  dc_dc.DC_JG_SYS_RIGHT_DEPARTMENT j\n" + 
						"on t.parent_dep_code =j.sys_right_department_id\n" + 
						"order by j.dep_name)dd\n" + 
						"group by dd.flag,rollup(dd.处室,dd.处理部门)\n" + 
						")ddd)dddd";
		try {
			list1=dao.queryForList(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("市场监管投诉件处理情况统计表", "WDY", i == 1 ? "查看报表" : "下载报表", sql,
				beginTime + "," + endTime, req);
		return list1;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> ziXun(String beginTime, String endTime, int i,
			HttpServletRequest req) {
		List list=new ArrayList();
		List<Map> list1=new ArrayList<Map>();
		list.add(beginTime+" 00:00:00");
		list.add(endTime+" 23:59:59");
		list.add(this.getOneYearAgo(beginTime)+" 00:00:00");
		list.add(this.getOneYearAgo(endTime)+" 23:59:59");
		list.add(beginTime+" 00:00:00");
		list.add(endTime+" 23:59:59");
		String sql=
				"select nvl(ddd.处室，'合计')处室,nvl(ddd.处理部门,'汇总')处理部门,nvl(ddd.信息件,'0') 信息件,\n" +
						"to_char(round((nvl(ddd.信息件,0)-nvl(ddd.去年,0))/nvl(ddd.去年,1),4)*100,'fm99999990.0999')||'%' 同比,\n" + 
						"ddd.已办结 ,to_char(round(ddd.已办结/nvl(ddd.信息件,1),4)*100,'fm99990.0999')||'%' 办结率 from\n" + 
						"(select dd.dep_name 处室,dd.处理部门,sum(dd.信息件)信息件,sum(dd.去年) 去年,sum(dd.已办结) 已办结 from\n" + 
						"(select distinct(j.dep_name),t.dep_name 处理部门,t.xinxijian 信息件,t.qunian 去年,t.banjie 已办结,t.flag\n" + 
						" from\n" + 
						"(select distinct(h.parent_dep_code),h.dep_name,g.xinxijian ,g.banjie,g.qunian,1 flag from\n" + 
						"(select d.handepcode,\n" + 
						"       sum(case when d.time1 is not null and d.time1>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"                    and d.time1<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else null end) xinxijian,\n" + 
						"       sum(case when d.time2 is not null and d.time2>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"                    and d.time2<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else null end) qunian,\n" + 
						"       nvl(sum(case when d.time3 is not null and d.finishtime is not null and d.time3>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"                    and d.time3<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else null end ),0) banjie\n" + 
						"       from\n" + 
						"(select\n" + 
						"       i.handepcode,\n" + 
						"       i.accepttime time1,\n" + 
						"       i.accepttime time2,\n" + 
						"       i.accepttime time3,\n" + 
						"       i.accepttime time4,\n" + 
						"       i.finishtime\n" + 
						"\n" + 
						"  from dc_dc.dc_cpr_infoware i\n" + 
						"where i.inftype='3' and i.handepcode is not null\n" + 
						") d\n" + 
						"group by d.handepcode) g\n" + 
						"left join  dc_dc.DC_JG_SYS_RIGHT_DEPARTMENT h\n" + 
						"on g.handepcode =h.sys_right_department_id) t\n" + 
						"left join  dc_dc.DC_JG_SYS_RIGHT_DEPARTMENT j\n" + 
						"on t.parent_dep_code =j.sys_right_department_id\n" + 
						"order by j.dep_name\n" + 
						")dd\n" + 
						"group by dd.flag,rollup(dd.dep_name,dd.处理部门)\n" + 
						")ddd";
		try {
			list1=dao.queryForList(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("咨询件处理情况统计表", "WDY", i == 1 ? "查看报表" : "下载报表", sql,
				beginTime + "," + endTime, req);
		return list1;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> huanJie(String beginTime, String endTime, int i,
			HttpServletRequest req) {
		List list=new ArrayList();
		List<Map> list1=new ArrayList<Map>();
		list.add(beginTime+" 00:00:00");
		list.add(endTime+" 23:59:59");
		/*String sql=
				"select o.actpardepname 单位,\n" +
						"o.handle 处理人,\n" + 
						"nvl(sum(case when o.statename='预登记' and o.action='提交到分派' then 1 else null end),0) 预登记1,\n" + 
						"nvl(sum(case when o.statename='预登记' and o.action='直接回复' then 1 else null end),0) 预登记2,\n" + 
						"nvl(sum(case when o.statename='待分派' and o.action='撤回' then 1 else null end),0) 待分派1,\n" + 
						"nvl(sum(case when o.statename='待分派' and o.action='呈报上级' then 1 else null end),0) 待分派2,\n" + 
						"nvl(sum(case when o.statename='待分派' and o.action='回退' then 1 else null end),0) 待分派3,\n" + 
						"nvl(sum(case when o.statename='待分派' and o.action='局内转移' then 1 else null end),0) 待分派4,\n" + 
						"nvl(sum(case when o.statename='待分派' and o.action='强制回收' then 1 else null end),0) 待分派5,\n" + 
						"nvl(sum(case when o.statename='待分派' and o.action='请求审批' then 1 else null end),0) 待分派6,\n" + 
						"nvl(sum(case when o.statename='待分派' and o.action='消费通回退' then 1 else null end),0) 待分派7,\n" + 
						"nvl(sum(case when o.statename='待分派' and o.action='直接交办' then 1 else null end),0) 待分派8,\n" + 
						"nvl(sum(case when o.statename='待分派' and o.action='逐级分派' then 1 else null end),0) 待分派9,\n" + 
						"nvl(sum(case when o.statename='待决策' and o.action='批示' then 1 else null end),0) 待决策,\n" + 
						"nvl(sum(case when o.statename='待审批' and o.action='撤回' then 1 else null end),0) 待审批1,\n" + 
						"nvl(sum(case when o.statename='待审批' and o.action='强制回收' then 1 else null end),0) 待审批2,\n" + 
						"nvl(sum(case when o.statename='待审批' and o.action='审批' then 1 else null end),0) 待审批3,\n" + 
						"nvl(sum(case when o.statename='待审批' and o.action='直接交办' then 1 else null end),0) 待审批4,\n" + 
						"nvl(sum(case when o.statename='待受理' and o.action='不受理' then 1 else null end),0) 待受理1,\n" + 
						"nvl(sum(case when o.statename='待受理' and o.action='撤回' then 1 else null end),0) 待受理2,\n" + 
						"nvl(sum(case when o.statename='待受理' and o.action='回退' then 1 else null end),0) 待受理3,\n" + 
						"nvl(sum(case when o.statename='待受理' and o.action='强制回收' then 1 else null end),0) 待受理4,\n" + 
						"nvl(sum(case when o.statename='待受理' and o.action='受理' then 1 else null end),0) 待受理5,\n" + 
						"nvl(sum(case when o.statename='办理中' and o.action='强制回收' then 1 else null end),0) 办理中1,\n" + 
						"nvl(sum(case when o.statename='办理中' and o.action='申请办结' then 1 else null end),0) 办理中2,\n" + 
						"nvl(sum(case when o.statename='办理中' and o.action='申请挂起' then 1 else null end),0) 办理中3,\n" + 
						"nvl(sum(case when o.statename='办理中' and o.action='申请结案' then 1 else null end),0) 办理中4,\n" + 
						"nvl(sum(case when o.statename='办理中' and o.action='申请延时' then 1 else null end),0) 办理中5,\n" + 
						"nvl(sum(case when o.statename='办理中' and o.action='申请中止' then 1 else null end),0) 办理中6,\n" + 
						"nvl(sum(case when o.statename='办结确认' and o.action='不同意' then 1 else null end),0) 办结确认1,\n" + 
						"nvl(sum(case when o.statename='办结确认' and o.action='撤回' then 1 else null end),0) 办结确认2,\n" + 
						"nvl(sum(case when o.statename='办结确认' and o.action='同意' then 1 else null end),0) 办结确认3,\n" + 
						"nvl(sum(case when o.statename='中止审批' and o.action='不同意' then 1 else null end),0) 中止审批1,\n" + 
						"nvl(sum(case when o.statename='中止审批' and o.action='撤回' then 1 else null end),0) 中止审批2,\n" + 
						"nvl(sum(case when o.statename='中止审批' and o.action='同意' then 1 else null end),0) 中止审批3,\n" + 
						"nvl(sum(case when o.statename='挂起审批' and o.action='不同意' then 1 else null end),0) 挂起审批1,\n" + 
						"nvl(sum(case when o.statename='挂起审批' and o.action='同意' then 1 else null end),0) 挂起审批2,\n" + 
						"nvl(sum(case when o.statename='回收审批' and o.action='撤回' then 1 else null end),0) 回收审批1,\n" + 
						"nvl(sum(case when o.statename='回收审批' and o.action='同意' then 1 else null end),0) 回收审批2,\n" + 
						"nvl(sum(case when o.statename='延时审批' and o.action='不同意' then 1 else null end),0) 延时审批1,\n" + 
						"nvl(sum(case when o.statename='延时审批' and o.action='撤回' then 1 else null end),0) 延时审批2,\n" + 
						"nvl(sum(case when o.statename='延时审批' and o.action='同意' then 1 else null end),0) 延时审批3,\n" + 
						"nvl(sum(case when o.statename='办结审核' and o.action='不同意' then 1 else null end),0)办结审核1,\n" + 
						"nvl(sum(case when o.statename='办结审核' and o.action='撤回' then 1 else null end),0) 办结审核2,\n" + 
						"nvl(sum(case when o.statename='办结审核' and o.action='同意' then 1 else null end),0) 办结审核3,\n" + 
						"nvl(sum(case when o.statename='不受理审核' and o.action='不同意' then 1 else null end),0)不受理审核1,\n" + 
						"nvl(sum(case when o.statename='不受理审核' and o.action='撤回' then 1 else null end),0) 不受理审核2,\n" + 
						"nvl(sum(case when o.statename='不受理审核' and o.action='同意' then 1 else null end),0) 不受理审核3,\n" + 
						"nvl(sum(case when o.statename='已挂起' and o.action='撤销' then 1 else null end),0) 已挂起1,\n" + 
						"nvl(sum(case when o.statename='已挂起' and o.action='强制回收' then 1 else null end),0) 已挂起2,\n" + 
						"nvl(sum(case when o.statename='待归档' and o.action='驳回' then 1 else null end),0) 待归档1,\n" + 
						"nvl(sum(case when o.statename='待归档' and o.action='撤回' then 1 else null end),0) 待归档2,\n" + 
						"nvl(sum(case when o.statename='待归档' and o.action='归档' then 1 else null end),0) 待归档3,\n" + 
						"nvl(sum(case when o.statename='待归档' and o.action='强制回收' then 1 else null end),0) 待归档4,\n" + 
						"nvl(sum(case when o.statename='待归档' and o.action='申请重办' then 1 else null end),0) 待归档5,\n" + 
						"nvl(sum(case when o.statename='待归档' and o.action='自动归档' then 1 else null end),0) 待归档6,\n" + 
						"nvl(sum(case when o.statename='驳回审批' and o.action='不同意' then 1 else null end),0)驳回审批1,\n" + 
						"nvl(sum(case when o.statename='驳回审批' and o.action='撤回' then 1 else null end),0) 驳回审批2,\n" + 
						"nvl(sum(case when o.statename='驳回审批' and o.action='同意' then 1 else null end),0) 驳回审批3,\n" + 
						"nvl(sum(case when o.statename='重办审批' and o.action='撤回' then 1 else null end),0) 重办审批1,\n" + 
						"nvl(sum(case when o.statename='重办审批' and o.action='同意' then 1 else null end),0) 重办审批2,\n" + 
						"nvl(sum(case when o.statename='已归档' and o.action='强制回收' then 1 else null end),0) 已归档1,\n" + 
						"nvl(sum(case when o.statename='已归档' and o.action='消费通回退' then 1 else null end),0) 已归档2,\n" + 
						"nvl(sum(case when o.statename='已归档' and o.action='消费通结束' then 1 else null end),0) 已归档3\n" + 
						"from\n" + 
						"(select i.actiontime,i.action,i.statename,i.actpardepname ,i.handle from  dc_dc.dc_CPR_PROCESS_INSTANCE  i) o\n" + 
						"where o.actiontime>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"and o.actiontime<=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"group by o.actpardepname\n" + 
						",o.handle\n" + 
						"order by o.actpardepname";*/
		
		
		
		String sql = "select \n" + 
				  "o.actpardepname 单位, \n" + 
				 " o.handle 处理人,\n" + 
				"  nvl(sum(case when o.prestatename='预登记' and o.action IN ('撤回','分派','提交到分派') then 1 else null end),0) 预登记1,\n" + 
				 " nvl(sum(case when o.prestatename='预登记' and o.action='直接回复' then 1 else null end),0) 预登记2,\n" + 
				"  nvl(sum(case when o.prestatename='待分派' and o.action='撤回' then 1 else null end),0) 待分派1,\n" + 
				"  nvl(sum(case when o.prestatename='待分派' and o.action='呈报上级' then 1 else null end),0) 待分派2,\n" + 
				"  nvl(sum(case when o.prestatename='待分派' and o.action='回退' then 1 else null end),0) 待分派3,\n" + 
				"  nvl(sum(case when o.prestatename='待分派' and o.action IN ('提交到分派','分派') then 1 else null end),0) 待分派4,--表头改成逐级分派\n" + 
				"  nvl(sum(case when o.prestatename='待分派' and o.action='强制回收' then 1 else null end),0) 待分派5,\n" + 
				"  nvl(sum(case when o.prestatename='待分派' and o.action='请求审批' then 1 else null end),0) 待分派6,\n" + 
				"  nvl(sum(case when o.prestatename='待分派' and o.action='消费通回退' then 1 else null end),0) 待分派7,\n" + 
				"  nvl(sum(case when o.prestatename='待分派' and o.action='直接交办' then 1 else null end),0) 待分派8,\n" + 
				"  nvl(sum(case when o.prestatename='待分派' and o.action='请求裁定'  then 1 else null end),0) 待分派9,--表头改成裁定\n" + 
				"  nvl(sum(case when o.prestatename='待决策' and o.action='批示' then 1 else null end),0) 待决策,\n" + 
				"  nvl(sum(case when o.prestatename='待审批' and o.action='撤回' then 1 else null end),0) 待审批1,\n" + 
				"  nvl(sum(case when o.prestatename='待审批' and o.action='强制回收' then 1 else null end),0) 待审批2,\n" + 
				"  nvl(sum(case when o.prestatename='待审批' and o.action='审批' then 1 else null end),0) 待审批3,\n" + 
				"  nvl(sum(case when o.prestatename='待审批' and o.action='直接交办' then 1 else null end),0) 待审批4,\n" + 
				"  nvl(sum(case when o.prestatename='待受理' and o.action='不受理' then 1 else null end),0) 待受理1,\n" + 
				"  nvl(sum(case when o.prestatename='待受理' and o.action='撤回' then 1 else null end),0) 待受理2,\n" + 
				"  nvl(sum(case when o.prestatename='待受理' and o.action='回退' then 1 else null end),0) 待受理3,\n" + 
				"  nvl(sum(case when o.prestatename='待受理' and o.action='强制回收' then 1 else null end),0) 待受理4,\n" + 
				"  nvl(sum(case when o.prestatename='待受理' and o.action='受理' then 1 else null end),0) 待受理5,\n" + 
				"  nvl(sum(case when o.prestatename='办理中' and o.action='强制回收' then 1 else null end),0) 办理中1,\n" + 
				"  nvl(sum(case when o.prestatename='办理中' and o.action='申请办结' then 1 else null end),0) 办理中2,\n" + 
				"  nvl(sum(case when o.prestatename='办理中' and o.action='申请挂起' then 1 else null end),0) 办理中3,\n" + 
				"  nvl(sum(case when o.prestatename='办理中' and o.action='申请结案' then 1 else null end),0) 办理中4,\n" + 
				"  nvl(sum(case when o.prestatename='办理中' and o.action='申请延时' then 1 else null end),0) 办理中5,\n" + 
				"  nvl(sum(case when o.prestatename='办理中' and o.action='申请中止' then 1 else null end),0) 办理中6,\n" + 
				"  nvl(sum(case when o.prestatename='办结确认' and o.action IN ('不同意','拟同意') then 1 else null end),0) 办结确认1,\n" + 
				"  nvl(sum(case when o.prestatename='办结确认' and o.action='撤回' then 1 else null end),0) 办结确认2,\n" + 
				"  nvl(sum(case when o.prestatename='办结确认' and o.action='同意' then 1 else null end),0) 办结确认3,\n" + 
				"  nvl(sum(case when o.prestatename='中止审批' and o.action IN ('不同意','拟同意') then 1 else null end),0) 中止审批1,\n" + 
				"  nvl(sum(case when o.prestatename='中止审批' and o.action='撤回' then 1 else null end),0) 中止审批2,\n" + 
				"  nvl(sum(case when o.prestatename='中止审批' and o.action='同意' then 1 else null end),0) 中止审批3,\n" + 
				"  nvl(sum(case when o.prestatename='挂起审批' and o.action IN ('不同意','拟同意') then 1 else null end),0) 挂起审批1,\n" + 
				"  nvl(sum(case when o.prestatename='挂起审批' and o.action='同意' then 1 else null end),0) 挂起审批2,\n" + 
				"  nvl(sum(case when o.prestatename='回收审批' and o.action='撤回' then 1 else null end),0) 回收审批1,\n" + 
				" nvl(sum(case when o.prestatename='回收审批' and o.action='同意' then 1 else null end),0) 回收审批2,\n" + 
				"  nvl(sum(case when o.prestatename='延时审批' and o.action IN ('不同意','拟同意') then 1 else null end),0) 延时审批1,\n" + 
				"  nvl(sum(case when o.prestatename='延时审批' and o.action='撤回' then 1 else null end),0) 延时审批2,\n" + 
				"  nvl(sum(case when o.prestatename='延时审批' and o.action='同意' then 1 else null end),0) 延时审批3,\n" + 
				"  nvl(sum(case when o.prestatename='办结审核' and o.action IN ('不同意','拟同意')  then 1 else null end),0)办结审核1,\n" + 
				"  nvl(sum(case when o.prestatename='办结审核' and o.action='撤回' then 1 else null end),0) 办结审核2,\n" + 
				"  nvl(sum(case when o.prestatename='办结审核' and o.action='同意' then 1 else null end),0) 办结审核3,\n" + 
				"  nvl(sum(case when o.prestatename='不受理审核' and o.action IN ('不同意','拟同意') then 1 else null end),0)不受理审核1,\n" + 
				"  nvl(sum(case when o.prestatename='不受理审核' and o.action='撤回' then 1 else null end),0) 不受理审核2,\n" + 
				"  nvl(sum(case when o.prestatename='不受理审核' and o.action='同意' then 1 else null end),0) 不受理审核3,\n" + 
				"  nvl(sum(case when o.prestatename='已挂起' and o.action='撤销' then 1 else null end),0) 已挂起1,\n" + 
				"  nvl(sum(case when o.prestatename='已挂起' and o.action='强制回收' then 1 else null end),0) 已挂起2,\n" + 
				"  nvl(sum(case when o.prestatename='待归档' and o.action='驳回' then 1 else null end),0) 待归档1,\n" + 
				"  nvl(sum(case when o.prestatename='待归档' and o.action='撤回' then 1 else null end),0) 待归档2,\n" + 
				"  nvl(sum(case when o.prestatename='待归档' and o.action IN ('归档','自动归档') then 1 else null end),0) 待归档3,\n" + 
				"  nvl(sum(case when o.prestatename='待归档' and o.action='强制回收' then 1 else null end),0) 待归档4,\n" + 
				"  nvl(sum(case when o.prestatename='待归档' and o.action='申请重办' then 1 else null end),0) 待归档5,\n" + 
				"  nvl(sum(case when o.prestatename='待归档' and o.action='自动归档' then 1 else null end),0) 待归档6,\n" + 
				"  nvl(sum(case when o.prestatename='驳回审批' and o.action='不同意' then 1 else null end),0)驳回审批1,\n" + 
				" nvl(sum(case when o.prestatename='驳回审批' and o.action='撤回' then 1 else null end),0) 驳回审批2,\n" + 
				"  nvl(sum(case when o.prestatename='驳回审批' and o.action='同意' then 1 else null end),0) 驳回审批3,\n" + 
				" nvl(sum(case when o.prestatename='重办审批' and o.action='撤回' then 1 else null end),0) 重办审批1,\n" + 
				"  nvl(sum(case when o.prestatename='重办审批' and o.action='同意' then 1 else null end),0) 重办审批2,\n" + 
			    " nvl(sum(case when o.prestatename='已归档' and o.action='强制回收' then 1 else null end),0) 已归档1,\n" + 
				" nvl(sum(case when o.prestatename='已归档' and o.action='消费通回退' then 1 else null end),0) 已归档2,\n" + 
				"  nvl(sum(case when o.prestatename IN ('待归档','信息件受理') and o.action='消费通结束' then 1 else null end),0) 已归档3 \n" + 
				" from dc_dc.dc_cpr_process_step  o\n" + 
				" where o.actiontime>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
				"   and o.actiontime<=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
				" group by o.actpardepname,o.handle\n" + 
				" order by o.actpardepname ";

		
		
		
			try {
				list1=dao.queryForList(sql, list);
			} catch (OptimusException e) {
				e.printStackTrace();
			}
		logop.logInfoYeWu("环节工作量统计表", "WDY", i == 1 ? "查看报表" : "下载报表", sql,
				beginTime + "," + endTime, req);
		return list1;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> xinXiDengJi(String beginTime, String endTime,
			String hBeginTime, String hEndTime, String regCode, int i,
			HttpServletRequest req) {
		boolean hb=hBeginTime!=null &&hBeginTime.length()!=0;
		boolean he=hEndTime!=null &&hEndTime.length()!=0;
		boolean rc=regCode!=null &&regCode.length()!=0;
		String[] split = regCode.split(",");
		List list=new ArrayList();
		List<Map> list1=new ArrayList<Map>();
		StringBuffer sql=new StringBuffer();
		list.add(beginTime+" 00:00:00");
		list.add(endTime+" 23:59:59");
		list.add(this.getOneYearAgo(beginTime)+" 00:00:00");
		list.add(this.getOneYearAgo(endTime)+" 23:59:59");
		list.add(hb?hBeginTime+" 00:00:00":this.getOneMonthAgo(beginTime)+" 00:00:00");
		list.add(he?hEndTime+" 23:59:59":this.getOneMonthAgo(endTime)+" 23:59:59");
		list.add(beginTime+" 00:00:00");
		list.add(endTime+" 23:59:59");
		list.add(this.getOneYearAgo(beginTime)+" 00:00:00");
		list.add(this.getOneYearAgo(endTime)+" 23:59:59");
		list.add(hb?hBeginTime+" 00:00:00":this.getOneMonthAgo(beginTime)+" 00:00:00");
		list.add(he?hEndTime+" 23:59:59":this.getOneMonthAgo(endTime)+" 23:59:59");
		if (rc) {
			for (int j = 0; j < split.length ; j++) {
				list.add(split[j]);
			}
		}
		
		sql.append(
				"select nvl(t.handle,'合计') 姓名,nvl(t.zhijiehuifu,'0') 直接回复数,nvl(t.yudengji,'0')预登记,nvl(t.gongshang,'0') 工商,\n" +
						"nvl(t.jiajian,'0') 价检, nvl(t.zhijian,'0') 质监, nvl(t.zhishi,'0')知识,nvl(t.xiaoweihui,'0')消委会,nvl(t.heji,'0') 合计,\n" + 
						"nvl(t.dengjiliang,'0') 登记量,\n" + 
						"nvl(t.shangyue,'0') 上一时段,to_char(round((nvl(t.dengjiliang,'0')-nvl(t.shangyue,'0'))/nvl(t.shangyue,'1'),4)*100,'fm9999990.099')||'%'环比增减,\n" + 
						"nvl(t.qunian,'0') 去年同期,to_char(round((nvl(t.dengjiliang,'0')-nvl(t.qunian,'0'))/nvl(t.qunian,'1'),4)*100,'fm9999999990.099')||'%' 同比增减\n" + 
						"from(\n" + 
						"select r.handle,sum(case when r.action ='直接回复' and r.now=1  then 1 else null end) zhijiehuifu,\n" + 
						"                sum(case when r.statename='预登记' and r.now=1 then 1 else null end) yudengji,\n" + 
						"                sum(case when r.statename<>'预登记' and r.businesstype ='CH20' and r.now=1 then 1 else null end) gongshang,\n" + 
						"                sum(case when r.statename<>'预登记' and r.businesstype ='ZH20' and r.now=1 then 1 else null end) jiajian,\n" + 
						"                sum(case when r.statename<>'预登记' and r.businesstype ='ZH21' and r.now=1 then 1 else null end) zhijian,\n" + 
						"                sum(case when r.statename<>'预登记' and r.businesstype ='ZH19' and r.now=1 then 1 else null end) zhishi,\n" + 
						"                sum(case when r.statename<>'预登记' and r.businesstype ='ZH18' and r.now=1 then 1 else null end) xiaoweihui,\n" + 
						"                sum(case when r.statename1<>'预登记' and r.businesstype1 in('CH20','ZH18','ZH19','ZH20','ZH21') and r.now=1 then 1 else null end) heji,\n" + 
						"                sum(case when r.now=1 then 1 else null end )dengjiliang,\n" + 
						"                sum(case when r.qunian=1 then 1 else null end)qunian,\n" + 
						"                sum(case when r.shangyue=1 then 1 else null end) shangyue\n" + 
						"                from\n" + 
						"(select nvl(c.handle,'null') handle,c.statename statename,c.statename statename1,c.action,o.businesstype businesstype,o.businesstype businesstype1,i.regtime,\n" + 
						"(case when i.regtime>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"      and  i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else null end) now,\n" + 
						"(case when i.regtime>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"      and  i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else null end) qunian,\n" + 
						"(case when i.regtime>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"      and  i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else null end) shangyue,\n" + 
						"      1 flag\n" + 
						" from dc_dc.dc_cpr_infoware i left join dc_dc.dc_CPR_INVOLVED_OBJECT  o on i.invobjid =o.invobjid\n" + 
						"left join dc_dc.dc_CPR_PROCESS_INSTANCE c on c.proinsid=i.proinsid\n" + 
						"where ((i.regtime>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"      and  i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss'))\n" + 
						"      or (i.regtime>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"      and  i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss'))\n" + 
						"      or(i.regtime>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"      and  i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss')))\n");
			if (rc) {
				sql.append(" and (  \n" );
				for (int j = 0,t=split.length; j < split.length; j++) {
					if (j==0&&t==1) {
						sql.append(" i.regdepcode = ? \n ");
						break;
					}else if (j==0&&t>1) {
						sql.append(" i.regdepcode = ? \n ");
					}else{
						sql.append("or  i.regdepcode = ? \n ");
					}
				}
				sql.append(" )  \n" );
				}
			sql.append(
						"      ) r\n" + 
						"\n" + 
						"      group by r.flag,rollup(r.handle)\n" + 
						")t\n" + 
						"      order by t.handle");
			try {
				list1=dao.queryForList(sql.toString(),list);
			} catch (OptimusException e) {
				e.printStackTrace();
			}
		logop.logInfoYeWu("信息件登记量统计表", "WDY", i == 1 ? "查看报表" : "下载报表", sql.toString(),
					beginTime + "," + endTime+","+hBeginTime+","+hEndTime+","+regCode, req);
		return list1;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> guiDang(String beginTime, String endTime,
			String hBeginTime, String hEndTime, int i, HttpServletRequest req) {
		boolean hb=hBeginTime!=null &&hBeginTime.length()!=0;
		boolean he=hEndTime!=null &&hEndTime.length()!=0;
		List list=new ArrayList();
		List<Map> list1=new ArrayList<Map>();
		list.add(beginTime+" 00:00:00");
		list.add(endTime+" 23:59:59");
		list.add(hb?hBeginTime+" 00:00:00":this.getOneMonthAgo(beginTime)+" 00:00:00");
		list.add(he?hEndTime+" 23:59:59":this.getOneMonthAgo(endTime)+" 23:59:59");
		list.add(this.getOneYearAgo(beginTime)+" 00:00:00");
		list.add(this.getOneYearAgo(endTime)+" 23:59:59");
		list.add(beginTime+" 00:00:00");
		list.add(endTime+" 23:59:59");
		list.add(hb?hBeginTime+" 00:00:00":this.getOneMonthAgo(beginTime)+" 00:00:00");
		list.add(he?hEndTime+" 23:59:59":this.getOneMonthAgo(endTime)+" 23:59:59");
		list.add(this.getOneYearAgo(beginTime)+" 00:00:00");
		list.add(this.getOneYearAgo(endTime)+" 23:59:59");
		String sql=
				"select nvl(f.actpardepname,'总计') 单位,nvl(f.actdepname,'汇总') 部门,nvl(f.guidang,'0') 归档,nvl(f.tongguoshu,'0') 通过数,\n" +
						"nvl(f.bohui1,'0') 有误,nvl(f.bohui2,'0') 证据不充分,nvl(f.bohui3,'0') 不完整,nvl(f.bohui4,'0') 转派,\n" + 
						"nvl(f.huan,'0') 上一时段,to_char(round((nvl(f.guidang,'0')-nvl(f.huan,'0'))/nvl(f.huan,'1'),4)*100,'fm999999990.099')||'%' 环比,\n" + 
						"nvl(f.tong,'0') 去年,to_char(round((nvl(f.guidang,'0')-nvl(f.tong,'0'))/nvl(f.tong,'1'),4)*100,'fm999999990.099')||'%' 同比 from\n" + 
						"                (select b.actpardepname,b.actdepname,sum(b.guidang) guidang,sum(b.tongguoshu) tongguoshu,sum(b.bohui1)bohui1,sum(b.bohui2) bohui2,\n" + 
						"                        sum(b.bohui3)bohui3,sum(b.bohui4)bohui4,sum(b.huan)huan,sum(b.tong)tong from\n" + 
						"                        (\n" + 
						"                            select c.actpardepname,c.actdepname,sum(case when c.action='归档' and c.now=1 then 1 else null end) guidang,\n" + 
						"                            sum(case when c.action ='归档' and c.prestatename <> '驳回审批' and c.now=1 then 1 else null end) tongguoshu,\n" + 
						"                            sum(case when c.action ='驳回' and c.prestatename='待归档' and c.denyreason='1' and c.now=1 then 1 else null end) bohui1,\n" + 
						"                            sum(case when c.action ='驳回' and c.prestatename='待归档' and c.denyreason='2' and c.now=1 then 1 else null end) bohui2,\n" + 
						"                            sum(case when c.action ='驳回' and c.prestatename='待归档' and c.denyreason='3' and c.now=1 then 1 else null end) bohui3,\n" + 
						"                            sum(case when c.action ='驳回' and c.prestatename='待归档' and c.denyreason='4' and c.now=1 then 1 else null end) bohui4,\n" + 
						"                            sum(case when c.action ='归档' and c.huan=1 then 1 else null end ) huan,\n" + 
						"                            sum(case when c.action ='归档' and c.tong=1 then 1 else null end ) tong,1 flag\n" + 
						"                            from (\n" + 
						"                                      select d.denyreason denyreason ,s.actdepname,s.actpardepname ,s.action,s.prestatename ,i.regtime,\n" + 
						"                                      (case when i.regtime>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"                                            and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else null end) now,\n" + 
						"                                      (case when i.regtime>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"                                            and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else null end) huan,\n" + 
						"                                            (case when i.regtime>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"                                            and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else null end) tong\n" + 
						"                                      from dc_dc.dc_CPR_INFOWARE_DENY d right join dc_dc.dc_cpr_process_step s on d.prostepid=s.prostepid\n" + 
						"                                      left join dc_dc.dc_cpr_infoware i on s.proinsid=i.proinsid\n" + 
						"                                      where s.actpardepname is not null\n" + 
						"                                      and ((i.regtime>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"                                            and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss'))or\n" + 
						"                                            (i.regtime>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"                                            and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss')) or\n" + 
						"                                            (i.regtime>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"                                            and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss'))\n" + 
						"                                      )\n" + 
						") c\n" + 
						"group by c.actpardepname,c.actdepname\n" + 
						"order by c.actpardepname\n" + 
						") b\n" + 
						"group by b.flag,rollup(b.actpardepname,b.actdepname)\n" + 
						")f";
		try {
			list1=dao.queryForList(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("归档审核统计表", "WDY", i == 1 ? "查看报表" : "下载报表", sql.toString(),
				beginTime + "," + endTime+","+hBeginTime+","+hEndTime, req);
		return list1;
	}
	
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> beiDuBan(String beginTime, String endTime, int i,
			HttpServletRequest req) {
		List list=new ArrayList();
		List<Map> list1=new ArrayList<Map>();
		list.add(beginTime+" 00:00:00");
		list.add(endTime+" 23:59:59");
		String sql=
				"select nvl(g.supedpardepname,'合计') 上级单位,nvl(g.supeddepname,'汇总') 单位,\n" +
						"sum(g.counts) 督办件数,sum(g.shouli)受理,sum(g.diaocha) 调查,\n" + 
						"sum(g.banjie) 办结,sum(g.fenpai)分派,sum(g.shenpi) 审批,sum(g.juece)决策,\n" + 
						"sum(g.shenqingbanjie) 申请办结,\n" + 
						"sum(g.banjiequeren) 办结确认,\n" + 
						"sum(g.zaiciduban) 再次督办 from\n" + 
						"(\n" + 
						"        select t.supedpardepname,t.supeddepname,sum(t.count) counts,\n" + 
						"        sum(case when t.supmode='1' then t.count else 0 end) shouli,\n" + 
						"        sum(case when t.supmode='2' then t.count else 0 end) diaocha,\n" + 
						"        sum(case when t.supmode='3' then t.count else 0 end) banjie,\n" + 
						"        sum(case when t.supmode='4' then t.count else 0 end) fenpai,\n" + 
						"        sum(case when t.supmode='5' then t.count else 0 end) shenpi,\n" + 
						"        sum(case when t.supmode='6' then t.count else 0 end) juece,\n" + 
						"        sum(case when t.supmode='7' then t.count else 0 end) shenqingbanjie,\n" + 
						"        sum(case when t.supmode='8' then t.count else 0 end) banjiequeren,\n" + 
						"        sum(case when t.supmode='9' then t.count else 0 end) zaiciduban,\n" + 
						"        1 flag from\n" + 
						"                (\n" + 
						"                select s.supeddepname,s.supedpardepname,s.supmode,s.count from dc_dc.dc_CPR_SUPERVISION s left join dc_dc.dc_cpr_infoware i--dc_dc.dc_cpr_process_step s on a.prosteid=s.prostepid --\n" + 
						"                on s.infowareid=i.infowareid\n" + 
						"                where i.regtime>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"                and   i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"                )t\n" + 
						"         group by  t.supedpardepname,t.supeddepname\n" + 
						"         order by t.supedpardepname\n" + 
						"         )g\n" + 
						"  group by g.flag,rollup(g.supedpardepname,g.supeddepname)";
		try {
			list1=dao.queryForList(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("被督办部门类型统计表", "WDY", i == 1 ? "查看报表" : "下载报表", sql,
				beginTime + "," + endTime, req);
		return list1;	
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> zhiJieJieDa(String beginTime, String endTime, int i,
			HttpServletRequest req) {
		List list=new ArrayList();
		List<Map> list1=new ArrayList<Map>();
		list.add(beginTime);
		list.add(endTime+" 23:59:59");
		String sql=
				" select  REGEXP_REPLACE ( t.infotype,'[0-9]+','') infotype  ,count(t.infotype)count from (\n" +
						"select (case i.inftype when '1' then '0投诉'\n" + 
						"                       when '2' then '1举报'\n" + 
						"                       when '3' then '2咨询'\n" + 
						"                       when '4' then '3建议'\n" + 
						"                       when '8' then '4行政监察件'\n" + 
						"                       when '6' then '5消委会投诉'\n" + 
						"                       when '9' then '6其他'\n" + 
						"                       else '6其他' end\n" + 
						"            ) infotype from\n" + 
						" dc_dc.dc_cpr_infoware i left join dc_dc.dc_cpr_process_instance  o\n" + 
						"  on  i.proinsid=o.proinsid\n" + 
						"where i.regtime>=to_date(?,'yyyy-mm-dd')\n" + 
						"and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"  and o.RECDEPCODE='6100'\n" + 
						" and o.action='直接回复'\n" + 
						" and i.finishtime is not null\n" + 
						")t\n" + 
						"group by t.infotype\n" + 
						"order by t.infotype";
		try {
			list1=dao.queryForList(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("直接解答数量统计", "WDY", i == 1 ? "查看报表" : "下载报表", sql,
				beginTime + "," + endTime, req);
		return list1;
	}

	@SuppressWarnings({"unchecked", "rawtypes" })
	public List<Map> ziXunJianYiYeWuFanWei(String beginTime, String endTime,
			int i, HttpServletRequest req) {
		List list=new ArrayList();
		List<Map> list1=new ArrayList<Map>();
		list.add(beginTime);
		list.add(endTime+" 23:59:59");
		String sql=
				"\n" +
						"  --咨询建议业务范围分类统计\n" + 
						"select  distinct tt.name name,t.businessrange,t.sums from (\n" + 
						"with x as (\n" + 
						"SELECT o.businessrange\n" + 
						"FROM dc_cpr_infoware  i,dc_dc.dc_CPR_INQUIRY o,dc_cpr_info_provider p,\n" + 
						"dc_cpr_involved_main m ,dc_cpr_process_instance ins,dc_code.dc_12315_codedata c\n" + 
						"where i.inquiryid=o.inquiryid (+)\n" + 
						"and i.infproid = p.infproid(+)\n" + 
						"and m.invmaiid(+)=i.invmaiid\n" + 
						"and ins.proinsid(+)=i.proinsid\n" + 
						"and o.businessrange =c.code\n" + 
						"and c.codetable='ZH01'\n" + 
						"and I.REGTIME>=to_date(?,'yyyy-mm-dd')\n" + 
						"and I.REGTIME<=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"and length(trim(o.businessrange))='8'\n" + 
						"and i.inftype in ('3','4'))\n" + 
						"select '999999999'businessrange,count(1) sums from x group by 1\n" + 
						"union all\n" + 
						"select nvl(x.businessrange,'99999999'),count(1) sums from x group by x.businessrange\n" + 
						"union all\n" + 
						"select substr(x.businessrange,0,2)||'000000' businessrange,count(1)sums from x where x.businessrange is not null group by substr(x.businessrange,0,2)\n" + 
						"union all\n" + 
						"select substr(x.businessrange,0,4)||'0000' businessrange,count(1)sums from x where x.businessrange is not null group by substr(x.businessrange,0,4)\n" + 
						"union all\n" + 
						"select substr(x.businessrange,0,6)||'00' businessrange,count(1)sums from x where x.businessrange is not null group by substr(x.businessrange,0,6)\n" + 
						") t\n" + 
						",\n" + 
						"(select c.code,c.name from\n" + 
						"(SELECT  CODE,  substr('　　　　　',0,level-1)||NAME AS NAME\n" + 
						"    FROM (SELECT * FROM DC_CODE.DC_12315_CODEDATA WHERE CODETABLE = 'ZH01')\n" + 
						"   START WITH PARENTCODE = '0'\n" + 
						"  CONNECT BY PRIOR CODE = PARENTCODE\n" + 
						"  union all\n" + 
						"  select '99999999' as code,'未指定' as name from dual\n" + 
						"  union all\n" + 
						"  select '999999999'as code, '全部' as name from dual\n" + 
						"   ) c order by c.code)tt\n" + 
						"WHERE  t.businessrange=tt.code\n" + 
						"order by t.businessrange";
		try {
			list1 = dao.queryForList(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("咨询建议业务范围分类统计", "WDY", i == 1 ? "查看报表" : "下载报表", sql,
				beginTime + "," + endTime, req);
		return list1;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> shenSuJuBaoJiBenWenTi(String beginTime, String endTime,
			String infoType, String yeWuLeiBie, int i, HttpServletRequest req) {
		List list = new ArrayList();
		List<Map> list1 = new ArrayList<Map>();
		//list.add(beginTime + " 00:00:00");
		//list.add(endTime + " 23:59:59");
		//if (yeWuLeiBie != null && yeWuLeiBie.length() != 0) {
		//	list.add(yeWuLeiBie);
		//}
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT  t.code,c.name ,t.nums from (\n" +
						"SELECT substr(i.APPLBASQUE,0,2)||'00' code ,count(1) nums FROM\n" + 
						"dc_cpr_infoware i,\n" + 
						"dc_CPR_INVOLVED_OBJECT o\n" + 
						"where  i.regtime>=to_date(?,'yyyy-mm-dd')\n" + 
						"and i.regtime <=to_date(?,'yyyy-mm-dd hh24:mi:Ss')\n" + 
						"and o.invobjid(+)=i.invobjid \n");
		list.add(beginTime);
		list.add(endTime+" 23:59:59");
		if (infoType!=null &&infoType.trim().length()!=0) {
			sql.append("and i.inftype =? \n");
			list.add(infoType);
		}else{
			sql.append("and i.inftype in ('1','2') \n");
		}
		if (yeWuLeiBie!=null&&yeWuLeiBie.trim().length()!=0) {
			sql.append("and o.BUSINESSTYPE=? \n ");
			list.add(yeWuLeiBie);
		}
		sql.append(
				"group by  substr(i.APPLBASQUE,0,2)||'00'\n" +
						") t,dc_code.dc_12315_codedata c\n" + 
						"where t.code=c.code\n" + 
						"and c.codetable='CH27'\n" + 
						"union all\n" + 
						"SELECT  t.code,'　'||c.name ,t.nums from (\n" + 
						"SELECT i.APPLBASQUE code,count(1) nums FROM\n" + 
						"dc_cpr_infoware i,\n" + 
						"dc_CPR_INVOLVED_OBJECT o\n" + 
						"where  i.regtime>=to_date(?,'yyyy-mm-dd')\n" + 
						"and i.regtime <=to_date(?,'yyyy-mm-dd hh24:mi:Ss')\n" + 
						"and o.invobjid(+)=i.invobjid \n");
		list.add(beginTime);
		list.add(endTime+" 23:59:59");
		if (infoType!=null &&infoType.trim().length()!=0) {
			sql.append("and i.inftype =? \n");
			list.add(infoType);
		}else{
			sql.append("and i.inftype in ('1','2') \n");
		}
		if (yeWuLeiBie!=null&&yeWuLeiBie.trim().length()!=0) {
			sql.append("and o.BUSINESSTYPE=? \n ");
			list.add(yeWuLeiBie);
		}
		sql.append("group by i.APPLBASQUE)t,dc_code.dc_12315_codedata c\n" +
						"where t.code=c.code\n" + 
						"and c.codetable='CH27'\n" + 
						"order by code asc,name desc");
		/*sql.append("select  w.name,w.nums  from (\n"
				+ "select distinct y.name ,z.nums ,y.code from\n"
				+ "(with x as (\n"
				+ "select i.inftype, i.applbasque ,o.businesstype ,c.name from dc_dc.dc_CPR_INFOWARE i ,dc_code.dc_12315_codedata c,\n"
				+ "dc_dc.dc_CPR_INVOLVED_OBJECT o where  i.invobjid=o.invobjid and i.applbasque=c.code\n"
				+ "and i.regtime>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n"
				+ "and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n"
				+ "and c.codetable='CH27'\n"
				+ "and o.businesstype is not null\n"
				+ "and i.applbasque is not null\n");
		if (infoType != null && "1".equals(infoType)) {
			sql.append("and i.inftype='1' \n");
		} else if (infoType != null && "2".equals(infoType)) {
			sql.append("and i.inftype='2' \n");
		} else if (infoType != null && "3".equals(infoType)) {
			sql.append("and i.inftype='3' \n");
		} else {
			sql.append("and i.inftype in ('3','2','1') \n");
		}

		if (yeWuLeiBie != null && yeWuLeiBie.length() != 0) {
			sql.append("and o.BUSINESSTYPE=?\n");
		}
		sql.append("--价格检查\n"
				+ ")\n"
				+ "select x.applbasque,sum(1)nums from x  group by x.applbasque\n"
				+ "union all\n"
				+ "select substr(x.applbasque,0,2)||'00',sum(1) nums from x group by substr(x.applbasque,0,2)\n"
				+ "union all\n"
				+ "select '9999合计' applbasque,sum(1) nums from x\n"
				+ ")z\n"
				+ "left join\n"
				+ "(select code , (case when substr(code,3,2)<>'00'then '　'||name\n"
				+ "      else name end) name  from dc_code.dc_12315_codedata where codetable='CH27'\n"
				+ "      union all\n"
				+ "      select '9999合计' code ,'合计'name from dual)y\n"
				+ "      on z.applbasque=y.code\n" + "      where 1=1)w\n"
				+ "      where w.name is not null\n"
				+ "order by w.code ,nums desc");*/

		logop.logInfoYeWu("申诉举报基本问题分类统计", "WDY", i == 1 ? "查看报表" : "下载报表",
				sql.toString(), beginTime + "," + endTime+","+infoType+","+yeWuLeiBie, req);
		try {
			list1=dao.queryForList(sql.toString(), list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return list1;
	}

	@SuppressWarnings({ "rawtypes", "unchecked"})
	public List<List<Map>> dengJiXinXiSheJiJinE(Map<String,String> map, int i,
			HttpServletRequest req) {
		List list=new ArrayList();
		List list1 = new ArrayList();
		StringBuffer sb=new StringBuffer();
		list.add(map.get("begintime")+" 00:00:00");
		list.add(map.get("endtime")+" 23:59:59");
		if (!stringIsNull(map.get("infotype"))) {
			sb.append("  and i.inftype=? \n");
			list.add(map.get("infotype").trim());
		}
		if (!stringIsNull(map.get("laiyuanfangshi"))) {
			sb.append("  and i.INFOORI=? \n");
			list.add(map.get("laiyuanfangshi").trim());
		}
		if (!stringIsNull(map.get("jieshoufangshi"))) {
			sb.append("  and i.INCFORM=? \n");
			list.add(map.get("jieshoufangshi").trim());
		}
		if (!stringIsNull(map.get("shijianjibie"))) {
			sb.append("  and i.EVEGRADE=? \n");
			list.add(map.get("shijianjibie").trim());
		}
		if (!stringIsNull(map.get("renyuanshenfen"))) {
			sb.append("  and p.PERTYPE=? \n");
			list.add(map.get("renyuanshenfen").trim());
		}
		if (!stringIsNull(map.get("shejizhuti"))) {
			sb.append("  and m.PTTYPE=? \n");
			list.add(map.get("shejizhuti").trim());
		}
		if (!stringIsNull(map.get("wangzhanleixing"))) {
			sb.append("  and m.WEBSITETYPE=? \n");
			list.add(map.get("wangzhanleixing").trim());
		}
		if (!stringIsNull(map.get("wangzhanleixing"))) {
			sb.append("  and m.WEBSITETYPE=? \n");
			list.add(map.get("wangzhanleixing").trim());
		}
		if (!stringIsNull(map.get("hangyeleibie"))) {
			String str=map.get("hangyeleibie").trim();
			if ("00".equals(str.substring(str.length()-2, str.length()))) {
				sb.append("  and m.TRADETYPE  like ? \n");
				list.add(str.substring(0, 1)+"%");
			}else{
				sb.append("  and m.TRADETYPE  = ? \n");
				list.add(str);
			}
		}
		if (!stringIsNull(map.get("qiyeleixing"))) {
			String str=map.get("qiyeleixing").trim();
			if ("000".equals(str.substring(str.length()-3, str.length()))) {
				sb.append("  and m.ENTTYPE  like ? \n");
				list.add(str.substring(0, 1)+"%");
			}else if("00".equals(str.substring(str.length()-2, str.length()))){
				sb.append("  and m.ENTTYPE  like ? \n");
				list.add(str.substring(0, 2)+"%");
			}else if ("0".equals(str.substring(str.length()-1, str.length()))) {
				sb.append("  and m.ENTTYPE  like ? \n");
				list.add(str.substring(0, 3)+"%");
			}else{
				sb.append("  and m.ENTTYPE  = ? \n");
				list.add(str);
			}
		}
		String[] sql=getSqlByParameter(sb.toString(), map.get("shejiketi"));
		for (int j = 0,k=sql.length; j < k; j++) {
			try {
				list1.add(dao.queryForList(sql[j],list));
			} catch (OptimusException e) {
				e.printStackTrace();
			}
		}
		logop.logInfoYeWu("登记信息涉及金额统计表", "WDY", i == 1 ? "查看报表" : "下载报表",
				sql[0], map.toString(), req);
		return list1;
	}
	
	
	public boolean stringIsNull(Object str){
		if (str!=null&&str.toString().trim().length()!=0) {
			return false;
		}else {
			return true;
		}
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<List<Map>> shenSuJuBaoSheJiKeTi(String beginTime, String endTime,
			String infoType, String yeWuLeiBie, int i, HttpServletRequest req) {
		List list = new ArrayList();
		List list1 = new ArrayList();
		list.add(beginTime + " 00:00:00");
		list.add(endTime + " 23:59:59");
		String infotype = "";
		if (infoType != null && "1".equals(infoType)) {
			infotype += "and i.inftype='1' \n";
		} else if (infoType != null && "2".equals(infoType)) {
			infotype += "and i.inftype='2' \n";
		} else {
			infotype += "and i.inftype in ('2','1') \n";
		}
		String[] sql = getSql(infotype, yeWuLeiBie);
		for (int j = 0; j < sql.length; j++) {
			try {
				list1.add(dao.queryForList(sql[j], list));
			} catch (OptimusException e) {
				e.printStackTrace();
			}
		}
		logop.logInfoYeWu("申诉举报涉及客体类型统计", "WDY", i == 1 ? "查看报表" : "下载报表",
				sql[0], beginTime + "," + endTime+","+infoType+","+yeWuLeiBie, req);
		return list1;
	}
	
	//根据业务类型获取查询SQL语句
	public String[] getSql(String infotype,String yeWuLeiBie){
		String sqlGongShang=
				"--第一部分 工商\n" +
						"select w.name,w.nums from\n" + 
						"(\n" + 
						"select distinct y.name ,z.nums ,z.invobjtype from\n" + 
						"(with x as (\n" + 
						"select i.inftype,o.invobjtype,o.businesstype from dc_dc.dc_CPR_INFOWARE i ,\n" + 
						"dc_dc.dc_CPR_INVOLVED_OBJECT o where  i.invobjid=o.invobjid\n" + 
						"and i.regtime>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"and o.businesstype is not null\n" + 
						"and o.invobjtype is not null\n" + 
						"and o.businesstype='CH20'\n" + 
						infotype+
						")\n" + 
						"select '00工商' as invobjtype,nvl(sum(1),0) nums from x\n" + 
						"union all\n" + 
						"select x.invobjtype,sum(1)nums from x  group by x.invobjtype\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,3)||'00000',sum(1)nums from x group by substr(x.invobjtype,0,3)\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,5)||'000',sum(1) nums from x  group by substr(x.invobjtype,0,5)\n" + 
						"union all\n" + 
						"select '9999合计' invobjtype,nvl(sum(1),0) nums from x\n" + 
						")z\n" + 
						"left join\n" + 
						"(select code , (\n" + 
						" case when substr(code,7,2)<>'00' then '　　　　'||name\n" + 
						"      when substr(code,6,3)<>'000'   then '　　　'||name\n" + 
						"      when substr(code,4,5)<>'00000' or substr(code,6,3)='900' then '　　'||name\n" + 
						"      when substr(code,2,7)<>'0000000'then '　'||name\n" + 
						"      else name end) name  from dc_code.dc_12315_codedata where codetable='CH20'\n" + 
						"      union all\n" + 
						"      select '9999合计' code ,'合计'name from dual\n" + 
						"      union all\n" + 
						"      select '00工商' code,'工商' name from dual)y\n" + 
						"      on z.invobjtype=y.code\n" + 
						"      where 1=1)w\n" + 
						"order by w.invobjtype";
		String sqlXiaoWeiHui=
				"--第二部分 消委会\n" +
						"select  w.name,w.nums from (\n" + 
						"select distinct y.name ,z.nums ,y.code from\n" + 
						"(with x as (\n" + 
						"select i.inftype,o.invobjtype,o.businesstype from dc_dc.dc_CPR_INFOWARE i ,\n" + 
						"dc_dc.dc_CPR_INVOLVED_OBJECT o where  i.invobjid=o.invobjid\n" + 
						"and i.regtime>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"and o.businesstype is not null\n" + 
						"and o.invobjtype is not null\n" + 
						"and o.businesstype='ZH18'\n" + 
						infotype+
						"--消委会\n" + 
						")\n" + 
						"select x.invobjtype,sum(1)nums from x  group by x.invobjtype\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,2)||'00000000',sum(1)nums from x  group by substr(x.invobjtype,0,2)\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,4)||'000000',sum(1)nums from x   group by substr(x.invobjtype,0,4)\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,6)||'0000',sum(1) nums from x   group by substr(x.invobjtype,0,6)\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,8)||'00',sum(1) nums from x group by substr(x.invobjtype,0,8)\n" + 
						"union all\n" + 
						"select '9999合计' invobjtype,nvl(sum(1),0) nums from x union all\n" + 
						"select '00消委会' invobjtype,nvl(sum(1),0) nums from x\n" + 
						")z\n" + 
						"left join\n" + 
						"(select code , (case when substr(code,9,2)<>'00'then '　　　　'||name\n" + 
						"      when substr(code,7,4)<>'0000'then '　　　'||name\n" + 
						"      when substr(code,5,6)<>'000000'then '　　'||name\n" + 
						"      when substr(code,3,8)<>'00000000'then '　'||name\n" + 
						"      else name end) name  from dc_code.dc_12315_codedata where codetable='ZH18'\n" + 
						"      union all\n" + 
						"      select '9999合计' code ,'合计'name from dual\n" + 
						"      union all\n" + 
						"      select '00消委会' code ,'消委会'name from dual)y\n" + 
						"      on z.invobjtype=y.code\n" + 
						"      where 1=1)w\n" + 
						"order by w.code";
		String sqlZhiShiChanQuan=
				"--第三部分 知识产权\n" +
						"select  w.name,w.nums from (\n" + 
						"select distinct y.name ,z.nums ,y.code from\n" + 
						"(with x as (\n" + 
						"select i.inftype,o.invobjtype,o.businesstype from dc_dc.dc_CPR_INFOWARE i ,\n" + 
						"dc_dc.dc_CPR_INVOLVED_OBJECT o where  i.invobjid=o.invobjid\n" + 
						"and i.regtime>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"and o.businesstype is not null\n" + 
						"and o.invobjtype is not null\n" + 
						"and o.businesstype='ZH19'\n" + 
						infotype+
						"--知识产权\n" + 
						")\n" + 
						"select x.invobjtype,sum(1)nums from x   group by x.invobjtype\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,1)||'00',sum(1) nums from x group by substr(x.invobjtype,0,1)\n" + 
						"union all\n" + 
						"select '9999合计' invobjtype,nvl(sum(1),0) nums from x\n" + 
						"union all\n" + 
						"select '00知识产权' invobjtype,nvl(sum(1),0) nums from x\n" + 
						")z\n" + 
						"left join\n" + 
						"(select code , (case when substr(code,2,2)<>'00'then '　'||name\n" + 
						"      else name end) name  from dc_code.dc_12315_codedata where codetable='ZH19'\n" + 
						"      union all\n" + 
						"      select '9999合计' code ,'合计'name from dual\n" + 
						"      union all\n" + 
						"      select '00知识产权' code ,'知识产权'name from dual)y\n" + 
						"      on z.invobjtype=y.code\n" + 
						"      where 1=1)w\n" + 
						"      where w.name is not null\n" + 
						"order by w.code";
		String sqlJiaGeJianCha=
				"--第四部分  价格检查\n" +
						"select  w.name,w.nums from (\n" + 
						"select distinct y.name ,z.nums ,y.code from\n" + 
						"(with x as (\n" + 
						"select i.inftype,(case when o.invobjtype='0100' then '9900' else o.invobjtype end) invobjtype,o.businesstype from dc_dc.dc_CPR_INFOWARE i ,\n" + 
						"dc_dc.dc_CPR_INVOLVED_OBJECT o where  i.invobjid=o.invobjid\n" + 
						"and i.regtime>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"and o.businesstype is not null\n" + 
						"and o.invobjtype is not null\n" + 
						"and o.businesstype='ZH20'\n" + 
						infotype+
						"--价格检查\n" + 
						")\n" + 
						"select x.invobjtype,sum(1)nums from x  group by x.invobjtype\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,2)||'00',sum(1) nums from x  group by substr(x.invobjtype,0,2)\n" + 
						"union all\n" + 
						"select '9999合计' invobjtype,nvl(sum(1),0) nums from x\n" + 
						"union all\n" + 
						"select '00价格检查' invobjtype,nvl(sum(1),0) nums from x\n" + 
						")z\n" + 
						"left join\n" + 
						"(select code , (case when substr(code,3,2)<>'00'then '　'||name\n" + 
						"      else name end) name  from dc_code.dc_12315_codedata where codetable='ZH20'\n" + 
						"      union all\n" + 
						"      select '9999合计' code ,'合计'name from dual\n" + 
						"      union all\n" + 
						"      select '00价格检查' code ,'价格检查'name from dual)y\n" + 
						"      on z.invobjtype=y.code\n" + 
						"      where 1=1)w\n" + 
						"      where w.name is not null\n" + 
						"order by w.code";
		String sqlZhiLianJianDu=
				"--第五部分 质量监督\n" +
						"select  w.name,w.nums from (\n" + 
						"select distinct y.name ,z.nums ,y.code from\n" + 
						"(with x as (\n" + 
						"select i.inftype,o.invobjtype,o.businesstype from dc_dc.dc_CPR_INFOWARE i ,\n" + 
						"dc_dc.dc_CPR_INVOLVED_OBJECT o where  i.invobjid=o.invobjid\n" + 
						"and i.regtime>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"and o.businesstype is not null\n" + 
						"and o.invobjtype is not null\n" + 
						"and o.businesstype='ZH21'\n" + 
						infotype+
						"--质量监督\n" + 
						")\n" + 
						"select x.invobjtype,sum(1)nums from x group by x.invobjtype\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,2)||'00000000',sum(1)nums from x   group by substr(x.invobjtype,0,2)\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,4)||'000000',sum(1)nums from x  group by substr(x.invobjtype,0,4)\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,6)||'0000',sum(1) nums from x   group by substr(x.invobjtype,0,6)\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,8)||'00',sum(1) nums from x  group by substr(x.invobjtype,0,8)\n" + 
						"union all\n" + 
						"select '9999合计' invobjtype,nvl(sum(1),0) nums from x\n" + 
						"union all\n" + 
						"select '00质量监督' invobjtype,nvl(sum(1),0) nums from x\n" + 
						")z\n" + 
						"left join\n" + 
						"(select code , (case when substr(code,9,2)<>'00'then '　　　　'||name\n" + 
						"      when substr(code,7,4)<>'0000'then '　　　'||name\n" + 
						"      when substr(code,5,6)<>'000000'then '　　'||name\n" + 
						"      when substr(code,3,8)<>'00000000'then '　'||name\n" + 
						"      else name end) name  from dc_code.dc_12315_codedata where codetable='ZH21'\n" + 
						"      union all\n" + 
						"      select '9999合计' code ,'合计'name from dual\n" + 
						"       union all\n" + 
						"      select '00质量监督' code ,'质量监督'name from dual)y\n" + 
						"      on z.invobjtype=y.code\n" + 
						"      where 1=1)w\n" + 
						"      where w.name is not null\n" + 
						"order by w.code";
		
		/**
		 * 根据前台选择的业务类别代码来选择查询sql并放入数组中
		 */
			String[] str ;
			if (yeWuLeiBie != null && "CH20".equals(yeWuLeiBie)) {
				str=new String[]{sqlGongShang};
			} else if (yeWuLeiBie != null && "ZH18".equals(yeWuLeiBie)) {
				str=new String[]{sqlXiaoWeiHui};
			} else if (yeWuLeiBie != null && "ZH19".equals(yeWuLeiBie)) {
				str=new String[]{sqlZhiShiChanQuan};
			} else if (yeWuLeiBie != null && "ZH20".equals(yeWuLeiBie)) {
				str=new String[]{sqlJiaGeJianCha};
			} else if (yeWuLeiBie != null && "ZH21".equals(yeWuLeiBie)) {
				str=new String[]{sqlZhiLianJianDu};
			} else {
				str=new String[]{sqlGongShang,sqlXiaoWeiHui,sqlZhiShiChanQuan,sqlJiaGeJianCha,sqlZhiLianJianDu};
			}
		return str;
	}
		
	//登记信息涉及金额统计表 根据业务类别获取sql
	public String[] getSqlByParameter(String parameter,String objectType){
		String sqlGongShang=
				"--第一部分  工商\n" +
						"select  w.name,nvl(w.invoam,0)invoam,nvl(w.caseval,0) caseval,nvl(w.ecoloval,0) ecoloval from (\n" + 
						"select distinct y.name ,z.invoam ,z.caseval,z.ecoloval,z.invobjtype from\n" + 
						"(\n" + 
						"with x as\n" + 
						" (select o.invobjtype,\n" + 
						"         nvl(o.invoam, 0) invoam,\n" + 
						"         nvl(n.caseval,0) caseval,\n" + 
						"         nvl(n.ecoloval,0) ecoloval\n" + 
						"    from dc_cpr_infoware        i,\n" + 
						"         dc_CPR_INVOLVED_OBJECT o,\n" + 
						"         dc_cpr_feedback        f,\n" + 
						"         dc_CPR_CASE_INFO       n,\n" + 
						"         DC_CPR_INFO_PROVIDER   p,\n" + 
						"         dc_cpr_involved_main   m\n" + 
						"   where i.invobjid = o.invobjid(+)\n" + 
						"     and o.invobjid = f.coninvobjectid(+)\n" + 
						"     and f.caseinfoid = n.caseinfoid(+)\n" + 
						"     and p.infproid=i.INFPROID\n" + 
						"     and m.invmaiid=i.invmaiid\n" + 
						"     and o.businesstype is not null\n" + 
						"     and o.invobjtype is not null\n" + 
						"     and o.businesstype='CH20'\n" + 
						"     and length(trim(o.invobjtype))=8\n" + 
						"     and i.regtime>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"     and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"     --and i.inftype=''  信息件类型\n" + 
						"     --and i.INFOORI=''   来源方式\n" + 
						"     --and i.INCFORM=''   接收方式\n" + 
						"     --and i.EVEGRADE=''  事件级别\n" + 
						"     --and p.PERTYPE='1'  人员身份\n" + 
						"     --and m.PTTYPE='10'  涉及主体类型\n" + 
						"     --and m.TRADETYPE='200'  行业类型\n" + 
						"     --and m.WEBSITETYPE=''  网站类型\n" + 
						"     --and m.ENTTYPE =''    企业类型\n" + 
						parameter+
						"      )\n" + 
						"select '00工商' as invobjtype,sum(invoam) invoam，sum(caseval) caseval,sum(ecoloval) ecoloval from x\n" + 
						"union all\n" + 
						"select x.invobjtype,sum(invoam) invoam，sum(caseval) caseval,sum(ecoloval) ecoloval from x  group by x.invobjtype\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,3)||'00000' invobjtype,sum(invoam) invoam，sum(caseval) caseval,sum(ecoloval) ecoloval from x group by substr(x.invobjtype,0,3)\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,5)||'000' invobjtype,sum(invoam) invoam，sum(caseval) caseval,sum(ecoloval) ecoloval  from x  group by substr(x.invobjtype,0,5)\n" + 
						"union all\n" + 
						"select '9999合计' invobjtype,sum(invoam) invoam，sum(caseval) caseval,sum(ecoloval) ecoloval  from x\n" + 
						") z\n" + 
						"left join\n" + 
						"(select code , (\n" + 
						" case when substr(code,7,2)<>'00' then '　　　　'||name\n" + 
						"      when substr(code,6,3)<>'000'   then '　　　'||name\n" + 
						"      when substr(code,4,5)<>'00000' or substr(code,6,3)='900' then '　　'||name\n" + 
						"      when substr(code,2,7)<>'0000000'then '　'||name\n" + 
						"      else name end) name  from dc_code.dc_12315_codedata where codetable='CH20'\n" + 
						"      union all\n" + 
						"      select '9999合计' code ,'合计'name from dual\n" + 
						"      union all\n" + 
						"      select '00工商' code,'工商' name from dual)y\n" + 
						"on z.invobjtype=y.code  where 1=1)w\n" + 
						"order by w.invobjtype";

		String sqlXiaoWeiHui=
				"select  w.name,nvl(w.invoam,0)invoam,nvl(w.caseval,0) caseval,nvl(w.ecoloval,0) ecoloval from (\n" +
						"select distinct y.name ,z.invoam ,z.caseval,z.ecoloval,z.invobjtype from\n" + 
						"(\n" + 
						"with x as\n" + 
						" (select o.invobjtype,\n" + 
						"         nvl(o.invoam, 0) invoam,\n" + 
						"         nvl(n.caseval,0) caseval,\n" + 
						"         nvl(n.ecoloval,0) ecoloval\n" + 
						"    from dc_cpr_infoware        i,\n" + 
						"         dc_CPR_INVOLVED_OBJECT o,\n" + 
						"         dc_cpr_feedback        f,\n" + 
						"         dc_CPR_CASE_INFO       n,\n" + 
						"         DC_CPR_INFO_PROVIDER   p,\n" + 
						"         dc_cpr_involved_main   m\n" + 
						"   where i.invobjid = o.invobjid(+)\n" + 
						"     and o.invobjid = f.coninvobjectid(+)\n" + 
						"     and f.caseinfoid = n.caseinfoid(+)\n" + 
						"     and p.infproid=i.INFPROID\n" + 
						"     and m.invmaiid=i.invmaiid\n" + 
						"     and o.businesstype is not null\n" + 
						"     and o.invobjtype is not null\n" + 
						"     and o.businesstype='ZH18'\n" + 
						"     and length(trim(o.invobjtype))=10\n" + 
						"     and i.regtime>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"     and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"     --and i.inftype=''  信息件类型\n" + 
						"     --and i.INFOORI=''   来源方式\n" + 
						"     --and i.INCFORM=''   接收方式\n" + 
						"     --and i.EVEGRADE=''  事件级别\n" + 
						"     --and p.PERTYPE='1'  人员身份\n" + 
						"     --and m.PTTYPE='10'  涉及主体类型\n" + 
						"     --and m.TRADETYPE='200'  行业类型\n" + 
						"     --and m.WEBSITETYPE=''  网站类型\n" + 
						"     --and m.ENTTYPE =''    企业类型\n" + 
						parameter+
						"      )\n" + 
						"select '00消委会' as invobjtype,sum(invoam) invoam，sum(caseval) caseval,sum(ecoloval) ecoloval from x\n" + 
						"union all\n" + 
						"select x.invobjtype,sum(invoam) invoam，sum(caseval) caseval,sum(ecoloval) ecoloval from x  group by x.invobjtype\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,2)||'00000000' invobjtype,sum(invoam) invoam，sum(caseval) caseval,sum(ecoloval) ecoloval from x group by substr(x.invobjtype,0,2)\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,4)||'000000' invobjtype,sum(invoam) invoam，sum(caseval) caseval,sum(ecoloval) ecoloval  from x  group by substr(x.invobjtype,0,4)\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,6)||'0000' invobjtype,sum(invoam) invoam，sum(caseval) caseval,sum(ecoloval) ecoloval  from x  group by substr(x.invobjtype,0,6)\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,8)||'00' invobjtype,sum(invoam) invoam，sum(caseval) caseval,sum(ecoloval) ecoloval  from x  group by substr(x.invobjtype,0,8)\n" + 
						"union all\n" + 
						"select '9999合计' invobjtype,sum(invoam) invoam，sum(caseval) caseval,sum(ecoloval) ecoloval  from x\n" + 
						") z\n" + 
						"left join\n" + 
						"(select code , (case when substr(code,9,2)<>'00'then '　　　　'||name\n" + 
						"      when substr(code,7,4)<>'0000'then '　　　'||name\n" + 
						"      when substr(code,5,6)<>'000000'then '　　'||name\n" + 
						"      when substr(code,3,8)<>'00000000'then '　'||name\n" + 
						"      else name end) name  from dc_code.dc_12315_codedata where codetable='ZH18'\n" + 
						"      union all\n" + 
						"      select '9999合计' code ,'合计'name from dual\n" + 
						"      union all\n" + 
						"      select '00消委会' code ,'消委会'name from dual)y\n" + 
						"      on z.invobjtype=y.code\n" + 
						"      where 1=1)w\n" + 
						"order by w.invobjtype";

		String sqlZhiShiChanQuan=
				"--第三部分 知识产权\n" +
						"select  w.name,nvl(w.invoam,0)invoam,nvl(w.caseval,0) caseval,nvl(w.ecoloval,0) ecoloval from (\n" + 
						"select distinct y.name ,z.invoam ,z.caseval,z.ecoloval,z.invobjtype from\n" + 
						"(\n" + 
						"with x as\n" + 
						" (select o.invobjtype,\n" + 
						"         nvl(o.invoam, 0) invoam,\n" + 
						"         nvl(n.caseval,0) caseval,\n" + 
						"         nvl(n.ecoloval,0) ecoloval\n" + 
						"    from dc_cpr_infoware        i,\n" + 
						"         dc_CPR_INVOLVED_OBJECT o,\n" + 
						"         dc_cpr_feedback        f,\n" + 
						"         dc_CPR_CASE_INFO       n,\n" + 
						"         DC_CPR_INFO_PROVIDER   p,\n" + 
						"         dc_cpr_involved_main   m\n" + 
						"   where i.invobjid = o.invobjid(+)\n" + 
						"     and o.invobjid = f.coninvobjectid(+)\n" + 
						"     and f.caseinfoid = n.caseinfoid(+)\n" + 
						"     and p.infproid=i.INFPROID\n" + 
						"     and m.invmaiid=i.invmaiid\n" + 
						"     and o.businesstype is not null\n" + 
						"     and o.invobjtype is not null\n" + 
						"     and o.businesstype='ZH19'\n" + 
						"     and length(trim(o.invobjtype))=3\n" + 
						"     and i.regtime>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"     and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"     --and i.inftype=''  信息件类型\n" + 
						"     --and i.INFOORI=''   来源方式\n" + 
						"     --and i.INCFORM=''   接收方式\n" + 
						"     --and i.EVEGRADE=''  事件级别\n" + 
						"     --and p.PERTYPE='1'  人员身份\n" + 
						"     --and m.PTTYPE='10'  涉及主体类型\n" + 
						"     --and m.TRADETYPE='200'  行业类型\n" + 
						"     --and m.WEBSITETYPE=''  网站类型\n" + 
						"     --and m.ENTTYPE =''    企业类型\n" + 
						parameter+
						"      )\n" + 
						"select '00知识产权' as invobjtype,sum(invoam) invoam，sum(caseval) caseval,sum(ecoloval) ecoloval from x\n" + 
						"union all\n" + 
						"select x.invobjtype,sum(invoam) invoam，sum(caseval) caseval,sum(ecoloval) ecoloval from x  group by x.invobjtype\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,1)||'00' invobjtype,sum(invoam) invoam，sum(caseval) caseval,sum(ecoloval) ecoloval from x group by substr(x.invobjtype,0,1)\n" + 
						"union all\n" + 
						"select '9999合计' invobjtype,sum(invoam) invoam，sum(caseval) caseval,sum(ecoloval) ecoloval  from x\n" + 
						") z\n" + 
						"left join\n" + 
						"(select code , (case when substr(code,2,2)<>'00'then '　'||name\n" + 
						"      else name end) name  from dc_code.dc_12315_codedata where codetable='ZH19'\n" + 
						"      union all\n" + 
						"      select '9999合计' code ,'合计'name from dual\n" + 
						"      union all\n" + 
						"      select '00知识产权' code ,'知识产权'name from dual)y\n" + 
						"      on z.invobjtype=y.code\n" + 
						"      where 1=1)w\n" + 
						"      where w.name is not null\n" + 
						"order by w.invobjtype";

		String sqlJiaGeJianCha=
				"--第四部分 价格检查\n" +
						"select  w.name,nvl(w.invoam,0)invoam,nvl(w.caseval,0) caseval,nvl(w.ecoloval,0) ecoloval from (\n" + 
						"select distinct y.name ,z.invoam ,z.caseval,z.ecoloval,z.invobjtype from\n" + 
						"(\n" + 
						"with x as\n" + 
						" (select o.invobjtype,\n" + 
						"         nvl(o.invoam, 0) invoam,\n" + 
						"         nvl(n.caseval,0) caseval,\n" + 
						"         nvl(n.ecoloval,0) ecoloval\n" + 
						"    from dc_cpr_infoware        i,\n" + 
						"         dc_CPR_INVOLVED_OBJECT o,\n" + 
						"         dc_cpr_feedback        f,\n" + 
						"         dc_CPR_CASE_INFO       n,\n" + 
						"         DC_CPR_INFO_PROVIDER   p,\n" + 
						"         dc_cpr_involved_main   m\n" + 
						"   where i.invobjid = o.invobjid(+)\n" + 
						"     and o.invobjid = f.coninvobjectid(+)\n" + 
						"     and f.caseinfoid = n.caseinfoid(+)\n" + 
						"     and p.infproid=i.INFPROID\n" + 
						"     and m.invmaiid=i.invmaiid\n" + 
						"     and o.businesstype is not null\n" + 
						"     and o.invobjtype is not null\n" + 
						"     and o.businesstype='ZH20'\n" + 
						"     and length(trim(o.invobjtype))=4\n" + 
						"     and i.regtime>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"     and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"     --and i.inftype=''  信息件类型\n" + 
						"     --and i.INFOORI=''   来源方式\n" + 
						"     --and i.INCFORM=''   接收方式\n" + 
						"     --and i.EVEGRADE=''  事件级别\n" + 
						"     --and p.PERTYPE='1'  人员身份\n" + 
						"     --and m.PTTYPE='10'  涉及主体类型\n" + 
						"     --and m.TRADETYPE='200'  行业类型\n" + 
						"     --and m.WEBSITETYPE=''  网站类型\n" + 
						"     --and m.ENTTYPE =''    企业类型\n" + 
						parameter+
						"      )\n" + 
						"select '00价格检查' as invobjtype,sum(invoam) invoam，sum(caseval) caseval,sum(ecoloval) ecoloval from x\n" + 
						"union all\n" + 
						"select x.invobjtype,sum(invoam) invoam，sum(caseval) caseval,sum(ecoloval) ecoloval from x  group by x.invobjtype\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,2)||'00' invobjtype,sum(invoam) invoam，sum(caseval) caseval,sum(ecoloval) ecoloval from x group by substr(x.invobjtype,0,2)\n" + 
						"UNION ALL\n" + 
						"select '9999合计' invobjtype,sum(invoam) invoam，sum(caseval) caseval,sum(ecoloval) ecoloval  from x\n" + 
						") z\n" + 
						"left join\n" + 
						"(select code , (case when substr(code,3,2)<>'00'then '　'||name\n" + 
						"      else name end) name  from dc_code.dc_12315_codedata where codetable='ZH20'\n" + 
						"      union all\n" + 
						"      select '9999合计' code ,'合计'name from dual\n" + 
						"      union all\n" + 
						"      select '00价格检查' code ,'价格检查'name from dual)y\n" + 
						"      on z.invobjtype=y.code\n" + 
						"      where 1=1)w\n" + 
						"      where w.name is not null\n" + 
						"order by w.invobjtype";

		String sqlZhiLianJianDu=
				"--第五部分  质量监督\n" +
						"select  w.name,nvl(w.invoam,0)invoam,nvl(w.caseval,0) caseval,nvl(w.ecoloval,0) ecoloval from (\n" + 
						"select distinct y.name ,z.invoam ,z.caseval,z.ecoloval,z.invobjtype from\n" + 
						"(\n" + 
						"with x as\n" + 
						" (select o.invobjtype,\n" + 
						"         nvl(o.invoam, 0) invoam,\n" + 
						"         nvl(n.caseval,0) caseval,\n" + 
						"         nvl(n.ecoloval,0) ecoloval\n" + 
						"    from dc_cpr_infoware        i,\n" + 
						"         dc_CPR_INVOLVED_OBJECT o,\n" + 
						"         dc_cpr_feedback        f,\n" + 
						"         dc_CPR_CASE_INFO       n,\n" + 
						"         DC_CPR_INFO_PROVIDER   p,\n" + 
						"         dc_cpr_involved_main   m\n" + 
						"   where i.invobjid = o.invobjid(+)\n" + 
						"     and o.invobjid = f.coninvobjectid(+)\n" + 
						"     and f.caseinfoid = n.caseinfoid(+)\n" + 
						"     and p.infproid=i.INFPROID\n" + 
						"     and m.invmaiid=i.invmaiid\n" + 
						"     and o.businesstype is not null\n" + 
						"     and o.invobjtype is not null\n" + 
						"     and o.businesstype='ZH21'\n" + 
						"     and length(trim(o.invobjtype))=10\n" + 
						"     and i.regtime>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"     and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"     --and o.businesstype=''  涉及客体类型\n" + 
						"     --and i.inftype=''  信息件类型\n" + 
						"     --and i.INFOORI=''   来源方式\n" + 
						"     --and i.INCFORM=''   接收方式\n" + 
						"     --and i.EVEGRADE=''  事件级别\n" + 
						"     --and p.PERTYPE='1'  人员身份\n" + 
						"     --and m.PTTYPE='10'  涉及主体类型\n" + 
						"     --and m.TRADETYPE='200'  行业类型\n" + 
						"     --and m.WEBSITETYPE=''  网站类型\n" + 
						"     --and m.ENTTYPE =''    企业类型\n" + 
						parameter+
						"      )\n" + 
						"select '00质量监督' as invobjtype,sum(invoam) invoam，sum(caseval) caseval,sum(ecoloval) ecoloval from x\n" + 
						"union all\n" + 
						"select x.invobjtype,sum(invoam) invoam，sum(caseval) caseval,sum(ecoloval) ecoloval from x  group by x.invobjtype\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,2)||'00000000' invobjtype,sum(invoam) invoam，sum(caseval) caseval,sum(ecoloval) ecoloval from x group by substr(x.invobjtype,0,2)\n" + 
						"UNION ALL\n" + 
						"select substr(x.invobjtype,0,4)||'000000' invobjtype,sum(invoam) invoam，sum(caseval) caseval,sum(ecoloval) ecoloval from x group by substr(x.invobjtype,0,4)\n" + 
						"UNION ALL\n" + 
						"select substr(x.invobjtype,0,6)||'0000' invobjtype,sum(invoam) invoam，sum(caseval) caseval,sum(ecoloval) ecoloval from x group by substr(x.invobjtype,0,6)\n" + 
						"UNION ALL\n" + 
						"select substr(x.invobjtype,0,8)||'00' invobjtype,sum(invoam) invoam，sum(caseval) caseval,sum(ecoloval) ecoloval from x group by substr(x.invobjtype,0,8)\n" + 
						"UNION ALL\n" + 
						"select '9999合计' invobjtype,sum(invoam) invoam，sum(caseval) caseval,sum(ecoloval) ecoloval  from x\n" + 
						") z\n" + 
						"left join\n" + 
						"(select code , (case when substr(code,9,2)<>'00'then '　　　　'||name\n" + 
						"      when substr(code,7,4)<>'0000'then '　　　'||name\n" + 
						"      when substr(code,5,6)<>'000000'then '　　'||name\n" + 
						"      when substr(code,3,8)<>'00000000'then '　'||name\n" + 
						"      else name end) name  from dc_code.dc_12315_codedata where codetable='ZH21'\n" + 
						"      union all\n" + 
						"      select '9999合计' code ,'合计'name from dual\n" + 
						"       union all\n" + 
						"      select '00质量监督' code ,'质量监督'name from dual)y\n" + 
						"      on z.invobjtype=y.code\n" + 
						"      where 1=1)w\n" + 
						"      where w.name is not null\n" + 
						"order by w.invobjtype";
		/**
		 * 根据前台选择的业务类别代码来选择查询sql并放入数组中
		 */
			String[] str ;
			if (objectType != null && "CH20".equals(objectType)) {
				str=new String[]{sqlGongShang};
			} else if (objectType != null && "ZH18".equals(objectType)) {
				str=new String[]{sqlXiaoWeiHui};
			} else if (objectType != null && "ZH19".equals(objectType)) {
				str=new String[]{sqlZhiShiChanQuan};
			} else if (objectType != null && "ZH20".equals(objectType)) {
				str=new String[]{sqlJiaGeJianCha};
			} else if (objectType != null && "ZH21".equals(objectType)) {
				str=new String[]{sqlZhiLianJianDu};
			} else {
				str=new String[]{sqlGongShang,sqlXiaoWeiHui,sqlZhiShiChanQuan,sqlJiaGeJianCha,sqlZhiLianJianDu};
			}
		return str;
	}

	//消费者权益服务站统计表
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> fuWuZhanXiaoFeiZhe(String beginTime, String endTime,
			String stationtype, String regno, String status, int i,
			HttpServletRequest req) {
		List list=new ArrayList();
		List<Map> list1=new ArrayList<Map>();
		//list.add(beginTime+" 00:00:00");
		//list.add(endTime+" 23:59:59");
		StringBuffer sb=new StringBuffer();
		sb.append(
				"select cc.name,nvl(z.福田,0) 福田,nvl(z.罗湖,0) 罗湖,nvl(z.南山,0)南山,nvl(z.盐田,0)盐田,nvl(z.宝安,0)宝安,nvl(z.龙岗,0)龙岗,nvl(z.光明,0)光明,nvl(z.坪山,0)坪山,nvl(z.龙华,0)龙华,nvl(z.大鹏,0)大鹏,\n" +
						"nvl(z.福田,0) +nvl(z.罗湖,0)+nvl(z.南山,0)+nvl(z.盐田,0)+nvl(z.宝安,0)+nvl(z.龙岗,0)+nvl(z.光明,0)+nvl(z.坪山,0)+nvl(z.龙华,0)+nvl(z.大鹏,0) as 合计\n" + 
						"from(\n" + 
						"\n" + 
						"with x as(\n" + 
						"select s.stationtype,\n" + 
						"(case when s.branchcode='4000'then 1  end) 福田,\n" + 
						"(case when s.branchcode='4100'then 1  end) 罗湖,\n" + 
						"(case when s.branchcode='4200'then 1  end) 南山,\n" + 
						"(case when s.branchcode='4300'then 1  end) 盐田,\n" + 
						"(case when s.branchcode='4400'then 1  end) 宝安,\n" + 
						"(case when s.branchcode='4500'then 1  end) 龙岗,\n" + 
						"(case when s.branchcode='4600'then 1  end) 光明,\n" + 
						"(case when s.branchcode='4700'then 1  end) 坪山,\n" + 
						"(case when s.branchcode='4800'then 1  end) 龙华,\n" + 
						"(case when s.branchcode='4900'then 1  end) 大鹏\n" + 
						"\n" + 
						" from  dc_CPR_STATION s\n" + 
						"where 1=1 \n");
		if (beginTime!=null &&beginTime.length()!=0) {
			sb.append("and s.setupdate>=to_date(?,'yyyy-mm-dd hh24:mi:ss') \n");
			list.add(beginTime.trim()+" 00:00:00");
		}
		if (endTime!=null &&endTime.length()!=0) {
			sb.append("and s.setupdate<=to_date(?,'yyyy-mm-dd hh24:mi:ss') \n");
			list.add(endTime.trim()+" 23:59:59");
		}
		if (stationtype!=null&&stationtype.length()!=0) {
			if (stationtype.endsWith("00")) {
				sb.append("and s.stationtype liek ? \n");
				list.add(stationtype.trim().substring(0, 1)+"%");
			}else{
				sb.append("and s.stationtype = ? \n");
				list.add(stationtype.trim());
			}
		}
		
		if (regno!=null&&regno.length()!=0) {
			sb.append("and s.BRANCHCODE=? \n");
			list.add(regno.trim());
		}
		if (status!=null&&status.length()!=0) {
			sb.append("and s.status = ? \n");
			list.add(status.trim());
		}
		
		sb.append(
				")\n" +
						"--select * from x\n" + 
						"select substr(x.stationtype,0,1)||'00' stationtype,sum(x.福田) 福田,sum(x.罗湖)罗湖,sum(x.南山) 南山,sum(x.盐田)盐田,sum(x.宝安) 宝安,sum(x.龙岗) 龙岗,sum(x.光明) 光明,sum(x.坪山) 坪山,sum(x.龙华) 龙华,sum(x.大鹏) 大鹏 from x group by substr(x.stationtype,0,1)\n" + 
						"union all\n" + 
						"select x.stationtype,sum(x.福田) 福田,sum(x.罗湖)罗湖,sum(x.南山) 南山,sum(x.盐田)盐田,sum(x.宝安) 宝安,sum(x.龙岗) 龙岗,sum(x.光明) 光明,sum(x.坪山) 坪山,sum(x.龙华) 龙华,sum(x.大鹏) 大鹏\n" + 
						"from x\n" + 
						"group by x.stationtype\n" + 
						"union all\n" + 
						"select '999' as stationtype,sum(x.福田) 福田,sum(x.罗湖)罗湖,sum(x.南山) 南山,sum(x.盐田)盐田,sum(x.宝安) 宝安,sum(x.龙岗) 龙岗,sum(x.光明) 光明,sum(x.坪山) 坪山,sum(x.龙华) 龙华,sum(x.大鹏) 大鹏\n" + 
						"from x\n" + 
						"union all\n" + 
						"select '9999' as stationtype,round(nvl(sum(x.福田),0)/nvl(sum(1),1),2) 福田,\n" + 
						"                             round(nvl(sum(x.罗湖),0)/nvl(sum(1),1),2) 罗湖,\n" + 
						"                             round(nvl(sum(x.南山),0)/nvl(sum(1),1),2) 南山,\n" + 
						"                             round(nvl(sum(x.盐田),0)/nvl(sum(1),1),2) 盐田,\n" + 
						"                             round(nvl(sum(x.宝安),0)/nvl(sum(1),1),2) 宝安,\n" + 
						"                             round(nvl(sum(x.龙岗),0)/nvl(sum(1),1),2) 龙岗,\n" + 
						"                             round(nvl(sum(x.光明),0)/nvl(sum(1),1),2) 光明,\n" + 
						"                             round(nvl(sum(x.坪山),0)/nvl(sum(1),1),2) 坪山,\n" + 
						"                             round(nvl(sum(x.龙华),0)/nvl(sum(1),1),2) 龙华,\n" + 
						"                             round(nvl(sum(x.大鹏),0)/nvl(sum(1),1),2) 大鹏\n" + 
						"from x\n" + 
						") z\n" + 
						"right join ((select c.code,(case when c.parentcode=0 then c.code||c.name else '　'||c.code||c.name end ) name from dc_code.dc_12315_codedata c where codetable='ZH12'\n" + 
						"--order by c.code\n" + 
						")\n" + 
						"union all\n" + 
						"(select '999' as code,'总计' as  name from dual)\n" + 
						"union all\n" + 
						"(select '9999' as code,'比重' as  name from dual)) cc\n" + 
						"on z.stationtype=cc.code\n" + 
						"   order by cc.code asc");
		try {
			list1=dao.queryForList(sb.toString(), list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("消费者权益服务站统计表", "WDY", i == 1 ? "查看报表" : "下载报表",
				sb.toString(), beginTime + "," + endTime+","+stationtype+","+regno+","+status, req);
		return list1;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> fuWuZhanXinXiJian(Map map, int i, HttpServletRequest req) {
		StringBuffer sb=new StringBuffer();
		List list=new ArrayList();
		List<Map> list1=new ArrayList<Map>();
		sb.append(
				"select i.REGINO , p.persname ,m.invname ,i.content,to_char(t.transtime,'yyyy-mm-dd') transtime,\n" +
						"       to_char(t.receivetime,'yyyy-mm-dd')receivetime,\n" + 
						"       to_char(r.replytime,'yyyy-mm-dd')replytime,\n" + 
						"       to_char(i.finishtime,'yyyy-mm-dd') finishtime,\n" + 
						"       c.operator,(case when c.success=0 then '失败' else '成功' end)success,to_char(c.amountmoney,'fm9999999999990') amountmoney from dc_cpr_infoware i,\n" + 
						"         dc_cpr_info_provider p,\n" + 
						"         dc_cpr_involved_main m,\n" + 
						"         dc_cpr_infoware_transfer t,\n" + 
						"         dc_cpr_infoware_transfer_reply r,\n" + 
						"         dc_cpr_conciliation c,\n" + 
						"         dc_CPR_STATION s\n" + 
						"   where i.INFPROID=p.Infproid\n" + 
						"   and i.INVMAIID=m.invmaiid\n" + 
						"   and i.INFOWAREID=t.Infowareid\n" + 
						"   and t.INFTRAID=r.INFTRAID\n" + 
						"   and t.INFTRAID=c.INFTRAID\n" + 
						"   and t.STATIONID=s.STATIONID \n");
		if (!stringIsNull(map.get("paifakaishi"))) {
			sb.append("and t.TRANSTIME>=to_date(?,'yyyy-mm-dd hh24:mi:ss') \n");
			list.add(map.get("paifakaishi")+" 00:00:00");
		}
		if (!stringIsNull(map.get("paifajieshu"))) {
			sb.append("and t.TRANSTIME<=to_date(?,'yyyy-mm-dd hh24:mi:ss') \n  ");
			list.add(map.get("paifajieshu")+" 23:59:59");
		}
		if (!stringIsNull(map.get("jiedankaishi"))) {
			sb.append("and t.RECEIVETIME>=to_date(?,'yyyy-mm-dd hh24:mi:ss') \n  ");
			list.add(map.get("jiedankaishi")+" 00:00:00");
		}
		if (!stringIsNull(map.get("jiedanjieshu"))) {
			sb.append("and t.RECEIVETIME<=to_date(?,'yyyy-mm-dd hh24:mi:ss') \n  ");
			list.add(map.get("jiedanjieshu")+" 23:59:59");
		}
		if (!stringIsNull(map.get("huifukaishi"))) {
			sb.append("and r.replytime>=to_date(?,'yyyy-mm-dd hh24:mi:ss') \n  ");
			list.add(map.get("huifukaishi")+" 00:00:00");
		}
		if (!stringIsNull(map.get("huifujieshu"))) {
			sb.append("and r.replytime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') \n  ");
			list.add(map.get("huifujieshu")+" 23:59:59");
		}
		if (!stringIsNull(map.get("banjiekaishi"))) {
			sb.append("and i.FINISHTIME>=to_date(?,'yyyy-mm-dd hh24:mi:ss') \n  ");
			list.add(map.get("banjiekaishi")+" 00:00:00");
		}
		if (!stringIsNull(map.get("banjiejieshu"))) {
			sb.append("and i.FINISHTIME<=to_date(?,'yyyy-mm-dd hh24:mi:ss') \n  ");
			list.add(map.get("banjiejieshu")+" 23:59:59");
		}
		if (!stringIsNull(map.get("shensuren"))) {
			sb.append("and p.persname=? \n  ");
			list.add(map.get("shensuren"));
		}
		if (!stringIsNull(map.get("shensuduixiang"))) {
			sb.append(" and m.invname=? \n ");
			list.add(map.get("shensuduixiang"));
		}
		if (!stringIsNull(map.get("jingbanren"))) {
			sb.append("and c.operator=? \n ");
			list.add(map.get("jingbanren"));
		}
		if (!stringIsNull(map.get("tiaojiechenggong"))) {
			sb.append("and c.success = ?  \n ");
			list.add(map.get("tiaojiechenggong"));
		}
		try {
			list1=dao.queryForList(sb.toString(), list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("服务站信息件统计表", "WDY", i == 1 ? "查看报表" : "下载报表",
				sb.toString(), map.toString(), req);
		return list1;
	}

	@SuppressWarnings("rawtypes")
	public  List<Map> getRegCode() {
		List<Map> res=new ArrayList<Map>();
		try {
			res=dao.queryForList("select regdep as text, regdepcode as value\n" + 
							"  from DC_CPR_WORKWARE\n" + 
							" group by regdepcode, regdep", null);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return res;
	}


	@SuppressWarnings("rawtypes")
	public List<Map> getRegCode(String str){
		List<Map> res=new ArrayList<Map>();
		try {
			res=dao.queryForList(
					"\n" +
					"\n" + 
					"select nvl(branchcode,'9999') id,'1' p_id,(case when branchcode='4000' then '福田局'\n" + 
					"when branchcode='4100' then '罗湖局'\n" + 
					"  when branchcode='4200' then '南山局'\n" + 
					"    when branchcode='4300' then '盐田局'\n" + 
					"      when branchcode='4400' then '宝安局'\n" + 
					"        when branchcode='4500' then '龙岗局'\n" + 
					"          when branchcode ='4600' then '光明局'\n" + 
					"            when branchcode='4700' then '坪山局'\n" + 
					"              when branchcode='4800' then '龙华局'\n" + 
					"                when branchcode='4900' then '大鹏局'\n" + 
					"                  else '其他' end) name\n" + 
					"                    from DC_CPR_STATION group by branchcode\n" + 
					"union all\n" + 
					"select nvl(d.deptcode,'8888') id,nvl(d.branchcode,'9999') p_id, nvl(de.dep_name,'其他') name from DC_CPR_STATION d ,dc_jg_sys_right_department  de\n" + 
					"where d.deptcode=de.sys_right_department_id(+)\n" + 
					"\n" + 
					"  group by nvl(deptcode,'8888') ,d.branchcode , de.dep_name\n" + 
					"union all\n" + 
					"select d.stano id,nvl(d.deptcode,'8888') p_id, d.name name from DC_CPR_STATION d\n" + 
					"group by d.stano ,nvl(d.deptcode,'8888') , d.name", null);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return res;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> gongZuoLianXiJian(String begintime, String endtime,
			String stano, String regcode, int i, HttpServletRequest req) {
		List<Map> res=new ArrayList<Map>();
		List list=new ArrayList(); 
		StringBuffer sb=new StringBuffer();
		sb.append(
				"select w.WORKNO,w.REGDEP 登记部门,w.content 主要内容, t.TRATIME 转发时间,w.ACCEPTTIME 接单时间,\n" +
						"r.REPLYTIME 回复时间,w.finishtime 办结时间,t.TRAUSENAME 服务站经办人,s.SUPNAME1 监管部门跟踪人\n" + 
						"  from DC_CPR_WORKWARE w,dc_CPR_WORKWARE_TRANSFER t ,dc_CPR_WORKWARE_TRANSFER_REPLY r,DC_CPR_STATION s\n" + 
						"where w.workwareid=t.workwareid and t.wortraid=r.wortraid and t.STATIONID=s.STATIONID\n" + 
						"and w.regtime>to_date(?,'yyyy-mm-dd')\n" + 
						"and w.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss')");
		list.add(begintime);
		list.add(endtime+" 23:59:59");
		if (regcode !=null &&regcode.length()!=0) {
			sb.append("and w.regdepcode=? \n");
			list.add(regcode);
		}
		if (stano !=null&&stano.length()!=0) {
			if ("8888".equals(stano)) {
				sb.append("and  s.DEPTCODE is null \n");
			}else if("9999".equals(stano)){
				sb.append("and  s.BRANCHCODE is null \n");
			}else if(stano.length()==4&&stano.endsWith("00")&&stano.startsWith("4")){
				sb.append("and  s.BRANCHCODE =? \n");
				list.add(stano);
			}else if(stano.length()==4){
				sb.append(" and s.DEPTCODE =? \n");
				list.add(stano);
			}else{
				sb.append(" and s.STANO =? \n");
				list.add(stano);
			}
		}
		try {
			res=dao.queryForList(sb.toString(), list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("工作联系件统计表", "WDY", i == 1 ? "查看报表" : "下载报表",
				sb.toString(), begintime+","+endtime+","+stano+","+regcode, req);
		return res;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> chuLiQingKuang(String begintime, String endtime,
			String regcode, int i, HttpServletRequest req) {
		List<Map> res=new ArrayList<Map>();
		List list=new ArrayList(); 
		StringBuffer sb=new StringBuffer();
		sb.append(
				"select t.workno 编号,t.cnt 发送数量,t.RECEIVETIME 接收数量,to_char(round(nvl(t.RECEIVETIME,0)/nvl(t.cnt,1),4)*100,'fm990.0999')||'%' 接收率,\n" +
						" t.REPLYTIME 回复数量,to_char(round(nvl(t.REPLYTIME,0)/nvl(t.cnt,1),4)*100,'fm990.0999')||'%' 回复率,\n" + 
						" t.REPLYTIME 办结数量, to_char(round(nvl(t.REPLYTIME,0)/nvl(t.cnt,1),4)*100,'fm990.0999')||'%'办结率 from\n" + 
						" (\n" + 
						" with x as (\n" + 
						" select w.workno, t.wortraid,t.RECEIVETIME  ,--接受时间,\n" + 
						" r.REPLYTIME,t.state\n" + 
						" from dc_CPR_WORKWARE_TRANSFER_REPLY  r ,DC_CPR_WORKWARE w,dc_CPR_WORKWARE_TRANSFER t\n" + 
						" where t.workwareid=w.workwareid\n" + 
						" and t.WORTRAID=r.wortraid(+)\n" + 
						" and  w.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						" and w.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') \n");
		list.add(begintime);
		list.add(endtime+" 23:59:59");
		if (regcode!=null &&regcode.length()!=0) {
			sb.append("and w.regdepcode=? \n");
			list.add(regcode);
		}
		sb.append(")\n" +" select x.workno,count(x.wortraid) cnt,count(x.RECEIVETIME)  RECEIVETIME,\n" + 
						" count(x.REPLYTIME) REPLYTIME from x\n" + 
						" group by x.workno\n" + 
						" ) t");
		try {
			res=dao.queryForList(sb.toString(), list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("工作联系件统计表", "WDY", i == 1 ? "查看报表" : "下载报表",
				sb.toString(), begintime+","+endtime+","+regcode, req);
		return res;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> xinXiJianGongZuoJian(String begintime, String endtime,
			String stano, String regcode, int i, HttpServletRequest req) {
		List<Map> res=new ArrayList<Map>();
		List list1=new ArrayList(); 
		List list2=new ArrayList(); 
		StringBuffer sb1=new StringBuffer();
		StringBuffer sb2=new StringBuffer();
		sb1.append(
				"with x as (\n" +
						"select s.name,\n" + 
						"       i.REGDEPCODE,\n" + 
						"       i.REGDEPNAME,\n" + 
						"       d.PARENT_DEP_CODE,\n" + 
						"       i.finishtime,\n" + 
						"       i.regtime,\n" + 
						"       c.success,\n" + 
						"       (case when i.regtime>=to_date(?,'yyyy-mm-dd') and\n" + 
						"          i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"          then 1 end) flag,\n" + 
						"       (case when i.regtime>=to_date(?,'yyyy-mm-dd') and\n" + 
						"          i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"          then 1 end) flag1\n" + 
						"  from dc_cpr_infoware i, DC_CPR_INFOWARE_TRANSFER t, dc_CPR_CONCILIATION c,dc_cpr_station s, dc_jg_sys_right_department d\n" + 
						" where t.inftraid = c.inftraid(+)\n" + 
						"   and i.infowareid = t.infowareid(+)\n" + 
						"   and s.stationid(+)=t.stationid\n" + 
						"   and i.regdepcode=d.sys_right_department_id \n");
		list1.add(begintime);
		list1.add(endtime+" 23:59:59");
		list1.add(this.getTongBiDate(begintime));
		list1.add(this.getTongBiDate(endtime)+" 23:59:59");
		if (regcode !=null &&regcode.length()!=0) {
			sb1.append("and i.REGDEPCODE=? \n");
			list1.add(regcode);
		}
			if (stano !=null&&stano.length()!=0) {
				if ("8888".equals(stano)) {
					sb1.append("and  s.DEPTCODE is null \n");
				}else if("9999".equals(stano)){
					sb1.append("and  s.BRANCHCODE is null \n");
				}else if(stano.length()==4&&stano.endsWith("00")&&stano.startsWith("4")){
					sb1.append("and  s.BRANCHCODE =? \n");
					list1.add(stano);
				}else if(stano.length()==4){
					sb1.append(" and s.DEPTCODE =? \n");
					list1.add(stano);
				}else{
					sb1.append(" and s.STANO =? \n");
					list1.add(stano);
				}
			}
			sb1.append(
					")select '信息件' as waretype,x.PARENT_DEP_CODE,x.regdepname bumen,count(x.flag)dengjishu,\n" +
							"to_char(round((count(x.flag)-count(x.flag1))/decode(count(x.flag1),0,1,count(x.flag1)),4)*100,'fm9999999990.09')||'%' tongzeng,\n" + 
							"\n" + 
							"count(CASE WHEN x.finishtime is not null and x.flag='1' then 1 else null end) banjie,\n" + 
							"to_char(round(count(CASE WHEN x.finishtime is not null and x.flag='1'then 1 else null end)/decode(count(x.flag),0,1,count(x.flag)),4)*100,'fm9999990.09')||'%'  banjielv,\n" + 
							"count(case when x.success='1'and x.flag='1' then 1 else null end )tiaojie,\n" + 
							"to_char(round(count(case when x.success='1' and x.flag='1' then 1 else null end )/decode(count(x.flag),0,1,count(x.flag)),4)*100,'fm9999990.09')||'%' tiaojielv,\n" + 
							"'0'as tiaojie,'0%' as tiaojielv, \n"+
							"count(case when x.finishtime is  null and x.flag='1' then 1 else null end) weichuli,\n" + 
							"to_char(round(count(case when x.finishtime is  null and x.flag='1'then 1 else null end)/decode(count(x.flag),0,1,count(x.flag)),4)*100,'fm9999990.09')||'%' weichulilv\n" + 
							"from x\n" + 
							"group by x.regdepname,x.PARENT_DEP_CODE\n" + 
							"order by x. PARENT_DEP_CODE,x.regdepname desc");
			sb2.append(
					"with x as (\n" +
							"select w.regdepcode ,w.REGDEP ,\n" + 
							"(case when w.regtime>=to_date(?,'yyyy-mm-dd') and\n" + 
							"          w.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
							"          then 1 end) flag,\n" + 
							"       (case when w.regtime>=to_date(?,'yyyy-mm-dd') and\n" + 
							"          w.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
							"          then 1 end) flag1,w.finishtime\n" + 
							"from dc_CPR_WORKWARE w where 1=1 \n");
			list2.add(begintime);
			list2.add(endtime+" 23:59:59");
			list2.add(this.getTongBiDate(begintime));
			list2.add(this.getTongBiDate(endtime)+" 23:59:59");
			if (regcode!=null &&regcode.length()!=0) {
				sb2.append("and  REGDEPCODE=? \n");
				list2.add(regcode);
			}
			sb2.append(
					")SELECT '工作联系件' waretype,x.regdep  bumen,count(x.flag)dengjishu,\n" +
							"to_char(round((count(x.flag)-count(x.flag1))/decode(count(x.flag1),0,1,count(x.flag1)),4)*100,'fm9999990.09')||'%' tongzeng,\n" + 
							"count(case when x.finishtime is not null and x.flag='1' then 1 else null end) banjie,\n" + 
							"to_char(round(count(case when x.finishtime is not null and x.flag='1' then 1 else null end)/decode(count(x.flag),0,1,count(x.flag)),4)*100,'fm9999990.09')||'%' banjielv,\n" + 
							" '0'as tiaojie,'0%' as tiaojielv,\n" + 
							"count(case when x.finishtime is null and x.flag='1' then 1 else null end )weichuli,\n" + 
							"to_char(round(count(case when x.finishtime is null and x.flag='1' then 1 else null end )/decode(count(x.flag),0,1,count(x.flag)),4)*100,'fm9999990.09')||'%'weichulilv\n" + 
							"FROM X group by x.regdep");
			try {
				res=dao.queryForList(sb1.toString(),list1);
				res.addAll(dao.queryForList(sb2.toString(), list2));
			} catch (OptimusException e) {
				e.printStackTrace();
			}
			return res;
	}
	
	

	
	//获取同比时间（去年同期）
	public String getTongBiDate(String beforDate){
		String str=beforDate;
		try {
			cal.setTime(sdf.parse(str));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.add(Calendar.YEAR, -1);//
		str = sdf.format(cal.getTime());
		return str;
	}

	@SuppressWarnings("rawtypes")
	public List<Map> getStationType() {
		List<Map> res=new ArrayList<Map>();
		try {
			res=dao.queryForList(
					"select code as id,parentcode as p_id, name as name from dc_code.dc_12315_codedata where codetable='ZH12'", null);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return res;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> ziXingDengJi(String begintime, String endtime,
			String stano, String stationtype, int i, HttpServletRequest req) {
		List<Map> res=new ArrayList<Map>();
		List list=new ArrayList(); 
		StringBuffer sb=new StringBuffer();
		sb.append(
				"select t.name as name,t.cnt cnt ,t.success as success,\n" +
						"to_char(round(t.success/decode(nvl(t.cnt,0),0,1,nvl(t.cnt,0)),4)*100,'fm99999990.09')||'%' tiaojielv,\n" + 
						"t.jine from\n" + 
						"(\n" + 
						"with x as (\n" + 
						"select i.id,s.name,s.stationtype ,\n" + 
						"i.SUCCESS,i.AMOUNT\n" + 
						"from DC_CPR_COMPLAINT_INFO i,dc_cpr_station s\n" + 
						"where i.stationid=s.stationid\n" + 
						"and i.COMPLAINTTIME>=to_date(?,'yyyy-mm-dd')\n" + 
						"and i.complainttime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') \n");
		list.add(begintime);
		list.add(endtime+" 23:59:59");
		if (stano !=null&&stano.length()!=0) {
			if ("8888".equals(stano)) {
				sb.append("and  s.DEPTCODE is null \n");
			}else if("9999".equals(stano)){
				sb.append("and  s.BRANCHCODE is null \n");
			}else if(stano.length()==4&&stano.endsWith("00")&&stano.startsWith("4")){
				sb.append("and  s.BRANCHCODE =? \n");
				list.add(stano);
			}else if(stano.length()==4){
				sb.append(" and s.DEPTCODE =? \n");
				list.add(stano);
			}else{
				sb.append(" and s.STANO =? \n");
				list.add(stano);
			}
		}
		if (stationtype!=null &&stationtype.length()!=0) {
			if (stationtype.endsWith("00")) {
				sb.append("and substr(s.stationtype,0,1)=? \n");
				list.add(stationtype.substring(0, 1));
			}else{
				sb.append("and s.stationtype =? \n");
				list.add(stationtype);
			}
		}
		sb.append(
				")\n" +
						"select x.name,count(1)as cnt,sum(x.success)success,sum(x.amount)jine from x\n" + 
						"group by x.name\n" + 
						"union all\n" + 
						"select '合计' name,count(1) as cnt,sum(x.success) success,sum(x.amount) jine from x\n" + 
						")t");
		try {
			res=dao.queryForList(sb.toString(), list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return res;
	}
	





}
