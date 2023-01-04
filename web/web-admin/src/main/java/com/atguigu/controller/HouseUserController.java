package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.HouseUser;
import com.atguigu.service.HouseUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value="/houseUser")
@SuppressWarnings({"unchecked", "rawtypes"})
public class HouseUserController extends BaseController {

   @Reference
   private HouseUserService houseUserService;

   private final static String LIST_ACTION = "redirect:/house/";
   
   private final static String PAGE_CREATE = "houseUser/create";
   private final static String PAGE_EDIT = "houseUser/edit";
   private final static String PAGE_SUCCESS = "common/successPage";

   
   /** 
    * 进入新增
    * @param model
    * @param houseId
    * @return
    */
   @PreAuthorize("hasAuthority('house.editUser')")
   @GetMapping("/create")
   public String create(ModelMap model, @RequestParam("houseId") Long houseId) {
      model.addAttribute("houseId",houseId);
      return PAGE_CREATE;
   }
   
   /**
    * 保存新增
    * @param houseUser
    * @return
    */
   @PreAuthorize("hasAuthority('house.editUser')")
   @PostMapping("/save")
   public String save(HouseUser houseUser) {
      houseUserService.insert(houseUser);
      return PAGE_SUCCESS;
   }
   
   /** 
    * 编辑
    * @param model
    * @param id
    * @return
    */
   @PreAuthorize("hasAuthority('house.editUser')")
   @GetMapping("/edit/{id}")
   public String edit(ModelMap model,@PathVariable Long id) {
      HouseUser houseUser = houseUserService.getById(id);
      model.addAttribute("houseUser",houseUser);
      return PAGE_EDIT;
   }
   
   /** 
    * 保存更新
    * @param houseUser
    * @return
    */
   @PreAuthorize("hasAuthority('house.editUser')")
   @PostMapping(value="/update")
   public String update(HouseUser houseUser) {
      houseUserService.update(houseUser);
      return PAGE_SUCCESS;
   }
   
   /**
    * 删除
    * @param model
    * @param id
    * @return
    */
   @PreAuthorize("hasAuthority('house.editUser')")
   @GetMapping("/delete/{houseId}/{id}")
   public String delete(ModelMap model,@PathVariable Long houseId, @PathVariable Long id) {
      houseUserService.delete(id);
      return LIST_ACTION + houseId;
   }
   
}