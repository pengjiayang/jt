package com.jt.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jt.enu.KEY_ENUM;

@Retention(RetentionPolicy.RUNTIME) //运行期间保留
@Target(ElementType.METHOD)	//注解的使用范围
public @interface Cache_find {
	String key()		default "";						//用户key
	KEY_ENUM keyType() 	default KEY_ENUM.EMPTY;			//用户key类型
	int seconds()		default 0;					    //数据保存时间秒 默认永不过期
}
