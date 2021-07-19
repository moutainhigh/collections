package com.esp.server.web.service;

import com.esp.server.web.common.ApiResponse;
import com.esp.server.web.vo.UserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ProjectName: espservers
 * @Auther: GERRY
 * @Date: 2018/11/26 21:29
 * @Description: 用户接口
 */
// name 对应zuul中配置的名称
@FeignClient(name = "gateway")
public interface UserService {
    /////////////// 注册相关接口 //////////
    @GetMapping("user/accounts/register")
    ApiResponse intiRegPage();

    @PostMapping("user/accounts/register")
    ApiResponse accountsSubmitToQiniu(@RequestBody UserVO account);

    /**
     * 文件上的接口
     * @param multipartFile
     * @return
     */
    @PostMapping(value = "user/accounts/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ApiResponse avatarUpload(@RequestPart("multipartFile") MultipartFile multipartFile);

    @GetMapping("user/accounts/verify")
    ApiResponse verify(@RequestParam("key") String key);

    ////////////// 登录操作 ///////////////////
    @PostMapping("user/accounts/signin")
    ApiResponse loginSubmit(@RequestBody UserVO user);

    @GetMapping("user/accounts/logout")
    ApiResponse logout(@RequestParam("token") String token);

    ///////////////// 个人信息 //////////////////
    @GetMapping("user/accounts/profile")
    ApiResponse profile(@RequestParam("email") String email);

    @PostMapping("user/accounts/profileSubmit")
    ApiResponse profileSubmit(@RequestBody UserVO user);

    // 忘记密码
    @GetMapping("user/accounts/remember")
    ApiResponse remember(@RequestParam("email") String email,@RequestParam("activeUrl") String activeUrl);

    // 重置密码
    @GetMapping("user/accounts/reset")
    ApiResponse reset(@RequestParam("key") String key);

    // 重置密码提交
    @PostMapping("user/accounts/resetSubmit")
    ApiResponse resetSubmit(@RequestBody UserVO user);

    // 修改密码
    @PostMapping("user/accounts/changePassword")
    ApiResponse changePassword(@RequestParam("email") String email,
                               @RequestParam("password") String password,
                               @RequestParam("newPassword") String newPassword,
                               @RequestParam("confirmPassword") String confirmPassword);
}
