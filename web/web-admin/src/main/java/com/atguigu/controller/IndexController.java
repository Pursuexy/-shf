package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.entity.Permission;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@SuppressWarnings({"unchecked", "rawtypes"})
public class IndexController {

    @Reference
    private AdminService adminService;

    @Reference
    private PermissionService permissionService;
    private final static String PAGE_INDEX = "frame/index";
    private final static String PAGE_LOGIN = "frame/login";
    private final static String PAGE_MAIN = "frame/main";

    private final static String PAGE_AUTH = "frame/auth";

    /**
     * 框架首页
     *
     * @return
     */
    @GetMapping("/")
    public String index(ModelMap model) {
        //后续替换为当前登录用户id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Admin admin = adminService.getByUsername(user.getUsername());
        List<Permission> permissionList = permissionService.findMenuPermissionByAdminId(admin.getId());
        model.addAttribute("admin", admin);
        model.addAttribute("permissionList", permissionList);
        return PAGE_INDEX;
    }

    /**
     * 框架主页
     *
     * @return
     */
    @GetMapping("/main")
    public String main() {
        return PAGE_MAIN;
    }

    @GetMapping("/login")
    public String login() {
        return PAGE_LOGIN;
    }

    @GetMapping("/auth")
    public String auth() {
        return PAGE_AUTH;
    }

    /**
     * 获取当前登录信息
     *
     * @return
     */
    @PreAuthorize("hasAuthority('userInfo.show')")
    @GetMapping("/userInfo")
    @ResponseBody
    public Object getInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal();
    }

}