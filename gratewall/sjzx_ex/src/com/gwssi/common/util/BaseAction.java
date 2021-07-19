package com.gwssi.common.util;

import org.apache.struts.action.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.actions.DispatchAction;
import com.genersoft.frame.base.ApplicationException;

/**
 * <p>Title: ����ͳ��������Ŀ-���������</p>
 *
 * <p>Description: ������ϵͳʹ�õ�Action����.�Ժ�Ĺ�������������ڴ������.</p>
 *
 * <p>Copyright: Copyright (c) 2007.8</p>
 *
 * <p>Company: gwssi</p>
 *
 * @author chenzw
 * @version 1.0
 */
public abstract class BaseAction extends DispatchAction {


    /**
     * ���캯��
     */
    public BaseAction() {
        super();
    }

    /**
     * Structs���������ú���.���й������߼�����
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                    HttpServletRequest request, HttpServletResponse response)throws Exception {

        try {
            //��������ʵ�ַ���.
            String forward = processor(request, response);

            return mapping.findForward(forward);
        } catch (ApplicationException e) {
            //ҵ���쳣����
            String message = e.getMessage();
            request.setAttribute("message", message);
            e.printStackTrace();
        } catch (Throwable e) {
            //�����쳣����
            String message = e.getMessage();
            request.setAttribute("exception", message);
            e.printStackTrace();
        }
        return mapping.findForward("error");
    }

    /**
     * �߱��Ĵ�����.����ʵ��
     * ����ɹ���,����forward�ַ���. ����ʱ�׳��쳣.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return String  forward�ַ���.
     * @throws Exception
     */
    abstract public String processor(HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * ��ǰ��������ʹ�õ����ݿ���������.
     * �˷�����Ŀ����Ԥ���Ժ���������µ�����ʱ��չ.
     * @param request HttpServletRequest
     * @return String
     */
    protected String getAccountType (HttpServletRequest request){
        return accountType;
    }
    static private String accountType = "1";

}
