package com.gwssi.ebaic.apply.setup.event;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.ebaic.common.util.SubmitUtil;
import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.torch.domain.TorchRequest;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.util.ThreadLocalManager;
import com.gwssi.torch.web.TorchController;

@Service("applySetupContactAfterEvent")
public class ApplySetupContactAfterEvent implements OnEventListener {

	public void execQuery(TorchController controller,
			Map<String, String> params, QueryConfigBo editConfigBo,
			Object result) {
	}

	public void execEdit(TorchController controller,
			Map<String, String> param, EditConfigBo editConfigBo, Object result) {
		
		TorchRequest tReq = (TorchRequest)ThreadLocalManager.get(ThreadLocalManager.TORCH_REQUEST);
		Map<String,String>  params = tReq.getParams();
		if(params == null || params.isEmpty()){
			throw new EBaicException("数据异常，请联系管理员");
		}

		String gid = params.get("gid");
		/*String cerValFrom = params.get("applySetupEntContact_Form.cerValFrom");
		String cerValTo = params.get("applySetupEntContact_Form.cerValTo");*/
		String cerNo = params.get("applySetupEntContact_Form.cerNo");
		if(StringUtils.isBlank(gid) /*|| StringUtils.isBlank(cerValFrom)*/ 
				 || StringUtils.isBlank(cerNo)){
			throw new EBaicException("获取页面信息失败!");
		}
		
		//更新时间戳
		SubmitUtil.updateTimeStamp("be_wk_requisition", "gid", gid);
		
		/*Calendar cerValFromC = DateUtil.parseDate(cerValFrom);
		Calendar cerValToC = DateUtil.parseDate(cerValTo);*/
//		if(cerValFromC.getTimeInMillis() > cerValToC.getTimeInMillis()){
//			throw new EBaicException("证件有效期起始日期不能晚于有效期截止日期！");
//		}
		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		String now = sdf.format(new Date());
//		Calendar nowTime = DateUtil.parseDate(now);
//		if(cerValToC.getTimeInMillis()<nowTime.getTimeInMillis()){
//			throw new EBaicException("有效期日期必须大于今天!");
//		}
//		
//		int cerValFromCyear = cerValFromC.getWeekYear();
//		int cerValToCyear = cerValToC.getWeekYear();
//		if(cerValToCyear-cerValFromCyear<1){
//			throw new EBaicException("证件有效期不能短于一年!");
//		}
		
		/*String sql = " update be_wk_entcontact t set t.cer_val_from = ?,t.cer_val_to = ? where t.gid = ? and t.cer_no = ?";
		List<Object> list = new ArrayList<Object>();
		list.add(cerValFromC);
		list.add(cerValToC);
		list.add(gid);
		list.add(cerNo);
		Object [] updateParams = list.toArray();
		DaoUtil.getInstance().execute(sql, updateParams);*/
		
	}

}
