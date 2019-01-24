package com.jt.manage.service;

import java.util.List;

import com.jt.manage.vo.EasyUITree;

public interface ItemCatService {

	List<EasyUITree> findItemCatAll(long parentId);

	List<EasyUITree> findItemCatCache(Long parentId);
	
}
