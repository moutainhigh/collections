package com.esp.server.userserver.controller;

import com.esp.server.userserver.common.ApiResponse;
import com.esp.server.userserver.domain.Agency;
import com.esp.server.userserver.domain.User;
import com.esp.server.userserver.model.ListResponse;
import com.esp.server.userserver.model.PageParams;
import com.esp.server.userserver.service.AgencyService;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ProjectName: espservers
 * @Auther: GERRY
 * @Date: 2018/11/30 14:21
 * @Description:
 */
@RestController
@RequestMapping("agency")
public class AgencyController {

    @Autowired
    private AgencyService agencyService;

    /**
     * 查询所有的经纪人列表
     * @param limit
     * @param offset
     * @return
     */
    @GetMapping("agentList")
    public ApiResponse<ListResponse<User>> houseList(Integer limit, Integer offset) {
        PageParams pageParams = new PageParams();
        pageParams.setLimit(limit);
        pageParams.setOffset(offset);
        Pair<List<User>, Long> pair = agencyService.getAllAgent(pageParams);

        return ApiResponse.ofSuccess(ListResponse.build(pair.getKey(), pair.getValue()));
    }

    /**
     * 查询房产的经纪人详情
     * @param id
     * @return
     */
    @GetMapping("detail")
    public ApiResponse<User> getAgencyDetail(Long id) {
        User agentDetail = agencyService.getAgentDetail(id);

        return ApiResponse.ofSuccess(agentDetail);
    }

    /**
     * 创建经纪机构
     * @param agency
     * @return
     */
    @PostMapping("create")
    public ApiResponse create(@RequestBody Agency agency) {
        // 添加机构
        agencyService.add(agency);

        return ApiResponse.ofSuccess();
    }

    /**
     * 查询所有的经纪机构
     * @return
     */
    @GetMapping("list")
    public ApiResponse<List<Agency>> list() {
        // 添加机构
        List<Agency> allAgency = agencyService.getAllAgency();

        return ApiResponse.ofSuccess(allAgency);
    }

    /**
     * 查询经纪机构详情
     * @param id
     * @return
     */
    @GetMapping("AgencyDetail")
    public ApiResponse<Agency> detail(Integer id) {
        Agency agency = agencyService.getAgency(id);

        return ApiResponse.ofSuccess(agency);
    }
}
