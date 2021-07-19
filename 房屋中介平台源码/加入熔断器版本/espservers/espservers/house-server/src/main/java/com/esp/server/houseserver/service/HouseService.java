package com.esp.server.houseserver.service;


import com.esp.server.houseserver.common.ApiResponse;
import com.esp.server.houseserver.constant.HouseUserType;
import com.esp.server.houseserver.domain.*;
import com.esp.server.houseserver.mapper.CityMapper;
import com.esp.server.houseserver.mapper.HouseMapper;
import com.esp.server.houseserver.model.LimitOffset;
import com.esp.server.houseserver.model.User;
import com.esp.server.houseserver.utils.BeanHelper;
import com.esp.server.houseserver.utils.GeneralConvert;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HouseService {

    @Autowired(required = false)
    private HouseMapper houseMapper;

    @Autowired(required = false)
    private CityMapper cityMapper;

    @Autowired
    private MailService mailService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${qiniu.cdn.prefix:}")
    private String imgPrefix;


    public List<House> queryAndSetImg(House query, LimitOffset pageParams) {
        List<House> houses = houseMapper.selectHouse(query, pageParams);
        houses.forEach(h -> {
            // 设置详情页的第一张显示图片
            h.setFirstImg(imgPrefix + h.getFirstImg());
            h.setImageList(h.getImageList().stream().map(img -> imgPrefix + img).collect(Collectors.toList()));
            h.setFloorPlanList(h.getFloorPlanList().stream().map(img -> imgPrefix + img).collect(Collectors.toList()));
        });
        return houses;
    }


    public List<House> queryHousesByIds(List<Long> ids, Integer size) {
        House query = new House();
        query.setIds(ids);
        return queryAndSetImg(query, LimitOffset.build(size, 1));
    }

    @Transactional(rollbackFor = Exception.class)
    public List<Community> getCommunities() {
        Community community = new Community();
        return houseMapper.selectCommunity(community);
    }

    public List<City> getCities() {
        return cityMapper.selectAll();
    }


    /**
     * 1. 添加房产
     * 2. 绑定房产到用户关系
     *
     * @param house
     * @param userId
     */
    @Transactional(rollbackFor = Exception.class)
    public void addHouse(House house, Long userId) {
        BeanHelper.setDefaultProp(house, House.class);
        BeanHelper.onInsert(house);
        houseMapper.insert(house);
        // 绑定用户和房产关系（数据库中不使用外键，使用业务保证表的关系）
        bindUser2House(house.getId(), userId, HouseUserType.SALE);
    }


    @Transactional(rollbackFor = Exception.class)
    public void bindUser2House(Long id, Long userId, HouseUserType sale) {
        HouseUser existHouseUser = houseMapper.selectHouseUser(userId, id, sale.value);
        if (existHouseUser != null) {
            return;
        }
        HouseUser houseUser = new HouseUser();
        houseUser.setHouseId(id);
        houseUser.setUserId(userId);
        houseUser.setType(sale.value);
        BeanHelper.setDefaultProp(houseUser, HouseUser.class);
        BeanHelper.onInsert(houseUser);
        houseMapper.insertHouseUser(houseUser);
    }


    /**
     * 当售卖时只能将房产下架，不能解绑用户关系
     * 当收藏时可以解除用户收藏房产这一关系
     * 解绑操作1.
     *
     * @param houseId
     * @param userId
     * @param type
     */
    public void unbindUser2House(Long houseId, Long userId, HouseUserType type) {
        if (type.equals(HouseUserType.SALE)) {
            houseMapper.downHouse(houseId);
        } else {
            houseMapper.deleteHouseUser(houseId, userId, type.value);
        }

    }


    /**
     * 查询房产列表（分页）
     *
     * @param query
     * @param build
     * @return
     */
    public Pair<List<House>, Long> queryHouse(House query, LimitOffset build) {
        List<House> houses = Lists.newArrayList();
        House houseQuery = query;
        if (StringUtils.isNoneBlank(query.getName())) {
            Community community = new Community();
            community.setName(query.getName());
            // 跟小区名称获取所有小区信息集合
            List<Community> communities = houseMapper.selectCommunity(community);
            if (!communities.isEmpty()) {
                houseQuery = new House();
                houseQuery.setCommunityId(communities.get(0).getId());
            }
        }

        houses = queryAndSetImg(houseQuery, build);
        Long count = houseMapper.selectHouseCount(houseQuery);
        return ImmutablePair.of(houses, count);
    }


    /**
     * 根据房产id查询访问详情
     *
     * @param id
     * @return
     */
    public House queryOneHouse(long id) {
        House query = new House();
        query.setId(id);
        List<House> houses = queryAndSetImg(query, LimitOffset.build(1, 0));
        if (!houses.isEmpty()) {
            return houses.get(0);
        }
        return null;
    }


    /**
     * 向经纪人发送留言通知
     *
     * @param userMsg
     */
    public void addUserMsg(UserMsg userMsg) {
        BeanHelper.onInsert(userMsg);
        BeanHelper.setDefaultProp(userMsg, UserMsg.class);
        houseMapper.insertUserMsg(userMsg);
        String agentEmail = getUser(userMsg.getAgentId()).getEmail();

        mailService.sendMail("来自用户" + userMsg.getEmail(), userMsg.getMsg(), agentEmail);
    }

    /**
     * 根据用户编号查询用户信息
     * @param id
     * @return
     */
    private User getUser(Long id) {
        ApiResponse<User> apiResponse = restTemplate.getForObject("http://user-service/agency/detail?id=" + id, ApiResponse.class);
        return GeneralConvert.converter(apiResponse.getData(), User.class);
    }


    /**
     * 更新评分
     *
     * @param id
     * @param rating
     */
    public void updateRating(Long id, Double rating) {
        House house = queryOneHouse(id);
        Double oldRating = house.getRating();
        Double newRating = oldRating.equals(0D) ? rating : Math.min(Math.round(oldRating + rating) / 2, 5);
        House updateHouse = new House();
        updateHouse.setId(id);
        updateHouse.setRating(newRating);
        houseMapper.updateHouse(updateHouse);
    }
}
