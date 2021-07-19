/**
 *
 */
package com.esp.server.userserver.controller;

import com.esp.server.userserver.common.ApiResponse;
import com.esp.server.userserver.constant.UserEnum;
import com.esp.server.userserver.domain.Agency;
import com.esp.server.userserver.domain.User;
import com.esp.server.userserver.model.QiniuPutRet;
import com.esp.server.userserver.service.AgencyService;
import com.esp.server.userserver.service.QiNiuService;
import com.esp.server.userserver.service.UserService;
import com.google.gson.Gson;
import com.qiniu.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author eric
 */
@RestController
public class UserController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserService accountService;

    @Autowired
    private AgencyService agencyService;

    @Autowired
    private QiNiuService qiNiuService;

    @Autowired
    private Gson gson;


//----------------------------注册流程-------------------------------------------
    @GetMapping(value = "accounts/register")
    public ApiResponse intiRegPage() {
        List<Agency> allAgency = agencyService.getAllAgency();

        return Objects.isNull(allAgency) ?
                ApiResponse.ofMessage(20001, "获取机构件列表失败") :
                ApiResponse.ofSuccess(allAgency);
    }

    @PostMapping(value = "accounts/upload")
    public ApiResponse avatarUpload(@RequestParam("multipartFile") MultipartFile avatarFile) {
        String photoUrl =  "/";
        try {
            Response response = qiNiuService.uploadFile(avatarFile.getInputStream());
            QiniuPutRet qiniuPutRet = gson.fromJson(response.bodyString(), QiniuPutRet.class);
            photoUrl += qiniuPutRet.getKey();
            return ApiResponse.ofSuccess(photoUrl);
        } catch (IOException e) {
            return ApiResponse.ofMessage(40001,"文件上传失败");
        }
    }

    @PostMapping(value = "accounts/register")
    public ApiResponse accountsSubmitToQiniu(@RequestBody User account) {
        boolean exist = accountService.isExist(account.getEmail());

        if (!exist) {
            accountService.addAccount(account);

            return ApiResponse.ofSuccess(account.getEmail());
        } else {
            return ApiResponse.ofMessage(UserEnum.EMAIL_REGISTERED.getCode(), UserEnum.EMAIL_REGISTERED.getMsg());
        }
    }

    @GetMapping("accounts/verify")
    public ApiResponse verify(String key) {
        boolean result = accountService.enableAccount(key);
        if (result) {
            return ApiResponse.ofSuccess();
        } else {
            return ApiResponse.ofMessage(UserEnum.EMAIL_INVALID.getCode(), UserEnum.EMAIL_INVALID.getMsg());
        }
    }


    //----------------------------登录操作流程-------------------------------------------

    @PostMapping(value = "/accounts/signin")
    public ApiResponse loginSubmit(@RequestBody User user) {
        User loginUser = accountService.login(user.getEmail(), user.getPassword());
        if (loginUser == null) {
            return ApiResponse.ofMessage(50001, "输入的账号或者密错误");
        } else {
            return ApiResponse.ofSuccess(loginUser);
        }
    }

    /**
     * @param token
     * @return
     */
    @GetMapping("accounts/logout")
    public ApiResponse logout(String token) {
        accountService.logout(token);
        return ApiResponse.ofSuccess();
    }

    @GetMapping("token/verify")
    public ApiResponse verifyToken(String token) {
        User user = accountService.getLoginUserByToken(token);
        return ApiResponse.ofSuccess(user.getKey());
    }


    // 忘记密码
    @GetMapping("accounts/remember")
    public ApiResponse remember(String email,String activeUrl) {
        accountService.resetNotify(email, activeUrl);
        return ApiResponse.ofSuccess();
    }


    // 重置密码
    @GetMapping("accounts/reset")
    public ApiResponse reset(String key) {
        String email = accountService.getResetKeyEmail(key);
        return ApiResponse.ofSuccess(email);
    }

    // 重置密码提交
    @PostMapping("accounts/resetSubmit")
    public ApiResponse resetSubmit(@RequestBody User user) {
        accountService.reset(user.getKey(), user.getPassword());
        return ApiResponse.ofSuccess();
    }


    //----------------------------个人信息修改--------------------------------------
    @GetMapping("accounts/profile")
    public ApiResponse profile(String email) {
        User user = accountService.getUserByEmail(email);
        return ApiResponse.ofSuccess(user);
    }


    @PostMapping("accounts/profileSubmit")
    public ApiResponse profileSubmit(@RequestBody User updateUser) {
        User user = accountService.updateUser(updateUser);
        return ApiResponse.ofSuccess(user.getEmail());
    }

    /**
     * 修改密码操作
     *
     * @param email
     * @param password
     * @param newPassword
     * @param confirmPassword
     * @return
     */
    @PostMapping("accounts/changePassword")
    public ApiResponse changePassword(String email, String password, String newPassword,
                                 String confirmPassword) {
        User user = accountService.login(email, password);

        if (user != null) {
            User updateUser = new User();
            updateUser.setPassword(newPassword);
            updateUser.setEmail(email);
            accountService.updateUser(updateUser);
            return ApiResponse.ofSuccess();
        } else {
            return ApiResponse.ofMessage(30001, "输入的账号或者旧密码错误");
        }
    }

    @GetMapping("/accounts/getUser")
    public ApiResponse<User> getUserById(Long id) {
        return ApiResponse.ofSuccess(accountService.getUserById(id));
    }
}
