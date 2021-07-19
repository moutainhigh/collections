package com.gwssi.common.delivery.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwssi.common.delivery.domain.CpWkDeliveryOrderBO;
import com.gwssi.common.delivery.domain.CpWkDeliveryOrderMaterialBO;
import com.gwssi.common.delivery.domain.CpWkDeliveryStatusBO;
import com.gwssi.common.delivery.domain.CpWkDeliveryStatusDetailBO;
import com.gwssi.common.delivery.domain.CpWkDeliverySyncBO;
import com.gwssi.common.delivery.remote.DeliveryRemoteService;
import com.gwssi.common.delivery.remote.DeliveryRemoteServiceFactory;
import com.gwssi.ebaic.domain.BeWkReqBO;
import com.gwssi.optimus.core.cache.dictionary.DictionaryManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.plugin.auth.model.TPtYhBO;
import com.gwssi.optimus.util.DateUtil;
import com.gwssi.rodimus.dao.BaseDaoUtil;
import com.gwssi.rodimus.dao.EmsDaoUtil;
import com.gwssi.torch.util.ParameterUtils;
import com.gwssi.torch.util.StringUtil;
import com.gwssi.torch.util.UUIDUtil;

@Service(value="deliveryService")
public class DeliveryService {
	
	@Autowired
	DeliveryRemoteServiceFactory deliveryRemoteServiceFactory;

	protected final static Logger logger = Logger.getLogger(DeliveryService.class);

