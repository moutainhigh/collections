package com.gwssi.report.service;

import java.text.SimpleDateFormat;
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
import com.gwssi.report.query_jiean.QueryJieAnUtils;
import com.gwssi.report.util.LogOperation;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Service
public class QueryJieAnService extends BaseService implements ReportSource{
	IPersistenceDAO dao = this.getPersistenceDAO("dc_dc");
	LogOperation logop = new LogOperation();
	static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	@Override
	public ReportModel getReport(TCognosReportBO queryInfo) {
		return null;
	}
	public List<Map> getRegDepTree() {
		List<Map>  list=new ArrayList<Map>();
		try {
			list=dao.queryForList(
					"select d.SYS_RIGHT_DEPARTMENT_ID as id,"
					+ "d.PARENT_DEP_CODE as p_id,d.dep_name as name  "
					+ "from dc_dc.dc_jg_sys_right_department  d",null );
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return list;
	}
	public List<Map> getWenTiLeiXingTree() {
		List<Map>  list=new ArrayList<Map>();
		try {
			list=dao.queryForList(
					"select d.code as id, d.PARENTCODE as p_id, d.name as name\n" +
							"   from dc_code.dc_12315_codedata d\n" + 
							"  where d.codetable = 'CH27'", null);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return list;
	}
	public List<Map> getKeTiLeiXingTree() {
		List<Map>  list=new ArrayList<Map>();
		try {
			list=dao.queryForList(
						"select 'CH20'||d.code as id, 'CH20'||decode(d.PARENTCODE,'0','1',d.PARENTCODE) as p_id, d.name as name\n" +
						"  from dc_code.dc_12315_codedata d\n" + 
						" where d.codetable in('CH20')\n" + 
						" union all\n" + 
						" select 'CH201' as id,'01' as p_id,'工商' as name from dual\n" + 
						" union all\n" + 
						" select 'ZH18'||d.code as id,  'ZH18'||decode(d.PARENTCODE,'0','2',d.PARENTCODE) as p_id, d.name as name\n" + 
						"  from dc_code.dc_12315_codedata d\n" + 
						" where d.codetable in('ZH18')\n" + 
						" union all\n" + 
						" select 'ZH182' as id,'02' as p_id,'消委会' as name from dual\n" + 
						" union all\n" + 
						" select 'ZH19'||d.code as id, 'ZH19'||decode(d.PARENTCODE,'0','3',d.PARENTCODE) as p_id, d.name as name\n" + 
						"  from dc_code.dc_12315_codedata d\n" + 
						" where d.codetable in('ZH19')\n" + 
						" union all\n" + 
						" select 'ZH193' as id,'03' as p_id,'知识产权' as name from dual\n" + 
						" union all\n" + 
						" select 'ZH20'||d.code as id,'ZH20'|| decode(d.PARENTCODE,'0','4',d.PARENTCODE) as p_id, d.name as name\n" + 
						"  from dc_code.dc_12315_codedata d\n" + 
						" where d.codetable in('ZH20')\n" + 
						" union all\n" + 
						" select 'ZH204' as id,'04' as p_id,'价格检查' as name from dual\n" + 
						" union all\n" + 
						" select 'ZH21'||d.code as id,  'ZH21'||decode(d.PARENTCODE,'0','5',d.PARENTCODE) as p_id, d.name as name\n" + 
						"  from dc_code.dc_12315_codedata d\n" + 
						" where d.codetable in('ZH21')\n" + 
						" union all\n" + 
						" select 'ZH215' as id,'05' as p_id,'质量监督' as name from dual",null);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Map> getZhuTiLeiXingTree() {
		List<Map> list =new ArrayList<Map>();
		try {
			list=dao.queryForList(
							"select d.code as id, d.PARENTCODE as p_id, d.name as name\n" +
							"    from dc_code.dc_12315_codedata d\n" + 
							"   where codetable = 'CH09'",null);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return list;
	}
	public List<Map> getHangYeLeiXingTree() {
		List<Map> list =new ArrayList<Map>();
		try {
			list=dao.queryForList(
					"select d.code as id, d.PARENTCODE as p_id, d.name as name\n" +
							"     from dc_code.dc_12315_codedata d\n" + 
							"    where codetable = 'CH15'",null);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Map> getWeiFaZhongLeiTree() {
		List<Map> list =new ArrayList<Map>();
		try {
			list=dao.queryForList(
					"select d.code as id, d.PARENTCODE as p_id, d.name as name\n" +
							"    from dc_code.dc_12315_codedata d\n" + 
							"   where codetable = 'CE02'", null);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return list;
	}
	public List<Map> getXingZhengChuFaSelect() {
		List<Map> list =new ArrayList<Map>();
		try {
			list=dao.queryForList(
					"select d.name as text, d.code as value\n" +
							"     from dc_code.dc_12315_codedata d\n" + 
							"    where codetable = 'CE12'", null);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return list;
	}
	public List<Map> getZhiXingLeiBieSelect() {
		List<Map> list =new ArrayList<Map>();
		try {
			list=dao.queryForList("select d.name as text, d.code as value\n" +
					"      from dc_code.dc_12315_codedata d\n" + 
					"     where codetable = 'CE38'", null);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Map> getJieShouFangShiSelect() {
		List<Map> list = new ArrayList<Map>();
		try {
			list=dao.queryForList("select d.name as text, d.code as value\n"
					+ "     from dc_code.dc_12315_codedata d\n"
					+ "    where codetable = 'CH03'", null);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Map> getWangZhanLeiXingSelect() {
		List<Map> list = new ArrayList<Map>();
		try {
			list = dao.queryForList("select d.name as text, d.code as value\n"
					+ "      from dc_code.dc_12315_codedata d\n"
					+ "     where codetable = 'ZH04'", null);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return list;
	}
	public List<Map> getQinQuanLeiXingSelect() {
		List<Map> list = new ArrayList<Map>();
		try {
			list=dao.queryForList(
					"select d.name as text, d.code as value\n" +
							"      from dc_code.dc_12315_codedata d\n" + 
							"     where codetable = 'CH52'\n" + 
							"", null);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return list;
	}
	public List<Map> getYiWuLeiXingSelect() {
		List<Map> list = new ArrayList<Map>();
		try {
			list=dao.queryForList(
					"select d.name as text, d.code as value\n" +
							"   from dc_code.dc_12315_codedata d\n" + 
							"  where codetable = 'CH53'",null);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return list;
	}
	public List<Map> getInfoTypeSelect() {
		List<Map> list = new ArrayList<Map>();
		try {
			list=dao.queryForList(
					"select d.name as text, d.code as value\n" +
							"      from dc_code.dc_12315_codedata d\n" + 
							"     where codetable = 'CH23'", null);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Map> getChuFaZhongLeiSelect() {
		List<Map> list = new ArrayList<Map>();
		try {
			list=dao.queryForList(
							"select d.name as text, d.code as value\n" +
							"    from dc_code.dc_12315_codedata d\n" + 
							"   where codetable = 'CE16'", null);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Map> getYeWuFanWeiTree() {
		List<Map> list = new ArrayList<Map>();
		try {
			list=dao.queryForList(
					"select t.code as id,t.parentcode as p_id,t.name as name  from dc_code.dc_12315_codedata t where codetable='ZH01'\n" +
							"order by t.code", null);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Map> getRenYuanShenFenSelect() {
		List<Map> list = new ArrayList<Map>();
		try {
			list=dao.queryForList("select t.name as text,"
					+ "t.code as value  from dc_code.dc_12315_codedata t "
					+ "where codetable='CH10'\n" +"order by t.code", null);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Map> getQiYeLeiXingTree() {
		List<Map> list = new ArrayList<Map>();
		try {
			list=dao.queryForList(
					"select t.code as id,t.parentcode as p_id,t.name as name  from dc_code.dc_12315_codedata t where codetable='CA16'\n" +
							"order by t.code", null);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Map> getGuanJianZiSelect() {
		List<Map> list = new ArrayList<Map>();
		try {
			list=dao.queryForList("select t.content as text,t.KEYWORDID as value  from DC_CPR_KEYWORD t", 
					null);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	public List<Map> chuFaZhongLei(Map<String,String> map,int i, HttpServletRequest req) {
		List list=new ArrayList();
		StringBuffer sb=new StringBuffer();
		List<Map> res=new ArrayList<Map>();
		sb.append(
				"with x as (\n" +
						"       select co.name,po.* from (\n" + 
						"       select i.finishtime, --完成时间\n" + 
						"              v.VISITCONFIRM, --回访情况\n" + 
						"              m.MEDIATE_RESULT, --调解结果\n" + 
						"              m.CHEATSIGN， --是否欺诈\n" + 
						"              m.DISAM, --争议金额\n" + 
						"              m.REDECOLOS, --挽回经济损失\n" + 
						"              m.DOUAMEAM, --加倍赔偿金额\n" + 
						"              m.SPIAMEAM, --精神赔偿金额\n" + 
						"              f.PUTONCASE, --是否立案\n" + 
						"              c.CASEVAL, --案值\n" + 
						"              p.illegincome, --违法所得\n" + 
						"              p.penam, --处罚金额\n" + 
						"              p.forfam, --没收金额\n" + 
						"              p.apprcuram, --变价金额\n" + 
						"              c.ECOLOVAL, --经济损失值\n" + 
						"              p.distoriedplace, --捣毁窝点\n" + 
						"              f.executeperson, --出动人数\n" + 
						"              f.executecar, -- 出动车辆\n" + 
						"              h.INJNUM, --受伤人数\n" + 
						"              d.VICTIMNUM, --受害人数\n" + 
						"              h.SACNUM, --牺牲人数\n" + 
						"              p.PENTYPE ,--处罚种类\n" + 
						"              (case when i.regtime >= to_date(?, 'yyyy-mm-dd')\n" + 
						"                   and i.regtime <=\n" + 
						"              to_date(?, 'yyyy-mm-dd hh24:mi:ss')then 1 else 0 end) flag, \n");
		list.add(map.get("begintime"));
		list.add(map.get("endtime")+" 23:59:59");
		sb.append(
				"(case when i.regtime >= to_date(?, 'yyyy-mm-dd')\n" +
						"                    and i.regtime <=\n" + 
						"               to_date(?, 'yyyy-mm-dd hh24:mi:ss')then 1 else 0 end) tflag, \n");
		list.add(QueryJieAnUtils.getTongBiDate(map.get("begintime")));
		list.add(QueryJieAnUtils.getTongBiDate(map.get("endtime"))+" 23:59:59");
		sb.append(
				"(case when i.regtime >= to_date(?, 'yyyy-mm-dd')\n" +
						"                    and i.regtime <=\n" + 
						"               to_date(?, 'yyyy-mm-dd hh24:mi:ss')then 1 else 0 end) hflag \n");
		if (map.get("hbegintime")!=null&&map.get("hbegintime").length()!=0) {
			list.add(map.get("hbegintime"));
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(map.get("begintime")));
		}
		if (map.get("hendtime")!=null&&map.get("hendtime").length()!=0) {
			list.add(map.get("hendtime")+" 23:59:59");
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(map.get("endtime"))+" 23:59:59");
		}
		sb.append(
				"from dc_cpr_infoware         i,\n" +
						"              DC_CPR_RETURN_VISIT     v,\n" + 
						"              dc_cpr_feedback         f,\n" + 
						"              DC_CPR_MEDIATION        m,\n" + 
						"              dc_cpr_case_info        c,\n" + 
						"              DC_CPR_LEGAL_PUNISHMENT p,\n" + 
						"              DC_CPR_CASE_HANDLE      h,\n" + 
						"              DC_CPR_CONFIRMED_DAMAGE d,\n" + 
						"              dc_cpr_involved_object  o,\n" + 
						"              dc_cpr_involved_main    ma\n" + 
						"        where i.infowareid = v.infowareid(+)\n" + 
						"          and i.feedbackid = f.feedbackid\n" + 
						"          and f.mediationid = m.mediationid(+)\n" + 
						"          and f.caseinfoid = c.caseinfoid(+)\n" + 
						"          and c.LEGPUNID = p.legpunid\n" + 
						"          and c.CASEHANDLEID = h.casehandleid(+)\n" + 
						"          and c.CONDAMID = d.condamid(+)\n" + 
						"          and o.invobjid(+) = i.invobjid\n" + 
						"          and i.invmaiid = ma.invmaiid(+) \n");
		if (map.get("dengjibumen")!=null &&map.get("dengjibumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("dengjibumen"),"i.REGDEPCODE"));
		}
		
		if (map.get("chengbanbumen")!=null &&map.get("chengbanbumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("chengbanbumen"),"i.HANDEPCODE "));
		}
		
		if (map.get("wentileixing")!=null &&map.get("wentileixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("wentileixing"),"i.APPLBASQUE"));
		}
		
		if (map.get("ketileixing")!=null &&map.get("ketileixing").length()!=0) {
			sb.append(QueryJieAnUtils.UtilOfObjectTypeS(map.get("ketileixing"),"o.invobjtype"));
			sb.append(QueryJieAnUtils.UtilOfObjectTypeB(map.get("ketileixing"),"o.BUSINESSTYPE"));
		}
		
		if (map.get("zhutileixing")!=null &&map.get("zhutileixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("zhutileixing"),"ma.PTTYPE"));
		}
		
		if (map.get("hangyeleixing")!=null &&map.get("hangyeleixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("hangyeleixing"),"ma.TRADETYPE"));
		}
		
		if (map.get("weifazhonglei")!=null &&map.get("weifazhonglei").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("weifazhonglei"),"p.ILLEGACTTYPE"));
		}
		
		if (map.get("xingzhengchufa")!=null &&map.get("xingzhengchufa").length()!=0) {
			sb.append("and p.COMMEATYPE =? \n ");
			list.add(map.get("xingzhengchufa"));
		}
		
		if (map.get("zhixingleibie")!=null &&map.get("zhixingleibie").length()!=0) {
			sb.append("and h.EXESORT =? \n ");
			list.add(map.get("zhixingleibie"));
		}
		
		if (map.get("jieshoufangshi")!=null &&map.get("jieshoufangshi").length()!=0) {
			sb.append("and i.INCFORM =? \n ");
			list.add(map.get("jieshoufangshi"));
		}
		
		if (map.get("infotype")!=null &&map.get("infotype").length()!=0) {
			sb.append("and i.INFTYPE =? \n ");
			list.add(map.get("infotype"));
		}
		
		if (map.get("wangzhanleixing")!=null &&map.get("wangzhanleixing").length()!=0) {
			sb.append("and ma.WEBSITETYPE =? \n ");
			list.add(map.get("wangzhanleixing"));
		}
		
		if (map.get("qinquanleixing")!=null &&map.get("qinquanleixing").length()!=0) {
			sb.append("and m.TORTYPE =? \n ");
			list.add(map.get("qinquanleixing"));
		}
		
		if (map.get("yiwuleixing")!=null &&map.get("yiwuleixing").length()!=0) {
			sb.append("and m.DEFOBL =? \n ");
			list.add(map.get("yiwuleixing"));
		}
		
		sb.append(
						") po left join\n" +
						"          dc_code.dc_12315_codedata co\n" + 
						"              on co.codetable='CE16'\n" + 
						"              and po.pentype=co.code\n" + 
						"\n" + 
						"       )\n" + 
						"       select x.name,\n" + 
						"       sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"       sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )shushi,\n" + 
						"       sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )tiaojie,\n" + 
						"       sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"       sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"       sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"       sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)jiabeipeichangjine,\n" + 
						"       sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"       sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)lianshu,\n" + 
						"       sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"       sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"       sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )chufajine,\n" + 
						"       sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"       sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )bianjiajine,\n" + 
						"       sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"       sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"       sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"       sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"       sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"       sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"       sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"       from x group by x.name\n" + 
						"       union all\n" + 
						"       select '上一段时间段数据'as name,\n" + 
						"       sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"       sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )shushi,\n" + 
						"       sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )tiaojie,\n" + 
						"       sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end)jiabeipeichangjine,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"       sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end)lianshu,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end )chufajine,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end )bianjiajine,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"       from x\n" + 
						"       union all\n" + 
						"        select '环比增减（%）'as name,\n" + 
						"       round((case when sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)=0 then 0\n" + 
						"       when sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end)=0 and\n" + 
						"         sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)-\n" + 
						"         sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end))/\n" + 
						"         sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end) end ),4)*100 subnum,\n" + 
						"       round((case when sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )=0\n" + 
						"       and  sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) =0 then 0\n" + 
						"       when sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )-\n" + 
						"         sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ))/\n" + 
						"         sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) end),4)*100 shushi,\n" + 
						"\n" + 
						"       round((case when sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0\n" + 
						"             and  sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0 then 0\n" + 
						"        when sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )-\n" + 
						"          sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ))/\n" + 
						"          sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ) end ),4)*100 tiaojie,\n" + 
						"       round(( case when sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 and\n" + 
						"         sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 then 0\n" + 
						"         when sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 and\n" + 
						"           sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)<>0 then 1 else\n" + 
						"           (sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)-\n" + 
						"           sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end))/\n" + 
						"           sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end) end ),4)*100 qizha,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end )=0 and\n" + 
						"             sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) =0 then 0\n" + 
						"            when sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end )=0\n" + 
						"             and sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end )<>0 then 1\n" + 
						"            else(sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end )-\n" + 
						"            sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end ))/\n" + 
						"            sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end ) end  ),4)*100 zhengyijine,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end)=0\n" + 
						"             and sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)=0 then 0\n" + 
						"             when sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end)=0\n" + 
						"             and sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)<>0 then 1\n" + 
						"             else(sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)-\n" + 
						"             sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end))/\n" + 
						"             sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end) end),4)*100 wanhuijingjisunshi,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end)=0 and\n" + 
						"             sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end) =0 then 0\n" + 
						"            when sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end)=0 and\n" + 
						"            sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)<>0 then 1\n" + 
						"            else (sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)-\n" + 
						"            sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end))/\n" + 
						"            sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end) end),4)*100 jiabeipeichangjine,\n" + 
						"      round( (case when sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)<>0 then 1\n" + 
						"         else(sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end) end),4)*100 jingshenpeichangjine,\n" + 
						"       round((case when sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)=0 then 0\n" + 
						"       when sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end) <> 0 then 1 else\n" + 
						"         (sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)-\n" + 
						"         sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end))/\n" + 
						"         sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end) end),4)*100lianshu,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end )=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end )=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) <>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end )-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end ))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end ) end),4)*100 anzhi,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end)=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end) =0 and\n" + 
						"         sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end) end),4)*100 weifasuode,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end )=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.penam,0) else 0 end ) <>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end ))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end ) end),4)*100 chufajine,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end)=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end)=0 and\n" + 
						"         sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) <>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end)-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end) end),4)*100 moshoujine,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end )=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )<>0 then 1\n" + 
						"         else(sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end ))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end ) end),4)*100 bianjiajine,\n" + 
						"      round( (case when sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0  then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)<>0 then 1\n" + 
						"         else (sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)-\n" + 
						"           sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end))/\n" + 
						"           sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end) end),4)*100 jingjisunshizhi,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)<>0 then 1\n" + 
						"         else (sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)-\n" + 
						"           sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end))/\n" + 
						"           sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end) end),4)*100 daohuiwodian,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end )=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end ))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end ) end),4)*100chudongrenshu,\n" + 
						"      round( (case when sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end)=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end) end),4)*100 chudongcheliang,\n" + 
						"      round( (case when sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end )=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end ))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end ) end),4)*100 shoushangrenshu,\n" + 
						"      round( (case when sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end) end),4)*100 shouhairenshu,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end )=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) =0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end )<>0 then 1\n" + 
						"         else(sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end )-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end ))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end ) end),4)*100 xishengrenshu\n" + 
						"       from x\n" + 
						"   union all\n" + 
						"       select '去年同期数据'as name,\n" + 
						"       sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"       sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )shushi,\n" + 
						"       sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )tiaojie,\n" + 
						"       sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end)jiabeipeichangjine,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"       sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end)lianshu,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end )chufajine,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end )bianjiajine,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"       from x\n" + 
						"       union all\n" + 
						"         select '同比增减（%）'as name,\n" + 
						"       round((case when sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)=0 then 0\n" + 
						"       when sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end)=0 and\n" + 
						"         sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)-\n" + 
						"         sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end))/\n" + 
						"         sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end) end ),4)*100 subnum,\n" + 
						"       round((case when sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )=0\n" + 
						"       and  sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) =0 then 0\n" + 
						"       when sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )-\n" + 
						"         sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ))/\n" + 
						"         sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) end),4)*100 shushi,\n" + 
						"       round((case when sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0\n" + 
						"             and  sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0 then 0\n" + 
						"        when sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )-\n" + 
						"          sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ))/\n" + 
						"          sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ) end ),4)*100 tiaojie,\n" + 
						"       round(( case when sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 and\n" + 
						"         sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 then 0\n" + 
						"         when sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 and\n" + 
						"           sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)<>0 then 1 else\n" + 
						"           (sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)-\n" + 
						"           sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end))/\n" + 
						"           sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end) end ),4)*100 qizha,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end )=0 and\n" + 
						"             sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) =0 then 0\n" + 
						"            when sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end )=0\n" + 
						"             and sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end )<>0 then 1\n" + 
						"            else(sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end )-\n" + 
						"            sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end ))/\n" + 
						"            sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end ) end  ),4)*100zhengyijine,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end)=0\n" + 
						"             and sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)=0 then 0\n" + 
						"             when sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end)=0\n" + 
						"             and sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)<>0 then 1\n" + 
						"             else(sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)-\n" + 
						"             sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end))/\n" + 
						"             sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end) end),4)*100 wanhuijingjisunshi,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end)=0 and\n" + 
						"             sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end) =0 then 0\n" + 
						"            when sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end)=0 and\n" + 
						"            sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)<>0 then 1\n" + 
						"            else (sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)-\n" + 
						"            sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end))/\n" + 
						"            sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end) end),4)*100 jiabeipeichangjine,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)<>0 then 1\n" + 
						"         else(sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end) end),4)*100 jingshenpeichangjine,\n" + 
						"       round((case when sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)=0 then 0\n" + 
						"       when sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end) <> 0 then 1 else\n" + 
						"         (sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)-\n" + 
						"         sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end))/\n" + 
						"         sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end) end),4)*100lianshu,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end )=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end )=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) <>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end )-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end ))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end ) end),4)*100 anzhi,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end)=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end) =0 and\n" + 
						"         sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end) end),4)*100 weifasuode,\n" + 
						"      round( (case when sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end )=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.penam,0) else 0 end ) <>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end ))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end ) end),4)*100 chufajine,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end)=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end)=0 and\n" + 
						"         sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) <>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end)-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end) end),4)*100 moshoujine,\n" + 
						"      round( (case when sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end )=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )<>0 then 1\n" + 
						"         else(sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end ))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end ) end),4)*100 bianjiajine,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0  then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)<>0 then 1\n" + 
						"         else (sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)-\n" + 
						"           sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end))/\n" + 
						"           sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end) end),4)*100 jingjisunshizhi,\n" + 
						"      round( (case when sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)<>0 then 1\n" + 
						"         else (sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)-\n" + 
						"           sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end))/\n" + 
						"           sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end) end),4)*100 daohuiwodian,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end )=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end ))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end ) end),4)*100 chudongrenshu,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end)=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end) end),4)*100 chudongcheliang,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end )=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end ))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end ) end),4)*100 shoushangrenshu,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end) end),4)*100 shouhairenshu,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end )=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) =0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end )<>0 then 1\n" + 
						"         else(sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end )-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end ))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end ) end),4)*100 xishengrenshu\n" + 
						"       from x");
		try {
			res=dao.queryForList(sb.toString(), list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("结案信息申诉举报处罚问题统计报表", "WDY", i == 1 ? "查看报表" : "下载报表", sb.toString(),
				map.get("begintime")+","+map.get("endtime"), req);
		return res;
	}
	public List<Map> banJieBuMen(Map<String, String> map, int i,
			HttpServletRequest req) {
		List list=new ArrayList();
		StringBuffer sb=new StringBuffer();
		List<Map> res=new ArrayList<Map>();
		sb.append(
				"with x as (\n" +
						"        select  co.dep_name as name ,po.* from (\n" + 
						"        select /*+ parallel(8)*/ i.finishtime, --完成时间\n" + 
						"               v.VISITCONFIRM, --回访情况\n" + 
						"               m.MEDIATE_RESULT, --调解结果\n" + 
						"               m.CHEATSIGN， --是否欺诈\n" + 
						"               m.DISAM, --争议金额\n" + 
						"               m.REDECOLOS, --挽回经济损失\n" + 
						"               m.DOUAMEAM, --加倍赔偿金额\n" + 
						"               m.SPIAMEAM, --精神赔偿金额\n" + 
						"               f.PUTONCASE, --是否立案\n" + 
						"               c.CASEVAL, --案值\n" + 
						"               p.illegincome, --违法所得\n" + 
						"               p.penam, --处罚金额\n" + 
						"               p.forfam, --没收金额\n" + 
						"               p.apprcuram, --变价金额\n" + 
						"               c.ECOLOVAL, --经济损失值\n" + 
						"               p.distoriedplace, --捣毁窝点\n" + 
						"               f.executeperson, --出动人数\n" + 
						"               f.executecar, -- 出动车辆\n" + 
						"               h.INJNUM, --受伤人数\n" + 
						"               d.VICTIMNUM, --受害人数\n" + 
						"               h.SACNUM, --牺牲人数\n" + 
						"               nvl(j.PARENT_DEP_CODE,'9999') as PARENT_DEP_CODE ,--处理部门上级代码\n" + 
						"               (case when i.regtime >= to_date(?, 'yyyy-mm-dd')\n" + 
						"                    and i.regtime <=\n" + 
						"               to_date(?, 'yyyy-mm-dd hh24:mi:ss')then 1 else 0 end) flag, \n");
		list.add(map.get("begintime"));
		list.add(map.get("endtime")+" 23:59:59");
		sb.append(
				"(case when i.regtime >= to_date(?, 'yyyy-mm-dd')\n" +
						"                    and i.regtime <=\n" + 
						"               to_date(?, 'yyyy-mm-dd hh24:mi:ss')then 1 else 0 end) tflag, \n");
		list.add(QueryJieAnUtils.getTongBiDate(map.get("begintime")));
		list.add(QueryJieAnUtils.getTongBiDate(map.get("endtime"))+" 23:59:59");
		sb.append(
				"(case when i.regtime >= to_date(?, 'yyyy-mm-dd')\n" +
						"                    and i.regtime <=\n" + 
						"               to_date(?, 'yyyy-mm-dd hh24:mi:ss')then 1 else 0 end) hflag \n");
		if (map.get("hbegintime")!=null&&map.get("hbegintime").length()!=0) {
			list.add(map.get("hbegintime"));
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(map.get("begintime")));
		}
		if (map.get("hendtime")!=null&&map.get("hendtime").length()!=0) {
			list.add(map.get("hendtime")+" 23:59:59");
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(map.get("endtime"))+" 23:59:59");
		}
		sb.append(
				"from dc_cpr_infoware         i,\n" +
				"               DC_CPR_RETURN_VISIT     v,\n" + 
				"               dc_cpr_feedback         f,\n" + 
				"               DC_CPR_MEDIATION        m,\n" + 
				"               dc_cpr_case_info        c,\n" + 
				"               DC_CPR_LEGAL_PUNISHMENT p,\n" + 
				"               DC_CPR_CASE_HANDLE      h,\n" + 
				"               DC_CPR_CONFIRMED_DAMAGE d,\n" + 
				"               dc_cpr_involved_object  o,\n" + 
				"               dc_cpr_involved_main    ma,\n" + 
				"               dc_jg_sys_right_department j\n" + 
				"         where i.infowareid = v.infowareid(+)\n" + 
				"           and i.feedbackid = f.feedbackid\n" + 
				"           and f.mediationid = m.mediationid(+)\n" + 
				"           and f.caseinfoid = c.caseinfoid(+)\n" + 
				"           and c.LEGPUNID = p.legpunid(+)\n" + 
				"           and c.CASEHANDLEID = h.casehandleid(+)\n" + 
				"           and c.CONDAMID = d.condamid(+)\n" + 
				"           and o.invobjid(+) = i.invobjid\n" + 
				"           and i.invmaiid = ma.invmaiid(+)\n" + 
				"           and i.handepcode=j.sys_right_department_id(+) \n");
		if (map.get("dengjibumen")!=null &&map.get("dengjibumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("dengjibumen"),"i.REGDEPCODE"));
		}
		
		if (map.get("chengbanbumen")!=null &&map.get("chengbanbumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("chengbanbumen"),"i.HANDEPCODE"));
		}
		
		if (map.get("wentileixing")!=null &&map.get("wentileixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("wentileixing")," i.APPLBASQUE"));
		}
		
		if (map.get("ketileixing")!=null &&map.get("ketileixing").length()!=0) {
			sb.append(QueryJieAnUtils.UtilOfObjectTypeS(map.get("ketileixing"),"o.invobjtype"));
			sb.append(QueryJieAnUtils.UtilOfObjectTypeB(map.get("ketileixing"),"o.BUSINESSTYPE"));
		}
		
		if (map.get("zhutileixing")!=null &&map.get("zhutileixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("zhutileixing"),"ma.PTTYPE"));
		}
		
		if (map.get("hangyeleixing")!=null &&map.get("hangyeleixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("hangyeleixing"),"ma.TRADETYPE"));
		}
		
		if (map.get("weifazhonglei")!=null &&map.get("weifazhonglei").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("weifazhonglei"),"p.ILLEGACTTYPE"));
		}
		
		if (map.get("xingzhengchufa")!=null &&map.get("xingzhengchufa").length()!=0) {
			sb.append("and p.COMMEATYPE =? \n ");
			list.add(map.get("xingzhengchufa"));
		}
		
		if (map.get("zhixingleibie")!=null &&map.get("zhixingleibie").length()!=0) {
			sb.append("and h.EXESORT =? \n ");
			list.add(map.get("zhixingleibie"));
		}
		
		if (map.get("jieshoufangshi")!=null &&map.get("jieshoufangshi").length()!=0) {
			sb.append("and i.INCFORM =? \n ");
			list.add(map.get("jieshoufangshi"));
		}
		
		if (map.get("infotype")!=null &&map.get("infotype").length()!=0) {
			sb.append("and i.INFTYPE =? \n ");
			list.add(map.get("infotype"));
		}
		
		if (map.get("wangzhanleixing")!=null &&map.get("wangzhanleixing").length()!=0) {
			sb.append("and ma.WEBSITETYPE =? \n ");
			list.add(map.get("wangzhanleixing"));
		}
		
		if (map.get("qinquanleixing")!=null &&map.get("qinquanleixing").length()!=0) {
			sb.append("and m.TORTYPE =? \n ");
			list.add(map.get("qinquanleixing"));
		}
		
		if (map.get("yiwuleixing")!=null &&map.get("yiwuleixing").length()!=0) {
			sb.append("and m.DEFOBL =? \n ");
			list.add(map.get("yiwuleixing"));
		}
		if (map.get("chufazhonglei")!=null &&map.get("chufazhonglei").length()!=0) {
			sb.append("and p.Pentype =? \n ");
			list.add(map.get("chufazhonglei"));
		}
		sb.append(
					") po left join\n" +
					"          dc_dc.dc_jg_sys_right_department co\n" + 
					"              on po.PARENT_DEP_CODE=co.SYS_RIGHT_DEPARTMENT_ID(+)\n" + 
					"\n" + 
					"\n" + 
					"              union all\n" + 
					"       select  '　'||co.dep_name as name ,po.finishtime,\n" + 
					"              po.VISITCONFIRM,\n" + 
					"              po.MEDIATE_RESULT,\n" + 
					"              po.CHEATSIGN，\n" + 
					"              po.DISAM,\n" + 
					"              po.REDECOLOS,\n" + 
					"              po.DOUAMEAM,\n" + 
					"              po.SPIAMEAM,\n" + 
					"              po.PUTONCASE,\n" + 
					"              po.CASEVAL,\n" + 
					"              po.illegincome,\n" + 
					"              po.penam,\n" + 
					"              po.forfam,\n" + 
					"              po.apprcuram,\n" + 
					"              po.ECOLOVAL,\n" + 
					"              po.distoriedplace,\n" + 
					"              po.executeperson,\n" + 
					"              po.executecar,\n" + 
					"              po.INJNUM,\n" + 
					"              po.VICTIMNUM,\n" + 
					"              po.SACNUM,\n" + 
					"              nvl(co.parent_dep_code,'9999') as PARENT_DEP_CODE,\n" + 
					"              po.flag,\n" + 
					"              po.tflag,\n" + 
					"              po.hflag from (\n" + 
					"       select /*+ parallel(8)*/ i.finishtime, --完成时间\n" + 
					"              v.VISITCONFIRM, --回访情况\n" + 
					"              m.MEDIATE_RESULT, --调解结果\n" + 
					"              m.CHEATSIGN， --是否欺诈\n" + 
					"              m.DISAM, --争议金额\n" + 
					"              m.REDECOLOS, --挽回经济损失\n" + 
					"              m.DOUAMEAM, --加倍赔偿金额\n" + 
					"              m.SPIAMEAM, --精神赔偿金额\n" + 
					"              f.PUTONCASE, --是否立案\n" + 
					"              c.CASEVAL, --案值\n" + 
					"              p.illegincome, --违法所得\n" + 
					"              p.penam, --处罚金额\n" + 
					"              p.forfam, --没收金额\n" + 
					"              p.apprcuram, --变价金额\n" + 
					"              c.ECOLOVAL, --经济损失值\n" + 
					"              p.distoriedplace, --捣毁窝点\n" + 
					"              f.executeperson, --出动人数\n" + 
					"              f.executecar, -- 出动车辆\n" + 
					"              h.INJNUM, --受伤人数\n" + 
					"              d.VICTIMNUM, --受害人数\n" + 
					"              h.SACNUM, --牺牲人数\n" + 
					"              i.handepcode  PARENT_DEP_CODE,--处理部门上级代码\n" + 
					"              (case when i.regtime >= to_date(?, 'yyyy-mm-dd')\n" + 
					"                   and i.regtime <=\n" + 
					"              to_date(?, 'yyyy-mm-dd hh24:mi:ss')then 1 else 0 end) flag, \n");
		list.add(map.get("begintime"));
		list.add(map.get("endtime")+" 23:59:59");
		sb.append(
				"(case when i.regtime >= to_date(?, 'yyyy-mm-dd')\n" +
						"                    and i.regtime <=\n" + 
						"               to_date(?, 'yyyy-mm-dd hh24:mi:ss')then 1 else 0 end) tflag, \n");
		list.add(QueryJieAnUtils.getTongBiDate(map.get("begintime")));
		list.add(QueryJieAnUtils.getTongBiDate(map.get("endtime"))+" 23:59:59");
		sb.append(
				"(case when i.regtime >= to_date(?, 'yyyy-mm-dd')\n" +
						"                    and i.regtime <=\n" + 
						"               to_date(?, 'yyyy-mm-dd hh24:mi:ss')then 1 else 0 end) hflag \n");
		if (map.get("hbegintime")!=null&&map.get("hbegintime").length()!=0) {
			list.add(map.get("hbegintime"));
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(map.get("begintime")));
		}
		if (map.get("hendtime")!=null&&map.get("hendtime").length()!=0) {
			list.add(map.get("hendtime")+" 23:59:59");
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(map.get("endtime"))+" 23:59:59");
		}
		sb.append(
					"from dc_cpr_infoware         i,\n" +
					"               DC_CPR_RETURN_VISIT     v,\n" + 
					"               dc_cpr_feedback         f,\n" + 
					"               DC_CPR_MEDIATION        m,\n" + 
					"               dc_cpr_case_info        c,\n" + 
					"               DC_CPR_LEGAL_PUNISHMENT p,\n" + 
					"               DC_CPR_CASE_HANDLE      h,\n" + 
					"               DC_CPR_CONFIRMED_DAMAGE d,\n" + 
					"               dc_cpr_involved_object  o,\n" + 
					"               dc_cpr_involved_main    ma\n" + 
					"\n" + 
					"         where i.infowareid = v.infowareid(+)\n" + 
					"           and i.feedbackid = f.feedbackid\n" + 
					"           and f.mediationid = m.mediationid(+)\n" + 
					"           and f.caseinfoid = c.caseinfoid(+)\n" + 
					"           and c.LEGPUNID = p.legpunid(+)\n" + 
					"           and c.CASEHANDLEID = h.casehandleid(+)\n" + 
					"           and c.CONDAMID = d.condamid(+)\n" + 
					"           and o.invobjid(+) = i.invobjid\n" + 
					"           and i.invmaiid = ma.invmaiid(+) \n");
		if (map.get("dengjibumen")!=null &&map.get("dengjibumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("dengjibumen"),"i.REGDEPCODE"));
		}
		
		if (map.get("chengbanbumen")!=null &&map.get("chengbanbumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("chengbanbumen"),"i.HANDEPCODE"));
		}
		
		if (map.get("wentileixing")!=null &&map.get("wentileixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("wentileixing"),"i.APPLBASQUE"));
		}
		
		if (map.get("ketileixing")!=null &&map.get("ketileixing").length()!=0) {
			sb.append(QueryJieAnUtils.UtilOfObjectTypeS(map.get("ketileixing"),"o.invobjtype"));
			sb.append(QueryJieAnUtils.UtilOfObjectTypeB(map.get("ketileixing"),"o.BUSINESSTYPE"));
		}
		
		if (map.get("zhutileixing")!=null &&map.get("zhutileixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("zhutileixing"),"ma.PTTYPE"));
		}
		
		if (map.get("hangyeleixing")!=null &&map.get("hangyeleixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("hangyeleixing"),"ma.TRADETYPE"));
		}
		
		if (map.get("weifazhonglei")!=null &&map.get("weifazhonglei").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("weifazhonglei"),"p.ILLEGACTTYPE"));
		}
		
		if (map.get("xingzhengchufa")!=null &&map.get("xingzhengchufa").length()!=0) {
			sb.append("and p.COMMEATYPE =? \n ");
			list.add(map.get("xingzhengchufa"));
		}
		
		if (map.get("zhixingleibie")!=null &&map.get("zhixingleibie").length()!=0) {
			sb.append("and h.EXESORT =? \n ");
			list.add(map.get("zhixingleibie"));
		}
		
		if (map.get("jieshoufangshi")!=null &&map.get("jieshoufangshi").length()!=0) {
			sb.append("and i.INCFORM =? \n ");
			list.add(map.get("jieshoufangshi"));
		}
		
		if (map.get("infotype")!=null &&map.get("infotype").length()!=0) {
			sb.append("and i.INFTYPE =? \n ");
			list.add(map.get("infotype"));
		}
		
		if (map.get("wangzhanleixing")!=null &&map.get("wangzhanleixing").length()!=0) {
			sb.append("and ma.WEBSITETYPE =? \n ");
			list.add(map.get("wangzhanleixing"));
		}
		
		if (map.get("qinquanleixing")!=null &&map.get("qinquanleixing").length()!=0) {
			sb.append("and m.TORTYPE =? \n ");
			list.add(map.get("qinquanleixing"));
		}
		
		if (map.get("yiwuleixing")!=null &&map.get("yiwuleixing").length()!=0) {
			sb.append("and m.DEFOBL =? \n ");
			list.add(map.get("yiwuleixing"));
		}
		if (map.get("chufazhonglei")!=null &&map.get("chufazhonglei").length()!=0) {
			sb.append("and p.Pentype =? \n ");
			list.add(map.get("chufazhonglei"));
		}
		sb.append(
				") po left join\n" +
						"          dc_dc.dc_jg_sys_right_department co\n" + 
						"              on po.PARENT_DEP_CODE=co.SYS_RIGHT_DEPARTMENT_ID(+)\n" + 
						"       )\n" + 
						"\n" + 
						"       select x.name,x.PARENT_DEP_CODE,\n" + 
						"       sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"       sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )shushi,\n" + 
						"       sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )tiaojie,\n" + 
						"       sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"       sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"       sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"       sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)jiabeipeichangjine,\n" + 
						"       sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"       sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)lianshu,\n" + 
						"       sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"       sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"       sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )chufajine,\n" + 
						"       sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"       sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )bianjiajine,\n" + 
						"       sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"       sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"       sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"       sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"       sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"       sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"       sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"       from x group by x.name,x.PARENT_DEP_CODE\n" + 
						"\n" + 
						"       union all\n" + 
						"       select '上一段时间段数据'as name,'99999991'PARENT_DEP_CODE,\n" + 
						"       sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end)/2 subnum,\n" + 
						"       sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )/2 shushi,\n" + 
						"       sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )/2 tiaojie,\n" + 
						"       sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end)/2 qizha,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end )/2 zhengyijine,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end)/2 wanhuijingjisunshi,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end)/2 jiabeipeichangjine,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end)/2 jingshenpeichangjine,\n" + 
						"       sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end)/2 lianshu,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end )/2 anzhi,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end)/2 weifasuode,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end )/2 chufajine,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end)/2 moshoujine,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end )/2 bianjiajine,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end)/2 jingjisunshizhi,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end)/2 daohuiwodian,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end )/2chudongrenshu,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end)/2 chudongcheliang,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end )/2 shoushangrenshu,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end)/2 shouhairenshu,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end )/2 xishengrenshu\n" + 
						"       from x\n" + 
						"       union all\n" + 
						"        select '环比增减（%）'as name,'99999992'PARENT_DEP_CODE,\n" + 
						"       round((case when sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)=0 then 0\n" + 
						"       when sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end)=0 and\n" + 
						"         sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)-\n" + 
						"         sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end))/\n" + 
						"         sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end) end ),4)*100 subnum,\n" + 
						"       round((case when sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )=0\n" + 
						"       and  sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) =0 then 0\n" + 
						"       when sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )-\n" + 
						"         sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ))/\n" + 
						"         sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) end),4)*100 shushi,\n" + 
						"\n" + 
						"       round((case when sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0\n" + 
						"             and  sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0 then 0\n" + 
						"        when sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )-\n" + 
						"          sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ))/\n" + 
						"          sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ) end ),4)*100 tiaojie,\n" + 
						"       round(( case when sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 and\n" + 
						"         sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 then 0\n" + 
						"         when sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 and\n" + 
						"           sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)<>0 then 1 else\n" + 
						"           (sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)-\n" + 
						"           sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end))/\n" + 
						"           sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end) end ),4)*100 qizha,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end )=0 and\n" + 
						"             sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) =0 then 0\n" + 
						"            when sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end )=0\n" + 
						"             and sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end )<>0 then 1\n" + 
						"            else(sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end )-\n" + 
						"            sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end ))/\n" + 
						"            sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end ) end  ),4)*100 zhengyijine,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end)=0\n" + 
						"             and sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)=0 then 0\n" + 
						"             when sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end)=0\n" + 
						"             and sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)<>0 then 1\n" + 
						"             else(sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)-\n" + 
						"             sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end))/\n" + 
						"             sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end) end),4)*100 wanhuijingjisunshi,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end)=0 and\n" + 
						"             sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end) =0 then 0\n" + 
						"            when sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end)=0 and\n" + 
						"            sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)<>0 then 1\n" + 
						"            else (sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)-\n" + 
						"            sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end))/\n" + 
						"            sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end) end),4)*100 jiabeipeichangjine,\n" + 
						"      round( (case when sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)<>0 then 1\n" + 
						"         else(sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end) end),4)*100 jingshenpeichangjine,\n" + 
						"       round((case when sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)=0 then 0\n" + 
						"       when sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end) <> 0 then 1 else\n" + 
						"         (sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)-\n" + 
						"         sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end))/\n" + 
						"         sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end) end),4)*100lianshu,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end )=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end )=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) <>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end )-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end ))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end ) end),4)*100 anzhi,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end)=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end) =0 and\n" + 
						"         sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end) end),4)*100 weifasuode,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end )=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.penam,0) else 0 end ) <>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end ))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end ) end),4)*100 chufajine,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end)=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end)=0 and\n" + 
						"         sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) <>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end)-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end) end),4)*100 moshoujine,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end )=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )<>0 then 1\n" + 
						"         else(sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end ))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end ) end),4)*100 bianjiajine,\n" + 
						"      round( (case when sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0  then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)<>0 then 1\n" + 
						"         else (sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)-\n" + 
						"           sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end))/\n" + 
						"           sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end) end),4)*100 jingjisunshizhi,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)<>0 then 1\n" + 
						"         else (sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)-\n" + 
						"           sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end))/\n" + 
						"           sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end) end),4)*100 daohuiwodian,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end )=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end ))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end ) end),4)*100chudongrenshu,\n" + 
						"      round( (case when sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end)=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end) end),4)*100 chudongcheliang,\n" + 
						"      round( (case when sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end )=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end ))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end ) end),4)*100 shoushangrenshu,\n" + 
						"      round( (case when sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end) end),4)*100 shouhairenshu,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end )=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) =0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end )<>0 then 1\n" + 
						"         else(sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end )-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end ))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end ) end),4)*100 xishengrenshu\n" + 
						"       from x\n" + 
						"   union all\n" + 
						"       select '去年同期数据'as name,'99999993'PARENT_DEP_CODE,\n" + 
						"       sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end)/2 subnum,\n" + 
						"       sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )/2 shushi,\n" + 
						"       sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )/2 tiaojie,\n" + 
						"       sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end)/2 qizha,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end )/2 zhengyijine,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end)/2 wanhuijingjisunshi,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end)/2 jiabeipeichangjine,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end)/2 jingshenpeichangjine,\n" + 
						"       sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end)/2 lianshu,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end )/2 anzhi,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end)/2 weifasuode,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end )/2 chufajine,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end)/2 moshoujine,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end )/2 bianjiajine,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end)/2 jingjisunshizhi,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end)/2 daohuiwodian,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end )/2 chudongrenshu,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end)/2 chudongcheliang,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end )/2 shoushangrenshu,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end)/2 shouhairenshu,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end )/2 xishengrenshu\n" + 
						"       from x\n" + 
						"       union all\n" + 
						"         select '同比增减（%）'as name,'99999994'PARENT_DEP_CODE,\n" + 
						"       round((case when sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)=0 then 0\n" + 
						"       when sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end)=0 and\n" + 
						"         sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)-\n" + 
						"         sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end))/\n" + 
						"         sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end) end ),4)*100 subnum,\n" + 
						"       round((case when sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )=0\n" + 
						"       and  sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) =0 then 0\n" + 
						"       when sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )-\n" + 
						"         sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ))/\n" + 
						"         sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) end),4)*100 shushi,\n" + 
						"       round((case when sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0\n" + 
						"             and  sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0 then 0\n" + 
						"        when sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )-\n" + 
						"          sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ))/\n" + 
						"          sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ) end ),4)*100 tiaojie,\n" + 
						"       round(( case when sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 and\n" + 
						"         sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 then 0\n" + 
						"         when sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 and\n" + 
						"           sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)<>0 then 1 else\n" + 
						"           (sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)-\n" + 
						"           sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end))/\n" + 
						"           sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end) end ),4)*100 qizha,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end )=0 and\n" + 
						"             sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) =0 then 0\n" + 
						"            when sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end )=0\n" + 
						"             and sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end )<>0 then 1\n" + 
						"            else(sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end )-\n" + 
						"            sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end ))/\n" + 
						"            sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end ) end  ),4)*100zhengyijine,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end)=0\n" + 
						"             and sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)=0 then 0\n" + 
						"             when sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end)=0\n" + 
						"             and sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)<>0 then 1\n" + 
						"             else(sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)-\n" + 
						"             sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end))/\n" + 
						"             sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end) end),4)*100 wanhuijingjisunshi,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end)=0 and\n" + 
						"             sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end) =0 then 0\n" + 
						"            when sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end)=0 and\n" + 
						"            sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)<>0 then 1\n" + 
						"            else (sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)-\n" + 
						"            sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end))/\n" + 
						"            sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end) end),4)*100 jiabeipeichangjine,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)<>0 then 1\n" + 
						"         else(sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end) end),4)*100 jingshenpeichangjine,\n" + 
						"       round((case when sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)=0 then 0\n" + 
						"       when sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end) <> 0 then 1 else\n" + 
						"         (sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)-\n" + 
						"         sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end))/\n" + 
						"         sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end) end),4)*100lianshu,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end )=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end )=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) <>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end )-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end ))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end ) end),4)*100 anzhi,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end)=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end) =0 and\n" + 
						"         sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end) end),4)*100 weifasuode,\n" + 
						"      round( (case when sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end )=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.penam,0) else 0 end ) <>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end ))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end ) end),4)*100 chufajine,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end)=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end)=0 and\n" + 
						"         sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) <>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end)-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end) end),4)*100 moshoujine,\n" + 
						"      round( (case when sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end )=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )<>0 then 1\n" + 
						"         else(sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end ))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end ) end),4)*100 bianjiajine,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0  then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)<>0 then 1\n" + 
						"         else (sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)-\n" + 
						"           sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end))/\n" + 
						"           sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end) end),4)*100 jingjisunshizhi,\n" + 
						"      round( (case when sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)<>0 then 1\n" + 
						"         else (sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)-\n" + 
						"           sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end))/\n" + 
						"           sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end) end),4)*100 daohuiwodian,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end )=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end ))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end ) end),4)*100 chudongrenshu,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end)=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end) end),4)*100 chudongcheliang,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end )=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end ))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end ) end),4)*100 shoushangrenshu,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end) end),4)*100 shouhairenshu,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end )=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) =0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end )<>0 then 1\n" + 
						"         else(sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end )-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end ))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end ) end),4)*100 xishengrenshu\n" + 
						"       from x\n" + 
						"        order by PARENT_DEP_CODE asc ,name desc");
		try {
			res=dao.queryForList(sb.toString(), list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("结案信息申诉举报办结部门统计报表", "WDY", i == 1 ? "查看报表" : "下载报表", sb.toString(),
				map.get("begintime")+","+map.get("endtime"), req);
		return res;
	}
	public List<Map> wenTiLeiXing(Map<String, String> map, int i,
			HttpServletRequest req) {
		List list=new ArrayList();
		StringBuffer sb=new StringBuffer();
		List<Map> res=new ArrayList<Map>();
		sb.append(
				"with x as (\n" +
						"       select j.name as name ,p.* from (\n" + 
						"        select /*+ parallel(8)*/ i.finishtime, --完成时间\n" + 
						"               v.VISITCONFIRM, --回访情况\n" + 
						"               m.MEDIATE_RESULT, --调解结果\n" + 
						"               m.CHEATSIGN， --是否欺诈\n" + 
						"               m.DISAM, --争议金额\n" + 
						"               m.REDECOLOS, --挽回经济损失\n" + 
						"               m.DOUAMEAM, --加倍赔偿金额\n" + 
						"               m.SPIAMEAM, --精神赔偿金额\n" + 
						"               f.PUTONCASE, --是否立案\n" + 
						"               c.CASEVAL, --案值\n" + 
						"               p.illegincome, --违法所得\n" + 
						"               p.penam, --处罚金额\n" + 
						"               p.forfam, --没收金额\n" + 
						"               p.apprcuram, --变价金额\n" + 
						"               c.ECOLOVAL, --经济损失值\n" + 
						"               p.distoriedplace, --捣毁窝点\n" + 
						"               f.executeperson, --出动人数\n" + 
						"               f.executecar, -- 出动车辆\n" + 
						"               h.INJNUM, --受伤人数\n" + 
						"               d.VICTIMNUM, --受害人数\n" + 
						"               h.SACNUM, --牺牲人数\n" + 
						"\n" + 
						"               nvl(j.parentcode,'9999') as code,\n" + 
						"               (case when i.regtime >= to_date(?, 'yyyy-mm-dd')\n" + 
						"                    and i.regtime <=\n" + 
						"               to_date(?, 'yyyy-mm-dd hh24:mi:ss')then 1 else 0 end) flag, \n");
		list.add(map.get("begintime"));
		list.add(map.get("endtime")+" 23:59:59");
		sb.append(
				"(case when i.regtime >= to_date(?, 'yyyy-mm-dd')\n" +
						"                    and i.regtime <=\n" + 
						"               to_date(?, 'yyyy-mm-dd hh24:mi:ss')then 1 else 0 end) tflag, \n");
		list.add(QueryJieAnUtils.getTongBiDate(map.get("begintime")));
		list.add(QueryJieAnUtils.getTongBiDate(map.get("endtime"))+" 23:59:59");
		sb.append(
				"(case when i.regtime >= to_date(?, 'yyyy-mm-dd')\n" +
						"                    and i.regtime <=\n" + 
						"               to_date(?, 'yyyy-mm-dd hh24:mi:ss')then 1 else 0 end) hflag \n");
		if (map.get("hbegintime")!=null&&map.get("hbegintime").length()!=0) {
			list.add(map.get("hbegintime"));
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(map.get("begintime")));
		}
		if (map.get("hendtime")!=null&&map.get("hendtime").length()!=0) {
			list.add(map.get("hendtime")+" 23:59:59");
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(map.get("endtime"))+" 23:59:59");
		}
		sb.append(
				"from dc_cpr_infoware         i,\n" +
						"              DC_CPR_RETURN_VISIT     v,\n" + 
						"              dc_cpr_feedback         f,\n" + 
						"              DC_CPR_MEDIATION        m,\n" + 
						"              dc_cpr_case_info        c,\n" + 
						"              DC_CPR_LEGAL_PUNISHMENT p,\n" + 
						"              DC_CPR_CASE_HANDLE      h,\n" + 
						"              DC_CPR_CONFIRMED_DAMAGE d,\n" + 
						"              dc_cpr_involved_object  o,\n" + 
						"              dc_cpr_involved_main    ma,\n" + 
						"              (select j.code code ,--j.parentcode as parentcode\n" + 
						"              decode(length(j.parentcode),1,j.code,j.parentcode) as parentcode\n" + 
						"               from dc_code.dc_12315_codedata j where j.codetable='CH27') j\n" + 
						"        where i.infowareid = v.infowareid(+)\n" + 
						"          and i.feedbackid = f.feedbackid\n" + 
						"          and f.mediationid = m.mediationid(+)\n" + 
						"          and f.caseinfoid = c.caseinfoid(+) \n" + 
						"          and c.LEGPUNID = p.legpunid(+)\n" + 
						"          and c.CASEHANDLEID = h.casehandleid(+)\n" + 
						"          and c.CONDAMID = d.condamid(+)\n" + 
						"          and o.invobjid(+) = i.invobjid\n" + 
						"          and i.invmaiid = ma.invmaiid(+)\n" + 
						"          and i.APPLBASQUE=j.code(+)\n" );
		if (map.get("dengjibumen")!=null &&map.get("dengjibumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("dengjibumen"),"i.REGDEPCODE"));
		}
		
		if (map.get("chengbanbumen")!=null &&map.get("chengbanbumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("chengbanbumen"),"i.HANDEPCODE"));
		}
		
		if (map.get("ketileixing")!=null &&map.get("ketileixing").length()!=0) {
			sb.append(QueryJieAnUtils.UtilOfObjectTypeS(map.get("ketileixing"),"o.invobjtype"));
			sb.append(QueryJieAnUtils.UtilOfObjectTypeB(map.get("ketileixing"),"o.BUSINESSTYPE"));
		}
		
		if (map.get("zhutileixing")!=null &&map.get("zhutileixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("zhutileixing"),"ma.PTTYPE"));
		}
		
		if (map.get("hangyeleixing")!=null &&map.get("hangyeleixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("hangyeleixing"),"ma.TRADETYPE"));
		}
		
		if (map.get("weifazhonglei")!=null &&map.get("weifazhonglei").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("weifazhonglei"),"p.ILLEGACTTYPE"));
		}
		
		if (map.get("xingzhengchufa")!=null &&map.get("xingzhengchufa").length()!=0) {
			sb.append("and p.COMMEATYPE =? \n ");
			list.add(map.get("xingzhengchufa"));
		}
		
		if (map.get("zhixingleibie")!=null &&map.get("zhixingleibie").length()!=0) {
			sb.append("and h.EXESORT =? \n ");
			list.add(map.get("zhixingleibie"));
		}
		
		if (map.get("jieshoufangshi")!=null &&map.get("jieshoufangshi").length()!=0) {
			sb.append("and i.INCFORM =? \n ");
			list.add(map.get("jieshoufangshi"));
		}
		
		if (map.get("infotype")!=null &&map.get("infotype").length()!=0) {
			sb.append("and i.INFTYPE =? \n ");
			list.add(map.get("infotype"));
		}
		
		if (map.get("wangzhanleixing")!=null &&map.get("wangzhanleixing").length()!=0) {
			sb.append("and ma.WEBSITETYPE =? \n ");
			list.add(map.get("wangzhanleixing"));
		}
		
		if (map.get("qinquanleixing")!=null &&map.get("qinquanleixing").length()!=0) {
			sb.append("and m.TORTYPE =? \n ");
			list.add(map.get("qinquanleixing"));
		}
		
		if (map.get("yiwuleixing")!=null &&map.get("yiwuleixing").length()!=0) {
			sb.append("and m.DEFOBL =? \n ");
			list.add(map.get("yiwuleixing"));
		}
		if (map.get("chufazhonglei")!=null &&map.get("chufazhonglei").length()!=0) {
			sb.append("and p.Pentype =? \n ");
			list.add(map.get("chufazhonglei"));
		}
		sb.append(
				") p,\n" +
						"           dc_code.dc_12315_codedata j\n" + 
						"           where  j.codetable(+)='CH27'\n" + 
						"           and j.code(+)=p.code\n" + 
						"\n" + 
						"\n" + 
						"           union all\n" + 
						"           select j.name as name ,p.* from (\n" + 
						"            select /*+ parallel(8)*/ i.finishtime, --完成时间\n" + 
						"               v.VISITCONFIRM, --回访情况\n" + 
						"               m.MEDIATE_RESULT, --调解结果\n" + 
						"               m.CHEATSIGN， --是否欺诈\n" + 
						"               m.DISAM, --争议金额\n" + 
						"               m.REDECOLOS, --挽回经济损失\n" + 
						"               m.DOUAMEAM, --加倍赔偿金额\n" + 
						"               m.SPIAMEAM, --精神赔偿金额\n" + 
						"               f.PUTONCASE, --是否立案\n" + 
						"               c.CASEVAL, --案值\n" + 
						"               p.illegincome, --违法所得\n" + 
						"               p.penam, --处罚金额\n" + 
						"               p.forfam, --没收金额\n" + 
						"               p.apprcuram, --变价金额\n" + 
						"               c.ECOLOVAL, --经济损失值\n" + 
						"               p.distoriedplace, --捣毁窝点\n" + 
						"               f.executeperson, --出动人数\n" + 
						"               f.executecar, -- 出动车辆\n" + 
						"               h.INJNUM, --受伤人数\n" + 
						"               d.VICTIMNUM, --受害人数\n" + 
						"               h.SACNUM, --牺牲人数\n" + 
						"               j.code ,\n" + 
						"               (case when i.regtime >= to_date(?, 'yyyy-mm-dd')\n" + 
						"                    and i.regtime <=\n" + 
						"               to_date(?, 'yyyy-mm-dd hh24:mi:ss')then 1 else 0 end) flag, \n");
		list.add(map.get("begintime"));
		list.add(map.get("endtime")+" 23:59:59");
		sb.append(
				"(case when i.regtime >= to_date(?, 'yyyy-mm-dd')\n" +
						"                    and i.regtime <=\n" + 
						"               to_date(?, 'yyyy-mm-dd hh24:mi:ss')then 1 else 0 end) tflag, \n");
		list.add(QueryJieAnUtils.getTongBiDate(map.get("begintime")));
		list.add(QueryJieAnUtils.getTongBiDate(map.get("endtime"))+" 23:59:59");
		sb.append(
				"(case when i.regtime >= to_date(?, 'yyyy-mm-dd')\n" +
						"                    and i.regtime <=\n" + 
						"               to_date(?, 'yyyy-mm-dd hh24:mi:ss')then 1 else 0 end) hflag \n");
		if (map.get("hbegintime")!=null&&map.get("hbegintime").length()!=0) {
			list.add(map.get("hbegintime"));
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(map.get("begintime")));
		}
		if (map.get("hendtime")!=null&&map.get("hendtime").length()!=0) {
			list.add(map.get("hendtime")+" 23:59:59");
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(map.get("endtime"))+" 23:59:59");
		}
		sb.append(
				"from dc_cpr_infoware         i,\n" +
						"              DC_CPR_RETURN_VISIT     v,\n" + 
						"              dc_cpr_feedback         f,\n" + 
						"              DC_CPR_MEDIATION        m,\n" + 
						"              dc_cpr_case_info        c,\n" + 
						"              DC_CPR_LEGAL_PUNISHMENT p,\n" + 
						"              DC_CPR_CASE_HANDLE      h,\n" + 
						"              DC_CPR_CONFIRMED_DAMAGE d,\n" + 
						"              dc_cpr_involved_object  o,\n" + 
						"              dc_cpr_involved_main    ma,\n" + 
						"              (select j.code code ,j.name name from dc_code.dc_12315_codedata j where codetable='CH27'\n" + 
						"             -- and length(j.parentcode)<>1\n" + 
						"              ) j\n" + 
						"        where i.infowareid = v.infowareid(+)\n" + 
						"          and i.feedbackid = f.feedbackid\n" + 
						"          and f.mediationid = m.mediationid(+)\n" + 
						"          and f.caseinfoid = c.caseinfoid(+)\n" + 
						"          and c.LEGPUNID = p.legpunid(+)\n" + 
						"          and c.CASEHANDLEID = h.casehandleid(+)\n" + 
						"          and c.CONDAMID = d.condamid(+)\n" + 
						"          and o.invobjid(+) = i.invobjid\n" + 
						"          and i.invmaiid = ma.invmaiid(+)\n" + 
						"          and i.APPLBASQUE=j.code  \n");
		if (map.get("dengjibumen")!=null &&map.get("dengjibumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("dengjibumen"),"i.REGDEPCODE"));
		}
		
		if (map.get("chengbanbumen")!=null &&map.get("chengbanbumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("chengbanbumen"),"i.HANDEPCODE"));
		}
		
		if (map.get("ketileixing")!=null &&map.get("ketileixing").length()!=0) {
			sb.append(QueryJieAnUtils.UtilOfObjectTypeS(map.get("ketileixing"),"o.invobjtype"));
			sb.append(QueryJieAnUtils.UtilOfObjectTypeB(map.get("ketileixing"),"o.BUSINESSTYPE"));
		}
		
		if (map.get("zhutileixing")!=null &&map.get("zhutileixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("zhutileixing"),"ma.PTTYPE"));
		}
		
		if (map.get("hangyeleixing")!=null &&map.get("hangyeleixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("hangyeleixing"),"ma.TRADETYPE"));
		}
		
		if (map.get("weifazhonglei")!=null &&map.get("weifazhonglei").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("weifazhonglei"),"p.ILLEGACTTYPE"));
		}
		
		if (map.get("xingzhengchufa")!=null &&map.get("xingzhengchufa").length()!=0) {
			sb.append("and p.COMMEATYPE =? \n ");
			list.add(map.get("xingzhengchufa"));
		}
		
		if (map.get("zhixingleibie")!=null &&map.get("zhixingleibie").length()!=0) {
			sb.append("and h.EXESORT =? \n ");
			list.add(map.get("zhixingleibie"));
		}
		
		if (map.get("jieshoufangshi")!=null &&map.get("jieshoufangshi").length()!=0) {
			sb.append("and i.INCFORM =? \n ");
			list.add(map.get("jieshoufangshi"));
		}
		
		if (map.get("infotype")!=null &&map.get("infotype").length()!=0) {
			sb.append("and i.INFTYPE =? \n ");
			list.add(map.get("infotype"));
		}
		
		if (map.get("wangzhanleixing")!=null &&map.get("wangzhanleixing").length()!=0) {
			sb.append("and ma.WEBSITETYPE =? \n ");
			list.add(map.get("wangzhanleixing"));
		}
		
		if (map.get("qinquanleixing")!=null &&map.get("qinquanleixing").length()!=0) {
			sb.append("and m.TORTYPE =? \n ");
			list.add(map.get("qinquanleixing"));
		}
		
		if (map.get("yiwuleixing")!=null &&map.get("yiwuleixing").length()!=0) {
			sb.append("and m.DEFOBL =? \n ");
			list.add(map.get("yiwuleixing"));
		}
		if (map.get("chufazhonglei")!=null &&map.get("chufazhonglei").length()!=0) {
			sb.append("and p.Pentype =? \n ");
			list.add(map.get("chufazhonglei"));
		}
		sb.append(
				") p,\n" +
						"           (select '　'||j.name as name ,j.code  as code\n" + 
						"           from dc_code.dc_12315_codedata j\n" + 
						"           where j.codetable='CH27'\n" + 
						"           ) j\n" + 
						"           where p.code=j.code(+)");
		sb.append(
				")\n" +
						"       select x.name,x.code,\n" + 
						"       sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"       sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )shushi,\n" + 
						"       sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )tiaojie,\n" + 
						"       sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"       sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"       sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"       sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)jiabeipeichangjine,\n" + 
						"       sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"       sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)lianshu,\n" + 
						"       sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"       sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"       sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )chufajine,\n" + 
						"       sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"       sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )bianjiajine,\n" + 
						"       sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"       sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"       sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"       sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"       sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"       sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"       sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"       from x group by x.name,x.code\n" + 
						"\n" + 
						"\n" + 
						"       union all\n" + 
						"       select '上一段时间段数据'as name,'999901' as code,\n" + 
						"       sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end)/2 subnum,\n" + 
						"       sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )/2 shushi,\n" + 
						"       sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )/2 tiaojie,\n" + 
						"       sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end)/2 qizha,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end )/2 zhengyijine,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end)/2 wanhuijingjisunshi,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end)/2 jiabeipeichangjine,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end)/2 jingshenpeichangjine,\n" + 
						"       sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end)/2 lianshu,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end )/2 anzhi,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end)/2 weifasuode,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end )/2 chufajine,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end)/2 moshoujine,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end )/2 bianjiajine,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end)/2 jingjisunshizhi,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end)/2 daohuiwodian,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end )/2chudongrenshu,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end)/2 chudongcheliang,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end )/2 shoushangrenshu,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end)/2 shouhairenshu,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end )/2 xishengrenshu\n" + 
						"       from x\n" + 
						"       union all\n" + 
						"        select '环比增减（%）'as name,'999902' as code,\n" + 
						"       round((case when sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)=0 then 0\n" + 
						"       when sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end)=0 and\n" + 
						"         sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)-\n" + 
						"         sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end))/\n" + 
						"         sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end) end ),4)*100 subnum,\n" + 
						"       round((case when sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )=0\n" + 
						"       and  sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) =0 then 0\n" + 
						"       when sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )-\n" + 
						"         sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ))/\n" + 
						"         sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) end),4)*100 shushi,\n" + 
						"\n" + 
						"       round((case when sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0\n" + 
						"             and  sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0 then 0\n" + 
						"        when sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )-\n" + 
						"          sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ))/\n" + 
						"          sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ) end ),4)*100 tiaojie,\n" + 
						"       round(( case when sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 and\n" + 
						"         sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 then 0\n" + 
						"         when sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 and\n" + 
						"           sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)<>0 then 1 else\n" + 
						"           (sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)-\n" + 
						"           sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end))/\n" + 
						"           sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end) end ),4)*100 qizha,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end )=0 and\n" + 
						"             sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) =0 then 0\n" + 
						"            when sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end )=0\n" + 
						"             and sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end )<>0 then 1\n" + 
						"            else(sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end )-\n" + 
						"            sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end ))/\n" + 
						"            sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end ) end  ),4)*100 zhengyijine,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end)=0\n" + 
						"             and sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)=0 then 0\n" + 
						"             when sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end)=0\n" + 
						"             and sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)<>0 then 1\n" + 
						"             else(sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)-\n" + 
						"             sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end))/\n" + 
						"             sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end) end),4)*100 wanhuijingjisunshi,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end)=0 and\n" + 
						"             sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end) =0 then 0\n" + 
						"            when sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end)=0 and\n" + 
						"            sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)<>0 then 1\n" + 
						"            else (sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)-\n" + 
						"            sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end))/\n" + 
						"            sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end) end),4)*100 jiabeipeichangjine,\n" + 
						"      round( (case when sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)<>0 then 1\n" + 
						"         else(sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end) end),4)*100 jingshenpeichangjine,\n" + 
						"       round((case when sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)=0 then 0\n" + 
						"       when sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end) <> 0 then 1 else\n" + 
						"         (sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)-\n" + 
						"         sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end))/\n" + 
						"         sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end) end),4)*100lianshu,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end )=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end )=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) <>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end )-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end ))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end ) end),4)*100 anzhi,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end)=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end) =0 and\n" + 
						"         sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end) end),4)*100 weifasuode,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end )=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.penam,0) else 0 end ) <>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end ))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end ) end),4)*100 chufajine,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end)=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end)=0 and\n" + 
						"         sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) <>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end)-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end) end),4)*100 moshoujine,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end )=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )<>0 then 1\n" + 
						"         else(sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end ))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end ) end),4)*100 bianjiajine,\n" + 
						"      round( (case when sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0  then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)<>0 then 1\n" + 
						"         else (sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)-\n" + 
						"           sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end))/\n" + 
						"           sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end) end),4)*100 jingjisunshizhi,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)<>0 then 1\n" + 
						"         else (sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)-\n" + 
						"           sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end))/\n" + 
						"           sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end) end),4)*100 daohuiwodian,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end )=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end ))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end ) end),4)*100chudongrenshu,\n" + 
						"      round( (case when sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end)=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end) end),4)*100 chudongcheliang,\n" + 
						"      round( (case when sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end )=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end ))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end ) end),4)*100 shoushangrenshu,\n" + 
						"      round( (case when sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end) end),4)*100 shouhairenshu,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end )=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) =0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end )<>0 then 1\n" + 
						"         else(sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end )-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end ))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end ) end),4)*100 xishengrenshu\n" + 
						"       from x\n" + 
						"   union all\n" + 
						"       select '去年同期数据'as name,'999903' as code,\n" + 
						"       sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end)/2 subnum,\n" + 
						"       sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )/2 shushi,\n" + 
						"       sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )/2 tiaojie,\n" + 
						"       sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end)/2 qizha,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end )/2 zhengyijine,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end)/2 wanhuijingjisunshi,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end)/2 jiabeipeichangjine,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end)/2 jingshenpeichangjine,\n" + 
						"       sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end)/2 lianshu,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end )/2 anzhi,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end)/2 weifasuode,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end )/2 chufajine,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end)/2 moshoujine,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end )/2 bianjiajine,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end)/2 jingjisunshizhi,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end)/2 daohuiwodian,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end )/2 chudongrenshu,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end)/2 chudongcheliang,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end )/2 shoushangrenshu,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end)/2 shouhairenshu,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end )/2 xishengrenshu\n" + 
						"       from x\n" + 
						"       union all\n" + 
						"         select '同比增减（%）'as name,'999904' as code,\n" + 
						"       round((case when sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)=0 then 0\n" + 
						"       when sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end)=0 and\n" + 
						"         sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)-\n" + 
						"         sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end))/\n" + 
						"         sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end) end ),4)*100 subnum,\n" + 
						"       round((case when sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )=0\n" + 
						"       and  sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) =0 then 0\n" + 
						"       when sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )-\n" + 
						"         sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ))/\n" + 
						"         sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) end),4)*100 shushi,\n" + 
						"       round((case when sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0\n" + 
						"             and  sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0 then 0\n" + 
						"        when sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )-\n" + 
						"          sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ))/\n" + 
						"          sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ) end ),4)*100 tiaojie,\n" + 
						"       round(( case when sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 and\n" + 
						"         sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 then 0\n" + 
						"         when sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 and\n" + 
						"           sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)<>0 then 1 else\n" + 
						"           (sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)-\n" + 
						"           sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end))/\n" + 
						"           sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end) end ),4)*100 qizha,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end )=0 and\n" + 
						"             sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) =0 then 0\n" + 
						"            when sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end )=0\n" + 
						"             and sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end )<>0 then 1\n" + 
						"            else(sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end )-\n" + 
						"            sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end ))/\n" + 
						"            sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end ) end  ),4)*100zhengyijine,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end)=0\n" + 
						"             and sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)=0 then 0\n" + 
						"             when sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end)=0\n" + 
						"             and sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)<>0 then 1\n" + 
						"             else(sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)-\n" + 
						"             sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end))/\n" + 
						"             sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end) end),4)*100 wanhuijingjisunshi,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end)=0 and\n" + 
						"             sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end) =0 then 0\n" + 
						"            when sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end)=0 and\n" + 
						"            sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)<>0 then 1\n" + 
						"            else (sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)-\n" + 
						"            sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end))/\n" + 
						"            sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end) end),4)*100 jiabeipeichangjine,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)<>0 then 1\n" + 
						"         else(sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end) end),4)*100 jingshenpeichangjine,\n" + 
						"       round((case when sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)=0 then 0\n" + 
						"       when sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end) <> 0 then 1 else\n" + 
						"         (sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)-\n" + 
						"         sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end))/\n" + 
						"         sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end) end),4)*100lianshu,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end )=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end )=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) <>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end )-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end ))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end ) end),4)*100 anzhi,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end)=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end) =0 and\n" + 
						"         sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end) end),4)*100 weifasuode,\n" + 
						"      round( (case when sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end )=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.penam,0) else 0 end ) <>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end ))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end ) end),4)*100 chufajine,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end)=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end)=0 and\n" + 
						"         sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) <>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end)-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end) end),4)*100 moshoujine,\n" + 
						"      round( (case when sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end )=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )<>0 then 1\n" + 
						"         else(sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end ))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end ) end),4)*100 bianjiajine,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0  then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)<>0 then 1\n" + 
						"         else (sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)-\n" + 
						"           sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end))/\n" + 
						"           sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end) end),4)*100 jingjisunshizhi,\n" + 
						"      round( (case when sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)<>0 then 1\n" + 
						"         else (sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)-\n" + 
						"           sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end))/\n" + 
						"           sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end) end),4)*100 daohuiwodian,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end )=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end ))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end ) end),4)*100 chudongrenshu,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end)=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end) end),4)*100 chudongcheliang,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end )=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end ))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end ) end),4)*100 shoushangrenshu,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end) end),4)*100 shouhairenshu,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end )=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) =0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end )<>0 then 1\n" + 
						"         else(sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end )-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end ))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end ) end),4)*100 xishengrenshu\n" + 
						"       from x\n" + 
						"         order by code asc,name desc");
		try {
			res=dao.queryForList(sb.toString(), list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("结案信息申诉举报基本问题统计报表", "WDY", i == 1 ? "查看报表" : "下载报表", sb.toString(),
				map.get("begintime")+","+map.get("endtime"), req);
		return res;
	}
	public List<Map> qinQuanLeiXing(Map<String, String> map, int i,
			HttpServletRequest req) {
		List list=new ArrayList();
		StringBuffer sb=new StringBuffer();
		List<Map> res=new ArrayList<Map>();
		sb.append(
				"with x as (\n" +
						"\n" + 
						"        select i.finishtime, --完成时间\n" + 
						"               v.VISITCONFIRM, --回访情况\n" + 
						"               m.MEDIATE_RESULT, --调解结果\n" + 
						"               m.CHEATSIGN， --是否欺诈\n" + 
						"               m.DISAM, --争议金额\n" + 
						"               m.REDECOLOS, --挽回经济损失\n" + 
						"               m.DOUAMEAM, --加倍赔偿金额\n" + 
						"               m.SPIAMEAM, --精神赔偿金额\n" + 
						"               f.PUTONCASE, --是否立案\n" + 
						"               c.CASEVAL, --案值\n" + 
						"               p.illegincome, --违法所得\n" + 
						"               p.penam, --处罚金额\n" + 
						"               p.forfam, --没收金额\n" + 
						"               p.apprcuram, --变价金额\n" + 
						"               c.ECOLOVAL, --经济损失值\n" + 
						"               p.distoriedplace, --捣毁窝点\n" + 
						"               f.executeperson, --出动人数\n" + 
						"               f.executecar, -- 出动车辆\n" + 
						"               h.INJNUM, --受伤人数\n" + 
						"               d.VICTIMNUM, --受害人数\n" + 
						"               h.SACNUM, --牺牲人数\n" + 
						"               nvl(m.TORTYPE,'999') as code,\n" + 
						"               j.name as name,\n" + 
						"               (case when i.regtime >= to_date(?, 'yyyy-mm-dd')\n" + 
						"                    and i.regtime <=\n" + 
						"               to_date(?, 'yyyy-mm-dd hh24:mi:ss')then 1 else 0 end) flag, \n");
		list.add(map.get("begintime"));
		list.add(map.get("endtime")+" 23:59:59");
		sb.append(
				"(case when i.regtime >= to_date(?, 'yyyy-mm-dd')\n" +
						"                    and i.regtime <=\n" + 
						"               to_date(?, 'yyyy-mm-dd hh24:mi:ss')then 1 else 0 end) tflag, \n");
		list.add(QueryJieAnUtils.getTongBiDate(map.get("begintime")));
		list.add(QueryJieAnUtils.getTongBiDate(map.get("endtime"))+" 23:59:59");
		sb.append(
				"(case when i.regtime >= to_date(?, 'yyyy-mm-dd')\n" +
						"                    and i.regtime <=\n" + 
						"               to_date(?, 'yyyy-mm-dd hh24:mi:ss')then 1 else 0 end) hflag \n");
		if (map.get("hbegintime")!=null&&map.get("hbegintime").length()!=0) {
			list.add(map.get("hbegintime"));
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(map.get("begintime")));
		}
		if (map.get("hendtime")!=null&&map.get("hendtime").length()!=0) {
			list.add(map.get("hendtime")+" 23:59:59");
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(map.get("endtime"))+" 23:59:59");
		}
		sb.append(
				"from dc_cpr_infoware         i,\n" +
						"               DC_CPR_RETURN_VISIT     v,\n" + 
						"               dc_cpr_feedback         f,\n" + 
						"               DC_CPR_MEDIATION        m,\n" + 
						"               dc_cpr_case_info        c,\n" + 
						"               DC_CPR_LEGAL_PUNISHMENT p,\n" + 
						"               DC_CPR_CASE_HANDLE      h,\n" + 
						"               DC_CPR_CONFIRMED_DAMAGE d,\n" + 
						"               dc_cpr_involved_object  o,\n" + 
						"               dc_cpr_involved_main    ma,\n" + 
						"               (select j.code code ,--j.parentcode as parentcode\n" + 
						"              j.name as name\n" + 
						"                from dc_code.dc_12315_codedata j where j.codetable='CH52') j\n" + 
						"         where i.infowareid = v.infowareid(+)\n" + 
						"           and i.feedbackid = f.feedbackid\n" + 
						"           and f.mediationid = m.mediationid\n" + 
						"           and f.caseinfoid = c.caseinfoid(+)\n" + 
						"           and c.LEGPUNID = p.legpunid(+)\n" + 
						"           and c.CASEHANDLEID = h.casehandleid(+)\n" + 
						"           and c.CONDAMID = d.condamid(+)\n" + 
						"           and o.invobjid(+) = i.invobjid\n" + 
						"           and i.invmaiid = ma.invmaiid(+)\n" + 
						"           and m.TORTYPE=j.code(+) \n");
		if (map.get("dengjibumen")!=null &&map.get("dengjibumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("dengjibumen"),"i.REGDEPCODE"));
		}
		
		if (map.get("chengbanbumen")!=null &&map.get("chengbanbumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("chengbanbumen"),"i.HANDEPCODE"));
		}
		
		if (map.get("wentileixing")!=null &&map.get("wentileixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("wentileixing"),"i.APPLBASQUE"));
		}
		
		if (map.get("ketileixing")!=null &&map.get("ketileixing").length()!=0) {
			sb.append(QueryJieAnUtils.UtilOfObjectTypeS(map.get("ketileixing"),"o.invobjtype"));
			sb.append(QueryJieAnUtils.UtilOfObjectTypeB(map.get("ketileixing"),"o.BUSINESSTYPE"));
		}
		
		if (map.get("zhutileixing")!=null &&map.get("zhutileixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("zhutileixing"),"ma.PTTYPE"));
		}
		
		if (map.get("hangyeleixing")!=null &&map.get("hangyeleixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("hangyeleixing"),"ma.TRADETYPE"));
		}
		
		if (map.get("weifazhonglei")!=null &&map.get("weifazhonglei").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("weifazhonglei"),"p.ILLEGACTTYPE"));
		}
		
		if (map.get("xingzhengchufa")!=null &&map.get("xingzhengchufa").length()!=0) {
			sb.append("and p.COMMEATYPE =? \n ");
			list.add(map.get("xingzhengchufa"));
		}
		
		if (map.get("zhixingleibie")!=null &&map.get("zhixingleibie").length()!=0) {
			sb.append("and h.EXESORT =? \n ");
			list.add(map.get("zhixingleibie"));
		}
		
		if (map.get("jieshoufangshi")!=null &&map.get("jieshoufangshi").length()!=0) {
			sb.append("and i.INCFORM =? \n ");
			list.add(map.get("jieshoufangshi"));
		}
		
		if (map.get("infotype")!=null &&map.get("infotype").length()!=0) {
			sb.append("and i.INFTYPE =? \n ");
			list.add(map.get("infotype"));
		}
		
		if (map.get("wangzhanleixing")!=null &&map.get("wangzhanleixing").length()!=0) {
			sb.append("and ma.WEBSITETYPE =? \n ");
			list.add(map.get("wangzhanleixing"));
		}
		
		/*if (map.get("qinquanleixing")!=null &&map.get("qinquanleixing").length()!=0) {
			sb.append("and m.TORTYPE =? \n ");
			list.add(map.get("qinquanleixing"));
		}*/
		
		if (map.get("yiwuleixing")!=null &&map.get("yiwuleixing").length()!=0) {
			sb.append("and m.DEFOBL =? \n ");
			list.add(map.get("yiwuleixing"));
		}
		if (map.get("chufazhonglei")!=null &&map.get("chufazhonglei").length()!=0) {
			sb.append("and p.Pentype =? \n ");
			list.add(map.get("chufazhonglei"));
		}
		sb.append(
				")\n" +
						"       select x.name,x.code,\n" + 
						"       sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"       sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )shushi,\n" + 
						"       sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )tiaojie,\n" + 
						"       sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"       sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"       sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"       sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)jiabeipeichangjine,\n" + 
						"       sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"       sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)lianshu,\n" + 
						"       sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"       sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"       sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )chufajine,\n" + 
						"       sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"       sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )bianjiajine,\n" + 
						"       sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"       sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"       sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"       sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"       sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"       sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"       sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"       from x group by x.name,x.code\n" + 
						"\n" + 
						"\n" + 
						"       union all\n" + 
						"       select '上一段时间段数据'as name,'999901' as code,\n" + 
						"       sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"       sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) shushi,\n" + 
						"       sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ) tiaojie,\n" + 
						"       sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end) jiabeipeichangjine,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"       sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end) lianshu,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end ) chufajine,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end ) bianjiajine,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"       sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"       from x\n" + 
						"       union all\n" + 
						"        select '环比增减（%）'as name,'999902' as code,\n" + 
						"       round((case when sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)=0 then 0\n" + 
						"       when sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end)=0 and\n" + 
						"         sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)-\n" + 
						"         sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end))/\n" + 
						"         sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end) end ),4)*100 subnum,\n" + 
						"       round((case when sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )=0\n" + 
						"       and  sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) =0 then 0\n" + 
						"       when sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )-\n" + 
						"         sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ))/\n" + 
						"         sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) end),4)*100 shushi,\n" + 
						"\n" + 
						"       round((case when sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0\n" + 
						"             and  sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0 then 0\n" + 
						"        when sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )-\n" + 
						"          sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ))/\n" + 
						"          sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ) end ),4)*100 tiaojie,\n" + 
						"       round(( case when sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 and\n" + 
						"         sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 then 0\n" + 
						"         when sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 and\n" + 
						"           sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)<>0 then 1 else\n" + 
						"           (sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)-\n" + 
						"           sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end))/\n" + 
						"           sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end) end ),4)*100 qizha,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end )=0 and\n" + 
						"             sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) =0 then 0\n" + 
						"            when sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end )=0\n" + 
						"             and sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end )<>0 then 1\n" + 
						"            else(sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end )-\n" + 
						"            sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end ))/\n" + 
						"            sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end ) end  ),4)*100 zhengyijine,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end)=0\n" + 
						"             and sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)=0 then 0\n" + 
						"             when sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end)=0\n" + 
						"             and sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)<>0 then 1\n" + 
						"             else(sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)-\n" + 
						"             sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end))/\n" + 
						"             sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end) end),4)*100 wanhuijingjisunshi,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end)=0 and\n" + 
						"             sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end) =0 then 0\n" + 
						"            when sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end)=0 and\n" + 
						"            sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)<>0 then 1\n" + 
						"            else (sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)-\n" + 
						"            sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end))/\n" + 
						"            sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end) end),4)*100 jiabeipeichangjine,\n" + 
						"      round( (case when sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)<>0 then 1\n" + 
						"         else(sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end) end),4)*100 jingshenpeichangjine,\n" + 
						"       round((case when sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)=0 then 0\n" + 
						"       when sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end) <> 0 then 1 else\n" + 
						"         (sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)-\n" + 
						"         sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end))/\n" + 
						"         sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end) end),4)*100lianshu,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end )=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end )=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) <>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end )-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end ))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end ) end),4)*100 anzhi,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end)=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end) =0 and\n" + 
						"         sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end) end),4)*100 weifasuode,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end )=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.penam,0) else 0 end ) <>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end ))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end ) end),4)*100 chufajine,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end)=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end)=0 and\n" + 
						"         sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) <>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end)-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end) end),4)*100 moshoujine,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end )=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )<>0 then 1\n" + 
						"         else(sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end ))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end ) end),4)*100 bianjiajine,\n" + 
						"      round( (case when sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0  then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)<>0 then 1\n" + 
						"         else (sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)-\n" + 
						"           sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end))/\n" + 
						"           sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end) end),4)*100 jingjisunshizhi,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)<>0 then 1\n" + 
						"         else (sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)-\n" + 
						"           sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end))/\n" + 
						"           sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end) end),4)*100 daohuiwodian,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end )=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end ))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end ) end),4)*100chudongrenshu,\n" + 
						"      round( (case when sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end)=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end) end),4)*100 chudongcheliang,\n" + 
						"      round( (case when sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end )=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end ))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end ) end),4)*100 shoushangrenshu,\n" + 
						"      round( (case when sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end) end),4)*100 shouhairenshu,\n" + 
						"       round((case when sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end )=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) =0 then 0\n" + 
						"       when sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end )<>0 then 1\n" + 
						"         else(sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end )-\n" + 
						"         sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end ))/\n" + 
						"         sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end ) end),4)*100 xishengrenshu\n" + 
						"       from x\n" + 
						"   union all\n" + 
						"       select '去年同期数据'as name,'999903' as code,\n" + 
						"       sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"       sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) shushi,\n" + 
						"       sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ) tiaojie,\n" + 
						"       sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end) jiabeipeichangjine,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"       sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end) lianshu,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end ) chufajine,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end ) bianjiajine,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end ) chudongrenshu,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"       sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"       from x\n" + 
						"       union all\n" + 
						"         select '同比增减（%）'as name,'999904' as code,\n" + 
						"       round((case when sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)=0 then 0\n" + 
						"       when sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end)=0 and\n" + 
						"         sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)-\n" + 
						"         sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end))/\n" + 
						"         sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end) end ),4)*100 subnum,\n" + 
						"       round((case when sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )=0\n" + 
						"       and  sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) =0 then 0\n" + 
						"       when sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )-\n" + 
						"         sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ))/\n" + 
						"         sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) end),4)*100 shushi,\n" + 
						"       round((case when sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0\n" + 
						"             and  sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0 then 0\n" + 
						"        when sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )-\n" + 
						"          sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ))/\n" + 
						"          sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ) end ),4)*100 tiaojie,\n" + 
						"       round(( case when sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 and\n" + 
						"         sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 then 0\n" + 
						"         when sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 and\n" + 
						"           sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)<>0 then 1 else\n" + 
						"           (sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)-\n" + 
						"           sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end))/\n" + 
						"           sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end) end ),4)*100 qizha,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end )=0 and\n" + 
						"             sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) =0 then 0\n" + 
						"            when sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end )=0\n" + 
						"             and sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end )<>0 then 1\n" + 
						"            else(sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end )-\n" + 
						"            sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end ))/\n" + 
						"            sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end ) end  ),4)*100zhengyijine,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end)=0\n" + 
						"             and sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)=0 then 0\n" + 
						"             when sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end)=0\n" + 
						"             and sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)<>0 then 1\n" + 
						"             else(sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)-\n" + 
						"             sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end))/\n" + 
						"             sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end) end),4)*100 wanhuijingjisunshi,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end)=0 and\n" + 
						"             sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end) =0 then 0\n" + 
						"            when sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end)=0 and\n" + 
						"            sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)<>0 then 1\n" + 
						"            else (sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)-\n" + 
						"            sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end))/\n" + 
						"            sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end) end),4)*100 jiabeipeichangjine,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)<>0 then 1\n" + 
						"         else(sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end) end),4)*100 jingshenpeichangjine,\n" + 
						"       round((case when sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)=0 then 0\n" + 
						"       when sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end) <> 0 then 1 else\n" + 
						"         (sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)-\n" + 
						"         sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end))/\n" + 
						"         sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end) end),4)*100lianshu,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end )=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end )=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) <>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end )-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end ))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end ) end),4)*100 anzhi,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end)=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end) =0 and\n" + 
						"         sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end) end),4)*100 weifasuode,\n" + 
						"      round( (case when sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end )=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.penam,0) else 0 end ) <>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end ))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end ) end),4)*100 chufajine,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end)=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end)=0 and\n" + 
						"         sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) <>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end)-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end) end),4)*100 moshoujine,\n" + 
						"      round( (case when sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end )=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )<>0 then 1\n" + 
						"         else(sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end ))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end ) end),4)*100 bianjiajine,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0  then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)<>0 then 1\n" + 
						"         else (sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)-\n" + 
						"           sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end))/\n" + 
						"           sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end) end),4)*100 jingjisunshizhi,\n" + 
						"      round( (case when sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)<>0 then 1\n" + 
						"         else (sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)-\n" + 
						"           sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end))/\n" + 
						"           sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end) end),4)*100 daohuiwodian,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end )=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end ))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end ) end),4)*100 chudongrenshu,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end)=0 and\n" + 
						"       sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end) end),4)*100 chudongcheliang,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end )=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end ))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end ) end),4)*100 shoushangrenshu,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)<>0 then 1 else\n" + 
						"         (sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end) end),4)*100 shouhairenshu,\n" + 
						"       round((case when sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end )=0\n" + 
						"       and sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) =0 then 0\n" + 
						"       when sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end )=0\n" + 
						"         and sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end )<>0 then 1\n" + 
						"         else(sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end )-\n" + 
						"         sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end ))/\n" + 
						"         sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end ) end),4)*100 xishengrenshu\n" + 
						"       from x\n" + 
						"         order by code asc,name desc");
		try {
			res=dao.queryForList(sb.toString(), list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("结案信息申诉举报侵权类型统计报表", "WDY", i == 1 ? "查看报表" : "下载报表", sb.toString(),
				map.get("begintime")+","+map.get("endtime"), req);
		return res;
	}
	public List<List<Map>> sheJiKeTi(Map<String, String> map, int i,
			HttpServletRequest req) {
		List<List<Map>> res=new ArrayList<List<Map>>();
		List listHuanBiTongBi=new ArrayList();
		List list=new ArrayList();
		list.add(map.get("begintime"));
		list.add(map.get("endtime")+" 23:59:59");
		listHuanBiTongBi.add(map.get("begintime"));
		listHuanBiTongBi.add(map.get("endtime")+" 23:59:59");
		listHuanBiTongBi.add(QueryJieAnUtils.getTongBiDate(map.get("begintime")));
		listHuanBiTongBi.add(QueryJieAnUtils.getTongBiDate(map.get("endtime"))+" 23:59:59");
		
		if (map.get("hbegintime")!=null&&map.get("hbegintime").length()!=0) {
			listHuanBiTongBi.add(map.get("hbegintime"));
		}else{
			listHuanBiTongBi.add(QueryJieAnUtils.getHuanBiDate(map.get("begintime")));
		}
		if (map.get("hendtime")!=null&&map.get("hendtime").length()!=0) {
			listHuanBiTongBi.add(map.get("hendtime")+" 23:59:59");
		}else{
			listHuanBiTongBi.add(QueryJieAnUtils.getHuanBiDate(map.get("endtime"))+" 23:59:59");
		}
		if (map.get("xingzhengchufa")!=null &&map.get("xingzhengchufa").length()!=0) {
			listHuanBiTongBi.add(map.get("xingzhengchufa"));
			list.add(map.get("xingzhengchufa"));
		}
		if (map.get("zhixingleibie")!=null &&map.get("zhixingleibie").length()!=0) {
			listHuanBiTongBi.add(map.get("zhixingleibie"));
			list.add(map.get("zhixingleibie"));
		}
		if (map.get("jieshoufangshi")!=null &&map.get("jieshoufangshi").length()!=0) {
			listHuanBiTongBi.add(map.get("jieshoufangshi"));
			list.add(map.get("jieshoufangshi"));
		}
		if (map.get("infotype")!=null &&map.get("infotype").length()!=0) {
			listHuanBiTongBi.add(map.get("infotype"));
			list.add(map.get("infotype"));
		}
		if (map.get("wangzhanleixing")!=null &&map.get("wangzhanleixing").length()!=0) {
			listHuanBiTongBi.add(map.get("wangzhanleixing"));
			list.add(map.get("wangzhanleixing"));
		}
		if (map.get("qinquanleixing")!=null &&map.get("qinquanleixing").length()!=0) {
			listHuanBiTongBi.add(map.get("qinquanleixing"));
			list.add(map.get("qinquanleixing"));
		}
		if (map.get("yiwuleixing")!=null &&map.get("yiwuleixing").length()!=0) {
			listHuanBiTongBi.add(map.get("yiwuleixing"));
			list.add(map.get("yiwuleixing"));
		}
		if (map.get("chufazhonglei")!=null &&map.get("chufazhonglei").length()!=0) {
			listHuanBiTongBi.add(map.get("chufazhonglei"));
			list.add(map.get("chufazhonglei"));
		}
		String [] sqls={this.getGongShang(map),this.getXiaoWeiHui(map),this.getZhiShiChanQuan(map),this.getJiaGeJianCha(map),this.getZhiLiangJianDu(map)};
		String sql=this.listHuanBiTongBi(map);
		try {
			for (int j = 0; j < sqls.length; j++) {
				res.add(dao.queryForList(sqls[j], list));
			}
			res.add(dao.queryForList(sql,listHuanBiTongBi));
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("结案信息申诉举报涉及客体统计报表", "WDY", i == 1 ? "查看报表" : "下载报表", sql.toString(),
				map.get("begintime")+","+map.get("endtime"), req);
		return res;
	}
	private String listHuanBiTongBi(Map<String, String> map) {
		StringBuffer sb=new StringBuffer();
		sb.append(
				"with x as (\n" +
						"\n" + 
						"        select i.finishtime, --完成时间\n" + 
						"               v.VISITCONFIRM, --回访情况\n" + 
						"               m.MEDIATE_RESULT, --调解结果\n" + 
						"               m.CHEATSIGN， --是否欺诈\n" + 
						"               m.DISAM, --争议金额\n" + 
						"               m.REDECOLOS, --挽回经济损失\n" + 
						"               m.DOUAMEAM, --加倍赔偿金额\n" + 
						"               m.SPIAMEAM, --精神赔偿金额\n" + 
						"               f.PUTONCASE, --是否立案\n" + 
						"               c.CASEVAL, --案值\n" + 
						"               p.illegincome, --违法所得\n" + 
						"               p.penam, --处罚金额\n" + 
						"               p.forfam, --没收金额\n" + 
						"               p.apprcuram, --变价金额\n" + 
						"               c.ECOLOVAL, --经济损失值\n" + 
						"               p.distoriedplace, --捣毁窝点\n" + 
						"               f.executeperson, --出动人数\n" + 
						"               f.executecar, -- 出动车辆\n" + 
						"               h.INJNUM, --受伤人数\n" + 
						"               d.VICTIMNUM, --受害人数\n" + 
						"               h.SACNUM, --牺牲人数\n" + 
						"               nvl(o.invobjtype,'9999999999') as code,\n" + 
						"               (case when i.regtime >= to_date(?, 'yyyy-mm-dd')\n" + 
						"                    and i.regtime <=\n" + 
						"               to_date(?, 'yyyy-mm-dd hh24:mi:ss')then 1 else 0 end) flag,\n" + 
						"              (case when i.regtime >= to_date(?, 'yyyy-mm-dd')\n" + 
						"                    and i.regtime <=\n" + 
						"               to_date(?, 'yyyy-mm-dd hh24:mi:ss')then 1 else 0 end) tflag,\n" + 
						"               (case when i.regtime >= to_date(?, 'yyyy-mm-dd')\n" + 
						"                    and i.regtime <=\n" + 
						"               to_date(?, 'yyyy-mm-dd hh24:mi:ss')then 1 else 0 end) hflag\n" + 
						"          from dc_cpr_infoware         i,\n" + 
						"               DC_CPR_RETURN_VISIT     v,\n" + 
						"               dc_cpr_feedback         f,\n" + 
						"               DC_CPR_MEDIATION        m,\n" + 
						"               dc_cpr_case_info        c,\n" + 
						"               DC_CPR_LEGAL_PUNISHMENT p,\n" + 
						"               DC_CPR_CASE_HANDLE      h,\n" + 
						"               DC_CPR_CONFIRMED_DAMAGE d,\n" + 
						"               dc_cpr_involved_object  o,\n" + 
						"               dc_cpr_involved_main    ma\n" + 
						"         where i.infowareid = v.infowareid(+)\n" + 
						"           and i.feedbackid = f.feedbackid\n" + 
						"           and f.mediationid = m.mediationid(+)\n" + 
						"           and f.caseinfoid = c.caseinfoid(+)\n" + 
						"           and c.LEGPUNID = p.legpunid(+)\n" + 
						"           and c.CASEHANDLEID = h.casehandleid(+)\n" + 
						"           and c.CONDAMID = d.condamid(+)\n" + 
						"           and o.invobjid = i.invobjid\n" + 
						"           and i.invmaiid = ma.invmaiid(+)\n" + 
						"           and o.businesstype in ('ZH20','ZH21','ZH18','ZH19','CH20')\n" + 
						"           and o.invobjtype is not null \n");
		this.setSqlParameter(sb, map);
		sb.append(
				")\n" +
						"         select '总数'as name,'99999999990' as code,\n" + 
						"        sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"        sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) shushi,\n" + 
						"        sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ) tiaojie,\n" + 
						"        sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end) jiabeipeichangjine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"        sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end) lianshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"        sum(case when x.flag=1 then nvl(x.penam,0) else 0 end ) chufajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end ) bianjiajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"        sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"        from x\n" + 
						"        union all\n" + 
						"             select '上一段时间段数据'as name,'99999999991' as code,\n" + 
						"        sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"        sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) shushi,\n" + 
						"        sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ) tiaojie,\n" + 
						"        sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end) jiabeipeichangjine,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"        sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end) lianshu,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end ) chufajine,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end ) bianjiajine,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"        from x\n" + 
						"        union all\n" + 
						"         select '环比增减（%）'as name,'99999999992' as code,\n" + 
						"        round((case when sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end)=0 and\n" + 
						"          sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)-\n" + 
						"          sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end))/\n" + 
						"          sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end) end ),4)*100 subnum,\n" + 
						"        round((case when sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )=0\n" + 
						"        and  sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) =0 then 0\n" + 
						"        when sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )-\n" + 
						"          sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ))/\n" + 
						"          sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) end),4)*100 shushi,\n" + 
						"\n" + 
						"        round((case when sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0\n" + 
						"              and  sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0 then 0\n" + 
						"         when sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0\n" + 
						"           and sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )<>0 then 1 else\n" + 
						"           (sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )-\n" + 
						"           sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ))/\n" + 
						"           sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ) end ),4)*100 tiaojie,\n" + 
						"        round(( case when sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 and\n" + 
						"          sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 then 0\n" + 
						"          when sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 and\n" + 
						"            sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)<>0 then 1 else\n" + 
						"            (sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)-\n" + 
						"            sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end))/\n" + 
						"            sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end) end ),4)*100 qizha,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end )=0 and\n" + 
						"              sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) =0 then 0\n" + 
						"             when sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end )=0\n" + 
						"              and sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end )<>0 then 1\n" + 
						"             else(sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end )-\n" + 
						"             sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end ))/\n" + 
						"             sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end ) end  ),4)*100 zhengyijine,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end)=0\n" + 
						"              and sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)=0 then 0\n" + 
						"              when sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end)=0\n" + 
						"              and sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)<>0 then 1\n" + 
						"              else(sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)-\n" + 
						"              sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end))/\n" + 
						"              sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end) end),4)*100 wanhuijingjisunshi,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end)=0 and\n" + 
						"              sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end) =0 then 0\n" + 
						"             when sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end)=0 and\n" + 
						"             sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)<>0 then 1\n" + 
						"             else (sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)-\n" + 
						"             sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end))/\n" + 
						"             sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end) end),4)*100 jiabeipeichangjine,\n" + 
						"       round( (case when sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)<>0 then 1\n" + 
						"          else(sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end) end),4)*100 jingshenpeichangjine,\n" + 
						"        round((case when sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end) <> 0 then 1 else\n" + 
						"          (sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)-\n" + 
						"          sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end))/\n" + 
						"          sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end) end),4)*100lianshu,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end )=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) <>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end )-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end ))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end ) end),4)*100 anzhi,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end)=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end) =0 and\n" + 
						"          sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end) end),4)*100 weifasuode,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end )=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.penam,0) else 0 end ) <>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end ))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end ) end),4)*100 chufajine,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end)=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end)=0 and\n" + 
						"          sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) <>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end)-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end) end),4)*100 moshoujine,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end )=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )<>0 then 1\n" + 
						"          else(sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end ))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end ) end),4)*100 bianjiajine,\n" + 
						"       round( (case when sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0  then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)<>0 then 1\n" + 
						"          else (sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)-\n" + 
						"            sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end))/\n" + 
						"            sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end) end),4)*100 jingjisunshizhi,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)<>0 then 1\n" + 
						"          else (sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)-\n" + 
						"            sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end))/\n" + 
						"            sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end) end),4)*100 daohuiwodian,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end ))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end ) end),4)*100chudongrenshu,\n" + 
						"       round( (case when sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end)=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end) end),4)*100 chudongcheliang,\n" + 
						"       round( (case when sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end ))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end ) end),4)*100 shoushangrenshu,\n" + 
						"       round( (case when sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end) end),4)*100 shouhairenshu,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) =0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end )<>0 then 1\n" + 
						"          else(sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end )-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end ))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end ) end),4)*100 xishengrenshu\n" + 
						"        from x\n" + 
						"    union all\n" + 
						"        select '去年同期数据'as name,'99999999993' as code,\n" + 
						"        sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"        sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) shushi,\n" + 
						"        sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ) tiaojie,\n" + 
						"        sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end) jiabeipeichangjine,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"        sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end) lianshu,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end ) chufajine,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end ) bianjiajine,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end ) chudongrenshu,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"        from x\n" + 
						"        union all\n" + 
						"          select '同比增减（%）'as name,'99999999994' as code,\n" + 
						"        round((case when sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end)=0 and\n" + 
						"          sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)-\n" + 
						"          sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end))/\n" + 
						"          sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end) end ),4)*100 subnum,\n" + 
						"        round((case when sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )=0\n" + 
						"        and  sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) =0 then 0\n" + 
						"        when sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )-\n" + 
						"          sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ))/\n" + 
						"          sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) end),4)*100 shushi,\n" + 
						"        round((case when sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0\n" + 
						"              and  sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0 then 0\n" + 
						"         when sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0\n" + 
						"           and sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )<>0 then 1 else\n" + 
						"           (sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )-\n" + 
						"           sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ))/\n" + 
						"           sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ) end ),4)*100 tiaojie,\n" + 
						"        round(( case when sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 and\n" + 
						"          sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 then 0\n" + 
						"          when sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 and\n" + 
						"            sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)<>0 then 1 else\n" + 
						"            (sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)-\n" + 
						"            sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end))/\n" + 
						"            sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end) end ),4)*100 qizha,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end )=0 and\n" + 
						"              sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) =0 then 0\n" + 
						"             when sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end )=0\n" + 
						"              and sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end )<>0 then 1\n" + 
						"             else(sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end )-\n" + 
						"             sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end ))/\n" + 
						"             sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end ) end  ),4)*100zhengyijine,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end)=0\n" + 
						"              and sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)=0 then 0\n" + 
						"              when sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end)=0\n" + 
						"              and sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)<>0 then 1\n" + 
						"              else(sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)-\n" + 
						"              sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end))/\n" + 
						"              sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end) end),4)*100 wanhuijingjisunshi,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end)=0 and\n" + 
						"              sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end) =0 then 0\n" + 
						"             when sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end)=0 and\n" + 
						"             sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)<>0 then 1\n" + 
						"             else (sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)-\n" + 
						"             sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end))/\n" + 
						"             sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end) end),4)*100 jiabeipeichangjine,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)<>0 then 1\n" + 
						"          else(sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end) end),4)*100 jingshenpeichangjine,\n" + 
						"        round((case when sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end) <> 0 then 1 else\n" + 
						"          (sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)-\n" + 
						"          sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end))/\n" + 
						"          sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end) end),4)*100lianshu,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end )=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) <>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end )-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end ))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end ) end),4)*100 anzhi,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end)=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end) =0 and\n" + 
						"          sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end) end),4)*100 weifasuode,\n" + 
						"       round( (case when sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end )=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.penam,0) else 0 end ) <>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end ))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end ) end),4)*100 chufajine,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end)=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end)=0 and\n" + 
						"          sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) <>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end)-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end) end),4)*100 moshoujine,\n" + 
						"       round( (case when sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end )=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )<>0 then 1\n" + 
						"          else(sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end ))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end ) end),4)*100 bianjiajine,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0  then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)<>0 then 1\n" + 
						"          else (sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)-\n" + 
						"            sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end))/\n" + 
						"            sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end) end),4)*100 jingjisunshizhi,\n" + 
						"       round( (case when sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)<>0 then 1\n" + 
						"          else (sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)-\n" + 
						"            sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end))/\n" + 
						"            sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end) end),4)*100 daohuiwodian,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end ))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end ) end),4)*100 chudongrenshu,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end)=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end) end),4)*100 chudongcheliang,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end ))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end ) end),4)*100 shoushangrenshu,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end) end),4)*100 shouhairenshu,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) =0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end )<>0 then 1\n" + 
						"          else(sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end )-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end ))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end ) end),4)*100 xishengrenshu\n" + 
						"        from x");
		return sb.toString();
	}
	
	private String getZhiLiangJianDu(Map<String, String> map) {
		StringBuffer sb=new StringBuffer();
		sb.append(
				"select nvl(j.name,x.code) as name,x.code ,\n" +
						"x.subnum,\n" + 
						"x.shushi,\n" + 
						"x.tiaojie,\n" + 
						"x.qizha,\n" + 
						"x.zhengyijine,\n" + 
						"x.wanhuijingjisunshi,\n" + 
						"x.jiabeipeichangjine,\n" + 
						"x.jingshenpeichangjine,\n" + 
						"x.lianshu,\n" + 
						"x.anzhi,\n" + 
						"x.weifasuode,\n" + 
						"x.chufajine,\n" + 
						"x.moshoujine,\n" + 
						"x.bianjiajine,\n" + 
						"x.jingjisunshizhi,\n" + 
						"x.daohuiwodian,\n" + 
						"x.chudongrenshu,\n" + 
						"x.chudongcheliang,\n" + 
						"x.shoushangrenshu,\n" + 
						"x.shouhairenshu,\n" + 
						"x.xishengrenshu from (\n" + 
						"select distinct --x.name,\n" + 
						"x.code,\n" + 
						"x.subnum,\n" + 
						"x.shushi,\n" + 
						"x.tiaojie,\n" + 
						"x.qizha,\n" + 
						"x.zhengyijine,\n" + 
						"x.wanhuijingjisunshi,\n" + 
						"x.jiabeipeichangjine,\n" + 
						"x.jingshenpeichangjine,\n" + 
						"x.lianshu,\n" + 
						"x.anzhi,\n" + 
						"x.weifasuode,\n" + 
						"x.chufajine,\n" + 
						"x.moshoujine,\n" + 
						"x.bianjiajine,\n" + 
						"x.jingjisunshizhi,\n" + 
						"x.daohuiwodian,\n" + 
						"x.chudongrenshu,\n" + 
						"x.chudongcheliang,\n" + 
						"x.shoushangrenshu,\n" + 
						"x.shouhairenshu,\n" + 
						"x.xishengrenshu from (\n" + 
						"with x as (\n" + 
						"\n" + 
						"        select i.finishtime, --完成时间\n" + 
						"               v.VISITCONFIRM, --回访情况\n" + 
						"               m.MEDIATE_RESULT, --调解结果\n" + 
						"               m.CHEATSIGN， --是否欺诈\n" + 
						"               m.DISAM, --争议金额\n" + 
						"               m.REDECOLOS, --挽回经济损失\n" + 
						"               m.DOUAMEAM, --加倍赔偿金额\n" + 
						"               m.SPIAMEAM, --精神赔偿金额\n" + 
						"               f.PUTONCASE, --是否立案\n" + 
						"               c.CASEVAL, --案值\n" + 
						"               p.illegincome, --违法所得\n" + 
						"               p.penam, --处罚金额\n" + 
						"               p.forfam, --没收金额\n" + 
						"               p.apprcuram, --变价金额\n" + 
						"               c.ECOLOVAL, --经济损失值\n" + 
						"               p.distoriedplace, --捣毁窝点\n" + 
						"               f.executeperson, --出动人数\n" + 
						"               f.executecar, -- 出动车辆\n" + 
						"               h.INJNUM, --受伤人数\n" + 
						"               d.VICTIMNUM, --受害人数\n" + 
						"               h.SACNUM, --牺牲人数\n" + 
						"               nvl(o.invobjtype,'9999999999') as code,\n" + 
						"               (case when i.regtime >= to_date(?, 'yyyy-mm-dd')\n" + 
						"                    and i.regtime <=\n" + 
						"               to_date(?, 'yyyy-mm-dd hh24:mi:ss')then 1 else 0 end) flag \n");
		sb.append(
						"from dc_cpr_infoware         i,\n" +
						"              DC_CPR_RETURN_VISIT     v,\n" + 
						"              dc_cpr_feedback         f,\n" + 
						"              DC_CPR_MEDIATION        m,\n" + 
						"              dc_cpr_case_info        c,\n" + 
						"              DC_CPR_LEGAL_PUNISHMENT p,\n" + 
						"              DC_CPR_CASE_HANDLE      h,\n" + 
						"              DC_CPR_CONFIRMED_DAMAGE d,\n" + 
						"              dc_cpr_involved_object  o,\n" + 
						"              dc_cpr_involved_main    ma\n" + 
						"        where i.infowareid = v.infowareid(+)\n" + 
						"          and i.feedbackid = f.feedbackid\n" + 
						"          and f.mediationid = m.mediationid(+)\n" + 
						"          and f.caseinfoid = c.caseinfoid(+)\n" + 
						"          and c.LEGPUNID = p.legpunid(+)\n" + 
						"          and c.CASEHANDLEID = h.casehandleid(+)\n" + 
						"          and c.CONDAMID = d.condamid(+)\n" + 
						"          and o.invobjid = i.invobjid\n" + 
						"          and i.invmaiid = ma.invmaiid(+)\n" + 
						"          and o.businesstype ='ZH21'\n" + 
						"          and o.invobjtype is not null \n");
		this.setSqlParameter(sb,map);
		sb.append(
				")\n" +
						"        select '00质量监督' as code, sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"        sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )shushi,\n" + 
						"        sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )tiaojie,\n" + 
						"        sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)jiabeipeichangjine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"        sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)lianshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"        sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )chufajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )bianjiajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"        sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"        from x --group by x.name\n" + 
						"        union all\n" + 
						"        select x.code,\n" + 
						"        sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"        sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )shushi,\n" + 
						"        sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )tiaojie,\n" + 
						"        sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)jiabeipeichangjine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"        sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)lianshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"        sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )chufajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )bianjiajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"        sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"        from x group by x.code\n" + 
						"        union all\n" + 
						"        select substr(x.code,0,2)||'00000000',\n" + 
						"        sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"        sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )shushi,\n" + 
						"        sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )tiaojie,\n" + 
						"        sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)jiabeipeichangjine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"        sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)lianshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"        sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )chufajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )bianjiajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"        sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"        from x group by substr(x.code,0,2)||'00000000'\n" + 
						"        union all\n" + 
						"        select substr(x.code,0,4)||'000000',\n" + 
						"        sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"        sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )shushi,\n" + 
						"        sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )tiaojie,\n" + 
						"        sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)jiabeipeichangjine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"        sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)lianshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"        sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )chufajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )bianjiajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"        sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"        from x group by substr(x.code,0,4)||'000000'\n" + 
						"         union all\n" + 
						"        select substr(x.code,0,6)||'0000',\n" + 
						"        sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"        sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )shushi,\n" + 
						"        sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )tiaojie,\n" + 
						"        sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)jiabeipeichangjine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"        sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)lianshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"        sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )chufajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )bianjiajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"        sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"        from x group by substr(x.code,0,6)||'0000'\n" + 
						"         union all\n" + 
						"        select substr(x.code,0,8)||'00',\n" + 
						"        sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"        sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )shushi,\n" + 
						"        sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )tiaojie,\n" + 
						"        sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)jiabeipeichangjine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"        sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)lianshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"        sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )chufajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )bianjiajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"        sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"        from x group by substr(x.code,0,8)||'00'\n" + 
						"        ) x\n" + 
						"      )x,  (select code , (case when substr(code,9,2)<>'00'then '　　　　'||name\n" + 
						"      when substr(code,7,4)<>'0000'then '　　　'||name\n" + 
						"      when substr(code,5,6)<>'000000'then '　　'||name\n" + 
						"      when substr(code,3,8)<>'00000000'then '　'||name\n" + 
						"      else name end) name  from dc_code.dc_12315_codedata where codetable='ZH21'\n" + 
						"       union all\n" + 
						"      select '00质量监督' code ,'质量监督'name from dual)j\n" + 
						"      where x.code=j.code(+)\n" + 
						"      order by code asc,subnum desc");
		return sb.toString();
	}
	private String getJiaGeJianCha(Map<String, String> map) {
		StringBuffer sb=new StringBuffer();
		sb.append(
				"select nvl(j.name,x.code) as name,x.code ,\n" +
						"x.subnum,\n" + 
						"x.shushi,\n" + 
						"x.tiaojie,\n" + 
						"x.qizha,\n" + 
						"x.zhengyijine,\n" + 
						"x.wanhuijingjisunshi,\n" + 
						"x.jiabeipeichangjine,\n" + 
						"x.jingshenpeichangjine,\n" + 
						"x.lianshu,\n" + 
						"x.anzhi,\n" + 
						"x.weifasuode,\n" + 
						"x.chufajine,\n" + 
						"x.moshoujine,\n" + 
						"x.bianjiajine,\n" + 
						"x.jingjisunshizhi,\n" + 
						"x.daohuiwodian,\n" + 
						"x.chudongrenshu,\n" + 
						"x.chudongcheliang,\n" + 
						"x.shoushangrenshu,\n" + 
						"x.shouhairenshu,\n" + 
						"x.xishengrenshu from (\n" + 
						"select distinct --x.name,\n" + 
						"x.code,\n" + 
						"x.subnum,\n" + 
						"x.shushi,\n" + 
						"x.tiaojie,\n" + 
						"x.qizha,\n" + 
						"x.zhengyijine,\n" + 
						"x.wanhuijingjisunshi,\n" + 
						"x.jiabeipeichangjine,\n" + 
						"x.jingshenpeichangjine,\n" + 
						"x.lianshu,\n" + 
						"x.anzhi,\n" + 
						"x.weifasuode,\n" + 
						"x.chufajine,\n" + 
						"x.moshoujine,\n" + 
						"x.bianjiajine,\n" + 
						"x.jingjisunshizhi,\n" + 
						"x.daohuiwodian,\n" + 
						"x.chudongrenshu,\n" + 
						"x.chudongcheliang,\n" + 
						"x.shoushangrenshu,\n" + 
						"x.shouhairenshu,\n" + 
						"x.xishengrenshu from (\n" + 
						"with x as (\n" + 
						"\n" + 
						"        select i.finishtime, --完成时间\n" + 
						"               v.VISITCONFIRM, --回访情况\n" + 
						"               m.MEDIATE_RESULT, --调解结果\n" + 
						"               m.CHEATSIGN， --是否欺诈\n" + 
						"               m.DISAM, --争议金额\n" + 
						"               m.REDECOLOS, --挽回经济损失\n" + 
						"               m.DOUAMEAM, --加倍赔偿金额\n" + 
						"               m.SPIAMEAM, --精神赔偿金额\n" + 
						"               f.PUTONCASE, --是否立案\n" + 
						"               c.CASEVAL, --案值\n" + 
						"               p.illegincome, --违法所得\n" + 
						"               p.penam, --处罚金额\n" + 
						"               p.forfam, --没收金额\n" + 
						"               p.apprcuram, --变价金额\n" + 
						"               c.ECOLOVAL, --经济损失值\n" + 
						"               p.distoriedplace, --捣毁窝点\n" + 
						"               f.executeperson, --出动人数\n" + 
						"               f.executecar, -- 出动车辆\n" + 
						"               h.INJNUM, --受伤人数\n" + 
						"               d.VICTIMNUM, --受害人数\n" + 
						"               h.SACNUM, --牺牲人数\n" + 
						"               nvl(o.invobjtype,'9999999999') as code,\n" + 
						"               (case when i.regtime >= to_date(?, 'yyyy-mm-dd')\n" + 
						"                    and i.regtime <=\n" + 
						"               to_date(?, 'yyyy-mm-dd hh24:mi:ss')then 1 else 0 end) flag \n");
		sb.append(
				"from dc_cpr_infoware         i,\n" +
						"               DC_CPR_RETURN_VISIT     v,\n" + 
						"               dc_cpr_feedback         f,\n" + 
						"               DC_CPR_MEDIATION        m,\n" + 
						"               dc_cpr_case_info        c,\n" + 
						"               DC_CPR_LEGAL_PUNISHMENT p,\n" + 
						"               DC_CPR_CASE_HANDLE      h,\n" + 
						"               DC_CPR_CONFIRMED_DAMAGE d,\n" + 
						"               dc_cpr_involved_object  o,\n" + 
						"               dc_cpr_involved_main    ma\n" + 
						"         where i.infowareid = v.infowareid(+)\n" + 
						"           and i.feedbackid = f.feedbackid\n" + 
						"           and f.mediationid = m.mediationid(+)\n" + 
						"           and f.caseinfoid = c.caseinfoid(+)\n" + 
						"           and c.LEGPUNID = p.legpunid(+)\n" + 
						"           and c.CASEHANDLEID = h.casehandleid(+)\n" + 
						"           and c.CONDAMID = d.condamid(+)\n" + 
						"           and o.invobjid = i.invobjid\n" + 
						"           and i.invmaiid = ma.invmaiid(+)\n" + 
						"           and o.businesstype ='ZH20'\n" + 
						"           and o.invobjtype is not null \n");
		this.setSqlParameter(sb, map);
		sb.append(
				")\n" +
						"        select '00价格检查' as code,1 as flag, sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"        sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )shushi,\n" + 
						"        sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )tiaojie,\n" + 
						"        sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)jiabeipeichangjine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"        sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)lianshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"        sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )chufajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )bianjiajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"        sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"        from x --group by x.name\n" + 
						"        union all\n" + 
						"        select x.code,2as falg,\n" + 
						"        sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"        sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )shushi,\n" + 
						"        sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )tiaojie,\n" + 
						"        sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)jiabeipeichangjine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"        sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)lianshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"        sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )chufajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )bianjiajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"        sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"        from x group by x.code\n" + 
						"        union all\n" + 
						"        select substr(x.code,0,2)||'00',3 as flag,\n" + 
						"        sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"        sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )shushi,\n" + 
						"        sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )tiaojie,\n" + 
						"        sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)jiabeipeichangjine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"        sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)lianshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"        sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )chufajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )bianjiajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"        sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"        from x group by substr(x.code,0,2)||'00'\n" + 
						"        ) x\n" + 
						"      )x,  (select code , (case when substr(code,3,2)<>'00'then '　'||name\n" + 
						"      else name end) name  from dc_code.dc_12315_codedata where codetable='ZH20'\n" + 
						"      union all\n" + 
						"      select '00价格检查' code ,'价格检查'name from dual)j\n" + 
						"      where x.code=j.code(+)\n" + 
						"      order by code asc,subnum desc");
		return sb.toString();
	}
	private String getZhiShiChanQuan(Map<String, String> map) {
		StringBuffer sb=new StringBuffer();
		sb.append(
						"select nvl(j.name,x.code) as name,x.code ,\n" +
						"x.subnum,\n" + 
						"x.shushi,\n" + 
						"x.tiaojie,\n" + 
						"x.qizha,\n" + 
						"x.zhengyijine,\n" + 
						"x.wanhuijingjisunshi,\n" + 
						"x.jiabeipeichangjine,\n" + 
						"x.jingshenpeichangjine,\n" + 
						"x.lianshu,\n" + 
						"x.anzhi,\n" + 
						"x.weifasuode,\n" + 
						"x.chufajine,\n" + 
						"x.moshoujine,\n" + 
						"x.bianjiajine,\n" + 
						"x.jingjisunshizhi,\n" + 
						"x.daohuiwodian,\n" + 
						"x.chudongrenshu,\n" + 
						"x.chudongcheliang,\n" + 
						"x.shoushangrenshu,\n" + 
						"x.shouhairenshu,\n" + 
						"x.xishengrenshu from (\n" + 
						"select distinct --x.name,\n" + 
						"x.code,\n" + 
						"x.subnum,\n" + 
						"x.shushi,\n" + 
						"x.tiaojie,\n" + 
						"x.qizha,\n" + 
						"x.zhengyijine,\n" + 
						"x.wanhuijingjisunshi,\n" + 
						"x.jiabeipeichangjine,\n" + 
						"x.jingshenpeichangjine,\n" + 
						"x.lianshu,\n" + 
						"x.anzhi,\n" + 
						"x.weifasuode,\n" + 
						"x.chufajine,\n" + 
						"x.moshoujine,\n" + 
						"x.bianjiajine,\n" + 
						"x.jingjisunshizhi,\n" + 
						"x.daohuiwodian,\n" + 
						"x.chudongrenshu,\n" + 
						"x.chudongcheliang,\n" + 
						"x.shoushangrenshu,\n" + 
						"x.shouhairenshu,\n" + 
						"x.xishengrenshu from (\n" + 
						"with x as (\n" + 
						"\n" + 
						"        select i.finishtime, --完成时间\n" + 
						"               v.VISITCONFIRM, --回访情况\n" + 
						"               m.MEDIATE_RESULT, --调解结果\n" + 
						"               m.CHEATSIGN， --是否欺诈\n" + 
						"               m.DISAM, --争议金额\n" + 
						"               m.REDECOLOS, --挽回经济损失\n" + 
						"               m.DOUAMEAM, --加倍赔偿金额\n" + 
						"               m.SPIAMEAM, --精神赔偿金额\n" + 
						"               f.PUTONCASE, --是否立案\n" + 
						"               c.CASEVAL, --案值\n" + 
						"               p.illegincome, --违法所得\n" + 
						"               p.penam, --处罚金额\n" + 
						"               p.forfam, --没收金额\n" + 
						"               p.apprcuram, --变价金额\n" + 
						"               c.ECOLOVAL, --经济损失值\n" + 
						"               p.distoriedplace, --捣毁窝点\n" + 
						"               f.executeperson, --出动人数\n" + 
						"               f.executecar, -- 出动车辆\n" + 
						"               h.INJNUM, --受伤人数\n" + 
						"               d.VICTIMNUM, --受害人数\n" + 
						"               h.SACNUM, --牺牲人数\n" + 
						"               nvl(o.invobjtype,'9999999999') as code,\n" + 
						"               (case when i.regtime >= to_date(?, 'yyyy-mm-dd')\n" + 
						"                    and i.regtime <=\n" + 
						"               to_date(?, 'yyyy-mm-dd hh24:mi:ss')then 1 else 0 end) flag\n" + 
						"          from dc_cpr_infoware         i,\n" + 
						"               DC_CPR_RETURN_VISIT     v,\n" + 
						"               dc_cpr_feedback         f,\n" + 
						"               DC_CPR_MEDIATION        m,\n" + 
						"               dc_cpr_case_info        c,\n" + 
						"               DC_CPR_LEGAL_PUNISHMENT p,\n" + 
						"               DC_CPR_CASE_HANDLE      h,\n" + 
						"               DC_CPR_CONFIRMED_DAMAGE d,\n" + 
						"               dc_cpr_involved_object  o,\n" + 
						"               dc_cpr_involved_main    ma\n" + 
						"         where i.infowareid = v.infowareid(+)\n" + 
						"           and i.feedbackid = f.feedbackid\n" + 
						"           and f.mediationid = m.mediationid(+)\n" + 
						"           and f.caseinfoid = c.caseinfoid(+)\n" + 
						"           and c.LEGPUNID = p.legpunid(+)\n" + 
						"           and c.CASEHANDLEID = h.casehandleid(+)\n" + 
						"           and c.CONDAMID = d.condamid(+)\n" + 
						"           and o.invobjid = i.invobjid\n" + 
						"           and i.invmaiid = ma.invmaiid(+)\n" + 
						"           and o.businesstype ='ZH19'\n" + 
						"           and o.invobjtype is not null \n");
		this.setSqlParameter(sb, map);
		sb.append(
				")\n" +
						"        select '00知识产权' as code, sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"        sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )shushi,\n" + 
						"        sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )tiaojie,\n" + 
						"        sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)jiabeipeichangjine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"        sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)lianshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"        sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )chufajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )bianjiajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"        sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"        from x --group by x.name\n" + 
						"        union all\n" + 
						"        select x.code,\n" + 
						"        sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"        sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )shushi,\n" + 
						"        sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )tiaojie,\n" + 
						"        sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)jiabeipeichangjine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"        sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)lianshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"        sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )chufajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )bianjiajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"        sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"        from x group by x.code\n" + 
						"        union all\n" + 
						"        select substr(x.code,0,1)||'00',\n" + 
						"        sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"        sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )shushi,\n" + 
						"        sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )tiaojie,\n" + 
						"        sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)jiabeipeichangjine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"        sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)lianshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"        sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )chufajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )bianjiajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"        sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"        from x group by substr(x.code,0,1)||'00'\n" + 
						"        ) x\n" + 
						"      )x,  (select code , (case when substr(code,2,2)<>'00'then '　'||name\n" + 
						"      else name end) name  from dc_code.dc_12315_codedata where codetable='ZH19'\n" + 
						"      union all\n" + 
						"      select '00知识产权' code ,'知识产权'name from dual)j\n" + 
						"      where x.code=j.code(+)\n" + 
						"      order by code asc,subnum desc");
		return sb.toString();
	}
	private String getXiaoWeiHui(Map<String, String> map) {
		StringBuffer sb=new StringBuffer();
		sb.append(
				"select nvl(j.name,x.code) as name,x.code ,\n" +
						"x.subnum,\n" + 
						"x.shushi,\n" + 
						"x.tiaojie,\n" + 
						"x.qizha,\n" + 
						"x.zhengyijine,\n" + 
						"x.wanhuijingjisunshi,\n" + 
						"x.jiabeipeichangjine,\n" + 
						"x.jingshenpeichangjine,\n" + 
						"x.lianshu,\n" + 
						"x.anzhi,\n" + 
						"x.weifasuode,\n" + 
						"x.chufajine,\n" + 
						"x.moshoujine,\n" + 
						"x.bianjiajine,\n" + 
						"x.jingjisunshizhi,\n" + 
						"x.daohuiwodian,\n" + 
						"x.chudongrenshu,\n" + 
						"x.chudongcheliang,\n" + 
						"x.shoushangrenshu,\n" + 
						"x.shouhairenshu,\n" + 
						"x.xishengrenshu from (\n" + 
						"select distinct --x.name,\n" + 
						"x.code,\n" + 
						"x.subnum,\n" + 
						"x.shushi,\n" + 
						"x.tiaojie,\n" + 
						"x.qizha,\n" + 
						"x.zhengyijine,\n" + 
						"x.wanhuijingjisunshi,\n" + 
						"x.jiabeipeichangjine,\n" + 
						"x.jingshenpeichangjine,\n" + 
						"x.lianshu,\n" + 
						"x.anzhi,\n" + 
						"x.weifasuode,\n" + 
						"x.chufajine,\n" + 
						"x.moshoujine,\n" + 
						"x.bianjiajine,\n" + 
						"x.jingjisunshizhi,\n" + 
						"x.daohuiwodian,\n" + 
						"x.chudongrenshu,\n" + 
						"x.chudongcheliang,\n" + 
						"x.shoushangrenshu,\n" + 
						"x.shouhairenshu,\n" + 
						"x.xishengrenshu from (\n" + 
						"with x as (\n" + 
						"\n" + 
						"        select i.finishtime, --完成时间\n" + 
						"               v.VISITCONFIRM, --回访情况\n" + 
						"               m.MEDIATE_RESULT, --调解结果\n" + 
						"               m.CHEATSIGN， --是否欺诈\n" + 
						"               m.DISAM, --争议金额\n" + 
						"               m.REDECOLOS, --挽回经济损失\n" + 
						"               m.DOUAMEAM, --加倍赔偿金额\n" + 
						"               m.SPIAMEAM, --精神赔偿金额\n" + 
						"               f.PUTONCASE, --是否立案\n" + 
						"               c.CASEVAL, --案值\n" + 
						"               p.illegincome, --违法所得\n" + 
						"               p.penam, --处罚金额\n" + 
						"               p.forfam, --没收金额\n" + 
						"               p.apprcuram, --变价金额\n" + 
						"               c.ECOLOVAL, --经济损失值\n" + 
						"               p.distoriedplace, --捣毁窝点\n" + 
						"               f.executeperson, --出动人数\n" + 
						"               f.executecar, -- 出动车辆\n" + 
						"               h.INJNUM, --受伤人数\n" + 
						"               d.VICTIMNUM, --受害人数\n" + 
						"               h.SACNUM, --牺牲人数\n" + 
						"               nvl(o.invobjtype,'9999999999') as code,\n" + 
						"               (case when i.regtime >= to_date(?, 'yyyy-mm-dd')\n" + 
						"                    and i.regtime <=\n" + 
						"               to_date(?, 'yyyy-mm-dd hh24:mi:ss')then 1 else 0 end) flag\n" + 
						"          from dc_cpr_infoware         i,\n" + 
						"               DC_CPR_RETURN_VISIT     v,\n" + 
						"               dc_cpr_feedback         f,\n" + 
						"               DC_CPR_MEDIATION        m,\n" + 
						"               dc_cpr_case_info        c,\n" + 
						"               DC_CPR_LEGAL_PUNISHMENT p,\n" + 
						"               DC_CPR_CASE_HANDLE      h,\n" + 
						"               DC_CPR_CONFIRMED_DAMAGE d,\n" + 
						"               dc_cpr_involved_object  o,\n" + 
						"               dc_cpr_involved_main    ma\n" + 
						"         where i.infowareid = v.infowareid(+)\n" + 
						"           and i.feedbackid = f.feedbackid\n" + 
						"           and f.mediationid = m.mediationid(+)\n" + 
						"           and f.caseinfoid = c.caseinfoid(+)\n" + 
						"           and c.LEGPUNID = p.legpunid(+)\n" + 
						"           and c.CASEHANDLEID = h.casehandleid(+)\n" + 
						"           and c.CONDAMID = d.condamid(+)\n" + 
						"           and o.invobjid = i.invobjid\n" + 
						"           and i.invmaiid = ma.invmaiid(+)\n" + 
						"           and o.businesstype ='ZH18'\n" + 
						"           and o.invobjtype is not null \n");
		this.setSqlParameter(sb, map);
		sb.append(
				")\n" +
						"        select '00消委会' as code, sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"        sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )shushi,\n" + 
						"        sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )tiaojie,\n" + 
						"        sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)jiabeipeichangjine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"        sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)lianshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"        sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )chufajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )bianjiajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"        sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"        from x --group by x.name\n" + 
						"        union all\n" + 
						"        select x.code,\n" + 
						"        sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"        sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )shushi,\n" + 
						"        sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )tiaojie,\n" + 
						"        sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)jiabeipeichangjine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"        sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)lianshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"        sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )chufajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )bianjiajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"        sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"        from x group by x.code\n" + 
						"        union all\n" + 
						"        select substr(x.code,0,2)||'00000000',\n" + 
						"        sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"        sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )shushi,\n" + 
						"        sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )tiaojie,\n" + 
						"        sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)jiabeipeichangjine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"        sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)lianshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"        sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )chufajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )bianjiajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"        sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"        from x group by substr(x.code,0,2)||'00000000'\n" + 
						"        union all\n" + 
						"        select substr(x.code,0,4)||'000000',\n" + 
						"        sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"        sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )shushi,\n" + 
						"        sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )tiaojie,\n" + 
						"        sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)jiabeipeichangjine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"        sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)lianshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"        sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )chufajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )bianjiajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"        sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"        from x group by substr(x.code,0,4)||'000000'\n" + 
						"         union all\n" + 
						"        select substr(x.code,0,6)||'0000',\n" + 
						"        sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"        sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )shushi,\n" + 
						"        sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )tiaojie,\n" + 
						"        sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)jiabeipeichangjine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"        sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)lianshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"        sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )chufajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )bianjiajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"        sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"        from x group by substr(x.code,0,6)||'0000'\n" + 
						"         union all\n" + 
						"        select substr(x.code,0,8)||'00',\n" + 
						"        sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"        sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )shushi,\n" + 
						"        sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )tiaojie,\n" + 
						"        sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)jiabeipeichangjine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"        sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)lianshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"        sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )chufajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )bianjiajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"        sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"        from x group by substr(x.code,0,8)||'00'\n" + 
						"        ) x\n" + 
						"      )x,  (select code , (case when substr(code,9,2)<>'00'then '　　　　　'||name\n" + 
						"      when substr(code,7,4)<>'0000'then '　　　　'||name\n" + 
						"      when substr(code,5,6)<>'000000'then '　　　'||name\n" + 
						"      when substr(code,3,8)<>'00000000'then '　　'||name\n" + 
						"      else '　'||name end) name  from dc_code.dc_12315_codedata where codetable='ZH18'\n" + 
						"      union all\n" + 
						"      select '00消委会' code ,'消委会'name from dual)j\n" + 
						"      where x.code=j.code(+)\n" + 
						"      order by code asc,subnum desc");
		return sb.toString();
	}
	private String getGongShang(Map<String, String> map) {
		StringBuffer sb=new StringBuffer();
		sb.append(
				"select distinct nvl(j.name,x.code) as name ,x.code ,\n" +
						"x.subnum,\n" + 
						"x.shushi,\n" + 
						"x.tiaojie,\n" + 
						"x.qizha,\n" + 
						"x.zhengyijine,\n" + 
						"x.wanhuijingjisunshi,\n" + 
						"x.jiabeipeichangjine,\n" + 
						"x.jingshenpeichangjine,\n" + 
						"x.lianshu,\n" + 
						"x.anzhi,\n" + 
						"x.weifasuode,\n" + 
						"x.chufajine,\n" + 
						"x.moshoujine,\n" + 
						"x.bianjiajine,\n" + 
						"x.jingjisunshizhi,\n" + 
						"x.daohuiwodian,\n" + 
						"x.chudongrenshu,\n" + 
						"x.chudongcheliang,\n" + 
						"x.shoushangrenshu,\n" + 
						"x.shouhairenshu,\n" + 
						"x.xishengrenshu from (\n" + 
						"select distinct --x.name,\n" + 
						"x.code,\n" + 
						"x.subnum,\n" + 
						"x.shushi,\n" + 
						"x.tiaojie,\n" + 
						"x.qizha,\n" + 
						"x.zhengyijine,\n" + 
						"x.wanhuijingjisunshi,\n" + 
						"x.jiabeipeichangjine,\n" + 
						"x.jingshenpeichangjine,\n" + 
						"x.lianshu,\n" + 
						"x.anzhi,\n" + 
						"x.weifasuode,\n" + 
						"x.chufajine,\n" + 
						"x.moshoujine,\n" + 
						"x.bianjiajine,\n" + 
						"x.jingjisunshizhi,\n" + 
						"x.daohuiwodian,\n" + 
						"x.chudongrenshu,\n" + 
						"x.chudongcheliang,\n" + 
						"x.shoushangrenshu,\n" + 
						"x.shouhairenshu,\n" + 
						"x.xishengrenshu from (\n" + 
						"with x as (\n" + 
						"\n" + 
						"        select i.finishtime, --完成时间\n" + 
						"               v.VISITCONFIRM, --回访情况\n" + 
						"               m.MEDIATE_RESULT, --调解结果\n" + 
						"               m.CHEATSIGN， --是否欺诈\n" + 
						"               m.DISAM, --争议金额\n" + 
						"               m.REDECOLOS, --挽回经济损失\n" + 
						"               m.DOUAMEAM, --加倍赔偿金额\n" + 
						"               m.SPIAMEAM, --精神赔偿金额\n" + 
						"               f.PUTONCASE, --是否立案\n" + 
						"               c.CASEVAL, --案值\n" + 
						"               p.illegincome, --违法所得\n" + 
						"               p.penam, --处罚金额\n" + 
						"               p.forfam, --没收金额\n" + 
						"               p.apprcuram, --变价金额\n" + 
						"               c.ECOLOVAL, --经济损失值\n" + 
						"               p.distoriedplace, --捣毁窝点\n" + 
						"               f.executeperson, --出动人数\n" + 
						"               f.executecar, -- 出动车辆\n" + 
						"               h.INJNUM, --受伤人数\n" + 
						"               d.VICTIMNUM, --受害人数\n" + 
						"               h.SACNUM, --牺牲人数\n" + 
						"               nvl(o.invobjtype,'9999999999') as code,\n" + 
						"               (case when i.regtime >= to_date(?, 'yyyy-mm-dd')\n" + 
						"                    and i.regtime <=\n" + 
						"               to_date(?, 'yyyy-mm-dd hh24:mi:ss')then 1 else 0 end) flag\n" + 
						"          from dc_cpr_infoware         i,\n" + 
						"               DC_CPR_RETURN_VISIT     v,\n" + 
						"               dc_cpr_feedback         f,\n" + 
						"               DC_CPR_MEDIATION        m,\n" + 
						"               dc_cpr_case_info        c,\n" + 
						"               DC_CPR_LEGAL_PUNISHMENT p,\n" + 
						"               DC_CPR_CASE_HANDLE      h,\n" + 
						"               DC_CPR_CONFIRMED_DAMAGE d,\n" + 
						"               dc_cpr_involved_object  o,\n" + 
						"               dc_cpr_involved_main    ma\n" + 
						"         where i.infowareid = v.infowareid(+)\n" + 
						"           and i.feedbackid = f.feedbackid\n" + 
						"           and f.mediationid = m.mediationid(+)\n" + 
						"           and f.caseinfoid = c.caseinfoid(+)\n" + 
						"           and c.LEGPUNID = p.legpunid(+)\n" + 
						"           and c.CASEHANDLEID = h.casehandleid(+)\n" + 
						"           and c.CONDAMID = d.condamid(+)\n" + 
						"           and o.invobjid = i.invobjid\n" + 
						"           and i.invmaiid = ma.invmaiid(+)\n" + 
						"           and o.businesstype ='CH20'\n" + 
						"           and substr(o.invobjtype,0,1) in ('1','2')\n" + 
						"           and o.invobjtype is not null \n");
		this.setSqlParameter(sb, map);
		sb.append(
				")\n" +
						"        select '00工商' as code, sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"        sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )shushi,\n" + 
						"        sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )tiaojie,\n" + 
						"        sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)jiabeipeichangjine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"        sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)lianshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"        sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )chufajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )bianjiajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"        sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"        from x --group by x.name\n" + 
						"        union all\n" + 
						"        select x.code,\n" + 
						"        sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"        sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )shushi,\n" + 
						"        sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )tiaojie,\n" + 
						"        sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)jiabeipeichangjine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"        sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)lianshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"        sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )chufajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )bianjiajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"        sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"        from x group by x.code\n" + 
						"        union all\n" + 
						"        select substr(x.code,0,3)||'00000',\n" + 
						"        sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"        sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )shushi,\n" + 
						"        sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )tiaojie,\n" + 
						"        sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)jiabeipeichangjine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"        sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)lianshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"        sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )chufajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )bianjiajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"        sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"        from x group by substr(x.code,0,3)||'00000'\n" + 
						"        --substr(x.invobjtype,0,5)||'000'\n" + 
						"        union all\n" + 
						"        select substr(x.code,0,5)||'000',\n" + 
						"        sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"        sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )shushi,\n" + 
						"        sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )tiaojie,\n" + 
						"        sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)jiabeipeichangjine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"        sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)lianshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"        sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )chufajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )bianjiajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"        sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"        from x group by substr(x.code,0,5)||'000'\n" + 
						"        union all\n" + 
						"        select substr(x.code,0,1)||'0000000',\n" + 
						"        sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"        sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )shushi,\n" + 
						"        sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )tiaojie,\n" + 
						"        sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)jiabeipeichangjine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"        sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)lianshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"        sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )chufajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )bianjiajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"        sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"        from x group by substr(x.code,0,1)||'0000000'\n" + 
						"        ) x\n" + 
						"      )x,  (select code , (\n" + 
						"               case when substr(code,7,2)<>'00' then '　　　　'||name\n" + 
						"               when substr(code,6,3)<>'000'   then '　　　'||name\n" + 
						"                when substr(code,4,5)<>'00000' or substr(code,6,3)='900' then '　　'||name\n" + 
						"                when substr(code,2,7)<>'0000000'then '　'||name\n" + 
						"                else ''||name end) name  from dc_code.dc_12315_codedata where codetable='CH20'\n" + 
						"                union all\n" + 
						"                select '00工商' code,'工商' name from dual\n" + 
						"                union all\n" + 
						"                select '10000000' code,'商品' name from dual\n" + 
						"                union all\n" + 
						"                select '20000000' code ,'服务' name from dual)j\n" + 
						"      where x.code=j.code(+)\n" + 
						"      order by code asc,subnum desc");
		return sb.toString();
	}
	
	private StringBuffer setSqlParameter(StringBuffer sb,Map<String,String> map){
		if (map.get("dengjibumen")!=null &&map.get("dengjibumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("dengjibumen"),"i.REGDEPCODE"));
		}
		
		if (map.get("chengbanbumen")!=null &&map.get("chengbanbumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("chengbanbumen"),"i.HANDEPCODE"));
		}
		
		if (map.get("wentileixing")!=null &&map.get("wentileixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("wentileixing"),"i.APPLBASQUE"));
		}
		if (map.get("zhutileixing")!=null &&map.get("zhutileixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("zhutileixing"),"ma.PTTYPE"));
		}
		if (map.get("hangyeleixing")!=null &&map.get("hangyeleixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("hangyeleixing"),"ma.TRADETYPE"));
		}
		if (map.get("weifazhonglei")!=null &&map.get("weifazhonglei").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("weifazhonglei"),"p.ILLEGACTTYPE"));
		}
		if (map.get("xingzhengchufa")!=null &&map.get("xingzhengchufa").length()!=0) {
			sb.append("and p.COMMEATYPE =? \n ");
		}
		if (map.get("zhixingleibie")!=null &&map.get("zhixingleibie").length()!=0) {
			sb.append("and h.EXESORT =? \n ");
		}
		if (map.get("jieshoufangshi")!=null &&map.get("jieshoufangshi").length()!=0) {
			sb.append("and i.INCFORM =? \n ");
		}
		if (map.get("infotype")!=null &&map.get("infotype").length()!=0) {
			sb.append("and i.INFTYPE =? \n ");
		}
		if (map.get("wangzhanleixing")!=null &&map.get("wangzhanleixing").length()!=0) {
			sb.append("and ma.WEBSITETYPE =? \n ");
		}
		if (map.get("qinquanleixing")!=null &&map.get("qinquanleixing").length()!=0) {
			sb.append("and m.TORTYPE =? \n ");
		}
		if (map.get("yiwuleixing")!=null &&map.get("yiwuleixing").length()!=0) {
			sb.append("and m.DEFOBL =? \n ");
		}
		if (map.get("chufazhonglei")!=null &&map.get("chufazhonglei").length()!=0) {
			sb.append("and p.Pentype =? \n ");
		}
		return sb;
	}
	public List<Map> yiWuLeiXing(Map<String, String> map, int i,
			HttpServletRequest req) {
		List list=new ArrayList();
		StringBuffer sb=new StringBuffer();
		List<Map> res=new ArrayList<Map>();
		sb.append(
				"with x as (\n" +
						"\n" + 
						"        select i.finishtime, --完成时间\n" + 
						"               v.VISITCONFIRM, --回访情况\n" + 
						"               m.MEDIATE_RESULT, --调解结果\n" + 
						"               m.CHEATSIGN， --是否欺诈\n" + 
						"               m.DISAM, --争议金额\n" + 
						"               m.REDECOLOS, --挽回经济损失\n" + 
						"               m.DOUAMEAM, --加倍赔偿金额\n" + 
						"               m.SPIAMEAM, --精神赔偿金额\n" + 
						"               f.PUTONCASE, --是否立案\n" + 
						"               c.CASEVAL, --案值\n" + 
						"               p.illegincome, --违法所得\n" + 
						"               p.penam, --处罚金额\n" + 
						"               p.forfam, --没收金额\n" + 
						"               p.apprcuram, --变价金额\n" + 
						"               c.ECOLOVAL, --经济损失值\n" + 
						"               p.distoriedplace, --捣毁窝点\n" + 
						"               f.executeperson, --出动人数\n" + 
						"               f.executecar, -- 出动车辆\n" + 
						"               h.INJNUM, --受伤人数\n" + 
						"               d.VICTIMNUM, --受害人数\n" + 
						"               h.SACNUM, --牺牲人数\n" + 
						"               nvl(m.DEFOBL,'9999') as code,\n" + 
						"               j.name as name,\n" + 
						"               (case when i.regtime >= to_date(?, 'yyyy-mm-dd')\n" + 
						"                    and i.regtime <=\n" + 
						"               to_date(?, 'yyyy-mm-dd hh24:mi:ss')then 1 else 0 end) flag,\n" + 
						"               (case when i.regtime >= to_date(?, 'yyyy-mm-dd')\n" + 
						"                    and i.regtime <=\n" + 
						"               to_date(?, 'yyyy-mm-dd hh24:mi:ss')then 1 else 0 end) tflag,\n" + 
						"               (case when i.regtime >= to_date(?, 'yyyy-mm-dd')\n" + 
						"                    and i.regtime <=\n" + 
						"               to_date(?, 'yyyy-mm-dd hh24:mi:ss')then 1 else 0 end) hflag\n" + 
						"          from dc_cpr_infoware         i,\n" + 
						"               DC_CPR_RETURN_VISIT     v,\n" + 
						"               dc_cpr_feedback         f,\n" + 
						"               DC_CPR_MEDIATION        m,\n" + 
						"               dc_cpr_case_info        c,\n" + 
						"               DC_CPR_LEGAL_PUNISHMENT p,\n" + 
						"               DC_CPR_CASE_HANDLE      h,\n" + 
						"               DC_CPR_CONFIRMED_DAMAGE d,\n" + 
						"               dc_cpr_involved_object  o,\n" + 
						"               dc_cpr_involved_main    ma,\n" + 
						"               (select j.code code ,--j.parentcode as parentcode\n" + 
						"              j.name as name\n" + 
						"                from dc_code.dc_12315_codedata j where j.codetable='CH53') j\n" + 
						"         where i.infowareid = v.infowareid(+)\n" + 
						"           and i.feedbackid = f.feedbackid\n" + 
						"           and f.mediationid = m.mediationid\n" + 
						"           and f.caseinfoid = c.caseinfoid(+)\n" + 
						"           and c.LEGPUNID = p.legpunid(+)\n" + 
						"           and c.CASEHANDLEID = h.casehandleid(+)\n" + 
						"           and c.CONDAMID = d.condamid(+)\n" + 
						"           and o.invobjid(+) = i.invobjid\n" + 
						"           and i.invmaiid = ma.invmaiid(+)\n" + 
						"           and m.DEFOBL=j.code（+） \n");
		list.add(map.get("begintime"));
		list.add(map.get("endtime")+" 23:59:59");
		list.add(QueryJieAnUtils.getTongBiDate(map.get("begintime")));
		list.add(QueryJieAnUtils.getTongBiDate(map.get("endtime"))+" 23:59:59");
		if (map.get("hbegintime")!=null&&map.get("hbegintime").length()!=0) {
			list.add(map.get("hbegintime"));
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(map.get("begintime")));
		}
		if (map.get("hendtime")!=null&&map.get("hendtime").length()!=0) {
			list.add(map.get("hendtime")+" 23:59:59");
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(map.get("endtime"))+" 23:59:59");
		}
		if (map.get("dengjibumen")!=null &&map.get("dengjibumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("dengjibumen"),"i.REGDEPCODE"));
		}
		
		if (map.get("chengbanbumen")!=null &&map.get("chengbanbumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("chengbanbumen"),"i.HANDEPCODE"));
		}
		
		if (map.get("wentileixing")!=null &&map.get("wentileixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("wentileixing")," i.APPLBASQUE"));
		}
		
		if (map.get("ketileixing")!=null &&map.get("ketileixing").length()!=0) {
			sb.append(QueryJieAnUtils.UtilOfObjectTypeS(map.get("ketileixing"),"o.invobjtype"));
			sb.append(QueryJieAnUtils.UtilOfObjectTypeB(map.get("ketileixing"),"o.BUSINESSTYPE"));
		}
		
		if (map.get("zhutileixing")!=null &&map.get("zhutileixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("zhutileixing"),"ma.PTTYPE"));
		}
		
		if (map.get("hangyeleixing")!=null &&map.get("hangyeleixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("hangyeleixing"),"ma.TRADETYPE"));
		}
		
		if (map.get("weifazhonglei")!=null &&map.get("weifazhonglei").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("weifazhonglei"),"p.ILLEGACTTYPE"));
		}
		
		if (map.get("xingzhengchufa")!=null &&map.get("xingzhengchufa").length()!=0) {
			sb.append("and p.COMMEATYPE =? \n ");
			list.add(map.get("xingzhengchufa"));
		}
		
		if (map.get("zhixingleibie")!=null &&map.get("zhixingleibie").length()!=0) {
			sb.append("and h.EXESORT =? \n ");
			list.add(map.get("zhixingleibie"));
		}
		
		if (map.get("jieshoufangshi")!=null &&map.get("jieshoufangshi").length()!=0) {
			sb.append("and i.INCFORM =? \n ");
			list.add(map.get("jieshoufangshi"));
		}
		
		if (map.get("infotype")!=null &&map.get("infotype").length()!=0) {
			sb.append("and i.INFTYPE =? \n ");
			list.add(map.get("infotype"));
		}
		
		if (map.get("wangzhanleixing")!=null &&map.get("wangzhanleixing").length()!=0) {
			sb.append("and ma.WEBSITETYPE =? \n ");
			list.add(map.get("wangzhanleixing"));
		}
		
		if (map.get("qinquanleixing")!=null &&map.get("qinquanleixing").length()!=0) {
			sb.append("and m.TORTYPE =? \n ");
			list.add(map.get("qinquanleixing"));
		}
		
		/*if (map.get("yiwuleixing")!=null &&map.get("yiwuleixing").length()!=0) {
			sb.append("and m.DEFOBL =? \n ");
			list.add(map.get("yiwuleixing"));
		}*/
		if (map.get("chufazhonglei")!=null &&map.get("chufazhonglei").length()!=0) {
			sb.append("and p.Pentype =? \n ");
			list.add(map.get("chufazhonglei"));
		}
		sb.append(
				")\n" +
						"        select x.name,x.code,\n" + 
						"        sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"        sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )shushi,\n" + 
						"        sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )tiaojie,\n" + 
						"        sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)jiabeipeichangjine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"        sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)lianshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"        sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )chufajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )bianjiajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"        sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"        from x group by x.name,x.code\n" + 
						"\n" + 
						"\n" + 
						"        union all\n" + 
						"        select '上一段时间段数据'as name,'999901' as code,\n" + 
						"        sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"        sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) shushi,\n" + 
						"        sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ) tiaojie,\n" + 
						"        sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end) jiabeipeichangjine,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"        sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end) lianshu,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end ) chufajine,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end ) bianjiajine,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"        from x\n" + 
						"        union all\n" + 
						"         select '环比增减（%）'as name,'999902' as code,\n" + 
						"        round((case when sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end)=0 and\n" + 
						"          sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)-\n" + 
						"          sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end))/\n" + 
						"          sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end) end ),4)*100 subnum,\n" + 
						"        round((case when sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )=0\n" + 
						"        and  sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) =0 then 0\n" + 
						"        when sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )-\n" + 
						"          sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ))/\n" + 
						"          sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) end),4)*100 shushi,\n" + 
						"\n" + 
						"        round((case when sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0\n" + 
						"              and  sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0 then 0\n" + 
						"         when sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0\n" + 
						"           and sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )<>0 then 1 else\n" + 
						"           (sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )-\n" + 
						"           sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ))/\n" + 
						"           sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ) end ),4)*100 tiaojie,\n" + 
						"        round(( case when sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 and\n" + 
						"          sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 then 0\n" + 
						"          when sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 and\n" + 
						"            sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)<>0 then 1 else\n" + 
						"            (sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)-\n" + 
						"            sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end))/\n" + 
						"            sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end) end ),4)*100 qizha,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end )=0 and\n" + 
						"              sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) =0 then 0\n" + 
						"             when sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end )=0\n" + 
						"              and sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end )<>0 then 1\n" + 
						"             else(sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end )-\n" + 
						"             sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end ))/\n" + 
						"             sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end ) end  ),4)*100 zhengyijine,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end)=0\n" + 
						"              and sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)=0 then 0\n" + 
						"              when sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end)=0\n" + 
						"              and sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)<>0 then 1\n" + 
						"              else(sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)-\n" + 
						"              sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end))/\n" + 
						"              sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end) end),4)*100 wanhuijingjisunshi,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end)=0 and\n" + 
						"              sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end) =0 then 0\n" + 
						"             when sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end)=0 and\n" + 
						"             sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)<>0 then 1\n" + 
						"             else (sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)-\n" + 
						"             sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end))/\n" + 
						"             sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end) end),4)*100 jiabeipeichangjine,\n" + 
						"       round( (case when sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)<>0 then 1\n" + 
						"          else(sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end) end),4)*100 jingshenpeichangjine,\n" + 
						"        round((case when sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end) <> 0 then 1 else\n" + 
						"          (sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)-\n" + 
						"          sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end))/\n" + 
						"          sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end) end),4)*100lianshu,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end )=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) <>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end )-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end ))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end ) end),4)*100 anzhi,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end)=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end) =0 and\n" + 
						"          sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end) end),4)*100 weifasuode,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end )=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.penam,0) else 0 end ) <>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end ))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end ) end),4)*100 chufajine,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end)=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end)=0 and\n" + 
						"          sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) <>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end)-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end) end),4)*100 moshoujine,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end )=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )<>0 then 1\n" + 
						"          else(sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end ))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end ) end),4)*100 bianjiajine,\n" + 
						"       round( (case when sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0  then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)<>0 then 1\n" + 
						"          else (sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)-\n" + 
						"            sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end))/\n" + 
						"            sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end) end),4)*100 jingjisunshizhi,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)<>0 then 1\n" + 
						"          else (sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)-\n" + 
						"            sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end))/\n" + 
						"            sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end) end),4)*100 daohuiwodian,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end ))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end ) end),4)*100chudongrenshu,\n" + 
						"       round( (case when sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end)=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end) end),4)*100 chudongcheliang,\n" + 
						"       round( (case when sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end ))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end ) end),4)*100 shoushangrenshu,\n" + 
						"       round( (case when sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end) end),4)*100 shouhairenshu,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) =0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end )<>0 then 1\n" + 
						"          else(sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end )-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end ))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end ) end),4)*100 xishengrenshu\n" + 
						"        from x\n" + 
						"    union all\n" + 
						"        select '去年同期数据'as name,'999903' as code,\n" + 
						"        sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"        sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) shushi,\n" + 
						"        sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ) tiaojie,\n" + 
						"        sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end) jiabeipeichangjine,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"        sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end) lianshu,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end ) chufajine,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end ) bianjiajine,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end ) chudongrenshu,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"        from x\n" + 
						"        union all\n" + 
						"          select '同比增减（%）'as name,'999904' as code,\n" + 
						"        round((case when sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end)=0 and\n" + 
						"          sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)-\n" + 
						"          sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end))/\n" + 
						"          sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end) end ),4)*100 subnum,\n" + 
						"        round((case when sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )=0\n" + 
						"        and  sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) =0 then 0\n" + 
						"        when sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )-\n" + 
						"          sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ))/\n" + 
						"          sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) end),4)*100 shushi,\n" + 
						"        round((case when sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0\n" + 
						"              and  sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0 then 0\n" + 
						"         when sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0\n" + 
						"           and sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )<>0 then 1 else\n" + 
						"           (sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )-\n" + 
						"           sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ))/\n" + 
						"           sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ) end ),4)*100 tiaojie,\n" + 
						"        round(( case when sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 and\n" + 
						"          sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 then 0\n" + 
						"          when sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 and\n" + 
						"            sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)<>0 then 1 else\n" + 
						"            (sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)-\n" + 
						"            sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end))/\n" + 
						"            sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end) end ),4)*100 qizha,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end )=0 and\n" + 
						"              sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) =0 then 0\n" + 
						"             when sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end )=0\n" + 
						"              and sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end )<>0 then 1\n" + 
						"             else(sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end )-\n" + 
						"             sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end ))/\n" + 
						"             sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end ) end  ),4)*100zhengyijine,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end)=0\n" + 
						"              and sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)=0 then 0\n" + 
						"              when sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end)=0\n" + 
						"              and sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)<>0 then 1\n" + 
						"              else(sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)-\n" + 
						"              sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end))/\n" + 
						"              sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end) end),4)*100 wanhuijingjisunshi,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end)=0 and\n" + 
						"              sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end) =0 then 0\n" + 
						"             when sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end)=0 and\n" + 
						"             sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)<>0 then 1\n" + 
						"             else (sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)-\n" + 
						"             sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end))/\n" + 
						"             sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end) end),4)*100 jiabeipeichangjine,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)<>0 then 1\n" + 
						"          else(sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end) end),4)*100 jingshenpeichangjine,\n" + 
						"        round((case when sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end) <> 0 then 1 else\n" + 
						"          (sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)-\n" + 
						"          sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end))/\n" + 
						"          sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end) end),4)*100lianshu,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end )=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) <>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end )-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end ))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end ) end),4)*100 anzhi,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end)=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end) =0 and\n" + 
						"          sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end) end),4)*100 weifasuode,\n" + 
						"       round( (case when sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end )=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.penam,0) else 0 end ) <>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end ))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end ) end),4)*100 chufajine,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end)=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end)=0 and\n" + 
						"          sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) <>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end)-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end) end),4)*100 moshoujine,\n" + 
						"       round( (case when sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end )=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )<>0 then 1\n" + 
						"          else(sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end ))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end ) end),4)*100 bianjiajine,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0  then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)<>0 then 1\n" + 
						"          else (sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)-\n" + 
						"            sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end))/\n" + 
						"            sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end) end),4)*100 jingjisunshizhi,\n" + 
						"       round( (case when sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)<>0 then 1\n" + 
						"          else (sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)-\n" + 
						"            sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end))/\n" + 
						"            sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end) end),4)*100 daohuiwodian,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end ))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end ) end),4)*100 chudongrenshu,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end)=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end) end),4)*100 chudongcheliang,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end ))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end ) end),4)*100 shoushangrenshu,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end) end),4)*100 shouhairenshu,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) =0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end )<>0 then 1\n" + 
						"          else(sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end )-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end ))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end ) end),4)*100 xishengrenshu\n" + 
						"        from x\n" + 
						"          order by code asc,name desc");
		try {
			res=dao.queryForList(sb.toString(),list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("结案信息申诉举报未履行义务统计报表", "WDY", i == 1 ? "查看报表" : "下载报表", sb.toString(),
				map.get("begintime")+","+map.get("endtime"), req);
		return res;
	}
	public List<Map> xiaoFeiShiJian(Map<String, String> map, int i,
			HttpServletRequest req) {
		List list=new ArrayList();
		StringBuffer sb=new StringBuffer();
		List<Map> res=new ArrayList<Map>();
		sb.append(
				"with x as (\n" +
						"        select /*+ parallel(8)*/  i.finishtime, --完成时间\n" + 
						"               v.VISITCONFIRM, --回访情况\n" + 
						"               m.MEDIATE_RESULT, --调解结果\n" + 
						"               m.CHEATSIGN， --是否欺诈\n" + 
						"               m.DISAM, --争议金额\n" + 
						"               m.REDECOLOS, --挽回经济损失\n" + 
						"               m.DOUAMEAM, --加倍赔偿金额\n" + 
						"               m.SPIAMEAM, --精神赔偿金额\n" + 
						"               f.PUTONCASE, --是否立案\n" + 
						"               c.CASEVAL, --案值\n" + 
						"               p.illegincome, --违法所得\n" + 
						"               p.penam, --处罚金额\n" + 
						"               p.forfam, --没收金额\n" + 
						"               p.apprcuram, --变价金额\n" + 
						"               c.ECOLOVAL, --经济损失值\n" + 
						"               p.distoriedplace, --捣毁窝点\n" + 
						"               f.executeperson, --出动人数\n" + 
						"               f.executecar, -- 出动车辆\n" + 
						"               h.INJNUM, --受伤人数\n" + 
						"               d.VICTIMNUM, --受害人数\n" + 
						"               h.SACNUM, --牺牲人数\n" + 
						"               nvl(comp.CONSECOCCTYPE,'9999') as code,\n" + 
						"               j.name as name,\n" + 
						"               (case when i.regtime >= to_date(?, 'yyyy-mm-dd')\n" + 
						"                    and i.regtime <=\n" + 
						"               to_date(?, 'yyyy-mm-dd hh24:mi:ss')then 1 else 0 end) flag,\n" + 
						"               (case when i.regtime >= to_date(?, 'yyyy-mm-dd')\n" + 
						"                    and i.regtime <=\n" + 
						"               to_date(?, 'yyyy-mm-dd hh24:mi:ss')then 1 else 0 end) tflag,\n" + 
						"               (case when i.regtime >= to_date(?, 'yyyy-mm-dd')\n" + 
						"                    and i.regtime <=\n" + 
						"               to_date(?, 'yyyy-mm-dd hh24:mi:ss')then 1 else 0 end) hflag\n" + 
						"          from dc_cpr_infoware         i,\n" + 
						"               DC_CPR_RETURN_VISIT     v,\n" + 
						"               dc_cpr_feedback         f,\n" + 
						"               DC_CPR_MEDIATION        m,\n" + 
						"               dc_cpr_case_info        c,\n" + 
						"               DC_CPR_LEGAL_PUNISHMENT p,\n" + 
						"               DC_CPR_CASE_HANDLE      h,\n" + 
						"               DC_CPR_CONFIRMED_DAMAGE d,\n" + 
						"               dc_cpr_involved_object  o,\n" + 
						"               dc_cpr_involved_main    ma,\n" + 
						"               DC_CPR_COMPLAINT comp,\n" + 
						"               (select j.code code ,--j.parentcode as parentcode\n" + 
						"              j.name as name\n" + 
						"                from dc_code.dc_12315_codedata j where j.codetable='CH32') j\n" + 
						"         where i.infowareid = v.infowareid(+)\n" + 
						"           and i.feedbackid = f.feedbackid\n" + 
						"           and f.mediationid = m.mediationid(+)\n" + 
						"           and f.caseinfoid = c.caseinfoid(+)\n" + 
						"           and c.LEGPUNID = p.legpunid(+)\n" + 
						"           and c.CASEHANDLEID = h.casehandleid(+)\n" + 
						"           and c.CONDAMID = d.condamid(+)\n" + 
						"           and o.invobjid(+) = i.invobjid\n" + 
						"           and i.invmaiid = ma.invmaiid(+)\n" + 
						"           and comp.CONSECOCCTYPE=j.code（+）\n" + 
						"           and i.Complaintid=comp.complaintid\n");
		list.add(map.get("begintime"));
		list.add(map.get("endtime")+" 23:59:59");
		list.add(QueryJieAnUtils.getTongBiDate(map.get("begintime")));
		list.add(QueryJieAnUtils.getTongBiDate(map.get("endtime"))+" 23:59:59");
		if (map.get("hbegintime")!=null&&map.get("hbegintime").length()!=0) {
			list.add(map.get("hbegintime"));
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(map.get("begintime")));
		}
		if (map.get("hendtime")!=null&&map.get("hendtime").length()!=0) {
			list.add(map.get("hendtime")+" 23:59:59");
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(map.get("endtime"))+" 23:59:59");
		}
		if (map.get("dengjibumen")!=null &&map.get("dengjibumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("dengjibumen"),"i.REGDEPCODE"));
		}
		
		if (map.get("chengbanbumen")!=null &&map.get("chengbanbumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("chengbanbumen"),"i.HANDEPCODE"));
		}
		
		if (map.get("wentileixing")!=null &&map.get("wentileixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("wentileixing")," i.APPLBASQUE"));
		}
		
		if (map.get("ketileixing")!=null &&map.get("ketileixing").length()!=0) {
			sb.append(QueryJieAnUtils.UtilOfObjectTypeS(map.get("ketileixing"),"o.invobjtype"));
			sb.append(QueryJieAnUtils.UtilOfObjectTypeB(map.get("ketileixing"),"o.BUSINESSTYPE"));
		}
		
		if (map.get("zhutileixing")!=null &&map.get("zhutileixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("zhutileixing"),"ma.PTTYPE"));
		}
		
		if (map.get("hangyeleixing")!=null &&map.get("hangyeleixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("hangyeleixing"),"ma.TRADETYPE"));
		}
		
		if (map.get("weifazhonglei")!=null &&map.get("weifazhonglei").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("weifazhonglei"),"p.ILLEGACTTYPE"));
		}
		
		if (map.get("xingzhengchufa")!=null &&map.get("xingzhengchufa").length()!=0) {
			sb.append("and p.COMMEATYPE =? \n ");
			list.add(map.get("xingzhengchufa"));
		}
		
		if (map.get("zhixingleibie")!=null &&map.get("zhixingleibie").length()!=0) {
			sb.append("and h.EXESORT =? \n ");
			list.add(map.get("zhixingleibie"));
		}
		
		if (map.get("jieshoufangshi")!=null &&map.get("jieshoufangshi").length()!=0) {
			sb.append("and i.INCFORM =? \n ");
			list.add(map.get("jieshoufangshi"));
		}
		
		if (map.get("infotype")!=null &&map.get("infotype").length()!=0) {
			sb.append("and i.INFTYPE =? \n ");
			list.add(map.get("infotype"));
		}
		
		if (map.get("wangzhanleixing")!=null &&map.get("wangzhanleixing").length()!=0) {
			sb.append("and ma.WEBSITETYPE =? \n ");
			list.add(map.get("wangzhanleixing"));
		}
		
		if (map.get("qinquanleixing")!=null &&map.get("qinquanleixing").length()!=0) {
			sb.append("and m.TORTYPE =? \n ");
			list.add(map.get("qinquanleixing"));
		}
		
		if (map.get("yiwuleixing")!=null &&map.get("yiwuleixing").length()!=0) {
			sb.append("and m.DEFOBL =? \n ");
			list.add(map.get("yiwuleixing"));
		}
		if (map.get("chufazhonglei")!=null &&map.get("chufazhonglei").length()!=0) {
			sb.append("and p.Pentype =? \n ");
			list.add(map.get("chufazhonglei"));
		}
		sb.append(
				")\n" +
						"        select x.name,x.code,\n" + 
						"        sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"        sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )shushi,\n" + 
						"        sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )tiaojie,\n" + 
						"        sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)jiabeipeichangjine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"        sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)lianshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"        sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )chufajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )bianjiajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"        sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"        from x group by x.name,x.code\n" + 
						"\n" + 
						"\n" + 
						"        union all\n" + 
						"        select '上一段时间段数据'as name,'999901' as code,\n" + 
						"        sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"        sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) shushi,\n" + 
						"        sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ) tiaojie,\n" + 
						"        sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end) jiabeipeichangjine,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"        sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end) lianshu,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end ) chufajine,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end ) bianjiajine,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"        from x\n" + 
						"        union all\n" + 
						"         select '环比增减（%）'as name,'999902' as code,\n" + 
						"        round((case when sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end)=0 and\n" + 
						"          sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)-\n" + 
						"          sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end))/\n" + 
						"          sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end) end ),4)*100 subnum,\n" + 
						"        round((case when sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )=0\n" + 
						"        and  sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) =0 then 0\n" + 
						"        when sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )-\n" + 
						"          sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ))/\n" + 
						"          sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) end),4)*100 shushi,\n" + 
						"\n" + 
						"        round((case when sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0\n" + 
						"              and  sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0 then 0\n" + 
						"         when sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0\n" + 
						"           and sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )<>0 then 1 else\n" + 
						"           (sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )-\n" + 
						"           sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ))/\n" + 
						"           sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ) end ),4)*100 tiaojie,\n" + 
						"        round(( case when sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 and\n" + 
						"          sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 then 0\n" + 
						"          when sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 and\n" + 
						"            sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)<>0 then 1 else\n" + 
						"            (sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)-\n" + 
						"            sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end))/\n" + 
						"            sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end) end ),4)*100 qizha,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end )=0 and\n" + 
						"              sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) =0 then 0\n" + 
						"             when sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end )=0\n" + 
						"              and sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end )<>0 then 1\n" + 
						"             else(sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end )-\n" + 
						"             sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end ))/\n" + 
						"             sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end ) end  ),4)*100 zhengyijine,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end)=0\n" + 
						"              and sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)=0 then 0\n" + 
						"              when sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end)=0\n" + 
						"              and sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)<>0 then 1\n" + 
						"              else(sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)-\n" + 
						"              sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end))/\n" + 
						"              sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end) end),4)*100 wanhuijingjisunshi,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end)=0 and\n" + 
						"              sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end) =0 then 0\n" + 
						"             when sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end)=0 and\n" + 
						"             sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)<>0 then 1\n" + 
						"             else (sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)-\n" + 
						"             sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end))/\n" + 
						"             sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end) end),4)*100 jiabeipeichangjine,\n" + 
						"       round( (case when sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)<>0 then 1\n" + 
						"          else(sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end) end),4)*100 jingshenpeichangjine,\n" + 
						"        round((case when sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end) <> 0 then 1 else\n" + 
						"          (sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)-\n" + 
						"          sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end))/\n" + 
						"          sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end) end),4)*100lianshu,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end )=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) <>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end )-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end ))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end ) end),4)*100 anzhi,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end)=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end) =0 and\n" + 
						"          sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end) end),4)*100 weifasuode,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end )=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.penam,0) else 0 end ) <>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end ))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end ) end),4)*100 chufajine,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end)=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end)=0 and\n" + 
						"          sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) <>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end)-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end) end),4)*100 moshoujine,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end )=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )<>0 then 1\n" + 
						"          else(sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end ))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end ) end),4)*100 bianjiajine,\n" + 
						"       round( (case when sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0  then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)<>0 then 1\n" + 
						"          else (sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)-\n" + 
						"            sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end))/\n" + 
						"            sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end) end),4)*100 jingjisunshizhi,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)<>0 then 1\n" + 
						"          else (sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)-\n" + 
						"            sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end))/\n" + 
						"            sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end) end),4)*100 daohuiwodian,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end ))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end ) end),4)*100chudongrenshu,\n" + 
						"       round( (case when sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end)=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end) end),4)*100 chudongcheliang,\n" + 
						"       round( (case when sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end ))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end ) end),4)*100 shoushangrenshu,\n" + 
						"       round( (case when sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end) end),4)*100 shouhairenshu,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) =0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end )<>0 then 1\n" + 
						"          else(sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end )-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end ))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end ) end),4)*100 xishengrenshu\n" + 
						"        from x\n" + 
						"    union all\n" + 
						"        select '去年同期数据'as name,'999903' as code,\n" + 
						"        sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"        sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) shushi,\n" + 
						"        sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ) tiaojie,\n" + 
						"        sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end) jiabeipeichangjine,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"        sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end) lianshu,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end ) chufajine,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end ) bianjiajine,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end ) chudongrenshu,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"        from x\n" + 
						"        union all\n" + 
						"          select '同比增减（%）'as name,'999904' as code,\n" + 
						"        round((case when sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end)=0 and\n" + 
						"          sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)-\n" + 
						"          sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end))/\n" + 
						"          sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end) end ),4)*100 subnum,\n" + 
						"        round((case when sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )=0\n" + 
						"        and  sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) =0 then 0\n" + 
						"        when sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )-\n" + 
						"          sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ))/\n" + 
						"          sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) end),4)*100 shushi,\n" + 
						"        round((case when sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0\n" + 
						"              and  sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0 then 0\n" + 
						"         when sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0\n" + 
						"           and sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )<>0 then 1 else\n" + 
						"           (sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )-\n" + 
						"           sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ))/\n" + 
						"           sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ) end ),4)*100 tiaojie,\n" + 
						"        round(( case when sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 and\n" + 
						"          sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 then 0\n" + 
						"          when sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 and\n" + 
						"            sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)<>0 then 1 else\n" + 
						"            (sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)-\n" + 
						"            sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end))/\n" + 
						"            sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end) end ),4)*100 qizha,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end )=0 and\n" + 
						"              sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) =0 then 0\n" + 
						"             when sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end )=0\n" + 
						"              and sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end )<>0 then 1\n" + 
						"             else(sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end )-\n" + 
						"             sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end ))/\n" + 
						"             sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end ) end  ),4)*100zhengyijine,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end)=0\n" + 
						"              and sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)=0 then 0\n" + 
						"              when sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end)=0\n" + 
						"              and sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)<>0 then 1\n" + 
						"              else(sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)-\n" + 
						"              sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end))/\n" + 
						"              sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end) end),4)*100 wanhuijingjisunshi,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end)=0 and\n" + 
						"              sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end) =0 then 0\n" + 
						"             when sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end)=0 and\n" + 
						"             sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)<>0 then 1\n" + 
						"             else (sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)-\n" + 
						"             sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end))/\n" + 
						"             sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end) end),4)*100 jiabeipeichangjine,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)<>0 then 1\n" + 
						"          else(sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end) end),4)*100 jingshenpeichangjine,\n" + 
						"        round((case when sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end) <> 0 then 1 else\n" + 
						"          (sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)-\n" + 
						"          sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end))/\n" + 
						"          sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end) end),4)*100lianshu,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end )=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) <>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end )-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end ))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end ) end),4)*100 anzhi,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end)=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end) =0 and\n" + 
						"          sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end) end),4)*100 weifasuode,\n" + 
						"       round( (case when sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end )=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.penam,0) else 0 end ) <>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end ))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end ) end),4)*100 chufajine,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end)=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end)=0 and\n" + 
						"          sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) <>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end)-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end) end),4)*100 moshoujine,\n" + 
						"       round( (case when sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end )=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )<>0 then 1\n" + 
						"          else(sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end ))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end ) end),4)*100 bianjiajine,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0  then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)<>0 then 1\n" + 
						"          else (sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)-\n" + 
						"            sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end))/\n" + 
						"            sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end) end),4)*100 jingjisunshizhi,\n" + 
						"       round( (case when sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)<>0 then 1\n" + 
						"          else (sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)-\n" + 
						"            sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end))/\n" + 
						"            sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end) end),4)*100 daohuiwodian,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end ))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end ) end),4)*100 chudongrenshu,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end)=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end) end),4)*100 chudongcheliang,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end ))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end ) end),4)*100 shoushangrenshu,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end) end),4)*100 shouhairenshu,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) =0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end )<>0 then 1\n" + 
						"          else(sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end )-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end ))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end ) end),4)*100 xishengrenshu\n" + 
						"        from x\n" + 
						"          order by code asc,name desc");
		try {
			res=dao.queryForList(sb.toString(), list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("结案信息申诉举报消费事件统计报表", "WDY", i == 1 ? "查看报表" : "下载报表", sb.toString(),
				map.get("begintime")+","+map.get("endtime"), req);
		
		return res;
	}
	public List<Map> xingZhengCuoShi(Map<String, String> map, int i,
			HttpServletRequest req) {
		List list=new ArrayList();
		StringBuffer sb=new StringBuffer();
		List<Map> res=new ArrayList<Map>();
		sb.append(
				"with x as (\n" +
						"\n" + 
						"        select i.finishtime, --完成时间\n" + 
						"               v.VISITCONFIRM, --回访情况\n" + 
						"               m.MEDIATE_RESULT, --调解结果\n" + 
						"               m.CHEATSIGN， --是否欺诈\n" + 
						"               m.DISAM, --争议金额\n" + 
						"               m.REDECOLOS, --挽回经济损失\n" + 
						"               m.DOUAMEAM, --加倍赔偿金额\n" + 
						"               m.SPIAMEAM, --精神赔偿金额\n" + 
						"               f.PUTONCASE, --是否立案\n" + 
						"               c.CASEVAL, --案值\n" + 
						"               p.illegincome, --违法所得\n" + 
						"               p.penam, --处罚金额\n" + 
						"               p.forfam, --没收金额\n" + 
						"               p.apprcuram, --变价金额\n" + 
						"               c.ECOLOVAL, --经济损失值\n" + 
						"               p.distoriedplace, --捣毁窝点\n" + 
						"               f.executeperson, --出动人数\n" + 
						"               f.executecar, -- 出动车辆\n" + 
						"               h.INJNUM, --受伤人数\n" + 
						"               d.VICTIMNUM, --受害人数\n" + 
						"               h.SACNUM, --牺牲人数\n" + 
						"               nvl(p.COMMEATYPE,'999') as code,\n" + 
						"               j.name as name,\n" + 
						"               (case when i.regtime >= to_date(?, 'yyyy-mm-dd')\n" + 
						"                    and i.regtime <=\n" + 
						"               to_date(?, 'yyyy-mm-dd hh24:mi:ss')then 1 else 0 end) flag,\n" + 
						"               (case when i.regtime >= to_date(?, 'yyyy-mm-dd')\n" + 
						"                    and i.regtime <=\n" + 
						"               to_date(?, 'yyyy-mm-dd hh24:mi:ss')then 1 else 0 end) tflag,\n" + 
						"               (case when i.regtime >= to_date(?, 'yyyy-mm-dd')\n" + 
						"                    and i.regtime <=\n" + 
						"               to_date(?, 'yyyy-mm-dd hh24:mi:ss')then 1 else 0 end) hflag\n" + 
						"          from dc_cpr_infoware         i,\n" + 
						"               DC_CPR_RETURN_VISIT     v,\n" + 
						"               dc_cpr_feedback         f,\n" + 
						"               DC_CPR_MEDIATION        m,\n" + 
						"               dc_cpr_case_info        c,\n" + 
						"               DC_CPR_LEGAL_PUNISHMENT p,\n" + 
						"               DC_CPR_CASE_HANDLE      h,\n" + 
						"               DC_CPR_CONFIRMED_DAMAGE d,\n" + 
						"               dc_cpr_involved_object  o,\n" + 
						"               dc_cpr_involved_main    ma,\n" + 
						"               (select j.code code ,--j.parentcode as parentcode\n" + 
						"              j.name as name\n" + 
						"                from dc_code.dc_12315_codedata j where j.codetable='CE12') j\n" + 
						"         where i.infowareid = v.infowareid(+)\n" + 
						"           and i.feedbackid = f.feedbackid\n" + 
						"           and f.mediationid = m.mediationid\n" + 
						"           and f.caseinfoid = c.caseinfoid(+)\n" + 
						"           and c.LEGPUNID = p.legpunid\n" + 
						"           and c.CASEHANDLEID = h.casehandleid(+)\n" + 
						"           and c.CONDAMID = d.condamid(+)\n" + 
						"           and o.invobjid(+) = i.invobjid\n" + 
						"           and i.invmaiid = ma.invmaiid(+)\n" + 
						"           and p.COMMEATYPE=j.code(+) \n");
		list.add(map.get("begintime"));
		list.add(map.get("endtime")+" 23:59:59");
		list.add(QueryJieAnUtils.getTongBiDate(map.get("begintime")));
		list.add(QueryJieAnUtils.getTongBiDate(map.get("endtime"))+" 23:59:59");
		if (map.get("hbegintime")!=null&&map.get("hbegintime").length()!=0) {
			list.add(map.get("hbegintime"));
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(map.get("begintime")));
		}
		if (map.get("hendtime")!=null&&map.get("hendtime").length()!=0) {
			list.add(map.get("hendtime")+" 23:59:59");
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(map.get("endtime"))+" 23:59:59");
		}
		if (map.get("dengjibumen")!=null &&map.get("dengjibumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("dengjibumen"),"i.REGDEPCODE"));
		}
		
		if (map.get("chengbanbumen")!=null &&map.get("chengbanbumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("chengbanbumen"),"i.HANDEPCODE"));
		}
		
		if (map.get("wentileixing")!=null &&map.get("wentileixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("wentileixing")," i.APPLBASQUE"));
		}
		
		if (map.get("ketileixing")!=null &&map.get("ketileixing").length()!=0) {
			sb.append(QueryJieAnUtils.UtilOfObjectTypeS(map.get("ketileixing"),"o.invobjtype"));
			sb.append(QueryJieAnUtils.UtilOfObjectTypeB(map.get("ketileixing"),"o.BUSINESSTYPE"));
		}
		
		if (map.get("zhutileixing")!=null &&map.get("zhutileixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("zhutileixing"),"ma.PTTYPE"));
		}
		
		if (map.get("hangyeleixing")!=null &&map.get("hangyeleixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("hangyeleixing"),"ma.TRADETYPE"));
		}
		
		if (map.get("weifazhonglei")!=null &&map.get("weifazhonglei").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("weifazhonglei"),"p.ILLEGACTTYPE"));
		}
		
		/*if (map.get("xingzhengchufa")!=null &&map.get("xingzhengchufa").length()!=0) {
			sb.append("and p.COMMEATYPE =? \n ");
			list.add(map.get("xingzhengchufa"));
		}*/
		
		if (map.get("zhixingleibie")!=null &&map.get("zhixingleibie").length()!=0) {
			sb.append("and h.EXESORT =? \n ");
			list.add(map.get("zhixingleibie"));
		}
		
		if (map.get("jieshoufangshi")!=null &&map.get("jieshoufangshi").length()!=0) {
			sb.append("and i.INCFORM =? \n ");
			list.add(map.get("jieshoufangshi"));
		}
		
		if (map.get("infotype")!=null &&map.get("infotype").length()!=0) {
			sb.append("and i.INFTYPE =? \n ");
			list.add(map.get("infotype"));
		}
		
		if (map.get("wangzhanleixing")!=null &&map.get("wangzhanleixing").length()!=0) {
			sb.append("and ma.WEBSITETYPE =? \n ");
			list.add(map.get("wangzhanleixing"));
		}
		
		if (map.get("qinquanleixing")!=null &&map.get("qinquanleixing").length()!=0) {
			sb.append("and m.TORTYPE =? \n ");
			list.add(map.get("qinquanleixing"));
		}
		
		if (map.get("yiwuleixing")!=null &&map.get("yiwuleixing").length()!=0) {
			sb.append("and m.DEFOBL =? \n ");
			list.add(map.get("yiwuleixing"));
		}
		if (map.get("chufazhonglei")!=null &&map.get("chufazhonglei").length()!=0) {
			sb.append("and p.Pentype =? \n ");
			list.add(map.get("chufazhonglei"));
		}
		sb.append(
				")\n" +
						"        select x.name,x.code,\n" + 
						"        sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"        sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )shushi,\n" + 
						"        sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )tiaojie,\n" + 
						"        sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)jiabeipeichangjine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"        sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)lianshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"        sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )chufajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )bianjiajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"        sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"        from x group by x.name,x.code\n" + 
						"\n" + 
						"\n" + 
						"        union all\n" + 
						"        select '上一段时间段数据'as name,'999901' as code,\n" + 
						"        sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"        sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) shushi,\n" + 
						"        sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ) tiaojie,\n" + 
						"        sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end) jiabeipeichangjine,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"        sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end) lianshu,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end ) chufajine,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end ) bianjiajine,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"        from x\n" + 
						"        union all\n" + 
						"         select '环比增减（%）'as name,'999902' as code,\n" + 
						"        round((case when sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end)=0 and\n" + 
						"          sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)-\n" + 
						"          sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end))/\n" + 
						"          sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end) end ),4)*100 subnum,\n" + 
						"        round((case when sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )=0\n" + 
						"        and  sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) =0 then 0\n" + 
						"        when sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )-\n" + 
						"          sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ))/\n" + 
						"          sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) end),4)*100 shushi,\n" + 
						"\n" + 
						"        round((case when sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0\n" + 
						"              and  sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0 then 0\n" + 
						"         when sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0\n" + 
						"           and sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )<>0 then 1 else\n" + 
						"           (sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )-\n" + 
						"           sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ))/\n" + 
						"           sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ) end ),4)*100 tiaojie,\n" + 
						"        round(( case when sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 and\n" + 
						"          sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 then 0\n" + 
						"          when sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 and\n" + 
						"            sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)<>0 then 1 else\n" + 
						"            (sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)-\n" + 
						"            sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end))/\n" + 
						"            sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end) end ),4)*100 qizha,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end )=0 and\n" + 
						"              sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) =0 then 0\n" + 
						"             when sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end )=0\n" + 
						"              and sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end )<>0 then 1\n" + 
						"             else(sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end )-\n" + 
						"             sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end ))/\n" + 
						"             sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end ) end  ),4)*100 zhengyijine,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end)=0\n" + 
						"              and sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)=0 then 0\n" + 
						"              when sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end)=0\n" + 
						"              and sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)<>0 then 1\n" + 
						"              else(sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)-\n" + 
						"              sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end))/\n" + 
						"              sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end) end),4)*100 wanhuijingjisunshi,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end)=0 and\n" + 
						"              sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end) =0 then 0\n" + 
						"             when sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end)=0 and\n" + 
						"             sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)<>0 then 1\n" + 
						"             else (sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)-\n" + 
						"             sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end))/\n" + 
						"             sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end) end),4)*100 jiabeipeichangjine,\n" + 
						"       round( (case when sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)<>0 then 1\n" + 
						"          else(sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end) end),4)*100 jingshenpeichangjine,\n" + 
						"        round((case when sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end) <> 0 then 1 else\n" + 
						"          (sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)-\n" + 
						"          sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end))/\n" + 
						"          sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end) end),4)*100lianshu,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end )=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) <>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end )-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end ))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end ) end),4)*100 anzhi,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end)=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end) =0 and\n" + 
						"          sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end) end),4)*100 weifasuode,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end )=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.penam,0) else 0 end ) <>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end ))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end ) end),4)*100 chufajine,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end)=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end)=0 and\n" + 
						"          sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) <>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end)-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end) end),4)*100 moshoujine,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end )=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )<>0 then 1\n" + 
						"          else(sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end ))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end ) end),4)*100 bianjiajine,\n" + 
						"       round( (case when sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0  then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)<>0 then 1\n" + 
						"          else (sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)-\n" + 
						"            sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end))/\n" + 
						"            sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end) end),4)*100 jingjisunshizhi,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)<>0 then 1\n" + 
						"          else (sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)-\n" + 
						"            sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end))/\n" + 
						"            sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end) end),4)*100 daohuiwodian,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end ))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end ) end),4)*100chudongrenshu,\n" + 
						"       round( (case when sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end)=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end) end),4)*100 chudongcheliang,\n" + 
						"       round( (case when sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end ))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end ) end),4)*100 shoushangrenshu,\n" + 
						"       round( (case when sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end) end),4)*100 shouhairenshu,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) =0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end )<>0 then 1\n" + 
						"          else(sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end )-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end ))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end ) end),4)*100 xishengrenshu\n" + 
						"        from x\n" + 
						"    union all\n" + 
						"        select '去年同期数据'as name,'999903' as code,\n" + 
						"        sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"        sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) shushi,\n" + 
						"        sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ) tiaojie,\n" + 
						"        sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end) jiabeipeichangjine,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"        sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end) lianshu,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end ) chufajine,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end ) bianjiajine,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end ) chudongrenshu,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"        from x\n" + 
						"        union all\n" + 
						"          select '同比增减（%）'as name,'999904' as code,\n" + 
						"        round((case when sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end)=0 and\n" + 
						"          sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)-\n" + 
						"          sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end))/\n" + 
						"          sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end) end ),4)*100 subnum,\n" + 
						"        round((case when sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )=0\n" + 
						"        and  sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) =0 then 0\n" + 
						"        when sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )-\n" + 
						"          sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ))/\n" + 
						"          sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) end),4)*100 shushi,\n" + 
						"        round((case when sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0\n" + 
						"              and  sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0 then 0\n" + 
						"         when sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0\n" + 
						"           and sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )<>0 then 1 else\n" + 
						"           (sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )-\n" + 
						"           sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ))/\n" + 
						"           sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ) end ),4)*100 tiaojie,\n" + 
						"        round(( case when sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 and\n" + 
						"          sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 then 0\n" + 
						"          when sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 and\n" + 
						"            sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)<>0 then 1 else\n" + 
						"            (sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)-\n" + 
						"            sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end))/\n" + 
						"            sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end) end ),4)*100 qizha,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end )=0 and\n" + 
						"              sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) =0 then 0\n" + 
						"             when sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end )=0\n" + 
						"              and sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end )<>0 then 1\n" + 
						"             else(sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end )-\n" + 
						"             sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end ))/\n" + 
						"             sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end ) end  ),4)*100zhengyijine,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end)=0\n" + 
						"              and sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)=0 then 0\n" + 
						"              when sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end)=0\n" + 
						"              and sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)<>0 then 1\n" + 
						"              else(sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)-\n" + 
						"              sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end))/\n" + 
						"              sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end) end),4)*100 wanhuijingjisunshi,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end)=0 and\n" + 
						"              sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end) =0 then 0\n" + 
						"             when sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end)=0 and\n" + 
						"             sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)<>0 then 1\n" + 
						"             else (sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)-\n" + 
						"             sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end))/\n" + 
						"             sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end) end),4)*100 jiabeipeichangjine,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)<>0 then 1\n" + 
						"          else(sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end) end),4)*100 jingshenpeichangjine,\n" + 
						"        round((case when sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end) <> 0 then 1 else\n" + 
						"          (sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)-\n" + 
						"          sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end))/\n" + 
						"          sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end) end),4)*100lianshu,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end )=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) <>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end )-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end ))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end ) end),4)*100 anzhi,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end)=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end) =0 and\n" + 
						"          sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end) end),4)*100 weifasuode,\n" + 
						"       round( (case when sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end )=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.penam,0) else 0 end ) <>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end ))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end ) end),4)*100 chufajine,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end)=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end)=0 and\n" + 
						"          sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) <>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end)-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end) end),4)*100 moshoujine,\n" + 
						"       round( (case when sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end )=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )<>0 then 1\n" + 
						"          else(sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end ))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end ) end),4)*100 bianjiajine,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0  then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)<>0 then 1\n" + 
						"          else (sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)-\n" + 
						"            sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end))/\n" + 
						"            sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end) end),4)*100 jingjisunshizhi,\n" + 
						"       round( (case when sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)<>0 then 1\n" + 
						"          else (sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)-\n" + 
						"            sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end))/\n" + 
						"            sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end) end),4)*100 daohuiwodian,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end ))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end ) end),4)*100 chudongrenshu,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end)=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end) end),4)*100 chudongcheliang,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end ))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end ) end),4)*100 shoushangrenshu,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end) end),4)*100 shouhairenshu,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) =0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end )<>0 then 1\n" + 
						"          else(sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end )-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end ))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end ) end),4)*100 xishengrenshu\n" + 
						"        from x\n" + 
						"          order by code asc,name desc");
		try {
			res=dao.queryForList(sb.toString(), list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("结案信息申诉举报行政强制措施类型统计报表", "WDY", i == 1 ? "查看报表" : "下载报表", sb.toString(),
				map.get("begintime")+","+map.get("endtime"), req);
		return res;
	}
	public List<Map> zhiXingLeiBie(Map<String, String> map, int i,
			HttpServletRequest req) {
		List list=new ArrayList();
		StringBuffer sb=new StringBuffer();
		List<Map> res=new ArrayList<Map>();
		sb.append(
				"with x as (\n" +
						"\n" + 
						"        select i.finishtime, --完成时间\n" + 
						"               v.VISITCONFIRM, --回访情况\n" + 
						"               m.MEDIATE_RESULT, --调解结果\n" + 
						"               m.CHEATSIGN， --是否欺诈\n" + 
						"               m.DISAM, --争议金额\n" + 
						"               m.REDECOLOS, --挽回经济损失\n" + 
						"               m.DOUAMEAM, --加倍赔偿金额\n" + 
						"               m.SPIAMEAM, --精神赔偿金额\n" + 
						"               f.PUTONCASE, --是否立案\n" + 
						"               c.CASEVAL, --案值\n" + 
						"               p.illegincome, --违法所得\n" + 
						"               p.penam, --处罚金额\n" + 
						"               p.forfam, --没收金额\n" + 
						"               p.apprcuram, --变价金额\n" + 
						"               c.ECOLOVAL, --经济损失值\n" + 
						"               p.distoriedplace, --捣毁窝点\n" + 
						"               f.executeperson, --出动人数\n" + 
						"               f.executecar, -- 出动车辆\n" + 
						"               h.INJNUM, --受伤人数\n" + 
						"               d.VICTIMNUM, --受害人数\n" + 
						"               h.SACNUM, --牺牲人数\n" + 
						"               nvl(h.EXESORT,'999') as code,\n" + 
						"               j.name as name,\n" + 
						"               (case when i.regtime >= to_date(?, 'yyyy-mm-dd')\n" + 
						"                    and i.regtime <=\n" + 
						"               to_date(?, 'yyyy-mm-dd hh24:mi:ss')then 1 else 0 end) flag,\n" + 
						"               (case when i.regtime >= to_date(?, 'yyyy-mm-dd')\n" + 
						"                    and i.regtime <=\n" + 
						"               to_date(?, 'yyyy-mm-dd hh24:mi:ss')then 1 else 0 end) tflag,\n" + 
						"               (case when i.regtime >= to_date(?, 'yyyy-mm-dd')\n" + 
						"                    and i.regtime <=\n" + 
						"               to_date(?, 'yyyy-mm-dd hh24:mi:ss')then 1 else 0 end) hflag\n" + 
						"          from dc_cpr_infoware         i,\n" + 
						"               DC_CPR_RETURN_VISIT     v,\n" + 
						"               dc_cpr_feedback         f,\n" + 
						"               DC_CPR_MEDIATION        m,\n" + 
						"               dc_cpr_case_info        c,\n" + 
						"               DC_CPR_LEGAL_PUNISHMENT p,\n" + 
						"               DC_CPR_CASE_HANDLE      h,\n" + 
						"               DC_CPR_CONFIRMED_DAMAGE d,\n" + 
						"               dc_cpr_involved_object  o,\n" + 
						"               dc_cpr_involved_main    ma,\n" + 
						"               (select j.code code ,--j.parentcode as parentcode\n" + 
						"              j.name as name\n" + 
						"                from dc_code.dc_12315_codedata j where j.codetable='CE38') j\n" + 
						"         where i.infowareid = v.infowareid(+)\n" + 
						"           and i.feedbackid = f.feedbackid\n" + 
						"           and f.mediationid = m.mediationid(+)\n" + 
						"           and f.caseinfoid = c.caseinfoid(+)\n" + 
						"           and c.LEGPUNID = p.legpunid(+)\n" + 
						"           and c.CASEHANDLEID = h.casehandleid\n" + 
						"           and c.CONDAMID = d.condamid(+)\n" + 
						"           and o.invobjid(+) = i.invobjid\n" + 
						"           and i.invmaiid = ma.invmaiid(+)\n" + 
						"           and h.EXESORT=j.code(+) \n");
		list.add(map.get("begintime"));
		list.add(map.get("endtime")+" 23:59:59");
		list.add(QueryJieAnUtils.getTongBiDate(map.get("begintime")));
		list.add(QueryJieAnUtils.getTongBiDate(map.get("endtime"))+" 23:59:59");
		if (map.get("hbegintime")!=null&&map.get("hbegintime").length()!=0) {
			list.add(map.get("hbegintime"));
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(map.get("begintime")));
		}
		if (map.get("hendtime")!=null&&map.get("hendtime").length()!=0) {
			list.add(map.get("hendtime")+" 23:59:59");
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(map.get("endtime"))+" 23:59:59");
		}
		if (map.get("dengjibumen")!=null &&map.get("dengjibumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("dengjibumen"),"i.REGDEPCODE"));
		}
		
		if (map.get("chengbanbumen")!=null &&map.get("chengbanbumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("chengbanbumen"),"i.HANDEPCODE"));
		}
		
		if (map.get("wentileixing")!=null &&map.get("wentileixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("wentileixing")," i.APPLBASQUE"));
		}
		
		if (map.get("ketileixing")!=null &&map.get("ketileixing").length()!=0) {
			sb.append(QueryJieAnUtils.UtilOfObjectTypeS(map.get("ketileixing"),"o.invobjtype"));
			sb.append(QueryJieAnUtils.UtilOfObjectTypeB(map.get("ketileixing"),"o.BUSINESSTYPE"));
		}
		
		if (map.get("zhutileixing")!=null &&map.get("zhutileixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("zhutileixing"),"ma.PTTYPE"));
		}
		
		if (map.get("hangyeleixing")!=null &&map.get("hangyeleixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("hangyeleixing"),"ma.TRADETYPE"));
		}
		
		if (map.get("weifazhonglei")!=null &&map.get("weifazhonglei").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("weifazhonglei"),"p.ILLEGACTTYPE"));
		}
		
		if (map.get("xingzhengchufa")!=null &&map.get("xingzhengchufa").length()!=0) {
			sb.append("and p.COMMEATYPE =? \n ");
			list.add(map.get("xingzhengchufa"));
		}
		
		/*if (map.get("zhixingleibie")!=null &&map.get("zhixingleibie").length()!=0) {
			sb.append("and h.EXESORT =? \n ");
			list.add(map.get("zhixingleibie"));
		}*/
		
		if (map.get("jieshoufangshi")!=null &&map.get("jieshoufangshi").length()!=0) {
			sb.append("and i.INCFORM =? \n ");
			list.add(map.get("jieshoufangshi"));
		}
		
		if (map.get("infotype")!=null &&map.get("infotype").length()!=0) {
			sb.append("and i.INFTYPE =? \n ");
			list.add(map.get("infotype"));
		}
		
		if (map.get("wangzhanleixing")!=null &&map.get("wangzhanleixing").length()!=0) {
			sb.append("and ma.WEBSITETYPE =? \n ");
			list.add(map.get("wangzhanleixing"));
		}
		
		if (map.get("qinquanleixing")!=null &&map.get("qinquanleixing").length()!=0) {
			sb.append("and m.TORTYPE =? \n ");
			list.add(map.get("qinquanleixing"));
		}
		
		if (map.get("yiwuleixing")!=null &&map.get("yiwuleixing").length()!=0) {
			sb.append("and m.DEFOBL =? \n ");
			list.add(map.get("yiwuleixing"));
		}
		if (map.get("chufazhonglei")!=null &&map.get("chufazhonglei").length()!=0) {
			sb.append("and p.Pentype =? \n ");
			list.add(map.get("chufazhonglei"));
		}
		sb.append(
				")\n" +
						"        select x.name,x.code,\n" + 
						"        sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"        sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )shushi,\n" + 
						"        sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )tiaojie,\n" + 
						"        sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)jiabeipeichangjine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"        sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)lianshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"        sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )chufajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )bianjiajine,\n" + 
						"        sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"        sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"        sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"        sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"        from x group by x.name,x.code\n" + 
						"\n" + 
						"\n" + 
						"        union all\n" + 
						"        select '上一段时间段数据'as name,'999901' as code,\n" + 
						"        sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"        sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) shushi,\n" + 
						"        sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ) tiaojie,\n" + 
						"        sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end) jiabeipeichangjine,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"        sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end) lianshu,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end ) chufajine,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end ) bianjiajine,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end )chudongrenshu,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"        sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"        from x\n" + 
						"        union all\n" + 
						"         select '环比增减（%）'as name,'999902' as code,\n" + 
						"        round((case when sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end)=0 and\n" + 
						"          sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)-\n" + 
						"          sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end))/\n" + 
						"          sum(case when x.hflag=1 and x.finishtime is not null then 1 else 0 end) end ),4)*100 subnum,\n" + 
						"        round((case when sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )=0\n" + 
						"        and  sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) =0 then 0\n" + 
						"        when sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )-\n" + 
						"          sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ))/\n" + 
						"          sum(case when x.hflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) end),4)*100 shushi,\n" + 
						"\n" + 
						"        round((case when sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0\n" + 
						"              and  sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0 then 0\n" + 
						"         when sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0\n" + 
						"           and sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )<>0 then 1 else\n" + 
						"           (sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )-\n" + 
						"           sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ))/\n" + 
						"           sum(case when x.hflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ) end ),4)*100 tiaojie,\n" + 
						"        round(( case when sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 and\n" + 
						"          sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 then 0\n" + 
						"          when sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 and\n" + 
						"            sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)<>0 then 1 else\n" + 
						"            (sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)-\n" + 
						"            sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end))/\n" + 
						"            sum(case when x.hflag=1 and x.CHEATSIGN='1' then 1 else 0 end) end ),4)*100 qizha,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end )=0 and\n" + 
						"              sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) =0 then 0\n" + 
						"             when sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end )=0\n" + 
						"              and sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end )<>0 then 1\n" + 
						"             else(sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end )-\n" + 
						"             sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end ))/\n" + 
						"             sum(case when x.hflag=1 then nvl(x.DISAM,0) else 0 end ) end  ),4)*100 zhengyijine,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end)=0\n" + 
						"              and sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)=0 then 0\n" + 
						"              when sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end)=0\n" + 
						"              and sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)<>0 then 1\n" + 
						"              else(sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)-\n" + 
						"              sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end))/\n" + 
						"              sum(case when x.hflag=1 then nvl(x.REDECOLOS,0) else 0 end) end),4)*100 wanhuijingjisunshi,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end)=0 and\n" + 
						"              sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end) =0 then 0\n" + 
						"             when sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end)=0 and\n" + 
						"             sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)<>0 then 1\n" + 
						"             else (sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)-\n" + 
						"             sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end))/\n" + 
						"             sum(case when x.hflag=1 then nvl(x.DOUAMEAM,0) else 0 end) end),4)*100 jiabeipeichangjine,\n" + 
						"       round( (case when sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)<>0 then 1\n" + 
						"          else(sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.SPIAMEAM,0) else 0 end) end),4)*100 jingshenpeichangjine,\n" + 
						"        round((case when sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end) <> 0 then 1 else\n" + 
						"          (sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)-\n" + 
						"          sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end))/\n" + 
						"          sum(case when x.hflag=1 and x.PUTONCASE='1' then 1 else 0 end) end),4)*100lianshu,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end )=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) <>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end )-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end ))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.CASEVAL,0) else  0 end ) end),4)*100 anzhi,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end)=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end) =0 and\n" + 
						"          sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.illegincome,0) else 0 end) end),4)*100 weifasuode,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end )=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.penam,0) else 0 end ) <>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end ))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.penam,0) else 0 end ) end),4)*100 chufajine,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end)=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end)=0 and\n" + 
						"          sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) <>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end)-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.forfam,0) else 0 end) end),4)*100 moshoujine,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end )=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )<>0 then 1\n" + 
						"          else(sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end ))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.apprcuram,0) else 0 end ) end),4)*100 bianjiajine,\n" + 
						"       round( (case when sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0  then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)<>0 then 1\n" + 
						"          else (sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)-\n" + 
						"            sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end))/\n" + 
						"            sum(case when x.hflag=1 then nvl(x.ECOLOVAL,0) else 0 end) end),4)*100 jingjisunshizhi,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)<>0 then 1\n" + 
						"          else (sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)-\n" + 
						"            sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end))/\n" + 
						"            sum(case when x.hflag=1 then nvl(x.distoriedplace,0) else 0 end) end),4)*100 daohuiwodian,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end ))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.executeperson,0) else 0 end ) end),4)*100chudongrenshu,\n" + 
						"       round( (case when sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end)=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.executecar,0) else 0 end) end),4)*100 chudongcheliang,\n" + 
						"       round( (case when sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end ))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.INJNUM,0) else 0 end ) end),4)*100 shoushangrenshu,\n" + 
						"       round( (case when sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.VICTIMNUM,0) else 0 end) end),4)*100 shouhairenshu,\n" + 
						"        round((case when sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) =0 then 0\n" + 
						"        when sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end )<>0 then 1\n" + 
						"          else(sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end )-\n" + 
						"          sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end ))/\n" + 
						"          sum(case when x.hflag=1 then nvl(x.SACNUM,0) else 0 end ) end),4)*100 xishengrenshu\n" + 
						"        from x\n" + 
						"    union all\n" + 
						"        select '去年同期数据'as name,'999903' as code,\n" + 
						"        sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end) subnum,\n" + 
						"        sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) shushi,\n" + 
						"        sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ) tiaojie,\n" + 
						"        sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end) qizha,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end ) zhengyijine,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end) wanhuijingjisunshi,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end) jiabeipeichangjine,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end) jingshenpeichangjine,\n" + 
						"        sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end) lianshu,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end ) anzhi,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end) weifasuode,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end ) chufajine,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end) moshoujine,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end ) bianjiajine,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end) jingjisunshizhi,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end) daohuiwodian,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end ) chudongrenshu,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end) chudongcheliang,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end ) shoushangrenshu,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end) shouhairenshu,\n" + 
						"        sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end ) xishengrenshu\n" + 
						"        from x\n" + 
						"        union all\n" + 
						"          select '同比增减（%）'as name,'999904' as code,\n" + 
						"        round((case when sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end)=0 and\n" + 
						"          sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 and x.finishtime is not null then 1 else 0 end)-\n" + 
						"          sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end))/\n" + 
						"          sum(case when x.tflag=1 and x.finishtime is not null then 1 else 0 end) end ),4)*100 subnum,\n" + 
						"        round((case when sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )=0\n" + 
						"        and  sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) =0 then 0\n" + 
						"        when sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 and x.VISITCONFIRM='1' then 1 else 0 end )-\n" + 
						"          sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ))/\n" + 
						"          sum(case when x.tflag=1 and x.VISITCONFIRM='1' then 1 else 0 end ) end),4)*100 shushi,\n" + 
						"        round((case when sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0\n" + 
						"              and  sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0 then 0\n" + 
						"         when sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )=0\n" + 
						"           and sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )<>0 then 1 else\n" + 
						"           (sum(case when x.flag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end )-\n" + 
						"           sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ))/\n" + 
						"           sum(case when x.tflag=1 and x.MEDIATE_RESULT='1' then 1 else 0 end ) end ),4)*100 tiaojie,\n" + 
						"        round(( case when sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 and\n" + 
						"          sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 then 0\n" + 
						"          when sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end)=0 and\n" + 
						"            sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)<>0 then 1 else\n" + 
						"            (sum(case when x.flag=1 and x.CHEATSIGN='1' then 1 else 0 end)-\n" + 
						"            sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end))/\n" + 
						"            sum(case when x.tflag=1 and x.CHEATSIGN='1' then 1 else 0 end) end ),4)*100 qizha,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end )=0 and\n" + 
						"              sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end ) =0 then 0\n" + 
						"             when sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end )=0\n" + 
						"              and sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end )<>0 then 1\n" + 
						"             else(sum(case when x.flag=1 then nvl(x.DISAM,0) else 0 end )-\n" + 
						"             sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end ))/\n" + 
						"             sum(case when x.tflag=1 then nvl(x.DISAM,0) else 0 end ) end  ),4)*100zhengyijine,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end)=0\n" + 
						"              and sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)=0 then 0\n" + 
						"              when sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end)=0\n" + 
						"              and sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)<>0 then 1\n" + 
						"              else(sum(case when x.flag=1 then nvl(x.REDECOLOS,0) else 0 end)-\n" + 
						"              sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end))/\n" + 
						"              sum(case when x.tflag=1 then nvl(x.REDECOLOS,0) else 0 end) end),4)*100 wanhuijingjisunshi,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end)=0 and\n" + 
						"              sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end) =0 then 0\n" + 
						"             when sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end)=0 and\n" + 
						"             sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)<>0 then 1\n" + 
						"             else (sum(case when x.flag=1 then nvl(x.DOUAMEAM,0) else 0 end)-\n" + 
						"             sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end))/\n" + 
						"             sum(case when x.tflag=1 then nvl(x.DOUAMEAM,0) else 0 end) end),4)*100 jiabeipeichangjine,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)<>0 then 1\n" + 
						"          else(sum(case when x.flag=1 then nvl(x.SPIAMEAM,0) else 0 end)-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.SPIAMEAM,0) else 0 end) end),4)*100 jingshenpeichangjine,\n" + 
						"        round((case when sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end) <> 0 then 1 else\n" + 
						"          (sum(case when x.flag=1 and x.PUTONCASE='1' then 1 else 0 end)-\n" + 
						"          sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end))/\n" + 
						"          sum(case when x.tflag=1 and x.PUTONCASE='1' then 1 else 0 end) end),4)*100lianshu,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end )=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end ) <>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.CASEVAL,0) else  0 end )-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end ))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.CASEVAL,0) else  0 end ) end),4)*100 anzhi,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end)=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end) =0 and\n" + 
						"          sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.illegincome,0) else 0 end)-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.illegincome,0) else 0 end) end),4)*100 weifasuode,\n" + 
						"       round( (case when sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end )=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.penam,0) else 0 end ) <>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.penam,0) else 0 end )-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end ))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.penam,0) else 0 end ) end),4)*100 chufajine,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end)=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end)=0 and\n" + 
						"          sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end) <>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.forfam,0) else 0 end)-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.forfam,0) else 0 end) end),4)*100 moshoujine,\n" + 
						"       round( (case when sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end )=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )<>0 then 1\n" + 
						"          else(sum(case when x.flag=1 then nvl(x.apprcuram,0) else 0 end )-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end ))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.apprcuram,0) else 0 end ) end),4)*100 bianjiajine,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0  then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)<>0 then 1\n" + 
						"          else (sum(case when x.flag=1 then nvl(x.ECOLOVAL,0) else 0 end)-\n" + 
						"            sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end))/\n" + 
						"            sum(case when x.tflag=1 then nvl(x.ECOLOVAL,0) else 0 end) end),4)*100 jingjisunshizhi,\n" + 
						"       round( (case when sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)<>0 then 1\n" + 
						"          else (sum(case when x.flag=1 then nvl(x.distoriedplace,0) else 0 end)-\n" + 
						"            sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end))/\n" + 
						"            sum(case when x.tflag=1 then nvl(x.distoriedplace,0) else 0 end) end),4)*100 daohuiwodian,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.executeperson,0) else 0 end )-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end ))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.executeperson,0) else 0 end ) end),4)*100 chudongrenshu,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end)=0 and\n" + 
						"        sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.executecar,0) else 0 end)-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.executecar,0) else 0 end) end),4)*100 chudongcheliang,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.INJNUM,0) else 0 end )-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end ))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.INJNUM,0) else 0 end ) end),4)*100 shoushangrenshu,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end)=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)<>0 then 1 else\n" + 
						"          (sum(case when x.flag=1 then nvl(x.VICTIMNUM,0) else 0 end)-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.VICTIMNUM,0) else 0 end) end),4)*100 shouhairenshu,\n" + 
						"        round((case when sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end )=0\n" + 
						"        and sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end ) =0 then 0\n" + 
						"        when sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end )=0\n" + 
						"          and sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end )<>0 then 1\n" + 
						"          else(sum(case when x.flag=1 then nvl(x.SACNUM,0) else 0 end )-\n" + 
						"          sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end ))/\n" + 
						"          sum(case when x.tflag=1 then nvl(x.SACNUM,0) else 0 end ) end),4)*100 xishengrenshu\n" + 
						"        from x\n" + 
						"          order by code asc,name desc");
		try {
			res=dao.queryForList(sb.toString(), list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("结案信息申诉举报执行类别统计报表", "WDY", i == 1 ? "查看报表" : "下载报表", sb.toString(),
				map.get("begintime")+","+map.get("endtime"), req);
		return res;
	}
	
	public List<Map> zj_ChengBanBuMen(Map<String, String> map, int i,
			HttpServletRequest req) {
		List list=new ArrayList();
		StringBuffer sb=new StringBuffer();
		List<Map> res=new ArrayList<Map>();
		sb.append(
				"with x as (\n" +
						" select nvl(j.DEP_NAME,'部门信息为空') as name ,nvl(x.PARENT_DEP_CODE,'9999') as code,x.replied,x.visitconfirm,x.flag,x.tflag,x.hflag from (\n" + 
						"  select  /*+ parallel(8)*/  j.PARENT_DEP_CODE  ,decode(f.replied,1,1,0) replied ,v.visitconfirm visitconfirm,\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) flag,\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) tflag,  --同比\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) hflag    --环比\n" + 
						"   from dc_cpr_infoware i,DC_CPR_RETURN_VISIT v,\n" + 
						"  dc_cpr_feedback f,DC_CPR_INQUIRY q,DC_CPR_INFO_PROVIDER p,dc_cpr_involved_main m,\n" + 
						"  dc_jg_sys_right_department j\n" + 
						"  where i.feedbackid=f.feedbackid(+)\n" + 
						"  and i.infowareid=v.infowareid(+)\n" + 
						"  and i.INQUIRYID=q.inquiryid(+)\n" + 
						"  and i.INFPROID=p.INFPROID(+)\n" + 
						"  and i.Invmaiid=m.INVMAIID(+)\n" + 
						"  and j.sys_right_department_id(+)=i.handepcode \n");
		list.add(map.get("begintime"));
		list.add(map.get("endtime")+" 23:59:59");
		list.add(QueryJieAnUtils.getTongBiDate(map.get("begintime")));
		list.add(QueryJieAnUtils.getTongBiDate(map.get("endtime"))+" 23:59:59");
		if (map.get("hbegintime")!=null&&map.get("hbegintime").length()!=0) {
			list.add(map.get("hbegintime"));
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(map.get("begintime")));
		}
		if (map.get("hendtime")!=null&&map.get("hendtime").length()!=0) {
			list.add(map.get("hendtime")+" 23:59:59");
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(map.get("endtime"))+" 23:59:59");
		}
		if (map.get("infotype")!=null&&map.get("infotype").length()!=0) {
			sb.append("and i.inftype =? \n");
			list.add(map.get("infotype"));
		}else{
			sb.append("and i.inftype in ('3','4') \n");
		}
		
		if (map.get("dengjibumen")!=null&&map.get("dengjibumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("dengjibumen"),"i.REGDEPCODE"));
		}
		if (map.get("chengbanbumen")!=null&&map.get("chengbanbumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("chengbanbumen"),"i.HANDEPCODE"));
		}
		if (map.get("jieshoufangshi")!=null&&map.get("jieshoufangshi").length()!=0) {
			sb.append(" and i.INCFORM =? \n");
			list.add(map.get("jieshoufangshi"));
		}
		if (map.get("suoshubumen")!=null&&map.get("suoshubumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("suoshubumen"),"q.INQDEPCODE"));
		}
		if (map.get("wentileixing")!=null&&map.get("wentileixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("wentileixing"),"i.APPLBASQUE"));
		}
		if (map.get("yewufanwei")!=null&&map.get("yewufanwei").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("yewufanwei"),"q.BUSINESSRANGE"));
		}
		if (map.get("renyuanshenfen")!=null&&map.get("renyuanshenfen").length()!=0) {
			sb.append(" and p.PERIDE=? \n");
			list.add(map.get("renyuanshenfen"));
		}
		if (map.get("hangyeleixing")!=null&&map.get("hangyeleixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("hangyeleixing"),"m.TRADETYPE"));
		}
		if (map.get("qiyeleixing")!=null&&map.get("qiyeleixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("qiyeleixing"),"m.ENTTYPE"));
		}
		sb.append(
				") x left join dc_jg_sys_right_department j\n" +
						"  on x.PARENT_DEP_CODE=j.sys_right_department_id\n" + 
						"  union all\n" + 
						"  select  /*+ parallel(8)*/  '　'||nvl(j.dep_name,'部门信息为空') as name,nvl(j.PARENT_DEP_CODE,'9999') as  code ,decode(f.replied,1,1,0) replied ,v.visitconfirm visitconfirm,\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) flag,\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) tflag,\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) hflag\n" + 
						"   from dc_cpr_infoware i,DC_CPR_RETURN_VISIT v,\n" + 
						"  dc_cpr_feedback f,DC_CPR_INQUIRY q,DC_CPR_INFO_PROVIDER p,dc_cpr_involved_main m,\n" + 
						"  dc_jg_sys_right_department j\n" + 
						"  where i.feedbackid=f.feedbackid(+)\n" + 
						"  and i.infowareid=v.infowareid(+)\n" + 
						"  and i.INQUIRYID=q.inquiryid(+)\n" + 
						"  and i.INFPROID=p.INFPROID(+)\n" + 
						"  and i.Invmaiid=m.INVMAIID(+)\n" + 
						"  and j.sys_right_department_id(+)=i.handepcode \n");
		list.add(map.get("begintime"));
		list.add(map.get("endtime")+" 23:59:59");
		list.add(QueryJieAnUtils.getTongBiDate(map.get("begintime")));
		list.add(QueryJieAnUtils.getTongBiDate(map.get("begintime"))+" 23:59:59");
		if (map.get("hbegintime")!=null&&map.get("hbegintime").length()!=0) {
			list.add(map.get("hbegintime"));
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(map.get("begintime")));
		}
		if (map.get("hendtime")!=null&&map.get("hendtime").length()!=0) {
			list.add(map.get("hendtime")+" 23:59:59");
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(map.get("endtime"))+" 23:59:59");
		}
		if (map.get("infotype")!=null&&map.get("infotype").length()!=0) {
			sb.append("and i.inftype =? \n");
			list.add(map.get("infotype"));
		}else{
			sb.append("and i.inftype in ('3','4') \n");
		}
		
		if (map.get("dengjibumen")!=null&&map.get("dengjibumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("dengjibumen"),"i.REGDEPCODE"));
		}
		if (map.get("chengbanbumen")!=null&&map.get("chengbanbumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("chengbanbumen"),"i.HANDEPCODE"));
		}
		if (map.get("jieshoufangshi")!=null&&map.get("jieshoufangshi").length()!=0) {
			sb.append(" and i.INCFORM =? \n");
			list.add(map.get("jieshoufangshi"));
		}
		if (map.get("suoshubumen")!=null&&map.get("suoshubumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("suoshubumen"),"q.INQDEPCODE"));
		}
		if (map.get("wentileixing")!=null&&map.get("wentileixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("wentileixing"),"i.APPLBASQUE"));
		}
		if (map.get("yewufanwei")!=null&&map.get("yewufanwei").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("yewufanwei"),"q.BUSINESSRANGE"));
		}
		if (map.get("renyuanshenfen")!=null&&map.get("renyuanshenfen").length()!=0) {
			sb.append(" and p.PERIDE=? \n");
			list.add(map.get("renyuanshenfen"));
		}
		if (map.get("hangyeleixing")!=null&&map.get("hangyeleixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("hangyeleixing"),"m.TRADETYPE"));
		}
		if (map.get("qiyeleixing")!=null&&map.get("qiyeleixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("qiyeleixing"),"m.ENTTYPE"));
		}
		sb.append(
				")\n" +
						"  select x.name ,x.code,to_char(sum(case when x.replied =0 and flag=1 then 1 else 0 end )) weihuifu,\n" + 
						"                 to_char(sum(case when x.replied =1 and flag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"                to_char( sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)) shushi,\n" + 
						"                 to_char(sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)) bushushi,\n" + 
						"                 to_char(sum(flag)) heji,\n" + 
						"                 to_char(round(ratio_to_report(sum(x.flag)) over () ,4)*200,'fm9999999990.0999') as zhanbi,\n" + 
						"                 to_char(sum(hflag)) huanbishu,\n" + 
						"                 to_char(round((case when sum(hflag)=0 and  sum(flag)=0 then 0\n" + 
						"                       when sum(hflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                       else  (sum(flag)-sum(hflag))/sum(hflag) end ),4)*100,'fm999990.0999')||'%' huanbi,\n" + 
						"                 to_char(sum(tflag)) tongbishu,\n" + 
						"                 to_char(round((case when sum(tflag)=0 and  sum(flag)=0 then 0\n" + 
						"                       when sum(tflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                       else  (sum(flag)-sum(tflag))/sum(tflag) end ),4)*100,'fm9999999990.0999')||'%' tongbi\n" + 
						"                 from x group by x.name,x.code\n" + 
						"\n" + 
						"                 union all\n" + 
						"                 select '占总量' as name,'99991'as code,\n" + 
						"                  to_char(round((case when\n" + 
						"                 sum(case when x.replied =0 and flag=1 then 1 else 0 end )=0 then 0 else\n" + 
						"                 sum(case when x.replied =0 and flag=1 then 1 else 0 end )/sum(flag) end\n" + 
						"                 ),4)*100,'fm9999999990.0999')||'%' weihuifu,\n" + 
						"                 to_char(round((case when\n" + 
						"                 sum(case when x.replied =1 and flag=1 then 1 else 0 end )=0 then 0 else\n" + 
						"                 sum(case when x.replied =1 and flag=1 then 1 else 0 end )/sum(flag) end\n" + 
						"                 ),4)*100,'fm9999999990.0999')||'%' yihuifu,\n" + 
						"                  to_char(round((case when\n" + 
						"                 sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)=0 then 0 else\n" + 
						"                 sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)/sum(flag) end\n" + 
						"                 ) ,4)*100,'fm9999999990.0999')||'%'shushi,\n" + 
						"                  to_char(round((case when\n" + 
						"                 sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)=0 then 0 else\n" + 
						"                 sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)/sum(flag) end\n" + 
						"                 ) ,4)*100,'fm9999999990.0999')||'%'bushushi,\n" + 
						"                  to_char(round((case when sum(flag)=0 then 0 else\n" + 
						"                 sum(flag)/sum(flag) end),4)*100,'fm9999999990.0999')||'%' heji ,\n" + 
						"                  ' ' as zhanbi,  ' ' as huanbishu, ' ' as huanbi, ' ' as tongbishu, ' ' as tongbi\n" + 
						"                 from x\n" + 
						"\n" + 
						"              union all\n" + 
						"              select '上时段数据'  as name ,'99992' as code,\n" + 
						"              to_char(sum(case when x.replied =0 and hflag=1 then 1 else 0 end )/2) weihuifu,\n" + 
						"                 to_char(sum(case when x.replied =1 and hflag=1 then 1 else 0 end )/2) as yihuifu,\n" + 
						"                to_char( sum(case when x.visitconfirm =1 and hflag=1 then 1 else 0 end)/2) shushi,\n" + 
						"                 to_char(sum(case when x.visitconfirm =2 and hflag=1 then 1 else 0 end)/2) bushushi,\n" + 
						"                 to_char(sum(hflag)/2) heji,\n" + 
						"                 ' ' as zhanbi, ' ' as huanbishu, ' ' as huanbi, ' ' as tongbishu, ' ' as tongbi\n" + 
						"                 from x\n" + 
						"              union all\n" + 
						"               select '环比增减（%）'  as name ,'99993' as code,\n" + 
						"                to_char(round(\n" + 
						"                (case when sum(case when x.replied =0 and hflag=1 then 1 else 0 end )=0\n" + 
						"                             and  sum(case when x.replied =0 and flag=1 then 1 else 0 end )=0\n" + 
						"                             then 0\n" + 
						"                             when sum(case when x.replied =0 and hflag=1 then 1 else 0 end )=0\n" + 
						"                             and  sum(case when x.replied =0 and flag=1 then 1 else 0 end )<>0\n" + 
						"                             then 1 else\n" + 
						"                             (sum(case when x.replied =0 and flag=1 then 1 else 0 end )-\n" + 
						"                             sum(case when x.replied =0 and hflag=1 then 1 else 0 end ))/\n" + 
						"                             sum(case when x.replied =0 and hflag=1 then 1 else 0 end ) end)\n" + 
						"                             ,4)*100,'fm9999999990.0999')||'%'  weihuifu,\n" + 
						"                to_char(round(\n" + 
						"                     (case when sum(case when x.replied =1 and hflag=1 then 1 else 0 end )=0\n" + 
						"                           and  sum(case when x.replied =1 and flag=1 then 1 else 0 end )=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.replied =1 and hflag=1 then 1 else 0 end )=0\n" + 
						"                           and  sum(case when x.replied =1 and flag=1 then 1 else 0 end )<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.replied =1 and flag=1 then 1 else 0 end )-\n" + 
						"                           sum(case when x.replied =1 and hflag=1 then 1 else 0 end ))/\n" + 
						"                           sum(case when x.replied =1 and hflag=1 then 1 else 0 end ) end)\n" + 
						"                            ,4)*100,'fm9999999990.0999')||'%' as yihuifu,\n" + 
						"                to_char(round(\n" + 
						"                     (case when sum(case when x.visitconfirm =1 and hflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.visitconfirm =1 and hflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)-\n" + 
						"                           sum(case when x.visitconfirm =1 and hflag=1 then 1 else 0 end))/\n" + 
						"                           sum(case when x.visitconfirm =1 and hflag=1 then 1 else 0 end) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  shushi,\n" + 
						"               to_char(round(\n" + 
						"                     (case when sum(case when x.visitconfirm =2 and hflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.visitconfirm =2 and hflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)-\n" + 
						"                           sum(case when x.visitconfirm =2 and hflag=1 then 1 else 0 end))/\n" + 
						"                           sum(case when x.visitconfirm =2 and hflag=1 then 1 else 0 end) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  bushushi,\n" + 
						"                           to_char(round(\n" + 
						"                     (case when sum(hflag)=0  and  sum(flag)=0   then 0\n" + 
						"                           when sum(hflag)=0  and  sum(flag)<>0  then 1\n" + 
						"                             else(sum(flag)- sum(hflag))/sum(hflag) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  heji,\n" + 
						"                 ' ' as zhanbi, ' ' as huanbishu, ' ' as huanbi, ' ' as tongbishu, ' ' as tongbi\n" + 
						"                 from x\n" + 
						"            union all\n" + 
						"              select '去年同期数据'  as name ,'99994' as code,\n" + 
						"              to_char(sum(case when x.replied =0 and tflag=1 then 1 else 0 end )/2) weihuifu,\n" + 
						"                 to_char(sum(case when x.replied =1 and tflag=1 then 1 else 0 end )/2) as yihuifu,\n" + 
						"                to_char( sum(case when x.visitconfirm =1 and tflag=1 then 1 else 0 end)/2) shushi,\n" + 
						"                 to_char(sum(case when x.visitconfirm =2 and tflag=1 then 1 else 0 end)/2) bushushi,\n" + 
						"                 to_char(sum(tflag)/2) heji,\n" + 
						"                 ' ' as zhanbi, ' ' as huanbishu, ' ' as huanbi, ' ' as tongbishu, ' ' as tongbi\n" + 
						"                 from x\n" + 
						"                union all\n" + 
						"               select '同比增减（%）'  as name ,'99995' as code,\n" + 
						"                to_char(round(\n" + 
						"                (case when sum(case when x.replied =0 and tflag=1 then 1 else 0 end )=0\n" + 
						"                             and  sum(case when x.replied =0 and flag=1 then 1 else 0 end )=0\n" + 
						"                             then 0\n" + 
						"                             when sum(case when x.replied =0 and tflag=1 then 1 else 0 end )=0\n" + 
						"                             and  sum(case when x.replied =0 and flag=1 then 1 else 0 end )<>0\n" + 
						"                             then 1 else\n" + 
						"                             (sum(case when x.replied =0 and flag=1 then 1 else 0 end )-\n" + 
						"                             sum(case when x.replied =0 and tflag=1 then 1 else 0 end ))/\n" + 
						"                             sum(case when x.replied =0 and tflag=1 then 1 else 0 end ) end)\n" + 
						"                             ,4)*100,'fm9999999990.0999')||'%'  weihuifu,\n" + 
						"                to_char(round(\n" + 
						"                     (case when sum(case when x.replied =1 and tflag=1 then 1 else 0 end )=0\n" + 
						"                           and  sum(case when x.replied =1 and flag=1 then 1 else 0 end )=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.replied =1 and tflag=1 then 1 else 0 end )=0\n" + 
						"                           and  sum(case when x.replied =1 and flag=1 then 1 else 0 end )<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.replied =1 and flag=1 then 1 else 0 end )-\n" + 
						"                           sum(case when x.replied =1 and tflag=1 then 1 else 0 end ))/\n" + 
						"                           sum(case when x.replied =1 and tflag=1 then 1 else 0 end ) end)\n" + 
						"                            ,4)*100,'fm9999999990.0999')||'%' as yihuifu,\n" + 
						"                to_char(round(\n" + 
						"                     (case when sum(case when x.visitconfirm =1 and tflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.visitconfirm =1 and tflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)-\n" + 
						"                           sum(case when x.visitconfirm =1 and tflag=1 then 1 else 0 end))/\n" + 
						"                           sum(case when x.visitconfirm =1 and tflag=1 then 1 else 0 end) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  shushi,\n" + 
						"               to_char(round(\n" + 
						"                     (case when sum(case when x.visitconfirm =2 and tflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.visitconfirm =2 and tflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)-\n" + 
						"                           sum(case when x.visitconfirm =2 and tflag=1 then 1 else 0 end))/\n" + 
						"                           sum(case when x.visitconfirm =2 and tflag=1 then 1 else 0 end) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  bushushi,\n" + 
						"                           to_char(round(\n" + 
						"                     (case when sum(tflag)=0  and  sum(flag)=0   then 0\n" + 
						"                           when sum(tflag)=0  and  sum(flag)<>0  then 1\n" + 
						"                             else(sum(flag)- sum(tflag))/sum(tflag) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  heji,\n" + 
						"                 ' ' as zhanbi,  ' ' as huanbishu,  ' ' as huanbi,  ' ' as tongbishu, ' ' as tongbi\n" + 
						"                 from x\n" + 
						"                 order by code asc,name desc");
		try {
			res=dao.queryForList(sb.toString(), list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("结案信息咨询建议承办部门统计报表", "WDY", i == 1 ? "查看报表" : "下载报表", sb.toString(),
				map.get("begintime")+","+map.get("endtime"), req);
		return res;
	}
	public List<Map> zj_JieShouFangShi(Map<String, String> map, int i,
			HttpServletRequest req) {
		List list=new ArrayList();
		StringBuffer sb=new StringBuffer();
		List<Map> res=new ArrayList<Map>();
		sb.append(
				"with x as (\n" +
						"  select  /*+ parallel(8)*/  j.name ,decode(f.replied,1,1,0) replied ,v.visitconfirm visitconfirm,\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) flag,\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) tflag,  --同比\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) hflag  --环比\n" + 
						"   from dc_cpr_infoware i,DC_CPR_RETURN_VISIT v,\n" + 
						"  dc_cpr_feedback f,DC_CPR_INQUIRY q,DC_CPR_INFO_PROVIDER p,dc_cpr_involved_main m,\n" + 
						"  dc_code.dc_12315_codedata j\n" + 
						"  where i.feedbackid=f.feedbackid(+)\n" + 
						"  and i.infowareid=v.infowareid(+)\n" + 
						"  and i.INQUIRYID=q.inquiryid(+)\n" + 
						"  and i.INFPROID=p.INFPROID(+)\n" + 
						"  and i.Invmaiid=m.INVMAIID(+)\n" + 
						"  and j.codetable(+)='CH03'\n" + 
						"  and j.code(+)=i.incform \n");
		list.add(map.get("begintime"));
		list.add(map.get("endtime")+" 23:59:59");
		list.add(QueryJieAnUtils.getTongBiDate(map.get("begintime")));
		list.add(QueryJieAnUtils.getTongBiDate(map.get("endtime"))+" 23:59:59");
		if (map.get("hbegintime")!=null&&map.get("hbegintime").length()!=0) {
			list.add(map.get("hbegintime"));
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(map.get("begintime")));
		}
		if (map.get("hendtime")!=null&&map.get("hendtime").length()!=0) {
			list.add(map.get("hendtime")+" 23:59:59");
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(map.get("endtime"))+" 23:59:59");
		}
		if (map.get("infotype")!=null&&map.get("infotype").length()!=0) {
			sb.append("and i.inftype =? \n");
			list.add(map.get("infotype"));
		}else{
			sb.append("and i.inftype in ('3','4') \n");
		}
		
		if (map.get("dengjibumen")!=null&&map.get("dengjibumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("dengjibumen"),"i.REGDEPCODE"));
		}
		if (map.get("chengbanbumen")!=null&&map.get("chengbanbumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("chengbanbumen"),"i.HANDEPCODE"));
		}
		if (map.get("jieshoufangshi")!=null&&map.get("jieshoufangshi").length()!=0) {
			sb.append(" and i.INCFORM =? \n");
			list.add(map.get("jieshoufangshi"));
		}
		if (map.get("suoshubumen")!=null&&map.get("suoshubumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("suoshubumen"),"q.INQDEPCODE"));
		}
		if (map.get("wentileixing")!=null&&map.get("wentileixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("wentileixing"),"i.APPLBASQUE"));
		}
		if (map.get("yewufanwei")!=null&&map.get("yewufanwei").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("yewufanwei"),"q.BUSINESSRANGE"));
		}
		if (map.get("renyuanshenfen")!=null&&map.get("renyuanshenfen").length()!=0) {
			sb.append(" and p.PERIDE=? \n");
			list.add(map.get("renyuanshenfen"));
		}
		if (map.get("hangyeleixing")!=null&&map.get("hangyeleixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("hangyeleixing"),"m.TRADETYPE"));
		}
		if (map.get("qiyeleixing")!=null&&map.get("qiyeleixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("qiyeleixing"),"m.ENTTYPE"));
		}
		sb.append(
				")\n" +
						"  select x.name ,to_char(sum(case when x.replied =0 and flag=1 then 1 else 0 end )) weihuifu,\n" + 
						"                 to_char(sum(case when x.replied =1 and flag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"                to_char( sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)) shushi,\n" + 
						"                 to_char(sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)) bushushi,\n" + 
						"                 to_char(sum(flag)) heji,\n" + 
						"                 to_char(round(ratio_to_report(sum(x.flag)) over () ,4)*100,'fm9999999990.0999')||'%' as zhanbi,\n" + 
						"                 to_char(sum(hflag)) huanbishu,\n" + 
						"                 to_char(round((case when sum(hflag)=0 and  sum(flag)=0 then 0\n" + 
						"                       when sum(hflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                       else  (sum(flag)-sum(hflag))/sum(hflag) end ),4)*100,'fm999990.0999')||'%' huanbi,\n" + 
						"                 to_char(sum(tflag)) tongbishu,\n" + 
						"                 to_char(round((case when sum(tflag)=0 and  sum(flag)=0 then 0\n" + 
						"                       when sum(tflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                       else  (sum(flag)-sum(tflag))/sum(tflag) end ),4)*100,'fm9999999990.0999')||'%' tongbi\n" + 
						"                 from x group by x.name\n" + 
						"                 union all\n" + 
						"                 select '占总量' as name,\n" + 
						"                  to_char(round((case when\n" + 
						"                 sum(case when x.replied =0 and flag=1 then 1 else 0 end )=0 then 0 else\n" + 
						"                 sum(case when x.replied =0 and flag=1 then 1 else 0 end )/sum(flag) end\n" + 
						"                 ),4)*100,'fm9999999990.0999')||'%' weihuifu,\n" + 
						"                 to_char(round((case when\n" + 
						"                 sum(case when x.replied =1 and flag=1 then 1 else 0 end )=0 then 0 else\n" + 
						"                 sum(case when x.replied =1 and flag=1 then 1 else 0 end )/sum(flag) end\n" + 
						"                 ),4)*100,'fm9999999990.0999')||'%' yihuifu,\n" + 
						"                  to_char(round((case when\n" + 
						"                 sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)=0 then 0 else\n" + 
						"                 sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)/sum(flag) end\n" + 
						"                 ) ,4)*100,'fm9999999990.0999')||'%'shushi,\n" + 
						"                  to_char(round((case when\n" + 
						"                 sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)=0 then 0 else\n" + 
						"                 sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)/sum(flag) end\n" + 
						"                 ) ,4)*100,'fm9999999990.0999')||'%'bushushi,\n" + 
						"                  to_char(round((case when sum(flag)=0 then 0 else\n" + 
						"                 sum(flag)/sum(flag) end),4)*100,'fm9999999990.0999')||'%' heji ,\n" + 
						"                 ' ' as zhanbi, ' ' as huanbishu,' ' as huanbi,' ' as tongbishu, ' ' as tongbi\n" + 
						"                 from x\n" + 
						"              union all\n" + 
						"              select '上时段数据'  as name ,\n" + 
						"              to_char(sum(case when x.replied =0 and hflag=1 then 1 else 0 end )) weihuifu,\n" + 
						"                 to_char(sum(case when x.replied =1 and hflag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"                to_char( sum(case when x.visitconfirm =1 and hflag=1 then 1 else 0 end)) shushi,\n" + 
						"                 to_char(sum(case when x.visitconfirm =2 and hflag=1 then 1 else 0 end)) bushushi,\n" + 
						"                 to_char(sum(hflag)) heji,\n" + 
						"                 ' ' as zhanbi, ' ' as huanbishu, ' ' as huanbi, ' ' as tongbishu, ' ' as tongbi\n" + 
						"                 from x\n" + 
						"              union all\n" + 
						"               select '环比增减（%）'  as name ,\n" + 
						"                to_char(round(\n" + 
						"                (case when sum(case when x.replied =0 and hflag=1 then 1 else 0 end )=0\n" + 
						"                             and  sum(case when x.replied =0 and flag=1 then 1 else 0 end )=0\n" + 
						"                             then 0\n" + 
						"                             when sum(case when x.replied =0 and hflag=1 then 1 else 0 end )=0\n" + 
						"                             and  sum(case when x.replied =0 and flag=1 then 1 else 0 end )<>0\n" + 
						"                             then 1 else\n" + 
						"                             (sum(case when x.replied =0 and flag=1 then 1 else 0 end )-\n" + 
						"                             sum(case when x.replied =0 and hflag=1 then 1 else 0 end ))/\n" + 
						"                             sum(case when x.replied =0 and hflag=1 then 1 else 0 end ) end)\n" + 
						"                             ,4)*100,'fm9999999990.0999')||'%'  weihuifu,\n" + 
						"                to_char(round(\n" + 
						"                     (case when sum(case when x.replied =1 and hflag=1 then 1 else 0 end )=0\n" + 
						"                           and  sum(case when x.replied =1 and flag=1 then 1 else 0 end )=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.replied =1 and hflag=1 then 1 else 0 end )=0\n" + 
						"                           and  sum(case when x.replied =1 and flag=1 then 1 else 0 end )<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.replied =1 and flag=1 then 1 else 0 end )-\n" + 
						"                           sum(case when x.replied =1 and hflag=1 then 1 else 0 end ))/\n" + 
						"                           sum(case when x.replied =1 and hflag=1 then 1 else 0 end ) end)\n" + 
						"                            ,4)*100,'fm9999999990.0999')||'%' as yihuifu,\n" + 
						"                to_char(round(\n" + 
						"                     (case when sum(case when x.visitconfirm =1 and hflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.visitconfirm =1 and hflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)-\n" + 
						"                           sum(case when x.visitconfirm =1 and hflag=1 then 1 else 0 end))/\n" + 
						"                           sum(case when x.visitconfirm =1 and hflag=1 then 1 else 0 end) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  shushi,\n" + 
						"               to_char(round(\n" + 
						"                     (case when sum(case when x.visitconfirm =2 and hflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.visitconfirm =2 and hflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)-\n" + 
						"                           sum(case when x.visitconfirm =2 and hflag=1 then 1 else 0 end))/\n" + 
						"                           sum(case when x.visitconfirm =2 and hflag=1 then 1 else 0 end) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  bushushi,\n" + 
						"                           to_char(round(\n" + 
						"                     (case when sum(hflag)=0  and  sum(flag)=0   then 0\n" + 
						"                           when sum(hflag)=0  and  sum(flag)<>0  then 1\n" + 
						"                             else(sum(flag)- sum(hflag))/sum(hflag) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  heji,\n" + 
						"                 ' ' as zhanbi, ' ' as huanbishu, ' ' as huanbi, ' ' as tongbishu, ' ' as tongbi\n" + 
						"                 from x\n" + 
						"            union all\n" + 
						"              select '去年同期数据'  as name ,\n" + 
						"              to_char(sum(case when x.replied =0 and tflag=1 then 1 else 0 end )) weihuifu,\n" + 
						"                 to_char(sum(case when x.replied =1 and tflag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"                to_char( sum(case when x.visitconfirm =1 and tflag=1 then 1 else 0 end)) shushi,\n" + 
						"                 to_char(sum(case when x.visitconfirm =2 and tflag=1 then 1 else 0 end)) bushushi,\n" + 
						"                 to_char(sum(tflag)) heji,\n" + 
						"                 ' ' as zhanbi, ' ' as huanbishu, ' ' as huanbi, ' ' as tongbishu, ' ' as tongbi\n" + 
						"                 from x\n" + 
						"                union all\n" + 
						"               select '同比增减（%）'  as name ,\n" + 
						"                to_char(round(\n" + 
						"                (case when sum(case when x.replied =0 and tflag=1 then 1 else 0 end )=0\n" + 
						"                             and  sum(case when x.replied =0 and flag=1 then 1 else 0 end )=0\n" + 
						"                             then 0\n" + 
						"                             when sum(case when x.replied =0 and tflag=1 then 1 else 0 end )=0\n" + 
						"                             and  sum(case when x.replied =0 and flag=1 then 1 else 0 end )<>0\n" + 
						"                             then 1 else\n" + 
						"                             (sum(case when x.replied =0 and flag=1 then 1 else 0 end )-\n" + 
						"                             sum(case when x.replied =0 and tflag=1 then 1 else 0 end ))/\n" + 
						"                             sum(case when x.replied =0 and tflag=1 then 1 else 0 end ) end)\n" + 
						"                             ,4)*100,'fm9999999990.0999')||'%'  weihuifu,\n" + 
						"                to_char(round(\n" + 
						"                     (case when sum(case when x.replied =1 and tflag=1 then 1 else 0 end )=0\n" + 
						"                           and  sum(case when x.replied =1 and flag=1 then 1 else 0 end )=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.replied =1 and tflag=1 then 1 else 0 end )=0\n" + 
						"                           and  sum(case when x.replied =1 and flag=1 then 1 else 0 end )<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.replied =1 and flag=1 then 1 else 0 end )-\n" + 
						"                           sum(case when x.replied =1 and tflag=1 then 1 else 0 end ))/\n" + 
						"                           sum(case when x.replied =1 and tflag=1 then 1 else 0 end ) end)\n" + 
						"                            ,4)*100,'fm9999999990.0999')||'%' as yihuifu,\n" + 
						"                to_char(round(\n" + 
						"                     (case when sum(case when x.visitconfirm =1 and tflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.visitconfirm =1 and tflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)-\n" + 
						"                           sum(case when x.visitconfirm =1 and tflag=1 then 1 else 0 end))/\n" + 
						"                           sum(case when x.visitconfirm =1 and tflag=1 then 1 else 0 end) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  shushi,\n" + 
						"               to_char(round(\n" + 
						"                     (case when sum(case when x.visitconfirm =2 and tflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.visitconfirm =2 and tflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)-\n" + 
						"                           sum(case when x.visitconfirm =2 and tflag=1 then 1 else 0 end))/\n" + 
						"                           sum(case when x.visitconfirm =2 and tflag=1 then 1 else 0 end) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  bushushi,\n" + 
						"                           to_char(round(\n" + 
						"                     (case when sum(tflag)=0  and  sum(flag)=0   then 0\n" + 
						"                           when sum(tflag)=0  and  sum(flag)<>0  then 1\n" + 
						"                             else(sum(flag)- sum(tflag))/sum(tflag) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  heji,\n" + 
						"                 ' ' as zhanbi, ' ' as huanbishu, ' ' as huanbi, ' ' as tongbishu, ' ' as tongbi\n" + 
						"                 from x");
		try {
			res=dao.queryForList(sb.toString(),list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("结案信息咨询建议接收方式统计报表", "WDY", i == 1 ? "查看报表" : "下载报表", sb.toString(),
				map.get("begintime")+","+map.get("endtime"), req);
		return res;
	}
	public List<Map> zj_HangYeLeiXing(Map<String, String> map, int i,
			HttpServletRequest req) {
		List list=new ArrayList();
		StringBuffer sb=new StringBuffer();
		List<Map> res=new ArrayList<Map>();
		sb.append(
						"with x as (\n" +
						" select  /*+ parallel(8)*/ J.PARENTCODE, J.PARENTNAME,j.CODE, CASE WHEN j.PARENTCODE = '0' THEN j.name ELSE '　'||j.name END AS NAME ,\n" + 
						" decode(f.replied,1,1,0) replied ,v.visitconfirm visitconfirm,\n" + 
						" (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						" and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) flag,\n" + 
						" (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						" and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) tflag,  --同比\n" + 
						" (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						" and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) hflag  --环比\n" + 
						"  from dc_cpr_infoware i,DC_CPR_RETURN_VISIT v,\n" + 
						" dc_cpr_feedback f,DC_CPR_INQUIRY q,DC_CPR_INFO_PROVIDER p,dc_cpr_involved_main m,\n" + 
						" (SELECT S.CODE, S.NAME, DECODE(D.NAME,NULL,S.CODE,S.PARENTCODE) AS PARENTCODE, NVL(D.NAME,S.NAME) AS PARENTNAME\n" + 
						"   FROM DC_CODE.DC_12315_CODEDATA S, DC_CODE.DC_12315_CODEDATA D\n" + 
						"  WHERE S.CODETABLE = 'CH15'\n" + 
						"    AND D.CODETABLE(+) = 'CH15'\n" + 
						"    AND S.PARENTCODE = D.CODE(+)) j\n" + 
						" where i.feedbackid=f.feedbackid(+)\n" + 
						" and i.infowareid=v.infowareid(+)\n" + 
						" and i.INQUIRYID=q.inquiryid(+)\n" + 
						" and i.INFPROID=p.INFPROID(+)\n" + 
						" and i.Invmaiid=m.INVMAIID(+)\n" + 
						" and j.code(+)=M.TRADETYPE \n");
		list.add(map.get("begintime"));
		list.add(map.get("endtime")+" 23:59:59");
		list.add(QueryJieAnUtils.getTongBiDate(map.get("begintime")));
		list.add(QueryJieAnUtils.getTongBiDate(map.get("endtime"))+" 23:59:59");
		if (map.get("hbegintime")!=null&&map.get("hbegintime").length()!=0) {
			list.add(map.get("hbegintime"));
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(map.get("begintime")));
		}
		if (map.get("hendtime")!=null&&map.get("hendtime").length()!=0) {
			list.add(map.get("hendtime")+" 23:59:59");
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(map.get("endtime"))+" 23:59:59");
		}
		if (map.get("infotype")!=null&&map.get("infotype").length()!=0) {
			sb.append("and i.inftype =? \n");
			list.add(map.get("infotype"));
		}else{
			sb.append("and i.inftype in ('3','4') \n");
		}
		
		if (map.get("dengjibumen")!=null&&map.get("dengjibumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("dengjibumen"),"i.REGDEPCODE"));
		}
		if (map.get("chengbanbumen")!=null&&map.get("chengbanbumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("chengbanbumen"),"i.HANDEPCODE"));
		}
		if (map.get("jieshoufangshi")!=null&&map.get("jieshoufangshi").length()!=0) {
			sb.append(" and i.INCFORM =? \n");
			list.add(map.get("jieshoufangshi"));
		}
		if (map.get("suoshubumen")!=null&&map.get("suoshubumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("suoshubumen"),"q.INQDEPCODE"));
		}
		if (map.get("wentileixing")!=null&&map.get("wentileixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("wentileixing"),"i.APPLBASQUE"));
		}
		if (map.get("yewufanwei")!=null&&map.get("yewufanwei").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("yewufanwei"),"q.BUSINESSRANGE"));
		}
		if (map.get("renyuanshenfen")!=null&&map.get("renyuanshenfen").length()!=0) {
			sb.append(" and p.PERIDE=? \n");
			list.add(map.get("renyuanshenfen"));
		}
		if (map.get("hangyeleixing")!=null&&map.get("hangyeleixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("hangyeleixing"),"m.TRADETYPE"));
		}
		if (map.get("qiyeleixing")!=null&&map.get("qiyeleixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("qiyeleixing"),"m.ENTTYPE"));
		}
		sb.append(
				"),\n" +
						"  X1 AS (SELECT x.name ,X.CODE,to_char(sum(case when x.replied =0 and flag=1 then 1 else 0 end )) weihuifu,\n" + 
						"                 to_char(sum(case when x.replied =1 and flag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"                to_char( sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)) shushi,\n" + 
						"                 to_char(sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)) bushushi,\n" + 
						"                 to_char(sum(flag)) heji,\n" + 
						"                 to_char(round(ratio_to_report(sum(x.flag)) over () ,4)*100,'fm9999999990.0999')||'%' as zhanbi,\n" + 
						"                 to_char(sum(hflag)) huanbishu,\n" + 
						"                 to_char(round((case when sum(hflag)=0 and  sum(flag)=0 then 0\n" + 
						"                       when sum(hflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                       else  (sum(flag)-sum(hflag))/sum(hflag) end ),4)*100,'fm999990.0999')||'%' huanbi,\n" + 
						"                 to_char(sum(tflag)) tongbishu,\n" + 
						"                 to_char(round((case when sum(tflag)=0 and  sum(flag)=0 then 0\n" + 
						"                       when sum(tflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                       else  (sum(flag)-sum(tflag))/sum(tflag) end ),4)*100,'fm9999999990.0999')||'%' tongbi\n" + 
						"                 from x group BY x.name,X.CODE\n" + 
						"UNION ALL\n" + 
						"select x.PARENTNAME AS NAME,X.PARENTCODE AS CODE,to_char(sum(case when x.replied =0 and flag=1 then 1 else 0 end )) weihuifu,\n" + 
						"                 to_char(sum(case when x.replied =1 and flag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"                to_char( sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)) shushi,\n" + 
						"                 to_char(sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)) bushushi,\n" + 
						"                 to_char(sum(flag)) heji,\n" + 
						"                 to_char(round(ratio_to_report(sum(x.flag)) over () ,4)*100,'fm9999999990.0999')||'%' as zhanbi,\n" + 
						"                 to_char(sum(hflag)) huanbishu,\n" + 
						"                 to_char(round((case when sum(hflag)=0 and  sum(flag)=0 then 0\n" + 
						"                       when sum(hflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                       else  (sum(flag)-sum(hflag))/sum(hflag) end ),4)*100,'fm999990.0999')||'%' huanbi,\n" + 
						"                 to_char(sum(tflag)) tongbishu,\n" + 
						"                 to_char(round((case when sum(tflag)=0 and  sum(flag)=0 then 0\n" + 
						"                       when sum(tflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                       else  (sum(flag)-sum(tflag))/sum(tflag) end ),4)*100,'fm9999999990.0999')||'%' tongbi\n" + 
						"                 from x group by x.PARENTNAME,X.PARENTCODE\n" + 
						"                 ),\n" + 
						"X2 AS ( SELECT NAME,CODE,weihuifu,yihuifu,shushi,bushushi,heji,zhanbi,huanbishu,huanbi,tongbishu,tongbi FROM x1 ORDER BY CODE ,name desc)\n" + 
						"                  SELECT NAME,weihuifu,yihuifu,shushi,bushushi,heji,zhanbi,huanbishu,huanbi,tongbishu,tongbi FROM x2\n" + 
						"                 union all\n" + 
						"                 select '占总量' as name,\n" + 
						"                  to_char(round((case when\n" + 
						"                 sum(case when x.replied =0 and flag=1 then 1 else 0 end )=0 then 0 else\n" + 
						"                 sum(case when x.replied =0 and flag=1 then 1 else 0 end )/sum(flag) end\n" + 
						"                 ),4)*100,'fm9999999990.0999')||'%' weihuifu,\n" + 
						"                 to_char(round((case when\n" + 
						"                 sum(case when x.replied =1 and flag=1 then 1 else 0 end )=0 then 0 else\n" + 
						"                 sum(case when x.replied =1 and flag=1 then 1 else 0 end )/sum(flag) end\n" + 
						"                 ),4)*100,'fm9999999990.0999')||'%' yihuifu,\n" + 
						"                  to_char(round((case when\n" + 
						"                 sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)=0 then 0 else\n" + 
						"                 sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)/sum(flag) end\n" + 
						"                 ) ,4)*100,'fm9999999990.0999')||'%'shushi,\n" + 
						"                  to_char(round((case when\n" + 
						"                 sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)=0 then 0 else\n" + 
						"                 sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)/sum(flag) end\n" + 
						"                 ) ,4)*100,'fm9999999990.0999')||'%'bushushi,\n" + 
						"                  to_char(round((case when sum(flag)=0 then 0 else\n" + 
						"                 sum(flag)/sum(flag) end),4)*100,'fm9999999990.0999')||'%' heji ,\n" + 
						"                 ' ' as zhanbi, ' ' as huanbishu,' ' as huanbi,' ' as tongbishu, ' ' as tongbi\n" + 
						"                 from x\n" + 
						"              union all\n" + 
						"              select '上时段数据'  as name ,\n" + 
						"              to_char(sum(case when x.replied =0 and hflag=1 then 1 else 0 end )) weihuifu,\n" + 
						"                 to_char(sum(case when x.replied =1 and hflag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"                to_char( sum(case when x.visitconfirm =1 and hflag=1 then 1 else 0 end)) shushi,\n" + 
						"                 to_char(sum(case when x.visitconfirm =2 and hflag=1 then 1 else 0 end)) bushushi,\n" + 
						"                 to_char(sum(hflag)) heji,\n" + 
						"                 ' ' as zhanbi, ' ' as huanbishu, ' ' as huanbi, ' ' as tongbishu, ' ' as tongbi\n" + 
						"                 from x\n" + 
						"              union all\n" + 
						"               select '环比增减（%）'  as name ,\n" + 
						"                to_char(round(\n" + 
						"                (case when sum(case when x.replied =0 and hflag=1 then 1 else 0 end )=0\n" + 
						"                             and  sum(case when x.replied =0 and flag=1 then 1 else 0 end )=0\n" + 
						"                             then 0\n" + 
						"                             when sum(case when x.replied =0 and hflag=1 then 1 else 0 end )=0\n" + 
						"                             and  sum(case when x.replied =0 and flag=1 then 1 else 0 end )<>0\n" + 
						"                             then 1 else\n" + 
						"                             (sum(case when x.replied =0 and flag=1 then 1 else 0 end )-\n" + 
						"                             sum(case when x.replied =0 and hflag=1 then 1 else 0 end ))/\n" + 
						"                             sum(case when x.replied =0 and hflag=1 then 1 else 0 end ) end)\n" + 
						"                             ,4)*100,'fm9999999990.0999')||'%'  weihuifu,\n" + 
						"                to_char(round(\n" + 
						"                     (case when sum(case when x.replied =1 and hflag=1 then 1 else 0 end )=0\n" + 
						"                           and  sum(case when x.replied =1 and flag=1 then 1 else 0 end )=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.replied =1 and hflag=1 then 1 else 0 end )=0\n" + 
						"                           and  sum(case when x.replied =1 and flag=1 then 1 else 0 end )<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.replied =1 and flag=1 then 1 else 0 end )-\n" + 
						"                           sum(case when x.replied =1 and hflag=1 then 1 else 0 end ))/\n" + 
						"                           sum(case when x.replied =1 and hflag=1 then 1 else 0 end ) end)\n" + 
						"                            ,4)*100,'fm9999999990.0999')||'%' as yihuifu,\n" + 
						"                to_char(round(\n" + 
						"                     (case when sum(case when x.visitconfirm =1 and hflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.visitconfirm =1 and hflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)-\n" + 
						"                           sum(case when x.visitconfirm =1 and hflag=1 then 1 else 0 end))/\n" + 
						"                           sum(case when x.visitconfirm =1 and hflag=1 then 1 else 0 end) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  shushi,\n" + 
						"               to_char(round(\n" + 
						"                     (case when sum(case when x.visitconfirm =2 and hflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.visitconfirm =2 and hflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)-\n" + 
						"                           sum(case when x.visitconfirm =2 and hflag=1 then 1 else 0 end))/\n" + 
						"                           sum(case when x.visitconfirm =2 and hflag=1 then 1 else 0 end) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  bushushi,\n" + 
						"                           to_char(round(\n" + 
						"                     (case when sum(hflag)=0  and  sum(flag)=0   then 0\n" + 
						"                           when sum(hflag)=0  and  sum(flag)<>0  then 1\n" + 
						"                             else(sum(flag)- sum(hflag))/sum(hflag) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  heji,\n" + 
						"                 ' ' as zhanbi, ' ' as huanbishu, ' ' as huanbi, ' ' as tongbishu, ' ' as tongbi\n" + 
						"                 from x\n" + 
						"            union all\n" + 
						"              select '去年同期数据'  as name ,\n" + 
						"              to_char(sum(case when x.replied =0 and tflag=1 then 1 else 0 end )) weihuifu,\n" + 
						"                 to_char(sum(case when x.replied =1 and tflag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"                to_char( sum(case when x.visitconfirm =1 and tflag=1 then 1 else 0 end)) shushi,\n" + 
						"                 to_char(sum(case when x.visitconfirm =2 and tflag=1 then 1 else 0 end)) bushushi,\n" + 
						"                 to_char(sum(tflag)) heji,\n" + 
						"                 ' ' as zhanbi, ' ' as huanbishu, ' ' as huanbi, ' ' as tongbishu, ' ' as tongbi\n" + 
						"                 from x\n" + 
						"                union all\n" + 
						"               select '同比增减（%）'  as name ,\n" + 
						"                to_char(round(\n" + 
						"                (case when sum(case when x.replied =0 and tflag=1 then 1 else 0 end )=0\n" + 
						"                             and  sum(case when x.replied =0 and flag=1 then 1 else 0 end )=0\n" + 
						"                             then 0\n" + 
						"                             when sum(case when x.replied =0 and tflag=1 then 1 else 0 end )=0\n" + 
						"                             and  sum(case when x.replied =0 and flag=1 then 1 else 0 end )<>0\n" + 
						"                             then 1 else\n" + 
						"                             (sum(case when x.replied =0 and flag=1 then 1 else 0 end )-\n" + 
						"                             sum(case when x.replied =0 and tflag=1 then 1 else 0 end ))/\n" + 
						"                             sum(case when x.replied =0 and tflag=1 then 1 else 0 end ) end)\n" + 
						"                             ,4)*100,'fm9999999990.0999')||'%'  weihuifu,\n" + 
						"                to_char(round(\n" + 
						"                     (case when sum(case when x.replied =1 and tflag=1 then 1 else 0 end )=0\n" + 
						"                           and  sum(case when x.replied =1 and flag=1 then 1 else 0 end )=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.replied =1 and tflag=1 then 1 else 0 end )=0\n" + 
						"                           and  sum(case when x.replied =1 and flag=1 then 1 else 0 end )<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.replied =1 and flag=1 then 1 else 0 end )-\n" + 
						"                           sum(case when x.replied =1 and tflag=1 then 1 else 0 end ))/\n" + 
						"                           sum(case when x.replied =1 and tflag=1 then 1 else 0 end ) end)\n" + 
						"                            ,4)*100,'fm9999999990.0999')||'%' as yihuifu,\n" + 
						"                to_char(round(\n" + 
						"                     (case when sum(case when x.visitconfirm =1 and tflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.visitconfirm =1 and tflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)-\n" + 
						"                           sum(case when x.visitconfirm =1 and tflag=1 then 1 else 0 end))/\n" + 
						"                           sum(case when x.visitconfirm =1 and tflag=1 then 1 else 0 end) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  shushi,\n" + 
						"               to_char(round(\n" + 
						"                     (case when sum(case when x.visitconfirm =2 and tflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.visitconfirm =2 and tflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)-\n" + 
						"                           sum(case when x.visitconfirm =2 and tflag=1 then 1 else 0 end))/\n" + 
						"                           sum(case when x.visitconfirm =2 and tflag=1 then 1 else 0 end) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  bushushi,\n" + 
						"                           to_char(round(\n" + 
						"                     (case when sum(tflag)=0  and  sum(flag)=0   then 0\n" + 
						"                           when sum(tflag)=0  and  sum(flag)<>0  then 1\n" + 
						"                             else(sum(flag)- sum(tflag))/sum(tflag) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  heji,\n" + 
						"                 ' ' as zhanbi, ' ' as huanbishu, ' ' as huanbi, ' ' as tongbishu, ' ' as tongbi\n" + 
						"                 from x");
		try {
			res=dao.queryForList(sb.toString(), list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("结案信息咨询建议行业类型统计报表", "WDY", i == 1 ? "查看报表" : "下载报表", sb.toString(),
				map.get("begintime")+","+map.get("endtime"), req);
		return res;
	}
	public List<Map> zj_SuoShuBuMen(Map<String, String> map, int i,
			HttpServletRequest req) {
		List list=new ArrayList();
		StringBuffer sb=new StringBuffer();
		List<Map> res=new ArrayList<Map>();
		sb.append(
				"with x as (\n" +
						"select  /*+ parallel(8)*/  nvl(q.INQDEPNAME,'空') AS NAME ,nvl(j.parent_dep_code,'9999') as code,decode(f.replied,1,1,0) replied ,v.visitconfirm visitconfirm,\n" + 
						"(case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) flag,\n" + 
						"(case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) tflag,  --同比\n" + 
						"(case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) hflag  --环比\n" + 
						" from dc_cpr_infoware i,DC_CPR_RETURN_VISIT v,\n" + 
						"dc_cpr_feedback f,DC_CPR_INQUIRY q,DC_CPR_INFO_PROVIDER p,dc_cpr_involved_main m,\n" + 
						"dc_jg_sys_right_department j\n" + 
						"where i.feedbackid=f.feedbackid(+)\n" + 
						"and i.infowareid=v.infowareid(+)\n" + 
						"and i.INQUIRYID=q.inquiryid(+)\n" + 
						"and i.INFPROID=p.INFPROID(+)\n" + 
						"and i.Invmaiid=m.INVMAIID(+)\n" + 
						"and q.INQDEPCODE=j.sys_right_department_id(+) \n");
		list.add(map.get("begintime"));
		list.add(map.get("endtime")+" 23:59:59");
		list.add(QueryJieAnUtils.getTongBiDate(map.get("begintime")));
		list.add(QueryJieAnUtils.getTongBiDate(map.get("endtime"))+" 23:59:59");
		if (map.get("hbegintime")!=null&&map.get("hbegintime").length()!=0) {
			list.add(map.get("hbegintime"));
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(map.get("begintime")));
		}
		if (map.get("hendtime")!=null&&map.get("hendtime").length()!=0) {
			list.add(map.get("hendtime")+" 23:59:59");
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(map.get("endtime"))+" 23:59:59");
		}
		if (map.get("infotype")!=null&&map.get("infotype").length()!=0) {
			sb.append("and i.inftype =? \n");
			list.add(map.get("infotype"));
		}else{
			sb.append("and i.inftype in ('3','4') \n");
		}
		
		if (map.get("dengjibumen")!=null&&map.get("dengjibumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("dengjibumen"),"i.REGDEPCODE"));
		}
		if (map.get("chengbanbumen")!=null&&map.get("chengbanbumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("chengbanbumen"),"i.HANDEPCODE"));
		}
		if (map.get("jieshoufangshi")!=null&&map.get("jieshoufangshi").length()!=0) {
			sb.append(" and i.INCFORM =? \n");
			list.add(map.get("jieshoufangshi"));
		}
		if (map.get("suoshubumen")!=null&&map.get("suoshubumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("suoshubumen"),"q.INQDEPCODE"));
		}
		if (map.get("wentileixing")!=null&&map.get("wentileixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("wentileixing"),"i.APPLBASQUE"));
		}
		if (map.get("yewufanwei")!=null&&map.get("yewufanwei").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("yewufanwei"),"q.BUSINESSRANGE"));
		}
		if (map.get("renyuanshenfen")!=null&&map.get("renyuanshenfen").length()!=0) {
			sb.append(" and p.PERIDE=? \n");
			list.add(map.get("renyuanshenfen"));
		}
		if (map.get("hangyeleixing")!=null&&map.get("hangyeleixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("hangyeleixing"),"m.TRADETYPE"));
		}
		if (map.get("qiyeleixing")!=null&&map.get("qiyeleixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("qiyeleixing"),"m.ENTTYPE"));
		}
		sb.append(
				")\n" +
						"  select x.name ,x.code as code,to_char(sum(case when x.replied =0 and flag=1 then 1 else 0 end )) weihuifu,\n" + 
						"                 to_char(sum(case when x.replied =1 and flag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"                to_char( sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)) shushi,\n" + 
						"                 to_char(sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)) bushushi,\n" + 
						"                 to_char(sum(flag)) heji,\n" + 
						"                 to_char(round(ratio_to_report(sum(x.flag)) over () ,4)*100,'fm9999999990.0999')||'%' as zhanbi,\n" + 
						"                 to_char(sum(hflag)) huanbishu,\n" + 
						"                 to_char(round((case when sum(hflag)=0 and  sum(flag)=0 then 0\n" + 
						"                       when sum(hflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                       else  (sum(flag)-sum(hflag))/sum(hflag) end ),4)*100,'fm999990.0999')||'%' huanbi,\n" + 
						"                 to_char(sum(tflag)) tongbishu,\n" + 
						"                 to_char(round((case when sum(tflag)=0 and  sum(flag)=0 then 0\n" + 
						"                       when sum(tflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                       else  (sum(flag)-sum(tflag))/sum(tflag) end ),4)*100,'fm9999999990.0999')||'%' tongbi\n" + 
						"                 from x group by x.name,x.code\n" + 
						"                 union all\n" + 
						"                 select '占总量' as name,'99991' as code,\n" + 
						"                  to_char(round((case when\n" + 
						"                 sum(case when x.replied =0 and flag=1 then 1 else 0 end )=0 then 0 else\n" + 
						"                 sum(case when x.replied =0 and flag=1 then 1 else 0 end )/sum(flag) end\n" + 
						"                 ),4)*100,'fm9999999990.0999')||'%' weihuifu,\n" + 
						"                 to_char(round((case when\n" + 
						"                 sum(case when x.replied =1 and flag=1 then 1 else 0 end )=0 then 0 else\n" + 
						"                 sum(case when x.replied =1 and flag=1 then 1 else 0 end )/sum(flag) end\n" + 
						"                 ),4)*100,'fm9999999990.0999')||'%' yihuifu,\n" + 
						"                  to_char(round((case when\n" + 
						"                 sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)=0 then 0 else\n" + 
						"                 sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)/sum(flag) end\n" + 
						"                 ) ,4)*100,'fm9999999990.0999')||'%'shushi,\n" + 
						"                  to_char(round((case when\n" + 
						"                 sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)=0 then 0 else\n" + 
						"                 sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)/sum(flag) end\n" + 
						"                 ) ,4)*100,'fm9999999990.0999')||'%'bushushi,\n" + 
						"                  to_char(round((case when sum(flag)=0 then 0 else\n" + 
						"                 sum(flag)/sum(flag) end),4)*100,'fm9999999990.0999')||'%' heji ,\n" + 
						"                 ' ' as zhanbi, ' ' as huanbishu,' ' as huanbi,' ' as tongbishu, ' ' as tongbi\n" + 
						"                 from x\n" + 
						"              union all\n" + 
						"              select '上时段数据'  as name ,'99992' as code,\n" + 
						"              to_char(sum(case when x.replied =0 and hflag=1 then 1 else 0 end )) weihuifu,\n" + 
						"                 to_char(sum(case when x.replied =1 and hflag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"                to_char( sum(case when x.visitconfirm =1 and hflag=1 then 1 else 0 end)) shushi,\n" + 
						"                 to_char(sum(case when x.visitconfirm =2 and hflag=1 then 1 else 0 end)) bushushi,\n" + 
						"                 to_char(sum(hflag)) heji,\n" + 
						"                 ' ' as zhanbi, ' ' as huanbishu, ' ' as huanbi, ' ' as tongbishu, ' ' as tongbi\n" + 
						"                 from x\n" + 
						"              union all\n" + 
						"               select '环比增减（%）'  as name ,'99993' as code,\n" + 
						"                to_char(round(\n" + 
						"                (case when sum(case when x.replied =0 and hflag=1 then 1 else 0 end )=0\n" + 
						"                             and  sum(case when x.replied =0 and flag=1 then 1 else 0 end )=0\n" + 
						"                             then 0\n" + 
						"                             when sum(case when x.replied =0 and hflag=1 then 1 else 0 end )=0\n" + 
						"                             and  sum(case when x.replied =0 and flag=1 then 1 else 0 end )<>0\n" + 
						"                             then 1 else\n" + 
						"                             (sum(case when x.replied =0 and flag=1 then 1 else 0 end )-\n" + 
						"                             sum(case when x.replied =0 and hflag=1 then 1 else 0 end ))/\n" + 
						"                             sum(case when x.replied =0 and hflag=1 then 1 else 0 end ) end)\n" + 
						"                             ,4)*100,'fm9999999990.0999')||'%'  weihuifu,\n" + 
						"                to_char(round(\n" + 
						"                     (case when sum(case when x.replied =1 and hflag=1 then 1 else 0 end )=0\n" + 
						"                           and  sum(case when x.replied =1 and flag=1 then 1 else 0 end )=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.replied =1 and hflag=1 then 1 else 0 end )=0\n" + 
						"                           and  sum(case when x.replied =1 and flag=1 then 1 else 0 end )<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.replied =1 and flag=1 then 1 else 0 end )-\n" + 
						"                           sum(case when x.replied =1 and hflag=1 then 1 else 0 end ))/\n" + 
						"                           sum(case when x.replied =1 and hflag=1 then 1 else 0 end ) end)\n" + 
						"                            ,4)*100,'fm9999999990.0999')||'%' as yihuifu,\n" + 
						"                to_char(round(\n" + 
						"                     (case when sum(case when x.visitconfirm =1 and hflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.visitconfirm =1 and hflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)-\n" + 
						"                           sum(case when x.visitconfirm =1 and hflag=1 then 1 else 0 end))/\n" + 
						"                           sum(case when x.visitconfirm =1 and hflag=1 then 1 else 0 end) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  shushi,\n" + 
						"               to_char(round(\n" + 
						"                     (case when sum(case when x.visitconfirm =2 and hflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.visitconfirm =2 and hflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)-\n" + 
						"                           sum(case when x.visitconfirm =2 and hflag=1 then 1 else 0 end))/\n" + 
						"                           sum(case when x.visitconfirm =2 and hflag=1 then 1 else 0 end) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  bushushi,\n" + 
						"                           to_char(round(\n" + 
						"                     (case when sum(hflag)=0  and  sum(flag)=0   then 0\n" + 
						"                           when sum(hflag)=0  and  sum(flag)<>0  then 1\n" + 
						"                             else(sum(flag)- sum(hflag))/sum(hflag) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  heji,\n" + 
						"                 ' ' as zhanbi, ' ' as huanbishu, ' ' as huanbi, ' ' as tongbishu, ' ' as tongbi\n" + 
						"                 from x\n" + 
						"            union all\n" + 
						"              select '去年同期数据'  as name ,'99994' as code,\n" + 
						"              to_char(sum(case when x.replied =0 and tflag=1 then 1 else 0 end )) weihuifu,\n" + 
						"                 to_char(sum(case when x.replied =1 and tflag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"                to_char( sum(case when x.visitconfirm =1 and tflag=1 then 1 else 0 end)) shushi,\n" + 
						"                 to_char(sum(case when x.visitconfirm =2 and tflag=1 then 1 else 0 end)) bushushi,\n" + 
						"                 to_char(sum(tflag)) heji,\n" + 
						"                 ' ' as zhanbi, ' ' as huanbishu, ' ' as huanbi, ' ' as tongbishu, ' ' as tongbi\n" + 
						"                 from x\n" + 
						"                union all\n" + 
						"               select '同比增减（%）'  as name ,'99995' as code,\n" + 
						"                to_char(round(\n" + 
						"                (case when sum(case when x.replied =0 and tflag=1 then 1 else 0 end )=0\n" + 
						"                             and  sum(case when x.replied =0 and flag=1 then 1 else 0 end )=0\n" + 
						"                             then 0\n" + 
						"                             when sum(case when x.replied =0 and tflag=1 then 1 else 0 end )=0\n" + 
						"                             and  sum(case when x.replied =0 and flag=1 then 1 else 0 end )<>0\n" + 
						"                             then 1 else\n" + 
						"                             (sum(case when x.replied =0 and flag=1 then 1 else 0 end )-\n" + 
						"                             sum(case when x.replied =0 and tflag=1 then 1 else 0 end ))/\n" + 
						"                             sum(case when x.replied =0 and tflag=1 then 1 else 0 end ) end)\n" + 
						"                             ,4)*100,'fm9999999990.0999')||'%'  weihuifu,\n" + 
						"                to_char(round(\n" + 
						"                     (case when sum(case when x.replied =1 and tflag=1 then 1 else 0 end )=0\n" + 
						"                           and  sum(case when x.replied =1 and flag=1 then 1 else 0 end )=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.replied =1 and tflag=1 then 1 else 0 end )=0\n" + 
						"                           and  sum(case when x.replied =1 and flag=1 then 1 else 0 end )<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.replied =1 and flag=1 then 1 else 0 end )-\n" + 
						"                           sum(case when x.replied =1 and tflag=1 then 1 else 0 end ))/\n" + 
						"                           sum(case when x.replied =1 and tflag=1 then 1 else 0 end ) end)\n" + 
						"                            ,4)*100,'fm9999999990.0999')||'%' as yihuifu,\n" + 
						"                to_char(round(\n" + 
						"                     (case when sum(case when x.visitconfirm =1 and tflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.visitconfirm =1 and tflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)-\n" + 
						"                           sum(case when x.visitconfirm =1 and tflag=1 then 1 else 0 end))/\n" + 
						"                           sum(case when x.visitconfirm =1 and tflag=1 then 1 else 0 end) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  shushi,\n" + 
						"               to_char(round(\n" + 
						"                     (case when sum(case when x.visitconfirm =2 and tflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.visitconfirm =2 and tflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)-\n" + 
						"                           sum(case when x.visitconfirm =2 and tflag=1 then 1 else 0 end))/\n" + 
						"                           sum(case when x.visitconfirm =2 and tflag=1 then 1 else 0 end) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  bushushi,\n" + 
						"                           to_char(round(\n" + 
						"                     (case when sum(tflag)=0  and  sum(flag)=0   then 0\n" + 
						"                           when sum(tflag)=0  and  sum(flag)<>0  then 1\n" + 
						"                             else(sum(flag)- sum(tflag))/sum(tflag) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  heji,\n" + 
						"                 ' ' as zhanbi, ' ' as huanbishu, ' ' as huanbi, ' ' as tongbishu, ' ' as tongbi\n" + 
						"                 from x\n" + 
						"                 order by code asc,name desc");
		try {
			res=dao.queryForList(sb.toString(),list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("结案信息咨询建议咨询所属部门统计报表", "WDY", i == 1 ? "查看报表" : "下载报表", sb.toString(),
		map.get("begintime")+","+map.get("endtime"), req);
	    return res;
	}
	
	public List<Map> zj_YeWuFanWei(Map<String, String> map, int i,
			HttpServletRequest req) {
		List list=new ArrayList();
		StringBuffer sb=new StringBuffer();
		List<Map> res=new ArrayList<Map>();
		sb.append(
				"with x as (\n" +
						"  select  /*+ parallel(8)*/  q.BUSINESSRANGE as code,decode(f.replied,1,1,0) replied ,v.visitconfirm visitconfirm,\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) flag,\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) tflag,  --同比\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) hflag  --环比\n" + 
						"   from dc_cpr_infoware i,DC_CPR_RETURN_VISIT v,\n" + 
						"  dc_cpr_feedback f,DC_CPR_INQUIRY q,DC_CPR_INFO_PROVIDER p,dc_cpr_involved_main m\n" + 
						"  where i.feedbackid=f.feedbackid(+)\n" + 
						"  and i.infowareid=v.infowareid(+)\n" + 
						"  and i.INQUIRYID=q.inquiryid(+)\n" + 
						"  and i.INFPROID=p.INFPROID(+)\n" + 
						"  and i.Invmaiid=m.INVMAIID(+)\n" + 
						"  and q.BUSINESSRANGE is not null\n" + 
						"  and length(trim(q.BUSINESSRANGE))=8 \n");
		list.add(map.get("begintime"));
		list.add(map.get("endtime")+" 23:59:59");
		list.add(QueryJieAnUtils.getTongBiDate(map.get("begintime")));
		list.add(QueryJieAnUtils.getTongBiDate(map.get("endtime"))+" 23:59:59");
		if (map.get("hbegintime")!=null&&map.get("hbegintime").length()!=0) {
			list.add(map.get("hbegintime"));
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(map.get("begintime")));
		}
		if (map.get("hendtime")!=null&&map.get("hendtime").length()!=0) {
			list.add(map.get("hendtime")+" 23:59:59");
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(map.get("endtime"))+" 23:59:59");
		}
		if (map.get("infotype")!=null&&map.get("infotype").length()!=0) {
			sb.append("and i.inftype =? \n");
			list.add(map.get("infotype"));
		}else{
			sb.append("and i.inftype in ('3','4') \n");
		}
		
		if (map.get("dengjibumen")!=null&&map.get("dengjibumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("dengjibumen"),"i.REGDEPCODE"));
		}
		if (map.get("chengbanbumen")!=null&&map.get("chengbanbumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("chengbanbumen"),"i.HANDEPCODE"));
		}
		if (map.get("jieshoufangshi")!=null&&map.get("jieshoufangshi").length()!=0) {
			sb.append(" and i.INCFORM =? \n");
			list.add(map.get("jieshoufangshi"));
		}
		if (map.get("suoshubumen")!=null&&map.get("suoshubumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("suoshubumen"),"q.INQDEPCODE"));
		}
		if (map.get("wentileixing")!=null&&map.get("wentileixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("wentileixing"),"i.APPLBASQUE"));
		}
		if (map.get("yewufanwei")!=null&&map.get("yewufanwei").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("yewufanwei"),"q.BUSINESSRANGE"));
		}
		if (map.get("renyuanshenfen")!=null&&map.get("renyuanshenfen").length()!=0) {
			sb.append(" and p.PERIDE=? \n");
			list.add(map.get("renyuanshenfen"));
		}
		if (map.get("hangyeleixing")!=null&&map.get("hangyeleixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("hangyeleixing"),"m.TRADETYPE"));
		}
		if (map.get("qiyeleixing")!=null&&map.get("qiyeleixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("qiyeleixing"),"m.ENTTYPE"));
		}
		sb.append(
				"),\n" +
						"  x1 as (select  distinct  y.code,nvl(j.name,y.code) name,y.weihuifu,y.yihuifu,y.shushi,y.bushushi,y.heji,y.zhanbi,y.huanbishu,y.huanbi,y.tongbishu,y.tongbi\n" + 
						"   from (\n" + 
						"  select x.code ,to_char(sum(case when x.replied =0 and flag=1 then 1 else 0 end )) weihuifu,\n" + 
						"                 to_char(sum(case when x.replied =1 and flag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"                to_char( sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)) shushi,\n" + 
						"                 to_char(sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)) bushushi,\n" + 
						"                 to_char(sum(flag)) heji,\n" + 
						"                 to_char(round(ratio_to_report(sum(x.flag)) over () ,4)*100,'fm9999999990.0999')||'%' as zhanbi,\n" + 
						"                 to_char(sum(hflag)) huanbishu,\n" + 
						"                 to_char(round((case when sum(hflag)=0 and  sum(flag)=0 then 0\n" + 
						"                       when sum(hflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                       else  (sum(flag)-sum(hflag))/sum(hflag) end ),4)*100,'fm999990.0999')||'%' huanbi,\n" + 
						"                 to_char(sum(tflag)) tongbishu,\n" + 
						"                 to_char(round((case when sum(tflag)=0 and  sum(flag)=0 then 0\n" + 
						"                       when sum(tflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                       else  (sum(flag)-sum(tflag))/sum(tflag) end ),4)*100,'fm9999999990.0999')||'%' tongbi\n" + 
						"                 from x  group  by x.code\n" + 
						"                 union all\n" + 
						"     select substr(x.code,0,6)||'00' as code ,to_char(sum(case when x.replied =0 and flag=1 then 1 else 0 end )) weihuifu,\n" + 
						"                 to_char(sum(case when x.replied =1 and flag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"                to_char( sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)) shushi,\n" + 
						"                 to_char(sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)) bushushi,\n" + 
						"                 to_char(sum(flag)) heji,\n" + 
						"                 to_char(round(ratio_to_report(sum(x.flag)) over () ,4)*100,'fm9999999990.0999')||'%' as zhanbi,\n" + 
						"                 to_char(sum(hflag)) huanbishu,\n" + 
						"                 to_char(round((case when sum(hflag)=0 and  sum(flag)=0 then 0\n" + 
						"                       when sum(hflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                       else  (sum(flag)-sum(hflag))/sum(hflag) end ),4)*100,'fm999990.0999')||'%' huanbi,\n" + 
						"                 to_char(sum(tflag)) tongbishu,\n" + 
						"                 to_char(round((case when sum(tflag)=0 and  sum(flag)=0 then 0\n" + 
						"                       when sum(tflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                       else  (sum(flag)-sum(tflag))/sum(tflag) end ),4)*100,'fm9999999990.0999')||'%' tongbi\n" + 
						"                 from x group by substr(x.code,0,6)||'00'\n" + 
						"                  union all\n" + 
						"     select substr(x.code,0,4)||'0000' as code ,to_char(sum(case when x.replied =0 and flag=1 then 1 else 0 end )) weihuifu,\n" + 
						"                 to_char(sum(case when x.replied =1 and flag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"                to_char( sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)) shushi,\n" + 
						"                 to_char(sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)) bushushi,\n" + 
						"                 to_char(sum(flag)) heji,\n" + 
						"                 to_char(round(ratio_to_report(sum(x.flag)) over () ,4)*100,'fm9999999990.0999')||'%' as zhanbi,\n" + 
						"                 to_char(sum(hflag)) huanbishu,\n" + 
						"                 to_char(round((case when sum(hflag)=0 and  sum(flag)=0 then 0\n" + 
						"                       when sum(hflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                       else  (sum(flag)-sum(hflag))/sum(hflag) end ),4)*100,'fm999990.0999')||'%' huanbi,\n" + 
						"                 to_char(sum(tflag)) tongbishu,\n" + 
						"                 to_char(round((case when sum(tflag)=0 and  sum(flag)=0 then 0\n" + 
						"                       when sum(tflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                       else  (sum(flag)-sum(tflag))/sum(tflag) end ),4)*100,'fm9999999990.0999')||'%' tongbi\n" + 
						"                 from x group by substr(x.code,0,4)||'0000'\n" + 
						"                  union all\n" + 
						"     select substr(x.code,0,2)||'000000' as code ,to_char(sum(case when x.replied =0 and flag=1 then 1 else 0 end )) weihuifu,\n" + 
						"                 to_char(sum(case when x.replied =1 and flag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"                to_char( sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)) shushi,\n" + 
						"                 to_char(sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)) bushushi,\n" + 
						"                 to_char(sum(flag)) heji,\n" + 
						"                 to_char(round(ratio_to_report(sum(x.flag)) over () ,4)*100,'fm9999999990.0999')||'%' as zhanbi,\n" + 
						"                 to_char(sum(hflag)) huanbishu,\n" + 
						"                 to_char(round((case when sum(hflag)=0 and  sum(flag)=0 then 0\n" + 
						"                       when sum(hflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                       else  (sum(flag)-sum(hflag))/sum(hflag) end ),4)*100,'fm999990.0999')||'%' huanbi,\n" + 
						"                 to_char(sum(tflag)) tongbishu,\n" + 
						"                 to_char(round((case when sum(tflag)=0 and  sum(flag)=0 then 0\n" + 
						"                       when sum(tflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                       else  (sum(flag)-sum(tflag))/sum(tflag) end ),4)*100,'fm9999999990.0999')||'%' tongbi\n" + 
						"                 from x group by substr(x.code,0,2)||'000000' )y,\n" + 
						"                 (SELECT  CODE,  substr('　　　　　',0,level-1)||NAME AS NAME\n" + 
						"    FROM (SELECT * FROM DC_CODE.DC_12315_CODEDATA WHERE CODETABLE = 'ZH01')\n" + 
						"   START WITH PARENTCODE = '0'\n" + 
						"  CONNECT BY PRIOR CODE = PARENTCODE)j\n" + 
						"  where y.code=j.code(+)\n" + 
						"  order by code\n" + 
						"\n" + 
						"                 )\n" + 
						"\n" + 
						"                 select * from x1\n" + 
						"\n" + 
						"\n" + 
						"                   union all\n" + 
						"                 select '9999999901' as code,'占总量' as name,\n" + 
						"                  to_char(round((case when\n" + 
						"                 sum(case when x.replied =0 and flag=1 then 1 else 0 end )=0 then 0 else\n" + 
						"                 sum(case when x.replied =0 and flag=1 then 1 else 0 end )/sum(flag) end\n" + 
						"                 ),4)*100,'fm9999999990.0999')||'%' weihuifu,\n" + 
						"                 to_char(round((case when\n" + 
						"                 sum(case when x.replied =1 and flag=1 then 1 else 0 end )=0 then 0 else\n" + 
						"                 sum(case when x.replied =1 and flag=1 then 1 else 0 end )/sum(flag) end\n" + 
						"                 ),4)*100,'fm9999999990.0999')||'%' yihuifu,\n" + 
						"                  to_char(round((case when\n" + 
						"                 sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)=0 then 0 else\n" + 
						"                 sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)/sum(flag) end\n" + 
						"                 ) ,4)*100,'fm9999999990.0999')||'%'shushi,\n" + 
						"                  to_char(round((case when\n" + 
						"                 sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)=0 then 0 else\n" + 
						"                 sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)/sum(flag) end\n" + 
						"                 ) ,4)*100,'fm9999999990.0999')||'%'bushushi,\n" + 
						"                  to_char(round((case when sum(flag)=0 then 0 else\n" + 
						"                 sum(flag)/sum(flag) end),4)*100,'fm9999999990.0999')||'%' heji ,\n" + 
						"                 ' ' as zhanbi, ' ' as huanbishu,' ' as huanbi,' ' as tongbishu, ' ' as tongbi\n" + 
						"                 from x\n" + 
						"              union all\n" + 
						"              select '9999999902'  as code ,'上时段数据' as name,\n" + 
						"              to_char(sum(case when x.replied =0 and hflag=1 then 1 else 0 end )) weihuifu,\n" + 
						"                 to_char(sum(case when x.replied =1 and hflag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"                to_char( sum(case when x.visitconfirm =1 and hflag=1 then 1 else 0 end)) shushi,\n" + 
						"                 to_char(sum(case when x.visitconfirm =2 and hflag=1 then 1 else 0 end)) bushushi,\n" + 
						"                 to_char(sum(hflag)) heji,\n" + 
						"                 ' ' as zhanbi, ' ' as huanbishu, ' ' as huanbi, ' ' as tongbishu, ' ' as tongbi\n" + 
						"                 from x\n" + 
						"              union all\n" + 
						"               select '9999999903'  as code ,'环比增减（%）' as name,\n" + 
						"                to_char(round(\n" + 
						"                (case when sum(case when x.replied =0 and hflag=1 then 1 else 0 end )=0\n" + 
						"                             and  sum(case when x.replied =0 and flag=1 then 1 else 0 end )=0\n" + 
						"                             then 0\n" + 
						"                             when sum(case when x.replied =0 and hflag=1 then 1 else 0 end )=0\n" + 
						"                             and  sum(case when x.replied =0 and flag=1 then 1 else 0 end )<>0\n" + 
						"                             then 1 else\n" + 
						"                             (sum(case when x.replied =0 and flag=1 then 1 else 0 end )-\n" + 
						"                             sum(case when x.replied =0 and hflag=1 then 1 else 0 end ))/\n" + 
						"                             sum(case when x.replied =0 and hflag=1 then 1 else 0 end ) end)\n" + 
						"                             ,4)*100,'fm9999999990.0999')||'%'  weihuifu,\n" + 
						"                to_char(round(\n" + 
						"                     (case when sum(case when x.replied =1 and hflag=1 then 1 else 0 end )=0\n" + 
						"                           and  sum(case when x.replied =1 and flag=1 then 1 else 0 end )=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.replied =1 and hflag=1 then 1 else 0 end )=0\n" + 
						"                           and  sum(case when x.replied =1 and flag=1 then 1 else 0 end )<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.replied =1 and flag=1 then 1 else 0 end )-\n" + 
						"                           sum(case when x.replied =1 and hflag=1 then 1 else 0 end ))/\n" + 
						"                           sum(case when x.replied =1 and hflag=1 then 1 else 0 end ) end)\n" + 
						"                            ,4)*100,'fm9999999990.0999')||'%' as yihuifu,\n" + 
						"                to_char(round(\n" + 
						"                     (case when sum(case when x.visitconfirm =1 and hflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.visitconfirm =1 and hflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)-\n" + 
						"                           sum(case when x.visitconfirm =1 and hflag=1 then 1 else 0 end))/\n" + 
						"                           sum(case when x.visitconfirm =1 and hflag=1 then 1 else 0 end) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  shushi,\n" + 
						"               to_char(round(\n" + 
						"                     (case when sum(case when x.visitconfirm =2 and hflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.visitconfirm =2 and hflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)-\n" + 
						"                           sum(case when x.visitconfirm =2 and hflag=1 then 1 else 0 end))/\n" + 
						"                           sum(case when x.visitconfirm =2 and hflag=1 then 1 else 0 end) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  bushushi,\n" + 
						"                           to_char(round(\n" + 
						"                     (case when sum(hflag)=0  and  sum(flag)=0   then 0\n" + 
						"                           when sum(hflag)=0  and  sum(flag)<>0  then 1\n" + 
						"                             else(sum(flag)- sum(hflag))/sum(hflag) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  heji,\n" + 
						"                 ' ' as zhanbi, ' ' as huanbishu, ' ' as huanbi, ' ' as tongbishu, ' ' as tongbi\n" + 
						"                 from x\n" + 
						"            union all\n" + 
						"              select '9999999904'  as code ,'去年同期数据' as name,\n" + 
						"              to_char(sum(case when x.replied =0 and tflag=1 then 1 else 0 end )) weihuifu,\n" + 
						"                 to_char(sum(case when x.replied =1 and tflag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"                to_char( sum(case when x.visitconfirm =1 and tflag=1 then 1 else 0 end)) shushi,\n" + 
						"                 to_char(sum(case when x.visitconfirm =2 and tflag=1 then 1 else 0 end)) bushushi,\n" + 
						"                 to_char(sum(tflag)) heji,\n" + 
						"                 ' ' as zhanbi, ' ' as huanbishu, ' ' as huanbi, ' ' as tongbishu, ' ' as tongbi\n" + 
						"                 from x\n" + 
						"                union all\n" + 
						"               select '9999999905'  as code ,'同比增减（%）' as name,\n" + 
						"                to_char(round(\n" + 
						"                (case when sum(case when x.replied =0 and tflag=1 then 1 else 0 end )=0\n" + 
						"                             and  sum(case when x.replied =0 and flag=1 then 1 else 0 end )=0\n" + 
						"                             then 0\n" + 
						"                             when sum(case when x.replied =0 and tflag=1 then 1 else 0 end )=0\n" + 
						"                             and  sum(case when x.replied =0 and flag=1 then 1 else 0 end )<>0\n" + 
						"                             then 1 else\n" + 
						"                             (sum(case when x.replied =0 and flag=1 then 1 else 0 end )-\n" + 
						"                             sum(case when x.replied =0 and tflag=1 then 1 else 0 end ))/\n" + 
						"                             sum(case when x.replied =0 and tflag=1 then 1 else 0 end ) end)\n" + 
						"                             ,4)*100,'fm9999999990.0999')||'%'  weihuifu,\n" + 
						"                to_char(round(\n" + 
						"                     (case when sum(case when x.replied =1 and tflag=1 then 1 else 0 end )=0\n" + 
						"                           and  sum(case when x.replied =1 and flag=1 then 1 else 0 end )=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.replied =1 and tflag=1 then 1 else 0 end )=0\n" + 
						"                           and  sum(case when x.replied =1 and flag=1 then 1 else 0 end )<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.replied =1 and flag=1 then 1 else 0 end )-\n" + 
						"                           sum(case when x.replied =1 and tflag=1 then 1 else 0 end ))/\n" + 
						"                           sum(case when x.replied =1 and tflag=1 then 1 else 0 end ) end)\n" + 
						"                            ,4)*100,'fm9999999990.0999')||'%' as yihuifu,\n" + 
						"                to_char(round(\n" + 
						"                     (case when sum(case when x.visitconfirm =1 and tflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.visitconfirm =1 and tflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)-\n" + 
						"                           sum(case when x.visitconfirm =1 and tflag=1 then 1 else 0 end))/\n" + 
						"                           sum(case when x.visitconfirm =1 and tflag=1 then 1 else 0 end) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  shushi,\n" + 
						"               to_char(round(\n" + 
						"                     (case when sum(case when x.visitconfirm =2 and tflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.visitconfirm =2 and tflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)-\n" + 
						"                           sum(case when x.visitconfirm =2 and tflag=1 then 1 else 0 end))/\n" + 
						"                           sum(case when x.visitconfirm =2 and tflag=1 then 1 else 0 end) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  bushushi,\n" + 
						"                           to_char(round(\n" + 
						"                     (case when sum(tflag)=0  and  sum(flag)=0   then 0\n" + 
						"                           when sum(tflag)=0  and  sum(flag)<>0  then 1\n" + 
						"                             else(sum(flag)- sum(tflag))/sum(tflag) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  heji,\n" + 
						"                 ' ' as zhanbi, ' ' as huanbishu, ' ' as huanbi, ' ' as tongbishu, ' ' as tongbi\n" + 
						"                 from x");
		try {
			res = dao.queryForList(sb.toString(), list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("结案信息咨询建议业务范围统计报表", "WDY", i == 1 ? "查看报表" : "下载报表",
				sb.toString(), map.get("begintime") + "," + map.get("endtime"),req);
		return res;
	}
	public List<Map> zj_JiBenWenTi(Map<String, String> map, int i,
			HttpServletRequest req) {
		List list=new ArrayList();
		StringBuffer sb=new StringBuffer();
		List<Map> res=new ArrayList<Map>();
		sb.append(
				"with x as (\n" +
						"  select  /*+ parallel(8)*/  i.APPLBASQUE as code,decode(f.replied,1,1,0) replied ,v.visitconfirm visitconfirm,\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) flag,\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) tflag,  --同比\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) hflag  --环比\n" + 
						"   from dc_cpr_infoware i,DC_CPR_RETURN_VISIT v,\n" + 
						"  dc_cpr_feedback f,DC_CPR_INQUIRY q,DC_CPR_INFO_PROVIDER p,dc_cpr_involved_main m\n" + 
						"  where i.feedbackid=f.feedbackid(+)\n" + 
						"  and i.infowareid=v.infowareid(+)\n" + 
						"  and i.INQUIRYID=q.inquiryid(+)\n" + 
						"  and i.INFPROID=p.INFPROID(+)\n" + 
						"  and i.Invmaiid=m.INVMAIID(+) \n");
		list.add(map.get("begintime"));
		list.add(map.get("endtime")+" 23:59:59");
		list.add(QueryJieAnUtils.getTongBiDate(map.get("begintime")));
		list.add(QueryJieAnUtils.getTongBiDate(map.get("endtime"))+" 23:59:59");
		if (map.get("hbegintime")!=null&&map.get("hbegintime").length()!=0) {
			list.add(map.get("hbegintime"));
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(map.get("begintime")));
		}
		if (map.get("hendtime")!=null&&map.get("hendtime").length()!=0) {
			list.add(map.get("hendtime")+" 23:59:59");
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(map.get("endtime"))+" 23:59:59");
		}
		if (map.get("infotype")!=null&&map.get("infotype").length()!=0) {
			sb.append("and i.inftype =? \n");
			list.add(map.get("infotype"));
		}else{
			sb.append("and i.inftype in ('3','4') \n");
		}
		
		if (map.get("dengjibumen")!=null&&map.get("dengjibumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("dengjibumen"),"i.REGDEPCODE"));
		}
		if (map.get("chengbanbumen")!=null&&map.get("chengbanbumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("chengbanbumen"),"i.HANDEPCODE"));
		}
		if (map.get("jieshoufangshi")!=null&&map.get("jieshoufangshi").length()!=0) {
			sb.append(" and i.INCFORM =? \n");
			list.add(map.get("jieshoufangshi"));
		}
		if (map.get("suoshubumen")!=null&&map.get("suoshubumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("suoshubumen"),"q.INQDEPCODE"));
		}
		if (map.get("wentileixing")!=null&&map.get("wentileixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("wentileixing"),"i.APPLBASQUE"));
		}
		if (map.get("yewufanwei")!=null&&map.get("yewufanwei").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("yewufanwei"),"q.BUSINESSRANGE"));
		}
		if (map.get("renyuanshenfen")!=null&&map.get("renyuanshenfen").length()!=0) {
			sb.append(" and p.PERIDE=? \n");
			list.add(map.get("renyuanshenfen"));
		}
		if (map.get("hangyeleixing")!=null&&map.get("hangyeleixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("hangyeleixing"),"m.TRADETYPE"));
		}
		if (map.get("qiyeleixing")!=null&&map.get("qiyeleixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("qiyeleixing"),"m.ENTTYPE"));
		}
		sb.append(
				"),\n" +
						"  x1 as (\n" + 
						"               select y.code,nvl(j.name,'　'||y.code)as name,y.weihuifu,y.yihuifu,y.shushi,y.bushushi,y.heji,y.zhanbi,y.huanbishu,y.huanbi,y.tongbishu,y.tongbi from (\n" + 
						"               select  nvl(decode(x.code,'9900','9890',x.code),'9990') as code ,to_char(sum(case when x.replied =0 and flag=1 then 1 else 0 end )) weihuifu,\n" + 
						"                 to_char(sum(case when x.replied =1 and flag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"                to_char( sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)) shushi,\n" + 
						"                 to_char(sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)) bushushi,\n" + 
						"                 to_char(sum(flag)) heji,\n" + 
						"                 to_char(round(ratio_to_report(sum(x.flag)) over () ,4)*100,'fm9999999990.0999')||'%' as zhanbi,\n" + 
						"                 to_char(sum(hflag)) huanbishu,\n" + 
						"                 to_char(round((case when sum(hflag)=0 and  sum(flag)=0 then 0\n" + 
						"                       when sum(hflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                       else  (sum(flag)-sum(hflag))/sum(hflag) end ),4)*100,'fm999990.0999')||'%' huanbi,\n" + 
						"                 to_char(sum(tflag)) tongbishu,\n" + 
						"                 to_char(round((case when sum(tflag)=0 and  sum(flag)=0 then 0\n" + 
						"                       when sum(tflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                       else  (sum(flag)-sum(tflag))/sum(tflag) end ),4)*100,'fm9999999990.0999')||'%' tongbi\n" + 
						"                 from x   group by x.code)y,\n" + 
						"                 (SELECT PARENTCODE, CODE, '　'||NAME AS NAME\n" + 
						"                 FROM (SELECT * FROM DC_CODE.DC_12315_CODEDATA WHERE CODETABLE = 'CH27' and code<>'9900')\n" + 
						"                 START WITH PARENTCODE in('0','3')\n" + 
						"                 CONNECT BY PRIOR  code= PARENTCODE\n" + 
						"                  union  all\n" + 
						"                  select '0' as parentcode,'9990' as code,'　空'as name from dual\n" + 
						"                  union  all\n" + 
						"                  select '0' as parentcode,'9890' as code,'　其他'as name from dual)j\n" + 
						"                 where y.code=j.code(+)\n" + 
						"                 union all\n" + 
						"                  select y.code,nvl(j.name, y.code),y.weihuifu,y.yihuifu,y.shushi,y.bushushi,y.heji,y.zhanbi,y.huanbishu,y.huanbi,y.tongbishu,y.tongbi from (\n" + 
						"                select  substr(nvl(decode(x.code,'9900','9800',x.code),'9900'),0,2)||'00' as code ,to_char(sum(case when x.replied =0 and flag=1 then 1 else 0 end )) weihuifu,\n" + 
						"                 to_char(sum(case when x.replied =1 and flag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"                to_char( sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)) shushi,\n" + 
						"                 to_char(sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)) bushushi,\n" + 
						"                 to_char(sum(flag)) heji,\n" + 
						"                 to_char(round(ratio_to_report(sum(x.flag)) over () ,4)*100,'fm9999999990.0999') as zhanbi,\n" + 
						"                 to_char(sum(hflag)) huanbishu,\n" + 
						"                 to_char(round((case when sum(hflag)=0 and  sum(flag)=0 then 0\n" + 
						"                       when sum(hflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                       else  (sum(flag)-sum(hflag))/sum(hflag) end ),4)*100,'fm999990.0999')||'%' huanbi,\n" + 
						"                 to_char(sum(tflag)) tongbishu,\n" + 
						"                 to_char(round((case when sum(tflag)=0 and  sum(flag)=0 then 0\n" + 
						"                       when sum(tflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                       else  (sum(flag)-sum(tflag))/sum(tflag) end ),4)*100,'fm9999999990.0999')||'%' tongbi\n" + 
						"                 from x   group by substr(nvl(decode(x.code,'9900','9800',x.code),'9900'),0,2)||'00') y,\n" + 
						"                 (SELECT PARENTCODE, CODE, NAME AS NAME\n" + 
						"                 FROM (SELECT * FROM DC_CODE.DC_12315_CODEDATA WHERE CODETABLE = 'CH27' and code<>'9900')\n" + 
						"                 START WITH PARENTCODE in('0','3')\n" + 
						"                 CONNECT BY PRIOR  PARENTCODE= code\n" + 
						"\n" + 
						"                 union all\n" + 
						"                 select '0' as parentcode,'9900' as code,'空'as name from dual\n" + 
						"                  union  all\n" + 
						"                  select '0' as parentcode,'9800' as code,'其他'as name from dual\n" + 
						"                 )j\n" + 
						"                 where y.code=j.code(+)\n" + 
						"                 order by code,name desc\n" + 
						"\n" + 
						"  )\n" + 
						"                select * from x1\n" + 
						"                 union all\n" + 
						"                 select '999901'as code,'占总量' as name,\n" + 
						"                  to_char(round((case when\n" + 
						"                 sum(case when x.replied =0 and flag=1 then 1 else 0 end )=0 then 0 else\n" + 
						"                 sum(case when x.replied =0 and flag=1 then 1 else 0 end )/sum(flag) end\n" + 
						"                 ),4)*100,'fm9999999990.0999')||'%' weihuifu,\n" + 
						"                 to_char(round((case when\n" + 
						"                 sum(case when x.replied =1 and flag=1 then 1 else 0 end )=0 then 0 else\n" + 
						"                 sum(case when x.replied =1 and flag=1 then 1 else 0 end )/sum(flag) end\n" + 
						"                 ),4)*100,'fm9999999990.0999')||'%' yihuifu,\n" + 
						"                  to_char(round((case when\n" + 
						"                 sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)=0 then 0 else\n" + 
						"                 sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)/sum(flag) end\n" + 
						"                 ) ,4)*100,'fm9999999990.0999')||'%'shushi,\n" + 
						"                  to_char(round((case when\n" + 
						"                 sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)=0 then 0 else\n" + 
						"                 sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)/sum(flag) end\n" + 
						"                 ) ,4)*100,'fm9999999990.0999')||'%'bushushi,\n" + 
						"                  to_char(round((case when sum(flag)=0 then 0 else\n" + 
						"                 sum(flag)/sum(flag) end),4)*100,'fm9999999990.0999')||'%' heji ,\n" + 
						"                 ' ' as zhanbi, ' ' as huanbishu,' ' as huanbi,' ' as tongbishu, ' ' as tongbi\n" + 
						"                 from x\n" + 
						"              union all\n" + 
						"              select  '999902'as code,'上时段数据'  as name ,\n" + 
						"              to_char(sum(case when x.replied =0 and hflag=1 then 1 else 0 end )) weihuifu,\n" + 
						"                 to_char(sum(case when x.replied =1 and hflag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"                to_char( sum(case when x.visitconfirm =1 and hflag=1 then 1 else 0 end)) shushi,\n" + 
						"                 to_char(sum(case when x.visitconfirm =2 and hflag=1 then 1 else 0 end)) bushushi,\n" + 
						"                 to_char(sum(hflag)) heji,\n" + 
						"                 ' ' as zhanbi, ' ' as huanbishu, ' ' as huanbi, ' ' as tongbishu, ' ' as tongbi\n" + 
						"                 from x\n" + 
						"              union all\n" + 
						"               select  '999903'as code,'环比增减（%）'  as name ,\n" + 
						"                to_char(round(\n" + 
						"                (case when sum(case when x.replied =0 and hflag=1 then 1 else 0 end )=0\n" + 
						"                             and  sum(case when x.replied =0 and flag=1 then 1 else 0 end )=0\n" + 
						"                             then 0\n" + 
						"                             when sum(case when x.replied =0 and hflag=1 then 1 else 0 end )=0\n" + 
						"                             and  sum(case when x.replied =0 and flag=1 then 1 else 0 end )<>0\n" + 
						"                             then 1 else\n" + 
						"                             (sum(case when x.replied =0 and flag=1 then 1 else 0 end )-\n" + 
						"                             sum(case when x.replied =0 and hflag=1 then 1 else 0 end ))/\n" + 
						"                             sum(case when x.replied =0 and hflag=1 then 1 else 0 end ) end)\n" + 
						"                             ,4)*100,'fm9999999990.0999')||'%'  weihuifu,\n" + 
						"                to_char(round(\n" + 
						"                     (case when sum(case when x.replied =1 and hflag=1 then 1 else 0 end )=0\n" + 
						"                           and  sum(case when x.replied =1 and flag=1 then 1 else 0 end )=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.replied =1 and hflag=1 then 1 else 0 end )=0\n" + 
						"                           and  sum(case when x.replied =1 and flag=1 then 1 else 0 end )<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.replied =1 and flag=1 then 1 else 0 end )-\n" + 
						"                           sum(case when x.replied =1 and hflag=1 then 1 else 0 end ))/\n" + 
						"                           sum(case when x.replied =1 and hflag=1 then 1 else 0 end ) end)\n" + 
						"                            ,4)*100,'fm9999999990.0999')||'%' as yihuifu,\n" + 
						"                to_char(round(\n" + 
						"                     (case when sum(case when x.visitconfirm =1 and hflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.visitconfirm =1 and hflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)-\n" + 
						"                           sum(case when x.visitconfirm =1 and hflag=1 then 1 else 0 end))/\n" + 
						"                           sum(case when x.visitconfirm =1 and hflag=1 then 1 else 0 end) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  shushi,\n" + 
						"               to_char(round(\n" + 
						"                     (case when sum(case when x.visitconfirm =2 and hflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.visitconfirm =2 and hflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)-\n" + 
						"                           sum(case when x.visitconfirm =2 and hflag=1 then 1 else 0 end))/\n" + 
						"                           sum(case when x.visitconfirm =2 and hflag=1 then 1 else 0 end) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  bushushi,\n" + 
						"                           to_char(round(\n" + 
						"                     (case when sum(hflag)=0  and  sum(flag)=0   then 0\n" + 
						"                           when sum(hflag)=0  and  sum(flag)<>0  then 1\n" + 
						"                             else(sum(flag)- sum(hflag))/sum(hflag) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  heji,\n" + 
						"                 ' ' as zhanbi, ' ' as huanbishu, ' ' as huanbi, ' ' as tongbishu, ' ' as tongbi\n" + 
						"                 from x\n" + 
						"            union all\n" + 
						"              select  '999904'as code, '去年同期数据'  as name ,\n" + 
						"              to_char(sum(case when x.replied =0 and tflag=1 then 1 else 0 end )) weihuifu,\n" + 
						"                 to_char(sum(case when x.replied =1 and tflag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"                to_char( sum(case when x.visitconfirm =1 and tflag=1 then 1 else 0 end)) shushi,\n" + 
						"                 to_char(sum(case when x.visitconfirm =2 and tflag=1 then 1 else 0 end)) bushushi,\n" + 
						"                 to_char(sum(tflag)) heji,\n" + 
						"                 ' ' as zhanbi, ' ' as huanbishu, ' ' as huanbi, ' ' as tongbishu, ' ' as tongbi\n" + 
						"                 from x\n" + 
						"                union all\n" + 
						"               select  '999905'as code,'同比增减（%）'  as name ,\n" + 
						"                to_char(round(\n" + 
						"                (case when sum(case when x.replied =0 and tflag=1 then 1 else 0 end )=0\n" + 
						"                             and  sum(case when x.replied =0 and flag=1 then 1 else 0 end )=0\n" + 
						"                             then 0\n" + 
						"                             when sum(case when x.replied =0 and tflag=1 then 1 else 0 end )=0\n" + 
						"                             and  sum(case when x.replied =0 and flag=1 then 1 else 0 end )<>0\n" + 
						"                             then 1 else\n" + 
						"                             (sum(case when x.replied =0 and flag=1 then 1 else 0 end )-\n" + 
						"                             sum(case when x.replied =0 and tflag=1 then 1 else 0 end ))/\n" + 
						"                             sum(case when x.replied =0 and tflag=1 then 1 else 0 end ) end)\n" + 
						"                             ,4)*100,'fm9999999990.0999')||'%'  weihuifu,\n" + 
						"                to_char(round(\n" + 
						"                     (case when sum(case when x.replied =1 and tflag=1 then 1 else 0 end )=0\n" + 
						"                           and  sum(case when x.replied =1 and flag=1 then 1 else 0 end )=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.replied =1 and tflag=1 then 1 else 0 end )=0\n" + 
						"                           and  sum(case when x.replied =1 and flag=1 then 1 else 0 end )<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.replied =1 and flag=1 then 1 else 0 end )-\n" + 
						"                           sum(case when x.replied =1 and tflag=1 then 1 else 0 end ))/\n" + 
						"                           sum(case when x.replied =1 and tflag=1 then 1 else 0 end ) end)\n" + 
						"                            ,4)*100,'fm9999999990.0999')||'%' as yihuifu,\n" + 
						"                to_char(round(\n" + 
						"                     (case when sum(case when x.visitconfirm =1 and tflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.visitconfirm =1 and tflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)-\n" + 
						"                           sum(case when x.visitconfirm =1 and tflag=1 then 1 else 0 end))/\n" + 
						"                           sum(case when x.visitconfirm =1 and tflag=1 then 1 else 0 end) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  shushi,\n" + 
						"               to_char(round(\n" + 
						"                     (case when sum(case when x.visitconfirm =2 and tflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.visitconfirm =2 and tflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)-\n" + 
						"                           sum(case when x.visitconfirm =2 and tflag=1 then 1 else 0 end))/\n" + 
						"                           sum(case when x.visitconfirm =2 and tflag=1 then 1 else 0 end) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  bushushi,\n" + 
						"                           to_char(round(\n" + 
						"                     (case when sum(tflag)=0  and  sum(flag)=0   then 0\n" + 
						"                           when sum(tflag)=0  and  sum(flag)<>0  then 1\n" + 
						"                             else(sum(flag)- sum(tflag))/sum(tflag) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  heji,\n" + 
						"                 ' ' as zhanbi, ' ' as huanbishu, ' ' as huanbi, ' ' as tongbishu, ' ' as tongbi\n" + 
						"                 from x order by code,name asc");
		try {
			res=dao.queryForList(sb.toString(), list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("结案信息咨询建议基本问题统计报表", "WDY", i == 1 ? "查看报表" : "下载报表",
				sb.toString(), map.get("begintime") + "," + map.get("endtime"),req);
		return res;
	}
	public List<List<Map>> zj_SheJiKeTi(Map<String, String> map, int i,
			HttpServletRequest req) {
		List list=new ArrayList();
		List listTongBiHuanBi=new ArrayList();
		List<List<Map>> res=new ArrayList<List<Map>>();
		String[] sqls={this.getGongshangSqlForZiXun(map),this.getXiaoWeiHuiSqlForZiXun(map),
				this.getZhiShiChanQuanSqlForZiXun(map),
				this.getZhiLiangJianDuSqlForZiXun(map)};
		String jiaGeJianCha=this.getJiaGeJianChaSqlForZiXun(map);
		String tongBiHuanBi=this.getTongBiHuanBiSqlForZiXun(map);
		list.add(map.get("begintime"));
		list.add(map.get("endtime")+" 23:59:59");
		list.add(QueryJieAnUtils.getTongBiDate(map.get("begintime")));
		list.add(QueryJieAnUtils.getTongBiDate(map.get("endtime"))+" 23:59:59");
		if (map.get("hbegintime")!=null&&map.get("hbegintime").length()!=0) {
			list.add(map.get("hbegintime"));
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(map.get("begintime")));
		}
		if (map.get("hendtime")!=null&&map.get("hendtime").length()!=0) {
			list.add(map.get("hendtime")+" 23:59:59");
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(map.get("endtime"))+" 23:59:59");
		}
		if (map.get("infotype")!=null&&map.get("infotype").length()!=0) {
			list.add(map.get("infotype"));
		}		
		if (map.get("jieshoufangshi")!=null&&map.get("jieshoufangshi").length()!=0) {
			list.add(map.get("jieshoufangshi"));
		}
		if (map.get("renyuanshenfen")!=null&&map.get("renyuanshenfen").length()!=0) {
			list.add(map.get("renyuanshenfen"));
		}
		
		listTongBiHuanBi.add(map.get("begintime"));
		listTongBiHuanBi.add(map.get("endtime")+" 23:59:59");
		listTongBiHuanBi.add(QueryJieAnUtils.getTongBiDate(map.get("begintime")));
		listTongBiHuanBi.add(QueryJieAnUtils.getTongBiDate(map.get("endtime"))+" 23:59:59");
		if (map.get("hbegintime")!=null&&map.get("hbegintime").length()!=0) {
			listTongBiHuanBi.add(map.get("hbegintime"));
		}else{
			listTongBiHuanBi.add(QueryJieAnUtils.getHuanBiDate(map.get("begintime")));
		}
		if (map.get("hendtime")!=null&&map.get("hendtime").length()!=0) {
			listTongBiHuanBi.add(map.get("hendtime")+" 23:59:59");
		}else{
			listTongBiHuanBi.add(QueryJieAnUtils.getHuanBiDate(map.get("endtime"))+" 23:59:59");
		}
		if (map.get("infotype")!=null&&map.get("infotype").length()!=0) {
			listTongBiHuanBi.add(map.get("infotype"));
		}		
		if (map.get("jieshoufangshi")!=null&&map.get("jieshoufangshi").length()!=0) {
			listTongBiHuanBi.add(map.get("jieshoufangshi"));
		}
		if (map.get("renyuanshenfen")!=null&&map.get("renyuanshenfen").length()!=0) {
			listTongBiHuanBi.add(map.get("renyuanshenfen"));
		}
		
		listTongBiHuanBi.add(map.get("begintime"));
		listTongBiHuanBi.add(map.get("endtime")+" 23:59:59");
		listTongBiHuanBi.add(QueryJieAnUtils.getTongBiDate(map.get("begintime")));
		listTongBiHuanBi.add(QueryJieAnUtils.getTongBiDate(map.get("endtime"))+" 23:59:59");
		if (map.get("hbegintime")!=null&&map.get("hbegintime").length()!=0) {
			listTongBiHuanBi.add(map.get("hbegintime"));
		}else{
			listTongBiHuanBi.add(QueryJieAnUtils.getHuanBiDate(map.get("begintime")));
		}
		if (map.get("hendtime")!=null&&map.get("hendtime").length()!=0) {
			listTongBiHuanBi.add(map.get("hendtime")+" 23:59:59");
		}else{
			listTongBiHuanBi.add(QueryJieAnUtils.getHuanBiDate(map.get("endtime"))+" 23:59:59");
		}
		if (map.get("infotype")!=null&&map.get("infotype").length()!=0) {
			listTongBiHuanBi.add(map.get("infotype"));
		}		
		if (map.get("jieshoufangshi")!=null&&map.get("jieshoufangshi").length()!=0) {
			listTongBiHuanBi.add(map.get("jieshoufangshi"));
		}
		if (map.get("renyuanshenfen")!=null&&map.get("renyuanshenfen").length()!=0) {
			listTongBiHuanBi.add(map.get("renyuanshenfen"));
		}
		try {
			for (int j = 0; j < sqls.length; j++) {
				res.add(dao.queryForList(sqls[j], list));
			}
			res.add(dao.queryForList(jiaGeJianCha, listTongBiHuanBi));
			res.add(dao.queryForList(tongBiHuanBi, list));
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("结案信息咨询建议涉及客体统计报表", "WDY", i == 1 ? "查看报表" : "下载报表",
				sqls[0], map.get("begintime") + "," + map.get("endtime"),req);
		return res;
	}
	
	private String getGongshangSqlForZiXun(Map<String,String> map){
		StringBuffer sb=new StringBuffer();
		sb.append(
				"with x as (\n" +
						" select  /*+ parallel(8)*/  o.invobjtype as code,decode(f.replied,1,1,0) replied ,v.visitconfirm visitconfirm,\n" + 
						" (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						" and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) flag,\n" + 
						" (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						" and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) tflag,  --同比\n" + 
						" (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						" and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) hflag  --环比\n" + 
						"  from dc_cpr_infoware i,DC_CPR_RETURN_VISIT v,dc_cpr_involved_object o,\n" + 
						" dc_cpr_feedback f,DC_CPR_INQUIRY q,DC_CPR_INFO_PROVIDER p,dc_cpr_involved_main m\n" + 
						" where i.feedbackid=f.feedbackid(+)\n" + 
						" and i.infowareid=v.infowareid(+)\n" + 
						" and i.INQUIRYID=q.inquiryid(+)\n" + 
						" and i.INFPROID=p.INFPROID(+)\n" + 
						" and i.Invmaiid=m.INVMAIID(+)\n" + 
						" and i.invobjid=o.invobjid(+)\n" + 
						" and o.businesstype='CH20'\n" + 
						" and o.invobjtype is not null \n");
		if (map.get("infotype")!=null&&map.get("infotype").length()!=0) {
			sb.append("and i.inftype =? \n");
			//list.add(map.get("infotype"));
		}else{
			sb.append("and i.inftype in ('3','4') \n");
		}
		
		if (map.get("dengjibumen")!=null&&map.get("dengjibumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("dengjibumen"),"i.REGDEPCODE"));
		}
		if (map.get("chengbanbumen")!=null&&map.get("chengbanbumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("chengbanbumen"),"i.HANDEPCODE"));
		}
		if (map.get("jieshoufangshi")!=null&&map.get("jieshoufangshi").length()!=0) {
			sb.append(" and i.INCFORM =? \n");
			//list.add(map.get("jieshoufangshi"));
		}
		if (map.get("suoshubumen")!=null&&map.get("suoshubumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("suoshubumen"),"q.INQDEPCODE"));
		}
		if (map.get("wentileixing")!=null&&map.get("wentileixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("wentileixing"),"i.APPLBASQUE"));
		}
		if (map.get("yewufanwei")!=null&&map.get("yewufanwei").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("yewufanwei"),"q.BUSINESSRANGE"));
		}
		if (map.get("renyuanshenfen")!=null&&map.get("renyuanshenfen").length()!=0) {
			sb.append(" and p.PERIDE=? \n");
			//list.add(map.get("renyuanshenfen"));
		}
		if (map.get("hangyeleixing")!=null&&map.get("hangyeleixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("hangyeleixing"),"m.TRADETYPE"));
		}
		if (map.get("qiyeleixing")!=null&&map.get("qiyeleixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("qiyeleixing"),"m.ENTTYPE"));
		}
		sb.append(
				"),\n" +
						"  x1 as (select  distinct  y.code,nvl(j.name,y.code) name ,y.weihuifu,y.yihuifu,y.shushi,y.bushushi,y.heji,y.zhanbi,y.huanbishu,y.huanbi,y.tongbishu,y.tongbi\n" + 
						"   from (\n" + 
						"  select '00工商'as code ,to_char(sum(case when x.replied =0 and flag=1 then 1 else 0 end )) weihuifu,\n" + 
						"                 to_char(sum(case when x.replied =1 and flag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"                to_char( sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)) shushi,\n" + 
						"                 to_char(sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)) bushushi,\n" + 
						"                 to_char(sum(flag)) heji,\n" + 
						"                 to_char(round(ratio_to_report(sum(x.flag)) over () ,4)*100,'fm9999999990.0999')||'%' as zhanbi,\n" + 
						"                 to_char(sum(hflag)) huanbishu,\n" + 
						"                 to_char(round((case when sum(hflag)=0 and  sum(flag)=0 then 0\n" + 
						"                       when sum(hflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                       else  (sum(flag)-sum(hflag))/sum(hflag) end ),4)*100,'fm999990.0999')||'%' huanbi,\n" + 
						"                 to_char(sum(tflag)) tongbishu,\n" + 
						"                 to_char(round((case when sum(tflag)=0 and  sum(flag)=0 then 0\n" + 
						"                       when sum(tflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                       else  (sum(flag)-sum(tflag))/sum(tflag) end ),4)*100,'fm9999999990.0999')||'%' tongbi\n" + 
						"                 from x\n" + 
						"                  union all\n" + 
						"          select  substr(x.code,0,1)||'0000000' as code ,to_char(sum(case when x.replied =0 and flag=1 then 1 else 0 end )) weihuifu,\n" + 
						"                 to_char(sum(case when x.replied =1 and flag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"                to_char( sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)) shushi,\n" + 
						"                 to_char(sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)) bushushi,\n" + 
						"                 to_char(sum(flag)) heji,\n" + 
						"                 to_char(round(ratio_to_report(sum(x.flag)) over () ,4)*100,'fm9999999990.0999')||'%' as zhanbi,\n" + 
						"                 to_char(sum(hflag)) huanbishu,\n" + 
						"                 to_char(round((case when sum(hflag)=0 and  sum(flag)=0 then 0\n" + 
						"                       when sum(hflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                       else  (sum(flag)-sum(hflag))/sum(hflag) end ),4)*100,'fm999990.0999')||'%' huanbi,\n" + 
						"                 to_char(sum(tflag)) tongbishu,\n" + 
						"                 to_char(round((case when sum(tflag)=0 and  sum(flag)=0 then 0\n" + 
						"                       when sum(tflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                       else  (sum(flag)-sum(tflag))/sum(tflag) end ),4)*100,'fm9999999990.0999')||'%' tongbi\n" + 
						"                 from x group by  substr(x.code,0,1)||'0000000'\n" + 
						"                 union all\n" + 
						"     select x.code as code ,to_char(sum(case when x.replied =0 and flag=1 then 1 else 0 end )) weihuifu,\n" + 
						"                 to_char(sum(case when x.replied =1 and flag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"                to_char( sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)) shushi,\n" + 
						"                 to_char(sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)) bushushi,\n" + 
						"                 to_char(sum(flag)) heji,\n" + 
						"                 to_char(round(ratio_to_report(sum(x.flag)) over () ,4)*100,'fm9999999990.0999')||'%' as zhanbi,\n" + 
						"                 to_char(sum(hflag)) huanbishu,\n" + 
						"                 to_char(round((case when sum(hflag)=0 and  sum(flag)=0 then 0\n" + 
						"                       when sum(hflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                       else  (sum(flag)-sum(hflag))/sum(hflag) end ),4)*100,'fm999990.0999')||'%' huanbi,\n" + 
						"                 to_char(sum(tflag)) tongbishu,\n" + 
						"                 to_char(round((case when sum(tflag)=0 and  sum(flag)=0 then 0\n" + 
						"                       when sum(tflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                       else  (sum(flag)-sum(tflag))/sum(tflag) end ),4)*100,'fm9999999990.0999')||'%' tongbi\n" + 
						"                 from x group by x.code\n" + 
						"                  union all\n" + 
						"     select substr(x.code,0,3)||'00000' as code ,to_char(sum(case when x.replied =0 and flag=1 then 1 else 0 end )) weihuifu,\n" + 
						"                 to_char(sum(case when x.replied =1 and flag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"                to_char( sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)) shushi,\n" + 
						"                 to_char(sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)) bushushi,\n" + 
						"                 to_char(sum(flag)) heji,\n" + 
						"                 to_char(round(ratio_to_report(sum(x.flag)) over () ,4)*100,'fm9999999990.0999')||'%' as zhanbi,\n" + 
						"                 to_char(sum(hflag)) huanbishu,\n" + 
						"                 to_char(round((case when sum(hflag)=0 and  sum(flag)=0 then 0\n" + 
						"                       when sum(hflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                       else  (sum(flag)-sum(hflag))/sum(hflag) end ),4)*100,'fm999990.0999')||'%' huanbi,\n" + 
						"                 to_char(sum(tflag)) tongbishu,\n" + 
						"                 to_char(round((case when sum(tflag)=0 and  sum(flag)=0 then 0\n" + 
						"                       when sum(tflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                       else  (sum(flag)-sum(tflag))/sum(tflag) end ),4)*100,'fm9999999990.0999')||'%' tongbi\n" + 
						"                 from x group by substr(x.code,0,3)||'00000'\n" + 
						"                  union all\n" + 
						"     select substr(x.code,0,5)||'000' as code ,to_char(sum(case when x.replied =0 and flag=1 then 1 else 0 end )) weihuifu,\n" + 
						"                 to_char(sum(case when x.replied =1 and flag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"                to_char( sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)) shushi,\n" + 
						"                 to_char(sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)) bushushi,\n" + 
						"                 to_char(sum(flag)) heji,\n" + 
						"                 to_char(round(ratio_to_report(sum(x.flag)) over () ,4)*100,'fm9999999990.0999')||'%' as zhanbi,\n" + 
						"                 to_char(sum(hflag)) huanbishu,\n" + 
						"                 to_char(round((case when sum(hflag)=0 and  sum(flag)=0 then 0\n" + 
						"                       when sum(hflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                       else  (sum(flag)-sum(hflag))/sum(hflag) end ),4)*100,'fm999990.0999')||'%' huanbi,\n" + 
						"                 to_char(sum(tflag)) tongbishu,\n" + 
						"                 to_char(round((case when sum(tflag)=0 and  sum(flag)=0 then 0\n" + 
						"                       when sum(tflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                       else  (sum(flag)-sum(tflag))/sum(tflag) end ),4)*100,'fm9999999990.0999')||'%' tongbi\n" + 
						"                 from x group by substr(x.code,0,5)||'000' )y,\n" + 
						"\n" + 
						"\n" + 
						"\n" + 
						"                 (SELECT  CODE,  substr('　　　　　',0,level)||NAME AS NAME\n" + 
						"    FROM (SELECT * FROM DC_CODE.DC_12315_CODEDATA WHERE CODETABLE = 'CH20')\n" + 
						"   START WITH PARENTCODE = '0'\n" + 
						"  CONNECT BY PRIOR CODE = PARENTCODE\n" + 
						"  union all\n" + 
						"   select '00工商' code,'工商' name from dual\n" + 
						"    union all\n" + 
						"   select '2000000' code,'　服务' name from dual\n" + 
						"    union all\n" + 
						"   select '1000000' code,'　商品' name from dual)j\n" + 
						"  where y.code=j.code(+)\n" + 
						"  order by code\n" + 
						"\n" + 
						"                 )\n" + 
						"\n" + 
						"                 select * from x1");
		return sb.toString();
	}
	private String getXiaoWeiHuiSqlForZiXun(Map<String,String> map){
		StringBuffer sb=new StringBuffer();
		sb.append(
				"with x as (\n" +
						" select  /*+ parallel(8)*/  o.invobjtype as code,decode(f.replied,1,1,0) replied ,v.visitconfirm visitconfirm,\n" + 
						" (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						" and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) flag,\n" + 
						" (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						" and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) tflag,  --同比\n" + 
						" (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						" and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) hflag  --环比\n" + 
						"  from dc_cpr_infoware i,DC_CPR_RETURN_VISIT v,dc_cpr_involved_object o,\n" + 
						" dc_cpr_feedback f,DC_CPR_INQUIRY q,DC_CPR_INFO_PROVIDER p,dc_cpr_involved_main m\n" + 
						" where i.feedbackid=f.feedbackid(+)\n" + 
						" and i.infowareid=v.infowareid(+)\n" + 
						" and i.INQUIRYID=q.inquiryid(+)\n" + 
						" and i.INFPROID=p.INFPROID(+)\n" + 
						" and i.Invmaiid=m.INVMAIID(+)\n" + 
						" and i.invobjid=o.invobjid(+)\n" + 
						" and o.businesstype='ZH18'\n" + 
						" and o.invobjtype is not null \n");
		if (map.get("infotype")!=null&&map.get("infotype").length()!=0) {
			sb.append("and i.inftype =? \n");
			//list.add(map.get("infotype"));
		}else{
			sb.append("and i.inftype in ('3','4') \n");
		}
		
		if (map.get("dengjibumen")!=null&&map.get("dengjibumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("dengjibumen"),"i.REGDEPCODE"));
		}
		if (map.get("chengbanbumen")!=null&&map.get("chengbanbumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("chengbanbumen"),"i.HANDEPCODE"));
		}
		if (map.get("jieshoufangshi")!=null&&map.get("jieshoufangshi").length()!=0) {
			sb.append(" and i.INCFORM =? \n");
			//list.add(map.get("jieshoufangshi"));
		}
		if (map.get("suoshubumen")!=null&&map.get("suoshubumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("suoshubumen"),"q.INQDEPCODE"));
		}
		if (map.get("wentileixing")!=null&&map.get("wentileixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("wentileixing"),"i.APPLBASQUE"));
		}
		if (map.get("yewufanwei")!=null&&map.get("yewufanwei").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("yewufanwei"),"q.BUSINESSRANGE"));
		}
		if (map.get("renyuanshenfen")!=null&&map.get("renyuanshenfen").length()!=0) {
			sb.append(" and p.PERIDE=? \n");
			//list.add(map.get("renyuanshenfen"));
		}
		if (map.get("hangyeleixing")!=null&&map.get("hangyeleixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("hangyeleixing"),"m.TRADETYPE"));
		}
		if (map.get("qiyeleixing")!=null&&map.get("qiyeleixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("qiyeleixing"),"m.ENTTYPE"));
		}
		sb.append(
				"),\n" +
						"x1 as (select  distinct  y.code,nvl(j.name,y.code) name ,y.weihuifu,y.yihuifu,y.shushi,y.bushushi,y.heji,y.zhanbi,y.huanbishu,y.huanbi,y.tongbishu,y.tongbi\n" + 
						" from (\n" + 
						"select '00消委会'as code ,to_char(sum(case when x.replied =0 and flag=1 then 1 else 0 end )) weihuifu,\n" + 
						"               to_char(sum(case when x.replied =1 and flag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"              to_char( sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)) shushi,\n" + 
						"               to_char(sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)) bushushi,\n" + 
						"               to_char(sum(flag)) heji,\n" + 
						"               to_char(round(ratio_to_report(sum(x.flag)) over () ,4)*100,'fm9999999990.0999')||'%' as zhanbi,\n" + 
						"               to_char(sum(hflag)) huanbishu,\n" + 
						"               to_char(round((case when sum(hflag)=0 and  sum(flag)=0 then 0\n" + 
						"                     when sum(hflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                     else  (sum(flag)-sum(hflag))/sum(hflag) end ),4)*100,'fm999990.0999')||'%' huanbi,\n" + 
						"               to_char(sum(tflag)) tongbishu,\n" + 
						"               to_char(round((case when sum(tflag)=0 and  sum(flag)=0 then 0\n" + 
						"                     when sum(tflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                     else  (sum(flag)-sum(tflag))/sum(tflag) end ),4)*100,'fm9999999990.0999')||'%' tongbi\n" + 
						"               from x\n" + 
						"                union all\n" + 
						"        select  substr(x.code,0,2)||'00000000' as code ,to_char(sum(case when x.replied =0 and flag=1 then 1 else 0 end )) weihuifu,\n" + 
						"               to_char(sum(case when x.replied =1 and flag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"              to_char( sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)) shushi,\n" + 
						"               to_char(sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)) bushushi,\n" + 
						"               to_char(sum(flag)) heji,\n" + 
						"               to_char(round(ratio_to_report(sum(x.flag)) over () ,4)*100,'fm9999999990.0999')||'%' as zhanbi,\n" + 
						"               to_char(sum(hflag)) huanbishu,\n" + 
						"               to_char(round((case when sum(hflag)=0 and  sum(flag)=0 then 0\n" + 
						"                     when sum(hflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                     else  (sum(flag)-sum(hflag))/sum(hflag) end ),4)*100,'fm999990.0999')||'%' huanbi,\n" + 
						"               to_char(sum(tflag)) tongbishu,\n" + 
						"               to_char(round((case when sum(tflag)=0 and  sum(flag)=0 then 0\n" + 
						"                     when sum(tflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                     else  (sum(flag)-sum(tflag))/sum(tflag) end ),4)*100,'fm9999999990.0999')||'%' tongbi\n" + 
						"               from x group by substr(x.code,0,2)||'00000000'\n" + 
						"\n" + 
						"                union all\n" + 
						"   select substr(x.code,0,4)||'000000' as code ,to_char(sum(case when x.replied =0 and flag=1 then 1 else 0 end )) weihuifu,\n" + 
						"               to_char(sum(case when x.replied =1 and flag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"              to_char( sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)) shushi,\n" + 
						"               to_char(sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)) bushushi,\n" + 
						"               to_char(sum(flag)) heji,\n" + 
						"               to_char(round(ratio_to_report(sum(x.flag)) over () ,4)*100,'fm9999999990.0999')||'%' as zhanbi,\n" + 
						"               to_char(sum(hflag)) huanbishu,\n" + 
						"               to_char(round((case when sum(hflag)=0 and  sum(flag)=0 then 0\n" + 
						"                     when sum(hflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                     else  (sum(flag)-sum(hflag))/sum(hflag) end ),4)*100,'fm999990.0999')||'%' huanbi,\n" + 
						"               to_char(sum(tflag)) tongbishu,\n" + 
						"               to_char(round((case when sum(tflag)=0 and  sum(flag)=0 then 0\n" + 
						"                     when sum(tflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                     else  (sum(flag)-sum(tflag))/sum(tflag) end ),4)*100,'fm9999999990.0999')||'%' tongbi\n" + 
						"               from x group by substr(x.code,0,4)||'000000'\n" + 
						"                union all\n" + 
						"   select substr(x.code,0,6)||'0000' as code ,to_char(sum(case when x.replied =0 and flag=1 then 1 else 0 end )) weihuifu,\n" + 
						"               to_char(sum(case when x.replied =1 and flag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"              to_char( sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)) shushi,\n" + 
						"               to_char(sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)) bushushi,\n" + 
						"               to_char(sum(flag)) heji,\n" + 
						"               to_char(round(ratio_to_report(sum(x.flag)) over () ,4)*100,'fm9999999990.0999')||'%' as zhanbi,\n" + 
						"               to_char(sum(hflag)) huanbishu,\n" + 
						"               to_char(round((case when sum(hflag)=0 and  sum(flag)=0 then 0\n" + 
						"                     when sum(hflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                     else  (sum(flag)-sum(hflag))/sum(hflag) end ),4)*100,'fm999990.0999')||'%' huanbi,\n" + 
						"               to_char(sum(tflag)) tongbishu,\n" + 
						"               to_char(round((case when sum(tflag)=0 and  sum(flag)=0 then 0\n" + 
						"                     when sum(tflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                     else  (sum(flag)-sum(tflag))/sum(tflag) end ),4)*100,'fm9999999990.0999')||'%' tongbi\n" + 
						"               from x group by substr(x.code,0,6)||'0000'\n" + 
						"               union all\n" + 
						"   select substr(x.code,0,8)||'00' as code ,to_char(sum(case when x.replied =0 and flag=1 then 1 else 0 end )) weihuifu,\n" + 
						"               to_char(sum(case when x.replied =1 and flag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"              to_char( sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)) shushi,\n" + 
						"               to_char(sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)) bushushi,\n" + 
						"               to_char(sum(flag)) heji,\n" + 
						"               to_char(round(ratio_to_report(sum(x.flag)) over () ,4)*100,'fm9999999990.0999')||'%' as zhanbi,\n" + 
						"               to_char(sum(hflag)) huanbishu,\n" + 
						"               to_char(round((case when sum(hflag)=0 and  sum(flag)=0 then 0\n" + 
						"                     when sum(hflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                     else  (sum(flag)-sum(hflag))/sum(hflag) end ),4)*100,'fm999990.0999')||'%' huanbi,\n" + 
						"               to_char(sum(tflag)) tongbishu,\n" + 
						"               to_char(round((case when sum(tflag)=0 and  sum(flag)=0 then 0\n" + 
						"                     when sum(tflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                     else  (sum(flag)-sum(tflag))/sum(tflag) end ),4)*100,'fm9999999990.0999')||'%' tongbi\n" + 
						"               from x group by substr(x.code,0,8)||'00'\n" + 
						"               union all\n" + 
						"   select x.code as code ,to_char(sum(case when x.replied =0 and flag=1 then 1 else 0 end )) weihuifu,\n" + 
						"               to_char(sum(case when x.replied =1 and flag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"              to_char( sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)) shushi,\n" + 
						"               to_char(sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)) bushushi,\n" + 
						"               to_char(sum(flag)) heji,\n" + 
						"               to_char(round(ratio_to_report(sum(x.flag)) over () ,4)*100,'fm9999999990.0999')||'%' as zhanbi,\n" + 
						"               to_char(sum(hflag)) huanbishu,\n" + 
						"               to_char(round((case when sum(hflag)=0 and  sum(flag)=0 then 0\n" + 
						"                     when sum(hflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                     else  (sum(flag)-sum(hflag))/sum(hflag) end ),4)*100,'fm999990.0999')||'%' huanbi,\n" + 
						"               to_char(sum(tflag)) tongbishu,\n" + 
						"               to_char(round((case when sum(tflag)=0 and  sum(flag)=0 then 0\n" + 
						"                     when sum(tflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                     else  (sum(flag)-sum(tflag))/sum(tflag) end ),4)*100,'fm9999999990.0999')||'%' tongbi\n" + 
						"               from x group by x.code\n" + 
						"               )y,\n" + 
						"               (SELECT  CODE,  substr('　　　　　',0,level)||NAME AS NAME\n" + 
						"  FROM (SELECT * FROM DC_CODE.DC_12315_CODEDATA WHERE CODETABLE = 'ZH18')\n" + 
						" START WITH PARENTCODE = '0'\n" + 
						"CONNECT BY PRIOR CODE = PARENTCODE\n" + 
						"union all\n" + 
						" select '00消委会' code,'消委会' name from dual)j\n" + 
						"where y.code=j.code(+)\n" + 
						"order by code\n" + 
						"               )\n" + 
						"               select * from x1");
		return sb.toString();
	}
	private String getZhiShiChanQuanSqlForZiXun(Map<String ,String> map){
		StringBuffer sb=new StringBuffer();
		sb.append(
				"with x as (\n" +
						"  select  /*+ parallel(8)*/  o.invobjtype as code,decode(f.replied,1,1,0) replied ,v.visitconfirm visitconfirm,\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) flag,\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) tflag,  --同比\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) hflag  --环比\n" + 
						"   from dc_cpr_infoware i,DC_CPR_RETURN_VISIT v,dc_cpr_involved_object o,\n" + 
						"  dc_cpr_feedback f,DC_CPR_INQUIRY q,DC_CPR_INFO_PROVIDER p,dc_cpr_involved_main m\n" + 
						"  where i.feedbackid=f.feedbackid(+)\n" + 
						"  and i.infowareid=v.infowareid(+)\n" + 
						"  and i.INQUIRYID=q.inquiryid(+)\n" + 
						"  and i.INFPROID=p.INFPROID(+)\n" + 
						"  and i.Invmaiid=m.INVMAIID(+)\n" + 
						"  and i.invobjid=o.invobjid(+)\n" + 
						"  and o.businesstype='ZH19'\n" + 
						"  and o.invobjtype is not null \n");
		if (map.get("infotype")!=null&&map.get("infotype").length()!=0) {
			sb.append("and i.inftype =? \n");
			//list.add(map.get("infotype"));
		}else{
			sb.append("and i.inftype in ('3','4') \n");
		}
		
		if (map.get("dengjibumen")!=null&&map.get("dengjibumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("dengjibumen"),"i.REGDEPCODE"));
		}
		if (map.get("chengbanbumen")!=null&&map.get("chengbanbumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("chengbanbumen"),"i.HANDEPCODE"));
		}
		if (map.get("jieshoufangshi")!=null&&map.get("jieshoufangshi").length()!=0) {
			sb.append(" and i.INCFORM =? \n");
			//list.add(map.get("jieshoufangshi"));
		}
		if (map.get("suoshubumen")!=null&&map.get("suoshubumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("suoshubumen"),"q.INQDEPCODE"));
		}
		if (map.get("wentileixing")!=null&&map.get("wentileixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("wentileixing"),"i.APPLBASQUE"));
		}
		if (map.get("yewufanwei")!=null&&map.get("yewufanwei").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("yewufanwei"),"q.BUSINESSRANGE"));
		}
		if (map.get("renyuanshenfen")!=null&&map.get("renyuanshenfen").length()!=0) {
			sb.append(" and p.PERIDE=? \n");
			//list.add(map.get("renyuanshenfen"));
		}
		if (map.get("hangyeleixing")!=null&&map.get("hangyeleixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("hangyeleixing"),"m.TRADETYPE"));
		}
		if (map.get("qiyeleixing")!=null&&map.get("qiyeleixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("qiyeleixing"),"m.ENTTYPE"));
		}
		
		sb.append(
				"),\n" +
						" x1 as (select  distinct  y.code,nvl(j.name,y.code) name ,y.weihuifu,y.yihuifu,y.shushi,y.bushushi,y.heji,y.zhanbi,y.huanbishu,y.huanbi,y.tongbishu,y.tongbi\n" + 
						"  from (\n" + 
						" select '00知识产权'as code ,to_char(sum(case when x.replied =0 and flag=1 then 1 else 0 end )) weihuifu,\n" + 
						"                to_char(sum(case when x.replied =1 and flag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"               to_char( sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)) shushi,\n" + 
						"                to_char(sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)) bushushi,\n" + 
						"                to_char(sum(flag)) heji,\n" + 
						"                to_char(round(ratio_to_report(sum(x.flag)) over () ,4)*100,'fm9999999990.0999')||'%' as zhanbi,\n" + 
						"                to_char(sum(hflag)) huanbishu,\n" + 
						"                to_char(round((case when sum(hflag)=0 and  sum(flag)=0 then 0\n" + 
						"                      when sum(hflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                      else  (sum(flag)-sum(hflag))/sum(hflag) end ),4)*100,'fm999990.0999')||'%' huanbi,\n" + 
						"                to_char(sum(tflag)) tongbishu,\n" + 
						"                to_char(round((case when sum(tflag)=0 and  sum(flag)=0 then 0\n" + 
						"                      when sum(tflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                      else  (sum(flag)-sum(tflag))/sum(tflag) end ),4)*100,'fm9999999990.0999')||'%' tongbi\n" + 
						"                from x\n" + 
						"                 union all\n" + 
						"    select substr(x.code,0,1)||'00' as code ,to_char(sum(case when x.replied =0 and flag=1 then 1 else 0 end )) weihuifu,\n" + 
						"                to_char(sum(case when x.replied =1 and flag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"               to_char( sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)) shushi,\n" + 
						"                to_char(sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)) bushushi,\n" + 
						"                to_char(sum(flag)) heji,\n" + 
						"                to_char(round(ratio_to_report(sum(x.flag)) over () ,4)*100,'fm9999999990.0999')||'%' as zhanbi,\n" + 
						"                to_char(sum(hflag)) huanbishu,\n" + 
						"                to_char(round((case when sum(hflag)=0 and  sum(flag)=0 then 0\n" + 
						"                      when sum(hflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                      else  (sum(flag)-sum(hflag))/sum(hflag) end ),4)*100,'fm999990.0999')||'%' huanbi,\n" + 
						"                to_char(sum(tflag)) tongbishu,\n" + 
						"                to_char(round((case when sum(tflag)=0 and  sum(flag)=0 then 0\n" + 
						"                      when sum(tflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                      else  (sum(flag)-sum(tflag))/sum(tflag) end ),4)*100,'fm9999999990.0999')||'%' tongbi\n" + 
						"                from x group by substr(x.code,0,1)||'00'\n" + 
						"                union all\n" + 
						"    select x.code as code ,to_char(sum(case when x.replied =0 and flag=1 then 1 else 0 end )) weihuifu,\n" + 
						"                to_char(sum(case when x.replied =1 and flag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"               to_char( sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)) shushi,\n" + 
						"                to_char(sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)) bushushi,\n" + 
						"                to_char(sum(flag)) heji,\n" + 
						"                to_char(round(ratio_to_report(sum(x.flag)) over () ,4)*100,'fm9999999990.0999')||'%' as zhanbi,\n" + 
						"                to_char(sum(hflag)) huanbishu,\n" + 
						"                to_char(round((case when sum(hflag)=0 and  sum(flag)=0 then 0\n" + 
						"                      when sum(hflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                      else  (sum(flag)-sum(hflag))/sum(hflag) end ),4)*100,'fm999990.0999')||'%' huanbi,\n" + 
						"                to_char(sum(tflag)) tongbishu,\n" + 
						"                to_char(round((case when sum(tflag)=0 and  sum(flag)=0 then 0\n" + 
						"                      when sum(tflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                      else  (sum(flag)-sum(tflag))/sum(tflag) end ),4)*100,'fm9999999990.0999')||'%' tongbi\n" + 
						"                from x group by x.code\n" + 
						"\n" + 
						"                )y,\n" + 
						"\n" + 
						"                (SELECT  CODE,  substr('　　　　　',0,level)||NAME AS NAME\n" + 
						"   FROM (SELECT * FROM DC_CODE.DC_12315_CODEDATA WHERE CODETABLE = 'ZH19')\n" + 
						"  START WITH PARENTCODE = '0'\n" + 
						" CONNECT BY PRIOR CODE = PARENTCODE\n" + 
						" union all\n" + 
						"  select '00知识产权' code,'知识产权' name from dual)j\n" + 
						" where y.code=j.code(+)\n" + 
						" order by code\n" + 
						"                )\n" + 
						"                select * from x1");
		return sb.toString();
	}
	private String getJiaGeJianChaSqlForZiXun(Map<String ,String> map){
		StringBuffer sb=new StringBuffer();
		sb.append("with x as ( \n");
		sb.append(
				"select  /*+ parallel(8)*/  substr(o.invobjtype,0,2)||'00' as code,j.name,decode(f.replied,1,1,0) replied ,v.visitconfirm visitconfirm,\n" +
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) flag,\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) tflag,  --同比\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) hflag  --环比\n" + 
						"   from dc_cpr_infoware i,DC_CPR_RETURN_VISIT v,dc_cpr_involved_object o,dc_code.dc_12315_codedata j,\n" + 
						"  dc_cpr_feedback f,DC_CPR_INQUIRY q,DC_CPR_INFO_PROVIDER p,dc_cpr_involved_main m\n" + 
						"  where i.feedbackid=f.feedbackid(+)\n" + 
						"  and i.infowareid=v.infowareid(+)\n" + 
						"  and i.INQUIRYID=q.inquiryid(+)\n" + 
						"  and i.INFPROID=p.INFPROID(+)\n" + 
						"  and i.Invmaiid=m.INVMAIID(+)\n" + 
						"  and i.invobjid=o.invobjid(+)\n" + 
						"  and o.businesstype='ZH20'\n" + 
						"  and j.codetable(+)='ZH20'\n" + 
						"  and j.code(+)=substr(o.invobjtype,0,2)||'00'\n" + 
						"  and o.invobjtype is not null \n");
		if (map.get("infotype")!=null&&map.get("infotype").length()!=0) {
			sb.append("and i.inftype =? \n");
			//list.add(map.get("infotype"));
		}else{
			sb.append("and i.inftype in ('3','4') \n");
		}
		
		if (map.get("dengjibumen")!=null&&map.get("dengjibumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("dengjibumen"),"i.REGDEPCODE"));
		}
		if (map.get("chengbanbumen")!=null&&map.get("chengbanbumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("chengbanbumen"),"i.HANDEPCODE"));
		}
		if (map.get("jieshoufangshi")!=null&&map.get("jieshoufangshi").length()!=0) {
			sb.append(" and i.INCFORM =? \n");
		}
		if (map.get("suoshubumen")!=null&&map.get("suoshubumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("suoshubumen"),"q.INQDEPCODE"));
		}
		if (map.get("wentileixing")!=null&&map.get("wentileixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("wentileixing"),"i.APPLBASQUE"));
		}
		if (map.get("yewufanwei")!=null&&map.get("yewufanwei").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("yewufanwei"),"q.BUSINESSRANGE"));
		}
		if (map.get("renyuanshenfen")!=null&&map.get("renyuanshenfen").length()!=0) {
			sb.append(" and p.PERIDE=? \n");
		}
		if (map.get("hangyeleixing")!=null&&map.get("hangyeleixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("hangyeleixing"),"m.TRADETYPE"));
		}
		if (map.get("qiyeleixing")!=null&&map.get("qiyeleixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("qiyeleixing"),"m.ENTTYPE"));
		}
		sb.append(
				"union all\n" +
						"  select  /*+ parallel(8)*/  o.invobjtype as code,'　'||j.name as name,decode(f.replied,1,1,0) replied ,v.visitconfirm visitconfirm,\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) flag,\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) tflag,  --同比\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) hflag  --环比\n" + 
						"   from dc_cpr_infoware i,DC_CPR_RETURN_VISIT v,dc_cpr_involved_object o,dc_code.dc_12315_codedata j,\n" + 
						"  dc_cpr_feedback f,DC_CPR_INQUIRY q,DC_CPR_INFO_PROVIDER p,dc_cpr_involved_main m\n" + 
						"  where i.feedbackid=f.feedbackid(+)\n" + 
						"  and i.infowareid=v.infowareid(+)\n" + 
						"  and i.INQUIRYID=q.inquiryid(+)\n" + 
						"  and i.INFPROID=p.INFPROID(+)\n" + 
						"  and i.Invmaiid=m.INVMAIID(+)\n" + 
						"  and i.invobjid=o.invobjid(+)\n" + 
						"  and o.businesstype='ZH20'\n" + 
						"  and j.codetable(+)='ZH20'\n" + 
						"  and j.code(+)=o.invobjtype\n" + 
						"  and o.invobjtype is not null \n");
		if (map.get("infotype")!=null&&map.get("infotype").length()!=0) {
			sb.append("and i.inftype =? \n");
		}else{
			sb.append("and i.inftype in ('3','4') \n");
		}
		
		if (map.get("dengjibumen")!=null&&map.get("dengjibumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("dengjibumen"),"i.REGDEPCODE"));
		}
		if (map.get("chengbanbumen")!=null&&map.get("chengbanbumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("chengbanbumen"),"i.HANDEPCODE"));
		}
		if (map.get("jieshoufangshi")!=null&&map.get("jieshoufangshi").length()!=0) {
			sb.append(" and i.INCFORM =? \n");
		}
		if (map.get("suoshubumen")!=null&&map.get("suoshubumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("suoshubumen"),"q.INQDEPCODE"));
		}
		if (map.get("wentileixing")!=null&&map.get("wentileixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("wentileixing"),"i.APPLBASQUE"));
		}
		if (map.get("yewufanwei")!=null&&map.get("yewufanwei").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("yewufanwei"),"q.BUSINESSRANGE"));
		}
		if (map.get("renyuanshenfen")!=null&&map.get("renyuanshenfen").length()!=0) {
			sb.append(" and p.PERIDE=? \n");
		}
		if (map.get("hangyeleixing")!=null&&map.get("hangyeleixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("hangyeleixing"),"m.TRADETYPE"));
		}
		if (map.get("qiyeleixing")!=null&&map.get("qiyeleixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("qiyeleixing"),"m.ENTTYPE"));
		}
		sb.append(
				")\n" +
						"                 select x.code as code ,x.name as name,to_char(sum(case when x.replied =0 and flag=1 then 1 else 0 end )) weihuifu,\n" + 
						"                 to_char(sum(case when x.replied =1 and flag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"                to_char( sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)) shushi,\n" + 
						"                 to_char(sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)) bushushi,\n" + 
						"                 to_char(sum(flag)) heji,\n" + 
						"                 to_char(round(ratio_to_report(sum(x.flag)) over () ,4)*200,'fm9999999990.0999')||'%' as zhanbi,\n" + 
						"                 to_char(sum(hflag)) huanbishu,\n" + 
						"                 to_char(round((case when sum(hflag)=0 and  sum(flag)=0 then 0\n" + 
						"                       when sum(hflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                       else  (sum(flag)-sum(hflag))/sum(hflag) end ),4)*100,'fm999990.0999')||'%' huanbi,\n" + 
						"                 to_char(sum(tflag)) tongbishu,\n" + 
						"                 to_char(round((case when sum(tflag)=0 and  sum(flag)=0 then 0\n" + 
						"                       when sum(tflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                       else  (sum(flag)-sum(tflag))/sum(tflag) end ),4)*100,'fm9999999990.0999')||'%' tongbi\n" + 
						"                 from x group by x.code ,x.name\n" + 
						"                 order by code");
		
		return sb.toString();
	}
	private String getZhiLiangJianDuSqlForZiXun(Map<String ,String> map){
		StringBuffer sb=new StringBuffer();
		sb.append(
				"with x as (\n" +
						"  select  /*+ parallel(8)*/  o.invobjtype as code,decode(f.replied,1,1,0) replied ,v.visitconfirm visitconfirm,\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) flag,\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) tflag,  --同比\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) hflag  --环比\n" + 
						"   from dc_cpr_infoware i,DC_CPR_RETURN_VISIT v,dc_cpr_involved_object o,\n" + 
						"  dc_cpr_feedback f,DC_CPR_INQUIRY q,DC_CPR_INFO_PROVIDER p,dc_cpr_involved_main m\n" + 
						"  where i.feedbackid=f.feedbackid(+)\n" + 
						"  and i.infowareid=v.infowareid(+)\n" + 
						"  and i.INQUIRYID=q.inquiryid(+)\n" + 
						"  and i.INFPROID=p.INFPROID(+)\n" + 
						"  and i.Invmaiid=m.INVMAIID(+)\n" + 
						"  and i.invobjid=o.invobjid(+)\n" + 
						"  and o.businesstype='ZH21'\n" + 
						"  and o.invobjtype is not null \n");
		if (map.get("infotype")!=null&&map.get("infotype").length()!=0) {
			sb.append("and i.inftype =? \n");
			//list.add(map.get("infotype"));
		}else{
			sb.append("and i.inftype in ('3','4') \n");
		}
		
		if (map.get("dengjibumen")!=null&&map.get("dengjibumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("dengjibumen"),"i.REGDEPCODE"));
		}
		if (map.get("chengbanbumen")!=null&&map.get("chengbanbumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("chengbanbumen"),"i.HANDEPCODE"));
		}
		if (map.get("jieshoufangshi")!=null&&map.get("jieshoufangshi").length()!=0) {
			sb.append(" and i.INCFORM =? \n");
		}
		if (map.get("suoshubumen")!=null&&map.get("suoshubumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("suoshubumen"),"q.INQDEPCODE"));
		}
		if (map.get("wentileixing")!=null&&map.get("wentileixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("wentileixing"),"i.APPLBASQUE"));
		}
		if (map.get("yewufanwei")!=null&&map.get("yewufanwei").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("yewufanwei"),"q.BUSINESSRANGE"));
		}
		if (map.get("renyuanshenfen")!=null&&map.get("renyuanshenfen").length()!=0) {
			sb.append(" and p.PERIDE=? \n");
		}
		if (map.get("hangyeleixing")!=null&&map.get("hangyeleixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("hangyeleixing"),"m.TRADETYPE"));
		}
		if (map.get("qiyeleixing")!=null&&map.get("qiyeleixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("qiyeleixing"),"m.ENTTYPE"));
		}
		sb.append(
				"),\n" +
						"x1 as (select  distinct  y.code,nvl(j.name,y.code) name ,y.weihuifu,y.yihuifu,y.shushi,y.bushushi,y.heji,y.zhanbi,y.huanbishu,y.huanbi,y.tongbishu,y.tongbi\n" + 
						" from (\n" + 
						"select '00质量监督'as code ,to_char(sum(case when x.replied =0 and flag=1 then 1 else 0 end )) weihuifu,\n" + 
						"               to_char(sum(case when x.replied =1 and flag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"              to_char( sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)) shushi,\n" + 
						"               to_char(sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)) bushushi,\n" + 
						"               to_char(sum(flag)) heji,\n" + 
						"               to_char(round(ratio_to_report(sum(x.flag)) over () ,4)*100,'fm9999999990.0999')||'%' as zhanbi,\n" + 
						"               to_char(sum(hflag)) huanbishu,\n" + 
						"               to_char(round((case when sum(hflag)=0 and  sum(flag)=0 then 0\n" + 
						"                     when sum(hflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                     else  (sum(flag)-sum(hflag))/sum(hflag) end ),4)*100,'fm999990.0999')||'%' huanbi,\n" + 
						"               to_char(sum(tflag)) tongbishu,\n" + 
						"               to_char(round((case when sum(tflag)=0 and  sum(flag)=0 then 0\n" + 
						"                     when sum(tflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                     else  (sum(flag)-sum(tflag))/sum(tflag) end ),4)*100,'fm9999999990.0999')||'%' tongbi\n" + 
						"               from x\n" + 
						"                union all\n" + 
						"        select  substr(x.code,0,2)||'00000000' as code ,to_char(sum(case when x.replied =0 and flag=1 then 1 else 0 end )) weihuifu,\n" + 
						"               to_char(sum(case when x.replied =1 and flag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"              to_char( sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)) shushi,\n" + 
						"               to_char(sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)) bushushi,\n" + 
						"               to_char(sum(flag)) heji,\n" + 
						"               to_char(round(ratio_to_report(sum(x.flag)) over () ,4)*100,'fm9999999990.0999')||'%' as zhanbi,\n" + 
						"               to_char(sum(hflag)) huanbishu,\n" + 
						"               to_char(round((case when sum(hflag)=0 and  sum(flag)=0 then 0\n" + 
						"                     when sum(hflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                     else  (sum(flag)-sum(hflag))/sum(hflag) end ),4)*100,'fm999990.0999')||'%' huanbi,\n" + 
						"               to_char(sum(tflag)) tongbishu,\n" + 
						"               to_char(round((case when sum(tflag)=0 and  sum(flag)=0 then 0\n" + 
						"                     when sum(tflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                     else  (sum(flag)-sum(tflag))/sum(tflag) end ),4)*100,'fm9999999990.0999')||'%' tongbi\n" + 
						"               from x group by substr(x.code,0,2)||'00000000'\n" + 
						"\n" + 
						"                union all\n" + 
						"   select substr(x.code,0,4)||'000000' as code ,to_char(sum(case when x.replied =0 and flag=1 then 1 else 0 end )) weihuifu,\n" + 
						"               to_char(sum(case when x.replied =1 and flag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"              to_char( sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)) shushi,\n" + 
						"               to_char(sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)) bushushi,\n" + 
						"               to_char(sum(flag)) heji,\n" + 
						"               to_char(round(ratio_to_report(sum(x.flag)) over () ,4)*100,'fm9999999990.0999')||'%' as zhanbi,\n" + 
						"               to_char(sum(hflag)) huanbishu,\n" + 
						"               to_char(round((case when sum(hflag)=0 and  sum(flag)=0 then 0\n" + 
						"                     when sum(hflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                     else  (sum(flag)-sum(hflag))/sum(hflag) end ),4)*100,'fm999990.0999')||'%' huanbi,\n" + 
						"               to_char(sum(tflag)) tongbishu,\n" + 
						"               to_char(round((case when sum(tflag)=0 and  sum(flag)=0 then 0\n" + 
						"                     when sum(tflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                     else  (sum(flag)-sum(tflag))/sum(tflag) end ),4)*100,'fm9999999990.0999')||'%' tongbi\n" + 
						"               from x group by substr(x.code,0,4)||'000000'\n" + 
						"                union all\n" + 
						"   select substr(x.code,0,6)||'0000' as code ,to_char(sum(case when x.replied =0 and flag=1 then 1 else 0 end )) weihuifu,\n" + 
						"               to_char(sum(case when x.replied =1 and flag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"              to_char( sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)) shushi,\n" + 
						"               to_char(sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)) bushushi,\n" + 
						"               to_char(sum(flag)) heji,\n" + 
						"               to_char(round(ratio_to_report(sum(x.flag)) over () ,4)*100,'fm9999999990.0999')||'%' as zhanbi,\n" + 
						"               to_char(sum(hflag)) huanbishu,\n" + 
						"               to_char(round((case when sum(hflag)=0 and  sum(flag)=0 then 0\n" + 
						"                     when sum(hflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                     else  (sum(flag)-sum(hflag))/sum(hflag) end ),4)*100,'fm999990.0999')||'%' huanbi,\n" + 
						"               to_char(sum(tflag)) tongbishu,\n" + 
						"               to_char(round((case when sum(tflag)=0 and  sum(flag)=0 then 0\n" + 
						"                     when sum(tflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                     else  (sum(flag)-sum(tflag))/sum(tflag) end ),4)*100,'fm9999999990.0999')||'%' tongbi\n" + 
						"               from x group by substr(x.code,0,6)||'0000'\n" + 
						"               union all\n" + 
						"   select substr(x.code,0,8)||'00' as code ,to_char(sum(case when x.replied =0 and flag=1 then 1 else 0 end )) weihuifu,\n" + 
						"               to_char(sum(case when x.replied =1 and flag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"              to_char( sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)) shushi,\n" + 
						"               to_char(sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)) bushushi,\n" + 
						"               to_char(sum(flag)) heji,\n" + 
						"               to_char(round(ratio_to_report(sum(x.flag)) over () ,4)*100,'fm9999999990.0999')||'%' as zhanbi,\n" + 
						"               to_char(sum(hflag)) huanbishu,\n" + 
						"               to_char(round((case when sum(hflag)=0 and  sum(flag)=0 then 0\n" + 
						"                     when sum(hflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                     else  (sum(flag)-sum(hflag))/sum(hflag) end ),4)*100,'fm999990.0999')||'%' huanbi,\n" + 
						"               to_char(sum(tflag)) tongbishu,\n" + 
						"               to_char(round((case when sum(tflag)=0 and  sum(flag)=0 then 0\n" + 
						"                     when sum(tflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                     else  (sum(flag)-sum(tflag))/sum(tflag) end ),4)*100,'fm9999999990.0999')||'%' tongbi\n" + 
						"               from x group by substr(x.code,0,8)||'00'\n" + 
						"               union all\n" + 
						"   select x.code as code ,to_char(sum(case when x.replied =0 and flag=1 then 1 else 0 end )) weihuifu,\n" + 
						"               to_char(sum(case when x.replied =1 and flag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"              to_char( sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)) shushi,\n" + 
						"               to_char(sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)) bushushi,\n" + 
						"               to_char(sum(flag)) heji,\n" + 
						"               to_char(round(ratio_to_report(sum(x.flag)) over () ,4)*100,'fm9999999990.0999')||'%' as zhanbi,\n" + 
						"               to_char(sum(hflag)) huanbishu,\n" + 
						"               to_char(round((case when sum(hflag)=0 and  sum(flag)=0 then 0\n" + 
						"                     when sum(hflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                     else  (sum(flag)-sum(hflag))/sum(hflag) end ),4)*100,'fm999990.0999')||'%' huanbi,\n" + 
						"               to_char(sum(tflag)) tongbishu,\n" + 
						"               to_char(round((case when sum(tflag)=0 and  sum(flag)=0 then 0\n" + 
						"                     when sum(tflag)=0 and  sum(flag)<>0 then 1\n" + 
						"                     else  (sum(flag)-sum(tflag))/sum(tflag) end ),4)*100,'fm9999999990.0999')||'%' tongbi\n" + 
						"               from x group by x.code\n" + 
						"               )y,\n" + 
						"               (SELECT  CODE,  substr('　　　　　',0,level)||NAME AS NAME\n" + 
						"  FROM (SELECT * FROM DC_CODE.DC_12315_CODEDATA WHERE CODETABLE = 'ZH21')\n" + 
						" START WITH PARENTCODE = '0'\n" + 
						"CONNECT BY PRIOR CODE = PARENTCODE\n" + 
						"union all\n" + 
						" select '00质量监督' code,'质量监督' name from dual)j\n" + 
						"where y.code=j.code(+)\n" + 
						"order by code\n" + 
						"               )\n" + 
						"               select * from x1");
		return sb.toString();
	}
	private String getTongBiHuanBiSqlForZiXun(Map<String ,String> map){
		StringBuffer sb=new StringBuffer();
		sb.append(
				"with x as (\n" +
						" select  /*+ parallel(8)*/  o.invobjtype as code,decode(f.replied,1,1,0) replied ,v.visitconfirm visitconfirm,\n" + 
						" (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						" and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) flag,\n" + 
						" (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						" and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) tflag,  --同比\n" + 
						" (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						" and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) hflag  --环比\n" + 
						"  from dc_cpr_infoware i,DC_CPR_RETURN_VISIT v,dc_cpr_involved_object o,\n" + 
						" dc_cpr_feedback f,DC_CPR_INQUIRY q,DC_CPR_INFO_PROVIDER p,dc_cpr_involved_main m\n" + 
						" where i.feedbackid=f.feedbackid(+)\n" + 
						" and i.infowareid=v.infowareid(+)\n" + 
						" and i.INQUIRYID=q.inquiryid(+)\n" + 
						" and i.INFPROID=p.INFPROID(+)\n" + 
						" and i.Invmaiid=m.INVMAIID(+)\n" + 
						" and i.invobjid=o.invobjid(+)\n" + 
						" and o.businesstype in ('CH20','ZH18','ZH19','ZH20','ZH21') \n");
		if (map.get("infotype")!=null&&map.get("infotype").length()!=0) {
			sb.append("and i.inftype =? \n");
		}else{
			sb.append("and i.inftype in ('3','4') \n");
		}
		
		if (map.get("dengjibumen")!=null&&map.get("dengjibumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("dengjibumen"),"i.REGDEPCODE"));
		}
		if (map.get("chengbanbumen")!=null&&map.get("chengbanbumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("chengbanbumen"),"i.HANDEPCODE"));
		}
		if (map.get("jieshoufangshi")!=null&&map.get("jieshoufangshi").length()!=0) {
			sb.append(" and i.INCFORM =? \n");
		}
		if (map.get("suoshubumen")!=null&&map.get("suoshubumen").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("suoshubumen"),"q.INQDEPCODE"));
		}
		if (map.get("wentileixing")!=null&&map.get("wentileixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("wentileixing"),"i.APPLBASQUE"));
		}
		if (map.get("yewufanwei")!=null&&map.get("yewufanwei").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("yewufanwei"),"q.BUSINESSRANGE"));
		}
		if (map.get("renyuanshenfen")!=null&&map.get("renyuanshenfen").length()!=0) {
			sb.append(" and p.PERIDE=? \n");
		}
		if (map.get("hangyeleixing")!=null&&map.get("hangyeleixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("hangyeleixing"),"m.TRADETYPE"));
		}
		if (map.get("qiyeleixing")!=null&&map.get("qiyeleixing").length()!=0) {
			sb.append(QueryJieAnUtils.splitStringByComma(map.get("qiyeleixing"),"m.ENTTYPE"));
		}
		sb.append(
				")\n" +
						"                 select '9999999901' as code,'占总量' as name,\n" + 
						"                  to_char(round((case when\n" + 
						"                 sum(case when x.replied =0 and flag=1 then 1 else 0 end )=0 then 0 else\n" + 
						"                 sum(case when x.replied =0 and flag=1 then 1 else 0 end )/sum(flag) end\n" + 
						"                 ),4)*100,'fm9999999990.0999')||'%' weihuifu,\n" + 
						"                 to_char(round((case when\n" + 
						"                 sum(case when x.replied =1 and flag=1 then 1 else 0 end )=0 then 0 else\n" + 
						"                 sum(case when x.replied =1 and flag=1 then 1 else 0 end )/sum(flag) end\n" + 
						"                 ),4)*100,'fm9999999990.0999')||'%' yihuifu,\n" + 
						"                  to_char(round((case when\n" + 
						"                 sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)=0 then 0 else\n" + 
						"                 sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)/sum(flag) end\n" + 
						"                 ) ,4)*100,'fm9999999990.0999')||'%'shushi,\n" + 
						"                  to_char(round((case when\n" + 
						"                 sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)=0 then 0 else\n" + 
						"                 sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)/sum(flag) end\n" + 
						"                 ) ,4)*100,'fm9999999990.0999')||'%'bushushi,\n" + 
						"                  to_char(round((case when sum(flag)=0 then 0 else\n" + 
						"                 sum(flag)/sum(flag) end),4)*100,'fm9999999990.0999')||'%' heji ,\n" + 
						"                 ' ' as zhanbi, ' ' as huanbishu,' ' as huanbi,' ' as tongbishu, ' ' as tongbi\n" + 
						"                 from x\n" + 
						"              union all\n" + 
						"              select '9999999902'  as code ,'上时段数据' as name,\n" + 
						"              to_char(sum(case when x.replied =0 and hflag=1 then 1 else 0 end )) weihuifu,\n" + 
						"                 to_char(sum(case when x.replied =1 and hflag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"                to_char( sum(case when x.visitconfirm =1 and hflag=1 then 1 else 0 end)) shushi,\n" + 
						"                 to_char(sum(case when x.visitconfirm =2 and hflag=1 then 1 else 0 end)) bushushi,\n" + 
						"                 to_char(sum(hflag)) heji,\n" + 
						"                 ' ' as zhanbi, ' ' as huanbishu, ' ' as huanbi, ' ' as tongbishu, ' ' as tongbi\n" + 
						"                 from x\n" + 
						"              union all\n" + 
						"               select '9999999903'  as code ,'环比增减（%）' as name,\n" + 
						"                to_char(round(\n" + 
						"                (case when sum(case when x.replied =0 and hflag=1 then 1 else 0 end )=0\n" + 
						"                             and  sum(case when x.replied =0 and flag=1 then 1 else 0 end )=0\n" + 
						"                             then 0\n" + 
						"                             when sum(case when x.replied =0 and hflag=1 then 1 else 0 end )=0\n" + 
						"                             and  sum(case when x.replied =0 and flag=1 then 1 else 0 end )<>0\n" + 
						"                             then 1 else\n" + 
						"                             (sum(case when x.replied =0 and flag=1 then 1 else 0 end )-\n" + 
						"                             sum(case when x.replied =0 and hflag=1 then 1 else 0 end ))/\n" + 
						"                             sum(case when x.replied =0 and hflag=1 then 1 else 0 end ) end)\n" + 
						"                             ,4)*100,'fm9999999990.0999')||'%'  weihuifu,\n" + 
						"                to_char(round(\n" + 
						"                     (case when sum(case when x.replied =1 and hflag=1 then 1 else 0 end )=0\n" + 
						"                           and  sum(case when x.replied =1 and flag=1 then 1 else 0 end )=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.replied =1 and hflag=1 then 1 else 0 end )=0\n" + 
						"                           and  sum(case when x.replied =1 and flag=1 then 1 else 0 end )<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.replied =1 and flag=1 then 1 else 0 end )-\n" + 
						"                           sum(case when x.replied =1 and hflag=1 then 1 else 0 end ))/\n" + 
						"                           sum(case when x.replied =1 and hflag=1 then 1 else 0 end ) end)\n" + 
						"                            ,4)*100,'fm9999999990.0999')||'%' as yihuifu,\n" + 
						"                to_char(round(\n" + 
						"                     (case when sum(case when x.visitconfirm =1 and hflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.visitconfirm =1 and hflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)-\n" + 
						"                           sum(case when x.visitconfirm =1 and hflag=1 then 1 else 0 end))/\n" + 
						"                           sum(case when x.visitconfirm =1 and hflag=1 then 1 else 0 end) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  shushi,\n" + 
						"               to_char(round(\n" + 
						"                     (case when sum(case when x.visitconfirm =2 and hflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.visitconfirm =2 and hflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)-\n" + 
						"                           sum(case when x.visitconfirm =2 and hflag=1 then 1 else 0 end))/\n" + 
						"                           sum(case when x.visitconfirm =2 and hflag=1 then 1 else 0 end) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  bushushi,\n" + 
						"                           to_char(round(\n" + 
						"                     (case when sum(hflag)=0  and  sum(flag)=0   then 0\n" + 
						"                           when sum(hflag)=0  and  sum(flag)<>0  then 1\n" + 
						"                             else(sum(flag)- sum(hflag))/sum(hflag) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  heji,\n" + 
						"                 ' ' as zhanbi, ' ' as huanbishu, ' ' as huanbi, ' ' as tongbishu, ' ' as tongbi\n" + 
						"                 from x\n" + 
						"            union all\n" + 
						"              select '9999999904'  as code ,'去年同期数据' as name,\n" + 
						"              to_char(sum(case when x.replied =0 and tflag=1 then 1 else 0 end )) weihuifu,\n" + 
						"                 to_char(sum(case when x.replied =1 and tflag=1 then 1 else 0 end )) as yihuifu,\n" + 
						"                to_char( sum(case when x.visitconfirm =1 and tflag=1 then 1 else 0 end)) shushi,\n" + 
						"                 to_char(sum(case when x.visitconfirm =2 and tflag=1 then 1 else 0 end)) bushushi,\n" + 
						"                 to_char(sum(tflag)) heji,\n" + 
						"                 ' ' as zhanbi, ' ' as huanbishu, ' ' as huanbi, ' ' as tongbishu, ' ' as tongbi\n" + 
						"                 from x\n" + 
						"                union all\n" + 
						"               select '9999999905'  as code ,'同比增减（%）' as name,\n" + 
						"                to_char(round(\n" + 
						"                (case when sum(case when x.replied =0 and tflag=1 then 1 else 0 end )=0\n" + 
						"                             and  sum(case when x.replied =0 and flag=1 then 1 else 0 end )=0\n" + 
						"                             then 0\n" + 
						"                             when sum(case when x.replied =0 and tflag=1 then 1 else 0 end )=0\n" + 
						"                             and  sum(case when x.replied =0 and flag=1 then 1 else 0 end )<>0\n" + 
						"                             then 1 else\n" + 
						"                             (sum(case when x.replied =0 and flag=1 then 1 else 0 end )-\n" + 
						"                             sum(case when x.replied =0 and tflag=1 then 1 else 0 end ))/\n" + 
						"                             sum(case when x.replied =0 and tflag=1 then 1 else 0 end ) end)\n" + 
						"                             ,4)*100,'fm9999999990.0999')||'%'  weihuifu,\n" + 
						"                to_char(round(\n" + 
						"                     (case when sum(case when x.replied =1 and tflag=1 then 1 else 0 end )=0\n" + 
						"                           and  sum(case when x.replied =1 and flag=1 then 1 else 0 end )=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.replied =1 and tflag=1 then 1 else 0 end )=0\n" + 
						"                           and  sum(case when x.replied =1 and flag=1 then 1 else 0 end )<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.replied =1 and flag=1 then 1 else 0 end )-\n" + 
						"                           sum(case when x.replied =1 and tflag=1 then 1 else 0 end ))/\n" + 
						"                           sum(case when x.replied =1 and tflag=1 then 1 else 0 end ) end)\n" + 
						"                            ,4)*100,'fm9999999990.0999')||'%' as yihuifu,\n" + 
						"                to_char(round(\n" + 
						"                     (case when sum(case when x.visitconfirm =1 and tflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.visitconfirm =1 and tflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.visitconfirm =1 and flag=1 then 1 else 0 end)-\n" + 
						"                           sum(case when x.visitconfirm =1 and tflag=1 then 1 else 0 end))/\n" + 
						"                           sum(case when x.visitconfirm =1 and tflag=1 then 1 else 0 end) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  shushi,\n" + 
						"               to_char(round(\n" + 
						"                     (case when sum(case when x.visitconfirm =2 and tflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)=0\n" + 
						"                           then 0\n" + 
						"                           when sum(case when x.visitconfirm =2 and tflag=1 then 1 else 0 end)=0\n" + 
						"                           and  sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)<>0\n" + 
						"                           then 1 else\n" + 
						"                           (sum(case when x.visitconfirm =2 and flag=1 then 1 else 0 end)-\n" + 
						"                           sum(case when x.visitconfirm =2 and tflag=1 then 1 else 0 end))/\n" + 
						"                           sum(case when x.visitconfirm =2 and tflag=1 then 1 else 0 end) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  bushushi,\n" + 
						"                           to_char(round(\n" + 
						"                     (case when sum(tflag)=0  and  sum(flag)=0   then 0\n" + 
						"                           when sum(tflag)=0  and  sum(flag)<>0  then 1\n" + 
						"                             else(sum(flag)- sum(tflag))/sum(tflag) end)\n" + 
						"                           ,4)*100,'fm9999999990.0999')||'%' as  heji,\n" + 
						"                 ' ' as zhanbi, ' ' as huanbishu, ' ' as huanbi, ' ' as tongbishu, ' ' as tongbi\n" + 
						"                 from x"); 
		return sb.toString();
	}
	
	public List<Map> xiaoWeiHuiQianShi(String tongjinianfen,
			String shangnian, int i, HttpServletRequest req) {
		StringBuffer sb=new StringBuffer();
		List<Map> res=new ArrayList<Map>();
		sb.append("select j.name,nvl(t.\"");
		sb.append(shangnian);
		sb.append("\",0) \"");
		sb.append(shangnian);
		sb.append("\",nvl(t.\"");
		sb.append(tongjinianfen);
		sb.append("\",0) \"");
		sb.append(tongjinianfen);
		sb.append("\", \n");
		sb.append("to_char(round(t.cnt,4)*100,'fm9999999990.099')||'%' cnt from (\n" +
						" SELECT T.INVOBJTYPE,T.\"");
		sb.append(shangnian);
		sb.append("\",t.\"");
		sb.append(tongjinianfen);
		sb.append("\",(case when nvl(T.\"");
		sb.append(shangnian);
		sb.append("\",0)=0\n" +" and nvl(T.\"");
		sb.append(tongjinianfen);
		sb.append("\",0)=0 then 0\n" +" when nvl(T.\"");
		sb.append(shangnian);
		sb.append("\",0)=0\n" +" and nvl(T.\"");
		sb.append(tongjinianfen);
		sb.append("\",0)<>0 then 1\n" +" else (nvl(t.\"");
		sb.append(tongjinianfen);
		sb.append("\",0)-t.\"");
		sb.append(shangnian);
		sb.append("\")/t.\"");
		sb.append(shangnian);
		sb.append(
				"\"\n" +
						" end  )cnt  FROM(\n" + 
						" SELECT * FROM (\n" + 
						" SELECT TO_CHAR(I.REGTIME,'YYYY')REGTIME ,O.INVOBJTYPE,COUNT(1) NUMS FROM DC_CPR_INFOWARE i,dc_cpr_involved_object o\n" + 
						" where  i.inftype='6'\n" + 
						" and o.invobjid=i.invobjid \n");
		sb.append(
						"GROUP BY O.INVOBJTYPE,TO_CHAR(I.REGTIME,'YYYY')\n" +
						")T\n" + 
						" PIVOT(SUM(T.NUMS) FOR  REGTIME IN (");
		sb.append(shangnian);
		sb.append(",");
		sb.append(tongjinianfen);
		sb.append(
				"))\n" +
						")T\n" + 
						"order by abs(cnt) desc,\"");
		sb.append(shangnian);
		sb.append("\" desc,\"");
		sb.append(tongjinianfen);
		sb.append("\" desc\n" +" )t,\n" + 
						"( select * from dc_code.dc_12315_codedata  j where codetable='ZH18') j\n" + 
						"where t.INVOBJTYPE=j.code(+)\n" + 
						"and rownum<=10");
		try {
			res=dao.queryForList(sb.toString(), null);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("消委会投诉增长或下降居前十位的商品和服务", "WDY", i == 1 ? "查看报表" : "下载报表", sb.toString(),
				tongjinianfen+","+shangnian, req);
		return res;
	}
	public Map<String, Map<String, String>> dianZiShangWuReDian(String begintime, String endtime,
			String hbegintime, String hendtime, int i, HttpServletRequest req) {
		Map res=new HashMap<String,Map<String,String>>();
		List list=new ArrayList();
		List<Map> resList=new ArrayList<Map>();
		StringBuffer sb=new StringBuffer();
		String [] strs={
				"电子电器商品",
				"机械类商品",
				"烟酒饮料食品",
				"建材装饰商品",
				"珠宝首饰商品",
				"日用百货商品",
				"其他商品",
				"游戏娱乐服务",
				"中介服务",
				"物流快递服务",
				"旅游服务",
				"金融支付服务",
				"软件服务",
				"即时通信服务",
				"其他服务类"};
		sb.append(
				"with x as (\n" +
						"select m.WEBSITETYPE,\n" + 
						"(case when substr(o.invobjtype,0,3) in ('101','103') then '烟酒饮料食品'\n" + 
						"      when substr(o.invobjtype,0,3) ='141' then '建材装饰商品'\n" + 
						"      when substr(o.invobjtype,0,3) ='159' then '珠宝首饰商品'\n" + 
						"      when substr(o.invobjtype,0,3) in ('114','117','119','121','124','148','162') then '日用百货商品'\n" + 
						"      when substr(o.invobjtype,0,3) in ('127','131','134','145' ) then '电子电器商品'\n" + 
						"      when substr(o.invobjtype,0,3) in ('无对应代码') then '机械类商品'\n" + 
						"      when substr(o.invobjtype,0,3) ='无对应代码' then '游戏娱乐服务'\n" + 
						"      when substr(o.invobjtype,0,3)='260' then '中介服务'\n" + 
						"      when substr(o.invobjtype,0,3)='无对应代码' then '物流快递服务'\n" + 
						"      when substr(o.invobjtype,0,3)='无对应代码' then '旅游服务'\n" + 
						"      when substr(o.invobjtype,0,3)='263' then  '金融支付服务'\n" + 
						"      when substr(o.invobjtype,0,3)='无对应代码' then '软件服务'\n" + 
						"      when substr(o.invobjtype,0,3)='无对应代码'  then '即时通信服务'\n" + 
						"      else( case when substr(o.invobjtype,0,1) ='1' then '其他商品' else '其他服务类'end )end\n" + 
						") invobjtype,\n" + 
						"(case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) flag,\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) tflag,  --同比\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) hflag  --环比\n" + 
						"from dc_cpr_infoware i,dc_cpr_involved_main m,dc_cpr_involved_object o\n" + 
						"where m.invmaiid=i.invmaiid\n" + 
						"and o.invobjid=i.invobjid\n" + 
						"and m.EBUSINESS='1'  --非现场购物\n" + 
						"and i.inftype='1'    --投诉\n" + 
						"and o.businesstype='CH20'\n" + 
						")\n" + 
						"--select * from x\n" + 
						"select  x.invobjtype 项目,to_char(sum(case when x.WEBSITETYPE='01'and x.flag=1 then 1 else 0 end))交易平台类,\n" + 
						"to_char(sum(case when x.WEBSITETYPE='02' and x.flag=1 then 1 else 0 end))应用类,\n" + 
						"to_char(sum(case when x.WEBSITETYPE='03' and x.flag=1 then 1 else 0 end))服务类,\n" + 
						"to_char(sum(case when x.WEBSITETYPE='04' and x.flag=1 then 1 else 0 end))互联网门户,\n" + 
						"to_char(sum(case when (x.WEBSITETYPE='05'or  x.WEBSITETYPE not in ('01','02','03','04') ) and x.flag=1 then 1 else 0 end))其它,\n" + 
						"to_char(round(ratio_to_report(sum(x.flag)) over (),4)*100,'fm9999999990.099')||'%'  占比,\n" + 
						"to_char(sum(x.hflag)) 环比数,\n" + 
						"to_char(round((case when sum(x.hflag)=0 and sum(x.flag)=0 then 0\n" + 
						"  when sum(x.hflag)=0 and sum(x.flag)<>0 then 1\n" + 
						"  else(nvl(sum(x.flag),0)-sum(x.hflag))/sum(x.hflag) end ),4)*100,'fm9999999990.099')||'%' 环比,\n" + 
						"  to_char(sum(x.tflag)) 同比数\n" + 
						"from x group by x.invobjtype\n" + 
						"order by invobjtype");
		
		list.add(begintime);
		list.add(endtime+" 23:59:59");
		list.add(QueryJieAnUtils.getTongBiDate(begintime));
		list.add(QueryJieAnUtils.getTongBiDate(endtime)+" 23:59:59");
		if (hbegintime!=null&&hbegintime.length()!=0) {
			list.add(hbegintime);
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(begintime));
		}
		if (hendtime!=null&&hendtime.length()!=0) {
			list.add(hendtime+" 23:59:59");
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(endtime)+" 23:59:59");
		}
		try {
			resList=dao.queryForList(sb.toString(), list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		if (null==resList||resList.size()==0) {
			return null;
		}
		
		for (int j = 0,j1 = resList.size(); j <j1 ; j++) {
			Map map = resList.get(j);
			for (int k = 0; k < strs.length; k++) {
				if (strs[k].equals(map.get("项目"))) {
					res.put(strs[k], map);
				}
			}
		}
		for (int j = 0; j < strs.length; j++) {
			if (res.get(strs[j])==null) {
				Map map=new HashMap();
				map.put("项目",strs[j]);
				map.put("交易平台类", "0");
				map.put("应用类", "0");
				map.put("服务类", "0");
				map.put("互联网门户", "0");
				map.put("其它", "0");
				map.put("占比", "0%");
				map.put("环比数", "0");
				map.put("环比", "0%");
				map.put("同比数", "0");
				res.put(strs[j], map);
			}
		}
		logop.logInfoYeWu("电子商务投诉热点情况统计表", "WDY", i == 1 ? "查看报表" : "下载报表", sb.toString(),
				begintime+","+endtime, req);
		return res;
	}
	
	public List<List<Map>> dianZiShangWuTouSu(String begintime, String endtime,
			String hbegintime, String hendtime, int i, HttpServletRequest req) {
		List list=new ArrayList();
		List listDouble=new ArrayList();
		List<List<Map>> res=new ArrayList<List<Map>>();
		String gongShang=
				"\n" +
						"with x as (\n" + 
						"select m.REMSHOTYPE REMSHOTYPE,--decode(m.REMSHOTYPE,'1','电子商务','2','邮购','3','电话购物','4','电视购物',m.REMSHOTYPE) REMSHOTYPE,\n" + 
						"m.websitetype,--decode(m.websitetype,'01','交易平台类','02','应用类','03','服务类','04','互联网门户','05','其他',m.websitetype) websitetype,\n" + 
						"o.invobjtype,\n" + 
						"(case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) flag,\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) tflag,  --同比\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) hflag  --环比\n" + 
						"from dc_cpr_involved_main m,dc_cpr_involved_object o ,dc_cpr_infoware  i\n" + 
						"where m.invmaiid(+)=i.invmaiid\n" + 
						"and   o.invobjid=i.invobjid\n" + 
						"and  m.EBUSINESS='1'\n" + 
						"and i.inftype='1'\n" + 
						"and o.businesstype='CH20'\n" + 
						"and o.invobjtype is not null\n" + 
						"),\n" + 
						"x1 as (\n" + 
						"select j.name,t.* from (\n" + 
						"select distinct t.* from (\n" + 
						"select '00工商' as code,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='1' then 1 else 0 end )  as 电子商务,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='2' then 1 else 0 end )  as 邮购,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='3' then 1 else 0 end )  as 电话购物,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='4' then 1 else 0 end )  as 电视购物,\n" + 
						"sum(case when x.flag=1 and x.websitetype='01' then 1 else 0 end ) as 交易平台类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='02' then 1 else 0 end ) as 应用类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='03' then 1 else 0 end ) as 服务类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='04' then 1 else 0 end ) as 互联网门户,\n" + 
						"sum(case when x.flag=1 and x.websitetype='05' then 1 else 0 end ) as 其他，\n" + 
						"ratio_to_report(sum(x.flag)) over () 占比,\n" + 
						"sum(x.hflag) 环比数,\n" + 
						"(case when sum(x.hflag)=0 and sum(x.flag)=0 then 0\n" + 
						"      when sum(x.hflag)=0 and sum(x.flag)<>0 then 1\n" + 
						"      else(nvl(sum(x.flag),0)-sum(x.hflag))/sum(x.hflag) end ) 环比,\n" + 
						"sum(x.tflag) 同比数\n" + 
						"from  x\n" + 
						"union all\n" + 
						"select x.invobjtype as code,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='1' then 1 else 0 end )  as 电子商务,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='2' then 1 else 0 end )  as 邮购,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='3' then 1 else 0 end )  as 电话购物,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='4' then 1 else 0 end )  as 电视购物,\n" + 
						"sum(case when x.flag=1 and x.websitetype='01' then 1 else 0 end ) as 交易平台类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='02' then 1 else 0 end ) as 应用类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='03' then 1 else 0 end ) as 服务类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='04' then 1 else 0 end ) as 互联网门户,\n" + 
						"sum(case when x.flag=1 and x.websitetype='05' then 1 else 0 end ) as 其他，\n" + 
						"ratio_to_report(sum(x.flag)) over () 占比,\n" + 
						"sum(x.hflag) 环比数,\n" + 
						"(case when sum(x.hflag)=0 and sum(x.flag)=0 then 0\n" + 
						"      when sum(x.hflag)=0 and sum(x.flag)<>0 then 1\n" + 
						"      else(nvl(sum(x.flag),0)-sum(x.hflag))/sum(x.hflag) end ) 环比,\n" + 
						"sum(x.tflag) 同比数\n" + 
						"from  x  group by x.invobjtype\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,1)||'0000000' as code,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='1' then 1 else 0 end )  as 电子商务,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='2' then 1 else 0 end )  as 邮购,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='3' then 1 else 0 end )  as 电话购物,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='4' then 1 else 0 end )  as 电视购物,\n" + 
						"sum(case when x.flag=1 and x.websitetype='01' then 1 else 0 end ) as 交易平台类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='02' then 1 else 0 end ) as 应用类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='03' then 1 else 0 end ) as 服务类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='04' then 1 else 0 end ) as 互联网门户,\n" + 
						"sum(case when x.flag=1 and x.websitetype='05' then 1 else 0 end ) as 其他，\n" + 
						"ratio_to_report(sum(x.flag)) over () 占比,\n" + 
						"sum(x.hflag) 环比数,\n" + 
						"(case when sum(x.hflag)=0 and sum(x.flag)=0 then 0\n" + 
						"      when sum(x.hflag)=0 and sum(x.flag)<>0 then 1\n" + 
						"      else(nvl(sum(x.flag),0)-sum(x.hflag))/sum(x.hflag) end ) 环比,\n" + 
						"sum(x.tflag) 同比数\n" + 
						"from  x  group by substr(x.invobjtype,0,1)||'0000000'\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,3)||'00000' as code,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='1' then 1 else 0 end )  as 电子商务,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='2' then 1 else 0 end )  as 邮购,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='3' then 1 else 0 end )  as 电话购物,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='4' then 1 else 0 end )  as 电视购物,\n" + 
						"sum(case when x.flag=1 and x.websitetype='01' then 1 else 0 end ) as 交易平台类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='02' then 1 else 0 end ) as 应用类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='03' then 1 else 0 end ) as 服务类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='04' then 1 else 0 end ) as 互联网门户,\n" + 
						"sum(case when x.flag=1 and x.websitetype='05' then 1 else 0 end ) as 其他，\n" + 
						"ratio_to_report(sum(x.flag)) over () 占比,\n" + 
						"sum(x.hflag) 环比数,\n" + 
						"(case when sum(x.hflag)=0 and sum(x.flag)=0 then 0\n" + 
						"      when sum(x.hflag)=0 and sum(x.flag)<>0 then 1\n" + 
						"      else(nvl(sum(x.flag),0)-sum(x.hflag))/sum(x.hflag) end ) 环比,\n" + 
						"sum(x.tflag) 同比数\n" + 
						"from  x  group by substr(x.invobjtype,0,3)||'00000'\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,5)||'000' as code,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='1' then 1 else 0 end )  as 电子商务,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='2' then 1 else 0 end )  as 邮购,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='3' then 1 else 0 end )  as 电话购物,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='4' then 1 else 0 end )  as 电视购物,\n" + 
						"sum(case when x.flag=1 and x.websitetype='01' then 1 else 0 end ) as 交易平台类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='02' then 1 else 0 end ) as 应用类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='03' then 1 else 0 end ) as 服务类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='04' then 1 else 0 end ) as 互联网门户,\n" + 
						"sum(case when x.flag=1 and x.websitetype='05' then 1 else 0 end ) as 其他，\n" + 
						"ratio_to_report(sum(x.flag)) over () 占比,\n" + 
						"sum(x.hflag) 环比数,\n" + 
						"(case when sum(x.hflag)=0 and sum(x.flag)=0 then 0\n" + 
						"      when sum(x.hflag)=0 and sum(x.flag)<>0 then 1\n" + 
						"      else(nvl(sum(x.flag),0)-sum(x.hflag))/sum(x.hflag) end ) 环比,\n" + 
						"sum(x.tflag) 同比数\n" + 
						"from  x  group by substr(x.invobjtype,0,5)||'000'\n" + 
						") t)t,\n" + 
						"(SELECT  CODE,  substr('　　　　　',0,level)||NAME AS NAME\n" + 
						"    FROM (SELECT * FROM DC_CODE.DC_12315_CODEDATA WHERE CODETABLE = 'CH20')\n" + 
						"   START WITH PARENTCODE = '0'\n" + 
						"  CONNECT BY PRIOR CODE = PARENTCODE\n" + 
						"  union all\n" + 
						"   select '00工商' code,'工商' name from dual\n" + 
						"    union all\n" + 
						"   select '2000000' code,'　服务' name from dual\n" + 
						"    union all\n" + 
						"   select '1000000' code,'　商品' name from dual)j\n" + 
						"   where t.code=j.code(+)\n" + 
						")\n" + 
						"select x1.name,x1.code,to_char(x1.电子商务) 电子商务,\n" + 
						"to_char(x1.邮购) 邮购,\n" + 
						"to_char(x1.电话购物) 电话购物 ,\n" + 
						"to_char(x1.电视购物) 电视购物,\n" + 
						"to_char(x1.交易平台类) 交易平台类,\n" + 
						"to_char(x1.应用类) 应用类,\n" + 
						"to_char(x1.服务类) 服务类,\n" + 
						"to_char(x1.互联网门户) 互联网门户,\n" + 
						"to_char(x1.其他) 其他,\n" + 
						"to_char(round(x1.占比,4)*100,'fm9999999990.099')||'%' 占比,\n" + 
						"to_char(x1.环比数) 环比数,\n" + 
						"to_char(round(x1.环比,4)*100,'fm9999999990.099')||'%' 环比,\n" + 
						"to_char(x1.同比数) 同比数\n" + 
						"from x1\n" + 
						" order by code";
		String xiaoWeiHui=
				"\n" +
						"with x as (\n" + 
						"select m.REMSHOTYPE REMSHOTYPE,--decode(m.REMSHOTYPE,'1','电子商务','2','邮购','3','电话购物','4','电视购物',m.REMSHOTYPE) REMSHOTYPE,\n" + 
						"m.websitetype,--decode(m.websitetype,'01','交易平台类','02','应用类','03','服务类','04','互联网门户','05','其他',m.websitetype) websitetype,\n" + 
						"o.invobjtype,\n" + 
						"(case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) flag,\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) tflag,  --同比\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) hflag  --环比\n" + 
						"from dc_cpr_involved_main m,dc_cpr_involved_object o ,dc_cpr_infoware  i\n" + 
						"where m.invmaiid(+)=i.invmaiid\n" + 
						"and   o.invobjid=i.invobjid\n" + 
						"and  m.EBUSINESS='1'\n" + 
						"and i.inftype='1'\n" + 
						"and o.businesstype='ZH18'\n" + 
						"and o.invobjtype is not null\n" + 
						"),\n" + 
						"x1 as (\n" + 
						"select j.name,t.* from (\n" + 
						"select distinct t.* from (\n" + 
						"select '00消委会' as code,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='1' then 1 else 0 end )  as 电子商务,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='2' then 1 else 0 end )  as 邮购,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='3' then 1 else 0 end )  as 电话购物,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='4' then 1 else 0 end )  as 电视购物,\n" + 
						"sum(case when x.flag=1 and x.websitetype='01' then 1 else 0 end ) as 交易平台类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='02' then 1 else 0 end ) as 应用类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='03' then 1 else 0 end ) as 服务类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='04' then 1 else 0 end ) as 互联网门户,\n" + 
						"sum(case when x.flag=1 and x.websitetype='05' then 1 else 0 end ) as 其他，\n" + 
						"ratio_to_report(sum(x.flag)) over () 占比,\n" + 
						"sum(x.hflag) 环比数,\n" + 
						"(case when sum(x.hflag)=0 and sum(x.flag)=0 then 0\n" + 
						"      when sum(x.hflag)=0 and sum(x.flag)<>0 then 1\n" + 
						"      else(nvl(sum(x.flag),0)-sum(x.hflag))/sum(x.hflag) end ) 环比,\n" + 
						"sum(x.tflag) 同比数\n" + 
						"from  x\n" + 
						"union all\n" + 
						"select x.invobjtype as code,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='1' then 1 else 0 end )  as 电子商务,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='2' then 1 else 0 end )  as 邮购,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='3' then 1 else 0 end )  as 电话购物,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='4' then 1 else 0 end )  as 电视购物,\n" + 
						"sum(case when x.flag=1 and x.websitetype='01' then 1 else 0 end ) as 交易平台类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='02' then 1 else 0 end ) as 应用类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='03' then 1 else 0 end ) as 服务类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='04' then 1 else 0 end ) as 互联网门户,\n" + 
						"sum(case when x.flag=1 and x.websitetype='05' then 1 else 0 end ) as 其他，\n" + 
						"ratio_to_report(sum(x.flag)) over () 占比,\n" + 
						"sum(x.hflag) 环比数,\n" + 
						"(case when sum(x.hflag)=0 and sum(x.flag)=0 then 0\n" + 
						"      when sum(x.hflag)=0 and sum(x.flag)<>0 then 1\n" + 
						"      else(nvl(sum(x.flag),0)-sum(x.hflag))/sum(x.hflag) end ) 环比,\n" + 
						"sum(x.tflag) 同比数\n" + 
						"from  x  group by x.invobjtype\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,2)||'00000000' as code,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='1' then 1 else 0 end )  as 电子商务,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='2' then 1 else 0 end )  as 邮购,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='3' then 1 else 0 end )  as 电话购物,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='4' then 1 else 0 end )  as 电视购物,\n" + 
						"sum(case when x.flag=1 and x.websitetype='01' then 1 else 0 end ) as 交易平台类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='02' then 1 else 0 end ) as 应用类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='03' then 1 else 0 end ) as 服务类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='04' then 1 else 0 end ) as 互联网门户,\n" + 
						"sum(case when x.flag=1 and x.websitetype='05' then 1 else 0 end ) as 其他，\n" + 
						"ratio_to_report(sum(x.flag)) over () 占比,\n" + 
						"sum(x.hflag) 环比数,\n" + 
						"(case when sum(x.hflag)=0 and sum(x.flag)=0 then 0\n" + 
						"      when sum(x.hflag)=0 and sum(x.flag)<>0 then 1\n" + 
						"      else(nvl(sum(x.flag),0)-sum(x.hflag))/sum(x.hflag) end ) 环比,\n" + 
						"sum(x.tflag) 同比数\n" + 
						"from  x  group by substr(x.invobjtype,0,2)||'00000000'\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,4)||'000000' as code,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='1' then 1 else 0 end )  as 电子商务,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='2' then 1 else 0 end )  as 邮购,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='3' then 1 else 0 end )  as 电话购物,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='4' then 1 else 0 end )  as 电视购物,\n" + 
						"sum(case when x.flag=1 and x.websitetype='01' then 1 else 0 end ) as 交易平台类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='02' then 1 else 0 end ) as 应用类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='03' then 1 else 0 end ) as 服务类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='04' then 1 else 0 end ) as 互联网门户,\n" + 
						"sum(case when x.flag=1 and x.websitetype='05' then 1 else 0 end ) as 其他，\n" + 
						"ratio_to_report(sum(x.flag)) over () 占比,\n" + 
						"sum(x.hflag) 环比数,\n" + 
						"(case when sum(x.hflag)=0 and sum(x.flag)=0 then 0\n" + 
						"      when sum(x.hflag)=0 and sum(x.flag)<>0 then 1\n" + 
						"      else(nvl(sum(x.flag),0)-sum(x.hflag))/sum(x.hflag) end ) 环比,\n" + 
						"sum(x.tflag) 同比数\n" + 
						"from  x  group by substr(x.invobjtype,0,4)||'000000'\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,6)||'0000' as code,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='1' then 1 else 0 end )  as 电子商务,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='2' then 1 else 0 end )  as 邮购,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='3' then 1 else 0 end )  as 电话购物,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='4' then 1 else 0 end )  as 电视购物,\n" + 
						"sum(case when x.flag=1 and x.websitetype='01' then 1 else 0 end ) as 交易平台类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='02' then 1 else 0 end ) as 应用类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='03' then 1 else 0 end ) as 服务类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='04' then 1 else 0 end ) as 互联网门户,\n" + 
						"sum(case when x.flag=1 and x.websitetype='05' then 1 else 0 end ) as 其他，\n" + 
						"ratio_to_report(sum(x.flag)) over () 占比,\n" + 
						"sum(x.hflag) 环比数,\n" + 
						"(case when sum(x.hflag)=0 and sum(x.flag)=0 then 0\n" + 
						"      when sum(x.hflag)=0 and sum(x.flag)<>0 then 1\n" + 
						"      else(nvl(sum(x.flag),0)-sum(x.hflag))/sum(x.hflag) end ) 环比,\n" + 
						"sum(x.tflag) 同比数\n" + 
						"from  x  group by substr(x.invobjtype,0,6)||'0000'\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,8)||'00' as code,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='1' then 1 else 0 end )  as 电子商务,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='2' then 1 else 0 end )  as 邮购,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='3' then 1 else 0 end )  as 电话购物,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='4' then 1 else 0 end )  as 电视购物,\n" + 
						"sum(case when x.flag=1 and x.websitetype='01' then 1 else 0 end ) as 交易平台类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='02' then 1 else 0 end ) as 应用类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='03' then 1 else 0 end ) as 服务类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='04' then 1 else 0 end ) as 互联网门户,\n" + 
						"sum(case when x.flag=1 and x.websitetype='05' then 1 else 0 end ) as 其他，\n" + 
						"ratio_to_report(sum(x.flag)) over () 占比,\n" + 
						"sum(x.hflag) 环比数,\n" + 
						"(case when sum(x.hflag)=0 and sum(x.flag)=0 then 0\n" + 
						"      when sum(x.hflag)=0 and sum(x.flag)<>0 then 1\n" + 
						"      else(nvl(sum(x.flag),0)-sum(x.hflag))/sum(x.hflag) end ) 环比,\n" + 
						"sum(x.tflag) 同比数\n" + 
						"from  x  group by substr(x.invobjtype,0,8)||'00'\n" + 
						") t)t,\n" + 
						"(SELECT  CODE,  substr('　　　　　',0,level)||NAME AS NAME\n" + 
						"    FROM (SELECT * FROM DC_CODE.DC_12315_CODEDATA WHERE CODETABLE = 'ZH18')\n" + 
						"   START WITH PARENTCODE = '0'\n" + 
						"  CONNECT BY PRIOR CODE = PARENTCODE\n" + 
						"  union all\n" + 
						"   select '00消委会' code,'消委会' name from dual)j\n" + 
						"   where t.code=j.code(+)\n" + 
						")\n" + 
						"select x1.name,x1.code,to_char(x1.电子商务) 电子商务,\n" + 
						"to_char(x1.邮购) 邮购,\n" + 
						"to_char(x1.电话购物) 电话购物 ,\n" + 
						"to_char(x1.电视购物) 电视购物,\n" + 
						"to_char(x1.交易平台类) 交易平台类,\n" + 
						"to_char(x1.应用类) 应用类,\n" + 
						"to_char(x1.服务类) 服务类,\n" + 
						"to_char(x1.互联网门户) 互联网门户,\n" + 
						"to_char(x1.其他) 其他,\n" + 
						"to_char(round(x1.占比,4)*100,'fm9999999990.099')||'%' 占比,\n" + 
						"to_char(x1.环比数) 环比数,\n" + 
						"to_char(round(x1.环比,4)*100,'fm9999999990.099')||'%' 环比,\n" + 
						"to_char(x1.同比数) 同比数\n" + 
						"from x1\n" + 
						" order by code";
		String zhiShiChanQuan=
				"\n" +
						"with x as (\n" + 
						"select m.REMSHOTYPE REMSHOTYPE,--decode(m.REMSHOTYPE,'1','电子商务','2','邮购','3','电话购物','4','电视购物',m.REMSHOTYPE) REMSHOTYPE,\n" + 
						"m.websitetype,--decode(m.websitetype,'01','交易平台类','02','应用类','03','服务类','04','互联网门户','05','其他',m.websitetype) websitetype,\n" + 
						"o.invobjtype,\n" + 
						"(case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) flag,\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) tflag,  --同比\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) hflag  --环比\n" + 
						"from dc_cpr_involved_main m,dc_cpr_involved_object o ,dc_cpr_infoware  i\n" + 
						"where m.invmaiid(+)=i.invmaiid\n" + 
						"and   o.invobjid=i.invobjid\n" + 
						"and i.inftype='1'\n" + 
						"and  m.EBUSINESS='1'\n" + 
						"and o.businesstype='ZH19'\n" + 
						"and o.invobjtype is not null\n" + 
						"),\n" + 
						"x1 as (\n" + 
						"select j.name,t.* from (\n" + 
						"select distinct t.* from (\n" + 
						"select '00知识产权' as code,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='1' then 1 else 0 end )  as 电子商务,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='2' then 1 else 0 end )  as 邮购,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='3' then 1 else 0 end )  as 电话购物,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='4' then 1 else 0 end )  as 电视购物,\n" + 
						"sum(case when x.flag=1 and x.websitetype='01' then 1 else 0 end ) as 交易平台类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='02' then 1 else 0 end ) as 应用类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='03' then 1 else 0 end ) as 服务类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='04' then 1 else 0 end ) as 互联网门户,\n" + 
						"sum(case when x.flag=1 and x.websitetype='05' then 1 else 0 end ) as 其他，\n" + 
						"ratio_to_report(sum(x.flag)) over () 占比,\n" + 
						"sum(x.hflag) 环比数,\n" + 
						"(case when sum(x.hflag)=0 and sum(x.flag)=0 then 0\n" + 
						"      when sum(x.hflag)=0 and sum(x.flag)<>0 then 1\n" + 
						"      else(nvl(sum(x.flag),0)-sum(x.hflag))/sum(x.hflag) end ) 环比,\n" + 
						"sum(x.tflag) 同比数\n" + 
						"from  x\n" + 
						"union all\n" + 
						"select x.invobjtype as code,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='1' then 1 else 0 end )  as 电子商务,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='2' then 1 else 0 end )  as 邮购,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='3' then 1 else 0 end )  as 电话购物,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='4' then 1 else 0 end )  as 电视购物,\n" + 
						"sum(case when x.flag=1 and x.websitetype='01' then 1 else 0 end ) as 交易平台类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='02' then 1 else 0 end ) as 应用类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='03' then 1 else 0 end ) as 服务类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='04' then 1 else 0 end ) as 互联网门户,\n" + 
						"sum(case when x.flag=1 and x.websitetype='05' then 1 else 0 end ) as 其他，\n" + 
						"ratio_to_report(sum(x.flag)) over () 占比,\n" + 
						"sum(x.hflag) 环比数,\n" + 
						"(case when sum(x.hflag)=0 and sum(x.flag)=0 then 0\n" + 
						"      when sum(x.hflag)=0 and sum(x.flag)<>0 then 1\n" + 
						"      else(nvl(sum(x.flag),0)-sum(x.hflag))/sum(x.hflag) end ) 环比,\n" + 
						"sum(x.tflag) 同比数\n" + 
						"from  x  group by x.invobjtype\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,1)||'00' as code,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='1' then 1 else 0 end )  as 电子商务,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='2' then 1 else 0 end )  as 邮购,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='3' then 1 else 0 end )  as 电话购物,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='4' then 1 else 0 end )  as 电视购物,\n" + 
						"sum(case when x.flag=1 and x.websitetype='01' then 1 else 0 end ) as 交易平台类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='02' then 1 else 0 end ) as 应用类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='03' then 1 else 0 end ) as 服务类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='04' then 1 else 0 end ) as 互联网门户,\n" + 
						"sum(case when x.flag=1 and x.websitetype='05' then 1 else 0 end ) as 其他，\n" + 
						"ratio_to_report(sum(x.flag)) over () 占比,\n" + 
						"sum(x.hflag) 环比数,\n" + 
						"(case when sum(x.hflag)=0 and sum(x.flag)=0 then 0\n" + 
						"      when sum(x.hflag)=0 and sum(x.flag)<>0 then 1\n" + 
						"      else(nvl(sum(x.flag),0)-sum(x.hflag))/sum(x.hflag) end ) 环比,\n" + 
						"sum(x.tflag) 同比数\n" + 
						"from  x  group by substr(x.invobjtype,0,1)||'00'\n" + 
						") t)t,\n" + 
						"(SELECT  CODE,  substr('　　　　　',0,level)||NAME AS NAME\n" + 
						"    FROM (SELECT * FROM DC_CODE.DC_12315_CODEDATA WHERE CODETABLE = 'ZH19')\n" + 
						"   START WITH PARENTCODE = '0'\n" + 
						"  CONNECT BY PRIOR CODE = PARENTCODE\n" + 
						"  union all\n" + 
						"   select '00知识产权' code,'知识产权' name from dual)j\n" + 
						"   where t.code=j.code(+)\n" + 
						")\n" + 
						"select nvl(x1.name,'　'||x1.code) as name ,x1.code,to_char(x1.电子商务) 电子商务,\n" + 
						"to_char(x1.邮购) 邮购,\n" + 
						"to_char(x1.电话购物) 电话购物 ,\n" + 
						"to_char(x1.电视购物) 电视购物,\n" + 
						"to_char(x1.交易平台类) 交易平台类,\n" + 
						"to_char(x1.应用类) 应用类,\n" + 
						"to_char(x1.服务类) 服务类,\n" + 
						"to_char(x1.互联网门户) 互联网门户,\n" + 
						"to_char(x1.其他) 其他,\n" + 
						"to_char(round(x1.占比,4)*100,'fm9999999990.099')||'%' 占比,\n" + 
						"to_char(x1.环比数) 环比数,\n" + 
						"to_char(round(x1.环比,4)*100,'fm9999999990.099')||'%' 环比,\n" + 
						"to_char(x1.同比数) 同比数\n" + 
						"from x1\n" + 
						" order by code";
		String zhiLiangJianDu=
				"with x as (\n" +
						"select m.REMSHOTYPE REMSHOTYPE,--decode(m.REMSHOTYPE,'1','电子商务','2','邮购','3','电话购物','4','电视购物',m.REMSHOTYPE) REMSHOTYPE,\n" + 
						"m.websitetype,--decode(m.websitetype,'01','交易平台类','02','应用类','03','服务类','04','互联网门户','05','其他',m.websitetype) websitetype,\n" + 
						"o.invobjtype,\n" + 
						"(case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) flag,\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) tflag,  --同比\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) hflag  --环比\n" + 
						"from dc_cpr_involved_main m,dc_cpr_involved_object o ,dc_cpr_infoware  i\n" + 
						"where m.invmaiid(+)=i.invmaiid\n" + 
						"and   o.invobjid=i.invobjid\n" + 
						"and i.inftype='1'\n" + 
						"and  m.EBUSINESS='1'\n" + 
						"and o.businesstype='ZH21'\n" + 
						"and o.invobjtype is not null\n" + 
						"),\n" + 
						"x1 as (\n" + 
						"select j.name,t.* from (\n" + 
						"select distinct t.* from (\n" + 
						"select '00质量监督' as code,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='1' then 1 else 0 end )  as 电子商务,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='2' then 1 else 0 end )  as 邮购,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='3' then 1 else 0 end )  as 电话购物,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='4' then 1 else 0 end )  as 电视购物,\n" + 
						"sum(case when x.flag=1 and x.websitetype='01' then 1 else 0 end ) as 交易平台类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='02' then 1 else 0 end ) as 应用类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='03' then 1 else 0 end ) as 服务类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='04' then 1 else 0 end ) as 互联网门户,\n" + 
						"sum(case when x.flag=1 and x.websitetype='05' then 1 else 0 end ) as 其他，\n" + 
						"ratio_to_report(sum(x.flag)) over () 占比,\n" + 
						"sum(x.hflag) 环比数,\n" + 
						"(case when sum(x.hflag)=0 and sum(x.flag)=0 then 0\n" + 
						"      when sum(x.hflag)=0 and sum(x.flag)<>0 then 1\n" + 
						"      else(nvl(sum(x.flag),0)-sum(x.hflag))/sum(x.hflag) end ) 环比,\n" + 
						"sum(x.tflag) 同比数\n" + 
						"from  x\n" + 
						"union all\n" + 
						"select x.invobjtype as code,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='1' then 1 else 0 end )  as 电子商务,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='2' then 1 else 0 end )  as 邮购,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='3' then 1 else 0 end )  as 电话购物,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='4' then 1 else 0 end )  as 电视购物,\n" + 
						"sum(case when x.flag=1 and x.websitetype='01' then 1 else 0 end ) as 交易平台类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='02' then 1 else 0 end ) as 应用类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='03' then 1 else 0 end ) as 服务类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='04' then 1 else 0 end ) as 互联网门户,\n" + 
						"sum(case when x.flag=1 and x.websitetype='05' then 1 else 0 end ) as 其他，\n" + 
						"ratio_to_report(sum(x.flag)) over () 占比,\n" + 
						"sum(x.hflag) 环比数,\n" + 
						"(case when sum(x.hflag)=0 and sum(x.flag)=0 then 0\n" + 
						"      when sum(x.hflag)=0 and sum(x.flag)<>0 then 1\n" + 
						"      else(nvl(sum(x.flag),0)-sum(x.hflag))/sum(x.hflag) end ) 环比,\n" + 
						"sum(x.tflag) 同比数\n" + 
						"from  x  group by x.invobjtype\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,2)||'00000000' as code,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='1' then 1 else 0 end )  as 电子商务,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='2' then 1 else 0 end )  as 邮购,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='3' then 1 else 0 end )  as 电话购物,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='4' then 1 else 0 end )  as 电视购物,\n" + 
						"sum(case when x.flag=1 and x.websitetype='01' then 1 else 0 end ) as 交易平台类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='02' then 1 else 0 end ) as 应用类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='03' then 1 else 0 end ) as 服务类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='04' then 1 else 0 end ) as 互联网门户,\n" + 
						"sum(case when x.flag=1 and x.websitetype='05' then 1 else 0 end ) as 其他，\n" + 
						"ratio_to_report(sum(x.flag)) over () 占比,\n" + 
						"sum(x.hflag) 环比数,\n" + 
						"(case when sum(x.hflag)=0 and sum(x.flag)=0 then 0\n" + 
						"      when sum(x.hflag)=0 and sum(x.flag)<>0 then 1\n" + 
						"      else(nvl(sum(x.flag),0)-sum(x.hflag))/sum(x.hflag) end ) 环比,\n" + 
						"sum(x.tflag) 同比数\n" + 
						"from  x  group by substr(x.invobjtype,0,2)||'00000000'\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,4)||'000000' as code,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='1' then 1 else 0 end )  as 电子商务,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='2' then 1 else 0 end )  as 邮购,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='3' then 1 else 0 end )  as 电话购物,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='4' then 1 else 0 end )  as 电视购物,\n" + 
						"sum(case when x.flag=1 and x.websitetype='01' then 1 else 0 end ) as 交易平台类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='02' then 1 else 0 end ) as 应用类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='03' then 1 else 0 end ) as 服务类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='04' then 1 else 0 end ) as 互联网门户,\n" + 
						"sum(case when x.flag=1 and x.websitetype='05' then 1 else 0 end ) as 其他，\n" + 
						"ratio_to_report(sum(x.flag)) over () 占比,\n" + 
						"sum(x.hflag) 环比数,\n" + 
						"(case when sum(x.hflag)=0 and sum(x.flag)=0 then 0\n" + 
						"      when sum(x.hflag)=0 and sum(x.flag)<>0 then 1\n" + 
						"      else(nvl(sum(x.flag),0)-sum(x.hflag))/sum(x.hflag) end ) 环比,\n" + 
						"sum(x.tflag) 同比数\n" + 
						"from  x  group by substr(x.invobjtype,0,4)||'000000'\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,6)||'0000' as code,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='1' then 1 else 0 end )  as 电子商务,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='2' then 1 else 0 end )  as 邮购,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='3' then 1 else 0 end )  as 电话购物,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='4' then 1 else 0 end )  as 电视购物,\n" + 
						"sum(case when x.flag=1 and x.websitetype='01' then 1 else 0 end ) as 交易平台类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='02' then 1 else 0 end ) as 应用类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='03' then 1 else 0 end ) as 服务类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='04' then 1 else 0 end ) as 互联网门户,\n" + 
						"sum(case when x.flag=1 and x.websitetype='05' then 1 else 0 end ) as 其他，\n" + 
						"ratio_to_report(sum(x.flag)) over () 占比,\n" + 
						"sum(x.hflag) 环比数,\n" + 
						"(case when sum(x.hflag)=0 and sum(x.flag)=0 then 0\n" + 
						"      when sum(x.hflag)=0 and sum(x.flag)<>0 then 1\n" + 
						"      else(nvl(sum(x.flag),0)-sum(x.hflag))/sum(x.hflag) end ) 环比,\n" + 
						"sum(x.tflag) 同比数\n" + 
						"from  x  group by substr(x.invobjtype,0,6)||'0000'\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,8)||'00' as code,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='1' then 1 else 0 end )  as 电子商务,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='2' then 1 else 0 end )  as 邮购,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='3' then 1 else 0 end )  as 电话购物,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='4' then 1 else 0 end )  as 电视购物,\n" + 
						"sum(case when x.flag=1 and x.websitetype='01' then 1 else 0 end ) as 交易平台类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='02' then 1 else 0 end ) as 应用类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='03' then 1 else 0 end ) as 服务类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='04' then 1 else 0 end ) as 互联网门户,\n" + 
						"sum(case when x.flag=1 and x.websitetype='05' then 1 else 0 end ) as 其他，\n" + 
						"ratio_to_report(sum(x.flag)) over () 占比,\n" + 
						"sum(x.hflag) 环比数,\n" + 
						"(case when sum(x.hflag)=0 and sum(x.flag)=0 then 0\n" + 
						"      when sum(x.hflag)=0 and sum(x.flag)<>0 then 1\n" + 
						"      else(nvl(sum(x.flag),0)-sum(x.hflag))/sum(x.hflag) end ) 环比,\n" + 
						"sum(x.tflag) 同比数\n" + 
						"from  x  group by substr(x.invobjtype,0,8)||'00'\n" + 
						") t)t,\n" + 
						"(SELECT  CODE,  substr('　　　　　',0,level)||NAME AS NAME\n" + 
						"    FROM (SELECT * FROM DC_CODE.DC_12315_CODEDATA WHERE CODETABLE = 'ZH21')\n" + 
						"   START WITH PARENTCODE = '0'\n" + 
						"  CONNECT BY PRIOR CODE = PARENTCODE\n" + 
						"  union all\n" + 
						"   select '00质量监督' code,'质量监督' name from dual)j\n" + 
						"   where t.code=j.code(+)\n" + 
						")\n" + 
						"select x1.name,x1.code,to_char(x1.电子商务) 电子商务,\n" + 
						"to_char(x1.邮购) 邮购,\n" + 
						"to_char(x1.电话购物) 电话购物 ,\n" + 
						"to_char(x1.电视购物) 电视购物,\n" + 
						"to_char(x1.交易平台类) 交易平台类,\n" + 
						"to_char(x1.应用类) 应用类,\n" + 
						"to_char(x1.服务类) 服务类,\n" + 
						"to_char(x1.互联网门户) 互联网门户,\n" + 
						"to_char(x1.其他) 其他,\n" + 
						"to_char(round(x1.占比,4)*100,'fm9999999990.099')||'%' 占比,\n" + 
						"to_char(x1.环比数) 环比数,\n" + 
						"to_char(round(x1.环比,4)*100,'fm9999999990.099')||'%' 环比,\n" + 
						"to_char(x1.同比数) 同比数\n" + 
						"from x1\n" + 
						" order by code";
		String jiaGeJianCha=
				"select x1.name,x1.code,to_char(x1.电子商务) 电子商务,\n" +
						"to_char(x1.邮购) 邮购,\n" + 
						"to_char(x1.电话购物) 电话购物 ,\n" + 
						"to_char(x1.电视购物) 电视购物,\n" + 
						"to_char(x1.交易平台类) 交易平台类,\n" + 
						"to_char(x1.应用类) 应用类,\n" + 
						"to_char(x1.服务类) 服务类,\n" + 
						"to_char(x1.互联网门户) 互联网门户,\n" + 
						"to_char(x1.其他) 其他,\n" + 
						"to_char(round(x1.占比,4)*100,'fm9999999990.099')||'%' 占比,\n" + 
						"to_char(x1.环比数) 环比数,\n" + 
						"to_char(round(x1.环比,4)*100,'fm9999999990.099')||'%' 环比,\n" + 
						"to_char(x1.同比数) 同比数\n" + 
						"from(\n" + 
						"with x as (\n" + 
						"select m.REMSHOTYPE REMSHOTYPE,--decode(m.REMSHOTYPE,'1','电子商务','2','邮购','3','电话购物','4','电视购物',m.REMSHOTYPE) REMSHOTYPE,\n" + 
						"m.websitetype,--decode(m.websitetype,'01','交易平台类','02','应用类','03','服务类','04','互联网门户','05','其他',m.websitetype) websitetype,\n" + 
						"substr(o.invobjtype,0,2)||'00' invobjtype,'　'||j.name as name,\n" + 
						"(case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) flag,\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) tflag,  --同比\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) hflag  --环比\n" + 
						"from dc_cpr_involved_main m,dc_cpr_involved_object o ,dc_cpr_infoware  i,dc_code.dc_12315_codedata j\n" + 
						"where m.invmaiid(+)=i.invmaiid\n" + 
						"and o.invobjid=i.invobjid\n" + 
						"and i.inftype='1'\n" + 
						"and j.codetable='ZH20'\n" + 
						"and substr(o.invobjtype,0,2)||'00'=j.code(+)\n" + 
						"and  m.EBUSINESS='1'\n" + 
						"and o.businesstype='ZH20'\n" + 
						"and o.invobjtype is not null\n" + 
						"union all\n" + 
						"select m.REMSHOTYPE REMSHOTYPE,--decode(m.REMSHOTYPE,'1','电子商务','2','邮购','3','电话购物','4','电视购物',m.REMSHOTYPE) REMSHOTYPE,\n" + 
						"m.websitetype,--decode(m.websitetype,'01','交易平台类','02','应用类','03','服务类','04','互联网门户','05','其他',m.websitetype) websitetype,\n" + 
						"o.invobjtype as invobjtype,'　　'||j.name as name,\n" + 
						"(case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) flag,\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) tflag,  --同比\n" + 
						"  (case when i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"  and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1 else 0 end) hflag  --环比\n" + 
						"from dc_cpr_involved_main m,dc_cpr_involved_object o ,dc_cpr_infoware  i,dc_code.dc_12315_codedata j\n" + 
						"where m.invmaiid(+)=i.invmaiid\n" + 
						"and o.invobjid=i.invobjid\n" + 
						"and i.inftype='1'\n" + 
						"and j.codetable='ZH20'\n" + 
						"and o.invobjtype=j.code(+)\n" + 
						"and  m.EBUSINESS='1'\n" + 
						"and o.businesstype='ZH20'\n" + 
						"and o.invobjtype is not null\n" + 
						"\n" + 
						")\n" + 
						"select '价格检查' as name,'0000' as code,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='1' then 1 else 0 end )/2  as 电子商务,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='2' then 1 else 0 end )/2  as 邮购,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='3' then 1 else 0 end )/2  as 电话购物,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='4' then 1 else 0 end )/2  as 电视购物,\n" + 
						"sum(case when x.flag=1 and x.websitetype='01' then 1 else 0 end )/2 as 交易平台类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='02' then 1 else 0 end )/2 as 应用类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='03' then 1 else 0 end )/2 as 服务类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='04' then 1 else 0 end )/2 as 互联网门户,\n" + 
						"sum(case when x.flag=1 and x.websitetype='05' then 1 else 0 end )/2 as 其他,\n" + 
						"ratio_to_report(sum(x.flag)) over () 占比,\n" + 
						"sum(x.hflag)/2 环比数,\n" + 
						"(case when sum(x.hflag)=0 and sum(x.flag)=0 then 0\n" + 
						"      when sum(x.hflag)=0 and sum(x.flag)<>0 then 1\n" + 
						"      else(nvl(sum(x.flag),0)-sum(x.hflag))/sum(x.hflag) end ) 环比,\n" + 
						"sum(x.tflag)/2 同比数\n" + 
						"from  x\n" + 
						"union all\n" + 
						"select x.name,x.invobjtype as code,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='1' then 1 else 0 end )  as 电子商务,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='2' then 1 else 0 end )  as 邮购,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='3' then 1 else 0 end )  as 电话购物,\n" + 
						"sum(case when x.flag=1 and x.REMSHOTYPE='4' then 1 else 0 end )  as 电视购物,\n" + 
						"sum(case when x.flag=1 and x.websitetype='01' then 1 else 0 end ) as 交易平台类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='02' then 1 else 0 end ) as 应用类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='03' then 1 else 0 end ) as 服务类,\n" + 
						"sum(case when x.flag=1 and x.websitetype='04' then 1 else 0 end ) as 互联网门户,\n" + 
						"sum(case when x.flag=1 and x.websitetype='05' then 1 else 0 end ) as 其他，\n" + 
						"ratio_to_report(sum(x.flag)) over () 占比,\n" + 
						"sum(x.hflag) 环比数,\n" + 
						"(case when sum(x.hflag)=0 and sum(x.flag)=0 then 0\n" + 
						"      when sum(x.hflag)=0 and sum(x.flag)<>0 then 1\n" + 
						"      else(nvl(sum(x.flag),0)-sum(x.hflag))/sum(x.hflag) end ) 环比,\n" + 
						"sum(x.tflag) 同比数\n" + 
						"from  x  group by x.invobjtype,x.name\n" + 
						") x1\n" + 
						"order by code,name desc";
		list.add(begintime);
		list.add(endtime+" 23:59:59");
		list.add(QueryJieAnUtils.getTongBiDate(begintime));
		list.add(QueryJieAnUtils.getTongBiDate(endtime)+" 23:59:59");
		if (hbegintime!=null&&hbegintime.length()!=0) {
			list.add(hbegintime);
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(begintime));
		}
		if (hendtime!=null&&hendtime.length()!=0) {
			list.add(hendtime+" 23:59:59");
		}else{
			list.add(QueryJieAnUtils.getHuanBiDate(endtime)+" 23:59:59");
		}
		listDouble.add(begintime);
		listDouble.add(endtime+" 23:59:59");
		listDouble.add(QueryJieAnUtils.getTongBiDate(begintime));
		listDouble.add(QueryJieAnUtils.getTongBiDate(endtime)+" 23:59:59");
		if (hbegintime!=null&&hbegintime.length()!=0) {
			listDouble.add(hbegintime);
		}else{
			listDouble.add(QueryJieAnUtils.getHuanBiDate(begintime));
		}
		if (hendtime!=null&&hendtime.length()!=0) {
			listDouble.add(hendtime+" 23:59:59");
		}else{
			listDouble.add(QueryJieAnUtils.getHuanBiDate(endtime)+" 23:59:59");
		}
		listDouble.add(begintime);
		listDouble.add(endtime+" 23:59:59");
		listDouble.add(QueryJieAnUtils.getTongBiDate(begintime));
		listDouble.add(QueryJieAnUtils.getTongBiDate(endtime)+" 23:59:59");
		if (hbegintime!=null&&hbegintime.length()!=0) {
			listDouble.add(hbegintime);
		}else{
			listDouble.add(QueryJieAnUtils.getHuanBiDate(begintime));
		}
		if (hendtime!=null&&hendtime.length()!=0) {
			listDouble.add(hendtime+" 23:59:59");
		}else{
			listDouble.add(QueryJieAnUtils.getHuanBiDate(endtime)+" 23:59:59");
		}
		
		try {
			res.add(dao.queryForList(gongShang,list));
			res.add(dao.queryForList(xiaoWeiHui,list));
			res.add(dao.queryForList(zhiShiChanQuan,list));
			res.add(dao.queryForList(zhiLiangJianDu,list));
			res.add(dao.queryForList(jiaGeJianCha,listDouble));
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("电子商务投诉情况统计报表", "WDY", i == 1 ? "查看报表" : "下载报表", gongShang,
				begintime+","+endtime, req);
		return res;
	}
	public List<Map> dianZiShangWuXiaoFei(String begintime,
			String endtime, int i, HttpServletRequest req) {
		List<Map> res=new ArrayList<Map>();
		List list=new ArrayList();
		String sb=
				"with x as (\n" +
						"  select m.REMSHOTYPE,              --远程购物类型(CDODETYPE：ZH17)\n" + 
						"         m.websitetype,             --网站类型/购物类型(CDODETYPE：ZH04)\n" + 
						"         f.putoncase,               --是否立案\n" + 
						"         f.mediationid,             --调解信息ID\n" + 
						"         i.applbasque,              --基本问题代码(CDODETYPE：CH27)\n" + 
						"         p.forfam,                  --没收金额\n" + 
						"         p.penam                    --罚款金额\n" + 
						"   from  dc_cpr_infoware i,         --信息件表\n" + 
						"         dc_cpr_involved_main m,    --涉及主体表\n" + 
						"         dc_cpr_feedback f,         --反馈信息表\n" + 
						"         dc_cpr_case_info c ,       -- 案件信息\n" + 
						"         dc_cpr_legal_punishment p  --违法行为及处罚信息表\n" + 
						"   where i.feedbackid=f.feedbackid(+)\n" + 
						"     and m.invmaiid(+)=i.invmaiid\n" + 
						"     and f.caseinfoid=c.caseinfoid(+)\n" + 
						"     and c.legpunid=p.legpunid(+)\n" + 
						"     and i.inftype='1'\n" + 
						"     and m.EBUSINESS='1'\n" + 
						"     and i.regtime >=to_date(?,'yyyy-mm-dd')\n" + 
						"     and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"  ),\n" + 
						"  x1 as (\n" + 
						"  select j.code as code, j.name as name,\n" + 
						"         count(1)as 登记量,\n" + 
						"         sum(case when x.REMSHOTYPE ='1'  then 1 else 0 end ) as 电子商务,\n" + 
						"         sum(case when x.REMSHOTYPE ='2'  then 1 else 0 end ) as 邮购,\n" + 
						"         sum(case when x.REMSHOTYPE ='3'  then 1 else 0 end ) as 电话购物,\n" + 
						"         sum(case when x.REMSHOTYPE ='4'  then 1 else 0 end ) as 电视购物,\n" + 
						"         sum(case when x.websitetype='01' then 1 else 0 end ) as 交易平台类,\n" + 
						"         sum(case when x.websitetype='02' then 1 else 0 end ) as 应用类,\n" + 
						"         sum(case when x.websitetype='03' then 1 else 0 end ) as 服务类,\n" + 
						"         sum(case when x.websitetype='04' then 1 else 0 end ) as 互联网门户,\n" + 
						"         sum(case when x.websitetype='05' then 1 else 0 end ) as 其他,\n" + 
						"         sum(case when x.putoncase  ='1'  then 1 else 0 end ) as 立案处理,\n" + 
						"         count(x.mediationid) as 调解处理,\n" + 
						"         sum(nvl(x.forfam,0)) as 没收金额,\n" + 
						"         sum(nvl(x.penam,0))  as 罚款金额\n" + 
						"         from x,dc_code.dc_12315_codedata j\n" + 
						"         where j.code=substr(x.applbasque,0,2)||'00'\n" + 
						"         and j.codetable='CH27'\n" + 
						"           group by j.code,j.name\n" + 
						" union all\n" + 
						"  select x.applbasque as code,'　'||j.name as name,\n" + 
						"         count(1)as 登记量,\n" + 
						"         sum(case when x.REMSHOTYPE ='1'  then 1 else 0 end ) as 电子商务,\n" + 
						"         sum(case when x.REMSHOTYPE ='2'  then 1 else 0 end ) as 邮购,\n" + 
						"         sum(case when x.REMSHOTYPE ='3'  then 1 else 0 end ) as 电话购物,\n" + 
						"         sum(case when x.REMSHOTYPE ='4'  then 1 else 0 end ) as 电视购物,\n" + 
						"         sum(case when x.websitetype='01' then 1 else 0 end ) as 交易平台类,\n" + 
						"         sum(case when x.websitetype='02' then 1 else 0 end ) as 应用类,\n" + 
						"         sum(case when x.websitetype='03' then 1 else 0 end ) as 服务类,\n" + 
						"         sum(case when x.websitetype='04' then 1 else 0 end ) as 互联网门户,\n" + 
						"         sum(case when x.websitetype='05' then 1 else 0 end ) as 其他,\n" + 
						"         sum(case when x.putoncase  ='1'  then 1 else 0 end ) as 立案处理,\n" + 
						"         count(x.mediationid) as 调解处理,\n" + 
						"         sum(nvl(x.forfam,0)) as 没收金额,\n" + 
						"         sum(nvl(x.penam,0))  as 罚款金额\n" + 
						"         from x  ,dc_code.dc_12315_codedata j\n" + 
						"         where j.code(+)=x.applbasque\n" + 
						"         and j.codetable='CH27'\n" + 
						"           group by x.applbasque,j.name\n" + 
						"  )\n" + 
						"select\n" + 
						"x1.code,x1.name,\n" + 
						"to_char(x1.登记量)     登记量,\n" + 
						"to_char(x1.电子商务  ) 电子商务       ,\n" + 
						"to_char(x1.邮购      ) 邮购           ,\n" + 
						"to_char(x1.电话购物  ) 电话购物       ,\n" + 
						"to_char(x1.电视购物  ) 电视购物       ,\n" + 
						"to_char(x1.交易平台类) 交易平台类     ,\n" + 
						"to_char(x1.应用类    ) 应用类         ,\n" + 
						"to_char(x1.服务类    ) 服务类         ,\n" + 
						"to_char(x1.互联网门户) 互联网门户     ,\n" + 
						"to_char(x1.其他      ) 其他           ,\n" + 
						"to_char(x1.立案处理  ) 立案处理       ,\n" + 
						"to_char(x1.调解处理  ) 调解处理       ,\n" + 
						"to_char(x1.没收金额  ) 没收金额       ,\n" + 
						"to_char(x1.罚款金额  ) 罚款金额\n" + 
						" from x1\n" + 
						"order by code,name desc";
		list.add(begintime);
		list.add(endtime+" 23:59:59");
		try {
			res=dao.queryForList(sb, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("电子商务消费投诉情况统计报表", "WDY", i == 1 ? "查看报表" : "下载报表", sb,
				begintime+","+endtime, req);
		return res;
	}
	public List<Map> zhiLiangJianDuJuBao(String begintime, String endtime,
			int i, HttpServletRequest req) {
		List<Map> res=new ArrayList<Map>();
		List list=new ArrayList();
		String sb=
				"with x as (\n" +
						"select o.invobjtype,me.REDECOLOS,i.accepttime ,i.inftype ,o.INVOAM ,p.TRANSFERED from dc_cpr_infoware i,dc_cpr_involved_object o,\n" + 
						"DC_CPR_MEDIATION me,dc_cpr_feedback f,DC_CPR_LEGAL_PUNISHMENT p, dc_cpr_case_info c\n" + 
						"where i.feedbackid=f.feedbackid(+)\n" + 
						"and o.invobjid(+)=i.invobjid\n" + 
						"and f.mediationid=me.mediationid(+)\n" + 
						"and c.caseinfoid(+)=f.caseinfoid\n" + 
						"and p.legpunid(+)=c.legpunid\n" + 
						"and i.inftype ='2'\n" + 
						"and o.businesstype='ZH21'\n" + 
						"and length(o.invobjtype)=10\n" + 
						"and i.regtime>=to_date(?,'yyyy-mm-dd')\n" + 
						"and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"),\n" + 
						"x1 as (\n" + 
						"   select distinct t.code,t.cnt,t.chulishu,t.sifa,t.jiazhi,t.sunshi from(\n" + 
						"   select x.invobjtype as code,COUNT(1) cnt,\n" + 
						"   sum(case when x.accepttime is not null then 1 else 0 end) chulishu,\n" + 
						"   sum(case when x.TRANSFERED='1' then 1 else 0 end ) sifa,\n" + 
						"   sum(nvl(x.INVOAM,0)) jiazhi,\n" + 
						"   sum(nvl(x.REDECOLOS,0)) sunshi\n" + 
						"   from x group by x.invobjtype\n" + 
						"   UNION ALL\n" + 
						"   select '00质量监督' as code,COUNT(1) cnt,\n" + 
						"   sum(case when x.accepttime is not null then 1 else 0 end) chulishu,\n" + 
						"   sum(case when x.TRANSFERED='1' then 1 else 0 end ) sifa,\n" + 
						"   sum(nvl(x.INVOAM,0)) jiazhi,\n" + 
						"   sum(nvl(x.REDECOLOS,0)) sunshi\n" + 
						"   from x\n" + 
						"   union all\n" + 
						"   select substr(x.invobjtype,0,2)||'00000000' as code,COUNT(1) cnt,\n" + 
						"   sum(case when x.accepttime is not null then 1 else 0 end) chulishu,\n" + 
						"   sum(case when x.TRANSFERED='1' then 1 else 0 end ) sifa,\n" + 
						"   sum(nvl(x.INVOAM,0)) jiazhi,\n" + 
						"   sum(nvl(x.REDECOLOS,0)) sunshi\n" + 
						"   from x group by substr(x.invobjtype,0,2)||'00000000'\n" + 
						"   union all\n" + 
						"   select substr(x.invobjtype,0,4)||'000000' as code,COUNT(1) cnt,\n" + 
						"   sum(case when x.accepttime is not null then 1 else 0 end) chulishu,\n" + 
						"   sum(case when x.TRANSFERED='1' then 1 else 0 end ) sifa,\n" + 
						"   sum(nvl(x.INVOAM,0)) jiazhi,\n" + 
						"   sum(nvl(x.REDECOLOS,0)) sunshi\n" + 
						"   from x group by substr(x.invobjtype,0,4)||'000000'\n" + 
						"   union all\n" + 
						"   select substr(x.invobjtype,0,6)||'0000' as code,COUNT(1) cnt,\n" + 
						"   sum(case when x.accepttime is not null then 1 else 0 end) chulishu,\n" + 
						"   sum(case when x.TRANSFERED='1' then 1 else 0 end ) sifa,\n" + 
						"   sum(nvl(x.INVOAM,0)) jiazhi,\n" + 
						"   sum(nvl(x.REDECOLOS,0)) sunshi\n" + 
						"   from x group by substr(x.invobjtype,0,6)||'0000'\n" + 
						"   union all\n" + 
						"   select substr(x.invobjtype,0,8)||'00' as code,COUNT(1) cnt,\n" + 
						"   sum(case when x.accepttime is not null then 1 else 0 end) chulishu,\n" + 
						"   sum(case when x.TRANSFERED='1' then 1 else 0 end ) sifa,\n" + 
						"   sum(nvl(x.INVOAM,0)) jiazhi,\n" + 
						"   sum(nvl(x.REDECOLOS,0)) sunshi\n" + 
						"   from x group by substr(x.invobjtype,0,8)||'00'\n" + 
						"   )t\n" + 
						")\n" + 
						"select x1.code,j.name,x1.cnt,x1.chulishu,x1.sifa,x1.jiazhi,x1.sunshi from x1,\n" + 
						"(SELECT  CODE,  substr('　　　　　',0,level)||NAME AS NAME\n" + 
						"    FROM (SELECT * FROM DC_CODE.DC_12315_CODEDATA WHERE CODETABLE = 'ZH21')\n" + 
						"   START WITH PARENTCODE = '0'\n" + 
						"  CONNECT BY PRIOR CODE = PARENTCODE\n" + 
						"  union all\n" + 
						"   select '00质量监督' code,'质量监督' name from dual)j\n" + 
						"     where j.code(+)=x1.code\n" + 
						"   order by code, name desc";
		list.add(begintime);
		list.add(endtime+" 23:59:59");
		try {
			res=dao.queryForList(sb, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("全市质量监督案件统计表（举报）", "WDY", i == 1 ? "查看报表" : "下载报表", sb,
				begintime+","+endtime, req);
		return res;
	}
	public List<Map> zhiLiangJianDuJuBaoShenSu(String begintime,
			String endtime, int i, HttpServletRequest req) {
		List<Map> res=new ArrayList<Map>();
		List list=new ArrayList();
		String sb=
				"with x as (\n" +
						"select o.invobjtype,me.REDECOLOS,F.ACCTYPE from dc_cpr_infoware i,dc_cpr_involved_object o,\n" + 
						"DC_CPR_MEDIATION me,dc_cpr_feedback f\n" + 
						"where i.feedbackid=f.feedbackid(+)\n" + 
						"and o.invobjid(+)=i.invobjid\n" + 
						"and f.mediationid=me.mediationid(+)\n" + 
						"and i.inftype IN ('1','2')\n" + 
						"and o.businesstype='ZH21'\n" + 
						"and length(o.invobjtype)=10\n" + 
						"and i.regtime>=to_date(?,'yyyy-mm-dd')\n" + 
						"and i.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"),\n" + 
						"x1 as (\n" + 
						"select distinct t.code,t.cnt,t.sunshi,t.shouli from (\n" + 
						"select x.invobjtype as code,count(1) as cnt,sum(nvl(x.REDECOLOS,0)) as sunshi,count(CASE WHEN X.ACCTYPE IN('10','11','12','13') THEN 1 ELSE NULL END) shouli\n" + 
						"from x group by x.invobjtype\n" + 
						"union all\n" + 
						"select '00质量监督' as code,count(1) as cnt,sum(nvl(x.REDECOLOS,0)) as sunshi,count(CASE WHEN X.ACCTYPE IN('10','11','12','13') THEN 1 ELSE NULL END) shouli\n" + 
						"from x\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,2)||'00000000' as code,count(1) as cnt,sum(nvl(x.REDECOLOS,0)) as sunshi,count(CASE WHEN X.ACCTYPE IN('10','11','12','13') THEN 1 ELSE NULL END) shouli\n" + 
						"from x group by substr(x.invobjtype,0,2)||'00000000'\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,2)||'00000000' as code,count(1) as cnt,sum(nvl(x.REDECOLOS,0)) as sunshi,count(CASE WHEN X.ACCTYPE IN('10','11','12','13') THEN 1 ELSE NULL END) shouli\n" + 
						"from x group by substr(x.invobjtype,0,2)||'00000000'\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,4)||'000000' as code,count(1) as cnt,sum(nvl(x.REDECOLOS,0)) as sunshi,count(CASE WHEN X.ACCTYPE IN('10','11','12','13') THEN 1 ELSE NULL END) shouli\n" + 
						"from x group by substr(x.invobjtype,0,4)||'000000'\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,6)||'0000' as code,count(1) as cnt,sum(nvl(x.REDECOLOS,0)) as sunshi,count(CASE WHEN X.ACCTYPE IN('10','11','12','13') THEN 1 ELSE NULL END) shouli\n" + 
						"from x group by substr(x.invobjtype,0,6)||'0000'\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,8)||'00' as code,count(1) as cnt,sum(nvl(x.REDECOLOS,0)) as sunshi,count(CASE WHEN X.ACCTYPE IN('10','11','12','13') THEN 1 ELSE NULL END) shouli\n" + 
						"from x group by substr(x.invobjtype,0,8)||'00'\n" + 
						")t\n" + 
						")\n" + 
						"select x1.code,j.name,x1.cnt,x1.sunshi,x1.shouli from x1,\n" + 
						"(SELECT  CODE,  substr('　　　　　',0,level)||NAME AS NAME\n" + 
						"    FROM (SELECT * FROM DC_CODE.DC_12315_CODEDATA WHERE CODETABLE = 'ZH21')\n" + 
						"   START WITH PARENTCODE = '0'\n" + 
						"  CONNECT BY PRIOR CODE = PARENTCODE\n" + 
						"  union all\n" + 
						"   select '00质量监督' code,'质量监督' name from dual)j\n" + 
						"   where j.code(+)=x1.code\n" + 
						"   order by code, name desc";
		list.add(begintime);
		list.add(endtime);
		try {
			res=dao.queryForList(sb, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("全市质量监督案件统计表（举报及申诉）", "WDY", i == 1 ? "查看报表" : "下载报表", sb,
				begintime+","+endtime, req);
		return res;
	}
	
	
}
