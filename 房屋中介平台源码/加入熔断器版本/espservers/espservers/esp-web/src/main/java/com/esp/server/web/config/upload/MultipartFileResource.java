package com.esp.server.web.config.upload;


import org.springframework.core.io.InputStreamResource;

import java.io.IOException;
import java.io.InputStream;

/**
 * @ProjectName: espservers
 * @Auther: GERRY
 * @Date: 2018/12/2 01:55
 * @Description:
 */
public class MultipartFileResource extends InputStreamResource {

    private String filename;
    private long size;


    public MultipartFileResource(String filename, long size, InputStream inputStream) {
        super(inputStream);
        this.size = size;
        this.filename = filename;
    }
    @Override
    public String getFilename() {
        return this.filename;
    }
    @Override
    public InputStream getInputStream() throws IOException, IllegalStateException {
        //To change body of generated methods, choose Tools | Templates.
        return super.getInputStream();
    }

    @Override
    public long contentLength(){
        return size;
    }

}

