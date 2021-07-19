package com.gwssi.ebaic.apply.setup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.ebaic.apply.util.EntranceUtil;
import com.gwssi.ebaic.apply.util.SetupInitUtil;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.util.HttpSessionUtil;
import com.gwssi.rodimus.util.StringUtil;

/**
 * 设立入口。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Service("setupEntranceService")
public class SetupEntranceService {
	
	//准备全局变量 非本账户申请的名称查询时 用以申请的名称还有三天到期的校验和赋值 赋值后  改校验走完后执行初始化时从此取值
	private String nameId = null;
	private String flag = null;
	private String notNo = null;
	
	/**
	 * 根据企业名称和申请人证件号码校验方法提取 设立全局变量 在校验申请的名称是否还有三天到期时 将全局变量赋值 之后做其他校验 如执行规则,初始化等
	 * @param entName
	 * @param cerNo
	 */
	public void value2Variable(String entName, String cerNo){
		String userId = HttpSessionUtil.getCurrentUserId();
		if(StringUtil.isBlank(userId)){
			throw new EBaicException("登录超时，请重新登录。");
		}
		// 0. 检查名称预先核准文号与申请人证件号码是否匹配
		StringBuffer sql = new StringBuffer();
		sql.append(" select distinct nm.name_id,nrt.cer_no,nm.ent_name, nm.not_no, to_char(save_per_to,'yyyy-mm-dd') as  save_per_to, case when to_char(nm.save_per_to,'yyyy-mm-dd') >= to_char(sysdate,'yyyy-mm-dd') then '1' else '0' end as flag,nrt.transact_auth ")
		   .append(" from nm_rs_name nm left join nm_rs_transact nrt on nm.name_id = nrt.name_id left join nm_rs_selfnameinfo si ")
		   .append(" on nm.name_id = si.name_id where nm.prted_name_id is null ")
		   .append(" and nm.name_state_co = '11' and nrt.busi_type_co in ('10','12','13','1E') ")
		   .append(" and nrt.cer_no = ? and nm.ent_name = ? ");
		List<Map<String,Object>> list = DaoUtil.getInstance().queryForList(sql.toString(),cerNo, entName );
		if(list==null || list.size()==0){
			throw new EBaicException("企业名称与申请人证件号码不匹配。");
		}	
		
		for(Map <String,Object> nameInfo:list ){
			if(nameInfo==null || nameInfo.isEmpty()){
				throw new EBaicException("根据输入的信息不能找到名称数据，请确认是否已经获得了核准的名称。");
			}
			nameId = StringUtil.safe2String(nameInfo.get("nameId"));
			if(StringUtil.isBlank(nameId)){
				throw new EBaicException("通过该身份证号和企业名称，查询到结果库中nameId为空。");
			}
			notNo = StringUtil.safe2String(nameInfo.get("notNo"));
			if(StringUtil.isBlank(notNo)){
				throw new EBaicException("通过该身份证号和企业名称，查询到结果库中预先核准文号为空。");
			}
			flag = StringUtil.safe2String(nameInfo.get("flag"));
			if("0".equals(flag)){//过期
				throw new EBaicException("该名称已经过期，请重新申请。");
			}
			String transactAuth = StringUtil.safe2String(nameInfo.get("transactAuth"));
			if("110000000".equals(transactAuth)){
				throw new EBaicException("该名称核准机关是北京市工商局，不能够在分局办理。");
			}
		}
	}

	/**
	 * @return gid
	 */
	public String cpCheckIn()  {
//			String userId = HttpSessionUtil.getCurrentUserId();
//			if(StringUtil.isBlank(userId)){
//				throw new EBaicException("登录超时，请重新登录。");
//			}
//			// 0. 检查名称预先核准文号与申请人证件号码是否匹配
//			StringBuffer sql = new StringBuffer();
//			sql.append(" select distinct nm.name_id,nrt.cer_no,nm.ent_name, nm.not_no, to_char(save_per_to,'yyyy-mm-dd') as  save_per_to, case when to_char(nm.save_per_to,'yyyy-mm-dd') >= to_char(sysdate,'yyyy-mm-dd') then '1' else '0' end as flag,nrt.transact_auth ")
//			   .append(" from nm_rs_name nm left join nm_rs_transact nrt on nm.name_id = nrt.name_id left join nm_rs_selfnameinfo si ")
//			   .append(" on nm.name_id = si.name_id where nm.prted_name_id is null ")
//			   .append(" and nm.name_state_co = '11' and nrt.busi_type_co in ('10','12','13','1E') ")
//			   .append(" and nrt.cer_no = ? and nm.ent_name = ? ");
//			List<Map<String,Object>> list = DaoUtil.getInstance().queryForList(sql.toString(),cerNo, entName );
//			if(list==null || list.size()==0){
//				throw new EBaicException("企业名称与申请人证件号码不匹配。");
//			}	
//			String nameId = null;
//			String flag = null;
//			String notNo = null;
//			for(Map <String,Object> nameInfo:list ){
//				if(nameInfo==null || nameInfo.isEmpty()){
//					throw new EBaicException("根据输入的信息不能找到名称数据，请确认是否已经获得了核准的名称。");
//				}
//				nameId = StringUtil.safe2String(nameInfo.get("nameId"));
//				if(StringUtil.isBlank(nameId)){
//					throw new EBaicException("通过该身份证号和企业名称，查询到结果库中nameId为空。");
//				}
//				notNo = StringUtil.safe2String(nameInfo.get("notNo"));
//				if(StringUtil.isBlank(notNo)){
//					throw new EBaicException("通过该身份证号和企业名称，查询到结果库中预先核准文号为空。");
//				}
//				flag = StringUtil.safe2String(nameInfo.get("flag"));
//				if("0".equals(flag)){//过期
//					throw new EBaicException("该名称已经过期，请重新申请。");
//				}
//				String transactAuth = StringUtil.safe2String(nameInfo.get("transactAuth"));
//				if("110000000".equals(transactAuth)){
//					throw new EBaicException("该名称核准机关是北京市工商局，不能够在分局办理。");
//				}
//			}
			
			// 1. 检查当前登录用户是否已经开始做设立了,如果有那么返回跳到该笔业务。
			String sqlBe = "select r.gid from be_wk_requisition r where r.name_app_id = ? and r.operation_type='10' and r.state ='0' ";
			String gid = DaoUtil.getInstance().queryForOneString(sqlBe,notNo);
			if(StringUtil.isNotBlank(gid)){
				return gid;
			}
			
			// 2. 执行入口规则，包含名称在办业务
			EntranceUtil.cpCheckIn(nameId);
			// 3. 执行设立初始化
			gid = SetupInitUtil.cpSetupInit(nameId);
			
			return gid;
	}
	/**
	 * @param notNo
	 * @param cerNo
	 * @return gid
	 */
	public String cpNameListEnter(String nameId) {
		String gid = "";
		// 1. 执行入口规则，包含名称在办业务
		EntranceUtil.cpCheckIn(nameId);
		// 2. 执行设立初始化
		gid = SetupInitUtil.cpSetupInit(nameId);
		
		return gid;
	}
	
	
	/**
	 * 公司设立起名的有效期校验     待办名称列表中的进入办理处理事件
	 * @param nameId
	 * @return 
	 */
	public String checkEntValidTime(String nameId){
		String sql = "select  (select save_per_to from NM_RS_NAME  where name_id = ?) - to_date(to_char(sysdate, 'yyyy-MM-dd'), 'yyyy-mm-dd') from dual";
		String result = DaoUtil.getInstance().queryForOneString(sql,nameId);
		return result;
	}
	
	
	/**
	 *  公司设立起名的有效期校验    非本账户申请
	 * @param entName
	 * @param cerNo
	 * @return
	 */
	public String checkEntValidTime2(String entName, String cerNo) {
//		
//		StringBuffer sql = new StringBuffer();
//		sql.append(" select distinct nm.name_id,nrt.cer_no,nm.ent_name, nm.not_no, to_char(save_per_to,'yyyy-mm-dd') as  save_per_to, case when to_char(nm.save_per_to,'yyyy-mm-dd') >= to_char(sysdate,'yyyy-mm-dd') then '1' else '0' end as flag,nrt.transact_auth ")
//		   .append(" from nm_rs_name nm left join nm_rs_transact nrt on nm.name_id = nrt.name_id left join nm_rs_selfnameinfo si ")
//		   .append(" on nm.name_id = si.name_id where nm.prted_name_id is null ")
//		   .append(" and nm.name_state_co = '11' and nrt.busi_type_co in ('10','12','13','1E') ")
//		   .append(" and nrt.cer_no = ? and nm.ent_name = ? ");
//		
//		List<Map<String,Object>> list = DaoUtil.getInstance().queryForList(sql.toString(),cerNo, entName );
//		if(list==null || list.size()==0){
//			throw new EBaicException("企业名称与申请人证件号码不匹配。");
//		}	
//		
//		String nameId = null;
//		
//		for(Map <String,Object> nameInfo:list ){
//			nameId = StringUtil.safe2String(nameInfo.get("nameId"));
//			if(StringUtil.isBlank(nameId)){
//				throw new EBaicException("通过该身份证号和企业名称，查询到结果库中nameId为空。");
//			}
//		}
		
		value2Variable(entName,cerNo);
		String sql1 = "select (select save_per_to from NM_RS_NAME  where name_id = ?) - to_date(to_char(sysdate, 'yyyy-MM-dd'), 'yyyy-mm-dd') from dual";
		String result = DaoUtil.getInstance().queryForOneString(sql1,nameId);
		
		return result;
	}
}
