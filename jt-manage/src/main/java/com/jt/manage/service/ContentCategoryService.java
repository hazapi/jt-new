package com.jt.manage.service;

import java.util.List;

import com.jt.common.po.ContentCategory;
import com.jt.common.vo.SysResult;
import com.jt.manage.vo.EasyUITree;

public interface ContentCategoryService {

	List<EasyUITree> findContentCategory(Long parentId);

	void createContentCategory(Long parentId, String name);

	void updateContentCategoryName(Long id, String name);

	SysResult deleteContentCategory(Long id);

	Long[] getCategoryId();

}
