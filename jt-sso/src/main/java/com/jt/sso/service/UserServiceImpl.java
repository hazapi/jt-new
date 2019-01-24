package com.jt.sso.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.sso.mapper.UserMapper;
import com.jt.sso.pojo.User;

import redis.clients.jedis.JedisCluster;


@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private JedisCluster jedisCluster;

	@Override
	public List<User> findAll() {
		
		return userMapper.selectList(null);
	}

	@Override
	public boolean findCheckUser(String param, Integer type) {
		String cloumn = null;
		switch (type) {
		case 1:
			cloumn = "username";break;
		case 2:
			cloumn = "phone";break;
		case 3:
			cloumn = "email";break;

		}
		//如果数据库中没有记录返回值为0，有记录不为0
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(cloumn, param);
		int count =
		userMapper.selectCount(queryWrapper);
		return count == 0 ? false:true;
	}

	@Override
	@Transactional //开启事物控制
	public void saveUser(User user) {
		//补全数据
		user.setEmail(user.getPhone());
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		
		userMapper.insert(user);
		
	}

	@Override
	public String findUserByUP(User user) {
		QueryWrapper<User> queryWrapper = 
				new QueryWrapper<User>(user);
		User userDB =
		userMapper.selectOne(queryWrapper);
		//System.out.println("4:"+userDB);
		if (userDB!=null) {
			//表示用户名和密码正确
			String str =
			"JT_TICKET"+System.currentTimeMillis()+user.getUsername();
			//System.out.println("5:"+str);
			String token =
			DigestUtils.md5DigestAsHex(str.getBytes());
			//System.out.println("6:"+token);
			
			try {
				String userJSON = 
						objectMapper.writeValueAsString(userDB);
				//System.out.println("8"+userJSON);
				//将数据保存在redis
				jedisCluster.setex(token, 3600*7*24, userJSON);
				//System.out.println("1");
				return token;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
