package com.jt.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jt.enu.KEY_ENUM1;

//定义一个查询注解
@Retention(RetentionPolicy.RUNTIME)//程序运行时有效
@Target({ElementType.METHOD})//注解懂得作用方位
public @interface Cache_find1 {
	String key() default "";//接收用户key 的值
	KEY_ENUM1 keyType() default KEY_ENUM1.AUTO;//定义枚举的类型
	int secondes() default 0;//0表示永不失效
}
