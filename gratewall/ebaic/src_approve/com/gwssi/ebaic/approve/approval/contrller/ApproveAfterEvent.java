package com.gwssi.ebaic.approve.approval.contrller;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.web.TorchController;
@Service("approveAfterEvent")
public class ApproveAfterEvent implements OnEventListener {

    public void execQuery(TorchController controller,
            Map<String, String> params, QueryConfigBo editConfigBo,Object result) {
        System.out.println("execQuery after");

    }

    public void execEdit(TorchController controller,
            Map<String, String> params, EditConfigBo editConfigBo,Object result) {
        System.out.println("execQuery after");

    }

}
