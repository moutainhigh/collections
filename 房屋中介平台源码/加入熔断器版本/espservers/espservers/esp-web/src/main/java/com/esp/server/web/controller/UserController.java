package com.esp.server.web.controller;

import com.esp.server.web.common.ApiResponse;
import com.esp.server.web.common.UserContext;
import com.esp.server.web.service.UserService;
import com.esp.server.web.util.CookieUtil;
import com.esp.server.web.util.GeneralConvert;
import com.esp.server.web.vo.AgencyVO;
import com.esp.server.web.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @ProjectName: espservers
 * @Auther: GERRY
 * @Date: 2018/11/26 21:36
 * @Description:
 */
@Controller
public class UserController {

    @Autowired(required = false)
    private UserService accountService;

    /////////////////////////////////////////// 注册 //////////////////////////////////
    @RequestMapping(value = "accounts/register", method = {RequestMethod.POST, RequestMethod.GET})
    public String accountsSubmitToQiniu(HttpServletRequest request, UserVO account,
                                        @RequestParam(value = "avatarFile", required = false) MultipartFile file,
                                        ModelMap modelMap, RedirectAttributes model) {
        if (StringUtils.isBlank(account.getEmail())) {
            // 调用后端服务或者机构列表渲染机构下拉列表
            ApiResponse apiResponse = accountService.initRegPage();
            List<?> list = (List<?>) apiResponse.getData();
            List<AgencyVO> agencyVOList= GeneralConvert.convert2List(list, AgencyVO.class);
            modelMap.put("agencyList", agencyVOList);
            return "user/accounts/register";
        }

        // 调用图片上传
        ApiResponse apiResponse = accountService.avatarUpload(file);
        if (apiResponse.getCode() == 200) {
            account.setAvatar(apiResponse.getData()+"");
        }

        String baseUrl = StringUtils.substringBeforeLast(request.getRequestURL().toString(), "/accounts/register");
        // 定义激活的连接地址
        String activeUrl = baseUrl+"/accounts/verify";
        account.setEnableUrl(activeUrl);
        // 构建人物信息
        apiResponse = accountService.accountsSubmitToQiniu(account);
        if (apiResponse.getCode() == 200) {
            modelMap.put("email", apiResponse.getData());
            return "user/accounts/registerSubmit";
        } else {
            model.addFlashAttribute("errorMsg",apiResponse.getMessage());
            return "redirect:/accounts/register";
        }
    }

    @GetMapping("/accounts/verify")
    public String verify(String key, RedirectAttributes model) {
        ApiResponse verify = accountService.verify(key);
        if (verify.getCode() == 200) {
            model.addFlashAttribute("successMsg", "激活成功,请登录");
            return "redirect:/accounts/signin";
        } else {
            model.addFlashAttribute("errorMsg", verify.getMessage());
            return "redirect:/accounts/signin";
        }
    }

    ////////////////////// 登录 ////////////////////////
    @GetMapping("/accounts/signin")
    public String initLogin() {
        return "user/accounts/signin";
    }

    @PostMapping(value = "/accounts/signin")
    public String loginSubmit(UserVO user, RedirectAttributes model, HttpServletResponse response) {
        ApiResponse apiResponse = accountService.loginSubmit(user);
        UserVO loginUser = GeneralConvert.converter(apiResponse.getData(), UserVO.class);
        if (loginUser == null) {
            model.addFlashAttribute("errorMsg", "输入的账号和密码错误");
            return "redirect:/accounts/signin";
        } else {
            UserContext.setUser(loginUser);
            // 把服务端生成token进行保存到cookie中
            CookieUtil.setToken(loginUser.getToken(), response);
            return  "redirect:/index";
        }
    }

    @GetMapping("accounts/logout")
    public String logout(@CookieValue(name = "token",required = false) String token,
                         HttpServletResponse response, HttpSession session) {
        // 删除email中对应的token
        accountService.logout(token);
        // 删除客户端的token
        CookieUtil.delToken(response);
        // 让会话马上失效
        UserContext.remove();

        return "redirect:/index";
    }

    ////////////////////// 个人信息 /////////////
    @GetMapping("accounts/profile")
    public String profile(String email , Model model) {
        ApiResponse profile = accountService.profile(email);
        UserVO userVO = GeneralConvert.converter(profile.getData(), UserVO.class);
        model.addAttribute("user", userVO);

        return "user/accounts/profile";
    }

    @GetMapping("accounts/remember")
    public String remember(String email, RedirectAttributes model, ModelMap modelMap,HttpServletRequest request) {
        if (StringUtils.isBlank(email)) {
            model.addFlashAttribute("errorMsg", "邮箱不能为空");
            return "redirect:/accounts/signin";
        }
        String baseUrl = StringUtils.substringBeforeLast(request.getRequestURL().toString(), "/accounts/remember");
        // 定义激活的连接地址
        String activeUrl = baseUrl+"/accounts/reset";
        accountService.remember(email, activeUrl);
        modelMap.put("email", email);
        return "user/accounts/remember";
    }

    @RequestMapping("accounts/reset")
    public String reset(String key, RedirectAttributes model, ModelMap modelMap) {
        ApiResponse reset = accountService.reset(key);
        String email = reset.getData().toString();

        if (StringUtils.isBlank(email)) {
            model.addFlashAttribute("errorMsg","重置链接已过期");
            return "redirect:/accounts/signin";
        }

        modelMap.put("email", email);
        modelMap.put("success_key", key);

        return "user/accounts/reset";
    }

    @PostMapping("accounts/resetSubmit")
    public String resetSubmit(UserVO user,RedirectAttributes model) {
        ApiResponse apiResponse = accountService.resetSubmit(user);
        if (200 == apiResponse.getCode()) {
            model.addFlashAttribute("successMsg", "密码重置成功");
        } else {
            model.addFlashAttribute("errorMsg", "密码重置失败");
        }

        return "redirect:/index";
    }

    @PostMapping(value = "accounts/profileSubmit")
    public String profileSubmit(UserVO updateUser, RedirectAttributes model) {
        ApiResponse apiResponse = accountService.profileSubmit(updateUser);
        String email = apiResponse.getData().toString();
        model.addFlashAttribute("successMsg", "个人信息更新成功");
        return "redirect:/accounts/profile?email="+email;
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
    public String changePassword(String email, String password, String newPassword,
                                 String confirmPassword, RedirectAttributes model) {
        if (!confirmPassword.equals(newPassword)) {
            model.addFlashAttribute("errorMsg", "确认密码错误");
            return "redirect:/accounts/profile?email="+email;
        }

        ApiResponse apiResponse = accountService.changePassword(email, password, newPassword, confirmPassword);
        if (apiResponse.getCode() == 200) {
            model.addFlashAttribute("successMsg", "密码更新成功");
            return "redirect:/accounts/logout";
        } else {
            return "redirect:/accounts/profile?email="+email;
        }

    }

}
