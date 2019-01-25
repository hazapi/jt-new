package com.jt.web.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.po.Content;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;

@Service
public class ContentServiceImpl implements ContentService {
	
	@Autowired
	private HttpClientService httpClient;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Content> findContentByCCId(Long categoryId) {
		String url = "http://manage.jt.com/web/content/findContentByCCId";
		
		Map<String,String> params = new HashMap<>();
		params.put("categoryId", categoryId+"");
		System.out.println("post提交准备");
		String resultJSON = httpClient.doPost(url, params);
		System.out.println("post提交完成");
		System.out.println("resultJSON"+resultJSON);
		List<Content> content = null;
		
		try {
			
			System.out.println("开始转换");
			 content = objectMapper.readValue(resultJSON,new TypeReference<List<Content>>() {
			});
			System.out.println("转换成功");
			Content content2 = content.get(0);
			System.out.println(content2);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return content;
	}

}
