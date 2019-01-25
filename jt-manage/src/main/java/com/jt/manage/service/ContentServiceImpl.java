package com.jt.manage.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.common.po.Content;
import com.jt.common.po.ContentCategory;
import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.SysResult;
import com.jt.manage.mapper.ContentCategoryMapper;
import com.jt.manage.mapper.ContentMapper;

@Service
public class ContentServiceImpl implements ContentService{
	@Autowired
	private ContentMapper contentMapper;
	@Autowired
	private ContentCategoryMapper contentCategoryMapper;

	@Override
	public EasyUIResult findContent(Long categoryId, Integer page, Integer rows) {
		ContentCategory cc = contentCategoryMapper.selectByPrimaryKey(categoryId);
		Integer start = (page-1)*rows;
		EasyUIResult result =null;
		if(!cc.getIsParent()) {
			//求出总条数
			Content content = new Content();
			content.setCategoryId(categoryId);
			int count = contentMapper.selectCount(content);
			//设置起始页
			//设置分页
			List<Content> contentList = contentMapper.findContentLimit(categoryId,start,rows);

			result = new EasyUIResult(count,contentList);
		}
		return result;
	}

	@Override
	public List<Content> findContentByCCId(Long categoryId) {
		Content content = new Content();
		content.setCategoryId(categoryId);
		List<Content> cList = contentMapper.select(content);
		return cList;
	}

	@Override
	public SysResult saveContent(Content content) {
		//补全content属性
		content.setCreated(new Date());
		content.setUpdated(content.getCreated());
		//2.将content属性,映射到数据库tb_content表中
		contentMapper.insert(content);
		//返回结果
		return SysResult.oK();
	}

	@Override
	public SysResult updateContent(Content content) {
		//更新编辑的时间
		content.setUpdated(new Date());
		contentMapper.updateByPrimaryKey(content);
		return SysResult.oK();
	}

	@Override
	public SysResult deleteContent(Long[] ids) {
		for (Long id:ids) {
			contentMapper.deleteByPrimaryKey(id);
		}
		return SysResult.oK();
	}

}
