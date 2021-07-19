package com.esp.server.web.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * @ProjectName: microservice-house
 * @Auther: GERRY
 * @Date: 2018/11/8 20:47
 * @Description:
 */
@Controller
public class ApiErrorExceptionController implements ErrorController {

    public static final String ERROR_PATH = "/error";

    @Autowired
    private ErrorAttributes errorAttributes;

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    /**
     * web错误的处理方法
     * @return
     */
    @RequestMapping(value =ERROR_PATH, produces = "text/html")
    public String errorHandler(HttpServletResponse response) {
        int status = response.getStatus();

        switch (status) {
            case 403:
                return "403";
            case 404:
                return "404";
            case 500:
                return "500";
        }

        return "index";
    }
}
