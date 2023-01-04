package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.*;
import com.atguigu.result.Result;
import com.atguigu.service.*;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.PageInfo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 房源管理
 * @author: xyf
 * @date: 2023/1/3 12:06
 */
@Controller
@RequestMapping(value="/house")
@SuppressWarnings({"unchecked", "rawtypes"})
public class HouseController extends BaseController{

    @Reference
    private CommunityService communityService;
    @Reference
    private DictService dictService;
    @Reference
    private HouseService houseService;
    @Reference
    private HouseImageService houseImageService;

    @Reference
    private HouseBrokerService houseBrokerService;

    @Reference
    private HouseUserService houseUserService;

    @Reference
    private UserFollowService userFollowService;


    private final static String LIST_ACTION = "redirect:/house";
    private final static String PAGE_SUCCESS = "common/successPage";
    private static final String PAGE_EDIT = "house/edit";
    private static final String PAGE_CREATE = "house/create";
    private final static String PAGE_INDEX = "house/index";
    private final static String PAGE_SHOW = "house/show";

    /**
     * 进入新增
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('house.create')")
    @GetMapping("/create")
    public String create(ModelMap model) {
        model.addAttribute("communityList",communityService.findAll());
        model.addAttribute("houseTypeList",dictService.findListByDictCode("houseType"));
        model.addAttribute("floorList",dictService.findListByDictCode("floor"));
        model.addAttribute("buildStructureList",dictService.findListByDictCode("buildStructure"));
        model.addAttribute("directionList",dictService.findListByDictCode("direction"));
        model.addAttribute("decorationList",dictService.findListByDictCode("decoration"));
        model.addAttribute("houseUseList",dictService.findListByDictCode("houseUse"));
        return PAGE_CREATE;
    }

    /**
     * 保存新增
     * @param house
     * @return
     */
    @PreAuthorize("hasAuthority('house.create')")
    @PostMapping("/save")
    public String save(House house) {
        houseService.insert(house);
        return PAGE_SUCCESS;
    }


    /**
     * 列表
     * @param model
     * @param request
     * @return
     */
    @PreAuthorize("hasAuthority('house.show')")
    @RequestMapping
    public String index(ModelMap model, HttpServletRequest request) {
        Map<String,Object> filters = getFilters(request);
        PageInfo<House> page = houseService.findPage(filters);
        model.addAttribute("page", page);
        model.addAttribute("filters", filters);
        model.addAttribute("communityList",communityService.findAll());
        model.addAttribute("houseTypeList",dictService.findListByDictCode("houseType"));
        model.addAttribute("floorList",dictService.findListByDictCode("floor"));
        model.addAttribute("buildStructureList",dictService.findListByDictCode("buildStructure"));
        model.addAttribute("directionList",dictService.findListByDictCode("direction"));
        model.addAttribute("decorationList",dictService.findListByDictCode("decoration"));
        model.addAttribute("houseUseList",dictService.findListByDictCode("houseUse"));
        return PAGE_INDEX;
    }

    /**
     * 编辑
     * @param model
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('house.edit')")
    @GetMapping("/edit/{id}")
    public String edit(ModelMap model,@PathVariable Long id) {
        House house = houseService.getById(id);
        model.addAttribute("house",house);
        model.addAttribute("communityList",communityService.findAll());
        model.addAttribute("houseTypeList",dictService.findListByDictCode("houseType"));
        model.addAttribute("floorList",dictService.findListByDictCode("floor"));
        model.addAttribute("buildStructureList",dictService.findListByDictCode("buildStructure"));
        model.addAttribute("directionList",dictService.findListByDictCode("direction"));
        model.addAttribute("decorationList",dictService.findListByDictCode("decoration"));
        model.addAttribute("houseUseList",dictService.findListByDictCode("houseUse"));
        return PAGE_EDIT;
    }

    /**
     * 保存更新
     * @param house
     * @return
     */
    @PreAuthorize("hasAuthority('house.edit')")
    @PostMapping(value="/update")
    public String update(House house) {
        houseService.update(house);
        return PAGE_SUCCESS;
    }
    /**
     * 删除
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('house.delete')")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        houseService.delete(id);
        return LIST_ACTION;
    }

    /**
     * 发布
     * @param id
     * @param status
     * @return
     */
    @PreAuthorize("hasAuthority('house.publish')")
    @GetMapping("/publish/{id}/{status}")
    public String publish(@PathVariable Long id,@PathVariable Integer status) {
        houseService.publish(id, status);
        return LIST_ACTION;
    }

    /**
     * 详情
     * @param model
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('house.show')")
    @GetMapping("/{id}")
    public String show(ModelMap model,@PathVariable Long id) {
        House house = houseService.getById(id);
        Community community = communityService.getById(house.getCommunityId());
        List<HouseBroker> houseBrokerList = houseBrokerService.findListByHouseId(id);
        List<HouseUser> houseUserList = houseUserService.findListByHouseId(id);
        List<HouseImage> houseImage1List = houseImageService.findList(id, 1);
        List<HouseImage> houseImage2List = houseImageService.findList(id, 2);

        model.addAttribute("house",house);
        model.addAttribute("community",community);
        model.addAttribute("houseBrokerList",houseBrokerList);
        model.addAttribute("houseUserList",houseUserList);
        model.addAttribute("houseImage1List",houseImage1List);
        model.addAttribute("houseImage2List",houseImage2List);
        return PAGE_SHOW;
    }

    /**
     * 房源列表
     *
     * @return
     */
    @PreAuthorize("hasAuthority('house.show')")
    @PostMapping(value = "/list/{pageNum}/{pageSize}")
    public Result findListPage(@RequestBody HouseQueryVo houseQueryVo,
                               @PathVariable Integer pageNum,
                               @PathVariable Integer pageSize) {
        PageInfo<HouseVo> pageInfo = houseService.findListPage(pageNum, pageSize, houseQueryVo);
        return Result.ok(pageInfo);
    }

    @PreAuthorize("hasAuthority('house.show')")
    @GetMapping("info/{id}")
    public Result info(@PathVariable Long id, HttpServletRequest request) {
        House house = houseService.getById(id);
        Community community = communityService.getById(house.getCommunityId());
        List<HouseBroker> houseBrokerList = houseBrokerService.findListByHouseId(id);
        List<HouseImage> houseImage1List = houseImageService.findList(id, 1);

        Map<String, Object> map = new HashMap<>();
        map.put("house",house);
        map.put("community",community);
        map.put("houseBrokerList",houseBrokerList);
        map.put("houseImage1List",houseImage1List);
        //关注业务后续补充，当前默认返回未关注
        UserInfo userInfo = (UserInfo)request.getSession().getAttribute("USER");
        Boolean isFollow = false;
        if(null != userInfo) {
            Long userId = userInfo.getId();
            isFollow = userFollowService.isFollowed(userId, id);
        }
        map.put("isFollow",isFollow);
        return Result.ok(map);
    }
}
