package com.jt.manage.service;

import com.jt.common.po.Item;
import com.jt.common.po.ItemDesc;
import com.jt.common.vo.EasyUIResult;

public interface ItemService {

	EasyUIResult findItemListByPage(Integer page, Integer rows);

	String findItemCatNameById(Long itemId);

	void saveItem(Item item, String desc);

	void updateStatus(Long[] ids, int status);

	ItemDesc findItemDesc(Long itemId);

	void updateItem(Item item, String desc);

	void deleteItems(Long[] ids);

	Item findItemById(Long itemId);

	
}
