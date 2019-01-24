package com.jt.manage.mapper;

import java.util.List;

import com.jt.manage.pojo.User;

public interface UserMapper {
	//查询全部数据
	List<User> findAll();
}
