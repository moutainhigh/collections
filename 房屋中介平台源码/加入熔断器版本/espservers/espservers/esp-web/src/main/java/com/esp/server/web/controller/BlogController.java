package com.esp.server.web.controller;

import com.esp.server.web.common.ApiResponse;
import com.esp.server.web.common.PageData;
import com.esp.server.web.common.PageParams;
import com.esp.server.web.constant.CommentType;
import com.esp.server.web.model.*;
import com.esp.server.web.service.BlogService;
import com.esp.server.web.service.CommentService;
import com.esp.server.web.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @ProjectName: espservers
 * @Auther: GERRY
 * @Date: 2018/12/5 14:48
 * @Description:
 */
@Controller
@RequestMapping("blog")
public class BlogController {

    public static final int COMMENT_SIZE = 10;

    @Autowired(required = false)
    private BlogService blogService;

    @Autowired(required = false)
    private CommentService commentService;

    @Autowired(required = false)
    private HouseService houseService;

    @GetMapping("list")
    public String list(Integer pageSize, Integer pageNum, Blog query, ModelMap modelMap){
        // 构建一个分页参数对象
        PageParams pageParams = PageParams.build(pageSize, pageNum);
        // 构建一个博客请求对象
        BlogQueryReq queryReq = new BlogQueryReq();
        queryReq.setBlog(query);
        queryReq.setLimit(pageParams.getLimit());
        queryReq.setOffset(pageParams.getOffset());
        // 调用博客服务查询博客列表
        ApiResponse<ListResponse<Blog>> listResponseApiResponse = blogService.listBlog(queryReq);
        ListResponse<Blog> listResponse = listResponseApiResponse.getData();
        PageData<Blog> pageData = PageData.buildPage
                (listResponse.getList(),listResponse.getCount(),pageParams.getPageSize(), pageParams.getPageNum());
        // 获取热门房产信息
        ApiResponse<List<House>> hotHouse = houseService.getHotHouse(HouseController.HOT_SIZE);
        List<House> hotHouseData = hotHouse.getData();

        modelMap.put("hotHouses", hotHouseData);
        modelMap.put("pageData", pageData);

        return "blog/listing";
    }

    @GetMapping("detail")
    public String blogDetail(Integer id,ModelMap modelMap){
        // 查询博客详情
        ApiResponse<Blog> blogApiResponse = blogService.queryOneBlog(id);
        Blog blog = blogApiResponse.getData();

        // 查询博客对应的评论列表
        CommentReq commentReq = new CommentReq();
        commentReq.setBlogId(id);
        commentReq.setSize(COMMENT_SIZE);
        commentReq.setType(CommentType.BLOG.value);
        ApiResponse<List<Comment>> listApiResponse = commentService.listComments(commentReq);
        List<Comment> comments = listApiResponse.getData();

        // 获取热门房产信息
        ApiResponse<List<House>> hotHouse = houseService.getHotHouse(HouseController.HOT_SIZE);
        List<House> hotHouseData = hotHouse.getData();

        modelMap.put("hotHouses", hotHouseData);
        modelMap.put("blog", blog);
        modelMap.put("commentList", comments);

        return "blog/detail";
    }
}
