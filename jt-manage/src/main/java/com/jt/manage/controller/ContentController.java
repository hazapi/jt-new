package com.jt.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.common.po.Content;
import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.SysResult;
import com.jt.manage.service.ContentService;

@RestController
@RequestMapping("/content")
public class ContentController {
	@Autowired
	private ContentService contentService;

	@RequestMapping("/query/list")
	public EasyUIResult findContent(Long categoryId,Integer page,Integer rows){

		return contentService.findContent(categoryId,page,rows);
	}

	//保存内容
	@RequestMapping("/save")
	public SysResult saveContent(Content content) {
		SysResult result=contentService.saveContent(content);


		return result;
	}

	@RequestMapping("/edit")
	public SysResult updateContent(Content content) {
		SysResult result=contentService.updateContent(content);

		return result;


	}

	//根据ID删除
	@RequestMapping("/delete")
	public SysResult deleteContent(Long[] ids) {
		SysResult result=contentService.deleteContent(ids);
		return result;


	}



}
