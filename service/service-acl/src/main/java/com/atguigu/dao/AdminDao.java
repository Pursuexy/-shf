package com.atguigu.dao;

import com.atguigu.entity.Admin;

import java.util.List;

/**
 * @description: 用户管理
 * @author: xyf
 * @date: 2023/1/2 22:59
 */
public interface AdminDao extends BaseDao<Admin> {

    List<Admin> findAll();

    Admin getByUsername(String username);
}