	/**
	 * 用户手动创建订单。
	 * 
	 * 目前采取即时下单的方式，即申请人提交信息后，立即调用快递接口，推送数据。
	 * 
	 * @param orderBO 
	 * @param gid 
	 * @throws OptimusException
	 */
	public String createOrder(CpWkDeliveryOrderBO orderBO) throws OptimusException{
		BaseDaoUtil dao = EmsDaoUtil.getInstance();
		if(orderBO==null){
			throw new OptimusException("“快递订单信息”不能为空。");
		}
		String gid = orderBO.getGid();
		if(StringUtils.isBlank(gid)){
			throw new OptimusException("“申请单编号”不能为空。");
		}
		if(StringUtils.isBlank(orderBO.getKdgs())){
			throw new OptimusException("“快递公司编码”不能为空。");
		}
		if(StringUtils.isBlank(orderBO.getDeliveryFrom())){
			throw new OptimusException("“寄递方向”不能为空。");
		}
		// 获得remote service
		DeliveryRemoteService remote = deliveryRemoteServiceFactory.getDeliveryRemoteService(orderBO.getKdgs());
		
		Calendar now = DateUtil.getCurrentDate();
		String sql = " select * from be_wk_requisition r where r.gid=? ";
		BeWkReqBO reqBO = dao.queryForRowBo(sql, BeWkReqBO.class, gid);
		if(reqBO==null){
			throw new OptimusException("“申请单信息”不能为空。");
		}
		if(StringUtils.isBlank(reqBO.getAppUserId())){
			throw new OptimusException("“申请人编号”不能为空。");
		}
		sql = " select * from t_pt_yh y where y.user_id=? ";
		TPtYhBO yhBO = dao.queryForRowBo(sql,TPtYhBO.class, reqBO.getAppUserId());
		if(yhBO==null){
			throw new OptimusException("未找到该申请业务用户信息。");
		}
		sql = " select e.uni_scid,e.reg_no from be_wk_ent e left join be_wk_requisition r on r.ent_id = e.ent_id where r.gid=? ";
		Map<String, Object> entMap = dao.queryForRow(sql, gid);
		if(entMap==null){
			throw new OptimusException("未找到该业务的企业信息。");
		}
		//企业注册号
		String regNo = StringUtil.safe2String(entMap.get("regNo"));
		//企业统一社会信用代码
		//String uniScidS = StringUtil.safe2String(entMap.get("uniScid"));
		// 订单业务类型文本
		//String ddywlx = DictionaryManager.getData("CD01").getText(reqBO.getOperationType());
		String ddhm="";
		sql =" select * from cp_wk_delivery_status s where s.gid=? and rownum=1 order by s.timestamp desc ";
		List<CpWkDeliveryStatusBO>cpWkDeliveryStatusBOList = dao.queryForListBo(sql,CpWkDeliveryStatusBO.class, gid);
		if(cpWkDeliveryStatusBOList!=null && cpWkDeliveryStatusBOList.size()>0){
			ddhm = cpWkDeliveryStatusBOList.get(0).getDdhm();
		}else{
			//获取订单号码
			ddhm = createDmhm();
		}
		//String optTypeCh = DictionaryManager.getData("CD01").getText(reqBO.getOperationType());
		String entTypeCh = DictionaryManager.getData("CA16").getText(reqBO.getCatId());
		String optTypeCh="";
		String userType ="";
		String agentName="";
		String userName="";
		String ddywlx="";
		String staffNo = reqBO.getApproveNo();
		if(StringUtils.isBlank(staffNo)){
			staffNo = reqBO.getCensorNo();
		}
		
		if("10".equals(reqBO.getOperationType())){
			ddywlx = "20";
			optTypeCh="设立登记";
		}else if ("20".equals(reqBO.getOperationType())){
			ddywlx="30";
			optTypeCh="变更登记";
		}else if ("23".equals(reqBO.getOperationType())){
			ddywlx="40";
			optTypeCh="备案";
		}else if ("30".equals(reqBO.getOperationType())){
			ddywlx="50";
			optTypeCh="注销";
		}else if ("40".equals(reqBO.getOperationType())){
			ddywlx="65";
			optTypeCh="增减补换证照";
		}else if ("92".equals(reqBO.getOperationType())){
			ddywlx="92";
			optTypeCh="股权出质";
		}
		if(!StringUtils.isBlank(reqBO.getAgentName())){
			userType="代理机构用户";
			userName = yhBO.getLoginName();
			if(StringUtils.isNotEmpty(userName)){
				String[] nameArray = userName.split("-");
				if(nameArray!=null && nameArray.length>0){
					agentName = nameArray[0];
				}
			}
		}else{
			userType="普通用户";
		}
		String entName = reqBO.getEntName();
		//构造订单数据
		if(StringUtils.isNotBlank(entName) && entName.length()<26){
			orderBO.setDdmc(reqBO.getEntName()+"业务订单");
		}else{
			orderBO.setDdmc("业务订单");
		}
		//orderBO.setDdmc("北京工商网上登记-"+reqBO.getEntName()+"("+ddywlx+")业务订单");//长度不够
		orderBO.setOrderId(UUIDUtil.getUUID());//订单编号
		orderBO.setDdhm(ddhm);	//订单号码
		orderBO.setDdsljg(reqBO.getRegOrg());// 订单受理机构
		orderBO.setSmqf("1"); //上门收件，单程 
		orderBO.setGid(gid);
		orderBO.setTimestamp(now);
		orderBO.setPsyxj("01");//配送优先级 01-普通；02-高
		orderBO.setDdywlx(ddywlx); // 订单业务类型代码
		orderBO.setDshk("0"); // 代收货款，0-不代收
		orderBO.setDsje("0"); // 代收金额
		orderBO.setDdzt("2"); // 订单状态 1-网申通过；2-内审通过
		orderBO.setSjrSj(orderBO.getSjrDh()); // 收件人电话
		orderBO.setSjrDw(reqBO.getEntName());
		orderBO.setBz(agentName);
		orderBO.setYlzd1(regNo);
		orderBO.setYlzd2(optTypeCh);
		orderBO.setYlzd3(entTypeCh);
		if(!StringUtils.isBlank(staffNo)){
			userType = userType+","+staffNo;
		}
		orderBO.setYlzd4(userType);
		// 构造快递订单明细数据（即有哪些货物）
		List<CpWkDeliveryOrderMaterialBO> materialList = getOrderMaterialList(orderBO,reqBO);
		
		// 调用remote service下单
		String retCode = remote.createOrder(gid,orderBO,materialList); //返回订单状态代码
		// 构造数据同步记录
		CpWkDeliverySyncBO syncBO = new CpWkDeliverySyncBO();
		syncBO.setId(UUIDUtil.getUUID());
		syncBO.setType("1"); // TODO EMS 待定义
		syncBO.setBusiSn(orderBO.getOrderId());//快递单号
		syncBO.setSentStatus(null);// -1-待重试，0-待办，1-持续获取更新信息，2-进行中，9-完成
		syncBO.setSentTime(null);
		syncBO.setSentRetryCnt(null);
		syncBO.setFetchStatus("1");// -1-待重试，0-待办，1-持续获取更新信息，2-进行中，9-完成
		syncBO.setFetchTime(now);
		syncBO.setFetchRetryCnt(BigDecimal.ZERO);
		syncBO.setDdhm(orderBO.getDdhm());
		// 更新快递状态
		reqBO.setCertReceiveForm("ems");
		reqBO.setTimestamp(now);
		//邮件状态创建记录后续制作更新
		CpWkDeliveryStatusBO statusBO = new CpWkDeliveryStatusBO();
		statusBO.setTimestamp(now);
		statusBO.setDdhm(orderBO.getDdhm());
		//此字段为必填项，所给状态中无对应状态 建议添加状态“09”，下订单成功
		statusBO.setPsqk("09");
		statusBO.setYjzt("下订单成功");
		statusBO.setGid(gid);
		//reqBO.setDeliveryStatus(busiStatus);
		// 数据库操作
		dao.insert(orderBO);
		for (CpWkDeliveryOrderMaterialBO cpWkDeliveryOrderMaterialBO : materialList) {
			dao.insert(cpWkDeliveryOrderMaterialBO);
		}
		dao.insert(syncBO);
		
		if(cpWkDeliveryStatusBOList!=null && cpWkDeliveryStatusBOList.size()>0){
			dao.update(statusBO);
		}else{
			dao.insert(statusBO);
		}
		dao.update(reqBO);
		
		return retCode;
	}
	
