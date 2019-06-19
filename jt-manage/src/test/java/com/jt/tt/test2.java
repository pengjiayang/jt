package com.jt.tt;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class test2 {
	
	public static void main(String[] args) {
		
		Runnable r = test2::run;
		new Thread(r).start();
	}
	public static void run() {
		System.out.println("方法代码引用");
	}
	
	//redis事物控制
	@Test
	public void TestTx() {
		@SuppressWarnings("resource")
		Jedis jedis = new Jedis("192.168.92.135",6379);
		Transaction transaction = jedis.multi();//开启事务
		try {
			transaction.set("ww", "wwww");
			transaction.set("dd", null);
			transaction.exec();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.discard();
		}
	}
}
