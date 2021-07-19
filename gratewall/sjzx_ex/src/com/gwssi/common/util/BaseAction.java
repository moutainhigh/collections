package com.gwssi.common.util;

import org.apache.struts.action.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.actions.DispatchAction;
import com.genersoft.frame.base.ApplicationException;

/**
 * <p>Title: 国家统计数据项目-发布库管理</p>
 *
 * <p>Description: 发布库系统使用的Action基类.以后的公共功能扩充均在此类进行.</p>
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
     * 构造函数
     */
    public BaseAction() {
        super();
    }

    /**
     * Structs控制器调用函数.进行公共的逻辑处理
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
            //调用子类实现方法.
            String forward = processor(request, response);

            return mapping.findForward(forward);
        } catch (ApplicationException e) {
            //业务异常处理
            String message = e.getMessage();
            request.setAttribute("message", message);
            e.printStackTrace();
        } catch (Throwable e) {
            //其他异常处理
            String message = e.getMessage();
            request.setAttribute("exception", message);
            e.printStackTrace();
        }
        return mapping.findForward("error");
    }

    /**
     * 具本的处理方法.子类实现
     * 处理成功后,返回forward字符串. 出错时抛出异常.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return String  forward字符串.
     * @throws Exception
     */
    abstract public String processor(HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * 当前请求中所使用的数据库连接账套.
     * 此方法的目的是预防以后对账套有新的需求时扩展.
     * @param request HttpServletRequest
     * @return String
     */
    protected String getAccountType (HttpServletRequest request){
        return accountType;
    }
    static private String accountType = "1";

}
