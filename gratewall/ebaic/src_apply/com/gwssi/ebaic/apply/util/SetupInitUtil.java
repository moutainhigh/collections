package com.gwssi.ebaic.apply.util;

import java.util.ArrayList;
import java.util.List;

import com.gwssi.optimus.core.persistence.jdbc.StoredProcParam;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.util.HttpSessionUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.torch.util.UUIDUtil;

/**
 * 设立业务初始化。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class SetupInitUtil {
	/**
	 * 设立业务初始化。
	 */
	public static String cpSetupInit(String nameId) {
		String userId = HttpSessionUtil.getCurrentUserId();
		if(StringUtil.isBlank(userId)){
			throw new EBaicException("登录超时，请重新登录。");
		}
		String gid = UUIDUtil.getUUID();
		List<StoredProcParam> params = new ArrayList<StoredProcParam>();
		params.add(new StoredProcParam(1, gid, StoredProcParam.IN, oracle.jdbc.OracleTypes.VARCHAR));
		params.add(new StoredProcParam(2, nameId, StoredProcParam.IN, oracle.jdbc.OracleTypes.VARCHAR));
		params.add(new StoredProcParam(3, userId , StoredProcParam.IN, oracle.jdbc.OracleTypes.VARCHAR));
		DaoUtil.getInstance().callStoreProcess("{call PROC_BE_CP_SETUP_INIT(?,?,?)}", params);
		return gid;
	}
}
