package com.jt;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.pojo.ItemDesc;
import redis.clients.jedis.Jedis;

public class TestRedis {
	//String 类型操作方式
	@Test
	public void testString() {
		Jedis jedis = new Jedis("192.168.92.132",6379);
		jedis.set("1902", "1902班");
		System.out.println(jedis.get("1902"));
	}
	
	//分布式锁的问题!!!多线程操作同样的数据
	@Test
	public void testTimeOut() throws Exception {
		@SuppressWarnings("resource")
		Jedis jedis = new Jedis("192.168.92.132",6379);
		jedis.setex("aa",2, "aaa");//set+expre
		System.out.println(jedis.get("aa"));
		Thread.sleep(3000);
		Long result = jedis.setnx("aa", "bb");//当key不存在时操作正常,key存在时操作失败
		System.out.println(result);
		System.out.println("获取输出的数据"+jedis.get("aa"));
	}
	
	//list集合转化成JSON
	@Test
	public void listTOJSON() throws Exception {
		ItemDesc itemDesc1 = new ItemDesc();
		itemDesc1.setItemId(1000L).setItemDesc("数据测试!!!!");
		ItemDesc itemDesc2 = new ItemDesc();
		itemDesc2.setItemId(1000L).setItemDesc("数据测试!!!!");
		List<ItemDesc> list =new ArrayList<ItemDesc>();
		list.add(itemDesc1);
		list.add(itemDesc2);
		list.forEach((ItemDesc item)->System.out.println(item));
		Stream<ItemDesc> stream = list.stream();
		stream.filter(p ->p.getItemId()>100L);
		ObjectMapper mapper = new ObjectMapper();
		//转化JSON必须有get/set方法
		String json = mapper.writeValueAsString(list);
		System.out.println(json);
		//将数据保存到redis汇总
		Jedis jedis = new Jedis("192.168.92.132",6379);
		jedis.set("itemDescList",json);
		//从redis获取数据
		String result = jedis.get("itemDescList");
		@SuppressWarnings("unchecked")
		List<ItemDesc> descList = mapper.readValue(result, list.getClass());
		System.out.println(descList);
	}
	
	
	//1.实现对象转化JSON
	@Test
	public void objectToObject() throws Exception {
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(1000L).setItemDesc("数据测试!!!!");
		ObjectMapper mapper = new ObjectMapper();
		//转化JSON必须有get/set方法
		String json = mapper.writeValueAsString(itemDesc);
		System.out.println(json);
		//JSON串转化为对象
		ItemDesc item = mapper.readValue(json, ItemDesc.class);
		System.out.println(item);
	}
	//利用Redis保存业务数据,从数据库中查询的数据存在缓存当中
	//数据库数据:对象Object 
	
	public void testSetObject() {
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
