package com.jt.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.jt.common.mapper.SysMapper;
import com.jt.common.po.Item;

public interface ItemMapper extends SysMapper<Item>{
	//查询记录总数 @Insert  @Update  @Delete
	
	@Select("select count(*) from tb_item")
	int findItemCount();
	
	/**
	 * 规定:
	 * 	mybatis中不允许多值传参
	 * 核心思想: 将多值转化为单值
	 * 实际应用: 1.对象   2.Map集合  3.List/Array 
	 * @param start
	 * @param rows
	 * @return
	 */
	//@Select("select * from tb_item  order by updated desc limit #{start},#{rows}")
	List<Item> findItemList(@Param("start") Integer start, @Param("rows") Integer rows);
	
	@Select("select name from tb_item_cat where id = #{itemId}")
	String findItemCatById(Long itemId);

	void updateStatus(@Param("ids")Long[] ids, @Param("status")int status);
	
	
	
	
	
	
	
	
}
