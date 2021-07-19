package com.esp.server.web.service;

import com.esp.server.web.common.ApiResponse;
import com.esp.server.web.model.Blog;
import com.esp.server.web.model.BlogQueryReq;
import com.esp.server.web.model.ListResponse;
import com.esp.server.web.util.ResponseDataUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ProjectName: espservers
 * @Auther: GERRY
 * @Date: 2018/12/5 14:40
 * @Description:
 */
@FeignClient(value = "gateway", fallback = BlogService.BlogServiceFallback.class)
public interface BlogService {

    @PostMapping("/comment/blog/list")
    ApiResponse<ListResponse<Blog>> listBlog(@RequestBody BlogQueryReq query);

    @GetMapping("/comment/blog/one")
    ApiResponse<Blog> queryOneBlog(@RequestParam("id") Integer id);

    @Component
    class BlogServiceFallback implements BlogService {
        @Override
        public ApiResponse<ListResponse<Blog>> listBlog(BlogQueryReq query) {
            return ResponseDataUtil.setResponseData("listBlog");
        }

        @Override
        public ApiResponse<Blog> queryOneBlog(Integer id) {
            return ResponseDataUtil.setResponseData("queryOneBlog");
        }
    }
}
