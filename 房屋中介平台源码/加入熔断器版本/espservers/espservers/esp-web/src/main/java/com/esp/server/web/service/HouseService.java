package com.esp.server.web.service;

import com.esp.server.web.common.ApiResponse;
import com.esp.server.web.model.*;
import com.esp.server.web.util.ResponseDataUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ProjectName: espservers
 * @Auther: GERRY
 * @Date: 2018/11/29 20:19
 * @Description: 房产接口
 */
@FeignClient(name = "gateway", fallback = HouseService.HouseServiceFallback.class)
public interface HouseService {
    // 查询最新房源信息
    @GetMapping("/house/house/newest")
    ApiResponse<List<House>> getNewest();

    // 查询所有房源信息
    @PostMapping("/house/house/list")
    ApiResponse<ListResponse<House>> houseList(@RequestBody HouseQueryReq houseQueryReq);

    // 获取热门房产
    @RequestMapping("house/house/hot")
    ApiResponse<List<House>> getHotHouse(@RequestParam("size") Integer size);

    // 查询房产详情
    @GetMapping("house/house/detail")
    ApiResponse<House> houseDetail(@RequestParam("id") long id);

    // 收藏和不收藏房产
    @PostMapping("house/house/bind")
    ApiResponse bind(@RequestBody HouseUserReq req);

    // 联系经纪人（邮件）
    @PostMapping("house/house/addUserMsg")
    ApiResponse leaveMsg(UserMsg userMsg);

    // 评分
    @GetMapping("house/house/rating")
    ApiResponse houseRate(@RequestParam("rating") Double rating,@RequestParam("id") Long id);

    /////////////////// 初始化添加房产页面/////////////////
    /**
     * 获取所有的小区
     * @return
     */
    @GetMapping("house/house/allCommunities")
    public ApiResponse<List<Community>> allCommunities();

    /**
     * 获取所有的城市
     * @return
     */
    @GetMapping("house/house/allCities")
    public ApiResponse<List<City>> allCity();

    /**
     * 上传所有的房屋图片
     * @param multipartFiles
     * @return
     */
    @PostMapping(value = "house/house/uploadHouseFiles",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ApiResponse<List<String>> houseFileUpload(@RequestPart("multipartFiles") MultipartFile[] multipartFiles);

    /**
     * 上传所有的户型图片
     * @param multipartFiles
     * @return
     */
    @PostMapping(value = "house/house/uploadPlanFiles",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ApiResponse<List<String>> floorPlanUpload(@RequestPart("multipartFiles") MultipartFile[] multipartFiles);

    /**
     * 新增房产
     * @param house
     * @return
     */
    @PostMapping("house/house/add")
    ApiResponse addHouse(@RequestBody House house);

    @Component
    class HouseServiceFallback implements HouseService {
        @Override
        public ApiResponse<List<House>> getNewest() {
            return ResponseDataUtil.setResponseData("getNewest");
        }

        @Override
        public ApiResponse<ListResponse<House>> houseList(HouseQueryReq houseQueryReq) {
            return ResponseDataUtil.setResponseData("houseList");
        }

        @Override
        public ApiResponse<List<House>> getHotHouse(Integer size) {
            return ResponseDataUtil.setResponseData("getHotHouse");
        }

        @Override
        public ApiResponse<House> houseDetail(long id) {
            return ResponseDataUtil.setResponseData("houseDetail");
        }

        @Override
        public ApiResponse bind(HouseUserReq req) {
            return ResponseDataUtil.setResponseData("bind");
        }

        @Override
        public ApiResponse leaveMsg(UserMsg userMsg) {
            return ResponseDataUtil.setResponseData("leaveMsg");
        }

        @Override
        public ApiResponse houseRate(Double rating, Long id) {
            return ResponseDataUtil.setResponseData("houseRate");
        }

        @Override
        public ApiResponse<List<Community>> allCommunities() {
            return ResponseDataUtil.setResponseData("allCommunities");
        }

        @Override
        public ApiResponse<List<City>> allCity() {
            return ResponseDataUtil.setResponseData("allCity");
        }

        @Override
        public ApiResponse<List<String>> houseFileUpload(MultipartFile[] multipartFiles) {
            return ResponseDataUtil.setResponseData("houseFileUpload");
        }

        @Override
        public ApiResponse<List<String>> floorPlanUpload(MultipartFile[] multipartFiles) {
            return ResponseDataUtil.setResponseData("floorPlanUpload");
        }

        @Override
        public ApiResponse addHouse(House house) {
            return ResponseDataUtil.setResponseData("addHouse");
        }
    }
}
