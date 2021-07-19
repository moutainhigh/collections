package com.esp.server.web.config.upload;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import java.io.OutputStream;

/**
 * @ProjectName: espservers
 * @Auther: GERRY
 * @Date: 2018/12/2 01:54
 * @Description:
 */
public class HttpOutputMessageImpl implements HttpOutputMessage {


    private OutputStream body;
    private HttpHeaders headers;


    public HttpOutputMessageImpl(OutputStream body, HttpHeaders headers) {
        this.body = body;
        this.headers = headers;
    }

    @Override
    public OutputStream getBody() {
        return body;
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }
}

