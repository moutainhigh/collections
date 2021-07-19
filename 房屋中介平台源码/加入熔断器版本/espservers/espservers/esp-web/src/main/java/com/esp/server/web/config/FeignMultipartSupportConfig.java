package com.esp.server.web.config;

import com.esp.server.web.config.upload.SpringMultipartEncoder;
import feign.codec.Encoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

/**
 * @ProjectName: espservers
 * @Auther: GERRY
 * @Date: 2018/12/2 01:57
 * @Description:
 */
@Configuration // 配置能实现多文件和单文件上传的类
public class FeignMultipartSupportConfig {
    /**
     * Multipart form encoder.
     *
     * @return the encoder
     */
    @Bean
    @Primary
    @Scope("prototype")
    public Encoder multipartFormEncoder() {
        return new SpringMultipartEncoder();
    }


    /**
     * Multipart logger level feign . logger . level.
     *
     * @return the feign . logger . level
     */
    @Bean
    public feign.Logger.Level multipartLoggerLevel() {
        return feign.Logger.Level.FULL;
    }


}

