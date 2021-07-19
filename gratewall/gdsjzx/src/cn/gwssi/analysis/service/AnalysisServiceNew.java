package cn.gwssi.analysis.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.service.BaseService;

import edu.emory.mathcs.backport.java.util.Arrays;
/**
 * 分析报表统一service
 * @author wuyincheng ,Aug 23, 2016
 *  jre >= 1.7
 */
@Service
public class AnalysisServiceNew extends BaseService {
	
	//非变更数据
	private static final String ANA_TABLE = " t_reg_entry_exit ";
	
	//变更
	private static final String BG_ANA_TABLE = " t_reg_entry_alter ";
	
	//未注明类型
	private static final String NULL_REPLACE = "[未注明类型]";

	private static final List<String> EMPTY = new ArrayList<String>();
	
	/**
	 * * dateType 0 - 按日  1 - 按月
	 * startTime 开始时间
	 * endTime 结束时间
	 * isBG  0 - 非变更信息  1 - 变更表  (识别取哪一张表数据)
	 * -------分析指标维度
	 * regorg_p  区域    regorg 行政
	 * indus_p1 产业 1|2|3 indus_p2(ABCDEFG...- Z)   indus - 行业
	 * ent 企业
	 * dom_p 农资、私营、农合   dom 资金规模
	 * measure 度量维度
	 * econ_p 内资、私营、外企  econ 经济性质
	 * organ_p 组织形式第一级 organ 第二级
	 * 
	 * ------------------输出维度[列]
	 * out=[time, a.name.....]
	 * -----------------默认别名及顺序
	 * t_cognos_xzqhdm       b          QU YU
	 * t_dm_hymldm           c      -- HANG YE
	 * t_dm_qylxdm           d      -- QI YE
	 * t_reg_dic_domscale    e      -- ZI JIN GUI MO
	 * t_dm_jjxzdm           g      -- JING JI XING ZHI
	 * t_dm_zzxsdm           h      --  ZU ZHI XING SHI
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> selectAnalysisData(String dateType,  String isBG, String isBoth,  String [] out, Map<String,String> conditions) {
		final StringBuilder sqlHeader = new StringBuilder(); 
		if("0".equals(isBoth)){
			sqlHeader.append("select sum(a.value) s ");
		}else{
			final String measure = conditions.get("measure");
			if(measure == null || "".equals(measure.trim()))
				return null;
			final String [] mCode = measure.split(",");
			sqlHeader.append("select ").append(" (sum(case a.measure when '" + mCode[0] + "' then a.value end )) s ");
			for(int i = 1; i < mCode.length; i ++){
				sqlHeader.append(",(sum(case a.measure when '" + mCode[i] + "' then a.value end )) s").append(i).append(' ');
			}
//			sqlHeader.append("select (sum(case a.measure when '" + mCode[0] + "' then a.value end )) s," +
//							        "(sum(case a.measure when '" + mCode[1] + "' then a.value end )) s1");
		}
		
		final StringBuilder table = new StringBuilder(" from (");
		final String tableName = "0".equals(isBG) ? ANA_TABLE : BG_ANA_TABLE;
		table.append(tableName).append(" a ");
		final StringBuilder where = new StringBuilder(" where (");
		final StringBuilder groupAndHaving = new StringBuilder();
		initOutData(out, dateType, sqlHeader, groupAndHaving, conditions.get("measure"));
		//初始化where子句
		List<String> params = new ArrayList<String>(conditions.size());
		initWhereData(conditions,dateType, where, params, tableName, groupAndHaving);
		where.append(')');
		final Set<String> columns = new HashSet<String>();
		//去重
		for(String t : conditions.keySet()){
			columns.add(t.indexOf('_') == -1 ? t : t.substring(0, t.indexOf('_')));
		}
		for(String t : out){
			columns.add(t.indexOf('_') == -1 ? t : t.substring(0, t.indexOf('_')));
		}
		System.out.println(columns);
		//拼接left join
		for(String t : columns){
			switch(t){
				case"regorg_p":
				case"regorg":table.append(" left join t_cognos_xzqhdm b on (case when  a.regorg='440003'  then '440003' when a.regorg='440606' then '440606' else substring(a.regorg,1,4)||'00' end)=b.xz_code");break;
				case"indus_p1":
				case"indus_p2":
				case"indus":table.append(" left join t_dm_hymldm c on a.industryco=c.code1");break;
				case"ent":table.append(" left join t_dm_qylxdm d on a.enttype=d.code");break;
				case"dom_p":
				case"dom":table.append(" left join t_reg_dic_domscale e on a.domscale=e.scalecode");break;
				case"econ_p":
				case"econ":table.append(" left join t_dm_jjxzdm g on a.economicproperty=g.code");break;
				case"organ_p":
				case"organ":table.append(" left join t_dm_zzxsdm h on a.organizationmode=h.code");break;
			}
		}
		table.append(')');
		final String sql = sqlHeader.append(table.toString())
				                     .append(where.toString())
				                     .append(groupAndHaving.toString()).toString();
		List<Map> v1 = null;
		try {
			v1 = this.getPersistenceDAO("iqDataSource").queryForList(sql, params);
		} catch (OptimusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return v1;
	}
	
	private void initWhereData(Map<String, String> cons,String dateType, StringBuilder where, List<String> params,String tableName,
			                    StringBuilder having) {
		where.append("1=1 ");
		final Set<String> conKeys = cons.keySet();
		//时间需要特殊处理
		final String startTime = cons.get("startTime");
		final String endTime = cons.get("endTime");
		final String measureCode = cons.get("measure");
		//这里判断是否为本期还是期末
		final boolean flag = (measureCode.startsWith("001001") || measureCode.startsWith("002001")) && "1".equals(dateType);
		having.append((flag ? " having transdt in (select max(transdt) from "+tableName+" where transdt>=? and transdt<=? group by substring(convert(char,transdt,120),0,7))" : ""));
		List<String>  temp = null;
		where.append("and transdt>=? and transdt<=? ");
		params.add(startTime);
		params.add(endTime);
		for(String column : conKeys) {
			temp = splitCode(cons.get(column));
			switch(column){
				//measureCode
				case"measure":{
					if(temp.size() > 0){
						where.append(" and a.measure in").append(getInStru(temp));
						params.addAll(temp);
					}
					break;
				}
				//区域&行政
				case"regorg_p":{
					if(temp.size() > 0){
						where.append(" and b.qy_code in").append(getInStru(temp));
						params.addAll(temp);
					}
					break;
				}
				case"regorg":{
					if(temp.size() > 0){
						where.append(" and b.xz_code in").append(getInStru(temp));
						params.addAll(temp);
					}
					break;
				}
				//产业&&行业
				case"indus_p1":{
					if("1".equals(cons.get(column)))
						where.append(" and c.code2='A'");
					else if("2".equals(cons.get(column)))
						where.append(" and c.code2 in ('B','C','D','E')");
					else if("3".equals(cons.get(column)))
						where.append(" and c.code2 not in ('A','B','C','D','E')");
					else if("1,2".equals(cons.get(column)))
						where.append(" and c.code2 in ('A','B','C','D','E')");
					else if("1,3".equals(cons.get(column)))
						where.append(" and c.code2 not in ('B','C','D','E')");
					else if("2,3".equals(cons.get(column)))
						where.append(" and c.code2 != 'A'");
					break;
				}
				//产业&&行业(ABCDEFG.....)
				case"indus_p2":{
					if(temp.size() > 0){
						where.append(" and c.code2 in").append(getInStru(temp));
						params.addAll(temp);
					}
					break;
				}
				case"indus":{
					if(temp.size() > 0){
						where.append(" and c.code1 in").append(getInStru(temp));
						params.addAll(temp);
					}
					break;
				}
				//企业
				case"ent":{
					if(temp.size() > 0){
						where.append(" and d.code in").append(getInStru(temp));
						params.addAll(temp);
					}
					break;
				}
				//资金规模
				case"dom_p":{
					if(temp.size() > 0){
						where.append(" and substring(e.scalecode,0,3) in").append(getInStru(temp));
						params.addAll(temp);
					}
					break;
				}
				case"dom":{
					if(temp.size() > 0){
						where.append(" and e.scalecode in").append(getInStru(temp));
						params.addAll(temp);
					}
					break;
				}
				//经济性质
				case"econ_p":{
					if(temp.size() > 0){
						where.append(" and g.parent_code in").append(getInStru(temp));
						params.addAll(temp);
					}
					break;
				}
				case"econ":{
					if(temp.size() > 0){
						where.append(" and g.code in").append(getInStru(temp));
						params.addAll(temp);
					}
					break;
				}
				//组织形式
				case"organ_p":{
					if(temp.size() > 0){
						where.append(" and h.parentcode in").append(getInStru(temp));
						params.addAll(temp);
					}
					break;
				}
				case"organ":{
					if(temp.size() > 0){
						where.append(" and h.code in").append(getInStru(temp));
						params.addAll(temp);
					}
					break;
				}
		}
		}
		if(flag){
			params.add(startTime);
			params.add(endTime);
		}
	}

	////////
	private void initOutData(String[] out, String dateType, StringBuilder sqlHeader, StringBuilder groupAndHaving, String measure) {
		final boolean flag = (measure.startsWith("001001") || measure.startsWith("002001")) && "1".equals(dateType);
		int columns = 0;
		for(String column : out){
			switch(column){
				case"time":{
					sqlHeader.append(",substring(convert(char,transdt,120),0,")
					         .append("0".equals(dateType) ? 10 : 7)
					         .append(") c").append(columns);
					final String cName = flag ? "transdt" : "c";
					groupAndHaving.append(groupAndHaving.length() == 0 ? " group by " : ",").append(cName);
					if(!flag)
						groupAndHaving.append(columns);
					columns ++;
					break; 
				}
				//区域&行政
				case"regorg_p":
				case"regorg":{
					sqlHeader.append("regorg_p".equals(column) ? ",isnull(b.qy_value,'"+NULL_REPLACE+"') c" : ",isnull(b.xz_value,'"+NULL_REPLACE+"') c")
					         .append(columns);
					groupAndHaving.append(groupAndHaving.length() == 0 ? " group by c" : ",c")
					              .append(columns ++);
					break;
				}
				//产业&&行业
				case"indus_p1":{
					sqlHeader.append(",(case when  c.code2 is null then '" + NULL_REPLACE + "' when  c.code2='A' then '第一产业'  when  c.code2 in ('B','C','D','E') then '第二产业' else '第三产业' end) c")
					         .append(columns);
					groupAndHaving.append(groupAndHaving.length() == 0 ? " group by c" : ",c")
					              .append(columns ++);
					break;
				}
				//产业&&行业
				case"indus_p2":
				case"indus":{
					sqlHeader.append("indus_p2".equals(column) ? ",isnull(c.value2,'"+NULL_REPLACE+"')c" : ",isnull(c.value1,'"+NULL_REPLACE+"') c")
					         .append(columns);
					groupAndHaving.append(groupAndHaving.length() == 0 ? " group by c" : ",c")
					              .append(columns ++);
					break;
				}
				//企业
				case"ent":{
					sqlHeader.append(",isnull(d.value,'"+NULL_REPLACE+"') c")
					         .append(columns);
					groupAndHaving.append(groupAndHaving.length() == 0 ? " group by c" : ",c")
					              .append(columns ++);
					break;
				}
				//资金规模
				case"dom_p":{
					sqlHeader.append(",(case  when e.scalecode is null then '"+NULL_REPLACE+"' when substring(e.scalecode,0,3)='001' then '内资'  when  substring(e.scalecode,0,3)='002' then '私营' else '农合' end) c")
					         .append(columns);
					groupAndHaving.append(groupAndHaving.length() == 0 ? " group by c" : ",c")
					              .append(columns ++);
					break;
				}
				case"dom":{
					sqlHeader.append(",isnull(e.scale,'"+NULL_REPLACE+"') c").append(columns);
					groupAndHaving.append(groupAndHaving.length() == 0 ? " group by c" : ",c").append(columns ++);
					break;
				}
				//经济性质
				case"econ_p":
				case"econ":{
					sqlHeader.append("econ_p".equals(column) ? ",isnull(g.parent_value,'"+NULL_REPLACE+"') c" : ",isnull(g.value,'"+NULL_REPLACE+"') c").append(columns);
					groupAndHaving.append(groupAndHaving.length() == 0 ? " group by c" : ",c").append(columns ++);
					break;
				}
				//组织形式
				case"organ_p":
				case"organ":{
					sqlHeader.append("organ_p".equals(column) ? ",isnull(h.parent_value,'"+NULL_REPLACE+"') c" : ",isnull(h.value,'"+NULL_REPLACE+"') c").append(columns);
					groupAndHaving.append(groupAndHaving.length() == 0 ? " group by c" : ",c").append(columns ++);
					break;
				}
			}
		}
		
	}
	private static String getInStru(List<String> mc) {
		final StringBuilder s = new StringBuilder();
		if (mc.size() > 0) {
			s.append('(').append('?');
		}
		for (int i = 1; i < mc.size(); i++) {
			s.append(',').append('?');
		}
		if (mc.size() > 0) {
			s.append(')');
		}
		return s.length() == 0 ? null : s.toString();
	}
	
	@SuppressWarnings("unchecked")
	private static List<String> splitCode(String mc) {
		if (mc == null || "".equals(mc.trim()))
			return EMPTY;
		final String s[] = mc.split(",");
		return Arrays.asList(s);
	}
	
}
