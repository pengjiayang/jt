package com.jt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jt.anno.Cache_find;
import com.jt.anno.Cache_find1;
import com.jt.enu.KEY_ENUM;
import com.jt.enu.KEY_ENUM1;
import com.jt.service.ItemCatService;
import com.jt.vo.EasyUITree;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/item/cat")
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;
	
	/**
	 * 1.用户发起pst请求懈怠了itemCatId=560
	 * 2.servlet
	 */
	//实现根据id查询商品分类信息
	@RequestMapping("/queryItemName")
	public String findItemCatNameById(Long itemCatId) {
		return itemCatService.findItemCatNameById(itemCatId);
	}
	
	
	@RequestMapping("/list")
	@Cache_find(key="ITEM_CAT",keyType=KEY_ENUM.AUTO)
	public List<EasyUITree> findItemCatTree(@RequestParam(value="id",defaultValue="0") Long parentId){
		parentId=0L;
		return itemCatService.findItemCatTree(parentId);
//		return itemCatService.findItemCatByCatBy(parentId);
	}
}



