	/**
	 * 获取订单相关的物品信息
	 * 
	 * @param ddywlx
	 * @param reqId
	 * @param now
	 * @return
	 * @throws OptimusException
	 */
	public List<CpWkDeliveryOrderMaterialBO> getOrderMaterialList(CpWkDeliveryOrderBO orderBO,BeWkReqBO reqBO) 
			throws OptimusException{
		List<CpWkDeliveryOrderMaterialBO> materialList = new ArrayList<CpWkDeliveryOrderMaterialBO>();
		
		if("0".equals(orderBO.getDeliveryFrom())){	//	邮件方向：0-寄给工商局；1-寄给用户
			
			CpWkDeliveryOrderMaterialBO material = new CpWkDeliveryOrderMaterialBO();
			String njhm = UUIDUtil.getLongId()+"";//长度为30
			material.setDdhm(orderBO.getDdhm());//	订单号码，外键
			material.setNjhm(njhm);//	主键
			material.setNjxh("1"); //	内件序号
			material.setNjlx("01");// 	内件类型	
			material.setGid(orderBO.getGid());
			material.setTimestamp(orderBO.getTimestamp());
			material.setNjmc("工商登记申请材料");
			material.setNjsl("");//		内件数量
			material.setNjzl("");//		内件重量
			material.setOrderId(orderBO.getOrderId());
			materialList.add(material);
			
		}else if("1".equals(orderBO.getDeliveryFrom())){
			
			//	证照总数
			String sql = " select count(1) from VIEW_DBLINK_CP_RS_CERTIFICATE t left join cp_wk_ent r on r.reg_no = t.b_lic_no where r.ent_id=? ";
			
			long sumAmount = EmsDaoUtil.getInstance().queryForOneLong(sql,reqBO.getEntId());
			
			// 正本数量
			//	sql = " select count(1) from cp_rs_certificate t left join cp_wk_requisition r on r.reg_no = t.b_lic_no where r.requisition_id=? and t.ori_cop_sign='1' ";
			//	int zbAmount = super.getPersistenceDAO().queryForInt(sql, param);
			
			CpWkDeliveryOrderMaterialBO material = new CpWkDeliveryOrderMaterialBO();
			String njhm = UUIDUtil.getLongId()+"";//长度为30
			material.setNjhm(njhm);	//主键
			material.setNjxh("1");//	内件序号
			material.setNjlx("01");// 	内件类型	
			material.setDdhm(orderBO.getDdhm());//	订单号码，外键
			material.setGid(orderBO.getGid());
			material.setTimestamp(orderBO.getTimestamp());
			material.setNjmc("企业营业执照");
			material.setNjsl(String.valueOf(sumAmount));//内件数量
			material.setNjzl("");//内件重量
			material.setOrderId(orderBO.getOrderId());
			materialList.add(material);
		}else{
			throw new OptimusException("不支持的寄件方向。");
		}
		return materialList;
	}
	

	
	/**   以下查询方法，只从数据库查询     **/
	
