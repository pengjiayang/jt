package com.jt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemCat;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyUITree;

import redis.clients.jedis.Jedis;

@Service
public class ItemCatServiceImpl implements ItemCatService{
	
	@Autowired
	private ItemCatMapper itemCatMapper;

	@Override
	public String findItemCatNameById(Long itemCatId) {
		ItemCat item = itemCatMapper.selectById(itemCatId);
		return item.getName();
	}

	@Override
	public List<EasyUITree> findItemCatTree(Long parentId) {
		//根据父类id查询全部根级商品信息获取全部根父类商品item.id=根id
		 List<ItemCat> item = findItemByParentId(parentId);
		 //创建itemtree对象风格装数据
		 List<EasyUITree> treeItem = new ArrayList<>();
		//封装树节点对象
		 for (ItemCat itemCat : item) {
			 EasyUITree tree = new EasyUITree();
			 tree.setId(itemCat.getId());
			 tree.setText(itemCat.getName());
			 String state = itemCat.getIsParent()?"closed":"open";
			 List<ItemCat> child = findItemByParentId(itemCat.getId());//
			 List<EasyUITree> treeItem1 = new ArrayList<>();
			for (ItemCat s : child) {
				EasyUITree children = new EasyUITree();
					children.setId(s.getId());
					children.setText(s.getName());
					String state1 = s.getIsParent()?"closed":"open";
					children.setState(state1);
					List<ItemCat> child1 = findItemByParentId(s.getId());
					List<EasyUITree> treeItem11 = new ArrayList<>();
					for (ItemCat ss : child1) {
						EasyUITree children1 = new EasyUITree();
						children1.setId(ss.getId());
						children1.setText(ss.getName());
						String state11 = ss.getIsParent()?"closed":"open";
						treeItem11.add(children1);
						children1.setState(state11);
						}
					treeItem1.add(children);
					children.setChildren(treeItem11);
			}
			tree.setChildren(treeItem1);
			tree.setState(state);
			treeItem.add(tree);
		}
		return treeItem;
	}

	private List<ItemCat> findItemByParentId(Long parentId) {
		QueryWrapper<ItemCat> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("parent_id", parentId);
		return itemCatMapper.selectList(queryWrapper);
	}

	@Override
	public List<EasyUITree> findItemCatByCatBy(Long parentId) {
		// TODO Auto-generated method stub
		return null;
	}

//	public List<EasyUITree> findItemCatByCatBy(Long parentId) {
//		String key = "ITEM_CAT_"+parentId;
//		String result = jedis.get(key);
//		List<EasyUITree> treeList = new ArrayList<>();
//		if(StringUtils.isEmpty(result)) {
//			//如果为空,查询数据库
//			treeList = findItemCatTree(parentId);
//			//将数据转化为JSON
//			String json = ObjectMapperUtil.toJSON(treeList);
//			jedis.setex(key, 7*24*3600, json);
//			System.out.println("业务查询数据库");
//		}else {
//			//数据有缓存
//			treeList = ObjectMapperUtil.toObject(result, treeList.getClass());
//			System.out.println("业务查询redis缓存");
//		}
//		return treeList;
//	}	
}




