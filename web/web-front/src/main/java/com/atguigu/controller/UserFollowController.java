package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.UserInfo;
import com.atguigu.result.Result;
import com.atguigu.service.UserFollowService;
import com.atguigu.vo.UserFollowVo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value="/userFollow")
@CrossOrigin
@SuppressWarnings({"unchecked", "rawtypes"})
public class UserFollowController extends BaseController {

   @Reference
   private UserFollowService userFollowService;
   
   /**
    * 关注房源
    * @param houseId
    * @param request
    * @return
    */
   @GetMapping("/auth/follow/{houseId}")
   public Result follow(@PathVariable("houseId") Long houseId, HttpServletRequest request){
      UserInfo userInfo = (UserInfo)request.getSession().getAttribute("USER");
      Long userId = userInfo.getId();
      userFollowService.follow(userId, houseId);
      return Result.ok();
   }

   @GetMapping(value = "/auth/list/{pageNum}/{pageSize}")
   public Result findListPage(@PathVariable Integer pageNum,
                              @PathVariable Integer pageSize,
                              HttpServletRequest request) {
      UserInfo userInfo = (UserInfo)request.getSession().getAttribute("USER");
      Long userId = userInfo.getId();
      PageInfo<UserFollowVo> pageInfo = userFollowService.findListPage(pageNum, pageSize, userId);
      return Result.ok(pageInfo);
   }

   @GetMapping("auth/cancelFollow/{id}")
   public Result cancelFollow(@PathVariable("id") Long id, HttpServletRequest request){
      userFollowService.cancelFollow(id);
      return Result.ok();
   }

}