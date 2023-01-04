package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Role;
import com.atguigu.service.PermissionService;
import com.atguigu.service.RoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/role")
@SuppressWarnings({"unchecked", "rawtypes"})
public class RoleController extends BaseController {
   @Reference
   private RoleService roleService;
   @Reference
   private PermissionService permissionService;


   private final static String PAGE_SUCCESS = "common/successPage";
   private final static String PAGE_INDEX = "role/index";
   private final static String PAGE_CREATE = "role/create";
   private final static String PAGE_EDIT = "role/edit";
   private final static String LIST_ACTION = "redirect:/role";
   private final static String PAGE_ASSGIN_SHOW = "role/assginShow";
   
   /** 
    * 列表
    * @param model
    * @return
    */
   @PreAuthorize("hasAuthority('role.show')")
   @RequestMapping
   public String index(ModelMap model, HttpServletRequest request) {
      Map<String,Object> filters = getFilters(request);
      PageInfo<Role> page = roleService.findPage(filters);
      model.addAttribute("page", page);
      model.addAttribute("filters", filters);
      return PAGE_INDEX;
   }


   @PreAuthorize("hasAuthority('role.create')")
   @GetMapping("/create")
   public String create() {
      return PAGE_CREATE;
   }

   @PreAuthorize("hasAuthority('role.create')")
   @PostMapping("/save")
   public String save(Role role, HttpServletRequest request) {
      roleService.insert(role);
      return PAGE_SUCCESS;
   }

   @PreAuthorize("hasAuthority('role.edit')")
   @GetMapping("/edit/{id}")
   public String edit(ModelMap model,@PathVariable Long id) {
      Role role = roleService.getById(id);
      model.addAttribute("role",role);
      return PAGE_EDIT;
   }

   @PreAuthorize("hasAuthority('role.edit')")
   @PostMapping(value="/update")
   public String update(Role role) {
      roleService.update(role);
      return PAGE_SUCCESS;
   }

   @PreAuthorize("hasAuthority('role.delete')")
   @GetMapping("/delete/{id}")
   public String delete(@PathVariable Long id) {
      roleService.delete(id);
      return LIST_ACTION;
   }

   /**
    * 进入分配权限页面
    * @param roleId
    * @return
    */
   @PreAuthorize("hasAuthority('role.assgin')")
   @GetMapping("/assignShow/{roleId}")
   public String assignShow(ModelMap model,@PathVariable Long roleId) {
      List<Map<String,Object>> zNodes = permissionService.findPermissionByRoleId(roleId);
      model.addAttribute("zNodes", zNodes);
      model.addAttribute("roleId", roleId);
      return PAGE_ASSGIN_SHOW;
   }

   /**
    * 给角色分配权限
    * @param roleId
    * @param permissionIds
    * @return
    */
   @PreAuthorize("hasAuthority('role.assgin')")
   @PostMapping("/assignPermission")
   public String assignPermission(Long roleId,Long[] permissionIds) {
      permissionService.saveRolePermissionRealtionShip(roleId, permissionIds);
      return PAGE_SUCCESS;
   }
}