	/**
	 * 
	 * @param ddhm
	 * @return
	 * @throws OptimusException
	 */
	public CpWkDeliveryOrderBO queryOrder(String ddhm) throws OptimusException{
		if(StringUtils.isBlank(ddhm)){
			throw new OptimusException("订单号码不能为空。");
		}
		String sql = "select t.* from CP_WK_DELIVERY_ORDER t where t.DDHM=?";
		CpWkDeliveryOrderBO retBo = EmsDaoUtil.getInstance().queryForRowBo(sql, CpWkDeliveryOrderBO.class, ddhm);
		return retBo;
	}
	/**
	 * 一笔业务只有一个订单。
	 * @param gid
	 * @return
	 * @throws OptimusException
	 */
	public List<CpWkDeliveryStatusBO> queryOrderStatusByGid(String gid) throws OptimusException{
		if(StringUtils.isBlank(gid)){
			throw new OptimusException("“gid”不能为空。");
		}
		String sql = "select t.* from CP_WK_DELIVERY_STATUS t where t.gid=? order by DDHM ASC";
		List<CpWkDeliveryStatusBO> list = EmsDaoUtil.getInstance().queryForListBo(sql, CpWkDeliveryStatusBO.class, gid);
		return list;
	}
	/**
	 * 根据订单编号加载单个订单的状态。
	 * @param ddhm
	 * @return
	 * @throws OptimusException
	 */
	public CpWkDeliveryStatusBO queryOrderStatus(String ddhm) throws OptimusException{
		String sql = "select t.* from CP_WK_DELIVERY_STATUS t where t.DDHM=?";
		CpWkDeliveryStatusBO retBo  = EmsDaoUtil.getInstance().queryForRowBo(sql, CpWkDeliveryStatusBO.class, ddhm);
		return retBo;
	}
	/**
	 * 根据订单编号，加载订单处理记录（物流流水信息），按时间倒序显示。
	 * @param ddhm
	 * @return
	 * @throws OptimusException
	 */
	public List<CpWkDeliveryStatusDetailBO> queryOrderStatusDetail(String gid) throws OptimusException{
		StringBuffer sql = new StringBuffer(" select s.detail_id,s.yjzt,s.yjhm,s.ddhm,s.bz,to_char(to_date(s.clsj,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd hh24:mi:ss')as clsj ");
		sql.append(" from cp_wk_delivery_status_detail s, ");
		sql.append(" (select distinct o.ddhm from cp_wk_delivery_order o where o.gid = ?) d ");
		sql.append(" where s.ddhm=d.ddhm order by s.clsj desc");
		List<CpWkDeliveryStatusDetailBO> list = EmsDaoUtil.getInstance().queryForListBo(sql.toString(), CpWkDeliveryStatusDetailBO.class, gid);
		return list;
	}
	
