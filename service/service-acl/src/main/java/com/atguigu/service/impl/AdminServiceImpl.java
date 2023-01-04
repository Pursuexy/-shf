package com.atguigu.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.AdminDao;
import com.atguigu.dao.BaseDao;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service(interfaceClass = AdminService.class)
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {

   @Autowired
   private AdminDao adminDao;

   @Override
   protected BaseDao<Admin> getEntityDao() {
      return adminDao;
   }

   @Override
   public List<Admin> findAll() {
      return adminDao.findAll();
   }

   @Override
   public Admin getByUsername(String username) {
      return adminDao.getByUsername(username);
   }

}