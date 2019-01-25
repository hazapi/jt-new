package com.jt.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.RespectBinding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.po.Content;
import com.jt.web.pojo.AdPojo;
import com.jt.web.service.ContentService;
import com.sun.tools.classfile.Method;

@Controller
public class IndexController {
	
	@Autowired
	private ContentService contentService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Value("${AD1_CATEGORY_ID}")
	private Long categoryId;
	
	//实现系统首页跳转并实现首页广告位轮播展示
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String index(Model model){
		
		List<Content> con = contentService.findContentByCCId(categoryId);
		System.out.println(con.toString());
		List<AdPojo> ad1 = new ArrayList<>();
		for (Content content : con) {
			AdPojo adp = new AdPojo();
			adp.setAlt(content.getSubTitle());
			adp.setHref(content.getUrl());
			adp.setSrc(content.getPic());
			adp.setSrcB(content.getPic2());
			adp.setTxt(content.getContent());
			ad1.add(adp);
		}
		
		try {
			String ad1JSON = objectMapper.writeValueAsString(ad1);
			model.addAttribute("indexAD1", ad1JSON);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	
		return "index";
	}
}
