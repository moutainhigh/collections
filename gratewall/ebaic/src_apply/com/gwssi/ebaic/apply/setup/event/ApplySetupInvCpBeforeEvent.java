package com.gwssi.ebaic.apply.setup.event;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.web.TorchController;

@Service("applySetupInvCpBeforeEvent")
public class ApplySetupInvCpBeforeEvent implements OnEventListener{

	public void execQuery(TorchController controller,
			Map<String, String> params, QueryConfigBo editConfigBo,
			Object result) {
		
	}

	public void execEdit(TorchController controller,
			Map<String, String> params, EditConfigBo editConfigBo, Object result) {
		
		//0.准备参数
		if(params == null || params.isEmpty()){
			throw new EBaicException("获取表单信息异常！");
		}
		StringBuffer sql = new StringBuffer();
		String inv = params.get("inv");
		String bLicNo = params.get("bLicNo");
		//String corpRpt = params.get("corpRpt");
		String contry = params.get("contry");
		//1.校验企业名称、注册号、法定代表人姓名、国别和地区 和企业结果库 做比对
		if(StringUtil.isBlank(inv)){
			throw new EBaicException("数据异常：非自然人股东名称为空。");
		}
		if(StringUtil.isBlank(bLicNo)){
			throw new EBaicException("数据异常：非自然人股东证照号码为空。");
		}
		/*if(StringUtil.isBlank(corpRpt)){
			throw new EBaicException("数据异常：非自然人股东法定代表人为空。");
		}*/
		if(StringUtil.isBlank(contry)){
			throw new EBaicException("数据异常：非自然人国别和地区为空。");
		}
		
		sql.append(" select e.ent_state,e.reg_no,e.uni_scid,e.ent_name,mbr.name from cp_rs_ent e left join cp_rs_entmember mbr on e.ent_id = mbr.ent_id  where mbr.le_rep_sign='1' and e.ent_name = ?  ");
		Map<String, Object> row = DaoUtil.getInstance().queryForRow(sql.toString(), inv);
		if(row!=null){//北京企业
			if(!"1".equals((String)row.get("entState"))){
				throw new EBaicException("当前非自然人股东，企业状态为非正常营业状态，请核对信息后保存。");
			}
			if(!bLicNo.equals((String)row.get("regNo")) && !bLicNo.equals((String)row.get("uniScid"))){
				throw new EBaicException("当前非自然人股东，证照号码和工商登记时的企业注册号/统一社会信用代码不相同，请核对信息后保存。");
			}
			/*if(!corpRpt.equals((String)row.get("name"))){
				throw new EBaicException("当前非自然人股东，法定代表人名字和工商登记时的法定代表人名字不相同，请核对信息后保存。");
			}*/
		}
		
	}
}
