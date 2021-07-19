package com.esp.server.web.controller;

import com.esp.server.web.common.ApiResponse;
import com.esp.server.web.common.PageData;
import com.esp.server.web.common.PageParams;
import com.esp.server.web.common.UserContext;
import com.esp.server.web.constant.CommentType;
import com.esp.server.web.constant.HouseUserType;
import com.esp.server.web.model.*;
import com.esp.server.web.service.AgencyService;
import com.esp.server.web.service.CommentService;
import com.esp.server.web.service.HouseService;
import com.esp.server.web.util.GeneralConvert;
import com.esp.server.web.util.ListUtil;
import com.esp.server.web.vo.UserVO;
import com.google.common.base.Joiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;

@Controller
public class HouseController {

    // 热度房产显示个数定义
    public static final Integer HOT_SIZE= 3;

    @Autowired(required = false)
    private HouseService houseService; // 房产

    @Autowired(required = false)
    private CommentService commentService; // 评论

    @Autowired(required = false)
    private AgencyService agencyService; // 经纪人

    /**
     * 热门房产和房产列表
     * @param query
     * @param pageNum
     * @param pageSize
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/house/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String listHouse(House query, Integer pageNum, Integer pageSize, ModelMap modelMap) {
        // 把传入参数封装为一个分页参数对象
        PageParams pageParams = PageParams.build(pageSize, pageNum);
        // 封装一个房产查询对象
        HouseQueryReq houseQuery = new HouseQueryReq();
        houseQuery.setLimit(pageParams.getLimit());
        houseQuery.setOffset(pageParams.getOffset());
        houseQuery.setQuery(query);
        // 调用房产服务提供的接口
        ApiResponse apiResponse = houseService.houseList(houseQuery);
        // 把获取的数据强制转换为一个ListResponse对象
        ListResponse listResponse = GeneralConvert.converter(apiResponse.getData(), ListResponse.class);
        List<House> list = GeneralConvert.convert2List(listResponse.getList(),House.class);
        // 设置显示的标签（售卖/出租）
        list.forEach(h-> {
            h.setType(h.getType());
        });
        // 构建一个分页对象
        PageData<House> pageData = PageData.buildPage(list,
                listResponse.getCount(), pageParams.getPageSize(), pageParams.getPageNum());
        // 获取热门房产信息
        ApiResponse<List<House>> hotHouse = houseService.getHotHouse(HOT_SIZE);
        List<House> hotHouseData = hotHouse.getData();

        // 封装页面渲染的数据
        modelMap.put("hotHouses", hotHouseData);
        modelMap.put("query", query);
        modelMap.put("pageData", pageData);

        return "house/listing";
    }

    /**
     * 根据房产编号查询房产的详情
     * @param id
     * @param modelMap
     * @return
     */
    @GetMapping("/house/detail")
    public String houseDetail(long id,ModelMap modelMap){
        // 根据房产id查询房产详情
        ApiResponse<House> apiResponse = houseService.houseDetail(id);
        House house = apiResponse.getData();
        // 加载经纪人详情
        if (Objects.nonNull(house.getUserId())) {
            if (!Objects.equals(0L, house.getUserId())) {
                ApiResponse<UserVO> agencyDetail = agencyService.getAgencyDetail(house.getUserId());
                UserVO data = agencyDetail.getData();
                modelMap.put("agency",  data);
            }
        }
        // 获取房产对应的所有评论
        CommentReq commentReq = new CommentReq();
        commentReq.setHouseId(id);
        commentReq.setSize(BlogController.COMMENT_SIZE);
        commentReq.setType(CommentType.HOUSE.value);
        ApiResponse<List<Comment>> listApiResponse = commentService.listComments(commentReq);
        List<Comment> comments = listApiResponse.getData();
        // 获取热门房产信息
        ApiResponse<List<House>> hot= houseService.getHotHouse(HOT_SIZE);
        List<House> hotHouse = hot.getData();
        // 封装页面渲染的数据
        modelMap.put("commentList", comments);
        modelMap.put("hotHouses", hotHouse);
        modelMap.put("house", house);

        return "house/detail";
    }

    /**
     * 初始化所有的城市和小区
     * @param modelMap
     * @return
     */
    @GetMapping("/house/initAdd")
    public String initAddHouse(ModelMap modelMap) {
        ApiResponse<List<City>> cityResponse = houseService.allCity();
        List<City> cities = cityResponse.getData();
        ApiResponse<List<Community>> communitiesResponse = houseService.allCommunities();
        List<Community> communities = communitiesResponse.getData();

        // 初始化数据
        modelMap.put("cities", cities);
        modelMap.put("communities", communities);

        return "house/add";
    }

    /**
     * 新增房产信息
     * @param house
     * @return
     */
    @PostMapping("/house/add")
    public String addHouse(House house) {
        // 设置用户信息
        UserVO user = UserContext.getUser();
        house.setUserId(user.getId());

        // 上传户型图片
        MultipartFile[] planFiles = ListUtil.converterListToMulFileArray(house.getFloorPlanFiles());
        ApiResponse<List<String>> planApiResponse = houseService.floorPlanUpload(planFiles);
        List<String> planList = planApiResponse.getData();
        // 连接器类
        String planFileImages = Joiner.on(",").join(planList);
        house.setFloorPlan(planFileImages);


        // 上传房屋图片
        MultipartFile[] houseFiles = ListUtil.converterListToMulFileArray(house.getHouseFiles());
        ApiResponse<List<String>> houseApiResponse = houseService.houseFileUpload(houseFiles);
        List<String> houseList = houseApiResponse.getData();
        String houseFileImages = Joiner.on(",").join(houseList);
        house.setImages(houseFileImages);


        // 添加房屋信息
        houseService.addHouse(house);

        return "redirect:/house/ownList";
    }

