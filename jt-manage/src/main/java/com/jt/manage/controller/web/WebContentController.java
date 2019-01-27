package com.jt.manage.controller.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jt.common.po.Content;
import com.jt.common.po.Item;
import com.jt.common.po.ItemDesc;
import com.jt.manage.service.ContentService;
import com.jt.manage.service.ItemService;


@RestController  //@Controller + @ResponseBody
@RequestMapping("/web/content")
public class WebContentController {

	@Autowired
	private ContentService contentService;
	
	@RequestMapping("findContentByCCId")
	public List<Content> findContentByCCId(Long categoryId){
		
		return contentService.findContentByCCId(categoryId);
		
	}
}