	//单程寄递前缀，计数与双向寄递数量分开
	public static final String PREFIX_SINGLE = "S_";
	/**
	 * 获取订单号码  当前日期+四位数字(例如：201505220001)
	 * 
	 * @return
	 * @throws OptimusException
	 */
	public static synchronized String createDmhm() throws OptimusException{
		BaseDaoUtil dao = EmsDaoUtil.getInstance();
		/*String sql = "select to_char(trunc(sysdate),'yyyymmdd')*10000+ddhm.nextval as ddhm from dual";
		int ddhm = dao.queryForInt(sql, null);
		if(0==ddhm){
			throw new OptimusException("生成订单号码失败，请稍后重试。 ");
		}*/
		String sql="select nvl(max(to_number(substr(ddhm,-4))),0) as maxno from cp_wk_delivery_status t where ddhm like '"+PREFIX_SINGLE+"' || to_char(sysdate, 'yyyymmdd')||'%' ";
		long maxIndex=dao.queryForOneLong(sql);
		String no=(maxIndex+1)+"";
		int len=no.length();
		if(no.length()<4){
			for(int i=0;i<4-len;i++){
				no="0"+no;
			}
		}
		String currDateStr=DateUtil.toDateStr(Calendar.getInstance(), 11);
		String ddhm=PREFIX_SINGLE+currDateStr+no;
		return String.valueOf(ddhm);
		
//		String sql="select nvl(max(to_number(substr(ddhm,9))),0) as maxno from cp_wk_delivery_status t where ddhm like to_char(sysdate, 'yyyymmdd')||'%' ";
//		long maxIndex=dao.queryForOneLong(sql);
//		String no=(maxIndex+1)+"";
//		int len=no.length();
//		if(no.length()<4){
//			for(int i=0;i<4-len;i++){
//				no="0"+no;
//			}
//		}
//		String currDateStr=DateUtil.toDateStr(Calendar.getInstance(), 11);
//		String ddhm=currDateStr+no;
//		return String.valueOf(ddhm);
	}
	/**
	 * 构造订单信息
	 * 
	 * @return
	 */
	public CpWkDeliveryOrderBO prepareOrderMsg(String gid)throws OptimusException{
		if(StringUtils.isBlank(gid)){
			throw new OptimusException("“gid”不能为空。");
		}
		//查询用户信息
		StringBuffer sql = new StringBuffer(" select d.wb as jjr_xm, r.kdgs, r.ddsljg, r.jdd_csdm, r.sfd_csdm, ");
		sql.append(" r.receiver_name as sjr_xm,r.sjr_dh,r.receiver_mobile as sjr_sj, ");
		sql.append(" r.receiver_dom as sjr_dz,p.org_addr as jjr_dz,p.org_tel as jjr_dh ");
		sql.append(" from be_wk_post_receiver r left join system_org_post p ");
		sql.append(" on p.org_id = r.ddsljg left join t_pt_dmsjb d ");
		sql.append(" on d.dm = r.ddsljg and d.dmb_id = 'MC_DJSPJG' ");
		sql.append(" where r.gid = ? and r.flag = '1' ");
		Map<String,Object> orderMap = EmsDaoUtil.getInstance().queryForRow(sql.toString(), gid);
		if(orderMap == null){
			throw new OptimusException("未找到订单信息。");
		}
		//CpWkDeliveryOrderBO orderBO = new CpWkDeliveryOrderBO();
		CpWkDeliveryOrderBO orderBO = ParameterUtils.map2Bo(CpWkDeliveryOrderBO.class, orderMap);
		if(orderBO==null){
			throw new OptimusException("未找到订单信息。");
		}
		orderBO.setDeliveryFrom("1");// 寄递方向：0-寄给工商局；1-寄给用户
		orderBO.setGid(gid);
		if(StringUtils.isBlank(orderBO.getSjrSj())){
			orderBO.setSjrSj(orderBO.getSjrDh());
		}
		if(StringUtils.isBlank(orderBO.getSjrDh())){
			orderBO.setSjrDh(orderBO.getSjrSj());
		}
		if(StringUtils.isBlank(orderBO.getSjrDh())||
		   StringUtils.isBlank(orderBO.getSjrSj())){
			throw new OptimusException("收件人联系方式不能为空。");
		}
		return orderBO;
	}
	/**
	 * 查询是否已经下过订单
	 * 
	 * @param gid
	 * @return
	 */
	public boolean noOrder(String gid)throws OptimusException{
		if(StringUtils.isBlank(gid)){
			throw new OptimusException("“gid”不能为空。");
		}
		String sql = " select count(1) from cp_wk_delivery_order o where o.gid=? and (o.sent_status is null or o.sent_status<>'-1') ";
		long cnt = EmsDaoUtil.getInstance().queryForOneLong(sql, gid);
		return cnt==0;
	}
}
