package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.result.Result;
import com.atguigu.service.HouseService;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: xyf
 * @date: 2023/1/4 23:42
 */
@Controller
@RequestMapping(value="/house")
@SuppressWarnings({"unchecked", "rawtypes"})
public class HouseController {

    @Reference
    public HouseService houseService;

    @PostMapping(value = "/list/{pageNum}/{pageSize}")
    @ResponseBody
    public Result findListPage(@RequestBody HouseQueryVo houseQueryVo,
                               @PathVariable Integer pageNum,
                               @PathVariable Integer pageSize) {
        PageInfo<HouseVo> pageInfo = houseService.findListPage(pageNum, pageSize, houseQueryVo);
        return Result.ok(pageInfo);
    }
}
