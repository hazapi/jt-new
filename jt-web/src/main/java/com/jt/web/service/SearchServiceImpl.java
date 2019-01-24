package com.jt.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.web.pojo.SolrResult;

@Service
public class SearchServiceImpl implements SearchService {
	
	@Autowired
	private HttpClientService httpClient;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Override
	public List<SolrResult> search(String q) {
		String url = "http://search.jt.com/solr/search";
		
		Map<String,String> params = new HashMap<>();
		params.put("q", q);
		
		String resultJSON = 
				httpClient.doGet(url, params);

		List<SolrResult> list = new ArrayList<>();
		
		try {
			list = objectMapper.readValue(resultJSON, list.getClass());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
