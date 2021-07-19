package com.gwssi.ebaic.apply.setup.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.ebaic.apply.setup.service.SetupInvService;
import com.gwssi.ebaic.common.util.SubmitUtil;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.rule.RuleUtil;
import com.gwssi.rodimus.util.ParamUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.rodimus.validate.msg.ValidateMsg;
import com.gwssi.torch.util.JSON;

@Controller
@RequestMapping("/apply/setup/inv")
public class SetupInvController {
	
	@Autowired
	SetupInvService setupInvService;
	
	
	@RequestMapping("/runRule")
	public void runRule(OptimusRequest requeest,OptimusResponse response) throws OptimusException{
		String gid = ParamUtil.get("gid");
		String entType = setupInvService.getEntType(gid);
		HashMap<String, Object> param = new HashMap<String,Object>();
		param.put("gid", gid);
		param.put("entType", entType);
		ValidateMsg ret = RuleUtil.getInstance().runApp("ebaic_setup_inv_save", param);
		
//		List<ValidateMsgEntity> listMsg=ret.getAllMsg();
//		StringBuffer sb=new StringBuffer();
//		boolean flag =true;
//		for (ValidateMsgEntity vmsg:listMsg) {
//			//如果规则错误信息不为空，说明规则不通过
//			if (StringUtils.isNotBlank(vmsg.getMsg())) {
//				System.out.println("--->"+vmsg.getName()+"---"+vmsg.getType()+"---"+vmsg.getMsg());
//				sb.append(vmsg.getMsg()).append(";");
//				flag=false;
//			}
//		}
		String msg = ret.getAllMsgString();
		boolean flag = StringUtil.isBlank(msg);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("result", flag);
		map.put("msg", msg);
		response.addResponseBody(JSON.toJSON(map));
		
		//更新be_wk_requisition表时间戳
		SubmitUtil.updateTimeStamp("be_wk_requisition", "gid", gid);
	}
	
	@RequestMapping("/getCatId")
	@ResponseBody
	public String getCatId(OptimusRequest request,OptimusResponse response) throws OptimusException{
		
		String gid = ParamUtil.get("gid");
		String catId = setupInvService.getCatId(gid);
	    return catId;
	}
	
	@RequestMapping("/getCwpInfo")
	@ResponseBody
	public void getCwpInfo(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String inv=ParamUtil.get("inv");
		String infoTab=ParamUtil.get("infoTab");
		StringBuffer sql1 = new StringBuffer();
		Map<String,Object> map=new HashMap<String,Object>();
		if(!StringUtil.isBlank(infoTab)&&"0".equals(infoTab)){
			//如果状态为【信息未填写】
				sql1.append(" SELECT E.OP_LOC, E.DOM_DISTRICT, E.DOM_STREET || E.DOM_VILLAGE || E.DOM_NO || E.DOM_BUILDING ||E.DOM_OTHER AS dom, C.B_LIC_TYPE, C.B_LIC_NO, E.ENT_NAME FROM CP_RS_ENT E, CP_RS_CERTIFICATE C WHERE C.ENT_ID = E.ENT_ID AND C.ORI_COP_SIGN = '1' AND ent_name = ?");
				Map<String, Object> row1 =DaoUtil.getInstance().queryForRow( sql1.toString(), inv);
				if(row1!=null){
				String prov=row1.get("opLoc").toString()==null?"":row1.get("opLoc").toString().substring(0, 3);
					if(!StringUtil.isBlank(prov)&&"北京市".equals(prov)){
							map.put("bLicType", (String)row1.get("bLicType")==null? "":(String)row1.get("bLicType") );
							map.put("bLicNo", (String)row1.get("bLicNo")==null ? "":(String)row1.get("bLicNo"));
							map.put("prov", "110000");
							map.put("city", (String)row1.get("domDistrict")==null ? "":(String)row1.get("domDistrict"));
							map.put("domOther", (String)row1.get("dom")==null ? "":(String)row1.get("dom"));
							map.put("infoTab", infoTab);
					}
				}
		}
		response.addResponseBody(JSON.toJSON(map));
	}
}
