package com.gwssi.ebaic.approve.process.event;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.ebaic.torch.event.BaseEventListener;
import com.gwssi.rodimus.exception.EBaicException;
@Service("processBeforeEvent")
public class ProcessBeforeEvent extends BaseEventListener {

    @Override
    public void exec(Map<String, String> formData) {
        String gid = formData.get("gid");
        if(StringUtils.isBlank(gid)) {
            throw new EBaicException("业务流水号不能为空!");
        }
        //ProcessUtil.generatorBeWkReqprocess(gid);
    }

}
