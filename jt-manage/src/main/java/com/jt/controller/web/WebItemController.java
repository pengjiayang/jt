package com.jt.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jt.anno.Cache_find;
import com.jt.enu.KEY_ENUM;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
//后台接收用户请求获取商品信息
//url:manage.jt/web/item/findItemById?id=562379
@Api(tags="1-find the item")
@RestController
@RequestMapping("/web/item")
public class WebItemController {

	@Autowired
	private ItemService is;
	//查询item对象
	@Cache_find(key="ITEM",keyType=KEY_ENUM.AUTO)
	@ApiOperation(value="1-查找商品",notes="根据id删除商品",produces="application/json")
	@ApiImplicitParam(name="id",value="根据id查询商品",paramType="query",dataType="Long")
	@RequestMapping(value="/findItemById",method=RequestMethod.GET)
	public Item findItemById(Long id) {
		return is.findItemById(id);
	}
	//查询itemDesc
	@Cache_find(key="ITEM_DESC",keyType=KEY_ENUM.AUTO)
	@ApiOperation(value="2-查找商品描述",notes="根据id查找商品描述",produces="application/json")
	@ApiImplicitParam(name="id",value="根据id查询商品描述",paramType="query",dataType="Long")
	@RequestMapping(value="/findItemDescById",method=RequestMethod.GET)
	public ItemDesc findItemDescById(Long id) {
		return is.findItemDescById(id);
	}
}
