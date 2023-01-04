package com.atguigu.dao;

import com.atguigu.entity.Role;

import java.util.List;

/**
 * @description: 角色管理
 * @author: xyf
 * @date: 2023/1/2 13:14
 */
public interface RoleDao extends BaseDao<Role>{
    List<Role> findAll();
}
