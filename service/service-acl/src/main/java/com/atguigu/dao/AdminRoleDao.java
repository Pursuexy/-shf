package com.atguigu.dao;

import com.atguigu.entity.AdminRole;

import java.util.List;

public interface AdminRoleDao extends BaseDao<AdminRole> {

    void deleteByAdminId(Long adminId);

    List<Long> findRoleIdByAdminId(Long adminId);
}