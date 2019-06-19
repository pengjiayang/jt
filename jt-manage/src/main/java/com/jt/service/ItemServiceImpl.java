package com.jt.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.xml.crypto.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUIData;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;

	@Override
	public EasyUIData findItemByPage(Integer rows, Integer page) {
		//查询商品记录的总数
		Integer total = itemMapper.selectCount(null);
		Integer start = (page-1)*rows;
		//分页查询商品的记录
		List<Item> find = itemMapper.findItemByPage(start, rows);
		
		EasyUIData easyData = new EasyUIData();
		easyData.setRows(find);
		easyData.setTotal(total);
		return easyData;
	}
	@Transactional//添加事物的控制
	@Override
	public int saveItem(Item item,ItemDesc itemDesc) {
		item.setStatus(1)
			.setCreated(new Date())
			.setUpdated(item.getCreated());
			int rows = itemMapper.insert(item);
			itemDesc.setItemId(item.getId()).setCreated(new Date()).setUpdated(itemDesc.getCreated());
			itemDescMapper.insert(itemDesc);
			return rows;
	}

	
	/**
	 * propagation :事物的传播属性 
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void updateItem(Item item,ItemDesc itemDesc) {
		item.setUpdated(new Date());
		itemMapper.updateById(item);
		itemDesc.setItemId(item.getId()).setUpdated(new Date());
		itemDescMapper.updateById(itemDesc);
	}
	
	
	@Transactional
	@Override
	public void deleteItem(Long[] ids) {
		List<Long> itemList = Arrays.asList(ids);
		itemMapper.deleteBatchIds(itemList);
		itemDescMapper.deleteBatchIds(itemList);
	}
	/**
	 * sql:update tb_item set status =#{status},updated=#{updated} where id in();
	 */
	@Override
	public void updateStatus(Long[] ids, int status) {
		Item item = new Item();
		item.setStatus(status).setUpdated(new Date());
		UpdateWrapper<Item> updateWrapper = new UpdateWrapper<>();
		updateWrapper.in("id", Arrays.asList(ids));
		itemMapper.update(item, updateWrapper);
	}
	@Override
	public ItemDesc findItemDescById(Long itemId) {
		return itemDescMapper.selectById(itemId);
	}
	@Override
	public Item findItemById(Long id) {
		return itemMapper.selectById(id);
	}
}




