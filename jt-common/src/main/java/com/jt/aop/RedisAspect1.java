package com.jt.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.jt.anno.Cache_find1;
import com.jt.enu.KEY_ENUM1;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ShardedJedis;

//定义一个查询的注解 
@Component//将对象交给spring容器管理
@Aspect //表示切面
public class RedisAspect1 {
	//容器初始化时不需要实例化该对象
	//只有用户使用时才初始化话
	//一般工具类中加该注解
//	@Autowired(required = false) //公共类加注解的话要先给属性赋值 或者加required
//	private ShardedJedis jedis;
	//环绕要加返回值
	//使用该方法可以直接获取我们的注解对象
	@Autowired(required = false)
	private JedisCluster jedis;
	@Around("@annotation(cache_find)")//传入注解//传入切入点
	public Object around(ProceedingJoinPoint joinPoint,Cache_find1 cache_find) {
		//1.获取key的值
		String key = getKey(joinPoint,cache_find);
		String result = jedis.get(key);
		Object data =null;
		try {
			if(StringUtils.isEmpty(result)) {
				//查询数据库
				data = joinPoint.proceed();
				String json = ObjectMapperUtil.toJSON(data);
				if(cache_find.secondes()==0) {
					jedis.set(key, json);
				}else {
					jedis.setex(key, cache_find.secondes(),json);
					System.out.println("第一次查询");
				}
			}else {
				Class<?> targetClass = getTargetClass(joinPoint);
				data = ObjectMapperUtil.toObject(result, targetClass);
				System.out.println("AOP查询缓存!!!");
			}
		} catch (Throwable e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		//如果结果为null,表示缓存中没有数据
		//查询数据库
		return data;
	}

	private String getKey(ProceedingJoinPoint joinPoint, Cache_find1 cache_find) {
		//1.获取key 的类型
		KEY_ENUM1 keyType = cache_find.keyType();
		if(keyType.equals(KEY_ENUM1.EMPTY)) {
			return cache_find.key();
		}
		String strArgs = String.valueOf(joinPoint.getArgs()[0]);
		
		return cache_find.key()+"_"+strArgs;
	}
	public Class<?> getTargetClass(ProceedingJoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		return signature.getReturnType();
	}
	
}

























