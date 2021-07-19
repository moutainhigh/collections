package com.gwssi.entSelect.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.gwssi.entSelect.util.StringUtil;
import com.gwssi.entSelect.util.util;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

@Service("EntSelectService")
public class EntSelectService extends BaseService {
	
	@Resource(name = "jdbc_sqlserver")
	private JdbcTemplate template;
	
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
				sql = new StringBuffer("select id,cerno,inv,invtype,invatt,conprop,subconam,responway,exeaffsign from v_outer_qy_chuzi_xinxi where licflag = 'hhrxx' ");
				sql.append(" and main_tb_id = ?");
			}else{
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
		String sql = "select to_char(t.altdate,'yyyy/MM/dd') as altdate,t.ALTTIME, wm_concat(t.ALTITEM) ALTITEMS from v_outer_qy_nzwz_biangeng_xx t "
				+ "where t.main_tb_id = ? group by t.ALTTIME,t.altdate order by t.ALTTIME desc";
		List<String> list = new ArrayList<String>();
		list.add(id);
		return dao.queryForList(sql, list);
	}
	
	/*变更详细信息*/
	public List<Map> queryBiangengDetail(String id, String alttime) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");;
		String sql = "select * from v_outer_qy_nzwz_biangeng_xx t where t.main_tb_id = ? and t.alttime = ?";
		List list = new ArrayList();
		list.add(id);
		list.add(Integer.parseInt(alttime));
		return dao.queryForList(sql, list);
	}

	/*变更详细信息(第二种需要查询额外表)*/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> queryBiangengDetail2(String altitemcode, String regino) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");;
		StringBuffer sql = new StringBuffer();
		List params = new ArrayList();
		if("03".equals(altitemcode)|| "B7".equals(altitemcode)){ //负责人变更--首席代表变更
			sql.append("select t.persname as content, t.bgtype from dc_ra_mer_persons_ext t where (t.licflag = 'fddbr' or t.licflag = 'jyz') and t.regino = ?");
			params.add(regino);
		}else if("30".equals(altitemcode)||"D2".equals(altitemcode) || "D9".equals(altitemcode) || "11".equals(altitemcode)){ //投资人变更
			sql.append("select t.inv||' '||t.Subconam||'（万元）'||t.conprop||'%' as content, t.bgtype from dc_ra_mer_invest_ext t where 1=1 and t.regino = ?");
			params.add(regino);
		}else if("46".equals(altitemcode) || "A3".equals(altitemcode) || "48".equals(altitemcode) || "B8".equals(altitemcode) || "47".equals(altitemcode) || "49".equals(altitemcode) || "70".equals(altitemcode) || "C7".equals(altitemcode)){ //主要人员变更
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
		}else if("E3".equals(altitemcode)){ //清算组成员变更
			sql.append("select wm_concat(t.persname) as content, t.bgtype from dc_ra_mer_persons_ext t where t.licflag in ('qsz','qszcy') and t.regino = ? group by t.bgtype");
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
		}
		
		
		else{
			return null;
		}
		
		return dao.queryForList(sql.toString(), params);
	}
	
	/*股权质押信息*/
	public List<Map> queryGuQuanZhiYa(String id) throws OptimusException{		
		String sql = "select distinct case b.limityycode when '0017' then '股权质押' end as limityyvalue,b.limittimebegin,b.limittimeend, '限制中' as limitstatus,b.Limitsm,a.memo,a.hztime,b.limityycode from tdXZSDInfo a left join tdXZSDDetail b on a.ID=b.InfoID where b.LimitState='1' and a.EntID = ? and b.LimitYYCode in ('0017')";
		List res = template.queryForList(sql, new Object[]{id});
		return util.typechage(res);
	}
	
	/*动产抵押信息*/
	public List<Map> queryDongChandiYa(String id, String regno, String unifsocicrediden) throws OptimusException{		
		String sql = "select MorRegCNO,AuthDate,case RegStatus when '0' then '新登记' when '1' then '变更' when '2' then '注销' end as RegStatus,OriMorContNO ,RecordID from tdPledgeBase where RecordID in (select PRecordID from tdPledgor where IsNew='1'and MortgagorType in ('01','02') and MortgagorIDNumber in (?,?,?)) and AuthFlag='1'";
		List res = template_dcdy.queryForList(sql, new Object[]{id,regno,unifsocicrediden});
		return util.typechage(res);
	}
	
	/*动产抵押详细信息*/
	public List<Map> queryDCDYDetail(String id, String flag) throws OptimusException{
		String sql = "";
		if("1".equals(flag)){//抵押人名称
			sql = "select Mortgagor,PRecordID from tdPledgor where PRecordID in (?)";
		}else if("2".equals(flag)){//抵押权人名称
			sql = "select Mortgagee,PRecordID from TDPLEDGOROBLIGEE where PRecordID in (?)";
		}else if("3".equals(flag)){//抵押物名称与数量
			sql = "select GuaName + ' ' + Quan as GuaNameQuan,PRecordID from TDGUARANTY where PRecordID in (?)";
		}else{
			return null;
		}
		
		List res = template_dcdy.queryForList(sql, new Object[]{id});
		return util.typechage(res);
	}
	
	
	
	/*法院冻结信息*/
	public List<Map> queryFaYuanDongJie(String id) throws OptimusException{
		String sql = "select distinct case b.limityycode when '0001' then '法院冻结' end as limityyvalue,b.limittimebegin,b.limittimeend, '限制中' as limitstatus,b.Limitsm,a.memo,a.hztime,b.limityycode from tdXZSDInfo a left join tdXZSDDetail b on a.ID=b.InfoID where b.LimitState='1' and a.EntID = ? and b.LimitYYCode in ('0001')";
		List res = template.queryForList(sql, new Object[]{id});
		return util.typechage(res);
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
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");;
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
		StringBuffer sql =  new StringBuffer("select ancheyear from dc_nb_gt_jbxx where ifpub>1  ");
		//sql = new StringBuffer("select ancheyear from dc_nb_gt_jbxx where ifpub>1  ");
		List<String> params = new ArrayList<String>();
		if (StringUtil.isNotEmpty(pripid)) {
			sql.append("and pripid = ? order by ancheyear");
			params.add(pripid);
		}
		List<Map> list = dao.queryForList(sql.toString(), params);
		//System.out.println("===>" + list);
		return list;
	}

	// 除个体之外的年报
	@SuppressWarnings("rawtypes")
	public List getNonGtnb(String pripid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sql = new StringBuffer("select ancheyear from dc_nb_qy_jbxx where ifpub>1  ");
		
		List<String> params = new ArrayList<String>();
		if (StringUtil.isNotEmpty(pripid)) {
			sql.append("and  pripid = ? order by ancheyear");
			params.add(pripid.trim());
		}
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
			sql = new StringBuffer("select case when t.inv is not null then t.inv else s.persname end as persname from DC_RA_MER_PERSONS s ,dc_ra_mer_invest t where s.id = t.id and t.exeaffsign='1' and s.main_tb_id = ?");
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

	


}
