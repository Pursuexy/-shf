package com.atguigu.dao;

import com.atguigu.entity.RolePermission;

import java.util.List;

public interface RolePermissionDao extends BaseDao<RolePermission> {

    void deleteByRoleId(Long roleId);

    List<Long> findPermissionIdListByRoleId(Long roleId);
}