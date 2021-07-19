package com.esp.server.web.service;

import com.esp.server.web.common.ApiResponse;
import com.esp.server.web.model.Comment;
import com.esp.server.web.model.CommentReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @ProjectName: espservers
 * @Auther: GERRY
 * @Date: 2018/11/30 14:39
 * @Description:
 */
@FeignClient("gateway")
public interface CommentService {

    @PostMapping("/comment/comment/list")
    ApiResponse<List<Comment>> listComments(@RequestBody CommentReq commentReq);

    @PostMapping("/comment/comment/add")
    ApiResponse addComment(@RequestBody CommentReq commentReq);
}
