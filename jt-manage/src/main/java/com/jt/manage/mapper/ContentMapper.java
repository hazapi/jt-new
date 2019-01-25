package com.jt.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jt.common.mapper.SysMapper;
import com.jt.common.po.Content;

public interface ContentMapper extends SysMapper<Content> {

	List<Content> findContentLimit(@Param("categoryId") Long categoryId, 
						  @Param("start") Integer start, 
						  @Param("rows") Integer rows);

}
