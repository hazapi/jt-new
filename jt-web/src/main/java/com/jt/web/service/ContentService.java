package com.jt.web.service;

import java.util.List;

import com.jt.common.po.Content;

public interface ContentService {

	List<Content> findContentByCCId(Long categoryId);

}
