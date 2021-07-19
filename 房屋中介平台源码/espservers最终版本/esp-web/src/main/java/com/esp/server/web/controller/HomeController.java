package com.esp.server.web.controller;

import com.esp.server.web.common.ApiResponse;
import com.esp.server.web.model.House;
import com.esp.server.web.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @ProjectName: espservers
 * @Auther: GERRY
 * @Date: 2018/11/27 19:02
 * @Description: 主页
 */
@Controller
public class HomeController {

    @Autowired(required = false)
    private HouseService houseService;

    @GetMapping("/index")
    public String index(ModelMap modelMap) {
        // 获取最新房源信息列表
        ApiResponse<List<House>> apiResponse = houseService.getNewest();
        List<House> data = apiResponse.getData();
        modelMap.put("newestHouses", data);

        return "index";
    }

    @GetMapping("")
    public String goIndex() {
        return "redirect:/index";
    }
}
