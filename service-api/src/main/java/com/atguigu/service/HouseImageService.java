package com.atguigu.service;

import com.atguigu.entity.HouseImage;

import java.util.List;

/**
 * @description:
 * @author: xyf
 * @date: 2023/1/3 12:24
 */
public interface HouseImageService extends BaseService<HouseImage>{
    List<HouseImage> findList(Long houseId, Integer type);
}
