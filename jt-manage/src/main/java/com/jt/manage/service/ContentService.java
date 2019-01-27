package com.jt.manage.service;

import java.util.List;

import com.jt.common.po.Content;
import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.SysResult;

public interface ContentService {

	EasyUIResult findContent(Long categoryId, Integer page, Integer rows);

	List<Content> findContentByCCId(Long categoryId);

	SysResult saveContent(Content content);

	SysResult updateContent(Content content);

	SysResult deleteContent(Long[] ids);

}
