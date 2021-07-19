package com.ye.http.httpwrap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {


    @RequestMapping("/")
    public String doTest(String url){

        HttpUtils.doGetAsyn(url, new HttpUtils.CallBack() {
            @Override
            public void onRequestComplete(String result) {
                System.out.println(result);

            }
        });
        return  "123";

    }
}
