package com.gwssi.entSelect.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.gwssi.entSelect.util.StringUtil;
import com.gwssi.entSelect.util.util;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.core.web.event.OptimusRequest;

@Service("EntSelectService")
public class EntSelectService extends BaseService {
	
	/*@Resource(name = "jdbc_sqlserver")
	private JdbcTemplate template;*/
	
	@Resource(name = "jdbc_sqlserver_dcdy")
	private JdbcTemplate template_dcdy;
	
	/*基本信息*/
	public List<Map> queryJbxx(String unifsocicrediden,String entName) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		
		StringBuffer sql = null;
		List<String> params = new ArrayList<String>();
		if(StringUtil.isEmpty(unifsocicrediden) && StringUtil.isEmpty(entName)){
			return null;
		}
		sql = new StringBuffer("select * from V_OUTER_ENT_SELECT t where 1=1 ");
		if(StringUtil.isNotEmpty(unifsocicrediden)){			
			sql.append(" and (t.unifsocicrediden = ? or t.regno = ?)");
			params.add(unifsocicrediden);
			params.add(unifsocicrediden);
		}
		if(StringUtil.isNotEmpty(entName)){
			sql.append(" and t.entname = ?");
			params.add(entName);
		}
		return util.typechage(dao.pageQueryForList(sql.toString(), params));
	}
	
	
	//基本信息列表
	public List<Map> queryJbxxList(String unifsocicrediden,String entName) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sql = null;
		if(StringUtil.isEmpty(unifsocicrediden) && StringUtil.isEmpty(entName)){
			return null;
		}
		sql = new StringBuffer("select * from (select t.*,rownum rn from (select * from V_OUTER_ENT_SELECT t where 1 = 1 ");
		List<String> params = new ArrayList<String>();
		if(StringUtil.isNotEmpty(unifsocicrediden)){
			sql.append(" and (t.unifsocicrediden = ? or t.regno = ?)");
			params.add(unifsocicrediden);
			params.add(unifsocicrediden);
		}
		if(StringUtil.isNotEmpty(entName)){
			sql.append(" and  t.entname like ?");
			params.add("%"+entName+"%");
		}
		sql.append(") t where rownum <=20) where rn>=1");
		List list = dao.queryForList(sql.toString(), params);		
		
		return util.typechage(list);
		
		
		//return util.typechage();
	}
	
	
	/*许可经营信息*/
	public List<Map> queryXueKe(String id) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");;
		StringBuffer sql = null;
		List<String> params = new ArrayList<String>();
		if(StringUtil.isNotEmpty(id)){
			//sql = new StringBuffer("select cbuitem,busform from dc_ra_mer_base_ext t where 1=1 ");
			sql = new StringBuffer("select cbuitem,pabuitem from dc_ra_mer_base_ext t where 1=1 ");
			sql.append(" and t.main_tb_id = ?");
			params.add(id);
			return util.typechage(dao.pageQueryForList(sql.toString(), params));
		}
		return null;
	}
	
	
	/*股东信息*/
	public List<Map> queryGuDong(String id, String opetype) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");;
		StringBuffer sql = null;
		List<String> params = new ArrayList<String>();
		if(StringUtil.isNotEmpty(id)){
			if("HHQY".equals(opetype) || "WZHH".equals(opetype)){
				sql = new StringBuffer("select id,cerno,inv,invtype,invatt,conprop,subconam,responway,exeaffsign from v_outer_qy_chuzi_xinxi where 1=1 ");
				sql.append(" and main_tb_id = ?");
			}else if("JT".equals(opetype)){
				sql = new StringBuffer("select id,cerno,inv,invtype,invatt,conprop,subconam,responway,exeaffsign from v_outer_jt_chuzi_xinxi where 1=1 ");
				sql.append(" and main_tb_id = ?");
			}else {
				sql = new StringBuffer("select id,cerno,inv,invtype,invatt,conprop,subconam,responway,exeaffsign from v_outer_qy_chuzi_xinxi where 1= 1 ");
				sql.append(" and main_tb_id = ?");
			}
			params.add(id);
			return dao.queryForList(sql.toString(), params);
		}else{
			return null;
		}
	}
	
	
	
	
	/*成员信息*/
	public List<Map> queryChenYuan(String id) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");;
		StringBuffer sql = null;
		List<String> params = new ArrayList<String>();
		if(StringUtil.isNotEmpty(id)){
			sql = new StringBuffer("select name,post,posbrform from v_outer_qy_nzwz_renyuan_xinxi  where 1=1 ");
			sql.append(" and pripid = ?");
			params.add(id);
			return dao.queryForList(sql.toString(), params);
		}else{
			return null;
		}
	}
	
	
	
	/*变更信息*/
	public List<Map> queryBianGen(String id) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");;
		String sql = "select to_char(t.altdate,'yyyy/MM/dd') as altdate,t.ALTTIME, wm_concat(t.ALTITEM) ALTITEMS,t.regino from v_outer_qy_nzwz_biangeng_xx t "
				+ "where t.main_tb_id = ? group by t.ALTTIME,t.altdate,t.regino order by t.altdate desc,t.ALTTIME desc,t.regino desc";
		List<String> list = new ArrayList<String>();
		list.add(id);
		return dao.queryForList(sql, list);
	}
	
	/*变更详细信息*/
	public List<Map> queryBiangengDetail(String id, String alttime,String pregino) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");;
		String sql = "select * from v_outer_qy_nzwz_biangeng_xx t where t.main_tb_id = ? and t.alttime = ? and t.REGINO= ? ";
		List list = new ArrayList();
		list.add(id);
		list.add(Integer.parseInt(alttime));
		list.add(pregino);
		return dao.queryForList(sql, list);
	}


	
	/*变更详细信息(第二种需要查询额外表)*/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> queryBiangengDetail2(String altitemcode, String regino,String id) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");;
		StringBuffer sql = new StringBuffer();
		
		
		List ids  = new ArrayList();
		List params = new ArrayList();
		List weipaiDaiBiaoBianGenQianParam = new ArrayList();
		List weipaiDaiBiaoHouParam = new ArrayList();
		
		if("03".equals(altitemcode)|| "B7".equals(altitemcode)){ //负责人变更--首席代表变更
			sql.append("select t.persname as content, t.bgtype from dc_ra_mer_persons_ext t where (t.licflag = 'fddbr' or t.licflag = 'jyz') and t.regino = ?");
			params.add(regino);
		}else if("30".equals(altitemcode)||"D2".equals(altitemcode) || "D9".equals(altitemcode)){ //投资人变更
			//sql.append("select t.inv||' '||to_char(t.subconam,'fm999999999990.099999')||'（万元）'||to_char(t.conprop,'fm99990.009999')||'%' as content, t.bgtype,case EXCAFFSIGN  when '1' then '是' when '2' then '否' end as zshhr, case RESPONWAY when '1' then '普通合伙人' when '2' then '有限合伙人' end as  RESPONWAY  from dc_ra_mer_invest_ext t where 1=1 and t.regino = ? order by t.id");
			
			sql.append("select t.inv || ' ' || to_char(t.subconam, 'fm999999999990.099999') ||'（万元）' || to_char(t.conprop, 'fm99990.009999') || '%' as content,t.bgtype,case EXCAFFSIGN\n" +
					"         when '1' then\n" + 
					"          '是'\n" + 
					"         when '2' then\n" + 
					"          '否'\n" + 
					"       end as zshhr,\n" + 
					"       case RESPONWAY\n" + 
					"         when '1' then\n" + 
					"          '普通合伙人'\n" + 
					"         when '2' then\n" + 
					"          '有限合伙人'\n" + 
					"       end as RESPONWAY,\n" + 
					"       case when p.persname is not null then '委派代表:'||P.Persname else null end as persname\n" + 
					"  from dc_ra_mer_invest_ext t left join dc_ra_mer_persons_ext p  on t.id = p.id  and t.bgtype = p.bgtype \n" + 
					" where 1 = 1 \n " + 
					"   and t.regino = ? \n" + 
					" order by t.id ");

			
			params.add(regino);
		}
		/*else if("30".equals(altitemcode)||"D2".equals(altitemcode) || "D9".equals(altitemcode) || "11".equals(altitemcode)){ //投资人变更
			sql.append("select t.inv||' '||to_char(t.subconam,'fm999999999990.099999')||'（万元）'||to_char(t.conprop,'fm99990.009999')||'%' as content, t.bgtype from dc_ra_mer_invest_ext t where 1=1 and t.regino = ?");
			params.add(regino);
		}*/else if("46".equals(altitemcode) || "A3".equals(altitemcode) || "48".equals(altitemcode) || "B8".equals(altitemcode) || "47".equals(altitemcode) || "49".equals(altitemcode) || "70".equals(altitemcode) || "C7".equals(altitemcode)){ //主要人员变更
			sql.append("select wm_concat(t.persname ||'('||(func_getcode('C00005', a.POST)) ||')') as content, t.bgtype from dc_ra_mer_member_ext a left join dc_ra_mer_persons_ext t on t.id = a.id where t.regino = ? group by t.bgtype");
			params.add(regino);
		}else if("A8".equals(altitemcode) || "D6".equals(altitemcode) || "D7".equals(altitemcode) || "42".equals(altitemcode) || "15".equals(altitemcode) || "19".equals(altitemcode)){ //隶属企业名称变更
			sql.append("select t.entname as content, t.bgtype from dc_ra_mer_subent_ext t where 1=1 and t.regino = ?");
			params.add(regino);
		}else if("A9".equals(altitemcode) || "B2".equals(altitemcode)){ //隶属企业地址变更
			sql.append("select t.dom as content, t.bgtype from dc_ra_mer_subent_ext t where 1=1 and t.regino = ?");
			params.add(regino);
		}else if("B9".equals(altitemcode)){ //经营者表更
			sql.append("select t.persname as content, t.bgtype from dc_ra_mer_persons_ext t where t.licflag = 'jyz' and t.regino = ?");
			params.add(regino);
		}else if("B8".equals(altitemcode)){ //代表表更
			sql.append("select t.persname||'('||t.POST||')' as content, t.bgtype from V_OUTER_QY_ZHUYAORENYUANBIAO t where t.licflag in ('DSZ','QTDS','ZJL','JSCY','QT') and t.regino = ?");
			params.add(regino);
		}else if("C3".equals(altitemcode)){ //投资人姓名变更
			sql.append("select t.persname as content, t.bgtype from dc_ra_mer_persons_ext t where t.licflag = 'fddbr' and t.regino = ?");
			params.add(regino);
		}else if("C9".equals(altitemcode)){ //出资计划变更
			sql.append("select t.curactconam as content, t.bgtype from dc_ra_mer_invplan_ext t where 1=1 and t.regino = ?");
			params.add(regino);
		}else if("D1".equals(altitemcode)){ //审批项目变更
			sql.append("select t.licname as content, t.bgtype from dc_ra_mer_license_ext t where 1=1 and t.regino = ?");
			params.add(regino);
		}else if("E3".equals(altitemcode) || "E7".equals(altitemcode) || "73".equals(altitemcode)){ //清算组成员变更
			sql.append("select wm_concat(t.persname) as content, t.bgtype from dc_ra_mer_clmember_ext t where t.licflag in ('qsz','qszcy') and t.regino = ? group by t.bgtype");
			params.add(regino);
		}else if("E9".equals(altitemcode)){ //合伙人变更
			sql.append("select wm_concat(t.inv) as content, t.bgtype from dc_ra_mer_invest_ext t where 1=1 and t.regino = ? group by t.bgtype");
			params.add(regino);
		}else if("F4".equals(altitemcode)){ //执行事务合伙人/委派代表变更
			sql.append("select t.persname as content, t.bgtype from V_OUTER_QY_ZHUYAORENYUANBIAO t where t.licflag = 'hhrxx' and t.regino = ?");
			params.add(regino);
		}else if("43".equals(altitemcode)){ //指定联系人变更
			sql.append("select t.persname as content, t.bgtype from dc_ra_mer_persons_ext t where t.licflag = 'gslly' and t.regino = ?");
			params.add(regino);
		}else if("44".equals(altitemcode)){ //前置许可信息变更
			sql.append("select wm_concat(t.licname||'('||t.licno||')') as content, t.bgtype from dc_ra_mer_license_ext t where t.regino = ? group by t.bgtype");
			params.add(regino);
		}else if("72".equals(altitemcode)){ //分公司备案变更
			sql.append("select t.brname as content, t.bgtype from dc_ra_mer_branches_ext t where t.regino = ?");
			params.add(regino);
		}else{
			return null;
		}
		List<Map> resultList =  dao.queryForList(sql.toString(), params);
		
		
		if("E9".equals(altitemcode)) {
			 String sql2  ="select inv,subconam,conprop,zshhr from v_qy_chuzi_xinxi2 t where t.pripid= ? and t.regino = ? ";
			 ids.add(id);
			 ids.add(regino);
			List<Map> list =  dao.queryForList(sql2, ids);
			Map addMap = null;
			/*for (int i = 0; i < resultList.size(); i++) {
				addMap = new HashMap(); 
				addMap  = resultList.get(i);
				String contents = (String) addMap.get("content");
				System.out.println(contents);
			}*/
			
			addMap = new HashMap(); 
			addMap  = resultList.get(1);
			String contents = (String) addMap.get("content");
			String [] tempCon = contents.split(",");
			//System.out.println("=========> " +list.size());
			Map ret = null;
			StringBuffer retSb = new StringBuffer();
			for (int i = 0; i < tempCon.length; i++) {
				for (int j = 0; j < list.size(); j++) {
					ret = (Map) list.get(j);
					//System.out.println(ret);
					String inv = (String) ret.get("inv");
					BigDecimal conprop = (BigDecimal) ret.get("conprop");
					BigDecimal subconam = (BigDecimal) ret.get("subconam");
					String  zshhr = (String) ret.get("zshhr");
					
					if(inv.equals(tempCon[i])) {
						//System.out.println(zshhr);
						retSb.append(tempCon[i] +"  "+subconam +"（万元）" + conprop+"% <br/>");
					}
					
				}
			}
			
			Map mapReturn = resultList.get(1);
			mapReturn.put("content", retSb);
			resultList.add(mapReturn);
		}
		
		return resultList;
		
	}
	
	/*股权质押信息*/
	public List<Map> queryGuQuanZhiYa(String id) throws OptimusException{	
	
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		String sql = "select * from v_out_dc_gqdj_xx a where a.status = '1' and a.pripid = ?";
		List<String> params = new ArrayList<String>();
		params.add(id);
		List<Map> list = dao.queryForList(sql, params);
		return util.typechage(list);
		
		/*String sql = "select distinct case b.limityycode when '0017' then '股权质押' end as limityyvalue,b.limittimebegin,b.limittimeend, '限制中' as limitstatus,b.Limitsm,a.memo,a.hztime,b.limityycode from tdXZSDInfo a left join tdXZSDDetail b on a.ID=b.InfoID where b.LimitState='1' and a.EntID = ? and b.LimitYYCode in ('0017')";
		List res = template.queryForList(sql, new Object[]{id});
		return util.typechage(res);*/
	}
	
	/*动产抵押信息*/
	public List<Map> queryDongChandiYa(String id, String regno, String unifsocicrediden) throws OptimusException{		
		
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		String sql = "select * from v_out_dc_dcdy_xx t where t.mortgagortype in ('1','2') and t.mortgagorregno in (?, ?, ?)";
		List<String> params = new ArrayList<String>();
		params.add(id);
		params.add(regno);
		params.add(unifsocicrediden);
		List<Map> list = dao.queryForList(sql, params);
		return util.typechage(list);
		
		/*String sql = "select MorRegCNO,AuthDate,case RegStatus when '0' then '新登记' when '1' then '变更' when '2' then '注销' end as RegStatus,OriMorContNO ,RecordID from tdPledgeBase where RecordID in (select PRecordID from tdPledgor where IsNew='1'and MortgagorType in ('01','02') and MortgagorIDNumber in (?,?,?)) and AuthFlag='1'";
		List res = template_dcdy.queryForList(sql, new Object[]{id,regno,unifsocicrediden});
		return util.typechage(res);*/
	}
	
	/*动产抵押详细信息*/
	public List<Map> queryDCDYDetail(String id, String flag) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();
		String sql = "";
		if("1".equals(flag)){//抵押人名称
			sql = "select t.mortgagor, t.id as PRecordID from DC_HT_DCDY_JBXX_ZS t where t.id in (?)";
			//sql = "select Mortgagor,PRecordID from tdPledgor where PRecordID in (?)";
		}else if("2".equals(flag)){//抵押权人名称
			sql = "select t.more as Mortgagee, t.id as PRecordID from DC_HT_DCDY_JBXX_ZS t where t.id in (?)";
			//sql = "select Mortgagee,PRecordID from TDPLEDGOROBLIGEE where PRecordID in (?)";
		}else if("3".equals(flag)){//抵押物名称与数量
			sql = "select t.guaname ||' '|| t.guaamount || t.GUAUNIT as GuaNameQuan, t.main_tb_id as PRecordID from DC_HT_DYWXX_ZS t where t.main_tb_id in (?)";
			//sql = "select GuaName + ' ' + Quan as GuaNameQuan,PRecordID from TDGUARANTY where PRecordID in (?)";
		}else{
			return null;
		}
		params.add(id);
		List<Map> queryForList = dao.queryForList(sql, params);
		
		//List res = template_dcdy.queryForList(sql, new Object[]{id});
		return util.typechage(queryForList);
	}
	
	
	
	/*法院冻结信息*/
	public List<Map> queryFaYuanDongJie(String id) throws OptimusException{
		/*String sql = "select distinct case b.limityycode when '0001' then '法院冻结' end as limityyvalue,b.limittimebegin,b.limittimeend, '限制中' as limitstatus,b.Limitsm,a.memo,a.hztime,b.limityycode from tdXZSDInfo a left join tdXZSDDetail b on a.ID=b.InfoID where b.LimitState='1' and a.EntID = ? and b.LimitYYCode in ('0001')";
		List res = template.queryForList(sql, new Object[]{id});
		return util.typechage(res);*/
		
		
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");;
		StringBuffer sql = null;
		List<String> params = new ArrayList<String>();
		if(StringUtil.isNotEmpty(id)){
			//sql = new StringBuffer("select * from dc_ms_abnormal_diretory t  where  1=1  and t.type in('1','2')");
			//sql = new StringBuffer("select * from dc_ms_abnormal_diretory t  where  1=1  and t.type in ('2','3')");
			//sql = new StringBuffer("SELECT DISTINCT CASE b.limityycode WHEN '0001' THEN '法院冻结' END AS limityyvalue, b.limittimebegin, b.limittimeend, '限制中' AS limitstatus, b.Limitsm , a.remarks as memo,a.apprdate, b.limityycode FROM dc_ms_ba_entilimit a LEFT JOIN dc_ms_ba_tdxzsddetail b ON a.id = b.infoid WHERE b.limitstate = '1' AND b.limityycode IN ('0001') ");
			sql = new StringBuffer("select * from  V_outer_dc_ms_ba_entilimit a ");
			sql.append(" where a.entid = ? order by a.apprdate desc");
			params.add(id);
			return util.typechage(dao.queryForList(sql.toString(), params));
		}else{
			return null;
		}
	}
	
	/*经营异常*/
	public List<Map> queryJinYin(String id) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");;
		StringBuffer sql = null;
		List<String> params = new ArrayList<String>();
		if(StringUtil.isNotEmpty(id)){
			//sql = new StringBuffer("select * from dc_ms_abnormal_diretory t  where  1=1  and t.type in('1','2')");
			//sql = new StringBuffer("select * from dc_ms_abnormal_diretory t  where  1=1  and t.type in ('2','3')");
			sql = new StringBuffer("select * from v_outer_DC_MS_ABNORMAL_DriY t  where  1=1 ");
			sql.append(" and t.entid = ?");
			params.add(id);
			return util.typechage(dao.pageQueryForList(sql.toString(), params));
		}else{
			return null;
		}
	}
	
	//查询集团的母公司
	public List<Map> getEntMother(String id) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");;
		StringBuffer sql = null;
		sql = new StringBuffer("select t.entname, a.entname as subname, a.regno ,a.dom from dc_ra_mer_base t ,dc_ra_mer_subent a where 1 = 1 and t.id =  a.main_tb_id ");
		List<String> params = new ArrayList<String>();
		if(StringUtil.isNotEmpty(id)){
			sql.append(" and t.id = ?");
			params.add(id);
		}
		return dao.pageQueryForList(sql.toString(), params);
	}

	//企业是否有经营异常,--注销不显示
	@SuppressWarnings("rawtypes")
	public String getEntIsNormal(String id) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sql = null;
		
		//sql = new StringBuffer("select count(1)  as count from  dc_ms_abnormal_diretory t ");
		sql = new StringBuffer(" select count(1) as count from dc_ms_abnormal_diretory t1 ,dc_ra_mer_base t2 where t2.pripid  = t1.entid and t2.entstatus <> '4' and t1.type='2' ");
		List<String> params = new ArrayList<String>();
		if(StringUtil.isNotEmpty(id)){
			//sql.append("where  t.entid = ? and t.type in ('1', '2')");
			sql.append(" and   t1.entid = ?");
			params.add(id.trim());
		}
		List<Map> list  = dao.queryForList(sql.toString(), params);
		BigDecimal str = (BigDecimal) list.get(0).get("count");
		//System.out.println(str);
		return str.toString();
	}
	
	//企业是否有严重违法失信
	public int getEntIsHonesty(String id) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sql = new StringBuffer();
		List<String> params = new ArrayList<String>();
		if (StringUtil.isNotEmpty(id)) {
			sql.append("select count(1) as count from dc_ms_dishonesty_directory t where t.type='2' and t.entid = ? ");
			params.add(id);
			int queryForInt = dao.queryForInt(sql.toString(), params);
			return queryForInt;
		}
		return 0;
	}
	
	//年检
	public List getNJ(String entId) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");;
		StringBuffer sql = null;
		sql = new StringBuffer("select njyear from dc_tdnjonlinesl  where njresult = '13' and  ");
		List<String> params = new ArrayList<String>();
		if(StringUtil.isNotEmpty(entId)){
			sql.append(" entid = ? order by njyear");
			params.add(entId.trim());
		}
		List<Map> list  = dao.queryForList(sql.toString(), params);
		//System.out.println("===>" + list);
		return list;
	}
	
	
	// 个体年报
	@SuppressWarnings("rawtypes")
	public List getGtnb(String pripid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		/// StringBuffer sql =  new StringBuffer("select ancheyear from dc_nb_gt_jbxx where ifpub>1  ");
		StringBuffer sql = new StringBuffer();
		sql.append("select ancheyear from ");
		sql.append(" (select t.ancheyear, row_number() over(partition by pripid,ancheyear order by updatetime desc) as cnt ");
		sql.append(" from dc_nb_gt_jbxx t where ifpub > 1 ");
		
		List<String> params = new ArrayList<String>();
		if (StringUtil.isNotEmpty(pripid)) {
			sql.append(" and pripid = ? ");
			params.add(pripid);
		}
		sql.append(" ) where cnt = 1 order by ancheyear ");
		List<Map> list = dao.queryForList(sql.toString(), params);
		//System.out.println("===>" + list);
		return list;
	}

	// 除个体之外的年报
	@SuppressWarnings("rawtypes")
	public List getNonGtnb(String pripid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		// StringBuffer sql = new StringBuffer("select ancheyear from dc_nb_qy_jbxx where ifpub>1  ");
		/* 
		 * 20180110
		 * 为了去除部分企业信息年报信息重复的问题，所以对sql进行如下修改
		 *  select ancheyear from 
			  (select t.ancheyear, row_number() over( partition by pripid,ancheyear  order by updatetime desc) as cnt
			    from dc_nb_qy_jbxx t where ifpub > 1 and pripid = 'A039273')
			where cnt = 1
			order by ancheyear;
		 */
		StringBuffer sql = new StringBuffer();
		sql.append("select ancheyear from");
		sql.append(" (select t.ancheyear, row_number() over(partition by pripid,ancheyear order by updatetime desc) as cnt");
		sql.append(" from dc_nb_qy_jbxx t where ifpub > 1 ");
		
		List<String> params = new ArrayList<String>();
		if (StringUtil.isNotEmpty(pripid)) {
			sql.append(" and  pripid = ? ");
			params.add(pripid.trim());
		}
		sql.append(" ) where cnt = 1 order by ancheyear");
		List<Map> list = dao.queryForList(sql.toString(), params);
		//System.out.println("===>" + list);
		return list;
	}
	
	
	//年报的新的简洁的查询方式，暂时没用到
	@SuppressWarnings("rawtypes")
	public List getnb(String pripid, String opetype) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sql = new StringBuffer();
		List<Map> list = null;//结果集
		List<String> params = new ArrayList<String>();
		if (opetype.equals("GT")) {
			sql.append("select ancheyear from dc_nb_gt_jbxx where ifpub>1  ");
			if (StringUtil.isNotEmpty(pripid)) {
				sql.append("and  pripid = ?");
				params.add(pripid.trim());
			}
		} else {
			sql.append("select ancheyear from dc_nb_qy_jbxx where ifpub>1  ");
			if (StringUtil.isNotEmpty(pripid)) {
				sql.append("and  pripid = ?");
				params.add(pripid.trim());
			}
		}
		list = dao.queryForList(sql.toString(), params);
		return list;
	}
	
	//法定代表人
	public List queryFDDBR(String id) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sql = null;
		List<String> params = new ArrayList<String>();
		if (StringUtil.isNotEmpty(id)) {
			sql = new StringBuffer("select persname from dc_ra_mer_persons p where p.main_tb_id = ? and p.licflag = 'fddbr'");
			params.add(id.trim());
		}
		List<Map> list = dao.queryForList(sql.toString(), params);
		return list;
	}
	
	//经营者
	public List queryJYZ(String id) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sql = null;
		List<String> params = new ArrayList<String>();
		if (StringUtil.isNotEmpty(id)) {
			sql = new StringBuffer("select persname from dc_ra_mer_persons p where p.main_tb_id = ? and p.licflag = 'jyz'");
			params.add(id.trim());
		}
		List<Map> list = dao.queryForList(sql.toString(), params);
		return list;
	}
	
	//执行合伙人
	public List queryZXHHR(String id) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sql = null;
		List<String> params = new ArrayList<String>();
		if (StringUtil.isNotEmpty(id)) {
			// 修改sql为外关联的形式。20171228
			//sql = new StringBuffer("select case when t.inv is not null then t.inv else s.persname end as persname from DC_RA_MER_PERSONS s ,dc_ra_mer_invest t where s.id = t.id and t.exeaffsign='1' and s.main_tb_id = ?");
			sql = new StringBuffer("select case when t.inv is not null then t.inv else s.persname end as persname from DC_RA_MER_PERSONS s ,dc_ra_mer_invest t where s.id(+) = t.id and t.exeaffsign='1' and t.main_tb_id = ?");
			params.add(id.trim());
		}
		List<Map> list = dao.queryForList(sql.toString(), params);
		return list;
	}
	
   //委派代表
	public List wpdb(String id,String entId) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sql = null;
		List<String> params = new ArrayList<String>();
		if (StringUtil.isNotEmpty(id)&&StringUtil.isNotEmpty(entId)) {
			sql = new StringBuffer("select persname from dc_ra_mer_persons where id=? and id in(select id from dc_ra_mer_invest where main_tb_id= ? and exeaffsign='1' and invatt<>'0')");
			params.add(id.trim());
			params.add(entId.trim());
			List<Map> list = dao.queryForList(sql.toString(), params);
			return list;
		}else{
			return null;
		}
	}
	
	public Object queryTypeChange(String id) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sql = null;
		List<String> params = new ArrayList<String>();
		if (StringUtil.isNotEmpty(id)) {
			sql = new StringBuffer("select t.codeindex_value from DC_STANDARD_CODEDATA t where t.standard_codeindex = 'C00013' and t.codeindex_code = ?");
			params.add(id.trim());
			List<Map> list = dao.queryForList(sql.toString(), params);
			if(list.size()>0){
				return list.get(0).get("codeindexValue");
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	//查询企业分支机构
	public List<Map> queryBranch(String id) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");;
		StringBuffer sql = null;
		sql = new StringBuffer("select wm_concat(brname) as content from dc_ra_mer_branches t where t.main_tb_id = ? ");
		List<String> params = new ArrayList<String>();
		if(StringUtil.isNotEmpty(id)){
			params.add(id);
		}else{
			params.add(null);
		}
		return dao.pageQueryForList(sql.toString(), params);
	}

	//企业严重违法失信信息查询
	public List<Map> queryYanZhongWeiFa(String id) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sql = new StringBuffer();
		List<String> params = new ArrayList<String>();
		if (StringUtil.isNotEmpty(id)) {
			sql.append("select * from v_out_ms_dishonesty_directory t where t.entId = ? ");
			params.add(id);
			return dao.queryForList(sql.toString(), params);
		}
		return null;
	}

	/**
	 * 根据企业id，查询dc_ra_mer_base表中的企业是否是撤销登记状态（status=8）。
	 *    如果是撤销登记状态，则在商事登记薄的提示框中显示“该企业已撤销登记”；
	 *    否则，就不显示。
	 * 
	 * @param id 企业id
	 * @throws OptimusException 
	 */
	public int getEntStatus(String id) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sql = new StringBuffer();
		List<String> params = new ArrayList<String>();
		if (StringUtils.isNotEmpty(id)) {
			sql.append("select count(1) from dc_ra_mer_base t where t.entstatus = '8' and t.pripid = ?");
			params.add(id);
			int num = dao.queryForInt(sql.toString(), params);
			return num;
		}
		return 0;
	}

	/**
	 * 根据id查询企业变更撤销
	 * @param id
	 * @throws OptimusException 
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryBianGenRecoxtion(String id, String alttime) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sql = new StringBuffer();
		List<String> params = new ArrayList<String>();
		if (StringUtils.isNotEmpty(id) && StringUtils.isNotEmpty(alttime)) {
			sql.append("select altitem as altitemcode from DC_RA_MER_ALTITEM t where t.altitem = 'E4' and t.alttime = ? and t.main_tb_id = ? ");
			params.add(alttime);
			params.add(id);
			List<Map> queryForList = dao.queryForList(sql.toString(), params);
			return queryForList;
		}
		return null;
	}

}
