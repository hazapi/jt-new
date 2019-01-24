package com.jt.web.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.po.User;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private HttpClientService httpClient;
	@Autowired
	private ObjectMapper objectMapper;
	
	@Override
	public void saveUser(User user) {
		String url ="http://sso.jt.com/user/register";
		Map<String,String> params = 
				new HashMap<>();
		String md5Pass = 
				DigestUtils.md5Hex(user.getPassword());
		params.put("username",user.getUsername());
		params.put("password",md5Pass);
		params.put("phone", user.getPhone());
		params.put("email", user.getEmail());
		
		String sysJSON = 
				httpClient.doPost(url, params, null);
		try {
			SysResult sysResult = 
			objectMapper.readValue(sysJSON, SysResult.class);

			if(sysResult.getStatus() != 200) {
				
				throw new RuntimeException();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public String findUserByUP(User user) {
		String url = "http://sso.jt.com/user/login";
		
		Map<String,String> params 
					= new HashMap<String, String>();
		params.put("username",user.getUsername());
		params.put("password",DigestUtils.md5Hex(user.getPassword()));
		
		String sysJSON = httpClient.doPost(url, params);
		
		try {
			SysResult sysResult = 
					objectMapper.readValue(sysJSON, SysResult.class);
			//判断后台处理是否正确
			if(sysResult.getStatus() == 200) {
				//如果正确则返回token数据
				return (String) sysResult.getData();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	
	
	
	
	
}
