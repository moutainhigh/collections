package com.esp.server.web.service;

import com.esp.server.web.common.ApiResponse;
import com.esp.server.web.model.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ProjectName: espservers
 * @Auther: GERRY
 * @Date: 2018/11/29 20:19
 * @Description: 房产接口
 */
@FeignClient(name = "gateway")
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
}
