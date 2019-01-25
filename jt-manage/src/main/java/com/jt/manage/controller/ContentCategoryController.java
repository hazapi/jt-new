package com.jt.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.po.ContentCategory;
import com.jt.common.vo.SysResult;
import com.jt.manage.service.ContentCategoryService;
import com.jt.manage.vo.EasyUITree;

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
	@Autowired
	private ContentCategoryService contentCategoryService;
	
	/**
	 * 网站内容分类表查询(广告位)
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public List<EasyUITree> findContentCategory(
		@RequestParam(value="id",defaultValue="0")
		Long parentId){
		
		return contentCategoryService.findContentCategory(parentId);
		
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public SysResult createContentCategory(Long parentId,String name) {
		try {
			contentCategoryService.createContentCategory(parentId,name);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "添加失败");
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public SysResult updateContentCategoryName(Long id,String name) {
		try {
			contentCategoryService.updateContentCategoryName(id,name);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"更改失败");
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public SysResult deleteContentCategory(Long id) {
		try {
			SysResult result= contentCategoryService.deleteContentCategory(id);
			System.out.println(result.getStatus());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"删除失败,请检查是否存在子级目录或该条信息可能已经不存在");
	}
	
}
