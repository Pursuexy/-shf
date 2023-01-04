package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Dict;
import com.atguigu.result.Result;
import com.atguigu.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value="/dict")
@SuppressWarnings({"unchecked", "rawtypes"})
public class DictController extends BaseController {

   @Reference
   private DictService dictService;

   /**
    * 根据编码获取子节点数据列表
    * @param dictCode
    * @return
    */
   @GetMapping(value = "/findListByDictCode/{dictCode}")
   @ResponseBody
   public Result<List<Dict>> findListByDictCode(@PathVariable String dictCode) {
      List<Dict> list = dictService.findListByDictCode(dictCode);
      return Result.ok(list);
   }
}