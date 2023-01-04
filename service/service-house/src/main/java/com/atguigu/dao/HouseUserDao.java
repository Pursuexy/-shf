package com.atguigu.dao;

import com.atguigu.entity.HouseUser;

import java.util.List;

public interface HouseUserDao extends BaseDao<HouseUser> {

    List<HouseUser> findListByHouseId(Long houseId);
}