package cn.gwssi.analysis.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.cognos.org.apache.axis.utils.StringUtils;
import com.ctc.wstx.util.StringUtil;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

import edu.emory.mathcs.backport.java.util.Arrays;
/**
 * 业务分析统一service
 * @author wuyincheng ,Aug 23, 2016
 *  jre >= 1.7
 */
@Service
public class BusinessService extends BaseService {
	
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
	 * @throws OptimusException 
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> selectBusinessSum(Map params) throws OptimusException {
		IPersistenceDAO dao = this.getPersistenceDAO("iqDataSource");
		StringBuffer sql = new StringBuffer(
				"select sum(a.value) as s,(select b.xz_value from t_cognos_xzqhdm b where xz_code = a.regorg) as x,a.transdt as t " +
				"from t_task_measure a  left join t_dm_ywlxdm b on a.accepttype=b.code  where 1=1 ");
		List cons=new ArrayList();		
		if(params.get("transdt")!=null && params.get("transdt")!=""){
		//	sql.append(" and a.transdt= '2016-08-21' ");
			sql.append(" and a.transdt= ? ");
			cons.add(params.get("transdt"));
		}
		
		if(params.get("accepttype")!=null && params.get("accepttype")!="" && !"-1".equals(params.get("accepttype").toString())){
			sql.append(" and b.PARENTCODE in ");
			/*if("-1".equals(params.get("accepttype").toString())){
					sql.append(" ('1','2','3','4','5','6','7') ");	
					for(int i=1;i<8,i++){
					cons.add(i.toString());
					}
					}else{
			sql.append(getInStru(splitCode(params.get("accepttype").toString())));
				for(String s:splitCode(params.get("accepttype").toString())){
						cons.add(s);
						}			
					}*/
		sql.append(getInStru(splitCode(params.get("accepttype").toString())));
			for(String s:splitCode(params.get("accepttype").toString())){
					cons.add(s);
					}			
			}
		
		if(params.get("measure")!=null && params.get("measure")!=""){
			sql.append(" and a.measure=?");
			cons.add(params.get("measure"));
		}
		
		if(params.get("regorg")!=null && params.get("regorg")!=""&& !"-1".equals(params.get("regorg").toString())){
			sql.append(" and a.regorg in ");
			sql.append(getInStru(splitCode(params.get("regorg").toString())));
			for(String s:splitCode(params.get("regorg").toString())){
				cons.add(s);
			}
		}
		sql.append(
				" group by a.regorg,t ");
		return dao.queryForList(sql.toString(), cons);
	}
	
	
	@SuppressWarnings("rawtypes")
	public List<List> businessSecondSum(Map params) throws OptimusException {
		IPersistenceDAO dao = this.getPersistenceDAO("iqDataSource");
		List list=new ArrayList();
		
		StringBuffer sql = new StringBuffer(
				"select sum(a.value) s,convert(varchar(32),a.transdt)  t,isnull(d.value,'[未注明类型]') x from t_task_measure a left join t_dm_ywlxdm b on a.accepttype=b.code left join t_cognos_xzqhdm c on a.regorg=c.xz_code left join t_cognos_ywlx d on d.code = b.parentcode where 1=1 ");
		List cons=new ArrayList();		
		if(params.get("transdt")!=null && params.get("transdt")!=""){
			//sql.append(" and a.transdt= '2016-08-21' ");
			sql.append(" and a.transdt= ? ");
			cons.add(params.get("transdt"));
		}
		
		if(params.get("accepttype")!=null && params.get("accepttype")!=""&& !"-1".equals(params.get("accepttype").toString())){
			sql.append(" and b.parentcode in ");
			sql.append(getInStru(splitCode(params.get("accepttype").toString())));
			for(String s:splitCode(params.get("accepttype").toString())){
				cons.add(s);
			}
		}
		
		if(params.get("regorg")!=null && params.get("regorg")!=""  && !"-1".equals(params.get("regorg"))){
			sql.append(" and a.regorg in ");
			sql.append(getInStru(splitCode(params.get("regorg").toString())));
			for(String s:splitCode(params.get("regorg").toString())){
				cons.add(s);
			}
		}
		
		if("-1".equals(params.get("regorg"))  && params.get("regorgCN")!=null && params.get("regorgCN")!=""){
			sql.append(" and c.xz_value like '%");
			sql.append(params.get("regorgCN").toString());
			sql.append("%'");
		}
		StringBuffer sb1=new StringBuffer(sql.toString());
		StringBuffer sb2=new StringBuffer(sql.toString());
			sb1.append(" and a.measure='1'");
			sb2.append(" and a.measure='2'");
		
		sb1.append(" group by t,x ");
		sb2.append(" group by t,x ");
		list.add(dao.queryForList(sb1.toString(), cons));
		list.add(dao.queryForList(sb2.toString(), cons));
		return list;
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
