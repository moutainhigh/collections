package com.gwssi.ebaic.mobile.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gwssi.ebaic.mobile.api.QuickMsgService;
import com.gwssi.ebaic.mobile.domain.QuickMsgBo;

@Service(value="quickMsgServiceImpl")
public class QuickMsgServiceImpl implements QuickMsgService {

	public List<QuickMsgBo> getTopN(String userId, String moduleId, int topN) {
		// TODO Auto-generated method stub
		return null;
	}

}
