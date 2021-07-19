package com.ye.stock.tushare.openfeign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name = "rq",url = "http://api.waditu.com")
public interface OpenFeignService {

    @PostMapping(value = "/")
    public String getInfos(@RequestBody ReqJson reqJson);

}
