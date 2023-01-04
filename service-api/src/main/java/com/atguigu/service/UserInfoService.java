package com.atguigu.service;

import com.atguigu.entity.UserInfo;

public interface UserInfoService extends BaseService<UserInfo> {
    UserInfo getByPhone(String phone);
}