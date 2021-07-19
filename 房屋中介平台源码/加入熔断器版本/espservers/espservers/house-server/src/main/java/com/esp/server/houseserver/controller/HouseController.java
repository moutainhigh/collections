package com.esp.server.houseserver.controller;

import com.esp.server.houseserver.common.ApiResponse;
import com.esp.server.houseserver.constant.HouseState;
import com.esp.server.houseserver.constant.HouseUserType;
import com.esp.server.houseserver.domain.City;
import com.esp.server.houseserver.domain.Community;
import com.esp.server.houseserver.domain.House;
import com.esp.server.houseserver.domain.UserMsg;
import com.esp.server.houseserver.model.*;
import com.esp.server.houseserver.service.HouseService;
import com.esp.server.houseserver.service.QiNiuService;
import com.esp.server.houseserver.service.RecommendService;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.qiniu.http.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
public class HouseController {
  
  @Autowired
  private HouseService houseService;
  
  @Autowired
  private RecommendService recommendService;

  @Autowired
  private QiNiuService qiNiuService;

  @Autowired
  private Gson gson;

  /**
   * 根据条件分页查询房产信息列表的方法
   * @param houseQueryReq
   * @return
   */
  @PostMapping("house/list")
  public ApiResponse<ListResponse<House>> houseList(@RequestBody HouseQueryReq houseQueryReq) {
    Integer limit = houseQueryReq.getLimit();
    Integer offset = houseQueryReq.getOffset();
    House query = houseQueryReq.getQuery();
    Pair<List<House>, Long> houseList = houseService.queryHouse(query, LimitOffset.build(limit, offset));

    return ApiResponse.ofSuccess(ListResponse.build(houseList.getKey(), houseList.getValue()));
  }

  /**
   * 房产详情
   * @param id
   * @return
   */
  @GetMapping("house/detail")
  public ApiResponse<House> houseDetail(long id){
    House house = houseService.queryOneHouse(id);
    // 增加房产热度
    recommendService.increaseHot(id);
    return ApiResponse.ofSuccess(house);
  }

  /**
   * 向经济人发送信息
   * @param userMsg
   * @return
   */
  @PostMapping("house/addUserMsg")
  public ApiResponse<Object> houseMsg(@RequestBody UserMsg userMsg){
    houseService.addUserMsg(userMsg);
    return ApiResponse.ofSuccess();
  }

  /**
   * 房屋评分
   * @param rating
   * @param id
   * @return
   */
  @GetMapping("house/rating")
  public ApiResponse<Object> houseRate(Double rating,Long id){
    houseService.updateRating(id,rating);
    return ApiResponse.ofSuccess();
  }


  /**
   * 获取所有的小区
   * @return
   */
  @GetMapping("house/allCommunities")
  public ApiResponse<List<Community>> allCommunities(){
    List<Community> list = houseService.getCommunities();
    return ApiResponse.ofSuccess(list);
  }

  /**
   * 获取所有的城市
   * @return
   */
  @GetMapping("house/allCities")
  public ApiResponse<List<City>> allCity(){
    List<City> list = houseService.getCities();
    return ApiResponse.ofSuccess(list);
  }

  /**
   * 上传所有的房屋图片
   * @param houseFiles
   * @return
   */
  @PostMapping(value = "house/uploadHouseFiles")
  public ApiResponse<List<String>> houseFileUpload(@RequestParam("multipartFiles") MultipartFile[] houseFiles) {
    List<String> houseFileList = Lists.newArrayList();
    try {
      // 循环上传房屋图片
      Arrays.asList(houseFiles).forEach(houseFile->{
        Response response = null;
        try {
          response = qiNiuService.uploadFile(houseFile.getInputStream());
          QiniuPutRet qiniuPutRet = gson.fromJson(response.bodyString(), QiniuPutRet.class);
          String url = "/"+qiniuPutRet.getKey();
          houseFileList.add(url);
        } catch (IOException e) {
          e.printStackTrace();
        }

      });

      return ApiResponse.ofSuccess(houseFileList);
    } catch (Exception e) {
      return ApiResponse.ofMessage(40001,"文件上传失败");
    }
  }

  /**
   * 上传所有的户型图片
   * @param planFiles
   * @return
   */
  @PostMapping(value = "house/uploadPlanFiles")
  public ApiResponse<List<String>> floorPlanUpload(@RequestParam("multipartFiles") MultipartFile[] planFiles) {
    List<String> planFileList = Lists.newArrayList();
    try {
      // 循环上传房屋图片
      Arrays.asList(planFiles).forEach(floorPlan->{
        Response response = null;
        try {
          response = qiNiuService.uploadFile(floorPlan.getInputStream());
          QiniuPutRet qiniuPutRet = gson.fromJson(response.bodyString(), QiniuPutRet.class);
          String url = "/"+qiniuPutRet.getKey();
          planFileList.add(url);
        } catch (IOException e) {
          e.printStackTrace();
        }

      });

      return ApiResponse.ofSuccess(planFileList);
    } catch (Exception e) {
      return ApiResponse.ofMessage(40001,"文件上传失败");
    }
  }

  /**
   * 房产新增
   * @param house
   * @return
   */
  @PostMapping("house/add")
  public ApiResponse addHouse(@RequestBody House house){
    house.setState(HouseState.HOUSE_STATE_UP.value);
    if (house.getUserId() == null) {
      return ApiResponse.ofError();
    }
    houseService.addHouse(house,house.getUserId());
    return ApiResponse.ofSuccess();
  }


  /**
   * 绑定还是解除绑定
   * @param req
   * @return
   */
  @PostMapping("house/bind")
  public ApiResponse<Object> bind(@RequestBody HouseUserReq req){
    Integer bindType = req.getBindType();
    HouseUserType houseUserType = Objects.equal(bindType, HouseUserType.SALE.value) ? HouseUserType.SALE : HouseUserType.BOOKMARK;
    if (req.isUnBind()) {
      houseService.unbindUser2House(req.getHouseId(),req.getUserId(),houseUserType);
    }else {
      houseService.bindUser2House(req.getHouseId(), req.getUserId(), houseUserType);
    }
    return ApiResponse.ofSuccess();
  }

  /**
   * 获取热门房产，显示的条数
   * @param size
   * @return
   */
  @RequestMapping("house/hot")
  public ApiResponse<List<House>> getHotHouse(Integer size){
    List<House> list =   recommendService.getHotHouse(size);
    /*log.error("==========================");
    try {
      TimeUnit.SECONDS.sleep(3);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    log.error("data is {}",list);*/
    return ApiResponse.ofSuccess(list);

  }

  /**
   * 获取最新的房源
   * @return
   */
  @GetMapping("house/newest")
  public ApiResponse<List<House>> getNewest(){
    return ApiResponse.ofSuccess(recommendService.getLastest());
  }

}