    /**
     * 查询个人的房产信息
     * @param query
     * @param pageNum
     * @param pageSize
     * @param modelMap
     * @return
     */
    @GetMapping("/house/ownList")
    public String getOwnHouseList(House query, Integer pageNum, Integer pageSize, ModelMap modelMap) {
        // 设置用户信息
        UserVO user = UserContext.getUser();
        query.setUserId(user.getId());
        // 设置状态为未收藏
        query.setBookmarked(false);
        // 把传入参数封装为一个分页参数对象
        PageParams pageParams = PageParams.build(pageSize, pageNum);
        // 封装一个房产查询对象
        HouseQueryReq houseQuery = new HouseQueryReq();
        houseQuery.setLimit(pageParams.getLimit());
        houseQuery.setOffset(pageParams.getOffset());
        houseQuery.setQuery(query);
        // 调用房产服务提供的接口
        ApiResponse apiResponse = houseService.houseList(houseQuery);
        // 把获取的数据强制转换为一个ListResponse对象
        ListResponse listResponse = GeneralConvert.converter(apiResponse.getData(), ListResponse.class);
        List<House> list = GeneralConvert.convert2List(listResponse.getList(),House.class);
        // 构建一个分页对象
        PageData<House> pageData = PageData.buildPage(list,
                listResponse.getCount(), pageParams.getPageSize(), pageParams.getPageNum());

        modelMap.put("pageData", pageData);
        modelMap.put("active", "own");  // 样式设置

        return "house/ownlist";
    }

    /**
     * 查询个人收藏的房产信息
     * @param query
     * @param pageNum
     * @param pageSize
     * @param modelMap
     * @return
     */
    @GetMapping("/house/bookmarked")
    public String getBookMarkedHouseList(House query, Integer pageNum, Integer pageSize, ModelMap modelMap) {
        // 设置用户信息
        UserVO user = UserContext.getUser();
        query.setUserId(user.getId());
        // 设置状态为未收藏
        query.setBookmarked(true);
        // 把传入参数封装为一个分页参数对象
        PageParams pageParams = PageParams.build(pageSize, pageNum);
        // 封装一个房产查询对象
        HouseQueryReq houseQuery = new HouseQueryReq();
        houseQuery.setLimit(pageParams.getLimit());
        houseQuery.setOffset(pageParams.getOffset());
        houseQuery.setQuery(query);
        // 调用房产服务提供的接口
        ApiResponse apiResponse = houseService.houseList(houseQuery);
        // 把获取的数据强制转换为一个ListResponse对象
        ListResponse listResponse = GeneralConvert.converter(apiResponse.getData(), ListResponse.class);
        List<House> list = GeneralConvert.convert2List(listResponse.getList(),House.class);
        // 构建一个分页对象
        PageData<House> pageData = PageData.buildPage(list,
                listResponse.getCount(), pageParams.getPageSize(), pageParams.getPageNum());

        modelMap.put("pageData", pageData);
        modelMap.put("active", "marked"); // 样式设置

        return "house/ownlist";
    }


    /**
     * 我的房产信息下架和取消收藏
     * @return
     */
    @GetMapping("/house/del")
    public String downStateOrUnBookmarked(HouseUserReq query) {
        // 设置用户信息
        UserVO user = UserContext.getUser();
        query.setUserId(user.getId());
        // 设置为解绑状态
        query.setUnBind(true);
        // 获取操作标识11标识下架房产，22代码取消收藏
        boolean flag = 11 == query.getMark();
        // 设置房产状态
        if (flag) {
            // 售卖下架
            query.setBindType(HouseUserType.SALE.value);
        } else {
            // 收藏删除关系
            query.setBindType(HouseUserType.BOOKMARK.value);
        }

        houseService.bind(query);

        return flag ? "redirect:/house/ownList" : "redirect:/house/bookmarked";
    }



    /**
     * 房产收藏和取消收藏
     * @param req
     * @return
     */
    @PostMapping({"/house/bind", "/house/unBind"})
    @ResponseBody
    public ApiResponse<Object> bindOrUnbind(HouseUserReq req) {
        UserVO user = UserContext.getUser();
        req.setUserId(user.getId());
        // 设置收藏或者是取消收藏标识
        req.setUnBind(req.getMark()!=null ? false : true);
        // 执行收藏或者取消收藏
        houseService.bind(req);

        return ApiResponse.ofSuccess(req.getMark()!=null ? "收藏成功" : "取消收藏");
    }

    /**
     * 给经纪人发送邮件
     * @param userMsg
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/house/leaveMsg")
    public String leaveMsg(UserMsg userMsg, RedirectAttributes redirectAttributes) {
        ApiResponse apiResponse = houseService.leaveMsg(userMsg);
        if (apiResponse.getCode() == 200) {
            redirectAttributes.addFlashAttribute("successMsg", "留言成功");
            return "redirect:/house/detail?id=" + userMsg.getHouseId();
        } else {

            redirectAttributes.addFlashAttribute("successMsg", "留言失败");
            return "redirect:/house/detail?id=" + userMsg.getHouseId();
        }
    }

    /**
     * 评分
     * @param rating
     * @param id
     * @return
     */
    @GetMapping("/house/rating")
    @ResponseBody
    public ApiResponse rating(Double rating, Long id) {
        houseService.houseRate(rating, id);

        return ApiResponse.ofSuccess();
    }
}
