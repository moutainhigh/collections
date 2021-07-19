package com.gwssi.ebaic.apply.setup.event;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.rodimus.expr.ExprUtil;
import com.gwssi.rodimus.upload.UploadConfigUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.torch.db.result.PageBo;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.web.TorchController;
/**
 * 预览页文件列表处理
 * 
 * @author lxb
 */
@Service("applySetupPreviewFilesAfterEvent")
public class ApplySetupPreviewFilesAfterEvent implements OnEventListener{

	
	public void execEdit(TorchController controller,
			Map<String, String> params, EditConfigBo editConfigBo, Object result) {
		
	}
	
	
	public void execQuery(TorchController controller,
			Map<String, String> params, QueryConfigBo editConfigBo,
			Object result) {
		
		PageBo pageBo =(PageBo) result;		
		List<Map<String, Object>> list = pageBo.getResult();
		if(list.isEmpty()){
			return ;
		}
		Map<String, Object> paramMap = UploadConfigUtil.prepareParams(null);
		paramMap.putAll(params);
		Iterator<Map<String,Object>> it =list.iterator();
		while(it.hasNext()){
			Map<String, Object> row = it.next();
			String fileId = StringUtil.safe2String(row.get("fileId"));
			if(StringUtils.isBlank(fileId)){ 
				it.remove();//没上传过图片的不显示
				continue;
			}
			
			String trigerExpr = StringUtil.safe2String(row.get("trigerExpr"));
			/**校验表达式**/
			if(StringUtil.isNotBlank(trigerExpr)){//表达式为空，视为真
				Object exprResult = ExprUtil.run(trigerExpr, paramMap);
				//表达式不满足的不显示
				if(exprResult==null){ // 结果为空，视为真
					it.remove();
				}else if(!(exprResult instanceof Boolean)){
					it.remove();
				}else if(Boolean.FALSE.equals(exprResult)){//表达式执行结果为FALSE
					it.remove();
				}
			}
		}
		
	}

}
